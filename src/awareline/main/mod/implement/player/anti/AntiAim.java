/*     */ package awareline.main.mod.implement.player.anti;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.EntityUtils;
/*     */ import awareline.main.mod.implement.misc.Teams;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.MoveUtils;
/*     */ import awareline.main.utility.math.MathUtil;
/*     */ import awareline.main.utility.math.RotationUtil;
/*     */ import awareline.main.utility.timer.TimerUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ public class AntiAim
/*     */   extends Module {
/*     */   private EntityArrow arrow;
/*  32 */   private final Mode<String> mode = new Mode("Mode", new String[] { "Single", "Switch" }, "Switch");
/*     */   
/*  34 */   private final Mode<String> antiAimMode = new Mode("AntiAim", new String[] { "NormalAnti", "BodyAnti", "FakeRoll", "RandomAnti", "Jitter", "Jitter2", "Jitter3", "Jitter4", "CustomJitter" }, "NormalAnti");
/*     */ 
/*     */   
/*  37 */   public final Numbers<Double> range = new Numbers("Range", Double.valueOf(20.0D), Double.valueOf(1.0D), Double.valueOf(70.0D), Double.valueOf(5.0D));
/*  38 */   public final Numbers<Double> pitchDown = new Numbers("PitchDown", Double.valueOf(90.0D), Double.valueOf(0.0D), Double.valueOf(90.0D), Double.valueOf(5.0D));
/*  39 */   public final Option<Boolean> playerValue = new Option("Player", Boolean.valueOf(true));
/*  40 */   public final Option<Boolean> invisibleValue = new Option("Invisible", Boolean.valueOf(true));
/*  41 */   public final Option<Boolean> mobsValue = new Option("Mobs", Boolean.valueOf(true));
/*  42 */   public final Option<Boolean> animalsValue = new Option("Animals", Boolean.valueOf(false));
/*  43 */   public final Option<Boolean> deadValue = new Option("Dead", Boolean.valueOf(false));
/*     */   
/*  45 */   public final Option<Boolean> fakeAngle = new Option("FakeAngle", Boolean.valueOf(false));
/*  46 */   private final Numbers<Double> fakeAngleTicks = new Numbers("FakeAngleTicks", 
/*  47 */       Double.valueOf(2.0D), Double.valueOf(2.0D), Double.valueOf(10.0D), Double.valueOf(1.0D), this.fakeAngle::get);
/*  48 */   public final Option<Boolean> fastSwitch = new Option("FastSwitch", Boolean.valueOf(false), () -> Boolean.valueOf(this.mode.is("Switch")));
/*  49 */   public final Option<Boolean> zitterYaw = new Option("ZitterYaw", Boolean.valueOf(true));
/*  50 */   public final Option<Boolean> bypassZitterHead = new Option("BypassZitterHead", Boolean.valueOf(true), this.zitterYaw::get);
/*  51 */   public final Option<Boolean> rollAA = new Option("RollAA", Boolean.valueOf(false));
/*  52 */   public final Option<Boolean> wallRoll = new Option("WallRoll", Boolean.valueOf(true));
/*  53 */   public final Option<Boolean> smoothless = new Option("Smoothless", Boolean.valueOf(false));
/*  54 */   public final Option<Boolean> autoStop = new Option("AutoStop", Boolean.valueOf(false));
/*  55 */   public final Option<Boolean> globalsCheck = new Option("GlobalsCheck", Boolean.valueOf(false));
/*     */   
/*  57 */   private final Numbers<Double> customJitterMax = new Numbers("CustomJitterMax", 
/*  58 */       Double.valueOf(25.0D), Double.valueOf(1.0D), Double.valueOf(180.0D), Double.valueOf(5.0D), () -> Boolean.valueOf(this.antiAimMode.is("CustomJitter")));
/*  59 */   private final Numbers<Double> customJitterMin = new Numbers("CustomJitterMin", 
/*  60 */       Double.valueOf(25.0D), Double.valueOf(1.0D), Double.valueOf(180.0D), Double.valueOf(5.0D), () -> Boolean.valueOf(this.antiAimMode.is("CustomJitter")));
/*  61 */   private final Numbers<Double> customJitterTicks = new Numbers("CustomJitterTicks", 
/*  62 */       Double.valueOf(4.0D), Double.valueOf(2.0D), Double.valueOf(20.0D), Double.valueOf(1.0D), () -> Boolean.valueOf(this.antiAimMode.is("CustomJitter")));
/*  63 */   public final Option<Boolean> onlyVisuals = new Option("OnlyVisuals", Boolean.valueOf(false));
/*  64 */   public final Option<Boolean> smoothHeadRender = new Option("SmoothHeadRender", Boolean.valueOf(false)); public static AntiAim getInstance; private float shouldYaw; private float shouldPitch; private float lastYaw; private float lastPitch; private Entity target;
/*  65 */   public final Option<Boolean> detect = new Option("DetectRoll", Boolean.valueOf(false)); private int index; private final Comparator<EntityLivingBase> angleComparator; private final TimerUtils timerUtils; public List<EntityLivingBase> targets;
/*     */   private final Comparator<Entity> distanceComparator;
/*     */   
/*     */   public AntiAim() {
/*  69 */     super("AntiAim", ModuleType.Player);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     this.angleComparator = Comparator.comparingDouble(e2 -> e2.getDistanceToEntity((Entity)mc.thePlayer));
/*     */     
/* 103 */     this.timerUtils = new TimerUtils();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 336 */     this.targets = new ArrayList<>();
/* 337 */     this.distanceComparator = Comparator.comparingDouble(e3 -> e3.getDistanceToEntity((Entity)mc.thePlayer)); addSettings(new Value[] { 
/*     */           (Value)this.mode, (Value)this.range, (Value)this.pitchDown, (Value)this.playerValue, (Value)this.invisibleValue, (Value)this.mobsValue, (Value)this.animalsValue, (Value)this.deadValue, (Value)this.antiAimMode, (Value)this.fastSwitch, 
/*     */           (Value)this.fakeAngle, (Value)this.fakeAngleTicks, (Value)this.zitterYaw, (Value)this.bypassZitterHead, (Value)this.rollAA, (Value)this.wallRoll, (Value)this.smoothless, (Value)this.detect, (Value)this.globalsCheck, (Value)this.autoStop, 
/* 340 */           (Value)this.smoothHeadRender, (Value)this.onlyVisuals, (Value)this.customJitterMax, (Value)this.customJitterMin, (Value)this.customJitterTicks }); getInstance = this; } private List<EntityLivingBase> getTargets() { List<EntityLivingBase> targets = new ArrayList<>();
/*     */     
/* 342 */     for (Entity o : mc.theWorld.getLoadedEntityList()) {
/* 343 */       if (o instanceof EntityLivingBase) {
/* 344 */         EntityLivingBase entity = (EntityLivingBase)o;
/* 345 */         if (!EntityUtils.isSelectedForAntiAim((Entity)entity, true))
/*     */           continue; 
/* 347 */         targets.add(entity);
/*     */       } 
/*     */     } 
/* 350 */     if (!targets.isEmpty()) {
/* 351 */       this.targets.sort(this.distanceComparator);
/*     */     }
/* 353 */     return targets; }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*     */     this.lastYaw = mc.thePlayer.rotationYaw;
/*     */     this.lastPitch = mc.thePlayer.rotationPitch;
/*     */     this.shouldPitch = 85.0F;
/*     */     this.arrow = null;
/*     */     this.targets.clear();
/*     */     this.target = null;
/*     */   }
/*     */   
/*     */   public void onDisable() {
/*     */     this.arrow = null;
/*     */     super.onDisable();
/*     */   }
/*     */   
/*     */   private boolean canConsume() {
/*     */     return (mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof net.minecraft.item.ItemBow);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onUpdate(EventPreUpdate event) {
/*     */     setSuffix("Yaw " + (int)this.lastYaw + " Pitch " + this.lastPitch);
/*     */     mc.gameSettings.keyBindSneak.pressed = (mc.thePlayer.ticksExisted % 3 == 0);
/*     */     if (mc.gameSettings.keyBindAttack.isKeyDown() || mc.gameSettings.keyBindDrop.isKeyDown() || (canConsume() && mc.gameSettings.keyBindUseItem.isKeyDown() && mc.thePlayer.getItemInUseDuration() > 20) || canConsume() || mc.gameSettings.keyBindUseItem.isKeyDown());
/*     */     if (this.arrow == null) {
/*     */       this.targets = getTargets();
/*     */       this.targets.sort(this.angleComparator);
/*     */       if (this.mode.is("Switch"))
/*     */         if (((Boolean)this.fastSwitch.get()).booleanValue()) {
/*     */           this.index++;
/*     */         } else if (this.timerUtils.delay(80.0F)) {
/*     */           this.index++;
/*     */           this.timerUtils.reset();
/*     */         }  
/*     */       if (this.targets == null) {
/*     */         this.target = null;
/*     */       } else {
/*     */         if (!this.targets.isEmpty()) {
/*     */           if (this.mode.is("Switch") && this.index >= this.targets.size())
/*     */             this.index = 0; 
/*     */           this.target = (Entity)this.targets.get(this.mode.is("Single") ? 0 : this.index);
/*     */           if (this.target != null) {
/*     */             float[] rots = RotationUtil.getRotationsForDown(this.target);
/*     */             if (this.antiAimMode.is("Jitter")) {
/*     */               this.shouldYaw = MathUtil.getRandomInRange(-rots[0] + 45.0F, -rots[0] - 45.0F);
/*     */             } else if (this.antiAimMode.is("Jitter2")) {
/*     */               this.shouldYaw = (mc.thePlayer.ticksExisted % (MoveUtils.INSTANCE.isOnGround(0.01D) ? 3 : 2) == 0) ? (-rots[0] + 45.0F) : (-rots[0] - 45.0F);
/*     */             } else if (this.antiAimMode.is("Jitter3")) {
/*     */               this.shouldYaw = (mc.thePlayer.ticksExisted % 2 == 0) ? (-rots[0] + 45.0F) : (-rots[0] - 45.0F);
/*     */             } else if (this.antiAimMode.is("Jitter4")) {
/*     */               this.shouldYaw = (mc.thePlayer.ticksExisted % 3 == 0) ? (-rots[0] + 45.0F) : (-rots[0] - 45.0F);
/*     */             } else if (this.antiAimMode.is("CustomJitter")) {
/*     */               this.shouldYaw = (mc.thePlayer.ticksExisted % ((Double)this.customJitterTicks.get()).intValue() == 0) ? (-rots[0] + ((Double)this.customJitterMax.get()).intValue()) : (-rots[0] - ((Double)this.customJitterMin.get()).intValue());
/*     */             } else {
/*     */               this.shouldYaw = -rots[0];
/*     */             } 
/*     */           } 
/*     */         } else {
/*     */           float[] rots = RotationUtil.getRotationsForDown((Entity)mc.thePlayer);
/*     */           if (this.antiAimMode.is("Jitter")) {
/*     */             this.shouldYaw = MathUtil.getRandomInRange(-rots[0] + 45.0F, -rots[0] - 45.0F);
/*     */           } else if (this.antiAimMode.is("Jitter2")) {
/*     */             this.shouldYaw = (mc.thePlayer.ticksExisted % (MoveUtils.INSTANCE.isOnGround(0.01D) ? 3 : 2) == 0) ? (-rots[0] + 45.0F) : (-rots[0] - 45.0F);
/*     */           } else if (this.antiAimMode.is("Jitter3")) {
/*     */             this.shouldYaw = (mc.thePlayer.ticksExisted % 2 == 0) ? (-rots[0] + 45.0F) : (-rots[0] - 45.0F);
/*     */           } else if (this.antiAimMode.is("Jitter4")) {
/*     */             this.shouldYaw = (mc.thePlayer.ticksExisted % 3 == 0) ? (-rots[0] + 45.0F) : (-rots[0] - 45.0F);
/*     */           } else if (this.antiAimMode.is("CustomJitter")) {
/*     */             this.shouldYaw = (mc.thePlayer.ticksExisted % ((Double)this.customJitterTicks.get()).intValue() == 0) ? (-rots[0] + ((Double)this.customJitterMax.get()).intValue()) : (-rots[0] - ((Double)this.customJitterMin.get()).intValue());
/*     */           } else {
/*     */             this.shouldYaw = -rots[0];
/*     */           } 
/*     */         } 
/*     */         this.shouldPitch = ((Double)this.pitchDown.get()).intValue();
/*     */       } 
/*     */     } 
/*     */     for (Entity e : mc.theWorld.getLoadedEntityList()) {
/*     */       if (!(e instanceof EntityArrow))
/*     */         continue; 
/*     */       this.arrow = (EntityArrow)e;
/*     */       if (this.arrow.shootingEntity != null && this.arrow.shootingEntity.isEntityEqual((Entity)mc.thePlayer)) {
/*     */         this.target = (Entity)(this.arrow = null);
/*     */         continue;
/*     */       } 
/*     */       if (this.arrow.shootingEntity instanceof EntityPlayer) {
/*     */         EntityPlayer player = (EntityPlayer)this.arrow.shootingEntity;
/*     */         if (Teams.getInstance.isEnabled() && Teams.getInstance.isOnSameTeam((Entity)player))
/*     */           continue; 
/*     */       } 
/*     */       if (!((Boolean)this.globalsCheck.get()).booleanValue() && this.arrow.inGround) {
/*     */         this.target = (Entity)(this.arrow = null);
/*     */         continue;
/*     */       } 
/*     */       if (mc.thePlayer.getDistanceSqToEntity((Entity)this.arrow) >= 20.0D) {
/*     */         this.target = (Entity)(this.arrow = null);
/*     */         continue;
/*     */       } 
/*     */       if (this.arrow != null && this.target == null) {
/*     */         double angleA = Math.toRadians(this.arrow.rotationYaw);
/*     */         Vec3 cVec = new Vec3(mc.thePlayer.posX + Math.cos(angleA) * 0.5D, mc.thePlayer.posY, mc.thePlayer.posZ - Math.sin(angleA) * 0.7D);
/*     */         Vec3 cVec2 = new Vec3(mc.thePlayer.posX + Math.cos(angleA) * 1.7D, mc.thePlayer.posY, mc.thePlayer.posZ - Math.sin(angleA) * 1.7D);
/*     */         Vec3 vec = new Vec3(mc.thePlayer.posX + Math.cos(angleA) * 1.5D, mc.thePlayer.posY, mc.thePlayer.posZ - Math.sin(angleA) * 1.5D);
/*     */         if (!isBlockPosAir(getBlockPos(cVec)) || !isBlockPosAir(getBlockPos(cVec2))) {
/*     */           cVec = new Vec3(mc.thePlayer.posX - Math.cos(angleA) * 0.5D, mc.thePlayer.posY, mc.thePlayer.posZ + Math.sin(angleA) * 0.5D);
/*     */           cVec2 = new Vec3(mc.thePlayer.posX - Math.cos(angleA) * 1.7D, mc.thePlayer.posY, mc.thePlayer.posZ + Math.sin(angleA) * 1.7D);
/*     */           vec = new Vec3(mc.thePlayer.posX - Math.cos(angleA) * 1.5D, mc.thePlayer.posY, mc.thePlayer.posZ + Math.sin(angleA) * 1.5D);
/*     */         } else if (isBlockPosAir(getBlockPos(vec)) && isBlockPosAir(getBlockPos(vec).down(1)) && isBlockPosAir(getBlockPos(vec).down(2))) {
/*     */           vec = new Vec3(mc.thePlayer.posX - Math.cos(angleA) * 1.5D, mc.thePlayer.posY, mc.thePlayer.posZ + Math.sin(angleA) * 1.5D);
/*     */         } 
/*     */         if (!isBlockPosAir(getBlockPos(cVec)) || !isBlockPosAir(getBlockPos(cVec2)))
/*     */           return; 
/*     */         if (isBlockPosAir(getBlockPos(vec)) && isBlockPosAir(getBlockPos(vec).down(1)) && isBlockPosAir(getBlockPos(vec).down(2)))
/*     */           return; 
/*     */         if (((Boolean)this.autoStop.get()).booleanValue())
/*     */           MoveUtils.INSTANCE.stop(); 
/*     */         float[] rots = RotationUtil.getRotationsForDown((Entity)this.arrow);
/*     */         if (this.antiAimMode.is("NormalAnti")) {
/*     */           this.shouldYaw = -rots[0];
/*     */           this.shouldPitch = 90.0F;
/*     */           continue;
/*     */         } 
/*     */         if (this.antiAimMode.is("Jitter")) {
/*     */           this.shouldYaw = MathUtil.getRandomInRange(-rots[0] + 45.0F, -rots[0] - 45.0F);
/*     */           this.shouldPitch = 90.0F;
/*     */           continue;
/*     */         } 
/*     */         if (this.antiAimMode.is("CustomJitter")) {
/*     */           this.shouldYaw = (mc.thePlayer.ticksExisted % ((Double)this.customJitterTicks.get()).intValue() == 0) ? (-rots[0] + ((Double)this.customJitterMax.get()).intValue()) : (-rots[0] - ((Double)this.customJitterMin.get()).intValue());
/*     */           this.shouldPitch = 90.0F;
/*     */           continue;
/*     */         } 
/*     */         if (this.antiAimMode.is("Jitter2")) {
/*     */           this.shouldYaw = (mc.thePlayer.ticksExisted % (MoveUtils.INSTANCE.isOnGround(0.01D) ? 3 : 2) == 0) ? (-rots[0] + 45.0F) : (-rots[0] - 45.0F);
/*     */           this.shouldPitch = 90.0F;
/*     */           continue;
/*     */         } 
/*     */         if (this.antiAimMode.is("Jitter3")) {
/*     */           this.shouldYaw = (mc.thePlayer.ticksExisted % 2 == 0) ? (-rots[0] + 45.0F) : (-rots[0] - 45.0F);
/*     */           this.shouldPitch = 90.0F;
/*     */           continue;
/*     */         } 
/*     */         if (this.antiAimMode.is("Jitter4")) {
/*     */           this.shouldYaw = (mc.thePlayer.ticksExisted % 3 == 0) ? (-rots[0] + 45.0F) : (-rots[0] - 45.0F);
/*     */           this.shouldPitch = 90.0F;
/*     */           continue;
/*     */         } 
/*     */         if (this.antiAimMode.is("RandomAnti")) {
/*     */           if (mc.thePlayer.ticksExisted % 3 == 0) {
/*     */             this.shouldYaw = (float)(this.arrow.rotationYaw * Math.nextAfter(vec.xCoord, vec.zCoord) * 4.0D);
/*     */           } else {
/*     */             this.shouldYaw = -rots[0];
/*     */           } 
/*     */           this.shouldPitch = 90.0F;
/*     */           continue;
/*     */         } 
/*     */         if (this.antiAimMode.is("FakeRoll")) {
/*     */           this.shouldYaw = (float)(this.arrow.rotationYaw * Math.nextAfter(vec.xCoord, vec.zCoord) * 4.0D);
/*     */           this.shouldPitch = 90.0F;
/*     */           continue;
/*     */         } 
/*     */         if (this.antiAimMode.is("BodyAnti")) {
/*     */           this.shouldYaw = -rots[0];
/*     */           this.shouldPitch = -rots[1];
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     float turnspeed1 = ((Boolean)this.fakeAngle.get()).booleanValue() ? ((mc.thePlayer.ticksExisted % ((Double)this.fakeAngleTicks.get()).intValue() == 0) ? (((Boolean)this.smoothless.get()).booleanValue() ? 120 : '´') : false) : (((Boolean)this.smoothless.get()).booleanValue() ? 120 : '´');
/*     */     float turnspeed2 = ((Boolean)this.fakeAngle.get()).booleanValue() ? ((mc.thePlayer.ticksExisted % ((Double)this.fakeAngleTicks.get()).intValue() == 0) ? 180.0F : 0.0F) : 180.0F;
/*     */     this.lastYaw = RotationUtil.getRotateForScaffold(this.shouldYaw + ((((Boolean)this.wallRoll.get()).booleanValue() && this.target != null) ? (isCollided() ? (mc.gameSettings.keyBindLeft.isKeyDown() ? 90 : -90) : false) : false), this.shouldPitch, this.lastYaw, this.lastPitch, turnspeed1, turnspeed2)[0];
/*     */     this.lastPitch = RotationUtil.getRotateForScaffold(this.shouldYaw, this.shouldPitch, this.lastYaw, this.lastPitch, turnspeed1, turnspeed2)[1];
/*     */     if (((Boolean)this.detect.get()).booleanValue())
/*     */       this.lastYaw = (ThreadLocalRandom.current().nextBoolean() && mc.thePlayer.ticksExisted % 2 == 0) ? (float)(MathUtil.randomClickDelay(this.lastYaw, (-this.lastYaw + 90.0F)) + 90L) : Math.abs(this.lastYaw + 360.0F); 
/*     */     if (((Boolean)this.zitterYaw.get()).booleanValue())
/*     */       if (!isCollided()) {
/*     */         if (isMoving()) {
/*     */           if (MoveUtils.INSTANCE.isOnGround(0.01D)) {
/*     */             this.lastYaw += (float)(Math.random() * 18.0D - 14.0D);
/*     */             if (!((Boolean)this.bypassZitterHead.get()).booleanValue())
/*     */               this.lastPitch += (float)(Math.random() * 18.0D - 16.0D); 
/*     */           } else {
/*     */             this.lastYaw += (float)(Math.random() * 88.0D - 66.0D);
/*     */             if (!((Boolean)this.bypassZitterHead.get()).booleanValue())
/*     */               this.lastPitch += (float)(Math.random() * 88.0D - 76.0D); 
/*     */           } 
/*     */         } else if (mc.thePlayer.ticksExisted % 2 == 0) {
/*     */           this.lastYaw += (float)(Math.random() * 88.0D - 66.0D);
/*     */           if (!((Boolean)this.bypassZitterHead.get()).booleanValue())
/*     */             this.lastPitch += (float)(Math.random() * 88.0D - 76.0D); 
/*     */         } else {
/*     */           this.lastYaw += (float)(Math.random() * 18.0D - 14.0D);
/*     */           if (!((Boolean)this.bypassZitterHead.get()).booleanValue())
/*     */             this.lastPitch += (float)(Math.random() * 18.0D - 16.0D); 
/*     */         } 
/*     */       } else {
/*     */         this.lastYaw += (float)(Math.random() * 8.0D - 4.0D);
/*     */         if (!((Boolean)this.bypassZitterHead.get()).booleanValue())
/*     */           this.lastPitch += (float)(Math.random() * 12.0D - 6.0D); 
/*     */       }  
/*     */     if (!((Boolean)this.onlyVisuals.get()).booleanValue()) {
/*     */       event.setYaw(this.lastYaw);
/*     */       event.setPitch(this.lastPitch);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static BlockPos getBlockPos(Vec3 vec) {
/*     */     return new BlockPos(vec.xCoord, vec.yCoord, vec.zCoord);
/*     */   }
/*     */   
/*     */   public static boolean isBlockPosAir(BlockPos blockPos) {
/*     */     return (mc.theWorld.getBlockState(blockPos).getBlock().getMaterial() == Material.air);
/*     */   }
/*     */   
/*     */   private boolean isCollided() {
/*     */     return (!mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, 0.0D, -0.5D)).isEmpty() || !mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.5D, 0.0D, 0.0D)).isEmpty() || !mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, 0.0D, 0.5D)).isEmpty() || !mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(-0.5D, 0.0D, 0.0D)).isEmpty());
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\anti\AntiAim.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */