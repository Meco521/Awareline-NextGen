/*     */ package net.optifine;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorRaw;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ import net.optifine.util.ResUtils;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ public class RandomEntities {
/*  27 */   private static final Map<String, RandomEntityProperties> mapProperties = new HashMap<>();
/*     */   private static boolean active = false;
/*     */   private static RenderGlobal renderGlobal;
/*  30 */   private static final RandomEntity randomEntity = new RandomEntity();
/*     */   private static TileEntityRendererDispatcher tileEntityRendererDispatcher;
/*  32 */   private static final RandomTileEntity randomTileEntity = new RandomTileEntity();
/*     */   private static boolean working = false;
/*     */   public static final String SUFFIX_PNG = ".png";
/*     */   public static final String SUFFIX_PROPERTIES = ".properties";
/*     */   public static final String PREFIX_TEXTURES_ENTITY = "textures/entity/";
/*     */   public static final String PREFIX_TEXTURES_PAINTING = "textures/painting/";
/*     */   public static final String PREFIX_TEXTURES = "textures/";
/*     */   public static final String PREFIX_OPTIFINE_RANDOM = "optifine/random/";
/*     */   public static final String PREFIX_MCPATCHER_MOB = "mcpatcher/mob/";
/*  41 */   private static final String[] DEPENDANT_SUFFIXES = new String[] { "_armor", "_eyes", "_exploding", "_shooting", "_fur", "_eyes", "_invulnerable", "_angry", "_tame", "_collar" };
/*     */   private static final String PREFIX_DYNAMIC_TEXTURE_HORSE = "horse/";
/*  43 */   private static final String[] HORSE_TEXTURES = (String[])ReflectorRaw.getFieldValue(null, EntityHorse.class, String[].class, 2);
/*  44 */   private static final String[] HORSE_TEXTURES_ABBR = (String[])ReflectorRaw.getFieldValue(null, EntityHorse.class, String[].class, 3);
/*     */ 
/*     */   
/*     */   public static void entityLoaded(Entity entity, World world) {
/*  48 */     if (world != null) {
/*     */       
/*  50 */       DataWatcher datawatcher = entity.getDataWatcher();
/*  51 */       datawatcher.spawnPosition = entity.getPosition();
/*  52 */       datawatcher.spawnBiome = world.getBiomeGenForCoords(datawatcher.spawnPosition);
/*  53 */       UUID uuid = entity.getUniqueID();
/*     */       
/*  55 */       if (entity instanceof EntityVillager)
/*     */       {
/*  57 */         updateEntityVillager(uuid, (EntityVillager)entity);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void entityUnloaded(Entity entity, World world) {}
/*     */ 
/*     */   
/*     */   private static void updateEntityVillager(UUID uuid, EntityVillager ev) {
/*  68 */     Entity entity = IntegratedServerUtils.getEntity(uuid);
/*     */     
/*  70 */     if (entity instanceof EntityVillager) {
/*     */       
/*  72 */       EntityVillager entityvillager = (EntityVillager)entity;
/*  73 */       int i = entityvillager.getProfession();
/*  74 */       ev.setProfession(i);
/*  75 */       int j = Reflector.getFieldValueInt(entityvillager, Reflector.EntityVillager_careerId, 0);
/*  76 */       Reflector.setFieldValueInt(ev, Reflector.EntityVillager_careerId, j);
/*  77 */       int k = Reflector.getFieldValueInt(entityvillager, Reflector.EntityVillager_careerLevel, 0);
/*  78 */       Reflector.setFieldValueInt(ev, Reflector.EntityVillager_careerLevel, k);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void worldChanged(World oldWorld, World newWorld) {
/*  84 */     if (newWorld != null) {
/*     */       
/*  86 */       List<Entity> list = newWorld.getLoadedEntityList();
/*     */       
/*  88 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/*  90 */         Entity entity = list.get(i);
/*  91 */         entityLoaded(entity, newWorld);
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     randomEntity.setEntity((Entity)null);
/*  96 */     randomTileEntity.setTileEntity((TileEntity)null);
/*     */   }
/*     */   
/*     */   public static ResourceLocation getTextureLocation(ResourceLocation loc) {
/*     */     ResourceLocation name;
/* 101 */     if (!active)
/*     */     {
/* 103 */       return loc;
/*     */     }
/* 105 */     if (working)
/*     */     {
/* 107 */       return loc;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 115 */       working = true;
/* 116 */       IRandomEntity irandomentity = getRandomEntityRendered();
/*     */       
/* 118 */       if (irandomentity != null) {
/*     */         
/* 120 */         String s = loc.getResourcePath();
/*     */         
/* 122 */         if (s.startsWith("horse/"))
/*     */         {
/* 124 */           s = getHorseTexturePath(s, "horse/".length());
/*     */         }
/*     */         
/* 127 */         if (!s.startsWith("textures/entity/") && !s.startsWith("textures/painting/")) {
/*     */           
/* 129 */           ResourceLocation resourcelocation2 = loc;
/* 130 */           return resourcelocation2;
/*     */         } 
/*     */         
/* 133 */         RandomEntityProperties randomentityproperties = mapProperties.get(s);
/*     */         
/* 135 */         if (randomentityproperties == null) {
/*     */           
/* 137 */           ResourceLocation resourcelocation3 = loc;
/* 138 */           return resourcelocation3;
/*     */         } 
/*     */         
/* 141 */         ResourceLocation resourcelocation1 = randomentityproperties.getTextureLocation(loc, irandomentity);
/* 142 */         return resourcelocation1;
/*     */       } 
/*     */       
/* 145 */       name = loc;
/*     */     }
/*     */     finally {
/*     */       
/* 149 */       working = false;
/*     */     } 
/*     */     
/* 152 */     return name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getHorseTexturePath(String path, int pos) {
/* 158 */     if (HORSE_TEXTURES != null && HORSE_TEXTURES_ABBR != null) {
/*     */       
/* 160 */       for (int i = 0; i < HORSE_TEXTURES_ABBR.length; i++) {
/*     */         
/* 162 */         String s = HORSE_TEXTURES_ABBR[i];
/*     */         
/* 164 */         if (path.startsWith(s, pos))
/*     */         {
/* 166 */           return HORSE_TEXTURES[i];
/*     */         }
/*     */       } 
/*     */       
/* 170 */       return path;
/*     */     } 
/*     */ 
/*     */     
/* 174 */     return path;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static IRandomEntity getRandomEntityRendered() {
/* 180 */     if (renderGlobal.renderedEntity != null) {
/*     */       
/* 182 */       randomEntity.setEntity(renderGlobal.renderedEntity);
/* 183 */       return randomEntity;
/*     */     } 
/*     */ 
/*     */     
/* 187 */     if (tileEntityRendererDispatcher.tileEntityRendered != null) {
/*     */       
/* 189 */       TileEntity tileentity = tileEntityRendererDispatcher.tileEntityRendered;
/*     */       
/* 191 */       if (tileentity.getWorld() != null) {
/*     */         
/* 193 */         randomTileEntity.setTileEntity(tileentity);
/* 194 */         return randomTileEntity;
/*     */       } 
/*     */     } 
/*     */     
/* 198 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static RandomEntityProperties makeProperties(ResourceLocation loc, boolean mcpatcher) {
/* 204 */     String s = loc.getResourcePath();
/* 205 */     ResourceLocation resourcelocation = getLocationProperties(loc, mcpatcher);
/*     */     
/* 207 */     if (resourcelocation != null) {
/*     */       
/* 209 */       RandomEntityProperties randomentityproperties = parseProperties(resourcelocation, loc);
/*     */       
/* 211 */       if (randomentityproperties != null)
/*     */       {
/* 213 */         return randomentityproperties;
/*     */       }
/*     */     } 
/*     */     
/* 217 */     ResourceLocation[] aresourcelocation = getLocationsVariants(loc, mcpatcher);
/* 218 */     return (aresourcelocation == null) ? null : new RandomEntityProperties(s, aresourcelocation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static RandomEntityProperties parseProperties(ResourceLocation propLoc, ResourceLocation resLoc) {
/*     */     try {
/* 225 */       String s = propLoc.getResourcePath();
/* 226 */       dbg(resLoc.getResourcePath() + ", properties: " + s);
/* 227 */       InputStream inputstream = Config.getResourceStream(propLoc);
/*     */       
/* 229 */       if (inputstream == null) {
/*     */         
/* 231 */         warn("Properties not found: " + s);
/* 232 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 236 */       PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 237 */       propertiesOrdered.load(inputstream);
/* 238 */       inputstream.close();
/* 239 */       RandomEntityProperties randomentityproperties = new RandomEntityProperties((Properties)propertiesOrdered, s, resLoc);
/* 240 */       return !randomentityproperties.isValid(s) ? null : randomentityproperties;
/*     */     
/*     */     }
/* 243 */     catch (FileNotFoundException var6) {
/*     */       
/* 245 */       warn("File not found: " + resLoc.getResourcePath());
/* 246 */       return null;
/*     */     }
/* 248 */     catch (IOException ioexception) {
/*     */       
/* 250 */       ioexception.printStackTrace();
/* 251 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static ResourceLocation getLocationProperties(ResourceLocation loc, boolean mcpatcher) {
/* 257 */     ResourceLocation resourcelocation = getLocationRandom(loc, mcpatcher);
/*     */     
/* 259 */     if (resourcelocation == null)
/*     */     {
/* 261 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 265 */     String s = resourcelocation.getResourceDomain();
/* 266 */     String s1 = resourcelocation.getResourcePath();
/* 267 */     String s2 = StrUtils.removeSuffix(s1, ".png");
/* 268 */     String s3 = s2 + ".properties";
/* 269 */     ResourceLocation resourcelocation1 = new ResourceLocation(s, s3);
/*     */     
/* 271 */     if (Config.hasResource(resourcelocation1))
/*     */     {
/* 273 */       return resourcelocation1;
/*     */     }
/*     */ 
/*     */     
/* 277 */     String s4 = getParentTexturePath(s2);
/*     */     
/* 279 */     if (s4 == null)
/*     */     {
/* 281 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 285 */     ResourceLocation resourcelocation2 = new ResourceLocation(s, s4 + ".properties");
/* 286 */     return Config.hasResource(resourcelocation2) ? resourcelocation2 : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static ResourceLocation getLocationRandom(ResourceLocation loc, boolean mcpatcher) {
/* 294 */     String s = loc.getResourceDomain();
/* 295 */     String s1 = loc.getResourcePath();
/* 296 */     String s2 = "textures/";
/* 297 */     String s3 = "optifine/random/";
/*     */     
/* 299 */     if (mcpatcher) {
/*     */       
/* 301 */       s2 = "textures/entity/";
/* 302 */       s3 = "mcpatcher/mob/";
/*     */     } 
/*     */     
/* 305 */     if (!s1.startsWith(s2))
/*     */     {
/* 307 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 311 */     String s4 = StrUtils.replacePrefix(s1, s2, s3);
/* 312 */     return new ResourceLocation(s, s4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getPathBase(String pathRandom) {
/* 318 */     return pathRandom.startsWith("optifine/random/") ? StrUtils.replacePrefix(pathRandom, "optifine/random/", "textures/") : (pathRandom.startsWith("mcpatcher/mob/") ? StrUtils.replacePrefix(pathRandom, "mcpatcher/mob/", "textures/entity/") : null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static ResourceLocation getLocationIndexed(ResourceLocation loc, int index) {
/* 323 */     if (loc == null)
/*     */     {
/* 325 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 329 */     String s = loc.getResourcePath();
/* 330 */     int i = s.lastIndexOf('.');
/*     */     
/* 332 */     if (i < 0)
/*     */     {
/* 334 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 338 */     String s1 = s.substring(0, i);
/* 339 */     String s2 = s.substring(i);
/* 340 */     String s3 = s1 + index + s2;
/* 341 */     ResourceLocation resourcelocation = new ResourceLocation(loc.getResourceDomain(), s3);
/* 342 */     return resourcelocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getParentTexturePath(String path) {
/* 349 */     for (int i = 0; i < DEPENDANT_SUFFIXES.length; i++) {
/*     */       
/* 351 */       String s = DEPENDANT_SUFFIXES[i];
/*     */       
/* 353 */       if (path.endsWith(s)) {
/*     */         
/* 355 */         String s1 = StrUtils.removeSuffix(path, s);
/* 356 */         return s1;
/*     */       } 
/*     */     } 
/*     */     
/* 360 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static ResourceLocation[] getLocationsVariants(ResourceLocation loc, boolean mcpatcher) {
/* 365 */     List<ResourceLocation> list = new ArrayList();
/* 366 */     list.add(loc);
/* 367 */     ResourceLocation resourcelocation = getLocationRandom(loc, mcpatcher);
/*     */     
/* 369 */     if (resourcelocation == null)
/*     */     {
/* 371 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 375 */     for (int i = 1; i < list.size() + 10; i++) {
/*     */       
/* 377 */       int j = i + 1;
/* 378 */       ResourceLocation resourcelocation1 = getLocationIndexed(resourcelocation, j);
/*     */       
/* 380 */       if (Config.hasResource(resourcelocation1))
/*     */       {
/* 382 */         list.add(resourcelocation1);
/*     */       }
/*     */     } 
/*     */     
/* 386 */     if (list.size() <= 1)
/*     */     {
/* 388 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 392 */     ResourceLocation[] aresourcelocation = list.<ResourceLocation>toArray(new ResourceLocation[0]);
/* 393 */     dbg(loc.getResourcePath() + ", variants: " + aresourcelocation.length);
/* 394 */     return aresourcelocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void update() {
/* 401 */     mapProperties.clear();
/* 402 */     active = false;
/*     */     
/* 404 */     if (Config.isRandomEntities())
/*     */     {
/* 406 */       initialize();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initialize() {
/* 412 */     renderGlobal = Config.getRenderGlobal();
/* 413 */     tileEntityRendererDispatcher = TileEntityRendererDispatcher.instance;
/* 414 */     String[] astring = { "optifine/random/", "mcpatcher/mob/" };
/* 415 */     String[] astring1 = { ".png", ".properties" };
/* 416 */     String[] astring2 = ResUtils.collectFiles(astring, astring1);
/* 417 */     Set<String> set = new HashSet();
/*     */     
/* 419 */     for (int i = 0; i < astring2.length; i++) {
/*     */       
/* 421 */       String s = astring2[i];
/* 422 */       s = StrUtils.removeSuffix(s, astring1);
/* 423 */       s = StrUtils.trimTrailing(s, "0123456789");
/* 424 */       s = s + ".png";
/* 425 */       String s1 = getPathBase(s);
/*     */       
/* 427 */       if (!set.contains(s1)) {
/*     */         
/* 429 */         set.add(s1);
/* 430 */         ResourceLocation resourcelocation = new ResourceLocation(s1);
/*     */         
/* 432 */         if (Config.hasResource(resourcelocation)) {
/*     */           
/* 434 */           RandomEntityProperties randomentityproperties = mapProperties.get(s1);
/*     */           
/* 436 */           if (randomentityproperties == null) {
/*     */             
/* 438 */             randomentityproperties = makeProperties(resourcelocation, false);
/*     */             
/* 440 */             if (randomentityproperties == null)
/*     */             {
/* 442 */               randomentityproperties = makeProperties(resourcelocation, true);
/*     */             }
/*     */             
/* 445 */             if (randomentityproperties != null)
/*     */             {
/* 447 */               mapProperties.put(s1, randomentityproperties);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 454 */     active = !mapProperties.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void dbg(String str) {
/* 459 */     Config.dbg("RandomEntities: " + str);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void warn(String str) {
/* 464 */     Config.warn("RandomEntities: " + str);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\RandomEntities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */