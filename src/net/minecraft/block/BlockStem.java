/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockStem
/*     */   extends BlockBush
/*     */   implements IGrowable {
/*  25 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
/*  26 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>()
/*     */       {
/*     */         public boolean apply(EnumFacing p_apply_1_)
/*     */         {
/*  30 */           return (p_apply_1_ != EnumFacing.DOWN);
/*     */         }
/*     */       });
/*     */   
/*     */   private final Block crop;
/*     */   
/*     */   protected BlockStem(Block crop) {
/*  37 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AGE, Integer.valueOf(0)).withProperty((IProperty)FACING, (Comparable)EnumFacing.UP));
/*  38 */     this.crop = crop;
/*  39 */     setTickRandomly(true);
/*  40 */     float f = 0.125F;
/*  41 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
/*  42 */     setCreativeTab((CreativeTabs)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  51 */     state = state.withProperty((IProperty)FACING, (Comparable)EnumFacing.UP);
/*     */     
/*  53 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/*  55 */       if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == this.crop) {
/*     */         
/*  57 */         state = state.withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  62 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canPlaceBlockOn(Block ground) {
/*  70 */     return (ground == Blocks.farmland);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  75 */     super.updateTick(worldIn, pos, state, rand);
/*     */     
/*  77 */     if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
/*     */       
/*  79 */       float f = BlockCrops.getGrowthChance(this, worldIn, pos);
/*     */       
/*  81 */       if (rand.nextInt((int)(25.0F / f) + 1) == 0) {
/*     */         
/*  83 */         int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */         
/*  85 */         if (i < 7) {
/*     */           
/*  87 */           state = state.withProperty((IProperty)AGE, Integer.valueOf(i + 1));
/*  88 */           worldIn.setBlockState(pos, state, 2);
/*     */         }
/*     */         else {
/*     */           
/*  92 */           for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */             
/*  94 */             if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == this.crop) {
/*     */               return;
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 100 */           pos = pos.offset(EnumFacing.Plane.HORIZONTAL.random(rand));
/* 101 */           Block block = worldIn.getBlockState(pos.down()).getBlock();
/*     */           
/* 103 */           if ((worldIn.getBlockState(pos).getBlock()).blockMaterial == Material.air && (block == Blocks.farmland || block == Blocks.dirt || block == Blocks.grass))
/*     */           {
/* 105 */             worldIn.setBlockState(pos, this.crop.getDefaultState());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void growStem(World worldIn, BlockPos pos, IBlockState state) {
/* 114 */     int i = ((Integer)state.getValue((IProperty)AGE)).intValue() + MathHelper.getRandomIntegerInRange(worldIn.rand, 2, 5);
/* 115 */     worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(Math.min(7, i))), 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRenderColor(IBlockState state) {
/* 120 */     if (state.getBlock() != this)
/*     */     {
/* 122 */       return super.getRenderColor(state);
/*     */     }
/*     */ 
/*     */     
/* 126 */     int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/* 127 */     int j = i << 5;
/* 128 */     int k = 255 - (i << 3);
/* 129 */     int l = i << 2;
/* 130 */     return j << 16 | k << 8 | l;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/* 136 */     return getRenderColor(worldIn.getBlockState(pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/* 144 */     float f = 0.125F;
/* 145 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 150 */     this.maxY = (((((Integer)worldIn.getBlockState(pos).getValue((IProperty)AGE)).intValue() << 1) + 2) / 16.0F);
/* 151 */     float f = 0.125F;
/* 152 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, (float)this.maxY, 0.5F + f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 160 */     super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
/*     */     
/* 162 */     if (!worldIn.isRemote) {
/*     */       
/* 164 */       Item item = getSeedItem();
/*     */       
/* 166 */       if (item != null) {
/*     */         
/* 168 */         int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */         
/* 170 */         for (int j = 0; j < 3; j++) {
/*     */           
/* 172 */           if (worldIn.rand.nextInt(15) <= i)
/*     */           {
/* 174 */             spawnAsEntity(worldIn, pos, new ItemStack(item));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getSeedItem() {
/* 183 */     return (this.crop == Blocks.pumpkin) ? Items.pumpkin_seeds : ((this.crop == Blocks.melon_block) ? Items.melon_seeds : null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 191 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 196 */     Item item = getSeedItem();
/* 197 */     return (item != null) ? item : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 205 */     return (((Integer)state.getValue((IProperty)AGE)).intValue() != 7);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 210 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 215 */     growStem(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 223 */     return getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 231 */     return ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 236 */     return new BlockState(this, new IProperty[] { (IProperty)AGE, (IProperty)FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockStem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */