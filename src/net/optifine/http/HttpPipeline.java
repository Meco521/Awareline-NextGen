/*     */ package net.optifine.http;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ 
/*     */ public class HttpPipeline
/*     */ {
/*  16 */   private static final Map mapConnections = new HashMap<>();
/*     */   
/*     */   public static final String HEADER_USER_AGENT = "User-Agent";
/*     */   public static final String HEADER_HOST = "Host";
/*     */   public static final String HEADER_ACCEPT = "Accept";
/*     */   public static final String HEADER_LOCATION = "Location";
/*     */   public static final String HEADER_KEEP_ALIVE = "Keep-Alive";
/*     */   public static final String HEADER_CONNECTION = "Connection";
/*     */   public static final String HEADER_VALUE_KEEP_ALIVE = "keep-alive";
/*     */   public static final String HEADER_TRANSFER_ENCODING = "Transfer-Encoding";
/*     */   public static final String HEADER_VALUE_CHUNKED = "chunked";
/*     */   
/*     */   public static void addRequest(String urlStr, HttpListener listener) throws IOException {
/*  29 */     addRequest(urlStr, listener, Proxy.NO_PROXY);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRequest(String urlStr, HttpListener listener, Proxy proxy) throws IOException {
/*  34 */     HttpRequest httprequest = makeRequest(urlStr, proxy);
/*  35 */     HttpPipelineRequest httppipelinerequest = new HttpPipelineRequest(httprequest, listener);
/*  36 */     addRequest(httppipelinerequest);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HttpRequest makeRequest(String urlStr, Proxy proxy) throws IOException {
/*  41 */     URL url = new URL(urlStr);
/*     */     
/*  43 */     if (!url.getProtocol().equals("http"))
/*     */     {
/*  45 */       throw new IOException("Only protocol http is supported: " + url);
/*     */     }
/*     */ 
/*     */     
/*  49 */     String s = url.getFile();
/*  50 */     String s1 = url.getHost();
/*  51 */     int i = url.getPort();
/*     */     
/*  53 */     if (i <= 0)
/*     */     {
/*  55 */       i = 80;
/*     */     }
/*     */     
/*  58 */     String s2 = "GET";
/*  59 */     String s3 = "HTTP/1.1";
/*  60 */     Map<String, String> map = new LinkedHashMap<>();
/*  61 */     map.put("User-Agent", "Java/" + System.getProperty("java.version"));
/*  62 */     map.put("Host", s1);
/*  63 */     map.put("Accept", "text/html, image/gif, image/png");
/*  64 */     map.put("Connection", "keep-alive");
/*  65 */     byte[] abyte = new byte[0];
/*  66 */     HttpRequest httprequest = new HttpRequest(s1, i, proxy, s2, s, s3, map, abyte);
/*  67 */     return httprequest;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addRequest(HttpPipelineRequest pr) {
/*  73 */     HttpRequest httprequest = pr.getHttpRequest();
/*     */     
/*  75 */     for (HttpPipelineConnection httppipelineconnection = getConnection(httprequest.getHost(), httprequest.getPort(), httprequest.getProxy()); !httppipelineconnection.addRequest(pr); httppipelineconnection = getConnection(httprequest.getHost(), httprequest.getPort(), httprequest.getProxy()))
/*     */     {
/*  77 */       removeConnection(httprequest.getHost(), httprequest.getPort(), httprequest.getProxy(), httppipelineconnection);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static synchronized HttpPipelineConnection getConnection(String host, int port, Proxy proxy) {
/*  83 */     String s = makeConnectionKey(host, port, proxy);
/*  84 */     HttpPipelineConnection httppipelineconnection = (HttpPipelineConnection)mapConnections.get(s);
/*     */     
/*  86 */     if (httppipelineconnection == null) {
/*     */       
/*  88 */       httppipelineconnection = new HttpPipelineConnection(host, port, proxy);
/*  89 */       mapConnections.put(s, httppipelineconnection);
/*     */     } 
/*     */     
/*  92 */     return httppipelineconnection;
/*     */   }
/*     */ 
/*     */   
/*     */   private static synchronized void removeConnection(String host, int port, Proxy proxy, HttpPipelineConnection hpc) {
/*  97 */     String s = makeConnectionKey(host, port, proxy);
/*  98 */     HttpPipelineConnection httppipelineconnection = (HttpPipelineConnection)mapConnections.get(s);
/*     */     
/* 100 */     if (httppipelineconnection == hpc)
/*     */     {
/* 102 */       mapConnections.remove(s);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static String makeConnectionKey(String host, int port, Proxy proxy) {
/* 108 */     String s = host + ":" + port + "-" + proxy;
/* 109 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] get(String urlStr) throws IOException {
/* 114 */     return get(urlStr, Proxy.NO_PROXY);
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] get(String urlStr, Proxy proxy) throws IOException {
/* 119 */     if (urlStr.startsWith("file:")) {
/*     */       
/* 121 */       URL url = new URL(urlStr);
/* 122 */       InputStream inputstream = url.openStream();
/* 123 */       byte[] abyte = Config.readAll(inputstream);
/* 124 */       return abyte;
/*     */     } 
/*     */ 
/*     */     
/* 128 */     HttpRequest httprequest = makeRequest(urlStr, proxy);
/* 129 */     HttpResponse httpresponse = executeRequest(httprequest);
/*     */     
/* 131 */     if (httpresponse.getStatus() / 100 != 2)
/*     */     {
/* 133 */       throw new IOException("HTTP response: " + httpresponse.getStatus());
/*     */     }
/*     */ 
/*     */     
/* 137 */     return httpresponse.getBody();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HttpResponse executeRequest(HttpRequest req) throws IOException {
/* 144 */     final Map<String, Object> map = new HashMap<>();
/* 145 */     String s = "Response";
/* 146 */     String s1 = "Exception";
/* 147 */     HttpListener httplistener = new HttpListener()
/*     */       {
/*     */         public void finished(HttpRequest req, HttpResponse resp)
/*     */         {
/* 151 */           synchronized (map) {
/*     */             
/* 153 */             map.put("Response", resp);
/* 154 */             map.notifyAll();
/*     */           } 
/*     */         }
/*     */         
/*     */         public void failed(HttpRequest req, Exception e) {
/* 159 */           synchronized (map) {
/*     */             
/* 161 */             map.put("Exception", e);
/* 162 */             map.notifyAll();
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 167 */     synchronized (map) {
/*     */       
/* 169 */       HttpPipelineRequest httppipelinerequest = new HttpPipelineRequest(req, httplistener);
/* 170 */       addRequest(httppipelinerequest);
/*     */ 
/*     */       
/*     */       try {
/* 174 */         map.wait();
/*     */       }
/* 176 */       catch (InterruptedException var10) {
/*     */         
/* 178 */         throw new InterruptedIOException("Interrupted");
/*     */       } 
/*     */       
/* 181 */       Exception exception = (Exception)map.get("Exception");
/*     */       
/* 183 */       if (exception != null) {
/*     */         
/* 185 */         if (exception instanceof IOException)
/*     */         {
/* 187 */           throw (IOException)exception;
/*     */         }
/* 189 */         if (exception instanceof RuntimeException)
/*     */         {
/* 191 */           throw (RuntimeException)exception;
/*     */         }
/*     */ 
/*     */         
/* 195 */         throw new RuntimeException(exception.getMessage(), exception);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 200 */       HttpResponse httpresponse = (HttpResponse)map.get("Response");
/*     */       
/* 202 */       if (httpresponse == null)
/*     */       {
/* 204 */         throw new IOException("Response is null");
/*     */       }
/*     */ 
/*     */       
/* 208 */       return httpresponse;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasActiveRequests() {
/* 216 */     for (Object e : mapConnections.values()) {
/*     */       
/* 218 */       HttpPipelineConnection httppipelineconnection = (HttpPipelineConnection)e;
/* 219 */       if (httppipelineconnection.hasActiveRequests())
/*     */       {
/* 221 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 225 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\http\HttpPipeline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */