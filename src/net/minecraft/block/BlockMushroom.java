/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.feature.WorldGenBigMushroom;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ 
/*     */ public class BlockMushroom
/*     */   extends BlockBush
/*     */   implements IGrowable {
/*     */   protected BlockMushroom() {
/*  16 */     float f = 0.2F;
/*  17 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
/*  18 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  23 */     if (rand.nextInt(25) == 0) {
/*     */       
/*  25 */       int i = 5;
/*  26 */       int j = 4;
/*     */       
/*  28 */       for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
/*     */         
/*  30 */         if (worldIn.getBlockState(blockpos).getBlock() == this) {
/*     */           
/*  32 */           i--;
/*     */           
/*  34 */           if (i <= 0) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  41 */       BlockPos blockpos1 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);
/*     */       
/*  43 */       for (int k = 0; k < 4; k++) {
/*     */         
/*  45 */         if (worldIn.isAirBlock(blockpos1) && canBlockStay(worldIn, blockpos1, getDefaultState()))
/*     */         {
/*  47 */           pos = blockpos1;
/*     */         }
/*     */         
/*  50 */         blockpos1 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);
/*     */       } 
/*     */       
/*  53 */       if (worldIn.isAirBlock(blockpos1) && canBlockStay(worldIn, blockpos1, getDefaultState()))
/*     */       {
/*  55 */         worldIn.setBlockState(blockpos1, getDefaultState(), 2);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  62 */     return (super.canPlaceBlockAt(worldIn, pos) && canBlockStay(worldIn, pos, getDefaultState()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canPlaceBlockOn(Block ground) {
/*  70 */     return ground.isFullBlock();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/*  75 */     if (pos.getY() >= 0 && pos.getY() < 256) {
/*     */       
/*  77 */       IBlockState iblockstate = worldIn.getBlockState(pos.down());
/*  78 */       return (iblockstate.getBlock() == Blocks.mycelium) ? true : ((iblockstate.getBlock() == Blocks.dirt && iblockstate.getValue((IProperty)BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL) ? true : ((worldIn.getLight(pos) < 13 && canPlaceBlockOn(iblockstate.getBlock()))));
/*     */     } 
/*     */ 
/*     */     
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generateBigMushroom(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*     */     WorldGenBigMushroom worldGenBigMushroom;
/*  88 */     worldIn.setBlockToAir(pos);
/*  89 */     WorldGenerator worldgenerator = null;
/*     */     
/*  91 */     if (this == Blocks.brown_mushroom) {
/*     */       
/*  93 */       worldGenBigMushroom = new WorldGenBigMushroom(Blocks.brown_mushroom_block);
/*     */     }
/*  95 */     else if (this == Blocks.red_mushroom) {
/*     */       
/*  97 */       worldGenBigMushroom = new WorldGenBigMushroom(Blocks.red_mushroom_block);
/*     */     } 
/*     */     
/* 100 */     if (worldGenBigMushroom != null && worldGenBigMushroom.generate(worldIn, rand, pos))
/*     */     {
/* 102 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 106 */     worldIn.setBlockState(pos, state, 3);
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 116 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 121 */     return (rand.nextFloat() < 0.4D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 126 */     generateBigMushroom(worldIn, pos, state, rand);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockMushroom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */