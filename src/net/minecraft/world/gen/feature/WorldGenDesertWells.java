/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockSand;
/*     */ import net.minecraft.block.BlockSlab;
/*     */ import net.minecraft.block.BlockStoneSlab;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.block.state.pattern.BlockStateHelper;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenDesertWells extends WorldGenerator {
/*  18 */   private static final BlockStateHelper field_175913_a = BlockStateHelper.forBlock((Block)Blocks.sand).where((IProperty)BlockSand.VARIANT, Predicates.equalTo(BlockSand.EnumType.SAND));
/*  19 */   private final IBlockState field_175911_b = Blocks.stone_slab.getDefaultState().withProperty((IProperty)BlockStoneSlab.VARIANT, (Comparable)BlockStoneSlab.EnumType.SAND).withProperty((IProperty)BlockSlab.HALF, (Comparable)BlockSlab.EnumBlockHalf.BOTTOM);
/*  20 */   private final IBlockState field_175912_c = Blocks.sandstone.getDefaultState();
/*  21 */   private final IBlockState field_175910_d = Blocks.flowing_water.getDefaultState();
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  25 */     while (worldIn.isAirBlock(position) && position.getY() > 2)
/*     */     {
/*  27 */       position = position.down();
/*     */     }
/*     */     
/*  30 */     if (!field_175913_a.apply(worldIn.getBlockState(position)))
/*     */     {
/*  32 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  36 */     for (int i = -2; i <= 2; i++) {
/*     */       
/*  38 */       for (int j = -2; j <= 2; j++) {
/*     */         
/*  40 */         if (worldIn.isAirBlock(position.add(i, -1, j)) && worldIn.isAirBlock(position.add(i, -2, j)))
/*     */         {
/*  42 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  47 */     for (int l = -1; l <= 0; l++) {
/*     */       
/*  49 */       for (int l1 = -2; l1 <= 2; l1++) {
/*     */         
/*  51 */         for (int k = -2; k <= 2; k++)
/*     */         {
/*  53 */           worldIn.setBlockState(position.add(l1, l, k), this.field_175912_c, 2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  58 */     worldIn.setBlockState(position, this.field_175910_d, 2);
/*     */     
/*  60 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
/*     */     {
/*  62 */       worldIn.setBlockState(position.offset(enumfacing), this.field_175910_d, 2);
/*     */     }
/*     */     
/*  65 */     for (int i1 = -2; i1 <= 2; i1++) {
/*     */       
/*  67 */       for (int i2 = -2; i2 <= 2; i2++) {
/*     */         
/*  69 */         if (i1 == -2 || i1 == 2 || i2 == -2 || i2 == 2)
/*     */         {
/*  71 */           worldIn.setBlockState(position.add(i1, 1, i2), this.field_175912_c, 2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     worldIn.setBlockState(position.add(2, 1, 0), this.field_175911_b, 2);
/*  77 */     worldIn.setBlockState(position.add(-2, 1, 0), this.field_175911_b, 2);
/*  78 */     worldIn.setBlockState(position.add(0, 1, 2), this.field_175911_b, 2);
/*  79 */     worldIn.setBlockState(position.add(0, 1, -2), this.field_175911_b, 2);
/*     */     
/*  81 */     for (int j1 = -1; j1 <= 1; j1++) {
/*     */       
/*  83 */       for (int j2 = -1; j2 <= 1; j2++) {
/*     */         
/*  85 */         if (j1 == 0 && j2 == 0) {
/*     */           
/*  87 */           worldIn.setBlockState(position.add(j1, 4, j2), this.field_175912_c, 2);
/*     */         }
/*     */         else {
/*     */           
/*  91 */           worldIn.setBlockState(position.add(j1, 4, j2), this.field_175911_b, 2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  96 */     for (int k1 = 1; k1 <= 3; k1++) {
/*     */       
/*  98 */       worldIn.setBlockState(position.add(-1, k1, -1), this.field_175912_c, 2);
/*  99 */       worldIn.setBlockState(position.add(-1, k1, 1), this.field_175912_c, 2);
/* 100 */       worldIn.setBlockState(position.add(1, k1, -1), this.field_175912_c, 2);
/* 101 */       worldIn.setBlockState(position.add(1, k1, 1), this.field_175912_c, 2);
/*     */     } 
/*     */     
/* 104 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenDesertWells.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */