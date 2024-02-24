/*     */ package net.minecraft.block;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBrewingStand;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockBrewingStand extends BlockContainer {
/*  27 */   public static final PropertyBool[] HAS_BOTTLE = new PropertyBool[] { PropertyBool.create("has_bottle_0"), PropertyBool.create("has_bottle_1"), PropertyBool.create("has_bottle_2") };
/*     */ 
/*     */   
/*     */   public BlockBrewingStand() {
/*  31 */     super(Material.iron);
/*  32 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)HAS_BOTTLE[0], Boolean.valueOf(false)).withProperty((IProperty)HAS_BOTTLE[1], Boolean.valueOf(false)).withProperty((IProperty)HAS_BOTTLE[2], Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  40 */     return StatCollector.translateToLocal("item.brewingStand.name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  48 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/*  56 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  64 */     return (TileEntity)new TileEntityBrewingStand();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/*  77 */     setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 0.875F, 0.5625F);
/*  78 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  79 */     setBlockBoundsForItemRender();
/*  80 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  88 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
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
/* 101 */     if (tileentity instanceof TileEntityBrewingStand) {
/*     */       
/* 103 */       playerIn.displayGUIChest((IInventory)tileentity);
/* 104 */       playerIn.triggerAchievement(StatList.field_181729_M);
/*     */     } 
/*     */     
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 116 */     if (stack.hasDisplayName()) {
/*     */       
/* 118 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 120 */       if (tileentity instanceof TileEntityBrewingStand)
/*     */       {
/* 122 */         ((TileEntityBrewingStand)tileentity).setName(stack.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 129 */     double d0 = (pos.getX() + 0.4F + rand.nextFloat() * 0.2F);
/* 130 */     double d1 = (pos.getY() + 0.7F + rand.nextFloat() * 0.3F);
/* 131 */     double d2 = (pos.getZ() + 0.4F + rand.nextFloat() * 0.2F);
/* 132 */     worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 137 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 139 */     if (tileentity instanceof TileEntityBrewingStand)
/*     */     {
/* 141 */       InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
/*     */     }
/*     */     
/* 144 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 152 */     return Items.brewing_stand;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 157 */     return Items.brewing_stand;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/* 162 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 167 */     return Container.calcRedstone(worldIn.getTileEntity(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 172 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 180 */     IBlockState iblockstate = getDefaultState();
/*     */     
/* 182 */     for (int i = 0; i < 3; i++)
/*     */     {
/* 184 */       iblockstate = iblockstate.withProperty((IProperty)HAS_BOTTLE[i], Boolean.valueOf(((meta & 1 << i) > 0)));
/*     */     }
/*     */     
/* 187 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 195 */     int i = 0;
/*     */     
/* 197 */     for (int j = 0; j < 3; j++) {
/*     */       
/* 199 */       if (((Boolean)state.getValue((IProperty)HAS_BOTTLE[j])).booleanValue())
/*     */       {
/* 201 */         i |= 1 << j;
/*     */       }
/*     */     } 
/*     */     
/* 205 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 210 */     return new BlockState(this, new IProperty[] { (IProperty)HAS_BOTTLE[0], (IProperty)HAS_BOTTLE[1], (IProperty)HAS_BOTTLE[2] });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockBrewingStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */