/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelWither;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerWitherAura;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.boss.BossStatus;
/*    */ import net.minecraft.entity.boss.EntityWither;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderWither extends RenderLiving<EntityWither> {
/* 12 */   private static final ResourceLocation invulnerableWitherTextures = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
/* 13 */   private static final ResourceLocation witherTextures = new ResourceLocation("textures/entity/wither/wither.png");
/*    */ 
/*    */   
/*    */   public RenderWither(RenderManager renderManagerIn) {
/* 17 */     super(renderManagerIn, (ModelBase)new ModelWither(0.0F), 1.0F);
/* 18 */     addLayer(new LayerWitherAura(this));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityWither entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 26 */     BossStatus.setBossStatus((IBossDisplayData)entity, true);
/* 27 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityWither entity) {
/* 35 */     int i = entity.getInvulTime();
/* 36 */     return (i > 0 && (i > 80 || i / 5 % 2 != 1)) ? invulnerableWitherTextures : witherTextures;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityWither entitylivingbaseIn, float partialTickTime) {
/* 45 */     float f = 2.0F;
/* 46 */     int i = entitylivingbaseIn.getInvulTime();
/*    */     
/* 48 */     if (i > 0)
/*    */     {
/* 50 */       f -= (i - partialTickTime) / 220.0F * 0.5F;
/*    */     }
/*    */     
/* 53 */     GlStateManager.scale(f, f, f);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderWither.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */