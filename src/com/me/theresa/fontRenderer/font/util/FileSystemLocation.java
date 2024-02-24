/*    */ package com.me.theresa.fontRenderer.font.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.URL;
/*    */ import java.nio.file.Files;
/*    */ 
/*    */ 
/*    */ public class FileSystemLocation
/*    */   implements SlickResourceLocation
/*    */ {
/*    */   private final File root;
/*    */   
/*    */   public FileSystemLocation(File root) {
/* 16 */     this.root = root;
/*    */   }
/*    */ 
/*    */   
/*    */   public URL getResource(String ref) {
/*    */     try {
/* 22 */       File file = new File(this.root, ref);
/* 23 */       if (!file.exists()) {
/* 24 */         file = new File(ref);
/*    */       }
/* 26 */       if (!file.exists()) {
/* 27 */         return null;
/*    */       }
/*    */       
/* 30 */       return file.toURI().toURL();
/* 31 */     } catch (IOException e) {
/* 32 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public InputStream getResourceAsStream(String ref) {
/*    */     try {
/* 38 */       File file = new File(this.root, ref);
/* 39 */       if (!file.exists()) {
/* 40 */         file = new File(ref);
/*    */       }
/* 42 */       return Files.newInputStream(file.toPath(), new java.nio.file.OpenOption[0]);
/* 43 */     } catch (IOException e) {
/* 44 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\fon\\util\FileSystemLocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */