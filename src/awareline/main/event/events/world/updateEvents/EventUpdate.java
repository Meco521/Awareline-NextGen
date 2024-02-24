/*    */ package awareline.main.event.events.world.updateEvents;
/*    */ 
/*    */ 
/*    */ public class EventUpdate extends Event {
/*    */   private double x;
/*    */   private double y;
/*    */   
/*  8 */   public void setX(double x) { this.x = x; } private double z; private boolean onGround; public void setY(double y) { this.y = y; } public void setZ(double z) { this.z = z; } public void setOnGround(boolean onGround) { this.onGround = onGround; }
/*    */   
/* 10 */   public double getX() { return this.x; } public double getY() { return this.y; } public double getZ() { return this.z; } public boolean isOnGround() {
/* 11 */     return this.onGround;
/*    */   }
/*    */   public EventUpdate(double x, double y, double z) {
/* 14 */     this.x = x;
/* 15 */     this.y = y;
/* 16 */     this.z = z;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\worl\\updateEvents\EventUpdate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */