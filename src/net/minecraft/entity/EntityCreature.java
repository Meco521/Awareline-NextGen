/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.passive.EntityTameable;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class EntityCreature
/*     */   extends EntityLiving {
/*  15 */   public static final UUID FLEEING_SPEED_MODIFIER_UUID = UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
/*  16 */   public static final AttributeModifier FLEEING_SPEED_MODIFIER = (new AttributeModifier(FLEEING_SPEED_MODIFIER_UUID, "Fleeing speed bonus", 2.0D, 2)).setSaved(false);
/*  17 */   private BlockPos homePosition = BlockPos.ORIGIN;
/*     */ 
/*     */   
/*  20 */   private float maximumHomeDistance = -1.0F;
/*  21 */   private final EntityAIBase aiBase = (EntityAIBase)new EntityAIMoveTowardsRestriction(this, 1.0D);
/*     */   
/*     */   private boolean isMovementAITaskSet;
/*     */   
/*     */   public EntityCreature(World worldIn) {
/*  26 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getBlockPathWeight(BlockPos pos) {
/*  31 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/*  39 */     return (super.getCanSpawnHere() && getBlockPathWeight(new BlockPos(this.posX, (getEntityBoundingBox()).minY, this.posZ)) >= 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPath() {
/*  47 */     return !this.navigator.noPath();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWithinHomeDistanceCurrentPosition() {
/*  52 */     return isWithinHomeDistanceFromPosition(new BlockPos(this));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWithinHomeDistanceFromPosition(BlockPos pos) {
/*  57 */     return (this.maximumHomeDistance == -1.0F) ? true : ((this.homePosition.distanceSq((Vec3i)pos) < (this.maximumHomeDistance * this.maximumHomeDistance)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHomePosAndDistance(BlockPos pos, int distance) {
/*  65 */     this.homePosition = pos;
/*  66 */     this.maximumHomeDistance = distance;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getHomePosition() {
/*  71 */     return this.homePosition;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaximumHomeDistance() {
/*  76 */     return this.maximumHomeDistance;
/*     */   }
/*     */ 
/*     */   
/*     */   public void detachHome() {
/*  81 */     this.maximumHomeDistance = -1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasHome() {
/*  89 */     return (this.maximumHomeDistance != -1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateLeashedState() {
/*  97 */     super.updateLeashedState();
/*     */     
/*  99 */     if (getLeashed() && getLeashedToEntity() != null && (getLeashedToEntity()).worldObj == this.worldObj) {
/*     */       
/* 101 */       Entity entity = getLeashedToEntity();
/* 102 */       setHomePosAndDistance(new BlockPos((int)entity.posX, (int)entity.posY, (int)entity.posZ), 5);
/* 103 */       float f = getDistanceToEntity(entity);
/*     */       
/* 105 */       if (this instanceof EntityTameable && ((EntityTameable)this).isSitting()) {
/*     */         
/* 107 */         if (f > 10.0F)
/*     */         {
/* 109 */           clearLeashed(true, true);
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 115 */       if (!this.isMovementAITaskSet) {
/*     */         
/* 117 */         this.tasks.addTask(2, this.aiBase);
/*     */         
/* 119 */         if (getNavigator() instanceof PathNavigateGround)
/*     */         {
/* 121 */           ((PathNavigateGround)getNavigator()).setAvoidsWater(false);
/*     */         }
/*     */         
/* 124 */         this.isMovementAITaskSet = true;
/*     */       } 
/*     */       
/* 127 */       func_142017_o(f);
/*     */       
/* 129 */       if (f > 4.0F)
/*     */       {
/* 131 */         getNavigator().tryMoveToEntityLiving(entity, 1.0D);
/*     */       }
/*     */       
/* 134 */       if (f > 6.0F) {
/*     */         
/* 136 */         double d0 = (entity.posX - this.posX) / f;
/* 137 */         double d1 = (entity.posY - this.posY) / f;
/* 138 */         double d2 = (entity.posZ - this.posZ) / f;
/* 139 */         this.motionX += d0 * Math.abs(d0) * 0.4D;
/* 140 */         this.motionY += d1 * Math.abs(d1) * 0.4D;
/* 141 */         this.motionZ += d2 * Math.abs(d2) * 0.4D;
/*     */       } 
/*     */       
/* 144 */       if (f > 10.0F)
/*     */       {
/* 146 */         clearLeashed(true, true);
/*     */       }
/*     */     }
/* 149 */     else if (!getLeashed() && this.isMovementAITaskSet) {
/*     */       
/* 151 */       this.isMovementAITaskSet = false;
/* 152 */       this.tasks.removeTask(this.aiBase);
/*     */       
/* 154 */       if (getNavigator() instanceof PathNavigateGround)
/*     */       {
/* 156 */         ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*     */       }
/*     */       
/* 159 */       detachHome();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void func_142017_o(float p_142017_1_) {}
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\EntityCreature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */