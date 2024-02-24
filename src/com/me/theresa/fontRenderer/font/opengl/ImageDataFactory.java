/*    */ package com.me.theresa.fontRenderer.font.opengl;
/*    */ 
/*    */ import com.me.theresa.fontRenderer.font.log.Log;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImageDataFactory
/*    */ {
/*    */   static boolean usePngLoader = true;
/*    */   private static boolean pngLoaderPropertyChecked = false;
/*    */   private static final String PNG_LOADER = "org.newdawn.slick.pngloader";
/*    */   
/*    */   private static void checkProperty() {
/* 21 */     if (!pngLoaderPropertyChecked) {
/* 22 */       pngLoaderPropertyChecked = true;
/*    */       
/*    */       try {
/* 25 */         AccessController.doPrivileged(new PrivilegedAction() {
/*    */               public Object run() {
/* 27 */                 String val = System.getProperty("org.newdawn.slick.pngloader");
/* 28 */                 if ("false".equalsIgnoreCase(val)) {
/* 29 */                   ImageDataFactory.usePngLoader = false;
/*    */                 }
/*    */                 
/* 32 */                 Log.info("Use Java PNG Loader = " + ImageDataFactory.usePngLoader);
/* 33 */                 return null;
/*    */               }
/*    */             });
/* 36 */       } catch (Throwable throwable) {}
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static LoadableImageData getImageDataFor(String ref) {
/* 44 */     checkProperty();
/*    */     
/* 46 */     ref = ref.toLowerCase();
/*    */     
/* 48 */     if (ref.endsWith(".tga")) {
/* 49 */       return new TGAImageData();
/*    */     }
/* 51 */     if (ref.endsWith(".png")) {
/* 52 */       CompositeImageData data = new CompositeImageData();
/* 53 */       if (usePngLoader) {
/* 54 */         data.add(new PNGImageData());
/*    */       }
/* 56 */       data.add(new ImageIOImageData());
/*    */       
/* 58 */       return data;
/*    */     } 
/*    */     
/* 61 */     return new ImageIOImageData();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\ImageDataFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */