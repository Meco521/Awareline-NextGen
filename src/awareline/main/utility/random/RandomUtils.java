/*    */ package awareline.main.utility.random;
/*    */ 
/*    */ import java.security.SecureRandom;
/*    */ 
/*    */ public class RandomUtils {
/*  6 */   private static final SecureRandom secureRandom = new SecureRandom();
/*    */   
/*    */   public static int nextInt(int min, int max) {
/*  9 */     return (int)nextDouble(min, max);
/*    */   }
/*    */   
/*    */   public static float nextFloat(float min, float max) {
/* 13 */     return (float)nextDouble(min, max);
/*    */   }
/*    */   
/*    */   public static double nextDouble(double min, double max) {
/* 17 */     return max + (min - max) * secureRandom.nextDouble();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\random\RandomUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */