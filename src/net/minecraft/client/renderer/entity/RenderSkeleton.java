/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelSkeleton;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntitySkeleton;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderSkeleton extends RenderBiped<EntitySkeleton> {
/* 12 */   private static final ResourceLocation skeletonTextures = new ResourceLocation("textures/entity/skeleton/skeleton.png");
/* 13 */   private static final ResourceLocation witherSkeletonTextures = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
/*    */ 
/*    */   
/*    */   public RenderSkeleton(RenderManager renderManagerIn) {
/* 17 */     super(renderManagerIn, (ModelBiped)new ModelSkeleton(), 0.5F);
/* 18 */     addLayer(new LayerHeldItem(this));
/* 19 */     addLayer(new LayerBipedArmor(this)
/*    */         {
/*    */           protected void initArmor()
/*    */           {
/* 23 */             this.modelLeggings = (ModelBase)new ModelSkeleton(0.5F, true);
/* 24 */             this.modelArmor = (ModelBase)new ModelSkeleton(1.0F, true);
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntitySkeleton entitylivingbaseIn, float partialTickTime) {
/* 35 */     if (entitylivingbaseIn.getSkeletonType() == 1)
/*    */     {
/* 37 */       GlStateManager.scale(1.2F, 1.2F, 1.2F);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void transformHeldFull3DItemLayer() {
/* 43 */     GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntitySkeleton entity) {
/* 51 */     return (entity.getSkeletonType() == 1) ? witherSkeletonTextures : skeletonTextures;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderSkeleton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */