/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBook;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityEnchantmentTableRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.tileentity.TileEntityEnchantmentTable;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterBook
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterBook() {
/* 17 */     super(TileEntityEnchantmentTable.class, "book", 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelBook();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelBook))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelBook modelbook = (ModelBook)model;
/* 34 */     return modelPart.equals("cover_right") ? modelbook.coverRight : (modelPart.equals("cover_left") ? modelbook.coverLeft : (modelPart.equals("pages_right") ? modelbook.pagesRight : (modelPart.equals("pages_left") ? modelbook.pagesLeft : (modelPart.equals("flipping_page_right") ? modelbook.flippingPageRight : (modelPart.equals("flipping_page_left") ? modelbook.flippingPageLeft : (modelPart.equals("book_spine") ? modelbook.bookSpine : null))))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 40 */     return new String[] { "cover_right", "cover_left", "pages_right", "pages_left", "flipping_page_right", "flipping_page_left", "book_spine" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     TileEntityEnchantmentTableRenderer tileEntityEnchantmentTableRenderer;
/* 45 */     TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
/* 46 */     TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getSpecialRendererByClass(TileEntityEnchantmentTable.class);
/*    */     
/* 48 */     if (!(tileentityspecialrenderer instanceof TileEntityEnchantmentTableRenderer))
/*    */     {
/* 50 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 54 */     if (tileentityspecialrenderer.getEntityClass() == null) {
/*    */       
/* 56 */       tileEntityEnchantmentTableRenderer = new TileEntityEnchantmentTableRenderer();
/* 57 */       tileEntityEnchantmentTableRenderer.setRendererDispatcher(tileentityrendererdispatcher);
/*    */     } 
/*    */     
/* 60 */     if (!Reflector.TileEntityEnchantmentTableRenderer_modelBook.exists()) {
/*    */       
/* 62 */       Config.warn("Field not found: TileEntityEnchantmentTableRenderer.modelBook");
/* 63 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 67 */     Reflector.setFieldValue(tileEntityEnchantmentTableRenderer, Reflector.TileEntityEnchantmentTableRenderer_modelBook, modelBase);
/* 68 */     return (IEntityRenderer)tileEntityEnchantmentTableRenderer;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */