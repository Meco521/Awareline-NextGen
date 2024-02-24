/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FontUtils
/*     */ {
/*     */   public static Properties readFontProperties(ResourceLocation locationFontTexture) {
/*  15 */     String s = locationFontTexture.getResourcePath();
/*  16 */     Properties properties = new PropertiesOrdered();
/*  17 */     String s1 = ".png";
/*     */     
/*  19 */     if (!s.endsWith(s1))
/*     */     {
/*  21 */       return properties;
/*     */     }
/*     */ 
/*     */     
/*  25 */     String s2 = s.substring(0, s.length() - s1.length()) + ".properties";
/*     */ 
/*     */     
/*     */     try {
/*  29 */       ResourceLocation resourcelocation = new ResourceLocation(locationFontTexture.getResourceDomain(), s2);
/*  30 */       InputStream inputstream = Config.getResourceStream(Config.getResourceManager(), resourcelocation);
/*     */       
/*  32 */       if (inputstream == null)
/*     */       {
/*  34 */         return properties;
/*     */       }
/*     */       
/*  37 */       Config.log("Loading " + s2);
/*  38 */       properties.load(inputstream);
/*  39 */       inputstream.close();
/*     */     }
/*  41 */     catch (FileNotFoundException fileNotFoundException) {
/*     */ 
/*     */     
/*     */     }
/*  45 */     catch (IOException ioexception) {
/*     */       
/*  47 */       ioexception.printStackTrace();
/*     */     } 
/*     */     
/*  50 */     return properties;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void readCustomCharWidths(Properties props, float[] charWidth) {
/*  56 */     for (Object e : props.keySet()) {
/*     */       
/*  58 */       String s = (String)e;
/*  59 */       String s1 = "width.";
/*     */       
/*  61 */       if (s.startsWith(s1)) {
/*     */         
/*  63 */         String s2 = s.substring(s1.length());
/*  64 */         int i = Config.parseInt(s2, -1);
/*     */         
/*  66 */         if (i >= 0 && i < charWidth.length) {
/*     */           
/*  68 */           String s3 = props.getProperty(s);
/*  69 */           float f = Config.parseFloat(s3, -1.0F);
/*     */           
/*  71 */           if (f >= 0.0F)
/*     */           {
/*  73 */             charWidth[i] = f;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static float readFloat(Properties props, String key, float defOffset) {
/*  82 */     String s = props.getProperty(key);
/*     */     
/*  84 */     if (s == null)
/*     */     {
/*  86 */       return defOffset;
/*     */     }
/*     */ 
/*     */     
/*  90 */     float f = Config.parseFloat(s, Float.MIN_VALUE);
/*     */     
/*  92 */     if (f == Float.MIN_VALUE) {
/*     */       
/*  94 */       Config.warn("Invalid value for " + key + ": " + s);
/*  95 */       return defOffset;
/*     */     } 
/*     */ 
/*     */     
/*  99 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean readBoolean(Properties props, String key, boolean defVal) {
/* 106 */     String s = props.getProperty(key);
/*     */     
/* 108 */     if (s == null)
/*     */     {
/* 110 */       return defVal;
/*     */     }
/*     */ 
/*     */     
/* 114 */     String s1 = s.toLowerCase().trim();
/*     */     
/* 116 */     if (!s1.equals("true") && !s1.equals("on")) {
/*     */       
/* 118 */       if (!s1.equals("false") && !s1.equals("off")) {
/*     */         
/* 120 */         Config.warn("Invalid value for " + key + ": " + s);
/* 121 */         return defVal;
/*     */       } 
/*     */ 
/*     */       
/* 125 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResourceLocation getHdFontLocation(ResourceLocation fontLoc) {
/* 137 */     if (!Config.isCustomFonts())
/*     */     {
/* 139 */       return fontLoc;
/*     */     }
/* 141 */     if (fontLoc == null)
/*     */     {
/* 143 */       return fontLoc;
/*     */     }
/* 145 */     if (!Config.isMinecraftThread())
/*     */     {
/* 147 */       return fontLoc;
/*     */     }
/*     */ 
/*     */     
/* 151 */     String s = fontLoc.getResourcePath();
/* 152 */     String s1 = "textures/";
/* 153 */     String s2 = "mcpatcher/";
/*     */     
/* 155 */     if (!s.startsWith(s1))
/*     */     {
/* 157 */       return fontLoc;
/*     */     }
/*     */ 
/*     */     
/* 161 */     s = s.substring(s1.length());
/* 162 */     s = s2 + s;
/* 163 */     ResourceLocation resourcelocation = new ResourceLocation(fontLoc.getResourceDomain(), s);
/* 164 */     return Config.hasResource(Config.getResourceManager(), resourcelocation) ? resourcelocation : fontLoc;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\FontUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */