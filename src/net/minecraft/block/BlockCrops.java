/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockCrops
/*     */   extends BlockBush
/*     */   implements IGrowable {
/*  20 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
/*     */ 
/*     */   
/*     */   protected BlockCrops() {
/*  24 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AGE, Integer.valueOf(0)));
/*  25 */     setTickRandomly(true);
/*  26 */     float f = 0.5F;
/*  27 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
/*  28 */     setCreativeTab((CreativeTabs)null);
/*  29 */     setHardness(0.0F);
/*  30 */     setStepSound(soundTypeGrass);
/*  31 */     disableStats();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canPlaceBlockOn(Block ground) {
/*  39 */     return (ground == Blocks.farmland);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  44 */     super.updateTick(worldIn, pos, state, rand);
/*     */     
/*  46 */     if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
/*     */       
/*  48 */       int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */       
/*  50 */       if (i < 7) {
/*     */         
/*  52 */         float f = getGrowthChance(this, worldIn, pos);
/*     */         
/*  54 */         if (rand.nextInt((int)(25.0F / f) + 1) == 0)
/*     */         {
/*  56 */           worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(i + 1)), 2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, BlockPos pos, IBlockState state) {
/*  64 */     int i = ((Integer)state.getValue((IProperty)AGE)).intValue() + MathHelper.getRandomIntegerInRange(worldIn.rand, 2, 5);
/*     */     
/*  66 */     if (i > 7)
/*     */     {
/*  68 */       i = 7;
/*     */     }
/*     */     
/*  71 */     worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(i)), 2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static float getGrowthChance(Block blockIn, World worldIn, BlockPos pos) {
/*  76 */     float f = 1.0F;
/*  77 */     BlockPos blockpos = pos.down();
/*     */     
/*  79 */     for (int i = -1; i <= 1; i++) {
/*     */       
/*  81 */       for (int j = -1; j <= 1; j++) {
/*     */         
/*  83 */         float f1 = 0.0F;
/*  84 */         IBlockState iblockstate = worldIn.getBlockState(blockpos.add(i, 0, j));
/*     */         
/*  86 */         if (iblockstate.getBlock() == Blocks.farmland) {
/*     */           
/*  88 */           f1 = 1.0F;
/*     */           
/*  90 */           if (((Integer)iblockstate.getValue((IProperty)BlockFarmland.MOISTURE)).intValue() > 0)
/*     */           {
/*  92 */             f1 = 3.0F;
/*     */           }
/*     */         } 
/*     */         
/*  96 */         if (i != 0 || j != 0)
/*     */         {
/*  98 */           f1 /= 4.0F;
/*     */         }
/*     */         
/* 101 */         f += f1;
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     BlockPos blockpos1 = pos.north();
/* 106 */     BlockPos blockpos2 = pos.south();
/* 107 */     BlockPos blockpos3 = pos.west();
/* 108 */     BlockPos blockpos4 = pos.east();
/* 109 */     boolean flag = (blockIn == worldIn.getBlockState(blockpos3).getBlock() || blockIn == worldIn.getBlockState(blockpos4).getBlock());
/* 110 */     boolean flag1 = (blockIn == worldIn.getBlockState(blockpos1).getBlock() || blockIn == worldIn.getBlockState(blockpos2).getBlock());
/*     */     
/* 112 */     if (flag && flag1) {
/*     */       
/* 114 */       f /= 2.0F;
/*     */     }
/*     */     else {
/*     */       
/* 118 */       boolean flag2 = (blockIn == worldIn.getBlockState(blockpos3.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.south()).getBlock() || blockIn == worldIn.getBlockState(blockpos3.south()).getBlock());
/*     */       
/* 120 */       if (flag2)
/*     */       {
/* 122 */         f /= 2.0F;
/*     */       }
/*     */     } 
/*     */     
/* 126 */     return f;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/* 131 */     return ((worldIn.getLight(pos) >= 8 || worldIn.canSeeSky(pos)) && canPlaceBlockOn(worldIn.getBlockState(pos.down()).getBlock()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getSeed() {
/* 136 */     return Items.wheat_seeds;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getCrop() {
/* 141 */     return Items.wheat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 149 */     super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
/*     */     
/* 151 */     if (!worldIn.isRemote) {
/*     */       
/* 153 */       int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */       
/* 155 */       if (i >= 7) {
/*     */         
/* 157 */         int j = 3 + fortune;
/*     */         
/* 159 */         for (int k = 0; k < j; k++) {
/*     */           
/* 161 */           if (worldIn.rand.nextInt(15) <= i)
/*     */           {
/* 163 */             spawnAsEntity(worldIn, pos, new ItemStack(getSeed(), 1, 0));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 175 */     return (((Integer)state.getValue((IProperty)AGE)).intValue() == 7) ? getCrop() : getSeed();
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 180 */     return getSeed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 188 */     return (((Integer)state.getValue((IProperty)AGE)).intValue() < 7);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 193 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 198 */     grow(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 206 */     return getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 214 */     return ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 219 */     return new BlockState(this, new IProperty[] { (IProperty)AGE });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockCrops.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */