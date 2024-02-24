/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.gen.MapGenBase;
/*     */ 
/*     */ public abstract class MapGenStructure
/*     */   extends MapGenBase {
/*     */   private MapGenStructureData structureData;
/*  24 */   protected Map<Long, StructureStart> structureMap = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getStructureName();
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void recursiveGenerate(World worldIn, final int chunkX, final int chunkZ, int p_180701_4_, int p_180701_5_, ChunkPrimer chunkPrimerIn) {
/*  33 */     initializeStructureData(worldIn);
/*     */     
/*  35 */     if (!this.structureMap.containsKey(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ)))) {
/*     */       
/*  37 */       this.rand.nextInt();
/*     */ 
/*     */       
/*     */       try {
/*  41 */         if (canSpawnStructureAtCoords(chunkX, chunkZ))
/*     */         {
/*  43 */           StructureStart structurestart = getStructureStart(chunkX, chunkZ);
/*  44 */           this.structureMap.put(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ)), structurestart);
/*  45 */           setStructureStart(chunkX, chunkZ, structurestart);
/*     */         }
/*     */       
/*  48 */       } catch (Throwable throwable) {
/*     */         
/*  50 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception preparing structure feature");
/*  51 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Feature being prepared");
/*  52 */         crashreportcategory.addCrashSectionCallable("Is feature chunk", new Callable<String>()
/*     */             {
/*     */               public String call() {
/*  55 */                 return MapGenStructure.this.canSpawnStructureAtCoords(chunkX, chunkZ) ? "True" : "False";
/*     */               }
/*     */             });
/*  58 */         crashreportcategory.addCrashSection("Chunk location", String.format("%d,%d", new Object[] { Integer.valueOf(chunkX), Integer.valueOf(chunkZ) }));
/*  59 */         crashreportcategory.addCrashSectionCallable("Chunk pos hash", new Callable<String>()
/*     */             {
/*     */               public String call() {
/*  62 */                 return String.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ));
/*     */               }
/*     */             });
/*  65 */         crashreportcategory.addCrashSectionCallable("Structure type", new Callable<String>()
/*     */             {
/*     */               public String call() {
/*  68 */                 return MapGenStructure.this.getClass().getCanonicalName();
/*     */               }
/*     */             });
/*  71 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generateStructure(World worldIn, Random randomIn, ChunkCoordIntPair chunkCoord) {
/*  78 */     initializeStructureData(worldIn);
/*  79 */     int i = (chunkCoord.chunkXPos << 4) + 8;
/*  80 */     int j = (chunkCoord.chunkZPos << 4) + 8;
/*  81 */     boolean flag = false;
/*     */     
/*  83 */     for (StructureStart structurestart : this.structureMap.values()) {
/*     */       
/*  85 */       if (structurestart.isSizeableStructure() && structurestart.func_175788_a(chunkCoord) && structurestart.getBoundingBox().intersectsWith(i, j, i + 15, j + 15)) {
/*     */         
/*  87 */         structurestart.generateStructure(worldIn, randomIn, new StructureBoundingBox(i, j, i + 15, j + 15));
/*  88 */         structurestart.func_175787_b(chunkCoord);
/*  89 */         flag = true;
/*  90 */         setStructureStart(structurestart.getChunkPosX(), structurestart.getChunkPosZ(), structurestart);
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_175795_b(BlockPos pos) {
/*  99 */     initializeStructureData(this.worldObj);
/* 100 */     return (func_175797_c(pos) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StructureStart func_175797_c(BlockPos pos) {
/* 107 */     for (StructureStart structurestart : this.structureMap.values()) {
/*     */       
/* 109 */       if (structurestart.isSizeableStructure() && structurestart.getBoundingBox().isVecInside((Vec3i)pos)) {
/*     */         
/* 111 */         Iterator<StructureComponent> iterator = structurestart.getComponents().iterator();
/*     */ 
/*     */ 
/*     */         
/* 115 */         while (iterator.hasNext()) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 120 */           StructureComponent structurecomponent = iterator.next();
/*     */           
/* 122 */           if (structurecomponent.getBoundingBox().isVecInside((Vec3i)pos))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 128 */             return structurestart; } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 132 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPositionInStructure(World worldIn, BlockPos pos) {
/* 137 */     initializeStructureData(worldIn);
/*     */     
/* 139 */     for (StructureStart structurestart : this.structureMap.values()) {
/*     */       
/* 141 */       if (structurestart.isSizeableStructure() && structurestart.getBoundingBox().isVecInside((Vec3i)pos))
/*     */       {
/* 143 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 147 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getClosestStrongholdPos(World worldIn, BlockPos pos) {
/* 152 */     this.worldObj = worldIn;
/* 153 */     initializeStructureData(worldIn);
/* 154 */     this.rand.setSeed(worldIn.getSeed());
/* 155 */     long i = this.rand.nextLong();
/* 156 */     long j = this.rand.nextLong();
/* 157 */     long k = (pos.getX() >> 4) * i;
/* 158 */     long l = (pos.getZ() >> 4) * j;
/* 159 */     this.rand.setSeed(k ^ l ^ worldIn.getSeed());
/* 160 */     recursiveGenerate(worldIn, pos.getX() >> 4, pos.getZ() >> 4, 0, 0, (ChunkPrimer)null);
/* 161 */     double d0 = Double.MAX_VALUE;
/* 162 */     BlockPos blockpos = null;
/*     */     
/* 164 */     for (StructureStart structurestart : this.structureMap.values()) {
/*     */       
/* 166 */       if (structurestart.isSizeableStructure()) {
/*     */         
/* 168 */         StructureComponent structurecomponent = structurestart.getComponents().get(0);
/* 169 */         BlockPos blockpos1 = structurecomponent.getBoundingBoxCenter();
/* 170 */         double d1 = blockpos1.distanceSq((Vec3i)pos);
/*     */         
/* 172 */         if (d1 < d0) {
/*     */           
/* 174 */           d0 = d1;
/* 175 */           blockpos = blockpos1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 180 */     if (blockpos != null)
/*     */     {
/* 182 */       return blockpos;
/*     */     }
/*     */ 
/*     */     
/* 186 */     List<BlockPos> list = getCoordList();
/*     */     
/* 188 */     if (list != null) {
/*     */       
/* 190 */       BlockPos blockpos2 = null;
/*     */       
/* 192 */       for (BlockPos blockpos3 : list) {
/*     */         
/* 194 */         double d2 = blockpos3.distanceSq((Vec3i)pos);
/*     */         
/* 196 */         if (d2 < d0) {
/*     */           
/* 198 */           d0 = d2;
/* 199 */           blockpos2 = blockpos3;
/*     */         } 
/*     */       } 
/*     */       
/* 203 */       return blockpos2;
/*     */     } 
/*     */ 
/*     */     
/* 207 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<BlockPos> getCoordList() {
/* 214 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void initializeStructureData(World worldIn) {
/* 219 */     if (this.structureData == null) {
/*     */       
/* 221 */       this.structureData = (MapGenStructureData)worldIn.loadItemData(MapGenStructureData.class, getStructureName());
/*     */       
/* 223 */       if (this.structureData == null) {
/*     */         
/* 225 */         this.structureData = new MapGenStructureData(getStructureName());
/* 226 */         worldIn.setItemData(getStructureName(), this.structureData);
/*     */       }
/*     */       else {
/*     */         
/* 230 */         NBTTagCompound nbttagcompound = this.structureData.getTagCompound();
/*     */         
/* 232 */         for (String s : nbttagcompound.getKeySet()) {
/*     */           
/* 234 */           NBTBase nbtbase = nbttagcompound.getTag(s);
/*     */           
/* 236 */           if (nbtbase.getId() == 10) {
/*     */             
/* 238 */             NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbtbase;
/*     */             
/* 240 */             if (nbttagcompound1.hasKey("ChunkX") && nbttagcompound1.hasKey("ChunkZ")) {
/*     */               
/* 242 */               int i = nbttagcompound1.getInteger("ChunkX");
/* 243 */               int j = nbttagcompound1.getInteger("ChunkZ");
/* 244 */               StructureStart structurestart = MapGenStructureIO.getStructureStart(nbttagcompound1, worldIn);
/*     */               
/* 246 */               if (structurestart != null)
/*     */               {
/* 248 */                 this.structureMap.put(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(i, j)), structurestart);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setStructureStart(int chunkX, int chunkZ, StructureStart start) {
/* 259 */     this.structureData.writeInstance(start.writeStructureComponentsToNBT(chunkX, chunkZ), chunkX, chunkZ);
/* 260 */     this.structureData.markDirty();
/*     */   }
/*     */   
/*     */   protected abstract boolean canSpawnStructureAtCoords(int paramInt1, int paramInt2);
/*     */   
/*     */   protected abstract StructureStart getStructureStart(int paramInt1, int paramInt2);
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\structure\MapGenStructure.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */