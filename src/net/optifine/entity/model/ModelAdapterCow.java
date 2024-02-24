/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelCow;
/*    */ import net.minecraft.client.renderer.entity.RenderCow;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.passive.EntityCow;
/*    */ 
/*    */ public class ModelAdapterCow
/*    */   extends ModelAdapterQuadruped
/*    */ {
/*    */   public ModelAdapterCow() {
/* 14 */     super(EntityCow.class, "cow", 0.7F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelCow();
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 24 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 25 */     return (IEntityRenderer)new RenderCow(rendermanager, modelBase, shadowSize);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterCow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */