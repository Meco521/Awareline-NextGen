/*     */ package com.me.theresa.fontRenderer.font.opengl;
/*     */ 
/*     */ import com.me.theresa.fontRenderer.font.util.DeferredResource;
/*     */ import com.me.theresa.fontRenderer.font.util.LoadingList;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeferredTexture
/*     */   extends TextureImpl
/*     */   implements DeferredResource
/*     */ {
/*     */   private final InputStream in;
/*     */   private final String resourceName;
/*     */   private final boolean flipped;
/*     */   private final int filter;
/*     */   private TextureImpl target;
/*     */   private final int[] trans;
/*     */   
/*     */   public DeferredTexture(InputStream in, String resourceName, boolean flipped, int filter, int[] trans) {
/*  25 */     this.in = in;
/*  26 */     this.resourceName = resourceName;
/*  27 */     this.flipped = flipped;
/*  28 */     this.filter = filter;
/*  29 */     this.trans = trans;
/*     */     
/*  31 */     LoadingList.get().add(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void load() throws IOException {
/*  36 */     boolean before = InternalTextureLoader.get().isDeferredLoading();
/*  37 */     InternalTextureLoader.get().setDeferredLoading(false);
/*  38 */     this.target = InternalTextureLoader.get().getTexture(this.in, this.resourceName, this.flipped, this.filter, this.trans);
/*  39 */     InternalTextureLoader.get().setDeferredLoading(before);
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkTarget() {
/*  44 */     if (this.target == null) {
/*     */       try {
/*  46 */         load();
/*  47 */         LoadingList.get().remove(this);
/*     */         return;
/*  49 */       } catch (IOException e) {
/*  50 */         throw new RuntimeException("Attempt to use deferred texture before loading and resource not found: " + this.resourceName);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind() {
/*  57 */     checkTarget();
/*     */     
/*  59 */     this.target.bind();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getHeight() {
/*  64 */     checkTarget();
/*     */     
/*  66 */     return this.target.getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getImageHeight() {
/*  71 */     checkTarget();
/*  72 */     return this.target.getImageHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getImageWidth() {
/*  77 */     checkTarget();
/*  78 */     return this.target.getImageWidth();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTextureHeight() {
/*  83 */     checkTarget();
/*  84 */     return this.target.getTextureHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTextureID() {
/*  89 */     checkTarget();
/*  90 */     return this.target.getTextureID();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTextureRef() {
/*  95 */     checkTarget();
/*  96 */     return this.target.getTextureRef();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTextureWidth() {
/* 101 */     checkTarget();
/* 102 */     return this.target.getTextureWidth();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWidth() {
/* 107 */     checkTarget();
/* 108 */     return this.target.getWidth();
/*     */   }
/*     */ 
/*     */   
/*     */   public void release() {
/* 113 */     checkTarget();
/* 114 */     this.target.release();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAlpha(boolean alpha) {
/* 119 */     checkTarget();
/* 120 */     this.target.setAlpha(alpha);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHeight(int height) {
/* 125 */     checkTarget();
/* 126 */     this.target.setHeight(height);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTextureHeight(int texHeight) {
/* 131 */     checkTarget();
/* 132 */     this.target.setTextureHeight(texHeight);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTextureID(int textureID) {
/* 137 */     checkTarget();
/* 138 */     this.target.setTextureID(textureID);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTextureWidth(int texWidth) {
/* 143 */     checkTarget();
/* 144 */     this.target.setTextureWidth(texWidth);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWidth(int width) {
/* 149 */     checkTarget();
/* 150 */     this.target.setWidth(width);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getTextureData() {
/* 155 */     checkTarget();
/* 156 */     return this.target.getTextureData();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 161 */     return this.resourceName;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAlpha() {
/* 166 */     checkTarget();
/* 167 */     return this.target.hasAlpha();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTextureFilter(int textureFilter) {
/* 172 */     checkTarget();
/* 173 */     this.target.setTextureFilter(textureFilter);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\DeferredTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */