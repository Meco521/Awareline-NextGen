/*    */ package awareline.main.event.events.world.renderEvents;
/*    */ 
/*    */ import awareline.main.event.Event;
/*    */ 
/*    */ public class EventRender
/*    */   extends Event {
/*    */   public float getPartialTicks() {
/*  8 */     return this.partialTicks;
/*    */   } public final float partialTicks;
/*    */   public EventRender(float partialTicks) {
/* 11 */     this.partialTicks = partialTicks;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\world\renderEvents\EventRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */