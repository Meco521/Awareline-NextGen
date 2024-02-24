/*     */ package net.minecraft.world;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockPortal;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.block.state.pattern.BlockPattern;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.LongHashMap;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3i;
/*     */ 
/*     */ 
/*     */ public class Teleporter
/*     */ {
/*     */   private final WorldServer worldServerInstance;
/*     */   private final Random random;
/*  24 */   private final LongHashMap<PortalPosition> destinationCoordinateCache = new LongHashMap();
/*  25 */   private final List<Long> destinationCoordinateKeys = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public Teleporter(WorldServer worldIn) {
/*  29 */     this.worldServerInstance = worldIn;
/*  30 */     this.random = new Random(worldIn.getSeed());
/*     */   }
/*     */ 
/*     */   
/*     */   public void placeInPortal(Entity entityIn, float rotationYaw) {
/*  35 */     if (this.worldServerInstance.provider.getDimensionId() != 1) {
/*     */       
/*  37 */       if (!placeInExistingPortal(entityIn, rotationYaw))
/*     */       {
/*  39 */         makePortal(entityIn);
/*  40 */         placeInExistingPortal(entityIn, rotationYaw);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  45 */       int i = MathHelper.floor_double(entityIn.posX);
/*  46 */       int j = MathHelper.floor_double(entityIn.posY) - 1;
/*  47 */       int k = MathHelper.floor_double(entityIn.posZ);
/*  48 */       int l = 1;
/*  49 */       int i1 = 0;
/*     */       
/*  51 */       for (int j1 = -2; j1 <= 2; j1++) {
/*     */         
/*  53 */         for (int k1 = -2; k1 <= 2; k1++) {
/*     */           
/*  55 */           for (int l1 = -1; l1 < 3; l1++) {
/*     */             
/*  57 */             int i2 = i + k1 * l + j1 * i1;
/*  58 */             int j2 = j + l1;
/*  59 */             int k2 = k + k1 * i1 - j1 * l;
/*  60 */             boolean flag = (l1 < 0);
/*  61 */             this.worldServerInstance.setBlockState(new BlockPos(i2, j2, k2), flag ? Blocks.obsidian.getDefaultState() : Blocks.air.getDefaultState());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  66 */       entityIn.setLocationAndAngles(i, j, k, entityIn.rotationYaw, 0.0F);
/*  67 */       entityIn.motionX = entityIn.motionY = entityIn.motionZ = 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
/*  73 */     int i = 128;
/*  74 */     double d0 = -1.0D;
/*  75 */     int j = MathHelper.floor_double(entityIn.posX);
/*  76 */     int k = MathHelper.floor_double(entityIn.posZ);
/*  77 */     boolean flag = true;
/*  78 */     BlockPos blockpos = BlockPos.ORIGIN;
/*  79 */     long l = ChunkCoordIntPair.chunkXZ2Int(j, k);
/*     */     
/*  81 */     if (this.destinationCoordinateCache.containsItem(l)) {
/*     */       
/*  83 */       PortalPosition teleporter$portalposition = (PortalPosition)this.destinationCoordinateCache.getValueByKey(l);
/*  84 */       d0 = 0.0D;
/*  85 */       blockpos = teleporter$portalposition;
/*  86 */       teleporter$portalposition.lastUpdateTime = this.worldServerInstance.getTotalWorldTime();
/*  87 */       flag = false;
/*     */     }
/*     */     else {
/*     */       
/*  91 */       BlockPos blockpos3 = new BlockPos(entityIn);
/*     */       
/*  93 */       for (int i1 = -128; i1 <= 128; i1++) {
/*     */ 
/*     */ 
/*     */         
/*  97 */         for (int j1 = -128; j1 <= 128; j1++) {
/*     */           
/*  99 */           for (BlockPos blockpos1 = blockpos3.add(i1, this.worldServerInstance.getActualHeight() - 1 - blockpos3.getY(), j1); blockpos1.getY() >= 0; blockpos1 = blockpos2) {
/*     */             
/* 101 */             BlockPos blockpos2 = blockpos1.down();
/*     */             
/* 103 */             if (this.worldServerInstance.getBlockState(blockpos1).getBlock() == Blocks.portal) {
/*     */               
/* 105 */               while (this.worldServerInstance.getBlockState(blockpos2 = blockpos1.down()).getBlock() == Blocks.portal)
/*     */               {
/* 107 */                 blockpos1 = blockpos2;
/*     */               }
/*     */               
/* 110 */               double d1 = blockpos1.distanceSq((Vec3i)blockpos3);
/*     */               
/* 112 */               if (d0 < 0.0D || d1 < d0) {
/*     */                 
/* 114 */                 d0 = d1;
/* 115 */                 blockpos = blockpos1;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 123 */     if (d0 >= 0.0D) {
/*     */       
/* 125 */       if (flag) {
/*     */         
/* 127 */         this.destinationCoordinateCache.add(l, new PortalPosition(blockpos, this.worldServerInstance.getTotalWorldTime()));
/* 128 */         this.destinationCoordinateKeys.add(Long.valueOf(l));
/*     */       } 
/*     */       
/* 131 */       double d5 = blockpos.getX() + 0.5D;
/* 132 */       double d6 = blockpos.getY() + 0.5D;
/* 133 */       double d7 = blockpos.getZ() + 0.5D;
/* 134 */       BlockPattern.PatternHelper blockpattern$patternhelper = Blocks.portal.func_181089_f(this.worldServerInstance, blockpos);
/* 135 */       boolean flag1 = (blockpattern$patternhelper.getFinger().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE);
/* 136 */       double d2 = (blockpattern$patternhelper.getFinger().getAxis() == EnumFacing.Axis.X) ? blockpattern$patternhelper.getPos().getZ() : blockpattern$patternhelper.getPos().getX();
/* 137 */       d6 = (blockpattern$patternhelper.getPos().getY() + 1) - (entityIn.func_181014_aG()).yCoord * blockpattern$patternhelper.func_181119_e();
/*     */       
/* 139 */       if (flag1)
/*     */       {
/* 141 */         d2++;
/*     */       }
/*     */       
/* 144 */       if (blockpattern$patternhelper.getFinger().getAxis() == EnumFacing.Axis.X) {
/*     */         
/* 146 */         d7 = d2 + (1.0D - (entityIn.func_181014_aG()).xCoord) * blockpattern$patternhelper.func_181118_d() * blockpattern$patternhelper.getFinger().rotateY().getAxisDirection().getOffset();
/*     */       }
/*     */       else {
/*     */         
/* 150 */         d5 = d2 + (1.0D - (entityIn.func_181014_aG()).xCoord) * blockpattern$patternhelper.func_181118_d() * blockpattern$patternhelper.getFinger().rotateY().getAxisDirection().getOffset();
/*     */       } 
/*     */       
/* 153 */       float f = 0.0F;
/* 154 */       float f1 = 0.0F;
/* 155 */       float f2 = 0.0F;
/* 156 */       float f3 = 0.0F;
/*     */       
/* 158 */       if (blockpattern$patternhelper.getFinger().getOpposite() == entityIn.getTeleportDirection()) {
/*     */         
/* 160 */         f = 1.0F;
/* 161 */         f1 = 1.0F;
/*     */       }
/* 163 */       else if (blockpattern$patternhelper.getFinger().getOpposite() == entityIn.getTeleportDirection().getOpposite()) {
/*     */         
/* 165 */         f = -1.0F;
/* 166 */         f1 = -1.0F;
/*     */       }
/* 168 */       else if (blockpattern$patternhelper.getFinger().getOpposite() == entityIn.getTeleportDirection().rotateY()) {
/*     */         
/* 170 */         f2 = 1.0F;
/* 171 */         f3 = -1.0F;
/*     */       }
/*     */       else {
/*     */         
/* 175 */         f2 = -1.0F;
/* 176 */         f3 = 1.0F;
/*     */       } 
/*     */       
/* 179 */       double d3 = entityIn.motionX;
/* 180 */       double d4 = entityIn.motionZ;
/* 181 */       entityIn.motionX = d3 * f + d4 * f3;
/* 182 */       entityIn.motionZ = d3 * f2 + d4 * f1;
/* 183 */       entityIn.rotationYaw = rotationYaw - (entityIn.getTeleportDirection().getOpposite().getHorizontalIndex() * 90) + (blockpattern$patternhelper.getFinger().getHorizontalIndex() * 90);
/* 184 */       entityIn.setLocationAndAngles(d5, d6, d7, entityIn.rotationYaw, entityIn.rotationPitch);
/* 185 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 189 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean makePortal(Entity entityIn) {
/* 195 */     int i = 16;
/* 196 */     double d0 = -1.0D;
/* 197 */     int j = MathHelper.floor_double(entityIn.posX);
/* 198 */     int k = MathHelper.floor_double(entityIn.posY);
/* 199 */     int l = MathHelper.floor_double(entityIn.posZ);
/* 200 */     int i1 = j;
/* 201 */     int j1 = k;
/* 202 */     int k1 = l;
/* 203 */     int l1 = 0;
/* 204 */     int i2 = this.random.nextInt(4);
/* 205 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 207 */     for (int j2 = j - i; j2 <= j + i; j2++) {
/*     */       
/* 209 */       double d1 = j2 + 0.5D - entityIn.posX;
/*     */       
/* 211 */       for (int l2 = l - i; l2 <= l + i; l2++) {
/*     */         
/* 213 */         double d2 = l2 + 0.5D - entityIn.posZ;
/*     */         
/*     */         int j3;
/* 216 */         label172: for (j3 = this.worldServerInstance.getActualHeight() - 1; j3 >= 0; j3--) {
/*     */           
/* 218 */           if (this.worldServerInstance.isAirBlock((BlockPos)blockpos$mutableblockpos.set(j2, j3, l2))) {
/*     */             
/* 220 */             while (j3 > 0 && this.worldServerInstance.isAirBlock((BlockPos)blockpos$mutableblockpos.set(j2, j3 - 1, l2)))
/*     */             {
/* 222 */               j3--;
/*     */             }
/*     */             
/* 225 */             for (int k3 = i2; k3 < i2 + 4; k3++) {
/*     */               
/* 227 */               int l3 = k3 % 2;
/* 228 */               int i4 = 1 - l3;
/*     */               
/* 230 */               if (k3 % 4 >= 2) {
/*     */                 
/* 232 */                 l3 = -l3;
/* 233 */                 i4 = -i4;
/*     */               } 
/*     */               
/* 236 */               for (int j4 = 0; j4 < 3; j4++) {
/*     */                 
/* 238 */                 for (int k4 = 0; k4 < 4; k4++) {
/*     */                   
/* 240 */                   for (int l4 = -1; l4 < 4; ) {
/*     */                     
/* 242 */                     int i5 = j2 + (k4 - 1) * l3 + j4 * i4;
/* 243 */                     int j5 = j3 + l4;
/* 244 */                     int k5 = l2 + (k4 - 1) * i4 - j4 * l3;
/* 245 */                     blockpos$mutableblockpos.set(i5, j5, k5);
/*     */                     
/* 247 */                     if (l4 >= 0 || this.worldServerInstance.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock().getMaterial().isSolid()) { if (l4 >= 0 && !this.worldServerInstance.isAirBlock((BlockPos)blockpos$mutableblockpos))
/*     */                         continue label172; 
/*     */                       l4++; }
/*     */                     
/*     */                     continue label172;
/*     */                   } 
/*     */                 } 
/*     */               } 
/* 255 */               double d5 = j3 + 0.5D - entityIn.posY;
/* 256 */               double d7 = d1 * d1 + d5 * d5 + d2 * d2;
/*     */               
/* 258 */               if (d0 < 0.0D || d7 < d0) {
/*     */                 
/* 260 */                 d0 = d7;
/* 261 */                 i1 = j2;
/* 262 */                 j1 = j3;
/* 263 */                 k1 = l2;
/* 264 */                 l1 = k3 % 4;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 272 */     if (d0 < 0.0D)
/*     */     {
/* 274 */       for (int l5 = j - i; l5 <= j + i; l5++) {
/*     */         
/* 276 */         double d3 = l5 + 0.5D - entityIn.posX;
/*     */         
/* 278 */         for (int j6 = l - i; j6 <= l + i; j6++) {
/*     */           
/* 280 */           double d4 = j6 + 0.5D - entityIn.posZ;
/*     */           
/*     */           int i7;
/* 283 */           label169: for (i7 = this.worldServerInstance.getActualHeight() - 1; i7 >= 0; i7--) {
/*     */             
/* 285 */             if (this.worldServerInstance.isAirBlock((BlockPos)blockpos$mutableblockpos.set(l5, i7, j6))) {
/*     */               
/* 287 */               while (i7 > 0 && this.worldServerInstance.isAirBlock((BlockPos)blockpos$mutableblockpos.set(l5, i7 - 1, j6)))
/*     */               {
/* 289 */                 i7--;
/*     */               }
/*     */               
/* 292 */               for (int k7 = i2; k7 < i2 + 2; k7++) {
/*     */                 
/* 294 */                 int j8 = k7 % 2;
/* 295 */                 int j9 = 1 - j8;
/*     */                 
/* 297 */                 for (int j10 = 0; j10 < 4; j10++) {
/*     */                   
/* 299 */                   for (int j11 = -1; j11 < 4; ) {
/*     */                     
/* 301 */                     int j12 = l5 + (j10 - 1) * j8;
/* 302 */                     int i13 = i7 + j11;
/* 303 */                     int j13 = j6 + (j10 - 1) * j9;
/* 304 */                     blockpos$mutableblockpos.set(j12, i13, j13);
/*     */                     
/* 306 */                     if (j11 >= 0 || this.worldServerInstance.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock().getMaterial().isSolid()) { if (j11 >= 0 && !this.worldServerInstance.isAirBlock((BlockPos)blockpos$mutableblockpos))
/*     */                         continue label169; 
/*     */                       j11++; }
/*     */                     
/*     */                     continue label169;
/*     */                   } 
/*     */                 } 
/* 313 */                 double d6 = i7 + 0.5D - entityIn.posY;
/* 314 */                 double d8 = d3 * d3 + d6 * d6 + d4 * d4;
/*     */                 
/* 316 */                 if (d0 < 0.0D || d8 < d0) {
/*     */                   
/* 318 */                   d0 = d8;
/* 319 */                   i1 = l5;
/* 320 */                   j1 = i7;
/* 321 */                   k1 = j6;
/* 322 */                   l1 = k7 % 2;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 331 */     int i6 = i1;
/* 332 */     int k2 = j1;
/* 333 */     int k6 = k1;
/* 334 */     int l6 = l1 % 2;
/* 335 */     int i3 = 1 - l6;
/*     */     
/* 337 */     if (l1 % 4 >= 2) {
/*     */       
/* 339 */       l6 = -l6;
/* 340 */       i3 = -i3;
/*     */     } 
/*     */     
/* 343 */     if (d0 < 0.0D) {
/*     */       
/* 345 */       j1 = MathHelper.clamp_int(j1, 70, this.worldServerInstance.getActualHeight() - 10);
/* 346 */       k2 = j1;
/*     */       
/* 348 */       for (int j7 = -1; j7 <= 1; j7++) {
/*     */         
/* 350 */         for (int l7 = 1; l7 < 3; l7++) {
/*     */           
/* 352 */           for (int k8 = -1; k8 < 3; k8++) {
/*     */             
/* 354 */             int k9 = i6 + (l7 - 1) * l6 + j7 * i3;
/* 355 */             int k10 = k2 + k8;
/* 356 */             int k11 = k6 + (l7 - 1) * i3 - j7 * l6;
/* 357 */             boolean flag = (k8 < 0);
/* 358 */             this.worldServerInstance.setBlockState(new BlockPos(k9, k10, k11), flag ? Blocks.obsidian.getDefaultState() : Blocks.air.getDefaultState());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 364 */     IBlockState iblockstate = Blocks.portal.getDefaultState().withProperty((IProperty)BlockPortal.AXIS, (l6 != 0) ? (Comparable)EnumFacing.Axis.X : (Comparable)EnumFacing.Axis.Z);
/*     */     
/* 366 */     for (int i8 = 0; i8 < 4; i8++) {
/*     */       
/* 368 */       for (int l8 = 0; l8 < 4; l8++) {
/*     */         
/* 370 */         for (int l9 = -1; l9 < 4; l9++) {
/*     */           
/* 372 */           int l10 = i6 + (l8 - 1) * l6;
/* 373 */           int l11 = k2 + l9;
/* 374 */           int k12 = k6 + (l8 - 1) * i3;
/* 375 */           boolean flag1 = (l8 == 0 || l8 == 3 || l9 == -1 || l9 == 3);
/* 376 */           this.worldServerInstance.setBlockState(new BlockPos(l10, l11, k12), flag1 ? Blocks.obsidian.getDefaultState() : iblockstate, 2);
/*     */         } 
/*     */       } 
/*     */       
/* 380 */       for (int i9 = 0; i9 < 4; i9++) {
/*     */         
/* 382 */         for (int i10 = -1; i10 < 4; i10++) {
/*     */           
/* 384 */           int i11 = i6 + (i9 - 1) * l6;
/* 385 */           int i12 = k2 + i10;
/* 386 */           int l12 = k6 + (i9 - 1) * i3;
/* 387 */           BlockPos blockpos = new BlockPos(i11, i12, l12);
/* 388 */           this.worldServerInstance.notifyNeighborsOfStateChange(blockpos, this.worldServerInstance.getBlockState(blockpos).getBlock());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 393 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeStalePortalLocations(long worldTime) {
/* 402 */     if (worldTime % 100L == 0L) {
/*     */       
/* 404 */       Iterator<Long> iterator = this.destinationCoordinateKeys.iterator();
/* 405 */       long i = worldTime - 300L;
/*     */       
/* 407 */       while (iterator.hasNext()) {
/*     */         
/* 409 */         Long olong = iterator.next();
/* 410 */         PortalPosition teleporter$portalposition = (PortalPosition)this.destinationCoordinateCache.getValueByKey(olong.longValue());
/*     */         
/* 412 */         if (teleporter$portalposition == null || teleporter$portalposition.lastUpdateTime < i) {
/*     */           
/* 414 */           iterator.remove();
/* 415 */           this.destinationCoordinateCache.remove(olong.longValue());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class PortalPosition
/*     */     extends BlockPos
/*     */   {
/*     */     public long lastUpdateTime;
/*     */     
/*     */     public PortalPosition(BlockPos pos, long lastUpdate) {
/* 427 */       super(pos.getX(), pos.getY(), pos.getZ());
/* 428 */       this.lastUpdateTime = lastUpdate;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\Teleporter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */