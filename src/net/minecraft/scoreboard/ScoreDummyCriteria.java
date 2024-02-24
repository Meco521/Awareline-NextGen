/*    */ package net.minecraft.scoreboard;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ 
/*    */ public class ScoreDummyCriteria
/*    */   implements IScoreObjectiveCriteria
/*    */ {
/*    */   private final String dummyName;
/*    */   
/*    */   public ScoreDummyCriteria(String name) {
/* 13 */     this.dummyName = name;
/* 14 */     IScoreObjectiveCriteria.INSTANCES.put(name, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 19 */     return this.dummyName;
/*    */   }
/*    */ 
/*    */   
/*    */   public int setScore(List<EntityPlayer> p_96635_1_) {
/* 24 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isReadOnly() {
/* 29 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public IScoreObjectiveCriteria.EnumRenderType getRenderType() {
/* 34 */     return IScoreObjectiveCriteria.EnumRenderType.INTEGER;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\scoreboard\ScoreDummyCriteria.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */