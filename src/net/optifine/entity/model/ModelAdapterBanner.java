/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBanner;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.tileentity.TileEntityBanner;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterBanner
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterBanner() {
/* 17 */     super(TileEntityBanner.class, "banner", 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelBanner();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelBanner))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelBanner modelbanner = (ModelBanner)model;
/* 34 */     return modelPart.equals("slate") ? modelbanner.bannerSlate : (modelPart.equals("stand") ? modelbanner.bannerStand : (modelPart.equals("top") ? modelbanner.bannerTop : null));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 40 */     return new String[] { "slate", "stand", "top" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     TileEntityBannerRenderer tileEntityBannerRenderer;
/* 45 */     TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
/* 46 */     TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getSpecialRendererByClass(TileEntityBanner.class);
/*    */     
/* 48 */     if (!(tileentityspecialrenderer instanceof TileEntityBannerRenderer))
/*    */     {
/* 50 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 54 */     if (tileentityspecialrenderer.getEntityClass() == null) {
/*    */       
/* 56 */       tileEntityBannerRenderer = new TileEntityBannerRenderer();
/* 57 */       tileEntityBannerRenderer.setRendererDispatcher(tileentityrendererdispatcher);
/*    */     } 
/*    */     
/* 60 */     if (!Reflector.TileEntityBannerRenderer_bannerModel.exists()) {
/*    */       
/* 62 */       Config.warn("Field not found: TileEntityBannerRenderer.bannerModel");
/* 63 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 67 */     Reflector.setFieldValue(tileEntityBannerRenderer, Reflector.TileEntityBannerRenderer_bannerModel, modelBase);
/* 68 */     return (IEntityRenderer)tileEntityBannerRenderer;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterBanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */