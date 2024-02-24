/*     */ package net.minecraft.entity;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.item.EntityArmorStand;
/*     */ import net.minecraft.entity.item.EntityExpBottle;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.monster.EntityCreeper;
/*     */ import net.minecraft.entity.monster.EntityGuardian;
/*     */ import net.minecraft.entity.monster.EntityPigZombie;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.entity.passive.EntityCow;
/*     */ import net.minecraft.entity.passive.EntityPig;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.projectile.EntityLargeFireball;
/*     */ import net.minecraft.entity.projectile.EntitySnowball;
/*     */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ 
/*     */ public class EntityList {
/*  26 */   private static final Logger logger = LogManager.getLogger();
/*  27 */   private static final Map<String, Class<? extends Entity>> stringToClassMapping = Maps.newHashMap();
/*  28 */   private static final Map<Class<? extends Entity>, String> classToStringMapping = Maps.newHashMap();
/*  29 */   private static final Map<Integer, Class<? extends Entity>> idToClassMapping = Maps.newHashMap();
/*  30 */   private static final Map<Class<? extends Entity>, Integer> classToIDMapping = Maps.newHashMap();
/*  31 */   private static final Map<String, Integer> stringToIDMapping = Maps.newHashMap();
/*  32 */   public static final Map<Integer, EntityEggInfo> entityEggs = Maps.newLinkedHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addMapping(Class<? extends Entity> entityClass, String entityName, int id) {
/*  39 */     if (stringToClassMapping.containsKey(entityName))
/*     */     {
/*  41 */       throw new IllegalArgumentException("ID is already registered: " + entityName);
/*     */     }
/*  43 */     if (idToClassMapping.containsKey(Integer.valueOf(id)))
/*     */     {
/*  45 */       throw new IllegalArgumentException("ID is already registered: " + id);
/*     */     }
/*  47 */     if (id == 0)
/*     */     {
/*  49 */       throw new IllegalArgumentException("Cannot register to reserved id: " + id);
/*     */     }
/*  51 */     if (entityClass == null)
/*     */     {
/*  53 */       throw new IllegalArgumentException("Cannot register null clazz for id: " + id);
/*     */     }
/*     */ 
/*     */     
/*  57 */     stringToClassMapping.put(entityName, entityClass);
/*  58 */     classToStringMapping.put(entityClass, entityName);
/*  59 */     idToClassMapping.put(Integer.valueOf(id), entityClass);
/*  60 */     classToIDMapping.put(entityClass, Integer.valueOf(id));
/*  61 */     stringToIDMapping.put(entityName, Integer.valueOf(id));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addMapping(Class<? extends Entity> entityClass, String entityName, int entityID, int baseColor, int spotColor) {
/*  70 */     addMapping(entityClass, entityName, entityID);
/*  71 */     entityEggs.put(Integer.valueOf(entityID), new EntityEggInfo(entityID, baseColor, spotColor));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Entity createEntityByName(String entityName, World worldIn) {
/*  79 */     Entity entity = null;
/*     */ 
/*     */     
/*     */     try {
/*  83 */       Class<? extends Entity> oclass = stringToClassMapping.get(entityName);
/*     */       
/*  85 */       if (oclass != null)
/*     */       {
/*  87 */         entity = oclass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldIn });
/*     */       }
/*     */     }
/*  90 */     catch (Exception exception) {
/*     */       
/*  92 */       exception.printStackTrace();
/*     */     } 
/*     */     
/*  95 */     return entity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Entity createEntityFromNBT(NBTTagCompound nbt, World worldIn) {
/* 103 */     Entity entity = null;
/*     */     
/* 105 */     if ("Minecart".equals(nbt.getString("id"))) {
/*     */       
/* 107 */       nbt.setString("id", EntityMinecart.EnumMinecartType.byNetworkID(nbt.getInteger("Type")).getName());
/* 108 */       nbt.removeTag("Type");
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 113 */       Class<? extends Entity> oclass = stringToClassMapping.get(nbt.getString("id"));
/*     */       
/* 115 */       if (oclass != null)
/*     */       {
/* 117 */         entity = oclass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldIn });
/*     */       }
/*     */     }
/* 120 */     catch (Exception exception) {
/*     */       
/* 122 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 125 */     if (entity != null) {
/*     */       
/* 127 */       entity.readFromNBT(nbt);
/*     */     }
/*     */     else {
/*     */       
/* 131 */       logger.warn("Skipping Entity with id " + nbt.getString("id"));
/*     */     } 
/*     */     
/* 134 */     return entity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Entity createEntityByID(int entityID, World worldIn) {
/* 142 */     Entity entity = null;
/*     */ 
/*     */     
/*     */     try {
/* 146 */       Class<? extends Entity> oclass = getClassFromID(entityID);
/*     */       
/* 148 */       if (oclass != null)
/*     */       {
/* 150 */         entity = oclass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldIn });
/*     */       }
/*     */     }
/* 153 */     catch (Exception exception) {
/*     */       
/* 155 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 158 */     if (entity == null)
/*     */     {
/* 160 */       logger.warn("Skipping Entity with id " + entityID);
/*     */     }
/*     */     
/* 163 */     return entity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEntityID(Entity entityIn) {
/* 171 */     Integer integer = classToIDMapping.get(entityIn.getClass());
/* 172 */     return (integer == null) ? 0 : integer.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Class<? extends Entity> getClassFromID(int entityID) {
/* 177 */     return idToClassMapping.get(Integer.valueOf(entityID));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getEntityString(Entity entityIn) {
/* 185 */     return classToStringMapping.get(entityIn.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getIDFromString(String entityName) {
/* 193 */     Integer integer = stringToIDMapping.get(entityName);
/* 194 */     return (integer == null) ? 90 : integer.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getStringFromID(int entityID) {
/* 202 */     return classToStringMapping.get(getClassFromID(entityID));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void func_151514_a() {}
/*     */ 
/*     */   
/*     */   public static List<String> getEntityNameList() {
/* 211 */     List<String> list = Lists.newArrayList();
/*     */     
/* 213 */     for (Map.Entry<String, Class<? extends Entity>> entry : stringToClassMapping.entrySet()) {
/*     */       
/* 215 */       Class<? extends Entity> oclass = entry.getValue();
/*     */       
/* 217 */       if ((oclass.getModifiers() & 0x400) != 1024)
/*     */       {
/* 219 */         list.add(entry.getKey());
/*     */       }
/*     */     } 
/*     */     
/* 223 */     list.add("LightningBolt");
/* 224 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isStringEntityName(Entity entityIn, String entityName) {
/* 229 */     String s = getEntityString(entityIn);
/*     */     
/* 231 */     if (s == null && entityIn instanceof net.minecraft.entity.player.EntityPlayer) {
/*     */       
/* 233 */       s = "Player";
/*     */     }
/* 235 */     else if (s == null && entityIn instanceof net.minecraft.entity.effect.EntityLightningBolt) {
/*     */       
/* 237 */       s = "LightningBolt";
/*     */     } 
/*     */     
/* 240 */     return entityName.equals(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isStringValidEntityName(String entityName) {
/* 245 */     return ("Player".equals(entityName) || getEntityNameList().contains(entityName));
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 250 */     addMapping((Class)EntityItem.class, "Item", 1);
/* 251 */     addMapping((Class)EntityXPOrb.class, "XPOrb", 2);
/* 252 */     addMapping((Class)EntityEgg.class, "ThrownEgg", 7);
/* 253 */     addMapping((Class)EntityLeashKnot.class, "LeashKnot", 8);
/* 254 */     addMapping((Class)EntityPainting.class, "Painting", 9);
/* 255 */     addMapping((Class)EntityArrow.class, "Arrow", 10);
/* 256 */     addMapping((Class)EntitySnowball.class, "Snowball", 11);
/* 257 */     addMapping((Class)EntityLargeFireball.class, "Fireball", 12);
/* 258 */     addMapping((Class)EntitySmallFireball.class, "SmallFireball", 13);
/* 259 */     addMapping((Class)EntityEnderPearl.class, "ThrownEnderpearl", 14);
/* 260 */     addMapping((Class)EntityEnderEye.class, "EyeOfEnderSignal", 15);
/* 261 */     addMapping((Class)EntityPotion.class, "ThrownPotion", 16);
/* 262 */     addMapping((Class)EntityExpBottle.class, "ThrownExpBottle", 17);
/* 263 */     addMapping((Class)EntityItemFrame.class, "ItemFrame", 18);
/* 264 */     addMapping((Class)EntityWitherSkull.class, "WitherSkull", 19);
/* 265 */     addMapping((Class)EntityTNTPrimed.class, "PrimedTnt", 20);
/* 266 */     addMapping((Class)EntityFallingBlock.class, "FallingSand", 21);
/* 267 */     addMapping((Class)EntityFireworkRocket.class, "FireworksRocketEntity", 22);
/* 268 */     addMapping((Class)EntityArmorStand.class, "ArmorStand", 30);
/* 269 */     addMapping((Class)EntityBoat.class, "Boat", 41);
/* 270 */     addMapping((Class)EntityMinecartEmpty.class, EntityMinecart.EnumMinecartType.RIDEABLE.getName(), 42);
/* 271 */     addMapping((Class)EntityMinecartChest.class, EntityMinecart.EnumMinecartType.CHEST.getName(), 43);
/* 272 */     addMapping((Class)EntityMinecartFurnace.class, EntityMinecart.EnumMinecartType.FURNACE.getName(), 44);
/* 273 */     addMapping((Class)EntityMinecartTNT.class, EntityMinecart.EnumMinecartType.TNT.getName(), 45);
/* 274 */     addMapping((Class)EntityMinecartHopper.class, EntityMinecart.EnumMinecartType.HOPPER.getName(), 46);
/* 275 */     addMapping((Class)EntityMinecartMobSpawner.class, EntityMinecart.EnumMinecartType.SPAWNER.getName(), 47);
/* 276 */     addMapping((Class)EntityMinecartCommandBlock.class, EntityMinecart.EnumMinecartType.COMMAND_BLOCK.getName(), 40);
/* 277 */     addMapping((Class)EntityLiving.class, "Mob", 48);
/* 278 */     addMapping((Class)EntityMob.class, "Monster", 49);
/* 279 */     addMapping((Class)EntityCreeper.class, "Creeper", 50, 894731, 0);
/* 280 */     addMapping((Class)EntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
/* 281 */     addMapping((Class)EntitySpider.class, "Spider", 52, 3419431, 11013646);
/* 282 */     addMapping((Class)EntityGiantZombie.class, "Giant", 53);
/* 283 */     addMapping((Class)EntityZombie.class, "Zombie", 54, 44975, 7969893);
/* 284 */     addMapping((Class)EntitySlime.class, "Slime", 55, 5349438, 8306542);
/* 285 */     addMapping((Class)EntityGhast.class, "Ghast", 56, 16382457, 12369084);
/* 286 */     addMapping((Class)EntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
/* 287 */     addMapping((Class)EntityEnderman.class, "Enderman", 58, 1447446, 0);
/* 288 */     addMapping((Class)EntityCaveSpider.class, "CaveSpider", 59, 803406, 11013646);
/* 289 */     addMapping((Class)EntitySilverfish.class, "Silverfish", 60, 7237230, 3158064);
/* 290 */     addMapping((Class)EntityBlaze.class, "Blaze", 61, 16167425, 16775294);
/* 291 */     addMapping((Class)EntityMagmaCube.class, "LavaSlime", 62, 3407872, 16579584);
/* 292 */     addMapping((Class)EntityDragon.class, "EnderDragon", 63);
/* 293 */     addMapping((Class)EntityWither.class, "WitherBoss", 64);
/* 294 */     addMapping((Class)EntityBat.class, "Bat", 65, 4996656, 986895);
/* 295 */     addMapping((Class)EntityWitch.class, "Witch", 66, 3407872, 5349438);
/* 296 */     addMapping((Class)EntityEndermite.class, "Endermite", 67, 1447446, 7237230);
/* 297 */     addMapping((Class)EntityGuardian.class, "Guardian", 68, 5931634, 15826224);
/* 298 */     addMapping((Class)EntityPig.class, "Pig", 90, 15771042, 14377823);
/* 299 */     addMapping((Class)EntitySheep.class, "Sheep", 91, 15198183, 16758197);
/* 300 */     addMapping((Class)EntityCow.class, "Cow", 92, 4470310, 10592673);
/* 301 */     addMapping((Class)EntityChicken.class, "Chicken", 93, 10592673, 16711680);
/* 302 */     addMapping((Class)EntitySquid.class, "Squid", 94, 2243405, 7375001);
/* 303 */     addMapping((Class)EntityWolf.class, "Wolf", 95, 14144467, 13545366);
/* 304 */     addMapping((Class)EntityMooshroom.class, "MushroomCow", 96, 10489616, 12040119);
/* 305 */     addMapping((Class)EntitySnowman.class, "SnowMan", 97);
/* 306 */     addMapping((Class)EntityOcelot.class, "Ozelot", 98, 15720061, 5653556);
/* 307 */     addMapping((Class)EntityIronGolem.class, "VillagerGolem", 99);
/* 308 */     addMapping((Class)EntityHorse.class, "EntityHorse", 100, 12623485, 15656192);
/* 309 */     addMapping((Class)EntityRabbit.class, "Rabbit", 101, 10051392, 7555121);
/* 310 */     addMapping((Class)EntityVillager.class, "Villager", 120, 5651507, 12422002);
/* 311 */     addMapping((Class)EntityEnderCrystal.class, "EnderCrystal", 200);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class EntityEggInfo
/*     */   {
/*     */     public final int spawnedID;
/*     */     public final int primaryColor;
/*     */     public final int secondaryColor;
/*     */     public final StatBase field_151512_d;
/*     */     public final StatBase field_151513_e;
/*     */     
/*     */     public EntityEggInfo(int id, int baseColor, int spotColor) {
/* 324 */       this.spawnedID = id;
/* 325 */       this.primaryColor = baseColor;
/* 326 */       this.secondaryColor = spotColor;
/* 327 */       this.field_151512_d = StatList.getStatKillEntity(this);
/* 328 */       this.field_151513_e = StatList.getStatEntityKilledBy(this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\EntityList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */