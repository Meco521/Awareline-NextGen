/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ public class EntityAIPlay
/*     */   extends EntityAIBase {
/*     */   private final EntityVillager villagerObj;
/*     */   private EntityLivingBase targetVillager;
/*     */   private final double speed;
/*     */   private int playTime;
/*     */   
/*     */   public EntityAIPlay(EntityVillager villagerObjIn, double speedIn) {
/*  18 */     this.villagerObj = villagerObjIn;
/*  19 */     this.speed = speedIn;
/*  20 */     setMutexBits(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  28 */     if (this.villagerObj.getGrowingAge() >= 0)
/*     */     {
/*  30 */       return false;
/*     */     }
/*  32 */     if (this.villagerObj.getRNG().nextInt(400) != 0)
/*     */     {
/*  34 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  38 */     List<EntityVillager> list = this.villagerObj.worldObj.getEntitiesWithinAABB(EntityVillager.class, this.villagerObj.getEntityBoundingBox().expand(6.0D, 3.0D, 6.0D));
/*  39 */     double d0 = Double.MAX_VALUE;
/*     */     
/*  41 */     for (EntityVillager entityvillager : list) {
/*     */       
/*  43 */       if (entityvillager != this.villagerObj && !entityvillager.isPlaying() && entityvillager.getGrowingAge() < 0) {
/*     */         
/*  45 */         double d1 = entityvillager.getDistanceSqToEntity((Entity)this.villagerObj);
/*     */         
/*  47 */         if (d1 <= d0) {
/*     */           
/*  49 */           d0 = d1;
/*  50 */           this.targetVillager = (EntityLivingBase)entityvillager;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  55 */     if (this.targetVillager == null) {
/*     */       
/*  57 */       Vec3 vec3 = RandomPositionGenerator.findRandomTarget((EntityCreature)this.villagerObj, 16, 3);
/*     */       
/*  59 */       if (vec3 == null)
/*     */       {
/*  61 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  65 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  74 */     return (this.playTime > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  82 */     if (this.targetVillager != null)
/*     */     {
/*  84 */       this.villagerObj.setPlaying(true);
/*     */     }
/*     */     
/*  87 */     this.playTime = 1000;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  95 */     this.villagerObj.setPlaying(false);
/*  96 */     this.targetVillager = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/* 104 */     this.playTime--;
/*     */     
/* 106 */     if (this.targetVillager != null) {
/*     */       
/* 108 */       if (this.villagerObj.getDistanceSqToEntity((Entity)this.targetVillager) > 4.0D)
/*     */       {
/* 110 */         this.villagerObj.getNavigator().tryMoveToEntityLiving((Entity)this.targetVillager, this.speed);
/*     */       }
/*     */     }
/* 113 */     else if (this.villagerObj.getNavigator().noPath()) {
/*     */       
/* 115 */       Vec3 vec3 = RandomPositionGenerator.findRandomTarget((EntityCreature)this.villagerObj, 16, 3);
/*     */       
/* 117 */       if (vec3 == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 122 */       this.villagerObj.getNavigator().tryMoveToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord, this.speed);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\ai\EntityAIPlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */