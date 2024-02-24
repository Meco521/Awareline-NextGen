/*     */ package com.me.theresa.fontRenderer.font.opengl;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TGAImageData
/*     */   implements LoadableImageData
/*     */ {
/*     */   private int texWidth;
/*     */   private int texHeight;
/*     */   private int width;
/*     */   private int height;
/*     */   private short pixelDepth;
/*     */   
/*     */   private short flipEndian(short signedShort) {
/*  30 */     int input = signedShort & 0xFFFF;
/*  31 */     return (short)(input << 8 | (input & 0xFF00) >>> 8);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDepth() {
/*  36 */     return this.pixelDepth;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  41 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  46 */     return this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTexWidth() {
/*  51 */     return this.texWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTexHeight() {
/*  56 */     return this.texHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis) throws IOException {
/*  61 */     return loadImage(fis, true, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis, boolean flipped, int[] transparent) throws IOException {
/*  66 */     return loadImage(fis, flipped, false, transparent);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis, boolean flipped, boolean forceAlpha, int[] transparent) throws IOException {
/*  71 */     if (transparent != null) {
/*  72 */       forceAlpha = true;
/*     */     }
/*  74 */     byte red = 0;
/*  75 */     byte green = 0;
/*  76 */     byte blue = 0;
/*  77 */     byte alpha = 0;
/*     */     
/*  79 */     BufferedInputStream bis = new BufferedInputStream(fis, 100000);
/*  80 */     DataInputStream dis = new DataInputStream(bis);
/*     */ 
/*     */     
/*  83 */     short idLength = (short)dis.read();
/*  84 */     short colorMapType = (short)dis.read();
/*  85 */     short imageType = (short)dis.read();
/*  86 */     short cMapStart = flipEndian(dis.readShort());
/*  87 */     short cMapLength = flipEndian(dis.readShort());
/*  88 */     short cMapDepth = (short)dis.read();
/*  89 */     short xOffset = flipEndian(dis.readShort());
/*  90 */     short yOffset = flipEndian(dis.readShort());
/*     */     
/*  92 */     if (imageType != 2) {
/*  93 */       throw new IOException("Slick only supports uncompressed RGB(A) TGA images");
/*     */     }
/*     */     
/*  96 */     this.width = flipEndian(dis.readShort());
/*  97 */     this.height = flipEndian(dis.readShort());
/*  98 */     this.pixelDepth = (short)dis.read();
/*  99 */     if (this.pixelDepth == 32) {
/* 100 */       forceAlpha = false;
/*     */     }
/*     */     
/* 103 */     this.texWidth = get2Fold(this.width);
/* 104 */     this.texHeight = get2Fold(this.height);
/*     */     
/* 106 */     short imageDescriptor = (short)dis.read();
/* 107 */     if ((imageDescriptor & 0x20) == 0) {
/* 108 */       flipped = !flipped;
/*     */     }
/*     */ 
/*     */     
/* 112 */     if (idLength > 0) {
/* 113 */       bis.skip(idLength);
/*     */     }
/*     */     
/* 116 */     byte[] rawData = null;
/* 117 */     if (this.pixelDepth == 32 || forceAlpha) {
/* 118 */       this.pixelDepth = 32;
/* 119 */       rawData = new byte[this.texWidth * this.texHeight * 4];
/* 120 */     } else if (this.pixelDepth == 24) {
/* 121 */       rawData = new byte[this.texWidth * this.texHeight * 3];
/*     */     } else {
/* 123 */       throw new RuntimeException("Only 24 and 32 bit TGAs are supported");
/*     */     } 
/*     */     
/* 126 */     if (this.pixelDepth == 24) {
/* 127 */       if (flipped) {
/* 128 */         for (int i = this.height - 1; i >= 0; i--) {
/* 129 */           for (int j = 0; j < this.width; j++) {
/* 130 */             blue = dis.readByte();
/* 131 */             green = dis.readByte();
/* 132 */             red = dis.readByte();
/*     */             
/* 134 */             int ofs = (j + i * this.texWidth) * 3;
/* 135 */             rawData[ofs] = red;
/* 136 */             rawData[ofs + 1] = green;
/* 137 */             rawData[ofs + 2] = blue;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 141 */         for (int i = 0; i < this.height; i++) {
/* 142 */           for (int j = 0; j < this.width; j++) {
/* 143 */             blue = dis.readByte();
/* 144 */             green = dis.readByte();
/* 145 */             red = dis.readByte();
/*     */             
/* 147 */             int ofs = (j + i * this.texWidth) * 3;
/* 148 */             rawData[ofs] = red;
/* 149 */             rawData[ofs + 1] = green;
/* 150 */             rawData[ofs + 2] = blue;
/*     */           } 
/*     */         } 
/*     */       } 
/* 154 */     } else if (this.pixelDepth == 32) {
/* 155 */       if (flipped) {
/* 156 */         for (int i = this.height - 1; i >= 0; i--) {
/* 157 */           for (int j = 0; j < this.width; j++) {
/* 158 */             blue = dis.readByte();
/* 159 */             green = dis.readByte();
/* 160 */             red = dis.readByte();
/* 161 */             if (forceAlpha) {
/* 162 */               alpha = -1;
/*     */             } else {
/* 164 */               alpha = dis.readByte();
/*     */             } 
/*     */             
/* 167 */             int ofs = j + i * this.texWidth << 2;
/*     */             
/* 169 */             rawData[ofs] = red;
/* 170 */             rawData[ofs + 1] = green;
/* 171 */             rawData[ofs + 2] = blue;
/* 172 */             rawData[ofs + 3] = alpha;
/*     */             
/* 174 */             if (alpha == 0) {
/* 175 */               rawData[ofs + 2] = 0;
/* 176 */               rawData[ofs + 1] = 0;
/* 177 */               rawData[ofs] = 0;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } else {
/* 182 */         for (int i = 0; i < this.height; i++) {
/* 183 */           for (int j = 0; j < this.width; j++) {
/* 184 */             blue = dis.readByte();
/* 185 */             green = dis.readByte();
/* 186 */             red = dis.readByte();
/* 187 */             if (forceAlpha) {
/* 188 */               alpha = -1;
/*     */             } else {
/* 190 */               alpha = dis.readByte();
/*     */             } 
/*     */             
/* 193 */             int ofs = j + i * this.texWidth << 2;
/*     */             
/* 195 */             if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
/* 196 */               rawData[ofs] = red;
/* 197 */               rawData[ofs + 1] = green;
/* 198 */               rawData[ofs + 2] = blue;
/* 199 */               rawData[ofs + 3] = alpha;
/*     */             } else {
/* 201 */               rawData[ofs] = red;
/* 202 */               rawData[ofs + 1] = green;
/* 203 */               rawData[ofs + 2] = blue;
/* 204 */               rawData[ofs + 3] = alpha;
/*     */             } 
/*     */             
/* 207 */             if (alpha == 0) {
/* 208 */               rawData[ofs + 2] = 0;
/* 209 */               rawData[ofs + 1] = 0;
/* 210 */               rawData[ofs] = 0;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 216 */     fis.close();
/*     */     
/* 218 */     if (transparent != null) {
/* 219 */       for (int i = 0; i < rawData.length; i += 4) {
/* 220 */         boolean match = true;
/* 221 */         for (int c = 0; c < 3; c++) {
/* 222 */           if (rawData[i + c] != transparent[c]) {
/* 223 */             match = false;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 228 */         if (match) {
/* 229 */           rawData[i + 3] = 0;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 235 */     ByteBuffer scratch = BufferUtils.createByteBuffer(rawData.length);
/* 236 */     scratch.put(rawData);
/*     */     
/* 238 */     int perPixel = this.pixelDepth / 8;
/* 239 */     if (this.height < this.texHeight - 1) {
/* 240 */       int topOffset = (this.texHeight - 1) * this.texWidth * perPixel;
/* 241 */       int bottomOffset = (this.height - 1) * this.texWidth * perPixel;
/* 242 */       for (int x = 0; x < this.texWidth * perPixel; x++) {
/* 243 */         scratch.put(topOffset + x, scratch.get(x));
/* 244 */         scratch.put(bottomOffset + this.texWidth * perPixel + x, scratch.get(this.texWidth * perPixel + x));
/*     */       } 
/*     */     } 
/* 247 */     if (this.width < this.texWidth - 1) {
/* 248 */       for (int y = 0; y < this.texHeight; y++) {
/* 249 */         for (int i = 0; i < perPixel; i++) {
/* 250 */           scratch.put((y + 1) * this.texWidth * perPixel - perPixel + i, scratch.get(y * this.texWidth * perPixel + i));
/* 251 */           scratch.put(y * this.texWidth * perPixel + this.width * perPixel + i, scratch.get(y * this.texWidth * perPixel + (this.width - 1) * perPixel + i));
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 256 */     scratch.flip();
/*     */     
/* 258 */     return scratch;
/*     */   }
/*     */ 
/*     */   
/*     */   private int get2Fold(int fold) {
/* 263 */     int ret = 2;
/* 264 */     while (ret < fold) {
/* 265 */       ret <<= 1;
/*     */     }
/* 267 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer getImageBufferData() {
/* 272 */     throw new RuntimeException("TGAImageData doesn't store it's image.");
/*     */   }
/*     */   
/*     */   public void configureEdging(boolean edging) {}
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\TGAImageData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */