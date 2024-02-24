/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class ModelHumanoidHead
/*    */   extends ModelSkeletonHead {
/*  7 */   private final ModelRenderer head = new ModelRenderer(this, 32, 0);
/*    */ 
/*    */   
/*    */   public ModelHumanoidHead() {
/* 11 */     super(0, 0, 64, 64);
/* 12 */     this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.25F);
/* 13 */     this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/* 21 */     super.render(entityIn, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale);
/* 22 */     this.head.render(scale);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 32 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/* 33 */     this.head.rotateAngleY = this.skeletonHead.rotateAngleY;
/* 34 */     this.head.rotateAngleX = this.skeletonHead.rotateAngleX;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\model\ModelHumanoidHead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */