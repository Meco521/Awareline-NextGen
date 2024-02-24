/*     */ package awareline.main.mod.implement.misc;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.misc.EventChat;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*     */ import awareline.main.utility.timer.TimerUtil;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class AutoHypixel extends Module {
/*  15 */   private final Option<Boolean> gg = new Option("AutoGG", Boolean.valueOf(true));
/*  16 */   private final String[] AMode = new String[] { "BedWars_1v1", "BedWars_2v2", "BedWars_3v3", "BedWars_4v4", "SkyWars_Solo", "SkyWars_Solo_Insane", "SkyWars_Solo_LuckyBlock", "SkyWars_Team", "SkyWars_Team_Insane", "SkyWars_Team_LuckyBlock", "SurivialGames_Solo", "SurivialGames_Team", "MiniWalls" };
/*     */ 
/*     */   
/*  19 */   private final Mode<String> mode = new Mode("PlayMode", this.AMode, this.AMode[0]);
/*  20 */   private final Numbers<Double> delay = new Numbers("PlayDelay", Double.valueOf(3.0D), Double.valueOf(0.0D), Double.valueOf(5.0D), Double.valueOf(0.1D));
/*  21 */   private final TimerUtil timer = new TimerUtil();
/*     */   public boolean winning;
/*     */   float y;
/*     */   
/*     */   public AutoHypixel() {
/*  26 */     super("AutoHypixel", new String[] { "autoplay", "autohyp" }, ModuleType.Misc);
/*  27 */     addSettings(new Value[] { (Value)this.mode, (Value)this.delay, (Value)this.gg });
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onUpdate(EventPreUpdate event) {
/*  32 */     setSuffix((Serializable)this.mode.get());
/*     */     
/*  34 */     if (!this.winning) {
/*     */       return;
/*     */     }
/*     */     
/*  38 */     if (!this.timer.hasReached(((Double)this.delay.get()).doubleValue() * 1000.0D)) {
/*     */       return;
/*     */     }
/*     */     
/*  42 */     if (this.mode.is("BedWars_1v1")) {
/*  43 */       mc.thePlayer.sendChatMessage("/play bedwars_eight_one");
/*     */     }
/*  45 */     if (this.mode.is("BedWars_2v2")) {
/*  46 */       mc.thePlayer.sendChatMessage("/play bedwars_eight_two");
/*     */     }
/*  48 */     if (this.mode.is("BedWars_3v3")) {
/*  49 */       mc.thePlayer.sendChatMessage("/play bedwars_four_three");
/*     */     }
/*  51 */     if (this.mode.is("BedWars_4v4")) {
/*  52 */       mc.thePlayer.sendChatMessage("/play bedwars_four_four");
/*     */     }
/*  54 */     if (this.mode.is("SkyWars_Solo")) {
/*  55 */       mc.thePlayer.sendChatMessage("/play solo_normal");
/*     */     }
/*  57 */     if (this.mode.is("SkyWars_Solo_Insane")) {
/*  58 */       mc.thePlayer.sendChatMessage("/play solo_insane");
/*     */     }
/*  60 */     if (this.mode.is("SkyWars_Solo_LuckyBlock")) {
/*  61 */       mc.thePlayer.sendChatMessage("/play solo_insane_lucky");
/*     */     }
/*  63 */     if (this.mode.is("SkyWars_Team")) {
/*  64 */       mc.thePlayer.sendChatMessage("/play teams_normal");
/*     */     }
/*  66 */     if (this.mode.is("SkyWars_Team_Insane")) {
/*  67 */       mc.thePlayer.sendChatMessage("/play teams_insane");
/*     */     }
/*  69 */     if (this.mode.is("SkyWars_Team_LuckyBlock")) {
/*  70 */       mc.thePlayer.sendChatMessage("/play teams_insane_lucky");
/*     */     }
/*  72 */     if (this.mode.is("SurivialGames_Solo")) {
/*  73 */       mc.thePlayer.sendChatMessage("/play blitz_solo_normal");
/*     */     }
/*  75 */     if (this.mode.is("SurivialGames_Solo")) {
/*  76 */       mc.thePlayer.sendChatMessage("/play blitz_teams_normal");
/*     */     }
/*  78 */     if (this.mode.is("MiniWalls")) {
/*  79 */       mc.thePlayer.sendChatMessage("/play arcade_mini_walls");
/*     */     }
/*  81 */     String delaytext = "Join next game in " + this.delay.get() + "s";
/*  82 */     if (this.winning) {
/*  83 */       ClientNotification.sendClientMessage("AutoHypixel", delaytext, (long)(((Double)this.delay.get()).doubleValue() * 1000.0D), ClientNotification.Type.SUCCESS);
/*     */     }
/*     */     
/*  86 */     this.winning = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  91 */     if (this.mode.is("BedWars_1v1")) {
/*  92 */       mc.thePlayer.sendChatMessage("/play bedwars_eight_one");
/*     */     }
/*  94 */     if (this.mode.is("BedWars_2v2")) {
/*  95 */       mc.thePlayer.sendChatMessage("/play bedwars_eight_two");
/*     */     }
/*  97 */     if (this.mode.is("BedWars_3v3")) {
/*  98 */       mc.thePlayer.sendChatMessage("/play bedwars_four_three");
/*     */     }
/* 100 */     if (this.mode.is("BedWars_4v4")) {
/* 101 */       mc.thePlayer.sendChatMessage("/play bedwars_four_four");
/*     */     }
/* 103 */     if (this.mode.is("SkyWars_Solo")) {
/* 104 */       mc.thePlayer.sendChatMessage("/play solo_normal");
/*     */     }
/* 106 */     if (this.mode.is("SkyWars_Solo_Insane")) {
/* 107 */       mc.thePlayer.sendChatMessage("/play solo_insane");
/*     */     }
/* 109 */     if (this.mode.is("SkyWars_Solo_LuckyBlock")) {
/* 110 */       mc.thePlayer.sendChatMessage("/play solo_insane_lucky");
/*     */     }
/* 112 */     if (this.mode.is("SkyWars_Team")) {
/* 113 */       mc.thePlayer.sendChatMessage("/play teams_normal");
/*     */     }
/* 115 */     if (this.mode.is("SkyWars_Team_Insane")) {
/* 116 */       mc.thePlayer.sendChatMessage("/play teams_insane");
/*     */     }
/* 118 */     if (this.mode.is("SkyWars_Team_LuckyBlock")) {
/* 119 */       mc.thePlayer.sendChatMessage("/play teams_insane_lucky");
/*     */     }
/* 121 */     if (this.mode.is("SurivialGames_Solo")) {
/* 122 */       mc.thePlayer.sendChatMessage("/play blitz_solo_normal");
/*     */     }
/* 124 */     if (this.mode.is("SurivialGames_Solo")) {
/* 125 */       mc.thePlayer.sendChatMessage("/play blitz_teams_normal");
/*     */     }
/* 127 */     if (this.mode.is("MiniWalls")) {
/* 128 */       mc.thePlayer.sendChatMessage("/play arcade_mini_walls");
/*     */     }
/*     */   }
/*     */   
/*     */   public void onDisable() {
/* 133 */     this.y = -40.0F;
/* 134 */     this.winning = false;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onChat(EventChat e) {
/* 139 */     if (e.getMessage().contains("Victory") || e.getMessage().contains("胜锟斤拷")) {
/* 140 */       this.winning = true;
/* 141 */       e.setCancelled(true);
/*     */     } 
/* 143 */     if (((Boolean)this.gg.getValue()).booleanValue() && (
/* 144 */       e.getMessage().contains("Victory") || e.getMessage().contains("胜锟斤拷"))) {
/* 145 */       mc.thePlayer.sendChatMessage("GG");
/* 146 */       e.setCancelled(true);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\AutoHypixel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */