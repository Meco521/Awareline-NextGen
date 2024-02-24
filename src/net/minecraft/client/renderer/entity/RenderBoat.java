/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBoat;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityBoat;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderBoat extends Render<EntityBoat> {
/* 12 */   private static final ResourceLocation boatTextures = new ResourceLocation("textures/entity/boat.png");
/*    */ 
/*    */   
/* 15 */   protected ModelBase modelBoat = (ModelBase)new ModelBoat();
/*    */ 
/*    */   
/*    */   public RenderBoat(RenderManager renderManagerIn) {
/* 19 */     super(renderManagerIn);
/* 20 */     this.shadowSize = 0.5F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityBoat entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 28 */     GlStateManager.pushMatrix();
/* 29 */     GlStateManager.translate((float)x, (float)y + 0.25F, (float)z);
/* 30 */     GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
/* 31 */     float f = entity.getTimeSinceHit() - partialTicks;
/* 32 */     float f1 = entity.getDamageTaken() - partialTicks;
/*    */     
/* 34 */     if (f1 < 0.0F)
/*    */     {
/* 36 */       f1 = 0.0F;
/*    */     }
/*    */     
/* 39 */     if (f > 0.0F)
/*    */     {
/* 41 */       GlStateManager.rotate(MathHelper.sin(f) * f * f1 / 10.0F * entity.getForwardDirection(), 1.0F, 0.0F, 0.0F);
/*    */     }
/*    */     
/* 44 */     float f2 = 0.75F;
/* 45 */     GlStateManager.scale(f2, f2, f2);
/* 46 */     GlStateManager.scale(1.0F / f2, 1.0F / f2, 1.0F / f2);
/* 47 */     bindEntityTexture(entity);
/* 48 */     GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 49 */     this.modelBoat.render((Entity)entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
/* 50 */     GlStateManager.popMatrix();
/* 51 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityBoat entity) {
/* 59 */     return boatTextures;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */