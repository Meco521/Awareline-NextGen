/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityXPOrb;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.optifine.CustomColors;
/*    */ 
/*    */ public class RenderXPOrb extends Render<EntityXPOrb> {
/* 16 */   private static final ResourceLocation experienceOrbTextures = new ResourceLocation("textures/entity/experience_orb.png");
/*    */ 
/*    */   
/*    */   public RenderXPOrb(RenderManager renderManagerIn) {
/* 20 */     super(renderManagerIn);
/* 21 */     this.shadowSize = 0.15F;
/* 22 */     this.shadowOpaque = 0.75F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityXPOrb entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 30 */     GlStateManager.pushMatrix();
/* 31 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 32 */     bindEntityTexture(entity);
/* 33 */     int i = entity.getTextureByXP();
/* 34 */     float f = (i % 4 << 4) / 64.0F;
/* 35 */     float f1 = ((i % 4 << 4) + 16) / 64.0F;
/* 36 */     float f2 = (i / 4 << 4) / 64.0F;
/* 37 */     float f3 = ((i / 4 << 4) + 16) / 64.0F;
/* 38 */     float f4 = 1.0F;
/* 39 */     float f5 = 0.5F;
/* 40 */     float f6 = 0.25F;
/* 41 */     int j = entity.getBrightnessForRender(partialTicks);
/* 42 */     int k = j % 65536;
/* 43 */     int l = j / 65536;
/* 44 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k, l);
/* 45 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 46 */     float f7 = 255.0F;
/* 47 */     float f8 = (entity.xpColor + partialTicks) / 2.0F;
/*    */     
/* 49 */     if (Config.isCustomColors())
/*    */     {
/* 51 */       f8 = CustomColors.getXpOrbTimer(f8);
/*    */     }
/*    */     
/* 54 */     l = (int)((MathHelper.sin(f8 + 0.0F) + 1.0F) * 0.5F * 255.0F);
/* 55 */     int i1 = 255;
/* 56 */     int j1 = (int)((MathHelper.sin(f8 + 4.1887903F) + 1.0F) * 0.1F * 255.0F);
/* 57 */     GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 58 */     GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 59 */     float f9 = 0.3F;
/* 60 */     GlStateManager.scale(0.3F, 0.3F, 0.3F);
/* 61 */     Tessellator tessellator = Tessellator.getInstance();
/* 62 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 63 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
/* 64 */     int k1 = l;
/* 65 */     int l1 = 255;
/* 66 */     int i2 = j1;
/*    */     
/* 68 */     if (Config.isCustomColors()) {
/*    */       
/* 70 */       int j2 = CustomColors.getXpOrbColor(f8);
/*    */       
/* 72 */       if (j2 >= 0) {
/*    */         
/* 74 */         k1 = j2 >> 16 & 0xFF;
/* 75 */         l1 = j2 >> 8 & 0xFF;
/* 76 */         i2 = j2 & 0xFF;
/*    */       } 
/*    */     } 
/*    */     
/* 80 */     worldrenderer.pos((0.0F - f5), (0.0F - f6), 0.0D).tex(f, f3).color(k1, l1, i2, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 81 */     worldrenderer.pos((f4 - f5), (0.0F - f6), 0.0D).tex(f1, f3).color(k1, l1, i2, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 82 */     worldrenderer.pos((f4 - f5), (1.0F - f6), 0.0D).tex(f1, f2).color(k1, l1, i2, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 83 */     worldrenderer.pos((0.0F - f5), (1.0F - f6), 0.0D).tex(f, f2).color(k1, l1, i2, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 84 */     tessellator.draw();
/* 85 */     GlStateManager.disableBlend();
/* 86 */     GlStateManager.disableRescaleNormal();
/* 87 */     GlStateManager.popMatrix();
/* 88 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityXPOrb entity) {
/* 96 */     return experienceOrbTextures;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderXPOrb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */