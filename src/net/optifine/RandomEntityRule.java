/*     */ package net.optifine;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.config.Matches;
/*     */ import net.optifine.config.RangeListInt;
/*     */ import net.optifine.config.VillagerProfession;
/*     */ import net.optifine.config.Weather;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.util.MathUtils;
/*     */ 
/*     */ public class RandomEntityRule {
/*  22 */   private String pathProps = null;
/*  23 */   private ResourceLocation baseResLoc = null;
/*     */   private final int index;
/*  25 */   private int[] textures = null;
/*  26 */   private ResourceLocation[] resourceLocations = null;
/*  27 */   private int[] weights = null;
/*  28 */   private BiomeGenBase[] biomes = null;
/*  29 */   private RangeListInt heights = null;
/*  30 */   private RangeListInt healthRange = null;
/*     */   private boolean healthPercent = false;
/*  32 */   private NbtTagValue nbtName = null;
/*  33 */   public int[] sumWeights = null;
/*  34 */   public int sumAllWeights = 1;
/*  35 */   private VillagerProfession[] professions = null;
/*  36 */   private EnumDyeColor[] collarColors = null;
/*  37 */   private Boolean baby = null;
/*  38 */   private RangeListInt moonPhases = null;
/*  39 */   private RangeListInt dayTimes = null;
/*  40 */   private Weather[] weatherList = null;
/*     */ 
/*     */   
/*     */   public RandomEntityRule(Properties props, String pathProps, ResourceLocation baseResLoc, int index, String valTextures, ConnectedParser cp) {
/*  44 */     this.pathProps = pathProps;
/*  45 */     this.baseResLoc = baseResLoc;
/*  46 */     this.index = index;
/*  47 */     this.textures = cp.parseIntList(valTextures);
/*  48 */     this.weights = cp.parseIntList(props.getProperty("weights." + index));
/*  49 */     this.biomes = cp.parseBiomes(props.getProperty("biomes." + index));
/*  50 */     this.heights = cp.parseRangeListInt(props.getProperty("heights." + index));
/*     */     
/*  52 */     if (this.heights == null)
/*     */     {
/*  54 */       this.heights = parseMinMaxHeight(props, index);
/*     */     }
/*     */     
/*  57 */     String s = props.getProperty("health." + index);
/*     */     
/*  59 */     if (s != null) {
/*     */       
/*  61 */       this.healthPercent = s.contains("%");
/*  62 */       s = s.replace("%", "");
/*  63 */       this.healthRange = cp.parseRangeListInt(s);
/*     */     } 
/*     */     
/*  66 */     this.nbtName = cp.parseNbtTagValue("name", props.getProperty("name." + index));
/*  67 */     this.professions = cp.parseProfessions(props.getProperty("professions." + index));
/*  68 */     this.collarColors = cp.parseDyeColors(props.getProperty("collarColors." + index), "collar color", ConnectedParser.DYE_COLORS_INVALID);
/*  69 */     this.baby = cp.parseBooleanObject(props.getProperty("baby." + index));
/*  70 */     this.moonPhases = cp.parseRangeListInt(props.getProperty("moonPhase." + index));
/*  71 */     this.dayTimes = cp.parseRangeListInt(props.getProperty("dayTime." + index));
/*  72 */     this.weatherList = cp.parseWeather(props.getProperty("weather." + index), "weather." + index, (Weather[])null);
/*     */   }
/*     */ 
/*     */   
/*     */   private RangeListInt parseMinMaxHeight(Properties props, int index) {
/*  77 */     String s = props.getProperty("minHeight." + index);
/*  78 */     String s1 = props.getProperty("maxHeight." + index);
/*     */     
/*  80 */     if (s == null && s1 == null)
/*     */     {
/*  82 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  86 */     int i = 0;
/*     */     
/*  88 */     if (s != null) {
/*     */       
/*  90 */       i = Config.parseInt(s, -1);
/*     */       
/*  92 */       if (i < 0) {
/*     */         
/*  94 */         Config.warn("Invalid minHeight: " + s);
/*  95 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/*  99 */     int j = 256;
/*     */     
/* 101 */     if (s1 != null) {
/*     */       
/* 103 */       j = Config.parseInt(s1, -1);
/*     */       
/* 105 */       if (j < 0) {
/*     */         
/* 107 */         Config.warn("Invalid maxHeight: " + s1);
/* 108 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/* 112 */     if (j < 0) {
/*     */       
/* 114 */       Config.warn("Invalid minHeight, maxHeight: " + s + ", " + s1);
/* 115 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 119 */     RangeListInt rangelistint = new RangeListInt();
/* 120 */     rangelistint.addRange(new RangeInt(i, j));
/* 121 */     return rangelistint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String path) {
/* 128 */     if (this.textures != null && this.textures.length != 0) {
/*     */       
/* 130 */       if (this.resourceLocations != null)
/*     */       {
/* 132 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 136 */       this.resourceLocations = new ResourceLocation[this.textures.length];
/* 137 */       boolean flag = this.pathProps.startsWith("mcpatcher/mob/");
/* 138 */       ResourceLocation resourcelocation = RandomEntities.getLocationRandom(this.baseResLoc, flag);
/*     */       
/* 140 */       if (resourcelocation == null) {
/*     */         
/* 142 */         Config.warn("Invalid path: " + this.baseResLoc.getResourcePath());
/* 143 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 147 */       for (int i = 0; i < this.resourceLocations.length; i++) {
/*     */         
/* 149 */         int j = this.textures[i];
/*     */         
/* 151 */         if (j <= 1) {
/*     */           
/* 153 */           this.resourceLocations[i] = this.baseResLoc;
/*     */         }
/*     */         else {
/*     */           
/* 157 */           ResourceLocation resourcelocation1 = RandomEntities.getLocationIndexed(resourcelocation, j);
/*     */           
/* 159 */           if (resourcelocation1 == null) {
/*     */             
/* 161 */             Config.warn("Invalid path: " + this.baseResLoc.getResourcePath());
/* 162 */             return false;
/*     */           } 
/*     */           
/* 165 */           if (!Config.hasResource(resourcelocation1)) {
/*     */             
/* 167 */             Config.warn("Texture not found: " + resourcelocation1.getResourcePath());
/* 168 */             return false;
/*     */           } 
/*     */           
/* 171 */           this.resourceLocations[i] = resourcelocation1;
/*     */         } 
/*     */       } 
/*     */       
/* 175 */       if (this.weights != null) {
/*     */         
/* 177 */         if (this.weights.length > this.resourceLocations.length) {
/*     */           
/* 179 */           Config.warn("More weights defined than skins, trimming weights: " + path);
/* 180 */           int[] aint = new int[this.resourceLocations.length];
/* 181 */           System.arraycopy(this.weights, 0, aint, 0, aint.length);
/* 182 */           this.weights = aint;
/*     */         } 
/*     */         
/* 185 */         if (this.weights.length < this.resourceLocations.length) {
/*     */           
/* 187 */           Config.warn("Less weights defined than skins, expanding weights: " + path);
/* 188 */           int[] aint1 = new int[this.resourceLocations.length];
/* 189 */           System.arraycopy(this.weights, 0, aint1, 0, this.weights.length);
/* 190 */           int l = MathUtils.getAverage(this.weights);
/*     */           
/* 192 */           for (int j1 = this.weights.length; j1 < aint1.length; j1++)
/*     */           {
/* 194 */             aint1[j1] = l;
/*     */           }
/*     */           
/* 197 */           this.weights = aint1;
/*     */         } 
/*     */         
/* 200 */         this.sumWeights = new int[this.weights.length];
/* 201 */         int k = 0;
/*     */         
/* 203 */         for (int i1 = 0; i1 < this.weights.length; i1++) {
/*     */           
/* 205 */           if (this.weights[i1] < 0) {
/*     */             
/* 207 */             Config.warn("Invalid weight: " + this.weights[i1]);
/* 208 */             return false;
/*     */           } 
/*     */           
/* 211 */           k += this.weights[i1];
/* 212 */           this.sumWeights[i1] = k;
/*     */         } 
/*     */         
/* 215 */         this.sumAllWeights = k;
/*     */         
/* 217 */         if (this.sumAllWeights <= 0) {
/*     */           
/* 219 */           Config.warn("Invalid sum of all weights: " + k);
/* 220 */           this.sumAllWeights = 1;
/*     */         } 
/*     */       } 
/*     */       
/* 224 */       if (this.professions == ConnectedParser.PROFESSIONS_INVALID) {
/*     */         
/* 226 */         Config.warn("Invalid professions or careers: " + path);
/* 227 */         return false;
/*     */       } 
/* 229 */       if (this.collarColors == ConnectedParser.DYE_COLORS_INVALID) {
/*     */         
/* 231 */         Config.warn("Invalid collar colors: " + path);
/* 232 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 236 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 243 */     Config.warn("Invalid skins for rule: " + this.index);
/* 244 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(IRandomEntity randomEntity) {
/* 250 */     if (this.biomes != null && !Matches.biome(randomEntity.getSpawnBiome(), this.biomes))
/*     */     {
/* 252 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 256 */     if (this.heights != null) {
/*     */       
/* 258 */       BlockPos blockpos = randomEntity.getSpawnPosition();
/*     */       
/* 260 */       if (blockpos != null && !this.heights.isInRange(blockpos.getY()))
/*     */       {
/* 262 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 266 */     if (this.healthRange != null) {
/*     */       
/* 268 */       int i1 = randomEntity.getHealth();
/*     */       
/* 270 */       if (this.healthPercent) {
/*     */         
/* 272 */         int i = randomEntity.getMaxHealth();
/*     */         
/* 274 */         if (i > 0)
/*     */         {
/* 276 */           i1 = (int)((i1 * 100) / i);
/*     */         }
/*     */       } 
/*     */       
/* 280 */       if (!this.healthRange.isInRange(i1))
/*     */       {
/* 282 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 286 */     if (this.nbtName != null) {
/*     */       
/* 288 */       String s = randomEntity.getName();
/*     */       
/* 290 */       if (!this.nbtName.matchesValue(s))
/*     */       {
/* 292 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 296 */     if (this.professions != null && randomEntity instanceof RandomEntity) {
/*     */       
/* 298 */       RandomEntity randomentity = (RandomEntity)randomEntity;
/* 299 */       Entity entity = randomentity.getEntity();
/*     */       
/* 301 */       if (entity instanceof EntityVillager) {
/*     */         
/* 303 */         EntityVillager entityvillager = (EntityVillager)entity;
/* 304 */         int j = entityvillager.getProfession();
/* 305 */         int k = Reflector.getFieldValueInt(entityvillager, Reflector.EntityVillager_careerId, -1);
/*     */         
/* 307 */         if (j < 0 || k < 0)
/*     */         {
/* 309 */           return false;
/*     */         }
/*     */         
/* 312 */         boolean flag = false;
/*     */         
/* 314 */         for (int l = 0; l < this.professions.length; l++) {
/*     */           
/* 316 */           VillagerProfession villagerprofession = this.professions[l];
/*     */           
/* 318 */           if (villagerprofession.matches(j, k)) {
/*     */             
/* 320 */             flag = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 325 */         if (!flag)
/*     */         {
/* 327 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 332 */     if (this.collarColors != null && randomEntity instanceof RandomEntity) {
/*     */       
/* 334 */       RandomEntity randomentity1 = (RandomEntity)randomEntity;
/* 335 */       Entity entity1 = randomentity1.getEntity();
/*     */       
/* 337 */       if (entity1 instanceof EntityWolf) {
/*     */         
/* 339 */         EntityWolf entitywolf = (EntityWolf)entity1;
/*     */         
/* 341 */         if (!entitywolf.isTamed())
/*     */         {
/* 343 */           return false;
/*     */         }
/*     */         
/* 346 */         EnumDyeColor enumdyecolor = entitywolf.getCollarColor();
/*     */         
/* 348 */         if (!Config.equalsOne(enumdyecolor, (Object[])this.collarColors))
/*     */         {
/* 350 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 355 */     if (this.baby != null && randomEntity instanceof RandomEntity) {
/*     */       
/* 357 */       RandomEntity randomentity2 = (RandomEntity)randomEntity;
/* 358 */       Entity entity2 = randomentity2.getEntity();
/*     */       
/* 360 */       if (entity2 instanceof EntityLiving) {
/*     */         
/* 362 */         EntityLiving entityliving = (EntityLiving)entity2;
/*     */         
/* 364 */         if (entityliving.isChild() != this.baby.booleanValue())
/*     */         {
/* 366 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 371 */     if (this.moonPhases != null) {
/*     */       
/* 373 */       WorldClient worldClient = (Config.getMinecraft()).theWorld;
/*     */       
/* 375 */       if (worldClient != null) {
/*     */         
/* 377 */         int j1 = worldClient.getMoonPhase();
/*     */         
/* 379 */         if (!this.moonPhases.isInRange(j1))
/*     */         {
/* 381 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 386 */     if (this.dayTimes != null) {
/*     */       
/* 388 */       WorldClient worldClient = (Config.getMinecraft()).theWorld;
/*     */       
/* 390 */       if (worldClient != null) {
/*     */         
/* 392 */         int k1 = (int)worldClient.getWorldInfo().getWorldTime();
/*     */         
/* 394 */         if (!this.dayTimes.isInRange(k1))
/*     */         {
/* 396 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 401 */     if (this.weatherList != null) {
/*     */       
/* 403 */       WorldClient worldClient = (Config.getMinecraft()).theWorld;
/*     */       
/* 405 */       if (worldClient != null) {
/*     */         
/* 407 */         Weather weather = Weather.getWeather((World)worldClient, 0.0F);
/*     */         
/* 409 */         if (!ArrayUtils.contains((Object[])this.weatherList, weather))
/*     */         {
/* 411 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 416 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation(ResourceLocation loc, int randomId) {
/* 422 */     if (this.resourceLocations != null && this.resourceLocations.length != 0) {
/*     */       
/* 424 */       int i = 0;
/*     */       
/* 426 */       if (this.weights == null) {
/*     */         
/* 428 */         i = randomId % this.resourceLocations.length;
/*     */       }
/*     */       else {
/*     */         
/* 432 */         int j = randomId % this.sumAllWeights;
/*     */         
/* 434 */         for (int k = 0; k < this.sumWeights.length; k++) {
/*     */           
/* 436 */           if (this.sumWeights[k] > j) {
/*     */             
/* 438 */             i = k;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 444 */       return this.resourceLocations[i];
/*     */     } 
/*     */ 
/*     */     
/* 448 */     return loc;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\RandomEntityRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */