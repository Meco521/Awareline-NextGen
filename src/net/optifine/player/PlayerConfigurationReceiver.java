/*    */ package net.optifine.player;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonParser;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.http.IFileDownloadListener;
/*    */ 
/*    */ public class PlayerConfigurationReceiver
/*    */   implements IFileDownloadListener
/*    */ {
/* 12 */   private String player = null;
/*    */ 
/*    */   
/*    */   public PlayerConfigurationReceiver(String player) {
/* 16 */     this.player = player;
/*    */   }
/*    */ 
/*    */   
/*    */   public void fileDownloadFinished(String url, byte[] bytes, Throwable exception) {
/* 21 */     if (bytes != null)
/*    */       
/*    */       try {
/*    */         
/* 25 */         String s = new String(bytes, StandardCharsets.US_ASCII);
/* 26 */         JsonParser jsonparser = new JsonParser();
/* 27 */         JsonElement jsonelement = jsonparser.parse(s);
/* 28 */         PlayerConfigurationParser playerconfigurationparser = new PlayerConfigurationParser(this.player);
/* 29 */         PlayerConfiguration playerconfiguration = playerconfigurationparser.parsePlayerConfiguration(jsonelement);
/*    */         
/* 31 */         if (playerconfiguration != null)
/*    */         {
/* 33 */           playerconfiguration.setInitialized(true);
/* 34 */           PlayerConfigurations.setPlayerConfiguration(this.player, playerconfiguration);
/*    */         }
/*    */       
/* 37 */       } catch (Exception exception1) {
/*    */         
/* 39 */         Config.dbg("Error parsing configuration: " + url + ", " + exception1.getClass().getName() + ": " + exception1.getMessage());
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\player\PlayerConfigurationReceiver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */