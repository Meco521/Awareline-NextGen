/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelChest;
/*    */ import net.minecraft.client.model.ModelLargeChest;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.tileentity.TileEntityChest;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterChestLarge
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterChestLarge() {
/* 18 */     super(TileEntityChest.class, "chest_large", 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 23 */     return (ModelBase)new ModelLargeChest();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 28 */     if (!(model instanceof ModelChest))
/*    */     {
/* 30 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 34 */     ModelChest modelchest = (ModelChest)model;
/* 35 */     return modelPart.equals("lid") ? modelchest.chestLid : (modelPart.equals("base") ? modelchest.chestBelow : (modelPart.equals("knob") ? modelchest.chestKnob : null));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 41 */     return new String[] { "lid", "base", "knob" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     TileEntityChestRenderer tileEntityChestRenderer;
/* 46 */     TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
/* 47 */     TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getSpecialRendererByClass(TileEntityChest.class);
/*    */     
/* 49 */     if (!(tileentityspecialrenderer instanceof TileEntityChestRenderer))
/*    */     {
/* 51 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 55 */     if (tileentityspecialrenderer.getEntityClass() == null) {
/*    */       
/* 57 */       tileEntityChestRenderer = new TileEntityChestRenderer();
/* 58 */       tileEntityChestRenderer.setRendererDispatcher(tileentityrendererdispatcher);
/*    */     } 
/*    */     
/* 61 */     if (!Reflector.TileEntityChestRenderer_largeChest.exists()) {
/*    */       
/* 63 */       Config.warn("Field not found: TileEntityChestRenderer.largeChest");
/* 64 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 68 */     Reflector.setFieldValue(tileEntityChestRenderer, Reflector.TileEntityChestRenderer_largeChest, modelBase);
/* 69 */     return (IEntityRenderer)tileEntityChestRenderer;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterChestLarge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */