/*    */ package net.optifine.util;
/*    */ 
/*    */ 
/*    */ public class SmoothFloat
/*    */ {
/*    */   private float valueLast;
/*    */   private float timeFadeUpSec;
/*    */   private float timeFadeDownSec;
/*    */   private long timeLastMs;
/*    */   
/*    */   public SmoothFloat(float valueLast, float timeFadeSec) {
/* 12 */     this(valueLast, timeFadeSec, timeFadeSec);
/*    */   }
/*    */ 
/*    */   
/*    */   public SmoothFloat(float valueLast, float timeFadeUpSec, float timeFadeDownSec) {
/* 17 */     this.valueLast = valueLast;
/* 18 */     this.timeFadeUpSec = timeFadeUpSec;
/* 19 */     this.timeFadeDownSec = timeFadeDownSec;
/* 20 */     this.timeLastMs = System.currentTimeMillis();
/*    */   }
/*    */ 
/*    */   
/*    */   public float getValueLast() {
/* 25 */     return this.valueLast;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getTimeFadeUpSec() {
/* 30 */     return this.timeFadeUpSec;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getTimeFadeDownSec() {
/* 35 */     return this.timeFadeDownSec;
/*    */   }
/*    */ 
/*    */   
/*    */   public long getTimeLastMs() {
/* 40 */     return this.timeLastMs;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getSmoothValue(float value, float timeFadeUpSec, float timeFadeDownSec) {
/* 45 */     this.timeFadeUpSec = timeFadeUpSec;
/* 46 */     this.timeFadeDownSec = timeFadeDownSec;
/* 47 */     return getSmoothValue(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getSmoothValue(float value) {
/* 52 */     long i = System.currentTimeMillis();
/* 53 */     float f = this.valueLast;
/* 54 */     long j = this.timeLastMs;
/* 55 */     float f1 = (float)(i - j) / 1000.0F;
/* 56 */     float f2 = (value >= f) ? this.timeFadeUpSec : this.timeFadeDownSec;
/* 57 */     float f3 = getSmoothValue(f, value, f1, f2);
/* 58 */     this.valueLast = f3;
/* 59 */     this.timeLastMs = i;
/* 60 */     return f3;
/*    */   }
/*    */   
/*    */   public static float getSmoothValue(float valPrev, float value, float timeDeltaSec, float timeFadeSec) {
/*    */     float f1;
/* 65 */     if (timeDeltaSec <= 0.0F)
/*    */     {
/* 67 */       return valPrev;
/*    */     }
/*    */ 
/*    */     
/* 71 */     float f = value - valPrev;
/*    */ 
/*    */     
/* 74 */     if (timeFadeSec > 0.0F && timeDeltaSec < timeFadeSec && Math.abs(f) > 1.0E-6F) {
/*    */       
/* 76 */       float f2 = timeFadeSec / timeDeltaSec;
/* 77 */       float f3 = 4.61F;
/* 78 */       float f4 = 0.13F;
/* 79 */       float f5 = 10.0F;
/* 80 */       float f6 = f3 - 1.0F / (f4 + f2 / f5);
/* 81 */       float f7 = timeDeltaSec / timeFadeSec * f6;
/* 82 */       f7 = NumUtils.limit(f7, 0.0F, 1.0F);
/* 83 */       f1 = valPrev + f * f7;
/*    */     }
/*    */     else {
/*    */       
/* 87 */       f1 = value;
/*    */     } 
/*    */     
/* 90 */     return f1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\SmoothFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */