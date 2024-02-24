/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemLead;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class BlockFence
/*     */   extends Block
/*     */ {
/*  25 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*     */ 
/*     */   
/*  28 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*     */ 
/*     */   
/*  31 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*     */ 
/*     */   
/*  34 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*     */ 
/*     */   
/*     */   public BlockFence(Material materialIn) {
/*  38 */     this(materialIn, materialIn.getMaterialMapColor());
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFence(Material p_i46395_1_, MapColor p_i46395_2_) {
/*  43 */     super(p_i46395_1_, p_i46395_2_);
/*  44 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false)));
/*  45 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/*  53 */     boolean flag = canConnectTo((IBlockAccess)worldIn, pos.north());
/*  54 */     boolean flag1 = canConnectTo((IBlockAccess)worldIn, pos.south());
/*  55 */     boolean flag2 = canConnectTo((IBlockAccess)worldIn, pos.west());
/*  56 */     boolean flag3 = canConnectTo((IBlockAccess)worldIn, pos.east());
/*  57 */     float f = 0.375F;
/*  58 */     float f1 = 0.625F;
/*  59 */     float f2 = 0.375F;
/*  60 */     float f3 = 0.625F;
/*     */     
/*  62 */     if (flag)
/*     */     {
/*  64 */       f2 = 0.0F;
/*     */     }
/*     */     
/*  67 */     if (flag1)
/*     */     {
/*  69 */       f3 = 1.0F;
/*     */     }
/*     */     
/*  72 */     if (flag || flag1) {
/*     */       
/*  74 */       setBlockBounds(f, 0.0F, f2, f1, 1.5F, f3);
/*  75 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     } 
/*     */     
/*  78 */     f2 = 0.375F;
/*  79 */     f3 = 0.625F;
/*     */     
/*  81 */     if (flag2)
/*     */     {
/*  83 */       f = 0.0F;
/*     */     }
/*     */     
/*  86 */     if (flag3)
/*     */     {
/*  88 */       f1 = 1.0F;
/*     */     }
/*     */     
/*  91 */     if (flag2 || flag3 || (!flag && !flag1)) {
/*     */       
/*  93 */       setBlockBounds(f, 0.0F, f2, f1, 1.5F, f3);
/*  94 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     } 
/*     */     
/*  97 */     if (flag)
/*     */     {
/*  99 */       f2 = 0.0F;
/*     */     }
/*     */     
/* 102 */     if (flag1)
/*     */     {
/* 104 */       f3 = 1.0F;
/*     */     }
/*     */     
/* 107 */     setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 112 */     boolean flag = canConnectTo(worldIn, pos.north());
/* 113 */     boolean flag1 = canConnectTo(worldIn, pos.south());
/* 114 */     boolean flag2 = canConnectTo(worldIn, pos.west());
/* 115 */     boolean flag3 = canConnectTo(worldIn, pos.east());
/* 116 */     float f = 0.375F;
/* 117 */     float f1 = 0.625F;
/* 118 */     float f2 = 0.375F;
/* 119 */     float f3 = 0.625F;
/*     */     
/* 121 */     if (flag)
/*     */     {
/* 123 */       f2 = 0.0F;
/*     */     }
/*     */     
/* 126 */     if (flag1)
/*     */     {
/* 128 */       f3 = 1.0F;
/*     */     }
/*     */     
/* 131 */     if (flag2)
/*     */     {
/* 133 */       f = 0.0F;
/*     */     }
/*     */     
/* 136 */     if (flag3)
/*     */     {
/* 138 */       f1 = 1.0F;
/*     */     }
/*     */     
/* 141 */     setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 149 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/* 154 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/* 159 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
/* 164 */     Block block = worldIn.getBlockState(pos).getBlock();
/* 165 */     return (block == Blocks.barrier) ? false : (((!(block instanceof BlockFence) || block.blockMaterial != this.blockMaterial) && !(block instanceof BlockFenceGate)) ? ((block.blockMaterial.isOpaque() && block.isFullCube()) ? ((block.blockMaterial != Material.gourd)) : false) : true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 170 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 175 */     return worldIn.isRemote ? true : ItemLead.attachToFence(playerIn, worldIn, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 183 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 192 */     return state.withProperty((IProperty)NORTH, Boolean.valueOf(canConnectTo(worldIn, pos.north()))).withProperty((IProperty)EAST, Boolean.valueOf(canConnectTo(worldIn, pos.east()))).withProperty((IProperty)SOUTH, Boolean.valueOf(canConnectTo(worldIn, pos.south()))).withProperty((IProperty)WEST, Boolean.valueOf(canConnectTo(worldIn, pos.west())));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 197 */     return new BlockState(this, new IProperty[] { (IProperty)NORTH, (IProperty)EAST, (IProperty)WEST, (IProperty)SOUTH });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockFence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */