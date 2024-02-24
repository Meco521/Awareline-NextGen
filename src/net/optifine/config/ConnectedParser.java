/*      */ package net.optifine.config;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.EnumSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockDoublePlant;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.item.EnumDyeColor;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.IStringSerializable;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ 
/*      */ public class ConnectedParser {
/*   24 */   private String context = null;
/*   25 */   public static final VillagerProfession[] PROFESSIONS_INVALID = new VillagerProfession[0];
/*   26 */   public static final EnumDyeColor[] DYE_COLORS_INVALID = new EnumDyeColor[0];
/*   27 */   private static final INameGetter<Enum> NAME_GETTER_ENUM = new INameGetter<Enum>()
/*      */     {
/*      */       public String getName(Enum en)
/*      */       {
/*   31 */         return en.name();
/*      */       }
/*      */     };
/*   34 */   private static final INameGetter<EnumDyeColor> NAME_GETTER_DYE_COLOR = new INameGetter<EnumDyeColor>()
/*      */     {
/*      */       public String getName(EnumDyeColor col)
/*      */       {
/*   38 */         return col.getName();
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*      */   public ConnectedParser(String context) {
/*   44 */     this.context = context;
/*      */   }
/*      */ 
/*      */   
/*      */   public String parseName(String path) {
/*   49 */     String s = path;
/*   50 */     int i = path.lastIndexOf('/');
/*      */     
/*   52 */     if (i >= 0)
/*      */     {
/*   54 */       s = path.substring(i + 1);
/*      */     }
/*      */     
/*   57 */     int j = s.lastIndexOf('.');
/*      */     
/*   59 */     if (j >= 0)
/*      */     {
/*   61 */       s = s.substring(0, j);
/*      */     }
/*      */     
/*   64 */     return s;
/*      */   }
/*      */ 
/*      */   
/*      */   public String parseBasePath(String path) {
/*   69 */     int i = path.lastIndexOf('/');
/*   70 */     return (i < 0) ? "" : path.substring(0, i);
/*      */   }
/*      */ 
/*      */   
/*      */   public MatchBlock[] parseMatchBlocks(String propMatchBlocks) {
/*   75 */     if (propMatchBlocks == null)
/*      */     {
/*   77 */       return null;
/*      */     }
/*      */ 
/*      */     
/*   81 */     List list = new ArrayList();
/*   82 */     String[] astring = Config.tokenize(propMatchBlocks, " ");
/*      */     
/*   84 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*   86 */       String s = astring[i];
/*   87 */       MatchBlock[] amatchblock = parseMatchBlock(s);
/*      */       
/*   89 */       if (amatchblock != null)
/*      */       {
/*   91 */         list.addAll(Arrays.asList(amatchblock));
/*      */       }
/*      */     } 
/*      */     
/*   95 */     MatchBlock[] amatchblock1 = (MatchBlock[])list.toArray((Object[])new MatchBlock[0]);
/*   96 */     return amatchblock1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public IBlockState parseBlockState(String str, IBlockState def) {
/*  102 */     MatchBlock[] amatchblock = parseMatchBlock(str);
/*      */     
/*  104 */     if (amatchblock == null)
/*      */     {
/*  106 */       return def;
/*      */     }
/*  108 */     if (amatchblock.length != 1)
/*      */     {
/*  110 */       return def;
/*      */     }
/*      */ 
/*      */     
/*  114 */     MatchBlock matchblock = amatchblock[0];
/*  115 */     int i = matchblock.getBlockId();
/*  116 */     Block block = Block.getBlockById(i);
/*  117 */     return block.getDefaultState();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public MatchBlock[] parseMatchBlock(String blockStr) {
/*  123 */     if (blockStr == null)
/*      */     {
/*  125 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  129 */     blockStr = blockStr.trim();
/*      */     
/*  131 */     if (blockStr.length() <= 0)
/*      */     {
/*  133 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  137 */     String[] astring = Config.tokenize(blockStr, ":");
/*  138 */     String s = "minecraft";
/*  139 */     int i = 0;
/*      */     
/*  141 */     if (astring.length > 1 && isFullBlockName(astring)) {
/*      */       
/*  143 */       s = astring[0];
/*  144 */       i = 1;
/*      */     }
/*      */     else {
/*      */       
/*  148 */       s = "minecraft";
/*  149 */       i = 0;
/*      */     } 
/*      */     
/*  152 */     String s1 = astring[i];
/*  153 */     String[] astring1 = Arrays.<String>copyOfRange(astring, i + 1, astring.length);
/*  154 */     Block[] ablock = parseBlockPart(s, s1);
/*      */     
/*  156 */     if (ablock == null)
/*      */     {
/*  158 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  162 */     MatchBlock[] amatchblock = new MatchBlock[ablock.length];
/*      */     
/*  164 */     for (int j = 0; j < ablock.length; j++) {
/*      */       
/*  166 */       Block block = ablock[j];
/*  167 */       int k = Block.getIdFromBlock(block);
/*  168 */       int[] aint = null;
/*      */       
/*  170 */       if (astring1.length > 0) {
/*      */         
/*  172 */         aint = parseBlockMetadatas(block, astring1);
/*      */         
/*  174 */         if (aint == null)
/*      */         {
/*  176 */           return null;
/*      */         }
/*      */       } 
/*      */       
/*  180 */       MatchBlock matchblock = new MatchBlock(k, aint);
/*  181 */       amatchblock[j] = matchblock;
/*      */     } 
/*      */     
/*  184 */     return amatchblock;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFullBlockName(String[] parts) {
/*  192 */     if (parts.length < 2)
/*      */     {
/*  194 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  198 */     String s = parts[1];
/*  199 */     return (s.length() < 1) ? false : (startsWithDigit(s) ? false : (!s.contains("=")));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean startsWithDigit(String str) {
/*  205 */     if (str == null)
/*      */     {
/*  207 */       return false;
/*      */     }
/*  209 */     if (str.length() < 1)
/*      */     {
/*  211 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  215 */     char c0 = str.charAt(0);
/*  216 */     return Character.isDigit(c0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Block[] parseBlockPart(String domain, String blockPart) {
/*  222 */     if (startsWithDigit(blockPart)) {
/*      */       
/*  224 */       int[] aint = parseIntList(blockPart);
/*      */       
/*  226 */       if (aint == null)
/*      */       {
/*  228 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  232 */       Block[] ablock1 = new Block[aint.length];
/*      */       
/*  234 */       for (int j = 0; j < aint.length; j++) {
/*      */         
/*  236 */         int i = aint[j];
/*  237 */         Block block1 = Block.getBlockById(i);
/*      */         
/*  239 */         if (block1 == null) {
/*      */           
/*  241 */           warn("Block not found for id: " + i);
/*  242 */           return null;
/*      */         } 
/*      */         
/*  245 */         ablock1[j] = block1;
/*      */       } 
/*      */       
/*  248 */       return ablock1;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  253 */     String s = domain + ":" + blockPart;
/*  254 */     Block block = Block.getBlockFromName(s);
/*      */     
/*  256 */     if (block == null) {
/*      */       
/*  258 */       warn("Block not found for name: " + s);
/*  259 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  263 */     Block[] ablock = { block };
/*  264 */     return ablock;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] parseBlockMetadatas(Block block, String[] params) {
/*  271 */     if (params.length <= 0)
/*      */     {
/*  273 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  277 */     String s = params[0];
/*      */     
/*  279 */     if (startsWithDigit(s)) {
/*      */       
/*  281 */       int[] aint = parseIntList(s);
/*  282 */       return aint;
/*      */     } 
/*      */ 
/*      */     
/*  286 */     IBlockState iblockstate = block.getDefaultState();
/*  287 */     Collection collection = iblockstate.getPropertyNames();
/*  288 */     Map<IProperty, List<Comparable>> map = new HashMap<>();
/*      */     
/*  290 */     for (int i = 0; i < params.length; i++) {
/*      */       
/*  292 */       String s1 = params[i];
/*      */       
/*  294 */       if (!s1.isEmpty()) {
/*      */         
/*  296 */         String[] astring = Config.tokenize(s1, "=");
/*      */         
/*  298 */         if (astring.length != 2) {
/*      */           
/*  300 */           warn("Invalid block property: " + s1);
/*  301 */           return null;
/*      */         } 
/*      */         
/*  304 */         String s2 = astring[0];
/*  305 */         String s3 = astring[1];
/*  306 */         IProperty iproperty = ConnectedProperties.getProperty(s2, collection);
/*      */         
/*  308 */         if (iproperty == null) {
/*      */           
/*  310 */           warn("Property not found: " + s2 + ", block: " + block);
/*  311 */           return null;
/*      */         } 
/*      */         
/*  314 */         List<Comparable> list = map.get(s2);
/*      */         
/*  316 */         if (list == null) {
/*      */           
/*  318 */           list = new ArrayList<>();
/*  319 */           map.put(iproperty, list);
/*      */         } 
/*      */         
/*  322 */         String[] astring1 = Config.tokenize(s3, ",");
/*      */         
/*  324 */         for (int j = 0; j < astring1.length; j++) {
/*      */           
/*  326 */           String s4 = astring1[j];
/*  327 */           Comparable comparable = parsePropertyValue(iproperty, s4);
/*      */           
/*  329 */           if (comparable == null) {
/*      */             
/*  331 */             warn("Property value not found: " + s4 + ", property: " + s2 + ", block: " + block);
/*  332 */             return null;
/*      */           } 
/*      */           
/*  335 */           list.add(comparable);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  340 */     if (map.isEmpty())
/*      */     {
/*  342 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  346 */     List<Integer> list1 = new ArrayList<>();
/*      */     
/*  348 */     for (int k = 0; k < 16; k++) {
/*      */       
/*  350 */       int l = k;
/*      */ 
/*      */       
/*      */       try {
/*  354 */         IBlockState iblockstate1 = getStateFromMeta(block, l);
/*      */         
/*  356 */         if (matchState(iblockstate1, map))
/*      */         {
/*  358 */           list1.add(Integer.valueOf(l));
/*      */         }
/*      */       }
/*  361 */       catch (IllegalArgumentException illegalArgumentException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  367 */     if (list1.size() == 16)
/*      */     {
/*  369 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  373 */     int[] aint1 = new int[list1.size()];
/*      */     
/*  375 */     for (int i1 = 0; i1 < aint1.length; i1++)
/*      */     {
/*  377 */       aint1[i1] = ((Integer)list1.get(i1)).intValue();
/*      */     }
/*      */     
/*  380 */     return aint1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private IBlockState getStateFromMeta(Block block, int md) {
/*      */     try {
/*  391 */       IBlockState iblockstate = block.getStateFromMeta(md);
/*      */       
/*  393 */       if (block == Blocks.double_plant && md > 7) {
/*      */         
/*  395 */         IBlockState iblockstate1 = block.getStateFromMeta(md & 0x7);
/*  396 */         iblockstate = iblockstate.withProperty((IProperty)BlockDoublePlant.VARIANT, iblockstate1.getValue((IProperty)BlockDoublePlant.VARIANT));
/*      */       } 
/*      */       
/*  399 */       return iblockstate;
/*      */     }
/*  401 */     catch (IllegalArgumentException var5) {
/*      */       
/*  403 */       return block.getDefaultState();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static Comparable parsePropertyValue(IProperty prop, String valStr) {
/*  409 */     Class<?> oclass = prop.getValueClass();
/*  410 */     Comparable comparable = parseValue(valStr, oclass);
/*      */     
/*  412 */     if (comparable == null) {
/*      */       
/*  414 */       Collection collection = prop.getAllowedValues();
/*  415 */       comparable = getPropertyValue(valStr, collection);
/*      */     } 
/*      */     
/*  418 */     return comparable;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Comparable getPropertyValue(String value, Collection propertyValues) {
/*  423 */     for (Object e : propertyValues) {
/*      */       
/*  425 */       Comparable comparable = (Comparable)e;
/*  426 */       if (getValueName(comparable).equals(value))
/*      */       {
/*  428 */         return comparable;
/*      */       }
/*      */     } 
/*      */     
/*  432 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Object getValueName(Comparable obj) {
/*  438 */     if (obj instanceof IStringSerializable) {
/*      */       
/*  440 */       IStringSerializable istringserializable = (IStringSerializable)obj;
/*  441 */       return istringserializable.getName();
/*      */     } 
/*      */ 
/*      */     
/*  445 */     return obj.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Comparable parseValue(String str, Class<?> cls) {
/*  451 */     if (cls == String.class) {
/*  452 */       return str;
/*      */     }
/*  454 */     if (cls == Boolean.class) {
/*  455 */       return Boolean.valueOf(str);
/*      */     }
/*  457 */     if (cls == Float.class) {
/*  458 */       return Float.valueOf(str);
/*      */     }
/*  460 */     if (cls == Double.class) {
/*  461 */       return Double.valueOf(str);
/*      */     }
/*  463 */     if (cls == Integer.class) {
/*  464 */       return Integer.valueOf(str);
/*      */     }
/*  466 */     if (cls == Long.class) {
/*  467 */       return Long.valueOf(str);
/*      */     }
/*  469 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean matchState(IBlockState bs, Map<IProperty, List<Comparable>> mapPropValues) {
/*  475 */     for (Map.Entry<IProperty, List<Comparable>> entry : mapPropValues.entrySet()) {
/*      */       
/*  477 */       List<Comparable> list = entry.getValue();
/*  478 */       Comparable comparable = bs.getValue(entry.getKey());
/*      */       
/*  480 */       if (comparable == null)
/*      */       {
/*  482 */         return false;
/*      */       }
/*      */       
/*  485 */       if (!list.contains(comparable))
/*      */       {
/*  487 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  491 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public BiomeGenBase[] parseBiomes(String str) {
/*  496 */     if (str == null)
/*      */     {
/*  498 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  502 */     str = str.trim();
/*  503 */     boolean flag = false;
/*      */     
/*  505 */     if (!str.isEmpty() && str.charAt(0) == '!') {
/*      */       
/*  507 */       flag = true;
/*  508 */       str = str.substring(1);
/*      */     } 
/*      */     
/*  511 */     String[] astring = Config.tokenize(str, " ");
/*  512 */     List<BiomeGenBase> list = new ArrayList();
/*      */     
/*  514 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  516 */       String s = astring[i];
/*  517 */       BiomeGenBase biomegenbase = findBiome(s);
/*      */       
/*  519 */       if (biomegenbase == null) {
/*      */         
/*  521 */         warn("Biome not found: " + s);
/*      */       }
/*      */       else {
/*      */         
/*  525 */         list.add(biomegenbase);
/*      */       } 
/*      */     } 
/*      */     
/*  529 */     if (flag) {
/*      */       
/*  531 */       List<BiomeGenBase> list1 = new ArrayList<>(Arrays.asList(BiomeGenBase.getBiomeGenArray()));
/*  532 */       list1.removeAll(list);
/*  533 */       list = list1;
/*      */     } 
/*      */     
/*  536 */     BiomeGenBase[] abiomegenbase = list.<BiomeGenBase>toArray(new BiomeGenBase[0]);
/*  537 */     return abiomegenbase;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public BiomeGenBase findBiome(String biomeName) {
/*  543 */     biomeName = biomeName.toLowerCase();
/*      */     
/*  545 */     if (biomeName.equals("nether"))
/*      */     {
/*  547 */       return BiomeGenBase.hell;
/*      */     }
/*      */ 
/*      */     
/*  551 */     BiomeGenBase[] abiomegenbase = BiomeGenBase.getBiomeGenArray();
/*      */     
/*  553 */     for (int i = 0; i < abiomegenbase.length; i++) {
/*      */       
/*  555 */       BiomeGenBase biomegenbase = abiomegenbase[i];
/*      */       
/*  557 */       if (biomegenbase != null) {
/*      */         
/*  559 */         String s = biomegenbase.biomeName.replace(" ", "").toLowerCase();
/*      */         
/*  561 */         if (s.equals(biomeName))
/*      */         {
/*  563 */           return biomegenbase;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  568 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int parseInt(String str, int defVal) {
/*  574 */     if (str == null)
/*      */     {
/*  576 */       return defVal;
/*      */     }
/*      */ 
/*      */     
/*  580 */     str = str.trim();
/*  581 */     int i = Config.parseInt(str, -1);
/*      */     
/*  583 */     if (i < 0) {
/*      */       
/*  585 */       warn("Invalid number: " + str);
/*  586 */       return defVal;
/*      */     } 
/*      */ 
/*      */     
/*  590 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] parseIntList(String str) {
/*  597 */     if (str == null)
/*      */     {
/*  599 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  603 */     List<Integer> list = new ArrayList<>();
/*  604 */     String[] astring = Config.tokenize(str, " ,");
/*      */     
/*  606 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  608 */       String s = astring[i];
/*      */       
/*  610 */       if (s.contains("-")) {
/*      */         
/*  612 */         String[] astring1 = Config.tokenize(s, "-");
/*      */         
/*  614 */         if (astring1.length != 2) {
/*      */           
/*  616 */           warn("Invalid interval: " + s + ", when parsing: " + str);
/*      */         }
/*      */         else {
/*      */           
/*  620 */           int k = Config.parseInt(astring1[0], -1);
/*  621 */           int l = Config.parseInt(astring1[1], -1);
/*      */           
/*  623 */           if (k >= 0 && l >= 0 && k <= l)
/*      */           {
/*  625 */             for (int i1 = k; i1 <= l; i1++)
/*      */             {
/*  627 */               list.add(Integer.valueOf(i1));
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  632 */             warn("Invalid interval: " + s + ", when parsing: " + str);
/*      */           }
/*      */         
/*      */         } 
/*      */       } else {
/*      */         
/*  638 */         int j = Config.parseInt(s, -1);
/*      */         
/*  640 */         if (j < 0) {
/*      */           
/*  642 */           warn("Invalid number: " + s + ", when parsing: " + str);
/*      */         }
/*      */         else {
/*      */           
/*  646 */           list.add(Integer.valueOf(j));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  651 */     int[] aint = new int[list.size()];
/*      */     
/*  653 */     for (int j1 = 0; j1 < aint.length; j1++)
/*      */     {
/*  655 */       aint[j1] = ((Integer)list.get(j1)).intValue();
/*      */     }
/*      */     
/*  658 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean[] parseFaces(String str, boolean[] defVal) {
/*  664 */     if (str == null)
/*      */     {
/*  666 */       return defVal;
/*      */     }
/*      */ 
/*      */     
/*  670 */     EnumSet<EnumFacing> enumset = EnumSet.allOf(EnumFacing.class);
/*  671 */     String[] astring = Config.tokenize(str, " ,");
/*      */     
/*  673 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  675 */       String s = astring[i];
/*      */       
/*  677 */       if (s.equals("sides")) {
/*      */         
/*  679 */         enumset.add(EnumFacing.NORTH);
/*  680 */         enumset.add(EnumFacing.SOUTH);
/*  681 */         enumset.add(EnumFacing.WEST);
/*  682 */         enumset.add(EnumFacing.EAST);
/*      */       }
/*  684 */       else if (s.equals("all")) {
/*      */         
/*  686 */         enumset.addAll(Arrays.asList(EnumFacing.VALUES));
/*      */       }
/*      */       else {
/*      */         
/*  690 */         EnumFacing enumfacing = parseFace(s);
/*      */         
/*  692 */         if (enumfacing != null)
/*      */         {
/*  694 */           enumset.add(enumfacing);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  699 */     boolean[] aboolean = new boolean[EnumFacing.VALUES.length];
/*      */     
/*  701 */     for (int j = 0; j < aboolean.length; j++)
/*      */     {
/*  703 */       aboolean[j] = enumset.contains(EnumFacing.VALUES[j]);
/*      */     }
/*      */     
/*  706 */     return aboolean;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumFacing parseFace(String str) {
/*  712 */     str = str.toLowerCase();
/*      */     
/*  714 */     if (!str.equals("bottom") && !str.equals("down")) {
/*      */       
/*  716 */       if (!str.equals("top") && !str.equals("up")) {
/*      */         
/*  718 */         if (str.equals("north"))
/*      */         {
/*  720 */           return EnumFacing.NORTH;
/*      */         }
/*  722 */         if (str.equals("south"))
/*      */         {
/*  724 */           return EnumFacing.SOUTH;
/*      */         }
/*  726 */         if (str.equals("east"))
/*      */         {
/*  728 */           return EnumFacing.EAST;
/*      */         }
/*  730 */         if (str.equals("west"))
/*      */         {
/*  732 */           return EnumFacing.WEST;
/*      */         }
/*      */ 
/*      */         
/*  736 */         Config.warn("Unknown face: " + str);
/*  737 */         return null;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  742 */       return EnumFacing.UP;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  747 */     return EnumFacing.DOWN;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void dbg(String str) {
/*  753 */     Config.dbg(this.context + ": " + str);
/*      */   }
/*      */ 
/*      */   
/*      */   public void warn(String str) {
/*  758 */     Config.warn(this.context + ": " + str);
/*      */   }
/*      */ 
/*      */   
/*      */   public RangeListInt parseRangeListInt(String str) {
/*  763 */     if (str == null)
/*      */     {
/*  765 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  769 */     RangeListInt rangelistint = new RangeListInt();
/*  770 */     String[] astring = Config.tokenize(str, " ,");
/*      */     
/*  772 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  774 */       String s = astring[i];
/*  775 */       RangeInt rangeint = parseRangeInt(s);
/*      */       
/*  777 */       if (rangeint == null)
/*      */       {
/*  779 */         return null;
/*      */       }
/*      */       
/*  782 */       rangelistint.addRange(rangeint);
/*      */     } 
/*      */     
/*  785 */     return rangelistint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private RangeInt parseRangeInt(String str) {
/*  791 */     if (str == null)
/*      */     {
/*  793 */       return null;
/*      */     }
/*  795 */     if (str.indexOf('-') >= 0) {
/*      */       
/*  797 */       String[] astring = Config.tokenize(str, "-");
/*      */       
/*  799 */       if (astring.length != 2) {
/*      */         
/*  801 */         warn("Invalid range: " + str);
/*  802 */         return null;
/*      */       } 
/*      */ 
/*      */       
/*  806 */       int j = Config.parseInt(astring[0], -1);
/*  807 */       int k = Config.parseInt(astring[1], -1);
/*      */       
/*  809 */       if (j >= 0 && k >= 0)
/*      */       {
/*  811 */         return new RangeInt(j, k);
/*      */       }
/*      */ 
/*      */       
/*  815 */       warn("Invalid range: " + str);
/*  816 */       return null;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  822 */     int i = Config.parseInt(str, -1);
/*      */     
/*  824 */     if (i < 0) {
/*      */       
/*  826 */       warn("Invalid integer: " + str);
/*  827 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  831 */     return new RangeInt(i, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean parseBoolean(String str, boolean defVal) {
/*  838 */     if (str == null)
/*      */     {
/*  840 */       return defVal;
/*      */     }
/*      */ 
/*      */     
/*  844 */     String s = str.toLowerCase().trim();
/*      */     
/*  846 */     if (s.equals("true"))
/*      */     {
/*  848 */       return true;
/*      */     }
/*  850 */     if (s.equals("false"))
/*      */     {
/*  852 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  856 */     warn("Invalid boolean: " + str);
/*  857 */     return defVal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean parseBooleanObject(String str) {
/*  864 */     if (str == null)
/*      */     {
/*  866 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  870 */     String s = str.toLowerCase().trim();
/*      */     
/*  872 */     if (s.equals("true"))
/*      */     {
/*  874 */       return Boolean.TRUE;
/*      */     }
/*  876 */     if (s.equals("false"))
/*      */     {
/*  878 */       return Boolean.FALSE;
/*      */     }
/*      */ 
/*      */     
/*  882 */     warn("Invalid boolean: " + str);
/*  883 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int parseColor(String str, int defVal) {
/*  890 */     if (str == null)
/*      */     {
/*  892 */       return defVal;
/*      */     }
/*      */ 
/*      */     
/*  896 */     str = str.trim();
/*      */ 
/*      */     
/*      */     try {
/*  900 */       int i = Integer.parseInt(str, 16) & 0xFFFFFF;
/*  901 */       return i;
/*      */     }
/*  903 */     catch (NumberFormatException var3) {
/*      */       
/*  905 */       return defVal;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int parseColor4(String str, int defVal) {
/*  912 */     if (str == null)
/*      */     {
/*  914 */       return defVal;
/*      */     }
/*      */ 
/*      */     
/*  918 */     str = str.trim();
/*      */ 
/*      */     
/*      */     try {
/*  922 */       int i = (int)(Long.parseLong(str, 16) & 0xFFFFFFFFFFFFFFFFL);
/*  923 */       return i;
/*      */     }
/*  925 */     catch (NumberFormatException var3) {
/*      */       
/*  927 */       return defVal;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumWorldBlockLayer parseBlockRenderLayer(String str, EnumWorldBlockLayer def) {
/*  934 */     if (str == null)
/*      */     {
/*  936 */       return def;
/*      */     }
/*      */ 
/*      */     
/*  940 */     str = str.toLowerCase().trim();
/*  941 */     EnumWorldBlockLayer[] aenumworldblocklayer = EnumWorldBlockLayer.values();
/*      */     
/*  943 */     for (int i = 0; i < aenumworldblocklayer.length; i++) {
/*      */       
/*  945 */       EnumWorldBlockLayer enumworldblocklayer = aenumworldblocklayer[i];
/*      */       
/*  947 */       if (str.equals(enumworldblocklayer.name().toLowerCase()))
/*      */       {
/*  949 */         return enumworldblocklayer;
/*      */       }
/*      */     } 
/*      */     
/*  953 */     return def;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T parseObject(String str, T[] objs, INameGetter<T> nameGetter, String property) {
/*  959 */     if (str == null)
/*      */     {
/*  961 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  965 */     String s = str.toLowerCase().trim();
/*      */     
/*  967 */     for (int i = 0; i < objs.length; i++) {
/*      */       
/*  969 */       T t = objs[i];
/*  970 */       String s1 = nameGetter.getName(t);
/*      */       
/*  972 */       if (s1 != null && s1.toLowerCase().equals(s))
/*      */       {
/*  974 */         return t;
/*      */       }
/*      */     } 
/*      */     
/*  978 */     warn("Invalid " + property + ": " + str);
/*  979 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T[] parseObjects(String str, T[] objs, INameGetter nameGetter, String property, T[] errValue) {
/*  985 */     if (str == null)
/*      */     {
/*  987 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  991 */     str = str.toLowerCase().trim();
/*  992 */     String[] astring = Config.tokenize(str, " ");
/*  993 */     T[] at = (T[])Array.newInstance(objs.getClass().getComponentType(), astring.length);
/*      */     
/*  995 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  997 */       String s = astring[i];
/*  998 */       T t = parseObject(s, objs, nameGetter, property);
/*      */       
/* 1000 */       if (t == null)
/*      */       {
/* 1002 */         return errValue;
/*      */       }
/*      */       
/* 1005 */       at[i] = t;
/*      */     } 
/*      */     
/* 1008 */     return at;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Enum parseEnum(String str, Enum[] enums, String property) {
/* 1014 */     return parseObject(str, enums, NAME_GETTER_ENUM, property);
/*      */   }
/*      */ 
/*      */   
/*      */   public Enum[] parseEnums(String str, Enum[] enums, String property, Enum[] errValue) {
/* 1019 */     return parseObjects(str, enums, NAME_GETTER_ENUM, property, errValue);
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumDyeColor[] parseDyeColors(String str, String property, EnumDyeColor[] errValue) {
/* 1024 */     return parseObjects(str, EnumDyeColor.values(), NAME_GETTER_DYE_COLOR, property, errValue);
/*      */   }
/*      */ 
/*      */   
/*      */   public Weather[] parseWeather(String str, String property, Weather[] errValue) {
/* 1029 */     return parseObjects(str, Weather.values(), NAME_GETTER_ENUM, property, errValue);
/*      */   }
/*      */ 
/*      */   
/*      */   public NbtTagValue parseNbtTagValue(String path, String value) {
/* 1034 */     return (path != null && value != null) ? new NbtTagValue(path, value) : null;
/*      */   }
/*      */ 
/*      */   
/*      */   public VillagerProfession[] parseProfessions(String profStr) {
/* 1039 */     if (profStr == null)
/*      */     {
/* 1041 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1045 */     List<VillagerProfession> list = new ArrayList<>();
/* 1046 */     String[] astring = Config.tokenize(profStr, " ");
/*      */     
/* 1048 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/* 1050 */       String s = astring[i];
/* 1051 */       VillagerProfession villagerprofession = parseProfession(s);
/*      */       
/* 1053 */       if (villagerprofession == null) {
/*      */         
/* 1055 */         warn("Invalid profession: " + s);
/* 1056 */         return PROFESSIONS_INVALID;
/*      */       } 
/*      */       
/* 1059 */       list.add(villagerprofession);
/*      */     } 
/*      */     
/* 1062 */     if (list.isEmpty())
/*      */     {
/* 1064 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1068 */     VillagerProfession[] avillagerprofession = list.<VillagerProfession>toArray(new VillagerProfession[0]);
/* 1069 */     return avillagerprofession;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private VillagerProfession parseProfession(String str) {
/* 1076 */     str = str.toLowerCase();
/* 1077 */     String[] astring = Config.tokenize(str, ":");
/*      */     
/* 1079 */     if (astring.length > 2)
/*      */     {
/* 1081 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1085 */     String s = astring[0];
/* 1086 */     String s1 = null;
/*      */     
/* 1088 */     if (astring.length > 1)
/*      */     {
/* 1090 */       s1 = astring[1];
/*      */     }
/*      */     
/* 1093 */     int i = parseProfessionId(s);
/*      */     
/* 1095 */     if (i < 0)
/*      */     {
/* 1097 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1101 */     int[] aint = null;
/*      */     
/* 1103 */     if (s1 != null) {
/*      */       
/* 1105 */       aint = parseCareerIds(i, s1);
/*      */       
/* 1107 */       if (aint == null)
/*      */       {
/* 1109 */         return null;
/*      */       }
/*      */     } 
/*      */     
/* 1113 */     return new VillagerProfession(i, aint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseProfessionId(String str) {
/* 1120 */     int i = Config.parseInt(str, -1);
/* 1121 */     return (i >= 0) ? i : (str.equals("farmer") ? 0 : (str.equals("librarian") ? 1 : (str.equals("priest") ? 2 : (str.equals("blacksmith") ? 3 : (str.equals("butcher") ? 4 : (str.equals("nitwit") ? 5 : -1))))));
/*      */   }
/*      */ 
/*      */   
/*      */   private static int[] parseCareerIds(int prof, String str) {
/* 1126 */     Set<Integer> set = new HashSet<>();
/* 1127 */     String[] astring = Config.tokenize(str, ",");
/*      */     
/* 1129 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/* 1131 */       String s = astring[i];
/* 1132 */       int j = parseCareerId(prof, s);
/*      */       
/* 1134 */       if (j < 0)
/*      */       {
/* 1136 */         return null;
/*      */       }
/*      */       
/* 1139 */       set.add(Integer.valueOf(j));
/*      */     } 
/*      */     
/* 1142 */     Integer[] ainteger = set.<Integer>toArray(new Integer[0]);
/* 1143 */     int[] aint = new int[ainteger.length];
/*      */     
/* 1145 */     for (int k = 0; k < aint.length; k++)
/*      */     {
/* 1147 */       aint[k] = ainteger[k].intValue();
/*      */     }
/*      */     
/* 1150 */     return aint;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int parseCareerId(int prof, String str) {
/* 1155 */     int i = Config.parseInt(str, -1);
/*      */     
/* 1157 */     if (i >= 0)
/*      */     {
/* 1159 */       return i;
/*      */     }
/*      */ 
/*      */     
/* 1163 */     if (prof == 0) {
/*      */       
/* 1165 */       if (str.equals("farmer"))
/*      */       {
/* 1167 */         return 1;
/*      */       }
/*      */       
/* 1170 */       if (str.equals("fisherman"))
/*      */       {
/* 1172 */         return 2;
/*      */       }
/*      */       
/* 1175 */       if (str.equals("shepherd"))
/*      */       {
/* 1177 */         return 3;
/*      */       }
/*      */       
/* 1180 */       if (str.equals("fletcher"))
/*      */       {
/* 1182 */         return 4;
/*      */       }
/*      */     } 
/*      */     
/* 1186 */     if (prof == 1) {
/*      */       
/* 1188 */       if (str.equals("librarian"))
/*      */       {
/* 1190 */         return 1;
/*      */       }
/*      */       
/* 1193 */       if (str.equals("cartographer"))
/*      */       {
/* 1195 */         return 2;
/*      */       }
/*      */     } 
/*      */     
/* 1199 */     if (prof == 2 && str.equals("cleric"))
/*      */     {
/* 1201 */       return 1;
/*      */     }
/*      */ 
/*      */     
/* 1205 */     if (prof == 3) {
/*      */       
/* 1207 */       if (str.equals("armor"))
/*      */       {
/* 1209 */         return 1;
/*      */       }
/*      */       
/* 1212 */       if (str.equals("weapon"))
/*      */       {
/* 1214 */         return 2;
/*      */       }
/*      */       
/* 1217 */       if (str.equals("tool"))
/*      */       {
/* 1219 */         return 3;
/*      */       }
/*      */     } 
/*      */     
/* 1223 */     if (prof == 4) {
/*      */       
/* 1225 */       if (str.equals("butcher"))
/*      */       {
/* 1227 */         return 1;
/*      */       }
/*      */       
/* 1230 */       if (str.equals("leather"))
/*      */       {
/* 1232 */         return 2;
/*      */       }
/*      */     } 
/*      */     
/* 1236 */     return (prof == 5 && str.equals("nitwit")) ? 1 : -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] parseItems(String str) {
/* 1243 */     str = str.trim();
/* 1244 */     Set<Integer> set = new TreeSet<>();
/* 1245 */     String[] astring = Config.tokenize(str, " ");
/*      */     
/* 1247 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/* 1249 */       String s = astring[i];
/* 1250 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/* 1251 */       Item item = (Item)Item.itemRegistry.getObject(resourcelocation);
/*      */       
/* 1253 */       if (item == null) {
/*      */         
/* 1255 */         warn("Item not found: " + s);
/*      */       }
/*      */       else {
/*      */         
/* 1259 */         int j = Item.getIdFromItem(item);
/*      */         
/* 1261 */         if (j < 0) {
/*      */           
/* 1263 */           warn("Item has no ID: " + item + ", name: " + s);
/*      */         }
/*      */         else {
/*      */           
/* 1267 */           set.add(new Integer(j));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1272 */     Integer[] ainteger = set.<Integer>toArray(new Integer[0]);
/* 1273 */     int[] aint = Config.toPrimitive(ainteger);
/* 1274 */     return aint;
/*      */   }
/*      */ 
/*      */   
/*      */   public int[] parseEntities(String str) {
/* 1279 */     str = str.trim();
/* 1280 */     Set<Integer> set = new TreeSet<>();
/* 1281 */     String[] astring = Config.tokenize(str, " ");
/*      */     
/* 1283 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/* 1285 */       String s = astring[i];
/* 1286 */       int j = EntityUtils.getEntityIdByName(s);
/*      */       
/* 1288 */       if (j < 0) {
/*      */         
/* 1290 */         warn("Entity not found: " + s);
/*      */       }
/*      */       else {
/*      */         
/* 1294 */         set.add(new Integer(j));
/*      */       } 
/*      */     } 
/*      */     
/* 1298 */     Integer[] ainteger = set.<Integer>toArray(new Integer[0]);
/* 1299 */     int[] aint = Config.toPrimitive(ainteger);
/* 1300 */     return aint;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\config\ConnectedParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */