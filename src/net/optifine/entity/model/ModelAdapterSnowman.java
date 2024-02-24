/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelSnowMan;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSnowMan;
/*    */ import net.minecraft.entity.monster.EntitySnowman;
/*    */ 
/*    */ public class ModelAdapterSnowman
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterSnowman() {
/* 15 */     super(EntitySnowman.class, "snow_golem", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 20 */     return (ModelBase)new ModelSnowMan();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 25 */     if (!(model instanceof ModelSnowMan))
/*    */     {
/* 27 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 31 */     ModelSnowMan modelsnowman = (ModelSnowMan)model;
/* 32 */     return modelPart.equals("body") ? modelsnowman.body : (modelPart.equals("body_bottom") ? modelsnowman.bottomBody : (modelPart.equals("head") ? modelsnowman.head : (modelPart.equals("left_hand") ? modelsnowman.leftHand : (modelPart.equals("right_hand") ? modelsnowman.rightHand : null))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 38 */     return new String[] { "body", "body_bottom", "head", "right_hand", "left_hand" };
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 43 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 44 */     RenderSnowMan rendersnowman = new RenderSnowMan(rendermanager);
/* 45 */     rendersnowman.mainModel = modelBase;
/* 46 */     rendersnowman.shadowSize = shadowSize;
/* 47 */     return (IEntityRenderer)rendersnowman;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterSnowman.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */