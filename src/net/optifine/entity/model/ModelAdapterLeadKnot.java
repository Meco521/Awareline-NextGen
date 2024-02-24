/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelLeashKnot;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderLeashKnot;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.EntityLeashKnot;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterLeadKnot
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterLeadKnot() {
/* 17 */     super(EntityLeashKnot.class, "lead_knot", 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelLeashKnot();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelLeashKnot))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelLeashKnot modelleashknot = (ModelLeashKnot)model;
/* 34 */     return modelPart.equals("knot") ? modelleashknot.field_110723_a : null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 40 */     return new String[] { "knot" };
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 45 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 46 */     RenderLeashKnot renderleashknot = new RenderLeashKnot(rendermanager);
/*    */     
/* 48 */     if (!Reflector.RenderLeashKnot_leashKnotModel.exists()) {
/*    */       
/* 50 */       Config.warn("Field not found: RenderLeashKnot.leashKnotModel");
/* 51 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 55 */     Reflector.setFieldValue(renderleashknot, Reflector.RenderLeashKnot_leashKnotModel, modelBase);
/* 56 */     renderleashknot.shadowSize = shadowSize;
/* 57 */     return (IEntityRenderer)renderleashknot;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterLeadKnot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */