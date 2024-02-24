/*    */ package net.minecraft.scoreboard;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ public class ScoreHealthCriteria
/*    */   extends ScoreDummyCriteria
/*    */ {
/*    */   public ScoreHealthCriteria(String name) {
/* 12 */     super(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public int setScore(List<EntityPlayer> p_96635_1_) {
/* 17 */     float f = 0.0F;
/*    */     
/* 19 */     for (EntityPlayer entityplayer : p_96635_1_)
/*    */     {
/* 21 */       f += entityplayer.getHealth() + entityplayer.getAbsorptionAmount();
/*    */     }
/*    */     
/* 24 */     if (!p_96635_1_.isEmpty())
/*    */     {
/* 26 */       f /= p_96635_1_.size();
/*    */     }
/*    */     
/* 29 */     return MathHelper.ceiling_float_int(f);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isReadOnly() {
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public IScoreObjectiveCriteria.EnumRenderType getRenderType() {
/* 39 */     return IScoreObjectiveCriteria.EnumRenderType.HEARTS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\scoreboard\ScoreHealthCriteria.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */