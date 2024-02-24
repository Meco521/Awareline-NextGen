/*    */ package com.me.guichaguri.betterfps.clones.block;
/*    */ 
/*    */ import com.me.guichaguri.betterfps.clones.tileentity.HopperLogic;
/*    */ import com.me.guichaguri.betterfps.transformers.cloner.CopyMode;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockHopper;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntityHopper;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HopperBlock
/*    */   extends BlockHopper
/*    */ {
/*    */   @CopyMode(CopyMode.Mode.APPEND)
/*    */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 27 */     TileEntity te = worldIn.getTileEntity(pos);
/* 28 */     if (te != null) {
/* 29 */       TileEntityHopper hopper = (TileEntityHopper)te;
/* 30 */       ((HopperLogic)hopper).checkBlockOnTop();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\clones\block\HopperBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */