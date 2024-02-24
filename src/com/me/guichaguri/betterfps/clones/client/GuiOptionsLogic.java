/*    */ package com.me.guichaguri.betterfps.clones.client;
/*    */ 
/*    */ import com.me.guichaguri.betterfps.gui.GuiBetterFpsConfig;
/*    */ import com.me.guichaguri.betterfps.transformers.cloner.CopyMode;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiOptions;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiOptionsLogic
/*    */   extends GuiOptions
/*    */ {
/*    */   @CopyMode(CopyMode.Mode.IGNORE)
/*    */   public GuiOptionsLogic(GuiScreen screen, GameSettings settings) {
/* 18 */     super(screen, settings);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @CopyMode(CopyMode.Mode.APPEND)
/*    */   public void initGui() {
/* 26 */     int x_BF = this.width / 2 + 5;
/* 27 */     int y_BF = this.height / 6 + 24 - 8;
/*    */     
/* 29 */     int width_BF = 150;
/*    */     
/* 31 */     if (hasButtonInCoords_BF(x_BF, y_BF, 0, 2)) {
/*    */       
/* 33 */       x_BF = this.width / 2 - 155;
/*    */       
/* 35 */       if (hasButtonInCoords_BF(x_BF, y_BF, 0, 2)) {
/*    */         
/* 37 */         GuiButton language = null;
/* 38 */         for (GuiButton b : this.buttonList) {
/* 39 */           if (b.id == 102) {
/* 40 */             language = b;
/*    */             break;
/*    */           } 
/*    */         } 
/* 44 */         if (language == null) {
/* 45 */           x_BF = 0;
/* 46 */           y_BF = 0;
/* 47 */           width_BF = 100;
/*    */         } else {
/* 49 */           this.buttonList.remove(language);
/*    */           
/* 51 */           this.buttonList.add(language);
/* 52 */           x_BF = language.xPosition + language.width;
/* 53 */           y_BF = language.yPosition;
/* 54 */           width_BF -= language.width;
/*    */         } 
/*    */       } 
/*    */     } 
/* 58 */     this.buttonList.add(new GuiButton(72109, x_BF, y_BF, width_BF, 20, "BetterFps Options"));
/*    */   }
/*    */   
/*    */   private boolean hasButtonInCoords_BF(int x, int y, int xRadius, int yRadius) {
/* 62 */     for (GuiButton b : this.buttonList) {
/* 63 */       if (b.xPosition <= x + xRadius && b.yPosition <= y + yRadius && b.xPosition >= x - xRadius && b.yPosition >= y - yRadius)
/*    */       {
/* 65 */         return true;
/*    */       }
/*    */     } 
/* 68 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @CopyMode(CopyMode.Mode.APPEND)
/*    */   protected void actionPerformed(GuiButton button) {
/* 74 */     if (button.id == 72109) {
/* 75 */       this.mc.gameSettings.saveOptions();
/* 76 */       this.mc.displayGuiScreen((GuiScreen)new GuiBetterFpsConfig((GuiScreen)this));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\clones\client\GuiOptionsLogic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */