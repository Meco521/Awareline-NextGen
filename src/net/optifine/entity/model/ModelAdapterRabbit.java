/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRabbit;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderRabbit;
/*    */ import net.minecraft.entity.passive.EntityRabbit;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterRabbit
/*    */   extends ModelAdapter
/*    */ {
/* 17 */   private static Map<String, Integer> mapPartFields = null;
/*    */ 
/*    */   
/*    */   public ModelAdapterRabbit() {
/* 21 */     super(EntityRabbit.class, "rabbit", 0.3F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 26 */     return (ModelBase)new ModelRabbit();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 31 */     if (!(model instanceof ModelRabbit))
/*    */     {
/* 33 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 37 */     ModelRabbit modelrabbit = (ModelRabbit)model;
/* 38 */     Map<String, Integer> map = getMapPartFields();
/*    */     
/* 40 */     if (map.containsKey(modelPart)) {
/*    */       
/* 42 */       int i = ((Integer)map.get(modelPart)).intValue();
/* 43 */       return (ModelRenderer)Reflector.getFieldValue(modelrabbit, Reflector.ModelRabbit_renderers, i);
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
/* 54 */     return new String[] { "left_foot", "right_foot", "left_thigh", "right_thigh", "body", "left_arm", "right_arm", "head", "right_ear", "left_ear", "tail", "nose" };
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
/* 66 */     mapPartFields.put("left_foot", Integer.valueOf(0));
/* 67 */     mapPartFields.put("right_foot", Integer.valueOf(1));
/* 68 */     mapPartFields.put("left_thigh", Integer.valueOf(2));
/* 69 */     mapPartFields.put("right_thigh", Integer.valueOf(3));
/* 70 */     mapPartFields.put("body", Integer.valueOf(4));
/* 71 */     mapPartFields.put("left_arm", Integer.valueOf(5));
/* 72 */     mapPartFields.put("right_arm", Integer.valueOf(6));
/* 73 */     mapPartFields.put("head", Integer.valueOf(7));
/* 74 */     mapPartFields.put("right_ear", Integer.valueOf(8));
/* 75 */     mapPartFields.put("left_ear", Integer.valueOf(9));
/* 76 */     mapPartFields.put("tail", Integer.valueOf(10));
/* 77 */     mapPartFields.put("nose", Integer.valueOf(11));
/* 78 */     return mapPartFields;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 84 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 85 */     RenderRabbit renderrabbit = new RenderRabbit(rendermanager, modelBase, shadowSize);
/* 86 */     return (IEntityRenderer)renderrabbit;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterRabbit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */