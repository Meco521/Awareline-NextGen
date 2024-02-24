/*    */ package awareline.main.utility.object;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ public class WorldBlockObject {
/*    */   private final Block block;
/*    */   
/*  9 */   public Block getBlock() { return this.block; } private final BlockPos blockPos; private final int blockID; public BlockPos getBlockPos() {
/* 10 */     return this.blockPos; } public int getBlockID() {
/* 11 */     return this.blockID;
/*    */   }
/*    */   public WorldBlockObject(Block block, BlockPos blockPos) {
/* 14 */     this.block = block;
/* 15 */     this.blockPos = blockPos;
/* 16 */     this.blockID = Block.getIdFromBlock(block);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\object\WorldBlockObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */