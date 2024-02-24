/*    */ package net.optifine.player;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.optifine.http.FileDownloadThread;
/*    */ import net.optifine.http.HttpUtils;
/*    */ 
/*    */ public class PlayerConfigurations
/*    */ {
/* 14 */   private static Map mapConfigurations = null;
/* 15 */   private static final boolean reloadPlayerItems = Boolean.getBoolean("player.models.reload");
/* 16 */   private static long timeReloadPlayerItemsMs = System.currentTimeMillis();
/*    */ 
/*    */   
/*    */   public static void renderPlayerItems(ModelBiped modelBiped, AbstractClientPlayer player, float scale, float partialTicks) {
/* 20 */     PlayerConfiguration playerconfiguration = getPlayerConfiguration(player);
/*    */     
/* 22 */     if (playerconfiguration != null)
/*    */     {
/* 24 */       playerconfiguration.renderPlayerItems(modelBiped, player, scale, partialTicks);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized PlayerConfiguration getPlayerConfiguration(AbstractClientPlayer player) {
/* 30 */     if (reloadPlayerItems && System.currentTimeMillis() > timeReloadPlayerItemsMs + 5000L) {
/*    */       
/* 32 */       EntityPlayerSP entityPlayerSP = (Minecraft.getMinecraft()).thePlayer;
/*    */       
/* 34 */       if (entityPlayerSP != null) {
/*    */         
/* 36 */         setPlayerConfiguration(entityPlayerSP.getNameClear(), (PlayerConfiguration)null);
/* 37 */         timeReloadPlayerItemsMs = System.currentTimeMillis();
/*    */       } 
/*    */     } 
/*    */     
/* 41 */     String s1 = player.getNameClear();
/*    */     
/* 43 */     if (s1 == null)
/*    */     {
/* 45 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 49 */     PlayerConfiguration playerconfiguration = (PlayerConfiguration)getMapConfigurations().get(s1);
/*    */     
/* 51 */     if (playerconfiguration == null) {
/*    */       
/* 53 */       playerconfiguration = new PlayerConfiguration();
/* 54 */       getMapConfigurations().put(s1, playerconfiguration);
/* 55 */       PlayerConfigurationReceiver playerconfigurationreceiver = new PlayerConfigurationReceiver(s1);
/* 56 */       String s = HttpUtils.getPlayerItemsUrl() + "/users/" + s1 + ".cfg";
/* 57 */       FileDownloadThread filedownloadthread = new FileDownloadThread(s, playerconfigurationreceiver);
/* 58 */       filedownloadthread.start();
/*    */     } 
/*    */     
/* 61 */     return playerconfiguration;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized void setPlayerConfiguration(String player, PlayerConfiguration pc) {
/* 67 */     getMapConfigurations().put(player, pc);
/*    */   }
/*    */ 
/*    */   
/*    */   private static Map getMapConfigurations() {
/* 72 */     if (mapConfigurations == null)
/*    */     {
/* 74 */       mapConfigurations = new HashMap<>();
/*    */     }
/*    */     
/* 77 */     return mapConfigurations;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\player\PlayerConfigurations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */