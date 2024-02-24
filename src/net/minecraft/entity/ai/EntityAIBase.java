/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class EntityAIBase
/*    */ {
/*    */   private int mutexBits;
/*    */   
/*    */   public abstract boolean shouldExecute();
/*    */   
/*    */   public boolean continueExecuting() {
/* 21 */     return shouldExecute();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isInterruptible() {
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMutexBits(int mutexBitsIn) {
/* 60 */     this.mutexBits = mutexBitsIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMutexBits() {
/* 69 */     return this.mutexBits;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\ai\EntityAIBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */