/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.model.ModelBox;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.projectile.EntityArrow;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ public class LayerArrow
/*    */   implements LayerRenderer<EntityLivingBase>
/*    */ {
/*    */   private final RendererLivingEntity field_177168_a;
/*    */   
/*    */   public LayerArrow(RendererLivingEntity p_i46124_1_) {
/* 21 */     this.field_177168_a = p_i46124_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 26 */     int i = entitylivingbaseIn.getArrowCountInEntity();
/*    */     
/* 28 */     if (i > 0) {
/*    */       
/* 30 */       EntityArrow entityArrow = new EntityArrow(entitylivingbaseIn.worldObj, entitylivingbaseIn.posX, entitylivingbaseIn.posY, entitylivingbaseIn.posZ);
/* 31 */       Random random = new Random(entitylivingbaseIn.getEntityId());
/* 32 */       RenderHelper.disableStandardItemLighting();
/*    */       
/* 34 */       for (int j = 0; j < i; j++) {
/*    */         
/* 36 */         GlStateManager.pushMatrix();
/* 37 */         ModelRenderer modelrenderer = this.field_177168_a.getMainModel().getRandomModelBox(random);
/* 38 */         ModelBox modelbox = modelrenderer.cubeList.get(random.nextInt(modelrenderer.cubeList.size()));
/* 39 */         modelrenderer.postRender(0.0625F);
/* 40 */         float f = random.nextFloat();
/* 41 */         float f1 = random.nextFloat();
/* 42 */         float f2 = random.nextFloat();
/* 43 */         float f3 = (modelbox.posX1 + (modelbox.posX2 - modelbox.posX1) * f) / 16.0F;
/* 44 */         float f4 = (modelbox.posY1 + (modelbox.posY2 - modelbox.posY1) * f1) / 16.0F;
/* 45 */         float f5 = (modelbox.posZ1 + (modelbox.posZ2 - modelbox.posZ1) * f2) / 16.0F;
/* 46 */         GlStateManager.translate(f3, f4, f5);
/* 47 */         f = f * 2.0F - 1.0F;
/* 48 */         f1 = f1 * 2.0F - 1.0F;
/* 49 */         f2 = f2 * 2.0F - 1.0F;
/* 50 */         f *= -1.0F;
/* 51 */         f1 *= -1.0F;
/* 52 */         f2 *= -1.0F;
/* 53 */         float f6 = MathHelper.sqrt_float(f * f + f2 * f2);
/* 54 */         ((Entity)entityArrow).prevRotationYaw = ((Entity)entityArrow).rotationYaw = (float)(Math.atan2(f, f2) * 180.0D / Math.PI);
/* 55 */         ((Entity)entityArrow).prevRotationPitch = ((Entity)entityArrow).rotationPitch = (float)(Math.atan2(f1, f6) * 180.0D / Math.PI);
/* 56 */         double d0 = 0.0D;
/* 57 */         double d1 = 0.0D;
/* 58 */         double d2 = 0.0D;
/* 59 */         this.field_177168_a.getRenderManager().renderEntityWithPosYaw((Entity)entityArrow, d0, d1, d2, 0.0F, partialTicks);
/* 60 */         GlStateManager.popMatrix();
/*    */       } 
/*    */       
/* 63 */       RenderHelper.enableStandardItemLighting();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 69 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\layers\LayerArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */