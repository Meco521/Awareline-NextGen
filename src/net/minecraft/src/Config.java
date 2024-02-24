/*      */ package net.minecraft.src;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.lang.reflect.Array;
/*      */ import java.net.URI;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*      */ import net.minecraft.client.resources.DefaultResourcePack;
/*      */ import net.minecraft.client.resources.IResource;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourcePack;
/*      */ import net.minecraft.client.resources.ResourcePackRepository;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.FrameTimer;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Util;
/*      */ import net.optifine.VersionCheckThread;
/*      */ import net.optifine.config.GlVersion;
/*      */ import net.optifine.gui.GuiMessage;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.shaders.Shaders;
/*      */ import net.optifine.util.DisplayModeComparator;
/*      */ import net.optifine.util.PropertiesOrdered;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ import org.lwjgl.LWJGLException;
/*      */ import org.lwjgl.Sys;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.DisplayMode;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import org.lwjgl.opengl.PixelFormat;
/*      */ 
/*      */ public class Config {
/*      */   public static final String VERSION = "OptiFine_1.8.9_HD_U_M6_pre1";
/*      */   private static String build;
/*      */   private static String newRelease;
/*      */   private static boolean notify64BitJava;
/*      */   public static String openGlVersion;
/*   64 */   public static int minecraftVersionInt = -1; public static String openGlRenderer; public static String openGlVendor; public static String[] openGlExtensions; public static GlVersion glVersion; public static GlVersion glslVersion;
/*      */   public static boolean fancyFogAvailable;
/*      */   public static boolean occlusionAvailable;
/*      */   private static GameSettings gameSettings;
/*   68 */   private static final Minecraft minecraft = Minecraft.getMinecraft();
/*      */   private static boolean initialized;
/*      */   private static Thread minecraftThread;
/*      */   private static DisplayMode desktopDisplayMode;
/*      */   private static DisplayMode[] displayModes;
/*      */   private static int antialiasingLevel;
/*      */   private static int availableProcessors;
/*      */   public static boolean zoomMode;
/*      */   public static boolean zoomSmoothCamera;
/*      */   private static int texturePackClouds;
/*      */   public static boolean waterOpacityChanged;
/*      */   private static boolean fullscreenModeChecked;
/*      */   private static boolean desktopModeChecked;
/*      */   private static DefaultResourcePack defaultResourcePackLazy;
/*   82 */   private static final Logger LOGGER = LogManager.getLogger();
/*   83 */   public static final boolean logDetail = System.getProperty("log.detail", "false").equals("true");
/*      */   
/*      */   private static String mcDebugLast;
/*      */   private static int fpsMinLast;
/*      */   public static float renderPartialTicks;
/*      */   
/*      */   public static String getVersion() {
/*   90 */     return "OptiFine_1.8.9_HD_U_M6_pre1";
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getVersionDebug() {
/*   95 */     StringBuilder stringbuffer = new StringBuilder(32);
/*      */     
/*   97 */     if (isDynamicLights()) {
/*      */       
/*   99 */       stringbuffer.append("DL: ");
/*  100 */       stringbuffer.append(DynamicLights.getCount());
/*  101 */       stringbuffer.append(", ");
/*      */     } 
/*      */     
/*  104 */     stringbuffer.append("OptiFine_1.8.9_HD_U_M6_pre1");
/*  105 */     String s = Shaders.getShaderPackName();
/*      */     
/*  107 */     if (s != null) {
/*      */       
/*  109 */       stringbuffer.append(", ");
/*  110 */       stringbuffer.append(s);
/*      */     } 
/*      */     
/*  113 */     return stringbuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void initGameSettings(GameSettings p_initGameSettings_0_) {
/*  118 */     if (gameSettings == null) {
/*      */       
/*  120 */       gameSettings = p_initGameSettings_0_;
/*  121 */       desktopDisplayMode = Display.getDesktopDisplayMode();
/*  122 */       updateAvailableProcessors();
/*  123 */       ReflectorForge.putLaunchBlackboard("optifine.ForgeSplashCompatible", Boolean.TRUE);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void initDisplay() {
/*  129 */     checkInitialized();
/*  130 */     antialiasingLevel = gameSettings.ofAaLevel;
/*  131 */     checkDisplaySettings();
/*  132 */     checkDisplayMode();
/*  133 */     minecraftThread = Thread.currentThread();
/*  134 */     updateThreadPriorities();
/*  135 */     Shaders.startup(Minecraft.getMinecraft());
/*      */   }
/*      */ 
/*      */   
/*      */   public static void checkInitialized() {
/*  140 */     if (!initialized)
/*      */     {
/*  142 */       if (Display.isCreated()) {
/*      */         
/*  144 */         initialized = true;
/*  145 */         checkOpenGlCaps();
/*  146 */         startVersionCheckThread();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void checkOpenGlCaps() {
/*  153 */     log("");
/*  154 */     log(getVersion());
/*  155 */     log("Build: " + getBuild());
/*  156 */     log("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
/*  157 */     log("Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
/*  158 */     log("VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"));
/*  159 */     log("LWJGL: " + Sys.getVersion());
/*  160 */     openGlVersion = GL11.glGetString(7938);
/*  161 */     openGlRenderer = GL11.glGetString(7937);
/*  162 */     openGlVendor = GL11.glGetString(7936);
/*  163 */     log("OpenGL: " + openGlRenderer + ", version " + openGlVersion + ", " + openGlVendor);
/*  164 */     log("OpenGL Version: " + getOpenGlVersionString());
/*      */     
/*  166 */     if (!(GLContext.getCapabilities()).OpenGL12)
/*      */     {
/*  168 */       log("OpenGL Mipmap levels: Not available (GL12.GL_TEXTURE_MAX_LEVEL)");
/*      */     }
/*      */     
/*  171 */     fancyFogAvailable = (GLContext.getCapabilities()).GL_NV_fog_distance;
/*      */     
/*  173 */     if (!fancyFogAvailable)
/*      */     {
/*  175 */       log("OpenGL Fancy fog: Not available (GL_NV_fog_distance)");
/*      */     }
/*      */     
/*  178 */     occlusionAvailable = (GLContext.getCapabilities()).GL_ARB_occlusion_query;
/*      */     
/*  180 */     if (!occlusionAvailable)
/*      */     {
/*  182 */       log("OpenGL Occlussion culling: Not available (GL_ARB_occlusion_query)");
/*      */     }
/*      */     
/*  185 */     int i = TextureUtils.getGLMaximumTextureSize();
/*  186 */     dbg("Maximum texture size: " + i + "x" + i);
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getBuild() {
/*  191 */     if (build == null) {
/*      */       
/*      */       try {
/*      */         
/*  195 */         InputStream inputstream = Config.class.getResourceAsStream("/buildof.txt");
/*      */         
/*  197 */         if (inputstream == null)
/*      */         {
/*  199 */           return null;
/*      */         }
/*      */         
/*  202 */         build = readLines(inputstream)[0];
/*      */       }
/*  204 */       catch (Exception exception) {
/*      */         
/*  206 */         warn(exception.getClass().getName() + ": " + exception.getMessage());
/*  207 */         build = "";
/*      */       } 
/*      */     }
/*      */     
/*  211 */     return build;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFancyFogAvailable() {
/*  216 */     return fancyFogAvailable;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getMinecraftVersionInt() {
/*  221 */     if (minecraftVersionInt < 0) {
/*      */       
/*  223 */       String[] astring = tokenize("1.8.9", ".");
/*  224 */       int i = 0;
/*      */       
/*  226 */       if (astring.length > 0)
/*      */       {
/*  228 */         i += 10000 * parseInt(astring[0], 0);
/*      */       }
/*      */       
/*  231 */       if (astring.length > 1)
/*      */       {
/*  233 */         i += 100 * parseInt(astring[1], 0);
/*      */       }
/*      */       
/*  236 */       if (astring.length > 2)
/*      */       {
/*  238 */         i += parseInt(astring[2], 0);
/*      */       }
/*      */       
/*  241 */       minecraftVersionInt = i;
/*      */     } 
/*      */     
/*  244 */     return minecraftVersionInt;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getOpenGlVersionString() {
/*  249 */     GlVersion glversion = getGlVersion();
/*  250 */     return glversion.getMajor() + "." + glversion.getMinor() + "." + glversion.getRelease();
/*      */   }
/*      */ 
/*      */   
/*      */   private static GlVersion getGlVersionLwjgl() {
/*  255 */     return (GLContext.getCapabilities()).OpenGL44 ? new GlVersion(4, 4) : ((GLContext.getCapabilities()).OpenGL43 ? new GlVersion(4, 3) : ((GLContext.getCapabilities()).OpenGL42 ? new GlVersion(4, 2) : ((GLContext.getCapabilities()).OpenGL41 ? new GlVersion(4, 1) : ((GLContext.getCapabilities()).OpenGL40 ? new GlVersion(4, 0) : ((GLContext.getCapabilities()).OpenGL33 ? new GlVersion(3, 3) : ((GLContext.getCapabilities()).OpenGL32 ? new GlVersion(3, 2) : ((GLContext.getCapabilities()).OpenGL31 ? new GlVersion(3, 1) : ((GLContext.getCapabilities()).OpenGL30 ? new GlVersion(3, 0) : ((GLContext.getCapabilities()).OpenGL21 ? new GlVersion(2, 1) : ((GLContext.getCapabilities()).OpenGL20 ? new GlVersion(2, 0) : ((GLContext.getCapabilities()).OpenGL15 ? new GlVersion(1, 5) : ((GLContext.getCapabilities()).OpenGL14 ? new GlVersion(1, 4) : ((GLContext.getCapabilities()).OpenGL13 ? new GlVersion(1, 3) : ((GLContext.getCapabilities()).OpenGL12 ? new GlVersion(1, 2) : ((GLContext.getCapabilities()).OpenGL11 ? new GlVersion(1, 1) : new GlVersion(1, 0))))))))))))))));
/*      */   }
/*      */ 
/*      */   
/*      */   public static GlVersion getGlVersion() {
/*  260 */     if (glVersion == null) {
/*      */       
/*  262 */       String s = GL11.glGetString(7938);
/*  263 */       glVersion = parseGlVersion(s, null);
/*      */       
/*  265 */       if (glVersion == null)
/*      */       {
/*  267 */         glVersion = getGlVersionLwjgl();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  272 */     return glVersion;
/*      */   }
/*      */ 
/*      */   
/*      */   public static GlVersion getGlslVersion() {
/*  277 */     if (glslVersion == null) {
/*      */       
/*  279 */       String s = GL11.glGetString(35724);
/*  280 */       glslVersion = parseGlVersion(s, null);
/*      */       
/*  282 */       if (glslVersion == null)
/*      */       {
/*  284 */         glslVersion = new GlVersion(1, 10);
/*      */       }
/*      */     } 
/*      */     
/*  288 */     return glslVersion;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static GlVersion parseGlVersion(String p_parseGlVersion_0_, GlVersion p_parseGlVersion_1_) {
/*      */     try {
/*  295 */       if (p_parseGlVersion_0_ == null)
/*      */       {
/*  297 */         return p_parseGlVersion_1_;
/*      */       }
/*      */ 
/*      */       
/*  301 */       Pattern pattern = Pattern.compile("([0-9]+)\\.([0-9]+)(\\.([0-9]+))?(.+)?");
/*  302 */       Matcher matcher = pattern.matcher(p_parseGlVersion_0_);
/*      */       
/*  304 */       if (!matcher.matches())
/*      */       {
/*  306 */         return p_parseGlVersion_1_;
/*      */       }
/*      */ 
/*      */       
/*  310 */       int i = Integer.parseInt(matcher.group(1));
/*  311 */       int j = Integer.parseInt(matcher.group(2));
/*  312 */       int k = (matcher.group(4) != null) ? Integer.parseInt(matcher.group(4)) : 0;
/*  313 */       String s = matcher.group(5);
/*  314 */       return new GlVersion(i, j, k, s);
/*      */ 
/*      */     
/*      */     }
/*  318 */     catch (Exception exception) {
/*      */       
/*  320 */       exception.printStackTrace();
/*  321 */       return p_parseGlVersion_1_;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static String[] getOpenGlExtensions() {
/*  327 */     if (openGlExtensions == null)
/*      */     {
/*  329 */       openGlExtensions = detectOpenGlExtensions();
/*      */     }
/*      */     
/*  332 */     return openGlExtensions;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] detectOpenGlExtensions() {
/*      */     try {
/*  339 */       GlVersion glversion = getGlVersion();
/*      */       
/*  341 */       if (glversion.getMajor() >= 3) {
/*      */         
/*  343 */         int i = GL11.glGetInteger(33309);
/*      */         
/*  345 */         if (i > 0)
/*      */         {
/*  347 */           String[] astring = new String[i];
/*      */           
/*  349 */           for (int j = 0; j < i; j++)
/*      */           {
/*  351 */             astring[j] = GL30.glGetStringi(7939, j);
/*      */           }
/*      */           
/*  354 */           return astring;
/*      */         }
/*      */       
/*      */       } 
/*  358 */     } catch (Exception exception1) {
/*      */       
/*  360 */       exception1.printStackTrace();
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  365 */       String s = GL11.glGetString(7939);
/*  366 */       return s.split(" ");
/*      */     }
/*  368 */     catch (Exception exception) {
/*      */       
/*  370 */       exception.printStackTrace();
/*  371 */       return new String[0];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateThreadPriorities() {
/*  377 */     updateAvailableProcessors();
/*      */     
/*  379 */     if (isSingleProcessor()) {
/*      */       
/*  381 */       if (isSmoothWorld())
/*      */       {
/*  383 */         minecraftThread.setPriority(10);
/*  384 */         setThreadPriority("Server thread", 1);
/*      */       }
/*      */       else
/*      */       {
/*  388 */         minecraftThread.setPriority(5);
/*  389 */         setThreadPriority("Server thread", 5);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  394 */       minecraftThread.setPriority(10);
/*  395 */       setThreadPriority("Server thread", 5);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setThreadPriority(String p_setThreadPriority_0_, int p_setThreadPriority_1_) {
/*      */     try {
/*  403 */       ThreadGroup threadgroup = Thread.currentThread().getThreadGroup();
/*      */       
/*  405 */       if (threadgroup == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  410 */       int i = threadgroup.activeCount() + 10 << 1;
/*  411 */       Thread[] athread = new Thread[i];
/*  412 */       threadgroup.enumerate(athread, false);
/*      */       
/*  414 */       for (Thread thread : athread) {
/*  415 */         if (thread != null && thread.getName().startsWith(p_setThreadPriority_0_)) {
/*  416 */           thread.setPriority(p_setThreadPriority_1_);
/*      */         }
/*      */       }
/*      */     
/*  420 */     } catch (Throwable throwable) {
/*      */       
/*  422 */       warn(throwable.getClass().getName() + ": " + throwable.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isMinecraftThread() {
/*  428 */     return (Thread.currentThread() == minecraftThread);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void startVersionCheckThread() {
/*  433 */     VersionCheckThread versioncheckthread = new VersionCheckThread();
/*  434 */     versioncheckthread.start();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isMipmaps() {
/*  439 */     return (gameSettings.mipmapLevels > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getMipmapLevels() {
/*  444 */     return gameSettings.mipmapLevels;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getMipmapType() {
/*  449 */     switch (gameSettings.ofMipmapType) {
/*      */       
/*      */       case 0:
/*  452 */         return 9986;
/*      */       
/*      */       case 1:
/*  455 */         return 9986;
/*      */       
/*      */       case 2:
/*  458 */         if (isMultiTexture())
/*      */         {
/*  460 */           return 9985;
/*      */         }
/*      */         
/*  463 */         return 9986;
/*      */       
/*      */       case 3:
/*  466 */         if (isMultiTexture())
/*      */         {
/*  468 */           return 9987;
/*      */         }
/*      */         
/*  471 */         return 9986;
/*      */     } 
/*      */     
/*  474 */     return 9986;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isFogFancy() {
/*  480 */     return (fancyFogAvailable && gameSettings.ofFogType == 2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFogFast() {
/*  485 */     return (gameSettings.ofFogType == 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFogOff() {
/*  490 */     return (gameSettings.ofFogType == 3);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFogOn() {
/*  495 */     return (gameSettings.ofFogType != 3);
/*      */   }
/*      */ 
/*      */   
/*      */   public static float getFogStart() {
/*  500 */     return gameSettings.ofFogStart;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void detail(String p_detail_0_) {
/*  505 */     if (logDetail);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void dbg(String p_dbg_0_) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void warn(String p_warn_0_) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void error(String p_error_0_) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void log(String p_log_0_) {
/*  528 */     dbg(p_log_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getUpdatesPerFrame() {
/*  533 */     return gameSettings.ofChunkUpdates;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRainFancy() {
/*  538 */     return (gameSettings.ofRain == 0) ? gameSettings.fancyGraphics : ((gameSettings.ofRain == 2));
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRainOff() {
/*  543 */     return (gameSettings.ofRain == 3);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isCloudsFancy() {
/*  548 */     return (gameSettings.ofClouds != 0) ? ((gameSettings.ofClouds == 2)) : ((isShaders() && !Shaders.shaderPackClouds.isDefault()) ? Shaders.shaderPackClouds.isFancy() : ((texturePackClouds != 0) ? ((texturePackClouds == 2)) : gameSettings.fancyGraphics));
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isCloudsOff() {
/*  553 */     return (gameSettings.ofClouds != 0) ? ((gameSettings.ofClouds == 3)) : ((isShaders() && !Shaders.shaderPackClouds.isDefault()) ? Shaders.shaderPackClouds.isOff() : ((texturePackClouds == 3)));
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateTexturePackClouds() {
/*  558 */     texturePackClouds = 0;
/*  559 */     IResourceManager iresourcemanager = getResourceManager();
/*      */     
/*  561 */     if (iresourcemanager != null) {
/*      */       
/*      */       try {
/*      */         
/*  565 */         InputStream inputstream = iresourcemanager.getResource(new ResourceLocation("mcpatcher/color.properties")).getInputStream();
/*      */         
/*  567 */         if (inputstream == null) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/*  572 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  573 */         propertiesOrdered.load(inputstream);
/*  574 */         inputstream.close();
/*  575 */         String s = propertiesOrdered.getProperty("clouds");
/*      */         
/*  577 */         if (s == null) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/*  582 */         dbg("Texture pack clouds: " + s);
/*  583 */         s = s.toLowerCase();
/*      */         
/*  585 */         if (s.equals("fast"))
/*      */         {
/*  587 */           texturePackClouds = 1;
/*      */         }
/*      */         
/*  590 */         if (s.equals("fancy"))
/*      */         {
/*  592 */           texturePackClouds = 2;
/*      */         }
/*      */         
/*  595 */         if (s.equals("off"))
/*      */         {
/*  597 */           texturePackClouds = 3;
/*      */         }
/*      */       }
/*  600 */       catch (Exception exception) {}
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ModelManager getModelManager() {
/*  608 */     return (minecraft.getRenderItem()).modelManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isTreesFancy() {
/*  613 */     return (gameSettings.ofTrees == 0) ? gameSettings.fancyGraphics : ((gameSettings.ofTrees != 1));
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isTreesSmart() {
/*  618 */     return (gameSettings.ofTrees == 4);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isDroppedItemsFancy() {
/*  623 */     return (gameSettings.ofDroppedItems == 0) ? gameSettings.fancyGraphics : ((gameSettings.ofDroppedItems == 2));
/*      */   }
/*      */ 
/*      */   
/*      */   public static int limit(int p_limit_0_, int p_limit_1_, int p_limit_2_) {
/*  628 */     return (p_limit_0_ < p_limit_1_) ? p_limit_1_ : ((p_limit_0_ > p_limit_2_) ? p_limit_2_ : p_limit_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static float limit(float p_limit_0_, float p_limit_1_, float p_limit_2_) {
/*  633 */     return (p_limit_0_ < p_limit_1_) ? p_limit_1_ : ((p_limit_0_ > p_limit_2_) ? p_limit_2_ : p_limit_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static double limit(double p_limit_0_, double p_limit_2_, double p_limit_4_) {
/*  638 */     return (p_limit_0_ < p_limit_2_) ? p_limit_2_ : ((p_limit_0_ > p_limit_4_) ? p_limit_4_ : p_limit_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static float limitTo1(float p_limitTo1_0_) {
/*  643 */     return (p_limitTo1_0_ < 0.0F) ? 0.0F : ((p_limitTo1_0_ > 1.0F) ? 1.0F : p_limitTo1_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedWater() {
/*  648 */     return (gameSettings.ofAnimatedWater != 2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedPortal() {
/*  653 */     return gameSettings.ofAnimatedPortal;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedLava() {
/*  658 */     return (gameSettings.ofAnimatedLava != 2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedFire() {
/*  663 */     return gameSettings.ofAnimatedFire;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedRedstone() {
/*  668 */     return gameSettings.ofAnimatedRedstone;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedExplosion() {
/*  673 */     return gameSettings.ofAnimatedExplosion;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedFlame() {
/*  678 */     return gameSettings.ofAnimatedFlame;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedSmoke() {
/*  683 */     return gameSettings.ofAnimatedSmoke;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isVoidParticles() {
/*  688 */     return gameSettings.ofVoidParticles;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isWaterParticles() {
/*  693 */     return gameSettings.ofWaterParticles;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRainSplash() {
/*  698 */     return gameSettings.ofRainSplash;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isPortalParticles() {
/*  703 */     return gameSettings.ofPortalParticles;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isPotionParticles() {
/*  708 */     return gameSettings.ofPotionParticles;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFireworkParticles() {
/*  713 */     return gameSettings.ofFireworkParticles;
/*      */   }
/*      */ 
/*      */   
/*      */   public static float getAmbientOcclusionLevel() {
/*  718 */     return (isShaders() && Shaders.aoLevel >= 0.0F) ? Shaders.aoLevel : gameSettings.ofAoLevel;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String listToString(List p_listToString_0_) {
/*  723 */     return listToString(p_listToString_0_, ", ");
/*      */   }
/*      */ 
/*      */   
/*      */   public static String listToString(List p_listToString_0_, String p_listToString_1_) {
/*  728 */     if (p_listToString_0_ == null)
/*      */     {
/*  730 */       return "";
/*      */     }
/*      */ 
/*      */     
/*  734 */     StringBuilder stringbuffer = new StringBuilder(p_listToString_0_.size() * 5);
/*      */     
/*  736 */     for (int i = 0; i < p_listToString_0_.size(); i++) {
/*      */       
/*  738 */       Object object = p_listToString_0_.get(i);
/*      */       
/*  740 */       if (i > 0)
/*      */       {
/*  742 */         stringbuffer.append(p_listToString_1_);
/*      */       }
/*      */       
/*  745 */       stringbuffer.append(object);
/*      */     } 
/*      */     
/*  748 */     return stringbuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String arrayToString(Object[] p_arrayToString_0_) {
/*  754 */     return arrayToString(p_arrayToString_0_, ", ");
/*      */   }
/*      */ 
/*      */   
/*      */   public static String arrayToString(Object[] p_arrayToString_0_, String p_arrayToString_1_) {
/*  759 */     if (p_arrayToString_0_ == null)
/*      */     {
/*  761 */       return "";
/*      */     }
/*      */ 
/*      */     
/*  765 */     StringBuilder stringbuffer = new StringBuilder(p_arrayToString_0_.length * 5);
/*      */     
/*  767 */     for (int i = 0; i < p_arrayToString_0_.length; i++) {
/*      */       
/*  769 */       Object object = p_arrayToString_0_[i];
/*      */       
/*  771 */       if (i > 0)
/*      */       {
/*  773 */         stringbuffer.append(p_arrayToString_1_);
/*      */       }
/*      */       
/*  776 */       stringbuffer.append(object);
/*      */     } 
/*      */     
/*  779 */     return stringbuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String arrayToString(int[] p_arrayToString_0_) {
/*  785 */     return arrayToString(p_arrayToString_0_, ", ");
/*      */   }
/*      */ 
/*      */   
/*      */   public static String arrayToString(int[] p_arrayToString_0_, String p_arrayToString_1_) {
/*  790 */     if (p_arrayToString_0_ == null)
/*      */     {
/*  792 */       return "";
/*      */     }
/*      */ 
/*      */     
/*  796 */     StringBuilder stringbuffer = new StringBuilder(p_arrayToString_0_.length * 5);
/*      */     
/*  798 */     for (int i = 0; i < p_arrayToString_0_.length; i++) {
/*      */       
/*  800 */       int j = p_arrayToString_0_[i];
/*      */       
/*  802 */       if (i > 0)
/*      */       {
/*  804 */         stringbuffer.append(p_arrayToString_1_);
/*      */       }
/*      */       
/*  807 */       stringbuffer.append(j);
/*      */     } 
/*      */     
/*  810 */     return stringbuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String arrayToString(float[] p_arrayToString_0_) {
/*  816 */     return arrayToString(p_arrayToString_0_, ", ");
/*      */   }
/*      */ 
/*      */   
/*      */   public static String arrayToString(float[] p_arrayToString_0_, String p_arrayToString_1_) {
/*  821 */     if (p_arrayToString_0_ == null)
/*      */     {
/*  823 */       return "";
/*      */     }
/*      */ 
/*      */     
/*  827 */     StringBuilder stringbuffer = new StringBuilder(p_arrayToString_0_.length * 5);
/*      */     
/*  829 */     for (int i = 0; i < p_arrayToString_0_.length; i++) {
/*      */       
/*  831 */       float f = p_arrayToString_0_[i];
/*      */       
/*  833 */       if (i > 0)
/*      */       {
/*  835 */         stringbuffer.append(p_arrayToString_1_);
/*      */       }
/*      */       
/*  838 */       stringbuffer.append(f);
/*      */     } 
/*      */     
/*  841 */     return stringbuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Minecraft getMinecraft() {
/*  847 */     return minecraft;
/*      */   }
/*      */ 
/*      */   
/*      */   public static TextureManager getTextureManager() {
/*  852 */     return minecraft.getTextureManager();
/*      */   }
/*      */ 
/*      */   
/*      */   public static IResourceManager getResourceManager() {
/*  857 */     return minecraft.getResourceManager();
/*      */   }
/*      */ 
/*      */   
/*      */   public static InputStream getResourceStream(ResourceLocation p_getResourceStream_0_) throws IOException {
/*  862 */     return getResourceStream(minecraft.getResourceManager(), p_getResourceStream_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static InputStream getResourceStream(IResourceManager p_getResourceStream_0_, ResourceLocation p_getResourceStream_1_) throws IOException {
/*  867 */     IResource iresource = p_getResourceStream_0_.getResource(p_getResourceStream_1_);
/*  868 */     return (iresource == null) ? null : iresource.getInputStream();
/*      */   }
/*      */ 
/*      */   
/*      */   public static IResource getResource(ResourceLocation p_getResource_0_) throws IOException {
/*  873 */     return minecraft.getResourceManager().getResource(p_getResource_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean hasResource(ResourceLocation p_hasResource_0_) {
/*  878 */     if (p_hasResource_0_ == null)
/*      */     {
/*  880 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  884 */     IResourcePack iresourcepack = getDefiningResourcePack(p_hasResource_0_);
/*  885 */     return (iresourcepack != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean hasResource(IResourceManager p_hasResource_0_, ResourceLocation p_hasResource_1_) {
/*      */     try {
/*  893 */       IResource iresource = p_hasResource_0_.getResource(p_hasResource_1_);
/*  894 */       return (iresource != null);
/*      */     }
/*  896 */     catch (IOException var3) {
/*      */       
/*  898 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static IResourcePack[] getResourcePacks() {
/*  904 */     ResourcePackRepository resourcepackrepository = minecraft.getResourcePackRepository();
/*  905 */     List list = resourcepackrepository.getRepositoryEntries();
/*  906 */     List<IResourcePack> list1 = new ArrayList();
/*      */     
/*  908 */     for (Object e : list) {
/*      */       
/*  910 */       ResourcePackRepository.Entry resourcepackrepository$entry = (ResourcePackRepository.Entry)e;
/*  911 */       list1.add(resourcepackrepository$entry.getResourcePack());
/*      */     } 
/*      */     
/*  914 */     if (resourcepackrepository.getResourcePackInstance() != null)
/*      */     {
/*  916 */       list1.add(resourcepackrepository.getResourcePackInstance());
/*      */     }
/*      */     
/*  919 */     return list1.<IResourcePack>toArray(new IResourcePack[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getResourcePackNames() {
/*  924 */     if (minecraft.getResourcePackRepository() == null)
/*      */     {
/*  926 */       return "";
/*      */     }
/*      */ 
/*      */     
/*  930 */     IResourcePack[] airesourcepack = getResourcePacks();
/*      */     
/*  932 */     if (airesourcepack.length <= 0)
/*      */     {
/*  934 */       return getDefaultResourcePack().getPackName();
/*      */     }
/*      */ 
/*      */     
/*  938 */     String[] astring = new String[airesourcepack.length];
/*      */     
/*  940 */     for (int i = 0; i < airesourcepack.length; i++)
/*      */     {
/*  942 */       astring[i] = airesourcepack[i].getPackName();
/*      */     }
/*      */     
/*  945 */     return arrayToString((Object[])astring);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DefaultResourcePack getDefaultResourcePack() {
/*  952 */     if (defaultResourcePackLazy == null) {
/*      */       
/*  954 */       Minecraft minecraft = Minecraft.getMinecraft();
/*  955 */       defaultResourcePackLazy = (DefaultResourcePack)Reflector.getFieldValue(minecraft, Reflector.Minecraft_defaultResourcePack);
/*      */       
/*  957 */       if (defaultResourcePackLazy == null) {
/*      */         
/*  959 */         ResourcePackRepository resourcepackrepository = minecraft.getResourcePackRepository();
/*      */         
/*  961 */         if (resourcepackrepository != null)
/*      */         {
/*  963 */           defaultResourcePackLazy = (DefaultResourcePack)resourcepackrepository.rprDefaultResourcePack;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  968 */     return defaultResourcePackLazy;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFromDefaultResourcePack(ResourceLocation p_isFromDefaultResourcePack_0_) {
/*  973 */     IResourcePack iresourcepack = getDefiningResourcePack(p_isFromDefaultResourcePack_0_);
/*  974 */     return (iresourcepack == getDefaultResourcePack());
/*      */   }
/*      */ 
/*      */   
/*      */   public static IResourcePack getDefiningResourcePack(ResourceLocation p_getDefiningResourcePack_0_) {
/*  979 */     ResourcePackRepository resourcepackrepository = minecraft.getResourcePackRepository();
/*  980 */     IResourcePack iresourcepack = resourcepackrepository.getResourcePackInstance();
/*      */     
/*  982 */     if (iresourcepack != null && iresourcepack.resourceExists(p_getDefiningResourcePack_0_))
/*      */     {
/*  984 */       return iresourcepack;
/*      */     }
/*      */ 
/*      */     
/*  988 */     List<ResourcePackRepository.Entry> list = resourcepackrepository.repositoryEntries;
/*      */     
/*  990 */     for (int i = list.size() - 1; i >= 0; i--) {
/*      */       
/*  992 */       ResourcePackRepository.Entry resourcepackrepository$entry = list.get(i);
/*  993 */       IResourcePack iresourcepack1 = resourcepackrepository$entry.getResourcePack();
/*      */       
/*  995 */       if (iresourcepack1.resourceExists(p_getDefiningResourcePack_0_))
/*      */       {
/*  997 */         return iresourcepack1;
/*      */       }
/*      */     } 
/*      */     
/* 1001 */     if (getDefaultResourcePack().resourceExists(p_getDefiningResourcePack_0_))
/*      */     {
/* 1003 */       return (IResourcePack)getDefaultResourcePack();
/*      */     }
/*      */ 
/*      */     
/* 1007 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static RenderGlobal getRenderGlobal() {
/* 1014 */     return minecraft.renderGlobal;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isBetterGrass() {
/* 1019 */     return (gameSettings.ofBetterGrass != 3);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isBetterGrassFancy() {
/* 1024 */     return (gameSettings.ofBetterGrass == 2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isWeatherEnabled() {
/* 1029 */     return gameSettings.ofWeather;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSkyEnabled() {
/* 1034 */     return gameSettings.ofSky;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSunMoonEnabled() {
/* 1039 */     return gameSettings.ofSunMoon;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSunTexture() {
/* 1044 */     return (isSunMoonEnabled() && (!isShaders() || Shaders.isSun()));
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isMoonTexture() {
/* 1049 */     return (isSunMoonEnabled() && (!isShaders() || Shaders.isMoon()));
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isVignetteEnabled() {
/* 1054 */     return ((!isShaders() || Shaders.isVignette()) && ((gameSettings.ofVignette == 0) ? gameSettings.fancyGraphics : (gameSettings.ofVignette == 2)));
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isStarsEnabled() {
/* 1059 */     return gameSettings.ofStars;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sleep(long p_sleep_0_) {
/*      */     try {
/* 1066 */       Thread.sleep(p_sleep_0_);
/*      */     }
/* 1068 */     catch (InterruptedException interruptedexception) {
/*      */       
/* 1070 */       interruptedexception.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isTimeDayOnly() {
/* 1076 */     return (gameSettings.ofTime == 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isTimeDefault() {
/* 1081 */     return (gameSettings.ofTime == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isTimeNightOnly() {
/* 1086 */     return (gameSettings.ofTime == 2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isClearWater() {
/* 1091 */     return gameSettings.ofClearWater;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getAnisotropicFilterLevel() {
/* 1096 */     return gameSettings.ofAfLevel;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAnisotropicFiltering() {
/* 1101 */     return (getAnisotropicFilterLevel() > 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getAntialiasingLevel() {
/* 1106 */     return antialiasingLevel;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAntialiasing() {
/* 1111 */     return (antialiasingLevel > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAntialiasingConfigured() {
/* 1116 */     return (gameSettings.ofAaLevel > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isMultiTexture() {
/* 1121 */     return (getAnisotropicFilterLevel() > 1 || antialiasingLevel > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean between(int p_between_0_, int p_between_1_, int p_between_2_) {
/* 1126 */     return (p_between_0_ >= p_between_1_ && p_between_0_ <= p_between_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean between(float p_between_0_, float p_between_1_, float p_between_2_) {
/* 1131 */     return (p_between_0_ >= p_between_1_ && p_between_0_ <= p_between_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isDrippingWaterLava() {
/* 1136 */     return gameSettings.ofDrippingWaterLava;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isBetterSnow() {
/* 1141 */     return gameSettings.ofBetterSnow;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Dimension getFullscreenDimension() {
/* 1146 */     if (desktopDisplayMode == null)
/*      */     {
/* 1148 */       return null;
/*      */     }
/* 1150 */     if (gameSettings == null)
/*      */     {
/* 1152 */       return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
/*      */     }
/*      */ 
/*      */     
/* 1156 */     String s = gameSettings.ofFullscreenMode;
/*      */     
/* 1158 */     if (s.equals("Default"))
/*      */     {
/* 1160 */       return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
/*      */     }
/*      */ 
/*      */     
/* 1164 */     String[] astring = tokenize(s, " x");
/* 1165 */     return (astring.length < 2) ? new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight()) : new Dimension(parseInt(astring[0], -1), parseInt(astring[1], -1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int parseInt(String p_parseInt_0_, int p_parseInt_1_) {
/*      */     try {
/* 1174 */       if (p_parseInt_0_ == null)
/*      */       {
/* 1176 */         return p_parseInt_1_;
/*      */       }
/*      */ 
/*      */       
/* 1180 */       p_parseInt_0_ = p_parseInt_0_.trim();
/* 1181 */       return Integer.parseInt(p_parseInt_0_);
/*      */     
/*      */     }
/* 1184 */     catch (NumberFormatException var3) {
/*      */       
/* 1186 */       return p_parseInt_1_;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static float parseFloat(String p_parseFloat_0_, float p_parseFloat_1_) {
/*      */     try {
/* 1194 */       if (p_parseFloat_0_ == null)
/*      */       {
/* 1196 */         return p_parseFloat_1_;
/*      */       }
/*      */ 
/*      */       
/* 1200 */       p_parseFloat_0_ = p_parseFloat_0_.trim();
/* 1201 */       return Float.parseFloat(p_parseFloat_0_);
/*      */     
/*      */     }
/* 1204 */     catch (NumberFormatException var3) {
/*      */       
/* 1206 */       return p_parseFloat_1_;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean parseBoolean(String p_parseBoolean_0_, boolean p_parseBoolean_1_) {
/*      */     try {
/* 1214 */       if (p_parseBoolean_0_ == null)
/*      */       {
/* 1216 */         return p_parseBoolean_1_;
/*      */       }
/*      */ 
/*      */       
/* 1220 */       p_parseBoolean_0_ = p_parseBoolean_0_.trim();
/* 1221 */       return Boolean.parseBoolean(p_parseBoolean_0_);
/*      */     
/*      */     }
/* 1224 */     catch (NumberFormatException var3) {
/*      */       
/* 1226 */       return p_parseBoolean_1_;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Boolean parseBoolean(String p_parseBoolean_0_, Boolean p_parseBoolean_1_) {
/*      */     try {
/* 1234 */       if (p_parseBoolean_0_ == null)
/*      */       {
/* 1236 */         return p_parseBoolean_1_;
/*      */       }
/*      */ 
/*      */       
/* 1240 */       p_parseBoolean_0_ = p_parseBoolean_0_.trim().toLowerCase();
/* 1241 */       return p_parseBoolean_0_.equals("true") ? Boolean.TRUE : (p_parseBoolean_0_.equals("false") ? Boolean.FALSE : p_parseBoolean_1_);
/*      */     
/*      */     }
/* 1244 */     catch (NumberFormatException var3) {
/*      */       
/* 1246 */       return p_parseBoolean_1_;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static String[] tokenize(String p_tokenize_0_, String p_tokenize_1_) {
/* 1252 */     StringTokenizer stringtokenizer = new StringTokenizer(p_tokenize_0_, p_tokenize_1_);
/* 1253 */     List<String> list = new ArrayList();
/*      */     
/* 1255 */     while (stringtokenizer.hasMoreTokens()) {
/*      */       
/* 1257 */       String s = stringtokenizer.nextToken();
/* 1258 */       list.add(s);
/*      */     } 
/*      */     
/* 1261 */     return list.<String>toArray(new String[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static DisplayMode[] getDisplayModes() {
/* 1266 */     if (displayModes == null) {
/*      */       
/*      */       try {
/*      */         
/* 1270 */         DisplayMode[] adisplaymode = Display.getAvailableDisplayModes();
/* 1271 */         Set<Dimension> set = getDisplayModeDimensions(adisplaymode);
/* 1272 */         List<DisplayMode> list = new ArrayList();
/*      */         
/* 1274 */         for (Dimension dimension : set) {
/*      */           
/* 1276 */           DisplayMode[] adisplaymode1 = getDisplayModes(adisplaymode, dimension);
/* 1277 */           DisplayMode displaymode = getDisplayMode(adisplaymode1, desktopDisplayMode);
/*      */           
/* 1279 */           if (displaymode != null)
/*      */           {
/* 1281 */             list.add(displaymode);
/*      */           }
/*      */         } 
/*      */         
/* 1285 */         DisplayMode[] adisplaymode2 = list.<DisplayMode>toArray(new DisplayMode[0]);
/* 1286 */         Arrays.sort(adisplaymode2, (Comparator<? super DisplayMode>)new DisplayModeComparator());
/* 1287 */         return adisplaymode2;
/*      */       }
/* 1289 */       catch (Exception exception) {
/*      */         
/* 1291 */         exception.printStackTrace();
/* 1292 */         displayModes = new DisplayMode[] { desktopDisplayMode };
/*      */       } 
/*      */     }
/*      */     
/* 1296 */     return displayModes;
/*      */   }
/*      */ 
/*      */   
/*      */   public static DisplayMode getLargestDisplayMode() {
/* 1301 */     DisplayMode[] adisplaymode = getDisplayModes();
/*      */     
/* 1303 */     if (adisplaymode != null && adisplaymode.length >= 1) {
/*      */       
/* 1305 */       DisplayMode displaymode = adisplaymode[adisplaymode.length - 1];
/* 1306 */       return (desktopDisplayMode.getWidth() > displaymode.getWidth()) ? desktopDisplayMode : ((desktopDisplayMode.getWidth() == displaymode.getWidth() && desktopDisplayMode.getHeight() > displaymode.getHeight()) ? desktopDisplayMode : displaymode);
/*      */     } 
/*      */ 
/*      */     
/* 1310 */     return desktopDisplayMode;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Set<Dimension> getDisplayModeDimensions(DisplayMode[] p_getDisplayModeDimensions_0_) {
/* 1316 */     Set<Dimension> set = new HashSet<>();
/*      */     
/* 1318 */     for (DisplayMode displaymode : p_getDisplayModeDimensions_0_) {
/* 1319 */       Dimension dimension = new Dimension(displaymode.getWidth(), displaymode.getHeight());
/* 1320 */       set.add(dimension);
/*      */     } 
/*      */     
/* 1323 */     return set;
/*      */   }
/*      */ 
/*      */   
/*      */   private static DisplayMode[] getDisplayModes(DisplayMode[] p_getDisplayModes_0_, Dimension p_getDisplayModes_1_) {
/* 1328 */     List<DisplayMode> list = new ArrayList();
/*      */     
/* 1330 */     for (DisplayMode displaymode : p_getDisplayModes_0_) {
/* 1331 */       if (displaymode.getWidth() == p_getDisplayModes_1_.getWidth() && displaymode.getHeight() == p_getDisplayModes_1_.getHeight()) {
/* 1332 */         list.add(displaymode);
/*      */       }
/*      */     } 
/*      */     
/* 1336 */     return list.<DisplayMode>toArray(new DisplayMode[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   private static DisplayMode getDisplayMode(DisplayMode[] p_getDisplayMode_0_, DisplayMode p_getDisplayMode_1_) {
/* 1341 */     if (p_getDisplayMode_1_ != null)
/*      */     {
/* 1343 */       for (DisplayMode displaymode : p_getDisplayMode_0_) {
/* 1344 */         if (displaymode.getBitsPerPixel() == p_getDisplayMode_1_.getBitsPerPixel() && displaymode.getFrequency() == p_getDisplayMode_1_.getFrequency()) {
/* 1345 */           return displaymode;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1350 */     if (p_getDisplayMode_0_.length <= 0)
/*      */     {
/* 1352 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1356 */     Arrays.sort(p_getDisplayMode_0_, (Comparator<? super DisplayMode>)new DisplayModeComparator());
/* 1357 */     return p_getDisplayMode_0_[p_getDisplayMode_0_.length - 1];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] getDisplayModeNames() {
/* 1363 */     DisplayMode[] adisplaymode = getDisplayModes();
/* 1364 */     String[] astring = new String[adisplaymode.length];
/*      */     
/* 1366 */     for (int i = 0; i < adisplaymode.length; i++) {
/*      */       
/* 1368 */       DisplayMode displaymode = adisplaymode[i];
/* 1369 */       String s = displaymode.getWidth() + "x" + displaymode.getHeight();
/* 1370 */       astring[i] = s;
/*      */     } 
/*      */     
/* 1373 */     return astring;
/*      */   }
/*      */   
/*      */   public static DisplayMode getDisplayMode(Dimension p_getDisplayMode_0_) {
/* 1377 */     DisplayMode[] adisplaymode = getDisplayModes();
/*      */     
/* 1379 */     for (DisplayMode displaymode : adisplaymode) {
/* 1380 */       if (displaymode.getWidth() == p_getDisplayMode_0_.width && displaymode.getHeight() == p_getDisplayMode_0_.height) {
/* 1381 */         return displaymode;
/*      */       }
/*      */     } 
/*      */     
/* 1385 */     return desktopDisplayMode;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedTerrain() {
/* 1390 */     return gameSettings.ofAnimatedTerrain;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedTextures() {
/* 1395 */     return gameSettings.ofAnimatedTextures;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSwampColors() {
/* 1400 */     return gameSettings.ofSwampColors;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRandomEntities() {
/* 1405 */     return gameSettings.ofRandomEntities;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void checkGlError(String p_checkGlError_0_) {
/* 1410 */     int i = GlStateManager.glGetError();
/*      */     
/* 1412 */     if (i != 0 && GlErrors.isEnabled(i)) {
/*      */       
/* 1414 */       String s = getGlErrorString(i);
/* 1415 */       String s1 = String.format("OpenGL error: %s (%s), at: %s", new Object[] { Integer.valueOf(i), s, p_checkGlError_0_ });
/* 1416 */       error(s1);
/*      */       
/* 1418 */       if (isShowGlErrors() && TimedEvent.isActive("ShowGlError", 10000L)) {
/*      */         
/* 1420 */         String s2 = I18n.format("of.message.openglError", new Object[] { Integer.valueOf(i), s });
/* 1421 */         minecraft.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentText(s2));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSmoothBiomes() {
/* 1428 */     return gameSettings.ofSmoothBiomes;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isCustomColors() {
/* 1433 */     return gameSettings.ofCustomColors;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isCustomSky() {
/* 1438 */     return gameSettings.ofCustomSky;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isCustomFonts() {
/* 1443 */     return gameSettings.ofCustomFonts;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isShowCapes() {
/* 1448 */     return gameSettings.ofShowCapes;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isConnectedTextures() {
/* 1453 */     return (gameSettings.ofConnectedTextures != 3);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isNaturalTextures() {
/* 1458 */     return gameSettings.ofNaturalTextures;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isEmissiveTextures() {
/* 1463 */     return gameSettings.ofEmissiveTextures;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isConnectedTexturesFancy() {
/* 1468 */     return (gameSettings.ofConnectedTextures == 2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFastRender() {
/* 1473 */     return gameSettings.ofFastRender;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isShaders() {
/* 1478 */     return Shaders.shaderPackLoaded;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String[] readLines(File p_readLines_0_) throws IOException {
/* 1483 */     FileInputStream fileinputstream = new FileInputStream(p_readLines_0_);
/* 1484 */     return readLines(fileinputstream);
/*      */   }
/*      */ 
/*      */   
/*      */   public static String[] readLines(InputStream p_readLines_0_) throws IOException {
/* 1489 */     List<String> list = new ArrayList();
/* 1490 */     InputStreamReader inputstreamreader = new InputStreamReader(p_readLines_0_, StandardCharsets.US_ASCII);
/* 1491 */     BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
/*      */ 
/*      */     
/*      */     while (true) {
/* 1495 */       String s = bufferedreader.readLine();
/*      */       
/* 1497 */       if (s == null)
/*      */       {
/* 1499 */         return (String[])list.toArray((Object[])new String[0]);
/*      */       }
/*      */       
/* 1502 */       list.add(s);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static String readInputStream(InputStream p_readInputStream_0_) throws IOException {
/* 1508 */     return readInputStream(p_readInputStream_0_, "ASCII");
/*      */   }
/*      */ 
/*      */   
/*      */   public static String readInputStream(InputStream p_readInputStream_0_, String p_readInputStream_1_) throws IOException {
/* 1513 */     InputStreamReader inputstreamreader = new InputStreamReader(p_readInputStream_0_, p_readInputStream_1_);
/* 1514 */     BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
/* 1515 */     StringBuilder stringbuffer = new StringBuilder();
/*      */ 
/*      */     
/*      */     while (true) {
/* 1519 */       String s = bufferedreader.readLine();
/*      */       
/* 1521 */       if (s == null)
/*      */       {
/* 1523 */         return stringbuffer.toString();
/*      */       }
/*      */       
/* 1526 */       stringbuffer.append(s);
/* 1527 */       stringbuffer.append("\n");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static byte[] readAll(InputStream p_readAll_0_) throws IOException {
/* 1533 */     ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
/* 1534 */     byte[] abyte = new byte[1024];
/*      */ 
/*      */     
/*      */     while (true) {
/* 1538 */       int i = p_readAll_0_.read(abyte);
/*      */       
/* 1540 */       if (i < 0) {
/*      */         
/* 1542 */         p_readAll_0_.close();
/* 1543 */         return bytearrayoutputstream.toByteArray();
/*      */       } 
/*      */       
/* 1546 */       bytearrayoutputstream.write(abyte, 0, i);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static GameSettings getGameSettings() {
/* 1552 */     return gameSettings;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getNewRelease() {
/* 1557 */     return newRelease;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setNewRelease(String p_setNewRelease_0_) {
/* 1562 */     newRelease = p_setNewRelease_0_;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int compareRelease(String p_compareRelease_0_, String p_compareRelease_1_) {
/* 1567 */     String[] astring = splitRelease(p_compareRelease_0_);
/* 1568 */     String[] astring1 = splitRelease(p_compareRelease_1_);
/* 1569 */     String s = astring[0];
/* 1570 */     String s1 = astring1[0];
/*      */     
/* 1572 */     if (!s.equals(s1))
/*      */     {
/* 1574 */       return s.compareTo(s1);
/*      */     }
/*      */ 
/*      */     
/* 1578 */     int i = parseInt(astring[1], -1);
/* 1579 */     int j = parseInt(astring1[1], -1);
/*      */     
/* 1581 */     if (i != j)
/*      */     {
/* 1583 */       return i - j;
/*      */     }
/*      */ 
/*      */     
/* 1587 */     String s2 = astring[2];
/* 1588 */     String s3 = astring1[2];
/*      */     
/* 1590 */     if (!s2.equals(s3)) {
/*      */       
/* 1592 */       if (s2.isEmpty())
/*      */       {
/* 1594 */         return 1;
/*      */       }
/*      */       
/* 1597 */       if (s3.isEmpty())
/*      */       {
/* 1599 */         return -1;
/*      */       }
/*      */     } 
/*      */     
/* 1603 */     return s2.compareTo(s3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] splitRelease(String p_splitRelease_0_) {
/* 1610 */     if (p_splitRelease_0_ != null && !p_splitRelease_0_.isEmpty()) {
/*      */       
/* 1612 */       Pattern pattern = Pattern.compile("([A-Z])([0-9]+)(.*)");
/* 1613 */       Matcher matcher = pattern.matcher(p_splitRelease_0_);
/*      */       
/* 1615 */       if (!matcher.matches())
/*      */       {
/* 1617 */         return new String[] { "", "", "" };
/*      */       }
/*      */ 
/*      */       
/* 1621 */       String s = normalize(matcher.group(1));
/* 1622 */       String s1 = normalize(matcher.group(2));
/* 1623 */       String s2 = normalize(matcher.group(3));
/* 1624 */       return new String[] { s, s1, s2 };
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1629 */     return new String[] { "", "", "" };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int intHash(int p_intHash_0_) {
/* 1635 */     p_intHash_0_ = p_intHash_0_ ^ 0x3D ^ p_intHash_0_ >> 16;
/* 1636 */     p_intHash_0_ += p_intHash_0_ << 3;
/* 1637 */     p_intHash_0_ ^= p_intHash_0_ >> 4;
/* 1638 */     p_intHash_0_ *= 668265261;
/* 1639 */     p_intHash_0_ ^= p_intHash_0_ >> 15;
/* 1640 */     return p_intHash_0_;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getRandom(BlockPos p_getRandom_0_, int p_getRandom_1_) {
/* 1645 */     int i = intHash(p_getRandom_1_ + 37);
/* 1646 */     i = intHash(i + p_getRandom_0_.getX());
/* 1647 */     i = intHash(i + p_getRandom_0_.getZ());
/* 1648 */     i = intHash(i + p_getRandom_0_.getY());
/* 1649 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getAvailableProcessors() {
/* 1654 */     return availableProcessors;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateAvailableProcessors() {
/* 1659 */     availableProcessors = Runtime.getRuntime().availableProcessors();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSingleProcessor() {
/* 1664 */     return (availableProcessors <= 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSmoothWorld() {
/* 1669 */     return gameSettings.ofSmoothWorld;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isLazyChunkLoading() {
/* 1674 */     return gameSettings.ofLazyChunkLoading;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isDynamicFov() {
/* 1679 */     return gameSettings.ofDynamicFov;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAlternateBlocks() {
/* 1684 */     return gameSettings.ofAlternateBlocks;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getChunkViewDistance() {
/* 1689 */     if (gameSettings == null)
/*      */     {
/* 1691 */       return 10;
/*      */     }
/*      */ 
/*      */     
/* 1695 */     return gameSettings.renderDistanceChunks;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(Object p_equals_0_, Object p_equals_1_) {
/* 1701 */     return (p_equals_0_ == p_equals_1_ || (p_equals_0_ != null && p_equals_0_.equals(p_equals_1_)));
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean equalsOne(Object p_equalsOne_0_, Object[] p_equalsOne_1_) {
/* 1706 */     if (p_equalsOne_1_ == null)
/*      */     {
/* 1708 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1712 */     for (Object object : p_equalsOne_1_) {
/* 1713 */       if (equals(p_equalsOne_0_, object)) {
/* 1714 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1718 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equalsOne(int p_equalsOne_0_, int[] p_equalsOne_1_) {
/* 1724 */     for (int j : p_equalsOne_1_) {
/* 1725 */       if (j == p_equalsOne_0_) {
/* 1726 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1730 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSameOne(Object p_isSameOne_0_, Object[] p_isSameOne_1_) {
/* 1735 */     if (p_isSameOne_1_ == null)
/*      */     {
/* 1737 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1741 */     for (Object object : p_isSameOne_1_) {
/* 1742 */       if (p_isSameOne_0_ == object) {
/* 1743 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1747 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String normalize(String p_normalize_0_) {
/* 1753 */     return (p_normalize_0_ == null) ? "" : p_normalize_0_;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void checkDisplaySettings() {
/* 1758 */     int i = antialiasingLevel;
/*      */     
/* 1760 */     if (i > 0) {
/*      */       
/* 1762 */       DisplayMode displaymode = Display.getDisplayMode();
/* 1763 */       dbg("FSAA Samples: " + i);
/*      */ 
/*      */       
/*      */       try {
/* 1767 */         Display.destroy();
/* 1768 */         Display.setDisplayMode(displaymode);
/* 1769 */         Display.create((new PixelFormat()).withDepthBits(24).withSamples(i));
/*      */         
/* 1771 */         if (Util.getOSType() == Util.EnumOS.WINDOWS)
/*      */         {
/* 1773 */           Display.setResizable(false);
/* 1774 */           Display.setResizable(true);
/*      */         }
/*      */       
/* 1777 */       } catch (LWJGLException lwjglexception2) {
/*      */         
/* 1779 */         warn("Error setting FSAA: " + i + "x");
/* 1780 */         lwjglexception2.printStackTrace();
/*      */ 
/*      */         
/*      */         try {
/* 1784 */           Display.setDisplayMode(displaymode);
/* 1785 */           Display.create((new PixelFormat()).withDepthBits(24));
/*      */           
/* 1787 */           if (Util.getOSType() == Util.EnumOS.WINDOWS)
/*      */           {
/* 1789 */             Display.setResizable(false);
/* 1790 */             Display.setResizable(true);
/*      */           }
/*      */         
/* 1793 */         } catch (LWJGLException lwjglexception1) {
/*      */           
/* 1795 */           lwjglexception1.printStackTrace();
/*      */ 
/*      */           
/*      */           try {
/* 1799 */             Display.setDisplayMode(displaymode);
/* 1800 */             Display.create();
/*      */             
/* 1802 */             if (Util.getOSType() == Util.EnumOS.WINDOWS)
/*      */             {
/* 1804 */               Display.setResizable(false);
/* 1805 */               Display.setResizable(true);
/*      */             }
/*      */           
/* 1808 */           } catch (LWJGLException lwjglexception) {
/*      */             
/* 1810 */             lwjglexception.printStackTrace();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1815 */       if (!Minecraft.isRunningOnMac && getDefaultResourcePack() != null) {
/*      */         
/* 1817 */         InputStream inputstream = null;
/* 1818 */         InputStream inputstream1 = null;
/*      */ 
/*      */         
/*      */         try {
/* 1822 */           inputstream = getDefaultResourcePack().getInputStreamAssets(new ResourceLocation("icons/icon_16x16.png"));
/* 1823 */           inputstream1 = getDefaultResourcePack().getInputStreamAssets(new ResourceLocation("icons/icon_32x32.png"));
/*      */           
/* 1825 */           if (inputstream != null && inputstream1 != null)
/*      */           {
/* 1827 */             Display.setIcon(new ByteBuffer[] { readIconImage(inputstream), readIconImage(inputstream1) });
/*      */           }
/*      */         }
/* 1830 */         catch (IOException ioexception) {
/*      */           
/* 1832 */           warn("Error setting window icon: " + ioexception.getClass().getName() + ": " + ioexception.getMessage());
/*      */         }
/*      */         finally {
/*      */           
/* 1836 */           IOUtils.closeQuietly(inputstream);
/* 1837 */           IOUtils.closeQuietly(inputstream1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static ByteBuffer readIconImage(InputStream p_readIconImage_0_) throws IOException {
/* 1845 */     BufferedImage bufferedimage = ImageIO.read(p_readIconImage_0_);
/* 1846 */     int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
/* 1847 */     ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
/*      */     
/* 1849 */     for (int i : aint)
/*      */     {
/* 1851 */       bytebuffer.putInt(i << 8 | i >> 24 & 0xFF);
/*      */     }
/*      */     
/* 1854 */     bytebuffer.flip();
/* 1855 */     return bytebuffer;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void checkDisplayMode() {
/*      */     try {
/* 1862 */       if (minecraft.isFullScreen()) {
/*      */         
/* 1864 */         if (fullscreenModeChecked) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 1869 */         fullscreenModeChecked = true;
/* 1870 */         desktopModeChecked = false;
/* 1871 */         DisplayMode displaymode = Display.getDisplayMode();
/* 1872 */         Dimension dimension = getFullscreenDimension();
/*      */         
/* 1874 */         if (dimension == null) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 1879 */         if (displaymode.getWidth() == dimension.width && displaymode.getHeight() == dimension.height) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 1884 */         DisplayMode displaymode1 = getDisplayMode(dimension);
/*      */         
/* 1886 */         if (displaymode1 == null) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 1891 */         Display.setDisplayMode(displaymode1);
/* 1892 */         minecraft.displayWidth = Display.getDisplayMode().getWidth();
/* 1893 */         minecraft.displayHeight = Display.getDisplayMode().getHeight();
/*      */         
/* 1895 */         if (minecraft.displayWidth <= 0)
/*      */         {
/* 1897 */           minecraft.displayWidth = 1;
/*      */         }
/*      */         
/* 1900 */         if (minecraft.displayHeight <= 0)
/*      */         {
/* 1902 */           minecraft.displayHeight = 1;
/*      */         }
/*      */         
/* 1905 */         if (minecraft.currentScreen != null) {
/*      */           
/* 1907 */           ScaledResolution scaledresolution = new ScaledResolution(minecraft);
/* 1908 */           int i = scaledresolution.getScaledWidth();
/* 1909 */           int j = scaledresolution.getScaledHeight();
/* 1910 */           minecraft.currentScreen.setWorldAndResolution(minecraft, i, j);
/*      */         } 
/*      */         
/* 1913 */         updateFramebufferSize();
/* 1914 */         Display.setFullscreen(true);
/* 1915 */         minecraft.gameSettings.updateVSync();
/* 1916 */         GlStateManager.enableTexture2D();
/*      */       }
/*      */       else {
/*      */         
/* 1920 */         if (desktopModeChecked) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 1925 */         desktopModeChecked = true;
/* 1926 */         fullscreenModeChecked = false;
/* 1927 */         minecraft.gameSettings.updateVSync();
/* 1928 */         Display.update();
/* 1929 */         GlStateManager.enableTexture2D();
/*      */         
/* 1931 */         if (Util.getOSType() == Util.EnumOS.WINDOWS)
/*      */         {
/* 1933 */           Display.setResizable(false);
/* 1934 */           Display.setResizable(true);
/*      */         }
/*      */       
/*      */       } 
/* 1938 */     } catch (Exception exception) {
/*      */       
/* 1940 */       exception.printStackTrace();
/* 1941 */       gameSettings.ofFullscreenMode = "Default";
/* 1942 */       gameSettings.saveOfOptions();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateFramebufferSize() {
/* 1948 */     minecraft.getFramebuffer().createBindFramebuffer(minecraft.displayWidth, minecraft.displayHeight);
/*      */     
/* 1950 */     if (minecraft.entityRenderer != null)
/*      */     {
/* 1952 */       minecraft.entityRenderer.updateShaderGroupSize(minecraft.displayWidth, minecraft.displayHeight);
/*      */     }
/*      */     
/* 1955 */     minecraft.loadingScreen = new LoadingScreenRenderer(minecraft);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Object[] addObjectToArray(Object[] p_addObjectToArray_0_, Object p_addObjectToArray_1_) {
/* 1960 */     if (p_addObjectToArray_0_ == null)
/*      */     {
/* 1962 */       throw new NullPointerException("The given array is NULL");
/*      */     }
/*      */ 
/*      */     
/* 1966 */     int i = p_addObjectToArray_0_.length;
/* 1967 */     int j = i + 1;
/* 1968 */     Object[] aobject = (Object[])Array.newInstance(p_addObjectToArray_0_.getClass().getComponentType(), j);
/* 1969 */     System.arraycopy(p_addObjectToArray_0_, 0, aobject, 0, i);
/* 1970 */     aobject[i] = p_addObjectToArray_1_;
/* 1971 */     return aobject;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object[] addObjectToArray(Object[] p_addObjectToArray_0_, Object p_addObjectToArray_1_, int p_addObjectToArray_2_) {
/* 1977 */     List<Object> list = new ArrayList(Arrays.asList(p_addObjectToArray_0_));
/* 1978 */     list.add(p_addObjectToArray_2_, p_addObjectToArray_1_);
/* 1979 */     Object[] aobject = (Object[])Array.newInstance(p_addObjectToArray_0_.getClass().getComponentType(), list.size());
/* 1980 */     return list.toArray(aobject);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Object[] addObjectsToArray(Object[] p_addObjectsToArray_0_, Object[] p_addObjectsToArray_1_) {
/* 1985 */     if (p_addObjectsToArray_0_ == null)
/*      */     {
/* 1987 */       throw new NullPointerException("The given array is NULL");
/*      */     }
/* 1989 */     if (p_addObjectsToArray_1_.length == 0)
/*      */     {
/* 1991 */       return p_addObjectsToArray_0_;
/*      */     }
/*      */ 
/*      */     
/* 1995 */     int i = p_addObjectsToArray_0_.length;
/* 1996 */     int j = i + p_addObjectsToArray_1_.length;
/* 1997 */     Object[] aobject = (Object[])Array.newInstance(p_addObjectsToArray_0_.getClass().getComponentType(), j);
/* 1998 */     System.arraycopy(p_addObjectsToArray_0_, 0, aobject, 0, i);
/* 1999 */     System.arraycopy(p_addObjectsToArray_1_, 0, aobject, i, p_addObjectsToArray_1_.length);
/* 2000 */     return aobject;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object[] removeObjectFromArray(Object[] p_removeObjectFromArray_0_, Object p_removeObjectFromArray_1_) {
/* 2006 */     List list = new ArrayList(Arrays.asList(p_removeObjectFromArray_0_));
/* 2007 */     list.remove(p_removeObjectFromArray_1_);
/* 2008 */     return collectionToArray(list, p_removeObjectFromArray_0_.getClass().getComponentType());
/*      */   }
/*      */ 
/*      */   
/*      */   public static Object[] collectionToArray(Collection p_collectionToArray_0_, Class<?> p_collectionToArray_1_) {
/* 2013 */     if (p_collectionToArray_0_ == null)
/*      */     {
/* 2015 */       return null;
/*      */     }
/* 2017 */     if (p_collectionToArray_1_ == null)
/*      */     {
/* 2019 */       return null;
/*      */     }
/* 2021 */     if (p_collectionToArray_1_.isPrimitive())
/*      */     {
/* 2023 */       throw new IllegalArgumentException("Can not make arrays with primitive elements (int, double), element class: " + p_collectionToArray_1_);
/*      */     }
/*      */ 
/*      */     
/* 2027 */     Object[] aobject = (Object[])Array.newInstance(p_collectionToArray_1_, p_collectionToArray_0_.size());
/* 2028 */     return p_collectionToArray_0_.toArray(aobject);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isCustomItems() {
/* 2034 */     return gameSettings.ofCustomItems;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawFps() {
/* 2039 */     int i = Minecraft.getDebugFPS();
/* 2040 */     String s = getUpdates(minecraft.debug);
/* 2041 */     int j = minecraft.renderGlobal.getCountActiveRenderers();
/* 2042 */     int k = minecraft.renderGlobal.getCountEntitiesRendered();
/* 2043 */     int l = minecraft.renderGlobal.getCountTileEntitiesRendered();
/* 2044 */     String s1 = i + "/" + getFpsMin() + " fps, C: " + j + ", E: " + k + "+" + l + ", U: " + s;
/* 2045 */     minecraft.fontRendererObj.drawString(s1, 2, 2, -2039584);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getFpsMin() {
/* 2050 */     if (minecraft.debug == mcDebugLast)
/*      */     {
/* 2052 */       return fpsMinLast;
/*      */     }
/*      */ 
/*      */     
/* 2056 */     mcDebugLast = minecraft.debug;
/* 2057 */     FrameTimer frametimer = minecraft.getFrameTimer();
/* 2058 */     long[] along = frametimer.getFrames();
/* 2059 */     int i = frametimer.getIndex();
/* 2060 */     int j = frametimer.getLastIndex();
/*      */     
/* 2062 */     if (i == j)
/*      */     {
/* 2064 */       return fpsMinLast;
/*      */     }
/*      */ 
/*      */     
/* 2068 */     int k = Minecraft.getDebugFPS();
/*      */     
/* 2070 */     if (k <= 0)
/*      */     {
/* 2072 */       k = 1;
/*      */     }
/*      */     
/* 2075 */     long i1 = (long)(1.0D / k * 1.0E9D);
/* 2076 */     long j1 = 0L;
/*      */     int k1;
/* 2078 */     for (k1 = MathHelper.normalizeAngle(i - 1, along.length); k1 != j && j1 < 1.0E9D; k1 = MathHelper.normalizeAngle(k1 - 1, along.length)) {
/*      */       
/* 2080 */       long l1 = along[k1];
/*      */       
/* 2082 */       if (l1 > i1)
/*      */       {
/* 2084 */         i1 = l1;
/*      */       }
/*      */       
/* 2087 */       j1 += l1;
/*      */     } 
/*      */     
/* 2090 */     double d0 = i1 / 1.0E9D;
/* 2091 */     fpsMinLast = (int)(1.0D / d0);
/* 2092 */     return fpsMinLast;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getUpdates(String p_getUpdates_0_) {
/* 2099 */     int i = p_getUpdates_0_.indexOf('(');
/*      */     
/* 2101 */     if (i < 0)
/*      */     {
/* 2103 */       return "";
/*      */     }
/*      */ 
/*      */     
/* 2107 */     int j = p_getUpdates_0_.indexOf(' ', i);
/* 2108 */     return (j < 0) ? "" : p_getUpdates_0_.substring(i + 1, j);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getBitsOs() {
/* 2114 */     String s = System.getenv("ProgramFiles(X86)");
/* 2115 */     return (s != null) ? 64 : 32;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getBitsJre() {
/* 2120 */     String[] astring = { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
/*      */     
/* 2122 */     for (String s : astring) {
/* 2123 */       String s1 = System.getProperty(s);
/*      */       
/* 2125 */       if (s1 != null && s1.contains("64")) {
/* 2126 */         return 64;
/*      */       }
/*      */     } 
/*      */     
/* 2130 */     return 32;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isNotify64BitJava() {
/* 2135 */     return notify64BitJava;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setNotify64BitJava(boolean p_setNotify64BitJava_0_) {
/* 2140 */     notify64BitJava = p_setNotify64BitJava_0_;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void showGuiMessage(String p_showGuiMessage_0_, String p_showGuiMessage_1_) {
/* 2145 */     GuiMessage guimessage = new GuiMessage(minecraft.currentScreen, p_showGuiMessage_0_, p_showGuiMessage_1_);
/* 2146 */     minecraft.displayGuiScreen((GuiScreen)guimessage);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int[] addIntToArray(int[] p_addIntToArray_0_, int p_addIntToArray_1_) {
/* 2151 */     return addIntsToArray(p_addIntToArray_0_, new int[] { p_addIntToArray_1_ });
/*      */   }
/*      */ 
/*      */   
/*      */   public static int[] addIntsToArray(int[] p_addIntsToArray_0_, int[] p_addIntsToArray_1_) {
/* 2156 */     if (p_addIntsToArray_0_ != null && p_addIntsToArray_1_ != null) {
/*      */       
/* 2158 */       int i = p_addIntsToArray_0_.length;
/* 2159 */       int j = i + p_addIntsToArray_1_.length;
/* 2160 */       int[] aint = new int[j];
/* 2161 */       System.arraycopy(p_addIntsToArray_0_, 0, aint, 0, i);
/*      */       
/* 2163 */       System.arraycopy(p_addIntsToArray_1_, 0, aint, i, p_addIntsToArray_1_.length);
/*      */       
/* 2165 */       return aint;
/*      */     } 
/*      */ 
/*      */     
/* 2169 */     throw new NullPointerException("The given array is NULL");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DynamicTexture getMojangLogoTexture(DynamicTexture p_getMojangLogoTexture_0_) {
/*      */     try {
/* 2177 */       ResourceLocation resourcelocation = new ResourceLocation("textures/gui/title/mojang.png");
/* 2178 */       InputStream inputstream = getResourceStream(resourcelocation);
/*      */       
/* 2180 */       if (inputstream == null)
/*      */       {
/* 2182 */         return p_getMojangLogoTexture_0_;
/*      */       }
/*      */ 
/*      */       
/* 2186 */       BufferedImage bufferedimage = ImageIO.read(inputstream);
/*      */       
/* 2188 */       if (bufferedimage == null)
/*      */       {
/* 2190 */         return p_getMojangLogoTexture_0_;
/*      */       }
/*      */ 
/*      */       
/* 2194 */       return new DynamicTexture(bufferedimage);
/*      */ 
/*      */     
/*      */     }
/* 2198 */     catch (Exception exception) {
/*      */       
/* 2200 */       warn(exception.getClass().getName() + ": " + exception.getMessage());
/* 2201 */       return p_getMojangLogoTexture_0_;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeFile(File p_writeFile_0_, String p_writeFile_1_) throws IOException {
/* 2207 */     FileOutputStream fileoutputstream = new FileOutputStream(p_writeFile_0_);
/* 2208 */     byte[] abyte = p_writeFile_1_.getBytes(StandardCharsets.US_ASCII);
/* 2209 */     fileoutputstream.write(abyte);
/* 2210 */     fileoutputstream.close();
/*      */   }
/*      */ 
/*      */   
/*      */   public static TextureMap getTextureMap() {
/* 2215 */     return minecraft.getTextureMapBlocks();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isDynamicLights() {
/* 2220 */     return (gameSettings.ofDynamicLights != 3);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isDynamicLightsFast() {
/* 2225 */     return (gameSettings.ofDynamicLights == 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isDynamicHandLight() {
/* 2230 */     return (isDynamicLights() && (!isShaders() || Shaders.isDynamicHandLight()));
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isCustomEntityModels() {
/* 2235 */     return gameSettings.ofCustomEntityModels;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isCustomGuis() {
/* 2240 */     return gameSettings.ofCustomGuis;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getScreenshotSize() {
/* 2245 */     return gameSettings.ofScreenshotSize;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int[] toPrimitive(Integer[] p_toPrimitive_0_) {
/* 2250 */     if (p_toPrimitive_0_ == null)
/*      */     {
/* 2252 */       return null;
/*      */     }
/* 2254 */     if (p_toPrimitive_0_.length == 0)
/*      */     {
/* 2256 */       return new int[0];
/*      */     }
/*      */ 
/*      */     
/* 2260 */     int[] aint = new int[p_toPrimitive_0_.length];
/*      */     
/* 2262 */     for (int i = 0; i < aint.length; i++)
/*      */     {
/* 2264 */       aint[i] = p_toPrimitive_0_[i].intValue();
/*      */     }
/*      */     
/* 2267 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isRenderRegions() {
/* 2273 */     return gameSettings.ofRenderRegions;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isVbo() {
/* 2278 */     return OpenGlHelper.useVbo();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSmoothFps() {
/* 2283 */     return gameSettings.ofSmoothFps;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean openWebLink(URI p_openWebLink_0_) {
/*      */     try {
/* 2290 */       Desktop.getDesktop().browse(p_openWebLink_0_);
/* 2291 */       return true;
/*      */     }
/* 2293 */     catch (Exception exception) {
/*      */       
/* 2295 */       warn("Error opening link: " + p_openWebLink_0_);
/* 2296 */       warn(exception.getClass().getName() + ": " + exception.getMessage());
/* 2297 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isShowGlErrors() {
/* 2303 */     return gameSettings.ofShowGlErrors;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String arrayToString(boolean[] p_arrayToString_0_, String p_arrayToString_1_) {
/* 2308 */     if (p_arrayToString_0_ == null)
/*      */     {
/* 2310 */       return "";
/*      */     }
/*      */ 
/*      */     
/* 2314 */     StringBuilder stringbuffer = new StringBuilder(p_arrayToString_0_.length * 5);
/*      */     
/* 2316 */     for (int i = 0; i < p_arrayToString_0_.length; i++) {
/*      */       
/* 2318 */       boolean flag = p_arrayToString_0_[i];
/*      */       
/* 2320 */       if (i > 0)
/*      */       {
/* 2322 */         stringbuffer.append(p_arrayToString_1_);
/*      */       }
/*      */       
/* 2325 */       stringbuffer.append(flag);
/*      */     } 
/*      */     
/* 2328 */     return stringbuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isIntegratedServerRunning() {
/* 2334 */     return (minecraft.getIntegratedServer() != null && minecraft.isIntegratedServerRunning());
/*      */   }
/*      */ 
/*      */   
/*      */   public static IntBuffer createDirectIntBuffer(int p_createDirectIntBuffer_0_) {
/* 2339 */     return GLAllocation.createDirectByteBuffer(p_createDirectIntBuffer_0_ << 2).asIntBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getGlErrorString(int p_getGlErrorString_0_) {
/* 2344 */     switch (p_getGlErrorString_0_) {
/*      */       
/*      */       case 0:
/* 2347 */         return "No error";
/*      */       
/*      */       case 1280:
/* 2350 */         return "Invalid enum";
/*      */       
/*      */       case 1281:
/* 2353 */         return "Invalid value";
/*      */       
/*      */       case 1282:
/* 2356 */         return "Invalid operation";
/*      */       
/*      */       case 1283:
/* 2359 */         return "Stack overflow";
/*      */       
/*      */       case 1284:
/* 2362 */         return "Stack underflow";
/*      */       
/*      */       case 1285:
/* 2365 */         return "Out of memory";
/*      */       
/*      */       case 1286:
/* 2368 */         return "Invalid framebuffer operation";
/*      */     } 
/*      */     
/* 2371 */     return "Unknown";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isTrue(Boolean p_isTrue_0_) {
/* 2377 */     return (p_isTrue_0_ != null && p_isTrue_0_.booleanValue());
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isQuadsToTriangles() {
/* 2382 */     return (isShaders() && !Shaders.canRenderQuads());
/*      */   }
/*      */ 
/*      */   
/*      */   public static void checkNull(Object p_checkNull_0_, String p_checkNull_1_) throws NullPointerException {
/* 2387 */     if (p_checkNull_0_ == null)
/*      */     {
/* 2389 */       throw new NullPointerException(p_checkNull_1_);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\src\Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */