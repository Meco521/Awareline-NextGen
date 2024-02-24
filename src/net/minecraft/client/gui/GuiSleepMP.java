/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiSleepMP
/*    */   extends GuiChat
/*    */ {
/*    */   public void initGui() {
/* 17 */     super.initGui();
/* 18 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 40, I18n.format("multiplayer.stopSleeping", new Object[0])));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 27 */     if (keyCode == 1) {
/*    */       
/* 29 */       wakeFromSleep();
/*    */     }
/* 31 */     else if (keyCode != 28 && keyCode != 156) {
/*    */       
/* 33 */       super.keyTyped(typedChar, keyCode);
/*    */     }
/*    */     else {
/*    */       
/* 37 */       String s = this.inputField.getText().trim();
/*    */       
/* 39 */       if (!s.isEmpty())
/*    */       {
/* 41 */         this.mc.thePlayer.sendChatMessage(s);
/*    */       }
/*    */       
/* 44 */       this.inputField.setText("");
/* 45 */       this.mc.ingameGUI.getChatGUI().resetScroll();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 54 */     if (button.id == 1) {
/*    */       
/* 56 */       wakeFromSleep();
/*    */     }
/*    */     else {
/*    */       
/* 60 */       super.actionPerformed(button);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void wakeFromSleep() {
/* 66 */     NetHandlerPlayClient nethandlerplayclient = this.mc.thePlayer.sendQueue;
/* 67 */     nethandlerplayclient.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SLEEPING));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiSleepMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */