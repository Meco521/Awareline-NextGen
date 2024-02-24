/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeColorHelper;
/*     */ 
/*     */ public class BlockGrass
/*     */   extends Block
/*     */   implements IGrowable {
/*  22 */   public static final PropertyBool SNOWY = PropertyBool.create("snowy");
/*     */ 
/*     */   
/*     */   protected BlockGrass() {
/*  26 */     super(Material.grass);
/*  27 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)SNOWY, Boolean.valueOf(false)));
/*  28 */     setTickRandomly(true);
/*  29 */     setCreativeTab(CreativeTabs.tabBlock);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  38 */     Block block = worldIn.getBlockState(pos.up()).getBlock();
/*  39 */     return state.withProperty((IProperty)SNOWY, Boolean.valueOf((block == Blocks.snow || block == Blocks.snow_layer)));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlockColor() {
/*  44 */     return ColorizerGrass.getGrassColor(0.5D, 1.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRenderColor(IBlockState state) {
/*  49 */     return getBlockColor();
/*     */   }
/*     */ 
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/*  54 */     return BiomeColorHelper.getGrassColorAtPos(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  59 */     if (!worldIn.isRemote)
/*     */     {
/*  61 */       if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getBlock().getLightOpacity() > 2) {
/*     */         
/*  63 */         worldIn.setBlockState(pos, Blocks.dirt.getDefaultState());
/*     */ 
/*     */       
/*     */       }
/*  67 */       else if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
/*     */         
/*  69 */         for (int i = 0; i < 4; i++) {
/*     */           
/*  71 */           BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
/*  72 */           Block block = worldIn.getBlockState(blockpos.up()).getBlock();
/*  73 */           IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */           
/*  75 */           if (iblockstate.getBlock() == Blocks.dirt && iblockstate.getValue((IProperty)BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && block.getLightOpacity() <= 2)
/*     */           {
/*  77 */             worldIn.setBlockState(blockpos, Blocks.grass.getDefaultState());
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
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  90 */     return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), rand, fortune);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/*  98 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 103 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 108 */     BlockPos blockpos = pos.up();
/*     */     
/* 110 */     for (int i = 0; i < 128; i++) {
/*     */       
/* 112 */       BlockPos blockpos1 = blockpos;
/* 113 */       int j = 0;
/*     */ 
/*     */       
/*     */       while (true) {
/* 117 */         if (j >= i / 16) {
/*     */           
/* 119 */           if ((worldIn.getBlockState(blockpos1).getBlock()).blockMaterial == Material.air) {
/*     */             
/* 121 */             if (rand.nextInt(8) == 0) {
/*     */               
/* 123 */               BlockFlower.EnumFlowerType blockflower$enumflowertype = worldIn.getBiomeGenForCoords(blockpos1).pickRandomFlower(rand, blockpos1);
/* 124 */               BlockFlower blockflower = blockflower$enumflowertype.getBlockType().getBlock();
/* 125 */               IBlockState iblockstate = blockflower.getDefaultState().withProperty(blockflower.getTypeProperty(), blockflower$enumflowertype);
/*     */               
/* 127 */               if (blockflower.canBlockStay(worldIn, blockpos1, iblockstate))
/*     */               {
/* 129 */                 worldIn.setBlockState(blockpos1, iblockstate, 3);
/*     */               }
/*     */               
/*     */               break;
/*     */             } 
/* 134 */             IBlockState iblockstate1 = Blocks.tallgrass.getDefaultState().withProperty((IProperty)BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS);
/*     */             
/* 136 */             if (Blocks.tallgrass.canBlockStay(worldIn, blockpos1, iblockstate1))
/*     */             {
/* 138 */               worldIn.setBlockState(blockpos1, iblockstate1, 3);
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 146 */         blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
/*     */         
/* 148 */         if (worldIn.getBlockState(blockpos1.down()).getBlock() != Blocks.grass || worldIn.getBlockState(blockpos1).getBlock().isNormalCube()) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 153 */         j++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 160 */     return EnumWorldBlockLayer.CUTOUT_MIPPED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 168 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 173 */     return new BlockState(this, new IProperty[] { (IProperty)SNOWY });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockGrass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */