/*    */ package awareline.main.utility.render.color;
/*    */ 
/*    */ public enum Colors {
/*  4 */   BLACK(-16711423),
/*  5 */   WHITE(-65794),
/*  6 */   RED(-65536),
/*  7 */   ORANGE(-29696);
/*    */   
/*    */   public final int c;
/*    */   
/*    */   Colors(int co) {
/* 12 */     this.c = co;
/*    */   }
/*    */   
/*    */   public static int getColor(int brightness) {
/* 16 */     return getColor(brightness, brightness, brightness, 255);
/*    */   }
/*    */   
/*    */   public static int getColor(int brightness, int alpha) {
/* 20 */     return getColor(brightness, brightness, brightness, alpha);
/*    */   }
/*    */   
/*    */   public static int getColor(int red, int green, int blue) {
/* 24 */     return getColor(red, green, blue, 255);
/*    */   }
/*    */   
/*    */   public static int getColor(int red, int green, int blue, int alpha) {
/* 28 */     int color = 0;
/* 29 */     color |= alpha << 24;
/* 30 */     color |= red << 16;
/* 31 */     color |= green << 8;
/* 32 */     color |= blue;
/* 33 */     return color;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\color\Colors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */