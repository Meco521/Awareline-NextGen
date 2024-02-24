/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelSquid
/*    */   extends ModelBase
/*    */ {
/*    */   ModelRenderer squidBody;
/* 11 */   ModelRenderer[] squidTentacles = new ModelRenderer[8];
/*    */ 
/*    */   
/*    */   public ModelSquid() {
/* 15 */     int i = -16;
/* 16 */     this.squidBody = new ModelRenderer(this, 0, 0);
/* 17 */     this.squidBody.addBox(-6.0F, -8.0F, -6.0F, 12, 16, 12);
/* 18 */     this.squidBody.rotationPointY += (24 + i);
/*    */     
/* 20 */     for (int j = 0; j < this.squidTentacles.length; j++) {
/*    */       
/* 22 */       this.squidTentacles[j] = new ModelRenderer(this, 48, 0);
/* 23 */       double d0 = j * Math.PI * 2.0D / this.squidTentacles.length;
/* 24 */       float f = (float)Math.cos(d0) * 5.0F;
/* 25 */       float f1 = (float)Math.sin(d0) * 5.0F;
/* 26 */       this.squidTentacles[j].addBox(-1.0F, 0.0F, -1.0F, 2, 18, 2);
/* 27 */       (this.squidTentacles[j]).rotationPointX = f;
/* 28 */       (this.squidTentacles[j]).rotationPointZ = f1;
/* 29 */       (this.squidTentacles[j]).rotationPointY = (31 + i);
/* 30 */       d0 = j * Math.PI * -2.0D / this.squidTentacles.length + 1.5707963267948966D;
/* 31 */       (this.squidTentacles[j]).rotateAngleY = (float)d0;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 42 */     for (ModelRenderer modelrenderer : this.squidTentacles)
/*    */     {
/* 44 */       modelrenderer.rotateAngleX = ageInTicks;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/* 53 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/* 54 */     this.squidBody.render(scale);
/*    */     
/* 56 */     for (int i = 0; i < this.squidTentacles.length; i++)
/*    */     {
/* 58 */       this.squidTentacles[i].render(scale);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\model\ModelSquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */