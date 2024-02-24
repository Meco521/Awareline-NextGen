/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelSkeletonHead;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.tileentity.RenderWitherSkull;
/*    */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterWitherSkull
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterWitherSkull() {
/* 17 */     super(EntityWitherSkull.class, "wither_skull", 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelSkeletonHead();
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
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 45 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 46 */     RenderWitherSkull renderwitherskull = new RenderWitherSkull(rendermanager);
/*    */     
/* 48 */     if (!Reflector.RenderWitherSkull_model.exists()) {
/*    */       
/* 50 */       Config.warn("Field not found: RenderWitherSkull_model");
/* 51 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 55 */     Reflector.setFieldValue(renderwitherskull, Reflector.RenderWitherSkull_model, modelBase);
/* 56 */     renderwitherskull.shadowSize = shadowSize;
/* 57 */     return (IEntityRenderer)renderwitherskull;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterWitherSkull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */