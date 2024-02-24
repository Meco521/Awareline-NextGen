/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.IllegalFormatException;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.io.Charsets;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringTranslate
/*     */ {
/*  20 */   private static final Pattern numericVariablePattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  25 */   private static final Splitter equalSignSplitter = Splitter.on('=').limit(2);
/*     */ 
/*     */   
/*  28 */   private static final StringTranslate instance = new StringTranslate();
/*  29 */   private final Map<String, String> languageList = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastUpdateTimeInMilliseconds;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringTranslate() {
/*     */     try {
/*  40 */       InputStream inputstream = StringTranslate.class.getResourceAsStream("/assets/minecraft/lang/en_US.lang");
/*     */       
/*  42 */       for (String s : IOUtils.readLines(inputstream, Charsets.UTF_8)) {
/*     */         
/*  44 */         if (!s.isEmpty() && s.charAt(0) != '#') {
/*     */           
/*  46 */           String[] astring = (String[])Iterables.toArray(equalSignSplitter.split(s), String.class);
/*     */           
/*  48 */           if (astring != null && astring.length == 2) {
/*     */             
/*  50 */             String s1 = astring[0];
/*  51 */             String s2 = numericVariablePattern.matcher(astring[1]).replaceAll("%$1s");
/*  52 */             this.languageList.put(s1, s2);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  57 */       this.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
/*     */     }
/*  59 */     catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static StringTranslate getInstance() {
/*  70 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void replaceWith(Map<String, String> p_135063_0_) {
/*  78 */     instance.languageList.clear();
/*  79 */     instance.languageList.putAll(p_135063_0_);
/*  80 */     instance.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String translateKey(String key) {
/*  88 */     return tryTranslateKey(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String translateKeyFormat(String key, Object... format) {
/*  96 */     String s = tryTranslateKey(key);
/*     */ 
/*     */     
/*     */     try {
/* 100 */       return String.format(s, format);
/*     */     }
/* 102 */     catch (IllegalFormatException var5) {
/*     */       
/* 104 */       return "Format error: " + s;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String tryTranslateKey(String key) {
/* 113 */     String s = this.languageList.get(key);
/* 114 */     return (s == null) ? key : s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean isKeyTranslated(String key) {
/* 122 */     return this.languageList.containsKey(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLastUpdateTimeInMilliseconds() {
/* 130 */     return this.lastUpdateTimeInMilliseconds;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\StringTranslate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */