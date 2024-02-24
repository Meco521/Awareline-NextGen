/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelPig;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderPig;
/*    */ import net.minecraft.entity.passive.EntityPig;
/*    */ 
/*    */ public class ModelAdapterPig
/*    */   extends ModelAdapterQuadruped
/*    */ {
/*    */   public ModelAdapterPig() {
/* 14 */     super(EntityPig.class, "pig", 0.7F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelPig();
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 24 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 25 */     return (IEntityRenderer)new RenderPig(rendermanager, modelBase, shadowSize);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterPig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */