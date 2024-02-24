/*      */ package awareline.main.mod.implement.move;
/*      */ 
/*      */ import awareline.main.event.EventHandler;
/*      */ import awareline.main.event.events.LBEvents.EventWorldChanged;
/*      */ import awareline.main.event.events.world.moveEvents.EventMove;
/*      */ import awareline.main.event.events.world.moveEvents.EventStep;
/*      */ import awareline.main.event.events.world.moveEvents.EventStrafe;
/*      */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*      */ import awareline.main.event.events.world.updateEvents.EventPostUpdate;
/*      */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*      */ import awareline.main.mod.Module;
/*      */ import awareline.main.mod.ModuleType;
/*      */ import awareline.main.mod.implement.combat.auto.AutoPotion;
/*      */ import awareline.main.mod.implement.move.antifallutily.TimeHelper;
/*      */ import awareline.main.mod.implement.world.GameSpeed;
/*      */ import awareline.main.mod.implement.world.Scaffold;
/*      */ import awareline.main.mod.values.Mode;
/*      */ import awareline.main.mod.values.Numbers;
/*      */ import awareline.main.mod.values.Option;
/*      */ import awareline.main.mod.values.Value;
/*      */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*      */ import awareline.main.utility.BlockUtils;
/*      */ import awareline.main.utility.MoveUtils;
/*      */ import awareline.main.utility.PlayerUtil;
/*      */ import io.netty.util.internal.ThreadLocalRandom;
/*      */ import java.io.Serializable;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.RoundingMode;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.MathHelper;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Speed
/*      */   extends Module
/*      */ {
/*      */   public static Speed getInstance;
/*   49 */   public final Option<Boolean> dmgboost = new Option("DamageBoost", Boolean.valueOf(false));
/*   50 */   public final Numbers<Double> dmgzoom = new Numbers("DamageBoosts", 
/*   51 */       Double.valueOf(2.0D), Double.valueOf(0.1D), Double.valueOf(10.0D), Double.valueOf(0.1D), this.dmgboost::get);
/*   52 */   private final String[] SpeedMode = new String[] { "Watchdog", "ACRGround", "NCP", "NCPBhop", "OldNCP", "SpartanBhop", "OldMatrix", "Matrix", "HmXix", "AAC3.5.0", "AAC3.3.13", "AACv4", "AACv5", "LowHop", "LowJump", "LowJump2", "LowJumpFast", "Bhop", "Jump", "GroundStrafeHop", "VerusYport", "MineMenClub", "Vulcan", "KoksCraft", "BlocksMC" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   62 */   public final Mode<String> mode = new Mode("Mode", this.SpeedMode, "OldNCP");
/*      */ 
/*      */ 
/*      */   
/*   66 */   private final Option<Boolean> should = new Option("LowHop-Jump", Boolean.valueOf(true), () -> Boolean.valueOf(this.mode.is("LowHop")));
/*      */   
/*   68 */   private final Option<Boolean> lowHopNoCheckTarget = new Option("LowHop-NoCheckTarget", Boolean.valueOf(false), () -> Boolean.valueOf(this.mode.is("LowHop")));
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   73 */   private final Option<Boolean> oldNCPSlowDown = new Option("NCPBypass", Boolean.valueOf(false), () -> Boolean.valueOf(this.mode.is("OldNCP")));
/*      */   
/*   75 */   private final Option<Boolean> jumpModeNoStrafe = new Option("JumpNoStrafe", Boolean.valueOf(false), () -> Boolean.valueOf(this.mode.is("Jump")));
/*      */   
/*   77 */   private final Mode<String> lowJumpStrafe = new Mode("LowJumpStrafe", new String[] { "Full", "Angle", "None" }, "Angle", () -> Boolean.valueOf(
/*      */         
/*   79 */         (this.mode.is("LowJump") || this.mode.is("LowJump2") || this.mode.is("LowJumpFast"))));
/*      */ 
/*      */ 
/*      */   
/*   83 */   private final Option<Boolean> timerboost = new Option("TimerBoost", Boolean.valueOf(false));
/*   84 */   private final Numbers<Double> timermax = new Numbers("TimerMax", 
/*   85 */       Double.valueOf(0.98D), Double.valueOf(0.4D), Double.valueOf(5.0D), Double.valueOf(0.01D), this.timerboost::get);
/*   86 */   private final Numbers<Double> timermin = new Numbers("TimerMin", 
/*   87 */       Double.valueOf(1.17D), Double.valueOf(0.4D), Double.valueOf(5.0D), Double.valueOf(0.01D), this.timerboost::get);
/*   88 */   private final Numbers<Double> timertick = new Numbers("TimerTick", 
/*   89 */       Double.valueOf(77.2D), Double.valueOf(0.0D), Double.valueOf(200.0D), Double.valueOf(1.0D), this.timerboost::get);
/*      */ 
/*      */ 
/*      */   
/*   93 */   private final Option<Boolean> autodisable = new Option("AutoDisable", Boolean.valueOf(true));
/*   94 */   private final Option<Boolean> spoofY = new Option("HideJump", Boolean.valueOf(false));
/*   95 */   private final Option<Boolean> jumpnobob = new Option("NoBob", Boolean.valueOf(false)); private final TimeHelper timer; private final TimeHelper lastCheck; public boolean collided; public boolean cooldown; public boolean shouldslow;
/*      */   public boolean onGround;
/*      */   public boolean lessSlow;
/*   98 */   public int level = 1;
/*      */   
/*      */   public double movementSpeed;
/*      */   
/*      */   int jumps;
/*      */   private int airMoves;
/*      */   private int stage;
/*  105 */   private double moveSpeed = 0.2873D; private double speed; private double xDist;
/*      */   private double less;
/*      */   private double stair;
/*      */   private double lastDist;
/*      */   private int timerDelay;
/*      */   private boolean firstJump;
/*      */   private boolean waitForGround;
/*      */   private boolean reset;
/*      */   private double distance;
/*      */   
/*      */   public Speed() {
/*  116 */     super("Speed", ModuleType.Movement);
/*  117 */     addSettings(new Value[] { (Value)this.mode, (Value)this.dmgboost, (Value)this.dmgzoom, (Value)this.jumpModeNoStrafe, (Value)this.oldNCPSlowDown, (Value)this.timerboost, (Value)this.timermax, (Value)this.timermin, (Value)this.timertick, (Value)this.lowJumpStrafe, (Value)this.should, (Value)this.lowHopNoCheckTarget, (Value)this.spoofY, (Value)this.jumpnobob, (Value)this.autodisable });
/*      */ 
/*      */ 
/*      */     
/*  121 */     this.shouldslow = false;
/*  122 */     this.timer = new TimeHelper();
/*  123 */     this.lastCheck = new TimeHelper();
/*  124 */     getInstance = this;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onDisable() {
/*  129 */     mc.timer.timerSpeed = 1.0F;
/*  130 */     mc.thePlayer.speedInAir = 0.02F;
/*  131 */     mc.thePlayer.jumpMovementFactor = 0.02F;
/*  132 */     if (this.mode.is("NCPBhop")) {
/*  133 */       mc.timer.timerSpeed = 1.0F;
/*  134 */       this.moveSpeed = MoveUtils.INSTANCE.getBaseMoveSpeed();
/*  135 */       this.level = 0;
/*  136 */     } else if (this.mode.is("BlocksMC")) {
/*  137 */       this.speed = 0.0D;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onEnable() {
/*  143 */     checkModule(Flight.class);
/*  144 */     mc.timer.timerSpeed = 1.0F;
/*  145 */     mc.thePlayer.speedInAir = 0.02F;
/*  146 */     if (this.mode.is("OldNCP")) {
/*  147 */       mc.thePlayer.resetLastTickDistance();
/*  148 */     } else if (this.mode.is("NCPBhop")) {
/*  149 */       mc.timer.timerSpeed = 1.0F;
/*  150 */       this.level = (!mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, mc.thePlayer.motionY, 0.0D)).isEmpty() || mc.thePlayer.isCollidedVertically) ? 1 : 4;
/*  151 */     } else if (this.mode.is("AAC3.3.13")) {
/*  152 */       this.firstJump = true;
/*  153 */     } else if (this.mode.is("NCP")) {
/*  154 */       this.collided = mc.thePlayer.isCollidedHorizontally;
/*  155 */       this.lessSlow = false;
/*  156 */       this.speed = MoveUtils.INSTANCE.defaultSpeed();
/*  157 */       this.less = 0.0D;
/*  158 */       this.stage = 2;
/*  159 */       mc.timer.timerSpeed = 1.0F;
/*  160 */     } else if (this.mode.is("KoksCraft")) {
/*  161 */       this.jumps = 0;
/*      */     } else {
/*  163 */       mc.thePlayer.resetLastTickDistance();
/*  164 */       this.lessSlow = false;
/*  165 */       this.less = 0.0D;
/*  166 */       this.stage = 2;
/*  167 */       mc.timer.timerSpeed = 1.0F;
/*      */     } 
/*  169 */     if (AutoPotion.getInstance.isEnabled() && AutoPotion.getInstance.stuck) {
/*  170 */       AutoPotion.getInstance.stuck = false;
/*      */     }
/*      */   }
/*      */   
/*      */   @EventHandler
/*      */   public void onStep(EventStep event) {
/*  176 */     if (this.mode.is("OldNCP")) {
/*  177 */       double height = (mc.thePlayer.getEntityBoundingBox()).minY - mc.thePlayer.posY;
/*  178 */       if (height > 0.7D) {
/*  179 */         this.less = 0.0D;
/*      */       }
/*  181 */       if (height == 0.5D) {
/*  182 */         this.stair = 0.75D;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   @EventHandler
/*      */   public void onPacket(PacketEvent event) {
/*  189 */     if (this.mode.is("BlocksMC") && 
/*  190 */       event.getState() == PacketEvent.State.OUTGOING) { this; if (mc.thePlayer != null) { this; if (mc.theWorld != null && 
/*  191 */           event.getPacket() instanceof C0BPacketEntityAction) {
/*  192 */           event.setCancelled(true);
/*      */         } }
/*      */        }
/*      */   
/*      */   }
/*      */   
/*      */   @EventHandler
/*      */   public void onPost(EventPostUpdate event) {
/*  200 */     if (BlockUtils.isInLiquid() || BlockUtils.isOnLiquid() || mc.thePlayer
/*  201 */       .isInWater() || mc.thePlayer.isInWeb || mc.thePlayer.isSneaking() || 
/*  202 */       BlockUtils.isInsideBlock()) {
/*      */       return;
/*      */     }
/*  205 */     if (this.mode.is("AAC3.5.0")) {
/*  206 */       if (mc.thePlayer.isSneaking()) {
/*      */         return;
/*      */       }
/*  209 */       if (mc.thePlayer.moving() && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava() && 
/*  210 */         mc.thePlayer.fallDistance <= 1.0F) {
/*  211 */         if (mc.thePlayer.onGround) {
/*  212 */           mc.thePlayer.jump();
/*  213 */           mc.thePlayer.motionX *= 1.0118000507354736D;
/*  214 */           mc.thePlayer.motionZ *= 1.0118000507354736D;
/*      */         } else {
/*  216 */           mc.thePlayer.motionY -= 0.014700000174343586D;
/*  217 */           mc.thePlayer.motionX *= 1.0013799667358398D;
/*  218 */           mc.thePlayer.motionZ *= 1.0013799667358398D;
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @EventHandler
/*      */   public void onStrafe(EventStrafe event) {
/*  227 */     if (Scaffold.getInstance.isEnabled() && ((Boolean)Scaffold.getInstance.StopSpeed
/*  228 */       .get()).booleanValue()) {
/*      */       return;
/*      */     }
/*  231 */     if (BlockUtils.isInLiquid() || BlockUtils.isOnLiquid() || mc.thePlayer
/*  232 */       .isInWater() || mc.thePlayer.isInWeb || mc.thePlayer.isSneaking() || 
/*  233 */       BlockUtils.isInsideBlock()) {
/*      */       return;
/*      */     }
/*  236 */     if (this.mode.is("MineMenClub")) {
/*  237 */       if (mc.thePlayer.hurtTime <= 6) {
/*  238 */         MoveUtils.INSTANCE.strafe();
/*      */       }
/*  240 */     } else if (this.mode.is("Vulcan")) {
/*  241 */       if (!isMoving()) {
/*      */         return;
/*      */       }
/*  244 */       switch (mc.thePlayer.offGroundTicks) {
/*      */         case 0:
/*  246 */           mc.thePlayer.jump();
/*      */           
/*  248 */           if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
/*  249 */             MoveUtils.INSTANCE.strafe(0.6D); break;
/*      */           } 
/*  251 */           MoveUtils.INSTANCE.strafe(0.485D);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 9:
/*  256 */           if (!(PlayerUtil.blockRelativeToPlayer(0.0D, mc.thePlayer.motionY, 0.0D) instanceof net.minecraft.block.BlockAir))
/*      */           {
/*  258 */             MoveUtils.INSTANCE.strafe();
/*      */           }
/*      */           break;
/*      */         
/*      */         case 1:
/*      */         case 2:
/*  264 */           MoveUtils.INSTANCE.strafe();
/*      */           break;
/*      */         
/*      */         case 5:
/*  268 */           mc.thePlayer.motionY = MoveUtils.INSTANCE.predictedMotion(mc.thePlayer.motionY, 2);
/*      */           break;
/*      */       } 
/*  271 */     } else if (this.mode.is("BlocksMC")) {
/*  272 */       double base = MoveUtils.INSTANCE.getAllowedHorizontalDistance();
/*  273 */       boolean potionActive = mc.thePlayer.isPotionActive(Potion.moveSpeed);
/*      */       
/*  275 */       if (isMoving()) {
/*  276 */         switch (mc.thePlayer.offGroundTicks) {
/*      */           case 0:
/*  278 */             mc.thePlayer.motionY = MoveUtils.INSTANCE.jumpBoostMotion(0.41999998688697815D);
/*  279 */             this.speed = base * (potionActive ? 2.0D : 2.15D);
/*      */             break;
/*      */           
/*      */           case 1:
/*  283 */             this.speed -= 0.8D * (this.speed - base);
/*      */             break;
/*      */           
/*      */           default:
/*  287 */             MoveUtils.INSTANCE.getClass(); this.speed -= this.speed / 159.89999389648438D;
/*      */             break;
/*      */         } 
/*      */         
/*  291 */         this.reset = false;
/*  292 */       } else if (!this.reset) {
/*  293 */         this.speed = 0.0D;
/*      */         
/*  295 */         this.reset = true;
/*  296 */         this.speed = MoveUtils.INSTANCE.getAllowedHorizontalDistance();
/*      */       } 
/*      */       
/*  299 */       if (mc.thePlayer.isCollidedHorizontally) {
/*  300 */         this.speed = MoveUtils.INSTANCE.getAllowedHorizontalDistance();
/*      */       }
/*      */       
/*  303 */       event.setSpeed(Math.max(this.speed, base), Math.random() / 2000.0D);
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getSpeedPotion() {
/*  308 */     return mc.thePlayer.isPotionActive(Potion.moveSpeed) ? (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1) : 0;
/*      */   }
/*      */   
/*      */   @EventHandler
/*      */   public void onPre(EventPreUpdate event) {
/*  313 */     if (Scaffold.getInstance.isEnabled() && ((Boolean)Scaffold.getInstance.StopSpeed
/*  314 */       .get()).booleanValue()) {
/*      */       return;
/*      */     }
/*  317 */     if (BlockUtils.isInLiquid() || BlockUtils.isOnLiquid() || mc.thePlayer
/*  318 */       .isInWater() || mc.thePlayer.isInWeb || mc.thePlayer.isSneaking() || 
/*  319 */       BlockUtils.isInsideBlock()) {
/*      */       return;
/*      */     }
/*  322 */     if (this.mode.is("Watchdog")) {
/*  323 */       if (BlockUtils.isInLiquid() || BlockUtils.isOnLiquid() || mc.thePlayer
/*  324 */         .isInWater() || mc.thePlayer.isInWeb || mc.thePlayer.isSneaking()) {
/*      */         return;
/*      */       }
/*  327 */       if (mc.thePlayer.onGround && isMoving()) {
/*  328 */         mc.thePlayer.jump();
/*  329 */         MoveUtils.INSTANCE.setSpeed(0.46D + getSpeedPotion() * 0.02D);
/*      */       } 
/*  331 */     } else if (this.mode.is("LowJump") || this.mode.is("LowJump2") || this.mode.is("LowJumpFast")) {
/*      */       try {
/*  333 */         if (BlockUtils.isInLiquid() || BlockUtils.isOnLiquid() || mc.thePlayer
/*  334 */           .isInWater() || mc.thePlayer.isInWeb || mc.thePlayer.isSneaking()) {
/*      */           return;
/*      */         }
/*  337 */         if (isMoving()) {
/*  338 */           if (MoveUtils.INSTANCE.isOnGround(0.01D) && mc.thePlayer.isCollidedVertically) {
/*  339 */             mc.thePlayer.jump();
/*  340 */             if (this.mode.is("LowJump")) {
/*  341 */               mc.thePlayer.motionY = 0.4099999964237213D;
/*  342 */             } else if (this.mode.is("LowJumpFast")) {
/*  343 */               mc.thePlayer.motionY = 0.36899998784065247D;
/*  344 */             } else if (this.mode.is("LowJump2")) {
/*  345 */               mc.thePlayer.motionY = 0.4000000059604645D;
/*      */             } 
/*  347 */           } else if (!mc.thePlayer.onGround) {
/*  348 */             if (this.lowJumpStrafe.is("Angle")) {
/*  349 */               strafe();
/*  350 */             } else if (this.lowJumpStrafe.is("Full")) {
/*  351 */               setMotion(Math.max(getSpeed(), MoveUtils.INSTANCE.getBaseMoveSpeed()));
/*      */             } 
/*      */           } 
/*      */         }
/*  355 */       } catch (RuntimeException runtimeException) {
/*  356 */         runtimeException.printStackTrace();
/*      */       } 
/*  358 */     } else if (this.mode.is("Matrix")) {
/*  359 */       if (!isMoving()) {
/*      */         return;
/*      */       }
/*  362 */       if (BlockUtils.isInLiquid() || BlockUtils.isOnLiquid() || mc.thePlayer
/*  363 */         .isInWater() || mc.thePlayer.isInWeb || mc.thePlayer.isSneaking() || 
/*  364 */         BlockUtils.isInsideBlock()) {
/*  365 */         mc.timer.timerSpeed = 1.0F;
/*      */         
/*      */         return;
/*      */       } 
/*  369 */       mc.timer.timerSpeed = 1.0F;
/*  370 */       mc.thePlayer.motionY -= 0.00348D;
/*  371 */       mc.thePlayer.jumpMovementFactor = 0.026F;
/*  372 */       mc.gameSettings.keyBindJump.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindJump);
/*  373 */       if (mc.thePlayer.onGround) {
/*  374 */         mc.gameSettings.keyBindJump.pressed = false;
/*  375 */         mc.timer.timerSpeed = 1.35F;
/*  376 */         mc.thePlayer.jump();
/*  377 */         MoveUtils.INSTANCE.strafe();
/*  378 */       } else if (MoveUtils.INSTANCE.getSpeed() < 0.215D) {
/*  379 */         MoveUtils.INSTANCE.strafe(0.215F);
/*      */       } 
/*  381 */     } else if (this.mode.is("HmXix")) {
/*  382 */       if (!isMoving()) {
/*      */         return;
/*      */       }
/*  385 */       if (BlockUtils.isInLiquid() || BlockUtils.isOnLiquid() || mc.thePlayer
/*  386 */         .isInWater() || mc.thePlayer.isInWeb || mc.thePlayer.isSneaking() || 
/*  387 */         BlockUtils.isInsideBlock()) {
/*  388 */         mc.timer.timerSpeed = 1.0F;
/*      */         
/*      */         return;
/*      */       } 
/*  392 */       mc.thePlayer.motionY -= 0.00348D;
/*  393 */       mc.thePlayer.jumpMovementFactor = 0.026F;
/*  394 */       mc.thePlayer.speedInAir += 0.001F;
/*  395 */       mc.gameSettings.keyBindJump.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindJump);
/*  396 */       if (mc.thePlayer.hurtResistantTime > 0 && 
/*  397 */         mc.thePlayer.motionY > 0.003D) {
/*  398 */         mc.thePlayer.motionX *= 1.0012D;
/*  399 */         mc.thePlayer.motionZ *= 1.0012D;
/*      */       } 
/*      */       
/*  402 */       if (isMoving() && (
/*  403 */         mc.thePlayer.onGround || mc.thePlayer.fallDistance >= 0.6D)) {
/*  404 */         MoveUtils.INSTANCE.strafe();
/*      */       }
/*      */       
/*  407 */       if (isMoving() && mc.thePlayer.onGround) {
/*  408 */         mc.gameSettings.keyBindJump.pressed = false;
/*  409 */         mc.thePlayer.jump();
/*  410 */       } else if (MoveUtils.INSTANCE.getSpeed() <= 0.22D) {
/*  411 */         MoveUtils.INSTANCE.strafe(0.22F);
/*      */       } 
/*  413 */     } else if (this.mode.is("OldMatrix")) {
/*  414 */       if (isMoving()) {
/*  415 */         if (BlockUtils.isInLiquid() || BlockUtils.isOnLiquid() || mc.thePlayer
/*  416 */           .isInWater() || mc.thePlayer.isInWeb || mc.thePlayer.isSneaking() || 
/*  417 */           BlockUtils.isInsideBlock()) {
/*  418 */           mc.timer.timerSpeed = 1.0F;
/*      */           return;
/*      */         } 
/*  421 */         if (mc.thePlayer.fallDistance <= 1.0F) {
/*  422 */           if (mc.thePlayer.onGround) {
/*  423 */             mc.thePlayer.jump();
/*      */           }
/*  425 */           mc.gameSettings.keyBindJump.pressed = false;
/*      */           
/*  427 */           if (mc.thePlayer.ticksExisted % 5 == 0) {
/*  428 */             mc.thePlayer.jumpMovementFactor = (float)(mc.thePlayer.jumpMovementFactor + 2.0E-4D);
/*      */           }
/*  430 */           if (mc.thePlayer.ticksExisted % 10 == 0) {
/*  431 */             mc.thePlayer.motionY = -0.04D - 0.032D * Math.random();
/*      */           }
/*  433 */           mc.thePlayer.motionX *= 1.0D;
/*  434 */           mc.thePlayer.motionZ *= 1.0D;
/*      */         } 
/*      */       } 
/*  437 */     } else if (this.mode.is("GroundStrafeHop")) {
/*  438 */       if (BlockUtils.isInLiquid() || BlockUtils.isOnLiquid() || mc.thePlayer
/*  439 */         .isInWater() || mc.thePlayer.isInWeb || BlockUtils.isInsideBlock()) {
/*      */         return;
/*      */       }
/*  442 */       if (isMoving()) {
/*  443 */         if (mc.thePlayer.onGround) {
/*  444 */           mc.thePlayer.jump();
/*  445 */           MoveUtils.INSTANCE.strafe();
/*      */         } 
/*      */       } else {
/*  448 */         mc.thePlayer.motionX = 0.0D;
/*  449 */         mc.thePlayer.motionZ = 0.0D;
/*      */       } 
/*  451 */     } else if (this.mode.is("Jump")) {
/*  452 */       if (BlockUtils.isInLiquid() || BlockUtils.isOnLiquid() || mc.thePlayer
/*  453 */         .isInWater() || mc.thePlayer.isInWeb || BlockUtils.isInsideBlock()) {
/*      */         return;
/*      */       }
/*  456 */       if (isMoving()) {
/*  457 */         if (MoveUtils.INSTANCE.isOnGround(0.01D) && mc.thePlayer.isCollidedVertically) {
/*  458 */           mc.thePlayer.jump();
/*  459 */         } else if (!mc.thePlayer.onGround && 
/*  460 */           !((Boolean)this.jumpModeNoStrafe.get()).booleanValue()) {
/*  461 */           MoveUtils.INSTANCE.setSpeed(MoveUtils.INSTANCE.getSpeed());
/*      */         }
/*      */       
/*      */       }
/*  465 */     } else if (this.mode.is("AACv5")) {
/*      */       
/*  467 */       if (!isMoving()) {
/*      */         return;
/*      */       }
/*  470 */       if (mc.thePlayer.onGround) {
/*  471 */         mc.thePlayer.jump();
/*  472 */         mc.thePlayer.speedInAir = 0.0201F;
/*  473 */         mc.timer.timerSpeed = 0.94F;
/*      */       } 
/*  475 */       if (mc.thePlayer.fallDistance > 0.7D && mc.thePlayer.fallDistance < 1.3D) {
/*  476 */         mc.thePlayer.speedInAir = 0.02F;
/*  477 */         mc.timer.timerSpeed = 1.8F;
/*      */       } 
/*  479 */     } else if (this.mode.is("AAC3.3.13")) {
/*      */       
/*  481 */       if (isMoving()) {
/*  482 */         if (mc.thePlayer.hurtTime <= 0) {
/*  483 */           if (mc.thePlayer.onGround) {
/*  484 */             this.waitForGround = false;
/*      */             
/*  486 */             if (!this.firstJump) {
/*  487 */               this.firstJump = true;
/*      */             }
/*  489 */             mc.thePlayer.jump();
/*  490 */             mc.thePlayer.motionY = 0.41D;
/*      */           } else {
/*  492 */             if (this.waitForGround) {
/*      */               return;
/*      */             }
/*  495 */             if (mc.thePlayer.isCollidedHorizontally) {
/*      */               return;
/*      */             }
/*  498 */             this.firstJump = false;
/*  499 */             mc.thePlayer.motionY -= 0.0149D;
/*      */           } 
/*      */           
/*  502 */           if (!mc.thePlayer.isCollidedHorizontally)
/*  503 */             MoveUtils.INSTANCE.forward(this.firstJump ? 0.0016D : 0.001799D); 
/*      */         } else {
/*  505 */           this.firstJump = true;
/*  506 */           this.waitForGround = true;
/*      */         } 
/*      */       } else {
/*  509 */         mc.thePlayer.motionZ = 0.0D;
/*  510 */         mc.thePlayer.motionX = 0.0D;
/*      */       } 
/*      */       
/*  513 */       double speed = MoveUtils.INSTANCE.getSpeed();
/*  514 */       mc.thePlayer.motionX = -(Math.sin(MoveUtils.INSTANCE.getDirection()) * speed);
/*  515 */       mc.thePlayer.motionZ = Math.cos(MoveUtils.INSTANCE.getDirection()) * speed;
/*  516 */     } else if (this.mode.is("AACv4")) {
/*      */       
/*  518 */       if (!isMoving()) {
/*      */         return;
/*      */       }
/*  521 */       if (mc.thePlayer.onGround) {
/*  522 */         mc.thePlayer.jump();
/*  523 */         mc.thePlayer.speedInAir = 0.02F;
/*  524 */         mc.timer.timerSpeed = 1.0F;
/*      */       } 
/*  526 */       if (mc.thePlayer.fallDistance > 0.7D && mc.thePlayer.fallDistance < 1.3D) {
/*  527 */         mc.thePlayer.speedInAir = 0.02F;
/*  528 */         mc.timer.timerSpeed = 1.08F;
/*      */       } 
/*  530 */     } else if (this.mode.is("SpartanBhop")) {
/*  531 */       if (BlockUtils.isInLiquid() || BlockUtils.isOnLiquid() || mc.thePlayer
/*  532 */         .isInWater() || mc.thePlayer.isInWeb || mc.thePlayer.isSneaking() || 
/*  533 */         BlockUtils.isInsideBlock()) {
/*  534 */         mc.timer.timerSpeed = 1.0F;
/*      */         return;
/*      */       } 
/*  537 */       strafe();
/*  538 */       if (mc.thePlayer.fallDistance < 1.0F) {
/*  539 */         if (mc.thePlayer.moving()) {
/*  540 */           mc.timer.timerSpeed = 1.1F;
/*      */         }
/*  542 */         if (mc.gameSettings.keyBindForward.isKeyDown() && !mc.gameSettings.keyBindJump.isKeyDown()) {
/*  543 */           if (mc.thePlayer.onGround) {
/*  544 */             mc.thePlayer.jump();
/*  545 */             this.airMoves = 0;
/*      */           } else {
/*  547 */             if (this.airMoves >= 3) {
/*  548 */               mc.thePlayer.jumpMovementFactor = 0.0275F;
/*      */             }
/*  550 */             if (this.airMoves >= 4 && (this.airMoves % 2) == 0.0D) {
/*  551 */               mc.thePlayer.motionY = -0.3199999928474426D - 0.009D * Math.random();
/*  552 */               mc.thePlayer.jumpMovementFactor = 0.0238F;
/*      */             } 
/*  554 */             this.airMoves++;
/*      */           } 
/*      */         }
/*      */       } 
/*  558 */     } else if (this.mode.is("NCPBhop")) {
/*  559 */       if (mc.thePlayer.ticksExisted <= 40) {
/*      */         return;
/*      */       }
/*  562 */       double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
/*  563 */       double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
/*  564 */       this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
/*  565 */     } else if (this.mode.is("MineMenClub")) {
/*      */       
/*  567 */       if (mc.thePlayer.onGround && isMoving()) {
/*  568 */         mc.thePlayer.jump();
/*      */       }
/*  570 */     } else if (this.mode.is("KoksCraft")) {
/*  571 */       if (mc.thePlayer.onGround) {
/*  572 */         if (mc.thePlayer.hurtTime == 0) {
/*  573 */           MoveUtils.INSTANCE.strafe(MoveUtils.INSTANCE.getAllowedHorizontalDistance() * 0.99D);
/*      */         }
/*  575 */         mc.thePlayer.jump();
/*      */         
/*  577 */         this.jumps++;
/*      */       } 
/*      */       
/*  580 */       if (mc.thePlayer.offGroundTicks == 1 && mc.thePlayer.hurtTime == 0) {
/*  581 */         mc.thePlayer.motionY = MoveUtils.INSTANCE.predictedMotion(mc.thePlayer.motionY, (this.jumps % 2 == 0) ? 2 : 4);
/*      */       }
/*      */     }
/*  584 */     else if (this.mode.is("BlocksMC")) {
/*  585 */       if (!isMoving()) {
/*  586 */         event.setPosX(event.getPosX() + (Math.random() - 0.5D) / 100.0D);
/*  587 */         event.setPosZ(event.getPosZ() + (Math.random() - 0.5D) / 100.0D);
/*      */       } 
/*  589 */       sendNoEvent((Packet)new C0BPacketEntityAction((Entity)mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @EventHandler
/*      */   private void onWorldChange(EventWorldChanged e) {
/*  596 */     if (((Boolean)this.autodisable.get()).booleanValue()) {
/*  597 */       ClientNotification.sendClientMessage(getHUDName(), "Auto disable", 3000L, ClientNotification.Type.WARNING);
/*      */       
/*  599 */       setEnabled(false);
/*      */     } 
/*      */   }
/*      */   
/*      */   @EventHandler
/*      */   private void onUpdate(EventPreUpdate e) {
/*  605 */     setSuffix((Serializable)this.mode.get());
/*      */     
/*  607 */     if (((Boolean)this.jumpnobob.get()).booleanValue()) {
/*  608 */       mc.thePlayer.cameraYaw = 0.0F;
/*      */     }
/*  610 */     if (((Boolean)this.spoofY.get()).booleanValue() && !mc.gameSettings.keyBindJump.isKeyDown() && isMoving() && !mc.thePlayer.onGround) {
/*  611 */       mc.thePlayer.posY -= mc.thePlayer.posY - mc.thePlayer.lastTickPosY;
/*  612 */       mc.thePlayer.lastTickPosY -= mc.thePlayer.posY - mc.thePlayer.lastTickPosY;
/*  613 */       mc.thePlayer.cameraYaw = mc.thePlayer.cameraPitch = 0.1F;
/*      */     } 
/*  615 */     if (Scaffold.getInstance.isEnabled() && ((Boolean)Scaffold.getInstance.StopSpeed
/*  616 */       .get()).booleanValue()) {
/*      */       return;
/*      */     }
/*  619 */     if (((Boolean)this.timerboost.get()).booleanValue() && 
/*  620 */       !GameSpeed.getInstance.isEnabled()) {
/*  621 */       if (this.xDist > 200.0D) {
/*  622 */         this.xDist = 0.0D;
/*      */       }
/*  624 */       if (MoveUtils.INSTANCE.isMovingKeyBindingActive() && this.xDist++ > ((Double)this.timertick.get()).intValue() && MoveUtils.INSTANCE.isMovingKeyBindingActive() && ((Double)this.timermax.get()).floatValue() > ((Double)this.timermin.get()).floatValue()) {
/*  625 */         mc.timer.timerSpeed = (float)((((Double)this.timermax.get()).floatValue() > ((Double)this.timermin.get()).floatValue()) ? ThreadLocalRandom.current().nextDouble(((Double)this.timermin.get()).floatValue(), ((Double)this.timermax.get()).floatValue()) : (((Double)this.timermin.get()).floatValue() - Math.random() / 10.0D));
/*      */       } else {
/*  627 */         mc.timer.timerSpeed = 1.0F;
/*      */       } 
/*  629 */       if (mc.thePlayer.fallDistance > 3.0D) {
/*  630 */         mc.timer.timerSpeed = 1.0F;
/*      */       }
/*      */     } 
/*      */     
/*  634 */     if (this.mode.is("LowHop")) {
/*  635 */       mc.thePlayer.cameraPitch = 0.0F;
/*  636 */       mc.thePlayer.cameraYaw = 0.0F;
/*  637 */     } else if (this.mode.is("Bhop")) {
/*      */       
/*  639 */       this; this; double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
/*  640 */       this; this; double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
/*  641 */       this.distance = Math.sqrt(xDist * xDist + zDist * zDist);
/*      */     } 
/*      */   }
/*      */   
/*      */   public double getSpeed() {
/*  646 */     double x = mc.thePlayer.motionX;
/*  647 */     double z = mc.thePlayer.motionZ;
/*  648 */     return Math.sqrt(x * mc.thePlayer.motionX + z * mc.thePlayer.motionZ) * (((Boolean)this.dmgboost.get()).booleanValue() ? ((mc.thePlayer.hurtResistantTime > 0) ? (1 + ((Double)this.dmgzoom.get()).intValue()) : true) : true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @EventHandler
/*      */   public void onMove(EventMove e) {
/*      */     // Byte code:
/*      */     //   0: getstatic awareline/main/mod/implement/world/Scaffold.getInstance : Lawareline/main/mod/implement/world/Scaffold;
/*      */     //   3: invokevirtual isEnabled : ()Z
/*      */     //   6: ifeq -> 28
/*      */     //   9: getstatic awareline/main/mod/implement/world/Scaffold.getInstance : Lawareline/main/mod/implement/world/Scaffold;
/*      */     //   12: getfield StopSpeed : Lawareline/main/mod/values/Option;
/*      */     //   15: invokevirtual get : ()Ljava/lang/Object;
/*      */     //   18: checkcast java/lang/Boolean
/*      */     //   21: invokevirtual booleanValue : ()Z
/*      */     //   24: ifeq -> 28
/*      */     //   27: return
/*      */     //   28: getstatic awareline/main/mod/implement/move/WaterWalk.getInstance : Lawareline/main/mod/implement/move/WaterWalk;
/*      */     //   31: invokevirtual isEnabled : ()Z
/*      */     //   34: ifeq -> 74
/*      */     //   37: getstatic awareline/main/mod/implement/move/WaterWalk.getInstance : Lawareline/main/mod/implement/move/WaterWalk;
/*      */     //   40: getfield stopSpeed : Lawareline/main/mod/values/Option;
/*      */     //   43: invokevirtual get : ()Ljava/lang/Object;
/*      */     //   46: checkcast java/lang/Boolean
/*      */     //   49: invokevirtual booleanValue : ()Z
/*      */     //   52: ifeq -> 74
/*      */     //   55: getstatic awareline/main/mod/implement/move/WaterWalk.getInstance : Lawareline/main/mod/implement/move/WaterWalk;
/*      */     //   58: invokevirtual shouldJesus : ()Z
/*      */     //   61: ifne -> 73
/*      */     //   64: getstatic awareline/main/mod/implement/move/WaterWalk.getInstance : Lawareline/main/mod/implement/move/WaterWalk;
/*      */     //   67: invokevirtual canJeboos : ()Z
/*      */     //   70: ifeq -> 74
/*      */     //   73: return
/*      */     //   74: aload_0
/*      */     //   75: getfield mode : Lawareline/main/mod/values/Mode;
/*      */     //   78: ldc 'Matrix'
/*      */     //   80: invokevirtual is : (Ljava/lang/String;)Z
/*      */     //   83: ifne -> 134
/*      */     //   86: aload_0
/*      */     //   87: getfield mode : Lawareline/main/mod/values/Mode;
/*      */     //   90: ldc 'OldMatrix'
/*      */     //   92: invokevirtual is : (Ljava/lang/String;)Z
/*      */     //   95: ifne -> 134
/*      */     //   98: aload_0
/*      */     //   99: getfield mode : Lawareline/main/mod/values/Mode;
/*      */     //   102: ldc 'HmXix'
/*      */     //   104: invokevirtual is : (Ljava/lang/String;)Z
/*      */     //   107: ifne -> 134
/*      */     //   110: aload_0
/*      */     //   111: getfield mode : Lawareline/main/mod/values/Mode;
/*      */     //   114: ldc 'NCP'
/*      */     //   116: invokevirtual is : (Ljava/lang/String;)Z
/*      */     //   119: ifne -> 134
/*      */     //   122: aload_0
/*      */     //   123: getfield mode : Lawareline/main/mod/values/Mode;
/*      */     //   126: ldc 'GroundStrafeHop'
/*      */     //   128: invokevirtual is : (Ljava/lang/String;)Z
/*      */     //   131: ifeq -> 167
/*      */     //   134: aload_0
/*      */     //   135: invokevirtual isMoving : ()Z
/*      */     //   138: ifeq -> 167
/*      */     //   141: getstatic awareline/main/mod/implement/combat/TargetStrafe.getInstance : Lawareline/main/mod/implement/combat/TargetStrafe;
/*      */     //   144: invokevirtual isEnabled : ()Z
/*      */     //   147: ifeq -> 167
/*      */     //   150: getstatic awareline/main/mod/implement/combat/KillAura.getInstance : Lawareline/main/mod/implement/combat/KillAura;
/*      */     //   153: invokevirtual getTarget : ()Lnet/minecraft/entity/EntityLivingBase;
/*      */     //   156: ifnull -> 167
/*      */     //   159: aload_1
/*      */     //   160: aload_0
/*      */     //   161: invokevirtual getSpeed : ()D
/*      */     //   164: invokevirtual setMoveSpeed : (D)V
/*      */     //   167: aload_0
/*      */     //   168: getfield mode : Lawareline/main/mod/values/Mode;
/*      */     //   171: invokevirtual get : ()Ljava/lang/Object;
/*      */     //   174: checkcast java/lang/String
/*      */     //   177: astore_2
/*      */     //   178: iconst_m1
/*      */     //   179: istore_3
/*      */     //   180: aload_2
/*      */     //   181: invokevirtual hashCode : ()I
/*      */     //   184: lookupswitch default -> 303, -1777903888 -> 250, -930943079 -> 292, 2320462 -> 278, 2659582 -> 264, 2020858242 -> 236
/*      */     //   236: aload_2
/*      */     //   237: ldc 'LowJump'
/*      */     //   239: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   242: ifeq -> 303
/*      */     //   245: iconst_0
/*      */     //   246: istore_3
/*      */     //   247: goto -> 303
/*      */     //   250: aload_2
/*      */     //   251: ldc 'LowJump2'
/*      */     //   253: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   256: ifeq -> 303
/*      */     //   259: iconst_1
/*      */     //   260: istore_3
/*      */     //   261: goto -> 303
/*      */     //   264: aload_2
/*      */     //   265: ldc 'LowJumpFast'
/*      */     //   267: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   270: ifeq -> 303
/*      */     //   273: iconst_2
/*      */     //   274: istore_3
/*      */     //   275: goto -> 303
/*      */     //   278: aload_2
/*      */     //   279: ldc 'Jump'
/*      */     //   281: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   284: ifeq -> 303
/*      */     //   287: iconst_3
/*      */     //   288: istore_3
/*      */     //   289: goto -> 303
/*      */     //   292: aload_2
/*      */     //   293: ldc 'VerusYport'
/*      */     //   295: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   298: ifeq -> 303
/*      */     //   301: iconst_4
/*      */     //   302: istore_3
/*      */     //   303: iload_3
/*      */     //   304: tableswitch default -> 531, 0 -> 340, 1 -> 340, 2 -> 340, 3 -> 340, 4 -> 387
/*      */     //   340: aload_0
/*      */     //   341: invokevirtual isMoving : ()Z
/*      */     //   344: ifeq -> 531
/*      */     //   347: aload_0
/*      */     //   348: invokevirtual getSpeed : ()D
/*      */     //   351: dstore #4
/*      */     //   353: aload_0
/*      */     //   354: invokevirtual isMoving : ()Z
/*      */     //   357: ifeq -> 384
/*      */     //   360: getstatic awareline/main/mod/implement/combat/TargetStrafe.getInstance : Lawareline/main/mod/implement/combat/TargetStrafe;
/*      */     //   363: invokevirtual isEnabled : ()Z
/*      */     //   366: ifeq -> 384
/*      */     //   369: getstatic awareline/main/mod/implement/combat/KillAura.getInstance : Lawareline/main/mod/implement/combat/KillAura;
/*      */     //   372: invokevirtual getTarget : ()Lnet/minecraft/entity/EntityLivingBase;
/*      */     //   375: ifnull -> 384
/*      */     //   378: aload_1
/*      */     //   379: dload #4
/*      */     //   381: invokevirtual setMoveSpeed : (D)V
/*      */     //   384: goto -> 531
/*      */     //   387: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   390: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   393: invokevirtual isSneaking : ()Z
/*      */     //   396: ifeq -> 400
/*      */     //   399: return
/*      */     //   400: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   403: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   406: getfield isInWeb : Z
/*      */     //   409: ifne -> 531
/*      */     //   412: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   415: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   418: invokevirtual isInLava : ()Z
/*      */     //   421: ifne -> 531
/*      */     //   424: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   427: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   430: invokevirtual isInWater : ()Z
/*      */     //   433: ifne -> 531
/*      */     //   436: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   439: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   442: invokevirtual isOnLadder : ()Z
/*      */     //   445: ifne -> 531
/*      */     //   448: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   451: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   454: getfield ridingEntity : Lnet/minecraft/entity/Entity;
/*      */     //   457: ifnonnull -> 531
/*      */     //   460: aload_0
/*      */     //   461: invokevirtual isMoving : ()Z
/*      */     //   464: ifeq -> 531
/*      */     //   467: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   470: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   473: getfield keyBindJump : Lnet/minecraft/client/settings/KeyBinding;
/*      */     //   476: iconst_0
/*      */     //   477: putfield pressed : Z
/*      */     //   480: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   483: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   486: getfield onGround : Z
/*      */     //   489: ifeq -> 527
/*      */     //   492: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   495: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   498: invokevirtual jump : ()V
/*      */     //   501: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   504: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   507: dconst_0
/*      */     //   508: putfield motionY : D
/*      */     //   511: getstatic awareline/main/utility/MoveUtils.INSTANCE : Lawareline/main/utility/MoveUtils;
/*      */     //   514: ldc_w 0.61
/*      */     //   517: invokevirtual strafe : (F)V
/*      */     //   520: aload_1
/*      */     //   521: ldc2_w 0.41999998688698
/*      */     //   524: invokevirtual setY : (D)V
/*      */     //   527: aload_0
/*      */     //   528: invokevirtual strafe : ()V
/*      */     //   531: aload_0
/*      */     //   532: getfield mode : Lawareline/main/mod/values/Mode;
/*      */     //   535: ldc 'NCPBhop'
/*      */     //   537: invokevirtual is : (Ljava/lang/String;)Z
/*      */     //   540: ifeq -> 1310
/*      */     //   543: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   546: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   549: getfield ticksExisted : I
/*      */     //   552: bipush #40
/*      */     //   554: if_icmpgt -> 558
/*      */     //   557: return
/*      */     //   558: aload_0
/*      */     //   559: dup
/*      */     //   560: getfield timerDelay : I
/*      */     //   563: iconst_1
/*      */     //   564: iadd
/*      */     //   565: putfield timerDelay : I
/*      */     //   568: aload_0
/*      */     //   569: dup
/*      */     //   570: getfield timerDelay : I
/*      */     //   573: iconst_5
/*      */     //   574: irem
/*      */     //   575: putfield timerDelay : I
/*      */     //   578: aload_0
/*      */     //   579: getfield timerDelay : I
/*      */     //   582: ifeq -> 598
/*      */     //   585: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   588: getfield timer : Lnet/minecraft/util/Timer;
/*      */     //   591: fconst_1
/*      */     //   592: putfield timerSpeed : F
/*      */     //   595: goto -> 670
/*      */     //   598: aload_0
/*      */     //   599: invokevirtual isMoving : ()Z
/*      */     //   602: ifeq -> 617
/*      */     //   605: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   608: getfield timer : Lnet/minecraft/util/Timer;
/*      */     //   611: ldc_w 32767.0
/*      */     //   614: putfield timerSpeed : F
/*      */     //   617: aload_0
/*      */     //   618: invokevirtual isMoving : ()Z
/*      */     //   621: ifeq -> 670
/*      */     //   624: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   627: getfield timer : Lnet/minecraft/util/Timer;
/*      */     //   630: ldc_w 1.3
/*      */     //   633: putfield timerSpeed : F
/*      */     //   636: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   639: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   642: dup
/*      */     //   643: getfield motionX : D
/*      */     //   646: ldc2_w 1.0199999809265137
/*      */     //   649: dmul
/*      */     //   650: putfield motionX : D
/*      */     //   653: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   656: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   659: dup
/*      */     //   660: getfield motionZ : D
/*      */     //   663: ldc2_w 1.0199999809265137
/*      */     //   666: dmul
/*      */     //   667: putfield motionZ : D
/*      */     //   670: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   673: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   676: getfield onGround : Z
/*      */     //   679: ifeq -> 694
/*      */     //   682: aload_0
/*      */     //   683: invokevirtual isMoving : ()Z
/*      */     //   686: ifeq -> 694
/*      */     //   689: aload_0
/*      */     //   690: iconst_2
/*      */     //   691: putfield level : I
/*      */     //   694: aload_0
/*      */     //   695: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   698: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   701: getfield posY : D
/*      */     //   704: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   707: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   710: getfield posY : D
/*      */     //   713: d2i
/*      */     //   714: i2d
/*      */     //   715: dsub
/*      */     //   716: invokespecial round : (D)D
/*      */     //   719: aload_0
/*      */     //   720: ldc2_w 0.138
/*      */     //   723: invokespecial round : (D)D
/*      */     //   726: dcmpl
/*      */     //   727: ifne -> 773
/*      */     //   730: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   733: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   736: astore_2
/*      */     //   737: aload_2
/*      */     //   738: dup
/*      */     //   739: getfield motionY : D
/*      */     //   742: ldc2_w 0.08
/*      */     //   745: dsub
/*      */     //   746: putfield motionY : D
/*      */     //   749: aload_1
/*      */     //   750: aload_1
/*      */     //   751: invokevirtual getY : ()D
/*      */     //   754: ldc2_w 0.09316090325960147
/*      */     //   757: dsub
/*      */     //   758: invokevirtual setY : (D)V
/*      */     //   761: aload_2
/*      */     //   762: dup
/*      */     //   763: getfield posY : D
/*      */     //   766: ldc2_w 0.09316090325960147
/*      */     //   769: dsub
/*      */     //   770: putfield posY : D
/*      */     //   773: aload_0
/*      */     //   774: getfield level : I
/*      */     //   777: iconst_1
/*      */     //   778: if_icmpne -> 835
/*      */     //   781: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   784: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   787: getfield moveForward : F
/*      */     //   790: fconst_0
/*      */     //   791: fcmpl
/*      */     //   792: ifne -> 809
/*      */     //   795: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   798: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   801: getfield moveStrafing : F
/*      */     //   804: fconst_0
/*      */     //   805: fcmpl
/*      */     //   806: ifeq -> 835
/*      */     //   809: aload_0
/*      */     //   810: iconst_2
/*      */     //   811: putfield level : I
/*      */     //   814: aload_0
/*      */     //   815: ldc2_w 1.35
/*      */     //   818: getstatic awareline/main/utility/MoveUtils.INSTANCE : Lawareline/main/utility/MoveUtils;
/*      */     //   821: invokevirtual getBaseMoveSpeed : ()D
/*      */     //   824: dmul
/*      */     //   825: ldc2_w 0.01
/*      */     //   828: dsub
/*      */     //   829: putfield moveSpeed : D
/*      */     //   832: goto -> 1004
/*      */     //   835: aload_0
/*      */     //   836: getfield level : I
/*      */     //   839: iconst_2
/*      */     //   840: if_icmpne -> 882
/*      */     //   843: aload_0
/*      */     //   844: iconst_3
/*      */     //   845: putfield level : I
/*      */     //   848: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   851: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   854: ldc2_w 0.399399995803833
/*      */     //   857: putfield motionY : D
/*      */     //   860: aload_1
/*      */     //   861: ldc2_w 0.399399995803833
/*      */     //   864: invokevirtual setY : (D)V
/*      */     //   867: aload_0
/*      */     //   868: dup
/*      */     //   869: getfield moveSpeed : D
/*      */     //   872: ldc2_w 2.149
/*      */     //   875: dmul
/*      */     //   876: putfield moveSpeed : D
/*      */     //   879: goto -> 1004
/*      */     //   882: aload_0
/*      */     //   883: getfield level : I
/*      */     //   886: iconst_3
/*      */     //   887: if_icmpne -> 924
/*      */     //   890: aload_0
/*      */     //   891: iconst_4
/*      */     //   892: putfield level : I
/*      */     //   895: ldc2_w 0.66
/*      */     //   898: aload_0
/*      */     //   899: getfield lastDist : D
/*      */     //   902: getstatic awareline/main/utility/MoveUtils.INSTANCE : Lawareline/main/utility/MoveUtils;
/*      */     //   905: invokevirtual getBaseMoveSpeed : ()D
/*      */     //   908: dsub
/*      */     //   909: dmul
/*      */     //   910: dstore_2
/*      */     //   911: aload_0
/*      */     //   912: aload_0
/*      */     //   913: getfield lastDist : D
/*      */     //   916: dload_2
/*      */     //   917: dsub
/*      */     //   918: putfield moveSpeed : D
/*      */     //   921: goto -> 1004
/*      */     //   924: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   927: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   930: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   933: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   936: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   939: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   942: invokevirtual getEntityBoundingBox : ()Lnet/minecraft/util/AxisAlignedBB;
/*      */     //   945: dconst_0
/*      */     //   946: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   949: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   952: getfield motionY : D
/*      */     //   955: dconst_0
/*      */     //   956: invokevirtual offset : (DDD)Lnet/minecraft/util/AxisAlignedBB;
/*      */     //   959: invokevirtual getCollidingBoundingBoxes : (Lnet/minecraft/entity/Entity;Lnet/minecraft/util/AxisAlignedBB;)Ljava/util/List;
/*      */     //   962: invokeinterface isEmpty : ()Z
/*      */     //   967: ifeq -> 982
/*      */     //   970: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   973: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   976: getfield isCollidedVertically : Z
/*      */     //   979: ifeq -> 987
/*      */     //   982: aload_0
/*      */     //   983: iconst_1
/*      */     //   984: putfield level : I
/*      */     //   987: aload_0
/*      */     //   988: aload_0
/*      */     //   989: getfield lastDist : D
/*      */     //   992: aload_0
/*      */     //   993: getfield lastDist : D
/*      */     //   996: ldc2_w 159.0
/*      */     //   999: ddiv
/*      */     //   1000: dsub
/*      */     //   1001: putfield moveSpeed : D
/*      */     //   1004: aload_0
/*      */     //   1005: aload_0
/*      */     //   1006: getfield moveSpeed : D
/*      */     //   1009: getstatic awareline/main/utility/MoveUtils.INSTANCE : Lawareline/main/utility/MoveUtils;
/*      */     //   1012: invokevirtual getBaseMoveSpeed : ()D
/*      */     //   1015: invokestatic max : (DD)D
/*      */     //   1018: putfield moveSpeed : D
/*      */     //   1021: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1024: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1027: getfield movementInput : Lnet/minecraft/util/MovementInput;
/*      */     //   1030: getfield moveForward : F
/*      */     //   1033: fstore_2
/*      */     //   1034: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1037: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1040: getfield movementInput : Lnet/minecraft/util/MovementInput;
/*      */     //   1043: getfield moveStrafe : F
/*      */     //   1046: fstore_3
/*      */     //   1047: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1050: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1053: getfield rotationYaw : F
/*      */     //   1056: fstore #4
/*      */     //   1058: fload_2
/*      */     //   1059: fconst_0
/*      */     //   1060: fcmpl
/*      */     //   1061: ifne -> 1083
/*      */     //   1064: fload_3
/*      */     //   1065: fconst_0
/*      */     //   1066: fcmpl
/*      */     //   1067: ifne -> 1083
/*      */     //   1070: aload_1
/*      */     //   1071: dconst_0
/*      */     //   1072: invokevirtual setX : (D)V
/*      */     //   1075: aload_1
/*      */     //   1076: dconst_0
/*      */     //   1077: invokevirtual setZ : (D)V
/*      */     //   1080: goto -> 1169
/*      */     //   1083: fload_2
/*      */     //   1084: fconst_0
/*      */     //   1085: fcmpl
/*      */     //   1086: ifeq -> 1169
/*      */     //   1089: fload_3
/*      */     //   1090: fconst_1
/*      */     //   1091: fcmpl
/*      */     //   1092: iflt -> 1119
/*      */     //   1095: fload #4
/*      */     //   1097: fload_2
/*      */     //   1098: fconst_0
/*      */     //   1099: fcmpl
/*      */     //   1100: ifle -> 1108
/*      */     //   1103: bipush #-45
/*      */     //   1105: goto -> 1110
/*      */     //   1108: bipush #45
/*      */     //   1110: i2f
/*      */     //   1111: fadd
/*      */     //   1112: fstore #4
/*      */     //   1114: fconst_0
/*      */     //   1115: fstore_3
/*      */     //   1116: goto -> 1148
/*      */     //   1119: fload_3
/*      */     //   1120: ldc_w -1.0
/*      */     //   1123: fcmpg
/*      */     //   1124: ifgt -> 1148
/*      */     //   1127: fload #4
/*      */     //   1129: fload_2
/*      */     //   1130: fconst_0
/*      */     //   1131: fcmpl
/*      */     //   1132: ifle -> 1140
/*      */     //   1135: bipush #45
/*      */     //   1137: goto -> 1142
/*      */     //   1140: bipush #-45
/*      */     //   1142: i2f
/*      */     //   1143: fadd
/*      */     //   1144: fstore #4
/*      */     //   1146: fconst_0
/*      */     //   1147: fstore_3
/*      */     //   1148: fload_2
/*      */     //   1149: fconst_0
/*      */     //   1150: fcmpl
/*      */     //   1151: ifle -> 1159
/*      */     //   1154: fconst_1
/*      */     //   1155: fstore_2
/*      */     //   1156: goto -> 1169
/*      */     //   1159: fload_2
/*      */     //   1160: fconst_0
/*      */     //   1161: fcmpg
/*      */     //   1162: ifge -> 1169
/*      */     //   1165: ldc_w -1.0
/*      */     //   1168: fstore_2
/*      */     //   1169: fload #4
/*      */     //   1171: ldc_w 90.0
/*      */     //   1174: fadd
/*      */     //   1175: f2d
/*      */     //   1176: invokestatic toRadians : (D)D
/*      */     //   1179: invokestatic cos : (D)D
/*      */     //   1182: dstore #5
/*      */     //   1184: fload #4
/*      */     //   1186: ldc_w 90.0
/*      */     //   1189: fadd
/*      */     //   1190: f2d
/*      */     //   1191: invokestatic toRadians : (D)D
/*      */     //   1194: invokestatic sin : (D)D
/*      */     //   1197: dstore #7
/*      */     //   1199: aload_1
/*      */     //   1200: fload_2
/*      */     //   1201: f2d
/*      */     //   1202: aload_0
/*      */     //   1203: getfield moveSpeed : D
/*      */     //   1206: dmul
/*      */     //   1207: dload #5
/*      */     //   1209: dmul
/*      */     //   1210: fload_3
/*      */     //   1211: f2d
/*      */     //   1212: aload_0
/*      */     //   1213: getfield moveSpeed : D
/*      */     //   1216: dmul
/*      */     //   1217: dload #7
/*      */     //   1219: dmul
/*      */     //   1220: dadd
/*      */     //   1221: invokevirtual setX : (D)V
/*      */     //   1224: aload_1
/*      */     //   1225: fload_2
/*      */     //   1226: f2d
/*      */     //   1227: aload_0
/*      */     //   1228: getfield moveSpeed : D
/*      */     //   1231: dmul
/*      */     //   1232: dload #7
/*      */     //   1234: dmul
/*      */     //   1235: fload_3
/*      */     //   1236: f2d
/*      */     //   1237: aload_0
/*      */     //   1238: getfield moveSpeed : D
/*      */     //   1241: dmul
/*      */     //   1242: dload #5
/*      */     //   1244: dmul
/*      */     //   1245: dsub
/*      */     //   1246: invokevirtual setZ : (D)V
/*      */     //   1249: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1252: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1255: ldc_w 0.6
/*      */     //   1258: putfield stepHeight : F
/*      */     //   1261: fload_2
/*      */     //   1262: fconst_0
/*      */     //   1263: fcmpl
/*      */     //   1264: ifne -> 1283
/*      */     //   1267: fload_3
/*      */     //   1268: fconst_0
/*      */     //   1269: fcmpl
/*      */     //   1270: ifne -> 1283
/*      */     //   1273: aload_1
/*      */     //   1274: dconst_0
/*      */     //   1275: invokevirtual setX : (D)V
/*      */     //   1278: aload_1
/*      */     //   1279: dconst_0
/*      */     //   1280: invokevirtual setZ : (D)V
/*      */     //   1283: aload_0
/*      */     //   1284: invokevirtual isMoving : ()Z
/*      */     //   1287: ifeq -> 1307
/*      */     //   1290: getstatic awareline/main/mod/implement/combat/TargetStrafe.getInstance : Lawareline/main/mod/implement/combat/TargetStrafe;
/*      */     //   1293: invokevirtual isEnabled : ()Z
/*      */     //   1296: ifeq -> 1307
/*      */     //   1299: aload_1
/*      */     //   1300: aload_0
/*      */     //   1301: getfield speed : D
/*      */     //   1304: invokevirtual setMoveSpeed : (D)V
/*      */     //   1307: goto -> 2960
/*      */     //   1310: aload_0
/*      */     //   1311: getfield mode : Lawareline/main/mod/values/Mode;
/*      */     //   1314: ldc 'LowHop'
/*      */     //   1316: invokevirtual is : (Ljava/lang/String;)Z
/*      */     //   1319: ifeq -> 1847
/*      */     //   1322: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1325: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1328: invokevirtual isSneaking : ()Z
/*      */     //   1331: ifeq -> 1335
/*      */     //   1334: return
/*      */     //   1335: getstatic awareline/main/utility/MoveUtils.INSTANCE : Lawareline/main/utility/MoveUtils;
/*      */     //   1338: invokevirtual isMovingKeyBindingActive : ()Z
/*      */     //   1341: ifeq -> 1356
/*      */     //   1344: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1347: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1350: invokevirtual isInWater : ()Z
/*      */     //   1353: ifeq -> 1367
/*      */     //   1356: aload_0
/*      */     //   1357: dconst_0
/*      */     //   1358: putfield speed : D
/*      */     //   1361: aload_0
/*      */     //   1362: iconst_0
/*      */     //   1363: putfield stage : I
/*      */     //   1366: return
/*      */     //   1367: getstatic awareline/main/utility/MoveUtils.INSTANCE : Lawareline/main/utility/MoveUtils;
/*      */     //   1370: ldc2_w 0.01
/*      */     //   1373: invokevirtual isOnGround : (D)Z
/*      */     //   1376: ifeq -> 1775
/*      */     //   1379: aload_0
/*      */     //   1380: getfield stage : I
/*      */     //   1383: ifge -> 1398
/*      */     //   1386: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1389: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1392: getfield isCollidedHorizontally : Z
/*      */     //   1395: ifeq -> 1775
/*      */     //   1398: aload_0
/*      */     //   1399: iconst_0
/*      */     //   1400: putfield stage : I
/*      */     //   1403: ldc2_w 0.4001999986886975
/*      */     //   1406: getstatic awareline/main/utility/MoveUtils.INSTANCE : Lawareline/main/utility/MoveUtils;
/*      */     //   1409: invokevirtual getJumpEffect : ()I
/*      */     //   1412: i2d
/*      */     //   1413: ldc2_w 0.099
/*      */     //   1416: dmul
/*      */     //   1417: dadd
/*      */     //   1418: dstore_2
/*      */     //   1419: aload_0
/*      */     //   1420: getfield should : Lawareline/main/mod/values/Option;
/*      */     //   1423: invokevirtual get : ()Ljava/lang/Object;
/*      */     //   1426: checkcast java/lang/Boolean
/*      */     //   1429: invokevirtual booleanValue : ()Z
/*      */     //   1432: ifeq -> 1770
/*      */     //   1435: ldc2_w 3.0
/*      */     //   1438: dstore #4
/*      */     //   1440: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1443: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1446: getfield posX : D
/*      */     //   1449: dstore #6
/*      */     //   1451: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1454: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1457: getfield posZ : D
/*      */     //   1460: dstore #8
/*      */     //   1462: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1465: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1468: getfield movementInput : Lnet/minecraft/util/MovementInput;
/*      */     //   1471: getfield moveForward : F
/*      */     //   1474: f2d
/*      */     //   1475: dstore #10
/*      */     //   1477: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1480: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1483: getfield movementInput : Lnet/minecraft/util/MovementInput;
/*      */     //   1486: getfield moveStrafe : F
/*      */     //   1489: f2d
/*      */     //   1490: dstore #12
/*      */     //   1492: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1495: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1498: getfield rotationYaw : F
/*      */     //   1501: f2d
/*      */     //   1502: dstore #14
/*      */     //   1504: dload #14
/*      */     //   1506: ldc2_w 90.0
/*      */     //   1509: dadd
/*      */     //   1510: invokestatic toRadians : (D)D
/*      */     //   1513: invokestatic cos : (D)D
/*      */     //   1516: dstore #16
/*      */     //   1518: dload #14
/*      */     //   1520: ldc2_w 90.0
/*      */     //   1523: dadd
/*      */     //   1524: invokestatic toRadians : (D)D
/*      */     //   1527: invokestatic sin : (D)D
/*      */     //   1530: dstore #18
/*      */     //   1532: dload #6
/*      */     //   1534: dload #10
/*      */     //   1536: ldc2_w 3.0
/*      */     //   1539: dmul
/*      */     //   1540: dload #16
/*      */     //   1542: dmul
/*      */     //   1543: dload #12
/*      */     //   1545: ldc2_w 3.0
/*      */     //   1548: dmul
/*      */     //   1549: dload #18
/*      */     //   1551: dmul
/*      */     //   1552: dadd
/*      */     //   1553: dadd
/*      */     //   1554: dstore #6
/*      */     //   1556: dload #8
/*      */     //   1558: dload #10
/*      */     //   1560: ldc2_w 3.0
/*      */     //   1563: dmul
/*      */     //   1564: dload #18
/*      */     //   1566: dmul
/*      */     //   1567: dload #12
/*      */     //   1569: ldc2_w 3.0
/*      */     //   1572: dmul
/*      */     //   1573: dload #16
/*      */     //   1575: dmul
/*      */     //   1576: dsub
/*      */     //   1577: dadd
/*      */     //   1578: dstore #8
/*      */     //   1580: new net/minecraft/util/BlockPos
/*      */     //   1583: dup
/*      */     //   1584: dload #6
/*      */     //   1586: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1589: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1592: getfield posY : D
/*      */     //   1595: ldc2_w 0.1
/*      */     //   1598: dadd
/*      */     //   1599: dload #8
/*      */     //   1601: invokespecial <init> : (DDD)V
/*      */     //   1604: astore #20
/*      */     //   1606: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1609: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1612: aload #20
/*      */     //   1614: invokevirtual getBlockState : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
/*      */     //   1617: invokeinterface getBlock : ()Lnet/minecraft/block/Block;
/*      */     //   1622: astore #21
/*      */     //   1624: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1627: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   1630: getfield keyBindJump : Lnet/minecraft/client/settings/KeyBinding;
/*      */     //   1633: invokevirtual getKeyCode : ()I
/*      */     //   1636: invokestatic isKeyDown : (I)Z
/*      */     //   1639: ifne -> 1730
/*      */     //   1642: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1645: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1648: getfield isCollidedHorizontally : Z
/*      */     //   1651: ifne -> 1730
/*      */     //   1654: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1657: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1660: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1663: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1666: invokevirtual getPosition : ()Lnet/minecraft/util/BlockPos;
/*      */     //   1669: iconst_0
/*      */     //   1670: iconst_m1
/*      */     //   1671: iconst_0
/*      */     //   1672: invokevirtual add : (III)Lnet/minecraft/util/BlockPos;
/*      */     //   1675: invokevirtual getBlockState : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
/*      */     //   1678: invokeinterface getBlock : ()Lnet/minecraft/block/Block;
/*      */     //   1683: invokevirtual isFullBlock : ()Z
/*      */     //   1686: ifeq -> 1730
/*      */     //   1689: aload_0
/*      */     //   1690: getfield lowHopNoCheckTarget : Lawareline/main/mod/values/Option;
/*      */     //   1693: invokevirtual get : ()Ljava/lang/Object;
/*      */     //   1696: checkcast java/lang/Boolean
/*      */     //   1699: invokevirtual booleanValue : ()Z
/*      */     //   1702: ifne -> 1714
/*      */     //   1705: getstatic awareline/main/mod/implement/combat/KillAura.getInstance : Lawareline/main/mod/implement/combat/KillAura;
/*      */     //   1708: getfield target : Lnet/minecraft/entity/EntityLivingBase;
/*      */     //   1711: ifnonnull -> 1730
/*      */     //   1714: aload #21
/*      */     //   1716: getstatic net/minecraft/init/Blocks.air : Lnet/minecraft/block/Block;
/*      */     //   1719: if_acmpeq -> 1734
/*      */     //   1722: aload #21
/*      */     //   1724: invokevirtual isNormalCube : ()Z
/*      */     //   1727: ifeq -> 1734
/*      */     //   1730: iconst_1
/*      */     //   1731: goto -> 1735
/*      */     //   1734: iconst_0
/*      */     //   1735: istore #22
/*      */     //   1737: iload #22
/*      */     //   1739: ifeq -> 1770
/*      */     //   1742: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1745: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   1748: getfield keyBindSprint : Lnet/minecraft/client/settings/KeyBinding;
/*      */     //   1751: invokevirtual getKeyCode : ()I
/*      */     //   1754: invokestatic isKeyDown : (I)Z
/*      */     //   1757: ifne -> 1770
/*      */     //   1760: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1763: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1766: dload_2
/*      */     //   1767: putfield motionY : D
/*      */     //   1770: aload_1
/*      */     //   1771: dload_2
/*      */     //   1772: invokevirtual setY : (D)V
/*      */     //   1775: aload_0
/*      */     //   1776: aload_0
/*      */     //   1777: aload_0
/*      */     //   1778: getfield stage : I
/*      */     //   1781: invokevirtual getSpeed : (I)D
/*      */     //   1784: invokestatic random : ()D
/*      */     //   1787: ldc2_w 5000.0
/*      */     //   1790: ddiv
/*      */     //   1791: dadd
/*      */     //   1792: ldc2_w 0.9
/*      */     //   1795: getstatic awareline/main/utility/MoveUtils.INSTANCE : Lawareline/main/utility/MoveUtils;
/*      */     //   1798: invokevirtual getSpeedEffect : ()I
/*      */     //   1801: i2d
/*      */     //   1802: ldc2_w 0.03
/*      */     //   1805: dmul
/*      */     //   1806: dsub
/*      */     //   1807: dmul
/*      */     //   1808: putfield speed : D
/*      */     //   1811: aload_0
/*      */     //   1812: getfield stage : I
/*      */     //   1815: ifge -> 1826
/*      */     //   1818: aload_0
/*      */     //   1819: aload_0
/*      */     //   1820: invokevirtual getBaseMovementSpeed : ()D
/*      */     //   1823: putfield speed : D
/*      */     //   1826: aload_1
/*      */     //   1827: aload_0
/*      */     //   1828: getfield speed : D
/*      */     //   1831: invokevirtual setMoveSpeed : (D)V
/*      */     //   1834: aload_0
/*      */     //   1835: dup
/*      */     //   1836: getfield stage : I
/*      */     //   1839: iconst_1
/*      */     //   1840: iadd
/*      */     //   1841: putfield stage : I
/*      */     //   1844: goto -> 2960
/*      */     //   1847: aload_0
/*      */     //   1848: getfield mode : Lawareline/main/mod/values/Mode;
/*      */     //   1851: ldc 'ACRGround'
/*      */     //   1853: invokevirtual is : (Ljava/lang/String;)Z
/*      */     //   1856: ifeq -> 2050
/*      */     //   1859: aload_0
/*      */     //   1860: invokevirtual strafe : ()V
/*      */     //   1863: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1866: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1869: invokevirtual moving : ()Z
/*      */     //   1872: ifeq -> 2960
/*      */     //   1875: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1878: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1881: invokevirtual isOnLadder : ()Z
/*      */     //   1884: ifne -> 2960
/*      */     //   1887: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1890: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1893: invokevirtual isInWater : ()Z
/*      */     //   1896: ifne -> 2960
/*      */     //   1899: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1902: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1905: invokevirtual isInLava : ()Z
/*      */     //   1908: ifne -> 2960
/*      */     //   1911: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1914: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1917: getfield isInWeb : Z
/*      */     //   1920: ifne -> 2960
/*      */     //   1923: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1926: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   1929: getfield keyBindJump : Lnet/minecraft/client/settings/KeyBinding;
/*      */     //   1932: invokevirtual isKeyDown : ()Z
/*      */     //   1935: ifne -> 2960
/*      */     //   1938: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1941: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1944: getfield fallDistance : F
/*      */     //   1947: fconst_1
/*      */     //   1948: fcmpg
/*      */     //   1949: ifgt -> 2960
/*      */     //   1952: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1955: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1958: getfield onGround : Z
/*      */     //   1961: ifeq -> 2037
/*      */     //   1964: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1967: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1970: invokevirtual jump : ()V
/*      */     //   1973: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1976: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1979: dup
/*      */     //   1980: getfield motionY : D
/*      */     //   1983: ldc2_w 0.28999999165534973
/*      */     //   1986: dsub
/*      */     //   1987: putfield motionY : D
/*      */     //   1990: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1993: getfield timer : Lnet/minecraft/util/Timer;
/*      */     //   1996: fconst_1
/*      */     //   1997: putfield timerSpeed : F
/*      */     //   2000: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2003: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   2006: dup
/*      */     //   2007: getfield motionX : D
/*      */     //   2010: ldc2_w 0.962
/*      */     //   2013: dmul
/*      */     //   2014: putfield motionX : D
/*      */     //   2017: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2020: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   2023: dup
/*      */     //   2024: getfield motionZ : D
/*      */     //   2027: ldc2_w 0.962
/*      */     //   2030: dmul
/*      */     //   2031: putfield motionZ : D
/*      */     //   2034: goto -> 2960
/*      */     //   2037: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2040: getfield timer : Lnet/minecraft/util/Timer;
/*      */     //   2043: fconst_1
/*      */     //   2044: putfield timerSpeed : F
/*      */     //   2047: goto -> 2960
/*      */     //   2050: aload_0
/*      */     //   2051: getfield mode : Lawareline/main/mod/values/Mode;
/*      */     //   2054: ldc 'NCP'
/*      */     //   2056: invokevirtual is : (Ljava/lang/String;)Z
/*      */     //   2059: ifeq -> 2169
/*      */     //   2062: invokestatic isInLiquid : ()Z
/*      */     //   2065: ifne -> 2116
/*      */     //   2068: invokestatic isOnLiquid : ()Z
/*      */     //   2071: ifne -> 2116
/*      */     //   2074: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2077: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   2080: invokevirtual isInWater : ()Z
/*      */     //   2083: ifne -> 2116
/*      */     //   2086: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2089: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   2092: getfield isInWeb : Z
/*      */     //   2095: ifne -> 2116
/*      */     //   2098: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2101: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   2104: invokevirtual isSneaking : ()Z
/*      */     //   2107: ifne -> 2116
/*      */     //   2110: invokestatic isInsideBlock : ()Z
/*      */     //   2113: ifeq -> 2117
/*      */     //   2116: return
/*      */     //   2117: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2120: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   2123: aload_0
/*      */     //   2124: invokevirtual isMoving : ()Z
/*      */     //   2127: invokevirtual setSprinting : (Z)V
/*      */     //   2130: aload_0
/*      */     //   2131: invokevirtual isMoving : ()Z
/*      */     //   2134: ifne -> 2144
/*      */     //   2137: getstatic awareline/main/utility/MoveUtils.INSTANCE : Lawareline/main/utility/MoveUtils;
/*      */     //   2140: aload_1
/*      */     //   2141: invokevirtual pause : (Lawareline/main/event/events/world/moveEvents/EventMove;)V
/*      */     //   2144: getstatic awareline/main/mod/implement/world/Scaffold.getInstance : Lawareline/main/mod/implement/world/Scaffold;
/*      */     //   2147: invokevirtual isEnabled : ()Z
/*      */     //   2150: ifne -> 2960
/*      */     //   2153: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2156: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   2159: getfield keyBindJump : Lnet/minecraft/client/settings/KeyBinding;
/*      */     //   2162: iconst_0
/*      */     //   2163: putfield pressed : Z
/*      */     //   2166: goto -> 2960
/*      */     //   2169: aload_0
/*      */     //   2170: getfield mode : Lawareline/main/mod/values/Mode;
/*      */     //   2173: ldc 'OldNCP'
/*      */     //   2175: invokevirtual is : (Ljava/lang/String;)Z
/*      */     //   2178: ifeq -> 2650
/*      */     //   2181: invokestatic inLiquid : ()Z
/*      */     //   2184: ifne -> 2193
/*      */     //   2187: invokestatic onLiquid : ()Z
/*      */     //   2190: ifeq -> 2194
/*      */     //   2193: return
/*      */     //   2194: aload_0
/*      */     //   2195: invokevirtual isMoving : ()Z
/*      */     //   2198: ifne -> 2213
/*      */     //   2201: aload_0
/*      */     //   2202: dconst_0
/*      */     //   2203: putfield speed : D
/*      */     //   2206: getstatic awareline/main/utility/MoveUtils.INSTANCE : Lawareline/main/utility/MoveUtils;
/*      */     //   2209: aload_1
/*      */     //   2210: invokevirtual pause : (Lawareline/main/event/events/world/moveEvents/EventMove;)V
/*      */     //   2213: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2216: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   2219: getfield isCollidedHorizontally : Z
/*      */     //   2222: ifeq -> 2230
/*      */     //   2225: aload_0
/*      */     //   2226: iconst_1
/*      */     //   2227: putfield collided : Z
/*      */     //   2230: aload_0
/*      */     //   2231: getfield collided : Z
/*      */     //   2234: ifeq -> 2242
/*      */     //   2237: aload_0
/*      */     //   2238: iconst_m1
/*      */     //   2239: putfield stage : I
/*      */     //   2242: aload_0
/*      */     //   2243: getfield stair : D
/*      */     //   2246: dconst_0
/*      */     //   2247: dcmpl
/*      */     //   2248: ifle -> 2263
/*      */     //   2251: aload_0
/*      */     //   2252: dup
/*      */     //   2253: getfield stair : D
/*      */     //   2256: ldc2_w 0.25
/*      */     //   2259: dsub
/*      */     //   2260: putfield stair : D
/*      */     //   2263: aload_0
/*      */     //   2264: dup
/*      */     //   2265: getfield less : D
/*      */     //   2268: aload_0
/*      */     //   2269: getfield less : D
/*      */     //   2272: dconst_1
/*      */     //   2273: dcmpl
/*      */     //   2274: ifle -> 2283
/*      */     //   2277: ldc2_w 0.12
/*      */     //   2280: goto -> 2286
/*      */     //   2283: ldc2_w 0.11
/*      */     //   2286: dsub
/*      */     //   2287: putfield less : D
/*      */     //   2290: aload_0
/*      */     //   2291: getfield less : D
/*      */     //   2294: dconst_0
/*      */     //   2295: dcmpg
/*      */     //   2296: ifge -> 2304
/*      */     //   2299: aload_0
/*      */     //   2300: dconst_0
/*      */     //   2301: putfield less : D
/*      */     //   2304: getstatic awareline/main/utility/MoveUtils.INSTANCE : Lawareline/main/utility/MoveUtils;
/*      */     //   2307: ldc2_w 0.01
/*      */     //   2310: invokevirtual isOnGround : (D)Z
/*      */     //   2313: ifeq -> 2457
/*      */     //   2316: aload_0
/*      */     //   2317: invokevirtual isMoving : ()Z
/*      */     //   2320: ifeq -> 2457
/*      */     //   2323: aload_0
/*      */     //   2324: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2327: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   2330: getfield isCollidedHorizontally : Z
/*      */     //   2333: putfield collided : Z
/*      */     //   2336: aload_0
/*      */     //   2337: getfield stage : I
/*      */     //   2340: ifge -> 2350
/*      */     //   2343: aload_0
/*      */     //   2344: getfield collided : Z
/*      */     //   2347: ifeq -> 2457
/*      */     //   2350: aload_0
/*      */     //   2351: iconst_0
/*      */     //   2352: putfield stage : I
/*      */     //   2355: ldc2_w 0.39938
/*      */     //   2358: getstatic awareline/main/utility/MoveUtils.INSTANCE : Lawareline/main/utility/MoveUtils;
/*      */     //   2361: invokevirtual getJumpEffect : ()I
/*      */     //   2364: i2d
/*      */     //   2365: ldc2_w 0.1
/*      */     //   2368: dmul
/*      */     //   2369: dadd
/*      */     //   2370: dstore_2
/*      */     //   2371: aload_0
/*      */     //   2372: getfield stair : D
/*      */     //   2375: dconst_0
/*      */     //   2376: dcmpl
/*      */     //   2377: ifne -> 2404
/*      */     //   2380: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2383: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   2386: invokevirtual jump : ()V
/*      */     //   2389: aload_1
/*      */     //   2390: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2393: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   2396: dload_2
/*      */     //   2397: dup2_x1
/*      */     //   2398: putfield motionY : D
/*      */     //   2401: invokevirtual setY : (D)V
/*      */     //   2404: aload_0
/*      */     //   2405: dup
/*      */     //   2406: getfield less : D
/*      */     //   2409: dconst_1
/*      */     //   2410: dadd
/*      */     //   2411: putfield less : D
/*      */     //   2414: aload_0
/*      */     //   2415: aload_0
/*      */     //   2416: getfield less : D
/*      */     //   2419: dconst_1
/*      */     //   2420: dcmpl
/*      */     //   2421: ifle -> 2435
/*      */     //   2424: aload_0
/*      */     //   2425: getfield lessSlow : Z
/*      */     //   2428: ifne -> 2435
/*      */     //   2431: iconst_1
/*      */     //   2432: goto -> 2436
/*      */     //   2435: iconst_0
/*      */     //   2436: putfield lessSlow : Z
/*      */     //   2439: aload_0
/*      */     //   2440: getfield less : D
/*      */     //   2443: ldc2_w 1.12
/*      */     //   2446: dcmpl
/*      */     //   2447: ifle -> 2457
/*      */     //   2450: aload_0
/*      */     //   2451: ldc2_w 1.12
/*      */     //   2454: putfield less : D
/*      */     //   2457: aload_0
/*      */     //   2458: aload_0
/*      */     //   2459: aload_0
/*      */     //   2460: getfield stage : I
/*      */     //   2463: invokevirtual getWatchdogSpeed : (I)D
/*      */     //   2466: ldc2_w 0.0331
/*      */     //   2469: dadd
/*      */     //   2470: ldc2_w 0.02889
/*      */     //   2473: dsub
/*      */     //   2474: getstatic awareline/main/utility/MoveUtils.INSTANCE : Lawareline/main/utility/MoveUtils;
/*      */     //   2477: invokevirtual getBaseMoveSpeed : ()D
/*      */     //   2480: ldc2_w 1.309999942779541
/*      */     //   2483: dmul
/*      */     //   2484: invokestatic max : (DD)D
/*      */     //   2487: putfield speed : D
/*      */     //   2490: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2493: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   2496: getstatic net/minecraft/potion/Potion.moveSpeed : Lnet/minecraft/potion/Potion;
/*      */     //   2499: invokevirtual isPotionActive : (Lnet/minecraft/potion/Potion;)Z
/*      */     //   2502: ifeq -> 2540
/*      */     //   2505: aload_0
/*      */     //   2506: dup
/*      */     //   2507: getfield speed : D
/*      */     //   2510: ldc2_w 0.906
/*      */     //   2513: getstatic awareline/main/utility/MoveUtils.INSTANCE : Lawareline/main/utility/MoveUtils;
/*      */     //   2516: invokevirtual getSpeedEffect : ()I
/*      */     //   2519: bipush #100
/*      */     //   2521: idiv
/*      */     //   2522: i2d
/*      */     //   2523: dadd
/*      */     //   2524: getstatic awareline/main/utility/MoveUtils.INSTANCE : Lawareline/main/utility/MoveUtils;
/*      */     //   2527: invokevirtual getBaseMoveSpeed : ()D
/*      */     //   2530: invokestatic max : (DD)D
/*      */     //   2533: dmul
/*      */     //   2534: putfield speed : D
/*      */     //   2537: goto -> 2565
/*      */     //   2540: aload_0
/*      */     //   2541: dup
/*      */     //   2542: getfield speed : D
/*      */     //   2545: ldc2_w 0.9121
/*      */     //   2548: getstatic awareline/main/utility/MoveUtils.INSTANCE : Lawareline/main/utility/MoveUtils;
/*      */     //   2551: invokevirtual getBaseMoveSpeed : ()D
/*      */     //   2554: ldc2_w 3.0
/*      */     //   2557: dmul
/*      */     //   2558: invokestatic max : (DD)D
/*      */     //   2561: dmul
/*      */     //   2562: putfield speed : D
/*      */     //   2565: aload_0
/*      */     //   2566: getfield stair : D
/*      */     //   2569: dconst_0
/*      */     //   2570: dcmpl
/*      */     //   2571: ifle -> 2598
/*      */     //   2574: aload_0
/*      */     //   2575: dup
/*      */     //   2576: getfield speed : D
/*      */     //   2579: ldc2_w 0.7
/*      */     //   2582: getstatic awareline/main/utility/MoveUtils.INSTANCE : Lawareline/main/utility/MoveUtils;
/*      */     //   2585: invokevirtual getSpeedEffect : ()I
/*      */     //   2588: i2d
/*      */     //   2589: ldc2_w 0.1
/*      */     //   2592: dmul
/*      */     //   2593: dsub
/*      */     //   2594: dmul
/*      */     //   2595: putfield speed : D
/*      */     //   2598: aload_0
/*      */     //   2599: invokevirtual isMoving : ()Z
/*      */     //   2602: ifeq -> 2960
/*      */     //   2605: aload_1
/*      */     //   2606: aload_0
/*      */     //   2607: getfield speed : D
/*      */     //   2610: aload_0
/*      */     //   2611: getfield oldNCPSlowDown : Lawareline/main/mod/values/Option;
/*      */     //   2614: invokevirtual get : ()Ljava/lang/Object;
/*      */     //   2617: checkcast java/lang/Boolean
/*      */     //   2620: invokevirtual booleanValue : ()Z
/*      */     //   2623: ifne -> 2630
/*      */     //   2626: dconst_0
/*      */     //   2627: goto -> 2633
/*      */     //   2630: ldc2_w 0.026
/*      */     //   2633: dsub
/*      */     //   2634: invokevirtual setMoveSpeed : (D)V
/*      */     //   2637: aload_0
/*      */     //   2638: dup
/*      */     //   2639: getfield stage : I
/*      */     //   2642: iconst_1
/*      */     //   2643: iadd
/*      */     //   2644: putfield stage : I
/*      */     //   2647: goto -> 2960
/*      */     //   2650: aload_0
/*      */     //   2651: getfield mode : Lawareline/main/mod/values/Mode;
/*      */     //   2654: ldc 'Bhop'
/*      */     //   2656: invokevirtual is : (Ljava/lang/String;)Z
/*      */     //   2659: ifeq -> 2960
/*      */     //   2662: aload_0
/*      */     //   2663: pop
/*      */     //   2664: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2667: getfield timer : Lnet/minecraft/util/Timer;
/*      */     //   2670: ldc_w 1.07
/*      */     //   2673: putfield timerSpeed : F
/*      */     //   2676: aload_0
/*      */     //   2677: invokespecial canZoom : ()Z
/*      */     //   2680: ifeq -> 2710
/*      */     //   2683: aload_0
/*      */     //   2684: getfield stage : I
/*      */     //   2687: iconst_1
/*      */     //   2688: if_icmpne -> 2710
/*      */     //   2691: aload_0
/*      */     //   2692: ldc2_w 2.55
/*      */     //   2695: aload_0
/*      */     //   2696: invokevirtual getBaseMovementSpeed : ()D
/*      */     //   2699: dmul
/*      */     //   2700: ldc2_w 0.01
/*      */     //   2703: dsub
/*      */     //   2704: putfield movementSpeed : D
/*      */     //   2707: goto -> 2913
/*      */     //   2710: aload_0
/*      */     //   2711: invokespecial canZoom : ()Z
/*      */     //   2714: ifeq -> 2761
/*      */     //   2717: aload_0
/*      */     //   2718: getfield stage : I
/*      */     //   2721: iconst_2
/*      */     //   2722: if_icmpne -> 2761
/*      */     //   2725: aload_0
/*      */     //   2726: pop
/*      */     //   2727: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2730: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   2733: ldc2_w 0.3999
/*      */     //   2736: putfield motionY : D
/*      */     //   2739: aload_1
/*      */     //   2740: ldc2_w 0.3999
/*      */     //   2743: invokevirtual setY : (D)V
/*      */     //   2746: aload_0
/*      */     //   2747: dup
/*      */     //   2748: getfield movementSpeed : D
/*      */     //   2751: ldc2_w 2.1
/*      */     //   2754: dmul
/*      */     //   2755: putfield movementSpeed : D
/*      */     //   2758: goto -> 2913
/*      */     //   2761: aload_0
/*      */     //   2762: getfield stage : I
/*      */     //   2765: iconst_3
/*      */     //   2766: if_icmpne -> 2796
/*      */     //   2769: ldc2_w 0.66
/*      */     //   2772: aload_0
/*      */     //   2773: getfield distance : D
/*      */     //   2776: aload_0
/*      */     //   2777: invokevirtual getBaseMovementSpeed : ()D
/*      */     //   2780: dsub
/*      */     //   2781: dmul
/*      */     //   2782: dstore_2
/*      */     //   2783: aload_0
/*      */     //   2784: aload_0
/*      */     //   2785: getfield distance : D
/*      */     //   2788: dload_2
/*      */     //   2789: dsub
/*      */     //   2790: putfield movementSpeed : D
/*      */     //   2793: goto -> 2913
/*      */     //   2796: aload_0
/*      */     //   2797: pop
/*      */     //   2798: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2801: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   2804: aload_0
/*      */     //   2805: pop
/*      */     //   2806: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2809: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   2812: aload_0
/*      */     //   2813: pop
/*      */     //   2814: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2817: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   2820: getfield boundingBox : Lnet/minecraft/util/AxisAlignedBB;
/*      */     //   2823: dconst_0
/*      */     //   2824: aload_0
/*      */     //   2825: pop
/*      */     //   2826: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2829: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   2832: getfield motionY : D
/*      */     //   2835: dconst_0
/*      */     //   2836: invokevirtual offset : (DDD)Lnet/minecraft/util/AxisAlignedBB;
/*      */     //   2839: invokevirtual getCollidingBoundingBoxes : (Lnet/minecraft/entity/Entity;Lnet/minecraft/util/AxisAlignedBB;)Ljava/util/List;
/*      */     //   2842: astore_2
/*      */     //   2843: aload_2
/*      */     //   2844: invokeinterface size : ()I
/*      */     //   2849: ifgt -> 2873
/*      */     //   2852: aload_0
/*      */     //   2853: pop
/*      */     //   2854: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2857: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   2860: getfield isCollidedVertically : Z
/*      */     //   2863: ifeq -> 2896
/*      */     //   2866: aload_0
/*      */     //   2867: getfield stage : I
/*      */     //   2870: ifle -> 2896
/*      */     //   2873: aload_0
/*      */     //   2874: aload_0
/*      */     //   2875: pop
/*      */     //   2876: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2879: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   2882: invokevirtual moving : ()Z
/*      */     //   2885: ifeq -> 2892
/*      */     //   2888: iconst_1
/*      */     //   2889: goto -> 2893
/*      */     //   2892: iconst_0
/*      */     //   2893: putfield stage : I
/*      */     //   2896: aload_0
/*      */     //   2897: aload_0
/*      */     //   2898: getfield distance : D
/*      */     //   2901: aload_0
/*      */     //   2902: getfield distance : D
/*      */     //   2905: ldc2_w 159.0
/*      */     //   2908: ddiv
/*      */     //   2909: dsub
/*      */     //   2910: putfield movementSpeed : D
/*      */     //   2913: aload_0
/*      */     //   2914: aload_0
/*      */     //   2915: getfield movementSpeed : D
/*      */     //   2918: aload_0
/*      */     //   2919: invokevirtual getBaseMovementSpeed : ()D
/*      */     //   2922: invokestatic max : (DD)D
/*      */     //   2925: putfield movementSpeed : D
/*      */     //   2928: aload_1
/*      */     //   2929: aload_0
/*      */     //   2930: getfield movementSpeed : D
/*      */     //   2933: invokevirtual setMoveSpeedNoDamageBoost : (D)V
/*      */     //   2936: aload_0
/*      */     //   2937: pop
/*      */     //   2938: getstatic awareline/main/mod/implement/move/Speed.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2941: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   2944: invokevirtual moving : ()Z
/*      */     //   2947: ifeq -> 2960
/*      */     //   2950: aload_0
/*      */     //   2951: dup
/*      */     //   2952: getfield stage : I
/*      */     //   2955: iconst_1
/*      */     //   2956: iadd
/*      */     //   2957: putfield stage : I
/*      */     //   2960: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #653	-> 0
/*      */     //   #654	-> 27
/*      */     //   #656	-> 28
/*      */     //   #657	-> 55
/*      */     //   #658	-> 73
/*      */     //   #661	-> 74
/*      */     //   #662	-> 116
/*      */     //   #663	-> 134
/*      */     //   #664	-> 159
/*      */     //   #667	-> 167
/*      */     //   #672	-> 340
/*      */     //   #673	-> 347
/*      */     //   #674	-> 353
/*      */     //   #675	-> 378
/*      */     //   #677	-> 384
/*      */     //   #681	-> 387
/*      */     //   #682	-> 399
/*      */     //   #684	-> 400
/*      */     //   #685	-> 460
/*      */     //   #686	-> 467
/*      */     //   #687	-> 480
/*      */     //   #688	-> 492
/*      */     //   #689	-> 501
/*      */     //   #690	-> 511
/*      */     //   #691	-> 520
/*      */     //   #693	-> 527
/*      */     //   #699	-> 531
/*      */     //   #700	-> 543
/*      */     //   #701	-> 557
/*      */     //   #703	-> 558
/*      */     //   #704	-> 568
/*      */     //   #705	-> 578
/*      */     //   #706	-> 585
/*      */     //   #708	-> 598
/*      */     //   #709	-> 605
/*      */     //   #711	-> 617
/*      */     //   #712	-> 624
/*      */     //   #713	-> 636
/*      */     //   #714	-> 653
/*      */     //   #718	-> 670
/*      */     //   #719	-> 689
/*      */     //   #721	-> 694
/*      */     //   #722	-> 730
/*      */     //   #723	-> 737
/*      */     //   #724	-> 749
/*      */     //   #725	-> 761
/*      */     //   #728	-> 773
/*      */     //   #729	-> 809
/*      */     //   #730	-> 814
/*      */     //   #731	-> 835
/*      */     //   #732	-> 843
/*      */     //   #733	-> 848
/*      */     //   #734	-> 860
/*      */     //   #735	-> 867
/*      */     //   #736	-> 882
/*      */     //   #737	-> 890
/*      */     //   #738	-> 895
/*      */     //   #739	-> 911
/*      */     //   #740	-> 921
/*      */     //   #741	-> 924
/*      */     //   #742	-> 982
/*      */     //   #744	-> 987
/*      */     //   #746	-> 1004
/*      */     //   #747	-> 1021
/*      */     //   #748	-> 1034
/*      */     //   #749	-> 1047
/*      */     //   #750	-> 1058
/*      */     //   #751	-> 1070
/*      */     //   #752	-> 1075
/*      */     //   #753	-> 1083
/*      */     //   #754	-> 1089
/*      */     //   #755	-> 1095
/*      */     //   #756	-> 1114
/*      */     //   #757	-> 1119
/*      */     //   #758	-> 1127
/*      */     //   #759	-> 1146
/*      */     //   #761	-> 1148
/*      */     //   #762	-> 1154
/*      */     //   #763	-> 1159
/*      */     //   #764	-> 1165
/*      */     //   #768	-> 1169
/*      */     //   #769	-> 1184
/*      */     //   #770	-> 1199
/*      */     //   #771	-> 1224
/*      */     //   #773	-> 1249
/*      */     //   #774	-> 1261
/*      */     //   #775	-> 1273
/*      */     //   #776	-> 1278
/*      */     //   #778	-> 1283
/*      */     //   #779	-> 1299
/*      */     //   #781	-> 1307
/*      */     //   #782	-> 1322
/*      */     //   #783	-> 1334
/*      */     //   #785	-> 1335
/*      */     //   #786	-> 1356
/*      */     //   #787	-> 1361
/*      */     //   #788	-> 1366
/*      */     //   #790	-> 1367
/*      */     //   #791	-> 1398
/*      */     //   #792	-> 1403
/*      */     //   #793	-> 1419
/*      */     //   #794	-> 1435
/*      */     //   #795	-> 1440
/*      */     //   #796	-> 1451
/*      */     //   #797	-> 1462
/*      */     //   #798	-> 1477
/*      */     //   #799	-> 1492
/*      */     //   #800	-> 1504
/*      */     //   #801	-> 1518
/*      */     //   #802	-> 1532
/*      */     //   #803	-> 1556
/*      */     //   #804	-> 1580
/*      */     //   #805	-> 1606
/*      */     //   #806	-> 1624
/*      */     //   #807	-> 1693
/*      */     //   #808	-> 1737
/*      */     //   #809	-> 1760
/*      */     //   #811	-> 1770
/*      */     //   #813	-> 1775
/*      */     //   #814	-> 1811
/*      */     //   #815	-> 1818
/*      */     //   #817	-> 1826
/*      */     //   #818	-> 1834
/*      */     //   #819	-> 1847
/*      */     //   #820	-> 1859
/*      */     //   #821	-> 1863
/*      */     //   #822	-> 1938
/*      */     //   #823	-> 1952
/*      */     //   #824	-> 1964
/*      */     //   #825	-> 1973
/*      */     //   #826	-> 1990
/*      */     //   #827	-> 2000
/*      */     //   #828	-> 2017
/*      */     //   #830	-> 2037
/*      */     //   #834	-> 2050
/*      */     //   #835	-> 2062
/*      */     //   #836	-> 2080
/*      */     //   #837	-> 2110
/*      */     //   #838	-> 2116
/*      */     //   #840	-> 2117
/*      */     //   #841	-> 2130
/*      */     //   #842	-> 2137
/*      */     //   #844	-> 2144
/*      */     //   #845	-> 2153
/*      */     //   #847	-> 2169
/*      */     //   #848	-> 2181
/*      */     //   #849	-> 2193
/*      */     //   #851	-> 2194
/*      */     //   #852	-> 2201
/*      */     //   #853	-> 2206
/*      */     //   #855	-> 2213
/*      */     //   #856	-> 2225
/*      */     //   #858	-> 2230
/*      */     //   #859	-> 2237
/*      */     //   #861	-> 2242
/*      */     //   #862	-> 2251
/*      */     //   #864	-> 2263
/*      */     //   #865	-> 2290
/*      */     //   #866	-> 2299
/*      */     //   #868	-> 2304
/*      */     //   #869	-> 2323
/*      */     //   #870	-> 2336
/*      */     //   #871	-> 2350
/*      */     //   #872	-> 2355
/*      */     //   #873	-> 2371
/*      */     //   #874	-> 2380
/*      */     //   #875	-> 2389
/*      */     //   #877	-> 2404
/*      */     //   #878	-> 2414
/*      */     //   #879	-> 2439
/*      */     //   #880	-> 2450
/*      */     //   #884	-> 2457
/*      */     //   #885	-> 2490
/*      */     //   #886	-> 2505
/*      */     //   #888	-> 2540
/*      */     //   #890	-> 2565
/*      */     //   #891	-> 2574
/*      */     //   #894	-> 2598
/*      */     //   #895	-> 2605
/*      */     //   #896	-> 2637
/*      */     //   #898	-> 2650
/*      */     //   #899	-> 2662
/*      */     //   #900	-> 2676
/*      */     //   #901	-> 2691
/*      */     //   #902	-> 2710
/*      */     //   #903	-> 2725
/*      */     //   #904	-> 2739
/*      */     //   #905	-> 2746
/*      */     //   #906	-> 2761
/*      */     //   #907	-> 2769
/*      */     //   #908	-> 2783
/*      */     //   #909	-> 2793
/*      */     //   #910	-> 2796
/*      */     //   #911	-> 2836
/*      */     //   #910	-> 2839
/*      */     //   #912	-> 2843
/*      */     //   #913	-> 2873
/*      */     //   #915	-> 2896
/*      */     //   #917	-> 2913
/*      */     //   #918	-> 2928
/*      */     //   #919	-> 2936
/*      */     //   #920	-> 2950
/*      */     //   #923	-> 2960
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   353	31	4	speed	D
/*      */     //   737	36	2	thePlayer	Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   911	10	2	difference	D
/*      */     //   1034	273	2	forward	F
/*      */     //   1047	260	3	strafe	F
/*      */     //   1058	249	4	yaw	F
/*      */     //   1184	123	5	mx2	D
/*      */     //   1199	108	7	mz2	D
/*      */     //   1440	330	4	reach	D
/*      */     //   1451	319	6	x	D
/*      */     //   1462	308	8	z	D
/*      */     //   1477	293	10	forward	D
/*      */     //   1492	278	12	strafe	D
/*      */     //   1504	266	14	yaw	D
/*      */     //   1518	252	16	cos	D
/*      */     //   1532	238	18	sin	D
/*      */     //   1606	164	20	blockBelow	Lnet/minecraft/util/BlockPos;
/*      */     //   1624	146	21	predict	Lnet/minecraft/block/Block;
/*      */     //   1737	33	22	shouldJump	Z
/*      */     //   1419	356	2	y	D
/*      */     //   2371	86	2	motY	D
/*      */     //   2783	10	2	difference	D
/*      */     //   2843	70	2	collidingList	Ljava/util/List;
/*      */     //   0	2961	0	this	Lawareline/main/mod/implement/move/Speed;
/*      */     //   0	2961	1	e	Lawareline/main/event/events/world/moveEvents/EventMove;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean canZoom() {
/*  926 */     this; if (mc.thePlayer.moving()) { this; if (mc.thePlayer.onGround); }  return false;
/*      */   }
/*      */   
/*      */   @EventHandler
/*      */   public void onMotion(EventPreUpdate e) {
/*  931 */     if (Scaffold.getInstance.isEnabled() && ((Boolean)Scaffold.getInstance.StopSpeed
/*  932 */       .get()).booleanValue()) {
/*      */       return;
/*      */     }
/*  935 */     if (this.mode.is("NCP")) {
/*  936 */       if (mc.thePlayer == null || mc.theWorld == null) {
/*      */         return;
/*      */       }
/*  939 */       if (BlockUtils.isInLiquid() || BlockUtils.isOnLiquid() || mc.thePlayer
/*  940 */         .isInWater() || mc.thePlayer.isInWeb || mc.thePlayer.isSneaking() || 
/*  941 */         BlockUtils.isInsideBlock()) {
/*      */         return;
/*      */       }
/*  944 */       if (isMoving()) {
/*  945 */         EntityPlayerSP thePlayer = mc.thePlayer;
/*  946 */         BlockPos blockPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
/*  947 */         boolean canGroundLess = !(mc.theWorld.getBlockState(blockPos).getBlock() instanceof net.minecraft.block.BlockStairs);
/*  948 */         if (BlockUtils.isInLiquid() && !thePlayer.isCollidedHorizontally) {
/*      */           return;
/*      */         }
/*  951 */         if (canGroundLess ? thePlayer.onGround : (thePlayer.onGround && MoveUtils.INSTANCE.isOnGround(0.01D))) {
/*  952 */           if (!mc.gameSettings.keyBindJump.isKeyDown()) {
/*  953 */             mc.thePlayer.motionY = 0.399544464469D + MoveUtils.INSTANCE.getJumpEffect() * 0.1D;
/*      */           }
/*      */           
/*  956 */           setMotion(Math.max(0.4804D + MoveUtils.INSTANCE
/*  957 */                 .getSpeedEffect() * 0.1D * (
/*  958 */                 Scaffold.getInstance.isEnabled() ? 0.6F : 1.0F), MoveUtils.INSTANCE
/*      */                 
/*  960 */                 .getBaseMoveSpeed()));
/*      */ 
/*      */         
/*      */         }
/*  964 */         else if (mc.thePlayer.fallDistance > 0.1F && mc.thePlayer.fallDistance < 0.6F) {
/*  965 */           setMotion(Math.max(getSpeed() * 1.0027D, MoveUtils.INSTANCE.getBaseMoveSpeed()));
/*      */         } else {
/*  967 */           setMotion(Math.max(getSpeed() * 1.0006D, MoveUtils.INSTANCE.getBaseMoveSpeed()));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void strafe() {
/*  976 */     double movementSpeed = MathHelper.sqrt_double(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
/*  977 */     double forward = mc.thePlayer.movementInput.moveForward;
/*  978 */     double strafe = mc.thePlayer.movementInput.moveStrafe;
/*  979 */     double yaw = mc.thePlayer.rotationYaw;
/*  980 */     if (forward == 0.0D && strafe == 0.0D) {
/*  981 */       mc.thePlayer.motionX = 0.0D;
/*  982 */       mc.thePlayer.motionZ = 0.0D;
/*  983 */     } else if (forward != 0.0D) {
/*  984 */       if (strafe >= 1.0D) {
/*  985 */         yaw = (mc.thePlayer.rotationYaw + ((forward > 0.0D) ? -45 : 45));
/*  986 */         strafe = 0.0D;
/*  987 */       } else if (strafe <= -1.0D) {
/*  988 */         yaw = (mc.thePlayer.rotationYaw + ((forward > 0.0D) ? 45 : -45));
/*  989 */         strafe = 0.0D;
/*      */       } 
/*      */     } 
/*      */     
/*  993 */     if (forward > 0.0D) {
/*  994 */       forward = 1.0D;
/*  995 */     } else if (forward < 0.0D) {
/*  996 */       forward = -1.0D;
/*      */     } 
/*      */     
/*  999 */     double mx = Math.cos(Math.toRadians(yaw + 90.0D));
/* 1000 */     double mz = Math.sin(Math.toRadians(yaw + 90.0D));
/*      */     
/* 1002 */     mc.thePlayer.motionX = forward * movementSpeed * mx + strafe * movementSpeed * mz;
/* 1003 */     mc.thePlayer.motionZ = forward * movementSpeed * mz - strafe * movementSpeed * mx;
/*      */   }
/*      */   
/*      */   public double defaultSpeed() {
/* 1007 */     double n = 0.2873D;
/* 1008 */     if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
/* 1009 */       n *= 1.0D + 0.2D * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
/*      */     }
/* 1011 */     return n;
/*      */   }
/*      */   
/*      */   public double getSpeed(int stage) {
/* 1015 */     double value = getBaseMovementSpeed() + 0.028D * MoveUtils.INSTANCE.getSpeedEffect() + MoveUtils.INSTANCE.getSpeedEffect() / 15.0D;
/* 1016 */     double first = 0.4145D + MoveUtils.INSTANCE.getSpeedEffect() / 12.5D;
/* 1017 */     double pre = stage / 500.0D * 2.0D;
/* 1018 */     if (stage == 0) {
/* 1019 */       value = 0.64D + (MoveUtils.INSTANCE.getSpeedEffect() + 0.028D * MoveUtils.INSTANCE.getSpeedEffect()) * 0.134D;
/* 1020 */     } else if (stage == 1) {
/* 1021 */       value = first;
/* 1022 */     } else if (stage >= 2) {
/* 1023 */       value = first - pre;
/*      */     } 
/* 1025 */     if (mc.thePlayer.isCollidedHorizontally) {
/* 1026 */       value = 0.2D;
/* 1027 */       if (stage == 0) {
/* 1028 */         value = 0.0D;
/*      */       }
/*      */     } 
/* 1031 */     return Math.max(value, getBaseMovementSpeed() + 0.028D * MoveUtils.INSTANCE.getSpeedEffect());
/*      */   }
/*      */   
/*      */   public double getWatchdogSpeed(int stage) {
/* 1035 */     double value = defaultSpeed() + 0.028D * MoveUtils.INSTANCE.getSpeedEffect() + MoveUtils.INSTANCE.getSpeedEffect() / 15.0D;
/* 1036 */     double firstvalue = 0.4145D + MoveUtils.INSTANCE.getSpeedEffect() / 12.5D;
/* 1037 */     double decr = stage / 500.0D * 2.0D;
/* 1038 */     if (stage == 0) {
/* 1039 */       if (this.timer.delay(300L)) {
/* 1040 */         this.timer.reset();
/*      */       }
/* 1042 */       if (!this.lastCheck.delay(500L)) {
/* 1043 */         if (!this.shouldslow) {
/* 1044 */           this.shouldslow = true;
/*      */         }
/* 1046 */       } else if (this.shouldslow) {
/* 1047 */         this.shouldslow = false;
/*      */       } 
/* 1049 */       value = 0.64D + (MoveUtils.INSTANCE.getSpeedEffect() + 0.028D * MoveUtils.INSTANCE.getSpeedEffect()) * 0.134D;
/* 1050 */     } else if (stage == 1) {
/* 1051 */       value = firstvalue;
/* 1052 */     } else if (stage >= 2) {
/* 1053 */       value = firstvalue - decr;
/*      */     } 
/* 1055 */     if (this.shouldslow || !this.lastCheck.delay(500L) || this.collided) {
/* 1056 */       value = 0.2D;
/* 1057 */       if (stage == 0) {
/* 1058 */         value = 0.0D;
/*      */       }
/*      */     } 
/* 1061 */     return Math.max(value, this.shouldslow ? value : (defaultSpeed() + 0.028D * MoveUtils.INSTANCE.getSpeedEffect()));
/*      */   }
/*      */ 
/*      */   
/*      */   public double getBaseMovementSpeed() {
/* 1066 */     double baseSpeed = 0.2873D;
/* 1067 */     if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
/* 1068 */       int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
/* 1069 */       baseSpeed *= 1.0D + 0.2D * (amplifier + 1);
/*      */     } 
/* 1071 */     return baseSpeed;
/*      */   }
/*      */   
/*      */   public void setMotion(double speed) {
/* 1075 */     double forward = mc.thePlayer.movementInput.moveForward;
/* 1076 */     double strafe = mc.thePlayer.movementInput.moveStrafe;
/* 1077 */     float yaw = mc.thePlayer.rotationYaw;
/* 1078 */     if (forward == 0.0D && strafe == 0.0D) {
/* 1079 */       mc.thePlayer.motionX = 0.0D;
/* 1080 */       mc.thePlayer.motionZ = 0.0D;
/*      */     } else {
/* 1082 */       if (forward != 0.0D) {
/* 1083 */         if (strafe > 0.0D) {
/* 1084 */           yaw += ((forward > 0.0D) ? -45 : 45);
/* 1085 */         } else if (strafe < 0.0D) {
/* 1086 */           yaw += ((forward > 0.0D) ? 45 : -45);
/*      */         } 
/* 1088 */         strafe = 0.0D;
/* 1089 */         if (forward > 0.0D) {
/* 1090 */           forward = 1.0D;
/* 1091 */         } else if (forward < 0.0D) {
/* 1092 */           forward = -1.0D;
/*      */         } 
/*      */       } 
/* 1095 */       mc.thePlayer.motionX = forward * speed * -Math.sin(Math.toRadians(yaw)) + strafe * speed * Math.cos(Math.toRadians(yaw));
/* 1096 */       mc.thePlayer.motionZ = forward * speed * Math.cos(Math.toRadians(yaw)) - strafe * speed * -Math.sin(Math.toRadians(yaw));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDirection() {
/* 1102 */     float rotationYaw = mc.thePlayer.rotationYaw;
/* 1103 */     if (mc.thePlayer.moveForward < 0.0F) {
/* 1104 */       rotationYaw += 180.0F;
/*      */     }
/* 1106 */     float forward = 1.0F;
/* 1107 */     if (mc.thePlayer.moveForward < 0.0F) {
/* 1108 */       forward = -0.5F;
/* 1109 */     } else if (mc.thePlayer.moveForward > 0.0F) {
/* 1110 */       forward = 0.5F;
/*      */     } 
/* 1112 */     if (mc.thePlayer.moveStrafing > 0.0F) {
/* 1113 */       rotationYaw -= 90.0F * forward;
/*      */     }
/* 1115 */     if (mc.thePlayer.moveStrafing < 0.0F) {
/* 1116 */       rotationYaw += 90.0F * forward;
/*      */     }
/* 1118 */     return rotationYaw;
/*      */   }
/*      */   
/*      */   private double round(double value) {
/* 1122 */     BigDecimal bigDecimal = new BigDecimal(value);
/* 1123 */     bigDecimal = bigDecimal.setScale(3, RoundingMode.HALF_UP);
/* 1124 */     return bigDecimal.doubleValue();
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\Speed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */