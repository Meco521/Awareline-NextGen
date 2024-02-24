/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.entity.RenderSpider;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntitySpider;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.optifine.shaders.Shaders;
/*    */ 
/*    */ public class LayerSpiderEyes implements LayerRenderer<EntitySpider> {
/* 13 */   private static final ResourceLocation SPIDER_EYES = new ResourceLocation("textures/entity/spider_eyes.png");
/*    */   
/*    */   private final RenderSpider spiderRenderer;
/*    */   
/*    */   public LayerSpiderEyes(RenderSpider spiderRendererIn) {
/* 18 */     this.spiderRenderer = spiderRendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntitySpider entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 23 */     this.spiderRenderer.bindTexture(SPIDER_EYES);
/* 24 */     GlStateManager.enableBlend();
/* 25 */     GlStateManager.disableAlpha();
/* 26 */     GlStateManager.blendFunc(1, 1);
/*    */     
/* 28 */     if (entitylivingbaseIn.isInvisible()) {
/*    */       
/* 30 */       GlStateManager.depthMask(false);
/*    */     }
/*    */     else {
/*    */       
/* 34 */       GlStateManager.depthMask(true);
/*    */     } 
/*    */     
/* 37 */     int i = 61680;
/* 38 */     int j = i % 65536;
/* 39 */     int k = i / 65536;
/* 40 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
/* 41 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 43 */     if (Config.isShaders())
/*    */     {
/* 45 */       Shaders.beginSpiderEyes();
/*    */     }
/*    */     
/* 48 */     (Config.getRenderGlobal()).renderOverlayEyes = true;
/* 49 */     this.spiderRenderer.getMainModel().render((Entity)entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
/* 50 */     (Config.getRenderGlobal()).renderOverlayEyes = false;
/*    */     
/* 52 */     if (Config.isShaders())
/*    */     {
/* 54 */       Shaders.endSpiderEyes();
/*    */     }
/*    */     
/* 57 */     i = entitylivingbaseIn.getBrightnessForRender(partialTicks);
/* 58 */     j = i % 65536;
/* 59 */     k = i / 65536;
/* 60 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
/* 61 */     this.spiderRenderer.setLightmap((EntityLiving)entitylivingbaseIn, partialTicks);
/* 62 */     GlStateManager.disableBlend();
/* 63 */     GlStateManager.enableAlpha();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 68 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\layers\LayerSpiderEyes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */