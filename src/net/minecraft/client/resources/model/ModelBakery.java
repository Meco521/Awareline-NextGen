/*     */ package net.minecraft.client.resources.model;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.util.Collection;
/*     */ import java.util.Deque;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.BlockModelShapes;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.BlockPart;
/*     */ import net.minecraft.client.renderer.block.model.BlockPartFace;
/*     */ import net.minecraft.client.renderer.block.model.FaceBakery;
/*     */ import net.minecraft.client.renderer.block.model.ItemModelGenerator;
/*     */ import net.minecraft.client.renderer.block.model.ModelBlock;
/*     */ import net.minecraft.client.renderer.block.model.ModelBlockDefinition;
/*     */ import net.minecraft.client.renderer.texture.IIconCreator;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IRegistry;
/*     */ import net.minecraft.util.RegistrySimple;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.client.model.ITransformation;
/*     */ import net.minecraftforge.fml.common.registry.RegistryDelegate;
/*     */ import net.optifine.CustomItems;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ public class ModelBakery {
/*  40 */   private static final Set<ResourceLocation> LOCATIONS_BUILTIN_TEXTURES = Sets.newHashSet((Object[])new ResourceLocation[] { new ResourceLocation("blocks/water_flow"), new ResourceLocation("blocks/water_still"), new ResourceLocation("blocks/lava_flow"), new ResourceLocation("blocks/lava_still"), new ResourceLocation("blocks/destroy_stage_0"), new ResourceLocation("blocks/destroy_stage_1"), new ResourceLocation("blocks/destroy_stage_2"), new ResourceLocation("blocks/destroy_stage_3"), new ResourceLocation("blocks/destroy_stage_4"), new ResourceLocation("blocks/destroy_stage_5"), new ResourceLocation("blocks/destroy_stage_6"), new ResourceLocation("blocks/destroy_stage_7"), new ResourceLocation("blocks/destroy_stage_8"), new ResourceLocation("blocks/destroy_stage_9"), new ResourceLocation("items/empty_armor_slot_helmet"), new ResourceLocation("items/empty_armor_slot_chestplate"), new ResourceLocation("items/empty_armor_slot_leggings"), new ResourceLocation("items/empty_armor_slot_boots") });
/*  41 */   private static final Logger LOGGER = LogManager.getLogger();
/*  42 */   protected static final ModelResourceLocation MODEL_MISSING = new ModelResourceLocation("builtin/missing", "missing");
/*  43 */   private static final Map<String, String> BUILT_IN_MODELS = Maps.newHashMap();
/*  44 */   private static final Joiner JOINER = Joiner.on(" -> ");
/*     */   private final IResourceManager resourceManager;
/*  46 */   final Map<ResourceLocation, TextureAtlasSprite> sprites = Maps.newHashMap();
/*  47 */   private final Map<ResourceLocation, ModelBlock> models = Maps.newLinkedHashMap();
/*  48 */   private final Map<ModelResourceLocation, ModelBlockDefinition.Variants> variants = Maps.newLinkedHashMap();
/*     */   private final TextureMap textureMap;
/*     */   private final BlockModelShapes blockModelShapes;
/*  51 */   private final FaceBakery faceBakery = new FaceBakery();
/*  52 */   private final ItemModelGenerator itemModelGenerator = new ItemModelGenerator();
/*  53 */   private final RegistrySimple<ModelResourceLocation, IBakedModel> bakedRegistry = new RegistrySimple();
/*  54 */   private static final ModelBlock MODEL_GENERATED = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
/*  55 */   private static final ModelBlock MODEL_COMPASS = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
/*  56 */   private static final ModelBlock MODEL_CLOCK = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
/*  57 */   private static final ModelBlock MODEL_ENTITY = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
/*  58 */   private final Map<String, ResourceLocation> itemLocations = Maps.newLinkedHashMap();
/*  59 */   private final Map<ResourceLocation, ModelBlockDefinition> blockDefinitions = Maps.newHashMap();
/*  60 */   private final Map<Item, List<String>> variantNames = Maps.newIdentityHashMap();
/*  61 */   private static final Map<RegistryDelegate<Item>, Set<String>> customVariantNames = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ModelBakery(IResourceManager p_i46085_1_, TextureMap p_i46085_2_, BlockModelShapes p_i46085_3_) {
/*  65 */     this.resourceManager = p_i46085_1_;
/*  66 */     this.textureMap = p_i46085_2_;
/*  67 */     this.blockModelShapes = p_i46085_3_;
/*     */   }
/*     */ 
/*     */   
/*     */   public IRegistry<ModelResourceLocation, IBakedModel> setupModelRegistry() {
/*  72 */     loadVariantItemModels();
/*  73 */     loadModelsCheck();
/*  74 */     loadSprites();
/*  75 */     bakeItemModels();
/*  76 */     bakeBlockModels();
/*  77 */     return (IRegistry<ModelResourceLocation, IBakedModel>)this.bakedRegistry;
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadVariantItemModels() {
/*  82 */     loadVariants(this.blockModelShapes.getBlockStateMapper().putAllStateModelLocations().values());
/*  83 */     this.variants.put(MODEL_MISSING, new ModelBlockDefinition.Variants(MODEL_MISSING.getVariant(), Lists.newArrayList((Object[])new ModelBlockDefinition.Variant[] { new ModelBlockDefinition.Variant(new ResourceLocation(MODEL_MISSING.getResourcePath()), ModelRotation.X0_Y0, false, 1) })));
/*  84 */     ResourceLocation resourcelocation = new ResourceLocation("item_frame");
/*  85 */     ModelBlockDefinition modelblockdefinition = getModelBlockDefinition(resourcelocation);
/*  86 */     registerVariant(modelblockdefinition, new ModelResourceLocation(resourcelocation, "normal"));
/*  87 */     registerVariant(modelblockdefinition, new ModelResourceLocation(resourcelocation, "map"));
/*  88 */     loadVariantModels();
/*  89 */     loadItemModels();
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadVariants(Collection<ModelResourceLocation> p_177591_1_) {
/*  94 */     for (ModelResourceLocation modelresourcelocation : p_177591_1_) {
/*     */ 
/*     */       
/*     */       try {
/*  98 */         ModelBlockDefinition modelblockdefinition = getModelBlockDefinition(modelresourcelocation);
/*     */ 
/*     */         
/*     */         try {
/* 102 */           registerVariant(modelblockdefinition, modelresourcelocation);
/*     */         }
/* 104 */         catch (Exception exception) {
/*     */           
/* 106 */           LOGGER.warn("Unable to load variant: " + modelresourcelocation.getVariant() + " from " + modelresourcelocation, exception);
/*     */         }
/*     */       
/* 109 */       } catch (Exception exception1) {
/*     */         
/* 111 */         LOGGER.warn("Unable to load definition " + modelresourcelocation, exception1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerVariant(ModelBlockDefinition p_177569_1_, ModelResourceLocation p_177569_2_) {
/* 118 */     this.variants.put(p_177569_2_, p_177569_1_.getVariants(p_177569_2_.getVariant()));
/*     */   }
/*     */ 
/*     */   
/*     */   private ModelBlockDefinition getModelBlockDefinition(ResourceLocation p_177586_1_) {
/* 123 */     ResourceLocation resourcelocation = getBlockStateLocation(p_177586_1_);
/* 124 */     ModelBlockDefinition modelblockdefinition = this.blockDefinitions.get(resourcelocation);
/*     */     
/* 126 */     if (modelblockdefinition == null) {
/*     */       
/* 128 */       List<ModelBlockDefinition> list = Lists.newArrayList();
/*     */ 
/*     */       
/*     */       try {
/* 132 */         for (IResource iresource : this.resourceManager.getAllResources(resourcelocation)) {
/*     */           
/* 134 */           InputStream inputstream = null;
/*     */ 
/*     */           
/*     */           try {
/* 138 */             inputstream = iresource.getInputStream();
/* 139 */             ModelBlockDefinition modelblockdefinition1 = ModelBlockDefinition.parseFromReader(new InputStreamReader(inputstream, Charsets.UTF_8));
/* 140 */             list.add(modelblockdefinition1);
/*     */           }
/* 142 */           catch (Exception exception) {
/*     */             
/* 144 */             throw new RuntimeException("Encountered an exception when loading model definition of '" + p_177586_1_ + "' from: '" + iresource.getResourceLocation() + "' in resourcepack: '" + iresource.getResourcePackName() + "'", exception);
/*     */           }
/*     */           finally {
/*     */             
/* 148 */             IOUtils.closeQuietly(inputstream);
/*     */           }
/*     */         
/*     */         } 
/* 152 */       } catch (IOException ioexception) {
/*     */         
/* 154 */         throw new RuntimeException("Encountered an exception when loading model definition of model " + resourcelocation.toString(), ioexception);
/*     */       } 
/*     */       
/* 157 */       modelblockdefinition = new ModelBlockDefinition(list);
/* 158 */       this.blockDefinitions.put(resourcelocation, modelblockdefinition);
/*     */     } 
/*     */     
/* 161 */     return modelblockdefinition;
/*     */   }
/*     */ 
/*     */   
/*     */   private ResourceLocation getBlockStateLocation(ResourceLocation p_177584_1_) {
/* 166 */     return new ResourceLocation(p_177584_1_.getResourceDomain(), "blockstates/" + p_177584_1_.getResourcePath() + ".json");
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadVariantModels() {
/* 171 */     for (Map.Entry<ModelResourceLocation, ModelBlockDefinition.Variants> entry : this.variants.entrySet()) {
/*     */       
/* 173 */       for (ModelBlockDefinition.Variant modelblockdefinition$variant : ((ModelBlockDefinition.Variants)entry.getValue()).getVariants()) {
/*     */         
/* 175 */         ResourceLocation resourcelocation = modelblockdefinition$variant.getModelLocation();
/*     */         
/* 177 */         if (this.models.get(resourcelocation) == null)
/*     */           
/*     */           try {
/*     */             
/* 181 */             ModelBlock modelblock = loadModel(resourcelocation);
/* 182 */             this.models.put(resourcelocation, modelblock);
/*     */           }
/* 184 */           catch (Exception exception) {
/*     */             
/* 186 */             LOGGER.warn("Unable to load block model: '" + resourcelocation + "' for variant: '" + entry.getKey() + "'", exception);
/*     */           }  
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private ModelBlock loadModel(ResourceLocation p_177594_1_) throws IOException {
/*     */     Reader reader;
/*     */     ModelBlock modelblock;
/* 195 */     String s = p_177594_1_.getResourcePath();
/*     */     
/* 197 */     if ("builtin/generated".equals(s))
/*     */     {
/* 199 */       return MODEL_GENERATED;
/*     */     }
/* 201 */     if ("builtin/compass".equals(s))
/*     */     {
/* 203 */       return MODEL_COMPASS;
/*     */     }
/* 205 */     if ("builtin/clock".equals(s))
/*     */     {
/* 207 */       return MODEL_CLOCK;
/*     */     }
/* 209 */     if ("builtin/entity".equals(s))
/*     */     {
/* 211 */       return MODEL_ENTITY;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 217 */     if (s.startsWith("builtin/")) {
/*     */       
/* 219 */       String s1 = s.substring("builtin/".length());
/* 220 */       String s2 = BUILT_IN_MODELS.get(s1);
/*     */       
/* 222 */       if (s2 == null)
/*     */       {
/* 224 */         throw new FileNotFoundException(p_177594_1_.toString());
/*     */       }
/*     */       
/* 227 */       reader = new StringReader(s2);
/*     */     }
/*     */     else {
/*     */       
/* 231 */       p_177594_1_ = getModelLocation(p_177594_1_);
/* 232 */       IResource iresource = this.resourceManager.getResource(p_177594_1_);
/* 233 */       reader = new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 240 */       ModelBlock modelblock1 = ModelBlock.deserialize(reader);
/* 241 */       modelblock1.name = p_177594_1_.toString();
/* 242 */       modelblock = modelblock1;
/* 243 */       String s3 = TextureUtils.getBasePath(p_177594_1_.getResourcePath());
/* 244 */       fixModelLocations(modelblock1, s3);
/*     */     }
/*     */     finally {
/*     */       
/* 248 */       reader.close();
/*     */     } 
/*     */     
/* 251 */     return modelblock;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ResourceLocation getModelLocation(ResourceLocation p_177580_1_) {
/* 257 */     ResourceLocation resourcelocation = p_177580_1_;
/* 258 */     String s = p_177580_1_.getResourcePath();
/*     */     
/* 260 */     if (!s.startsWith("mcpatcher") && !s.startsWith("optifine"))
/*     */     {
/* 262 */       return new ResourceLocation(p_177580_1_.getResourceDomain(), "models/" + p_177580_1_.getResourcePath() + ".json");
/*     */     }
/*     */ 
/*     */     
/* 266 */     if (!s.endsWith(".json"))
/*     */     {
/* 268 */       resourcelocation = new ResourceLocation(p_177580_1_.getResourceDomain(), s + ".json");
/*     */     }
/*     */     
/* 271 */     return resourcelocation;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadItemModels() {
/* 277 */     registerVariantNames();
/*     */     
/* 279 */     for (Item item : Item.itemRegistry) {
/*     */       
/* 281 */       for (String s : getVariantNames(item)) {
/*     */         
/* 283 */         ResourceLocation resourcelocation = getItemLocation(s);
/* 284 */         this.itemLocations.put(s, resourcelocation);
/*     */         
/* 286 */         if (this.models.get(resourcelocation) == null) {
/*     */           
/*     */           try {
/*     */             
/* 290 */             ModelBlock modelblock = loadModel(resourcelocation);
/* 291 */             this.models.put(resourcelocation, modelblock);
/*     */           }
/* 293 */           catch (Exception exception) {
/*     */             
/* 295 */             LOGGER.warn("Unable to load item model: '" + resourcelocation + "' for item: '" + Item.itemRegistry.getNameForObject(item) + "'", exception);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadItemModel(String p_loadItemModel_1_, ResourceLocation p_loadItemModel_2_, ResourceLocation p_loadItemModel_3_) {
/* 304 */     this.itemLocations.put(p_loadItemModel_1_, p_loadItemModel_2_);
/*     */     
/* 306 */     if (this.models.get(p_loadItemModel_2_) == null) {
/*     */       
/*     */       try {
/*     */         
/* 310 */         ModelBlock modelblock = loadModel(p_loadItemModel_2_);
/* 311 */         this.models.put(p_loadItemModel_2_, modelblock);
/*     */       }
/* 313 */       catch (Exception exception) {
/*     */         
/* 315 */         LOGGER.warn("Unable to load item model: '{}' for item: '{}'", new Object[] { p_loadItemModel_2_, p_loadItemModel_3_ });
/* 316 */         LOGGER.warn(exception.getClass().getName() + ": " + exception.getMessage());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerVariantNames() {
/* 323 */     this.variantNames.clear();
/* 324 */     this.variantNames.put(Item.getItemFromBlock(Blocks.stone), Lists.newArrayList((Object[])new String[] { "stone", "granite", "granite_smooth", "diorite", "diorite_smooth", "andesite", "andesite_smooth" }));
/* 325 */     this.variantNames.put(Item.getItemFromBlock(Blocks.dirt), Lists.newArrayList((Object[])new String[] { "dirt", "coarse_dirt", "podzol" }));
/* 326 */     this.variantNames.put(Item.getItemFromBlock(Blocks.planks), Lists.newArrayList((Object[])new String[] { "oak_planks", "spruce_planks", "birch_planks", "jungle_planks", "acacia_planks", "dark_oak_planks" }));
/* 327 */     this.variantNames.put(Item.getItemFromBlock(Blocks.sapling), Lists.newArrayList((Object[])new String[] { "oak_sapling", "spruce_sapling", "birch_sapling", "jungle_sapling", "acacia_sapling", "dark_oak_sapling" }));
/* 328 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.sand), Lists.newArrayList((Object[])new String[] { "sand", "red_sand" }));
/* 329 */     this.variantNames.put(Item.getItemFromBlock(Blocks.log), Lists.newArrayList((Object[])new String[] { "oak_log", "spruce_log", "birch_log", "jungle_log" }));
/* 330 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.leaves), Lists.newArrayList((Object[])new String[] { "oak_leaves", "spruce_leaves", "birch_leaves", "jungle_leaves" }));
/* 331 */     this.variantNames.put(Item.getItemFromBlock(Blocks.sponge), Lists.newArrayList((Object[])new String[] { "sponge", "sponge_wet" }));
/* 332 */     this.variantNames.put(Item.getItemFromBlock(Blocks.sandstone), Lists.newArrayList((Object[])new String[] { "sandstone", "chiseled_sandstone", "smooth_sandstone" }));
/* 333 */     this.variantNames.put(Item.getItemFromBlock(Blocks.red_sandstone), Lists.newArrayList((Object[])new String[] { "red_sandstone", "chiseled_red_sandstone", "smooth_red_sandstone" }));
/* 334 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.tallgrass), Lists.newArrayList((Object[])new String[] { "dead_bush", "tall_grass", "fern" }));
/* 335 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.deadbush), Lists.newArrayList((Object[])new String[] { "dead_bush" }));
/* 336 */     this.variantNames.put(Item.getItemFromBlock(Blocks.wool), Lists.newArrayList((Object[])new String[] { "black_wool", "red_wool", "green_wool", "brown_wool", "blue_wool", "purple_wool", "cyan_wool", "silver_wool", "gray_wool", "pink_wool", "lime_wool", "yellow_wool", "light_blue_wool", "magenta_wool", "orange_wool", "white_wool" }));
/* 337 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.yellow_flower), Lists.newArrayList((Object[])new String[] { "dandelion" }));
/* 338 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.red_flower), Lists.newArrayList((Object[])new String[] { "poppy", "blue_orchid", "allium", "houstonia", "red_tulip", "orange_tulip", "white_tulip", "pink_tulip", "oxeye_daisy" }));
/* 339 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.stone_slab), Lists.newArrayList((Object[])new String[] { "stone_slab", "sandstone_slab", "cobblestone_slab", "brick_slab", "stone_brick_slab", "nether_brick_slab", "quartz_slab" }));
/* 340 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.stone_slab2), Lists.newArrayList((Object[])new String[] { "red_sandstone_slab" }));
/* 341 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.stained_glass), Lists.newArrayList((Object[])new String[] { "black_stained_glass", "red_stained_glass", "green_stained_glass", "brown_stained_glass", "blue_stained_glass", "purple_stained_glass", "cyan_stained_glass", "silver_stained_glass", "gray_stained_glass", "pink_stained_glass", "lime_stained_glass", "yellow_stained_glass", "light_blue_stained_glass", "magenta_stained_glass", "orange_stained_glass", "white_stained_glass" }));
/* 342 */     this.variantNames.put(Item.getItemFromBlock(Blocks.monster_egg), Lists.newArrayList((Object[])new String[] { "stone_monster_egg", "cobblestone_monster_egg", "stone_brick_monster_egg", "mossy_brick_monster_egg", "cracked_brick_monster_egg", "chiseled_brick_monster_egg" }));
/* 343 */     this.variantNames.put(Item.getItemFromBlock(Blocks.stonebrick), Lists.newArrayList((Object[])new String[] { "stonebrick", "mossy_stonebrick", "cracked_stonebrick", "chiseled_stonebrick" }));
/* 344 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.wooden_slab), Lists.newArrayList((Object[])new String[] { "oak_slab", "spruce_slab", "birch_slab", "jungle_slab", "acacia_slab", "dark_oak_slab" }));
/* 345 */     this.variantNames.put(Item.getItemFromBlock(Blocks.cobblestone_wall), Lists.newArrayList((Object[])new String[] { "cobblestone_wall", "mossy_cobblestone_wall" }));
/* 346 */     this.variantNames.put(Item.getItemFromBlock(Blocks.anvil), Lists.newArrayList((Object[])new String[] { "anvil_intact", "anvil_slightly_damaged", "anvil_very_damaged" }));
/* 347 */     this.variantNames.put(Item.getItemFromBlock(Blocks.quartz_block), Lists.newArrayList((Object[])new String[] { "quartz_block", "chiseled_quartz_block", "quartz_column" }));
/* 348 */     this.variantNames.put(Item.getItemFromBlock(Blocks.stained_hardened_clay), Lists.newArrayList((Object[])new String[] { "black_stained_hardened_clay", "red_stained_hardened_clay", "green_stained_hardened_clay", "brown_stained_hardened_clay", "blue_stained_hardened_clay", "purple_stained_hardened_clay", "cyan_stained_hardened_clay", "silver_stained_hardened_clay", "gray_stained_hardened_clay", "pink_stained_hardened_clay", "lime_stained_hardened_clay", "yellow_stained_hardened_clay", "light_blue_stained_hardened_clay", "magenta_stained_hardened_clay", "orange_stained_hardened_clay", "white_stained_hardened_clay" }));
/* 349 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.stained_glass_pane), Lists.newArrayList((Object[])new String[] { "black_stained_glass_pane", "red_stained_glass_pane", "green_stained_glass_pane", "brown_stained_glass_pane", "blue_stained_glass_pane", "purple_stained_glass_pane", "cyan_stained_glass_pane", "silver_stained_glass_pane", "gray_stained_glass_pane", "pink_stained_glass_pane", "lime_stained_glass_pane", "yellow_stained_glass_pane", "light_blue_stained_glass_pane", "magenta_stained_glass_pane", "orange_stained_glass_pane", "white_stained_glass_pane" }));
/* 350 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.leaves2), Lists.newArrayList((Object[])new String[] { "acacia_leaves", "dark_oak_leaves" }));
/* 351 */     this.variantNames.put(Item.getItemFromBlock(Blocks.log2), Lists.newArrayList((Object[])new String[] { "acacia_log", "dark_oak_log" }));
/* 352 */     this.variantNames.put(Item.getItemFromBlock(Blocks.prismarine), Lists.newArrayList((Object[])new String[] { "prismarine", "prismarine_bricks", "dark_prismarine" }));
/* 353 */     this.variantNames.put(Item.getItemFromBlock(Blocks.carpet), Lists.newArrayList((Object[])new String[] { "black_carpet", "red_carpet", "green_carpet", "brown_carpet", "blue_carpet", "purple_carpet", "cyan_carpet", "silver_carpet", "gray_carpet", "pink_carpet", "lime_carpet", "yellow_carpet", "light_blue_carpet", "magenta_carpet", "orange_carpet", "white_carpet" }));
/* 354 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.double_plant), Lists.newArrayList((Object[])new String[] { "sunflower", "syringa", "double_grass", "double_fern", "double_rose", "paeonia" }));
/* 355 */     this.variantNames.put(Items.bow, Lists.newArrayList((Object[])new String[] { "bow", "bow_pulling_0", "bow_pulling_1", "bow_pulling_2" }));
/* 356 */     this.variantNames.put(Items.coal, Lists.newArrayList((Object[])new String[] { "coal", "charcoal" }));
/* 357 */     this.variantNames.put(Items.fishing_rod, Lists.newArrayList((Object[])new String[] { "fishing_rod", "fishing_rod_cast" }));
/* 358 */     this.variantNames.put(Items.fish, Lists.newArrayList((Object[])new String[] { "cod", "salmon", "clownfish", "pufferfish" }));
/* 359 */     this.variantNames.put(Items.cooked_fish, Lists.newArrayList((Object[])new String[] { "cooked_cod", "cooked_salmon" }));
/* 360 */     this.variantNames.put(Items.dye, Lists.newArrayList((Object[])new String[] { "dye_black", "dye_red", "dye_green", "dye_brown", "dye_blue", "dye_purple", "dye_cyan", "dye_silver", "dye_gray", "dye_pink", "dye_lime", "dye_yellow", "dye_light_blue", "dye_magenta", "dye_orange", "dye_white" }));
/* 361 */     this.variantNames.put(Items.potionitem, Lists.newArrayList((Object[])new String[] { "bottle_drinkable", "bottle_splash" }));
/* 362 */     this.variantNames.put(Items.skull, Lists.newArrayList((Object[])new String[] { "skull_skeleton", "skull_wither", "skull_zombie", "skull_char", "skull_creeper" }));
/* 363 */     this.variantNames.put(Item.getItemFromBlock(Blocks.oak_fence_gate), Lists.newArrayList((Object[])new String[] { "oak_fence_gate" }));
/* 364 */     this.variantNames.put(Item.getItemFromBlock(Blocks.oak_fence), Lists.newArrayList((Object[])new String[] { "oak_fence" }));
/* 365 */     this.variantNames.put(Items.oak_door, Lists.newArrayList((Object[])new String[] { "oak_door" }));
/*     */     
/* 367 */     for (Map.Entry<RegistryDelegate<Item>, Set<String>> entry : customVariantNames.entrySet())
/*     */     {
/* 369 */       this.variantNames.put((Item)((RegistryDelegate)entry.getKey()).get(), Lists.newArrayList(((Set)entry.getValue()).iterator()));
/*     */     }
/*     */ 
/*     */     
/* 373 */     CustomItems.update();
/* 374 */     CustomItems.loadModels(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private List<String> getVariantNames(Item p_177596_1_) {
/* 379 */     List<String> list = this.variantNames.get(p_177596_1_);
/*     */     
/* 381 */     if (list == null)
/*     */     {
/* 383 */       list = Collections.singletonList(((ResourceLocation)Item.itemRegistry.getNameForObject(p_177596_1_)).toString());
/*     */     }
/*     */     
/* 386 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private ResourceLocation getItemLocation(String p_177583_1_) {
/* 391 */     ResourceLocation resourcelocation = new ResourceLocation(p_177583_1_);
/*     */     
/* 393 */     if (Reflector.ForgeHooksClient.exists())
/*     */     {
/* 395 */       resourcelocation = new ResourceLocation(p_177583_1_.replaceAll("#.*", ""));
/*     */     }
/*     */     
/* 398 */     return new ResourceLocation(resourcelocation.getResourceDomain(), "item/" + resourcelocation.getResourcePath());
/*     */   }
/*     */ 
/*     */   
/*     */   private void bakeBlockModels() {
/* 403 */     for (Map.Entry<ModelResourceLocation, ModelBlockDefinition.Variants> entry : this.variants.entrySet()) {
/*     */       
/* 405 */       ModelResourceLocation modelresourcelocation = entry.getKey();
/* 406 */       WeightedBakedModel.Builder weightedbakedmodel$builder = new WeightedBakedModel.Builder();
/* 407 */       int i = 0;
/*     */       
/* 409 */       for (ModelBlockDefinition.Variant modelblockdefinition$variant : ((ModelBlockDefinition.Variants)entry.getValue()).getVariants()) {
/*     */         
/* 411 */         ModelBlock modelblock = this.models.get(modelblockdefinition$variant.getModelLocation());
/*     */         
/* 413 */         if (modelblock != null && modelblock.isResolved()) {
/*     */           
/* 415 */           i++;
/* 416 */           weightedbakedmodel$builder.add(bakeModel(modelblock, modelblockdefinition$variant.getRotation(), modelblockdefinition$variant.isUvLocked()), modelblockdefinition$variant.getWeight());
/*     */           
/*     */           continue;
/*     */         } 
/* 420 */         LOGGER.warn("Missing model for: " + modelresourcelocation);
/*     */       } 
/*     */ 
/*     */       
/* 424 */       if (i == 0) {
/*     */         
/* 426 */         LOGGER.warn("No weighted models for: " + modelresourcelocation); continue;
/*     */       } 
/* 428 */       if (i == 1) {
/*     */         
/* 430 */         this.bakedRegistry.putObject(modelresourcelocation, weightedbakedmodel$builder.first());
/*     */         
/*     */         continue;
/*     */       } 
/* 434 */       this.bakedRegistry.putObject(modelresourcelocation, weightedbakedmodel$builder.build());
/*     */     } 
/*     */ 
/*     */     
/* 438 */     for (Map.Entry<String, ResourceLocation> entry : this.itemLocations.entrySet()) {
/*     */       
/* 440 */       ResourceLocation resourcelocation = entry.getValue();
/* 441 */       ModelResourceLocation modelresourcelocation1 = new ModelResourceLocation(entry.getKey(), "inventory");
/*     */       
/* 443 */       if (Reflector.ModelLoader_getInventoryVariant.exists())
/*     */       {
/* 445 */         modelresourcelocation1 = (ModelResourceLocation)Reflector.call(Reflector.ModelLoader_getInventoryVariant, new Object[] { entry.getKey() });
/*     */       }
/*     */       
/* 448 */       ModelBlock modelblock1 = this.models.get(resourcelocation);
/*     */       
/* 450 */       if (modelblock1 != null && modelblock1.isResolved()) {
/*     */         
/* 452 */         if (isCustomRenderer(modelblock1)) {
/*     */           
/* 454 */           this.bakedRegistry.putObject(modelresourcelocation1, new BuiltInModel(modelblock1.getAllTransforms()));
/*     */           
/*     */           continue;
/*     */         } 
/* 458 */         this.bakedRegistry.putObject(modelresourcelocation1, bakeModel(modelblock1, ModelRotation.X0_Y0, false));
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 463 */       LOGGER.warn("Missing model for: " + resourcelocation);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<ResourceLocation> getVariantsTextureLocations() {
/* 470 */     Set<ResourceLocation> set = Sets.newHashSet();
/* 471 */     List<ModelResourceLocation> list = Lists.newArrayList(this.variants.keySet());
/* 472 */     list.sort(new Comparator<ModelResourceLocation>() {
/*     */           public int compare(ModelResourceLocation p_compare_1_, ModelResourceLocation p_compare_2_) {
/* 474 */             return p_compare_1_.toString().compareTo(p_compare_2_.toString());
/*     */           }
/*     */         });
/*     */     
/* 478 */     for (ModelResourceLocation modelresourcelocation : list) {
/*     */       
/* 480 */       ModelBlockDefinition.Variants modelblockdefinition$variants = this.variants.get(modelresourcelocation);
/*     */       
/* 482 */       for (ModelBlockDefinition.Variant modelblockdefinition$variant : modelblockdefinition$variants.getVariants()) {
/*     */         
/* 484 */         ModelBlock modelblock = this.models.get(modelblockdefinition$variant.getModelLocation());
/*     */         
/* 486 */         if (modelblock == null) {
/*     */           
/* 488 */           LOGGER.warn("Missing model for: " + modelresourcelocation);
/*     */           
/*     */           continue;
/*     */         } 
/* 492 */         set.addAll(getTextureLocations(modelblock));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 497 */     set.addAll(LOCATIONS_BUILTIN_TEXTURES);
/* 498 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBakedModel bakeModel(ModelBlock modelBlockIn, ModelRotation modelRotationIn, boolean uvLocked) {
/* 503 */     return bakeModel(modelBlockIn, modelRotationIn, uvLocked);
/*     */   }
/*     */ 
/*     */   
/*     */   protected IBakedModel bakeModel(ModelBlock p_bakeModel_1_, ITransformation p_bakeModel_2_, boolean p_bakeModel_3_) {
/* 508 */     TextureAtlasSprite textureatlassprite = this.sprites.get(new ResourceLocation(p_bakeModel_1_.resolveTextureName("particle")));
/* 509 */     SimpleBakedModel.Builder simplebakedmodel$builder = (new SimpleBakedModel.Builder(p_bakeModel_1_)).setTexture(textureatlassprite);
/*     */     
/* 511 */     for (BlockPart blockpart : p_bakeModel_1_.getElements()) {
/*     */       
/* 513 */       for (Map.Entry<EnumFacing, BlockPartFace> entry : (Iterable<Map.Entry<EnumFacing, BlockPartFace>>)blockpart.mapFaces.entrySet()) {
/*     */         
/* 515 */         EnumFacing enumfacing = entry.getKey();
/* 516 */         BlockPartFace blockpartface = entry.getValue();
/* 517 */         TextureAtlasSprite textureatlassprite1 = this.sprites.get(new ResourceLocation(p_bakeModel_1_.resolveTextureName(blockpartface.texture)));
/* 518 */         boolean flag = true;
/*     */         
/* 520 */         if (Reflector.ForgeHooksClient.exists())
/*     */         {
/* 522 */           flag = TRSRTransformation.isInteger();
/*     */         }
/*     */         
/* 525 */         if (blockpartface.cullFace != null && flag) {
/*     */           
/* 527 */           simplebakedmodel$builder.addFaceQuad(p_bakeModel_2_.rotate(blockpartface.cullFace), makeBakedQuad(blockpart, blockpartface, textureatlassprite1, enumfacing, p_bakeModel_2_, p_bakeModel_3_));
/*     */           
/*     */           continue;
/*     */         } 
/* 531 */         simplebakedmodel$builder.addGeneralQuad(makeBakedQuad(blockpart, blockpartface, textureatlassprite1, enumfacing, p_bakeModel_2_, p_bakeModel_3_));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 536 */     return simplebakedmodel$builder.makeBakedModel();
/*     */   }
/*     */ 
/*     */   
/*     */   private BakedQuad makeBakedQuad(BlockPart p_177589_1_, BlockPartFace p_177589_2_, TextureAtlasSprite p_177589_3_, EnumFacing p_177589_4_, ModelRotation p_177589_5_, boolean p_177589_6_) {
/* 541 */     return Reflector.ForgeHooksClient.exists() ? makeBakedQuad(p_177589_1_, p_177589_2_, p_177589_3_, p_177589_4_, p_177589_5_, p_177589_6_) : this.faceBakery.makeBakedQuad(p_177589_1_.positionFrom, p_177589_1_.positionTo, p_177589_2_, p_177589_3_, p_177589_4_, p_177589_5_, p_177589_1_.partRotation, p_177589_6_, p_177589_1_.shade);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BakedQuad makeBakedQuad(BlockPart p_makeBakedQuad_1_, BlockPartFace p_makeBakedQuad_2_, TextureAtlasSprite p_makeBakedQuad_3_, EnumFacing p_makeBakedQuad_4_, ITransformation p_makeBakedQuad_5_, boolean p_makeBakedQuad_6_) {
/* 546 */     return this.faceBakery.makeBakedQuad(p_makeBakedQuad_1_.positionFrom, p_makeBakedQuad_1_.positionTo, p_makeBakedQuad_2_, p_makeBakedQuad_3_, p_makeBakedQuad_4_, p_makeBakedQuad_5_, p_makeBakedQuad_1_.partRotation, p_makeBakedQuad_6_, p_makeBakedQuad_1_.shade);
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadModelsCheck() {
/* 551 */     loadModels();
/*     */     
/* 553 */     for (ModelBlock modelblock : this.models.values())
/*     */     {
/* 555 */       modelblock.getParentFromMap(this.models);
/*     */     }
/*     */     
/* 558 */     ModelBlock.checkModelHierarchy(this.models);
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadModels() {
/* 563 */     Deque<ResourceLocation> deque = Queues.newArrayDeque();
/* 564 */     Set<ResourceLocation> set = Sets.newHashSet();
/*     */     
/* 566 */     for (Map.Entry<ResourceLocation, ModelBlock> entry : this.models.entrySet()) {
/*     */       
/* 568 */       set.add(entry.getKey());
/* 569 */       ResourceLocation resourcelocation1 = ((ModelBlock)entry.getValue()).getParentLocation();
/*     */       
/* 571 */       if (resourcelocation1 != null)
/*     */       {
/* 573 */         deque.add(resourcelocation1);
/*     */       }
/*     */     } 
/*     */     
/* 577 */     while (!deque.isEmpty()) {
/*     */       
/* 579 */       ResourceLocation resourcelocation2 = deque.pop();
/*     */ 
/*     */       
/*     */       try {
/* 583 */         if (this.models.get(resourcelocation2) != null) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 588 */         ModelBlock modelblock = loadModel(resourcelocation2);
/* 589 */         this.models.put(resourcelocation2, modelblock);
/* 590 */         ResourceLocation resourcelocation3 = modelblock.getParentLocation();
/*     */         
/* 592 */         if (resourcelocation3 != null && !set.contains(resourcelocation3))
/*     */         {
/* 594 */           deque.add(resourcelocation3);
/*     */         }
/*     */       }
/* 597 */       catch (Exception var6) {
/*     */         
/* 599 */         LOGGER.warn("In parent chain: " + JOINER.join(getParentPath(resourcelocation2)) + "; unable to load model: '" + resourcelocation2 + "'");
/*     */       } 
/*     */       
/* 602 */       set.add(resourcelocation2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private List<ResourceLocation> getParentPath(ResourceLocation p_177573_1_) {
/* 608 */     List<ResourceLocation> list = Lists.newArrayList((Object[])new ResourceLocation[] { p_177573_1_ });
/* 609 */     ResourceLocation resourcelocation = p_177573_1_;
/*     */     
/* 611 */     while ((resourcelocation = getParentLocation(resourcelocation)) != null)
/*     */     {
/* 613 */       list.add(0, resourcelocation);
/*     */     }
/*     */     
/* 616 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private ResourceLocation getParentLocation(ResourceLocation p_177576_1_) {
/* 621 */     for (Map.Entry<ResourceLocation, ModelBlock> entry : this.models.entrySet()) {
/*     */       
/* 623 */       ModelBlock modelblock = entry.getValue();
/*     */       
/* 625 */       if (modelblock != null && p_177576_1_.equals(modelblock.getParentLocation()))
/*     */       {
/* 627 */         return entry.getKey();
/*     */       }
/*     */     } 
/*     */     
/* 631 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<ResourceLocation> getTextureLocations(ModelBlock p_177585_1_) {
/* 636 */     Set<ResourceLocation> set = Sets.newHashSet();
/*     */     
/* 638 */     for (BlockPart blockpart : p_177585_1_.getElements()) {
/*     */       
/* 640 */       for (BlockPartFace blockpartface : blockpart.mapFaces.values()) {
/*     */         
/* 642 */         ResourceLocation resourcelocation = new ResourceLocation(p_177585_1_.resolveTextureName(blockpartface.texture));
/* 643 */         set.add(resourcelocation);
/*     */       } 
/*     */     } 
/*     */     
/* 647 */     set.add(new ResourceLocation(p_177585_1_.resolveTextureName("particle")));
/* 648 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadSprites() {
/* 653 */     final Set<ResourceLocation> set = getVariantsTextureLocations();
/* 654 */     set.addAll(getItemsTextureLocations());
/* 655 */     set.remove(TextureMap.LOCATION_MISSING_TEXTURE);
/* 656 */     IIconCreator iiconcreator = new IIconCreator()
/*     */       {
/*     */         public void registerSprites(TextureMap iconRegistry)
/*     */         {
/* 660 */           for (ResourceLocation resourcelocation : set) {
/*     */             
/* 662 */             TextureAtlasSprite textureatlassprite = iconRegistry.registerSprite(resourcelocation);
/* 663 */             ModelBakery.this.sprites.put(resourcelocation, textureatlassprite);
/*     */           } 
/*     */         }
/*     */       };
/* 667 */     this.textureMap.loadSprites(this.resourceManager, iiconcreator);
/* 668 */     this.sprites.put(new ResourceLocation("missingno"), this.textureMap.getMissingSprite());
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<ResourceLocation> getItemsTextureLocations() {
/* 673 */     Set<ResourceLocation> set = Sets.newHashSet();
/*     */     
/* 675 */     for (ResourceLocation resourcelocation : this.itemLocations.values()) {
/*     */       
/* 677 */       ModelBlock modelblock = this.models.get(resourcelocation);
/*     */       
/* 679 */       if (modelblock != null) {
/*     */         
/* 681 */         set.add(new ResourceLocation(modelblock.resolveTextureName("particle")));
/*     */         
/* 683 */         if (hasItemModel(modelblock)) {
/*     */           
/* 685 */           for (String s : ItemModelGenerator.LAYERS) {
/*     */             
/* 687 */             ResourceLocation resourcelocation2 = new ResourceLocation(modelblock.resolveTextureName(s));
/*     */             
/* 689 */             if (modelblock.getRootModel() == MODEL_COMPASS && !TextureMap.LOCATION_MISSING_TEXTURE.equals(resourcelocation2)) {
/*     */               
/* 691 */               TextureAtlasSprite.setLocationNameCompass(resourcelocation2.toString());
/*     */             }
/* 693 */             else if (modelblock.getRootModel() == MODEL_CLOCK && !TextureMap.LOCATION_MISSING_TEXTURE.equals(resourcelocation2)) {
/*     */               
/* 695 */               TextureAtlasSprite.setLocationNameClock(resourcelocation2.toString());
/*     */             } 
/*     */             
/* 698 */             set.add(resourcelocation2);
/*     */           }  continue;
/*     */         } 
/* 701 */         if (!isCustomRenderer(modelblock))
/*     */         {
/* 703 */           for (BlockPart blockpart : modelblock.getElements()) {
/*     */             
/* 705 */             for (BlockPartFace blockpartface : blockpart.mapFaces.values()) {
/*     */               
/* 707 */               ResourceLocation resourcelocation1 = new ResourceLocation(modelblock.resolveTextureName(blockpartface.texture));
/* 708 */               set.add(resourcelocation1);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 715 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasItemModel(ModelBlock p_177581_1_) {
/* 720 */     if (p_177581_1_ == null)
/*     */     {
/* 722 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 726 */     ModelBlock modelblock = p_177581_1_.getRootModel();
/* 727 */     return (modelblock == MODEL_GENERATED || modelblock == MODEL_COMPASS || modelblock == MODEL_CLOCK);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isCustomRenderer(ModelBlock p_177587_1_) {
/* 733 */     if (p_177587_1_ == null)
/*     */     {
/* 735 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 739 */     ModelBlock modelblock = p_177587_1_.getRootModel();
/* 740 */     return (modelblock == MODEL_ENTITY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void bakeItemModels() {
/* 746 */     for (ResourceLocation resourcelocation : this.itemLocations.values()) {
/*     */       
/* 748 */       ModelBlock modelblock = this.models.get(resourcelocation);
/*     */       
/* 750 */       if (hasItemModel(modelblock)) {
/*     */         
/* 752 */         ModelBlock modelblock1 = makeItemModel(modelblock);
/*     */         
/* 754 */         if (modelblock1 != null)
/*     */         {
/* 756 */           modelblock1.name = resourcelocation.toString();
/*     */         }
/*     */         
/* 759 */         this.models.put(resourcelocation, modelblock1); continue;
/*     */       } 
/* 761 */       if (isCustomRenderer(modelblock))
/*     */       {
/* 763 */         this.models.put(resourcelocation, modelblock);
/*     */       }
/*     */     } 
/*     */     
/* 767 */     for (TextureAtlasSprite textureatlassprite : this.sprites.values()) {
/*     */       
/* 769 */       if (!textureatlassprite.hasAnimationMetadata())
/*     */       {
/* 771 */         textureatlassprite.clearFramesTextureData();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ModelBlock makeItemModel(ModelBlock p_177582_1_) {
/* 778 */     return this.itemModelGenerator.makeItemModel(this.textureMap, p_177582_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBlock getModelBlock(ResourceLocation p_getModelBlock_1_) {
/* 783 */     ModelBlock modelblock = this.models.get(p_getModelBlock_1_);
/* 784 */     return modelblock;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void fixModelLocations(ModelBlock p_fixModelLocations_0_, String p_fixModelLocations_1_) {
/* 789 */     ResourceLocation resourcelocation = fixModelLocation(p_fixModelLocations_0_.getParentLocation(), p_fixModelLocations_1_);
/*     */     
/* 791 */     if (resourcelocation != p_fixModelLocations_0_.getParentLocation())
/*     */     {
/* 793 */       Reflector.setFieldValue(p_fixModelLocations_0_, Reflector.ModelBlock_parentLocation, resourcelocation);
/*     */     }
/*     */     
/* 796 */     Map<String, String> map = (Map<String, String>)Reflector.getFieldValue(p_fixModelLocations_0_, Reflector.ModelBlock_textures);
/*     */     
/* 798 */     if (map != null)
/*     */     {
/* 800 */       for (Map.Entry<String, String> entry : map.entrySet()) {
/*     */         
/* 802 */         String s = entry.getValue();
/* 803 */         String s1 = fixResourcePath(s, p_fixModelLocations_1_);
/*     */         
/* 805 */         if (s1 != s)
/*     */         {
/* 807 */           entry.setValue(s1);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static ResourceLocation fixModelLocation(ResourceLocation p_fixModelLocation_0_, String p_fixModelLocation_1_) {
/* 815 */     if (p_fixModelLocation_0_ != null && p_fixModelLocation_1_ != null) {
/*     */       
/* 817 */       if (!p_fixModelLocation_0_.getResourceDomain().equals("minecraft"))
/*     */       {
/* 819 */         return p_fixModelLocation_0_;
/*     */       }
/*     */ 
/*     */       
/* 823 */       String s = p_fixModelLocation_0_.getResourcePath();
/* 824 */       String s1 = fixResourcePath(s, p_fixModelLocation_1_);
/*     */       
/* 826 */       if (s1 != s)
/*     */       {
/* 828 */         p_fixModelLocation_0_ = new ResourceLocation(p_fixModelLocation_0_.getResourceDomain(), s1);
/*     */       }
/*     */       
/* 831 */       return p_fixModelLocation_0_;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 836 */     return p_fixModelLocation_0_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String fixResourcePath(String p_fixResourcePath_0_, String p_fixResourcePath_1_) {
/* 842 */     p_fixResourcePath_0_ = TextureUtils.fixResourcePath(p_fixResourcePath_0_, p_fixResourcePath_1_);
/* 843 */     p_fixResourcePath_0_ = StrUtils.removeSuffix(p_fixResourcePath_0_, ".json");
/* 844 */     p_fixResourcePath_0_ = StrUtils.removeSuffix(p_fixResourcePath_0_, ".png");
/* 845 */     return p_fixResourcePath_0_;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void addVariantName(Item p_addVariantName_0_, String... p_addVariantName_1_) {
/* 851 */     RegistryDelegate<Item> registrydelegate = (RegistryDelegate)Reflector.getFieldValue(p_addVariantName_0_, Reflector.ForgeItem_delegate);
/*     */     
/* 853 */     if (customVariantNames.containsKey(registrydelegate)) {
/*     */       
/* 855 */       ((Set)customVariantNames.get(registrydelegate)).addAll(Lists.newArrayList((Object[])p_addVariantName_1_));
/*     */     }
/*     */     else {
/*     */       
/* 859 */       customVariantNames.put(registrydelegate, Sets.newHashSet((Object[])p_addVariantName_1_));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T extends ResourceLocation> void registerItemVariants(Item p_registerItemVariants_0_, T... p_registerItemVariants_1_) {
/* 865 */     RegistryDelegate<Item> registrydelegate = (RegistryDelegate)Reflector.getFieldValue(p_registerItemVariants_0_, Reflector.ForgeItem_delegate);
/*     */     
/* 867 */     if (!customVariantNames.containsKey(registrydelegate))
/*     */     {
/* 869 */       customVariantNames.put(registrydelegate, Sets.newHashSet());
/*     */     }
/*     */     
/* 872 */     for (T t : p_registerItemVariants_1_)
/*     */     {
/* 874 */       ((Set<String>)customVariantNames.get(registrydelegate)).add(t.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 880 */     BUILT_IN_MODELS.put("missing", "{ \"textures\": {   \"particle\": \"missingno\",   \"missingno\": \"missingno\"}, \"elements\": [ {     \"from\": [ 0, 0, 0 ],     \"to\": [ 16, 16, 16 ],     \"faces\": {         \"down\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"down\", \"texture\": \"#missingno\" },         \"up\":    { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"up\", \"texture\": \"#missingno\" },         \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"north\", \"texture\": \"#missingno\" },         \"south\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"south\", \"texture\": \"#missingno\" },         \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"west\", \"texture\": \"#missingno\" },         \"east\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"east\", \"texture\": \"#missingno\" }    }}]}");
/* 881 */     MODEL_GENERATED.name = "generation marker";
/* 882 */     MODEL_COMPASS.name = "compass generation marker";
/* 883 */     MODEL_CLOCK.name = "class generation marker";
/* 884 */     MODEL_ENTITY.name = "block entity marker";
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\model\ModelBakery.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */