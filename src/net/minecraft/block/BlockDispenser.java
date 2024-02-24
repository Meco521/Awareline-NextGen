/*     */ package net.minecraft.block;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.dispenser.IBehaviorDispenseItem;
/*     */ import net.minecraft.dispenser.IBlockSource;
/*     */ import net.minecraft.dispenser.IPosition;
/*     */ import net.minecraft.dispenser.PositionImpl;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryHelper;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityDispenser;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.RegistryDefaulted;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockDispenser extends BlockContainer {
/*  30 */   public static final PropertyDirection FACING = PropertyDirection.create("facing");
/*  31 */   public static final PropertyBool TRIGGERED = PropertyBool.create("triggered");
/*  32 */   public static final RegistryDefaulted<Item, IBehaviorDispenseItem> dispenseBehaviorRegistry = new RegistryDefaulted(new BehaviorDefaultDispenseItem());
/*  33 */   protected Random rand = new Random();
/*     */ 
/*     */   
/*     */   protected BlockDispenser() {
/*  37 */     super(Material.rock);
/*  38 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)TRIGGERED, Boolean.valueOf(false)));
/*  39 */     setCreativeTab(CreativeTabs.tabRedstone);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  47 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  52 */     super.onBlockAdded(worldIn, pos, state);
/*  53 */     setDefaultDirection(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setDefaultDirection(World worldIn, BlockPos pos, IBlockState state) {
/*  58 */     if (!worldIn.isRemote) {
/*     */       
/*  60 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*  61 */       boolean flag = worldIn.getBlockState(pos.north()).getBlock().isFullBlock();
/*  62 */       boolean flag1 = worldIn.getBlockState(pos.south()).getBlock().isFullBlock();
/*     */       
/*  64 */       if (enumfacing == EnumFacing.NORTH && flag && !flag1) {
/*     */         
/*  66 */         enumfacing = EnumFacing.SOUTH;
/*     */       }
/*  68 */       else if (enumfacing == EnumFacing.SOUTH && flag1 && !flag) {
/*     */         
/*  70 */         enumfacing = EnumFacing.NORTH;
/*     */       }
/*     */       else {
/*     */         
/*  74 */         boolean flag2 = worldIn.getBlockState(pos.west()).getBlock().isFullBlock();
/*  75 */         boolean flag3 = worldIn.getBlockState(pos.east()).getBlock().isFullBlock();
/*     */         
/*  77 */         if (enumfacing == EnumFacing.WEST && flag2 && !flag3) {
/*     */           
/*  79 */           enumfacing = EnumFacing.EAST;
/*     */         }
/*  81 */         else if (enumfacing == EnumFacing.EAST && flag3 && !flag2) {
/*     */           
/*  83 */           enumfacing = EnumFacing.WEST;
/*     */         } 
/*     */       } 
/*     */       
/*  87 */       worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)TRIGGERED, Boolean.valueOf(false)), 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  93 */     if (worldIn.isRemote)
/*     */     {
/*  95 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  99 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 101 */     if (tileentity instanceof TileEntityDispenser) {
/*     */       
/* 103 */       playerIn.displayGUIChest((IInventory)tileentity);
/*     */       
/* 105 */       if (tileentity instanceof net.minecraft.tileentity.TileEntityDropper) {
/*     */         
/* 107 */         playerIn.triggerAchievement(StatList.field_181731_O);
/*     */       }
/*     */       else {
/*     */         
/* 111 */         playerIn.triggerAchievement(StatList.field_181733_Q);
/*     */       } 
/*     */     } 
/*     */     
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dispense(World worldIn, BlockPos pos) {
/* 121 */     BlockSourceImpl blocksourceimpl = new BlockSourceImpl(worldIn, pos);
/* 122 */     TileEntityDispenser tileentitydispenser = blocksourceimpl.<TileEntityDispenser>getBlockTileEntity();
/*     */     
/* 124 */     if (tileentitydispenser != null) {
/*     */       
/* 126 */       int i = tileentitydispenser.getDispenseSlot();
/*     */       
/* 128 */       if (i < 0) {
/*     */         
/* 130 */         worldIn.playAuxSFX(1001, pos, 0);
/*     */       }
/*     */       else {
/*     */         
/* 134 */         ItemStack itemstack = tileentitydispenser.getStackInSlot(i);
/* 135 */         IBehaviorDispenseItem ibehaviordispenseitem = getBehavior(itemstack);
/*     */         
/* 137 */         if (ibehaviordispenseitem != IBehaviorDispenseItem.itemDispenseBehaviorProvider) {
/*     */           
/* 139 */           ItemStack itemstack1 = ibehaviordispenseitem.dispense(blocksourceimpl, itemstack);
/* 140 */           tileentitydispenser.setInventorySlotContents(i, (itemstack1.stackSize <= 0) ? null : itemstack1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected IBehaviorDispenseItem getBehavior(ItemStack stack) {
/* 148 */     return (IBehaviorDispenseItem)dispenseBehaviorRegistry.getObject((stack == null) ? null : stack.getItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 156 */     boolean flag = (worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up()));
/* 157 */     boolean flag1 = ((Boolean)state.getValue((IProperty)TRIGGERED)).booleanValue();
/*     */     
/* 159 */     if (flag && !flag1) {
/*     */       
/* 161 */       worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/* 162 */       worldIn.setBlockState(pos, state.withProperty((IProperty)TRIGGERED, Boolean.valueOf(true)), 4);
/*     */     }
/* 164 */     else if (!flag && flag1) {
/*     */       
/* 166 */       worldIn.setBlockState(pos, state.withProperty((IProperty)TRIGGERED, Boolean.valueOf(false)), 4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 172 */     if (!worldIn.isRemote)
/*     */     {
/* 174 */       dispense(worldIn, pos);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 183 */     return (TileEntity)new TileEntityDispenser();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 192 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)BlockPistonBase.getFacingFromEntity(worldIn, pos, placer)).withProperty((IProperty)TRIGGERED, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 200 */     worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)BlockPistonBase.getFacingFromEntity(worldIn, pos, placer)), 2);
/*     */     
/* 202 */     if (stack.hasDisplayName()) {
/*     */       
/* 204 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 206 */       if (tileentity instanceof TileEntityDispenser)
/*     */       {
/* 208 */         ((TileEntityDispenser)tileentity).setCustomName(stack.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 215 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 217 */     if (tileentity instanceof TileEntityDispenser) {
/*     */       
/* 219 */       InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
/* 220 */       worldIn.updateComparatorOutputLevel(pos, this);
/*     */     } 
/*     */     
/* 223 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IPosition getDispensePosition(IBlockSource coords) {
/* 231 */     EnumFacing enumfacing = getFacing(coords.getBlockMetadata());
/* 232 */     double d0 = coords.getX() + 0.7D * enumfacing.getFrontOffsetX();
/* 233 */     double d1 = coords.getY() + 0.7D * enumfacing.getFrontOffsetY();
/* 234 */     double d2 = coords.getZ() + 0.7D * enumfacing.getFrontOffsetZ();
/* 235 */     return (IPosition)new PositionImpl(d0, d1, d2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumFacing getFacing(int meta) {
/* 243 */     return EnumFacing.getFront(meta & 0x7);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/* 248 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 253 */     return Container.calcRedstone(worldIn.getTileEntity(pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/* 261 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateForEntityRender(IBlockState state) {
/* 269 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.SOUTH);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 277 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)getFacing(meta)).withProperty((IProperty)TRIGGERED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 285 */     int i = 0;
/* 286 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     
/* 288 */     if (((Boolean)state.getValue((IProperty)TRIGGERED)).booleanValue())
/*     */     {
/* 290 */       i |= 0x8;
/*     */     }
/*     */     
/* 293 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 298 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)TRIGGERED });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockDispenser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */