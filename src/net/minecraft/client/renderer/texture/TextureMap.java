/*     */ package net.minecraft.client.renderer.texture;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.data.AnimationMetadataSection;
/*     */ import net.minecraft.client.resources.data.TextureMetadataSection;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.ConnectedTextures;
/*     */ import net.optifine.CustomItems;
/*     */ import net.optifine.SmartAnimations;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorForge;
/*     */ import net.optifine.shaders.ShadersTex;
/*     */ import net.optifine.util.CounterInt;
/*     */ import net.optifine.util.TextureUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ 
/*     */ public class TextureMap extends AbstractTexture implements ITickableTextureObject {
/*  36 */   private static final boolean ENABLE_SKIP = Boolean.parseBoolean(System.getProperty("fml.skipFirstTextureLoad", "true"));
/*  37 */   private static final Logger logger = LogManager.getLogger();
/*  38 */   public static final ResourceLocation LOCATION_MISSING_TEXTURE = new ResourceLocation("missingno");
/*  39 */   public static final ResourceLocation locationBlocksTexture = new ResourceLocation("textures/atlas/blocks.png");
/*     */   
/*     */   private final List<TextureAtlasSprite> listAnimatedSprites;
/*     */   private final Map<String, TextureAtlasSprite> mapRegisteredSprites;
/*     */   private final Map<String, TextureAtlasSprite> mapUploadedSprites;
/*     */   private final String basePath;
/*     */   private final IIconCreator iconCreator;
/*     */   private int mipmapLevels;
/*     */   private final TextureAtlasSprite missingImage;
/*     */   private boolean skipFirst;
/*     */   private TextureAtlasSprite[] iconGrid;
/*     */   private int iconGridSize;
/*     */   private int iconGridCountX;
/*     */   private int iconGridCountY;
/*     */   private double iconGridSizeU;
/*     */   private double iconGridSizeV;
/*     */   private final CounterInt counterIndexInMap;
/*     */   public int atlasWidth;
/*     */   public int atlasHeight;
/*     */   private int countAnimationsActive;
/*     */   private int frameCountAnimations;
/*     */   
/*     */   public TextureMap(String p_i46099_1_) {
/*  62 */     this(p_i46099_1_, (IIconCreator)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureMap(String p_i5_1_, boolean p_i5_2_) {
/*  67 */     this(p_i5_1_, (IIconCreator)null, p_i5_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureMap(String p_i46100_1_, IIconCreator iconCreatorIn) {
/*  72 */     this(p_i46100_1_, iconCreatorIn, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureMap(String p_i6_1_, IIconCreator p_i6_2_, boolean p_i6_3_) {
/*  77 */     this.skipFirst = false;
/*  78 */     this.iconGrid = null;
/*  79 */     this.iconGridSize = -1;
/*  80 */     this.iconGridCountX = -1;
/*  81 */     this.iconGridCountY = -1;
/*  82 */     this.iconGridSizeU = -1.0D;
/*  83 */     this.iconGridSizeV = -1.0D;
/*  84 */     this.counterIndexInMap = new CounterInt(0);
/*  85 */     this.atlasWidth = 0;
/*  86 */     this.atlasHeight = 0;
/*  87 */     this.listAnimatedSprites = Lists.newArrayList();
/*  88 */     this.mapRegisteredSprites = Maps.newHashMap();
/*  89 */     this.mapUploadedSprites = Maps.newHashMap();
/*  90 */     this.missingImage = new TextureAtlasSprite("missingno");
/*  91 */     this.basePath = p_i6_1_;
/*  92 */     this.iconCreator = p_i6_2_;
/*  93 */     this.skipFirst = (p_i6_3_ && ENABLE_SKIP);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initMissingImage() {
/*  98 */     int i = getMinSpriteSize();
/*  99 */     int[] aint = getMissingImageData(i);
/* 100 */     this.missingImage.setIconWidth(i);
/* 101 */     this.missingImage.setIconHeight(i);
/* 102 */     int[][] aint1 = new int[this.mipmapLevels + 1][];
/* 103 */     aint1[0] = aint;
/* 104 */     this.missingImage.setFramesTextureData(Lists.newArrayList((Object[])new int[][][] { aint1 }));
/* 105 */     this.missingImage.setIndexInMap(this.counterIndexInMap.nextValue());
/*     */   }
/*     */   
/*     */   public void loadTexture(IResourceManager resourceManager) {
/* 109 */     if (this.iconCreator != null)
/*     */     {
/* 111 */       loadSprites(resourceManager, this.iconCreator);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadSprites(IResourceManager resourceManager, IIconCreator p_174943_2_) {
/* 117 */     this.mapRegisteredSprites.clear();
/* 118 */     this.counterIndexInMap.reset();
/* 119 */     p_174943_2_.registerSprites(this);
/*     */     
/* 121 */     if (this.mipmapLevels >= 4) {
/*     */       
/* 123 */       this.mipmapLevels = detectMaxMipmapLevel(this.mapRegisteredSprites, resourceManager);
/* 124 */       Config.log("Mipmap levels: " + this.mipmapLevels);
/*     */     } 
/*     */     
/* 127 */     initMissingImage();
/* 128 */     deleteGlTexture();
/* 129 */     loadTextureAtlas(resourceManager);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadTextureAtlas(IResourceManager resourceManager) {
/* 134 */     Config.dbg("Multitexture: " + Config.isMultiTexture());
/*     */     
/* 136 */     if (Config.isMultiTexture())
/*     */     {
/* 138 */       for (TextureAtlasSprite textureatlassprite : this.mapUploadedSprites.values())
/*     */       {
/* 140 */         textureatlassprite.deleteSpriteTexture();
/*     */       }
/*     */     }
/*     */     
/* 144 */     ConnectedTextures.updateIcons(this);
/* 145 */     CustomItems.updateIcons(this);
/* 146 */     BetterGrass.updateIcons(this);
/* 147 */     int i2 = TextureUtils.getGLMaximumTextureSize();
/* 148 */     Stitcher stitcher = new Stitcher(i2, i2, true, 0, this.mipmapLevels);
/* 149 */     this.mapUploadedSprites.clear();
/* 150 */     this.listAnimatedSprites.clear();
/* 151 */     int i = Integer.MAX_VALUE;
/* 152 */     Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPre, new Object[] { this });
/* 153 */     int j = getMinSpriteSize();
/* 154 */     this.iconGridSize = j;
/* 155 */     int k = 1 << this.mipmapLevels;
/* 156 */     int l = 0;
/* 157 */     int i1 = 0;
/* 158 */     Iterator<Map.Entry<String, TextureAtlasSprite>> iterator = this.mapRegisteredSprites.entrySet().iterator();
/*     */ 
/*     */ 
/*     */     
/* 162 */     while (iterator.hasNext()) {
/*     */       
/* 164 */       Map.Entry<String, TextureAtlasSprite> entry = iterator.next();
/*     */       
/* 166 */       if (!this.skipFirst) {
/*     */         
/* 168 */         TextureAtlasSprite textureatlassprite3 = entry.getValue();
/* 169 */         ResourceLocation resourcelocation1 = new ResourceLocation(textureatlassprite3.getIconName());
/* 170 */         ResourceLocation resourcelocation2 = completeResourceLocation(resourcelocation1, 0);
/* 171 */         textureatlassprite3.updateIndexInMap(this.counterIndexInMap);
/*     */         
/* 173 */         if (textureatlassprite3.hasCustomLoader(resourceManager, resourcelocation1)) {
/*     */           
/* 175 */           if (!textureatlassprite3.load(resourceManager, resourcelocation1)) {
/*     */             
/* 177 */             i = Math.min(i, Math.min(textureatlassprite3.getIconWidth(), textureatlassprite3.getIconHeight()));
/* 178 */             stitcher.addSprite(textureatlassprite3);
/* 179 */             Config.detail("Custom loader (skipped): " + textureatlassprite3);
/* 180 */             i1++;
/*     */           } 
/*     */           
/* 183 */           Config.detail("Custom loader: " + textureatlassprite3);
/* 184 */           l++;
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/*     */         try {
/* 190 */           IResource iresource = resourceManager.getResource(resourcelocation2);
/* 191 */           BufferedImage[] abufferedimage = new BufferedImage[1 + this.mipmapLevels];
/* 192 */           abufferedimage[0] = TextureUtil.readBufferedImage(iresource.getInputStream());
/* 193 */           int k3 = abufferedimage[0].getWidth();
/* 194 */           int l3 = abufferedimage[0].getHeight();
/*     */           
/* 196 */           if (k3 < 1 || l3 < 1) {
/*     */             
/* 198 */             Config.warn("Invalid sprite size: " + textureatlassprite3);
/*     */             
/*     */             continue;
/*     */           } 
/* 202 */           if (k3 < j || this.mipmapLevels > 0) {
/*     */             
/* 204 */             int i4 = (this.mipmapLevels > 0) ? TextureUtils.scaleToGrid(k3, j) : TextureUtils.scaleToMin(k3, j);
/*     */             
/* 206 */             if (i4 != k3) {
/*     */               
/* 208 */               if (!TextureUtils.isPowerOfTwo(k3)) {
/*     */                 
/* 210 */                 Config.log("Scaled non power of 2: " + textureatlassprite3.getIconName() + ", " + k3 + " -> " + i4);
/*     */               }
/*     */               else {
/*     */                 
/* 214 */                 Config.log("Scaled too small texture: " + textureatlassprite3.getIconName() + ", " + k3 + " -> " + i4);
/*     */               } 
/*     */               
/* 217 */               int j1 = l3 * i4 / k3;
/* 218 */               abufferedimage[0] = TextureUtils.scaleImage(abufferedimage[0], i4);
/*     */             } 
/*     */           } 
/*     */           
/* 222 */           TextureMetadataSection texturemetadatasection = (TextureMetadataSection)iresource.getMetadata("texture");
/*     */           
/* 224 */           if (texturemetadatasection != null) {
/*     */             
/* 226 */             List<Integer> list1 = texturemetadatasection.getListMipmaps();
/*     */             
/* 228 */             if (!list1.isEmpty()) {
/*     */               
/* 230 */               int k1 = abufferedimage[0].getWidth();
/* 231 */               int l1 = abufferedimage[0].getHeight();
/*     */               
/* 233 */               if (MathHelper.roundUpToPowerOfTwo(k1) != k1 || MathHelper.roundUpToPowerOfTwo(l1) != l1)
/*     */               {
/* 235 */                 throw new RuntimeException("Unable to load extra miplevels, source-texture is not power of two");
/*     */               }
/*     */             } 
/*     */             
/* 239 */             Iterator<Integer> iterator1 = list1.iterator();
/*     */             
/* 241 */             while (iterator1.hasNext()) {
/*     */               
/* 243 */               int j4 = ((Integer)iterator1.next()).intValue();
/*     */               
/* 245 */               if (j4 > 0 && j4 < abufferedimage.length - 1 && abufferedimage[j4] == null) {
/*     */                 
/* 247 */                 ResourceLocation resourcelocation = completeResourceLocation(resourcelocation1, j4);
/*     */ 
/*     */                 
/*     */                 try {
/* 251 */                   abufferedimage[j4] = TextureUtil.readBufferedImage(resourceManager.getResource(resourcelocation).getInputStream());
/*     */                 }
/* 253 */                 catch (IOException ioexception) {
/*     */                   
/* 255 */                   logger.error("Unable to load miplevel {} from: {}", new Object[] { Integer.valueOf(j4), resourcelocation, ioexception });
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 261 */           AnimationMetadataSection animationmetadatasection = (AnimationMetadataSection)iresource.getMetadata("animation");
/* 262 */           textureatlassprite3.loadSprite(abufferedimage, animationmetadatasection);
/*     */         }
/* 264 */         catch (RuntimeException runtimeexception) {
/*     */           
/* 266 */           logger.error("Unable to parse metadata from " + resourcelocation2, runtimeexception);
/* 267 */           ReflectorForge.FMLClientHandler_trackBrokenTexture(resourcelocation2, runtimeexception.getMessage());
/*     */           
/*     */           continue;
/* 270 */         } catch (IOException ioexception1) {
/*     */           
/* 272 */           logger.error("Using missing texture, unable to load " + resourcelocation2 + ", " + ioexception1.getClass().getName());
/* 273 */           ReflectorForge.FMLClientHandler_trackMissingTexture(resourcelocation2);
/*     */           
/*     */           continue;
/*     */         } 
/* 277 */         i = Math.min(i, Math.min(textureatlassprite3.getIconWidth(), textureatlassprite3.getIconHeight()));
/* 278 */         int j3 = Math.min(Integer.lowestOneBit(textureatlassprite3.getIconWidth()), Integer.lowestOneBit(textureatlassprite3.getIconHeight()));
/*     */         
/* 280 */         if (j3 < k) {
/*     */           
/* 282 */           logger.warn("Texture {} with size {}x{} limits mip level from {} to {}", new Object[] { resourcelocation2, Integer.valueOf(textureatlassprite3.getIconWidth()), Integer.valueOf(textureatlassprite3.getIconHeight()), Integer.valueOf(MathHelper.calculateLogBaseTwo(k)), Integer.valueOf(MathHelper.calculateLogBaseTwo(j3)) });
/* 283 */           k = j3;
/*     */         } 
/*     */         
/* 286 */         stitcher.addSprite(textureatlassprite3);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 291 */     if (l > 0)
/*     */     {
/* 293 */       Config.dbg("Custom loader sprites: " + l);
/*     */     }
/*     */     
/* 296 */     if (i1 > 0)
/*     */     {
/* 298 */       Config.dbg("Custom loader sprites (skipped): " + i1);
/*     */     }
/*     */     
/* 301 */     int j2 = Math.min(i, k);
/* 302 */     int k2 = MathHelper.calculateLogBaseTwo(j2);
/*     */     
/* 304 */     if (k2 < 0)
/*     */     {
/* 306 */       k2 = 0;
/*     */     }
/*     */     
/* 309 */     if (k2 < this.mipmapLevels) {
/*     */       
/* 311 */       logger.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", new Object[] { this.basePath, Integer.valueOf(this.mipmapLevels), Integer.valueOf(k2), Integer.valueOf(j2) });
/* 312 */       this.mipmapLevels = k2;
/*     */     } 
/*     */     
/* 315 */     for (TextureAtlasSprite textureatlassprite1 : this.mapRegisteredSprites.values()) {
/*     */       
/* 317 */       if (this.skipFirst) {
/*     */         break;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 324 */         textureatlassprite1.generateMipmaps(this.mipmapLevels);
/*     */       }
/* 326 */       catch (Throwable throwable1) {
/*     */         
/* 328 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Applying mipmap");
/* 329 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Sprite being mipmapped");
/* 330 */         crashreportcategory.addCrashSectionCallable("Sprite name", new Callable<String>()
/*     */             {
/*     */               public String call() {
/* 333 */                 return textureatlassprite1.getIconName();
/*     */               }
/*     */             });
/* 336 */         crashreportcategory.addCrashSectionCallable("Sprite size", new Callable<String>()
/*     */             {
/*     */               public String call() {
/* 339 */                 return textureatlassprite1.getIconWidth() + " x " + textureatlassprite1.getIconHeight();
/*     */               }
/*     */             });
/* 342 */         crashreportcategory.addCrashSectionCallable("Sprite frames", new Callable<String>()
/*     */             {
/*     */               public String call() {
/* 345 */                 return textureatlassprite1.getFrameCount() + " frames";
/*     */               }
/*     */             });
/* 348 */         crashreportcategory.addCrashSection("Mipmap levels", Integer.valueOf(this.mipmapLevels));
/* 349 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */     } 
/*     */     
/* 353 */     this.missingImage.generateMipmaps(this.mipmapLevels);
/* 354 */     stitcher.addSprite(this.missingImage);
/* 355 */     this.skipFirst = false;
/*     */     
/* 357 */     stitcher.doStitch();
/*     */     
/* 359 */     logger.info("Created: {}x{} {}-atlas", new Object[] { Integer.valueOf(stitcher.getCurrentWidth()), Integer.valueOf(stitcher.getCurrentHeight()), this.basePath });
/*     */     
/* 361 */     if (Config.isShaders()) {
/*     */       
/* 363 */       ShadersTex.allocateTextureMap(getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight(), stitcher, this);
/*     */     }
/*     */     else {
/*     */       
/* 367 */       TextureUtil.allocateTextureImpl(getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
/*     */     } 
/*     */     
/* 370 */     Map<String, TextureAtlasSprite> map = Maps.newHashMap(this.mapRegisteredSprites);
/*     */     
/* 372 */     for (TextureAtlasSprite textureatlassprite2 : stitcher.getStichSlots()) {
/*     */       
/* 374 */       String s = textureatlassprite2.getIconName();
/* 375 */       map.remove(s);
/* 376 */       this.mapUploadedSprites.put(s, textureatlassprite2);
/*     */ 
/*     */       
/*     */       try {
/* 380 */         if (Config.isShaders())
/*     */         {
/* 382 */           ShadersTex.uploadTexSubForLoadAtlas(this, textureatlassprite2.getIconName(), textureatlassprite2.getFrameTextureData(0), textureatlassprite2.getIconWidth(), textureatlassprite2.getIconHeight(), textureatlassprite2.getOriginX(), textureatlassprite2.getOriginY(), false, false);
/*     */         }
/*     */         else
/*     */         {
/* 386 */           TextureUtil.uploadTextureMipmap(textureatlassprite2.getFrameTextureData(0), textureatlassprite2.getIconWidth(), textureatlassprite2.getIconHeight(), textureatlassprite2.getOriginX(), textureatlassprite2.getOriginY(), false, false);
/*     */         }
/*     */       
/* 389 */       } catch (Throwable throwable) {
/*     */         
/* 391 */         CrashReport crashreport1 = CrashReport.makeCrashReport(throwable, "Stitching texture atlas");
/* 392 */         CrashReportCategory crashreportcategory1 = crashreport1.makeCategory("Texture being stitched together");
/* 393 */         crashreportcategory1.addCrashSection("Atlas path", this.basePath);
/* 394 */         crashreportcategory1.addCrashSection("Sprite", textureatlassprite2);
/* 395 */         throw new ReportedException(crashreport1);
/*     */       } 
/*     */       
/* 398 */       if (textureatlassprite2.hasAnimationMetadata()) {
/*     */         
/* 400 */         textureatlassprite2.setAnimationIndex(this.listAnimatedSprites.size());
/* 401 */         this.listAnimatedSprites.add(textureatlassprite2);
/*     */       } 
/*     */     } 
/*     */     
/* 405 */     for (TextureAtlasSprite textureatlassprite4 : map.values())
/*     */     {
/* 407 */       textureatlassprite4.copyFrom(this.missingImage);
/*     */     }
/*     */     
/* 410 */     Config.log("Animated sprites: " + this.listAnimatedSprites.size());
/*     */     
/* 412 */     if (Config.isMultiTexture()) {
/*     */       
/* 414 */       int l2 = stitcher.getCurrentWidth();
/* 415 */       int i3 = stitcher.getCurrentHeight();
/*     */       
/* 417 */       for (TextureAtlasSprite textureatlassprite5 : stitcher.getStichSlots()) {
/*     */         
/* 419 */         textureatlassprite5.sheetWidth = l2;
/* 420 */         textureatlassprite5.sheetHeight = i3;
/* 421 */         textureatlassprite5.mipmapLevels = this.mipmapLevels;
/* 422 */         TextureAtlasSprite textureatlassprite6 = textureatlassprite5.spriteSingle;
/*     */         
/* 424 */         if (textureatlassprite6 != null) {
/*     */           
/* 426 */           if (textureatlassprite6.getIconWidth() <= 0) {
/*     */             
/* 428 */             textureatlassprite6.setIconWidth(textureatlassprite5.getIconWidth());
/* 429 */             textureatlassprite6.setIconHeight(textureatlassprite5.getIconHeight());
/* 430 */             textureatlassprite6.initSprite(textureatlassprite5.getIconWidth(), textureatlassprite5.getIconHeight(), 0, 0, false);
/* 431 */             textureatlassprite6.clearFramesTextureData();
/* 432 */             List<int[][]> list = textureatlassprite5.getFramesTextureData();
/* 433 */             textureatlassprite6.setFramesTextureData(list);
/* 434 */             textureatlassprite6.setAnimationMetadata(textureatlassprite5.getAnimationMetadata());
/*     */           } 
/*     */           
/* 437 */           textureatlassprite6.sheetWidth = l2;
/* 438 */           textureatlassprite6.sheetHeight = i3;
/* 439 */           textureatlassprite6.mipmapLevels = this.mipmapLevels;
/* 440 */           textureatlassprite6.setAnimationIndex(textureatlassprite5.getAnimationIndex());
/* 441 */           textureatlassprite5.bindSpriteTexture();
/* 442 */           boolean flag1 = false;
/* 443 */           boolean flag = true;
/*     */ 
/*     */           
/*     */           try {
/* 447 */             TextureUtil.uploadTextureMipmap(textureatlassprite6.getFrameTextureData(0), textureatlassprite6.getIconWidth(), textureatlassprite6.getIconHeight(), textureatlassprite6.getOriginX(), textureatlassprite6.getOriginY(), flag1, flag);
/*     */           }
/* 449 */           catch (Exception exception) {
/*     */             
/* 451 */             Config.dbg("Error uploading sprite single: " + textureatlassprite6 + ", parent: " + textureatlassprite5);
/* 452 */             exception.printStackTrace();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 457 */       Config.getMinecraft().getTextureManager().bindTexture(locationBlocksTexture);
/*     */     } 
/*     */     
/* 460 */     Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPost, new Object[] { this });
/* 461 */     updateIconGrid(stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
/*     */     
/* 463 */     if (Config.equals(System.getProperty("saveTextureMap"), "true")) {
/*     */       
/* 465 */       Config.dbg("Exporting texture map: " + this.basePath);
/* 466 */       TextureUtils.saveGlTexture("debug/" + this.basePath.replaceAll("/", "_"), getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceLocation completeResourceLocation(ResourceLocation p_completeResourceLocation_1_) {
/* 475 */     return completeResourceLocation(p_completeResourceLocation_1_, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation completeResourceLocation(ResourceLocation location, int p_147634_2_) {
/* 480 */     return isAbsoluteLocation(location) ? new ResourceLocation(location.getResourceDomain(), location.getResourcePath() + ".png") : ((p_147634_2_ == 0) ? new ResourceLocation(location.getResourceDomain(), String.format("%s/%s%s", new Object[] { this.basePath, location.getResourcePath(), ".png" })) : new ResourceLocation(location.getResourceDomain(), String.format("%s/mipmaps/%s.%d%s", new Object[] { this.basePath, location.getResourcePath(), Integer.valueOf(p_147634_2_), ".png" })));
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getAtlasSprite(String iconName) {
/* 485 */     TextureAtlasSprite textureatlassprite = this.mapUploadedSprites.get(iconName);
/*     */     
/* 487 */     if (textureatlassprite == null)
/*     */     {
/* 489 */       textureatlassprite = this.missingImage;
/*     */     }
/*     */     
/* 492 */     return textureatlassprite;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAnimations() {
/* 497 */     boolean flag = false;
/* 498 */     boolean flag1 = false;
/* 499 */     TextureUtil.bindTexture(getGlTextureId());
/* 500 */     int i = 0;
/*     */     
/* 502 */     for (TextureAtlasSprite textureatlassprite : this.listAnimatedSprites) {
/*     */       
/* 504 */       if (isTerrainAnimationActive(textureatlassprite)) {
/*     */         
/* 506 */         textureatlassprite.updateAnimation();
/*     */         
/* 508 */         if (textureatlassprite.isAnimationActive())
/*     */         {
/* 510 */           i++;
/*     */         }
/*     */         
/* 513 */         if (textureatlassprite.spriteNormal != null)
/*     */         {
/* 515 */           flag = true;
/*     */         }
/*     */         
/* 518 */         if (textureatlassprite.spriteSpecular != null)
/*     */         {
/* 520 */           flag1 = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 525 */     if (Config.isMultiTexture()) {
/*     */       
/* 527 */       for (TextureAtlasSprite textureatlassprite2 : this.listAnimatedSprites) {
/*     */         
/* 529 */         if (isTerrainAnimationActive(textureatlassprite2)) {
/*     */           
/* 531 */           TextureAtlasSprite textureatlassprite1 = textureatlassprite2.spriteSingle;
/*     */           
/* 533 */           if (textureatlassprite1 != null) {
/*     */             
/* 535 */             if (textureatlassprite2 == TextureUtils.iconClock || textureatlassprite2 == TextureUtils.iconCompass)
/*     */             {
/* 537 */               textureatlassprite1.frameCounter = textureatlassprite2.frameCounter;
/*     */             }
/*     */             
/* 540 */             textureatlassprite2.bindSpriteTexture();
/* 541 */             textureatlassprite1.updateAnimation();
/*     */             
/* 543 */             if (textureatlassprite1.isAnimationActive())
/*     */             {
/* 545 */               i++;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 551 */       TextureUtil.bindTexture(getGlTextureId());
/*     */     } 
/*     */     
/* 554 */     if (Config.isShaders()) {
/*     */       
/* 556 */       if (flag) {
/*     */         
/* 558 */         TextureUtil.bindTexture((getMultiTexID()).norm);
/*     */         
/* 560 */         for (TextureAtlasSprite textureatlassprite3 : this.listAnimatedSprites) {
/*     */           
/* 562 */           if (textureatlassprite3.spriteNormal != null && isTerrainAnimationActive(textureatlassprite3)) {
/*     */             
/* 564 */             if (textureatlassprite3 == TextureUtils.iconClock || textureatlassprite3 == TextureUtils.iconCompass)
/*     */             {
/* 566 */               textureatlassprite3.spriteNormal.frameCounter = textureatlassprite3.frameCounter;
/*     */             }
/*     */             
/* 569 */             textureatlassprite3.spriteNormal.updateAnimation();
/*     */             
/* 571 */             if (textureatlassprite3.spriteNormal.isAnimationActive())
/*     */             {
/* 573 */               i++;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 579 */       if (flag1) {
/*     */         
/* 581 */         TextureUtil.bindTexture((getMultiTexID()).spec);
/*     */         
/* 583 */         for (TextureAtlasSprite textureatlassprite4 : this.listAnimatedSprites) {
/*     */           
/* 585 */           if (textureatlassprite4.spriteSpecular != null && isTerrainAnimationActive(textureatlassprite4)) {
/*     */             
/* 587 */             if (textureatlassprite4 == TextureUtils.iconClock || textureatlassprite4 == TextureUtils.iconCompass)
/*     */             {
/* 589 */               textureatlassprite4.spriteNormal.frameCounter = textureatlassprite4.frameCounter;
/*     */             }
/*     */             
/* 592 */             textureatlassprite4.spriteSpecular.updateAnimation();
/*     */             
/* 594 */             if (textureatlassprite4.spriteSpecular.isAnimationActive())
/*     */             {
/* 596 */               i++;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 602 */       if (flag || flag1)
/*     */       {
/* 604 */         TextureUtil.bindTexture(getGlTextureId());
/*     */       }
/*     */     } 
/*     */     
/* 608 */     int j = (Config.getMinecraft()).entityRenderer.frameCount;
/*     */     
/* 610 */     if (j != this.frameCountAnimations) {
/*     */       
/* 612 */       this.countAnimationsActive = i;
/* 613 */       this.frameCountAnimations = j;
/*     */     } 
/*     */     
/* 616 */     if (SmartAnimations.isActive())
/*     */     {
/* 618 */       SmartAnimations.resetSpritesRendered();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite registerSprite(ResourceLocation location) {
/* 624 */     if (location == null)
/*     */     {
/* 626 */       throw new IllegalArgumentException("Location cannot be null!");
/*     */     }
/*     */ 
/*     */     
/* 630 */     TextureAtlasSprite textureatlassprite = this.mapRegisteredSprites.get(location.toString());
/*     */     
/* 632 */     if (textureatlassprite == null) {
/*     */       
/* 634 */       textureatlassprite = TextureAtlasSprite.makeAtlasSprite(location);
/* 635 */       this.mapRegisteredSprites.put(location.toString(), textureatlassprite);
/* 636 */       textureatlassprite.updateIndexInMap(this.counterIndexInMap);
/*     */       
/* 638 */       if (Config.isEmissiveTextures())
/*     */       {
/* 640 */         checkEmissive(location, textureatlassprite);
/*     */       }
/*     */     } 
/*     */     
/* 644 */     return textureatlassprite;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/* 650 */     updateAnimations();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMipmapLevels(int mipmapLevelsIn) {
/* 655 */     this.mipmapLevels = mipmapLevelsIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getMissingSprite() {
/* 660 */     return this.missingImage;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getTextureExtry(String p_getTextureExtry_1_) {
/* 665 */     return this.mapRegisteredSprites.get(p_getTextureExtry_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setTextureEntry(String p_setTextureEntry_1_, TextureAtlasSprite p_setTextureEntry_2_) {
/* 670 */     if (!this.mapRegisteredSprites.containsKey(p_setTextureEntry_1_)) {
/*     */       
/* 672 */       this.mapRegisteredSprites.put(p_setTextureEntry_1_, p_setTextureEntry_2_);
/* 673 */       p_setTextureEntry_2_.updateIndexInMap(this.counterIndexInMap);
/* 674 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 678 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setTextureEntry(TextureAtlasSprite p_setTextureEntry_1_) {
/* 684 */     return setTextureEntry(p_setTextureEntry_1_.getIconName(), p_setTextureEntry_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBasePath() {
/* 689 */     return this.basePath;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMipmapLevels() {
/* 694 */     return this.mipmapLevels;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isAbsoluteLocation(ResourceLocation p_isAbsoluteLocation_1_) {
/* 699 */     String s = p_isAbsoluteLocation_1_.getResourcePath();
/* 700 */     return isAbsoluteLocationPath(s);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isAbsoluteLocationPath(String p_isAbsoluteLocationPath_1_) {
/* 705 */     String s = p_isAbsoluteLocationPath_1_.toLowerCase();
/* 706 */     return (s.startsWith("mcpatcher/") || s.startsWith("optifine/"));
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getSpriteSafe(String p_getSpriteSafe_1_) {
/* 711 */     ResourceLocation resourcelocation = new ResourceLocation(p_getSpriteSafe_1_);
/* 712 */     return this.mapRegisteredSprites.get(resourcelocation.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getRegisteredSprite(ResourceLocation p_getRegisteredSprite_1_) {
/* 717 */     return this.mapRegisteredSprites.get(p_getRegisteredSprite_1_.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isTerrainAnimationActive(TextureAtlasSprite p_isTerrainAnimationActive_1_) {
/* 722 */     return (p_isTerrainAnimationActive_1_ != TextureUtils.iconWaterStill && p_isTerrainAnimationActive_1_ != TextureUtils.iconWaterFlow) ? ((p_isTerrainAnimationActive_1_ != TextureUtils.iconLavaStill && p_isTerrainAnimationActive_1_ != TextureUtils.iconLavaFlow) ? ((p_isTerrainAnimationActive_1_ != TextureUtils.iconFireLayer0 && p_isTerrainAnimationActive_1_ != TextureUtils.iconFireLayer1) ? ((p_isTerrainAnimationActive_1_ == TextureUtils.iconPortal) ? Config.isAnimatedPortal() : ((p_isTerrainAnimationActive_1_ != TextureUtils.iconClock && p_isTerrainAnimationActive_1_ != TextureUtils.iconCompass) ? Config.isAnimatedTerrain() : true)) : Config.isAnimatedFire()) : Config.isAnimatedLava()) : Config.isAnimatedWater();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCountRegisteredSprites() {
/* 727 */     return this.counterIndexInMap.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   private int detectMaxMipmapLevel(Map p_detectMaxMipmapLevel_1_, IResourceManager p_detectMaxMipmapLevel_2_) {
/* 732 */     int i = detectMinimumSpriteSize(p_detectMaxMipmapLevel_1_, p_detectMaxMipmapLevel_2_, 20);
/*     */     
/* 734 */     if (i < 16)
/*     */     {
/* 736 */       i = 16;
/*     */     }
/*     */     
/* 739 */     i = MathHelper.roundUpToPowerOfTwo(i);
/*     */     
/* 741 */     if (i > 16)
/*     */     {
/* 743 */       Config.log("Sprite size: " + i);
/*     */     }
/*     */     
/* 746 */     int j = MathHelper.calculateLogBaseTwo(i);
/*     */     
/* 748 */     if (j < 4)
/*     */     {
/* 750 */       j = 4;
/*     */     }
/*     */     
/* 753 */     return j;
/*     */   }
/*     */ 
/*     */   
/*     */   private int detectMinimumSpriteSize(Map p_detectMinimumSpriteSize_1_, IResourceManager p_detectMinimumSpriteSize_2_, int p_detectMinimumSpriteSize_3_) {
/* 758 */     Map<Object, Object> map = new HashMap<>();
/*     */     
/* 760 */     for (Object e : p_detectMinimumSpriteSize_1_.entrySet()) {
/*     */       
/* 762 */       Map.Entry entry = (Map.Entry)e;
/* 763 */       TextureAtlasSprite textureatlassprite = (TextureAtlasSprite)entry.getValue();
/* 764 */       ResourceLocation resourcelocation = new ResourceLocation(textureatlassprite.getIconName());
/* 765 */       ResourceLocation resourcelocation1 = completeResourceLocation(resourcelocation);
/*     */       
/* 767 */       if (!textureatlassprite.hasCustomLoader(p_detectMinimumSpriteSize_2_, resourcelocation)) {
/*     */         
/*     */         try {
/*     */           
/* 771 */           IResource iresource = p_detectMinimumSpriteSize_2_.getResource(resourcelocation1);
/*     */           
/* 773 */           if (iresource != null) {
/*     */             
/* 775 */             InputStream inputstream = iresource.getInputStream();
/*     */             
/* 777 */             if (inputstream != null)
/*     */             {
/* 779 */               Dimension dimension = TextureUtils.getImageSize(inputstream, "png");
/* 780 */               inputstream.close();
/*     */               
/* 782 */               if (dimension != null)
/*     */               {
/* 784 */                 int i = dimension.width;
/* 785 */                 int j = MathHelper.roundUpToPowerOfTwo(i);
/*     */                 
/* 787 */                 if (!map.containsKey(Integer.valueOf(j))) {
/*     */                   
/* 789 */                   map.put(Integer.valueOf(j), Integer.valueOf(1));
/*     */                   
/*     */                   continue;
/*     */                 } 
/* 793 */                 int k = ((Integer)map.get(Integer.valueOf(j))).intValue();
/* 794 */                 map.put(Integer.valueOf(j), Integer.valueOf(k + 1));
/*     */               }
/*     */             
/*     */             }
/*     */           
/*     */           } 
/* 800 */         } catch (Exception exception) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 807 */     int l = 0;
/* 808 */     Set<?> set = map.keySet();
/* 809 */     Set set1 = new TreeSet(set);
/*     */ 
/*     */     
/* 812 */     for (Iterator<Integer> iterator = set1.iterator(); iterator.hasNext(); l += i) {
/*     */       
/* 814 */       int j1 = ((Integer)iterator.next()).intValue();
/* 815 */       int i = ((Integer)map.get(Integer.valueOf(j1))).intValue();
/*     */     } 
/*     */     
/* 818 */     int i1 = 16;
/* 819 */     int k1 = 0;
/* 820 */     int l1 = l * p_detectMinimumSpriteSize_3_ / 100;
/* 821 */     Iterator<Integer> iterator1 = set1.iterator();
/*     */     
/* 823 */     while (iterator1.hasNext()) {
/*     */       
/* 825 */       int i2 = ((Integer)iterator1.next()).intValue();
/* 826 */       int j2 = ((Integer)map.get(Integer.valueOf(i2))).intValue();
/* 827 */       k1 += j2;
/*     */       
/* 829 */       if (i2 > i1)
/*     */       {
/* 831 */         i1 = i2;
/*     */       }
/*     */       
/* 834 */       if (k1 > l1)
/*     */       {
/* 836 */         return i1;
/*     */       }
/*     */     } 
/*     */     
/* 840 */     return i1;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getMinSpriteSize() {
/* 845 */     int i = 1 << this.mipmapLevels;
/*     */     
/* 847 */     if (i < 8)
/*     */     {
/* 849 */       i = 8;
/*     */     }
/*     */     
/* 852 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private int[] getMissingImageData(int p_getMissingImageData_1_) {
/* 857 */     BufferedImage bufferedimage = new BufferedImage(16, 16, 2);
/* 858 */     bufferedimage.setRGB(0, 0, 16, 16, TextureUtil.missingTextureData, 0, 16);
/* 859 */     BufferedImage bufferedimage1 = TextureUtils.scaleImage(bufferedimage, p_getMissingImageData_1_);
/* 860 */     int[] aint = new int[p_getMissingImageData_1_ * p_getMissingImageData_1_];
/* 861 */     bufferedimage1.getRGB(0, 0, p_getMissingImageData_1_, p_getMissingImageData_1_, aint, 0, p_getMissingImageData_1_);
/* 862 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTextureBound() {
/* 867 */     int i = GlStateManager.getBoundTexture();
/* 868 */     int j = getGlTextureId();
/* 869 */     return (i == j);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateIconGrid(int p_updateIconGrid_1_, int p_updateIconGrid_2_) {
/* 874 */     this.iconGridCountX = -1;
/* 875 */     this.iconGridCountY = -1;
/* 876 */     this.iconGrid = null;
/*     */     
/* 878 */     if (this.iconGridSize > 0) {
/*     */       
/* 880 */       this.iconGridCountX = p_updateIconGrid_1_ / this.iconGridSize;
/* 881 */       this.iconGridCountY = p_updateIconGrid_2_ / this.iconGridSize;
/* 882 */       this.iconGrid = new TextureAtlasSprite[this.iconGridCountX * this.iconGridCountY];
/* 883 */       this.iconGridSizeU = 1.0D / this.iconGridCountX;
/* 884 */       this.iconGridSizeV = 1.0D / this.iconGridCountY;
/*     */       
/* 886 */       for (TextureAtlasSprite textureatlassprite : this.mapUploadedSprites.values()) {
/*     */         
/* 888 */         double d0 = 0.5D / p_updateIconGrid_1_;
/* 889 */         double d1 = 0.5D / p_updateIconGrid_2_;
/* 890 */         double d2 = Math.min(textureatlassprite.getMinU(), textureatlassprite.getMaxU()) + d0;
/* 891 */         double d3 = Math.min(textureatlassprite.getMinV(), textureatlassprite.getMaxV()) + d1;
/* 892 */         double d4 = Math.max(textureatlassprite.getMinU(), textureatlassprite.getMaxU()) - d0;
/* 893 */         double d5 = Math.max(textureatlassprite.getMinV(), textureatlassprite.getMaxV()) - d1;
/* 894 */         int i = (int)(d2 / this.iconGridSizeU);
/* 895 */         int j = (int)(d3 / this.iconGridSizeV);
/* 896 */         int k = (int)(d4 / this.iconGridSizeU);
/* 897 */         int l = (int)(d5 / this.iconGridSizeV);
/*     */         
/* 899 */         for (int i1 = i; i1 <= k; i1++) {
/*     */           
/* 901 */           if (i1 >= 0 && i1 < this.iconGridCountX) {
/*     */             
/* 903 */             for (int j1 = j; j1 <= l; j1++) {
/*     */               
/* 905 */               if (j1 >= 0 && j1 < this.iconGridCountX)
/*     */               {
/* 907 */                 int k1 = j1 * this.iconGridCountX + i1;
/* 908 */                 this.iconGrid[k1] = textureatlassprite;
/*     */               }
/*     */               else
/*     */               {
/* 912 */                 Config.warn("Invalid grid V: " + j1 + ", icon: " + textureatlassprite.getIconName());
/*     */               }
/*     */             
/*     */             } 
/*     */           } else {
/*     */             
/* 918 */             Config.warn("Invalid grid U: " + i1 + ", icon: " + textureatlassprite.getIconName());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getIconByUV(double p_getIconByUV_1_, double p_getIconByUV_3_) {
/* 927 */     if (this.iconGrid == null)
/*     */     {
/* 929 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 933 */     int i = (int)(p_getIconByUV_1_ / this.iconGridSizeU);
/* 934 */     int j = (int)(p_getIconByUV_3_ / this.iconGridSizeV);
/* 935 */     int k = j * this.iconGridCountX + i;
/* 936 */     return (k >= 0 && k <= this.iconGrid.length) ? this.iconGrid[k] : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkEmissive(ResourceLocation p_checkEmissive_1_, TextureAtlasSprite p_checkEmissive_2_) {
/* 942 */     String s = EmissiveTextures.getSuffixEmissive();
/*     */     
/* 944 */     if (s != null)
/*     */     {
/* 946 */       if (!p_checkEmissive_1_.getResourcePath().endsWith(s)) {
/*     */         
/* 948 */         ResourceLocation resourcelocation = new ResourceLocation(p_checkEmissive_1_.getResourceDomain(), p_checkEmissive_1_.getResourcePath() + s);
/* 949 */         ResourceLocation resourcelocation1 = completeResourceLocation(resourcelocation);
/*     */         
/* 951 */         if (Config.hasResource(resourcelocation1)) {
/*     */           
/* 953 */           TextureAtlasSprite textureatlassprite = registerSprite(resourcelocation);
/* 954 */           textureatlassprite.isEmissive = true;
/* 955 */           p_checkEmissive_2_.spriteEmissive = textureatlassprite;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCountAnimations() {
/* 963 */     return this.listAnimatedSprites.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCountAnimationsActive() {
/* 968 */     return this.countAnimationsActive;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\texture\TextureMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */