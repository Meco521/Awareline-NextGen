/*     */ package awareline.main.ui.font.fontmanager.font;
/*     */ import awareline.main.Client;
/*     */ import awareline.main.ui.font.obj.Resource;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontFormatException;
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public final class FontManager {
/*     */   public static final TrueTypeFontDrawer default16;
/*     */   public static final TrueTypeFontDrawer default17;
/*     */   public static final TrueTypeFontDrawer default18;
/*     */   public static final TrueTypeFontDrawer vector16;
/*     */   public static final TrueTypeFontDrawer default32;
/*     */   public static final TrueTypeFontDrawer default64;
/*     */   public static final TrueTypeFontDrawer system14;
/*     */   public static final TrueTypeFontDrawer icon20;
/*     */   public static final MCFontDrawer mcFontDrawer;
/*  31 */   private static final HashMap<String, Font> nativeFonts = new HashMap<>();
/*  32 */   private static final File defaultFontJsonFile = new File(Client.instance.getClientDirectory(), "font_setting.json");
/*     */   
/*     */   private static final String PUHUITI = "regular.ttf";
/*     */   private static final String VECTOR = "Alibaba-PuHuiTi-Medium.ttf";
/*     */   private static final String UNI_FONT = "Alibaba-PuHuiTi-Medium.ttf";
/*     */   private static final FontRecorder defaultFontRecorder;
/*     */   private static final FontRecorder vectorFontRecorder;
/*     */   private static final FontRecorder uniFontRecorder;
/*     */   private static final FontOptions fontOptions;
/*  41 */   private static final Logger logger = LogManager.getLogger("FontManager");
/*     */   
/*     */   static {
/*  44 */     fontOptions = readFontSetting();
/*     */     
/*  46 */     mcFontDrawer = new MCFontDrawer((Minecraft.getMinecraft()).fontRendererObj);
/*     */     
/*  48 */     defaultFontRecorder = createDefaultFontRecorder();
/*  49 */     vectorFontRecorder = createVectorFontRecorder();
/*  50 */     uniFontRecorder = new FontRecorder(getNativeFont("Alibaba-PuHuiTi-Medium.ttf"), false, false, false);
/*  51 */     default16 = createDefaultDrawer(16);
/*     */     
/*  53 */     vector16 = createVectorDrawer(16);
/*  54 */     default17 = createDefaultDrawer(17);
/*  55 */     default18 = createDefaultDrawer(18);
/*  56 */     default32 = createDefaultDrawer(32);
/*  57 */     default64 = createDefaultDrawer(64);
/*  58 */     system14 = createByName("SansSerif", 14, false, false, false);
/*  59 */     icon20 = createByName("SansSerif", 20, true, false, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init() {}
/*     */ 
/*     */   
/*     */   private static Font getNativeFontFromClient(String name) {
/*  67 */     Font font = nativeFonts.get(name);
/*     */     
/*  69 */     if (font == null) {
/*  70 */       Resource resource = new Resource(true, new String[] { "fonts", name });
/*     */       
/*     */       try {
/*  73 */         nativeFonts.put(name, font = createFontByInputStream(resource.openStream()));
/*  74 */       } catch (Throwable e) {
/*  75 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */     
/*  79 */     return font;
/*     */   }
/*     */   private static Font getNativeFont(String name) {
/*  82 */     Font font = nativeFonts.get(name);
/*     */     
/*  84 */     if (font == null) {
/*  85 */       Resource resource = new Resource(new String[] { "fonts", name });
/*     */       
/*     */       try {
/*  88 */         nativeFonts.put(name, font = createFontByInputStream(resource.openStream()));
/*  89 */       } catch (Throwable e) {
/*  90 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     return font;
/*     */   }
/*     */   
/*     */   private static FontOptions readFontSetting() {
/*  98 */     if (defaultFontJsonFile.exists()) {
/*     */       try {
/* 100 */         JsonParser parser = new JsonParser();
/*     */         
/* 102 */         JsonObject object = parser.parse(FileUtils.readFileToString(defaultFontJsonFile)).getAsJsonObject();
/*     */         
/* 104 */         return new FontOptions(object);
/* 105 */       } catch (Throwable e) {
/* 106 */         logger.error("Unable to read font setting file", e);
/*     */       } 
/*     */     }
/*     */     
/* 110 */     writeFontSetting();
/*     */     
/* 112 */     return new FontOptions("", "", false, false, false);
/*     */   }
/*     */   
/*     */   private static void writeFontSetting() {
/*     */     try {
/* 117 */       Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
/*     */       
/* 119 */       JsonObject object = new JsonObject();
/*     */       
/* 121 */       object.addProperty("FontPath", "");
/* 122 */       object.addProperty("FontMode", "Path");
/* 123 */       object.addProperty("AntiAliasing", Boolean.valueOf(true));
/* 124 */       object.addProperty("FractionalMetrics", Boolean.valueOf(true));
/* 125 */       object.addProperty("TextureBlurred", Boolean.valueOf(true));
/*     */       
/* 127 */       FileUtils.writeStringToFile(defaultFontJsonFile, gson.toJson((JsonElement)object), StandardCharsets.UTF_8);
/* 128 */     } catch (Throwable e) {
/* 129 */       logger.error("Unable to write font setting file", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static TrueTypeFontDrawer createDefaultDrawer(int size) {
/* 134 */     return new TrueTypeFontDrawer(new FontRecorder[] { defaultFontRecorder
/* 135 */           .deriveFont(size), uniFontRecorder
/* 136 */           .deriveFont(size) });
/*     */   }
/*     */ 
/*     */   
/*     */   private static TrueTypeFontDrawer createVectorDrawer(int size) {
/* 141 */     return new TrueTypeFontDrawer(new FontRecorder[] { vectorFontRecorder
/* 142 */           .deriveFont(size), uniFontRecorder
/* 143 */           .deriveFont(size) });
/*     */   }
/*     */ 
/*     */   
/*     */   private static TrueTypeFontDrawer createByName(String fontName, int size, boolean antiAliasing, boolean fractionalMetrics, boolean textureBlurred) {
/* 148 */     return new TrueTypeFontDrawer(new FontRecorder[] { new FontRecorder(new Font(fontName, 0, size), antiAliasing, fractionalMetrics, textureBlurred), uniFontRecorder
/*     */           
/* 150 */           .deriveFont(size) });
/*     */   }
/*     */ 
/*     */   
/*     */   private static Font createFontByInputStream(InputStream fontStream) throws IOException, FontFormatException {
/*     */     try {
/* 156 */       return Font.createFont(0, fontStream);
/*     */     } finally {
/* 158 */       IOUtils.closeQuietly(fontStream);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static FontRecorder createDefaultFontRecorder() {
/*     */     
/* 164 */     try { if (StringUtils.isNotEmpty(fontOptions.fontPath))
/* 165 */       { File file; switch (fontOptions.fontMode)
/*     */         { case "Path":
/* 167 */             file = new File(fontOptions.fontPath);
/*     */             
/* 169 */             if (file.exists()) {
/* 170 */               return new FontRecorder(createFontByInputStream(Files.newInputStream(file.toPath(), new java.nio.file.OpenOption[0])), fontOptions.antiAliasing, fontOptions.fractionalMetrics, fontOptions.textureBlurred);
/*     */             }
/* 172 */             logger.warn("Font file not found: " + fontOptions.fontPath);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 190 */             return new FontRecorder(getNativeFont("regular.ttf"), true, true, true);case "Name": return new FontRecorder(new Font(fontOptions.fontPath, 0, 0), fontOptions.antiAliasing, fontOptions.fractionalMetrics, fontOptions.textureBlurred); }  logger.warn("Unknown font mode: {}", new Object[] { fontOptions.fontMode }); }  } catch (Throwable e) { logger.error("Unable to load custom font", e); }  return new FontRecorder(getNativeFont("regular.ttf"), true, true, true);
/*     */   }
/*     */   
/*     */   private static FontRecorder createVectorFontRecorder() {
/*     */     
/* 195 */     try { if (StringUtils.isNotEmpty(fontOptions.fontPath))
/* 196 */       { File file; switch (fontOptions.fontMode)
/*     */         { case "Path":
/* 198 */             file = new File(fontOptions.fontPath);
/*     */             
/* 200 */             if (file.exists()) {
/* 201 */               return new FontRecorder(createFontByInputStream(Files.newInputStream(file.toPath(), new java.nio.file.OpenOption[0])), fontOptions.antiAliasing, fontOptions.fractionalMetrics, fontOptions.textureBlurred);
/*     */             }
/* 203 */             logger.warn("Font file not found: " + fontOptions.fontPath);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 221 */             return new FontRecorder(getNativeFont("Alibaba-PuHuiTi-Medium.ttf"), true, true, true);case "Name": return new FontRecorder(new Font(fontOptions.fontPath, 0, 0), fontOptions.antiAliasing, fontOptions.fractionalMetrics, fontOptions.textureBlurred); }  logger.warn("Unknown font mode: {}", new Object[] { fontOptions.fontMode }); }  } catch (Throwable e) { logger.error("Unable to load custom font", e); }  return new FontRecorder(getNativeFont("Alibaba-PuHuiTi-Medium.ttf"), true, true, true);
/*     */   }
/*     */   
/*     */   private static class FontOptions {
/*     */     final String fontPath;
/*     */     final String fontMode;
/*     */     final boolean antiAliasing;
/*     */     final boolean fractionalMetrics;
/*     */     final boolean textureBlurred;
/*     */     
/*     */     public FontOptions(String fontPath, String fontMode, boolean antiAliasing, boolean fractionalMetrics, boolean textureBlurred) {
/* 232 */       this.fontPath = fontPath;
/* 233 */       this.fontMode = fontMode;
/* 234 */       this.antiAliasing = antiAliasing;
/* 235 */       this.fractionalMetrics = fractionalMetrics;
/* 236 */       this.textureBlurred = textureBlurred;
/*     */     }
/*     */     
/*     */     public FontOptions(JsonObject object) {
/* 240 */       this.fontPath = getString(object, "FontPath");
/* 241 */       this.fontMode = getString(object, "FontMode");
/* 242 */       this.antiAliasing = getBool(object, "AntiAliasing");
/* 243 */       this.fractionalMetrics = getBool(object, "FractionalMetrics");
/* 244 */       this.textureBlurred = getBool(object, "TextureBlurred");
/*     */     }
/*     */     
/*     */     private static String getString(JsonObject object, String s) {
/* 248 */       return object.get(s).getAsString();
/*     */     }
/*     */     
/*     */     private static boolean getBool(JsonObject object, String s) {
/* 252 */       return object.get(s).getAsBoolean();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\fontmanager\font\FontManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */