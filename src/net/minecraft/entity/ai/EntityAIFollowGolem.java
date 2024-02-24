/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.monster.EntityIronGolem;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ 
/*     */ public class EntityAIFollowGolem
/*     */   extends EntityAIBase
/*     */ {
/*     */   private final EntityVillager theVillager;
/*     */   private EntityIronGolem theGolem;
/*     */   private int takeGolemRoseTick;
/*     */   private boolean tookGolemRose;
/*     */   
/*     */   public EntityAIFollowGolem(EntityVillager theVillagerIn) {
/*  17 */     this.theVillager = theVillagerIn;
/*  18 */     setMutexBits(3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  26 */     if (this.theVillager.getGrowingAge() >= 0)
/*     */     {
/*  28 */       return false;
/*     */     }
/*  30 */     if (!this.theVillager.worldObj.isDaytime())
/*     */     {
/*  32 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  36 */     List<EntityIronGolem> list = this.theVillager.worldObj.getEntitiesWithinAABB(EntityIronGolem.class, this.theVillager.getEntityBoundingBox().expand(6.0D, 2.0D, 6.0D));
/*     */     
/*  38 */     if (list.isEmpty())
/*     */     {
/*  40 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  44 */     for (EntityIronGolem entityirongolem : list) {
/*     */       
/*  46 */       if (entityirongolem.getHoldRoseTick() > 0) {
/*     */         
/*  48 */         this.theGolem = entityirongolem;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  53 */     return (this.theGolem != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  63 */     return (this.theGolem.getHoldRoseTick() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  71 */     this.takeGolemRoseTick = this.theVillager.getRNG().nextInt(320);
/*  72 */     this.tookGolemRose = false;
/*  73 */     this.theGolem.getNavigator().clearPathEntity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  81 */     this.theGolem = null;
/*  82 */     this.theVillager.getNavigator().clearPathEntity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  90 */     this.theVillager.getLookHelper().setLookPositionWithEntity((Entity)this.theGolem, 30.0F, 30.0F);
/*     */     
/*  92 */     if (this.theGolem.getHoldRoseTick() == this.takeGolemRoseTick) {
/*     */       
/*  94 */       this.theVillager.getNavigator().tryMoveToEntityLiving((Entity)this.theGolem, 0.5D);
/*  95 */       this.tookGolemRose = true;
/*     */     } 
/*     */     
/*  98 */     if (this.tookGolemRose && this.theVillager.getDistanceSqToEntity((Entity)this.theGolem) < 4.0D) {
/*     */       
/* 100 */       this.theGolem.setHoldingRose(false);
/* 101 */       this.theVillager.getNavigator().clearPathEntity();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\ai\EntityAIFollowGolem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */