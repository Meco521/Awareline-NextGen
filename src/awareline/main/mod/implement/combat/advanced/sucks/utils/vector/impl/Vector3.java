/*    */ package awareline.main.mod.implement.combat.advanced.sucks.utils.vector.impl;
/*    */ 
/*    */ import awareline.main.mod.implement.combat.advanced.sucks.utils.vector.Vector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Vector3<T extends Number>
/*    */   extends Vector<Number>
/*    */ {
/*    */   public Vector3(T x, T y, T z) {
/* 12 */     super((Number)x, (Number)y, (Number)z);
/*    */   }
/*    */   
/*    */   public Vector2<T> toVector2() {
/* 16 */     return new Vector2<>((T)getX(), (T)getY());
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\advanced\suck\\utils\vector\impl\Vector3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */