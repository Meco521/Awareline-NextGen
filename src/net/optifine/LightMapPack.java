/*     */ package net.optifine;
/*     */ 
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class LightMapPack
/*     */ {
/*     */   private final LightMap lightMap;
/*     */   private final LightMap lightMapRain;
/*     */   private final LightMap lightMapThunder;
/*  10 */   private int[] colorBuffer1 = new int[0];
/*  11 */   private int[] colorBuffer2 = new int[0];
/*     */ 
/*     */   
/*     */   public LightMapPack(LightMap lightMap, LightMap lightMapRain, LightMap lightMapThunder) {
/*  15 */     if (lightMapRain != null || lightMapThunder != null) {
/*     */       
/*  17 */       if (lightMapRain == null)
/*     */       {
/*  19 */         lightMapRain = lightMap;
/*     */       }
/*     */       
/*  22 */       if (lightMapThunder == null)
/*     */       {
/*  24 */         lightMapThunder = lightMapRain;
/*     */       }
/*     */     } 
/*     */     
/*  28 */     this.lightMap = lightMap;
/*  29 */     this.lightMapRain = lightMapRain;
/*  30 */     this.lightMapThunder = lightMapThunder;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean updateLightmap(World world, float torchFlickerX, int[] lmColors, boolean nightvision, float partialTicks) {
/*  35 */     if (this.lightMapRain == null && this.lightMapThunder == null)
/*     */     {
/*  37 */       return this.lightMap.updateLightmap(world, torchFlickerX, lmColors, nightvision);
/*     */     }
/*     */ 
/*     */     
/*  41 */     int i = world.provider.getDimensionId();
/*     */     
/*  43 */     if (i != 1 && i != -1) {
/*     */       
/*  45 */       float f = world.getRainStrength(partialTicks);
/*  46 */       float f1 = world.getThunderStrength(partialTicks);
/*  47 */       float f2 = 1.0E-4F;
/*  48 */       boolean flag = (f > f2);
/*  49 */       boolean flag1 = (f1 > f2);
/*     */       
/*  51 */       if (!flag && !flag1)
/*     */       {
/*  53 */         return this.lightMap.updateLightmap(world, torchFlickerX, lmColors, nightvision);
/*     */       }
/*     */ 
/*     */       
/*  57 */       if (f > 0.0F)
/*     */       {
/*  59 */         f1 /= f;
/*     */       }
/*     */       
/*  62 */       float f3 = 1.0F - f;
/*  63 */       float f4 = f - f1;
/*     */       
/*  65 */       if (this.colorBuffer1.length != lmColors.length) {
/*     */         
/*  67 */         this.colorBuffer1 = new int[lmColors.length];
/*  68 */         this.colorBuffer2 = new int[lmColors.length];
/*     */       } 
/*     */       
/*  71 */       int j = 0;
/*  72 */       int[][] aint = { lmColors, this.colorBuffer1, this.colorBuffer2 };
/*  73 */       float[] afloat = new float[3];
/*     */       
/*  75 */       if (f3 > f2 && this.lightMap.updateLightmap(world, torchFlickerX, aint[j], nightvision)) {
/*     */         
/*  77 */         afloat[j] = f3;
/*  78 */         j++;
/*     */       } 
/*     */       
/*  81 */       if (f4 > f2 && this.lightMapRain != null && this.lightMapRain.updateLightmap(world, torchFlickerX, aint[j], nightvision)) {
/*     */         
/*  83 */         afloat[j] = f4;
/*  84 */         j++;
/*     */       } 
/*     */       
/*  87 */       if (f1 > f2 && this.lightMapThunder != null && this.lightMapThunder.updateLightmap(world, torchFlickerX, aint[j], nightvision)) {
/*     */         
/*  89 */         afloat[j] = f1;
/*  90 */         j++;
/*     */       } 
/*     */       
/*  93 */       return (j == 2) ? blend(aint[0], afloat[0], aint[1], afloat[1]) : ((j == 3) ? blend(aint[0], afloat[0], aint[1], afloat[1], aint[2], afloat[2]) : true);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  98 */     return this.lightMap.updateLightmap(world, torchFlickerX, lmColors, nightvision);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean blend(int[] cols0, float br0, int[] cols1, float br1) {
/* 105 */     if (cols1.length != cols0.length)
/*     */     {
/* 107 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 111 */     for (int i = 0; i < cols0.length; i++) {
/*     */       
/* 113 */       int j = cols0[i];
/* 114 */       int k = j >> 16 & 0xFF;
/* 115 */       int l = j >> 8 & 0xFF;
/* 116 */       int i1 = j & 0xFF;
/* 117 */       int j1 = cols1[i];
/* 118 */       int k1 = j1 >> 16 & 0xFF;
/* 119 */       int l1 = j1 >> 8 & 0xFF;
/* 120 */       int i2 = j1 & 0xFF;
/* 121 */       int j2 = (int)(k * br0 + k1 * br1);
/* 122 */       int k2 = (int)(l * br0 + l1 * br1);
/* 123 */       int l2 = (int)(i1 * br0 + i2 * br1);
/* 124 */       cols0[i] = 0xFF000000 | j2 << 16 | k2 << 8 | l2;
/*     */     } 
/*     */     
/* 127 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean blend(int[] cols0, float br0, int[] cols1, float br1, int[] cols2, float br2) {
/* 133 */     if (cols1.length == cols0.length && cols2.length == cols0.length) {
/*     */       
/* 135 */       for (int i = 0; i < cols0.length; i++) {
/*     */         
/* 137 */         int j = cols0[i];
/* 138 */         int k = j >> 16 & 0xFF;
/* 139 */         int l = j >> 8 & 0xFF;
/* 140 */         int i1 = j & 0xFF;
/* 141 */         int j1 = cols1[i];
/* 142 */         int k1 = j1 >> 16 & 0xFF;
/* 143 */         int l1 = j1 >> 8 & 0xFF;
/* 144 */         int i2 = j1 & 0xFF;
/* 145 */         int j2 = cols2[i];
/* 146 */         int k2 = j2 >> 16 & 0xFF;
/* 147 */         int l2 = j2 >> 8 & 0xFF;
/* 148 */         int i3 = j2 & 0xFF;
/* 149 */         int j3 = (int)(k * br0 + k1 * br1 + k2 * br2);
/* 150 */         int k3 = (int)(l * br0 + l1 * br1 + l2 * br2);
/* 151 */         int l3 = (int)(i1 * br0 + i2 * br1 + i3 * br2);
/* 152 */         cols0[i] = 0xFF000000 | j3 << 16 | k3 << 8 | l3;
/*     */       } 
/*     */       
/* 155 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 159 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\LightMapPack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */