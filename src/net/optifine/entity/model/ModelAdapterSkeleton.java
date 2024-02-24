/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelSkeleton;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderBiped;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSkeleton;
/*    */ import net.minecraft.entity.monster.EntitySkeleton;
/*    */ 
/*    */ public class ModelAdapterSkeleton
/*    */   extends ModelAdapterBiped {
/*    */   public ModelAdapterSkeleton() {
/* 16 */     super(EntitySkeleton.class, "skeleton", 0.7F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 21 */     return (ModelBase)new ModelSkeleton();
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 26 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 27 */     RenderSkeleton renderskeleton = new RenderSkeleton(rendermanager);
/* 28 */     Render.setModelBipedMain((RenderBiped)renderskeleton, (ModelBiped)modelBase);
/* 29 */     renderskeleton.mainModel = modelBase;
/* 30 */     renderskeleton.shadowSize = shadowSize;
/* 31 */     return (IEntityRenderer)renderskeleton;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterSkeleton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */