/*      */ package net.optifine;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.model.ModelBase;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.entity.RenderItem;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.resources.IResourcePack;
/*      */ import net.minecraft.client.resources.model.IBakedModel;
/*      */ import net.minecraft.client.resources.model.ModelBakery;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemArmor;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.optifine.config.NbtTagValue;
/*      */ import net.optifine.render.Blender;
/*      */ import net.optifine.util.PropertiesOrdered;
/*      */ import net.optifine.util.ResUtils;
/*      */ import net.optifine.util.StrUtils;
/*      */ 
/*      */ public class CustomItems {
/*   38 */   private static CustomItemProperties[][] itemProperties = (CustomItemProperties[][])null;
/*   39 */   private static CustomItemProperties[][] enchantmentProperties = (CustomItemProperties[][])null;
/*   40 */   private static Map mapPotionIds = null;
/*   41 */   private static final ItemModelGenerator itemModelGenerator = new ItemModelGenerator();
/*      */   private static boolean useGlint = true;
/*      */   private static final boolean renderOffHand = false;
/*      */   public static final int MASK_POTION_SPLASH = 16384;
/*      */   public static final int MASK_POTION_NAME = 63;
/*      */   public static final int MASK_POTION_EXTENDED = 64;
/*      */   public static final String KEY_TEXTURE_OVERLAY = "texture.potion_overlay";
/*      */   public static final String KEY_TEXTURE_SPLASH = "texture.potion_bottle_splash";
/*      */   public static final String KEY_TEXTURE_DRINKABLE = "texture.potion_bottle_drinkable";
/*      */   public static final String DEFAULT_TEXTURE_OVERLAY = "items/potion_overlay";
/*      */   public static final String DEFAULT_TEXTURE_SPLASH = "items/potion_bottle_splash";
/*      */   public static final String DEFAULT_TEXTURE_DRINKABLE = "items/potion_bottle_drinkable";
/*   53 */   private static final int[][] EMPTY_INT2_ARRAY = new int[0][];
/*      */   
/*      */   private static final String TYPE_POTION_NORMAL = "normal";
/*      */   private static final String TYPE_POTION_SPLASH = "splash";
/*      */   private static final String TYPE_POTION_LINGER = "linger";
/*      */   
/*      */   public static void update() {
/*   60 */     itemProperties = (CustomItemProperties[][])null;
/*   61 */     enchantmentProperties = (CustomItemProperties[][])null;
/*   62 */     useGlint = true;
/*      */     
/*   64 */     if (Config.isCustomItems()) {
/*      */       
/*   66 */       readCitProperties("mcpatcher/cit.properties");
/*   67 */       IResourcePack[] airesourcepack = Config.getResourcePacks();
/*      */       
/*   69 */       for (int i = airesourcepack.length - 1; i >= 0; i--) {
/*      */         
/*   71 */         IResourcePack iresourcepack = airesourcepack[i];
/*   72 */         update(iresourcepack);
/*      */       } 
/*      */       
/*   75 */       update((IResourcePack)Config.getDefaultResourcePack());
/*      */       
/*   77 */       if (itemProperties.length <= 0)
/*      */       {
/*   79 */         itemProperties = (CustomItemProperties[][])null;
/*      */       }
/*      */       
/*   82 */       if (enchantmentProperties.length <= 0)
/*      */       {
/*   84 */         enchantmentProperties = (CustomItemProperties[][])null;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readCitProperties(String fileName) {
/*      */     try {
/*   93 */       ResourceLocation resourcelocation = new ResourceLocation(fileName);
/*   94 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*      */       
/*   96 */       if (inputstream == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  101 */       Config.dbg("CustomItems: Loading " + fileName);
/*  102 */       PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  103 */       propertiesOrdered.load(inputstream);
/*  104 */       inputstream.close();
/*  105 */       useGlint = Config.parseBoolean(propertiesOrdered.getProperty("useGlint"), true);
/*      */     }
/*  107 */     catch (FileNotFoundException var4) {
/*      */       
/*      */       return;
/*      */     }
/*  111 */     catch (IOException ioexception) {
/*      */       
/*  113 */       ioexception.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void update(IResourcePack rp) {
/*  119 */     String[] astring = ResUtils.collectFiles(rp, "mcpatcher/cit/", ".properties", (String[])null);
/*  120 */     Map map = makeAutoImageProperties(rp);
/*      */     
/*  122 */     if (!map.isEmpty()) {
/*      */       
/*  124 */       Set set = map.keySet();
/*  125 */       String[] astring1 = (String[])set.toArray((Object[])new String[0]);
/*  126 */       astring = (String[])Config.addObjectsToArray((Object[])astring, (Object[])astring1);
/*      */     } 
/*      */     
/*  129 */     Arrays.sort((Object[])astring);
/*  130 */     List list = makePropertyList(itemProperties);
/*  131 */     List list1 = makePropertyList(enchantmentProperties);
/*      */     
/*  133 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  135 */       String s = astring[i];
/*  136 */       Config.dbg("CustomItems: " + s);
/*      */ 
/*      */       
/*      */       try {
/*  140 */         CustomItemProperties customitemproperties = null;
/*      */         
/*  142 */         if (map.containsKey(s))
/*      */         {
/*  144 */           customitemproperties = (CustomItemProperties)map.get(s);
/*      */         }
/*      */         
/*  147 */         if (customitemproperties == null)
/*      */         
/*  149 */         { ResourceLocation resourcelocation = new ResourceLocation(s);
/*  150 */           InputStream inputstream = rp.getInputStream(resourcelocation);
/*      */           
/*  152 */           if (inputstream == null)
/*      */           
/*  154 */           { Config.warn("CustomItems file not found: " + s); }
/*      */           
/*      */           else
/*      */           
/*  158 */           { PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  159 */             propertiesOrdered.load(inputstream);
/*  160 */             inputstream.close();
/*  161 */             customitemproperties = new CustomItemProperties((Properties)propertiesOrdered, s);
/*      */ 
/*      */             
/*  164 */             if (customitemproperties.isValid(s))
/*      */             
/*  166 */             { addToItemList(customitemproperties, list);
/*  167 */               addToEnchantmentList(customitemproperties, list1); }  }  continue; }  if (customitemproperties.isValid(s)) { addToItemList(customitemproperties, list); addToEnchantmentList(customitemproperties, list1); }
/*      */ 
/*      */       
/*  170 */       } catch (FileNotFoundException var11) {
/*      */         
/*  172 */         Config.warn("CustomItems file not found: " + s);
/*      */         continue;
/*  174 */       } catch (Exception exception) {
/*      */         
/*  176 */         exception.printStackTrace();
/*      */         continue;
/*      */       } 
/*      */     } 
/*  180 */     itemProperties = propertyListToArray(list);
/*  181 */     enchantmentProperties = propertyListToArray(list1);
/*  182 */     Comparator<? super CustomItemProperties> comparator = getPropertiesComparator();
/*      */     
/*  184 */     for (int j = 0; j < itemProperties.length; j++) {
/*      */       
/*  186 */       CustomItemProperties[] acustomitemproperties = itemProperties[j];
/*      */       
/*  188 */       if (acustomitemproperties != null)
/*      */       {
/*  190 */         Arrays.sort(acustomitemproperties, comparator);
/*      */       }
/*      */     } 
/*      */     
/*  194 */     for (int k = 0; k < enchantmentProperties.length; k++) {
/*      */       
/*  196 */       CustomItemProperties[] acustomitemproperties1 = enchantmentProperties[k];
/*      */       
/*  198 */       if (acustomitemproperties1 != null)
/*      */       {
/*  200 */         Arrays.sort(acustomitemproperties1, comparator);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static Comparator getPropertiesComparator() {
/*  207 */     Comparator comparator = new Comparator()
/*      */       {
/*      */         public int compare(Object o1, Object o2)
/*      */         {
/*  211 */           CustomItemProperties customitemproperties = (CustomItemProperties)o1;
/*  212 */           CustomItemProperties customitemproperties1 = (CustomItemProperties)o2;
/*  213 */           return (customitemproperties.layer != customitemproperties1.layer) ? (customitemproperties.layer - customitemproperties1.layer) : ((customitemproperties.weight != customitemproperties1.weight) ? (customitemproperties1.weight - customitemproperties.weight) : (!customitemproperties.basePath.equals(customitemproperties1.basePath) ? customitemproperties.basePath.compareTo(customitemproperties1.basePath) : customitemproperties.name.compareTo(customitemproperties1.name)));
/*      */         }
/*      */       };
/*  216 */     return comparator;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateIcons(TextureMap textureMap) {
/*  221 */     for (CustomItemProperties customitemproperties : getAllProperties())
/*      */     {
/*  223 */       customitemproperties.updateIcons(textureMap);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void loadModels(ModelBakery modelBakery) {
/*  229 */     for (CustomItemProperties customitemproperties : getAllProperties())
/*      */     {
/*  231 */       customitemproperties.loadModels(modelBakery);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateModels() {
/*  237 */     for (CustomItemProperties customitemproperties : getAllProperties()) {
/*      */       
/*  239 */       if (customitemproperties.type == 1) {
/*      */         
/*  241 */         TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
/*  242 */         customitemproperties.updateModelTexture(texturemap, itemModelGenerator);
/*  243 */         customitemproperties.updateModelsFull();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static List<CustomItemProperties> getAllProperties() {
/*  250 */     List<CustomItemProperties> list = new ArrayList<>();
/*  251 */     addAll(itemProperties, list);
/*  252 */     addAll(enchantmentProperties, list);
/*  253 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addAll(CustomItemProperties[][] cipsArr, List<CustomItemProperties> list) {
/*  258 */     if (cipsArr != null)
/*      */     {
/*  260 */       for (int i = 0; i < cipsArr.length; i++) {
/*      */         
/*  262 */         CustomItemProperties[] acustomitemproperties = cipsArr[i];
/*      */         
/*  264 */         if (acustomitemproperties != null)
/*      */         {
/*  266 */           for (int j = 0; j < acustomitemproperties.length; j++) {
/*      */             
/*  268 */             CustomItemProperties customitemproperties = acustomitemproperties[j];
/*      */             
/*  270 */             if (customitemproperties != null)
/*      */             {
/*  272 */               list.add(customitemproperties);
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static Map makeAutoImageProperties(IResourcePack rp) {
/*  282 */     Map<Object, Object> map = new HashMap<>();
/*  283 */     map.putAll(makePotionImageProperties(rp, "normal", Item.getIdFromItem((Item)Items.potionitem)));
/*  284 */     map.putAll(makePotionImageProperties(rp, "splash", Item.getIdFromItem((Item)Items.potionitem)));
/*  285 */     map.putAll(makePotionImageProperties(rp, "linger", Item.getIdFromItem((Item)Items.potionitem)));
/*  286 */     return map;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Map makePotionImageProperties(IResourcePack rp, String type, int itemId) {
/*  291 */     Map<Object, Object> map = new HashMap<>();
/*  292 */     String s = type + "/";
/*  293 */     String[] astring = { "mcpatcher/cit/potion/" + s, "mcpatcher/cit/Potion/" + s };
/*  294 */     String[] astring1 = { ".png" };
/*  295 */     String[] astring2 = ResUtils.collectFiles(rp, astring, astring1);
/*      */     
/*  297 */     for (int i = 0; i < astring2.length; i++) {
/*      */       
/*  299 */       String s1 = astring2[i];
/*  300 */       String name = StrUtils.removePrefixSuffix(s1, astring, astring1);
/*  301 */       Properties properties = makePotionProperties(name, type, itemId, s1);
/*      */       
/*  303 */       if (properties != null) {
/*      */         
/*  305 */         String s3 = StrUtils.removeSuffix(s1, astring1) + ".properties";
/*  306 */         CustomItemProperties customitemproperties = new CustomItemProperties(properties, s3);
/*  307 */         map.put(s3, customitemproperties);
/*      */       } 
/*      */     } 
/*      */     
/*  311 */     return map;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Properties makePotionProperties(String name, String type, int itemId, String path) {
/*  316 */     if (StrUtils.endsWith(name, new String[] { "_n", "_s" }))
/*      */     {
/*  318 */       return null;
/*      */     }
/*  320 */     if (name.equals("empty") && type.equals("normal")) {
/*      */       
/*  322 */       itemId = Item.getIdFromItem(Items.glass_bottle);
/*  323 */       PropertiesOrdered propertiesOrdered1 = new PropertiesOrdered();
/*  324 */       propertiesOrdered1.put("type", "item");
/*  325 */       propertiesOrdered1.put("items", String.valueOf(itemId));
/*  326 */       return (Properties)propertiesOrdered1;
/*      */     } 
/*      */ 
/*      */     
/*  330 */     int[] aint = (int[])getMapPotionIds().get(name);
/*      */     
/*  332 */     if (aint == null) {
/*      */       
/*  334 */       Config.warn("Potion not found for image: " + path);
/*  335 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  339 */     StringBuffer stringbuffer = new StringBuffer();
/*      */     
/*  341 */     for (int i = 0; i < aint.length; i++) {
/*      */       
/*  343 */       int j = aint[i];
/*      */       
/*  345 */       if (type.equals("splash"))
/*      */       {
/*  347 */         j |= 0x4000;
/*      */       }
/*      */       
/*  350 */       if (i > 0)
/*      */       {
/*  352 */         stringbuffer.append(" ");
/*      */       }
/*      */       
/*  355 */       stringbuffer.append(j);
/*      */     } 
/*      */     
/*  358 */     int k = 16447;
/*      */     
/*  360 */     if (name.equals("water") || name.equals("mundane"))
/*      */     {
/*  362 */       k |= 0x40;
/*      */     }
/*      */     
/*  365 */     PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  366 */     propertiesOrdered.put("type", "item");
/*  367 */     propertiesOrdered.put("items", String.valueOf(itemId));
/*  368 */     propertiesOrdered.put("damage", stringbuffer);
/*  369 */     propertiesOrdered.put("damageMask", String.valueOf(k));
/*      */     
/*  371 */     if (type.equals("splash")) {
/*      */       
/*  373 */       propertiesOrdered.put("texture.potion_bottle_splash", name);
/*      */     }
/*      */     else {
/*      */       
/*  377 */       propertiesOrdered.put("texture.potion_bottle_drinkable", name);
/*      */     } 
/*      */     
/*  380 */     return (Properties)propertiesOrdered;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Map getMapPotionIds() {
/*  387 */     if (mapPotionIds == null) {
/*      */       
/*  389 */       mapPotionIds = new LinkedHashMap<>();
/*  390 */       mapPotionIds.put("water", getPotionId(0, 0));
/*  391 */       mapPotionIds.put("awkward", getPotionId(0, 1));
/*  392 */       mapPotionIds.put("thick", getPotionId(0, 2));
/*  393 */       mapPotionIds.put("potent", getPotionId(0, 3));
/*  394 */       mapPotionIds.put("regeneration", getPotionIds(1));
/*  395 */       mapPotionIds.put("movespeed", getPotionIds(2));
/*  396 */       mapPotionIds.put("fireresistance", getPotionIds(3));
/*  397 */       mapPotionIds.put("poison", getPotionIds(4));
/*  398 */       mapPotionIds.put("heal", getPotionIds(5));
/*  399 */       mapPotionIds.put("nightvision", getPotionIds(6));
/*  400 */       mapPotionIds.put("clear", getPotionId(7, 0));
/*  401 */       mapPotionIds.put("bungling", getPotionId(7, 1));
/*  402 */       mapPotionIds.put("charming", getPotionId(7, 2));
/*  403 */       mapPotionIds.put("rank", getPotionId(7, 3));
/*  404 */       mapPotionIds.put("weakness", getPotionIds(8));
/*  405 */       mapPotionIds.put("damageboost", getPotionIds(9));
/*  406 */       mapPotionIds.put("moveslowdown", getPotionIds(10));
/*  407 */       mapPotionIds.put("leaping", getPotionIds(11));
/*  408 */       mapPotionIds.put("harm", getPotionIds(12));
/*  409 */       mapPotionIds.put("waterbreathing", getPotionIds(13));
/*  410 */       mapPotionIds.put("invisibility", getPotionIds(14));
/*  411 */       mapPotionIds.put("thin", getPotionId(15, 0));
/*  412 */       mapPotionIds.put("debonair", getPotionId(15, 1));
/*  413 */       mapPotionIds.put("sparkling", getPotionId(15, 2));
/*  414 */       mapPotionIds.put("stinky", getPotionId(15, 3));
/*  415 */       mapPotionIds.put("mundane", getPotionId(0, 4));
/*  416 */       mapPotionIds.put("speed", mapPotionIds.get("movespeed"));
/*  417 */       mapPotionIds.put("fire_resistance", mapPotionIds.get("fireresistance"));
/*  418 */       mapPotionIds.put("instant_health", mapPotionIds.get("heal"));
/*  419 */       mapPotionIds.put("night_vision", mapPotionIds.get("nightvision"));
/*  420 */       mapPotionIds.put("strength", mapPotionIds.get("damageboost"));
/*  421 */       mapPotionIds.put("slowness", mapPotionIds.get("moveslowdown"));
/*  422 */       mapPotionIds.put("instant_damage", mapPotionIds.get("harm"));
/*  423 */       mapPotionIds.put("water_breathing", mapPotionIds.get("waterbreathing"));
/*      */     } 
/*      */     
/*  426 */     return mapPotionIds;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int[] getPotionIds(int baseId) {
/*  431 */     return new int[] { baseId, baseId + 16, baseId + 32, baseId + 48 };
/*      */   }
/*      */ 
/*      */   
/*      */   private static int[] getPotionId(int baseId, int subId) {
/*  436 */     return new int[] { baseId + (subId << 4) };
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getPotionNameDamage(String name) {
/*  441 */     String s = "potion." + name;
/*  442 */     Potion[] apotion = Potion.potionTypes;
/*      */     
/*  444 */     for (int i = 0; i < apotion.length; i++) {
/*      */       
/*  446 */       Potion potion = apotion[i];
/*      */       
/*  448 */       if (potion != null) {
/*      */         
/*  450 */         String s1 = potion.getName();
/*      */         
/*  452 */         if (s.equals(s1))
/*      */         {
/*  454 */           return potion.getId();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  459 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static List makePropertyList(CustomItemProperties[][] propsArr) {
/*  464 */     List<List> list = new ArrayList();
/*      */     
/*  466 */     if (propsArr != null)
/*      */     {
/*  468 */       for (int i = 0; i < propsArr.length; i++) {
/*      */         
/*  470 */         CustomItemProperties[] acustomitemproperties = propsArr[i];
/*  471 */         List list1 = null;
/*      */         
/*  473 */         if (acustomitemproperties != null)
/*      */         {
/*  475 */           list1 = new ArrayList(Arrays.asList((Object[])acustomitemproperties));
/*      */         }
/*      */         
/*  478 */         list.add(list1);
/*      */       } 
/*      */     }
/*      */     
/*  482 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   private static CustomItemProperties[][] propertyListToArray(List<List> lists) {
/*  487 */     CustomItemProperties[][] acustomitemproperties = new CustomItemProperties[lists.size()][];
/*      */     
/*  489 */     for (int i = 0; i < lists.size(); i++) {
/*      */       
/*  491 */       List list = lists.get(i);
/*      */       
/*  493 */       if (list != null) {
/*      */         
/*  495 */         CustomItemProperties[] acustomitemproperties1 = (CustomItemProperties[])list.toArray((Object[])new CustomItemProperties[0]);
/*  496 */         Arrays.sort(acustomitemproperties1, new CustomItemsComparator());
/*  497 */         acustomitemproperties[i] = acustomitemproperties1;
/*      */       } 
/*      */     } 
/*      */     
/*  501 */     return acustomitemproperties;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToItemList(CustomItemProperties cp, List itemList) {
/*  506 */     if (cp.items != null)
/*      */     {
/*  508 */       for (int i = 0; i < cp.items.length; i++) {
/*      */         
/*  510 */         int j = cp.items[i];
/*      */         
/*  512 */         if (j <= 0) {
/*      */           
/*  514 */           Config.warn("Invalid item ID: " + j);
/*      */         }
/*      */         else {
/*      */           
/*  518 */           addToList(cp, itemList, j);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToEnchantmentList(CustomItemProperties cp, List enchantmentList) {
/*  526 */     if (cp.type == 2)
/*      */     {
/*  528 */       if (cp.enchantmentIds != null)
/*      */       {
/*  530 */         for (int i = 0; i < 256; i++) {
/*      */           
/*  532 */           if (cp.enchantmentIds.isInRange(i))
/*      */           {
/*  534 */             addToList(cp, enchantmentList, i);
/*      */           }
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToList(CustomItemProperties cp, List<List> lists, int id) {
/*  543 */     while (id >= lists.size())
/*      */     {
/*  545 */       lists.add(null);
/*      */     }
/*      */     
/*  548 */     List<List> list = lists.get(id);
/*      */     
/*  550 */     if (list == null) {
/*      */       
/*  552 */       list = new ArrayList();
/*  553 */       list.set(id, list);
/*      */     } 
/*      */     
/*  556 */     list.add(cp);
/*      */   }
/*      */ 
/*      */   
/*      */   public static IBakedModel getCustomItemModel(ItemStack itemStack, IBakedModel model, ResourceLocation modelLocation, boolean fullModel) {
/*  561 */     if (!fullModel && model.isGui3d())
/*      */     {
/*  563 */       return model;
/*      */     }
/*  565 */     if (itemProperties == null)
/*      */     {
/*  567 */       return model;
/*      */     }
/*      */ 
/*      */     
/*  571 */     CustomItemProperties customitemproperties = getCustomItemProperties(itemStack, 1);
/*      */     
/*  573 */     if (customitemproperties == null)
/*      */     {
/*  575 */       return model;
/*      */     }
/*      */ 
/*      */     
/*  579 */     IBakedModel ibakedmodel = customitemproperties.getBakedModel(modelLocation, fullModel);
/*  580 */     return (ibakedmodel != null) ? ibakedmodel : model;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean bindCustomArmorTexture(ItemStack itemStack, int layer, String overlay) {
/*  587 */     if (itemProperties == null)
/*      */     {
/*  589 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  593 */     ResourceLocation resourcelocation = getCustomArmorLocation(itemStack, layer, overlay);
/*      */     
/*  595 */     if (resourcelocation == null)
/*      */     {
/*  597 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  601 */     Config.getTextureManager().bindTexture(resourcelocation);
/*  602 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ResourceLocation getCustomArmorLocation(ItemStack itemStack, int layer, String overlay) {
/*  609 */     CustomItemProperties customitemproperties = getCustomItemProperties(itemStack, 3);
/*      */     
/*  611 */     if (customitemproperties == null)
/*      */     {
/*  613 */       return null;
/*      */     }
/*  615 */     if (customitemproperties.mapTextureLocations == null)
/*      */     {
/*  617 */       return customitemproperties.textureLocation;
/*      */     }
/*      */ 
/*      */     
/*  621 */     Item item = itemStack.getItem();
/*      */     
/*  623 */     if (!(item instanceof ItemArmor))
/*      */     {
/*  625 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  629 */     ItemArmor itemarmor = (ItemArmor)item;
/*  630 */     String s = itemarmor.getArmorMaterial().getName();
/*  631 */     StringBuffer stringbuffer = new StringBuffer();
/*  632 */     stringbuffer.append("texture.");
/*  633 */     stringbuffer.append(s);
/*  634 */     stringbuffer.append("_layer_");
/*  635 */     stringbuffer.append(layer);
/*      */     
/*  637 */     if (overlay != null) {
/*      */       
/*  639 */       stringbuffer.append("_");
/*  640 */       stringbuffer.append(overlay);
/*      */     } 
/*      */     
/*  643 */     String s1 = stringbuffer.toString();
/*  644 */     ResourceLocation resourcelocation = (ResourceLocation)customitemproperties.mapTextureLocations.get(s1);
/*  645 */     return (resourcelocation == null) ? customitemproperties.textureLocation : resourcelocation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static CustomItemProperties getCustomItemProperties(ItemStack itemStack, int type) {
/*  652 */     if (itemProperties == null)
/*      */     {
/*  654 */       return null;
/*      */     }
/*  656 */     if (itemStack == null)
/*      */     {
/*  658 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  662 */     Item item = itemStack.getItem();
/*  663 */     int i = Item.getIdFromItem(item);
/*      */     
/*  665 */     if (i >= 0 && i < itemProperties.length) {
/*      */       
/*  667 */       CustomItemProperties[] acustomitemproperties = itemProperties[i];
/*      */       
/*  669 */       if (acustomitemproperties != null)
/*      */       {
/*  671 */         for (int j = 0; j < acustomitemproperties.length; j++) {
/*      */           
/*  673 */           CustomItemProperties customitemproperties = acustomitemproperties[j];
/*      */           
/*  675 */           if (customitemproperties.type == type && matchesProperties(customitemproperties, itemStack, (int[][])null))
/*      */           {
/*  677 */             return customitemproperties;
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  683 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean matchesProperties(CustomItemProperties cip, ItemStack itemStack, int[][] enchantmentIdLevels) {
/*  689 */     Item item = itemStack.getItem();
/*      */     
/*  691 */     if (cip.damage != null) {
/*      */       
/*  693 */       int i = itemStack.getItemDamage();
/*      */       
/*  695 */       if (cip.damageMask != 0)
/*      */       {
/*  697 */         i &= cip.damageMask;
/*      */       }
/*      */       
/*  700 */       if (cip.damagePercent) {
/*      */         
/*  702 */         int j = item.getMaxDamage();
/*  703 */         i = (int)((i * 100) / j);
/*      */       } 
/*      */       
/*  706 */       if (!cip.damage.isInRange(i))
/*      */       {
/*  708 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  712 */     if (cip.stackSize != null && !cip.stackSize.isInRange(itemStack.stackSize))
/*      */     {
/*  714 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  718 */     int[][] aint = enchantmentIdLevels;
/*      */     
/*  720 */     if (cip.enchantmentIds != null) {
/*      */       
/*  722 */       if (enchantmentIdLevels == null)
/*      */       {
/*  724 */         aint = getEnchantmentIdLevels(itemStack);
/*      */       }
/*      */       
/*  727 */       boolean flag = false;
/*      */       
/*  729 */       for (int k = 0; k < aint.length; k++) {
/*      */         
/*  731 */         int l = aint[k][0];
/*      */         
/*  733 */         if (cip.enchantmentIds.isInRange(l)) {
/*      */           
/*  735 */           flag = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*  740 */       if (!flag)
/*      */       {
/*  742 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  746 */     if (cip.enchantmentLevels != null) {
/*      */       
/*  748 */       if (aint == null)
/*      */       {
/*  750 */         aint = getEnchantmentIdLevels(itemStack);
/*      */       }
/*      */       
/*  753 */       boolean flag1 = false;
/*      */       
/*  755 */       for (int i1 = 0; i1 < aint.length; i1++) {
/*      */         
/*  757 */         int k1 = aint[i1][1];
/*      */         
/*  759 */         if (cip.enchantmentLevels.isInRange(k1)) {
/*      */           
/*  761 */           flag1 = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*  766 */       if (!flag1)
/*      */       {
/*  768 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  772 */     if (cip.nbtTagValues != null) {
/*      */       
/*  774 */       NBTTagCompound nbttagcompound = itemStack.getTagCompound();
/*      */       
/*  776 */       for (int j1 = 0; j1 < cip.nbtTagValues.length; j1++) {
/*      */         
/*  778 */         NbtTagValue nbttagvalue = cip.nbtTagValues[j1];
/*      */         
/*  780 */         if (!nbttagvalue.matches(nbttagcompound))
/*      */         {
/*  782 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  787 */     if (cip.hand != 0) {
/*      */       
/*  789 */       if (cip.hand == 1);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  794 */       if (cip.hand == 2)
/*      */       {
/*  796 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  800 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[][] getEnchantmentIdLevels(ItemStack itemStack) {
/*  806 */     Item item = itemStack.getItem();
/*  807 */     NBTTagList nbttaglist = (item == Items.enchanted_book) ? Items.enchanted_book.getEnchantments(itemStack) : itemStack.getEnchantmentTagList();
/*      */     
/*  809 */     if (nbttaglist != null && nbttaglist.tagCount() > 0) {
/*      */       
/*  811 */       int[][] aint = new int[nbttaglist.tagCount()][2];
/*      */       
/*  813 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*      */         
/*  815 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*  816 */         int j = nbttagcompound.getShort("id");
/*  817 */         int k = nbttagcompound.getShort("lvl");
/*  818 */         aint[i][0] = j;
/*  819 */         aint[i][1] = k;
/*      */       } 
/*      */       
/*  822 */       return aint;
/*      */     } 
/*      */ 
/*      */     
/*  826 */     return EMPTY_INT2_ARRAY;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean renderCustomEffect(RenderItem renderItem, ItemStack itemStack, IBakedModel model) {
/*  832 */     if (enchantmentProperties == null)
/*      */     {
/*  834 */       return false;
/*      */     }
/*  836 */     if (itemStack == null)
/*      */     {
/*  838 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  842 */     int[][] aint = getEnchantmentIdLevels(itemStack);
/*      */     
/*  844 */     if (aint.length <= 0)
/*      */     {
/*  846 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  850 */     Set<Integer> set = null;
/*  851 */     boolean flag = false;
/*  852 */     TextureManager texturemanager = Config.getTextureManager();
/*      */     
/*  854 */     for (int i = 0; i < aint.length; i++) {
/*      */       
/*  856 */       int j = aint[i][0];
/*      */       
/*  858 */       if (j >= 0 && j < enchantmentProperties.length) {
/*      */         
/*  860 */         CustomItemProperties[] acustomitemproperties = enchantmentProperties[j];
/*      */         
/*  862 */         if (acustomitemproperties != null)
/*      */         {
/*  864 */           for (int k = 0; k < acustomitemproperties.length; k++) {
/*      */             
/*  866 */             CustomItemProperties customitemproperties = acustomitemproperties[k];
/*      */             
/*  868 */             if (set == null)
/*      */             {
/*  870 */               set = new HashSet();
/*      */             }
/*      */             
/*  873 */             if (set.add(Integer.valueOf(j)) && matchesProperties(customitemproperties, itemStack, aint) && customitemproperties.textureLocation != null) {
/*      */               
/*  875 */               texturemanager.bindTexture(customitemproperties.textureLocation);
/*  876 */               float f = customitemproperties.getTextureWidth(texturemanager);
/*      */               
/*  878 */               if (!flag) {
/*      */                 
/*  880 */                 flag = true;
/*  881 */                 GlStateManager.depthMask(false);
/*  882 */                 GlStateManager.depthFunc(514);
/*  883 */                 GlStateManager.disableLighting();
/*  884 */                 GlStateManager.matrixMode(5890);
/*      */               } 
/*      */               
/*  887 */               Blender.setupBlend(customitemproperties.blend, 1.0F);
/*  888 */               GlStateManager.pushMatrix();
/*  889 */               GlStateManager.scale(f / 2.0F, f / 2.0F, f / 2.0F);
/*  890 */               float f1 = customitemproperties.speed * (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
/*  891 */               GlStateManager.translate(f1, 0.0F, 0.0F);
/*  892 */               GlStateManager.rotate(customitemproperties.rotation, 0.0F, 0.0F, 1.0F);
/*  893 */               renderItem.renderModel(model, -1);
/*  894 */               GlStateManager.popMatrix();
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  901 */     if (flag) {
/*      */       
/*  903 */       GlStateManager.enableAlpha();
/*  904 */       GlStateManager.enableBlend();
/*  905 */       GlStateManager.blendFunc(770, 771);
/*  906 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  907 */       GlStateManager.matrixMode(5888);
/*  908 */       GlStateManager.enableLighting();
/*  909 */       GlStateManager.depthFunc(515);
/*  910 */       GlStateManager.depthMask(true);
/*  911 */       texturemanager.bindTexture(TextureMap.locationBlocksTexture);
/*      */     } 
/*      */     
/*  914 */     return flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean renderCustomArmorEffect(EntityLivingBase entity, ItemStack itemStack, ModelBase model, float limbSwing, float prevLimbSwing, float partialTicks, float timeLimbSwing, float yaw, float pitch, float scale) {
/*  921 */     if (enchantmentProperties == null)
/*      */     {
/*  923 */       return false;
/*      */     }
/*  925 */     if (Config.isShaders() && Shaders.isShadowPass)
/*      */     {
/*  927 */       return false;
/*      */     }
/*  929 */     if (itemStack == null)
/*      */     {
/*  931 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  935 */     int[][] aint = getEnchantmentIdLevels(itemStack);
/*      */     
/*  937 */     if (aint.length <= 0)
/*      */     {
/*  939 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  943 */     Set<Integer> set = null;
/*  944 */     boolean flag = false;
/*  945 */     TextureManager texturemanager = Config.getTextureManager();
/*      */     
/*  947 */     for (int i = 0; i < aint.length; i++) {
/*      */       
/*  949 */       int j = aint[i][0];
/*      */       
/*  951 */       if (j >= 0 && j < enchantmentProperties.length) {
/*      */         
/*  953 */         CustomItemProperties[] acustomitemproperties = enchantmentProperties[j];
/*      */         
/*  955 */         if (acustomitemproperties != null)
/*      */         {
/*  957 */           for (int k = 0; k < acustomitemproperties.length; k++) {
/*      */             
/*  959 */             CustomItemProperties customitemproperties = acustomitemproperties[k];
/*      */             
/*  961 */             if (set == null)
/*      */             {
/*  963 */               set = new HashSet();
/*      */             }
/*      */             
/*  966 */             if (set.add(Integer.valueOf(j)) && matchesProperties(customitemproperties, itemStack, aint) && customitemproperties.textureLocation != null) {
/*      */               
/*  968 */               texturemanager.bindTexture(customitemproperties.textureLocation);
/*  969 */               float f = customitemproperties.getTextureWidth(texturemanager);
/*      */               
/*  971 */               if (!flag) {
/*      */                 
/*  973 */                 flag = true;
/*      */                 
/*  975 */                 if (Config.isShaders())
/*      */                 {
/*  977 */                   ShadersRender.renderEnchantedGlintBegin();
/*      */                 }
/*      */                 
/*  980 */                 GlStateManager.enableBlend();
/*  981 */                 GlStateManager.depthFunc(514);
/*  982 */                 GlStateManager.depthMask(false);
/*      */               } 
/*      */               
/*  985 */               Blender.setupBlend(customitemproperties.blend, 1.0F);
/*  986 */               GlStateManager.disableLighting();
/*  987 */               GlStateManager.matrixMode(5890);
/*  988 */               GlStateManager.loadIdentity();
/*  989 */               GlStateManager.rotate(customitemproperties.rotation, 0.0F, 0.0F, 1.0F);
/*  990 */               float f1 = f / 8.0F;
/*  991 */               GlStateManager.scale(f1, f1 / 2.0F, f1);
/*  992 */               float f2 = customitemproperties.speed * (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
/*  993 */               GlStateManager.translate(0.0F, f2, 0.0F);
/*  994 */               GlStateManager.matrixMode(5888);
/*  995 */               model.render((Entity)entity, limbSwing, prevLimbSwing, timeLimbSwing, yaw, pitch, scale);
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1002 */     if (flag) {
/*      */       
/* 1004 */       GlStateManager.enableAlpha();
/* 1005 */       GlStateManager.enableBlend();
/* 1006 */       GlStateManager.blendFunc(770, 771);
/* 1007 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1008 */       GlStateManager.matrixMode(5890);
/* 1009 */       GlStateManager.loadIdentity();
/* 1010 */       GlStateManager.matrixMode(5888);
/* 1011 */       GlStateManager.enableLighting();
/* 1012 */       GlStateManager.depthMask(true);
/* 1013 */       GlStateManager.depthFunc(515);
/* 1014 */       GlStateManager.disableBlend();
/*      */       
/* 1016 */       if (Config.isShaders())
/*      */       {
/* 1018 */         ShadersRender.renderEnchantedGlintEnd();
/*      */       }
/*      */     } 
/*      */     
/* 1022 */     return flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isUseGlint() {
/* 1029 */     return useGlint;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\CustomItems.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */