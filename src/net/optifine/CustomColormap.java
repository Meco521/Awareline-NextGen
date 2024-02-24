/*     */ package net.optifine;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashSet;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.BlockStateBase;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.config.MatchBlock;
/*     */ import net.optifine.config.Matches;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ public class CustomColormap
/*     */   implements CustomColors.IColorizer
/*     */ {
/*  29 */   public String name = null;
/*  30 */   public String basePath = null;
/*  31 */   private int format = -1;
/*  32 */   private MatchBlock[] matchBlocks = null;
/*  33 */   private String source = null;
/*  34 */   private int color = -1;
/*  35 */   private int yVariance = 0;
/*  36 */   private int yOffset = 0;
/*  37 */   private int width = 0;
/*  38 */   private int height = 0;
/*  39 */   private int[] colors = null;
/*  40 */   private float[][] colorsRgb = (float[][])null;
/*     */   private static final int FORMAT_UNKNOWN = -1;
/*     */   private static final int FORMAT_VANILLA = 0;
/*     */   private static final int FORMAT_GRID = 1;
/*     */   private static final int FORMAT_FIXED = 2;
/*     */   public static final String FORMAT_VANILLA_STRING = "vanilla";
/*     */   public static final String FORMAT_GRID_STRING = "grid";
/*     */   public static final String FORMAT_FIXED_STRING = "fixed";
/*  48 */   public static final String[] FORMAT_STRINGS = new String[] { "vanilla", "grid", "fixed" };
/*     */   
/*     */   public static final String KEY_FORMAT = "format";
/*     */   public static final String KEY_BLOCKS = "blocks";
/*     */   public static final String KEY_SOURCE = "source";
/*     */   public static final String KEY_COLOR = "color";
/*     */   public static final String KEY_Y_VARIANCE = "yVariance";
/*     */   public static final String KEY_Y_OFFSET = "yOffset";
/*     */   
/*     */   public CustomColormap(Properties props, String path, int width, int height, String formatDefault) {
/*  58 */     ConnectedParser connectedparser = new ConnectedParser("Colormap");
/*  59 */     this.name = connectedparser.parseName(path);
/*  60 */     this.basePath = connectedparser.parseBasePath(path);
/*  61 */     this.format = parseFormat(props.getProperty("format", formatDefault));
/*  62 */     this.matchBlocks = connectedparser.parseMatchBlocks(props.getProperty("blocks"));
/*  63 */     this.source = parseTexture(props.getProperty("source"), path, this.basePath);
/*  64 */     this.color = ConnectedParser.parseColor(props.getProperty("color"), -1);
/*  65 */     this.yVariance = connectedparser.parseInt(props.getProperty("yVariance"), 0);
/*  66 */     this.yOffset = connectedparser.parseInt(props.getProperty("yOffset"), 0);
/*  67 */     this.width = width;
/*  68 */     this.height = height;
/*     */   }
/*     */ 
/*     */   
/*     */   private int parseFormat(String str) {
/*  73 */     if (str == null)
/*     */     {
/*  75 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*  79 */     str = str.trim();
/*     */     
/*  81 */     if (str.equals("vanilla"))
/*     */     {
/*  83 */       return 0;
/*     */     }
/*  85 */     if (str.equals("grid"))
/*     */     {
/*  87 */       return 1;
/*     */     }
/*  89 */     if (str.equals("fixed"))
/*     */     {
/*  91 */       return 2;
/*     */     }
/*     */ 
/*     */     
/*  95 */     warn("Unknown format: " + str);
/*  96 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String path) {
/* 103 */     if (this.format != 0 && this.format != 1) {
/*     */       
/* 105 */       if (this.format != 2)
/*     */       {
/* 107 */         return false;
/*     */       }
/*     */       
/* 110 */       if (this.color < 0)
/*     */       {
/* 112 */         this.color = 16777215;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 117 */       if (this.source == null) {
/*     */         
/* 119 */         warn("Source not defined: " + path);
/* 120 */         return false;
/*     */       } 
/*     */       
/* 123 */       readColors();
/*     */       
/* 125 */       if (this.colors == null)
/*     */       {
/* 127 */         return false;
/*     */       }
/*     */       
/* 130 */       if (this.color < 0) {
/*     */         
/* 132 */         if (this.format == 0)
/*     */         {
/* 134 */           this.color = getColor(127, 127);
/*     */         }
/*     */         
/* 137 */         if (this.format == 1)
/*     */         {
/* 139 */           this.color = getColorGrid(BiomeGenBase.plains, new BlockPos(0, 64, 0));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 144 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidMatchBlocks(String path) {
/* 149 */     if (this.matchBlocks == null) {
/*     */       
/* 151 */       this.matchBlocks = detectMatchBlocks();
/*     */       
/* 153 */       if (this.matchBlocks == null) {
/*     */         
/* 155 */         warn("Match blocks not defined: " + path);
/* 156 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 160 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private MatchBlock[] detectMatchBlocks() {
/* 165 */     Block block = Block.getBlockFromName(this.name);
/*     */     
/* 167 */     if (block != null)
/*     */     {
/* 169 */       return new MatchBlock[] { new MatchBlock(Block.getIdFromBlock(block)) };
/*     */     }
/*     */ 
/*     */     
/* 173 */     Pattern pattern = Pattern.compile("^block(\\d+).*$");
/* 174 */     Matcher matcher = pattern.matcher(this.name);
/*     */     
/* 176 */     if (matcher.matches()) {
/*     */       
/* 178 */       String s = matcher.group(1);
/* 179 */       int i = Config.parseInt(s, -1);
/*     */       
/* 181 */       if (i >= 0)
/*     */       {
/* 183 */         return new MatchBlock[] { new MatchBlock(i) };
/*     */       }
/*     */     } 
/*     */     
/* 187 */     ConnectedParser connectedparser = new ConnectedParser("Colormap");
/* 188 */     MatchBlock[] amatchblock = connectedparser.parseMatchBlock(this.name);
/* 189 */     return (amatchblock != null) ? amatchblock : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readColors() {
/*     */     try {
/* 197 */       this.colors = null;
/*     */       
/* 199 */       if (this.source == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 204 */       String s = this.source + ".png";
/* 205 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/* 206 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */       
/* 208 */       if (inputstream == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 213 */       BufferedImage bufferedimage = TextureUtil.readBufferedImage(inputstream);
/*     */       
/* 215 */       if (bufferedimage == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 220 */       int i = bufferedimage.getWidth();
/* 221 */       int j = bufferedimage.getHeight();
/* 222 */       boolean flag = (this.width < 0 || this.width == i);
/* 223 */       boolean flag1 = (this.height < 0 || this.height == j);
/*     */       
/* 225 */       if (!flag || !flag1)
/*     */       {
/* 227 */         dbg("Non-standard palette size: " + i + "x" + j + ", should be: " + this.width + "x" + this.height + ", path: " + s);
/*     */       }
/*     */       
/* 230 */       this.width = i;
/* 231 */       this.height = j;
/*     */       
/* 233 */       if (this.width <= 0 || this.height <= 0) {
/*     */         
/* 235 */         warn("Invalid palette size: " + i + "x" + j + ", path: " + s);
/*     */         
/*     */         return;
/*     */       } 
/* 239 */       this.colors = new int[i * j];
/* 240 */       bufferedimage.getRGB(0, 0, i, j, this.colors, 0, i);
/*     */     }
/* 242 */     catch (IOException ioexception) {
/*     */       
/* 244 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void dbg(String str) {
/* 250 */     Config.dbg("CustomColors: " + str);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void warn(String str) {
/* 255 */     Config.warn("CustomColors: " + str);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String parseTexture(String texStr, String path, String basePath) {
/* 260 */     if (texStr != null) {
/*     */       
/* 262 */       texStr = texStr.trim();
/* 263 */       String s1 = ".png";
/*     */       
/* 265 */       if (texStr.endsWith(s1))
/*     */       {
/* 267 */         texStr = texStr.substring(0, texStr.length() - s1.length());
/*     */       }
/*     */       
/* 270 */       texStr = fixTextureName(texStr, basePath);
/* 271 */       return texStr;
/*     */     } 
/*     */ 
/*     */     
/* 275 */     String s = path;
/* 276 */     int i = path.lastIndexOf('/');
/*     */     
/* 278 */     if (i >= 0)
/*     */     {
/* 280 */       s = path.substring(i + 1);
/*     */     }
/*     */     
/* 283 */     int j = s.lastIndexOf('.');
/*     */     
/* 285 */     if (j >= 0)
/*     */     {
/* 287 */       s = s.substring(0, j);
/*     */     }
/*     */     
/* 290 */     s = fixTextureName(s, basePath);
/* 291 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String fixTextureName(String iconName, String basePath) {
/* 297 */     iconName = TextureUtils.fixResourcePath(iconName, basePath);
/*     */     
/* 299 */     if (!iconName.startsWith(basePath) && !iconName.startsWith("textures/") && !iconName.startsWith("mcpatcher/"))
/*     */     {
/* 301 */       iconName = basePath + "/" + iconName;
/*     */     }
/*     */     
/* 304 */     if (iconName.endsWith(".png"))
/*     */     {
/* 306 */       iconName = iconName.substring(0, iconName.length() - 4);
/*     */     }
/*     */     
/* 309 */     String s = "textures/blocks/";
/*     */     
/* 311 */     if (iconName.startsWith(s))
/*     */     {
/* 313 */       iconName = iconName.substring(s.length());
/*     */     }
/*     */     
/* 316 */     if (!iconName.isEmpty() && iconName.charAt(0) == '/')
/*     */     {
/* 318 */       iconName = iconName.substring(1);
/*     */     }
/*     */     
/* 321 */     return iconName;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchesBlock(BlockStateBase blockState) {
/* 326 */     return Matches.block(blockState, this.matchBlocks);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorRandom() {
/* 331 */     if (this.format == 2)
/*     */     {
/* 333 */       return this.color;
/*     */     }
/*     */ 
/*     */     
/* 337 */     int i = CustomColors.random.nextInt(this.colors.length);
/* 338 */     return this.colors[i];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColor(int index) {
/* 344 */     index = Config.limit(index, 0, this.colors.length - 1);
/* 345 */     return this.colors[index] & 0xFFFFFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColor(int cx, int cy) {
/* 350 */     cx = Config.limit(cx, 0, this.width - 1);
/* 351 */     cy = Config.limit(cy, 0, this.height - 1);
/* 352 */     return this.colors[cy * this.width + cx] & 0xFFFFFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[][] getColorsRgb() {
/* 357 */     if (this.colorsRgb == null)
/*     */     {
/* 359 */       this.colorsRgb = toRgb(this.colors);
/*     */     }
/*     */     
/* 362 */     return this.colorsRgb;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColor(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos) {
/* 367 */     return getColor(blockAccess, blockPos);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColor(IBlockAccess blockAccess, BlockPos blockPos) {
/* 372 */     BiomeGenBase biomegenbase = CustomColors.getColorBiome(blockAccess, blockPos);
/* 373 */     return getColor(biomegenbase, blockPos);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isColorConstant() {
/* 378 */     return (this.format == 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColor(BiomeGenBase biome, BlockPos blockPos) {
/* 383 */     return (this.format == 0) ? getColorVanilla(biome, blockPos) : ((this.format == 1) ? getColorGrid(biome, blockPos) : this.color);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorSmooth(IBlockAccess blockAccess, double x, double y, double z, int radius) {
/* 388 */     if (this.format == 2)
/*     */     {
/* 390 */       return this.color;
/*     */     }
/*     */ 
/*     */     
/* 394 */     int i = MathHelper.floor_double(x);
/* 395 */     int j = MathHelper.floor_double(y);
/* 396 */     int k = MathHelper.floor_double(z);
/* 397 */     int l = 0;
/* 398 */     int i1 = 0;
/* 399 */     int j1 = 0;
/* 400 */     int k1 = 0;
/* 401 */     BlockPosM blockposm = new BlockPosM(0, 0, 0);
/*     */     
/* 403 */     for (int l1 = i - radius; l1 <= i + radius; l1++) {
/*     */       
/* 405 */       for (int i2 = k - radius; i2 <= k + radius; i2++) {
/*     */         
/* 407 */         blockposm.setXyz(l1, j, i2);
/* 408 */         int j2 = getColor(blockAccess, blockposm);
/* 409 */         l += j2 >> 16 & 0xFF;
/* 410 */         i1 += j2 >> 8 & 0xFF;
/* 411 */         j1 += j2 & 0xFF;
/* 412 */         k1++;
/*     */       } 
/*     */     } 
/*     */     
/* 416 */     int k2 = l / k1;
/* 417 */     int l2 = i1 / k1;
/* 418 */     int i3 = j1 / k1;
/* 419 */     return k2 << 16 | l2 << 8 | i3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getColorVanilla(BiomeGenBase biome, BlockPos blockPos) {
/* 425 */     double d0 = MathHelper.clamp_float(biome.getFloatTemperature(blockPos), 0.0F, 1.0F);
/* 426 */     double d1 = MathHelper.clamp_float(biome.getFloatRainfall(), 0.0F, 1.0F);
/* 427 */     d1 *= d0;
/* 428 */     int i = (int)((1.0D - d0) * (this.width - 1));
/* 429 */     int j = (int)((1.0D - d1) * (this.height - 1));
/* 430 */     return getColor(i, j);
/*     */   }
/*     */ 
/*     */   
/*     */   private int getColorGrid(BiomeGenBase biome, BlockPos blockPos) {
/* 435 */     int i = biome.biomeID;
/* 436 */     int j = blockPos.getY() - this.yOffset;
/*     */     
/* 438 */     if (this.yVariance > 0) {
/*     */       
/* 440 */       int k = blockPos.getX() << 16 + blockPos.getZ();
/* 441 */       int l = Config.intHash(k);
/* 442 */       int i1 = (this.yVariance << 1) + 1;
/* 443 */       int j1 = (l & 0xFF) % i1 - this.yVariance;
/* 444 */       j += j1;
/*     */     } 
/*     */     
/* 447 */     return getColor(i, j);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLength() {
/* 452 */     return (this.format == 2) ? 1 : this.colors.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 457 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 462 */     return this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   private static float[][] toRgb(int[] cols) {
/* 467 */     float[][] afloat = new float[cols.length][3];
/*     */     
/* 469 */     for (int i = 0; i < cols.length; i++) {
/*     */       
/* 471 */       int j = cols[i];
/* 472 */       float f = (j >> 16 & 0xFF) / 255.0F;
/* 473 */       float f1 = (j >> 8 & 0xFF) / 255.0F;
/* 474 */       float f2 = (j & 0xFF) / 255.0F;
/* 475 */       float[] afloat1 = afloat[i];
/* 476 */       afloat1[0] = f;
/* 477 */       afloat1[1] = f1;
/* 478 */       afloat1[2] = f2;
/*     */     } 
/*     */     
/* 481 */     return afloat;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMatchBlock(MatchBlock mb) {
/* 486 */     if (this.matchBlocks == null)
/*     */     {
/* 488 */       this.matchBlocks = new MatchBlock[0];
/*     */     }
/*     */     
/* 491 */     this.matchBlocks = (MatchBlock[])Config.addObjectToArray((Object[])this.matchBlocks, mb);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMatchBlock(int blockId, int metadata) {
/* 496 */     MatchBlock matchblock = getMatchBlock(blockId);
/*     */     
/* 498 */     if (matchblock != null) {
/*     */       
/* 500 */       if (metadata >= 0)
/*     */       {
/* 502 */         matchblock.addMetadata(metadata);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 507 */       addMatchBlock(new MatchBlock(blockId, metadata));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private MatchBlock getMatchBlock(int blockId) {
/* 513 */     if (this.matchBlocks == null)
/*     */     {
/* 515 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 519 */     for (int i = 0; i < this.matchBlocks.length; i++) {
/*     */       
/* 521 */       MatchBlock matchblock = this.matchBlocks[i];
/*     */       
/* 523 */       if (matchblock.getBlockId() == blockId)
/*     */       {
/* 525 */         return matchblock;
/*     */       }
/*     */     } 
/*     */     
/* 529 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getMatchBlockIds() {
/* 535 */     if (this.matchBlocks == null)
/*     */     {
/* 537 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 541 */     Set<Integer> set = new HashSet();
/*     */     
/* 543 */     for (int i = 0; i < this.matchBlocks.length; i++) {
/*     */       
/* 545 */       MatchBlock matchblock = this.matchBlocks[i];
/*     */       
/* 547 */       if (matchblock.getBlockId() >= 0)
/*     */       {
/* 549 */         set.add(Integer.valueOf(matchblock.getBlockId()));
/*     */       }
/*     */     } 
/*     */     
/* 553 */     Integer[] ainteger = set.<Integer>toArray(new Integer[0]);
/* 554 */     int[] aint = new int[ainteger.length];
/*     */     
/* 556 */     for (int j = 0; j < ainteger.length; j++)
/*     */     {
/* 558 */       aint[j] = ainteger[j].intValue();
/*     */     }
/*     */     
/* 561 */     return aint;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 567 */     return this.basePath + "/" + this.name + ", blocks: " + Config.arrayToString((Object[])this.matchBlocks) + ", source: " + this.source;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\CustomColormap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */