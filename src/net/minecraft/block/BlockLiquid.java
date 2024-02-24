/*     */ package net.minecraft.block;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeColorHelper;
/*     */ 
/*     */ public abstract class BlockLiquid extends Block {
/*  20 */   public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);
/*     */ 
/*     */   
/*     */   protected BlockLiquid(Material materialIn) {
/*  24 */     super(materialIn);
/*  25 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)LEVEL, Integer.valueOf(0)));
/*  26 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  27 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  32 */     return (this.blockMaterial != Material.lava);
/*     */   }
/*     */ 
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/*  37 */     return (this.blockMaterial == Material.water) ? BiomeColorHelper.getWaterColorAtPos(worldIn, pos) : 16777215;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getLiquidHeightPercent(int meta) {
/*  45 */     if (meta >= 8)
/*     */     {
/*  47 */       meta = 0;
/*     */     }
/*     */     
/*  50 */     return (meta + 1) / 9.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getLevel(IBlockAccess worldIn, BlockPos pos) {
/*  55 */     return (worldIn.getBlockState(pos).getBlock().getMaterial() == this.blockMaterial) ? ((Integer)worldIn.getBlockState(pos).getValue((IProperty)LEVEL)).intValue() : -1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getEffectiveFlowDecay(IBlockAccess worldIn, BlockPos pos) {
/*  60 */     int i = getLevel(worldIn, pos);
/*  61 */     return (i >= 8) ? 0 : i;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
/*  79 */     return (hitIfLiquid && ((Integer)state.getValue((IProperty)LEVEL)).intValue() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/*  87 */     Material material = worldIn.getBlockState(pos).getBlock().getMaterial();
/*  88 */     return (material == this.blockMaterial) ? false : ((side == EnumFacing.UP) ? true : ((material == Material.ice) ? false : super.isBlockSolid(worldIn, pos, side)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/*  93 */     return (worldIn.getBlockState(pos).getBlock().getMaterial() == this.blockMaterial) ? false : ((side == EnumFacing.UP) ? true : super.shouldSideBeRendered(worldIn, pos, side));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRenderSides(IBlockAccess blockAccess, BlockPos pos) {
/*  98 */     for (int i = -1; i <= 1; i++) {
/*     */       
/* 100 */       for (int j = -1; j <= 1; j++) {
/*     */         
/* 102 */         IBlockState iblockstate = blockAccess.getBlockState(pos.add(i, 0, j));
/* 103 */         Block block = iblockstate.getBlock();
/* 104 */         Material material = block.getMaterial();
/*     */         
/* 106 */         if (material != this.blockMaterial && !block.isFullBlock())
/*     */         {
/* 108 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 118 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/* 126 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 134 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 142 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vec3 getFlowVector(IBlockAccess worldIn, BlockPos pos) {
/* 147 */     Vec3 vec3 = new Vec3(0.0D, 0.0D, 0.0D);
/* 148 */     int i = getEffectiveFlowDecay(worldIn, pos);
/*     */     
/* 150 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 152 */       BlockPos blockpos = pos.offset(enumfacing);
/* 153 */       int j = getEffectiveFlowDecay(worldIn, blockpos);
/*     */       
/* 155 */       if (j < 0) {
/*     */         
/* 157 */         if (!worldIn.getBlockState(blockpos).getBlock().getMaterial().blocksMovement()) {
/*     */           
/* 159 */           j = getEffectiveFlowDecay(worldIn, blockpos.down());
/*     */           
/* 161 */           if (j >= 0) {
/*     */             
/* 163 */             int k = j - i - 8;
/* 164 */             vec3 = vec3.addVector(((blockpos.getX() - pos.getX()) * k), ((blockpos.getY() - pos.getY()) * k), ((blockpos.getZ() - pos.getZ()) * k));
/*     */           } 
/*     */         }  continue;
/*     */       } 
/* 168 */       if (j >= 0) {
/*     */         
/* 170 */         int l = j - i;
/* 171 */         vec3 = vec3.addVector(((blockpos.getX() - pos.getX()) * l), ((blockpos.getY() - pos.getY()) * l), ((blockpos.getZ() - pos.getZ()) * l));
/*     */       } 
/*     */     } 
/*     */     
/* 175 */     if (((Integer)worldIn.getBlockState(pos).getValue((IProperty)LEVEL)).intValue() >= 8)
/*     */     {
/* 177 */       for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL) {
/*     */         
/* 179 */         BlockPos blockpos1 = pos.offset(enumfacing1);
/*     */         
/* 181 */         if (isBlockSolid(worldIn, blockpos1, enumfacing1) || isBlockSolid(worldIn, blockpos1.up(), enumfacing1)) {
/*     */           
/* 183 */           vec3 = vec3.normalize().addVector(0.0D, -6.0D, 0.0D);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 189 */     return vec3.normalize();
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3 motion) {
/* 194 */     return motion.add(getFlowVector((IBlockAccess)worldIn, pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/* 202 */     return (this.blockMaterial == Material.water) ? 5 : ((this.blockMaterial == Material.lava) ? (worldIn.provider.getHasNoSky() ? 10 : 30) : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMixedBrightnessForBlock(IBlockAccess worldIn, BlockPos pos) {
/* 207 */     int i = worldIn.getCombinedLight(pos, 0);
/* 208 */     int j = worldIn.getCombinedLight(pos.up(), 0);
/* 209 */     int k = i & 0xFF;
/* 210 */     int l = j & 0xFF;
/* 211 */     int i1 = i >> 16 & 0xFF;
/* 212 */     int j1 = j >> 16 & 0xFF;
/* 213 */     return ((k > l) ? k : l) | ((i1 > j1) ? i1 : j1) << 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 218 */     return (this.blockMaterial == Material.water) ? EnumWorldBlockLayer.TRANSLUCENT : EnumWorldBlockLayer.SOLID;
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 223 */     double d0 = pos.getX();
/* 224 */     double d1 = pos.getY();
/* 225 */     double d2 = pos.getZ();
/*     */     
/* 227 */     if (this.blockMaterial == Material.water) {
/*     */       
/* 229 */       int i = ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/*     */       
/* 231 */       if (i > 0 && i < 8) {
/*     */         
/* 233 */         if (rand.nextInt(64) == 0)
/*     */         {
/* 235 */           worldIn.playSound(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D, "liquid.water", rand.nextFloat() * 0.25F + 0.75F, rand.nextFloat() + 0.5F, false);
/*     */         }
/*     */       }
/* 238 */       else if (rand.nextInt(10) == 0) {
/*     */         
/* 240 */         worldIn.spawnParticle(EnumParticleTypes.SUSPENDED, d0 + rand.nextFloat(), d1 + rand.nextFloat(), d2 + rand.nextFloat(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       } 
/*     */     } 
/*     */     
/* 244 */     if (this.blockMaterial == Material.lava && worldIn.getBlockState(pos.up()).getBlock().getMaterial() == Material.air && !worldIn.getBlockState(pos.up()).getBlock().isOpaqueCube()) {
/*     */       
/* 246 */       if (rand.nextInt(100) == 0) {
/*     */         
/* 248 */         double d8 = d0 + rand.nextFloat();
/* 249 */         double d4 = d1 + this.maxY;
/* 250 */         double d6 = d2 + rand.nextFloat();
/* 251 */         worldIn.spawnParticle(EnumParticleTypes.LAVA, d8, d4, d6, 0.0D, 0.0D, 0.0D, new int[0]);
/* 252 */         worldIn.playSound(d8, d4, d6, "liquid.lavapop", 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
/*     */       } 
/*     */       
/* 255 */       if (rand.nextInt(200) == 0)
/*     */       {
/* 257 */         worldIn.playSound(d0, d1, d2, "liquid.lava", 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
/*     */       }
/*     */     } 
/*     */     
/* 261 */     if (rand.nextInt(10) == 0 && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down())) {
/*     */       
/* 263 */       Material material = worldIn.getBlockState(pos.down(2)).getBlock().getMaterial();
/*     */       
/* 265 */       if (!material.blocksMovement() && !material.isLiquid()) {
/*     */         
/* 267 */         double d3 = d0 + rand.nextFloat();
/* 268 */         double d5 = d1 - 1.05D;
/* 269 */         double d7 = d2 + rand.nextFloat();
/*     */         
/* 271 */         if (this.blockMaterial == Material.water) {
/*     */           
/* 273 */           worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d3, d5, d7, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         }
/*     */         else {
/*     */           
/* 277 */           worldIn.spawnParticle(EnumParticleTypes.DRIP_LAVA, d3, d5, d7, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getFlowDirection(IBlockAccess worldIn, BlockPos pos, Material materialIn) {
/* 285 */     Vec3 vec3 = getFlowingBlock(materialIn).getFlowVector(worldIn, pos);
/* 286 */     return (vec3.xCoord == 0.0D && vec3.zCoord == 0.0D) ? -1000.0D : (MathHelper.atan2(vec3.zCoord, vec3.xCoord) - 1.5707963267948966D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 291 */     checkForMixing(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 299 */     checkForMixing(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkForMixing(World worldIn, BlockPos pos, IBlockState state) {
/* 304 */     if (this.blockMaterial == Material.lava) {
/*     */       
/* 306 */       boolean flag = false;
/*     */       
/* 308 */       for (EnumFacing enumfacing : EnumFacing.values()) {
/*     */         
/* 310 */         if (enumfacing != EnumFacing.DOWN && worldIn.getBlockState(pos.offset(enumfacing)).getBlock().getMaterial() == Material.water) {
/*     */           
/* 312 */           flag = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 317 */       if (flag) {
/*     */         
/* 319 */         Integer integer = (Integer)state.getValue((IProperty)LEVEL);
/*     */         
/* 321 */         if (integer.intValue() == 0) {
/*     */           
/* 323 */           worldIn.setBlockState(pos, Blocks.obsidian.getDefaultState());
/* 324 */           triggerMixEffects(worldIn, pos);
/* 325 */           return true;
/*     */         } 
/*     */         
/* 328 */         if (integer.intValue() <= 4) {
/*     */           
/* 330 */           worldIn.setBlockState(pos, Blocks.cobblestone.getDefaultState());
/* 331 */           triggerMixEffects(worldIn, pos);
/* 332 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 337 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void triggerMixEffects(World worldIn, BlockPos pos) {
/* 342 */     double d0 = pos.getX();
/* 343 */     double d1 = pos.getY();
/* 344 */     double d2 = pos.getZ();
/* 345 */     worldIn.playSoundEffect(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D, "random.fizz", 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);
/*     */     
/* 347 */     for (int i = 0; i < 8; i++)
/*     */     {
/* 349 */       worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0 + Math.random(), d1 + 1.2D, d2 + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 358 */     return getDefaultState().withProperty((IProperty)LEVEL, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 366 */     return ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 371 */     return new BlockState(this, new IProperty[] { (IProperty)LEVEL });
/*     */   }
/*     */ 
/*     */   
/*     */   public static BlockDynamicLiquid getFlowingBlock(Material materialIn) {
/* 376 */     if (materialIn == Material.water)
/*     */     {
/* 378 */       return Blocks.flowing_water;
/*     */     }
/* 380 */     if (materialIn == Material.lava)
/*     */     {
/* 382 */       return Blocks.flowing_lava;
/*     */     }
/*     */ 
/*     */     
/* 386 */     throw new IllegalArgumentException("Invalid material");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockStaticLiquid getStaticBlock(Material materialIn) {
/* 392 */     if (materialIn == Material.water)
/*     */     {
/* 394 */       return Blocks.water;
/*     */     }
/* 396 */     if (materialIn == Material.lava)
/*     */     {
/* 398 */       return Blocks.lava;
/*     */     }
/*     */ 
/*     */     
/* 402 */     throw new IllegalArgumentException("Invalid material");
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockLiquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */