/*     */ package net.optifine.model;
/*     */ 
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ public class QuadBounds
/*     */ {
/*   7 */   private float minX = Float.MAX_VALUE;
/*   8 */   private float minY = Float.MAX_VALUE;
/*   9 */   private float minZ = Float.MAX_VALUE;
/*  10 */   private float maxX = -3.4028235E38F;
/*  11 */   private float maxY = -3.4028235E38F;
/*  12 */   private float maxZ = -3.4028235E38F;
/*     */ 
/*     */   
/*     */   public QuadBounds(int[] vertexData) {
/*  16 */     int i = vertexData.length / 4;
/*     */     
/*  18 */     for (int j = 0; j < 4; j++) {
/*     */       
/*  20 */       int k = j * i;
/*  21 */       float f = Float.intBitsToFloat(vertexData[k]);
/*  22 */       float f1 = Float.intBitsToFloat(vertexData[k + 1]);
/*  23 */       float f2 = Float.intBitsToFloat(vertexData[k + 2]);
/*     */       
/*  25 */       if (this.minX > f)
/*     */       {
/*  27 */         this.minX = f;
/*     */       }
/*     */       
/*  30 */       if (this.minY > f1)
/*     */       {
/*  32 */         this.minY = f1;
/*     */       }
/*     */       
/*  35 */       if (this.minZ > f2)
/*     */       {
/*  37 */         this.minZ = f2;
/*     */       }
/*     */       
/*  40 */       if (this.maxX < f)
/*     */       {
/*  42 */         this.maxX = f;
/*     */       }
/*     */       
/*  45 */       if (this.maxY < f1)
/*     */       {
/*  47 */         this.maxY = f1;
/*     */       }
/*     */       
/*  50 */       if (this.maxZ < f2)
/*     */       {
/*  52 */         this.maxZ = f2;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMinX() {
/*  59 */     return this.minX;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMinY() {
/*  64 */     return this.minY;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMinZ() {
/*  69 */     return this.minZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaxX() {
/*  74 */     return this.maxX;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaxY() {
/*  79 */     return this.maxY;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaxZ() {
/*  84 */     return this.maxZ;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFaceQuad(EnumFacing face) {
/*     */     float f;
/*     */     float f1;
/*     */     float f2;
/*  93 */     switch (face) {
/*     */       
/*     */       case DOWN:
/*  96 */         f = this.minY;
/*  97 */         f1 = this.maxY;
/*  98 */         f2 = 0.0F;
/*     */         break;
/*     */       
/*     */       case UP:
/* 102 */         f = this.minY;
/* 103 */         f1 = this.maxY;
/* 104 */         f2 = 1.0F;
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 108 */         f = this.minZ;
/* 109 */         f1 = this.maxZ;
/* 110 */         f2 = 0.0F;
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 114 */         f = this.minZ;
/* 115 */         f1 = this.maxZ;
/* 116 */         f2 = 1.0F;
/*     */         break;
/*     */       
/*     */       case WEST:
/* 120 */         f = this.minX;
/* 121 */         f1 = this.maxX;
/* 122 */         f2 = 0.0F;
/*     */         break;
/*     */       
/*     */       case EAST:
/* 126 */         f = this.minX;
/* 127 */         f1 = this.maxX;
/* 128 */         f2 = 1.0F;
/*     */         break;
/*     */       
/*     */       default:
/* 132 */         return false;
/*     */     } 
/*     */     
/* 135 */     return (f == f2 && f1 == f2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullQuad(EnumFacing face) {
/*     */     float f;
/*     */     float f1;
/*     */     float f2;
/*     */     float f3;
/* 145 */     switch (face) {
/*     */       
/*     */       case DOWN:
/*     */       case UP:
/* 149 */         f = this.minX;
/* 150 */         f1 = this.maxX;
/* 151 */         f2 = this.minZ;
/* 152 */         f3 = this.maxZ;
/*     */         break;
/*     */       
/*     */       case NORTH:
/*     */       case SOUTH:
/* 157 */         f = this.minX;
/* 158 */         f1 = this.maxX;
/* 159 */         f2 = this.minY;
/* 160 */         f3 = this.maxY;
/*     */         break;
/*     */       
/*     */       case WEST:
/*     */       case EAST:
/* 165 */         f = this.minY;
/* 166 */         f1 = this.maxY;
/* 167 */         f2 = this.minZ;
/* 168 */         f3 = this.maxZ;
/*     */         break;
/*     */       
/*     */       default:
/* 172 */         return false;
/*     */     } 
/*     */     
/* 175 */     return (f == 0.0F && f1 == 1.0F && f2 == 0.0F && f3 == 1.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\model\QuadBounds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */