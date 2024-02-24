/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockCake
/*     */   extends Block
/*     */ {
/*  23 */   public static final PropertyInteger BITES = PropertyInteger.create("bites", 0, 6);
/*     */ 
/*     */   
/*     */   protected BlockCake() {
/*  27 */     super(Material.cake);
/*  28 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)BITES, Integer.valueOf(0)));
/*  29 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  34 */     float f = 0.0625F;
/*  35 */     float f1 = (1 + (((Integer)worldIn.getBlockState(pos).getValue((IProperty)BITES)).intValue() << 1)) / 16.0F;
/*  36 */     float f2 = 0.5F;
/*  37 */     setBlockBounds(f1, 0.0F, f, 1.0F - f, f2, 1.0F - f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  45 */     float f = 0.0625F;
/*  46 */     float f1 = 0.5F;
/*  47 */     setBlockBounds(f, 0.0F, f, 1.0F - f, f1, 1.0F - f);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  52 */     float f = 0.0625F;
/*  53 */     float f1 = (1 + (((Integer)state.getValue((IProperty)BITES)).intValue() << 1)) / 16.0F;
/*  54 */     float f2 = 0.5F;
/*  55 */     return new AxisAlignedBB((pos.getX() + f1), pos.getY(), (pos.getZ() + f), ((pos.getX() + 1) - f), (pos.getY() + f2), ((pos.getZ() + 1) - f));
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/*  60 */     return getCollisionBoundingBox(worldIn, pos, worldIn.getBlockState(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  73 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  78 */     eatCake(worldIn, pos, state, playerIn);
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
/*  84 */     eatCake(worldIn, pos, worldIn.getBlockState(pos), playerIn);
/*     */   }
/*     */ 
/*     */   
/*     */   private void eatCake(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/*  89 */     if (player.canEat(false)) {
/*     */       
/*  91 */       player.triggerAchievement(StatList.field_181724_H);
/*  92 */       player.getFoodStats().addStats(2, 0.1F);
/*  93 */       int i = ((Integer)state.getValue((IProperty)BITES)).intValue();
/*     */       
/*  95 */       if (i < 6) {
/*     */         
/*  97 */         worldIn.setBlockState(pos, state.withProperty((IProperty)BITES, Integer.valueOf(i + 1)), 3);
/*     */       }
/*     */       else {
/*     */         
/* 101 */         worldIn.setBlockToAir(pos);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 108 */     return super.canPlaceBlockAt(worldIn, pos) ? canBlockStay(worldIn, pos) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 116 */     if (!canBlockStay(worldIn, pos))
/*     */     {
/* 118 */       worldIn.setBlockToAir(pos);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canBlockStay(World worldIn, BlockPos pos) {
/* 124 */     return worldIn.getBlockState(pos.down()).getBlock().getMaterial().isSolid();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 132 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 140 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 145 */     return Items.cake;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 150 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 158 */     return getDefaultState().withProperty((IProperty)BITES, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 166 */     return ((Integer)state.getValue((IProperty)BITES)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 171 */     return new BlockState(this, new IProperty[] { (IProperty)BITES });
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 176 */     return 7 - ((Integer)worldIn.getBlockState(pos).getValue((IProperty)BITES)).intValue() << 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/* 181 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockCake.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */