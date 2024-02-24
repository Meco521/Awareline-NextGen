/*    */ package net.optifine.http;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public class FileDownloadThread
/*    */   extends Thread {
/*  7 */   private String urlString = null;
/*  8 */   private IFileDownloadListener listener = null;
/*    */ 
/*    */   
/*    */   public FileDownloadThread(String urlString, IFileDownloadListener listener) {
/* 12 */     this.urlString = urlString;
/* 13 */     this.listener = listener;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 20 */       byte[] abyte = HttpPipeline.get(this.urlString, Minecraft.getMinecraft().getProxy());
/* 21 */       this.listener.fileDownloadFinished(this.urlString, abyte, (Throwable)null);
/*    */     }
/* 23 */     catch (Exception exception) {
/*    */       
/* 25 */       this.listener.fileDownloadFinished(this.urlString, (byte[])null, exception);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUrlString() {
/* 31 */     return this.urlString;
/*    */   }
/*    */ 
/*    */   
/*    */   public IFileDownloadListener getListener() {
/* 36 */     return this.listener;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\http\FileDownloadThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */