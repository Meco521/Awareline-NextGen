/*    */ package net.optifine.gui;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiChat;
/*    */ import net.minecraft.client.gui.GuiVideoSettings;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.shaders.Shaders;
/*    */ 
/*    */ public class GuiChatOF
/*    */   extends GuiChat
/*    */ {
/*    */   private static final String CMD_RELOAD_SHADERS = "/reloadShaders";
/*    */   private static final String CMD_RELOAD_CHUNKS = "/reloadChunks";
/*    */   
/*    */   public GuiChatOF(GuiChat guiChat) {
/* 15 */     super(GuiVideoSettings.getGuiChatText(guiChat));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void sendChatMessage(String msg) {
/* 23 */     if (checkCustomCommand(msg)) {
/*    */       
/* 25 */       this.mc.ingameGUI.getChatGUI().addToSentMessages(msg);
/*    */     }
/*    */     else {
/*    */       
/* 29 */       super.sendChatMessage(msg);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean checkCustomCommand(String msg) {
/* 35 */     if (msg == null)
/*    */     {
/* 37 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 41 */     msg = msg.trim();
/*    */     
/* 43 */     if (msg.equals("/reloadShaders")) {
/*    */       
/* 45 */       if (Config.isShaders()) {
/*    */         
/* 47 */         Shaders.uninit();
/* 48 */         Shaders.loadShaderPack();
/*    */       } 
/*    */       
/* 51 */       return true;
/*    */     } 
/* 53 */     if (msg.equals("/reloadChunks")) {
/*    */       
/* 55 */       this.mc.renderGlobal.loadRenderers();
/* 56 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 60 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\gui\GuiChatOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */