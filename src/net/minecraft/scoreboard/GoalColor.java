/*    */ package net.minecraft.scoreboard;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ 
/*    */ 
/*    */ public class GoalColor
/*    */   implements IScoreObjectiveCriteria
/*    */ {
/*    */   private final String goalName;
/*    */   
/*    */   public GoalColor(String p_i45549_1_, EnumChatFormatting p_i45549_2_) {
/* 14 */     this.goalName = p_i45549_1_ + p_i45549_2_.getFriendlyName();
/* 15 */     IScoreObjectiveCriteria.INSTANCES.put(this.goalName, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 20 */     return this.goalName;
/*    */   }
/*    */ 
/*    */   
/*    */   public int setScore(List<EntityPlayer> p_96635_1_) {
/* 25 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isReadOnly() {
/* 30 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public IScoreObjectiveCriteria.EnumRenderType getRenderType() {
/* 35 */     return IScoreObjectiveCriteria.EnumRenderType.INTEGER;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\scoreboard\GoalColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */