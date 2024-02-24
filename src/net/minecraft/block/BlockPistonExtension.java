/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPistonExtension
/*     */   extends Block
/*     */ {
/*  26 */   public static final PropertyDirection FACING = PropertyDirection.create("facing");
/*  27 */   public static final PropertyEnum<EnumPistonType> TYPE = PropertyEnum.create("type", EnumPistonType.class);
/*  28 */   public static final PropertyBool SHORT = PropertyBool.create("short");
/*     */ 
/*     */   
/*     */   public BlockPistonExtension() {
/*  32 */     super(Material.piston);
/*  33 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)TYPE, EnumPistonType.DEFAULT).withProperty((IProperty)SHORT, Boolean.valueOf(false)));
/*  34 */     setStepSound(soundTypePiston);
/*  35 */     setHardness(0.5F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/*  40 */     if (player.capabilities.isCreativeMode) {
/*     */       
/*  42 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */       
/*  44 */       if (enumfacing != null) {
/*     */         
/*  46 */         BlockPos blockpos = pos.offset(enumfacing.getOpposite());
/*  47 */         Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */         
/*  49 */         if (block == Blocks.piston || block == Blocks.sticky_piston)
/*     */         {
/*  51 */           worldIn.setBlockToAir(blockpos);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  56 */     super.onBlockHarvested(worldIn, pos, state, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/*  61 */     super.breakBlock(worldIn, pos, state);
/*  62 */     EnumFacing enumfacing = ((EnumFacing)state.getValue((IProperty)FACING)).getOpposite();
/*  63 */     pos = pos.offset(enumfacing);
/*  64 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  66 */     if ((iblockstate.getBlock() == Blocks.piston || iblockstate.getBlock() == Blocks.sticky_piston) && ((Boolean)iblockstate.getValue((IProperty)BlockPistonBase.EXTENDED)).booleanValue()) {
/*     */       
/*  68 */       iblockstate.getBlock().dropBlockAsItem(worldIn, pos, iblockstate, 0);
/*  69 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 104 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/* 112 */     applyHeadBounds(state);
/* 113 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/* 114 */     applyCoreBounds(state);
/* 115 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/* 116 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private void applyCoreBounds(IBlockState state) {
/* 121 */     float f = 0.25F;
/* 122 */     float f1 = 0.375F;
/* 123 */     float f2 = 0.625F;
/* 124 */     float f3 = 0.25F;
/* 125 */     float f4 = 0.75F;
/*     */     
/* 127 */     switch ((EnumFacing)state.getValue((IProperty)FACING)) {
/*     */       
/*     */       case DOWN:
/* 130 */         setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
/*     */         break;
/*     */       
/*     */       case UP:
/* 134 */         setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 138 */         setBlockBounds(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 142 */         setBlockBounds(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
/*     */         break;
/*     */       
/*     */       case WEST:
/* 146 */         setBlockBounds(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
/*     */         break;
/*     */       
/*     */       case EAST:
/* 150 */         setBlockBounds(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 156 */     applyHeadBounds(worldIn.getBlockState(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyHeadBounds(IBlockState state) {
/* 161 */     float f = 0.25F;
/* 162 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */     
/* 164 */     if (enumfacing != null)
/*     */     {
/* 166 */       switch (enumfacing) {
/*     */         
/*     */         case DOWN:
/* 169 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
/*     */           break;
/*     */         
/*     */         case UP:
/* 173 */           setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case NORTH:
/* 177 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 181 */           setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case WEST:
/* 185 */           setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case EAST:
/* 189 */           setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 199 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 200 */     BlockPos blockpos = pos.offset(enumfacing.getOpposite());
/* 201 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */     
/* 203 */     if (iblockstate.getBlock() != Blocks.piston && iblockstate.getBlock() != Blocks.sticky_piston) {
/*     */       
/* 205 */       worldIn.setBlockToAir(pos);
/*     */     }
/*     */     else {
/*     */       
/* 209 */       iblockstate.getBlock().onNeighborBlockChange(worldIn, blockpos, iblockstate, neighborBlock);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 215 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumFacing getFacing(int meta) {
/* 220 */     int i = meta & 0x7;
/* 221 */     return (i > 5) ? null : EnumFacing.getFront(i);
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 226 */     return (worldIn.getBlockState(pos).getValue((IProperty)TYPE) == EnumPistonType.STICKY) ? Item.getItemFromBlock(Blocks.sticky_piston) : Item.getItemFromBlock(Blocks.piston);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 234 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)getFacing(meta)).withProperty((IProperty)TYPE, ((meta & 0x8) > 0) ? EnumPistonType.STICKY : EnumPistonType.DEFAULT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 242 */     int i = 0;
/* 243 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     
/* 245 */     if (state.getValue((IProperty)TYPE) == EnumPistonType.STICKY)
/*     */     {
/* 247 */       i |= 0x8;
/*     */     }
/*     */     
/* 250 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 255 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)TYPE, (IProperty)SHORT });
/*     */   }
/*     */   
/*     */   public enum EnumPistonType
/*     */     implements IStringSerializable {
/* 260 */     DEFAULT("normal"),
/* 261 */     STICKY("sticky");
/*     */     
/*     */     private final String VARIANT;
/*     */ 
/*     */     
/*     */     EnumPistonType(String name) {
/* 267 */       this.VARIANT = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 272 */       return this.VARIANT;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 277 */       return this.VARIANT;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockPistonExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */