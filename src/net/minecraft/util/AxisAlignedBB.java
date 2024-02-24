/*     */ package net.minecraft.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AxisAlignedBB
/*     */ {
/*     */   public final double minX;
/*     */   public final double minY;
/*     */   public final double minZ;
/*     */   public final double maxX;
/*     */   public final double maxY;
/*     */   public final double maxZ;
/*     */   
/*     */   public AxisAlignedBB(double x1, double y1, double z1, double x2, double y2, double z2) {
/*  15 */     this.minX = Math.min(x1, x2);
/*  16 */     this.minY = Math.min(y1, y2);
/*  17 */     this.minZ = Math.min(z1, z2);
/*  18 */     this.maxX = Math.max(x1, x2);
/*  19 */     this.maxY = Math.max(y1, y2);
/*  20 */     this.maxZ = Math.max(z1, z2);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB(BlockPos pos1, BlockPos pos2) {
/*  25 */     this.minX = pos1.getX();
/*  26 */     this.minY = pos1.getY();
/*  27 */     this.minZ = pos1.getZ();
/*  28 */     this.maxX = pos2.getX();
/*  29 */     this.maxY = pos2.getY();
/*  30 */     this.maxZ = pos2.getZ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB addCoord(double x, double y, double z) {
/*  38 */     double d0 = this.minX;
/*  39 */     double d1 = this.minY;
/*  40 */     double d2 = this.minZ;
/*  41 */     double d3 = this.maxX;
/*  42 */     double d4 = this.maxY;
/*  43 */     double d5 = this.maxZ;
/*     */     
/*  45 */     if (x < 0.0D) {
/*     */       
/*  47 */       d0 += x;
/*     */     }
/*  49 */     else if (x > 0.0D) {
/*     */       
/*  51 */       d3 += x;
/*     */     } 
/*     */     
/*  54 */     if (y < 0.0D) {
/*     */       
/*  56 */       d1 += y;
/*     */     }
/*  58 */     else if (y > 0.0D) {
/*     */       
/*  60 */       d4 += y;
/*     */     } 
/*     */     
/*  63 */     if (z < 0.0D) {
/*     */       
/*  65 */       d2 += z;
/*     */     }
/*  67 */     else if (z > 0.0D) {
/*     */       
/*  69 */       d5 += z;
/*     */     } 
/*     */     
/*  72 */     return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB expand(double x, double y, double z) {
/*  81 */     double d0 = this.minX - x;
/*  82 */     double d1 = this.minY - y;
/*  83 */     double d2 = this.minZ - z;
/*  84 */     double d3 = this.maxX + x;
/*  85 */     double d4 = this.maxY + y;
/*  86 */     double d5 = this.maxZ + z;
/*  87 */     return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB union(AxisAlignedBB other) {
/*  92 */     double d0 = Math.min(this.minX, other.minX);
/*  93 */     double d1 = Math.min(this.minY, other.minY);
/*  94 */     double d2 = Math.min(this.minZ, other.minZ);
/*  95 */     double d3 = Math.max(this.maxX, other.maxX);
/*  96 */     double d4 = Math.max(this.maxY, other.maxY);
/*  97 */     double d5 = Math.max(this.maxZ, other.maxZ);
/*  98 */     return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AxisAlignedBB fromBounds(double x1, double y1, double z1, double x2, double y2, double z2) {
/* 106 */     double d0 = Math.min(x1, x2);
/* 107 */     double d1 = Math.min(y1, y2);
/* 108 */     double d2 = Math.min(z1, z2);
/* 109 */     double d3 = Math.max(x1, x2);
/* 110 */     double d4 = Math.max(y1, y2);
/* 111 */     double d5 = Math.max(z1, z2);
/* 112 */     return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB offset(double x, double y, double z) {
/* 120 */     return new AxisAlignedBB(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double calculateXOffset(AxisAlignedBB other, double offsetX) {
/* 130 */     if (other.maxY > this.minY && other.minY < this.maxY && other.maxZ > this.minZ && other.minZ < this.maxZ) {
/*     */       
/* 132 */       if (offsetX > 0.0D && other.maxX <= this.minX) {
/*     */         
/* 134 */         double d1 = this.minX - other.maxX;
/*     */         
/* 136 */         if (d1 < offsetX)
/*     */         {
/* 138 */           offsetX = d1;
/*     */         }
/*     */       }
/* 141 */       else if (offsetX < 0.0D && other.minX >= this.maxX) {
/*     */         
/* 143 */         double d0 = this.maxX - other.minX;
/*     */         
/* 145 */         if (d0 > offsetX)
/*     */         {
/* 147 */           offsetX = d0;
/*     */         }
/*     */       } 
/*     */       
/* 151 */       return offsetX;
/*     */     } 
/*     */ 
/*     */     
/* 155 */     return offsetX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double calculateYOffset(AxisAlignedBB other, double offsetY) {
/* 166 */     if (other.maxX > this.minX && other.minX < this.maxX && other.maxZ > this.minZ && other.minZ < this.maxZ) {
/*     */       
/* 168 */       if (offsetY > 0.0D && other.maxY <= this.minY) {
/*     */         
/* 170 */         double d1 = this.minY - other.maxY;
/*     */         
/* 172 */         if (d1 < offsetY)
/*     */         {
/* 174 */           offsetY = d1;
/*     */         }
/*     */       }
/* 177 */       else if (offsetY < 0.0D && other.minY >= this.maxY) {
/*     */         
/* 179 */         double d0 = this.maxY - other.minY;
/*     */         
/* 181 */         if (d0 > offsetY)
/*     */         {
/* 183 */           offsetY = d0;
/*     */         }
/*     */       } 
/*     */       
/* 187 */       return offsetY;
/*     */     } 
/*     */ 
/*     */     
/* 191 */     return offsetY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double calculateZOffset(AxisAlignedBB other, double offsetZ) {
/* 202 */     if (other.maxX > this.minX && other.minX < this.maxX && other.maxY > this.minY && other.minY < this.maxY) {
/*     */       
/* 204 */       if (offsetZ > 0.0D && other.maxZ <= this.minZ) {
/*     */         
/* 206 */         double d1 = this.minZ - other.maxZ;
/*     */         
/* 208 */         if (d1 < offsetZ)
/*     */         {
/* 210 */           offsetZ = d1;
/*     */         }
/*     */       }
/* 213 */       else if (offsetZ < 0.0D && other.minZ >= this.maxZ) {
/*     */         
/* 215 */         double d0 = this.maxZ - other.minZ;
/*     */         
/* 217 */         if (d0 > offsetZ)
/*     */         {
/* 219 */           offsetZ = d0;
/*     */         }
/*     */       } 
/*     */       
/* 223 */       return offsetZ;
/*     */     } 
/*     */ 
/*     */     
/* 227 */     return offsetZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean intersectsWith(AxisAlignedBB other) {
/* 236 */     return (other.maxX > this.minX && other.minX < this.maxX) ? ((other.maxY > this.minY && other.minY < this.maxY) ? ((other.maxZ > this.minZ && other.minZ < this.maxZ)) : false) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVecInside(Vec3 vec) {
/* 244 */     return (vec.xCoord > this.minX && vec.xCoord < this.maxX) ? ((vec.yCoord > this.minY && vec.yCoord < this.maxY) ? ((vec.zCoord > this.minZ && vec.zCoord < this.maxZ)) : false) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAverageEdgeLength() {
/* 252 */     double d0 = this.maxX - this.minX;
/* 253 */     double d1 = this.maxY - this.minY;
/* 254 */     double d2 = this.maxZ - this.minZ;
/* 255 */     return (d0 + d1 + d2) / 3.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB contract(double x, double y, double z) {
/* 263 */     double d0 = this.minX + x;
/* 264 */     double d1 = this.minY + y;
/* 265 */     double d2 = this.minZ + z;
/* 266 */     double d3 = this.maxX - x;
/* 267 */     double d4 = this.maxY - y;
/* 268 */     double d5 = this.maxZ - z;
/* 269 */     return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */ 
/*     */   
/*     */   public MovingObjectPosition calculateIntercept(Vec3 vecA, Vec3 vecB) {
/* 274 */     Vec3 vec3 = vecA.getIntermediateWithXValue(vecB, this.minX);
/* 275 */     Vec3 vec31 = vecA.getIntermediateWithXValue(vecB, this.maxX);
/* 276 */     Vec3 vec32 = vecA.getIntermediateWithYValue(vecB, this.minY);
/* 277 */     Vec3 vec33 = vecA.getIntermediateWithYValue(vecB, this.maxY);
/* 278 */     Vec3 vec34 = vecA.getIntermediateWithZValue(vecB, this.minZ);
/* 279 */     Vec3 vec35 = vecA.getIntermediateWithZValue(vecB, this.maxZ);
/*     */     
/* 281 */     if (!isVecInYZ(vec3))
/*     */     {
/* 283 */       vec3 = null;
/*     */     }
/*     */     
/* 286 */     if (!isVecInYZ(vec31))
/*     */     {
/* 288 */       vec31 = null;
/*     */     }
/*     */     
/* 291 */     if (!isVecInXZ(vec32))
/*     */     {
/* 293 */       vec32 = null;
/*     */     }
/*     */     
/* 296 */     if (!isVecInXZ(vec33))
/*     */     {
/* 298 */       vec33 = null;
/*     */     }
/*     */     
/* 301 */     if (!isVecInXY(vec34))
/*     */     {
/* 303 */       vec34 = null;
/*     */     }
/*     */     
/* 306 */     if (!isVecInXY(vec35))
/*     */     {
/* 308 */       vec35 = null;
/*     */     }
/*     */     
/* 311 */     Vec3 vec36 = null;
/*     */     
/* 313 */     if (vec3 != null)
/*     */     {
/* 315 */       vec36 = vec3;
/*     */     }
/*     */     
/* 318 */     if (vec31 != null && (vec36 == null || vecA.squareDistanceTo(vec31) < vecA.squareDistanceTo(vec36)))
/*     */     {
/* 320 */       vec36 = vec31;
/*     */     }
/*     */     
/* 323 */     if (vec32 != null && (vec36 == null || vecA.squareDistanceTo(vec32) < vecA.squareDistanceTo(vec36)))
/*     */     {
/* 325 */       vec36 = vec32;
/*     */     }
/*     */     
/* 328 */     if (vec33 != null && (vec36 == null || vecA.squareDistanceTo(vec33) < vecA.squareDistanceTo(vec36)))
/*     */     {
/* 330 */       vec36 = vec33;
/*     */     }
/*     */     
/* 333 */     if (vec34 != null && (vec36 == null || vecA.squareDistanceTo(vec34) < vecA.squareDistanceTo(vec36)))
/*     */     {
/* 335 */       vec36 = vec34;
/*     */     }
/*     */     
/* 338 */     if (vec35 != null && (vec36 == null || vecA.squareDistanceTo(vec35) < vecA.squareDistanceTo(vec36)))
/*     */     {
/* 340 */       vec36 = vec35;
/*     */     }
/*     */     
/* 343 */     if (vec36 == null)
/*     */     {
/* 345 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 349 */     EnumFacing enumfacing = null;
/*     */     
/* 351 */     if (vec36 == vec3) {
/*     */       
/* 353 */       enumfacing = EnumFacing.WEST;
/*     */     }
/* 355 */     else if (vec36 == vec31) {
/*     */       
/* 357 */       enumfacing = EnumFacing.EAST;
/*     */     }
/* 359 */     else if (vec36 == vec32) {
/*     */       
/* 361 */       enumfacing = EnumFacing.DOWN;
/*     */     }
/* 363 */     else if (vec36 == vec33) {
/*     */       
/* 365 */       enumfacing = EnumFacing.UP;
/*     */     }
/* 367 */     else if (vec36 == vec34) {
/*     */       
/* 369 */       enumfacing = EnumFacing.NORTH;
/*     */     }
/*     */     else {
/*     */       
/* 373 */       enumfacing = EnumFacing.SOUTH;
/*     */     } 
/*     */     
/* 376 */     return new MovingObjectPosition(vec36, enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isVecInYZ(Vec3 vec) {
/* 385 */     return (vec == null) ? false : ((vec.yCoord >= this.minY && vec.yCoord <= this.maxY && vec.zCoord >= this.minZ && vec.zCoord <= this.maxZ));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isVecInXZ(Vec3 vec) {
/* 393 */     return (vec == null) ? false : ((vec.xCoord >= this.minX && vec.xCoord <= this.maxX && vec.zCoord >= this.minZ && vec.zCoord <= this.maxZ));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isVecInXY(Vec3 vec) {
/* 401 */     return (vec == null) ? false : ((vec.xCoord >= this.minX && vec.xCoord <= this.maxX && vec.yCoord >= this.minY && vec.yCoord <= this.maxY));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 406 */     return "box[" + this.minX + ", " + this.minY + ", " + this.minZ + " -> " + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNaN() {
/* 411 */     return (Double.isNaN(this.minX) || Double.isNaN(this.minY) || Double.isNaN(this.minZ) || Double.isNaN(this.maxX) || Double.isNaN(this.maxY) || Double.isNaN(this.maxZ));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\AxisAlignedBB.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */