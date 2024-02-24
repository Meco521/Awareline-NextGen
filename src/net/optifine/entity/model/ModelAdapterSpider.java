/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelSpider;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSpider;
/*    */ import net.minecraft.entity.monster.EntitySpider;
/*    */ 
/*    */ public class ModelAdapterSpider
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterSpider() {
/* 15 */     super(EntitySpider.class, "spider", 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ModelAdapterSpider(Class entityClass, String name, float shadowSize) {
/* 20 */     super(entityClass, name, shadowSize);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 25 */     return (ModelBase)new ModelSpider();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 30 */     if (!(model instanceof ModelSpider))
/*    */     {
/* 32 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 36 */     ModelSpider modelspider = (ModelSpider)model;
/* 37 */     return modelPart.equals("head") ? modelspider.spiderHead : (modelPart.equals("neck") ? modelspider.spiderNeck : (modelPart.equals("body") ? modelspider.spiderBody : (modelPart.equals("leg1") ? modelspider.spiderLeg1 : (modelPart.equals("leg2") ? modelspider.spiderLeg2 : (modelPart.equals("leg3") ? modelspider.spiderLeg3 : (modelPart.equals("leg4") ? modelspider.spiderLeg4 : (modelPart.equals("leg5") ? modelspider.spiderLeg5 : (modelPart.equals("leg6") ? modelspider.spiderLeg6 : (modelPart.equals("leg7") ? modelspider.spiderLeg7 : (modelPart.equals("leg8") ? modelspider.spiderLeg8 : null))))))))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 43 */     return new String[] { "head", "neck", "body", "leg1", "leg2", "leg3", "leg4", "leg5", "leg6", "leg7", "leg8" };
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 48 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 49 */     RenderSpider renderspider = new RenderSpider(rendermanager);
/* 50 */     renderspider.mainModel = modelBase;
/* 51 */     renderspider.shadowSize = shadowSize;
/* 52 */     return (IEntityRenderer)renderspider;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterSpider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */