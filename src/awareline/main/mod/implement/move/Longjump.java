/*     */ package awareline.main.mod.implement.move;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.EventTick;
/*     */ import awareline.main.event.events.world.moveEvents.EventJump;
/*     */ import awareline.main.event.events.world.moveEvents.EventMove;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.event.events.world.updateEvents.MotionUpdateEvent;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.mod.implement.world.Scaffold;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*     */ import awareline.main.utility.MoveUtils;
/*     */ import awareline.main.utility.timer.TimerUtil;
/*     */ import java.awt.Color;
/*     */ import java.io.Serializable;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*     */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ public class Longjump extends Module {
/*  35 */   public final TimerUtil timer = new TimerUtil(); public static Longjump getInstance;
/*  36 */   private final String[] modes = new String[] { "Watchdog", "WatchdogBow", "NCP", "HmXix", "HmXixSame" };
/*  37 */   private final Mode<String> mode = new Mode("Mode", this.modes, this.modes[0]);
/*  38 */   private final Option<Boolean> glide = new Option("NCPGlide", Boolean.valueOf(false), () -> Boolean.valueOf(this.mode.is("NCP")));
/*  39 */   private final Option<Boolean> antiFlag = new Option("NoFlag", Boolean.valueOf(false), () -> Boolean.valueOf(
/*  40 */         (this.mode.is("NCP") || this.mode.is("HmXixSame") || this.mode.is("HmXix"))));
/*  41 */   private final Option<Boolean> autoToggle = new Option("AutoDisable", Boolean.valueOf(false));
/*  42 */   private final Option<Boolean> fakeDamage = new Option("FakeDamage", Boolean.valueOf(false));
/*  43 */   private final Option<Boolean> spoofY = new Option("SpoofY", Boolean.valueOf(false));
/*  44 */   private final TimerUtil waittingTime = new TimerUtil(); private int stage; private double moveSpeed; private double lastDist;
/*     */   private boolean shouldDisable;
/*     */   private int tick;
/*     */   private boolean shouldBoost;
/*     */   private boolean wait;
/*     */   private double baseSpeed;
/*     */   private boolean airFlag;
/*     */   private double y;
/*     */   private double aa;
/*     */   private double P;
/*     */   private boolean K;
/*     */   private double U;
/*     */   private boolean O;
/*     */   private double X;
/*     */   private boolean H;
/*     */   private boolean ab;
/*     */   private int I;
/*     */   private int ticks;
/*     */   private int sameAirTicks;
/*     */   
/*     */   public Longjump() {
/*  65 */     super("LongJump", new String[] { "lj" }, ModuleType.Movement);
/*  66 */     addSettings(new Value[] { (Value)this.mode, (Value)this.glide, (Value)this.antiFlag, (Value)this.spoofY, (Value)this.fakeDamage, (Value)this.autoToggle });
/*  67 */     getInstance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  72 */     if (this.mode.is("NCP") || this.mode.is("HmXixSame")) {
/*  73 */       if (mc.thePlayer != null) {
/*  74 */         this.moveSpeed = MoveUtils.INSTANCE.getBaseMoveSpeed();
/*     */       }
/*  76 */       this.lastDist = 0.0D;
/*  77 */       this.stage = 0;
/*  78 */       mc.timer.timerSpeed = 1.0F;
/*     */     } 
/*  80 */     if (this.mode.is("WatchdogBow")) {
/*  81 */       this.tick = 0;
/*  82 */       mc.timer.timerSpeed = 1.0F;
/*  83 */     } else if (this.mode.is("Watchdog")) {
/*  84 */       this.ab = true;
/*  85 */       this.I = 0;
/*  86 */       this.O = false;
/*  87 */       this.K = false;
/*  88 */       this.H = false;
/*  89 */       this.P = 0.0D;
/*  90 */       mc.thePlayer.motionY = -0.1552320045166016D;
/*  91 */       mc.timer.timerSpeed = 1.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  97 */     if (mc.thePlayer == null) {
/*     */       return;
/*     */     }
/* 100 */     this.y = mc.thePlayer.posY;
/* 101 */     mc.timer.timerSpeed = 1.0F;
/* 102 */     if (((Boolean)this.fakeDamage.get()).booleanValue())
/*     */     {
/* 104 */       if (!((Boolean)this.antiFlag.get()).booleanValue()) {
/* 105 */         mc.thePlayer.handleStatusUpdate((byte)2);
/*     */       }
/*     */     }
/* 108 */     if (this.mode.is("NCP") || this.mode.is("HmXixSame") || this.mode.is("HmXix")) {
/* 109 */       this.airFlag = !mc.thePlayer.onGround;
/* 110 */       if (((Boolean)this.antiFlag.get()).booleanValue()) {
/* 111 */         this.waittingTime.reset();
/*     */       }
/* 113 */       checkModule(new Class[] { Speed.class, Scaffold.class, Flight.class });
/*     */ 
/*     */       
/* 116 */       this.shouldDisable = ((Boolean)this.autoToggle.getValue()).booleanValue();
/* 117 */       this.timer.reset();
/* 118 */       this.moveSpeed = 0.0D;
/* 119 */       this.lastDist = 0.0D;
/* 120 */       this.sameAirTicks = 0;
/* 121 */     } else if (this.mode.is("WatchdogBow")) {
/* 122 */       if (bowSlot() == -1 || !mc.thePlayer.inventory.hasItem(Items.arrow)) {
/* 123 */         ClientNotification.sendClientMessage("LongJump", "You need bow in your hotbar and arrows", 4000L, ClientNotification.Type.WARNING);
/* 124 */         setEnabled(false);
/*     */         return;
/*     */       } 
/* 127 */       this.wait = true;
/* 128 */     } else if (this.mode.is("Watchdog")) {
/* 129 */       mc.timer.timerSpeed = 1.0F;
/* 130 */       this.ab = true;
/* 131 */       this.I = 0;
/* 132 */       this.O = false;
/* 133 */       this.K = false;
/* 134 */       this.H = false;
/* 135 */       this.P = 0.0D;
/* 136 */       this.U = mc.thePlayer.m();
/* 137 */       this.K = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onTick(EventTick event) {
/* 143 */     if (this.mode.is("WatchdogBow")) {
/* 144 */       if (bowSlot() == -1 || !mc.thePlayer.inventory.hasItem(Items.arrow)) {
/*     */         return;
/*     */       }
/* 147 */       this.tick++;
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onUpdate(EventPreUpdate e) {
/* 153 */     setSuffix((Serializable)this.mode.getValue());
/*     */     
/* 155 */     if (((Boolean)this.spoofY.isEnabled()).booleanValue()) {
/* 156 */       mc.thePlayer.posY = this.y;
/*     */     }
/*     */     
/* 159 */     if (this.mode.is("WatchdogBow")) {
/* 160 */       if (bowSlot() == -1 || !mc.thePlayer.inventory.hasItem(Items.arrow)) {
/*     */         return;
/*     */       }
/* 163 */       this.lastDist = mc.thePlayer.getLastTickDistance();
/* 164 */       this.baseSpeed = mc.thePlayer.getBaseMoveSpeed(0.16D);
/*     */       
/* 166 */       if (mc.thePlayer.hurtResistantTime == 19) {
/* 167 */         this.wait = false;
/* 168 */         this.stage = 1;
/*     */       } 
/* 170 */     } else if (this.mode.is("NCP") || this.mode.is("HmXixSame") || this.mode.is("HmXix")) {
/* 171 */       if (this.airFlag) {
/* 172 */         checkModule(getClass());
/*     */       }
/* 174 */       if (e.getType() == 0) {
/* 175 */         double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
/* 176 */         double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
/* 177 */         this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
/*     */       } 
/* 179 */     } else if (this.mode.is("Watchdog")) {
/* 180 */       this.aa = mc.thePlayer.getLastTickDistance();
/* 181 */       this.X = c();
/*     */     } 
/*     */   }
/*     */   
/*     */   private double c() {
/* 186 */     double d = 0.28630206268501246D;
/* 187 */     int n = mc.thePlayer.b(Potion.moveSpeed);
/* 188 */     int n2 = mc.thePlayer.b(Potion.moveSlowdown);
/* 189 */     double d2 = (n - n2);
/* 190 */     return d + d2 * 0.07D;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPre(EventPreUpdate e) {
/* 195 */     if (this.mode.is("Watchdog")) {
/* 196 */       if (mc.thePlayer.onGround) {
/* 197 */         e.setGround(false);
/* 198 */         if (this.I < a()) {
/* 199 */           mc.thePlayer.motionY = 0.41999998688697815D;
/* 200 */           this.I++;
/*     */         } else {
/* 202 */           this.ab = false;
/*     */         } 
/*     */       } 
/* 205 */       if (!this.ab && 
/* 206 */         mc.thePlayer.offGroundTicks == 1) {
/* 207 */         this.O = true;
/* 208 */         e.setGround(true);
/*     */       } 
/*     */       
/* 211 */       if (this.K && mc.thePlayer.isCollided) {
/* 212 */         setEnabled(false);
/* 213 */         this.K = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onJump(EventJump e) {
/* 220 */     if (this.mode.is("Watchdog") && 
/* 221 */       mc.thePlayer.moving()) {
/* 222 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onMotion(MotionUpdateEvent event) {
/* 229 */     if (this.mode.is("WatchdogBow")) {
/* 230 */       if (bowSlot() == -1 || !mc.thePlayer.inventory.hasItem(Items.arrow)) {
/*     */         return;
/*     */       }
/* 233 */       if (event.getState().equals(MotionUpdateEvent.State.PRE)) {
/* 234 */         if (this.tick == 1) {
/* 235 */           sendPacket((Packet)new C03PacketPlayer());
/* 236 */           sendPacket((Packet)new C09PacketHeldItemChange(bowSlot()));
/* 237 */           sendPacket((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getStackInSlot(bowSlot())));
/* 238 */         } else if (this.tick == 2) {
/* 239 */           sendPacket((Packet)new C03PacketPlayer());
/* 240 */         } else if (this.tick == 3) {
/* 241 */           sendPacket((Packet)new C03PacketPlayer());
/* 242 */         } else if (this.tick == 4) {
/* 243 */           sendPacket((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(event.getX(), event.getY(), event.getZ(), event.getYaw(), -90.0F, event.isOnGround()));
/* 244 */           sendPacket((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
/* 245 */           sendPacket((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
/*     */         } 
/*     */       }
/*     */       
/* 249 */       if (this.tick > 35 && 
/* 250 */         mc.thePlayer.onGround) {
/* 251 */         mc.timer.timerSpeed = 1.0F;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int HUDColor() {
/* 258 */     return (new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue())).getRGB();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void on2D(EventRender2D e) {
/* 263 */     if ((this.mode.is("NCP") || this.mode.is("HmXixSame") || this.mode.is("HmXix")) && (
/* 264 */       (Boolean)this.antiFlag.get()).booleanValue()) {
/* 265 */       ScaledResolution sr = e.getResolution();
/* 266 */       float x = (sr.getScaledWidth() / 2), y = (sr.getScaledHeight() - 45);
/* 267 */       float ok = Math.min(100.0F, (float)this.waittingTime.getElapsedTime() / 5000.0F * 100.0F);
/* 268 */       RenderUtil.drawRect(x - 50.0F, y - 5.0F, x + 50.0F, y, (new Color(0, 0, 0)).getRGB());
/* 269 */       RenderUtil.drawGradientSideways((x - 50.0F), (y - 5.0F), (x - 50.0F + ok), y, HUDColor(), HUDColor() / 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onMove(EventMove e) {
/* 276 */     if (((Boolean)this.antiFlag.get()).booleanValue() && !this.waittingTime.hasReached(5000.0D)) {
/* 277 */       MoveUtils.INSTANCE.pause(e);
/* 278 */       this.sameAirTicks = 0;
/*     */       
/*     */       return;
/*     */     } 
/* 282 */     if (this.sameAirTicks <= 0 && ((Boolean)this.fakeDamage.get()).booleanValue() && ((Boolean)this.antiFlag.get()).booleanValue())
/*     */     {
/* 284 */       mc.thePlayer.handleStatusUpdate((byte)2);
/*     */     }
/*     */     
/* 287 */     switch ((String)this.mode.get()) {
/*     */       case "Watchdog":
/* 289 */         if (!isEnabled()) {
/*     */           return;
/*     */         }
/* 292 */         if (this.ab || !mc.thePlayer.moving()) {
/* 293 */           e.setMoveSpeed(0.0D); break;
/*     */         } 
/* 295 */         c(e);
/* 296 */         b(e);
/*     */         break;
/*     */ 
/*     */       
/*     */       case "WatchdogBow":
/* 301 */         if (bowSlot() == -1 || !mc.thePlayer.inventory.hasItem(Items.arrow)) {
/*     */           break;
/*     */         }
/* 304 */         if (this.wait) {
/* 305 */           e.x = 0.0D;
/* 306 */           e.z = 0.0D; break;
/* 307 */         }  if (mc.thePlayer.moving()) {
/* 308 */           if (mc.thePlayer.onGround) {
/* 309 */             if (this.shouldBoost) {
/* 310 */               e.setY(mc.thePlayer
/* 311 */                   .motionY = mc.thePlayer.getBaseMotionY() + 0.1D * (4 - (mc.thePlayer.isPotionActive(Potion.jump) ? (mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) : 0)));
/* 312 */               this.moveSpeed *= 0.9D;
/*     */             } else {
/* 314 */               this.moveSpeed = mc.thePlayer.getBySprinting() * 2.0D;
/*     */             } 
/*     */           } else {
/* 317 */             if (mc.thePlayer.motionY == 0.03196443960654492D) {
/* 318 */               mc.thePlayer.motionY = -0.13D;
/*     */             }
/* 320 */             this.moveSpeed = this.lastDist - this.lastDist / 55.0D;
/*     */           } 
/*     */           
/* 323 */           MoveUtils.INSTANCE.setSpeed(Math.max(this.moveSpeed, this.baseSpeed));
/* 324 */           this.shouldBoost = mc.thePlayer.onGround;
/* 325 */           if (!this.shouldBoost) {
/* 326 */             setEnabled(false);
/*     */           }
/*     */         } 
/*     */         break;
/*     */       
/*     */       case "HmXixSame":
/* 332 */         if (++this.sameAirTicks >= 30) {
/* 333 */           checkModule(getClass());
/*     */         }
/* 335 */         if (mc.thePlayer.ticksExisted % 2 == 0) {
/* 336 */           mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-5D, mc.thePlayer.posZ);
/*     */         }
/* 338 */         e.setY(mc.thePlayer.motionY = 0.0D);
/* 339 */         mc.thePlayer.setVelocity(0.0D, 0.0D, 0.0D);
/* 340 */         if (mc.thePlayer.moveStrafing <= 0.0F && mc.thePlayer.moveForward <= 0.0F) {
/* 341 */           this.stage = 1;
/*     */         }
/* 343 */         if (this.stage == 1 && (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)) {
/* 344 */           this.stage = 2;
/* 345 */           this.moveSpeed = 2.1D * MoveUtils.INSTANCE.getBaseMoveSpeed() - 0.01D;
/* 346 */         } else if (this.stage == 2) {
/* 347 */           this.stage = 3;
/* 348 */           mc.thePlayer.motionY = 0.424D;
/* 349 */           e.y = 0.424D;
/* 350 */           this.moveSpeed *= 2.0D;
/* 351 */         } else if (this.stage == 3) {
/* 352 */           this.stage = 4;
/* 353 */           double difference = 0.0D * (this.lastDist - MoveUtils.INSTANCE.getBaseMoveSpeed() - mc.thePlayer.fallDistance);
/* 354 */           this.moveSpeed = this.lastDist - difference;
/* 355 */         } else if (this.stage == 4) {
/*     */           
/* 357 */           if (!mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, mc.thePlayer.motionY, 0.0D)).isEmpty() || mc.thePlayer.isCollidedVertically)
/*     */           {
/* 359 */             this.stage = 5; } 
/* 360 */           this.moveSpeed = this.lastDist - this.lastDist / 159.0D;
/* 361 */         } else if (this.stage == 5) {
/* 362 */           if (this.shouldDisable) {
/* 363 */             setEnabled(false);
/* 364 */             if (((Boolean)HUD.notifications.getValue()).booleanValue()) {
/* 365 */               ClientNotification.sendClientMessage("LongJump", "LongJump Toggle", 1600L, ClientNotification.Type.WARNING);
/*     */             } else {
/* 367 */               ClientNotification.sendClientMessage("LongJump", "LongJump AutoDisabled", 1600L, ClientNotification.Type.WARNING);
/*     */             } 
/*     */           } 
/* 370 */           this.stage = 1;
/* 371 */         } else if (this.stage == 0) {
/* 372 */           this.stage = 1;
/*     */         } 
/* 374 */         this.moveSpeed = Math.max(this.moveSpeed, MoveUtils.INSTANCE.getBaseMoveSpeed());
/* 375 */         MoveUtils.INSTANCE.setSpeedEvent(e, this.moveSpeed);
/*     */         break;
/*     */       
/*     */       case "HmXix":
/* 379 */         if (mc.thePlayer.ticksExisted % 2 == 0) {
/* 380 */           mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-5D, mc.thePlayer.posZ);
/*     */         }
/* 382 */         if (mc.thePlayer.moveStrafing <= 0.0F && mc.thePlayer.moveForward <= 0.0F) {
/* 383 */           this.stage = 1;
/*     */         }
/* 385 */         if (this.stage == 1 && (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)) {
/* 386 */           this.stage = 2;
/* 387 */           this.moveSpeed = 2.1D * MoveUtils.INSTANCE.getBaseMoveSpeed() - 0.01D;
/* 388 */         } else if (this.stage == 2) {
/* 389 */           this.stage = 3;
/* 390 */           mc.thePlayer.motionY = 0.424D;
/* 391 */           e.y = 0.424D;
/* 392 */           this.moveSpeed *= 2.0D;
/* 393 */         } else if (this.stage == 3) {
/* 394 */           this.stage = 4;
/* 395 */           double difference = 0.0D * (this.lastDist - MoveUtils.INSTANCE.getBaseMoveSpeed() - mc.thePlayer.fallDistance);
/* 396 */           this.moveSpeed = this.lastDist - difference;
/* 397 */         } else if (this.stage == 4) {
/*     */           
/* 399 */           if (!mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, mc.thePlayer.motionY, 0.0D)).isEmpty() || mc.thePlayer.isCollidedVertically)
/*     */           {
/* 401 */             this.stage = 5; } 
/* 402 */           this.moveSpeed = this.lastDist - this.lastDist / 159.0D;
/* 403 */         } else if (this.stage == 5) {
/* 404 */           if (this.shouldDisable) {
/* 405 */             checkModule(Longjump.class);
/*     */           }
/* 407 */           this.stage = 1;
/* 408 */         } else if (this.stage == 0) {
/* 409 */           this.stage = 1;
/*     */         } 
/* 411 */         this.moveSpeed = Math.max(this.moveSpeed, MoveUtils.INSTANCE.getBaseMoveSpeed());
/* 412 */         MoveUtils.INSTANCE.setSpeedEvent(e, this.moveSpeed);
/*     */         break;
/*     */       
/*     */       case "NCP":
/* 416 */         if (mc.thePlayer.moveStrafing <= 0.0F && mc.thePlayer.moveForward <= 0.0F) {
/* 417 */           this.stage = 1;
/*     */         }
/* 419 */         if (this.stage == 1 && (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)) {
/* 420 */           this.stage = 2;
/* 421 */           this.moveSpeed = 2.0D * MoveUtils.INSTANCE.getBaseMoveSpeed() - 0.01D;
/* 422 */         } else if (this.stage == 2) {
/* 423 */           this.stage = 3;
/* 424 */           mc.thePlayer.motionY = 0.424D;
/* 425 */           e.y = 0.424D;
/* 426 */           this.moveSpeed *= 1.7D;
/* 427 */           if (((Boolean)this.glide.get()).booleanValue()) {
/* 428 */             mc.timer.timerSpeed = 0.4F;
/*     */           }
/* 430 */         } else if (this.stage == 3) {
/* 431 */           this.stage = 4;
/* 432 */           double difference = 0.0D * (this.lastDist - MoveUtils.INSTANCE.getBaseMoveSpeed() - mc.thePlayer.fallDistance);
/* 433 */           this.moveSpeed = this.lastDist - difference;
/* 434 */           if (((Boolean)this.glide.get()).booleanValue()) {
/* 435 */             mc.timer.timerSpeed = 0.6F;
/*     */           }
/* 437 */         } else if (this.stage == 4) {
/*     */           
/* 439 */           if (!mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, mc.thePlayer.motionY, 0.0D)).isEmpty() || mc.thePlayer.isCollidedVertically)
/*     */           {
/* 441 */             this.stage = 5; } 
/* 442 */           this.moveSpeed = this.lastDist - this.lastDist / 159.0D;
/* 443 */         } else if (this.stage == 5) {
/* 444 */           if (this.shouldDisable) {
/* 445 */             checkModule(Longjump.class);
/*     */           }
/* 447 */           this.stage = 1;
/* 448 */         } else if (this.stage == 0) {
/* 449 */           this.stage = 1;
/*     */         } 
/* 451 */         this.moveSpeed = Math.max(this.moveSpeed, MoveUtils.INSTANCE.getBaseMoveSpeed());
/* 452 */         MoveUtils.INSTANCE.setSpeedEvent(e, this.moveSpeed);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int bowSlot() {
/* 459 */     return mc.thePlayer.getSlotByItem((Item)Items.bow);
/*     */   }
/*     */   
/*     */   private void b(EventMove moveEvent) {
/* 463 */     if (mc.thePlayer.onGround) {
/* 464 */       if (this.H) {
/* 465 */         mc.thePlayer.motionY = mc.thePlayer.getJumpMotion();
/* 466 */         moveEvent.setY(mc.thePlayer.motionY);
/* 467 */         this.U *= 2.1399999D;
/*     */       } else {
/* 469 */         this.U = 0.28630206268501246D;
/*     */       } 
/*     */     }
/*     */     
/* 473 */     if (this.H) {
/* 474 */       this.U = this.aa - 0.3D * (this.aa - this.X);
/*     */     }
/* 476 */     this.U -= this.aa / 59.0D;
/* 477 */     this.K = true;
/* 478 */     this.U = Math.max(this.U, this.X);
/* 479 */     this.H = mc.thePlayer.onGround;
/*     */   }
/*     */   
/*     */   private void c(EventMove moveEvent) {
/* 483 */     if (mc.thePlayer.onGround) {
/* 484 */       if (this.H) {
/* 485 */         mc.thePlayer.motionY = mc.thePlayer.getJumpMotion();
/* 486 */         moveEvent.setY(mc.thePlayer.motionY);
/* 487 */         this.U *= 2.1399999D;
/*     */       } else {
/* 489 */         this.U = 0.28630206268501246D;
/*     */       } 
/*     */     }
/*     */     
/* 493 */     if (this.H) {
/* 494 */       this.U = this.aa - 0.76999D * (this.aa - this.X);
/*     */     }
/* 496 */     this.U *= 0.9788305162963192D;
/* 497 */     this.K = true;
/* 498 */     if (mc.thePlayer.offGroundTicks > 1) {
/* 499 */       if (mc.thePlayer.hurtTime > 0 && mc.thePlayer.hurtTime < 6) {
/* 500 */         moveEvent.setMoveSpeed(this.U * MathUtil.random(0.7200000286102295D, 0.8199999928474426D));
/* 501 */         moveEvent.setY(mc.thePlayer.motionY += mc.thePlayer.motionY * -0.04D);
/*     */       } 
/* 503 */       if (this.P > 0.0D && mc.thePlayer.motionY < 0.0D) {
/* 504 */         mc.thePlayer.motionY = this.P;
/* 505 */         moveEvent.setY(mc.thePlayer.motionY);
/* 506 */         this.P = 0.0D;
/*     */       } 
/*     */     } 
/* 509 */     if (mc.thePlayer.offGroundTicks > 3 && this.O) {
/* 510 */       this.U *= 2.2D - 0.35D * mc.thePlayer.b(Potion.moveSpeed);
/* 511 */       this.O = false;
/*     */     } 
/* 513 */     this.U = Math.max(this.U, this.X);
/* 514 */     this.H = mc.thePlayer.onGround;
/*     */   }
/*     */   
/*     */   private int a() {
/* 518 */     int n = mc.thePlayer.b(Potion.jump);
/* 519 */     int n2 = 3;
/* 520 */     if (n == 1) {
/* 521 */       n2 = 4;
/*     */     }
/* 523 */     if (n == 2) {
/* 524 */       n2 = 5;
/*     */     }
/* 526 */     if (n == 3) {
/* 527 */       n2 = 5;
/*     */     }
/* 529 */     if (n == 4) {
/* 530 */       n2 = 6;
/*     */     }
/* 532 */     if (n > 4) {
/* 533 */       n2 = 3 + n;
/*     */     }
/* 535 */     return n2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\Longjump.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */