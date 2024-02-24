/*    */ package awareline.main.ui.font.obj;
/*    */ 
/*    */ public final class Delayer
/*    */ {
/*    */   private final boolean autoReset;
/*    */   private long last;
/*    */   
/*    */   public Delayer() {
/*  9 */     this(true);
/*    */   }
/*    */   
/*    */   public Delayer(boolean autoReset) {
/* 13 */     this.autoReset = autoReset;
/*    */   }
/*    */   
/*    */   public boolean check(double delay) {
/* 17 */     if (delay <= 0.0D) {
/* 18 */       if (this.autoReset) {
/* 19 */         reset();
/*    */       }
/*    */       
/* 22 */       return true;
/*    */     } 
/*    */     
/* 25 */     boolean b = ((System.currentTimeMillis() - this.last) >= delay);
/*    */     
/* 27 */     if (this.autoReset && b) {
/* 28 */       reset();
/*    */     }
/*    */     
/* 31 */     return b;
/*    */   }
/*    */   
/*    */   public boolean check(int delay) {
/* 35 */     if (delay <= 0) {
/* 36 */       if (this.autoReset) {
/* 37 */         reset();
/*    */       }
/*    */       
/* 40 */       return true;
/*    */     } 
/*    */     
/* 43 */     boolean b = (System.currentTimeMillis() - this.last >= delay);
/*    */     
/* 45 */     if (this.autoReset && b) {
/* 46 */       reset();
/*    */     }
/*    */     
/* 49 */     return b;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 53 */     this.last = System.currentTimeMillis();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\obj\Delayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */