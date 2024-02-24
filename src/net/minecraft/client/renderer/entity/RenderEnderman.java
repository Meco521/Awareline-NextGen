/*    */ package net.minecraft.client.renderer.entity;
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelEnderman;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerEndermanEyes;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerHeldBlock;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityEnderman;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderEnderman extends RenderLiving<EntityEnderman> {
/* 14 */   private static final ResourceLocation endermanTextures = new ResourceLocation("textures/entity/enderman/enderman.png");
/*    */   
/*    */   private final ModelEnderman endermanModel;
/*    */   
/* 18 */   private final Random rnd = new Random();
/*    */ 
/*    */   
/*    */   public RenderEnderman(RenderManager renderManagerIn) {
/* 22 */     super(renderManagerIn, (ModelBase)new ModelEnderman(0.0F), 0.5F);
/* 23 */     this.endermanModel = (ModelEnderman)this.mainModel;
/* 24 */     addLayer(new LayerEndermanEyes(this));
/* 25 */     addLayer(new LayerHeldBlock(this));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityEnderman entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 33 */     this.endermanModel.isCarrying = (entity.getHeldBlockState().getBlock().getMaterial() != Material.air);
/* 34 */     this.endermanModel.isAttacking = entity.isScreaming();
/*    */     
/* 36 */     if (entity.isScreaming()) {
/*    */       
/* 38 */       double d0 = 0.02D;
/* 39 */       x += this.rnd.nextGaussian() * d0;
/* 40 */       z += this.rnd.nextGaussian() * d0;
/*    */     } 
/*    */     
/* 43 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityEnderman entity) {
/* 51 */     return endermanTextures;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderEnderman.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */