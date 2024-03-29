/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityFallingBlock;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockFalling
/*    */   extends Block
/*    */ {
/*    */   public static boolean fallInstantly;
/*    */   
/*    */   public BlockFalling() {
/* 19 */     super(Material.sand);
/* 20 */     setCreativeTab(CreativeTabs.tabBlock);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockFalling(Material materialIn) {
/* 25 */     super(materialIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 30 */     worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 38 */     worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 43 */     if (!worldIn.isRemote)
/*    */     {
/* 45 */       checkFallable(worldIn, pos);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   private void checkFallable(World worldIn, BlockPos pos) {
/* 51 */     if (canFallInto(worldIn, pos.down()) && pos.getY() >= 0) {
/*    */       
/* 53 */       int i = 32;
/*    */       
/* 55 */       if (!fallInstantly && worldIn.isAreaLoaded(pos.add(-i, -i, -i), pos.add(i, i, i))) {
/*    */         
/* 57 */         if (!worldIn.isRemote)
/*    */         {
/* 59 */           EntityFallingBlock entityfallingblock = new EntityFallingBlock(worldIn, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, worldIn.getBlockState(pos));
/* 60 */           onStartFalling(entityfallingblock);
/* 61 */           worldIn.spawnEntityInWorld((Entity)entityfallingblock);
/*    */         }
/*    */       
/*    */       } else {
/*    */         
/* 66 */         worldIn.setBlockToAir(pos);
/*    */         
/*    */         BlockPos blockpos;
/* 69 */         for (blockpos = pos.down(); canFallInto(worldIn, blockpos) && blockpos.getY() > 0; blockpos = blockpos.down());
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 74 */         if (blockpos.getY() > 0)
/*    */         {
/* 76 */           worldIn.setBlockState(blockpos.up(), getDefaultState());
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onStartFalling(EntityFallingBlock fallingEntity) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int tickRate(World worldIn) {
/* 91 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean canFallInto(World worldIn, BlockPos pos) {
/* 96 */     Block block = worldIn.getBlockState(pos).getBlock();
/* 97 */     Material material = block.blockMaterial;
/* 98 */     return (block == Blocks.fire || material == Material.air || material == Material.water || material == Material.lava);
/*    */   }
/*    */   
/*    */   public void onEndFalling(World worldIn, BlockPos pos) {}
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockFalling.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */