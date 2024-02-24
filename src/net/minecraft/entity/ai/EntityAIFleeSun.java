/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.Vec3;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class EntityAIFleeSun
/*    */   extends EntityAIBase
/*    */ {
/*    */   private final EntityCreature theCreature;
/*    */   private double shelterX;
/*    */   private double shelterY;
/*    */   private double shelterZ;
/*    */   private final double movementSpeed;
/*    */   private final World theWorld;
/*    */   
/*    */   public EntityAIFleeSun(EntityCreature theCreatureIn, double movementSpeedIn) {
/* 21 */     this.theCreature = theCreatureIn;
/* 22 */     this.movementSpeed = movementSpeedIn;
/* 23 */     this.theWorld = theCreatureIn.worldObj;
/* 24 */     setMutexBits(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 32 */     if (!this.theWorld.isDaytime())
/*    */     {
/* 34 */       return false;
/*    */     }
/* 36 */     if (!this.theCreature.isBurning())
/*    */     {
/* 38 */       return false;
/*    */     }
/* 40 */     if (!this.theWorld.canSeeSky(new BlockPos(this.theCreature.posX, (this.theCreature.getEntityBoundingBox()).minY, this.theCreature.posZ)))
/*    */     {
/* 42 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 46 */     Vec3 vec3 = findPossibleShelter();
/*    */     
/* 48 */     if (vec3 == null)
/*    */     {
/* 50 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 54 */     this.shelterX = vec3.xCoord;
/* 55 */     this.shelterY = vec3.yCoord;
/* 56 */     this.shelterZ = vec3.zCoord;
/* 57 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 67 */     return !this.theCreature.getNavigator().noPath();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 75 */     this.theCreature.getNavigator().tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
/*    */   }
/*    */ 
/*    */   
/*    */   private Vec3 findPossibleShelter() {
/* 80 */     Random random = this.theCreature.getRNG();
/* 81 */     BlockPos blockpos = new BlockPos(this.theCreature.posX, (this.theCreature.getEntityBoundingBox()).minY, this.theCreature.posZ);
/*    */     
/* 83 */     for (int i = 0; i < 10; i++) {
/*    */       
/* 85 */       BlockPos blockpos1 = blockpos.add(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);
/*    */       
/* 87 */       if (!this.theWorld.canSeeSky(blockpos1) && this.theCreature.getBlockPathWeight(blockpos1) < 0.0F)
/*    */       {
/* 89 */         return new Vec3(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ());
/*    */       }
/*    */     } 
/*    */     
/* 93 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\ai\EntityAIFleeSun.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */