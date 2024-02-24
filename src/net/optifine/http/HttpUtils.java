/*     */ package net.optifine.http;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ public class HttpUtils {
/*  14 */   private static String playerItemsUrl = null;
/*     */   public static final String SERVER_URL = "http://s.optifine.net";
/*     */   public static final String POST_URL = "http://optifine.net";
/*     */   
/*     */   public static byte[] get(String urlStr) throws IOException {
/*     */     byte[] abyte1;
/*  20 */     HttpURLConnection httpurlconnection = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  25 */       URL url = new URL(urlStr);
/*  26 */       httpurlconnection = (HttpURLConnection)url.openConnection(Minecraft.getMinecraft().getProxy());
/*  27 */       httpurlconnection.setDoInput(true);
/*  28 */       httpurlconnection.setDoOutput(false);
/*  29 */       httpurlconnection.connect();
/*     */       
/*  31 */       if (httpurlconnection.getResponseCode() / 100 != 2) {
/*     */         
/*  33 */         if (httpurlconnection.getErrorStream() != null)
/*     */         {
/*  35 */           Config.readAll(httpurlconnection.getErrorStream());
/*     */         }
/*     */         
/*  38 */         throw new IOException("HTTP response: " + httpurlconnection.getResponseCode());
/*     */       } 
/*     */       
/*  41 */       InputStream inputstream = httpurlconnection.getInputStream();
/*  42 */       byte[] abyte = new byte[httpurlconnection.getContentLength()];
/*  43 */       int i = 0;
/*     */ 
/*     */       
/*     */       do {
/*  47 */         int j = inputstream.read(abyte, i, abyte.length - i);
/*     */         
/*  49 */         if (j < 0)
/*     */         {
/*  51 */           throw new IOException("Input stream closed: " + urlStr);
/*     */         }
/*     */         
/*  54 */         i += j;
/*     */       }
/*  56 */       while (i < abyte.length);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  62 */       abyte1 = abyte;
/*     */     }
/*     */     finally {
/*     */       
/*  66 */       if (httpurlconnection != null)
/*     */       {
/*  68 */         httpurlconnection.disconnect();
/*     */       }
/*     */     } 
/*     */     
/*  72 */     return abyte1;
/*     */   }
/*     */   
/*     */   public static String post(String urlStr, Map headers, byte[] content) throws IOException {
/*     */     String s3;
/*  77 */     HttpURLConnection httpurlconnection = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  82 */       URL url = new URL(urlStr);
/*  83 */       httpurlconnection = (HttpURLConnection)url.openConnection(Minecraft.getMinecraft().getProxy());
/*  84 */       httpurlconnection.setRequestMethod("POST");
/*     */       
/*  86 */       if (headers != null)
/*     */       {
/*  88 */         for (Object s : headers.keySet()) {
/*     */           
/*  90 */           String s1 = String.valueOf(headers.get(s));
/*  91 */           httpurlconnection.setRequestProperty((String)s, s1);
/*     */         } 
/*     */       }
/*     */       
/*  95 */       httpurlconnection.setRequestProperty("Content-Type", "text/plain");
/*  96 */       httpurlconnection.setRequestProperty("Content-Length", String.valueOf(content.length));
/*  97 */       httpurlconnection.setRequestProperty("Content-Language", "en-US");
/*  98 */       httpurlconnection.setUseCaches(false);
/*  99 */       httpurlconnection.setDoInput(true);
/* 100 */       httpurlconnection.setDoOutput(true);
/* 101 */       OutputStream outputstream = httpurlconnection.getOutputStream();
/* 102 */       outputstream.write(content);
/* 103 */       outputstream.flush();
/* 104 */       outputstream.close();
/* 105 */       InputStream inputstream = httpurlconnection.getInputStream();
/* 106 */       InputStreamReader inputstreamreader = new InputStreamReader(inputstream, StandardCharsets.US_ASCII);
/* 107 */       BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
/* 108 */       StringBuffer stringbuffer = new StringBuffer();
/*     */       
/*     */       String s2;
/* 111 */       while ((s2 = bufferedreader.readLine()) != null) {
/*     */         
/* 113 */         stringbuffer.append(s2);
/* 114 */         stringbuffer.append('\r');
/*     */       } 
/*     */       
/* 117 */       bufferedreader.close();
/* 118 */       s3 = stringbuffer.toString();
/*     */     }
/*     */     finally {
/*     */       
/* 122 */       if (httpurlconnection != null)
/*     */       {
/* 124 */         httpurlconnection.disconnect();
/*     */       }
/*     */     } 
/*     */     
/* 128 */     return s3;
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized String getPlayerItemsUrl() {
/* 133 */     if (playerItemsUrl == null) {
/*     */ 
/*     */       
/*     */       try {
/* 137 */         boolean flag = Config.parseBoolean(System.getProperty("player.models.local"), false);
/*     */         
/* 139 */         if (flag)
/*     */         {
/* 141 */           File file1 = (Minecraft.getMinecraft()).mcDataDir;
/* 142 */           File file2 = new File(file1, "playermodels");
/* 143 */           playerItemsUrl = file2.toURI().toURL().toExternalForm();
/*     */         }
/*     */       
/* 146 */       } catch (Exception exception) {
/*     */         
/* 148 */         Config.warn(exception.getClass().getName() + ": " + exception.getMessage());
/*     */       } 
/*     */       
/* 151 */       if (playerItemsUrl == null)
/*     */       {
/* 153 */         playerItemsUrl = "http://s.optifine.net";
/*     */       }
/*     */     } 
/*     */     
/* 157 */     return playerItemsUrl;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\http\HttpUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */