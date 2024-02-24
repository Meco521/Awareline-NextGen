/*     */ package net.minecraft.entity.ai;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class EntityAIFindEntityNearestPlayer extends EntityAIBase {
/*  16 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   EntityLiving entityLiving;
/*     */ 
/*     */   
/*     */   private final Predicate<Entity> predicate;
/*     */   
/*     */   private final EntityAINearestAttackableTarget.Sorter sorter;
/*     */   
/*     */   private EntityLivingBase entityTarget;
/*     */ 
/*     */   
/*     */   public EntityAIFindEntityNearestPlayer(EntityLiving entityLivingIn) {
/*  30 */     this.entityLiving = entityLivingIn;
/*     */     
/*  32 */     if (entityLivingIn instanceof net.minecraft.entity.EntityCreature)
/*     */     {
/*  34 */       LOGGER.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
/*     */     }
/*     */     
/*  37 */     this.predicate = new Predicate<Entity>()
/*     */       {
/*     */         public boolean apply(Entity p_apply_1_)
/*     */         {
/*  41 */           if (!(p_apply_1_ instanceof EntityPlayer))
/*     */           {
/*  43 */             return false;
/*     */           }
/*  45 */           if (((EntityPlayer)p_apply_1_).capabilities.disableDamage)
/*     */           {
/*  47 */             return false;
/*     */           }
/*     */ 
/*     */           
/*  51 */           double d0 = EntityAIFindEntityNearestPlayer.this.maxTargetRange();
/*     */           
/*  53 */           if (p_apply_1_.isSneaking())
/*     */           {
/*  55 */             d0 *= 0.800000011920929D;
/*     */           }
/*     */           
/*  58 */           if (p_apply_1_.isInvisible()) {
/*     */             
/*  60 */             float f = ((EntityPlayer)p_apply_1_).getArmorVisibility();
/*     */             
/*  62 */             if (f < 0.1F)
/*     */             {
/*  64 */               f = 0.1F;
/*     */             }
/*     */             
/*  67 */             d0 *= (0.7F * f);
/*     */           } 
/*     */           
/*  70 */           return (p_apply_1_.getDistanceToEntity((Entity)EntityAIFindEntityNearestPlayer.this.entityLiving) > d0) ? false : EntityAITarget.isSuitableTarget(EntityAIFindEntityNearestPlayer.this.entityLiving, (EntityLivingBase)p_apply_1_, false, true);
/*     */         }
/*     */       };
/*     */     
/*  74 */     this.sorter = new EntityAINearestAttackableTarget.Sorter((Entity)entityLivingIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  82 */     double d0 = maxTargetRange();
/*  83 */     List<EntityPlayer> list = this.entityLiving.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.entityLiving.getEntityBoundingBox().expand(d0, 4.0D, d0), this.predicate);
/*  84 */     list.sort(this.sorter);
/*     */     
/*  86 */     if (list.isEmpty())
/*     */     {
/*  88 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  92 */     this.entityTarget = (EntityLivingBase)list.get(0);
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/* 102 */     EntityLivingBase entitylivingbase = this.entityLiving.getAttackTarget();
/*     */     
/* 104 */     if (entitylivingbase == null)
/*     */     {
/* 106 */       return false;
/*     */     }
/* 108 */     if (!entitylivingbase.isEntityAlive())
/*     */     {
/* 110 */       return false;
/*     */     }
/* 112 */     if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).capabilities.disableDamage)
/*     */     {
/* 114 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 118 */     Team team = this.entityLiving.getTeam();
/* 119 */     Team team1 = entitylivingbase.getTeam();
/*     */     
/* 121 */     if (team != null && team1 == team)
/*     */     {
/* 123 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 127 */     double d0 = maxTargetRange();
/* 128 */     return (this.entityLiving.getDistanceSqToEntity((Entity)entitylivingbase) > d0 * d0) ? false : ((!(entitylivingbase instanceof EntityPlayerMP) || !((EntityPlayerMP)entitylivingbase).theItemInWorldManager.isCreative()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/* 138 */     this.entityLiving.setAttackTarget(this.entityTarget);
/* 139 */     super.startExecuting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/* 147 */     this.entityLiving.setAttackTarget((EntityLivingBase)null);
/* 148 */     super.startExecuting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double maxTargetRange() {
/* 156 */     IAttributeInstance iattributeinstance = this.entityLiving.getEntityAttribute(SharedMonsterAttributes.followRange);
/* 157 */     return (iattributeinstance == null) ? 16.0D : iattributeinstance.getAttributeValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\ai\EntityAIFindEntityNearestPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */