/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.pathfinding.PathEntity;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityAIAvoidEntity<T extends Entity>
/*     */   extends EntityAIBase
/*     */ {
/*     */   private final Predicate<Entity> canBeSeenSelector;
/*     */   protected EntityCreature theEntity;
/*     */   private final double farSpeed;
/*     */   private final double nearSpeed;
/*     */   protected T closestLivingEntity;
/*     */   private final float avoidDistance;
/*     */   private PathEntity entityPathEntity;
/*     */   private final PathNavigate entityPathNavigate;
/*     */   private final Class<T> classToAvoid;
/*     */   private final Predicate<? super T> avoidTargetSelector;
/*     */   
/*     */   public EntityAIAvoidEntity(EntityCreature theEntityIn, Class<T> classToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
/*  35 */     this(theEntityIn, classToAvoidIn, Predicates.alwaysTrue(), avoidDistanceIn, farSpeedIn, nearSpeedIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityAIAvoidEntity(EntityCreature theEntityIn, Class<T> classToAvoidIn, Predicate<? super T> avoidTargetSelectorIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
/*  40 */     this.canBeSeenSelector = new Predicate<Entity>()
/*     */       {
/*     */         public boolean apply(Entity p_apply_1_)
/*     */         {
/*  44 */           return (p_apply_1_.isEntityAlive() && EntityAIAvoidEntity.this.theEntity.getEntitySenses().canSee(p_apply_1_));
/*     */         }
/*     */       };
/*  47 */     this.theEntity = theEntityIn;
/*  48 */     this.classToAvoid = classToAvoidIn;
/*  49 */     this.avoidTargetSelector = avoidTargetSelectorIn;
/*  50 */     this.avoidDistance = avoidDistanceIn;
/*  51 */     this.farSpeed = farSpeedIn;
/*  52 */     this.nearSpeed = nearSpeedIn;
/*  53 */     this.entityPathNavigate = theEntityIn.getNavigator();
/*  54 */     setMutexBits(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  62 */     List<T> list = this.theEntity.worldObj.getEntitiesWithinAABB(this.classToAvoid, this.theEntity.getEntityBoundingBox().expand(this.avoidDistance, 3.0D, this.avoidDistance), Predicates.and(new Predicate[] { EntitySelectors.NOT_SPECTATING, this.canBeSeenSelector, this.avoidTargetSelector }));
/*     */     
/*  64 */     if (list.isEmpty())
/*     */     {
/*  66 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  70 */     this.closestLivingEntity = list.get(0);
/*  71 */     Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, new Vec3(((Entity)this.closestLivingEntity).posX, ((Entity)this.closestLivingEntity).posY, ((Entity)this.closestLivingEntity).posZ));
/*     */     
/*  73 */     if (vec3 == null)
/*     */     {
/*  75 */       return false;
/*     */     }
/*  77 */     if (this.closestLivingEntity.getDistanceSq(vec3.xCoord, vec3.yCoord, vec3.zCoord) < this.closestLivingEntity.getDistanceSqToEntity((Entity)this.theEntity))
/*     */     {
/*  79 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  83 */     this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord);
/*  84 */     return (this.entityPathEntity == null) ? false : this.entityPathEntity.isDestinationSame(vec3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  94 */     return !this.entityPathNavigate.noPath();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/* 102 */     this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/* 110 */     this.closestLivingEntity = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/* 118 */     if (this.theEntity.getDistanceSqToEntity((Entity)this.closestLivingEntity) < 49.0D) {
/*     */       
/* 120 */       this.theEntity.getNavigator().setSpeed(this.nearSpeed);
/*     */     }
/*     */     else {
/*     */       
/* 124 */       this.theEntity.getNavigator().setSpeed(this.farSpeed);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\ai\EntityAIAvoidEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */