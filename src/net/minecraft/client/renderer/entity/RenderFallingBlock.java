/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.client.resources.model.IBakedModel;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityFallingBlock;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class RenderFallingBlock extends Render<EntityFallingBlock> {
/*    */   public RenderFallingBlock(RenderManager renderManagerIn) {
/* 22 */     super(renderManagerIn);
/* 23 */     this.shadowSize = 0.5F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityFallingBlock entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 31 */     if (entity.getBlock() != null) {
/*    */       
/* 33 */       bindTexture(TextureMap.locationBlocksTexture);
/* 34 */       IBlockState iblockstate = entity.getBlock();
/* 35 */       Block block = iblockstate.getBlock();
/* 36 */       BlockPos blockpos = new BlockPos((Entity)entity);
/* 37 */       World world = entity.getWorldObj();
/*    */       
/* 39 */       if (iblockstate != world.getBlockState(blockpos) && block.getRenderType() != -1)
/*    */       {
/* 41 */         if (block.getRenderType() == 3) {
/*    */           
/* 43 */           GlStateManager.pushMatrix();
/* 44 */           GlStateManager.translate((float)x, (float)y, (float)z);
/* 45 */           GlStateManager.disableLighting();
/* 46 */           Tessellator tessellator = Tessellator.getInstance();
/* 47 */           WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 48 */           worldrenderer.begin(7, DefaultVertexFormats.BLOCK);
/* 49 */           int i = blockpos.getX();
/* 50 */           int j = blockpos.getY();
/* 51 */           int k = blockpos.getZ();
/* 52 */           worldrenderer.setTranslation((-i - 0.5F), -j, (-k - 0.5F));
/* 53 */           BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 54 */           IBakedModel ibakedmodel = blockrendererdispatcher.getModelFromBlockState(iblockstate, (IBlockAccess)world, (BlockPos)null);
/* 55 */           blockrendererdispatcher.getBlockModelRenderer().renderModel((IBlockAccess)world, ibakedmodel, iblockstate, blockpos, worldrenderer, false);
/* 56 */           worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
/* 57 */           tessellator.draw();
/* 58 */           GlStateManager.enableLighting();
/* 59 */           GlStateManager.popMatrix();
/* 60 */           super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityFallingBlock entity) {
/* 71 */     return TextureMap.locationBlocksTexture;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderFallingBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */