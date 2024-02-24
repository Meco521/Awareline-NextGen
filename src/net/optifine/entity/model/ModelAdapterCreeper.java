/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelCreeper;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderCreeper;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.monster.EntityCreeper;
/*    */ 
/*    */ public class ModelAdapterCreeper
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterCreeper() {
/* 15 */     super(EntityCreeper.class, "creeper", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 20 */     return (ModelBase)new ModelCreeper();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 25 */     if (!(model instanceof ModelCreeper))
/*    */     {
/* 27 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 31 */     ModelCreeper modelcreeper = (ModelCreeper)model;
/* 32 */     return modelPart.equals("head") ? modelcreeper.head : (modelPart.equals("armor") ? modelcreeper.creeperArmor : (modelPart.equals("body") ? modelcreeper.body : (modelPart.equals("leg1") ? modelcreeper.leg1 : (modelPart.equals("leg2") ? modelcreeper.leg2 : (modelPart.equals("leg3") ? modelcreeper.leg3 : (modelPart.equals("leg4") ? modelcreeper.leg4 : null))))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 38 */     return new String[] { "head", "armor", "body", "leg1", "leg2", "leg3", "leg4" };
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 43 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 44 */     RenderCreeper rendercreeper = new RenderCreeper(rendermanager);
/* 45 */     rendercreeper.mainModel = modelBase;
/* 46 */     rendercreeper.shadowSize = shadowSize;
/* 47 */     return (IEntityRenderer)rendercreeper;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterCreeper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */