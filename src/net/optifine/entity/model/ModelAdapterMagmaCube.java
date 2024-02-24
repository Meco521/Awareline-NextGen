/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelMagmaCube;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderMagmaCube;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.monster.EntityMagmaCube;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterMagmaCube
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterMagmaCube() {
/* 17 */     super(EntityMagmaCube.class, "magma_cube", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelMagmaCube();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelMagmaCube))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelMagmaCube modelmagmacube = (ModelMagmaCube)model;
/*    */     
/* 35 */     if (modelPart.equals("core"))
/*    */     {
/* 37 */       return (ModelRenderer)Reflector.getFieldValue(modelmagmacube, Reflector.ModelMagmaCube_core);
/*    */     }
/*    */ 
/*    */     
/* 41 */     String s = "segment";
/*    */     
/* 43 */     if (modelPart.startsWith(s)) {
/*    */       
/* 45 */       ModelRenderer[] amodelrenderer = (ModelRenderer[])Reflector.getFieldValue(modelmagmacube, Reflector.ModelMagmaCube_segments);
/*    */       
/* 47 */       if (amodelrenderer == null)
/*    */       {
/* 49 */         return null;
/*    */       }
/*    */ 
/*    */       
/* 53 */       String s1 = modelPart.substring(s.length());
/* 54 */       int i = Config.parseInt(s1, -1);
/* 55 */       i--; return (
/* 56 */         i >= 0 && i < amodelrenderer.length) ? amodelrenderer[i] : null;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 61 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 69 */     return new String[] { "core", "segment1", "segment2", "segment3", "segment4", "segment5", "segment6", "segment7", "segment8" };
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 74 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 75 */     RenderMagmaCube rendermagmacube = new RenderMagmaCube(rendermanager);
/* 76 */     rendermagmacube.mainModel = modelBase;
/* 77 */     rendermagmacube.shadowSize = shadowSize;
/* 78 */     return (IEntityRenderer)rendermagmacube;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterMagmaCube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */