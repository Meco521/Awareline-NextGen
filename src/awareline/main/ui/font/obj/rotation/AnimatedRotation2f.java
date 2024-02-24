/*    */ package awareline.main.ui.font.obj.rotation;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public class AnimatedRotation2f extends Rotation2f {
/*    */   public float targetYaw;
/*    */   public float targetPitch;
/*    */   
/*    */   public void toward(double speed) {
/* 10 */     update();
/*    */     
/* 12 */     float yawDifference = getAngleDifference(this.targetYaw, getYaw());
/* 13 */     float pitchDifference = getAngleDifference(this.targetPitch, getPitch());
/* 14 */     this.yaw += (float)((yawDifference > speed) ? speed : Math.max(yawDifference, -speed));
/* 15 */     this.pitch += (float)((pitchDifference > speed) ? speed : Math.max(pitchDifference, -speed));
/*    */     
/* 17 */     float mouseSensitivity = (Minecraft.getMinecraft()).gameSettings.mouseSensitivity * 0.6F + 0.2F;
/* 18 */     float gcd = mouseSensitivity * mouseSensitivity * mouseSensitivity * 1.2F;
/*    */     
/* 20 */     float deltaYaw = this.yaw - this.prevYaw;
/* 21 */     deltaYaw -= deltaYaw % gcd;
/*    */     
/* 23 */     this.yaw = this.prevYaw + deltaYaw;
/*    */     
/* 25 */     float deltaPitch = this.pitch - this.prevPitch;
/* 26 */     deltaPitch -= deltaPitch % gcd;
/*    */     
/* 28 */     this.pitch = this.prevPitch + deltaPitch;
/*    */   }
/*    */   private float prevYaw; private float prevPitch;
/*    */   public void instant() {
/* 32 */     this.yaw = this.targetYaw;
/* 33 */     this.pitch = this.targetPitch;
/*    */     
/* 35 */     update();
/*    */   }
/*    */   
/*    */   private void update() {
/* 39 */     this.prevYaw = getYaw();
/* 40 */     this.prevPitch = getPitch();
/*    */   }
/*    */   
/*    */   public void setTargetYawPitch(float yaw, float pitch) {
/* 44 */     this.targetYaw = yaw;
/* 45 */     this.targetPitch = pitch;
/*    */   }
/*    */   
/*    */   public void setTargetYaw(float targetYaw) {
/* 49 */     this.targetYaw = targetYaw;
/*    */   }
/*    */   
/*    */   public void setTargetPitch(float targetPitch) {
/* 53 */     this.targetPitch = targetPitch;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setYaw(float yaw) {
/* 58 */     update();
/* 59 */     super.setYaw(yaw);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPitch(float pitch) {
/* 64 */     update();
/* 65 */     super.setPitch(pitch);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setYawPitch(float yaw, float pitch) {
/* 70 */     update();
/* 71 */     super.setYawPitch(yaw, pitch);
/*    */   }
/*    */   
/*    */   private static float getAngleDifference(float a, float b) {
/* 75 */     return ((a - b) % 360.0F + 540.0F) % 360.0F - 180.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\obj\rotation\AnimatedRotation2f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */