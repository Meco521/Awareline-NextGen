/*    */ package awareline.main.event.events.world;
/*    */ 
/*    */ import awareline.main.event.Event;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ public final class BlockBreakEvent
/*    */   extends Event {
/*    */   private final BlockPos blockPos;
/*    */   
/*    */   public BlockPos getBlockPos() {
/* 11 */     return this.blockPos;
/*    */   }
/*    */   public BlockBreakEvent(BlockPos blockPos) {
/* 14 */     this.blockPos = blockPos;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\world\BlockBreakEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */