/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.optifine.entity.model.anim.ModelUpdater;
/*    */ 
/*    */ 
/*    */ public class CustomModelRenderer
/*    */ {
/*    */   private final String modelPart;
/*    */   private final boolean attach;
/*    */   private final ModelRenderer modelRenderer;
/*    */   private final ModelUpdater modelUpdater;
/*    */   
/*    */   public CustomModelRenderer(String modelPart, boolean attach, ModelRenderer modelRenderer, ModelUpdater modelUpdater) {
/* 15 */     this.modelPart = modelPart;
/* 16 */     this.attach = attach;
/* 17 */     this.modelRenderer = modelRenderer;
/* 18 */     this.modelUpdater = modelUpdater;
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer() {
/* 23 */     return this.modelRenderer;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getModelPart() {
/* 28 */     return this.modelPart;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAttach() {
/* 33 */     return this.attach;
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelUpdater getModelUpdater() {
/* 38 */     return this.modelUpdater;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\CustomModelRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */