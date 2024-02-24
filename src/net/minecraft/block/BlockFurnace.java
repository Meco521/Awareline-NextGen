/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryHelper;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityFurnace;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockFurnace extends BlockContainer {
/*  27 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*     */   
/*     */   private final boolean isBurning;
/*     */   private static boolean keepInventory;
/*     */   
/*     */   protected BlockFurnace(boolean isBurning) {
/*  33 */     super(Material.rock);
/*  34 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*  35 */     this.isBurning = isBurning;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  43 */     return Item.getItemFromBlock(Blocks.furnace);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  48 */     setDefaultFacing(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
/*  53 */     if (!worldIn.isRemote) {
/*     */       
/*  55 */       Block block = worldIn.getBlockState(pos.north()).getBlock();
/*  56 */       Block block1 = worldIn.getBlockState(pos.south()).getBlock();
/*  57 */       Block block2 = worldIn.getBlockState(pos.west()).getBlock();
/*  58 */       Block block3 = worldIn.getBlockState(pos.east()).getBlock();
/*  59 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */       
/*  61 */       if (enumfacing == EnumFacing.NORTH && block.isFullBlock() && !block1.isFullBlock()) {
/*     */         
/*  63 */         enumfacing = EnumFacing.SOUTH;
/*     */       }
/*  65 */       else if (enumfacing == EnumFacing.SOUTH && block1.isFullBlock() && !block.isFullBlock()) {
/*     */         
/*  67 */         enumfacing = EnumFacing.NORTH;
/*     */       }
/*  69 */       else if (enumfacing == EnumFacing.WEST && block2.isFullBlock() && !block3.isFullBlock()) {
/*     */         
/*  71 */         enumfacing = EnumFacing.EAST;
/*     */       }
/*  73 */       else if (enumfacing == EnumFacing.EAST && block3.isFullBlock() && !block2.isFullBlock()) {
/*     */         
/*  75 */         enumfacing = EnumFacing.WEST;
/*     */       } 
/*     */       
/*  78 */       worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)enumfacing), 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  85 */     if (this.isBurning) {
/*     */       
/*  87 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*  88 */       double d0 = pos.getX() + 0.5D;
/*  89 */       double d1 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
/*  90 */       double d2 = pos.getZ() + 0.5D;
/*  91 */       double d3 = 0.52D;
/*  92 */       double d4 = rand.nextDouble() * 0.6D - 0.3D;
/*     */       
/*  94 */       switch (enumfacing) {
/*     */         
/*     */         case WEST:
/*  97 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*  98 */           worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           break;
/*     */         
/*     */         case EAST:
/* 102 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/* 103 */           worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           break;
/*     */         
/*     */         case NORTH:
/* 107 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
/* 108 */           worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 112 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
/* 113 */           worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 120 */     if (worldIn.isRemote)
/*     */     {
/* 122 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 126 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 128 */     if (tileentity instanceof TileEntityFurnace) {
/*     */       
/* 130 */       playerIn.displayGUIChest((IInventory)tileentity);
/* 131 */       playerIn.triggerAchievement(StatList.field_181741_Y);
/*     */     } 
/*     */     
/* 134 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setState(boolean active, World worldIn, BlockPos pos) {
/* 140 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 141 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 142 */     keepInventory = true;
/*     */     
/* 144 */     if (active) {
/*     */       
/* 146 */       worldIn.setBlockState(pos, Blocks.lit_furnace.getDefaultState().withProperty((IProperty)FACING, iblockstate.getValue((IProperty)FACING)), 3);
/* 147 */       worldIn.setBlockState(pos, Blocks.lit_furnace.getDefaultState().withProperty((IProperty)FACING, iblockstate.getValue((IProperty)FACING)), 3);
/*     */     }
/*     */     else {
/*     */       
/* 151 */       worldIn.setBlockState(pos, Blocks.furnace.getDefaultState().withProperty((IProperty)FACING, iblockstate.getValue((IProperty)FACING)), 3);
/* 152 */       worldIn.setBlockState(pos, Blocks.furnace.getDefaultState().withProperty((IProperty)FACING, iblockstate.getValue((IProperty)FACING)), 3);
/*     */     } 
/*     */     
/* 155 */     keepInventory = false;
/*     */     
/* 157 */     if (tileentity != null) {
/*     */       
/* 159 */       tileentity.validate();
/* 160 */       worldIn.setTileEntity(pos, tileentity);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 169 */     return (TileEntity)new TileEntityFurnace();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 178 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 186 */     worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite()), 2);
/*     */     
/* 188 */     if (stack.hasDisplayName()) {
/*     */       
/* 190 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 192 */       if (tileentity instanceof TileEntityFurnace)
/*     */       {
/* 194 */         ((TileEntityFurnace)tileentity).setCustomInventoryName(stack.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 201 */     if (!keepInventory) {
/*     */       
/* 203 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 205 */       if (tileentity instanceof TileEntityFurnace) {
/*     */         
/* 207 */         InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
/* 208 */         worldIn.updateComparatorOutputLevel(pos, this);
/*     */       } 
/*     */     } 
/*     */     
/* 212 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/* 217 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 222 */     return Container.calcRedstone(worldIn.getTileEntity(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 227 */     return Item.getItemFromBlock(Blocks.furnace);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/* 235 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateForEntityRender(IBlockState state) {
/* 243 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.SOUTH);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 251 */     EnumFacing enumfacing = EnumFacing.getFront(meta);
/*     */     
/* 253 */     if (enumfacing.getAxis() == EnumFacing.Axis.Y)
/*     */     {
/* 255 */       enumfacing = EnumFacing.NORTH;
/*     */     }
/*     */     
/* 258 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 266 */     return ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 271 */     return new BlockState(this, new IProperty[] { (IProperty)FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */