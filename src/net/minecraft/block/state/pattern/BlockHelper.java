/*    */ package net.minecraft.block.state.pattern;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ 
/*    */ public class BlockHelper
/*    */   implements Predicate<IBlockState>
/*    */ {
/*    */   private final Block block;
/*    */   
/*    */   private BlockHelper(Block blockType) {
/* 13 */     this.block = blockType;
/*    */   }
/*    */ 
/*    */   
/*    */   public static BlockHelper forBlock(Block blockType) {
/* 18 */     return new BlockHelper(blockType);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean apply(IBlockState p_apply_1_) {
/* 23 */     return (p_apply_1_ != null && p_apply_1_.getBlock() == this.block);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\state\pattern\BlockHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */