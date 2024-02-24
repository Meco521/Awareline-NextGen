/*     */ package net.minecraft.block.state;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockPistonBase;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class BlockPistonStructureHelper
/*     */ {
/*     */   private final World world;
/*     */   private final BlockPos pistonPos;
/*     */   private final BlockPos blockToMove;
/*     */   private final EnumFacing moveDirection;
/*  20 */   private final List<BlockPos> toMove = Lists.newArrayList();
/*  21 */   private final List<BlockPos> toDestroy = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public BlockPistonStructureHelper(World worldIn, BlockPos posIn, EnumFacing pistonFacing, boolean extending) {
/*  25 */     this.world = worldIn;
/*  26 */     this.pistonPos = posIn;
/*     */     
/*  28 */     if (extending) {
/*     */       
/*  30 */       this.moveDirection = pistonFacing;
/*  31 */       this.blockToMove = posIn.offset(pistonFacing);
/*     */     }
/*     */     else {
/*     */       
/*  35 */       this.moveDirection = pistonFacing.getOpposite();
/*  36 */       this.blockToMove = posIn.offset(pistonFacing, 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canMove() {
/*  42 */     this.toMove.clear();
/*  43 */     this.toDestroy.clear();
/*  44 */     Block block = this.world.getBlockState(this.blockToMove).getBlock();
/*     */     
/*  46 */     if (!BlockPistonBase.canPush(block, this.world, this.blockToMove, this.moveDirection, false)) {
/*     */       
/*  48 */       if (block.getMobilityFlag() != 1)
/*     */       {
/*  50 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  54 */       this.toDestroy.add(this.blockToMove);
/*  55 */       return true;
/*     */     } 
/*     */     
/*  58 */     if (!func_177251_a(this.blockToMove))
/*     */     {
/*  60 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  64 */     for (int i = 0; i < this.toMove.size(); i++) {
/*     */       
/*  66 */       BlockPos blockpos = this.toMove.get(i);
/*     */       
/*  68 */       if (this.world.getBlockState(blockpos).getBlock() == Blocks.slime_block && !func_177250_b(blockpos))
/*     */       {
/*  70 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  74 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean func_177251_a(BlockPos origin) {
/*  80 */     Block block = this.world.getBlockState(origin).getBlock();
/*     */     
/*  82 */     if (block.getMaterial() == Material.air)
/*     */     {
/*  84 */       return true;
/*     */     }
/*  86 */     if (!BlockPistonBase.canPush(block, this.world, origin, this.moveDirection, false))
/*     */     {
/*  88 */       return true;
/*     */     }
/*  90 */     if (origin.equals(this.pistonPos))
/*     */     {
/*  92 */       return true;
/*     */     }
/*  94 */     if (this.toMove.contains(origin))
/*     */     {
/*  96 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 100 */     int i = 1;
/*     */     
/* 102 */     if (i + this.toMove.size() > 12)
/*     */     {
/* 104 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 108 */     while (block == Blocks.slime_block) {
/*     */       
/* 110 */       BlockPos blockpos = origin.offset(this.moveDirection.getOpposite(), i);
/* 111 */       block = this.world.getBlockState(blockpos).getBlock();
/*     */       
/* 113 */       if (block.getMaterial() == Material.air || !BlockPistonBase.canPush(block, this.world, blockpos, this.moveDirection, false) || blockpos.equals(this.pistonPos)) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 118 */       i++;
/*     */       
/* 120 */       if (i + this.toMove.size() > 12)
/*     */       {
/* 122 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 126 */     int i1 = 0;
/*     */     
/* 128 */     for (int j = i - 1; j >= 0; j--) {
/*     */       
/* 130 */       this.toMove.add(origin.offset(this.moveDirection.getOpposite(), j));
/* 131 */       i1++;
/*     */     } 
/*     */     
/* 134 */     int j1 = 1;
/*     */ 
/*     */     
/*     */     while (true) {
/* 138 */       BlockPos blockpos1 = origin.offset(this.moveDirection, j1);
/* 139 */       int k = this.toMove.indexOf(blockpos1);
/*     */       
/* 141 */       if (k > -1) {
/*     */         
/* 143 */         func_177255_a(i1, k);
/*     */         
/* 145 */         for (int l = 0; l <= k + i1; l++) {
/*     */           
/* 147 */           BlockPos blockpos2 = this.toMove.get(l);
/*     */           
/* 149 */           if (this.world.getBlockState(blockpos2).getBlock() == Blocks.slime_block && !func_177250_b(blockpos2))
/*     */           {
/* 151 */             return false;
/*     */           }
/*     */         } 
/*     */         
/* 155 */         return true;
/*     */       } 
/*     */       
/* 158 */       block = this.world.getBlockState(blockpos1).getBlock();
/*     */       
/* 160 */       if (block.getMaterial() == Material.air)
/*     */       {
/* 162 */         return true;
/*     */       }
/*     */       
/* 165 */       if (!BlockPistonBase.canPush(block, this.world, blockpos1, this.moveDirection, true) || blockpos1.equals(this.pistonPos))
/*     */       {
/* 167 */         return false;
/*     */       }
/*     */       
/* 170 */       if (block.getMobilityFlag() == 1) {
/*     */         
/* 172 */         this.toDestroy.add(blockpos1);
/* 173 */         return true;
/*     */       } 
/*     */       
/* 176 */       if (this.toMove.size() >= 12)
/*     */       {
/* 178 */         return false;
/*     */       }
/*     */       
/* 181 */       this.toMove.add(blockpos1);
/* 182 */       i1++;
/* 183 */       j1++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_177255_a(int p_177255_1_, int p_177255_2_) {
/* 191 */     List<BlockPos> list = Lists.newArrayList();
/* 192 */     List<BlockPos> list1 = Lists.newArrayList();
/* 193 */     List<BlockPos> list2 = Lists.newArrayList();
/* 194 */     list.addAll(this.toMove.subList(0, p_177255_2_));
/* 195 */     list1.addAll(this.toMove.subList(this.toMove.size() - p_177255_1_, this.toMove.size()));
/* 196 */     list2.addAll(this.toMove.subList(p_177255_2_, this.toMove.size() - p_177255_1_));
/* 197 */     this.toMove.clear();
/* 198 */     this.toMove.addAll(list);
/* 199 */     this.toMove.addAll(list1);
/* 200 */     this.toMove.addAll(list2);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_177250_b(BlockPos p_177250_1_) {
/* 205 */     for (EnumFacing enumfacing : EnumFacing.values()) {
/*     */       
/* 207 */       if (enumfacing.getAxis() != this.moveDirection.getAxis() && !func_177251_a(p_177250_1_.offset(enumfacing)))
/*     */       {
/* 209 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 213 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BlockPos> getBlocksToMove() {
/* 218 */     return this.toMove;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BlockPos> getBlocksToDestroy() {
/* 223 */     return this.toDestroy;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\state\BlockPistonStructureHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */