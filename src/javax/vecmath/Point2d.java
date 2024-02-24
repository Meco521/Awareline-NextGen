/*    */ package javax.vecmath;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Point2d
/*    */   extends Tuple2d
/*    */ {
/*    */   static final long serialVersionUID = 1133748791492571954L;
/*    */   
/*    */   public final double distanceSquared(Point2d p1) {
/* 49 */     double dx = this.x - p1.x;
/* 50 */     double dy = this.y - p1.y;
/* 51 */     return dx * dx + dy * dy;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final double distance(Point2d p1) {
/* 62 */     double dx = this.x - p1.x;
/* 63 */     double dy = this.y - p1.y;
/* 64 */     return Math.sqrt(dx * dx + dy * dy);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final double distanceL1(Point2d p1) {
/* 75 */     return Math.abs(this.x - p1.x) + Math.abs(this.y - p1.y);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final double distanceLinf(Point2d p1) {
/* 86 */     return Math.max(Math.abs(this.x - p1.x), Math.abs(this.y - p1.y));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\javax\vecmath\Point2d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */