/*     */ package awareline.main.event.events.world.moveEvents;
/*     */ 
/*     */ import awareline.main.event.Event;
/*     */ import awareline.main.mod.implement.combat.KillAura;
/*     */ import awareline.main.mod.implement.combat.TargetStrafe;
/*     */ import awareline.main.mod.implement.combat.VanillaAura;
/*     */ import awareline.main.mod.implement.combat.advanced.AdvancedAura;
/*     */ import awareline.main.mod.implement.move.Speed;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.util.MovementInput;
/*     */ 
/*     */ public class EventMove extends Event {
/*  15 */   final Minecraft mc = Minecraft.getMinecraft();
/*  16 */   final KillAura killAura = KillAura.getInstance;
/*  17 */   final Speed speed = Speed.getInstance;
/*  18 */   final TargetStrafe targetStrafe = TargetStrafe.getInstance; public double x; public double y;
/*  19 */   final MovementInput movementInput = this.mc.thePlayer.movementInput; public double z; public double getX() {
/*  20 */     return this.x;
/*     */   } public double getY() {
/*  22 */     return this.y;
/*     */   } public double getZ() {
/*  24 */     return this.z;
/*     */   }
/*     */   
/*     */   public EventMove(double x, double y, double z) {
/*  28 */     this.x = x;
/*  29 */     this.y = y;
/*  30 */     this.z = z;
/*     */   }
/*     */   
/*     */   public void setX(double x2) {
/*  34 */     this.x = x2;
/*     */   }
/*     */   
/*     */   public void setY(double y) {
/*  38 */     this.y = y;
/*     */   }
/*     */   
/*     */   public void setZ(double z2) {
/*  42 */     this.z = z2;
/*     */   }
/*     */   
/*     */   public void setMoveSpeed(double moveSpeed) {
/*  46 */     double moveForward = this.movementInput.getMoveForward();
/*  47 */     double moveStrafe = this.movementInput.getMoveStrafe();
/*  48 */     double yaw = this.mc.thePlayer.rotationYaw;
/*  49 */     double modifier = (moveForward == 0.0D) ? 90.0D : ((moveForward < 0.0D) ? -45.0D : 45.0D);
/*  50 */     boolean moving = (moveForward != 0.0D || moveStrafe != 0.0D);
/*     */     
/*  52 */     yaw += (moveForward < 0.0D) ? 180.0D : 0.0D;
/*     */     
/*  54 */     if (moveStrafe < 0.0D) {
/*  55 */       yaw += modifier;
/*  56 */     } else if (moveStrafe > 0.0D) {
/*  57 */       yaw -= modifier;
/*     */     } 
/*     */     
/*  60 */     if (moving) {
/*     */       
/*  62 */       boolean canDamageBoost = (((Boolean)this.speed.dmgboost.get()).booleanValue() && this.mc.thePlayer.hurtResistantTime > 0 && !this.mc.thePlayer.isPotionActive(Potion.poison) && !this.mc.thePlayer.isBurning());
/*  63 */       if (canDamageBoost) {
/*  64 */         moveSpeed *= ((Boolean)this.speed.dmgboost.get()).booleanValue() ? (1 + ((Double)this.speed.dmgzoom.get()).intValue()) : 1.0D;
/*     */       }
/*  66 */       if (targetstrafeCheck()) {
/*  67 */         if (targetStrafeUseMode()) {
/*  68 */           this.targetStrafe.exhiStrafe(this, moveSpeed, (Entity)this.killAura.getTarget());
/*     */         } else {
/*  70 */           this.targetStrafe.circleStrafe(this, moveSpeed, (Entity)this.killAura.getTarget());
/*     */         } 
/*     */       } else {
/*  73 */         this.x = -(Math.sin(Math.toRadians(yaw)) * moveSpeed);
/*  74 */         this.z = Math.cos(Math.toRadians(yaw)) * moveSpeed;
/*     */       } 
/*     */     } else {
/*  77 */       this.x = 0.0D;
/*  78 */       this.z = 0.0D;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean targetStrafeUseMode() {
/*  83 */     return TargetStrafe.getInstance.mode.is("Normal");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean targetstrafeCheck() {
/*  91 */     KillAura killAura = KillAura.getInstance;
/*  92 */     AdvancedAura customAura = AdvancedAura.getInstance;
/*  93 */     TargetStrafe targetStrafe = TargetStrafe.getInstance;
/*  94 */     if (!targetStrafe.isEnabled()) {
/*  95 */       return false;
/*     */     }
/*  97 */     if (targetStrafe.shouldTarget()) {
/*  98 */       return true;
/*     */     }
/* 100 */     if (killAura.getTarget() != null) {
/* 101 */       return (killAura.isEnabled() || customAura.isEnabled() || VanillaAura.getInstance.isEnabled());
/*     */     }
/* 103 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMoveSpeedNoDamageBoost(double moveSpeed) {
/* 108 */     double moveForward = this.movementInput.getMoveForward();
/* 109 */     double moveStrafe = this.movementInput.getMoveStrafe();
/* 110 */     double yaw = this.mc.thePlayer.rotationYaw;
/* 111 */     double modifier = (moveForward == 0.0D) ? 90.0D : ((moveForward < 0.0D) ? -45.0D : 45.0D);
/* 112 */     boolean moving = (moveForward != 0.0D || moveStrafe != 0.0D);
/*     */     
/* 114 */     yaw += (moveForward < 0.0D) ? 180.0D : 0.0D;
/*     */     
/* 116 */     if (moveStrafe < 0.0D) {
/* 117 */       yaw += modifier;
/* 118 */     } else if (moveStrafe > 0.0D) {
/* 119 */       yaw -= modifier;
/*     */     } 
/*     */     
/* 122 */     if (moving) {
/* 123 */       if (targetstrafeCheck()) {
/* 124 */         if (targetStrafeUseMode()) {
/* 125 */           this.targetStrafe.exhiStrafe(this, moveSpeed, (Entity)this.killAura.getTarget());
/*     */         } else {
/* 127 */           this.targetStrafe.circleStrafe(this, moveSpeed, (Entity)this.killAura.getTarget());
/*     */         } 
/*     */       } else {
/* 130 */         this.x = -(Math.sin(Math.toRadians(yaw)) * moveSpeed);
/* 131 */         this.z = Math.cos(Math.toRadians(yaw)) * moveSpeed;
/*     */       } 
/*     */     } else {
/* 134 */       this.x = 0.0D;
/* 135 */       this.z = 0.0D;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\world\moveEvents\EventMove.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */