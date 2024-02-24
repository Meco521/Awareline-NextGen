/*     */ package awareline.main.mod.implement.world;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.LBEvents.EventWorldChanged;
/*     */ import awareline.main.event.events.world.packetEvents.EventPacketReceive;
/*     */ import awareline.main.event.events.world.packetEvents.EventPacketSend;
/*     */ import awareline.main.event.events.world.updateEvents.EventPostUpdate;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.event.events.world.worldChangeEvents.RespawnEvent;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.KillAura;
/*     */ import awareline.main.mod.implement.move.antifallutily.TimeHelper;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.chat.Helper;
/*     */ import awareline.main.utility.timer.TimerUtil;
/*     */ import java.io.Serializable;
/*     */ import net.minecraft.potion.Potion;
/*     */ 
/*     */ public class GameSpeed extends Module {
/*     */   public static GameSpeed getInstance;
/*  25 */   public final Numbers<Double> op = new Numbers("Speed", Double.valueOf(1.0D), Double.valueOf(0.1D), Double.valueOf(30.0D), Double.valueOf(0.1D));
/*  26 */   private final Option<Boolean> lagbackstop = new Option("LagCheck", Boolean.valueOf(true));
/*  27 */   private final Option<Boolean> onmove = new Option("OnlyMove", Boolean.valueOf(false));
/*  28 */   private final Option<Boolean> fallDistanceCheck = new Option("FallCheck", Boolean.valueOf(false));
/*  29 */   public final Numbers<Double> fallDistance = new Numbers("FallDist", 
/*  30 */       Double.valueOf(1.0D), Double.valueOf(0.1D), Double.valueOf(10.0D), Double.valueOf(0.1D), this.fallDistanceCheck::get);
/*  31 */   private final Option<Boolean> respawnCheck = new Option("RespawnCheck", Boolean.valueOf(false));
/*  32 */   private final Option<Boolean> packetLimit = new Option("Limit", Boolean.valueOf(false));
/*  33 */   private final Option<Boolean> murder = new Option("Murder", Boolean.valueOf(false));
/*  34 */   private final Mode<String> packetLimitMode = new Mode("LimitMode", new String[] { "Normal", "Strict" }, "Normal", this.packetLimit::get);
/*     */   
/*  36 */   private final Mode<String> mode = new Mode("Mode", new String[] { "Default", "Custom", "DCJ", "DCJ2" }, "Default");
/*     */   
/*  38 */   private final TimerUtil time = new TimerUtil();
/*  39 */   private final TimeHelper timer = new TimeHelper();
/*     */   private boolean lagBacking;
/*     */   private int lagBackTicks;
/*     */   private int packetcount;
/*     */   private boolean stopSendPacket;
/*     */   
/*     */   public GameSpeed() {
/*  46 */     super("GameSpeed", new String[] { "timer" }, ModuleType.World);
/*  47 */     addSettings(new Value[] { (Value)this.mode, (Value)this.op, (Value)this.onmove, (Value)this.murder, (Value)this.respawnCheck, (Value)this.packetLimitMode, (Value)this.packetLimit, (Value)this.lagbackstop, (Value)this.fallDistanceCheck, (Value)this.fallDistance });
/*  48 */     getInstance = this;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onRespawn(RespawnEvent event) {
/*  53 */     if (((Boolean)this.respawnCheck.get()).booleanValue()) {
/*  54 */       this.timer.reset();
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPacket(EventPacketSend e) {
/*  60 */     if (((Boolean)this.packetLimit.get()).booleanValue() && 
/*  61 */       e.getPacket() instanceof net.minecraft.network.play.client.C03PacketPlayer) {
/*  62 */       this.packetcount++;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onUpdate(EventPostUpdate event) {
/*  69 */     if (((Boolean)this.packetLimit.get()).booleanValue() && 
/*  70 */       this.time.hasReached(1000.0D)) {
/*  71 */       if (this.packetLimitMode.is("Strict") ? (this.packetcount > 20) : (this.packetcount >= 22)) {
/*  72 */         this.stopSendPacket = true;
/*  73 */         Helper.sendMessageWithoutPrefix("[GameSpeed] packet limit less timer...");
/*     */       } else {
/*  75 */         this.stopSendPacket = false;
/*     */       } 
/*  77 */       this.packetcount = 0;
/*  78 */       this.time.reset();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onWorldChange(EventWorldChanged e) {
/*  85 */     checkModule(GameSpeed.class);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPacket(EventPacketReceive e) {
/*  90 */     if (((Boolean)this.lagbackstop.get()).booleanValue())
/*     */     {
/*  92 */       if (e.getPacket() instanceof net.minecraft.network.play.server.S08PacketPlayerPosLook) {
/*  93 */         this.lagBacking = true;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onUpdate(EventPreUpdate e) {
/* 100 */     if (mc.thePlayer == null || mc.theWorld == null) {
/*     */       return;
/*     */     }
/* 103 */     setSuffix(this.mode.is("Custom") ? (Serializable)this.op.get() : (Serializable)this.mode.get());
/* 104 */     if (((Boolean)this.respawnCheck.get()).booleanValue() && 
/* 105 */       !this.timer.isDelayComplete(5000.0D)) {
/*     */       return;
/*     */     }
/*     */     
/* 109 */     if (((Boolean)this.murder.get()).booleanValue() && 
/* 110 */       KillAura.getInstance.isEnabled() && KillAura.getInstance.target != null && KillAura.getInstance.target.hurtResistantTime > 0) {
/* 111 */       mc.timer.timerSpeed = 1.0F;
/* 112 */       checkModule(GameSpeed.class);
/*     */       
/*     */       return;
/*     */     } 
/* 116 */     if (((Boolean)this.packetLimit.get()).booleanValue() && this.stopSendPacket) {
/* 117 */       mc.timer.timerSpeed = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 121 */     if (((Boolean)this.lagbackstop.get()).booleanValue()) {
/*     */       
/* 123 */       if (this.lagBackTicks >= 40 && 
/* 124 */         this.lagBacking) {
/* 125 */         this.lagBacking = false;
/*     */       }
/*     */ 
/*     */       
/* 129 */       if (!this.lagBacking) {
/* 130 */         this.lagBackTicks = 0;
/*     */       }
/*     */       
/* 133 */       if (this.lagBacking) {
/* 134 */         mc.timer.timerSpeed = 1.0F;
/* 135 */         this.lagBackTicks++;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } else {
/* 140 */       if (this.lagBackTicks != 0) {
/* 141 */         this.lagBackTicks = 0;
/*     */       }
/*     */       
/* 144 */       if (this.lagBacking) {
/* 145 */         this.lagBacking = false;
/*     */       }
/*     */     } 
/*     */     
/* 149 */     if (((Boolean)this.fallDistanceCheck.get()).booleanValue() && 
/* 150 */       mc.thePlayer.fallDistance > ((Double)this.fallDistance.get()).floatValue()) {
/* 151 */       mc.timer.timerSpeed = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 155 */     switch (((String)this.mode.get()).toLowerCase()) {
/*     */       case "dcj2":
/* 157 */         mc.timer.timerSpeed = isMoving() ? dcjSpeed(true) : 1.0F;
/*     */         break;
/*     */       
/*     */       case "dcj":
/* 161 */         if (!mc.thePlayer.onGround && mc.thePlayer.fallDistance > 0.1F) {
/* 162 */           mc.timer.timerSpeed = 1.04F;
/*     */           return;
/*     */         } 
/* 165 */         mc.timer.timerSpeed = isMoving() ? dcjSpeed(mc.thePlayer.onGround) : 1.0F;
/*     */         break;
/*     */       
/*     */       case "custom":
/* 169 */         customModeSpeed();
/*     */         break;
/*     */       
/*     */       case "default":
/* 173 */         defaultSpeed();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void customModeSpeed() {
/* 180 */     if (!((Boolean)this.onmove.get()).booleanValue()) {
/* 181 */       mc.timer.timerSpeed = ((Double)this.op.get()).floatValue();
/*     */     } else {
/* 183 */       mc.timer.timerSpeed = isMoving() ? ((Double)this.op.get()).floatValue() : 1.0F;
/*     */     } 
/*     */   }
/*     */   
/*     */   private float dcjSpeed(boolean onground) {
/* 188 */     float speed = onground ? 1.24F : 1.42F;
/* 189 */     if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
/* 190 */       return speed + 0.2F * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
/*     */     }
/* 192 */     return speed;
/*     */   }
/*     */ 
/*     */   
/*     */   private void defaultSpeed() {
/* 197 */     if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
/* 198 */       mc.timer.timerSpeed = 1.3F + 0.2F * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
/*     */     } else {
/* 200 */       mc.timer.timerSpeed = 1.3F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 206 */     this.time.reset();
/* 207 */     this.lagBackTicks = 0;
/* 208 */     this.lagBacking = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 213 */     this.lagBackTicks = 0;
/* 214 */     this.lagBacking = false;
/* 215 */     if (mc.timer.timerSpeed != 1.0F)
/* 216 */       mc.timer.timerSpeed = 1.0F; 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\world\GameSpeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */