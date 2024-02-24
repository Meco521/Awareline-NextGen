/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.ColorizerFoliage;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockVine
/*     */   extends Block
/*     */ {
/*  29 */   public static final PropertyBool UP = PropertyBool.create("up");
/*  30 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*  31 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*  32 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*  33 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*  34 */   public static final PropertyBool[] ALL_FACES = new PropertyBool[] { UP, NORTH, SOUTH, WEST, EAST };
/*     */ 
/*     */   
/*     */   public BlockVine() {
/*  38 */     super(Material.vine);
/*  39 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)UP, Boolean.valueOf(false)).withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false)));
/*  40 */     setTickRandomly(true);
/*  41 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  50 */     return state.withProperty((IProperty)UP, Boolean.valueOf(worldIn.getBlockState(pos.up()).getBlock().isBlockNormalCube()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  58 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReplaceable(World worldIn, BlockPos pos) {
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  84 */     float f = 0.0625F;
/*  85 */     float f1 = 1.0F;
/*  86 */     float f2 = 1.0F;
/*  87 */     float f3 = 1.0F;
/*  88 */     float f4 = 0.0F;
/*  89 */     float f5 = 0.0F;
/*  90 */     float f6 = 0.0F;
/*  91 */     boolean flag = false;
/*     */     
/*  93 */     if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)WEST)).booleanValue()) {
/*     */       
/*  95 */       f4 = Math.max(f4, 0.0625F);
/*  96 */       f1 = 0.0F;
/*  97 */       f2 = 0.0F;
/*  98 */       f5 = 1.0F;
/*  99 */       f3 = 0.0F;
/* 100 */       f6 = 1.0F;
/* 101 */       flag = true;
/*     */     } 
/*     */     
/* 104 */     if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)EAST)).booleanValue()) {
/*     */       
/* 106 */       f1 = Math.min(f1, 0.9375F);
/* 107 */       f4 = 1.0F;
/* 108 */       f2 = 0.0F;
/* 109 */       f5 = 1.0F;
/* 110 */       f3 = 0.0F;
/* 111 */       f6 = 1.0F;
/* 112 */       flag = true;
/*     */     } 
/*     */     
/* 115 */     if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)NORTH)).booleanValue()) {
/*     */       
/* 117 */       f6 = Math.max(f6, 0.0625F);
/* 118 */       f3 = 0.0F;
/* 119 */       f1 = 0.0F;
/* 120 */       f4 = 1.0F;
/* 121 */       f2 = 0.0F;
/* 122 */       f5 = 1.0F;
/* 123 */       flag = true;
/*     */     } 
/*     */     
/* 126 */     if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)SOUTH)).booleanValue()) {
/*     */       
/* 128 */       f3 = Math.min(f3, 0.9375F);
/* 129 */       f6 = 1.0F;
/* 130 */       f1 = 0.0F;
/* 131 */       f4 = 1.0F;
/* 132 */       f2 = 0.0F;
/* 133 */       f5 = 1.0F;
/* 134 */       flag = true;
/*     */     } 
/*     */     
/* 137 */     if (!flag && canPlaceOn(worldIn.getBlockState(pos.up()).getBlock())) {
/*     */       
/* 139 */       f2 = Math.min(f2, 0.9375F);
/* 140 */       f5 = 1.0F;
/* 141 */       f1 = 0.0F;
/* 142 */       f4 = 1.0F;
/* 143 */       f3 = 0.0F;
/* 144 */       f6 = 1.0F;
/*     */     } 
/*     */     
/* 147 */     setBlockBounds(f1, f2, f3, f4, f5, f6);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 152 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/* 160 */     switch (side) {
/*     */       
/*     */       case UP:
/* 163 */         return canPlaceOn(worldIn.getBlockState(pos.up()).getBlock());
/*     */       
/*     */       case NORTH:
/*     */       case SOUTH:
/*     */       case EAST:
/*     */       case WEST:
/* 169 */         return canPlaceOn(worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock());
/*     */     } 
/*     */     
/* 172 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canPlaceOn(Block blockIn) {
/* 178 */     return (blockIn.isFullCube() && blockIn.blockMaterial.blocksMovement());
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean recheckGrownSides(World worldIn, BlockPos pos, IBlockState state) {
/* 183 */     IBlockState iblockstate = state;
/*     */     
/* 185 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 187 */       PropertyBool propertybool = getPropertyFor(enumfacing);
/*     */       
/* 189 */       if (((Boolean)state.getValue((IProperty)propertybool)).booleanValue() && !canPlaceOn(worldIn.getBlockState(pos.offset(enumfacing)).getBlock())) {
/*     */         
/* 191 */         IBlockState iblockstate1 = worldIn.getBlockState(pos.up());
/*     */         
/* 193 */         if (iblockstate1.getBlock() != this || !((Boolean)iblockstate1.getValue((IProperty)propertybool)).booleanValue())
/*     */         {
/* 195 */           state = state.withProperty((IProperty)propertybool, Boolean.valueOf(false));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 200 */     if (getNumGrownFaces(state) == 0)
/*     */     {
/* 202 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 206 */     if (iblockstate != state)
/*     */     {
/* 208 */       worldIn.setBlockState(pos, state, 2);
/*     */     }
/*     */     
/* 211 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlockColor() {
/* 217 */     return ColorizerFoliage.getFoliageColorBasic();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRenderColor(IBlockState state) {
/* 222 */     return ColorizerFoliage.getFoliageColorBasic();
/*     */   }
/*     */ 
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/* 227 */     return worldIn.getBiomeGenForCoords(pos).getFoliageColorAtPos(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 235 */     if (!worldIn.isRemote && !recheckGrownSides(worldIn, pos, state)) {
/*     */       
/* 237 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 238 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 244 */     if (!worldIn.isRemote)
/*     */     {
/* 246 */       if (worldIn.rand.nextInt(4) == 0) {
/*     */         
/* 248 */         int i = 4;
/* 249 */         int j = 5;
/* 250 */         boolean flag = false;
/*     */         
/*     */         int k;
/* 253 */         label103: for (k = -i; k <= i; k++) {
/*     */           
/* 255 */           for (int l = -i; l <= i; l++) {
/*     */             
/* 257 */             for (int i1 = -1; i1 <= 1; i1++) {
/*     */               
/* 259 */               if (worldIn.getBlockState(pos.add(k, i1, l)).getBlock() == this) {
/*     */                 
/* 261 */                 j--;
/*     */                 
/* 263 */                 if (j <= 0) {
/*     */                   
/* 265 */                   flag = true;
/*     */                   
/*     */                   break label103;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 273 */         EnumFacing enumfacing1 = EnumFacing.random(rand);
/* 274 */         BlockPos blockpos1 = pos.up();
/*     */         
/* 276 */         if (enumfacing1 == EnumFacing.UP && pos.getY() < 255 && worldIn.isAirBlock(blockpos1)) {
/*     */           
/* 278 */           if (!flag)
/*     */           {
/* 280 */             IBlockState iblockstate2 = state;
/*     */             
/* 282 */             for (EnumFacing enumfacing3 : EnumFacing.Plane.HORIZONTAL) {
/*     */               
/* 284 */               if (rand.nextBoolean() || !canPlaceOn(worldIn.getBlockState(blockpos1.offset(enumfacing3)).getBlock()))
/*     */               {
/* 286 */                 iblockstate2 = iblockstate2.withProperty((IProperty)getPropertyFor(enumfacing3), Boolean.valueOf(false));
/*     */               }
/*     */             } 
/*     */             
/* 290 */             if (((Boolean)iblockstate2.getValue((IProperty)NORTH)).booleanValue() || ((Boolean)iblockstate2.getValue((IProperty)EAST)).booleanValue() || ((Boolean)iblockstate2.getValue((IProperty)SOUTH)).booleanValue() || ((Boolean)iblockstate2.getValue((IProperty)WEST)).booleanValue())
/*     */             {
/* 292 */               worldIn.setBlockState(blockpos1, iblockstate2, 2);
/*     */             }
/*     */           }
/*     */         
/* 296 */         } else if (enumfacing1.getAxis().isHorizontal() && !((Boolean)state.getValue((IProperty)getPropertyFor(enumfacing1))).booleanValue()) {
/*     */           
/* 298 */           if (!flag)
/*     */           {
/* 300 */             BlockPos blockpos3 = pos.offset(enumfacing1);
/* 301 */             Block block1 = worldIn.getBlockState(blockpos3).getBlock();
/*     */             
/* 303 */             if (block1.blockMaterial == Material.air) {
/*     */               
/* 305 */               EnumFacing enumfacing2 = enumfacing1.rotateY();
/* 306 */               EnumFacing enumfacing4 = enumfacing1.rotateYCCW();
/* 307 */               boolean flag1 = ((Boolean)state.getValue((IProperty)getPropertyFor(enumfacing2))).booleanValue();
/* 308 */               boolean flag2 = ((Boolean)state.getValue((IProperty)getPropertyFor(enumfacing4))).booleanValue();
/* 309 */               BlockPos blockpos4 = blockpos3.offset(enumfacing2);
/* 310 */               BlockPos blockpos = blockpos3.offset(enumfacing4);
/*     */               
/* 312 */               if (flag1 && canPlaceOn(worldIn.getBlockState(blockpos4).getBlock()))
/*     */               {
/* 314 */                 worldIn.setBlockState(blockpos3, getDefaultState().withProperty((IProperty)getPropertyFor(enumfacing2), Boolean.valueOf(true)), 2);
/*     */               }
/* 316 */               else if (flag2 && canPlaceOn(worldIn.getBlockState(blockpos).getBlock()))
/*     */               {
/* 318 */                 worldIn.setBlockState(blockpos3, getDefaultState().withProperty((IProperty)getPropertyFor(enumfacing4), Boolean.valueOf(true)), 2);
/*     */               }
/* 320 */               else if (flag1 && worldIn.isAirBlock(blockpos4) && canPlaceOn(worldIn.getBlockState(pos.offset(enumfacing2)).getBlock()))
/*     */               {
/* 322 */                 worldIn.setBlockState(blockpos4, getDefaultState().withProperty((IProperty)getPropertyFor(enumfacing1.getOpposite()), Boolean.valueOf(true)), 2);
/*     */               }
/* 324 */               else if (flag2 && worldIn.isAirBlock(blockpos) && canPlaceOn(worldIn.getBlockState(pos.offset(enumfacing4)).getBlock()))
/*     */               {
/* 326 */                 worldIn.setBlockState(blockpos, getDefaultState().withProperty((IProperty)getPropertyFor(enumfacing1.getOpposite()), Boolean.valueOf(true)), 2);
/*     */               }
/* 328 */               else if (canPlaceOn(worldIn.getBlockState(blockpos3.up()).getBlock()))
/*     */               {
/* 330 */                 worldIn.setBlockState(blockpos3, getDefaultState(), 2);
/*     */               }
/*     */             
/* 333 */             } else if (block1.blockMaterial.isOpaque() && block1.isFullCube()) {
/*     */               
/* 335 */               worldIn.setBlockState(pos, state.withProperty((IProperty)getPropertyFor(enumfacing1), Boolean.valueOf(true)), 2);
/*     */             }
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 341 */         else if (pos.getY() > 1) {
/*     */           
/* 343 */           BlockPos blockpos2 = pos.down();
/* 344 */           IBlockState iblockstate = worldIn.getBlockState(blockpos2);
/* 345 */           Block block = iblockstate.getBlock();
/*     */           
/* 347 */           if (block.blockMaterial == Material.air) {
/*     */             
/* 349 */             IBlockState iblockstate1 = state;
/*     */             
/* 351 */             for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */               
/* 353 */               if (rand.nextBoolean())
/*     */               {
/* 355 */                 iblockstate1 = iblockstate1.withProperty((IProperty)getPropertyFor(enumfacing), Boolean.valueOf(false));
/*     */               }
/*     */             } 
/*     */             
/* 359 */             if (((Boolean)iblockstate1.getValue((IProperty)NORTH)).booleanValue() || ((Boolean)iblockstate1.getValue((IProperty)EAST)).booleanValue() || ((Boolean)iblockstate1.getValue((IProperty)SOUTH)).booleanValue() || ((Boolean)iblockstate1.getValue((IProperty)WEST)).booleanValue())
/*     */             {
/* 361 */               worldIn.setBlockState(blockpos2, iblockstate1, 2);
/*     */             }
/*     */           }
/* 364 */           else if (block == this) {
/*     */             
/* 366 */             IBlockState iblockstate3 = iblockstate;
/*     */             
/* 368 */             for (EnumFacing enumfacing5 : EnumFacing.Plane.HORIZONTAL) {
/*     */               
/* 370 */               PropertyBool propertybool = getPropertyFor(enumfacing5);
/*     */               
/* 372 */               if (rand.nextBoolean() && ((Boolean)state.getValue((IProperty)propertybool)).booleanValue())
/*     */               {
/* 374 */                 iblockstate3 = iblockstate3.withProperty((IProperty)propertybool, Boolean.valueOf(true));
/*     */               }
/*     */             } 
/*     */             
/* 378 */             if (((Boolean)iblockstate3.getValue((IProperty)NORTH)).booleanValue() || ((Boolean)iblockstate3.getValue((IProperty)EAST)).booleanValue() || ((Boolean)iblockstate3.getValue((IProperty)SOUTH)).booleanValue() || ((Boolean)iblockstate3.getValue((IProperty)WEST)).booleanValue())
/*     */             {
/* 380 */               worldIn.setBlockState(blockpos2, iblockstate3, 2);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 395 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)UP, Boolean.valueOf(false)).withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false));
/* 396 */     return facing.getAxis().isHorizontal() ? iblockstate.withProperty((IProperty)getPropertyFor(facing.getOpposite()), Boolean.valueOf(true)) : iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 404 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 412 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
/* 417 */     if (!worldIn.isRemote && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.shears) {
/*     */       
/* 419 */       player.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
/* 420 */       spawnAsEntity(worldIn, pos, new ItemStack(Blocks.vine, 1, 0));
/*     */     }
/*     */     else {
/*     */       
/* 424 */       super.harvestBlock(worldIn, player, pos, state, te);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 430 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 438 */     return getDefaultState().withProperty((IProperty)SOUTH, Boolean.valueOf(((meta & 0x1) > 0))).withProperty((IProperty)WEST, Boolean.valueOf(((meta & 0x2) > 0))).withProperty((IProperty)NORTH, Boolean.valueOf(((meta & 0x4) > 0))).withProperty((IProperty)EAST, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 446 */     int i = 0;
/*     */     
/* 448 */     if (((Boolean)state.getValue((IProperty)SOUTH)).booleanValue())
/*     */     {
/* 450 */       i |= 0x1;
/*     */     }
/*     */     
/* 453 */     if (((Boolean)state.getValue((IProperty)WEST)).booleanValue())
/*     */     {
/* 455 */       i |= 0x2;
/*     */     }
/*     */     
/* 458 */     if (((Boolean)state.getValue((IProperty)NORTH)).booleanValue())
/*     */     {
/* 460 */       i |= 0x4;
/*     */     }
/*     */     
/* 463 */     if (((Boolean)state.getValue((IProperty)EAST)).booleanValue())
/*     */     {
/* 465 */       i |= 0x8;
/*     */     }
/*     */     
/* 468 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 473 */     return new BlockState(this, new IProperty[] { (IProperty)UP, (IProperty)NORTH, (IProperty)EAST, (IProperty)SOUTH, (IProperty)WEST });
/*     */   }
/*     */ 
/*     */   
/*     */   public static PropertyBool getPropertyFor(EnumFacing side) {
/* 478 */     switch (side) {
/*     */       
/*     */       case UP:
/* 481 */         return UP;
/*     */       
/*     */       case NORTH:
/* 484 */         return NORTH;
/*     */       
/*     */       case SOUTH:
/* 487 */         return SOUTH;
/*     */       
/*     */       case EAST:
/* 490 */         return EAST;
/*     */       
/*     */       case WEST:
/* 493 */         return WEST;
/*     */     } 
/*     */     
/* 496 */     throw new IllegalArgumentException(side + " is an invalid choice");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getNumGrownFaces(IBlockState state) {
/* 502 */     int i = 0;
/*     */     
/* 504 */     for (PropertyBool propertybool : ALL_FACES) {
/*     */       
/* 506 */       if (((Boolean)state.getValue((IProperty)propertybool)).booleanValue())
/*     */       {
/* 508 */         i++;
/*     */       }
/*     */     } 
/*     */     
/* 512 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockVine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */