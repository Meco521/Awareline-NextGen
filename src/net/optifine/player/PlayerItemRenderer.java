/*    */ package net.optifine.player;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ public class PlayerItemRenderer
/*    */ {
/*  8 */   private int attachTo = 0;
/*  9 */   private ModelRenderer modelRenderer = null;
/*    */ 
/*    */   
/*    */   public PlayerItemRenderer(int attachTo, ModelRenderer modelRenderer) {
/* 13 */     this.attachTo = attachTo;
/* 14 */     this.modelRenderer = modelRenderer;
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer() {
/* 19 */     return this.modelRenderer;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(ModelBiped modelBiped, float scale) {
/* 24 */     ModelRenderer modelrenderer = PlayerItemModel.getAttachModel(modelBiped, this.attachTo);
/*    */     
/* 26 */     if (modelrenderer != null)
/*    */     {
/* 28 */       modelrenderer.postRender(scale);
/*    */     }
/*    */     
/* 31 */     this.modelRenderer.render(scale);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\player\PlayerItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */