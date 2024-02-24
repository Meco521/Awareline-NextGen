/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.File;
/*    */ import java.io.InputStream;
/*    */ import java.nio.file.Files;
/*    */ import net.optifine.util.StrUtils;
/*    */ 
/*    */ 
/*    */ public class ShaderPackFolder
/*    */   implements IShaderPack
/*    */ {
/*    */   protected File packFile;
/*    */   
/*    */   public ShaderPackFolder(String name, File file) {
/* 16 */     this.packFile = file;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public InputStream getResourceAsStream(String resName) {
/*    */     try {
/* 27 */       String s = StrUtils.removePrefixSuffix(resName, "/", "/");
/* 28 */       File file1 = new File(this.packFile, s);
/* 29 */       return !file1.exists() ? null : new BufferedInputStream(Files.newInputStream(file1.toPath(), new java.nio.file.OpenOption[0]));
/*    */     }
/* 31 */     catch (Exception var4) {
/*    */       
/* 33 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasDirectory(String name) {
/* 39 */     File file1 = new File(this.packFile, name.substring(1));
/* 40 */     return !file1.exists() ? false : file1.isDirectory();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 45 */     return this.packFile.getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\ShaderPackFolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */