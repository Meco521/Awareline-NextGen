/*      */ package net.minecraft.client.settings;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.gson.Gson;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileWriter;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.lang.reflect.ParameterizedType;
/*      */ import java.lang.reflect.Type;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.audio.SoundCategory;
/*      */ import net.minecraft.client.gui.GuiNewChat;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.optifine.ClearWater;
/*      */ import net.optifine.CustomColors;
/*      */ import net.optifine.CustomGuis;
/*      */ import net.optifine.CustomSky;
/*      */ import net.optifine.Lang;
/*      */ import net.optifine.NaturalTextures;
/*      */ import net.optifine.RandomEntities;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.shaders.Shaders;
/*      */ import net.optifine.util.KeyUtils;
/*      */ import org.apache.commons.lang3.ArrayUtils;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ import org.lwjgl.input.Mouse;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.DisplayMode;
/*      */ 
/*      */ public class GameSettings {
/*      */   public static final int DEFAULT = 0;
/*      */   public static final int FAST = 1;
/*      */   public static final int FANCY = 2;
/*      */   public static final int OFF = 3;
/*   53 */   private static final Logger logger = LogManager.getLogger(); public static final int SMART = 4; public static final int ANIM_ON = 0; public static final int ANIM_GENERATED = 1; public static final int ANIM_OFF = 2; public static final String DEFAULT_STR = "Default";
/*   54 */   private static final Gson gson = new Gson();
/*   55 */   private static final ParameterizedType typeListString = new ParameterizedType() {
/*      */       public Type[] getActualTypeArguments() {
/*   57 */         return new Type[] { String.class };
/*      */       }
/*      */       
/*      */       public Type getRawType() {
/*   61 */         return List.class;
/*      */       }
/*      */       
/*      */       public Type getOwnerType() {
/*   65 */         return null;
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */   
/*   71 */   private static final String[] GUISCALES = new String[] { "options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large" };
/*   72 */   private static final String[] PARTICLES = new String[] { "options.particles.all", "options.particles.decreased", "options.particles.minimal" };
/*   73 */   private static final String[] AMBIENT_OCCLUSIONS = new String[] { "options.ao.off", "options.ao.min", "options.ao.max" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   78 */   private static final String[] CLOUDS_TYPES = new String[] { "options.off", "options.graphics.fast", "options.graphics.fancy" };
/*   79 */   private static final int[] OF_TREES_VALUES = new int[] { 0, 1, 4, 2 };
/*   80 */   private static final int[] OF_DYNAMIC_LIGHTS = new int[] { 3, 1, 2 };
/*   81 */   private static final String[] KEYS_DYNAMIC_LIGHTS = new String[] { "options.off", "options.graphics.fast", "options.graphics.fancy" };
/*   82 */   private final Set<EnumPlayerModelParts> setModelParts = Sets.newHashSet((Object[])EnumPlayerModelParts.values());
/*   83 */   private final Map<SoundCategory, Float> mapSoundLevels = Maps.newEnumMap(SoundCategory.class);
/*   84 */   public float mouseSensitivity = 0.5F;
/*      */   public boolean invertMouse;
/*   86 */   public int renderDistanceChunks = -1;
/*      */   public boolean viewBobbing = true;
/*      */   public boolean anaglyph;
/*      */   public boolean fboEnable = true;
/*   90 */   public int limitFramerate = 120;
/*      */ 
/*      */ 
/*      */   
/*   94 */   public int clouds = 2;
/*      */ 
/*      */   
/*      */   public boolean fancyGraphics = true;
/*      */   
/*   99 */   public int ambientOcclusion = 2;
/*  100 */   public List<String> resourcePacks = Lists.newArrayList();
/*  101 */   public List<String> incompatibleResourcePacks = Lists.newArrayList();
/*  102 */   public EntityPlayer.EnumChatVisibility chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
/*      */   public boolean chatColours = true;
/*      */   public boolean chatLinks = true;
/*      */   public boolean chatLinksPrompt = true;
/*  106 */   public float chatOpacity = 1.0F;
/*      */   
/*      */   public boolean snooperEnabled = true;
/*      */   
/*      */   public boolean fullScreen;
/*      */   
/*      */   public boolean enableVsync = true;
/*      */   
/*      */   public boolean useVbo = false;
/*      */   
/*      */   public boolean allowBlockAlternatives = true;
/*      */   
/*      */   public boolean reducedDebugInfo = false;
/*      */   public boolean hideServerAddress;
/*      */   public boolean advancedItemTooltips;
/*      */   public boolean pauseOnLostFocus = true;
/*      */   public boolean touchscreen;
/*      */   public int overrideWidth;
/*      */   public int overrideHeight;
/*      */   public boolean heldItemTooltips = true;
/*  126 */   public float chatScale = 1.0F;
/*  127 */   public float chatWidth = 1.0F;
/*  128 */   public float chatHeightUnfocused = 0.44366196F;
/*  129 */   public float chatHeightFocused = 1.0F;
/*      */   public boolean showInventoryAchievementHint = true;
/*  131 */   public int mipmapLevels = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean useNativeTransport = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean entityShadows = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  149 */   public KeyBinding keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
/*  150 */   public KeyBinding keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
/*  151 */   public KeyBinding keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
/*  152 */   public KeyBinding keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
/*  153 */   public KeyBinding keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
/*  154 */   public KeyBinding keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
/*  155 */   public KeyBinding keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.movement");
/*  156 */   public KeyBinding keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
/*  157 */   public KeyBinding keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
/*  158 */   public KeyBinding keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.gameplay");
/*  159 */   public KeyBinding keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
/*  160 */   public KeyBinding keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
/*  161 */   public KeyBinding keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
/*  162 */   public KeyBinding keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
/*  163 */   public KeyBinding keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
/*  164 */   public KeyBinding keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
/*  165 */   public KeyBinding keyBindTogglePerspective = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
/*  166 */   public KeyBinding keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
/*  167 */   public KeyBinding keyBindFullscreen = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
/*  168 */   public KeyBinding keyBindSpectatorOutlines = new KeyBinding("key.spectatorOutlines", 0, "key.categories.misc");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  177 */   public KeyBinding[] keyBindsHotbar = new KeyBinding[] { new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 10, "key.categories.inventory") };
/*      */   
/*      */   public KeyBinding[] keyBindings;
/*      */   
/*      */   public EnumDifficulty difficulty;
/*      */   
/*      */   public boolean hideGUI;
/*      */   
/*      */   public int thirdPersonView;
/*      */   
/*      */   public boolean showDebugInfo;
/*      */   
/*      */   public boolean showDebugProfilerChart;
/*      */   
/*      */   public boolean showLagometer;
/*      */   
/*      */   public String lastServer;
/*      */   
/*      */   public boolean smoothCamera;
/*      */   
/*      */   public boolean debugCamEnable;
/*      */   
/*      */   public float fovSetting;
/*      */   
/*      */   public float gammaSetting;
/*      */   
/*      */   public float saturation;
/*      */   
/*      */   public int guiScale;
/*      */   
/*      */   public int particleSetting;
/*      */   
/*      */   public String language;
/*      */   
/*      */   public boolean forceUnicodeFont;
/*      */   
/*  213 */   public int ofFogType = 1;
/*  214 */   public float ofFogStart = 0.8F;
/*  215 */   public int ofMipmapType = 0;
/*      */   public boolean ofOcclusionFancy = false;
/*      */   public boolean ofSmoothFps = false;
/*  218 */   public boolean ofSmoothWorld = Config.isSingleProcessor();
/*  219 */   public boolean ofLazyChunkLoading = Config.isSingleProcessor();
/*      */   public boolean ofRenderRegions = false;
/*      */   public boolean ofSmartAnimations = false;
/*  222 */   public float ofAoLevel = 1.0F;
/*  223 */   public int ofAaLevel = 0;
/*  224 */   public int ofAfLevel = 1;
/*  225 */   public int ofClouds = 0;
/*  226 */   public float ofCloudsHeight = 0.0F;
/*  227 */   public int ofTrees = 0;
/*  228 */   public int ofRain = 0;
/*  229 */   public int ofDroppedItems = 0;
/*  230 */   public int ofBetterGrass = 3;
/*  231 */   public int ofAutoSaveTicks = 4000;
/*      */   public boolean ofLagometer = false;
/*      */   public boolean ofProfiler = false;
/*      */   public boolean ofShowFps = false;
/*      */   public boolean ofWeather = true;
/*      */   public boolean ofSky = true;
/*      */   public boolean ofStars = true;
/*      */   public boolean ofSunMoon = true;
/*  239 */   public int ofVignette = 0;
/*  240 */   public int ofChunkUpdates = 1;
/*      */   public boolean ofChunkUpdatesDynamic = false;
/*  242 */   public int ofTime = 0;
/*      */   public boolean ofClearWater = false;
/*      */   public boolean ofBetterSnow = false;
/*  245 */   public String ofFullscreenMode = "Default";
/*      */   public boolean ofSwampColors = true;
/*      */   public boolean ofRandomEntities = true;
/*      */   public boolean ofSmoothBiomes = true;
/*      */   public boolean ofCustomFonts = true;
/*      */   public boolean ofCustomColors = true;
/*      */   public boolean ofCustomSky = true;
/*      */   public boolean ofShowCapes = true;
/*  253 */   public int ofConnectedTextures = 2;
/*      */   public boolean ofCustomItems = true;
/*      */   public boolean ofNaturalTextures = false;
/*      */   public boolean ofEmissiveTextures = true;
/*      */   public boolean ofFastMath = false;
/*      */   public boolean ofFastRender = false;
/*  259 */   public int ofTranslucentBlocks = 0;
/*      */   public boolean ofDynamicFov = true;
/*      */   public boolean ofAlternateBlocks = true;
/*  262 */   public int ofDynamicLights = 3;
/*      */   public boolean ofCustomEntityModels = true;
/*      */   public boolean ofCustomGuis = true;
/*      */   public boolean ofShowGlErrors = true;
/*  266 */   public int ofScreenshotSize = 1;
/*  267 */   public int ofAnimatedWater = 0;
/*  268 */   public int ofAnimatedLava = 0;
/*      */   public boolean ofAnimatedFire = true;
/*      */   public boolean ofAnimatedPortal = true;
/*      */   public boolean ofAnimatedRedstone = true;
/*      */   public boolean ofAnimatedExplosion = true;
/*      */   public boolean ofAnimatedFlame = true;
/*      */   public boolean ofAnimatedSmoke = true;
/*      */   public boolean ofVoidParticles = true;
/*      */   public boolean ofWaterParticles = true;
/*      */   public boolean ofRainSplash = true;
/*      */   public boolean ofPortalParticles = true;
/*      */   public boolean ofPotionParticles = true;
/*      */   public boolean ofFireworkParticles = true;
/*      */   public boolean ofDrippingWaterLava = true;
/*      */   public boolean ofAnimatedTerrain = true;
/*      */   public boolean ofAnimatedTextures = true;
/*      */   public KeyBinding ofKeyBindZoom;
/*      */   protected Minecraft mc;
/*      */   private File optionsFile;
/*      */   private File optionsFileOF;
/*      */   
/*      */   public GameSettings(Minecraft mcIn, File optionsFileIn) {
/*  290 */     this.keyBindings = (KeyBinding[])ArrayUtils.addAll((Object[])new KeyBinding[] { this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindSprint, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.keyBindCommand, this.keyBindScreenshot, this.keyBindTogglePerspective, this.keyBindSmoothCamera, this.keyBindFullscreen, this.keyBindSpectatorOutlines }, (Object[])this.keyBindsHotbar);
/*      */     
/*  292 */     this.difficulty = EnumDifficulty.NORMAL;
/*  293 */     this.lastServer = "";
/*  294 */     this.fovSetting = 70.0F;
/*  295 */     this.language = "en_US";
/*  296 */     this.forceUnicodeFont = false;
/*  297 */     this.mc = mcIn;
/*  298 */     this.optionsFile = new File(optionsFileIn, "options.txt");
/*      */     
/*  300 */     if (mcIn.isJava64bit() && Runtime.getRuntime().maxMemory() >= 1000000000L) {
/*  301 */       Options.RENDER_DISTANCE.setValueMax(32.0F);
/*  302 */       long i = 1000000L;
/*      */       
/*  304 */       if (Runtime.getRuntime().maxMemory() >= 1500L * i) {
/*  305 */         Options.RENDER_DISTANCE.setValueMax(48.0F);
/*      */       }
/*      */       
/*  308 */       if (Runtime.getRuntime().maxMemory() >= 2500L * i) {
/*  309 */         Options.RENDER_DISTANCE.setValueMax(64.0F);
/*      */       }
/*      */     } else {
/*  312 */       Options.RENDER_DISTANCE.setValueMax(16.0F);
/*      */     } 
/*      */     
/*  315 */     this.renderDistanceChunks = mcIn.isJava64bit() ? 12 : 8;
/*  316 */     this.optionsFileOF = new File(optionsFileIn, "optionsof.txt");
/*  317 */     this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
/*  318 */     this.ofKeyBindZoom = new KeyBinding("of.key.zoom", 46, "key.categories.misc");
/*  319 */     this.keyBindings = (KeyBinding[])ArrayUtils.add((Object[])this.keyBindings, this.ofKeyBindZoom);
/*  320 */     KeyUtils.fixKeyConflicts(this.keyBindings, new KeyBinding[] { this.ofKeyBindZoom });
/*  321 */     this.renderDistanceChunks = 8;
/*  322 */     loadOptions();
/*  323 */     Config.initGameSettings(this);
/*      */   }
/*      */   
/*      */   public GameSettings() {
/*  327 */     this.keyBindings = (KeyBinding[])ArrayUtils.addAll((Object[])new KeyBinding[] { this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindSprint, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.keyBindCommand, this.keyBindScreenshot, this.keyBindTogglePerspective, this.keyBindSmoothCamera, this.keyBindFullscreen, this.keyBindSpectatorOutlines }, (Object[])this.keyBindsHotbar);
/*      */ 
/*      */     
/*  330 */     this.difficulty = EnumDifficulty.NORMAL;
/*  331 */     this.lastServer = "";
/*  332 */     this.fovSetting = 70.0F;
/*  333 */     this.language = "en_US";
/*  334 */     this.forceUnicodeFont = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getKeyDisplayString(int key) {
/*  343 */     return (key < 0) ? I18n.format("key.mouseButton", new Object[] { Integer.valueOf(key + 101) }) : ((key < 256) ? Keyboard.getKeyName(key) : String.format("%c", new Object[] { Character.valueOf((char)(key - 256)) }).toUpperCase());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isKeyDown(KeyBinding key) {
/*  352 */     return (key.getKeyCode() == 0) ? false : ((key.getKeyCode() < 0) ? Mouse.isButtonDown(key.getKeyCode() + 100) : Keyboard.isKeyDown(key.getKeyCode()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getTranslation(String[] strArray, int index) {
/*  363 */     if (index < 0 || index >= strArray.length) {
/*  364 */       index = 0;
/*      */     }
/*      */     
/*  367 */     return I18n.format(strArray[index], new Object[0]);
/*      */   }
/*      */   
/*      */   private static int nextValue(int p_nextValue_0_, int[] p_nextValue_1_) {
/*  371 */     int i = indexOf(p_nextValue_0_, p_nextValue_1_);
/*      */     
/*  373 */     if (i < 0) {
/*  374 */       return p_nextValue_1_[0];
/*      */     }
/*  376 */     i++;
/*      */     
/*  378 */     if (i >= p_nextValue_1_.length) {
/*  379 */       i = 0;
/*      */     }
/*      */     
/*  382 */     return p_nextValue_1_[i];
/*      */   }
/*      */ 
/*      */   
/*      */   private static int limit(int p_limit_0_, int[] p_limit_1_) {
/*  387 */     int i = indexOf(p_limit_0_, p_limit_1_);
/*  388 */     return (i < 0) ? p_limit_1_[0] : p_limit_0_;
/*      */   }
/*      */   
/*      */   private static int indexOf(int p_indexOf_0_, int[] p_indexOf_1_) {
/*  392 */     for (int i = 0; i < p_indexOf_1_.length; i++) {
/*  393 */       if (p_indexOf_1_[i] == p_indexOf_0_) {
/*  394 */         return i;
/*      */       }
/*      */     } 
/*      */     
/*  398 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOptionKeyBinding(KeyBinding key, int keyCode) {
/*  408 */     key.setKeyCode(keyCode);
/*  409 */     saveOptions();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOptionFloatValue(Options settingsOption, float value) {
/*  419 */     setOptionFloatValueOF(settingsOption, value);
/*      */     
/*  421 */     if (settingsOption == Options.SENSITIVITY) {
/*  422 */       this.mouseSensitivity = value;
/*      */     }
/*      */     
/*  425 */     if (settingsOption == Options.FOV) {
/*  426 */       this.fovSetting = value;
/*      */     }
/*      */     
/*  429 */     if (settingsOption == Options.GAMMA) {
/*  430 */       this.gammaSetting = value;
/*      */     }
/*      */     
/*  433 */     if (settingsOption == Options.FRAMERATE_LIMIT) {
/*  434 */       this.limitFramerate = (int)value;
/*  435 */       this.enableVsync = false;
/*      */       
/*  437 */       if (this.limitFramerate <= 0) {
/*  438 */         this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
/*  439 */         this.enableVsync = true;
/*      */       } 
/*      */       
/*  442 */       updateVSync();
/*      */     } 
/*      */     
/*  445 */     if (settingsOption == Options.CHAT_OPACITY) {
/*  446 */       this.chatOpacity = value;
/*  447 */       this.mc.ingameGUI.getChatGUI().refreshChat();
/*      */     } 
/*      */     
/*  450 */     if (settingsOption == Options.CHAT_HEIGHT_FOCUSED) {
/*  451 */       this.chatHeightFocused = value;
/*  452 */       this.mc.ingameGUI.getChatGUI().refreshChat();
/*      */     } 
/*      */     
/*  455 */     if (settingsOption == Options.CHAT_HEIGHT_UNFOCUSED) {
/*  456 */       this.chatHeightUnfocused = value;
/*  457 */       this.mc.ingameGUI.getChatGUI().refreshChat();
/*      */     } 
/*      */     
/*  460 */     if (settingsOption == Options.CHAT_WIDTH) {
/*  461 */       this.chatWidth = value;
/*  462 */       this.mc.ingameGUI.getChatGUI().refreshChat();
/*      */     } 
/*      */     
/*  465 */     if (settingsOption == Options.CHAT_SCALE) {
/*  466 */       this.chatScale = value;
/*  467 */       this.mc.ingameGUI.getChatGUI().refreshChat();
/*      */     } 
/*      */     
/*  470 */     if (settingsOption == Options.MIPMAP_LEVELS) {
/*  471 */       int i = this.mipmapLevels;
/*  472 */       this.mipmapLevels = (int)value;
/*      */       
/*  474 */       if (i != value) {
/*  475 */         this.mc.getTextureMapBlocks().setMipmapLevels(this.mipmapLevels);
/*  476 */         this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/*  477 */         this.mc.getTextureMapBlocks().setBlurMipmapDirect(false, (this.mipmapLevels > 0));
/*  478 */         this.mc.scheduleResourcesRefresh();
/*      */       } 
/*      */     } 
/*      */     
/*  482 */     if (settingsOption == Options.BLOCK_ALTERNATIVES) {
/*  483 */       this.allowBlockAlternatives = !this.allowBlockAlternatives;
/*  484 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/*  487 */     if (settingsOption == Options.RENDER_DISTANCE) {
/*  488 */       this.renderDistanceChunks = (int)value;
/*  489 */       this.mc.renderGlobal.setDisplayListEntitiesDirty();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOptionValue(Options settingsOption, int value) {
/*  522 */     setOptionValueOF(settingsOption, value);
/*      */     
/*  524 */     if (settingsOption == Options.INVERT_MOUSE) {
/*  525 */       this.invertMouse = !this.invertMouse;
/*      */     }
/*      */     
/*  528 */     if (settingsOption == Options.GUI_SCALE) {
/*  529 */       this.guiScale += value;
/*      */       
/*  531 */       if (GuiScreen.isShiftKeyDown()) {
/*  532 */         this.guiScale = 0;
/*      */       }
/*      */       
/*  535 */       DisplayMode displaymode = Config.getLargestDisplayMode();
/*  536 */       int i = displaymode.getWidth() / 320;
/*  537 */       int j = displaymode.getHeight() / 240;
/*  538 */       int k = Math.min(i, j);
/*      */       
/*  540 */       if (this.guiScale < 0) {
/*  541 */         this.guiScale = k - 1;
/*      */       }
/*      */       
/*  544 */       if (this.mc.isUnicode() && this.guiScale % 2 != 0) {
/*  545 */         this.guiScale += value;
/*      */       }
/*      */       
/*  548 */       if (this.guiScale < 0 || this.guiScale >= k) {
/*  549 */         this.guiScale = 0;
/*      */       }
/*      */     } 
/*      */     
/*  553 */     if (settingsOption == Options.PARTICLES) {
/*  554 */       this.particleSetting = (this.particleSetting + value) % 3;
/*      */     }
/*      */     
/*  557 */     if (settingsOption == Options.VIEW_BOBBING) {
/*  558 */       this.viewBobbing = !this.viewBobbing;
/*      */     }
/*      */     
/*  561 */     if (settingsOption == Options.RENDER_CLOUDS) {
/*  562 */       this.clouds = (this.clouds + value) % 3;
/*      */     }
/*      */     
/*  565 */     if (settingsOption == Options.FORCE_UNICODE_FONT) {
/*  566 */       this.forceUnicodeFont = !this.forceUnicodeFont;
/*  567 */       this.mc.fontRendererObj.setUnicodeFlag((this.mc.getLanguageManager().isCurrentLocaleUnicode() || this.forceUnicodeFont));
/*      */     } 
/*      */     
/*  570 */     if (settingsOption == Options.FBO_ENABLE) {
/*  571 */       this.fboEnable = !this.fboEnable;
/*      */     }
/*      */     
/*  574 */     if (settingsOption == Options.ANAGLYPH) {
/*  575 */       if (!this.anaglyph && Config.isShaders()) {
/*  576 */         Config.showGuiMessage(Lang.get("of.message.an.shaders1"), Lang.get("of.message.an.shaders2"));
/*      */         
/*      */         return;
/*      */       } 
/*  580 */       this.anaglyph = !this.anaglyph;
/*  581 */       this.mc.refreshResources();
/*      */     } 
/*      */     
/*  584 */     if (settingsOption == Options.GRAPHICS) {
/*  585 */       this.fancyGraphics = !this.fancyGraphics;
/*  586 */       updateRenderClouds();
/*  587 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/*  590 */     if (settingsOption == Options.AMBIENT_OCCLUSION) {
/*  591 */       this.ambientOcclusion = (this.ambientOcclusion + value) % 3;
/*  592 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/*  595 */     if (settingsOption == Options.CHAT_VISIBILITY) {
/*  596 */       this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility((this.chatVisibility.getChatVisibility() + value) % 3);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  621 */     if (settingsOption == Options.CHAT_COLOR) {
/*  622 */       this.chatColours = !this.chatColours;
/*      */     }
/*      */     
/*  625 */     if (settingsOption == Options.CHAT_LINKS) {
/*  626 */       this.chatLinks = !this.chatLinks;
/*      */     }
/*      */     
/*  629 */     if (settingsOption == Options.CHAT_LINKS_PROMPT) {
/*  630 */       this.chatLinksPrompt = !this.chatLinksPrompt;
/*      */     }
/*      */     
/*  633 */     if (settingsOption == Options.SNOOPER_ENABLED) {
/*  634 */       this.snooperEnabled = !this.snooperEnabled;
/*      */     }
/*      */     
/*  637 */     if (settingsOption == Options.TOUCHSCREEN) {
/*  638 */       this.touchscreen = !this.touchscreen;
/*      */     }
/*      */     
/*  641 */     if (settingsOption == Options.USE_FULLSCREEN) {
/*  642 */       this.fullScreen = !this.fullScreen;
/*      */       
/*  644 */       if (this.mc.isFullScreen() != this.fullScreen) {
/*  645 */         this.mc.toggleFullscreen();
/*      */       }
/*      */     } 
/*      */     
/*  649 */     if (settingsOption == Options.ENABLE_VSYNC) {
/*  650 */       this.enableVsync = !this.enableVsync;
/*  651 */       Display.setVSyncEnabled(this.enableVsync);
/*      */     } 
/*      */     
/*  654 */     if (settingsOption == Options.USE_VBO) {
/*  655 */       this.useVbo = !this.useVbo;
/*  656 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/*  659 */     if (settingsOption == Options.BLOCK_ALTERNATIVES) {
/*  660 */       this.allowBlockAlternatives = !this.allowBlockAlternatives;
/*  661 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/*  664 */     if (settingsOption == Options.REDUCED_DEBUG_INFO) {
/*  665 */       this.reducedDebugInfo = !this.reducedDebugInfo;
/*      */     }
/*      */     
/*  668 */     if (settingsOption == Options.ENTITY_SHADOWS) {
/*  669 */       this.entityShadows = !this.entityShadows;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  678 */     saveOptions();
/*      */   }
/*      */   
/*      */   public float getOptionFloatValue(Options settingOption) {
/*  682 */     float f = getOptionFloatValueOF(settingOption);
/*  683 */     return (f != Float.MAX_VALUE) ? f : ((settingOption == Options.FOV) ? this.fovSetting : ((settingOption == Options.GAMMA) ? this.gammaSetting : ((settingOption == Options.SATURATION) ? this.saturation : ((settingOption == Options.SENSITIVITY) ? this.mouseSensitivity : ((settingOption == Options.CHAT_OPACITY) ? this.chatOpacity : ((settingOption == Options.CHAT_HEIGHT_FOCUSED) ? this.chatHeightFocused : ((settingOption == Options.CHAT_HEIGHT_UNFOCUSED) ? this.chatHeightUnfocused : ((settingOption == Options.CHAT_SCALE) ? this.chatScale : ((settingOption == Options.CHAT_WIDTH) ? this.chatWidth : ((settingOption == Options.FRAMERATE_LIMIT) ? this.limitFramerate : ((settingOption == Options.MIPMAP_LEVELS) ? this.mipmapLevels : ((settingOption == Options.RENDER_DISTANCE) ? this.renderDistanceChunks : 0.0F))))))))))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOptionOrdinalValue(Options settingOption) {
/*  699 */     switch (settingOption) {
/*      */       case INVERT_MOUSE:
/*  701 */         return this.invertMouse;
/*      */       
/*      */       case VIEW_BOBBING:
/*  704 */         return this.viewBobbing;
/*      */       
/*      */       case ANAGLYPH:
/*  707 */         return this.anaglyph;
/*      */       
/*      */       case FBO_ENABLE:
/*  710 */         return this.fboEnable;
/*      */       
/*      */       case CHAT_COLOR:
/*  713 */         return this.chatColours;
/*      */       
/*      */       case CHAT_LINKS:
/*  716 */         return this.chatLinks;
/*      */       
/*      */       case CHAT_LINKS_PROMPT:
/*  719 */         return this.chatLinksPrompt;
/*      */       
/*      */       case SNOOPER_ENABLED:
/*  722 */         return this.snooperEnabled;
/*      */       
/*      */       case USE_FULLSCREEN:
/*  725 */         return this.fullScreen;
/*      */       
/*      */       case ENABLE_VSYNC:
/*  728 */         return this.enableVsync;
/*      */       
/*      */       case USE_VBO:
/*  731 */         return this.useVbo;
/*      */       
/*      */       case TOUCHSCREEN:
/*  734 */         return this.touchscreen;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case FORCE_UNICODE_FONT:
/*  742 */         return this.forceUnicodeFont;
/*      */       
/*      */       case BLOCK_ALTERNATIVES:
/*  745 */         return this.allowBlockAlternatives;
/*      */       
/*      */       case REDUCED_DEBUG_INFO:
/*  748 */         return this.reducedDebugInfo;
/*      */       
/*      */       case ENTITY_SHADOWS:
/*  751 */         return this.entityShadows;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  759 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getKeyBinding(Options settingOption) {
/*  769 */     String s = getKeyBindingOF(settingOption);
/*      */     
/*  771 */     if (s != null) {
/*  772 */       return s;
/*      */     }
/*  774 */     String s1 = I18n.format(settingOption.getEnumString(), new Object[0]) + ": ";
/*      */     
/*  776 */     if (settingOption.getEnumFloat()) {
/*  777 */       float f1 = getOptionFloatValue(settingOption);
/*  778 */       float f = settingOption.normalizeValue(f1);
/*  779 */       return (settingOption == Options.MIPMAP_LEVELS && f1 >= 4.0D) ? (s1 + Lang.get("of.general.max")) : ((settingOption == Options.SENSITIVITY) ? ((f == 0.0F) ? (s1 + 
/*  780 */         I18n.format("options.sensitivity.min", new Object[0])) : ((f == 1.0F) ? (s1 + I18n.format("options.sensitivity.max", new Object[0])) : (s1 + (int)(f * 200.0F) + "%"))) : ((settingOption == Options.FOV) ? ((f1 == 70.0F) ? (s1 + 
/*  781 */         I18n.format("options.fov.min", new Object[0])) : ((f1 == 110.0F) ? (s1 + I18n.format("options.fov.max", new Object[0])) : (s1 + (int)f1))) : ((settingOption == Options.FRAMERATE_LIMIT) ? ((f1 == settingOption.valueMax) ? (s1 + 
/*  782 */         I18n.format("options.framerateLimit.max", new Object[0])) : (s1 + (int)f1 + " fps")) : ((settingOption == Options.RENDER_CLOUDS) ? ((f1 == settingOption.valueMin) ? (s1 + 
/*  783 */         I18n.format("options.cloudHeight.min", new Object[0])) : (s1 + ((int)f1 + 128))) : ((settingOption == Options.GAMMA) ? ((f == 0.0F) ? (s1 + 
/*  784 */         I18n.format("options.gamma.min", new Object[0])) : ((f == 1.0F) ? (s1 + I18n.format("options.gamma.max", new Object[0])) : (s1 + "+" + (int)(f * 100.0F) + "%"))) : ((settingOption == Options.SATURATION) ? (s1 + (int)(f * 400.0F) + "%") : ((settingOption == Options.CHAT_OPACITY) ? (s1 + (int)(f * 90.0F + 10.0F) + "%") : ((settingOption == Options.CHAT_HEIGHT_UNFOCUSED) ? (s1 + 
/*      */ 
/*      */         
/*  787 */         GuiNewChat.calculateChatboxHeight(f) + "px") : ((settingOption == Options.CHAT_HEIGHT_FOCUSED) ? (s1 + 
/*  788 */         GuiNewChat.calculateChatboxHeight(f) + "px") : ((settingOption == Options.CHAT_WIDTH) ? (s1 + 
/*  789 */         GuiNewChat.calculateChatboxWidth(f) + "px") : ((settingOption == Options.RENDER_DISTANCE) ? (s1 + (int)f1 + " chunks") : ((settingOption == Options.MIPMAP_LEVELS) ? ((f1 == 0.0F) ? (s1 + 
/*      */         
/*  791 */         I18n.format("options.off", new Object[0])) : (s1 + (int)f1)) : ((f == 0.0F) ? (s1 + I18n.format("options.off", new Object[0])) : (s1 + (int)(f * 100.0F) + "%"))))))))))))));
/*  792 */     }  if (settingOption.getEnumBoolean()) {
/*  793 */       boolean flag = getOptionOrdinalValue(settingOption);
/*  794 */       return flag ? (s1 + I18n.format("options.on", new Object[0])) : (s1 + I18n.format("options.off", new Object[0]));
/*  795 */     }  if (settingOption == Options.GUI_SCALE)
/*  796 */       return (this.guiScale >= GUISCALES.length) ? (s1 + this.guiScale + "x") : (s1 + getTranslation(GUISCALES, this.guiScale)); 
/*  797 */     if (settingOption == Options.CHAT_VISIBILITY)
/*  798 */       return s1 + I18n.format(this.chatVisibility.getResourceKey(), new Object[0]); 
/*  799 */     if (settingOption == Options.PARTICLES)
/*  800 */       return s1 + getTranslation(PARTICLES, this.particleSetting); 
/*  801 */     if (settingOption == Options.AMBIENT_OCCLUSION) {
/*  802 */       return s1 + getTranslation(AMBIENT_OCCLUSIONS, this.ambientOcclusion);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  811 */     if (settingOption == Options.RENDER_CLOUDS)
/*  812 */       return s1 + getTranslation(CLOUDS_TYPES, this.clouds); 
/*  813 */     if (settingOption == Options.GRAPHICS) {
/*  814 */       if (this.fancyGraphics) {
/*  815 */         return s1 + I18n.format("options.graphics.fancy", new Object[0]);
/*      */       }
/*  817 */       String s2 = "options.graphics.fast";
/*  818 */       return s1 + I18n.format("options.graphics.fast", new Object[0]);
/*      */     } 
/*      */     
/*  821 */     return s1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadOptions() {
/*  830 */     FileInputStream fileinputstream = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*      */     
/* 1145 */     } catch (Exception exception1) {
/* 1146 */       logger.error("Failed to load options", exception1);
/*      */     } finally {
/*      */       
/* 1149 */       IOUtils.closeQuietly(fileinputstream);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1154 */     loadOfOptions();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float parseFloat(String str) {
/* 1163 */     return str.equals("true") ? 1.0F : (str.equals("false") ? 0.0F : Float.parseFloat(str));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveOptions() {
/* 1170 */     if (Reflector.FMLClientHandler.exists()) {
/* 1171 */       Object object = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
/*      */       
/* 1173 */       if (object != null && Reflector.callBoolean(object, Reflector.FMLClientHandler_isLoading, new Object[0])) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */     
/*      */     try {
/* 1179 */       PrintWriter printwriter = new PrintWriter(new FileWriter(this.optionsFile));
/* 1180 */       printwriter.println("invertYMouse:" + this.invertMouse);
/* 1181 */       printwriter.println("mouseSensitivity:" + this.mouseSensitivity);
/* 1182 */       printwriter.println("fov:" + ((this.fovSetting - 70.0F) / 40.0F));
/* 1183 */       printwriter.println("gamma:" + this.gammaSetting);
/* 1184 */       printwriter.println("saturation:" + this.saturation);
/* 1185 */       printwriter.println("renderDistance:" + this.renderDistanceChunks);
/* 1186 */       printwriter.println("guiScale:" + this.guiScale);
/* 1187 */       printwriter.println("particles:" + this.particleSetting);
/* 1188 */       printwriter.println("bobView:" + this.viewBobbing);
/* 1189 */       printwriter.println("anaglyph3d:" + this.anaglyph);
/* 1190 */       printwriter.println("maxFps:" + this.limitFramerate);
/* 1191 */       printwriter.println("fboEnable:" + this.fboEnable);
/* 1192 */       printwriter.println("difficulty:" + this.difficulty.getDifficultyId());
/* 1193 */       printwriter.println("fancyGraphics:" + this.fancyGraphics);
/* 1194 */       printwriter.println("ao:" + this.ambientOcclusion);
/*      */       
/* 1196 */       switch (this.clouds) {
/*      */         case 0:
/* 1198 */           printwriter.println("renderClouds:false");
/*      */           break;
/*      */         
/*      */         case 1:
/* 1202 */           printwriter.println("renderClouds:fast");
/*      */           break;
/*      */         
/*      */         case 2:
/* 1206 */           printwriter.println("renderClouds:true");
/*      */           break;
/*      */       } 
/* 1209 */       printwriter.println("resourcePacks:" + gson.toJson(this.resourcePacks));
/* 1210 */       printwriter.println("incompatibleResourcePacks:" + gson.toJson(this.incompatibleResourcePacks));
/* 1211 */       printwriter.println("lastServer:" + this.lastServer);
/* 1212 */       printwriter.println("lang:" + this.language);
/* 1213 */       printwriter.println("chatVisibility:" + this.chatVisibility.getChatVisibility());
/* 1214 */       printwriter.println("chatColors:" + this.chatColours);
/* 1215 */       printwriter.println("chatLinks:" + this.chatLinks);
/* 1216 */       printwriter.println("chatLinksPrompt:" + this.chatLinksPrompt);
/* 1217 */       printwriter.println("chatOpacity:" + this.chatOpacity);
/* 1218 */       printwriter.println("snooperEnabled:" + this.snooperEnabled);
/* 1219 */       printwriter.println("fullscreen:" + this.fullScreen);
/* 1220 */       printwriter.println("enableVsync:" + this.enableVsync);
/* 1221 */       printwriter.println("useVbo:" + this.useVbo);
/* 1222 */       printwriter.println("hideServerAddress:" + this.hideServerAddress);
/* 1223 */       printwriter.println("advancedItemTooltips:" + this.advancedItemTooltips);
/* 1224 */       printwriter.println("pauseOnLostFocus:" + this.pauseOnLostFocus);
/* 1225 */       printwriter.println("touchscreen:" + this.touchscreen);
/* 1226 */       printwriter.println("overrideWidth:" + this.overrideWidth);
/* 1227 */       printwriter.println("overrideHeight:" + this.overrideHeight);
/* 1228 */       printwriter.println("heldItemTooltips:" + this.heldItemTooltips);
/* 1229 */       printwriter.println("chatHeightFocused:" + this.chatHeightFocused);
/* 1230 */       printwriter.println("chatHeightUnfocused:" + this.chatHeightUnfocused);
/* 1231 */       printwriter.println("chatScale:" + this.chatScale);
/* 1232 */       printwriter.println("chatWidth:" + this.chatWidth);
/* 1233 */       printwriter.println("showInventoryAchievementHint:" + this.showInventoryAchievementHint);
/* 1234 */       printwriter.println("mipmapLevels:" + this.mipmapLevels);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1249 */       printwriter.println("forceUnicodeFont:" + this.forceUnicodeFont);
/* 1250 */       printwriter.println("allowBlockAlternatives:" + this.allowBlockAlternatives);
/* 1251 */       printwriter.println("reducedDebugInfo:" + this.reducedDebugInfo);
/* 1252 */       printwriter.println("useNativeTransport:" + this.useNativeTransport);
/* 1253 */       printwriter.println("entityShadows:" + this.entityShadows);
/*      */ 
/*      */       
/* 1256 */       for (KeyBinding keybinding : this.keyBindings) {
/* 1257 */         printwriter.println("key_" + keybinding.getKeyDescription() + ":" + keybinding.getKeyCode());
/*      */       }
/*      */       
/* 1260 */       for (SoundCategory soundcategory : SoundCategory.values()) {
/* 1261 */         printwriter.println("soundCategory_" + soundcategory.getCategoryName() + ":" + getSoundLevel(soundcategory));
/*      */       }
/*      */       
/* 1264 */       for (EnumPlayerModelParts enumplayermodelparts : EnumPlayerModelParts.values()) {
/* 1265 */         printwriter.println("modelPart_" + enumplayermodelparts.getPartName() + ":" + this.setModelParts.contains(enumplayermodelparts));
/*      */       }
/*      */       
/* 1268 */       printwriter.close();
/* 1269 */     } catch (Exception exception) {
/* 1270 */       logger.error("Failed to save options", exception);
/*      */     } 
/*      */     
/* 1273 */     saveOfOptions();
/* 1274 */     sendSettingsToServer();
/*      */   }
/*      */   
/*      */   public float getSoundLevel(SoundCategory sndCategory) {
/* 1278 */     return this.mapSoundLevels.containsKey(sndCategory) ? ((Float)this.mapSoundLevels.get(sndCategory)).floatValue() : 1.0F;
/*      */   }
/*      */   
/*      */   public void setSoundLevel(SoundCategory sndCategory, float soundLevel) {
/* 1282 */     this.mc.getSoundHandler().setSoundLevel(sndCategory, soundLevel);
/* 1283 */     this.mapSoundLevels.put(sndCategory, Float.valueOf(soundLevel));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendSettingsToServer() {
/* 1290 */     if (this.mc.thePlayer != null) {
/* 1291 */       int i = 0;
/*      */       
/* 1293 */       for (EnumPlayerModelParts enumplayermodelparts : this.setModelParts) {
/* 1294 */         i |= enumplayermodelparts.getPartMask();
/*      */       }
/*      */       
/* 1297 */       this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C15PacketClientSettings(this.language, this.renderDistanceChunks, this.chatVisibility, this.chatColours, i));
/*      */     } 
/*      */   }
/*      */   
/*      */   public Set<EnumPlayerModelParts> getModelParts() {
/* 1302 */     return (Set<EnumPlayerModelParts>)ImmutableSet.copyOf(this.setModelParts);
/*      */   }
/*      */   
/*      */   public void setModelPartEnabled(EnumPlayerModelParts modelPart, boolean enable) {
/* 1306 */     if (enable) {
/* 1307 */       this.setModelParts.add(modelPart);
/*      */     } else {
/* 1309 */       this.setModelParts.remove(modelPart);
/*      */     } 
/*      */     
/* 1312 */     sendSettingsToServer();
/*      */   }
/*      */   
/*      */   public void switchModelPartEnabled(EnumPlayerModelParts modelPart) {
/* 1316 */     if (!getModelParts().contains(modelPart)) {
/* 1317 */       this.setModelParts.add(modelPart);
/*      */     } else {
/* 1319 */       this.setModelParts.remove(modelPart);
/*      */     } 
/*      */     
/* 1322 */     sendSettingsToServer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int shouldRenderClouds() {
/* 1329 */     return (this.renderDistanceChunks >= 4) ? this.clouds : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUsingNativeTransport() {
/* 1336 */     return this.useNativeTransport;
/*      */   }
/*      */   
/*      */   private void setOptionFloatValueOF(Options p_setOptionFloatValueOF_1_, float p_setOptionFloatValueOF_2_) {
/* 1340 */     if (p_setOptionFloatValueOF_1_ == Options.CLOUD_HEIGHT) {
/* 1341 */       this.ofCloudsHeight = p_setOptionFloatValueOF_2_;
/* 1342 */       this.mc.renderGlobal.resetClouds();
/*      */     } 
/*      */     
/* 1345 */     if (p_setOptionFloatValueOF_1_ == Options.AO_LEVEL) {
/* 1346 */       this.ofAoLevel = p_setOptionFloatValueOF_2_;
/* 1347 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1350 */     if (p_setOptionFloatValueOF_1_ == Options.AA_LEVEL) {
/* 1351 */       int i = (int)p_setOptionFloatValueOF_2_;
/*      */       
/* 1353 */       if (i > 0 && Config.isShaders()) {
/* 1354 */         Config.showGuiMessage(Lang.get("of.message.aa.shaders1"), Lang.get("of.message.aa.shaders2"));
/*      */         
/*      */         return;
/*      */       } 
/* 1358 */       int[] aint = { 0, 2, 4, 6, 8, 12, 16 };
/* 1359 */       this.ofAaLevel = 0;
/*      */       
/* 1361 */       for (int j = 0; j < aint.length; j++) {
/* 1362 */         if (i >= aint[j]) {
/* 1363 */           this.ofAaLevel = aint[j];
/*      */         }
/*      */       } 
/*      */       
/* 1367 */       this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
/*      */     } 
/*      */     
/* 1370 */     if (p_setOptionFloatValueOF_1_ == Options.AF_LEVEL) {
/* 1371 */       int k = (int)p_setOptionFloatValueOF_2_;
/*      */       
/* 1373 */       if (k > 1 && Config.isShaders()) {
/* 1374 */         Config.showGuiMessage(Lang.get("of.message.af.shaders1"), Lang.get("of.message.af.shaders2"));
/*      */         
/*      */         return;
/*      */       } 
/* 1378 */       for (this.ofAfLevel = 1; this.ofAfLevel << 1 <= k; this.ofAfLevel <<= 1);
/*      */ 
/*      */ 
/*      */       
/* 1382 */       this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
/* 1383 */       this.mc.refreshResources();
/*      */     } 
/*      */     
/* 1386 */     if (p_setOptionFloatValueOF_1_ == Options.MIPMAP_TYPE) {
/* 1387 */       int l = (int)p_setOptionFloatValueOF_2_;
/* 1388 */       this.ofMipmapType = Config.limit(l, 0, 3);
/* 1389 */       this.mc.refreshResources();
/*      */     } 
/*      */     
/* 1392 */     if (p_setOptionFloatValueOF_1_ == Options.FULLSCREEN_MODE) {
/* 1393 */       int i1 = (int)p_setOptionFloatValueOF_2_ - 1;
/* 1394 */       String[] astring = Config.getDisplayModeNames();
/*      */       
/* 1396 */       if (i1 < 0 || i1 >= astring.length) {
/* 1397 */         this.ofFullscreenMode = "Default";
/*      */         
/*      */         return;
/*      */       } 
/* 1401 */       this.ofFullscreenMode = astring[i1];
/*      */     } 
/*      */   }
/*      */   
/*      */   private float getOptionFloatValueOF(Options p_getOptionFloatValueOF_1_) {
/* 1406 */     if (p_getOptionFloatValueOF_1_ == Options.CLOUD_HEIGHT)
/* 1407 */       return this.ofCloudsHeight; 
/* 1408 */     if (p_getOptionFloatValueOF_1_ == Options.AO_LEVEL)
/* 1409 */       return this.ofAoLevel; 
/* 1410 */     if (p_getOptionFloatValueOF_1_ == Options.AA_LEVEL)
/* 1411 */       return this.ofAaLevel; 
/* 1412 */     if (p_getOptionFloatValueOF_1_ == Options.AF_LEVEL)
/* 1413 */       return this.ofAfLevel; 
/* 1414 */     if (p_getOptionFloatValueOF_1_ == Options.MIPMAP_TYPE)
/* 1415 */       return this.ofMipmapType; 
/* 1416 */     if (p_getOptionFloatValueOF_1_ == Options.FRAMERATE_LIMIT)
/* 1417 */       return (this.limitFramerate == Options.FRAMERATE_LIMIT.getValueMax() && this.enableVsync) ? 0.0F : this.limitFramerate; 
/* 1418 */     if (p_getOptionFloatValueOF_1_ == Options.FULLSCREEN_MODE) {
/* 1419 */       if (this.ofFullscreenMode.equals("Default")) {
/* 1420 */         return 0.0F;
/*      */       }
/* 1422 */       List<String> list = Arrays.asList(Config.getDisplayModeNames());
/* 1423 */       int i = list.indexOf(this.ofFullscreenMode);
/* 1424 */       return (i < 0) ? 0.0F : (i + 1);
/*      */     } 
/*      */     
/* 1427 */     return Float.MAX_VALUE;
/*      */   }
/*      */ 
/*      */   
/*      */   private void setOptionValueOF(Options p_setOptionValueOF_1_, int p_setOptionValueOF_2_) {
/* 1432 */     if (p_setOptionValueOF_1_ == Options.FOG_FANCY) {
/* 1433 */       switch (this.ofFogType) {
/*      */         case 1:
/* 1435 */           this.ofFogType = 2;
/*      */           
/* 1437 */           if (!Config.isFancyFogAvailable()) {
/* 1438 */             this.ofFogType = 3;
/*      */           }
/*      */           break;
/*      */ 
/*      */         
/*      */         case 2:
/* 1444 */           this.ofFogType = 3;
/*      */           break;
/*      */         
/*      */         case 3:
/* 1448 */           this.ofFogType = 1;
/*      */           break;
/*      */         
/*      */         default:
/* 1452 */           this.ofFogType = 1;
/*      */           break;
/*      */       } 
/*      */     }
/* 1456 */     if (p_setOptionValueOF_1_ == Options.FOG_START) {
/* 1457 */       this.ofFogStart += 0.2F;
/*      */       
/* 1459 */       if (this.ofFogStart > 0.81F) {
/* 1460 */         this.ofFogStart = 0.2F;
/*      */       }
/*      */     } 
/*      */     
/* 1464 */     if (p_setOptionValueOF_1_ == Options.SMOOTH_FPS) {
/* 1465 */       this.ofSmoothFps = !this.ofSmoothFps;
/*      */     }
/*      */     
/* 1468 */     if (p_setOptionValueOF_1_ == Options.SMOOTH_WORLD) {
/* 1469 */       this.ofSmoothWorld = !this.ofSmoothWorld;
/* 1470 */       Config.updateThreadPriorities();
/*      */     } 
/*      */     
/* 1473 */     if (p_setOptionValueOF_1_ == Options.CLOUDS) {
/* 1474 */       this.ofClouds++;
/*      */       
/* 1476 */       if (this.ofClouds > 3) {
/* 1477 */         this.ofClouds = 0;
/*      */       }
/*      */       
/* 1480 */       updateRenderClouds();
/* 1481 */       this.mc.renderGlobal.resetClouds();
/*      */     } 
/*      */     
/* 1484 */     if (p_setOptionValueOF_1_ == Options.TREES) {
/* 1485 */       this.ofTrees = nextValue(this.ofTrees, OF_TREES_VALUES);
/* 1486 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1489 */     if (p_setOptionValueOF_1_ == Options.DROPPED_ITEMS) {
/* 1490 */       this.ofDroppedItems++;
/*      */       
/* 1492 */       if (this.ofDroppedItems > 2) {
/* 1493 */         this.ofDroppedItems = 0;
/*      */       }
/*      */     } 
/*      */     
/* 1497 */     if (p_setOptionValueOF_1_ == Options.RAIN) {
/* 1498 */       this.ofRain++;
/*      */       
/* 1500 */       if (this.ofRain > 3) {
/* 1501 */         this.ofRain = 0;
/*      */       }
/*      */     } 
/*      */     
/* 1505 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_WATER) {
/* 1506 */       this.ofAnimatedWater++;
/*      */       
/* 1508 */       if (this.ofAnimatedWater == 1) {
/* 1509 */         this.ofAnimatedWater++;
/*      */       }
/*      */       
/* 1512 */       if (this.ofAnimatedWater > 2) {
/* 1513 */         this.ofAnimatedWater = 0;
/*      */       }
/*      */     } 
/*      */     
/* 1517 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_LAVA) {
/* 1518 */       this.ofAnimatedLava++;
/*      */       
/* 1520 */       if (this.ofAnimatedLava == 1) {
/* 1521 */         this.ofAnimatedLava++;
/*      */       }
/*      */       
/* 1524 */       if (this.ofAnimatedLava > 2) {
/* 1525 */         this.ofAnimatedLava = 0;
/*      */       }
/*      */     } 
/*      */     
/* 1529 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_FIRE) {
/* 1530 */       this.ofAnimatedFire = !this.ofAnimatedFire;
/*      */     }
/*      */     
/* 1533 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_PORTAL) {
/* 1534 */       this.ofAnimatedPortal = !this.ofAnimatedPortal;
/*      */     }
/*      */     
/* 1537 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_REDSTONE) {
/* 1538 */       this.ofAnimatedRedstone = !this.ofAnimatedRedstone;
/*      */     }
/*      */     
/* 1541 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_EXPLOSION) {
/* 1542 */       this.ofAnimatedExplosion = !this.ofAnimatedExplosion;
/*      */     }
/*      */     
/* 1545 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_FLAME) {
/* 1546 */       this.ofAnimatedFlame = !this.ofAnimatedFlame;
/*      */     }
/*      */     
/* 1549 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_SMOKE) {
/* 1550 */       this.ofAnimatedSmoke = !this.ofAnimatedSmoke;
/*      */     }
/*      */     
/* 1553 */     if (p_setOptionValueOF_1_ == Options.VOID_PARTICLES) {
/* 1554 */       this.ofVoidParticles = !this.ofVoidParticles;
/*      */     }
/*      */     
/* 1557 */     if (p_setOptionValueOF_1_ == Options.WATER_PARTICLES) {
/* 1558 */       this.ofWaterParticles = !this.ofWaterParticles;
/*      */     }
/*      */     
/* 1561 */     if (p_setOptionValueOF_1_ == Options.PORTAL_PARTICLES) {
/* 1562 */       this.ofPortalParticles = !this.ofPortalParticles;
/*      */     }
/*      */     
/* 1565 */     if (p_setOptionValueOF_1_ == Options.POTION_PARTICLES) {
/* 1566 */       this.ofPotionParticles = !this.ofPotionParticles;
/*      */     }
/*      */     
/* 1569 */     if (p_setOptionValueOF_1_ == Options.FIREWORK_PARTICLES) {
/* 1570 */       this.ofFireworkParticles = !this.ofFireworkParticles;
/*      */     }
/*      */     
/* 1573 */     if (p_setOptionValueOF_1_ == Options.DRIPPING_WATER_LAVA) {
/* 1574 */       this.ofDrippingWaterLava = !this.ofDrippingWaterLava;
/*      */     }
/*      */     
/* 1577 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_TERRAIN) {
/* 1578 */       this.ofAnimatedTerrain = !this.ofAnimatedTerrain;
/*      */     }
/*      */     
/* 1581 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_TEXTURES) {
/* 1582 */       this.ofAnimatedTextures = !this.ofAnimatedTextures;
/*      */     }
/*      */     
/* 1585 */     if (p_setOptionValueOF_1_ == Options.RAIN_SPLASH) {
/* 1586 */       this.ofRainSplash = !this.ofRainSplash;
/*      */     }
/*      */     
/* 1589 */     if (p_setOptionValueOF_1_ == Options.LAGOMETER) {
/* 1590 */       this.ofLagometer = !this.ofLagometer;
/*      */     }
/*      */     
/* 1593 */     if (p_setOptionValueOF_1_ == Options.SHOW_FPS) {
/* 1594 */       this.ofShowFps = !this.ofShowFps;
/*      */     }
/*      */     
/* 1597 */     if (p_setOptionValueOF_1_ == Options.AUTOSAVE_TICKS) {
/* 1598 */       int i = 900;
/* 1599 */       this.ofAutoSaveTicks = Math.max(this.ofAutoSaveTicks / i * i, i);
/* 1600 */       this.ofAutoSaveTicks <<= 1;
/*      */       
/* 1602 */       if (this.ofAutoSaveTicks > 32 * i) {
/* 1603 */         this.ofAutoSaveTicks = i;
/*      */       }
/*      */     } 
/*      */     
/* 1607 */     if (p_setOptionValueOF_1_ == Options.BETTER_GRASS) {
/* 1608 */       this.ofBetterGrass++;
/*      */       
/* 1610 */       if (this.ofBetterGrass > 3) {
/* 1611 */         this.ofBetterGrass = 1;
/*      */       }
/*      */       
/* 1614 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1617 */     if (p_setOptionValueOF_1_ == Options.CONNECTED_TEXTURES) {
/* 1618 */       this.ofConnectedTextures++;
/*      */       
/* 1620 */       if (this.ofConnectedTextures > 3) {
/* 1621 */         this.ofConnectedTextures = 1;
/*      */       }
/*      */       
/* 1624 */       if (this.ofConnectedTextures == 2) {
/* 1625 */         this.mc.renderGlobal.loadRenderers();
/*      */       } else {
/* 1627 */         this.mc.refreshResources();
/*      */       } 
/*      */     } 
/*      */     
/* 1631 */     if (p_setOptionValueOF_1_ == Options.WEATHER) {
/* 1632 */       this.ofWeather = !this.ofWeather;
/*      */     }
/*      */     
/* 1635 */     if (p_setOptionValueOF_1_ == Options.SKY) {
/* 1636 */       this.ofSky = !this.ofSky;
/*      */     }
/*      */     
/* 1639 */     if (p_setOptionValueOF_1_ == Options.STARS) {
/* 1640 */       this.ofStars = !this.ofStars;
/*      */     }
/*      */     
/* 1643 */     if (p_setOptionValueOF_1_ == Options.SUN_MOON) {
/* 1644 */       this.ofSunMoon = !this.ofSunMoon;
/*      */     }
/*      */     
/* 1647 */     if (p_setOptionValueOF_1_ == Options.VIGNETTE) {
/* 1648 */       this.ofVignette++;
/*      */       
/* 1650 */       if (this.ofVignette > 2) {
/* 1651 */         this.ofVignette = 0;
/*      */       }
/*      */     } 
/*      */     
/* 1655 */     if (p_setOptionValueOF_1_ == Options.CHUNK_UPDATES) {
/* 1656 */       this.ofChunkUpdates++;
/*      */       
/* 1658 */       if (this.ofChunkUpdates > 5) {
/* 1659 */         this.ofChunkUpdates = 1;
/*      */       }
/*      */     } 
/*      */     
/* 1663 */     if (p_setOptionValueOF_1_ == Options.CHUNK_UPDATES_DYNAMIC) {
/* 1664 */       this.ofChunkUpdatesDynamic = !this.ofChunkUpdatesDynamic;
/*      */     }
/*      */     
/* 1667 */     if (p_setOptionValueOF_1_ == Options.TIME) {
/* 1668 */       this.ofTime++;
/*      */       
/* 1670 */       if (this.ofTime > 2) {
/* 1671 */         this.ofTime = 0;
/*      */       }
/*      */     } 
/*      */     
/* 1675 */     if (p_setOptionValueOF_1_ == Options.CLEAR_WATER) {
/* 1676 */       this.ofClearWater = !this.ofClearWater;
/* 1677 */       updateWaterOpacity();
/*      */     } 
/*      */     
/* 1680 */     if (p_setOptionValueOF_1_ == Options.PROFILER) {
/* 1681 */       this.ofProfiler = !this.ofProfiler;
/*      */     }
/*      */     
/* 1684 */     if (p_setOptionValueOF_1_ == Options.BETTER_SNOW) {
/* 1685 */       this.ofBetterSnow = !this.ofBetterSnow;
/* 1686 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1689 */     if (p_setOptionValueOF_1_ == Options.SWAMP_COLORS) {
/* 1690 */       this.ofSwampColors = !this.ofSwampColors;
/* 1691 */       CustomColors.updateUseDefaultGrassFoliageColors();
/* 1692 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1695 */     if (p_setOptionValueOF_1_ == Options.RANDOM_ENTITIES) {
/* 1696 */       this.ofRandomEntities = !this.ofRandomEntities;
/* 1697 */       RandomEntities.update();
/*      */     } 
/*      */     
/* 1700 */     if (p_setOptionValueOF_1_ == Options.SMOOTH_BIOMES) {
/* 1701 */       this.ofSmoothBiomes = !this.ofSmoothBiomes;
/* 1702 */       CustomColors.updateUseDefaultGrassFoliageColors();
/* 1703 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1706 */     if (p_setOptionValueOF_1_ == Options.CUSTOM_FONTS) {
/* 1707 */       this.ofCustomFonts = !this.ofCustomFonts;
/* 1708 */       this.mc.fontRendererObj.onResourceManagerReload(Config.getResourceManager());
/* 1709 */       this.mc.standardGalacticFontRenderer.onResourceManagerReload(Config.getResourceManager());
/*      */     } 
/*      */     
/* 1712 */     if (p_setOptionValueOF_1_ == Options.CUSTOM_COLORS) {
/* 1713 */       this.ofCustomColors = !this.ofCustomColors;
/* 1714 */       CustomColors.update();
/* 1715 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1718 */     if (p_setOptionValueOF_1_ == Options.CUSTOM_ITEMS) {
/* 1719 */       this.ofCustomItems = !this.ofCustomItems;
/* 1720 */       this.mc.refreshResources();
/*      */     } 
/*      */     
/* 1723 */     if (p_setOptionValueOF_1_ == Options.CUSTOM_SKY) {
/* 1724 */       this.ofCustomSky = !this.ofCustomSky;
/* 1725 */       CustomSky.update();
/*      */     } 
/*      */     
/* 1728 */     if (p_setOptionValueOF_1_ == Options.SHOW_CAPES) {
/* 1729 */       this.ofShowCapes = !this.ofShowCapes;
/*      */     }
/*      */     
/* 1732 */     if (p_setOptionValueOF_1_ == Options.NATURAL_TEXTURES) {
/* 1733 */       this.ofNaturalTextures = !this.ofNaturalTextures;
/* 1734 */       NaturalTextures.update();
/* 1735 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1738 */     if (p_setOptionValueOF_1_ == Options.EMISSIVE_TEXTURES) {
/* 1739 */       this.ofEmissiveTextures = !this.ofEmissiveTextures;
/* 1740 */       this.mc.refreshResources();
/*      */     } 
/*      */     
/* 1743 */     if (p_setOptionValueOF_1_ == Options.FAST_MATH) {
/* 1744 */       this.ofFastMath = !this.ofFastMath;
/* 1745 */       MathHelper.fastMath = this.ofFastMath;
/*      */     } 
/*      */     
/* 1748 */     if (p_setOptionValueOF_1_ == Options.FAST_RENDER) {
/* 1749 */       if (!this.ofFastRender && Config.isShaders()) {
/* 1750 */         Config.showGuiMessage(Lang.get("of.message.fr.shaders1"), Lang.get("of.message.fr.shaders2"));
/*      */         
/*      */         return;
/*      */       } 
/* 1754 */       this.ofFastRender = !this.ofFastRender;
/*      */       
/* 1756 */       if (this.ofFastRender)
/*      */       {
/* 1758 */         this.mc.entityRenderer.stopUseShader();
/*      */       }
/*      */       
/* 1761 */       Config.updateFramebufferSize();
/*      */     } 
/*      */     
/* 1764 */     if (p_setOptionValueOF_1_ == Options.TRANSLUCENT_BLOCKS) {
/* 1765 */       if (this.ofTranslucentBlocks == 0) {
/* 1766 */         this.ofTranslucentBlocks = 1;
/* 1767 */       } else if (this.ofTranslucentBlocks == 1) {
/* 1768 */         this.ofTranslucentBlocks = 2;
/* 1769 */       } else if (this.ofTranslucentBlocks == 2) {
/* 1770 */         this.ofTranslucentBlocks = 0;
/*      */       } else {
/* 1772 */         this.ofTranslucentBlocks = 0;
/*      */       } 
/*      */       
/* 1775 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1778 */     if (p_setOptionValueOF_1_ == Options.LAZY_CHUNK_LOADING) {
/* 1779 */       this.ofLazyChunkLoading = !this.ofLazyChunkLoading;
/*      */     }
/*      */     
/* 1782 */     if (p_setOptionValueOF_1_ == Options.RENDER_REGIONS) {
/* 1783 */       this.ofRenderRegions = !this.ofRenderRegions;
/* 1784 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1787 */     if (p_setOptionValueOF_1_ == Options.SMART_ANIMATIONS) {
/* 1788 */       this.ofSmartAnimations = !this.ofSmartAnimations;
/* 1789 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1792 */     if (p_setOptionValueOF_1_ == Options.DYNAMIC_FOV) {
/* 1793 */       this.ofDynamicFov = !this.ofDynamicFov;
/*      */     }
/*      */     
/* 1796 */     if (p_setOptionValueOF_1_ == Options.ALTERNATE_BLOCKS) {
/* 1797 */       this.ofAlternateBlocks = !this.ofAlternateBlocks;
/* 1798 */       this.mc.refreshResources();
/*      */     } 
/*      */     
/* 1801 */     if (p_setOptionValueOF_1_ == Options.DYNAMIC_LIGHTS) {
/* 1802 */       this.ofDynamicLights = nextValue(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
/* 1803 */       DynamicLights.removeLights(this.mc.renderGlobal);
/*      */     } 
/*      */     
/* 1806 */     if (p_setOptionValueOF_1_ == Options.SCREENSHOT_SIZE) {
/* 1807 */       this.ofScreenshotSize++;
/*      */       
/* 1809 */       if (this.ofScreenshotSize > 4) {
/* 1810 */         this.ofScreenshotSize = 1;
/*      */       }
/*      */       
/* 1813 */       if (!OpenGlHelper.isFramebufferEnabled()) {
/* 1814 */         this.ofScreenshotSize = 1;
/*      */       }
/*      */     } 
/*      */     
/* 1818 */     if (p_setOptionValueOF_1_ == Options.CUSTOM_ENTITY_MODELS) {
/* 1819 */       this.ofCustomEntityModels = !this.ofCustomEntityModels;
/* 1820 */       this.mc.refreshResources();
/*      */     } 
/*      */     
/* 1823 */     if (p_setOptionValueOF_1_ == Options.CUSTOM_GUIS) {
/* 1824 */       this.ofCustomGuis = !this.ofCustomGuis;
/* 1825 */       CustomGuis.update();
/*      */     } 
/*      */     
/* 1828 */     if (p_setOptionValueOF_1_ == Options.SHOW_GL_ERRORS) {
/* 1829 */       this.ofShowGlErrors = !this.ofShowGlErrors;
/*      */     }
/*      */     
/* 1832 */     if (p_setOptionValueOF_1_ == Options.HELD_ITEM_TOOLTIPS) {
/* 1833 */       this.heldItemTooltips = !this.heldItemTooltips;
/*      */     }
/*      */     
/* 1836 */     if (p_setOptionValueOF_1_ == Options.ADVANCED_TOOLTIPS) {
/* 1837 */       this.advancedItemTooltips = !this.advancedItemTooltips;
/*      */     }
/*      */   }
/*      */   
/*      */   private String getKeyBindingOF(Options p_getKeyBindingOF_1_) {
/* 1842 */     String s = I18n.format(p_getKeyBindingOF_1_.getEnumString(), new Object[0]) + ": ";
/*      */     
/* 1844 */     if (s == null) {
/* 1845 */       s = p_getKeyBindingOF_1_.getEnumString();
/*      */     }
/*      */     
/* 1848 */     if (p_getKeyBindingOF_1_ == Options.RENDER_DISTANCE) {
/* 1849 */       int i1 = (int)getOptionFloatValue(p_getKeyBindingOF_1_);
/* 1850 */       String s1 = I18n.format("options.renderDistance.tiny", new Object[0]);
/* 1851 */       int i = 2;
/*      */       
/* 1853 */       if (i1 >= 4) {
/* 1854 */         s1 = I18n.format("options.renderDistance.short", new Object[0]);
/* 1855 */         i = 4;
/*      */       } 
/*      */       
/* 1858 */       if (i1 >= 8) {
/* 1859 */         s1 = I18n.format("options.renderDistance.normal", new Object[0]);
/* 1860 */         i = 8;
/*      */       } 
/*      */       
/* 1863 */       if (i1 >= 16) {
/* 1864 */         s1 = I18n.format("options.renderDistance.far", new Object[0]);
/* 1865 */         i = 16;
/*      */       } 
/*      */       
/* 1868 */       if (i1 >= 32) {
/* 1869 */         s1 = Lang.get("of.options.renderDistance.extreme");
/* 1870 */         i = 32;
/*      */       } 
/*      */       
/* 1873 */       if (i1 >= 48) {
/* 1874 */         s1 = Lang.get("of.options.renderDistance.insane");
/* 1875 */         i = 48;
/*      */       } 
/*      */       
/* 1878 */       if (i1 >= 64) {
/* 1879 */         s1 = Lang.get("of.options.renderDistance.ludicrous");
/* 1880 */         i = 64;
/*      */       } 
/*      */       
/* 1883 */       int j = this.renderDistanceChunks - i;
/* 1884 */       String s2 = s1;
/*      */       
/* 1886 */       if (j > 0) {
/* 1887 */         s2 = s1 + "+";
/*      */       }
/*      */       
/* 1890 */       return s + i1 + " " + s2;
/* 1891 */     }  if (p_getKeyBindingOF_1_ == Options.FOG_FANCY) {
/* 1892 */       switch (this.ofFogType) {
/*      */         case 1:
/* 1894 */           return s + Lang.getFast();
/*      */         
/*      */         case 2:
/* 1897 */           return s + Lang.getFancy();
/*      */         
/*      */         case 3:
/* 1900 */           return s + Lang.getOff();
/*      */       } 
/*      */       
/* 1903 */       return s + Lang.getOff();
/*      */     } 
/* 1905 */     if (p_getKeyBindingOF_1_ == Options.FOG_START)
/* 1906 */       return s + this.ofFogStart; 
/* 1907 */     if (p_getKeyBindingOF_1_ == Options.MIPMAP_TYPE) {
/* 1908 */       switch (this.ofMipmapType) {
/*      */         case 0:
/* 1910 */           return s + Lang.get("of.options.mipmap.nearest");
/*      */         
/*      */         case 1:
/* 1913 */           return s + Lang.get("of.options.mipmap.linear");
/*      */         
/*      */         case 2:
/* 1916 */           return s + Lang.get("of.options.mipmap.bilinear");
/*      */         
/*      */         case 3:
/* 1919 */           return s + Lang.get("of.options.mipmap.trilinear");
/*      */       } 
/*      */       
/* 1922 */       return s + "of.options.mipmap.nearest";
/*      */     } 
/* 1924 */     if (p_getKeyBindingOF_1_ == Options.SMOOTH_FPS)
/* 1925 */       return this.ofSmoothFps ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 1926 */     if (p_getKeyBindingOF_1_ == Options.SMOOTH_WORLD)
/* 1927 */       return this.ofSmoothWorld ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 1928 */     if (p_getKeyBindingOF_1_ == Options.CLOUDS) {
/* 1929 */       switch (this.ofClouds) {
/*      */         case 1:
/* 1931 */           return s + Lang.getFast();
/*      */         
/*      */         case 2:
/* 1934 */           return s + Lang.getFancy();
/*      */         
/*      */         case 3:
/* 1937 */           return s + Lang.getOff();
/*      */       } 
/*      */       
/* 1940 */       return s + Lang.getDefault();
/*      */     } 
/* 1942 */     if (p_getKeyBindingOF_1_ == Options.TREES) {
/* 1943 */       switch (this.ofTrees) {
/*      */         case 1:
/* 1945 */           return s + Lang.getFast();
/*      */         
/*      */         case 2:
/* 1948 */           return s + Lang.getFancy();
/*      */ 
/*      */         
/*      */         default:
/* 1952 */           return s + Lang.getDefault();
/*      */         case 4:
/*      */           break;
/* 1955 */       }  return s + Lang.get("of.general.smart");
/*      */     } 
/* 1957 */     if (p_getKeyBindingOF_1_ == Options.DROPPED_ITEMS) {
/* 1958 */       switch (this.ofDroppedItems) {
/*      */         case 1:
/* 1960 */           return s + Lang.getFast();
/*      */         
/*      */         case 2:
/* 1963 */           return s + Lang.getFancy();
/*      */       } 
/*      */       
/* 1966 */       return s + Lang.getDefault();
/*      */     } 
/* 1968 */     if (p_getKeyBindingOF_1_ == Options.RAIN) {
/* 1969 */       switch (this.ofRain) {
/*      */         case 1:
/* 1971 */           return s + Lang.getFast();
/*      */         
/*      */         case 2:
/* 1974 */           return s + Lang.getFancy();
/*      */         
/*      */         case 3:
/* 1977 */           return s + Lang.getOff();
/*      */       } 
/*      */       
/* 1980 */       return s + Lang.getDefault();
/*      */     } 
/* 1982 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_WATER) {
/* 1983 */       switch (this.ofAnimatedWater) {
/*      */         case 1:
/* 1985 */           return s + Lang.get("of.options.animation.dynamic");
/*      */         
/*      */         case 2:
/* 1988 */           return s + Lang.getOff();
/*      */       } 
/*      */       
/* 1991 */       return s + Lang.getOn();
/*      */     } 
/* 1993 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_LAVA) {
/* 1994 */       switch (this.ofAnimatedLava) {
/*      */         case 1:
/* 1996 */           return s + Lang.get("of.options.animation.dynamic");
/*      */         
/*      */         case 2:
/* 1999 */           return s + Lang.getOff();
/*      */       } 
/*      */       
/* 2002 */       return s + Lang.getOn();
/*      */     } 
/* 2004 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_FIRE)
/* 2005 */       return this.ofAnimatedFire ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2006 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_PORTAL)
/* 2007 */       return this.ofAnimatedPortal ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2008 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_REDSTONE)
/* 2009 */       return this.ofAnimatedRedstone ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2010 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_EXPLOSION)
/* 2011 */       return this.ofAnimatedExplosion ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2012 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_FLAME)
/* 2013 */       return this.ofAnimatedFlame ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2014 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_SMOKE)
/* 2015 */       return this.ofAnimatedSmoke ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2016 */     if (p_getKeyBindingOF_1_ == Options.VOID_PARTICLES)
/* 2017 */       return this.ofVoidParticles ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2018 */     if (p_getKeyBindingOF_1_ == Options.WATER_PARTICLES)
/* 2019 */       return this.ofWaterParticles ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2020 */     if (p_getKeyBindingOF_1_ == Options.PORTAL_PARTICLES)
/* 2021 */       return this.ofPortalParticles ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2022 */     if (p_getKeyBindingOF_1_ == Options.POTION_PARTICLES)
/* 2023 */       return this.ofPotionParticles ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2024 */     if (p_getKeyBindingOF_1_ == Options.FIREWORK_PARTICLES)
/* 2025 */       return this.ofFireworkParticles ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2026 */     if (p_getKeyBindingOF_1_ == Options.DRIPPING_WATER_LAVA)
/* 2027 */       return this.ofDrippingWaterLava ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2028 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_TERRAIN)
/* 2029 */       return this.ofAnimatedTerrain ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2030 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_TEXTURES)
/* 2031 */       return this.ofAnimatedTextures ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2032 */     if (p_getKeyBindingOF_1_ == Options.RAIN_SPLASH)
/* 2033 */       return this.ofRainSplash ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2034 */     if (p_getKeyBindingOF_1_ == Options.LAGOMETER)
/* 2035 */       return this.ofLagometer ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2036 */     if (p_getKeyBindingOF_1_ == Options.SHOW_FPS)
/* 2037 */       return this.ofShowFps ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2038 */     if (p_getKeyBindingOF_1_ == Options.AUTOSAVE_TICKS) {
/* 2039 */       int l = 900;
/* 2040 */       return (this.ofAutoSaveTicks <= l) ? (s + Lang.get("of.options.save.45s")) : ((this.ofAutoSaveTicks <= 2 * l) ? (s + Lang.get("of.options.save.90s")) : ((this.ofAutoSaveTicks <= 4 * l) ? (s + Lang.get("of.options.save.3min")) : ((this.ofAutoSaveTicks <= 8 * l) ? (s + Lang.get("of.options.save.6min")) : ((this.ofAutoSaveTicks <= 16 * l) ? (s + Lang.get("of.options.save.12min")) : (s + Lang.get("of.options.save.24min"))))));
/* 2041 */     }  if (p_getKeyBindingOF_1_ == Options.BETTER_GRASS) {
/* 2042 */       switch (this.ofBetterGrass) {
/*      */         case 1:
/* 2044 */           return s + Lang.getFast();
/*      */         
/*      */         case 2:
/* 2047 */           return s + Lang.getFancy();
/*      */       } 
/*      */       
/* 2050 */       return s + Lang.getOff();
/*      */     } 
/* 2052 */     if (p_getKeyBindingOF_1_ == Options.CONNECTED_TEXTURES) {
/* 2053 */       switch (this.ofConnectedTextures) {
/*      */         case 1:
/* 2055 */           return s + Lang.getFast();
/*      */         
/*      */         case 2:
/* 2058 */           return s + Lang.getFancy();
/*      */       } 
/*      */       
/* 2061 */       return s + Lang.getOff();
/*      */     } 
/* 2063 */     if (p_getKeyBindingOF_1_ == Options.WEATHER)
/* 2064 */       return this.ofWeather ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2065 */     if (p_getKeyBindingOF_1_ == Options.SKY)
/* 2066 */       return this.ofSky ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2067 */     if (p_getKeyBindingOF_1_ == Options.STARS)
/* 2068 */       return this.ofStars ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2069 */     if (p_getKeyBindingOF_1_ == Options.SUN_MOON)
/* 2070 */       return this.ofSunMoon ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2071 */     if (p_getKeyBindingOF_1_ == Options.VIGNETTE) {
/* 2072 */       switch (this.ofVignette) {
/*      */         case 1:
/* 2074 */           return s + Lang.getFast();
/*      */         
/*      */         case 2:
/* 2077 */           return s + Lang.getFancy();
/*      */       } 
/*      */       
/* 2080 */       return s + Lang.getDefault();
/*      */     } 
/* 2082 */     if (p_getKeyBindingOF_1_ == Options.CHUNK_UPDATES)
/* 2083 */       return s + this.ofChunkUpdates; 
/* 2084 */     if (p_getKeyBindingOF_1_ == Options.CHUNK_UPDATES_DYNAMIC)
/* 2085 */       return this.ofChunkUpdatesDynamic ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2086 */     if (p_getKeyBindingOF_1_ == Options.TIME)
/* 2087 */       return (this.ofTime == 1) ? (s + Lang.get("of.options.time.dayOnly")) : ((this.ofTime == 2) ? (s + Lang.get("of.options.time.nightOnly")) : (s + Lang.getDefault())); 
/* 2088 */     if (p_getKeyBindingOF_1_ == Options.CLEAR_WATER)
/* 2089 */       return this.ofClearWater ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2090 */     if (p_getKeyBindingOF_1_ == Options.AA_LEVEL) {
/* 2091 */       String s3 = "";
/*      */       
/* 2093 */       if (this.ofAaLevel != Config.getAntialiasingLevel()) {
/* 2094 */         s3 = " (" + Lang.get("of.general.restart") + ")";
/*      */       }
/*      */       
/* 2097 */       return (this.ofAaLevel == 0) ? (s + Lang.getOff() + s3) : (s + this.ofAaLevel + s3);
/* 2098 */     }  if (p_getKeyBindingOF_1_ == Options.AF_LEVEL)
/* 2099 */       return (this.ofAfLevel == 1) ? (s + Lang.getOff()) : (s + this.ofAfLevel); 
/* 2100 */     if (p_getKeyBindingOF_1_ == Options.PROFILER)
/* 2101 */       return this.ofProfiler ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2102 */     if (p_getKeyBindingOF_1_ == Options.BETTER_SNOW)
/* 2103 */       return this.ofBetterSnow ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2104 */     if (p_getKeyBindingOF_1_ == Options.SWAMP_COLORS)
/* 2105 */       return this.ofSwampColors ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2106 */     if (p_getKeyBindingOF_1_ == Options.RANDOM_ENTITIES)
/* 2107 */       return this.ofRandomEntities ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2108 */     if (p_getKeyBindingOF_1_ == Options.SMOOTH_BIOMES)
/* 2109 */       return this.ofSmoothBiomes ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2110 */     if (p_getKeyBindingOF_1_ == Options.CUSTOM_FONTS)
/* 2111 */       return this.ofCustomFonts ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2112 */     if (p_getKeyBindingOF_1_ == Options.CUSTOM_COLORS)
/* 2113 */       return this.ofCustomColors ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2114 */     if (p_getKeyBindingOF_1_ == Options.CUSTOM_SKY)
/* 2115 */       return this.ofCustomSky ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2116 */     if (p_getKeyBindingOF_1_ == Options.SHOW_CAPES)
/* 2117 */       return this.ofShowCapes ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2118 */     if (p_getKeyBindingOF_1_ == Options.CUSTOM_ITEMS)
/* 2119 */       return this.ofCustomItems ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2120 */     if (p_getKeyBindingOF_1_ == Options.NATURAL_TEXTURES)
/* 2121 */       return this.ofNaturalTextures ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2122 */     if (p_getKeyBindingOF_1_ == Options.EMISSIVE_TEXTURES)
/* 2123 */       return this.ofEmissiveTextures ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2124 */     if (p_getKeyBindingOF_1_ == Options.FAST_MATH)
/* 2125 */       return this.ofFastMath ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2126 */     if (p_getKeyBindingOF_1_ == Options.FAST_RENDER)
/* 2127 */       return this.ofFastRender ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2128 */     if (p_getKeyBindingOF_1_ == Options.TRANSLUCENT_BLOCKS)
/* 2129 */       return (this.ofTranslucentBlocks == 1) ? (s + Lang.getFast()) : ((this.ofTranslucentBlocks == 2) ? (s + Lang.getFancy()) : (s + Lang.getDefault())); 
/* 2130 */     if (p_getKeyBindingOF_1_ == Options.LAZY_CHUNK_LOADING)
/* 2131 */       return this.ofLazyChunkLoading ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2132 */     if (p_getKeyBindingOF_1_ == Options.RENDER_REGIONS)
/* 2133 */       return this.ofRenderRegions ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2134 */     if (p_getKeyBindingOF_1_ == Options.SMART_ANIMATIONS)
/* 2135 */       return this.ofSmartAnimations ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2136 */     if (p_getKeyBindingOF_1_ == Options.DYNAMIC_FOV)
/* 2137 */       return this.ofDynamicFov ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2138 */     if (p_getKeyBindingOF_1_ == Options.ALTERNATE_BLOCKS)
/* 2139 */       return this.ofAlternateBlocks ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2140 */     if (p_getKeyBindingOF_1_ == Options.DYNAMIC_LIGHTS) {
/* 2141 */       int k = indexOf(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
/* 2142 */       return s + getTranslation(KEYS_DYNAMIC_LIGHTS, k);
/* 2143 */     }  if (p_getKeyBindingOF_1_ == Options.SCREENSHOT_SIZE)
/* 2144 */       return (this.ofScreenshotSize <= 1) ? (s + Lang.getDefault()) : (s + this.ofScreenshotSize + "x"); 
/* 2145 */     if (p_getKeyBindingOF_1_ == Options.CUSTOM_ENTITY_MODELS)
/* 2146 */       return this.ofCustomEntityModels ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2147 */     if (p_getKeyBindingOF_1_ == Options.CUSTOM_GUIS)
/* 2148 */       return this.ofCustomGuis ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2149 */     if (p_getKeyBindingOF_1_ == Options.SHOW_GL_ERRORS)
/* 2150 */       return this.ofShowGlErrors ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2151 */     if (p_getKeyBindingOF_1_ == Options.FULLSCREEN_MODE)
/* 2152 */       return this.ofFullscreenMode.equals("Default") ? (s + Lang.getDefault()) : (s + this.ofFullscreenMode); 
/* 2153 */     if (p_getKeyBindingOF_1_ == Options.HELD_ITEM_TOOLTIPS)
/* 2154 */       return this.heldItemTooltips ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2155 */     if (p_getKeyBindingOF_1_ == Options.ADVANCED_TOOLTIPS)
/* 2156 */       return this.advancedItemTooltips ? (s + Lang.getOn()) : (s + Lang.getOff()); 
/* 2157 */     if (p_getKeyBindingOF_1_ == Options.FRAMERATE_LIMIT) {
/* 2158 */       float f = getOptionFloatValue(p_getKeyBindingOF_1_);
/* 2159 */       return (f == 0.0F) ? (s + Lang.get("of.options.framerateLimit.vsync")) : ((f == p_getKeyBindingOF_1_.valueMax) ? (s + I18n.format("options.framerateLimit.max", new Object[0])) : (s + (int)f + " fps"));
/*      */     } 
/* 2161 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadOfOptions() {
/*      */     try {
/* 2167 */       File file1 = this.optionsFileOF;
/*      */       
/* 2169 */       if (!file1.exists()) {
/* 2170 */         file1 = this.optionsFile;
/*      */       }
/*      */       
/* 2173 */       if (!file1.exists()) {
/*      */         return;
/*      */       }
/*      */       
/* 2177 */       BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(file1), StandardCharsets.UTF_8));
/* 2178 */       String s = "";
/*      */       
/* 2180 */       while ((s = bufferedreader.readLine()) != null) {
/*      */         try {
/* 2182 */           String[] astring = s.split(":");
/*      */           
/* 2184 */           if (astring[0].equals("ofRenderDistanceChunks") && astring.length >= 2) {
/* 2185 */             this.renderDistanceChunks = Integer.valueOf(astring[1]).intValue();
/* 2186 */             this.renderDistanceChunks = Config.limit(this.renderDistanceChunks, 2, 1024);
/*      */           } 
/*      */           
/* 2189 */           if (astring[0].equals("ofFogType") && astring.length >= 2) {
/* 2190 */             this.ofFogType = Integer.valueOf(astring[1]).intValue();
/* 2191 */             this.ofFogType = Config.limit(this.ofFogType, 1, 3);
/*      */           } 
/*      */           
/* 2194 */           if (astring[0].equals("ofFogStart") && astring.length >= 2) {
/* 2195 */             this.ofFogStart = Float.valueOf(astring[1]).floatValue();
/*      */             
/* 2197 */             if (this.ofFogStart < 0.2F) {
/* 2198 */               this.ofFogStart = 0.2F;
/*      */             }
/*      */             
/* 2201 */             if (this.ofFogStart > 0.81F) {
/* 2202 */               this.ofFogStart = 0.8F;
/*      */             }
/*      */           } 
/*      */           
/* 2206 */           if (astring[0].equals("ofMipmapType") && astring.length >= 2) {
/* 2207 */             this.ofMipmapType = Integer.valueOf(astring[1]).intValue();
/* 2208 */             this.ofMipmapType = Config.limit(this.ofMipmapType, 0, 3);
/*      */           } 
/*      */           
/* 2211 */           if (astring[0].equals("ofOcclusionFancy") && astring.length >= 2) {
/* 2212 */             this.ofOcclusionFancy = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2215 */           if (astring[0].equals("ofSmoothFps") && astring.length >= 2) {
/* 2216 */             this.ofSmoothFps = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2219 */           if (astring[0].equals("ofSmoothWorld") && astring.length >= 2) {
/* 2220 */             this.ofSmoothWorld = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2223 */           if (astring[0].equals("ofAoLevel") && astring.length >= 2) {
/* 2224 */             this.ofAoLevel = Float.valueOf(astring[1]).floatValue();
/* 2225 */             this.ofAoLevel = Config.limit(this.ofAoLevel, 0.0F, 1.0F);
/*      */           } 
/*      */           
/* 2228 */           if (astring[0].equals("ofClouds") && astring.length >= 2) {
/* 2229 */             this.ofClouds = Integer.valueOf(astring[1]).intValue();
/* 2230 */             this.ofClouds = Config.limit(this.ofClouds, 0, 3);
/* 2231 */             updateRenderClouds();
/*      */           } 
/*      */           
/* 2234 */           if (astring[0].equals("ofCloudsHeight") && astring.length >= 2) {
/* 2235 */             this.ofCloudsHeight = Float.valueOf(astring[1]).floatValue();
/* 2236 */             this.ofCloudsHeight = Config.limit(this.ofCloudsHeight, 0.0F, 1.0F);
/*      */           } 
/*      */           
/* 2239 */           if (astring[0].equals("ofTrees") && astring.length >= 2) {
/* 2240 */             this.ofTrees = Integer.valueOf(astring[1]).intValue();
/* 2241 */             this.ofTrees = limit(this.ofTrees, OF_TREES_VALUES);
/*      */           } 
/*      */           
/* 2244 */           if (astring[0].equals("ofDroppedItems") && astring.length >= 2) {
/* 2245 */             this.ofDroppedItems = Integer.valueOf(astring[1]).intValue();
/* 2246 */             this.ofDroppedItems = Config.limit(this.ofDroppedItems, 0, 2);
/*      */           } 
/*      */           
/* 2249 */           if (astring[0].equals("ofRain") && astring.length >= 2) {
/* 2250 */             this.ofRain = Integer.valueOf(astring[1]).intValue();
/* 2251 */             this.ofRain = Config.limit(this.ofRain, 0, 3);
/*      */           } 
/*      */           
/* 2254 */           if (astring[0].equals("ofAnimatedWater") && astring.length >= 2) {
/* 2255 */             this.ofAnimatedWater = Integer.valueOf(astring[1]).intValue();
/* 2256 */             this.ofAnimatedWater = Config.limit(this.ofAnimatedWater, 0, 2);
/*      */           } 
/*      */           
/* 2259 */           if (astring[0].equals("ofAnimatedLava") && astring.length >= 2) {
/* 2260 */             this.ofAnimatedLava = Integer.valueOf(astring[1]).intValue();
/* 2261 */             this.ofAnimatedLava = Config.limit(this.ofAnimatedLava, 0, 2);
/*      */           } 
/*      */           
/* 2264 */           if (astring[0].equals("ofAnimatedFire") && astring.length >= 2) {
/* 2265 */             this.ofAnimatedFire = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2268 */           if (astring[0].equals("ofAnimatedPortal") && astring.length >= 2) {
/* 2269 */             this.ofAnimatedPortal = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2272 */           if (astring[0].equals("ofAnimatedRedstone") && astring.length >= 2) {
/* 2273 */             this.ofAnimatedRedstone = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2276 */           if (astring[0].equals("ofAnimatedExplosion") && astring.length >= 2) {
/* 2277 */             this.ofAnimatedExplosion = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2280 */           if (astring[0].equals("ofAnimatedFlame") && astring.length >= 2) {
/* 2281 */             this.ofAnimatedFlame = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2284 */           if (astring[0].equals("ofAnimatedSmoke") && astring.length >= 2) {
/* 2285 */             this.ofAnimatedSmoke = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2288 */           if (astring[0].equals("ofVoidParticles") && astring.length >= 2) {
/* 2289 */             this.ofVoidParticles = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2292 */           if (astring[0].equals("ofWaterParticles") && astring.length >= 2) {
/* 2293 */             this.ofWaterParticles = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2296 */           if (astring[0].equals("ofPortalParticles") && astring.length >= 2) {
/* 2297 */             this.ofPortalParticles = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2300 */           if (astring[0].equals("ofPotionParticles") && astring.length >= 2) {
/* 2301 */             this.ofPotionParticles = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2304 */           if (astring[0].equals("ofFireworkParticles") && astring.length >= 2) {
/* 2305 */             this.ofFireworkParticles = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2308 */           if (astring[0].equals("ofDrippingWaterLava") && astring.length >= 2) {
/* 2309 */             this.ofDrippingWaterLava = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2312 */           if (astring[0].equals("ofAnimatedTerrain") && astring.length >= 2) {
/* 2313 */             this.ofAnimatedTerrain = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2316 */           if (astring[0].equals("ofAnimatedTextures") && astring.length >= 2) {
/* 2317 */             this.ofAnimatedTextures = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2320 */           if (astring[0].equals("ofRainSplash") && astring.length >= 2) {
/* 2321 */             this.ofRainSplash = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2324 */           if (astring[0].equals("ofLagometer") && astring.length >= 2) {
/* 2325 */             this.ofLagometer = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2328 */           if (astring[0].equals("ofShowFps") && astring.length >= 2) {
/* 2329 */             this.ofShowFps = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2332 */           if (astring[0].equals("ofAutoSaveTicks") && astring.length >= 2) {
/* 2333 */             this.ofAutoSaveTicks = Integer.valueOf(astring[1]).intValue();
/* 2334 */             this.ofAutoSaveTicks = Config.limit(this.ofAutoSaveTicks, 40, 40000);
/*      */           } 
/*      */           
/* 2337 */           if (astring[0].equals("ofBetterGrass") && astring.length >= 2) {
/* 2338 */             this.ofBetterGrass = Integer.valueOf(astring[1]).intValue();
/* 2339 */             this.ofBetterGrass = Config.limit(this.ofBetterGrass, 1, 3);
/*      */           } 
/*      */           
/* 2342 */           if (astring[0].equals("ofConnectedTextures") && astring.length >= 2) {
/* 2343 */             this.ofConnectedTextures = Integer.valueOf(astring[1]).intValue();
/* 2344 */             this.ofConnectedTextures = Config.limit(this.ofConnectedTextures, 1, 3);
/*      */           } 
/*      */           
/* 2347 */           if (astring[0].equals("ofWeather") && astring.length >= 2) {
/* 2348 */             this.ofWeather = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2351 */           if (astring[0].equals("ofSky") && astring.length >= 2) {
/* 2352 */             this.ofSky = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2355 */           if (astring[0].equals("ofStars") && astring.length >= 2) {
/* 2356 */             this.ofStars = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2359 */           if (astring[0].equals("ofSunMoon") && astring.length >= 2) {
/* 2360 */             this.ofSunMoon = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2363 */           if (astring[0].equals("ofVignette") && astring.length >= 2) {
/* 2364 */             this.ofVignette = Integer.valueOf(astring[1]).intValue();
/* 2365 */             this.ofVignette = Config.limit(this.ofVignette, 0, 2);
/*      */           } 
/*      */           
/* 2368 */           if (astring[0].equals("ofChunkUpdates") && astring.length >= 2) {
/* 2369 */             this.ofChunkUpdates = Integer.valueOf(astring[1]).intValue();
/* 2370 */             this.ofChunkUpdates = Config.limit(this.ofChunkUpdates, 1, 5);
/*      */           } 
/*      */           
/* 2373 */           if (astring[0].equals("ofChunkUpdatesDynamic") && astring.length >= 2) {
/* 2374 */             this.ofChunkUpdatesDynamic = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2377 */           if (astring[0].equals("ofTime") && astring.length >= 2) {
/* 2378 */             this.ofTime = Integer.valueOf(astring[1]).intValue();
/* 2379 */             this.ofTime = Config.limit(this.ofTime, 0, 2);
/*      */           } 
/*      */           
/* 2382 */           if (astring[0].equals("ofClearWater") && astring.length >= 2) {
/* 2383 */             this.ofClearWater = Boolean.valueOf(astring[1]).booleanValue();
/* 2384 */             updateWaterOpacity();
/*      */           } 
/*      */           
/* 2387 */           if (astring[0].equals("ofAaLevel") && astring.length >= 2) {
/* 2388 */             this.ofAaLevel = Integer.valueOf(astring[1]).intValue();
/* 2389 */             this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
/*      */           } 
/*      */           
/* 2392 */           if (astring[0].equals("ofAfLevel") && astring.length >= 2) {
/* 2393 */             this.ofAfLevel = Integer.valueOf(astring[1]).intValue();
/* 2394 */             this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
/*      */           } 
/*      */           
/* 2397 */           if (astring[0].equals("ofProfiler") && astring.length >= 2) {
/* 2398 */             this.ofProfiler = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2401 */           if (astring[0].equals("ofBetterSnow") && astring.length >= 2) {
/* 2402 */             this.ofBetterSnow = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2405 */           if (astring[0].equals("ofSwampColors") && astring.length >= 2) {
/* 2406 */             this.ofSwampColors = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2409 */           if (astring[0].equals("ofRandomEntities") && astring.length >= 2) {
/* 2410 */             this.ofRandomEntities = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2413 */           if (astring[0].equals("ofSmoothBiomes") && astring.length >= 2) {
/* 2414 */             this.ofSmoothBiomes = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2417 */           if (astring[0].equals("ofCustomFonts") && astring.length >= 2) {
/* 2418 */             this.ofCustomFonts = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2421 */           if (astring[0].equals("ofCustomColors") && astring.length >= 2) {
/* 2422 */             this.ofCustomColors = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2425 */           if (astring[0].equals("ofCustomItems") && astring.length >= 2) {
/* 2426 */             this.ofCustomItems = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2429 */           if (astring[0].equals("ofCustomSky") && astring.length >= 2) {
/* 2430 */             this.ofCustomSky = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2433 */           if (astring[0].equals("ofShowCapes") && astring.length >= 2) {
/* 2434 */             this.ofShowCapes = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2437 */           if (astring[0].equals("ofNaturalTextures") && astring.length >= 2) {
/* 2438 */             this.ofNaturalTextures = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2441 */           if (astring[0].equals("ofEmissiveTextures") && astring.length >= 2) {
/* 2442 */             this.ofEmissiveTextures = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2445 */           if (astring[0].equals("ofLazyChunkLoading") && astring.length >= 2) {
/* 2446 */             this.ofLazyChunkLoading = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2449 */           if (astring[0].equals("ofRenderRegions") && astring.length >= 2) {
/* 2450 */             this.ofRenderRegions = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2453 */           if (astring[0].equals("ofSmartAnimations") && astring.length >= 2) {
/* 2454 */             this.ofSmartAnimations = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2457 */           if (astring[0].equals("ofDynamicFov") && astring.length >= 2) {
/* 2458 */             this.ofDynamicFov = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2461 */           if (astring[0].equals("ofAlternateBlocks") && astring.length >= 2) {
/* 2462 */             this.ofAlternateBlocks = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2465 */           if (astring[0].equals("ofDynamicLights") && astring.length >= 2) {
/* 2466 */             this.ofDynamicLights = Integer.valueOf(astring[1]).intValue();
/* 2467 */             this.ofDynamicLights = limit(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
/*      */           } 
/*      */           
/* 2470 */           if (astring[0].equals("ofScreenshotSize") && astring.length >= 2) {
/* 2471 */             this.ofScreenshotSize = Integer.valueOf(astring[1]).intValue();
/* 2472 */             this.ofScreenshotSize = Config.limit(this.ofScreenshotSize, 1, 4);
/*      */           } 
/*      */           
/* 2475 */           if (astring[0].equals("ofCustomEntityModels") && astring.length >= 2) {
/* 2476 */             this.ofCustomEntityModels = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2479 */           if (astring[0].equals("ofCustomGuis") && astring.length >= 2) {
/* 2480 */             this.ofCustomGuis = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2483 */           if (astring[0].equals("ofShowGlErrors") && astring.length >= 2) {
/* 2484 */             this.ofShowGlErrors = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2487 */           if (astring[0].equals("ofFullscreenMode") && astring.length >= 2) {
/* 2488 */             this.ofFullscreenMode = astring[1];
/*      */           }
/*      */           
/* 2491 */           if (astring[0].equals("ofFastMath") && astring.length >= 2) {
/* 2492 */             this.ofFastMath = Boolean.valueOf(astring[1]).booleanValue();
/* 2493 */             MathHelper.fastMath = this.ofFastMath;
/*      */           } 
/*      */           
/* 2496 */           if (astring[0].equals("ofFastRender") && astring.length >= 2) {
/* 2497 */             this.ofFastRender = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2500 */           if (astring[0].equals("ofTranslucentBlocks") && astring.length >= 2) {
/* 2501 */             this.ofTranslucentBlocks = Integer.valueOf(astring[1]).intValue();
/* 2502 */             this.ofTranslucentBlocks = Config.limit(this.ofTranslucentBlocks, 0, 2);
/*      */           } 
/*      */           
/* 2505 */           if (astring[0].equals("key_" + this.ofKeyBindZoom.getKeyDescription())) {
/* 2506 */             this.ofKeyBindZoom.setKeyCode(Integer.parseInt(astring[1]));
/*      */           }
/* 2508 */         } catch (Exception exception) {
/* 2509 */           Config.dbg("Skipping bad option: " + s);
/* 2510 */           exception.printStackTrace();
/*      */         } 
/*      */       } 
/*      */       
/* 2514 */       KeyUtils.fixKeyConflicts(this.keyBindings, new KeyBinding[] { this.ofKeyBindZoom });
/* 2515 */       KeyBinding.resetKeyBindingArrayAndHash();
/* 2516 */       bufferedreader.close();
/* 2517 */     } catch (Exception exception1) {
/* 2518 */       Config.warn("Failed to load options");
/* 2519 */       exception1.printStackTrace();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void saveOfOptions() {
/*      */     try {
/* 2525 */       PrintWriter printwriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.optionsFileOF), StandardCharsets.UTF_8));
/* 2526 */       printwriter.println("ofFogType:" + this.ofFogType);
/* 2527 */       printwriter.println("ofFogStart:" + this.ofFogStart);
/* 2528 */       printwriter.println("ofMipmapType:" + this.ofMipmapType);
/* 2529 */       printwriter.println("ofOcclusionFancy:" + this.ofOcclusionFancy);
/* 2530 */       printwriter.println("ofSmoothFps:" + this.ofSmoothFps);
/* 2531 */       printwriter.println("ofSmoothWorld:" + this.ofSmoothWorld);
/* 2532 */       printwriter.println("ofAoLevel:" + this.ofAoLevel);
/* 2533 */       printwriter.println("ofClouds:" + this.ofClouds);
/* 2534 */       printwriter.println("ofCloudsHeight:" + this.ofCloudsHeight);
/* 2535 */       printwriter.println("ofTrees:" + this.ofTrees);
/* 2536 */       printwriter.println("ofDroppedItems:" + this.ofDroppedItems);
/* 2537 */       printwriter.println("ofRain:" + this.ofRain);
/* 2538 */       printwriter.println("ofAnimatedWater:" + this.ofAnimatedWater);
/* 2539 */       printwriter.println("ofAnimatedLava:" + this.ofAnimatedLava);
/* 2540 */       printwriter.println("ofAnimatedFire:" + this.ofAnimatedFire);
/* 2541 */       printwriter.println("ofAnimatedPortal:" + this.ofAnimatedPortal);
/* 2542 */       printwriter.println("ofAnimatedRedstone:" + this.ofAnimatedRedstone);
/* 2543 */       printwriter.println("ofAnimatedExplosion:" + this.ofAnimatedExplosion);
/* 2544 */       printwriter.println("ofAnimatedFlame:" + this.ofAnimatedFlame);
/* 2545 */       printwriter.println("ofAnimatedSmoke:" + this.ofAnimatedSmoke);
/* 2546 */       printwriter.println("ofVoidParticles:" + this.ofVoidParticles);
/* 2547 */       printwriter.println("ofWaterParticles:" + this.ofWaterParticles);
/* 2548 */       printwriter.println("ofPortalParticles:" + this.ofPortalParticles);
/* 2549 */       printwriter.println("ofPotionParticles:" + this.ofPotionParticles);
/* 2550 */       printwriter.println("ofFireworkParticles:" + this.ofFireworkParticles);
/* 2551 */       printwriter.println("ofDrippingWaterLava:" + this.ofDrippingWaterLava);
/* 2552 */       printwriter.println("ofAnimatedTerrain:" + this.ofAnimatedTerrain);
/* 2553 */       printwriter.println("ofAnimatedTextures:" + this.ofAnimatedTextures);
/* 2554 */       printwriter.println("ofRainSplash:" + this.ofRainSplash);
/* 2555 */       printwriter.println("ofLagometer:" + this.ofLagometer);
/* 2556 */       printwriter.println("ofShowFps:" + this.ofShowFps);
/* 2557 */       printwriter.println("ofAutoSaveTicks:" + this.ofAutoSaveTicks);
/* 2558 */       printwriter.println("ofBetterGrass:" + this.ofBetterGrass);
/* 2559 */       printwriter.println("ofConnectedTextures:" + this.ofConnectedTextures);
/* 2560 */       printwriter.println("ofWeather:" + this.ofWeather);
/* 2561 */       printwriter.println("ofSky:" + this.ofSky);
/* 2562 */       printwriter.println("ofStars:" + this.ofStars);
/* 2563 */       printwriter.println("ofSunMoon:" + this.ofSunMoon);
/* 2564 */       printwriter.println("ofVignette:" + this.ofVignette);
/* 2565 */       printwriter.println("ofChunkUpdates:" + this.ofChunkUpdates);
/* 2566 */       printwriter.println("ofChunkUpdatesDynamic:" + this.ofChunkUpdatesDynamic);
/* 2567 */       printwriter.println("ofTime:" + this.ofTime);
/* 2568 */       printwriter.println("ofClearWater:" + this.ofClearWater);
/* 2569 */       printwriter.println("ofAaLevel:" + this.ofAaLevel);
/* 2570 */       printwriter.println("ofAfLevel:" + this.ofAfLevel);
/* 2571 */       printwriter.println("ofProfiler:" + this.ofProfiler);
/* 2572 */       printwriter.println("ofBetterSnow:" + this.ofBetterSnow);
/* 2573 */       printwriter.println("ofSwampColors:" + this.ofSwampColors);
/* 2574 */       printwriter.println("ofRandomEntities:" + this.ofRandomEntities);
/* 2575 */       printwriter.println("ofSmoothBiomes:" + this.ofSmoothBiomes);
/* 2576 */       printwriter.println("ofCustomFonts:" + this.ofCustomFonts);
/* 2577 */       printwriter.println("ofCustomColors:" + this.ofCustomColors);
/* 2578 */       printwriter.println("ofCustomItems:" + this.ofCustomItems);
/* 2579 */       printwriter.println("ofCustomSky:" + this.ofCustomSky);
/* 2580 */       printwriter.println("ofShowCapes:" + this.ofShowCapes);
/* 2581 */       printwriter.println("ofNaturalTextures:" + this.ofNaturalTextures);
/* 2582 */       printwriter.println("ofEmissiveTextures:" + this.ofEmissiveTextures);
/* 2583 */       printwriter.println("ofLazyChunkLoading:" + this.ofLazyChunkLoading);
/* 2584 */       printwriter.println("ofRenderRegions:" + this.ofRenderRegions);
/* 2585 */       printwriter.println("ofSmartAnimations:" + this.ofSmartAnimations);
/* 2586 */       printwriter.println("ofDynamicFov:" + this.ofDynamicFov);
/* 2587 */       printwriter.println("ofAlternateBlocks:" + this.ofAlternateBlocks);
/* 2588 */       printwriter.println("ofDynamicLights:" + this.ofDynamicLights);
/* 2589 */       printwriter.println("ofScreenshotSize:" + this.ofScreenshotSize);
/* 2590 */       printwriter.println("ofCustomEntityModels:" + this.ofCustomEntityModels);
/* 2591 */       printwriter.println("ofCustomGuis:" + this.ofCustomGuis);
/* 2592 */       printwriter.println("ofShowGlErrors:" + this.ofShowGlErrors);
/* 2593 */       printwriter.println("ofFullscreenMode:" + this.ofFullscreenMode);
/* 2594 */       printwriter.println("ofFastMath:" + this.ofFastMath);
/* 2595 */       printwriter.println("ofFastRender:" + this.ofFastRender);
/* 2596 */       printwriter.println("ofTranslucentBlocks:" + this.ofTranslucentBlocks);
/* 2597 */       printwriter.println("key_" + this.ofKeyBindZoom.getKeyDescription() + ":" + this.ofKeyBindZoom.getKeyCode());
/* 2598 */       printwriter.close();
/* 2599 */     } catch (Exception exception) {
/* 2600 */       Config.warn("Failed to save options");
/* 2601 */       exception.printStackTrace();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateRenderClouds() {
/* 2606 */     switch (this.ofClouds) {
/*      */       case 1:
/* 2608 */         this.clouds = 1;
/*      */         return;
/*      */       
/*      */       case 2:
/* 2612 */         this.clouds = 2;
/*      */         return;
/*      */       
/*      */       case 3:
/* 2616 */         this.clouds = 0;
/*      */         return;
/*      */     } 
/*      */     
/* 2620 */     if (this.fancyGraphics) {
/* 2621 */       this.clouds = 2;
/*      */     } else {
/* 2623 */       this.clouds = 1;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void resetSettings() {
/* 2629 */     this.renderDistanceChunks = 8;
/* 2630 */     this.viewBobbing = true;
/* 2631 */     this.anaglyph = false;
/* 2632 */     this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
/* 2633 */     this.enableVsync = false;
/* 2634 */     updateVSync();
/* 2635 */     this.mipmapLevels = 4;
/* 2636 */     this.fancyGraphics = true;
/* 2637 */     this.ambientOcclusion = 2;
/* 2638 */     this.clouds = 2;
/* 2639 */     this.fovSetting = 70.0F;
/* 2640 */     this.gammaSetting = 0.0F;
/* 2641 */     this.guiScale = 0;
/* 2642 */     this.particleSetting = 0;
/* 2643 */     this.heldItemTooltips = true;
/* 2644 */     this.useVbo = false;
/* 2645 */     this.forceUnicodeFont = false;
/* 2646 */     this.ofFogType = 1;
/* 2647 */     this.ofFogStart = 0.8F;
/* 2648 */     this.ofMipmapType = 0;
/* 2649 */     this.ofOcclusionFancy = false;
/* 2650 */     this.ofSmartAnimations = false;
/* 2651 */     this.ofSmoothFps = false;
/* 2652 */     Config.updateAvailableProcessors();
/* 2653 */     this.ofSmoothWorld = Config.isSingleProcessor();
/* 2654 */     this.ofLazyChunkLoading = false;
/* 2655 */     this.ofRenderRegions = false;
/* 2656 */     this.ofFastMath = false;
/* 2657 */     this.ofFastRender = false;
/* 2658 */     this.ofTranslucentBlocks = 0;
/* 2659 */     this.ofDynamicFov = true;
/* 2660 */     this.ofAlternateBlocks = true;
/* 2661 */     this.ofDynamicLights = 3;
/* 2662 */     this.ofScreenshotSize = 1;
/* 2663 */     this.ofCustomEntityModels = true;
/* 2664 */     this.ofCustomGuis = true;
/* 2665 */     this.ofShowGlErrors = true;
/* 2666 */     this.ofAoLevel = 1.0F;
/* 2667 */     this.ofAaLevel = 0;
/* 2668 */     this.ofAfLevel = 1;
/* 2669 */     this.ofClouds = 0;
/* 2670 */     this.ofCloudsHeight = 0.0F;
/* 2671 */     this.ofTrees = 0;
/* 2672 */     this.ofRain = 0;
/* 2673 */     this.ofBetterGrass = 3;
/* 2674 */     this.ofAutoSaveTicks = 4000;
/* 2675 */     this.ofLagometer = false;
/* 2676 */     this.ofShowFps = false;
/* 2677 */     this.ofProfiler = false;
/* 2678 */     this.ofWeather = true;
/* 2679 */     this.ofSky = true;
/* 2680 */     this.ofStars = true;
/* 2681 */     this.ofSunMoon = true;
/* 2682 */     this.ofVignette = 0;
/* 2683 */     this.ofChunkUpdates = 1;
/* 2684 */     this.ofChunkUpdatesDynamic = false;
/* 2685 */     this.ofTime = 0;
/* 2686 */     this.ofClearWater = false;
/* 2687 */     this.ofBetterSnow = false;
/* 2688 */     this.ofFullscreenMode = "Default";
/* 2689 */     this.ofSwampColors = true;
/* 2690 */     this.ofRandomEntities = true;
/* 2691 */     this.ofSmoothBiomes = true;
/* 2692 */     this.ofCustomFonts = true;
/* 2693 */     this.ofCustomColors = true;
/* 2694 */     this.ofCustomItems = true;
/* 2695 */     this.ofCustomSky = true;
/* 2696 */     this.ofShowCapes = true;
/* 2697 */     this.ofConnectedTextures = 2;
/* 2698 */     this.ofNaturalTextures = false;
/* 2699 */     this.ofEmissiveTextures = true;
/* 2700 */     this.ofAnimatedWater = 0;
/* 2701 */     this.ofAnimatedLava = 0;
/* 2702 */     this.ofAnimatedFire = true;
/* 2703 */     this.ofAnimatedPortal = true;
/* 2704 */     this.ofAnimatedRedstone = true;
/* 2705 */     this.ofAnimatedExplosion = true;
/* 2706 */     this.ofAnimatedFlame = true;
/* 2707 */     this.ofAnimatedSmoke = true;
/* 2708 */     this.ofVoidParticles = true;
/* 2709 */     this.ofWaterParticles = true;
/* 2710 */     this.ofRainSplash = true;
/* 2711 */     this.ofPortalParticles = true;
/* 2712 */     this.ofPotionParticles = true;
/* 2713 */     this.ofFireworkParticles = true;
/* 2714 */     this.ofDrippingWaterLava = true;
/* 2715 */     this.ofAnimatedTerrain = true;
/* 2716 */     this.ofAnimatedTextures = true;
/* 2717 */     Shaders.setShaderPack("OFF");
/* 2718 */     Shaders.configAntialiasingLevel = 0;
/* 2719 */     Shaders.uninit();
/* 2720 */     Shaders.storeConfig();
/* 2721 */     updateWaterOpacity();
/* 2722 */     this.mc.refreshResources();
/* 2723 */     saveOptions();
/*      */   }
/*      */   
/*      */   public void updateVSync() {
/* 2727 */     Display.setVSyncEnabled(this.enableVsync);
/*      */   }
/*      */   
/*      */   private void updateWaterOpacity() {
/* 2731 */     if (Config.isIntegratedServerRunning()) {
/* 2732 */       Config.waterOpacityChanged = true;
/*      */     }
/*      */     
/* 2735 */     ClearWater.updateWaterOpacity(this, (World)this.mc.theWorld);
/*      */   }
/*      */   
/*      */   public void setAllAnimations(boolean p_setAllAnimations_1_) {
/* 2739 */     int i = p_setAllAnimations_1_ ? 0 : 2;
/* 2740 */     this.ofAnimatedWater = i;
/* 2741 */     this.ofAnimatedLava = i;
/* 2742 */     this.ofAnimatedFire = p_setAllAnimations_1_;
/* 2743 */     this.ofAnimatedPortal = p_setAllAnimations_1_;
/* 2744 */     this.ofAnimatedRedstone = p_setAllAnimations_1_;
/* 2745 */     this.ofAnimatedExplosion = p_setAllAnimations_1_;
/* 2746 */     this.ofAnimatedFlame = p_setAllAnimations_1_;
/* 2747 */     this.ofAnimatedSmoke = p_setAllAnimations_1_;
/* 2748 */     this.ofVoidParticles = p_setAllAnimations_1_;
/* 2749 */     this.ofWaterParticles = p_setAllAnimations_1_;
/* 2750 */     this.ofRainSplash = p_setAllAnimations_1_;
/* 2751 */     this.ofPortalParticles = p_setAllAnimations_1_;
/* 2752 */     this.ofPotionParticles = p_setAllAnimations_1_;
/* 2753 */     this.ofFireworkParticles = p_setAllAnimations_1_;
/* 2754 */     this.particleSetting = p_setAllAnimations_1_ ? 0 : 2;
/* 2755 */     this.ofDrippingWaterLava = p_setAllAnimations_1_;
/* 2756 */     this.ofAnimatedTerrain = p_setAllAnimations_1_;
/* 2757 */     this.ofAnimatedTextures = p_setAllAnimations_1_;
/*      */   }
/*      */   
/*      */   public enum Options {
/* 2761 */     INVERT_MOUSE("options.invertMouse", false, true),
/* 2762 */     SENSITIVITY("options.sensitivity", true, false),
/* 2763 */     FOV("options.fov", true, false, 30.0F, 110.0F, 1.0F),
/* 2764 */     GAMMA("options.gamma", true, false),
/* 2765 */     SATURATION("options.saturation", true, false),
/* 2766 */     RENDER_DISTANCE("options.renderDistance", true, false, 2.0F, 16.0F, 1.0F),
/* 2767 */     VIEW_BOBBING("options.viewBobbing", false, true),
/* 2768 */     ANAGLYPH("options.anaglyph", false, true),
/* 2769 */     FRAMERATE_LIMIT("options.framerateLimit", true, false, 0.0F, 260.0F, 5.0F),
/* 2770 */     FBO_ENABLE("options.fboEnable", false, true),
/* 2771 */     RENDER_CLOUDS("options.renderClouds", false, false),
/* 2772 */     GRAPHICS("options.graphics", false, false),
/* 2773 */     AMBIENT_OCCLUSION("options.ao", false, false),
/* 2774 */     GUI_SCALE("options.guiScale", false, false),
/* 2775 */     PARTICLES("options.particles", false, false),
/* 2776 */     CHAT_VISIBILITY("options.chat.visibility", false, false),
/* 2777 */     CHAT_COLOR("options.chat.color", false, true),
/* 2778 */     CHAT_LINKS("options.chat.links", false, true),
/* 2779 */     CHAT_OPACITY("options.chat.opacity", true, false),
/* 2780 */     CHAT_LINKS_PROMPT("options.chat.links.prompt", false, true),
/* 2781 */     SNOOPER_ENABLED("options.snooper", false, true),
/* 2782 */     USE_FULLSCREEN("options.fullscreen", false, true),
/* 2783 */     ENABLE_VSYNC("options.vsync", false, true),
/* 2784 */     USE_VBO("options.vbo", false, true),
/* 2785 */     TOUCHSCREEN("options.touchscreen", false, true),
/* 2786 */     CHAT_SCALE("options.chat.scale", true, false),
/* 2787 */     CHAT_WIDTH("options.chat.width", true, false),
/* 2788 */     CHAT_HEIGHT_FOCUSED("options.chat.height.focused", true, false),
/* 2789 */     CHAT_HEIGHT_UNFOCUSED("options.chat.height.unfocused", true, false),
/* 2790 */     MIPMAP_LEVELS("options.mipmapLevels", true, false, 0.0F, 4.0F, 1.0F),
/* 2791 */     FORCE_UNICODE_FONT("options.forceUnicodeFont", false, true),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2804 */     BLOCK_ALTERNATIVES("options.blockAlternatives", false, true),
/* 2805 */     REDUCED_DEBUG_INFO("options.reducedDebugInfo", false, true),
/* 2806 */     ENTITY_SHADOWS("options.entityShadows", false, true),
/*      */     
/* 2808 */     FOG_FANCY("of.options.FOG_FANCY", false, false),
/* 2809 */     FOG_START("of.options.FOG_START", false, false),
/* 2810 */     MIPMAP_TYPE("of.options.MIPMAP_TYPE", true, false, 0.0F, 3.0F, 1.0F),
/* 2811 */     SMOOTH_FPS("of.options.SMOOTH_FPS", false, false),
/* 2812 */     CLOUDS("of.options.CLOUDS", false, false),
/* 2813 */     CLOUD_HEIGHT("of.options.CLOUD_HEIGHT", true, false),
/* 2814 */     TREES("of.options.TREES", false, false),
/* 2815 */     RAIN("of.options.RAIN", false, false),
/* 2816 */     ANIMATED_WATER("of.options.ANIMATED_WATER", false, false),
/* 2817 */     ANIMATED_LAVA("of.options.ANIMATED_LAVA", false, false),
/* 2818 */     ANIMATED_FIRE("of.options.ANIMATED_FIRE", false, false),
/* 2819 */     ANIMATED_PORTAL("of.options.ANIMATED_PORTAL", false, false),
/* 2820 */     AO_LEVEL("of.options.AO_LEVEL", true, false),
/* 2821 */     LAGOMETER("of.options.LAGOMETER", false, false),
/* 2822 */     SHOW_FPS("of.options.SHOW_FPS", false, false),
/* 2823 */     AUTOSAVE_TICKS("of.options.AUTOSAVE_TICKS", false, false),
/* 2824 */     BETTER_GRASS("of.options.BETTER_GRASS", false, false),
/* 2825 */     ANIMATED_REDSTONE("of.options.ANIMATED_REDSTONE", false, false),
/* 2826 */     ANIMATED_EXPLOSION("of.options.ANIMATED_EXPLOSION", false, false),
/* 2827 */     ANIMATED_FLAME("of.options.ANIMATED_FLAME", false, false),
/* 2828 */     ANIMATED_SMOKE("of.options.ANIMATED_SMOKE", false, false),
/* 2829 */     WEATHER("of.options.WEATHER", false, false),
/* 2830 */     SKY("of.options.SKY", false, false),
/* 2831 */     STARS("of.options.STARS", false, false),
/* 2832 */     SUN_MOON("of.options.SUN_MOON", false, false),
/* 2833 */     VIGNETTE("of.options.VIGNETTE", false, false),
/* 2834 */     CHUNK_UPDATES("of.options.CHUNK_UPDATES", false, false),
/* 2835 */     CHUNK_UPDATES_DYNAMIC("of.options.CHUNK_UPDATES_DYNAMIC", false, false),
/* 2836 */     TIME("of.options.TIME", false, false),
/* 2837 */     CLEAR_WATER("of.options.CLEAR_WATER", false, false),
/* 2838 */     SMOOTH_WORLD("of.options.SMOOTH_WORLD", false, false),
/* 2839 */     VOID_PARTICLES("of.options.VOID_PARTICLES", false, false),
/* 2840 */     WATER_PARTICLES("of.options.WATER_PARTICLES", false, false),
/* 2841 */     RAIN_SPLASH("of.options.RAIN_SPLASH", false, false),
/* 2842 */     PORTAL_PARTICLES("of.options.PORTAL_PARTICLES", false, false),
/* 2843 */     POTION_PARTICLES("of.options.POTION_PARTICLES", false, false),
/* 2844 */     FIREWORK_PARTICLES("of.options.FIREWORK_PARTICLES", false, false),
/* 2845 */     PROFILER("of.options.PROFILER", false, false),
/* 2846 */     DRIPPING_WATER_LAVA("of.options.DRIPPING_WATER_LAVA", false, false),
/* 2847 */     BETTER_SNOW("of.options.BETTER_SNOW", false, false),
/* 2848 */     FULLSCREEN_MODE("of.options.FULLSCREEN_MODE", true, false, 0.0F, (Config.getDisplayModes()).length, 1.0F),
/* 2849 */     ANIMATED_TERRAIN("of.options.ANIMATED_TERRAIN", false, false),
/* 2850 */     SWAMP_COLORS("of.options.SWAMP_COLORS", false, false),
/* 2851 */     RANDOM_ENTITIES("of.options.RANDOM_ENTITIES", false, false),
/* 2852 */     SMOOTH_BIOMES("of.options.SMOOTH_BIOMES", false, false),
/* 2853 */     CUSTOM_FONTS("of.options.CUSTOM_FONTS", false, false),
/* 2854 */     CUSTOM_COLORS("of.options.CUSTOM_COLORS", false, false),
/* 2855 */     SHOW_CAPES("of.options.SHOW_CAPES", false, false),
/* 2856 */     CONNECTED_TEXTURES("of.options.CONNECTED_TEXTURES", false, false),
/* 2857 */     CUSTOM_ITEMS("of.options.CUSTOM_ITEMS", false, false),
/* 2858 */     AA_LEVEL("of.options.AA_LEVEL", true, false, 0.0F, 16.0F, 1.0F),
/* 2859 */     AF_LEVEL("of.options.AF_LEVEL", true, false, 1.0F, 16.0F, 1.0F),
/* 2860 */     ANIMATED_TEXTURES("of.options.ANIMATED_TEXTURES", false, false),
/* 2861 */     NATURAL_TEXTURES("of.options.NATURAL_TEXTURES", false, false),
/* 2862 */     EMISSIVE_TEXTURES("of.options.EMISSIVE_TEXTURES", false, false),
/* 2863 */     HELD_ITEM_TOOLTIPS("of.options.HELD_ITEM_TOOLTIPS", false, false),
/* 2864 */     DROPPED_ITEMS("of.options.DROPPED_ITEMS", false, false),
/* 2865 */     LAZY_CHUNK_LOADING("of.options.LAZY_CHUNK_LOADING", false, false),
/* 2866 */     CUSTOM_SKY("of.options.CUSTOM_SKY", false, false),
/* 2867 */     FAST_MATH("of.options.FAST_MATH", false, false),
/* 2868 */     FAST_RENDER("of.options.FAST_RENDER", false, false),
/* 2869 */     TRANSLUCENT_BLOCKS("of.options.TRANSLUCENT_BLOCKS", false, false),
/* 2870 */     DYNAMIC_FOV("of.options.DYNAMIC_FOV", false, false),
/* 2871 */     DYNAMIC_LIGHTS("of.options.DYNAMIC_LIGHTS", false, false),
/* 2872 */     ALTERNATE_BLOCKS("of.options.ALTERNATE_BLOCKS", false, false),
/* 2873 */     CUSTOM_ENTITY_MODELS("of.options.CUSTOM_ENTITY_MODELS", false, false),
/* 2874 */     ADVANCED_TOOLTIPS("of.options.ADVANCED_TOOLTIPS", false, false),
/* 2875 */     SCREENSHOT_SIZE("of.options.SCREENSHOT_SIZE", false, false),
/* 2876 */     CUSTOM_GUIS("of.options.CUSTOM_GUIS", false, false),
/* 2877 */     RENDER_REGIONS("of.options.RENDER_REGIONS", false, false),
/* 2878 */     SHOW_GL_ERRORS("of.options.SHOW_GL_ERRORS", false, false),
/* 2879 */     SMART_ANIMATIONS("of.options.SMART_ANIMATIONS", false, false);
/*      */     
/*      */     private final boolean enumFloat;
/*      */     
/*      */     private final boolean enumBoolean;
/*      */     
/*      */     private final String enumString;
/*      */     
/*      */     private final float valueStep;
/*      */     
/*      */     float valueMin;
/*      */     float valueMax;
/*      */     
/*      */     Options(String str, boolean isFloat, boolean isBoolean, float valMin, float valMax, float valStep) {
/* 2893 */       this.enumString = str;
/* 2894 */       this.enumFloat = isFloat;
/* 2895 */       this.enumBoolean = isBoolean;
/* 2896 */       this.valueMin = valMin;
/* 2897 */       this.valueMax = valMax;
/* 2898 */       this.valueStep = valStep;
/*      */     }
/*      */     
/*      */     public static Options getEnumOptions(int ordinal) {
/* 2902 */       for (Options gamesettings$options : values()) {
/* 2903 */         if (gamesettings$options.returnEnumOrdinal() == ordinal) {
/* 2904 */           return gamesettings$options;
/*      */         }
/*      */       } 
/*      */       
/* 2908 */       return null;
/*      */     }
/*      */     
/*      */     public boolean getEnumFloat() {
/* 2912 */       return this.enumFloat;
/*      */     }
/*      */     
/*      */     public boolean getEnumBoolean() {
/* 2916 */       return this.enumBoolean;
/*      */     }
/*      */     
/*      */     public int returnEnumOrdinal() {
/* 2920 */       return ordinal();
/*      */     }
/*      */     
/*      */     public String getEnumString() {
/* 2924 */       return this.enumString;
/*      */     }
/*      */     
/*      */     public float getValueMax() {
/* 2928 */       return this.valueMax;
/*      */     }
/*      */     
/*      */     public void setValueMax(float value) {
/* 2932 */       this.valueMax = value;
/*      */     }
/*      */     
/*      */     public float normalizeValue(float value) {
/* 2936 */       return MathHelper.clamp_float((snapToStepClamp(value) - this.valueMin) / (this.valueMax - this.valueMin), 0.0F, 1.0F);
/*      */     }
/*      */     
/*      */     public float denormalizeValue(float value) {
/* 2940 */       return snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.clamp_float(value, 0.0F, 1.0F));
/*      */     }
/*      */     
/*      */     public float snapToStepClamp(float value) {
/* 2944 */       value = snapToStep(value);
/* 2945 */       return MathHelper.clamp_float(value, this.valueMin, this.valueMax);
/*      */     }
/*      */     
/*      */     protected float snapToStep(float value) {
/* 2949 */       if (this.valueStep > 0.0F) {
/* 2950 */         value = this.valueStep * Math.round(value / this.valueStep);
/*      */       }
/*      */       
/* 2953 */       return value;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\settings\GameSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */