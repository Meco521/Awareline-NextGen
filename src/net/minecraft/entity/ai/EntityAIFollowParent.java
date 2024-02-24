/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.passive.EntityAnimal;
/*     */ 
/*     */ 
/*     */ public class EntityAIFollowParent
/*     */   extends EntityAIBase
/*     */ {
/*     */   EntityAnimal childAnimal;
/*     */   EntityAnimal parentAnimal;
/*     */   double moveSpeed;
/*     */   private int delayCounter;
/*     */   
/*     */   public EntityAIFollowParent(EntityAnimal animal, double speed) {
/*  17 */     this.childAnimal = animal;
/*  18 */     this.moveSpeed = speed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  26 */     if (this.childAnimal.getGrowingAge() >= 0)
/*     */     {
/*  28 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  32 */     List<EntityAnimal> list = this.childAnimal.worldObj.getEntitiesWithinAABB(this.childAnimal.getClass(), this.childAnimal.getEntityBoundingBox().expand(8.0D, 4.0D, 8.0D));
/*  33 */     EntityAnimal entityanimal = null;
/*  34 */     double d0 = Double.MAX_VALUE;
/*     */     
/*  36 */     for (EntityAnimal entityanimal1 : list) {
/*     */       
/*  38 */       if (entityanimal1.getGrowingAge() >= 0) {
/*     */         
/*  40 */         double d1 = this.childAnimal.getDistanceSqToEntity((Entity)entityanimal1);
/*     */         
/*  42 */         if (d1 <= d0) {
/*     */           
/*  44 */           d0 = d1;
/*  45 */           entityanimal = entityanimal1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  50 */     if (entityanimal == null)
/*     */     {
/*  52 */       return false;
/*     */     }
/*  54 */     if (d0 < 9.0D)
/*     */     {
/*  56 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  60 */     this.parentAnimal = entityanimal;
/*  61 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  71 */     if (this.childAnimal.getGrowingAge() >= 0)
/*     */     {
/*  73 */       return false;
/*     */     }
/*  75 */     if (!this.parentAnimal.isEntityAlive())
/*     */     {
/*  77 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  81 */     double d0 = this.childAnimal.getDistanceSqToEntity((Entity)this.parentAnimal);
/*  82 */     return (d0 >= 9.0D && d0 <= 256.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  91 */     this.delayCounter = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  99 */     this.parentAnimal = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/* 107 */     if (--this.delayCounter <= 0) {
/*     */       
/* 109 */       this.delayCounter = 10;
/* 110 */       this.childAnimal.getNavigator().tryMoveToEntityLiving((Entity)this.parentAnimal, this.moveSpeed);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\ai\EntityAIFollowParent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */