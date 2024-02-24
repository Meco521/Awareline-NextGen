/*     */ package awareline.main.mod.implement.combat;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*     */ import awareline.main.utility.math.RotationUtil;
/*     */ import awareline.main.utility.timer.TimerUtil;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import net.minecraft.client.settings.KeyBinding;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ 
/*     */ public class TerminatorBot
/*     */   extends Module
/*     */ {
/*  23 */   private final Numbers<Double> cps = new Numbers("CPS", 
/*  24 */       Double.valueOf(10.0D), Double.valueOf(1.0D), Double.valueOf(20.0D), Double.valueOf(1.0D));
/*  25 */   private final Numbers<Double> range = new Numbers("Range", 
/*  26 */       Double.valueOf(10.0D), Double.valueOf(0.0D), Double.valueOf(30.0D), Double.valueOf(0.1D));
/*  27 */   private final Option<Boolean> player = new Option("Players", Boolean.valueOf(true));
/*  28 */   private final Option<Boolean> monster = new Option("Monster", Boolean.valueOf(true));
/*  29 */   private final Option<Boolean> animal = new Option("Animals", Boolean.valueOf(false));
/*  30 */   private final Option<Boolean> timerFast = new Option("Timer", Boolean.valueOf(false));
/*  31 */   private final Option<Boolean> keepJump = new Option("Jump", Boolean.valueOf(false));
/*  32 */   private final Option<Boolean> targetCheck = new Option("NoBadTarget", Boolean.valueOf(false));
/*     */   
/*  34 */   private final TimerUtil cpsTimerUtil = new TimerUtil();
/*     */   private EntityLivingBase lastEntity;
/*     */   
/*     */   public TerminatorBot() {
/*  38 */     super("TerminatorBot", ModuleType.Combat);
/*  39 */     addSettings(new Value[] { (Value)this.cps, (Value)this.range, (Value)this.player, (Value)this.monster, (Value)this.animal, (Value)this.keepJump, (Value)this.timerFast });
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPre(EventPreUpdate e) {
/*  44 */     setSuffix((Serializable)this.range.get());
/*  45 */     List<EntityLivingBase> inRangeEntities = new CopyOnWriteArrayList<>();
/*     */     
/*  47 */     for (Entity entity : mc.theWorld.loadedEntityList) {
/*  48 */       if (mc.thePlayer.getDistanceToEntity(entity) <= ((Double)this.range.getValue()).doubleValue() && shouldAdd(entity) && entity instanceof EntityLivingBase) {
/*  49 */         inRangeEntities.add((EntityLivingBase)entity);
/*     */       }
/*     */     } 
/*     */     
/*  53 */     inRangeEntities.sort((e1, e2) -> (int)(mc.thePlayer.getDistanceToEntity((Entity)e1) - mc.thePlayer.getDistanceToEntity((Entity)e2)));
/*     */     
/*  55 */     if (!inRangeEntities.isEmpty()) {
/*  56 */       EntityLivingBase currentEntity = inRangeEntities.get(0);
/*  57 */       if (((Boolean)this.timerFast.get()).booleanValue()) {
/*  58 */         if (currentEntity != null) {
/*  59 */           if (mc.thePlayer.fallDistance < 2.0F) {
/*  60 */             mc.timer.timerSpeed = 1.2F;
/*     */           }
/*     */         }
/*  63 */         else if (mc.timer.timerSpeed != 1.0F) {
/*  64 */           mc.timer.timerSpeed = 1.0F;
/*     */         } 
/*     */       }
/*     */       
/*  68 */       if (this.lastEntity == null || this.lastEntity != currentEntity) {
/*  69 */         this.lastEntity = currentEntity;
/*  70 */         noti("Terminator", "New attack target is " + currentEntity.getName(), 4000L, ClientNotification.Type.INFO);
/*     */       } 
/*  72 */       if (((Boolean)this.targetCheck.get()).booleanValue() && ((
/*  73 */         currentEntity.motionX == 0.0D && currentEntity.motionZ == 0.0D) || currentEntity.moveForward == 0.0F || currentEntity.moveStrafing == 0.0F)) {
/*     */         
/*  75 */         currentEntity = null;
/*     */         
/*     */         return;
/*     */       } 
/*  79 */       if (!currentEntity.isEntityAlive()) {
/*  80 */         inRangeEntities.clear();
/*     */         return;
/*     */       } 
/*  83 */       float[] rotationValue = RotationUtil.getPredictedRotations(currentEntity);
/*     */       
/*  85 */       mc.thePlayer.rotationYaw = rotationValue[0];
/*  86 */       mc.thePlayer.rotationPitch = rotationValue[1];
/*  87 */       if (((Boolean)this.keepJump.get()).booleanValue()) {
/*  88 */         KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), true);
/*     */       }
/*  90 */       KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
/*  91 */       if (shouldAttack()) {
/*  92 */         mc.clickMouse();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  99 */     if (((Boolean)this.timerFast.get()).booleanValue()) {
/* 100 */       mc.timer.timerSpeed = 1.0F;
/*     */     }
/* 102 */     super.onDisable();
/*     */   }
/*     */   
/*     */   private boolean shouldAttack() {
/* 106 */     int APS = 20 / ((Double)this.cps.getValue()).intValue();
/* 107 */     if (this.cpsTimerUtil.hasReached((50 * APS))) {
/* 108 */       this.cpsTimerUtil.reset();
/* 109 */       return true;
/*     */     } 
/* 111 */     return false;
/*     */   }
/*     */   
/*     */   private boolean shouldAdd(Entity entity) {
/* 115 */     if (entity == mc.thePlayer) {
/* 116 */       return false;
/*     */     }
/*     */     
/* 119 */     if (!entity.isEntityAlive()) {
/* 120 */       return false;
/*     */     }
/*     */     
/* 123 */     if (entity instanceof net.minecraft.entity.player.EntityPlayer && ((Boolean)this.player.getValue()).booleanValue()) {
/* 124 */       return true;
/*     */     }
/*     */     
/* 127 */     if (entity instanceof net.minecraft.entity.monster.EntityMob && ((Boolean)this.monster.getValue()).booleanValue()) {
/* 128 */       return true;
/*     */     }
/*     */     
/* 131 */     return (entity instanceof net.minecraft.entity.passive.EntityAnimal && ((Boolean)this.animal.getValue()).booleanValue());
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\TerminatorBot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */