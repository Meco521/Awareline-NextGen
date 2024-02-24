/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.data.AnimationFrame;
/*     */ import net.minecraft.client.resources.data.AnimationMetadataSection;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.SmartAnimations;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import net.optifine.util.CounterInt;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ 
/*     */ public class TextureAtlasSprite
/*     */ {
/*     */   private final String iconName;
/*  26 */   protected List<int[][]> framesTextureData = Lists.newArrayList();
/*     */   protected int[][] interpolatedFrameData;
/*     */   private AnimationMetadataSection animationMetadata;
/*     */   protected boolean rotated;
/*     */   protected int originX;
/*     */   protected int originY;
/*     */   protected int width;
/*     */   protected int height;
/*     */   private float minU;
/*     */   private float maxU;
/*     */   private float minV;
/*     */   private float maxV;
/*     */   protected int frameCounter;
/*     */   protected int tickCounter;
/*  40 */   private static String locationNameClock = "builtin/clock";
/*  41 */   private static String locationNameCompass = "builtin/compass";
/*  42 */   private int indexInMap = -1;
/*     */   public float baseU;
/*     */   public float baseV;
/*     */   public int sheetWidth;
/*     */   public int sheetHeight;
/*  47 */   public int glSpriteTextureId = -1;
/*  48 */   public TextureAtlasSprite spriteSingle = null;
/*     */   public boolean isSpriteSingle = false;
/*  50 */   public int mipmapLevels = 0;
/*  51 */   public TextureAtlasSprite spriteNormal = null;
/*  52 */   public TextureAtlasSprite spriteSpecular = null;
/*     */   public boolean isShadersSprite = false;
/*     */   public boolean isEmissive = false;
/*  55 */   public TextureAtlasSprite spriteEmissive = null;
/*  56 */   private int animationIndex = -1;
/*     */   
/*     */   private boolean animationActive = false;
/*     */   
/*     */   private TextureAtlasSprite(String p_i7_1_, boolean p_i7_2_) {
/*  61 */     this.iconName = p_i7_1_;
/*  62 */     this.isSpriteSingle = p_i7_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite(String spriteName) {
/*  67 */     this.iconName = spriteName;
/*     */     
/*  69 */     if (Config.isMultiTexture())
/*     */     {
/*  71 */       this.spriteSingle = new TextureAtlasSprite(this.iconName + ".spriteSingle", true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected static TextureAtlasSprite makeAtlasSprite(ResourceLocation spriteResourceLocation) {
/*  77 */     String s = spriteResourceLocation.toString();
/*  78 */     return locationNameClock.equals(s) ? new TextureClock(s) : (locationNameCompass.equals(s) ? new TextureCompass(s) : new TextureAtlasSprite(s));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setLocationNameClock(String clockName) {
/*  83 */     locationNameClock = clockName;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setLocationNameCompass(String compassName) {
/*  88 */     locationNameCompass = compassName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initSprite(int inX, int inY, int originInX, int originInY, boolean rotatedIn) {
/*  93 */     this.originX = originInX;
/*  94 */     this.originY = originInY;
/*  95 */     this.rotated = rotatedIn;
/*  96 */     float f = (float)(0.009999999776482582D / inX);
/*  97 */     float f1 = (float)(0.009999999776482582D / inY);
/*  98 */     this.minU = originInX / (float)inX + f;
/*  99 */     this.maxU = (originInX + this.width) / (float)inX - f;
/* 100 */     this.minV = originInY / inY + f1;
/* 101 */     this.maxV = (originInY + this.height) / inY - f1;
/* 102 */     this.baseU = Math.min(this.minU, this.maxU);
/* 103 */     this.baseV = Math.min(this.minV, this.maxV);
/*     */     
/* 105 */     if (this.spriteSingle != null)
/*     */     {
/* 107 */       this.spriteSingle.initSprite(this.width, this.height, 0, 0, false);
/*     */     }
/*     */     
/* 110 */     if (this.spriteNormal != null)
/*     */     {
/* 112 */       this.spriteNormal.copyFrom(this);
/*     */     }
/*     */     
/* 115 */     if (this.spriteSpecular != null)
/*     */     {
/* 117 */       this.spriteSpecular.copyFrom(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void copyFrom(TextureAtlasSprite atlasSpirit) {
/* 123 */     this.originX = atlasSpirit.originX;
/* 124 */     this.originY = atlasSpirit.originY;
/* 125 */     this.width = atlasSpirit.width;
/* 126 */     this.height = atlasSpirit.height;
/* 127 */     this.rotated = atlasSpirit.rotated;
/* 128 */     this.minU = atlasSpirit.minU;
/* 129 */     this.maxU = atlasSpirit.maxU;
/* 130 */     this.minV = atlasSpirit.minV;
/* 131 */     this.maxV = atlasSpirit.maxV;
/*     */     
/* 133 */     if (atlasSpirit != Config.getTextureMap().getMissingSprite())
/*     */     {
/* 135 */       this.indexInMap = atlasSpirit.indexInMap;
/*     */     }
/*     */     
/* 138 */     this.baseU = atlasSpirit.baseU;
/* 139 */     this.baseV = atlasSpirit.baseV;
/* 140 */     this.sheetWidth = atlasSpirit.sheetWidth;
/* 141 */     this.sheetHeight = atlasSpirit.sheetHeight;
/* 142 */     this.glSpriteTextureId = atlasSpirit.glSpriteTextureId;
/* 143 */     this.mipmapLevels = atlasSpirit.mipmapLevels;
/*     */     
/* 145 */     if (this.spriteSingle != null)
/*     */     {
/* 147 */       this.spriteSingle.initSprite(this.width, this.height, 0, 0, false);
/*     */     }
/*     */     
/* 150 */     this.animationIndex = atlasSpirit.animationIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOriginX() {
/* 158 */     return this.originX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOriginY() {
/* 166 */     return this.originY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIconWidth() {
/* 174 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIconHeight() {
/* 182 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinU() {
/* 190 */     return this.minU;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxU() {
/* 198 */     return this.maxU;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getInterpolatedU(double u) {
/* 206 */     float f = this.maxU - this.minU;
/* 207 */     return this.minU + f * (float)u / 16.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinV() {
/* 215 */     return this.minV;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxV() {
/* 223 */     return this.maxV;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getInterpolatedV(double v) {
/* 231 */     float f = this.maxV - this.minV;
/* 232 */     return this.minV + f * (float)v / 16.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIconName() {
/* 237 */     return this.iconName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAnimation() {
/* 242 */     if (this.animationMetadata != null) {
/*     */       
/* 244 */       this.animationActive = SmartAnimations.isActive() ? SmartAnimations.isSpriteRendered(this.animationIndex) : true;
/* 245 */       this.tickCounter++;
/*     */       
/* 247 */       if (this.tickCounter >= this.animationMetadata.getFrameTimeSingle(this.frameCounter)) {
/*     */         
/* 249 */         int i = this.animationMetadata.getFrameIndex(this.frameCounter);
/* 250 */         int j = (this.animationMetadata.getFrameCount() == 0) ? this.framesTextureData.size() : this.animationMetadata.getFrameCount();
/* 251 */         this.frameCounter = (this.frameCounter + 1) % j;
/* 252 */         this.tickCounter = 0;
/* 253 */         int k = this.animationMetadata.getFrameIndex(this.frameCounter);
/* 254 */         boolean flag = false;
/* 255 */         boolean flag1 = this.isSpriteSingle;
/*     */         
/* 257 */         if (!this.animationActive) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 262 */         if (i != k && k >= 0 && k < this.framesTextureData.size())
/*     */         {
/* 264 */           TextureUtil.uploadTextureMipmap(this.framesTextureData.get(k), this.width, this.height, this.originX, this.originY, flag, flag1);
/*     */         }
/*     */       }
/* 267 */       else if (this.animationMetadata.isInterpolate()) {
/*     */         
/* 269 */         if (!this.animationActive) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 274 */         updateAnimationInterpolated();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateAnimationInterpolated() {
/* 281 */     double d0 = 1.0D - this.tickCounter / this.animationMetadata.getFrameTimeSingle(this.frameCounter);
/* 282 */     int i = this.animationMetadata.getFrameIndex(this.frameCounter);
/* 283 */     int j = (this.animationMetadata.getFrameCount() == 0) ? this.framesTextureData.size() : this.animationMetadata.getFrameCount();
/* 284 */     int k = this.animationMetadata.getFrameIndex((this.frameCounter + 1) % j);
/*     */     
/* 286 */     if (i != k && k >= 0 && k < this.framesTextureData.size()) {
/*     */       
/* 288 */       int[][] aint = this.framesTextureData.get(i);
/* 289 */       int[][] aint1 = this.framesTextureData.get(k);
/*     */       
/* 291 */       if (this.interpolatedFrameData == null || this.interpolatedFrameData.length != aint.length)
/*     */       {
/* 293 */         this.interpolatedFrameData = new int[aint.length][];
/*     */       }
/*     */       
/* 296 */       for (int l = 0; l < aint.length; l++) {
/*     */         
/* 298 */         if (this.interpolatedFrameData[l] == null)
/*     */         {
/* 300 */           this.interpolatedFrameData[l] = new int[(aint[l]).length];
/*     */         }
/*     */         
/* 303 */         if (l < aint1.length && (aint1[l]).length == (aint[l]).length)
/*     */         {
/* 305 */           for (int i1 = 0; i1 < (aint[l]).length; i1++) {
/*     */             
/* 307 */             int j1 = aint[l][i1];
/* 308 */             int k1 = aint1[l][i1];
/* 309 */             int l1 = (int)(((j1 & 0xFF0000) >> 16) * d0 + ((k1 & 0xFF0000) >> 16) * (1.0D - d0));
/* 310 */             int i2 = (int)(((j1 & 0xFF00) >> 8) * d0 + ((k1 & 0xFF00) >> 8) * (1.0D - d0));
/* 311 */             int j2 = (int)((j1 & 0xFF) * d0 + (k1 & 0xFF) * (1.0D - d0));
/* 312 */             this.interpolatedFrameData[l][i1] = j1 & 0xFF000000 | l1 << 16 | i2 << 8 | j2;
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 317 */       TextureUtil.uploadTextureMipmap(this.interpolatedFrameData, this.width, this.height, this.originX, this.originY, false, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int[][] getFrameTextureData(int index) {
/* 323 */     return this.framesTextureData.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFrameCount() {
/* 328 */     return this.framesTextureData.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIconWidth(int newWidth) {
/* 333 */     this.width = newWidth;
/*     */     
/* 335 */     if (this.spriteSingle != null)
/*     */     {
/* 337 */       this.spriteSingle.setIconWidth(this.width);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIconHeight(int newHeight) {
/* 343 */     this.height = newHeight;
/*     */     
/* 345 */     if (this.spriteSingle != null)
/*     */     {
/* 347 */       this.spriteSingle.setIconHeight(this.height);
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadSprite(BufferedImage[] images, AnimationMetadataSection meta) {
/* 352 */     resetSprite();
/* 353 */     int i = images[0].getWidth();
/* 354 */     int j = images[0].getHeight();
/* 355 */     this.width = i;
/* 356 */     this.height = j;
/*     */     
/* 358 */     if (this.spriteSingle != null) {
/*     */       
/* 360 */       this.spriteSingle.width = this.width;
/* 361 */       this.spriteSingle.height = this.height;
/*     */     } 
/*     */     
/* 364 */     int[][] aint = new int[images.length][];
/*     */     
/* 366 */     for (int k = 0; k < images.length; k++) {
/*     */       
/* 368 */       BufferedImage bufferedimage = images[k];
/*     */       
/* 370 */       if (bufferedimage != null) {
/*     */         
/* 372 */         if (this.width >> k != bufferedimage.getWidth())
/*     */         {
/* 374 */           bufferedimage = TextureUtils.scaleImage(bufferedimage, this.width >> k);
/*     */         }
/*     */         
/* 377 */         if (k > 0 && (bufferedimage.getWidth() != i >> k || bufferedimage.getHeight() != j >> k))
/*     */         {
/* 379 */           throw new RuntimeException(String.format("Unable to load miplevel: %d, image is size: %dx%d, expected %dx%d", new Object[] { Integer.valueOf(k), Integer.valueOf(bufferedimage.getWidth()), Integer.valueOf(bufferedimage.getHeight()), Integer.valueOf(i >> k), Integer.valueOf(j >> k) }));
/*     */         }
/*     */         
/* 382 */         aint[k] = new int[bufferedimage.getWidth() * bufferedimage.getHeight()];
/* 383 */         bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), aint[k], 0, bufferedimage.getWidth());
/*     */       } 
/*     */     } 
/*     */     
/* 387 */     if (meta == null) {
/*     */       
/* 389 */       if (j != i)
/*     */       {
/* 391 */         throw new RuntimeException("broken aspect ratio and not an animation");
/*     */       }
/*     */       
/* 394 */       this.framesTextureData.add(aint);
/*     */     }
/*     */     else {
/*     */       
/* 398 */       int j1 = j / i;
/* 399 */       this.height = this.width;
/*     */       
/* 401 */       if (meta.getFrameCount() > 0) {
/*     */         
/* 403 */         Iterator<Integer> iterator = meta.getFrameIndexSet().iterator();
/*     */         
/* 405 */         while (iterator.hasNext()) {
/*     */           
/* 407 */           int i1 = ((Integer)iterator.next()).intValue();
/*     */           
/* 409 */           if (i1 >= j1)
/*     */           {
/* 411 */             throw new RuntimeException("invalid frameindex " + i1);
/*     */           }
/*     */           
/* 414 */           allocateFrameTextureData(i1);
/* 415 */           this.framesTextureData.set(i1, getFrameTextureData(aint, i, i, i1));
/*     */         } 
/*     */         
/* 418 */         this.animationMetadata = meta;
/*     */       }
/*     */       else {
/*     */         
/* 422 */         List<AnimationFrame> list = Lists.newArrayList();
/*     */         
/* 424 */         for (int j2 = 0; j2 < j1; j2++) {
/*     */           
/* 426 */           this.framesTextureData.add(getFrameTextureData(aint, i, i, j2));
/* 427 */           list.add(new AnimationFrame(j2, -1));
/*     */         } 
/*     */         
/* 430 */         this.animationMetadata = new AnimationMetadataSection(list, this.width, this.height, meta.getFrameTime(), meta.isInterpolate());
/*     */       } 
/*     */     } 
/*     */     
/* 434 */     if (!this.isShadersSprite) {
/*     */       
/* 436 */       if (Config.isShaders())
/*     */       {
/* 438 */         loadShadersSprites();
/*     */       }
/*     */       
/* 441 */       for (int k1 = 0; k1 < this.framesTextureData.size(); k1++) {
/*     */         
/* 443 */         int[][] aint1 = this.framesTextureData.get(k1);
/*     */         
/* 445 */         if (aint1 != null && !this.iconName.startsWith("minecraft:blocks/leaves_"))
/*     */         {
/* 447 */           for (int i2 = 0; i2 < aint1.length; i2++) {
/*     */             
/* 449 */             int[] aint2 = aint1[i2];
/* 450 */             fixTransparentColor(aint2);
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 455 */       if (this.spriteSingle != null)
/*     */       {
/* 457 */         this.spriteSingle.loadSprite(images, meta);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void generateMipmaps(int level) {
/* 464 */     List<int[][]> list = Lists.newArrayList();
/*     */     
/* 466 */     for (int i = 0; i < this.framesTextureData.size(); i++) {
/*     */       
/* 468 */       final int[][] aint = this.framesTextureData.get(i);
/*     */       
/* 470 */       if (aint != null) {
/*     */         
/*     */         try {
/*     */           
/* 474 */           list.add(TextureUtil.generateMipmapData(level, this.width, aint));
/*     */         }
/* 476 */         catch (Throwable throwable) {
/*     */           
/* 478 */           CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Generating mipmaps for frame");
/* 479 */           CrashReportCategory crashreportcategory = crashreport.makeCategory("Frame being iterated");
/* 480 */           crashreportcategory.addCrashSection("Frame index", Integer.valueOf(i));
/* 481 */           crashreportcategory.addCrashSectionCallable("Frame sizes", new Callable<String>()
/*     */               {
/*     */                 public String call() {
/* 484 */                   StringBuilder stringbuilder = new StringBuilder();
/*     */                   
/* 486 */                   for (int[] aint1 : aint) {
/*     */                     
/* 488 */                     if (stringbuilder.length() > 0)
/*     */                     {
/* 490 */                       stringbuilder.append(", ");
/*     */                     }
/*     */                     
/* 493 */                     stringbuilder.append((aint1 == null) ? "null" : Integer.valueOf(aint1.length));
/*     */                   } 
/*     */                   
/* 496 */                   return stringbuilder.toString();
/*     */                 }
/*     */               });
/* 499 */           throw new ReportedException(crashreport);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 504 */     setFramesTextureData(list);
/*     */     
/* 506 */     if (this.spriteSingle != null)
/*     */     {
/* 508 */       this.spriteSingle.generateMipmaps(level);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void allocateFrameTextureData(int index) {
/* 514 */     if (this.framesTextureData.size() <= index)
/*     */     {
/* 516 */       for (int i = this.framesTextureData.size(); i <= index; i++)
/*     */       {
/* 518 */         this.framesTextureData.add((int[][])null);
/*     */       }
/*     */     }
/*     */     
/* 522 */     if (this.spriteSingle != null)
/*     */     {
/* 524 */       this.spriteSingle.allocateFrameTextureData(index);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[][] getFrameTextureData(int[][] data, int rows, int columns, int p_147962_3_) {
/* 530 */     int[][] aint = new int[data.length][];
/*     */     
/* 532 */     for (int i = 0; i < data.length; i++) {
/*     */       
/* 534 */       int[] aint1 = data[i];
/*     */       
/* 536 */       if (aint1 != null) {
/*     */         
/* 538 */         aint[i] = new int[(rows >> i) * (columns >> i)];
/* 539 */         System.arraycopy(aint1, p_147962_3_ * (aint[i]).length, aint[i], 0, (aint[i]).length);
/*     */       } 
/*     */     } 
/*     */     
/* 543 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearFramesTextureData() {
/* 548 */     this.framesTextureData.clear();
/*     */     
/* 550 */     if (this.spriteSingle != null)
/*     */     {
/* 552 */       this.spriteSingle.clearFramesTextureData();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnimationMetadata() {
/* 558 */     return (this.animationMetadata != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFramesTextureData(List<int[][]> newFramesTextureData) {
/* 563 */     this.framesTextureData = newFramesTextureData;
/*     */     
/* 565 */     if (this.spriteSingle != null)
/*     */     {
/* 567 */       this.spriteSingle.setFramesTextureData(newFramesTextureData);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void resetSprite() {
/* 573 */     this.animationMetadata = null;
/* 574 */     setFramesTextureData(Lists.newArrayList());
/* 575 */     this.frameCounter = 0;
/* 576 */     this.tickCounter = 0;
/*     */     
/* 578 */     if (this.spriteSingle != null)
/*     */     {
/* 580 */       this.spriteSingle.resetSprite();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 586 */     return "TextureAtlasSprite{name='" + this.iconName + '\'' + ", frameCount=" + this.framesTextureData.size() + ", rotated=" + this.rotated + ", x=" + this.originX + ", y=" + this.originY + ", height=" + this.height + ", width=" + this.width + ", u0=" + this.minU + ", u1=" + this.maxU + ", v0=" + this.minV + ", v1=" + this.maxV + '}';
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasCustomLoader(IResourceManager p_hasCustomLoader_1_, ResourceLocation p_hasCustomLoader_2_) {
/* 591 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean load(IResourceManager p_load_1_, ResourceLocation p_load_2_) {
/* 596 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIndexInMap() {
/* 601 */     return this.indexInMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIndexInMap(int p_setIndexInMap_1_) {
/* 606 */     this.indexInMap = p_setIndexInMap_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateIndexInMap(CounterInt p_updateIndexInMap_1_) {
/* 611 */     if (this.indexInMap < 0)
/*     */     {
/* 613 */       this.indexInMap = p_updateIndexInMap_1_.nextValue();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnimationIndex() {
/* 619 */     return this.animationIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAnimationIndex(int p_setAnimationIndex_1_) {
/* 624 */     this.animationIndex = p_setAnimationIndex_1_;
/*     */     
/* 626 */     if (this.spriteNormal != null)
/*     */     {
/* 628 */       this.spriteNormal.setAnimationIndex(p_setAnimationIndex_1_);
/*     */     }
/*     */     
/* 631 */     if (this.spriteSpecular != null)
/*     */     {
/* 633 */       this.spriteSpecular.setAnimationIndex(p_setAnimationIndex_1_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAnimationActive() {
/* 639 */     return this.animationActive;
/*     */   }
/*     */ 
/*     */   
/*     */   private void fixTransparentColor(int[] p_fixTransparentColor_1_) {
/* 644 */     if (p_fixTransparentColor_1_ != null) {
/*     */       
/* 646 */       long i = 0L;
/* 647 */       long j = 0L;
/* 648 */       long k = 0L;
/* 649 */       long l = 0L;
/*     */       
/* 651 */       for (int i1 = 0; i1 < p_fixTransparentColor_1_.length; i1++) {
/*     */         
/* 653 */         int j1 = p_fixTransparentColor_1_[i1];
/* 654 */         int k1 = j1 >> 24 & 0xFF;
/*     */         
/* 656 */         if (k1 >= 16) {
/*     */           
/* 658 */           int l1 = j1 >> 16 & 0xFF;
/* 659 */           int i2 = j1 >> 8 & 0xFF;
/* 660 */           int j2 = j1 & 0xFF;
/* 661 */           i += l1;
/* 662 */           j += i2;
/* 663 */           k += j2;
/* 664 */           l++;
/*     */         } 
/*     */       } 
/*     */       
/* 668 */       if (l > 0L) {
/*     */         
/* 670 */         int l2 = (int)(i / l);
/* 671 */         int i3 = (int)(j / l);
/* 672 */         int j3 = (int)(k / l);
/* 673 */         int k3 = l2 << 16 | i3 << 8 | j3;
/*     */         
/* 675 */         for (int l3 = 0; l3 < p_fixTransparentColor_1_.length; l3++) {
/*     */           
/* 677 */           int i4 = p_fixTransparentColor_1_[l3];
/* 678 */           int k2 = i4 >> 24 & 0xFF;
/*     */           
/* 680 */           if (k2 <= 16)
/*     */           {
/* 682 */             p_fixTransparentColor_1_[l3] = k3;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double getSpriteU16(float p_getSpriteU16_1_) {
/* 691 */     float f = this.maxU - this.minU;
/* 692 */     return ((p_getSpriteU16_1_ - this.minU) / f * 16.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getSpriteV16(float p_getSpriteV16_1_) {
/* 697 */     float f = this.maxV - this.minV;
/* 698 */     return ((p_getSpriteV16_1_ - this.minV) / f * 16.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindSpriteTexture() {
/* 703 */     if (this.glSpriteTextureId < 0) {
/*     */       
/* 705 */       this.glSpriteTextureId = TextureUtil.glGenTextures();
/* 706 */       TextureUtil.allocateTextureImpl(this.glSpriteTextureId, this.mipmapLevels, this.width, this.height);
/* 707 */       TextureUtils.applyAnisotropicLevel();
/*     */     } 
/*     */     
/* 710 */     TextureUtils.bindTexture(this.glSpriteTextureId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteSpriteTexture() {
/* 715 */     if (this.glSpriteTextureId >= 0) {
/*     */       
/* 717 */       TextureUtil.deleteTexture(this.glSpriteTextureId);
/* 718 */       this.glSpriteTextureId = -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float toSingleU(float p_toSingleU_1_) {
/* 724 */     p_toSingleU_1_ -= this.baseU;
/* 725 */     float f = this.sheetWidth / this.width;
/* 726 */     p_toSingleU_1_ *= f;
/* 727 */     return p_toSingleU_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public float toSingleV(float p_toSingleV_1_) {
/* 732 */     p_toSingleV_1_ -= this.baseV;
/* 733 */     float f = this.sheetHeight / this.height;
/* 734 */     p_toSingleV_1_ *= f;
/* 735 */     return p_toSingleV_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<int[][]> getFramesTextureData() {
/* 740 */     List<int[][]> list = (List)new ArrayList<>(this.framesTextureData);
/* 741 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public AnimationMetadataSection getAnimationMetadata() {
/* 746 */     return this.animationMetadata;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAnimationMetadata(AnimationMetadataSection p_setAnimationMetadata_1_) {
/* 751 */     this.animationMetadata = p_setAnimationMetadata_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadShadersSprites() {
/* 756 */     if (Shaders.configNormalMap) {
/*     */       
/* 758 */       String s = this.iconName + "_n";
/* 759 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/* 760 */       resourcelocation = Config.getTextureMap().completeResourceLocation(resourcelocation);
/*     */       
/* 762 */       if (Config.hasResource(resourcelocation)) {
/*     */         
/* 764 */         this.spriteNormal = new TextureAtlasSprite(s);
/* 765 */         this.spriteNormal.isShadersSprite = true;
/* 766 */         this.spriteNormal.copyFrom(this);
/* 767 */         this.spriteNormal.generateMipmaps(this.mipmapLevels);
/*     */       } 
/*     */     } 
/*     */     
/* 771 */     if (Shaders.configSpecularMap) {
/*     */       
/* 773 */       String s1 = this.iconName + "_s";
/* 774 */       ResourceLocation resourcelocation1 = new ResourceLocation(s1);
/* 775 */       resourcelocation1 = Config.getTextureMap().completeResourceLocation(resourcelocation1);
/*     */       
/* 777 */       if (Config.hasResource(resourcelocation1)) {
/*     */         
/* 779 */         this.spriteSpecular = new TextureAtlasSprite(s1);
/* 780 */         this.spriteSpecular.isShadersSprite = true;
/* 781 */         this.spriteSpecular.copyFrom(this);
/* 782 */         this.spriteSpecular.generateMipmaps(this.mipmapLevels);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\texture\TextureAtlasSprite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */