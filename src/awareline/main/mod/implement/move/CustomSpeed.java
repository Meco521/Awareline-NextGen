/*     */ package awareline.main.mod.implement.move;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.moveEvents.EventMove;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.KillAura;
/*     */ import awareline.main.mod.implement.combat.TargetStrafe;
/*     */ import awareline.main.mod.implement.world.Scaffold;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*     */ import awareline.main.utility.BlockUtils;
/*     */ import awareline.main.utility.MoveUtils;
/*     */ import io.netty.util.internal.ThreadLocalRandom;
/*     */ import net.minecraft.network.play.client.C0CPacketInput;
/*     */ 
/*     */ public class CustomSpeed extends Module {
/*  21 */   public final Numbers<Double> customSpeed = new Numbers("CustomSpeed", Double.valueOf(1.6D), Double.valueOf(0.2D), Double.valueOf(2.0D), Double.valueOf(0.01D));
/*  22 */   public final Numbers<Double> customY = new Numbers("CustomY", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(4.0D), Double.valueOf(0.01D));
/*  23 */   public final Numbers<Double> customTimer = new Numbers("CustomTimer", Double.valueOf(1.0D), Double.valueOf(0.1D), Double.valueOf(2.0D), Double.valueOf(0.01D));
/*  24 */   public final Option<Boolean> customStrafe = new Option("CustomStrafe", Boolean.valueOf(true));
/*  25 */   public final Option<Boolean> customResetXZ = new Option("CustomResetXZ", Boolean.valueOf(false));
/*  26 */   public final Option<Boolean> customResetY = new Option("CustomResetY", Boolean.valueOf(false));
/*  27 */   private final Option<Boolean> onlyGround = new Option("OnlyGround", Boolean.valueOf(false));
/*  28 */   private final Option<Boolean> safeStrafe = new Option("SafeStrafe", Boolean.valueOf(false));
/*  29 */   private final Option<Boolean> disableOnDeath = new Option("DisableOnDeath", Boolean.valueOf(false));
/*  30 */   private final Option<Boolean> jumpNoBob = new Option("JumpNoBob", Boolean.valueOf(false));
/*  31 */   private final Option<Boolean> fakeGround = new Option("FakeGround", Boolean.valueOf(false));
/*  32 */   private final Option<Boolean> basicCheck = new Option("BasicCheck", Boolean.valueOf(true));
/*  33 */   private final Option<Boolean> glideJump = new Option("GlideJump", Boolean.valueOf(false));
/*  34 */   private final Option<Boolean> damageStrafe = new Option("DamageStrafe", Boolean.valueOf(false));
/*  35 */   private final Option<Boolean> moveRotations = new Option("MoveRotations", Boolean.valueOf(false));
/*  36 */   private final Option<Boolean> sendExploitPacket = new Option("ExploitPacket", Boolean.valueOf(false));
/*     */   
/*     */   public CustomSpeed() {
/*  39 */     super("CustomSpeed", new String[] { "cs" }, ModuleType.Movement);
/*  40 */     addSettings(new Value[] { (Value)this.customSpeed, (Value)this.customY, (Value)this.customTimer, (Value)this.customStrafe, (Value)this.onlyGround, (Value)this.safeStrafe, (Value)this.customResetXZ, (Value)this.customResetY, (Value)this.basicCheck, (Value)this.glideJump, (Value)this.damageStrafe, (Value)this.moveRotations, (Value)this.fakeGround, (Value)this.sendExploitPacket, (Value)this.disableOnDeath, (Value)this.jumpNoBob });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onMove(EventMove e) {
/*  49 */     if (((Boolean)this.basicCheck.get()).booleanValue() && (
/*  50 */       BlockUtils.isInLiquid() || BlockUtils.isOnLiquid() || mc.thePlayer
/*  51 */       .isInWater() || mc.thePlayer.isInWeb || BlockUtils.isInsideBlock())) {
/*     */       return;
/*     */     }
/*     */     
/*  55 */     if (Scaffold.getInstance.isEnabled() && ((Boolean)Scaffold.getInstance.StopSpeed
/*  56 */       .get()).booleanValue()) {
/*     */       return;
/*     */     }
/*  59 */     if (isMoving() && (
/*  60 */       !((Boolean)this.damageStrafe.get()).booleanValue() || mc.thePlayer.hurtResistantTime > 0) && (
/*  61 */       !((Boolean)this.safeStrafe.get()).booleanValue() || MoveUtils.INSTANCE.getSpeed() < 0.215F) && 
/*  62 */       TargetStrafe.getInstance.isEnabled() && KillAura.getInstance.getTarget() != null) {
/*  63 */       e.setMoveSpeed(MoveUtils.INSTANCE.getSpeed());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPre(EventPreUpdate e) {
/*  72 */     if (((Boolean)this.basicCheck.get()).booleanValue() && (
/*  73 */       BlockUtils.isInLiquid() || BlockUtils.isOnLiquid() || mc.thePlayer
/*  74 */       .isInWater() || mc.thePlayer.isInWeb || BlockUtils.isInsideBlock())) {
/*     */       return;
/*     */     }
/*     */     
/*  78 */     if (mc.thePlayer.isSneaking()) {
/*     */       return;
/*     */     }
/*  81 */     if (Scaffold.getInstance.isEnabled() && ((Boolean)Scaffold.getInstance.StopSpeed
/*  82 */       .get()).booleanValue()) {
/*     */       return;
/*     */     }
/*  85 */     if (isMoving()) {
/*  86 */       if (((Boolean)this.glideJump.get()).booleanValue() && 
/*  87 */         mc.thePlayer.fallDistance <= 1.0F) {
/*  88 */         if (mc.thePlayer.ticksExisted % 5 == 0) {
/*  89 */           mc.thePlayer.jumpMovementFactor = (float)(mc.thePlayer.jumpMovementFactor + 2.0E-4D);
/*     */         }
/*  91 */         if (mc.thePlayer.ticksExisted % 10 == 0) {
/*  92 */           mc.thePlayer.motionY = -0.04D - 0.032D * Math.random();
/*     */         }
/*     */       } 
/*     */       
/*  96 */       if (((Boolean)this.fakeGround.get()).booleanValue() && 
/*  97 */         mc.thePlayer.onGround) {
/*  98 */         e.y += ThreadLocalRandom.current().nextDouble() / 1000.0D;
/*  99 */         e.onGround = true;
/*     */       } 
/*     */       
/* 102 */       mc.timer.timerSpeed = ((Double)this.customTimer.get()).floatValue();
/* 103 */       if (mc.thePlayer.onGround) {
/* 104 */         if (((Boolean)this.onlyGround.get()).booleanValue()) {
/* 105 */           mc.thePlayer.jump();
/* 106 */           MoveUtils.INSTANCE.strafe();
/*     */         } else {
/* 108 */           MoveUtils.INSTANCE.strafe(((Double)this.customSpeed.get()).floatValue());
/*     */         } 
/* 110 */         mc.thePlayer.motionY = ((Double)this.customY.get()).floatValue();
/* 111 */       } else if (((Boolean)this.customStrafe.get()).booleanValue()) {
/* 112 */         if (((Boolean)this.onlyGround.get()).booleanValue()) {
/*     */           return;
/*     */         }
/* 115 */         if ((!((Boolean)this.damageStrafe.get()).booleanValue() || mc.thePlayer.hurtResistantTime > 0) && (
/* 116 */           !((Boolean)this.safeStrafe.get()).booleanValue() || MoveUtils.INSTANCE.getSpeed() < 0.215F)) {
/* 117 */           MoveUtils.INSTANCE.strafe(((Double)this.customSpeed.get()).floatValue());
/*     */         }
/*     */       } else {
/*     */         
/* 121 */         if (((Boolean)this.onlyGround.get()).booleanValue()) {
/*     */           return;
/*     */         }
/* 124 */         if ((!((Boolean)this.damageStrafe.get()).booleanValue() || mc.thePlayer.hurtResistantTime > 0) && (
/* 125 */           !((Boolean)this.safeStrafe.get()).booleanValue() || MoveUtils.INSTANCE.getSpeed() < 0.215F)) {
/* 126 */           MoveUtils.INSTANCE.strafe();
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 131 */       mc.thePlayer.motionX = mc.thePlayer.motionZ = 0.0D;
/*     */     } 
/*     */   }
/*     */   @EventHandler
/*     */   public void onRender2d(EventRender2D e) {
/* 136 */     setSuffix(this.customTimer.get() + "|" + this.customSpeed.get() + "|" + this.customY.get());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 141 */     if (((Boolean)this.customResetXZ.get()).booleanValue()) mc.thePlayer.motionX = mc.thePlayer.motionZ = 0.0D; 
/* 142 */     if (((Boolean)this.customResetY.get()).booleanValue()) mc.thePlayer.motionY = 0.0D; 
/* 143 */     super.onEnable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 148 */     mc.timer.timerSpeed = 1.0F;
/* 149 */     super.onDisable();
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onUpdate(EventPreUpdate e) {
/* 155 */     if (((Boolean)this.disableOnDeath.get()).booleanValue()) {
/* 156 */       if (!mc.thePlayer.isEntityAlive()) {
/* 157 */         ClientNotification.sendClientMessage(getHUDName(), "Auto disable because player death", 4000L, ClientNotification.Type.WARNING);
/* 158 */         setEnabled(false);
/*     */         return;
/*     */       } 
/* 161 */       if (mc.thePlayer.ticksExisted <= 1) {
/* 162 */         ClientNotification.sendClientMessage(getHUDName(), "Auto disable because player death", 4000L, ClientNotification.Type.WARNING);
/* 163 */         setEnabled(false);
/*     */         return;
/*     */       } 
/*     */     } 
/* 167 */     if (Scaffold.getInstance.isEnabled() && ((Boolean)Scaffold.getInstance.StopSpeed
/* 168 */       .get()).booleanValue()) {
/*     */       return;
/*     */     }
/* 171 */     if (((Boolean)this.sendExploitPacket.get()).booleanValue()) {
/* 172 */       send((Packet)new C0CPacketInput());
/*     */     }
/* 174 */     if (((Boolean)this.moveRotations.get()).booleanValue() && isMoving() && 
/* 175 */       KillAura.getInstance.getTarget() == null) {
/* 176 */       e.setYaw((float)Math.toDegrees(MoveUtils.INSTANCE.getDirection()));
/*     */     }
/*     */ 
/*     */     
/* 180 */     if (((Boolean)this.jumpNoBob.get()).booleanValue()) {
/* 181 */       mc.thePlayer.cameraYaw = 0.0F;
/*     */     }
/* 183 */     if (mc.thePlayer.isSneaking()) {
/*     */       return;
/*     */     }
/* 186 */     if (isMoving())
/* 187 */       mc.thePlayer.setSprinting(true); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\CustomSpeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */