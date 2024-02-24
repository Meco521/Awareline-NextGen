/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockEventData
/*    */ {
/*    */   private final BlockPos position;
/*    */   private final Block blockType;
/*    */   private final int eventID;
/*    */   private final int eventParameter;
/*    */   
/*    */   public BlockEventData(BlockPos pos, Block blockType, int eventId, int p_i45756_4_) {
/* 16 */     this.position = pos;
/* 17 */     this.eventID = eventId;
/* 18 */     this.eventParameter = p_i45756_4_;
/* 19 */     this.blockType = blockType;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getPosition() {
/* 24 */     return this.position;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEventID() {
/* 32 */     return this.eventID;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEventParameter() {
/* 37 */     return this.eventParameter;
/*    */   }
/*    */ 
/*    */   
/*    */   public Block getBlock() {
/* 42 */     return this.blockType;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 47 */     if (!(p_equals_1_ instanceof BlockEventData))
/*    */     {
/* 49 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 53 */     BlockEventData blockeventdata = (BlockEventData)p_equals_1_;
/* 54 */     return (this.position.equals(blockeventdata.position) && this.eventID == blockeventdata.eventID && this.eventParameter == blockeventdata.eventParameter && this.blockType == blockeventdata.blockType);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 60 */     return "TE(" + this.position + ")," + this.eventID + "," + this.eventParameter + "," + this.blockType;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockEventData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */