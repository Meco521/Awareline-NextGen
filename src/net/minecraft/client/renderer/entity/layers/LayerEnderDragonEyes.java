/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.entity.RenderDragon;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.boss.EntityDragon;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.optifine.shaders.Shaders;
/*    */ 
/*    */ public class LayerEnderDragonEyes implements LayerRenderer<EntityDragon> {
/* 13 */   private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
/*    */   
/*    */   private final RenderDragon dragonRenderer;
/*    */   
/*    */   public LayerEnderDragonEyes(RenderDragon dragonRendererIn) {
/* 18 */     this.dragonRenderer = dragonRendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityDragon entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 23 */     this.dragonRenderer.bindTexture(TEXTURE);
/* 24 */     GlStateManager.enableBlend();
/* 25 */     GlStateManager.disableAlpha();
/* 26 */     GlStateManager.blendFunc(1, 1);
/* 27 */     GlStateManager.disableLighting();
/* 28 */     GlStateManager.depthFunc(514);
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
/* 42 */     this.dragonRenderer.getMainModel().render((Entity)entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
/* 43 */     (Config.getRenderGlobal()).renderOverlayEyes = false;
/*    */     
/* 45 */     if (Config.isShaders())
/*    */     {
/* 47 */       Shaders.endSpiderEyes();
/*    */     }
/*    */     
/* 50 */     this.dragonRenderer.setLightmap((EntityLiving)entitylivingbaseIn, partialTicks);
/* 51 */     GlStateManager.disableBlend();
/* 52 */     GlStateManager.enableAlpha();
/* 53 */     GlStateManager.depthFunc(515);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 58 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\layers\LayerEnderDragonEyes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */