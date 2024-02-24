/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import awareline.main.event.Event;
/*    */ import awareline.main.event.EventManager;
/*    */ import awareline.main.event.events.world.worldChangeEvents.LoadWorldEvent;
/*    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C00PacketKeepAlive;
/*    */ import net.optifine.CustomLoadingScreen;
/*    */ import net.optifine.CustomLoadingScreens;
/*    */ 
/*    */ public class GuiDownloadTerrain extends GuiScreen {
/*    */   private final NetHandlerPlayClient netHandlerPlayClient;
/* 15 */   private final CustomLoadingScreen customLoadingScreen = CustomLoadingScreens.getCustomLoadingScreen();
/*    */   private int progress;
/*    */   
/*    */   public GuiDownloadTerrain(NetHandlerPlayClient netHandler) {
/* 19 */     this.netHandlerPlayClient = netHandler;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void keyTyped(char typedChar, int keyCode) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 35 */     EventManager.call((Event)new LoadWorldEvent());
/* 36 */     this.buttonList.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateScreen() {
/* 44 */     this.progress++;
/*    */     
/* 46 */     if (this.progress % 20 == 0)
/*    */     {
/* 48 */       this.netHandlerPlayClient.addToSendQueue((Packet)new C00PacketKeepAlive());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 57 */     if (this.customLoadingScreen != null) {
/*    */       
/* 59 */       this.customLoadingScreen.drawBackground(this.width, this.height);
/*    */     }
/*    */     else {
/*    */       
/* 63 */       drawBackground(0);
/*    */     } 
/*    */     
/* 66 */     drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingTerrain", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
/* 67 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean doesGuiPauseGame() {
/* 75 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiDownloadTerrain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */