/*    */ package com.me.guichaguri.betterfps;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BetterFpsConfig
/*    */ {
/*  9 */   protected static BetterFpsConfig instance = null;
/*    */   
/*    */   public static BetterFpsConfig getConfig() {
/* 12 */     if (instance == null) BetterFpsHelper.loadConfig(); 
/* 13 */     return instance;
/*    */   }
/*    */   
/*    */   public static Object getValue(String key) {
/* 17 */     if (instance == null) BetterFpsHelper.loadConfig(); 
/*    */     try {
/* 19 */       Field f = BetterFpsConfig.class.getDeclaredField(key);
/* 20 */       return f.get(instance);
/* 21 */     } catch (Exception ex) {
/* 22 */       return null;
/*    */     } 
/*    */   }
/*    */   
/* 26 */   public String algorithm = "rivens-half";
/*    */   public boolean updateChecker = true;
/*    */   public boolean preallocateMemory = false;
/*    */   public boolean fastBoxRender = true;
/*    */   public boolean fog = true;
/*    */   public boolean fastHopper = true;
/*    */   public boolean fastBeacon = true;
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\BetterFpsConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */