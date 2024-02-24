/*    */ package awareline.main.event.events.world.updateEvents;
/*    */ 
/*    */ public class EventPreUpdate extends Event {
/*    */   public float yaw;
/*    */   public float pitch;
/*    */   public double y;
/*    */   
/*  8 */   public float getYaw() { return this.yaw; } public boolean onGround; public double x; public double z; private boolean modified; public float getPitch() {
/*  9 */     return this.pitch; }
/* 10 */   public double getY() { return this.y; }
/* 11 */   public boolean isOnGround() { return this.onGround; }
/* 12 */   public double getX() { return this.x; }
/* 13 */   public double getZ() { return this.z; } public boolean isModified() {
/* 14 */     return this.modified;
/*    */   }
/*    */   public EventPreUpdate(float yaw2, float pitch2, double x2, double y2, double z2, boolean onGround2) {
/* 17 */     this.yaw = yaw2;
/* 18 */     this.pitch = pitch2;
/* 19 */     this.y = y2;
/* 20 */     this.onGround = onGround2;
/* 21 */     this.x = x2;
/* 22 */     this.z = z2;
/*    */   }
/*    */   
/*    */   public void setYaw(float yaw2) {
/* 26 */     this.yaw = yaw2;
/* 27 */     this.modified = true;
/*    */   }
/*    */   
/*    */   public void setPitch(float pitch2) {
/* 31 */     this.pitch = Math.max(Math.min(pitch2, 90.0F), -90.0F);
/* 32 */     this.modified = true;
/*    */   }
/*    */   
/*    */   public void setBody(float yaw2, float pitch2) {
/* 36 */     this.yaw = yaw2;
/* 37 */     this.pitch = pitch2;
/*    */   }
/*    */   
/*    */   public void setY(double y2) {
/* 41 */     this.y = y2;
/*    */   }
/*    */   
/*    */   public void setOnGround(boolean onGround2) {
/* 45 */     this.onGround = onGround2;
/*    */   }
/*    */   
/*    */   public void setGround(boolean onGround2) {
/* 49 */     this.onGround = onGround2;
/*    */   }
/*    */   
/*    */   public void setZ(double z2) {
/* 53 */     this.z = z2;
/*    */   }
/*    */   
/*    */   public void setX(double x2) {
/* 57 */     this.x = x2;
/*    */   }
/*    */   
/*    */   public void setPosZ(double z2) {
/* 61 */     this.z = z2;
/*    */   }
/*    */   
/*    */   public void setPosX(double x2) {
/* 65 */     this.x = x2;
/*    */   }
/*    */   
/*    */   public double getPosX() {
/* 69 */     return this.x;
/*    */   }
/*    */   
/*    */   public double getPosZ() {
/* 73 */     return this.z;
/*    */   }
/*    */   
/*    */   public double getPosY() {
/* 77 */     return this.y;
/*    */   }
/*    */   
/*    */   public void setPosY(double v) {
/* 81 */     this.y = v;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\worl\\updateEvents\EventPreUpdate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */