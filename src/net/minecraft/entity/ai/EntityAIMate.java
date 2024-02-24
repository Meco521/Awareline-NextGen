/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.passive.EntityAnimal;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityAIMate
/*     */   extends EntityAIBase
/*     */ {
/*     */   private final EntityAnimal theAnimal;
/*     */   World theWorld;
/*     */   private EntityAnimal targetMate;
/*     */   int spawnBabyDelay;
/*     */   double moveSpeed;
/*     */   
/*     */   public EntityAIMate(EntityAnimal animal, double speedIn) {
/*  32 */     this.theAnimal = animal;
/*  33 */     this.theWorld = animal.worldObj;
/*  34 */     this.moveSpeed = speedIn;
/*  35 */     setMutexBits(3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  43 */     if (!this.theAnimal.isInLove())
/*     */     {
/*  45 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  49 */     this.targetMate = getNearbyMate();
/*  50 */     return (this.targetMate != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  59 */     return (this.targetMate.isEntityAlive() && this.targetMate.isInLove() && this.spawnBabyDelay < 60);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  67 */     this.targetMate = null;
/*  68 */     this.spawnBabyDelay = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  76 */     this.theAnimal.getLookHelper().setLookPositionWithEntity((Entity)this.targetMate, 10.0F, this.theAnimal.getVerticalFaceSpeed());
/*  77 */     this.theAnimal.getNavigator().tryMoveToEntityLiving((Entity)this.targetMate, this.moveSpeed);
/*  78 */     this.spawnBabyDelay++;
/*     */     
/*  80 */     if (this.spawnBabyDelay >= 60 && this.theAnimal.getDistanceSqToEntity((Entity)this.targetMate) < 9.0D)
/*     */     {
/*  82 */       spawnBaby();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityAnimal getNearbyMate() {
/*  92 */     float f = 8.0F;
/*  93 */     List<EntityAnimal> list = this.theWorld.getEntitiesWithinAABB(this.theAnimal.getClass(), this.theAnimal.getEntityBoundingBox().expand(f, f, f));
/*  94 */     double d0 = Double.MAX_VALUE;
/*  95 */     EntityAnimal entityanimal = null;
/*     */     
/*  97 */     for (EntityAnimal entityanimal1 : list) {
/*     */       
/*  99 */       if (this.theAnimal.canMateWith(entityanimal1) && this.theAnimal.getDistanceSqToEntity((Entity)entityanimal1) < d0) {
/*     */         
/* 101 */         entityanimal = entityanimal1;
/* 102 */         d0 = this.theAnimal.getDistanceSqToEntity((Entity)entityanimal1);
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     return entityanimal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void spawnBaby() {
/* 114 */     EntityAgeable entityageable = this.theAnimal.createChild((EntityAgeable)this.targetMate);
/*     */     
/* 116 */     if (entityageable != null) {
/*     */       
/* 118 */       EntityPlayer entityplayer = this.theAnimal.getPlayerInLove();
/*     */       
/* 120 */       if (entityplayer == null && this.targetMate.getPlayerInLove() != null)
/*     */       {
/* 122 */         entityplayer = this.targetMate.getPlayerInLove();
/*     */       }
/*     */       
/* 125 */       if (entityplayer != null) {
/*     */         
/* 127 */         entityplayer.triggerAchievement(StatList.animalsBredStat);
/*     */         
/* 129 */         if (this.theAnimal instanceof net.minecraft.entity.passive.EntityCow)
/*     */         {
/* 131 */           entityplayer.triggerAchievement((StatBase)AchievementList.breedCow);
/*     */         }
/*     */       } 
/*     */       
/* 135 */       this.theAnimal.setGrowingAge(6000);
/* 136 */       this.targetMate.setGrowingAge(6000);
/* 137 */       this.theAnimal.resetInLove();
/* 138 */       this.targetMate.resetInLove();
/* 139 */       entityageable.setGrowingAge(-24000);
/* 140 */       entityageable.setLocationAndAngles(this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, 0.0F, 0.0F);
/* 141 */       this.theWorld.spawnEntityInWorld((Entity)entityageable);
/* 142 */       Random random = this.theAnimal.getRNG();
/*     */       
/* 144 */       for (int i = 0; i < 7; i++) {
/*     */         
/* 146 */         double d0 = random.nextGaussian() * 0.02D;
/* 147 */         double d1 = random.nextGaussian() * 0.02D;
/* 148 */         double d2 = random.nextGaussian() * 0.02D;
/* 149 */         double d3 = random.nextDouble() * this.theAnimal.width * 2.0D - this.theAnimal.width;
/* 150 */         double d4 = 0.5D + random.nextDouble() * this.theAnimal.height;
/* 151 */         double d5 = random.nextDouble() * this.theAnimal.width * 2.0D - this.theAnimal.width;
/* 152 */         this.theWorld.spawnParticle(EnumParticleTypes.HEART, this.theAnimal.posX + d3, this.theAnimal.posY + d4, this.theAnimal.posZ + d5, d0, d1, d2, new int[0]);
/*     */       } 
/*     */       
/* 155 */       if (this.theWorld.getGameRules().getBoolean("doMobLoot"))
/*     */       {
/* 157 */         this.theWorld.spawnEntityInWorld((Entity)new EntityXPOrb(this.theWorld, this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, random.nextInt(7) + 1));
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\ai\EntityAIMate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */