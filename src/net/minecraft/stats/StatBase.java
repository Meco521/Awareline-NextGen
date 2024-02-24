/*     */ package net.minecraft.stats;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.event.HoverEvent;
/*     */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.IJsonSerializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatBase
/*     */ {
/*     */   public final String statId;
/*     */   private final IChatComponent statName;
/*     */   public boolean isIndependent;
/*     */   private final IStatType type;
/*     */   private final IScoreObjectiveCriteria objectiveCriteria;
/*     */   private Class<? extends IJsonSerializable> field_150956_d;
/*  25 */   static NumberFormat numberFormat = NumberFormat.getIntegerInstance(Locale.US);
/*  26 */   public static IStatType simpleStatType = new IStatType()
/*     */     {
/*     */       public String format(int number)
/*     */       {
/*  30 */         return StatBase.numberFormat.format(number);
/*     */       }
/*     */     };
/*  33 */   static DecimalFormat decimalFormat = new DecimalFormat("########0.00");
/*  34 */   public static IStatType timeStatType = new IStatType()
/*     */     {
/*     */       public String format(int number)
/*     */       {
/*  38 */         double d0 = number / 20.0D;
/*  39 */         double d1 = d0 / 60.0D;
/*  40 */         double d2 = d1 / 60.0D;
/*  41 */         double d3 = d2 / 24.0D;
/*  42 */         double d4 = d3 / 365.0D;
/*  43 */         return (d4 > 0.5D) ? (StatBase.decimalFormat.format(d4) + " y") : ((d3 > 0.5D) ? (StatBase.decimalFormat.format(d3) + " d") : ((d2 > 0.5D) ? (StatBase.decimalFormat.format(d2) + " h") : ((d1 > 0.5D) ? (StatBase.decimalFormat.format(d1) + " m") : (d0 + " s"))));
/*     */       }
/*     */     };
/*  46 */   public static IStatType distanceStatType = new IStatType()
/*     */     {
/*     */       public String format(int number)
/*     */       {
/*  50 */         double d0 = number / 100.0D;
/*  51 */         double d1 = d0 / 1000.0D;
/*  52 */         return (d1 > 0.5D) ? (StatBase.decimalFormat.format(d1) + " km") : ((d0 > 0.5D) ? (StatBase.decimalFormat.format(d0) + " m") : (number + " cm"));
/*     */       }
/*     */     };
/*  55 */   public static IStatType field_111202_k = new IStatType()
/*     */     {
/*     */       public String format(int number)
/*     */       {
/*  59 */         return StatBase.decimalFormat.format(number * 0.1D);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public StatBase(String statIdIn, IChatComponent statNameIn, IStatType typeIn) {
/*  65 */     this.statId = statIdIn;
/*  66 */     this.statName = statNameIn;
/*  67 */     this.type = typeIn;
/*  68 */     this.objectiveCriteria = (IScoreObjectiveCriteria)new ObjectiveStat(this);
/*  69 */     IScoreObjectiveCriteria.INSTANCES.put(this.objectiveCriteria.getName(), this.objectiveCriteria);
/*     */   }
/*     */ 
/*     */   
/*     */   public StatBase(String statIdIn, IChatComponent statNameIn) {
/*  74 */     this(statIdIn, statNameIn, simpleStatType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatBase initIndependentStat() {
/*  83 */     this.isIndependent = true;
/*  84 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatBase registerStat() {
/*  92 */     if (StatList.oneShotStats.containsKey(this.statId))
/*     */     {
/*  94 */       throw new RuntimeException("Duplicate stat id: \"" + ((StatBase)StatList.oneShotStats.get(this.statId)).statName + "\" and \"" + this.statName + "\" at id " + this.statId);
/*     */     }
/*     */ 
/*     */     
/*  98 */     StatList.allStats.add(this);
/*  99 */     StatList.oneShotStats.put(this.statId, this);
/* 100 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAchievement() {
/* 109 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String format(int p_75968_1_) {
/* 114 */     return this.type.format(p_75968_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public IChatComponent getStatName() {
/* 119 */     IChatComponent ichatcomponent = this.statName.createCopy();
/* 120 */     ichatcomponent.getChatStyle().setColor(EnumChatFormatting.GRAY);
/* 121 */     ichatcomponent.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ACHIEVEMENT, (IChatComponent)new ChatComponentText(this.statId)));
/* 122 */     return ichatcomponent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent createChatComponent() {
/* 130 */     IChatComponent ichatcomponent = getStatName();
/* 131 */     IChatComponent ichatcomponent1 = (new ChatComponentText("[")).appendSibling(ichatcomponent).appendText("]");
/* 132 */     ichatcomponent1.setChatStyle(ichatcomponent.getChatStyle());
/* 133 */     return ichatcomponent1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 138 */     if (this == p_equals_1_)
/*     */     {
/* 140 */       return true;
/*     */     }
/* 142 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/*     */       
/* 144 */       StatBase statbase = (StatBase)p_equals_1_;
/* 145 */       return this.statId.equals(statbase.statId);
/*     */     } 
/*     */ 
/*     */     
/* 149 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 155 */     return this.statId.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 160 */     return "Stat{id=" + this.statId + ", nameId=" + this.statName + ", awardLocallyOnly=" + this.isIndependent + ", formatter=" + this.type + ", objectiveCriteria=" + this.objectiveCriteria + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IScoreObjectiveCriteria getCriteria() {
/* 168 */     return this.objectiveCriteria;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends IJsonSerializable> func_150954_l() {
/* 173 */     return this.field_150956_d;
/*     */   }
/*     */ 
/*     */   
/*     */   public StatBase func_150953_b(Class<? extends IJsonSerializable> p_150953_1_) {
/* 178 */     this.field_150956_d = p_150953_1_;
/* 179 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\stats\StatBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */