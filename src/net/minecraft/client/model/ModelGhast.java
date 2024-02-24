/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class ModelGhast
/*    */   extends ModelBase
/*    */ {
/*    */   ModelRenderer body;
/* 12 */   ModelRenderer[] tentacles = new ModelRenderer[9];
/*    */ 
/*    */   
/*    */   public ModelGhast() {
/* 16 */     int i = -16;
/* 17 */     this.body = new ModelRenderer(this, 0, 0);
/* 18 */     this.body.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
/* 19 */     this.body.rotationPointY += (24 + i);
/* 20 */     Random random = new Random(1660L);
/*    */     
/* 22 */     for (int j = 0; j < this.tentacles.length; j++) {
/*    */       
/* 24 */       this.tentacles[j] = new ModelRenderer(this, 0, 0);
/* 25 */       float f = (((j % 3) - (j / 3 % 2) * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
/* 26 */       float f1 = ((j / 3) / 2.0F * 2.0F - 1.0F) * 5.0F;
/* 27 */       int k = random.nextInt(7) + 8;
/* 28 */       this.tentacles[j].addBox(-1.0F, 0.0F, -1.0F, 2, k, 2);
/* 29 */       (this.tentacles[j]).rotationPointX = f;
/* 30 */       (this.tentacles[j]).rotationPointZ = f1;
/* 31 */       (this.tentacles[j]).rotationPointY = (31 + i);
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
/* 42 */     for (int i = 0; i < this.tentacles.length; i++)
/*    */     {
/* 44 */       (this.tentacles[i]).rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + i) + 0.4F;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/* 53 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/* 54 */     GlStateManager.pushMatrix();
/* 55 */     GlStateManager.translate(0.0F, 0.6F, 0.0F);
/* 56 */     this.body.render(scale);
/*    */     
/* 58 */     for (ModelRenderer modelrenderer : this.tentacles)
/*    */     {
/* 60 */       modelrenderer.render(scale);
/*    */     }
/*    */     
/* 63 */     GlStateManager.popMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\model\ModelGhast.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */