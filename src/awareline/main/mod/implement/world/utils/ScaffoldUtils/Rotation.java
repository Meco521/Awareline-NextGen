/*    */ package awareline.main.mod.implement.world.utils.ScaffoldUtils;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class Rotation {
/*    */   private float yaw;
/*    */   private float pitch;
/*    */   
/*    */   public Rotation(float yaw, float pitch) {
/* 12 */     this.yaw = yaw;
/* 13 */     this.pitch = pitch;
/*    */   }
/*    */   
/*    */   public Rotation(Entity ent) {
/* 17 */     this.yaw = ent.rotationYaw;
/* 18 */     this.pitch = ent.rotationPitch;
/*    */   }
/*    */   
/*    */   public void add(float yaw, float pitch) {
/* 22 */     this.yaw += yaw;
/* 23 */     this.pitch += pitch;
/*    */   }
/*    */   
/*    */   public void remove(float yaw, float pitch) {
/* 27 */     this.yaw -= yaw;
/* 28 */     this.pitch -= pitch;
/*    */   }
/*    */   
/*    */   public float getYaw() {
/* 32 */     return this.yaw;
/*    */   }
/*    */   
/*    */   public float getPitch() {
/* 36 */     return this.pitch;
/*    */   }
/*    */   
/*    */   public void setYaw(float yaw) {
/* 40 */     this.yaw = yaw;
/*    */   }
/*    */   
/*    */   public void setPitch(float pitch) {
/* 44 */     this.pitch = pitch;
/*    */   }
/*    */   
/*    */   public float[] getBoth() {
/* 48 */     return new float[] { this.yaw, this.pitch };
/*    */   }
/*    */   
/*    */   public void toPlayer(EntityPlayer player) {
/* 52 */     if (Float.isNaN(this.yaw) || Float.isNaN(this.pitch)) {
/*    */       return;
/*    */     }
/* 55 */     fixedSensitivity(Float.valueOf((Minecraft.getMinecraft()).gameSettings.mouseSensitivity));
/*    */     
/* 57 */     player.rotationYaw = this.yaw;
/* 58 */     player.rotationPitch = this.pitch;
/*    */   }
/*    */   
/*    */   public void fixedSensitivity(Float sensitivity) {
/* 62 */     float f = sensitivity.floatValue() * 0.6F + 0.2F;
/* 63 */     float gcd = f * f * f * 1.2F;
/*    */     
/* 65 */     this.yaw -= this.yaw % gcd;
/* 66 */     this.pitch -= this.pitch % gcd;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\worl\\utils\ScaffoldUtils\Rotation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */