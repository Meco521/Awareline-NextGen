/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.projectile.EntityArrow;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class RenderArrow extends Render<EntityArrow> {
/* 14 */   private static final ResourceLocation arrowTextures = new ResourceLocation("textures/entity/arrow.png");
/*    */ 
/*    */   
/*    */   public RenderArrow(RenderManager renderManagerIn) {
/* 18 */     super(renderManagerIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityArrow entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 26 */     bindEntityTexture(entity);
/* 27 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 28 */     GlStateManager.pushMatrix();
/* 29 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 30 */     GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
/* 31 */     GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
/* 32 */     Tessellator tessellator = Tessellator.getInstance();
/* 33 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 34 */     int i = 0;
/* 35 */     float f = 0.0F;
/* 36 */     float f1 = 0.5F;
/* 37 */     float f2 = (i * 10) / 32.0F;
/* 38 */     float f3 = (5 + i * 10) / 32.0F;
/* 39 */     float f4 = 0.0F;
/* 40 */     float f5 = 0.15625F;
/* 41 */     float f6 = (5 + i * 10) / 32.0F;
/* 42 */     float f7 = (10 + i * 10) / 32.0F;
/* 43 */     float f8 = 0.05625F;
/* 44 */     GlStateManager.enableRescaleNormal();
/* 45 */     float f9 = entity.arrowShake - partialTicks;
/*    */     
/* 47 */     if (f9 > 0.0F) {
/*    */       
/* 49 */       float f10 = -MathHelper.sin(f9 * 3.0F) * f9;
/* 50 */       GlStateManager.rotate(f10, 0.0F, 0.0F, 1.0F);
/*    */     } 
/*    */     
/* 53 */     GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
/* 54 */     GlStateManager.scale(f8, f8, f8);
/* 55 */     GlStateManager.translate(-4.0F, 0.0F, 0.0F);
/* 56 */     GL11.glNormal3f(f8, 0.0F, 0.0F);
/* 57 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 58 */     worldrenderer.pos(-7.0D, -2.0D, -2.0D).tex(f4, f6).endVertex();
/* 59 */     worldrenderer.pos(-7.0D, -2.0D, 2.0D).tex(f5, f6).endVertex();
/* 60 */     worldrenderer.pos(-7.0D, 2.0D, 2.0D).tex(f5, f7).endVertex();
/* 61 */     worldrenderer.pos(-7.0D, 2.0D, -2.0D).tex(f4, f7).endVertex();
/* 62 */     tessellator.draw();
/* 63 */     GL11.glNormal3f(-f8, 0.0F, 0.0F);
/* 64 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 65 */     worldrenderer.pos(-7.0D, 2.0D, -2.0D).tex(f4, f6).endVertex();
/* 66 */     worldrenderer.pos(-7.0D, 2.0D, 2.0D).tex(f5, f6).endVertex();
/* 67 */     worldrenderer.pos(-7.0D, -2.0D, 2.0D).tex(f5, f7).endVertex();
/* 68 */     worldrenderer.pos(-7.0D, -2.0D, -2.0D).tex(f4, f7).endVertex();
/* 69 */     tessellator.draw();
/*    */     
/* 71 */     for (int j = 0; j < 4; j++) {
/*    */       
/* 73 */       GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 74 */       GL11.glNormal3f(0.0F, 0.0F, f8);
/* 75 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 76 */       worldrenderer.pos(-8.0D, -2.0D, 0.0D).tex(f, f2).endVertex();
/* 77 */       worldrenderer.pos(8.0D, -2.0D, 0.0D).tex(f1, f2).endVertex();
/* 78 */       worldrenderer.pos(8.0D, 2.0D, 0.0D).tex(f1, f3).endVertex();
/* 79 */       worldrenderer.pos(-8.0D, 2.0D, 0.0D).tex(f, f3).endVertex();
/* 80 */       tessellator.draw();
/*    */     } 
/*    */     
/* 83 */     GlStateManager.disableRescaleNormal();
/* 84 */     GlStateManager.popMatrix();
/* 85 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityArrow entity) {
/* 93 */     return arrowTextures;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */