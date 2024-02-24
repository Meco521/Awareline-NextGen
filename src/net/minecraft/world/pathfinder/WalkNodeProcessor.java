/*     */ package net.minecraft.world.pathfinder;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.pathfinding.PathPoint;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ public class WalkNodeProcessor
/*     */   extends NodeProcessor
/*     */ {
/*     */   private boolean canEnterDoors;
/*     */   private boolean canBreakDoors;
/*     */   private boolean avoidsWater;
/*     */   private boolean canSwim;
/*     */   private boolean shouldAvoidWater;
/*     */   
/*     */   public void initProcessor(IBlockAccess iblockaccessIn, Entity entityIn) {
/*  22 */     super.initProcessor(iblockaccessIn, entityIn);
/*  23 */     this.shouldAvoidWater = this.avoidsWater;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postProcess() {
/*  33 */     super.postProcess();
/*  34 */     this.avoidsWater = this.shouldAvoidWater;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathPoint getPathPointTo(Entity entityIn) {
/*     */     int i;
/*  44 */     if (this.canSwim && entityIn.isInWater()) {
/*     */       
/*  46 */       i = (int)(entityIn.getEntityBoundingBox()).minY;
/*  47 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor_double(entityIn.posX), i, MathHelper.floor_double(entityIn.posZ));
/*     */       
/*  49 */       for (Block block = this.blockaccess.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock(); block == Blocks.flowing_water || block == Blocks.water; block = this.blockaccess.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock()) {
/*     */         
/*  51 */         i++;
/*  52 */         blockpos$mutableblockpos.set(MathHelper.floor_double(entityIn.posX), i, MathHelper.floor_double(entityIn.posZ));
/*     */       } 
/*     */       
/*  55 */       this.avoidsWater = false;
/*     */     }
/*     */     else {
/*     */       
/*  59 */       i = MathHelper.floor_double((entityIn.getEntityBoundingBox()).minY + 0.5D);
/*     */     } 
/*     */     
/*  62 */     return openPoint(MathHelper.floor_double((entityIn.getEntityBoundingBox()).minX), i, MathHelper.floor_double((entityIn.getEntityBoundingBox()).minZ));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathPoint getPathPointToCoords(Entity entityIn, double x, double y, double target) {
/*  70 */     return openPoint(MathHelper.floor_double(x - (entityIn.width / 2.0F)), MathHelper.floor_double(y), MathHelper.floor_double(target - (entityIn.width / 2.0F)));
/*     */   }
/*     */ 
/*     */   
/*     */   public int findPathOptions(PathPoint[] pathOptions, Entity entityIn, PathPoint currentPoint, PathPoint targetPoint, float maxDistance) {
/*  75 */     int i = 0;
/*  76 */     int j = 0;
/*     */     
/*  78 */     if (getVerticalOffset(entityIn, currentPoint.xCoord, currentPoint.yCoord + 1, currentPoint.zCoord) == 1)
/*     */     {
/*  80 */       j = 1;
/*     */     }
/*     */     
/*  83 */     PathPoint pathpoint = getSafePoint(entityIn, currentPoint.xCoord, currentPoint.yCoord, currentPoint.zCoord + 1, j);
/*  84 */     PathPoint pathpoint1 = getSafePoint(entityIn, currentPoint.xCoord - 1, currentPoint.yCoord, currentPoint.zCoord, j);
/*  85 */     PathPoint pathpoint2 = getSafePoint(entityIn, currentPoint.xCoord + 1, currentPoint.yCoord, currentPoint.zCoord, j);
/*  86 */     PathPoint pathpoint3 = getSafePoint(entityIn, currentPoint.xCoord, currentPoint.yCoord, currentPoint.zCoord - 1, j);
/*     */     
/*  88 */     if (pathpoint != null && !pathpoint.visited && pathpoint.distanceTo(targetPoint) < maxDistance)
/*     */     {
/*  90 */       pathOptions[i++] = pathpoint;
/*     */     }
/*     */     
/*  93 */     if (pathpoint1 != null && !pathpoint1.visited && pathpoint1.distanceTo(targetPoint) < maxDistance)
/*     */     {
/*  95 */       pathOptions[i++] = pathpoint1;
/*     */     }
/*     */     
/*  98 */     if (pathpoint2 != null && !pathpoint2.visited && pathpoint2.distanceTo(targetPoint) < maxDistance)
/*     */     {
/* 100 */       pathOptions[i++] = pathpoint2;
/*     */     }
/*     */     
/* 103 */     if (pathpoint3 != null && !pathpoint3.visited && pathpoint3.distanceTo(targetPoint) < maxDistance)
/*     */     {
/* 105 */       pathOptions[i++] = pathpoint3;
/*     */     }
/*     */     
/* 108 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PathPoint getSafePoint(Entity entityIn, int x, int y, int z, int p_176171_5_) {
/* 116 */     PathPoint pathpoint = null;
/* 117 */     int i = getVerticalOffset(entityIn, x, y, z);
/*     */     
/* 119 */     if (i == 2)
/*     */     {
/* 121 */       return openPoint(x, y, z);
/*     */     }
/*     */ 
/*     */     
/* 125 */     if (i == 1)
/*     */     {
/* 127 */       pathpoint = openPoint(x, y, z);
/*     */     }
/*     */     
/* 130 */     if (pathpoint == null && p_176171_5_ > 0 && i != -3 && i != -4 && getVerticalOffset(entityIn, x, y + p_176171_5_, z) == 1) {
/*     */       
/* 132 */       pathpoint = openPoint(x, y + p_176171_5_, z);
/* 133 */       y += p_176171_5_;
/*     */     } 
/*     */     
/* 136 */     if (pathpoint != null) {
/*     */       
/* 138 */       int j = 0;
/*     */       
/*     */       int k;
/* 141 */       for (k = 0; y > 0; pathpoint = openPoint(x, y, z)) {
/*     */         
/* 143 */         k = getVerticalOffset(entityIn, x, y - 1, z);
/*     */         
/* 145 */         if (this.avoidsWater && k == -1)
/*     */         {
/* 147 */           return null;
/*     */         }
/*     */         
/* 150 */         if (k != 1) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 155 */         if (j++ >= entityIn.getMaxFallHeight())
/*     */         {
/* 157 */           return null;
/*     */         }
/*     */         
/* 160 */         y--;
/*     */         
/* 162 */         if (y <= 0)
/*     */         {
/* 164 */           return null;
/*     */         }
/*     */       } 
/*     */       
/* 168 */       if (k == -2)
/*     */       {
/* 170 */         return null;
/*     */       }
/*     */     } 
/*     */     
/* 174 */     return pathpoint;
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
/*     */   private int getVerticalOffset(Entity entityIn, int x, int y, int z) {
/* 186 */     return func_176170_a(this.blockaccess, entityIn, x, y, z, this.entitySizeX, this.entitySizeY, this.entitySizeZ, this.avoidsWater, this.canBreakDoors, this.canEnterDoors);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int func_176170_a(IBlockAccess blockaccessIn, Entity entityIn, int x, int y, int z, int sizeX, int sizeY, int sizeZ, boolean avoidWater, boolean breakDoors, boolean enterDoors) {
/* 191 */     boolean flag = false;
/* 192 */     BlockPos blockpos = new BlockPos(entityIn);
/* 193 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 195 */     for (int i = x; i < x + sizeX; i++) {
/*     */       
/* 197 */       for (int j = y; j < y + sizeY; j++) {
/*     */         
/* 199 */         for (int k = z; k < z + sizeZ; k++) {
/*     */           
/* 201 */           blockpos$mutableblockpos.set(i, j, k);
/* 202 */           Block block = blockaccessIn.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock();
/*     */           
/* 204 */           if (block.getMaterial() != Material.air) {
/*     */             
/* 206 */             if (block != Blocks.trapdoor && block != Blocks.iron_trapdoor) {
/*     */               
/* 208 */               if (block != Blocks.flowing_water && block != Blocks.water)
/*     */               {
/* 210 */                 if (!enterDoors && block instanceof net.minecraft.block.BlockDoor && block.getMaterial() == Material.wood)
/*     */                 {
/* 212 */                   return 0;
/*     */                 }
/*     */               }
/*     */               else
/*     */               {
/* 217 */                 if (avoidWater)
/*     */                 {
/* 219 */                   return -1;
/*     */                 }
/*     */                 
/* 222 */                 flag = true;
/*     */               }
/*     */             
/*     */             } else {
/*     */               
/* 227 */               flag = true;
/*     */             } 
/*     */             
/* 230 */             if (entityIn.worldObj.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock() instanceof net.minecraft.block.BlockRailBase) {
/*     */               
/* 232 */               if (!(entityIn.worldObj.getBlockState(blockpos).getBlock() instanceof net.minecraft.block.BlockRailBase) && !(entityIn.worldObj.getBlockState(blockpos.down()).getBlock() instanceof net.minecraft.block.BlockRailBase))
/*     */               {
/* 234 */                 return -3;
/*     */               }
/*     */             }
/* 237 */             else if (!block.isPassable(blockaccessIn, (BlockPos)blockpos$mutableblockpos) && (!breakDoors || !(block instanceof net.minecraft.block.BlockDoor) || block.getMaterial() != Material.wood)) {
/*     */               
/* 239 */               if (block instanceof net.minecraft.block.BlockFence || block instanceof net.minecraft.block.BlockFenceGate || block instanceof net.minecraft.block.BlockWall)
/*     */               {
/* 241 */                 return -3;
/*     */               }
/*     */               
/* 244 */               if (block == Blocks.trapdoor || block == Blocks.iron_trapdoor)
/*     */               {
/* 246 */                 return -4;
/*     */               }
/*     */               
/* 249 */               Material material = block.getMaterial();
/*     */               
/* 251 */               if (material != Material.lava)
/*     */               {
/* 253 */                 return 0;
/*     */               }
/*     */               
/* 256 */               if (!entityIn.isInLava())
/*     */               {
/* 258 */                 return -2;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 266 */     return flag ? 2 : 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnterDoors(boolean canEnterDoorsIn) {
/* 271 */     this.canEnterDoors = canEnterDoorsIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBreakDoors(boolean canBreakDoorsIn) {
/* 276 */     this.canBreakDoors = canBreakDoorsIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAvoidsWater(boolean avoidsWaterIn) {
/* 281 */     this.avoidsWater = avoidsWaterIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCanSwim(boolean canSwimIn) {
/* 286 */     this.canSwim = canSwimIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getEnterDoors() {
/* 291 */     return this.canEnterDoors;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getCanSwim() {
/* 296 */     return this.canSwim;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getAvoidsWater() {
/* 301 */     return this.avoidsWater;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\pathfinder\WalkNodeProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */