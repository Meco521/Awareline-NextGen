/*     */ package net.optifine;
/*     */ 
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class LightMap
/*     */ {
/*   8 */   private CustomColormap lightMapRgb = null;
/*   9 */   private final float[][] sunRgbs = new float[16][3];
/*  10 */   private final float[][] torchRgbs = new float[16][3];
/*     */ 
/*     */   
/*     */   public LightMap(CustomColormap lightMapRgb) {
/*  14 */     this.lightMapRgb = lightMapRgb;
/*     */   }
/*     */ 
/*     */   
/*     */   public CustomColormap getColormap() {
/*  19 */     return this.lightMapRgb;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean updateLightmap(World world, float torchFlickerX, int[] lmColors, boolean nightvision) {
/*  24 */     if (this.lightMapRgb == null)
/*     */     {
/*  26 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  30 */     int i = this.lightMapRgb.getHeight();
/*     */     
/*  32 */     if (nightvision && i < 64)
/*     */     {
/*  34 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  38 */     int j = this.lightMapRgb.getWidth();
/*     */     
/*  40 */     if (j < 16) {
/*     */       
/*  42 */       warn("Invalid lightmap width: " + j);
/*  43 */       this.lightMapRgb = null;
/*  44 */       return false;
/*     */     } 
/*     */ 
/*     */     
/*  48 */     int k = 0;
/*     */     
/*  50 */     if (nightvision)
/*     */     {
/*  52 */       k = j * 16 * 2;
/*     */     }
/*     */     
/*  55 */     float f = 1.1666666F * (world.getSunBrightness(1.0F) - 0.2F);
/*     */     
/*  57 */     if (world.getLastLightningBolt() > 0)
/*     */     {
/*  59 */       f = 1.0F;
/*     */     }
/*     */     
/*  62 */     f = Config.limitTo1(f);
/*  63 */     float f1 = f * (j - 1);
/*  64 */     float f2 = Config.limitTo1(torchFlickerX + 0.5F) * (j - 1);
/*  65 */     float f3 = Config.limitTo1((Config.getGameSettings()).gammaSetting);
/*  66 */     boolean flag = (f3 > 1.0E-4F);
/*  67 */     float[][] afloat = this.lightMapRgb.getColorsRgb();
/*  68 */     getLightMapColumn(afloat, f1, k, j, this.sunRgbs);
/*  69 */     getLightMapColumn(afloat, f2, k + 16 * j, j, this.torchRgbs);
/*  70 */     float[] afloat1 = new float[3];
/*     */     
/*  72 */     for (int l = 0; l < 16; l++) {
/*     */       
/*  74 */       for (int i1 = 0; i1 < 16; i1++) {
/*     */         
/*  76 */         for (int j1 = 0; j1 < 3; j1++) {
/*     */           
/*  78 */           float f4 = Config.limitTo1(this.sunRgbs[l][j1] + this.torchRgbs[i1][j1]);
/*     */           
/*  80 */           if (flag) {
/*     */             
/*  82 */             float f5 = 1.0F - f4;
/*  83 */             f5 = 1.0F - f5 * f5 * f5 * f5;
/*  84 */             f4 = f3 * f5 + (1.0F - f3) * f4;
/*     */           } 
/*     */           
/*  87 */           afloat1[j1] = f4;
/*     */         } 
/*     */         
/*  90 */         int k1 = (int)(afloat1[0] * 255.0F);
/*  91 */         int l1 = (int)(afloat1[1] * 255.0F);
/*  92 */         int i2 = (int)(afloat1[2] * 255.0F);
/*  93 */         lmColors[(l << 4) + i1] = 0xFF000000 | k1 << 16 | l1 << 8 | i2;
/*     */       } 
/*     */     } 
/*     */     
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getLightMapColumn(float[][] origMap, float x, int offset, int width, float[][] colRgb) {
/* 105 */     int i = (int)Math.floor(x);
/* 106 */     int j = (int)Math.ceil(x);
/*     */     
/* 108 */     if (i == j) {
/*     */       
/* 110 */       for (int i1 = 0; i1 < 16; i1++)
/*     */       {
/* 112 */         float[] afloat3 = origMap[offset + i1 * width + i];
/* 113 */         float[] afloat4 = colRgb[i1];
/*     */         
/* 115 */         System.arraycopy(afloat3, 0, afloat4, 0, 3);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 120 */       float f = 1.0F - x - i;
/* 121 */       float f1 = 1.0F - j - x;
/*     */       
/* 123 */       for (int k = 0; k < 16; k++) {
/*     */         
/* 125 */         float[] afloat = origMap[offset + k * width + i];
/* 126 */         float[] afloat1 = origMap[offset + k * width + j];
/* 127 */         float[] afloat2 = colRgb[k];
/*     */         
/* 129 */         for (int l = 0; l < 3; l++)
/*     */         {
/* 131 */           afloat2[l] = afloat[l] * f + afloat1[l] * f1;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void dbg(String str) {
/* 139 */     Config.dbg("CustomColors: " + str);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void warn(String str) {
/* 144 */     Config.warn("CustomColors: " + str);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\LightMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */