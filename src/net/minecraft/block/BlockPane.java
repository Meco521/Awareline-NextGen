/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPane
/*     */   extends Block
/*     */ {
/*  24 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*  25 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*  26 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*  27 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*     */   
/*     */   private final boolean canDrop;
/*     */   
/*     */   protected BlockPane(Material materialIn, boolean canDrop) {
/*  32 */     super(materialIn);
/*  33 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false)));
/*  34 */     this.canDrop = canDrop;
/*  35 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  44 */     return state.withProperty((IProperty)NORTH, Boolean.valueOf(canPaneConnectToBlock(worldIn.getBlockState(pos.north()).getBlock()))).withProperty((IProperty)SOUTH, Boolean.valueOf(canPaneConnectToBlock(worldIn.getBlockState(pos.south()).getBlock()))).withProperty((IProperty)WEST, Boolean.valueOf(canPaneConnectToBlock(worldIn.getBlockState(pos.west()).getBlock()))).withProperty((IProperty)EAST, Boolean.valueOf(canPaneConnectToBlock(worldIn.getBlockState(pos.east()).getBlock())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  52 */     return !this.canDrop ? null : super.getItemDropped(state, rand, fortune);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/*  70 */     return (worldIn.getBlockState(pos).getBlock() == this) ? false : super.shouldSideBeRendered(worldIn, pos, side);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/*  78 */     boolean flag = canPaneConnectToBlock(worldIn.getBlockState(pos.north()).getBlock());
/*  79 */     boolean flag1 = canPaneConnectToBlock(worldIn.getBlockState(pos.south()).getBlock());
/*  80 */     boolean flag2 = canPaneConnectToBlock(worldIn.getBlockState(pos.west()).getBlock());
/*  81 */     boolean flag3 = canPaneConnectToBlock(worldIn.getBlockState(pos.east()).getBlock());
/*     */     
/*  83 */     if ((!flag2 || !flag3) && (flag2 || flag3 || flag || flag1)) {
/*     */       
/*  85 */       if (flag2)
/*     */       {
/*  87 */         setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
/*  88 */         super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */       }
/*  90 */       else if (flag3)
/*     */       {
/*  92 */         setBlockBounds(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
/*  93 */         super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  98 */       setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
/*  99 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     } 
/*     */     
/* 102 */     if ((!flag || !flag1) && (flag2 || flag3 || flag || flag1)) {
/*     */       
/* 104 */       if (flag)
/*     */       {
/* 106 */         setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
/* 107 */         super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */       }
/* 109 */       else if (flag1)
/*     */       {
/* 111 */         setBlockBounds(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
/* 112 */         super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 117 */       setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
/* 118 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/* 127 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 132 */     float f = 0.4375F;
/* 133 */     float f1 = 0.5625F;
/* 134 */     float f2 = 0.4375F;
/* 135 */     float f3 = 0.5625F;
/* 136 */     boolean flag = canPaneConnectToBlock(worldIn.getBlockState(pos.north()).getBlock());
/* 137 */     boolean flag1 = canPaneConnectToBlock(worldIn.getBlockState(pos.south()).getBlock());
/* 138 */     boolean flag2 = canPaneConnectToBlock(worldIn.getBlockState(pos.west()).getBlock());
/* 139 */     boolean flag3 = canPaneConnectToBlock(worldIn.getBlockState(pos.east()).getBlock());
/*     */     
/* 141 */     if ((!flag2 || !flag3) && (flag2 || flag3 || flag || flag1)) {
/*     */       
/* 143 */       if (flag2)
/*     */       {
/* 145 */         f = 0.0F;
/*     */       }
/* 147 */       else if (flag3)
/*     */       {
/* 149 */         f1 = 1.0F;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 154 */       f = 0.0F;
/* 155 */       f1 = 1.0F;
/*     */     } 
/*     */     
/* 158 */     if ((!flag || !flag1) && (flag2 || flag3 || flag || flag1)) {
/*     */       
/* 160 */       if (flag)
/*     */       {
/* 162 */         f2 = 0.0F;
/*     */       }
/* 164 */       else if (flag1)
/*     */       {
/* 166 */         f3 = 1.0F;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 171 */       f2 = 0.0F;
/* 172 */       f3 = 1.0F;
/*     */     } 
/*     */     
/* 175 */     setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean canPaneConnectToBlock(Block blockIn) {
/* 180 */     return (blockIn.isFullBlock() || blockIn == this || blockIn == Blocks.glass || blockIn == Blocks.stained_glass || blockIn == Blocks.stained_glass_pane || blockIn instanceof BlockPane);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canSilkHarvest() {
/* 185 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 190 */     return EnumWorldBlockLayer.CUTOUT_MIPPED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 198 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 203 */     return new BlockState(this, new IProperty[] { (IProperty)NORTH, (IProperty)EAST, (IProperty)WEST, (IProperty)SOUTH });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */