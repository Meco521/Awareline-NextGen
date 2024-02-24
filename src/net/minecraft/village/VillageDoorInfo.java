/*     */ package net.minecraft.village;
/*     */ 
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Vec3i;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VillageDoorInfo
/*     */ {
/*     */   private final BlockPos doorBlockPos;
/*     */   private final BlockPos insideBlock;
/*     */   private final EnumFacing insideDirection;
/*     */   private int lastActivityTimestamp;
/*     */   private boolean isDetachedFromVillageFlag;
/*     */   private int doorOpeningRestrictionCounter;
/*     */   
/*     */   public VillageDoorInfo(BlockPos pos, int p_i45871_2_, int p_i45871_3_, int p_i45871_4_) {
/*  20 */     this(pos, getFaceDirection(p_i45871_2_, p_i45871_3_), p_i45871_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   private static EnumFacing getFaceDirection(int deltaX, int deltaZ) {
/*  25 */     return (deltaX < 0) ? EnumFacing.WEST : ((deltaX > 0) ? EnumFacing.EAST : ((deltaZ < 0) ? EnumFacing.NORTH : EnumFacing.SOUTH));
/*     */   }
/*     */ 
/*     */   
/*     */   public VillageDoorInfo(BlockPos pos, EnumFacing facing, int timestamp) {
/*  30 */     this.doorBlockPos = pos;
/*  31 */     this.insideDirection = facing;
/*  32 */     this.insideBlock = pos.offset(facing, 2);
/*  33 */     this.lastActivityTimestamp = timestamp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDistanceSquared(int x, int y, int z) {
/*  41 */     return (int)this.doorBlockPos.distanceSq(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDistanceToDoorBlockSq(BlockPos pos) {
/*  46 */     return (int)pos.distanceSq((Vec3i)this.doorBlockPos);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDistanceToInsideBlockSq(BlockPos pos) {
/*  51 */     return (int)this.insideBlock.distanceSq((Vec3i)pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_179850_c(BlockPos pos) {
/*  56 */     int i = pos.getX() - this.doorBlockPos.getX();
/*  57 */     int j = pos.getZ() - this.doorBlockPos.getY();
/*  58 */     return (i * this.insideDirection.getFrontOffsetX() + j * this.insideDirection.getFrontOffsetZ() >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetDoorOpeningRestrictionCounter() {
/*  63 */     this.doorOpeningRestrictionCounter = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void incrementDoorOpeningRestrictionCounter() {
/*  68 */     this.doorOpeningRestrictionCounter++;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDoorOpeningRestrictionCounter() {
/*  73 */     return this.doorOpeningRestrictionCounter;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getDoorBlockPos() {
/*  78 */     return this.doorBlockPos;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getInsideBlockPos() {
/*  83 */     return this.insideBlock;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInsideOffsetX() {
/*  88 */     return this.insideDirection.getFrontOffsetX() << 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInsideOffsetZ() {
/*  93 */     return this.insideDirection.getFrontOffsetZ() << 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInsidePosY() {
/*  98 */     return this.lastActivityTimestamp;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_179849_a(int timestamp) {
/* 103 */     this.lastActivityTimestamp = timestamp;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getIsDetachedFromVillageFlag() {
/* 108 */     return this.isDetachedFromVillageFlag;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIsDetachedFromVillageFlag(boolean detached) {
/* 113 */     this.isDetachedFromVillageFlag = detached;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\village\VillageDoorInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */