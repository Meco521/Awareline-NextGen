/*     */ package com.me.theresa.fontRenderer.font.opengl;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ public class ImageIOImageData implements LoadableImageData {
/*  15 */   private static final ColorModel glAlphaColorModel = new ComponentColorModel(
/*  16 */       ColorSpace.getInstance(1000), new int[] { 8, 8, 8, 8 }, true, false, 3, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  24 */   private static final ColorModel glColorModel = new ComponentColorModel(
/*  25 */       ColorSpace.getInstance(1000), new int[] { 8, 8, 8, 0 }, false, false, 1, 0);
/*     */ 
/*     */   
/*     */   private int depth;
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
/*     */   private boolean edging = true;
/*     */ 
/*     */   
/*     */   public int getDepth() {
/*  47 */     return this.depth;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  52 */     return this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTexHeight() {
/*  57 */     return this.texHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTexWidth() {
/*  62 */     return this.texWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  67 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis) throws IOException {
/*  72 */     return loadImage(fis, true, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis, boolean flipped, int[] transparent) throws IOException {
/*  77 */     return loadImage(fis, flipped, false, transparent);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis, boolean flipped, boolean forceAlpha, int[] transparent) throws IOException {
/*  82 */     if (transparent != null) {
/*  83 */       forceAlpha = true;
/*     */     }
/*     */     
/*  86 */     BufferedImage bufferedImage = ImageIO.read(fis);
/*  87 */     return imageToByteBuffer(bufferedImage, flipped, forceAlpha, transparent);
/*     */   }
/*     */   public ByteBuffer imageToByteBuffer(BufferedImage image, boolean flipped, boolean forceAlpha, int[] transparent) {
/*     */     BufferedImage texImage;
/*  91 */     ByteBuffer imageBuffer = null;
/*     */ 
/*     */ 
/*     */     
/*  95 */     int texWidth = 2;
/*  96 */     int texHeight = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     while (texWidth < image.getWidth()) {
/* 102 */       texWidth <<= 1;
/*     */     }
/* 104 */     while (texHeight < image.getHeight()) {
/* 105 */       texHeight <<= 1;
/*     */     }
/*     */     
/* 108 */     this.width = image.getWidth();
/* 109 */     this.height = image.getHeight();
/* 110 */     this.texHeight = texHeight;
/* 111 */     this.texWidth = texWidth;
/*     */ 
/*     */ 
/*     */     
/* 115 */     boolean useAlpha = (image.getColorModel().hasAlpha() || forceAlpha);
/*     */     
/* 117 */     if (useAlpha) {
/* 118 */       this.depth = 32;
/* 119 */       WritableRaster raster = Raster.createInterleavedRaster(0, texWidth, texHeight, 4, null);
/* 120 */       texImage = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable<>());
/*     */     } else {
/* 122 */       this.depth = 24;
/* 123 */       WritableRaster raster = Raster.createInterleavedRaster(0, texWidth, texHeight, 3, null);
/* 124 */       texImage = new BufferedImage(glColorModel, raster, false, new Hashtable<>());
/*     */     } 
/*     */ 
/*     */     
/* 128 */     Graphics2D g = (Graphics2D)texImage.getGraphics();
/*     */ 
/*     */     
/* 131 */     if (useAlpha) {
/* 132 */       g.setColor(new Color(0.0F, 0.0F, 0.0F, 0.0F));
/* 133 */       g.fillRect(0, 0, texWidth, texHeight);
/*     */     } 
/*     */     
/* 136 */     if (flipped) {
/* 137 */       g.scale(1.0D, -1.0D);
/* 138 */       g.drawImage(image, 0, -this.height, (ImageObserver)null);
/*     */     } else {
/* 140 */       g.drawImage(image, 0, 0, (ImageObserver)null);
/*     */     } 
/*     */     
/* 143 */     if (this.edging) {
/* 144 */       if (this.height < texHeight - 1) {
/* 145 */         copyArea(texImage, 0, 0, this.width, 1, 0, texHeight - 1);
/* 146 */         copyArea(texImage, 0, this.height - 1, this.width, 1, 0, 1);
/*     */       } 
/* 148 */       if (this.width < texWidth - 1) {
/* 149 */         copyArea(texImage, 0, 0, 1, this.height, texWidth - 1, 0);
/* 150 */         copyArea(texImage, this.width - 1, 0, 1, this.height, 1, 0);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 156 */     byte[] data = ((DataBufferByte)texImage.getRaster().getDataBuffer()).getData();
/*     */     
/* 158 */     if (transparent != null) {
/* 159 */       for (int i = 0; i < data.length; i += 4) {
/* 160 */         boolean match = true;
/* 161 */         for (int c = 0; c < 3; c++) {
/* 162 */           int value = (data[i + c] < 0) ? (256 + data[i + c]) : data[i + c];
/* 163 */           if (value != transparent[c]) {
/* 164 */             match = false;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 169 */         if (match) {
/* 170 */           data[i + 3] = 0;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 175 */     imageBuffer = ByteBuffer.allocateDirect(data.length);
/* 176 */     imageBuffer.order(ByteOrder.nativeOrder());
/* 177 */     imageBuffer.put(data, 0, data.length);
/* 178 */     imageBuffer.flip();
/* 179 */     g.dispose();
/*     */     
/* 181 */     return imageBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer getImageBufferData() {
/* 186 */     throw new RuntimeException("ImageIOImageData doesn't store it's image.");
/*     */   }
/*     */ 
/*     */   
/*     */   private void copyArea(BufferedImage image, int x, int y, int width, int height, int dx, int dy) {
/* 191 */     Graphics2D g = (Graphics2D)image.getGraphics();
/*     */     
/* 193 */     g.drawImage(image.getSubimage(x, y, width, height), x + dx, y + dy, (ImageObserver)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void configureEdging(boolean edging) {
/* 198 */     this.edging = edging;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\ImageIOImageData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */