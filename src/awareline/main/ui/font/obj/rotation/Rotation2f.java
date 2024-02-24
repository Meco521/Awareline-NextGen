/*    */ package awareline.main.ui.font.obj.rotation;
/*    */ 
/*    */ public class Rotation2f {
/*    */   protected float yaw;
/*    */   protected float pitch;
/*    */   
/*  7 */   public float getYaw() { return this.yaw; } public float getPitch() {
/*  8 */     return this.pitch;
/*    */   }
/*    */   
/*    */   public Rotation2f() {}
/*    */   
/*    */   public Rotation2f(float yaw, float pitch) {
/* 14 */     this.yaw = yaw;
/* 15 */     this.pitch = pitch;
/*    */   }
/*    */   
/*    */   public void setYaw(float yaw) {
/* 19 */     this.yaw = yaw;
/*    */   }
/*    */   
/*    */   public void setPitch(float pitch) {
/* 23 */     this.pitch = pitch;
/*    */   }
/*    */   
/*    */   public void setYawPitch(float[] arr) {
/* 27 */     setYawPitch(arr[0], arr[1]);
/*    */   }
/*    */   
/*    */   public void setYawPitch(float yaw, float pitch) {
/* 31 */     this.yaw = yaw;
/* 32 */     this.pitch = pitch;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\obj\rotation\Rotation2f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */