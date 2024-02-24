/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityOwnable;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.pathfinding.PathEntity;
/*     */ import net.minecraft.pathfinding.PathPoint;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EntityAITarget
/*     */   extends EntityAIBase
/*     */ {
/*     */   protected final EntityCreature taskOwner;
/*     */   protected boolean shouldCheckSight;
/*     */   private final boolean nearbyOnly;
/*     */   private int targetSearchStatus;
/*     */   private int targetSearchDelay;
/*     */   private int targetUnseenTicks;
/*     */   
/*     */   public EntityAITarget(EntityCreature creature, boolean checkSight) {
/*  46 */     this(creature, checkSight, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityAITarget(EntityCreature creature, boolean checkSight, boolean onlyNearby) {
/*  51 */     this.taskOwner = creature;
/*  52 */     this.shouldCheckSight = checkSight;
/*  53 */     this.nearbyOnly = onlyNearby;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  61 */     EntityLivingBase entitylivingbase = this.taskOwner.getAttackTarget();
/*     */     
/*  63 */     if (entitylivingbase == null)
/*     */     {
/*  65 */       return false;
/*     */     }
/*  67 */     if (!entitylivingbase.isEntityAlive())
/*     */     {
/*  69 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  73 */     Team team = this.taskOwner.getTeam();
/*  74 */     Team team1 = entitylivingbase.getTeam();
/*     */     
/*  76 */     if (team != null && team1 == team)
/*     */     {
/*  78 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  82 */     double d0 = getTargetDistance();
/*     */     
/*  84 */     if (this.taskOwner.getDistanceSqToEntity((Entity)entitylivingbase) > d0 * d0)
/*     */     {
/*  86 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  90 */     if (this.shouldCheckSight)
/*     */     {
/*  92 */       if (this.taskOwner.getEntitySenses().canSee((Entity)entitylivingbase)) {
/*     */         
/*  94 */         this.targetUnseenTicks = 0;
/*     */       }
/*  96 */       else if (++this.targetUnseenTicks > 60) {
/*     */         
/*  98 */         return false;
/*     */       } 
/*     */     }
/*     */     
/* 102 */     return (!(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer)entitylivingbase).capabilities.disableDamage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getTargetDistance() {
/* 110 */     IAttributeInstance iattributeinstance = this.taskOwner.getEntityAttribute(SharedMonsterAttributes.followRange);
/* 111 */     return (iattributeinstance == null) ? 16.0D : iattributeinstance.getAttributeValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/* 119 */     this.targetSearchStatus = 0;
/* 120 */     this.targetSearchDelay = 0;
/* 121 */     this.targetUnseenTicks = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/* 129 */     this.taskOwner.setAttackTarget((EntityLivingBase)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSuitableTarget(EntityLiving attacker, EntityLivingBase target, boolean includeInvincibles, boolean checkSight) {
/* 137 */     if (target == null)
/*     */     {
/* 139 */       return false;
/*     */     }
/* 141 */     if (target == attacker)
/*     */     {
/* 143 */       return false;
/*     */     }
/* 145 */     if (!target.isEntityAlive())
/*     */     {
/* 147 */       return false;
/*     */     }
/* 149 */     if (!attacker.canAttackClass(target.getClass()))
/*     */     {
/* 151 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 155 */     Team team = attacker.getTeam();
/* 156 */     Team team1 = target.getTeam();
/*     */     
/* 158 */     if (team != null && team1 == team)
/*     */     {
/* 160 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 164 */     if (attacker instanceof IEntityOwnable && StringUtils.isNotEmpty(((IEntityOwnable)attacker).getOwnerId())) {
/*     */       
/* 166 */       if (target instanceof IEntityOwnable && ((IEntityOwnable)attacker).getOwnerId().equals(((IEntityOwnable)target).getOwnerId()))
/*     */       {
/* 168 */         return false;
/*     */       }
/*     */       
/* 171 */       if (target == ((IEntityOwnable)attacker).getOwner())
/*     */       {
/* 173 */         return false;
/*     */       }
/*     */     }
/* 176 */     else if (target instanceof EntityPlayer && !includeInvincibles && ((EntityPlayer)target).capabilities.disableDamage) {
/*     */       
/* 178 */       return false;
/*     */     } 
/*     */     
/* 181 */     return (!checkSight || attacker.getEntitySenses().canSee((Entity)target));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSuitableTarget(EntityLivingBase target, boolean includeInvincibles) {
/* 192 */     if (!isSuitableTarget((EntityLiving)this.taskOwner, target, includeInvincibles, this.shouldCheckSight))
/*     */     {
/* 194 */       return false;
/*     */     }
/* 196 */     if (!this.taskOwner.isWithinHomeDistanceFromPosition(new BlockPos((Entity)target)))
/*     */     {
/* 198 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 202 */     if (this.nearbyOnly) {
/*     */       
/* 204 */       if (--this.targetSearchDelay <= 0)
/*     */       {
/* 206 */         this.targetSearchStatus = 0;
/*     */       }
/*     */       
/* 209 */       if (this.targetSearchStatus == 0)
/*     */       {
/* 211 */         this.targetSearchStatus = canEasilyReach(target) ? 1 : 2;
/*     */       }
/*     */       
/* 214 */       if (this.targetSearchStatus == 2)
/*     */       {
/* 216 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 220 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canEasilyReach(EntityLivingBase target) {
/* 231 */     this.targetSearchDelay = 10 + this.taskOwner.getRNG().nextInt(5);
/* 232 */     PathEntity pathentity = this.taskOwner.getNavigator().getPathToEntityLiving((Entity)target);
/*     */     
/* 234 */     if (pathentity == null)
/*     */     {
/* 236 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 240 */     PathPoint pathpoint = pathentity.getFinalPathPoint();
/*     */     
/* 242 */     if (pathpoint == null)
/*     */     {
/* 244 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 248 */     int i = pathpoint.xCoord - MathHelper.floor_double(target.posX);
/* 249 */     int j = pathpoint.zCoord - MathHelper.floor_double(target.posZ);
/* 250 */     return ((i * i + j * j) <= 2.25D);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\ai\EntityAITarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */