/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntityCow;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderCow extends RenderLiving<EntityCow> {
/*  9 */   private static final ResourceLocation cowTextures = new ResourceLocation("textures/entity/cow/cow.png");
/*    */ 
/*    */   
/*    */   public RenderCow(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
/* 13 */     super(renderManagerIn, modelBaseIn, shadowSizeIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityCow entity) {
/* 21 */     return cowTextures;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderCow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */