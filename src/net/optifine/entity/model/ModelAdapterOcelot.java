/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelOcelot;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderOcelot;
/*    */ import net.minecraft.entity.passive.EntityOcelot;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterOcelot
/*    */   extends ModelAdapter
/*    */ {
/* 17 */   private static Map<String, Integer> mapPartFields = null;
/*    */ 
/*    */   
/*    */   public ModelAdapterOcelot() {
/* 21 */     super(EntityOcelot.class, "ocelot", 0.4F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 26 */     return (ModelBase)new ModelOcelot();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 31 */     if (!(model instanceof ModelOcelot))
/*    */     {
/* 33 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 37 */     ModelOcelot modelocelot = (ModelOcelot)model;
/* 38 */     Map<String, Integer> map = getMapPartFields();
/*    */     
/* 40 */     if (map.containsKey(modelPart)) {
/*    */       
/* 42 */       int i = ((Integer)map.get(modelPart)).intValue();
/* 43 */       return (ModelRenderer)Reflector.getFieldValue(modelocelot, Reflector.ModelOcelot_ModelRenderers, i);
/*    */     } 
/*    */ 
/*    */     
/* 47 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 54 */     return new String[] { "back_left_leg", "back_right_leg", "front_left_leg", "front_right_leg", "tail", "tail2", "head", "body" };
/*    */   }
/*    */ 
/*    */   
/*    */   private static Map<String, Integer> getMapPartFields() {
/* 59 */     if (mapPartFields != null)
/*    */     {
/* 61 */       return mapPartFields;
/*    */     }
/*    */ 
/*    */     
/* 65 */     mapPartFields = new HashMap<>();
/* 66 */     mapPartFields.put("back_left_leg", Integer.valueOf(0));
/* 67 */     mapPartFields.put("back_right_leg", Integer.valueOf(1));
/* 68 */     mapPartFields.put("front_left_leg", Integer.valueOf(2));
/* 69 */     mapPartFields.put("front_right_leg", Integer.valueOf(3));
/* 70 */     mapPartFields.put("tail", Integer.valueOf(4));
/* 71 */     mapPartFields.put("tail2", Integer.valueOf(5));
/* 72 */     mapPartFields.put("head", Integer.valueOf(6));
/* 73 */     mapPartFields.put("body", Integer.valueOf(7));
/* 74 */     return mapPartFields;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 80 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 81 */     RenderOcelot renderocelot = new RenderOcelot(rendermanager, modelBase, shadowSize);
/* 82 */     return (IEntityRenderer)renderocelot;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterOcelot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */