/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collection;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ 
/*     */ public class S3EPacketTeams
/*     */   implements Packet<INetHandlerPlayClient> {
/*  14 */   private String name = "";
/*  15 */   private String displayName = "";
/*  16 */   private String prefix = "";
/*  17 */   private String suffix = "";
/*     */   
/*     */   private String nameTagVisibility;
/*     */   private int color;
/*     */   private final Collection<String> players;
/*     */   private int action;
/*     */   private int friendlyFlags;
/*     */   
/*     */   public S3EPacketTeams() {
/*  26 */     this.nameTagVisibility = Team.EnumVisible.ALWAYS.internalName;
/*  27 */     this.color = -1;
/*  28 */     this.players = Lists.newArrayList();
/*     */   }
/*     */ 
/*     */   
/*     */   public S3EPacketTeams(ScorePlayerTeam teamIn, int actionIn) {
/*  33 */     this.nameTagVisibility = Team.EnumVisible.ALWAYS.internalName;
/*  34 */     this.color = -1;
/*  35 */     this.players = Lists.newArrayList();
/*  36 */     this.name = teamIn.getRegisteredName();
/*  37 */     this.action = actionIn;
/*     */     
/*  39 */     if (actionIn == 0 || actionIn == 2) {
/*     */       
/*  41 */       this.displayName = teamIn.getTeamName();
/*  42 */       this.prefix = teamIn.getColorPrefix();
/*  43 */       this.suffix = teamIn.getColorSuffix();
/*  44 */       this.friendlyFlags = teamIn.func_98299_i();
/*  45 */       this.nameTagVisibility = (teamIn.getNameTagVisibility()).internalName;
/*  46 */       this.color = teamIn.getChatFormat().getColorIndex();
/*     */     } 
/*     */     
/*  49 */     if (actionIn == 0)
/*     */     {
/*  51 */       this.players.addAll(teamIn.getMembershipCollection());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public S3EPacketTeams(ScorePlayerTeam teamIn, Collection<String> playersIn, int actionIn) {
/*  57 */     this.nameTagVisibility = Team.EnumVisible.ALWAYS.internalName;
/*  58 */     this.color = -1;
/*  59 */     this.players = Lists.newArrayList();
/*     */     
/*  61 */     if (actionIn != 3 && actionIn != 4)
/*     */     {
/*  63 */       throw new IllegalArgumentException("Method must be join or leave for player constructor");
/*     */     }
/*  65 */     if (playersIn != null && !playersIn.isEmpty()) {
/*     */       
/*  67 */       this.action = actionIn;
/*  68 */       this.name = teamIn.getRegisteredName();
/*  69 */       this.players.addAll(playersIn);
/*     */     }
/*     */     else {
/*     */       
/*  73 */       throw new IllegalArgumentException("Players cannot be null/empty");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) {
/*  81 */     this.name = buf.readStringFromBuffer(16);
/*  82 */     this.action = buf.readByte();
/*     */     
/*  84 */     if (this.action == 0 || this.action == 2) {
/*     */       
/*  86 */       this.displayName = buf.readStringFromBuffer(32);
/*  87 */       this.prefix = buf.readStringFromBuffer(16);
/*  88 */       this.suffix = buf.readStringFromBuffer(16);
/*  89 */       this.friendlyFlags = buf.readByte();
/*  90 */       this.nameTagVisibility = buf.readStringFromBuffer(32);
/*  91 */       this.color = buf.readByte();
/*     */     } 
/*     */     
/*  94 */     if (this.action == 0 || this.action == 3 || this.action == 4) {
/*     */       
/*  96 */       int i = buf.readVarIntFromBuffer();
/*     */       
/*  98 */       for (int j = 0; j < i; j++)
/*     */       {
/* 100 */         this.players.add(buf.readStringFromBuffer(40));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) {
/* 109 */     buf.writeString(this.name);
/* 110 */     buf.writeByte(this.action);
/*     */     
/* 112 */     if (this.action == 0 || this.action == 2) {
/*     */       
/* 114 */       buf.writeString(this.displayName);
/* 115 */       buf.writeString(this.prefix);
/* 116 */       buf.writeString(this.suffix);
/* 117 */       buf.writeByte(this.friendlyFlags);
/* 118 */       buf.writeString(this.nameTagVisibility);
/* 119 */       buf.writeByte(this.color);
/*     */     } 
/*     */     
/* 122 */     if (this.action == 0 || this.action == 3 || this.action == 4) {
/*     */       
/* 124 */       buf.writeVarIntToBuffer(this.players.size());
/*     */       
/* 126 */       for (String s : this.players)
/*     */       {
/* 128 */         buf.writeString(s);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 138 */     handler.handleTeams(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 143 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/* 148 */     return this.displayName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/* 153 */     return this.prefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSuffix() {
/* 158 */     return this.suffix;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getPlayers() {
/* 163 */     return this.players;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAction() {
/* 168 */     return this.action;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFriendlyFlags() {
/* 173 */     return this.friendlyFlags;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColor() {
/* 178 */     return this.color;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNameTagVisibility() {
/* 183 */     return this.nameTagVisibility;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S3EPacketTeams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */