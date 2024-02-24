/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ public class MovementInputFromOptions
/*    */   extends MovementInput
/*    */ {
/*    */   private final GameSettings gameSettings;
/*    */   
/*    */   public MovementInputFromOptions(GameSettings gameSettingsIn) {
/* 11 */     this.gameSettings = gameSettingsIn;
/*    */   }
/*    */   public void updatePlayerMoveState() {
/* 14 */     this.moveStrafe = 0.0F;
/* 15 */     this.moveForward = 0.0F;
/*    */     
/* 17 */     if (this.gameSettings.keyBindForward.isKeyDown()) {
/* 18 */       this.moveForward++;
/*    */     }
/*    */     
/* 21 */     if (this.gameSettings.keyBindBack.isKeyDown()) {
/* 22 */       this.moveForward--;
/*    */     }
/*    */     
/* 25 */     if (this.gameSettings.keyBindLeft.isKeyDown()) {
/* 26 */       this.moveStrafe++;
/*    */     }
/*    */     
/* 29 */     if (this.gameSettings.keyBindRight.isKeyDown()) {
/* 30 */       this.moveStrafe--;
/*    */     }
/*    */     
/* 33 */     this.jump = this.gameSettings.keyBindJump.isKeyDown();
/* 34 */     this.sneak = this.gameSettings.keyBindSneak.getIsKeyPressed();
/*    */     
/* 36 */     if (this.sneak) {
/* 37 */       this.moveStrafe = (float)(this.moveStrafe * 0.3D);
/* 38 */       this.moveForward = (float)(this.moveForward * 0.3D);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\MovementInputFromOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */