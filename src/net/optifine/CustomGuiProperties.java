/*     */ package net.optifine;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBeacon;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.tileentity.TileEntityDispenser;
/*     */ import net.minecraft.tileentity.TileEntityEnderChest;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.IWorldNameable;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.config.NbtTagValue;
/*     */ import net.optifine.config.RangeListInt;
/*     */ import net.optifine.config.VillagerProfession;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorField;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ public class CustomGuiProperties {
/*  31 */   private String fileName = null;
/*  32 */   private String basePath = null;
/*  33 */   private EnumContainer container = null;
/*  34 */   private Map<ResourceLocation, ResourceLocation> textureLocations = null;
/*  35 */   private NbtTagValue nbtName = null;
/*  36 */   private BiomeGenBase[] biomes = null;
/*  37 */   private RangeListInt heights = null;
/*  38 */   private Boolean large = null;
/*  39 */   private Boolean trapped = null;
/*  40 */   private Boolean christmas = null;
/*  41 */   private Boolean ender = null;
/*  42 */   private RangeListInt levels = null;
/*  43 */   private VillagerProfession[] professions = null;
/*  44 */   private EnumVariant[] variants = null;
/*  45 */   private EnumDyeColor[] colors = null;
/*  46 */   private static final EnumVariant[] VARIANTS_HORSE = new EnumVariant[] { EnumVariant.HORSE, EnumVariant.DONKEY, EnumVariant.MULE, EnumVariant.LLAMA };
/*  47 */   private static final EnumVariant[] VARIANTS_DISPENSER = new EnumVariant[] { EnumVariant.DISPENSER, EnumVariant.DROPPER };
/*  48 */   private static final EnumVariant[] VARIANTS_INVALID = new EnumVariant[0];
/*  49 */   private static final EnumDyeColor[] COLORS_INVALID = new EnumDyeColor[0];
/*  50 */   private static final ResourceLocation ANVIL_GUI_TEXTURE = new ResourceLocation("textures/gui/container/anvil.png");
/*  51 */   private static final ResourceLocation BEACON_GUI_TEXTURE = new ResourceLocation("textures/gui/container/beacon.png");
/*  52 */   private static final ResourceLocation BREWING_STAND_GUI_TEXTURE = new ResourceLocation("textures/gui/container/brewing_stand.png");
/*  53 */   private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
/*  54 */   private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/crafting_table.png");
/*  55 */   private static final ResourceLocation HORSE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/horse.png");
/*  56 */   private static final ResourceLocation DISPENSER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/dispenser.png");
/*  57 */   private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");
/*  58 */   private static final ResourceLocation FURNACE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/furnace.png");
/*  59 */   private static final ResourceLocation HOPPER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/hopper.png");
/*  60 */   private static final ResourceLocation INVENTORY_GUI_TEXTURE = new ResourceLocation("textures/gui/container/inventory.png");
/*  61 */   private static final ResourceLocation SHULKER_BOX_GUI_TEXTURE = new ResourceLocation("textures/gui/container/shulker_box.png");
/*  62 */   private static final ResourceLocation VILLAGER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/villager.png");
/*     */ 
/*     */   
/*     */   public CustomGuiProperties(Properties props, String path) {
/*  66 */     ConnectedParser connectedparser = new ConnectedParser("CustomGuis");
/*  67 */     this.fileName = connectedparser.parseName(path);
/*  68 */     this.basePath = connectedparser.parseBasePath(path);
/*  69 */     this.container = (EnumContainer)connectedparser.parseEnum(props.getProperty("container"), (Enum[])EnumContainer.values(), "container");
/*  70 */     this.textureLocations = parseTextureLocations(props, "texture", this.container, "textures/gui/", this.basePath);
/*  71 */     this.nbtName = connectedparser.parseNbtTagValue("name", props.getProperty("name"));
/*  72 */     this.biomes = connectedparser.parseBiomes(props.getProperty("biomes"));
/*  73 */     this.heights = connectedparser.parseRangeListInt(props.getProperty("heights"));
/*  74 */     this.large = connectedparser.parseBooleanObject(props.getProperty("large"));
/*  75 */     this.trapped = connectedparser.parseBooleanObject(props.getProperty("trapped"));
/*  76 */     this.christmas = connectedparser.parseBooleanObject(props.getProperty("christmas"));
/*  77 */     this.ender = connectedparser.parseBooleanObject(props.getProperty("ender"));
/*  78 */     this.levels = connectedparser.parseRangeListInt(props.getProperty("levels"));
/*  79 */     this.professions = connectedparser.parseProfessions(props.getProperty("professions"));
/*  80 */     EnumVariant[] acustomguiproperties$enumvariant = getContainerVariants(this.container);
/*  81 */     this.variants = (EnumVariant[])connectedparser.parseEnums(props.getProperty("variants"), (Enum[])acustomguiproperties$enumvariant, "variants", (Enum[])VARIANTS_INVALID);
/*  82 */     this.colors = parseEnumDyeColors(props.getProperty("colors"));
/*     */   }
/*     */ 
/*     */   
/*     */   private static EnumVariant[] getContainerVariants(EnumContainer cont) {
/*  87 */     return (cont == EnumContainer.HORSE) ? VARIANTS_HORSE : ((cont == EnumContainer.DISPENSER) ? VARIANTS_DISPENSER : new EnumVariant[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   private static EnumDyeColor[] parseEnumDyeColors(String str) {
/*  92 */     if (str == null)
/*     */     {
/*  94 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  98 */     str = str.toLowerCase();
/*  99 */     String[] astring = Config.tokenize(str, " ");
/* 100 */     EnumDyeColor[] aenumdyecolor = new EnumDyeColor[astring.length];
/*     */     
/* 102 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 104 */       String s = astring[i];
/* 105 */       EnumDyeColor enumdyecolor = parseEnumDyeColor(s);
/*     */       
/* 107 */       if (enumdyecolor == null) {
/*     */         
/* 109 */         warn("Invalid color: " + s);
/* 110 */         return COLORS_INVALID;
/*     */       } 
/*     */       
/* 113 */       aenumdyecolor[i] = enumdyecolor;
/*     */     } 
/*     */     
/* 116 */     return aenumdyecolor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static EnumDyeColor parseEnumDyeColor(String str) {
/* 122 */     if (str == null)
/*     */     {
/* 124 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 128 */     EnumDyeColor[] aenumdyecolor = EnumDyeColor.values();
/*     */     
/* 130 */     for (int i = 0; i < aenumdyecolor.length; i++) {
/*     */       
/* 132 */       EnumDyeColor enumdyecolor = aenumdyecolor[i];
/*     */       
/* 134 */       if (enumdyecolor.getName().equals(str))
/*     */       {
/* 136 */         return enumdyecolor;
/*     */       }
/*     */       
/* 139 */       if (enumdyecolor.getUnlocalizedName().equals(str))
/*     */       {
/* 141 */         return enumdyecolor;
/*     */       }
/*     */     } 
/*     */     
/* 145 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ResourceLocation parseTextureLocation(String str, String basePath) {
/* 151 */     if (str == null)
/*     */     {
/* 153 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 157 */     str = str.trim();
/* 158 */     String s = TextureUtils.fixResourcePath(str, basePath);
/*     */     
/* 160 */     if (!s.endsWith(".png"))
/*     */     {
/* 162 */       s = s + ".png";
/*     */     }
/*     */     
/* 165 */     return new ResourceLocation(basePath + "/" + s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map<ResourceLocation, ResourceLocation> parseTextureLocations(Properties props, String property, EnumContainer container, String pathPrefix, String basePath) {
/* 171 */     Map<ResourceLocation, ResourceLocation> map = new HashMap<>();
/* 172 */     String s = props.getProperty(property);
/*     */     
/* 174 */     if (s != null) {
/*     */       
/* 176 */       ResourceLocation resourcelocation = getGuiTextureLocation(container);
/* 177 */       ResourceLocation resourcelocation1 = parseTextureLocation(s, basePath);
/*     */       
/* 179 */       if (resourcelocation != null && resourcelocation1 != null)
/*     */       {
/* 181 */         map.put(resourcelocation, resourcelocation1);
/*     */       }
/*     */     } 
/*     */     
/* 185 */     String s5 = property + ".";
/*     */     
/* 187 */     for (Object e : props.keySet()) {
/*     */       
/* 189 */       String s1 = (String)e;
/* 190 */       if (s1.startsWith(s5)) {
/*     */         
/* 192 */         String s2 = s1.substring(s5.length());
/* 193 */         s2 = s2.replace('\\', '/');
/* 194 */         s2 = StrUtils.removePrefixSuffix(s2, "/", ".png");
/* 195 */         String s3 = pathPrefix + s2 + ".png";
/* 196 */         String s4 = props.getProperty(s1);
/* 197 */         ResourceLocation resourcelocation2 = new ResourceLocation(s3);
/* 198 */         ResourceLocation resourcelocation3 = parseTextureLocation(s4, basePath);
/* 199 */         map.put(resourcelocation2, resourcelocation3);
/*     */       } 
/*     */     } 
/*     */     
/* 203 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   private static ResourceLocation getGuiTextureLocation(EnumContainer container) {
/* 208 */     if (container == null)
/*     */     {
/* 210 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 214 */     switch (container) {
/*     */       
/*     */       case ANVIL:
/* 217 */         return ANVIL_GUI_TEXTURE;
/*     */       
/*     */       case BEACON:
/* 220 */         return BEACON_GUI_TEXTURE;
/*     */       
/*     */       case BREWING_STAND:
/* 223 */         return BREWING_STAND_GUI_TEXTURE;
/*     */       
/*     */       case CHEST:
/* 226 */         return CHEST_GUI_TEXTURE;
/*     */       
/*     */       case CRAFTING:
/* 229 */         return CRAFTING_TABLE_GUI_TEXTURE;
/*     */       
/*     */       case CREATIVE:
/* 232 */         return null;
/*     */       
/*     */       case DISPENSER:
/* 235 */         return DISPENSER_GUI_TEXTURE;
/*     */       
/*     */       case ENCHANTMENT:
/* 238 */         return ENCHANTMENT_TABLE_GUI_TEXTURE;
/*     */       
/*     */       case FURNACE:
/* 241 */         return FURNACE_GUI_TEXTURE;
/*     */       
/*     */       case HOPPER:
/* 244 */         return HOPPER_GUI_TEXTURE;
/*     */       
/*     */       case HORSE:
/* 247 */         return HORSE_GUI_TEXTURE;
/*     */       
/*     */       case INVENTORY:
/* 250 */         return INVENTORY_GUI_TEXTURE;
/*     */       
/*     */       case SHULKER_BOX:
/* 253 */         return SHULKER_BOX_GUI_TEXTURE;
/*     */       
/*     */       case VILLAGER:
/* 256 */         return VILLAGER_GUI_TEXTURE;
/*     */     } 
/*     */     
/* 259 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String path) {
/* 266 */     if (this.fileName != null && !this.fileName.isEmpty()) {
/*     */       
/* 268 */       if (this.basePath == null) {
/*     */         
/* 270 */         warn("No base path found: " + path);
/* 271 */         return false;
/*     */       } 
/* 273 */       if (this.container == null) {
/*     */         
/* 275 */         warn("No container found: " + path);
/* 276 */         return false;
/*     */       } 
/* 278 */       if (this.textureLocations.isEmpty()) {
/*     */         
/* 280 */         warn("No texture found: " + path);
/* 281 */         return false;
/*     */       } 
/* 283 */       if (this.professions == ConnectedParser.PROFESSIONS_INVALID) {
/*     */         
/* 285 */         warn("Invalid professions or careers: " + path);
/* 286 */         return false;
/*     */       } 
/* 288 */       if (this.variants == VARIANTS_INVALID) {
/*     */         
/* 290 */         warn("Invalid variants: " + path);
/* 291 */         return false;
/*     */       } 
/* 293 */       if (this.colors == COLORS_INVALID) {
/*     */         
/* 295 */         warn("Invalid colors: " + path);
/* 296 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 300 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 305 */     warn("No name found: " + path);
/* 306 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void warn(String str) {
/* 312 */     Config.warn("[CustomGuis] " + str);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean matchesGeneral(EnumContainer ec, BlockPos pos, IBlockAccess blockAccess) {
/* 317 */     if (this.container != ec)
/*     */     {
/* 319 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 323 */     if (this.biomes != null) {
/*     */       
/* 325 */       BiomeGenBase biomegenbase = blockAccess.getBiomeGenForCoords(pos);
/*     */       
/* 327 */       if (!Matches.biome(biomegenbase, this.biomes))
/*     */       {
/* 329 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 333 */     return (this.heights == null || this.heights.isInRange(pos.getY()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesPos(EnumContainer ec, BlockPos pos, IBlockAccess blockAccess, GuiScreen screen) {
/* 339 */     if (!matchesGeneral(ec, pos, blockAccess))
/*     */     {
/* 341 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 345 */     if (this.nbtName != null) {
/*     */       
/* 347 */       String s = getName(screen);
/*     */       
/* 349 */       if (!this.nbtName.matchesValue(s))
/*     */       {
/* 351 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 355 */     switch (ec) {
/*     */       
/*     */       case BEACON:
/* 358 */         return matchesBeacon(pos, blockAccess);
/*     */       
/*     */       case CHEST:
/* 361 */         return matchesChest(pos, blockAccess);
/*     */       
/*     */       case DISPENSER:
/* 364 */         return matchesDispenser(pos, blockAccess);
/*     */     } 
/*     */     
/* 367 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getName(GuiScreen screen) {
/* 374 */     IWorldNameable iworldnameable = getWorldNameable(screen);
/* 375 */     return (iworldnameable == null) ? null : iworldnameable.getDisplayName().getUnformattedText();
/*     */   }
/*     */ 
/*     */   
/*     */   private static IWorldNameable getWorldNameable(GuiScreen screen) {
/* 380 */     return (screen instanceof net.minecraft.client.gui.inventory.GuiBeacon) ? getWorldNameable(screen, Reflector.GuiBeacon_tileBeacon) : ((screen instanceof net.minecraft.client.gui.inventory.GuiBrewingStand) ? getWorldNameable(screen, Reflector.GuiBrewingStand_tileBrewingStand) : ((screen instanceof net.minecraft.client.gui.inventory.GuiChest) ? getWorldNameable(screen, Reflector.GuiChest_lowerChestInventory) : ((screen instanceof GuiDispenser) ? (IWorldNameable)((GuiDispenser)screen).dispenserInventory : ((screen instanceof net.minecraft.client.gui.GuiEnchantment) ? getWorldNameable(screen, Reflector.GuiEnchantment_nameable) : ((screen instanceof net.minecraft.client.gui.inventory.GuiFurnace) ? getWorldNameable(screen, Reflector.GuiFurnace_tileFurnace) : ((screen instanceof net.minecraft.client.gui.GuiHopper) ? getWorldNameable(screen, Reflector.GuiHopper_hopperInventory) : null))))));
/*     */   }
/*     */ 
/*     */   
/*     */   private static IWorldNameable getWorldNameable(GuiScreen screen, ReflectorField fieldInventory) {
/* 385 */     Object object = Reflector.getFieldValue(screen, fieldInventory);
/* 386 */     return !(object instanceof IWorldNameable) ? null : (IWorldNameable)object;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean matchesBeacon(BlockPos pos, IBlockAccess blockAccess) {
/* 391 */     TileEntity tileentity = blockAccess.getTileEntity(pos);
/*     */     
/* 393 */     if (!(tileentity instanceof TileEntityBeacon))
/*     */     {
/* 395 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 399 */     TileEntityBeacon tileentitybeacon = (TileEntityBeacon)tileentity;
/*     */     
/* 401 */     if (this.levels != null) {
/*     */       
/* 403 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 404 */       tileentitybeacon.writeToNBT(nbttagcompound);
/* 405 */       int i = nbttagcompound.getInteger("Levels");
/*     */       
/* 407 */       if (!this.levels.isInRange(i))
/*     */       {
/* 409 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 413 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesChest(BlockPos pos, IBlockAccess blockAccess) {
/* 419 */     TileEntity tileentity = blockAccess.getTileEntity(pos);
/*     */     
/* 421 */     if (tileentity instanceof TileEntityChest) {
/*     */       
/* 423 */       TileEntityChest tileentitychest = (TileEntityChest)tileentity;
/* 424 */       return matchesChest(tileentitychest, pos, blockAccess);
/*     */     } 
/* 426 */     if (tileentity instanceof TileEntityEnderChest) {
/*     */       
/* 428 */       TileEntityEnderChest tileentityenderchest = (TileEntityEnderChest)tileentity;
/* 429 */       return matchesEnderChest(tileentityenderchest, pos, blockAccess);
/*     */     } 
/*     */ 
/*     */     
/* 433 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesChest(TileEntityChest tec, BlockPos pos, IBlockAccess blockAccess) {
/* 439 */     boolean flag = (tec.adjacentChestXNeg != null || tec.adjacentChestXPos != null || tec.adjacentChestZNeg != null || tec.adjacentChestZPos != null);
/* 440 */     boolean flag1 = (tec.getChestType() == 1);
/* 441 */     boolean flag2 = CustomGuis.isChristmas;
/* 442 */     boolean flag3 = false;
/* 443 */     return matchesChest(flag, flag1, flag2, flag3);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean matchesEnderChest(TileEntityEnderChest teec, BlockPos pos, IBlockAccess blockAccess) {
/* 448 */     return matchesChest(false, false, false, true);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean matchesChest(boolean isLarge, boolean isTrapped, boolean isChristmas, boolean isEnder) {
/* 453 */     return (this.large != null && this.large.booleanValue() != isLarge) ? false : ((this.trapped != null && this.trapped.booleanValue() != isTrapped) ? false : ((this.christmas != null && this.christmas.booleanValue() != isChristmas) ? false : ((this.ender == null || this.ender.booleanValue() == isEnder))));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean matchesDispenser(BlockPos pos, IBlockAccess blockAccess) {
/* 458 */     TileEntity tileentity = blockAccess.getTileEntity(pos);
/*     */     
/* 460 */     if (!(tileentity instanceof TileEntityDispenser))
/*     */     {
/* 462 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 466 */     TileEntityDispenser tileentitydispenser = (TileEntityDispenser)tileentity;
/*     */     
/* 468 */     if (this.variants != null) {
/*     */       
/* 470 */       EnumVariant customguiproperties$enumvariant = getDispenserVariant(tileentitydispenser);
/*     */       
/* 472 */       if (!Config.equalsOne(customguiproperties$enumvariant, (Object[])this.variants))
/*     */       {
/* 474 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 478 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private EnumVariant getDispenserVariant(TileEntityDispenser ted) {
/* 484 */     return (ted instanceof net.minecraft.tileentity.TileEntityDropper) ? EnumVariant.DROPPER : EnumVariant.DISPENSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchesEntity(EnumContainer ec, Entity entity, IBlockAccess blockAccess) {
/* 489 */     if (!matchesGeneral(ec, entity.getPosition(), blockAccess))
/*     */     {
/* 491 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 495 */     if (this.nbtName != null) {
/*     */       
/* 497 */       String s = entity.getName();
/*     */       
/* 499 */       if (!this.nbtName.matchesValue(s))
/*     */       {
/* 501 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 505 */     switch (ec) {
/*     */       
/*     */       case HORSE:
/* 508 */         return matchesHorse(entity, blockAccess);
/*     */       
/*     */       case VILLAGER:
/* 511 */         return matchesVillager(entity, blockAccess);
/*     */     } 
/*     */     
/* 514 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesVillager(Entity entity, IBlockAccess blockAccess) {
/* 521 */     if (!(entity instanceof EntityVillager))
/*     */     {
/* 523 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 527 */     EntityVillager entityvillager = (EntityVillager)entity;
/*     */     
/* 529 */     if (this.professions != null) {
/*     */       
/* 531 */       int i = entityvillager.getProfession();
/* 532 */       int j = Reflector.getFieldValueInt(entityvillager, Reflector.EntityVillager_careerId, -1);
/*     */       
/* 534 */       if (j < 0)
/*     */       {
/* 536 */         return false;
/*     */       }
/*     */       
/* 539 */       boolean flag = false;
/*     */       
/* 541 */       for (int k = 0; k < this.professions.length; k++) {
/*     */         
/* 543 */         VillagerProfession villagerprofession = this.professions[k];
/*     */         
/* 545 */         if (villagerprofession.matches(i, j)) {
/*     */           
/* 547 */           flag = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 552 */       if (!flag)
/*     */       {
/* 554 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 558 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesHorse(Entity entity, IBlockAccess blockAccess) {
/* 564 */     if (!(entity instanceof EntityHorse))
/*     */     {
/* 566 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 570 */     EntityHorse entityhorse = (EntityHorse)entity;
/*     */     
/* 572 */     if (this.variants != null) {
/*     */       
/* 574 */       EnumVariant customguiproperties$enumvariant = getHorseVariant(entityhorse);
/*     */       
/* 576 */       if (!Config.equalsOne(customguiproperties$enumvariant, (Object[])this.variants))
/*     */       {
/* 578 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 582 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private EnumVariant getHorseVariant(EntityHorse entity) {
/* 588 */     int i = entity.getHorseType();
/*     */     
/* 590 */     switch (i) {
/*     */       
/*     */       case 0:
/* 593 */         return EnumVariant.HORSE;
/*     */       
/*     */       case 1:
/* 596 */         return EnumVariant.DONKEY;
/*     */       
/*     */       case 2:
/* 599 */         return EnumVariant.MULE;
/*     */     } 
/*     */     
/* 602 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumContainer getContainer() {
/* 608 */     return this.container;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation(ResourceLocation loc) {
/* 613 */     ResourceLocation resourcelocation = this.textureLocations.get(loc);
/* 614 */     return (resourcelocation == null) ? loc : resourcelocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 619 */     return "name: " + this.fileName + ", container: " + this.container + ", textures: " + this.textureLocations;
/*     */   }
/*     */   
/*     */   public enum EnumContainer
/*     */   {
/* 624 */     ANVIL,
/* 625 */     BEACON,
/* 626 */     BREWING_STAND,
/* 627 */     CHEST,
/* 628 */     CRAFTING,
/* 629 */     DISPENSER,
/* 630 */     ENCHANTMENT,
/* 631 */     FURNACE,
/* 632 */     HOPPER,
/* 633 */     HORSE,
/* 634 */     VILLAGER,
/* 635 */     SHULKER_BOX,
/* 636 */     CREATIVE,
/* 637 */     INVENTORY;
/*     */     
/* 639 */     public static final EnumContainer[] VALUES = values();
/*     */     static {
/*     */     
/*     */     } }
/*     */   
/* 644 */   private enum EnumVariant { HORSE,
/* 645 */     DONKEY,
/* 646 */     MULE,
/* 647 */     LLAMA,
/* 648 */     DISPENSER,
/* 649 */     DROPPER; }
/*     */ 
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\CustomGuiProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */