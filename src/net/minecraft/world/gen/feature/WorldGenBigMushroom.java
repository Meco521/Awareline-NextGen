/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockHugeMushroom;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class WorldGenBigMushroom
/*     */   extends WorldGenerator
/*     */ {
/*     */   private Block mushroomType;
/*     */   
/*     */   public WorldGenBigMushroom(Block p_i46449_1_) {
/*  19 */     super(true);
/*  20 */     this.mushroomType = p_i46449_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldGenBigMushroom() {
/*  25 */     super(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  30 */     if (this.mushroomType == null)
/*     */     {
/*  32 */       this.mushroomType = rand.nextBoolean() ? Blocks.brown_mushroom_block : Blocks.red_mushroom_block;
/*     */     }
/*     */     
/*  35 */     int i = rand.nextInt(3) + 4;
/*  36 */     boolean flag = true;
/*     */     
/*  38 */     if (position.getY() >= 1 && position.getY() + i + 1 < 256) {
/*     */       
/*  40 */       for (int j = position.getY(); j <= position.getY() + 1 + i; j++) {
/*     */         
/*  42 */         int k = 3;
/*     */         
/*  44 */         if (j <= position.getY() + 3)
/*     */         {
/*  46 */           k = 0;
/*     */         }
/*     */         
/*  49 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */         
/*  51 */         for (int l = position.getX() - k; l <= position.getX() + k && flag; l++) {
/*     */           
/*  53 */           for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; i1++) {
/*     */             
/*  55 */             if (j >= 0 && j < 256) {
/*     */               
/*  57 */               Block block = worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(l, j, i1)).getBlock();
/*     */               
/*  59 */               if (block.getMaterial() != Material.air && block.getMaterial() != Material.leaves)
/*     */               {
/*  61 */                 flag = false;
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/*  66 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  72 */       if (!flag)
/*     */       {
/*  74 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  78 */       Block block1 = worldIn.getBlockState(position.down()).getBlock();
/*     */       
/*  80 */       if (block1 != Blocks.dirt && block1 != Blocks.grass && block1 != Blocks.mycelium)
/*     */       {
/*  82 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  86 */       int k2 = position.getY() + i;
/*     */       
/*  88 */       if (this.mushroomType == Blocks.red_mushroom_block)
/*     */       {
/*  90 */         k2 = position.getY() + i - 3;
/*     */       }
/*     */       
/*  93 */       for (int l2 = k2; l2 <= position.getY() + i; l2++) {
/*     */         
/*  95 */         int j3 = 1;
/*     */         
/*  97 */         if (l2 < position.getY() + i)
/*     */         {
/*  99 */           j3++;
/*     */         }
/*     */         
/* 102 */         if (this.mushroomType == Blocks.brown_mushroom_block)
/*     */         {
/* 104 */           j3 = 3;
/*     */         }
/*     */         
/* 107 */         int k3 = position.getX() - j3;
/* 108 */         int l3 = position.getX() + j3;
/* 109 */         int j1 = position.getZ() - j3;
/* 110 */         int k1 = position.getZ() + j3;
/*     */         
/* 112 */         for (int l1 = k3; l1 <= l3; l1++) {
/*     */           
/* 114 */           for (int i2 = j1; i2 <= k1; i2++) {
/*     */             
/* 116 */             int j2 = 5;
/*     */             
/* 118 */             if (l1 == k3) {
/*     */               
/* 120 */               j2--;
/*     */             }
/* 122 */             else if (l1 == l3) {
/*     */               
/* 124 */               j2++;
/*     */             } 
/*     */             
/* 127 */             if (i2 == j1) {
/*     */               
/* 129 */               j2 -= 3;
/*     */             }
/* 131 */             else if (i2 == k1) {
/*     */               
/* 133 */               j2 += 3;
/*     */             } 
/*     */             
/* 136 */             BlockHugeMushroom.EnumType blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.byMetadata(j2);
/*     */             
/* 138 */             if (this.mushroomType == Blocks.brown_mushroom_block || l2 < position.getY() + i) {
/*     */               
/* 140 */               if ((l1 == k3 || l1 == l3) && (i2 == j1 || i2 == k1)) {
/*     */                 continue;
/*     */               }
/*     */ 
/*     */               
/* 145 */               if (l1 == position.getX() - j3 - 1 && i2 == j1)
/*     */               {
/* 147 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_WEST;
/*     */               }
/*     */               
/* 150 */               if (l1 == k3 && i2 == position.getZ() - j3 - 1)
/*     */               {
/* 152 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_WEST;
/*     */               }
/*     */               
/* 155 */               if (l1 == position.getX() + j3 - 1 && i2 == j1)
/*     */               {
/* 157 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_EAST;
/*     */               }
/*     */               
/* 160 */               if (l1 == l3 && i2 == position.getZ() - j3 - 1)
/*     */               {
/* 162 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_EAST;
/*     */               }
/*     */               
/* 165 */               if (l1 == position.getX() - j3 - 1 && i2 == k1)
/*     */               {
/* 167 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_WEST;
/*     */               }
/*     */               
/* 170 */               if (l1 == k3 && i2 == position.getZ() + j3 - 1)
/*     */               {
/* 172 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_WEST;
/*     */               }
/*     */               
/* 175 */               if (l1 == position.getX() + j3 - 1 && i2 == k1)
/*     */               {
/* 177 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_EAST;
/*     */               }
/*     */               
/* 180 */               if (l1 == l3 && i2 == position.getZ() + j3 - 1)
/*     */               {
/* 182 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_EAST;
/*     */               }
/*     */             } 
/*     */             
/* 186 */             if (blockhugemushroom$enumtype == BlockHugeMushroom.EnumType.CENTER && l2 < position.getY() + i)
/*     */             {
/* 188 */               blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.ALL_INSIDE;
/*     */             }
/*     */             
/* 191 */             if (position.getY() >= position.getY() + i - 1 || blockhugemushroom$enumtype != BlockHugeMushroom.EnumType.ALL_INSIDE) {
/*     */               
/* 193 */               BlockPos blockpos = new BlockPos(l1, l2, i2);
/*     */               
/* 195 */               if (!worldIn.getBlockState(blockpos).getBlock().isFullBlock())
/*     */               {
/* 197 */                 setBlockAndNotifyAdequately(worldIn, blockpos, this.mushroomType.getDefaultState().withProperty((IProperty)BlockHugeMushroom.VARIANT, (Comparable)blockhugemushroom$enumtype));
/*     */               }
/*     */             } 
/*     */             continue;
/*     */           } 
/*     */         } 
/*     */       } 
/* 204 */       for (int i3 = 0; i3 < i; i3++) {
/*     */         
/* 206 */         Block block2 = worldIn.getBlockState(position.up(i3)).getBlock();
/*     */         
/* 208 */         if (!block2.isFullBlock())
/*     */         {
/* 210 */           setBlockAndNotifyAdequately(worldIn, position.up(i3), this.mushroomType.getDefaultState().withProperty((IProperty)BlockHugeMushroom.VARIANT, (Comparable)BlockHugeMushroom.EnumType.STEM));
/*     */         }
/*     */       } 
/*     */       
/* 214 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 220 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenBigMushroom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */