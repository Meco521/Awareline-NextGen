/*    */ package awareline.main.mod.implement.combat.advanced.sucks.utils.vector.impl;
/*    */ 
/*    */ import awareline.main.mod.implement.combat.advanced.sucks.utils.vector.Vector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Vector2<T extends Number>
/*    */   extends Vector<Number>
/*    */ {
/*    */   public Vector2(T x, T y) {
/* 12 */     super((Number)x, (Number)y, Integer.valueOf(0));
/*    */   }
/*    */   
/*    */   public Vector3<T> toVector3() {
/* 16 */     return new Vector3<>((T)getX(), (T)getY(), (T)getZ());
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\advanced\suck\\utils\vector\impl\Vector2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */