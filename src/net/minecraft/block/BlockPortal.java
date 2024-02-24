/*     */ package net.minecraft.block;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.BlockWorldState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.block.state.pattern.BlockPattern;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPortal extends BlockBreakable {
/*  23 */   public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class, (Enum[])new EnumFacing.Axis[] { EnumFacing.Axis.X, EnumFacing.Axis.Z });
/*     */ 
/*     */   
/*     */   public BlockPortal() {
/*  27 */     super(Material.portal, false);
/*  28 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AXIS, (Comparable)EnumFacing.Axis.X));
/*  29 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  34 */     super.updateTick(worldIn, pos, state, rand);
/*     */     
/*  36 */     if (worldIn.provider.isSurfaceWorld() && worldIn.getGameRules().getBoolean("doMobSpawning") && rand.nextInt(2000) < worldIn.getDifficulty().getDifficultyId()) {
/*     */       
/*  38 */       int i = pos.getY();
/*     */       
/*     */       BlockPos blockpos;
/*  41 */       for (blockpos = pos; !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, blockpos) && blockpos.getY() > 0; blockpos = blockpos.down());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  46 */       if (i > 0 && !worldIn.getBlockState(blockpos.up()).getBlock().isNormalCube()) {
/*     */         
/*  48 */         Entity entity = ItemMonsterPlacer.spawnCreature(worldIn, 57, blockpos.getX() + 0.5D, blockpos.getY() + 1.1D, blockpos.getZ() + 0.5D);
/*     */         
/*  50 */         if (entity != null)
/*     */         {
/*  52 */           entity.timeUntilPortal = entity.getPortalCooldown();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  60 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  65 */     EnumFacing.Axis enumfacing$axis = (EnumFacing.Axis)worldIn.getBlockState(pos).getValue((IProperty)AXIS);
/*  66 */     float f = 0.125F;
/*  67 */     float f1 = 0.125F;
/*     */     
/*  69 */     if (enumfacing$axis == EnumFacing.Axis.X)
/*     */     {
/*  71 */       f = 0.5F;
/*     */     }
/*     */     
/*  74 */     if (enumfacing$axis == EnumFacing.Axis.Z)
/*     */     {
/*  76 */       f1 = 0.5F;
/*     */     }
/*     */     
/*  79 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getMetaForAxis(EnumFacing.Axis axis) {
/*  84 */     return (axis == EnumFacing.Axis.X) ? 1 : ((axis == EnumFacing.Axis.Z) ? 2 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_176548_d(World worldIn, BlockPos p_176548_2_) {
/*  94 */     Size blockportal$size = new Size(worldIn, p_176548_2_, EnumFacing.Axis.X);
/*     */     
/*  96 */     if (blockportal$size.func_150860_b() && blockportal$size.field_150864_e == 0) {
/*     */       
/*  98 */       blockportal$size.func_150859_c();
/*  99 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 103 */     Size blockportal$size1 = new Size(worldIn, p_176548_2_, EnumFacing.Axis.Z);
/*     */     
/* 105 */     if (blockportal$size1.func_150860_b() && blockportal$size1.field_150864_e == 0) {
/*     */       
/* 107 */       blockportal$size1.func_150859_c();
/* 108 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 112 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 122 */     EnumFacing.Axis enumfacing$axis = (EnumFacing.Axis)state.getValue((IProperty)AXIS);
/*     */     
/* 124 */     if (enumfacing$axis == EnumFacing.Axis.X) {
/*     */       
/* 126 */       Size blockportal$size = new Size(worldIn, pos, EnumFacing.Axis.X);
/*     */       
/* 128 */       if (!blockportal$size.func_150860_b() || blockportal$size.field_150864_e < blockportal$size.field_150868_h * blockportal$size.field_150862_g)
/*     */       {
/* 130 */         worldIn.setBlockState(pos, Blocks.air.getDefaultState());
/*     */       }
/*     */     }
/* 133 */     else if (enumfacing$axis == EnumFacing.Axis.Z) {
/*     */       
/* 135 */       Size blockportal$size1 = new Size(worldIn, pos, EnumFacing.Axis.Z);
/*     */       
/* 137 */       if (!blockportal$size1.func_150860_b() || blockportal$size1.field_150864_e < blockportal$size1.field_150868_h * blockportal$size1.field_150862_g)
/*     */       {
/* 139 */         worldIn.setBlockState(pos, Blocks.air.getDefaultState());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 146 */     EnumFacing.Axis enumfacing$axis = null;
/* 147 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/* 149 */     if (worldIn.getBlockState(pos).getBlock() == this) {
/*     */       
/* 151 */       enumfacing$axis = (EnumFacing.Axis)iblockstate.getValue((IProperty)AXIS);
/*     */       
/* 153 */       if (enumfacing$axis == null)
/*     */       {
/* 155 */         return false;
/*     */       }
/*     */       
/* 158 */       if (enumfacing$axis == EnumFacing.Axis.Z && side != EnumFacing.EAST && side != EnumFacing.WEST)
/*     */       {
/* 160 */         return false;
/*     */       }
/*     */       
/* 163 */       if (enumfacing$axis == EnumFacing.Axis.X && side != EnumFacing.SOUTH && side != EnumFacing.NORTH)
/*     */       {
/* 165 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 169 */     boolean flag = (worldIn.getBlockState(pos.west()).getBlock() == this && worldIn.getBlockState(pos.west(2)).getBlock() != this);
/* 170 */     boolean flag1 = (worldIn.getBlockState(pos.east()).getBlock() == this && worldIn.getBlockState(pos.east(2)).getBlock() != this);
/* 171 */     boolean flag2 = (worldIn.getBlockState(pos.north()).getBlock() == this && worldIn.getBlockState(pos.north(2)).getBlock() != this);
/* 172 */     boolean flag3 = (worldIn.getBlockState(pos.south()).getBlock() == this && worldIn.getBlockState(pos.south(2)).getBlock() != this);
/* 173 */     boolean flag4 = (flag || flag1 || enumfacing$axis == EnumFacing.Axis.X);
/* 174 */     boolean flag5 = (flag2 || flag3 || enumfacing$axis == EnumFacing.Axis.Z);
/* 175 */     return (flag4 && side == EnumFacing.WEST) ? true : ((flag4 && side == EnumFacing.EAST) ? true : ((flag5 && side == EnumFacing.NORTH) ? true : ((flag5 && side == EnumFacing.SOUTH))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 183 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 188 */     return EnumWorldBlockLayer.TRANSLUCENT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 196 */     if (entityIn.ridingEntity == null && entityIn.riddenByEntity == null)
/*     */     {
/* 198 */       entityIn.setPortal(pos);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 204 */     if (rand.nextInt(100) == 0)
/*     */     {
/* 206 */       worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "portal.portal", 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
/*     */     }
/*     */     
/* 209 */     for (int i = 0; i < 4; i++) {
/*     */       
/* 211 */       double d0 = (pos.getX() + rand.nextFloat());
/* 212 */       double d1 = (pos.getY() + rand.nextFloat());
/* 213 */       double d2 = (pos.getZ() + rand.nextFloat());
/* 214 */       double d3 = (rand.nextFloat() - 0.5D) * 0.5D;
/* 215 */       double d4 = (rand.nextFloat() - 0.5D) * 0.5D;
/* 216 */       double d5 = (rand.nextFloat() - 0.5D) * 0.5D;
/* 217 */       int j = (rand.nextInt(2) << 1) - 1;
/*     */       
/* 219 */       if (worldIn.getBlockState(pos.west()).getBlock() != this && worldIn.getBlockState(pos.east()).getBlock() != this) {
/*     */         
/* 221 */         d0 = pos.getX() + 0.5D + 0.25D * j;
/* 222 */         d3 = (rand.nextFloat() * 2.0F * j);
/*     */       }
/*     */       else {
/*     */         
/* 226 */         d2 = pos.getZ() + 0.5D + 0.25D * j;
/* 227 */         d5 = (rand.nextFloat() * 2.0F * j);
/*     */       } 
/*     */       
/* 230 */       worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5, new int[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 236 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 244 */     return getDefaultState().withProperty((IProperty)AXIS, ((meta & 0x3) == 2) ? (Comparable)EnumFacing.Axis.Z : (Comparable)EnumFacing.Axis.X);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 252 */     return getMetaForAxis((EnumFacing.Axis)state.getValue((IProperty)AXIS));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 257 */     return new BlockState(this, new IProperty[] { (IProperty)AXIS });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPattern.PatternHelper func_181089_f(World p_181089_1_, BlockPos p_181089_2_) {
/* 262 */     EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.Z;
/* 263 */     Size blockportal$size = new Size(p_181089_1_, p_181089_2_, EnumFacing.Axis.X);
/* 264 */     LoadingCache<BlockPos, BlockWorldState> loadingcache = BlockPattern.func_181627_a(p_181089_1_, true);
/*     */     
/* 266 */     if (!blockportal$size.func_150860_b()) {
/*     */       
/* 268 */       enumfacing$axis = EnumFacing.Axis.X;
/* 269 */       blockportal$size = new Size(p_181089_1_, p_181089_2_, EnumFacing.Axis.Z);
/*     */     } 
/*     */     
/* 272 */     if (!blockportal$size.func_150860_b())
/*     */     {
/* 274 */       return new BlockPattern.PatternHelper(p_181089_2_, EnumFacing.NORTH, EnumFacing.UP, loadingcache, 1, 1, 1);
/*     */     }
/*     */ 
/*     */     
/* 278 */     int[] aint = new int[(EnumFacing.AxisDirection.values()).length];
/* 279 */     EnumFacing enumfacing = blockportal$size.field_150866_c.rotateYCCW();
/* 280 */     BlockPos blockpos = blockportal$size.field_150861_f.up(blockportal$size.func_181100_a() - 1);
/*     */     
/* 282 */     for (EnumFacing.AxisDirection enumfacing$axisdirection : EnumFacing.AxisDirection.values()) {
/*     */       
/* 284 */       BlockPattern.PatternHelper blockpattern$patternhelper = new BlockPattern.PatternHelper((enumfacing.getAxisDirection() == enumfacing$axisdirection) ? blockpos : blockpos.offset(blockportal$size.field_150866_c, blockportal$size.func_181101_b() - 1), EnumFacing.getFacingFromAxis(enumfacing$axisdirection, enumfacing$axis), EnumFacing.UP, loadingcache, blockportal$size.func_181101_b(), blockportal$size.func_181100_a(), 1);
/*     */       
/* 286 */       for (int i = 0; i < blockportal$size.func_181101_b(); i++) {
/*     */         
/* 288 */         for (int j = 0; j < blockportal$size.func_181100_a(); j++) {
/*     */           
/* 290 */           BlockWorldState blockworldstate = blockpattern$patternhelper.translateOffset(i, j, 1);
/*     */           
/* 292 */           if (blockworldstate.getBlockState() != null && blockworldstate.getBlockState().getBlock().getMaterial() != Material.air)
/*     */           {
/* 294 */             aint[enumfacing$axisdirection.ordinal()] = aint[enumfacing$axisdirection.ordinal()] + 1;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 300 */     EnumFacing.AxisDirection enumfacing$axisdirection1 = EnumFacing.AxisDirection.POSITIVE;
/*     */     
/* 302 */     for (EnumFacing.AxisDirection enumfacing$axisdirection2 : EnumFacing.AxisDirection.values()) {
/*     */       
/* 304 */       if (aint[enumfacing$axisdirection2.ordinal()] < aint[enumfacing$axisdirection1.ordinal()])
/*     */       {
/* 306 */         enumfacing$axisdirection1 = enumfacing$axisdirection2;
/*     */       }
/*     */     } 
/*     */     
/* 310 */     return new BlockPattern.PatternHelper((enumfacing.getAxisDirection() == enumfacing$axisdirection1) ? blockpos : blockpos.offset(blockportal$size.field_150866_c, blockportal$size.func_181101_b() - 1), EnumFacing.getFacingFromAxis(enumfacing$axisdirection1, enumfacing$axis), EnumFacing.UP, loadingcache, blockportal$size.func_181101_b(), blockportal$size.func_181100_a(), 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Size
/*     */   {
/*     */     private final World world;
/*     */     private final EnumFacing.Axis axis;
/*     */     final EnumFacing field_150866_c;
/*     */     private final EnumFacing field_150863_d;
/* 320 */     int field_150864_e = 0;
/*     */     
/*     */     BlockPos field_150861_f;
/*     */     int field_150862_g;
/*     */     int field_150868_h;
/*     */     
/*     */     public Size(World worldIn, BlockPos p_i45694_2_, EnumFacing.Axis p_i45694_3_) {
/* 327 */       this.world = worldIn;
/* 328 */       this.axis = p_i45694_3_;
/*     */       
/* 330 */       if (p_i45694_3_ == EnumFacing.Axis.X) {
/*     */         
/* 332 */         this.field_150863_d = EnumFacing.EAST;
/* 333 */         this.field_150866_c = EnumFacing.WEST;
/*     */       }
/*     */       else {
/*     */         
/* 337 */         this.field_150863_d = EnumFacing.NORTH;
/* 338 */         this.field_150866_c = EnumFacing.SOUTH;
/*     */       } 
/*     */       
/* 341 */       for (BlockPos blockpos = p_i45694_2_; p_i45694_2_.getY() > blockpos.getY() - 21 && p_i45694_2_.getY() > 0 && func_150857_a(worldIn.getBlockState(p_i45694_2_.down()).getBlock()); p_i45694_2_ = p_i45694_2_.down());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 346 */       int i = func_180120_a(p_i45694_2_, this.field_150863_d) - 1;
/*     */       
/* 348 */       if (i >= 0) {
/*     */         
/* 350 */         this.field_150861_f = p_i45694_2_.offset(this.field_150863_d, i);
/* 351 */         this.field_150868_h = func_180120_a(this.field_150861_f, this.field_150866_c);
/*     */         
/* 353 */         if (this.field_150868_h < 2 || this.field_150868_h > 21) {
/*     */           
/* 355 */           this.field_150861_f = null;
/* 356 */           this.field_150868_h = 0;
/*     */         } 
/*     */       } 
/*     */       
/* 360 */       if (this.field_150861_f != null)
/*     */       {
/* 362 */         this.field_150862_g = func_150858_a();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected int func_180120_a(BlockPos p_180120_1_, EnumFacing p_180120_2_) {
/*     */       int i;
/* 370 */       for (i = 0; i < 22; i++) {
/*     */         
/* 372 */         BlockPos blockpos = p_180120_1_.offset(p_180120_2_, i);
/*     */         
/* 374 */         if (!func_150857_a(this.world.getBlockState(blockpos).getBlock()) || this.world.getBlockState(blockpos.down()).getBlock() != Blocks.obsidian) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 380 */       Block block = this.world.getBlockState(p_180120_1_.offset(p_180120_2_, i)).getBlock();
/* 381 */       return (block == Blocks.obsidian) ? i : 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_181100_a() {
/* 386 */       return this.field_150862_g;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_181101_b() {
/* 391 */       return this.field_150868_h;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected int func_150858_a() {
/* 398 */       label38: for (this.field_150862_g = 0; this.field_150862_g < 21; this.field_150862_g++) {
/*     */         
/* 400 */         for (int i = 0; i < this.field_150868_h; i++) {
/*     */           
/* 402 */           BlockPos blockpos = this.field_150861_f.offset(this.field_150866_c, i).up(this.field_150862_g);
/* 403 */           Block block = this.world.getBlockState(blockpos).getBlock();
/*     */           
/* 405 */           if (!func_150857_a(block)) {
/*     */             break label38;
/*     */           }
/*     */ 
/*     */           
/* 410 */           if (block == Blocks.portal)
/*     */           {
/* 412 */             this.field_150864_e++;
/*     */           }
/*     */           
/* 415 */           if (i == 0) {
/*     */             
/* 417 */             block = this.world.getBlockState(blockpos.offset(this.field_150863_d)).getBlock();
/*     */             
/* 419 */             if (block != Blocks.obsidian)
/*     */             {
/*     */               break label38;
/*     */             }
/*     */           }
/* 424 */           else if (i == this.field_150868_h - 1) {
/*     */             
/* 426 */             block = this.world.getBlockState(blockpos.offset(this.field_150866_c)).getBlock();
/*     */             
/* 428 */             if (block != Blocks.obsidian) {
/*     */               break label38;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 436 */       for (int j = 0; j < this.field_150868_h; j++) {
/*     */         
/* 438 */         if (this.world.getBlockState(this.field_150861_f.offset(this.field_150866_c, j).up(this.field_150862_g)).getBlock() != Blocks.obsidian) {
/*     */           
/* 440 */           this.field_150862_g = 0;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 445 */       if (this.field_150862_g <= 21 && this.field_150862_g >= 3)
/*     */       {
/* 447 */         return this.field_150862_g;
/*     */       }
/*     */ 
/*     */       
/* 451 */       this.field_150861_f = null;
/* 452 */       this.field_150868_h = 0;
/* 453 */       this.field_150862_g = 0;
/* 454 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean func_150857_a(Block p_150857_1_) {
/* 460 */       return (p_150857_1_.blockMaterial == Material.air || p_150857_1_ == Blocks.fire || p_150857_1_ == Blocks.portal);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_150860_b() {
/* 465 */       return (this.field_150861_f != null && this.field_150868_h >= 2 && this.field_150868_h <= 21 && this.field_150862_g >= 3 && this.field_150862_g <= 21);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_150859_c() {
/* 470 */       for (int i = 0; i < this.field_150868_h; i++) {
/*     */         
/* 472 */         BlockPos blockpos = this.field_150861_f.offset(this.field_150866_c, i);
/*     */         
/* 474 */         for (int j = 0; j < this.field_150862_g; j++)
/*     */         {
/* 476 */           this.world.setBlockState(blockpos.up(j), Blocks.portal.getDefaultState().withProperty((IProperty)BlockPortal.AXIS, (Comparable)this.axis), 2);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockPortal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */