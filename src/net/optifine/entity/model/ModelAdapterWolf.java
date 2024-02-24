/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelWolf;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderWolf;
/*    */ import net.minecraft.entity.passive.EntityWolf;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterWolf
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterWolf() {
/* 16 */     super(EntityWolf.class, "wolf", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 21 */     return (ModelBase)new ModelWolf();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 26 */     if (!(model instanceof ModelWolf))
/*    */     {
/* 28 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 32 */     ModelWolf modelwolf = (ModelWolf)model;
/* 33 */     return modelPart.equals("head") ? modelwolf.wolfHeadMain : (modelPart.equals("body") ? modelwolf.wolfBody : (modelPart.equals("leg1") ? modelwolf.wolfLeg1 : (modelPart.equals("leg2") ? modelwolf.wolfLeg2 : (modelPart.equals("leg3") ? modelwolf.wolfLeg3 : (modelPart.equals("leg4") ? modelwolf.wolfLeg4 : (modelPart.equals("tail") ? (ModelRenderer)Reflector.getFieldValue(modelwolf, Reflector.ModelWolf_tail) : (modelPart.equals("mane") ? (ModelRenderer)Reflector.getFieldValue(modelwolf, Reflector.ModelWolf_mane) : null)))))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 39 */     return new String[] { "head", "body", "leg1", "leg2", "leg3", "leg4", "tail", "mane" };
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 44 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 45 */     RenderWolf renderwolf = new RenderWolf(rendermanager, modelBase, shadowSize);
/* 46 */     return (IEntityRenderer)renderwolf;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterWolf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */