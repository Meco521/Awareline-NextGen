/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MovingObjectPosition
/*    */ {
/*    */   private BlockPos blockPos;
/*    */   public MovingObjectType typeOfHit;
/*    */   public EnumFacing sideHit;
/*    */   public Vec3 hitVec;
/*    */   public Entity entityHit;
/*    */   
/*    */   public MovingObjectPosition(Vec3 hitVecIn, EnumFacing facing, BlockPos blockPosIn) {
/* 22 */     this(MovingObjectType.BLOCK, hitVecIn, facing, blockPosIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public MovingObjectPosition(Vec3 p_i45552_1_, EnumFacing facing) {
/* 27 */     this(MovingObjectType.BLOCK, p_i45552_1_, facing, BlockPos.ORIGIN);
/*    */   }
/*    */ 
/*    */   
/*    */   public MovingObjectPosition(Entity entityIn) {
/* 32 */     this(entityIn, new Vec3(entityIn.posX, entityIn.posY, entityIn.posZ));
/*    */   }
/*    */ 
/*    */   
/*    */   public MovingObjectPosition(MovingObjectType typeOfHitIn, Vec3 hitVecIn, EnumFacing sideHitIn, BlockPos blockPosIn) {
/* 37 */     this.typeOfHit = typeOfHitIn;
/* 38 */     this.blockPos = blockPosIn;
/* 39 */     this.sideHit = sideHitIn;
/* 40 */     this.hitVec = new Vec3(hitVecIn.xCoord, hitVecIn.yCoord, hitVecIn.zCoord);
/*    */   }
/*    */ 
/*    */   
/*    */   public MovingObjectPosition(Entity entityHitIn, Vec3 hitVecIn) {
/* 45 */     this.typeOfHit = MovingObjectType.ENTITY;
/* 46 */     this.entityHit = entityHitIn;
/* 47 */     this.hitVec = hitVecIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getBlockPos() {
/* 52 */     return this.blockPos;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 57 */     return "HitResult{type=" + this.typeOfHit + ", blockpos=" + this.blockPos + ", f=" + this.sideHit + ", pos=" + this.hitVec + ", entity=" + this.entityHit + '}';
/*    */   }
/*    */   
/*    */   public enum MovingObjectType
/*    */   {
/* 62 */     MISS,
/* 63 */     BLOCK,
/* 64 */     ENTITY;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\MovingObjectPosition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */