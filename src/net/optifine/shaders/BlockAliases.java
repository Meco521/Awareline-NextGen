/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.config.MatchBlock;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorForge;
/*     */ import net.optifine.shaders.config.MacroProcessor;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockAliases
/*     */ {
/*  22 */   private static BlockAlias[][] blockAliases = (BlockAlias[][])null;
/*  23 */   private static PropertiesOrdered blockLayerPropertes = null;
/*     */   
/*     */   private static boolean updateOnResourcesReloaded;
/*     */   
/*     */   public static int getBlockAliasId(int blockId, int metadata) {
/*  28 */     if (blockAliases == null)
/*     */     {
/*  30 */       return blockId;
/*     */     }
/*  32 */     if (blockId >= 0 && blockId < blockAliases.length) {
/*     */       
/*  34 */       BlockAlias[] ablockalias = blockAliases[blockId];
/*     */       
/*  36 */       if (ablockalias == null)
/*     */       {
/*  38 */         return blockId;
/*     */       }
/*     */ 
/*     */       
/*  42 */       for (int i = 0; i < ablockalias.length; i++) {
/*     */         
/*  44 */         BlockAlias blockalias = ablockalias[i];
/*     */         
/*  46 */         if (blockalias.matches(blockId, metadata))
/*     */         {
/*  48 */           return blockalias.getBlockAliasId();
/*     */         }
/*     */       } 
/*     */       
/*  52 */       return blockId;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  57 */     return blockId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void resourcesReloaded() {
/*  63 */     if (updateOnResourcesReloaded) {
/*     */       
/*  65 */       updateOnResourcesReloaded = false;
/*  66 */       update(Shaders.getShaderPack());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update(IShaderPack shaderPack) {
/*  72 */     reset();
/*     */     
/*  74 */     if (shaderPack != null)
/*     */     {
/*  76 */       if (Reflector.Loader_getActiveModList.exists() && Minecraft.getMinecraft().getResourcePackRepository() == null) {
/*     */         
/*  78 */         Config.dbg("[Shaders] Delayed loading of block mappings after resources are loaded");
/*  79 */         updateOnResourcesReloaded = true;
/*     */       }
/*     */       else {
/*     */         
/*  83 */         List<List<BlockAlias>> list = new ArrayList<>();
/*  84 */         String s = "/shaders/block.properties";
/*  85 */         InputStream inputstream = shaderPack.getResourceAsStream(s);
/*     */         
/*  87 */         if (inputstream != null)
/*     */         {
/*  89 */           loadBlockAliases(inputstream, s, list);
/*     */         }
/*     */         
/*  92 */         loadModBlockAliases(list);
/*     */         
/*  94 */         if (!list.isEmpty())
/*     */         {
/*  96 */           blockAliases = toArrays(list);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void loadModBlockAliases(List<List<BlockAlias>> listBlockAliases) {
/* 104 */     String[] astring = ReflectorForge.getForgeModIds();
/*     */     
/* 106 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 108 */       String s = astring[i];
/*     */ 
/*     */       
/*     */       try {
/* 112 */         ResourceLocation resourcelocation = new ResourceLocation(s, "shaders/block.properties");
/* 113 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/* 114 */         loadBlockAliases(inputstream, resourcelocation.toString(), listBlockAliases);
/*     */       }
/* 116 */       catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadBlockAliases(InputStream in, String path, List<List<BlockAlias>> listBlockAliases) {
/* 125 */     if (in != null) {
/*     */       
/*     */       try {
/*     */         
/* 129 */         in = MacroProcessor.process(in, path);
/* 130 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 131 */         propertiesOrdered.load(in);
/* 132 */         in.close();
/* 133 */         Config.dbg("[Shaders] Parsing block mappings: " + path);
/* 134 */         ConnectedParser connectedparser = new ConnectedParser("Shaders");
/*     */         
/* 136 */         for (Object e : propertiesOrdered.keySet())
/*     */         {
/* 138 */           String s = (String)e;
/* 139 */           String s1 = propertiesOrdered.getProperty(s);
/*     */           
/* 141 */           if (s.startsWith("layer.")) {
/*     */             
/* 143 */             if (blockLayerPropertes == null)
/*     */             {
/* 145 */               blockLayerPropertes = new PropertiesOrdered();
/*     */             }
/*     */             
/* 148 */             blockLayerPropertes.put(s, s1);
/*     */             
/*     */             continue;
/*     */           } 
/* 152 */           String s2 = "block.";
/*     */           
/* 154 */           if (!s.startsWith(s2)) {
/*     */             
/* 156 */             Config.warn("[Shaders] Invalid block ID: " + s);
/*     */             
/*     */             continue;
/*     */           } 
/* 160 */           String s3 = StrUtils.removePrefix(s, s2);
/* 161 */           int i = Config.parseInt(s3, -1);
/*     */           
/* 163 */           if (i < 0) {
/*     */             
/* 165 */             Config.warn("[Shaders] Invalid block ID: " + s);
/*     */             
/*     */             continue;
/*     */           } 
/* 169 */           MatchBlock[] amatchblock = connectedparser.parseMatchBlocks(s1);
/*     */           
/* 171 */           if (amatchblock != null && amatchblock.length >= 1) {
/*     */             
/* 173 */             BlockAlias blockalias = new BlockAlias(i, amatchblock);
/* 174 */             addToList(listBlockAliases, blockalias);
/*     */             
/*     */             continue;
/*     */           } 
/* 178 */           Config.warn("[Shaders] Invalid block ID mapping: " + s + "=" + s1);
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 185 */       catch (IOException var14) {
/*     */         
/* 187 */         Config.warn("[Shaders] Error reading: " + path);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addToList(List<List<BlockAlias>> blocksAliases, BlockAlias ba) {
/* 194 */     int[] aint = ba.getMatchBlockIds();
/*     */     
/* 196 */     for (int i = 0; i < aint.length; i++) {
/*     */       
/* 198 */       int j = aint[i];
/*     */       
/* 200 */       while (j >= blocksAliases.size())
/*     */       {
/* 202 */         blocksAliases.add(null);
/*     */       }
/*     */       
/* 205 */       List<BlockAlias> list = blocksAliases.get(j);
/*     */       
/* 207 */       if (list == null) {
/*     */         
/* 209 */         list = new ArrayList<>();
/* 210 */         blocksAliases.set(j, list);
/*     */       } 
/*     */       
/* 213 */       BlockAlias blockalias = new BlockAlias(ba.getBlockAliasId(), ba.getMatchBlocks(j));
/* 214 */       list.add(blockalias);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static BlockAlias[][] toArrays(List<List<BlockAlias>> listBlocksAliases) {
/* 220 */     BlockAlias[][] ablockalias = new BlockAlias[listBlocksAliases.size()][];
/*     */     
/* 222 */     for (int i = 0; i < ablockalias.length; i++) {
/*     */       
/* 224 */       List<BlockAlias> list = listBlocksAliases.get(i);
/*     */       
/* 226 */       if (list != null)
/*     */       {
/* 228 */         ablockalias[i] = list.<BlockAlias>toArray(new BlockAlias[0]);
/*     */       }
/*     */     } 
/*     */     
/* 232 */     return ablockalias;
/*     */   }
/*     */ 
/*     */   
/*     */   public static PropertiesOrdered getBlockLayerPropertes() {
/* 237 */     return blockLayerPropertes;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void reset() {
/* 242 */     blockAliases = (BlockAlias[][])null;
/* 243 */     blockLayerPropertes = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\BlockAliases.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */