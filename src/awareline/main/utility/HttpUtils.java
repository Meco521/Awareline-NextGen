/*     */ package awareline.main.utility;
/*     */ 
/*     */ import com.allatori.annotations.ControlFlowObfuscation;
/*     */ import com.allatori.annotations.ExtensiveFlowObfuscation;
/*     */ import com.allatori.annotations.StringEncryption;
/*     */ import com.allatori.annotations.StringEncryptionType;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ControlFlowObfuscation("enable")
/*     */ @ExtensiveFlowObfuscation("maximum")
/*     */ @StringEncryption("enable")
/*     */ @StringEncryptionType("fast")
/*     */ public class HttpUtils
/*     */ {
/*     */   public static String get(String url, Map<String, String> params) {
/*  33 */     return get(url, params, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String get(String url, Map<String, String> params, Map<String, String> headers) {
/*  45 */     return request(mapToString(url, params, "?"), null, headers, "GET");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void getAsyn(String url, Map<String, String> params, OnHttpResult onHttpResult) {
/*  57 */     getAsyn(url, params, null, onHttpResult);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void getAsyn(String url, Map<String, String> params, Map<String, String> headers, OnHttpResult onHttpResult) {
/*  70 */     requestAsyn(mapToString(url, params, "?"), null, headers, "GET", onHttpResult);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String post(String url, Map<String, String> params) {
/*  81 */     return post(url, params, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String post(String url, Map<String, String> params, Map<String, String> headers) {
/*  93 */     return request(url, mapToString(null, params, null), headers, "POST");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void postAsyn(String url, Map<String, String> params, OnHttpResult onHttpResult) {
/* 104 */     postAsyn(url, params, null, onHttpResult);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void postAsyn(String url, Map<String, String> params, Map<String, String> headers, OnHttpResult onHttpResult) {
/* 116 */     requestAsyn(url, mapToString(null, params, null), headers, "POST", onHttpResult);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String put(String url, Map<String, String> params) {
/* 127 */     return put(url, params, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String put(String url, Map<String, String> params, Map<String, String> headers) {
/* 139 */     return request(url, mapToString(null, params, null), headers, "PUT");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void putAsyn(String url, Map<String, String> params, OnHttpResult onHttpResult) {
/* 150 */     putAsyn(url, params, null, onHttpResult);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void putAsyn(String url, Map<String, String> params, Map<String, String> headers, OnHttpResult onHttpResult) {
/* 162 */     requestAsyn(url, mapToString(null, params, null), headers, "PUT", onHttpResult);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String delete(String url, Map<String, String> params) {
/* 173 */     return delete(url, params, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String delete(String url, Map<String, String> params, Map<String, String> headers) {
/* 185 */     return request(mapToString(url, params, "?"), null, headers, "DELETE");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void deleteAsyn(String url, Map<String, String> params, OnHttpResult onHttpResult) {
/* 196 */     deleteAsyn(url, params, null, onHttpResult);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void deleteAsyn(String url, Map<String, String> params, Map<String, String> headers, OnHttpResult onHttpResult) {
/* 208 */     requestAsyn(mapToString(url, params, "?"), null, headers, "DELETE", onHttpResult);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String request(String url, String params, Map<String, String> headers, String method) {
/* 221 */     return request(url, params, headers, method, "application/x-www-form-urlencoded");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String request(String url, String params, Map<String, String> headers, String method, String mediaType) {
/* 235 */     String result = null;
/* 236 */     if (url == null || url.trim().isEmpty()) {
/* 237 */       return null;
/*     */     }
/* 239 */     method = method.toUpperCase();
/* 240 */     OutputStreamWriter writer = null;
/* 241 */     BufferedReader in = null;
/* 242 */     ByteArrayOutputStream resOut = null;
/*     */     try {
/* 244 */       URL httpUrl = new URL(url);
/* 245 */       HttpURLConnection conn = (HttpURLConnection)httpUrl.openConnection();
/* 246 */       if (method.equals("POST") || method.equals("PUT")) {
/* 247 */         conn.setDoOutput(true);
/* 248 */         conn.setUseCaches(false);
/*     */       } 
/* 250 */       conn.setReadTimeout(8000);
/* 251 */       conn.setConnectTimeout(5000);
/* 252 */       conn.setRequestMethod(method);
/* 253 */       conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
/* 254 */       conn.setRequestProperty("Accept-Charset", "utf-8");
/* 255 */       conn.setRequestProperty("Content-Type", mediaType);
/*     */       
/* 257 */       if (headers != null) {
/* 258 */         Iterator<String> iterator = headers.keySet().iterator();
/* 259 */         while (iterator.hasNext()) {
/* 260 */           String key = iterator.next();
/* 261 */           conn.setRequestProperty(key, headers.get(key));
/*     */         } 
/*     */       } 
/*     */       
/* 265 */       if (params != null) {
/* 266 */         conn.setRequestProperty("Content-Length", String.valueOf(params.length()));
/* 267 */         writer = new OutputStreamWriter(conn.getOutputStream());
/* 268 */         writer.write(params);
/* 269 */         writer.flush();
/*     */       } 
/*     */       
/* 272 */       if (conn.getResponseCode() >= 300) {
/* 273 */         throw new RuntimeException("HTTP Request is not success, Response code is " + conn.getResponseCode());
/*     */       }
/*     */       
/* 276 */       StringBuilder sb = new StringBuilder();
/* 277 */       InputStream input = conn.getInputStream();
/* 278 */       in = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
/* 279 */       String line = "";
/* 280 */       while ((line = in.readLine()) != null) {
/* 281 */         sb.append(line).append(System.lineSeparator());
/*     */       }
/* 283 */       result = sb.toString();
/*     */       
/* 285 */       conn.disconnect();
/* 286 */       in.close();
/* 287 */       input.close();
/* 288 */     } catch (IOException e) {
/* 289 */       e.printStackTrace();
/*     */     } finally {
/* 291 */       if (in != null) {
/*     */         try {
/* 293 */           in.close();
/* 294 */         } catch (IOException e) {
/* 295 */           e.printStackTrace();
/*     */         } 
/*     */       }
/* 298 */       if (writer != null) {
/*     */         try {
/* 300 */           writer.close();
/* 301 */         } catch (IOException e) {
/* 302 */           e.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/* 306 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void requestAsyn(String url, String params, Map<String, String> headers, String method, OnHttpResult onHttpResult) {
/* 320 */     requestAsyn(url, params, headers, method, "application/x-www-form-urlencoded", onHttpResult);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void requestAsyn(String url, String params, Map<String, String> headers, String method, String mediaType, OnHttpResult onHttpResult) {
/* 334 */     (new Thread(() -> {
/*     */           try {
/*     */             String result = request(url, params, headers, method, mediaType);
/*     */             onHttpResult.onSuccess(result);
/* 338 */           } catch (Exception e) {
/*     */             onHttpResult.onError(e.getMessage());
/*     */           } 
/* 341 */         })).start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String mapToString(String url, Map<String, String> params, String first) {
/*     */     StringBuilder sb;
/* 349 */     if (url != null) {
/* 350 */       sb = new StringBuilder(url);
/*     */     } else {
/* 352 */       sb = new StringBuilder();
/*     */     } 
/* 354 */     if (params != null) {
/* 355 */       boolean isFirst = true;
/* 356 */       Iterator<String> iterator = params.keySet().iterator();
/* 357 */       while (iterator.hasNext()) {
/* 358 */         String key = iterator.next();
/* 359 */         if (isFirst) {
/* 360 */           if (first != null) {
/* 361 */             sb.append(first);
/*     */           }
/* 363 */           isFirst = false;
/*     */         } else {
/* 365 */           sb.append("&");
/*     */         } 
/* 367 */         sb.append(key);
/* 368 */         sb.append("=");
/* 369 */         sb.append(params.get(key));
/*     */       } 
/*     */     } 
/* 372 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static interface OnHttpResult {
/*     */     void onSuccess(String param1String);
/*     */     
/*     */     void onError(String param1String);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\HttpUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */