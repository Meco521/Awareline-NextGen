/*      */ package net.minecraft.client.renderer.entity;
/*      */ import java.util.concurrent.Callable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockDoublePlant;
/*      */ import net.minecraft.block.BlockFlower;
/*      */ import net.minecraft.block.BlockPlanks;
/*      */ import net.minecraft.block.BlockSilverfish;
/*      */ import net.minecraft.block.BlockStone;
/*      */ import net.minecraft.block.BlockStoneBrick;
/*      */ import net.minecraft.block.BlockStoneSlab;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.gui.FontRenderer;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.ItemMeshDefinition;
/*      */ import net.minecraft.client.renderer.ItemModelMesher;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*      */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*      */ import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.resources.model.IBakedModel;
/*      */ import net.minecraft.client.resources.model.ModelManager;
/*      */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.EnumDyeColor;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemFishFood;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.Vec3i;
/*      */ import net.optifine.CustomItems;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.shaders.ShadersRender;
/*      */ 
/*      */ public class RenderItem implements IResourceManagerReloadListener {
/*   43 */   private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
/*      */   
/*      */   private final ItemModelMesher itemModelMesher;
/*      */   
/*      */   private final TextureManager textureManager;
/*      */   
/*      */   public float zLevel;
/*   50 */   public ModelManager modelManager = null;
/*      */ 
/*      */   
/*      */   private boolean notRenderingEffectsInGUI = true;
/*      */   
/*   55 */   private ModelResourceLocation modelLocation = null;
/*      */   private boolean renderItemGui = false;
/*      */   private boolean renderModelHasEmissive = false;
/*      */   private boolean renderModelEmissive = false;
/*      */   
/*      */   public RenderItem(TextureManager textureManager, ModelManager modelManager) {
/*   61 */     this.textureManager = textureManager;
/*   62 */     this.modelManager = modelManager;
/*      */     
/*   64 */     if (Reflector.ItemModelMesherForge_Constructor.exists()) {
/*   65 */       this.itemModelMesher = (ItemModelMesher)Reflector.newInstance(Reflector.ItemModelMesherForge_Constructor, new Object[] { modelManager });
/*      */     } else {
/*   67 */       this.itemModelMesher = new ItemModelMesher(modelManager);
/*      */     } 
/*      */     
/*   70 */     registerItems();
/*      */   }
/*      */   
/*      */   public static void forgeHooksClient_putQuadColor(WorldRenderer p_forgeHooksClient_putQuadColor_0_, BakedQuad p_forgeHooksClient_putQuadColor_1_, int p_forgeHooksClient_putQuadColor_2_) {
/*   74 */     float f = (p_forgeHooksClient_putQuadColor_2_ & 0xFF);
/*   75 */     float f1 = (p_forgeHooksClient_putQuadColor_2_ >>> 8 & 0xFF);
/*   76 */     float f2 = (p_forgeHooksClient_putQuadColor_2_ >>> 16 & 0xFF);
/*   77 */     float f3 = (p_forgeHooksClient_putQuadColor_2_ >>> 24 & 0xFF);
/*   78 */     int[] aint = p_forgeHooksClient_putQuadColor_1_.getVertexData();
/*   79 */     int i = aint.length / 4;
/*      */     
/*   81 */     for (int j = 0; j < 4; j++) {
/*   82 */       int k = aint[3 + i * j];
/*   83 */       float f4 = (k & 0xFF);
/*   84 */       float f5 = (k >>> 8 & 0xFF);
/*   85 */       float f6 = (k >>> 16 & 0xFF);
/*   86 */       float f7 = (k >>> 24 & 0xFF);
/*   87 */       int l = Math.min(255, (int)(f * f4 / 255.0F));
/*   88 */       int i1 = Math.min(255, (int)(f1 * f5 / 255.0F));
/*   89 */       int j1 = Math.min(255, (int)(f2 * f6 / 255.0F));
/*   90 */       int k1 = Math.min(255, (int)(f3 * f7 / 255.0F));
/*   91 */       p_forgeHooksClient_putQuadColor_0_.putColorRGBA(p_forgeHooksClient_putQuadColor_0_.getColorIndex(4 - j), l, i1, j1, k1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void isNotRenderingEffectsInGUI(boolean isNot) {
/*  101 */     this.notRenderingEffectsInGUI = isNot;
/*      */   }
/*      */   
/*      */   public ItemModelMesher getItemModelMesher() {
/*  105 */     return this.itemModelMesher;
/*      */   }
/*      */   
/*      */   protected void registerItem(Item itm, int subType, String identifier) {
/*  109 */     this.itemModelMesher.register(itm, subType, new ModelResourceLocation(identifier, "inventory"));
/*      */   }
/*      */   
/*      */   protected void registerBlock(Block blk, int subType, String identifier) {
/*  113 */     registerItem(Item.getItemFromBlock(blk), subType, identifier);
/*      */   }
/*      */   
/*      */   private void registerBlock(Block blk, String identifier) {
/*  117 */     registerBlock(blk, 0, identifier);
/*      */   }
/*      */   
/*      */   private void registerItem(Item itm, String identifier) {
/*  121 */     registerItem(itm, 0, identifier);
/*      */   }
/*      */   
/*      */   private void renderModel(IBakedModel model, ItemStack stack) {
/*  125 */     renderModel(model, -1, stack);
/*      */   }
/*      */   
/*      */   public void renderModel(IBakedModel model, int color) {
/*  129 */     renderModel(model, color, (ItemStack)null);
/*      */   }
/*      */   
/*      */   private void renderModel(IBakedModel model, int color, ItemStack stack) {
/*  133 */     Tessellator tessellator = Tessellator.getInstance();
/*  134 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  135 */     boolean flag = Minecraft.getMinecraft().getTextureMapBlocks().isTextureBound();
/*  136 */     boolean flag1 = (Config.isMultiTexture() && flag);
/*      */     
/*  138 */     if (flag1) {
/*  139 */       worldrenderer.setBlockLayer(EnumWorldBlockLayer.SOLID);
/*      */     }
/*      */     
/*  142 */     worldrenderer.begin(7, DefaultVertexFormats.ITEM);
/*      */     
/*  144 */     for (EnumFacing enumfacing : EnumFacing.VALUES) {
/*  145 */       renderQuads(worldrenderer, model.getFaceQuads(enumfacing), color, stack);
/*      */     }
/*      */     
/*  148 */     renderQuads(worldrenderer, model.getGeneralQuads(), color, stack);
/*  149 */     tessellator.draw();
/*      */     
/*  151 */     if (flag1) {
/*  152 */       worldrenderer.setBlockLayer((EnumWorldBlockLayer)null);
/*  153 */       GlStateManager.bindCurrentTexture();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renderItem(ItemStack stack, IBakedModel model) {
/*  158 */     if (stack != null) {
/*  159 */       GlStateManager.pushMatrix();
/*  160 */       GlStateManager.scale(0.5F, 0.5F, 0.5F);
/*      */       
/*  162 */       if (model.isBuiltInRenderer()) {
/*  163 */         GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*  164 */         GlStateManager.translate(-0.5F, -0.5F, -0.5F);
/*  165 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  166 */         GlStateManager.enableRescaleNormal();
/*  167 */         TileEntityItemStackRenderer.instance.renderByItem(stack);
/*      */       } else {
/*  169 */         GlStateManager.translate(-0.5F, -0.5F, -0.5F);
/*      */         
/*  171 */         if (Config.isCustomItems()) {
/*  172 */           model = CustomItems.getCustomItemModel(stack, model, (ResourceLocation)this.modelLocation, false);
/*      */         }
/*      */         
/*  175 */         this.renderModelHasEmissive = false;
/*  176 */         renderModel(model, stack);
/*      */         
/*  178 */         if (this.renderModelHasEmissive) {
/*  179 */           float f = OpenGlHelper.lastBrightnessX;
/*  180 */           float f1 = OpenGlHelper.lastBrightnessY;
/*  181 */           OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, f1);
/*  182 */           this.renderModelEmissive = true;
/*  183 */           renderModel(model, stack);
/*  184 */           this.renderModelEmissive = false;
/*  185 */           OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
/*      */         } 
/*      */         
/*  188 */         if (stack.hasEffect() && (!Config.isCustomItems() || !CustomItems.renderCustomEffect(this, stack, model))) {
/*  189 */           renderEffect(model);
/*      */         }
/*      */       } 
/*      */       
/*  193 */       GlStateManager.popMatrix();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void renderEffect(IBakedModel model) {
/*  198 */     if (EnchantEffect.getInstance.isEnabled()) {
/*  199 */       if ((!Config.isCustomItems() || CustomItems.isUseGlint()) && (!Config.isShaders() || !Shaders.isShadowPass)) {
/*  200 */         GlStateManager.depthMask(false);
/*  201 */         GlStateManager.depthFunc(514);
/*  202 */         GlStateManager.disableLighting();
/*  203 */         GlStateManager.blendFunc(768, 1);
/*  204 */         this.textureManager.bindTexture(RES_ITEM_GLINT);
/*  205 */         if (Config.isShaders() && !this.renderItemGui) {
/*  206 */           ShadersRender.renderEnchantedGlintBegin();
/*      */         }
/*  208 */         GlStateManager.matrixMode(5890);
/*  209 */         GlStateManager.pushMatrix();
/*  210 */         GlStateManager.scale(8.0F, 8.0F, 8.0F);
/*  211 */         float f = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
/*  212 */         GlStateManager.translate(f, 0.0F, 0.0F);
/*  213 */         GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
/*  214 */         renderModel(model, EnchantEffect.getInstance.getEnchantColor().getRGB());
/*  215 */         GlStateManager.popMatrix();
/*  216 */         GlStateManager.pushMatrix();
/*  217 */         GlStateManager.scale(8.0F, 8.0F, 8.0F);
/*  218 */         float f1 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F / 8.0F;
/*  219 */         GlStateManager.translate(-f1, 0.0F, 0.0F);
/*  220 */         GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
/*  221 */         renderModel(model, EnchantEffect.getInstance.getEnchantColor().getRGB());
/*  222 */         GlStateManager.popMatrix();
/*  223 */         GlStateManager.matrixMode(5888);
/*  224 */         GlStateManager.blendFunc(770, 771);
/*  225 */         GlStateManager.enableLighting();
/*  226 */         GlStateManager.depthFunc(515);
/*  227 */         GlStateManager.depthMask(true);
/*  228 */         this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
/*  229 */         if (Config.isShaders() && !this.renderItemGui) {
/*  230 */           ShadersRender.renderEnchantedGlintEnd();
/*      */         }
/*      */       }
/*      */     
/*  234 */     } else if ((!Config.isCustomItems() || CustomItems.isUseGlint()) && (
/*  235 */       !Config.isShaders() || !Shaders.isShadowPass)) {
/*  236 */       GlStateManager.depthMask(false);
/*  237 */       GlStateManager.depthFunc(514);
/*  238 */       GlStateManager.disableLighting();
/*  239 */       GlStateManager.blendFunc(768, 1);
/*  240 */       this.textureManager.bindTexture(RES_ITEM_GLINT);
/*      */       
/*  242 */       if (Config.isShaders() && !this.renderItemGui) {
/*  243 */         ShadersRender.renderEnchantedGlintBegin();
/*      */       }
/*      */       
/*  246 */       GlStateManager.matrixMode(5890);
/*  247 */       GlStateManager.pushMatrix();
/*  248 */       GlStateManager.scale(8.0F, 8.0F, 8.0F);
/*  249 */       float f = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
/*  250 */       GlStateManager.translate(f, 0.0F, 0.0F);
/*  251 */       GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
/*  252 */       renderModel(model, -8372020);
/*  253 */       GlStateManager.popMatrix();
/*  254 */       GlStateManager.pushMatrix();
/*  255 */       GlStateManager.scale(8.0F, 8.0F, 8.0F);
/*  256 */       float f1 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F / 8.0F;
/*  257 */       GlStateManager.translate(-f1, 0.0F, 0.0F);
/*  258 */       GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
/*  259 */       renderModel(model, -8372020);
/*  260 */       GlStateManager.popMatrix();
/*  261 */       GlStateManager.matrixMode(5888);
/*  262 */       GlStateManager.blendFunc(770, 771);
/*  263 */       GlStateManager.enableLighting();
/*  264 */       GlStateManager.depthFunc(515);
/*  265 */       GlStateManager.depthMask(true);
/*  266 */       this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
/*      */       
/*  268 */       if (Config.isShaders() && !this.renderItemGui) {
/*  269 */         ShadersRender.renderEnchantedGlintEnd();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void putQuadNormal(WorldRenderer renderer, BakedQuad quad) {
/*  277 */     Vec3i vec3i = quad.getFace().getDirectionVec();
/*  278 */     renderer.putNormal(vec3i.getX(), vec3i.getY(), vec3i.getZ());
/*      */   }
/*      */   
/*      */   private void renderQuad(WorldRenderer renderer, BakedQuad quad, int color) {
/*  282 */     if (this.renderModelEmissive) {
/*  283 */       if (quad.getQuadEmissive() == null) {
/*      */         return;
/*      */       }
/*      */       
/*  287 */       quad = quad.getQuadEmissive();
/*  288 */     } else if (quad.getQuadEmissive() != null) {
/*  289 */       this.renderModelHasEmissive = true;
/*      */     } 
/*      */     
/*  292 */     if (renderer.isMultiTexture()) {
/*  293 */       renderer.addVertexData(quad.getVertexDataSingle());
/*      */     } else {
/*  295 */       renderer.addVertexData(quad.getVertexData());
/*      */     } 
/*      */     
/*  298 */     renderer.putSprite(quad.getSprite());
/*      */     
/*  300 */     if (Reflector.IColoredBakedQuad.exists() && Reflector.IColoredBakedQuad.isInstance(quad)) {
/*  301 */       forgeHooksClient_putQuadColor(renderer, quad, color);
/*      */     } else {
/*  303 */       renderer.putColor4(color);
/*      */     } 
/*      */     
/*  306 */     putQuadNormal(renderer, quad);
/*      */   }
/*      */   
/*      */   private void renderQuads(WorldRenderer renderer, List<BakedQuad> quads, int color, ItemStack stack) {
/*  310 */     boolean flag = (color == -1 && stack != null);
/*  311 */     int i = 0;
/*      */     
/*  313 */     for (int j = quads.size(); i < j; i++) {
/*  314 */       BakedQuad bakedquad = quads.get(i);
/*  315 */       int k = color;
/*      */       
/*  317 */       if (flag && bakedquad.hasTintIndex()) {
/*  318 */         k = stack.getItem().getColorFromItemStack(stack, bakedquad.getTintIndex());
/*      */         
/*  320 */         if (Config.isCustomColors()) {
/*  321 */           k = CustomColors.getColorFromItemStack(stack, bakedquad.getTintIndex(), k);
/*      */         }
/*      */         
/*  324 */         if (EntityRenderer.anaglyphEnable) {
/*  325 */           k = TextureUtil.anaglyphColor(k);
/*      */         }
/*      */         
/*  328 */         k |= 0xFF000000;
/*      */       } 
/*      */       
/*  331 */       renderQuad(renderer, bakedquad, k);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean shouldRenderItemIn3D(ItemStack stack) {
/*  336 */     IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(stack);
/*  337 */     return (ibakedmodel == null) ? false : ibakedmodel.isGui3d();
/*      */   }
/*      */   
/*      */   private void preTransform(ItemStack stack) {
/*  341 */     IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(stack);
/*  342 */     Item item = stack.getItem();
/*      */     
/*  344 */     if (item != null) {
/*  345 */       boolean flag = ibakedmodel.isGui3d();
/*      */       
/*  347 */       if (!flag) {
/*  348 */         GlStateManager.scale(2.0F, 2.0F, 2.0F);
/*      */       }
/*      */       
/*  351 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renderItem(ItemStack stack, ItemCameraTransforms.TransformType cameraTransformType) {
/*  356 */     if (stack != null) {
/*  357 */       IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(stack);
/*  358 */       renderItemModelTransform(stack, ibakedmodel, cameraTransformType);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renderItemModelForEntity(ItemStack stack, EntityLivingBase entityToRenderFor, ItemCameraTransforms.TransformType cameraTransformType) {
/*  363 */     if (stack != null && entityToRenderFor != null) {
/*  364 */       IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(stack);
/*      */       
/*  366 */       if (entityToRenderFor instanceof EntityPlayer) {
/*  367 */         EntityPlayer entityplayer = (EntityPlayer)entityToRenderFor;
/*  368 */         Item item = stack.getItem();
/*  369 */         ModelResourceLocation modelresourcelocation = null;
/*      */         
/*  371 */         if (item == Items.fishing_rod && entityplayer.fishEntity != null) {
/*  372 */           modelresourcelocation = new ModelResourceLocation("fishing_rod_cast", "inventory");
/*  373 */         } else if (item == Items.bow && entityplayer.getItemInUse() != null) {
/*  374 */           int i = stack.getMaxItemUseDuration() - entityplayer.getItemInUseCount();
/*      */           
/*  376 */           if (i >= 18) {
/*  377 */             modelresourcelocation = new ModelResourceLocation("bow_pulling_2", "inventory");
/*  378 */           } else if (i > 13) {
/*  379 */             modelresourcelocation = new ModelResourceLocation("bow_pulling_1", "inventory");
/*  380 */           } else if (i > 0) {
/*  381 */             modelresourcelocation = new ModelResourceLocation("bow_pulling_0", "inventory");
/*      */           } 
/*  383 */         } else if (Reflector.ForgeItem_getModel.exists()) {
/*  384 */           modelresourcelocation = (ModelResourceLocation)Reflector.call(item, Reflector.ForgeItem_getModel, new Object[] { stack, entityplayer, Integer.valueOf(entityplayer.getItemInUseCount()) });
/*      */         } 
/*      */         
/*  387 */         if (modelresourcelocation != null) {
/*  388 */           ibakedmodel = this.itemModelMesher.getModelManager().getModel(modelresourcelocation);
/*  389 */           this.modelLocation = modelresourcelocation;
/*      */         } 
/*      */       } 
/*      */       
/*  393 */       renderItemModelTransform(stack, ibakedmodel, cameraTransformType);
/*  394 */       this.modelLocation = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void renderItemModelTransform(ItemStack stack, IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType) {
/*  399 */     this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
/*  400 */     this.textureManager.getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
/*  401 */     preTransform(stack);
/*  402 */     GlStateManager.enableRescaleNormal();
/*  403 */     GlStateManager.alphaFunc(516, 0.1F);
/*  404 */     GlStateManager.enableBlend();
/*  405 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  406 */     GlStateManager.pushMatrix();
/*      */     
/*  408 */     if (Reflector.ForgeHooksClient_handleCameraTransforms.exists()) {
/*  409 */       model = (IBakedModel)Reflector.call(Reflector.ForgeHooksClient_handleCameraTransforms, new Object[] { model, cameraTransformType });
/*      */     } else {
/*  411 */       ItemCameraTransforms itemcameratransforms = model.getItemCameraTransforms();
/*  412 */       itemcameratransforms.applyTransform(cameraTransformType);
/*      */       
/*  414 */       if (isThereOneNegativeScale(itemcameratransforms.getTransform(cameraTransformType))) {
/*  415 */         GlStateManager.cullFace(1028);
/*      */       }
/*      */     } 
/*      */     
/*  419 */     renderItem(stack, model);
/*  420 */     GlStateManager.cullFace(1029);
/*  421 */     GlStateManager.popMatrix();
/*  422 */     GlStateManager.disableRescaleNormal();
/*  423 */     GlStateManager.disableBlend();
/*  424 */     this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
/*  425 */     this.textureManager.getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isThereOneNegativeScale(ItemTransformVec3f itemTranformVec) {
/*  434 */     return ((itemTranformVec.scale.x < 0.0F)) ^ ((itemTranformVec.scale.y < 0.0F)) ^ ((itemTranformVec.scale.z < 0.0F) ? 1 : 0);
/*      */   }
/*      */   
/*      */   public void renderItemIntoGUI(ItemStack stack, int x, int y) {
/*  438 */     this.renderItemGui = true;
/*  439 */     IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(stack);
/*  440 */     GlStateManager.pushMatrix();
/*  441 */     this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
/*  442 */     this.textureManager.getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
/*  443 */     GlStateManager.enableRescaleNormal();
/*  444 */     GlStateManager.enableAlpha();
/*  445 */     GlStateManager.alphaFunc(516, 0.1F);
/*  446 */     GlStateManager.enableBlend();
/*  447 */     GlStateManager.blendFunc(770, 771);
/*  448 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  449 */     setupGuiTransform(x, y, ibakedmodel.isGui3d());
/*      */     
/*  451 */     if (Reflector.ForgeHooksClient_handleCameraTransforms.exists()) {
/*  452 */       ibakedmodel = (IBakedModel)Reflector.call(Reflector.ForgeHooksClient_handleCameraTransforms, new Object[] { ibakedmodel, ItemCameraTransforms.TransformType.GUI });
/*      */     } else {
/*  454 */       ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GUI);
/*      */     } 
/*      */     
/*  457 */     renderItem(stack, ibakedmodel);
/*  458 */     GlStateManager.disableAlpha();
/*  459 */     GlStateManager.disableRescaleNormal();
/*  460 */     GlStateManager.disableLighting();
/*  461 */     GlStateManager.popMatrix();
/*  462 */     this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
/*  463 */     this.textureManager.getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/*  464 */     this.renderItemGui = false;
/*      */   }
/*      */   
/*      */   private void setupGuiTransform(int xPosition, int yPosition, boolean isGui3d) {
/*  468 */     GlStateManager.translate(xPosition, yPosition, 100.0F + this.zLevel);
/*  469 */     GlStateManager.translate(8.0F, 8.0F, 0.0F);
/*  470 */     GlStateManager.scale(1.0F, 1.0F, -1.0F);
/*  471 */     GlStateManager.scale(0.5F, 0.5F, 0.5F);
/*      */     
/*  473 */     if (isGui3d) {
/*  474 */       GlStateManager.scale(40.0F, 40.0F, 40.0F);
/*  475 */       GlStateManager.rotate(210.0F, 1.0F, 0.0F, 0.0F);
/*  476 */       GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
/*  477 */       GlStateManager.enableLighting();
/*      */     } else {
/*  479 */       GlStateManager.scale(64.0F, 64.0F, 64.0F);
/*  480 */       GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
/*  481 */       GlStateManager.disableLighting();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renderItemAndEffectIntoGUI(final ItemStack stack, int xPosition, int yPosition) {
/*  486 */     if (stack != null && stack.getItem() != null) {
/*  487 */       this.zLevel += 50.0F;
/*      */       
/*      */       try {
/*  490 */         renderItemIntoGUI(stack, xPosition, yPosition);
/*  491 */       } catch (Throwable throwable) {
/*  492 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering item");
/*  493 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being rendered");
/*  494 */         crashreportcategory.addCrashSectionCallable("Item Type", new Callable<String>() {
/*      */               public String call() {
/*  496 */                 return String.valueOf(stack.getItem());
/*      */               }
/*      */             });
/*  499 */         crashreportcategory.addCrashSectionCallable("Item Aux", new Callable<String>() {
/*      */               public String call() {
/*  501 */                 return String.valueOf(stack.getMetadata());
/*      */               }
/*      */             });
/*  504 */         crashreportcategory.addCrashSectionCallable("Item NBT", new Callable<String>() {
/*      */               public String call() {
/*  506 */                 return String.valueOf(stack.getTagCompound());
/*      */               }
/*      */             });
/*  509 */         crashreportcategory.addCrashSectionCallable("Item Foil", new Callable<String>() {
/*      */               public String call() {
/*  511 */                 return String.valueOf(stack.hasEffect());
/*      */               }
/*      */             });
/*  514 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/*  517 */       this.zLevel -= 50.0F;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renderItemOverlays(FontRenderer fr, ItemStack stack, int xPosition, int yPosition) {
/*  522 */     renderItemOverlayIntoGUI(fr, stack, xPosition, yPosition, (String)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderItemOverlayIntoGUI(FontRenderer fr, ItemStack stack, int xPosition, int yPosition, String text) {
/*  529 */     if (stack != null) {
/*  530 */       if (stack.stackSize != 1 || text != null) {
/*  531 */         String s = (text == null) ? String.valueOf(stack.stackSize) : text;
/*      */         
/*  533 */         if (text == null && stack.stackSize < 1) {
/*  534 */           s = EnumChatFormatting.RED + String.valueOf(stack.stackSize);
/*      */         }
/*      */         
/*  537 */         GlStateManager.disableLighting();
/*  538 */         GlStateManager.disableDepth();
/*  539 */         GlStateManager.disableBlend();
/*  540 */         if (ToolTipsAnim.getInstance.isEnabled()) {
/*  541 */           Client.instance.FontLoaders.regular17.drawString(s, (xPosition + 19 - 2 - Client.instance.FontLoaders.regular17.getStringWidth(s)), (yPosition + 6 + 3), 16777215);
/*      */         } else {
/*  543 */           fr.drawStringWithShadow(s, (xPosition + 19 - 2 - fr.getStringWidth(s)), (yPosition + 6 + 3), 16777215);
/*      */         } 
/*  545 */         GlStateManager.enableLighting();
/*  546 */         GlStateManager.enableDepth();
/*  547 */         GlStateManager.enableBlend();
/*      */       } 
/*      */       
/*  550 */       if (ReflectorForge.isItemDamaged(stack)) {
/*  551 */         int j1 = (int)Math.round(13.0D - stack.getItemDamage() * 13.0D / stack.getMaxDamage());
/*  552 */         int i = (int)Math.round(255.0D - stack.getItemDamage() * 255.0D / stack.getMaxDamage());
/*      */         
/*  554 */         if (Reflector.ForgeItem_getDurabilityForDisplay.exists()) {
/*  555 */           double d0 = Reflector.callDouble(stack.getItem(), Reflector.ForgeItem_getDurabilityForDisplay, new Object[] { stack });
/*  556 */           j1 = (int)Math.round(13.0D - d0 * 13.0D);
/*  557 */           i = (int)Math.round(255.0D - d0 * 255.0D);
/*      */         } 
/*      */         
/*  560 */         GlStateManager.disableLighting();
/*  561 */         GlStateManager.disableDepth();
/*  562 */         GlStateManager.disableTexture2D();
/*  563 */         GlStateManager.disableAlpha();
/*  564 */         GlStateManager.disableBlend();
/*  565 */         Tessellator tessellator = Tessellator.getInstance();
/*  566 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  567 */         draw(worldrenderer, xPosition + 2, yPosition + 13, 13, 2, 0, 0, 0, 255);
/*  568 */         draw(worldrenderer, xPosition + 2, yPosition + 13, 12, 1, (255 - i) / 4, 64, 0, 255);
/*  569 */         int j = 255 - i;
/*  570 */         int k = i;
/*  571 */         int l = 0;
/*      */         
/*  573 */         if (Config.isCustomColors()) {
/*  574 */           int i1 = CustomColors.getDurabilityColor(i);
/*      */           
/*  576 */           if (i1 >= 0) {
/*  577 */             j = i1 >> 16 & 0xFF;
/*  578 */             k = i1 >> 8 & 0xFF;
/*  579 */             l = i1 & 0xFF;
/*      */           } 
/*      */         } 
/*      */         
/*  583 */         draw(worldrenderer, xPosition + 2, yPosition + 13, j1, 1, j, k, l, 255);
/*  584 */         GlStateManager.enableBlend();
/*  585 */         GlStateManager.enableAlpha();
/*  586 */         GlStateManager.enableTexture2D();
/*  587 */         GlStateManager.enableLighting();
/*  588 */         GlStateManager.enableDepth();
/*      */       } 
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
/*      */   private void draw(WorldRenderer renderer, int x, int y, int width, int height, int red, int green, int blue, int alpha) {
/*  607 */     renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  608 */     renderer.pos(x, y, 0.0D).color(red, green, blue, alpha).endVertex();
/*  609 */     renderer.pos(x, (y + height), 0.0D).color(red, green, blue, alpha).endVertex();
/*  610 */     renderer.pos((x + width), (y + height), 0.0D).color(red, green, blue, alpha).endVertex();
/*  611 */     renderer.pos((x + width), y, 0.0D).color(red, green, blue, alpha).endVertex();
/*  612 */     Tessellator.getInstance().draw();
/*      */   }
/*      */   
/*      */   private void registerItems() {
/*  616 */     registerBlock(Blocks.anvil, "anvil_intact");
/*  617 */     registerBlock(Blocks.anvil, 1, "anvil_slightly_damaged");
/*  618 */     registerBlock(Blocks.anvil, 2, "anvil_very_damaged");
/*  619 */     registerBlock(Blocks.carpet, EnumDyeColor.BLACK.getMetadata(), "black_carpet");
/*  620 */     registerBlock(Blocks.carpet, EnumDyeColor.BLUE.getMetadata(), "blue_carpet");
/*  621 */     registerBlock(Blocks.carpet, EnumDyeColor.BROWN.getMetadata(), "brown_carpet");
/*  622 */     registerBlock(Blocks.carpet, EnumDyeColor.CYAN.getMetadata(), "cyan_carpet");
/*  623 */     registerBlock(Blocks.carpet, EnumDyeColor.GRAY.getMetadata(), "gray_carpet");
/*  624 */     registerBlock(Blocks.carpet, EnumDyeColor.GREEN.getMetadata(), "green_carpet");
/*  625 */     registerBlock(Blocks.carpet, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_carpet");
/*  626 */     registerBlock(Blocks.carpet, EnumDyeColor.LIME.getMetadata(), "lime_carpet");
/*  627 */     registerBlock(Blocks.carpet, EnumDyeColor.MAGENTA.getMetadata(), "magenta_carpet");
/*  628 */     registerBlock(Blocks.carpet, EnumDyeColor.ORANGE.getMetadata(), "orange_carpet");
/*  629 */     registerBlock(Blocks.carpet, EnumDyeColor.PINK.getMetadata(), "pink_carpet");
/*  630 */     registerBlock(Blocks.carpet, EnumDyeColor.PURPLE.getMetadata(), "purple_carpet");
/*  631 */     registerBlock(Blocks.carpet, EnumDyeColor.RED.getMetadata(), "red_carpet");
/*  632 */     registerBlock(Blocks.carpet, EnumDyeColor.SILVER.getMetadata(), "silver_carpet");
/*  633 */     registerBlock(Blocks.carpet, EnumDyeColor.WHITE.getMetadata(), "white_carpet");
/*  634 */     registerBlock(Blocks.carpet, EnumDyeColor.YELLOW.getMetadata(), "yellow_carpet");
/*  635 */     registerBlock(Blocks.cobblestone_wall, BlockWall.EnumType.MOSSY.getMetadata(), "mossy_cobblestone_wall");
/*  636 */     registerBlock(Blocks.cobblestone_wall, BlockWall.EnumType.NORMAL.getMetadata(), "cobblestone_wall");
/*  637 */     registerBlock(Blocks.dirt, BlockDirt.DirtType.COARSE_DIRT.getMetadata(), "coarse_dirt");
/*  638 */     registerBlock(Blocks.dirt, BlockDirt.DirtType.DIRT.getMetadata(), "dirt");
/*  639 */     registerBlock(Blocks.dirt, BlockDirt.DirtType.PODZOL.getMetadata(), "podzol");
/*  640 */     registerBlock((Block)Blocks.double_plant, BlockDoublePlant.EnumPlantType.FERN.getMeta(), "double_fern");
/*  641 */     registerBlock((Block)Blocks.double_plant, BlockDoublePlant.EnumPlantType.GRASS.getMeta(), "double_grass");
/*  642 */     registerBlock((Block)Blocks.double_plant, BlockDoublePlant.EnumPlantType.PAEONIA.getMeta(), "paeonia");
/*  643 */     registerBlock((Block)Blocks.double_plant, BlockDoublePlant.EnumPlantType.ROSE.getMeta(), "double_rose");
/*  644 */     registerBlock((Block)Blocks.double_plant, BlockDoublePlant.EnumPlantType.SUNFLOWER.getMeta(), "sunflower");
/*  645 */     registerBlock((Block)Blocks.double_plant, BlockDoublePlant.EnumPlantType.SYRINGA.getMeta(), "syringa");
/*  646 */     registerBlock((Block)Blocks.leaves, BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_leaves");
/*  647 */     registerBlock((Block)Blocks.leaves, BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_leaves");
/*  648 */     registerBlock((Block)Blocks.leaves, BlockPlanks.EnumType.OAK.getMetadata(), "oak_leaves");
/*  649 */     registerBlock((Block)Blocks.leaves, BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_leaves");
/*  650 */     registerBlock((Block)Blocks.leaves2, BlockPlanks.EnumType.ACACIA.getMetadata() - 4, "acacia_leaves");
/*  651 */     registerBlock((Block)Blocks.leaves2, BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4, "dark_oak_leaves");
/*  652 */     registerBlock(Blocks.log, BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_log");
/*  653 */     registerBlock(Blocks.log, BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_log");
/*  654 */     registerBlock(Blocks.log, BlockPlanks.EnumType.OAK.getMetadata(), "oak_log");
/*  655 */     registerBlock(Blocks.log, BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_log");
/*  656 */     registerBlock(Blocks.log2, BlockPlanks.EnumType.ACACIA.getMetadata() - 4, "acacia_log");
/*  657 */     registerBlock(Blocks.log2, BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4, "dark_oak_log");
/*  658 */     registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.CHISELED_STONEBRICK.getMetadata(), "chiseled_brick_monster_egg");
/*  659 */     registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.COBBLESTONE.getMetadata(), "cobblestone_monster_egg");
/*  660 */     registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.CRACKED_STONEBRICK.getMetadata(), "cracked_brick_monster_egg");
/*  661 */     registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.MOSSY_STONEBRICK.getMetadata(), "mossy_brick_monster_egg");
/*  662 */     registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.STONE.getMetadata(), "stone_monster_egg");
/*  663 */     registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.STONEBRICK.getMetadata(), "stone_brick_monster_egg");
/*  664 */     registerBlock(Blocks.planks, BlockPlanks.EnumType.ACACIA.getMetadata(), "acacia_planks");
/*  665 */     registerBlock(Blocks.planks, BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_planks");
/*  666 */     registerBlock(Blocks.planks, BlockPlanks.EnumType.DARK_OAK.getMetadata(), "dark_oak_planks");
/*  667 */     registerBlock(Blocks.planks, BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_planks");
/*  668 */     registerBlock(Blocks.planks, BlockPlanks.EnumType.OAK.getMetadata(), "oak_planks");
/*  669 */     registerBlock(Blocks.planks, BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_planks");
/*  670 */     registerBlock(Blocks.prismarine, BlockPrismarine.EnumType.BRICKS.getMetadata(), "prismarine_bricks");
/*  671 */     registerBlock(Blocks.prismarine, BlockPrismarine.EnumType.DARK.getMetadata(), "dark_prismarine");
/*  672 */     registerBlock(Blocks.prismarine, BlockPrismarine.EnumType.ROUGH.getMetadata(), "prismarine");
/*  673 */     registerBlock(Blocks.quartz_block, BlockQuartz.EnumType.CHISELED.getMetadata(), "chiseled_quartz_block");
/*  674 */     registerBlock(Blocks.quartz_block, BlockQuartz.EnumType.DEFAULT.getMetadata(), "quartz_block");
/*  675 */     registerBlock(Blocks.quartz_block, BlockQuartz.EnumType.LINES_Y.getMetadata(), "quartz_column");
/*  676 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.ALLIUM.getMeta(), "allium");
/*  677 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.BLUE_ORCHID.getMeta(), "blue_orchid");
/*  678 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.HOUSTONIA.getMeta(), "houstonia");
/*  679 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.ORANGE_TULIP.getMeta(), "orange_tulip");
/*  680 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.OXEYE_DAISY.getMeta(), "oxeye_daisy");
/*  681 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.PINK_TULIP.getMeta(), "pink_tulip");
/*  682 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.POPPY.getMeta(), "poppy");
/*  683 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.RED_TULIP.getMeta(), "red_tulip");
/*  684 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.WHITE_TULIP.getMeta(), "white_tulip");
/*  685 */     registerBlock((Block)Blocks.sand, BlockSand.EnumType.RED_SAND.getMetadata(), "red_sand");
/*  686 */     registerBlock((Block)Blocks.sand, BlockSand.EnumType.SAND.getMetadata(), "sand");
/*  687 */     registerBlock(Blocks.sandstone, BlockSandStone.EnumType.CHISELED.getMetadata(), "chiseled_sandstone");
/*  688 */     registerBlock(Blocks.sandstone, BlockSandStone.EnumType.DEFAULT.getMetadata(), "sandstone");
/*  689 */     registerBlock(Blocks.sandstone, BlockSandStone.EnumType.SMOOTH.getMetadata(), "smooth_sandstone");
/*  690 */     registerBlock(Blocks.red_sandstone, BlockRedSandstone.EnumType.CHISELED.getMetadata(), "chiseled_red_sandstone");
/*  691 */     registerBlock(Blocks.red_sandstone, BlockRedSandstone.EnumType.DEFAULT.getMetadata(), "red_sandstone");
/*  692 */     registerBlock(Blocks.red_sandstone, BlockRedSandstone.EnumType.SMOOTH.getMetadata(), "smooth_red_sandstone");
/*  693 */     registerBlock(Blocks.sapling, BlockPlanks.EnumType.ACACIA.getMetadata(), "acacia_sapling");
/*  694 */     registerBlock(Blocks.sapling, BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_sapling");
/*  695 */     registerBlock(Blocks.sapling, BlockPlanks.EnumType.DARK_OAK.getMetadata(), "dark_oak_sapling");
/*  696 */     registerBlock(Blocks.sapling, BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_sapling");
/*  697 */     registerBlock(Blocks.sapling, BlockPlanks.EnumType.OAK.getMetadata(), "oak_sapling");
/*  698 */     registerBlock(Blocks.sapling, BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_sapling");
/*  699 */     registerBlock(Blocks.sponge, 0, "sponge");
/*  700 */     registerBlock(Blocks.sponge, 1, "sponge_wet");
/*  701 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.BLACK.getMetadata(), "black_stained_glass");
/*  702 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.BLUE.getMetadata(), "blue_stained_glass");
/*  703 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.BROWN.getMetadata(), "brown_stained_glass");
/*  704 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.CYAN.getMetadata(), "cyan_stained_glass");
/*  705 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.GRAY.getMetadata(), "gray_stained_glass");
/*  706 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.GREEN.getMetadata(), "green_stained_glass");
/*  707 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_stained_glass");
/*  708 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.LIME.getMetadata(), "lime_stained_glass");
/*  709 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.MAGENTA.getMetadata(), "magenta_stained_glass");
/*  710 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.ORANGE.getMetadata(), "orange_stained_glass");
/*  711 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.PINK.getMetadata(), "pink_stained_glass");
/*  712 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.PURPLE.getMetadata(), "purple_stained_glass");
/*  713 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.RED.getMetadata(), "red_stained_glass");
/*  714 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.SILVER.getMetadata(), "silver_stained_glass");
/*  715 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.WHITE.getMetadata(), "white_stained_glass");
/*  716 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.YELLOW.getMetadata(), "yellow_stained_glass");
/*  717 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.BLACK.getMetadata(), "black_stained_glass_pane");
/*  718 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.BLUE.getMetadata(), "blue_stained_glass_pane");
/*  719 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.BROWN.getMetadata(), "brown_stained_glass_pane");
/*  720 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.CYAN.getMetadata(), "cyan_stained_glass_pane");
/*  721 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.GRAY.getMetadata(), "gray_stained_glass_pane");
/*  722 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.GREEN.getMetadata(), "green_stained_glass_pane");
/*  723 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_stained_glass_pane");
/*  724 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.LIME.getMetadata(), "lime_stained_glass_pane");
/*  725 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.MAGENTA.getMetadata(), "magenta_stained_glass_pane");
/*  726 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.ORANGE.getMetadata(), "orange_stained_glass_pane");
/*  727 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.PINK.getMetadata(), "pink_stained_glass_pane");
/*  728 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.PURPLE.getMetadata(), "purple_stained_glass_pane");
/*  729 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.RED.getMetadata(), "red_stained_glass_pane");
/*  730 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.SILVER.getMetadata(), "silver_stained_glass_pane");
/*  731 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.WHITE.getMetadata(), "white_stained_glass_pane");
/*  732 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.YELLOW.getMetadata(), "yellow_stained_glass_pane");
/*  733 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.BLACK.getMetadata(), "black_stained_hardened_clay");
/*  734 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.BLUE.getMetadata(), "blue_stained_hardened_clay");
/*  735 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.BROWN.getMetadata(), "brown_stained_hardened_clay");
/*  736 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.CYAN.getMetadata(), "cyan_stained_hardened_clay");
/*  737 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.GRAY.getMetadata(), "gray_stained_hardened_clay");
/*  738 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.GREEN.getMetadata(), "green_stained_hardened_clay");
/*  739 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_stained_hardened_clay");
/*  740 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.LIME.getMetadata(), "lime_stained_hardened_clay");
/*  741 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.MAGENTA.getMetadata(), "magenta_stained_hardened_clay");
/*  742 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.ORANGE.getMetadata(), "orange_stained_hardened_clay");
/*  743 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.PINK.getMetadata(), "pink_stained_hardened_clay");
/*  744 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.PURPLE.getMetadata(), "purple_stained_hardened_clay");
/*  745 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.RED.getMetadata(), "red_stained_hardened_clay");
/*  746 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.SILVER.getMetadata(), "silver_stained_hardened_clay");
/*  747 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.WHITE.getMetadata(), "white_stained_hardened_clay");
/*  748 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.YELLOW.getMetadata(), "yellow_stained_hardened_clay");
/*  749 */     registerBlock(Blocks.stone, BlockStone.EnumType.ANDESITE.getMetadata(), "andesite");
/*  750 */     registerBlock(Blocks.stone, BlockStone.EnumType.ANDESITE_SMOOTH.getMetadata(), "andesite_smooth");
/*  751 */     registerBlock(Blocks.stone, BlockStone.EnumType.DIORITE.getMetadata(), "diorite");
/*  752 */     registerBlock(Blocks.stone, BlockStone.EnumType.DIORITE_SMOOTH.getMetadata(), "diorite_smooth");
/*  753 */     registerBlock(Blocks.stone, BlockStone.EnumType.GRANITE.getMetadata(), "granite");
/*  754 */     registerBlock(Blocks.stone, BlockStone.EnumType.GRANITE_SMOOTH.getMetadata(), "granite_smooth");
/*  755 */     registerBlock(Blocks.stone, BlockStone.EnumType.STONE.getMetadata(), "stone");
/*  756 */     registerBlock(Blocks.stonebrick, BlockStoneBrick.EnumType.CRACKED.getMetadata(), "cracked_stonebrick");
/*  757 */     registerBlock(Blocks.stonebrick, BlockStoneBrick.EnumType.DEFAULT.getMetadata(), "stonebrick");
/*  758 */     registerBlock(Blocks.stonebrick, BlockStoneBrick.EnumType.CHISELED.getMetadata(), "chiseled_stonebrick");
/*  759 */     registerBlock(Blocks.stonebrick, BlockStoneBrick.EnumType.MOSSY.getMetadata(), "mossy_stonebrick");
/*  760 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.BRICK.getMetadata(), "brick_slab");
/*  761 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.COBBLESTONE.getMetadata(), "cobblestone_slab");
/*  762 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.WOOD.getMetadata(), "old_wood_slab");
/*  763 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.NETHERBRICK.getMetadata(), "nether_brick_slab");
/*  764 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.QUARTZ.getMetadata(), "quartz_slab");
/*  765 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.SAND.getMetadata(), "sandstone_slab");
/*  766 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata(), "stone_brick_slab");
/*  767 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.STONE.getMetadata(), "stone_slab");
/*  768 */     registerBlock((Block)Blocks.stone_slab2, BlockStoneSlabNew.EnumType.RED_SANDSTONE.getMetadata(), "red_sandstone_slab");
/*  769 */     registerBlock((Block)Blocks.tallgrass, BlockTallGrass.EnumType.DEAD_BUSH.getMeta(), "dead_bush");
/*  770 */     registerBlock((Block)Blocks.tallgrass, BlockTallGrass.EnumType.FERN.getMeta(), "fern");
/*  771 */     registerBlock((Block)Blocks.tallgrass, BlockTallGrass.EnumType.GRASS.getMeta(), "tall_grass");
/*  772 */     registerBlock((Block)Blocks.wooden_slab, BlockPlanks.EnumType.ACACIA.getMetadata(), "acacia_slab");
/*  773 */     registerBlock((Block)Blocks.wooden_slab, BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_slab");
/*  774 */     registerBlock((Block)Blocks.wooden_slab, BlockPlanks.EnumType.DARK_OAK.getMetadata(), "dark_oak_slab");
/*  775 */     registerBlock((Block)Blocks.wooden_slab, BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_slab");
/*  776 */     registerBlock((Block)Blocks.wooden_slab, BlockPlanks.EnumType.OAK.getMetadata(), "oak_slab");
/*  777 */     registerBlock((Block)Blocks.wooden_slab, BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_slab");
/*  778 */     registerBlock(Blocks.wool, EnumDyeColor.BLACK.getMetadata(), "black_wool");
/*  779 */     registerBlock(Blocks.wool, EnumDyeColor.BLUE.getMetadata(), "blue_wool");
/*  780 */     registerBlock(Blocks.wool, EnumDyeColor.BROWN.getMetadata(), "brown_wool");
/*  781 */     registerBlock(Blocks.wool, EnumDyeColor.CYAN.getMetadata(), "cyan_wool");
/*  782 */     registerBlock(Blocks.wool, EnumDyeColor.GRAY.getMetadata(), "gray_wool");
/*  783 */     registerBlock(Blocks.wool, EnumDyeColor.GREEN.getMetadata(), "green_wool");
/*  784 */     registerBlock(Blocks.wool, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_wool");
/*  785 */     registerBlock(Blocks.wool, EnumDyeColor.LIME.getMetadata(), "lime_wool");
/*  786 */     registerBlock(Blocks.wool, EnumDyeColor.MAGENTA.getMetadata(), "magenta_wool");
/*  787 */     registerBlock(Blocks.wool, EnumDyeColor.ORANGE.getMetadata(), "orange_wool");
/*  788 */     registerBlock(Blocks.wool, EnumDyeColor.PINK.getMetadata(), "pink_wool");
/*  789 */     registerBlock(Blocks.wool, EnumDyeColor.PURPLE.getMetadata(), "purple_wool");
/*  790 */     registerBlock(Blocks.wool, EnumDyeColor.RED.getMetadata(), "red_wool");
/*  791 */     registerBlock(Blocks.wool, EnumDyeColor.SILVER.getMetadata(), "silver_wool");
/*  792 */     registerBlock(Blocks.wool, EnumDyeColor.WHITE.getMetadata(), "white_wool");
/*  793 */     registerBlock(Blocks.wool, EnumDyeColor.YELLOW.getMetadata(), "yellow_wool");
/*  794 */     registerBlock(Blocks.acacia_stairs, "acacia_stairs");
/*  795 */     registerBlock(Blocks.activator_rail, "activator_rail");
/*  796 */     registerBlock((Block)Blocks.beacon, "beacon");
/*  797 */     registerBlock(Blocks.bedrock, "bedrock");
/*  798 */     registerBlock(Blocks.birch_stairs, "birch_stairs");
/*  799 */     registerBlock(Blocks.bookshelf, "bookshelf");
/*  800 */     registerBlock(Blocks.brick_block, "brick_block");
/*  801 */     registerBlock(Blocks.brick_block, "brick_block");
/*  802 */     registerBlock(Blocks.brick_stairs, "brick_stairs");
/*  803 */     registerBlock((Block)Blocks.brown_mushroom, "brown_mushroom");
/*  804 */     registerBlock((Block)Blocks.cactus, "cactus");
/*  805 */     registerBlock(Blocks.clay, "clay");
/*  806 */     registerBlock(Blocks.coal_block, "coal_block");
/*  807 */     registerBlock(Blocks.coal_ore, "coal_ore");
/*  808 */     registerBlock(Blocks.cobblestone, "cobblestone");
/*  809 */     registerBlock(Blocks.crafting_table, "crafting_table");
/*  810 */     registerBlock(Blocks.dark_oak_stairs, "dark_oak_stairs");
/*  811 */     registerBlock((Block)Blocks.daylight_detector, "daylight_detector");
/*  812 */     registerBlock((Block)Blocks.deadbush, "dead_bush");
/*  813 */     registerBlock(Blocks.detector_rail, "detector_rail");
/*  814 */     registerBlock(Blocks.diamond_block, "diamond_block");
/*  815 */     registerBlock(Blocks.diamond_ore, "diamond_ore");
/*  816 */     registerBlock(Blocks.dispenser, "dispenser");
/*  817 */     registerBlock(Blocks.dropper, "dropper");
/*  818 */     registerBlock(Blocks.emerald_block, "emerald_block");
/*  819 */     registerBlock(Blocks.emerald_ore, "emerald_ore");
/*  820 */     registerBlock(Blocks.enchanting_table, "enchanting_table");
/*  821 */     registerBlock(Blocks.end_portal_frame, "end_portal_frame");
/*  822 */     registerBlock(Blocks.end_stone, "end_stone");
/*  823 */     registerBlock(Blocks.oak_fence, "oak_fence");
/*  824 */     registerBlock(Blocks.spruce_fence, "spruce_fence");
/*  825 */     registerBlock(Blocks.birch_fence, "birch_fence");
/*  826 */     registerBlock(Blocks.jungle_fence, "jungle_fence");
/*  827 */     registerBlock(Blocks.dark_oak_fence, "dark_oak_fence");
/*  828 */     registerBlock(Blocks.acacia_fence, "acacia_fence");
/*  829 */     registerBlock(Blocks.oak_fence_gate, "oak_fence_gate");
/*  830 */     registerBlock(Blocks.spruce_fence_gate, "spruce_fence_gate");
/*  831 */     registerBlock(Blocks.birch_fence_gate, "birch_fence_gate");
/*  832 */     registerBlock(Blocks.jungle_fence_gate, "jungle_fence_gate");
/*  833 */     registerBlock(Blocks.dark_oak_fence_gate, "dark_oak_fence_gate");
/*  834 */     registerBlock(Blocks.acacia_fence_gate, "acacia_fence_gate");
/*  835 */     registerBlock(Blocks.furnace, "furnace");
/*  836 */     registerBlock(Blocks.glass, "glass");
/*  837 */     registerBlock(Blocks.glass_pane, "glass_pane");
/*  838 */     registerBlock(Blocks.glowstone, "glowstone");
/*  839 */     registerBlock(Blocks.golden_rail, "golden_rail");
/*  840 */     registerBlock(Blocks.gold_block, "gold_block");
/*  841 */     registerBlock(Blocks.gold_ore, "gold_ore");
/*  842 */     registerBlock((Block)Blocks.grass, "grass");
/*  843 */     registerBlock(Blocks.gravel, "gravel");
/*  844 */     registerBlock(Blocks.hardened_clay, "hardened_clay");
/*  845 */     registerBlock(Blocks.hay_block, "hay_block");
/*  846 */     registerBlock(Blocks.heavy_weighted_pressure_plate, "heavy_weighted_pressure_plate");
/*  847 */     registerBlock((Block)Blocks.hopper, "hopper");
/*  848 */     registerBlock(Blocks.ice, "ice");
/*  849 */     registerBlock(Blocks.iron_bars, "iron_bars");
/*  850 */     registerBlock(Blocks.iron_block, "iron_block");
/*  851 */     registerBlock(Blocks.iron_ore, "iron_ore");
/*  852 */     registerBlock(Blocks.iron_trapdoor, "iron_trapdoor");
/*  853 */     registerBlock(Blocks.jukebox, "jukebox");
/*  854 */     registerBlock(Blocks.jungle_stairs, "jungle_stairs");
/*  855 */     registerBlock(Blocks.ladder, "ladder");
/*  856 */     registerBlock(Blocks.lapis_block, "lapis_block");
/*  857 */     registerBlock(Blocks.lapis_ore, "lapis_ore");
/*  858 */     registerBlock(Blocks.lever, "lever");
/*  859 */     registerBlock(Blocks.light_weighted_pressure_plate, "light_weighted_pressure_plate");
/*  860 */     registerBlock(Blocks.lit_pumpkin, "lit_pumpkin");
/*  861 */     registerBlock(Blocks.melon_block, "melon_block");
/*  862 */     registerBlock(Blocks.mossy_cobblestone, "mossy_cobblestone");
/*  863 */     registerBlock((Block)Blocks.mycelium, "mycelium");
/*  864 */     registerBlock(Blocks.netherrack, "netherrack");
/*  865 */     registerBlock(Blocks.nether_brick, "nether_brick");
/*  866 */     registerBlock(Blocks.nether_brick_fence, "nether_brick_fence");
/*  867 */     registerBlock(Blocks.nether_brick_stairs, "nether_brick_stairs");
/*  868 */     registerBlock(Blocks.noteblock, "noteblock");
/*  869 */     registerBlock(Blocks.oak_stairs, "oak_stairs");
/*  870 */     registerBlock(Blocks.obsidian, "obsidian");
/*  871 */     registerBlock(Blocks.packed_ice, "packed_ice");
/*  872 */     registerBlock((Block)Blocks.piston, "piston");
/*  873 */     registerBlock(Blocks.pumpkin, "pumpkin");
/*  874 */     registerBlock(Blocks.quartz_ore, "quartz_ore");
/*  875 */     registerBlock(Blocks.quartz_stairs, "quartz_stairs");
/*  876 */     registerBlock(Blocks.rail, "rail");
/*  877 */     registerBlock(Blocks.redstone_block, "redstone_block");
/*  878 */     registerBlock(Blocks.redstone_lamp, "redstone_lamp");
/*  879 */     registerBlock(Blocks.redstone_ore, "redstone_ore");
/*  880 */     registerBlock(Blocks.redstone_torch, "redstone_torch");
/*  881 */     registerBlock((Block)Blocks.red_mushroom, "red_mushroom");
/*  882 */     registerBlock(Blocks.sandstone_stairs, "sandstone_stairs");
/*  883 */     registerBlock(Blocks.red_sandstone_stairs, "red_sandstone_stairs");
/*  884 */     registerBlock(Blocks.sea_lantern, "sea_lantern");
/*  885 */     registerBlock(Blocks.slime_block, "slime");
/*  886 */     registerBlock(Blocks.snow, "snow");
/*  887 */     registerBlock(Blocks.snow_layer, "snow_layer");
/*  888 */     registerBlock(Blocks.soul_sand, "soul_sand");
/*  889 */     registerBlock(Blocks.spruce_stairs, "spruce_stairs");
/*  890 */     registerBlock((Block)Blocks.sticky_piston, "sticky_piston");
/*  891 */     registerBlock(Blocks.stone_brick_stairs, "stone_brick_stairs");
/*  892 */     registerBlock(Blocks.stone_button, "stone_button");
/*  893 */     registerBlock(Blocks.stone_pressure_plate, "stone_pressure_plate");
/*  894 */     registerBlock(Blocks.stone_stairs, "stone_stairs");
/*  895 */     registerBlock(Blocks.tnt, "tnt");
/*  896 */     registerBlock(Blocks.torch, "torch");
/*  897 */     registerBlock(Blocks.trapdoor, "trapdoor");
/*  898 */     registerBlock((Block)Blocks.tripwire_hook, "tripwire_hook");
/*  899 */     registerBlock(Blocks.vine, "vine");
/*  900 */     registerBlock(Blocks.waterlily, "waterlily");
/*  901 */     registerBlock(Blocks.web, "web");
/*  902 */     registerBlock(Blocks.wooden_button, "wooden_button");
/*  903 */     registerBlock(Blocks.wooden_pressure_plate, "wooden_pressure_plate");
/*  904 */     registerBlock((Block)Blocks.yellow_flower, BlockFlower.EnumFlowerType.DANDELION.getMeta(), "dandelion");
/*  905 */     registerBlock((Block)Blocks.chest, "chest");
/*  906 */     registerBlock(Blocks.trapped_chest, "trapped_chest");
/*  907 */     registerBlock(Blocks.ender_chest, "ender_chest");
/*  908 */     registerItem(Items.iron_shovel, "iron_shovel");
/*  909 */     registerItem(Items.iron_pickaxe, "iron_pickaxe");
/*  910 */     registerItem(Items.iron_axe, "iron_axe");
/*  911 */     registerItem(Items.flint_and_steel, "flint_and_steel");
/*  912 */     registerItem(Items.apple, "apple");
/*  913 */     registerItem((Item)Items.bow, 0, "bow");
/*  914 */     registerItem((Item)Items.bow, 1, "bow_pulling_0");
/*  915 */     registerItem((Item)Items.bow, 2, "bow_pulling_1");
/*  916 */     registerItem((Item)Items.bow, 3, "bow_pulling_2");
/*  917 */     registerItem(Items.arrow, "arrow");
/*  918 */     registerItem(Items.coal, 0, "coal");
/*  919 */     registerItem(Items.coal, 1, "charcoal");
/*  920 */     registerItem(Items.diamond, "diamond");
/*  921 */     registerItem(Items.iron_ingot, "iron_ingot");
/*  922 */     registerItem(Items.gold_ingot, "gold_ingot");
/*  923 */     registerItem(Items.iron_sword, "iron_sword");
/*  924 */     registerItem(Items.wooden_sword, "wooden_sword");
/*  925 */     registerItem(Items.wooden_shovel, "wooden_shovel");
/*  926 */     registerItem(Items.wooden_pickaxe, "wooden_pickaxe");
/*  927 */     registerItem(Items.wooden_axe, "wooden_axe");
/*  928 */     registerItem(Items.stone_sword, "stone_sword");
/*  929 */     registerItem(Items.stone_shovel, "stone_shovel");
/*  930 */     registerItem(Items.stone_pickaxe, "stone_pickaxe");
/*  931 */     registerItem(Items.stone_axe, "stone_axe");
/*  932 */     registerItem(Items.diamond_sword, "diamond_sword");
/*  933 */     registerItem(Items.diamond_shovel, "diamond_shovel");
/*  934 */     registerItem(Items.diamond_pickaxe, "diamond_pickaxe");
/*  935 */     registerItem(Items.diamond_axe, "diamond_axe");
/*  936 */     registerItem(Items.stick, "stick");
/*  937 */     registerItem(Items.bowl, "bowl");
/*  938 */     registerItem(Items.mushroom_stew, "mushroom_stew");
/*  939 */     registerItem(Items.golden_sword, "golden_sword");
/*  940 */     registerItem(Items.golden_shovel, "golden_shovel");
/*  941 */     registerItem(Items.golden_pickaxe, "golden_pickaxe");
/*  942 */     registerItem(Items.golden_axe, "golden_axe");
/*  943 */     registerItem(Items.string, "string");
/*  944 */     registerItem(Items.feather, "feather");
/*  945 */     registerItem(Items.gunpowder, "gunpowder");
/*  946 */     registerItem(Items.wooden_hoe, "wooden_hoe");
/*  947 */     registerItem(Items.stone_hoe, "stone_hoe");
/*  948 */     registerItem(Items.iron_hoe, "iron_hoe");
/*  949 */     registerItem(Items.diamond_hoe, "diamond_hoe");
/*  950 */     registerItem(Items.golden_hoe, "golden_hoe");
/*  951 */     registerItem(Items.wheat_seeds, "wheat_seeds");
/*  952 */     registerItem(Items.wheat, "wheat");
/*  953 */     registerItem(Items.bread, "bread");
/*  954 */     registerItem((Item)Items.leather_helmet, "leather_helmet");
/*  955 */     registerItem((Item)Items.leather_chestplate, "leather_chestplate");
/*  956 */     registerItem((Item)Items.leather_leggings, "leather_leggings");
/*  957 */     registerItem((Item)Items.leather_boots, "leather_boots");
/*  958 */     registerItem((Item)Items.chainmail_helmet, "chainmail_helmet");
/*  959 */     registerItem((Item)Items.chainmail_chestplate, "chainmail_chestplate");
/*  960 */     registerItem((Item)Items.chainmail_leggings, "chainmail_leggings");
/*  961 */     registerItem((Item)Items.chainmail_boots, "chainmail_boots");
/*  962 */     registerItem((Item)Items.iron_helmet, "iron_helmet");
/*  963 */     registerItem((Item)Items.iron_chestplate, "iron_chestplate");
/*  964 */     registerItem((Item)Items.iron_leggings, "iron_leggings");
/*  965 */     registerItem((Item)Items.iron_boots, "iron_boots");
/*  966 */     registerItem((Item)Items.diamond_helmet, "diamond_helmet");
/*  967 */     registerItem((Item)Items.diamond_chestplate, "diamond_chestplate");
/*  968 */     registerItem((Item)Items.diamond_leggings, "diamond_leggings");
/*  969 */     registerItem((Item)Items.diamond_boots, "diamond_boots");
/*  970 */     registerItem((Item)Items.golden_helmet, "golden_helmet");
/*  971 */     registerItem((Item)Items.golden_chestplate, "golden_chestplate");
/*  972 */     registerItem((Item)Items.golden_leggings, "golden_leggings");
/*  973 */     registerItem((Item)Items.golden_boots, "golden_boots");
/*  974 */     registerItem(Items.flint, "flint");
/*  975 */     registerItem(Items.porkchop, "porkchop");
/*  976 */     registerItem(Items.cooked_porkchop, "cooked_porkchop");
/*  977 */     registerItem(Items.painting, "painting");
/*  978 */     registerItem(Items.golden_apple, "golden_apple");
/*  979 */     registerItem(Items.golden_apple, 1, "golden_apple");
/*  980 */     registerItem(Items.sign, "sign");
/*  981 */     registerItem(Items.oak_door, "oak_door");
/*  982 */     registerItem(Items.spruce_door, "spruce_door");
/*  983 */     registerItem(Items.birch_door, "birch_door");
/*  984 */     registerItem(Items.jungle_door, "jungle_door");
/*  985 */     registerItem(Items.acacia_door, "acacia_door");
/*  986 */     registerItem(Items.dark_oak_door, "dark_oak_door");
/*  987 */     registerItem(Items.bucket, "bucket");
/*  988 */     registerItem(Items.water_bucket, "water_bucket");
/*  989 */     registerItem(Items.lava_bucket, "lava_bucket");
/*  990 */     registerItem(Items.minecart, "minecart");
/*  991 */     registerItem(Items.saddle, "saddle");
/*  992 */     registerItem(Items.iron_door, "iron_door");
/*  993 */     registerItem(Items.redstone, "redstone");
/*  994 */     registerItem(Items.snowball, "snowball");
/*  995 */     registerItem(Items.boat, "boat");
/*  996 */     registerItem(Items.leather, "leather");
/*  997 */     registerItem(Items.milk_bucket, "milk_bucket");
/*  998 */     registerItem(Items.brick, "brick");
/*  999 */     registerItem(Items.clay_ball, "clay_ball");
/* 1000 */     registerItem(Items.reeds, "reeds");
/* 1001 */     registerItem(Items.paper, "paper");
/* 1002 */     registerItem(Items.book, "book");
/* 1003 */     registerItem(Items.slime_ball, "slime_ball");
/* 1004 */     registerItem(Items.chest_minecart, "chest_minecart");
/* 1005 */     registerItem(Items.furnace_minecart, "furnace_minecart");
/* 1006 */     registerItem(Items.egg, "egg");
/* 1007 */     registerItem(Items.compass, "compass");
/* 1008 */     registerItem((Item)Items.fishing_rod, "fishing_rod");
/* 1009 */     registerItem((Item)Items.fishing_rod, 1, "fishing_rod_cast");
/* 1010 */     registerItem(Items.clock, "clock");
/* 1011 */     registerItem(Items.glowstone_dust, "glowstone_dust");
/* 1012 */     registerItem(Items.fish, ItemFishFood.FishType.COD.getMetadata(), "cod");
/* 1013 */     registerItem(Items.fish, ItemFishFood.FishType.SALMON.getMetadata(), "salmon");
/* 1014 */     registerItem(Items.fish, ItemFishFood.FishType.CLOWNFISH.getMetadata(), "clownfish");
/* 1015 */     registerItem(Items.fish, ItemFishFood.FishType.PUFFERFISH.getMetadata(), "pufferfish");
/* 1016 */     registerItem(Items.cooked_fish, ItemFishFood.FishType.COD.getMetadata(), "cooked_cod");
/* 1017 */     registerItem(Items.cooked_fish, ItemFishFood.FishType.SALMON.getMetadata(), "cooked_salmon");
/* 1018 */     registerItem(Items.dye, EnumDyeColor.BLACK.getDyeDamage(), "dye_black");
/* 1019 */     registerItem(Items.dye, EnumDyeColor.RED.getDyeDamage(), "dye_red");
/* 1020 */     registerItem(Items.dye, EnumDyeColor.GREEN.getDyeDamage(), "dye_green");
/* 1021 */     registerItem(Items.dye, EnumDyeColor.BROWN.getDyeDamage(), "dye_brown");
/* 1022 */     registerItem(Items.dye, EnumDyeColor.BLUE.getDyeDamage(), "dye_blue");
/* 1023 */     registerItem(Items.dye, EnumDyeColor.PURPLE.getDyeDamage(), "dye_purple");
/* 1024 */     registerItem(Items.dye, EnumDyeColor.CYAN.getDyeDamage(), "dye_cyan");
/* 1025 */     registerItem(Items.dye, EnumDyeColor.SILVER.getDyeDamage(), "dye_silver");
/* 1026 */     registerItem(Items.dye, EnumDyeColor.GRAY.getDyeDamage(), "dye_gray");
/* 1027 */     registerItem(Items.dye, EnumDyeColor.PINK.getDyeDamage(), "dye_pink");
/* 1028 */     registerItem(Items.dye, EnumDyeColor.LIME.getDyeDamage(), "dye_lime");
/* 1029 */     registerItem(Items.dye, EnumDyeColor.YELLOW.getDyeDamage(), "dye_yellow");
/* 1030 */     registerItem(Items.dye, EnumDyeColor.LIGHT_BLUE.getDyeDamage(), "dye_light_blue");
/* 1031 */     registerItem(Items.dye, EnumDyeColor.MAGENTA.getDyeDamage(), "dye_magenta");
/* 1032 */     registerItem(Items.dye, EnumDyeColor.ORANGE.getDyeDamage(), "dye_orange");
/* 1033 */     registerItem(Items.dye, EnumDyeColor.WHITE.getDyeDamage(), "dye_white");
/* 1034 */     registerItem(Items.bone, "bone");
/* 1035 */     registerItem(Items.sugar, "sugar");
/* 1036 */     registerItem(Items.cake, "cake");
/* 1037 */     registerItem(Items.bed, "bed");
/* 1038 */     registerItem(Items.repeater, "repeater");
/* 1039 */     registerItem(Items.cookie, "cookie");
/* 1040 */     registerItem((Item)Items.shears, "shears");
/* 1041 */     registerItem(Items.melon, "melon");
/* 1042 */     registerItem(Items.pumpkin_seeds, "pumpkin_seeds");
/* 1043 */     registerItem(Items.melon_seeds, "melon_seeds");
/* 1044 */     registerItem(Items.beef, "beef");
/* 1045 */     registerItem(Items.cooked_beef, "cooked_beef");
/* 1046 */     registerItem(Items.chicken, "chicken");
/* 1047 */     registerItem(Items.cooked_chicken, "cooked_chicken");
/* 1048 */     registerItem(Items.rabbit, "rabbit");
/* 1049 */     registerItem(Items.cooked_rabbit, "cooked_rabbit");
/* 1050 */     registerItem(Items.mutton, "mutton");
/* 1051 */     registerItem(Items.cooked_mutton, "cooked_mutton");
/* 1052 */     registerItem(Items.rabbit_foot, "rabbit_foot");
/* 1053 */     registerItem(Items.rabbit_hide, "rabbit_hide");
/* 1054 */     registerItem(Items.rabbit_stew, "rabbit_stew");
/* 1055 */     registerItem(Items.rotten_flesh, "rotten_flesh");
/* 1056 */     registerItem(Items.ender_pearl, "ender_pearl");
/* 1057 */     registerItem(Items.blaze_rod, "blaze_rod");
/* 1058 */     registerItem(Items.ghast_tear, "ghast_tear");
/* 1059 */     registerItem(Items.gold_nugget, "gold_nugget");
/* 1060 */     registerItem(Items.nether_wart, "nether_wart");
/* 1061 */     this.itemModelMesher.register((Item)Items.potionitem, new ItemMeshDefinition() {
/*      */           public ModelResourceLocation getModelLocation(ItemStack stack) {
/* 1063 */             return ItemPotion.isSplash(stack.getMetadata()) ? new ModelResourceLocation("bottle_splash", "inventory") : new ModelResourceLocation("bottle_drinkable", "inventory");
/*      */           }
/*      */         });
/* 1066 */     registerItem(Items.glass_bottle, "glass_bottle");
/* 1067 */     registerItem(Items.spider_eye, "spider_eye");
/* 1068 */     registerItem(Items.fermented_spider_eye, "fermented_spider_eye");
/* 1069 */     registerItem(Items.blaze_powder, "blaze_powder");
/* 1070 */     registerItem(Items.magma_cream, "magma_cream");
/* 1071 */     registerItem(Items.brewing_stand, "brewing_stand");
/* 1072 */     registerItem(Items.cauldron, "cauldron");
/* 1073 */     registerItem(Items.ender_eye, "ender_eye");
/* 1074 */     registerItem(Items.speckled_melon, "speckled_melon");
/* 1075 */     this.itemModelMesher.register(Items.spawn_egg, new ItemMeshDefinition() {
/*      */           public ModelResourceLocation getModelLocation(ItemStack stack) {
/* 1077 */             return new ModelResourceLocation("spawn_egg", "inventory");
/*      */           }
/*      */         });
/* 1080 */     registerItem(Items.experience_bottle, "experience_bottle");
/* 1081 */     registerItem(Items.fire_charge, "fire_charge");
/* 1082 */     registerItem(Items.writable_book, "writable_book");
/* 1083 */     registerItem(Items.emerald, "emerald");
/* 1084 */     registerItem(Items.item_frame, "item_frame");
/* 1085 */     registerItem(Items.flower_pot, "flower_pot");
/* 1086 */     registerItem(Items.carrot, "carrot");
/* 1087 */     registerItem(Items.potato, "potato");
/* 1088 */     registerItem(Items.baked_potato, "baked_potato");
/* 1089 */     registerItem(Items.poisonous_potato, "poisonous_potato");
/* 1090 */     registerItem((Item)Items.map, "map");
/* 1091 */     registerItem(Items.golden_carrot, "golden_carrot");
/* 1092 */     registerItem(Items.skull, 0, "skull_skeleton");
/* 1093 */     registerItem(Items.skull, 1, "skull_wither");
/* 1094 */     registerItem(Items.skull, 2, "skull_zombie");
/* 1095 */     registerItem(Items.skull, 3, "skull_char");
/* 1096 */     registerItem(Items.skull, 4, "skull_creeper");
/* 1097 */     registerItem(Items.carrot_on_a_stick, "carrot_on_a_stick");
/* 1098 */     registerItem(Items.nether_star, "nether_star");
/* 1099 */     registerItem(Items.pumpkin_pie, "pumpkin_pie");
/* 1100 */     registerItem(Items.firework_charge, "firework_charge");
/* 1101 */     registerItem(Items.comparator, "comparator");
/* 1102 */     registerItem(Items.netherbrick, "netherbrick");
/* 1103 */     registerItem(Items.quartz, "quartz");
/* 1104 */     registerItem(Items.tnt_minecart, "tnt_minecart");
/* 1105 */     registerItem(Items.hopper_minecart, "hopper_minecart");
/* 1106 */     registerItem((Item)Items.armor_stand, "armor_stand");
/* 1107 */     registerItem(Items.iron_horse_armor, "iron_horse_armor");
/* 1108 */     registerItem(Items.golden_horse_armor, "golden_horse_armor");
/* 1109 */     registerItem(Items.diamond_horse_armor, "diamond_horse_armor");
/* 1110 */     registerItem(Items.lead, "lead");
/* 1111 */     registerItem(Items.name_tag, "name_tag");
/* 1112 */     this.itemModelMesher.register(Items.banner, new ItemMeshDefinition() {
/*      */           public ModelResourceLocation getModelLocation(ItemStack stack) {
/* 1114 */             return new ModelResourceLocation("banner", "inventory");
/*      */           }
/*      */         });
/* 1117 */     registerItem(Items.record_13, "record_13");
/* 1118 */     registerItem(Items.record_cat, "record_cat");
/* 1119 */     registerItem(Items.record_blocks, "record_blocks");
/* 1120 */     registerItem(Items.record_chirp, "record_chirp");
/* 1121 */     registerItem(Items.record_far, "record_far");
/* 1122 */     registerItem(Items.record_mall, "record_mall");
/* 1123 */     registerItem(Items.record_mellohi, "record_mellohi");
/* 1124 */     registerItem(Items.record_stal, "record_stal");
/* 1125 */     registerItem(Items.record_strad, "record_strad");
/* 1126 */     registerItem(Items.record_ward, "record_ward");
/* 1127 */     registerItem(Items.record_11, "record_11");
/* 1128 */     registerItem(Items.record_wait, "record_wait");
/* 1129 */     registerItem(Items.prismarine_shard, "prismarine_shard");
/* 1130 */     registerItem(Items.prismarine_crystals, "prismarine_crystals");
/* 1131 */     this.itemModelMesher.register((Item)Items.enchanted_book, new ItemMeshDefinition() {
/*      */           public ModelResourceLocation getModelLocation(ItemStack stack) {
/* 1133 */             return new ModelResourceLocation("enchanted_book", "inventory");
/*      */           }
/*      */         });
/* 1136 */     this.itemModelMesher.register((Item)Items.filled_map, new ItemMeshDefinition() {
/*      */           public ModelResourceLocation getModelLocation(ItemStack stack) {
/* 1138 */             return new ModelResourceLocation("filled_map", "inventory");
/*      */           }
/*      */         });
/* 1141 */     registerBlock(Blocks.command_block, "command_block");
/* 1142 */     registerItem(Items.fireworks, "fireworks");
/* 1143 */     registerItem(Items.command_block_minecart, "command_block_minecart");
/* 1144 */     registerBlock(Blocks.barrier, "barrier");
/* 1145 */     registerBlock(Blocks.mob_spawner, "mob_spawner");
/* 1146 */     registerItem(Items.written_book, "written_book");
/* 1147 */     registerBlock(Blocks.brown_mushroom_block, BlockHugeMushroom.EnumType.ALL_INSIDE.getMetadata(), "brown_mushroom_block");
/* 1148 */     registerBlock(Blocks.red_mushroom_block, BlockHugeMushroom.EnumType.ALL_INSIDE.getMetadata(), "red_mushroom_block");
/* 1149 */     registerBlock(Blocks.dragon_egg, "dragon_egg");
/*      */     
/* 1151 */     if (Reflector.ModelLoader_onRegisterItems.exists()) {
/* 1152 */       Reflector.call(Reflector.ModelLoader_onRegisterItems, new Object[] { this.itemModelMesher });
/*      */     }
/*      */   }
/*      */   
/*      */   public void onResourceManagerReload(IResourceManager resourceManager) {
/* 1157 */     this.itemModelMesher.rebuildCache();
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */