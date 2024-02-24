/*   */ package net.minecraft.client;
/*   */ 
/*   */ import awareline.main.ui.gui.guimainmenu.mainmenu.ClientSetting;
/*   */ 
/*   */ 
/*   */ public class ClientBrandRetriever
/*   */ {
/*   */   public static String getClientModName() {
/* 9 */     return ((Boolean)ClientSetting.fakeForge.getValue()).booleanValue() ? "fml,forge" : "vanilla";
/*   */   }
/*   */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\ClientBrandRetriever.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */