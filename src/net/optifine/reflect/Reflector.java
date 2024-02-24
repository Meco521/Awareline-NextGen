/*     */ package net.optifine.reflect;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Map;
/*     */ import javax.vecmath.Matrix4f;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiEnchantment;
/*     */ import net.minecraft.client.gui.inventory.GuiFurnace;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelChest;
/*     */ import net.minecraft.client.model.ModelDragon;
/*     */ import net.minecraft.client.model.ModelOcelot;
/*     */ import net.minecraft.client.model.ModelRabbit;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.model.ModelSilverfish;
/*     */ import net.minecraft.client.model.ModelSkeletonHead;
/*     */ import net.minecraft.client.model.ModelSquid;
/*     */ import net.minecraft.client.model.ModelWither;
/*     */ import net.minecraft.client.multiplayer.ChunkProviderClient;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.client.renderer.entity.RenderBoat;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.entity.RenderMinecart;
/*     */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityEnchantmentTableRenderer;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityEnderChestRenderer;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*     */ import net.minecraft.client.resources.DefaultResourcePack;
/*     */ import net.minecraft.client.resources.model.ModelRotation;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryBasic;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBrewingStand;
/*     */ import net.minecraft.tileentity.TileEntityFurnace;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.optifine.Log;
/*     */ import net.optifine.util.ArrayUtils;
/*     */ 
/*     */ public class Reflector {
/*  54 */   public static ReflectorClass BetterFoliageClient = new ReflectorClass("mods.betterfoliage.client.BetterFoliageClient");
/*  55 */   public static final ReflectorClass BlamingTransformer = new ReflectorClass("net.minecraftforge.fml.common.asm.transformers.BlamingTransformer");
/*  56 */   public static ReflectorMethod BlamingTransformer_onCrash = new ReflectorMethod(BlamingTransformer, "onCrash");
/*  57 */   public static final ReflectorClass CoreModManager = new ReflectorClass("net.minecraftforge.fml.relauncher.CoreModManager");
/*  58 */   public static ReflectorMethod CoreModManager_onCrash = new ReflectorMethod(CoreModManager, "onCrash");
/*  59 */   public static final ReflectorClass DimensionManager = new ReflectorClass("net.minecraftforge.common.DimensionManager");
/*  60 */   public static ReflectorMethod DimensionManager_getStaticDimensionIDs = new ReflectorMethod(DimensionManager, "getStaticDimensionIDs");
/*  61 */   public static final ReflectorClass EntityViewRenderEvent_CameraSetup = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$CameraSetup");
/*  62 */   public static ReflectorConstructor EntityViewRenderEvent_CameraSetup_Constructor = new ReflectorConstructor(EntityViewRenderEvent_CameraSetup, new Class[] { EntityRenderer.class, Entity.class, Block.class, double.class, float.class, float.class, float.class });
/*  63 */   public static ReflectorField EntityViewRenderEvent_CameraSetup_yaw = new ReflectorField(EntityViewRenderEvent_CameraSetup, "yaw");
/*  64 */   public static ReflectorField EntityViewRenderEvent_CameraSetup_pitch = new ReflectorField(EntityViewRenderEvent_CameraSetup, "pitch");
/*  65 */   public static ReflectorField EntityViewRenderEvent_CameraSetup_roll = new ReflectorField(EntityViewRenderEvent_CameraSetup, "roll");
/*  66 */   public static final ReflectorClass EntityViewRenderEvent_FogColors = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$FogColors");
/*  67 */   public static ReflectorConstructor EntityViewRenderEvent_FogColors_Constructor = new ReflectorConstructor(EntityViewRenderEvent_FogColors, new Class[] { EntityRenderer.class, Entity.class, Block.class, double.class, float.class, float.class, float.class });
/*  68 */   public static ReflectorField EntityViewRenderEvent_FogColors_red = new ReflectorField(EntityViewRenderEvent_FogColors, "red");
/*  69 */   public static ReflectorField EntityViewRenderEvent_FogColors_green = new ReflectorField(EntityViewRenderEvent_FogColors, "green");
/*  70 */   public static ReflectorField EntityViewRenderEvent_FogColors_blue = new ReflectorField(EntityViewRenderEvent_FogColors, "blue");
/*  71 */   public static ReflectorClass Event = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.Event");
/*  72 */   public static final ReflectorClass EventBus = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.EventBus");
/*  73 */   public static final ReflectorMethod EventBus_post = new ReflectorMethod(EventBus, "post");
/*  74 */   public static final ReflectorClass Event_Result = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.Event$Result");
/*  75 */   public static ReflectorField Event_Result_DENY = new ReflectorField(Event_Result, "DENY");
/*  76 */   public static ReflectorField Event_Result_ALLOW = new ReflectorField(Event_Result, "ALLOW");
/*  77 */   public static ReflectorField Event_Result_DEFAULT = new ReflectorField(Event_Result, "DEFAULT");
/*  78 */   public static final ReflectorClass FMLClientHandler = new ReflectorClass("net.minecraftforge.fml.client.FMLClientHandler");
/*  79 */   public static ReflectorMethod FMLClientHandler_instance = new ReflectorMethod(FMLClientHandler, "instance");
/*  80 */   public static ReflectorMethod FMLClientHandler_handleLoadingScreen = new ReflectorMethod(FMLClientHandler, "handleLoadingScreen");
/*  81 */   public static ReflectorMethod FMLClientHandler_isLoading = new ReflectorMethod(FMLClientHandler, "isLoading");
/*  82 */   public static ReflectorMethod FMLClientHandler_trackBrokenTexture = new ReflectorMethod(FMLClientHandler, "trackBrokenTexture");
/*  83 */   public static ReflectorMethod FMLClientHandler_trackMissingTexture = new ReflectorMethod(FMLClientHandler, "trackMissingTexture");
/*  84 */   public static final ReflectorClass FMLCommonHandler = new ReflectorClass("net.minecraftforge.fml.common.FMLCommonHandler");
/*  85 */   public static ReflectorMethod FMLCommonHandler_enhanceCrashReport = new ReflectorMethod(FMLCommonHandler, "enhanceCrashReport");
/*  86 */   public static ReflectorMethod FMLCommonHandler_getBrandings = new ReflectorMethod(FMLCommonHandler, "getBrandings");
/*  87 */   public static ReflectorMethod FMLCommonHandler_handleServerAboutToStart = new ReflectorMethod(FMLCommonHandler, "handleServerAboutToStart");
/*  88 */   public static ReflectorMethod FMLCommonHandler_handleServerStarting = new ReflectorMethod(FMLCommonHandler, "handleServerStarting");
/*  89 */   public static ReflectorMethod FMLCommonHandler_instance = new ReflectorMethod(FMLCommonHandler, "instance");
/*  90 */   public static final ReflectorClass ForgeBiome = new ReflectorClass(BiomeGenBase.class);
/*  91 */   public static ReflectorMethod ForgeBiome_getWaterColorMultiplier = new ReflectorMethod(ForgeBiome, "getWaterColorMultiplier");
/*  92 */   public static final ReflectorClass ForgeBlock = new ReflectorClass(Block.class);
/*  93 */   public static ReflectorMethod ForgeBlock_addDestroyEffects = new ReflectorMethod(ForgeBlock, "addDestroyEffects");
/*  94 */   public static ReflectorMethod ForgeBlock_addHitEffects = new ReflectorMethod(ForgeBlock, "addHitEffects");
/*  95 */   public static ReflectorMethod ForgeBlock_canCreatureSpawn = new ReflectorMethod(ForgeBlock, "canCreatureSpawn");
/*  96 */   public static ReflectorMethod ForgeBlock_canRenderInLayer = new ReflectorMethod(ForgeBlock, "canRenderInLayer", new Class[] { EnumWorldBlockLayer.class });
/*  97 */   public static ReflectorMethod ForgeBlock_getBedDirection = new ReflectorMethod(ForgeBlock, "getBedDirection");
/*  98 */   public static ReflectorMethod ForgeBlock_getExtendedState = new ReflectorMethod(ForgeBlock, "getExtendedState");
/*  99 */   public static ReflectorMethod ForgeBlock_hasTileEntity = new ReflectorMethod(ForgeBlock, "hasTileEntity", new Class[] { IBlockState.class });
/* 100 */   public static ReflectorMethod ForgeBlock_isAir = new ReflectorMethod(ForgeBlock, "isAir");
/* 101 */   public static ReflectorMethod ForgeBlock_isBed = new ReflectorMethod(ForgeBlock, "isBed");
/* 102 */   public static final ReflectorClass ForgeChunkCache = new ReflectorClass(ChunkCache.class);
/* 103 */   public static ReflectorMethod ForgeChunkCache_isSideSolid = new ReflectorMethod(ForgeChunkCache, "isSideSolid");
/* 104 */   public static final ReflectorClass ForgeEntity = new ReflectorClass(Entity.class);
/* 105 */   public static ReflectorMethod ForgeEntity_canRiderInteract = new ReflectorMethod(ForgeEntity, "canRiderInteract");
/* 106 */   public static ReflectorMethod ForgeEntity_shouldRenderInPass = new ReflectorMethod(ForgeEntity, "shouldRenderInPass");
/* 107 */   public static ReflectorMethod ForgeEntity_shouldRiderSit = new ReflectorMethod(ForgeEntity, "shouldRiderSit");
/* 108 */   public static final ReflectorClass ForgeEventFactory = new ReflectorClass("net.minecraftforge.event.ForgeEventFactory");
/* 109 */   public static ReflectorMethod ForgeEventFactory_canEntityDespawn = new ReflectorMethod(ForgeEventFactory, "canEntityDespawn");
/* 110 */   public static ReflectorMethod ForgeEventFactory_canEntitySpawn = new ReflectorMethod(ForgeEventFactory, "canEntitySpawn");
/* 111 */   public static ReflectorMethod ForgeEventFactory_doSpecialSpawn = new ReflectorMethod(ForgeEventFactory, "doSpecialSpawn", new Class[] { EntityLiving.class, World.class, float.class, float.class, float.class });
/* 112 */   public static ReflectorMethod ForgeEventFactory_getMaxSpawnPackSize = new ReflectorMethod(ForgeEventFactory, "getMaxSpawnPackSize");
/* 113 */   public static ReflectorMethod ForgeEventFactory_renderBlockOverlay = new ReflectorMethod(ForgeEventFactory, "renderBlockOverlay");
/* 114 */   public static ReflectorMethod ForgeEventFactory_renderFireOverlay = new ReflectorMethod(ForgeEventFactory, "renderFireOverlay");
/* 115 */   public static ReflectorMethod ForgeEventFactory_renderWaterOverlay = new ReflectorMethod(ForgeEventFactory, "renderWaterOverlay");
/* 116 */   public static final ReflectorClass ForgeHooks = new ReflectorClass("net.minecraftforge.common.ForgeHooks");
/* 117 */   public static ReflectorMethod ForgeHooks_onLivingSetAttackTarget = new ReflectorMethod(ForgeHooks, "onLivingSetAttackTarget");
/* 118 */   public static final ReflectorClass ForgeHooksClient = new ReflectorClass("net.minecraftforge.client.ForgeHooksClient");
/* 119 */   public static ReflectorMethod ForgeHooksClient_applyTransform = new ReflectorMethod(ForgeHooksClient, "applyTransform", new Class[] { Matrix4f.class, Optional.class });
/* 120 */   public static ReflectorMethod ForgeHooksClient_dispatchRenderLast = new ReflectorMethod(ForgeHooksClient, "dispatchRenderLast");
/* 121 */   public static ReflectorMethod ForgeHooksClient_drawScreen = new ReflectorMethod(ForgeHooksClient, "drawScreen");
/* 122 */   public static ReflectorMethod ForgeHooksClient_fillNormal = new ReflectorMethod(ForgeHooksClient, "fillNormal");
/* 123 */   public static ReflectorMethod ForgeHooksClient_handleCameraTransforms = new ReflectorMethod(ForgeHooksClient, "handleCameraTransforms");
/* 124 */   public static ReflectorMethod ForgeHooksClient_getArmorTexture = new ReflectorMethod(ForgeHooksClient, "getArmorTexture");
/* 125 */   public static ReflectorMethod ForgeHooksClient_getFogDensity = new ReflectorMethod(ForgeHooksClient, "getFogDensity");
/* 126 */   public static ReflectorMethod ForgeHooksClient_getFOVModifier = new ReflectorMethod(ForgeHooksClient, "getFOVModifier");
/* 127 */   public static ReflectorMethod ForgeHooksClient_getMatrix = new ReflectorMethod(ForgeHooksClient, "getMatrix", new Class[] { ModelRotation.class });
/* 128 */   public static ReflectorMethod ForgeHooksClient_getOffsetFOV = new ReflectorMethod(ForgeHooksClient, "getOffsetFOV");
/* 129 */   public static ReflectorMethod ForgeHooksClient_loadEntityShader = new ReflectorMethod(ForgeHooksClient, "loadEntityShader");
/* 130 */   public static ReflectorMethod ForgeHooksClient_onDrawBlockHighlight = new ReflectorMethod(ForgeHooksClient, "onDrawBlockHighlight");
/* 131 */   public static ReflectorMethod ForgeHooksClient_onFogRender = new ReflectorMethod(ForgeHooksClient, "onFogRender");
/* 132 */   public static ReflectorMethod ForgeHooksClient_onTextureStitchedPre = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPre");
/* 133 */   public static ReflectorMethod ForgeHooksClient_onTextureStitchedPost = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPost");
/* 134 */   public static ReflectorMethod ForgeHooksClient_orientBedCamera = new ReflectorMethod(ForgeHooksClient, "orientBedCamera");
/* 135 */   public static ReflectorMethod ForgeHooksClient_renderFirstPersonHand = new ReflectorMethod(ForgeHooksClient, "renderFirstPersonHand");
/* 136 */   public static ReflectorMethod ForgeHooksClient_setRenderLayer = new ReflectorMethod(ForgeHooksClient, "setRenderLayer");
/* 137 */   public static ReflectorMethod ForgeHooksClient_setRenderPass = new ReflectorMethod(ForgeHooksClient, "setRenderPass");
/* 138 */   public static ReflectorMethod ForgeHooksClient_transform = new ReflectorMethod(ForgeHooksClient, "transform");
/* 139 */   public static final ReflectorClass ForgeItem = new ReflectorClass(Item.class);
/* 140 */   public static ReflectorField ForgeItem_delegate = new ReflectorField(ForgeItem, "delegate");
/* 141 */   public static ReflectorMethod ForgeItem_getDurabilityForDisplay = new ReflectorMethod(ForgeItem, "getDurabilityForDisplay");
/* 142 */   public static ReflectorMethod ForgeItem_getModel = new ReflectorMethod(ForgeItem, "getModel");
/* 143 */   public static ReflectorMethod ForgeItem_shouldCauseReequipAnimation = new ReflectorMethod(ForgeItem, "shouldCauseReequipAnimation");
/* 144 */   public static ReflectorMethod ForgeItem_showDurabilityBar = new ReflectorMethod(ForgeItem, "showDurabilityBar");
/* 145 */   public static final ReflectorClass ForgeModContainer = new ReflectorClass("net.minecraftforge.common.ForgeModContainer");
/* 146 */   public static ReflectorField ForgeModContainer_forgeLightPipelineEnabled = new ReflectorField(ForgeModContainer, "forgeLightPipelineEnabled");
/* 147 */   public static final ReflectorClass ForgeTileEntity = new ReflectorClass(TileEntity.class);
/* 148 */   public static ReflectorMethod ForgeTileEntity_canRenderBreaking = new ReflectorMethod(ForgeTileEntity, "canRenderBreaking");
/* 149 */   public static ReflectorMethod ForgeTileEntity_getRenderBoundingBox = new ReflectorMethod(ForgeTileEntity, "getRenderBoundingBox");
/* 150 */   public static ReflectorMethod ForgeTileEntity_hasFastRenderer = new ReflectorMethod(ForgeTileEntity, "hasFastRenderer");
/* 151 */   public static ReflectorMethod ForgeTileEntity_shouldRenderInPass = new ReflectorMethod(ForgeTileEntity, "shouldRenderInPass");
/* 152 */   public static final ReflectorClass ForgeVertexFormatElementEnumUseage = new ReflectorClass(VertexFormatElement.EnumUsage.class);
/* 153 */   public static ReflectorMethod ForgeVertexFormatElementEnumUseage_preDraw = new ReflectorMethod(ForgeVertexFormatElementEnumUseage, "preDraw");
/* 154 */   public static ReflectorMethod ForgeVertexFormatElementEnumUseage_postDraw = new ReflectorMethod(ForgeVertexFormatElementEnumUseage, "postDraw");
/* 155 */   public static final ReflectorClass ForgeWorld = new ReflectorClass(World.class);
/* 156 */   public static ReflectorMethod ForgeWorld_countEntities = new ReflectorMethod(ForgeWorld, "countEntities", new Class[] { EnumCreatureType.class, boolean.class });
/* 157 */   public static final ReflectorClass ForgeWorldProvider = new ReflectorClass(WorldProvider.class);
/* 158 */   public static ReflectorMethod ForgeWorldProvider_getCloudRenderer = new ReflectorMethod(ForgeWorldProvider, "getCloudRenderer");
/* 159 */   public static ReflectorMethod ForgeWorldProvider_getSkyRenderer = new ReflectorMethod(ForgeWorldProvider, "getSkyRenderer");
/* 160 */   public static ReflectorMethod ForgeWorldProvider_getWeatherRenderer = new ReflectorMethod(ForgeWorldProvider, "getWeatherRenderer");
/* 161 */   public static ReflectorClass IColoredBakedQuad = new ReflectorClass("net.minecraftforge.client.model.IColoredBakedQuad");
/* 162 */   public static final ReflectorClass IExtendedBlockState = new ReflectorClass("net.minecraftforge.common.property.IExtendedBlockState");
/* 163 */   public static ReflectorMethod IExtendedBlockState_getClean = new ReflectorMethod(IExtendedBlockState, "getClean");
/* 164 */   public static final ReflectorClass IModel = new ReflectorClass("net.minecraftforge.client.model.IModel");
/* 165 */   public static ReflectorMethod IModel_getTextures = new ReflectorMethod(IModel, "getTextures");
/* 166 */   public static final ReflectorClass IRenderHandler = new ReflectorClass("net.minecraftforge.client.IRenderHandler");
/* 167 */   public static ReflectorMethod IRenderHandler_render = new ReflectorMethod(IRenderHandler, "render");
/* 168 */   public static final ReflectorClass ItemModelMesherForge = new ReflectorClass("net.minecraftforge.client.ItemModelMesherForge");
/* 169 */   public static ReflectorConstructor ItemModelMesherForge_Constructor = new ReflectorConstructor(ItemModelMesherForge, new Class[] { ModelManager.class });
/* 170 */   public static final ReflectorClass Launch = new ReflectorClass("net.minecraft.launchwrapper.Launch");
/* 171 */   public static ReflectorField Launch_blackboard = new ReflectorField(Launch, "blackboard");
/* 172 */   public static final ReflectorClass LightUtil = new ReflectorClass("net.minecraftforge.client.model.pipeline.LightUtil");
/* 173 */   public static ReflectorField LightUtil_itemConsumer = new ReflectorField(LightUtil, "itemConsumer");
/* 174 */   public static ReflectorMethod LightUtil_putBakedQuad = new ReflectorMethod(LightUtil, "putBakedQuad");
/* 175 */   public static ReflectorField LightUtil_tessellator = new ReflectorField(LightUtil, "tessellator");
/* 176 */   public static final ReflectorClass Loader = new ReflectorClass("net.minecraftforge.fml.common.Loader");
/* 177 */   public static ReflectorMethod Loader_getActiveModList = new ReflectorMethod(Loader, "getActiveModList");
/* 178 */   public static ReflectorMethod Loader_instance = new ReflectorMethod(Loader, "instance");
/* 179 */   public static final ReflectorClass MinecraftForge = new ReflectorClass("net.minecraftforge.common.MinecraftForge");
/* 180 */   public static final ReflectorField MinecraftForge_EVENT_BUS = new ReflectorField(MinecraftForge, "EVENT_BUS");
/* 181 */   public static final ReflectorClass MinecraftForgeClient = new ReflectorClass("net.minecraftforge.client.MinecraftForgeClient");
/* 182 */   public static ReflectorMethod MinecraftForgeClient_getRenderPass = new ReflectorMethod(MinecraftForgeClient, "getRenderPass");
/* 183 */   public static ReflectorMethod MinecraftForgeClient_onRebuildChunk = new ReflectorMethod(MinecraftForgeClient, "onRebuildChunk");
/* 184 */   public static final ReflectorClass ModContainer = new ReflectorClass("net.minecraftforge.fml.common.ModContainer");
/* 185 */   public static ReflectorMethod ModContainer_getModId = new ReflectorMethod(ModContainer, "getModId");
/* 186 */   public static final ReflectorClass ModelLoader = new ReflectorClass("net.minecraftforge.client.model.ModelLoader");
/* 187 */   public static ReflectorField ModelLoader_stateModels = new ReflectorField(ModelLoader, "stateModels");
/* 188 */   public static ReflectorMethod ModelLoader_onRegisterItems = new ReflectorMethod(ModelLoader, "onRegisterItems");
/* 189 */   public static ReflectorMethod ModelLoader_getInventoryVariant = new ReflectorMethod(ModelLoader, "getInventoryVariant");
/* 190 */   public static ReflectorField ModelLoader_textures = new ReflectorField(ModelLoader, "textures");
/* 191 */   public static final ReflectorClass ModelLoader_VanillaLoader = new ReflectorClass("net.minecraftforge.client.model.ModelLoader$VanillaLoader");
/* 192 */   public static ReflectorField ModelLoader_VanillaLoader_INSTANCE = new ReflectorField(ModelLoader_VanillaLoader, "instance");
/* 193 */   public static ReflectorMethod ModelLoader_VanillaLoader_loadModel = new ReflectorMethod(ModelLoader_VanillaLoader, "loadModel");
/* 194 */   public static final ReflectorClass RenderBlockOverlayEvent_OverlayType = new ReflectorClass("net.minecraftforge.client.event.RenderBlockOverlayEvent$OverlayType");
/* 195 */   public static ReflectorField RenderBlockOverlayEvent_OverlayType_BLOCK = new ReflectorField(RenderBlockOverlayEvent_OverlayType, "BLOCK");
/* 196 */   public static final ReflectorClass RenderingRegistry = new ReflectorClass("net.minecraftforge.fml.client.registry.RenderingRegistry");
/* 197 */   public static ReflectorMethod RenderingRegistry_loadEntityRenderers = new ReflectorMethod(RenderingRegistry, "loadEntityRenderers", new Class[] { RenderManager.class, Map.class });
/* 198 */   public static final ReflectorClass RenderItemInFrameEvent = new ReflectorClass("net.minecraftforge.client.event.RenderItemInFrameEvent");
/* 199 */   public static ReflectorConstructor RenderItemInFrameEvent_Constructor = new ReflectorConstructor(RenderItemInFrameEvent, new Class[] { EntityItemFrame.class, RenderItemFrame.class });
/* 200 */   public static final ReflectorClass RenderLivingEvent_Pre = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Pre");
/* 201 */   public static ReflectorConstructor RenderLivingEvent_Pre_Constructor = new ReflectorConstructor(RenderLivingEvent_Pre, new Class[] { EntityLivingBase.class, RendererLivingEntity.class, double.class, double.class, double.class });
/* 202 */   public static final ReflectorClass RenderLivingEvent_Post = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Post");
/* 203 */   public static ReflectorConstructor RenderLivingEvent_Post_Constructor = new ReflectorConstructor(RenderLivingEvent_Post, new Class[] { EntityLivingBase.class, RendererLivingEntity.class, double.class, double.class, double.class });
/* 204 */   public static final ReflectorClass RenderLivingEvent_Specials_Pre = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Specials$Pre");
/* 205 */   public static ReflectorConstructor RenderLivingEvent_Specials_Pre_Constructor = new ReflectorConstructor(RenderLivingEvent_Specials_Pre, new Class[] { EntityLivingBase.class, RendererLivingEntity.class, double.class, double.class, double.class });
/* 206 */   public static final ReflectorClass RenderLivingEvent_Specials_Post = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Specials$Post");
/* 207 */   public static ReflectorConstructor RenderLivingEvent_Specials_Post_Constructor = new ReflectorConstructor(RenderLivingEvent_Specials_Post, new Class[] { EntityLivingBase.class, RendererLivingEntity.class, double.class, double.class, double.class });
/* 208 */   public static ReflectorClass SplashScreen = new ReflectorClass("net.minecraftforge.fml.client.SplashProgress");
/* 209 */   public static final ReflectorClass WorldEvent_Load = new ReflectorClass("net.minecraftforge.event.world.WorldEvent$Load");
/* 210 */   public static ReflectorConstructor WorldEvent_Load_Constructor = new ReflectorConstructor(WorldEvent_Load, new Class[] { World.class });
/* 211 */   public static final ReflectorClass ChunkProviderClient = new ReflectorClass(ChunkProviderClient.class);
/* 212 */   public static ReflectorField ChunkProviderClient_chunkMapping = new ReflectorField(ChunkProviderClient, LongHashMap.class);
/* 213 */   public static ReflectorClass EntityVillager = new ReflectorClass(EntityVillager.class);
/* 214 */   public static ReflectorField EntityVillager_careerId = new ReflectorField(new FieldLocatorTypes(EntityVillager.class, new Class[0], int.class, new Class[] { int.class, boolean.class, boolean.class, InventoryBasic.class }, "EntityVillager.careerId"));
/* 215 */   public static ReflectorField EntityVillager_careerLevel = new ReflectorField(new FieldLocatorTypes(EntityVillager.class, new Class[] { int.class }, int.class, new Class[] { boolean.class, boolean.class, InventoryBasic.class }, "EntityVillager.careerLevel"));
/* 216 */   public static final ReflectorClass GuiBeacon = new ReflectorClass(GuiBeacon.class);
/* 217 */   public static ReflectorField GuiBeacon_tileBeacon = new ReflectorField(GuiBeacon, IInventory.class);
/* 218 */   public static final ReflectorClass GuiBrewingStand = new ReflectorClass(GuiBrewingStand.class);
/* 219 */   public static ReflectorField GuiBrewingStand_tileBrewingStand = new ReflectorField(GuiBrewingStand, IInventory.class);
/* 220 */   public static final ReflectorClass GuiChest = new ReflectorClass(GuiChest.class);
/* 221 */   public static ReflectorField GuiChest_lowerChestInventory = new ReflectorField(GuiChest, IInventory.class, 1);
/* 222 */   public static final ReflectorClass GuiEnchantment = new ReflectorClass(GuiEnchantment.class);
/* 223 */   public static ReflectorField GuiEnchantment_nameable = new ReflectorField(GuiEnchantment, IWorldNameable.class);
/* 224 */   public static final ReflectorClass GuiFurnace = new ReflectorClass(GuiFurnace.class);
/* 225 */   public static ReflectorField GuiFurnace_tileFurnace = new ReflectorField(GuiFurnace, IInventory.class);
/* 226 */   public static final ReflectorClass GuiHopper = new ReflectorClass(GuiHopper.class);
/* 227 */   public static ReflectorField GuiHopper_hopperInventory = new ReflectorField(GuiHopper, IInventory.class, 1);
/* 228 */   public static ReflectorClass GuiMainMenu = new ReflectorClass(ClientMainMenu.class);
/* 229 */   public static final ReflectorClass Minecraft = new ReflectorClass(Minecraft.class);
/* 230 */   public static ReflectorField Minecraft_defaultResourcePack = new ReflectorField(Minecraft, DefaultResourcePack.class);
/* 231 */   public static final ReflectorClass ModelHumanoidHead = new ReflectorClass(ModelHumanoidHead.class);
/* 232 */   public static ReflectorField ModelHumanoidHead_head = new ReflectorField(ModelHumanoidHead, ModelRenderer.class);
/* 233 */   public static final ReflectorClass ModelBat = new ReflectorClass(ModelBat.class);
/* 234 */   public static ReflectorFields ModelBat_ModelRenderers = new ReflectorFields(ModelBat, ModelRenderer.class, 6);
/* 235 */   public static final ReflectorClass ModelBlaze = new ReflectorClass(ModelBlaze.class);
/* 236 */   public static ReflectorField ModelBlaze_blazeHead = new ReflectorField(ModelBlaze, ModelRenderer.class);
/* 237 */   public static ReflectorField ModelBlaze_blazeSticks = new ReflectorField(ModelBlaze, ModelRenderer[].class);
/* 238 */   public static final ReflectorClass ModelBlock = new ReflectorClass(ModelBlock.class);
/* 239 */   public static ReflectorField ModelBlock_parentLocation = new ReflectorField(ModelBlock, ResourceLocation.class);
/* 240 */   public static ReflectorField ModelBlock_textures = new ReflectorField(ModelBlock, Map.class);
/* 241 */   public static final ReflectorClass ModelDragon = new ReflectorClass(ModelDragon.class);
/* 242 */   public static ReflectorFields ModelDragon_ModelRenderers = new ReflectorFields(ModelDragon, ModelRenderer.class, 12);
/* 243 */   public static final ReflectorClass ModelEnderCrystal = new ReflectorClass(ModelEnderCrystal.class);
/* 244 */   public static ReflectorFields ModelEnderCrystal_ModelRenderers = new ReflectorFields(ModelEnderCrystal, ModelRenderer.class, 3);
/* 245 */   public static final ReflectorClass RenderEnderCrystal = new ReflectorClass(RenderEnderCrystal.class);
/* 246 */   public static ReflectorField RenderEnderCrystal_modelEnderCrystal = new ReflectorField(RenderEnderCrystal, ModelBase.class, 0);
/* 247 */   public static final ReflectorClass ModelEnderMite = new ReflectorClass(ModelEnderMite.class);
/* 248 */   public static ReflectorField ModelEnderMite_bodyParts = new ReflectorField(ModelEnderMite, ModelRenderer[].class);
/* 249 */   public static final ReflectorClass ModelGhast = new ReflectorClass(ModelGhast.class);
/* 250 */   public static ReflectorField ModelGhast_body = new ReflectorField(ModelGhast, ModelRenderer.class);
/* 251 */   public static ReflectorField ModelGhast_tentacles = new ReflectorField(ModelGhast, ModelRenderer[].class);
/* 252 */   public static final ReflectorClass ModelGuardian = new ReflectorClass(ModelGuardian.class);
/* 253 */   public static ReflectorField ModelGuardian_body = new ReflectorField(ModelGuardian, ModelRenderer.class, 0);
/* 254 */   public static ReflectorField ModelGuardian_eye = new ReflectorField(ModelGuardian, ModelRenderer.class, 1);
/* 255 */   public static ReflectorField ModelGuardian_spines = new ReflectorField(ModelGuardian, ModelRenderer[].class, 0);
/* 256 */   public static ReflectorField ModelGuardian_tail = new ReflectorField(ModelGuardian, ModelRenderer[].class, 1);
/* 257 */   public static final ReflectorClass ModelHorse = new ReflectorClass(ModelHorse.class);
/* 258 */   public static ReflectorFields ModelHorse_ModelRenderers = new ReflectorFields(ModelHorse, ModelRenderer.class, 39);
/* 259 */   public static final ReflectorClass RenderLeashKnot = new ReflectorClass(RenderLeashKnot.class);
/* 260 */   public static ReflectorField RenderLeashKnot_leashKnotModel = new ReflectorField(RenderLeashKnot, ModelLeashKnot.class);
/* 261 */   public static final ReflectorClass ModelMagmaCube = new ReflectorClass(ModelMagmaCube.class);
/* 262 */   public static ReflectorField ModelMagmaCube_core = new ReflectorField(ModelMagmaCube, ModelRenderer.class);
/* 263 */   public static ReflectorField ModelMagmaCube_segments = new ReflectorField(ModelMagmaCube, ModelRenderer[].class);
/* 264 */   public static final ReflectorClass ModelOcelot = new ReflectorClass(ModelOcelot.class);
/* 265 */   public static ReflectorFields ModelOcelot_ModelRenderers = new ReflectorFields(ModelOcelot, ModelRenderer.class, 8);
/* 266 */   public static final ReflectorClass ModelRabbit = new ReflectorClass(ModelRabbit.class);
/* 267 */   public static ReflectorFields ModelRabbit_renderers = new ReflectorFields(ModelRabbit, ModelRenderer.class, 12);
/* 268 */   public static final ReflectorClass ModelSilverfish = new ReflectorClass(ModelSilverfish.class);
/* 269 */   public static ReflectorField ModelSilverfish_bodyParts = new ReflectorField(ModelSilverfish, ModelRenderer[].class, 0);
/* 270 */   public static ReflectorField ModelSilverfish_wingParts = new ReflectorField(ModelSilverfish, ModelRenderer[].class, 1);
/* 271 */   public static final ReflectorClass ModelSlime = new ReflectorClass(ModelSlime.class);
/* 272 */   public static ReflectorFields ModelSlime_ModelRenderers = new ReflectorFields(ModelSlime, ModelRenderer.class, 4);
/* 273 */   public static final ReflectorClass ModelSquid = new ReflectorClass(ModelSquid.class);
/* 274 */   public static ReflectorField ModelSquid_body = new ReflectorField(ModelSquid, ModelRenderer.class);
/* 275 */   public static ReflectorField ModelSquid_tentacles = new ReflectorField(ModelSquid, ModelRenderer[].class);
/* 276 */   public static final ReflectorClass ModelWitch = new ReflectorClass(ModelWitch.class);
/* 277 */   public static ReflectorField ModelWitch_mole = new ReflectorField(ModelWitch, ModelRenderer.class, 0);
/* 278 */   public static ReflectorField ModelWitch_hat = new ReflectorField(ModelWitch, ModelRenderer.class, 1);
/* 279 */   public static final ReflectorClass ModelWither = new ReflectorClass(ModelWither.class);
/* 280 */   public static ReflectorField ModelWither_bodyParts = new ReflectorField(ModelWither, ModelRenderer[].class, 0);
/* 281 */   public static ReflectorField ModelWither_heads = new ReflectorField(ModelWither, ModelRenderer[].class, 1);
/* 282 */   public static final ReflectorClass ModelWolf = new ReflectorClass(ModelWolf.class);
/* 283 */   public static ReflectorField ModelWolf_tail = new ReflectorField(ModelWolf, ModelRenderer.class, 6);
/* 284 */   public static ReflectorField ModelWolf_mane = new ReflectorField(ModelWolf, ModelRenderer.class, 7);
/* 285 */   public static final ReflectorClass OptiFineClassTransformer = new ReflectorClass("optifine.OptiFineClassTransformer");
/* 286 */   public static ReflectorField OptiFineClassTransformer_instance = new ReflectorField(OptiFineClassTransformer, "instance");
/* 287 */   public static ReflectorMethod OptiFineClassTransformer_getOptiFineResource = new ReflectorMethod(OptiFineClassTransformer, "getOptiFineResource");
/* 288 */   public static final ReflectorClass RenderBoat = new ReflectorClass(RenderBoat.class);
/* 289 */   public static ReflectorField RenderBoat_modelBoat = new ReflectorField(RenderBoat, ModelBase.class);
/* 290 */   public static final ReflectorClass RenderMinecart = new ReflectorClass(RenderMinecart.class);
/* 291 */   public static ReflectorField RenderMinecart_modelMinecart = new ReflectorField(RenderMinecart, ModelBase.class);
/* 292 */   public static final ReflectorClass RenderWitherSkull = new ReflectorClass(RenderWitherSkull.class);
/* 293 */   public static ReflectorField RenderWitherSkull_model = new ReflectorField(RenderWitherSkull, ModelSkeletonHead.class);
/* 294 */   public static final ReflectorClass TileEntityBannerRenderer = new ReflectorClass(TileEntityBannerRenderer.class);
/* 295 */   public static ReflectorField TileEntityBannerRenderer_bannerModel = new ReflectorField(TileEntityBannerRenderer, ModelBanner.class);
/* 296 */   public static final ReflectorClass TileEntityBeacon = new ReflectorClass(TileEntityBeacon.class);
/* 297 */   public static ReflectorField TileEntityBeacon_customName = new ReflectorField(TileEntityBeacon, String.class);
/* 298 */   public static final ReflectorClass TileEntityBrewingStand = new ReflectorClass(TileEntityBrewingStand.class);
/* 299 */   public static ReflectorField TileEntityBrewingStand_customName = new ReflectorField(TileEntityBrewingStand, String.class);
/* 300 */   public static final ReflectorClass TileEntityChestRenderer = new ReflectorClass(TileEntityChestRenderer.class);
/* 301 */   public static ReflectorField TileEntityChestRenderer_simpleChest = new ReflectorField(TileEntityChestRenderer, ModelChest.class, 0);
/* 302 */   public static ReflectorField TileEntityChestRenderer_largeChest = new ReflectorField(TileEntityChestRenderer, ModelChest.class, 1);
/* 303 */   public static final ReflectorClass TileEntityEnchantmentTable = new ReflectorClass(TileEntityEnchantmentTable.class);
/* 304 */   public static ReflectorField TileEntityEnchantmentTable_customName = new ReflectorField(TileEntityEnchantmentTable, String.class);
/* 305 */   public static final ReflectorClass TileEntityEnchantmentTableRenderer = new ReflectorClass(TileEntityEnchantmentTableRenderer.class);
/* 306 */   public static ReflectorField TileEntityEnchantmentTableRenderer_modelBook = new ReflectorField(TileEntityEnchantmentTableRenderer, ModelBook.class);
/* 307 */   public static final ReflectorClass TileEntityEnderChestRenderer = new ReflectorClass(TileEntityEnderChestRenderer.class);
/* 308 */   public static ReflectorField TileEntityEnderChestRenderer_modelChest = new ReflectorField(TileEntityEnderChestRenderer, ModelChest.class);
/* 309 */   public static final ReflectorClass TileEntityFurnace = new ReflectorClass(TileEntityFurnace.class);
/* 310 */   public static ReflectorField TileEntityFurnace_customName = new ReflectorField(TileEntityFurnace, String.class);
/* 311 */   public static final ReflectorClass TileEntitySignRenderer = new ReflectorClass(TileEntitySignRenderer.class);
/* 312 */   public static ReflectorField TileEntitySignRenderer_model = new ReflectorField(TileEntitySignRenderer, ModelSign.class);
/* 313 */   public static final ReflectorClass TileEntitySkullRenderer = new ReflectorClass(TileEntitySkullRenderer.class);
/* 314 */   public static ReflectorField TileEntitySkullRenderer_humanoidHead = new ReflectorField(TileEntitySkullRenderer, ModelSkeletonHead.class, 1);
/*     */ 
/*     */ 
/*     */   
/*     */   public static void callVoid(ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 320 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 322 */       if (method == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 327 */       method.invoke(null, params);
/*     */     }
/* 329 */     catch (Throwable throwable) {
/*     */       
/* 331 */       handleException(throwable, null, refMethod, params);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean callBoolean(ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 339 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 341 */       if (method == null)
/*     */       {
/* 343 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 347 */       return ((Boolean)method.invoke(null, params)).booleanValue();
/*     */     
/*     */     }
/* 350 */     catch (Throwable throwable) {
/*     */       
/* 352 */       handleException(throwable, null, refMethod, params);
/* 353 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int callInt(ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 361 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 363 */       if (method == null)
/*     */       {
/* 365 */         return 0;
/*     */       }
/*     */ 
/*     */       
/* 369 */       return ((Integer)method.invoke(null, params)).intValue();
/*     */     
/*     */     }
/* 372 */     catch (Throwable throwable) {
/*     */       
/* 374 */       handleException(throwable, null, refMethod, params);
/* 375 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static float callFloat(ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 383 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 385 */       if (method == null)
/*     */       {
/* 387 */         return 0.0F;
/*     */       }
/*     */ 
/*     */       
/* 391 */       return ((Float)method.invoke(null, params)).floatValue();
/*     */     
/*     */     }
/* 394 */     catch (Throwable throwable) {
/*     */       
/* 396 */       handleException(throwable, null, refMethod, params);
/* 397 */       return 0.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double callDouble(ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 405 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 407 */       if (method == null)
/*     */       {
/* 409 */         return 0.0D;
/*     */       }
/*     */ 
/*     */       
/* 413 */       return ((Double)method.invoke(null, params)).doubleValue();
/*     */     
/*     */     }
/* 416 */     catch (Throwable throwable) {
/*     */       
/* 418 */       handleException(throwable, null, refMethod, params);
/* 419 */       return 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String callString(ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 427 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 429 */       if (method == null)
/*     */       {
/* 431 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 435 */       return (String)method.invoke(null, params);
/*     */     
/*     */     }
/* 438 */     catch (Throwable throwable) {
/*     */       
/* 440 */       handleException(throwable, null, refMethod, params);
/* 441 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object call(ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 449 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 451 */       if (method == null)
/*     */       {
/* 453 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 457 */       return method.invoke(null, params);
/*     */     
/*     */     }
/* 460 */     catch (Throwable throwable) {
/*     */       
/* 462 */       handleException(throwable, null, refMethod, params);
/* 463 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void callVoid(Object obj, ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 471 */       if (obj == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 476 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 478 */       if (method == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 483 */       method.invoke(obj, params);
/*     */     }
/* 485 */     catch (Throwable throwable) {
/*     */       
/* 487 */       handleException(throwable, obj, refMethod, params);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean callBoolean(Object obj, ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 495 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 497 */       if (method == null)
/*     */       {
/* 499 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 503 */       return ((Boolean)method.invoke(obj, params)).booleanValue();
/*     */     
/*     */     }
/* 506 */     catch (Throwable throwable) {
/*     */       
/* 508 */       handleException(throwable, obj, refMethod, params);
/* 509 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int callInt(Object obj, ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 517 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 519 */       if (method == null)
/*     */       {
/* 521 */         return 0;
/*     */       }
/*     */ 
/*     */       
/* 525 */       return ((Integer)method.invoke(obj, params)).intValue();
/*     */     
/*     */     }
/* 528 */     catch (Throwable throwable) {
/*     */       
/* 530 */       handleException(throwable, obj, refMethod, params);
/* 531 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double callDouble(Object obj, ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 539 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 541 */       if (method == null)
/*     */       {
/* 543 */         return 0.0D;
/*     */       }
/*     */ 
/*     */       
/* 547 */       return ((Double)method.invoke(obj, params)).doubleValue();
/*     */     
/*     */     }
/* 550 */     catch (Throwable throwable) {
/*     */       
/* 552 */       handleException(throwable, obj, refMethod, params);
/* 553 */       return 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String callString(Object obj, ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 561 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 563 */       if (method == null)
/*     */       {
/* 565 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 569 */       return (String)method.invoke(obj, params);
/*     */     
/*     */     }
/* 572 */     catch (Throwable throwable) {
/*     */       
/* 574 */       handleException(throwable, obj, refMethod, params);
/* 575 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object call(Object obj, ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 583 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 585 */       if (method == null)
/*     */       {
/* 587 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 591 */       return method.invoke(obj, params);
/*     */     
/*     */     }
/* 594 */     catch (Throwable throwable) {
/*     */       
/* 596 */       handleException(throwable, obj, refMethod, params);
/* 597 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object getFieldValue(ReflectorField refField) {
/* 603 */     return getFieldValue((Object)null, refField);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getFieldValue(Object obj, ReflectorField refField) {
/*     */     try {
/* 610 */       Field field = refField.getTargetField();
/*     */       
/* 612 */       if (field == null)
/*     */       {
/* 614 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 618 */       return field.get(obj);
/*     */     
/*     */     }
/* 621 */     catch (Throwable throwable) {
/*     */       
/* 623 */       Log.error("", throwable);
/* 624 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getFieldValueBoolean(Object obj, ReflectorField refField, boolean def) {
/*     */     try {
/* 632 */       Field field = refField.getTargetField();
/*     */       
/* 634 */       if (field == null)
/*     */       {
/* 636 */         return def;
/*     */       }
/*     */ 
/*     */       
/* 640 */       return field.getBoolean(obj);
/*     */     
/*     */     }
/* 643 */     catch (Throwable throwable) {
/*     */       
/* 645 */       Log.error("", throwable);
/* 646 */       return def;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object getFieldValue(ReflectorFields refFields, int index) {
/* 652 */     ReflectorField reflectorfield = refFields.getReflectorField(index);
/* 653 */     return (reflectorfield == null) ? null : getFieldValue(reflectorfield);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object getFieldValue(Object obj, ReflectorFields refFields, int index) {
/* 658 */     ReflectorField reflectorfield = refFields.getReflectorField(index);
/* 659 */     return (reflectorfield == null) ? null : getFieldValue(obj, reflectorfield);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getFieldValueFloat(Object obj, ReflectorField refField, float def) {
/*     */     try {
/* 666 */       Field field = refField.getTargetField();
/*     */       
/* 668 */       if (field == null)
/*     */       {
/* 670 */         return def;
/*     */       }
/*     */ 
/*     */       
/* 674 */       return field.getFloat(obj);
/*     */     
/*     */     }
/* 677 */     catch (Throwable throwable) {
/*     */       
/* 679 */       Log.error("", throwable);
/* 680 */       return def;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getFieldValueInt(Object obj, ReflectorField refField, int def) {
/*     */     try {
/* 688 */       Field field = refField.getTargetField();
/*     */       
/* 690 */       if (field == null)
/*     */       {
/* 692 */         return def;
/*     */       }
/*     */ 
/*     */       
/* 696 */       return field.getInt(obj);
/*     */     
/*     */     }
/* 699 */     catch (Throwable throwable) {
/*     */       
/* 701 */       Log.error("", throwable);
/* 702 */       return def;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean setFieldValue(ReflectorField refField, Object value) {
/* 708 */     return setFieldValue(null, refField, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean setFieldValue(Object obj, ReflectorField refField, Object value) {
/*     */     try {
/* 715 */       Field field = refField.getTargetField();
/*     */       
/* 717 */       if (field == null)
/*     */       {
/* 719 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 723 */       field.set(obj, value);
/* 724 */       return true;
/*     */     
/*     */     }
/* 727 */     catch (Throwable throwable) {
/*     */       
/* 729 */       Log.error("", throwable);
/* 730 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setFieldValueInt(Object obj, ReflectorField refField, int value) {
/*     */     try {
/* 738 */       Field field = refField.getTargetField();
/*     */       
/* 740 */       if (field != null)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 745 */         field.setInt(obj, value);
/*     */       }
/*     */     }
/* 748 */     catch (Throwable throwable) {
/*     */       
/* 750 */       Log.error("", throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean postForgeBusEvent(ReflectorConstructor constr, Object... params) {
/* 756 */     Object object = newInstance(constr, params);
/* 757 */     return postForgeBusEvent(object);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean postForgeBusEvent(Object event) {
/* 762 */     if (event == null)
/*     */     {
/* 764 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 768 */     Object object = getFieldValue(MinecraftForge_EVENT_BUS);
/*     */     
/* 770 */     if (object == null)
/*     */     {
/* 772 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 776 */     Object object1 = call(object, EventBus_post, new Object[] { event });
/*     */     
/* 778 */     if (!(object1 instanceof Boolean))
/*     */     {
/* 780 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 784 */     return ((Boolean)object1).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object newInstance(ReflectorConstructor constr, Object... params) {
/* 792 */     Constructor constructor = constr.getTargetConstructor();
/*     */     
/* 794 */     if (constructor == null)
/*     */     {
/* 796 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 802 */       return constructor.newInstance(params);
/*     */     }
/* 804 */     catch (Throwable throwable) {
/*     */       
/* 806 */       handleException(throwable, constr, params);
/* 807 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean matchesTypes(Class[] pTypes, Class[] cTypes) {
/* 814 */     if (pTypes.length != cTypes.length)
/*     */     {
/* 816 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 820 */     for (int i = 0; i < cTypes.length; i++) {
/*     */       
/* 822 */       Class oclass = pTypes[i];
/* 823 */       Class oclass1 = cTypes[i];
/*     */       
/* 825 */       if (oclass != oclass1)
/*     */       {
/* 827 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 831 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void handleException(Throwable e, Object obj, ReflectorMethod refMethod, Object[] params) {
/* 837 */     if (e instanceof java.lang.reflect.InvocationTargetException) {
/*     */       
/* 839 */       Throwable throwable = e.getCause();
/*     */       
/* 841 */       if (throwable instanceof RuntimeException)
/*     */       {
/* 843 */         throw (RuntimeException)throwable;
/*     */       }
/*     */ 
/*     */       
/* 847 */       Log.error("", e);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 852 */       Log.warn("*** Exception outside of method ***");
/* 853 */       Log.warn("Method deactivated: " + refMethod.getTargetMethod());
/* 854 */       refMethod.deactivate();
/*     */       
/* 856 */       if (e instanceof IllegalArgumentException) {
/*     */         
/* 858 */         Log.warn("*** IllegalArgumentException ***");
/* 859 */         Log.warn("Method: " + refMethod.getTargetMethod());
/* 860 */         Log.warn("Object: " + obj);
/* 861 */         Log.warn("Parameter classes: " + ArrayUtils.arrayToString(getClasses(params)));
/* 862 */         Log.warn("Parameters: " + ArrayUtils.arrayToString(params));
/*     */       } 
/*     */       
/* 865 */       Log.warn("", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void handleException(Throwable e, ReflectorConstructor refConstr, Object[] params) {
/* 871 */     if (e instanceof java.lang.reflect.InvocationTargetException) {
/*     */       
/* 873 */       Log.error("", e);
/*     */     }
/*     */     else {
/*     */       
/* 877 */       Log.warn("*** Exception outside of constructor ***");
/* 878 */       Log.warn("Constructor deactivated: " + refConstr.getTargetConstructor());
/* 879 */       refConstr.deactivate();
/*     */       
/* 881 */       if (e instanceof IllegalArgumentException) {
/*     */         
/* 883 */         Log.warn("*** IllegalArgumentException ***");
/* 884 */         Log.warn("Constructor: " + refConstr.getTargetConstructor());
/* 885 */         Log.warn("Parameter classes: " + ArrayUtils.arrayToString(getClasses(params)));
/* 886 */         Log.warn("Parameters: " + ArrayUtils.arrayToString(params));
/*     */       } 
/*     */       
/* 889 */       Log.warn("", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static Object[] getClasses(Object[] objs) {
/* 895 */     if (objs == null)
/*     */     {
/* 897 */       return (Object[])new Class[0];
/*     */     }
/*     */ 
/*     */     
/* 901 */     Class[] aclass = new Class[objs.length];
/*     */     
/* 903 */     for (int i = 0; i < aclass.length; i++) {
/*     */       
/* 905 */       Object object = objs[i];
/*     */       
/* 907 */       if (object != null)
/*     */       {
/* 909 */         aclass[i] = object.getClass();
/*     */       }
/*     */     } 
/*     */     
/* 913 */     return (Object[])aclass;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\reflect\Reflector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */