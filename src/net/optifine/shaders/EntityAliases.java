/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorForge;
/*     */ import net.optifine.shaders.config.MacroProcessor;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityAliases
/*     */ {
/*  20 */   private static int[] entityAliases = null;
/*     */   
/*     */   private static boolean updateOnResourcesReloaded;
/*     */   
/*     */   public static int getEntityAliasId(int entityId) {
/*  25 */     if (entityAliases == null)
/*     */     {
/*  27 */       return -1;
/*     */     }
/*  29 */     if (entityId >= 0 && entityId < entityAliases.length) {
/*     */       
/*  31 */       int i = entityAliases[entityId];
/*  32 */       return i;
/*     */     } 
/*     */ 
/*     */     
/*  36 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void resourcesReloaded() {
/*  42 */     if (updateOnResourcesReloaded) {
/*     */       
/*  44 */       updateOnResourcesReloaded = false;
/*  45 */       update(Shaders.getShaderPack());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update(IShaderPack shaderPack) {
/*  51 */     reset();
/*     */     
/*  53 */     if (shaderPack != null)
/*     */     {
/*  55 */       if (Reflector.Loader_getActiveModList.exists() && Config.getResourceManager() == null) {
/*     */         
/*  57 */         Config.dbg("[Shaders] Delayed loading of entity mappings after resources are loaded");
/*  58 */         updateOnResourcesReloaded = true;
/*     */       }
/*     */       else {
/*     */         
/*  62 */         List<Integer> list = new ArrayList<>();
/*  63 */         String s = "/shaders/entity.properties";
/*  64 */         InputStream inputstream = shaderPack.getResourceAsStream(s);
/*     */         
/*  66 */         if (inputstream != null)
/*     */         {
/*  68 */           loadEntityAliases(inputstream, s, list);
/*     */         }
/*     */         
/*  71 */         loadModEntityAliases(list);
/*     */         
/*  73 */         if (!list.isEmpty())
/*     */         {
/*  75 */           entityAliases = toArray(list);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void loadModEntityAliases(List<Integer> listEntityAliases) {
/*  83 */     String[] astring = ReflectorForge.getForgeModIds();
/*     */     
/*  85 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/*  87 */       String s = astring[i];
/*     */ 
/*     */       
/*     */       try {
/*  91 */         ResourceLocation resourcelocation = new ResourceLocation(s, "shaders/entity.properties");
/*  92 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*  93 */         loadEntityAliases(inputstream, resourcelocation.toString(), listEntityAliases);
/*     */       }
/*  95 */       catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadEntityAliases(InputStream in, String path, List<Integer> listEntityAliases) {
/* 104 */     if (in != null) {
/*     */       
/*     */       try {
/*     */         
/* 108 */         in = MacroProcessor.process(in, path);
/* 109 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 110 */         propertiesOrdered.load(in);
/* 111 */         in.close();
/* 112 */         Config.dbg("[Shaders] Parsing entity mappings: " + path);
/* 113 */         ConnectedParser connectedparser = new ConnectedParser("Shaders");
/*     */         
/* 115 */         for (Object e : propertiesOrdered.keySet())
/*     */         {
/* 117 */           String s = (String)e;
/* 118 */           String s1 = propertiesOrdered.getProperty(s);
/* 119 */           String s2 = "entity.";
/*     */           
/* 121 */           if (!s.startsWith(s2)) {
/*     */             
/* 123 */             Config.warn("[Shaders] Invalid entity ID: " + s);
/*     */             
/*     */             continue;
/*     */           } 
/* 127 */           String s3 = StrUtils.removePrefix(s, s2);
/* 128 */           int i = Config.parseInt(s3, -1);
/*     */           
/* 130 */           if (i < 0) {
/*     */             
/* 132 */             Config.warn("[Shaders] Invalid entity alias ID: " + i);
/*     */             
/*     */             continue;
/*     */           } 
/* 136 */           int[] aint = connectedparser.parseEntities(s1);
/*     */           
/* 138 */           if (aint != null && aint.length >= 1) {
/*     */             
/* 140 */             for (int j = 0; j < aint.length; j++) {
/*     */               
/* 142 */               int k = aint[j];
/* 143 */               addToList(listEntityAliases, k, i);
/*     */             } 
/*     */             
/*     */             continue;
/*     */           } 
/* 148 */           Config.warn("[Shaders] Invalid entity ID mapping: " + s + "=" + s1);
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 154 */       catch (IOException var15) {
/*     */         
/* 156 */         Config.warn("[Shaders] Error reading: " + path);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addToList(List<Integer> list, int index, int val) {
/* 163 */     while (list.size() <= index)
/*     */     {
/* 165 */       list.add(Integer.valueOf(-1));
/*     */     }
/*     */     
/* 168 */     list.set(index, Integer.valueOf(val));
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] toArray(List<Integer> list) {
/* 173 */     int[] aint = new int[list.size()];
/*     */     
/* 175 */     for (int i = 0; i < aint.length; i++)
/*     */     {
/* 177 */       aint[i] = ((Integer)list.get(i)).intValue();
/*     */     }
/*     */     
/* 180 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void reset() {
/* 185 */     entityAliases = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\EntityAliases.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */