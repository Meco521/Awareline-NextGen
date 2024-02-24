/*    */ package net.optifine.util;
/*    */ 
/*    */ 
/*    */ public class LockCounter
/*    */ {
/*    */   private int lockCount;
/*    */   
/*    */   public boolean lock() {
/*  9 */     this.lockCount++;
/* 10 */     return (this.lockCount == 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean unlock() {
/* 15 */     if (this.lockCount <= 0)
/*    */     {
/* 17 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 21 */     this.lockCount--;
/* 22 */     return (this.lockCount == 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isLocked() {
/* 28 */     return (this.lockCount > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLockCount() {
/* 33 */     return this.lockCount;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 38 */     return "lockCount: " + this.lockCount;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\LockCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */