/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import awareline.main.mod.implement.visual.TNTTag;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityTNTPrimed;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderTNTPrimed
/*    */   extends Render<EntityTNTPrimed> {
/*    */   public RenderTNTPrimed(RenderManager renderManagerIn) {
/* 17 */     super(renderManagerIn);
/* 18 */     this.shadowSize = 0.5F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityTNTPrimed entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 26 */     BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 27 */     GlStateManager.pushMatrix();
/* 28 */     GlStateManager.translate((float)x, (float)y + 0.5F, (float)z);
/*    */     
/* 30 */     if (entity.fuse - partialTicks + 1.0F < 10.0F) {
/*    */       
/* 32 */       float f = 1.0F - (entity.fuse - partialTicks + 1.0F) / 10.0F;
/* 33 */       f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 34 */       f *= f;
/* 35 */       f *= f;
/* 36 */       float f1 = 1.0F + f * 0.3F;
/* 37 */       GlStateManager.scale(f1, f1, f1);
/*    */     } 
/*    */     
/* 40 */     float f2 = (1.0F - (entity.fuse - partialTicks + 1.0F) / 100.0F) * 0.8F;
/* 41 */     bindEntityTexture(entity);
/* 42 */     GlStateManager.translate(-0.5F, -0.5F, 0.5F);
/* 43 */     blockrendererdispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), entity.getBrightness(partialTicks));
/* 44 */     GlStateManager.translate(0.0F, 0.0F, 1.0F);
/*    */     
/* 46 */     if (entity.fuse / 5 % 2 == 0) {
/*    */       
/* 48 */       GlStateManager.disableTexture2D();
/* 49 */       GlStateManager.disableLighting();
/* 50 */       GlStateManager.enableBlend();
/* 51 */       GlStateManager.blendFunc(770, 772);
/* 52 */       GlStateManager.color(1.0F, 1.0F, 1.0F, f2);
/* 53 */       GlStateManager.doPolygonOffset(-3.0F, -3.0F);
/* 54 */       GlStateManager.enablePolygonOffset();
/* 55 */       blockrendererdispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), 1.0F);
/* 56 */       GlStateManager.doPolygonOffset(0.0F, 0.0F);
/* 57 */       GlStateManager.disablePolygonOffset();
/* 58 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 59 */       GlStateManager.disableBlend();
/* 60 */       GlStateManager.enableLighting();
/* 61 */       GlStateManager.enableTexture2D();
/*    */     } 
/*    */     
/* 64 */     GlStateManager.popMatrix();
/* 65 */     if (TNTTag.getInstance.isEnabled()) {
/* 66 */       TNTTag.getInstance.renderTag(this, entity, x, y, z, partialTicks);
/*    */     }
/* 68 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityTNTPrimed entity) {
/* 76 */     return TextureMap.locationBlocksTexture;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderTNTPrimed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */