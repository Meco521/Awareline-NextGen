/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumWorldBlockLayer;
/*    */ import net.minecraft.world.EnumSkyBlock;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockIce
/*    */   extends BlockBreakable
/*    */ {
/*    */   public BlockIce() {
/* 23 */     super(Material.ice, false);
/* 24 */     this.slipperiness = 0.98F;
/* 25 */     setTickRandomly(true);
/* 26 */     setCreativeTab(CreativeTabs.tabBlock);
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumWorldBlockLayer getBlockLayer() {
/* 31 */     return EnumWorldBlockLayer.TRANSLUCENT;
/*    */   }
/*    */ 
/*    */   
/*    */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
/* 36 */     player.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
/* 37 */     player.addExhaustion(0.025F);
/*    */     
/* 39 */     if (canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier((EntityLivingBase)player)) {
/*    */       
/* 41 */       ItemStack itemstack = createStackedBlock(state);
/*    */       
/* 43 */       if (itemstack != null)
/*    */       {
/* 45 */         spawnAsEntity(worldIn, pos, itemstack);
/*    */       }
/*    */     }
/*    */     else {
/*    */       
/* 50 */       if (worldIn.provider.doesWaterVaporize()) {
/*    */         
/* 52 */         worldIn.setBlockToAir(pos);
/*    */         
/*    */         return;
/*    */       } 
/* 56 */       int i = EnchantmentHelper.getFortuneModifier((EntityLivingBase)player);
/* 57 */       dropBlockAsItem(worldIn, pos, state, i);
/* 58 */       Material material = worldIn.getBlockState(pos.down()).getBlock().getMaterial();
/*    */       
/* 60 */       if (material.blocksMovement() || material.isLiquid())
/*    */       {
/* 62 */         worldIn.setBlockState(pos, Blocks.flowing_water.getDefaultState());
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int quantityDropped(Random random) {
/* 72 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 77 */     if (worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > 11 - getLightOpacity())
/*    */     {
/* 79 */       if (worldIn.provider.doesWaterVaporize()) {
/*    */         
/* 81 */         worldIn.setBlockToAir(pos);
/*    */       }
/*    */       else {
/*    */         
/* 85 */         dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
/* 86 */         worldIn.setBlockState(pos, Blocks.water.getDefaultState());
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMobilityFlag() {
/* 93 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockIce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */