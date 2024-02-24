/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.pathfinding.PathEntity;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.village.Village;
/*     */ import net.minecraft.village.VillageDoorInfo;
/*     */ 
/*     */ 
/*     */ public class EntityAIMoveThroughVillage
/*     */   extends EntityAIBase
/*     */ {
/*     */   private final EntityCreature theEntity;
/*     */   private final double movementSpeed;
/*     */   private PathEntity entityPathNavigate;
/*     */   private VillageDoorInfo doorInfo;
/*     */   private final boolean isNocturnal;
/*  24 */   private final List<VillageDoorInfo> doorList = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public EntityAIMoveThroughVillage(EntityCreature theEntityIn, double movementSpeedIn, boolean isNocturnalIn) {
/*  28 */     this.theEntity = theEntityIn;
/*  29 */     this.movementSpeed = movementSpeedIn;
/*  30 */     this.isNocturnal = isNocturnalIn;
/*  31 */     setMutexBits(1);
/*     */     
/*  33 */     if (!(theEntityIn.getNavigator() instanceof PathNavigateGround))
/*     */     {
/*  35 */       throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  44 */     resizeDoorList();
/*     */     
/*  46 */     if (this.isNocturnal && this.theEntity.worldObj.isDaytime())
/*     */     {
/*  48 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  52 */     Village village = this.theEntity.worldObj.getVillageCollection().getNearestVillage(new BlockPos((Entity)this.theEntity), 0);
/*     */     
/*  54 */     if (village == null)
/*     */     {
/*  56 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  60 */     this.doorInfo = findNearestDoor(village);
/*     */     
/*  62 */     if (this.doorInfo == null)
/*     */     {
/*  64 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  68 */     PathNavigateGround pathnavigateground = (PathNavigateGround)this.theEntity.getNavigator();
/*  69 */     boolean flag = pathnavigateground.getEnterDoors();
/*  70 */     pathnavigateground.setBreakDoors(false);
/*  71 */     this.entityPathNavigate = pathnavigateground.getPathToPos(this.doorInfo.getDoorBlockPos());
/*  72 */     pathnavigateground.setBreakDoors(flag);
/*     */     
/*  74 */     if (this.entityPathNavigate != null)
/*     */     {
/*  76 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  80 */     Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 10, 7, new Vec3(this.doorInfo.getDoorBlockPos().getX(), this.doorInfo.getDoorBlockPos().getY(), this.doorInfo.getDoorBlockPos().getZ()));
/*     */     
/*  82 */     if (vec3 == null)
/*     */     {
/*  84 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  88 */     pathnavigateground.setBreakDoors(false);
/*  89 */     this.entityPathNavigate = this.theEntity.getNavigator().getPathToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord);
/*  90 */     pathnavigateground.setBreakDoors(flag);
/*  91 */     return (this.entityPathNavigate != null);
/*     */   }
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
/*     */   public boolean continueExecuting() {
/* 104 */     if (this.theEntity.getNavigator().noPath())
/*     */     {
/* 106 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 110 */     float f = this.theEntity.width + 4.0F;
/* 111 */     return (this.theEntity.getDistanceSq(this.doorInfo.getDoorBlockPos()) > (f * f));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/* 120 */     this.theEntity.getNavigator().setPath(this.entityPathNavigate, this.movementSpeed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/* 128 */     if (this.theEntity.getNavigator().noPath() || this.theEntity.getDistanceSq(this.doorInfo.getDoorBlockPos()) < 16.0D)
/*     */     {
/* 130 */       this.doorList.add(this.doorInfo);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private VillageDoorInfo findNearestDoor(Village villageIn) {
/* 136 */     VillageDoorInfo villagedoorinfo = null;
/* 137 */     int i = Integer.MAX_VALUE;
/*     */     
/* 139 */     for (VillageDoorInfo villagedoorinfo1 : villageIn.getVillageDoorInfoList()) {
/*     */       
/* 141 */       int j = villagedoorinfo1.getDistanceSquared(MathHelper.floor_double(this.theEntity.posX), MathHelper.floor_double(this.theEntity.posY), MathHelper.floor_double(this.theEntity.posZ));
/*     */       
/* 143 */       if (j < i && !doesDoorListContain(villagedoorinfo1)) {
/*     */         
/* 145 */         villagedoorinfo = villagedoorinfo1;
/* 146 */         i = j;
/*     */       } 
/*     */     } 
/*     */     
/* 150 */     return villagedoorinfo;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean doesDoorListContain(VillageDoorInfo doorInfoIn) {
/* 155 */     for (VillageDoorInfo villagedoorinfo : this.doorList) {
/*     */       
/* 157 */       if (doorInfoIn.getDoorBlockPos().equals(villagedoorinfo.getDoorBlockPos()))
/*     */       {
/* 159 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 163 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void resizeDoorList() {
/* 168 */     if (this.doorList.size() > 15)
/*     */     {
/* 170 */       this.doorList.remove(0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\ai\EntityAIMoveThroughVillage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */