/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelWitch;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityWitch;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderWitch extends RenderLiving<EntityWitch> {
/* 11 */   private static final ResourceLocation witchTextures = new ResourceLocation("textures/entity/witch.png");
/*    */ 
/*    */   
/*    */   public RenderWitch(RenderManager renderManagerIn) {
/* 15 */     super(renderManagerIn, (ModelBase)new ModelWitch(0.0F), 0.5F);
/* 16 */     addLayer(new LayerHeldItemWitch(this));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityWitch entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 24 */     ((ModelWitch)this.mainModel).field_82900_g = (entity.getHeldItem() != null);
/* 25 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityWitch entity) {
/* 33 */     return witchTextures;
/*    */   }
/*    */ 
/*    */   
/*    */   public void transformHeldFull3DItemLayer() {
/* 38 */     GlStateManager.translate(0.0F, 0.1875F, 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityWitch entitylivingbaseIn, float partialTickTime) {
/* 47 */     float f = 0.9375F;
/* 48 */     GlStateManager.scale(f, f, f);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderWitch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */