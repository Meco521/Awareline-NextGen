/*     */ package net.optifine.shaders;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.LayeredTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ShadersTex {
/*  28 */   public static ByteBuffer byteBuffer = BufferUtils.createByteBuffer(4194304); public static final int initialBufferSize = 1048576;
/*  29 */   public static IntBuffer intBuffer = byteBuffer.asIntBuffer();
/*  30 */   public static int[] intArray = new int[1048576];
/*     */   public static final int defBaseTexColor = 0;
/*     */   public static final int defNormTexColor = -8421377;
/*     */   public static final int defSpecTexColor = 0;
/*  34 */   public static Map<Integer, MultiTexID> multiTexMap = new HashMap<>();
/*     */ 
/*     */   
/*     */   public static IntBuffer getIntBuffer(int size) {
/*  38 */     if (intBuffer.capacity() < size) {
/*     */       
/*  40 */       int i = roundUpPOT(size);
/*  41 */       byteBuffer = BufferUtils.createByteBuffer(i << 2);
/*  42 */       intBuffer = byteBuffer.asIntBuffer();
/*     */     } 
/*     */     
/*  45 */     return intBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] getIntArray(int size) {
/*  50 */     if (intArray == null)
/*     */     {
/*  52 */       intArray = new int[1048576];
/*     */     }
/*     */     
/*  55 */     if (intArray.length < size)
/*     */     {
/*  57 */       intArray = new int[roundUpPOT(size)];
/*     */     }
/*     */     
/*  60 */     return intArray;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int roundUpPOT(int x) {
/*  65 */     int i = x - 1;
/*  66 */     i |= i >> 1;
/*  67 */     i |= i >> 2;
/*  68 */     i |= i >> 4;
/*  69 */     i |= i >> 8;
/*  70 */     i |= i >> 16;
/*  71 */     return i + 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int log2(int x) {
/*  76 */     int i = 0;
/*     */     
/*  78 */     if ((x & 0xFFFF0000) != 0) {
/*     */       
/*  80 */       i += 16;
/*  81 */       x >>= 16;
/*     */     } 
/*     */     
/*  84 */     if ((x & 0xFF00) != 0) {
/*     */       
/*  86 */       i += 8;
/*  87 */       x >>= 8;
/*     */     } 
/*     */     
/*  90 */     if ((x & 0xF0) != 0) {
/*     */       
/*  92 */       i += 4;
/*  93 */       x >>= 4;
/*     */     } 
/*     */     
/*  96 */     if ((x & 0x6) != 0) {
/*     */       
/*  98 */       i += 2;
/*  99 */       x >>= 2;
/*     */     } 
/*     */     
/* 102 */     if ((x & 0x2) != 0)
/*     */     {
/* 104 */       i++;
/*     */     }
/*     */     
/* 107 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static IntBuffer fillIntBuffer(int size, int value) {
/* 112 */     int[] aint = getIntArray(size);
/* 113 */     IntBuffer intbuffer = getIntBuffer(size);
/* 114 */     Arrays.fill(intArray, 0, size, value);
/* 115 */     intBuffer.put(intArray, 0, size);
/* 116 */     return intBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] createAIntImage(int size) {
/* 121 */     int[] aint = new int[size * 3];
/* 122 */     Arrays.fill(aint, 0, size, 0);
/* 123 */     Arrays.fill(aint, size, size << 1, -8421377);
/* 124 */     Arrays.fill(aint, size << 1, size * 3, 0);
/* 125 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] createAIntImage(int size, int color) {
/* 130 */     int[] aint = new int[size * 3];
/* 131 */     Arrays.fill(aint, 0, size, color);
/* 132 */     Arrays.fill(aint, size, size << 1, -8421377);
/* 133 */     Arrays.fill(aint, size << 1, size * 3, 0);
/* 134 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static MultiTexID getMultiTexID(AbstractTexture tex) {
/* 139 */     MultiTexID multitexid = tex.multiTex;
/*     */     
/* 141 */     if (multitexid == null) {
/*     */       
/* 143 */       int i = tex.getGlTextureId();
/* 144 */       multitexid = multiTexMap.get(Integer.valueOf(i));
/*     */       
/* 146 */       if (multitexid == null) {
/*     */         
/* 148 */         multitexid = new MultiTexID(i, GL11.glGenTextures(), GL11.glGenTextures());
/* 149 */         multiTexMap.put(Integer.valueOf(i), multitexid);
/*     */       } 
/*     */       
/* 152 */       tex.multiTex = multitexid;
/*     */     } 
/*     */     
/* 155 */     return multitexid;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void deleteTextures(AbstractTexture atex, int texid) {
/* 160 */     MultiTexID multitexid = atex.multiTex;
/*     */     
/* 162 */     if (multitexid != null) {
/*     */       
/* 164 */       atex.multiTex = null;
/* 165 */       multiTexMap.remove(Integer.valueOf(multitexid.base));
/* 166 */       GlStateManager.deleteTexture(multitexid.norm);
/* 167 */       GlStateManager.deleteTexture(multitexid.spec);
/*     */       
/* 169 */       if (multitexid.base != texid) {
/*     */         
/* 171 */         SMCLog.warning("Error : MultiTexID.base mismatch: " + multitexid.base + ", texid: " + texid);
/* 172 */         GlStateManager.deleteTexture(multitexid.base);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindNSTextures(int normTex, int specTex) {
/* 179 */     if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
/*     */       
/* 181 */       GlStateManager.setActiveTexture(33986);
/* 182 */       GlStateManager.bindTexture(normTex);
/* 183 */       GlStateManager.setActiveTexture(33987);
/* 184 */       GlStateManager.bindTexture(specTex);
/* 185 */       GlStateManager.setActiveTexture(33984);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindNSTextures(MultiTexID multiTex) {
/* 191 */     bindNSTextures(multiTex.norm, multiTex.spec);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindTextures(int baseTex, int normTex, int specTex) {
/* 196 */     if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
/*     */       
/* 198 */       GlStateManager.setActiveTexture(33986);
/* 199 */       GlStateManager.bindTexture(normTex);
/* 200 */       GlStateManager.setActiveTexture(33987);
/* 201 */       GlStateManager.bindTexture(specTex);
/* 202 */       GlStateManager.setActiveTexture(33984);
/*     */     } 
/*     */     
/* 205 */     GlStateManager.bindTexture(baseTex);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindTextures(MultiTexID multiTex) {
/* 210 */     if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
/*     */       
/* 212 */       if (Shaders.configNormalMap) {
/*     */         
/* 214 */         GlStateManager.setActiveTexture(33986);
/* 215 */         GlStateManager.bindTexture(multiTex.norm);
/*     */       } 
/*     */       
/* 218 */       if (Shaders.configSpecularMap) {
/*     */         
/* 220 */         GlStateManager.setActiveTexture(33987);
/* 221 */         GlStateManager.bindTexture(multiTex.spec);
/*     */       } 
/*     */       
/* 224 */       GlStateManager.setActiveTexture(33984);
/*     */     } 
/*     */     
/* 227 */     GlStateManager.bindTexture(multiTex.base);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindTexture(ITextureObject tex) {
/* 232 */     int i = tex.getGlTextureId();
/* 233 */     bindTextures(tex.getMultiTexID());
/*     */     
/* 235 */     if (GlStateManager.getActiveTextureUnit() == 33984) {
/*     */       
/* 237 */       int j = Shaders.atlasSizeX;
/* 238 */       int k = Shaders.atlasSizeY;
/*     */       
/* 240 */       if (tex instanceof TextureMap) {
/*     */         
/* 242 */         Shaders.atlasSizeX = ((TextureMap)tex).atlasWidth;
/* 243 */         Shaders.atlasSizeY = ((TextureMap)tex).atlasHeight;
/*     */       }
/*     */       else {
/*     */         
/* 247 */         Shaders.atlasSizeX = 0;
/* 248 */         Shaders.atlasSizeY = 0;
/*     */       } 
/*     */       
/* 251 */       if (Shaders.atlasSizeX != j || Shaders.atlasSizeY != k)
/*     */       {
/* 253 */         Shaders.uniform_atlasSize.setValue(Shaders.atlasSizeX, Shaders.atlasSizeY);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindTextures(int baseTex) {
/* 260 */     MultiTexID multitexid = multiTexMap.get(Integer.valueOf(baseTex));
/* 261 */     bindTextures(multitexid);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void initDynamicTexture(int texID, int width, int height, DynamicTexture tex) {
/* 266 */     MultiTexID multitexid = tex.getMultiTexID();
/* 267 */     int[] aint = tex.getTextureData();
/* 268 */     int i = width * height;
/* 269 */     Arrays.fill(aint, i, i << 1, -8421377);
/* 270 */     Arrays.fill(aint, i << 1, i * 3, 0);
/* 271 */     TextureUtil.allocateTexture(multitexid.base, width, height);
/* 272 */     TextureUtil.setTextureBlurMipmap(false, false);
/* 273 */     TextureUtil.setTextureClamped(false);
/* 274 */     TextureUtil.allocateTexture(multitexid.norm, width, height);
/* 275 */     TextureUtil.setTextureBlurMipmap(false, false);
/* 276 */     TextureUtil.setTextureClamped(false);
/* 277 */     TextureUtil.allocateTexture(multitexid.spec, width, height);
/* 278 */     TextureUtil.setTextureBlurMipmap(false, false);
/* 279 */     TextureUtil.setTextureClamped(false);
/* 280 */     GlStateManager.bindTexture(multitexid.base);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateDynamicTexture(int texID, int[] src, int width, int height, DynamicTexture tex) {
/* 285 */     MultiTexID multitexid = tex.getMultiTexID();
/* 286 */     GlStateManager.bindTexture(multitexid.base);
/* 287 */     updateDynTexSubImage1(src, width, height, 0, 0, 0);
/* 288 */     GlStateManager.bindTexture(multitexid.norm);
/* 289 */     updateDynTexSubImage1(src, width, height, 0, 0, 1);
/* 290 */     GlStateManager.bindTexture(multitexid.spec);
/* 291 */     updateDynTexSubImage1(src, width, height, 0, 0, 2);
/* 292 */     GlStateManager.bindTexture(multitexid.base);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateDynTexSubImage1(int[] src, int width, int height, int posX, int posY, int page) {
/* 297 */     int i = width * height;
/* 298 */     IntBuffer intbuffer = getIntBuffer(i);
/* 299 */     intbuffer.clear();
/* 300 */     int j = page * i;
/*     */     
/* 302 */     if (src.length >= j + i) {
/*     */       
/* 304 */       intbuffer.put(src, j, i).position(0).limit(i);
/* 305 */       GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intbuffer);
/* 306 */       intbuffer.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ITextureObject createDefaultTexture() {
/* 312 */     DynamicTexture dynamictexture = new DynamicTexture(1, 1);
/* 313 */     dynamictexture.getTextureData()[0] = -1;
/* 314 */     dynamictexture.updateDynamicTexture();
/* 315 */     return (ITextureObject)dynamictexture;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void allocateTextureMap(int texID, int mipmapLevels, int width, int height, Stitcher stitcher, TextureMap tex) {
/* 320 */     SMCLog.info("allocateTextureMap " + mipmapLevels + " " + width + " " + height + " ");
/* 321 */     tex.atlasWidth = width;
/* 322 */     tex.atlasHeight = height;
/* 323 */     MultiTexID multitexid = getMultiTexID((AbstractTexture)tex);
/* 324 */     TextureUtil.allocateTextureImpl(multitexid.base, mipmapLevels, width, height);
/*     */     
/* 326 */     if (Shaders.configNormalMap)
/*     */     {
/* 328 */       TextureUtil.allocateTextureImpl(multitexid.norm, mipmapLevels, width, height);
/*     */     }
/*     */     
/* 331 */     if (Shaders.configSpecularMap)
/*     */     {
/* 333 */       TextureUtil.allocateTextureImpl(multitexid.spec, mipmapLevels, width, height);
/*     */     }
/*     */     
/* 336 */     GlStateManager.bindTexture(texID);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void uploadTexSubForLoadAtlas(TextureMap textureMap, String iconName, int[][] data, int width, int height, int xoffset, int yoffset, boolean linear, boolean clamp) {
/* 341 */     MultiTexID multitexid = textureMap.multiTex;
/* 342 */     TextureUtil.uploadTextureMipmap(data, width, height, xoffset, yoffset, linear, clamp);
/* 343 */     boolean flag = false;
/*     */     
/* 345 */     if (Shaders.configNormalMap) {
/*     */       
/* 347 */       int[][] aint = readImageAndMipmaps(textureMap, iconName + "_n", width, height, data.length, flag, -8421377);
/* 348 */       GlStateManager.bindTexture(multitexid.norm);
/* 349 */       TextureUtil.uploadTextureMipmap(aint, width, height, xoffset, yoffset, linear, clamp);
/*     */     } 
/*     */     
/* 352 */     if (Shaders.configSpecularMap) {
/*     */       
/* 354 */       int[][] aint1 = readImageAndMipmaps(textureMap, iconName + "_s", width, height, data.length, flag, 0);
/* 355 */       GlStateManager.bindTexture(multitexid.spec);
/* 356 */       TextureUtil.uploadTextureMipmap(aint1, width, height, xoffset, yoffset, linear, clamp);
/*     */     } 
/*     */     
/* 359 */     GlStateManager.bindTexture(multitexid.base);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[][] readImageAndMipmaps(TextureMap updatingTextureMap, String name, int width, int height, int numLevels, boolean border, int defColor) {
/* 364 */     MultiTexID multitexid = updatingTextureMap.multiTex;
/* 365 */     int[][] aint = new int[numLevels][];
/*     */     
/* 367 */     int[] aint1 = new int[width * height];
/* 368 */     boolean flag = false;
/* 369 */     BufferedImage bufferedimage = readImage(updatingTextureMap.completeResourceLocation(new ResourceLocation(name)));
/*     */     
/* 371 */     if (bufferedimage != null) {
/*     */       
/* 373 */       int i = bufferedimage.getWidth();
/* 374 */       int j = bufferedimage.getHeight();
/*     */       
/* 376 */       if (i + (border ? 16 : 0) == width) {
/*     */         
/* 378 */         flag = true;
/* 379 */         bufferedimage.getRGB(0, 0, i, i, aint1, 0, i);
/*     */       } 
/*     */     } 
/*     */     
/* 383 */     if (!flag)
/*     */     {
/* 385 */       Arrays.fill(aint1, defColor);
/*     */     }
/*     */     
/* 388 */     GlStateManager.bindTexture(multitexid.spec);
/* 389 */     aint = genMipmapsSimple(aint.length - 1, width, aint);
/* 390 */     return aint;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage readImage(ResourceLocation resLoc) {
/*     */     try {
/* 397 */       if (!Config.hasResource(resLoc))
/*     */       {
/* 399 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 403 */       InputStream inputstream = Config.getResourceStream(resLoc);
/*     */       
/* 405 */       if (inputstream == null)
/*     */       {
/* 407 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 411 */       BufferedImage bufferedimage = ImageIO.read(inputstream);
/* 412 */       inputstream.close();
/* 413 */       return bufferedimage;
/*     */ 
/*     */     
/*     */     }
/* 417 */     catch (IOException var3) {
/*     */       
/* 419 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[][] genMipmapsSimple(int maxLevel, int width, int[][] data) {
/* 425 */     for (int i = 1; i <= maxLevel; i++) {
/*     */       
/* 427 */       if (data[i] == null) {
/*     */         
/* 429 */         int j = width >> i;
/* 430 */         int k = j << 1;
/* 431 */         int[] aint = data[i - 1];
/* 432 */         int[] aint1 = data[i] = new int[j * j];
/*     */         
/* 434 */         for (int i1 = 0; i1 < j; i1++) {
/*     */           
/* 436 */           for (int l = 0; l < j; l++) {
/*     */             
/* 438 */             int j1 = i1 * 2 * k + (l << 1);
/* 439 */             aint1[i1 * j + l] = blend4Simple(aint[j1], aint[j1 + 1], aint[j1 + k], aint[j1 + k + 1]);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 445 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void uploadTexSub1(int[][] src, int width, int height, int posX, int posY, int page) {
/* 450 */     int i = width * height;
/* 451 */     IntBuffer intbuffer = getIntBuffer(i);
/* 452 */     int j = src.length;
/* 453 */     int k = 0;
/* 454 */     int l = width;
/* 455 */     int i1 = height;
/* 456 */     int j1 = posX;
/*     */     
/* 458 */     for (int k1 = posY; l > 0 && i1 > 0 && k < j; k++) {
/*     */       
/* 460 */       int l1 = l * i1;
/* 461 */       int[] aint = src[k];
/* 462 */       intbuffer.clear();
/*     */       
/* 464 */       if (aint.length >= l1 * (page + 1)) {
/*     */         
/* 466 */         intbuffer.put(aint, l1 * page, l1).position(0).limit(l1);
/* 467 */         GL11.glTexSubImage2D(3553, k, j1, k1, l, i1, 32993, 33639, intbuffer);
/*     */       } 
/*     */       
/* 470 */       l >>= 1;
/* 471 */       i1 >>= 1;
/* 472 */       j1 >>= 1;
/* 473 */       k1 >>= 1;
/*     */     } 
/*     */     
/* 476 */     intbuffer.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public static int blend4Alpha(int c0, int c1, int c2, int c3) {
/* 481 */     int k1, i = c0 >>> 24 & 0xFF;
/* 482 */     int j = c1 >>> 24 & 0xFF;
/* 483 */     int k = c2 >>> 24 & 0xFF;
/* 484 */     int l = c3 >>> 24 & 0xFF;
/* 485 */     int i1 = i + j + k + l;
/* 486 */     int j1 = (i1 + 2) / 4;
/*     */ 
/*     */     
/* 489 */     if (i1 != 0) {
/*     */       
/* 491 */       k1 = i1;
/*     */     }
/*     */     else {
/*     */       
/* 495 */       k1 = 4;
/* 496 */       i = 1;
/* 497 */       j = 1;
/* 498 */       k = 1;
/* 499 */       l = 1;
/*     */     } 
/*     */     
/* 502 */     int l1 = (k1 + 1) / 2;
/* 503 */     int i2 = j1 << 24 | ((c0 >>> 16 & 0xFF) * i + (c1 >>> 16 & 0xFF) * j + (c2 >>> 16 & 0xFF) * k + (c3 >>> 16 & 0xFF) * l + l1) / k1 << 16 | ((c0 >>> 8 & 0xFF) * i + (c1 >>> 8 & 0xFF) * j + (c2 >>> 8 & 0xFF) * k + (c3 >>> 8 & 0xFF) * l + l1) / k1 << 8 | ((c0 >>> 0 & 0xFF) * i + (c1 >>> 0 & 0xFF) * j + (c2 >>> 0 & 0xFF) * k + (c3 >>> 0 & 0xFF) * l + l1) / k1 << 0;
/* 504 */     return i2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int blend4Simple(int c0, int c1, int c2, int c3) {
/* 509 */     int i = ((c0 >>> 24 & 0xFF) + (c1 >>> 24 & 0xFF) + (c2 >>> 24 & 0xFF) + (c3 >>> 24 & 0xFF) + 2) / 4 << 24 | ((c0 >>> 16 & 0xFF) + (c1 >>> 16 & 0xFF) + (c2 >>> 16 & 0xFF) + (c3 >>> 16 & 0xFF) + 2) / 4 << 16 | ((c0 >>> 8 & 0xFF) + (c1 >>> 8 & 0xFF) + (c2 >>> 8 & 0xFF) + (c3 >>> 8 & 0xFF) + 2) / 4 << 8 | ((c0 >>> 0 & 0xFF) + (c1 >>> 0 & 0xFF) + (c2 >>> 0 & 0xFF) + (c3 >>> 0 & 0xFF) + 2) / 4 << 0;
/* 510 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void genMipmapAlpha(int[] aint, int offset, int width, int height) {
/* 515 */     Math.min(width, height);
/* 516 */     int o2 = offset;
/* 517 */     int w2 = width;
/* 518 */     int h2 = height;
/* 519 */     int o1 = 0;
/* 520 */     int w1 = 0;
/* 521 */     int h1 = 0;
/*     */     
/*     */     int i;
/* 524 */     for (i = 0; w2 > 1 && h2 > 1; o2 = o1) {
/*     */       
/* 526 */       o1 = o2 + w2 * h2;
/* 527 */       w1 = w2 / 2;
/* 528 */       h1 = h2 / 2;
/*     */       
/* 530 */       for (int l1 = 0; l1 < h1; l1++) {
/*     */         
/* 532 */         int i2 = o1 + l1 * w1;
/* 533 */         int j2 = o2 + l1 * 2 * w2;
/*     */         
/* 535 */         for (int k2 = 0; k2 < w1; k2++)
/*     */         {
/* 537 */           aint[i2 + k2] = blend4Alpha(aint[j2 + (k2 << 1)], aint[j2 + (k2 << 1) + 1], aint[j2 + w2 + (k2 << 1)], aint[j2 + w2 + (k2 << 1) + 1]);
/*     */         }
/*     */       } 
/*     */       
/* 541 */       i++;
/* 542 */       w2 = w1;
/* 543 */       h2 = h1;
/*     */     } 
/*     */     
/* 546 */     while (i > 0) {
/*     */       
/* 548 */       i--;
/* 549 */       w2 = width >> i;
/* 550 */       h2 = height >> i;
/* 551 */       o2 = o1 - w2 * h2;
/* 552 */       int l2 = o2;
/*     */       
/* 554 */       for (int i3 = 0; i3 < h2; i3++) {
/*     */         
/* 556 */         for (int j3 = 0; j3 < w2; j3++) {
/*     */           
/* 558 */           if (aint[l2] == 0)
/*     */           {
/* 560 */             aint[l2] = aint[o1 + i3 / 2 * w1 + j3 / 2] & 0xFFFFFF;
/*     */           }
/*     */           
/* 563 */           l2++;
/*     */         } 
/*     */       } 
/*     */       
/* 567 */       o1 = o2;
/* 568 */       w1 = w2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void genMipmapSimple(int[] aint, int offset, int width, int height) {
/* 574 */     Math.min(width, height);
/* 575 */     int o2 = offset;
/* 576 */     int w2 = width;
/* 577 */     int h2 = height;
/* 578 */     int o1 = 0;
/* 579 */     int w1 = 0;
/* 580 */     int h1 = 0;
/*     */     
/*     */     int i;
/* 583 */     for (i = 0; w2 > 1 && h2 > 1; o2 = o1) {
/*     */       
/* 585 */       o1 = o2 + w2 * h2;
/* 586 */       w1 = w2 / 2;
/* 587 */       h1 = h2 / 2;
/*     */       
/* 589 */       for (int l1 = 0; l1 < h1; l1++) {
/*     */         
/* 591 */         int i2 = o1 + l1 * w1;
/* 592 */         int j2 = o2 + l1 * 2 * w2;
/*     */         
/* 594 */         for (int k2 = 0; k2 < w1; k2++)
/*     */         {
/* 596 */           aint[i2 + k2] = blend4Simple(aint[j2 + (k2 << 1)], aint[j2 + (k2 << 1) + 1], aint[j2 + w2 + (k2 << 1)], aint[j2 + w2 + (k2 << 1) + 1]);
/*     */         }
/*     */       } 
/*     */       
/* 600 */       i++;
/* 601 */       w2 = w1;
/* 602 */       h2 = h1;
/*     */     } 
/*     */     
/* 605 */     while (i > 0) {
/*     */       
/* 607 */       i--;
/* 608 */       w2 = width >> i;
/* 609 */       h2 = height >> i;
/* 610 */       o2 = o1 - w2 * h2;
/* 611 */       int l2 = o2;
/*     */       
/* 613 */       for (int i3 = 0; i3 < h2; i3++) {
/*     */         
/* 615 */         for (int j3 = 0; j3 < w2; j3++) {
/*     */           
/* 617 */           if (aint[l2] == 0)
/*     */           {
/* 619 */             aint[l2] = aint[o1 + i3 / 2 * w1 + j3 / 2] & 0xFFFFFF;
/*     */           }
/*     */           
/* 622 */           l2++;
/*     */         } 
/*     */       } 
/*     */       
/* 626 */       o1 = o2;
/* 627 */       w1 = w2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isSemiTransparent(int[] aint, int width, int height) {
/* 633 */     int i = width * height;
/*     */     
/* 635 */     if (aint[0] >>> 24 == 255 && aint[i - 1] == 0)
/*     */     {
/* 637 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 641 */     for (int j = 0; j < i; j++) {
/*     */       
/* 643 */       int k = aint[j] >>> 24;
/*     */       
/* 645 */       if (k != 0 && k != 255)
/*     */       {
/* 647 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 651 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateSubTex1(int[] src, int width, int height, int posX, int posY) {
/* 657 */     int i = 0;
/* 658 */     int j = width;
/* 659 */     int k = height;
/* 660 */     int l = posX;
/*     */     int i1;
/* 662 */     for (i1 = posY; j > 0 && k > 0; i1 /= 2) {
/*     */       
/* 664 */       GL11.glCopyTexSubImage2D(3553, i, l, i1, 0, 0, j, k);
/* 665 */       i++;
/* 666 */       j /= 2;
/* 667 */       k /= 2;
/* 668 */       l /= 2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setupTexture(MultiTexID multiTex, int[] src, int width, int height, boolean linear, boolean clamp) {
/* 674 */     int i = linear ? 9729 : 9728;
/* 675 */     int j = clamp ? 33071 : 10497;
/* 676 */     int k = width * height;
/* 677 */     IntBuffer intbuffer = getIntBuffer(k);
/* 678 */     intbuffer.clear();
/* 679 */     intbuffer.put(src, 0, k).position(0).limit(k);
/* 680 */     GlStateManager.bindTexture(multiTex.base);
/* 681 */     GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 32993, 33639, intbuffer);
/* 682 */     GL11.glTexParameteri(3553, 10241, i);
/* 683 */     GL11.glTexParameteri(3553, 10240, i);
/* 684 */     GL11.glTexParameteri(3553, 10242, j);
/* 685 */     GL11.glTexParameteri(3553, 10243, j);
/* 686 */     intbuffer.put(src, k, k).position(0).limit(k);
/* 687 */     GlStateManager.bindTexture(multiTex.norm);
/* 688 */     GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 32993, 33639, intbuffer);
/* 689 */     GL11.glTexParameteri(3553, 10241, i);
/* 690 */     GL11.glTexParameteri(3553, 10240, i);
/* 691 */     GL11.glTexParameteri(3553, 10242, j);
/* 692 */     GL11.glTexParameteri(3553, 10243, j);
/* 693 */     intbuffer.put(src, k << 1, k).position(0).limit(k);
/* 694 */     GlStateManager.bindTexture(multiTex.spec);
/* 695 */     GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 32993, 33639, intbuffer);
/* 696 */     GL11.glTexParameteri(3553, 10241, i);
/* 697 */     GL11.glTexParameteri(3553, 10240, i);
/* 698 */     GL11.glTexParameteri(3553, 10242, j);
/* 699 */     GL11.glTexParameteri(3553, 10243, j);
/* 700 */     GlStateManager.bindTexture(multiTex.base);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateSubImage(MultiTexID multiTex, int[] src, int width, int height, int posX, int posY, boolean linear, boolean clamp) {
/* 705 */     int i = width * height;
/* 706 */     IntBuffer intbuffer = getIntBuffer(i);
/* 707 */     intbuffer.clear();
/* 708 */     intbuffer.put(src, 0, i);
/* 709 */     intbuffer.position(0).limit(i);
/* 710 */     GlStateManager.bindTexture(multiTex.base);
/* 711 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 712 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 713 */     GL11.glTexParameteri(3553, 10242, 10497);
/* 714 */     GL11.glTexParameteri(3553, 10243, 10497);
/* 715 */     GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intbuffer);
/*     */     
/* 717 */     if (src.length == i * 3) {
/*     */       
/* 719 */       intbuffer.clear();
/* 720 */       intbuffer.put(src, i, i).position(0);
/* 721 */       intbuffer.position(0).limit(i);
/*     */     } 
/*     */     
/* 724 */     GlStateManager.bindTexture(multiTex.norm);
/* 725 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 726 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 727 */     GL11.glTexParameteri(3553, 10242, 10497);
/* 728 */     GL11.glTexParameteri(3553, 10243, 10497);
/* 729 */     GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intbuffer);
/*     */     
/* 731 */     if (src.length == i * 3) {
/*     */       
/* 733 */       intbuffer.clear();
/* 734 */       intbuffer.put(src, i << 1, i);
/* 735 */       intbuffer.position(0).limit(i);
/*     */     } 
/*     */     
/* 738 */     GlStateManager.bindTexture(multiTex.spec);
/* 739 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 740 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 741 */     GL11.glTexParameteri(3553, 10242, 10497);
/* 742 */     GL11.glTexParameteri(3553, 10243, 10497);
/* 743 */     GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intbuffer);
/* 744 */     GlStateManager.setActiveTexture(33984);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ResourceLocation getNSMapLocation(ResourceLocation location, String mapName) {
/* 749 */     if (location == null)
/*     */     {
/* 751 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 755 */     String s = location.getResourcePath();
/* 756 */     String[] astring = s.split(".png");
/* 757 */     String s1 = astring[0];
/* 758 */     return new ResourceLocation(location.getResourceDomain(), s1 + "_" + mapName + ".png");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void loadNSMap(IResourceManager manager, ResourceLocation location, int width, int height, int[] aint) {
/* 764 */     if (Shaders.configNormalMap)
/*     */     {
/* 766 */       loadNSMap1(manager, getNSMapLocation(location, "n"), width, height, aint, width * height, -8421377);
/*     */     }
/*     */     
/* 769 */     if (Shaders.configSpecularMap)
/*     */     {
/* 771 */       loadNSMap1(manager, getNSMapLocation(location, "s"), width, height, aint, width * height * 2, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void loadNSMap1(IResourceManager manager, ResourceLocation location, int width, int height, int[] aint, int offset, int defaultColor) {
/* 777 */     if (!loadNSMapFile(manager, location, width, height, aint, offset))
/*     */     {
/* 779 */       Arrays.fill(aint, offset, offset + width * height, defaultColor);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean loadNSMapFile(IResourceManager manager, ResourceLocation location, int width, int height, int[] aint, int offset) {
/* 785 */     if (location == null)
/*     */     {
/* 787 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 793 */       IResource iresource = manager.getResource(location);
/* 794 */       BufferedImage bufferedimage = ImageIO.read(iresource.getInputStream());
/*     */       
/* 796 */       if (bufferedimage == null)
/*     */       {
/* 798 */         return false;
/*     */       }
/* 800 */       if (bufferedimage.getWidth() == width && bufferedimage.getHeight() == height) {
/*     */         
/* 802 */         bufferedimage.getRGB(0, 0, width, height, aint, offset, width);
/* 803 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 807 */       return false;
/*     */     
/*     */     }
/* 810 */     catch (IOException var8) {
/*     */       
/* 812 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int loadSimpleTexture(int textureID, BufferedImage bufferedimage, boolean linear, boolean clamp, IResourceManager resourceManager, ResourceLocation location, MultiTexID multiTex) {
/* 819 */     int i = bufferedimage.getWidth();
/* 820 */     int j = bufferedimage.getHeight();
/* 821 */     int k = i * j;
/* 822 */     int[] aint = getIntArray(k * 3);
/* 823 */     bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
/* 824 */     loadNSMap(resourceManager, location, i, j, aint);
/* 825 */     setupTexture(multiTex, aint, i, j, linear, clamp);
/* 826 */     return textureID;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void mergeImage(int[] aint, int dstoff, int srcoff, int size) {}
/*     */ 
/*     */   
/*     */   public static int blendColor(int color1, int color2, int factor1) {
/* 835 */     int i = 255 - factor1;
/* 836 */     return ((color1 >>> 24 & 0xFF) * factor1 + (color2 >>> 24 & 0xFF) * i) / 255 << 24 | ((color1 >>> 16 & 0xFF) * factor1 + (color2 >>> 16 & 0xFF) * i) / 255 << 16 | ((color1 >>> 8 & 0xFF) * factor1 + (color2 >>> 8 & 0xFF) * i) / 255 << 8 | ((color1 >>> 0 & 0xFF) * factor1 + (color2 >>> 0 & 0xFF) * i) / 255 << 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadLayeredTexture(LayeredTexture tex, IResourceManager manager, List list) {
/* 841 */     int i = 0;
/* 842 */     int j = 0;
/* 843 */     int k = 0;
/* 844 */     int[] aint = null;
/*     */     
/* 846 */     for (Object e : list) {
/*     */       
/* 848 */       String s = (String)e;
/* 849 */       if (s != null) {
/*     */         
/*     */         try {
/*     */           
/* 853 */           ResourceLocation resourcelocation = new ResourceLocation(s);
/* 854 */           InputStream inputstream = manager.getResource(resourcelocation).getInputStream();
/* 855 */           BufferedImage bufferedimage = ImageIO.read(inputstream);
/*     */           
/* 857 */           if (k == 0) {
/*     */             
/* 859 */             i = bufferedimage.getWidth();
/* 860 */             j = bufferedimage.getHeight();
/* 861 */             k = i * j;
/* 862 */             aint = createAIntImage(k, 0);
/*     */           } 
/*     */           
/* 865 */           int[] aint1 = getIntArray(k * 3);
/* 866 */           bufferedimage.getRGB(0, 0, i, j, aint1, 0, i);
/* 867 */           loadNSMap(manager, resourcelocation, i, j, aint1);
/*     */           
/* 869 */           for (int l = 0; l < k; l++)
/*     */           {
/* 871 */             int i1 = aint1[l] >>> 24 & 0xFF;
/* 872 */             aint[0 + l] = blendColor(aint1[0 + l], aint[0 + l], i1);
/* 873 */             aint[k + l] = blendColor(aint1[k + l], aint[k + l], i1);
/* 874 */             aint[(k << 1) + l] = blendColor(aint1[(k << 1) + l], aint[(k << 1) + l], i1);
/*     */           }
/*     */         
/* 877 */         } catch (IOException ioexception) {
/*     */           
/* 879 */           ioexception.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 884 */     setupTexture(tex.getMultiTexID(), aint, i, j, false, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateTextureMinMagFilter() {
/* 889 */     TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
/* 890 */     ITextureObject itextureobject = texturemanager.getTexture(TextureMap.locationBlocksTexture);
/*     */     
/* 892 */     if (itextureobject != null) {
/*     */       
/* 894 */       MultiTexID multitexid = itextureobject.getMultiTexID();
/* 895 */       GlStateManager.bindTexture(multitexid.base);
/* 896 */       GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilB]);
/* 897 */       GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilB]);
/* 898 */       GlStateManager.bindTexture(multitexid.norm);
/* 899 */       GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilN]);
/* 900 */       GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilN]);
/* 901 */       GlStateManager.bindTexture(multitexid.spec);
/* 902 */       GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilS]);
/* 903 */       GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilS]);
/* 904 */       GlStateManager.bindTexture(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[][] getFrameTexData(int[][] src, int width, int height, int frameIndex) {
/* 910 */     int i = src.length;
/* 911 */     int[][] aint = new int[i][];
/*     */     
/* 913 */     for (int j = 0; j < i; j++) {
/*     */       
/* 915 */       int[] aint1 = src[j];
/*     */       
/* 917 */       if (aint1 != null) {
/*     */         
/* 919 */         int k = (width >> j) * (height >> j);
/* 920 */         int[] aint2 = new int[k * 3];
/* 921 */         aint[j] = aint2;
/* 922 */         int l = aint1.length / 3;
/* 923 */         int i1 = k * frameIndex;
/* 924 */         int j1 = 0;
/* 925 */         System.arraycopy(aint1, i1, aint2, j1, k);
/* 926 */         i1 += l;
/* 927 */         j1 += k;
/* 928 */         System.arraycopy(aint1, i1, aint2, j1, k);
/* 929 */         i1 += l;
/* 930 */         j1 += k;
/* 931 */         System.arraycopy(aint1, i1, aint2, j1, k);
/*     */       } 
/*     */     } 
/*     */     
/* 935 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[][] prepareAF(TextureAtlasSprite tas, int[][] src, int width, int height) {
/* 940 */     boolean flag = true;
/* 941 */     return src;
/*     */   }
/*     */   
/*     */   public static void fixTransparentColor(TextureAtlasSprite tas, int[] aint) {}
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\ShadersTex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */