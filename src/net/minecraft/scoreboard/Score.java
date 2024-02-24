/*     */ package net.minecraft.scoreboard;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ 
/*     */ 
/*     */ public class Score
/*     */ {
/*  10 */   public static final Comparator<Score> scoreComparator = new Comparator<Score>()
/*     */     {
/*     */       public int compare(Score p_compare_1_, Score p_compare_2_)
/*     */       {
/*  14 */         return (p_compare_1_.getScorePoints() > p_compare_2_.getScorePoints()) ? 1 : ((p_compare_1_.getScorePoints() < p_compare_2_.getScorePoints()) ? -1 : p_compare_2_.getPlayerName().compareToIgnoreCase(p_compare_1_.getPlayerName()));
/*     */       }
/*     */     };
/*     */   
/*     */   private final Scoreboard theScoreboard;
/*     */   private final ScoreObjective theScoreObjective;
/*     */   private final String scorePlayerName;
/*     */   private int scorePoints;
/*     */   private boolean locked;
/*     */   private boolean forceUpdate;
/*     */   
/*     */   public Score(Scoreboard theScoreboardIn, ScoreObjective theScoreObjectiveIn, String scorePlayerNameIn) {
/*  26 */     this.theScoreboard = theScoreboardIn;
/*  27 */     this.theScoreObjective = theScoreObjectiveIn;
/*  28 */     this.scorePlayerName = scorePlayerNameIn;
/*  29 */     this.forceUpdate = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void increseScore(int amount) {
/*  34 */     if (this.theScoreObjective.getCriteria().isReadOnly())
/*     */     {
/*  36 */       throw new IllegalStateException("Cannot modify read-only score");
/*     */     }
/*     */ 
/*     */     
/*  40 */     setScorePoints(this.scorePoints + amount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void decreaseScore(int amount) {
/*  46 */     if (this.theScoreObjective.getCriteria().isReadOnly())
/*     */     {
/*  48 */       throw new IllegalStateException("Cannot modify read-only score");
/*     */     }
/*     */ 
/*     */     
/*  52 */     setScorePoints(this.scorePoints - amount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_96648_a() {
/*  58 */     if (this.theScoreObjective.getCriteria().isReadOnly())
/*     */     {
/*  60 */       throw new IllegalStateException("Cannot modify read-only score");
/*     */     }
/*     */ 
/*     */     
/*  64 */     increseScore(1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScorePoints() {
/*  70 */     return this.scorePoints;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setScorePoints(int points) {
/*  75 */     int i = this.scorePoints;
/*  76 */     this.scorePoints = points;
/*     */     
/*  78 */     if (i != points || this.forceUpdate) {
/*     */       
/*  80 */       this.forceUpdate = false;
/*  81 */       this.theScoreboard.func_96536_a(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ScoreObjective getObjective() {
/*  87 */     return this.theScoreObjective;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPlayerName() {
/*  95 */     return this.scorePlayerName;
/*     */   }
/*     */ 
/*     */   
/*     */   public Scoreboard getScoreScoreboard() {
/* 100 */     return this.theScoreboard;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLocked() {
/* 105 */     return this.locked;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLocked(boolean locked) {
/* 110 */     this.locked = locked;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_96651_a(List<EntityPlayer> p_96651_1_) {
/* 115 */     setScorePoints(this.theScoreObjective.getCriteria().setScore(p_96651_1_));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\scoreboard\Score.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */