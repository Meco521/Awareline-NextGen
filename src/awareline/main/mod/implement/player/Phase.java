/*     */ package awareline.main.mod.implement.player;
/*     */ import awareline.antileak.VerifyData;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.BBSetEvent;
/*     */ import awareline.main.event.events.world.moveEvents.EventMove;
/*     */ import awareline.main.event.events.world.updateEvents.EventPostUpdate;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.move.Step;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.BlockUtils;
/*     */ import awareline.main.utility.timer.TimerUtil;
/*     */ import java.io.Serializable;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.potion.Potion;
/*     */ 
/*     */ public final class Phase extends Module {
/*  22 */   private final String[] modes = new String[] { "HmXix", "Collision", "DownClip" };
/*  23 */   private final Mode<String> mode = new Mode("Mode", this.modes, this.modes[0]);
/*  24 */   public final Numbers<Double> downclip = new Numbers("DownClips", 
/*  25 */       Double.valueOf(3.0D), Double.valueOf(2.0D), Double.valueOf(15.0D), Double.valueOf(1.0D), () -> Boolean.valueOf(((String)this.mode.get()).equals("Downclip")));
/*  26 */   private final TimerUtil tickTimer = new TimerUtil();
/*     */   
/*     */   public Phase() {
/*  29 */     super("Phase", ModuleType.Player);
/*  30 */     addSettings(new Value[] { (Value)this.mode, (Value)this.downclip });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  35 */     if (((String)this.mode.get()).equals("DownClip")) {
/*  36 */       mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - ((Double)this.downclip.get()).doubleValue(), mc.thePlayer.posZ);
/*  37 */       setEnabled(false);
/*     */     } 
/*  39 */     super.onEnable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  44 */     if (!isEnabled(Step.class)) {
/*  45 */       mc.thePlayer.stepHeight = 0.625F;
/*     */     }
/*  47 */     if (this.mode.is("HmXix")) {
/*  48 */       this.tickTimer.reset();
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onCollide(BBSetEvent collide) {
/*  54 */     if (!isEnabled()) {
/*     */       return;
/*     */     }
/*  57 */     if (this.mode.is("Collision")) {
/*  58 */       if (BlockUtils.isInsideBlock()) {
/*  59 */         collide.setBoundingBox(null);
/*     */       }
/*  61 */     } else if (this.mode.is("HmXix") && 
/*  62 */       BlockUtils.isInsideBlock()) {
/*  63 */       collide.setBoundingBox(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onMove(EventMove event) {
/*  70 */     if (this.mode.is("Collision") && isEnabled() && 
/*  71 */       BlockUtils.isInsideBlock()) {
/*     */       
/*  73 */       event.setY(mc.thePlayer.motionY += 0.09000000357627869D);
/*     */       
/*  75 */       event.setY(mc.thePlayer.motionY -= 0.09000000357627869D);
/*     */       
/*  77 */       event.setY(mc.thePlayer.motionY = 0.0D);
/*     */ 
/*     */       
/*  80 */       MoveUtils.INSTANCE.setSpeed(getBaseMoveSpeed());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onUpdate(EventPreUpdate event) {
/*  87 */     setSuffix((Serializable)this.mode.get());
/*  88 */     VerifyData.instance.getClass();
/*     */ 
/*     */ 
/*     */     
/*  92 */     if (this.mode.is("HmXix") && isEnabled()) {
/*  93 */       if (BlockUtils.isInsideBlock()) {
/*  94 */         mc.thePlayer.noClip = true;
/*  95 */         mc.thePlayer.motionY = 0.0D;
/*  96 */         mc.thePlayer.onGround = true;
/*     */       } 
/*  98 */       if (mc.thePlayer.onGround && this.tickTimer.hasTimePassed(2L) && mc.thePlayer.isCollidedHorizontally && (!BlockUtils.isInsideBlock() || mc.thePlayer.isSneaking())) {
/*  99 */         sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
/* 100 */         sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(0.5D, 0.0D, 0.5D, true));
/* 101 */         sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
/* 102 */         sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.2D, mc.thePlayer.posZ, true));
/* 103 */         sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(0.5D, 0.0D, 0.5D, true));
/* 104 */         sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + 0.5D, mc.thePlayer.posY, mc.thePlayer.posZ + 0.5D, true));
/* 105 */         double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
/* 106 */         double x = -Math.sin(yaw) * 0.04D;
/* 107 */         double z = Math.cos(yaw) * 0.04D;
/* 108 */         mc.thePlayer.setPosition(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z);
/* 109 */         this.tickTimer.reset();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPost(EventPostUpdate event) {
/* 116 */     if (this.mode.is("Collision") && isEnabled()) {
/* 117 */       if (mc.thePlayer.stepHeight > 0.0F) mc.thePlayer.stepHeight = 0.0F;
/*     */       
/* 119 */       float moveStrafe = mc.thePlayer.movementInput.getMoveStrafe();
/* 120 */       float moveForward = mc.thePlayer.movementInput.getMoveForward();
/* 121 */       float rotationYaw = mc.thePlayer.rotationYaw;
/*     */       
/* 123 */       double multiplier = 0.3D;
/* 124 */       double mx = -Math.sin(Math.toRadians(rotationYaw));
/* 125 */       double mz = Math.cos(Math.toRadians(rotationYaw));
/* 126 */       double x = moveForward * multiplier * mx + moveStrafe * multiplier * mz;
/* 127 */       double z = moveForward * multiplier * mz - moveStrafe * multiplier * mx;
/*     */       
/* 129 */       if (mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isOnLadder() && mc.thePlayer.onGround) {
/* 130 */         double posX = mc.thePlayer.posX, posY = mc.thePlayer.posY, posZ = mc.thePlayer.posZ;
/*     */         
/* 132 */         sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(posX + x, posY, posZ + z, true));
/* 133 */         sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + 3.0D, posZ, true));
/* 134 */         mc.thePlayer.setPosition(posX + x, posY, posZ + z);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private double getBaseMoveSpeed() {
/* 140 */     double baseSpeed = 0.2873D;
/* 141 */     if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
/* 142 */       int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
/* 143 */       baseSpeed *= 1.0D + 0.2D * (amplifier + 1);
/*     */     } 
/* 145 */     return baseSpeed;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\Phase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */