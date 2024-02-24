/*     */ package net.minecraft.block;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockStairs extends Block {
/*  25 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*  26 */   public static final PropertyEnum<EnumHalf> HALF = PropertyEnum.create("half", EnumHalf.class);
/*  27 */   public static final PropertyEnum<EnumShape> SHAPE = PropertyEnum.create("shape", EnumShape.class);
/*  28 */   private static final int[][] field_150150_a = new int[][] { { 4, 5 }, { 5, 7 }, { 6, 7 }, { 4, 6 }, { 0, 1 }, { 1, 3 }, { 2, 3 }, { 0, 2 } };
/*     */   
/*     */   private final Block modelBlock;
/*     */   private final IBlockState modelState;
/*     */   private boolean hasRaytraced;
/*     */   private int rayTracePass;
/*     */   
/*     */   protected BlockStairs(IBlockState modelState) {
/*  36 */     super((modelState.getBlock()).blockMaterial);
/*  37 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)HALF, EnumHalf.BOTTOM).withProperty((IProperty)SHAPE, EnumShape.STRAIGHT));
/*  38 */     this.modelBlock = modelState.getBlock();
/*  39 */     this.modelState = modelState;
/*  40 */     setHardness(this.modelBlock.blockHardness);
/*  41 */     setResistance(this.modelBlock.blockResistance / 3.0F);
/*  42 */     setStepSound(this.modelBlock.stepSound);
/*  43 */     setLightOpacity(255);
/*  44 */     setCreativeTab(CreativeTabs.tabBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  49 */     if (this.hasRaytraced) {
/*     */       
/*  51 */       setBlockBounds(0.5F * (this.rayTracePass % 2), 0.5F * (this.rayTracePass / 4 % 2), 0.5F * (this.rayTracePass / 2 % 2), 0.5F + 0.5F * (this.rayTracePass % 2), 0.5F + 0.5F * (this.rayTracePass / 4 % 2), 0.5F + 0.5F * (this.rayTracePass / 2 % 2));
/*     */     }
/*     */     else {
/*     */       
/*  55 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  64 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBaseCollisionBounds(IBlockAccess worldIn, BlockPos pos) {
/*  77 */     if (worldIn.getBlockState(pos).getValue((IProperty)HALF) == EnumHalf.TOP) {
/*     */       
/*  79 */       setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/*  83 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isBlockStairs(Block blockIn) {
/*  92 */     return blockIn instanceof BlockStairs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSameStair(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
/* 100 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 101 */     Block block = iblockstate.getBlock();
/* 102 */     return (isBlockStairs(block) && iblockstate.getValue((IProperty)HALF) == state.getValue((IProperty)HALF) && iblockstate.getValue((IProperty)FACING) == state.getValue((IProperty)FACING));
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_176307_f(IBlockAccess blockAccess, BlockPos pos) {
/* 107 */     IBlockState iblockstate = blockAccess.getBlockState(pos);
/* 108 */     EnumFacing enumfacing = (EnumFacing)iblockstate.getValue((IProperty)FACING);
/* 109 */     EnumHalf blockstairs$enumhalf = (EnumHalf)iblockstate.getValue((IProperty)HALF);
/* 110 */     boolean flag = (blockstairs$enumhalf == EnumHalf.TOP);
/*     */     
/* 112 */     if (enumfacing == EnumFacing.EAST) {
/*     */       
/* 114 */       IBlockState iblockstate1 = blockAccess.getBlockState(pos.east());
/* 115 */       Block block = iblockstate1.getBlock();
/*     */       
/* 117 */       if (isBlockStairs(block) && blockstairs$enumhalf == iblockstate1.getValue((IProperty)HALF))
/*     */       {
/* 119 */         EnumFacing enumfacing1 = (EnumFacing)iblockstate1.getValue((IProperty)FACING);
/*     */         
/* 121 */         if (enumfacing1 == EnumFacing.NORTH && !isSameStair(blockAccess, pos.south(), iblockstate))
/*     */         {
/* 123 */           return flag ? 1 : 2;
/*     */         }
/*     */         
/* 126 */         if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(blockAccess, pos.north(), iblockstate))
/*     */         {
/* 128 */           return flag ? 2 : 1;
/*     */         }
/*     */       }
/*     */     
/* 132 */     } else if (enumfacing == EnumFacing.WEST) {
/*     */       
/* 134 */       IBlockState iblockstate2 = blockAccess.getBlockState(pos.west());
/* 135 */       Block block1 = iblockstate2.getBlock();
/*     */       
/* 137 */       if (isBlockStairs(block1) && blockstairs$enumhalf == iblockstate2.getValue((IProperty)HALF))
/*     */       {
/* 139 */         EnumFacing enumfacing2 = (EnumFacing)iblockstate2.getValue((IProperty)FACING);
/*     */         
/* 141 */         if (enumfacing2 == EnumFacing.NORTH && !isSameStair(blockAccess, pos.south(), iblockstate))
/*     */         {
/* 143 */           return flag ? 2 : 1;
/*     */         }
/*     */         
/* 146 */         if (enumfacing2 == EnumFacing.SOUTH && !isSameStair(blockAccess, pos.north(), iblockstate))
/*     */         {
/* 148 */           return flag ? 1 : 2;
/*     */         }
/*     */       }
/*     */     
/* 152 */     } else if (enumfacing == EnumFacing.SOUTH) {
/*     */       
/* 154 */       IBlockState iblockstate3 = blockAccess.getBlockState(pos.south());
/* 155 */       Block block2 = iblockstate3.getBlock();
/*     */       
/* 157 */       if (isBlockStairs(block2) && blockstairs$enumhalf == iblockstate3.getValue((IProperty)HALF))
/*     */       {
/* 159 */         EnumFacing enumfacing3 = (EnumFacing)iblockstate3.getValue((IProperty)FACING);
/*     */         
/* 161 */         if (enumfacing3 == EnumFacing.WEST && !isSameStair(blockAccess, pos.east(), iblockstate))
/*     */         {
/* 163 */           return flag ? 2 : 1;
/*     */         }
/*     */         
/* 166 */         if (enumfacing3 == EnumFacing.EAST && !isSameStair(blockAccess, pos.west(), iblockstate))
/*     */         {
/* 168 */           return flag ? 1 : 2;
/*     */         }
/*     */       }
/*     */     
/* 172 */     } else if (enumfacing == EnumFacing.NORTH) {
/*     */       
/* 174 */       IBlockState iblockstate4 = blockAccess.getBlockState(pos.north());
/* 175 */       Block block3 = iblockstate4.getBlock();
/*     */       
/* 177 */       if (isBlockStairs(block3) && blockstairs$enumhalf == iblockstate4.getValue((IProperty)HALF)) {
/*     */         
/* 179 */         EnumFacing enumfacing4 = (EnumFacing)iblockstate4.getValue((IProperty)FACING);
/*     */         
/* 181 */         if (enumfacing4 == EnumFacing.WEST && !isSameStair(blockAccess, pos.east(), iblockstate))
/*     */         {
/* 183 */           return flag ? 1 : 2;
/*     */         }
/*     */         
/* 186 */         if (enumfacing4 == EnumFacing.EAST && !isSameStair(blockAccess, pos.west(), iblockstate))
/*     */         {
/* 188 */           return flag ? 2 : 1;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_176305_g(IBlockAccess blockAccess, BlockPos pos) {
/* 198 */     IBlockState iblockstate = blockAccess.getBlockState(pos);
/* 199 */     EnumFacing enumfacing = (EnumFacing)iblockstate.getValue((IProperty)FACING);
/* 200 */     EnumHalf blockstairs$enumhalf = (EnumHalf)iblockstate.getValue((IProperty)HALF);
/* 201 */     boolean flag = (blockstairs$enumhalf == EnumHalf.TOP);
/*     */     
/* 203 */     if (enumfacing == EnumFacing.EAST) {
/*     */       
/* 205 */       IBlockState iblockstate1 = blockAccess.getBlockState(pos.west());
/* 206 */       Block block = iblockstate1.getBlock();
/*     */       
/* 208 */       if (isBlockStairs(block) && blockstairs$enumhalf == iblockstate1.getValue((IProperty)HALF))
/*     */       {
/* 210 */         EnumFacing enumfacing1 = (EnumFacing)iblockstate1.getValue((IProperty)FACING);
/*     */         
/* 212 */         if (enumfacing1 == EnumFacing.NORTH && !isSameStair(blockAccess, pos.north(), iblockstate))
/*     */         {
/* 214 */           return flag ? 1 : 2;
/*     */         }
/*     */         
/* 217 */         if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(blockAccess, pos.south(), iblockstate))
/*     */         {
/* 219 */           return flag ? 2 : 1;
/*     */         }
/*     */       }
/*     */     
/* 223 */     } else if (enumfacing == EnumFacing.WEST) {
/*     */       
/* 225 */       IBlockState iblockstate2 = blockAccess.getBlockState(pos.east());
/* 226 */       Block block1 = iblockstate2.getBlock();
/*     */       
/* 228 */       if (isBlockStairs(block1) && blockstairs$enumhalf == iblockstate2.getValue((IProperty)HALF))
/*     */       {
/* 230 */         EnumFacing enumfacing2 = (EnumFacing)iblockstate2.getValue((IProperty)FACING);
/*     */         
/* 232 */         if (enumfacing2 == EnumFacing.NORTH && !isSameStair(blockAccess, pos.north(), iblockstate))
/*     */         {
/* 234 */           return flag ? 2 : 1;
/*     */         }
/*     */         
/* 237 */         if (enumfacing2 == EnumFacing.SOUTH && !isSameStair(blockAccess, pos.south(), iblockstate))
/*     */         {
/* 239 */           return flag ? 1 : 2;
/*     */         }
/*     */       }
/*     */     
/* 243 */     } else if (enumfacing == EnumFacing.SOUTH) {
/*     */       
/* 245 */       IBlockState iblockstate3 = blockAccess.getBlockState(pos.north());
/* 246 */       Block block2 = iblockstate3.getBlock();
/*     */       
/* 248 */       if (isBlockStairs(block2) && blockstairs$enumhalf == iblockstate3.getValue((IProperty)HALF))
/*     */       {
/* 250 */         EnumFacing enumfacing3 = (EnumFacing)iblockstate3.getValue((IProperty)FACING);
/*     */         
/* 252 */         if (enumfacing3 == EnumFacing.WEST && !isSameStair(blockAccess, pos.west(), iblockstate))
/*     */         {
/* 254 */           return flag ? 2 : 1;
/*     */         }
/*     */         
/* 257 */         if (enumfacing3 == EnumFacing.EAST && !isSameStair(blockAccess, pos.east(), iblockstate))
/*     */         {
/* 259 */           return flag ? 1 : 2;
/*     */         }
/*     */       }
/*     */     
/* 263 */     } else if (enumfacing == EnumFacing.NORTH) {
/*     */       
/* 265 */       IBlockState iblockstate4 = blockAccess.getBlockState(pos.south());
/* 266 */       Block block3 = iblockstate4.getBlock();
/*     */       
/* 268 */       if (isBlockStairs(block3) && blockstairs$enumhalf == iblockstate4.getValue((IProperty)HALF)) {
/*     */         
/* 270 */         EnumFacing enumfacing4 = (EnumFacing)iblockstate4.getValue((IProperty)FACING);
/*     */         
/* 272 */         if (enumfacing4 == EnumFacing.WEST && !isSameStair(blockAccess, pos.west(), iblockstate))
/*     */         {
/* 274 */           return flag ? 1 : 2;
/*     */         }
/*     */         
/* 277 */         if (enumfacing4 == EnumFacing.EAST && !isSameStair(blockAccess, pos.east(), iblockstate))
/*     */         {
/* 279 */           return flag ? 2 : 1;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 284 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_176306_h(IBlockAccess blockAccess, BlockPos pos) {
/* 289 */     IBlockState iblockstate = blockAccess.getBlockState(pos);
/* 290 */     EnumFacing enumfacing = (EnumFacing)iblockstate.getValue((IProperty)FACING);
/* 291 */     EnumHalf blockstairs$enumhalf = (EnumHalf)iblockstate.getValue((IProperty)HALF);
/* 292 */     boolean flag = (blockstairs$enumhalf == EnumHalf.TOP);
/* 293 */     float f = 0.5F;
/* 294 */     float f1 = 1.0F;
/*     */     
/* 296 */     if (flag) {
/*     */       
/* 298 */       f = 0.0F;
/* 299 */       f1 = 0.5F;
/*     */     } 
/*     */     
/* 302 */     float f2 = 0.0F;
/* 303 */     float f3 = 1.0F;
/* 304 */     float f4 = 0.0F;
/* 305 */     float f5 = 0.5F;
/* 306 */     boolean flag1 = true;
/*     */     
/* 308 */     if (enumfacing == EnumFacing.EAST) {
/*     */       
/* 310 */       f2 = 0.5F;
/* 311 */       f5 = 1.0F;
/* 312 */       IBlockState iblockstate1 = blockAccess.getBlockState(pos.east());
/* 313 */       Block block = iblockstate1.getBlock();
/*     */       
/* 315 */       if (isBlockStairs(block) && blockstairs$enumhalf == iblockstate1.getValue((IProperty)HALF)) {
/*     */         
/* 317 */         EnumFacing enumfacing1 = (EnumFacing)iblockstate1.getValue((IProperty)FACING);
/*     */         
/* 319 */         if (enumfacing1 == EnumFacing.NORTH && !isSameStair(blockAccess, pos.south(), iblockstate))
/*     */         {
/* 321 */           f5 = 0.5F;
/* 322 */           flag1 = false;
/*     */         }
/* 324 */         else if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(blockAccess, pos.north(), iblockstate))
/*     */         {
/* 326 */           f4 = 0.5F;
/* 327 */           flag1 = false;
/*     */         }
/*     */       
/*     */       } 
/* 331 */     } else if (enumfacing == EnumFacing.WEST) {
/*     */       
/* 333 */       f3 = 0.5F;
/* 334 */       f5 = 1.0F;
/* 335 */       IBlockState iblockstate2 = blockAccess.getBlockState(pos.west());
/* 336 */       Block block1 = iblockstate2.getBlock();
/*     */       
/* 338 */       if (isBlockStairs(block1) && blockstairs$enumhalf == iblockstate2.getValue((IProperty)HALF)) {
/*     */         
/* 340 */         EnumFacing enumfacing2 = (EnumFacing)iblockstate2.getValue((IProperty)FACING);
/*     */         
/* 342 */         if (enumfacing2 == EnumFacing.NORTH && !isSameStair(blockAccess, pos.south(), iblockstate))
/*     */         {
/* 344 */           f5 = 0.5F;
/* 345 */           flag1 = false;
/*     */         }
/* 347 */         else if (enumfacing2 == EnumFacing.SOUTH && !isSameStair(blockAccess, pos.north(), iblockstate))
/*     */         {
/* 349 */           f4 = 0.5F;
/* 350 */           flag1 = false;
/*     */         }
/*     */       
/*     */       } 
/* 354 */     } else if (enumfacing == EnumFacing.SOUTH) {
/*     */       
/* 356 */       f4 = 0.5F;
/* 357 */       f5 = 1.0F;
/* 358 */       IBlockState iblockstate3 = blockAccess.getBlockState(pos.south());
/* 359 */       Block block2 = iblockstate3.getBlock();
/*     */       
/* 361 */       if (isBlockStairs(block2) && blockstairs$enumhalf == iblockstate3.getValue((IProperty)HALF)) {
/*     */         
/* 363 */         EnumFacing enumfacing3 = (EnumFacing)iblockstate3.getValue((IProperty)FACING);
/*     */         
/* 365 */         if (enumfacing3 == EnumFacing.WEST && !isSameStair(blockAccess, pos.east(), iblockstate))
/*     */         {
/* 367 */           f3 = 0.5F;
/* 368 */           flag1 = false;
/*     */         }
/* 370 */         else if (enumfacing3 == EnumFacing.EAST && !isSameStair(blockAccess, pos.west(), iblockstate))
/*     */         {
/* 372 */           f2 = 0.5F;
/* 373 */           flag1 = false;
/*     */         }
/*     */       
/*     */       } 
/* 377 */     } else if (enumfacing == EnumFacing.NORTH) {
/*     */       
/* 379 */       IBlockState iblockstate4 = blockAccess.getBlockState(pos.north());
/* 380 */       Block block3 = iblockstate4.getBlock();
/*     */       
/* 382 */       if (isBlockStairs(block3) && blockstairs$enumhalf == iblockstate4.getValue((IProperty)HALF)) {
/*     */         
/* 384 */         EnumFacing enumfacing4 = (EnumFacing)iblockstate4.getValue((IProperty)FACING);
/*     */         
/* 386 */         if (enumfacing4 == EnumFacing.WEST && !isSameStair(blockAccess, pos.east(), iblockstate)) {
/*     */           
/* 388 */           f3 = 0.5F;
/* 389 */           flag1 = false;
/*     */         }
/* 391 */         else if (enumfacing4 == EnumFacing.EAST && !isSameStair(blockAccess, pos.west(), iblockstate)) {
/*     */           
/* 393 */           f2 = 0.5F;
/* 394 */           flag1 = false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 399 */     setBlockBounds(f2, f, f4, f3, f1, f5);
/* 400 */     return flag1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_176304_i(IBlockAccess blockAccess, BlockPos pos) {
/* 405 */     IBlockState iblockstate = blockAccess.getBlockState(pos);
/* 406 */     EnumFacing enumfacing = (EnumFacing)iblockstate.getValue((IProperty)FACING);
/* 407 */     EnumHalf blockstairs$enumhalf = (EnumHalf)iblockstate.getValue((IProperty)HALF);
/* 408 */     boolean flag = (blockstairs$enumhalf == EnumHalf.TOP);
/* 409 */     float f = 0.5F;
/* 410 */     float f1 = 1.0F;
/*     */     
/* 412 */     if (flag) {
/*     */       
/* 414 */       f = 0.0F;
/* 415 */       f1 = 0.5F;
/*     */     } 
/*     */     
/* 418 */     float f2 = 0.0F;
/* 419 */     float f3 = 0.5F;
/* 420 */     float f4 = 0.5F;
/* 421 */     float f5 = 1.0F;
/* 422 */     boolean flag1 = false;
/*     */     
/* 424 */     if (enumfacing == EnumFacing.EAST) {
/*     */       
/* 426 */       IBlockState iblockstate1 = blockAccess.getBlockState(pos.west());
/* 427 */       Block block = iblockstate1.getBlock();
/*     */       
/* 429 */       if (isBlockStairs(block) && blockstairs$enumhalf == iblockstate1.getValue((IProperty)HALF)) {
/*     */         
/* 431 */         EnumFacing enumfacing1 = (EnumFacing)iblockstate1.getValue((IProperty)FACING);
/*     */         
/* 433 */         if (enumfacing1 == EnumFacing.NORTH && !isSameStair(blockAccess, pos.north(), iblockstate))
/*     */         {
/* 435 */           f4 = 0.0F;
/* 436 */           f5 = 0.5F;
/* 437 */           flag1 = true;
/*     */         }
/* 439 */         else if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(blockAccess, pos.south(), iblockstate))
/*     */         {
/* 441 */           f4 = 0.5F;
/* 442 */           f5 = 1.0F;
/* 443 */           flag1 = true;
/*     */         }
/*     */       
/*     */       } 
/* 447 */     } else if (enumfacing == EnumFacing.WEST) {
/*     */       
/* 449 */       IBlockState iblockstate2 = blockAccess.getBlockState(pos.east());
/* 450 */       Block block1 = iblockstate2.getBlock();
/*     */       
/* 452 */       if (isBlockStairs(block1) && blockstairs$enumhalf == iblockstate2.getValue((IProperty)HALF)) {
/*     */         
/* 454 */         f2 = 0.5F;
/* 455 */         f3 = 1.0F;
/* 456 */         EnumFacing enumfacing2 = (EnumFacing)iblockstate2.getValue((IProperty)FACING);
/*     */         
/* 458 */         if (enumfacing2 == EnumFacing.NORTH && !isSameStair(blockAccess, pos.north(), iblockstate))
/*     */         {
/* 460 */           f4 = 0.0F;
/* 461 */           f5 = 0.5F;
/* 462 */           flag1 = true;
/*     */         }
/* 464 */         else if (enumfacing2 == EnumFacing.SOUTH && !isSameStair(blockAccess, pos.south(), iblockstate))
/*     */         {
/* 466 */           f4 = 0.5F;
/* 467 */           f5 = 1.0F;
/* 468 */           flag1 = true;
/*     */         }
/*     */       
/*     */       } 
/* 472 */     } else if (enumfacing == EnumFacing.SOUTH) {
/*     */       
/* 474 */       IBlockState iblockstate3 = blockAccess.getBlockState(pos.north());
/* 475 */       Block block2 = iblockstate3.getBlock();
/*     */       
/* 477 */       if (isBlockStairs(block2) && blockstairs$enumhalf == iblockstate3.getValue((IProperty)HALF)) {
/*     */         
/* 479 */         f4 = 0.0F;
/* 480 */         f5 = 0.5F;
/* 481 */         EnumFacing enumfacing3 = (EnumFacing)iblockstate3.getValue((IProperty)FACING);
/*     */         
/* 483 */         if (enumfacing3 == EnumFacing.WEST && !isSameStair(blockAccess, pos.west(), iblockstate))
/*     */         {
/* 485 */           flag1 = true;
/*     */         }
/* 487 */         else if (enumfacing3 == EnumFacing.EAST && !isSameStair(blockAccess, pos.east(), iblockstate))
/*     */         {
/* 489 */           f2 = 0.5F;
/* 490 */           f3 = 1.0F;
/* 491 */           flag1 = true;
/*     */         }
/*     */       
/*     */       } 
/* 495 */     } else if (enumfacing == EnumFacing.NORTH) {
/*     */       
/* 497 */       IBlockState iblockstate4 = blockAccess.getBlockState(pos.south());
/* 498 */       Block block3 = iblockstate4.getBlock();
/*     */       
/* 500 */       if (isBlockStairs(block3) && blockstairs$enumhalf == iblockstate4.getValue((IProperty)HALF)) {
/*     */         
/* 502 */         EnumFacing enumfacing4 = (EnumFacing)iblockstate4.getValue((IProperty)FACING);
/*     */         
/* 504 */         if (enumfacing4 == EnumFacing.WEST && !isSameStair(blockAccess, pos.west(), iblockstate)) {
/*     */           
/* 506 */           flag1 = true;
/*     */         }
/* 508 */         else if (enumfacing4 == EnumFacing.EAST && !isSameStair(blockAccess, pos.east(), iblockstate)) {
/*     */           
/* 510 */           f2 = 0.5F;
/* 511 */           f3 = 1.0F;
/* 512 */           flag1 = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 517 */     if (flag1)
/*     */     {
/* 519 */       setBlockBounds(f2, f, f4, f3, f1, f5);
/*     */     }
/*     */     
/* 522 */     return flag1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/* 530 */     setBaseCollisionBounds((IBlockAccess)worldIn, pos);
/* 531 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/* 532 */     boolean flag = func_176306_h((IBlockAccess)worldIn, pos);
/* 533 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     
/* 535 */     if (flag && func_176304_i((IBlockAccess)worldIn, pos))
/*     */     {
/* 537 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     }
/*     */     
/* 540 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 545 */     this.modelBlock.randomDisplayTick(worldIn, pos, state, rand);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
/* 550 */     this.modelBlock.onBlockClicked(worldIn, pos, playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
/* 558 */     this.modelBlock.onBlockDestroyedByPlayer(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMixedBrightnessForBlock(IBlockAccess worldIn, BlockPos pos) {
/* 563 */     return this.modelBlock.getMixedBrightnessForBlock(worldIn, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getExplosionResistance(Entity exploder) {
/* 571 */     return this.modelBlock.getExplosionResistance(exploder);
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 576 */     return this.modelBlock.getBlockLayer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/* 584 */     return this.modelBlock.tickRate(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/* 589 */     return this.modelBlock.getSelectedBoundingBox(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3 motion) {
/* 594 */     return this.modelBlock.modifyAcceleration(worldIn, pos, entityIn, motion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCollidable() {
/* 602 */     return this.modelBlock.isCollidable();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
/* 607 */     return this.modelBlock.canCollideCheck(state, hitIfLiquid);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 612 */     return this.modelBlock.canPlaceBlockAt(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 617 */     onNeighborBlockChange(worldIn, pos, this.modelState, Blocks.air);
/* 618 */     this.modelBlock.onBlockAdded(worldIn, pos, this.modelState);
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 623 */     this.modelBlock.breakBlock(worldIn, pos, this.modelState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn) {
/* 631 */     this.modelBlock.onEntityCollidedWithBlock(worldIn, pos, entityIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 636 */     this.modelBlock.updateTick(worldIn, pos, state, rand);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 641 */     return this.modelBlock.onBlockActivated(worldIn, pos, this.modelState, playerIn, EnumFacing.DOWN, 0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
/* 649 */     this.modelBlock.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state) {
/* 657 */     return this.modelBlock.getMapColor(this.modelState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 666 */     IBlockState iblockstate = super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
/* 667 */     iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing()).withProperty((IProperty)SHAPE, EnumShape.STRAIGHT);
/* 668 */     return (facing != EnumFacing.DOWN && (facing == EnumFacing.UP || hitY <= 0.5D)) ? iblockstate.withProperty((IProperty)HALF, EnumHalf.BOTTOM) : iblockstate.withProperty((IProperty)HALF, EnumHalf.TOP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
/* 676 */     MovingObjectPosition[] amovingobjectposition = new MovingObjectPosition[8];
/* 677 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 678 */     int i = ((EnumFacing)iblockstate.getValue((IProperty)FACING)).getHorizontalIndex();
/* 679 */     boolean flag = (iblockstate.getValue((IProperty)HALF) == EnumHalf.TOP);
/* 680 */     int[] aint = field_150150_a[i + (flag ? 4 : 0)];
/* 681 */     this.hasRaytraced = true;
/*     */     
/* 683 */     for (int j = 0; j < 8; j++) {
/*     */       
/* 685 */       this.rayTracePass = j;
/*     */       
/* 687 */       if (Arrays.binarySearch(aint, j) < 0)
/*     */       {
/* 689 */         amovingobjectposition[j] = super.collisionRayTrace(worldIn, pos, start, end);
/*     */       }
/*     */     } 
/*     */     
/* 693 */     for (int k : aint)
/*     */     {
/* 695 */       amovingobjectposition[k] = null;
/*     */     }
/*     */     
/* 698 */     MovingObjectPosition movingobjectposition1 = null;
/* 699 */     double d1 = 0.0D;
/*     */     
/* 701 */     for (MovingObjectPosition movingobjectposition : amovingobjectposition) {
/*     */       
/* 703 */       if (movingobjectposition != null) {
/*     */         
/* 705 */         double d0 = movingobjectposition.hitVec.squareDistanceTo(end);
/*     */         
/* 707 */         if (d0 > d1) {
/*     */           
/* 709 */           movingobjectposition1 = movingobjectposition;
/* 710 */           d1 = d0;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 715 */     return movingobjectposition1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 723 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)HALF, ((meta & 0x4) > 0) ? EnumHalf.TOP : EnumHalf.BOTTOM);
/* 724 */     iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.getFront(5 - (meta & 0x3)));
/* 725 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 733 */     int i = 0;
/*     */     
/* 735 */     if (state.getValue((IProperty)HALF) == EnumHalf.TOP)
/*     */     {
/* 737 */       i |= 0x4;
/*     */     }
/*     */     
/* 740 */     i |= 5 - ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/* 741 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 750 */     if (func_176306_h(worldIn, pos)) {
/*     */       
/* 752 */       switch (func_176305_g(worldIn, pos)) {
/*     */         
/*     */         case 0:
/* 755 */           state = state.withProperty((IProperty)SHAPE, EnumShape.STRAIGHT);
/*     */           break;
/*     */         
/*     */         case 1:
/* 759 */           state = state.withProperty((IProperty)SHAPE, EnumShape.INNER_RIGHT);
/*     */           break;
/*     */         
/*     */         case 2:
/* 763 */           state = state.withProperty((IProperty)SHAPE, EnumShape.INNER_LEFT);
/*     */           break;
/*     */       } 
/*     */     
/*     */     } else {
/* 768 */       switch (func_176307_f(worldIn, pos)) {
/*     */         
/*     */         case 0:
/* 771 */           state = state.withProperty((IProperty)SHAPE, EnumShape.STRAIGHT);
/*     */           break;
/*     */         
/*     */         case 1:
/* 775 */           state = state.withProperty((IProperty)SHAPE, EnumShape.OUTER_RIGHT);
/*     */           break;
/*     */         
/*     */         case 2:
/* 779 */           state = state.withProperty((IProperty)SHAPE, EnumShape.OUTER_LEFT);
/*     */           break;
/*     */       } 
/*     */     } 
/* 783 */     return state;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 788 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)HALF, (IProperty)SHAPE });
/*     */   }
/*     */   
/*     */   public enum EnumHalf
/*     */     implements IStringSerializable {
/* 793 */     TOP("top"),
/* 794 */     BOTTOM("bottom");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     EnumHalf(String name) {
/* 800 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 805 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 810 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum EnumShape
/*     */     implements IStringSerializable {
/* 816 */     STRAIGHT("straight"),
/* 817 */     INNER_LEFT("inner_left"),
/* 818 */     INNER_RIGHT("inner_right"),
/* 819 */     OUTER_LEFT("outer_left"),
/* 820 */     OUTER_RIGHT("outer_right");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     EnumShape(String name) {
/* 826 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 831 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 836 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockStairs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */