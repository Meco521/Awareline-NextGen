/*     */ package net.optifine;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.util.TextureUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Mipmaps
/*     */ {
/*     */   private final String iconName;
/*     */   private final int width;
/*     */   private final int height;
/*     */   private final int[] data;
/*     */   private final boolean direct;
/*     */   private final int[][] mipmapDatas;
/*     */   private IntBuffer[] mipmapBuffers;
/*     */   private final Dimension[] mipmapDimensions;
/*     */   
/*     */   public Mipmaps(String iconName, int width, int height, int[] data, boolean direct) {
/*  27 */     this.iconName = iconName;
/*  28 */     this.width = width;
/*  29 */     this.height = height;
/*  30 */     this.data = data;
/*  31 */     this.direct = direct;
/*  32 */     this.mipmapDimensions = makeMipmapDimensions(width, height, iconName);
/*  33 */     this.mipmapDatas = generateMipMapData(data, width, height, this.mipmapDimensions);
/*     */     
/*  35 */     if (direct)
/*     */     {
/*  37 */       this.mipmapBuffers = makeMipmapBuffers(this.mipmapDimensions, this.mipmapDatas);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Dimension[] makeMipmapDimensions(int width, int height, String iconName) {
/*  43 */     int i = TextureUtils.ceilPowerOfTwo(width);
/*  44 */     int j = TextureUtils.ceilPowerOfTwo(height);
/*     */     
/*  46 */     if (i == width && j == height) {
/*     */       
/*  48 */       List<Dimension> list = new ArrayList();
/*  49 */       int k = i;
/*  50 */       int l = j;
/*     */ 
/*     */       
/*     */       while (true) {
/*  54 */         k /= 2;
/*  55 */         l /= 2;
/*     */         
/*  57 */         if (k <= 0 && l <= 0) {
/*     */           
/*  59 */           Dimension[] adimension = (Dimension[])list.toArray((Object[])new Dimension[0]);
/*  60 */           return adimension;
/*     */         } 
/*     */         
/*  63 */         if (k <= 0)
/*     */         {
/*  65 */           k = 1;
/*     */         }
/*     */         
/*  68 */         if (l <= 0)
/*     */         {
/*  70 */           l = 1;
/*     */         }
/*     */         
/*  73 */         int i1 = k * l * 4;
/*  74 */         Dimension dimension = new Dimension(k, l);
/*  75 */         list.add(dimension);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  80 */     Config.warn("Mipmaps not possible (power of 2 dimensions needed), texture: " + iconName + ", dim: " + width + "x" + height);
/*  81 */     return new Dimension[0];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[][] generateMipMapData(int[] data, int width, int height, Dimension[] mipmapDimensions) {
/*  87 */     int[] aint = data;
/*  88 */     int i = width;
/*  89 */     boolean flag = true;
/*  90 */     int[][] aint1 = new int[mipmapDimensions.length][];
/*     */     
/*  92 */     for (int j = 0; j < mipmapDimensions.length; j++) {
/*     */       
/*  94 */       Dimension dimension = mipmapDimensions[j];
/*  95 */       int k = dimension.width;
/*  96 */       int l = dimension.height;
/*  97 */       int[] aint2 = new int[k * l];
/*  98 */       aint1[j] = aint2;
/*  99 */       int i1 = j + 1;
/*     */       
/* 101 */       if (flag)
/*     */       {
/* 103 */         for (int j1 = 0; j1 < k; j1++) {
/*     */           
/* 105 */           for (int k1 = 0; k1 < l; k1++) {
/*     */             
/* 107 */             int l1 = aint[(j1 << 1) + (k1 << 1) * i];
/* 108 */             int i2 = aint[(j1 << 1) + 1 + (k1 << 1) * i];
/* 109 */             int j2 = aint[(j1 << 1) + 1 + ((k1 << 1) + 1) * i];
/* 110 */             int k2 = aint[(j1 << 1) + ((k1 << 1) + 1) * i];
/* 111 */             int l2 = alphaBlend(l1, i2, j2, k2);
/* 112 */             aint2[j1 + k1 * k] = l2;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 117 */       aint = aint2;
/* 118 */       i = k;
/*     */       
/* 120 */       if (k <= 1 || l <= 1)
/*     */       {
/* 122 */         flag = false;
/*     */       }
/*     */     } 
/*     */     
/* 126 */     return aint1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int alphaBlend(int c1, int c2, int c3, int c4) {
/* 131 */     int i = alphaBlend(c1, c2);
/* 132 */     int j = alphaBlend(c3, c4);
/* 133 */     int k = alphaBlend(i, j);
/* 134 */     return k;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int alphaBlend(int c1, int c2) {
/* 139 */     int i = (c1 & 0xFF000000) >> 24 & 0xFF;
/* 140 */     int j = (c2 & 0xFF000000) >> 24 & 0xFF;
/* 141 */     int k = (i + j) / 2;
/*     */     
/* 143 */     if (i == 0 && j == 0) {
/*     */       
/* 145 */       i = 1;
/* 146 */       j = 1;
/*     */     }
/*     */     else {
/*     */       
/* 150 */       if (i == 0) {
/*     */         
/* 152 */         c1 = c2;
/* 153 */         k /= 2;
/*     */       } 
/*     */       
/* 156 */       if (j == 0) {
/*     */         
/* 158 */         c2 = c1;
/* 159 */         k /= 2;
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     int l = (c1 >> 16 & 0xFF) * i;
/* 164 */     int i1 = (c1 >> 8 & 0xFF) * i;
/* 165 */     int j1 = (c1 & 0xFF) * i;
/* 166 */     int k1 = (c2 >> 16 & 0xFF) * j;
/* 167 */     int l1 = (c2 >> 8 & 0xFF) * j;
/* 168 */     int i2 = (c2 & 0xFF) * j;
/* 169 */     int j2 = (l + k1) / (i + j);
/* 170 */     int k2 = (i1 + l1) / (i + j);
/* 171 */     int l2 = (j1 + i2) / (i + j);
/* 172 */     return k << 24 | j2 << 16 | k2 << 8 | l2;
/*     */   }
/*     */ 
/*     */   
/*     */   private int averageColor(int i, int j) {
/* 177 */     int k = (i & 0xFF000000) >> 24 & 0xFF;
/* 178 */     int p = (j & 0xFF000000) >> 24 & 0xFF;
/* 179 */     return (k + j >> 1 << 24) + ((k & 0xFEFEFE) + (p & 0xFEFEFE) >> 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IntBuffer[] makeMipmapBuffers(Dimension[] mipmapDimensions, int[][] mipmapDatas) {
/* 184 */     if (mipmapDimensions == null)
/*     */     {
/* 186 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 190 */     IntBuffer[] aintbuffer = new IntBuffer[mipmapDimensions.length];
/*     */     
/* 192 */     for (int i = 0; i < mipmapDimensions.length; i++) {
/*     */       
/* 194 */       Dimension dimension = mipmapDimensions[i];
/* 195 */       int j = dimension.width * dimension.height;
/* 196 */       IntBuffer intbuffer = GLAllocation.createDirectIntBuffer(j);
/* 197 */       int[] aint = mipmapDatas[i];
/* 198 */       intbuffer.clear();
/* 199 */       intbuffer.put(aint);
/* 200 */       intbuffer.clear();
/* 201 */       aintbuffer[i] = intbuffer;
/*     */     } 
/*     */     
/* 204 */     return aintbuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void allocateMipmapTextures(int width, int height, String name) {
/* 210 */     Dimension[] adimension = makeMipmapDimensions(width, height, name);
/*     */     
/* 212 */     for (int i = 0; i < adimension.length; i++) {
/*     */       
/* 214 */       Dimension dimension = adimension[i];
/* 215 */       int j = dimension.width;
/* 216 */       int k = dimension.height;
/* 217 */       int l = i + 1;
/* 218 */       GL11.glTexImage2D(3553, l, 6408, j, k, 0, 32993, 33639, (IntBuffer)null);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\Mipmaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */