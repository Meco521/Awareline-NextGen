/*     */ package net.optifine.shaders.config;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.StringReader;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.shaders.Shaders;
/*     */ 
/*     */ public class MacroProcessor {
/*     */   public static InputStream process(InputStream in, String path) throws IOException {
/*  17 */     String s = Config.readInputStream(in, "ASCII");
/*  18 */     String s1 = getMacroHeader(s);
/*     */     
/*  20 */     if (!s1.isEmpty()) {
/*     */       
/*  22 */       s = s1 + s;
/*     */       
/*  24 */       if (Shaders.saveFinalShaders) {
/*     */         
/*  26 */         String s2 = path.replace(':', '/') + ".pre";
/*  27 */         Shaders.saveShader(s2, s);
/*     */       } 
/*     */       
/*  30 */       s = process(s);
/*     */     } 
/*     */     
/*  33 */     if (Shaders.saveFinalShaders) {
/*     */       
/*  35 */       String s3 = path.replace(':', '/');
/*  36 */       Shaders.saveShader(s3, s);
/*     */     } 
/*     */     
/*  39 */     byte[] abyte = s.getBytes(StandardCharsets.US_ASCII);
/*  40 */     ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte);
/*  41 */     return bytearrayinputstream;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String process(String strIn) throws IOException {
/*  46 */     StringReader stringreader = new StringReader(strIn);
/*  47 */     BufferedReader bufferedreader = new BufferedReader(stringreader);
/*  48 */     MacroState macrostate = new MacroState();
/*  49 */     StringBuilder stringbuilder = new StringBuilder();
/*     */ 
/*     */     
/*     */     while (true) {
/*  53 */       String s = bufferedreader.readLine();
/*     */       
/*  55 */       if (s == null) {
/*     */         
/*  57 */         s = stringbuilder.toString();
/*  58 */         return s;
/*     */       } 
/*     */       
/*  61 */       if (macrostate.processLine(s) && !MacroState.isMacroLine(s)) {
/*     */         
/*  63 */         stringbuilder.append(s);
/*  64 */         stringbuilder.append("\n");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getMacroHeader(String str) throws IOException {
/*  71 */     StringBuilder stringbuilder = new StringBuilder();
/*  72 */     List<ShaderOption> list = null;
/*  73 */     List<ShaderMacro> list1 = null;
/*  74 */     StringReader stringreader = new StringReader(str);
/*  75 */     BufferedReader bufferedreader = new BufferedReader(stringreader);
/*     */ 
/*     */     
/*     */     while (true) {
/*  79 */       String s = bufferedreader.readLine();
/*     */       
/*  81 */       if (s == null)
/*     */       {
/*  83 */         return stringbuilder.toString();
/*     */       }
/*     */       
/*  86 */       if (MacroState.isMacroLine(s)) {
/*     */         
/*  88 */         if (stringbuilder.length() == 0)
/*     */         {
/*  90 */           stringbuilder.append(ShaderMacros.getFixedMacroLines());
/*     */         }
/*     */         
/*  93 */         if (list1 == null)
/*     */         {
/*  95 */           list1 = new ArrayList<>(Arrays.asList(ShaderMacros.getExtensions()));
/*     */         }
/*     */         
/*  98 */         Iterator<ShaderMacro> iterator = list1.iterator();
/*     */         
/* 100 */         while (iterator.hasNext()) {
/*     */           
/* 102 */           ShaderMacro shadermacro = iterator.next();
/*     */           
/* 104 */           if (s.contains(shadermacro.getName())) {
/*     */             
/* 106 */             stringbuilder.append(shadermacro.getSourceLine());
/* 107 */             stringbuilder.append("\n");
/* 108 */             iterator.remove();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<ShaderOption> getMacroOptions() {
/* 117 */     List<ShaderOption> list = new ArrayList<>();
/* 118 */     ShaderOption[] ashaderoption = Shaders.getShaderPackOptions();
/*     */     
/* 120 */     for (int i = 0; i < ashaderoption.length; i++) {
/*     */       
/* 122 */       ShaderOption shaderoption = ashaderoption[i];
/* 123 */       String s = shaderoption.getSourceLine();
/*     */       
/* 125 */       if (s != null && !s.isEmpty() && s.charAt(0) == '#')
/*     */       {
/* 127 */         list.add(shaderoption);
/*     */       }
/*     */     } 
/*     */     
/* 131 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\config\MacroProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */