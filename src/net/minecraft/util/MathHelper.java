/*     */ package net.minecraft.util;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import net.optifine.util.MathUtils;
/*     */ 
/*     */ 
/*     */ public class MathHelper
/*     */ {
/*  12 */   public static final float PI = MathUtils.roundToFloat(Math.PI);
/*  13 */   public static final float PId2 = MathUtils.roundToFloat(1.5707963267948966D);
/*  14 */   public static final float PI2 = MathUtils.roundToFloat(6.283185307179586D);
/*  15 */   private static final float radToIndex = MathUtils.roundToFloat(651.8986469044033D);
/*  16 */   public static final float deg2Rad = MathUtils.roundToFloat(0.017453292519943295D);
/*  17 */   private static final float[] SIN_TABLE_FAST = new float[4096];
/*     */ 
/*     */   
/*     */   public static boolean fastMath;
/*     */ 
/*     */   
/*  23 */   private static final float[] SIN_TABLE = new float[65536];
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float sin(float p_76126_0_) {
/*  42 */     return fastMath ? SIN_TABLE_FAST[(int)(p_76126_0_ * radToIndex) & 0xFFF] : SIN_TABLE[(int)(p_76126_0_ * 10430.378F) & 0xFFFF];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float cos(float value) {
/*  50 */     return fastMath ? SIN_TABLE_FAST[(int)(value * radToIndex + 1024.0F) & 0xFFF] : SIN_TABLE[(int)(value * 10430.378F + 16384.0F) & 0xFFFF];
/*     */   }
/*     */ 
/*     */   
/*     */   public static float sqrt_float(float value) {
/*  55 */     return (float)Math.sqrt(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float sqrt_double(double value) {
/*  60 */     return (float)Math.sqrt(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int floor_float(float value) {
/*  68 */     int i = (int)value;
/*  69 */     return (value < i) ? (i - 1) : i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int truncateDoubleToInt(double value) {
/*  77 */     return (int)(value + 1024.0D) - 1024;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int floor_double(double value) {
/*  85 */     int i = (int)value;
/*  86 */     return (value < i) ? (i - 1) : i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long floor_double_long(double value) {
/*  94 */     long i = (long)value;
/*  95 */     return (value < i) ? (i - 1L) : i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float abs(float value) {
/* 100 */     return (value >= 0.0F) ? value : -value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int abs_int(int value) {
/* 108 */     return (value >= 0) ? value : -value;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int ceiling_float_int(float value) {
/* 113 */     int i = (int)value;
/* 114 */     return (value > i) ? (i + 1) : i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int ceiling_double_int(double value) {
/* 119 */     int i = (int)value;
/* 120 */     return (value > i) ? (i + 1) : i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int clamp_int(int num, int min, int max) {
/* 129 */     return (num < min) ? min : Math.min(num, max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float clamp_float(float num, float min, float max) {
/* 138 */     return (num < min) ? min : Math.min(num, max);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double clamp_double(double num, double min, double max) {
/* 143 */     return (num < min) ? min : Math.min(num, max);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double denormalizeClamp(double lowerBnd, double upperBnd, double slide) {
/* 148 */     return (slide < 0.0D) ? lowerBnd : ((slide > 1.0D) ? upperBnd : (lowerBnd + (upperBnd - lowerBnd) * slide));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double abs_max(double p_76132_0_, double p_76132_2_) {
/* 156 */     if (p_76132_0_ < 0.0D)
/*     */     {
/* 158 */       p_76132_0_ = -p_76132_0_;
/*     */     }
/*     */     
/* 161 */     if (p_76132_2_ < 0.0D)
/*     */     {
/* 163 */       p_76132_2_ = -p_76132_2_;
/*     */     }
/*     */     
/* 166 */     return Math.max(p_76132_0_, p_76132_2_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int bucketInt(int p_76137_0_, int p_76137_1_) {
/* 174 */     return (p_76137_0_ < 0) ? (-((-p_76137_0_ - 1) / p_76137_1_) - 1) : (p_76137_0_ / p_76137_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getRandomIntegerInRange(Random p_76136_0_, int p_76136_1_, int p_76136_2_) {
/* 179 */     return (p_76136_1_ >= p_76136_2_) ? p_76136_1_ : (p_76136_0_.nextInt(p_76136_2_ - p_76136_1_ + 1) + p_76136_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float randomFloatClamp(Random p_151240_0_, float p_151240_1_, float p_151240_2_) {
/* 184 */     return (p_151240_1_ >= p_151240_2_) ? p_151240_1_ : (p_151240_0_.nextFloat() * (p_151240_2_ - p_151240_1_) + p_151240_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getRandomDoubleInRange(Random p_82716_0_, double p_82716_1_, double p_82716_3_) {
/* 189 */     return (p_82716_1_ >= p_82716_3_) ? p_82716_1_ : (p_82716_0_.nextDouble() * (p_82716_3_ - p_82716_1_) + p_82716_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double average(long[] values) {
/* 194 */     long i = 0L;
/*     */     
/* 196 */     for (long j : values)
/*     */     {
/* 198 */       i += j;
/*     */     }
/*     */     
/* 201 */     return i / values.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean epsilonEquals(float p_180185_0_, float p_180185_1_) {
/* 206 */     return (abs(p_180185_1_ - p_180185_0_) < 1.0E-5F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int normalizeAngle(int p_180184_0_, int p_180184_1_) {
/* 211 */     return (p_180184_0_ % p_180184_1_ + p_180184_1_) % p_180184_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float wrapAngleTo180_float(float value) {
/* 219 */     value %= 360.0F;
/*     */     
/* 221 */     if (value >= 180.0F)
/*     */     {
/* 223 */       value -= 360.0F;
/*     */     }
/*     */     
/* 226 */     if (value < -180.0F)
/*     */     {
/* 228 */       value += 360.0F;
/*     */     }
/*     */     
/* 231 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double wrapAngleTo180_double(double value) {
/* 239 */     value %= 360.0D;
/*     */     
/* 241 */     if (value >= 180.0D)
/*     */     {
/* 243 */       value -= 360.0D;
/*     */     }
/*     */     
/* 246 */     if (value < -180.0D)
/*     */     {
/* 248 */       value += 360.0D;
/*     */     }
/*     */     
/* 251 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int parseIntWithDefault(String p_82715_0_, int p_82715_1_) {
/*     */     try {
/* 261 */       return Integer.parseInt(p_82715_0_);
/*     */     }
/* 263 */     catch (Throwable var3) {
/*     */       
/* 265 */       return p_82715_1_;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int parseIntWithDefaultAndMax(String p_82714_0_, int p_82714_1_, int p_82714_2_) {
/* 274 */     return Math.max(p_82714_2_, parseIntWithDefault(p_82714_0_, p_82714_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double parseDoubleWithDefault(String p_82712_0_, double p_82712_1_) {
/*     */     try {
/* 284 */       return Double.parseDouble(p_82712_0_);
/*     */     }
/* 286 */     catch (Throwable var4) {
/*     */       
/* 288 */       return p_82712_1_;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static double parseDoubleWithDefaultAndMax(String p_82713_0_, double p_82713_1_, double p_82713_3_) {
/* 294 */     return Math.max(p_82713_3_, parseDoubleWithDefault(p_82713_0_, p_82713_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int roundUpToPowerOfTwo(int value) {
/* 302 */     int i = value - 1;
/* 303 */     i |= i >> 1;
/* 304 */     i |= i >> 2;
/* 305 */     i |= i >> 4;
/* 306 */     i |= i >> 8;
/* 307 */     i |= i >> 16;
/* 308 */     return i + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isPowerOfTwo(int value) {
/* 316 */     return (value != 0 && (value & value - 1) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int calculateLogBaseTwoDeBruijn(int value) {
/* 326 */     value = isPowerOfTwo(value) ? value : roundUpToPowerOfTwo(value);
/* 327 */     return multiplyDeBruijnBitPosition[(int)(value * 125613361L >> 27L) & 0x1F];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calculateLogBaseTwo(int value) {
/* 336 */     return calculateLogBaseTwoDeBruijn(value) - (isPowerOfTwo(value) ? 0 : 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int roundUp(int p_154354_0_, int p_154354_1_) {
/* 341 */     if (p_154354_1_ == 0)
/*     */     {
/* 343 */       return 0;
/*     */     }
/* 345 */     if (p_154354_0_ == 0)
/*     */     {
/* 347 */       return p_154354_1_;
/*     */     }
/*     */ 
/*     */     
/* 351 */     if (p_154354_0_ < 0)
/*     */     {
/* 353 */       p_154354_1_ *= -1;
/*     */     }
/*     */     
/* 356 */     int i = p_154354_0_ % p_154354_1_;
/* 357 */     return (i == 0) ? p_154354_0_ : (p_154354_0_ + p_154354_1_ - i);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int func_180183_b(float p_180183_0_, float p_180183_1_, float p_180183_2_) {
/* 363 */     return func_180181_b(floor_float(p_180183_0_ * 255.0F), floor_float(p_180183_1_ * 255.0F), floor_float(p_180183_2_ * 255.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   public static int func_180181_b(int p_180181_0_, int p_180181_1_, int p_180181_2_) {
/* 368 */     int i = (p_180181_0_ << 8) + p_180181_1_;
/* 369 */     i = (i << 8) + p_180181_2_;
/* 370 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int func_180188_d(int p_180188_0_, int p_180188_1_) {
/* 375 */     int i = (p_180188_0_ & 0xFF0000) >> 16;
/* 376 */     int j = (p_180188_1_ & 0xFF0000) >> 16;
/* 377 */     int k = (p_180188_0_ & 0xFF00) >> 8;
/* 378 */     int l = (p_180188_1_ & 0xFF00) >> 8;
/* 379 */     int i1 = p_180188_0_ & 0xFF;
/* 380 */     int j1 = p_180188_1_ & 0xFF;
/* 381 */     int k1 = (int)(i * j / 255.0F);
/* 382 */     int l1 = (int)(k * l / 255.0F);
/* 383 */     int i2 = (int)(i1 * j1 / 255.0F);
/* 384 */     return p_180188_0_ & 0xFF000000 | k1 << 16 | l1 << 8 | i2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double func_181162_h(double p_181162_0_) {
/* 389 */     return p_181162_0_ - Math.floor(p_181162_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static long getPositionRandom(Vec3i pos) {
/* 394 */     return getCoordinateRandom(pos.getX(), pos.getY(), pos.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public static long getCoordinateRandom(int x, int y, int z) {
/* 399 */     long i = x * 3129871L ^ z * 116129781L ^ y;
/* 400 */     i = i * i * 42317861L + i * 11L;
/* 401 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static UUID getRandomUuid(Random rand) {
/* 406 */     long i = rand.nextLong() & 0xFFFFFFFFFFFF0FFFL | 0x4000L;
/* 407 */     long j = rand.nextLong() & 0x3FFFFFFFFFFFFFFFL | Long.MIN_VALUE;
/* 408 */     return new UUID(i, j);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double func_181160_c(double p_181160_0_, double p_181160_2_, double p_181160_4_) {
/* 413 */     return (p_181160_0_ - p_181160_2_) / (p_181160_4_ - p_181160_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double atan2(double p_181159_0_, double p_181159_2_) {
/* 418 */     double d0 = p_181159_2_ * p_181159_2_ + p_181159_0_ * p_181159_0_;
/*     */     
/* 420 */     if (Double.isNaN(d0))
/*     */     {
/* 422 */       return Double.NaN;
/*     */     }
/*     */ 
/*     */     
/* 426 */     boolean flag = (p_181159_0_ < 0.0D);
/*     */     
/* 428 */     if (flag)
/*     */     {
/* 430 */       p_181159_0_ = -p_181159_0_;
/*     */     }
/*     */     
/* 433 */     boolean flag1 = (p_181159_2_ < 0.0D);
/*     */     
/* 435 */     if (flag1)
/*     */     {
/* 437 */       p_181159_2_ = -p_181159_2_;
/*     */     }
/*     */     
/* 440 */     boolean flag2 = (p_181159_0_ > p_181159_2_);
/*     */     
/* 442 */     if (flag2) {
/*     */       
/* 444 */       double d1 = p_181159_2_;
/* 445 */       p_181159_2_ = p_181159_0_;
/* 446 */       p_181159_0_ = d1;
/*     */     } 
/*     */     
/* 449 */     double d9 = func_181161_i(d0);
/* 450 */     p_181159_2_ *= d9;
/* 451 */     p_181159_0_ *= d9;
/* 452 */     double d2 = field_181163_d + p_181159_0_;
/* 453 */     int i = (int)Double.doubleToRawLongBits(d2);
/* 454 */     double d3 = field_181164_e[i];
/* 455 */     double d4 = field_181165_f[i];
/* 456 */     double d5 = d2 - field_181163_d;
/* 457 */     double d6 = p_181159_0_ * d4 - p_181159_2_ * d5;
/* 458 */     double d7 = (6.0D + d6 * d6) * d6 * 0.16666666666666666D;
/* 459 */     double d8 = d3 + d7;
/*     */     
/* 461 */     if (flag2)
/*     */     {
/* 463 */       d8 = 1.5707963267948966D - d8;
/*     */     }
/*     */     
/* 466 */     if (flag1)
/*     */     {
/* 468 */       d8 = Math.PI - d8;
/*     */     }
/*     */     
/* 471 */     if (flag)
/*     */     {
/* 473 */       d8 = -d8;
/*     */     }
/*     */     
/* 476 */     return d8;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double func_181161_i(double p_181161_0_) {
/* 482 */     double d0 = 0.5D * p_181161_0_;
/* 483 */     long i = Double.doubleToRawLongBits(p_181161_0_);
/* 484 */     i = 6910469410427058090L - (i >> 1L);
/* 485 */     p_181161_0_ = Double.longBitsToDouble(i);
/* 486 */     p_181161_0_ *= 1.5D - d0 * p_181161_0_ * p_181161_0_;
/* 487 */     return p_181161_0_;
/*     */   }
/*     */   
/*     */   public static int hsvToRGB(float p_181758_0_, float p_181758_1_, float p_181758_2_) {
/*     */     float f4, f5, f6;
/* 492 */     int j, k, l, i = (int)(p_181758_0_ * 6.0F) % 6;
/* 493 */     float f = p_181758_0_ * 6.0F - i;
/* 494 */     float f1 = p_181758_2_ * (1.0F - p_181758_1_);
/* 495 */     float f2 = p_181758_2_ * (1.0F - f * p_181758_1_);
/* 496 */     float f3 = p_181758_2_ * (1.0F - (1.0F - f) * p_181758_1_);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 501 */     switch (i) {
/*     */       
/*     */       case 0:
/* 504 */         f4 = p_181758_2_;
/* 505 */         f5 = f3;
/* 506 */         f6 = f1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 543 */         j = clamp_int((int)(f4 * 255.0F), 0, 255);
/* 544 */         k = clamp_int((int)(f5 * 255.0F), 0, 255);
/* 545 */         l = clamp_int((int)(f6 * 255.0F), 0, 255);
/* 546 */         return j << 16 | k << 8 | l;case 1: f4 = f2; f5 = p_181758_2_; f6 = f1; j = clamp_int((int)(f4 * 255.0F), 0, 255); k = clamp_int((int)(f5 * 255.0F), 0, 255); l = clamp_int((int)(f6 * 255.0F), 0, 255); return j << 16 | k << 8 | l;case 2: f4 = f1; f5 = p_181758_2_; f6 = f3; j = clamp_int((int)(f4 * 255.0F), 0, 255); k = clamp_int((int)(f5 * 255.0F), 0, 255); l = clamp_int((int)(f6 * 255.0F), 0, 255); return j << 16 | k << 8 | l;case 3: f4 = f1; f5 = f2; f6 = p_181758_2_; j = clamp_int((int)(f4 * 255.0F), 0, 255); k = clamp_int((int)(f5 * 255.0F), 0, 255); l = clamp_int((int)(f6 * 255.0F), 0, 255); return j << 16 | k << 8 | l;case 4: f4 = f3; f5 = f1; f6 = p_181758_2_; j = clamp_int((int)(f4 * 255.0F), 0, 255); k = clamp_int((int)(f5 * 255.0F), 0, 255); l = clamp_int((int)(f6 * 255.0F), 0, 255); return j << 16 | k << 8 | l;case 5: f4 = p_181758_2_; f5 = f1; f6 = f2; j = clamp_int((int)(f4 * 255.0F), 0, 255); k = clamp_int((int)(f5 * 255.0F), 0, 255); l = clamp_int((int)(f6 * 255.0F), 0, 255); return j << 16 | k << 8 | l;
/*     */     } 
/*     */     throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + p_181758_0_ + ", " + p_181758_1_ + ", " + p_181758_2_);
/*     */   }
/*     */   static {
/* 551 */     for (int i = 0; i < 65536; i++)
/*     */     {
/* 553 */       SIN_TABLE[i] = (float)Math.sin(i * Math.PI * 2.0D / 65536.0D);
/*     */     }
/*     */     
/* 556 */     for (int j = 0; j < SIN_TABLE_FAST.length; j++)
/*     */     {
/* 558 */       SIN_TABLE_FAST[j] = MathUtils.roundToFloat(Math.sin(j * Math.PI * 2.0D / 4096.0D)); } 
/*     */   }
/*     */   
/* 561 */   private static final int[] multiplyDeBruijnBitPosition = new int[] { 0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9 };
/* 562 */   private static final double field_181163_d = Double.longBitsToDouble(4805340802404319232L);
/* 563 */   private static final double[] field_181164_e = new double[257];
/* 564 */   private static final double[] field_181165_f = new double[257];
/*     */   static {
/* 566 */     for (int k = 0; k < 257; k++) {
/*     */       
/* 568 */       double d0 = k / 256.0D;
/* 569 */       double d1 = Math.asin(d0);
/* 570 */       field_181165_f[k] = Math.cos(d1);
/* 571 */       field_181164_e[k] = d1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static double round(double value, int places) {
/* 577 */     if (places < 0) {
/* 578 */       throw new IllegalArgumentException();
/*     */     }
/* 580 */     BigDecimal bd = new BigDecimal(value);
/* 581 */     bd = bd.setScale(places, RoundingMode.HALF_UP);
/* 582 */     return bd.doubleValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\MathHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */