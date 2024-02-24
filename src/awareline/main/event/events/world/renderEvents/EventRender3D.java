/*    */ package awareline.main.event.events.world.renderEvents;
/*    */ 
/*    */ import awareline.main.event.Event;
/*    */ 
/*    */ public class EventRender3D
/*    */   extends Event {
/*    */   public float ticks;
/*    */   
/*    */   public EventRender3D() {}
/*    */   
/*    */   public EventRender3D(float ticks) {
/* 12 */     this.ticks = ticks;
/*    */   }
/*    */   
/*    */   public float getPartialTicks() {
/* 16 */     return this.ticks;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\world\renderEvents\EventRender3D.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */