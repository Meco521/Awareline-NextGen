/*    */ package awareline.main.mod.implement.combat.advanced.sucks.utils.vector;
/*    */ 
/*    */ 
/*    */ public class Vector<T extends Number>
/*    */ {
/*    */   private T x;
/*    */   private T y;
/*    */   private T z;
/*    */   
/*    */   public Vector(T x, T y, T z) {
/* 11 */     this.x = x;
/* 12 */     this.y = y;
/* 13 */     this.z = z;
/*    */   }
/*    */   
/*    */   public Vector setX(T x) {
/* 17 */     this.x = x;
/* 18 */     return this;
/*    */   }
/*    */   
/*    */   public Vector setY(T y) {
/* 22 */     this.y = y;
/* 23 */     return this;
/*    */   }
/*    */   
/*    */   public Vector setZ(T z) {
/* 27 */     this.z = z;
/* 28 */     return this;
/*    */   }
/*    */   
/*    */   public T getX() {
/* 32 */     return this.x;
/*    */   }
/*    */   
/*    */   public T getY() {
/* 36 */     return this.y;
/*    */   }
/*    */   
/*    */   public T getZ() {
/* 40 */     return this.z;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\advanced\suck\\utils\vector\Vector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */