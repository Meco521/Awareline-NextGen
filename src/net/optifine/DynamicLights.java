/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.monster.EntityBlaze;
/*     */ import net.minecraft.entity.monster.EntityCreeper;
/*     */ import net.minecraft.entity.monster.EntityMagmaCube;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.config.EntityClassLocator;
/*     */ import net.optifine.config.IObjectLocator;
/*     */ import net.optifine.config.ItemLocator;
/*     */ import net.optifine.reflect.ReflectorForge;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DynamicLights
/*     */ {
/*  39 */   private static final DynamicLightsMap mapDynamicLights = new DynamicLightsMap();
/*  40 */   private static final Map<Class, Integer> mapEntityLightLevels = (Map)new HashMap<>();
/*  41 */   private static final Map<Item, Integer> mapItemLightLevels = new HashMap<>();
/*  42 */   private static long timeUpdateMs = 0L;
/*     */   
/*     */   private static final double MAX_DIST = 7.5D;
/*     */   
/*     */   private static final double MAX_DIST_SQ = 56.25D;
/*     */   
/*     */   private static final int LIGHT_LEVEL_MAX = 15;
/*     */   private static final int LIGHT_LEVEL_FIRE = 15;
/*     */   private static final int LIGHT_LEVEL_BLAZE = 10;
/*     */   private static final int LIGHT_LEVEL_MAGMA_CUBE = 8;
/*     */   private static final int LIGHT_LEVEL_MAGMA_CUBE_CORE = 13;
/*     */   private static final int LIGHT_LEVEL_GLOWSTONE_DUST = 8;
/*     */   private static final int LIGHT_LEVEL_PRISMARINE_CRYSTALS = 8;
/*     */   private static boolean initialized;
/*     */   
/*     */   public static void entityAdded(Entity entityIn, RenderGlobal renderGlobal) {}
/*     */   
/*     */   public static void entityRemoved(Entity entityIn, RenderGlobal renderGlobal) {
/*  60 */     synchronized (mapDynamicLights) {
/*     */       
/*  62 */       DynamicLight dynamiclight = mapDynamicLights.remove(entityIn.getEntityId());
/*     */       
/*  64 */       if (dynamiclight != null)
/*     */       {
/*  66 */         dynamiclight.updateLitChunks(renderGlobal);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update(RenderGlobal renderGlobal) {
/*  73 */     long i = System.currentTimeMillis();
/*     */     
/*  75 */     if (i >= timeUpdateMs + 50L) {
/*     */       
/*  77 */       timeUpdateMs = i;
/*     */       
/*  79 */       if (!initialized)
/*     */       {
/*  81 */         initialize();
/*     */       }
/*     */       
/*  84 */       synchronized (mapDynamicLights) {
/*     */         
/*  86 */         updateMapDynamicLights(renderGlobal);
/*     */         
/*  88 */         if (mapDynamicLights.size() > 0) {
/*     */           
/*  90 */           List<DynamicLight> list = mapDynamicLights.valueList();
/*     */           
/*  92 */           for (int j = 0; j < list.size(); j++) {
/*     */             
/*  94 */             DynamicLight dynamiclight = list.get(j);
/*  95 */             dynamiclight.update(renderGlobal);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initialize() {
/* 104 */     initialized = true;
/* 105 */     mapEntityLightLevels.clear();
/* 106 */     mapItemLightLevels.clear();
/* 107 */     String[] astring = ReflectorForge.getForgeModIds();
/*     */     
/* 109 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 111 */       String s = astring[i];
/*     */ 
/*     */       
/*     */       try {
/* 115 */         ResourceLocation resourcelocation = new ResourceLocation(s, "optifine/dynamic_lights.properties");
/* 116 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/* 117 */         loadModConfiguration(inputstream, resourcelocation.toString(), s);
/*     */       }
/* 119 */       catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     if (!mapEntityLightLevels.isEmpty())
/*     */     {
/* 127 */       Config.dbg("DynamicLights entities: " + mapEntityLightLevels.size());
/*     */     }
/*     */     
/* 130 */     if (!mapItemLightLevels.isEmpty())
/*     */     {
/* 132 */       Config.dbg("DynamicLights items: " + mapItemLightLevels.size());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void loadModConfiguration(InputStream in, String path, String modId) {
/* 138 */     if (in != null) {
/*     */       
/*     */       try {
/*     */         
/* 142 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 143 */         propertiesOrdered.load(in);
/* 144 */         in.close();
/* 145 */         Config.dbg("DynamicLights: Parsing " + path);
/* 146 */         ConnectedParser connectedparser = new ConnectedParser("DynamicLights");
/* 147 */         loadModLightLevels(propertiesOrdered.getProperty("entities"), mapEntityLightLevels, (IObjectLocator)new EntityClassLocator(), connectedparser, path, modId);
/* 148 */         loadModLightLevels(propertiesOrdered.getProperty("items"), mapItemLightLevels, (IObjectLocator)new ItemLocator(), connectedparser, path, modId);
/*     */       }
/* 150 */       catch (IOException var5) {
/*     */         
/* 152 */         Config.warn("DynamicLights: Error reading " + path);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void loadModLightLevels(String prop, Map<Object, Integer> mapLightLevels, IObjectLocator ol, ConnectedParser cp, String path, String modId) {
/* 159 */     if (prop != null) {
/*     */       
/* 161 */       String[] astring = Config.tokenize(prop, " ");
/*     */       
/* 163 */       for (int i = 0; i < astring.length; i++) {
/*     */         
/* 165 */         String s = astring[i];
/* 166 */         String[] astring1 = Config.tokenize(s, ":");
/*     */         
/* 168 */         if (astring1.length != 2) {
/*     */           
/* 170 */           cp.warn("Invalid entry: " + s + ", in:" + path);
/*     */         }
/*     */         else {
/*     */           
/* 174 */           String s1 = astring1[0];
/* 175 */           String s2 = astring1[1];
/* 176 */           String s3 = modId + ":" + s1;
/* 177 */           ResourceLocation resourcelocation = new ResourceLocation(s3);
/* 178 */           Object object = ol.getObject(resourcelocation);
/*     */           
/* 180 */           if (object == null) {
/*     */             
/* 182 */             cp.warn("Object not found: " + s3);
/*     */           }
/*     */           else {
/*     */             
/* 186 */             int j = cp.parseInt(s2, -1);
/*     */             
/* 188 */             if (j >= 0 && j <= 15) {
/*     */               
/* 190 */               mapLightLevels.put(object, new Integer(j));
/*     */             }
/*     */             else {
/*     */               
/* 194 */               cp.warn("Invalid light level: " + s);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void updateMapDynamicLights(RenderGlobal renderGlobal) {
/* 204 */     WorldClient worldClient = renderGlobal.getWorld();
/*     */     
/* 206 */     if (worldClient != null)
/*     */     {
/* 208 */       for (Entity entity : worldClient.getLoadedEntityList()) {
/*     */         
/* 210 */         int i = getLightLevel(entity);
/*     */         
/* 212 */         if (i > 0) {
/*     */           
/* 214 */           int j = entity.getEntityId();
/* 215 */           DynamicLight dynamiclight = mapDynamicLights.get(j);
/*     */           
/* 217 */           if (dynamiclight == null) {
/*     */             
/* 219 */             dynamiclight = new DynamicLight(entity);
/* 220 */             mapDynamicLights.put(j, dynamiclight);
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/* 225 */         int k = entity.getEntityId();
/* 226 */         DynamicLight dynamiclight1 = mapDynamicLights.remove(k);
/*     */         
/* 228 */         if (dynamiclight1 != null)
/*     */         {
/* 230 */           dynamiclight1.updateLitChunks(renderGlobal);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getCombinedLight(BlockPos pos, int combinedLight) {
/* 239 */     double d0 = getLightLevel(pos);
/* 240 */     combinedLight = getCombinedLight(d0, combinedLight);
/* 241 */     return combinedLight;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getCombinedLight(Entity entity, int combinedLight) {
/* 246 */     double d0 = getLightLevel(entity);
/* 247 */     combinedLight = getCombinedLight(d0, combinedLight);
/* 248 */     return combinedLight;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getCombinedLight(double lightPlayer, int combinedLight) {
/* 253 */     if (lightPlayer > 0.0D) {
/*     */       
/* 255 */       int i = (int)(lightPlayer * 16.0D);
/* 256 */       int j = combinedLight & 0xFF;
/*     */       
/* 258 */       if (i > j) {
/*     */         
/* 260 */         combinedLight &= 0xFFFFFF00;
/* 261 */         combinedLight |= i;
/*     */       } 
/*     */     } 
/*     */     
/* 265 */     return combinedLight;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getLightLevel(BlockPos pos) {
/* 270 */     double d0 = 0.0D;
/*     */     
/* 272 */     synchronized (mapDynamicLights) {
/*     */       
/* 274 */       List<DynamicLight> list = mapDynamicLights.valueList();
/* 275 */       int i = list.size();
/*     */       
/* 277 */       for (int j = 0; j < i; j++) {
/*     */         
/* 279 */         DynamicLight dynamiclight = list.get(j);
/* 280 */         int k = dynamiclight.getLastLightLevel();
/*     */         
/* 282 */         if (k > 0) {
/*     */           
/* 284 */           double d1 = dynamiclight.getLastPosX();
/* 285 */           double d2 = dynamiclight.getLastPosY();
/* 286 */           double d3 = dynamiclight.getLastPosZ();
/* 287 */           double d4 = pos.getX() - d1;
/* 288 */           double d5 = pos.getY() - d2;
/* 289 */           double d6 = pos.getZ() - d3;
/* 290 */           double d7 = d4 * d4 + d5 * d5 + d6 * d6;
/*     */           
/* 292 */           if (dynamiclight.isUnderwater() && !Config.isClearWater()) {
/*     */             
/* 294 */             k = Config.limit(k - 2, 0, 15);
/* 295 */             d7 *= 2.0D;
/*     */           } 
/*     */           
/* 298 */           if (d7 <= 56.25D) {
/*     */             
/* 300 */             double d8 = Math.sqrt(d7);
/* 301 */             double d9 = 1.0D - d8 / 7.5D;
/* 302 */             double d10 = d9 * k;
/*     */             
/* 304 */             if (d10 > d0)
/*     */             {
/* 306 */               d0 = d10;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 313 */     double d11 = Config.limit(d0, 0.0D, 15.0D);
/* 314 */     return d11;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getLightLevel(ItemStack itemStack) {
/* 319 */     if (itemStack == null)
/*     */     {
/* 321 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 325 */     Item item = itemStack.getItem();
/*     */     
/* 327 */     if (item instanceof ItemBlock) {
/*     */       
/* 329 */       ItemBlock itemblock = (ItemBlock)item;
/* 330 */       Block block = itemblock.getBlock();
/*     */       
/* 332 */       if (block != null)
/*     */       {
/* 334 */         return block.getLightValue();
/*     */       }
/*     */     } 
/*     */     
/* 338 */     if (item == Items.lava_bucket)
/*     */     {
/* 340 */       return Blocks.lava.getLightValue();
/*     */     }
/* 342 */     if (item != Items.blaze_rod && item != Items.blaze_powder) {
/*     */       
/* 344 */       if (item == Items.glowstone_dust)
/*     */       {
/* 346 */         return 8;
/*     */       }
/* 348 */       if (item == Items.prismarine_crystals)
/*     */       {
/* 350 */         return 8;
/*     */       }
/* 352 */       if (item == Items.magma_cream)
/*     */       {
/* 354 */         return 8;
/*     */       }
/* 356 */       if (item == Items.nether_star)
/*     */       {
/* 358 */         return Blocks.beacon.getLightValue() / 2;
/*     */       }
/*     */ 
/*     */       
/* 362 */       if (!mapItemLightLevels.isEmpty()) {
/*     */         
/* 364 */         Integer integer = mapItemLightLevels.get(item);
/*     */         
/* 366 */         if (integer != null)
/*     */         {
/* 368 */           return integer.intValue();
/*     */         }
/*     */       } 
/*     */       
/* 372 */       return 0;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 377 */     return 10;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLightLevel(Entity entity) {
/* 384 */     if (entity == Config.getMinecraft().getRenderViewEntity() && !Config.isDynamicHandLight())
/*     */     {
/* 386 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 390 */     if (entity instanceof EntityPlayer) {
/*     */       
/* 392 */       EntityPlayer entityplayer = (EntityPlayer)entity;
/*     */       
/* 394 */       if (entityplayer.isSpectator())
/*     */       {
/* 396 */         return 0;
/*     */       }
/*     */     } 
/*     */     
/* 400 */     if (entity.isBurning())
/*     */     {
/* 402 */       return 15;
/*     */     }
/*     */ 
/*     */     
/* 406 */     if (!mapEntityLightLevels.isEmpty()) {
/*     */       
/* 408 */       Integer integer = mapEntityLightLevels.get(entity.getClass());
/*     */       
/* 410 */       if (integer != null)
/*     */       {
/* 412 */         return integer.intValue();
/*     */       }
/*     */     } 
/*     */     
/* 416 */     if (entity instanceof net.minecraft.entity.projectile.EntityFireball)
/*     */     {
/* 418 */       return 15;
/*     */     }
/* 420 */     if (entity instanceof net.minecraft.entity.item.EntityTNTPrimed)
/*     */     {
/* 422 */       return 15;
/*     */     }
/* 424 */     if (entity instanceof EntityBlaze) {
/*     */       
/* 426 */       EntityBlaze entityblaze = (EntityBlaze)entity;
/* 427 */       return entityblaze.func_70845_n() ? 15 : 10;
/*     */     } 
/* 429 */     if (entity instanceof EntityMagmaCube) {
/*     */       
/* 431 */       EntityMagmaCube entitymagmacube = (EntityMagmaCube)entity;
/* 432 */       return (entitymagmacube.squishFactor > 0.6D) ? 13 : 8;
/*     */     } 
/*     */ 
/*     */     
/* 436 */     if (entity instanceof EntityCreeper) {
/*     */       
/* 438 */       EntityCreeper entitycreeper = (EntityCreeper)entity;
/*     */       
/* 440 */       if (entitycreeper.getCreeperFlashIntensity(0.0F) > 0.001D)
/*     */       {
/* 442 */         return 15;
/*     */       }
/*     */     } 
/*     */     
/* 446 */     if (entity instanceof EntityLivingBase) {
/*     */       
/* 448 */       EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
/* 449 */       ItemStack itemstack2 = entitylivingbase.getHeldItem();
/* 450 */       int i = getLightLevel(itemstack2);
/* 451 */       ItemStack itemstack1 = entitylivingbase.getEquipmentInSlot(4);
/* 452 */       int j = getLightLevel(itemstack1);
/* 453 */       return Math.max(i, j);
/*     */     } 
/* 455 */     if (entity instanceof EntityItem) {
/*     */       
/* 457 */       EntityItem entityitem = (EntityItem)entity;
/* 458 */       ItemStack itemstack = getItemStack(entityitem);
/* 459 */       return getLightLevel(itemstack);
/*     */     } 
/*     */ 
/*     */     
/* 463 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeLights(RenderGlobal renderGlobal) {
/* 472 */     synchronized (mapDynamicLights) {
/*     */       
/* 474 */       List<DynamicLight> list = mapDynamicLights.valueList();
/*     */       
/* 476 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/* 478 */         DynamicLight dynamiclight = list.get(i);
/* 479 */         dynamiclight.updateLitChunks(renderGlobal);
/*     */       } 
/*     */       
/* 482 */       mapDynamicLights.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void clear() {
/* 488 */     synchronized (mapDynamicLights) {
/*     */       
/* 490 */       mapDynamicLights.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getCount() {
/* 496 */     synchronized (mapDynamicLights) {
/*     */       
/* 498 */       return mapDynamicLights.size();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack getItemStack(EntityItem entityItem) {
/* 504 */     ItemStack itemstack = entityItem.getDataWatcher().getWatchableObjectItemStack(10);
/* 505 */     return itemstack;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\DynamicLights.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */