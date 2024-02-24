/*     */ package net.minecraft.block;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.feature.WorldGenCanopyTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenForest;
/*     */ import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenSavannaTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenTrees;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ 
/*     */ public class BlockSapling extends BlockBush implements IGrowable {
/*  22 */   public static final PropertyEnum<BlockPlanks.EnumType> TYPE = PropertyEnum.create("type", BlockPlanks.EnumType.class);
/*  23 */   public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
/*     */ 
/*     */   
/*     */   protected BlockSapling() {
/*  27 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)TYPE, BlockPlanks.EnumType.OAK).withProperty((IProperty)STAGE, Integer.valueOf(0)));
/*  28 */     float f = 0.4F;
/*  29 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
/*  30 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  38 */     return StatCollector.translateToLocal(getUnlocalizedName() + "." + BlockPlanks.EnumType.OAK.getUnlocalizedName() + ".name");
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  43 */     if (!worldIn.isRemote) {
/*     */       
/*  45 */       super.updateTick(worldIn, pos, state, rand);
/*     */       
/*  47 */       if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0)
/*     */       {
/*  49 */         grow(worldIn, pos, state, rand);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  56 */     if (((Integer)state.getValue((IProperty)STAGE)).intValue() == 0) {
/*     */       
/*  58 */       worldIn.setBlockState(pos, state.cycleProperty((IProperty)STAGE), 4);
/*     */     }
/*     */     else {
/*     */       
/*  62 */       generateTree(worldIn, pos, state, rand);
/*     */     }  } public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*     */     WorldGenForest worldGenForest;
/*     */     WorldGenSavannaTree worldGenSavannaTree;
/*     */     WorldGenCanopyTree worldGenCanopyTree;
/*     */     IBlockState iblockstate, iblockstate1;
/*  68 */     WorldGenerator worldgenerator = (rand.nextInt(10) == 0) ? (WorldGenerator)new WorldGenBigTree(true) : (WorldGenerator)new WorldGenTrees(true);
/*  69 */     int i = 0;
/*  70 */     int j = 0;
/*  71 */     boolean flag = false;
/*     */     
/*  73 */     switch ((BlockPlanks.EnumType)state.getValue((IProperty)TYPE)) {
/*     */ 
/*     */       
/*     */       case SPRUCE:
/*  77 */         label68: for (i = 0; i >= -1; i--) {
/*     */           
/*  79 */           for (j = 0; j >= -1; j--) {
/*     */             
/*  81 */             if (func_181624_a(worldIn, pos, i, j, BlockPlanks.EnumType.SPRUCE)) {
/*     */               
/*  83 */               WorldGenMegaPineTree worldGenMegaPineTree = new WorldGenMegaPineTree(false, rand.nextBoolean());
/*  84 */               flag = true;
/*     */               
/*     */               break label68;
/*     */             } 
/*     */           } 
/*     */         } 
/*  90 */         if (!flag) {
/*     */           
/*  92 */           j = 0;
/*  93 */           i = 0;
/*  94 */           WorldGenTaiga2 worldGenTaiga2 = new WorldGenTaiga2(true);
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case BIRCH:
/* 100 */         worldGenForest = new WorldGenForest(true, false);
/*     */         break;
/*     */       
/*     */       case JUNGLE:
/* 104 */         iblockstate = Blocks.log.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE);
/* 105 */         iblockstate1 = Blocks.leaves.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE).withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */ 
/*     */         
/* 108 */         label69: for (i = 0; i >= -1; i--) {
/*     */           
/* 110 */           for (j = 0; j >= -1; j--) {
/*     */             
/* 112 */             if (func_181624_a(worldIn, pos, i, j, BlockPlanks.EnumType.JUNGLE)) {
/*     */               
/* 114 */               WorldGenMegaJungle worldGenMegaJungle = new WorldGenMegaJungle(true, 10, 20, iblockstate, iblockstate1);
/* 115 */               flag = true;
/*     */               
/*     */               break label69;
/*     */             } 
/*     */           } 
/*     */         } 
/* 121 */         if (!flag) {
/*     */           
/* 123 */           j = 0;
/* 124 */           i = 0;
/* 125 */           WorldGenTrees worldGenTrees = new WorldGenTrees(true, 4 + rand.nextInt(7), iblockstate, iblockstate1, false);
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case ACACIA:
/* 131 */         worldGenSavannaTree = new WorldGenSavannaTree(true);
/*     */         break;
/*     */ 
/*     */       
/*     */       case DARK_OAK:
/* 136 */         label70: for (i = 0; i >= -1; i--) {
/*     */           
/* 138 */           for (j = 0; j >= -1; j--) {
/*     */             
/* 140 */             if (func_181624_a(worldIn, pos, i, j, BlockPlanks.EnumType.DARK_OAK)) {
/*     */               
/* 142 */               worldGenCanopyTree = new WorldGenCanopyTree(true);
/* 143 */               flag = true;
/*     */               
/*     */               break label70;
/*     */             } 
/*     */           } 
/*     */         } 
/* 149 */         if (!flag) {
/*     */           return;
/*     */         }
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 157 */     IBlockState iblockstate2 = Blocks.air.getDefaultState();
/*     */     
/* 159 */     if (flag) {
/*     */       
/* 161 */       worldIn.setBlockState(pos.add(i, 0, j), iblockstate2, 4);
/* 162 */       worldIn.setBlockState(pos.add(i + 1, 0, j), iblockstate2, 4);
/* 163 */       worldIn.setBlockState(pos.add(i, 0, j + 1), iblockstate2, 4);
/* 164 */       worldIn.setBlockState(pos.add(i + 1, 0, j + 1), iblockstate2, 4);
/*     */     }
/*     */     else {
/*     */       
/* 168 */       worldIn.setBlockState(pos, iblockstate2, 4);
/*     */     } 
/*     */     
/* 171 */     if (!worldGenCanopyTree.generate(worldIn, rand, pos.add(i, 0, j)))
/*     */     {
/* 173 */       if (flag) {
/*     */         
/* 175 */         worldIn.setBlockState(pos.add(i, 0, j), state, 4);
/* 176 */         worldIn.setBlockState(pos.add(i + 1, 0, j), state, 4);
/* 177 */         worldIn.setBlockState(pos.add(i, 0, j + 1), state, 4);
/* 178 */         worldIn.setBlockState(pos.add(i + 1, 0, j + 1), state, 4);
/*     */       }
/*     */       else {
/*     */         
/* 182 */         worldIn.setBlockState(pos, state, 4);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_181624_a(World p_181624_1_, BlockPos p_181624_2_, int p_181624_3_, int p_181624_4_, BlockPlanks.EnumType p_181624_5_) {
/* 189 */     return (isTypeAt(p_181624_1_, p_181624_2_.add(p_181624_3_, 0, p_181624_4_), p_181624_5_) && isTypeAt(p_181624_1_, p_181624_2_.add(p_181624_3_ + 1, 0, p_181624_4_), p_181624_5_) && isTypeAt(p_181624_1_, p_181624_2_.add(p_181624_3_, 0, p_181624_4_ + 1), p_181624_5_) && isTypeAt(p_181624_1_, p_181624_2_.add(p_181624_3_ + 1, 0, p_181624_4_ + 1), p_181624_5_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTypeAt(World worldIn, BlockPos pos, BlockPlanks.EnumType type) {
/* 197 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 198 */     return (iblockstate.getBlock() == this && iblockstate.getValue((IProperty)TYPE) == type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/* 207 */     return ((BlockPlanks.EnumType)state.getValue((IProperty)TYPE)).getMetadata();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/* 215 */     for (BlockPlanks.EnumType blockplanks$enumtype : BlockPlanks.EnumType.values())
/*     */     {
/* 217 */       list.add(new ItemStack(itemIn, 1, blockplanks$enumtype.getMetadata()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 226 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 231 */     return (worldIn.rand.nextFloat() < 0.45D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 236 */     grow(worldIn, pos, state, rand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 244 */     return getDefaultState().withProperty((IProperty)TYPE, BlockPlanks.EnumType.byMetadata(meta & 0x7)).withProperty((IProperty)STAGE, Integer.valueOf((meta & 0x8) >> 3));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 252 */     int i = 0;
/* 253 */     i |= ((BlockPlanks.EnumType)state.getValue((IProperty)TYPE)).getMetadata();
/* 254 */     i |= ((Integer)state.getValue((IProperty)STAGE)).intValue() << 3;
/* 255 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 260 */     return new BlockState(this, new IProperty[] { (IProperty)TYPE, (IProperty)STAGE });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockSapling.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */