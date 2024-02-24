/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelChicken;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderChicken;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.passive.EntityChicken;
/*    */ 
/*    */ public class ModelAdapterChicken
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterChicken() {
/* 15 */     super(EntityChicken.class, "chicken", 0.3F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 20 */     return (ModelBase)new ModelChicken();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 25 */     if (!(model instanceof ModelChicken))
/*    */     {
/* 27 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 31 */     ModelChicken modelchicken = (ModelChicken)model;
/* 32 */     return modelPart.equals("head") ? modelchicken.head : (modelPart.equals("body") ? modelchicken.body : (modelPart.equals("right_leg") ? modelchicken.rightLeg : (modelPart.equals("left_leg") ? modelchicken.leftLeg : (modelPart.equals("right_wing") ? modelchicken.rightWing : (modelPart.equals("left_wing") ? modelchicken.leftWing : (modelPart.equals("bill") ? modelchicken.bill : (modelPart.equals("chin") ? modelchicken.chin : null)))))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 38 */     return new String[] { "head", "body", "right_leg", "left_leg", "right_wing", "left_wing", "bill", "chin" };
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 43 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 44 */     RenderChicken renderchicken = new RenderChicken(rendermanager, modelBase, shadowSize);
/* 45 */     return (IEntityRenderer)renderchicken;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterChicken.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */