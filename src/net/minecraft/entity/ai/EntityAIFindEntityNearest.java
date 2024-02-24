/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class EntityAIFindEntityNearest
/*     */   extends EntityAIBase
/*     */ {
/*  17 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   EntityLiving mob;
/*     */   private final Predicate<EntityLivingBase> field_179443_c;
/*     */   private final EntityAINearestAttackableTarget.Sorter field_179440_d;
/*     */   private EntityLivingBase target;
/*     */   private final Class<? extends EntityLivingBase> field_179439_f;
/*     */   
/*     */   public EntityAIFindEntityNearest(EntityLiving mobIn, Class<? extends EntityLivingBase> p_i45884_2_) {
/*  26 */     this.mob = mobIn;
/*  27 */     this.field_179439_f = p_i45884_2_;
/*     */     
/*  29 */     if (mobIn instanceof net.minecraft.entity.EntityCreature)
/*     */     {
/*  31 */       LOGGER.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
/*     */     }
/*     */     
/*  34 */     this.field_179443_c = new Predicate<EntityLivingBase>()
/*     */       {
/*     */         public boolean apply(EntityLivingBase p_apply_1_)
/*     */         {
/*  38 */           double d0 = EntityAIFindEntityNearest.this.getFollowRange();
/*     */           
/*  40 */           if (p_apply_1_.isSneaking())
/*     */           {
/*  42 */             d0 *= 0.800000011920929D;
/*     */           }
/*     */           
/*  45 */           return p_apply_1_.isInvisible() ? false : ((p_apply_1_.getDistanceToEntity((Entity)EntityAIFindEntityNearest.this.mob) > d0) ? false : EntityAITarget.isSuitableTarget(EntityAIFindEntityNearest.this.mob, p_apply_1_, false, true));
/*     */         }
/*     */       };
/*  48 */     this.field_179440_d = new EntityAINearestAttackableTarget.Sorter((Entity)mobIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  56 */     double d0 = getFollowRange();
/*  57 */     List<EntityLivingBase> list = this.mob.worldObj.getEntitiesWithinAABB(this.field_179439_f, this.mob.getEntityBoundingBox().expand(d0, 4.0D, d0), this.field_179443_c);
/*  58 */     list.sort(this.field_179440_d);
/*     */     
/*  60 */     if (list.isEmpty())
/*     */     {
/*  62 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  66 */     this.target = list.get(0);
/*  67 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  76 */     EntityLivingBase entitylivingbase = this.mob.getAttackTarget();
/*     */     
/*  78 */     if (entitylivingbase == null)
/*     */     {
/*  80 */       return false;
/*     */     }
/*  82 */     if (!entitylivingbase.isEntityAlive())
/*     */     {
/*  84 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  88 */     double d0 = getFollowRange();
/*  89 */     return (this.mob.getDistanceSqToEntity((Entity)entitylivingbase) > d0 * d0) ? false : ((!(entitylivingbase instanceof EntityPlayerMP) || !((EntityPlayerMP)entitylivingbase).theItemInWorldManager.isCreative()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  98 */     this.mob.setAttackTarget(this.target);
/*  99 */     super.startExecuting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/* 107 */     this.mob.setAttackTarget((EntityLivingBase)null);
/* 108 */     super.startExecuting();
/*     */   }
/*     */ 
/*     */   
/*     */   protected double getFollowRange() {
/* 113 */     IAttributeInstance iattributeinstance = this.mob.getEntityAttribute(SharedMonsterAttributes.followRange);
/* 114 */     return (iattributeinstance == null) ? 16.0D : iattributeinstance.getAttributeValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\ai\EntityAIFindEntityNearest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */