/*    */ package com.me.guichaguri.betterfps;
/*    */ 
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BetterFps
/*    */ {
/* 12 */   public static final Logger log = LogManager.getLogger("BetterFps");
/*    */ 
/*    */   
/*    */   public static boolean isClient = false;
/*    */   
/* 17 */   public static int TNT_TICKS = 0;
/* 18 */   public static int MAX_TNT_TICKS = 100;
/*    */   
/* 20 */   public static int TICKABLE_RADIUS_POS = 1;
/* 21 */   public static int TICKABLE_RADIUS_NEG = -1;
/*    */ 
/*    */   
/*    */   public static void serverStart() {}
/*    */ 
/*    */   
/*    */   public static void worldTick() {
/* 28 */     TNT_TICKS = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isTickable(int dX, int dY) {
/* 33 */     return (dX == 0 && dY == 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\BetterFps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */