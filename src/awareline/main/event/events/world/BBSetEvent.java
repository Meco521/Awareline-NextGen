/*    */ package awareline.main.event.events.world;
/*    */ 
/*    */ import awareline.main.event.Event;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ public class BBSetEvent extends Event {
/*    */   public Block block;
/*    */   public BlockPos pos;
/*    */   
/* 13 */   public Block getBlock() { return this.block; } public AxisAlignedBB boundingBox; public final List<AxisAlignedBB> boxes; public BlockPos getPos() {
/* 14 */     return this.pos; }
/* 15 */   public AxisAlignedBB getBoundingBox() { return this.boundingBox; } public List<AxisAlignedBB> getBoxes() {
/* 16 */     return this.boxes;
/*    */   }
/*    */   public BBSetEvent(Block block, BlockPos pos, AxisAlignedBB boundingBox, List<AxisAlignedBB> boxes) {
/* 19 */     this.block = block;
/* 20 */     this.pos = pos;
/* 21 */     this.boundingBox = boundingBox;
/* 22 */     this.boxes = boxes;
/*    */   }
/*    */   
/*    */   public void setBlock(Block block) {
/* 26 */     this.block = block;
/*    */   }
/*    */   
/*    */   public void setPos(BlockPos pos) {
/* 30 */     this.pos = pos;
/*    */   }
/*    */   
/*    */   public void setBoundingBox(AxisAlignedBB boundingBox) {
/* 34 */     this.boundingBox = boundingBox;
/*    */   }
/*    */   
/*    */   public BlockPos getBlockPos() {
/* 38 */     return this.pos;
/*    */   }
/*    */   
/*    */   public int getX() {
/* 42 */     return this.pos.getX();
/*    */   }
/*    */   
/*    */   public int getY() {
/* 46 */     return this.pos.getY();
/*    */   }
/*    */   
/*    */   public int getZ() {
/* 50 */     return this.pos.getZ();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\world\BBSetEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */