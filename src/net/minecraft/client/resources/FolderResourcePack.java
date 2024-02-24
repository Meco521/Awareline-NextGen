/*    */ package net.minecraft.client.resources;
/*    */ import com.google.common.collect.Sets;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileFilter;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.InputStream;
/*    */ import java.util.Set;
/*    */ import org.apache.commons.io.filefilter.DirectoryFileFilter;
/*    */ 
/*    */ public class FolderResourcePack extends AbstractResourcePack {
/*    */   public FolderResourcePack(File resourcePackFileIn) {
/* 13 */     super(resourcePackFileIn);
/*    */   }
/*    */ 
/*    */   
/*    */   protected InputStream getInputStreamByName(String name) throws IOException {
/* 18 */     return new BufferedInputStream(new FileInputStream(new File(this.resourcePackFile, name)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean hasResourceName(String name) {
/* 23 */     return (new File(this.resourcePackFile, name)).isFile();
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> getResourceDomains() {
/* 28 */     Set<String> set = Sets.newHashSet();
/* 29 */     File file1 = new File(this.resourcePackFile, "assets/");
/*    */     
/* 31 */     if (file1.isDirectory())
/*    */     {
/* 33 */       for (File file2 : file1.listFiles((FileFilter)DirectoryFileFilter.DIRECTORY)) {
/*    */         
/* 35 */         String s = getRelativeName(file1, file2);
/*    */         
/* 37 */         if (!s.equals(s.toLowerCase())) {
/*    */           
/* 39 */           logNameNotLowercase(s);
/*    */         }
/*    */         else {
/*    */           
/* 43 */           set.add(s.substring(0, s.length() - 1));
/*    */         } 
/*    */       } 
/*    */     }
/*    */     
/* 48 */     return set;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\FolderResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */