/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FlatGeneratorInfo
/*     */ {
/*  16 */   private final List<FlatLayerInfo> flatLayers = Lists.newArrayList();
/*  17 */   private final Map<String, Map<String, String>> worldFeatures = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   private int biomeToUse;
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBiome() {
/*  25 */     return this.biomeToUse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBiome(int biome) {
/*  33 */     this.biomeToUse = biome;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, Map<String, String>> getWorldFeatures() {
/*  38 */     return this.worldFeatures;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<FlatLayerInfo> getFlatLayers() {
/*  43 */     return this.flatLayers;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_82645_d() {
/*  48 */     int i = 0;
/*     */     
/*  50 */     for (FlatLayerInfo flatlayerinfo : this.flatLayers) {
/*     */       
/*  52 */       flatlayerinfo.setMinY(i);
/*  53 */       i += flatlayerinfo.getLayerCount();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  59 */     StringBuilder stringbuilder = new StringBuilder();
/*  60 */     stringbuilder.append(3);
/*  61 */     stringbuilder.append(";");
/*     */     
/*  63 */     for (int i = 0; i < this.flatLayers.size(); i++) {
/*     */       
/*  65 */       if (i > 0)
/*     */       {
/*  67 */         stringbuilder.append(",");
/*     */       }
/*     */       
/*  70 */       stringbuilder.append(((FlatLayerInfo)this.flatLayers.get(i)).toString());
/*     */     } 
/*     */     
/*  73 */     stringbuilder.append(";");
/*  74 */     stringbuilder.append(this.biomeToUse);
/*     */     
/*  76 */     if (!this.worldFeatures.isEmpty()) {
/*     */       
/*  78 */       stringbuilder.append(";");
/*  79 */       int k = 0;
/*     */       
/*  81 */       for (Map.Entry<String, Map<String, String>> entry : this.worldFeatures.entrySet()) {
/*     */         
/*  83 */         if (k++ > 0)
/*     */         {
/*  85 */           stringbuilder.append(",");
/*     */         }
/*     */         
/*  88 */         stringbuilder.append(((String)entry.getKey()).toLowerCase());
/*  89 */         Map<String, String> map = entry.getValue();
/*     */         
/*  91 */         if (!map.isEmpty())
/*     */         {
/*  93 */           stringbuilder.append("(");
/*  94 */           int j = 0;
/*     */           
/*  96 */           for (Map.Entry<String, String> entry1 : map.entrySet()) {
/*     */             
/*  98 */             if (j++ > 0)
/*     */             {
/* 100 */               stringbuilder.append(" ");
/*     */             }
/*     */             
/* 103 */             stringbuilder.append(entry1.getKey());
/* 104 */             stringbuilder.append("=");
/* 105 */             stringbuilder.append(entry1.getValue());
/*     */           } 
/*     */           
/* 108 */           stringbuilder.append(")");
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 114 */       stringbuilder.append(";");
/*     */     } 
/*     */     
/* 117 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static FlatLayerInfo func_180715_a(int p_180715_0_, String p_180715_1_, int p_180715_2_) {
/* 122 */     String[] astring = (p_180715_0_ >= 3) ? p_180715_1_.split("\\*", 2) : p_180715_1_.split("x", 2);
/* 123 */     int i = 1;
/* 124 */     int j = 0;
/*     */     
/* 126 */     if (astring.length == 2) {
/*     */       
/*     */       try {
/*     */         
/* 130 */         i = Integer.parseInt(astring[0]);
/*     */         
/* 132 */         if (p_180715_2_ + i >= 256)
/*     */         {
/* 134 */           i = 256 - p_180715_2_;
/*     */         }
/*     */         
/* 137 */         if (i < 0)
/*     */         {
/* 139 */           i = 0;
/*     */         }
/*     */       }
/* 142 */       catch (Throwable var8) {
/*     */         
/* 144 */         return null;
/*     */       } 
/*     */     }
/*     */     
/* 148 */     Block block = null;
/*     */ 
/*     */     
/*     */     try {
/* 152 */       String s = astring[astring.length - 1];
/*     */       
/* 154 */       if (p_180715_0_ < 3) {
/*     */         
/* 156 */         astring = s.split(":", 2);
/*     */         
/* 158 */         if (astring.length > 1)
/*     */         {
/* 160 */           j = Integer.parseInt(astring[1]);
/*     */         }
/*     */         
/* 163 */         block = Block.getBlockById(Integer.parseInt(astring[0]));
/*     */       }
/*     */       else {
/*     */         
/* 167 */         astring = s.split(":", 3);
/* 168 */         block = (astring.length > 1) ? Block.getBlockFromName(astring[0] + ":" + astring[1]) : null;
/*     */         
/* 170 */         if (block != null) {
/*     */           
/* 172 */           j = (astring.length > 2) ? Integer.parseInt(astring[2]) : 0;
/*     */         }
/*     */         else {
/*     */           
/* 176 */           block = Block.getBlockFromName(astring[0]);
/*     */           
/* 178 */           if (block != null)
/*     */           {
/* 180 */             j = (astring.length > 1) ? Integer.parseInt(astring[1]) : 0;
/*     */           }
/*     */         } 
/*     */         
/* 184 */         if (block == null)
/*     */         {
/* 186 */           return null;
/*     */         }
/*     */       } 
/*     */       
/* 190 */       if (block == Blocks.air)
/*     */       {
/* 192 */         j = 0;
/*     */       }
/*     */       
/* 195 */       if (j < 0 || j > 15)
/*     */       {
/* 197 */         j = 0;
/*     */       }
/*     */     }
/* 200 */     catch (Throwable var9) {
/*     */       
/* 202 */       return null;
/*     */     } 
/*     */     
/* 205 */     FlatLayerInfo flatlayerinfo = new FlatLayerInfo(p_180715_0_, i, block, j);
/* 206 */     flatlayerinfo.setMinY(p_180715_2_);
/* 207 */     return flatlayerinfo;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<FlatLayerInfo> func_180716_a(int p_180716_0_, String p_180716_1_) {
/* 212 */     if (p_180716_1_ != null && p_180716_1_.length() >= 1) {
/*     */       
/* 214 */       List<FlatLayerInfo> list = Lists.newArrayList();
/* 215 */       String[] astring = p_180716_1_.split(",");
/* 216 */       int i = 0;
/*     */       
/* 218 */       for (String s : astring) {
/*     */         
/* 220 */         FlatLayerInfo flatlayerinfo = func_180715_a(p_180716_0_, s, i);
/*     */         
/* 222 */         if (flatlayerinfo == null)
/*     */         {
/* 224 */           return null;
/*     */         }
/*     */         
/* 227 */         list.add(flatlayerinfo);
/* 228 */         i += flatlayerinfo.getLayerCount();
/*     */       } 
/*     */       
/* 231 */       return list;
/*     */     } 
/*     */ 
/*     */     
/* 235 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static FlatGeneratorInfo createFlatGeneratorFromString(String flatGeneratorSettings) {
/* 241 */     if (flatGeneratorSettings == null)
/*     */     {
/* 243 */       return getDefaultFlatGenerator();
/*     */     }
/*     */ 
/*     */     
/* 247 */     String[] astring = flatGeneratorSettings.split(";", -1);
/* 248 */     int i = (astring.length == 1) ? 0 : MathHelper.parseIntWithDefault(astring[0], 0);
/*     */     
/* 250 */     if (i >= 0 && i <= 3) {
/*     */       
/* 252 */       FlatGeneratorInfo flatgeneratorinfo = new FlatGeneratorInfo();
/* 253 */       int j = (astring.length == 1) ? 0 : 1;
/* 254 */       List<FlatLayerInfo> list = func_180716_a(i, astring[j++]);
/*     */       
/* 256 */       if (list != null && !list.isEmpty()) {
/*     */         
/* 258 */         flatgeneratorinfo.flatLayers.addAll(list);
/* 259 */         flatgeneratorinfo.func_82645_d();
/* 260 */         int k = BiomeGenBase.plains.biomeID;
/*     */         
/* 262 */         if (i > 0 && astring.length > j)
/*     */         {
/* 264 */           k = MathHelper.parseIntWithDefault(astring[j++], k);
/*     */         }
/*     */         
/* 267 */         flatgeneratorinfo.biomeToUse = k;
/*     */         
/* 269 */         if (i > 0 && astring.length > j) {
/*     */           
/* 271 */           String[] astring1 = astring[j++].toLowerCase().split(",");
/*     */           
/* 273 */           for (String s : astring1) {
/*     */             
/* 275 */             String[] astring2 = s.split("\\(", 2);
/* 276 */             Map<String, String> map = Maps.newHashMap();
/*     */             
/* 278 */             if (!astring2[0].isEmpty()) {
/*     */               
/* 280 */               flatgeneratorinfo.worldFeatures.put(astring2[0], map);
/*     */               
/* 282 */               if (astring2.length > 1 && !astring2[1].isEmpty() && astring2[1].charAt(astring2[1].length() - 1) == ')' && astring2[1].length() > 1) {
/*     */                 
/* 284 */                 String[] astring3 = astring2[1].substring(0, astring2[1].length() - 1).split(" ");
/*     */                 
/* 286 */                 for (int l = 0; l < astring3.length; l++)
/*     */                 {
/* 288 */                   String[] astring4 = astring3[l].split("=", 2);
/*     */                   
/* 290 */                   if (astring4.length == 2)
/*     */                   {
/* 292 */                     map.put(astring4[0], astring4[1]);
/*     */                   }
/*     */                 }
/*     */               
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } else {
/*     */           
/* 301 */           flatgeneratorinfo.worldFeatures.put("village", Maps.newHashMap());
/*     */         } 
/*     */         
/* 304 */         return flatgeneratorinfo;
/*     */       } 
/*     */ 
/*     */       
/* 308 */       return getDefaultFlatGenerator();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 313 */     return getDefaultFlatGenerator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FlatGeneratorInfo getDefaultFlatGenerator() {
/* 320 */     FlatGeneratorInfo flatgeneratorinfo = new FlatGeneratorInfo();
/* 321 */     flatgeneratorinfo.biomeToUse = BiomeGenBase.plains.biomeID;
/* 322 */     flatgeneratorinfo.flatLayers.add(new FlatLayerInfo(1, Blocks.bedrock));
/* 323 */     flatgeneratorinfo.flatLayers.add(new FlatLayerInfo(2, Blocks.dirt));
/* 324 */     flatgeneratorinfo.flatLayers.add(new FlatLayerInfo(1, (Block)Blocks.grass));
/* 325 */     flatgeneratorinfo.func_82645_d();
/* 326 */     flatgeneratorinfo.worldFeatures.put("village", Maps.newHashMap());
/* 327 */     return flatgeneratorinfo;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\FlatGeneratorInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */