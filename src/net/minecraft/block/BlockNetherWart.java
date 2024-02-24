/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
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
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockNetherWart
/*     */   extends BlockBush
/*     */ {
/*  21 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);
/*     */ 
/*     */   
/*     */   protected BlockNetherWart() {
/*  25 */     super(Material.plants, MapColor.redColor);
/*  26 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AGE, Integer.valueOf(0)));
/*  27 */     setTickRandomly(true);
/*  28 */     float f = 0.5F;
/*  29 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
/*  30 */     setCreativeTab((CreativeTabs)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canPlaceBlockOn(Block ground) {
/*  38 */     return (ground == Blocks.soul_sand);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/*  43 */     return canPlaceBlockOn(worldIn.getBlockState(pos.down()).getBlock());
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  48 */     int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */     
/*  50 */     if (i < 3 && rand.nextInt(10) == 0) {
/*     */       
/*  52 */       state = state.withProperty((IProperty)AGE, Integer.valueOf(i + 1));
/*  53 */       worldIn.setBlockState(pos, state, 2);
/*     */     } 
/*     */     
/*  56 */     super.updateTick(worldIn, pos, state, rand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/*  64 */     if (!worldIn.isRemote) {
/*     */       
/*  66 */       int i = 1;
/*     */       
/*  68 */       if (((Integer)state.getValue((IProperty)AGE)).intValue() >= 3) {
/*     */         
/*  70 */         i = 2 + worldIn.rand.nextInt(3);
/*     */         
/*  72 */         if (fortune > 0)
/*     */         {
/*  74 */           i += worldIn.rand.nextInt(fortune + 1);
/*     */         }
/*     */       } 
/*     */       
/*  78 */       for (int j = 0; j < i; j++)
/*     */       {
/*  80 */         spawnAsEntity(worldIn, pos, new ItemStack(Items.nether_wart));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  90 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/*  98 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 103 */     return Items.nether_wart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 111 */     return getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 119 */     return ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 124 */     return new BlockState(this, new IProperty[] { (IProperty)AGE });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockNetherWart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */