/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelEnderCrystal;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.tileentity.RenderEnderCrystal;
/*    */ import net.minecraft.entity.item.EntityEnderCrystal;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterEnderCrystal
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterEnderCrystal() {
/* 18 */     this("end_crystal");
/*    */   }
/*    */ 
/*    */   
/*    */   protected ModelAdapterEnderCrystal(String name) {
/* 23 */     super(EntityEnderCrystal.class, name, 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 28 */     return (ModelBase)new ModelEnderCrystal(0.0F, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 33 */     if (!(model instanceof ModelEnderCrystal))
/*    */     {
/* 35 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 39 */     ModelEnderCrystal modelendercrystal = (ModelEnderCrystal)model;
/* 40 */     return modelPart.equals("cube") ? (ModelRenderer)Reflector.getFieldValue(modelendercrystal, Reflector.ModelEnderCrystal_ModelRenderers, 0) : (modelPart.equals("glass") ? (ModelRenderer)Reflector.getFieldValue(modelendercrystal, Reflector.ModelEnderCrystal_ModelRenderers, 1) : (modelPart.equals("base") ? (ModelRenderer)Reflector.getFieldValue(modelendercrystal, Reflector.ModelEnderCrystal_ModelRenderers, 2) : null));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 46 */     return new String[] { "cube", "glass", "base" };
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 51 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 52 */     Render render = (Render)rendermanager.getEntityRenderMap().get(EntityEnderCrystal.class);
/*    */     
/* 54 */     if (!(render instanceof RenderEnderCrystal)) {
/*    */       
/* 56 */       Config.warn("Not an instance of RenderEnderCrystal: " + render);
/* 57 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 61 */     RenderEnderCrystal renderendercrystal = (RenderEnderCrystal)render;
/*    */     
/* 63 */     if (!Reflector.RenderEnderCrystal_modelEnderCrystal.exists()) {
/*    */       
/* 65 */       Config.warn("Field not found: RenderEnderCrystal.modelEnderCrystal");
/* 66 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 70 */     Reflector.setFieldValue(renderendercrystal, Reflector.RenderEnderCrystal_modelEnderCrystal, modelBase);
/* 71 */     renderendercrystal.shadowSize = shadowSize;
/* 72 */     return (IEntityRenderer)renderendercrystal;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterEnderCrystal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */