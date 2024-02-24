/*     */ package net.minecraft.block;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockRailBase extends Block {
/*     */   public static boolean isRailBlock(World worldIn, BlockPos pos) {
/*  21 */     return isRailBlock(worldIn.getBlockState(pos));
/*     */   }
/*     */   protected final boolean isPowered;
/*     */   
/*     */   public static boolean isRailBlock(IBlockState state) {
/*  26 */     Block block = state.getBlock();
/*  27 */     return (block == Blocks.rail || block == Blocks.golden_rail || block == Blocks.detector_rail || block == Blocks.activator_rail);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockRailBase(boolean isPowered) {
/*  32 */     super(Material.circuits);
/*  33 */     this.isPowered = isPowered;
/*  34 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*  35 */     setCreativeTab(CreativeTabs.tabTransport);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  40 */     return null;
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
/*     */   public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
/*  56 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  57 */     return super.collisionRayTrace(worldIn, pos, start, end);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  62 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  63 */     EnumRailDirection blockrailbase$enumraildirection = (iblockstate.getBlock() == this) ? (EnumRailDirection)iblockstate.getValue(getShapeProperty()) : null;
/*     */     
/*  65 */     if (blockrailbase$enumraildirection != null && blockrailbase$enumraildirection.isAscending()) {
/*     */       
/*  67 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/*  71 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  82 */     return World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  87 */     if (!worldIn.isRemote) {
/*     */       
/*  89 */       state = func_176564_a(worldIn, pos, state, true);
/*     */       
/*  91 */       if (this.isPowered)
/*     */       {
/*  93 */         onNeighborBlockChange(worldIn, pos, state, this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 103 */     if (!worldIn.isRemote) {
/*     */       
/* 105 */       EnumRailDirection blockrailbase$enumraildirection = (EnumRailDirection)state.getValue(getShapeProperty());
/* 106 */       boolean flag = false;
/*     */       
/* 108 */       if (!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()))
/*     */       {
/* 110 */         flag = true;
/*     */       }
/*     */       
/* 113 */       if (blockrailbase$enumraildirection == EnumRailDirection.ASCENDING_EAST && !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.east())) {
/*     */         
/* 115 */         flag = true;
/*     */       }
/* 117 */       else if (blockrailbase$enumraildirection == EnumRailDirection.ASCENDING_WEST && !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.west())) {
/*     */         
/* 119 */         flag = true;
/*     */       }
/* 121 */       else if (blockrailbase$enumraildirection == EnumRailDirection.ASCENDING_NORTH && !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.north())) {
/*     */         
/* 123 */         flag = true;
/*     */       }
/* 125 */       else if (blockrailbase$enumraildirection == EnumRailDirection.ASCENDING_SOUTH && !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.south())) {
/*     */         
/* 127 */         flag = true;
/*     */       } 
/*     */       
/* 130 */       if (flag) {
/*     */         
/* 132 */         dropBlockAsItem(worldIn, pos, state, 0);
/* 133 */         worldIn.setBlockToAir(pos);
/*     */       }
/*     */       else {
/*     */         
/* 137 */         onNeighborChangedInternal(worldIn, pos, state, neighborBlock);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onNeighborChangedInternal(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {}
/*     */ 
/*     */   
/*     */   protected IBlockState func_176564_a(World worldIn, BlockPos p_176564_2_, IBlockState p_176564_3_, boolean p_176564_4_) {
/* 148 */     return worldIn.isRemote ? p_176564_3_ : (new Rail(worldIn, p_176564_2_, p_176564_3_)).func_180364_a(worldIn.isBlockPowered(p_176564_2_), p_176564_4_).getBlockState();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMobilityFlag() {
/* 153 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 158 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 163 */     super.breakBlock(worldIn, pos, state);
/*     */     
/* 165 */     if (((EnumRailDirection)state.getValue(getShapeProperty())).isAscending())
/*     */     {
/* 167 */       worldIn.notifyNeighborsOfStateChange(pos.up(), this);
/*     */     }
/*     */     
/* 170 */     if (this.isPowered) {
/*     */       
/* 172 */       worldIn.notifyNeighborsOfStateChange(pos, this);
/* 173 */       worldIn.notifyNeighborsOfStateChange(pos.down(), this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract IProperty<EnumRailDirection> getShapeProperty();
/*     */   
/*     */   public enum EnumRailDirection
/*     */     implements IStringSerializable {
/* 181 */     NORTH_SOUTH(0, "north_south"),
/* 182 */     EAST_WEST(1, "east_west"),
/* 183 */     ASCENDING_EAST(2, "ascending_east"),
/* 184 */     ASCENDING_WEST(3, "ascending_west"),
/* 185 */     ASCENDING_NORTH(4, "ascending_north"),
/* 186 */     ASCENDING_SOUTH(5, "ascending_south"),
/* 187 */     SOUTH_EAST(6, "south_east"),
/* 188 */     SOUTH_WEST(7, "south_west"),
/* 189 */     NORTH_WEST(8, "north_west"),
/* 190 */     NORTH_EAST(9, "north_east");
/*     */     
/* 192 */     private static final EnumRailDirection[] META_LOOKUP = new EnumRailDirection[(values()).length];
/*     */ 
/*     */     
/*     */     private final int meta;
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */ 
/*     */     
/*     */     EnumRailDirection(int meta, String name) {
/*     */       this.meta = meta;
/*     */       this.name = name;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getMetadata() {
/*     */       return this.meta;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*     */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isAscending() {
/*     */       return (this == ASCENDING_NORTH || this == ASCENDING_EAST || this == ASCENDING_SOUTH || this == ASCENDING_WEST);
/*     */     }
/*     */ 
/*     */     
/*     */     public static EnumRailDirection byMetadata(int meta) {
/*     */       if (meta < 0 || meta >= META_LOOKUP.length) {
/*     */         meta = 0;
/*     */       }
/*     */       return META_LOOKUP[meta];
/*     */     }
/*     */ 
/*     */     
/*     */     static {
/* 233 */       for (EnumRailDirection blockrailbase$enumraildirection : values())
/*     */       {
/* 235 */         META_LOOKUP[blockrailbase$enumraildirection.meta] = blockrailbase$enumraildirection; } 
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   public class Rail
/*     */   {
/*     */     private final World world;
/*     */     private final BlockPos pos;
/* 247 */     private final List<BlockPos> field_150657_g = Lists.newArrayList();
/*     */     private final BlockRailBase block;
/*     */     
/*     */     public Rail(World worldIn, BlockPos pos, IBlockState state) {
/* 251 */       this.world = worldIn;
/* 252 */       this.pos = pos;
/* 253 */       this.state = state;
/* 254 */       this.block = (BlockRailBase)state.getBlock();
/* 255 */       BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)state.getValue(BlockRailBase.this.getShapeProperty());
/* 256 */       this.isPowered = this.block.isPowered;
/* 257 */       func_180360_a(blockrailbase$enumraildirection);
/*     */     }
/*     */     private IBlockState state; private final boolean isPowered;
/*     */     
/*     */     private void func_180360_a(BlockRailBase.EnumRailDirection p_180360_1_) {
/* 262 */       this.field_150657_g.clear();
/*     */       
/* 264 */       switch (p_180360_1_) {
/*     */         
/*     */         case NORTH_SOUTH:
/* 267 */           this.field_150657_g.add(this.pos.north());
/* 268 */           this.field_150657_g.add(this.pos.south());
/*     */           break;
/*     */         
/*     */         case EAST_WEST:
/* 272 */           this.field_150657_g.add(this.pos.west());
/* 273 */           this.field_150657_g.add(this.pos.east());
/*     */           break;
/*     */         
/*     */         case ASCENDING_EAST:
/* 277 */           this.field_150657_g.add(this.pos.west());
/* 278 */           this.field_150657_g.add(this.pos.east().up());
/*     */           break;
/*     */         
/*     */         case ASCENDING_WEST:
/* 282 */           this.field_150657_g.add(this.pos.west().up());
/* 283 */           this.field_150657_g.add(this.pos.east());
/*     */           break;
/*     */         
/*     */         case ASCENDING_NORTH:
/* 287 */           this.field_150657_g.add(this.pos.north().up());
/* 288 */           this.field_150657_g.add(this.pos.south());
/*     */           break;
/*     */         
/*     */         case ASCENDING_SOUTH:
/* 292 */           this.field_150657_g.add(this.pos.north());
/* 293 */           this.field_150657_g.add(this.pos.south().up());
/*     */           break;
/*     */         
/*     */         case SOUTH_EAST:
/* 297 */           this.field_150657_g.add(this.pos.east());
/* 298 */           this.field_150657_g.add(this.pos.south());
/*     */           break;
/*     */         
/*     */         case SOUTH_WEST:
/* 302 */           this.field_150657_g.add(this.pos.west());
/* 303 */           this.field_150657_g.add(this.pos.south());
/*     */           break;
/*     */         
/*     */         case NORTH_WEST:
/* 307 */           this.field_150657_g.add(this.pos.west());
/* 308 */           this.field_150657_g.add(this.pos.north());
/*     */           break;
/*     */         
/*     */         case NORTH_EAST:
/* 312 */           this.field_150657_g.add(this.pos.east());
/* 313 */           this.field_150657_g.add(this.pos.north());
/*     */           break;
/*     */       } 
/*     */     }
/*     */     
/*     */     private void func_150651_b() {
/* 319 */       for (int i = 0; i < this.field_150657_g.size(); i++) {
/*     */         
/* 321 */         Rail blockrailbase$rail = findRailAt(this.field_150657_g.get(i));
/*     */         
/* 323 */         if (blockrailbase$rail != null && blockrailbase$rail.func_150653_a(this)) {
/*     */           
/* 325 */           this.field_150657_g.set(i, blockrailbase$rail.pos);
/*     */         }
/*     */         else {
/*     */           
/* 329 */           this.field_150657_g.remove(i--);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean hasRailAt(BlockPos pos) {
/* 336 */       return (BlockRailBase.isRailBlock(this.world, pos) || BlockRailBase.isRailBlock(this.world, pos.up()) || BlockRailBase.isRailBlock(this.world, pos.down()));
/*     */     }
/*     */ 
/*     */     
/*     */     private Rail findRailAt(BlockPos pos) {
/* 341 */       IBlockState iblockstate = this.world.getBlockState(pos);
/*     */       
/* 343 */       if (BlockRailBase.isRailBlock(iblockstate)) {
/*     */         
/* 345 */         BlockRailBase.this.getClass(); return new Rail(this.world, pos, iblockstate);
/*     */       } 
/*     */ 
/*     */       
/* 349 */       BlockPos lvt_2_1_ = pos.up();
/* 350 */       iblockstate = this.world.getBlockState(lvt_2_1_);
/*     */       
/* 352 */       if (BlockRailBase.isRailBlock(iblockstate)) {
/*     */         
/* 354 */         BlockRailBase.this.getClass(); return new Rail(this.world, lvt_2_1_, iblockstate);
/*     */       } 
/*     */ 
/*     */       
/* 358 */       lvt_2_1_ = pos.down();
/* 359 */       iblockstate = this.world.getBlockState(lvt_2_1_);
/* 360 */       BlockRailBase.this.getClass(); return BlockRailBase.isRailBlock(iblockstate) ? new Rail(this.world, lvt_2_1_, iblockstate) : null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean func_150653_a(Rail p_150653_1_) {
/* 367 */       return func_180363_c(p_150653_1_.pos);
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean func_180363_c(BlockPos p_180363_1_) {
/* 372 */       for (int i = 0; i < this.field_150657_g.size(); i++) {
/*     */         
/* 374 */         BlockPos blockpos = this.field_150657_g.get(i);
/*     */         
/* 376 */         if (blockpos.getX() == p_180363_1_.getX() && blockpos.getZ() == p_180363_1_.getZ())
/*     */         {
/* 378 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 382 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int countAdjacentRails() {
/* 387 */       int i = 0;
/*     */       
/* 389 */       for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */         
/* 391 */         if (hasRailAt(this.pos.offset(enumfacing)))
/*     */         {
/* 393 */           i++;
/*     */         }
/*     */       } 
/*     */       
/* 397 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean func_150649_b(Rail rail) {
/* 402 */       return (func_150653_a(rail) || this.field_150657_g.size() != 2);
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_150645_c(Rail p_150645_1_) {
/* 407 */       this.field_150657_g.add(p_150645_1_.pos);
/* 408 */       BlockPos blockpos = this.pos.north();
/* 409 */       BlockPos blockpos1 = this.pos.south();
/* 410 */       BlockPos blockpos2 = this.pos.west();
/* 411 */       BlockPos blockpos3 = this.pos.east();
/* 412 */       boolean flag = func_180363_c(blockpos);
/* 413 */       boolean flag1 = func_180363_c(blockpos1);
/* 414 */       boolean flag2 = func_180363_c(blockpos2);
/* 415 */       boolean flag3 = func_180363_c(blockpos3);
/* 416 */       BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = null;
/*     */       
/* 418 */       if (flag || flag1)
/*     */       {
/* 420 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */       }
/*     */       
/* 423 */       if (flag2 || flag3)
/*     */       {
/* 425 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
/*     */       }
/*     */       
/* 428 */       if (!this.isPowered) {
/*     */         
/* 430 */         if (flag1 && flag3 && !flag && !flag2)
/*     */         {
/* 432 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
/*     */         }
/*     */         
/* 435 */         if (flag1 && flag2 && !flag && !flag3)
/*     */         {
/* 437 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
/*     */         }
/*     */         
/* 440 */         if (flag && flag2 && !flag1 && !flag3)
/*     */         {
/* 442 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
/*     */         }
/*     */         
/* 445 */         if (flag && flag3 && !flag1 && !flag2)
/*     */         {
/* 447 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
/*     */         }
/*     */       } 
/*     */       
/* 451 */       if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
/*     */         
/* 453 */         if (BlockRailBase.isRailBlock(this.world, blockpos.up()))
/*     */         {
/* 455 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_NORTH;
/*     */         }
/*     */         
/* 458 */         if (BlockRailBase.isRailBlock(this.world, blockpos1.up()))
/*     */         {
/* 460 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_SOUTH;
/*     */         }
/*     */       } 
/*     */       
/* 464 */       if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST) {
/*     */         
/* 466 */         if (BlockRailBase.isRailBlock(this.world, blockpos3.up()))
/*     */         {
/* 468 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_EAST;
/*     */         }
/*     */         
/* 471 */         if (BlockRailBase.isRailBlock(this.world, blockpos2.up()))
/*     */         {
/* 473 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_WEST;
/*     */         }
/*     */       } 
/*     */       
/* 477 */       if (blockrailbase$enumraildirection == null)
/*     */       {
/* 479 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */       }
/*     */       
/* 482 */       this.state = this.state.withProperty(this.block.getShapeProperty(), blockrailbase$enumraildirection);
/* 483 */       this.world.setBlockState(this.pos, this.state, 3);
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean func_180361_d(BlockPos p_180361_1_) {
/* 488 */       Rail blockrailbase$rail = findRailAt(p_180361_1_);
/*     */       
/* 490 */       if (blockrailbase$rail == null)
/*     */       {
/* 492 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 496 */       blockrailbase$rail.func_150651_b();
/* 497 */       return blockrailbase$rail.func_150649_b(this);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Rail func_180364_a(boolean p_180364_1_, boolean p_180364_2_) {
/* 503 */       BlockPos blockpos = this.pos.north();
/* 504 */       BlockPos blockpos1 = this.pos.south();
/* 505 */       BlockPos blockpos2 = this.pos.west();
/* 506 */       BlockPos blockpos3 = this.pos.east();
/* 507 */       boolean flag = func_180361_d(blockpos);
/* 508 */       boolean flag1 = func_180361_d(blockpos1);
/* 509 */       boolean flag2 = func_180361_d(blockpos2);
/* 510 */       boolean flag3 = func_180361_d(blockpos3);
/* 511 */       BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = null;
/*     */       
/* 513 */       if ((flag || flag1) && !flag2 && !flag3)
/*     */       {
/* 515 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */       }
/*     */       
/* 518 */       if ((flag2 || flag3) && !flag && !flag1)
/*     */       {
/* 520 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
/*     */       }
/*     */       
/* 523 */       if (!this.isPowered) {
/*     */         
/* 525 */         if (flag1 && flag3 && !flag && !flag2)
/*     */         {
/* 527 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
/*     */         }
/*     */         
/* 530 */         if (flag1 && flag2 && !flag && !flag3)
/*     */         {
/* 532 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
/*     */         }
/*     */         
/* 535 */         if (flag && flag2 && !flag1 && !flag3)
/*     */         {
/* 537 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
/*     */         }
/*     */         
/* 540 */         if (flag && flag3 && !flag1 && !flag2)
/*     */         {
/* 542 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
/*     */         }
/*     */       } 
/*     */       
/* 546 */       if (blockrailbase$enumraildirection == null) {
/*     */         
/* 548 */         if (flag || flag1)
/*     */         {
/* 550 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */         }
/*     */         
/* 553 */         if (flag2 || flag3)
/*     */         {
/* 555 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
/*     */         }
/*     */         
/* 558 */         if (!this.isPowered)
/*     */         {
/* 560 */           if (p_180364_1_) {
/*     */             
/* 562 */             if (flag1 && flag3)
/*     */             {
/* 564 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
/*     */             }
/*     */             
/* 567 */             if (flag2 && flag1)
/*     */             {
/* 569 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
/*     */             }
/*     */             
/* 572 */             if (flag3 && flag)
/*     */             {
/* 574 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
/*     */             }
/*     */             
/* 577 */             if (flag && flag2)
/*     */             {
/* 579 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
/*     */             }
/*     */           }
/*     */           else {
/*     */             
/* 584 */             if (flag && flag2)
/*     */             {
/* 586 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
/*     */             }
/*     */             
/* 589 */             if (flag3 && flag)
/*     */             {
/* 591 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
/*     */             }
/*     */             
/* 594 */             if (flag2 && flag1)
/*     */             {
/* 596 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
/*     */             }
/*     */             
/* 599 */             if (flag1 && flag3)
/*     */             {
/* 601 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 607 */       if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
/*     */         
/* 609 */         if (BlockRailBase.isRailBlock(this.world, blockpos.up()))
/*     */         {
/* 611 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_NORTH;
/*     */         }
/*     */         
/* 614 */         if (BlockRailBase.isRailBlock(this.world, blockpos1.up()))
/*     */         {
/* 616 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_SOUTH;
/*     */         }
/*     */       } 
/*     */       
/* 620 */       if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST) {
/*     */         
/* 622 */         if (BlockRailBase.isRailBlock(this.world, blockpos3.up()))
/*     */         {
/* 624 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_EAST;
/*     */         }
/*     */         
/* 627 */         if (BlockRailBase.isRailBlock(this.world, blockpos2.up()))
/*     */         {
/* 629 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_WEST;
/*     */         }
/*     */       } 
/*     */       
/* 633 */       if (blockrailbase$enumraildirection == null)
/*     */       {
/* 635 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */       }
/*     */       
/* 638 */       func_180360_a(blockrailbase$enumraildirection);
/* 639 */       this.state = this.state.withProperty(this.block.getShapeProperty(), blockrailbase$enumraildirection);
/*     */       
/* 641 */       if (p_180364_2_ || this.world.getBlockState(this.pos) != this.state) {
/*     */         
/* 643 */         this.world.setBlockState(this.pos, this.state, 3);
/*     */         
/* 645 */         for (int i = 0; i < this.field_150657_g.size(); i++) {
/*     */           
/* 647 */           Rail blockrailbase$rail = findRailAt(this.field_150657_g.get(i));
/*     */           
/* 649 */           if (blockrailbase$rail != null) {
/*     */             
/* 651 */             blockrailbase$rail.func_150651_b();
/*     */             
/* 653 */             if (blockrailbase$rail.func_150649_b(this))
/*     */             {
/* 655 */               blockrailbase$rail.func_150645_c(this);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 661 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public IBlockState getBlockState() {
/* 666 */       return this.state;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockRailBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */