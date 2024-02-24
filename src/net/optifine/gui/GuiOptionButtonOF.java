/*    */ package net.optifine.gui;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiOptionButton;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ public class GuiOptionButtonOF
/*    */   extends GuiOptionButton implements IOptionControl {
/*  8 */   private GameSettings.Options option = null;
/*    */ 
/*    */   
/*    */   public GuiOptionButtonOF(int id, int x, int y, GameSettings.Options option, String text) {
/* 12 */     super(id, x, y, option, text);
/* 13 */     this.option = option;
/*    */   }
/*    */ 
/*    */   
/*    */   public GameSettings.Options getOption() {
/* 18 */     return this.option;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\gui\GuiOptionButtonOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */