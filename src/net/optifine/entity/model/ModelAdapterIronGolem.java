/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelIronGolem;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderIronGolem;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.monster.EntityIronGolem;
/*    */ 
/*    */ public class ModelAdapterIronGolem
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterIronGolem() {
/* 15 */     super(EntityIronGolem.class, "iron_golem", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 20 */     return (ModelBase)new ModelIronGolem();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 25 */     if (!(model instanceof ModelIronGolem))
/*    */     {
/* 27 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 31 */     ModelIronGolem modelirongolem = (ModelIronGolem)model;
/* 32 */     return modelPart.equals("head") ? modelirongolem.ironGolemHead : (modelPart.equals("body") ? modelirongolem.ironGolemBody : (modelPart.equals("left_arm") ? modelirongolem.ironGolemLeftArm : (modelPart.equals("right_arm") ? modelirongolem.ironGolemRightArm : (modelPart.equals("left_leg") ? modelirongolem.ironGolemLeftLeg : (modelPart.equals("right_leg") ? modelirongolem.ironGolemRightLeg : null)))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 38 */     return new String[] { "head", "body", "right_arm", "left_arm", "left_leg", "right_leg" };
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 43 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 44 */     RenderIronGolem renderirongolem = new RenderIronGolem(rendermanager);
/* 45 */     renderirongolem.mainModel = modelBase;
/* 46 */     renderirongolem.shadowSize = shadowSize;
/* 47 */     return (IEntityRenderer)renderirongolem;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterIronGolem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */