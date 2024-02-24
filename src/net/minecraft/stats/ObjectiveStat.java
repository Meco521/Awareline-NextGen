/*    */ package net.minecraft.stats;
/*    */ 
/*    */ import net.minecraft.scoreboard.ScoreDummyCriteria;
/*    */ 
/*    */ public class ObjectiveStat
/*    */   extends ScoreDummyCriteria
/*    */ {
/*    */   private final StatBase stat;
/*    */   
/*    */   public ObjectiveStat(StatBase statIn) {
/* 11 */     super(statIn.statId);
/* 12 */     this.stat = statIn;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\stats\ObjectiveStat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */