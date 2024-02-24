/*    */ package awareline.main.event.events.key;
/*    */ 
/*    */ import awareline.main.event.Event;
/*    */ 
/*    */ public class KeyPressEvent extends Event {
/*    */   private final int key;
/*    */   
/*    */   public int getKey() {
/*  9 */     return this.key;
/*    */   }
/*    */   public KeyPressEvent(int key) {
/* 12 */     this.key = key;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\key\KeyPressEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */