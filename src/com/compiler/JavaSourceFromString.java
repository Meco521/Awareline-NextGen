/*    */ package com.compiler;
/*    */ 
/*    */ import java.net.URI;
/*    */ import javax.tools.JavaFileObject;
/*    */ import javax.tools.SimpleJavaFileObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class JavaSourceFromString
/*    */   extends SimpleJavaFileObject
/*    */ {
/*    */   private final String code;
/*    */   
/*    */   JavaSourceFromString(String name, String code) {
/* 39 */     super(URI.create("string:///" + name.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension), JavaFileObject.Kind.SOURCE);
/*    */     
/* 41 */     this.code = code;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CharSequence getCharContent(boolean ignoreEncodingErrors) {
/* 47 */     return this.code;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\compiler\JavaSourceFromString.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */