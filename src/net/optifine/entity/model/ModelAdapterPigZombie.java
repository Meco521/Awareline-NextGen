/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelZombie;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderBiped;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderPigZombie;
/*    */ import net.minecraft.entity.monster.EntityPigZombie;
/*    */ 
/*    */ public class ModelAdapterPigZombie
/*    */   extends ModelAdapterBiped {
/*    */   public ModelAdapterPigZombie() {
/* 16 */     super(EntityPigZombie.class, "zombie_pigman", 0.5F);
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
/* 27 */     RenderPigZombie renderpigzombie = new RenderPigZombie(rendermanager);
/* 28 */     Render.setModelBipedMain((RenderBiped)renderpigzombie, (ModelBiped)modelBase);
/* 29 */     renderpigzombie.mainModel = modelBase;
/* 30 */     renderpigzombie.shadowSize = shadowSize;
/* 31 */     return (IEntityRenderer)renderpigzombie;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterPigZombie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */