/*     */ package net.minecraft.stats;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.IJsonSerializable;
/*     */ import net.minecraft.util.StatCollector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Achievement
/*     */   extends StatBase
/*     */ {
/*     */   public final int displayColumn;
/*     */   public final int displayRow;
/*     */   public final Achievement parentAchievement;
/*     */   private final String achievementDescription;
/*     */   private IStatStringFormat statStringFormatter;
/*     */   public final ItemStack theItemStack;
/*     */   private boolean isSpecial;
/*     */   
/*     */   public Achievement(String statIdIn, String unlocalizedName, int column, int row, Item itemIn, Achievement parent) {
/*  49 */     this(statIdIn, unlocalizedName, column, row, new ItemStack(itemIn), parent);
/*     */   }
/*     */ 
/*     */   
/*     */   public Achievement(String statIdIn, String unlocalizedName, int column, int row, Block blockIn, Achievement parent) {
/*  54 */     this(statIdIn, unlocalizedName, column, row, new ItemStack(blockIn), parent);
/*     */   }
/*     */ 
/*     */   
/*     */   public Achievement(String statIdIn, String unlocalizedName, int column, int row, ItemStack stack, Achievement parent) {
/*  59 */     super(statIdIn, (IChatComponent)new ChatComponentTranslation("achievement." + unlocalizedName, new Object[0]));
/*  60 */     this.theItemStack = stack;
/*  61 */     this.achievementDescription = "achievement." + unlocalizedName + ".desc";
/*  62 */     this.displayColumn = column;
/*  63 */     this.displayRow = row;
/*     */     
/*  65 */     if (column < AchievementList.minDisplayColumn)
/*     */     {
/*  67 */       AchievementList.minDisplayColumn = column;
/*     */     }
/*     */     
/*  70 */     if (row < AchievementList.minDisplayRow)
/*     */     {
/*  72 */       AchievementList.minDisplayRow = row;
/*     */     }
/*     */     
/*  75 */     if (column > AchievementList.maxDisplayColumn)
/*     */     {
/*  77 */       AchievementList.maxDisplayColumn = column;
/*     */     }
/*     */     
/*  80 */     if (row > AchievementList.maxDisplayRow)
/*     */     {
/*  82 */       AchievementList.maxDisplayRow = row;
/*     */     }
/*     */     
/*  85 */     this.parentAchievement = parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Achievement initIndependentStat() {
/*  94 */     this.isIndependent = true;
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Achievement setSpecial() {
/* 104 */     this.isSpecial = true;
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Achievement registerStat() {
/* 113 */     super.registerStat();
/* 114 */     AchievementList.achievementList.add(this);
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAchievement() {
/* 123 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public IChatComponent getStatName() {
/* 128 */     IChatComponent ichatcomponent = super.getStatName();
/* 129 */     ichatcomponent.getChatStyle().setColor(this.isSpecial ? EnumChatFormatting.DARK_PURPLE : EnumChatFormatting.GREEN);
/* 130 */     return ichatcomponent;
/*     */   }
/*     */ 
/*     */   
/*     */   public Achievement func_150953_b(Class<? extends IJsonSerializable> p_150953_1_) {
/* 135 */     return (Achievement)super.func_150953_b(p_150953_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 143 */     return (this.statStringFormatter != null) ? this.statStringFormatter.formatString(StatCollector.translateToLocal(this.achievementDescription)) : StatCollector.translateToLocal(this.achievementDescription);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Achievement setStatStringFormatter(IStatStringFormat statStringFormatterIn) {
/* 153 */     this.statStringFormatter = statStringFormatterIn;
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getSpecial() {
/* 163 */     return this.isSpecial;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\stats\Achievement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */