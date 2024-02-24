/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryHelper;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityHopper;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockHopper
/*     */   extends BlockContainer {
/*  32 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>()
/*     */       {
/*     */         public boolean apply(EnumFacing p_apply_1_)
/*     */         {
/*  36 */           return (p_apply_1_ != EnumFacing.UP);
/*     */         }
/*     */       });
/*  39 */   public static final PropertyBool ENABLED = PropertyBool.create("enabled");
/*     */ 
/*     */   
/*     */   public BlockHopper() {
/*  43 */     super(Material.iron, MapColor.stoneColor);
/*  44 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.DOWN).withProperty((IProperty)ENABLED, Boolean.valueOf(true)));
/*  45 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  46 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  51 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/*  59 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
/*  60 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  61 */     float f = 0.125F;
/*  62 */     setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*  63 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  64 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*  65 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  66 */     setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  67 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  68 */     setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*  69 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  70 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  79 */     EnumFacing enumfacing = facing.getOpposite();
/*     */     
/*  81 */     if (enumfacing == EnumFacing.UP)
/*     */     {
/*  83 */       enumfacing = EnumFacing.DOWN;
/*     */     }
/*     */     
/*  86 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)ENABLED, Boolean.valueOf(true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  94 */     return (TileEntity)new TileEntityHopper();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 102 */     super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
/*     */     
/* 104 */     if (stack.hasDisplayName()) {
/*     */       
/* 106 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 108 */       if (tileentity instanceof TileEntityHopper)
/*     */       {
/* 110 */         ((TileEntityHopper)tileentity).setCustomName(stack.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 117 */     updateState(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 122 */     if (worldIn.isRemote)
/*     */     {
/* 124 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 128 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 130 */     if (tileentity instanceof TileEntityHopper) {
/*     */       
/* 132 */       playerIn.displayGUIChest((IInventory)tileentity);
/* 133 */       playerIn.triggerAchievement(StatList.field_181732_P);
/*     */     } 
/*     */     
/* 136 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 145 */     updateState(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateState(World worldIn, BlockPos pos, IBlockState state) {
/* 150 */     boolean flag = !worldIn.isBlockPowered(pos);
/*     */     
/* 152 */     if (flag != ((Boolean)state.getValue((IProperty)ENABLED)).booleanValue())
/*     */     {
/* 154 */       worldIn.setBlockState(pos, state.withProperty((IProperty)ENABLED, Boolean.valueOf(flag)), 4);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 160 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 162 */     if (tileentity instanceof TileEntityHopper) {
/*     */       
/* 164 */       InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
/* 165 */       worldIn.updateComparatorOutputLevel(pos, this);
/*     */     } 
/*     */     
/* 168 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/* 176 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/* 181 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 189 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 194 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumFacing getFacing(int meta) {
/* 199 */     return EnumFacing.getFront(meta & 0x7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEnabled(int meta) {
/* 208 */     return ((meta & 0x8) != 8);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/* 213 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 218 */     return Container.calcRedstone(worldIn.getTileEntity(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 223 */     return EnumWorldBlockLayer.CUTOUT_MIPPED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 231 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)getFacing(meta)).withProperty((IProperty)ENABLED, Boolean.valueOf(isEnabled(meta)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 239 */     int i = 0;
/* 240 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     
/* 242 */     if (!((Boolean)state.getValue((IProperty)ENABLED)).booleanValue())
/*     */     {
/* 244 */       i |= 0x8;
/*     */     }
/*     */     
/* 247 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 252 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)ENABLED });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockHopper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */