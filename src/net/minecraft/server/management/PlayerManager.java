/*     */ package net.minecraft.server.management;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S21PacketChunkData;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.LongHashMap;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.optifine.ChunkPosComparator;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ 
/*     */ public class PlayerManager {
/*  27 */   static final Logger pmLogger = LogManager.getLogger();
/*     */   final WorldServer theWorldServer;
/*  29 */   private final List<EntityPlayerMP> players = Lists.newArrayList();
/*  30 */   final LongHashMap<PlayerInstance> playerInstances = new LongHashMap();
/*  31 */   final List<PlayerInstance> playerInstancesToUpdate = Lists.newArrayList();
/*  32 */   final List<PlayerInstance> playerInstanceList = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */   
/*     */   private int playerViewRadius;
/*     */ 
/*     */ 
/*     */   
/*     */   private long previousTotalWorldTime;
/*     */ 
/*     */   
/*  43 */   private final int[][] xzDirectionsConst = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
/*  44 */   private final Map<EntityPlayerMP, Set<ChunkCoordIntPair>> mapPlayerPendingEntries = new HashMap<>();
/*     */ 
/*     */   
/*     */   public PlayerManager(WorldServer serverWorld) {
/*  48 */     this.theWorldServer = serverWorld;
/*  49 */     setPlayerViewRadius(serverWorld.getMinecraftServer().getConfigurationManager().getViewDistance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldServer getWorldServer() {
/*  57 */     return this.theWorldServer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updatePlayerInstances() {
/*  65 */     Set<Map.Entry<EntityPlayerMP, Set<ChunkCoordIntPair>>> set = this.mapPlayerPendingEntries.entrySet();
/*  66 */     Iterator<Map.Entry<EntityPlayerMP, Set<ChunkCoordIntPair>>> iterator = set.iterator();
/*     */     
/*  68 */     while (iterator.hasNext()) {
/*     */       
/*  70 */       Map.Entry<EntityPlayerMP, Set<ChunkCoordIntPair>> entry = iterator.next();
/*  71 */       Set<ChunkCoordIntPair> set1 = entry.getValue();
/*     */       
/*  73 */       if (!set1.isEmpty()) {
/*     */         
/*  75 */         EntityPlayerMP entityplayermp = entry.getKey();
/*     */         
/*  77 */         if (entityplayermp.worldObj != this.theWorldServer) {
/*     */           
/*  79 */           iterator.remove();
/*     */           
/*     */           continue;
/*     */         } 
/*  83 */         int i = this.playerViewRadius / 3 + 1;
/*     */         
/*  85 */         if (!Config.isLazyChunkLoading())
/*     */         {
/*  87 */           i = (this.playerViewRadius << 1) + 1;
/*     */         }
/*     */         
/*  90 */         for (ChunkCoordIntPair chunkcoordintpair : getNearest(set1, entityplayermp, i)) {
/*     */           
/*  92 */           PlayerInstance playermanager$playerinstance = getPlayerInstance(chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPos, true);
/*  93 */           playermanager$playerinstance.addPlayer(entityplayermp);
/*  94 */           set1.remove(chunkcoordintpair);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 100 */     long j = this.theWorldServer.getTotalWorldTime();
/*     */     
/* 102 */     if (j - this.previousTotalWorldTime > 8000L) {
/*     */       
/* 104 */       this.previousTotalWorldTime = j;
/*     */       
/* 106 */       for (int k = 0; k < this.playerInstanceList.size(); k++)
/*     */       {
/* 108 */         PlayerInstance playermanager$playerinstance1 = this.playerInstanceList.get(k);
/* 109 */         playermanager$playerinstance1.onUpdate();
/* 110 */         playermanager$playerinstance1.processChunk();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 115 */       for (int l = 0; l < this.playerInstancesToUpdate.size(); l++) {
/*     */         
/* 117 */         PlayerInstance playermanager$playerinstance2 = this.playerInstancesToUpdate.get(l);
/* 118 */         playermanager$playerinstance2.onUpdate();
/*     */       } 
/*     */     } 
/*     */     
/* 122 */     this.playerInstancesToUpdate.clear();
/*     */     
/* 124 */     if (this.players.isEmpty()) {
/*     */       
/* 126 */       WorldProvider worldprovider = this.theWorldServer.provider;
/*     */       
/* 128 */       if (!worldprovider.canRespawnHere())
/*     */       {
/* 130 */         this.theWorldServer.theChunkProviderServer.unloadAllChunks();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPlayerInstance(int chunkX, int chunkZ) {
/* 137 */     long i = chunkX + 2147483647L | chunkZ + 2147483647L << 32L;
/* 138 */     return (this.playerInstances.getValueByKey(i) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PlayerInstance getPlayerInstance(int chunkX, int chunkZ, boolean createIfAbsent) {
/* 146 */     long i = chunkX + 2147483647L | chunkZ + 2147483647L << 32L;
/* 147 */     PlayerInstance playermanager$playerinstance = (PlayerInstance)this.playerInstances.getValueByKey(i);
/*     */     
/* 149 */     if (playermanager$playerinstance == null && createIfAbsent) {
/*     */       
/* 151 */       playermanager$playerinstance = new PlayerInstance(chunkX, chunkZ);
/* 152 */       this.playerInstances.add(i, playermanager$playerinstance);
/* 153 */       this.playerInstanceList.add(playermanager$playerinstance);
/*     */     } 
/*     */     
/* 156 */     return playermanager$playerinstance;
/*     */   }
/*     */ 
/*     */   
/*     */   public void markBlockForUpdate(BlockPos pos) {
/* 161 */     int i = pos.getX() >> 4;
/* 162 */     int j = pos.getZ() >> 4;
/* 163 */     PlayerInstance playermanager$playerinstance = getPlayerInstance(i, j, false);
/*     */     
/* 165 */     if (playermanager$playerinstance != null)
/*     */     {
/* 167 */       playermanager$playerinstance.flagChunkForUpdate(pos.getX() & 0xF, pos.getY(), pos.getZ() & 0xF);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPlayer(EntityPlayerMP player) {
/* 176 */     int i = (int)player.posX >> 4;
/* 177 */     int j = (int)player.posZ >> 4;
/* 178 */     player.managedPosX = player.posX;
/* 179 */     player.managedPosZ = player.posZ;
/* 180 */     int k = Math.min(this.playerViewRadius, 8);
/* 181 */     int l = i - k;
/* 182 */     int i1 = i + k;
/* 183 */     int j1 = j - k;
/* 184 */     int k1 = j + k;
/* 185 */     Set<ChunkCoordIntPair> set = getPendingEntriesSafe(player);
/*     */     
/* 187 */     for (int l1 = i - this.playerViewRadius; l1 <= i + this.playerViewRadius; l1++) {
/*     */       
/* 189 */       for (int i2 = j - this.playerViewRadius; i2 <= j + this.playerViewRadius; i2++) {
/*     */         
/* 191 */         if (l1 >= l && l1 <= i1 && i2 >= j1 && i2 <= k1) {
/*     */           
/* 193 */           getPlayerInstance(l1, i2, true).addPlayer(player);
/*     */         }
/*     */         else {
/*     */           
/* 197 */           set.add(new ChunkCoordIntPair(l1, i2));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 202 */     this.players.add(player);
/* 203 */     filterChunkLoadQueue(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void filterChunkLoadQueue(EntityPlayerMP player) {
/* 211 */     List<ChunkCoordIntPair> list = Lists.newArrayList(player.loadedChunks);
/* 212 */     int i = 0;
/* 213 */     int j = this.playerViewRadius;
/* 214 */     int k = (int)player.posX >> 4;
/* 215 */     int l = (int)player.posZ >> 4;
/* 216 */     int i1 = 0;
/* 217 */     int j1 = 0;
/* 218 */     ChunkCoordIntPair chunkcoordintpair = (getPlayerInstance(k, l, true)).chunkCoords;
/* 219 */     player.loadedChunks.clear();
/*     */     
/* 221 */     if (list.contains(chunkcoordintpair))
/*     */     {
/* 223 */       player.loadedChunks.add(chunkcoordintpair);
/*     */     }
/*     */     
/* 226 */     for (int k1 = 1; k1 <= j << 1; k1++) {
/*     */       
/* 228 */       for (int l1 = 0; l1 < 2; l1++) {
/*     */         
/* 230 */         int[] aint = this.xzDirectionsConst[i++ % 4];
/*     */         
/* 232 */         for (int i2 = 0; i2 < k1; i2++) {
/*     */           
/* 234 */           i1 += aint[0];
/* 235 */           j1 += aint[1];
/* 236 */           chunkcoordintpair = (getPlayerInstance(k + i1, l + j1, true)).chunkCoords;
/*     */           
/* 238 */           if (list.contains(chunkcoordintpair))
/*     */           {
/* 240 */             player.loadedChunks.add(chunkcoordintpair);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 246 */     i %= 4;
/*     */     
/* 248 */     for (int j2 = 0; j2 < j << 1; j2++) {
/*     */       
/* 250 */       i1 += this.xzDirectionsConst[i][0];
/* 251 */       j1 += this.xzDirectionsConst[i][1];
/* 252 */       chunkcoordintpair = (getPlayerInstance(k + i1, l + j1, true)).chunkCoords;
/*     */       
/* 254 */       if (list.contains(chunkcoordintpair))
/*     */       {
/* 256 */         player.loadedChunks.add(chunkcoordintpair);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePlayer(EntityPlayerMP player) {
/* 266 */     this.mapPlayerPendingEntries.remove(player);
/* 267 */     int i = (int)player.managedPosX >> 4;
/* 268 */     int j = (int)player.managedPosZ >> 4;
/*     */     
/* 270 */     for (int k = i - this.playerViewRadius; k <= i + this.playerViewRadius; k++) {
/*     */       
/* 272 */       for (int l = j - this.playerViewRadius; l <= j + this.playerViewRadius; l++) {
/*     */         
/* 274 */         PlayerInstance playermanager$playerinstance = getPlayerInstance(k, l, false);
/*     */         
/* 276 */         if (playermanager$playerinstance != null)
/*     */         {
/* 278 */           playermanager$playerinstance.removePlayer(player);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 283 */     this.players.remove(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean overlaps(int x1, int z1, int x2, int z2, int radius) {
/* 292 */     int i = x1 - x2;
/* 293 */     int j = z1 - z2;
/* 294 */     return (i >= -radius && i <= radius) ? ((j >= -radius && j <= radius)) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMountedMovingPlayer(EntityPlayerMP player) {
/* 302 */     int i = (int)player.posX >> 4;
/* 303 */     int j = (int)player.posZ >> 4;
/* 304 */     double d0 = player.managedPosX - player.posX;
/* 305 */     double d1 = player.managedPosZ - player.posZ;
/* 306 */     double d2 = d0 * d0 + d1 * d1;
/*     */     
/* 308 */     if (d2 >= 64.0D) {
/*     */       
/* 310 */       int k = (int)player.managedPosX >> 4;
/* 311 */       int l = (int)player.managedPosZ >> 4;
/* 312 */       int i1 = this.playerViewRadius;
/* 313 */       int j1 = i - k;
/* 314 */       int k1 = j - l;
/*     */       
/* 316 */       if (j1 != 0 || k1 != 0) {
/*     */         
/* 318 */         Set<ChunkCoordIntPair> set = getPendingEntriesSafe(player);
/*     */         
/* 320 */         for (int l1 = i - i1; l1 <= i + i1; l1++) {
/*     */           
/* 322 */           for (int i2 = j - i1; i2 <= j + i1; i2++) {
/*     */             
/* 324 */             if (!overlaps(l1, i2, k, l, i1))
/*     */             {
/* 326 */               if (Config.isLazyChunkLoading()) {
/*     */                 
/* 328 */                 set.add(new ChunkCoordIntPair(l1, i2));
/*     */               }
/*     */               else {
/*     */                 
/* 332 */                 getPlayerInstance(l1, i2, true).addPlayer(player);
/*     */               } 
/*     */             }
/*     */             
/* 336 */             if (!overlaps(l1 - j1, i2 - k1, i, j, i1)) {
/*     */               
/* 338 */               set.remove(new ChunkCoordIntPair(l1 - j1, i2 - k1));
/* 339 */               PlayerInstance playermanager$playerinstance = getPlayerInstance(l1 - j1, i2 - k1, false);
/*     */               
/* 341 */               if (playermanager$playerinstance != null)
/*     */               {
/* 343 */                 playermanager$playerinstance.removePlayer(player);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 349 */         filterChunkLoadQueue(player);
/* 350 */         player.managedPosX = player.posX;
/* 351 */         player.managedPosZ = player.posZ;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPlayerWatchingChunk(EntityPlayerMP player, int chunkX, int chunkZ) {
/* 358 */     PlayerInstance playermanager$playerinstance = getPlayerInstance(chunkX, chunkZ, false);
/* 359 */     return (playermanager$playerinstance != null && playermanager$playerinstance.playersWatchingChunk.contains(player) && !player.loadedChunks.contains(playermanager$playerinstance.chunkCoords));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlayerViewRadius(int radius) {
/* 364 */     radius = MathHelper.clamp_int(radius, 3, 64);
/*     */     
/* 366 */     if (radius != this.playerViewRadius) {
/*     */       
/* 368 */       int i = radius - this.playerViewRadius;
/*     */       
/* 370 */       for (EntityPlayerMP entityplayermp : Lists.newArrayList(this.players)) {
/*     */         
/* 372 */         int j = (int)entityplayermp.posX >> 4;
/* 373 */         int k = (int)entityplayermp.posZ >> 4;
/* 374 */         Set<ChunkCoordIntPair> set = getPendingEntriesSafe(entityplayermp);
/*     */         
/* 376 */         if (i > 0) {
/*     */           
/* 378 */           for (int j1 = j - radius; j1 <= j + radius; j1++) {
/*     */             
/* 380 */             for (int k1 = k - radius; k1 <= k + radius; k1++) {
/*     */               
/* 382 */               if (Config.isLazyChunkLoading()) {
/*     */                 
/* 384 */                 set.add(new ChunkCoordIntPair(j1, k1));
/*     */               }
/*     */               else {
/*     */                 
/* 388 */                 PlayerInstance playermanager$playerinstance1 = getPlayerInstance(j1, k1, true);
/*     */                 
/* 390 */                 if (!playermanager$playerinstance1.playersWatchingChunk.contains(entityplayermp))
/*     */                 {
/* 392 */                   playermanager$playerinstance1.addPlayer(entityplayermp);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/* 400 */         for (int l = j - this.playerViewRadius; l <= j + this.playerViewRadius; l++) {
/*     */           
/* 402 */           for (int i1 = k - this.playerViewRadius; i1 <= k + this.playerViewRadius; i1++) {
/*     */             
/* 404 */             if (!overlaps(l, i1, j, k, radius)) {
/*     */               
/* 406 */               set.remove(new ChunkCoordIntPair(l, i1));
/* 407 */               PlayerInstance playermanager$playerinstance = getPlayerInstance(l, i1, true);
/*     */               
/* 409 */               if (playermanager$playerinstance != null)
/*     */               {
/* 411 */                 playermanager$playerinstance.removePlayer(entityplayermp);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 419 */       this.playerViewRadius = radius;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getFurthestViewableBlock(int distance) {
/* 428 */     return (distance << 4) - 16;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private PriorityQueue<ChunkCoordIntPair> getNearest(Set<ChunkCoordIntPair> p_getNearest_1_, EntityPlayerMP p_getNearest_2_, int p_getNearest_3_) {
/*     */     float f;
/* 435 */     for (f = p_getNearest_2_.rotationYaw + 90.0F; f <= -180.0F; f += 360.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 440 */     while (f > 180.0F)
/*     */     {
/* 442 */       f -= 360.0F;
/*     */     }
/*     */     
/* 445 */     double d0 = f * 0.017453292519943295D;
/* 446 */     double d1 = p_getNearest_2_.rotationPitch;
/* 447 */     double d2 = d1 * 0.017453292519943295D;
/* 448 */     ChunkPosComparator chunkposcomparator = new ChunkPosComparator(p_getNearest_2_.chunkCoordX, p_getNearest_2_.chunkCoordZ, d0, d2);
/* 449 */     Comparator<ChunkCoordIntPair> comparator = Collections.reverseOrder((Comparator<ChunkCoordIntPair>)chunkposcomparator);
/* 450 */     PriorityQueue<ChunkCoordIntPair> priorityqueue = new PriorityQueue<>(p_getNearest_3_, comparator);
/*     */     
/* 452 */     for (ChunkCoordIntPair chunkcoordintpair : p_getNearest_1_) {
/*     */       
/* 454 */       if (priorityqueue.size() < p_getNearest_3_) {
/*     */         
/* 456 */         priorityqueue.add(chunkcoordintpair);
/*     */         
/*     */         continue;
/*     */       } 
/* 460 */       ChunkCoordIntPair chunkcoordintpair1 = priorityqueue.peek();
/*     */       
/* 462 */       if (chunkposcomparator.compare(chunkcoordintpair, chunkcoordintpair1) < 0) {
/*     */         
/* 464 */         priorityqueue.remove();
/* 465 */         priorityqueue.add(chunkcoordintpair);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 470 */     return priorityqueue;
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<ChunkCoordIntPair> getPendingEntriesSafe(EntityPlayerMP p_getPendingEntriesSafe_1_) {
/* 475 */     Set<ChunkCoordIntPair> set = this.mapPlayerPendingEntries.get(p_getPendingEntriesSafe_1_);
/*     */     
/* 477 */     if (set != null)
/*     */     {
/* 479 */       return set;
/*     */     }
/*     */ 
/*     */     
/* 483 */     int i = Math.min(this.playerViewRadius, 8);
/* 484 */     int j = (this.playerViewRadius << 1) + 1;
/* 485 */     int k = (i << 1) + 1;
/* 486 */     int l = j * j - k * k;
/* 487 */     l = Math.max(l, 16);
/* 488 */     HashSet<ChunkCoordIntPair> hashset = new HashSet(l);
/* 489 */     this.mapPlayerPendingEntries.put(p_getPendingEntriesSafe_1_, hashset);
/* 490 */     return hashset;
/*     */   }
/*     */ 
/*     */   
/*     */   class PlayerInstance
/*     */   {
/* 496 */     final List<EntityPlayerMP> playersWatchingChunk = Lists.newArrayList();
/*     */     final ChunkCoordIntPair chunkCoords;
/* 498 */     private final short[] locationOfBlockChange = new short[64];
/*     */     
/*     */     private int numBlocksToUpdate;
/*     */     private int flagsYAreasToUpdate;
/*     */     private long previousWorldTime;
/*     */     
/*     */     public PlayerInstance(int chunkX, int chunkZ) {
/* 505 */       this.chunkCoords = new ChunkCoordIntPair(chunkX, chunkZ);
/* 506 */       (PlayerManager.this.getWorldServer()).theChunkProviderServer.loadChunk(chunkX, chunkZ);
/*     */     }
/*     */ 
/*     */     
/*     */     public void addPlayer(EntityPlayerMP player) {
/* 511 */       if (this.playersWatchingChunk.contains(player)) {
/*     */         
/* 513 */         PlayerManager.pmLogger.debug("Failed to add player. {} already is in chunk {}, {}", new Object[] { player, Integer.valueOf(this.chunkCoords.chunkXPos), Integer.valueOf(this.chunkCoords.chunkZPos) });
/*     */       }
/*     */       else {
/*     */         
/* 517 */         if (this.playersWatchingChunk.isEmpty())
/*     */         {
/* 519 */           this.previousWorldTime = PlayerManager.this.theWorldServer.getTotalWorldTime();
/*     */         }
/*     */         
/* 522 */         this.playersWatchingChunk.add(player);
/* 523 */         player.loadedChunks.add(this.chunkCoords);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void removePlayer(EntityPlayerMP player) {
/* 529 */       if (this.playersWatchingChunk.contains(player)) {
/*     */         
/* 531 */         Chunk chunk = PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos);
/*     */         
/* 533 */         if (chunk.isPopulated())
/*     */         {
/* 535 */           player.playerNetServerHandler.sendPacket((Packet)new S21PacketChunkData(chunk, true, 0));
/*     */         }
/*     */         
/* 538 */         this.playersWatchingChunk.remove(player);
/* 539 */         player.loadedChunks.remove(this.chunkCoords);
/*     */         
/* 541 */         if (this.playersWatchingChunk.isEmpty()) {
/*     */           
/* 543 */           long i = this.chunkCoords.chunkXPos + 2147483647L | this.chunkCoords.chunkZPos + 2147483647L << 32L;
/* 544 */           increaseInhabitedTime(chunk);
/* 545 */           PlayerManager.this.playerInstances.remove(i);
/* 546 */           PlayerManager.this.playerInstanceList.remove(this);
/*     */           
/* 548 */           if (this.numBlocksToUpdate > 0)
/*     */           {
/* 550 */             PlayerManager.this.playerInstancesToUpdate.remove(this);
/*     */           }
/*     */           
/* 553 */           (PlayerManager.this.getWorldServer()).theChunkProviderServer.dropChunk(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void processChunk() {
/* 560 */       increaseInhabitedTime(PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos));
/*     */     }
/*     */ 
/*     */     
/*     */     private void increaseInhabitedTime(Chunk theChunk) {
/* 565 */       theChunk.setInhabitedTime(theChunk.getInhabitedTime() + PlayerManager.this.theWorldServer.getTotalWorldTime() - this.previousWorldTime);
/* 566 */       this.previousWorldTime = PlayerManager.this.theWorldServer.getTotalWorldTime();
/*     */     }
/*     */ 
/*     */     
/*     */     public void flagChunkForUpdate(int x, int y, int z) {
/* 571 */       if (this.numBlocksToUpdate == 0)
/*     */       {
/* 573 */         PlayerManager.this.playerInstancesToUpdate.add(this);
/*     */       }
/*     */       
/* 576 */       this.flagsYAreasToUpdate |= 1 << y >> 4;
/*     */       
/* 578 */       if (this.numBlocksToUpdate < 64) {
/*     */         
/* 580 */         short short1 = (short)(x << 12 | z << 8 | y);
/*     */         
/* 582 */         for (int i = 0; i < this.numBlocksToUpdate; i++) {
/*     */           
/* 584 */           if (this.locationOfBlockChange[i] == short1) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 590 */         this.locationOfBlockChange[this.numBlocksToUpdate++] = short1;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void sendToAllPlayersWatchingChunk(Packet thePacket) {
/* 596 */       for (int i = 0; i < this.playersWatchingChunk.size(); i++) {
/*     */         
/* 598 */         EntityPlayerMP entityplayermp = this.playersWatchingChunk.get(i);
/*     */         
/* 600 */         if (!entityplayermp.loadedChunks.contains(this.chunkCoords))
/*     */         {
/* 602 */           entityplayermp.playerNetServerHandler.sendPacket(thePacket);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onUpdate() {
/* 609 */       if (this.numBlocksToUpdate != 0) {
/*     */         
/* 611 */         if (this.numBlocksToUpdate == 1) {
/*     */           
/* 613 */           int k1 = (this.locationOfBlockChange[0] >> 12 & 0xF) + (this.chunkCoords.chunkXPos << 4);
/* 614 */           int i2 = this.locationOfBlockChange[0] & 0xFF;
/* 615 */           int k2 = (this.locationOfBlockChange[0] >> 8 & 0xF) + (this.chunkCoords.chunkZPos << 4);
/* 616 */           BlockPos blockpos = new BlockPos(k1, i2, k2);
/* 617 */           sendToAllPlayersWatchingChunk((Packet)new S23PacketBlockChange((World)PlayerManager.this.theWorldServer, blockpos));
/*     */           
/* 619 */           if (PlayerManager.this.theWorldServer.getBlockState(blockpos).getBlock().hasTileEntity())
/*     */           {
/* 621 */             sendTileToAllPlayersWatchingChunk(PlayerManager.this.theWorldServer.getTileEntity(blockpos));
/*     */           }
/*     */         }
/* 624 */         else if (this.numBlocksToUpdate != 64) {
/*     */           
/* 626 */           sendToAllPlayersWatchingChunk((Packet)new S22PacketMultiBlockChange(this.numBlocksToUpdate, this.locationOfBlockChange, PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos)));
/*     */           
/* 628 */           for (int j1 = 0; j1 < this.numBlocksToUpdate; j1++)
/*     */           {
/* 630 */             int l1 = (this.locationOfBlockChange[j1] >> 12 & 0xF) + (this.chunkCoords.chunkXPos << 4);
/* 631 */             int j2 = this.locationOfBlockChange[j1] & 0xFF;
/* 632 */             int l2 = (this.locationOfBlockChange[j1] >> 8 & 0xF) + (this.chunkCoords.chunkZPos << 4);
/* 633 */             BlockPos blockpos1 = new BlockPos(l1, j2, l2);
/*     */             
/* 635 */             if (PlayerManager.this.theWorldServer.getBlockState(blockpos1).getBlock().hasTileEntity())
/*     */             {
/* 637 */               sendTileToAllPlayersWatchingChunk(PlayerManager.this.theWorldServer.getTileEntity(blockpos1));
/*     */             }
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 643 */           int i = this.chunkCoords.chunkXPos << 4;
/* 644 */           int j = this.chunkCoords.chunkZPos << 4;
/* 645 */           sendToAllPlayersWatchingChunk((Packet)new S21PacketChunkData(PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos), false, this.flagsYAreasToUpdate));
/*     */           
/* 647 */           for (int k = 0; k < 16; k++) {
/*     */             
/* 649 */             if ((this.flagsYAreasToUpdate & 1 << k) != 0) {
/*     */               
/* 651 */               int l = k << 4;
/* 652 */               List<TileEntity> list = PlayerManager.this.theWorldServer.getTileEntitiesIn(i, l, j, i + 16, l + 16, j + 16);
/*     */               
/* 654 */               for (int i1 = 0; i1 < list.size(); i1++)
/*     */               {
/* 656 */                 sendTileToAllPlayersWatchingChunk(list.get(i1));
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 662 */         this.numBlocksToUpdate = 0;
/* 663 */         this.flagsYAreasToUpdate = 0;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void sendTileToAllPlayersWatchingChunk(TileEntity theTileEntity) {
/* 669 */       if (theTileEntity != null) {
/*     */         
/* 671 */         Packet packet = theTileEntity.getDescriptionPacket();
/*     */         
/* 673 */         if (packet != null)
/*     */         {
/* 675 */           sendToAllPlayersWatchingChunk(packet);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\server\management\PlayerManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */