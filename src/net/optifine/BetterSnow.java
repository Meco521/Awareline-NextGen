/*    */ package net.optifine;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockLever;
/*    */ import net.minecraft.block.BlockTorch;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.resources.model.IBakedModel;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ public class BetterSnow {
/* 14 */   private static IBakedModel modelSnowLayer = null;
/*    */ 
/*    */   
/*    */   public static void update() {
/* 18 */     modelSnowLayer = Config.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(Blocks.snow_layer.getDefaultState());
/*    */   }
/*    */ 
/*    */   
/*    */   public static IBakedModel getModelSnowLayer() {
/* 23 */     return modelSnowLayer;
/*    */   }
/*    */ 
/*    */   
/*    */   public static IBlockState getStateSnowLayer() {
/* 28 */     return Blocks.snow_layer.getDefaultState();
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean shouldRender(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos) {
/* 33 */     Block block = blockState.getBlock();
/* 34 */     return !checkBlock(block, blockState) ? false : hasSnowNeighbours(blockAccess, blockPos);
/*    */   }
/*    */ 
/*    */   
/*    */   private static boolean hasSnowNeighbours(IBlockAccess blockAccess, BlockPos pos) {
/* 39 */     Block block = Blocks.snow_layer;
/* 40 */     return (blockAccess.getBlockState(pos.north()).getBlock() != block && blockAccess.getBlockState(pos.south()).getBlock() != block && blockAccess.getBlockState(pos.west()).getBlock() != block && blockAccess.getBlockState(pos.east()).getBlock() != block) ? false : blockAccess.getBlockState(pos.down()).getBlock().isOpaqueCube();
/*    */   }
/*    */ 
/*    */   
/*    */   private static boolean checkBlock(Block block, IBlockState blockState) {
/* 45 */     if (block.isFullCube())
/*    */     {
/* 47 */       return false;
/*    */     }
/* 49 */     if (block.isOpaqueCube())
/*    */     {
/* 51 */       return false;
/*    */     }
/* 53 */     if (block instanceof net.minecraft.block.BlockSnow)
/*    */     {
/* 55 */       return false;
/*    */     }
/* 57 */     if (!(block instanceof net.minecraft.block.BlockBush) || (!(block instanceof net.minecraft.block.BlockDoublePlant) && !(block instanceof net.minecraft.block.BlockFlower) && !(block instanceof net.minecraft.block.BlockMushroom) && !(block instanceof net.minecraft.block.BlockSapling) && !(block instanceof net.minecraft.block.BlockTallGrass))) {
/*    */       
/* 59 */       if (!(block instanceof net.minecraft.block.BlockFence) && !(block instanceof net.minecraft.block.BlockFenceGate) && !(block instanceof net.minecraft.block.BlockFlowerPot) && !(block instanceof net.minecraft.block.BlockPane) && !(block instanceof net.minecraft.block.BlockReed) && !(block instanceof net.minecraft.block.BlockWall)) {
/*    */         
/* 61 */         if (block instanceof net.minecraft.block.BlockRedstoneTorch && blockState.getValue((IProperty)BlockTorch.FACING) == EnumFacing.UP)
/*    */         {
/* 63 */           return true;
/*    */         }
/*    */ 
/*    */         
/* 67 */         if (block instanceof BlockLever) {
/*    */           
/* 69 */           Object object = blockState.getValue((IProperty)BlockLever.FACING);
/*    */           
/* 71 */           if (object == BlockLever.EnumOrientation.UP_X || object == BlockLever.EnumOrientation.UP_Z)
/*    */           {
/* 73 */             return true;
/*    */           }
/*    */         } 
/*    */         
/* 77 */         return false;
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 82 */       return true;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 87 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\BetterSnow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */