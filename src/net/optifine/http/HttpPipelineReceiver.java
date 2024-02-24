/*     */ package net.optifine.http;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ public class HttpPipelineReceiver
/*     */   extends Thread
/*     */ {
/*  16 */   private HttpPipelineConnection httpPipelineConnection = null;
/*  17 */   private static final Charset ASCII = StandardCharsets.US_ASCII;
/*     */   
/*     */   private static final String HEADER_CONTENT_LENGTH = "Content-Length";
/*     */   private static final char CR = '\r';
/*     */   private static final char LF = '\n';
/*     */   
/*     */   public HttpPipelineReceiver(HttpPipelineConnection httpPipelineConnection) {
/*  24 */     super("HttpPipelineReceiver");
/*  25 */     this.httpPipelineConnection = httpPipelineConnection;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*  30 */     while (!Thread.interrupted()) {
/*     */       
/*  32 */       HttpPipelineRequest httppipelinerequest = null;
/*     */ 
/*     */       
/*     */       try {
/*  36 */         httppipelinerequest = this.httpPipelineConnection.getNextRequestReceive();
/*  37 */         InputStream inputstream = this.httpPipelineConnection.getInputStream();
/*  38 */         HttpResponse httpresponse = readResponse(inputstream);
/*  39 */         this.httpPipelineConnection.onResponseReceived(httppipelinerequest, httpresponse);
/*     */       }
/*  41 */       catch (InterruptedException var4) {
/*     */         
/*     */         return;
/*     */       }
/*  45 */       catch (Exception exception) {
/*     */         
/*  47 */         this.httpPipelineConnection.onExceptionReceive(httppipelinerequest, exception);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private HttpResponse readResponse(InputStream in) throws IOException {
/*  54 */     String s = readLine(in);
/*  55 */     String[] astring = Config.tokenize(s, " ");
/*     */     
/*  57 */     if (astring.length < 3)
/*     */     {
/*  59 */       throw new IOException("Invalid status line: " + s);
/*     */     }
/*     */ 
/*     */     
/*  63 */     String s1 = astring[0];
/*  64 */     int i = Config.parseInt(astring[1], 0);
/*  65 */     String s2 = astring[2];
/*  66 */     Map<String, String> map = new LinkedHashMap<>();
/*     */ 
/*     */     
/*     */     while (true) {
/*  70 */       String s3 = readLine(in);
/*     */       
/*  72 */       if (s3.length() <= 0) {
/*     */         
/*  74 */         byte[] abyte = null;
/*  75 */         String s6 = map.get("Content-Length");
/*     */         
/*  77 */         if (s6 != null) {
/*     */           
/*  79 */           int k = Config.parseInt(s6, -1);
/*     */           
/*  81 */           if (k > 0)
/*     */           {
/*  83 */             abyte = new byte[k];
/*  84 */             readFull(abyte, in);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/*  89 */           String s7 = map.get("Transfer-Encoding");
/*     */           
/*  91 */           if (Config.equals(s7, "chunked"))
/*     */           {
/*  93 */             abyte = readContentChunked(in);
/*     */           }
/*     */         } 
/*     */         
/*  97 */         return new HttpResponse(i, s, map, abyte);
/*     */       } 
/*     */       
/* 100 */       int j = s3.indexOf(':');
/*     */       
/* 102 */       if (j > 0) {
/*     */         
/* 104 */         String s4 = s3.substring(0, j).trim();
/* 105 */         String s5 = s3.substring(j + 1).trim();
/* 106 */         map.put(s4, s5);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] readContentChunked(InputStream in) throws IOException {
/*     */     int i;
/* 114 */     ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
/*     */ 
/*     */     
/*     */     do {
/* 118 */       String s = readLine(in);
/* 119 */       String[] astring = Config.tokenize(s, "; ");
/* 120 */       i = Integer.parseInt(astring[0], 16);
/* 121 */       byte[] abyte = new byte[i];
/* 122 */       readFull(abyte, in);
/* 123 */       bytearrayoutputstream.write(abyte);
/* 124 */       readLine(in);
/*     */     }
/* 126 */     while (i != 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     return bytearrayoutputstream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readFull(byte[] buf, InputStream in) throws IOException {
/*     */     int i;
/* 139 */     for (i = 0; i < buf.length; i += j) {
/*     */       
/* 141 */       int j = in.read(buf, i, buf.length - i);
/*     */       
/* 143 */       if (j < 0)
/*     */       {
/* 145 */         throw new EOFException();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String readLine(InputStream in) throws IOException {
/* 152 */     ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
/* 153 */     int i = -1;
/* 154 */     boolean flag = false;
/*     */ 
/*     */     
/*     */     while (true) {
/* 158 */       int j = in.read();
/*     */       
/* 160 */       if (j < 0) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 165 */       bytearrayoutputstream.write(j);
/*     */       
/* 167 */       if (i == 13 && j == 10) {
/*     */         
/* 169 */         flag = true;
/*     */         
/*     */         break;
/*     */       } 
/* 173 */       i = j;
/*     */     } 
/*     */     
/* 176 */     byte[] abyte = bytearrayoutputstream.toByteArray();
/* 177 */     String s = new String(abyte, ASCII);
/*     */     
/* 179 */     if (flag)
/*     */     {
/* 181 */       s = s.substring(0, s.length() - 2);
/*     */     }
/*     */     
/* 184 */     return s;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\http\HttpPipelineReceiver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */