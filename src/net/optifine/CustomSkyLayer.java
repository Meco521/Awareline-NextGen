/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.config.Matches;
/*     */ import net.optifine.config.RangeListInt;
/*     */ import net.optifine.render.Blender;
/*     */ import net.optifine.util.NumUtils;
/*     */ import net.optifine.util.SmoothFloat;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ 
/*     */ public class CustomSkyLayer
/*     */ {
/*  28 */   public String source = null;
/*  29 */   private int startFadeIn = -1;
/*  30 */   private int endFadeIn = -1;
/*  31 */   private int startFadeOut = -1;
/*  32 */   private int endFadeOut = -1;
/*  33 */   private int blend = 1;
/*     */   private boolean rotate = false;
/*  35 */   private float speed = 1.0F;
/*     */   private float[] axis;
/*     */   private RangeListInt days;
/*     */   private int daysLoop;
/*     */   private boolean weatherClear;
/*     */   private boolean weatherRain;
/*     */   private boolean weatherThunder;
/*     */   public BiomeGenBase[] biomes;
/*     */   public RangeListInt heights;
/*     */   private float transition;
/*     */   private SmoothFloat smoothPositionBrightness;
/*     */   public int textureId;
/*     */   private World lastWorld;
/*  48 */   public static final float[] DEFAULT_AXIS = new float[] { 1.0F, 0.0F, 0.0F };
/*     */   
/*     */   private static final String WEATHER_CLEAR = "clear";
/*     */   private static final String WEATHER_RAIN = "rain";
/*     */   private static final String WEATHER_THUNDER = "thunder";
/*     */   
/*     */   public CustomSkyLayer(Properties props, String defSource) {
/*  55 */     this.axis = DEFAULT_AXIS;
/*  56 */     this.days = null;
/*  57 */     this.daysLoop = 8;
/*  58 */     this.weatherClear = true;
/*  59 */     this.weatherRain = false;
/*  60 */     this.weatherThunder = false;
/*  61 */     this.biomes = null;
/*  62 */     this.heights = null;
/*  63 */     this.transition = 1.0F;
/*  64 */     this.smoothPositionBrightness = null;
/*  65 */     this.textureId = -1;
/*  66 */     this.lastWorld = null;
/*  67 */     ConnectedParser connectedparser = new ConnectedParser("CustomSky");
/*  68 */     this.source = props.getProperty("source", defSource);
/*  69 */     this.startFadeIn = parseTime(props.getProperty("startFadeIn"));
/*  70 */     this.endFadeIn = parseTime(props.getProperty("endFadeIn"));
/*  71 */     this.startFadeOut = parseTime(props.getProperty("startFadeOut"));
/*  72 */     this.endFadeOut = parseTime(props.getProperty("endFadeOut"));
/*  73 */     this.blend = Blender.parseBlend(props.getProperty("blend"));
/*  74 */     this.rotate = parseBoolean(props.getProperty("rotate"), true);
/*  75 */     this.speed = parseFloat(props.getProperty("speed"), 1.0F);
/*  76 */     this.axis = parseAxis(props.getProperty("axis"), DEFAULT_AXIS);
/*  77 */     this.days = connectedparser.parseRangeListInt(props.getProperty("days"));
/*  78 */     this.daysLoop = connectedparser.parseInt(props.getProperty("daysLoop"), 8);
/*  79 */     List<String> list = parseWeatherList(props.getProperty("weather", "clear"));
/*  80 */     this.weatherClear = list.contains("clear");
/*  81 */     this.weatherRain = list.contains("rain");
/*  82 */     this.weatherThunder = list.contains("thunder");
/*  83 */     this.biomes = connectedparser.parseBiomes(props.getProperty("biomes"));
/*  84 */     this.heights = connectedparser.parseRangeListInt(props.getProperty("heights"));
/*  85 */     this.transition = parseFloat(props.getProperty("transition"), 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private List<String> parseWeatherList(String str) {
/*  90 */     List<String> list = Arrays.asList(new String[] { "clear", "rain", "thunder" });
/*  91 */     List<String> list1 = new ArrayList<>();
/*  92 */     String[] astring = Config.tokenize(str, " ");
/*     */     
/*  94 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/*  96 */       String s = astring[i];
/*     */       
/*  98 */       if (!list.contains(s)) {
/*     */         
/* 100 */         Config.warn("Unknown weather: " + s);
/*     */       }
/*     */       else {
/*     */         
/* 104 */         list1.add(s);
/*     */       } 
/*     */     } 
/*     */     
/* 108 */     return list1;
/*     */   }
/*     */ 
/*     */   
/*     */   private int parseTime(String str) {
/* 113 */     if (str == null)
/*     */     {
/* 115 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 119 */     String[] astring = Config.tokenize(str, ":");
/*     */     
/* 121 */     if (astring.length != 2) {
/*     */       
/* 123 */       Config.warn("Invalid time: " + str);
/* 124 */       return -1;
/*     */     } 
/*     */ 
/*     */     
/* 128 */     String s = astring[0];
/* 129 */     String s1 = astring[1];
/* 130 */     int i = Config.parseInt(s, -1);
/* 131 */     int j = Config.parseInt(s1, -1);
/*     */     
/* 133 */     if (i >= 0 && i <= 23 && j >= 0 && j <= 59) {
/*     */       
/* 135 */       i -= 6;
/*     */       
/* 137 */       if (i < 0)
/*     */       {
/* 139 */         i += 24;
/*     */       }
/*     */       
/* 142 */       int k = i * 1000 + (int)(j / 60.0D * 1000.0D);
/* 143 */       return k;
/*     */     } 
/*     */ 
/*     */     
/* 147 */     Config.warn("Invalid time: " + str);
/* 148 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean parseBoolean(String str, boolean defVal) {
/* 156 */     if (str == null)
/*     */     {
/* 158 */       return defVal;
/*     */     }
/* 160 */     if (str.toLowerCase().equals("true"))
/*     */     {
/* 162 */       return true;
/*     */     }
/* 164 */     if (str.toLowerCase().equals("false"))
/*     */     {
/* 166 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 170 */     Config.warn("Unknown boolean: " + str);
/* 171 */     return defVal;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float parseFloat(String str, float defVal) {
/* 177 */     if (str == null)
/*     */     {
/* 179 */       return defVal;
/*     */     }
/*     */ 
/*     */     
/* 183 */     float f = Config.parseFloat(str, Float.MIN_VALUE);
/*     */     
/* 185 */     if (f == Float.MIN_VALUE) {
/*     */       
/* 187 */       Config.warn("Invalid value: " + str);
/* 188 */       return defVal;
/*     */     } 
/*     */ 
/*     */     
/* 192 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float[] parseAxis(String str, float[] defVal) {
/* 199 */     if (str == null)
/*     */     {
/* 201 */       return defVal;
/*     */     }
/*     */ 
/*     */     
/* 205 */     String[] astring = Config.tokenize(str, " ");
/*     */     
/* 207 */     if (astring.length != 3) {
/*     */       
/* 209 */       Config.warn("Invalid axis: " + str);
/* 210 */       return defVal;
/*     */     } 
/*     */ 
/*     */     
/* 214 */     float[] afloat = new float[3];
/*     */     
/* 216 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 218 */       afloat[i] = Config.parseFloat(astring[i], Float.MIN_VALUE);
/*     */       
/* 220 */       if (afloat[i] == Float.MIN_VALUE) {
/*     */         
/* 222 */         Config.warn("Invalid axis: " + str);
/* 223 */         return defVal;
/*     */       } 
/*     */       
/* 226 */       if (afloat[i] < -1.0F || afloat[i] > 1.0F) {
/*     */         
/* 228 */         Config.warn("Invalid axis values: " + str);
/* 229 */         return defVal;
/*     */       } 
/*     */     } 
/*     */     
/* 233 */     float f2 = afloat[0];
/* 234 */     float f = afloat[1];
/* 235 */     float f1 = afloat[2];
/*     */     
/* 237 */     if (f2 * f2 + f * f + f1 * f1 < 1.0E-5F) {
/*     */       
/* 239 */       Config.warn("Invalid axis values: " + str);
/* 240 */       return defVal;
/*     */     } 
/*     */ 
/*     */     
/* 244 */     float[] afloat1 = { f1, f, -f2 };
/* 245 */     return afloat1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String path) {
/* 253 */     if (this.source == null) {
/*     */       
/* 255 */       Config.warn("No source texture: " + path);
/* 256 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 260 */     this.source = TextureUtils.fixResourcePath(this.source, TextureUtils.getBasePath(path));
/*     */     
/* 262 */     if (this.startFadeIn >= 0 && this.endFadeIn >= 0 && this.endFadeOut >= 0) {
/*     */       
/* 264 */       int i = normalizeTime(this.endFadeIn - this.startFadeIn);
/*     */       
/* 266 */       if (this.startFadeOut < 0) {
/*     */         
/* 268 */         this.startFadeOut = normalizeTime(this.endFadeOut - i);
/*     */         
/* 270 */         if (timeBetween(this.startFadeOut, this.startFadeIn, this.endFadeIn))
/*     */         {
/* 272 */           this.startFadeOut = this.endFadeIn;
/*     */         }
/*     */       } 
/*     */       
/* 276 */       int j = normalizeTime(this.startFadeOut - this.endFadeIn);
/* 277 */       int k = normalizeTime(this.endFadeOut - this.startFadeOut);
/* 278 */       int l = normalizeTime(this.startFadeIn - this.endFadeOut);
/* 279 */       int i1 = i + j + k + l;
/*     */       
/* 281 */       if (i1 != 24000) {
/*     */         
/* 283 */         Config.warn("Invalid fadeIn/fadeOut times, sum is not 24h: " + i1);
/* 284 */         return false;
/*     */       } 
/* 286 */       if (this.speed < 0.0F) {
/*     */         
/* 288 */         Config.warn("Invalid speed: " + this.speed);
/* 289 */         return false;
/*     */       } 
/* 291 */       if (this.daysLoop <= 0) {
/*     */         
/* 293 */         Config.warn("Invalid daysLoop: " + this.daysLoop);
/* 294 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 298 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 303 */     Config.warn("Invalid times, required are: startFadeIn, endFadeIn and endFadeOut.");
/* 304 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int normalizeTime(int timeMc) {
/* 311 */     while (timeMc >= 24000)
/*     */     {
/* 313 */       timeMc -= 24000;
/*     */     }
/*     */     
/* 316 */     while (timeMc < 0)
/*     */     {
/* 318 */       timeMc += 24000;
/*     */     }
/*     */     
/* 321 */     return timeMc;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(World world, int timeOfDay, float celestialAngle, float rainStrength, float thunderStrength) {
/* 326 */     float f = getPositionBrightness(world);
/* 327 */     float f1 = getWeatherBrightness(rainStrength, thunderStrength);
/* 328 */     float f2 = getFadeBrightness(timeOfDay);
/* 329 */     float f3 = f * f1 * f2;
/* 330 */     f3 = Config.limit(f3, 0.0F, 1.0F);
/*     */     
/* 332 */     if (f3 >= 1.0E-4F) {
/*     */       
/* 334 */       GlStateManager.bindTexture(this.textureId);
/* 335 */       Blender.setupBlend(this.blend, f3);
/* 336 */       GlStateManager.pushMatrix();
/*     */       
/* 338 */       if (this.rotate) {
/*     */         
/* 340 */         float f4 = 0.0F;
/*     */         
/* 342 */         if (this.speed != Math.round(this.speed)) {
/*     */           
/* 344 */           long i = (world.getWorldTime() + 18000L) / 24000L;
/* 345 */           double d0 = (this.speed % 1.0F);
/* 346 */           double d1 = i * d0;
/* 347 */           f4 = (float)(d1 % 1.0D);
/*     */         } 
/*     */         
/* 350 */         GlStateManager.rotate(360.0F * (f4 + celestialAngle * this.speed), this.axis[0], this.axis[1], this.axis[2]);
/*     */       } 
/*     */       
/* 353 */       Tessellator tessellator = Tessellator.getInstance();
/* 354 */       GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 355 */       GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
/* 356 */       renderSide(tessellator, 4);
/* 357 */       GlStateManager.pushMatrix();
/* 358 */       GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 359 */       renderSide(tessellator, 1);
/* 360 */       GlStateManager.popMatrix();
/* 361 */       GlStateManager.pushMatrix();
/* 362 */       GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
/* 363 */       renderSide(tessellator, 0);
/* 364 */       GlStateManager.popMatrix();
/* 365 */       GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 366 */       renderSide(tessellator, 5);
/* 367 */       GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 368 */       renderSide(tessellator, 2);
/* 369 */       GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 370 */       renderSide(tessellator, 3);
/* 371 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private float getPositionBrightness(World world) {
/* 377 */     if (this.biomes == null && this.heights == null)
/*     */     {
/* 379 */       return 1.0F;
/*     */     }
/*     */ 
/*     */     
/* 383 */     float f = getPositionBrightnessRaw(world);
/*     */     
/* 385 */     if (this.smoothPositionBrightness == null)
/*     */     {
/* 387 */       this.smoothPositionBrightness = new SmoothFloat(f, this.transition);
/*     */     }
/*     */     
/* 390 */     f = this.smoothPositionBrightness.getSmoothValue(f);
/* 391 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float getPositionBrightnessRaw(World world) {
/* 397 */     Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
/*     */     
/* 399 */     if (entity == null)
/*     */     {
/* 401 */       return 0.0F;
/*     */     }
/*     */ 
/*     */     
/* 405 */     BlockPos blockpos = entity.getPosition();
/*     */     
/* 407 */     if (this.biomes != null) {
/*     */       
/* 409 */       BiomeGenBase biomegenbase = world.getBiomeGenForCoords(blockpos);
/*     */       
/* 411 */       if (biomegenbase == null)
/*     */       {
/* 413 */         return 0.0F;
/*     */       }
/*     */       
/* 416 */       if (!Matches.biome(biomegenbase, this.biomes))
/*     */       {
/* 418 */         return 0.0F;
/*     */       }
/*     */     } 
/*     */     
/* 422 */     return (this.heights != null && !this.heights.isInRange(blockpos.getY())) ? 0.0F : 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float getWeatherBrightness(float rainStrength, float thunderStrength) {
/* 428 */     float f = 1.0F - rainStrength;
/* 429 */     float f1 = rainStrength - thunderStrength;
/* 430 */     float f2 = 0.0F;
/*     */     
/* 432 */     if (this.weatherClear)
/*     */     {
/* 434 */       f2 += f;
/*     */     }
/*     */     
/* 437 */     if (this.weatherRain)
/*     */     {
/* 439 */       f2 += f1;
/*     */     }
/*     */     
/* 442 */     if (this.weatherThunder)
/*     */     {
/* 444 */       f2 += thunderStrength;
/*     */     }
/*     */     
/* 447 */     f2 = NumUtils.limit(f2, 0.0F, 1.0F);
/* 448 */     return f2;
/*     */   }
/*     */ 
/*     */   
/*     */   private float getFadeBrightness(int timeOfDay) {
/* 453 */     if (timeBetween(timeOfDay, this.startFadeIn, this.endFadeIn)) {
/*     */       
/* 455 */       int k = normalizeTime(this.endFadeIn - this.startFadeIn);
/* 456 */       int l = normalizeTime(timeOfDay - this.startFadeIn);
/* 457 */       return l / k;
/*     */     } 
/* 459 */     if (timeBetween(timeOfDay, this.endFadeIn, this.startFadeOut))
/*     */     {
/* 461 */       return 1.0F;
/*     */     }
/* 463 */     if (timeBetween(timeOfDay, this.startFadeOut, this.endFadeOut)) {
/*     */       
/* 465 */       int i = normalizeTime(this.endFadeOut - this.startFadeOut);
/* 466 */       int j = normalizeTime(timeOfDay - this.startFadeOut);
/* 467 */       return 1.0F - j / i;
/*     */     } 
/*     */ 
/*     */     
/* 471 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderSide(Tessellator tess, int side) {
/* 477 */     WorldRenderer worldrenderer = tess.getWorldRenderer();
/* 478 */     double d0 = (side % 3) / 3.0D;
/* 479 */     double d1 = (side / 3) / 2.0D;
/* 480 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 481 */     worldrenderer.pos(-100.0D, -100.0D, -100.0D).tex(d0, d1).endVertex();
/* 482 */     worldrenderer.pos(-100.0D, -100.0D, 100.0D).tex(d0, d1 + 0.5D).endVertex();
/* 483 */     worldrenderer.pos(100.0D, -100.0D, 100.0D).tex(d0 + 0.3333333333333333D, d1 + 0.5D).endVertex();
/* 484 */     worldrenderer.pos(100.0D, -100.0D, -100.0D).tex(d0 + 0.3333333333333333D, d1).endVertex();
/* 485 */     tess.draw();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive(World world, int timeOfDay) {
/* 490 */     if (world != this.lastWorld) {
/*     */       
/* 492 */       this.lastWorld = world;
/* 493 */       this.smoothPositionBrightness = null;
/*     */     } 
/*     */     
/* 496 */     if (timeBetween(timeOfDay, this.endFadeOut, this.startFadeIn))
/*     */     {
/* 498 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 502 */     if (this.days != null) {
/*     */       
/* 504 */       long i = world.getWorldTime();
/*     */       
/*     */       long j;
/* 507 */       for (j = i - this.startFadeIn; j < 0L; j += (24000 * this.daysLoop));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 512 */       int k = (int)(j / 24000L);
/* 513 */       int l = k % this.daysLoop;
/*     */       
/* 515 */       if (!this.days.isInRange(l))
/*     */       {
/* 517 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 521 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean timeBetween(int timeOfDay, int timeStart, int timeEnd) {
/* 527 */     return (timeStart <= timeEnd) ? ((timeOfDay >= timeStart && timeOfDay <= timeEnd)) : ((timeOfDay >= timeStart || timeOfDay <= timeEnd));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 532 */     return this.source + ", " + this.startFadeIn + "-" + this.endFadeIn + " " + this.startFadeOut + "-" + this.endFadeOut;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\CustomSkyLayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */