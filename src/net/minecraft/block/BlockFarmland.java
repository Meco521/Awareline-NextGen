/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockFarmland
/*     */   extends Block
/*     */ {
/*  23 */   public static final PropertyInteger MOISTURE = PropertyInteger.create("moisture", 0, 7);
/*     */ 
/*     */   
/*     */   protected BlockFarmland() {
/*  27 */     super(Material.ground);
/*  28 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)MOISTURE, Integer.valueOf(0)));
/*  29 */     setTickRandomly(true);
/*  30 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
/*  31 */     setLightOpacity(255);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  36 */     return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), (pos.getX() + 1), (pos.getY() + 1), (pos.getZ() + 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  44 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  54 */     int i = ((Integer)state.getValue((IProperty)MOISTURE)).intValue();
/*     */     
/*  56 */     if (!hasWater(worldIn, pos) && !worldIn.isRainingAt(pos.up())) {
/*     */       
/*  58 */       if (i > 0)
/*     */       {
/*  60 */         worldIn.setBlockState(pos, state.withProperty((IProperty)MOISTURE, Integer.valueOf(i - 1)), 2);
/*     */       }
/*  62 */       else if (!hasCrops(worldIn, pos))
/*     */       {
/*  64 */         worldIn.setBlockState(pos, Blocks.dirt.getDefaultState());
/*     */       }
/*     */     
/*  67 */     } else if (i < 7) {
/*     */       
/*  69 */       worldIn.setBlockState(pos, state.withProperty((IProperty)MOISTURE, Integer.valueOf(7)), 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
/*  78 */     if (entityIn instanceof net.minecraft.entity.EntityLivingBase) {
/*     */       
/*  80 */       if (!worldIn.isRemote && worldIn.rand.nextFloat() < fallDistance - 0.5F) {
/*     */         
/*  82 */         if (!(entityIn instanceof net.minecraft.entity.player.EntityPlayer) && !worldIn.getGameRules().getBoolean("mobGriefing")) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  87 */         worldIn.setBlockState(pos, Blocks.dirt.getDefaultState());
/*     */       } 
/*     */       
/*  90 */       super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasCrops(World worldIn, BlockPos pos) {
/*  96 */     Block block = worldIn.getBlockState(pos.up()).getBlock();
/*  97 */     return (block instanceof BlockCrops || block instanceof BlockStem);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasWater(World worldIn, BlockPos pos) {
/* 102 */     for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4))) {
/*     */       
/* 104 */       if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock().getMaterial() == Material.water)
/*     */       {
/* 106 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 118 */     super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/*     */     
/* 120 */     if (worldIn.getBlockState(pos.up()).getBlock().getMaterial().isSolid())
/*     */     {
/* 122 */       worldIn.setBlockState(pos, Blocks.dirt.getDefaultState());
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/*     */     Block block;
/* 128 */     switch (side) {
/*     */       
/*     */       case UP:
/* 131 */         return true;
/*     */       
/*     */       case NORTH:
/*     */       case SOUTH:
/*     */       case WEST:
/*     */       case EAST:
/* 137 */         block = worldIn.getBlockState(pos).getBlock();
/* 138 */         return (!block.isOpaqueCube() && block != Blocks.farmland);
/*     */     } 
/*     */     
/* 141 */     return super.shouldSideBeRendered(worldIn, pos, side);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 150 */     return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), rand, fortune);
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 155 */     return Item.getItemFromBlock(Blocks.dirt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 163 */     return getDefaultState().withProperty((IProperty)MOISTURE, Integer.valueOf(meta & 0x7));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 171 */     return ((Integer)state.getValue((IProperty)MOISTURE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 176 */     return new BlockState(this, new IProperty[] { (IProperty)MOISTURE });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockFarmland.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */