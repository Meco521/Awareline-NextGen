/*     */ package awareline.main.utility.math;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ public class MathUtil
/*     */ {
/*  11 */   public static final Random random = new Random();
/*     */   
/*     */   public static double randomFloat(float min, float max) {
/*  14 */     return ThreadLocalRandom.current().nextDouble(min, max);
/*     */   }
/*     */   
/*     */   public static double round(double in, int places) {
/*  18 */     places = (int)MathHelper.clamp_double(places, 0.0D, 2.147483647E9D);
/*  19 */     return Double.parseDouble(String.format("%." + places + "f", new Object[] { Double.valueOf(in) }));
/*     */   }
/*     */   public static double roundToHalf(double d) {
/*  22 */     return Math.round(d * 2.0D) / 2.0D;
/*     */   }
/*     */   
/*     */   public static double round(double num, double increment) {
/*  26 */     BigDecimal bd = new BigDecimal(num);
/*  27 */     bd = bd.setScale((int)increment, RoundingMode.HALF_UP);
/*  28 */     return bd.doubleValue();
/*     */   }
/*     */   public static float lerp(float a, float b, float c) {
/*  31 */     return a + c * (b - a);
/*     */   }
/*     */   
/*     */   public static double lerp(double a, double b, double c) {
/*  35 */     return a + c * (b - a);
/*     */   }
/*     */   
/*     */   public static double roundOther(double value, int places) {
/*  39 */     if (places < 0) {
/*  40 */       throw new IllegalArgumentException();
/*     */     }
/*  42 */     BigDecimal bd = new BigDecimal(value);
/*  43 */     bd = bd.setScale(places, RoundingMode.HALF_UP);
/*  44 */     return bd.doubleValue();
/*     */   }
/*     */   
/*     */   public static float getRandomInRange(float min, float max) {
/*  48 */     Random random = new Random();
/*  49 */     return random.nextFloat() * (max - min) + min;
/*     */   }
/*     */   
/*     */   public static boolean parsable(String s, byte type) {
/*     */     try {
/*  54 */       switch (type) {
/*     */         case 0:
/*  56 */           Short.parseShort(s);
/*     */           break;
/*     */         
/*     */         case 1:
/*  60 */           Byte.parseByte(s);
/*     */           break;
/*     */         
/*     */         case 2:
/*  64 */           Integer.parseInt(s);
/*     */           break;
/*     */         
/*     */         case 3:
/*  68 */           Float.parseFloat(s);
/*     */           break;
/*     */         
/*     */         case 4:
/*  72 */           Double.parseDouble(s);
/*     */           break;
/*     */         
/*     */         case 5:
/*  76 */           Long.parseLong(s);
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*  82 */     } catch (NumberFormatException e) {
/*  83 */       e.printStackTrace();
/*  84 */       return false;
/*     */     } 
/*  86 */     return true;
/*     */   }
/*     */   
/*     */   public static double square(double in) {
/*  90 */     return in * in;
/*     */   }
/*     */   
/*     */   public static double randomDouble(double min, double max) {
/*  94 */     return ThreadLocalRandom.current().nextDouble(min, max);
/*     */   }
/*     */   
/*     */   public static double randomNumber(double max, double min) {
/*  98 */     return Math.random() * (max - min) + min;
/*     */   }
/*     */   
/*     */   public static int getNextPostion(int position, int toPosition, double count) {
/* 102 */     int pos = position;
/* 103 */     if (pos < toPosition) {
/* 104 */       int speed = (int)((toPosition - pos) / count);
/* 105 */       if (speed < 1) {
/* 106 */         speed = 1;
/*     */       }
/* 108 */       pos += speed;
/* 109 */     } else if (pos > toPosition) {
/* 110 */       int speed = (int)((pos - toPosition) / count);
/* 111 */       if (speed < 1) {
/* 112 */         speed = 1;
/*     */       }
/* 114 */       pos -= speed;
/*     */     } 
/* 116 */     return pos;
/*     */   }
/*     */   
/*     */   public static double random(double max, double min) {
/* 120 */     return Math.random() * (max - min) + min;
/*     */   }
/*     */   
/*     */   public static double randomMis(double min, double max) {
/* 124 */     Random random = new Random();
/* 125 */     return min + random.nextDouble() * (max - min);
/*     */   }
/*     */   
/*     */   public static float getRandom() {
/* 129 */     Random random = new Random();
/* 130 */     return random.nextFloat();
/*     */   }
/*     */   
/*     */   public static long randomClickDelay(double minCPS, double maxCPS) {
/* 134 */     return (long)(Math.random() * (1000.0D / minCPS - 1000.0D / maxCPS + 1.0D) + 1000.0D / maxCPS);
/*     */   }
/*     */   
/*     */   public static double getRandom(double min, double max) {
/* 138 */     Random random = new Random();
/* 139 */     double range = max - min;
/* 140 */     double scaled = random.nextDouble() * range;
/* 141 */     if (scaled > max) {
/* 142 */       scaled = max;
/*     */     }
/* 144 */     double shifted = scaled + min;
/* 145 */     if (shifted > max) {
/* 146 */       shifted = max;
/*     */     }
/* 148 */     return shifted;
/*     */   }
/*     */   
/*     */   public static Double interpolate(double oldValue, double newValue, double interpolationValue) {
/* 152 */     return Double.valueOf(oldValue + (newValue - oldValue) * interpolationValue);
/*     */   }
/*     */   
/*     */   public static float interpolateFloat(float oldValue, float newValue, double interpolationValue) {
/* 156 */     return interpolate(oldValue, newValue, (float)interpolationValue).floatValue();
/*     */   }
/*     */   
/*     */   public static int interpolateInt(int oldValue, int newValue, double interpolationValue) {
/* 160 */     return interpolate(oldValue, newValue, (float)interpolationValue).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[][] getArcVertices(float radius, float angleStart, float angleEnd, int segments) {
/* 167 */     float range = Math.max(angleStart, angleEnd) - Math.min(angleStart, angleEnd);
/* 168 */     int nSegments = Math.max(2, Math.round(360.0F / range * segments));
/* 169 */     float segDeg = range / nSegments;
/*     */     
/* 171 */     float[][] vertices = new float[nSegments + 1][2];
/* 172 */     for (int i = 0; i <= nSegments; i++) {
/* 173 */       float angleOfVert = (angleStart + i * segDeg) / 180.0F * 3.1415927F;
/* 174 */       vertices[i][0] = MathHelper.sin(angleOfVert) * radius;
/* 175 */       vertices[i][1] = -MathHelper.cos(angleOfVert) * radius;
/*     */     } 
/*     */     
/* 178 */     return vertices;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\math\MathUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */