/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityDaylightDetector;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockDaylightDetector
/*     */   extends BlockContainer
/*     */ {
/*  27 */   public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
/*     */   
/*     */   private final boolean inverted;
/*     */   
/*     */   public BlockDaylightDetector(boolean inverted) {
/*  32 */     super(Material.wood);
/*  33 */     this.inverted = inverted;
/*  34 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)POWER, Integer.valueOf(0)));
/*  35 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
/*  36 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  37 */     setHardness(0.2F);
/*  38 */     setStepSound(soundTypeWood);
/*  39 */     setUnlocalizedName("daylightDetector");
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  44 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/*  49 */     return ((Integer)state.getValue((IProperty)POWER)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePower(World worldIn, BlockPos pos) {
/*  54 */     if (!worldIn.provider.getHasNoSky()) {
/*     */       
/*  56 */       IBlockState iblockstate = worldIn.getBlockState(pos);
/*  57 */       int i = worldIn.getLightFor(EnumSkyBlock.SKY, pos) - worldIn.getSkylightSubtracted();
/*  58 */       float f = worldIn.getCelestialAngleRadians(1.0F);
/*  59 */       float f1 = (f < 3.1415927F) ? 0.0F : 6.2831855F;
/*  60 */       f += (f1 - f) * 0.2F;
/*  61 */       i = Math.round(i * MathHelper.cos(f));
/*  62 */       i = MathHelper.clamp_int(i, 0, 15);
/*     */       
/*  64 */       if (this.inverted)
/*     */       {
/*  66 */         i = 15 - i;
/*     */       }
/*     */       
/*  69 */       if (((Integer)iblockstate.getValue((IProperty)POWER)).intValue() != i)
/*     */       {
/*  71 */         worldIn.setBlockState(pos, iblockstate.withProperty((IProperty)POWER, Integer.valueOf(i)), 3);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  78 */     if (playerIn.isAllowEdit()) {
/*     */       
/*  80 */       if (worldIn.isRemote)
/*     */       {
/*  82 */         return true;
/*     */       }
/*     */ 
/*     */       
/*  86 */       if (this.inverted) {
/*     */         
/*  88 */         worldIn.setBlockState(pos, Blocks.daylight_detector.getDefaultState().withProperty((IProperty)POWER, state.getValue((IProperty)POWER)), 4);
/*  89 */         Blocks.daylight_detector.updatePower(worldIn, pos);
/*     */       }
/*     */       else {
/*     */         
/*  93 */         worldIn.setBlockState(pos, Blocks.daylight_detector_inverted.getDefaultState().withProperty((IProperty)POWER, state.getValue((IProperty)POWER)), 4);
/*  94 */         Blocks.daylight_detector_inverted.updatePower(worldIn, pos);
/*     */       } 
/*     */       
/*  97 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 102 */     return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 111 */     return Item.getItemFromBlock(Blocks.daylight_detector);
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 116 */     return Item.getItemFromBlock(Blocks.daylight_detector);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/* 121 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 129 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/* 137 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 145 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 153 */     return (TileEntity)new TileEntityDaylightDetector();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 161 */     return getDefaultState().withProperty((IProperty)POWER, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 169 */     return ((Integer)state.getValue((IProperty)POWER)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 174 */     return new BlockState(this, new IProperty[] { (IProperty)POWER });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/* 182 */     if (!this.inverted)
/*     */     {
/* 184 */       super.getSubBlocks(itemIn, tab, list);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockDaylightDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */