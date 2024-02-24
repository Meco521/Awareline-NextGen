/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelChest;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityEnderChestRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.tileentity.TileEntityEnderChest;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterEnderChest
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterEnderChest() {
/* 17 */     super(TileEntityEnderChest.class, "ender_chest", 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelChest();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelChest))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelChest modelchest = (ModelChest)model;
/* 34 */     return modelPart.equals("lid") ? modelchest.chestLid : (modelPart.equals("base") ? modelchest.chestBelow : (modelPart.equals("knob") ? modelchest.chestKnob : null));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 40 */     return new String[] { "lid", "base", "knob" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     TileEntityEnderChestRenderer tileEntityEnderChestRenderer;
/* 45 */     TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
/* 46 */     TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getSpecialRendererByClass(TileEntityEnderChest.class);
/*    */     
/* 48 */     if (!(tileentityspecialrenderer instanceof TileEntityEnderChestRenderer))
/*    */     {
/* 50 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 54 */     if (tileentityspecialrenderer.getEntityClass() == null) {
/*    */       
/* 56 */       tileEntityEnderChestRenderer = new TileEntityEnderChestRenderer();
/* 57 */       tileEntityEnderChestRenderer.setRendererDispatcher(tileentityrendererdispatcher);
/*    */     } 
/*    */     
/* 60 */     if (!Reflector.TileEntityEnderChestRenderer_modelChest.exists()) {
/*    */       
/* 62 */       Config.warn("Field not found: TileEntityEnderChestRenderer.modelChest");
/* 63 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 67 */     Reflector.setFieldValue(tileEntityEnderChestRenderer, Reflector.TileEntityEnderChestRenderer_modelChest, modelBase);
/* 68 */     return (IEntityRenderer)tileEntityEnderChestRenderer;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterEnderChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */