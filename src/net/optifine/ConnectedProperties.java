/*      */ package net.optifine;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.optifine.config.ConnectedParser;
/*      */ import net.optifine.config.MatchBlock;
/*      */ import net.optifine.config.Matches;
/*      */ import net.optifine.config.RangeListInt;
/*      */ import net.optifine.util.TextureUtils;
/*      */ 
/*      */ public class ConnectedProperties {
/*   21 */   public String name = null;
/*   22 */   public String basePath = null;
/*   23 */   public MatchBlock[] matchBlocks = null;
/*   24 */   public int[] metadatas = null;
/*   25 */   public String[] matchTiles = null;
/*   26 */   public int method = 0;
/*   27 */   public String[] tiles = null;
/*   28 */   public int connect = 0;
/*   29 */   public int faces = 63;
/*   30 */   public BiomeGenBase[] biomes = null;
/*   31 */   public RangeListInt heights = null;
/*   32 */   public int renderPass = 0;
/*      */   public boolean innerSeams = false;
/*   34 */   public int[] ctmTileIndexes = null;
/*   35 */   public int width = 0;
/*   36 */   public int height = 0;
/*   37 */   public int[] weights = null;
/*   38 */   public int randomLoops = 0;
/*   39 */   public int symmetry = 1;
/*      */   public boolean linked = false;
/*   41 */   public NbtTagValue nbtName = null;
/*   42 */   public int[] sumWeights = null;
/*   43 */   public int sumAllWeights = 1;
/*   44 */   public TextureAtlasSprite[] matchTileIcons = null;
/*   45 */   public TextureAtlasSprite[] tileIcons = null;
/*   46 */   public MatchBlock[] connectBlocks = null;
/*   47 */   public String[] connectTiles = null;
/*   48 */   public TextureAtlasSprite[] connectTileIcons = null;
/*   49 */   public int tintIndex = -1;
/*   50 */   public IBlockState tintBlockState = Blocks.air.getDefaultState();
/*   51 */   public EnumWorldBlockLayer layer = null;
/*      */   
/*      */   public static final int METHOD_NONE = 0;
/*      */   public static final int METHOD_CTM = 1;
/*      */   public static final int METHOD_HORIZONTAL = 2;
/*      */   public static final int METHOD_TOP = 3;
/*      */   public static final int METHOD_RANDOM = 4;
/*      */   public static final int METHOD_REPEAT = 5;
/*      */   public static final int METHOD_VERTICAL = 6;
/*      */   public static final int METHOD_FIXED = 7;
/*      */   public static final int METHOD_HORIZONTAL_VERTICAL = 8;
/*      */   public static final int METHOD_VERTICAL_HORIZONTAL = 9;
/*      */   public static final int METHOD_CTM_COMPACT = 10;
/*      */   public static final int METHOD_OVERLAY = 11;
/*      */   public static final int METHOD_OVERLAY_FIXED = 12;
/*      */   public static final int METHOD_OVERLAY_RANDOM = 13;
/*      */   public static final int METHOD_OVERLAY_REPEAT = 14;
/*      */   public static final int METHOD_OVERLAY_CTM = 15;
/*      */   public static final int CONNECT_NONE = 0;
/*      */   public static final int CONNECT_BLOCK = 1;
/*      */   public static final int CONNECT_TILE = 2;
/*      */   public static final int CONNECT_MATERIAL = 3;
/*      */   public static final int CONNECT_UNKNOWN = 128;
/*      */   public static final int FACE_BOTTOM = 1;
/*      */   public static final int FACE_TOP = 2;
/*      */   public static final int FACE_NORTH = 4;
/*      */   public static final int FACE_SOUTH = 8;
/*      */   public static final int FACE_WEST = 16;
/*      */   public static final int FACE_EAST = 32;
/*      */   public static final int FACE_SIDES = 60;
/*      */   public static final int FACE_ALL = 63;
/*      */   public static final int FACE_UNKNOWN = 128;
/*      */   public static final int SYMMETRY_NONE = 1;
/*      */   public static final int SYMMETRY_OPPOSITE = 2;
/*      */   public static final int SYMMETRY_ALL = 6;
/*      */   public static final int SYMMETRY_UNKNOWN = 128;
/*      */   public static final String TILE_SKIP_PNG = "<skip>.png";
/*      */   public static final String TILE_DEFAULT_PNG = "<default>.png";
/*      */   
/*      */   public ConnectedProperties(Properties props, String path) {
/*   91 */     ConnectedParser connectedparser = new ConnectedParser("ConnectedTextures");
/*   92 */     this.name = connectedparser.parseName(path);
/*   93 */     this.basePath = connectedparser.parseBasePath(path);
/*   94 */     this.matchBlocks = connectedparser.parseMatchBlocks(props.getProperty("matchBlocks"));
/*   95 */     this.metadatas = connectedparser.parseIntList(props.getProperty("metadata"));
/*   96 */     this.matchTiles = parseMatchTiles(props.getProperty("matchTiles"));
/*   97 */     this.method = parseMethod(props.getProperty("method"));
/*   98 */     this.tiles = parseTileNames(props.getProperty("tiles"));
/*   99 */     this.connect = parseConnect(props.getProperty("connect"));
/*  100 */     this.faces = parseFaces(props.getProperty("faces"));
/*  101 */     this.biomes = connectedparser.parseBiomes(props.getProperty("biomes"));
/*  102 */     this.heights = connectedparser.parseRangeListInt(props.getProperty("heights"));
/*      */     
/*  104 */     if (this.heights == null) {
/*      */       
/*  106 */       int i = connectedparser.parseInt(props.getProperty("minHeight"), -1);
/*  107 */       int j = connectedparser.parseInt(props.getProperty("maxHeight"), 1024);
/*      */       
/*  109 */       if (i != -1 || j != 1024)
/*      */       {
/*  111 */         this.heights = new RangeListInt(new RangeInt(i, j));
/*      */       }
/*      */     } 
/*      */     
/*  115 */     this.renderPass = connectedparser.parseInt(props.getProperty("renderPass"), -1);
/*  116 */     this.innerSeams = connectedparser.parseBoolean(props.getProperty("innerSeams"), false);
/*  117 */     this.ctmTileIndexes = parseCtmTileIndexes(props);
/*  118 */     this.width = connectedparser.parseInt(props.getProperty("width"), -1);
/*  119 */     this.height = connectedparser.parseInt(props.getProperty("height"), -1);
/*  120 */     this.weights = connectedparser.parseIntList(props.getProperty("weights"));
/*  121 */     this.randomLoops = connectedparser.parseInt(props.getProperty("randomLoops"), 0);
/*  122 */     this.symmetry = parseSymmetry(props.getProperty("symmetry"));
/*  123 */     this.linked = connectedparser.parseBoolean(props.getProperty("linked"), false);
/*  124 */     this.nbtName = connectedparser.parseNbtTagValue("name", props.getProperty("name"));
/*  125 */     this.connectBlocks = connectedparser.parseMatchBlocks(props.getProperty("connectBlocks"));
/*  126 */     this.connectTiles = parseMatchTiles(props.getProperty("connectTiles"));
/*  127 */     this.tintIndex = connectedparser.parseInt(props.getProperty("tintIndex"), -1);
/*  128 */     this.tintBlockState = connectedparser.parseBlockState(props.getProperty("tintBlock"), Blocks.air.getDefaultState());
/*  129 */     this.layer = connectedparser.parseBlockRenderLayer(props.getProperty("layer"), EnumWorldBlockLayer.CUTOUT_MIPPED);
/*      */   }
/*      */ 
/*      */   
/*      */   private int[] parseCtmTileIndexes(Properties props) {
/*  134 */     if (this.tiles == null)
/*      */     {
/*  136 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  140 */     Map<Integer, Integer> map = new HashMap<>();
/*      */     
/*  142 */     for (Object object : props.keySet()) {
/*      */       
/*  144 */       if (object instanceof String) {
/*      */         
/*  146 */         String s = (String)object;
/*  147 */         String s1 = "ctm.";
/*      */         
/*  149 */         if (s.startsWith(s1)) {
/*      */           
/*  151 */           String s2 = s.substring(s1.length());
/*  152 */           String s3 = props.getProperty(s);
/*      */           
/*  154 */           if (s3 != null) {
/*      */             
/*  156 */             s3 = s3.trim();
/*  157 */             int i = Config.parseInt(s2, -1);
/*      */             
/*  159 */             if (i >= 0 && i <= 46) {
/*      */               
/*  161 */               int j = Config.parseInt(s3, -1);
/*      */               
/*  163 */               if (j >= 0 && j < this.tiles.length) {
/*      */                 
/*  165 */                 map.put(Integer.valueOf(i), Integer.valueOf(j));
/*      */                 
/*      */                 continue;
/*      */               } 
/*  169 */               Config.warn("Invalid CTM tile index: " + s3);
/*      */               
/*      */               continue;
/*      */             } 
/*      */             
/*  174 */             Config.warn("Invalid CTM index: " + s2);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  181 */     if (map.isEmpty())
/*      */     {
/*  183 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  187 */     int[] aint = new int[47];
/*      */     
/*  189 */     for (int k = 0; k < aint.length; k++) {
/*      */       
/*  191 */       aint[k] = -1;
/*      */       
/*  193 */       if (map.containsKey(Integer.valueOf(k)))
/*      */       {
/*  195 */         aint[k] = ((Integer)map.get(Integer.valueOf(k))).intValue();
/*      */       }
/*      */     } 
/*      */     
/*  199 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String[] parseMatchTiles(String str) {
/*  206 */     if (str == null)
/*      */     {
/*  208 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  212 */     String[] astring = Config.tokenize(str, " ");
/*      */     
/*  214 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  216 */       String s = astring[i];
/*      */       
/*  218 */       if (s.endsWith(".png"))
/*      */       {
/*  220 */         s = s.substring(0, s.length() - 4);
/*      */       }
/*      */       
/*  223 */       s = TextureUtils.fixResourcePath(s, this.basePath);
/*  224 */       astring[i] = s;
/*      */     } 
/*      */     
/*  227 */     return astring;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String parseName(String path) {
/*  233 */     String s = path;
/*  234 */     int i = path.lastIndexOf('/');
/*      */     
/*  236 */     if (i >= 0)
/*      */     {
/*  238 */       s = path.substring(i + 1);
/*      */     }
/*      */     
/*  241 */     int j = s.lastIndexOf('.');
/*      */     
/*  243 */     if (j >= 0)
/*      */     {
/*  245 */       s = s.substring(0, j);
/*      */     }
/*      */     
/*  248 */     return s;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String parseBasePath(String path) {
/*  253 */     int i = path.lastIndexOf('/');
/*  254 */     return (i < 0) ? "" : path.substring(0, i);
/*      */   }
/*      */ 
/*      */   
/*      */   private String[] parseTileNames(String str) {
/*  259 */     if (str == null)
/*      */     {
/*  261 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  265 */     List<String> list = new ArrayList();
/*  266 */     String[] astring = Config.tokenize(str, " ,");
/*      */ 
/*      */     
/*  269 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  271 */       String s = astring[i];
/*      */       
/*  273 */       if (s.contains("-")) {
/*      */         
/*  275 */         String[] astring1 = Config.tokenize(s, "-");
/*      */         
/*  277 */         if (astring1.length == 2) {
/*      */           
/*  279 */           int j = Config.parseInt(astring1[0], -1);
/*  280 */           int k = Config.parseInt(astring1[1], -1);
/*      */           
/*  282 */           if (j >= 0 && k >= 0) {
/*      */             
/*  284 */             if (j > k) {
/*      */               
/*  286 */               Config.warn("Invalid interval: " + s + ", when parsing: " + str);
/*      */             }
/*      */             else {
/*      */               
/*  290 */               int l = j;
/*      */ 
/*      */ 
/*      */               
/*  294 */               while (l <= k) {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  299 */                 list.add(String.valueOf(l));
/*  300 */                 l++;
/*      */               } 
/*      */             }  continue;
/*      */           } 
/*      */         } 
/*      */       } 
/*  306 */       list.add(s);
/*      */       continue;
/*      */     } 
/*  309 */     String[] astring2 = list.<String>toArray(new String[0]);
/*      */     
/*  311 */     for (int i1 = 0; i1 < astring2.length; i1++) {
/*      */       
/*  313 */       String s1 = astring2[i1];
/*  314 */       s1 = TextureUtils.fixResourcePath(s1, this.basePath);
/*      */       
/*  316 */       if (!s1.startsWith(this.basePath) && !s1.startsWith("textures/") && !s1.startsWith("mcpatcher/"))
/*      */       {
/*  318 */         s1 = this.basePath + "/" + s1;
/*      */       }
/*      */       
/*  321 */       if (s1.endsWith(".png"))
/*      */       {
/*  323 */         s1 = s1.substring(0, s1.length() - 4);
/*      */       }
/*      */       
/*  326 */       if (!s1.isEmpty() && s1.charAt(0) == '/')
/*      */       {
/*  328 */         s1 = s1.substring(1);
/*      */       }
/*      */       
/*  331 */       astring2[i1] = s1;
/*      */     } 
/*      */     
/*  334 */     return astring2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseSymmetry(String str) {
/*  340 */     if (str == null)
/*      */     {
/*  342 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*  346 */     str = str.trim();
/*      */     
/*  348 */     if (str.equals("opposite"))
/*      */     {
/*  350 */       return 2;
/*      */     }
/*  352 */     if (str.equals("all"))
/*      */     {
/*  354 */       return 6;
/*      */     }
/*      */ 
/*      */     
/*  358 */     Config.warn("Unknown symmetry: " + str);
/*  359 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseFaces(String str) {
/*  366 */     if (str == null)
/*      */     {
/*  368 */       return 63;
/*      */     }
/*      */ 
/*      */     
/*  372 */     String[] astring = Config.tokenize(str, " ,");
/*  373 */     int i = 0;
/*      */     
/*  375 */     for (int j = 0; j < astring.length; j++) {
/*      */       
/*  377 */       String s = astring[j];
/*  378 */       int k = parseFace(s);
/*  379 */       i |= k;
/*      */     } 
/*      */     
/*  382 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseFace(String str) {
/*  388 */     str = str.toLowerCase();
/*      */     
/*  390 */     if (!str.equals("bottom") && !str.equals("down")) {
/*      */       
/*  392 */       if (!str.equals("top") && !str.equals("up")) {
/*      */         
/*  394 */         if (str.equals("north"))
/*      */         {
/*  396 */           return 4;
/*      */         }
/*  398 */         if (str.equals("south"))
/*      */         {
/*  400 */           return 8;
/*      */         }
/*  402 */         if (str.equals("east"))
/*      */         {
/*  404 */           return 32;
/*      */         }
/*  406 */         if (str.equals("west"))
/*      */         {
/*  408 */           return 16;
/*      */         }
/*  410 */         if (str.equals("sides"))
/*      */         {
/*  412 */           return 60;
/*      */         }
/*  414 */         if (str.equals("all"))
/*      */         {
/*  416 */           return 63;
/*      */         }
/*      */ 
/*      */         
/*  420 */         Config.warn("Unknown face: " + str);
/*  421 */         return 128;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  426 */       return 2;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  431 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseConnect(String str) {
/*  437 */     if (str == null)
/*      */     {
/*  439 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  443 */     str = str.trim();
/*      */     
/*  445 */     if (str.equals("block"))
/*      */     {
/*  447 */       return 1;
/*      */     }
/*  449 */     if (str.equals("tile"))
/*      */     {
/*  451 */       return 2;
/*      */     }
/*  453 */     if (str.equals("material"))
/*      */     {
/*  455 */       return 3;
/*      */     }
/*      */ 
/*      */     
/*  459 */     Config.warn("Unknown connect: " + str);
/*  460 */     return 128;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IProperty getProperty(String key, Collection properties) {
/*  467 */     for (Object e : properties) {
/*      */       
/*  469 */       IProperty iproperty = (IProperty)e;
/*  470 */       if (key.equals(iproperty.getName()))
/*      */       {
/*  472 */         return iproperty;
/*      */       }
/*      */     } 
/*      */     
/*  476 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int parseMethod(String str) {
/*  481 */     if (str == null)
/*      */     {
/*  483 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*  487 */     str = str.trim();
/*      */     
/*  489 */     if (!str.equals("ctm") && !str.equals("glass")) {
/*      */       
/*  491 */       if (str.equals("ctm_compact"))
/*      */       {
/*  493 */         return 10;
/*      */       }
/*  495 */       if (!str.equals("horizontal") && !str.equals("bookshelf")) {
/*      */         
/*  497 */         if (str.equals("vertical"))
/*      */         {
/*  499 */           return 6;
/*      */         }
/*  501 */         if (str.equals("top"))
/*      */         {
/*  503 */           return 3;
/*      */         }
/*  505 */         if (str.equals("random"))
/*      */         {
/*  507 */           return 4;
/*      */         }
/*  509 */         if (str.equals("repeat"))
/*      */         {
/*  511 */           return 5;
/*      */         }
/*  513 */         if (str.equals("fixed"))
/*      */         {
/*  515 */           return 7;
/*      */         }
/*  517 */         if (!str.equals("horizontal+vertical") && !str.equals("h+v")) {
/*      */           
/*  519 */           if (!str.equals("vertical+horizontal") && !str.equals("v+h")) {
/*      */             
/*  521 */             if (str.equals("overlay"))
/*      */             {
/*  523 */               return 11;
/*      */             }
/*  525 */             if (str.equals("overlay_fixed"))
/*      */             {
/*  527 */               return 12;
/*      */             }
/*  529 */             if (str.equals("overlay_random"))
/*      */             {
/*  531 */               return 13;
/*      */             }
/*  533 */             if (str.equals("overlay_repeat"))
/*      */             {
/*  535 */               return 14;
/*      */             }
/*  537 */             if (str.equals("overlay_ctm"))
/*      */             {
/*  539 */               return 15;
/*      */             }
/*      */ 
/*      */             
/*  543 */             Config.warn("Unknown method: " + str);
/*  544 */             return 0;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  549 */           return 9;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  554 */         return 8;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  559 */       return 2;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  564 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValid(String path) {
/*  571 */     if (this.name != null && !this.name.isEmpty()) {
/*      */       
/*  573 */       if (this.basePath == null) {
/*      */         
/*  575 */         Config.warn("No base path found: " + path);
/*  576 */         return false;
/*      */       } 
/*      */ 
/*      */       
/*  580 */       if (this.matchBlocks == null)
/*      */       {
/*  582 */         this.matchBlocks = detectMatchBlocks();
/*      */       }
/*      */       
/*  585 */       if (this.matchTiles == null && this.matchBlocks == null)
/*      */       {
/*  587 */         this.matchTiles = detectMatchTiles();
/*      */       }
/*      */       
/*  590 */       if (this.matchBlocks == null && this.matchTiles == null) {
/*      */         
/*  592 */         Config.warn("No matchBlocks or matchTiles specified: " + path);
/*  593 */         return false;
/*      */       } 
/*  595 */       if (this.method == 0) {
/*      */         
/*  597 */         Config.warn("No method: " + path);
/*  598 */         return false;
/*      */       } 
/*  600 */       if (this.tiles != null && this.tiles.length > 0) {
/*      */         
/*  602 */         if (this.connect == 0)
/*      */         {
/*  604 */           this.connect = detectConnect();
/*      */         }
/*      */         
/*  607 */         if (this.connect == 128) {
/*      */           
/*  609 */           Config.warn("Invalid connect in: " + path);
/*  610 */           return false;
/*      */         } 
/*  612 */         if (this.renderPass > 0) {
/*      */           
/*  614 */           Config.warn("Render pass not supported: " + this.renderPass);
/*  615 */           return false;
/*      */         } 
/*  617 */         if ((this.faces & 0x80) != 0) {
/*      */           
/*  619 */           Config.warn("Invalid faces in: " + path);
/*  620 */           return false;
/*      */         } 
/*  622 */         if ((this.symmetry & 0x80) != 0) {
/*      */           
/*  624 */           Config.warn("Invalid symmetry in: " + path);
/*  625 */           return false;
/*      */         } 
/*      */ 
/*      */         
/*  629 */         switch (this.method) {
/*      */           
/*      */           case 1:
/*  632 */             return isValidCtm(path);
/*      */           
/*      */           case 2:
/*  635 */             return isValidHorizontal(path);
/*      */           
/*      */           case 3:
/*  638 */             return isValidTop(path);
/*      */           
/*      */           case 4:
/*  641 */             return isValidRandom(path);
/*      */           
/*      */           case 5:
/*  644 */             return isValidRepeat(path);
/*      */           
/*      */           case 6:
/*  647 */             return isValidVertical(path);
/*      */           
/*      */           case 7:
/*  650 */             return isValidFixed(path);
/*      */           
/*      */           case 8:
/*  653 */             return isValidHorizontalVertical(path);
/*      */           
/*      */           case 9:
/*  656 */             return isValidVerticalHorizontal(path);
/*      */           
/*      */           case 10:
/*  659 */             return isValidCtmCompact(path);
/*      */           
/*      */           case 11:
/*  662 */             return isValidOverlay(path);
/*      */           
/*      */           case 12:
/*  665 */             return isValidOverlayFixed(path);
/*      */           
/*      */           case 13:
/*  668 */             return isValidOverlayRandom(path);
/*      */           
/*      */           case 14:
/*  671 */             return isValidOverlayRepeat(path);
/*      */           
/*      */           case 15:
/*  674 */             return isValidOverlayCtm(path);
/*      */         } 
/*      */         
/*  677 */         Config.warn("Unknown method: " + path);
/*  678 */         return false;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  684 */       Config.warn("No tiles specified: " + path);
/*  685 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  691 */     Config.warn("No name found: " + path);
/*  692 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int detectConnect() {
/*  698 */     return (this.matchBlocks != null) ? 1 : ((this.matchTiles != null) ? 2 : 128);
/*      */   }
/*      */ 
/*      */   
/*      */   private MatchBlock[] detectMatchBlocks() {
/*  703 */     int[] aint = detectMatchBlockIds();
/*      */     
/*  705 */     if (aint == null)
/*      */     {
/*  707 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  711 */     MatchBlock[] amatchblock = new MatchBlock[aint.length];
/*      */     
/*  713 */     for (int i = 0; i < amatchblock.length; i++)
/*      */     {
/*  715 */       amatchblock[i] = new MatchBlock(aint[i]);
/*      */     }
/*      */     
/*  718 */     return amatchblock;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] detectMatchBlockIds() {
/*  724 */     if (!this.name.startsWith("block"))
/*      */     {
/*  726 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  730 */     int i = "block".length();
/*      */     
/*      */     int j;
/*  733 */     for (j = i; j < this.name.length(); j++) {
/*      */       
/*  735 */       char c0 = this.name.charAt(j);
/*      */       
/*  737 */       if (c0 < '0' || c0 > '9') {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  743 */     if (j == i)
/*      */     {
/*  745 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  749 */     String s = this.name.substring(i, j);
/*  750 */     int k = Config.parseInt(s, -1);
/*  751 */     (new int[1])[0] = k; return (k < 0) ? null : new int[1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String[] detectMatchTiles() {
/*  758 */     TextureAtlasSprite textureatlassprite = getIcon(this.name);
/*  759 */     (new String[1])[0] = this.name; return (textureatlassprite == null) ? null : new String[1];
/*      */   }
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getIcon(String iconName) {
/*  764 */     TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
/*  765 */     TextureAtlasSprite textureatlassprite = texturemap.getSpriteSafe(iconName);
/*      */     
/*  767 */     if (textureatlassprite != null)
/*      */     {
/*  769 */       return textureatlassprite;
/*      */     }
/*      */ 
/*      */     
/*  773 */     textureatlassprite = texturemap.getSpriteSafe("blocks/" + iconName);
/*  774 */     return textureatlassprite;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidCtm(String path) {
/*  780 */     if (this.tiles == null)
/*      */     {
/*  782 */       this.tiles = parseTileNames("0-11 16-27 32-43 48-58");
/*      */     }
/*      */     
/*  785 */     if (this.tiles.length < 47) {
/*      */       
/*  787 */       Config.warn("Invalid tiles, must be at least 47: " + path);
/*  788 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  792 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidCtmCompact(String path) {
/*  798 */     if (this.tiles == null)
/*      */     {
/*  800 */       this.tiles = parseTileNames("0-4");
/*      */     }
/*      */     
/*  803 */     if (this.tiles.length < 5) {
/*      */       
/*  805 */       Config.warn("Invalid tiles, must be at least 5: " + path);
/*  806 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  810 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidOverlay(String path) {
/*  816 */     if (this.tiles == null)
/*      */     {
/*  818 */       this.tiles = parseTileNames("0-16");
/*      */     }
/*      */     
/*  821 */     if (this.tiles.length < 17) {
/*      */       
/*  823 */       Config.warn("Invalid tiles, must be at least 17: " + path);
/*  824 */       return false;
/*      */     } 
/*  826 */     if (this.layer != null && this.layer != EnumWorldBlockLayer.SOLID)
/*      */     {
/*  828 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  832 */     Config.warn("Invalid overlay layer: " + this.layer);
/*  833 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidOverlayFixed(String path) {
/*  839 */     if (!isValidFixed(path))
/*      */     {
/*  841 */       return false;
/*      */     }
/*  843 */     if (this.layer != null && this.layer != EnumWorldBlockLayer.SOLID)
/*      */     {
/*  845 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  849 */     Config.warn("Invalid overlay layer: " + this.layer);
/*  850 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidOverlayRandom(String path) {
/*  856 */     if (!isValidRandom(path))
/*      */     {
/*  858 */       return false;
/*      */     }
/*  860 */     if (this.layer != null && this.layer != EnumWorldBlockLayer.SOLID)
/*      */     {
/*  862 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  866 */     Config.warn("Invalid overlay layer: " + this.layer);
/*  867 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidOverlayRepeat(String path) {
/*  873 */     if (!isValidRepeat(path))
/*      */     {
/*  875 */       return false;
/*      */     }
/*  877 */     if (this.layer != null && this.layer != EnumWorldBlockLayer.SOLID)
/*      */     {
/*  879 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  883 */     Config.warn("Invalid overlay layer: " + this.layer);
/*  884 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidOverlayCtm(String path) {
/*  890 */     if (!isValidCtm(path))
/*      */     {
/*  892 */       return false;
/*      */     }
/*  894 */     if (this.layer != null && this.layer != EnumWorldBlockLayer.SOLID)
/*      */     {
/*  896 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  900 */     Config.warn("Invalid overlay layer: " + this.layer);
/*  901 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidHorizontal(String path) {
/*  907 */     if (this.tiles == null)
/*      */     {
/*  909 */       this.tiles = parseTileNames("12-15");
/*      */     }
/*      */     
/*  912 */     if (this.tiles.length != 4) {
/*      */       
/*  914 */       Config.warn("Invalid tiles, must be exactly 4: " + path);
/*  915 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  919 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidVertical(String path) {
/*  925 */     if (this.tiles == null) {
/*      */       
/*  927 */       Config.warn("No tiles defined for vertical: " + path);
/*  928 */       return false;
/*      */     } 
/*  930 */     if (this.tiles.length != 4) {
/*      */       
/*  932 */       Config.warn("Invalid tiles, must be exactly 4: " + path);
/*  933 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  937 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidHorizontalVertical(String path) {
/*  943 */     if (this.tiles == null) {
/*      */       
/*  945 */       Config.warn("No tiles defined for horizontal+vertical: " + path);
/*  946 */       return false;
/*      */     } 
/*  948 */     if (this.tiles.length != 7) {
/*      */       
/*  950 */       Config.warn("Invalid tiles, must be exactly 7: " + path);
/*  951 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  955 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidVerticalHorizontal(String path) {
/*  961 */     if (this.tiles == null) {
/*      */       
/*  963 */       Config.warn("No tiles defined for vertical+horizontal: " + path);
/*  964 */       return false;
/*      */     } 
/*  966 */     if (this.tiles.length != 7) {
/*      */       
/*  968 */       Config.warn("Invalid tiles, must be exactly 7: " + path);
/*  969 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  973 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidRandom(String path) {
/*  979 */     if (this.tiles != null && this.tiles.length > 0) {
/*      */       
/*  981 */       if (this.weights != null) {
/*      */         
/*  983 */         if (this.weights.length > this.tiles.length) {
/*      */           
/*  985 */           Config.warn("More weights defined than tiles, trimming weights: " + path);
/*  986 */           int[] aint = new int[this.tiles.length];
/*  987 */           System.arraycopy(this.weights, 0, aint, 0, aint.length);
/*  988 */           this.weights = aint;
/*      */         } 
/*      */         
/*  991 */         if (this.weights.length < this.tiles.length) {
/*      */           
/*  993 */           Config.warn("Less weights defined than tiles, expanding weights: " + path);
/*  994 */           int[] aint1 = new int[this.tiles.length];
/*  995 */           System.arraycopy(this.weights, 0, aint1, 0, this.weights.length);
/*  996 */           int i = MathUtils.getAverage(this.weights);
/*      */           
/*  998 */           for (int j = this.weights.length; j < aint1.length; j++)
/*      */           {
/* 1000 */             aint1[j] = i;
/*      */           }
/*      */           
/* 1003 */           this.weights = aint1;
/*      */         } 
/*      */         
/* 1006 */         this.sumWeights = new int[this.weights.length];
/* 1007 */         int k = 0;
/*      */         
/* 1009 */         for (int l = 0; l < this.weights.length; l++) {
/*      */           
/* 1011 */           k += this.weights[l];
/* 1012 */           this.sumWeights[l] = k;
/*      */         } 
/*      */         
/* 1015 */         this.sumAllWeights = k;
/*      */         
/* 1017 */         if (this.sumAllWeights <= 0) {
/*      */           
/* 1019 */           Config.warn("Invalid sum of all weights: " + k);
/* 1020 */           this.sumAllWeights = 1;
/*      */         } 
/*      */       } 
/*      */       
/* 1024 */       if (this.randomLoops >= 0 && this.randomLoops <= 9)
/*      */       {
/* 1026 */         return true;
/*      */       }
/*      */ 
/*      */       
/* 1030 */       Config.warn("Invalid randomLoops: " + this.randomLoops);
/* 1031 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1036 */     Config.warn("Tiles not defined: " + path);
/* 1037 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidRepeat(String path) {
/* 1043 */     if (this.tiles == null) {
/*      */       
/* 1045 */       Config.warn("Tiles not defined: " + path);
/* 1046 */       return false;
/*      */     } 
/* 1048 */     if (this.width <= 0) {
/*      */       
/* 1050 */       Config.warn("Invalid width: " + path);
/* 1051 */       return false;
/*      */     } 
/* 1053 */     if (this.height <= 0) {
/*      */       
/* 1055 */       Config.warn("Invalid height: " + path);
/* 1056 */       return false;
/*      */     } 
/* 1058 */     if (this.tiles.length != this.width * this.height) {
/*      */       
/* 1060 */       Config.warn("Number of tiles does not equal width x height: " + path);
/* 1061 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1065 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidFixed(String path) {
/* 1071 */     if (this.tiles == null) {
/*      */       
/* 1073 */       Config.warn("Tiles not defined: " + path);
/* 1074 */       return false;
/*      */     } 
/* 1076 */     if (this.tiles.length != 1) {
/*      */       
/* 1078 */       Config.warn("Number of tiles should be 1 for method: fixed.");
/* 1079 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1083 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidTop(String path) {
/* 1089 */     if (this.tiles == null)
/*      */     {
/* 1091 */       this.tiles = parseTileNames("66");
/*      */     }
/*      */     
/* 1094 */     if (this.tiles.length != 1) {
/*      */       
/* 1096 */       Config.warn("Invalid tiles, must be exactly 1: " + path);
/* 1097 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1101 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateIcons(TextureMap textureMap) {
/* 1107 */     if (this.matchTiles != null)
/*      */     {
/* 1109 */       this.matchTileIcons = registerIcons(this.matchTiles, textureMap, false, false);
/*      */     }
/*      */     
/* 1112 */     if (this.connectTiles != null)
/*      */     {
/* 1114 */       this.connectTileIcons = registerIcons(this.connectTiles, textureMap, false, false);
/*      */     }
/*      */     
/* 1117 */     if (this.tiles != null)
/*      */     {
/* 1119 */       this.tileIcons = registerIcons(this.tiles, textureMap, true, !isMethodOverlay(this.method));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isMethodOverlay(int method) {
/* 1125 */     switch (method) {
/*      */       
/*      */       case 11:
/*      */       case 12:
/*      */       case 13:
/*      */       case 14:
/*      */       case 15:
/* 1132 */         return true;
/*      */     } 
/*      */     
/* 1135 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite[] registerIcons(String[] tileNames, TextureMap textureMap, boolean skipTiles, boolean defaultTiles) {
/* 1141 */     if (tileNames == null)
/*      */     {
/* 1143 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1147 */     List<TextureAtlasSprite> list = new ArrayList();
/*      */     
/* 1149 */     for (int i = 0; i < tileNames.length; i++) {
/*      */       
/* 1151 */       String s = tileNames[i];
/* 1152 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/* 1153 */       String s1 = resourcelocation.getResourceDomain();
/* 1154 */       String s2 = resourcelocation.getResourcePath();
/*      */       
/* 1156 */       if (!s2.contains("/"))
/*      */       {
/* 1158 */         s2 = "textures/blocks/" + s2;
/*      */       }
/*      */       
/* 1161 */       String s3 = s2 + ".png";
/*      */       
/* 1163 */       if (skipTiles && s3.endsWith("<skip>.png")) {
/*      */         
/* 1165 */         list.add(null);
/*      */       }
/* 1167 */       else if (defaultTiles && s3.endsWith("<default>.png")) {
/*      */         
/* 1169 */         list.add(ConnectedTextures.SPRITE_DEFAULT);
/*      */       }
/*      */       else {
/*      */         
/* 1173 */         ResourceLocation resourcelocation1 = new ResourceLocation(s1, s3);
/* 1174 */         boolean flag = Config.hasResource(resourcelocation1);
/*      */         
/* 1176 */         if (!flag)
/*      */         {
/* 1178 */           Config.warn("File not found: " + s3);
/*      */         }
/*      */         
/* 1181 */         String s4 = "textures/";
/* 1182 */         String s5 = s2;
/*      */         
/* 1184 */         if (s2.startsWith(s4))
/*      */         {
/* 1186 */           s5 = s2.substring(s4.length());
/*      */         }
/*      */         
/* 1189 */         ResourceLocation resourcelocation2 = new ResourceLocation(s1, s5);
/* 1190 */         TextureAtlasSprite textureatlassprite = textureMap.registerSprite(resourcelocation2);
/* 1191 */         list.add(textureatlassprite);
/*      */       } 
/*      */     } 
/*      */     
/* 1195 */     TextureAtlasSprite[] atextureatlassprite = list.<TextureAtlasSprite>toArray(new TextureAtlasSprite[0]);
/* 1196 */     return atextureatlassprite;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean matchesBlockId(int blockId) {
/* 1202 */     return Matches.blockId(blockId, this.matchBlocks);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean matchesBlock(int blockId, int metadata) {
/* 1207 */     return !Matches.block(blockId, metadata, this.matchBlocks) ? false : Matches.metadata(metadata, this.metadatas);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean matchesIcon(TextureAtlasSprite icon) {
/* 1212 */     return Matches.sprite(icon, this.matchTileIcons);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1217 */     return "CTM name: " + this.name + ", basePath: " + this.basePath + ", matchBlocks: " + Config.arrayToString((Object[])this.matchBlocks) + ", matchTiles: " + Config.arrayToString((Object[])this.matchTiles);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean matchesBiome(BiomeGenBase biome) {
/* 1222 */     return Matches.biome(biome, this.biomes);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMetadataMax() {
/* 1227 */     int i = -1;
/* 1228 */     i = getMax(this.metadatas, i);
/*      */     
/* 1230 */     if (this.matchBlocks != null)
/*      */     {
/* 1232 */       for (int j = 0; j < this.matchBlocks.length; j++) {
/*      */         
/* 1234 */         MatchBlock matchblock = this.matchBlocks[j];
/* 1235 */         i = getMax(matchblock.getMetadatas(), i);
/*      */       } 
/*      */     }
/*      */     
/* 1239 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   private int getMax(int[] mds, int max) {
/* 1244 */     if (mds == null)
/*      */     {
/* 1246 */       return max;
/*      */     }
/*      */ 
/*      */     
/* 1250 */     for (int i = 0; i < mds.length; i++) {
/*      */       
/* 1252 */       int j = mds[i];
/*      */       
/* 1254 */       if (j > max)
/*      */       {
/* 1256 */         max = j;
/*      */       }
/*      */     } 
/*      */     
/* 1260 */     return max;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\ConnectedProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */