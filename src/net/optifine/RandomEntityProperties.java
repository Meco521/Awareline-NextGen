/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ 
/*     */ 
/*     */ public class RandomEntityProperties
/*     */ {
/*  13 */   public String name = null;
/*  14 */   public String basePath = null;
/*  15 */   public ResourceLocation[] resourceLocations = null;
/*  16 */   public RandomEntityRule[] rules = null;
/*     */ 
/*     */   
/*     */   public RandomEntityProperties(String path, ResourceLocation[] variants) {
/*  20 */     ConnectedParser connectedparser = new ConnectedParser("RandomEntities");
/*  21 */     this.name = connectedparser.parseName(path);
/*  22 */     this.basePath = connectedparser.parseBasePath(path);
/*  23 */     this.resourceLocations = variants;
/*     */   }
/*     */ 
/*     */   
/*     */   public RandomEntityProperties(Properties props, String path, ResourceLocation baseResLoc) {
/*  28 */     ConnectedParser connectedparser = new ConnectedParser("RandomEntities");
/*  29 */     this.name = connectedparser.parseName(path);
/*  30 */     this.basePath = connectedparser.parseBasePath(path);
/*  31 */     this.rules = parseRules(props, path, baseResLoc, connectedparser);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation(ResourceLocation loc, IRandomEntity randomEntity) {
/*  36 */     if (this.rules != null)
/*     */     {
/*  38 */       for (int i = 0; i < this.rules.length; i++) {
/*     */         
/*  40 */         RandomEntityRule randomentityrule = this.rules[i];
/*     */         
/*  42 */         if (randomentityrule.matches(randomEntity))
/*     */         {
/*  44 */           return randomentityrule.getTextureLocation(loc, randomEntity.getId());
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  49 */     if (this.resourceLocations != null) {
/*     */       
/*  51 */       int j = randomEntity.getId();
/*  52 */       int k = j % this.resourceLocations.length;
/*  53 */       return this.resourceLocations[k];
/*     */     } 
/*     */ 
/*     */     
/*  57 */     return loc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private RandomEntityRule[] parseRules(Properties props, String pathProps, ResourceLocation baseResLoc, ConnectedParser cp) {
/*  63 */     List<RandomEntityRule> list = new ArrayList();
/*  64 */     int i = props.size();
/*     */     
/*  66 */     for (int j = 0; j < i; j++) {
/*     */       
/*  68 */       int k = j + 1;
/*  69 */       String s = props.getProperty("textures." + k);
/*     */       
/*  71 */       if (s == null)
/*     */       {
/*  73 */         s = props.getProperty("skins." + k);
/*     */       }
/*     */       
/*  76 */       if (s != null) {
/*     */         
/*  78 */         RandomEntityRule randomentityrule = new RandomEntityRule(props, pathProps, baseResLoc, k, s, cp);
/*     */         
/*  80 */         if (randomentityrule.isValid(pathProps))
/*     */         {
/*  82 */           list.add(randomentityrule);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  87 */     RandomEntityRule[] arandomentityrule = list.<RandomEntityRule>toArray(new RandomEntityRule[0]);
/*  88 */     return arandomentityrule;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValid(String path) {
/*  93 */     if (this.resourceLocations == null && this.rules == null) {
/*     */       
/*  95 */       Config.warn("No skins specified: " + path);
/*  96 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 100 */     if (this.rules != null)
/*     */     {
/* 102 */       for (int i = 0; i < this.rules.length; i++) {
/*     */         
/* 104 */         RandomEntityRule randomentityrule = this.rules[i];
/*     */         
/* 106 */         if (!randomentityrule.isValid(path))
/*     */         {
/* 108 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 113 */     if (this.resourceLocations != null)
/*     */     {
/* 115 */       for (int j = 0; j < this.resourceLocations.length; j++) {
/*     */         
/* 117 */         ResourceLocation resourcelocation = this.resourceLocations[j];
/*     */         
/* 119 */         if (!Config.hasResource(resourcelocation)) {
/*     */           
/* 121 */           Config.warn("Texture not found: " + resourcelocation.getResourcePath());
/* 122 */           return false;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 127 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDefault() {
/* 133 */     return (this.rules != null) ? false : ((this.resourceLocations == null));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\RandomEntityProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */