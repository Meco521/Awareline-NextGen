/*    */ package com.me.theresa.fontRenderer.font.util;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.net.URL;
/*    */ 
/*    */ public class ClasspathLocation
/*    */   implements SlickResourceLocation
/*    */ {
/*    */   public URL getResource(String ref) {
/* 10 */     String cpRef = ref.replace('\\', '/');
/* 11 */     return ResourceLoader.class.getClassLoader().getResource(cpRef);
/*    */   }
/*    */ 
/*    */   
/*    */   public InputStream getResourceAsStream(String ref) {
/* 16 */     String cpRef = ref.replace('\\', '/');
/* 17 */     return ResourceLoader.class.getClassLoader().getResourceAsStream(cpRef);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\fon\\util\ClasspathLocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */