/*    */ package awareline.main.utility.render.color;
/*    */ 
/*    */ public class ColorObject
/*    */ {
/*    */   public final int red;
/*    */   public final int green;
/*    */   public final int blue;
/*    */   public final int alpha;
/*    */   
/*    */   public ColorObject(int red, int green, int blue, int alpha) {
/* 11 */     this.red = red;
/* 12 */     this.green = green;
/* 13 */     this.blue = blue;
/* 14 */     this.alpha = alpha;
/*    */   }
/*    */   
/*    */   public int getColorInt() {
/* 18 */     return Colors.getColor(this.red, this.green, this.blue, this.alpha);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\color\ColorObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */