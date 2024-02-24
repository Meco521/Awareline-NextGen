/*    */ package awareline.main.event.events.display;
/*    */ 
/*    */ import awareline.main.event.Event;
/*    */ 
/*    */ public class DisplayChestGuiEvent extends Event {
/*    */   final String string;
/*    */   
/*    */   public String getString() {
/*  9 */     return this.string;
/*    */   }
/*    */   public DisplayChestGuiEvent(String string) {
/* 12 */     this.string = string;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\display\DisplayChestGuiEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */