/*     */ package com.me.theresa.fontRenderer.font.opengl;
/*     */ 
/*     */ import com.me.theresa.fontRenderer.font.opengl.renderer.Renderer;
/*     */ import com.me.theresa.fontRenderer.font.opengl.renderer.SGL;
/*     */ import com.me.theresa.fontRenderer.font.util.ResourceLoader;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.IntBuffer;
/*     */ import java.nio.file.Files;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import org.lwjgl.BufferUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InternalTextureLoader
/*     */ {
/*  23 */   protected static SGL GL = Renderer.get();
/*     */   
/*  25 */   private static final InternalTextureLoader loader = new InternalTextureLoader();
/*     */ 
/*     */   
/*     */   public static InternalTextureLoader get() {
/*  29 */     return loader;
/*     */   }
/*     */ 
/*     */   
/*  33 */   private final HashMap texturesLinear = new HashMap<>();
/*     */   
/*  35 */   private final HashMap texturesNearest = new HashMap<>();
/*     */   
/*  37 */   private int dstPixelFormat = 6408;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean deferred;
/*     */ 
/*     */   
/*     */   private boolean holdTextureData;
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHoldTextureData(boolean holdTextureData) {
/*  49 */     this.holdTextureData = holdTextureData;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDeferredLoading(boolean deferred) {
/*  54 */     this.deferred = deferred;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDeferredLoading() {
/*  59 */     return this.deferred;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear(String name) {
/*  64 */     this.texturesLinear.remove(name);
/*  65 */     this.texturesNearest.remove(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  70 */     this.texturesLinear.clear();
/*  71 */     this.texturesNearest.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void set16BitMode() {
/*  76 */     this.dstPixelFormat = 32859;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int createTextureID() {
/*  81 */     IntBuffer tmp = createIntBuffer(1);
/*  82 */     GL.glGenTextures(tmp);
/*  83 */     return tmp.get(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Texture getTexture(File source, boolean flipped, int filter) throws IOException {
/*  88 */     String resourceName = source.getAbsolutePath();
/*  89 */     InputStream in = Files.newInputStream(source.toPath(), new java.nio.file.OpenOption[0]);
/*     */     
/*  91 */     return getTexture(in, resourceName, flipped, filter, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Texture getTexture(File source, boolean flipped, int filter, int[] transparent) throws IOException {
/*  96 */     String resourceName = source.getAbsolutePath();
/*  97 */     InputStream in = Files.newInputStream(source.toPath(), new java.nio.file.OpenOption[0]);
/*     */     
/*  99 */     return getTexture(in, resourceName, flipped, filter, transparent);
/*     */   }
/*     */ 
/*     */   
/*     */   public Texture getTexture(String resourceName, boolean flipped, int filter) throws IOException {
/* 104 */     InputStream in = ResourceLoader.getResourceAsStream(resourceName);
/*     */     
/* 106 */     return getTexture(in, resourceName, flipped, filter, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Texture getTexture(String resourceName, boolean flipped, int filter, int[] transparent) throws IOException {
/* 111 */     InputStream in = ResourceLoader.getResourceAsStream(resourceName);
/*     */     
/* 113 */     return getTexture(in, resourceName, flipped, filter, transparent);
/*     */   }
/*     */   
/*     */   public Texture getTexture(InputStream in, String resourceName, boolean flipped, int filter) throws IOException {
/* 117 */     return getTexture(in, resourceName, flipped, filter, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureImpl getTexture(InputStream in, String resourceName, boolean flipped, int filter, int[] transparent) throws IOException {
/* 122 */     if (this.deferred) {
/* 123 */       return new DeferredTexture(in, resourceName, flipped, filter, transparent);
/*     */     }
/*     */     
/* 126 */     HashMap<String, TextureImpl> hash = this.texturesLinear;
/* 127 */     if (filter == 9728) {
/* 128 */       hash = this.texturesNearest;
/*     */     }
/*     */     
/* 131 */     String resName = resourceName;
/* 132 */     if (transparent != null) {
/* 133 */       resName = resName + ":" + transparent[0] + ":" + transparent[1] + ":" + transparent[2];
/*     */     }
/* 135 */     resName = resName + ":" + flipped;
/*     */     
/* 137 */     if (this.holdTextureData) {
/* 138 */       TextureImpl textureImpl = (TextureImpl)hash.get(resName);
/* 139 */       if (textureImpl != null) {
/* 140 */         return textureImpl;
/*     */       }
/*     */     } else {
/* 143 */       SoftReference<TextureImpl> ref = (SoftReference)hash.get(resName);
/* 144 */       if (ref != null) {
/* 145 */         TextureImpl textureImpl = ref.get();
/* 146 */         if (textureImpl != null) {
/* 147 */           return textureImpl;
/*     */         }
/* 149 */         hash.remove(resName);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 156 */       GL.glGetError();
/* 157 */     } catch (NullPointerException e) {
/* 158 */       throw new RuntimeException("Image based resources must be loaded as part of init() or the game loop. They cannot be loaded before initialisation.");
/*     */     } 
/*     */     
/* 161 */     TextureImpl tex = getTexture(in, resourceName, 3553, filter, filter, flipped, transparent);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     tex.setCacheName(resName);
/* 167 */     if (this.holdTextureData) {
/* 168 */       hash.put(resName, tex);
/*     */     } else {
/* 170 */       hash.put(resName, (TextureImpl)new SoftReference<>(tex));
/*     */     } 
/*     */     
/* 173 */     return tex;
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
/*     */   private TextureImpl getTexture(InputStream in, String resourceName, int target, int magFilter, int minFilter, boolean flipped, int[] transparent) throws IOException {
/* 185 */     LoadableImageData imageData = ImageDataFactory.getImageDataFor(resourceName);
/* 186 */     ByteBuffer textureBuffer = imageData.loadImage(new BufferedInputStream(in), flipped, transparent);
/*     */     
/* 188 */     int textureID = createTextureID();
/* 189 */     TextureImpl texture = new TextureImpl(resourceName, target, textureID);
/*     */     
/* 191 */     GL.glBindTexture(target, textureID);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 200 */     int width = imageData.getWidth();
/* 201 */     int height = imageData.getHeight();
/* 202 */     boolean hasAlpha = (imageData.getDepth() == 32);
/*     */     
/* 204 */     texture.setTextureWidth(imageData.getTexWidth());
/* 205 */     texture.setTextureHeight(imageData.getTexHeight());
/*     */     
/* 207 */     int texWidth = texture.getTextureWidth();
/* 208 */     int texHeight = texture.getTextureHeight();
/*     */     
/* 210 */     IntBuffer temp = BufferUtils.createIntBuffer(16);
/* 211 */     GL.glGetInteger(3379, temp);
/* 212 */     int max = temp.get(0);
/* 213 */     if (texWidth > max || texHeight > max) {
/* 214 */       throw new IOException("Attempt to allocate a texture to big for the current hardware");
/*     */     }
/*     */     
/* 217 */     int srcPixelFormat = hasAlpha ? 6408 : 6407;
/* 218 */     int componentCount = hasAlpha ? 4 : 3;
/*     */     
/* 220 */     texture.setWidth(width);
/* 221 */     texture.setHeight(height);
/* 222 */     texture.setAlpha(hasAlpha);
/*     */     
/* 224 */     if (this.holdTextureData) {
/* 225 */       texture.setTextureData(srcPixelFormat, componentCount, minFilter, magFilter, textureBuffer);
/*     */     }
/*     */     
/* 228 */     GL.glTexParameteri(target, 10241, minFilter);
/* 229 */     GL.glTexParameteri(target, 10240, magFilter);
/*     */ 
/*     */     
/* 232 */     GL.glTexImage2D(target, 0, this.dstPixelFormat, 
/*     */ 
/*     */         
/* 235 */         get2Fold(width), 
/* 236 */         get2Fold(height), 0, srcPixelFormat, 5121, textureBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 242 */     return texture;
/*     */   }
/*     */ 
/*     */   
/*     */   public Texture createTexture(int width, int height) throws IOException {
/* 247 */     return createTexture(width, height, 9728);
/*     */   }
/*     */ 
/*     */   
/*     */   public Texture createTexture(int width, int height, int filter) throws IOException {
/* 252 */     ImageData ds = new EmptyImageData(width, height);
/*     */     
/* 254 */     return getTexture(ds, filter);
/*     */   }
/*     */ 
/*     */   
/*     */   public Texture getTexture(ImageData dataSource, int filter) throws IOException {
/* 259 */     int target = 3553;
/*     */ 
/*     */     
/* 262 */     ByteBuffer textureBuffer = dataSource.getImageBufferData();
/*     */ 
/*     */     
/* 265 */     int textureID = createTextureID();
/* 266 */     TextureImpl texture = new TextureImpl("generated:" + dataSource, target, textureID);
/*     */     
/* 268 */     int minFilter = filter;
/* 269 */     int magFilter = filter;
/* 270 */     boolean flipped = false;
/*     */ 
/*     */     
/* 273 */     GL.glBindTexture(target, textureID);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 282 */     int width = dataSource.getWidth();
/* 283 */     int height = dataSource.getHeight();
/* 284 */     boolean hasAlpha = (dataSource.getDepth() == 32);
/*     */     
/* 286 */     texture.setTextureWidth(dataSource.getTexWidth());
/* 287 */     texture.setTextureHeight(dataSource.getTexHeight());
/*     */     
/* 289 */     int texWidth = texture.getTextureWidth();
/* 290 */     int texHeight = texture.getTextureHeight();
/*     */     
/* 292 */     int srcPixelFormat = hasAlpha ? 6408 : 6407;
/* 293 */     int componentCount = hasAlpha ? 4 : 3;
/*     */     
/* 295 */     texture.setWidth(width);
/* 296 */     texture.setHeight(height);
/* 297 */     texture.setAlpha(hasAlpha);
/*     */     
/* 299 */     IntBuffer temp = BufferUtils.createIntBuffer(16);
/* 300 */     GL.glGetInteger(3379, temp);
/* 301 */     int max = temp.get(0);
/* 302 */     if (texWidth > max || texHeight > max) {
/* 303 */       throw new IOException("Attempt to allocate a texture to big for the current hardware");
/*     */     }
/*     */     
/* 306 */     if (this.holdTextureData) {
/* 307 */       texture.setTextureData(srcPixelFormat, componentCount, minFilter, magFilter, textureBuffer);
/*     */     }
/*     */     
/* 310 */     GL.glTexParameteri(target, 10241, minFilter);
/* 311 */     GL.glTexParameteri(target, 10240, magFilter);
/*     */ 
/*     */     
/* 314 */     GL.glTexImage2D(target, 0, this.dstPixelFormat, 
/*     */ 
/*     */         
/* 317 */         get2Fold(width), 
/* 318 */         get2Fold(height), 0, srcPixelFormat, 5121, textureBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 324 */     return texture;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int get2Fold(int fold) {
/* 329 */     int ret = 2;
/* 330 */     while (ret < fold) {
/* 331 */       ret <<= 1;
/*     */     }
/* 333 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static IntBuffer createIntBuffer(int size) {
/* 338 */     ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
/* 339 */     temp.order(ByteOrder.nativeOrder());
/*     */     
/* 341 */     return temp.asIntBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reload() {
/* 346 */     Iterator<TextureImpl> texs = this.texturesLinear.values().iterator();
/* 347 */     while (texs.hasNext()) {
/* 348 */       ((TextureImpl)texs.next()).reload();
/*     */     }
/* 350 */     texs = this.texturesNearest.values().iterator();
/* 351 */     while (texs.hasNext()) {
/* 352 */       ((TextureImpl)texs.next()).reload();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int reload(TextureImpl texture, int srcPixelFormat, int componentCount, int minFilter, int magFilter, ByteBuffer textureBuffer) {
/* 358 */     int target = 3553;
/* 359 */     int textureID = createTextureID();
/* 360 */     GL.glBindTexture(target, textureID);
/*     */     
/* 362 */     GL.glTexParameteri(target, 10241, minFilter);
/* 363 */     GL.glTexParameteri(target, 10240, magFilter);
/*     */ 
/*     */     
/* 366 */     GL.glTexImage2D(target, 0, this.dstPixelFormat, texture
/*     */ 
/*     */         
/* 369 */         .getTextureWidth(), texture
/* 370 */         .getTextureHeight(), 0, srcPixelFormat, 5121, textureBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 376 */     return textureID;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\InternalTextureLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */