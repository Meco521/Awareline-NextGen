/*     */ package awareline.main.mod.implement.combat.advanced;
/*     */ import awareline.main.event.Event;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.EventManager;
/*     */ import awareline.main.event.events.LBEvents.EventMotion;
/*     */ import awareline.main.event.events.LBEvents.EventMotionUpdate;
/*     */ import awareline.main.event.events.LBEvents.EventWorldChanged;
/*     */ import awareline.main.event.events.world.EventAttack;
/*     */ import awareline.main.event.events.world.moveEvents.EventStrafe;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender3D;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.EntityUtils;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.EventEntityMovement;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.MSTimer;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.TimeUtils;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.liquidbounce.LiquidRender;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.liquidbounce.Rotation;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.liquidbounce.RotationUtils;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*     */ import awareline.main.utility.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.C0DPacketCloseWindow;
/*     */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import org.apache.commons.lang3.RandomUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class AdvancedAura extends Module {
/*  53 */   public final Option<Boolean> playerValue = new Option("Player", Boolean.valueOf(true));
/*  54 */   public final Option<Boolean> invisibleValue = new Option("Invisible", Boolean.valueOf(true));
/*  55 */   public final Option<Boolean> mobsValue = new Option("Mobs", Boolean.valueOf(true));
/*  56 */   public final Option<Boolean> animalsValue = new Option("Animals", Boolean.valueOf(false));
/*  57 */   public final Option<Boolean> deadValue = new Option("Dead", Boolean.valueOf(false));
/*  58 */   public final Option<Boolean> toggleWhenDeadValue = new Option("DisableOnDeath", Boolean.valueOf(true));
/*     */   
/*  60 */   public final Mode<String> priorityValue = new Mode("Priority", new String[] { "Health", "Distance", "Direction", "LivingTime", "Armor" }, "Health");
/*  61 */   public final Mode<String> targetModeValue = new Mode("TargetMode", new String[] { "Single", "Switch", "Multi" }, "Single");
/*  62 */   public final Mode<String> swingValue = new Mode("SwingMode", new String[] { "Normal", "Packet", "None" }, "Normal");
/*     */   
/*  64 */   public final Numbers<Double> maxCPS = new Numbers("MaxCPS", Double.valueOf(10.0D), Double.valueOf(1.0D), Double.valueOf(20.0D), Double.valueOf(1.0D));
/*  65 */   public final Numbers<Double> minCPS = new Numbers("MinCPS", Double.valueOf(8.0D), Double.valueOf(1.0D), Double.valueOf(20.0D), Double.valueOf(1.0D));
/*  66 */   public final Numbers<Double> rangeValue = new Numbers("Range", Double.valueOf(4.2D), Double.valueOf(1.0D), Double.valueOf(10.0D), Double.valueOf(0.01D));
/*  67 */   public final Numbers<Double> hurtTimeValue = new Numbers("HurtTime", Double.valueOf(10.0D), Double.valueOf(0.0D), Double.valueOf(10.0D), Double.valueOf(1.0D));
/*  68 */   public final Mode<String> attackTimingValue = new Mode("AttackTiming", new String[] { "Pre", "Post", "All", "Both" }, "Post");
/*     */   
/*  70 */   public final Numbers<Double> throughWallsRangeValue = new Numbers("ThroughWallsRange", Double.valueOf(1.5D), Double.valueOf(0.0D), Double.valueOf(8.0D), Double.valueOf(0.01D));
/*  71 */   public final Numbers<Double> discoverRangeValue = new Numbers("DiscoverRange", Double.valueOf(6.0D), Double.valueOf(0.0D), Double.valueOf(15.0D), Double.valueOf(0.01D));
/*  72 */   public final Numbers<Double> rangeSprintReducementValue = new Numbers("RangeSprintReducement", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(0.4D), Double.valueOf(0.01D));
/*     */   
/*  74 */   public final Mode<String> autoBlockValue = new Mode("AutoBlockMode", new String[] { "Range", "Fake", "Off" }, "Range");
/*  75 */   public final Numbers<Double> autoBlockRangeValue = new Numbers("AutoBlockRange", Double.valueOf(2.5D), Double.valueOf(0.0D), Double.valueOf(8.0D), Double.valueOf(0.01D));
/*  76 */   public final Mode<String> autoBlockPacketValue = new Mode("AutoBlockPacketMode", new String[] { "AfterTick", "AfterAttack", "Vanilla", "Hypixel" }, "AfterAttack");
/*  77 */   public final Option<Boolean> interactAutoBlockValue = new Option("InteractAutoBlock", Boolean.valueOf(true));
/*  78 */   public final Numbers<Double> blockRate = new Numbers("BlockRate", Double.valueOf(100.0D), Double.valueOf(1.0D), Double.valueOf(100.0D), Double.valueOf(1.0D));
/*  79 */   public final Option<Boolean> combatDelayValue = new Option("1.9CombatDelay", Boolean.valueOf(false));
/*  80 */   public final Option<Boolean> keepSprintValue = new Option("KeepSprint", Boolean.valueOf(true));
/*     */   
/*  82 */   public final Option<Boolean> raycastValue = new Option("RayCast", Boolean.valueOf(true));
/*  83 */   public final Option<Boolean> raycastIgnoredValue = new Option("RayCastIgnored", Boolean.valueOf(false));
/*  84 */   public final Option<Boolean> livingRaycastValue = new Option("LivingRayCast", Boolean.valueOf(true));
/*  85 */   public final Option<Boolean> aacValue = new Option("AAC", Boolean.valueOf(true));
/*  86 */   public final Mode<String> rotationModeValue = new Mode("RotationMode", new String[] { "None", "Normal", "Hypixel", "Nya", "OldMatrix" }, "Normal");
/*     */   
/*  88 */   public final Numbers<Double> maxTurnSpeed = new Numbers("MaxTurnSpeed", Double.valueOf(180.0D), Double.valueOf(0.0D), Double.valueOf(180.0D), Double.valueOf(1.0D));
/*  89 */   public final Numbers<Double> minTurnSpeed = new Numbers("MinTurnSpeed", Double.valueOf(180.0D), Double.valueOf(0.0D), Double.valueOf(180.0D), Double.valueOf(1.0D));
/*  90 */   public final Option<Boolean> silentRotationValue = new Option("SilentRotation", Boolean.valueOf(true));
/*  91 */   public final Mode<String> rotationStrafeValue = new Mode("Strafe", new String[] { "Off", "Strict", "Silent" }, "Silent");
/*  92 */   public final Option<Boolean> strafeOnlyGroundValue = new Option("StrafeOnlyGround", Boolean.valueOf(true));
/*  93 */   public final Option<Boolean> randomCenterValue = new Option("RandomCenter", Boolean.valueOf(false));
/*  94 */   public final Option<Boolean> outborderValue = new Option("Outborder", Boolean.valueOf(false));
/*  95 */   public final Option<Boolean> hitableValue = new Option("AlwaysHitable", Boolean.valueOf(true));
/*  96 */   public final Numbers<Double> fovValue = new Numbers("FOV", Double.valueOf(180.0D), Double.valueOf(0.0D), Double.valueOf(180.0D), Double.valueOf(0.01D));
/*     */   
/*  98 */   public final Option<Boolean> predictValue = new Option("Predict", Boolean.valueOf(true));
/*  99 */   public final Numbers<Double> maxPredictSize = new Numbers("MaxPredictSize", Double.valueOf(1.0D), Double.valueOf(0.1D), Double.valueOf(5.0D), Double.valueOf(0.01D));
/* 100 */   public final Numbers<Double> minPredictSize = new Numbers("MinPredictSize", Double.valueOf(1.0D), Double.valueOf(0.1D), Double.valueOf(5.0D), Double.valueOf(0.01D));
/*     */   
/* 102 */   public final Numbers<Double> failRateValue = new Numbers("FailRate", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(100.0D), Double.valueOf(0.01D));
/* 103 */   public final Option<Boolean> fakeSwingValue = new Option("FakeSwing", Boolean.valueOf(true));
/* 104 */   public final Option<Boolean> noInventoryAttackValue = new Option("NoInvAttack", Boolean.valueOf(false));
/* 105 */   public final Numbers<Double> noInventoryDelayValue = new Numbers("NoInvDelay", Double.valueOf(200.0D), Double.valueOf(0.0D), Double.valueOf(500.0D), Double.valueOf(1.0D));
/* 106 */   public final Numbers<Double> switchDelayValue = new Numbers("SwitchDelay", Double.valueOf(300.0D), Double.valueOf(1.0D), Double.valueOf(2000.0D), Double.valueOf(1.0D));
/* 107 */   public final Numbers<Double> limitedMultiTargetsValue = new Numbers("LimitedMultiTargets", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(50.0D), Double.valueOf(1.0D));
/*     */   
/* 109 */   public final Mode<String> markValue = new Mode("Esp", new String[] { "Liquid", "Jello", "Plat", "None", "Distance" }, "Distance");
/*     */ 
/*     */   
/*     */   EntityLivingBase target;
/*     */   
/* 114 */   private final MSTimer markTimer = new MSTimer();
/*     */   public static EntityLivingBase currentTarget;
/*     */   private boolean hitable;
/* 117 */   private final CopyOnWriteArrayList<Integer> prevTargetEntities = new CopyOnWriteArrayList<>();
/* 118 */   private final CopyOnWriteArrayList<EntityLivingBase> discoveredTargets = new CopyOnWriteArrayList<>();
/* 119 */   private final CopyOnWriteArrayList<EntityLivingBase> inRangeDiscoveredTargets = new CopyOnWriteArrayList<>();
/*     */ 
/*     */   
/* 122 */   private final MSTimer attackTimer = new MSTimer();
/* 123 */   private final MSTimer switchTimer = new MSTimer();
/*     */   
/*     */   private long attackDelay;
/*     */   
/*     */   private int clicks;
/*     */   
/* 129 */   private final MSTimer swingTimer = new MSTimer();
/*     */   
/*     */   private long swingDelay;
/*     */   
/* 133 */   private long containerOpen = -1L;
/*     */   public boolean blockingStatus;
/*     */   public static AdvancedAura getInstance;
/*     */   public final TimeUtils timeUtils;
/*     */   public float lastYaw;
/*     */   public float lastPitch;
/*     */   
/* 140 */   public AdvancedAura() { super("AdvancedAura", ModuleType.Combat);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 465 */     this.timeUtils = new TimeUtils(); addSettings(new Value[] { (Value)this.playerValue, (Value)this.invisibleValue, (Value)this.mobsValue, (Value)this.animalsValue, (Value)this.deadValue, (Value)this.toggleWhenDeadValue, (Value)this.priorityValue, (Value)this.targetModeValue, (Value)this.swingValue, (Value)this.combatDelayValue, (Value)this.maxCPS, (Value)this.minCPS, (Value)this.attackTimingValue, (Value)this.rangeValue, (Value)this.hurtTimeValue, (Value)this.throughWallsRangeValue, (Value)this.discoverRangeValue, (Value)this.rangeSprintReducementValue, (Value)this.autoBlockValue, (Value)this.autoBlockRangeValue, (Value)this.autoBlockPacketValue, (Value)this.interactAutoBlockValue, (Value)this.blockRate, (Value)this.keepSprintValue, (Value)this.raycastValue, (Value)this.raycastIgnoredValue, (Value)this.livingRaycastValue, (Value)this.aacValue, (Value)this.rotationModeValue, (Value)this.maxTurnSpeed, (Value)this.minTurnSpeed, (Value)this.silentRotationValue, (Value)this.rotationStrafeValue, (Value)this.strafeOnlyGroundValue, (Value)this.randomCenterValue, (Value)this.outborderValue, (Value)this.hitableValue, (Value)this.fovValue, (Value)this.predictValue, (Value)this.maxPredictSize, (Value)this.minPredictSize, (Value)this.failRateValue, (Value)this.fakeSwingValue, (Value)this.noInventoryAttackValue, (Value)this.noInventoryDelayValue, (Value)this.switchDelayValue, (Value)this.limitedMultiTargetsValue, (Value)this.markValue }); getInstance = this; } @EventHandler public void onWorldChange(EventWorldChanged e) { if (((Boolean)this.toggleWhenDeadValue.getValue()).booleanValue()) { ClientNotification.sendClientMessage("CustomAura", "Auto disable", 4000L, ClientNotification.Type.WARNING); setEnabled(false); }  } public void onEnable() { if (mc.thePlayer == null) return;  if (mc.theWorld == null) return;  updateTarget(); } public void onDisable() { this.target = null; currentTarget = null; this.hitable = false; this.prevTargetEntities.clear(); this.attackTimer.reset(); this.clicks = 0; stopBlocking(); }
/*     */   @EventHandler public void onMotion(EventMotion event) { if (mc.thePlayer.isRiding()) return;  if (this.attackTimingValue.is("All") || this.attackTimingValue.is("Both") || (this.attackTimingValue.is("Pre") && event.getTypes() == EventMotion.Type.PRE) || (this.attackTimingValue.is("Post") && event.getTypes() == EventMotion.Type.POST)) runAttackLoop();  if (!event.isPre()) { if (!this.autoBlockValue.is("Off") && !this.discoveredTargets.isEmpty() && (!this.autoBlockPacketValue.is("AfterAttack") || this.discoveredTargets.stream().anyMatch(it -> (mc.thePlayer.getDistanceToEntity((Entity)it) > getMaxRange()))) && canBlock()) { EntityLivingBase target = this.discoveredTargets.get(0); if (mc.thePlayer.getDistanceToEntity((Entity)target) < ((Double)this.autoBlockRangeValue.get()).doubleValue()) startBlocking((Entity)target, (((Boolean)this.interactAutoBlockValue.get()).booleanValue() && mc.thePlayer.getDistanceToEntity((Entity)target) < getMaxRange()));  }  updateHitable(); return; }  if (this.rotationStrafeValue.is("Off")) update();  }
/*     */   private void runAttackLoop() { boolean canSwing = false; if (this.clicks <= 0 && canSwing && this.swingTimer.hasTimePassed(this.swingDelay)) { this.swingTimer.reset(); this.swingDelay = getAttackDelay(((Double)this.minCPS.get()).intValue(), ((Double)this.maxCPS.get()).intValue()); runSwing(); return; }  while (this.clicks > 0) { runAttack(); this.clicks--; }  }
/* 468 */   private long getAttackDelay(int maxCps, int minCps) { long delay = this.timeUtils.randomClickDelay(Math.min(minCps, maxCps), Math.max(minCps, maxCps));
/* 469 */     if (((Boolean)this.combatDelayValue.get()).booleanValue()) {
/* 470 */       double value = 4.0D;
/* 471 */       if (mc.thePlayer.inventory.getCurrentItem() != null) {
/* 472 */         Item currentItem = mc.thePlayer.inventory.getCurrentItem().getItem();
/* 473 */         if (currentItem instanceof net.minecraft.item.ItemSword) {
/* 474 */           value -= 2.4D;
/* 475 */         } else if (currentItem instanceof net.minecraft.item.ItemPickaxe) {
/* 476 */           value -= 2.8D;
/* 477 */         } else if (currentItem instanceof net.minecraft.item.ItemAxe) {
/* 478 */           value -= 3.0D;
/*     */         } 
/*     */       } 
/* 481 */       delay = (long)Math.max(delay, 1000.0D / value);
/*     */     } 
/* 483 */     return delay; } private void runSwing() { switch (this.swingValue.getModeAsString()) { case "Packet": mc.getNetHandler().addToSendQueue((Packet)new C0APacketAnimation()); break;
/*     */       case "Normal": mc.thePlayer.swingItem(); break; }  }
/*     */   @EventHandler public void onStrafe(EventStrafe event) { if (this.rotationStrafeValue.is("Off") && !mc.thePlayer.isRiding())
/*     */       return;  update(); if (((Boolean)this.strafeOnlyGroundValue.get()).booleanValue() && !mc.thePlayer.onGround)
/*     */       return;  if (!this.discoveredTargets.isEmpty() && RotationUtils.targetRotation != null) { float yaw; float strafe; float forward; float friction; float f; switch (this.rotationStrafeValue.getModeAsString()) { case "Strict": if (RotationUtils.targetRotation == null)
/*     */             return;  yaw = RotationUtils.targetRotation.getYaw(); strafe = event.getStrafe(); forward = event.getForward(); friction = event.getFriction(); f = strafe * strafe + forward * forward; if (f >= 1.0E-4F) { f = MathHelper.sqrt_float(f); if (f < 1.0F)
/*     */               f = 1.0F;  f = friction / f; strafe *= f; forward *= f; float yawSin = MathHelper.sin((float)(yaw * Math.PI / 180.0D)); float yawCos = MathHelper.cos((float)(yaw * Math.PI / 180.0D)); mc.thePlayer.motionX += (strafe * yawCos - forward * yawSin); mc.thePlayer.motionZ += (forward * yawCos + strafe * yawSin); }  event.setCancelled(true); break;
/*     */         case "Silent": update(); RotationUtils.targetRotation.applyStrafeToPlayer(event); event.setCancelled(true); break; }  }  }
/* 491 */   private void updateTarget() { this.target = null;
/*     */ 
/*     */     
/* 494 */     Double hurtTime = (Double)this.hurtTimeValue.get();
/* 495 */     Double fov = (Double)this.fovValue.get();
/* 496 */     boolean switchMode = this.targetModeValue.is("Switch");
/*     */ 
/*     */     
/* 499 */     this.discoveredTargets.clear();
/*     */     
/* 501 */     for (Entity entity : mc.theWorld.loadedEntityList) {
/* 502 */       if (!(entity instanceof EntityLivingBase) || !EntityUtils.isSelected(entity, true) || (switchMode && this.prevTargetEntities.contains(Integer.valueOf(entity.getEntityId())))) {
/*     */         continue;
/*     */       }
/* 505 */       float distance = mc.thePlayer.getDistanceToEntity(entity);
/* 506 */       double entityFov = RotationUtils.getRotationDifference(entity);
/*     */       
/* 508 */       if (distance <= ((Double)this.discoverRangeValue.get()).doubleValue() && (fov.doubleValue() == 180.0D || entityFov <= fov.doubleValue()) && ((EntityLivingBase)entity).hurtTime <= hurtTime.doubleValue()) {
/* 509 */         this.discoveredTargets.add((EntityLivingBase)entity);
/*     */       }
/*     */     } 
/*     */     
/* 513 */     switch (this.priorityValue.getModeAsString()) {
/*     */       case "Armor":
/* 515 */         this.discoveredTargets.sort(Comparator.comparing(EntityLivingBase::getTotalArmorValue));
/*     */         break;
/*     */       case "Distance":
/* 518 */         this.discoveredTargets.sort(Comparator.comparingDouble(o1 -> mc.thePlayer.getDistanceToEntity((Entity)o1)));
/*     */         break;
/*     */       case "Health":
/* 521 */         this.discoveredTargets.sort(Comparator.comparingDouble(EntityLivingBase::getHealth));
/*     */         break;
/*     */       case "Direction":
/* 524 */         this.discoveredTargets.sort(Comparator.comparingDouble(RotationUtils::getRotationDifference));
/*     */         break;
/*     */       case "LivingTime":
/* 527 */         this.discoveredTargets.sort(Comparator.comparing(o1 -> Integer.valueOf(-o1.ticksExisted)));
/*     */         break;
/*     */     } 
/*     */     
/* 531 */     this.inRangeDiscoveredTargets.clear();
/* 532 */     this.inRangeDiscoveredTargets.addAll((Collection<? extends EntityLivingBase>)this.discoveredTargets.stream().filter(it -> (mc.thePlayer.getDistanceToEntity((Entity)it) < getRange((Entity)it))).collect(Collectors.toList()));
/*     */ 
/*     */     
/* 535 */     if (this.inRangeDiscoveredTargets.isEmpty() && !this.prevTargetEntities.isEmpty()) {
/* 536 */       this.prevTargetEntities.clear();
/* 537 */       updateTarget();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 542 */     for (EntityLivingBase entity : this.discoveredTargets) {
/*     */       
/* 544 */       if (!updateRotations((Entity)entity)) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 549 */       if (mc.thePlayer.getDistanceToEntity((Entity)entity) < getMaxRange())
/* 550 */         this.target = entity;  return;
/*     */     }  }
/*     */   @EventHandler public void onUpdate(EventMotionUpdate event) { if (cancelRun()) {
/*     */       this.target = null; currentTarget = null; this.hitable = false; stopBlocking(); this.discoveredTargets.clear(); this.inRangeDiscoveredTargets.clear(); return;
/*     */     }  if (((Boolean)this.noInventoryAttackValue.get()).booleanValue() && (mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiContainer || (System.currentTimeMillis() - this.containerOpen) < ((Double)this.noInventoryDelayValue.get()).doubleValue())) {
/*     */       this.target = null; currentTarget = null; this.hitable = false; if (mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiContainer)
/*     */         this.containerOpen = System.currentTimeMillis();  return;
/*     */     }  if (!this.rotationStrafeValue.is("Off") && !mc.thePlayer.isRiding())
/*     */       return; 
/*     */     if (mc.thePlayer.isRiding())
/*     */       update(); 
/*     */     if (this.attackTimingValue.is("All"))
/* 562 */       runAttackLoop();  } private boolean canBlock() { return (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword); }
/*     */   @EventHandler public void onRender3D(EventRender3D event) { setSuffix((Serializable)this.targetModeValue.getValue()); if (cancelRun()) { this.target = null; currentTarget = null; this.hitable = false; stopBlocking(); this.discoveredTargets.clear(); this.inRangeDiscoveredTargets.clear(); }  if (currentTarget != null && this.attackTimer.hasTimePassed(this.attackDelay) && currentTarget.hurtTime <= ((Double)this.hurtTimeValue.get()).doubleValue()) { this.clicks++; this.attackTimer.reset(); this.attackDelay = getAttackDelay(((Double)this.minCPS.get()).intValue(), ((Double)this.maxCPS.get()).intValue()); }  if (!this.discoveredTargets.isEmpty()) { float red1, green1, blue1, lineRed1, lineGreen1, lineBlue1; double x1, y1, z1, width1, height1; int drawTime; boolean drawMode; double drawPercent; CopyOnWriteArrayList<Vec3> points; AxisAlignedBB bb; double radius, height, posX, posY, posZ; int i; double baseMove, min; int j; Entity it = (Entity)this.discoveredTargets.get(0); EntityLivingBase entity = this.discoveredTargets.get(0); switch (this.markValue.getModeAsString()) { case "Liquid": LiquidRender.drawPlatform((Entity)entity, (entity.hurtResistantTime <= 0) ? new Color(37, 126, 255, 170) : new Color(255, 0, 0, 170)); break;case "Distance": red1 = (entity.hurtTime > 0) ? 1.0F : 0.0F; green1 = (entity.hurtTime > 0) ? 0.2F : 0.6F; blue1 = 1.0F; lineRed1 = (entity.hurtTime > 0) ? 1.0F : 0.0F; lineGreen1 = (entity.hurtTime > 0) ? 0.2F : 0.6F; lineBlue1 = 1.0F; x1 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosX; y1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosY; z1 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosZ; width1 = (entity.getEntityBoundingBox()).maxX - (entity.getEntityBoundingBox()).minX - 0.1D; height1 = (entity.getEntityBoundingBox()).maxY - (entity.getEntityBoundingBox()).minY + 0.2D; RenderUtil.drawEntityESP(x1, y1, z1, width1, height1, red1, green1, blue1, 0.2F, lineRed1, lineGreen1, lineBlue1, 0.0F, 4.0F); break;case "Plat": LiquidRender.drawPlatform((Entity)entity, this.hitable ? new Color(37, 126, 255, 70) : new Color(255, 0, 0, 70)); break;case "Jello": drawTime = (int)(System.currentTimeMillis() % 2000L); drawMode = (drawTime > 1000); drawPercent = drawTime / 1000.0D; if (!drawMode) { drawPercent = 1.0D - drawPercent; } else { drawPercent--; }  drawPercent = EaseUtils.easeInOutQuad(drawPercent); points = new CopyOnWriteArrayList<>(); bb = it.getEntityBoundingBox(); radius = bb.maxX - bb.minX; height = bb.maxY - bb.minY; posX = it.lastTickPosX + (it.posX - it.lastTickPosX) * mc.timer.renderPartialTicks; posY = it.lastTickPosY + (it.posY - it.lastTickPosY) * mc.timer.renderPartialTicks; if (drawMode) { posY -= 0.5D; } else { posY += 0.5D; }  posZ = it.lastTickPosZ + (it.posZ - it.lastTickPosZ) * mc.timer.renderPartialTicks; for (i = 0; i <= 360; i += 7) points.add(new Vec3(posX - Math.sin(i * Math.PI / 180.0D) * radius, posY + height * drawPercent, posZ + Math.cos(i * Math.PI / 180.0D) * radius));  points.add(points.get(0)); mc.entityRenderer.disableLightmap(); GL11.glPushMatrix(); GL11.glDisable(3553); GL11.glBlendFunc(770, 771); GL11.glEnable(2848); GL11.glEnable(3042); GL11.glDisable(2929); GL11.glBegin(3); baseMove = ((drawPercent > 0.5D) ? (1.0D - drawPercent) : drawPercent) * 2.0D; min = height / 60.0D * 20.0D * (1.0D - baseMove) * (drawMode ? -1 : true); for (j = 0; j <= 20; j++) { double moveFace = height / 60.0D * j * baseMove; if (drawMode)
/*     */               moveFace = -moveFace;  Vec3 firstPoint = points.get(0); GL11.glVertex3d(firstPoint.xCoord - (mc.getRenderManager()).viewerPosX, firstPoint.yCoord - moveFace - min - (mc.getRenderManager()).viewerPosY, firstPoint.zCoord - (mc.getRenderManager()).viewerPosZ); GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.7F * j / 20.0F); for (Vec3 vec3 : points)
/*     */               GL11.glVertex3d(vec3.xCoord - (mc.getRenderManager()).viewerPosX, vec3.yCoord - moveFace - min - (mc.getRenderManager()).viewerPosY, vec3.zCoord - (mc.getRenderManager()).viewerPosZ);  GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F); }  GL11.glEnd(); GL11.glEnable(2929); GL11.glDisable(2848); GL11.glEnable(3553); GL11.glPopMatrix(); break; }  }  }
/*     */   @EventHandler public void onEntityMove(EventEntityMovement event) { Entity movedEntity = event.getMovedEntity(); if (this.target == null || movedEntity != currentTarget)
/* 567 */       return;  updateHitable(); } private float getRange(Entity entity) { float range; if (mc.thePlayer.getDistanceToEntity(entity) >= ((Double)this.throughWallsRangeValue.get()).doubleValue()) {
/* 568 */       range = ((Double)this.rangeValue.get()).floatValue();
/*     */     } else {
/* 570 */       range = ((Double)this.throughWallsRangeValue.get()).floatValue();
/*     */     } 
/* 572 */     float sprint = mc.thePlayer.isSprinting() ? ((Double)this.rangeSprintReducementValue.get()).floatValue() : 0.0F;
/* 573 */     return range - sprint; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean updateRotations(Entity entity) {
/* 580 */     if (((Double)this.maxTurnSpeed.get()).doubleValue() <= 0.0D) {
/* 581 */       return true;
/*     */     }
/* 583 */     AxisAlignedBB boundingBox = entity.getEntityBoundingBox();
/*     */     
/* 585 */     if (((Boolean)this.predictValue.get()).booleanValue()) {
/* 586 */       boundingBox = boundingBox.offset((entity.posX - entity.prevPosX) * 
/* 587 */           RandomUtils.nextFloat(((Double)this.minPredictSize.get()).floatValue(), ((Double)this.maxPredictSize.get()).floatValue()), (entity.posY - entity.prevPosY) * 
/* 588 */           RandomUtils.nextFloat(((Double)this.minPredictSize.get()).floatValue(), ((Double)this.maxPredictSize.get()).floatValue()), (entity.posZ - entity.prevPosZ) * 
/* 589 */           RandomUtils.nextFloat(((Double)this.minPredictSize.get()).floatValue(), ((Double)this.maxPredictSize.get()).floatValue()));
/*     */     }
/*     */     
/* 592 */     Rotation rotation = new Rotation(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch); try {
/*     */       Rotation limitedRotation;
/* 594 */       switch (this.rotationModeValue.getModeAsString()) {
/*     */         
/*     */         case "Nya":
/* 597 */           rotation = RotationUtils.searchCenterLnk(boundingBox, (mc.thePlayer.getDistanceToEntity(entity) < ((Double)this.throughWallsRangeValue.get()).doubleValue()), (float)getMaxRange()).getRotation();
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case "Normal":
/* 607 */           rotation = RotationUtils.searchCenter(boundingBox, (((Boolean)this.outborderValue.get()).booleanValue() && !this.attackTimer.hasTimePassed(this.attackDelay / 2L)), ((Boolean)this.randomCenterValue.get()).booleanValue(), ((Boolean)this.predictValue.get()).booleanValue(), (mc.thePlayer.getDistanceToEntity(entity) < ((Double)this.throughWallsRangeValue.get()).doubleValue()), (float)getMaxRange()).getRotation();
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case "OldMatrix":
/* 619 */           rotation = RotationUtils.calculateCenter("CenterLine", "Off", 0.0D, boundingBox, ((Boolean)this.predictValue.get()).booleanValue(), (mc.thePlayer.getDistanceToEntity(entity) <= ((Double)this.throughWallsRangeValue.get()).doubleValue())).getRotation();
/* 620 */           rotation.setPitch(89.9F);
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 625 */       if (this.rotationModeValue.is("Hypixel")) {
/* 626 */         float[] rot = RotationUtils.rotateNCP(entity);
/* 627 */         limitedRotation = new Rotation(rot[0], rot[1]);
/* 628 */       } else if (this.rotationModeValue.is("OldMatrix")) {
/* 629 */         double diffAngle = RotationUtils.getRotationDifference(RotationUtils.serverRotation, rotation);
/* 630 */         if (diffAngle < 0.0D) diffAngle = -diffAngle; 
/* 631 */         if (diffAngle > 180.0D) diffAngle = 180.0D; 
/* 632 */         limitedRotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, rotation, (float)((float)(-Math.cos(diffAngle / 180.0D * Math.PI) * 0.5D + 0.5D) * ((Double)this.maxTurnSpeed.get()).doubleValue() + (Math.cos(diffAngle / 180.0D * Math.PI) * 0.5D + 0.5D) * ((Double)this.minTurnSpeed.get()).doubleValue()));
/*     */       } else {
/* 634 */         limitedRotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, rotation, 
/* 635 */             (float)(Math.random() * (((Double)this.maxTurnSpeed.get()).doubleValue() - ((Double)this.minTurnSpeed.get()).doubleValue()) + ((Double)this.minTurnSpeed.get()).doubleValue()));
/*     */       } 
/* 637 */       if (((Boolean)this.silentRotationValue.get()).booleanValue()) {
/* 638 */         RotationUtils.setTargetRotation(limitedRotation, ((Boolean)this.aacValue.get()).booleanValue() ? 15 : 0);
/*     */       } else {
/* 640 */         limitedRotation.toPlayer((EntityPlayer)mc.thePlayer);
/*     */       } 
/* 642 */       this.lastYaw = limitedRotation.getYaw();
/* 643 */       this.lastPitch = limitedRotation.getPitch();
/*     */       
/* 645 */       return true;
/* 646 */     } catch (NullPointerException e) {
/* 647 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startBlocking(Entity interactEntity, boolean interact) {
/* 657 */     if (this.autoBlockValue.is("Range") && mc.thePlayer.getDistanceToEntity(interactEntity) > ((Double)this.autoBlockRangeValue.get()).doubleValue()) {
/*     */       return;
/*     */     }
/* 660 */     if (this.blockingStatus) {
/*     */       return;
/*     */     }
/* 663 */     if (interact) {
/* 664 */       mc.getNetHandler().addToSendQueue((Packet)new C02PacketUseEntity(interactEntity, interactEntity.getPositionVector()));
/* 665 */       mc.getNetHandler().addToSendQueue((Packet)new C02PacketUseEntity(interactEntity, C02PacketUseEntity.Action.INTERACT));
/*     */     } 
/* 667 */     mc.getNetHandler().addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
/* 668 */     this.blockingStatus = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void stopBlocking() {
/* 675 */     if (this.blockingStatus) {
/* 676 */       mc.getNetHandler().addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, this.autoBlockPacketValue.is("Hypixel") ? new BlockPos(-1, -1, -1) : BlockPos.ORIGIN, EnumFacing.DOWN));
/* 677 */       this.blockingStatus = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateHitable() {
/* 685 */     if (((Boolean)this.hitableValue.get()).booleanValue()) {
/* 686 */       this.hitable = true;
/*     */       
/*     */       return;
/*     */     } 
/* 690 */     if (((Double)this.maxTurnSpeed.get()).doubleValue() <= 0.0D) {
/* 691 */       this.hitable = true;
/*     */       
/*     */       return;
/*     */     } 
/* 695 */     double reach = getMaxRange();
/*     */     
/* 697 */     if (((Boolean)this.raycastValue.get()).booleanValue()) {
/* 698 */       Entity raycastedEntity = RaycastUtils.raycastEntity(reach, it -> (!((Boolean)this.livingRaycastValue.get()).booleanValue() || (it instanceof EntityLivingBase && !(it instanceof net.minecraft.entity.item.EntityArmorStand) && (EntityUtils.isSelected(it, true) || ((Boolean)this.raycastIgnoredValue.get()).booleanValue() || (((Boolean)this.aacValue.get()).booleanValue() && !mc.theWorld.getEntitiesWithinAABBExcludingEntity(it, it.getEntityBoundingBox()).isEmpty())))));
/*     */ 
/*     */       
/* 701 */       if (((Boolean)this.raycastValue.get()).booleanValue() && raycastedEntity instanceof EntityLivingBase && 
/* 702 */         !EntityUtils.isFriend(raycastedEntity)) {
/* 703 */         currentTarget = (EntityLivingBase)raycastedEntity;
/*     */       }
/* 705 */       this.hitable = (((Double)this.maxTurnSpeed.get()).doubleValue() <= 0.0D || currentTarget == raycastedEntity);
/*     */     } else {
/* 707 */       this.hitable = RotationUtils.isFaced((Entity)currentTarget, reach);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void update() {
/* 712 */     if (cancelRun() || (((Boolean)this.noInventoryAttackValue.get()).booleanValue() && (mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiContainer || (
/* 713 */       System.currentTimeMillis() - this.containerOpen) < ((Double)this.noInventoryDelayValue.get()).doubleValue()))) {
/*     */       return;
/*     */     }
/*     */     
/* 717 */     updateTarget();
/*     */     
/* 719 */     if (this.discoveredTargets.isEmpty()) {
/* 720 */       stopBlocking();
/* 721 */       currentTarget = null;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 726 */     currentTarget = this.target;
/*     */     
/* 728 */     if (!this.targetModeValue.is("Switch") && EntityUtils.isSelected((Entity)currentTarget, true))
/* 729 */       this.target = currentTarget; 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onUpdate(EventPreUpdate e) {
/* 734 */     if (mc.thePlayer != null) {
/* 735 */       e.setYaw(this.lastYaw);
/* 736 */       e.setPitch(this.lastPitch);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void runAttack() {
/* 744 */     if (this.target == null)
/* 745 */       return;  if (currentTarget == null) {
/*     */       return;
/*     */     }
/* 748 */     double failRate = ((Double)this.failRateValue.get()).doubleValue();
/* 749 */     boolean openInventory = (((Boolean)this.aacValue.get()).booleanValue() && mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory);
/* 750 */     boolean failHit = (failRate > 0.0D && (new Random()).nextInt(100) <= failRate);
/*     */     
/* 752 */     if (this.hitable && !failHit) {
/*     */       
/* 754 */       if (openInventory) {
/* 755 */         mc.getNetHandler().addToSendQueue((Packet)new C0DPacketCloseWindow());
/*     */       }
/*     */ 
/*     */       
/* 759 */       if (this.autoBlockPacketValue.is("Hypixel")) {
/* 760 */         stopBlocking();
/*     */       }
/*     */ 
/*     */       
/* 764 */       if (!this.targetModeValue.is("Multi")) {
/* 765 */         attackEntity(currentTarget);
/*     */       } else {
/* 767 */         for (int index = 0; index <= this.inRangeDiscoveredTargets.size() && 
/* 768 */           index < ((Double)this.limitedMultiTargetsValue.get()).doubleValue(); index++) {
/*     */ 
/*     */           
/* 771 */           if (((Double)this.limitedMultiTargetsValue.get()).doubleValue() == 0.0D || index < ((Double)this.limitedMultiTargetsValue.get()).doubleValue()) {
/* 772 */             attackEntity(this.inRangeDiscoveredTargets.get(index));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 777 */       if (this.targetModeValue.is("Switch")) {
/* 778 */         if (this.switchTimer.hasTimePassed(((Double)this.switchDelayValue.get()).longValue())) {
/* 779 */           this.prevTargetEntities.add(Integer.valueOf(((Boolean)this.aacValue.get()).booleanValue() ? this.target.getEntityId() : currentTarget.getEntityId()));
/* 780 */           this.switchTimer.reset();
/*     */         } 
/*     */       } else {
/* 783 */         this.prevTargetEntities.add(Integer.valueOf(((Boolean)this.aacValue.get()).booleanValue() ? this.target.getEntityId() : currentTarget.getEntityId()));
/*     */       } 
/*     */       
/* 786 */       if (this.target == currentTarget) {
/* 787 */         this.target = null;
/*     */       }
/*     */ 
/*     */       
/* 791 */       if (openInventory) {
/* 792 */         mc.getNetHandler().addToSendQueue((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
/*     */       }
/* 794 */     } else if (((Boolean)this.fakeSwingValue.get()).booleanValue()) {
/* 795 */       runSwing();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void attackEntity(EntityLivingBase entity) {
/* 804 */     if (!this.autoBlockPacketValue.is("Vanilla") && (mc.thePlayer.isBlocking() || this.blockingStatus)) {
/* 805 */       mc.getNetHandler().addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
/* 806 */       this.blockingStatus = false;
/*     */     } 
/*     */     
/* 809 */     EventAttack eventAttack = new EventAttack((Entity)entity);
/*     */     
/* 811 */     EventManager.call((Event)eventAttack);
/* 812 */     this.markTimer.reset();
/*     */ 
/*     */     
/* 815 */     runSwing();
/*     */     
/* 817 */     mc.getNetHandler().addToSendQueue((Packet)new C02PacketUseEntity((Entity)entity, C02PacketUseEntity.Action.ATTACK));
/*     */     
/* 819 */     if (((Boolean)this.keepSprintValue.get()).booleanValue()) {
/*     */       
/* 821 */       if (mc.thePlayer.fallDistance > 0.0F && !mc.thePlayer.onGround && !mc.thePlayer.isOnLadder() && 
/* 822 */         !mc.thePlayer.isInWater() && !mc.thePlayer.isPotionActive(Potion.blindness) && !mc.thePlayer.isRiding()) {
/* 823 */         mc.thePlayer.onCriticalHit((Entity)entity);
/*     */       }
/*     */       
/* 826 */       if (EnchantmentHelper.func_152377_a(mc.thePlayer.getHeldItem(), entity.getCreatureAttribute()) > 0.0F) {
/* 827 */         mc.thePlayer.onEnchantmentCritical((Entity)entity);
/*     */       }
/* 829 */     } else if (mc.playerController.getCurrentGameType() != WorldSettings.GameType.SPECTATOR) {
/* 830 */       mc.thePlayer.attackTargetEntityWithCurrentItem((Entity)entity);
/*     */     } 
/*     */ 
/*     */     
/* 834 */     if (mc.thePlayer.isBlocking() || (!this.autoBlockValue.is("Off") && canBlock())) {
/* 835 */       if (this.autoBlockPacketValue.is("AfterTick")) {
/*     */         return;
/*     */       }
/* 838 */       if (((Double)this.blockRate.get()).doubleValue() <= 0.0D || (new Random()).nextInt(100) > ((Double)this.blockRate.get()).doubleValue()) {
/*     */         return;
/*     */       }
/* 841 */       startBlocking((Entity)entity, ((Boolean)this.interactAutoBlockValue.get()).booleanValue());
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean getBlockingStatus() {
/* 846 */     return this.blockingStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean cancelRun() {
/* 853 */     return (mc.thePlayer.isSpectator() || 
/* 854 */       !isAlive((EntityLivingBase)mc.thePlayer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isAlive(EntityLivingBase entity) {
/* 861 */     return ((entity.isEntityAlive() && entity.getHealth() > 0.0F) || (((Boolean)this.aacValue
/* 862 */       .get()).booleanValue() && entity.hurtTime > 3));
/*     */   }
/*     */   
/*     */   private double getMaxRange() {
/* 866 */     return Math.max(((Double)this.rangeValue.get()).doubleValue(), ((Double)this.throughWallsRangeValue.get()).doubleValue());
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\advanced\AdvancedAura.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */