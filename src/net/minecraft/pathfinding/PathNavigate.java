/*     */ package net.minecraft.pathfinding;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.ChunkCache;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
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
/*     */ public abstract class PathNavigate
/*     */ {
/*     */   protected EntityLiving theEntity;
/*     */   protected World worldObj;
/*     */   protected PathEntity currentPath;
/*     */   protected double speed;
/*     */   private final IAttributeInstance pathSearchRange;
/*     */   private int totalTicks;
/*     */   private int ticksAtLastPos;
/*  41 */   private Vec3 lastPosCheck = new Vec3(0.0D, 0.0D, 0.0D);
/*  42 */   private float heightRequirement = 1.0F;
/*     */   
/*     */   private final PathFinder pathFinder;
/*     */   
/*     */   public PathNavigate(EntityLiving entitylivingIn, World worldIn) {
/*  47 */     this.theEntity = entitylivingIn;
/*  48 */     this.worldObj = worldIn;
/*  49 */     this.pathSearchRange = entitylivingIn.getEntityAttribute(SharedMonsterAttributes.followRange);
/*  50 */     this.pathFinder = getPathFinder();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract PathFinder getPathFinder();
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpeed(double speedIn) {
/*  60 */     this.speed = speedIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPathSearchRange() {
/*  68 */     return (float)this.pathSearchRange.getAttributeValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final PathEntity getPathToXYZ(double x, double y, double z) {
/*  76 */     return getPathToPos(new BlockPos(MathHelper.floor_double(x), (int)y, MathHelper.floor_double(z)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathEntity getPathToPos(BlockPos pos) {
/*  84 */     if (!canNavigate())
/*     */     {
/*  86 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  90 */     float f = getPathSearchRange();
/*  91 */     this.worldObj.theProfiler.startSection("pathfind");
/*  92 */     BlockPos blockpos = new BlockPos((Entity)this.theEntity);
/*  93 */     int i = (int)(f + 8.0F);
/*  94 */     ChunkCache chunkcache = new ChunkCache(this.worldObj, blockpos.add(-i, -i, -i), blockpos.add(i, i, i), 0);
/*  95 */     PathEntity pathentity = this.pathFinder.createEntityPathTo((IBlockAccess)chunkcache, (Entity)this.theEntity, pos, f);
/*  96 */     this.worldObj.theProfiler.endSection();
/*  97 */     return pathentity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tryMoveToXYZ(double x, double y, double z, double speedIn) {
/* 106 */     PathEntity pathentity = getPathToXYZ(MathHelper.floor_double(x), (int)y, MathHelper.floor_double(z));
/* 107 */     return setPath(pathentity, speedIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeightRequirement(float jumpHeight) {
/* 115 */     this.heightRequirement = jumpHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathEntity getPathToEntityLiving(Entity entityIn) {
/* 123 */     if (!canNavigate())
/*     */     {
/* 125 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 129 */     float f = getPathSearchRange();
/* 130 */     this.worldObj.theProfiler.startSection("pathfind");
/* 131 */     BlockPos blockpos = (new BlockPos((Entity)this.theEntity)).up();
/* 132 */     int i = (int)(f + 16.0F);
/* 133 */     ChunkCache chunkcache = new ChunkCache(this.worldObj, blockpos.add(-i, -i, -i), blockpos.add(i, i, i), 0);
/* 134 */     PathEntity pathentity = this.pathFinder.createEntityPathTo((IBlockAccess)chunkcache, (Entity)this.theEntity, entityIn, f);
/* 135 */     this.worldObj.theProfiler.endSection();
/* 136 */     return pathentity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tryMoveToEntityLiving(Entity entityIn, double speedIn) {
/* 145 */     PathEntity pathentity = getPathToEntityLiving(entityIn);
/* 146 */     return (pathentity != null) ? setPath(pathentity, speedIn) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setPath(PathEntity pathentityIn, double speedIn) {
/* 155 */     if (pathentityIn == null) {
/*     */       
/* 157 */       this.currentPath = null;
/* 158 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 162 */     if (!pathentityIn.isSamePath(this.currentPath))
/*     */     {
/* 164 */       this.currentPath = pathentityIn;
/*     */     }
/*     */     
/* 167 */     removeSunnyPath();
/*     */     
/* 169 */     if (this.currentPath.getCurrentPathLength() == 0)
/*     */     {
/* 171 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 175 */     this.speed = speedIn;
/* 176 */     Vec3 vec3 = getEntityPosition();
/* 177 */     this.ticksAtLastPos = this.totalTicks;
/* 178 */     this.lastPosCheck = vec3;
/* 179 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathEntity getPath() {
/* 189 */     return this.currentPath;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdateNavigation() {
/* 194 */     this.totalTicks++;
/*     */     
/* 196 */     if (!noPath()) {
/*     */       
/* 198 */       if (canNavigate()) {
/*     */         
/* 200 */         pathFollow();
/*     */       }
/* 202 */       else if (this.currentPath != null && this.currentPath.getCurrentPathIndex() < this.currentPath.getCurrentPathLength()) {
/*     */         
/* 204 */         Vec3 vec3 = getEntityPosition();
/* 205 */         Vec3 vec31 = this.currentPath.getVectorFromIndex((Entity)this.theEntity, this.currentPath.getCurrentPathIndex());
/*     */         
/* 207 */         if (vec3.yCoord > vec31.yCoord && !this.theEntity.onGround && MathHelper.floor_double(vec3.xCoord) == MathHelper.floor_double(vec31.xCoord) && MathHelper.floor_double(vec3.zCoord) == MathHelper.floor_double(vec31.zCoord))
/*     */         {
/* 209 */           this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);
/*     */         }
/*     */       } 
/*     */       
/* 213 */       if (!noPath()) {
/*     */         
/* 215 */         Vec3 vec32 = this.currentPath.getPosition((Entity)this.theEntity);
/*     */         
/* 217 */         if (vec32 != null) {
/*     */           
/* 219 */           AxisAlignedBB axisalignedbb1 = (new AxisAlignedBB(vec32.xCoord, vec32.yCoord, vec32.zCoord, vec32.xCoord, vec32.yCoord, vec32.zCoord)).expand(0.5D, 0.5D, 0.5D);
/* 220 */           List<AxisAlignedBB> list = this.worldObj.getCollidingBoundingBoxes((Entity)this.theEntity, axisalignedbb1.addCoord(0.0D, -1.0D, 0.0D));
/* 221 */           double d0 = -1.0D;
/* 222 */           axisalignedbb1 = axisalignedbb1.offset(0.0D, 1.0D, 0.0D);
/*     */           
/* 224 */           for (AxisAlignedBB axisalignedbb : list)
/*     */           {
/* 226 */             d0 = axisalignedbb.calculateYOffset(axisalignedbb1, d0);
/*     */           }
/*     */           
/* 229 */           this.theEntity.getMoveHelper().setMoveTo(vec32.xCoord, vec32.yCoord + d0, vec32.zCoord, this.speed);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void pathFollow() {
/* 237 */     Vec3 vec3 = getEntityPosition();
/* 238 */     int i = this.currentPath.getCurrentPathLength();
/*     */     
/* 240 */     for (int j = this.currentPath.getCurrentPathIndex(); j < this.currentPath.getCurrentPathLength(); j++) {
/*     */       
/* 242 */       if ((this.currentPath.getPathPointFromIndex(j)).yCoord != (int)vec3.yCoord) {
/*     */         
/* 244 */         i = j;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 249 */     float f = this.theEntity.width * this.theEntity.width * this.heightRequirement;
/*     */     
/* 251 */     for (int k = this.currentPath.getCurrentPathIndex(); k < i; k++) {
/*     */       
/* 253 */       Vec3 vec31 = this.currentPath.getVectorFromIndex((Entity)this.theEntity, k);
/*     */       
/* 255 */       if (vec3.squareDistanceTo(vec31) < f)
/*     */       {
/* 257 */         this.currentPath.setCurrentPathIndex(k + 1);
/*     */       }
/*     */     } 
/*     */     
/* 261 */     int j1 = MathHelper.ceiling_float_int(this.theEntity.width);
/* 262 */     int k1 = (int)this.theEntity.height + 1;
/* 263 */     int l = j1;
/*     */     
/* 265 */     for (int i1 = i - 1; i1 >= this.currentPath.getCurrentPathIndex(); i1--) {
/*     */       
/* 267 */       if (isDirectPathBetweenPoints(vec3, this.currentPath.getVectorFromIndex((Entity)this.theEntity, i1), j1, k1, l)) {
/*     */         
/* 269 */         this.currentPath.setCurrentPathIndex(i1);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 274 */     checkForStuck(vec3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkForStuck(Vec3 positionVec3) {
/* 283 */     if (this.totalTicks - this.ticksAtLastPos > 100) {
/*     */       
/* 285 */       if (positionVec3.squareDistanceTo(this.lastPosCheck) < 2.25D)
/*     */       {
/* 287 */         clearPathEntity();
/*     */       }
/*     */       
/* 290 */       this.ticksAtLastPos = this.totalTicks;
/* 291 */       this.lastPosCheck = positionVec3;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean noPath() {
/* 300 */     return (this.currentPath == null || this.currentPath.isFinished());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearPathEntity() {
/* 308 */     this.currentPath = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Vec3 getEntityPosition();
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean canNavigate();
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isInLiquid() {
/* 323 */     return (this.theEntity.isInWater() || this.theEntity.isInLava());
/*     */   }
/*     */   
/*     */   protected void removeSunnyPath() {}
/*     */   
/*     */   protected abstract boolean isDirectPathBetweenPoints(Vec3 paramVec31, Vec3 paramVec32, int paramInt1, int paramInt2, int paramInt3);
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\pathfinding\PathNavigate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */