/*     */ package net.optifine.http;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.Proxy;
/*     */ import java.net.Socket;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpPipelineConnection
/*     */ {
/*     */   private String host;
/*     */   private int port;
/*     */   private Proxy proxy;
/*     */   private final List<HttpPipelineRequest> listRequests;
/*     */   private final List<HttpPipelineRequest> listRequestsSend;
/*     */   private Socket socket;
/*     */   private InputStream inputStream;
/*     */   private OutputStream outputStream;
/*     */   private HttpPipelineSender httpPipelineSender;
/*  33 */   private static final Pattern patternFullUrl = Pattern.compile("^[a-zA-Z]+://.*"); private HttpPipelineReceiver httpPipelineReceiver; private int countRequests; private boolean responseReceived; private long keepaliveTimeoutMs; private int keepaliveMaxCount; private long timeLastActivityMs; private boolean terminated; private static final String LF = "\n"; public static final int TIMEOUT_CONNECT_MS = 5000;
/*     */   public static final int TIMEOUT_READ_MS = 5000;
/*     */   
/*     */   public HttpPipelineConnection(String host, int port) {
/*  37 */     this(host, port, Proxy.NO_PROXY);
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpPipelineConnection(String host, int port, Proxy proxy) {
/*  42 */     this.host = null;
/*  43 */     this.port = 0;
/*  44 */     this.proxy = Proxy.NO_PROXY;
/*  45 */     this.listRequests = new LinkedList<>();
/*  46 */     this.listRequestsSend = new LinkedList<>();
/*  47 */     this.socket = null;
/*  48 */     this.inputStream = null;
/*  49 */     this.outputStream = null;
/*  50 */     this.httpPipelineSender = null;
/*  51 */     this.httpPipelineReceiver = null;
/*  52 */     this.countRequests = 0;
/*  53 */     this.responseReceived = false;
/*  54 */     this.keepaliveTimeoutMs = 5000L;
/*  55 */     this.keepaliveMaxCount = 1000;
/*  56 */     this.timeLastActivityMs = System.currentTimeMillis();
/*  57 */     this.terminated = false;
/*  58 */     this.host = host;
/*  59 */     this.port = port;
/*  60 */     this.proxy = proxy;
/*  61 */     this.httpPipelineSender = new HttpPipelineSender(this);
/*  62 */     this.httpPipelineSender.start();
/*  63 */     this.httpPipelineReceiver = new HttpPipelineReceiver(this);
/*  64 */     this.httpPipelineReceiver.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean addRequest(HttpPipelineRequest pr) {
/*  69 */     if (isClosed())
/*     */     {
/*  71 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  75 */     addRequest(pr, this.listRequests);
/*  76 */     addRequest(pr, this.listRequestsSend);
/*  77 */     this.countRequests++;
/*  78 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addRequest(HttpPipelineRequest pr, List<HttpPipelineRequest> list) {
/*  84 */     list.add(pr);
/*  85 */     notifyAll();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void setSocket(Socket s) throws IOException {
/*  90 */     if (!this.terminated) {
/*     */       
/*  92 */       if (this.socket != null)
/*     */       {
/*  94 */         throw new IllegalArgumentException("Already connected");
/*     */       }
/*     */ 
/*     */       
/*  98 */       this.socket = s;
/*  99 */       this.socket.setTcpNoDelay(true);
/* 100 */       this.inputStream = this.socket.getInputStream();
/* 101 */       this.outputStream = new BufferedOutputStream(this.socket.getOutputStream());
/* 102 */       onActivity();
/* 103 */       notifyAll();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized OutputStream getOutputStream() throws InterruptedException {
/* 110 */     while (this.outputStream == null) {
/*     */       
/* 112 */       checkTimeout();
/* 113 */       wait(1000L);
/*     */     } 
/*     */     
/* 116 */     return this.outputStream;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized InputStream getInputStream() throws InterruptedException {
/* 121 */     while (this.inputStream == null) {
/*     */       
/* 123 */       checkTimeout();
/* 124 */       wait(1000L);
/*     */     } 
/*     */     
/* 127 */     return this.inputStream;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized HttpPipelineRequest getNextRequestSend() throws InterruptedException, IOException {
/* 132 */     if (this.listRequestsSend.size() <= 0 && this.outputStream != null)
/*     */     {
/* 134 */       this.outputStream.flush();
/*     */     }
/*     */     
/* 137 */     return getNextRequest(this.listRequestsSend, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized HttpPipelineRequest getNextRequestReceive() throws InterruptedException {
/* 142 */     return getNextRequest(this.listRequests, false);
/*     */   }
/*     */ 
/*     */   
/*     */   private HttpPipelineRequest getNextRequest(List<HttpPipelineRequest> list, boolean remove) throws InterruptedException {
/* 147 */     while (list.size() <= 0) {
/*     */       
/* 149 */       checkTimeout();
/* 150 */       wait(1000L);
/*     */     } 
/*     */     
/* 153 */     onActivity();
/*     */     
/* 155 */     if (remove)
/*     */     {
/* 157 */       return list.remove(0);
/*     */     }
/*     */ 
/*     */     
/* 161 */     return list.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkTimeout() {
/* 167 */     if (this.socket != null) {
/*     */       
/* 169 */       long i = this.keepaliveTimeoutMs;
/*     */       
/* 171 */       if (!this.listRequests.isEmpty())
/*     */       {
/* 173 */         i = 5000L;
/*     */       }
/*     */       
/* 176 */       long j = System.currentTimeMillis();
/*     */       
/* 178 */       if (j > this.timeLastActivityMs + i)
/*     */       {
/* 180 */         terminate(new InterruptedException("Timeout " + i));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void onActivity() {
/* 187 */     this.timeLastActivityMs = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void onRequestSent(HttpPipelineRequest pr) {
/* 192 */     if (!this.terminated)
/*     */     {
/* 194 */       onActivity();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void onResponseReceived(HttpPipelineRequest pr, HttpResponse resp) {
/* 200 */     if (!this.terminated) {
/*     */       
/* 202 */       this.responseReceived = true;
/* 203 */       onActivity();
/*     */       
/* 205 */       if (!this.listRequests.isEmpty() && this.listRequests.get(0) == pr) {
/*     */         
/* 207 */         this.listRequests.remove(0);
/* 208 */         pr.setClosed(true);
/* 209 */         String s = resp.getHeader("Location");
/*     */         
/* 211 */         if (resp.getStatus() / 100 == 3 && s != null && pr.getHttpRequest().getRedirects() < 5) {
/*     */           
/*     */           try
/*     */           {
/* 215 */             s = normalizeUrl(s, pr.getHttpRequest());
/* 216 */             HttpRequest httprequest = HttpPipeline.makeRequest(s, pr.getHttpRequest().getProxy());
/* 217 */             httprequest.setRedirects(pr.getHttpRequest().getRedirects() + 1);
/* 218 */             HttpPipelineRequest httppipelinerequest = new HttpPipelineRequest(httprequest, pr.getHttpListener());
/* 219 */             HttpPipeline.addRequest(httppipelinerequest);
/*     */           }
/* 221 */           catch (IOException ioexception)
/*     */           {
/* 223 */             pr.getHttpListener().failed(pr.getHttpRequest(), ioexception);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 228 */           HttpListener httplistener = pr.getHttpListener();
/* 229 */           httplistener.finished(pr.getHttpRequest(), resp);
/*     */         } 
/*     */         
/* 232 */         checkResponseHeader(resp);
/*     */       }
/*     */       else {
/*     */         
/* 236 */         throw new IllegalArgumentException("Response out of order: " + pr);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String normalizeUrl(String url, HttpRequest hr) {
/* 243 */     if (patternFullUrl.matcher(url).matches())
/*     */     {
/* 245 */       return url;
/*     */     }
/* 247 */     if (url.startsWith("//"))
/*     */     {
/* 249 */       return "http:" + url;
/*     */     }
/*     */ 
/*     */     
/* 253 */     String s = hr.getHost();
/*     */     
/* 255 */     if (hr.getPort() != 80)
/*     */     {
/* 257 */       s = s + ":" + hr.getPort();
/*     */     }
/*     */     
/* 260 */     if (!url.isEmpty() && url.charAt(0) == '/')
/*     */     {
/* 262 */       return "http://" + s + url;
/*     */     }
/*     */ 
/*     */     
/* 266 */     String s1 = hr.getFile();
/* 267 */     int i = s1.lastIndexOf('/');
/* 268 */     return (i >= 0) ? ("http://" + s + s1.substring(0, i + 1) + url) : ("http://" + s + "/" + url);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkResponseHeader(HttpResponse resp) {
/* 275 */     String s = resp.getHeader("Connection");
/*     */     
/* 277 */     if (s != null && !s.toLowerCase().equals("keep-alive"))
/*     */     {
/* 279 */       terminate(new EOFException("Connection not keep-alive"));
/*     */     }
/*     */     
/* 282 */     String s1 = resp.getHeader("Keep-Alive");
/*     */     
/* 284 */     if (s1 != null) {
/*     */       
/* 286 */       String[] astring = Config.tokenize(s1, ",;");
/*     */       
/* 288 */       for (int i = 0; i < astring.length; i++) {
/*     */         
/* 290 */         String s2 = astring[i];
/* 291 */         String[] astring1 = split(s2, '=');
/*     */         
/* 293 */         if (astring1.length >= 2) {
/*     */           
/* 295 */           if (astring1[0].equals("timeout")) {
/*     */             
/* 297 */             int j = Config.parseInt(astring1[1], -1);
/*     */             
/* 299 */             if (j > 0)
/*     */             {
/* 301 */               this.keepaliveTimeoutMs = (j * 1000);
/*     */             }
/*     */           } 
/*     */           
/* 305 */           if (astring1[0].equals("max")) {
/*     */             
/* 307 */             int k = Config.parseInt(astring1[1], -1);
/*     */             
/* 309 */             if (k > 0)
/*     */             {
/* 311 */               this.keepaliveMaxCount = k;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] split(String str, char separator) {
/* 321 */     int i = str.indexOf(separator);
/*     */     
/* 323 */     if (i < 0)
/*     */     {
/* 325 */       return new String[] { str };
/*     */     }
/*     */ 
/*     */     
/* 329 */     String s = str.substring(0, i);
/* 330 */     String s1 = str.substring(i + 1);
/* 331 */     return new String[] { s, s1 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void onExceptionSend(HttpPipelineRequest pr, Exception e) {
/* 337 */     terminate(e);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void onExceptionReceive(HttpPipelineRequest pr, Exception e) {
/* 342 */     terminate(e);
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void terminate(Exception e) {
/* 347 */     if (!this.terminated) {
/*     */       
/* 349 */       this.terminated = true;
/* 350 */       terminateRequests(e);
/*     */       
/* 352 */       if (this.httpPipelineSender != null)
/*     */       {
/* 354 */         this.httpPipelineSender.interrupt();
/*     */       }
/*     */       
/* 357 */       if (this.httpPipelineReceiver != null)
/*     */       {
/* 359 */         this.httpPipelineReceiver.interrupt();
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 364 */         if (this.socket != null)
/*     */         {
/* 366 */           this.socket.close();
/*     */         }
/*     */       }
/* 369 */       catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 374 */       this.socket = null;
/* 375 */       this.inputStream = null;
/* 376 */       this.outputStream = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void terminateRequests(Exception e) {
/* 382 */     if (!this.listRequests.isEmpty()) {
/*     */       
/* 384 */       if (!this.responseReceived) {
/*     */         
/* 386 */         HttpPipelineRequest httppipelinerequest = this.listRequests.remove(0);
/* 387 */         httppipelinerequest.getHttpListener().failed(httppipelinerequest.getHttpRequest(), e);
/* 388 */         httppipelinerequest.setClosed(true);
/*     */       } 
/*     */       
/* 391 */       while (!this.listRequests.isEmpty()) {
/*     */         
/* 393 */         HttpPipelineRequest httppipelinerequest1 = this.listRequests.remove(0);
/* 394 */         HttpPipeline.addRequest(httppipelinerequest1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean isClosed() {
/* 401 */     return this.terminated ? true : ((this.countRequests >= this.keepaliveMaxCount));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCountRequests() {
/* 406 */     return this.countRequests;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean hasActiveRequests() {
/* 411 */     return !this.listRequests.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHost() {
/* 416 */     return this.host;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPort() {
/* 421 */     return this.port;
/*     */   }
/*     */ 
/*     */   
/*     */   public Proxy getProxy() {
/* 426 */     return this.proxy;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\http\HttpPipelineConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */