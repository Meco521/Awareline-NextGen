/*     */ package net.minecraft.scoreboard;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collection;
/*     */ import java.util.Set;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ 
/*     */ public class ScorePlayerTeam
/*     */   extends Team
/*     */ {
/*     */   private final Scoreboard theScoreboard;
/*     */   private final String registeredName;
/*  13 */   private final Set<String> membershipSet = Sets.newHashSet();
/*     */   private String teamNameSPT;
/*  15 */   private String namePrefixSPT = "";
/*  16 */   private String colorSuffix = "";
/*     */   private boolean allowFriendlyFire = true;
/*     */   private boolean canSeeFriendlyInvisibles = true;
/*  19 */   private Team.EnumVisible nameTagVisibility = Team.EnumVisible.ALWAYS;
/*  20 */   private Team.EnumVisible deathMessageVisibility = Team.EnumVisible.ALWAYS;
/*  21 */   private EnumChatFormatting chatFormat = EnumChatFormatting.RESET;
/*     */ 
/*     */   
/*     */   public ScorePlayerTeam(Scoreboard theScoreboardIn, String name) {
/*  25 */     this.theScoreboard = theScoreboardIn;
/*  26 */     this.registeredName = name;
/*  27 */     this.teamNameSPT = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRegisteredName() {
/*  35 */     return this.registeredName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTeamName() {
/*  40 */     return this.teamNameSPT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTeamName(String name) {
/*  45 */     if (name == null)
/*     */     {
/*  47 */       throw new IllegalArgumentException("Name cannot be null");
/*     */     }
/*     */ 
/*     */     
/*  51 */     this.teamNameSPT = name;
/*  52 */     this.theScoreboard.sendTeamUpdate(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<String> getMembershipCollection() {
/*  58 */     return this.membershipSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getColorPrefix() {
/*  66 */     return this.namePrefixSPT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNamePrefix(String prefix) {
/*  71 */     if (prefix == null)
/*     */     {
/*  73 */       throw new IllegalArgumentException("Prefix cannot be null");
/*     */     }
/*     */ 
/*     */     
/*  77 */     this.namePrefixSPT = prefix;
/*  78 */     this.theScoreboard.sendTeamUpdate(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getColorSuffix() {
/*  87 */     return this.colorSuffix;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNameSuffix(String suffix) {
/*  92 */     this.colorSuffix = suffix;
/*  93 */     this.theScoreboard.sendTeamUpdate(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String formatString(String input) {
/*  98 */     return this.namePrefixSPT + input + this.colorSuffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatPlayerName(Team p_96667_0_, String p_96667_1_) {
/* 106 */     return (p_96667_0_ == null) ? p_96667_1_ : p_96667_0_.formatString(p_96667_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getAllowFriendlyFire() {
/* 111 */     return this.allowFriendlyFire;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAllowFriendlyFire(boolean friendlyFire) {
/* 116 */     this.allowFriendlyFire = friendlyFire;
/* 117 */     this.theScoreboard.sendTeamUpdate(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getSeeFriendlyInvisiblesEnabled() {
/* 122 */     return this.canSeeFriendlyInvisibles;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSeeFriendlyInvisiblesEnabled(boolean friendlyInvisibles) {
/* 127 */     this.canSeeFriendlyInvisibles = friendlyInvisibles;
/* 128 */     this.theScoreboard.sendTeamUpdate(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Team.EnumVisible getNameTagVisibility() {
/* 133 */     return this.nameTagVisibility;
/*     */   }
/*     */ 
/*     */   
/*     */   public Team.EnumVisible getDeathMessageVisibility() {
/* 138 */     return this.deathMessageVisibility;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNameTagVisibility(Team.EnumVisible p_178772_1_) {
/* 143 */     this.nameTagVisibility = p_178772_1_;
/* 144 */     this.theScoreboard.sendTeamUpdate(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDeathMessageVisibility(Team.EnumVisible p_178773_1_) {
/* 149 */     this.deathMessageVisibility = p_178773_1_;
/* 150 */     this.theScoreboard.sendTeamUpdate(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_98299_i() {
/* 155 */     int i = 0;
/*     */     
/* 157 */     if (this.allowFriendlyFire)
/*     */     {
/* 159 */       i |= 0x1;
/*     */     }
/*     */     
/* 162 */     if (this.canSeeFriendlyInvisibles)
/*     */     {
/* 164 */       i |= 0x2;
/*     */     }
/*     */     
/* 167 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_98298_a(int p_98298_1_) {
/* 172 */     setAllowFriendlyFire(((p_98298_1_ & 0x1) > 0));
/* 173 */     setSeeFriendlyInvisiblesEnabled(((p_98298_1_ & 0x2) > 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChatFormat(EnumChatFormatting p_178774_1_) {
/* 178 */     this.chatFormat = p_178774_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumChatFormatting getChatFormat() {
/* 183 */     return this.chatFormat;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\scoreboard\ScorePlayerTeam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */