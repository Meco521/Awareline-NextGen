/*    */ package awareline.main.utility.math;
/*    */ 
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MathConst
/*    */ {
/*    */   public static final float PI = 3.1415927F;
/*    */   public static final float TO_RADIANS = 0.017453292F;
/*    */   public static final float TO_DEGREES = 57.295776F;
/* 16 */   public static final float[] COSINE = new float[361];
/* 17 */   public static final float[] SINE = new float[361];
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void calculate() {
/* 23 */     for (int i = 0; i <= 360; i++) {
/* 24 */       COSINE[i] = MathHelper.cos(i * 0.017453292F);
/* 25 */       SINE[i] = MathHelper.sin(i * 0.017453292F);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int toIntDegree(float angle) {
/* 36 */     return (int)(angle % 360.0F + 360.0F) % 360;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\math\MathConst.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */