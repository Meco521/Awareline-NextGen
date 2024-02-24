/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.ImageObserver;
/*     */ 
/*     */ public class ImageBufferDownload
/*     */   implements IImageBuffer
/*     */ {
/*     */   private int[] imageData;
/*     */   private int imageWidth;
/*     */   private int imageHeight;
/*     */   
/*     */   public BufferedImage parseUserSkin(BufferedImage image) {
/*  16 */     if (image == null)
/*     */     {
/*  18 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  22 */     this.imageWidth = 64;
/*  23 */     this.imageHeight = 64;
/*  24 */     int i = image.getWidth();
/*  25 */     int j = image.getHeight();
/*     */     
/*     */     int k;
/*  28 */     for (k = 1; this.imageWidth < i || this.imageHeight < j; k <<= 1) {
/*     */       
/*  30 */       this.imageWidth <<= 1;
/*  31 */       this.imageHeight <<= 1;
/*     */     } 
/*     */     
/*  34 */     BufferedImage bufferedimage = new BufferedImage(this.imageWidth, this.imageHeight, 2);
/*  35 */     Graphics graphics = bufferedimage.getGraphics();
/*  36 */     graphics.drawImage(image, 0, 0, (ImageObserver)null);
/*     */     
/*  38 */     if (image.getHeight() == 32 * k) {
/*     */       
/*  40 */       graphics.drawImage(bufferedimage, 24 * k, 48 * k, 20 * k, 52 * k, 4 * k, 16 * k, 8 * k, 20 * k, (ImageObserver)null);
/*  41 */       graphics.drawImage(bufferedimage, 28 * k, 48 * k, 24 * k, 52 * k, 8 * k, 16 * k, 12 * k, 20 * k, (ImageObserver)null);
/*  42 */       graphics.drawImage(bufferedimage, 20 * k, 52 * k, 16 * k, 64 * k, 8 * k, 20 * k, 12 * k, 32 * k, (ImageObserver)null);
/*  43 */       graphics.drawImage(bufferedimage, 24 * k, 52 * k, 20 * k, 64 * k, 4 * k, 20 * k, 8 * k, 32 * k, (ImageObserver)null);
/*  44 */       graphics.drawImage(bufferedimage, 28 * k, 52 * k, 24 * k, 64 * k, 0, 20 * k, 4 * k, 32 * k, (ImageObserver)null);
/*  45 */       graphics.drawImage(bufferedimage, 32 * k, 52 * k, 28 * k, 64 * k, 12 * k, 20 * k, 16 * k, 32 * k, (ImageObserver)null);
/*  46 */       graphics.drawImage(bufferedimage, 40 * k, 48 * k, 36 * k, 52 * k, 44 * k, 16 * k, 48 * k, 20 * k, (ImageObserver)null);
/*  47 */       graphics.drawImage(bufferedimage, 44 * k, 48 * k, 40 * k, 52 * k, 48 * k, 16 * k, 52 * k, 20 * k, (ImageObserver)null);
/*  48 */       graphics.drawImage(bufferedimage, 36 * k, 52 * k, 32 * k, 64 * k, 48 * k, 20 * k, 52 * k, 32 * k, (ImageObserver)null);
/*  49 */       graphics.drawImage(bufferedimage, 40 * k, 52 * k, 36 * k, 64 * k, 44 * k, 20 * k, 48 * k, 32 * k, (ImageObserver)null);
/*  50 */       graphics.drawImage(bufferedimage, 44 * k, 52 * k, 40 * k, 64 * k, 40 * k, 20 * k, 44 * k, 32 * k, (ImageObserver)null);
/*  51 */       graphics.drawImage(bufferedimage, 48 * k, 52 * k, 44 * k, 64 * k, 52 * k, 20 * k, 56 * k, 32 * k, (ImageObserver)null);
/*     */     } 
/*     */     
/*  54 */     graphics.dispose();
/*  55 */     this.imageData = ((DataBufferInt)bufferedimage.getRaster().getDataBuffer()).getData();
/*  56 */     setAreaOpaque(0, 0, 32 * k, 16 * k);
/*  57 */     setAreaTransparent(32 * k, 0, 64 * k, 32 * k);
/*  58 */     setAreaOpaque(0, 16 * k, 64 * k, 32 * k);
/*  59 */     setAreaTransparent(0, 32 * k, 16 * k, 48 * k);
/*  60 */     setAreaTransparent(16 * k, 32 * k, 40 * k, 48 * k);
/*  61 */     setAreaTransparent(40 * k, 32 * k, 56 * k, 48 * k);
/*  62 */     setAreaTransparent(0, 48 * k, 16 * k, 64 * k);
/*  63 */     setAreaOpaque(16 * k, 48 * k, 48 * k, 64 * k);
/*  64 */     setAreaTransparent(48 * k, 48 * k, 64 * k, 64 * k);
/*  65 */     return bufferedimage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void skinAvailable() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setAreaTransparent(int p_78434_1_, int p_78434_2_, int p_78434_3_, int p_78434_4_) {
/*  80 */     if (!hasTransparency(p_78434_1_, p_78434_2_, p_78434_3_, p_78434_4_))
/*     */     {
/*  82 */       for (int i = p_78434_1_; i < p_78434_3_; i++) {
/*     */         
/*  84 */         for (int j = p_78434_2_; j < p_78434_4_; j++)
/*     */         {
/*  86 */           this.imageData[i + j * this.imageWidth] = this.imageData[i + j * this.imageWidth] & 0xFFFFFF;
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setAreaOpaque(int p_78433_1_, int p_78433_2_, int p_78433_3_, int p_78433_4_) {
/*  97 */     for (int i = p_78433_1_; i < p_78433_3_; i++) {
/*     */       
/*  99 */       for (int j = p_78433_2_; j < p_78433_4_; j++)
/*     */       {
/* 101 */         this.imageData[i + j * this.imageWidth] = this.imageData[i + j * this.imageWidth] | 0xFF000000;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasTransparency(int p_78435_1_, int p_78435_2_, int p_78435_3_, int p_78435_4_) {
/* 111 */     for (int i = p_78435_1_; i < p_78435_3_; i++) {
/*     */       
/* 113 */       for (int j = p_78435_2_; j < p_78435_4_; j++) {
/*     */         
/* 115 */         int k = this.imageData[i + j * this.imageWidth];
/*     */         
/* 117 */         if ((k >> 24 & 0xFF) < 128)
/*     */         {
/* 119 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 124 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\ImageBufferDownload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */