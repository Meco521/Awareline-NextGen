/*    */ package net.minecraft.world.border;
/*    */ 
/*    */ public enum EnumBorderStatus
/*    */ {
/*  5 */   GROWING(4259712),
/*  6 */   SHRINKING(16724016),
/*  7 */   STATIONARY(2138367);
/*    */   
/*    */   private final int id;
/*    */ 
/*    */   
/*    */   EnumBorderStatus(int id) {
/* 13 */     this.id = id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getID() {
/* 22 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\border\EnumBorderStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */