/*    */ package net.optifine.http;
/*    */ 
/*    */ import java.net.Proxy;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class HttpRequest
/*    */ {
/*  9 */   private String host = null;
/* 10 */   private int port = 0;
/* 11 */   private Proxy proxy = Proxy.NO_PROXY;
/* 12 */   private String method = null;
/* 13 */   private String file = null;
/* 14 */   private String http = null;
/* 15 */   private Map<String, String> headers = new LinkedHashMap<>();
/* 16 */   private byte[] body = null;
/* 17 */   private int redirects = 0;
/*    */   
/*    */   public static final String METHOD_GET = "GET";
/*    */   public static final String METHOD_HEAD = "HEAD";
/*    */   public static final String METHOD_POST = "POST";
/*    */   public static final String HTTP_1_0 = "HTTP/1.0";
/*    */   public static final String HTTP_1_1 = "HTTP/1.1";
/*    */   
/*    */   public HttpRequest(String host, int port, Proxy proxy, String method, String file, String http, Map<String, String> headers, byte[] body) {
/* 26 */     this.host = host;
/* 27 */     this.port = port;
/* 28 */     this.proxy = proxy;
/* 29 */     this.method = method;
/* 30 */     this.file = file;
/* 31 */     this.http = http;
/* 32 */     this.headers = headers;
/* 33 */     this.body = body;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHost() {
/* 38 */     return this.host;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPort() {
/* 43 */     return this.port;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMethod() {
/* 48 */     return this.method;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getFile() {
/* 53 */     return this.file;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHttp() {
/* 58 */     return this.http;
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, String> getHeaders() {
/* 63 */     return this.headers;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] getBody() {
/* 68 */     return this.body;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRedirects() {
/* 73 */     return this.redirects;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRedirects(int redirects) {
/* 78 */     this.redirects = redirects;
/*    */   }
/*    */ 
/*    */   
/*    */   public Proxy getProxy() {
/* 83 */     return this.proxy;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\http\HttpRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */