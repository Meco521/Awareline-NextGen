/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.model.ModelLeashKnot;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLeashKnot;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderLeashKnot extends Render<EntityLeashKnot> {
/* 10 */   private static final ResourceLocation leashKnotTextures = new ResourceLocation("textures/entity/lead_knot.png");
/* 11 */   private final ModelLeashKnot leashKnotModel = new ModelLeashKnot();
/*    */ 
/*    */   
/*    */   public RenderLeashKnot(RenderManager renderManagerIn) {
/* 15 */     super(renderManagerIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityLeashKnot entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 23 */     GlStateManager.pushMatrix();
/* 24 */     GlStateManager.disableCull();
/* 25 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 26 */     float f = 0.0625F;
/* 27 */     GlStateManager.enableRescaleNormal();
/* 28 */     GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 29 */     GlStateManager.enableAlpha();
/* 30 */     bindEntityTexture(entity);
/* 31 */     this.leashKnotModel.render((Entity)entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, f);
/* 32 */     GlStateManager.popMatrix();
/* 33 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityLeashKnot entity) {
/* 41 */     return leashKnotTextures;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderLeashKnot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */