/*     */ package awareline.main.mod.implement.move;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.BBSetEvent;
/*     */ import awareline.main.event.events.world.EventTick;
/*     */ import awareline.main.event.events.world.moveEvents.EventMove;
/*     */ import awareline.main.event.events.world.moveEvents.EventStrafe;
/*     */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.event.events.world.updateEvents.EventPostUpdate;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.KillAura;
/*     */ import awareline.main.mod.implement.world.Scaffold;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.font.fontmanager.color.ColorUtils;
/*     */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*     */ import awareline.main.utility.MoveUtils;
/*     */ import awareline.main.utility.chat.Helper;
/*     */ import awareline.main.utility.timer.TimerUtil;
/*     */ import java.io.Serializable;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.LinkedList;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
/*     */ import net.minecraft.network.play.client.C13PacketPlayerAbilities;
/*     */ import net.minecraft.network.play.server.S12PacketEntityVelocity;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.optifine.util.MathUtils;
/*     */ 
/*     */ public class Flight
/*     */   extends Module {
/*     */   public static Flight getInstance;
/*  48 */   private final String[] flyModes = new String[] { "Verus", "DCJ", "MushMC", "Motion", "HmXix", "Minemora", "Funcraft", "Creative", "AAC4.3.6", "NCP", "Vulcan", "MineLand", "CubeCraft", "WatchdogExploit", "Boost" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public final Mode<String> mode = new Mode("Mode", this.flyModes, this.flyModes[0]);
/*     */   
/*  57 */   private final Numbers<Double> motionSpeed = new Numbers("MotionSpeed", 
/*  58 */       Double.valueOf(2.5D), Double.valueOf(0.1D), Double.valueOf(10.0D), Double.valueOf(0.1D), () -> Boolean.valueOf(this.mode.is("Motion")));
/*     */   
/*  60 */   public final Option<Boolean> blink = new Option("Blink", Boolean.valueOf(false), () -> Boolean.valueOf(this.mode.is("Boost")));
/*  61 */   private final Numbers<Double> blinkDelay = new Numbers("BlinkDelay", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(3000.0D), Double.valueOf(100.0D), () -> Boolean.valueOf(this.mode.is("Boost")));
/*     */   
/*  63 */   private final Option<Boolean> fastStop = new Option("FastStop", Boolean.valueOf(false)); private final Option<Boolean> viewBobbing = new Option("ViewBobbing", 
/*  64 */       Boolean.valueOf(false));
/*  65 */   private final Option<Boolean> combatSpoof = new Option("CombatSpoof", Boolean.valueOf(true));
/*  66 */   private final Option<Boolean> highBPS = new Option("HighBPS", Boolean.valueOf(false), () -> Boolean.valueOf(this.mode.is("Boost")));
/*  67 */   private final Option<Boolean> speedDisplay = new Option("SpeedDisplay", Boolean.valueOf(true));
/*     */   
/*  69 */   public final Option<Boolean> timer = new Option("Timer", Boolean.valueOf(false), () -> Boolean.valueOf(this.mode.is("Boost"))); public final Option<Boolean> noDelayTimerBoost = new Option("TimerNoDelay", 
/*  70 */       Boolean.valueOf(false), () -> Boolean.valueOf(this.mode.is("Boost")), this.timer::get);
/*  71 */   private final Numbers<Double> timerSpeed = new Numbers("TimerSpeed", Double.valueOf(1.5D), Double.valueOf(1.0D), Double.valueOf(10.0D), Double.valueOf(0.1D), this.timer::get, () -> Boolean.valueOf(this.mode.is("Boost"))); private final Numbers<Double> timerDuration = new Numbers("TimerDuration", 
/*  72 */       Double.valueOf(1000.0D), Double.valueOf(100.0D), Double.valueOf(8000.0D), Double.valueOf(100.0D), this.timer::get, () -> Boolean.valueOf(this.mode.is("Boost")));
/*     */   
/*  74 */   private final Option<Boolean> waiting = new Option("Waiting", Boolean.valueOf(false));
/*  75 */   private final Numbers<Double> waitingDelay = new Numbers("Waitings", 
/*  76 */       Double.valueOf(10.0D), Double.valueOf(0.0D), Double.valueOf(1000.0D), Double.valueOf(10.0D), this.waiting::get);
/*     */ 
/*     */   
/*  79 */   private final TimerUtil waitingTime = new TimerUtil(); private final TimerUtil motiononground = new TimerUtil(); private final TimerUtil timertimer = new TimerUtil(); private final TimerUtil teleportTimeUtil = new TimerUtil(); private final TimerUtil blinkingTimerUtil = new TimerUtil(); private final TimerUtil funcraftTicks = new TimerUtil();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   private final LinkedBlockingQueue<Packet> packetLinkedBlockingQueue = new LinkedBlockingQueue<>();
/*  85 */   private final LinkedList<double[]> positions = (LinkedList)new LinkedList<>(); public double lastDistance; public double y; public double lastDist;
/*     */   public double moveSpeed;
/*     */   public boolean blinkplz;
/*     */   public boolean clipDown;
/*     */   public boolean canZoomNow;
/*     */   private int minemoraTicks;
/*  91 */   private int boostWatchdogStatue = 1;
/*     */   private int watchdogPosYTicks;
/*     */   private int stage;
/*     */   private boolean failedStart;
/*     */   private boolean disableLogger;
/*     */   private boolean noWaittingStartNow;
/*     */   private boolean stucking;
/*     */   private boolean teleported;
/*     */   private double serverPosX;
/*     */   
/*     */   public Flight() {
/* 102 */     super("Flight", ModuleType.Movement);
/* 103 */     addSettings(new Value[] { (Value)this.mode, (Value)this.motionSpeed, (Value)this.combatSpoof, (Value)this.waiting, (Value)this.waitingDelay, (Value)this.timer, (Value)this.timerSpeed, (Value)this.timerDuration, (Value)this.noDelayTimerBoost, (Value)this.blink, (Value)this.blinkDelay, (Value)this.highBPS, (Value)this.fastStop, (Value)this.viewBobbing, (Value)this.speedDisplay });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     getInstance = this;
/*     */   }
/*     */   private double serverPosY; private double serverPosZ; private int ticks; private boolean jumped; private boolean clipped; private int ticksAboveGround; private int japanese; private int groundpacket;
/*     */   public float getMaxFallDist() {
/* 115 */     PotionEffect potioneffect = mc.thePlayer.getActivePotionEffect(Potion.jump);
/* 116 */     int f = (potioneffect != null) ? (potioneffect.getAmplifier() + 1) : 0;
/* 117 */     return (mc.thePlayer.getMaxFallHeight() + f);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 123 */     EntityPlayerSP p = mc.thePlayer;
/*     */     
/* 125 */     if (p == null) {
/*     */       return;
/*     */     }
/* 128 */     this.ticks = 0;
/* 129 */     this.y = mc.thePlayer.posY;
/* 130 */     this.clipDown = false;
/* 131 */     checkModule(new Class[] { Speed.class, Scaffold.class });
/* 132 */     this.waitingTime.reset();
/* 133 */     this.minemoraTicks = 0;
/* 134 */     this.stucking = false;
/* 135 */     if (this.mode.is("DCJ")) {
/* 136 */       this.watchdogPosYTicks = 0;
/* 137 */     } else if (this.mode.is("Funcraft")) {
/* 138 */       this.funcraftTicks.reset();
/* 139 */     } else if (this.mode.is("MineLand")) {
/* 140 */       this.serverPosX = p.posX;
/* 141 */       this.serverPosY = p.posY;
/* 142 */       this.serverPosZ = p.posZ;
/* 143 */       this.teleported = false;
/* 144 */     } else if (this.mode.is("AAC4.3.6")) {
/* 145 */       sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(p.posX, p.posY, p.posZ, true));
/* 146 */       ClientNotification.sendClientMessage("Fly", "You need to place a TNT near you", 4000L, ClientNotification.Type.WARNING);
/* 147 */       if (p.onGround) {
/* 148 */         p.jump();
/*     */       }
/* 150 */     } else if (this.mode.is("Boost")) {
/* 151 */       this.canZoomNow = false;
/* 152 */       if (!((Boolean)this.waiting.get()).booleanValue()) {
/* 153 */         if (this.blinkplz && ((Boolean)this.blink.get()).booleanValue()) {
/* 154 */           synchronized (this.positions) {
/* 155 */             this.positions.add(new double[] { p.posX, (p.getEntityBoundingBox()).minY + (p.getEyeHeight() / 2.0F), p.posZ });
/* 156 */             this.positions.add(new double[] { p.posX, (p.getEntityBoundingBox()).minY, p.posZ });
/*     */           } 
/*     */         }
/* 159 */         this.blinkingTimerUtil.reset();
/* 160 */         this.blinkplz = false;
/* 161 */         this.watchdogPosYTicks = 0;
/* 162 */         p.stepHeight = 0.0F;
/* 163 */         if (p.onGround) {
/* 164 */           double posX = p.posX, posY = p.posY, posZ = p.posZ;
/*     */           
/* 166 */           for (int i = 0; i < getMaxFallDist() / 0.08510000046342611D + 1.0D; i++) {
/* 167 */             sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + 0.060100000351667404D, posZ, false));
/* 168 */             sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + 5.000000237487257E-4D, posZ, false));
/* 169 */             sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + 0.004999999888241291D + 6.01000003516674E-8D, posZ, false));
/*     */           } 
/*     */           
/* 172 */           sendPacketNoEvent((Packet)new C03PacketPlayer(true));
/*     */         } 
/*     */       } 
/* 175 */       this.teleportTimeUtil.reset();
/* 176 */       this.timertimer.reset();
/* 177 */       this.motiononground.reset();
/* 178 */       if (!((Boolean)this.waiting.get()).booleanValue()) {
/* 179 */         if (p.onGround) {
/* 180 */           p.jump();
/* 181 */           p.motionY = 0.4198D;
/* 182 */           p.posY += 0.4198D;
/* 183 */           this.boostWatchdogStatue = 1;
/* 184 */           this.moveSpeed = 0.1D;
/* 185 */           this.lastDistance = 0.0D;
/* 186 */           this.failedStart = false;
/*     */         } else {
/* 188 */           ClientNotification.sendClientMessage("Fly", "You need boost on ground", 4000L, ClientNotification.Type.WARNING);
/* 189 */           this.moveSpeed = 0.0D;
/* 190 */           this.boostWatchdogStatue = 5;
/*     */         } 
/*     */       }
/* 193 */     } else if (this.mode.is("NCP")) {
/* 194 */       checkModule(new Class[] { Longjump.class, Scaffold.class });
/* 195 */       this.moveSpeed = 0.0D;
/* 196 */       this.lastDist = 0.0D;
/* 197 */     } else if (this.mode.is("Vulcan")) {
/* 198 */       checkModule(new Class[] { Longjump.class, Scaffold.class });
/* 199 */       this.ticks = 0;
/* 200 */       send((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(p.posX, p.posY - 2.0D, p.posZ, p.rotationYaw, p.rotationPitch, false));
/*     */     }
/* 202 */     else if (this.mode.is("WatchdogExploit")) {
/* 203 */       this.jumped = false;
/* 204 */       this.clipped = false;
/* 205 */       this.ticksAboveGround = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 211 */     this.groundpacket = 0;
/* 212 */     if (((Boolean)this.blink.get()).booleanValue()) {
/* 213 */       blink();
/*     */     }
/* 215 */     this.blinkplz = false;
/* 216 */     if (this.mode.is("NCP") || this.mode.is("HmXixSame")) {
/* 217 */       if (mc.thePlayer != null) {
/* 218 */         this.moveSpeed = MoveUtils.INSTANCE.getBaseMoveSpeed();
/*     */       }
/* 220 */       this.lastDist = 0.0D;
/* 221 */       this.stage = 0;
/* 222 */       mc.timer.timerSpeed = 1.0F;
/*     */     } 
/* 224 */     if (((Boolean)this.fastStop.get()).booleanValue()) {
/* 225 */       MoveUtils.INSTANCE.stop();
/*     */     }
/* 227 */     this.teleportTimeUtil.reset();
/* 228 */     mc.thePlayer.capabilities.isFlying = false;
/* 229 */     mc.timer.timerSpeed = 1.0F;
/* 230 */     mc.thePlayer.stepHeight = 0.5F;
/* 231 */     this.motiononground.reset();
/* 232 */     this.waitingTime.reset();
/* 233 */     if (this.mode.is("Vulcan")) {
/* 234 */       MoveUtils.INSTANCE.stop();
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPacket(PacketEvent e) {
/* 240 */     Packet<?> packet = e.getPacket();
/* 241 */     if (((Boolean)this.waiting.get()).booleanValue() && !this.waitingTime.hasReached(((Double)this.waitingDelay.get()).floatValue())) {
/*     */       return;
/*     */     }
/* 244 */     if (e.getState() == PacketEvent.State.INCOMING) {
/* 245 */       if (this.mode.is("Watchdog")) {
/* 246 */         if (e.getPacket() instanceof net.minecraft.network.play.server.S08PacketPlayerPosLook) {
/* 247 */           this.ticksAboveGround = 0;
/*     */         }
/* 249 */       } else if (this.mode.is("MineLand")) {
/* 250 */         if (packet instanceof net.minecraft.network.play.server.S08PacketPlayerPosLook && !this.teleported) {
/* 251 */           e.setCancelled(true);
/* 252 */         } else if (packet instanceof S12PacketEntityVelocity) {
/* 253 */           S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity)packet;
/* 254 */           if (wrapper.getEntityID() == mc.thePlayer.getEntityId() && wrapper.motionY / 8000.0D > 0.5D) {
/* 255 */             this.teleported = true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/* 260 */     if (e.getState() == PacketEvent.State.OUTGOING) {
/* 261 */       if (this.mode.is("Funcraft") && 
/* 262 */         packet instanceof C03PacketPlayer) {
/* 263 */         C03PacketPlayer c03PacketPlayer = (C03PacketPlayer)e.getPacket();
/* 264 */         c03PacketPlayer.onGround = false;
/*     */       } 
/*     */       
/* 267 */       if (this.mode.is("Boost") && this.blinkplz && ((Boolean)this.blink.get()).booleanValue()) {
/* 268 */         if (packet instanceof C03PacketPlayer) {
/* 269 */           C03PacketPlayer c03PacketPlayer = (C03PacketPlayer)e.getPacket();
/* 270 */           c03PacketPlayer.onGround = false;
/*     */         } 
/*     */         
/* 273 */         if (mc.thePlayer == null || this.disableLogger) {
/*     */           return;
/*     */         }
/* 276 */         if (packet instanceof C03PacketPlayer) {
/* 277 */           e.setCancelled(true);
/*     */         }
/* 279 */         if (packet instanceof C03PacketPlayer.C04PacketPlayerPosition || packet instanceof C03PacketPlayer.C06PacketPlayerPosLook || packet instanceof net.minecraft.network.play.client.C08PacketPlayerBlockPlacement || packet instanceof net.minecraft.network.play.client.C0APacketAnimation || packet instanceof net.minecraft.network.play.client.C0BPacketEntityAction || packet instanceof net.minecraft.network.play.client.C02PacketUseEntity) {
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
/* 291 */           e.setCancelled(true);
/* 292 */           this.packetLinkedBlockingQueue.add(packet);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onUpdate(EventPreUpdate e) {
/* 300 */     if (this.mode.is("DCJ")) {
/* 301 */       mc.thePlayer.jumpMovementFactor = (float)(mc.thePlayer.jumpMovementFactor * 1.003D);
/* 302 */       if (this.ticks == 6) {
/* 303 */         this.ticks = 0;
/*     */       }
/*     */       
/* 306 */       if (!mc.gameSettings.keyBindForward.isKeyDown() && !mc.gameSettings.keyBindLeft.isKeyDown() && !mc.gameSettings.keyBindRight.isKeyDown() && !mc.gameSettings.keyBindBack.isKeyDown()) {
/* 307 */         mc.timer.timerSpeed = 1.0F;
/* 308 */         mc.thePlayer.motionY = 0.0D;
/* 309 */         mc.thePlayer.onGround = false;
/* 310 */         mc.thePlayer.motionX = 0.0D;
/* 311 */         mc.thePlayer.motionZ = 0.0D;
/*     */       } else {
/* 313 */         mc.timer.timerSpeed = 1.0F;
/* 314 */         mc.thePlayer.setSprinting(true);
/* 315 */         mc.thePlayer.motionY = 0.0D;
/* 316 */         mc.thePlayer.onGround = true;
/*     */       } 
/* 318 */       this.groundpacket++;
/*     */       
/* 320 */       if (this.groundpacket >= 80) {
/* 321 */         sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.05D, mc.thePlayer.posZ, true));
/*     */         
/* 323 */         this.groundpacket = 0;
/* 324 */         msg("send packet");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPre(EventPreUpdate e) {
/* 331 */     setSuffix((Serializable)this.mode.get());
/*     */     
/* 333 */     EntityPlayerSP player = mc.thePlayer;
/*     */     
/* 335 */     if (player == null || mc.theWorld == null) {
/*     */       return;
/*     */     }
/*     */     
/* 339 */     if (((Boolean)this.waiting.get()).booleanValue() && !this.waitingTime.hasReached(((Double)this.waitingDelay.get()).intValue())) {
/* 340 */       this.stucking = true;
/* 341 */       this.noWaittingStartNow = this.mode.is("Boost");
/*     */       return;
/*     */     } 
/* 344 */     if (this.noWaittingStartNow) {
/* 345 */       if (this.blinkplz && ((Boolean)this.blink.get()).booleanValue()) {
/* 346 */         synchronized (this.positions) {
/* 347 */           this.positions.add(new double[] { player.posX, (player.getEntityBoundingBox()).minY + (player.getEyeHeight() / 2.0F), player.posZ });
/* 348 */           this.positions.add(new double[] { player.posX, (player.getEntityBoundingBox()).minY, player.posZ });
/*     */         } 
/*     */       }
/*     */       
/* 352 */       this.blinkingTimerUtil.reset();
/* 353 */       this.blinkplz = false;
/* 354 */       this.watchdogPosYTicks = 0;
/* 355 */       player.stepHeight = 0.0F;
/* 356 */       double x1 = player.posX, y1 = player.posY, z2 = player.posZ;
/*     */       
/* 358 */       for (int i = 0; i < getMaxFallDist() / 0.08510000046342611D + 1.0D; i++) {
/* 359 */         sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(x1, y1 + 0.060100000351667404D, z2, false));
/* 360 */         sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(x1, y1 + 5.000000237487257E-4D, z2, false));
/* 361 */         sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(x1, y1 + 0.004999999888241291D + 6.01000003516674E-8D, z2, false));
/*     */       } 
/*     */       
/* 364 */       sendPacketNoEvent((Packet)new C03PacketPlayer(true));
/* 365 */       player.motionY = 0.4198D;
/* 366 */       player.posY += 0.4198D;
/* 367 */       this.boostWatchdogStatue = 1;
/* 368 */       this.moveSpeed = 0.1D;
/* 369 */       this.lastDistance = 0.0D;
/* 370 */       this.failedStart = false;
/* 371 */       this.noWaittingStartNow = false;
/*     */     } 
/*     */ 
/*     */     
/* 375 */     if (!((Boolean)this.viewBobbing.get()).booleanValue()) {
/* 376 */       player.cameraPitch = 0.0F;
/* 377 */       player.cameraYaw = 0.0F;
/*     */     } 
/*     */     
/* 380 */     if (((Boolean)this.combatSpoof.get()).booleanValue()) {
/* 381 */       KillAura.getInstance.target = null;
/*     */     }
/*     */     
/* 384 */     if (this.mode.is("Creative")) {
/* 385 */       if (!player.capabilities.isFlying) {
/* 386 */         player.capabilities.isFlying = true;
/*     */       }
/* 388 */     } else if (this.mode.is("AAC4.3.6")) {
/* 389 */       if (player.onGround) {
/* 390 */         MoveUtils.INSTANCE.stop();
/* 391 */       } else if (player.fallDistance > 2.0F) {
/* 392 */         mc.timer.timerSpeed = 0.7F;
/* 393 */         MoveUtils.INSTANCE.setMotion(1.5D);
/* 394 */         if (player.movementInput.jump) {
/* 395 */           player.motionY = 0.75D;
/*     */           return;
/*     */         } 
/* 398 */         if (player.movementInput.sneak) {
/* 399 */           player.motionY = -0.75D;
/*     */           return;
/*     */         } 
/* 402 */         player.motionY = 0.0D;
/* 403 */       } else if (player.fallDistance == 0.0F) {
/* 404 */         MoveUtils.INSTANCE.stop();
/*     */       } 
/* 406 */     } else if (this.mode.is("MushMC")) {
/* 407 */       mc.timer.timerSpeed = 0.85F;
/* 408 */       MoveUtils.INSTANCE.setMotion(4.0D);
/* 409 */       if (player.movementInput.jump) {
/* 410 */         player.motionY = 0.75D;
/*     */         return;
/*     */       } 
/* 413 */       if (player.movementInput.sneak) {
/* 414 */         player.motionY = -0.75D;
/*     */         return;
/*     */       } 
/* 417 */       player.motionY = 0.0D;
/* 418 */       player.motionY -= ((player.ticksExisted % 10 == 0) ? 0.08F : 0.0F);
/* 419 */     } else if (this.mode.is("Minemora")) {
/* 420 */       if (!player.onGround) {
/* 421 */         this.minemoraTicks++;
/* 422 */         if (this.minemoraTicks >= 5) {
/* 423 */           player.motionY = 0.019999999552965164D;
/* 424 */           this.minemoraTicks = 0;
/*     */         }
/*     */       
/*     */       } 
/* 428 */     } else if (this.mode.is("NCP")) {
/* 429 */       player.posY = this.y;
/* 430 */       if (e.getType() == 0) {
/* 431 */         double xDist = player.posX - player.prevPosX;
/* 432 */         double zDist = player.posZ - player.prevPosZ;
/* 433 */         this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
/*     */       } 
/* 435 */     } else if (this.mode.is("Vulcan")) {
/* 436 */       float speed = 1.0F;
/* 437 */       player
/*     */         
/* 439 */         .motionY = -1.0E-10D + (mc.gameSettings.keyBindJump.isKeyDown() ? 1.0D : 0.0D) - (mc.gameSettings.keyBindSneak.isKeyDown() ? 1.0D : 0.0D);
/*     */       
/* 441 */       if (player.getDistance(player.lastReportedPosX, player.lastReportedPosY, player.lastReportedPosZ) <= 8.85D) {
/* 442 */         e.setCancelled(true);
/*     */       } else {
/* 444 */         this.ticks++;
/* 445 */         if (this.ticks >= 8) {
/* 446 */           MoveUtils.INSTANCE.stop();
/* 447 */           checkModule(Flight.class);
/*     */         } 
/*     */       } 
/* 450 */     } else if (this.mode.is("MineLand")) {
/* 451 */       if (!this.teleported) {
/*     */         
/* 453 */         double yaw = MoveUtils.INSTANCE.direction();
/* 454 */         double speed = 6.0D;
/*     */         
/* 456 */         if (player.ticksExisted % 3 == 0) {
/* 457 */           send((Packet)new C03PacketPlayer(player.onGround));
/* 458 */           player.setPosition(this.serverPosX, this.serverPosY, this.serverPosZ);
/*     */         } 
/*     */         
/* 461 */         e.setPosY(e.getPosY() - 1.1D + ((player.ticksExisted % 3 == 0) ? 0.42F : 0.0F));
/* 462 */         e.setPosX(e.getPosX() + MathHelper.sin((float)yaw) * 6.0D);
/* 463 */         e.setPosZ(e.getPosZ() - MathHelper.cos((float)yaw) * 6.0D);
/*     */       } else {
/*     */         
/* 466 */         mc.timer.timerSpeed = 0.3F;
/*     */       } 
/* 468 */     } else if (this.mode.is("CubeCraft")) {
/*     */       
/* 470 */       float speed = 1.0F;
/*     */       
/* 472 */       player
/*     */         
/* 474 */         .motionY = -1.0E-10D + (mc.gameSettings.keyBindJump.isKeyDown() ? 1.0D : 0.0D) - (mc.gameSettings.keyBindSneak.isKeyDown() ? 1.0D : 0.0D);
/*     */ 
/*     */       
/* 477 */       boolean playerNearby = (!KillAura.getInstance.targets.isEmpty() || mc.currentScreen != null);
/*     */ 
/*     */       
/* 480 */       if (player.getDistance(player.lastReportedPosX, player.lastReportedPosY, player.lastReportedPosZ) <= ((playerNearby ? 5 : 10) - 1.0F) - 0.15D && player.swingProgressInt != 3)
/*     */       {
/* 482 */         e.setCancelled(true);
/*     */       }
/* 484 */     } else if (this.mode.is("WatchdogExploit")) {
/* 485 */       if (!this.jumped && player.onGround) {
/* 486 */         player.motionY = 0.07500000298023224D;
/* 487 */         this.jumped = true;
/*     */         return;
/*     */       } 
/* 490 */       if (player.onGround && !this.clipped) {
/* 491 */         e.y -= 0.07500000298023224D;
/* 492 */         e.onGround = true;
/* 493 */         this.clipped = true;
/*     */       } 
/* 495 */       if (this.clipped) {
/* 496 */         player.motionY = 0.0D;
/*     */       }
/* 498 */       if (!MoveUtils.isOverVoid(mc)) {
/* 499 */         this.ticksAboveGround++;
/* 500 */         if (this.ticksAboveGround >= 300) {
/* 501 */           setEnabled(false);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(EventTick e) {
/* 510 */     if (((Boolean)this.waiting.get()).booleanValue() && !this.waitingTime.hasReached(((Double)this.waitingDelay.get()).intValue())) {
/*     */       return;
/*     */     }
/*     */     
/* 514 */     EntityPlayerSP player = mc.thePlayer;
/*     */     
/* 516 */     if (player == null) {
/*     */       return;
/*     */     }
/*     */     
/* 520 */     if (this.mode.is("DCJ"))
/*     */     {
/* 522 */       switch (++this.watchdogPosYTicks) {
/*     */         case 1:
/*     */         case 2:
/* 525 */           player.setPosition(player.posX, player.posY + 1.0E-5D, player.posZ);
/*     */           break;
/*     */         
/*     */         case 3:
/* 529 */           player.setPosition(player.posX, player.posY - 1.0E-5D, player.posZ);
/* 530 */           this.watchdogPosYTicks = 0;
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     }
/* 536 */     if (this.mode.is("Boost")) {
/*     */       
/* 538 */       if (((Boolean)this.timer.get()).booleanValue())
/*     */       {
/* 540 */         if (!this.teleportTimeUtil.hasReached(((Boolean)this.noDelayTimerBoost.get()).booleanValue() ? 0.0D : 250.0D)) {
/* 541 */           this.timertimer.reset();
/*     */         } else {
/* 543 */           mc.timer.timerSpeed = 1.0F;
/* 544 */           if (isMoving()) {
/* 545 */             mc.timer
/*     */               
/* 547 */               .timerSpeed = !this.timertimer.hasReached(((Double)this.timerDuration.get()).longValue()) ? ((KillAura.getInstance.getTarget() != null) ? 1.3F : ((Double)this.timerSpeed.get()).floatValue()) : 1.0F;
/*     */           } else {
/* 549 */             mc.timer.timerSpeed = 1.0F;
/*     */           } 
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 555 */       switch (++this.watchdogPosYTicks) {
/*     */         case 1:
/*     */         case 2:
/* 558 */           player.setPosition(player.posX, player.posY + 1.0E-5D, player.posZ);
/*     */           break;
/*     */         
/*     */         case 3:
/* 562 */           player.setPosition(player.posX, player.posY - 1.0E-5D, player.posZ);
/* 563 */           this.watchdogPosYTicks = 0;
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 568 */       if (!this.failedStart) {
/* 569 */         player.motionY = 0.0D;
/*     */       }
/*     */ 
/*     */       
/* 573 */       if (!((Boolean)this.blink.get()).booleanValue()) {
/*     */         return;
/*     */       }
/* 576 */       if (this.blinkplz) {
/* 577 */         synchronized (this.positions) {
/* 578 */           this.positions.add(new double[] { player.posX, (player.getEntityBoundingBox()).minY, player.posZ });
/*     */         } 
/*     */       }
/*     */       
/* 582 */       if (this.blinkingTimerUtil.hasReached(((Double)this.blinkDelay.get()).doubleValue() / mc.timer.timerSpeed)) {
/* 583 */         this.blinkplz = true;
/*     */       }
/*     */       
/* 586 */       if (this.blinkingTimerUtil.hasReached((((Double)this.blinkDelay.get()).doubleValue() + 500.0D) / mc.timer.timerSpeed)) {
/* 587 */         PlayerCapabilities playerCapabilities = new PlayerCapabilities();
/* 588 */         playerCapabilities.isFlying = true;
/* 589 */         playerCapabilities.allowFlying = true;
/* 590 */         playerCapabilities.setFlySpeed((float)MathUtils.randomNumber(0.1D, 9.0D));
/* 591 */         sendPacketNoEvent((Packet)new C0FPacketConfirmTransaction(0, (short)-1, false));
/* 592 */         sendPacketNoEvent((Packet)new C13PacketPlayerAbilities(playerCapabilities));
/* 593 */         blink();
/* 594 */         this.blinkplz = false;
/* 595 */         this.blinkingTimerUtil.reset();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(3)
/*     */   public void onRender(EventRender2D e) {
/* 603 */     if (mc.thePlayer == null) {
/*     */       return;
/*     */     }
/*     */     
/* 607 */     if (((Boolean)this.speedDisplay.get()).booleanValue()) {
/* 608 */       ScaledResolution sr = e.getResolution();
/* 609 */       double xDiff = (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * 2.0D;
/* 610 */       double zDiff = (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * 2.0D;
/* 611 */       BigDecimal bg = BigDecimal.valueOf(MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff) * 10.0D);
/* 612 */       int speed = (int)(bg.intValue() * mc.timer.timerSpeed);
/* 613 */       String str = speed + "block/sec";
/* 614 */       Client.instance.FontLoaders.regular17.drawString(str, ((sr.getScaledWidth() - Client.instance.FontLoaders.regular17.getStringWidth(str)) / 2), (sr.getScaledHeight() / 2 - 20), ColorUtils.WHITE.c);
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onStrafe(EventStrafe event) {
/* 620 */     if (this.mode.is("Vulcan") || this.mode.is("CubeCraft")) {
/* 621 */       event.setSpeed(1.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPost(EventPostUpdate e) {
/* 628 */     if (((Boolean)this.waiting.get()).booleanValue() && !this.waitingTime.hasReached(((Double)this.waitingDelay.get()).intValue())) {
/*     */       return;
/*     */     }
/*     */     
/* 632 */     if (mc.thePlayer == null) {
/*     */       return;
/*     */     }
/*     */     
/* 636 */     double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
/* 637 */     double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
/* 638 */     this.lastDistance = Math.sqrt(xDist * xDist + zDist * zDist);
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onMove(EventMove e) {
/* 644 */     EntityPlayerSP player = mc.thePlayer;
/*     */     
/* 646 */     if (player == null) {
/*     */       return;
/*     */     }
/*     */     
/* 650 */     if (this.stucking) {
/* 651 */       MoveUtils.INSTANCE.pause(e);
/*     */     }
/*     */     
/* 654 */     if (((Boolean)this.waiting.get()).booleanValue() && !this.waitingTime.hasReached(((Double)this.waitingDelay.get()).intValue())) {
/*     */       return;
/*     */     }
/*     */     
/* 658 */     if (((Boolean)this.fastStop.get()).booleanValue() && (
/* 659 */       !isMoving() || !MoveUtils.INSTANCE.isMovingKeyBindingActive())) {
/* 660 */       MoveUtils.INSTANCE.pause(e);
/*     */     }
/*     */ 
/*     */     
/* 664 */     setViewBobbing();
/*     */     
/* 666 */     if (this.mode.is("DCJ")) {
/* 667 */       e.setMoveSpeedNoDamageBoost(0.85D);
/* 668 */     } else if (this.mode.is("Minemora")) {
/* 669 */       if (MoveUtils.INSTANCE.isMovingKeyBindingActive()) {
/* 670 */         e.setMoveSpeed(MoveUtils.INSTANCE.getBaseMoveSpeed());
/*     */       }
/* 672 */     } else if (this.mode.is("HmXix")) {
/* 673 */       mc.timer.timerSpeed = isMoving() ? 5.5F : 1.0F;
/* 674 */       if (this.motiononground.hasReached(5000.0D)) {
/* 675 */         sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY, player.posZ, true));
/* 676 */         this.motiononground.reset();
/*     */       } 
/* 678 */       player.motionY = 0.0D;
/* 679 */       e.y = 0.0D;
/* 680 */       if (mc.gameSettings.keyBindJump.isKeyDown()) {
/* 681 */         player.motionY = 2.0D;
/* 682 */         e.y = 2.0D;
/* 683 */       } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
/* 684 */         player.motionY = -2.0D;
/* 685 */         e.y = -2.0D;
/*     */       } 
/* 687 */       this.moveSpeed = ((Double)this.motionSpeed.get()).floatValue();
/* 688 */       if (MoveUtils.INSTANCE.isMovingKeyBindingActive()) {
/* 689 */         e.setMoveSpeed(this.moveSpeed);
/*     */       }
/*     */     }
/* 692 */     else if (this.mode.is("Motion")) {
/*     */       
/* 694 */       handleVanillaKickBypass();
/*     */       
/* 696 */       player.motionY = 0.0D;
/* 697 */       e.y = 0.0D;
/* 698 */       if (mc.gameSettings.keyBindJump.isKeyDown()) {
/* 699 */         player.motionY = 2.0D;
/* 700 */         e.y = 2.0D;
/* 701 */       } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
/* 702 */         player.motionY = -2.0D;
/* 703 */         e.y = -2.0D;
/*     */       } 
/* 705 */       this.moveSpeed = ((Double)this.motionSpeed.get()).floatValue();
/* 706 */       if (MoveUtils.INSTANCE.isMovingKeyBindingActive()) {
/* 707 */         e.setMoveSpeed(this.moveSpeed);
/*     */       }
/* 709 */     } else if (this.mode.is("Funcraft")) {
/* 710 */       if (this.funcraftTicks.hasTimePassed(2L)) {
/* 711 */         player.setPosition(player.posX, player.posY + 1.0E-5D, player.posZ);
/* 712 */         this.funcraftTicks.reset();
/*     */       } 
/* 714 */     } else if (this.mode.is("Verus")) {
/* 715 */       player.motionY = 0.0D;
/* 716 */     } else if (this.mode.is("Boost")) {
/*     */       
/* 718 */       if (!player.moving()) {
/* 719 */         MoveUtils.INSTANCE.pause(e);
/*     */         
/*     */         return;
/*     */       } 
/* 723 */       if (this.failedStart) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 728 */       double amplifier = 1.0D + (player.isPotionActive(Potion.moveSpeed) ? (0.2D * (player.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1)) : 0.0D);
/* 729 */       double baseSpeed = 0.3D * amplifier;
/*     */       
/* 731 */       switch (this.boostWatchdogStatue) {
/*     */         case 1:
/* 733 */           this.moveSpeed = (player.isPotionActive(Potion.moveSpeed) ? 1.56D : (((Boolean)this.highBPS.get()).booleanValue() ? (2.12D - Math.random() / 1000.0D) : 2.034D)) * baseSpeed;
/* 734 */           this.boostWatchdogStatue = 2;
/*     */           break;
/*     */         case 2:
/* 737 */           this.moveSpeed *= ((Boolean)this.highBPS.get()).booleanValue() ? (2.9D - Math.random() / 1000.0D) : 2.16D;
/* 738 */           this.boostWatchdogStatue = 3;
/*     */           break;
/*     */         case 3:
/* 741 */           this.moveSpeed = this.lastDistance - ((player.ticksExisted % 2 == 0) ? 0.0103D : 0.0123D) * (this.lastDistance - baseSpeed);
/* 742 */           this.boostWatchdogStatue = 4;
/*     */           break;
/*     */         default:
/* 745 */           this.moveSpeed = this.lastDistance - this.lastDistance / 159.8D;
/*     */           break;
/*     */       } 
/*     */       
/* 749 */       this.moveSpeed = Math.max(this.moveSpeed, 0.3D);
/*     */       
/* 751 */       if (!this.failedStart) {
/* 752 */         player.motionY = 0.0D;
/*     */       }
/*     */       
/* 755 */       e.setMoveSpeed(this.moveSpeed);
/*     */     }
/* 757 */     else if (this.mode.is("NCP")) {
/* 758 */       if (player.moveStrafing <= 0.0F && player.moveForward <= 0.0F) {
/* 759 */         this.stage = 1;
/*     */       }
/* 761 */       if (this.stage == 1 && (player.moveForward != 0.0F || player.moveStrafing != 0.0F)) {
/* 762 */         if (!player.onGround) {
/* 763 */           checkModule(Flight.class);
/*     */           return;
/*     */         } 
/* 766 */         this.stage = 2;
/* 767 */         this.moveSpeed = 2.0D * MoveUtils.INSTANCE.getBaseMoveSpeed() - 0.01D;
/* 768 */       } else if (this.stage == 2) {
/* 769 */         this.stage = 3;
/* 770 */         player.motionY = 0.424D;
/* 771 */         e.y = 0.424D;
/* 772 */         this.moveSpeed *= 1.7D;
/* 773 */       } else if (this.stage == 3) {
/* 774 */         this.stage = 4;
/* 775 */         double difference = 0.0D * (this.lastDist - MoveUtils.INSTANCE.getBaseMoveSpeed() - player.fallDistance);
/* 776 */         this.moveSpeed = this.lastDist - difference;
/* 777 */       } else if (this.stage == 4) {
/*     */         
/* 779 */         if (!mc.theWorld.getCollidingBoundingBoxes((Entity)player, player.getEntityBoundingBox().offset(0.0D, player.motionY, 0.0D)).isEmpty() || player.isCollidedVertically)
/*     */         {
/* 781 */           this.stage = 5; } 
/* 782 */         this.moveSpeed = this.lastDist - this.lastDist / 159.0D;
/* 783 */       } else if (this.stage == 5) {
/* 784 */         checkModule(Flight.class);
/* 785 */         this.stage = 1;
/* 786 */       } else if (this.stage == 0) {
/* 787 */         this.stage = 1;
/*     */       } 
/* 789 */       this.moveSpeed = Math.max(this.moveSpeed, MoveUtils.INSTANCE.getBaseMoveSpeed());
/*     */       
/* 791 */       MoveUtils.INSTANCE.setSpeedEvent(e, this.moveSpeed);
/*     */     }
/* 793 */     else if (this.mode.is("WatchdogExploit")) {
/* 794 */       MoveUtils.INSTANCE.setSpeed(MoveUtils.INSTANCE.getBaseMoveSpeed(player));
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onBB(BBSetEvent event) {
/* 800 */     if (((Boolean)this.waiting.get()).booleanValue() && !this.waitingTime.hasReached(((Double)this.waitingDelay.get()).intValue())) {
/*     */       return;
/*     */     }
/*     */     
/* 804 */     if (mc.thePlayer != null) {
/*     */       return;
/*     */     }
/*     */     
/* 808 */     if ((this.mode.is("Verus") || this.mode.is("Funcraft") || this.mode.is("Boost")) && 
/* 809 */       event.getBlock() instanceof net.minecraft.block.BlockAir && event.getY() < mc.thePlayer.posY) {
/* 810 */       event.setBoundingBox(AxisAlignedBB.fromBounds(event.getX(), event
/* 811 */             .getY(), event.getZ(), (event.getX() + 1), mc.thePlayer.posY, (event.getZ() + 1)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void blink() {
/* 817 */     if (((Boolean)this.blink.get()).booleanValue()) {
/*     */       try {
/* 819 */         this.disableLogger = true;
/*     */         
/* 821 */         while (!this.packetLinkedBlockingQueue.isEmpty()) {
/* 822 */           sendPacketNoEvent(this.packetLinkedBlockingQueue.take());
/*     */         }
/*     */         
/* 825 */         Helper.sendMessage("Blinking!");
/* 826 */         this.disableLogger = false;
/* 827 */       } catch (Exception e) {
/* 828 */         e.printStackTrace();
/* 829 */         this.disableLogger = false;
/*     */       } 
/*     */       
/* 832 */       synchronized (this.positions) {
/* 833 */         this.positions.clear();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setViewBobbing() {
/* 842 */     mc.thePlayer.cameraYaw = ((Boolean)this.viewBobbing.get()).booleanValue() ? 0.095F : 0.0F;
/*     */   }
/*     */   
/*     */   private double calculateGround() {
/* 846 */     AxisAlignedBB playerBoundingBox = mc.thePlayer.getEntityBoundingBox();
/* 847 */     double blockHeight = 1.0D;
/*     */     double ground;
/* 849 */     for (ground = mc.thePlayer.posY; ground > 0.0D; ground -= blockHeight) {
/* 850 */       AxisAlignedBB customBox = new AxisAlignedBB(playerBoundingBox.maxX, ground + blockHeight, playerBoundingBox.maxZ, playerBoundingBox.minX, ground, playerBoundingBox.minZ);
/* 851 */       if (mc.theWorld.checkBlockCollision(customBox)) {
/* 852 */         if (blockHeight <= 0.05D) {
/* 853 */           return ground + blockHeight;
/*     */         }
/*     */         
/* 856 */         ground += blockHeight;
/* 857 */         blockHeight = 0.05D;
/*     */       } 
/*     */     } 
/*     */     
/* 861 */     return 0.0D;
/*     */   }
/*     */   
/*     */   public void handleVanillaKickBypass() {
/* 865 */     double ground = calculateGround();
/*     */     
/*     */     double posY;
/*     */     
/* 869 */     for (posY = mc.thePlayer.posY; posY > ground; posY -= 8.0D) {
/* 870 */       sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, posY, mc.thePlayer.posZ, true));
/*     */       
/* 872 */       if (posY - 8.0D < ground) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 877 */     sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, ground, mc.thePlayer.posZ, true));
/*     */     
/* 879 */     for (posY = ground; posY < mc.thePlayer.posY; posY += 8.0D) {
/* 880 */       sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, posY, mc.thePlayer.posZ, true));
/* 881 */       if (posY + 8.0D > mc.thePlayer.posY) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 886 */     sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\Flight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */