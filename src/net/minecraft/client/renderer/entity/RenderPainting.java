/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityPainting;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class RenderPainting extends Render<EntityPainting> {
/*  16 */   private static final ResourceLocation KRISTOFFER_PAINTING_TEXTURE = new ResourceLocation("textures/painting/paintings_kristoffer_zetterstrand.png");
/*     */ 
/*     */   
/*     */   public RenderPainting(RenderManager renderManagerIn) {
/*  20 */     super(renderManagerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(EntityPainting entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  28 */     GlStateManager.pushMatrix();
/*  29 */     GlStateManager.translate(x, y, z);
/*  30 */     GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
/*  31 */     GlStateManager.enableRescaleNormal();
/*  32 */     bindEntityTexture(entity);
/*  33 */     EntityPainting.EnumArt entitypainting$enumart = entity.art;
/*  34 */     float f = 0.0625F;
/*  35 */     GlStateManager.scale(f, f, f);
/*  36 */     renderPainting(entity, entitypainting$enumart.sizeX, entitypainting$enumart.sizeY, entitypainting$enumart.offsetX, entitypainting$enumart.offsetY);
/*  37 */     GlStateManager.disableRescaleNormal();
/*  38 */     GlStateManager.popMatrix();
/*  39 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityPainting entity) {
/*  47 */     return KRISTOFFER_PAINTING_TEXTURE;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderPainting(EntityPainting painting, int width, int height, int textureU, int textureV) {
/*  52 */     float f = -width / 2.0F;
/*  53 */     float f1 = -height / 2.0F;
/*  54 */     float f2 = 0.5F;
/*  55 */     float f3 = 0.75F;
/*  56 */     float f4 = 0.8125F;
/*  57 */     float f5 = 0.0F;
/*  58 */     float f6 = 0.0625F;
/*  59 */     float f7 = 0.75F;
/*  60 */     float f8 = 0.8125F;
/*  61 */     float f9 = 0.001953125F;
/*  62 */     float f10 = 0.001953125F;
/*  63 */     float f11 = 0.7519531F;
/*  64 */     float f12 = 0.7519531F;
/*  65 */     float f13 = 0.0F;
/*  66 */     float f14 = 0.0625F;
/*     */     
/*  68 */     for (int i = 0; i < width / 16; i++) {
/*     */       
/*  70 */       for (int j = 0; j < height / 16; j++) {
/*     */         
/*  72 */         float f15 = f + (i + 1 << 4);
/*  73 */         float f16 = f + (i << 4);
/*  74 */         float f17 = f1 + (j + 1 << 4);
/*  75 */         float f18 = f1 + (j << 4);
/*  76 */         setLightmap(painting, (f15 + f16) / 2.0F, (f17 + f18) / 2.0F);
/*  77 */         float f19 = (textureU + width - (i << 4)) / 256.0F;
/*  78 */         float f20 = (textureU + width - (i + 1 << 4)) / 256.0F;
/*  79 */         float f21 = (textureV + height - (j << 4)) / 256.0F;
/*  80 */         float f22 = (textureV + height - (j + 1 << 4)) / 256.0F;
/*  81 */         Tessellator tessellator = Tessellator.getInstance();
/*  82 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  83 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
/*  84 */         worldrenderer.pos(f15, f18, -f2).tex(f20, f21).normal(0.0F, 0.0F, -1.0F).endVertex();
/*  85 */         worldrenderer.pos(f16, f18, -f2).tex(f19, f21).normal(0.0F, 0.0F, -1.0F).endVertex();
/*  86 */         worldrenderer.pos(f16, f17, -f2).tex(f19, f22).normal(0.0F, 0.0F, -1.0F).endVertex();
/*  87 */         worldrenderer.pos(f15, f17, -f2).tex(f20, f22).normal(0.0F, 0.0F, -1.0F).endVertex();
/*  88 */         worldrenderer.pos(f15, f17, f2).tex(f3, f5).normal(0.0F, 0.0F, 1.0F).endVertex();
/*  89 */         worldrenderer.pos(f16, f17, f2).tex(f4, f5).normal(0.0F, 0.0F, 1.0F).endVertex();
/*  90 */         worldrenderer.pos(f16, f18, f2).tex(f4, f6).normal(0.0F, 0.0F, 1.0F).endVertex();
/*  91 */         worldrenderer.pos(f15, f18, f2).tex(f3, f6).normal(0.0F, 0.0F, 1.0F).endVertex();
/*  92 */         worldrenderer.pos(f15, f17, -f2).tex(f7, f9).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  93 */         worldrenderer.pos(f16, f17, -f2).tex(f8, f9).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  94 */         worldrenderer.pos(f16, f17, f2).tex(f8, f10).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  95 */         worldrenderer.pos(f15, f17, f2).tex(f7, f10).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  96 */         worldrenderer.pos(f15, f18, f2).tex(f7, f9).normal(0.0F, -1.0F, 0.0F).endVertex();
/*  97 */         worldrenderer.pos(f16, f18, f2).tex(f8, f9).normal(0.0F, -1.0F, 0.0F).endVertex();
/*  98 */         worldrenderer.pos(f16, f18, -f2).tex(f8, f10).normal(0.0F, -1.0F, 0.0F).endVertex();
/*  99 */         worldrenderer.pos(f15, f18, -f2).tex(f7, f10).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 100 */         worldrenderer.pos(f15, f17, f2).tex(f12, f13).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 101 */         worldrenderer.pos(f15, f18, f2).tex(f12, f14).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 102 */         worldrenderer.pos(f15, f18, -f2).tex(f11, f14).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 103 */         worldrenderer.pos(f15, f17, -f2).tex(f11, f13).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 104 */         worldrenderer.pos(f16, f17, -f2).tex(f12, f13).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 105 */         worldrenderer.pos(f16, f18, -f2).tex(f12, f14).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 106 */         worldrenderer.pos(f16, f18, f2).tex(f11, f14).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 107 */         worldrenderer.pos(f16, f17, f2).tex(f11, f13).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 108 */         tessellator.draw();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setLightmap(EntityPainting painting, float p_77008_2_, float p_77008_3_) {
/* 115 */     int i = MathHelper.floor_double(painting.posX);
/* 116 */     int j = MathHelper.floor_double(painting.posY + (p_77008_3_ / 16.0F));
/* 117 */     int k = MathHelper.floor_double(painting.posZ);
/* 118 */     EnumFacing enumfacing = painting.facingDirection;
/*     */     
/* 120 */     if (enumfacing == EnumFacing.NORTH)
/*     */     {
/* 122 */       i = MathHelper.floor_double(painting.posX + (p_77008_2_ / 16.0F));
/*     */     }
/*     */     
/* 125 */     if (enumfacing == EnumFacing.WEST)
/*     */     {
/* 127 */       k = MathHelper.floor_double(painting.posZ - (p_77008_2_ / 16.0F));
/*     */     }
/*     */     
/* 130 */     if (enumfacing == EnumFacing.SOUTH)
/*     */     {
/* 132 */       i = MathHelper.floor_double(painting.posX - (p_77008_2_ / 16.0F));
/*     */     }
/*     */     
/* 135 */     if (enumfacing == EnumFacing.EAST)
/*     */     {
/* 137 */       k = MathHelper.floor_double(painting.posZ + (p_77008_2_ / 16.0F));
/*     */     }
/*     */     
/* 140 */     int l = this.renderManager.worldObj.getCombinedLight(new BlockPos(i, j, k), 0);
/* 141 */     int i1 = l % 65536;
/* 142 */     int j1 = l / 65536;
/* 143 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i1, j1);
/* 144 */     GlStateManager.color(1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderPainting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */