/*     */ package net.minecraft.block;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityPiston;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPistonMoving extends BlockContainer {
/*  22 */   public static final PropertyDirection FACING = BlockPistonExtension.FACING;
/*  23 */   public static final PropertyEnum<BlockPistonExtension.EnumPistonType> TYPE = BlockPistonExtension.TYPE;
/*     */ 
/*     */   
/*     */   public BlockPistonMoving() {
/*  27 */     super(Material.piston);
/*  28 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)TYPE, BlockPistonExtension.EnumPistonType.DEFAULT));
/*  29 */     setHardness(-1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  37 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static TileEntity newTileEntity(IBlockState state, EnumFacing facing, boolean extending, boolean renderHead) {
/*  42 */     return (TileEntity)new TileEntityPiston(state, facing, extending, renderHead);
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/*  47 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  49 */     if (tileentity instanceof TileEntityPiston) {
/*     */       
/*  51 */       ((TileEntityPiston)tileentity).clearPistonTileEntity();
/*     */     }
/*     */     else {
/*     */       
/*  55 */       super.breakBlock(worldIn, pos, state);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
/*  77 */     BlockPos blockpos = pos.offset(((EnumFacing)state.getValue((IProperty)FACING)).getOpposite());
/*  78 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */     
/*  80 */     if (iblockstate.getBlock() instanceof BlockPistonBase && ((Boolean)iblockstate.getValue((IProperty)BlockPistonBase.EXTENDED)).booleanValue())
/*     */     {
/*  82 */       worldIn.setBlockToAir(blockpos);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 101 */     if (!worldIn.isRemote && worldIn.getTileEntity(pos) == null) {
/*     */       
/* 103 */       worldIn.setBlockToAir(pos);
/* 104 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 117 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 125 */     if (!worldIn.isRemote) {
/*     */       
/* 127 */       TileEntityPiston tileentitypiston = getTileEntity((IBlockAccess)worldIn, pos);
/*     */       
/* 129 */       if (tileentitypiston != null) {
/*     */         
/* 131 */         IBlockState iblockstate = tileentitypiston.getPistonState();
/* 132 */         iblockstate.getBlock().dropBlockAsItem(worldIn, pos, iblockstate, 0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
/* 142 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 150 */     if (!worldIn.isRemote)
/*     */     {
/* 152 */       worldIn.getTileEntity(pos);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 158 */     TileEntityPiston tileentitypiston = getTileEntity((IBlockAccess)worldIn, pos);
/*     */     
/* 160 */     if (tileentitypiston == null)
/*     */     {
/* 162 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 166 */     float f = tileentitypiston.getProgress(0.0F);
/*     */     
/* 168 */     if (tileentitypiston.isExtending())
/*     */     {
/* 170 */       f = 1.0F - f;
/*     */     }
/*     */     
/* 173 */     return getBoundingBox(worldIn, pos, tileentitypiston.getPistonState(), f, tileentitypiston.getFacing());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 179 */     TileEntityPiston tileentitypiston = getTileEntity(worldIn, pos);
/*     */     
/* 181 */     if (tileentitypiston != null) {
/*     */       
/* 183 */       IBlockState iblockstate = tileentitypiston.getPistonState();
/* 184 */       Block block = iblockstate.getBlock();
/*     */       
/* 186 */       if (block == this || block.getMaterial() == Material.air) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 191 */       float f = tileentitypiston.getProgress(0.0F);
/*     */       
/* 193 */       if (tileentitypiston.isExtending())
/*     */       {
/* 195 */         f = 1.0F - f;
/*     */       }
/*     */       
/* 198 */       block.setBlockBoundsBasedOnState(worldIn, pos);
/*     */       
/* 200 */       if (block == Blocks.piston || block == Blocks.sticky_piston)
/*     */       {
/* 202 */         f = 0.0F;
/*     */       }
/*     */       
/* 205 */       EnumFacing enumfacing = tileentitypiston.getFacing();
/* 206 */       this.minX = block.getBlockBoundsMinX() - (enumfacing.getFrontOffsetX() * f);
/* 207 */       this.minY = block.getBlockBoundsMinY() - (enumfacing.getFrontOffsetY() * f);
/* 208 */       this.minZ = block.getBlockBoundsMinZ() - (enumfacing.getFrontOffsetZ() * f);
/* 209 */       this.maxX = block.getBlockBoundsMaxX() - (enumfacing.getFrontOffsetX() * f);
/* 210 */       this.maxY = block.getBlockBoundsMaxY() - (enumfacing.getFrontOffsetY() * f);
/* 211 */       this.maxZ = block.getBlockBoundsMaxZ() - (enumfacing.getFrontOffsetZ() * f);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(World worldIn, BlockPos pos, IBlockState extendingBlock, float progress, EnumFacing direction) {
/* 217 */     if (extendingBlock.getBlock() != this && extendingBlock.getBlock().getMaterial() != Material.air) {
/*     */       
/* 219 */       AxisAlignedBB axisalignedbb = extendingBlock.getBlock().getCollisionBoundingBox(worldIn, pos, extendingBlock);
/*     */       
/* 221 */       if (axisalignedbb == null)
/*     */       {
/* 223 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 227 */       double d0 = axisalignedbb.minX;
/* 228 */       double d1 = axisalignedbb.minY;
/* 229 */       double d2 = axisalignedbb.minZ;
/* 230 */       double d3 = axisalignedbb.maxX;
/* 231 */       double d4 = axisalignedbb.maxY;
/* 232 */       double d5 = axisalignedbb.maxZ;
/*     */       
/* 234 */       if (direction.getFrontOffsetX() < 0) {
/*     */         
/* 236 */         d0 -= (direction.getFrontOffsetX() * progress);
/*     */       }
/*     */       else {
/*     */         
/* 240 */         d3 -= (direction.getFrontOffsetX() * progress);
/*     */       } 
/*     */       
/* 243 */       if (direction.getFrontOffsetY() < 0) {
/*     */         
/* 245 */         d1 -= (direction.getFrontOffsetY() * progress);
/*     */       }
/*     */       else {
/*     */         
/* 249 */         d4 -= (direction.getFrontOffsetY() * progress);
/*     */       } 
/*     */       
/* 252 */       if (direction.getFrontOffsetZ() < 0) {
/*     */         
/* 254 */         d2 -= (direction.getFrontOffsetZ() * progress);
/*     */       }
/*     */       else {
/*     */         
/* 258 */         d5 -= (direction.getFrontOffsetZ() * progress);
/*     */       } 
/*     */       
/* 261 */       return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 266 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private TileEntityPiston getTileEntity(IBlockAccess worldIn, BlockPos pos) {
/* 272 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 273 */     return (tileentity instanceof TileEntityPiston) ? (TileEntityPiston)tileentity : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 278 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 286 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)BlockPistonExtension.getFacing(meta)).withProperty((IProperty)TYPE, ((meta & 0x8) > 0) ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 294 */     int i = 0;
/* 295 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     
/* 297 */     if (state.getValue((IProperty)TYPE) == BlockPistonExtension.EnumPistonType.STICKY)
/*     */     {
/* 299 */       i |= 0x8;
/*     */     }
/*     */     
/* 302 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 307 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)TYPE });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockPistonMoving.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */