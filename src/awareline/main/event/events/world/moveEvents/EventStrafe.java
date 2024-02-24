/*    */ package awareline.main.event.events.world.moveEvents;
/*    */ 
/*    */ import awareline.main.event.Event;
/*    */ import awareline.main.utility.MoveUtils;
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public final class EventStrafe extends Event {
/*    */   private final float strafe;
/*    */   
/* 10 */   public float getStrafe() { return this.strafe; } private final float forward; private float friction; public float getForward() {
/* 11 */     return this.forward; } public float getFriction() {
/* 12 */     return this.friction;
/*    */   }
/*    */   public EventStrafe(float strafe, float forward, float friction) {
/* 15 */     this.strafe = strafe;
/* 16 */     this.forward = forward;
/* 17 */     this.friction = friction;
/*    */   }
/*    */   
/*    */   public void setSpeed(double speed, double motionMultiplier) {
/* 21 */     this.friction = (float)((this.forward != 0.0F && this.strafe != 0.0F) ? (speed * 0.9800000190734863D) : speed);
/* 22 */     (Minecraft.getMinecraft()).thePlayer.motionX *= motionMultiplier;
/* 23 */     (Minecraft.getMinecraft()).thePlayer.motionZ *= motionMultiplier;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setSpeed(double speed) {
/* 28 */     this.friction = (float)((this.forward != 0.0F && this.strafe != 0.0F) ? (speed * 0.9800000190734863D) : speed);
/* 29 */     MoveUtils.INSTANCE.stop();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\world\moveEvents\EventStrafe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */