/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBoat;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderBoat;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.item.EntityBoat;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterBoat
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterBoat() {
/* 17 */     super(EntityBoat.class, "boat", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelBoat();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelBoat))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelBoat modelboat = (ModelBoat)model;
/* 34 */     return modelPart.equals("bottom") ? modelboat.boatSides[0] : (modelPart.equals("back") ? modelboat.boatSides[1] : (modelPart.equals("front") ? modelboat.boatSides[2] : (modelPart.equals("right") ? modelboat.boatSides[3] : (modelPart.equals("left") ? modelboat.boatSides[4] : null))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 40 */     return new String[] { "bottom", "back", "front", "right", "left" };
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 45 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 46 */     RenderBoat renderboat = new RenderBoat(rendermanager);
/*    */     
/* 48 */     if (!Reflector.RenderBoat_modelBoat.exists()) {
/*    */       
/* 50 */       Config.warn("Field not found: RenderBoat.modelBoat");
/* 51 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 55 */     Reflector.setFieldValue(renderboat, Reflector.RenderBoat_modelBoat, modelBase);
/* 56 */     renderboat.shadowSize = shadowSize;
/* 57 */     return (IEntityRenderer)renderboat;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */