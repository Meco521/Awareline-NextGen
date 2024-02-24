/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.properties.PropertyBool;
/*    */ import net.minecraft.block.state.BlockState;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockMycelium
/*    */   extends Block
/*    */ {
/* 21 */   public static final PropertyBool SNOWY = PropertyBool.create("snowy");
/*    */ 
/*    */   
/*    */   protected BlockMycelium() {
/* 25 */     super(Material.grass, MapColor.purpleColor);
/* 26 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)SNOWY, Boolean.valueOf(false)));
/* 27 */     setTickRandomly(true);
/* 28 */     setCreativeTab(CreativeTabs.tabBlock);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 37 */     Block block = worldIn.getBlockState(pos.up()).getBlock();
/* 38 */     return state.withProperty((IProperty)SNOWY, Boolean.valueOf((block == Blocks.snow || block == Blocks.snow_layer)));
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 43 */     if (!worldIn.isRemote)
/*    */     {
/* 45 */       if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getBlock().getLightOpacity() > 2) {
/*    */         
/* 47 */         worldIn.setBlockState(pos, Blocks.dirt.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
/*    */ 
/*    */       
/*    */       }
/* 51 */       else if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
/*    */         
/* 53 */         for (int i = 0; i < 4; i++) {
/*    */           
/* 55 */           BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
/* 56 */           IBlockState iblockstate = worldIn.getBlockState(blockpos);
/* 57 */           Block block = worldIn.getBlockState(blockpos.up()).getBlock();
/*    */           
/* 59 */           if (iblockstate.getBlock() == Blocks.dirt && iblockstate.getValue((IProperty)BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && block.getLightOpacity() <= 2)
/*    */           {
/* 61 */             worldIn.setBlockState(blockpos, getDefaultState());
/*    */           }
/*    */         } 
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 71 */     super.randomDisplayTick(worldIn, pos, state, rand);
/*    */     
/* 73 */     if (rand.nextInt(10) == 0)
/*    */     {
/* 75 */       worldIn.spawnParticle(EnumParticleTypes.TOWN_AURA, (pos.getX() + rand.nextFloat()), (pos.getY() + 1.1F), (pos.getZ() + rand.nextFloat()), 0.0D, 0.0D, 0.0D, new int[0]);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 84 */     return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), rand, fortune);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetaFromState(IBlockState state) {
/* 92 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockState createBlockState() {
/* 97 */     return new BlockState(this, new IProperty[] { (IProperty)SNOWY });
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockMycelium.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */