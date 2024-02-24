/*     */ package net.minecraft.client.multiplayer;
/*     */ import awareline.main.event.Event;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.MovingSoundMinecart;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.client.particle.EntityFX;
/*     */ import net.minecraft.client.particle.EntityFirework;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.profiler.Profiler;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraft.world.storage.ISaveHandler;
/*     */ import net.minecraft.world.storage.MapStorage;
/*     */ import net.minecraft.world.storage.SaveHandlerMP;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import net.optifine.CustomGuis;
/*     */ import net.optifine.DynamicLights;
/*     */ import net.optifine.override.PlayerControllerOF;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ public class WorldClient extends World {
/*     */   private final NetHandlerPlayClient sendQueue;
/*  49 */   final Set<Entity> entityList = Sets.newHashSet(); private ChunkProviderClient clientChunkProvider;
/*  50 */   final Set<Entity> entitySpawnQueue = Sets.newHashSet();
/*  51 */   final Minecraft mc = Minecraft.getMinecraft();
/*  52 */   private final Set<ChunkCoordIntPair> previousActiveChunkSet = Sets.newHashSet();
/*     */   
/*     */   private boolean playerUpdate = false;
/*     */   
/*     */   public WorldClient(NetHandlerPlayClient netHandler, WorldSettings settings, int dimension, EnumDifficulty difficulty, Profiler profilerIn) {
/*  57 */     super((ISaveHandler)new SaveHandlerMP(), new WorldInfo(settings, "MpServer"), WorldProvider.getProviderForDimension(dimension), profilerIn, true);
/*     */     
/*  59 */     this.sendQueue = netHandler;
/*  60 */     getWorldInfo().setDifficulty(difficulty);
/*  61 */     this.provider.registerWorld(this);
/*  62 */     setSpawnPoint(new BlockPos(8, 64, 8));
/*  63 */     this.chunkProvider = createChunkProvider();
/*  64 */     this.mapStorage = (MapStorage)new SaveDataMemoryStorage();
/*  65 */     calculateInitialSkylight();
/*  66 */     calculateInitialWeather();
/*  67 */     Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, new Object[] { this });
/*     */     
/*  69 */     if (this.mc.playerController != null && this.mc.playerController.getClass() == PlayerControllerMP.class) {
/*     */       
/*  71 */       this.mc.playerController = (PlayerControllerMP)new PlayerControllerOF(this.mc, netHandler);
/*  72 */       CustomGuis.setPlayerControllerOF((PlayerControllerOF)this.mc.playerController);
/*     */     } 
/*  74 */     EventManager.call((Event)new EventTurn());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/*  82 */     super.tick();
/*  83 */     setTotalWorldTime(getTotalWorldTime() + 1L);
/*     */     
/*  85 */     if (getGameRules().getBoolean("doDaylightCycle"))
/*     */     {
/*  87 */       setWorldTime(getWorldTime() + 1L);
/*     */     }
/*     */     
/*  90 */     this.theProfiler.startSection("reEntryProcessing");
/*     */     
/*  92 */     for (int i = 0; i < 10 && !this.entitySpawnQueue.isEmpty(); i++) {
/*     */       
/*  94 */       Entity entity = this.entitySpawnQueue.iterator().next();
/*  95 */       this.entitySpawnQueue.remove(entity);
/*     */       
/*  97 */       if (!this.loadedEntityList.contains(entity))
/*     */       {
/*  99 */         spawnEntityInWorld(entity);
/*     */       }
/*     */     } 
/*     */     
/* 103 */     this.theProfiler.endStartSection("chunkCache");
/* 104 */     this.clientChunkProvider.unloadQueuedChunks();
/* 105 */     this.theProfiler.endStartSection("blocks");
/* 106 */     updateBlocks();
/* 107 */     this.theProfiler.endSection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidateBlockReceiveRegion(int x1, int y1, int z1, int x2, int y2, int z2) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IChunkProvider createChunkProvider() {
/* 130 */     this.clientChunkProvider = new ChunkProviderClient(this);
/* 131 */     return this.clientChunkProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateBlocks() {
/* 136 */     super.updateBlocks();
/* 137 */     this.previousActiveChunkSet.retainAll(this.activeChunkSet);
/*     */     
/* 139 */     if (this.previousActiveChunkSet.size() == this.activeChunkSet.size())
/*     */     {
/* 141 */       this.previousActiveChunkSet.clear();
/*     */     }
/*     */     
/* 144 */     int i = 0;
/*     */     
/* 146 */     for (ChunkCoordIntPair chunkcoordintpair : this.activeChunkSet) {
/*     */       
/* 148 */       if (!this.previousActiveChunkSet.contains(chunkcoordintpair)) {
/*     */         
/* 150 */         int j = chunkcoordintpair.chunkXPos << 4;
/* 151 */         int k = chunkcoordintpair.chunkZPos << 4;
/* 152 */         this.theProfiler.startSection("getChunk");
/* 153 */         Chunk chunk = getChunkFromChunkCoords(chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPos);
/* 154 */         playMoodSoundAndCheckLight(j, k, chunk);
/* 155 */         this.theProfiler.endSection();
/* 156 */         this.previousActiveChunkSet.add(chunkcoordintpair);
/* 157 */         i++;
/*     */         
/* 159 */         if (i >= 10) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void doPreChunk(int chuncX, int chuncZ, boolean loadChunk) {
/* 169 */     if (loadChunk) {
/*     */       
/* 171 */       this.clientChunkProvider.loadChunk(chuncX, chuncZ);
/*     */     }
/*     */     else {
/*     */       
/* 175 */       this.clientChunkProvider.unloadChunk(chuncX, chuncZ);
/*     */     } 
/*     */     
/* 178 */     if (!loadChunk)
/*     */     {
/* 180 */       markBlockRangeForRenderUpdate(chuncX << 4, 0, chuncZ << 4, (chuncX << 4) + 15, 256, (chuncZ << 4) + 15);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean spawnEntityInWorld(Entity entityIn) {
/* 189 */     boolean flag = super.spawnEntityInWorld(entityIn);
/* 190 */     this.entityList.add(entityIn);
/*     */     
/* 192 */     if (!flag) {
/*     */       
/* 194 */       this.entitySpawnQueue.add(entityIn);
/*     */     }
/* 196 */     else if (entityIn instanceof EntityMinecart) {
/*     */       
/* 198 */       this.mc.getSoundHandler().playSound((ISound)new MovingSoundMinecart((EntityMinecart)entityIn));
/*     */     } 
/*     */     
/* 201 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeEntity(Entity entityIn) {
/* 209 */     super.removeEntity(entityIn);
/* 210 */     this.entityList.remove(entityIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onEntityAdded(Entity entityIn) {
/* 215 */     super.onEntityAdded(entityIn);
/*     */     
/* 217 */     if (this.entitySpawnQueue.contains(entityIn))
/*     */     {
/* 219 */       this.entitySpawnQueue.remove(entityIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onEntityRemoved(Entity entityIn) {
/* 225 */     super.onEntityRemoved(entityIn);
/* 226 */     boolean flag = false;
/*     */     
/* 228 */     if (this.entityList.contains(entityIn))
/*     */     {
/* 230 */       if (entityIn.isEntityAlive()) {
/*     */         
/* 232 */         this.entitySpawnQueue.add(entityIn);
/* 233 */         flag = true;
/*     */       }
/*     */       else {
/*     */         
/* 237 */         this.entityList.remove(entityIn);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEntityToWorld(int entityID, Entity entityToSpawn) {
/* 250 */     Entity entity = getEntityByID(entityID);
/*     */     
/* 252 */     if (entity != null)
/*     */     {
/* 254 */       removeEntity(entity);
/*     */     }
/*     */     
/* 257 */     this.entityList.add(entityToSpawn);
/* 258 */     entityToSpawn.setEntityId(entityID);
/*     */     
/* 260 */     if (!spawnEntityInWorld(entityToSpawn))
/*     */     {
/* 262 */       this.entitySpawnQueue.add(entityToSpawn);
/*     */     }
/*     */     
/* 265 */     this.entitiesById.addKey(entityID, entityToSpawn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Entity getEntityByID(int id) {
/* 273 */     return (id == this.mc.thePlayer.getEntityId()) ? (Entity)this.mc.thePlayer : super.getEntityByID(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity removeEntityFromWorld(int entityID) {
/* 278 */     Entity entity = (Entity)this.entitiesById.removeObject(entityID);
/*     */     
/* 280 */     if (entity != null) {
/*     */       
/* 282 */       this.entityList.remove(entity);
/* 283 */       removeEntity(entity);
/*     */     } 
/*     */     
/* 286 */     return entity;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean invalidateRegionAndSetBlock(BlockPos pos, IBlockState state) {
/* 291 */     int i = pos.getX();
/* 292 */     int j = pos.getY();
/* 293 */     int k = pos.getZ();
/* 294 */     invalidateBlockReceiveRegion(i, j, k, i, j, k);
/* 295 */     return super.setBlockState(pos, state, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendQuittingDisconnectingPacket() {
/* 303 */     this.sendQueue.getNetworkManager().closeChannel((IChatComponent)new ChatComponentText("Quitting"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateWeather() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getRenderDistanceChunks() {
/* 315 */     return this.mc.gameSettings.renderDistanceChunks;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doVoidFogParticles(int posX, int posY, int posZ) {
/* 320 */     int i = 16;
/* 321 */     Random random = new Random();
/* 322 */     ItemStack itemstack = this.mc.thePlayer.getHeldItem();
/* 323 */     boolean flag = (this.mc.playerController.getCurrentGameType() == WorldSettings.GameType.CREATIVE && itemstack != null && Block.getBlockFromItem(itemstack.getItem()) == Blocks.barrier);
/* 324 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 326 */     for (int j = 0; j < 1000; j++) {
/*     */       
/* 328 */       int k = posX + this.rand.nextInt(i) - this.rand.nextInt(i);
/* 329 */       int l = posY + this.rand.nextInt(i) - this.rand.nextInt(i);
/* 330 */       int i1 = posZ + this.rand.nextInt(i) - this.rand.nextInt(i);
/* 331 */       blockpos$mutableblockpos.set(k, l, i1);
/* 332 */       IBlockState iblockstate = getBlockState((BlockPos)blockpos$mutableblockpos);
/* 333 */       iblockstate.getBlock().randomDisplayTick(this, (BlockPos)blockpos$mutableblockpos, iblockstate, random);
/*     */       
/* 335 */       if (flag && iblockstate.getBlock() == Blocks.barrier)
/*     */       {
/* 337 */         spawnParticle(EnumParticleTypes.BARRIER, (k + 0.5F), (l + 0.5F), (i1 + 0.5F), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllEntities() {
/* 347 */     this.loadedEntityList.removeAll(this.unloadedEntityList);
/*     */     
/* 349 */     for (Entity value : this.unloadedEntityList) {
/* 350 */       Entity entity = value;
/* 351 */       int j = entity.chunkCoordX;
/* 352 */       int k = entity.chunkCoordZ;
/*     */       
/* 354 */       if (entity.addedToChunk && isChunkLoaded(j, k, true)) {
/* 355 */         getChunkFromChunkCoords(j, k).removeEntity(entity);
/*     */       }
/*     */     } 
/*     */     
/* 359 */     for (Entity entity : this.unloadedEntityList) {
/* 360 */       onEntityRemoved(entity);
/*     */     }
/*     */     
/* 363 */     this.unloadedEntityList.clear();
/*     */     
/* 365 */     for (int i1 = 0; i1 < this.loadedEntityList.size(); i1++) {
/*     */       
/* 367 */       Entity entity1 = this.loadedEntityList.get(i1);
/*     */       
/* 369 */       if (entity1.ridingEntity != null) {
/*     */         
/* 371 */         if (!entity1.ridingEntity.isDead && entity1.ridingEntity.riddenByEntity == entity1) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 376 */         entity1.ridingEntity.riddenByEntity = null;
/* 377 */         entity1.ridingEntity = null;
/*     */       } 
/*     */       
/* 380 */       if (entity1.isDead) {
/*     */         
/* 382 */         int j1 = entity1.chunkCoordX;
/* 383 */         int k1 = entity1.chunkCoordZ;
/*     */         
/* 385 */         if (entity1.addedToChunk && isChunkLoaded(j1, k1, true))
/*     */         {
/* 387 */           getChunkFromChunkCoords(j1, k1).removeEntity(entity1);
/*     */         }
/*     */         
/* 390 */         this.loadedEntityList.remove(i1--);
/* 391 */         onEntityRemoved(entity1);
/*     */       } 
/*     */       continue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReportCategory addWorldInfoToCrashReport(CrashReport report) {
/* 401 */     CrashReportCategory crashreportcategory = super.addWorldInfoToCrashReport(report);
/* 402 */     crashreportcategory.addCrashSectionCallable("Forced entities", new Callable<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/* 406 */             return WorldClient.this.entityList.size() + " total; " + WorldClient.this.entityList;
/*     */           }
/*     */         });
/* 409 */     crashreportcategory.addCrashSectionCallable("Retry entities", new Callable<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/* 413 */             return WorldClient.this.entitySpawnQueue.size() + " total; " + WorldClient.this.entitySpawnQueue;
/*     */           }
/*     */         });
/* 416 */     crashreportcategory.addCrashSectionCallable("Server brand", new Callable<String>()
/*     */         {
/*     */           public String call() {
/* 419 */             return WorldClient.this.mc.thePlayer.getClientBrand();
/*     */           }
/*     */         });
/* 422 */     crashreportcategory.addCrashSectionCallable("Server type", new Callable<String>()
/*     */         {
/*     */           public String call() {
/* 425 */             return (WorldClient.this.mc.getIntegratedServer() == null) ? "Non-integrated multiplayer server" : "Integrated singleplayer server";
/*     */           }
/*     */         });
/* 428 */     return crashreportcategory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playSoundAtPos(BlockPos pos, String soundName, float volume, float pitch, boolean distanceDelay) {
/* 442 */     playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, soundName, volume, pitch, distanceDelay);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playSound(double x, double y, double z, String soundName, float volume, float pitch, boolean distanceDelay) {
/* 450 */     double d0 = this.mc.getRenderViewEntity().getDistanceSq(x, y, z);
/* 451 */     PositionedSoundRecord positionedsoundrecord = new PositionedSoundRecord(new ResourceLocation(soundName), volume, pitch, (float)x, (float)y, (float)z);
/*     */     
/* 453 */     if (distanceDelay && d0 > 100.0D) {
/*     */       
/* 455 */       double d1 = Math.sqrt(d0) / 40.0D;
/* 456 */       this.mc.getSoundHandler().playDelayedSound((ISound)positionedsoundrecord, (int)(d1 * 20.0D));
/*     */     }
/*     */     else {
/*     */       
/* 460 */       this.mc.getSoundHandler().playSound((ISound)positionedsoundrecord);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void makeFireworks(double x, double y, double z, double motionX, double motionY, double motionZ, NBTTagCompound compund) {
/* 466 */     this.mc.effectRenderer.addEffect((EntityFX)new EntityFirework.StarterFX(this, x, y, z, motionX, motionY, motionZ, this.mc.effectRenderer, compund));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWorldScoreboard(Scoreboard scoreboardIn) {
/* 471 */     this.worldScoreboard = scoreboardIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldTime(long time) {
/* 479 */     if (time < 0L) {
/*     */       
/* 481 */       time = -time;
/* 482 */       getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
/*     */     }
/*     */     else {
/*     */       
/* 486 */       getGameRules().setOrCreateGameRule("doDaylightCycle", "true");
/*     */     } 
/*     */     
/* 489 */     super.setWorldTime(time);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCombinedLight(BlockPos pos, int lightValue) {
/* 494 */     int i = super.getCombinedLight(pos, lightValue);
/*     */     
/* 496 */     if (Config.isDynamicLights())
/*     */     {
/* 498 */       i = DynamicLights.getCombinedLight(pos, i);
/*     */     }
/*     */     
/* 501 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setBlockState(BlockPos pos, IBlockState newState, int flags) {
/* 511 */     this.playerUpdate = isPlayerActing();
/* 512 */     boolean flag = super.setBlockState(pos, newState, flags);
/* 513 */     this.playerUpdate = false;
/* 514 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isPlayerActing() {
/* 519 */     if (this.mc.playerController instanceof PlayerControllerOF) {
/*     */       
/* 521 */       PlayerControllerOF playercontrollerof = (PlayerControllerOF)this.mc.playerController;
/* 522 */       return playercontrollerof.isActing();
/*     */     } 
/*     */ 
/*     */     
/* 526 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPlayerUpdate() {
/* 532 */     return this.playerUpdate;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\multiplayer\WorldClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */