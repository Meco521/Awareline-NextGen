/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ 
/*     */ public class FileResourcePack
/*     */   extends AbstractResourcePack
/*     */   implements Closeable {
/*  20 */   public static final Splitter entryNameSplitter = Splitter.on('/').omitEmptyStrings().limit(3);
/*     */   
/*     */   private ZipFile resourcePackZipFile;
/*     */   
/*     */   public FileResourcePack(File resourcePackFileIn) {
/*  25 */     super(resourcePackFileIn);
/*     */   }
/*     */ 
/*     */   
/*     */   private ZipFile getResourcePackZipFile() throws IOException {
/*  30 */     if (this.resourcePackZipFile == null)
/*     */     {
/*  32 */       this.resourcePackZipFile = new ZipFile(this.resourcePackFile);
/*     */     }
/*     */     
/*  35 */     return this.resourcePackZipFile;
/*     */   }
/*     */ 
/*     */   
/*     */   protected InputStream getInputStreamByName(String name) throws IOException {
/*  40 */     ZipFile zipfile = getResourcePackZipFile();
/*  41 */     ZipEntry zipentry = zipfile.getEntry(name);
/*     */     
/*  43 */     if (zipentry == null)
/*     */     {
/*  45 */       throw new ResourcePackFileNotFoundException(this.resourcePackFile, name);
/*     */     }
/*     */ 
/*     */     
/*  49 */     return zipfile.getInputStream(zipentry);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasResourceName(String name) {
/*     */     try {
/*  57 */       return (getResourcePackZipFile().getEntry(name) != null);
/*     */     }
/*  59 */     catch (IOException var3) {
/*     */       
/*  61 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getResourceDomains() {
/*     */     ZipFile zipfile;
/*     */     try {
/*  71 */       zipfile = getResourcePackZipFile();
/*     */     }
/*  73 */     catch (IOException var8) {
/*     */       
/*  75 */       return Collections.emptySet();
/*     */     } 
/*     */     
/*  78 */     Enumeration<? extends ZipEntry> enumeration = zipfile.entries();
/*  79 */     Set<String> set = Sets.newHashSet();
/*     */     
/*  81 */     while (enumeration.hasMoreElements()) {
/*     */       
/*  83 */       ZipEntry zipentry = enumeration.nextElement();
/*  84 */       String s = zipentry.getName();
/*     */       
/*  86 */       if (s.startsWith("assets/")) {
/*     */         
/*  88 */         List<String> list = Lists.newArrayList(entryNameSplitter.split(s));
/*     */         
/*  90 */         if (list.size() > 1) {
/*     */           
/*  92 */           String s1 = list.get(1);
/*     */           
/*  94 */           if (!s1.equals(s1.toLowerCase())) {
/*     */             
/*  96 */             logNameNotLowercase(s1);
/*     */             
/*     */             continue;
/*     */           } 
/* 100 */           set.add(s1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 106 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 111 */     close();
/* 112 */     super.finalize();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 117 */     if (this.resourcePackZipFile != null) {
/*     */       
/* 119 */       this.resourcePackZipFile.close();
/* 120 */       this.resourcePackZipFile = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\FileResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */