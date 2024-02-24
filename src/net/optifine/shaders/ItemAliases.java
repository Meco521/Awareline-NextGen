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
/*     */ public class ItemAliases
/*     */ {
/*  20 */   private static int[] itemAliases = null;
/*     */   
/*     */   private static boolean updateOnResourcesReloaded;
/*     */   private static final int NO_ALIAS = -2147483648;
/*     */   
/*     */   public static int getItemAliasId(int itemId) {
/*  26 */     if (itemAliases == null)
/*     */     {
/*  28 */       return itemId;
/*     */     }
/*  30 */     if (itemId >= 0 && itemId < itemAliases.length) {
/*     */       
/*  32 */       int i = itemAliases[itemId];
/*  33 */       return (i == Integer.MIN_VALUE) ? itemId : i;
/*     */     } 
/*     */ 
/*     */     
/*  37 */     return itemId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void resourcesReloaded() {
/*  43 */     if (updateOnResourcesReloaded) {
/*     */       
/*  45 */       updateOnResourcesReloaded = false;
/*  46 */       update(Shaders.getShaderPack());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update(IShaderPack shaderPack) {
/*  52 */     reset();
/*     */     
/*  54 */     if (shaderPack != null)
/*     */     {
/*  56 */       if (Reflector.Loader_getActiveModList.exists() && Config.getResourceManager() == null) {
/*     */         
/*  58 */         Config.dbg("[Shaders] Delayed loading of item mappings after resources are loaded");
/*  59 */         updateOnResourcesReloaded = true;
/*     */       }
/*     */       else {
/*     */         
/*  63 */         List<Integer> list = new ArrayList<>();
/*  64 */         String s = "/shaders/item.properties";
/*  65 */         InputStream inputstream = shaderPack.getResourceAsStream(s);
/*     */         
/*  67 */         if (inputstream != null)
/*     */         {
/*  69 */           loadItemAliases(inputstream, s, list);
/*     */         }
/*     */         
/*  72 */         loadModItemAliases(list);
/*     */         
/*  74 */         if (!list.isEmpty())
/*     */         {
/*  76 */           itemAliases = toArray(list);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void loadModItemAliases(List<Integer> listItemAliases) {
/*  84 */     String[] astring = ReflectorForge.getForgeModIds();
/*     */     
/*  86 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/*  88 */       String s = astring[i];
/*     */ 
/*     */       
/*     */       try {
/*  92 */         ResourceLocation resourcelocation = new ResourceLocation(s, "shaders/item.properties");
/*  93 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*  94 */         loadItemAliases(inputstream, resourcelocation.toString(), listItemAliases);
/*     */       }
/*  96 */       catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadItemAliases(InputStream in, String path, List<Integer> listItemAliases) {
/* 105 */     if (in != null) {
/*     */       
/*     */       try {
/*     */         
/* 109 */         in = MacroProcessor.process(in, path);
/* 110 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 111 */         propertiesOrdered.load(in);
/* 112 */         in.close();
/* 113 */         Config.dbg("[Shaders] Parsing item mappings: " + path);
/* 114 */         ConnectedParser connectedparser = new ConnectedParser("Shaders");
/*     */         
/* 116 */         for (Object e : propertiesOrdered.keySet())
/*     */         {
/* 118 */           String s = (String)e;
/* 119 */           String s1 = propertiesOrdered.getProperty(s);
/* 120 */           String s2 = "item.";
/*     */           
/* 122 */           if (!s.startsWith(s2)) {
/*     */             
/* 124 */             Config.warn("[Shaders] Invalid item ID: " + s);
/*     */             
/*     */             continue;
/*     */           } 
/* 128 */           String s3 = StrUtils.removePrefix(s, s2);
/* 129 */           int i = Config.parseInt(s3, -1);
/*     */           
/* 131 */           if (i < 0) {
/*     */             
/* 133 */             Config.warn("[Shaders] Invalid item alias ID: " + i);
/*     */             
/*     */             continue;
/*     */           } 
/* 137 */           int[] aint = connectedparser.parseItems(s1);
/*     */           
/* 139 */           if (aint != null && aint.length >= 1) {
/*     */             
/* 141 */             for (int j = 0; j < aint.length; j++) {
/*     */               
/* 143 */               int k = aint[j];
/* 144 */               addToList(listItemAliases, k, i);
/*     */             } 
/*     */             
/*     */             continue;
/*     */           } 
/* 149 */           Config.warn("[Shaders] Invalid item ID mapping: " + s + "=" + s1);
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 155 */       catch (IOException var15) {
/*     */         
/* 157 */         Config.warn("[Shaders] Error reading: " + path);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addToList(List<Integer> list, int index, int val) {
/* 164 */     while (list.size() <= index)
/*     */     {
/* 166 */       list.add(Integer.valueOf(-2147483648));
/*     */     }
/*     */     
/* 169 */     list.set(index, Integer.valueOf(val));
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] toArray(List<Integer> list) {
/* 174 */     int[] aint = new int[list.size()];
/*     */     
/* 176 */     for (int i = 0; i < aint.length; i++)
/*     */     {
/* 178 */       aint[i] = ((Integer)list.get(i)).intValue();
/*     */     }
/*     */     
/* 181 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void reset() {
/* 186 */     itemAliases = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\ItemAliases.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */