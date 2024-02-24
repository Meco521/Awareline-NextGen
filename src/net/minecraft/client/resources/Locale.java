/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.IllegalFormatException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.Charsets;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Locale
/*     */ {
/*  20 */   private static final Splitter splitter = Splitter.on('=').limit(2);
/*  21 */   private static final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
/*  22 */   Map<String, String> properties = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   private boolean unicode;
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void loadLocaleDataFiles(IResourceManager resourceManager, List<String> languageList) {
/*  30 */     this.properties.clear();
/*     */     
/*  32 */     for (String s : languageList) {
/*     */       
/*  34 */       String s1 = String.format("lang/%s.lang", new Object[] { s });
/*     */       
/*  36 */       for (String s2 : resourceManager.getResourceDomains()) {
/*     */ 
/*     */         
/*     */         try {
/*  40 */           loadLocaleData(resourceManager.getAllResources(new ResourceLocation(s2, s1)));
/*     */         }
/*  42 */         catch (IOException iOException) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  49 */     checkUnicode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnicode() {
/*  54 */     return this.unicode;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkUnicode() {
/*  59 */     this.unicode = false;
/*  60 */     int i = 0;
/*  61 */     int j = 0;
/*     */     
/*  63 */     for (String s : this.properties.values()) {
/*     */       
/*  65 */       int k = s.length();
/*  66 */       j += k;
/*     */       
/*  68 */       for (int l = 0; l < k; l++) {
/*     */         
/*  70 */         if (s.charAt(l) >= 'Ā')
/*     */         {
/*  72 */           i++;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  77 */     float f = i / j;
/*  78 */     this.unicode = (f > 0.1D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadLocaleData(List<IResource> resourcesList) throws IOException {
/*  86 */     for (IResource iresource : resourcesList) {
/*     */       
/*  88 */       InputStream inputstream = iresource.getInputStream();
/*     */ 
/*     */       
/*     */       try {
/*  92 */         loadLocaleData(inputstream);
/*     */       }
/*     */       finally {
/*     */         
/*  96 */         IOUtils.closeQuietly(inputstream);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadLocaleData(InputStream inputStreamIn) throws IOException {
/* 103 */     for (String s : IOUtils.readLines(inputStreamIn, Charsets.UTF_8)) {
/*     */       
/* 105 */       if (!s.isEmpty() && s.charAt(0) != '#') {
/*     */         
/* 107 */         String[] astring = (String[])Iterables.toArray(splitter.split(s), String.class);
/*     */         
/* 109 */         if (astring != null && astring.length == 2) {
/*     */           
/* 111 */           String s1 = astring[0];
/* 112 */           String s2 = pattern.matcher(astring[1]).replaceAll("%$1s");
/* 113 */           this.properties.put(s1, s2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String translateKeyPrivate(String translateKey) {
/* 124 */     String s = this.properties.get(translateKey);
/* 125 */     return (s == null) ? translateKey : s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String formatMessage(String translateKey, Object[] parameters) {
/* 133 */     String s = translateKeyPrivate(translateKey);
/*     */ 
/*     */     
/*     */     try {
/* 137 */       return String.format(s, parameters);
/*     */     }
/* 139 */     catch (IllegalFormatException var5) {
/*     */       
/* 141 */       return "Format error: " + s;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\Locale.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */