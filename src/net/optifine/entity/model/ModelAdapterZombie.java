/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelZombie;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderBiped;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderZombie;
/*    */ import net.minecraft.entity.monster.EntityZombie;
/*    */ 
/*    */ public class ModelAdapterZombie
/*    */   extends ModelAdapterBiped {
/*    */   public ModelAdapterZombie() {
/* 16 */     super(EntityZombie.class, "zombie", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 21 */     return (ModelBase)new ModelZombie();
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 26 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 27 */     RenderZombie renderzombie = new RenderZombie(rendermanager);
/* 28 */     Render.setModelBipedMain((RenderBiped)renderzombie, (ModelBiped)modelBase);
/* 29 */     renderzombie.mainModel = modelBase;
/* 30 */     renderzombie.shadowSize = shadowSize;
/* 31 */     return (IEntityRenderer)renderzombie;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterZombie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */