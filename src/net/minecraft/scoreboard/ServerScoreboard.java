/*     */ package net.minecraft.scoreboard;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
/*     */ import net.minecraft.network.play.server.S3CPacketUpdateScore;
/*     */ import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
/*     */ import net.minecraft.network.play.server.S3EPacketTeams;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ 
/*     */ public class ServerScoreboard
/*     */   extends Scoreboard
/*     */ {
/*     */   private final MinecraftServer scoreboardMCServer;
/*  20 */   private final Set<ScoreObjective> field_96553_b = Sets.newHashSet();
/*     */   
/*     */   private ScoreboardSaveData scoreboardSaveData;
/*     */   
/*     */   public ServerScoreboard(MinecraftServer mcServer) {
/*  25 */     this.scoreboardMCServer = mcServer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_96536_a(Score p_96536_1_) {
/*  30 */     super.func_96536_a(p_96536_1_);
/*     */     
/*  32 */     if (this.field_96553_b.contains(p_96536_1_.getObjective()))
/*     */     {
/*  34 */       this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3CPacketUpdateScore(p_96536_1_));
/*     */     }
/*     */     
/*  37 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_96516_a(String p_96516_1_) {
/*  42 */     super.func_96516_a(p_96516_1_);
/*  43 */     this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3CPacketUpdateScore(p_96516_1_));
/*  44 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_178820_a(String p_178820_1_, ScoreObjective p_178820_2_) {
/*  49 */     super.func_178820_a(p_178820_1_, p_178820_2_);
/*  50 */     this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3CPacketUpdateScore(p_178820_1_, p_178820_2_));
/*  51 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObjectiveInDisplaySlot(int p_96530_1_, ScoreObjective p_96530_2_) {
/*  59 */     ScoreObjective scoreobjective = getObjectiveInDisplaySlot(p_96530_1_);
/*  60 */     super.setObjectiveInDisplaySlot(p_96530_1_, p_96530_2_);
/*     */     
/*  62 */     if (scoreobjective != p_96530_2_ && scoreobjective != null)
/*     */     {
/*  64 */       if (func_96552_h(scoreobjective) > 0) {
/*     */         
/*  66 */         this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3DPacketDisplayScoreboard(p_96530_1_, p_96530_2_));
/*     */       }
/*     */       else {
/*     */         
/*  70 */         sendDisplaySlotRemovalPackets(scoreobjective);
/*     */       } 
/*     */     }
/*     */     
/*  74 */     if (p_96530_2_ != null)
/*     */     {
/*  76 */       if (this.field_96553_b.contains(p_96530_2_)) {
/*     */         
/*  78 */         this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3DPacketDisplayScoreboard(p_96530_1_, p_96530_2_));
/*     */       }
/*     */       else {
/*     */         
/*  82 */         func_96549_e(p_96530_2_);
/*     */       } 
/*     */     }
/*     */     
/*  86 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addPlayerToTeam(String player, String newTeam) {
/*  94 */     if (super.addPlayerToTeam(player, newTeam)) {
/*     */       
/*  96 */       ScorePlayerTeam scoreplayerteam = getTeam(newTeam);
/*  97 */       this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3EPacketTeams(scoreplayerteam, Arrays.asList(new String[] { player }, ), 3));
/*  98 */       markSaveDataDirty();
/*  99 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 103 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePlayerFromTeam(String p_96512_1_, ScorePlayerTeam p_96512_2_) {
/* 113 */     super.removePlayerFromTeam(p_96512_1_, p_96512_2_);
/* 114 */     this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3EPacketTeams(p_96512_2_, Arrays.asList(new String[] { p_96512_1_ }, ), 4));
/* 115 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onScoreObjectiveAdded(ScoreObjective scoreObjectiveIn) {
/* 123 */     super.onScoreObjectiveAdded(scoreObjectiveIn);
/* 124 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onObjectiveDisplayNameChanged(ScoreObjective p_96532_1_) {
/* 129 */     super.onObjectiveDisplayNameChanged(p_96532_1_);
/*     */     
/* 131 */     if (this.field_96553_b.contains(p_96532_1_))
/*     */     {
/* 133 */       this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3BPacketScoreboardObjective(p_96532_1_, 2));
/*     */     }
/*     */     
/* 136 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onScoreObjectiveRemoved(ScoreObjective p_96533_1_) {
/* 141 */     super.onScoreObjectiveRemoved(p_96533_1_);
/*     */     
/* 143 */     if (this.field_96553_b.contains(p_96533_1_))
/*     */     {
/* 145 */       sendDisplaySlotRemovalPackets(p_96533_1_);
/*     */     }
/*     */     
/* 148 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcastTeamCreated(ScorePlayerTeam playerTeam) {
/* 156 */     super.broadcastTeamCreated(playerTeam);
/* 157 */     this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3EPacketTeams(playerTeam, 0));
/* 158 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendTeamUpdate(ScorePlayerTeam playerTeam) {
/* 166 */     super.sendTeamUpdate(playerTeam);
/* 167 */     this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3EPacketTeams(playerTeam, 2));
/* 168 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_96513_c(ScorePlayerTeam playerTeam) {
/* 173 */     super.func_96513_c(playerTeam);
/* 174 */     this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3EPacketTeams(playerTeam, 1));
/* 175 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_96547_a(ScoreboardSaveData p_96547_1_) {
/* 180 */     this.scoreboardSaveData = p_96547_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void markSaveDataDirty() {
/* 185 */     if (this.scoreboardSaveData != null)
/*     */     {
/* 187 */       this.scoreboardSaveData.markDirty();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Packet> func_96550_d(ScoreObjective p_96550_1_) {
/* 193 */     List<Packet> list = Lists.newArrayList();
/* 194 */     list.add(new S3BPacketScoreboardObjective(p_96550_1_, 0));
/*     */     
/* 196 */     for (int i = 0; i < 19; i++) {
/*     */       
/* 198 */       if (getObjectiveInDisplaySlot(i) == p_96550_1_)
/*     */       {
/* 200 */         list.add(new S3DPacketDisplayScoreboard(i, p_96550_1_));
/*     */       }
/*     */     } 
/*     */     
/* 204 */     for (Score score : getSortedScores(p_96550_1_))
/*     */     {
/* 206 */       list.add(new S3CPacketUpdateScore(score));
/*     */     }
/*     */     
/* 209 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_96549_e(ScoreObjective p_96549_1_) {
/* 214 */     List<Packet> list = func_96550_d(p_96549_1_);
/*     */     
/* 216 */     for (EntityPlayerMP entityplayermp : this.scoreboardMCServer.getConfigurationManager().getPlayerList()) {
/*     */       
/* 218 */       for (Packet packet : list)
/*     */       {
/* 220 */         entityplayermp.playerNetServerHandler.sendPacket(packet);
/*     */       }
/*     */     } 
/*     */     
/* 224 */     this.field_96553_b.add(p_96549_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Packet> func_96548_f(ScoreObjective p_96548_1_) {
/* 229 */     List<Packet> list = Lists.newArrayList();
/* 230 */     list.add(new S3BPacketScoreboardObjective(p_96548_1_, 1));
/*     */     
/* 232 */     for (int i = 0; i < 19; i++) {
/*     */       
/* 234 */       if (getObjectiveInDisplaySlot(i) == p_96548_1_)
/*     */       {
/* 236 */         list.add(new S3DPacketDisplayScoreboard(i, p_96548_1_));
/*     */       }
/*     */     } 
/*     */     
/* 240 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendDisplaySlotRemovalPackets(ScoreObjective p_96546_1_) {
/* 245 */     List<Packet> list = func_96548_f(p_96546_1_);
/*     */     
/* 247 */     for (EntityPlayerMP entityplayermp : this.scoreboardMCServer.getConfigurationManager().getPlayerList()) {
/*     */       
/* 249 */       for (Packet packet : list)
/*     */       {
/* 251 */         entityplayermp.playerNetServerHandler.sendPacket(packet);
/*     */       }
/*     */     } 
/*     */     
/* 255 */     this.field_96553_b.remove(p_96546_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_96552_h(ScoreObjective p_96552_1_) {
/* 260 */     int i = 0;
/*     */     
/* 262 */     for (int j = 0; j < 19; j++) {
/*     */       
/* 264 */       if (getObjectiveInDisplaySlot(j) == p_96552_1_)
/*     */       {
/* 266 */         i++;
/*     */       }
/*     */     } 
/*     */     
/* 270 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\scoreboard\ServerScoreboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */