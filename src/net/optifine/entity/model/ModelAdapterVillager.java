/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelVillager;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderVillager;
/*    */ import net.minecraft.entity.passive.EntityVillager;
/*    */ 
/*    */ public class ModelAdapterVillager
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterVillager() {
/* 15 */     super(EntityVillager.class, "villager", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 20 */     return (ModelBase)new ModelVillager(0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 25 */     if (!(model instanceof ModelVillager))
/*    */     {
/* 27 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 31 */     ModelVillager modelvillager = (ModelVillager)model;
/* 32 */     return modelPart.equals("head") ? modelvillager.villagerHead : (modelPart.equals("body") ? modelvillager.villagerBody : (modelPart.equals("arms") ? modelvillager.villagerArms : (modelPart.equals("left_leg") ? modelvillager.leftVillagerLeg : (modelPart.equals("right_leg") ? modelvillager.rightVillagerLeg : (modelPart.equals("nose") ? modelvillager.villagerNose : null)))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 38 */     return new String[] { "head", "body", "arms", "right_leg", "left_leg", "nose" };
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 43 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 44 */     RenderVillager rendervillager = new RenderVillager(rendermanager);
/* 45 */     rendervillager.mainModel = modelBase;
/* 46 */     rendervillager.shadowSize = shadowSize;
/* 47 */     return (IEntityRenderer)rendervillager;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterVillager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */