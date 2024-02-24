/*    */ package net.minecraft.client.renderer.tileentity;
/*    */ 
/*    */ import net.minecraft.client.model.ModelSkeletonHead;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderWitherSkull extends Render<EntityWitherSkull> {
/* 12 */   private static final ResourceLocation invulnerableWitherTextures = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
/* 13 */   private static final ResourceLocation witherTextures = new ResourceLocation("textures/entity/wither/wither.png");
/*    */ 
/*    */   
/* 16 */   private final ModelSkeletonHead skeletonHeadModel = new ModelSkeletonHead();
/*    */ 
/*    */   
/*    */   public RenderWitherSkull(RenderManager renderManagerIn) {
/* 20 */     super(renderManagerIn);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private float func_82400_a(float p_82400_1_, float p_82400_2_, float p_82400_3_) {
/*    */     float f;
/* 27 */     for (f = p_82400_2_ - p_82400_1_; f < -180.0F; f += 360.0F);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 32 */     while (f >= 180.0F)
/*    */     {
/* 34 */       f -= 360.0F;
/*    */     }
/*    */     
/* 37 */     return p_82400_1_ + p_82400_3_ * f;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityWitherSkull entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 45 */     GlStateManager.pushMatrix();
/* 46 */     GlStateManager.disableCull();
/* 47 */     float f = func_82400_a(entity.prevRotationYaw, entity.rotationYaw, partialTicks);
/* 48 */     float f1 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
/* 49 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 50 */     float f2 = 0.0625F;
/* 51 */     GlStateManager.enableRescaleNormal();
/* 52 */     GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 53 */     GlStateManager.enableAlpha();
/* 54 */     bindEntityTexture((Entity)entity);
/* 55 */     this.skeletonHeadModel.render((Entity)entity, 0.0F, 0.0F, 0.0F, f, f1, f2);
/* 56 */     GlStateManager.popMatrix();
/* 57 */     super.doRender((Entity)entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityWitherSkull entity) {
/* 65 */     return entity.isInvulnerable() ? invulnerableWitherTextures : witherTextures;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\tileentity\RenderWitherSkull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */