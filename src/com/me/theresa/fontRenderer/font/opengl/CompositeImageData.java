/*     */ package com.me.theresa.fontRenderer.font.opengl;
/*     */ 
/*     */ import com.me.theresa.fontRenderer.font.log.Log;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class CompositeImageData
/*     */   implements LoadableImageData
/*     */ {
/*  13 */   private final ArrayList sources = new ArrayList();
/*     */   
/*     */   private LoadableImageData picked;
/*     */ 
/*     */   
/*     */   public void add(LoadableImageData data) {
/*  19 */     this.sources.add(data);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis) throws IOException {
/*  24 */     return loadImage(fis, false, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis, boolean flipped, int[] transparent) throws IOException {
/*  29 */     return loadImage(fis, flipped, false, transparent);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream is, boolean flipped, boolean forceAlpha, int[] transparent) throws IOException {
/*  34 */     CompositeIOException exception = new CompositeIOException();
/*  35 */     ByteBuffer buffer = null;
/*     */     
/*  37 */     BufferedInputStream in = new BufferedInputStream(is, is.available());
/*  38 */     in.mark(is.available());
/*     */ 
/*     */     
/*  41 */     for (int i = 0; i < this.sources.size(); i++) {
/*  42 */       in.reset();
/*     */       try {
/*  44 */         LoadableImageData data = this.sources.get(i);
/*     */         
/*  46 */         buffer = data.loadImage(in, flipped, forceAlpha, transparent);
/*  47 */         this.picked = data;
/*     */         break;
/*  49 */       } catch (Exception e) {
/*  50 */         Log.warn(this.sources.get(i).getClass() + " failed to read the data", e);
/*  51 */         exception.addException(e);
/*     */       } 
/*     */     } 
/*     */     
/*  55 */     if (this.picked == null) {
/*  56 */       throw exception;
/*     */     }
/*     */     
/*  59 */     return buffer;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkPicked() {
/*  64 */     if (this.picked == null) {
/*  65 */       throw new RuntimeException("Attempt to make use of uninitialised or invalid composite image data");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDepth() {
/*  71 */     checkPicked();
/*     */     
/*  73 */     return this.picked.getDepth();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  78 */     checkPicked();
/*     */     
/*  80 */     return this.picked.getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer getImageBufferData() {
/*  85 */     checkPicked();
/*     */     
/*  87 */     return this.picked.getImageBufferData();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTexHeight() {
/*  92 */     checkPicked();
/*     */     
/*  94 */     return this.picked.getTexHeight();
/*     */   }
/*     */   
/*     */   public int getTexWidth() {
/*  98 */     checkPicked();
/*     */     
/* 100 */     return this.picked.getTexWidth();
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 104 */     checkPicked();
/*     */     
/* 106 */     return this.picked.getWidth();
/*     */   }
/*     */   
/*     */   public void configureEdging(boolean edging) {
/* 110 */     for (int i = 0; i < this.sources.size(); i++)
/* 111 */       ((LoadableImageData)this.sources.get(i)).configureEdging(edging); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\CompositeImageData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */