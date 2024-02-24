/*    */ package awareline.main.ui.font.obj;
/*    */ 
/*    */ public final class Locker {
/*  4 */   private final Object lock = new Object();
/*    */   
/*    */   public void lock() {
/*    */     try {
/*  8 */       synchronized (this.lock) {
/*  9 */         this.lock.wait();
/*    */       } 
/* 11 */     } catch (InterruptedException e) {
/* 12 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void unlock() {
/* 17 */     synchronized (this.lock) {
/* 18 */       this.lock.notify();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\obj\Locker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */