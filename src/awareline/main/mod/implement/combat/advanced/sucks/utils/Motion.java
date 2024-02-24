/*    */ package awareline.main.mod.implement.combat.advanced.sucks.utils;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class Motion {
/*    */   private double motionX;
/*    */   private double motionY;
/*    */   private double motionZ;
/*    */   
/*    */   public Motion(double x, double y, double z) {
/* 11 */     this.motionX = x;
/* 12 */     this.motionY = y;
/* 13 */     this.motionZ = z;
/*    */   }
/*    */   
/*    */   public Motion(Entity entity) {
/* 17 */     this.motionX = entity.motionX;
/* 18 */     this.motionY = entity.motionY;
/* 19 */     this.motionZ = entity.motionZ;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 23 */     this.motionX = 0.0D;
/* 24 */     this.motionY = 0.0D;
/* 25 */     this.motionZ = 0.0D;
/*    */   }
/*    */   
/*    */   public void setTo(Motion motion) {
/* 29 */     this.motionX = motion.motionX;
/* 30 */     this.motionY = motion.motionY;
/* 31 */     this.motionZ = motion.motionZ;
/*    */   }
/*    */   
/*    */   public void add(double x, double y, double z) {
/* 35 */     this.motionX += x;
/* 36 */     this.motionY += y;
/* 37 */     this.motionZ += z;
/*    */   }
/*    */   
/*    */   public void remove(double x, double y, double z) {
/* 41 */     this.motionX -= x;
/* 42 */     this.motionY -= y;
/* 43 */     this.motionZ -= z;
/*    */   }
/*    */   
/*    */   public double getMotionX() {
/* 47 */     return this.motionX;
/*    */   }
/*    */   
/*    */   public double getMotionY() {
/* 51 */     return this.motionY;
/*    */   }
/*    */   
/*    */   public double getMotionZ() {
/* 55 */     return this.motionZ;
/*    */   }
/*    */   
/*    */   public void setMotionX(double motionX) {
/* 59 */     this.motionX = motionX;
/*    */   }
/*    */   
/*    */   public void setMotionY(double motionY) {
/* 63 */     this.motionY = motionY;
/*    */   }
/*    */   
/*    */   public void setMotionZ(double motionZ) {
/* 67 */     this.motionZ = motionZ;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\advanced\suck\\utils\Motion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */