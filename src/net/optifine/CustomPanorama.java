/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Random;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.util.MathUtils;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ 
/*     */ 
/*     */ public class CustomPanorama
/*     */ {
/*  17 */   private static CustomPanoramaProperties customPanoramaProperties = null;
/*  18 */   private static final Random random = new Random();
/*     */ 
/*     */   
/*     */   public static CustomPanoramaProperties getCustomPanoramaProperties() {
/*  22 */     return customPanoramaProperties;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update() {
/*  27 */     customPanoramaProperties = null;
/*  28 */     String[] astring = getPanoramaFolders();
/*     */     
/*  30 */     if (astring.length > 1) {
/*     */       PropertiesOrdered propertiesOrdered;
/*  32 */       Properties[] aproperties = getPanoramaProperties(astring);
/*  33 */       int[] aint = getWeights(aproperties);
/*  34 */       int i = getRandomIndex(aint);
/*  35 */       String s = astring[i];
/*  36 */       Properties properties = aproperties[i];
/*     */       
/*  38 */       if (properties == null)
/*     */       {
/*  40 */         properties = aproperties[0];
/*     */       }
/*     */       
/*  43 */       if (properties == null)
/*     */       {
/*  45 */         propertiesOrdered = new PropertiesOrdered();
/*     */       }
/*     */       
/*  48 */       CustomPanoramaProperties custompanoramaproperties = new CustomPanoramaProperties(s, (Properties)propertiesOrdered);
/*  49 */       customPanoramaProperties = custompanoramaproperties;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String[] getPanoramaFolders() {
/*  55 */     List<String> list = new ArrayList<>();
/*  56 */     list.add("textures/gui/title/background");
/*     */     
/*  58 */     for (int i = 0; i < 100; i++) {
/*     */       
/*  60 */       String s = "optifine/gui/background" + i;
/*  61 */       String s1 = s + "/panorama_0.png";
/*  62 */       ResourceLocation resourcelocation = new ResourceLocation(s1);
/*     */       
/*  64 */       if (Config.hasResource(resourcelocation))
/*     */       {
/*  66 */         list.add(s);
/*     */       }
/*     */     } 
/*     */     
/*  70 */     String[] astring = list.<String>toArray(new String[0]);
/*  71 */     return astring;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Properties[] getPanoramaProperties(String[] folders) {
/*  76 */     Properties[] aproperties = new Properties[folders.length];
/*     */     
/*  78 */     for (int i = 0; i < folders.length; i++) {
/*     */       
/*  80 */       String s = folders[i];
/*     */       
/*  82 */       if (i == 0) {
/*     */         
/*  84 */         s = "optifine/gui";
/*     */       }
/*     */       else {
/*     */         
/*  88 */         Config.dbg("CustomPanorama: " + s);
/*     */       } 
/*     */       
/*  91 */       ResourceLocation resourcelocation = new ResourceLocation(s + "/background.properties");
/*     */ 
/*     */       
/*     */       try {
/*  95 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */         
/*  97 */         if (inputstream != null)
/*     */         {
/*  99 */           PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 100 */           propertiesOrdered.load(inputstream);
/* 101 */           Config.dbg("CustomPanorama: " + resourcelocation.getResourcePath());
/* 102 */           aproperties[i] = (Properties)propertiesOrdered;
/* 103 */           inputstream.close();
/*     */         }
/*     */       
/* 106 */       } catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     return aproperties;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] getWeights(Properties[] propertiess) {
/* 117 */     int[] aint = new int[propertiess.length];
/*     */     
/* 119 */     for (int i = 0; i < aint.length; i++) {
/*     */       
/* 121 */       Properties properties = propertiess[i];
/*     */       
/* 123 */       if (properties == null)
/*     */       {
/* 125 */         properties = propertiess[0];
/*     */       }
/*     */       
/* 128 */       if (properties == null) {
/*     */         
/* 130 */         aint[i] = 1;
/*     */       }
/*     */       else {
/*     */         
/* 134 */         String s = properties.getProperty("weight", (String)null);
/* 135 */         aint[i] = Config.parseInt(s, 1);
/*     */       } 
/*     */     } 
/*     */     
/* 139 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getRandomIndex(int[] weights) {
/* 144 */     int i = MathUtils.getSum(weights);
/* 145 */     int j = random.nextInt(i);
/* 146 */     int k = 0;
/*     */     
/* 148 */     for (int l = 0; l < weights.length; l++) {
/*     */       
/* 150 */       k += weights[l];
/*     */       
/* 152 */       if (k > j)
/*     */       {
/* 154 */         return l;
/*     */       }
/*     */     } 
/*     */     
/* 158 */     return weights.length - 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\CustomPanorama.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */