/*      */ package net.optifine;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.block.model.BlockPart;
/*      */ import net.minecraft.client.renderer.block.model.BlockPartFace;
/*      */ import net.minecraft.client.renderer.block.model.ModelBlock;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.resources.model.IBakedModel;
/*      */ import net.minecraft.client.resources.model.ModelBakery;
/*      */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*      */ import net.minecraft.client.resources.model.ModelRotation;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemArmor;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.optifine.config.NbtTagValue;
/*      */ import net.optifine.config.RangeInt;
/*      */ import net.optifine.config.RangeListInt;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.util.StrUtils;
/*      */ 
/*      */ public class CustomItemProperties {
/*   27 */   public String name = null;
/*   28 */   public String basePath = null;
/*   29 */   public int type = 1;
/*   30 */   public int[] items = null;
/*   31 */   public String texture = null;
/*   32 */   public Map<String, String> mapTextures = null;
/*   33 */   public String model = null;
/*   34 */   public Map<String, String> mapModels = null;
/*   35 */   public RangeListInt damage = null;
/*      */   public boolean damagePercent = false;
/*   37 */   public int damageMask = 0;
/*   38 */   public RangeListInt stackSize = null;
/*   39 */   public RangeListInt enchantmentIds = null;
/*   40 */   public RangeListInt enchantmentLevels = null;
/*   41 */   public NbtTagValue[] nbtTagValues = null;
/*   42 */   public int hand = 0;
/*   43 */   public int blend = 1;
/*   44 */   public float speed = 0.0F;
/*   45 */   public float rotation = 0.0F;
/*   46 */   public int layer = 0;
/*   47 */   public float duration = 1.0F;
/*   48 */   public int weight = 0;
/*   49 */   public ResourceLocation textureLocation = null;
/*   50 */   public Map mapTextureLocations = null;
/*   51 */   public TextureAtlasSprite sprite = null;
/*   52 */   public Map mapSprites = null;
/*   53 */   public IBakedModel bakedModelTexture = null;
/*   54 */   public Map<String, IBakedModel> mapBakedModelsTexture = null;
/*   55 */   public IBakedModel bakedModelFull = null;
/*   56 */   public Map<String, IBakedModel> mapBakedModelsFull = null;
/*   57 */   private int textureWidth = 0;
/*   58 */   private int textureHeight = 0;
/*      */   
/*      */   public static final int TYPE_UNKNOWN = 0;
/*      */   public static final int TYPE_ITEM = 1;
/*      */   public static final int TYPE_ENCHANTMENT = 2;
/*      */   public static final int TYPE_ARMOR = 3;
/*      */   public static final int HAND_ANY = 0;
/*      */   public static final int HAND_MAIN = 1;
/*      */   public static final int HAND_OFF = 2;
/*      */   public static final String INVENTORY = "inventory";
/*      */   
/*      */   public CustomItemProperties(Properties props, String path) {
/*   70 */     this.name = parseName(path);
/*   71 */     this.basePath = parseBasePath(path);
/*   72 */     this.type = parseType(props.getProperty("type"));
/*   73 */     this.items = parseItems(props.getProperty("items"), props.getProperty("matchItems"));
/*   74 */     this.mapModels = parseModels(props, this.basePath);
/*   75 */     this.model = parseModel(props.getProperty("model"), path, this.basePath, this.type, this.mapModels);
/*   76 */     this.mapTextures = parseTextures(props, this.basePath);
/*   77 */     boolean flag = (this.mapModels == null && this.model == null);
/*   78 */     this.texture = parseTexture(props.getProperty("texture"), props.getProperty("tile"), props.getProperty("source"), path, this.basePath, this.type, this.mapTextures, flag);
/*   79 */     String s = props.getProperty("damage");
/*      */     
/*   81 */     if (s != null) {
/*      */       
/*   83 */       this.damagePercent = s.contains("%");
/*   84 */       s = s.replace("%", "");
/*   85 */       this.damage = parseRangeListInt(s);
/*   86 */       this.damageMask = parseInt(props.getProperty("damageMask"), 0);
/*      */     } 
/*      */     
/*   89 */     this.stackSize = parseRangeListInt(props.getProperty("stackSize"));
/*   90 */     this.enchantmentIds = parseRangeListInt(props.getProperty("enchantmentIDs"), (IParserInt)new ParserEnchantmentId());
/*   91 */     this.enchantmentLevels = parseRangeListInt(props.getProperty("enchantmentLevels"));
/*   92 */     this.nbtTagValues = parseNbtTagValues(props);
/*   93 */     this.hand = parseHand(props.getProperty("hand"));
/*   94 */     this.blend = Blender.parseBlend(props.getProperty("blend"));
/*   95 */     this.speed = parseFloat(props.getProperty("speed"), 0.0F);
/*   96 */     this.rotation = parseFloat(props.getProperty("rotation"), 0.0F);
/*   97 */     this.layer = parseInt(props.getProperty("layer"), 0);
/*   98 */     this.weight = parseInt(props.getProperty("weight"), 0);
/*   99 */     this.duration = parseFloat(props.getProperty("duration"), 1.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   private static String parseName(String path) {
/*  104 */     String s = path;
/*  105 */     int i = path.lastIndexOf('/');
/*      */     
/*  107 */     if (i >= 0)
/*      */     {
/*  109 */       s = path.substring(i + 1);
/*      */     }
/*      */     
/*  112 */     int j = s.lastIndexOf('.');
/*      */     
/*  114 */     if (j >= 0)
/*      */     {
/*  116 */       s = s.substring(0, j);
/*      */     }
/*      */     
/*  119 */     return s;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String parseBasePath(String path) {
/*  124 */     int i = path.lastIndexOf('/');
/*  125 */     return (i < 0) ? "" : path.substring(0, i);
/*      */   }
/*      */ 
/*      */   
/*      */   private int parseType(String str) {
/*  130 */     if (str == null)
/*      */     {
/*  132 */       return 1;
/*      */     }
/*  134 */     if (str.equals("item"))
/*      */     {
/*  136 */       return 1;
/*      */     }
/*  138 */     if (str.equals("enchantment"))
/*      */     {
/*  140 */       return 2;
/*      */     }
/*  142 */     if (str.equals("armor"))
/*      */     {
/*  144 */       return 3;
/*      */     }
/*      */ 
/*      */     
/*  148 */     Config.warn("Unknown method: " + str);
/*  149 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] parseItems(String str, String str2) {
/*  155 */     if (str == null)
/*      */     {
/*  157 */       str = str2;
/*      */     }
/*      */     
/*  160 */     if (str == null)
/*      */     {
/*  162 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  166 */     str = str.trim();
/*  167 */     Set<Integer> set = new TreeSet();
/*  168 */     String[] astring = Config.tokenize(str, " ");
/*      */ 
/*      */     
/*  171 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  173 */       String s = astring[i];
/*  174 */       int j = Config.parseInt(s, -1);
/*      */       
/*  176 */       if (j >= 0) {
/*      */         
/*  178 */         set.add(new Integer(j));
/*      */         
/*      */         continue;
/*      */       } 
/*  182 */       if (s.contains("-")) {
/*      */         
/*  184 */         String[] astring1 = Config.tokenize(s, "-");
/*      */         
/*  186 */         if (astring1.length == 2) {
/*      */           
/*  188 */           int k = Config.parseInt(astring1[0], -1);
/*  189 */           int l = Config.parseInt(astring1[1], -1);
/*      */           
/*  191 */           if (k >= 0 && l >= 0) {
/*      */             
/*  193 */             int i1 = Math.min(k, l);
/*  194 */             int j1 = Math.max(k, l);
/*  195 */             int k1 = i1;
/*      */ 
/*      */ 
/*      */             
/*  199 */             while (k1 <= j1) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  204 */               set.add(new Integer(k1));
/*  205 */               k1++;
/*      */             } 
/*      */             continue;
/*      */           } 
/*      */         } 
/*      */       } 
/*  211 */       Item item = Item.getByNameOrId(s);
/*      */       
/*  213 */       if (item == null) {
/*      */         
/*  215 */         Config.warn("Item not found: " + s);
/*      */       }
/*      */       else {
/*      */         
/*  219 */         int i2 = Item.getIdFromItem(item);
/*      */         
/*  221 */         if (i2 <= 0) {
/*      */           
/*  223 */           Config.warn("Item not found: " + s);
/*      */         }
/*      */         else {
/*      */           
/*  227 */           set.add(new Integer(i2));
/*      */         } 
/*      */       } 
/*      */       
/*      */       continue;
/*      */     } 
/*  233 */     Integer[] ainteger = set.<Integer>toArray(new Integer[0]);
/*  234 */     int[] aint = new int[ainteger.length];
/*      */     
/*  236 */     for (int l1 = 0; l1 < aint.length; l1++)
/*      */     {
/*  238 */       aint[l1] = ainteger[l1].intValue();
/*      */     }
/*      */     
/*  241 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String parseTexture(String texStr, String texStr2, String texStr3, String path, String basePath, int type, Map<String, String> mapTexs, boolean textureFromPath) {
/*  247 */     if (texStr == null)
/*      */     {
/*  249 */       texStr = texStr2;
/*      */     }
/*      */     
/*  252 */     if (texStr == null)
/*      */     {
/*  254 */       texStr = texStr3;
/*      */     }
/*      */     
/*  257 */     if (texStr != null) {
/*      */       
/*  259 */       String s2 = ".png";
/*      */       
/*  261 */       if (texStr.endsWith(s2))
/*      */       {
/*  263 */         texStr = texStr.substring(0, texStr.length() - s2.length());
/*      */       }
/*      */       
/*  266 */       texStr = fixTextureName(texStr, basePath);
/*  267 */       return texStr;
/*      */     } 
/*  269 */     if (type == 3)
/*      */     {
/*  271 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  275 */     if (mapTexs != null) {
/*      */       
/*  277 */       String s = mapTexs.get("texture.bow_standby");
/*      */       
/*  279 */       if (s != null)
/*      */       {
/*  281 */         return s;
/*      */       }
/*      */     } 
/*      */     
/*  285 */     if (!textureFromPath)
/*      */     {
/*  287 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  291 */     String s1 = path;
/*  292 */     int i = path.lastIndexOf('/');
/*      */     
/*  294 */     if (i >= 0)
/*      */     {
/*  296 */       s1 = path.substring(i + 1);
/*      */     }
/*      */     
/*  299 */     int j = s1.lastIndexOf('.');
/*      */     
/*  301 */     if (j >= 0)
/*      */     {
/*  303 */       s1 = s1.substring(0, j);
/*      */     }
/*      */     
/*  306 */     s1 = fixTextureName(s1, basePath);
/*  307 */     return s1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Map parseTextures(Properties props, String basePath) {
/*  314 */     String s = "texture.";
/*  315 */     Map map = getMatchingProperties(props, s);
/*      */     
/*  317 */     if (map.size() <= 0)
/*      */     {
/*  319 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  323 */     Set set = map.keySet();
/*  324 */     Map<Object, Object> map1 = new LinkedHashMap<>();
/*      */     
/*  326 */     for (Object e : set) {
/*      */       
/*  328 */       String s1 = (String)e;
/*  329 */       String s2 = (String)map.get(s1);
/*  330 */       s2 = fixTextureName(s2, basePath);
/*  331 */       map1.put(s1, s2);
/*      */     } 
/*      */     
/*  334 */     return map1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String fixTextureName(String iconName, String basePath) {
/*  340 */     iconName = TextureUtils.fixResourcePath(iconName, basePath);
/*      */     
/*  342 */     if (!iconName.startsWith(basePath) && !iconName.startsWith("textures/") && !iconName.startsWith("mcpatcher/"))
/*      */     {
/*  344 */       iconName = basePath + "/" + iconName;
/*      */     }
/*      */     
/*  347 */     if (iconName.endsWith(".png"))
/*      */     {
/*  349 */       iconName = iconName.substring(0, iconName.length() - 4);
/*      */     }
/*      */     
/*  352 */     if (!iconName.isEmpty() && iconName.charAt(0) == '/')
/*      */     {
/*  354 */       iconName = iconName.substring(1);
/*      */     }
/*      */     
/*  357 */     return iconName;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String parseModel(String modelStr, String path, String basePath, int type, Map<String, String> mapModelNames) {
/*  362 */     if (modelStr != null) {
/*      */       
/*  364 */       String s1 = ".json";
/*      */       
/*  366 */       if (modelStr.endsWith(s1))
/*      */       {
/*  368 */         modelStr = modelStr.substring(0, modelStr.length() - s1.length());
/*      */       }
/*      */       
/*  371 */       modelStr = fixModelName(modelStr, basePath);
/*  372 */       return modelStr;
/*      */     } 
/*  374 */     if (type == 3)
/*      */     {
/*  376 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  380 */     if (mapModelNames != null) {
/*      */       
/*  382 */       String s = mapModelNames.get("model.bow_standby");
/*      */       
/*  384 */       if (s != null)
/*      */       {
/*  386 */         return s;
/*      */       }
/*      */     } 
/*      */     
/*  390 */     return modelStr;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Map parseModels(Properties props, String basePath) {
/*  396 */     String s = "model.";
/*  397 */     Map map = getMatchingProperties(props, s);
/*      */     
/*  399 */     if (map.size() <= 0)
/*      */     {
/*  401 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  405 */     Set set = map.keySet();
/*  406 */     Map<Object, Object> map1 = new LinkedHashMap<>();
/*      */     
/*  408 */     for (Object e : set) {
/*      */       
/*  410 */       String s1 = (String)e;
/*  411 */       String s2 = (String)map.get(s1);
/*  412 */       s2 = fixModelName(s2, basePath);
/*  413 */       map1.put(s1, s2);
/*      */     } 
/*      */     
/*  416 */     return map1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String fixModelName(String modelName, String basePath) {
/*  422 */     modelName = TextureUtils.fixResourcePath(modelName, basePath);
/*  423 */     boolean flag = (modelName.startsWith("block/") || modelName.startsWith("item/"));
/*      */     
/*  425 */     if (!modelName.startsWith(basePath) && !flag && !modelName.startsWith("mcpatcher/"))
/*      */     {
/*  427 */       modelName = basePath + "/" + modelName;
/*      */     }
/*      */     
/*  430 */     String s = ".json";
/*      */     
/*  432 */     if (modelName.endsWith(s))
/*      */     {
/*  434 */       modelName = modelName.substring(0, modelName.length() - s.length());
/*      */     }
/*      */     
/*  437 */     if (!modelName.isEmpty() && modelName.charAt(0) == '/')
/*      */     {
/*  439 */       modelName = modelName.substring(1);
/*      */     }
/*      */     
/*  442 */     return modelName;
/*      */   }
/*      */ 
/*      */   
/*      */   private int parseInt(String str, int defVal) {
/*  447 */     if (str == null)
/*      */     {
/*  449 */       return defVal;
/*      */     }
/*      */ 
/*      */     
/*  453 */     str = str.trim();
/*  454 */     int i = Config.parseInt(str, -2147483648);
/*      */     
/*  456 */     if (i == Integer.MIN_VALUE) {
/*      */       
/*  458 */       Config.warn("Invalid integer: " + str);
/*  459 */       return defVal;
/*      */     } 
/*      */ 
/*      */     
/*  463 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float parseFloat(String str, float defVal) {
/*  470 */     if (str == null)
/*      */     {
/*  472 */       return defVal;
/*      */     }
/*      */ 
/*      */     
/*  476 */     str = str.trim();
/*  477 */     float f = Config.parseFloat(str, Float.MIN_VALUE);
/*      */     
/*  479 */     if (f == Float.MIN_VALUE) {
/*      */       
/*  481 */       Config.warn("Invalid float: " + str);
/*  482 */       return defVal;
/*      */     } 
/*      */ 
/*      */     
/*  486 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private RangeListInt parseRangeListInt(String str) {
/*  493 */     return parseRangeListInt(str, (IParserInt)null);
/*      */   }
/*      */ 
/*      */   
/*      */   private RangeListInt parseRangeListInt(String str, IParserInt parser) {
/*  498 */     if (str == null)
/*      */     {
/*  500 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  504 */     String[] astring = Config.tokenize(str, " ");
/*  505 */     RangeListInt rangelistint = new RangeListInt();
/*      */     
/*  507 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  509 */       String s = astring[i];
/*      */       
/*  511 */       if (parser != null) {
/*      */         
/*  513 */         int j = parser.parse(s, -2147483648);
/*      */         
/*  515 */         if (j != Integer.MIN_VALUE) {
/*      */           
/*  517 */           rangelistint.addRange(new RangeInt(j, j));
/*      */           
/*      */           continue;
/*      */         } 
/*      */       } 
/*  522 */       RangeInt rangeint = parseRangeInt(s);
/*      */       
/*  524 */       if (rangeint == null) {
/*      */         
/*  526 */         Config.warn("Invalid range list: " + str);
/*  527 */         return null;
/*      */       } 
/*      */       
/*  530 */       rangelistint.addRange(rangeint);
/*      */       continue;
/*      */     } 
/*  533 */     return rangelistint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private RangeInt parseRangeInt(String str) {
/*  539 */     if (str == null)
/*      */     {
/*  541 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  545 */     str = str.trim();
/*  546 */     int i = str.length() - str.replace("-", "").length();
/*      */     
/*  548 */     if (i > 1) {
/*      */       
/*  550 */       Config.warn("Invalid range: " + str);
/*  551 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  555 */     String[] astring = Config.tokenize(str, "- ");
/*  556 */     int[] aint = new int[astring.length];
/*      */     
/*  558 */     for (int j = 0; j < astring.length; j++) {
/*      */       
/*  560 */       String s = astring[j];
/*  561 */       int k = Config.parseInt(s, -1);
/*      */       
/*  563 */       if (k < 0) {
/*      */         
/*  565 */         Config.warn("Invalid range: " + str);
/*  566 */         return null;
/*      */       } 
/*      */       
/*  569 */       aint[j] = k;
/*      */     } 
/*      */     
/*  572 */     if (aint.length == 1) {
/*      */       
/*  574 */       int i1 = aint[0];
/*      */       
/*  576 */       if (!str.isEmpty() && str.charAt(0) == '-')
/*      */       {
/*  578 */         return new RangeInt(0, i1);
/*      */       }
/*  580 */       if (!str.isEmpty() && str.charAt(str.length() - 1) == '-')
/*      */       {
/*  582 */         return new RangeInt(i1, 65535);
/*      */       }
/*      */ 
/*      */       
/*  586 */       return new RangeInt(i1, i1);
/*      */     } 
/*      */     
/*  589 */     if (aint.length == 2) {
/*      */       
/*  591 */       int l = Math.min(aint[0], aint[1]);
/*  592 */       int j1 = Math.max(aint[0], aint[1]);
/*  593 */       return new RangeInt(l, j1);
/*      */     } 
/*      */ 
/*      */     
/*  597 */     Config.warn("Invalid range: " + str);
/*  598 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private NbtTagValue[] parseNbtTagValues(Properties props) {
/*  606 */     String s = "nbt.";
/*  607 */     Map map = getMatchingProperties(props, s);
/*      */     
/*  609 */     if (map.size() <= 0)
/*      */     {
/*  611 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  615 */     List<NbtTagValue> list = new ArrayList();
/*      */     
/*  617 */     for (Object e : map.keySet()) {
/*      */       
/*  619 */       String s1 = (String)e;
/*  620 */       String s2 = (String)map.get(s1);
/*  621 */       String s3 = s1.substring(s.length());
/*  622 */       NbtTagValue nbttagvalue = new NbtTagValue(s3, s2);
/*  623 */       list.add(nbttagvalue);
/*      */     } 
/*      */     
/*  626 */     NbtTagValue[] anbttagvalue = list.<NbtTagValue>toArray(new NbtTagValue[0]);
/*  627 */     return anbttagvalue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Map getMatchingProperties(Properties props, String keyPrefix) {
/*  633 */     Map<Object, Object> map = new LinkedHashMap<>();
/*      */     
/*  635 */     for (Object e : props.keySet()) {
/*      */       
/*  637 */       String s = (String)e;
/*  638 */       String s1 = props.getProperty(s);
/*      */       
/*  640 */       if (s.startsWith(keyPrefix))
/*      */       {
/*  642 */         map.put(s, s1);
/*      */       }
/*      */     } 
/*      */     
/*  646 */     return map;
/*      */   }
/*      */ 
/*      */   
/*      */   private int parseHand(String str) {
/*  651 */     if (str == null)
/*      */     {
/*  653 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  657 */     str = str.toLowerCase();
/*      */     
/*  659 */     if (str.equals("any"))
/*      */     {
/*  661 */       return 0;
/*      */     }
/*  663 */     if (str.equals("main"))
/*      */     {
/*  665 */       return 1;
/*      */     }
/*  667 */     if (str.equals("off"))
/*      */     {
/*  669 */       return 2;
/*      */     }
/*      */ 
/*      */     
/*  673 */     Config.warn("Invalid hand: " + str);
/*  674 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValid(String path) {
/*  681 */     if (this.name != null && !this.name.isEmpty()) {
/*      */       
/*  683 */       if (this.basePath == null) {
/*      */         
/*  685 */         Config.warn("No base path found: " + path);
/*  686 */         return false;
/*      */       } 
/*  688 */       if (this.type == 0) {
/*      */         
/*  690 */         Config.warn("No type defined: " + path);
/*  691 */         return false;
/*      */       } 
/*      */ 
/*      */       
/*  695 */       if (this.type == 1 || this.type == 3) {
/*      */         
/*  697 */         if (this.items == null)
/*      */         {
/*  699 */           this.items = detectItems();
/*      */         }
/*      */         
/*  702 */         if (this.items == null) {
/*      */           
/*  704 */           Config.warn("No items defined: " + path);
/*  705 */           return false;
/*      */         } 
/*      */       } 
/*      */       
/*  709 */       if (this.texture == null && this.mapTextures == null && this.model == null && this.mapModels == null) {
/*      */         
/*  711 */         Config.warn("No texture or model specified: " + path);
/*  712 */         return false;
/*      */       } 
/*  714 */       if (this.type == 2 && this.enchantmentIds == null) {
/*      */         
/*  716 */         Config.warn("No enchantmentIDs specified: " + path);
/*  717 */         return false;
/*      */       } 
/*      */ 
/*      */       
/*  721 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  727 */     Config.warn("No name found: " + path);
/*  728 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] detectItems() {
/*  734 */     Item item = Item.getByNameOrId(this.name);
/*      */     
/*  736 */     if (item == null)
/*      */     {
/*  738 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  742 */     int i = Item.getIdFromItem(item);
/*  743 */     (new int[1])[0] = i; return (i <= 0) ? null : new int[1];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateIcons(TextureMap textureMap) {
/*  749 */     if (this.texture != null) {
/*      */       
/*  751 */       this.textureLocation = getTextureLocation(this.texture);
/*      */       
/*  753 */       if (this.type == 1) {
/*      */         
/*  755 */         ResourceLocation resourcelocation = getSpriteLocation(this.textureLocation);
/*  756 */         this.sprite = textureMap.registerSprite(resourcelocation);
/*      */       } 
/*      */     } 
/*      */     
/*  760 */     if (this.mapTextures != null) {
/*      */       
/*  762 */       this.mapTextureLocations = new HashMap<>();
/*  763 */       this.mapSprites = new HashMap<>();
/*      */       
/*  765 */       for (Map.Entry<String, String> entry : this.mapTextures.entrySet()) {
/*      */         
/*  767 */         String s = entry.getKey();
/*  768 */         String s1 = entry.getValue();
/*  769 */         ResourceLocation resourcelocation1 = getTextureLocation(s1);
/*  770 */         this.mapTextureLocations.put(s, resourcelocation1);
/*      */         
/*  772 */         if (this.type == 1) {
/*      */           
/*  774 */           ResourceLocation resourcelocation2 = getSpriteLocation(resourcelocation1);
/*  775 */           TextureAtlasSprite textureatlassprite = textureMap.registerSprite(resourcelocation2);
/*  776 */           this.mapSprites.put(s, textureatlassprite);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private ResourceLocation getTextureLocation(String texName) {
/*  784 */     if (texName == null)
/*      */     {
/*  786 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  790 */     ResourceLocation resourcelocation = new ResourceLocation(texName);
/*  791 */     String s = resourcelocation.getResourceDomain();
/*  792 */     String s1 = resourcelocation.getResourcePath();
/*      */     
/*  794 */     if (!s1.contains("/"))
/*      */     {
/*  796 */       s1 = "textures/items/" + s1;
/*      */     }
/*      */     
/*  799 */     String s2 = s1 + ".png";
/*  800 */     ResourceLocation resourcelocation1 = new ResourceLocation(s, s2);
/*  801 */     boolean flag = Config.hasResource(resourcelocation1);
/*      */     
/*  803 */     if (!flag)
/*      */     {
/*  805 */       Config.warn("File not found: " + s2);
/*      */     }
/*      */     
/*  808 */     return resourcelocation1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private ResourceLocation getSpriteLocation(ResourceLocation resLoc) {
/*  814 */     String s = resLoc.getResourcePath();
/*  815 */     s = StrUtils.removePrefix(s, "textures/");
/*  816 */     s = StrUtils.removeSuffix(s, ".png");
/*  817 */     ResourceLocation resourcelocation = new ResourceLocation(resLoc.getResourceDomain(), s);
/*  818 */     return resourcelocation;
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateModelTexture(TextureMap textureMap, ItemModelGenerator itemModelGenerator) {
/*  823 */     if (this.texture != null || this.mapTextures != null) {
/*      */       
/*  825 */       String[] astring = getModelTextures();
/*  826 */       boolean flag = isUseTint();
/*  827 */       this.bakedModelTexture = makeBakedModel(textureMap, itemModelGenerator, astring, flag);
/*      */       
/*  829 */       if (this.type == 1 && this.mapTextures != null)
/*      */       {
/*  831 */         for (Map.Entry<String, String> entry : this.mapTextures.entrySet()) {
/*      */           
/*  833 */           String s1 = entry.getValue();
/*  834 */           String s2 = StrUtils.removePrefix(entry.getKey(), "texture.");
/*      */           
/*  836 */           if (s2.startsWith("bow") || s2.startsWith("fishing_rod") || s2.startsWith("shield")) {
/*      */             
/*  838 */             String[] astring1 = { s1 };
/*  839 */             IBakedModel ibakedmodel = makeBakedModel(textureMap, itemModelGenerator, astring1, flag);
/*      */             
/*  841 */             if (this.mapBakedModelsTexture == null)
/*      */             {
/*  843 */               this.mapBakedModelsTexture = new HashMap<>();
/*      */             }
/*      */             
/*  846 */             this.mapBakedModelsTexture.put(s2, ibakedmodel);
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isUseTint() {
/*  855 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private static IBakedModel makeBakedModel(TextureMap textureMap, ItemModelGenerator itemModelGenerator, String[] textures, boolean useTint) {
/*  860 */     String[] astring = new String[textures.length];
/*      */     
/*  862 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  864 */       String s = textures[i];
/*  865 */       astring[i] = StrUtils.removePrefix(s, "textures/");
/*      */     } 
/*      */     
/*  868 */     ModelBlock modelblock = makeModelBlock(astring);
/*  869 */     ModelBlock modelblock1 = itemModelGenerator.makeItemModel(textureMap, modelblock);
/*  870 */     IBakedModel ibakedmodel = bakeModel(textureMap, modelblock1, useTint);
/*  871 */     return ibakedmodel;
/*      */   }
/*      */ 
/*      */   
/*      */   private String[] getModelTextures() {
/*  876 */     if (this.type == 1 && this.items.length == 1) {
/*      */       
/*  878 */       Item item = Item.getItemById(this.items[0]);
/*      */       
/*  880 */       if (item == Items.potionitem && this.damage != null && this.damage.getCountRanges() > 0) {
/*      */         
/*  882 */         RangeInt rangeint = this.damage.getRange(0);
/*  883 */         int i = rangeint.getMin();
/*  884 */         boolean flag = ((i & 0x4000) != 0);
/*  885 */         String s5 = getMapTexture(this.mapTextures, "texture.potion_overlay", "items/potion_overlay");
/*  886 */         String s6 = null;
/*      */         
/*  888 */         if (flag) {
/*      */           
/*  890 */           s6 = getMapTexture(this.mapTextures, "texture.potion_bottle_splash", "items/potion_bottle_splash");
/*      */         }
/*      */         else {
/*      */           
/*  894 */           s6 = getMapTexture(this.mapTextures, "texture.potion_bottle_drinkable", "items/potion_bottle_drinkable");
/*      */         } 
/*      */         
/*  897 */         return new String[] { s5, s6 };
/*      */       } 
/*      */       
/*  900 */       if (item instanceof ItemArmor) {
/*      */         
/*  902 */         ItemArmor itemarmor = (ItemArmor)item;
/*      */         
/*  904 */         if (itemarmor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
/*      */           
/*  906 */           String s = "leather";
/*  907 */           String s1 = "helmet";
/*      */           
/*  909 */           if (itemarmor.armorType == 0)
/*      */           {
/*  911 */             s1 = "helmet";
/*      */           }
/*      */           
/*  914 */           if (itemarmor.armorType == 1)
/*      */           {
/*  916 */             s1 = "chestplate";
/*      */           }
/*      */           
/*  919 */           if (itemarmor.armorType == 2)
/*      */           {
/*  921 */             s1 = "leggings";
/*      */           }
/*      */           
/*  924 */           if (itemarmor.armorType == 3)
/*      */           {
/*  926 */             s1 = "boots";
/*      */           }
/*      */           
/*  929 */           String s2 = s + "_" + s1;
/*  930 */           String s3 = getMapTexture(this.mapTextures, "texture." + s2, "items/" + s2);
/*  931 */           String s4 = getMapTexture(this.mapTextures, "texture." + s2 + "_overlay", "items/" + s2 + "_overlay");
/*  932 */           return new String[] { s3, s4 };
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  937 */     return new String[] { this.texture };
/*      */   }
/*      */ 
/*      */   
/*      */   private String getMapTexture(Map<String, String> map, String key, String def) {
/*  942 */     if (map == null)
/*      */     {
/*  944 */       return def;
/*      */     }
/*      */ 
/*      */     
/*  948 */     String s = map.get(key);
/*  949 */     return (s == null) ? def : s;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ModelBlock makeModelBlock(String[] modelTextures) {
/*  955 */     StringBuffer stringbuffer = new StringBuffer();
/*  956 */     stringbuffer.append("{\"parent\": \"builtin/generated\",\"textures\": {");
/*      */     
/*  958 */     for (int i = 0; i < modelTextures.length; i++) {
/*      */       
/*  960 */       String s = modelTextures[i];
/*      */       
/*  962 */       if (i > 0)
/*      */       {
/*  964 */         stringbuffer.append(", ");
/*      */       }
/*      */       
/*  967 */       stringbuffer.append("\"layer").append(i).append("\": \"").append(s).append("\"");
/*      */     } 
/*      */     
/*  970 */     stringbuffer.append("}}");
/*  971 */     String s1 = stringbuffer.toString();
/*  972 */     ModelBlock modelblock = ModelBlock.deserialize(s1);
/*  973 */     return modelblock;
/*      */   }
/*      */ 
/*      */   
/*      */   private static IBakedModel bakeModel(TextureMap textureMap, ModelBlock modelBlockIn, boolean useTint) {
/*  978 */     ModelRotation modelrotation = ModelRotation.X0_Y0;
/*  979 */     boolean flag = false;
/*  980 */     String s = modelBlockIn.resolveTextureName("particle");
/*  981 */     TextureAtlasSprite textureatlassprite = textureMap.getAtlasSprite((new ResourceLocation(s)).toString());
/*  982 */     SimpleBakedModel.Builder simplebakedmodel$builder = (new SimpleBakedModel.Builder(modelBlockIn)).setTexture(textureatlassprite);
/*      */     
/*  984 */     for (BlockPart blockpart : modelBlockIn.getElements()) {
/*      */       
/*  986 */       for (Map.Entry<EnumFacing, BlockPartFace> entry : (Iterable<Map.Entry<EnumFacing, BlockPartFace>>)blockpart.mapFaces.entrySet()) {
/*      */         
/*  988 */         BlockPartFace blockpartface = entry.getValue();
/*      */         
/*  990 */         if (!useTint)
/*      */         {
/*  992 */           blockpartface = new BlockPartFace(blockpartface.cullFace, -1, blockpartface.texture, blockpartface.blockFaceUV);
/*      */         }
/*      */         
/*  995 */         String s1 = modelBlockIn.resolveTextureName(blockpartface.texture);
/*  996 */         TextureAtlasSprite textureatlassprite1 = textureMap.getAtlasSprite((new ResourceLocation(s1)).toString());
/*  997 */         BakedQuad bakedquad = makeBakedQuad(blockpart, blockpartface, textureatlassprite1, entry.getKey(), modelrotation, flag);
/*      */         
/*  999 */         if (blockpartface.cullFace == null) {
/*      */           
/* 1001 */           simplebakedmodel$builder.addGeneralQuad(bakedquad);
/*      */           
/*      */           continue;
/*      */         } 
/* 1005 */         simplebakedmodel$builder.addFaceQuad(modelrotation.rotateFace(blockpartface.cullFace), bakedquad);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1010 */     return simplebakedmodel$builder.makeBakedModel();
/*      */   }
/*      */ 
/*      */   
/*      */   private static BakedQuad makeBakedQuad(BlockPart blockPart, BlockPartFace blockPartFace, TextureAtlasSprite textureAtlasSprite, EnumFacing enumFacing, ModelRotation modelRotation, boolean uvLocked) {
/* 1015 */     FaceBakery facebakery = new FaceBakery();
/* 1016 */     return facebakery.makeBakedQuad(blockPart.positionFrom, blockPart.positionTo, blockPartFace, textureAtlasSprite, enumFacing, modelRotation, blockPart.partRotation, uvLocked, blockPart.shade);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1021 */     return this.basePath + "/" + this.name + ", type: " + this.type + ", items: [" + Config.arrayToString(this.items) + "], textture: " + this.texture;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getTextureWidth(TextureManager textureManager) {
/* 1026 */     if (this.textureWidth <= 0) {
/*      */       
/* 1028 */       if (this.textureLocation != null) {
/*      */         
/* 1030 */         ITextureObject itextureobject = textureManager.getTexture(this.textureLocation);
/* 1031 */         int i = itextureobject.getGlTextureId();
/* 1032 */         int j = GlStateManager.getBoundTexture();
/* 1033 */         GlStateManager.bindTexture(i);
/* 1034 */         this.textureWidth = GL11.glGetTexLevelParameteri(3553, 0, 4096);
/* 1035 */         GlStateManager.bindTexture(j);
/*      */       } 
/*      */       
/* 1038 */       if (this.textureWidth <= 0)
/*      */       {
/* 1040 */         this.textureWidth = 16;
/*      */       }
/*      */     } 
/*      */     
/* 1044 */     return this.textureWidth;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getTextureHeight(TextureManager textureManager) {
/* 1049 */     if (this.textureHeight <= 0) {
/*      */       
/* 1051 */       if (this.textureLocation != null) {
/*      */         
/* 1053 */         ITextureObject itextureobject = textureManager.getTexture(this.textureLocation);
/* 1054 */         int i = itextureobject.getGlTextureId();
/* 1055 */         int j = GlStateManager.getBoundTexture();
/* 1056 */         GlStateManager.bindTexture(i);
/* 1057 */         this.textureHeight = GL11.glGetTexLevelParameteri(3553, 0, 4097);
/* 1058 */         GlStateManager.bindTexture(j);
/*      */       } 
/*      */       
/* 1061 */       if (this.textureHeight <= 0)
/*      */       {
/* 1063 */         this.textureHeight = 16;
/*      */       }
/*      */     } 
/*      */     
/* 1067 */     return this.textureHeight;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public IBakedModel getBakedModel(ResourceLocation modelLocation, boolean fullModel) {
/*      */     IBakedModel ibakedmodel;
/*      */     Map<String, IBakedModel> map;
/* 1075 */     if (fullModel) {
/*      */       
/* 1077 */       ibakedmodel = this.bakedModelFull;
/* 1078 */       map = this.mapBakedModelsFull;
/*      */     }
/*      */     else {
/*      */       
/* 1082 */       ibakedmodel = this.bakedModelTexture;
/* 1083 */       map = this.mapBakedModelsTexture;
/*      */     } 
/*      */     
/* 1086 */     if (modelLocation != null && map != null) {
/*      */       
/* 1088 */       String s = modelLocation.getResourcePath();
/* 1089 */       IBakedModel ibakedmodel1 = map.get(s);
/*      */       
/* 1091 */       if (ibakedmodel1 != null)
/*      */       {
/* 1093 */         return ibakedmodel1;
/*      */       }
/*      */     } 
/*      */     
/* 1097 */     return ibakedmodel;
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadModels(ModelBakery modelBakery) {
/* 1102 */     if (this.model != null)
/*      */     {
/* 1104 */       loadItemModel(modelBakery, this.model);
/*      */     }
/*      */     
/* 1107 */     if (this.type == 1 && this.mapModels != null)
/*      */     {
/* 1109 */       for (Map.Entry<String, String> entry : this.mapModels.entrySet()) {
/*      */         
/* 1111 */         String s1 = entry.getValue();
/* 1112 */         String s2 = StrUtils.removePrefix(entry.getKey(), "model.");
/*      */         
/* 1114 */         if (s2.startsWith("bow") || s2.startsWith("fishing_rod") || s2.startsWith("shield"))
/*      */         {
/* 1116 */           loadItemModel(modelBakery, s1);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateModelsFull() {
/* 1124 */     ModelManager modelmanager = Config.getModelManager();
/* 1125 */     IBakedModel ibakedmodel = modelmanager.getMissingModel();
/*      */     
/* 1127 */     if (this.model != null) {
/*      */       
/* 1129 */       ResourceLocation resourcelocation = getModelLocation(this.model);
/* 1130 */       ModelResourceLocation modelresourcelocation = new ModelResourceLocation(resourcelocation, "inventory");
/* 1131 */       this.bakedModelFull = modelmanager.getModel(modelresourcelocation);
/*      */       
/* 1133 */       if (this.bakedModelFull == ibakedmodel) {
/*      */         
/* 1135 */         Config.warn("Custom Items: Model not found " + modelresourcelocation.getResourcePath());
/* 1136 */         this.bakedModelFull = null;
/*      */       } 
/*      */     } 
/*      */     
/* 1140 */     if (this.type == 1 && this.mapModels != null)
/*      */     {
/* 1142 */       for (Map.Entry<String, String> entry : this.mapModels.entrySet()) {
/*      */         
/* 1144 */         String s1 = entry.getValue();
/* 1145 */         String s2 = StrUtils.removePrefix(entry.getKey(), "model.");
/*      */         
/* 1147 */         if (s2.startsWith("bow") || s2.startsWith("fishing_rod") || s2.startsWith("shield")) {
/*      */           
/* 1149 */           ResourceLocation resourcelocation1 = getModelLocation(s1);
/* 1150 */           ModelResourceLocation modelresourcelocation1 = new ModelResourceLocation(resourcelocation1, "inventory");
/* 1151 */           IBakedModel ibakedmodel1 = modelmanager.getModel(modelresourcelocation1);
/*      */           
/* 1153 */           if (ibakedmodel1 == ibakedmodel) {
/*      */             
/* 1155 */             Config.warn("Custom Items: Model not found " + modelresourcelocation1.getResourcePath());
/*      */             
/*      */             continue;
/*      */           } 
/* 1159 */           if (this.mapBakedModelsFull == null)
/*      */           {
/* 1161 */             this.mapBakedModelsFull = new HashMap<>();
/*      */           }
/*      */           
/* 1164 */           this.mapBakedModelsFull.put(s2, ibakedmodel1);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void loadItemModel(ModelBakery modelBakery, String model) {
/* 1173 */     ResourceLocation resourcelocation = getModelLocation(model);
/* 1174 */     ModelResourceLocation modelresourcelocation = new ModelResourceLocation(resourcelocation, "inventory");
/*      */     
/* 1176 */     if (Reflector.ModelLoader.exists()) {
/*      */       
/*      */       try
/*      */       {
/* 1180 */         Object object = Reflector.ModelLoader_VanillaLoader_INSTANCE.getValue();
/* 1181 */         checkNull(object, "vanillaLoader is null");
/* 1182 */         Object object1 = Reflector.call(object, Reflector.ModelLoader_VanillaLoader_loadModel, new Object[] { modelresourcelocation });
/* 1183 */         checkNull(object1, "iModel is null");
/* 1184 */         Map<ModelResourceLocation, Object> map = (Map)Reflector.getFieldValue(modelBakery, Reflector.ModelLoader_stateModels);
/* 1185 */         checkNull(map, "stateModels is null");
/* 1186 */         map.put(modelresourcelocation, object1);
/* 1187 */         Set set = (Set)Reflector.getFieldValue(modelBakery, Reflector.ModelLoader_textures);
/* 1188 */         checkNull(set, "registryTextures is null");
/* 1189 */         Collection collection = (Collection)Reflector.call(object1, Reflector.IModel_getTextures, new Object[0]);
/* 1190 */         checkNull(collection, "modelTextures is null");
/* 1191 */         set.addAll(collection);
/*      */       }
/* 1193 */       catch (Exception exception)
/*      */       {
/* 1195 */         Config.warn("Error registering model with ModelLoader: " + modelresourcelocation + ", " + exception.getClass().getName() + ": " + exception.getMessage());
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1200 */       modelBakery.loadItemModel(resourcelocation.toString(), (ResourceLocation)modelresourcelocation, resourcelocation);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void checkNull(Object obj, String msg) throws NullPointerException {
/* 1206 */     if (obj == null)
/*      */     {
/* 1208 */       throw new NullPointerException(msg);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static ResourceLocation getModelLocation(String modelName) {
/* 1214 */     return (Reflector.ModelLoader.exists() && !modelName.startsWith("mcpatcher/") && !modelName.startsWith("optifine/")) ? new ResourceLocation("models/" + modelName) : new ResourceLocation(modelName);
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\CustomItemProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */