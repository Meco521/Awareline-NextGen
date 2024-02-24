/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class BlockRedstoneLight
/*    */   extends Block
/*    */ {
/*    */   private final boolean isOn;
/*    */   
/*    */   public BlockRedstoneLight(boolean isOn) {
/* 19 */     super(Material.redstoneLight);
/* 20 */     this.isOn = isOn;
/*    */     
/* 22 */     if (isOn)
/*    */     {
/* 24 */       setLightLevel(1.0F);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 30 */     if (!worldIn.isRemote)
/*    */     {
/* 32 */       if (this.isOn && !worldIn.isBlockPowered(pos)) {
/*    */         
/* 34 */         worldIn.setBlockState(pos, Blocks.redstone_lamp.getDefaultState(), 2);
/*    */       }
/* 36 */       else if (!this.isOn && worldIn.isBlockPowered(pos)) {
/*    */         
/* 38 */         worldIn.setBlockState(pos, Blocks.lit_redstone_lamp.getDefaultState(), 2);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 48 */     if (!worldIn.isRemote)
/*    */     {
/* 50 */       if (this.isOn && !worldIn.isBlockPowered(pos)) {
/*    */         
/* 52 */         worldIn.scheduleUpdate(pos, this, 4);
/*    */       }
/* 54 */       else if (!this.isOn && worldIn.isBlockPowered(pos)) {
/*    */         
/* 56 */         worldIn.setBlockState(pos, Blocks.lit_redstone_lamp.getDefaultState(), 2);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 63 */     if (!worldIn.isRemote)
/*    */     {
/* 65 */       if (this.isOn && !worldIn.isBlockPowered(pos))
/*    */       {
/* 67 */         worldIn.setBlockState(pos, Blocks.redstone_lamp.getDefaultState(), 2);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 77 */     return Item.getItemFromBlock(Blocks.redstone_lamp);
/*    */   }
/*    */ 
/*    */   
/*    */   public Item getItem(World worldIn, BlockPos pos) {
/* 82 */     return Item.getItemFromBlock(Blocks.redstone_lamp);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemStack createStackedBlock(IBlockState state) {
/* 87 */     return new ItemStack(Blocks.redstone_lamp);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockRedstoneLight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */