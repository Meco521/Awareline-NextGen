/*     */ package net.minecraft.client.renderer.entity.layers;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelQuadruped;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.RenderMooshroom;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntityMooshroom;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class LayerMooshroomMushroom implements LayerRenderer<EntityMooshroom> {
/*     */   private final RenderMooshroom mooshroomRenderer;
/*     */   private final ModelRenderer modelRendererMushroom;
/*  20 */   private static final ResourceLocation LOCATION_MUSHROOM_RED = new ResourceLocation("textures/entity/cow/mushroom_red.png");
/*     */   
/*     */   private static boolean hasTextureMushroom = false;
/*     */   
/*     */   public static void update() {
/*  25 */     hasTextureMushroom = Config.hasResource(LOCATION_MUSHROOM_RED);
/*     */   }
/*     */ 
/*     */   
/*     */   public LayerMooshroomMushroom(RenderMooshroom mooshroomRendererIn) {
/*  30 */     this.mooshroomRenderer = mooshroomRendererIn;
/*  31 */     this.modelRendererMushroom = new ModelRenderer(this.mooshroomRenderer.mainModel);
/*  32 */     this.modelRendererMushroom.setTextureSize(16, 16);
/*  33 */     this.modelRendererMushroom.rotationPointX = -6.0F;
/*  34 */     this.modelRendererMushroom.rotationPointZ = -8.0F;
/*  35 */     this.modelRendererMushroom.rotateAngleY = MathHelper.PI / 4.0F;
/*  36 */     int[][] aint = { null, null, { 16, 16, 0, 0 }, { 16, 16, 0, 0 }, null, null };
/*  37 */     this.modelRendererMushroom.addBox(aint, 0.0F, 0.0F, 10.0F, 20.0F, 16.0F, 0.0F, 0.0F);
/*  38 */     int[][] aint1 = { null, null, null, null, { 16, 16, 0, 0 }, { 16, 16, 0, 0 } };
/*  39 */     this.modelRendererMushroom.addBox(aint1, 10.0F, 0.0F, 0.0F, 0.0F, 16.0F, 20.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doRenderLayer(EntityMooshroom entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/*  44 */     if (!entitylivingbaseIn.isChild() && !entitylivingbaseIn.isInvisible()) {
/*     */       
/*  46 */       BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/*     */       
/*  48 */       if (hasTextureMushroom) {
/*     */         
/*  50 */         this.mooshroomRenderer.bindTexture(LOCATION_MUSHROOM_RED);
/*     */       }
/*     */       else {
/*     */         
/*  54 */         this.mooshroomRenderer.bindTexture(TextureMap.locationBlocksTexture);
/*     */       } 
/*     */       
/*  57 */       GlStateManager.enableCull();
/*  58 */       GlStateManager.cullFace(1028);
/*  59 */       GlStateManager.pushMatrix();
/*  60 */       GlStateManager.scale(1.0F, -1.0F, 1.0F);
/*  61 */       GlStateManager.translate(0.2F, 0.35F, 0.5F);
/*  62 */       GlStateManager.rotate(42.0F, 0.0F, 1.0F, 0.0F);
/*  63 */       GlStateManager.pushMatrix();
/*  64 */       GlStateManager.translate(-0.5F, -0.5F, 0.5F);
/*     */       
/*  66 */       if (hasTextureMushroom) {
/*     */         
/*  68 */         this.modelRendererMushroom.render(0.0625F);
/*     */       }
/*     */       else {
/*     */         
/*  72 */         blockrendererdispatcher.renderBlockBrightness(Blocks.red_mushroom.getDefaultState(), 1.0F);
/*     */       } 
/*     */       
/*  75 */       GlStateManager.popMatrix();
/*  76 */       GlStateManager.pushMatrix();
/*  77 */       GlStateManager.translate(0.1F, 0.0F, -0.6F);
/*  78 */       GlStateManager.rotate(42.0F, 0.0F, 1.0F, 0.0F);
/*  79 */       GlStateManager.translate(-0.5F, -0.5F, 0.5F);
/*     */       
/*  81 */       if (hasTextureMushroom) {
/*     */         
/*  83 */         this.modelRendererMushroom.render(0.0625F);
/*     */       }
/*     */       else {
/*     */         
/*  87 */         blockrendererdispatcher.renderBlockBrightness(Blocks.red_mushroom.getDefaultState(), 1.0F);
/*     */       } 
/*     */       
/*  90 */       GlStateManager.popMatrix();
/*  91 */       GlStateManager.popMatrix();
/*  92 */       GlStateManager.pushMatrix();
/*  93 */       ((ModelQuadruped)this.mooshroomRenderer.getMainModel()).head.postRender(0.0625F);
/*  94 */       GlStateManager.scale(1.0F, -1.0F, 1.0F);
/*  95 */       GlStateManager.translate(0.0F, 0.7F, -0.2F);
/*  96 */       GlStateManager.rotate(12.0F, 0.0F, 1.0F, 0.0F);
/*  97 */       GlStateManager.translate(-0.5F, -0.5F, 0.5F);
/*     */       
/*  99 */       if (hasTextureMushroom) {
/*     */         
/* 101 */         this.modelRendererMushroom.render(0.0625F);
/*     */       }
/*     */       else {
/*     */         
/* 105 */         blockrendererdispatcher.renderBlockBrightness(Blocks.red_mushroom.getDefaultState(), 1.0F);
/*     */       } 
/*     */       
/* 108 */       GlStateManager.popMatrix();
/* 109 */       GlStateManager.cullFace(1029);
/* 110 */       GlStateManager.disableCull();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldCombineTextures() {
/* 116 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\layers\LayerMooshroomMushroom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */