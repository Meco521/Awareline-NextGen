/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelEnderMite;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderEndermite;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.monster.EntityEndermite;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterEndermite
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterEndermite() {
/* 17 */     super(EntityEndermite.class, "endermite", 0.3F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelEnderMite();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelEnderMite))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelEnderMite modelendermite = (ModelEnderMite)model;
/* 34 */     String s = "body";
/*    */     
/* 36 */     if (modelPart.startsWith(s)) {
/*    */       
/* 38 */       ModelRenderer[] amodelrenderer = (ModelRenderer[])Reflector.getFieldValue(modelendermite, Reflector.ModelEnderMite_bodyParts);
/*    */       
/* 40 */       if (amodelrenderer == null)
/*    */       {
/* 42 */         return null;
/*    */       }
/*    */ 
/*    */       
/* 46 */       String s1 = modelPart.substring(s.length());
/* 47 */       int i = Config.parseInt(s1, -1);
/* 48 */       i--; return (
/* 49 */         i >= 0 && i < amodelrenderer.length) ? amodelrenderer[i] : null;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 54 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 61 */     return new String[] { "body1", "body2", "body3", "body4" };
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 66 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 67 */     RenderEndermite renderendermite = new RenderEndermite(rendermanager);
/* 68 */     renderendermite.mainModel = modelBase;
/* 69 */     renderendermite.shadowSize = shadowSize;
/* 70 */     return (IEntityRenderer)renderendermite;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterEndermite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */