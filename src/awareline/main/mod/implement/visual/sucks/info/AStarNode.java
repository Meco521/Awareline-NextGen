/*    */ package awareline.main.mod.implement.visual.sucks.info;
/*    */ 
/*    */ public class AStarNode {
/*    */   private double x;
/*    */   private double z;
/*    */   private double heuristic;
/*    */   private AStarNode parent;
/*    */   
/*    */   public AStarNode(double x, double z) {
/* 10 */     this.x = x;
/* 11 */     this.z = z;
/*    */   }
/*    */   
/*    */   public AStarNode getParent() {
/* 15 */     return this.parent;
/*    */   }
/*    */   
/*    */   public void setParent(AStarNode parent) {
/* 19 */     this.parent = parent;
/*    */   }
/*    */   
/*    */   public double getX() {
/* 23 */     return this.x;
/*    */   }
/*    */   
/*    */   public void setX(int x) {
/* 27 */     this.x = x;
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 31 */     return this.z;
/*    */   }
/*    */   
/*    */   public void setZ(int y) {
/* 35 */     this.z = y;
/*    */   }
/*    */   
/*    */   public double getHeuristic() {
/* 39 */     return this.heuristic;
/*    */   }
/*    */   
/*    */   public void setHeuristic(int heuristic) {
/* 43 */     this.heuristic = heuristic;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\sucks\info\AStarNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */