/*    */ package awareline.main.mod.implement.combat.advanced.sucks.utils;
/*    */ 
/*    */ import org.apache.commons.lang3.RandomUtils;
/*    */ 
/*    */ public class TimeUtils {
/*    */   public long randomDelay(int minDelay, int maxDelay) {
/*  7 */     return RandomUtils.nextInt(minDelay, maxDelay);
/*    */   }
/*    */   
/*    */   public long randomClickDelay(int minCPS, int maxCPS) {
/* 11 */     return (long)(Math.random() * (1000 / minCPS - 1000 / maxCPS + 1) + (1000 / maxCPS));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\advanced\suck\\utils\TimeUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */