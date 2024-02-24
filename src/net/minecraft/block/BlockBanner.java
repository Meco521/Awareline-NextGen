/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBanner;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockBanner extends BlockContainer {
/*  27 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*  28 */   public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);
/*     */ 
/*     */   
/*     */   protected BlockBanner() {
/*  32 */     super(Material.wood);
/*  33 */     float f = 0.25F;
/*  34 */     float f1 = 1.0F;
/*  35 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  43 */     return StatCollector.translateToLocal("item.banner.white.name");
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  48 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/*  53 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  54 */     return super.getSelectedBoundingBox(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  59 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  64 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSpawnInBlock() {
/*  80 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  88 */     return (TileEntity)new TileEntityBanner();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  96 */     return Items.banner;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 101 */     return Items.banner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 109 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 111 */     if (tileentity instanceof TileEntityBanner) {
/*     */       
/* 113 */       ItemStack itemstack = new ItemStack(Items.banner, 1, ((TileEntityBanner)tileentity).getBaseColor());
/* 114 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 115 */       tileentity.writeToNBT(nbttagcompound);
/* 116 */       nbttagcompound.removeTag("x");
/* 117 */       nbttagcompound.removeTag("y");
/* 118 */       nbttagcompound.removeTag("z");
/* 119 */       nbttagcompound.removeTag("id");
/* 120 */       itemstack.setTagInfo("BlockEntityTag", (NBTBase)nbttagcompound);
/* 121 */       spawnAsEntity(worldIn, pos, itemstack);
/*     */     }
/*     */     else {
/*     */       
/* 125 */       super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 131 */     return (!hasInvalidNeighbor(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
/* 136 */     if (te instanceof TileEntityBanner) {
/*     */       
/* 138 */       TileEntityBanner tileentitybanner = (TileEntityBanner)te;
/* 139 */       ItemStack itemstack = new ItemStack(Items.banner, 1, ((TileEntityBanner)te).getBaseColor());
/* 140 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 141 */       TileEntityBanner.setBaseColorAndPatterns(nbttagcompound, tileentitybanner.getBaseColor(), tileentitybanner.getPatterns());
/* 142 */       itemstack.setTagInfo("BlockEntityTag", (NBTBase)nbttagcompound);
/* 143 */       spawnAsEntity(worldIn, pos, itemstack);
/*     */     }
/*     */     else {
/*     */       
/* 147 */       super.harvestBlock(worldIn, player, pos, state, (TileEntity)null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class BlockBannerHanging
/*     */     extends BlockBanner
/*     */   {
/*     */     public BlockBannerHanging() {
/* 155 */       setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*     */     }
/*     */ 
/*     */     
/*     */     public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 160 */       EnumFacing enumfacing = (EnumFacing)worldIn.getBlockState(pos).getValue((IProperty)FACING);
/* 161 */       float f = 0.0F;
/* 162 */       float f1 = 0.78125F;
/* 163 */       float f2 = 0.0F;
/* 164 */       float f3 = 1.0F;
/* 165 */       float f4 = 0.125F;
/* 166 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 168 */       switch (enumfacing) {
/*     */ 
/*     */         
/*     */         default:
/* 172 */           setBlockBounds(f2, f, 1.0F - f4, f3, f1, 1.0F);
/*     */           return;
/*     */         
/*     */         case SOUTH:
/* 176 */           setBlockBounds(f2, f, 0.0F, f3, f1, f4);
/*     */           return;
/*     */         
/*     */         case WEST:
/* 180 */           setBlockBounds(1.0F - f4, f, f2, 1.0F, f1, f3); return;
/*     */         case EAST:
/*     */           break;
/*     */       } 
/* 184 */       setBlockBounds(0.0F, f, f2, f4, f1, f3);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 190 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */       
/* 192 */       if (!worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getBlock().getMaterial().isSolid()) {
/*     */         
/* 194 */         dropBlockAsItem(worldIn, pos, state, 0);
/* 195 */         worldIn.setBlockToAir(pos);
/*     */       } 
/*     */       
/* 198 */       super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/*     */     }
/*     */ 
/*     */     
/*     */     public IBlockState getStateFromMeta(int meta) {
/* 203 */       EnumFacing enumfacing = EnumFacing.getFront(meta);
/*     */       
/* 205 */       if (enumfacing.getAxis() == EnumFacing.Axis.Y)
/*     */       {
/* 207 */         enumfacing = EnumFacing.NORTH;
/*     */       }
/*     */       
/* 210 */       return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMetaFromState(IBlockState state) {
/* 215 */       return ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     }
/*     */ 
/*     */     
/*     */     protected BlockState createBlockState() {
/* 220 */       return new BlockState(this, new IProperty[] { (IProperty)FACING });
/*     */     }
/*     */   }
/*     */   
/*     */   public static class BlockBannerStanding
/*     */     extends BlockBanner
/*     */   {
/*     */     public BlockBannerStanding() {
/* 228 */       setDefaultState(this.blockState.getBaseState().withProperty((IProperty)ROTATION, Integer.valueOf(0)));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 233 */       if (!worldIn.getBlockState(pos.down()).getBlock().getMaterial().isSolid()) {
/*     */         
/* 235 */         dropBlockAsItem(worldIn, pos, state, 0);
/* 236 */         worldIn.setBlockToAir(pos);
/*     */       } 
/*     */       
/* 239 */       super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/*     */     }
/*     */ 
/*     */     
/*     */     public IBlockState getStateFromMeta(int meta) {
/* 244 */       return getDefaultState().withProperty((IProperty)ROTATION, Integer.valueOf(meta));
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMetaFromState(IBlockState state) {
/* 249 */       return ((Integer)state.getValue((IProperty)ROTATION)).intValue();
/*     */     }
/*     */ 
/*     */     
/*     */     protected BlockState createBlockState() {
/* 254 */       return new BlockState(this, new IProperty[] { (IProperty)ROTATION });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockBanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */