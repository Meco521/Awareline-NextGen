/*    */ package com.me.theresa.fontRenderer.font.util;
/*    */ 
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.File;
/*    */ import java.io.InputStream;
/*    */ import java.net.URL;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ public class ResourceLoader
/*    */ {
/* 12 */   private static final ArrayList locations = new ArrayList();
/*    */   
/*    */   static {
/* 15 */     locations.add(new ClasspathLocation());
/* 16 */     locations.add(new FileSystemLocation(new File(".")));
/*    */   }
/*    */ 
/*    */   
/*    */   public static InputStream getResourceAsStream(String ref) {
/* 21 */     InputStream in = null;
/*    */     
/* 23 */     for (int i = 0; i < locations.size(); i++) {
/* 24 */       SlickResourceLocation location = locations.get(i);
/* 25 */       in = location.getResourceAsStream(ref);
/* 26 */       if (in != null) {
/*    */         break;
/*    */       }
/*    */     } 
/*    */     
/* 31 */     if (in == null) {
/* 32 */       throw new RuntimeException("Resource not found: " + ref);
/*    */     }
/*    */     
/* 35 */     return new BufferedInputStream(in);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean resourceExists(String ref) {
/* 40 */     URL url = null;
/*    */     
/* 42 */     for (int i = 0; i < locations.size(); i++) {
/* 43 */       SlickResourceLocation location = locations.get(i);
/* 44 */       url = location.getResource(ref);
/* 45 */       if (url != null) {
/* 46 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public static URL getResource(String ref) {
/* 55 */     URL url = null;
/*    */     
/* 57 */     for (int i = 0; i < locations.size(); i++) {
/* 58 */       SlickResourceLocation location = locations.get(i);
/* 59 */       url = location.getResource(ref);
/* 60 */       if (url != null) {
/*    */         break;
/*    */       }
/*    */     } 
/*    */     
/* 65 */     if (url == null) {
/* 66 */       throw new RuntimeException("Resource not found: " + ref);
/*    */     }
/*    */     
/* 69 */     return url;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\fon\\util\ResourceLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */