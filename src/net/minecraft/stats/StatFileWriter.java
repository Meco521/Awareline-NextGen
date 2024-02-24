/*    */ package net.minecraft.stats;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.IJsonSerializable;
/*    */ import net.minecraft.util.TupleIntJsonSerializable;
/*    */ 
/*    */ 
/*    */ public class StatFileWriter
/*    */ {
/* 12 */   protected final Map<StatBase, TupleIntJsonSerializable> statsData = Maps.newConcurrentMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasAchievementUnlocked(Achievement achievementIn) {
/* 19 */     return (readStat(achievementIn) > 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canUnlockAchievement(Achievement achievementIn) {
/* 27 */     return (achievementIn.parentAchievement == null || hasAchievementUnlocked(achievementIn.parentAchievement));
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_150874_c(Achievement p_150874_1_) {
/* 32 */     if (hasAchievementUnlocked(p_150874_1_))
/*    */     {
/* 34 */       return 0;
/*    */     }
/*    */ 
/*    */     
/* 38 */     int i = 0;
/*    */     
/* 40 */     for (Achievement achievement = p_150874_1_.parentAchievement; achievement != null && !hasAchievementUnlocked(achievement); i++)
/*    */     {
/* 42 */       achievement = achievement.parentAchievement;
/*    */     }
/*    */     
/* 45 */     return i;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void increaseStat(EntityPlayer player, StatBase stat, int amount) {
/* 51 */     if (!stat.isAchievement() || canUnlockAchievement((Achievement)stat))
/*    */     {
/* 53 */       unlockAchievement(player, stat, readStat(stat) + amount);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void unlockAchievement(EntityPlayer playerIn, StatBase statIn, int p_150873_3_) {
/* 62 */     TupleIntJsonSerializable tupleintjsonserializable = this.statsData.get(statIn);
/*    */     
/* 64 */     if (tupleintjsonserializable == null) {
/*    */       
/* 66 */       tupleintjsonserializable = new TupleIntJsonSerializable();
/* 67 */       this.statsData.put(statIn, tupleintjsonserializable);
/*    */     } 
/*    */     
/* 70 */     tupleintjsonserializable.setIntegerValue(p_150873_3_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int readStat(StatBase stat) {
/* 78 */     TupleIntJsonSerializable tupleintjsonserializable = this.statsData.get(stat);
/* 79 */     return (tupleintjsonserializable == null) ? 0 : tupleintjsonserializable.getIntegerValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public <T extends IJsonSerializable> T func_150870_b(StatBase p_150870_1_) {
/* 84 */     TupleIntJsonSerializable tupleintjsonserializable = this.statsData.get(p_150870_1_);
/* 85 */     return (tupleintjsonserializable != null) ? (T)tupleintjsonserializable.getJsonSerializableValue() : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T extends IJsonSerializable> T func_150872_a(StatBase p_150872_1_, T p_150872_2_) {
/* 90 */     TupleIntJsonSerializable tupleintjsonserializable = this.statsData.get(p_150872_1_);
/*    */     
/* 92 */     if (tupleintjsonserializable == null) {
/*    */       
/* 94 */       tupleintjsonserializable = new TupleIntJsonSerializable();
/* 95 */       this.statsData.put(p_150872_1_, tupleintjsonserializable);
/*    */     } 
/*    */     
/* 98 */     tupleintjsonserializable.setJsonSerializableValue((IJsonSerializable)p_150872_2_);
/* 99 */     return p_150872_2_;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\stats\StatFileWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */