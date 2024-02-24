/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.optifine.util.CounterInt;
/*    */ import net.optifine.util.SmoothFloat;
/*    */ 
/*    */ 
/*    */ public class Smoother
/*    */ {
/* 11 */   private static final Map<Integer, SmoothFloat> mapSmoothValues = new HashMap<>();
/* 12 */   private static final CounterInt counterIds = new CounterInt(1);
/*    */ 
/*    */   
/*    */   public static float getSmoothValue(int id, float value, float timeFadeUpSec, float timeFadeDownSec) {
/* 16 */     synchronized (mapSmoothValues) {
/*    */       
/* 18 */       Integer integer = Integer.valueOf(id);
/* 19 */       SmoothFloat smoothfloat = mapSmoothValues.get(integer);
/*    */       
/* 21 */       if (smoothfloat == null) {
/*    */         
/* 23 */         smoothfloat = new SmoothFloat(value, timeFadeUpSec, timeFadeDownSec);
/* 24 */         mapSmoothValues.put(integer, smoothfloat);
/*    */       } 
/*    */       
/* 27 */       float f = smoothfloat.getSmoothValue(value, timeFadeUpSec, timeFadeDownSec);
/* 28 */       return f;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getNextId() {
/* 34 */     synchronized (counterIds) {
/*    */       
/* 36 */       return counterIds.nextValue();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void resetValues() {
/* 42 */     synchronized (mapSmoothValues) {
/*    */       
/* 44 */       mapSmoothValues.clear();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shader\\uniform\Smoother.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */