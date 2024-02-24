/*     */ package net.optifine;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Iterables;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.resources.IResourcePack;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.Charsets;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ public class Lang
/*     */ {
/*  22 */   private static final Splitter splitter = Splitter.on('=').limit(2);
/*  23 */   private static final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d.]*[df]");
/*     */ 
/*     */   
/*     */   public static void resourcesReloaded() {
/*  27 */     Map map = I18n.getLocaleProperties();
/*  28 */     List<String> list = new ArrayList<>();
/*  29 */     String s = "optifine/lang/";
/*  30 */     String s1 = "en_US";
/*  31 */     String s2 = ".lang";
/*  32 */     list.add(s + s1 + s2);
/*     */     
/*  34 */     if (!(Config.getGameSettings()).language.equals(s1))
/*     */     {
/*  36 */       list.add(s + (Config.getGameSettings()).language + s2);
/*     */     }
/*     */     
/*  39 */     String[] astring = list.<String>toArray(new String[0]);
/*  40 */     loadResources((IResourcePack)Config.getDefaultResourcePack(), astring, map);
/*  41 */     IResourcePack[] airesourcepack = Config.getResourcePacks();
/*     */     
/*  43 */     for (int i = 0; i < airesourcepack.length; i++) {
/*     */       
/*  45 */       IResourcePack iresourcepack = airesourcepack[i];
/*  46 */       loadResources(iresourcepack, astring, map);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadResources(IResourcePack rp, String[] files, Map localeProperties) {
/*     */     try {
/*  54 */       for (int i = 0; i < files.length; i++) {
/*     */         
/*  56 */         String s = files[i];
/*  57 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/*     */         
/*  59 */         if (rp.resourceExists(resourcelocation))
/*     */         {
/*  61 */           InputStream inputstream = rp.getInputStream(resourcelocation);
/*     */           
/*  63 */           if (inputstream != null)
/*     */           {
/*  65 */             loadLocaleData(inputstream, localeProperties);
/*     */           }
/*     */         }
/*     */       
/*     */       } 
/*  70 */     } catch (IOException ioexception) {
/*     */       
/*  72 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadLocaleData(InputStream is, Map<String, String> localeProperties) throws IOException {
/*  78 */     Iterator<String> iterator = IOUtils.readLines(is, Charsets.UTF_8).iterator();
/*  79 */     is.close();
/*     */     
/*  81 */     while (iterator.hasNext()) {
/*     */       
/*  83 */       String s = iterator.next();
/*     */       
/*  85 */       if (!s.isEmpty() && s.charAt(0) != '#') {
/*     */         
/*  87 */         String[] astring = (String[])Iterables.toArray(splitter.split(s), String.class);
/*     */         
/*  89 */         if (astring != null && astring.length == 2) {
/*     */           
/*  91 */           String s1 = astring[0];
/*  92 */           String s2 = pattern.matcher(astring[1]).replaceAll("%$1s");
/*  93 */           localeProperties.put(s1, s2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String get(String key) {
/* 101 */     return I18n.format(key, new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String get(String key, String def) {
/* 106 */     String s = I18n.format(key, new Object[0]);
/* 107 */     return (s != null && !s.equals(key)) ? s : def;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getOn() {
/* 112 */     return I18n.format("options.on", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getOff() {
/* 117 */     return I18n.format("options.off", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getFast() {
/* 122 */     return I18n.format("options.graphics.fast", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getFancy() {
/* 127 */     return I18n.format("options.graphics.fancy", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getDefault() {
/* 132 */     return I18n.format("generator.default", new Object[0]);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\Lang.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */