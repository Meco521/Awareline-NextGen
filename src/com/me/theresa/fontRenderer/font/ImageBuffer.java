/*     */ package com.me.theresa.fontRenderer.font;
/*     */ 
/*     */ import com.me.theresa.fontRenderer.font.impl.ImageData;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import org.lwjgl.BufferUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImageBuffer
/*     */   implements ImageData
/*     */ {
/*     */   private final int width;
/*     */   private final int height;
/*     */   private final int texWidth;
/*     */   private final int texHeight;
/*     */   private final byte[] rawData;
/*     */   
/*     */   public ImageBuffer(int width, int height) {
/*  24 */     this.width = width;
/*  25 */     this.height = height;
/*     */     
/*  27 */     this.texWidth = get2Fold(width);
/*  28 */     this.texHeight = get2Fold(height);
/*     */     
/*  30 */     this.rawData = new byte[this.texWidth * this.texHeight * 4];
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getRGBA() {
/*  35 */     return this.rawData;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDepth() {
/*  40 */     return 32;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  45 */     return this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTexHeight() {
/*  50 */     return this.texHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTexWidth() {
/*  55 */     return this.texWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  60 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer getImageBufferData() {
/*  65 */     ByteBuffer scratch = BufferUtils.createByteBuffer(this.rawData.length);
/*  66 */     scratch.put(this.rawData);
/*  67 */     scratch.flip();
/*     */     
/*  69 */     return scratch;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRGBA(int x, int y, int r, int g, int b, int a) {
/*  74 */     if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
/*  75 */       throw new RuntimeException("Specified location: " + x + "," + y + " outside of image");
/*     */     }
/*     */     
/*  78 */     int ofs = x + y * this.texWidth << 2;
/*     */     
/*  80 */     if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
/*  81 */       this.rawData[ofs] = (byte)b;
/*  82 */       this.rawData[ofs + 1] = (byte)g;
/*  83 */       this.rawData[ofs + 2] = (byte)r;
/*  84 */       this.rawData[ofs + 3] = (byte)a;
/*     */     } else {
/*  86 */       this.rawData[ofs] = (byte)r;
/*  87 */       this.rawData[ofs + 1] = (byte)g;
/*  88 */       this.rawData[ofs + 2] = (byte)b;
/*  89 */       this.rawData[ofs + 3] = (byte)a;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Image getImage() {
/*  95 */     return new Image(this);
/*     */   }
/*     */   
/*     */   public Image getImage(int filter) {
/*  99 */     return new Image(this, filter);
/*     */   }
/*     */   
/*     */   private int get2Fold(int fold) {
/* 103 */     int ret = 2;
/* 104 */     while (ret < fold) {
/* 105 */       ret <<= 1;
/*     */     }
/* 107 */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\ImageBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */