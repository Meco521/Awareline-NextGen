/*      */ package net.optifine;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.MapColor;
/*      */ import net.minecraft.block.state.BlockStateBase;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.particle.EntityFX;
/*      */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.EnumDyeColor;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.ColorizerFoliage;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.optifine.config.ConnectedParser;
/*      */ import net.optifine.config.MatchBlock;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.render.RenderEnv;
/*      */ import net.optifine.util.PropertiesOrdered;
/*      */ import net.optifine.util.ResUtils;
/*      */ import net.optifine.util.StrUtils;
/*      */ import org.apache.commons.lang3.tuple.ImmutablePair;
/*      */ import org.apache.commons.lang3.tuple.Pair;
/*      */ 
/*      */ public class CustomColors {
/*   46 */   private static String paletteFormatDefault = "vanilla";
/*   47 */   static CustomColormap waterColors = null;
/*   48 */   static CustomColormap foliagePineColors = null;
/*   49 */   static CustomColormap foliageBirchColors = null;
/*   50 */   static CustomColormap swampFoliageColors = null;
/*   51 */   static CustomColormap swampGrassColors = null;
/*   52 */   private static CustomColormap[] colorsBlockColormaps = null;
/*   53 */   private static CustomColormap[][] blockColormaps = (CustomColormap[][])null;
/*   54 */   private static CustomColormap skyColors = null;
/*   55 */   private static final CustomColorFader skyColorFader = new CustomColorFader();
/*   56 */   private static CustomColormap fogColors = null;
/*   57 */   private static final CustomColorFader fogColorFader = new CustomColorFader();
/*   58 */   private static CustomColormap underwaterColors = null;
/*   59 */   private static final CustomColorFader underwaterColorFader = new CustomColorFader();
/*   60 */   private static CustomColormap underlavaColors = null;
/*   61 */   private static final CustomColorFader underlavaColorFader = new CustomColorFader();
/*   62 */   private static LightMapPack[] lightMapPacks = null;
/*   63 */   private static int lightmapMinDimensionId = 0;
/*   64 */   private static CustomColormap redstoneColors = null;
/*   65 */   private static CustomColormap xpOrbColors = null;
/*   66 */   private static int xpOrbTime = -1;
/*   67 */   private static CustomColormap durabilityColors = null;
/*   68 */   private static CustomColormap stemColors = null;
/*   69 */   private static CustomColormap stemMelonColors = null;
/*   70 */   private static CustomColormap stemPumpkinColors = null;
/*   71 */   private static CustomColormap myceliumParticleColors = null;
/*      */   private static boolean useDefaultGrassFoliageColors = true;
/*   73 */   private static int particleWaterColor = -1;
/*   74 */   private static int particlePortalColor = -1;
/*   75 */   private static int lilyPadColor = -1;
/*   76 */   private static int expBarTextColor = -1;
/*   77 */   private static int bossTextColor = -1;
/*   78 */   private static int signTextColor = -1;
/*   79 */   private static Vec3 fogColorNether = null;
/*   80 */   private static Vec3 fogColorEnd = null;
/*   81 */   private static Vec3 skyColorEnd = null;
/*   82 */   private static int[] spawnEggPrimaryColors = null;
/*   83 */   private static int[] spawnEggSecondaryColors = null;
/*   84 */   private static float[][] wolfCollarColors = (float[][])null;
/*   85 */   private static float[][] sheepColors = (float[][])null;
/*   86 */   private static int[] textColors = null;
/*   87 */   private static int[] mapColorsOriginal = null;
/*   88 */   private static int[] potionColors = null;
/*   89 */   private static final IBlockState BLOCK_STATE_DIRT = Blocks.dirt.getDefaultState();
/*   90 */   private static final IBlockState BLOCK_STATE_WATER = Blocks.water.getDefaultState();
/*   91 */   public static Random random = new Random();
/*   92 */   private static final IColorizer COLORIZER_GRASS = new IColorizer()
/*      */     {
/*      */       public int getColor(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos)
/*      */       {
/*   96 */         BiomeGenBase biomegenbase = CustomColors.getColorBiome(blockAccess, blockPos);
/*   97 */         return (CustomColors.swampGrassColors != null && biomegenbase == BiomeGenBase.swampland) ? CustomColors.swampGrassColors.getColor(biomegenbase, blockPos) : biomegenbase.getGrassColorAtPos(blockPos);
/*      */       }
/*      */       
/*      */       public boolean isColorConstant() {
/*  101 */         return false;
/*      */       }
/*      */     };
/*  104 */   private static final IColorizer COLORIZER_FOLIAGE = new IColorizer()
/*      */     {
/*      */       public int getColor(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos)
/*      */       {
/*  108 */         BiomeGenBase biomegenbase = CustomColors.getColorBiome(blockAccess, blockPos);
/*  109 */         return (CustomColors.swampFoliageColors != null && biomegenbase == BiomeGenBase.swampland) ? CustomColors.swampFoliageColors.getColor(biomegenbase, blockPos) : biomegenbase.getFoliageColorAtPos(blockPos);
/*      */       }
/*      */       
/*      */       public boolean isColorConstant() {
/*  113 */         return false;
/*      */       }
/*      */     };
/*  116 */   private static final IColorizer COLORIZER_FOLIAGE_PINE = new IColorizer()
/*      */     {
/*      */       public int getColor(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos)
/*      */       {
/*  120 */         return (CustomColors.foliagePineColors != null) ? CustomColors.foliagePineColors.getColor(blockAccess, blockPos) : ColorizerFoliage.getFoliageColorPine();
/*      */       }
/*      */       
/*      */       public boolean isColorConstant() {
/*  124 */         return (CustomColors.foliagePineColors == null);
/*      */       }
/*      */     };
/*  127 */   private static final IColorizer COLORIZER_FOLIAGE_BIRCH = new IColorizer()
/*      */     {
/*      */       public int getColor(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos)
/*      */       {
/*  131 */         return (CustomColors.foliageBirchColors != null) ? CustomColors.foliageBirchColors.getColor(blockAccess, blockPos) : ColorizerFoliage.getFoliageColorBirch();
/*      */       }
/*      */       
/*      */       public boolean isColorConstant() {
/*  135 */         return (CustomColors.foliageBirchColors == null);
/*      */       }
/*      */     };
/*  138 */   private static final IColorizer COLORIZER_WATER = new IColorizer()
/*      */     {
/*      */       public int getColor(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos)
/*      */       {
/*  142 */         BiomeGenBase biomegenbase = CustomColors.getColorBiome(blockAccess, blockPos);
/*  143 */         return (CustomColors.waterColors != null) ? CustomColors.waterColors.getColor(biomegenbase, blockPos) : (Reflector.ForgeBiome_getWaterColorMultiplier.exists() ? Reflector.callInt(biomegenbase, Reflector.ForgeBiome_getWaterColorMultiplier, new Object[0]) : biomegenbase.waterColorMultiplier);
/*      */       }
/*      */       
/*      */       public boolean isColorConstant() {
/*  147 */         return false;
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*      */   public static void update() {
/*  153 */     paletteFormatDefault = "vanilla";
/*  154 */     waterColors = null;
/*  155 */     foliageBirchColors = null;
/*  156 */     foliagePineColors = null;
/*  157 */     swampGrassColors = null;
/*  158 */     swampFoliageColors = null;
/*  159 */     skyColors = null;
/*  160 */     fogColors = null;
/*  161 */     underwaterColors = null;
/*  162 */     underlavaColors = null;
/*  163 */     redstoneColors = null;
/*  164 */     xpOrbColors = null;
/*  165 */     xpOrbTime = -1;
/*  166 */     durabilityColors = null;
/*  167 */     stemColors = null;
/*  168 */     myceliumParticleColors = null;
/*  169 */     lightMapPacks = null;
/*  170 */     particleWaterColor = -1;
/*  171 */     particlePortalColor = -1;
/*  172 */     lilyPadColor = -1;
/*  173 */     expBarTextColor = -1;
/*  174 */     bossTextColor = -1;
/*  175 */     signTextColor = -1;
/*  176 */     fogColorNether = null;
/*  177 */     fogColorEnd = null;
/*  178 */     skyColorEnd = null;
/*  179 */     colorsBlockColormaps = null;
/*  180 */     blockColormaps = (CustomColormap[][])null;
/*  181 */     useDefaultGrassFoliageColors = true;
/*  182 */     spawnEggPrimaryColors = null;
/*  183 */     spawnEggSecondaryColors = null;
/*  184 */     wolfCollarColors = (float[][])null;
/*  185 */     sheepColors = (float[][])null;
/*  186 */     textColors = null;
/*  187 */     setMapColors(mapColorsOriginal);
/*  188 */     potionColors = null;
/*  189 */     paletteFormatDefault = getValidProperty("mcpatcher/color.properties", "palette.format", CustomColormap.FORMAT_STRINGS, "vanilla");
/*  190 */     String s = "mcpatcher/colormap/";
/*  191 */     String[] astring = { "water.png", "watercolorX.png" };
/*  192 */     waterColors = getCustomColors(s, astring, 256, 256);
/*  193 */     updateUseDefaultGrassFoliageColors();
/*      */     
/*  195 */     if (Config.isCustomColors()) {
/*      */       
/*  197 */       String[] astring1 = { "pine.png", "pinecolor.png" };
/*  198 */       foliagePineColors = getCustomColors(s, astring1, 256, 256);
/*  199 */       String[] astring2 = { "birch.png", "birchcolor.png" };
/*  200 */       foliageBirchColors = getCustomColors(s, astring2, 256, 256);
/*  201 */       String[] astring3 = { "swampgrass.png", "swampgrasscolor.png" };
/*  202 */       swampGrassColors = getCustomColors(s, astring3, 256, 256);
/*  203 */       String[] astring4 = { "swampfoliage.png", "swampfoliagecolor.png" };
/*  204 */       swampFoliageColors = getCustomColors(s, astring4, 256, 256);
/*  205 */       String[] astring5 = { "sky0.png", "skycolor0.png" };
/*  206 */       skyColors = getCustomColors(s, astring5, 256, 256);
/*  207 */       String[] astring6 = { "fog0.png", "fogcolor0.png" };
/*  208 */       fogColors = getCustomColors(s, astring6, 256, 256);
/*  209 */       String[] astring7 = { "underwater.png", "underwatercolor.png" };
/*  210 */       underwaterColors = getCustomColors(s, astring7, 256, 256);
/*  211 */       String[] astring8 = { "underlava.png", "underlavacolor.png" };
/*  212 */       underlavaColors = getCustomColors(s, astring8, 256, 256);
/*  213 */       String[] astring9 = { "redstone.png", "redstonecolor.png" };
/*  214 */       redstoneColors = getCustomColors(s, astring9, 16, 1);
/*  215 */       xpOrbColors = getCustomColors(s + "xporb.png", -1, -1);
/*  216 */       durabilityColors = getCustomColors(s + "durability.png", -1, -1);
/*  217 */       String[] astring10 = { "stem.png", "stemcolor.png" };
/*  218 */       stemColors = getCustomColors(s, astring10, 8, 1);
/*  219 */       stemPumpkinColors = getCustomColors(s + "pumpkinstem.png", 8, 1);
/*  220 */       stemMelonColors = getCustomColors(s + "melonstem.png", 8, 1);
/*  221 */       String[] astring11 = { "myceliumparticle.png", "myceliumparticlecolor.png" };
/*  222 */       myceliumParticleColors = getCustomColors(s, astring11, -1, -1);
/*  223 */       Pair<LightMapPack[], Integer> pair = parseLightMapPacks();
/*  224 */       lightMapPacks = (LightMapPack[])pair.getLeft();
/*  225 */       lightmapMinDimensionId = ((Integer)pair.getRight()).intValue();
/*  226 */       readColorProperties("mcpatcher/color.properties");
/*  227 */       blockColormaps = readBlockColormaps(new String[] { s + "custom/", s + "blocks/" }, colorsBlockColormaps, 256, 256);
/*  228 */       updateUseDefaultGrassFoliageColors();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getValidProperty(String fileName, String key, String[] validValues, String valDef) {
/*      */     try {
/*  236 */       ResourceLocation resourcelocation = new ResourceLocation(fileName);
/*  237 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*      */       
/*  239 */       if (inputstream == null)
/*      */       {
/*  241 */         return valDef;
/*      */       }
/*      */ 
/*      */       
/*  245 */       PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  246 */       propertiesOrdered.load(inputstream);
/*  247 */       inputstream.close();
/*  248 */       String s = propertiesOrdered.getProperty(key);
/*      */       
/*  250 */       if (s == null)
/*      */       {
/*  252 */         return valDef;
/*      */       }
/*      */ 
/*      */       
/*  256 */       List<String> list = Arrays.asList(validValues);
/*      */       
/*  258 */       if (!list.contains(s)) {
/*      */         
/*  260 */         warn("Invalid value: " + key + "=" + s);
/*  261 */         warn("Expected values: " + Config.arrayToString((Object[])validValues));
/*  262 */         return valDef;
/*      */       } 
/*      */ 
/*      */       
/*  266 */       dbg(key + "=" + s);
/*  267 */       return s;
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  272 */     catch (FileNotFoundException var9) {
/*      */       
/*  274 */       return valDef;
/*      */     }
/*  276 */     catch (IOException ioexception) {
/*      */       
/*  278 */       ioexception.printStackTrace();
/*  279 */       return valDef;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static Pair<LightMapPack[], Integer> parseLightMapPacks() {
/*  285 */     String s = "mcpatcher/lightmap/world";
/*  286 */     String s1 = ".png";
/*  287 */     String[] astring = ResUtils.collectFiles(s, s1);
/*  288 */     Map<Integer, String> map = new HashMap<>();
/*      */     
/*  290 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  292 */       String s2 = astring[i];
/*  293 */       String s3 = StrUtils.removePrefixSuffix(s2, s, s1);
/*  294 */       int j = Config.parseInt(s3, -2147483648);
/*      */       
/*  296 */       if (j == Integer.MIN_VALUE) {
/*      */         
/*  298 */         warn("Invalid dimension ID: " + s3 + ", path: " + s2);
/*      */       }
/*      */       else {
/*      */         
/*  302 */         map.put(Integer.valueOf(j), s2);
/*      */       } 
/*      */     } 
/*      */     
/*  306 */     Set<Integer> set = map.keySet();
/*  307 */     Integer[] ainteger = set.<Integer>toArray(new Integer[0]);
/*  308 */     Arrays.sort((Object[])ainteger);
/*      */     
/*  310 */     if (ainteger.length <= 0)
/*      */     {
/*  312 */       return (Pair<LightMapPack[], Integer>)new ImmutablePair(null, Integer.valueOf(0));
/*      */     }
/*      */ 
/*      */     
/*  316 */     int j1 = ainteger[0].intValue();
/*  317 */     int k1 = ainteger[ainteger.length - 1].intValue();
/*  318 */     int k = k1 - j1 + 1;
/*  319 */     CustomColormap[] acustomcolormap = new CustomColormap[k];
/*      */     
/*  321 */     for (int l = 0; l < ainteger.length; l++) {
/*      */       
/*  323 */       Integer integer = ainteger[l];
/*  324 */       String s4 = map.get(integer);
/*  325 */       CustomColormap customcolormap = getCustomColors(s4, -1, -1);
/*      */       
/*  327 */       if (customcolormap != null)
/*      */       {
/*  329 */         if (customcolormap.getWidth() < 16) {
/*      */           
/*  331 */           warn("Invalid lightmap width: " + customcolormap.getWidth() + ", path: " + s4);
/*      */         }
/*      */         else {
/*      */           
/*  335 */           int i1 = integer.intValue() - j1;
/*  336 */           acustomcolormap[i1] = customcolormap;
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  341 */     LightMapPack[] alightmappack = new LightMapPack[acustomcolormap.length];
/*      */     
/*  343 */     for (int l1 = 0; l1 < acustomcolormap.length; l1++) {
/*      */       
/*  345 */       CustomColormap customcolormap3 = acustomcolormap[l1];
/*      */       
/*  347 */       if (customcolormap3 != null) {
/*      */         
/*  349 */         String s5 = customcolormap3.name;
/*  350 */         String s6 = customcolormap3.basePath;
/*  351 */         CustomColormap customcolormap1 = getCustomColors(s6 + "/" + s5 + "_rain.png", -1, -1);
/*  352 */         CustomColormap customcolormap2 = getCustomColors(s6 + "/" + s5 + "_thunder.png", -1, -1);
/*  353 */         LightMap lightmap = new LightMap(customcolormap3);
/*  354 */         LightMap lightmap1 = (customcolormap1 != null) ? new LightMap(customcolormap1) : null;
/*  355 */         LightMap lightmap2 = (customcolormap2 != null) ? new LightMap(customcolormap2) : null;
/*  356 */         LightMapPack lightmappack = new LightMapPack(lightmap, lightmap1, lightmap2);
/*  357 */         alightmappack[l1] = lightmappack;
/*      */       } 
/*      */     } 
/*      */     
/*  361 */     return (Pair<LightMapPack[], Integer>)new ImmutablePair(alightmappack, Integer.valueOf(j1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getTextureHeight(String path, int defHeight) {
/*      */     try {
/*  369 */       InputStream inputstream = Config.getResourceStream(new ResourceLocation(path));
/*      */       
/*  371 */       if (inputstream == null)
/*      */       {
/*  373 */         return defHeight;
/*      */       }
/*      */ 
/*      */       
/*  377 */       BufferedImage bufferedimage = ImageIO.read(inputstream);
/*  378 */       inputstream.close();
/*  379 */       return (bufferedimage == null) ? defHeight : bufferedimage.getHeight();
/*      */     
/*      */     }
/*  382 */     catch (IOException var4) {
/*      */       
/*  384 */       return defHeight;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readColorProperties(String fileName) {
/*      */     try {
/*  392 */       ResourceLocation resourcelocation = new ResourceLocation(fileName);
/*  393 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*      */       
/*  395 */       if (inputstream == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  400 */       dbg("Loading " + fileName);
/*  401 */       PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  402 */       propertiesOrdered.load(inputstream);
/*  403 */       inputstream.close();
/*  404 */       particleWaterColor = readColor((Properties)propertiesOrdered, new String[] { "particle.water", "drop.water" });
/*  405 */       particlePortalColor = readColor((Properties)propertiesOrdered, "particle.portal");
/*  406 */       lilyPadColor = readColor((Properties)propertiesOrdered, "lilypad");
/*  407 */       expBarTextColor = readColor((Properties)propertiesOrdered, "text.xpbar");
/*  408 */       bossTextColor = readColor((Properties)propertiesOrdered, "text.boss");
/*  409 */       signTextColor = readColor((Properties)propertiesOrdered, "text.sign");
/*  410 */       fogColorNether = readColorVec3((Properties)propertiesOrdered, "fog.nether");
/*  411 */       fogColorEnd = readColorVec3((Properties)propertiesOrdered, "fog.end");
/*  412 */       skyColorEnd = readColorVec3((Properties)propertiesOrdered, "sky.end");
/*  413 */       colorsBlockColormaps = readCustomColormaps((Properties)propertiesOrdered, fileName);
/*  414 */       spawnEggPrimaryColors = readSpawnEggColors((Properties)propertiesOrdered, fileName, "egg.shell.", "Spawn egg shell");
/*  415 */       spawnEggSecondaryColors = readSpawnEggColors((Properties)propertiesOrdered, fileName, "egg.spots.", "Spawn egg spot");
/*  416 */       wolfCollarColors = readDyeColors((Properties)propertiesOrdered, fileName, "collar.", "Wolf collar");
/*  417 */       sheepColors = readDyeColors((Properties)propertiesOrdered, fileName, "sheep.", "Sheep");
/*  418 */       textColors = readTextColors((Properties)propertiesOrdered, fileName, "text.code.", "Text");
/*  419 */       int[] aint = readMapColors((Properties)propertiesOrdered, fileName, "map.", "Map");
/*      */       
/*  421 */       if (aint != null) {
/*      */         
/*  423 */         if (mapColorsOriginal == null)
/*      */         {
/*  425 */           mapColorsOriginal = getMapColors();
/*      */         }
/*      */         
/*  428 */         setMapColors(aint);
/*      */       } 
/*      */       
/*  431 */       potionColors = readPotionColors((Properties)propertiesOrdered, fileName, "potion.", "Potion");
/*  432 */       xpOrbTime = Config.parseInt(propertiesOrdered.getProperty("xporb.time"), -1);
/*      */     }
/*  434 */     catch (FileNotFoundException var5) {
/*      */       
/*      */       return;
/*      */     }
/*  438 */     catch (IOException ioexception) {
/*      */       
/*  440 */       ioexception.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static CustomColormap[] readCustomColormaps(Properties props, String fileName) {
/*  446 */     List<CustomColormap> list = new ArrayList();
/*  447 */     String s = "palette.block.";
/*  448 */     Map<Object, Object> map = new HashMap<>();
/*      */     
/*  450 */     for (Object e : props.keySet()) {
/*      */       
/*  452 */       String s1 = (String)e;
/*  453 */       String s2 = props.getProperty(s1);
/*      */       
/*  455 */       if (s1.startsWith(s))
/*      */       {
/*  457 */         map.put(s1, s2);
/*      */       }
/*      */     } 
/*      */     
/*  461 */     String[] astring = (String[])map.keySet().toArray((Object[])new String[0]);
/*      */     
/*  463 */     for (int j = 0; j < astring.length; j++) {
/*      */       
/*  465 */       String s6 = astring[j];
/*  466 */       String s3 = props.getProperty(s6);
/*  467 */       dbg("Block palette: " + s6 + " = " + s3);
/*  468 */       String s4 = s6.substring(s.length());
/*  469 */       String s5 = TextureUtils.getBasePath(fileName);
/*  470 */       s4 = TextureUtils.fixResourcePath(s4, s5);
/*  471 */       CustomColormap customcolormap = getCustomColors(s4, 256, 256);
/*      */       
/*  473 */       if (customcolormap == null) {
/*      */         
/*  475 */         warn("Colormap not found: " + s4);
/*      */       }
/*      */       else {
/*      */         
/*  479 */         ConnectedParser connectedparser = new ConnectedParser("CustomColors");
/*  480 */         MatchBlock[] amatchblock = connectedparser.parseMatchBlocks(s3);
/*      */         
/*  482 */         if (amatchblock != null && amatchblock.length > 0) {
/*      */           
/*  484 */           for (int i = 0; i < amatchblock.length; i++) {
/*      */             
/*  486 */             MatchBlock matchblock = amatchblock[i];
/*  487 */             customcolormap.addMatchBlock(matchblock);
/*      */           } 
/*      */           
/*  490 */           list.add(customcolormap);
/*      */         }
/*      */         else {
/*      */           
/*  494 */           warn("Invalid match blocks: " + s3);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  499 */     if (list.size() <= 0)
/*      */     {
/*  501 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  505 */     CustomColormap[] acustomcolormap = list.<CustomColormap>toArray(new CustomColormap[0]);
/*  506 */     return acustomcolormap;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static CustomColormap[][] readBlockColormaps(String[] basePaths, CustomColormap[] basePalettes, int width, int height) {
/*  512 */     String[] astring = ResUtils.collectFiles(basePaths, new String[] { ".properties" });
/*  513 */     Arrays.sort((Object[])astring);
/*  514 */     List list = new ArrayList();
/*      */     
/*  516 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  518 */       String s = astring[i];
/*  519 */       dbg("Block colormap: " + s);
/*      */ 
/*      */       
/*      */       try {
/*  523 */         ResourceLocation resourcelocation = new ResourceLocation("minecraft", s);
/*  524 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*      */         
/*  526 */         if (inputstream == null)
/*      */         {
/*  528 */           warn("File not found: " + s);
/*      */         }
/*      */         else
/*      */         {
/*  532 */           PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  533 */           propertiesOrdered.load(inputstream);
/*  534 */           inputstream.close();
/*  535 */           CustomColormap customcolormap = new CustomColormap((Properties)propertiesOrdered, s, width, height, paletteFormatDefault);
/*      */           
/*  537 */           if (customcolormap.isValid(s) && customcolormap.isValidMatchBlocks(s))
/*      */           {
/*  539 */             addToBlockList(customcolormap, list);
/*      */           }
/*      */         }
/*      */       
/*  543 */       } catch (FileNotFoundException var12) {
/*      */         
/*  545 */         warn("File not found: " + s);
/*      */       }
/*  547 */       catch (Exception exception) {
/*      */         
/*  549 */         exception.printStackTrace();
/*      */       } 
/*      */     } 
/*      */     
/*  553 */     if (basePalettes != null)
/*      */     {
/*  555 */       for (int j = 0; j < basePalettes.length; j++) {
/*      */         
/*  557 */         CustomColormap customcolormap1 = basePalettes[j];
/*  558 */         addToBlockList(customcolormap1, list);
/*      */       } 
/*      */     }
/*      */     
/*  562 */     if (list.size() <= 0)
/*      */     {
/*  564 */       return (CustomColormap[][])null;
/*      */     }
/*      */ 
/*      */     
/*  568 */     CustomColormap[][] acustomcolormap = blockListToArray(list);
/*  569 */     return acustomcolormap;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void addToBlockList(CustomColormap cm, List blockList) {
/*  575 */     int[] aint = cm.getMatchBlockIds();
/*      */     
/*  577 */     if (aint != null && aint.length > 0) {
/*      */       
/*  579 */       for (int i = 0; i < aint.length; i++) {
/*      */         
/*  581 */         int j = aint[i];
/*      */         
/*  583 */         if (j < 0)
/*      */         {
/*  585 */           warn("Invalid block ID: " + j);
/*      */         }
/*      */         else
/*      */         {
/*  589 */           addToList(cm, blockList, j);
/*      */         }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/*  595 */       warn("No match blocks: " + Config.arrayToString(aint));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToList(CustomColormap cm, List<List> lists, int id) {
/*  601 */     while (id >= lists.size())
/*      */     {
/*  603 */       lists.add(null);
/*      */     }
/*      */     
/*  606 */     List<CustomColormap> list = lists.get(id);
/*      */     
/*  608 */     if (list == null)
/*      */     {
/*  610 */       list = new ArrayList();
/*      */     }
/*      */ 
/*      */     
/*  614 */     list.add(cm);
/*      */   }
/*      */ 
/*      */   
/*      */   private static CustomColormap[][] blockListToArray(List<List> lists) {
/*  619 */     CustomColormap[][] acustomcolormap = new CustomColormap[lists.size()][];
/*      */     
/*  621 */     for (int i = 0; i < lists.size(); i++) {
/*      */       
/*  623 */       List list = lists.get(i);
/*      */       
/*  625 */       if (list != null) {
/*      */         
/*  627 */         CustomColormap[] acustomcolormap1 = (CustomColormap[])list.toArray((Object[])new CustomColormap[0]);
/*  628 */         acustomcolormap[i] = acustomcolormap1;
/*      */       } 
/*      */     } 
/*      */     
/*  632 */     return acustomcolormap;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int readColor(Properties props, String[] names) {
/*  637 */     for (int i = 0; i < names.length; i++) {
/*      */       
/*  639 */       String s = names[i];
/*  640 */       int j = readColor(props, s);
/*      */       
/*  642 */       if (j >= 0)
/*      */       {
/*  644 */         return j;
/*      */       }
/*      */     } 
/*      */     
/*  648 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int readColor(Properties props, String name) {
/*  653 */     String s = props.getProperty(name);
/*      */     
/*  655 */     if (s == null)
/*      */     {
/*  657 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*  661 */     s = s.trim();
/*  662 */     int i = parseColor(s);
/*      */     
/*  664 */     if (i < 0) {
/*      */       
/*  666 */       warn("Invalid color: " + name + " = " + s);
/*  667 */       return i;
/*      */     } 
/*      */ 
/*      */     
/*  671 */     dbg(name + " = " + s);
/*  672 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseColor(String str) {
/*  679 */     if (str == null)
/*      */     {
/*  681 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*  685 */     str = str.trim();
/*      */ 
/*      */     
/*      */     try {
/*  689 */       int i = Integer.parseInt(str, 16) & 0xFFFFFF;
/*  690 */       return i;
/*      */     }
/*  692 */     catch (NumberFormatException var2) {
/*      */       
/*  694 */       return -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Vec3 readColorVec3(Properties props, String name) {
/*  701 */     int i = readColor(props, name);
/*      */     
/*  703 */     if (i < 0)
/*      */     {
/*  705 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  709 */     int j = i >> 16 & 0xFF;
/*  710 */     int k = i >> 8 & 0xFF;
/*  711 */     int l = i & 0xFF;
/*  712 */     float f = j / 255.0F;
/*  713 */     float f1 = k / 255.0F;
/*  714 */     float f2 = l / 255.0F;
/*  715 */     return new Vec3(f, f1, f2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static CustomColormap getCustomColors(String basePath, String[] paths, int width, int height) {
/*  721 */     for (int i = 0; i < paths.length; i++) {
/*      */       
/*  723 */       String s = paths[i];
/*  724 */       s = basePath + s;
/*  725 */       CustomColormap customcolormap = getCustomColors(s, width, height);
/*      */       
/*  727 */       if (customcolormap != null)
/*      */       {
/*  729 */         return customcolormap;
/*      */       }
/*      */     } 
/*      */     
/*  733 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static CustomColormap getCustomColors(String pathImage, int width, int height) {
/*      */     try {
/*  740 */       ResourceLocation resourcelocation = new ResourceLocation(pathImage);
/*      */       
/*  742 */       if (!Config.hasResource(resourcelocation))
/*      */       {
/*  744 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  748 */       dbg("Colormap " + pathImage);
/*  749 */       PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  750 */       String s = StrUtils.replaceSuffix(pathImage, ".png", ".properties");
/*  751 */       ResourceLocation resourcelocation1 = new ResourceLocation(s);
/*      */       
/*  753 */       if (Config.hasResource(resourcelocation1)) {
/*      */         
/*  755 */         InputStream inputstream = Config.getResourceStream(resourcelocation1);
/*  756 */         propertiesOrdered.load(inputstream);
/*  757 */         inputstream.close();
/*  758 */         dbg("Colormap properties: " + s);
/*      */       }
/*      */       else {
/*      */         
/*  762 */         propertiesOrdered.put("format", paletteFormatDefault);
/*  763 */         propertiesOrdered.put("source", pathImage);
/*  764 */         s = pathImage;
/*      */       } 
/*      */       
/*  767 */       CustomColormap customcolormap = new CustomColormap((Properties)propertiesOrdered, s, width, height, paletteFormatDefault);
/*  768 */       return !customcolormap.isValid(s) ? null : customcolormap;
/*      */     
/*      */     }
/*  771 */     catch (Exception exception) {
/*      */       
/*  773 */       exception.printStackTrace();
/*  774 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateUseDefaultGrassFoliageColors() {
/*  780 */     useDefaultGrassFoliageColors = (foliageBirchColors == null && foliagePineColors == null && swampGrassColors == null && swampFoliageColors == null && Config.isSwampColors() && Config.isSmoothBiomes());
/*      */   }
/*      */   
/*      */   public static int getColorMultiplier(BakedQuad quad, IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos, RenderEnv renderEnv) {
/*      */     IColorizer customcolors$icolorizer;
/*  785 */     Block block = blockState.getBlock();
/*  786 */     IBlockState iblockstate = renderEnv.getBlockState();
/*      */     
/*  788 */     if (blockColormaps != null) {
/*      */       
/*  790 */       if (!quad.hasTintIndex()) {
/*      */         
/*  792 */         if (block == Blocks.grass)
/*      */         {
/*  794 */           iblockstate = BLOCK_STATE_DIRT;
/*      */         }
/*      */         
/*  797 */         if (block == Blocks.redstone_wire)
/*      */         {
/*  799 */           return -1;
/*      */         }
/*      */       } 
/*      */       
/*  803 */       if (block == Blocks.double_plant && renderEnv.getMetadata() >= 8) {
/*      */         
/*  805 */         blockPos = blockPos.down();
/*  806 */         iblockstate = blockAccess.getBlockState(blockPos);
/*      */       } 
/*      */       
/*  809 */       CustomColormap customcolormap = getBlockColormap(iblockstate);
/*      */       
/*  811 */       if (customcolormap != null) {
/*      */         
/*  813 */         if (Config.isSmoothBiomes() && !customcolormap.isColorConstant())
/*      */         {
/*  815 */           return getSmoothColorMultiplier(blockState, blockAccess, blockPos, customcolormap, renderEnv.getColorizerBlockPosM());
/*      */         }
/*      */         
/*  818 */         return customcolormap.getColor(blockAccess, blockPos);
/*      */       } 
/*      */     } 
/*      */     
/*  822 */     if (!quad.hasTintIndex())
/*      */     {
/*  824 */       return -1;
/*      */     }
/*  826 */     if (block == Blocks.waterlily)
/*      */     {
/*  828 */       return getLilypadColorMultiplier(blockAccess, blockPos);
/*      */     }
/*  830 */     if (block == Blocks.redstone_wire)
/*      */     {
/*  832 */       return getRedstoneColor(renderEnv.getBlockState());
/*      */     }
/*  834 */     if (block instanceof net.minecraft.block.BlockStem)
/*      */     {
/*  836 */       return getStemColorMultiplier(block, blockAccess, blockPos, renderEnv);
/*      */     }
/*  838 */     if (useDefaultGrassFoliageColors)
/*      */     {
/*  840 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*  844 */     int i = renderEnv.getMetadata();
/*      */ 
/*      */     
/*  847 */     if (block != Blocks.grass && block != Blocks.tallgrass && block != Blocks.double_plant) {
/*      */       
/*  849 */       if (block == Blocks.double_plant) {
/*      */         
/*  851 */         customcolors$icolorizer = COLORIZER_GRASS;
/*      */         
/*  853 */         if (i >= 8)
/*      */         {
/*  855 */           blockPos = blockPos.down();
/*      */         }
/*      */       }
/*  858 */       else if (block == Blocks.leaves) {
/*      */         
/*  860 */         switch (i & 0x3) {
/*      */           
/*      */           case 0:
/*  863 */             customcolors$icolorizer = COLORIZER_FOLIAGE;
/*      */             break;
/*      */           
/*      */           case 1:
/*  867 */             customcolors$icolorizer = COLORIZER_FOLIAGE_PINE;
/*      */             break;
/*      */           
/*      */           case 2:
/*  871 */             customcolors$icolorizer = COLORIZER_FOLIAGE_BIRCH;
/*      */             break;
/*      */           
/*      */           default:
/*  875 */             customcolors$icolorizer = COLORIZER_FOLIAGE;
/*      */             break;
/*      */         } 
/*  878 */       } else if (block == Blocks.leaves2) {
/*      */         
/*  880 */         customcolors$icolorizer = COLORIZER_FOLIAGE;
/*      */       }
/*      */       else {
/*      */         
/*  884 */         if (block != Blocks.vine)
/*      */         {
/*  886 */           return -1;
/*      */         }
/*      */         
/*  889 */         customcolors$icolorizer = COLORIZER_FOLIAGE;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  894 */       customcolors$icolorizer = COLORIZER_GRASS;
/*      */     } 
/*      */     
/*  897 */     return (Config.isSmoothBiomes() && !customcolors$icolorizer.isColorConstant()) ? getSmoothColorMultiplier(blockState, blockAccess, blockPos, customcolors$icolorizer, renderEnv.getColorizerBlockPosM()) : customcolors$icolorizer.getColor(iblockstate, blockAccess, blockPos);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static BiomeGenBase getColorBiome(IBlockAccess blockAccess, BlockPos blockPos) {
/*  903 */     BiomeGenBase biomegenbase = blockAccess.getBiomeGenForCoords(blockPos);
/*      */     
/*  905 */     if (biomegenbase == BiomeGenBase.swampland && !Config.isSwampColors())
/*      */     {
/*  907 */       biomegenbase = BiomeGenBase.plains;
/*      */     }
/*      */     
/*  910 */     return biomegenbase;
/*      */   }
/*      */ 
/*      */   
/*      */   private static CustomColormap getBlockColormap(IBlockState blockState) {
/*  915 */     if (blockColormaps == null)
/*      */     {
/*  917 */       return null;
/*      */     }
/*  919 */     if (!(blockState instanceof BlockStateBase))
/*      */     {
/*  921 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  925 */     BlockStateBase blockstatebase = (BlockStateBase)blockState;
/*  926 */     int i = blockstatebase.getBlockId();
/*      */     
/*  928 */     if (i >= 0 && i < blockColormaps.length) {
/*      */       
/*  930 */       CustomColormap[] acustomcolormap = blockColormaps[i];
/*      */       
/*  932 */       if (acustomcolormap == null)
/*      */       {
/*  934 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  938 */       for (int j = 0; j < acustomcolormap.length; j++) {
/*      */         
/*  940 */         CustomColormap customcolormap = acustomcolormap[j];
/*      */         
/*  942 */         if (customcolormap.matchesBlock(blockstatebase))
/*      */         {
/*  944 */           return customcolormap;
/*      */         }
/*      */       } 
/*      */       
/*  948 */       return null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  953 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getSmoothColorMultiplier(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos, IColorizer colorizer, BlockPosM blockPosM) {
/*  960 */     int i = 0;
/*  961 */     int j = 0;
/*  962 */     int k = 0;
/*  963 */     int l = blockPos.getX();
/*  964 */     int i1 = blockPos.getY();
/*  965 */     int j1 = blockPos.getZ();
/*  966 */     BlockPosM blockposm = blockPosM;
/*      */     
/*  968 */     for (int k1 = l - 1; k1 <= l + 1; k1++) {
/*      */       
/*  970 */       for (int l1 = j1 - 1; l1 <= j1 + 1; l1++) {
/*      */         
/*  972 */         blockposm.setXyz(k1, i1, l1);
/*  973 */         int i2 = colorizer.getColor(blockState, blockAccess, blockposm);
/*  974 */         i += i2 >> 16 & 0xFF;
/*  975 */         j += i2 >> 8 & 0xFF;
/*  976 */         k += i2 & 0xFF;
/*      */       } 
/*      */     } 
/*      */     
/*  980 */     int j2 = i / 9;
/*  981 */     int k2 = j / 9;
/*  982 */     int l2 = k / 9;
/*  983 */     return j2 << 16 | k2 << 8 | l2;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getFluidColor(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, RenderEnv renderEnv) {
/*  988 */     Block block = blockState.getBlock();
/*  989 */     IColorizer customcolors$icolorizer = getBlockColormap(blockState);
/*      */     
/*  991 */     if (customcolors$icolorizer == null && blockState.getBlock().getMaterial() == Material.water)
/*      */     {
/*  993 */       customcolors$icolorizer = COLORIZER_WATER;
/*      */     }
/*      */     
/*  996 */     return (customcolors$icolorizer == null) ? block.colorMultiplier(blockAccess, blockPos, 0) : ((Config.isSmoothBiomes() && !customcolors$icolorizer.isColorConstant()) ? getSmoothColorMultiplier(blockState, blockAccess, blockPos, customcolors$icolorizer, renderEnv.getColorizerBlockPosM()) : customcolors$icolorizer.getColor(blockState, blockAccess, blockPos));
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updatePortalFX(EntityFX fx) {
/* 1001 */     if (particlePortalColor >= 0) {
/*      */       
/* 1003 */       int i = particlePortalColor;
/* 1004 */       int j = i >> 16 & 0xFF;
/* 1005 */       int k = i >> 8 & 0xFF;
/* 1006 */       int l = i & 0xFF;
/* 1007 */       float f = j / 255.0F;
/* 1008 */       float f1 = k / 255.0F;
/* 1009 */       float f2 = l / 255.0F;
/* 1010 */       fx.setRBGColorF(f, f1, f2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateMyceliumFX(EntityFX fx) {
/* 1016 */     if (myceliumParticleColors != null) {
/*      */       
/* 1018 */       int i = myceliumParticleColors.getColorRandom();
/* 1019 */       int j = i >> 16 & 0xFF;
/* 1020 */       int k = i >> 8 & 0xFF;
/* 1021 */       int l = i & 0xFF;
/* 1022 */       float f = j / 255.0F;
/* 1023 */       float f1 = k / 255.0F;
/* 1024 */       float f2 = l / 255.0F;
/* 1025 */       fx.setRBGColorF(f, f1, f2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getRedstoneColor(IBlockState blockState) {
/* 1031 */     if (redstoneColors == null)
/*      */     {
/* 1033 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 1037 */     int i = getRedstoneLevel(blockState, 15);
/* 1038 */     int j = redstoneColors.getColor(i);
/* 1039 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateReddustFX(EntityFX fx, IBlockAccess blockAccess, double x, double y, double z) {
/* 1045 */     if (redstoneColors != null) {
/*      */       
/* 1047 */       IBlockState iblockstate = blockAccess.getBlockState(new BlockPos(x, y, z));
/* 1048 */       int i = getRedstoneLevel(iblockstate, 15);
/* 1049 */       int j = redstoneColors.getColor(i);
/* 1050 */       int k = j >> 16 & 0xFF;
/* 1051 */       int l = j >> 8 & 0xFF;
/* 1052 */       int i1 = j & 0xFF;
/* 1053 */       float f = k / 255.0F;
/* 1054 */       float f1 = l / 255.0F;
/* 1055 */       float f2 = i1 / 255.0F;
/* 1056 */       fx.setRBGColorF(f, f1, f2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getRedstoneLevel(IBlockState state, int def) {
/* 1062 */     Block block = state.getBlock();
/*      */     
/* 1064 */     if (!(block instanceof BlockRedstoneWire))
/*      */     {
/* 1066 */       return def;
/*      */     }
/*      */ 
/*      */     
/* 1070 */     Object object = state.getValue((IProperty)BlockRedstoneWire.POWER);
/*      */     
/* 1072 */     if (!(object instanceof Integer))
/*      */     {
/* 1074 */       return def;
/*      */     }
/*      */ 
/*      */     
/* 1078 */     Integer integer = (Integer)object;
/* 1079 */     return integer.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float getXpOrbTimer(float timer) {
/* 1086 */     if (xpOrbTime <= 0)
/*      */     {
/* 1088 */       return timer;
/*      */     }
/*      */ 
/*      */     
/* 1092 */     float f = 628.0F / xpOrbTime;
/* 1093 */     return timer * f;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getXpOrbColor(float timer) {
/* 1099 */     if (xpOrbColors == null)
/*      */     {
/* 1101 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 1105 */     int i = (int)Math.round(((MathHelper.sin(timer) + 1.0F) * (xpOrbColors.getLength() - 1)) / 2.0D);
/* 1106 */     int j = xpOrbColors.getColor(i);
/* 1107 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getDurabilityColor(int dur255) {
/* 1113 */     if (durabilityColors == null)
/*      */     {
/* 1115 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 1119 */     int i = dur255 * durabilityColors.getLength() / 255;
/* 1120 */     int j = durabilityColors.getColor(i);
/* 1121 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateWaterFX(EntityFX fx, IBlockAccess blockAccess, double x, double y, double z, RenderEnv renderEnv) {
/* 1127 */     if (waterColors != null || blockColormaps != null || particleWaterColor >= 0) {
/*      */       
/* 1129 */       BlockPos blockpos = new BlockPos(x, y, z);
/* 1130 */       renderEnv.reset(BLOCK_STATE_WATER, blockpos);
/* 1131 */       int i = getFluidColor(blockAccess, BLOCK_STATE_WATER, blockpos, renderEnv);
/* 1132 */       int j = i >> 16 & 0xFF;
/* 1133 */       int k = i >> 8 & 0xFF;
/* 1134 */       int l = i & 0xFF;
/* 1135 */       float f = j / 255.0F;
/* 1136 */       float f1 = k / 255.0F;
/* 1137 */       float f2 = l / 255.0F;
/*      */       
/* 1139 */       if (particleWaterColor >= 0) {
/*      */         
/* 1141 */         int i1 = particleWaterColor >> 16 & 0xFF;
/* 1142 */         int j1 = particleWaterColor >> 8 & 0xFF;
/* 1143 */         int k1 = particleWaterColor & 0xFF;
/* 1144 */         f *= i1 / 255.0F;
/* 1145 */         f1 *= j1 / 255.0F;
/* 1146 */         f2 *= k1 / 255.0F;
/*      */       } 
/*      */       
/* 1149 */       fx.setRBGColorF(f, f1, f2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getLilypadColorMultiplier(IBlockAccess blockAccess, BlockPos blockPos) {
/* 1155 */     return (lilyPadColor < 0) ? Blocks.waterlily.colorMultiplier(blockAccess, blockPos) : lilyPadColor;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Vec3 getFogColorNether(Vec3 col) {
/* 1160 */     return (fogColorNether == null) ? col : fogColorNether;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Vec3 getFogColorEnd(Vec3 col) {
/* 1165 */     return (fogColorEnd == null) ? col : fogColorEnd;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Vec3 getSkyColorEnd(Vec3 col) {
/* 1170 */     return (skyColorEnd == null) ? col : skyColorEnd;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Vec3 getSkyColor(Vec3 skyColor3d, IBlockAccess blockAccess, double x, double y, double z) {
/* 1175 */     if (skyColors == null)
/*      */     {
/* 1177 */       return skyColor3d;
/*      */     }
/*      */ 
/*      */     
/* 1181 */     int i = skyColors.getColorSmooth(blockAccess, x, y, z, 3);
/* 1182 */     int j = i >> 16 & 0xFF;
/* 1183 */     int k = i >> 8 & 0xFF;
/* 1184 */     int l = i & 0xFF;
/* 1185 */     float f = j / 255.0F;
/* 1186 */     float f1 = k / 255.0F;
/* 1187 */     float f2 = l / 255.0F;
/* 1188 */     float f3 = (float)skyColor3d.xCoord / 0.5F;
/* 1189 */     float f4 = (float)skyColor3d.yCoord / 0.66275F;
/* 1190 */     float f5 = (float)skyColor3d.zCoord;
/* 1191 */     f *= f3;
/* 1192 */     f1 *= f4;
/* 1193 */     f2 *= f5;
/* 1194 */     Vec3 vec3 = skyColorFader.getColor(f, f1, f2);
/* 1195 */     return vec3;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Vec3 getFogColor(Vec3 fogColor3d, IBlockAccess blockAccess, double x, double y, double z) {
/* 1201 */     if (fogColors == null)
/*      */     {
/* 1203 */       return fogColor3d;
/*      */     }
/*      */ 
/*      */     
/* 1207 */     int i = fogColors.getColorSmooth(blockAccess, x, y, z, 3);
/* 1208 */     int j = i >> 16 & 0xFF;
/* 1209 */     int k = i >> 8 & 0xFF;
/* 1210 */     int l = i & 0xFF;
/* 1211 */     float f = j / 255.0F;
/* 1212 */     float f1 = k / 255.0F;
/* 1213 */     float f2 = l / 255.0F;
/* 1214 */     float f3 = (float)fogColor3d.xCoord / 0.753F;
/* 1215 */     float f4 = (float)fogColor3d.yCoord / 0.8471F;
/* 1216 */     float f5 = (float)fogColor3d.zCoord;
/* 1217 */     f *= f3;
/* 1218 */     f1 *= f4;
/* 1219 */     f2 *= f5;
/* 1220 */     Vec3 vec3 = fogColorFader.getColor(f, f1, f2);
/* 1221 */     return vec3;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vec3 getUnderwaterColor(IBlockAccess blockAccess, double x, double y, double z) {
/* 1227 */     return getUnderFluidColor(blockAccess, x, y, z, underwaterColors, underwaterColorFader);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Vec3 getUnderlavaColor(IBlockAccess blockAccess, double x, double y, double z) {
/* 1232 */     return getUnderFluidColor(blockAccess, x, y, z, underlavaColors, underlavaColorFader);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Vec3 getUnderFluidColor(IBlockAccess blockAccess, double x, double y, double z, CustomColormap underFluidColors, CustomColorFader underFluidColorFader) {
/* 1237 */     if (underFluidColors == null)
/*      */     {
/* 1239 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1243 */     int i = underFluidColors.getColorSmooth(blockAccess, x, y, z, 3);
/* 1244 */     int j = i >> 16 & 0xFF;
/* 1245 */     int k = i >> 8 & 0xFF;
/* 1246 */     int l = i & 0xFF;
/* 1247 */     float f = j / 255.0F;
/* 1248 */     float f1 = k / 255.0F;
/* 1249 */     float f2 = l / 255.0F;
/* 1250 */     Vec3 vec3 = underFluidColorFader.getColor(f, f1, f2);
/* 1251 */     return vec3;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getStemColorMultiplier(Block blockStem, IBlockAccess blockAccess, BlockPos blockPos, RenderEnv renderEnv) {
/* 1257 */     CustomColormap customcolormap = stemColors;
/*      */     
/* 1259 */     if (blockStem == Blocks.pumpkin_stem && stemPumpkinColors != null)
/*      */     {
/* 1261 */       customcolormap = stemPumpkinColors;
/*      */     }
/*      */     
/* 1264 */     if (blockStem == Blocks.melon_stem && stemMelonColors != null)
/*      */     {
/* 1266 */       customcolormap = stemMelonColors;
/*      */     }
/*      */     
/* 1269 */     if (customcolormap == null)
/*      */     {
/* 1271 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 1275 */     int i = renderEnv.getMetadata();
/* 1276 */     return customcolormap.getColor(i);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean updateLightmap(World world, float torchFlickerX, int[] lmColors, boolean nightvision, float partialTicks) {
/* 1282 */     if (world == null)
/*      */     {
/* 1284 */       return false;
/*      */     }
/* 1286 */     if (lightMapPacks == null)
/*      */     {
/* 1288 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1292 */     int i = world.provider.getDimensionId();
/* 1293 */     int j = i - lightmapMinDimensionId;
/*      */     
/* 1295 */     if (j >= 0 && j < lightMapPacks.length) {
/*      */       
/* 1297 */       LightMapPack lightmappack = lightMapPacks[j];
/* 1298 */       return (lightmappack == null) ? false : lightmappack.updateLightmap(world, torchFlickerX, lmColors, nightvision, partialTicks);
/*      */     } 
/*      */ 
/*      */     
/* 1302 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vec3 getWorldFogColor(Vec3 fogVec, World world, Entity renderViewEntity, float partialTicks) {
/*      */     Minecraft minecraft;
/* 1309 */     int i = world.provider.getDimensionId();
/*      */     
/* 1311 */     switch (i) {
/*      */       
/*      */       case -1:
/* 1314 */         fogVec = getFogColorNether(fogVec);
/*      */         break;
/*      */       
/*      */       case 0:
/* 1318 */         minecraft = Minecraft.getMinecraft();
/* 1319 */         fogVec = getFogColor(fogVec, (IBlockAccess)minecraft.theWorld, renderViewEntity.posX, renderViewEntity.posY + 1.0D, renderViewEntity.posZ);
/*      */         break;
/*      */       
/*      */       case 1:
/* 1323 */         fogVec = getFogColorEnd(fogVec);
/*      */         break;
/*      */     } 
/* 1326 */     return fogVec;
/*      */   }
/*      */   
/*      */   public static Vec3 getWorldSkyColor(Vec3 skyVec, World world, Entity renderViewEntity, float partialTicks) {
/*      */     Minecraft minecraft;
/* 1331 */     int i = world.provider.getDimensionId();
/*      */     
/* 1333 */     switch (i) {
/*      */       
/*      */       case 0:
/* 1336 */         minecraft = Minecraft.getMinecraft();
/* 1337 */         skyVec = getSkyColor(skyVec, (IBlockAccess)minecraft.theWorld, renderViewEntity.posX, renderViewEntity.posY + 1.0D, renderViewEntity.posZ);
/*      */         break;
/*      */       
/*      */       case 1:
/* 1341 */         skyVec = getSkyColorEnd(skyVec);
/*      */         break;
/*      */     } 
/* 1344 */     return skyVec;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int[] readSpawnEggColors(Properties props, String fileName, String prefix, String logName) {
/* 1349 */     List<Integer> list = new ArrayList<>();
/* 1350 */     Set<Object> set = props.keySet();
/* 1351 */     int i = 0;
/*      */     
/* 1353 */     for (Object e : set) {
/*      */       
/* 1355 */       String s = (String)e;
/* 1356 */       String s1 = props.getProperty(s);
/*      */       
/* 1358 */       if (s.startsWith(prefix)) {
/*      */         
/* 1360 */         String s2 = StrUtils.removePrefix(s, prefix);
/* 1361 */         int j = EntityUtils.getEntityIdByName(s2);
/*      */         
/* 1363 */         if (j < 0) {
/*      */           
/* 1365 */           warn("Invalid spawn egg name: " + s);
/*      */           
/*      */           continue;
/*      */         } 
/* 1369 */         int k = parseColor(s1);
/*      */         
/* 1371 */         if (k < 0) {
/*      */           
/* 1373 */           warn("Invalid spawn egg color: " + s + " = " + s1);
/*      */           
/*      */           continue;
/*      */         } 
/* 1377 */         while (list.size() <= j)
/*      */         {
/* 1379 */           list.add(Integer.valueOf(-1));
/*      */         }
/*      */         
/* 1382 */         list.set(j, Integer.valueOf(k));
/* 1383 */         i++;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1389 */     if (i <= 0)
/*      */     {
/* 1391 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1395 */     dbg(logName + " colors: " + i);
/* 1396 */     int[] aint = new int[list.size()];
/*      */     
/* 1398 */     for (int l = 0; l < aint.length; l++)
/*      */     {
/* 1400 */       aint[l] = ((Integer)list.get(l)).intValue();
/*      */     }
/*      */     
/* 1403 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getSpawnEggColor(ItemMonsterPlacer item, ItemStack itemStack, int layer, int color) {
/* 1409 */     int i = itemStack.getMetadata();
/* 1410 */     int[] aint = (layer == 0) ? spawnEggPrimaryColors : spawnEggSecondaryColors;
/*      */     
/* 1412 */     if (aint == null)
/*      */     {
/* 1414 */       return color;
/*      */     }
/* 1416 */     if (i >= 0 && i < aint.length) {
/*      */       
/* 1418 */       int j = aint[i];
/* 1419 */       return (j < 0) ? color : j;
/*      */     } 
/*      */ 
/*      */     
/* 1423 */     return color;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getColorFromItemStack(ItemStack itemStack, int layer, int color) {
/* 1429 */     if (itemStack == null)
/*      */     {
/* 1431 */       return color;
/*      */     }
/*      */ 
/*      */     
/* 1435 */     Item item = itemStack.getItem();
/* 1436 */     return (item == null) ? color : ((item instanceof ItemMonsterPlacer) ? getSpawnEggColor((ItemMonsterPlacer)item, itemStack, layer, color) : color);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static float[][] readDyeColors(Properties props, String fileName, String prefix, String logName) {
/* 1442 */     EnumDyeColor[] aenumdyecolor = EnumDyeColor.values();
/* 1443 */     Map<String, EnumDyeColor> map = new HashMap<>();
/*      */     
/* 1445 */     for (int i = 0; i < aenumdyecolor.length; i++) {
/*      */       
/* 1447 */       EnumDyeColor enumdyecolor = aenumdyecolor[i];
/* 1448 */       map.put(enumdyecolor.getName(), enumdyecolor);
/*      */     } 
/*      */     
/* 1451 */     float[][] afloat1 = new float[aenumdyecolor.length][];
/* 1452 */     int k = 0;
/*      */     
/* 1454 */     for (Object e : props.keySet()) {
/*      */       
/* 1456 */       String s = (String)e;
/* 1457 */       String s1 = props.getProperty(s);
/*      */       
/* 1459 */       if (s.startsWith(prefix)) {
/*      */         
/* 1461 */         String s2 = StrUtils.removePrefix(s, prefix);
/*      */         
/* 1463 */         if (s2.equals("lightBlue"))
/*      */         {
/* 1465 */           s2 = "light_blue";
/*      */         }
/*      */         
/* 1468 */         EnumDyeColor enumdyecolor1 = map.get(s2);
/* 1469 */         int j = parseColor(s1);
/*      */         
/* 1471 */         if (enumdyecolor1 != null && j >= 0) {
/*      */           
/* 1473 */           float[] afloat = { (j >> 16 & 0xFF) / 255.0F, (j >> 8 & 0xFF) / 255.0F, (j & 0xFF) / 255.0F };
/* 1474 */           afloat1[enumdyecolor1.ordinal()] = afloat;
/* 1475 */           k++;
/*      */           
/*      */           continue;
/*      */         } 
/* 1479 */         warn("Invalid color: " + s + " = " + s1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1484 */     if (k <= 0)
/*      */     {
/* 1486 */       return (float[][])null;
/*      */     }
/*      */ 
/*      */     
/* 1490 */     dbg(logName + " colors: " + k);
/* 1491 */     return afloat1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static float[] getDyeColors(EnumDyeColor dye, float[][] dyeColors, float[] colors) {
/* 1497 */     if (dyeColors == null)
/*      */     {
/* 1499 */       return colors;
/*      */     }
/* 1501 */     if (dye == null)
/*      */     {
/* 1503 */       return colors;
/*      */     }
/*      */ 
/*      */     
/* 1507 */     float[] afloat = dyeColors[dye.ordinal()];
/* 1508 */     return (afloat == null) ? colors : afloat;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] getWolfCollarColors(EnumDyeColor dye, float[] colors) {
/* 1514 */     return getDyeColors(dye, wolfCollarColors, colors);
/*      */   }
/*      */ 
/*      */   
/*      */   public static float[] getSheepColors(EnumDyeColor dye, float[] colors) {
/* 1519 */     return getDyeColors(dye, sheepColors, colors);
/*      */   }
/*      */ 
/*      */   
/*      */   private static int[] readTextColors(Properties props, String fileName, String prefix, String logName) {
/* 1524 */     int[] aint = new int[32];
/* 1525 */     Arrays.fill(aint, -1);
/* 1526 */     int i = 0;
/*      */     
/* 1528 */     for (Object e : props.keySet()) {
/*      */       
/* 1530 */       String s = (String)e;
/* 1531 */       String s1 = props.getProperty(s);
/*      */       
/* 1533 */       if (s.startsWith(prefix)) {
/*      */         
/* 1535 */         String s2 = StrUtils.removePrefix(s, prefix);
/* 1536 */         int j = Config.parseInt(s2, -1);
/* 1537 */         int k = parseColor(s1);
/*      */         
/* 1539 */         if (j >= 0 && j < aint.length && k >= 0) {
/*      */           
/* 1541 */           aint[j] = k;
/* 1542 */           i++;
/*      */           
/*      */           continue;
/*      */         } 
/* 1546 */         warn("Invalid color: " + s + " = " + s1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1551 */     if (i <= 0)
/*      */     {
/* 1553 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1557 */     dbg(logName + " colors: " + i);
/* 1558 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getTextColor(int index, int color) {
/* 1564 */     if (textColors == null)
/*      */     {
/* 1566 */       return color;
/*      */     }
/* 1568 */     if (index >= 0 && index < textColors.length) {
/*      */       
/* 1570 */       int i = textColors[index];
/* 1571 */       return (i < 0) ? color : i;
/*      */     } 
/*      */ 
/*      */     
/* 1575 */     return color;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[] readMapColors(Properties props, String fileName, String prefix, String logName) {
/* 1581 */     int[] aint = new int[MapColor.mapColorArray.length];
/* 1582 */     Arrays.fill(aint, -1);
/* 1583 */     int i = 0;
/*      */     
/* 1585 */     for (Object o : props.keySet()) {
/*      */       
/* 1587 */       String s = (String)o;
/* 1588 */       String s1 = props.getProperty(s);
/*      */       
/* 1590 */       if (s.startsWith(prefix)) {
/*      */         
/* 1592 */         String s2 = StrUtils.removePrefix(s, prefix);
/* 1593 */         int j = getMapColorIndex(s2);
/* 1594 */         int k = parseColor(s1);
/*      */         
/* 1596 */         if (j >= 0 && j < aint.length && k >= 0) {
/*      */           
/* 1598 */           aint[j] = k;
/* 1599 */           i++;
/*      */           
/*      */           continue;
/*      */         } 
/* 1603 */         warn("Invalid color: " + s + " = " + s1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1608 */     if (i <= 0)
/*      */     {
/* 1610 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1614 */     dbg(logName + " colors: " + i);
/* 1615 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[] readPotionColors(Properties props, String fileName, String prefix, String logName) {
/* 1621 */     int[] aint = new int[Potion.potionTypes.length];
/* 1622 */     Arrays.fill(aint, -1);
/* 1623 */     int i = 0;
/*      */     
/* 1625 */     for (Object e : props.keySet()) {
/*      */       
/* 1627 */       String s = (String)e;
/* 1628 */       String s1 = props.getProperty(s);
/*      */       
/* 1630 */       if (s.startsWith(prefix)) {
/*      */         
/* 1632 */         int j = getPotionId(s);
/* 1633 */         int k = parseColor(s1);
/*      */         
/* 1635 */         if (j >= 0 && j < aint.length && k >= 0) {
/*      */           
/* 1637 */           aint[j] = k;
/* 1638 */           i++;
/*      */           
/*      */           continue;
/*      */         } 
/* 1642 */         warn("Invalid color: " + s + " = " + s1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1647 */     if (i <= 0)
/*      */     {
/* 1649 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1653 */     dbg(logName + " colors: " + i);
/* 1654 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getPotionId(String name) {
/* 1660 */     if (name.equals("potion.water"))
/*      */     {
/* 1662 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 1666 */     Potion[] apotion = Potion.potionTypes;
/*      */     
/* 1668 */     for (int i = 0; i < apotion.length; i++) {
/*      */       
/* 1670 */       Potion potion = apotion[i];
/*      */       
/* 1672 */       if (potion != null && potion.getName().equals(name))
/*      */       {
/* 1674 */         return potion.getId();
/*      */       }
/*      */     } 
/*      */     
/* 1678 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getPotionColor(int potionId, int color) {
/* 1684 */     if (potionColors == null)
/*      */     {
/* 1686 */       return color;
/*      */     }
/* 1688 */     if (potionId >= 0 && potionId < potionColors.length) {
/*      */       
/* 1690 */       int i = potionColors[potionId];
/* 1691 */       return (i < 0) ? color : i;
/*      */     } 
/*      */ 
/*      */     
/* 1695 */     return color;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getMapColorIndex(String name) {
/* 1701 */     return (name == null) ? -1 : (name.equals("air") ? MapColor.airColor.colorIndex : (name.equals("grass") ? MapColor.grassColor.colorIndex : (name.equals("sand") ? MapColor.sandColor.colorIndex : (name.equals("cloth") ? MapColor.clothColor.colorIndex : (name.equals("tnt") ? MapColor.tntColor.colorIndex : (name.equals("ice") ? MapColor.iceColor.colorIndex : (name.equals("iron") ? MapColor.ironColor.colorIndex : (name.equals("foliage") ? MapColor.foliageColor.colorIndex : (name.equals("clay") ? MapColor.clayColor.colorIndex : (name.equals("dirt") ? MapColor.dirtColor.colorIndex : (name.equals("stone") ? MapColor.stoneColor.colorIndex : (name.equals("water") ? MapColor.waterColor.colorIndex : (name.equals("wood") ? MapColor.woodColor.colorIndex : (name.equals("quartz") ? MapColor.quartzColor.colorIndex : (name.equals("gold") ? MapColor.goldColor.colorIndex : (name.equals("diamond") ? MapColor.diamondColor.colorIndex : (name.equals("lapis") ? MapColor.lapisColor.colorIndex : (name.equals("emerald") ? MapColor.emeraldColor.colorIndex : (name.equals("podzol") ? MapColor.obsidianColor.colorIndex : (name.equals("netherrack") ? MapColor.netherrackColor.colorIndex : ((!name.equals("snow") && !name.equals("white")) ? ((!name.equals("adobe") && !name.equals("orange")) ? (name.equals("magenta") ? MapColor.magentaColor.colorIndex : ((!name.equals("light_blue") && !name.equals("lightBlue")) ? (name.equals("yellow") ? MapColor.yellowColor.colorIndex : (name.equals("lime") ? MapColor.limeColor.colorIndex : (name.equals("pink") ? MapColor.pinkColor.colorIndex : (name.equals("gray") ? MapColor.grayColor.colorIndex : (name.equals("silver") ? MapColor.silverColor.colorIndex : (name.equals("cyan") ? MapColor.cyanColor.colorIndex : (name.equals("purple") ? MapColor.purpleColor.colorIndex : (name.equals("blue") ? MapColor.blueColor.colorIndex : (name.equals("brown") ? MapColor.brownColor.colorIndex : (name.equals("green") ? MapColor.greenColor.colorIndex : (name.equals("red") ? MapColor.redColor.colorIndex : (name.equals("black") ? MapColor.blackColor.colorIndex : -1)))))))))))) : MapColor.lightBlueColor.colorIndex)) : MapColor.adobeColor.colorIndex) : MapColor.snowColor.colorIndex)))))))))))))))))))));
/*      */   }
/*      */ 
/*      */   
/*      */   private static int[] getMapColors() {
/* 1706 */     MapColor[] amapcolor = MapColor.mapColorArray;
/* 1707 */     int[] aint = new int[amapcolor.length];
/* 1708 */     Arrays.fill(aint, -1);
/*      */     
/* 1710 */     for (int i = 0; i < amapcolor.length && i < aint.length; i++) {
/*      */       
/* 1712 */       MapColor mapcolor = amapcolor[i];
/*      */       
/* 1714 */       if (mapcolor != null)
/*      */       {
/* 1716 */         aint[i] = mapcolor.colorValue;
/*      */       }
/*      */     } 
/*      */     
/* 1720 */     return aint;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setMapColors(int[] colors) {
/* 1725 */     if (colors != null) {
/*      */       
/* 1727 */       MapColor[] amapcolor = MapColor.mapColorArray;
/* 1728 */       boolean flag = false;
/*      */       
/* 1730 */       for (int i = 0; i < amapcolor.length && i < colors.length; i++) {
/*      */         
/* 1732 */         MapColor mapcolor = amapcolor[i];
/*      */         
/* 1734 */         if (mapcolor != null) {
/*      */           
/* 1736 */           int j = colors[i];
/*      */           
/* 1738 */           if (j >= 0 && mapcolor.colorValue != j) {
/*      */             
/* 1740 */             mapcolor.colorValue = j;
/* 1741 */             flag = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1746 */       if (flag)
/*      */       {
/* 1748 */         Minecraft.getMinecraft().getTextureManager().reloadBannerTextures();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void dbg(String str) {
/* 1755 */     Config.dbg("CustomColors: " + str);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void warn(String str) {
/* 1760 */     Config.warn("CustomColors: " + str);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getExpBarTextColor(int color) {
/* 1765 */     return (expBarTextColor < 0) ? color : expBarTextColor;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getBossTextColor(int color) {
/* 1770 */     return (bossTextColor < 0) ? color : bossTextColor;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getSignTextColor(int color) {
/* 1775 */     return (signTextColor < 0) ? color : signTextColor;
/*      */   }
/*      */   
/*      */   public static interface IColorizer {
/*      */     int getColor(IBlockState param1IBlockState, IBlockAccess param1IBlockAccess, BlockPos param1BlockPos);
/*      */     
/*      */     boolean isColorConstant();
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\CustomColors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */