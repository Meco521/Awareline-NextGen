/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ import java.util.Enumeration;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ 
/*     */ public class ShaderPackZip
/*     */   implements IShaderPack
/*     */ {
/*     */   protected File packFile;
/*     */   protected ZipFile packZipFile;
/*     */   protected String baseFolder;
/*     */   
/*     */   public ShaderPackZip(String name, File file) {
/*  26 */     this.packFile = file;
/*  27 */     this.packZipFile = null;
/*  28 */     this.baseFolder = "";
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/*  33 */     if (this.packZipFile != null) {
/*     */ 
/*     */       
/*     */       try {
/*  37 */         this.packZipFile.close();
/*     */       }
/*  39 */       catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  44 */       this.packZipFile = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getResourceAsStream(String resName) {
/*     */     try {
/*  52 */       if (this.packZipFile == null) {
/*     */         
/*  54 */         this.packZipFile = new ZipFile(this.packFile);
/*  55 */         this.baseFolder = detectBaseFolder(this.packZipFile);
/*     */       } 
/*     */       
/*  58 */       String s = StrUtils.removePrefix(resName, "/");
/*     */       
/*  60 */       if (s.contains(".."))
/*     */       {
/*  62 */         s = resolveRelative(s);
/*     */       }
/*     */       
/*  65 */       ZipEntry zipentry = this.packZipFile.getEntry(this.baseFolder + s);
/*  66 */       return (zipentry == null) ? null : this.packZipFile.getInputStream(zipentry);
/*     */     }
/*  68 */     catch (Exception var4) {
/*     */       
/*  70 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String resolveRelative(String name) {
/*  76 */     Deque<String> deque = new ArrayDeque<>();
/*  77 */     String[] astring = Config.tokenize(name, "/");
/*     */     
/*  79 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/*  81 */       String s = astring[i];
/*     */       
/*  83 */       if (s.equals("..")) {
/*     */         
/*  85 */         if (deque.isEmpty())
/*     */         {
/*  87 */           return "";
/*     */         }
/*     */         
/*  90 */         deque.removeLast();
/*     */       }
/*     */       else {
/*     */         
/*  94 */         deque.add(s);
/*     */       } 
/*     */     } 
/*     */     
/*  98 */     String s1 = Joiner.on('/').join(deque);
/*  99 */     return s1;
/*     */   }
/*     */ 
/*     */   
/*     */   private String detectBaseFolder(ZipFile zip) {
/* 104 */     ZipEntry zipentry = zip.getEntry("shaders/");
/*     */     
/* 106 */     if (zipentry != null && zipentry.isDirectory())
/*     */     {
/* 108 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 112 */     Pattern pattern = Pattern.compile("([^/]+/)shaders/");
/* 113 */     Enumeration<? extends ZipEntry> enumeration = zip.entries();
/*     */     
/* 115 */     while (enumeration.hasMoreElements()) {
/*     */       
/* 117 */       ZipEntry zipentry1 = enumeration.nextElement();
/* 118 */       String s = zipentry1.getName();
/* 119 */       Matcher matcher = pattern.matcher(s);
/*     */       
/* 121 */       if (matcher.matches()) {
/*     */         
/* 123 */         String s1 = matcher.group(1);
/*     */         
/* 125 */         if (s1 != null) {
/*     */           
/* 127 */           if (s1.equals("shaders/"))
/*     */           {
/* 129 */             return "";
/*     */           }
/*     */           
/* 132 */           return s1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 137 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasDirectory(String resName) {
/*     */     try {
/* 145 */       if (this.packZipFile == null) {
/*     */         
/* 147 */         this.packZipFile = new ZipFile(this.packFile);
/* 148 */         this.baseFolder = detectBaseFolder(this.packZipFile);
/*     */       } 
/*     */       
/* 151 */       String s = StrUtils.removePrefix(resName, "/");
/* 152 */       ZipEntry zipentry = this.packZipFile.getEntry(this.baseFolder + s);
/* 153 */       return (zipentry != null);
/*     */     }
/* 155 */     catch (IOException var4) {
/*     */       
/* 157 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 163 */     return this.packFile.getName();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\ShaderPackZip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */