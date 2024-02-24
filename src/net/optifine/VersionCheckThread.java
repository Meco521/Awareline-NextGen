/*    */ package net.optifine;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.URL;
/*    */ import net.minecraft.client.ClientBrandRetriever;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ 
/*    */ public class VersionCheckThread
/*    */   extends Thread
/*    */ {
/*    */   public VersionCheckThread() {
/* 14 */     super("VersionCheck");
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 19 */     HttpURLConnection httpurlconnection = null;
/*    */ 
/*    */     
/*    */     try {
/* 23 */       Config.dbg("Checking for new version");
/* 24 */       URL url = new URL("http://optifine.net/version/1.8.9/HD_U.txt");
/* 25 */       httpurlconnection = (HttpURLConnection)url.openConnection();
/*    */       
/* 27 */       if ((Config.getGameSettings()).snooperEnabled) {
/*    */         
/* 29 */         httpurlconnection.setRequestProperty("OF-MC-Version", "1.8.9");
/* 30 */         httpurlconnection.setRequestProperty("OF-MC-Brand", ClientBrandRetriever.getClientModName());
/* 31 */         httpurlconnection.setRequestProperty("OF-Edition", "HD_U");
/* 32 */         httpurlconnection.setRequestProperty("OF-Release", "M6_pre1");
/* 33 */         httpurlconnection.setRequestProperty("OF-Java-Version", System.getProperty("java.version"));
/* 34 */         httpurlconnection.setRequestProperty("OF-CpuCount", String.valueOf(Config.getAvailableProcessors()));
/* 35 */         httpurlconnection.setRequestProperty("OF-OpenGL-Version", Config.openGlVersion);
/* 36 */         httpurlconnection.setRequestProperty("OF-OpenGL-Vendor", Config.openGlVendor);
/*    */       } 
/*    */       
/* 39 */       httpurlconnection.setDoInput(true);
/* 40 */       httpurlconnection.setDoOutput(false);
/* 41 */       httpurlconnection.connect();
/*    */ 
/*    */       
/*    */       try {
/* 45 */         InputStream inputstream = httpurlconnection.getInputStream();
/* 46 */         String s = Config.readInputStream(inputstream);
/* 47 */         inputstream.close();
/* 48 */         String[] astring = Config.tokenize(s, "\n\r");
/*    */         
/* 50 */         if (astring.length >= 1) {
/*    */           
/* 52 */           String s1 = astring[0].trim();
/* 53 */           Config.dbg("Version found: " + s1);
/*    */           
/* 55 */           if (Config.compareRelease(s1, "M6_pre1") <= 0) {
/*    */             return;
/*    */           }
/*    */ 
/*    */           
/* 60 */           Config.setNewRelease(s1);
/*    */ 
/*    */           
/*    */           return;
/*    */         } 
/*    */       } finally {
/* 66 */         if (httpurlconnection != null)
/*    */         {
/* 68 */           httpurlconnection.disconnect();
/*    */         }
/*    */       }
/*    */     
/* 72 */     } catch (Exception exception) {
/*    */       
/* 74 */       Config.dbg(exception.getClass().getName() + ": " + exception.getMessage());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\VersionCheckThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */