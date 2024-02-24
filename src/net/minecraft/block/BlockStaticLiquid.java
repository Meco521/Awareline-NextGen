/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockStaticLiquid
/*     */   extends BlockLiquid
/*     */ {
/*     */   protected BlockStaticLiquid(Material materialIn) {
/*  16 */     super(materialIn);
/*  17 */     setTickRandomly(false);
/*     */     
/*  19 */     if (materialIn == Material.lava)
/*     */     {
/*  21 */       setTickRandomly(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  30 */     if (!checkForMixing(worldIn, pos, state))
/*     */     {
/*  32 */       updateLiquid(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateLiquid(World worldIn, BlockPos pos, IBlockState state) {
/*  38 */     BlockDynamicLiquid blockdynamicliquid = getFlowingBlock(this.blockMaterial);
/*  39 */     worldIn.setBlockState(pos, blockdynamicliquid.getDefaultState().withProperty((IProperty)LEVEL, state.getValue((IProperty)LEVEL)), 2);
/*  40 */     worldIn.scheduleUpdate(pos, blockdynamicliquid, tickRate(worldIn));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  45 */     if (this.blockMaterial == Material.lava)
/*     */     {
/*  47 */       if (worldIn.getGameRules().getBoolean("doFireTick")) {
/*     */         
/*  49 */         int i = rand.nextInt(3);
/*     */         
/*  51 */         if (i > 0) {
/*     */           
/*  53 */           BlockPos blockpos = pos;
/*     */           
/*  55 */           for (int j = 0; j < i; j++) {
/*     */             
/*  57 */             blockpos = blockpos.add(rand.nextInt(3) - 1, 1, rand.nextInt(3) - 1);
/*  58 */             Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */             
/*  60 */             if (block.blockMaterial == Material.air) {
/*     */               
/*  62 */               if (isSurroundingBlockFlammable(worldIn, blockpos)) {
/*     */                 
/*  64 */                 worldIn.setBlockState(blockpos, Blocks.fire.getDefaultState());
/*     */                 
/*     */                 return;
/*     */               } 
/*  68 */             } else if (block.blockMaterial.blocksMovement()) {
/*     */               
/*     */               return;
/*     */             }
/*     */           
/*     */           } 
/*     */         } else {
/*     */           
/*  76 */           for (int k = 0; k < 3; k++) {
/*     */             
/*  78 */             BlockPos blockpos1 = pos.add(rand.nextInt(3) - 1, 0, rand.nextInt(3) - 1);
/*     */             
/*  80 */             if (worldIn.isAirBlock(blockpos1.up()) && getCanBlockBurn(worldIn, blockpos1))
/*     */             {
/*  82 */               worldIn.setBlockState(blockpos1.up(), Blocks.fire.getDefaultState());
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isSurroundingBlockFlammable(World worldIn, BlockPos pos) {
/*  92 */     for (EnumFacing enumfacing : EnumFacing.values()) {
/*     */       
/*  94 */       if (getCanBlockBurn(worldIn, pos.offset(enumfacing)))
/*     */       {
/*  96 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean getCanBlockBurn(World worldIn, BlockPos pos) {
/* 105 */     return worldIn.getBlockState(pos).getBlock().getMaterial().getCanBurn();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockStaticLiquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */