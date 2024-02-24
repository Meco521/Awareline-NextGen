/*    */ package awareline.main.event.events.world.updateEvents;
/*    */ 
/*    */ 
/*    */ public class EventPostUpdate extends Event {
/*    */   public float yaw;
/*    */   public float pitch;
/*    */   
/*  8 */   public void setYaw(float yaw) { this.yaw = yaw; } public void setPitch(float pitch) { this.pitch = pitch; }
/*    */   
/* 10 */   public float getYaw() { return this.yaw; } public float getPitch() {
/* 11 */     return this.pitch;
/*    */   }
/*    */   public EventPostUpdate(float yaw2, float pitch2) {
/* 14 */     this.yaw = yaw2;
/* 15 */     this.pitch = pitch2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\worl\\updateEvents\EventPostUpdate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */