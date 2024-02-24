/*     */ package awareline.main.mod.implement.combat;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.EventAttack;
/*     */ import awareline.main.event.events.world.moveEvents.EventMove;
/*     */ import awareline.main.event.events.world.packetEvents.EventPacketSend;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.move.Flight;
/*     */ import awareline.main.mod.implement.move.Longjump;
/*     */ import awareline.main.mod.implement.move.Speed;
/*     */ import awareline.main.mod.implement.world.Scaffold;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.MoveUtils;
/*     */ import awareline.main.utility.chat.Helper;
/*     */ import awareline.main.utility.math.MathUtil;
/*     */ import awareline.main.utility.timer.TimerUtil;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ 
/*     */ public class Criticals
/*     */   extends Module {
/*  33 */   private final String[] critMode = new String[] { "Packet", "Mineplex", "Watchdog", "NCP", "RNCP", "OldNCP", "DCJ", "BlockMC", "AACv3", "AACv4", "Vulcan", "Horizon", "Taka", "VerusSmart", "MatrixSemi", "Jump", "Hop", "LowHop", "CustomY", "NoGround", "Edit" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   public final Mode<String> mode = new Mode("CritMode", this.critMode, this.critMode[0]);
/*  44 */   private final Option<Boolean> spoofY = new Option("HideJump", 
/*  45 */       Boolean.valueOf(false), () -> Boolean.valueOf((this.mode.is("Jump") || this.mode.is("LowHop") || this.mode.is("Hop") || this.mode.is("Hop2") || this.mode.is("TPHop"))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   private final Numbers<Double> hurtTime = new Numbers("HurtTime", Double.valueOf(17.0D), Double.valueOf(1.0D), Double.valueOf(20.0D), Double.valueOf(1.0D)); private final Numbers<Double> critDelay = new Numbers("Delay", 
/*  53 */       Double.valueOf(300.0D), Double.valueOf(0.0D), Double.valueOf(5000.0D), Double.valueOf(10.0D));
/*  54 */   private final Numbers<Double> customY = new Numbers("CustomY", Double.valueOf(0.1D), Double.valueOf(0.01D), Double.valueOf(0.42D), Double.valueOf(0.01D), () -> Boolean.valueOf(this.mode.is("CustomY")));
/*     */   
/*  56 */   private final Option<Boolean> checkModule = new Option("CheckModule", Boolean.valueOf(false)), checkEatFood = new Option("CheckEating", 
/*  57 */       Boolean.valueOf(false)),
/*  58 */      checkHungry = new Option("CheckHungry", Boolean.valueOf(false)),
/*  59 */      stuck = new Option("Stuck", Boolean.valueOf(false)),
/*  60 */      onlyNoMove = new Option("OnlyNoMove", Boolean.valueOf(false)),
/*  61 */      packetLimits = new Option("PacketLimits", Boolean.valueOf(false));
/*     */ 
/*     */   
/*  64 */   private final Option<Boolean> particle = new Option("Particle", Boolean.valueOf(false)); private final Option<Boolean> regen = new Option("Regen", 
/*  65 */       Boolean.valueOf(false));
/*  66 */   private final Option<Boolean> random = new Option("Random", Boolean.valueOf(false));
/*  67 */   private final Option<Boolean> debug = new Option("Debug", Boolean.valueOf(false));
/*  68 */   private final TimerUtil delay = new TimerUtil(); private final TimerUtil time = new TimerUtil(); public EntityLivingBase target;
/*     */   public int packetcount;
/*     */   public int stage;
/*     */   public int attacks;
/*     */   double speed;
/*     */   private boolean stopSendPacket;
/*     */   
/*     */   public Criticals() {
/*  76 */     super("Criticals", ModuleType.Combat);
/*  77 */     addSettings(new Value[] { (Value)this.mode, (Value)this.hurtTime, (Value)this.critDelay, (Value)this.customY, (Value)this.random, (Value)this.checkModule, (Value)this.checkEatFood, (Value)this.checkHungry, (Value)this.onlyNoMove, (Value)this.stuck, (Value)this.regen, (Value)this.spoofY, (Value)this.packetLimits, (Value)this.particle, (Value)this.debug });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  84 */     this.attacks = 0;
/*  85 */     if (this.mode.is("NoGround")) {
/*  86 */       if (check()) {
/*  87 */         mc.thePlayer.motionY = 0.41999998688697815D;
/*     */       }
/*  89 */     } else if (this.mode.is("Watchdog")) {
/*  90 */       noti(getHUDName(), "Watchdog criticals only safe with legit jump");
/*  91 */     } else if (this.mode.is("Edit")) {
/*  92 */       noti(getHUDName(), "Edit critcals will keep critcal effect on attacking even air and on ground");
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPre(EventPreUpdate e) {
/*  98 */     if (((Boolean)this.spoofY.get()).booleanValue() && (
/*  99 */       this.mode.is("Jump") || this.mode.is("Hop") || this.mode.is("LowHop") || this.mode.is("TPHop")) && 
/* 100 */       this.target != null && !mc.gameSettings.keyBindJump.isKeyDown() && !mc.thePlayer.onGround && (
/* 101 */       !Speed.getInstance.isEnabled() || Longjump.getInstance.isEnabled())) {
/* 102 */       mc.thePlayer.posY -= mc.thePlayer.posY - mc.thePlayer.lastTickPosY;
/* 103 */       mc.thePlayer.lastTickPosY -= mc.thePlayer.posY - mc.thePlayer.lastTickPosY;
/* 104 */       mc.thePlayer.cameraYaw = mc.thePlayer.cameraPitch = isMoving() ? 0.1F : 0.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onUpdate(EventMove e) {
/* 114 */     getTarget();
/* 115 */     if (((Boolean)this.stuck.get()).booleanValue() && 
/* 116 */       KillAura.getInstance.target != null) {
/* 117 */       MoveUtils.INSTANCE.pause(e);
/*     */     }
/*     */     
/* 120 */     if (((Boolean)this.packetLimits.get()).booleanValue() && 
/* 121 */       this.time.hasReached(1000.0D)) {
/* 122 */       if (this.packetcount > 21) {
/* 123 */         this.stopSendPacket = true;
/* 124 */         this.target = null;
/* 125 */         Helper.sendMessage("[CRIT] Limit packeting..");
/*     */       } else {
/* 127 */         this.stopSendPacket = false;
/*     */       } 
/* 129 */       this.packetcount = 0;
/* 130 */       this.time.reset();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(0)
/*     */   private void onMove(EventMove e) {
/* 137 */     if (this.target == null) {
/*     */       return;
/*     */     }
/* 140 */     if (this.mode.is("LowHop")) {
/* 141 */       if (MoveUtils.INSTANCE.isOnGround(0.01D) && (this.stage >= 0 || mc.thePlayer.isCollidedHorizontally)) {
/* 142 */         this.stage = 0;
/* 143 */         double y = 0.4001999986886975D + MoveUtils.INSTANCE.getJumpEffect() * 0.099D;
/* 144 */         e.setY(y);
/*     */       } 
/* 146 */       this.speed = Speed.getInstance.getBaseMovementSpeed();
/* 147 */       e.setMoveSpeedNoDamageBoost(this.speed);
/* 148 */       this.stage++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onAttack(EventAttack e) {
/* 155 */     if (((Boolean)this.checkModule.get()).booleanValue() && 
/* 156 */       isMoving() && (
/* 157 */       Speed.getInstance.isEnabled() || Scaffold.getInstance.isEnabled() || Flight.getInstance
/* 158 */       .isEnabled() || Longjump.getInstance.isEnabled())) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 164 */     if (((Boolean)this.checkHungry.get()).booleanValue() && mc.thePlayer.getFoodStats().getFoodLevel() <= 19) {
/*     */       return;
/*     */     }
/* 167 */     if (((Boolean)this.checkEatFood.get()).booleanValue() && isEating()) {
/*     */       return;
/*     */     }
/* 170 */     if (((Boolean)this.onlyNoMove.get()).booleanValue() && isMoving()) {
/*     */       return;
/*     */     }
/* 173 */     if (e.entity != null && this.target.hurtResistantTime <= ((Double)this.hurtTime
/* 174 */       .get()).intValue() && this.delay.hasReached(getDelay()) && (
/* 175 */       !((Boolean)this.random.get()).booleanValue() || ThreadLocalRandom.current().nextBoolean())) {
/* 176 */       if (this.mode.is("Vulcan")) {
/* 177 */         this.attacks++;
/* 178 */         if (this.attacks > 7) {
/* 179 */           sendCriticalPacket(0.0D, 0.16477328182606651D, 0.0D, false);
/* 180 */           sendCriticalPacket(0.0D, 0.08307781780646721D, 0.0D, false);
/* 181 */           sendCriticalPacket(0.0D, 0.0030162615090425808D, 0.0D, false);
/* 182 */           this.attacks = 0;
/*     */         } 
/* 184 */       } else if (this.mode.is("Taka")) {
/* 185 */         this.attacks++;
/* 186 */         if (this.attacks >= 5) {
/* 187 */           sendCriticalPacket(0.0D, 0.33319999363422365D, 0.0D, false);
/* 188 */           sendCriticalPacket(0.0D, 0.24813599859094576D, 0.0D, false);
/* 189 */           sendCriticalPacket(0.0D, 0.16477328182606651D, 0.0D, false);
/* 190 */           sendCriticalPacket(0.0D, 0.08307781780646721D, 0.0D, false);
/* 191 */           this.attacks = 0;
/*     */         } 
/* 193 */       } else if (this.mode.is("MatrixSemi")) {
/* 194 */         this.attacks++;
/* 195 */         if (this.attacks > 3) {
/* 196 */           sendCriticalPacket(0.0D, 0.0825080378093D, 0.0D, false);
/* 197 */           sendCriticalPacket(0.0D, 0.023243243674D, 0.0D, false);
/* 198 */           sendCriticalPacket(0.0D, 0.0215634532004D, 0.0D, false);
/* 199 */           sendCriticalPacket(0.0D, 0.00150000001304D, 0.0D, false);
/* 200 */           this.attacks = 0;
/*     */         } 
/* 202 */       } else if (this.mode.is("VerusSmart")) {
/* 203 */         this.attacks++;
/* 204 */         if (this.attacks > 4) {
/* 205 */           this.attacks = 0;
/* 206 */           sendCriticalPacket(0.0D, 0.001D, 0.0D, true);
/* 207 */           sendCriticalPacket(0.0D, 0.0D, 0.0D, false);
/*     */         } 
/*     */       } 
/* 210 */       this.delay.reset();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onPacket(EventPacketSend e) {
/* 217 */     setSuffix((String)this.mode.get() + " " + ((Double)this.hurtTime.get()).intValue());
/*     */     
/* 219 */     if (((Boolean)this.checkModule.get()).booleanValue() && (
/* 220 */       Speed.getInstance.isEnabled() || Scaffold.getInstance.isEnabled() || Flight.getInstance
/* 221 */       .isEnabled() || Longjump.getInstance.isEnabled())) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 226 */     if (((Boolean)this.checkHungry.get()).booleanValue() && mc.thePlayer.getFoodStats().getFoodLevel() <= 19) {
/*     */       return;
/*     */     }
/* 229 */     if (((Boolean)this.checkEatFood.get()).booleanValue() && isEating()) {
/*     */       return;
/*     */     }
/* 232 */     if (((Boolean)this.onlyNoMove.get()).booleanValue() && isMoving()) {
/*     */       return;
/*     */     }
/* 235 */     if (((Boolean)this.packetLimits.get()).booleanValue()) {
/* 236 */       if (e.getPacket() instanceof C03PacketPlayer) {
/* 237 */         this.packetcount++;
/*     */       }
/* 239 */       if (this.stopSendPacket) {
/* 240 */         this.target = null;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 245 */     if (e.getPacket() instanceof net.minecraft.network.play.client.C0APacketAnimation && this.target != null && this.target.hurtResistantTime <= ((Double)this.hurtTime
/* 246 */       .get()).intValue() && this.delay.hasReached(getDelay()) && (
/* 247 */       !((Boolean)this.random.get()).booleanValue() || ThreadLocalRandom.current().nextBoolean())) {
/* 248 */       if (((Boolean)this.debug.get()).booleanValue()) {
/* 249 */         Helper.sendMessage("CRIT: " + (int)MathUtil.randomNumber(-9999.0D, 9999.0D));
/*     */       }
/* 251 */       if (((Boolean)this.particle.get()).booleanValue()) {
/* 252 */         mc.thePlayer.onCriticalHit((Entity)this.target);
/*     */       }
/* 254 */       if (this.mode.is("BlockMC")) {
/* 255 */         if (NCPCheck()) {
/* 256 */           crit(new double[] { 0.0825080378093D, 0.0215634532004D, 0.1040220332227D });
/*     */         }
/* 258 */       } else if (this.mode.is("Mineplex")) {
/* 259 */         if (NCPCheck()) {
/* 260 */           crit(new double[] { 4.5E-15D, 4.5E-15D });
/*     */         }
/* 262 */       } else if (this.mode.is("OldNCP")) {
/* 263 */         if (mc.thePlayer.onGround) {
/* 264 */           crit(new double[] { 0.05D, 0.0D, 0.012511D, 0.0D });
/*     */         }
/* 266 */       } else if (this.mode.is("RNCP")) {
/* 267 */         if (mc.thePlayer.onGround) {
/* 268 */           crit(new double[] { 0.11D, 0.1100013579D });
/*     */         }
/* 270 */       } else if (this.mode.is("NCP")) {
/* 271 */         if (check()) {
/* 272 */           double[] ncpDouble = { 0.11D, 0.1100013579D };
/* 273 */           for (double packets : ncpDouble) {
/*     */             
/* 275 */             if (packets + 2.0E-4D <= 0.0625D && packets != 0.0D) {
/* 276 */               packets += random(-2.0E-4D, 2.0E-4D);
/*     */             }
/* 278 */             if (packets >= 0.0625D) {
/* 279 */               packets += random(-1.9999999494757503E-4D, 0.0D);
/*     */             }
/* 281 */             packets += random(0.0D, 2.0E-4D);
/*     */             
/* 283 */             crit(new double[] { packets });
/*     */           } 
/*     */         } 
/* 286 */       } else if (this.mode.is("Packet")) {
/* 287 */         if (mc.thePlayer.onGround) {
/* 288 */           crit(new double[] { 0.00521D, 0.0052D, 0.00419D, 0.0123333333D, 0.01213213132123D, 0.05D, 1.086765133E-10D });
/*     */         }
/* 290 */       } else if (this.mode.is("AACv4")) {
/* 291 */         if (safeCheck() && !isMoving()) {
/* 292 */           mc.thePlayer.motionX = 0.0D;
/* 293 */           mc.thePlayer.motionZ = 0.0D;
/* 294 */           mc.thePlayer.jumpMovementFactor = 0.0F;
/* 295 */           sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.0E-14D, mc.thePlayer.posZ, false));
/* 296 */           sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 8.0E-15D, mc.thePlayer.posZ, false));
/*     */         } 
/* 298 */       } else if (this.mode.is("Horizon")) {
/* 299 */         if (check()) {
/* 300 */           sendCriticalPacket(0.0D, 1.0E-4D, 0.0D, false);
/* 301 */           sendCriticalPacket(0.0D, 0.0D, 0.0D, false);
/*     */         } 
/* 303 */       } else if (this.mode.is("BlocksMC")) {
/* 304 */         if (check()) {
/* 305 */           sendCriticalPacket(0.0D, 0.0825080378093D, 0.0D, false);
/* 306 */           sendCriticalPacket(0.0D, 0.0215634532004D, 0.0D, false);
/* 307 */           sendCriticalPacket(0.0D, 0.1040220332227D, 0.0D, false);
/*     */         } 
/* 309 */       } else if (this.mode.is("AACv3")) {
/* 310 */         if (safeCheck()) {
/* 311 */           crit(new double[] { 0.05250000001304D, 0.00150000001304D, 0.01400000001304D, 0.00150000001304D });
/*     */         }
/* 313 */       } else if (this.mode.is("Jump")) {
/* 314 */         if (safeCheck()) {
/* 315 */           mc.thePlayer.jump();
/*     */         }
/* 317 */       } else if (this.mode.is("Hop")) {
/* 318 */         if (mc.thePlayer.onGround) {
/* 319 */           mc.thePlayer.motionY = 0.10000000149011612D;
/* 320 */           mc.thePlayer.fallDistance = 0.1F;
/* 321 */           mc.thePlayer.onGround = false;
/*     */         } 
/* 323 */       } else if (this.mode.is("DCJ")) {
/* 324 */         if (safeCheck() && !mc.gameSettings.keyBindJump.isKeyDown())
/*     */         {
/* 326 */           if (this.target.hurtResistantTime > 17) {
/* 327 */             msg("packet crit");
/* 328 */             sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.1D, mc.thePlayer.posZ, false));
/* 329 */             mc.thePlayer.motionY = 0.10000000149011612D;
/* 330 */             mc.thePlayer.fallDistance = 0.1F;
/* 331 */             mc.thePlayer.onGround = false;
/*     */           } else {
/*     */             
/* 334 */             mc.thePlayer.motionY = 0.41999998688697815D;
/*     */           }
/*     */         
/*     */         }
/*     */       }
/* 339 */       else if (this.mode.is("CustomY") && 
/* 340 */         mc.thePlayer.onGround) {
/* 341 */         mc.thePlayer.motionY = ((Double)this.customY.get()).floatValue();
/* 342 */         mc.thePlayer.fallDistance = ((Double)this.customY.get()).floatValue();
/* 343 */         mc.thePlayer.onGround = false;
/*     */       } 
/*     */       
/* 346 */       this.delay.reset();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 352 */     if (this.mode.is("Watchdog")) {
/* 353 */       if (safeCheck() && e.getPacket() instanceof net.minecraft.network.play.client.C0APacketAnimation && this.target != null && this.target.hurtResistantTime <= ((Double)this.hurtTime
/* 354 */         .get()).intValue() && this.delay
/* 355 */         .hasReached(getDelay()) && (
/* 356 */         !((Boolean)this.random.get()).booleanValue() || ThreadLocalRandom.current().nextBoolean())) {
/* 357 */         mc.thePlayer.onCriticalHit((Entity)this.target);
/* 358 */         if (MoveUtils.INSTANCE.isOnGround(-1.0D) && mc.thePlayer.ticksExisted % (isMoving() ? 3 : 2) == 0) {
/* 359 */           legitJump();
/* 360 */         } else if (this.target.moveForward != 0.0F && this.target.moveStrafing != 0.0F) {
/* 361 */           if (mc.thePlayer.hurtResistantTime == 0 && mc.thePlayer.onGroundTicks > 0) {
/* 362 */             legitJump();
/*     */           }
/*     */         } else {
/* 365 */           legitJump();
/*     */         } 
/*     */       } 
/* 368 */     } else if (this.mode.is("Edit")) {
/* 369 */       if (check() && e.getPacket() instanceof C03PacketPlayer) {
/* 370 */         C03PacketPlayer packet = (C03PacketPlayer)e.getPacket();
/* 371 */         packet.setOnGround(false);
/* 372 */         if (this.target != null && this.target.hurtResistantTime <= ((Double)this.hurtTime
/* 373 */           .get()).intValue() && this.delay.hasReached(getDelay()) && (
/* 374 */           !((Boolean)this.random.get()).booleanValue() || ThreadLocalRandom.current().nextBoolean())) {
/* 375 */           packet.setY(packet.getY() + ThreadLocalRandom.current().nextDouble(0.11921599284565D));
/*     */         }
/*     */       } 
/* 378 */     } else if (this.mode.is("NoGround") && 
/* 379 */       e.getPacket() instanceof C03PacketPlayer) {
/* 380 */       C03PacketPlayer packet = (C03PacketPlayer)e.getPacket();
/* 381 */       packet.setOnGround(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void legitJump() {
/* 387 */     if (mc.thePlayer.onGround) {
/* 388 */       if (isMoving()) {
/* 389 */         mc.thePlayer.jump();
/*     */       } else {
/* 391 */         mc.thePlayer.motionY = 0.41999998688697815D;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private double random(double min, double max) {
/* 397 */     return min + ThreadLocalRandom.current().nextFloat() * (max - min);
/*     */   }
/*     */   
/*     */   private void crit(double[] value) {
/* 401 */     double x = mc.thePlayer.posX, y = mc.thePlayer.posY, z = mc.thePlayer.posZ;
/*     */     
/* 403 */     if (((Boolean)this.regen.get()).booleanValue()) {
/* 404 */       sendPacketNoEvent((Packet)new C03PacketPlayer(true));
/*     */     }
/*     */     
/* 407 */     for (double v : value) {
/* 408 */       sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, y + v, z, true));
/* 409 */       sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean check() {
/* 414 */     return (!mc.thePlayer.isInWater() && !mc.thePlayer.isInWeb && mc.thePlayer.onGround);
/*     */   }
/*     */   
/*     */   private boolean safeCheck() {
/* 418 */     return (mc.thePlayer.onGround && !mc.thePlayer.isOnLadder() && 
/* 419 */       !mc.thePlayer.isInWater() && !mc.thePlayer.isPotionActive(Potion.blindness) && mc.thePlayer.ridingEntity == null);
/*     */   }
/*     */   
/*     */   private boolean NCPCheck() {
/* 423 */     return (!mc.thePlayer.isInWater() && !mc.thePlayer.isInWeb && !mc.thePlayer.isCollidedHorizontally && mc.thePlayer.onGround);
/*     */   }
/*     */   
/*     */   public boolean isEating() {
/* 427 */     return (mc.thePlayer.isUsingItem() && mc.thePlayer.getItemInUse().getItem().getItemUseAction(mc.thePlayer.getItemInUse()) == EnumAction.EAT);
/*     */   }
/*     */   
/*     */   public final void sendCriticalPacket(double xOffset, double yOffset, double zOffset, boolean ground) {
/* 431 */     double x = mc.thePlayer.posX + xOffset, y = mc.thePlayer.posY + yOffset, z = mc.thePlayer.posZ + zOffset;
/* 432 */     sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, ground));
/*     */   }
/*     */   
/*     */   public double getDelay() {
/* 436 */     return Math.abs(((Double)this.critDelay.get()).doubleValue());
/*     */   }
/*     */ 
/*     */   
/*     */   private void getTarget() {
/* 441 */     if (KillAura.getInstance.getTarget() != null || VanillaAura.getInstance.lastEntity != null) {
/* 442 */       this.target = KillAura.getInstance.getTarget();
/*     */       
/*     */       return;
/*     */     } 
/* 446 */     MovingObjectPosition mouseOver = mc.objectMouseOver;
/* 447 */     if (mouseOver != null)
/* 448 */       if (mouseOver.entityHit instanceof EntityLivingBase) {
/* 449 */         this.target = (EntityLivingBase)mouseOver.entityHit;
/*     */       } else {
/* 451 */         this.target = null;
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\Criticals.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */