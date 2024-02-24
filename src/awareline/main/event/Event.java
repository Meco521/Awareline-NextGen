/*    */ package awareline.main.event;
/*    */ 
/*    */ public abstract class Event
/*    */ {
/*    */   private boolean cancelled;
/*    */   public byte type;
/*    */   
/*    */   public boolean isCancelled() {
/*  9 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancelled) {
/* 13 */     this.cancelled = cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled() {
/* 17 */     this.cancelled = true;
/*    */   }
/*    */   
/*    */   public byte getType() {
/* 21 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(byte type) {
/* 25 */     this.type = type;
/*    */   }
/*    */   
/*    */   public void cancelEvent() {
/* 29 */     this.cancelled = true;
/*    */   }
/*    */   
/*    */   public void stopCancelEvent() {
/* 33 */     this.cancelled = false;
/*    */   }
/*    */   
/*    */   public void cancel() {
/* 37 */     this.cancelled = true;
/*    */   }
/*    */   
/*    */   public enum State {
/* 41 */     PRE,
/* 42 */     POST;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\Event.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */