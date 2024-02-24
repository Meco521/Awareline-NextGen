/*    */ package awareline.main.event.events.world;
/*    */ 
/*    */ 
/*    */ public class EventRotation extends Event {
/*    */   private float yaw;
/*    */   private float pitch;
/*    */   
/*  8 */   public float getYaw() { return this.yaw; } public float getPitch() {
/*  9 */     return this.pitch;
/*    */   }
/*    */   public EventRotation(float yaw, float pitch) {
/* 12 */     this.yaw = yaw;
/* 13 */     this.pitch = pitch;
/*    */   }
/*    */   
/*    */   public void setYaw(float yaw) {
/* 17 */     this.yaw = yaw;
/*    */   }
/*    */   
/*    */   public void setPitch(float pitch) {
/* 21 */     this.pitch = pitch;
/*    */   }
/*    */   
/*    */   public EventRotation getAngle() {
/* 25 */     this.yaw %= 360.0F;
/* 26 */     this.pitch %= 360.0F;
/* 27 */     while (this.yaw <= -180.0F) {
/* 28 */       this.yaw += 360.0F;
/*    */     }
/* 30 */     while (this.pitch <= -180.0F) {
/* 31 */       this.pitch += 360.0F;
/*    */     }
/* 33 */     while (this.yaw > 180.0F) {
/* 34 */       this.yaw -= 360.0F;
/*    */     }
/* 36 */     while (this.pitch > 180.0F) {
/* 37 */       this.pitch -= 360.0F;
/*    */     }
/* 39 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\world\EventRotation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */