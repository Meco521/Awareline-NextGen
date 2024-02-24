/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.IntBuffer;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.Mipmaps;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextureUtil
/*     */ {
/*  26 */   private static final Logger logger = LogManager.getLogger();
/*  27 */   private static final IntBuffer dataBuffer = GLAllocation.createDirectIntBuffer(4194304);
/*  28 */   public static final DynamicTexture missingTexture = new DynamicTexture(16, 16);
/*  29 */   public static final int[] missingTextureData = missingTexture.getTextureData();
/*     */   private static final int[] mipmapBuffer;
/*  31 */   private static final int[] dataArray = new int[4194304];
/*     */ 
/*     */   
/*     */   public static int glGenTextures() {
/*  35 */     return GlStateManager.generateTexture();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void deleteTexture(int textureId) {
/*  40 */     GlStateManager.deleteTexture(textureId);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int uploadTextureImage(int p_110987_0_, BufferedImage p_110987_1_) {
/*  45 */     return uploadTextureImageAllocate(p_110987_0_, p_110987_1_, false, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void uploadTexture(int textureId, int[] p_110988_1_, int p_110988_2_, int p_110988_3_) {
/*  50 */     bindTexture(textureId);
/*  51 */     uploadTextureSub(0, p_110988_1_, p_110988_2_, p_110988_3_, 0, 0, false, false, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[][] generateMipmapData(int p_147949_0_, int p_147949_1_, int[][] p_147949_2_) {
/*  56 */     int[][] aint = new int[p_147949_0_ + 1][];
/*  57 */     aint[0] = p_147949_2_[0];
/*     */     
/*  59 */     if (p_147949_0_ > 0) {
/*     */       
/*  61 */       boolean flag = false;
/*     */       
/*  63 */       for (int i = 0; i < (p_147949_2_[0]).length; i++) {
/*     */         
/*  65 */         if (p_147949_2_[0][i] >> 24 == 0) {
/*     */           
/*  67 */           flag = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*  72 */       for (int l1 = 1; l1 <= p_147949_0_; l1++) {
/*     */         
/*  74 */         if (p_147949_2_[l1] != null) {
/*     */           
/*  76 */           aint[l1] = p_147949_2_[l1];
/*     */         }
/*     */         else {
/*     */           
/*  80 */           int[] aint1 = aint[l1 - 1];
/*  81 */           int[] aint2 = new int[aint1.length >> 2];
/*  82 */           int j = p_147949_1_ >> l1;
/*  83 */           int k = aint2.length / j;
/*  84 */           int l = j << 1;
/*     */           
/*  86 */           for (int i1 = 0; i1 < j; i1++) {
/*     */             
/*  88 */             for (int j1 = 0; j1 < k; j1++) {
/*     */               
/*  90 */               int k1 = 2 * (i1 + j1 * l);
/*  91 */               aint2[i1 + j1 * j] = blendColors(aint1[k1], aint1[k1 + 1], aint1[k1 + l], aint1[k1 + 1 + l], flag);
/*     */             } 
/*     */           } 
/*     */           
/*  95 */           aint[l1] = aint2;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int blendColors(int p_147943_0_, int p_147943_1_, int p_147943_2_, int p_147943_3_, boolean p_147943_4_) {
/* 105 */     return Mipmaps.alphaBlend(p_147943_0_, p_147943_1_, p_147943_2_, p_147943_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int blendColorComponent(int p_147944_0_, int p_147944_1_, int p_147944_2_, int p_147944_3_, int p_147944_4_) {
/* 110 */     float f = (float)Math.pow(((p_147944_0_ >> p_147944_4_ & 0xFF) / 255.0F), 2.2D);
/* 111 */     float f1 = (float)Math.pow(((p_147944_1_ >> p_147944_4_ & 0xFF) / 255.0F), 2.2D);
/* 112 */     float f2 = (float)Math.pow(((p_147944_2_ >> p_147944_4_ & 0xFF) / 255.0F), 2.2D);
/* 113 */     float f3 = (float)Math.pow(((p_147944_3_ >> p_147944_4_ & 0xFF) / 255.0F), 2.2D);
/* 114 */     float f4 = (float)Math.pow((f + f1 + f2 + f3) * 0.25D, 0.45454545454545453D);
/* 115 */     return (int)(f4 * 255.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void uploadTextureMipmap(int[][] p_147955_0_, int p_147955_1_, int p_147955_2_, int p_147955_3_, int p_147955_4_, boolean p_147955_5_, boolean p_147955_6_) {
/* 120 */     for (int i = 0; i < p_147955_0_.length; i++) {
/*     */       
/* 122 */       int[] aint = p_147955_0_[i];
/* 123 */       uploadTextureSub(i, aint, p_147955_1_ >> i, p_147955_2_ >> i, p_147955_3_ >> i, p_147955_4_ >> i, p_147955_5_, p_147955_6_, (p_147955_0_.length > 1));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void uploadTextureSub(int p_147947_0_, int[] p_147947_1_, int p_147947_2_, int p_147947_3_, int p_147947_4_, int p_147947_5_, boolean p_147947_6_, boolean p_147947_7_, boolean p_147947_8_) {
/* 129 */     int i = 4194304 / p_147947_2_;
/* 130 */     setTextureBlurMipmap(p_147947_6_, p_147947_8_);
/* 131 */     setTextureClamped(p_147947_7_);
/*     */     
/*     */     int k;
/* 134 */     for (k = 0; k < p_147947_2_ * p_147947_3_; k += p_147947_2_ * j) {
/*     */       
/* 136 */       int l = k / p_147947_2_;
/* 137 */       int j = Math.min(i, p_147947_3_ - l);
/* 138 */       int i1 = p_147947_2_ * j;
/* 139 */       copyToBufferPos(p_147947_1_, k, i1);
/* 140 */       GL11.glTexSubImage2D(3553, p_147947_0_, p_147947_4_, p_147947_5_ + l, p_147947_2_, j, 32993, 33639, dataBuffer);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int uploadTextureImageAllocate(int p_110989_0_, BufferedImage p_110989_1_, boolean p_110989_2_, boolean p_110989_3_) {
/* 146 */     allocateTexture(p_110989_0_, p_110989_1_.getWidth(), p_110989_1_.getHeight());
/* 147 */     return uploadTextureImageSub(p_110989_0_, p_110989_1_, 0, 0, p_110989_2_, p_110989_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void allocateTexture(int p_110991_0_, int p_110991_1_, int p_110991_2_) {
/* 152 */     allocateTextureImpl(p_110991_0_, 0, p_110991_1_, p_110991_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void allocateTextureImpl(int p_180600_0_, int p_180600_1_, int p_180600_2_, int p_180600_3_) {
/* 157 */     Object<TextureUtil> object = (Object<TextureUtil>)TextureUtil.class;
/*     */     
/* 159 */     if (Reflector.SplashScreen.exists())
/*     */     {
/* 161 */       object = (Object<TextureUtil>)Reflector.SplashScreen.getTargetClass();
/*     */     }
/*     */     
/* 164 */     synchronized (object) {
/*     */       
/* 166 */       deleteTexture(p_180600_0_);
/* 167 */       bindTexture(p_180600_0_);
/*     */     } 
/*     */     
/* 170 */     if (p_180600_1_ >= 0) {
/*     */       
/* 172 */       GL11.glTexParameteri(3553, 33085, p_180600_1_);
/* 173 */       GL11.glTexParameterf(3553, 33082, 0.0F);
/* 174 */       GL11.glTexParameterf(3553, 33083, p_180600_1_);
/* 175 */       GL11.glTexParameterf(3553, 34049, 0.0F);
/*     */     } 
/*     */     
/* 178 */     for (int i = 0; i <= p_180600_1_; i++)
/*     */     {
/* 180 */       GL11.glTexImage2D(3553, i, 6408, p_180600_2_ >> i, p_180600_3_ >> i, 0, 32993, 33639, (IntBuffer)null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static int uploadTextureImageSub(int textureId, BufferedImage p_110995_1_, int p_110995_2_, int p_110995_3_, boolean p_110995_4_, boolean p_110995_5_) {
/* 186 */     bindTexture(textureId);
/* 187 */     uploadTextureImageSubImpl(p_110995_1_, p_110995_2_, p_110995_3_, p_110995_4_, p_110995_5_);
/* 188 */     return textureId;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void uploadTextureImageSubImpl(BufferedImage p_110993_0_, int p_110993_1_, int p_110993_2_, boolean p_110993_3_, boolean p_110993_4_) {
/* 193 */     int i = p_110993_0_.getWidth();
/* 194 */     int j = p_110993_0_.getHeight();
/* 195 */     int k = 4194304 / i;
/* 196 */     int[] aint = dataArray;
/* 197 */     setTextureBlurred(p_110993_3_);
/* 198 */     setTextureClamped(p_110993_4_);
/*     */     int l;
/* 200 */     for (l = 0; l < i * j; l += i * k) {
/*     */       
/* 202 */       int i1 = l / i;
/* 203 */       int j1 = Math.min(k, j - i1);
/* 204 */       int k1 = i * j1;
/* 205 */       p_110993_0_.getRGB(0, i1, i, j1, aint, 0, i);
/* 206 */       copyToBuffer(aint, k1);
/* 207 */       GL11.glTexSubImage2D(3553, 0, p_110993_1_, p_110993_2_ + i1, i, j1, 32993, 33639, dataBuffer);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setTextureClamped(boolean p_110997_0_) {
/* 213 */     if (p_110997_0_) {
/*     */       
/* 215 */       GL11.glTexParameteri(3553, 10242, 33071);
/* 216 */       GL11.glTexParameteri(3553, 10243, 33071);
/*     */     }
/*     */     else {
/*     */       
/* 220 */       GL11.glTexParameteri(3553, 10242, 10497);
/* 221 */       GL11.glTexParameteri(3553, 10243, 10497);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void setTextureBlurred(boolean p_147951_0_) {
/* 227 */     setTextureBlurMipmap(p_147951_0_, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setTextureBlurMipmap(boolean p_147954_0_, boolean p_147954_1_) {
/* 232 */     if (p_147954_0_) {
/*     */       
/* 234 */       GL11.glTexParameteri(3553, 10241, p_147954_1_ ? 9987 : 9729);
/* 235 */       GL11.glTexParameteri(3553, 10240, 9729);
/*     */     }
/*     */     else {
/*     */       
/* 239 */       int i = Config.getMipmapType();
/* 240 */       GL11.glTexParameteri(3553, 10241, p_147954_1_ ? i : 9728);
/* 241 */       GL11.glTexParameteri(3553, 10240, 9728);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void copyToBuffer(int[] p_110990_0_, int p_110990_1_) {
/* 247 */     copyToBufferPos(p_110990_0_, 0, p_110990_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void copyToBufferPos(int[] p_110994_0_, int p_110994_1_, int p_110994_2_) {
/* 252 */     int[] aint = p_110994_0_;
/*     */     
/* 254 */     if ((Minecraft.getMinecraft()).gameSettings.anaglyph)
/*     */     {
/* 256 */       aint = updateAnaglyph(p_110994_0_);
/*     */     }
/*     */     
/* 259 */     dataBuffer.clear();
/* 260 */     dataBuffer.put(aint, p_110994_1_, p_110994_2_);
/* 261 */     dataBuffer.position(0).limit(p_110994_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindTexture(int p_94277_0_) {
/* 266 */     GlStateManager.bindTexture(p_94277_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] readImageData(IResourceManager resourceManager, ResourceLocation imageLocation) throws IOException {
/* 271 */     BufferedImage bufferedimage = readBufferedImage(resourceManager.getResource(imageLocation).getInputStream());
/*     */     
/* 273 */     if (bufferedimage == null)
/*     */     {
/* 275 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 279 */     int i = bufferedimage.getWidth();
/* 280 */     int j = bufferedimage.getHeight();
/* 281 */     int[] aint = new int[i * j];
/* 282 */     bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
/* 283 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static BufferedImage readBufferedImage(InputStream imageStream) throws IOException {
/*     */     BufferedImage bufferedimage;
/* 289 */     if (imageStream == null)
/*     */     {
/* 291 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 299 */       bufferedimage = ImageIO.read(imageStream);
/*     */     }
/*     */     finally {
/*     */       
/* 303 */       IOUtils.closeQuietly(imageStream);
/*     */     } 
/*     */     
/* 306 */     return bufferedimage;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] updateAnaglyph(int[] p_110985_0_) {
/* 312 */     int[] aint = new int[p_110985_0_.length];
/*     */     
/* 314 */     for (int i = 0; i < p_110985_0_.length; i++)
/*     */     {
/* 316 */       aint[i] = anaglyphColor(p_110985_0_[i]);
/*     */     }
/*     */     
/* 319 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int anaglyphColor(int p_177054_0_) {
/* 324 */     int i = p_177054_0_ >> 24 & 0xFF;
/* 325 */     int j = p_177054_0_ >> 16 & 0xFF;
/* 326 */     int k = p_177054_0_ >> 8 & 0xFF;
/* 327 */     int l = p_177054_0_ & 0xFF;
/* 328 */     int i1 = (j * 30 + k * 59 + l * 11) / 100;
/* 329 */     int j1 = (j * 30 + k * 70) / 100;
/* 330 */     int k1 = (j * 30 + l * 70) / 100;
/* 331 */     return i << 24 | i1 << 16 | j1 << 8 | k1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void processPixelValues(int[] p_147953_0_, int p_147953_1_, int p_147953_2_) {
/* 336 */     int[] aint = new int[p_147953_1_];
/* 337 */     int i = p_147953_2_ / 2;
/*     */     
/* 339 */     for (int j = 0; j < i; j++) {
/*     */       
/* 341 */       System.arraycopy(p_147953_0_, j * p_147953_1_, aint, 0, p_147953_1_);
/* 342 */       System.arraycopy(p_147953_0_, (p_147953_2_ - 1 - j) * p_147953_1_, p_147953_0_, j * p_147953_1_, p_147953_1_);
/* 343 */       System.arraycopy(aint, 0, p_147953_0_, (p_147953_2_ - 1 - j) * p_147953_1_, p_147953_1_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 349 */     int i = -16777216;
/* 350 */     int j = -524040;
/* 351 */     int[] aint = { -524040, -524040, -524040, -524040, -524040, -524040, -524040, -524040 };
/* 352 */     int[] aint1 = { -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216 };
/* 353 */     int k = aint.length;
/*     */     
/* 355 */     for (int l = 0; l < 16; l++) {
/*     */       
/* 357 */       System.arraycopy((l < k) ? aint : aint1, 0, missingTextureData, 16 * l, k);
/* 358 */       System.arraycopy((l < k) ? aint1 : aint, 0, missingTextureData, 16 * l + k, k);
/*     */     } 
/*     */     
/* 361 */     missingTexture.updateDynamicTexture();
/* 362 */     mipmapBuffer = new int[4];
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\texture\TextureUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */