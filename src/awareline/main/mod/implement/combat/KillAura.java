/*     */ package awareline.main.mod.implement.combat;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.moveEvents.EventStrafe;
/*     */ import awareline.main.event.events.world.packetEvents.EventPacketSend;
/*     */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.event.events.world.updateEvents.MotionUpdateEvent;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.implement.combat.advanced.AdvancedAura;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.EntityUtils;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.RaycastUtils;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.RotationUtils;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.liquidbounce.RotationUtils;
/*     */ import awareline.main.mod.implement.misc.Teams;
/*     */ import awareline.main.mod.implement.move.NoSlow;
/*     */ import awareline.main.mod.implement.move.antifallutily.TimeHelper;
/*     */ import awareline.main.mod.implement.world.Scaffold;
/*     */ import awareline.main.mod.implement.world.utils.ScaffoldUtils.Rotation;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.gui.guimainmenu.mainmenu.ClientSetting;
/*     */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*     */ import awareline.main.utility.math.MathUtil;
/*     */ import awareline.main.utility.math.RotationUtil;
/*     */ import awareline.main.utility.timer.TimerUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import java.util.stream.Collector;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.settings.KeyBinding;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*     */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.C0APacketAnimation;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.optifine.util.MathUtils;
/*     */ 
/*     */ public class KillAura extends Module {
/*     */   public static KillAura getInstance;
/*  58 */   public static Random random = new Random();
/*     */ 
/*     */   
/*  61 */   public final Option<Boolean> playerValue = new Option("Player", Boolean.valueOf(true)); public final Option<Boolean> invisibleValue = new Option("Invisible", 
/*  62 */       Boolean.valueOf(false));
/*  63 */   public final Option<Boolean> mobsValue = new Option("Mobs", Boolean.valueOf(true));
/*  64 */   public final Option<Boolean> animalsValue = new Option("Animals", Boolean.valueOf(false));
/*  65 */   public final Option<Boolean> deadValue = new Option("Dead", Boolean.valueOf(false));
/*     */ 
/*     */   
/*  68 */   public final Numbers<Double> range = new Numbers("Range", Double.valueOf(4.2D), Double.valueOf(0.1D), Double.valueOf(12.0D), Double.valueOf(0.1D)); public final Numbers<Double> blockRange = new Numbers("BlockRange", 
/*  69 */       Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(10.0D), Double.valueOf(0.5D));
/*  70 */   public final Numbers<Double> wallRange = new Numbers("WallRange", Double.valueOf(8.0D), Double.valueOf(0.0D), Double.valueOf(12.0D), Double.valueOf(0.1D));
/*  71 */   public final Numbers<Double> fovCheck = new Numbers("Fov", Double.valueOf(180.0D), Double.valueOf(1.0D), Double.valueOf(180.0D), Double.valueOf(1.0D));
/*     */ 
/*     */   
/*  74 */   public final Mode<String> rotMode = new Mode("Rotations", new String[] { "CustomSpeed", "Watchdog", "NCP", "Legit", "Smooth", "Reduce", "AAC", "HvH", "None" }, "Watchdog"); public final Mode<String> mode = new Mode("Mode", new String[] { "Switch", "Random", "Single", "Multi", "MultiInstantly", "MultiNCP" }, "Switch"); public final Mode<String> attackTiming = new Mode("AttackTiming", new String[] { "Pre", "Post", "All" }, "Post");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public final Option<Boolean> autoBlock = new Option("AutoBlock", Boolean.valueOf(true));
/*  81 */   public final Mode<String> autoBlockMode = new Mode("AutoBlocks", new String[] { "Vanilla", "Packet", "Watchdog", "OldWatchdog", "AAC", "Fake", "NCP", "OldNCP", "OldIntave", "DCJ", "KeyBind" }, "Watchdog", this.autoBlock::get);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public final Mode<String> autoBlockTiming = new Mode("BlockTiming", new String[] { "Pre", "Post", "All" }, "Post", this.autoBlock::get);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   public final Option<Boolean> keepSprint = new Option("KeepSprint", Boolean.valueOf(true));
/*     */   
/*  99 */   public final Numbers<Double> limitedMultiTargetsValue = new Numbers("LimitedMultiTargets", 
/* 100 */       Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(50.0D), Double.valueOf(1.0D), () -> Boolean.valueOf(this.mode.is("Multi")));
/* 101 */   public final Numbers<Double> switchSize = new Numbers("MaxGetTarget", Double.valueOf(1.0D), Double.valueOf(1.0D), Double.valueOf(7.0D), Double.valueOf(1.0D), () -> Boolean.valueOf(!this.mode.is("Single")));
/*     */ 
/*     */   
/* 104 */   public final ArrayList<EntityLivingBase> attacked = new ArrayList<>();
/*     */   
/* 106 */   public final Option<Boolean> autoCPS = new Option("AutoCPS", Boolean.valueOf(false));
/* 107 */   public final Numbers<Double> maxCPS = new Numbers("MaxCPS", Double.valueOf(15.0D), Double.valueOf(1.0D), Double.valueOf(40.0D), Double.valueOf(1.0D), () -> Boolean.valueOf(!((Boolean)this.autoCPS.get()).booleanValue())); public final Numbers<Double> minCPS = new Numbers("MinCPS", 
/* 108 */       Double.valueOf(9.0D), Double.valueOf(1.0D), Double.valueOf(40.0D), Double.valueOf(1.0D), () -> Boolean.valueOf(!((Boolean)this.autoCPS.get()).booleanValue()));
/* 109 */   public final Numbers<Double> mistake = new Numbers("Mistakes", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(20.0D), Double.valueOf(1.0D)); public final Numbers<Double> blockRate = new Numbers("BlockRate", Double.valueOf(100.0D), Double.valueOf(10.0D), Double.valueOf(100.0D), Double.valueOf(5.0D));
/*     */   
/* 111 */   public final Option<Boolean> requiteMouseDown = new Option("RequiteMouseDown", Boolean.valueOf(false)); public final Option<Boolean> smoothBack = new Option("SmoothBack", Boolean.valueOf(false)); public final Option<Boolean> autoDisabled = new Option("AutoDisable", 
/* 112 */       Boolean.valueOf(true)); public final Option<Boolean> imitateDC = new Option("ImitateDC", Boolean.valueOf(false));
/* 113 */   public final Option<Boolean> glich = new Option("SwingGlich", Boolean.valueOf(false)); public final Option<Boolean> force = new Option("ForceUpdate", Boolean.valueOf(false));
/* 114 */   public final Option<Boolean> dynamic = new Option("DynamicUnBlock", Boolean.valueOf(false)); public final Option<Boolean> hurtBlock = new Option("BlockWhenHurt", Boolean.valueOf(false));
/* 115 */   public final Option<Boolean> noSwing = new Option("NoSwing", Boolean.valueOf(false)); public final Option<Boolean> lockView = new Option("LockView", Boolean.valueOf(false));
/* 116 */   public final Option<Boolean> noInvAttack = new Option("NoInvAttack", Boolean.valueOf(false)); public final Option<Boolean> lockMouseOverTarget = new Option("LockMouseOverTarget", Boolean.valueOf(false));
/*     */ 
/*     */   
/* 119 */   public final Mode<String> markMode = new Mode("MarkMode", new String[] { "Normal", "Circle", "Box" }, "Circle");
/*     */ 
/*     */ 
/*     */   
/* 123 */   public final Option<Boolean> mark = new Option("Mark", Boolean.valueOf(true)); public final Option<Boolean> checkWinning = new Option("FindWinning", 
/* 124 */       Boolean.valueOf(true));
/* 125 */   public final Option<Boolean> witherPriority = new Option("WitherPriority", Boolean.valueOf(true));
/* 126 */   private final TimeHelper attackTimer = new TimeHelper();
/* 127 */   private final TimerUtil switchTimer = new TimerUtil();
/*     */   
/* 129 */   private final Numbers<Double> maxTurnSpeed = new Numbers("MaxTurnSpeed", 
/* 130 */       Double.valueOf(180.0D), Double.valueOf(0.0D), Double.valueOf(180.0D), Double.valueOf(0.5D), () -> Boolean.valueOf(this.rotMode.is("CustomSpeed"))),
/* 131 */      minTurnSpeed = new Numbers("MinTurnSpeed", Double.valueOf(85.0D), Double.valueOf(0.0D), Double.valueOf(180.0D), Double.valueOf(0.5D), () -> Boolean.valueOf(this.rotMode.is("CustomSpeed")));
/*     */ 
/*     */   
/* 134 */   private final Numbers<Double> switchDelay = new Numbers("SwitchDelay", 
/* 135 */       Double.valueOf(400.0D), Double.valueOf(1.0D), Double.valueOf(1000.0D), Double.valueOf(1.0D));
/* 136 */   private final Mode<String> priority = new Mode("Priority", new String[] { "Angle", "Range", "Armor", "Health", "Fov", "HurtTime" }, "Range");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 141 */   private final Option<Boolean> rayCast = new Option("RayCast", Boolean.valueOf(false));
/*     */   
/* 143 */   private final Mode<String> moveControl = new Mode("Strafe", new String[] { "Off", "Strict", "Silent" }, "Off");
/*     */ 
/*     */ 
/*     */   
/* 147 */   public final Option<Boolean> strafeOnlyGroundValue = new Option("StrafeOnlyGround", 
/* 148 */       Boolean.valueOf(true), () -> Boolean.valueOf(!this.moveControl.is("Off")));
/*     */   public EntityLivingBase target;
/*     */   public EntityLivingBase blockTarget;
/* 151 */   public List<EntityLivingBase> targets = new ArrayList<>(0); public boolean isBlocking; public float yaw;
/*     */   public float pitch;
/*     */   public int killed;
/*     */   public int index;
/*     */   private double cps;
/* 156 */   private float needCPS = 10.0F; private float realCPS = 10.0F;
/*     */   private long turnTime;
/*     */   
/*     */   public KillAura() {
/* 160 */     super("KillAura", new String[] { "ka", "aura" }, ModuleType.Combat);
/* 161 */     addSettings(new Value[] { (Value)this.mode, (Value)this.priority, (Value)this.witherPriority, (Value)this.attackTiming, (Value)this.maxCPS, (Value)this.minCPS, (Value)this.keepSprint, (Value)this.autoBlock, (Value)this.autoBlockMode, (Value)this.autoBlockTiming, (Value)this.blockRate, (Value)this.hurtBlock, (Value)this.rotMode, (Value)this.range, (Value)this.blockRange, (Value)this.wallRange, (Value)this.switchSize, (Value)this.limitedMultiTargetsValue, (Value)this.playerValue, (Value)this.invisibleValue, (Value)this.mobsValue, (Value)this.animalsValue, (Value)this.deadValue, (Value)this.rayCast, (Value)this.lockMouseOverTarget, (Value)this.requiteMouseDown, (Value)this.noSwing, (Value)this.lockView, (Value)this.moveControl, (Value)this.strafeOnlyGroundValue, (Value)this.smoothBack, (Value)this.autoCPS, (Value)this.noInvAttack, (Value)this.mistake, (Value)this.fovCheck, (Value)this.maxTurnSpeed, (Value)this.minTurnSpeed, (Value)this.switchDelay, (Value)this.imitateDC, (Value)this.glich, (Value)this.dynamic, (Value)this.force, (Value)this.autoDisabled, (Value)this.mark, (Value)this.markMode, (Value)this.checkWinning });
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
/* 173 */     getInstance = this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 179 */     this.target = this.blockTarget = null;
/* 180 */     this.isBlocking = false;
/* 181 */     mc.thePlayer.movementYaw = null;
/* 182 */     if (((Boolean)this.autoBlock.get()).booleanValue() && hasSword() && (
/* 183 */       this.isBlocking || mc.thePlayer.isBlocking() || mc.thePlayer.getItemInUseCount() > 0)) {
/* 184 */       unBlock();
/*     */     }
/*     */     
/*     */     try {
/* 188 */       if (!this.targets.isEmpty()) {
/* 189 */         this.targets.clear();
/*     */       }
/* 191 */     } catch (RuntimeException e) {
/* 192 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 198 */     checkModule(VanillaAura.class);
/* 199 */     this.attacked.clear();
/* 200 */     this.target = null;
/* 201 */     this.blockTarget = null;
/* 202 */     this.index = 0;
/* 203 */     this.cps = 0.0D;
/* 204 */     this.attackTimer.reset();
/* 205 */     this.realCPS = this.needCPS = 15.0F;
/* 206 */     this.yaw = mc.thePlayer.rotationYaw;
/* 207 */     this.pitch = mc.thePlayer.rotationPitch;
/* 208 */     if (((Boolean)this.smoothBack.get()).booleanValue()) {
/* 209 */       this.turnTime = System.currentTimeMillis() - 300L;
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPacket(EventPacketSend e) {
/* 215 */     if (((Boolean)this.checkWinning.get()).booleanValue() && 
/* 216 */       this.target != null) {
/* 217 */       boolean pressed = e.getPacket() instanceof C0APacketAnimation;
/* 218 */       if (pressed != ClientSetting.getInstance.wasPressed) {
/* 219 */         ClientSetting.getInstance.wasPressed = pressed;
/* 220 */         long lastPress = System.currentTimeMillis();
/* 221 */         if (pressed) {
/* 222 */           ClientSetting.getInstance.clicks.add(Long.valueOf(lastPress));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long getCPSForDoAttack() {
/* 230 */     if (((Boolean)this.autoCPS.get()).booleanValue()) {
/* 231 */       return randomClickDelay(this.realCPS, this.realCPS);
/*     */     }
/* 233 */     double maxCps = ((Double)this.maxCPS.get()).doubleValue(), minCps = ((Double)this.minCPS.get()).doubleValue();
/* 234 */     return randomClickDelay(Math.min(minCps, maxCps), Math.max(minCps, maxCps));
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onUpdate(EventPreUpdate event) {
/* 240 */     setSuffix((String)this.mode.get() + (((Boolean)this.autoCPS.get()).booleanValue() ? (" " + getAutoCPS()) : ""));
/*     */     
/* 242 */     if (((Boolean)this.keepSprint.get()).booleanValue() && isMoving()) {
/* 243 */       mc.thePlayer.setSprinting(true);
/*     */     }
/*     */     
/* 246 */     if (mc.thePlayer.inventory.getStackInSlot(0) != null && 
/* 247 */       mc.thePlayer.inventory.getStackInSlot(0).getItem() == Items.compass && 
/* 248 */       mc.thePlayer.inventory.getStackInSlot(0).getDisplayName().contains("Teleporter")) {
/* 249 */       this.target = null;
/* 250 */       this.blockTarget = null;
/* 251 */       this.targets.clear();
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 257 */     if (Scaffold.getInstance.isEnabled() && ((Boolean)Scaffold.getInstance.combatspoof.get()).booleanValue()) {
/* 258 */       this.target = null;
/* 259 */       this.blockTarget = null;
/* 260 */       this.targets.clear();
/*     */       
/*     */       return;
/*     */     } 
/* 264 */     if (((Boolean)this.noInvAttack.get()).booleanValue() && mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiContainer) {
/* 265 */       this.target = null;
/* 266 */       this.blockTarget = null;
/* 267 */       this.targets.clear();
/*     */       return;
/*     */     } 
/* 270 */     if (this.target == null && this.blockTarget == null && ((Boolean)this.autoBlock.get()).booleanValue() && hasSword() && 
/* 271 */       this.isBlocking) {
/* 272 */       unBlock();
/*     */     }
/*     */ 
/*     */     
/* 276 */     this.blockTarget = null;
/*     */     
/* 278 */     for (Entity entity : (mc.thePlayer.getEntityWorld()).loadedEntityList) {
/* 279 */       if (entity instanceof EntityLivingBase) {
/* 280 */         EntityLivingBase livingBase = (EntityLivingBase)entity;
/* 281 */         if (!EntityUtils.isSelectedForAuraBlockTarget(entity, true)) {
/*     */           continue;
/*     */         }
/* 284 */         this.blockTarget = livingBase;
/*     */       } 
/*     */     } 
/*     */     
/* 288 */     if (((Boolean)this.autoBlock.get()).booleanValue() && 
/* 289 */       this.blockTarget != null && this.target == null && hasSword()) {
/* 290 */       doBlock();
/*     */     }
/*     */     
/* 293 */     this.targets = getTargets();
/*     */     
/* 295 */     updateTargetMode();
/*     */ 
/*     */     
/* 298 */     if (this.targets.isEmpty()) {
/* 299 */       this.target = null;
/*     */     }
/*     */     
/* 302 */     if (!this.targets.isEmpty())
/*     */     {
/* 304 */       if (((Boolean)this.lockMouseOverTarget.get()).booleanValue()) {
/* 305 */         MovingObjectPosition movingObjectPosition = mc.objectMouseOver;
/* 306 */         if (movingObjectPosition != null && movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
/* 307 */           this.target = (EntityLivingBase)movingObjectPosition.entityHit;
/*     */         }
/*     */       } else {
/* 310 */         if (this.index >= this.targets.size()) {
/* 311 */           this.index = 0;
/*     */         }
/*     */         
/* 314 */         this.target = this.targets.get(this.index);
/*     */       } 
/*     */     }
/*     */     
/* 318 */     if (!mc.playerController.isBreakingBlock()) {
/* 319 */       runRotation(event, this.target);
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateTargetMode() {
/* 324 */     if (this.mode.is("Single")) {
/* 325 */       if (mc.thePlayer.ticksExisted % 2 == 0 && this.targets.size() > 1) {
/* 326 */         if (this.target.getDistanceToEntity((Entity)mc.thePlayer) > ((Double)this.range.get()).doubleValue()) {
/* 327 */           this.index++;
/* 328 */         } else if (this.target.isDead) {
/* 329 */           this.index++;
/*     */         } 
/*     */       }
/* 332 */     } else if (this.mode.is("Switch")) {
/* 333 */       if (this.targets.size() > 1 && 
/* 334 */         this.switchTimer.delay((float)((Double)this.switchDelay.get()).longValue())) {
/* 335 */         this.index++;
/* 336 */         this.switchTimer.reset();
/*     */       }
/*     */     
/* 339 */     } else if (this.mode.is("Random")) {
/* 340 */       if (ThreadLocalRandom.current().nextBoolean()) {
/* 341 */         if (this.targets.size() > 1 && 
/* 342 */           this.switchTimer.delay(Math.abs(ThreadLocalRandom.current().nextFloat() * 1000.0F))) {
/* 343 */           this.index++;
/* 344 */           this.switchTimer.reset();
/*     */         }
/*     */       
/*     */       }
/* 348 */       else if (mc.thePlayer.ticksExisted % 2 == 0 && this.targets.size() > 1) {
/* 349 */         if (this.target.getDistanceToEntity((Entity)mc.thePlayer) > ((Double)this.range.get()).doubleValue()) {
/* 350 */           this.index++;
/* 351 */         } else if (this.target.isDead) {
/* 352 */           this.index++;
/*     */         }
/*     */       
/*     */       } 
/* 356 */     } else if ((this.mode.is("MultiNCP") || this.mode.is("MultiInstantly")) && 
/* 357 */       this.targets.size() > 1 && 
/* 358 */       this.switchTimer.delay(1.0F)) {
/* 359 */       this.index++;
/* 360 */       this.switchTimer.reset();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAutoCPS() {
/* 367 */     return Math.abs(this.realCPS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onStrafe(EventStrafe event) {
/* 375 */     if (this.moveControl.is("Off") && !mc.thePlayer.isRiding()) {
/*     */       return;
/*     */     }
/* 378 */     if (((Boolean)this.strafeOnlyGroundValue.get()).booleanValue() && !mc.thePlayer.onGround) {
/*     */       return;
/*     */     }
/* 381 */     if (this.moveControl.is("Strict")) {
/* 382 */       float yaw = this.yaw;
/* 383 */       float strafe = event.getStrafe();
/* 384 */       float forward = event.getForward();
/* 385 */       float friction = event.getFriction();
/*     */       
/* 387 */       float f = strafe * strafe + forward * forward;
/*     */       
/* 389 */       if (f >= 1.0E-4F) {
/* 390 */         f = MathHelper.sqrt_float(f);
/*     */         
/* 392 */         if (f < 1.0F) {
/* 393 */           f = 1.0F;
/*     */         }
/* 395 */         f = friction / f;
/* 396 */         strafe *= f;
/* 397 */         forward *= f;
/*     */         
/* 399 */         float yawSin = MathHelper.sin((float)(yaw * Math.PI / 180.0D));
/* 400 */         float yawCos = MathHelper.cos((float)(yaw * Math.PI / 180.0D));
/*     */         
/* 402 */         mc.thePlayer.motionX += (strafe * yawCos - forward * yawSin);
/* 403 */         mc.thePlayer.motionZ += (forward * yawCos + strafe * yawSin);
/*     */       } 
/* 405 */       event.setCancelled(true);
/* 406 */     } else if (this.moveControl.is("Silent")) {
/* 407 */       RotationUtils.targetRotation.applyStrafeToPlayerForKillAura(event);
/* 408 */       event.setCancelled(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean shouldBlock() {
/* 413 */     return (((Boolean)this.autoBlock.get()).booleanValue() && this.target != null && hasSword() && isEnabled() && 
/* 414 */       !mc.playerController.isBreakingBlock() && !Scaffold.getInstance.isEnabled());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onReload(EventWorldChanged e) {
/* 419 */     if (((Boolean)this.autoDisabled.get()).booleanValue()) {
/* 420 */       ClientNotification.sendClientMessage(getHUDName(), "Auto disable", 2500L, ClientNotification.Type.WARNING);
/* 421 */       setEnabled(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onMotionUpdate(MotionUpdateEvent e) {
/* 428 */     if (Scaffold.getInstance.isEnabled() && ((Boolean)Scaffold.getInstance.combatspoof.get()).booleanValue()) {
/* 429 */       this.target = null;
/* 430 */       this.blockTarget = null;
/* 431 */       this.targets.clear();
/*     */       
/*     */       return;
/*     */     } 
/* 435 */     if (((Boolean)this.requiteMouseDown.get()).booleanValue() && !mc.gameSettings.keyBindAttack.isKeyDown()) {
/* 436 */       this.target = null;
/* 437 */       this.blockTarget = null;
/* 438 */       this.targets.clear();
/*     */       
/*     */       return;
/*     */     } 
/* 442 */     if (this.target != null) {
/*     */       
/* 444 */       if (((Boolean)this.autoCPS.get()).booleanValue()) {
/* 445 */         runAutoCPS();
/*     */       }
/* 447 */       if (this.attackTiming.is("All") || (this.attackTiming
/* 448 */         .is("Pre") && e.getState() == MotionUpdateEvent.State.PRE) || (this.attackTiming
/* 449 */         .is("Post") && e.getState() == MotionUpdateEvent.State.POST)) {
/* 450 */         if (random.nextInt(100) < ((Double)this.mistake.getValueState()).intValue()) {
/* 451 */           sendPacket((Packet)new C0APacketAnimation());
/*     */         } else {
/* 453 */           runAttack();
/*     */         } 
/*     */       }
/* 456 */       if (((Boolean)this.autoBlock.get()).booleanValue() && 
/* 457 */         shouldBlock() && !this.isBlocking && (
/* 458 */         this.autoBlockTiming.is("All") || (this.autoBlockTiming
/* 459 */         .is("Pre") && e.getState() == MotionUpdateEvent.State.PRE) || (this.autoBlockTiming
/* 460 */         .is("Post") && e.getState() == MotionUpdateEvent.State.POST)) && (
/* 461 */         new Random()).nextInt(100) <= ((Double)this.blockRate.getValue()).doubleValue() && (
/* 462 */         !((Boolean)this.hurtBlock.getValue()).booleanValue() || mc.thePlayer.hurtResistantTime > 0)) {
/* 463 */         doBlock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void swing() {
/* 472 */     if (!((Boolean)this.noSwing.get()).booleanValue()) {
/* 473 */       mc.thePlayer.swingItem();
/*     */     } else {
/* 475 */       sendPacket((Packet)new C0APacketAnimation());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void doAttack() {
/* 482 */     if (((Boolean)this.imitateDC.get()).booleanValue() && this.target.hurtResistantTime > 12 && this.target.hurtResistantTime < 17.0D + Math.random() * (Math.random() * 2.0D - 1.0D)) {
/*     */       return;
/*     */     }
/* 485 */     if (((Boolean)this.autoBlock.get()).booleanValue() && 
/* 486 */       hasSword() && this.isBlocking) {
/* 487 */       unBlock();
/*     */     }
/*     */     
/* 490 */     if (((Boolean)this.glich.getValue()).booleanValue()) {
/* 491 */       int beforeHeldItem = mc.thePlayer.inventory.currentItem;
/* 492 */       sendPacketNoEvent((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem = 8));
/* 493 */       sendPacketNoEvent((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem = beforeHeldItem));
/*     */     } 
/*     */     
/* 496 */     if (!this.attacked.contains(this.target) && this.target instanceof EntityPlayer) {
/* 497 */       this.attacked.add(this.target);
/*     */     }
/* 499 */     if (this.mode.is("Multi") || this.mode.is("MultiInstantly")) {
/* 500 */       swing();
/* 501 */       mc.playerController.syncCurrentPlayItem();
/* 502 */       if (this.mode.is("Multi")) {
/* 503 */         if (this.targets.size() > ((Double)this.limitedMultiTargetsValue.get()).intValue()) {
/* 504 */           for (int index = 0; index <= this.targets.size() && 
/* 505 */             index < ((Double)this.limitedMultiTargetsValue.get()).intValue(); index++)
/*     */           {
/*     */             
/* 508 */             if (this.targets.size() == 1 || this.targets.isEmpty()) {
/* 509 */               sendPacketNoEvent((Packet)new C02PacketUseEntity((Entity)this.target, C02PacketUseEntity.Action.ATTACK));
/*     */               break;
/*     */             } 
/* 512 */             if (index < ((Double)this.limitedMultiTargetsValue.get()).doubleValue()) {
/* 513 */               sendPacketNoEvent((Packet)new C02PacketUseEntity((Entity)this.targets.get(index), C02PacketUseEntity.Action.ATTACK));
/*     */             }
/*     */           }
/*     */         
/* 517 */         } else if (this.targets.size() <= ((Double)this.limitedMultiTargetsValue.get()).intValue()) {
/* 518 */           for (EntityLivingBase ent : this.targets) {
/* 519 */             if (mc.thePlayer.getDistanceToEntity((Entity)ent) <= ((Double)this.range.get()).doubleValue()) {
/* 520 */               sendPacketNoEvent((Packet)new C02PacketUseEntity((Entity)ent, C02PacketUseEntity.Action.ATTACK));
/*     */             }
/*     */           } 
/*     */         } 
/* 524 */       } else if (this.mode.is("MultiInstantly")) {
/* 525 */         for (EntityLivingBase ent : this.targets) {
/* 526 */           if (mc.thePlayer.getDistanceToEntity((Entity)ent) <= ((Double)this.range.get()).doubleValue())
/* 527 */             sendPacketNoEvent((Packet)new C02PacketUseEntity((Entity)ent, C02PacketUseEntity.Action.ATTACK)); 
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       boolean canHit;
/* 532 */       EntityLivingBase aacTarget = this.target;
/*     */       
/* 534 */       if (((Boolean)this.rayCast.get()).booleanValue()) {
/* 535 */         double reach = Math.min(((Double)this.range.get()).doubleValue() + ((Double)this.blockRange.get()).doubleValue(), mc.thePlayer.getDistanceToEntity((Entity)aacTarget)) + 1.0D;
/* 536 */         Entity raycastedEntity = RaycastUtils.raycastEntity(reach, this.yaw, this.pitch, entity -> (entity instanceof EntityLivingBase && !(entity instanceof net.minecraft.entity.item.EntityArmorStand) && (!Teams.getInstance.isOnSameTeam(entity) || !mc.theWorld.getEntitiesWithinAABBExcludingEntity(entity, entity.getEntityBoundingBox()).isEmpty())));
/* 537 */         if (raycastedEntity instanceof EntityLivingBase) {
/* 538 */           aacTarget = (EntityLivingBase)raycastedEntity;
/* 539 */           canHit = true;
/*     */         } else {
/* 541 */           canHit = false;
/*     */         } 
/*     */       } else {
/* 544 */         aacTarget = this.target;
/* 545 */         canHit = true;
/*     */       } 
/* 547 */       if (mc.thePlayer.getDistanceToEntity((Entity)aacTarget) <= ((Double)this.range.get()).doubleValue() && canHit) {
/* 548 */         if (!((Boolean)this.rayCast.get()).booleanValue()) {
/* 549 */           swing();
/*     */         }
/* 551 */         else if (canHit) {
/* 552 */           swing();
/*     */         } 
/*     */         
/* 555 */         if (((Boolean)this.keepSprint.get()).booleanValue()) {
/* 556 */           sendPacket((Packet)new C02PacketUseEntity((Entity)aacTarget, C02PacketUseEntity.Action.ATTACK));
/*     */         } else {
/* 558 */           mc.playerController.attackEntity((EntityPlayer)mc.thePlayer, (Entity)aacTarget);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void runAttack() {
/* 565 */     while (this.cps > 0.0D) {
/* 566 */       doAttack();
/* 567 */       this.cps--;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void runAutoCPS() {
/* 573 */     boolean shouldRandom = (this.target.getHealth() > mc.thePlayer.getHealth());
/* 574 */     if (shouldRandom) {
/* 575 */       this.needCPS = MathUtil.getRandomInRange(MathUtil.getRandomInRange(13.0F, 14.0F), MathUtil.getRandomInRange(13.0F, 14.0F));
/*     */     }
/* 577 */     this.realCPS = this.needCPS;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onRender(EventRender2D renderEvent) {
/* 582 */     if (this.target != null && this.attackTimer.isDelayComplete(getCPSForDoAttack())) {
/* 583 */       this.cps++;
/* 584 */       this.attackTimer.reset();
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onLag(PacketEvent e) {
/* 590 */     if (((Boolean)this.autoBlock.get()).booleanValue())
/*     */     {
/* 592 */       if (this.autoBlockMode.is("NCP") && mc.thePlayer.isUsingItem() && (this.target != null || this.blockTarget != null))
/*     */       {
/* 594 */         if (e.getPacket() instanceof net.minecraft.network.play.server.S30PacketWindowItems) {
/* 595 */           e.setCancelled(true);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void doBlock() {
/* 603 */     if (((Boolean)this.autoBlock.get()).booleanValue()) {
/* 604 */       if (this.autoBlockMode.is("Watchdog")) {
/* 605 */         if (NoSlow.getInstance.isEnabled() && NoSlow.getInstance.mode.is("Watchdog")) {
/* 606 */           sendPacket((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
/*     */         } else {
/* 608 */           msg("Watchdog auto block only with NoSlow enable and set mode watchdog else doesn't work");
/*     */         } 
/* 610 */       } else if (this.autoBlockMode.is("KeyBind")) {
/* 611 */         KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
/* 612 */       } else if (this.autoBlockMode.is("Vanilla")) {
/* 613 */         mc.playerController.sendUseItem((EntityPlayer)mc.thePlayer, (World)mc.theWorld, mc.thePlayer.getHeldItem());
/* 614 */         mc.thePlayer.setItemInUse(mc.thePlayer.getHeldItem(), mc.thePlayer.getHeldItem().getMaxItemUseDuration());
/* 615 */       } else if (this.autoBlockMode.is("AAC")) {
/* 616 */         if (mc.thePlayer.ticksExisted % 2 == 0) {
/* 617 */           mc.playerController.interactWithEntitySendPacket((EntityPlayer)mc.thePlayer, (Entity)this.target);
/* 618 */           sendPacket((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
/*     */         } 
/* 620 */       } else if (this.autoBlockMode.is("Packet")) {
/* 621 */         sendPacket((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
/* 622 */       } else if (this.autoBlockMode.is("DCJ")) {
/* 623 */         EntityPlayerSP thePlayer = mc.thePlayer;
/* 624 */         ItemStack item = thePlayer.inventory.getCurrentItem();
/* 625 */         thePlayer.setItemInUse(item, item.getMaxItemUseDuration());
/* 626 */         sendPacketNoEvent((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, item, 0.0F, 0.0F, 0.0F));
/*     */         
/* 628 */         KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
/* 629 */       } else if (this.autoBlockMode.is("OldNCP") || this.autoBlockMode.is("OldIntave")) {
/* 630 */         sendPacket((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
/* 631 */       } else if (this.autoBlockMode.is("NCP")) {
/* 632 */         sendPacket((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer
/* 633 */               .getHeldItem(), 0.0F, 0.0F, 0.0F));
/* 634 */       } else if (this.autoBlockMode.is("OldWatchdog")) {
/* 635 */         sendPacket((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer
/* 636 */               .getHeldItem(), 0.0F, 0.0F, 0.0F));
/*     */       } 
/* 638 */       this.isBlocking = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void unBlock() {
/* 643 */     if (((Boolean)this.autoBlock.get()).booleanValue()) {
/* 644 */       if (this.autoBlockMode.is("Fake") || this.autoBlockMode.is("NCP")) {
/*     */         return;
/*     */       }
/* 647 */       if (hasSword()) {
/* 648 */         double blockvalue = -1.0D;
/* 649 */         if (!isMoving() && ((Boolean)this.dynamic.getValue()).booleanValue()) {
/* 650 */           blockvalue = ThreadLocalRandom.current().nextDouble(-1.0D, -0.2D);
/*     */         }
/* 652 */         if (this.isBlocking) {
/* 653 */           if (this.autoBlockMode.is("KeyBind")) {
/* 654 */             KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
/* 655 */           } else if (this.autoBlockMode.is("OldIntave")) {
/* 656 */             sendPacketNoEvent((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
/* 657 */             sendPacketNoEvent((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
/* 658 */           } else if (this.autoBlockMode.is("Watchdog")) {
/* 659 */             sendPacket((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, 
/* 660 */                   ((Boolean)this.dynamic.getValue()).booleanValue() ? new BlockPos(blockvalue, blockvalue, blockvalue) : BlockPos.ORIGIN, EnumFacing.DOWN));
/*     */           }
/* 662 */           else if (this.autoBlockMode.is("DCJ")) {
/* 663 */             sendPacket((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, 
/* 664 */                   ((Boolean)this.dynamic.getValue()).booleanValue() ? new BlockPos(blockvalue, blockvalue, blockvalue) : BlockPos.ORIGIN, EnumFacing.DOWN));
/*     */             
/* 666 */             mc.thePlayer.stopUsingItem();
/* 667 */             KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
/*     */           } else {
/* 669 */             send((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, 
/* 670 */                   ((Boolean)this.dynamic.getValue()).booleanValue() ? new BlockPos(blockvalue, blockvalue, blockvalue) : BlockPos.ORIGIN, EnumFacing.DOWN));
/*     */           } 
/*     */           
/* 673 */           this.isBlocking = false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void runRotation(EventPreUpdate event, EntityLivingBase target) {
/* 680 */     if (mc.thePlayer == null) {
/*     */       return;
/*     */     }
/* 683 */     if (target != null) {
/* 684 */       float[] arrayOfFloat1; float turnSpeed; boolean Hitble; float[] rots; float[] ncpRotations; float[] hvhRotations; float[] rotations; Rotation limitedRotation; Entity raycastedEntity; float yaw; float f1; float pitch; float[] arrayOfFloat2; float yaw2; float pitch2; Rotation rotation1; switch ((String)this.rotMode.get()) {
/*     */         case "None":
/* 686 */           arrayOfFloat1 = RotationUtil.getAnglesAGC((Entity)target);
/* 687 */           this.yaw = mc.thePlayer.rotationYaw;
/* 688 */           this.pitch = arrayOfFloat1[1];
/* 689 */           event.setYaw(this.yaw);
/* 690 */           event.setPitch(this.pitch);
/*     */           break;
/*     */         
/*     */         case "CustomSpeed":
/* 694 */           turnSpeed = MathUtil.getRandomInRange(((Double)this.minTurnSpeed.get()).floatValue(), ((Double)this.maxTurnSpeed.get()).floatValue());
/* 695 */           limitedRotation = ClientSetting.getInstance.limitAngleChange(new Rotation(this.yaw, this.pitch), new Rotation(ClientSetting.getInstance
/* 696 */                 .getRotation(target)[0], ClientSetting.getInstance
/* 697 */                 .getRotation(target)[1]), turnSpeed);
/* 698 */           limitedRotation.fixedSensitivity(Float.valueOf(mc.gameSettings.mouseSensitivity));
/* 699 */           event.setYaw(this.yaw = limitedRotation.getYaw());
/* 700 */           event.setPitch(this.pitch = limitedRotation.getPitch());
/*     */           break;
/*     */ 
/*     */         
/*     */         case "Reduce":
/* 705 */           raycastedEntity = RaycastUtils.raycastEntity(((Double)this.range.get()).doubleValue(), this.yaw, this.pitch, entity -> 
/* 706 */               (entity instanceof EntityLivingBase && !(entity instanceof net.minecraft.entity.item.EntityArmorStand) && (!Teams.getInstance.isOnSameTeam(entity) || !mc.theWorld.getEntitiesWithinAABBExcludingEntity(entity, entity.getEntityBoundingBox()).isEmpty())));
/*     */           
/* 708 */           Hitble = raycastedEntity instanceof EntityLivingBase;
/* 709 */           f1 = Hitble ? 0.0F : ((mc.thePlayer.ticksExisted % 3 == 1) ? 0.0F : 1000.0F);
/* 710 */           arrayOfFloat2 = RotationUtil.getAnglesAGC((Entity)target);
/* 711 */           yaw2 = arrayOfFloat2[0] + MathUtil.getRandomInRange(-26.8F, 26.8F);
/* 712 */           pitch2 = arrayOfFloat2[1] + MathUtil.getRandomInRange(-26.8F, 26.8F);
/* 713 */           rotation1 = ClientSetting.getInstance.limitAngleChange(new Rotation(this.yaw, this.pitch), new Rotation(yaw2, pitch2), f1);
/*     */           
/* 715 */           rotation1.fixedSensitivity(Float.valueOf(mc.gameSettings.mouseSensitivity));
/*     */           
/* 717 */           event.setYaw(this.yaw = rotation1.getYaw());
/* 718 */           event.setPitch(this.pitch = rotation1.getPitch());
/*     */           break;
/*     */         
/*     */         case "Legit":
/* 722 */           rots = RotationUtil.getAnglesAGC((Entity)target);
/* 723 */           yaw = rots[0] + MathUtil.getRandomInRange(-6.8F, 6.8F);
/* 724 */           pitch = rots[1] + MathUtil.getRandomInRange(-6.8F, 6.8F);
/* 725 */           event.setYaw(this.yaw = yaw);
/* 726 */           event.setPitch(this.pitch = pitch);
/*     */           break;
/*     */         
/*     */         case "NCP":
/*     */         case "Watchdog":
/* 731 */           ncpRotations = RotationUtil.getFluxRotations((Entity)target, (((Double)this.range.get()).intValue() + ((Double)this.blockRange.get()).intValue()));
/* 732 */           event.setYaw(this.yaw = ncpRotations[0]);
/* 733 */           event.setPitch(this.pitch = ncpRotations[1]);
/*     */           break;
/*     */         
/*     */         case "HvH":
/* 737 */           hvhRotations = RotationUtil.getCustomRotation(RotationUtil.getLocation(((EntityLivingBase)this.targets.get(this.index)).getEntityBoundingBox()));
/* 738 */           event.setYaw(this.yaw = hvhRotations[0]);
/* 739 */           event.setPitch(this.pitch = hvhRotations[1]);
/*     */           break;
/*     */ 
/*     */         
/*     */         case "Smooth":
/* 744 */           rotations = getSmoothRotations(target);
/* 745 */           this.yaw = rotations[0];
/* 746 */           this.pitch = rotations[1];
/* 747 */           event.setYaw(this.yaw);
/* 748 */           event.setPitch(this.pitch);
/*     */           break;
/*     */       } 
/*     */       
/* 752 */       if (((Boolean)this.force.getValue()).booleanValue()) {
/* 753 */         sendPacket((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(event.getX(), event.getY(), event
/* 754 */               .getZ(), event.getYaw(), event.getPitch(), event.isOnGround()));
/* 755 */         event.setCancelled(true);
/*     */       } 
/*     */       
/* 758 */       if (((Boolean)this.lockView.get()).booleanValue()) {
/* 759 */         mc.thePlayer.rotationYaw = this.yaw;
/* 760 */         mc.thePlayer.rotationPitch = Math.max(Math.min(this.pitch, 90.0F), -90.0F);
/*     */       } 
/*     */       
/* 763 */       if (((Boolean)this.smoothBack.get()).booleanValue()) {
/* 764 */         this.turnTime = System.currentTimeMillis();
/*     */       }
/*     */     } else {
/* 767 */       if (((Boolean)this.lockView.get()).booleanValue() || !((Boolean)this.smoothBack.get()).booleanValue()) {
/*     */         return;
/*     */       }
/* 770 */       if (System.currentTimeMillis() - this.turnTime < 350L) {
/* 771 */         float[] facesReturn = RotationUtil.getRotateForReturn(this.yaw, this.pitch, 
/* 772 */             MathUtil.getRandomInRange(80.0F, 120.0F));
/* 773 */         this.yaw = facesReturn[0];
/* 774 */         this.pitch = facesReturn[1];
/* 775 */         event.setYaw(this.yaw);
/* 776 */         event.setPitch(this.pitch);
/*     */       } else {
/* 778 */         this.yaw = mc.thePlayer.rotationYaw;
/* 779 */         this.pitch = mc.thePlayer.rotationPitch;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<EntityLivingBase> getTargets() {
/*     */     List<EntityLivingBase> list;
/* 789 */     Stream<EntityLivingBase> stream = mc.theWorld.loadedEntityList.stream().filter(entity -> entity instanceof EntityLivingBase).map(entity -> (EntityLivingBase)entity).filter(getInstance::isValidEntity);
/* 790 */     if (this.priority.isCurrentMode("Armor")) {
/* 791 */       stream = stream.sorted(Comparator.comparingInt(o -> (o instanceof EntityPlayer) ? ((EntityPlayer)o).inventory.getTotalArmorValue() : (int)o.getHealth()));
/*     */     
/*     */     }
/* 794 */     else if (this.priority.isCurrentMode("Range")) {
/* 795 */       stream = stream.sorted((o1, o2) -> (int)(o1.getDistanceToEntity((Entity)mc.thePlayer) - o2.getDistanceToEntity((Entity)mc.thePlayer)));
/*     */     }
/* 797 */     else if (this.priority.isCurrentMode("Fov")) {
/* 798 */       stream = stream.sorted(Comparator.comparingDouble(o -> getDistanceBetweenAngles(mc.thePlayer.rotationPitch, RotationUtils.getRotations(o)[0])));
/*     */     }
/* 800 */     else if (this.priority.isCurrentMode("Angle")) {
/* 801 */       stream = stream.sorted((o1, o2) -> {
/*     */             float[] rot1 = RotationUtils.getRotations(o1);
/*     */             float[] rot2 = RotationUtils.getRotations(o2);
/*     */             return (int)(mc.thePlayer.rotationYaw - rot1[0] - mc.thePlayer.rotationYaw - rot2[0]);
/*     */           });
/* 806 */     } else if (this.priority.isCurrentMode("Health")) {
/* 807 */       stream = stream.sorted((o1, o2) -> (int)(o1.getHealth() - o2.getHealth()));
/* 808 */     } else if (this.priority.isCurrentMode("HurtTime")) {
/* 809 */       stream = stream.sorted(Comparator.comparingInt(o -> 20 - o.hurtResistantTime));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 817 */     if (((Boolean)this.witherPriority.getValue()).booleanValue()) {
/* 818 */       List<EntityLivingBase> sortedList = stream.collect((Collector)Collectors.toList());
/* 819 */       list = new ArrayList<>();
/* 820 */       list.addAll((Collection<? extends EntityLivingBase>)sortedList.stream().filter(entity -> entity instanceof net.minecraft.entity.boss.EntityWither).collect(Collectors.toList()));
/* 821 */       list.addAll((Collection<? extends EntityLivingBase>)sortedList.stream().filter(entity -> !(entity instanceof net.minecraft.entity.boss.EntityWither)).collect(Collectors.toList()));
/*     */     } else {
/* 823 */       list = stream.collect((Collector)Collectors.toList());
/*     */     } 
/*     */ 
/*     */     
/* 827 */     if (Keyboard.isKeyDown(56)) {
/* 828 */       Collections.reverse(list);
/*     */     }
/* 830 */     return list.subList(0, Math.min(list.size(), this.mode.is("Switch") ? ((Double)this.switchSize.get()).intValue() : 1));
/*     */   }
/*     */   
/*     */   private boolean isValidEntity(EntityLivingBase entityLivingBase) {
/* 834 */     return EntityUtils.isSelectedForAura((Entity)entityLivingBase, true);
/*     */   }
/*     */   
/*     */   public float getDistanceBetweenAngles(float angle1, float angle2) {
/* 838 */     float angle = Math.abs(angle1 - angle2) % 360.0F;
/* 839 */     if (angle > 180.0F) {
/* 840 */       angle = 360.0F - angle;
/*     */     }
/*     */     
/* 843 */     return angle;
/*     */   }
/*     */   
/*     */   public long randomClickDelay(double d, double d2) {
/* 847 */     return (long)(Math.random() * (1000.0D / d - 1000.0D / d2 + 1.0D) + 1000.0D / d2);
/*     */   }
/*     */   
/*     */   public EntityLivingBase getTarget() {
/* 851 */     if (VanillaAura.getInstance.isEnabled()) {
/* 852 */       return VanillaAura.getInstance.lastEntity;
/*     */     }
/* 854 */     if (isEnabled() && this.target != null) {
/* 855 */       return this.targets.get(0);
/*     */     }
/* 857 */     if (AdvancedAura.getInstance.isEnabled() && AdvancedAura.currentTarget != null) {
/* 858 */       return AdvancedAura.currentTarget;
/*     */     }
/* 860 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onRender(EventRender3D e) {
/* 867 */     if (((Boolean)this.mark.get()).booleanValue()) {
/* 868 */       if (this.target == null) {
/*     */         return;
/*     */       }
/* 871 */       if (this.markMode.is("Normal")) {
/* 872 */         if (this.mode.is("MultiInstantly")) {
/* 873 */           for (EntityLivingBase ent : this.targets) {
/* 874 */             if (mc.thePlayer.getDistanceToEntity((Entity)ent) <= ((Double)this.range.get()).doubleValue()) {
/* 875 */               ClientSetting.getInstance.normalMark(this.targets.get(this.index));
/*     */             }
/*     */           } 
/* 878 */         } else if (this.mode.is("Multi")) {
/* 879 */           if (this.targets.size() > ((Double)this.limitedMultiTargetsValue.get()).intValue()) {
/* 880 */             for (int index = 0; index <= this.targets.size() && 
/* 881 */               index < ((Double)this.limitedMultiTargetsValue.get()).intValue(); index++)
/*     */             {
/*     */               
/* 884 */               if (this.targets.size() == 1 || this.targets.isEmpty()) {
/* 885 */                 ClientSetting.getInstance.normalMark(this.target);
/*     */                 break;
/*     */               } 
/* 888 */               if (index < ((Double)this.limitedMultiTargetsValue.get()).doubleValue()) {
/* 889 */                 ClientSetting.getInstance.normalMark(this.targets.get(index));
/*     */               }
/*     */             }
/*     */           
/* 893 */           } else if (this.targets.size() <= ((Double)this.limitedMultiTargetsValue.get()).intValue()) {
/* 894 */             for (EntityLivingBase ent : this.targets) {
/* 895 */               if (mc.thePlayer.getDistanceToEntity((Entity)ent) <= ((Double)this.range.get()).doubleValue()) {
/* 896 */                 ClientSetting.getInstance.normalMark(ent);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } else {
/* 901 */           ClientSetting.getInstance.normalMark(this.target);
/*     */         } 
/* 903 */       } else if (this.markMode.is("Circle")) {
/* 904 */         if (this.mode.is("MultiInstantly")) {
/* 905 */           for (EntityLivingBase ent : this.targets) {
/* 906 */             if (mc.thePlayer.getDistanceToEntity((Entity)ent) <= ((Double)this.range.get()).doubleValue()) {
/* 907 */               ClientSetting.getInstance.drawCircle((Entity)this.targets.get(this.index), 0.67D, true);
/*     */             }
/*     */           } 
/* 910 */         } else if (this.mode.is("Multi")) {
/* 911 */           if (this.targets.size() > ((Double)this.limitedMultiTargetsValue.get()).intValue()) {
/* 912 */             for (int index = 0; index <= this.targets.size() && 
/* 913 */               index < ((Double)this.limitedMultiTargetsValue.get()).intValue(); index++)
/*     */             {
/*     */               
/* 916 */               if (this.targets.size() == 1 || this.targets.isEmpty()) {
/* 917 */                 ClientSetting.getInstance.drawCircle((Entity)this.target, 0.67D, true);
/*     */                 break;
/*     */               } 
/* 920 */               if (index < ((Double)this.limitedMultiTargetsValue.get()).doubleValue()) {
/* 921 */                 ClientSetting.getInstance.drawCircle((Entity)this.targets.get(index), 0.67D, true);
/*     */               }
/*     */             }
/*     */           
/* 925 */           } else if (this.targets.size() <= ((Double)this.limitedMultiTargetsValue.get()).intValue()) {
/* 926 */             for (EntityLivingBase ent : this.targets) {
/* 927 */               if (mc.thePlayer.getDistanceToEntity((Entity)ent) <= ((Double)this.range.get()).doubleValue()) {
/* 928 */                 ClientSetting.getInstance.drawCircle((Entity)ent, 0.67D, true);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } else {
/* 933 */           ClientSetting.getInstance.drawCircle((Entity)this.target, 0.67D, true);
/*     */         } 
/* 935 */       } else if (this.markMode.is("Box")) {
/* 936 */         if (this.mode.is("MultiInstantly")) {
/* 937 */           for (EntityLivingBase ent : this.targets) {
/* 938 */             if (mc.thePlayer.getDistanceToEntity((Entity)ent) <= ((Double)this.range.get()).doubleValue()) {
/* 939 */               ClientSetting.getInstance.boxESP(this.targets.get(this.index));
/*     */             }
/*     */           } 
/* 942 */         } else if (this.mode.is("Multi")) {
/* 943 */           if (this.targets.size() > ((Double)this.limitedMultiTargetsValue.get()).intValue()) {
/* 944 */             for (int index = 0; index <= this.targets.size() && 
/* 945 */               index < ((Double)this.limitedMultiTargetsValue.get()).intValue(); index++)
/*     */             {
/*     */               
/* 948 */               if (this.targets.size() == 1 || this.targets.isEmpty()) {
/* 949 */                 ClientSetting.getInstance.boxESP(this.target);
/*     */                 break;
/*     */               } 
/* 952 */               if (index < ((Double)this.limitedMultiTargetsValue.get()).doubleValue()) {
/* 953 */                 ClientSetting.getInstance.boxESP(this.targets.get(index));
/*     */               }
/*     */             }
/*     */           
/* 957 */           } else if (this.targets.size() <= ((Double)this.limitedMultiTargetsValue.get()).intValue()) {
/* 958 */             for (EntityLivingBase ent : this.targets) {
/* 959 */               if (mc.thePlayer.getDistanceToEntity((Entity)ent) <= ((Double)this.range.get()).doubleValue()) {
/* 960 */                 ClientSetting.getInstance.boxESP(ent);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } else {
/* 965 */           ClientSetting.getInstance.boxESP(this.target);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public float[] getSmoothRotations(EntityLivingBase entity) {
/* 972 */     float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
/* 973 */     float fac = f1 * f1 * f1 * 256.0F;
/*     */     
/* 975 */     double x = entity.posX - mc.thePlayer.posX;
/* 976 */     double z = entity.posZ - mc.thePlayer.posZ;
/*     */ 
/*     */ 
/*     */     
/* 980 */     double y = entity.posY + entity.getEyeHeight() - (mc.thePlayer.getEntityBoundingBox()).minY + (mc.thePlayer.getEntityBoundingBox()).maxY - (mc.thePlayer.getEntityBoundingBox()).minY;
/*     */     
/* 982 */     double d3 = MathHelper.sqrt_double(x * x + z * z);
/* 983 */     float yaw2 = (float)(MathHelper.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
/* 984 */     float pitch2 = (float)-(MathHelper.atan2(y, d3) * 180.0D / Math.PI);
/* 985 */     yaw2 = smoothRotation(this.yaw, yaw2, fac * MathUtils.getRandomFloat(0.9F, 1.0F));
/* 986 */     pitch2 = smoothRotation(this.pitch, pitch2, fac * MathUtils.getRandomFloat(0.7F, 1.0F));
/*     */     
/* 988 */     return new float[] { yaw2, pitch2 };
/*     */   }
/*     */   
/*     */   public float smoothRotation(float from, float to, float speed) {
/* 992 */     float f = MathHelper.wrapAngleTo180_float(to - from);
/* 993 */     if (f > speed) {
/* 994 */       f = speed;
/*     */     }
/* 996 */     if (f < -speed) {
/* 997 */       f = -speed;
/*     */     }
/* 999 */     return from + f;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\KillAura.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */