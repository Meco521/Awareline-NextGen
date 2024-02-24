/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.IdentityHashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockAir
/*    */   extends Block
/*    */ {
/* 14 */   private static final Map mapOriginalOpacity = new IdentityHashMap<>();
/*    */ 
/*    */   
/*    */   protected BlockAir() {
/* 18 */     super(Material.air);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRenderType() {
/* 26 */     return -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 31 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOpaqueCube() {
/* 39 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isReplaceable(World worldIn, BlockPos pos) {
/* 59 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void setLightOpacity(Block p_setLightOpacity_0_, int p_setLightOpacity_1_) {
/* 64 */     if (!mapOriginalOpacity.containsKey(p_setLightOpacity_0_))
/*    */     {
/* 66 */       mapOriginalOpacity.put(p_setLightOpacity_0_, Integer.valueOf(p_setLightOpacity_0_.lightOpacity));
/*    */     }
/*    */     
/* 69 */     p_setLightOpacity_0_.lightOpacity = p_setLightOpacity_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void restoreLightOpacity(Block p_restoreLightOpacity_0_) {
/* 74 */     if (mapOriginalOpacity.containsKey(p_restoreLightOpacity_0_)) {
/*    */       
/* 76 */       int i = ((Integer)mapOriginalOpacity.get(p_restoreLightOpacity_0_)).intValue();
/* 77 */       setLightOpacity(p_restoreLightOpacity_0_, i);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockAir.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */