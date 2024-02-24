/*     */ package net.minecraft.world;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.optifine.BlockPosM;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorForge;
/*     */ 
/*     */ public final class SpawnerAnimals {
/*  26 */   private static final int MOB_COUNT_DIV = (int)Math.pow(17.0D, 2.0D);
/*  27 */   private final Set<ChunkCoordIntPair> eligibleChunksForSpawning = Sets.newHashSet();
/*  28 */   private final Map<Class, EntityLiving> mapSampleEntitiesByClass = (Map)new HashMap<>();
/*  29 */   private int lastPlayerChunkX = Integer.MAX_VALUE;
/*  30 */   private int lastPlayerChunkZ = Integer.MAX_VALUE;
/*     */ 
/*     */ 
/*     */   
/*     */   private int countChunkPos;
/*     */ 
/*     */ 
/*     */   
/*     */   public int findChunksForSpawning(WorldServer worldServerIn, boolean spawnHostileMobs, boolean spawnPeacefulMobs, boolean p_77192_4_) {
/*  39 */     if (!spawnHostileMobs && !spawnPeacefulMobs)
/*     */     {
/*  41 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*  45 */     boolean flag = true;
/*  46 */     EntityPlayer entityplayer = null;
/*     */     
/*  48 */     if (worldServerIn.playerEntities.size() == 1) {
/*     */       
/*  50 */       entityplayer = worldServerIn.playerEntities.get(0);
/*     */       
/*  52 */       if (!this.eligibleChunksForSpawning.isEmpty() && entityplayer != null && entityplayer.chunkCoordX == this.lastPlayerChunkX && entityplayer.chunkCoordZ == this.lastPlayerChunkZ)
/*     */       {
/*  54 */         flag = false;
/*     */       }
/*     */     } 
/*     */     
/*  58 */     if (flag) {
/*     */       
/*  60 */       this.eligibleChunksForSpawning.clear();
/*  61 */       int i = 0;
/*     */       
/*  63 */       for (EntityPlayer entityplayer1 : worldServerIn.playerEntities) {
/*     */         
/*  65 */         if (!entityplayer1.isSpectator()) {
/*     */           
/*  67 */           int j = MathHelper.floor_double(entityplayer1.posX / 16.0D);
/*  68 */           int k = MathHelper.floor_double(entityplayer1.posZ / 16.0D);
/*  69 */           int l = 8;
/*     */           
/*  71 */           for (int i1 = -l; i1 <= l; i1++) {
/*     */             
/*  73 */             for (int j1 = -l; j1 <= l; j1++) {
/*     */               
/*  75 */               boolean flag1 = (i1 == -l || i1 == l || j1 == -l || j1 == l);
/*  76 */               ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i1 + j, j1 + k);
/*     */               
/*  78 */               if (!this.eligibleChunksForSpawning.contains(chunkcoordintpair)) {
/*     */                 
/*  80 */                 i++;
/*     */                 
/*  82 */                 if (!flag1 && worldServerIn.getWorldBorder().contains(chunkcoordintpair))
/*     */                 {
/*  84 */                   this.eligibleChunksForSpawning.add(chunkcoordintpair);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  92 */       this.countChunkPos = i;
/*     */       
/*  94 */       if (entityplayer != null) {
/*     */         
/*  96 */         this.lastPlayerChunkX = entityplayer.chunkCoordX;
/*  97 */         this.lastPlayerChunkZ = entityplayer.chunkCoordZ;
/*     */       } 
/*     */     } 
/*     */     
/* 101 */     int j4 = 0;
/* 102 */     BlockPos blockpos2 = worldServerIn.getSpawnPoint();
/* 103 */     BlockPosM blockposm = new BlockPosM(0, 0, 0);
/* 104 */     new BlockPos.MutableBlockPos();
/*     */     
/* 106 */     for (EnumCreatureType enumcreaturetype : EnumCreatureType.values()) {
/*     */       
/* 108 */       if ((!enumcreaturetype.getPeacefulCreature() || spawnPeacefulMobs) && (enumcreaturetype.getPeacefulCreature() || spawnHostileMobs) && (!enumcreaturetype.getAnimal() || p_77192_4_)) {
/*     */         
/* 110 */         int k4 = Reflector.ForgeWorld_countEntities.exists() ? Reflector.callInt(worldServerIn, Reflector.ForgeWorld_countEntities, new Object[] { enumcreaturetype, Boolean.valueOf(true) }) : worldServerIn.countEntities(enumcreaturetype.getCreatureClass());
/* 111 */         int l4 = enumcreaturetype.getMaxNumberOfCreature() * this.countChunkPos / MOB_COUNT_DIV;
/*     */         
/* 113 */         if (k4 <= l4) {
/*     */           
/* 115 */           Collection<ChunkCoordIntPair> collection = this.eligibleChunksForSpawning;
/*     */           
/* 117 */           if (Reflector.ForgeHooksClient.exists()) {
/*     */             
/* 119 */             ArrayList<ChunkCoordIntPair> arraylist = Lists.newArrayList(collection);
/* 120 */             Collections.shuffle(arraylist);
/* 121 */             collection = arraylist;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 126 */           label118: for (ChunkCoordIntPair chunkcoordintpair1 : collection) {
/*     */             
/* 128 */             BlockPosM blockPosM = getRandomChunkPosition(worldServerIn, chunkcoordintpair1.chunkXPos, chunkcoordintpair1.chunkZPos, blockposm);
/* 129 */             int k1 = blockPosM.getX();
/* 130 */             int l1 = blockPosM.getY();
/* 131 */             int i2 = blockPosM.getZ();
/* 132 */             Block block = worldServerIn.getBlockState((BlockPos)blockPosM).getBlock();
/*     */             
/* 134 */             if (!block.isNormalCube()) {
/*     */               
/* 136 */               int j2 = 0;
/*     */               
/* 138 */               for (int k2 = 0; k2 < 3; k2++) {
/*     */                 
/* 140 */                 int l2 = k1;
/* 141 */                 int i3 = l1;
/* 142 */                 int j3 = i2;
/* 143 */                 int k3 = 6;
/* 144 */                 BiomeGenBase.SpawnListEntry biomegenbase$spawnlistentry = null;
/* 145 */                 IEntityLivingData ientitylivingdata = null;
/*     */                 
/* 147 */                 for (int l3 = 0; l3 < 4; l3++) {
/*     */                   
/* 149 */                   l2 += worldServerIn.rand.nextInt(k3) - worldServerIn.rand.nextInt(k3);
/* 150 */                   i3 += worldServerIn.rand.nextInt(1) - worldServerIn.rand.nextInt(1);
/* 151 */                   j3 += worldServerIn.rand.nextInt(k3) - worldServerIn.rand.nextInt(k3);
/* 152 */                   BlockPos blockpos1 = new BlockPos(l2, i3, j3);
/* 153 */                   float f = l2 + 0.5F;
/* 154 */                   float f1 = j3 + 0.5F;
/*     */                   
/* 156 */                   if (!worldServerIn.isAnyPlayerWithinRangeAt(f, i3, f1, 24.0D) && blockpos2.distanceSq(f, i3, f1) >= 576.0D) {
/*     */                     
/* 158 */                     if (biomegenbase$spawnlistentry == null) {
/*     */                       
/* 160 */                       biomegenbase$spawnlistentry = worldServerIn.getSpawnListEntryForTypeAt(enumcreaturetype, blockpos1);
/*     */                       
/* 162 */                       if (biomegenbase$spawnlistentry == null) {
/*     */                         break;
/*     */                       }
/*     */                     } 
/*     */ 
/*     */                     
/* 168 */                     if (worldServerIn.canCreatureTypeSpawnHere(enumcreaturetype, biomegenbase$spawnlistentry, blockpos1) && canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.getPlacementForEntity(biomegenbase$spawnlistentry.entityClass), worldServerIn, blockpos1)) {
/*     */                       EntityLiving entityliving;
/*     */ 
/*     */ 
/*     */                       
/*     */                       try {
/* 174 */                         entityliving = this.mapSampleEntitiesByClass.get(biomegenbase$spawnlistentry.entityClass);
/*     */                         
/* 176 */                         if (entityliving == null)
/*     */                         {
/* 178 */                           entityliving = biomegenbase$spawnlistentry.entityClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldServerIn });
/* 179 */                           this.mapSampleEntitiesByClass.put(biomegenbase$spawnlistentry.entityClass, entityliving);
/*     */                         }
/*     */                       
/* 182 */                       } catch (Exception exception1) {
/*     */                         
/* 184 */                         exception1.printStackTrace();
/* 185 */                         return j4;
/*     */                       } 
/*     */                       
/* 188 */                       entityliving.setLocationAndAngles(f, i3, f1, worldServerIn.rand.nextFloat() * 360.0F, 0.0F);
/* 189 */                       boolean flag2 = Reflector.ForgeEventFactory_canEntitySpawn.exists() ? ReflectorForge.canEntitySpawn(entityliving, worldServerIn, f, i3, f1) : ((entityliving.getCanSpawnHere() && entityliving.isNotColliding()));
/*     */                       
/* 191 */                       if (flag2) {
/*     */                         
/* 193 */                         this.mapSampleEntitiesByClass.remove(biomegenbase$spawnlistentry.entityClass);
/*     */                         
/* 195 */                         if (!ReflectorForge.doSpecialSpawn(entityliving, worldServerIn, f, i3, f1))
/*     */                         {
/* 197 */                           ientitylivingdata = entityliving.onInitialSpawn(worldServerIn.getDifficultyForLocation(new BlockPos((Entity)entityliving)), ientitylivingdata);
/*     */                         }
/*     */                         
/* 200 */                         if (entityliving.isNotColliding()) {
/*     */                           
/* 202 */                           j2++;
/* 203 */                           worldServerIn.spawnEntityInWorld((Entity)entityliving);
/*     */                         } 
/*     */                         
/* 206 */                         int i4 = Reflector.ForgeEventFactory_getMaxSpawnPackSize.exists() ? Reflector.callInt(Reflector.ForgeEventFactory_getMaxSpawnPackSize, new Object[] { entityliving }) : entityliving.getMaxSpawnedInChunk();
/*     */                         
/* 208 */                         if (j2 >= i4) {
/*     */                           continue label118;
/*     */                         }
/*     */                       } 
/*     */ 
/*     */                       
/* 214 */                       j4 += j2;
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 225 */     return j4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static BlockPos getRandomChunkPosition(World worldIn, int x, int z) {
/* 231 */     Chunk chunk = worldIn.getChunkFromChunkCoords(x, z);
/* 232 */     int i = (x << 4) + worldIn.rand.nextInt(16);
/* 233 */     int j = (z << 4) + worldIn.rand.nextInt(16);
/* 234 */     int k = MathHelper.roundUp(chunk.getHeight(new BlockPos(i, 0, j)) + 1, 16);
/* 235 */     int l = worldIn.rand.nextInt((k > 0) ? k : (chunk.getTopFilledSegment() + 16 - 1));
/* 236 */     return new BlockPos(i, l, j);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BlockPosM getRandomChunkPosition(World p_getRandomChunkPosition_0_, int p_getRandomChunkPosition_1_, int p_getRandomChunkPosition_2_, BlockPosM p_getRandomChunkPosition_3_) {
/* 241 */     Chunk chunk = p_getRandomChunkPosition_0_.getChunkFromChunkCoords(p_getRandomChunkPosition_1_, p_getRandomChunkPosition_2_);
/* 242 */     int i = (p_getRandomChunkPosition_1_ << 4) + p_getRandomChunkPosition_0_.rand.nextInt(16);
/* 243 */     int j = (p_getRandomChunkPosition_2_ << 4) + p_getRandomChunkPosition_0_.rand.nextInt(16);
/* 244 */     int k = MathHelper.roundUp(chunk.getHeightValue(i & 0xF, j & 0xF) + 1, 16);
/* 245 */     int l = p_getRandomChunkPosition_0_.rand.nextInt((k > 0) ? k : (chunk.getTopFilledSegment() + 16 - 1));
/* 246 */     p_getRandomChunkPosition_3_.setXyz(i, l, j);
/* 247 */     return p_getRandomChunkPosition_3_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType spawnPlacementTypeIn, World worldIn, BlockPos pos) {
/* 252 */     if (!worldIn.getWorldBorder().contains(pos))
/*     */     {
/* 254 */       return false;
/*     */     }
/* 256 */     if (spawnPlacementTypeIn == null)
/*     */     {
/* 258 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 262 */     Block block = worldIn.getBlockState(pos).getBlock();
/*     */     
/* 264 */     if (spawnPlacementTypeIn == EntityLiving.SpawnPlacementType.IN_WATER)
/*     */     {
/* 266 */       return (block.getMaterial().isLiquid() && worldIn.getBlockState(pos.down()).getBlock().getMaterial().isLiquid() && !worldIn.getBlockState(pos.up()).getBlock().isNormalCube());
/*     */     }
/*     */ 
/*     */     
/* 270 */     BlockPos blockpos = pos.down();
/* 271 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/* 272 */     boolean flag = Reflector.ForgeBlock_canCreatureSpawn.exists() ? Reflector.callBoolean(iblockstate.getBlock(), Reflector.ForgeBlock_canCreatureSpawn, new Object[] { worldIn, blockpos, spawnPlacementTypeIn }) : World.doesBlockHaveSolidTopSurface(worldIn, blockpos);
/*     */     
/* 274 */     if (!flag)
/*     */     {
/* 276 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 280 */     Block block1 = worldIn.getBlockState(blockpos).getBlock();
/* 281 */     boolean flag1 = (block1 != Blocks.bedrock && block1 != Blocks.barrier);
/* 282 */     return (flag1 && !block.isNormalCube() && !block.getMaterial().isLiquid() && !worldIn.getBlockState(pos.up()).getBlock().isNormalCube());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void performWorldGenSpawning(World worldIn, BiomeGenBase biomeIn, int p_77191_2_, int p_77191_3_, int p_77191_4_, int p_77191_5_, Random randomIn) {
/* 293 */     List<BiomeGenBase.SpawnListEntry> list = biomeIn.getSpawnableList(EnumCreatureType.CREATURE);
/*     */     
/* 295 */     if (!list.isEmpty())
/*     */     {
/* 297 */       while (randomIn.nextFloat() < biomeIn.getSpawningChance()) {
/*     */         
/* 299 */         BiomeGenBase.SpawnListEntry biomegenbase$spawnlistentry = (BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(worldIn.rand, list);
/* 300 */         int i = biomegenbase$spawnlistentry.minGroupCount + randomIn.nextInt(1 + biomegenbase$spawnlistentry.maxGroupCount - biomegenbase$spawnlistentry.minGroupCount);
/* 301 */         IEntityLivingData ientitylivingdata = null;
/* 302 */         int j = p_77191_2_ + randomIn.nextInt(p_77191_4_);
/* 303 */         int k = p_77191_3_ + randomIn.nextInt(p_77191_5_);
/* 304 */         int l = j;
/* 305 */         int i1 = k;
/*     */         
/* 307 */         for (int j1 = 0; j1 < i; j1++) {
/*     */           
/* 309 */           boolean flag = false;
/*     */           
/* 311 */           for (int k1 = 0; !flag && k1 < 4; k1++) {
/*     */             
/* 313 */             BlockPos blockpos = worldIn.getTopSolidOrLiquidBlock(new BlockPos(j, 0, k));
/*     */             
/* 315 */             if (canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, worldIn, blockpos)) {
/*     */               EntityLiving entityliving;
/*     */ 
/*     */ 
/*     */               
/*     */               try {
/* 321 */                 entityliving = biomegenbase$spawnlistentry.entityClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldIn });
/*     */               }
/* 323 */               catch (Exception exception1) {
/*     */                 
/* 325 */                 exception1.printStackTrace();
/*     */                 
/*     */                 continue;
/*     */               } 
/* 329 */               if (Reflector.ForgeEventFactory_canEntitySpawn.exists()) {
/*     */                 
/* 331 */                 Object object = Reflector.call(Reflector.ForgeEventFactory_canEntitySpawn, new Object[] { entityliving, worldIn, Float.valueOf(j + 0.5F), Integer.valueOf(blockpos.getY()), Float.valueOf(k + 0.5F) });
/*     */                 
/* 333 */                 if (object == ReflectorForge.EVENT_RESULT_DENY) {
/*     */                   continue;
/*     */                 }
/*     */               } 
/*     */ 
/*     */               
/* 339 */               entityliving.setLocationAndAngles((j + 0.5F), blockpos.getY(), (k + 0.5F), randomIn.nextFloat() * 360.0F, 0.0F);
/* 340 */               worldIn.spawnEntityInWorld((Entity)entityliving);
/* 341 */               ientitylivingdata = entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos((Entity)entityliving)), ientitylivingdata);
/* 342 */               flag = true;
/*     */             } 
/*     */             
/* 345 */             j += randomIn.nextInt(5) - randomIn.nextInt(5);
/*     */             
/* 347 */             for (k += randomIn.nextInt(5) - randomIn.nextInt(5); j < p_77191_2_ || j >= p_77191_2_ + p_77191_4_ || k < p_77191_3_ || k >= p_77191_3_ + p_77191_4_; k = i1 + randomIn.nextInt(5) - randomIn.nextInt(5))
/*     */             {
/* 349 */               j = l + randomIn.nextInt(5) - randomIn.nextInt(5);
/*     */             }
/*     */             continue;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\SpawnerAnimals.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */