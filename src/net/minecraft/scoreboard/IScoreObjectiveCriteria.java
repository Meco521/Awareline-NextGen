/*    */ package net.minecraft.scoreboard;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ 
/*    */ 
/*    */ public interface IScoreObjectiveCriteria
/*    */ {
/* 12 */   public static final Map<String, IScoreObjectiveCriteria> INSTANCES = Maps.newHashMap();
/* 13 */   public static final IScoreObjectiveCriteria DUMMY = new ScoreDummyCriteria("dummy");
/* 14 */   public static final IScoreObjectiveCriteria TRIGGER = new ScoreDummyCriteria("trigger");
/* 15 */   public static final IScoreObjectiveCriteria deathCount = new ScoreDummyCriteria("deathCount");
/* 16 */   public static final IScoreObjectiveCriteria playerKillCount = new ScoreDummyCriteria("playerKillCount");
/* 17 */   public static final IScoreObjectiveCriteria totalKillCount = new ScoreDummyCriteria("totalKillCount");
/* 18 */   public static final IScoreObjectiveCriteria health = new ScoreHealthCriteria("health");
/* 19 */   public static final IScoreObjectiveCriteria[] field_178792_h = new IScoreObjectiveCriteria[] { new GoalColor("teamkill.", EnumChatFormatting.BLACK), new GoalColor("teamkill.", EnumChatFormatting.DARK_BLUE), new GoalColor("teamkill.", EnumChatFormatting.DARK_GREEN), new GoalColor("teamkill.", EnumChatFormatting.DARK_AQUA), new GoalColor("teamkill.", EnumChatFormatting.DARK_RED), new GoalColor("teamkill.", EnumChatFormatting.DARK_PURPLE), new GoalColor("teamkill.", EnumChatFormatting.GOLD), new GoalColor("teamkill.", EnumChatFormatting.GRAY), new GoalColor("teamkill.", EnumChatFormatting.DARK_GRAY), new GoalColor("teamkill.", EnumChatFormatting.BLUE), new GoalColor("teamkill.", EnumChatFormatting.GREEN), new GoalColor("teamkill.", EnumChatFormatting.AQUA), new GoalColor("teamkill.", EnumChatFormatting.RED), new GoalColor("teamkill.", EnumChatFormatting.LIGHT_PURPLE), new GoalColor("teamkill.", EnumChatFormatting.YELLOW), new GoalColor("teamkill.", EnumChatFormatting.WHITE) };
/* 20 */   public static final IScoreObjectiveCriteria[] field_178793_i = new IScoreObjectiveCriteria[] { new GoalColor("killedByTeam.", EnumChatFormatting.BLACK), new GoalColor("killedByTeam.", EnumChatFormatting.DARK_BLUE), new GoalColor("killedByTeam.", EnumChatFormatting.DARK_GREEN), new GoalColor("killedByTeam.", EnumChatFormatting.DARK_AQUA), new GoalColor("killedByTeam.", EnumChatFormatting.DARK_RED), new GoalColor("killedByTeam.", EnumChatFormatting.DARK_PURPLE), new GoalColor("killedByTeam.", EnumChatFormatting.GOLD), new GoalColor("killedByTeam.", EnumChatFormatting.GRAY), new GoalColor("killedByTeam.", EnumChatFormatting.DARK_GRAY), new GoalColor("killedByTeam.", EnumChatFormatting.BLUE), new GoalColor("killedByTeam.", EnumChatFormatting.GREEN), new GoalColor("killedByTeam.", EnumChatFormatting.AQUA), new GoalColor("killedByTeam.", EnumChatFormatting.RED), new GoalColor("killedByTeam.", EnumChatFormatting.LIGHT_PURPLE), new GoalColor("killedByTeam.", EnumChatFormatting.YELLOW), new GoalColor("killedByTeam.", EnumChatFormatting.WHITE) };
/*    */   
/*    */   String getName();
/*    */   
/*    */   int setScore(List<EntityPlayer> paramList);
/*    */   
/*    */   boolean isReadOnly();
/*    */   
/*    */   EnumRenderType getRenderType();
/*    */   
/*    */   public enum EnumRenderType
/*    */   {
/* 32 */     INTEGER("integer"),
/* 33 */     HEARTS("hearts");
/*    */     
/* 35 */     private static final Map<String, EnumRenderType> field_178801_c = Maps.newHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     private final String field_178798_d;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/* 55 */       for (EnumRenderType iscoreobjectivecriteria$enumrendertype : values())
/*    */       {
/* 57 */         field_178801_c.put(iscoreobjectivecriteria$enumrendertype.func_178796_a(), iscoreobjectivecriteria$enumrendertype);
/*    */       }
/*    */     }
/*    */     
/*    */     EnumRenderType(String p_i45548_3_) {
/*    */       this.field_178798_d = p_i45548_3_;
/*    */     }
/*    */     
/*    */     public String func_178796_a() {
/*    */       return this.field_178798_d;
/*    */     }
/*    */     
/*    */     public static EnumRenderType func_178795_a(String p_178795_0_) {
/*    */       EnumRenderType iscoreobjectivecriteria$enumrendertype = field_178801_c.get(p_178795_0_);
/*    */       return (iscoreobjectivecriteria$enumrendertype == null) ? INTEGER : iscoreobjectivecriteria$enumrendertype;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\scoreboard\IScoreObjectiveCriteria.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */