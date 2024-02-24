/*     */ package net.minecraft.block;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockFire extends Block {
/*  22 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
/*  23 */   public static final PropertyBool FLIP = PropertyBool.create("flip");
/*  24 */   public static final PropertyBool ALT = PropertyBool.create("alt");
/*  25 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*  26 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*  27 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*  28 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*  29 */   public static final PropertyInteger UPPER = PropertyInteger.create("upper", 0, 2);
/*  30 */   private final Map<Block, Integer> encouragements = Maps.newIdentityHashMap();
/*  31 */   private final Map<Block, Integer> flammabilities = Maps.newIdentityHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  39 */     int i = pos.getX();
/*  40 */     int j = pos.getY();
/*  41 */     int k = pos.getZ();
/*     */     
/*  43 */     if (!World.doesBlockHaveSolidTopSurface(worldIn, pos.down()) && !Blocks.fire.canCatchFire(worldIn, pos.down())) {
/*     */       
/*  45 */       boolean flag = ((i + j + k & 0x1) == 1);
/*  46 */       boolean flag1 = ((i / 2 + j / 2 + k / 2 & 0x1) == 1);
/*  47 */       int l = 0;
/*     */       
/*  49 */       if (canCatchFire(worldIn, pos.up()))
/*     */       {
/*  51 */         l = flag ? 1 : 2;
/*     */       }
/*     */       
/*  54 */       return state.withProperty((IProperty)NORTH, Boolean.valueOf(canCatchFire(worldIn, pos.north()))).withProperty((IProperty)EAST, Boolean.valueOf(canCatchFire(worldIn, pos.east()))).withProperty((IProperty)SOUTH, Boolean.valueOf(canCatchFire(worldIn, pos.south()))).withProperty((IProperty)WEST, Boolean.valueOf(canCatchFire(worldIn, pos.west()))).withProperty((IProperty)UPPER, Integer.valueOf(l)).withProperty((IProperty)FLIP, Boolean.valueOf(flag1)).withProperty((IProperty)ALT, Boolean.valueOf(flag));
/*     */     } 
/*     */ 
/*     */     
/*  58 */     return getDefaultState();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BlockFire() {
/*  64 */     super(Material.fire);
/*  65 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AGE, Integer.valueOf(0)).withProperty((IProperty)FLIP, Boolean.valueOf(false)).withProperty((IProperty)ALT, Boolean.valueOf(false)).withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false)).withProperty((IProperty)UPPER, Integer.valueOf(0)));
/*  66 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init() {
/*  71 */     Blocks.fire.setFireInfo(Blocks.planks, 5, 20);
/*  72 */     Blocks.fire.setFireInfo(Blocks.double_wooden_slab, 5, 20);
/*  73 */     Blocks.fire.setFireInfo(Blocks.wooden_slab, 5, 20);
/*  74 */     Blocks.fire.setFireInfo(Blocks.oak_fence_gate, 5, 20);
/*  75 */     Blocks.fire.setFireInfo(Blocks.spruce_fence_gate, 5, 20);
/*  76 */     Blocks.fire.setFireInfo(Blocks.birch_fence_gate, 5, 20);
/*  77 */     Blocks.fire.setFireInfo(Blocks.jungle_fence_gate, 5, 20);
/*  78 */     Blocks.fire.setFireInfo(Blocks.dark_oak_fence_gate, 5, 20);
/*  79 */     Blocks.fire.setFireInfo(Blocks.acacia_fence_gate, 5, 20);
/*  80 */     Blocks.fire.setFireInfo(Blocks.oak_fence, 5, 20);
/*  81 */     Blocks.fire.setFireInfo(Blocks.spruce_fence, 5, 20);
/*  82 */     Blocks.fire.setFireInfo(Blocks.birch_fence, 5, 20);
/*  83 */     Blocks.fire.setFireInfo(Blocks.jungle_fence, 5, 20);
/*  84 */     Blocks.fire.setFireInfo(Blocks.dark_oak_fence, 5, 20);
/*  85 */     Blocks.fire.setFireInfo(Blocks.acacia_fence, 5, 20);
/*  86 */     Blocks.fire.setFireInfo(Blocks.oak_stairs, 5, 20);
/*  87 */     Blocks.fire.setFireInfo(Blocks.birch_stairs, 5, 20);
/*  88 */     Blocks.fire.setFireInfo(Blocks.spruce_stairs, 5, 20);
/*  89 */     Blocks.fire.setFireInfo(Blocks.jungle_stairs, 5, 20);
/*  90 */     Blocks.fire.setFireInfo(Blocks.log, 5, 5);
/*  91 */     Blocks.fire.setFireInfo(Blocks.log2, 5, 5);
/*  92 */     Blocks.fire.setFireInfo(Blocks.leaves, 30, 60);
/*  93 */     Blocks.fire.setFireInfo(Blocks.leaves2, 30, 60);
/*  94 */     Blocks.fire.setFireInfo(Blocks.bookshelf, 30, 20);
/*  95 */     Blocks.fire.setFireInfo(Blocks.tnt, 15, 100);
/*  96 */     Blocks.fire.setFireInfo(Blocks.tallgrass, 60, 100);
/*  97 */     Blocks.fire.setFireInfo(Blocks.double_plant, 60, 100);
/*  98 */     Blocks.fire.setFireInfo(Blocks.yellow_flower, 60, 100);
/*  99 */     Blocks.fire.setFireInfo(Blocks.red_flower, 60, 100);
/* 100 */     Blocks.fire.setFireInfo(Blocks.deadbush, 60, 100);
/* 101 */     Blocks.fire.setFireInfo(Blocks.wool, 30, 60);
/* 102 */     Blocks.fire.setFireInfo(Blocks.vine, 15, 100);
/* 103 */     Blocks.fire.setFireInfo(Blocks.coal_block, 5, 5);
/* 104 */     Blocks.fire.setFireInfo(Blocks.hay_block, 60, 20);
/* 105 */     Blocks.fire.setFireInfo(Blocks.carpet, 60, 20);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFireInfo(Block blockIn, int encouragement, int flammability) {
/* 110 */     this.encouragements.put(blockIn, Integer.valueOf(encouragement));
/* 111 */     this.flammabilities.put(blockIn, Integer.valueOf(flammability));
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 116 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/* 129 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 137 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/* 145 */     return 30;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 150 */     if (worldIn.getGameRules().getBoolean("doFireTick")) {
/*     */       
/* 152 */       if (!canPlaceBlockAt(worldIn, pos))
/*     */       {
/* 154 */         worldIn.setBlockToAir(pos);
/*     */       }
/*     */       
/* 157 */       Block block = worldIn.getBlockState(pos.down()).getBlock();
/* 158 */       boolean flag = (block == Blocks.netherrack);
/*     */       
/* 160 */       if (worldIn.provider instanceof net.minecraft.world.WorldProviderEnd && block == Blocks.bedrock)
/*     */       {
/* 162 */         flag = true;
/*     */       }
/*     */       
/* 165 */       if (!flag && worldIn.isRaining() && canDie(worldIn, pos)) {
/*     */         
/* 167 */         worldIn.setBlockToAir(pos);
/*     */       }
/*     */       else {
/*     */         
/* 171 */         int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */         
/* 173 */         if (i < 15) {
/*     */           
/* 175 */           state = state.withProperty((IProperty)AGE, Integer.valueOf(i + rand.nextInt(3) / 2));
/* 176 */           worldIn.setBlockState(pos, state, 4);
/*     */         } 
/*     */         
/* 179 */         worldIn.scheduleUpdate(pos, this, tickRate(worldIn) + rand.nextInt(10));
/*     */         
/* 181 */         if (!flag) {
/*     */           
/* 183 */           if (!canNeighborCatchFire(worldIn, pos)) {
/*     */             
/* 185 */             if (!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) || i > 3)
/*     */             {
/* 187 */               worldIn.setBlockToAir(pos);
/*     */             }
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 193 */           if (!canCatchFire((IBlockAccess)worldIn, pos.down()) && i == 15 && rand.nextInt(4) == 0) {
/*     */             
/* 195 */             worldIn.setBlockToAir(pos);
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/* 200 */         boolean flag1 = worldIn.isBlockinHighHumidity(pos);
/* 201 */         int j = 0;
/*     */         
/* 203 */         if (flag1)
/*     */         {
/* 205 */           j = -50;
/*     */         }
/*     */         
/* 208 */         catchOnFire(worldIn, pos.east(), 300 + j, rand, i);
/* 209 */         catchOnFire(worldIn, pos.west(), 300 + j, rand, i);
/* 210 */         catchOnFire(worldIn, pos.down(), 250 + j, rand, i);
/* 211 */         catchOnFire(worldIn, pos.up(), 250 + j, rand, i);
/* 212 */         catchOnFire(worldIn, pos.north(), 300 + j, rand, i);
/* 213 */         catchOnFire(worldIn, pos.south(), 300 + j, rand, i);
/*     */         
/* 215 */         for (int k = -1; k <= 1; k++) {
/*     */           
/* 217 */           for (int l = -1; l <= 1; l++) {
/*     */             
/* 219 */             for (int i1 = -1; i1 <= 4; i1++) {
/*     */               
/* 221 */               if (k != 0 || i1 != 0 || l != 0) {
/*     */                 
/* 223 */                 int j1 = 100;
/*     */                 
/* 225 */                 if (i1 > 1)
/*     */                 {
/* 227 */                   j1 += (i1 - 1) * 100;
/*     */                 }
/*     */                 
/* 230 */                 BlockPos blockpos = pos.add(k, i1, l);
/* 231 */                 int k1 = getNeighborEncouragement(worldIn, blockpos);
/*     */                 
/* 233 */                 if (k1 > 0) {
/*     */                   
/* 235 */                   int l1 = (k1 + 40 + worldIn.getDifficulty().getDifficultyId() * 7) / (i + 30);
/*     */                   
/* 237 */                   if (flag1)
/*     */                   {
/* 239 */                     l1 /= 2;
/*     */                   }
/*     */                   
/* 242 */                   if (l1 > 0 && rand.nextInt(j1) <= l1 && (!worldIn.isRaining() || !canDie(worldIn, blockpos))) {
/*     */                     
/* 244 */                     int i2 = i + rand.nextInt(5) / 4;
/*     */                     
/* 246 */                     if (i2 > 15)
/*     */                     {
/* 248 */                       i2 = 15;
/*     */                     }
/*     */                     
/* 251 */                     worldIn.setBlockState(blockpos, state.withProperty((IProperty)AGE, Integer.valueOf(i2)), 3);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canDie(World worldIn, BlockPos pos) {
/* 264 */     return (worldIn.isRainingAt(pos) || worldIn.isRainingAt(pos.west()) || worldIn.isRainingAt(pos.east()) || worldIn.isRainingAt(pos.north()) || worldIn.isRainingAt(pos.south()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresUpdates() {
/* 269 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getFlammability(Block blockIn) {
/* 274 */     Integer integer = this.flammabilities.get(blockIn);
/* 275 */     return (integer == null) ? 0 : integer.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   private int getEncouragement(Block blockIn) {
/* 280 */     Integer integer = this.encouragements.get(blockIn);
/* 281 */     return (integer == null) ? 0 : integer.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   private void catchOnFire(World worldIn, BlockPos pos, int chance, Random random, int age) {
/* 286 */     int i = getFlammability(worldIn.getBlockState(pos).getBlock());
/*     */     
/* 288 */     if (random.nextInt(chance) < i) {
/*     */       
/* 290 */       IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */       
/* 292 */       if (random.nextInt(age + 10) < 5 && !worldIn.isRainingAt(pos)) {
/*     */         
/* 294 */         int j = age + random.nextInt(5) / 4;
/*     */         
/* 296 */         if (j > 15)
/*     */         {
/* 298 */           j = 15;
/*     */         }
/*     */         
/* 301 */         worldIn.setBlockState(pos, getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(j)), 3);
/*     */       }
/*     */       else {
/*     */         
/* 305 */         worldIn.setBlockToAir(pos);
/*     */       } 
/*     */       
/* 308 */       if (iblockstate.getBlock() == Blocks.tnt)
/*     */       {
/* 310 */         Blocks.tnt.onBlockDestroyedByPlayer(worldIn, pos, iblockstate.withProperty((IProperty)BlockTNT.EXPLODE, Boolean.valueOf(true)));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canNeighborCatchFire(World worldIn, BlockPos pos) {
/* 317 */     for (EnumFacing enumfacing : EnumFacing.values()) {
/*     */       
/* 319 */       if (canCatchFire((IBlockAccess)worldIn, pos.offset(enumfacing)))
/*     */       {
/* 321 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 325 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getNeighborEncouragement(World worldIn, BlockPos pos) {
/* 330 */     if (!worldIn.isAirBlock(pos))
/*     */     {
/* 332 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 336 */     int i = 0;
/*     */     
/* 338 */     for (EnumFacing enumfacing : EnumFacing.values())
/*     */     {
/* 340 */       i = Math.max(getEncouragement(worldIn.getBlockState(pos.offset(enumfacing)).getBlock()), i);
/*     */     }
/*     */     
/* 343 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCollidable() {
/* 352 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCatchFire(IBlockAccess worldIn, BlockPos pos) {
/* 360 */     return (getEncouragement(worldIn.getBlockState(pos).getBlock()) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 365 */     return (World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) || canNeighborCatchFire(worldIn, pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 373 */     if (!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) && !canNeighborCatchFire(worldIn, pos))
/*     */     {
/* 375 */       worldIn.setBlockToAir(pos);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 381 */     if (worldIn.provider.getDimensionId() > 0 || !Blocks.portal.func_176548_d(worldIn, pos))
/*     */     {
/* 383 */       if (!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) && !canNeighborCatchFire(worldIn, pos)) {
/*     */         
/* 385 */         worldIn.setBlockToAir(pos);
/*     */       }
/*     */       else {
/*     */         
/* 389 */         worldIn.scheduleUpdate(pos, this, tickRate(worldIn) + worldIn.rand.nextInt(10));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 396 */     if (rand.nextInt(24) == 0)
/*     */     {
/* 398 */       worldIn.playSound((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F), "fire.fire", 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
/*     */     }
/*     */     
/* 401 */     if (!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) && !Blocks.fire.canCatchFire((IBlockAccess)worldIn, pos.down())) {
/*     */       
/* 403 */       if (Blocks.fire.canCatchFire((IBlockAccess)worldIn, pos.west()))
/*     */       {
/* 405 */         for (int j = 0; j < 2; j++) {
/*     */           
/* 407 */           double d3 = pos.getX() + rand.nextDouble() * 0.10000000149011612D;
/* 408 */           double d8 = pos.getY() + rand.nextDouble();
/* 409 */           double d13 = pos.getZ() + rand.nextDouble();
/* 410 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d3, d8, d13, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         } 
/*     */       }
/*     */       
/* 414 */       if (Blocks.fire.canCatchFire((IBlockAccess)worldIn, pos.east()))
/*     */       {
/* 416 */         for (int k = 0; k < 2; k++) {
/*     */           
/* 418 */           double d4 = (pos.getX() + 1) - rand.nextDouble() * 0.10000000149011612D;
/* 419 */           double d9 = pos.getY() + rand.nextDouble();
/* 420 */           double d14 = pos.getZ() + rand.nextDouble();
/* 421 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d4, d9, d14, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         } 
/*     */       }
/*     */       
/* 425 */       if (Blocks.fire.canCatchFire((IBlockAccess)worldIn, pos.north()))
/*     */       {
/* 427 */         for (int l = 0; l < 2; l++) {
/*     */           
/* 429 */           double d5 = pos.getX() + rand.nextDouble();
/* 430 */           double d10 = pos.getY() + rand.nextDouble();
/* 431 */           double d15 = pos.getZ() + rand.nextDouble() * 0.10000000149011612D;
/* 432 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d5, d10, d15, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         } 
/*     */       }
/*     */       
/* 436 */       if (Blocks.fire.canCatchFire((IBlockAccess)worldIn, pos.south()))
/*     */       {
/* 438 */         for (int i1 = 0; i1 < 2; i1++) {
/*     */           
/* 440 */           double d6 = pos.getX() + rand.nextDouble();
/* 441 */           double d11 = pos.getY() + rand.nextDouble();
/* 442 */           double d16 = (pos.getZ() + 1) - rand.nextDouble() * 0.10000000149011612D;
/* 443 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d6, d11, d16, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         } 
/*     */       }
/*     */       
/* 447 */       if (Blocks.fire.canCatchFire((IBlockAccess)worldIn, pos.up()))
/*     */       {
/* 449 */         for (int j1 = 0; j1 < 2; j1++)
/*     */         {
/* 451 */           double d7 = pos.getX() + rand.nextDouble();
/* 452 */           double d12 = (pos.getY() + 1) - rand.nextDouble() * 0.10000000149011612D;
/* 453 */           double d17 = pos.getZ() + rand.nextDouble();
/* 454 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d7, d12, d17, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         }
/*     */       
/*     */       }
/*     */     } else {
/*     */       
/* 460 */       for (int i = 0; i < 3; i++) {
/*     */         
/* 462 */         double d0 = pos.getX() + rand.nextDouble();
/* 463 */         double d1 = pos.getY() + rand.nextDouble() * 0.5D + 0.5D;
/* 464 */         double d2 = pos.getZ() + rand.nextDouble();
/* 465 */         worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state) {
/* 475 */     return MapColor.tntColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 480 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 488 */     return getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 496 */     return ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 501 */     return new BlockState(this, new IProperty[] { (IProperty)AGE, (IProperty)NORTH, (IProperty)EAST, (IProperty)SOUTH, (IProperty)WEST, (IProperty)UPPER, (IProperty)FLIP, (IProperty)ALT });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockFire.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */