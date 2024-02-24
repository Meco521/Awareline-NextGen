/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.entity.RenderEnderman;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityEnderman;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.optifine.shaders.Shaders;
/*    */ 
/*    */ public class LayerEndermanEyes implements LayerRenderer<EntityEnderman> {
/* 13 */   private static final ResourceLocation RES_ENDERMAN_EYES = new ResourceLocation("textures/entity/enderman/enderman_eyes.png");
/*    */   
/*    */   private final RenderEnderman endermanRenderer;
/*    */   
/*    */   public LayerEndermanEyes(RenderEnderman endermanRendererIn) {
/* 18 */     this.endermanRenderer = endermanRendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityEnderman entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 23 */     this.endermanRenderer.bindTexture(RES_ENDERMAN_EYES);
/* 24 */     GlStateManager.enableBlend();
/* 25 */     GlStateManager.disableAlpha();
/* 26 */     GlStateManager.blendFunc(1, 1);
/* 27 */     GlStateManager.disableLighting();
/* 28 */     GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());
/* 29 */     int i = 61680;
/* 30 */     int j = i % 65536;
/* 31 */     int k = i / 65536;
/* 32 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
/* 33 */     GlStateManager.enableLighting();
/* 34 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 36 */     if (Config.isShaders())
/*    */     {
/* 38 */       Shaders.beginSpiderEyes();
/*    */     }
/*    */     
/* 41 */     (Config.getRenderGlobal()).renderOverlayEyes = true;
/* 42 */     this.endermanRenderer.getMainModel().render((Entity)entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
/* 43 */     (Config.getRenderGlobal()).renderOverlayEyes = false;
/*    */     
/* 45 */     if (Config.isShaders())
/*    */     {
/* 47 */       Shaders.endSpiderEyes();
/*    */     }
/*    */     
/* 50 */     this.endermanRenderer.setLightmap((EntityLiving)entitylivingbaseIn, partialTicks);
/* 51 */     GlStateManager.depthMask(true);
/* 52 */     GlStateManager.disableBlend();
/* 53 */     GlStateManager.enableAlpha();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 58 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\layers\LayerEndermanEyes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */