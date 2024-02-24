/*     */ package net.minecraft.util;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.Proxy;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.URL;
/*     */ import java.nio.file.Files;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ public class HttpUtil {
/*  23 */   public static final ListeningExecutorService field_180193_a = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool((new ThreadFactoryBuilder()).setDaemon(true).setNameFormat("Downloader %d").build()));
/*     */ 
/*     */   
/*  26 */   private static final AtomicInteger downloadThreadsStarted = new AtomicInteger(0);
/*  27 */   static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String buildPostString(Map<String, Object> data) {
/*  34 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/*  36 */     for (Map.Entry<String, Object> entry : data.entrySet()) {
/*     */       
/*  38 */       if (stringbuilder.length() > 0)
/*     */       {
/*  40 */         stringbuilder.append('&');
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/*  45 */         stringbuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
/*     */       }
/*  47 */       catch (UnsupportedEncodingException unsupportedencodingexception1) {
/*     */         
/*  49 */         unsupportedencodingexception1.printStackTrace();
/*     */       } 
/*     */       
/*  52 */       if (entry.getValue() != null) {
/*     */         
/*  54 */         stringbuilder.append('=');
/*     */ 
/*     */         
/*     */         try {
/*  58 */           stringbuilder.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
/*     */         }
/*  60 */         catch (UnsupportedEncodingException unsupportedencodingexception) {
/*     */           
/*  62 */           unsupportedencodingexception.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  67 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String postMap(URL url, Map<String, Object> data, boolean skipLoggingErrors) {
/*  75 */     return post(url, buildPostString(data), skipLoggingErrors);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String post(URL url, String content, boolean skipLoggingErrors) {
/*     */     try {
/*  85 */       Proxy proxy = (MinecraftServer.getServer() == null) ? null : MinecraftServer.getServer().getServerProxy();
/*     */       
/*  87 */       if (proxy == null)
/*     */       {
/*  89 */         proxy = Proxy.NO_PROXY;
/*     */       }
/*     */       
/*  92 */       HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection(proxy);
/*  93 */       httpurlconnection.setRequestMethod("POST");
/*  94 */       httpurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
/*  95 */       httpurlconnection.setRequestProperty("Content-Length", String.valueOf((content.getBytes()).length));
/*  96 */       httpurlconnection.setRequestProperty("Content-Language", "en-US");
/*  97 */       httpurlconnection.setUseCaches(false);
/*  98 */       httpurlconnection.setDoInput(true);
/*  99 */       httpurlconnection.setDoOutput(true);
/* 100 */       DataOutputStream dataoutputstream = new DataOutputStream(httpurlconnection.getOutputStream());
/* 101 */       dataoutputstream.writeBytes(content);
/* 102 */       dataoutputstream.flush();
/* 103 */       dataoutputstream.close();
/* 104 */       BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
/* 105 */       StringBuilder stringbuffer = new StringBuilder();
/*     */       
/*     */       String s;
/* 108 */       while ((s = bufferedreader.readLine()) != null) {
/*     */         
/* 110 */         stringbuffer.append(s);
/* 111 */         stringbuffer.append('\r');
/*     */       } 
/*     */       
/* 114 */       bufferedreader.close();
/* 115 */       return stringbuffer.toString();
/*     */     }
/* 117 */     catch (Exception exception) {
/*     */       
/* 119 */       if (!skipLoggingErrors)
/*     */       {
/* 121 */         logger.error("Could not post to " + url, exception);
/*     */       }
/*     */       
/* 124 */       return "";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ListenableFuture<Object> downloadResourcePack(final File saveFile, final String packUrl, final Map<String, String> p_180192_2_, final int maxSize, final IProgressUpdate p_180192_4_, final Proxy p_180192_5_) {
/* 130 */     ListenableFuture<?> listenablefuture = field_180193_a.submit(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 134 */             HttpURLConnection httpurlconnection = null;
/* 135 */             InputStream inputstream = null;
/* 136 */             OutputStream outputstream = null;
/*     */             
/* 138 */             if (p_180192_4_ != null) {
/*     */               
/* 140 */               p_180192_4_.resetProgressAndMessage("Downloading Resource Pack");
/* 141 */               p_180192_4_.displayLoadingString("Making Request...");
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/* 148 */               byte[] abyte = new byte[4096];
/* 149 */               URL url = new URL(packUrl);
/* 150 */               httpurlconnection = (HttpURLConnection)url.openConnection(p_180192_5_);
/* 151 */               float f = 0.0F;
/* 152 */               float f1 = p_180192_2_.entrySet().size();
/*     */               
/* 154 */               for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>)p_180192_2_.entrySet()) {
/*     */                 
/* 156 */                 httpurlconnection.setRequestProperty(entry.getKey(), entry.getValue());
/*     */                 
/* 158 */                 if (p_180192_4_ != null)
/*     */                 {
/* 160 */                   p_180192_4_.setLoadingProgress((int)(++f / f1 * 100.0F));
/*     */                 }
/*     */               } 
/*     */               
/* 164 */               inputstream = httpurlconnection.getInputStream();
/* 165 */               f1 = httpurlconnection.getContentLength();
/* 166 */               int i = httpurlconnection.getContentLength();
/*     */               
/* 168 */               if (p_180192_4_ != null)
/*     */               {
/* 170 */                 p_180192_4_.displayLoadingString(String.format("Downloading file (%.2f MB)...", new Object[] { Float.valueOf(f1 / 1000.0F / 1000.0F) }));
/*     */               }
/*     */               
/* 173 */               if (saveFile.exists()) {
/*     */                 
/* 175 */                 long j = saveFile.length();
/*     */                 
/* 177 */                 if (j == i) {
/*     */                   
/* 179 */                   if (p_180192_4_ != null)
/*     */                   {
/* 181 */                     p_180192_4_.setDoneWorking();
/*     */                   }
/*     */                   
/*     */                   return;
/*     */                 } 
/*     */                 
/* 187 */                 HttpUtil.logger.warn("Deleting " + saveFile + " as it does not match what we currently have (" + i + " vs our " + j + ").");
/* 188 */                 FileUtils.deleteQuietly(saveFile);
/*     */               }
/* 190 */               else if (saveFile.getParentFile() != null) {
/*     */                 
/* 192 */                 saveFile.getParentFile().mkdirs();
/*     */               } 
/*     */               
/* 195 */               outputstream = new DataOutputStream(Files.newOutputStream(saveFile.toPath(), new java.nio.file.OpenOption[0]));
/*     */               
/* 197 */               if (maxSize > 0 && f1 > maxSize) {
/*     */                 
/* 199 */                 if (p_180192_4_ != null)
/*     */                 {
/* 201 */                   p_180192_4_.setDoneWorking();
/*     */                 }
/*     */                 
/* 204 */                 throw new IOException("Filesize is bigger than maximum allowed (file is " + f + ", limit is " + maxSize + ")");
/*     */               } 
/*     */               
/* 207 */               int k = 0;
/*     */               
/* 209 */               while ((k = inputstream.read(abyte)) >= 0) {
/*     */                 
/* 211 */                 f += k;
/*     */                 
/* 213 */                 if (p_180192_4_ != null)
/*     */                 {
/* 215 */                   p_180192_4_.setLoadingProgress((int)(f / f1 * 100.0F));
/*     */                 }
/*     */                 
/* 218 */                 if (maxSize > 0 && f > maxSize) {
/*     */                   
/* 220 */                   if (p_180192_4_ != null)
/*     */                   {
/* 222 */                     p_180192_4_.setDoneWorking();
/*     */                   }
/*     */                   
/* 225 */                   throw new IOException("Filesize was bigger than maximum allowed (got >= " + f + ", limit was " + maxSize + ")");
/*     */                 } 
/*     */                 
/* 228 */                 if (Thread.interrupted()) {
/*     */                   
/* 230 */                   HttpUtil.logger.error("INTERRUPTED");
/*     */                   
/* 232 */                   if (p_180192_4_ != null)
/*     */                   {
/* 234 */                     p_180192_4_.setDoneWorking();
/*     */                   }
/*     */                   
/*     */                   return;
/*     */                 } 
/*     */                 
/* 240 */                 outputstream.write(abyte, 0, k);
/*     */               } 
/*     */               
/* 243 */               if (p_180192_4_ != null) {
/*     */                 
/* 245 */                 p_180192_4_.setDoneWorking();
/*     */                 
/*     */                 return;
/*     */               } 
/* 249 */             } catch (Throwable throwable) {
/*     */               
/* 251 */               throwable.printStackTrace();
/*     */               
/* 253 */               if (httpurlconnection != null) {
/*     */                 
/* 255 */                 InputStream inputstream1 = httpurlconnection.getErrorStream();
/*     */ 
/*     */                 
/*     */                 try {
/* 259 */                   HttpUtil.logger.error(IOUtils.toString(inputstream1));
/*     */                 }
/* 261 */                 catch (IOException ioexception) {
/*     */                   
/* 263 */                   ioexception.printStackTrace();
/*     */                 } 
/*     */               } 
/*     */               
/* 267 */               if (p_180192_4_ != null) {
/*     */                 
/* 269 */                 p_180192_4_.setDoneWorking();
/*     */ 
/*     */ 
/*     */                 
/*     */                 return;
/*     */               } 
/*     */             } finally {
/* 276 */               IOUtils.closeQuietly(inputstream);
/* 277 */               IOUtils.closeQuietly(outputstream);
/*     */             } 
/*     */           }
/*     */         });
/* 281 */     return (ListenableFuture)listenablefuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getSuitableLanPort() throws IOException {
/* 286 */     ServerSocket serversocket = null;
/* 287 */     int i = -1;
/*     */ 
/*     */     
/*     */     try {
/* 291 */       serversocket = new ServerSocket(0);
/* 292 */       i = serversocket.getLocalPort();
/*     */     } finally {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 298 */         if (serversocket != null)
/*     */         {
/* 300 */           serversocket.close();
/*     */         }
/*     */       }
/* 303 */       catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 309 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String get(URL url) throws IOException {
/* 317 */     HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
/* 318 */     httpurlconnection.setRequestMethod("GET");
/* 319 */     BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
/* 320 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/*     */     String s;
/* 323 */     while ((s = bufferedreader.readLine()) != null) {
/*     */       
/* 325 */       stringbuilder.append(s);
/* 326 */       stringbuilder.append('\r');
/*     */     } 
/*     */     
/* 329 */     bufferedreader.close();
/* 330 */     return stringbuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\HttpUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */