/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Random;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ public class MathUtils
/*     */ {
/*     */   public static final float PI = 3.1415927F;
/*     */   public static final float PI2 = 6.2831855F;
/*     */   public static final float PId2 = 1.5707964F;
/*  13 */   private static final float[] ASIN_TABLE = new float[65536];
/*     */ 
/*     */   
/*     */   public static float asin(float value) {
/*  17 */     return ASIN_TABLE[(int)((value + 1.0F) * 32767.5D) & 0xFFFF];
/*     */   }
/*     */ 
/*     */   
/*     */   public static float acos(float value) {
/*  22 */     return 1.5707964F - ASIN_TABLE[(int)((value + 1.0F) * 32767.5D) & 0xFFFF];
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getAverage(int[] vals) {
/*  27 */     if (vals.length <= 0)
/*     */     {
/*  29 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*  33 */     int i = getSum(vals);
/*  34 */     int j = i / vals.length;
/*  35 */     return j;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getSum(int[] vals) {
/*  41 */     if (vals.length <= 0)
/*     */     {
/*  43 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*  47 */     int i = 0;
/*     */     
/*  49 */     for (int j = 0; j < vals.length; j++) {
/*     */       
/*  51 */       int k = vals[j];
/*  52 */       i += k;
/*     */     } 
/*     */     
/*  55 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int roundDownToPowerOfTwo(int val) {
/*  61 */     int i = MathHelper.roundUpToPowerOfTwo(val);
/*  62 */     return (val == i) ? i : (i / 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean equalsDelta(float f1, float f2, float delta) {
/*  67 */     return (Math.abs(f1 - f2) <= delta);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float toDeg(float angle) {
/*  72 */     return angle * 180.0F / MathHelper.PI;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float toRad(float angle) {
/*  77 */     return angle / 180.0F * MathHelper.PI;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float roundToFloat(double d) {
/*  82 */     return (float)(Math.round(d * 1.0E8D) / 1.0E8D);
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/*  87 */     for (int i = 0; i < 65536; i++)
/*     */     {
/*  89 */       ASIN_TABLE[i] = (float)Math.asin(i / 32767.5D - 1.0D);
/*     */     }
/*     */     
/*  92 */     for (int j = -1; j < 2; j++)
/*     */     {
/*  94 */       ASIN_TABLE[(int)((j + 1.0D) * 32767.5D) & 0xFFFF] = (float)Math.asin(j);
/*     */     }
/*     */   }
/*     */   
/*     */   public static double randomNumber(double max, double min) {
/*  99 */     return Math.random() * (max - min) + min;
/*     */   }
/*     */   public static double getRandom(double min, double max) {
/* 102 */     Random random = new Random();
/* 103 */     double range = max - min;
/* 104 */     double scaled = random.nextDouble() * range;
/* 105 */     if (scaled > max) {
/* 106 */       scaled = max;
/*     */     }
/* 108 */     double shifted = scaled + min;
/* 109 */     if (shifted > max) {
/* 110 */       shifted = max;
/*     */     }
/* 112 */     return shifted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[][] getArcVertices(float radius, float angleStart, float angleEnd, int segments) {
/* 119 */     float range = Math.max(angleStart, angleEnd) - Math.min(angleStart, angleEnd);
/* 120 */     int nSegments = Math.max(2, Math.round(360.0F / range * segments));
/* 121 */     float segDeg = range / nSegments;
/*     */     
/* 123 */     float[][] vertices = new float[nSegments + 1][2];
/* 124 */     for (int i = 0; i <= nSegments; i++) {
/* 125 */       float angleOfVert = (angleStart + i * segDeg) / 180.0F * 3.1415927F;
/* 126 */       vertices[i][0] = MathHelper.sin(angleOfVert) * radius;
/* 127 */       vertices[i][1] = -MathHelper.cos(angleOfVert) * radius;
/*     */     } 
/*     */     
/* 130 */     return vertices;
/*     */   }
/*     */   
/*     */   public static float getRandomFloat(float max, float min) {
/* 134 */     SecureRandom random = new SecureRandom();
/* 135 */     return random.nextFloat() * (max - min) + min;
/*     */   }
/*     */   
/*     */   public static double getIncremental(double val, double inc) {
/* 139 */     double one = 1.0D / inc;
/* 140 */     return Math.round(val * one) / one;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\MathUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */