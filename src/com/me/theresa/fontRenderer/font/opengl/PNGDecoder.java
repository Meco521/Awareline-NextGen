/*     */ package com.me.theresa.fontRenderer.font.opengl;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.zip.CRC32;
/*     */ import java.util.zip.DataFormatException;
/*     */ import java.util.zip.Inflater;
/*     */ 
/*     */ public class PNGDecoder
/*     */ {
/*  14 */   public static Format ALPHA = new Format(1, true);
/*  15 */   public static Format LUMINANCE = new Format(1, false);
/*  16 */   public static Format LUMINANCE_ALPHA = new Format(2, true);
/*  17 */   public static Format RGB = new Format(3, false);
/*  18 */   public static Format RGBA = new Format(4, true);
/*  19 */   public static Format BGRA = new Format(4, true);
/*  20 */   public static Format ABGR = new Format(4, true);
/*     */   
/*     */   public static class Format
/*     */   {
/*     */     final int numComponents;
/*     */     final boolean hasAlpha;
/*     */     
/*     */     Format(int numComponents, boolean hasAlpha) {
/*  28 */       this.numComponents = numComponents;
/*  29 */       this.hasAlpha = hasAlpha;
/*     */     }
/*     */     
/*     */     public int getNumComponents() {
/*  33 */       return this.numComponents;
/*     */     }
/*     */     
/*     */     public boolean isHasAlpha() {
/*  37 */       return this.hasAlpha;
/*     */     }
/*     */   }
/*     */   
/*  41 */   private static final byte[] SIGNATURE = new byte[] { -119, 80, 78, 71, 13, 10, 26, 10 };
/*     */   
/*     */   private static final int IHDR = 1229472850;
/*     */   
/*     */   private static final int PLTE = 1347179589;
/*     */   
/*     */   private static final int tRNS = 1951551059;
/*     */   
/*     */   private static final int IDAT = 1229209940;
/*     */   
/*     */   private static final int IEND = 1229278788;
/*     */   private static final byte COLOR_GREYSCALE = 0;
/*     */   private static final byte COLOR_TRUECOLOR = 2;
/*     */   private static final byte COLOR_INDEXED = 3;
/*     */   private static final byte COLOR_GREYALPHA = 4;
/*     */   private static final byte COLOR_TRUEALPHA = 6;
/*     */   private final InputStream input;
/*     */   private final CRC32 crc;
/*     */   private final byte[] buffer;
/*     */   private int chunkLength;
/*     */   private int chunkType;
/*     */   private int chunkRemaining;
/*     */   private int width;
/*     */   private int height;
/*     */   private int bitdepth;
/*     */   private int colorType;
/*     */   private int bytesPerPixel;
/*     */   private byte[] palette;
/*     */   private byte[] paletteA;
/*     */   private byte[] transPixel;
/*     */   
/*     */   public PNGDecoder(InputStream input) throws IOException {
/*  73 */     this.input = input;
/*  74 */     this.crc = new CRC32();
/*  75 */     this.buffer = new byte[4096];
/*     */     
/*  77 */     readFully(this.buffer, 0, SIGNATURE.length);
/*  78 */     if (!checkSignature(this.buffer)) {
/*  79 */       throw new IOException("Not a valid PNG file");
/*     */     }
/*     */     
/*  82 */     openChunk(1229472850);
/*  83 */     readIHDR();
/*  84 */     closeChunk();
/*     */ 
/*     */     
/*     */     while (true) {
/*  88 */       openChunk();
/*  89 */       switch (this.chunkType) {
/*     */         case 1229209940:
/*     */           break;
/*     */         case 1347179589:
/*  93 */           readPLTE();
/*     */           break;
/*     */         case 1951551059:
/*  96 */           readtRNS();
/*     */           break;
/*     */       } 
/*  99 */       closeChunk();
/*     */     } 
/*     */     
/* 102 */     if (this.colorType == 3 && this.palette == null) {
/* 103 */       throw new IOException("Missing PLTE chunk");
/*     */     }
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 108 */     return this.height;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 112 */     return this.width;
/*     */   }
/*     */   
/*     */   public boolean hasAlpha() {
/* 116 */     return (this.colorType == 6 || this.paletteA != null || this.transPixel != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRGB() {
/* 121 */     return (this.colorType == 6 || this.colorType == 2 || this.colorType == 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Format decideTextureFormat(Format fmt) {
/* 128 */     switch (this.colorType) {
/*     */       case 2:
/* 130 */         if (fmt == ABGR || fmt == RGBA || fmt == BGRA || fmt == RGB) {
/* 131 */           return fmt;
/*     */         }
/*     */         
/* 134 */         return RGB;
/*     */       case 6:
/* 136 */         if (fmt == ABGR || fmt == RGBA || fmt == BGRA || fmt == RGB) {
/* 137 */           return fmt;
/*     */         }
/*     */         
/* 140 */         return RGBA;
/*     */       case 0:
/* 142 */         if (fmt == LUMINANCE || fmt == ALPHA) {
/* 143 */           return fmt;
/*     */         }
/*     */         
/* 146 */         return LUMINANCE;
/*     */       case 4:
/* 148 */         return LUMINANCE_ALPHA;
/*     */       case 3:
/* 150 */         if (fmt == ABGR || fmt == RGBA || fmt == BGRA) {
/* 151 */           return fmt;
/*     */         }
/*     */         
/* 154 */         return RGBA;
/*     */     } 
/* 156 */     throw new UnsupportedOperationException("Not yet implemented");
/*     */   }
/*     */ 
/*     */   
/*     */   public void decode(ByteBuffer buffer, int stride, Format fmt) throws IOException {
/* 161 */     int offset = buffer.position();
/* 162 */     int lineSize = (this.width * this.bitdepth + 7) / 8 * this.bytesPerPixel;
/* 163 */     byte[] curLine = new byte[lineSize + 1];
/* 164 */     byte[] prevLine = new byte[lineSize + 1];
/* 165 */     byte[] palLine = (this.bitdepth < 8) ? new byte[this.width + 1] : null;
/*     */     
/* 167 */     Inflater inflater = new Inflater();
/*     */     try {
/* 169 */       for (int y = 0; y < this.height; y++) {
/* 170 */         readChunkUnzip(inflater, curLine, 0, curLine.length);
/* 171 */         unfilter(curLine, prevLine);
/*     */         
/* 173 */         buffer.position(offset + y * stride);
/*     */         
/* 175 */         switch (this.colorType) {
/*     */           case 2:
/* 177 */             if (fmt == ABGR) {
/* 178 */               copyRGBtoABGR(buffer, curLine); break;
/* 179 */             }  if (fmt == RGBA) {
/* 180 */               copyRGBtoRGBA(buffer, curLine); break;
/* 181 */             }  if (fmt == BGRA) {
/* 182 */               copyRGBtoBGRA(buffer, curLine); break;
/* 183 */             }  if (fmt == RGB) {
/* 184 */               copy(buffer, curLine); break;
/*     */             } 
/* 186 */             throw new UnsupportedOperationException("Unsupported format for this image");
/*     */ 
/*     */           
/*     */           case 6:
/* 190 */             if (fmt == ABGR) {
/* 191 */               copyRGBAtoABGR(buffer, curLine); break;
/* 192 */             }  if (fmt == RGBA) {
/* 193 */               copy(buffer, curLine); break;
/* 194 */             }  if (fmt == BGRA) {
/* 195 */               copyRGBAtoBGRA(buffer, curLine); break;
/*     */             } 
/* 197 */             if (fmt == RGB) {
/* 198 */               copyRGBAtoRGB(buffer, curLine);
/*     */               break;
/*     */             } 
/* 201 */             throw new UnsupportedOperationException("Unsupported format for this image");
/*     */ 
/*     */           
/*     */           case 0:
/* 205 */             if (fmt == LUMINANCE || fmt == ALPHA) {
/* 206 */               copy(buffer, curLine); break;
/*     */             } 
/* 208 */             throw new UnsupportedOperationException("Unsupported format for this image");
/*     */ 
/*     */           
/*     */           case 4:
/* 212 */             if (fmt == LUMINANCE_ALPHA) {
/* 213 */               copy(buffer, curLine); break;
/*     */             } 
/* 215 */             throw new UnsupportedOperationException("Unsupported format for this image");
/*     */ 
/*     */           
/*     */           case 3:
/* 219 */             switch (this.bitdepth) {
/*     */               case 8:
/* 221 */                 palLine = curLine;
/*     */                 break;
/*     */               case 4:
/* 224 */                 expand4(curLine, palLine);
/*     */                 break;
/*     */               case 2:
/* 227 */                 expand2(curLine, palLine);
/*     */                 break;
/*     */               case 1:
/* 230 */                 expand1(curLine, palLine);
/*     */                 break;
/*     */               default:
/* 233 */                 throw new UnsupportedOperationException("Unsupported bitdepth for this image");
/*     */             } 
/* 235 */             if (fmt == ABGR) {
/* 236 */               copyPALtoABGR(buffer, palLine); break;
/* 237 */             }  if (fmt == RGBA) {
/* 238 */               copyPALtoRGBA(buffer, palLine); break;
/* 239 */             }  if (fmt == BGRA) {
/* 240 */               copyPALtoBGRA(buffer, palLine); break;
/*     */             } 
/* 242 */             throw new UnsupportedOperationException("Unsupported format for this image");
/*     */ 
/*     */           
/*     */           default:
/* 246 */             throw new UnsupportedOperationException("Not yet implemented");
/*     */         } 
/*     */         
/* 249 */         byte[] tmp = curLine;
/* 250 */         curLine = prevLine;
/* 251 */         prevLine = tmp;
/*     */       } 
/*     */     } finally {
/* 254 */       inflater.end();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void copy(ByteBuffer buffer, byte[] curLine) {
/* 259 */     buffer.put(curLine, 1, curLine.length - 1);
/*     */   }
/*     */   
/*     */   private void copyRGBtoABGR(ByteBuffer buffer, byte[] curLine) {
/* 263 */     if (this.transPixel != null) {
/* 264 */       byte tr = this.transPixel[1];
/* 265 */       byte tg = this.transPixel[3];
/* 266 */       byte tb = this.transPixel[5];
/* 267 */       for (int i = 1, n = curLine.length; i < n; i += 3) {
/* 268 */         byte r = curLine[i];
/* 269 */         byte g = curLine[i + 1];
/* 270 */         byte b = curLine[i + 2];
/* 271 */         byte a = -1;
/* 272 */         if (r == tr && g == tg && b == tb) {
/* 273 */           a = 0;
/*     */         }
/* 275 */         buffer.put(a).put(b).put(g).put(r);
/*     */       } 
/*     */     } else {
/* 278 */       for (int i = 1, n = curLine.length; i < n; i += 3) {
/* 279 */         buffer.put((byte)-1).put(curLine[i + 2]).put(curLine[i + 1]).put(curLine[i]);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void copyRGBtoRGBA(ByteBuffer buffer, byte[] curLine) {
/* 285 */     if (this.transPixel != null) {
/* 286 */       byte tr = this.transPixel[1];
/* 287 */       byte tg = this.transPixel[3];
/* 288 */       byte tb = this.transPixel[5];
/* 289 */       for (int i = 1, n = curLine.length; i < n; i += 3) {
/* 290 */         byte r = curLine[i];
/* 291 */         byte g = curLine[i + 1];
/* 292 */         byte b = curLine[i + 2];
/* 293 */         byte a = -1;
/* 294 */         if (r == tr && g == tg && b == tb) {
/* 295 */           a = 0;
/*     */         }
/* 297 */         buffer.put(r).put(g).put(b).put(a);
/*     */       } 
/*     */     } else {
/* 300 */       for (int i = 1, n = curLine.length; i < n; i += 3) {
/* 301 */         buffer.put(curLine[i]).put(curLine[i + 1]).put(curLine[i + 2]).put((byte)-1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void copyRGBtoBGRA(ByteBuffer buffer, byte[] curLine) {
/* 307 */     if (this.transPixel != null) {
/* 308 */       byte tr = this.transPixel[1];
/* 309 */       byte tg = this.transPixel[3];
/* 310 */       byte tb = this.transPixel[5];
/* 311 */       for (int i = 1, n = curLine.length; i < n; i += 3) {
/* 312 */         byte r = curLine[i];
/* 313 */         byte g = curLine[i + 1];
/* 314 */         byte b = curLine[i + 2];
/* 315 */         byte a = -1;
/* 316 */         if (r == tr && g == tg && b == tb) {
/* 317 */           a = 0;
/*     */         }
/* 319 */         buffer.put(b).put(g).put(r).put(a);
/*     */       } 
/*     */     } else {
/* 322 */       for (int i = 1, n = curLine.length; i < n; i += 3) {
/* 323 */         buffer.put(curLine[i + 2]).put(curLine[i + 1]).put(curLine[i]).put((byte)-1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void copyRGBAtoABGR(ByteBuffer buffer, byte[] curLine) {
/* 329 */     for (int i = 1, n = curLine.length; i < n; i += 4) {
/* 330 */       buffer.put(curLine[i + 3]).put(curLine[i + 2]).put(curLine[i + 1]).put(curLine[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void copyRGBAtoBGRA(ByteBuffer buffer, byte[] curLine) {
/* 335 */     for (int i = 1, n = curLine.length; i < n; i += 4) {
/* 336 */       buffer.put(curLine[i + 2]).put(curLine[i + 1]).put(curLine[i + 0]).put(curLine[i + 3]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void copyRGBAtoRGB(ByteBuffer buffer, byte[] curLine) {
/* 341 */     for (int i = 1, n = curLine.length; i < n; i += 4) {
/* 342 */       buffer.put(curLine[i]).put(curLine[i + 1]).put(curLine[i + 2]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void copyPALtoABGR(ByteBuffer buffer, byte[] curLine) {
/* 347 */     if (this.paletteA != null) {
/* 348 */       for (int i = 1, n = curLine.length; i < n; i++) {
/* 349 */         int idx = curLine[i] & 0xFF;
/* 350 */         byte r = this.palette[idx * 3 + 0];
/* 351 */         byte g = this.palette[idx * 3 + 1];
/* 352 */         byte b = this.palette[idx * 3 + 2];
/* 353 */         byte a = this.paletteA[idx];
/* 354 */         buffer.put(a).put(b).put(g).put(r);
/*     */       } 
/*     */     } else {
/* 357 */       for (int i = 1, n = curLine.length; i < n; i++) {
/* 358 */         int idx = curLine[i] & 0xFF;
/* 359 */         byte r = this.palette[idx * 3 + 0];
/* 360 */         byte g = this.palette[idx * 3 + 1];
/* 361 */         byte b = this.palette[idx * 3 + 2];
/* 362 */         byte a = -1;
/* 363 */         buffer.put(a).put(b).put(g).put(r);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void copyPALtoRGBA(ByteBuffer buffer, byte[] curLine) {
/* 369 */     if (this.paletteA != null) {
/* 370 */       for (int i = 1, n = curLine.length; i < n; i++) {
/* 371 */         int idx = curLine[i] & 0xFF;
/* 372 */         byte r = this.palette[idx * 3 + 0];
/* 373 */         byte g = this.palette[idx * 3 + 1];
/* 374 */         byte b = this.palette[idx * 3 + 2];
/* 375 */         byte a = this.paletteA[idx];
/* 376 */         buffer.put(r).put(g).put(b).put(a);
/*     */       } 
/*     */     } else {
/* 379 */       for (int i = 1, n = curLine.length; i < n; i++) {
/* 380 */         int idx = curLine[i] & 0xFF;
/* 381 */         byte r = this.palette[idx * 3 + 0];
/* 382 */         byte g = this.palette[idx * 3 + 1];
/* 383 */         byte b = this.palette[idx * 3 + 2];
/* 384 */         byte a = -1;
/* 385 */         buffer.put(r).put(g).put(b).put(a);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void copyPALtoBGRA(ByteBuffer buffer, byte[] curLine) {
/* 391 */     if (this.paletteA != null) {
/* 392 */       for (int i = 1, n = curLine.length; i < n; i++) {
/* 393 */         int idx = curLine[i] & 0xFF;
/* 394 */         byte r = this.palette[idx * 3 + 0];
/* 395 */         byte g = this.palette[idx * 3 + 1];
/* 396 */         byte b = this.palette[idx * 3 + 2];
/* 397 */         byte a = this.paletteA[idx];
/* 398 */         buffer.put(b).put(g).put(r).put(a);
/*     */       } 
/*     */     } else {
/* 401 */       for (int i = 1, n = curLine.length; i < n; i++) {
/* 402 */         int idx = curLine[i] & 0xFF;
/* 403 */         byte r = this.palette[idx * 3 + 0];
/* 404 */         byte g = this.palette[idx * 3 + 1];
/* 405 */         byte b = this.palette[idx * 3 + 2];
/* 406 */         byte a = -1;
/* 407 */         buffer.put(b).put(g).put(r).put(a);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void expand4(byte[] src, byte[] dst) {
/* 413 */     for (int i = 1, n = dst.length; i < n; i += 2) {
/* 414 */       int val = src[1 + (i >> 1)] & 0xFF;
/* 415 */       switch (n - i)
/*     */       { default:
/* 417 */           dst[i + 1] = (byte)(val & 0xF); break;
/*     */         case 1:
/* 419 */           break; }  dst[i] = (byte)(val >> 4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void expand2(byte[] src, byte[] dst) {
/* 425 */     for (int i = 1, n = dst.length; i < n; i += 4) {
/* 426 */       int val = src[1 + (i >> 2)] & 0xFF;
/* 427 */       switch (n - i)
/*     */       { default:
/* 429 */           dst[i + 3] = (byte)(val & 0x3);
/*     */         case 3:
/* 431 */           dst[i + 2] = (byte)(val >> 2 & 0x3);
/*     */         case 2:
/* 433 */           dst[i + 1] = (byte)(val >> 4 & 0x3); break;
/*     */         case 1:
/* 435 */           break; }  dst[i] = (byte)(val >> 6);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void expand1(byte[] src, byte[] dst) {
/* 441 */     for (int i = 1, n = dst.length; i < n; i += 8) {
/* 442 */       int val = src[1 + (i >> 3)] & 0xFF;
/* 443 */       switch (n - i)
/*     */       { default:
/* 445 */           dst[i + 7] = (byte)(val & 0x1);
/*     */         case 7:
/* 447 */           dst[i + 6] = (byte)(val >> 1 & 0x1);
/*     */         case 6:
/* 449 */           dst[i + 5] = (byte)(val >> 2 & 0x1);
/*     */         case 5:
/* 451 */           dst[i + 4] = (byte)(val >> 3 & 0x1);
/*     */         case 4:
/* 453 */           dst[i + 3] = (byte)(val >> 4 & 0x1);
/*     */         case 3:
/* 455 */           dst[i + 2] = (byte)(val >> 5 & 0x1);
/*     */         case 2:
/* 457 */           dst[i + 1] = (byte)(val >> 6 & 0x1); break;
/*     */         case 1:
/* 459 */           break; }  dst[i] = (byte)(val >> 7);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void unfilter(byte[] curLine, byte[] prevLine) throws IOException {
/* 465 */     switch (curLine[0]) {
/*     */       case 0:
/*     */         return;
/*     */       case 1:
/* 469 */         unfilterSub(curLine);
/*     */       
/*     */       case 2:
/* 472 */         unfilterUp(curLine, prevLine);
/*     */       
/*     */       case 3:
/* 475 */         unfilterAverage(curLine, prevLine);
/*     */       
/*     */       case 4:
/* 478 */         unfilterPaeth(curLine, prevLine);
/*     */     } 
/*     */     
/* 481 */     throw new IOException("invalide filter type in scanline: " + curLine[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   private void unfilterSub(byte[] curLine) {
/* 486 */     int bpp = this.bytesPerPixel;
/* 487 */     for (int i = bpp + 1, n = curLine.length; i < n; i++) {
/* 488 */       curLine[i] = (byte)(curLine[i] + curLine[i - bpp]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void unfilterUp(byte[] curLine, byte[] prevLine) {
/* 493 */     int bpp = this.bytesPerPixel;
/* 494 */     for (int i = 1, n = curLine.length; i < n; i++) {
/* 495 */       curLine[i] = (byte)(curLine[i] + prevLine[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void unfilterAverage(byte[] curLine, byte[] prevLine) {
/* 500 */     int bpp = this.bytesPerPixel;
/*     */     
/*     */     int i;
/* 503 */     for (i = 1; i <= bpp; i++) {
/* 504 */       curLine[i] = (byte)(curLine[i] + (byte)((prevLine[i] & 0xFF) >>> 1));
/*     */     }
/* 506 */     for (int n = curLine.length; i < n; i++) {
/* 507 */       curLine[i] = (byte)(curLine[i] + (byte)((prevLine[i] & 0xFF) + (curLine[i - bpp] & 0xFF) >>> 1));
/*     */     }
/*     */   }
/*     */   
/*     */   private void unfilterPaeth(byte[] curLine, byte[] prevLine) {
/* 512 */     int bpp = this.bytesPerPixel;
/*     */     
/*     */     int i;
/* 515 */     for (i = 1; i <= bpp; i++) {
/* 516 */       curLine[i] = (byte)(curLine[i] + prevLine[i]);
/*     */     }
/* 518 */     for (int n = curLine.length; i < n; i++) {
/* 519 */       int a = curLine[i - bpp] & 0xFF;
/* 520 */       int b = prevLine[i] & 0xFF;
/* 521 */       int c = prevLine[i - bpp] & 0xFF;
/* 522 */       int p = a + b - c;
/* 523 */       int pa = p - a;
/* 524 */       if (pa < 0) pa = -pa; 
/* 525 */       int pb = p - b;
/* 526 */       if (pb < 0) pb = -pb; 
/* 527 */       int pc = p - c;
/* 528 */       if (pc < 0) pc = -pc; 
/* 529 */       if (pa <= pb && pa <= pc) {
/* 530 */         c = a;
/* 531 */       } else if (pb <= pc) {
/* 532 */         c = b;
/* 533 */       }  curLine[i] = (byte)(curLine[i] + (byte)c);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readIHDR() throws IOException {
/* 538 */     checkChunkLength(13);
/* 539 */     readChunk(this.buffer, 0, 13);
/* 540 */     this.width = readInt(this.buffer, 0);
/* 541 */     this.height = readInt(this.buffer, 4);
/* 542 */     this.bitdepth = this.buffer[8] & 0xFF;
/* 543 */     this.colorType = this.buffer[9] & 0xFF;
/*     */     
/* 545 */     switch (this.colorType) {
/*     */       case 0:
/* 547 */         if (this.bitdepth != 8) {
/* 548 */           throw new IOException("Unsupported bit depth: " + this.bitdepth);
/*     */         }
/* 550 */         this.bytesPerPixel = 1;
/*     */         break;
/*     */       case 4:
/* 553 */         if (this.bitdepth != 8) {
/* 554 */           throw new IOException("Unsupported bit depth: " + this.bitdepth);
/*     */         }
/* 556 */         this.bytesPerPixel = 2;
/*     */         break;
/*     */       case 2:
/* 559 */         if (this.bitdepth != 8) {
/* 560 */           throw new IOException("Unsupported bit depth: " + this.bitdepth);
/*     */         }
/* 562 */         this.bytesPerPixel = 3;
/*     */         break;
/*     */       case 6:
/* 565 */         if (this.bitdepth != 8) {
/* 566 */           throw new IOException("Unsupported bit depth: " + this.bitdepth);
/*     */         }
/* 568 */         this.bytesPerPixel = 4;
/*     */         break;
/*     */       case 3:
/* 571 */         switch (this.bitdepth) {
/*     */           case 1:
/*     */           case 2:
/*     */           case 4:
/*     */           case 8:
/* 576 */             this.bytesPerPixel = 1;
/*     */             break;
/*     */         } 
/* 579 */         throw new IOException("Unsupported bit depth: " + this.bitdepth);
/*     */ 
/*     */       
/*     */       default:
/* 583 */         throw new IOException("unsupported color format: " + this.colorType);
/*     */     } 
/*     */     
/* 586 */     if (this.buffer[10] != 0) {
/* 587 */       throw new IOException("unsupported compression method");
/*     */     }
/* 589 */     if (this.buffer[11] != 0) {
/* 590 */       throw new IOException("unsupported filtering method");
/*     */     }
/* 592 */     if (this.buffer[12] != 0) {
/* 593 */       throw new IOException("unsupported interlace method");
/*     */     }
/*     */   }
/*     */   
/*     */   private void readPLTE() throws IOException {
/* 598 */     int paletteEntries = this.chunkLength / 3;
/* 599 */     if (paletteEntries < 1 || paletteEntries > 256 || this.chunkLength % 3 != 0) {
/* 600 */       throw new IOException("PLTE chunk has wrong length");
/*     */     }
/* 602 */     this.palette = new byte[paletteEntries * 3];
/* 603 */     readChunk(this.palette, 0, this.palette.length);
/*     */   }
/*     */   
/*     */   private void readtRNS() throws IOException {
/* 607 */     switch (this.colorType) {
/*     */       case 0:
/* 609 */         checkChunkLength(2);
/* 610 */         this.transPixel = new byte[2];
/* 611 */         readChunk(this.transPixel, 0, 2);
/*     */         break;
/*     */       case 2:
/* 614 */         checkChunkLength(6);
/* 615 */         this.transPixel = new byte[6];
/* 616 */         readChunk(this.transPixel, 0, 6);
/*     */         break;
/*     */       case 3:
/* 619 */         if (this.palette == null) {
/* 620 */           throw new IOException("tRNS chunk without PLTE chunk");
/*     */         }
/* 622 */         this.paletteA = new byte[this.palette.length / 3];
/* 623 */         Arrays.fill(this.paletteA, (byte)-1);
/* 624 */         readChunk(this.paletteA, 0, this.paletteA.length);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void closeChunk() throws IOException {
/* 632 */     if (this.chunkRemaining > 0) {
/*     */       
/* 634 */       skip((this.chunkRemaining + 4));
/*     */     } else {
/* 636 */       readFully(this.buffer, 0, 4);
/* 637 */       int expectedCrc = readInt(this.buffer, 0);
/* 638 */       int computedCrc = (int)this.crc.getValue();
/* 639 */       if (computedCrc != expectedCrc) {
/* 640 */         throw new IOException("Invalid CRC");
/*     */       }
/*     */     } 
/* 643 */     this.chunkRemaining = 0;
/* 644 */     this.chunkLength = 0;
/* 645 */     this.chunkType = 0;
/*     */   }
/*     */   
/*     */   private void openChunk() throws IOException {
/* 649 */     readFully(this.buffer, 0, 8);
/* 650 */     this.chunkLength = readInt(this.buffer, 0);
/* 651 */     this.chunkType = readInt(this.buffer, 4);
/* 652 */     this.chunkRemaining = this.chunkLength;
/* 653 */     this.crc.reset();
/* 654 */     this.crc.update(this.buffer, 4, 4);
/*     */   }
/*     */   
/*     */   private void openChunk(int expected) throws IOException {
/* 658 */     openChunk();
/* 659 */     if (this.chunkType != expected) {
/* 660 */       throw new IOException("Expected chunk: " + Integer.toHexString(expected));
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkChunkLength(int expected) throws IOException {
/* 665 */     if (this.chunkLength != expected) {
/* 666 */       throw new IOException("Chunk has wrong size");
/*     */     }
/*     */   }
/*     */   
/*     */   private int readChunk(byte[] buffer, int offset, int length) throws IOException {
/* 671 */     if (length > this.chunkRemaining) {
/* 672 */       length = this.chunkRemaining;
/*     */     }
/* 674 */     readFully(buffer, offset, length);
/* 675 */     this.crc.update(buffer, offset, length);
/* 676 */     this.chunkRemaining -= length;
/* 677 */     return length;
/*     */   }
/*     */   
/*     */   private void refillInflater(Inflater inflater) throws IOException {
/* 681 */     while (this.chunkRemaining == 0) {
/* 682 */       closeChunk();
/* 683 */       openChunk(1229209940);
/*     */     } 
/* 685 */     int read = readChunk(this.buffer, 0, this.buffer.length);
/* 686 */     inflater.setInput(this.buffer, 0, read);
/*     */   }
/*     */   
/*     */   private void readChunkUnzip(Inflater inflater, byte[] buffer, int offset, int length) throws IOException {
/*     */     try {
/*     */       do {
/* 692 */         int read = inflater.inflate(buffer, offset, length);
/* 693 */         if (read <= 0) {
/* 694 */           if (inflater.finished()) {
/* 695 */             throw new EOFException();
/*     */           }
/* 697 */           if (inflater.needsInput()) {
/* 698 */             refillInflater(inflater);
/*     */           } else {
/* 700 */             throw new IOException("Can't inflate " + length + " bytes");
/*     */           } 
/*     */         } else {
/* 703 */           offset += read;
/* 704 */           length -= read;
/*     */         } 
/* 706 */       } while (length > 0);
/* 707 */     } catch (DataFormatException ex) {
/* 708 */       throw new IOException("inflate error", ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readFully(byte[] buffer, int offset, int length) throws IOException {
/*     */     do {
/* 714 */       int read = this.input.read(buffer, offset, length);
/* 715 */       if (read < 0) {
/* 716 */         throw new EOFException();
/*     */       }
/* 718 */       offset += read;
/* 719 */       length -= read;
/* 720 */     } while (length > 0);
/*     */   }
/*     */   
/*     */   private int readInt(byte[] buffer, int offset) {
/* 724 */     return buffer[offset] << 24 | (buffer[offset + 1] & 0xFF) << 16 | (buffer[offset + 2] & 0xFF) << 8 | buffer[offset + 3] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void skip(long amount) throws IOException {
/* 732 */     while (amount > 0L) {
/* 733 */       long skipped = this.input.skip(amount);
/* 734 */       if (skipped < 0L) {
/* 735 */         throw new EOFException();
/*     */       }
/* 737 */       amount -= skipped;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean checkSignature(byte[] buffer) {
/* 742 */     for (int i = 0; i < SIGNATURE.length; i++) {
/* 743 */       if (buffer[i] != SIGNATURE[i]) {
/* 744 */         return false;
/*     */       }
/*     */     } 
/* 747 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\PNGDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */