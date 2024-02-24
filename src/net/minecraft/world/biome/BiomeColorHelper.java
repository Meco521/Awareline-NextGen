/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ public class BiomeColorHelper
/*    */ {
/*  8 */   private static final ColorResolver GRASS_COLOR = new ColorResolver()
/*    */     {
/*    */       public int getColorAtPos(BiomeGenBase biome, BlockPos blockPosition)
/*    */       {
/* 12 */         return biome.getGrassColorAtPos(blockPosition);
/*    */       }
/*    */     };
/* 15 */   private static final ColorResolver FOLIAGE_COLOR = new ColorResolver()
/*    */     {
/*    */       public int getColorAtPos(BiomeGenBase biome, BlockPos blockPosition)
/*    */       {
/* 19 */         return biome.getFoliageColorAtPos(blockPosition);
/*    */       }
/*    */     };
/* 22 */   private static final ColorResolver WATER_COLOR_MULTIPLIER = new ColorResolver()
/*    */     {
/*    */       public int getColorAtPos(BiomeGenBase biome, BlockPos blockPosition)
/*    */       {
/* 26 */         return biome.waterColorMultiplier;
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   private static int getColorAtPos(IBlockAccess blockAccess, BlockPos pos, ColorResolver colorResolver) {
/* 32 */     int i = 0;
/* 33 */     int j = 0;
/* 34 */     int k = 0;
/*    */     
/* 36 */     for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-1, 0, -1), pos.add(1, 0, 1))) {
/*    */       
/* 38 */       int l = colorResolver.getColorAtPos(blockAccess.getBiomeGenForCoords((BlockPos)blockpos$mutableblockpos), (BlockPos)blockpos$mutableblockpos);
/* 39 */       i += (l & 0xFF0000) >> 16;
/* 40 */       j += (l & 0xFF00) >> 8;
/* 41 */       k += l & 0xFF;
/*    */     } 
/*    */     
/* 44 */     return (i / 9 & 0xFF) << 16 | (j / 9 & 0xFF) << 8 | k / 9 & 0xFF;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getGrassColorAtPos(IBlockAccess p_180286_0_, BlockPos p_180286_1_) {
/* 49 */     return getColorAtPos(p_180286_0_, p_180286_1_, GRASS_COLOR);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getFoliageColorAtPos(IBlockAccess p_180287_0_, BlockPos p_180287_1_) {
/* 54 */     return getColorAtPos(p_180287_0_, p_180287_1_, FOLIAGE_COLOR);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getWaterColorAtPos(IBlockAccess p_180288_0_, BlockPos p_180288_1_) {
/* 59 */     return getColorAtPos(p_180288_0_, p_180288_1_, WATER_COLOR_MULTIPLIER);
/*    */   }
/*    */   
/*    */   static interface ColorResolver {
/*    */     int getColorAtPos(BiomeGenBase param1BiomeGenBase, BlockPos param1BlockPos);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\biome\BiomeColorHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */