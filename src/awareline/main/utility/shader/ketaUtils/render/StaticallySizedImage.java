/*     */ package awareline.main.utility.shader.ketaUtils.render;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.nio.ByteBuffer;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL30;
/*     */ import org.lwjgl.opengl.GL42;
/*     */ 
/*     */ 
/*     */ public final class StaticallySizedImage
/*     */ {
/*     */   private final int textureId;
/*     */   private final int width;
/*     */   private final int height;
/*     */   
/*     */   public int getWidth() {
/*  18 */     return this.width; } public int getHeight() { return this.height; }
/*     */ 
/*     */   
/*     */   public StaticallySizedImage(BufferedImage image, boolean useMipMap, int numMinMaps) {
/*  22 */     this.textureId = GL11.glGenTextures();
/*  23 */     this.width = image.getWidth();
/*  24 */     this.height = image.getHeight();
/*     */     
/*  26 */     int[] pixels = new int[this.width * this.height];
/*     */     
/*  28 */     image.getRGB(0, 0, this.width, this.height, pixels, 0, this.width);
/*     */ 
/*     */     
/*  31 */     ByteBuffer buffer = BufferUtils.createByteBuffer(this.width * this.height * 4);
/*     */     
/*  33 */     for (int y = 0; y < this.height; y++) {
/*  34 */       for (int x = 0; x < this.width; x++) {
/*  35 */         int pixel = pixels[y * this.width + x];
/*     */         
/*  37 */         buffer.put((byte)-1);
/*  38 */         buffer.put((byte)-1);
/*  39 */         buffer.put((byte)-1);
/*  40 */         buffer.put((byte)(pixel >> 24 & 0xFF));
/*     */       } 
/*     */     } 
/*     */     
/*  44 */     buffer.flip();
/*     */     
/*  46 */     GL11.glBindTexture(3553, this.textureId);
/*     */     
/*  48 */     GL42.glTexStorage2D(3553, useMipMap ? numMinMaps : 1, 32856, this.width, this.height);
/*     */     
/*  50 */     GL11.glTexSubImage2D(3553, 0, 0, 0, this.width, this.height, 32993, 5121, buffer);
/*     */ 
/*     */ 
/*     */     
/*  54 */     if (useMipMap) {
/*  55 */       GL30.glGenerateMipmap(3553);
/*     */       
/*  57 */       GL11.glTexParameteri(3553, 10241, 9987);
/*     */     } else {
/*  59 */       GL11.glTexParameteri(3553, 10240, 9729);
/*     */     } 
/*     */     
/*  62 */     GL11.glTexParameterf(3553, 34049, 0.0F);
/*     */     
/*  64 */     GL11.glTexParameteri(3553, 10240, 9729);
/*     */     
/*  66 */     GL11.glTexImage2D(3553, 0, 6408, this.width, this.height, 0, 6408, 5121, buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind() {
/*  72 */     GL11.glBindTexture(3553, this.textureId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(double x, double y) {
/*  77 */     draw(x, y, this.width, this.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(double x, double y, double width, double height) {
/*  85 */     bind();
/*     */     
/*  87 */     GL11.glEnable(3042);
/*     */     
/*  89 */     GL11.glBegin(7);
/*     */ 
/*     */     
/*  92 */     GL11.glTexCoord2d(0.0D, 0.0D);
/*     */     
/*  94 */     GL11.glVertex2d(x, y);
/*     */     
/*  96 */     GL11.glTexCoord2d(0.0D, 1.0D);
/*  97 */     GL11.glVertex2d(x, y + height);
/*     */     
/*  99 */     GL11.glTexCoord2d(1.0D, 1.0D);
/* 100 */     GL11.glVertex2d(x + width, y + height);
/*     */     
/* 102 */     GL11.glTexCoord2d(1.0D, 0.0D);
/* 103 */     GL11.glVertex2d(x + width, y);
/*     */ 
/*     */     
/* 106 */     GL11.glEnd();
/*     */     
/* 108 */     GL11.glDisable(3042);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(double x, double y, double width, double height, int colour) {
/* 117 */     bind();
/*     */     
/* 119 */     DrawUtil.glColour(colour);
/*     */     
/* 121 */     GL11.glEnable(3042);
/*     */     
/* 123 */     GL11.glBegin(7);
/*     */ 
/*     */     
/* 126 */     GL11.glTexCoord2d(0.0D, 0.0D);
/*     */     
/* 128 */     GL11.glVertex2d(x, y);
/*     */     
/* 130 */     GL11.glTexCoord2d(0.0D, 1.0D);
/* 131 */     GL11.glVertex2d(x, y + height);
/*     */     
/* 133 */     GL11.glTexCoord2d(1.0D, 1.0D);
/* 134 */     GL11.glVertex2d(x + width, y + height);
/*     */     
/* 136 */     GL11.glTexCoord2d(1.0D, 0.0D);
/* 137 */     GL11.glVertex2d(x + width, y);
/*     */ 
/*     */     
/* 140 */     GL11.glEnd();
/*     */     
/* 142 */     GL11.glDisable(3042);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\shader\ketaUtils\render\StaticallySizedImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */