/*    */ package awareline.main.event.events.misc;
/*    */ 
/*    */ import awareline.main.event.Event;
/*    */ 
/*    */ public class EventKey
/*    */   extends Event {
/*    */   public int getKey() {
/*  8 */     return this.key;
/*    */   } private int key;
/*    */   public EventKey(int key) {
/* 11 */     this.key = key;
/*    */   }
/*    */   
/*    */   public void setKey(int key) {
/* 15 */     this.key = key;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\misc\EventKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */