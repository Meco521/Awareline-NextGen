/*    */ package com.me.guichaguri.betterfps;
/*    */ 
/*    */ import com.me.guichaguri.betterfps.gui.GuiBetterFpsConfig;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BetterFpsClient
/*    */ {
/*    */   protected static Minecraft mc;
/* 15 */   private static final KeyBinding MENU_KEY = new KeyBinding("BetterFps", 88, "key.categories.misc");
/*    */ 
/*    */   
/*    */   public static void start(Minecraft minecraft) {
/* 19 */     mc = minecraft;
/* 20 */     BetterFps.isClient = true;
/*    */     
/* 22 */     if (BetterFpsConfig.instance == null) {
/* 23 */       BetterFpsHelper.loadConfig();
/*    */     }
/* 25 */     BetterFpsHelper.init();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void worldLoad() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void keyEvent(int key) {
/* 41 */     if (MENU_KEY.getKeyCode() == key)
/* 42 */       mc.displayGuiScreen((GuiScreen)new GuiBetterFpsConfig(mc.currentScreen)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\BetterFpsClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */