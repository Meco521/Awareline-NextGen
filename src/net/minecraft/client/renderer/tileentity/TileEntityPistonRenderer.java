/*    */ package net.minecraft.client.renderer.tileentity;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockPistonBase;
/*    */ import net.minecraft.block.BlockPistonExtension;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.tileentity.TileEntityPiston;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class TileEntityPistonRenderer extends TileEntitySpecialRenderer<TileEntityPiston> {
/* 19 */   private final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
/*    */ 
/*    */   
/*    */   public void renderTileEntityAt(TileEntityPiston te, double x, double y, double z, float partialTicks, int destroyStage) {
/* 23 */     BlockPos blockpos = te.getPos();
/* 24 */     IBlockState iblockstate = te.getPistonState();
/* 25 */     Block block = iblockstate.getBlock();
/*    */     
/* 27 */     if (block.getMaterial() != Material.air && te.getProgress(partialTicks) < 1.0F) {
/*    */       
/* 29 */       Tessellator tessellator = Tessellator.getInstance();
/* 30 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 31 */       bindTexture(TextureMap.locationBlocksTexture);
/* 32 */       RenderHelper.disableStandardItemLighting();
/* 33 */       GlStateManager.blendFunc(770, 771);
/* 34 */       GlStateManager.enableBlend();
/* 35 */       GlStateManager.disableCull();
/*    */       
/* 37 */       if (Minecraft.isAmbientOcclusionEnabled()) {
/*    */         
/* 39 */         GlStateManager.shadeModel(7425);
/*    */       }
/*    */       else {
/*    */         
/* 43 */         GlStateManager.shadeModel(7424);
/*    */       } 
/*    */       
/* 46 */       worldrenderer.begin(7, DefaultVertexFormats.BLOCK);
/* 47 */       worldrenderer.setTranslation(((float)x - blockpos.getX() + te.getOffsetX(partialTicks)), ((float)y - blockpos.getY() + te.getOffsetY(partialTicks)), ((float)z - blockpos.getZ() + te.getOffsetZ(partialTicks)));
/* 48 */       World world = getWorld();
/*    */       
/* 50 */       if (block == Blocks.piston_head && te.getProgress(partialTicks) < 0.5F) {
/*    */         
/* 52 */         iblockstate = iblockstate.withProperty((IProperty)BlockPistonExtension.SHORT, Boolean.valueOf(true));
/* 53 */         this.blockRenderer.getBlockModelRenderer().renderModel((IBlockAccess)world, this.blockRenderer.getModelFromBlockState(iblockstate, (IBlockAccess)world, blockpos), iblockstate, blockpos, worldrenderer, true);
/*    */       }
/* 55 */       else if (te.shouldPistonHeadBeRendered() && !te.isExtending()) {
/*    */         
/* 57 */         BlockPistonExtension.EnumPistonType blockpistonextension$enumpistontype = (block == Blocks.sticky_piston) ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT;
/* 58 */         IBlockState iblockstate1 = Blocks.piston_head.getDefaultState().withProperty((IProperty)BlockPistonExtension.TYPE, (Comparable)blockpistonextension$enumpistontype).withProperty((IProperty)BlockPistonExtension.FACING, iblockstate.getValue((IProperty)BlockPistonBase.FACING));
/* 59 */         iblockstate1 = iblockstate1.withProperty((IProperty)BlockPistonExtension.SHORT, Boolean.valueOf((te.getProgress(partialTicks) >= 0.5F)));
/* 60 */         this.blockRenderer.getBlockModelRenderer().renderModel((IBlockAccess)world, this.blockRenderer.getModelFromBlockState(iblockstate1, (IBlockAccess)world, blockpos), iblockstate1, blockpos, worldrenderer, true);
/* 61 */         worldrenderer.setTranslation(((float)x - blockpos.getX()), ((float)y - blockpos.getY()), ((float)z - blockpos.getZ()));
/* 62 */         iblockstate.withProperty((IProperty)BlockPistonBase.EXTENDED, Boolean.valueOf(true));
/* 63 */         this.blockRenderer.getBlockModelRenderer().renderModel((IBlockAccess)world, this.blockRenderer.getModelFromBlockState(iblockstate, (IBlockAccess)world, blockpos), iblockstate, blockpos, worldrenderer, true);
/*    */       }
/*    */       else {
/*    */         
/* 67 */         this.blockRenderer.getBlockModelRenderer().renderModel((IBlockAccess)world, this.blockRenderer.getModelFromBlockState(iblockstate, (IBlockAccess)world, blockpos), iblockstate, blockpos, worldrenderer, false);
/*    */       } 
/*    */       
/* 70 */       worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
/* 71 */       tessellator.draw();
/* 72 */       RenderHelper.enableStandardItemLighting();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\tileentity\TileEntityPistonRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */