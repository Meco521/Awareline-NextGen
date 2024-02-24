/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelWitch;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderWitch;
/*    */ import net.minecraft.entity.monster.EntityWitch;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterWitch
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterWitch() {
/* 16 */     super(EntityWitch.class, "witch", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 21 */     return (ModelBase)new ModelWitch(0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 26 */     if (!(model instanceof ModelWitch))
/*    */     {
/* 28 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 32 */     ModelWitch modelwitch = (ModelWitch)model;
/* 33 */     return modelPart.equals("mole") ? (ModelRenderer)Reflector.getFieldValue(modelwitch, Reflector.ModelWitch_mole) : (modelPart.equals("hat") ? (ModelRenderer)Reflector.getFieldValue(modelwitch, Reflector.ModelWitch_hat) : (modelPart.equals("head") ? modelwitch.villagerHead : (modelPart.equals("body") ? modelwitch.villagerBody : (modelPart.equals("arms") ? modelwitch.villagerArms : (modelPart.equals("left_leg") ? modelwitch.leftVillagerLeg : (modelPart.equals("right_leg") ? modelwitch.rightVillagerLeg : (modelPart.equals("nose") ? modelwitch.villagerNose : null)))))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 39 */     return new String[] { "mole", "head", "body", "arms", "right_leg", "left_leg", "nose" };
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 44 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 45 */     RenderWitch renderwitch = new RenderWitch(rendermanager);
/* 46 */     renderwitch.mainModel = modelBase;
/* 47 */     renderwitch.shadowSize = shadowSize;
/* 48 */     return (IEntityRenderer)renderwitch;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterWitch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */