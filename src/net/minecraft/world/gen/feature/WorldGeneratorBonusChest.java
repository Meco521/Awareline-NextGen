/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.WeightedRandomChestContent;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGeneratorBonusChest
/*    */   extends WorldGenerator
/*    */ {
/*    */   private final List<WeightedRandomChestContent> chestItems;
/*    */   private final int itemsToGenerateInBonusChest;
/*    */   
/*    */   public WorldGeneratorBonusChest(List<WeightedRandomChestContent> p_i45634_1_, int p_i45634_2_) {
/* 26 */     this.chestItems = p_i45634_1_;
/* 27 */     this.itemsToGenerateInBonusChest = p_i45634_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*    */     Block block;
/* 34 */     while (((block = worldIn.getBlockState(position).getBlock()).getMaterial() == Material.air || block.getMaterial() == Material.leaves) && position.getY() > 1)
/*    */     {
/* 36 */       position = position.down();
/*    */     }
/*    */     
/* 39 */     if (position.getY() < 1)
/*    */     {
/* 41 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 45 */     position = position.up();
/*    */     
/* 47 */     for (int i = 0; i < 4; i++) {
/*    */       
/* 49 */       BlockPos blockpos = position.add(rand.nextInt(4) - rand.nextInt(4), rand.nextInt(3) - rand.nextInt(3), rand.nextInt(4) - rand.nextInt(4));
/*    */       
/* 51 */       if (worldIn.isAirBlock(blockpos) && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, blockpos.down())) {
/*    */         
/* 53 */         worldIn.setBlockState(blockpos, Blocks.chest.getDefaultState(), 2);
/* 54 */         TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*    */         
/* 56 */         if (tileentity instanceof net.minecraft.tileentity.TileEntityChest)
/*    */         {
/* 58 */           WeightedRandomChestContent.generateChestContents(rand, this.chestItems, (IInventory)tileentity, this.itemsToGenerateInBonusChest);
/*    */         }
/*    */         
/* 61 */         BlockPos blockpos1 = blockpos.east();
/* 62 */         BlockPos blockpos2 = blockpos.west();
/* 63 */         BlockPos blockpos3 = blockpos.north();
/* 64 */         BlockPos blockpos4 = blockpos.south();
/*    */         
/* 66 */         if (worldIn.isAirBlock(blockpos2) && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, blockpos2.down()))
/*    */         {
/* 68 */           worldIn.setBlockState(blockpos2, Blocks.torch.getDefaultState(), 2);
/*    */         }
/*    */         
/* 71 */         if (worldIn.isAirBlock(blockpos1) && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, blockpos1.down()))
/*    */         {
/* 73 */           worldIn.setBlockState(blockpos1, Blocks.torch.getDefaultState(), 2);
/*    */         }
/*    */         
/* 76 */         if (worldIn.isAirBlock(blockpos3) && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, blockpos3.down()))
/*    */         {
/* 78 */           worldIn.setBlockState(blockpos3, Blocks.torch.getDefaultState(), 2);
/*    */         }
/*    */         
/* 81 */         if (worldIn.isAirBlock(blockpos4) && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, blockpos4.down()))
/*    */         {
/* 83 */           worldIn.setBlockState(blockpos4, Blocks.torch.getDefaultState(), 2);
/*    */         }
/*    */         
/* 86 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 90 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGeneratorBonusChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */