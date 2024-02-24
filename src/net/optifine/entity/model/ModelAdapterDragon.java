/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelDragon;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderDragon;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.boss.EntityDragon;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterDragon
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterDragon() {
/* 16 */     super(EntityDragon.class, "dragon", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 21 */     return (ModelBase)new ModelDragon(0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 26 */     if (!(model instanceof ModelDragon))
/*    */     {
/* 28 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 32 */     ModelDragon modeldragon = (ModelDragon)model;
/* 33 */     return modelPart.equals("head") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 0) : (modelPart.equals("spine") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 1) : (modelPart.equals("jaw") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 2) : (modelPart.equals("body") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 3) : (modelPart.equals("rear_leg") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 4) : (modelPart.equals("front_leg") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 5) : (modelPart.equals("rear_leg_tip") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 6) : (modelPart.equals("front_leg_tip") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 7) : (modelPart.equals("rear_foot") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 8) : (modelPart.equals("front_foot") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 9) : (modelPart.equals("wing") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 10) : (modelPart.equals("wing_tip") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 11) : null)))))))))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 39 */     return new String[] { "head", "spine", "jaw", "body", "rear_leg", "front_leg", "rear_leg_tip", "front_leg_tip", "rear_foot", "front_foot", "wing", "wing_tip" };
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 44 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 45 */     RenderDragon renderdragon = new RenderDragon(rendermanager);
/* 46 */     renderdragon.mainModel = modelBase;
/* 47 */     renderdragon.shadowSize = shadowSize;
/* 48 */     return (IEntityRenderer)renderdragon;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterDragon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */