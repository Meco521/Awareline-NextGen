/*    */ package awareline.main.utility.render.render;
/*    */ 
/*    */ import java.awt.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Colors
/*    */ {
/*  9 */   BLACK(-16711423),
/* 10 */   BLUE(-12028161),
/* 11 */   INDIGO((new Color(63, 81, 181)).getRGB()),
/* 12 */   DARKBLUE(-12621684),
/* 13 */   GREEN(-9830551),
/* 14 */   DARKGREEN(-9320847),
/* 15 */   WHITE(-65794),
/* 16 */   AQUA(-7820064),
/* 17 */   DARKAQUA(-12621684),
/* 18 */   GREY(-9868951),
/* 19 */   DARKGREY(-14342875),
/* 20 */   RED(-65536),
/* 21 */   DARKRED(-8388608),
/* 22 */   ORANGE(-29696),
/* 23 */   DARKORANGE(-2263808),
/* 24 */   YELLOW(-256),
/* 25 */   DARKYELLOW(-2702025),
/* 26 */   MAGENTA(-18751),
/* 27 */   SLOWLY(-13220000),
/* 28 */   DARKMAGENTA(-2252579);
/*    */   
/*    */   public int c;
/*    */   
/*    */   Colors(int co) {
/* 33 */     this.c = co;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\render\Colors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */