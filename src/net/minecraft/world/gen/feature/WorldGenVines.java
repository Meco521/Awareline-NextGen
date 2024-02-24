/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockVine;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenVines
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 16 */     for (; position.getY() < 128; position = position.up()) {
/*    */       
/* 18 */       if (worldIn.isAirBlock(position)) {
/*    */         
/* 20 */         for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL.facings()) {
/*    */           
/* 22 */           if (Blocks.vine.canPlaceBlockOnSide(worldIn, position, enumfacing)) {
/*    */             
/* 24 */             IBlockState iblockstate = Blocks.vine.getDefaultState().withProperty((IProperty)BlockVine.NORTH, Boolean.valueOf((enumfacing == EnumFacing.NORTH))).withProperty((IProperty)BlockVine.EAST, Boolean.valueOf((enumfacing == EnumFacing.EAST))).withProperty((IProperty)BlockVine.SOUTH, Boolean.valueOf((enumfacing == EnumFacing.SOUTH))).withProperty((IProperty)BlockVine.WEST, Boolean.valueOf((enumfacing == EnumFacing.WEST)));
/* 25 */             worldIn.setBlockState(position, iblockstate, 2);
/*    */ 
/*    */             
/*    */             break;
/*    */           } 
/*    */         } 
/*    */       } else {
/* 32 */         position = position.add(rand.nextInt(4) - rand.nextInt(4), 0, rand.nextInt(4) - rand.nextInt(4));
/*    */       } 
/*    */     } 
/*    */     
/* 36 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenVines.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */