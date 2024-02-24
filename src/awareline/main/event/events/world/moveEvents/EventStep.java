/*    */ package awareline.main.event.events.world.moveEvents;
/*    */ 
/*    */ import awareline.main.event.Event;
/*    */ 
/*    */ public class EventStep
/*    */   extends Event {
/*    */   public float getStepHeight() {
/*  8 */     return this.stepHeight;
/*    */   } private float stepHeight;
/*    */   public EventStep(float stepHeight) {
/* 11 */     this.stepHeight = stepHeight;
/*    */   }
/*    */   
/*    */   public float getHeight() {
/* 15 */     return this.stepHeight;
/*    */   }
/*    */   
/*    */   public void setStepHeight(float stepHeight) {
/* 19 */     this.stepHeight = stepHeight;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\world\moveEvents\EventStep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */