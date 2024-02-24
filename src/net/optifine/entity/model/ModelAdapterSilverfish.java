/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelSilverfish;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSilverfish;
/*    */ import net.minecraft.entity.monster.EntitySilverfish;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterSilverfish
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterSilverfish() {
/* 17 */     super(EntitySilverfish.class, "silverfish", 0.3F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelSilverfish();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelSilverfish))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelSilverfish modelsilverfish = (ModelSilverfish)model;
/* 34 */     String s = "body";
/*    */     
/* 36 */     if (modelPart.startsWith(s)) {
/*    */       
/* 38 */       ModelRenderer[] amodelrenderer1 = (ModelRenderer[])Reflector.getFieldValue(modelsilverfish, Reflector.ModelSilverfish_bodyParts);
/*    */       
/* 40 */       if (amodelrenderer1 == null)
/*    */       {
/* 42 */         return null;
/*    */       }
/*    */ 
/*    */       
/* 46 */       String s3 = modelPart.substring(s.length());
/* 47 */       int j = Config.parseInt(s3, -1);
/* 48 */       j--; return (
/* 49 */         j >= 0 && j < amodelrenderer1.length) ? amodelrenderer1[j] : null;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 54 */     String s1 = "wing";
/*    */     
/* 56 */     if (modelPart.startsWith(s1)) {
/*    */       
/* 58 */       ModelRenderer[] amodelrenderer = (ModelRenderer[])Reflector.getFieldValue(modelsilverfish, Reflector.ModelSilverfish_wingParts);
/*    */       
/* 60 */       if (amodelrenderer == null)
/*    */       {
/* 62 */         return null;
/*    */       }
/*    */ 
/*    */       
/* 66 */       String s2 = modelPart.substring(s1.length());
/* 67 */       int i = Config.parseInt(s2, -1);
/* 68 */       i--; return (
/* 69 */         i >= 0 && i < amodelrenderer.length) ? amodelrenderer[i] : null;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 74 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 82 */     return new String[] { "body1", "body2", "body3", "body4", "body5", "body6", "body7", "wing1", "wing2", "wing3" };
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 87 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 88 */     RenderSilverfish rendersilverfish = new RenderSilverfish(rendermanager);
/* 89 */     rendersilverfish.mainModel = modelBase;
/* 90 */     rendersilverfish.shadowSize = shadowSize;
/* 91 */     return (IEntityRenderer)rendersilverfish;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterSilverfish.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */