/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WorldGenerator
/*    */ {
/*    */   private final boolean doBlockNotify;
/*    */   
/*    */   public WorldGenerator() {
/* 19 */     this(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldGenerator(boolean notify) {
/* 24 */     this.doBlockNotify = notify;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract boolean generate(World paramWorld, Random paramRandom, BlockPos paramBlockPos);
/*    */ 
/*    */   
/*    */   public void func_175904_e() {}
/*    */ 
/*    */   
/*    */   protected void setBlockAndNotifyAdequately(World worldIn, BlockPos pos, IBlockState state) {
/* 35 */     if (this.doBlockNotify) {
/*    */       
/* 37 */       worldIn.setBlockState(pos, state, 3);
/*    */     }
/*    */     else {
/*    */       
/* 41 */       worldIn.setBlockState(pos, state, 2);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\feature\WorldGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */