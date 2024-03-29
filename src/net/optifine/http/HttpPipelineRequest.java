/*    */ package net.optifine.http;
/*    */ 
/*    */ public class HttpPipelineRequest
/*    */ {
/*  5 */   private HttpRequest httpRequest = null;
/*  6 */   private HttpListener httpListener = null;
/*    */   
/*    */   private boolean closed = false;
/*    */   
/*    */   public HttpPipelineRequest(HttpRequest httpRequest, HttpListener httpListener) {
/* 11 */     this.httpRequest = httpRequest;
/* 12 */     this.httpListener = httpListener;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpRequest getHttpRequest() {
/* 17 */     return this.httpRequest;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpListener getHttpListener() {
/* 22 */     return this.httpListener;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isClosed() {
/* 27 */     return this.closed;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setClosed(boolean closed) {
/* 32 */     this.closed = closed;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\http\HttpPipelineRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */