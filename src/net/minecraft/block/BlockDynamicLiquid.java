/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockDynamicLiquid
/*     */   extends BlockLiquid {
/*     */   int adjacentSourceBlocks;
/*     */   
/*     */   protected BlockDynamicLiquid(Material materialIn) {
/*  20 */     super(materialIn);
/*     */   }
/*     */ 
/*     */   
/*     */   private void placeStaticBlock(World worldIn, BlockPos pos, IBlockState currentState) {
/*  25 */     worldIn.setBlockState(pos, getStaticBlock(this.blockMaterial).getDefaultState().withProperty((IProperty)LEVEL, currentState.getValue((IProperty)LEVEL)), 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  30 */     int i = ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/*  31 */     int j = 1;
/*     */     
/*  33 */     if (this.blockMaterial == Material.lava && !worldIn.provider.doesWaterVaporize())
/*     */     {
/*  35 */       j = 2;
/*     */     }
/*     */     
/*  38 */     int k = tickRate(worldIn);
/*     */     
/*  40 */     if (i > 0) {
/*     */       
/*  42 */       int l = -100;
/*  43 */       this.adjacentSourceBlocks = 0;
/*     */       
/*  45 */       for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
/*     */       {
/*  47 */         l = checkAdjacentBlock(worldIn, pos.offset(enumfacing), l);
/*     */       }
/*     */       
/*  50 */       int i1 = l + j;
/*     */       
/*  52 */       if (i1 >= 8 || l < 0)
/*     */       {
/*  54 */         i1 = -1;
/*     */       }
/*     */       
/*  57 */       if (getLevel((IBlockAccess)worldIn, pos.up()) >= 0) {
/*     */         
/*  59 */         int j1 = getLevel((IBlockAccess)worldIn, pos.up());
/*     */         
/*  61 */         if (j1 >= 8) {
/*     */           
/*  63 */           i1 = j1;
/*     */         }
/*     */         else {
/*     */           
/*  67 */           i1 = j1 + 8;
/*     */         } 
/*     */       } 
/*     */       
/*  71 */       if (this.adjacentSourceBlocks >= 2 && this.blockMaterial == Material.water) {
/*     */         
/*  73 */         IBlockState iblockstate1 = worldIn.getBlockState(pos.down());
/*     */         
/*  75 */         if (iblockstate1.getBlock().getMaterial().isSolid()) {
/*     */           
/*  77 */           i1 = 0;
/*     */         }
/*  79 */         else if (iblockstate1.getBlock().getMaterial() == this.blockMaterial && ((Integer)iblockstate1.getValue((IProperty)LEVEL)).intValue() == 0) {
/*     */           
/*  81 */           i1 = 0;
/*     */         } 
/*     */       } 
/*     */       
/*  85 */       if (this.blockMaterial == Material.lava && i < 8 && i1 < 8 && i1 > i && rand.nextInt(4) != 0)
/*     */       {
/*  87 */         k <<= 2;
/*     */       }
/*     */       
/*  90 */       if (i1 == i) {
/*     */         
/*  92 */         placeStaticBlock(worldIn, pos, state);
/*     */       }
/*     */       else {
/*     */         
/*  96 */         i = i1;
/*     */         
/*  98 */         if (i1 < 0)
/*     */         {
/* 100 */           worldIn.setBlockToAir(pos);
/*     */         }
/*     */         else
/*     */         {
/* 104 */           state = state.withProperty((IProperty)LEVEL, Integer.valueOf(i1));
/* 105 */           worldIn.setBlockState(pos, state, 2);
/* 106 */           worldIn.scheduleUpdate(pos, this, k);
/* 107 */           worldIn.notifyNeighborsOfStateChange(pos, this);
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 113 */       placeStaticBlock(worldIn, pos, state);
/*     */     } 
/*     */     
/* 116 */     IBlockState iblockstate = worldIn.getBlockState(pos.down());
/*     */     
/* 118 */     if (canFlowInto(worldIn, pos.down(), iblockstate)) {
/*     */       
/* 120 */       if (this.blockMaterial == Material.lava && worldIn.getBlockState(pos.down()).getBlock().getMaterial() == Material.water) {
/*     */         
/* 122 */         worldIn.setBlockState(pos.down(), Blocks.stone.getDefaultState());
/* 123 */         triggerMixEffects(worldIn, pos.down());
/*     */         
/*     */         return;
/*     */       } 
/* 127 */       if (i >= 8)
/*     */       {
/* 129 */         tryFlowInto(worldIn, pos.down(), iblockstate, i);
/*     */       }
/*     */       else
/*     */       {
/* 133 */         tryFlowInto(worldIn, pos.down(), iblockstate, i + 8);
/*     */       }
/*     */     
/* 136 */     } else if (i >= 0 && (i == 0 || isBlocked(worldIn, pos.down(), iblockstate))) {
/*     */       
/* 138 */       Set<EnumFacing> set = getPossibleFlowDirections(worldIn, pos);
/* 139 */       int k1 = i + j;
/*     */       
/* 141 */       if (i >= 8)
/*     */       {
/* 143 */         k1 = 1;
/*     */       }
/*     */       
/* 146 */       if (k1 >= 8) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 151 */       for (EnumFacing enumfacing1 : set)
/*     */       {
/* 153 */         tryFlowInto(worldIn, pos.offset(enumfacing1), worldIn.getBlockState(pos.offset(enumfacing1)), k1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void tryFlowInto(World worldIn, BlockPos pos, IBlockState state, int level) {
/* 160 */     if (canFlowInto(worldIn, pos, state)) {
/*     */       
/* 162 */       if (state.getBlock() != Blocks.air)
/*     */       {
/* 164 */         if (this.blockMaterial == Material.lava) {
/*     */           
/* 166 */           triggerMixEffects(worldIn, pos);
/*     */         }
/*     */         else {
/*     */           
/* 170 */           state.getBlock().dropBlockAsItem(worldIn, pos, state, 0);
/*     */         } 
/*     */       }
/*     */       
/* 174 */       worldIn.setBlockState(pos, getDefaultState().withProperty((IProperty)LEVEL, Integer.valueOf(level)), 3);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int func_176374_a(World worldIn, BlockPos pos, int distance, EnumFacing calculateFlowCost) {
/* 180 */     int i = 1000;
/*     */     
/* 182 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 184 */       if (enumfacing != calculateFlowCost) {
/*     */         
/* 186 */         BlockPos blockpos = pos.offset(enumfacing);
/* 187 */         IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */         
/* 189 */         if (!isBlocked(worldIn, blockpos, iblockstate) && (iblockstate.getBlock().getMaterial() != this.blockMaterial || ((Integer)iblockstate.getValue((IProperty)LEVEL)).intValue() > 0)) {
/*     */           
/* 191 */           if (!isBlocked(worldIn, blockpos.down(), iblockstate))
/*     */           {
/* 193 */             return distance;
/*     */           }
/*     */           
/* 196 */           if (distance < 4) {
/*     */             
/* 198 */             int j = func_176374_a(worldIn, blockpos, distance + 1, enumfacing.getOpposite());
/*     */             
/* 200 */             if (j < i)
/*     */             {
/* 202 */               i = j;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 209 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<EnumFacing> getPossibleFlowDirections(World worldIn, BlockPos pos) {
/* 214 */     int i = 1000;
/* 215 */     Set<EnumFacing> set = EnumSet.noneOf(EnumFacing.class);
/*     */     
/* 217 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 219 */       BlockPos blockpos = pos.offset(enumfacing);
/* 220 */       IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */       
/* 222 */       if (!isBlocked(worldIn, blockpos, iblockstate) && (iblockstate.getBlock().getMaterial() != this.blockMaterial || ((Integer)iblockstate.getValue((IProperty)LEVEL)).intValue() > 0)) {
/*     */         int j;
/*     */ 
/*     */         
/* 226 */         if (isBlocked(worldIn, blockpos.down(), worldIn.getBlockState(blockpos.down()))) {
/*     */           
/* 228 */           j = func_176374_a(worldIn, blockpos, 1, enumfacing.getOpposite());
/*     */         }
/*     */         else {
/*     */           
/* 232 */           j = 0;
/*     */         } 
/*     */         
/* 235 */         if (j < i)
/*     */         {
/* 237 */           set.clear();
/*     */         }
/*     */         
/* 240 */         if (j <= i) {
/*     */           
/* 242 */           set.add(enumfacing);
/* 243 */           i = j;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 248 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isBlocked(World worldIn, BlockPos pos, IBlockState state) {
/* 253 */     Block block = worldIn.getBlockState(pos).getBlock();
/* 254 */     return (!(block instanceof BlockDoor) && block != Blocks.standing_sign && block != Blocks.ladder && block != Blocks.reeds) ? ((block.blockMaterial == Material.portal) ? true : block.blockMaterial.blocksMovement()) : true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int checkAdjacentBlock(World worldIn, BlockPos pos, int currentMinLevel) {
/* 259 */     int i = getLevel((IBlockAccess)worldIn, pos);
/*     */     
/* 261 */     if (i < 0)
/*     */     {
/* 263 */       return currentMinLevel;
/*     */     }
/*     */ 
/*     */     
/* 267 */     if (i == 0)
/*     */     {
/* 269 */       this.adjacentSourceBlocks++;
/*     */     }
/*     */     
/* 272 */     if (i >= 8)
/*     */     {
/* 274 */       i = 0;
/*     */     }
/*     */     
/* 277 */     return (currentMinLevel >= 0 && i >= currentMinLevel) ? currentMinLevel : i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canFlowInto(World worldIn, BlockPos pos, IBlockState state) {
/* 283 */     Material material = state.getBlock().getMaterial();
/* 284 */     return (material != this.blockMaterial && material != Material.lava && !isBlocked(worldIn, pos, state));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 289 */     if (!checkForMixing(worldIn, pos, state))
/*     */     {
/* 291 */       worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockDynamicLiquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */