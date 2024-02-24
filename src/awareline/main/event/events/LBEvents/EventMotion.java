/*    */ package awareline.main.event.events.LBEvents;
/*    */ 
/*    */ import awareline.main.event.Event;
/*    */ 
/*    */ public class EventMotion extends Event {
/*    */   private Type motionType;
/*    */   
/*    */   public EventMotion(Type type) {
/*  9 */     this.motionType = type;
/*    */   }
/*    */   
/*    */   public void setTypes(Type motionType) {
/* 13 */     this.motionType = motionType;
/*    */   }
/*    */   
/*    */   public Type getTypes() {
/* 17 */     return this.motionType;
/*    */   }
/*    */   
/*    */   public boolean isPre() {
/* 21 */     return (this.motionType == Type.PRE);
/*    */   }
/*    */   
/*    */   public enum Type
/*    */   {
/* 26 */     PRE,
/* 27 */     POST;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\LBEvents\EventMotion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */