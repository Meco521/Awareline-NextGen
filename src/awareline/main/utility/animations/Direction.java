/*    */ package awareline.main.utility.animations;
/*    */ 
/*    */ public enum Direction {
/*  4 */   FORWARDS,
/*  5 */   BACKWARDS;
/*    */   
/*    */   public Direction opposite() {
/*  8 */     if (this == FORWARDS)
/*  9 */       return BACKWARDS; 
/* 10 */     return FORWARDS;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean forwards() {
/* 15 */     return (this == FORWARDS);
/*    */   }
/*    */   
/*    */   public boolean backwards() {
/* 19 */     return (this == BACKWARDS);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\animations\Direction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */