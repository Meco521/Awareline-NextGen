/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelMinecart;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderMinecart;
/*    */ import net.minecraft.entity.item.EntityMinecart;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterMinecart
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterMinecart() {
/* 17 */     super(EntityMinecart.class, "minecart", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ModelAdapterMinecart(Class entityClass, String name, float shadow) {
/* 22 */     super(entityClass, name, shadow);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 27 */     return (ModelBase)new ModelMinecart();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 32 */     if (!(model instanceof ModelMinecart))
/*    */     {
/* 34 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 38 */     ModelMinecart modelminecart = (ModelMinecart)model;
/* 39 */     return modelPart.equals("bottom") ? modelminecart.sideModels[0] : (modelPart.equals("back") ? modelminecart.sideModels[1] : (modelPart.equals("front") ? modelminecart.sideModels[2] : (modelPart.equals("right") ? modelminecart.sideModels[3] : (modelPart.equals("left") ? modelminecart.sideModels[4] : (modelPart.equals("dirt") ? modelminecart.sideModels[5] : null)))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 45 */     return new String[] { "bottom", "back", "front", "right", "left", "dirt" };
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 50 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 51 */     RenderMinecart renderminecart = new RenderMinecart(rendermanager);
/*    */     
/* 53 */     if (!Reflector.RenderMinecart_modelMinecart.exists()) {
/*    */       
/* 55 */       Config.warn("Field not found: RenderMinecart.modelMinecart");
/* 56 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 60 */     Reflector.setFieldValue(renderminecart, Reflector.RenderMinecart_modelMinecart, modelBase);
/* 61 */     renderminecart.shadowSize = shadowSize;
/* 62 */     return (IEntityRenderer)renderminecart;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterMinecart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */