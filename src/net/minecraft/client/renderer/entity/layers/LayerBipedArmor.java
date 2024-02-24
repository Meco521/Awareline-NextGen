/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*    */ 
/*    */ public class LayerBipedArmor
/*    */   extends LayerArmorBase<ModelBiped> {
/*    */   public LayerBipedArmor(RendererLivingEntity<?> rendererIn) {
/* 10 */     super(rendererIn);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void initArmor() {
/* 15 */     this.modelLeggings = new ModelBiped(0.5F);
/* 16 */     this.modelArmor = new ModelBiped(1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setModelPartVisible(ModelBiped model, int armorSlot) {
/* 21 */     setModelVisible(model);
/*    */     
/* 23 */     switch (armorSlot) {
/*    */       
/*    */       case 1:
/* 26 */         model.bipedRightLeg.showModel = true;
/* 27 */         model.bipedLeftLeg.showModel = true;
/*    */         break;
/*    */       
/*    */       case 2:
/* 31 */         model.bipedBody.showModel = true;
/* 32 */         model.bipedRightLeg.showModel = true;
/* 33 */         model.bipedLeftLeg.showModel = true;
/*    */         break;
/*    */       
/*    */       case 3:
/* 37 */         model.bipedBody.showModel = true;
/* 38 */         model.bipedRightArm.showModel = true;
/* 39 */         model.bipedLeftArm.showModel = true;
/*    */         break;
/*    */       
/*    */       case 4:
/* 43 */         model.bipedHead.showModel = true;
/* 44 */         model.bipedHeadwear.showModel = true;
/*    */         break;
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void setModelVisible(ModelBiped model) {
/* 50 */     model.setInvisible(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\layers\LayerBipedArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */