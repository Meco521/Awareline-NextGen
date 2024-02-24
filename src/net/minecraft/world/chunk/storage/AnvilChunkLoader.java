/*     */ package net.minecraft.world.chunk.storage;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.MinecraftException;
/*     */ import net.minecraft.world.NextTickListEntry;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.NibbleArray;
/*     */ import net.minecraft.world.storage.IThreadedFileIO;
/*     */ import net.minecraft.world.storage.ThreadedFileIOBase;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class AnvilChunkLoader
/*     */   implements IChunkLoader, IThreadedFileIO {
/*  35 */   private static final Logger logger = LogManager.getLogger();
/*  36 */   private final Map<ChunkCoordIntPair, NBTTagCompound> chunksToRemove = new ConcurrentHashMap<>();
/*  37 */   private final Set<ChunkCoordIntPair> pendingAnvilChunksCoordinates = Collections.newSetFromMap(new ConcurrentHashMap<>());
/*     */   
/*     */   private final File chunkSaveLocation;
/*     */   
/*     */   private boolean field_183014_e = false;
/*     */ 
/*     */   
/*     */   public AnvilChunkLoader(File chunkSaveLocationIn) {
/*  45 */     this.chunkSaveLocation = chunkSaveLocationIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chunk loadChunk(World worldIn, int x, int z) throws IOException {
/*  53 */     ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(x, z);
/*  54 */     NBTTagCompound nbttagcompound = this.chunksToRemove.get(chunkcoordintpair);
/*     */     
/*  56 */     if (nbttagcompound == null) {
/*     */       
/*  58 */       DataInputStream datainputstream = RegionFileCache.getChunkInputStream(this.chunkSaveLocation, x, z);
/*     */       
/*  60 */       if (datainputstream == null)
/*     */       {
/*  62 */         return null;
/*     */       }
/*     */       
/*  65 */       nbttagcompound = CompressedStreamTools.read(datainputstream);
/*     */     } 
/*     */     
/*  68 */     return checkedReadChunkFromNBT(worldIn, x, z, nbttagcompound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Chunk checkedReadChunkFromNBT(World worldIn, int x, int z, NBTTagCompound p_75822_4_) {
/*  76 */     if (!p_75822_4_.hasKey("Level", 10)) {
/*     */       
/*  78 */       logger.error("Chunk file at " + x + "," + z + " is missing level data, skipping");
/*  79 */       return null;
/*     */     } 
/*     */ 
/*     */     
/*  83 */     NBTTagCompound nbttagcompound = p_75822_4_.getCompoundTag("Level");
/*     */     
/*  85 */     if (!nbttagcompound.hasKey("Sections", 9)) {
/*     */       
/*  87 */       logger.error("Chunk file at " + x + "," + z + " is missing block data, skipping");
/*  88 */       return null;
/*     */     } 
/*     */ 
/*     */     
/*  92 */     Chunk chunk = readChunkFromNBT(worldIn, nbttagcompound);
/*     */     
/*  94 */     if (!chunk.isAtLocation(x, z)) {
/*     */       
/*  96 */       logger.error("Chunk file at " + x + "," + z + " is in the wrong location; relocating. (Expected " + x + ", " + z + ", got " + chunk.xPosition + ", " + chunk.zPosition + ")");
/*  97 */       nbttagcompound.setInteger("xPos", x);
/*  98 */       nbttagcompound.setInteger("zPos", z);
/*  99 */       chunk = readChunkFromNBT(worldIn, nbttagcompound);
/*     */     } 
/*     */     
/* 102 */     return chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveChunk(World worldIn, Chunk chunkIn) throws MinecraftException {
/* 108 */     worldIn.checkSessionLock();
/*     */ 
/*     */     
/*     */     try {
/* 112 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 113 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 114 */       nbttagcompound.setTag("Level", (NBTBase)nbttagcompound1);
/* 115 */       writeChunkToNBT(chunkIn, worldIn, nbttagcompound1);
/* 116 */       addChunkToPending(chunkIn.getChunkCoordIntPair(), nbttagcompound);
/*     */     }
/* 118 */     catch (Exception exception) {
/*     */       
/* 120 */       logger.error("Failed to save chunk", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addChunkToPending(ChunkCoordIntPair p_75824_1_, NBTTagCompound p_75824_2_) {
/* 126 */     if (!this.pendingAnvilChunksCoordinates.contains(p_75824_1_))
/*     */     {
/* 128 */       this.chunksToRemove.put(p_75824_1_, p_75824_2_);
/*     */     }
/*     */     
/* 131 */     ThreadedFileIOBase.getThreadedIOInstance().queueIO(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean writeNextIO() {
/*     */     boolean lvt_3_1_;
/* 139 */     if (this.chunksToRemove.isEmpty()) {
/*     */       
/* 141 */       if (this.field_183014_e)
/*     */       {
/* 143 */         logger.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", new Object[] { this.chunkSaveLocation.getName() });
/*     */       }
/*     */       
/* 146 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 150 */     ChunkCoordIntPair chunkcoordintpair = this.chunksToRemove.keySet().iterator().next();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 155 */       this.pendingAnvilChunksCoordinates.add(chunkcoordintpair);
/* 156 */       NBTTagCompound nbttagcompound = this.chunksToRemove.remove(chunkcoordintpair);
/*     */       
/* 158 */       if (nbttagcompound != null) {
/*     */         
/*     */         try {
/*     */           
/* 162 */           func_183013_b(chunkcoordintpair, nbttagcompound);
/*     */         }
/* 164 */         catch (Exception exception) {
/*     */           
/* 166 */           logger.error("Failed to save chunk", exception);
/*     */         } 
/*     */       }
/*     */       
/* 170 */       lvt_3_1_ = true;
/*     */     }
/*     */     finally {
/*     */       
/* 174 */       this.pendingAnvilChunksCoordinates.remove(chunkcoordintpair);
/*     */     } 
/*     */     
/* 177 */     return lvt_3_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_183013_b(ChunkCoordIntPair p_183013_1_, NBTTagCompound p_183013_2_) throws IOException {
/* 183 */     DataOutputStream dataoutputstream = RegionFileCache.getChunkOutputStream(this.chunkSaveLocation, p_183013_1_.chunkXPos, p_183013_1_.chunkZPos);
/* 184 */     CompressedStreamTools.write(p_183013_2_, dataoutputstream);
/* 185 */     dataoutputstream.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveExtraChunkData(World worldIn, Chunk chunkIn) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void chunkTick() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveExtraData() {
/*     */     try {
/* 210 */       this.field_183014_e = true;
/*     */ 
/*     */       
/*     */       while (true) {
/* 214 */         if (writeNextIO());
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 222 */       this.field_183014_e = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeChunkToNBT(Chunk chunkIn, World worldIn, NBTTagCompound p_75820_3_) {
/* 232 */     p_75820_3_.setByte("V", (byte)1);
/* 233 */     p_75820_3_.setInteger("xPos", chunkIn.xPosition);
/* 234 */     p_75820_3_.setInteger("zPos", chunkIn.zPosition);
/* 235 */     p_75820_3_.setLong("LastUpdate", worldIn.getTotalWorldTime());
/* 236 */     p_75820_3_.setIntArray("HeightMap", chunkIn.getHeightMap());
/* 237 */     p_75820_3_.setBoolean("TerrainPopulated", chunkIn.isTerrainPopulated());
/* 238 */     p_75820_3_.setBoolean("LightPopulated", chunkIn.isLightPopulated());
/* 239 */     p_75820_3_.setLong("InhabitedTime", chunkIn.getInhabitedTime());
/* 240 */     ExtendedBlockStorage[] aextendedblockstorage = chunkIn.getBlockStorageArray();
/* 241 */     NBTTagList nbttaglist = new NBTTagList();
/* 242 */     boolean flag = !worldIn.provider.getHasNoSky();
/*     */     
/* 244 */     for (ExtendedBlockStorage extendedblockstorage : aextendedblockstorage) {
/*     */       
/* 246 */       if (extendedblockstorage != null) {
/*     */         
/* 248 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 249 */         nbttagcompound.setByte("Y", (byte)(extendedblockstorage.getYLocation() >> 4 & 0xFF));
/* 250 */         byte[] abyte = new byte[(extendedblockstorage.getData()).length];
/* 251 */         NibbleArray nibblearray = new NibbleArray();
/* 252 */         NibbleArray nibblearray1 = null;
/*     */         
/* 254 */         for (int i = 0; i < (extendedblockstorage.getData()).length; i++) {
/*     */           
/* 256 */           char c0 = extendedblockstorage.getData()[i];
/* 257 */           int j = i & 0xF;
/* 258 */           int k = i >> 8 & 0xF;
/* 259 */           int l = i >> 4 & 0xF;
/*     */           
/* 261 */           if (c0 >> 12 != 0) {
/*     */             
/* 263 */             if (nibblearray1 == null)
/*     */             {
/* 265 */               nibblearray1 = new NibbleArray();
/*     */             }
/*     */             
/* 268 */             nibblearray1.set(j, k, l, c0 >> 12);
/*     */           } 
/*     */           
/* 271 */           abyte[i] = (byte)(c0 >> 4 & 0xFF);
/* 272 */           nibblearray.set(j, k, l, c0 & 0xF);
/*     */         } 
/*     */         
/* 275 */         nbttagcompound.setByteArray("Blocks", abyte);
/* 276 */         nbttagcompound.setByteArray("Data", nibblearray.getData());
/*     */         
/* 278 */         if (nibblearray1 != null)
/*     */         {
/* 280 */           nbttagcompound.setByteArray("Add", nibblearray1.getData());
/*     */         }
/*     */         
/* 283 */         nbttagcompound.setByteArray("BlockLight", extendedblockstorage.getBlocklightArray().getData());
/*     */         
/* 285 */         if (flag) {
/*     */           
/* 287 */           nbttagcompound.setByteArray("SkyLight", extendedblockstorage.getSkylightArray().getData());
/*     */         }
/*     */         else {
/*     */           
/* 291 */           nbttagcompound.setByteArray("SkyLight", new byte[(extendedblockstorage.getBlocklightArray().getData()).length]);
/*     */         } 
/*     */         
/* 294 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 298 */     p_75820_3_.setTag("Sections", (NBTBase)nbttaglist);
/* 299 */     p_75820_3_.setByteArray("Biomes", chunkIn.getBiomeArray());
/* 300 */     chunkIn.setHasEntities(false);
/* 301 */     NBTTagList nbttaglist1 = new NBTTagList();
/*     */     
/* 303 */     for (int i1 = 0; i1 < (chunkIn.getEntityLists()).length; i1++) {
/*     */       
/* 305 */       for (Entity entity : chunkIn.getEntityLists()[i1]) {
/*     */         
/* 307 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */         
/* 309 */         if (entity.writeToNBTOptional(nbttagcompound1)) {
/*     */           
/* 311 */           chunkIn.setHasEntities(true);
/* 312 */           nbttaglist1.appendTag((NBTBase)nbttagcompound1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 317 */     p_75820_3_.setTag("Entities", (NBTBase)nbttaglist1);
/* 318 */     NBTTagList nbttaglist2 = new NBTTagList();
/*     */     
/* 320 */     for (TileEntity tileentity : chunkIn.getTileEntityMap().values()) {
/*     */       
/* 322 */       NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/* 323 */       tileentity.writeToNBT(nbttagcompound2);
/* 324 */       nbttaglist2.appendTag((NBTBase)nbttagcompound2);
/*     */     } 
/*     */     
/* 327 */     p_75820_3_.setTag("TileEntities", (NBTBase)nbttaglist2);
/* 328 */     List<NextTickListEntry> list = worldIn.getPendingBlockUpdates(chunkIn, false);
/*     */     
/* 330 */     if (list != null) {
/*     */       
/* 332 */       long j1 = worldIn.getTotalWorldTime();
/* 333 */       NBTTagList nbttaglist3 = new NBTTagList();
/*     */       
/* 335 */       for (NextTickListEntry nextticklistentry : list) {
/*     */         
/* 337 */         NBTTagCompound nbttagcompound3 = new NBTTagCompound();
/* 338 */         ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(nextticklistentry.getBlock());
/* 339 */         nbttagcompound3.setString("i", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 340 */         nbttagcompound3.setInteger("x", nextticklistentry.position.getX());
/* 341 */         nbttagcompound3.setInteger("y", nextticklistentry.position.getY());
/* 342 */         nbttagcompound3.setInteger("z", nextticklistentry.position.getZ());
/* 343 */         nbttagcompound3.setInteger("t", (int)(nextticklistentry.scheduledTime - j1));
/* 344 */         nbttagcompound3.setInteger("p", nextticklistentry.priority);
/* 345 */         nbttaglist3.appendTag((NBTBase)nbttagcompound3);
/*     */       } 
/*     */       
/* 348 */       p_75820_3_.setTag("TileTicks", (NBTBase)nbttaglist3);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Chunk readChunkFromNBT(World worldIn, NBTTagCompound p_75823_2_) {
/* 358 */     int i = p_75823_2_.getInteger("xPos");
/* 359 */     int j = p_75823_2_.getInteger("zPos");
/* 360 */     Chunk chunk = new Chunk(worldIn, i, j);
/* 361 */     chunk.setHeightMap(p_75823_2_.getIntArray("HeightMap"));
/* 362 */     chunk.setTerrainPopulated(p_75823_2_.getBoolean("TerrainPopulated"));
/* 363 */     chunk.setLightPopulated(p_75823_2_.getBoolean("LightPopulated"));
/* 364 */     chunk.setInhabitedTime(p_75823_2_.getLong("InhabitedTime"));
/* 365 */     NBTTagList nbttaglist = p_75823_2_.getTagList("Sections", 10);
/* 366 */     int k = 16;
/* 367 */     ExtendedBlockStorage[] aextendedblockstorage = new ExtendedBlockStorage[k];
/* 368 */     boolean flag = !worldIn.provider.getHasNoSky();
/*     */     
/* 370 */     for (int l = 0; l < nbttaglist.tagCount(); l++) {
/*     */       
/* 372 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(l);
/* 373 */       int i1 = nbttagcompound.getByte("Y");
/* 374 */       ExtendedBlockStorage extendedblockstorage = new ExtendedBlockStorage(i1 << 4, flag);
/* 375 */       byte[] abyte = nbttagcompound.getByteArray("Blocks");
/* 376 */       NibbleArray nibblearray = new NibbleArray(nbttagcompound.getByteArray("Data"));
/* 377 */       NibbleArray nibblearray1 = nbttagcompound.hasKey("Add", 7) ? new NibbleArray(nbttagcompound.getByteArray("Add")) : null;
/* 378 */       char[] achar = new char[abyte.length];
/*     */       
/* 380 */       for (int j1 = 0; j1 < achar.length; j1++) {
/*     */         
/* 382 */         int k1 = j1 & 0xF;
/* 383 */         int l1 = j1 >> 8 & 0xF;
/* 384 */         int i2 = j1 >> 4 & 0xF;
/* 385 */         int j2 = (nibblearray1 != null) ? nibblearray1.get(k1, l1, i2) : 0;
/* 386 */         achar[j1] = (char)(j2 << 12 | (abyte[j1] & 0xFF) << 4 | nibblearray.get(k1, l1, i2));
/*     */       } 
/*     */       
/* 389 */       extendedblockstorage.setData(achar);
/* 390 */       extendedblockstorage.setBlocklightArray(new NibbleArray(nbttagcompound.getByteArray("BlockLight")));
/*     */       
/* 392 */       if (flag)
/*     */       {
/* 394 */         extendedblockstorage.setSkylightArray(new NibbleArray(nbttagcompound.getByteArray("SkyLight")));
/*     */       }
/*     */       
/* 397 */       extendedblockstorage.removeInvalidBlocks();
/* 398 */       aextendedblockstorage[i1] = extendedblockstorage;
/*     */     } 
/*     */     
/* 401 */     chunk.setStorageArrays(aextendedblockstorage);
/*     */     
/* 403 */     if (p_75823_2_.hasKey("Biomes", 7))
/*     */     {
/* 405 */       chunk.setBiomeArray(p_75823_2_.getByteArray("Biomes"));
/*     */     }
/*     */     
/* 408 */     NBTTagList nbttaglist1 = p_75823_2_.getTagList("Entities", 10);
/*     */     
/* 410 */     if (nbttaglist1 != null)
/*     */     {
/* 412 */       for (int k2 = 0; k2 < nbttaglist1.tagCount(); k2++) {
/*     */         
/* 414 */         NBTTagCompound nbttagcompound1 = nbttaglist1.getCompoundTagAt(k2);
/* 415 */         Entity entity = EntityList.createEntityFromNBT(nbttagcompound1, worldIn);
/* 416 */         chunk.setHasEntities(true);
/*     */         
/* 418 */         if (entity != null) {
/*     */           
/* 420 */           chunk.addEntity(entity);
/* 421 */           Entity entity1 = entity;
/*     */           
/* 423 */           for (NBTTagCompound nbttagcompound4 = nbttagcompound1; nbttagcompound4.hasKey("Riding", 10); nbttagcompound4 = nbttagcompound4.getCompoundTag("Riding")) {
/*     */             
/* 425 */             Entity entity2 = EntityList.createEntityFromNBT(nbttagcompound4.getCompoundTag("Riding"), worldIn);
/*     */             
/* 427 */             if (entity2 != null) {
/*     */               
/* 429 */               chunk.addEntity(entity2);
/* 430 */               entity1.mountEntity(entity2);
/*     */             } 
/*     */             
/* 433 */             entity1 = entity2;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 439 */     NBTTagList nbttaglist2 = p_75823_2_.getTagList("TileEntities", 10);
/*     */     
/* 441 */     if (nbttaglist2 != null)
/*     */     {
/* 443 */       for (int l2 = 0; l2 < nbttaglist2.tagCount(); l2++) {
/*     */         
/* 445 */         NBTTagCompound nbttagcompound2 = nbttaglist2.getCompoundTagAt(l2);
/* 446 */         TileEntity tileentity = TileEntity.createAndLoadEntity(nbttagcompound2);
/*     */         
/* 448 */         if (tileentity != null)
/*     */         {
/* 450 */           chunk.addTileEntity(tileentity);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 455 */     if (p_75823_2_.hasKey("TileTicks", 9)) {
/*     */       
/* 457 */       NBTTagList nbttaglist3 = p_75823_2_.getTagList("TileTicks", 10);
/*     */       
/* 459 */       if (nbttaglist3 != null)
/*     */       {
/* 461 */         for (int i3 = 0; i3 < nbttaglist3.tagCount(); i3++) {
/*     */           Block block;
/* 463 */           NBTTagCompound nbttagcompound3 = nbttaglist3.getCompoundTagAt(i3);
/*     */ 
/*     */           
/* 466 */           if (nbttagcompound3.hasKey("i", 8)) {
/*     */             
/* 468 */             block = Block.getBlockFromName(nbttagcompound3.getString("i"));
/*     */           }
/*     */           else {
/*     */             
/* 472 */             block = Block.getBlockById(nbttagcompound3.getInteger("i"));
/*     */           } 
/*     */           
/* 475 */           worldIn.scheduleBlockUpdate(new BlockPos(nbttagcompound3.getInteger("x"), nbttagcompound3.getInteger("y"), nbttagcompound3.getInteger("z")), block, nbttagcompound3.getInteger("t"), nbttagcompound3.getInteger("p"));
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 480 */     return chunk;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\chunk\storage\AnvilChunkLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */