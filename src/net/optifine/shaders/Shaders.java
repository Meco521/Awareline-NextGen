/*      */ package net.optifine.shaders;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.nio.file.attribute.BasicFileAttributes;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Deque;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.World;
/*      */ import net.optifine.config.ConnectedParser;
/*      */ import net.optifine.shaders.config.EnumShaderOption;
/*      */ import net.optifine.shaders.config.MacroState;
/*      */ import net.optifine.shaders.config.PropertyDefaultTrueFalse;
/*      */ import net.optifine.shaders.config.RenderScale;
/*      */ import net.optifine.shaders.config.ScreenShaderOptions;
/*      */ import net.optifine.shaders.config.ShaderLine;
/*      */ import net.optifine.shaders.config.ShaderOption;
/*      */ import net.optifine.shaders.config.ShaderPackParser;
/*      */ import net.optifine.shaders.config.ShaderParser;
/*      */ import net.optifine.shaders.uniform.ShaderUniform1f;
/*      */ import net.optifine.shaders.uniform.ShaderUniform1i;
/*      */ import net.optifine.shaders.uniform.ShaderUniform2i;
/*      */ import net.optifine.shaders.uniform.ShaderUniform3f;
/*      */ import net.optifine.shaders.uniform.ShaderUniformM4;
/*      */ import net.optifine.texture.InternalFormat;
/*      */ import net.optifine.texture.PixelFormat;
/*      */ import net.optifine.texture.PixelType;
/*      */ import net.optifine.texture.TextureType;
/*      */ import net.optifine.util.PropertiesOrdered;
/*      */ import net.optifine.util.StrUtils;
/*      */ import org.lwjgl.opengl.ARBShaderObjects;
/*      */ import org.lwjgl.opengl.EXTFramebufferObject;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GL20;
/*      */ import org.lwjgl.util.vector.Vector4f;
/*      */ 
/*      */ public class Shaders {
/*   61 */   static Minecraft mc = Minecraft.getMinecraft();
/*      */   static EntityRenderer entityRenderer;
/*      */   public static boolean isInitializedOnce = false;
/*      */   public static boolean isShaderPackInitialized = false;
/*      */   public static ContextCapabilities capabilities;
/*      */   public static String glVersionString;
/*      */   public static String glVendorString;
/*      */   public static String glRendererString;
/*      */   public static boolean hasGlGenMipmap = false;
/*   70 */   public static int countResetDisplayLists = 0;
/*   71 */   private static int renderDisplayWidth = 0;
/*   72 */   private static int renderDisplayHeight = 0;
/*   73 */   public static int renderWidth = 0;
/*   74 */   public static int renderHeight = 0;
/*      */   public static boolean isRenderingWorld = false;
/*      */   public static boolean isRenderingSky = false;
/*      */   public static boolean isCompositeRendered = false;
/*      */   public static boolean isRenderingDfb = false;
/*      */   public static boolean isShadowPass = false;
/*      */   public static boolean isEntitiesGlowing = false;
/*      */   public static boolean isSleeping;
/*      */   private static boolean isRenderingFirstPersonHand;
/*      */   private static boolean isHandRenderedMain;
/*      */   private static boolean isHandRenderedOff;
/*      */   private static boolean skipRenderHandMain;
/*      */   private static boolean skipRenderHandOff;
/*      */   public static boolean renderItemKeepDepthMask = false;
/*      */   public static boolean itemToRenderMainTranslucent = false;
/*      */   public static boolean itemToRenderOffTranslucent = false;
/*   90 */   static float[] sunPosition = new float[4];
/*   91 */   static float[] moonPosition = new float[4];
/*   92 */   static float[] shadowLightPosition = new float[4];
/*   93 */   static float[] upPosition = new float[4];
/*   94 */   static float[] shadowLightPositionVector = new float[4];
/*   95 */   static float[] upPosModelView = new float[] { 0.0F, 100.0F, 0.0F, 0.0F };
/*   96 */   static float[] sunPosModelView = new float[] { 0.0F, 100.0F, 0.0F, 0.0F };
/*   97 */   static float[] moonPosModelView = new float[] { 0.0F, -100.0F, 0.0F, 0.0F };
/*   98 */   private static final float[] tempMat = new float[16];
/*      */   static float clearColorR;
/*      */   static float clearColorG;
/*      */   static float clearColorB;
/*      */   static float skyColorR;
/*      */   static float skyColorG;
/*      */   static float skyColorB;
/*  105 */   static long worldTime = 0L;
/*  106 */   static long lastWorldTime = 0L;
/*  107 */   static long diffWorldTime = 0L;
/*  108 */   static float celestialAngle = 0.0F;
/*  109 */   static float sunAngle = 0.0F;
/*  110 */   static float shadowAngle = 0.0F;
/*  111 */   static int moonPhase = 0;
/*  112 */   static long systemTime = 0L;
/*  113 */   static long lastSystemTime = 0L;
/*  114 */   static long diffSystemTime = 0L;
/*  115 */   static int frameCounter = 0;
/*  116 */   static float frameTime = 0.0F;
/*  117 */   static float frameTimeCounter = 0.0F;
/*  118 */   static int systemTimeInt32 = 0;
/*  119 */   static float rainStrength = 0.0F;
/*  120 */   static float wetness = 0.0F;
/*  121 */   public static float wetnessHalfLife = 600.0F;
/*  122 */   public static float drynessHalfLife = 200.0F;
/*  123 */   public static float eyeBrightnessHalflife = 10.0F;
/*      */   static boolean usewetness = false;
/*  125 */   static int isEyeInWater = 0;
/*  126 */   static int eyeBrightness = 0;
/*  127 */   static float eyeBrightnessFadeX = 0.0F;
/*  128 */   static float eyeBrightnessFadeY = 0.0F;
/*  129 */   static float eyePosY = 0.0F;
/*  130 */   static float centerDepth = 0.0F;
/*  131 */   static float centerDepthSmooth = 0.0F;
/*  132 */   static float centerDepthSmoothHalflife = 1.0F;
/*      */   static boolean centerDepthSmoothEnabled = false;
/*  134 */   static int superSamplingLevel = 1;
/*  135 */   static float nightVision = 0.0F;
/*  136 */   static float blindness = 0.0F;
/*      */   static boolean lightmapEnabled = false;
/*      */   static boolean fogEnabled = true;
/*  139 */   public static int entityAttrib = 10;
/*  140 */   public static int midTexCoordAttrib = 11;
/*  141 */   public static int tangentAttrib = 12;
/*      */   public static boolean useEntityAttrib = false;
/*      */   public static boolean useMidTexCoordAttrib = false;
/*      */   public static boolean useTangentAttrib = false;
/*      */   public static boolean progUseEntityAttrib = false;
/*      */   public static boolean progUseMidTexCoordAttrib = false;
/*      */   public static boolean progUseTangentAttrib = false;
/*      */   private static boolean progArbGeometryShader4 = false;
/*  149 */   private static int progMaxVerticesOut = 3;
/*      */   private static boolean hasGeometryShaders = false;
/*  151 */   public static int atlasSizeX = 0;
/*  152 */   public static int atlasSizeY = 0;
/*  153 */   private static final ShaderUniforms shaderUniforms = new ShaderUniforms();
/*  154 */   public static ShaderUniform4f uniform_entityColor = shaderUniforms.make4f("entityColor");
/*  155 */   public static ShaderUniform1i uniform_entityId = shaderUniforms.make1i("entityId");
/*  156 */   public static ShaderUniform1i uniform_blockEntityId = shaderUniforms.make1i("blockEntityId");
/*  157 */   public static ShaderUniform1i uniform_texture = shaderUniforms.make1i("texture");
/*  158 */   public static ShaderUniform1i uniform_lightmap = shaderUniforms.make1i("lightmap");
/*  159 */   public static ShaderUniform1i uniform_normals = shaderUniforms.make1i("normals");
/*  160 */   public static ShaderUniform1i uniform_specular = shaderUniforms.make1i("specular");
/*  161 */   public static ShaderUniform1i uniform_shadow = shaderUniforms.make1i("shadow");
/*  162 */   public static ShaderUniform1i uniform_watershadow = shaderUniforms.make1i("watershadow");
/*  163 */   public static ShaderUniform1i uniform_shadowtex0 = shaderUniforms.make1i("shadowtex0");
/*  164 */   public static ShaderUniform1i uniform_shadowtex1 = shaderUniforms.make1i("shadowtex1");
/*  165 */   public static ShaderUniform1i uniform_depthtex0 = shaderUniforms.make1i("depthtex0");
/*  166 */   public static ShaderUniform1i uniform_depthtex1 = shaderUniforms.make1i("depthtex1");
/*  167 */   public static ShaderUniform1i uniform_shadowcolor = shaderUniforms.make1i("shadowcolor");
/*  168 */   public static ShaderUniform1i uniform_shadowcolor0 = shaderUniforms.make1i("shadowcolor0");
/*  169 */   public static ShaderUniform1i uniform_shadowcolor1 = shaderUniforms.make1i("shadowcolor1");
/*  170 */   public static ShaderUniform1i uniform_noisetex = shaderUniforms.make1i("noisetex");
/*  171 */   public static ShaderUniform1i uniform_gcolor = shaderUniforms.make1i("gcolor");
/*  172 */   public static ShaderUniform1i uniform_gdepth = shaderUniforms.make1i("gdepth");
/*  173 */   public static ShaderUniform1i uniform_gnormal = shaderUniforms.make1i("gnormal");
/*  174 */   public static ShaderUniform1i uniform_composite = shaderUniforms.make1i("composite");
/*  175 */   public static ShaderUniform1i uniform_gaux1 = shaderUniforms.make1i("gaux1");
/*  176 */   public static ShaderUniform1i uniform_gaux2 = shaderUniforms.make1i("gaux2");
/*  177 */   public static ShaderUniform1i uniform_gaux3 = shaderUniforms.make1i("gaux3");
/*  178 */   public static ShaderUniform1i uniform_gaux4 = shaderUniforms.make1i("gaux4");
/*  179 */   public static ShaderUniform1i uniform_colortex0 = shaderUniforms.make1i("colortex0");
/*  180 */   public static ShaderUniform1i uniform_colortex1 = shaderUniforms.make1i("colortex1");
/*  181 */   public static ShaderUniform1i uniform_colortex2 = shaderUniforms.make1i("colortex2");
/*  182 */   public static ShaderUniform1i uniform_colortex3 = shaderUniforms.make1i("colortex3");
/*  183 */   public static ShaderUniform1i uniform_colortex4 = shaderUniforms.make1i("colortex4");
/*  184 */   public static ShaderUniform1i uniform_colortex5 = shaderUniforms.make1i("colortex5");
/*  185 */   public static ShaderUniform1i uniform_colortex6 = shaderUniforms.make1i("colortex6");
/*  186 */   public static ShaderUniform1i uniform_colortex7 = shaderUniforms.make1i("colortex7");
/*  187 */   public static ShaderUniform1i uniform_gdepthtex = shaderUniforms.make1i("gdepthtex");
/*  188 */   public static ShaderUniform1i uniform_depthtex2 = shaderUniforms.make1i("depthtex2");
/*  189 */   public static ShaderUniform1i uniform_tex = shaderUniforms.make1i("tex");
/*  190 */   public static ShaderUniform1i uniform_heldItemId = shaderUniforms.make1i("heldItemId");
/*  191 */   public static ShaderUniform1i uniform_heldBlockLightValue = shaderUniforms.make1i("heldBlockLightValue");
/*  192 */   public static ShaderUniform1i uniform_heldItemId2 = shaderUniforms.make1i("heldItemId2");
/*  193 */   public static ShaderUniform1i uniform_heldBlockLightValue2 = shaderUniforms.make1i("heldBlockLightValue2");
/*  194 */   public static ShaderUniform1i uniform_fogMode = shaderUniforms.make1i("fogMode");
/*  195 */   public static ShaderUniform1f uniform_fogDensity = shaderUniforms.make1f("fogDensity");
/*  196 */   public static ShaderUniform3f uniform_fogColor = shaderUniforms.make3f("fogColor");
/*  197 */   public static ShaderUniform3f uniform_skyColor = shaderUniforms.make3f("skyColor");
/*  198 */   public static ShaderUniform1i uniform_worldTime = shaderUniforms.make1i("worldTime");
/*  199 */   public static ShaderUniform1i uniform_worldDay = shaderUniforms.make1i("worldDay");
/*  200 */   public static ShaderUniform1i uniform_moonPhase = shaderUniforms.make1i("moonPhase");
/*  201 */   public static ShaderUniform1i uniform_frameCounter = shaderUniforms.make1i("frameCounter");
/*  202 */   public static ShaderUniform1f uniform_frameTime = shaderUniforms.make1f("frameTime");
/*  203 */   public static ShaderUniform1f uniform_frameTimeCounter = shaderUniforms.make1f("frameTimeCounter");
/*  204 */   public static ShaderUniform1f uniform_sunAngle = shaderUniforms.make1f("sunAngle");
/*  205 */   public static ShaderUniform1f uniform_shadowAngle = shaderUniforms.make1f("shadowAngle");
/*  206 */   public static ShaderUniform1f uniform_rainStrength = shaderUniforms.make1f("rainStrength");
/*  207 */   public static ShaderUniform1f uniform_aspectRatio = shaderUniforms.make1f("aspectRatio");
/*  208 */   public static ShaderUniform1f uniform_viewWidth = shaderUniforms.make1f("viewWidth");
/*  209 */   public static ShaderUniform1f uniform_viewHeight = shaderUniforms.make1f("viewHeight");
/*  210 */   public static ShaderUniform1f uniform_near = shaderUniforms.make1f("near");
/*  211 */   public static ShaderUniform1f uniform_far = shaderUniforms.make1f("far");
/*  212 */   public static ShaderUniform3f uniform_sunPosition = shaderUniforms.make3f("sunPosition");
/*  213 */   public static ShaderUniform3f uniform_moonPosition = shaderUniforms.make3f("moonPosition");
/*  214 */   public static ShaderUniform3f uniform_shadowLightPosition = shaderUniforms.make3f("shadowLightPosition");
/*  215 */   public static ShaderUniform3f uniform_upPosition = shaderUniforms.make3f("upPosition");
/*  216 */   public static ShaderUniform3f uniform_previousCameraPosition = shaderUniforms.make3f("previousCameraPosition");
/*  217 */   public static ShaderUniform3f uniform_cameraPosition = shaderUniforms.make3f("cameraPosition");
/*  218 */   public static ShaderUniformM4 uniform_gbufferModelView = shaderUniforms.makeM4("gbufferModelView");
/*  219 */   public static ShaderUniformM4 uniform_gbufferModelViewInverse = shaderUniforms.makeM4("gbufferModelViewInverse");
/*  220 */   public static ShaderUniformM4 uniform_gbufferPreviousProjection = shaderUniforms.makeM4("gbufferPreviousProjection");
/*  221 */   public static ShaderUniformM4 uniform_gbufferProjection = shaderUniforms.makeM4("gbufferProjection");
/*  222 */   public static ShaderUniformM4 uniform_gbufferProjectionInverse = shaderUniforms.makeM4("gbufferProjectionInverse");
/*  223 */   public static ShaderUniformM4 uniform_gbufferPreviousModelView = shaderUniforms.makeM4("gbufferPreviousModelView");
/*  224 */   public static ShaderUniformM4 uniform_shadowProjection = shaderUniforms.makeM4("shadowProjection");
/*  225 */   public static ShaderUniformM4 uniform_shadowProjectionInverse = shaderUniforms.makeM4("shadowProjectionInverse");
/*  226 */   public static ShaderUniformM4 uniform_shadowModelView = shaderUniforms.makeM4("shadowModelView");
/*  227 */   public static ShaderUniformM4 uniform_shadowModelViewInverse = shaderUniforms.makeM4("shadowModelViewInverse");
/*  228 */   public static ShaderUniform1f uniform_wetness = shaderUniforms.make1f("wetness");
/*  229 */   public static ShaderUniform1f uniform_eyeAltitude = shaderUniforms.make1f("eyeAltitude");
/*  230 */   public static ShaderUniform2i uniform_eyeBrightness = shaderUniforms.make2i("eyeBrightness");
/*  231 */   public static ShaderUniform2i uniform_eyeBrightnessSmooth = shaderUniforms.make2i("eyeBrightnessSmooth");
/*  232 */   public static ShaderUniform2i uniform_terrainTextureSize = shaderUniforms.make2i("terrainTextureSize");
/*  233 */   public static ShaderUniform1i uniform_terrainIconSize = shaderUniforms.make1i("terrainIconSize");
/*  234 */   public static ShaderUniform1i uniform_isEyeInWater = shaderUniforms.make1i("isEyeInWater");
/*  235 */   public static ShaderUniform1f uniform_nightVision = shaderUniforms.make1f("nightVision");
/*  236 */   public static ShaderUniform1f uniform_blindness = shaderUniforms.make1f("blindness");
/*  237 */   public static ShaderUniform1f uniform_screenBrightness = shaderUniforms.make1f("screenBrightness");
/*  238 */   public static ShaderUniform1i uniform_hideGUI = shaderUniforms.make1i("hideGUI");
/*  239 */   public static ShaderUniform1f uniform_centerDepthSmooth = shaderUniforms.make1f("centerDepthSmooth");
/*  240 */   public static ShaderUniform2i uniform_atlasSize = shaderUniforms.make2i("atlasSize");
/*  241 */   public static ShaderUniform4i uniform_blendFunc = shaderUniforms.make4i("blendFunc");
/*  242 */   public static ShaderUniform1i uniform_instanceId = shaderUniforms.make1i("instanceId");
/*      */   static double previousCameraPositionX;
/*      */   static double previousCameraPositionY;
/*      */   static double previousCameraPositionZ;
/*      */   static double cameraPositionX;
/*      */   static double cameraPositionY;
/*      */   static double cameraPositionZ;
/*      */   static int cameraOffsetX;
/*      */   static int cameraOffsetZ;
/*  251 */   static int shadowPassInterval = 0;
/*      */   public static boolean needResizeShadow = false;
/*  253 */   static int shadowMapWidth = 1024;
/*  254 */   static int shadowMapHeight = 1024;
/*  255 */   static int spShadowMapWidth = 1024;
/*  256 */   static int spShadowMapHeight = 1024;
/*  257 */   static float shadowMapFOV = 90.0F;
/*  258 */   static float shadowMapHalfPlane = 160.0F;
/*      */   static boolean shadowMapIsOrtho = true;
/*  260 */   static float shadowDistanceRenderMul = -1.0F;
/*  261 */   static int shadowPassCounter = 0;
/*      */   static int preShadowPassThirdPersonView;
/*      */   public static boolean shouldSkipDefaultShadow = false;
/*      */   static boolean waterShadowEnabled = false;
/*      */   static final int MaxDrawBuffers = 8;
/*      */   static final int MaxColorBuffers = 8;
/*      */   static final int MaxDepthBuffers = 3;
/*      */   static final int MaxShadowColorBuffers = 8;
/*      */   static final int MaxShadowDepthBuffers = 2;
/*  270 */   static int usedColorBuffers = 0;
/*  271 */   static int usedDepthBuffers = 0;
/*  272 */   static int usedShadowColorBuffers = 0;
/*  273 */   static int usedShadowDepthBuffers = 0;
/*  274 */   static int usedColorAttachs = 0;
/*  275 */   static int usedDrawBuffers = 0;
/*  276 */   static int dfb = 0;
/*  277 */   static int sfb = 0;
/*  278 */   private static final int[] gbuffersFormat = new int[8];
/*  279 */   public static boolean[] gbuffersClear = new boolean[8];
/*  280 */   public static Vector4f[] gbuffersClearColor = new Vector4f[8];
/*  281 */   private static final Programs programs = new Programs();
/*  282 */   public static final Program ProgramNone = programs.getProgramNone();
/*  283 */   public static final Program ProgramShadow = programs.makeShadow("shadow", ProgramNone);
/*  284 */   public static final Program ProgramShadowSolid = programs.makeShadow("shadow_solid", ProgramShadow);
/*  285 */   public static final Program ProgramShadowCutout = programs.makeShadow("shadow_cutout", ProgramShadow);
/*  286 */   public static final Program ProgramBasic = programs.makeGbuffers("gbuffers_basic", ProgramNone);
/*  287 */   public static final Program ProgramTextured = programs.makeGbuffers("gbuffers_textured", ProgramBasic);
/*  288 */   public static final Program ProgramTexturedLit = programs.makeGbuffers("gbuffers_textured_lit", ProgramTextured);
/*  289 */   public static final Program ProgramSkyBasic = programs.makeGbuffers("gbuffers_skybasic", ProgramBasic);
/*  290 */   public static final Program ProgramSkyTextured = programs.makeGbuffers("gbuffers_skytextured", ProgramTextured);
/*  291 */   public static final Program ProgramClouds = programs.makeGbuffers("gbuffers_clouds", ProgramTextured);
/*  292 */   public static final Program ProgramTerrain = programs.makeGbuffers("gbuffers_terrain", ProgramTexturedLit);
/*  293 */   public static final Program ProgramTerrainSolid = programs.makeGbuffers("gbuffers_terrain_solid", ProgramTerrain);
/*  294 */   public static final Program ProgramTerrainCutoutMip = programs.makeGbuffers("gbuffers_terrain_cutout_mip", ProgramTerrain);
/*  295 */   public static final Program ProgramTerrainCutout = programs.makeGbuffers("gbuffers_terrain_cutout", ProgramTerrain);
/*  296 */   public static final Program ProgramDamagedBlock = programs.makeGbuffers("gbuffers_damagedblock", ProgramTerrain);
/*  297 */   public static final Program ProgramBlock = programs.makeGbuffers("gbuffers_block", ProgramTerrain);
/*  298 */   public static final Program ProgramBeaconBeam = programs.makeGbuffers("gbuffers_beaconbeam", ProgramTextured);
/*  299 */   public static final Program ProgramItem = programs.makeGbuffers("gbuffers_item", ProgramTexturedLit);
/*  300 */   public static final Program ProgramEntities = programs.makeGbuffers("gbuffers_entities", ProgramTexturedLit);
/*  301 */   public static final Program ProgramEntitiesGlowing = programs.makeGbuffers("gbuffers_entities_glowing", ProgramEntities);
/*  302 */   public static final Program ProgramArmorGlint = programs.makeGbuffers("gbuffers_armor_glint", ProgramTextured);
/*  303 */   public static final Program ProgramSpiderEyes = programs.makeGbuffers("gbuffers_spidereyes", ProgramTextured);
/*  304 */   public static final Program ProgramHand = programs.makeGbuffers("gbuffers_hand", ProgramTexturedLit);
/*  305 */   public static final Program ProgramWeather = programs.makeGbuffers("gbuffers_weather", ProgramTexturedLit);
/*  306 */   public static final Program ProgramDeferredPre = programs.makeVirtual("deferred_pre");
/*  307 */   public static final Program[] ProgramsDeferred = programs.makeDeferreds("deferred", 16);
/*  308 */   public static final Program ProgramDeferred = ProgramsDeferred[0];
/*  309 */   public static final Program ProgramWater = programs.makeGbuffers("gbuffers_water", ProgramTerrain);
/*  310 */   public static final Program ProgramHandWater = programs.makeGbuffers("gbuffers_hand_water", ProgramHand);
/*  311 */   public static final Program ProgramCompositePre = programs.makeVirtual("composite_pre");
/*  312 */   public static final Program[] ProgramsComposite = programs.makeComposites("composite", 16);
/*  313 */   public static final Program ProgramComposite = ProgramsComposite[0];
/*  314 */   public static final Program ProgramFinal = programs.makeComposite("final");
/*  315 */   public static final int ProgramCount = programs.getCount();
/*  316 */   public static final Program[] ProgramsAll = programs.getPrograms();
/*  317 */   public static Program activeProgram = ProgramNone;
/*  318 */   public static int activeProgramID = 0;
/*  319 */   private static final ProgramStack programStack = new ProgramStack();
/*      */   private static boolean hasDeferredPrograms = false;
/*  321 */   static IntBuffer activeDrawBuffers = null;
/*  322 */   private static int activeCompositeMipmapSetting = 0;
/*  323 */   public static Properties loadedShaders = null;
/*  324 */   public static Properties shadersConfig = null;
/*  325 */   public static ITextureObject defaultTexture = null;
/*  326 */   public static boolean[] shadowHardwareFilteringEnabled = new boolean[2];
/*  327 */   public static boolean[] shadowMipmapEnabled = new boolean[2];
/*  328 */   public static boolean[] shadowFilterNearest = new boolean[2];
/*  329 */   public static boolean[] shadowColorMipmapEnabled = new boolean[8];
/*  330 */   public static boolean[] shadowColorFilterNearest = new boolean[8];
/*      */   public static boolean configTweakBlockDamage = false;
/*      */   public static boolean configCloudShadow = false;
/*  333 */   public static float configHandDepthMul = 0.125F;
/*  334 */   public static float configRenderResMul = 1.0F;
/*  335 */   public static float configShadowResMul = 1.0F;
/*  336 */   public static int configTexMinFilB = 0;
/*  337 */   public static int configTexMinFilN = 0;
/*  338 */   public static int configTexMinFilS = 0;
/*  339 */   public static int configTexMagFilB = 0;
/*  340 */   public static int configTexMagFilN = 0;
/*  341 */   public static int configTexMagFilS = 0;
/*      */   public static boolean configShadowClipFrustrum = true;
/*      */   public static boolean configNormalMap = true;
/*      */   public static boolean configSpecularMap = true;
/*  345 */   public static PropertyDefaultTrueFalse configOldLighting = new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 0);
/*  346 */   public static PropertyDefaultTrueFalse configOldHandLight = new PropertyDefaultTrueFalse("oldHandLight", "Old Hand Light", 0);
/*  347 */   public static int configAntialiasingLevel = 0;
/*      */   public static final int texMinFilRange = 3;
/*      */   public static final int texMagFilRange = 2;
/*  350 */   public static final String[] texMinFilDesc = new String[] { "Nearest", "Nearest-Nearest", "Nearest-Linear" };
/*  351 */   public static final String[] texMagFilDesc = new String[] { "Nearest", "Linear" };
/*  352 */   public static final int[] texMinFilValue = new int[] { 9728, 9984, 9986 };
/*  353 */   public static final int[] texMagFilValue = new int[] { 9728, 9729 };
/*  354 */   private static IShaderPack shaderPack = null;
/*      */   public static boolean shaderPackLoaded = false;
/*      */   public static String currentShaderName;
/*      */   public static final String SHADER_PACK_NAME_NONE = "OFF";
/*      */   public static final String SHADER_PACK_NAME_DEFAULT = "(internal)";
/*      */   public static final String SHADER_PACKS_DIR_NAME = "shaderpacks";
/*      */   public static final String OPTIONS_FILE_NAME = "optionsshaders.txt";
/*      */   public static final File shaderPacksDir;
/*      */   static File configFile;
/*  363 */   private static ShaderOption[] shaderPackOptions = null;
/*  364 */   private static Set<String> shaderPackOptionSliders = null;
/*  365 */   static ShaderProfile[] shaderPackProfiles = null;
/*  366 */   static Map<String, ScreenShaderOptions> shaderPackGuiScreens = null;
/*  367 */   static Map<String, IExpressionBool> shaderPackProgramConditions = new HashMap<>();
/*      */   public static final String PATH_SHADERS_PROPERTIES = "/shaders/shaders.properties";
/*  369 */   public static PropertyDefaultFastFancyOff shaderPackClouds = new PropertyDefaultFastFancyOff("clouds", "Clouds", 0);
/*  370 */   public static PropertyDefaultTrueFalse shaderPackOldLighting = new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 0);
/*  371 */   public static PropertyDefaultTrueFalse shaderPackOldHandLight = new PropertyDefaultTrueFalse("oldHandLight", "Old Hand Light", 0);
/*  372 */   public static PropertyDefaultTrueFalse shaderPackDynamicHandLight = new PropertyDefaultTrueFalse("dynamicHandLight", "Dynamic Hand Light", 0);
/*  373 */   public static PropertyDefaultTrueFalse shaderPackShadowTranslucent = new PropertyDefaultTrueFalse("shadowTranslucent", "Shadow Translucent", 0);
/*  374 */   public static PropertyDefaultTrueFalse shaderPackUnderwaterOverlay = new PropertyDefaultTrueFalse("underwaterOverlay", "Underwater Overlay", 0);
/*  375 */   public static PropertyDefaultTrueFalse shaderPackSun = new PropertyDefaultTrueFalse("sun", "Sun", 0);
/*  376 */   public static PropertyDefaultTrueFalse shaderPackMoon = new PropertyDefaultTrueFalse("moon", "Moon", 0);
/*  377 */   public static PropertyDefaultTrueFalse shaderPackVignette = new PropertyDefaultTrueFalse("vignette", "Vignette", 0);
/*  378 */   public static PropertyDefaultTrueFalse shaderPackBackFaceSolid = new PropertyDefaultTrueFalse("backFace.solid", "Back-face Solid", 0);
/*  379 */   public static PropertyDefaultTrueFalse shaderPackBackFaceCutout = new PropertyDefaultTrueFalse("backFace.cutout", "Back-face Cutout", 0);
/*  380 */   public static PropertyDefaultTrueFalse shaderPackBackFaceCutoutMipped = new PropertyDefaultTrueFalse("backFace.cutoutMipped", "Back-face Cutout Mipped", 0);
/*  381 */   public static PropertyDefaultTrueFalse shaderPackBackFaceTranslucent = new PropertyDefaultTrueFalse("backFace.translucent", "Back-face Translucent", 0);
/*  382 */   public static PropertyDefaultTrueFalse shaderPackRainDepth = new PropertyDefaultTrueFalse("rain.depth", "Rain Depth", 0);
/*  383 */   public static PropertyDefaultTrueFalse shaderPackBeaconBeamDepth = new PropertyDefaultTrueFalse("beacon.beam.depth", "Rain Depth", 0);
/*  384 */   public static PropertyDefaultTrueFalse shaderPackSeparateAo = new PropertyDefaultTrueFalse("separateAo", "Separate AO", 0);
/*  385 */   public static PropertyDefaultTrueFalse shaderPackFrustumCulling = new PropertyDefaultTrueFalse("frustum.culling", "Frustum Culling", 0);
/*  386 */   private static Map<String, String> shaderPackResources = new HashMap<>();
/*  387 */   private static World currentWorld = null;
/*  388 */   private static final List<Integer> shaderPackDimensions = new ArrayList<>();
/*  389 */   private static ICustomTexture[] customTexturesGbuffers = null;
/*  390 */   private static ICustomTexture[] customTexturesComposite = null;
/*  391 */   private static ICustomTexture[] customTexturesDeferred = null;
/*  392 */   private static String noiseTexturePath = null;
/*  393 */   private static CustomUniforms customUniforms = null;
/*      */   private static final int STAGE_GBUFFERS = 0;
/*      */   private static final int STAGE_COMPOSITE = 1;
/*      */   private static final int STAGE_DEFERRED = 2;
/*  397 */   private static final String[] STAGE_NAMES = new String[] { "gbuffers", "composite", "deferred" };
/*      */   public static final boolean enableShadersOption = true;
/*      */   private static final boolean enableShadersDebug = true;
/*  400 */   public static final boolean saveFinalShaders = System.getProperty("shaders.debug.save", "false").equals("true");
/*  401 */   public static float blockLightLevel05 = 0.5F;
/*  402 */   public static float blockLightLevel06 = 0.6F;
/*  403 */   public static float blockLightLevel08 = 0.8F;
/*  404 */   public static float aoLevel = -1.0F;
/*  405 */   public static float sunPathRotation = 0.0F;
/*  406 */   public static float shadowAngleInterval = 0.0F;
/*  407 */   public static int fogMode = 0;
/*  408 */   public static float fogDensity = 0.0F;
/*      */   public static float fogColorR;
/*      */   public static float fogColorG;
/*      */   public static float fogColorB;
/*  412 */   public static float shadowIntervalSize = 2.0F;
/*  413 */   public static int terrainIconSize = 16;
/*  414 */   public static int[] terrainTextureSize = new int[2];
/*      */   private static ICustomTexture noiseTexture;
/*      */   private static boolean noiseTextureEnabled = false;
/*  417 */   private static int noiseTextureResolution = 256;
/*  418 */   static final int[] colorTextureImageUnit = new int[] { 0, 1, 2, 3, 7, 8, 9, 10 };
/*  419 */   private static final int bigBufferSize = 285 + 8 * ProgramCount << 2;
/*  420 */   private static final ByteBuffer bigBuffer = (ByteBuffer)BufferUtils.createByteBuffer(bigBufferSize).limit(0);
/*  421 */   static final float[] faProjection = new float[16];
/*  422 */   static final float[] faProjectionInverse = new float[16];
/*  423 */   static final float[] faModelView = new float[16];
/*  424 */   static final float[] faModelViewInverse = new float[16];
/*  425 */   static final float[] faShadowProjection = new float[16];
/*  426 */   static final float[] faShadowProjectionInverse = new float[16];
/*  427 */   static final float[] faShadowModelView = new float[16];
/*  428 */   static final float[] faShadowModelViewInverse = new float[16];
/*  429 */   static final FloatBuffer projection = nextFloatBuffer(16);
/*  430 */   static final FloatBuffer projectionInverse = nextFloatBuffer(16);
/*  431 */   static final FloatBuffer modelView = nextFloatBuffer(16);
/*  432 */   static final FloatBuffer modelViewInverse = nextFloatBuffer(16);
/*  433 */   static final FloatBuffer shadowProjection = nextFloatBuffer(16);
/*  434 */   static final FloatBuffer shadowProjectionInverse = nextFloatBuffer(16);
/*  435 */   static final FloatBuffer shadowModelView = nextFloatBuffer(16);
/*  436 */   static final FloatBuffer shadowModelViewInverse = nextFloatBuffer(16);
/*  437 */   static final FloatBuffer previousProjection = nextFloatBuffer(16);
/*  438 */   static final FloatBuffer previousModelView = nextFloatBuffer(16);
/*  439 */   static final FloatBuffer tempMatrixDirectBuffer = nextFloatBuffer(16);
/*  440 */   static final FloatBuffer tempDirectFloatBuffer = nextFloatBuffer(16);
/*  441 */   static final IntBuffer dfbColorTextures = nextIntBuffer(16);
/*  442 */   static final IntBuffer dfbDepthTextures = nextIntBuffer(3);
/*  443 */   static final IntBuffer sfbColorTextures = nextIntBuffer(8);
/*  444 */   static final IntBuffer sfbDepthTextures = nextIntBuffer(2);
/*  445 */   static final IntBuffer dfbDrawBuffers = nextIntBuffer(8);
/*  446 */   static final IntBuffer sfbDrawBuffers = nextIntBuffer(8);
/*  447 */   static final IntBuffer drawBuffersNone = (IntBuffer)nextIntBuffer(8).limit(0);
/*  448 */   static final IntBuffer drawBuffersColorAtt0 = (IntBuffer)nextIntBuffer(8).put(36064).position(0).limit(1);
/*  449 */   static final FlipTextures dfbColorTexturesFlip = new FlipTextures(dfbColorTextures, 8);
/*      */   static Map<Block, Integer> mapBlockToEntityData;
/*  451 */   private static final String[] formatNames = new String[] { "R8", "RG8", "RGB8", "RGBA8", "R8_SNORM", "RG8_SNORM", "RGB8_SNORM", "RGBA8_SNORM", "R16", "RG16", "RGB16", "RGBA16", "R16_SNORM", "RG16_SNORM", "RGB16_SNORM", "RGBA16_SNORM", "R16F", "RG16F", "RGB16F", "RGBA16F", "R32F", "RG32F", "RGB32F", "RGBA32F", "R32I", "RG32I", "RGB32I", "RGBA32I", "R32UI", "RG32UI", "RGB32UI", "RGBA32UI", "R3_G3_B2", "RGB5_A1", "RGB10_A2", "R11F_G11F_B10F", "RGB9_E5" };
/*  452 */   private static final int[] formatIds = new int[] { 33321, 33323, 32849, 32856, 36756, 36757, 36758, 36759, 33322, 33324, 32852, 32859, 36760, 36761, 36762, 36763, 33325, 33327, 34843, 34842, 33326, 33328, 34837, 34836, 33333, 33339, 36227, 36226, 33334, 33340, 36209, 36208, 10768, 32855, 32857, 35898, 35901 };
/*  453 */   private static final Pattern patternLoadEntityDataMap = Pattern.compile("\\s*([\\w:]+)\\s*=\\s*(-?\\d+)\\s*");
/*  454 */   public static int[] entityData = new int[32];
/*  455 */   public static int entityDataIndex = 0;
/*      */ 
/*      */   
/*      */   private static ByteBuffer nextByteBuffer(int size) {
/*  459 */     ByteBuffer bytebuffer = bigBuffer;
/*  460 */     int i = bytebuffer.limit();
/*  461 */     bytebuffer.position(i).limit(i + size);
/*  462 */     return bytebuffer.slice();
/*      */   }
/*      */ 
/*      */   
/*      */   public static IntBuffer nextIntBuffer(int size) {
/*  467 */     ByteBuffer bytebuffer = bigBuffer;
/*  468 */     int i = bytebuffer.limit();
/*  469 */     bytebuffer.position(i).limit(i + (size << 2));
/*  470 */     return bytebuffer.asIntBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   private static FloatBuffer nextFloatBuffer(int size) {
/*  475 */     ByteBuffer bytebuffer = bigBuffer;
/*  476 */     int i = bytebuffer.limit();
/*  477 */     bytebuffer.position(i).limit(i + (size << 2));
/*  478 */     return bytebuffer.asFloatBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   private static IntBuffer[] nextIntBufferArray(int count, int size) {
/*  483 */     IntBuffer[] aintbuffer = new IntBuffer[count];
/*      */     
/*  485 */     for (int i = 0; i < count; i++)
/*      */     {
/*  487 */       aintbuffer[i] = nextIntBuffer(size);
/*      */     }
/*      */     
/*  490 */     return aintbuffer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void loadConfig() {
/*  495 */     SMCLog.info("Load shaders configuration.");
/*      */ 
/*      */     
/*      */     try {
/*  499 */       if (!shaderPacksDir.exists())
/*      */       {
/*  501 */         shaderPacksDir.mkdir();
/*      */       }
/*      */     }
/*  504 */     catch (Exception var8) {
/*      */       
/*  506 */       SMCLog.severe("Failed to open the shaderpacks directory: " + shaderPacksDir);
/*      */     } 
/*      */     
/*  509 */     shadersConfig = (Properties)new PropertiesOrdered();
/*  510 */     shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), "");
/*      */     
/*  512 */     if (configFile.exists()) {
/*      */       
/*      */       try {
/*      */         
/*  516 */         FileReader filereader = new FileReader(configFile);
/*  517 */         shadersConfig.load(filereader);
/*  518 */         filereader.close();
/*      */       }
/*  520 */       catch (Exception exception) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  526 */     if (!configFile.exists()) {
/*      */       
/*      */       try {
/*      */         
/*  530 */         storeConfig();
/*      */       }
/*  532 */       catch (Exception exception) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  538 */     EnumShaderOption[] aenumshaderoption = EnumShaderOption.values();
/*      */     
/*  540 */     for (int i = 0; i < aenumshaderoption.length; i++) {
/*      */       
/*  542 */       EnumShaderOption enumshaderoption = aenumshaderoption[i];
/*  543 */       String s = enumshaderoption.getPropertyKey();
/*  544 */       String s1 = enumshaderoption.getValueDefault();
/*  545 */       String s2 = shadersConfig.getProperty(s, s1);
/*  546 */       setEnumShaderOption(enumshaderoption, s2);
/*      */     } 
/*      */     
/*  549 */     loadShaderPack();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setEnumShaderOption(EnumShaderOption eso, String str) {
/*  554 */     if (str == null)
/*      */     {
/*  556 */       str = eso.getValueDefault();
/*      */     }
/*      */     
/*  559 */     switch (eso) {
/*      */       
/*      */       case GBUFFERS:
/*  562 */         configAntialiasingLevel = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case DEFERRED:
/*  566 */         configNormalMap = Config.parseBoolean(str, true);
/*      */         return;
/*      */       
/*      */       case COMPOSITE:
/*  570 */         configSpecularMap = Config.parseBoolean(str, true);
/*      */         return;
/*      */       
/*      */       case SHADOW:
/*  574 */         configRenderResMul = Config.parseFloat(str, 1.0F);
/*      */         return;
/*      */       
/*      */       case null:
/*  578 */         configShadowResMul = Config.parseFloat(str, 1.0F);
/*      */         return;
/*      */       
/*      */       case null:
/*  582 */         configHandDepthMul = Config.parseFloat(str, 0.125F);
/*      */         return;
/*      */       
/*      */       case null:
/*  586 */         configCloudShadow = Config.parseBoolean(str, true);
/*      */         return;
/*      */       
/*      */       case null:
/*  590 */         configOldHandLight.setPropertyValue(str);
/*      */         return;
/*      */       
/*      */       case null:
/*  594 */         configOldLighting.setPropertyValue(str);
/*      */         return;
/*      */       
/*      */       case null:
/*  598 */         currentShaderName = str;
/*      */         return;
/*      */       
/*      */       case null:
/*  602 */         configTweakBlockDamage = Config.parseBoolean(str, true);
/*      */         return;
/*      */       
/*      */       case null:
/*  606 */         configShadowClipFrustrum = Config.parseBoolean(str, true);
/*      */         return;
/*      */       
/*      */       case null:
/*  610 */         configTexMinFilB = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case null:
/*  614 */         configTexMinFilN = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case null:
/*  618 */         configTexMinFilS = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case null:
/*  622 */         configTexMagFilB = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case null:
/*  626 */         configTexMagFilB = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case null:
/*  630 */         configTexMagFilB = Config.parseInt(str, 0);
/*      */         return;
/*      */     } 
/*      */     
/*  634 */     throw new IllegalArgumentException("Unknown option: " + eso);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeConfig() {
/*  640 */     SMCLog.info("Save shaders configuration.");
/*      */     
/*  642 */     if (shadersConfig == null)
/*      */     {
/*  644 */       shadersConfig = (Properties)new PropertiesOrdered();
/*      */     }
/*      */     
/*  647 */     EnumShaderOption[] aenumshaderoption = EnumShaderOption.values();
/*      */     
/*  649 */     for (int i = 0; i < aenumshaderoption.length; i++) {
/*      */       
/*  651 */       EnumShaderOption enumshaderoption = aenumshaderoption[i];
/*  652 */       String s = enumshaderoption.getPropertyKey();
/*  653 */       String s1 = getEnumShaderOption(enumshaderoption);
/*  654 */       shadersConfig.setProperty(s, s1);
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  659 */       FileWriter filewriter = new FileWriter(configFile);
/*  660 */       shadersConfig.store(filewriter, (String)null);
/*  661 */       filewriter.close();
/*      */     }
/*  663 */     catch (Exception exception) {
/*      */       
/*  665 */       SMCLog.severe("Error saving configuration: " + exception.getClass().getName() + ": " + exception.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getEnumShaderOption(EnumShaderOption eso) {
/*  671 */     switch (eso) {
/*      */       
/*      */       case GBUFFERS:
/*  674 */         return Integer.toString(configAntialiasingLevel);
/*      */       
/*      */       case DEFERRED:
/*  677 */         return Boolean.toString(configNormalMap);
/*      */       
/*      */       case COMPOSITE:
/*  680 */         return Boolean.toString(configSpecularMap);
/*      */       
/*      */       case SHADOW:
/*  683 */         return Float.toString(configRenderResMul);
/*      */       
/*      */       case null:
/*  686 */         return Float.toString(configShadowResMul);
/*      */       
/*      */       case null:
/*  689 */         return Float.toString(configHandDepthMul);
/*      */       
/*      */       case null:
/*  692 */         return Boolean.toString(configCloudShadow);
/*      */       
/*      */       case null:
/*  695 */         return configOldHandLight.getPropertyValue();
/*      */       
/*      */       case null:
/*  698 */         return configOldLighting.getPropertyValue();
/*      */       
/*      */       case null:
/*  701 */         return currentShaderName;
/*      */       
/*      */       case null:
/*  704 */         return Boolean.toString(configTweakBlockDamage);
/*      */       
/*      */       case null:
/*  707 */         return Boolean.toString(configShadowClipFrustrum);
/*      */       
/*      */       case null:
/*  710 */         return Integer.toString(configTexMinFilB);
/*      */       
/*      */       case null:
/*  713 */         return Integer.toString(configTexMinFilN);
/*      */       
/*      */       case null:
/*  716 */         return Integer.toString(configTexMinFilS);
/*      */       
/*      */       case null:
/*  719 */         return Integer.toString(configTexMagFilB);
/*      */       
/*      */       case null:
/*  722 */         return Integer.toString(configTexMagFilB);
/*      */       
/*      */       case null:
/*  725 */         return Integer.toString(configTexMagFilB);
/*      */     } 
/*      */     
/*  728 */     throw new IllegalArgumentException("Unknown option: " + eso);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setShaderPack(String par1name) {
/*  734 */     currentShaderName = par1name;
/*  735 */     shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), par1name);
/*  736 */     loadShaderPack();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void loadShaderPack() {
/*  741 */     boolean flag = shaderPackLoaded;
/*  742 */     boolean flag1 = isOldLighting();
/*      */     
/*  744 */     if (mc.renderGlobal != null)
/*      */     {
/*  746 */       mc.renderGlobal.pauseChunkUpdates();
/*      */     }
/*      */     
/*  749 */     shaderPackLoaded = false;
/*      */     
/*  751 */     if (shaderPack != null) {
/*      */       
/*  753 */       shaderPack.close();
/*  754 */       shaderPack = null;
/*  755 */       shaderPackResources.clear();
/*  756 */       shaderPackDimensions.clear();
/*  757 */       shaderPackOptions = null;
/*  758 */       shaderPackOptionSliders = null;
/*  759 */       shaderPackProfiles = null;
/*  760 */       shaderPackGuiScreens = null;
/*  761 */       shaderPackProgramConditions.clear();
/*  762 */       shaderPackClouds.resetValue();
/*  763 */       shaderPackOldHandLight.resetValue();
/*  764 */       shaderPackDynamicHandLight.resetValue();
/*  765 */       shaderPackOldLighting.resetValue();
/*  766 */       resetCustomTextures();
/*  767 */       noiseTexturePath = null;
/*      */     } 
/*      */     
/*  770 */     boolean flag2 = false;
/*      */     
/*  772 */     if (Config.isAntialiasing()) {
/*      */       
/*  774 */       SMCLog.info("Shaders can not be loaded, Antialiasing is enabled: " + Config.getAntialiasingLevel() + "x");
/*  775 */       flag2 = true;
/*      */     } 
/*      */     
/*  778 */     if (Config.isAnisotropicFiltering()) {
/*      */       
/*  780 */       SMCLog.info("Shaders can not be loaded, Anisotropic Filtering is enabled: " + Config.getAnisotropicFilterLevel() + "x");
/*  781 */       flag2 = true;
/*      */     } 
/*      */     
/*  784 */     if (Config.isFastRender()) {
/*      */       
/*  786 */       SMCLog.info("Shaders can not be loaded, Fast Render is enabled.");
/*  787 */       flag2 = true;
/*      */     } 
/*      */     
/*  790 */     String s = shadersConfig.getProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), "(internal)");
/*      */     
/*  792 */     if (!flag2) {
/*      */       
/*  794 */       shaderPack = getShaderPack(s);
/*  795 */       shaderPackLoaded = (shaderPack != null);
/*      */     } 
/*      */     
/*  798 */     if (shaderPackLoaded) {
/*      */       
/*  800 */       SMCLog.info("Loaded shaderpack: " + getShaderPackName());
/*      */     }
/*      */     else {
/*      */       
/*  804 */       SMCLog.info("No shaderpack loaded.");
/*  805 */       shaderPack = new ShaderPackNone();
/*      */     } 
/*      */     
/*  808 */     if (saveFinalShaders)
/*      */     {
/*  810 */       clearDirectory(new File(shaderPacksDir, "debug"));
/*      */     }
/*      */     
/*  813 */     loadShaderPackResources();
/*  814 */     loadShaderPackDimensions();
/*  815 */     shaderPackOptions = loadShaderPackOptions();
/*  816 */     loadShaderPackProperties();
/*  817 */     boolean flag3 = (shaderPackLoaded != flag);
/*  818 */     boolean flag4 = (isOldLighting() != flag1);
/*      */     
/*  820 */     if (flag3 || flag4) {
/*      */       
/*  822 */       DefaultVertexFormats.updateVertexFormats();
/*      */       
/*  824 */       if (Reflector.LightUtil.exists()) {
/*      */         
/*  826 */         Reflector.LightUtil_itemConsumer.setValue(null);
/*  827 */         Reflector.LightUtil_tessellator.setValue(null);
/*      */       } 
/*      */       
/*  830 */       updateBlockLightLevel();
/*      */     } 
/*      */     
/*  833 */     if (mc.getResourcePackRepository() != null)
/*      */     {
/*  835 */       CustomBlockLayers.update();
/*      */     }
/*      */     
/*  838 */     if (mc.renderGlobal != null)
/*      */     {
/*  840 */       mc.renderGlobal.resumeChunkUpdates();
/*      */     }
/*      */     
/*  843 */     if ((flag3 || flag4) && mc.getResourceManager() != null)
/*      */     {
/*  845 */       mc.scheduleResourcesRefresh();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static IShaderPack getShaderPack(String name) {
/*  851 */     if (name == null)
/*      */     {
/*  853 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  857 */     name = name.trim();
/*      */     
/*  859 */     if (!name.isEmpty() && !name.equals("OFF")) {
/*      */       
/*  861 */       if (name.equals("(internal)"))
/*      */       {
/*  863 */         return new ShaderPackDefault();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  869 */         File file1 = new File(shaderPacksDir, name);
/*  870 */         BasicFileAttributes readAttributes = Files.readAttributes(file1.toPath(), BasicFileAttributes.class, new java.nio.file.LinkOption[0]);
/*  871 */         return readAttributes.isDirectory() ? new ShaderPackFolder(name, file1) : ((readAttributes.isRegularFile() && name.toLowerCase().endsWith(".zip")) ? new ShaderPackZip(name, file1) : null);
/*      */       }
/*  873 */       catch (Exception exception) {
/*      */         
/*  875 */         exception.printStackTrace();
/*  876 */         return null;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  882 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IShaderPack getShaderPack() {
/*  889 */     return shaderPack;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void loadShaderPackDimensions() {
/*  894 */     shaderPackDimensions.clear();
/*      */     
/*  896 */     for (int i = -128; i <= 128; i++) {
/*      */       
/*  898 */       String s = "/shaders/world" + i;
/*      */       
/*  900 */       if (shaderPack.hasDirectory(s))
/*      */       {
/*  902 */         shaderPackDimensions.add(Integer.valueOf(i));
/*      */       }
/*      */     } 
/*      */     
/*  906 */     if (!shaderPackDimensions.isEmpty()) {
/*      */       
/*  908 */       Integer[] ainteger = shaderPackDimensions.<Integer>toArray(new Integer[0]);
/*  909 */       Config.dbg("[Shaders] Worlds: " + Config.arrayToString((Object[])ainteger));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void loadShaderPackProperties() {
/*  915 */     shaderPackClouds.resetValue();
/*  916 */     shaderPackOldHandLight.resetValue();
/*  917 */     shaderPackDynamicHandLight.resetValue();
/*  918 */     shaderPackOldLighting.resetValue();
/*  919 */     shaderPackShadowTranslucent.resetValue();
/*  920 */     shaderPackUnderwaterOverlay.resetValue();
/*  921 */     shaderPackSun.resetValue();
/*  922 */     shaderPackMoon.resetValue();
/*  923 */     shaderPackVignette.resetValue();
/*  924 */     shaderPackBackFaceSolid.resetValue();
/*  925 */     shaderPackBackFaceCutout.resetValue();
/*  926 */     shaderPackBackFaceCutoutMipped.resetValue();
/*  927 */     shaderPackBackFaceTranslucent.resetValue();
/*  928 */     shaderPackRainDepth.resetValue();
/*  929 */     shaderPackBeaconBeamDepth.resetValue();
/*  930 */     shaderPackSeparateAo.resetValue();
/*  931 */     shaderPackFrustumCulling.resetValue();
/*  932 */     BlockAliases.reset();
/*  933 */     ItemAliases.reset();
/*  934 */     EntityAliases.reset();
/*  935 */     customUniforms = null;
/*      */     
/*  937 */     for (int i = 0; i < ProgramsAll.length; i++) {
/*      */       
/*  939 */       Program program = ProgramsAll[i];
/*  940 */       program.resetProperties();
/*      */     } 
/*      */     
/*  943 */     if (shaderPack != null) {
/*      */       
/*  945 */       BlockAliases.update(shaderPack);
/*  946 */       ItemAliases.update(shaderPack);
/*  947 */       EntityAliases.update(shaderPack);
/*  948 */       String s = "/shaders/shaders.properties";
/*      */ 
/*      */       
/*      */       try {
/*  952 */         InputStream inputstream = shaderPack.getResourceAsStream(s);
/*      */         
/*  954 */         if (inputstream == null) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/*  959 */         inputstream = MacroProcessor.process(inputstream, s);
/*  960 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  961 */         propertiesOrdered.load(inputstream);
/*  962 */         inputstream.close();
/*  963 */         shaderPackClouds.loadFrom((Properties)propertiesOrdered);
/*  964 */         shaderPackOldHandLight.loadFrom((Properties)propertiesOrdered);
/*  965 */         shaderPackDynamicHandLight.loadFrom((Properties)propertiesOrdered);
/*  966 */         shaderPackOldLighting.loadFrom((Properties)propertiesOrdered);
/*  967 */         shaderPackShadowTranslucent.loadFrom((Properties)propertiesOrdered);
/*  968 */         shaderPackUnderwaterOverlay.loadFrom((Properties)propertiesOrdered);
/*  969 */         shaderPackSun.loadFrom((Properties)propertiesOrdered);
/*  970 */         shaderPackVignette.loadFrom((Properties)propertiesOrdered);
/*  971 */         shaderPackMoon.loadFrom((Properties)propertiesOrdered);
/*  972 */         shaderPackBackFaceSolid.loadFrom((Properties)propertiesOrdered);
/*  973 */         shaderPackBackFaceCutout.loadFrom((Properties)propertiesOrdered);
/*  974 */         shaderPackBackFaceCutoutMipped.loadFrom((Properties)propertiesOrdered);
/*  975 */         shaderPackBackFaceTranslucent.loadFrom((Properties)propertiesOrdered);
/*  976 */         shaderPackRainDepth.loadFrom((Properties)propertiesOrdered);
/*  977 */         shaderPackBeaconBeamDepth.loadFrom((Properties)propertiesOrdered);
/*  978 */         shaderPackSeparateAo.loadFrom((Properties)propertiesOrdered);
/*  979 */         shaderPackFrustumCulling.loadFrom((Properties)propertiesOrdered);
/*  980 */         shaderPackOptionSliders = ShaderPackParser.parseOptionSliders((Properties)propertiesOrdered, shaderPackOptions);
/*  981 */         shaderPackProfiles = ShaderPackParser.parseProfiles((Properties)propertiesOrdered, shaderPackOptions);
/*  982 */         shaderPackGuiScreens = ShaderPackParser.parseGuiScreens((Properties)propertiesOrdered, shaderPackProfiles, shaderPackOptions);
/*  983 */         shaderPackProgramConditions = ShaderPackParser.parseProgramConditions((Properties)propertiesOrdered, shaderPackOptions);
/*  984 */         customTexturesGbuffers = loadCustomTextures((Properties)propertiesOrdered, 0);
/*  985 */         customTexturesComposite = loadCustomTextures((Properties)propertiesOrdered, 1);
/*  986 */         customTexturesDeferred = loadCustomTextures((Properties)propertiesOrdered, 2);
/*  987 */         noiseTexturePath = propertiesOrdered.getProperty("texture.noise");
/*      */         
/*  989 */         if (noiseTexturePath != null)
/*      */         {
/*  991 */           noiseTextureEnabled = true;
/*      */         }
/*      */         
/*  994 */         customUniforms = ShaderPackParser.parseCustomUniforms((Properties)propertiesOrdered);
/*  995 */         ShaderPackParser.parseAlphaStates((Properties)propertiesOrdered);
/*  996 */         ShaderPackParser.parseBlendStates((Properties)propertiesOrdered);
/*  997 */         ShaderPackParser.parseRenderScales((Properties)propertiesOrdered);
/*  998 */         ShaderPackParser.parseBuffersFlip((Properties)propertiesOrdered);
/*      */       }
/* 1000 */       catch (IOException var3) {
/*      */         
/* 1002 */         Config.warn("[Shaders] Error reading: " + s);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static ICustomTexture[] loadCustomTextures(Properties props, int stage) {
/* 1009 */     String s = "texture." + STAGE_NAMES[stage] + ".";
/* 1010 */     Set<Object> set = props.keySet();
/* 1011 */     List<ICustomTexture> list = new ArrayList<>();
/*      */     
/* 1013 */     for (Object e : set) {
/*      */       
/* 1015 */       String s1 = (String)e;
/* 1016 */       if (s1.startsWith(s)) {
/*      */         
/* 1018 */         String s2 = StrUtils.removePrefix(s1, s);
/* 1019 */         s2 = StrUtils.removeSuffix(s2, new String[] { ".0", ".1", ".2", ".3", ".4", ".5", ".6", ".7", ".8", ".9" });
/* 1020 */         String s3 = props.getProperty(s1).trim();
/* 1021 */         int i = getTextureIndex(stage, s2);
/*      */         
/* 1023 */         if (i < 0) {
/*      */           
/* 1025 */           SMCLog.warning("Invalid texture name: " + s1);
/*      */           
/*      */           continue;
/*      */         } 
/* 1029 */         ICustomTexture icustomtexture = loadCustomTexture(i, s3);
/*      */         
/* 1031 */         if (icustomtexture != null) {
/*      */           
/* 1033 */           SMCLog.info("Custom texture: " + s1 + " = " + s3);
/* 1034 */           list.add(icustomtexture);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1040 */     if (list.size() <= 0)
/*      */     {
/* 1042 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1046 */     ICustomTexture[] aicustomtexture = list.<ICustomTexture>toArray(new ICustomTexture[0]);
/* 1047 */     return aicustomtexture;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ICustomTexture loadCustomTexture(int textureUnit, String path) {
/* 1053 */     if (path == null)
/*      */     {
/* 1055 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1059 */     path = path.trim();
/* 1060 */     return (path.indexOf(':') >= 0) ? loadCustomTextureLocation(textureUnit, path) : ((path.indexOf(' ') >= 0) ? loadCustomTextureRaw(textureUnit, path) : loadCustomTextureShaders(textureUnit, path));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ICustomTexture loadCustomTextureLocation(int textureUnit, String path) {
/* 1066 */     String s = path.trim();
/* 1067 */     int i = 0;
/*      */     
/* 1069 */     if (s.startsWith("minecraft:textures/")) {
/*      */       
/* 1071 */       s = StrUtils.addSuffixCheck(s, ".png");
/*      */       
/* 1073 */       if (s.endsWith("_n.png")) {
/*      */         
/* 1075 */         s = StrUtils.replaceSuffix(s, "_n.png", ".png");
/* 1076 */         i = 1;
/*      */       }
/* 1078 */       else if (s.endsWith("_s.png")) {
/*      */         
/* 1080 */         s = StrUtils.replaceSuffix(s, "_s.png", ".png");
/* 1081 */         i = 2;
/*      */       } 
/*      */     } 
/*      */     
/* 1085 */     ResourceLocation resourcelocation = new ResourceLocation(s);
/* 1086 */     CustomTextureLocation customtexturelocation = new CustomTextureLocation(textureUnit, resourcelocation, i);
/* 1087 */     return customtexturelocation;
/*      */   }
/*      */ 
/*      */   
/*      */   private static ICustomTexture loadCustomTextureRaw(int textureUnit, String line) {
/* 1092 */     ConnectedParser connectedparser = new ConnectedParser("Shaders");
/* 1093 */     String[] astring = Config.tokenize(line, " ");
/* 1094 */     Deque<String> deque = new ArrayDeque<>(Arrays.asList(astring));
/* 1095 */     String s = deque.poll();
/* 1096 */     TextureType texturetype = (TextureType)connectedparser.parseEnum(deque.poll(), (Enum[])TextureType.values(), "texture type");
/*      */     
/* 1098 */     if (texturetype == null) {
/*      */       
/* 1100 */       SMCLog.warning("Invalid raw texture type: " + line);
/* 1101 */       return null;
/*      */     } 
/*      */ 
/*      */     
/* 1105 */     InternalFormat internalformat = (InternalFormat)connectedparser.parseEnum(deque.poll(), (Enum[])InternalFormat.values(), "internal format");
/*      */     
/* 1107 */     if (internalformat == null) {
/*      */       
/* 1109 */       SMCLog.warning("Invalid raw texture internal format: " + line);
/* 1110 */       return null;
/*      */     } 
/*      */ 
/*      */     
/* 1114 */     int i = 0;
/* 1115 */     int j = 0;
/* 1116 */     int k = 0;
/*      */     
/* 1118 */     switch (texturetype) {
/*      */       
/*      */       case GBUFFERS:
/* 1121 */         i = connectedparser.parseInt(deque.poll(), -1);
/*      */         break;
/*      */       
/*      */       case DEFERRED:
/* 1125 */         i = connectedparser.parseInt(deque.poll(), -1);
/* 1126 */         j = connectedparser.parseInt(deque.poll(), -1);
/*      */         break;
/*      */       
/*      */       case COMPOSITE:
/* 1130 */         i = connectedparser.parseInt(deque.poll(), -1);
/* 1131 */         j = connectedparser.parseInt(deque.poll(), -1);
/* 1132 */         k = connectedparser.parseInt(deque.poll(), -1);
/*      */         break;
/*      */       
/*      */       case SHADOW:
/* 1136 */         i = connectedparser.parseInt(deque.poll(), -1);
/* 1137 */         j = connectedparser.parseInt(deque.poll(), -1);
/*      */         break;
/*      */       
/*      */       default:
/* 1141 */         SMCLog.warning("Invalid raw texture type: " + texturetype);
/* 1142 */         return null;
/*      */     } 
/*      */     
/* 1145 */     if (i >= 0 && j >= 0 && k >= 0) {
/*      */       
/* 1147 */       PixelFormat pixelformat = (PixelFormat)connectedparser.parseEnum(deque.poll(), (Enum[])PixelFormat.values(), "pixel format");
/*      */       
/* 1149 */       if (pixelformat == null) {
/*      */         
/* 1151 */         SMCLog.warning("Invalid raw texture pixel format: " + line);
/* 1152 */         return null;
/*      */       } 
/*      */ 
/*      */       
/* 1156 */       PixelType pixeltype = (PixelType)connectedparser.parseEnum(deque.poll(), (Enum[])PixelType.values(), "pixel type");
/*      */       
/* 1158 */       if (pixeltype == null) {
/*      */         
/* 1160 */         SMCLog.warning("Invalid raw texture pixel type: " + line);
/* 1161 */         return null;
/*      */       } 
/* 1163 */       if (!deque.isEmpty()) {
/*      */         
/* 1165 */         SMCLog.warning("Invalid raw texture, too many parameters: " + line);
/* 1166 */         return null;
/*      */       } 
/*      */ 
/*      */       
/* 1170 */       return loadCustomTextureRaw(textureUnit, line, s, texturetype, internalformat, i, j, k, pixelformat, pixeltype);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1176 */     SMCLog.warning("Invalid raw texture size: " + line);
/* 1177 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ICustomTexture loadCustomTextureRaw(int textureUnit, String line, String path, TextureType type, InternalFormat internalFormat, int width, int height, int depth, PixelFormat pixelFormat, PixelType pixelType) {
/*      */     try {
/* 1187 */       String s = "shaders/" + StrUtils.removePrefix(path, "/");
/* 1188 */       InputStream inputstream = shaderPack.getResourceAsStream(s);
/*      */       
/* 1190 */       if (inputstream == null) {
/*      */         
/* 1192 */         SMCLog.warning("Raw texture not found: " + path);
/* 1193 */         return null;
/*      */       } 
/*      */ 
/*      */       
/* 1197 */       byte[] abyte = Config.readAll(inputstream);
/* 1198 */       IOUtils.closeQuietly(inputstream);
/* 1199 */       ByteBuffer bytebuffer = GLAllocation.createDirectByteBuffer(abyte.length);
/* 1200 */       bytebuffer.put(abyte);
/* 1201 */       bytebuffer.flip();
/* 1202 */       TextureMetadataSection texturemetadatasection = SimpleShaderTexture.loadTextureMetadataSection(s, new TextureMetadataSection(true, true, new ArrayList()));
/* 1203 */       CustomTextureRaw customtextureraw = new CustomTextureRaw(type, internalFormat, width, height, depth, pixelFormat, pixelType, bytebuffer, textureUnit, texturemetadatasection.getTextureBlur(), texturemetadatasection.getTextureClamp());
/* 1204 */       return customtextureraw;
/*      */     
/*      */     }
/* 1207 */     catch (IOException ioexception) {
/*      */       
/* 1209 */       SMCLog.warning("Error loading raw texture: " + path);
/* 1210 */       SMCLog.warning(ioexception.getClass().getName() + ": " + ioexception.getMessage());
/* 1211 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static ICustomTexture loadCustomTextureShaders(int textureUnit, String path) {
/* 1217 */     path = path.trim();
/*      */     
/* 1219 */     if (path.indexOf('.') < 0)
/*      */     {
/* 1221 */       path = path + ".png";
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 1226 */       String s = "shaders/" + StrUtils.removePrefix(path, "/");
/* 1227 */       InputStream inputstream = shaderPack.getResourceAsStream(s);
/*      */       
/* 1229 */       if (inputstream == null) {
/*      */         
/* 1231 */         SMCLog.warning("Texture not found: " + path);
/* 1232 */         return null;
/*      */       } 
/*      */ 
/*      */       
/* 1236 */       IOUtils.closeQuietly(inputstream);
/* 1237 */       SimpleShaderTexture simpleshadertexture = new SimpleShaderTexture(s);
/* 1238 */       simpleshadertexture.loadTexture(mc.getResourceManager());
/* 1239 */       CustomTexture customtexture = new CustomTexture(textureUnit, s, (ITextureObject)simpleshadertexture);
/* 1240 */       return customtexture;
/*      */     
/*      */     }
/* 1243 */     catch (IOException ioexception) {
/*      */       
/* 1245 */       SMCLog.warning("Error loading texture: " + path);
/* 1246 */       SMCLog.warning(ioexception.getClass().getName() + ": " + ioexception.getMessage());
/* 1247 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getTextureIndex(int stage, String name) {
/* 1253 */     if (stage == 0) {
/*      */       
/* 1255 */       if (name.equals("texture"))
/*      */       {
/* 1257 */         return 0;
/*      */       }
/*      */       
/* 1260 */       if (name.equals("lightmap"))
/*      */       {
/* 1262 */         return 1;
/*      */       }
/*      */       
/* 1265 */       if (name.equals("normals"))
/*      */       {
/* 1267 */         return 2;
/*      */       }
/*      */       
/* 1270 */       if (name.equals("specular"))
/*      */       {
/* 1272 */         return 3;
/*      */       }
/*      */       
/* 1275 */       if (name.equals("shadowtex0") || name.equals("watershadow"))
/*      */       {
/* 1277 */         return 4;
/*      */       }
/*      */       
/* 1280 */       if (name.equals("shadow"))
/*      */       {
/* 1282 */         return waterShadowEnabled ? 5 : 4;
/*      */       }
/*      */       
/* 1285 */       if (name.equals("shadowtex1"))
/*      */       {
/* 1287 */         return 5;
/*      */       }
/*      */       
/* 1290 */       if (name.equals("depthtex0"))
/*      */       {
/* 1292 */         return 6;
/*      */       }
/*      */       
/* 1295 */       if (name.equals("gaux1"))
/*      */       {
/* 1297 */         return 7;
/*      */       }
/*      */       
/* 1300 */       if (name.equals("gaux2"))
/*      */       {
/* 1302 */         return 8;
/*      */       }
/*      */       
/* 1305 */       if (name.equals("gaux3"))
/*      */       {
/* 1307 */         return 9;
/*      */       }
/*      */       
/* 1310 */       if (name.equals("gaux4"))
/*      */       {
/* 1312 */         return 10;
/*      */       }
/*      */       
/* 1315 */       if (name.equals("depthtex1"))
/*      */       {
/* 1317 */         return 12;
/*      */       }
/*      */       
/* 1320 */       if (name.equals("shadowcolor0") || name.equals("shadowcolor"))
/*      */       {
/* 1322 */         return 13;
/*      */       }
/*      */       
/* 1325 */       if (name.equals("shadowcolor1"))
/*      */       {
/* 1327 */         return 14;
/*      */       }
/*      */       
/* 1330 */       if (name.equals("noisetex"))
/*      */       {
/* 1332 */         return 15;
/*      */       }
/*      */     } 
/*      */     
/* 1336 */     if (stage == 1 || stage == 2) {
/*      */       
/* 1338 */       if (name.equals("colortex0") || name.equals("colortex0"))
/*      */       {
/* 1340 */         return 0;
/*      */       }
/*      */       
/* 1343 */       if (name.equals("colortex1") || name.equals("gdepth"))
/*      */       {
/* 1345 */         return 1;
/*      */       }
/*      */       
/* 1348 */       if (name.equals("colortex2") || name.equals("gnormal"))
/*      */       {
/* 1350 */         return 2;
/*      */       }
/*      */       
/* 1353 */       if (name.equals("colortex3") || name.equals("composite"))
/*      */       {
/* 1355 */         return 3;
/*      */       }
/*      */       
/* 1358 */       if (name.equals("shadowtex0") || name.equals("watershadow"))
/*      */       {
/* 1360 */         return 4;
/*      */       }
/*      */       
/* 1363 */       if (name.equals("shadow"))
/*      */       {
/* 1365 */         return waterShadowEnabled ? 5 : 4;
/*      */       }
/*      */       
/* 1368 */       if (name.equals("shadowtex1"))
/*      */       {
/* 1370 */         return 5;
/*      */       }
/*      */       
/* 1373 */       if (name.equals("depthtex0") || name.equals("gdepthtex"))
/*      */       {
/* 1375 */         return 6;
/*      */       }
/*      */       
/* 1378 */       if (name.equals("colortex4") || name.equals("gaux1"))
/*      */       {
/* 1380 */         return 7;
/*      */       }
/*      */       
/* 1383 */       if (name.equals("colortex5") || name.equals("gaux2"))
/*      */       {
/* 1385 */         return 8;
/*      */       }
/*      */       
/* 1388 */       if (name.equals("colortex6") || name.equals("gaux3"))
/*      */       {
/* 1390 */         return 9;
/*      */       }
/*      */       
/* 1393 */       if (name.equals("colortex7") || name.equals("gaux4"))
/*      */       {
/* 1395 */         return 10;
/*      */       }
/*      */       
/* 1398 */       if (name.equals("depthtex1"))
/*      */       {
/* 1400 */         return 11;
/*      */       }
/*      */       
/* 1403 */       if (name.equals("depthtex2"))
/*      */       {
/* 1405 */         return 12;
/*      */       }
/*      */       
/* 1408 */       if (name.equals("shadowcolor0") || name.equals("shadowcolor"))
/*      */       {
/* 1410 */         return 13;
/*      */       }
/*      */       
/* 1413 */       if (name.equals("shadowcolor1"))
/*      */       {
/* 1415 */         return 14;
/*      */       }
/*      */       
/* 1418 */       if (name.equals("noisetex"))
/*      */       {
/* 1420 */         return 15;
/*      */       }
/*      */     } 
/*      */     
/* 1424 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void bindCustomTextures(ICustomTexture[] cts) {
/* 1429 */     if (cts != null)
/*      */     {
/* 1431 */       for (int i = 0; i < cts.length; i++) {
/*      */         
/* 1433 */         ICustomTexture icustomtexture = cts[i];
/* 1434 */         GlStateManager.setActiveTexture(33984 + icustomtexture.getTextureUnit());
/* 1435 */         int j = icustomtexture.getTextureId();
/* 1436 */         int k = icustomtexture.getTarget();
/*      */         
/* 1438 */         if (k == 3553) {
/*      */           
/* 1440 */           GlStateManager.bindTexture(j);
/*      */         }
/*      */         else {
/*      */           
/* 1444 */           GL11.glBindTexture(k, j);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void resetCustomTextures() {
/* 1452 */     deleteCustomTextures(customTexturesGbuffers);
/* 1453 */     deleteCustomTextures(customTexturesComposite);
/* 1454 */     deleteCustomTextures(customTexturesDeferred);
/* 1455 */     customTexturesGbuffers = null;
/* 1456 */     customTexturesComposite = null;
/* 1457 */     customTexturesDeferred = null;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void deleteCustomTextures(ICustomTexture[] cts) {
/* 1462 */     if (cts != null)
/*      */     {
/* 1464 */       for (int i = 0; i < cts.length; i++) {
/*      */         
/* 1466 */         ICustomTexture icustomtexture = cts[i];
/* 1467 */         icustomtexture.deleteTexture();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static ShaderOption[] getShaderPackOptions(String screenName) {
/* 1474 */     ShaderOption[] ashaderoption = (ShaderOption[])shaderPackOptions.clone();
/*      */     
/* 1476 */     if (shaderPackGuiScreens == null) {
/*      */       
/* 1478 */       if (shaderPackProfiles != null) {
/*      */         
/* 1480 */         ShaderOptionProfile shaderoptionprofile = new ShaderOptionProfile(shaderPackProfiles, ashaderoption);
/* 1481 */         ashaderoption = (ShaderOption[])Config.addObjectToArray((Object[])ashaderoption, shaderoptionprofile, 0);
/*      */       } 
/*      */       
/* 1484 */       ashaderoption = getVisibleOptions(ashaderoption);
/* 1485 */       return ashaderoption;
/*      */     } 
/*      */ 
/*      */     
/* 1489 */     String s = (screenName != null) ? ("screen." + screenName) : "screen";
/* 1490 */     ScreenShaderOptions screenshaderoptions = shaderPackGuiScreens.get(s);
/*      */     
/* 1492 */     if (screenshaderoptions == null)
/*      */     {
/* 1494 */       return new ShaderOption[0];
/*      */     }
/*      */ 
/*      */     
/* 1498 */     ShaderOption[] ashaderoption1 = screenshaderoptions.getShaderOptions();
/* 1499 */     List<ShaderOption> list = new ArrayList<>();
/*      */     
/* 1501 */     for (int i = 0; i < ashaderoption1.length; i++) {
/*      */       
/* 1503 */       ShaderOption shaderoption = ashaderoption1[i];
/*      */       
/* 1505 */       if (shaderoption == null) {
/*      */         
/* 1507 */         list.add((ShaderOption)null);
/*      */       }
/* 1509 */       else if (shaderoption instanceof net.optifine.shaders.config.ShaderOptionRest) {
/*      */         
/* 1511 */         ShaderOption[] ashaderoption2 = getShaderOptionsRest(shaderPackGuiScreens, ashaderoption);
/* 1512 */         list.addAll(Arrays.asList(ashaderoption2));
/*      */       }
/*      */       else {
/*      */         
/* 1516 */         list.add(shaderoption);
/*      */       } 
/*      */     } 
/*      */     
/* 1520 */     ShaderOption[] ashaderoption3 = list.<ShaderOption>toArray(new ShaderOption[0]);
/* 1521 */     return ashaderoption3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getShaderPackColumns(String screenName, int def) {
/* 1528 */     String s = (screenName != null) ? ("screen." + screenName) : "screen";
/*      */     
/* 1530 */     if (shaderPackGuiScreens == null)
/*      */     {
/* 1532 */       return def;
/*      */     }
/*      */ 
/*      */     
/* 1536 */     ScreenShaderOptions screenshaderoptions = shaderPackGuiScreens.get(s);
/* 1537 */     return (screenshaderoptions == null) ? def : screenshaderoptions.getColumns();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ShaderOption[] getShaderOptionsRest(Map<String, ScreenShaderOptions> mapScreens, ShaderOption[] ops) {
/* 1543 */     Set<String> set = new HashSet<>();
/*      */     
/* 1545 */     for (ScreenShaderOptions screenshaderoptions : mapScreens.values()) {
/*      */       
/* 1547 */       ShaderOption[] ashaderoption = screenshaderoptions.getShaderOptions();
/*      */       
/* 1549 */       for (int i = 0; i < ashaderoption.length; i++) {
/*      */         
/* 1551 */         ShaderOption shaderoption = ashaderoption[i];
/*      */         
/* 1553 */         if (shaderoption != null)
/*      */         {
/* 1555 */           set.add(shaderoption.getName());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1560 */     List<ShaderOption> list = new ArrayList<>();
/*      */     
/* 1562 */     for (int j = 0; j < ops.length; j++) {
/*      */       
/* 1564 */       ShaderOption shaderoption1 = ops[j];
/*      */       
/* 1566 */       if (shaderoption1.isVisible()) {
/*      */         
/* 1568 */         String s1 = shaderoption1.getName();
/*      */         
/* 1570 */         if (!set.contains(s1))
/*      */         {
/* 1572 */           list.add(shaderoption1);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1577 */     ShaderOption[] ashaderoption1 = list.<ShaderOption>toArray(new ShaderOption[0]);
/* 1578 */     return ashaderoption1;
/*      */   }
/*      */ 
/*      */   
/*      */   public static ShaderOption getShaderOption(String name) {
/* 1583 */     return ShaderUtils.getShaderOption(name, shaderPackOptions);
/*      */   }
/*      */ 
/*      */   
/*      */   public static ShaderOption[] getShaderPackOptions() {
/* 1588 */     return shaderPackOptions;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isShaderPackOptionSlider(String name) {
/* 1593 */     return (shaderPackOptionSliders == null) ? false : shaderPackOptionSliders.contains(name);
/*      */   }
/*      */ 
/*      */   
/*      */   private static ShaderOption[] getVisibleOptions(ShaderOption[] ops) {
/* 1598 */     List<ShaderOption> list = new ArrayList<>();
/*      */     
/* 1600 */     for (int i = 0; i < ops.length; i++) {
/*      */       
/* 1602 */       ShaderOption shaderoption = ops[i];
/*      */       
/* 1604 */       if (shaderoption.isVisible())
/*      */       {
/* 1606 */         list.add(shaderoption);
/*      */       }
/*      */     } 
/*      */     
/* 1610 */     ShaderOption[] ashaderoption = list.<ShaderOption>toArray(new ShaderOption[0]);
/* 1611 */     return ashaderoption;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void saveShaderPackOptions() {
/* 1616 */     saveShaderPackOptions(shaderPackOptions, shaderPack);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void saveShaderPackOptions(ShaderOption[] sos, IShaderPack sp) {
/* 1621 */     PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*      */     
/* 1623 */     if (shaderPackOptions != null)
/*      */     {
/* 1625 */       for (int i = 0; i < sos.length; i++) {
/*      */         
/* 1627 */         ShaderOption shaderoption = sos[i];
/*      */         
/* 1629 */         if (shaderoption.isChanged() && shaderoption.isEnabled())
/*      */         {
/* 1631 */           propertiesOrdered.setProperty(shaderoption.getName(), shaderoption.getValue());
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 1638 */       saveOptionProperties(sp, (Properties)propertiesOrdered);
/*      */     }
/* 1640 */     catch (IOException ioexception) {
/*      */       
/* 1642 */       Config.warn("[Shaders] Error saving configuration for " + shaderPack.getName());
/* 1643 */       ioexception.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void saveOptionProperties(IShaderPack sp, Properties props) throws IOException {
/* 1649 */     String s = "shaderpacks/" + sp.getName() + ".txt";
/* 1650 */     File file1 = new File((Minecraft.getMinecraft()).mcDataDir, s);
/*      */     
/* 1652 */     if (props.isEmpty()) {
/*      */       
/* 1654 */       file1.delete();
/*      */     }
/*      */     else {
/*      */       
/* 1658 */       FileOutputStream fileoutputstream = new FileOutputStream(file1);
/* 1659 */       props.store(fileoutputstream, (String)null);
/* 1660 */       fileoutputstream.flush();
/* 1661 */       fileoutputstream.close();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ShaderOption[] loadShaderPackOptions() {
/*      */     try {
/* 1669 */       String[] astring = programs.getProgramNames();
/* 1670 */       ShaderOption[] ashaderoption = ShaderPackParser.parseShaderPackOptions(shaderPack, astring, shaderPackDimensions);
/* 1671 */       Properties properties = loadOptionProperties(shaderPack);
/*      */       
/* 1673 */       for (int i = 0; i < ashaderoption.length; i++) {
/*      */         
/* 1675 */         ShaderOption shaderoption = ashaderoption[i];
/* 1676 */         String s = properties.getProperty(shaderoption.getName());
/*      */         
/* 1678 */         if (s != null) {
/*      */           
/* 1680 */           shaderoption.resetValue();
/*      */           
/* 1682 */           if (!shaderoption.setValue(s))
/*      */           {
/* 1684 */             Config.warn("[Shaders] Invalid value, option: " + shaderoption.getName() + ", value: " + s);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1689 */       return ashaderoption;
/*      */     }
/* 1691 */     catch (IOException ioexception) {
/*      */       
/* 1693 */       Config.warn("[Shaders] Error reading configuration for " + shaderPack.getName());
/* 1694 */       ioexception.printStackTrace();
/* 1695 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static Properties loadOptionProperties(IShaderPack sp) throws IOException {
/* 1701 */     PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 1702 */     String s = "shaderpacks/" + sp.getName() + ".txt";
/* 1703 */     File file1 = new File((Minecraft.getMinecraft()).mcDataDir, s);
/*      */     
/* 1705 */     if (file1.exists() && file1.isFile() && file1.canRead()) {
/*      */       
/* 1707 */       FileInputStream fileinputstream = new FileInputStream(file1);
/* 1708 */       propertiesOrdered.load(fileinputstream);
/* 1709 */       fileinputstream.close();
/* 1710 */       return (Properties)propertiesOrdered;
/*      */     } 
/*      */ 
/*      */     
/* 1714 */     return (Properties)propertiesOrdered;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShaderOption[] getChangedOptions(ShaderOption[] ops) {
/* 1720 */     List<ShaderOption> list = new ArrayList<>();
/*      */     
/* 1722 */     for (int i = 0; i < ops.length; i++) {
/*      */       
/* 1724 */       ShaderOption shaderoption = ops[i];
/*      */       
/* 1726 */       if (shaderoption.isEnabled() && shaderoption.isChanged())
/*      */       {
/* 1728 */         list.add(shaderoption);
/*      */       }
/*      */     } 
/*      */     
/* 1732 */     ShaderOption[] ashaderoption = list.<ShaderOption>toArray(new ShaderOption[0]);
/* 1733 */     return ashaderoption;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String applyOptions(String line, ShaderOption[] ops) {
/* 1738 */     if (ops != null && ops.length > 0) {
/*      */       
/* 1740 */       for (int i = 0; i < ops.length; i++) {
/*      */         
/* 1742 */         ShaderOption shaderoption = ops[i];
/*      */         
/* 1744 */         if (shaderoption.matchesLine(line)) {
/*      */           
/* 1746 */           line = shaderoption.getSourceLine();
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 1751 */       return line;
/*      */     } 
/*      */ 
/*      */     
/* 1755 */     return line;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static ArrayList listOfShaders() {
/* 1761 */     ArrayList<String> arraylist = new ArrayList<>();
/* 1762 */     arraylist.add("OFF");
/* 1763 */     arraylist.add("(internal)");
/* 1764 */     int i = arraylist.size();
/*      */ 
/*      */     
/*      */     try {
/* 1768 */       if (!shaderPacksDir.exists())
/*      */       {
/* 1770 */         shaderPacksDir.mkdir();
/*      */       }
/*      */       
/* 1773 */       File[] afile = shaderPacksDir.listFiles();
/*      */       
/* 1775 */       for (int j = 0; j < afile.length; j++) {
/*      */         
/* 1777 */         File file1 = afile[j];
/* 1778 */         String s = file1.getName();
/*      */         
/* 1780 */         BasicFileAttributes readAttributes = Files.readAttributes(file1.toPath(), BasicFileAttributes.class, new java.nio.file.LinkOption[0]);
/* 1781 */         if (readAttributes.isDirectory()) {
/*      */           
/* 1783 */           if (!s.equals("debug"))
/*      */           {
/* 1785 */             File file2 = new File(file1, "shaders");
/*      */             
/* 1787 */             if (file2.exists() && file2.isDirectory())
/*      */             {
/* 1789 */               arraylist.add(s);
/*      */             }
/*      */           }
/*      */         
/* 1793 */         } else if (readAttributes.isRegularFile() && s.toLowerCase().endsWith(".zip")) {
/*      */           
/* 1795 */           arraylist.add(s);
/*      */         }
/*      */       
/*      */       } 
/* 1799 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1804 */     List<String> list = arraylist.subList(i, arraylist.size());
/* 1805 */     list.sort(String.CASE_INSENSITIVE_ORDER);
/* 1806 */     return arraylist;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int checkFramebufferStatus(String location) {
/* 1811 */     int i = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/*      */     
/* 1813 */     if (i != 36053)
/*      */     {
/* 1815 */       System.err.format("FramebufferStatus 0x%04X at %s\n", new Object[] { Integer.valueOf(i), location });
/*      */     }
/*      */     
/* 1818 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int checkGLError(String location) {
/* 1823 */     int i = GlStateManager.glGetError();
/*      */     
/* 1825 */     if (i != 0 && GlErrors.isEnabled(i)) {
/*      */       
/* 1827 */       String s = Config.getGlErrorString(i);
/* 1828 */       String s1 = getErrorInfo(i, location);
/* 1829 */       String s2 = String.format("OpenGL error: %s (%s)%s, at: %s", new Object[] { Integer.valueOf(i), s, s1, location });
/* 1830 */       SMCLog.severe(s2);
/*      */       
/* 1832 */       if (Config.isShowGlErrors() && TimedEvent.isActive("ShowGlErrorShaders", 10000L)) {
/*      */         
/* 1834 */         String s3 = I18n.format("of.message.openglError", new Object[] { Integer.valueOf(i), s });
/* 1835 */         printChat(s3);
/*      */       } 
/*      */     } 
/*      */     
/* 1839 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String getErrorInfo(int errorCode, String location) {
/* 1844 */     StringBuilder stringbuilder = new StringBuilder();
/*      */     
/* 1846 */     if (errorCode == 1286) {
/*      */       
/* 1848 */       int i = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/* 1849 */       String s = getFramebufferStatusText(i);
/* 1850 */       String s1 = ", fbStatus: " + i + " (" + s + ")";
/* 1851 */       stringbuilder.append(s1);
/*      */     } 
/*      */     
/* 1854 */     String s2 = activeProgram.getName();
/*      */     
/* 1856 */     if (s2.isEmpty())
/*      */     {
/* 1858 */       s2 = "none";
/*      */     }
/*      */     
/* 1861 */     stringbuilder.append(", program: ").append(s2);
/* 1862 */     Program program = getProgramById(activeProgramID);
/*      */     
/* 1864 */     if (program != activeProgram) {
/*      */       
/* 1866 */       String s3 = program.getName();
/*      */       
/* 1868 */       if (s3.isEmpty())
/*      */       {
/* 1870 */         s3 = "none";
/*      */       }
/*      */       
/* 1873 */       stringbuilder.append(" (").append(s3).append(")");
/*      */     } 
/*      */     
/* 1876 */     if (location.equals("setDrawBuffers"))
/*      */     {
/* 1878 */       stringbuilder.append(", drawBuffers: ").append(activeProgram.getDrawBufSettings());
/*      */     }
/*      */     
/* 1881 */     return stringbuilder.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private static Program getProgramById(int programID) {
/* 1886 */     for (int i = 0; i < ProgramsAll.length; i++) {
/*      */       
/* 1888 */       Program program = ProgramsAll[i];
/*      */       
/* 1890 */       if (program.getId() == programID)
/*      */       {
/* 1892 */         return program;
/*      */       }
/*      */     } 
/*      */     
/* 1896 */     return ProgramNone;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String getFramebufferStatusText(int fbStatusCode) {
/* 1901 */     switch (fbStatusCode) {
/*      */       
/*      */       case 33305:
/* 1904 */         return "Undefined";
/*      */       
/*      */       case 36053:
/* 1907 */         return "Complete";
/*      */       
/*      */       case 36054:
/* 1910 */         return "Incomplete attachment";
/*      */       
/*      */       case 36055:
/* 1913 */         return "Incomplete missing attachment";
/*      */       
/*      */       case 36059:
/* 1916 */         return "Incomplete draw buffer";
/*      */       
/*      */       case 36060:
/* 1919 */         return "Incomplete read buffer";
/*      */       
/*      */       case 36061:
/* 1922 */         return "Unsupported";
/*      */       
/*      */       case 36182:
/* 1925 */         return "Incomplete multisample";
/*      */       
/*      */       case 36264:
/* 1928 */         return "Incomplete layer targets";
/*      */     } 
/*      */     
/* 1931 */     return "Unknown";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void printChat(String str) {
/* 1937 */     mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentText(str));
/*      */   }
/*      */ 
/*      */   
/*      */   private static void printChatAndLogError(String str) {
/* 1942 */     SMCLog.severe(str);
/* 1943 */     mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentText(str));
/*      */   }
/*      */ 
/*      */   
/*      */   public static void printIntBuffer(String title, IntBuffer buf) {
/* 1948 */     StringBuilder stringbuilder = new StringBuilder(128);
/* 1949 */     stringbuilder.append(title).append(" [pos ").append(buf.position()).append(" lim ").append(buf.limit()).append(" cap ").append(buf.capacity()).append(" :");
/* 1950 */     int i = buf.limit();
/*      */     
/* 1952 */     for (int j = 0; j < i; j++)
/*      */     {
/* 1954 */       stringbuilder.append(" ").append(buf.get(j));
/*      */     }
/*      */     
/* 1957 */     stringbuilder.append("]");
/* 1958 */     SMCLog.info(stringbuilder.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public static void startup(Minecraft mc) {
/* 1963 */     checkShadersModInstalled();
/* 1964 */     mc = mc;
/* 1965 */     mc = Minecraft.getMinecraft();
/* 1966 */     capabilities = GLContext.getCapabilities();
/* 1967 */     glVersionString = GL11.glGetString(7938);
/* 1968 */     glVendorString = GL11.glGetString(7936);
/* 1969 */     glRendererString = GL11.glGetString(7937);
/* 1970 */     SMCLog.info("OpenGL Version: " + glVersionString);
/* 1971 */     SMCLog.info("Vendor:  " + glVendorString);
/* 1972 */     SMCLog.info("Renderer: " + glRendererString);
/* 1973 */     SMCLog.info("Capabilities: " + (capabilities.OpenGL20 ? " 2.0 " : " - ") + (capabilities.OpenGL21 ? " 2.1 " : " - ") + (capabilities.OpenGL30 ? " 3.0 " : " - ") + (capabilities.OpenGL32 ? " 3.2 " : " - ") + (capabilities.OpenGL40 ? " 4.0 " : " - "));
/* 1974 */     SMCLog.info("GL_MAX_DRAW_BUFFERS: " + GL11.glGetInteger(34852));
/* 1975 */     SMCLog.info("GL_MAX_COLOR_ATTACHMENTS_EXT: " + GL11.glGetInteger(36063));
/* 1976 */     SMCLog.info("GL_MAX_TEXTURE_IMAGE_UNITS: " + GL11.glGetInteger(34930));
/* 1977 */     hasGlGenMipmap = capabilities.OpenGL30;
/* 1978 */     loadConfig();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateBlockLightLevel() {
/* 1983 */     if (isOldLighting()) {
/*      */       
/* 1985 */       blockLightLevel05 = 0.5F;
/* 1986 */       blockLightLevel06 = 0.6F;
/* 1987 */       blockLightLevel08 = 0.8F;
/*      */     }
/*      */     else {
/*      */       
/* 1991 */       blockLightLevel05 = 1.0F;
/* 1992 */       blockLightLevel06 = 1.0F;
/* 1993 */       blockLightLevel08 = 1.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isOldHandLight() {
/* 1999 */     return !configOldHandLight.isDefault() ? configOldHandLight.isTrue() : (!shaderPackOldHandLight.isDefault() ? shaderPackOldHandLight.isTrue() : true);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isDynamicHandLight() {
/* 2004 */     return !shaderPackDynamicHandLight.isDefault() ? shaderPackDynamicHandLight.isTrue() : true;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isOldLighting() {
/* 2009 */     return !configOldLighting.isDefault() ? configOldLighting.isTrue() : (!shaderPackOldLighting.isDefault() ? shaderPackOldLighting.isTrue() : true);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRenderShadowTranslucent() {
/* 2014 */     return !shaderPackShadowTranslucent.isFalse();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isUnderwaterOverlay() {
/* 2019 */     return !shaderPackUnderwaterOverlay.isFalse();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSun() {
/* 2024 */     return !shaderPackSun.isFalse();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isMoon() {
/* 2029 */     return !shaderPackMoon.isFalse();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isVignette() {
/* 2034 */     return !shaderPackVignette.isFalse();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRenderBackFace(EnumWorldBlockLayer blockLayerIn) {
/* 2039 */     switch (blockLayerIn) {
/*      */       
/*      */       case GBUFFERS:
/* 2042 */         return shaderPackBackFaceSolid.isTrue();
/*      */       
/*      */       case DEFERRED:
/* 2045 */         return shaderPackBackFaceCutout.isTrue();
/*      */       
/*      */       case COMPOSITE:
/* 2048 */         return shaderPackBackFaceCutoutMipped.isTrue();
/*      */       
/*      */       case SHADOW:
/* 2051 */         return shaderPackBackFaceTranslucent.isTrue();
/*      */     } 
/*      */     
/* 2054 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isRainDepth() {
/* 2060 */     return shaderPackRainDepth.isTrue();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isBeaconBeamDepth() {
/* 2065 */     return shaderPackBeaconBeamDepth.isTrue();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSeparateAo() {
/* 2070 */     return shaderPackSeparateAo.isTrue();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFrustumCulling() {
/* 2075 */     return !shaderPackFrustumCulling.isFalse();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void init() {
/*      */     boolean flag;
/* 2082 */     if (!isInitializedOnce) {
/*      */       
/* 2084 */       isInitializedOnce = true;
/* 2085 */       flag = true;
/*      */     }
/*      */     else {
/*      */       
/* 2089 */       flag = false;
/*      */     } 
/*      */     
/* 2092 */     if (!isShaderPackInitialized) {
/*      */       
/* 2094 */       checkGLError("Shaders.init pre");
/*      */       
/* 2096 */       if (getShaderPackName() != null);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2101 */       if (!capabilities.OpenGL20)
/*      */       {
/* 2103 */         printChatAndLogError("No OpenGL 2.0");
/*      */       }
/*      */       
/* 2106 */       if (!capabilities.GL_EXT_framebuffer_object)
/*      */       {
/* 2108 */         printChatAndLogError("No EXT_framebuffer_object");
/*      */       }
/*      */       
/* 2111 */       dfbDrawBuffers.position(0).limit(8);
/* 2112 */       dfbColorTextures.position(0).limit(16);
/* 2113 */       dfbDepthTextures.position(0).limit(3);
/* 2114 */       sfbDrawBuffers.position(0).limit(8);
/* 2115 */       sfbDepthTextures.position(0).limit(2);
/* 2116 */       sfbColorTextures.position(0).limit(8);
/* 2117 */       usedColorBuffers = 4;
/* 2118 */       usedDepthBuffers = 1;
/* 2119 */       usedShadowColorBuffers = 0;
/* 2120 */       usedShadowDepthBuffers = 0;
/* 2121 */       usedColorAttachs = 1;
/* 2122 */       usedDrawBuffers = 1;
/* 2123 */       Arrays.fill(gbuffersFormat, 6408);
/* 2124 */       Arrays.fill(gbuffersClear, true);
/* 2125 */       Arrays.fill((Object[])gbuffersClearColor, (Object)null);
/* 2126 */       Arrays.fill(shadowHardwareFilteringEnabled, false);
/* 2127 */       Arrays.fill(shadowMipmapEnabled, false);
/* 2128 */       Arrays.fill(shadowFilterNearest, false);
/* 2129 */       Arrays.fill(shadowColorMipmapEnabled, false);
/* 2130 */       Arrays.fill(shadowColorFilterNearest, false);
/* 2131 */       centerDepthSmoothEnabled = false;
/* 2132 */       noiseTextureEnabled = false;
/* 2133 */       sunPathRotation = 0.0F;
/* 2134 */       shadowIntervalSize = 2.0F;
/* 2135 */       shadowMapWidth = 1024;
/* 2136 */       shadowMapHeight = 1024;
/* 2137 */       spShadowMapWidth = 1024;
/* 2138 */       spShadowMapHeight = 1024;
/* 2139 */       shadowMapFOV = 90.0F;
/* 2140 */       shadowMapHalfPlane = 160.0F;
/* 2141 */       shadowMapIsOrtho = true;
/* 2142 */       shadowDistanceRenderMul = -1.0F;
/* 2143 */       aoLevel = -1.0F;
/* 2144 */       useEntityAttrib = false;
/* 2145 */       useMidTexCoordAttrib = false;
/* 2146 */       useTangentAttrib = false;
/* 2147 */       waterShadowEnabled = false;
/* 2148 */       hasGeometryShaders = false;
/* 2149 */       updateBlockLightLevel();
/* 2150 */       Smoother.resetValues();
/* 2151 */       shaderUniforms.reset();
/*      */       
/* 2153 */       if (customUniforms != null)
/*      */       {
/* 2155 */         customUniforms.reset();
/*      */       }
/*      */       
/* 2158 */       ShaderProfile shaderprofile = ShaderUtils.detectProfile(shaderPackProfiles, shaderPackOptions, false);
/* 2159 */       String s = "";
/*      */       
/* 2161 */       if (currentWorld != null) {
/*      */         
/* 2163 */         int i = currentWorld.provider.getDimensionId();
/*      */         
/* 2165 */         if (shaderPackDimensions.contains(Integer.valueOf(i)))
/*      */         {
/* 2167 */           s = "world" + i + "/";
/*      */         }
/*      */       } 
/*      */       
/* 2171 */       for (int k = 0; k < ProgramsAll.length; k++) {
/*      */         
/* 2173 */         Program program = ProgramsAll[k];
/* 2174 */         program.resetId();
/* 2175 */         program.resetConfiguration();
/*      */         
/* 2177 */         if (program.getProgramStage() != ProgramStage.NONE) {
/*      */           
/* 2179 */           String s1 = program.getName();
/* 2180 */           String s2 = s + s1;
/* 2181 */           boolean flag1 = true;
/*      */           
/* 2183 */           if (shaderPackProgramConditions.containsKey(s2))
/*      */           {
/* 2185 */             flag1 = (flag1 && ((IExpressionBool)shaderPackProgramConditions.get(s2)).eval());
/*      */           }
/*      */           
/* 2188 */           if (shaderprofile != null)
/*      */           {
/* 2190 */             flag1 = (flag1 && !shaderprofile.isProgramDisabled(s2));
/*      */           }
/*      */           
/* 2193 */           if (!flag1) {
/*      */             
/* 2195 */             SMCLog.info("Program disabled: " + s2);
/* 2196 */             s1 = "<disabled>";
/* 2197 */             s2 = s + s1;
/*      */           } 
/*      */           
/* 2200 */           String s3 = "/shaders/" + s2;
/* 2201 */           String s4 = s3 + ".vsh";
/* 2202 */           String s5 = s3 + ".gsh";
/* 2203 */           String s6 = s3 + ".fsh";
/* 2204 */           setupProgram(program, s4, s5, s6);
/* 2205 */           int j = program.getId();
/*      */           
/* 2207 */           if (j > 0)
/*      */           {
/* 2209 */             SMCLog.info("Program loaded: " + s2);
/*      */           }
/*      */           
/* 2212 */           initDrawBuffers(program);
/* 2213 */           updateToggleBuffers(program);
/*      */         } 
/*      */       } 
/*      */       
/* 2217 */       hasDeferredPrograms = false;
/*      */       
/* 2219 */       for (int l = 0; l < ProgramsDeferred.length; l++) {
/*      */         
/* 2221 */         if (ProgramsDeferred[l].getId() != 0) {
/*      */           
/* 2223 */           hasDeferredPrograms = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 2228 */       usedColorAttachs = usedColorBuffers;
/* 2229 */       shadowPassInterval = (usedShadowDepthBuffers > 0) ? 1 : 0;
/* 2230 */       shouldSkipDefaultShadow = (usedShadowDepthBuffers > 0);
/* 2231 */       SMCLog.info("usedColorBuffers: " + usedColorBuffers);
/* 2232 */       SMCLog.info("usedDepthBuffers: " + usedDepthBuffers);
/* 2233 */       SMCLog.info("usedShadowColorBuffers: " + usedShadowColorBuffers);
/* 2234 */       SMCLog.info("usedShadowDepthBuffers: " + usedShadowDepthBuffers);
/* 2235 */       SMCLog.info("usedColorAttachs: " + usedColorAttachs);
/* 2236 */       SMCLog.info("usedDrawBuffers: " + usedDrawBuffers);
/* 2237 */       dfbDrawBuffers.position(0).limit(usedDrawBuffers);
/* 2238 */       dfbColorTextures.position(0).limit(usedColorBuffers << 1);
/* 2239 */       dfbColorTexturesFlip.reset();
/*      */       
/* 2241 */       for (int i1 = 0; i1 < usedDrawBuffers; i1++)
/*      */       {
/* 2243 */         dfbDrawBuffers.put(i1, 36064 + i1);
/*      */       }
/*      */       
/* 2246 */       int j1 = GL11.glGetInteger(34852);
/*      */       
/* 2248 */       if (usedDrawBuffers > j1)
/*      */       {
/* 2250 */         printChatAndLogError("[Shaders] Error: Not enough draw buffers, needed: " + usedDrawBuffers + ", available: " + j1);
/*      */       }
/*      */       
/* 2253 */       sfbDrawBuffers.position(0).limit(usedShadowColorBuffers);
/*      */       
/* 2255 */       for (int k1 = 0; k1 < usedShadowColorBuffers; k1++)
/*      */       {
/* 2257 */         sfbDrawBuffers.put(k1, 36064 + k1);
/*      */       }
/*      */       
/* 2260 */       for (int l1 = 0; l1 < ProgramsAll.length; l1++) {
/*      */         
/* 2262 */         Program program1 = ProgramsAll[l1];
/*      */         
/*      */         Program program2;
/* 2265 */         for (program2 = program1; program2.getId() == 0 && program2.getProgramBackup() != program2; program2 = program2.getProgramBackup());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2270 */         if (program2 != program1 && program1 != ProgramShadow)
/*      */         {
/* 2272 */           program1.copyFrom(program2);
/*      */         }
/*      */       } 
/*      */       
/* 2276 */       resize();
/* 2277 */       resizeShadow();
/*      */       
/* 2279 */       if (noiseTextureEnabled)
/*      */       {
/* 2281 */         setupNoiseTexture();
/*      */       }
/*      */       
/* 2284 */       if (defaultTexture == null)
/*      */       {
/* 2286 */         defaultTexture = ShadersTex.createDefaultTexture();
/*      */       }
/*      */       
/* 2289 */       GlStateManager.pushMatrix();
/* 2290 */       GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
/* 2291 */       preCelestialRotate();
/* 2292 */       postCelestialRotate();
/* 2293 */       GlStateManager.popMatrix();
/* 2294 */       isShaderPackInitialized = true;
/* 2295 */       loadEntityDataMap();
/* 2296 */       resetDisplayLists();
/*      */       
/* 2298 */       if (!flag);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2303 */       checkGLError("Shaders.init");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void initDrawBuffers(Program p) {
/* 2309 */     int i = GL11.glGetInteger(34852);
/* 2310 */     Arrays.fill(p.getToggleColorTextures(), false);
/*      */     
/* 2312 */     if (p == ProgramFinal) {
/*      */       
/* 2314 */       p.setDrawBuffers((IntBuffer)null);
/*      */     }
/* 2316 */     else if (p.getId() == 0) {
/*      */       
/* 2318 */       if (p == ProgramShadow)
/*      */       {
/* 2320 */         p.setDrawBuffers(drawBuffersNone);
/*      */       }
/*      */       else
/*      */       {
/* 2324 */         p.setDrawBuffers(drawBuffersColorAtt0);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 2329 */       String s = p.getDrawBufSettings();
/*      */       
/* 2331 */       if (s == null) {
/*      */         
/* 2333 */         if (p != ProgramShadow && p != ProgramShadowSolid && p != ProgramShadowCutout)
/*      */         {
/* 2335 */           p.setDrawBuffers(dfbDrawBuffers);
/* 2336 */           usedDrawBuffers = usedColorBuffers;
/* 2337 */           Arrays.fill(p.getToggleColorTextures(), 0, usedColorBuffers, true);
/*      */         }
/*      */         else
/*      */         {
/* 2341 */           p.setDrawBuffers(sfbDrawBuffers);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 2346 */         IntBuffer intbuffer = p.getDrawBuffersBuffer();
/* 2347 */         int j = s.length();
/* 2348 */         usedDrawBuffers = Math.max(usedDrawBuffers, j);
/* 2349 */         j = Math.min(j, i);
/* 2350 */         p.setDrawBuffers(intbuffer);
/* 2351 */         intbuffer.limit(j);
/*      */         
/* 2353 */         for (int k = 0; k < j; k++) {
/*      */           
/* 2355 */           int l = getDrawBuffer(p, s, k);
/* 2356 */           intbuffer.put(k, l);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getDrawBuffer(Program p, String str, int ic) {
/* 2364 */     int i = 0;
/*      */     
/* 2366 */     if (ic >= str.length())
/*      */     {
/* 2368 */       return i;
/*      */     }
/*      */ 
/*      */     
/* 2372 */     int j = str.charAt(ic) - 48;
/*      */     
/* 2374 */     if (p == ProgramShadow) {
/*      */       
/* 2376 */       if (j >= 0 && j <= 1) {
/*      */         
/* 2378 */         i = j + 36064;
/* 2379 */         usedShadowColorBuffers = Math.max(usedShadowColorBuffers, j);
/*      */       } 
/*      */       
/* 2382 */       return i;
/*      */     } 
/*      */ 
/*      */     
/* 2386 */     if (j >= 0 && j <= 7) {
/*      */       
/* 2388 */       p.getToggleColorTextures()[j] = true;
/* 2389 */       i = j + 36064;
/* 2390 */       usedColorAttachs = Math.max(usedColorAttachs, j);
/* 2391 */       usedColorBuffers = Math.max(usedColorBuffers, j);
/*      */     } 
/*      */     
/* 2394 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void updateToggleBuffers(Program p) {
/* 2401 */     boolean[] aboolean = p.getToggleColorTextures();
/* 2402 */     Boolean[] aboolean1 = p.getBuffersFlip();
/*      */     
/* 2404 */     for (int i = 0; i < aboolean1.length; i++) {
/*      */       
/* 2406 */       Boolean obool = aboolean1[i];
/*      */       
/* 2408 */       if (obool != null)
/*      */       {
/* 2410 */         aboolean[i] = obool.booleanValue();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void resetDisplayLists() {
/* 2417 */     SMCLog.info("Reset model renderers");
/* 2418 */     countResetDisplayLists++;
/* 2419 */     SMCLog.info("Reset world renderers");
/* 2420 */     mc.renderGlobal.loadRenderers();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setupProgram(Program program, String vShaderPath, String gShaderPath, String fShaderPath) {
/* 2425 */     checkGLError("pre setupProgram");
/* 2426 */     int i = ARBShaderObjects.glCreateProgramObjectARB();
/* 2427 */     checkGLError("create");
/*      */     
/* 2429 */     if (i != 0) {
/*      */       
/* 2431 */       progUseEntityAttrib = false;
/* 2432 */       progUseMidTexCoordAttrib = false;
/* 2433 */       progUseTangentAttrib = false;
/* 2434 */       int j = createVertShader(program, vShaderPath);
/* 2435 */       int k = createGeomShader(program, gShaderPath);
/* 2436 */       int l = createFragShader(program, fShaderPath);
/* 2437 */       checkGLError("create");
/*      */       
/* 2439 */       if (j == 0 && k == 0 && l == 0) {
/*      */         
/* 2441 */         ARBShaderObjects.glDeleteObjectARB(i);
/* 2442 */         i = 0;
/* 2443 */         program.resetId();
/*      */       }
/*      */       else {
/*      */         
/* 2447 */         if (j != 0) {
/*      */           
/* 2449 */           ARBShaderObjects.glAttachObjectARB(i, j);
/* 2450 */           checkGLError("attach");
/*      */         } 
/*      */         
/* 2453 */         if (k != 0) {
/*      */           
/* 2455 */           ARBShaderObjects.glAttachObjectARB(i, k);
/* 2456 */           checkGLError("attach");
/*      */           
/* 2458 */           if (progArbGeometryShader4) {
/*      */             
/* 2460 */             ARBGeometryShader4.glProgramParameteriARB(i, 36315, 4);
/* 2461 */             ARBGeometryShader4.glProgramParameteriARB(i, 36316, 5);
/* 2462 */             ARBGeometryShader4.glProgramParameteriARB(i, 36314, progMaxVerticesOut);
/* 2463 */             checkGLError("arbGeometryShader4");
/*      */           } 
/*      */           
/* 2466 */           hasGeometryShaders = true;
/*      */         } 
/*      */         
/* 2469 */         if (l != 0) {
/*      */           
/* 2471 */           ARBShaderObjects.glAttachObjectARB(i, l);
/* 2472 */           checkGLError("attach");
/*      */         } 
/*      */         
/* 2475 */         if (progUseEntityAttrib) {
/*      */           
/* 2477 */           ARBVertexShader.glBindAttribLocationARB(i, entityAttrib, "mc_Entity");
/* 2478 */           checkGLError("mc_Entity");
/*      */         } 
/*      */         
/* 2481 */         if (progUseMidTexCoordAttrib) {
/*      */           
/* 2483 */           ARBVertexShader.glBindAttribLocationARB(i, midTexCoordAttrib, "mc_midTexCoord");
/* 2484 */           checkGLError("mc_midTexCoord");
/*      */         } 
/*      */         
/* 2487 */         if (progUseTangentAttrib) {
/*      */           
/* 2489 */           ARBVertexShader.glBindAttribLocationARB(i, tangentAttrib, "at_tangent");
/* 2490 */           checkGLError("at_tangent");
/*      */         } 
/*      */         
/* 2493 */         ARBShaderObjects.glLinkProgramARB(i);
/*      */         
/* 2495 */         if (GL20.glGetProgrami(i, 35714) != 1)
/*      */         {
/* 2497 */           SMCLog.severe("Error linking program: " + i + " (" + program.getName() + ")");
/*      */         }
/*      */         
/* 2500 */         printLogInfo(i, program.getName());
/*      */         
/* 2502 */         if (j != 0) {
/*      */           
/* 2504 */           ARBShaderObjects.glDetachObjectARB(i, j);
/* 2505 */           ARBShaderObjects.glDeleteObjectARB(j);
/*      */         } 
/*      */         
/* 2508 */         if (k != 0) {
/*      */           
/* 2510 */           ARBShaderObjects.glDetachObjectARB(i, k);
/* 2511 */           ARBShaderObjects.glDeleteObjectARB(k);
/*      */         } 
/*      */         
/* 2514 */         if (l != 0) {
/*      */           
/* 2516 */           ARBShaderObjects.glDetachObjectARB(i, l);
/* 2517 */           ARBShaderObjects.glDeleteObjectARB(l);
/*      */         } 
/*      */         
/* 2520 */         program.setId(i);
/* 2521 */         program.setRef(i);
/* 2522 */         useProgram(program);
/* 2523 */         ARBShaderObjects.glValidateProgramARB(i);
/* 2524 */         useProgram(ProgramNone);
/* 2525 */         printLogInfo(i, program.getName());
/* 2526 */         int i1 = GL20.glGetProgrami(i, 35715);
/*      */         
/* 2528 */         if (i1 != 1) {
/*      */           
/* 2530 */           String s = "\"";
/* 2531 */           printChatAndLogError("[Shaders] Error: Invalid program " + s + program.getName() + s);
/* 2532 */           ARBShaderObjects.glDeleteObjectARB(i);
/* 2533 */           i = 0;
/* 2534 */           program.resetId();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int createVertShader(Program program, String filename) {
/* 2542 */     int i = ARBShaderObjects.glCreateShaderObjectARB(35633);
/*      */     
/* 2544 */     if (i == 0)
/*      */     {
/* 2546 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 2550 */     StringBuilder stringbuilder = new StringBuilder(131072);
/* 2551 */     BufferedReader bufferedreader = null;
/*      */ 
/*      */     
/*      */     try {
/* 2555 */       bufferedreader = new BufferedReader(getShaderReader(filename));
/*      */     }
/* 2557 */     catch (Exception var10) {
/*      */       
/* 2559 */       ARBShaderObjects.glDeleteObjectARB(i);
/* 2560 */       return 0;
/*      */     } 
/*      */     
/* 2563 */     ShaderOption[] ashaderoption = getChangedOptions(shaderPackOptions);
/* 2564 */     List<String> list = new ArrayList<>();
/*      */     
/* 2566 */     if (bufferedreader != null) {
/*      */       
/*      */       try {
/*      */         
/* 2570 */         bufferedreader = ShaderPackParser.resolveIncludes(bufferedreader, filename, shaderPack, 0, list, 0);
/* 2571 */         MacroState macrostate = new MacroState();
/*      */ 
/*      */         
/*      */         while (true) {
/* 2575 */           String s = bufferedreader.readLine();
/*      */           
/* 2577 */           if (s == null) {
/*      */             
/* 2579 */             bufferedreader.close();
/*      */             
/*      */             break;
/*      */           } 
/* 2583 */           s = applyOptions(s, ashaderoption);
/* 2584 */           stringbuilder.append(s).append('\n');
/*      */           
/* 2586 */           if (macrostate.processLine(s)) {
/*      */             
/* 2588 */             ShaderLine shaderline = ShaderParser.parseLine(s);
/*      */             
/* 2590 */             if (shaderline != null) {
/*      */               
/* 2592 */               if (shaderline.isAttribute("mc_Entity")) {
/*      */                 
/* 2594 */                 useEntityAttrib = true;
/* 2595 */                 progUseEntityAttrib = true;
/*      */               }
/* 2597 */               else if (shaderline.isAttribute("mc_midTexCoord")) {
/*      */                 
/* 2599 */                 useMidTexCoordAttrib = true;
/* 2600 */                 progUseMidTexCoordAttrib = true;
/*      */               }
/* 2602 */               else if (shaderline.isAttribute("at_tangent")) {
/*      */                 
/* 2604 */                 useTangentAttrib = true;
/* 2605 */                 progUseTangentAttrib = true;
/*      */               } 
/*      */               
/* 2608 */               if (shaderline.isConstInt("countInstances"))
/*      */               {
/* 2610 */                 program.setCountInstances(shaderline.getValueInt());
/* 2611 */                 SMCLog.info("countInstances: " + program.getCountInstances());
/*      */               }
/*      */             
/*      */             } 
/*      */           } 
/*      */         } 
/* 2617 */       } catch (Exception exception) {
/*      */         
/* 2619 */         SMCLog.severe("Couldn't read " + filename + "!");
/* 2620 */         exception.printStackTrace();
/* 2621 */         ARBShaderObjects.glDeleteObjectARB(i);
/* 2622 */         return 0;
/*      */       } 
/*      */     }
/*      */     
/* 2626 */     if (saveFinalShaders)
/*      */     {
/* 2628 */       saveShader(filename, stringbuilder.toString());
/*      */     }
/*      */     
/* 2631 */     ARBShaderObjects.glShaderSourceARB(i, stringbuilder);
/* 2632 */     ARBShaderObjects.glCompileShaderARB(i);
/*      */     
/* 2634 */     if (GL20.glGetShaderi(i, 35713) != 1)
/*      */     {
/* 2636 */       SMCLog.severe("Error compiling vertex shader: " + filename);
/*      */     }
/*      */     
/* 2639 */     printShaderLogInfo(i, filename, list);
/* 2640 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int createGeomShader(Program program, String filename) {
/* 2646 */     int i = ARBShaderObjects.glCreateShaderObjectARB(36313);
/*      */     
/* 2648 */     if (i == 0)
/*      */     {
/* 2650 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 2654 */     StringBuilder stringbuilder = new StringBuilder(131072);
/* 2655 */     BufferedReader bufferedreader = null;
/*      */ 
/*      */     
/*      */     try {
/* 2659 */       bufferedreader = new BufferedReader(getShaderReader(filename));
/*      */     }
/* 2661 */     catch (Exception var11) {
/*      */       
/* 2663 */       ARBShaderObjects.glDeleteObjectARB(i);
/* 2664 */       return 0;
/*      */     } 
/*      */     
/* 2667 */     ShaderOption[] ashaderoption = getChangedOptions(shaderPackOptions);
/* 2668 */     List<String> list = new ArrayList<>();
/* 2669 */     progArbGeometryShader4 = false;
/* 2670 */     progMaxVerticesOut = 3;
/*      */     
/* 2672 */     if (bufferedreader != null) {
/*      */       
/*      */       try {
/*      */         
/* 2676 */         bufferedreader = ShaderPackParser.resolveIncludes(bufferedreader, filename, shaderPack, 0, list, 0);
/* 2677 */         MacroState macrostate = new MacroState();
/*      */ 
/*      */         
/*      */         while (true) {
/* 2681 */           String s = bufferedreader.readLine();
/*      */           
/* 2683 */           if (s == null) {
/*      */             
/* 2685 */             bufferedreader.close();
/*      */             
/*      */             break;
/*      */           } 
/* 2689 */           s = applyOptions(s, ashaderoption);
/* 2690 */           stringbuilder.append(s).append('\n');
/*      */           
/* 2692 */           if (macrostate.processLine(s)) {
/*      */             
/* 2694 */             ShaderLine shaderline = ShaderParser.parseLine(s);
/*      */             
/* 2696 */             if (shaderline != null)
/*      */             {
/* 2698 */               if (shaderline.isExtension("GL_ARB_geometry_shader4")) {
/*      */                 
/* 2700 */                 String s1 = Config.normalize(shaderline.getValue());
/*      */                 
/* 2702 */                 if (s1.equals("enable") || s1.equals("require") || s1.equals("warn"))
/*      */                 {
/* 2704 */                   progArbGeometryShader4 = true;
/*      */                 }
/*      */               } 
/*      */               
/* 2708 */               if (shaderline.isConstInt("maxVerticesOut"))
/*      */               {
/* 2710 */                 progMaxVerticesOut = shaderline.getValueInt();
/*      */               }
/*      */             }
/*      */           
/*      */           } 
/*      */         } 
/* 2716 */       } catch (Exception exception) {
/*      */         
/* 2718 */         SMCLog.severe("Couldn't read " + filename + "!");
/* 2719 */         exception.printStackTrace();
/* 2720 */         ARBShaderObjects.glDeleteObjectARB(i);
/* 2721 */         return 0;
/*      */       } 
/*      */     }
/*      */     
/* 2725 */     if (saveFinalShaders)
/*      */     {
/* 2727 */       saveShader(filename, stringbuilder.toString());
/*      */     }
/*      */     
/* 2730 */     ARBShaderObjects.glShaderSourceARB(i, stringbuilder);
/* 2731 */     ARBShaderObjects.glCompileShaderARB(i);
/*      */     
/* 2733 */     if (GL20.glGetShaderi(i, 35713) != 1)
/*      */     {
/* 2735 */       SMCLog.severe("Error compiling geometry shader: " + filename);
/*      */     }
/*      */     
/* 2738 */     printShaderLogInfo(i, filename, list);
/* 2739 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int createFragShader(Program program, String filename) {
/* 2745 */     int i = ARBShaderObjects.glCreateShaderObjectARB(35632);
/*      */     
/* 2747 */     if (i == 0)
/*      */     {
/* 2749 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 2753 */     StringBuilder stringbuilder = new StringBuilder(131072);
/* 2754 */     BufferedReader bufferedreader = null;
/*      */ 
/*      */     
/*      */     try {
/* 2758 */       bufferedreader = new BufferedReader(getShaderReader(filename));
/*      */     }
/* 2760 */     catch (Exception var14) {
/*      */       
/* 2762 */       ARBShaderObjects.glDeleteObjectARB(i);
/* 2763 */       return 0;
/*      */     } 
/*      */     
/* 2766 */     ShaderOption[] ashaderoption = getChangedOptions(shaderPackOptions);
/* 2767 */     List<String> list = new ArrayList<>();
/*      */     
/* 2769 */     if (bufferedreader != null) {
/*      */       
/*      */       try {
/*      */         
/* 2773 */         bufferedreader = ShaderPackParser.resolveIncludes(bufferedreader, filename, shaderPack, 0, list, 0);
/* 2774 */         MacroState macrostate = new MacroState();
/*      */ 
/*      */         
/*      */         while (true) {
/* 2778 */           String s = bufferedreader.readLine();
/*      */           
/* 2780 */           if (s == null) {
/*      */             
/* 2782 */             bufferedreader.close();
/*      */             
/*      */             break;
/*      */           } 
/* 2786 */           s = applyOptions(s, ashaderoption);
/* 2787 */           stringbuilder.append(s).append('\n');
/*      */           
/* 2789 */           if (macrostate.processLine(s))
/*      */           {
/* 2791 */             ShaderLine shaderline = ShaderParser.parseLine(s);
/*      */             
/* 2793 */             if (shaderline != null)
/*      */             {
/* 2795 */               if (shaderline.isUniform()) {
/*      */                 
/* 2797 */                 String s6 = shaderline.getName();
/*      */                 
/*      */                 int l1;
/* 2800 */                 if ((l1 = ShaderParser.getShadowDepthIndex(s6)) >= 0) {
/*      */                   
/* 2802 */                   usedShadowDepthBuffers = Math.max(usedShadowDepthBuffers, l1 + 1); continue;
/*      */                 } 
/* 2804 */                 if ((l1 = ShaderParser.getShadowColorIndex(s6)) >= 0) {
/*      */                   
/* 2806 */                   usedShadowColorBuffers = Math.max(usedShadowColorBuffers, l1 + 1); continue;
/*      */                 } 
/* 2808 */                 if ((l1 = ShaderParser.getDepthIndex(s6)) >= 0) {
/*      */                   
/* 2810 */                   usedDepthBuffers = Math.max(usedDepthBuffers, l1 + 1); continue;
/*      */                 } 
/* 2812 */                 if (s6.equals("gdepth") && gbuffersFormat[1] == 6408) {
/*      */                   
/* 2814 */                   gbuffersFormat[1] = 34836; continue;
/*      */                 } 
/* 2816 */                 if ((l1 = ShaderParser.getColorIndex(s6)) >= 0) {
/*      */                   
/* 2818 */                   usedColorBuffers = Math.max(usedColorBuffers, l1 + 1); continue;
/*      */                 } 
/* 2820 */                 if (s6.equals("centerDepthSmooth"))
/*      */                 {
/* 2822 */                   centerDepthSmoothEnabled = true; } 
/*      */                 continue;
/*      */               } 
/* 2825 */               if (!shaderline.isConstInt("shadowMapResolution") && !shaderline.isProperty("SHADOWRES")) {
/*      */                 
/* 2827 */                 if (!shaderline.isConstFloat("shadowMapFov") && !shaderline.isProperty("SHADOWFOV")) {
/*      */                   
/* 2829 */                   if (!shaderline.isConstFloat("shadowDistance") && !shaderline.isProperty("SHADOWHPL")) {
/*      */                     
/* 2831 */                     if (shaderline.isConstFloat("shadowDistanceRenderMul")) {
/*      */                       
/* 2833 */                       shadowDistanceRenderMul = shaderline.getValueFloat();
/* 2834 */                       SMCLog.info("Shadow distance render mul: " + shadowDistanceRenderMul); continue;
/*      */                     } 
/* 2836 */                     if (shaderline.isConstFloat("shadowIntervalSize")) {
/*      */                       
/* 2838 */                       shadowIntervalSize = shaderline.getValueFloat();
/* 2839 */                       SMCLog.info("Shadow map interval size: " + shadowIntervalSize); continue;
/*      */                     } 
/* 2841 */                     if (shaderline.isConstBool("generateShadowMipmap", true)) {
/*      */                       
/* 2843 */                       Arrays.fill(shadowMipmapEnabled, true);
/* 2844 */                       SMCLog.info("Generate shadow mipmap"); continue;
/*      */                     } 
/* 2846 */                     if (shaderline.isConstBool("generateShadowColorMipmap", true)) {
/*      */                       
/* 2848 */                       Arrays.fill(shadowColorMipmapEnabled, true);
/* 2849 */                       SMCLog.info("Generate shadow color mipmap"); continue;
/*      */                     } 
/* 2851 */                     if (shaderline.isConstBool("shadowHardwareFiltering", true)) {
/*      */                       
/* 2853 */                       Arrays.fill(shadowHardwareFilteringEnabled, true);
/* 2854 */                       SMCLog.info("Hardware shadow filtering enabled."); continue;
/*      */                     } 
/* 2856 */                     if (shaderline.isConstBool("shadowHardwareFiltering0", true)) {
/*      */                       
/* 2858 */                       shadowHardwareFilteringEnabled[0] = true;
/* 2859 */                       SMCLog.info("shadowHardwareFiltering0"); continue;
/*      */                     } 
/* 2861 */                     if (shaderline.isConstBool("shadowHardwareFiltering1", true)) {
/*      */                       
/* 2863 */                       shadowHardwareFilteringEnabled[1] = true;
/* 2864 */                       SMCLog.info("shadowHardwareFiltering1"); continue;
/*      */                     } 
/* 2866 */                     if (shaderline.isConstBool("shadowtex0Mipmap", "shadowtexMipmap", true)) {
/*      */                       
/* 2868 */                       shadowMipmapEnabled[0] = true;
/* 2869 */                       SMCLog.info("shadowtex0Mipmap"); continue;
/*      */                     } 
/* 2871 */                     if (shaderline.isConstBool("shadowtex1Mipmap", true)) {
/*      */                       
/* 2873 */                       shadowMipmapEnabled[1] = true;
/* 2874 */                       SMCLog.info("shadowtex1Mipmap"); continue;
/*      */                     } 
/* 2876 */                     if (shaderline.isConstBool("shadowcolor0Mipmap", "shadowColor0Mipmap", true)) {
/*      */                       
/* 2878 */                       shadowColorMipmapEnabled[0] = true;
/* 2879 */                       SMCLog.info("shadowcolor0Mipmap"); continue;
/*      */                     } 
/* 2881 */                     if (shaderline.isConstBool("shadowcolor1Mipmap", "shadowColor1Mipmap", true)) {
/*      */                       
/* 2883 */                       shadowColorMipmapEnabled[1] = true;
/* 2884 */                       SMCLog.info("shadowcolor1Mipmap"); continue;
/*      */                     } 
/* 2886 */                     if (shaderline.isConstBool("shadowtex0Nearest", "shadowtexNearest", "shadow0MinMagNearest", true)) {
/*      */                       
/* 2888 */                       shadowFilterNearest[0] = true;
/* 2889 */                       SMCLog.info("shadowtex0Nearest"); continue;
/*      */                     } 
/* 2891 */                     if (shaderline.isConstBool("shadowtex1Nearest", "shadow1MinMagNearest", true)) {
/*      */                       
/* 2893 */                       shadowFilterNearest[1] = true;
/* 2894 */                       SMCLog.info("shadowtex1Nearest"); continue;
/*      */                     } 
/* 2896 */                     if (shaderline.isConstBool("shadowcolor0Nearest", "shadowColor0Nearest", "shadowColor0MinMagNearest", true)) {
/*      */                       
/* 2898 */                       shadowColorFilterNearest[0] = true;
/* 2899 */                       SMCLog.info("shadowcolor0Nearest"); continue;
/*      */                     } 
/* 2901 */                     if (shaderline.isConstBool("shadowcolor1Nearest", "shadowColor1Nearest", "shadowColor1MinMagNearest", true)) {
/*      */                       
/* 2903 */                       shadowColorFilterNearest[1] = true;
/* 2904 */                       SMCLog.info("shadowcolor1Nearest"); continue;
/*      */                     } 
/* 2906 */                     if (!shaderline.isConstFloat("wetnessHalflife") && !shaderline.isProperty("WETNESSHL")) {
/*      */                       
/* 2908 */                       if (!shaderline.isConstFloat("drynessHalflife") && !shaderline.isProperty("DRYNESSHL")) {
/*      */                         
/* 2910 */                         if (shaderline.isConstFloat("eyeBrightnessHalflife")) {
/*      */                           
/* 2912 */                           eyeBrightnessHalflife = shaderline.getValueFloat();
/* 2913 */                           SMCLog.info("Eye brightness halflife: " + eyeBrightnessHalflife); continue;
/*      */                         } 
/* 2915 */                         if (shaderline.isConstFloat("centerDepthHalflife")) {
/*      */                           
/* 2917 */                           centerDepthSmoothHalflife = shaderline.getValueFloat();
/* 2918 */                           SMCLog.info("Center depth halflife: " + centerDepthSmoothHalflife); continue;
/*      */                         } 
/* 2920 */                         if (shaderline.isConstFloat("sunPathRotation")) {
/*      */                           
/* 2922 */                           sunPathRotation = shaderline.getValueFloat();
/* 2923 */                           SMCLog.info("Sun path rotation: " + sunPathRotation); continue;
/*      */                         } 
/* 2925 */                         if (shaderline.isConstFloat("ambientOcclusionLevel")) {
/*      */                           
/* 2927 */                           aoLevel = Config.limit(shaderline.getValueFloat(), 0.0F, 1.0F);
/* 2928 */                           SMCLog.info("AO Level: " + aoLevel); continue;
/*      */                         } 
/* 2930 */                         if (shaderline.isConstInt("superSamplingLevel")) {
/*      */                           
/* 2932 */                           int i1 = shaderline.getValueInt();
/*      */                           
/* 2934 */                           if (i1 > 1) {
/*      */                             
/* 2936 */                             SMCLog.info("Super sampling level: " + i1 + "x");
/* 2937 */                             superSamplingLevel = i1;
/*      */                             
/*      */                             continue;
/*      */                           } 
/* 2941 */                           superSamplingLevel = 1;
/*      */                           continue;
/*      */                         } 
/* 2944 */                         if (shaderline.isConstInt("noiseTextureResolution")) {
/*      */                           
/* 2946 */                           noiseTextureResolution = shaderline.getValueInt();
/* 2947 */                           noiseTextureEnabled = true;
/* 2948 */                           SMCLog.info("Noise texture enabled");
/* 2949 */                           SMCLog.info("Noise texture resolution: " + noiseTextureResolution); continue;
/*      */                         } 
/* 2951 */                         if (shaderline.isConstIntSuffix("Format")) {
/*      */                           
/* 2953 */                           String s5 = StrUtils.removeSuffix(shaderline.getName(), "Format");
/* 2954 */                           String s7 = shaderline.getValue();
/* 2955 */                           int i2 = getBufferIndexFromString(s5);
/* 2956 */                           int l = getTextureFormatFromString(s7);
/*      */                           
/* 2958 */                           if (i2 >= 0 && l != 0) {
/*      */                             
/* 2960 */                             gbuffersFormat[i2] = l;
/* 2961 */                             SMCLog.info("%s format: %s", new Object[] { s5, s7 });
/*      */                           }  continue;
/*      */                         } 
/* 2964 */                         if (shaderline.isConstBoolSuffix("Clear", false)) {
/*      */                           
/* 2966 */                           if (ShaderParser.isComposite(filename) || ShaderParser.isDeferred(filename)) {
/*      */                             
/* 2968 */                             String s4 = StrUtils.removeSuffix(shaderline.getName(), "Clear");
/* 2969 */                             int k1 = getBufferIndexFromString(s4);
/*      */                             
/* 2971 */                             if (k1 >= 0) {
/*      */                               
/* 2973 */                               gbuffersClear[k1] = false;
/* 2974 */                               SMCLog.info("%s clear disabled", new Object[] { s4 });
/*      */                             } 
/*      */                           }  continue;
/*      */                         } 
/* 2978 */                         if (shaderline.isConstVec4Suffix("ClearColor")) {
/*      */                           
/* 2980 */                           if (ShaderParser.isComposite(filename) || ShaderParser.isDeferred(filename)) {
/*      */                             
/* 2982 */                             String s3 = StrUtils.removeSuffix(shaderline.getName(), "ClearColor");
/* 2983 */                             int j1 = getBufferIndexFromString(s3);
/*      */                             
/* 2985 */                             if (j1 >= 0) {
/*      */                               
/* 2987 */                               Vector4f vector4f = shaderline.getValueVec4();
/*      */                               
/* 2989 */                               if (vector4f != null) {
/*      */                                 
/* 2991 */                                 gbuffersClearColor[j1] = vector4f;
/* 2992 */                                 SMCLog.info("%s clear color: %s %s %s %s", new Object[] { s3, Float.valueOf(vector4f.getX()), Float.valueOf(vector4f.getY()), Float.valueOf(vector4f.getZ()), Float.valueOf(vector4f.getW()) });
/*      */                                 
/*      */                                 continue;
/*      */                               } 
/* 2996 */                               SMCLog.warning("Invalid color value: " + shaderline.getValue());
/*      */                             } 
/*      */                           } 
/*      */                           continue;
/*      */                         } 
/* 3001 */                         if (shaderline.isProperty("GAUX4FORMAT", "RGBA32F")) {
/*      */                           
/* 3003 */                           gbuffersFormat[7] = 34836;
/* 3004 */                           SMCLog.info("gaux4 format : RGB32AF"); continue;
/*      */                         } 
/* 3006 */                         if (shaderline.isProperty("GAUX4FORMAT", "RGB32F")) {
/*      */                           
/* 3008 */                           gbuffersFormat[7] = 34837;
/* 3009 */                           SMCLog.info("gaux4 format : RGB32F"); continue;
/*      */                         } 
/* 3011 */                         if (shaderline.isProperty("GAUX4FORMAT", "RGB16")) {
/*      */                           
/* 3013 */                           gbuffersFormat[7] = 32852;
/* 3014 */                           SMCLog.info("gaux4 format : RGB16"); continue;
/*      */                         } 
/* 3016 */                         if (shaderline.isConstBoolSuffix("MipmapEnabled", true)) {
/*      */                           
/* 3018 */                           if (ShaderParser.isComposite(filename) || ShaderParser.isDeferred(filename) || ShaderParser.isFinal(filename)) {
/*      */                             
/* 3020 */                             String s2 = StrUtils.removeSuffix(shaderline.getName(), "MipmapEnabled");
/* 3021 */                             int j = getBufferIndexFromString(s2);
/*      */                             
/* 3023 */                             if (j >= 0) {
/*      */                               
/* 3025 */                               int k = program.getCompositeMipmapSetting();
/* 3026 */                               k |= 1 << j;
/* 3027 */                               program.setCompositeMipmapSetting(k);
/* 3028 */                               SMCLog.info("%s mipmap enabled", new Object[] { s2 });
/*      */                             } 
/*      */                           }  continue;
/*      */                         } 
/* 3032 */                         if (shaderline.isProperty("DRAWBUFFERS")) {
/*      */                           
/* 3034 */                           String s1 = shaderline.getValue();
/*      */                           
/* 3036 */                           if (ShaderParser.isValidDrawBuffers(s1)) {
/*      */                             
/* 3038 */                             program.setDrawBufSettings(s1);
/*      */                             
/*      */                             continue;
/*      */                           } 
/* 3042 */                           SMCLog.warning("Invalid draw buffers: " + s1);
/*      */                         } 
/*      */                         
/*      */                         continue;
/*      */                       } 
/*      */                       
/* 3048 */                       drynessHalfLife = shaderline.getValueFloat();
/* 3049 */                       SMCLog.info("Dryness halflife: " + drynessHalfLife);
/*      */                       
/*      */                       continue;
/*      */                     } 
/*      */                     
/* 3054 */                     wetnessHalfLife = shaderline.getValueFloat();
/* 3055 */                     SMCLog.info("Wetness halflife: " + wetnessHalfLife);
/*      */                     
/*      */                     continue;
/*      */                   } 
/*      */                   
/* 3060 */                   shadowMapHalfPlane = shaderline.getValueFloat();
/* 3061 */                   shadowMapIsOrtho = true;
/* 3062 */                   SMCLog.info("Shadow map distance: " + shadowMapHalfPlane);
/*      */                   
/*      */                   continue;
/*      */                 } 
/*      */                 
/* 3067 */                 shadowMapFOV = shaderline.getValueFloat();
/* 3068 */                 shadowMapIsOrtho = false;
/* 3069 */                 SMCLog.info("Shadow map field of view: " + shadowMapFOV);
/*      */                 
/*      */                 continue;
/*      */               } 
/*      */               
/* 3074 */               spShadowMapWidth = spShadowMapHeight = shaderline.getValueInt();
/* 3075 */               shadowMapWidth = shadowMapHeight = Math.round(spShadowMapWidth * configShadowResMul);
/* 3076 */               SMCLog.info("Shadow map resolution: " + spShadowMapWidth);
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         } 
/* 3082 */       } catch (Exception exception) {
/*      */         
/* 3084 */         SMCLog.severe("Couldn't read " + filename + "!");
/* 3085 */         exception.printStackTrace();
/* 3086 */         ARBShaderObjects.glDeleteObjectARB(i);
/* 3087 */         return 0;
/*      */       } 
/*      */     }
/*      */     
/* 3091 */     if (saveFinalShaders)
/*      */     {
/* 3093 */       saveShader(filename, stringbuilder.toString());
/*      */     }
/*      */     
/* 3096 */     ARBShaderObjects.glShaderSourceARB(i, stringbuilder);
/* 3097 */     ARBShaderObjects.glCompileShaderARB(i);
/*      */     
/* 3099 */     if (GL20.glGetShaderi(i, 35713) != 1)
/*      */     {
/* 3101 */       SMCLog.severe("Error compiling fragment shader: " + filename);
/*      */     }
/*      */     
/* 3104 */     printShaderLogInfo(i, filename, list);
/* 3105 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Reader getShaderReader(String filename) {
/* 3111 */     return new InputStreamReader(shaderPack.getResourceAsStream(filename));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveShader(String filename, String code) {
/*      */     try {
/* 3118 */       File file1 = new File(shaderPacksDir, "debug/" + filename);
/* 3119 */       file1.getParentFile().mkdirs();
/* 3120 */       Config.writeFile(file1, code);
/*      */     }
/* 3122 */     catch (IOException ioexception) {
/*      */       
/* 3124 */       Config.warn("Error saving: " + filename);
/* 3125 */       ioexception.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void clearDirectory(File dir) {
/* 3131 */     if (dir.exists())
/*      */     {
/* 3133 */       if (dir.isDirectory()) {
/*      */         
/* 3135 */         File[] afile = dir.listFiles();
/*      */         
/* 3137 */         if (afile != null)
/*      */         {
/* 3139 */           for (int i = 0; i < afile.length; i++) {
/*      */             
/* 3141 */             File file1 = afile[i];
/*      */             
/* 3143 */             if (file1.isDirectory())
/*      */             {
/* 3145 */               clearDirectory(file1);
/*      */             }
/*      */             
/* 3148 */             file1.delete();
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean printLogInfo(int obj, String name) {
/* 3157 */     IntBuffer intbuffer = BufferUtils.createIntBuffer(1);
/* 3158 */     ARBShaderObjects.glGetObjectParameterARB(obj, 35716, intbuffer);
/* 3159 */     int i = intbuffer.get();
/*      */     
/* 3161 */     if (i > 1) {
/*      */       
/* 3163 */       ByteBuffer bytebuffer = BufferUtils.createByteBuffer(i);
/* 3164 */       intbuffer.flip();
/* 3165 */       ARBShaderObjects.glGetInfoLogARB(obj, intbuffer, bytebuffer);
/* 3166 */       byte[] abyte = new byte[i];
/* 3167 */       bytebuffer.get(abyte);
/*      */       
/* 3169 */       if (abyte[i - 1] == 0)
/*      */       {
/* 3171 */         abyte[i - 1] = 10;
/*      */       }
/*      */       
/* 3174 */       String s = new String(abyte, Charsets.US_ASCII);
/* 3175 */       s = StrUtils.trim(s, " \n\r\t");
/* 3176 */       SMCLog.info("Info log: " + name + "\n" + s);
/* 3177 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 3181 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean printShaderLogInfo(int shader, String name, List<String> listFiles) {
/* 3187 */     IntBuffer intbuffer = BufferUtils.createIntBuffer(1);
/* 3188 */     int i = GL20.glGetShaderi(shader, 35716);
/*      */     
/* 3190 */     if (i <= 1)
/*      */     {
/* 3192 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 3196 */     for (int j = 0; j < listFiles.size(); j++) {
/*      */       
/* 3198 */       String s = listFiles.get(j);
/* 3199 */       SMCLog.info("File: " + (j + 1) + " = " + s);
/*      */     } 
/*      */     
/* 3202 */     String s1 = GL20.glGetShaderInfoLog(shader, i);
/* 3203 */     s1 = StrUtils.trim(s1, " \n\r\t");
/* 3204 */     SMCLog.info("Shader info log: " + name + "\n" + s1);
/* 3205 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setDrawBuffers(IntBuffer drawBuffers) {
/* 3211 */     if (drawBuffers == null)
/*      */     {
/* 3213 */       drawBuffers = drawBuffersNone;
/*      */     }
/*      */     
/* 3216 */     if (activeDrawBuffers != drawBuffers) {
/*      */       
/* 3218 */       activeDrawBuffers = drawBuffers;
/* 3219 */       GL20.glDrawBuffers(drawBuffers);
/* 3220 */       checkGLError("setDrawBuffers");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void useProgram(Program program) {
/* 3226 */     checkGLError("pre-useProgram");
/*      */     
/* 3228 */     if (isShadowPass) {
/*      */       
/* 3230 */       program = ProgramShadow;
/*      */     }
/* 3232 */     else if (isEntitiesGlowing) {
/*      */       
/* 3234 */       program = ProgramEntitiesGlowing;
/*      */     } 
/*      */     
/* 3237 */     if (activeProgram != program) {
/*      */       
/* 3239 */       updateAlphaBlend(activeProgram, program);
/* 3240 */       activeProgram = program;
/* 3241 */       int i = program.getId();
/* 3242 */       activeProgramID = i;
/* 3243 */       ARBShaderObjects.glUseProgramObjectARB(i);
/*      */       
/* 3245 */       if (checkGLError("useProgram") != 0) {
/*      */         
/* 3247 */         program.setId(0);
/* 3248 */         i = program.getId();
/* 3249 */         activeProgramID = i;
/* 3250 */         ARBShaderObjects.glUseProgramObjectARB(i);
/*      */       } 
/*      */       
/* 3253 */       shaderUniforms.setProgram(i);
/*      */       
/* 3255 */       if (customUniforms != null)
/*      */       {
/* 3257 */         customUniforms.setProgram(i);
/*      */       }
/*      */       
/* 3260 */       if (i != 0) {
/*      */         
/* 3262 */         IntBuffer intbuffer = program.getDrawBuffers();
/*      */         
/* 3264 */         if (isRenderingDfb)
/*      */         {
/* 3266 */           setDrawBuffers(intbuffer);
/*      */         }
/*      */         
/* 3269 */         activeCompositeMipmapSetting = program.getCompositeMipmapSetting();
/*      */         
/* 3271 */         switch (program.getProgramStage()) {
/*      */           
/*      */           case GBUFFERS:
/* 3274 */             setProgramUniform1i(uniform_texture, 0);
/* 3275 */             setProgramUniform1i(uniform_lightmap, 1);
/* 3276 */             setProgramUniform1i(uniform_normals, 2);
/* 3277 */             setProgramUniform1i(uniform_specular, 3);
/* 3278 */             setProgramUniform1i(uniform_shadow, waterShadowEnabled ? 5 : 4);
/* 3279 */             setProgramUniform1i(uniform_watershadow, 4);
/* 3280 */             setProgramUniform1i(uniform_shadowtex0, 4);
/* 3281 */             setProgramUniform1i(uniform_shadowtex1, 5);
/* 3282 */             setProgramUniform1i(uniform_depthtex0, 6);
/*      */             
/* 3284 */             if (customTexturesGbuffers != null || hasDeferredPrograms) {
/*      */               
/* 3286 */               setProgramUniform1i(uniform_gaux1, 7);
/* 3287 */               setProgramUniform1i(uniform_gaux2, 8);
/* 3288 */               setProgramUniform1i(uniform_gaux3, 9);
/* 3289 */               setProgramUniform1i(uniform_gaux4, 10);
/*      */             } 
/*      */             
/* 3292 */             setProgramUniform1i(uniform_depthtex1, 11);
/* 3293 */             setProgramUniform1i(uniform_shadowcolor, 13);
/* 3294 */             setProgramUniform1i(uniform_shadowcolor0, 13);
/* 3295 */             setProgramUniform1i(uniform_shadowcolor1, 14);
/* 3296 */             setProgramUniform1i(uniform_noisetex, 15);
/*      */             break;
/*      */           
/*      */           case DEFERRED:
/*      */           case COMPOSITE:
/* 3301 */             setProgramUniform1i(uniform_gcolor, 0);
/* 3302 */             setProgramUniform1i(uniform_gdepth, 1);
/* 3303 */             setProgramUniform1i(uniform_gnormal, 2);
/* 3304 */             setProgramUniform1i(uniform_composite, 3);
/* 3305 */             setProgramUniform1i(uniform_gaux1, 7);
/* 3306 */             setProgramUniform1i(uniform_gaux2, 8);
/* 3307 */             setProgramUniform1i(uniform_gaux3, 9);
/* 3308 */             setProgramUniform1i(uniform_gaux4, 10);
/* 3309 */             setProgramUniform1i(uniform_colortex0, 0);
/* 3310 */             setProgramUniform1i(uniform_colortex1, 1);
/* 3311 */             setProgramUniform1i(uniform_colortex2, 2);
/* 3312 */             setProgramUniform1i(uniform_colortex3, 3);
/* 3313 */             setProgramUniform1i(uniform_colortex4, 7);
/* 3314 */             setProgramUniform1i(uniform_colortex5, 8);
/* 3315 */             setProgramUniform1i(uniform_colortex6, 9);
/* 3316 */             setProgramUniform1i(uniform_colortex7, 10);
/* 3317 */             setProgramUniform1i(uniform_shadow, waterShadowEnabled ? 5 : 4);
/* 3318 */             setProgramUniform1i(uniform_watershadow, 4);
/* 3319 */             setProgramUniform1i(uniform_shadowtex0, 4);
/* 3320 */             setProgramUniform1i(uniform_shadowtex1, 5);
/* 3321 */             setProgramUniform1i(uniform_gdepthtex, 6);
/* 3322 */             setProgramUniform1i(uniform_depthtex0, 6);
/* 3323 */             setProgramUniform1i(uniform_depthtex1, 11);
/* 3324 */             setProgramUniform1i(uniform_depthtex2, 12);
/* 3325 */             setProgramUniform1i(uniform_shadowcolor, 13);
/* 3326 */             setProgramUniform1i(uniform_shadowcolor0, 13);
/* 3327 */             setProgramUniform1i(uniform_shadowcolor1, 14);
/* 3328 */             setProgramUniform1i(uniform_noisetex, 15);
/*      */             break;
/*      */           
/*      */           case SHADOW:
/* 3332 */             setProgramUniform1i(uniform_tex, 0);
/* 3333 */             setProgramUniform1i(uniform_texture, 0);
/* 3334 */             setProgramUniform1i(uniform_lightmap, 1);
/* 3335 */             setProgramUniform1i(uniform_normals, 2);
/* 3336 */             setProgramUniform1i(uniform_specular, 3);
/* 3337 */             setProgramUniform1i(uniform_shadow, waterShadowEnabled ? 5 : 4);
/* 3338 */             setProgramUniform1i(uniform_watershadow, 4);
/* 3339 */             setProgramUniform1i(uniform_shadowtex0, 4);
/* 3340 */             setProgramUniform1i(uniform_shadowtex1, 5);
/*      */             
/* 3342 */             if (customTexturesGbuffers != null) {
/*      */               
/* 3344 */               setProgramUniform1i(uniform_gaux1, 7);
/* 3345 */               setProgramUniform1i(uniform_gaux2, 8);
/* 3346 */               setProgramUniform1i(uniform_gaux3, 9);
/* 3347 */               setProgramUniform1i(uniform_gaux4, 10);
/*      */             } 
/*      */             
/* 3350 */             setProgramUniform1i(uniform_shadowcolor, 13);
/* 3351 */             setProgramUniform1i(uniform_shadowcolor0, 13);
/* 3352 */             setProgramUniform1i(uniform_shadowcolor1, 14);
/* 3353 */             setProgramUniform1i(uniform_noisetex, 15);
/*      */             break;
/*      */         } 
/* 3356 */         ItemStack itemstack = (mc.thePlayer != null) ? mc.thePlayer.getHeldItem() : null;
/* 3357 */         Item item = (itemstack != null) ? itemstack.getItem() : null;
/* 3358 */         int j = -1;
/* 3359 */         Block block = null;
/*      */         
/* 3361 */         if (item != null) {
/*      */           
/* 3363 */           j = Item.itemRegistry.getIDForObject(item);
/* 3364 */           block = (Block)Block.blockRegistry.getObjectById(j);
/* 3365 */           j = ItemAliases.getItemAliasId(j);
/*      */         } 
/*      */         
/* 3368 */         int k = (block != null) ? block.getLightValue() : 0;
/* 3369 */         setProgramUniform1i(uniform_heldItemId, j);
/* 3370 */         setProgramUniform1i(uniform_heldBlockLightValue, k);
/* 3371 */         setProgramUniform1i(uniform_fogMode, fogEnabled ? fogMode : 0);
/* 3372 */         setProgramUniform1f(uniform_fogDensity, fogEnabled ? fogDensity : 0.0F);
/* 3373 */         setProgramUniform3f(uniform_fogColor, fogColorR, fogColorG, fogColorB);
/* 3374 */         setProgramUniform3f(uniform_skyColor, skyColorR, skyColorG, skyColorB);
/* 3375 */         setProgramUniform1i(uniform_worldTime, (int)(worldTime % 24000L));
/* 3376 */         setProgramUniform1i(uniform_worldDay, (int)(worldTime / 24000L));
/* 3377 */         setProgramUniform1i(uniform_moonPhase, moonPhase);
/* 3378 */         setProgramUniform1i(uniform_frameCounter, frameCounter);
/* 3379 */         setProgramUniform1f(uniform_frameTime, frameTime);
/* 3380 */         setProgramUniform1f(uniform_frameTimeCounter, frameTimeCounter);
/* 3381 */         setProgramUniform1f(uniform_sunAngle, sunAngle);
/* 3382 */         setProgramUniform1f(uniform_shadowAngle, shadowAngle);
/* 3383 */         setProgramUniform1f(uniform_rainStrength, rainStrength);
/* 3384 */         setProgramUniform1f(uniform_aspectRatio, renderWidth / renderHeight);
/* 3385 */         setProgramUniform1f(uniform_viewWidth, renderWidth);
/* 3386 */         setProgramUniform1f(uniform_viewHeight, renderHeight);
/* 3387 */         setProgramUniform1f(uniform_near, 0.05F);
/* 3388 */         setProgramUniform1f(uniform_far, (mc.gameSettings.renderDistanceChunks << 4));
/* 3389 */         setProgramUniform3f(uniform_sunPosition, sunPosition[0], sunPosition[1], sunPosition[2]);
/* 3390 */         setProgramUniform3f(uniform_moonPosition, moonPosition[0], moonPosition[1], moonPosition[2]);
/* 3391 */         setProgramUniform3f(uniform_shadowLightPosition, shadowLightPosition[0], shadowLightPosition[1], shadowLightPosition[2]);
/* 3392 */         setProgramUniform3f(uniform_upPosition, upPosition[0], upPosition[1], upPosition[2]);
/* 3393 */         setProgramUniform3f(uniform_previousCameraPosition, (float)previousCameraPositionX, (float)previousCameraPositionY, (float)previousCameraPositionZ);
/* 3394 */         setProgramUniform3f(uniform_cameraPosition, (float)cameraPositionX, (float)cameraPositionY, (float)cameraPositionZ);
/* 3395 */         setProgramUniformMatrix4ARB(uniform_gbufferModelView, false, modelView);
/* 3396 */         setProgramUniformMatrix4ARB(uniform_gbufferModelViewInverse, false, modelViewInverse);
/* 3397 */         setProgramUniformMatrix4ARB(uniform_gbufferPreviousProjection, false, previousProjection);
/* 3398 */         setProgramUniformMatrix4ARB(uniform_gbufferProjection, false, projection);
/* 3399 */         setProgramUniformMatrix4ARB(uniform_gbufferProjectionInverse, false, projectionInverse);
/* 3400 */         setProgramUniformMatrix4ARB(uniform_gbufferPreviousModelView, false, previousModelView);
/*      */         
/* 3402 */         if (usedShadowDepthBuffers > 0) {
/*      */           
/* 3404 */           setProgramUniformMatrix4ARB(uniform_shadowProjection, false, shadowProjection);
/* 3405 */           setProgramUniformMatrix4ARB(uniform_shadowProjectionInverse, false, shadowProjectionInverse);
/* 3406 */           setProgramUniformMatrix4ARB(uniform_shadowModelView, false, shadowModelView);
/* 3407 */           setProgramUniformMatrix4ARB(uniform_shadowModelViewInverse, false, shadowModelViewInverse);
/*      */         } 
/*      */         
/* 3410 */         setProgramUniform1f(uniform_wetness, wetness);
/* 3411 */         setProgramUniform1f(uniform_eyeAltitude, eyePosY);
/* 3412 */         setProgramUniform2i(uniform_eyeBrightness, eyeBrightness & 0xFFFF, eyeBrightness >> 16);
/* 3413 */         setProgramUniform2i(uniform_eyeBrightnessSmooth, Math.round(eyeBrightnessFadeX), Math.round(eyeBrightnessFadeY));
/* 3414 */         setProgramUniform2i(uniform_terrainTextureSize, terrainTextureSize[0], terrainTextureSize[1]);
/* 3415 */         setProgramUniform1i(uniform_terrainIconSize, terrainIconSize);
/* 3416 */         setProgramUniform1i(uniform_isEyeInWater, isEyeInWater);
/* 3417 */         setProgramUniform1f(uniform_nightVision, nightVision);
/* 3418 */         setProgramUniform1f(uniform_blindness, blindness);
/* 3419 */         setProgramUniform1f(uniform_screenBrightness, mc.gameSettings.gammaSetting);
/* 3420 */         setProgramUniform1i(uniform_hideGUI, mc.gameSettings.hideGUI ? 1 : 0);
/* 3421 */         setProgramUniform1f(uniform_centerDepthSmooth, centerDepthSmooth);
/* 3422 */         setProgramUniform2i(uniform_atlasSize, atlasSizeX, atlasSizeY);
/*      */         
/* 3424 */         if (customUniforms != null)
/*      */         {
/* 3426 */           customUniforms.update();
/*      */         }
/*      */         
/* 3429 */         checkGLError("end useProgram");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void updateAlphaBlend(Program programOld, Program programNew) {
/* 3436 */     if (programOld.getAlphaState() != null)
/*      */     {
/* 3438 */       GlStateManager.unlockAlpha();
/*      */     }
/*      */     
/* 3441 */     if (programOld.getBlendState() != null)
/*      */     {
/* 3443 */       GlStateManager.unlockBlend();
/*      */     }
/*      */     
/* 3446 */     GlAlphaState glalphastate = programNew.getAlphaState();
/*      */     
/* 3448 */     if (glalphastate != null)
/*      */     {
/* 3450 */       GlStateManager.lockAlpha(glalphastate);
/*      */     }
/*      */     
/* 3453 */     GlBlendState glblendstate = programNew.getBlendState();
/*      */     
/* 3455 */     if (glblendstate != null)
/*      */     {
/* 3457 */       GlStateManager.lockBlend(glblendstate);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setProgramUniform1i(ShaderUniform1i su, int value) {
/* 3463 */     su.setValue(value);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setProgramUniform2i(ShaderUniform2i su, int i0, int i1) {
/* 3468 */     su.setValue(i0, i1);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setProgramUniform1f(ShaderUniform1f su, float value) {
/* 3473 */     su.setValue(value);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setProgramUniform3f(ShaderUniform3f su, float f0, float f1, float f2) {
/* 3478 */     su.setValue(f0, f1, f2);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setProgramUniformMatrix4ARB(ShaderUniformM4 su, boolean transpose, FloatBuffer matrix) {
/* 3483 */     su.setValue(transpose, matrix);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getBufferIndexFromString(String name) {
/* 3488 */     return (!name.equals("colortex0") && !name.equals("gcolor")) ? ((!name.equals("colortex1") && !name.equals("gdepth")) ? ((!name.equals("colortex2") && !name.equals("gnormal")) ? ((!name.equals("colortex3") && !name.equals("composite")) ? ((!name.equals("colortex4") && !name.equals("gaux1")) ? ((!name.equals("colortex5") && !name.equals("gaux2")) ? ((!name.equals("colortex6") && !name.equals("gaux3")) ? ((!name.equals("colortex7") && !name.equals("gaux4")) ? -1 : 7) : 6) : 5) : 4) : 3) : 2) : 1) : 0;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getTextureFormatFromString(String par) {
/* 3493 */     par = par.trim();
/*      */     
/* 3495 */     for (int i = 0; i < formatNames.length; i++) {
/*      */       
/* 3497 */       String s = formatNames[i];
/*      */       
/* 3499 */       if (par.equals(s))
/*      */       {
/* 3501 */         return formatIds[i];
/*      */       }
/*      */     } 
/*      */     
/* 3505 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setupNoiseTexture() {
/* 3510 */     if (noiseTexture == null && noiseTexturePath != null)
/*      */     {
/* 3512 */       noiseTexture = loadCustomTexture(15, noiseTexturePath);
/*      */     }
/*      */     
/* 3515 */     if (noiseTexture == null)
/*      */     {
/* 3517 */       noiseTexture = new HFNoiseTexture(noiseTextureResolution, noiseTextureResolution);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void loadEntityDataMap() {
/* 3523 */     mapBlockToEntityData = new IdentityHashMap<>(300);
/*      */     
/* 3525 */     if (mapBlockToEntityData.isEmpty())
/*      */     {
/* 3527 */       for (ResourceLocation resourcelocation : Block.blockRegistry.getKeys()) {
/*      */         
/* 3529 */         Block block = (Block)Block.blockRegistry.getObject(resourcelocation);
/* 3530 */         int i = Block.blockRegistry.getIDForObject(block);
/* 3531 */         mapBlockToEntityData.put(block, Integer.valueOf(i));
/*      */       } 
/*      */     }
/*      */     
/* 3535 */     BufferedReader bufferedreader = null;
/*      */ 
/*      */     
/*      */     try {
/* 3539 */       bufferedreader = new BufferedReader(new InputStreamReader(shaderPack.getResourceAsStream("/mc_Entity_x.txt")));
/*      */     }
/* 3541 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3546 */     if (bufferedreader != null) {
/*      */       try {
/*      */         String s1;
/*      */ 
/*      */ 
/*      */         
/* 3552 */         while ((s1 = bufferedreader.readLine()) != null)
/*      */         {
/* 3554 */           Matcher matcher = patternLoadEntityDataMap.matcher(s1);
/*      */           
/* 3556 */           if (matcher.matches()) {
/*      */             
/* 3558 */             String s2 = matcher.group(1);
/* 3559 */             String s = matcher.group(2);
/* 3560 */             int j = Integer.parseInt(s);
/* 3561 */             Block block1 = Block.getBlockFromName(s2);
/*      */             
/* 3563 */             if (block1 != null) {
/*      */               
/* 3565 */               mapBlockToEntityData.put(block1, Integer.valueOf(j));
/*      */               
/*      */               continue;
/*      */             } 
/* 3569 */             SMCLog.warning("Unknown block name %s", new Object[] { s2 });
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/* 3574 */           SMCLog.warning("unmatched %s\n", new Object[] { s1 });
/*      */         }
/*      */       
/*      */       }
/* 3578 */       catch (Exception var9) {
/*      */         
/* 3580 */         SMCLog.warning("Error parsing mc_Entity_x.txt");
/*      */       } 
/*      */     }
/*      */     
/* 3584 */     if (bufferedreader != null) {
/*      */       
/*      */       try {
/*      */         
/* 3588 */         bufferedreader.close();
/*      */       }
/* 3590 */       catch (Exception exception) {}
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static IntBuffer fillIntBufferZero(IntBuffer buf) {
/* 3599 */     int i = buf.limit();
/*      */     
/* 3601 */     for (int j = buf.position(); j < i; j++)
/*      */     {
/* 3603 */       buf.put(j, 0);
/*      */     }
/*      */     
/* 3606 */     return buf;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void uninit() {
/* 3611 */     if (isShaderPackInitialized) {
/*      */       
/* 3613 */       checkGLError("Shaders.uninit pre");
/*      */       
/* 3615 */       for (int i = 0; i < ProgramsAll.length; i++) {
/*      */         
/* 3617 */         Program program = ProgramsAll[i];
/*      */         
/* 3619 */         if (program.getRef() != 0) {
/*      */           
/* 3621 */           ARBShaderObjects.glDeleteObjectARB(program.getRef());
/* 3622 */           checkGLError("del programRef");
/*      */         } 
/*      */         
/* 3625 */         program.setRef(0);
/* 3626 */         program.setId(0);
/* 3627 */         program.setDrawBufSettings((String)null);
/* 3628 */         program.setDrawBuffers((IntBuffer)null);
/* 3629 */         program.setCompositeMipmapSetting(0);
/*      */       } 
/*      */       
/* 3632 */       hasDeferredPrograms = false;
/*      */       
/* 3634 */       if (dfb != 0) {
/*      */         
/* 3636 */         EXTFramebufferObject.glDeleteFramebuffersEXT(dfb);
/* 3637 */         dfb = 0;
/* 3638 */         checkGLError("del dfb");
/*      */       } 
/*      */       
/* 3641 */       if (sfb != 0) {
/*      */         
/* 3643 */         EXTFramebufferObject.glDeleteFramebuffersEXT(sfb);
/* 3644 */         sfb = 0;
/* 3645 */         checkGLError("del sfb");
/*      */       } 
/*      */       
/* 3648 */       if (dfbDepthTextures != null) {
/*      */         
/* 3650 */         GlStateManager.deleteTextures(dfbDepthTextures);
/* 3651 */         fillIntBufferZero(dfbDepthTextures);
/* 3652 */         checkGLError("del dfbDepthTextures");
/*      */       } 
/*      */       
/* 3655 */       if (dfbColorTextures != null) {
/*      */         
/* 3657 */         GlStateManager.deleteTextures(dfbColorTextures);
/* 3658 */         fillIntBufferZero(dfbColorTextures);
/* 3659 */         checkGLError("del dfbTextures");
/*      */       } 
/*      */       
/* 3662 */       if (sfbDepthTextures != null) {
/*      */         
/* 3664 */         GlStateManager.deleteTextures(sfbDepthTextures);
/* 3665 */         fillIntBufferZero(sfbDepthTextures);
/* 3666 */         checkGLError("del shadow depth");
/*      */       } 
/*      */       
/* 3669 */       if (sfbColorTextures != null) {
/*      */         
/* 3671 */         GlStateManager.deleteTextures(sfbColorTextures);
/* 3672 */         fillIntBufferZero(sfbColorTextures);
/* 3673 */         checkGLError("del shadow color");
/*      */       } 
/*      */       
/* 3676 */       if (dfbDrawBuffers != null)
/*      */       {
/* 3678 */         fillIntBufferZero(dfbDrawBuffers);
/*      */       }
/*      */       
/* 3681 */       if (noiseTexture != null) {
/*      */         
/* 3683 */         noiseTexture.deleteTexture();
/* 3684 */         noiseTexture = null;
/*      */       } 
/*      */       
/* 3687 */       SMCLog.info("Uninit");
/* 3688 */       shadowPassInterval = 0;
/* 3689 */       shouldSkipDefaultShadow = false;
/* 3690 */       isShaderPackInitialized = false;
/* 3691 */       checkGLError("Shaders.uninit");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void scheduleResize() {
/* 3697 */     renderDisplayHeight = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void scheduleResizeShadow() {
/* 3702 */     needResizeShadow = true;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void resize() {
/* 3707 */     renderDisplayWidth = mc.displayWidth;
/* 3708 */     renderDisplayHeight = mc.displayHeight;
/* 3709 */     renderWidth = Math.round(renderDisplayWidth * configRenderResMul);
/* 3710 */     renderHeight = Math.round(renderDisplayHeight * configRenderResMul);
/* 3711 */     setupFrameBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void resizeShadow() {
/* 3716 */     needResizeShadow = false;
/* 3717 */     shadowMapWidth = Math.round(spShadowMapWidth * configShadowResMul);
/* 3718 */     shadowMapHeight = Math.round(spShadowMapHeight * configShadowResMul);
/* 3719 */     setupShadowFrameBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setupFrameBuffer() {
/* 3724 */     if (dfb != 0) {
/*      */       
/* 3726 */       EXTFramebufferObject.glDeleteFramebuffersEXT(dfb);
/* 3727 */       GlStateManager.deleteTextures(dfbDepthTextures);
/* 3728 */       GlStateManager.deleteTextures(dfbColorTextures);
/*      */     } 
/*      */     
/* 3731 */     dfb = EXTFramebufferObject.glGenFramebuffersEXT();
/* 3732 */     GL11.glGenTextures((IntBuffer)dfbDepthTextures.clear().limit(usedDepthBuffers));
/* 3733 */     GL11.glGenTextures((IntBuffer)dfbColorTextures.clear().limit(16));
/* 3734 */     dfbDepthTextures.position(0);
/* 3735 */     dfbColorTextures.position(0);
/* 3736 */     EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
/* 3737 */     GL20.glDrawBuffers(0);
/* 3738 */     GL11.glReadBuffer(0);
/*      */     
/* 3740 */     for (int i = 0; i < usedDepthBuffers; i++) {
/*      */       
/* 3742 */       GlStateManager.bindTexture(dfbDepthTextures.get(i));
/* 3743 */       GL11.glTexParameteri(3553, 10242, 33071);
/* 3744 */       GL11.glTexParameteri(3553, 10243, 33071);
/* 3745 */       GL11.glTexParameteri(3553, 10241, 9728);
/* 3746 */       GL11.glTexParameteri(3553, 10240, 9728);
/* 3747 */       GL11.glTexParameteri(3553, 34891, 6409);
/* 3748 */       GL11.glTexImage2D(3553, 0, 6402, renderWidth, renderHeight, 0, 6402, 5126, (FloatBuffer)null);
/*      */     } 
/*      */     
/* 3751 */     EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, dfbDepthTextures.get(0), 0);
/* 3752 */     GL20.glDrawBuffers(dfbDrawBuffers);
/* 3753 */     GL11.glReadBuffer(0);
/* 3754 */     checkGLError("FT d");
/*      */     
/* 3756 */     for (int k = 0; k < usedColorBuffers; k++) {
/*      */       
/* 3758 */       GlStateManager.bindTexture(dfbColorTexturesFlip.getA(k));
/* 3759 */       GL11.glTexParameteri(3553, 10242, 33071);
/* 3760 */       GL11.glTexParameteri(3553, 10243, 33071);
/* 3761 */       GL11.glTexParameteri(3553, 10241, 9729);
/* 3762 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 3763 */       GL11.glTexImage2D(3553, 0, gbuffersFormat[k], renderWidth, renderHeight, 0, getPixelFormat(gbuffersFormat[k]), 33639, (ByteBuffer)null);
/* 3764 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + k, 3553, dfbColorTexturesFlip.getA(k), 0);
/* 3765 */       checkGLError("FT c");
/*      */     } 
/*      */     
/* 3768 */     for (int l = 0; l < usedColorBuffers; l++) {
/*      */       
/* 3770 */       GlStateManager.bindTexture(dfbColorTexturesFlip.getB(l));
/* 3771 */       GL11.glTexParameteri(3553, 10242, 33071);
/* 3772 */       GL11.glTexParameteri(3553, 10243, 33071);
/* 3773 */       GL11.glTexParameteri(3553, 10241, 9729);
/* 3774 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 3775 */       GL11.glTexImage2D(3553, 0, gbuffersFormat[l], renderWidth, renderHeight, 0, getPixelFormat(gbuffersFormat[l]), 33639, (ByteBuffer)null);
/* 3776 */       checkGLError("FT ca");
/*      */     } 
/*      */     
/* 3779 */     int i1 = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/*      */     
/* 3781 */     if (i1 == 36058) {
/*      */       
/* 3783 */       printChatAndLogError("[Shaders] Error: Failed framebuffer incomplete formats");
/*      */       
/* 3785 */       for (int j = 0; j < usedColorBuffers; j++) {
/*      */         
/* 3787 */         GlStateManager.bindTexture(dfbColorTexturesFlip.getA(j));
/* 3788 */         GL11.glTexImage2D(3553, 0, 6408, renderWidth, renderHeight, 0, 32993, 33639, (ByteBuffer)null);
/* 3789 */         EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + j, 3553, dfbColorTexturesFlip.getA(j), 0);
/* 3790 */         checkGLError("FT c");
/*      */       } 
/*      */       
/* 3793 */       i1 = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/*      */       
/* 3795 */       if (i1 == 36053)
/*      */       {
/* 3797 */         SMCLog.info("complete");
/*      */       }
/*      */     } 
/*      */     
/* 3801 */     GlStateManager.bindTexture(0);
/*      */     
/* 3803 */     if (i1 != 36053) {
/*      */       
/* 3805 */       printChatAndLogError("[Shaders] Error: Failed creating framebuffer! (Status " + i1 + ")");
/*      */     }
/*      */     else {
/*      */       
/* 3809 */       SMCLog.info("Framebuffer created.");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getPixelFormat(int internalFormat) {
/* 3815 */     switch (internalFormat) {
/*      */       
/*      */       case 33333:
/*      */       case 33334:
/*      */       case 33339:
/*      */       case 33340:
/*      */       case 36208:
/*      */       case 36209:
/*      */       case 36226:
/*      */       case 36227:
/* 3825 */         return 36251;
/*      */     } 
/*      */     
/* 3828 */     return 32993;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setupShadowFrameBuffer() {
/* 3834 */     if (usedShadowDepthBuffers != 0) {
/*      */       
/* 3836 */       if (sfb != 0) {
/*      */         
/* 3838 */         EXTFramebufferObject.glDeleteFramebuffersEXT(sfb);
/* 3839 */         GlStateManager.deleteTextures(sfbDepthTextures);
/* 3840 */         GlStateManager.deleteTextures(sfbColorTextures);
/*      */       } 
/*      */       
/* 3843 */       sfb = EXTFramebufferObject.glGenFramebuffersEXT();
/* 3844 */       EXTFramebufferObject.glBindFramebufferEXT(36160, sfb);
/* 3845 */       GL11.glDrawBuffer(0);
/* 3846 */       GL11.glReadBuffer(0);
/* 3847 */       GL11.glGenTextures((IntBuffer)sfbDepthTextures.clear().limit(usedShadowDepthBuffers));
/* 3848 */       GL11.glGenTextures((IntBuffer)sfbColorTextures.clear().limit(usedShadowColorBuffers));
/* 3849 */       sfbDepthTextures.position(0);
/* 3850 */       sfbColorTextures.position(0);
/*      */       
/* 3852 */       for (int i = 0; i < usedShadowDepthBuffers; i++) {
/*      */         
/* 3854 */         GlStateManager.bindTexture(sfbDepthTextures.get(i));
/* 3855 */         GL11.glTexParameterf(3553, 10242, 33071.0F);
/* 3856 */         GL11.glTexParameterf(3553, 10243, 33071.0F);
/* 3857 */         int j = shadowFilterNearest[i] ? 9728 : 9729;
/* 3858 */         GL11.glTexParameteri(3553, 10241, j);
/* 3859 */         GL11.glTexParameteri(3553, 10240, j);
/*      */         
/* 3861 */         if (shadowHardwareFilteringEnabled[i])
/*      */         {
/* 3863 */           GL11.glTexParameteri(3553, 34892, 34894);
/*      */         }
/*      */         
/* 3866 */         GL11.glTexImage2D(3553, 0, 6402, shadowMapWidth, shadowMapHeight, 0, 6402, 5126, (FloatBuffer)null);
/*      */       } 
/*      */       
/* 3869 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, sfbDepthTextures.get(0), 0);
/* 3870 */       checkGLError("FT sd");
/*      */       
/* 3872 */       for (int k = 0; k < usedShadowColorBuffers; k++) {
/*      */         
/* 3874 */         GlStateManager.bindTexture(sfbColorTextures.get(k));
/* 3875 */         GL11.glTexParameterf(3553, 10242, 33071.0F);
/* 3876 */         GL11.glTexParameterf(3553, 10243, 33071.0F);
/* 3877 */         int i1 = shadowColorFilterNearest[k] ? 9728 : 9729;
/* 3878 */         GL11.glTexParameteri(3553, 10241, i1);
/* 3879 */         GL11.glTexParameteri(3553, 10240, i1);
/* 3880 */         GL11.glTexImage2D(3553, 0, 6408, shadowMapWidth, shadowMapHeight, 0, 32993, 33639, (ByteBuffer)null);
/* 3881 */         EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + k, 3553, sfbColorTextures.get(k), 0);
/* 3882 */         checkGLError("FT sc");
/*      */       } 
/*      */       
/* 3885 */       GlStateManager.bindTexture(0);
/*      */       
/* 3887 */       if (usedShadowColorBuffers > 0)
/*      */       {
/* 3889 */         GL20.glDrawBuffers(sfbDrawBuffers);
/*      */       }
/*      */       
/* 3892 */       int l = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/*      */       
/* 3894 */       if (l != 36053) {
/*      */         
/* 3896 */         printChatAndLogError("[Shaders] Error: Failed creating shadow framebuffer! (Status " + l + ")");
/*      */       }
/*      */       else {
/*      */         
/* 3900 */         SMCLog.info("Shadow framebuffer created.");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginRender(Minecraft minecraft, float partialTicks, long finishTimeNano) {
/* 3907 */     checkGLError("pre beginRender");
/* 3908 */     checkWorldChanged((World)mc.theWorld);
/* 3909 */     mc.mcProfiler.startSection("init");
/* 3910 */     entityRenderer = mc.entityRenderer;
/*      */     
/* 3912 */     if (!isShaderPackInitialized) {
/*      */       
/*      */       try {
/*      */         
/* 3916 */         init();
/*      */       }
/* 3918 */       catch (IllegalStateException illegalstateexception) {
/*      */         
/* 3920 */         if (Config.normalize(illegalstateexception.getMessage()).equals("Function is not supported")) {
/*      */           
/* 3922 */           printChatAndLogError("[Shaders] Error: " + illegalstateexception.getMessage());
/* 3923 */           illegalstateexception.printStackTrace();
/* 3924 */           setShaderPack("OFF");
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     }
/* 3930 */     if (mc.displayWidth != renderDisplayWidth || mc.displayHeight != renderDisplayHeight)
/*      */     {
/* 3932 */       resize();
/*      */     }
/*      */     
/* 3935 */     if (needResizeShadow)
/*      */     {
/* 3937 */       resizeShadow();
/*      */     }
/*      */     
/* 3940 */     worldTime = mc.theWorld.getWorldTime();
/* 3941 */     diffWorldTime = (worldTime - lastWorldTime) % 24000L;
/*      */     
/* 3943 */     if (diffWorldTime < 0L)
/*      */     {
/* 3945 */       diffWorldTime += 24000L;
/*      */     }
/*      */     
/* 3948 */     lastWorldTime = worldTime;
/* 3949 */     moonPhase = mc.theWorld.getMoonPhase();
/* 3950 */     frameCounter++;
/*      */     
/* 3952 */     if (frameCounter >= 720720)
/*      */     {
/* 3954 */       frameCounter = 0;
/*      */     }
/*      */     
/* 3957 */     systemTime = System.currentTimeMillis();
/*      */     
/* 3959 */     if (lastSystemTime == 0L)
/*      */     {
/* 3961 */       lastSystemTime = systemTime;
/*      */     }
/*      */     
/* 3964 */     diffSystemTime = systemTime - lastSystemTime;
/* 3965 */     lastSystemTime = systemTime;
/* 3966 */     frameTime = (float)diffSystemTime / 1000.0F;
/* 3967 */     frameTimeCounter += frameTime;
/* 3968 */     frameTimeCounter %= 3600.0F;
/* 3969 */     rainStrength = minecraft.theWorld.getRainStrength(partialTicks);
/* 3970 */     float f = (float)diffSystemTime * 0.01F;
/* 3971 */     float f1 = (float)Math.exp(Math.log(0.5D) * f / ((wetness < rainStrength) ? drynessHalfLife : wetnessHalfLife));
/* 3972 */     wetness = wetness * f1 + rainStrength * (1.0F - f1);
/* 3973 */     Entity entity = mc.getRenderViewEntity();
/*      */     
/* 3975 */     if (entity != null) {
/*      */       
/* 3977 */       isSleeping = (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPlayerSleeping());
/* 3978 */       eyePosY = (float)entity.posY * partialTicks + (float)entity.lastTickPosY * (1.0F - partialTicks);
/* 3979 */       eyeBrightness = entity.getBrightnessForRender(partialTicks);
/* 3980 */       f1 = (float)diffSystemTime * 0.01F;
/* 3981 */       float f2 = (float)Math.exp(Math.log(0.5D) * f1 / eyeBrightnessHalflife);
/* 3982 */       eyeBrightnessFadeX = eyeBrightnessFadeX * f2 + (eyeBrightness & 0xFFFF) * (1.0F - f2);
/* 3983 */       eyeBrightnessFadeY = eyeBrightnessFadeY * f2 + (eyeBrightness >> 16) * (1.0F - f2);
/* 3984 */       Block block = ActiveRenderInfo.getBlockAtEntityViewpoint((World)mc.theWorld, entity, partialTicks);
/* 3985 */       Material material = block.getMaterial();
/*      */       
/* 3987 */       if (material == Material.water) {
/*      */         
/* 3989 */         isEyeInWater = 1;
/*      */       }
/* 3991 */       else if (material == Material.lava) {
/*      */         
/* 3993 */         isEyeInWater = 2;
/*      */       }
/*      */       else {
/*      */         
/* 3997 */         isEyeInWater = 0;
/*      */       } 
/*      */       
/* 4000 */       if (mc.thePlayer != null) {
/*      */         
/* 4002 */         nightVision = 0.0F;
/*      */         
/* 4004 */         if (mc.thePlayer.isPotionActive(Potion.nightVision))
/*      */         {
/* 4006 */           nightVision = (Config.getMinecraft()).entityRenderer.getNightVisionBrightness((EntityLivingBase)mc.thePlayer, partialTicks);
/*      */         }
/*      */         
/* 4009 */         blindness = 0.0F;
/*      */         
/* 4011 */         if (mc.thePlayer.isPotionActive(Potion.blindness)) {
/*      */           
/* 4013 */           int i = mc.thePlayer.getActivePotionEffect(Potion.blindness).getDuration();
/* 4014 */           blindness = Config.limit(i / 20.0F, 0.0F, 1.0F);
/*      */         } 
/*      */       } 
/*      */       
/* 4018 */       Vec3 vec3 = mc.theWorld.getSkyColor(entity, partialTicks);
/* 4019 */       vec3 = CustomColors.getWorldSkyColor(vec3, currentWorld, entity, partialTicks);
/* 4020 */       skyColorR = (float)vec3.xCoord;
/* 4021 */       skyColorG = (float)vec3.yCoord;
/* 4022 */       skyColorB = (float)vec3.zCoord;
/*      */     } 
/*      */     
/* 4025 */     isRenderingWorld = true;
/* 4026 */     isCompositeRendered = false;
/* 4027 */     isShadowPass = false;
/* 4028 */     isHandRenderedMain = false;
/* 4029 */     isHandRenderedOff = false;
/* 4030 */     skipRenderHandMain = false;
/* 4031 */     skipRenderHandOff = false;
/* 4032 */     bindGbuffersTextures();
/* 4033 */     previousCameraPositionX = cameraPositionX;
/* 4034 */     previousCameraPositionY = cameraPositionY;
/* 4035 */     previousCameraPositionZ = cameraPositionZ;
/* 4036 */     previousProjection.position(0);
/* 4037 */     projection.position(0);
/* 4038 */     previousProjection.put(projection);
/* 4039 */     previousProjection.position(0);
/* 4040 */     projection.position(0);
/* 4041 */     previousModelView.position(0);
/* 4042 */     modelView.position(0);
/* 4043 */     previousModelView.put(modelView);
/* 4044 */     previousModelView.position(0);
/* 4045 */     modelView.position(0);
/* 4046 */     checkGLError("beginRender");
/* 4047 */     ShadersRender.renderShadowMap(entityRenderer, 0, partialTicks, finishTimeNano);
/* 4048 */     mc.mcProfiler.endSection();
/* 4049 */     EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
/*      */     
/* 4051 */     for (int j = 0; j < usedColorBuffers; j++)
/*      */     {
/* 4053 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + j, 3553, dfbColorTexturesFlip.getA(j), 0);
/*      */     }
/*      */     
/* 4056 */     checkGLError("end beginRender");
/*      */   }
/*      */ 
/*      */   
/*      */   private static void bindGbuffersTextures() {
/* 4061 */     if (usedShadowDepthBuffers >= 1) {
/*      */       
/* 4063 */       GlStateManager.setActiveTexture(33988);
/* 4064 */       GlStateManager.bindTexture(sfbDepthTextures.get(0));
/*      */       
/* 4066 */       if (usedShadowDepthBuffers >= 2) {
/*      */         
/* 4068 */         GlStateManager.setActiveTexture(33989);
/* 4069 */         GlStateManager.bindTexture(sfbDepthTextures.get(1));
/*      */       } 
/*      */     } 
/*      */     
/* 4073 */     GlStateManager.setActiveTexture(33984);
/*      */     
/* 4075 */     for (int i = 0; i < usedColorBuffers; i++) {
/*      */       
/* 4077 */       GlStateManager.bindTexture(dfbColorTexturesFlip.getA(i));
/* 4078 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 4079 */       GL11.glTexParameteri(3553, 10241, 9729);
/* 4080 */       GlStateManager.bindTexture(dfbColorTexturesFlip.getB(i));
/* 4081 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 4082 */       GL11.glTexParameteri(3553, 10241, 9729);
/*      */     } 
/*      */     
/* 4085 */     GlStateManager.bindTexture(0);
/*      */     
/* 4087 */     for (int j = 0; j < 4 && 4 + j < usedColorBuffers; j++) {
/*      */       
/* 4089 */       GlStateManager.setActiveTexture(33991 + j);
/* 4090 */       GlStateManager.bindTexture(dfbColorTexturesFlip.getA(4 + j));
/*      */     } 
/*      */     
/* 4093 */     GlStateManager.setActiveTexture(33990);
/* 4094 */     GlStateManager.bindTexture(dfbDepthTextures.get(0));
/*      */     
/* 4096 */     if (usedDepthBuffers >= 2) {
/*      */       
/* 4098 */       GlStateManager.setActiveTexture(33995);
/* 4099 */       GlStateManager.bindTexture(dfbDepthTextures.get(1));
/*      */       
/* 4101 */       if (usedDepthBuffers >= 3) {
/*      */         
/* 4103 */         GlStateManager.setActiveTexture(33996);
/* 4104 */         GlStateManager.bindTexture(dfbDepthTextures.get(2));
/*      */       } 
/*      */     } 
/*      */     
/* 4108 */     for (int k = 0; k < usedShadowColorBuffers; k++) {
/*      */       
/* 4110 */       GlStateManager.setActiveTexture(33997 + k);
/* 4111 */       GlStateManager.bindTexture(sfbColorTextures.get(k));
/*      */     } 
/*      */     
/* 4114 */     if (noiseTextureEnabled) {
/*      */       
/* 4116 */       GlStateManager.setActiveTexture(33984 + noiseTexture.getTextureUnit());
/* 4117 */       GlStateManager.bindTexture(noiseTexture.getTextureId());
/*      */     } 
/*      */     
/* 4120 */     bindCustomTextures(customTexturesGbuffers);
/* 4121 */     GlStateManager.setActiveTexture(33984);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void checkWorldChanged(World world) {
/* 4126 */     if (currentWorld != world) {
/*      */       
/* 4128 */       World oldworld = currentWorld;
/* 4129 */       currentWorld = world;
/* 4130 */       setCameraOffset(mc.getRenderViewEntity());
/* 4131 */       int i = getDimensionId(oldworld);
/* 4132 */       int j = getDimensionId(world);
/*      */       
/* 4134 */       if (j != i) {
/*      */         
/* 4136 */         boolean flag = shaderPackDimensions.contains(Integer.valueOf(i));
/* 4137 */         boolean flag1 = shaderPackDimensions.contains(Integer.valueOf(j));
/*      */         
/* 4139 */         if (flag || flag1)
/*      */         {
/* 4141 */           uninit();
/*      */         }
/*      */       } 
/*      */       
/* 4145 */       Smoother.resetValues();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getDimensionId(World world) {
/* 4151 */     return (world == null) ? Integer.MIN_VALUE : world.provider.getDimensionId();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginRenderPass(int pass, float partialTicks, long finishTimeNano) {
/* 4156 */     if (!isShadowPass) {
/*      */       
/* 4158 */       EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
/* 4159 */       GL11.glViewport(0, 0, renderWidth, renderHeight);
/* 4160 */       activeDrawBuffers = null;
/* 4161 */       ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
/* 4162 */       useProgram(ProgramTextured);
/* 4163 */       checkGLError("end beginRenderPass");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setViewport(int vx, int vy, int vw, int vh) {
/* 4169 */     GlStateManager.colorMask(true, true, true, true);
/*      */     
/* 4171 */     if (isShadowPass) {
/*      */       
/* 4173 */       GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
/*      */     }
/*      */     else {
/*      */       
/* 4177 */       GL11.glViewport(0, 0, renderWidth, renderHeight);
/* 4178 */       EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
/* 4179 */       isRenderingDfb = true;
/* 4180 */       GlStateManager.enableCull();
/* 4181 */       GlStateManager.enableDepth();
/* 4182 */       setDrawBuffers(drawBuffersNone);
/* 4183 */       useProgram(ProgramTextured);
/* 4184 */       checkGLError("beginRenderPass");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFogMode(int value) {
/* 4190 */     fogMode = value;
/*      */     
/* 4192 */     if (fogEnabled)
/*      */     {
/* 4194 */       setProgramUniform1i(uniform_fogMode, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFogColor(float r, float g, float b) {
/* 4200 */     fogColorR = r;
/* 4201 */     fogColorG = g;
/* 4202 */     fogColorB = b;
/* 4203 */     setProgramUniform3f(uniform_fogColor, fogColorR, fogColorG, fogColorB);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setClearColor(float red, float green, float blue, float alpha) {
/* 4208 */     GlStateManager.clearColor(red, green, blue, alpha);
/* 4209 */     clearColorR = red;
/* 4210 */     clearColorG = green;
/* 4211 */     clearColorB = blue;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void clearRenderBuffer() {
/* 4216 */     if (isShadowPass) {
/*      */       
/* 4218 */       checkGLError("shadow clear pre");
/* 4219 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, sfbDepthTextures.get(0), 0);
/* 4220 */       GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 4221 */       GL20.glDrawBuffers(ProgramShadow.getDrawBuffers());
/* 4222 */       checkFramebufferStatus("shadow clear");
/* 4223 */       GL11.glClear(16640);
/* 4224 */       checkGLError("shadow clear");
/*      */     }
/*      */     else {
/*      */       
/* 4228 */       checkGLError("clear pre");
/*      */       
/* 4230 */       if (gbuffersClear[0]) {
/*      */         
/* 4232 */         Vector4f vector4f = gbuffersClearColor[0];
/*      */         
/* 4234 */         if (vector4f != null)
/*      */         {
/* 4236 */           GL11.glClearColor(vector4f.getX(), vector4f.getY(), vector4f.getZ(), vector4f.getW());
/*      */         }
/*      */         
/* 4239 */         if (dfbColorTexturesFlip.isChanged(0)) {
/*      */           
/* 4241 */           EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, dfbColorTexturesFlip.getB(0), 0);
/* 4242 */           GL20.glDrawBuffers(36064);
/* 4243 */           GL11.glClear(16384);
/* 4244 */           EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, dfbColorTexturesFlip.getA(0), 0);
/*      */         } 
/*      */         
/* 4247 */         GL20.glDrawBuffers(36064);
/* 4248 */         GL11.glClear(16384);
/*      */       } 
/*      */       
/* 4251 */       if (gbuffersClear[1]) {
/*      */         
/* 4253 */         GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 4254 */         Vector4f vector4f2 = gbuffersClearColor[1];
/*      */         
/* 4256 */         if (vector4f2 != null)
/*      */         {
/* 4258 */           GL11.glClearColor(vector4f2.getX(), vector4f2.getY(), vector4f2.getZ(), vector4f2.getW());
/*      */         }
/*      */         
/* 4261 */         if (dfbColorTexturesFlip.isChanged(1)) {
/*      */           
/* 4263 */           EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36065, 3553, dfbColorTexturesFlip.getB(1), 0);
/* 4264 */           GL20.glDrawBuffers(36065);
/* 4265 */           GL11.glClear(16384);
/* 4266 */           EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36065, 3553, dfbColorTexturesFlip.getA(1), 0);
/*      */         } 
/*      */         
/* 4269 */         GL20.glDrawBuffers(36065);
/* 4270 */         GL11.glClear(16384);
/*      */       } 
/*      */       
/* 4273 */       for (int i = 2; i < usedColorBuffers; i++) {
/*      */         
/* 4275 */         if (gbuffersClear[i]) {
/*      */           
/* 4277 */           GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 4278 */           Vector4f vector4f1 = gbuffersClearColor[i];
/*      */           
/* 4280 */           if (vector4f1 != null)
/*      */           {
/* 4282 */             GL11.glClearColor(vector4f1.getX(), vector4f1.getY(), vector4f1.getZ(), vector4f1.getW());
/*      */           }
/*      */           
/* 4285 */           if (dfbColorTexturesFlip.isChanged(i)) {
/*      */             
/* 4287 */             EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + i, 3553, dfbColorTexturesFlip.getB(i), 0);
/* 4288 */             GL20.glDrawBuffers(36064 + i);
/* 4289 */             GL11.glClear(16384);
/* 4290 */             EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + i, 3553, dfbColorTexturesFlip.getA(i), 0);
/*      */           } 
/*      */           
/* 4293 */           GL20.glDrawBuffers(36064 + i);
/* 4294 */           GL11.glClear(16384);
/*      */         } 
/*      */       } 
/*      */       
/* 4298 */       setDrawBuffers(dfbDrawBuffers);
/* 4299 */       checkFramebufferStatus("clear");
/* 4300 */       checkGLError("clear");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setCamera(float partialTicks) {
/* 4306 */     Entity entity = mc.getRenderViewEntity();
/* 4307 */     double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 4308 */     double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 4309 */     double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 4310 */     updateCameraOffset(entity);
/* 4311 */     cameraPositionX = d0 - cameraOffsetX;
/* 4312 */     cameraPositionY = d1;
/* 4313 */     cameraPositionZ = d2 - cameraOffsetZ;
/* 4314 */     GL11.glGetFloat(2983, (FloatBuffer)projection.position(0));
/* 4315 */     SMath.invertMat4FBFA((FloatBuffer)projectionInverse.position(0), (FloatBuffer)projection.position(0), faProjectionInverse, faProjection);
/* 4316 */     projection.position(0);
/* 4317 */     projectionInverse.position(0);
/* 4318 */     GL11.glGetFloat(2982, (FloatBuffer)modelView.position(0));
/* 4319 */     SMath.invertMat4FBFA((FloatBuffer)modelViewInverse.position(0), (FloatBuffer)modelView.position(0), faModelViewInverse, faModelView);
/* 4320 */     modelView.position(0);
/* 4321 */     modelViewInverse.position(0);
/* 4322 */     checkGLError("setCamera");
/*      */   }
/*      */ 
/*      */   
/*      */   private static void updateCameraOffset(Entity viewEntity) {
/* 4327 */     double d0 = Math.abs(cameraPositionX - previousCameraPositionX);
/* 4328 */     double d1 = Math.abs(cameraPositionZ - previousCameraPositionZ);
/* 4329 */     double d2 = Math.abs(cameraPositionX);
/* 4330 */     double d3 = Math.abs(cameraPositionZ);
/*      */     
/* 4332 */     if (d0 > 1000.0D || d1 > 1000.0D || d2 > 1000000.0D || d3 > 1000000.0D)
/*      */     {
/* 4334 */       setCameraOffset(viewEntity);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setCameraOffset(Entity viewEntity) {
/* 4340 */     if (viewEntity == null) {
/*      */       
/* 4342 */       cameraOffsetX = 0;
/* 4343 */       cameraOffsetZ = 0;
/*      */     }
/*      */     else {
/*      */       
/* 4347 */       cameraOffsetX = (int)viewEntity.posX / 1000 * 1000;
/* 4348 */       cameraOffsetZ = (int)viewEntity.posZ / 1000 * 1000;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setCameraShadow(float partialTicks) {
/* 4354 */     Entity entity = mc.getRenderViewEntity();
/* 4355 */     double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 4356 */     double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 4357 */     double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 4358 */     updateCameraOffset(entity);
/* 4359 */     cameraPositionX = d0 - cameraOffsetX;
/* 4360 */     cameraPositionY = d1;
/* 4361 */     cameraPositionZ = d2 - cameraOffsetZ;
/* 4362 */     GL11.glGetFloat(2983, (FloatBuffer)projection.position(0));
/* 4363 */     SMath.invertMat4FBFA((FloatBuffer)projectionInverse.position(0), (FloatBuffer)projection.position(0), faProjectionInverse, faProjection);
/* 4364 */     projection.position(0);
/* 4365 */     projectionInverse.position(0);
/* 4366 */     GL11.glGetFloat(2982, (FloatBuffer)modelView.position(0));
/* 4367 */     SMath.invertMat4FBFA((FloatBuffer)modelViewInverse.position(0), (FloatBuffer)modelView.position(0), faModelViewInverse, faModelView);
/* 4368 */     modelView.position(0);
/* 4369 */     modelViewInverse.position(0);
/* 4370 */     GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
/* 4371 */     GL11.glMatrixMode(5889);
/* 4372 */     GL11.glLoadIdentity();
/*      */     
/* 4374 */     if (shadowMapIsOrtho) {
/*      */       
/* 4376 */       GL11.glOrtho(-shadowMapHalfPlane, shadowMapHalfPlane, -shadowMapHalfPlane, shadowMapHalfPlane, 0.05000000074505806D, 256.0D);
/*      */     }
/*      */     else {
/*      */       
/* 4380 */       GLU.gluPerspective(shadowMapFOV, shadowMapWidth / shadowMapHeight, 0.05F, 256.0F);
/*      */     } 
/*      */     
/* 4383 */     GL11.glMatrixMode(5888);
/* 4384 */     GL11.glLoadIdentity();
/* 4385 */     GL11.glTranslatef(0.0F, 0.0F, -100.0F);
/* 4386 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 4387 */     celestialAngle = mc.theWorld.getCelestialAngle(partialTicks);
/* 4388 */     sunAngle = (celestialAngle < 0.75F) ? (celestialAngle + 0.25F) : (celestialAngle - 0.75F);
/* 4389 */     float f = celestialAngle * -360.0F;
/* 4390 */     float f1 = (shadowAngleInterval > 0.0F) ? (f % shadowAngleInterval - shadowAngleInterval * 0.5F) : 0.0F;
/*      */     
/* 4392 */     if (sunAngle <= 0.5D) {
/*      */       
/* 4394 */       GL11.glRotatef(f - f1, 0.0F, 0.0F, 1.0F);
/* 4395 */       GL11.glRotatef(sunPathRotation, 1.0F, 0.0F, 0.0F);
/* 4396 */       shadowAngle = sunAngle;
/*      */     }
/*      */     else {
/*      */       
/* 4400 */       GL11.glRotatef(f + 180.0F - f1, 0.0F, 0.0F, 1.0F);
/* 4401 */       GL11.glRotatef(sunPathRotation, 1.0F, 0.0F, 0.0F);
/* 4402 */       shadowAngle = sunAngle - 0.5F;
/*      */     } 
/*      */     
/* 4405 */     if (shadowMapIsOrtho) {
/*      */       
/* 4407 */       float f2 = shadowIntervalSize;
/* 4408 */       float f3 = f2 / 2.0F;
/* 4409 */       GL11.glTranslatef((float)d0 % f2 - f3, (float)d1 % f2 - f3, (float)d2 % f2 - f3);
/*      */     } 
/*      */     
/* 4412 */     float f9 = sunAngle * 6.2831855F;
/* 4413 */     float f10 = (float)Math.cos(f9);
/* 4414 */     float f4 = (float)Math.sin(f9);
/* 4415 */     float f5 = sunPathRotation * 6.2831855F;
/* 4416 */     float f6 = f10;
/* 4417 */     float f7 = f4 * (float)Math.cos(f5);
/* 4418 */     float f8 = f4 * (float)Math.sin(f5);
/*      */     
/* 4420 */     if (sunAngle > 0.5D) {
/*      */       
/* 4422 */       f6 = -f10;
/* 4423 */       f7 = -f7;
/* 4424 */       f8 = -f8;
/*      */     } 
/*      */     
/* 4427 */     shadowLightPositionVector[0] = f6;
/* 4428 */     shadowLightPositionVector[1] = f7;
/* 4429 */     shadowLightPositionVector[2] = f8;
/* 4430 */     shadowLightPositionVector[3] = 0.0F;
/* 4431 */     GL11.glGetFloat(2983, (FloatBuffer)shadowProjection.position(0));
/* 4432 */     SMath.invertMat4FBFA((FloatBuffer)shadowProjectionInverse.position(0), (FloatBuffer)shadowProjection.position(0), faShadowProjectionInverse, faShadowProjection);
/* 4433 */     shadowProjection.position(0);
/* 4434 */     shadowProjectionInverse.position(0);
/* 4435 */     GL11.glGetFloat(2982, (FloatBuffer)shadowModelView.position(0));
/* 4436 */     SMath.invertMat4FBFA((FloatBuffer)shadowModelViewInverse.position(0), (FloatBuffer)shadowModelView.position(0), faShadowModelViewInverse, faShadowModelView);
/* 4437 */     shadowModelView.position(0);
/* 4438 */     shadowModelViewInverse.position(0);
/* 4439 */     setProgramUniformMatrix4ARB(uniform_gbufferProjection, false, projection);
/* 4440 */     setProgramUniformMatrix4ARB(uniform_gbufferProjectionInverse, false, projectionInverse);
/* 4441 */     setProgramUniformMatrix4ARB(uniform_gbufferPreviousProjection, false, previousProjection);
/* 4442 */     setProgramUniformMatrix4ARB(uniform_gbufferModelView, false, modelView);
/* 4443 */     setProgramUniformMatrix4ARB(uniform_gbufferModelViewInverse, false, modelViewInverse);
/* 4444 */     setProgramUniformMatrix4ARB(uniform_gbufferPreviousModelView, false, previousModelView);
/* 4445 */     setProgramUniformMatrix4ARB(uniform_shadowProjection, false, shadowProjection);
/* 4446 */     setProgramUniformMatrix4ARB(uniform_shadowProjectionInverse, false, shadowProjectionInverse);
/* 4447 */     setProgramUniformMatrix4ARB(uniform_shadowModelView, false, shadowModelView);
/* 4448 */     setProgramUniformMatrix4ARB(uniform_shadowModelViewInverse, false, shadowModelViewInverse);
/* 4449 */     mc.gameSettings.thirdPersonView = 1;
/* 4450 */     checkGLError("setCamera");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void preCelestialRotate() {
/* 4455 */     GL11.glRotatef(sunPathRotation, 0.0F, 0.0F, 1.0F);
/* 4456 */     checkGLError("preCelestialRotate");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void postCelestialRotate() {
/* 4461 */     FloatBuffer floatbuffer = tempMatrixDirectBuffer;
/* 4462 */     floatbuffer.clear();
/* 4463 */     GL11.glGetFloat(2982, floatbuffer);
/* 4464 */     floatbuffer.get(tempMat, 0, 16);
/* 4465 */     SMath.multiplyMat4xVec4(sunPosition, tempMat, sunPosModelView);
/* 4466 */     SMath.multiplyMat4xVec4(moonPosition, tempMat, moonPosModelView);
/* 4467 */     System.arraycopy((shadowAngle == sunAngle) ? sunPosition : moonPosition, 0, shadowLightPosition, 0, 3);
/* 4468 */     setProgramUniform3f(uniform_sunPosition, sunPosition[0], sunPosition[1], sunPosition[2]);
/* 4469 */     setProgramUniform3f(uniform_moonPosition, moonPosition[0], moonPosition[1], moonPosition[2]);
/* 4470 */     setProgramUniform3f(uniform_shadowLightPosition, shadowLightPosition[0], shadowLightPosition[1], shadowLightPosition[2]);
/*      */     
/* 4472 */     if (customUniforms != null)
/*      */     {
/* 4474 */       customUniforms.update();
/*      */     }
/*      */     
/* 4477 */     checkGLError("postCelestialRotate");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setUpPosition() {
/* 4482 */     FloatBuffer floatbuffer = tempMatrixDirectBuffer;
/* 4483 */     floatbuffer.clear();
/* 4484 */     GL11.glGetFloat(2982, floatbuffer);
/* 4485 */     floatbuffer.get(tempMat, 0, 16);
/* 4486 */     SMath.multiplyMat4xVec4(upPosition, tempMat, upPosModelView);
/* 4487 */     setProgramUniform3f(uniform_upPosition, upPosition[0], upPosition[1], upPosition[2]);
/*      */     
/* 4489 */     if (customUniforms != null)
/*      */     {
/* 4491 */       customUniforms.update();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void genCompositeMipmap() {
/* 4497 */     if (hasGlGenMipmap) {
/*      */       
/* 4499 */       for (int i = 0; i < usedColorBuffers; i++) {
/*      */         
/* 4501 */         if ((activeCompositeMipmapSetting & 1 << i) != 0) {
/*      */           
/* 4503 */           GlStateManager.setActiveTexture(33984 + colorTextureImageUnit[i]);
/* 4504 */           GL11.glTexParameteri(3553, 10241, 9987);
/* 4505 */           GL30.glGenerateMipmap(3553);
/*      */         } 
/*      */       } 
/*      */       
/* 4509 */       GlStateManager.setActiveTexture(33984);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawComposite() {
/* 4515 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 4516 */     drawCompositeQuad();
/* 4517 */     int i = activeProgram.getCountInstances();
/*      */     
/* 4519 */     if (i > 1) {
/*      */       
/* 4521 */       for (int j = 1; j < i; j++) {
/*      */         
/* 4523 */         uniform_instanceId.setValue(j);
/* 4524 */         drawCompositeQuad();
/*      */       } 
/*      */       
/* 4527 */       uniform_instanceId.setValue(0);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void drawCompositeQuad() {
/* 4533 */     if (!canRenderQuads()) {
/*      */       
/* 4535 */       GL11.glBegin(5);
/* 4536 */       GL11.glTexCoord2f(0.0F, 0.0F);
/* 4537 */       GL11.glVertex3f(0.0F, 0.0F, 0.0F);
/* 4538 */       GL11.glTexCoord2f(1.0F, 0.0F);
/* 4539 */       GL11.glVertex3f(1.0F, 0.0F, 0.0F);
/* 4540 */       GL11.glTexCoord2f(0.0F, 1.0F);
/* 4541 */       GL11.glVertex3f(0.0F, 1.0F, 0.0F);
/* 4542 */       GL11.glTexCoord2f(1.0F, 1.0F);
/* 4543 */       GL11.glVertex3f(1.0F, 1.0F, 0.0F);
/* 4544 */       GL11.glEnd();
/*      */     }
/*      */     else {
/*      */       
/* 4548 */       GL11.glBegin(7);
/* 4549 */       GL11.glTexCoord2f(0.0F, 0.0F);
/* 4550 */       GL11.glVertex3f(0.0F, 0.0F, 0.0F);
/* 4551 */       GL11.glTexCoord2f(1.0F, 0.0F);
/* 4552 */       GL11.glVertex3f(1.0F, 0.0F, 0.0F);
/* 4553 */       GL11.glTexCoord2f(1.0F, 1.0F);
/* 4554 */       GL11.glVertex3f(1.0F, 1.0F, 0.0F);
/* 4555 */       GL11.glTexCoord2f(0.0F, 1.0F);
/* 4556 */       GL11.glVertex3f(0.0F, 1.0F, 0.0F);
/* 4557 */       GL11.glEnd();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderDeferred() {
/* 4563 */     if (!isShadowPass) {
/*      */       
/* 4565 */       boolean flag = checkBufferFlip(ProgramDeferredPre);
/*      */       
/* 4567 */       if (hasDeferredPrograms) {
/*      */         
/* 4569 */         checkGLError("pre-render Deferred");
/* 4570 */         renderComposites(ProgramsDeferred, false);
/* 4571 */         flag = true;
/*      */       } 
/*      */       
/* 4574 */       if (flag) {
/*      */         
/* 4576 */         bindGbuffersTextures();
/*      */         
/* 4578 */         for (int i = 0; i < usedColorBuffers; i++)
/*      */         {
/* 4580 */           EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + i, 3553, dfbColorTexturesFlip.getA(i), 0);
/*      */         }
/*      */         
/* 4583 */         if (ProgramWater.getDrawBuffers() != null) {
/*      */           
/* 4585 */           setDrawBuffers(ProgramWater.getDrawBuffers());
/*      */         }
/*      */         else {
/*      */           
/* 4589 */           setDrawBuffers(dfbDrawBuffers);
/*      */         } 
/*      */         
/* 4592 */         GlStateManager.setActiveTexture(33984);
/* 4593 */         mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderCompositeFinal() {
/* 4600 */     if (!isShadowPass) {
/*      */       
/* 4602 */       checkBufferFlip(ProgramCompositePre);
/* 4603 */       checkGLError("pre-render CompositeFinal");
/* 4604 */       renderComposites(ProgramsComposite, true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean checkBufferFlip(Program program) {
/* 4610 */     boolean flag = false;
/* 4611 */     Boolean[] aboolean = program.getBuffersFlip();
/*      */     
/* 4613 */     for (int i = 0; i < usedColorBuffers; i++) {
/*      */       
/* 4615 */       if (Config.isTrue(aboolean[i])) {
/*      */         
/* 4617 */         dfbColorTexturesFlip.flip(i);
/* 4618 */         flag = true;
/*      */       } 
/*      */     } 
/*      */     
/* 4622 */     return flag;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void renderComposites(Program[] ps, boolean renderFinal) {
/* 4627 */     if (!isShadowPass) {
/*      */       
/* 4629 */       GL11.glPushMatrix();
/* 4630 */       GL11.glLoadIdentity();
/* 4631 */       GL11.glMatrixMode(5889);
/* 4632 */       GL11.glPushMatrix();
/* 4633 */       GL11.glLoadIdentity();
/* 4634 */       GL11.glOrtho(0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D);
/* 4635 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 4636 */       GlStateManager.enableTexture2D();
/* 4637 */       GlStateManager.disableAlpha();
/* 4638 */       GlStateManager.disableBlend();
/* 4639 */       GlStateManager.enableDepth();
/* 4640 */       GlStateManager.depthFunc(519);
/* 4641 */       GlStateManager.depthMask(false);
/* 4642 */       GlStateManager.disableLighting();
/*      */       
/* 4644 */       if (usedShadowDepthBuffers >= 1) {
/*      */         
/* 4646 */         GlStateManager.setActiveTexture(33988);
/* 4647 */         GlStateManager.bindTexture(sfbDepthTextures.get(0));
/*      */         
/* 4649 */         if (usedShadowDepthBuffers >= 2) {
/*      */           
/* 4651 */           GlStateManager.setActiveTexture(33989);
/* 4652 */           GlStateManager.bindTexture(sfbDepthTextures.get(1));
/*      */         } 
/*      */       } 
/*      */       
/* 4656 */       for (int i = 0; i < usedColorBuffers; i++) {
/*      */         
/* 4658 */         GlStateManager.setActiveTexture(33984 + colorTextureImageUnit[i]);
/* 4659 */         GlStateManager.bindTexture(dfbColorTexturesFlip.getA(i));
/*      */       } 
/*      */       
/* 4662 */       GlStateManager.setActiveTexture(33990);
/* 4663 */       GlStateManager.bindTexture(dfbDepthTextures.get(0));
/*      */       
/* 4665 */       if (usedDepthBuffers >= 2) {
/*      */         
/* 4667 */         GlStateManager.setActiveTexture(33995);
/* 4668 */         GlStateManager.bindTexture(dfbDepthTextures.get(1));
/*      */         
/* 4670 */         if (usedDepthBuffers >= 3) {
/*      */           
/* 4672 */           GlStateManager.setActiveTexture(33996);
/* 4673 */           GlStateManager.bindTexture(dfbDepthTextures.get(2));
/*      */         } 
/*      */       } 
/*      */       
/* 4677 */       for (int k = 0; k < usedShadowColorBuffers; k++) {
/*      */         
/* 4679 */         GlStateManager.setActiveTexture(33997 + k);
/* 4680 */         GlStateManager.bindTexture(sfbColorTextures.get(k));
/*      */       } 
/*      */       
/* 4683 */       if (noiseTextureEnabled) {
/*      */         
/* 4685 */         GlStateManager.setActiveTexture(33984 + noiseTexture.getTextureUnit());
/* 4686 */         GlStateManager.bindTexture(noiseTexture.getTextureId());
/*      */       } 
/*      */       
/* 4689 */       if (renderFinal) {
/*      */         
/* 4691 */         bindCustomTextures(customTexturesComposite);
/*      */       }
/*      */       else {
/*      */         
/* 4695 */         bindCustomTextures(customTexturesDeferred);
/*      */       } 
/*      */       
/* 4698 */       GlStateManager.setActiveTexture(33984);
/*      */       
/* 4700 */       for (int l = 0; l < usedColorBuffers; l++)
/*      */       {
/* 4702 */         EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + l, 3553, dfbColorTexturesFlip.getB(l), 0);
/*      */       }
/*      */       
/* 4705 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, dfbDepthTextures.get(0), 0);
/* 4706 */       GL20.glDrawBuffers(dfbDrawBuffers);
/* 4707 */       checkGLError("pre-composite");
/*      */       
/* 4709 */       for (int i1 = 0; i1 < ps.length; i1++) {
/*      */         
/* 4711 */         Program program = ps[i1];
/*      */         
/* 4713 */         if (program.getId() != 0) {
/*      */           
/* 4715 */           useProgram(program);
/* 4716 */           checkGLError(program.getName());
/*      */           
/* 4718 */           if (activeCompositeMipmapSetting != 0)
/*      */           {
/* 4720 */             genCompositeMipmap();
/*      */           }
/*      */           
/* 4723 */           preDrawComposite();
/* 4724 */           drawComposite();
/* 4725 */           postDrawComposite();
/*      */           
/* 4727 */           for (int j = 0; j < usedColorBuffers; j++) {
/*      */             
/* 4729 */             if (program.getToggleColorTextures()[j]) {
/*      */               
/* 4731 */               dfbColorTexturesFlip.flip(j);
/* 4732 */               GlStateManager.setActiveTexture(33984 + colorTextureImageUnit[j]);
/* 4733 */               GlStateManager.bindTexture(dfbColorTexturesFlip.getA(j));
/* 4734 */               EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + j, 3553, dfbColorTexturesFlip.getB(j), 0);
/*      */             } 
/*      */           } 
/*      */           
/* 4738 */           GlStateManager.setActiveTexture(33984);
/*      */         } 
/*      */       } 
/*      */       
/* 4742 */       checkGLError("composite");
/*      */       
/* 4744 */       if (renderFinal) {
/*      */         
/* 4746 */         renderFinal();
/* 4747 */         isCompositeRendered = true;
/*      */       } 
/*      */       
/* 4750 */       GlStateManager.enableLighting();
/* 4751 */       GlStateManager.enableTexture2D();
/* 4752 */       GlStateManager.enableAlpha();
/* 4753 */       GlStateManager.enableBlend();
/* 4754 */       GlStateManager.depthFunc(515);
/* 4755 */       GlStateManager.depthMask(true);
/* 4756 */       GL11.glPopMatrix();
/* 4757 */       GL11.glMatrixMode(5888);
/* 4758 */       GL11.glPopMatrix();
/* 4759 */       useProgram(ProgramNone);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void preDrawComposite() {
/* 4765 */     RenderScale renderscale = activeProgram.getRenderScale();
/*      */     
/* 4767 */     if (renderscale != null) {
/*      */       
/* 4769 */       int i = (int)(renderWidth * renderscale.getOffsetX());
/* 4770 */       int j = (int)(renderHeight * renderscale.getOffsetY());
/* 4771 */       int k = (int)(renderWidth * renderscale.getScale());
/* 4772 */       int l = (int)(renderHeight * renderscale.getScale());
/* 4773 */       GL11.glViewport(i, j, k, l);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void postDrawComposite() {
/* 4779 */     RenderScale renderscale = activeProgram.getRenderScale();
/*      */     
/* 4781 */     if (renderscale != null)
/*      */     {
/* 4783 */       GL11.glViewport(0, 0, renderWidth, renderHeight);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void renderFinal() {
/* 4789 */     isRenderingDfb = false;
/* 4790 */     mc.getFramebuffer().bindFramebuffer(true);
/* 4791 */     OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_COLOR_ATTACHMENT0, 3553, (mc.getFramebuffer()).framebufferTexture, 0);
/* 4792 */     GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
/*      */     
/* 4794 */     if (EntityRenderer.anaglyphEnable) {
/*      */       
/* 4796 */       boolean flag = (EntityRenderer.anaglyphField != 0);
/* 4797 */       GlStateManager.colorMask(flag, !flag, !flag, true);
/*      */     } 
/*      */     
/* 4800 */     GlStateManager.depthMask(true);
/* 4801 */     GL11.glClearColor(clearColorR, clearColorG, clearColorB, 1.0F);
/* 4802 */     GL11.glClear(16640);
/* 4803 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 4804 */     GlStateManager.enableTexture2D();
/* 4805 */     GlStateManager.disableAlpha();
/* 4806 */     GlStateManager.disableBlend();
/* 4807 */     GlStateManager.enableDepth();
/* 4808 */     GlStateManager.depthFunc(519);
/* 4809 */     GlStateManager.depthMask(false);
/* 4810 */     checkGLError("pre-final");
/* 4811 */     useProgram(ProgramFinal);
/* 4812 */     checkGLError("final");
/*      */     
/* 4814 */     if (activeCompositeMipmapSetting != 0)
/*      */     {
/* 4816 */       genCompositeMipmap();
/*      */     }
/*      */     
/* 4819 */     drawComposite();
/* 4820 */     checkGLError("renderCompositeFinal");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endRender() {
/* 4825 */     if (isShadowPass) {
/*      */       
/* 4827 */       checkGLError("shadow endRender");
/*      */     }
/*      */     else {
/*      */       
/* 4831 */       if (!isCompositeRendered)
/*      */       {
/* 4833 */         renderCompositeFinal();
/*      */       }
/*      */       
/* 4836 */       isRenderingWorld = false;
/* 4837 */       GlStateManager.colorMask(true, true, true, true);
/* 4838 */       useProgram(ProgramNone);
/* 4839 */       RenderHelper.disableStandardItemLighting();
/* 4840 */       checkGLError("endRender end");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginSky() {
/* 4846 */     isRenderingSky = true;
/* 4847 */     fogEnabled = true;
/* 4848 */     setDrawBuffers(dfbDrawBuffers);
/* 4849 */     useProgram(ProgramSkyTextured);
/* 4850 */     pushEntity(-2, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setSkyColor(Vec3 v3color) {
/* 4855 */     skyColorR = (float)v3color.xCoord;
/* 4856 */     skyColorG = (float)v3color.yCoord;
/* 4857 */     skyColorB = (float)v3color.zCoord;
/* 4858 */     setProgramUniform3f(uniform_skyColor, skyColorR, skyColorG, skyColorB);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawHorizon() {
/* 4863 */     WorldRenderer worldrenderer = Tessellator.getInstance().getWorldRenderer();
/* 4864 */     float f = (mc.gameSettings.renderDistanceChunks << 4);
/* 4865 */     double d0 = f * 0.9238D;
/* 4866 */     double d1 = f * 0.3826D;
/* 4867 */     double d2 = -d1;
/* 4868 */     double d3 = -d0;
/* 4869 */     double d4 = 16.0D;
/* 4870 */     double d5 = -cameraPositionY;
/* 4871 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION);
/* 4872 */     worldrenderer.pos(d2, d5, d3).endVertex();
/* 4873 */     worldrenderer.pos(d2, d4, d3).endVertex();
/* 4874 */     worldrenderer.pos(d3, d4, d2).endVertex();
/* 4875 */     worldrenderer.pos(d3, d5, d2).endVertex();
/* 4876 */     worldrenderer.pos(d3, d5, d2).endVertex();
/* 4877 */     worldrenderer.pos(d3, d4, d2).endVertex();
/* 4878 */     worldrenderer.pos(d3, d4, d1).endVertex();
/* 4879 */     worldrenderer.pos(d3, d5, d1).endVertex();
/* 4880 */     worldrenderer.pos(d3, d5, d1).endVertex();
/* 4881 */     worldrenderer.pos(d3, d4, d1).endVertex();
/* 4882 */     worldrenderer.pos(d2, d4, d0).endVertex();
/* 4883 */     worldrenderer.pos(d2, d5, d0).endVertex();
/* 4884 */     worldrenderer.pos(d2, d5, d0).endVertex();
/* 4885 */     worldrenderer.pos(d2, d4, d0).endVertex();
/* 4886 */     worldrenderer.pos(d1, d4, d0).endVertex();
/* 4887 */     worldrenderer.pos(d1, d5, d0).endVertex();
/* 4888 */     worldrenderer.pos(d1, d5, d0).endVertex();
/* 4889 */     worldrenderer.pos(d1, d4, d0).endVertex();
/* 4890 */     worldrenderer.pos(d0, d4, d1).endVertex();
/* 4891 */     worldrenderer.pos(d0, d5, d1).endVertex();
/* 4892 */     worldrenderer.pos(d0, d5, d1).endVertex();
/* 4893 */     worldrenderer.pos(d0, d4, d1).endVertex();
/* 4894 */     worldrenderer.pos(d0, d4, d2).endVertex();
/* 4895 */     worldrenderer.pos(d0, d5, d2).endVertex();
/* 4896 */     worldrenderer.pos(d0, d5, d2).endVertex();
/* 4897 */     worldrenderer.pos(d0, d4, d2).endVertex();
/* 4898 */     worldrenderer.pos(d1, d4, d3).endVertex();
/* 4899 */     worldrenderer.pos(d1, d5, d3).endVertex();
/* 4900 */     worldrenderer.pos(d1, d5, d3).endVertex();
/* 4901 */     worldrenderer.pos(d1, d4, d3).endVertex();
/* 4902 */     worldrenderer.pos(d2, d4, d3).endVertex();
/* 4903 */     worldrenderer.pos(d2, d5, d3).endVertex();
/* 4904 */     worldrenderer.pos(d3, d5, d3).endVertex();
/* 4905 */     worldrenderer.pos(d3, d5, d0).endVertex();
/* 4906 */     worldrenderer.pos(d0, d5, d0).endVertex();
/* 4907 */     worldrenderer.pos(d0, d5, d3).endVertex();
/* 4908 */     Tessellator.getInstance().draw();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void preSkyList() {
/* 4913 */     setUpPosition();
/* 4914 */     GL11.glColor3f(fogColorR, fogColorG, fogColorB);
/* 4915 */     drawHorizon();
/* 4916 */     GL11.glColor3f(skyColorR, skyColorG, skyColorB);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endSky() {
/* 4921 */     isRenderingSky = false;
/* 4922 */     setDrawBuffers(dfbDrawBuffers);
/* 4923 */     useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
/* 4924 */     popEntity();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginUpdateChunks() {
/* 4929 */     checkGLError("beginUpdateChunks1");
/* 4930 */     checkFramebufferStatus("beginUpdateChunks1");
/*      */     
/* 4932 */     if (!isShadowPass)
/*      */     {
/* 4934 */       useProgram(ProgramTerrain);
/*      */     }
/*      */     
/* 4937 */     checkGLError("beginUpdateChunks2");
/* 4938 */     checkFramebufferStatus("beginUpdateChunks2");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endUpdateChunks() {
/* 4943 */     checkGLError("endUpdateChunks1");
/* 4944 */     checkFramebufferStatus("endUpdateChunks1");
/*      */     
/* 4946 */     if (!isShadowPass)
/*      */     {
/* 4948 */       useProgram(ProgramTerrain);
/*      */     }
/*      */     
/* 4951 */     checkGLError("endUpdateChunks2");
/* 4952 */     checkFramebufferStatus("endUpdateChunks2");
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean shouldRenderClouds(GameSettings gs) {
/* 4957 */     if (!shaderPackLoaded)
/*      */     {
/* 4959 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 4963 */     checkGLError("shouldRenderClouds");
/* 4964 */     return isShadowPass ? configCloudShadow : ((gs.clouds > 0));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void beginClouds() {
/* 4970 */     fogEnabled = true;
/* 4971 */     pushEntity(-3, 0);
/* 4972 */     useProgram(ProgramClouds);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endClouds() {
/* 4977 */     disableFog();
/* 4978 */     popEntity();
/* 4979 */     useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginEntities() {
/* 4984 */     if (isRenderingWorld)
/*      */     {
/* 4986 */       useProgram(ProgramEntities);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void nextEntity(Entity entity) {
/* 4992 */     if (isRenderingWorld) {
/*      */       
/* 4994 */       useProgram(ProgramEntities);
/* 4995 */       setEntityId(entity);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setEntityId(Entity entity) {
/* 5001 */     if (uniform_entityId.isDefined()) {
/*      */       
/* 5003 */       int i = EntityUtils.getEntityIdByClass(entity);
/* 5004 */       int j = EntityAliases.getEntityAliasId(i);
/*      */       
/* 5006 */       if (j >= 0)
/*      */       {
/* 5008 */         i = j;
/*      */       }
/*      */       
/* 5011 */       uniform_entityId.setValue(i);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginSpiderEyes() {
/* 5017 */     if (isRenderingWorld && ProgramSpiderEyes.getId() != ProgramNone.getId()) {
/*      */       
/* 5019 */       useProgram(ProgramSpiderEyes);
/* 5020 */       GlStateManager.enableAlpha();
/* 5021 */       GlStateManager.alphaFunc(516, 0.0F);
/* 5022 */       GlStateManager.blendFunc(770, 771);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endSpiderEyes() {
/* 5028 */     if (isRenderingWorld && ProgramSpiderEyes.getId() != ProgramNone.getId()) {
/*      */       
/* 5030 */       useProgram(ProgramEntities);
/* 5031 */       GlStateManager.disableAlpha();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endEntities() {
/* 5037 */     if (isRenderingWorld) {
/*      */       
/* 5039 */       setEntityId((Entity)null);
/* 5040 */       useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginEntitiesGlowing() {
/* 5046 */     if (isRenderingWorld)
/*      */     {
/* 5048 */       isEntitiesGlowing = true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endEntitiesGlowing() {
/* 5054 */     if (isRenderingWorld)
/*      */     {
/* 5056 */       isEntitiesGlowing = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setEntityColor(float r, float g, float b, float a) {
/* 5062 */     if (isRenderingWorld && !isShadowPass)
/*      */     {
/* 5064 */       uniform_entityColor.setValue(r, g, b, a);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginLivingDamage() {
/* 5070 */     if (isRenderingWorld) {
/*      */       
/* 5072 */       ShadersTex.bindTexture(defaultTexture);
/*      */       
/* 5074 */       if (!isShadowPass)
/*      */       {
/* 5076 */         setDrawBuffers(drawBuffersColorAtt0);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endLivingDamage() {
/* 5083 */     if (isRenderingWorld && !isShadowPass)
/*      */     {
/* 5085 */       setDrawBuffers(ProgramEntities.getDrawBuffers());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginBlockEntities() {
/* 5091 */     if (isRenderingWorld) {
/*      */       
/* 5093 */       checkGLError("beginBlockEntities");
/* 5094 */       useProgram(ProgramBlock);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void nextBlockEntity(TileEntity tileEntity) {
/* 5100 */     if (isRenderingWorld) {
/*      */       
/* 5102 */       checkGLError("nextBlockEntity");
/* 5103 */       useProgram(ProgramBlock);
/* 5104 */       setBlockEntityId(tileEntity);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setBlockEntityId(TileEntity tileEntity) {
/* 5110 */     if (uniform_blockEntityId.isDefined()) {
/*      */       
/* 5112 */       int i = getBlockEntityId(tileEntity);
/* 5113 */       uniform_blockEntityId.setValue(i);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getBlockEntityId(TileEntity tileEntity) {
/* 5119 */     if (tileEntity == null)
/*      */     {
/* 5121 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 5125 */     Block block = tileEntity.getBlockType();
/*      */     
/* 5127 */     if (block == null)
/*      */     {
/* 5129 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 5133 */     int i = Block.getIdFromBlock(block);
/* 5134 */     int j = tileEntity.getBlockMetadata();
/* 5135 */     int k = BlockAliases.getBlockAliasId(i, j);
/*      */     
/* 5137 */     if (k >= 0)
/*      */     {
/* 5139 */       i = k;
/*      */     }
/*      */     
/* 5142 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void endBlockEntities() {
/* 5149 */     if (isRenderingWorld) {
/*      */       
/* 5151 */       checkGLError("endBlockEntities");
/* 5152 */       setBlockEntityId((TileEntity)null);
/* 5153 */       useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
/* 5154 */       ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginLitParticles() {
/* 5160 */     useProgram(ProgramTexturedLit);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginParticles() {
/* 5165 */     useProgram(ProgramTextured);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endParticles() {
/* 5170 */     useProgram(ProgramTexturedLit);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void readCenterDepth() {
/* 5175 */     if (!isShadowPass && centerDepthSmoothEnabled) {
/*      */       
/* 5177 */       tempDirectFloatBuffer.clear();
/* 5178 */       GL11.glReadPixels(renderWidth / 2, renderHeight / 2, 1, 1, 6402, 5126, tempDirectFloatBuffer);
/* 5179 */       centerDepth = tempDirectFloatBuffer.get(0);
/* 5180 */       float f = (float)diffSystemTime * 0.01F;
/* 5181 */       float f1 = (float)Math.exp(Math.log(0.5D) * f / centerDepthSmoothHalflife);
/* 5182 */       centerDepthSmooth = centerDepthSmooth * f1 + centerDepth * (1.0F - f1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginWeather() {
/* 5188 */     if (!isShadowPass) {
/*      */       
/* 5190 */       if (usedDepthBuffers >= 3) {
/*      */         
/* 5192 */         GlStateManager.setActiveTexture(33996);
/* 5193 */         GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, renderWidth, renderHeight);
/* 5194 */         GlStateManager.setActiveTexture(33984);
/*      */       } 
/*      */       
/* 5197 */       GlStateManager.enableDepth();
/* 5198 */       GlStateManager.enableBlend();
/* 5199 */       GlStateManager.blendFunc(770, 771);
/* 5200 */       GlStateManager.enableAlpha();
/* 5201 */       useProgram(ProgramWeather);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endWeather() {
/* 5207 */     GlStateManager.disableBlend();
/* 5208 */     useProgram(ProgramTexturedLit);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void preWater() {
/* 5213 */     if (usedDepthBuffers >= 2) {
/*      */       
/* 5215 */       GlStateManager.setActiveTexture(33995);
/* 5216 */       checkGLError("pre copy depth");
/* 5217 */       GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, renderWidth, renderHeight);
/* 5218 */       checkGLError("copy depth");
/* 5219 */       GlStateManager.setActiveTexture(33984);
/*      */     } 
/*      */     
/* 5222 */     ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginWater() {
/* 5227 */     if (isRenderingWorld)
/*      */     {
/* 5229 */       if (!isShadowPass) {
/*      */         
/* 5231 */         renderDeferred();
/* 5232 */         useProgram(ProgramWater);
/* 5233 */         GlStateManager.enableBlend();
/* 5234 */         GlStateManager.depthMask(true);
/*      */       }
/*      */       else {
/*      */         
/* 5238 */         GlStateManager.depthMask(true);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endWater() {
/* 5245 */     if (isRenderingWorld) {
/*      */       
/* 5247 */       if (isShadowPass);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5252 */       useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void applyHandDepth() {
/* 5258 */     if (configHandDepthMul != 1.0D)
/*      */     {
/* 5260 */       GL11.glScaled(1.0D, 1.0D, configHandDepthMul);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginHand(boolean translucent) {
/* 5266 */     GL11.glMatrixMode(5888);
/* 5267 */     GL11.glPushMatrix();
/* 5268 */     GL11.glMatrixMode(5889);
/* 5269 */     GL11.glPushMatrix();
/* 5270 */     GL11.glMatrixMode(5888);
/*      */     
/* 5272 */     if (translucent) {
/*      */       
/* 5274 */       useProgram(ProgramHandWater);
/*      */     }
/*      */     else {
/*      */       
/* 5278 */       useProgram(ProgramHand);
/*      */     } 
/*      */     
/* 5281 */     checkGLError("beginHand");
/* 5282 */     checkFramebufferStatus("beginHand");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endHand() {
/* 5287 */     checkGLError("pre endHand");
/* 5288 */     checkFramebufferStatus("pre endHand");
/* 5289 */     GL11.glMatrixMode(5889);
/* 5290 */     GL11.glPopMatrix();
/* 5291 */     GL11.glMatrixMode(5888);
/* 5292 */     GL11.glPopMatrix();
/* 5293 */     GlStateManager.blendFunc(770, 771);
/* 5294 */     checkGLError("endHand");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginFPOverlay() {
/* 5299 */     GlStateManager.disableLighting();
/* 5300 */     GlStateManager.disableBlend();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void endFPOverlay() {}
/*      */ 
/*      */   
/*      */   public static void glEnableWrapper(int cap) {
/* 5309 */     GL11.glEnable(cap);
/*      */     
/* 5311 */     if (cap == 3553) {
/*      */       
/* 5313 */       enableTexture2D();
/*      */     }
/* 5315 */     else if (cap == 2912) {
/*      */       
/* 5317 */       enableFog();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glDisableWrapper(int cap) {
/* 5323 */     GL11.glDisable(cap);
/*      */     
/* 5325 */     if (cap == 3553) {
/*      */       
/* 5327 */       disableTexture2D();
/*      */     }
/* 5329 */     else if (cap == 2912) {
/*      */       
/* 5331 */       disableFog();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void sglEnableT2D(int cap) {
/* 5337 */     GL11.glEnable(cap);
/* 5338 */     enableTexture2D();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void sglDisableT2D(int cap) {
/* 5343 */     GL11.glDisable(cap);
/* 5344 */     disableTexture2D();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void sglEnableFog(int cap) {
/* 5349 */     GL11.glEnable(cap);
/* 5350 */     enableFog();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void sglDisableFog(int cap) {
/* 5355 */     GL11.glDisable(cap);
/* 5356 */     disableFog();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableTexture2D() {
/* 5361 */     if (isRenderingSky) {
/*      */       
/* 5363 */       useProgram(ProgramSkyTextured);
/*      */     }
/* 5365 */     else if (activeProgram == ProgramBasic) {
/*      */       
/* 5367 */       useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableTexture2D() {
/* 5373 */     if (isRenderingSky) {
/*      */       
/* 5375 */       useProgram(ProgramSkyBasic);
/*      */     }
/* 5377 */     else if (activeProgram == ProgramTextured || activeProgram == ProgramTexturedLit) {
/*      */       
/* 5379 */       useProgram(ProgramBasic);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void pushProgram() {
/* 5385 */     programStack.push(activeProgram);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void popProgram() {
/* 5390 */     Program program = programStack.pop();
/* 5391 */     useProgram(program);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginLeash() {
/* 5396 */     pushProgram();
/* 5397 */     useProgram(ProgramBasic);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endLeash() {
/* 5402 */     popProgram();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableFog() {
/* 5407 */     fogEnabled = true;
/* 5408 */     setProgramUniform1i(uniform_fogMode, fogMode);
/* 5409 */     setProgramUniform1f(uniform_fogDensity, fogDensity);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableFog() {
/* 5414 */     fogEnabled = false;
/* 5415 */     setProgramUniform1i(uniform_fogMode, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFogDensity(float value) {
/* 5420 */     fogDensity = value;
/*      */     
/* 5422 */     if (fogEnabled)
/*      */     {
/* 5424 */       setProgramUniform1f(uniform_fogDensity, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void sglFogi(int pname, int param) {
/* 5430 */     GL11.glFogi(pname, param);
/*      */     
/* 5432 */     if (pname == 2917) {
/*      */       
/* 5434 */       fogMode = param;
/*      */       
/* 5436 */       if (fogEnabled)
/*      */       {
/* 5438 */         setProgramUniform1i(uniform_fogMode, fogMode);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableLightmap() {
/* 5445 */     lightmapEnabled = true;
/*      */     
/* 5447 */     if (activeProgram == ProgramTextured)
/*      */     {
/* 5449 */       useProgram(ProgramTexturedLit);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableLightmap() {
/* 5455 */     lightmapEnabled = false;
/*      */     
/* 5457 */     if (activeProgram == ProgramTexturedLit)
/*      */     {
/* 5459 */       useProgram(ProgramTextured);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getEntityData() {
/* 5465 */     return entityData[entityDataIndex << 1];
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getEntityData2() {
/* 5470 */     return entityData[(entityDataIndex << 1) + 1];
/*      */   }
/*      */ 
/*      */   
/*      */   public static int setEntityData1(int data1) {
/* 5475 */     entityData[entityDataIndex << 1] = entityData[entityDataIndex << 1] & 0xFFFF | data1 << 16;
/* 5476 */     return data1;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int setEntityData2(int data2) {
/* 5481 */     entityData[(entityDataIndex << 1) + 1] = entityData[(entityDataIndex << 1) + 1] & 0xFFFF0000 | data2 & 0xFFFF;
/* 5482 */     return data2;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void pushEntity(int data0, int data1) {
/* 5487 */     entityDataIndex++;
/* 5488 */     entityData[entityDataIndex << 1] = data0 & 0xFFFF | data1 << 16;
/* 5489 */     entityData[(entityDataIndex << 1) + 1] = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void pushEntity(int data0) {
/* 5494 */     entityDataIndex++;
/* 5495 */     entityData[entityDataIndex << 1] = data0 & 0xFFFF;
/* 5496 */     entityData[(entityDataIndex << 1) + 1] = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void pushEntity(Block block) {
/* 5501 */     entityDataIndex++;
/* 5502 */     int i = block.getRenderType();
/* 5503 */     entityData[entityDataIndex << 1] = Block.blockRegistry.getIDForObject(block) & 0xFFFF | i << 16;
/* 5504 */     entityData[(entityDataIndex << 1) + 1] = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void popEntity() {
/* 5509 */     entityData[entityDataIndex << 1] = 0;
/* 5510 */     entityData[(entityDataIndex << 1) + 1] = 0;
/* 5511 */     entityDataIndex--;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void mcProfilerEndSection() {
/* 5516 */     mc.mcProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getShaderPackName() {
/* 5521 */     return (shaderPack == null) ? null : ((shaderPack instanceof ShaderPackNone) ? null : shaderPack.getName());
/*      */   }
/*      */ 
/*      */   
/*      */   public static InputStream getShaderPackResourceStream(String path) {
/* 5526 */     return (shaderPack == null) ? null : shaderPack.getResourceAsStream(path);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void nextAntialiasingLevel(boolean forward) {
/* 5531 */     if (forward) {
/*      */       
/* 5533 */       configAntialiasingLevel += 2;
/*      */       
/* 5535 */       if (configAntialiasingLevel > 4)
/*      */       {
/* 5537 */         configAntialiasingLevel = 0;
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 5542 */       configAntialiasingLevel -= 2;
/*      */       
/* 5544 */       if (configAntialiasingLevel < 0)
/*      */       {
/* 5546 */         configAntialiasingLevel = 4;
/*      */       }
/*      */     } 
/*      */     
/* 5550 */     configAntialiasingLevel = configAntialiasingLevel / 2 << 1;
/* 5551 */     configAntialiasingLevel = Config.limit(configAntialiasingLevel, 0, 4);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void checkShadersModInstalled() {
/*      */     try {
/* 5558 */       Class<?> clazz = Class.forName("shadersmod.transform.SMCClassTransformer");
/*      */     }
/* 5560 */     catch (Throwable var1) {
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 5565 */     throw new RuntimeException("Shaders Mod detected. Please remove it, OptiFine has built-in support for shaders.");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void resourcesReloaded() {
/* 5570 */     loadShaderPackResources();
/*      */     
/* 5572 */     if (shaderPackLoaded) {
/*      */       
/* 5574 */       BlockAliases.resourcesReloaded();
/* 5575 */       ItemAliases.resourcesReloaded();
/* 5576 */       EntityAliases.resourcesReloaded();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void loadShaderPackResources() {
/* 5582 */     shaderPackResources = new HashMap<>();
/*      */     
/* 5584 */     if (shaderPackLoaded) {
/*      */       
/* 5586 */       List<String> list = new ArrayList<>();
/* 5587 */       String s = "/shaders/lang/";
/* 5588 */       String s1 = "en_US";
/* 5589 */       String s2 = ".lang";
/* 5590 */       list.add(s + s1 + s2);
/*      */       
/* 5592 */       if (!(Config.getGameSettings()).language.equals(s1))
/*      */       {
/* 5594 */         list.add(s + (Config.getGameSettings()).language + s2);
/*      */       }
/*      */ 
/*      */       
/*      */       try {
/* 5599 */         for (String s3 : list) {
/*      */           
/* 5601 */           InputStream inputstream = shaderPack.getResourceAsStream(s3);
/*      */           
/* 5603 */           if (inputstream != null) {
/*      */             
/* 5605 */             PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 5606 */             Lang.loadLocaleData(inputstream, (Map)propertiesOrdered);
/* 5607 */             inputstream.close();
/*      */             
/* 5609 */             for (Object e : propertiesOrdered.keySet())
/*      */             {
/* 5611 */               String s4 = (String)e;
/* 5612 */               String s5 = propertiesOrdered.getProperty(s4);
/* 5613 */               shaderPackResources.put(s4, s5);
/*      */             }
/*      */           
/*      */           } 
/*      */         } 
/* 5618 */       } catch (IOException ioexception) {
/*      */         
/* 5620 */         ioexception.printStackTrace();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static String translate(String key, String def) {
/* 5627 */     String s = shaderPackResources.get(key);
/* 5628 */     return (s == null) ? def : s;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isProgramPath(String path) {
/* 5633 */     if (path == null)
/*      */     {
/* 5635 */       return false;
/*      */     }
/* 5637 */     if (path.length() <= 0)
/*      */     {
/* 5639 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 5643 */     int i = path.lastIndexOf('/');
/*      */     
/* 5645 */     if (i >= 0)
/*      */     {
/* 5647 */       path = path.substring(i + 1);
/*      */     }
/*      */     
/* 5650 */     Program program = getProgram(path);
/* 5651 */     return (program != null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Program getProgram(String name) {
/* 5657 */     return programs.getProgram(name);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setItemToRenderMain(ItemStack itemToRenderMain) {
/* 5662 */     itemToRenderMainTranslucent = isTranslucentBlock(itemToRenderMain);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setItemToRenderOff(ItemStack itemToRenderOff) {
/* 5667 */     itemToRenderOffTranslucent = isTranslucentBlock(itemToRenderOff);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isItemToRenderMainTranslucent() {
/* 5672 */     return itemToRenderMainTranslucent;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isItemToRenderOffTranslucent() {
/* 5677 */     return itemToRenderOffTranslucent;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isBothHandsRendered() {
/* 5682 */     return (isHandRenderedMain && isHandRenderedOff);
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isTranslucentBlock(ItemStack stack) {
/* 5687 */     if (stack == null)
/*      */     {
/* 5689 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 5693 */     Item item = stack.getItem();
/*      */     
/* 5695 */     if (item == null)
/*      */     {
/* 5697 */       return false;
/*      */     }
/* 5699 */     if (!(item instanceof ItemBlock))
/*      */     {
/* 5701 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 5705 */     ItemBlock itemblock = (ItemBlock)item;
/* 5706 */     Block block = itemblock.getBlock();
/*      */     
/* 5708 */     if (block == null)
/*      */     {
/* 5710 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 5714 */     EnumWorldBlockLayer enumworldblocklayer = block.getBlockLayer();
/* 5715 */     return (enumworldblocklayer == EnumWorldBlockLayer.TRANSLUCENT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSkipRenderHand() {
/* 5723 */     return skipRenderHandMain;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRenderBothHands() {
/* 5728 */     return (!skipRenderHandMain && !skipRenderHandOff);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setSkipRenderHands(boolean skipMain, boolean skipOff) {
/* 5733 */     skipRenderHandMain = skipMain;
/* 5734 */     skipRenderHandOff = skipOff;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setHandsRendered(boolean handMain, boolean handOff) {
/* 5739 */     isHandRenderedMain = handMain;
/* 5740 */     isHandRenderedOff = handOff;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isHandRenderedMain() {
/* 5745 */     return isHandRenderedMain;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isHandRenderedOff() {
/* 5750 */     return isHandRenderedOff;
/*      */   }
/*      */ 
/*      */   
/*      */   public static float getShadowRenderDistance() {
/* 5755 */     return (shadowDistanceRenderMul < 0.0F) ? -1.0F : (shadowMapHalfPlane * shadowDistanceRenderMul);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setRenderingFirstPersonHand(boolean flag) {
/* 5760 */     isRenderingFirstPersonHand = flag;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRenderingFirstPersonHand() {
/* 5765 */     return isRenderingFirstPersonHand;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginBeacon() {
/* 5770 */     if (isRenderingWorld)
/*      */     {
/* 5772 */       useProgram(ProgramBeaconBeam);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endBeacon() {
/* 5778 */     if (isRenderingWorld)
/*      */     {
/* 5780 */       useProgram(ProgramBlock);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static World getCurrentWorld() {
/* 5786 */     return currentWorld;
/*      */   }
/*      */ 
/*      */   
/*      */   public static BlockPos getCameraPosition() {
/* 5791 */     return new BlockPos(cameraPositionX, cameraPositionY, cameraPositionZ);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isCustomUniforms() {
/* 5796 */     return (customUniforms != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean canRenderQuads() {
/* 5801 */     return hasGeometryShaders ? capabilities.GL_NV_geometry_shader4 : true;
/*      */   }
/*      */ 
/*      */   
/*      */   static {
/* 5806 */     shaderPacksDir = new File((Minecraft.getMinecraft()).mcDataDir, "shaderpacks");
/* 5807 */     configFile = new File((Minecraft.getMinecraft()).mcDataDir, "optionsshaders.txt");
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\Shaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */