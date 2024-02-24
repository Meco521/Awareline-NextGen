/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.WeightedRandomChestContent;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class WorldGenDungeons
/*     */   extends WorldGenerator
/*     */ {
/*  22 */   private static final Logger field_175918_a = LogManager.getLogger();
/*  23 */   private static final String[] SPAWNERTYPES = new String[] { "Skeleton", "Zombie", "Zombie", "Spider" };
/*  24 */   private static final List<WeightedRandomChestContent> CHESTCONTENT = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 4, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 1, 10), new WeightedRandomChestContent(Items.wheat, 0, 1, 4, 10), new WeightedRandomChestContent(Items.gunpowder, 0, 1, 4, 10), new WeightedRandomChestContent(Items.string, 0, 1, 4, 10), new WeightedRandomChestContent(Items.bucket, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1), new WeightedRandomChestContent(Items.redstone, 0, 1, 4, 10), new WeightedRandomChestContent(Items.record_13, 0, 1, 1, 4), new WeightedRandomChestContent(Items.record_cat, 0, 1, 1, 4), new WeightedRandomChestContent(Items.name_tag, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 2), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) });
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  28 */     int i = 3;
/*  29 */     int j = rand.nextInt(2) + 2;
/*  30 */     int k = -j - 1;
/*  31 */     int l = j + 1;
/*  32 */     int i1 = -1;
/*  33 */     int j1 = 4;
/*  34 */     int k1 = rand.nextInt(2) + 2;
/*  35 */     int l1 = -k1 - 1;
/*  36 */     int i2 = k1 + 1;
/*  37 */     int j2 = 0;
/*     */     
/*  39 */     for (int k2 = k; k2 <= l; k2++) {
/*     */       
/*  41 */       for (int l2 = -1; l2 <= 4; l2++) {
/*     */         
/*  43 */         for (int i3 = l1; i3 <= i2; i3++) {
/*     */           
/*  45 */           BlockPos blockpos = position.add(k2, l2, i3);
/*  46 */           Material material = worldIn.getBlockState(blockpos).getBlock().getMaterial();
/*  47 */           boolean flag = material.isSolid();
/*     */           
/*  49 */           if (l2 == -1 && !flag)
/*     */           {
/*  51 */             return false;
/*     */           }
/*     */           
/*  54 */           if (l2 == 4 && !flag)
/*     */           {
/*  56 */             return false;
/*     */           }
/*     */           
/*  59 */           if ((k2 == k || k2 == l || i3 == l1 || i3 == i2) && l2 == 0 && worldIn.isAirBlock(blockpos) && worldIn.isAirBlock(blockpos.up()))
/*     */           {
/*  61 */             j2++;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  67 */     if (j2 >= 1 && j2 <= 5) {
/*     */       
/*  69 */       for (int k3 = k; k3 <= l; k3++) {
/*     */         
/*  71 */         for (int i4 = 3; i4 >= -1; i4--) {
/*     */           
/*  73 */           for (int k4 = l1; k4 <= i2; k4++) {
/*     */             
/*  75 */             BlockPos blockpos1 = position.add(k3, i4, k4);
/*     */             
/*  77 */             if (k3 != k && i4 != -1 && k4 != l1 && k3 != l && i4 != 4 && k4 != i2) {
/*     */               
/*  79 */               if (worldIn.getBlockState(blockpos1).getBlock() != Blocks.chest)
/*     */               {
/*  81 */                 worldIn.setBlockToAir(blockpos1);
/*     */               }
/*     */             }
/*  84 */             else if (blockpos1.getY() >= 0 && !worldIn.getBlockState(blockpos1.down()).getBlock().getMaterial().isSolid()) {
/*     */               
/*  86 */               worldIn.setBlockToAir(blockpos1);
/*     */             }
/*  88 */             else if (worldIn.getBlockState(blockpos1).getBlock().getMaterial().isSolid() && worldIn.getBlockState(blockpos1).getBlock() != Blocks.chest) {
/*     */               
/*  90 */               if (i4 == -1 && rand.nextInt(4) != 0) {
/*     */                 
/*  92 */                 worldIn.setBlockState(blockpos1, Blocks.mossy_cobblestone.getDefaultState(), 2);
/*     */               }
/*     */               else {
/*     */                 
/*  96 */                 worldIn.setBlockState(blockpos1, Blocks.cobblestone.getDefaultState(), 2);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 103 */       for (int l3 = 0; l3 < 2; l3++) {
/*     */         
/* 105 */         for (int j4 = 0; j4 < 3; j4++) {
/*     */           
/* 107 */           int l4 = position.getX() + rand.nextInt((j << 1) + 1) - j;
/* 108 */           int i5 = position.getY();
/* 109 */           int j5 = position.getZ() + rand.nextInt((k1 << 1) + 1) - k1;
/* 110 */           BlockPos blockpos2 = new BlockPos(l4, i5, j5);
/*     */           
/* 112 */           if (worldIn.isAirBlock(blockpos2)) {
/*     */             
/* 114 */             int j3 = 0;
/*     */             
/* 116 */             for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */               
/* 118 */               if (worldIn.getBlockState(blockpos2.offset(enumfacing)).getBlock().getMaterial().isSolid())
/*     */               {
/* 120 */                 j3++;
/*     */               }
/*     */             } 
/*     */             
/* 124 */             if (j3 == 1) {
/*     */               
/* 126 */               worldIn.setBlockState(blockpos2, Blocks.chest.correctFacing(worldIn, blockpos2, Blocks.chest.getDefaultState()), 2);
/* 127 */               List<WeightedRandomChestContent> list = WeightedRandomChestContent.func_177629_a(CHESTCONTENT, new WeightedRandomChestContent[] { Items.enchanted_book.getRandom(rand) });
/* 128 */               TileEntity tileentity1 = worldIn.getTileEntity(blockpos2);
/*     */               
/* 130 */               if (tileentity1 instanceof net.minecraft.tileentity.TileEntityChest)
/*     */               {
/* 132 */                 WeightedRandomChestContent.generateChestContents(rand, list, (IInventory)tileentity1, 8);
/*     */               }
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 141 */       worldIn.setBlockState(position, Blocks.mob_spawner.getDefaultState(), 2);
/* 142 */       TileEntity tileentity = worldIn.getTileEntity(position);
/*     */       
/* 144 */       if (tileentity instanceof TileEntityMobSpawner) {
/*     */         
/* 146 */         ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityName(pickMobSpawner(rand));
/*     */       }
/*     */       else {
/*     */         
/* 150 */         field_175918_a.error("Failed to fetch mob spawner entity at (" + position.getX() + ", " + position.getY() + ", " + position.getZ() + ")");
/*     */       } 
/*     */       
/* 153 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 157 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String pickMobSpawner(Random p_76543_1_) {
/* 166 */     return SPAWNERTYPES[p_76543_1_.nextInt(SPAWNERTYPES.length)];
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenDungeons.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */