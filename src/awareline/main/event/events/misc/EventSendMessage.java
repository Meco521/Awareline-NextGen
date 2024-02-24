/*    */ package awareline.main.event.events.misc;
/*    */ 
/*    */ import awareline.main.event.Event;
/*    */ 
/*    */ public class EventSendMessage
/*    */   extends Event {
/*    */   public String getMessage() {
/*  8 */     return this.message;
/*    */   } private String message;
/*    */   public EventSendMessage(String message) {
/* 11 */     this.message = message;
/*    */   }
/*    */   
/*    */   public void setMessage(String message) {
/* 15 */     this.message = message;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\misc\EventSendMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */