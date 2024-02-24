/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderSnowball<T extends Entity>
/*    */   extends Render<T>
/*    */ {
/*    */   protected final Item field_177084_a;
/*    */   private final RenderItem field_177083_e;
/*    */   
/*    */   public RenderSnowball(RenderManager renderManagerIn, Item p_i46137_2_, RenderItem p_i46137_3_) {
/* 18 */     super(renderManagerIn);
/* 19 */     this.field_177084_a = p_i46137_2_;
/* 20 */     this.field_177083_e = p_i46137_3_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 28 */     GlStateManager.pushMatrix();
/* 29 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 30 */     GlStateManager.enableRescaleNormal();
/* 31 */     GlStateManager.scale(0.5F, 0.5F, 0.5F);
/* 32 */     GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 33 */     GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 34 */     bindTexture(TextureMap.locationBlocksTexture);
/* 35 */     this.field_177083_e.renderItem(func_177082_d(entity), ItemCameraTransforms.TransformType.GROUND);
/* 36 */     GlStateManager.disableRescaleNormal();
/* 37 */     GlStateManager.popMatrix();
/* 38 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_177082_d(T entityIn) {
/* 43 */     return new ItemStack(this.field_177084_a, 1, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(Entity entity) {
/* 51 */     return TextureMap.locationBlocksTexture;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderSnowball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */