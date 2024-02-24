/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelSkeletonHead;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.tileentity.TileEntitySkull;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterHeadSkeleton
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterHeadSkeleton() {
/* 17 */     super(TileEntitySkull.class, "head_skeleton", 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelSkeletonHead(0, 0, 64, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelSkeletonHead))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelSkeletonHead modelskeletonhead = (ModelSkeletonHead)model;
/* 34 */     return modelPart.equals("head") ? modelskeletonhead.skeletonHead : null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 40 */     return new String[] { "head" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     TileEntitySkullRenderer tileEntitySkullRenderer;
/* 45 */     TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
/* 46 */     TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getSpecialRendererByClass(TileEntitySkull.class);
/*    */     
/* 48 */     if (!(tileentityspecialrenderer instanceof TileEntitySkullRenderer))
/*    */     {
/* 50 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 54 */     if (tileentityspecialrenderer.getEntityClass() == null) {
/*    */       
/* 56 */       tileEntitySkullRenderer = new TileEntitySkullRenderer();
/* 57 */       tileEntitySkullRenderer.setRendererDispatcher(tileentityrendererdispatcher);
/*    */     } 
/*    */     
/* 60 */     if (!Reflector.TileEntitySkullRenderer_humanoidHead.exists()) {
/*    */       
/* 62 */       Config.warn("Field not found: TileEntitySkullRenderer.humanoidHead");
/* 63 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 67 */     Reflector.setFieldValue(tileEntitySkullRenderer, Reflector.TileEntitySkullRenderer_humanoidHead, modelBase);
/* 68 */     return (IEntityRenderer)tileEntitySkullRenderer;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterHeadSkeleton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */