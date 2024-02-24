/*     */ package com.me.theresa.fontRenderer.font.opengl;
/*     */ 
/*     */ import com.me.theresa.fontRenderer.font.log.Log;
/*     */ import com.me.theresa.fontRenderer.font.opengl.renderer.Renderer;
/*     */ import com.me.theresa.fontRenderer.font.opengl.renderer.SGL;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.IntBuffer;
/*     */ import org.lwjgl.BufferUtils;
/*     */ 
/*     */ 
/*     */ public class TextureImpl
/*     */   implements Texture
/*     */ {
/*  15 */   protected static SGL GL = Renderer.get();
/*     */   
/*     */   static Texture lastBind;
/*     */   private int target;
/*     */   private int textureID;
/*     */   
/*     */   public static Texture getLastBind() {
/*  22 */     return lastBind;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int height;
/*     */ 
/*     */   
/*     */   private int width;
/*     */ 
/*     */   
/*     */   private int texWidth;
/*     */ 
/*     */   
/*     */   private int texHeight;
/*     */ 
/*     */   
/*     */   private float widthRatio;
/*     */ 
/*     */   
/*     */   private float heightRatio;
/*     */   
/*     */   private boolean alpha;
/*     */   
/*     */   String ref;
/*     */   
/*     */   private String cacheName;
/*     */   
/*     */   private ReloadData reloadData;
/*     */ 
/*     */   
/*     */   protected TextureImpl() {}
/*     */ 
/*     */   
/*     */   public TextureImpl(String ref, int target, int textureID) {
/*  57 */     this.target = target;
/*  58 */     this.ref = ref;
/*  59 */     this.textureID = textureID;
/*  60 */     lastBind = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCacheName(String cacheName) {
/*  65 */     this.cacheName = cacheName;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAlpha() {
/*  70 */     return this.alpha;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTextureRef() {
/*  75 */     return this.ref;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAlpha(boolean alpha) {
/*  80 */     this.alpha = alpha;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindNone() {
/*  85 */     lastBind = null;
/*  86 */     GL.glDisable(3553);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void unbind() {
/*  91 */     lastBind = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind() {
/*  96 */     if (lastBind != this) {
/*  97 */       lastBind = this;
/*  98 */       GL.glEnable(3553);
/*  99 */       GL.glBindTexture(this.target, this.textureID);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHeight(int height) {
/* 105 */     this.height = height;
/* 106 */     setHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWidth(int width) {
/* 111 */     this.width = width;
/* 112 */     setWidth();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getImageHeight() {
/* 117 */     return this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getImageWidth() {
/* 122 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getHeight() {
/* 127 */     return this.heightRatio;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWidth() {
/* 132 */     return this.widthRatio;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTextureHeight() {
/* 137 */     return this.texHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTextureWidth() {
/* 142 */     return this.texWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTextureHeight(int texHeight) {
/* 147 */     this.texHeight = texHeight;
/* 148 */     setHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTextureWidth(int texWidth) {
/* 153 */     this.texWidth = texWidth;
/* 154 */     setWidth();
/*     */   }
/*     */ 
/*     */   
/*     */   private void setHeight() {
/* 159 */     if (this.texHeight != 0) {
/* 160 */       this.heightRatio = this.height / this.texHeight;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void setWidth() {
/* 166 */     if (this.texWidth != 0) {
/* 167 */       this.widthRatio = this.width / this.texWidth;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void release() {
/* 173 */     IntBuffer texBuf = createIntBuffer(1);
/* 174 */     texBuf.put(this.textureID);
/* 175 */     texBuf.flip();
/*     */     
/* 177 */     GL.glDeleteTextures(texBuf);
/*     */     
/* 179 */     if (lastBind == this) {
/* 180 */       bindNone();
/*     */     }
/*     */     
/* 183 */     if (this.cacheName != null) {
/* 184 */       InternalTextureLoader.get().clear(this.cacheName);
/*     */     } else {
/* 186 */       InternalTextureLoader.get().clear(this.ref);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTextureID() {
/* 192 */     return this.textureID;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTextureID(int textureID) {
/* 197 */     this.textureID = textureID;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IntBuffer createIntBuffer(int size) {
/* 202 */     ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
/* 203 */     temp.order(ByteOrder.nativeOrder());
/*     */     
/* 205 */     return temp.asIntBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getTextureData() {
/* 210 */     ByteBuffer buffer = BufferUtils.createByteBuffer((hasAlpha() ? 4 : 3) * this.texWidth * this.texHeight);
/* 211 */     bind();
/* 212 */     GL.glGetTexImage(3553, 0, hasAlpha() ? 6408 : 6407, 5121, buffer);
/*     */     
/* 214 */     byte[] data = new byte[buffer.limit()];
/* 215 */     buffer.get(data);
/* 216 */     buffer.clear();
/*     */     
/* 218 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTextureFilter(int textureFilter) {
/* 223 */     bind();
/* 224 */     GL.glTexParameteri(this.target, 10241, textureFilter);
/* 225 */     GL.glTexParameteri(this.target, 10240, textureFilter);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextureData(int srcPixelFormat, int componentCount, int minFilter, int magFilter, ByteBuffer textureBuffer) {
/* 231 */     this.reloadData = new ReloadData();
/* 232 */     this.reloadData.srcPixelFormat = srcPixelFormat;
/* 233 */     this.reloadData.componentCount = componentCount;
/* 234 */     this.reloadData.minFilter = minFilter;
/* 235 */     this.reloadData.magFilter = magFilter;
/* 236 */     this.reloadData.textureBuffer = textureBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reload() {
/* 241 */     if (this.reloadData != null) {
/* 242 */       this.textureID = this.reloadData.reload();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class ReloadData
/*     */   {
/*     */     int srcPixelFormat;
/*     */ 
/*     */     
/*     */     int componentCount;
/*     */ 
/*     */     
/*     */     int minFilter;
/*     */     
/*     */     int magFilter;
/*     */     
/*     */     ByteBuffer textureBuffer;
/*     */ 
/*     */     
/*     */     public int reload() {
/* 264 */       Log.error("Reloading texture: " + TextureImpl.this.ref);
/* 265 */       return InternalTextureLoader.get().reload(TextureImpl.this, this.srcPixelFormat, this.componentCount, this.minFilter, this.magFilter, this.textureBuffer);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\TextureImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */