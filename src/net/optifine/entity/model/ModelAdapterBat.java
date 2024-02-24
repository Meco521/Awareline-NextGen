/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBat;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderBat;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.passive.EntityBat;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterBat
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterBat() {
/* 16 */     super(EntityBat.class, "bat", 0.25F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 21 */     return (ModelBase)new ModelBat();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 26 */     if (!(model instanceof ModelBat))
/*    */     {
/* 28 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 32 */     ModelBat modelbat = (ModelBat)model;
/* 33 */     return modelPart.equals("head") ? (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 0) : (modelPart.equals("body") ? (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 1) : (modelPart.equals("right_wing") ? (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 2) : (modelPart.equals("left_wing") ? (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 3) : (modelPart.equals("outer_right_wing") ? (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 4) : (modelPart.equals("outer_left_wing") ? (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 5) : null)))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 39 */     return new String[] { "head", "body", "right_wing", "left_wing", "outer_right_wing", "outer_left_wing" };
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 44 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 45 */     RenderBat renderbat = new RenderBat(rendermanager);
/* 46 */     renderbat.mainModel = modelBase;
/* 47 */     renderbat.shadowSize = shadowSize;
/* 48 */     return (IEntityRenderer)renderbat;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterBat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */