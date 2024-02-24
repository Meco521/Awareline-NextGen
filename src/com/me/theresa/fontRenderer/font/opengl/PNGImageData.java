/*     */ package com.me.theresa.fontRenderer.font.opengl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import org.lwjgl.BufferUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PNGImageData
/*     */   implements LoadableImageData
/*     */ {
/*     */   private int width;
/*     */   private int height;
/*     */   private int texHeight;
/*     */   private int texWidth;
/*     */   private PNGDecoder decoder;
/*     */   private int bitDepth;
/*     */   private ByteBuffer scratch;
/*     */   
/*     */   public int getDepth() {
/*  28 */     return this.bitDepth;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer getImageBufferData() {
/*  33 */     return this.scratch;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTexHeight() {
/*  38 */     return this.texHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTexWidth() {
/*  43 */     return this.texWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis) throws IOException {
/*  48 */     return loadImage(fis, false, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis, boolean flipped, int[] transparent) throws IOException {
/*  53 */     return loadImage(fis, flipped, false, transparent);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis, boolean flipped, boolean forceAlpha, int[] transparent) throws IOException {
/*  58 */     if (transparent != null) {
/*  59 */       forceAlpha = true;
/*  60 */       throw new IOException("Transparent color not support in custom PNG Decoder");
/*     */     } 
/*     */     
/*  63 */     PNGDecoder decoder = new PNGDecoder(fis);
/*     */     
/*  65 */     if (!decoder.isRGB()) {
/*  66 */       throw new IOException("Only RGB formatted images are supported by the PNGLoader");
/*     */     }
/*     */     
/*  69 */     this.width = decoder.getWidth();
/*  70 */     this.height = decoder.getHeight();
/*  71 */     this.texWidth = get2Fold(this.width);
/*  72 */     this.texHeight = get2Fold(this.height);
/*     */     
/*  74 */     int perPixel = decoder.hasAlpha() ? 4 : 3;
/*  75 */     this.bitDepth = decoder.hasAlpha() ? 32 : 24;
/*     */ 
/*     */     
/*  78 */     this.scratch = BufferUtils.createByteBuffer(this.texWidth * this.texHeight * perPixel);
/*  79 */     decoder.decode(this.scratch, this.texWidth * perPixel, (perPixel == 4) ? PNGDecoder.RGBA : PNGDecoder.RGB);
/*     */     
/*  81 */     if (this.height < this.texHeight - 1) {
/*  82 */       int topOffset = (this.texHeight - 1) * this.texWidth * perPixel;
/*  83 */       int bottomOffset = (this.height - 1) * this.texWidth * perPixel;
/*  84 */       for (int x = 0; x < this.texWidth; x++) {
/*  85 */         for (int i = 0; i < perPixel; i++) {
/*  86 */           this.scratch.put(topOffset + x + i, this.scratch.get(x + i));
/*  87 */           this.scratch.put(bottomOffset + this.texWidth * perPixel + x + i, this.scratch.get(bottomOffset + x + i));
/*     */         } 
/*     */       } 
/*     */     } 
/*  91 */     if (this.width < this.texWidth - 1) {
/*  92 */       for (int y = 0; y < this.texHeight; y++) {
/*  93 */         for (int i = 0; i < perPixel; i++) {
/*  94 */           this.scratch.put((y + 1) * this.texWidth * perPixel - perPixel + i, this.scratch.get(y * this.texWidth * perPixel + i));
/*  95 */           this.scratch.put(y * this.texWidth * perPixel + this.width * perPixel + i, this.scratch.get(y * this.texWidth * perPixel + (this.width - 1) * perPixel + i));
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 100 */     if (!decoder.hasAlpha() && forceAlpha) {
/* 101 */       ByteBuffer temp = BufferUtils.createByteBuffer(this.texWidth * this.texHeight * 4);
/* 102 */       for (int x = 0; x < this.texWidth; x++) {
/* 103 */         for (int y = 0; y < this.texHeight; y++) {
/* 104 */           int srcOffset = y * 3 + x * this.texHeight * 3;
/* 105 */           int dstOffset = (y << 2) + x * this.texHeight * 4;
/*     */           
/* 107 */           temp.put(dstOffset, this.scratch.get(srcOffset));
/* 108 */           temp.put(dstOffset + 1, this.scratch.get(srcOffset + 1));
/* 109 */           temp.put(dstOffset + 2, this.scratch.get(srcOffset + 2));
/* 110 */           if (x < this.height && y < this.width) {
/* 111 */             temp.put(dstOffset + 3, (byte)-1);
/*     */           } else {
/* 113 */             temp.put(dstOffset + 3, (byte)0);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 118 */       this.bitDepth = 32;
/* 119 */       this.scratch = temp;
/*     */     } 
/*     */     
/* 122 */     this.scratch.position(0);
/*     */     
/* 124 */     return this.scratch;
/*     */   }
/*     */ 
/*     */   
/*     */   private int get2Fold(int fold) {
/* 129 */     int ret = 2;
/* 130 */     while (ret < fold) {
/* 131 */       ret <<= 1;
/*     */     }
/* 133 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public void configureEdging(boolean edging) {}
/*     */   
/*     */   public int getWidth() {
/* 140 */     return this.width;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 144 */     return this.height;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\PNGImageData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */