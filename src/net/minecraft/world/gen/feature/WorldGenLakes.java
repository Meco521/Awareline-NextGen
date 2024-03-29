/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ 
/*     */ 
/*     */ public class WorldGenLakes
/*     */   extends WorldGenerator
/*     */ {
/*     */   private final Block block;
/*     */   
/*     */   public WorldGenLakes(Block blockIn) {
/*  19 */     this.block = blockIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  24 */     for (position = position.add(-8, 0, -8); position.getY() > 5 && worldIn.isAirBlock(position); position = position.down());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  29 */     if (position.getY() <= 4)
/*     */     {
/*  31 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  35 */     position = position.down(4);
/*  36 */     boolean[] aboolean = new boolean[2048];
/*  37 */     int i = rand.nextInt(4) + 4;
/*     */     
/*  39 */     for (int j = 0; j < i; j++) {
/*     */       
/*  41 */       double d0 = rand.nextDouble() * 6.0D + 3.0D;
/*  42 */       double d1 = rand.nextDouble() * 4.0D + 2.0D;
/*  43 */       double d2 = rand.nextDouble() * 6.0D + 3.0D;
/*  44 */       double d3 = rand.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
/*  45 */       double d4 = rand.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
/*  46 */       double d5 = rand.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;
/*     */       
/*  48 */       for (int l = 1; l < 15; l++) {
/*     */         
/*  50 */         for (int i1 = 1; i1 < 15; i1++) {
/*     */           
/*  52 */           for (int j1 = 1; j1 < 7; j1++) {
/*     */             
/*  54 */             double d6 = (l - d3) / d0 / 2.0D;
/*  55 */             double d7 = (j1 - d4) / d1 / 2.0D;
/*  56 */             double d8 = (i1 - d5) / d2 / 2.0D;
/*  57 */             double d9 = d6 * d6 + d7 * d7 + d8 * d8;
/*     */             
/*  59 */             if (d9 < 1.0D)
/*     */             {
/*  61 */               aboolean[((l << 4) + i1 << 3) + j1] = true;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  68 */     for (int k1 = 0; k1 < 16; k1++) {
/*     */       
/*  70 */       for (int l2 = 0; l2 < 16; l2++) {
/*     */         
/*  72 */         for (int k = 0; k < 8; k++) {
/*     */           
/*  74 */           boolean flag = (!aboolean[((k1 << 4) + l2 << 3) + k] && ((k1 < 15 && aboolean[((k1 + 1 << 4) + l2 << 3) + k]) || (k1 > 0 && aboolean[((k1 - 1 << 4) + l2 << 3) + k]) || (l2 < 15 && aboolean[((k1 << 4) + l2 + 1 << 3) + k]) || (l2 > 0 && aboolean[((k1 << 4) + l2 - 1 << 3) + k]) || (k < 7 && aboolean[((k1 << 4) + l2 << 3) + k + 1]) || (k > 0 && aboolean[((k1 << 4) + l2 << 3) + k - 1])));
/*     */           
/*  76 */           if (flag) {
/*     */             
/*  78 */             Material material = worldIn.getBlockState(position.add(k1, k, l2)).getBlock().getMaterial();
/*     */             
/*  80 */             if (k >= 4 && material.isLiquid())
/*     */             {
/*  82 */               return false;
/*     */             }
/*     */             
/*  85 */             if (k < 4 && !material.isSolid() && worldIn.getBlockState(position.add(k1, k, l2)).getBlock() != this.block)
/*     */             {
/*  87 */               return false;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     for (int l1 = 0; l1 < 16; l1++) {
/*     */       
/*  96 */       for (int i3 = 0; i3 < 16; i3++) {
/*     */         
/*  98 */         for (int i4 = 0; i4 < 8; i4++) {
/*     */           
/* 100 */           if (aboolean[((l1 << 4) + i3 << 3) + i4])
/*     */           {
/* 102 */             worldIn.setBlockState(position.add(l1, i4, i3), (i4 >= 4) ? Blocks.air.getDefaultState() : this.block.getDefaultState(), 2);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 108 */     for (int i2 = 0; i2 < 16; i2++) {
/*     */       
/* 110 */       for (int j3 = 0; j3 < 16; j3++) {
/*     */         
/* 112 */         for (int j4 = 4; j4 < 8; j4++) {
/*     */           
/* 114 */           if (aboolean[((i2 << 4) + j3 << 3) + j4]) {
/*     */             
/* 116 */             BlockPos blockpos = position.add(i2, j4 - 1, j3);
/*     */             
/* 118 */             if (worldIn.getBlockState(blockpos).getBlock() == Blocks.dirt && worldIn.getLightFor(EnumSkyBlock.SKY, position.add(i2, j4, j3)) > 0) {
/*     */               
/* 120 */               BiomeGenBase biomegenbase = worldIn.getBiomeGenForCoords(blockpos);
/*     */               
/* 122 */               if (biomegenbase.topBlock.getBlock() == Blocks.mycelium) {
/*     */                 
/* 124 */                 worldIn.setBlockState(blockpos, Blocks.mycelium.getDefaultState(), 2);
/*     */               }
/*     */               else {
/*     */                 
/* 128 */                 worldIn.setBlockState(blockpos, Blocks.grass.getDefaultState(), 2);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 136 */     if (this.block.getMaterial() == Material.lava)
/*     */     {
/* 138 */       for (int j2 = 0; j2 < 16; j2++) {
/*     */         
/* 140 */         for (int k3 = 0; k3 < 16; k3++) {
/*     */           
/* 142 */           for (int k4 = 0; k4 < 8; k4++) {
/*     */             
/* 144 */             boolean flag1 = (!aboolean[((j2 << 4) + k3 << 3) + k4] && ((j2 < 15 && aboolean[((j2 + 1 << 4) + k3 << 3) + k4]) || (j2 > 0 && aboolean[((j2 - 1 << 4) + k3 << 3) + k4]) || (k3 < 15 && aboolean[((j2 << 4) + k3 + 1 << 3) + k4]) || (k3 > 0 && aboolean[((j2 << 4) + k3 - 1 << 3) + k4]) || (k4 < 7 && aboolean[((j2 << 4) + k3 << 3) + k4 + 1]) || (k4 > 0 && aboolean[((j2 << 4) + k3 << 3) + k4 - 1])));
/*     */             
/* 146 */             if (flag1 && (k4 < 4 || rand.nextInt(2) != 0) && worldIn.getBlockState(position.add(j2, k4, k3)).getBlock().getMaterial().isSolid())
/*     */             {
/* 148 */               worldIn.setBlockState(position.add(j2, k4, k3), Blocks.stone.getDefaultState(), 2);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 155 */     if (this.block.getMaterial() == Material.water)
/*     */     {
/* 157 */       for (int k2 = 0; k2 < 16; k2++) {
/*     */         
/* 159 */         for (int l3 = 0; l3 < 16; l3++) {
/*     */           
/* 161 */           int l4 = 4;
/*     */           
/* 163 */           if (worldIn.canBlockFreezeWater(position.add(k2, l4, l3)))
/*     */           {
/* 165 */             worldIn.setBlockState(position.add(k2, l4, l3), Blocks.ice.getDefaultState(), 2);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 171 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenLakes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */