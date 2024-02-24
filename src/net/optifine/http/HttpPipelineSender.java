/*    */ package net.optifine.http;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.net.Proxy;
/*    */ import java.net.Socket;
/*    */ import java.nio.charset.Charset;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class HttpPipelineSender
/*    */   extends Thread {
/* 14 */   private HttpPipelineConnection httpPipelineConnection = null;
/*    */   private static final String CRLF = "\r\n";
/* 16 */   private static final Charset ASCII = StandardCharsets.US_ASCII;
/*    */ 
/*    */   
/*    */   public HttpPipelineSender(HttpPipelineConnection httpPipelineConnection) {
/* 20 */     super("HttpPipelineSender");
/* 21 */     this.httpPipelineConnection = httpPipelineConnection;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 26 */     HttpPipelineRequest httppipelinerequest = null;
/*    */ 
/*    */     
/*    */     try {
/* 30 */       connect();
/*    */       
/* 32 */       while (!Thread.interrupted())
/*    */       {
/* 34 */         httppipelinerequest = this.httpPipelineConnection.getNextRequestSend();
/* 35 */         HttpRequest httprequest = httppipelinerequest.getHttpRequest();
/* 36 */         OutputStream outputstream = this.httpPipelineConnection.getOutputStream();
/* 37 */         writeRequest(httprequest, outputstream);
/* 38 */         this.httpPipelineConnection.onRequestSent(httppipelinerequest);
/*    */       }
/*    */     
/* 41 */     } catch (InterruptedException var4) {
/*    */       
/*    */       return;
/*    */     }
/* 45 */     catch (Exception exception) {
/*    */       
/* 47 */       this.httpPipelineConnection.onExceptionSend(httppipelinerequest, exception);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void connect() throws IOException {
/* 53 */     String s = this.httpPipelineConnection.getHost();
/* 54 */     int i = this.httpPipelineConnection.getPort();
/* 55 */     Proxy proxy = this.httpPipelineConnection.getProxy();
/* 56 */     Socket socket = new Socket(proxy);
/* 57 */     socket.connect(new InetSocketAddress(s, i), 5000);
/* 58 */     this.httpPipelineConnection.setSocket(socket);
/*    */   }
/*    */ 
/*    */   
/*    */   private void writeRequest(HttpRequest req, OutputStream out) throws IOException {
/* 63 */     write(out, req.getMethod() + " " + req.getFile() + " " + req.getHttp() + "\r\n");
/* 64 */     Map<String, String> map = req.getHeaders();
/*    */     
/* 66 */     for (String s : map.keySet()) {
/*    */       
/* 68 */       String s1 = req.getHeaders().get(s);
/* 69 */       write(out, s + ": " + s1 + "\r\n");
/*    */     } 
/*    */     
/* 72 */     write(out, "\r\n");
/*    */   }
/*    */ 
/*    */   
/*    */   private void write(OutputStream out, String str) throws IOException {
/* 77 */     byte[] abyte = str.getBytes(ASCII);
/* 78 */     out.write(abyte);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\http\HttpPipelineSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */