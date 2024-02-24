/*     */ package awareline.main.mod.implement.combat;
/*     */ 
/*     */ import awareline.antileak.VerifyData;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.moveEvents.EventJump;
/*     */ import awareline.main.event.events.world.moveEvents.EventStrafe;
/*     */ import awareline.main.event.events.world.packetEvents.EventPacketReceive;
/*     */ import awareline.main.event.events.world.packetEvents.EventPacketSend;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.move.Flight;
/*     */ import awareline.main.mod.implement.move.antifallutily.TimeHelper;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import java.io.Serializable;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.potion.Potion;
/*     */ 
/*     */ public class Regen extends Module {
/*  25 */   private final Numbers<Double> packet = new Numbers("Packets", Double.valueOf(10.0D), Double.valueOf(1.0D), Double.valueOf(1000.0D), Double.valueOf(1.0D));
/*  26 */   private final Numbers<Double> regendelay = new Numbers("Delay", Double.valueOf(500.0D), Double.valueOf(0.0D), Double.valueOf(10000.0D), Double.valueOf(10.0D));
/*  27 */   private final Option<Boolean> ground = new Option("OnlyGround", Boolean.valueOf(false));
/*  28 */   private final Option<Boolean> check = new Option("StopFly", Boolean.valueOf(false));
/*  29 */   private final Option<Boolean> onlyeffect = new Option("OnlyEffect", Boolean.valueOf(false));
/*  30 */   private final Numbers<Double> health = new Numbers("Health", Double.valueOf(15.0D), Double.valueOf(0.5D), Double.valueOf(20.0D), Double.valueOf(0.5D));
/*  31 */   private final Mode<String> mode = new Mode("Mode", new String[] { "Normal", "Normal2", "Ghostly", "WorldGuard", "Old" }, "Normal");
/*     */   
/*     */   private final TimeHelper delay;
/*     */   
/*     */   private int ticks;
/*     */   private float yaw;
/*     */   private float pitch;
/*     */   
/*     */   public Regen() {
/*  40 */     super("Regen", new String[] { "reg" }, ModuleType.Combat);
/*  41 */     this.delay = new TimeHelper();
/*  42 */     addSettings(new Value[] { (Value)this.mode, (Value)this.health, (Value)this.ground, (Value)this.onlyeffect, (Value)this.check, (Value)this.packet, (Value)this.regendelay });
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onJump(EventJump event) {
/*  47 */     if (this.mode.is("WorldGuard") && 
/*  48 */       mc.thePlayer.onGround && mc.thePlayer.getHealth() < ((Double)this.health.getValue()).floatValue() && this.ticks <= 1) {
/*  49 */       event.setCancelled(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onStrafe(EventStrafe event) {
/*  56 */     if (this.mode.is("WorldGuard") && 
/*  57 */       mc.thePlayer.onGround && mc.thePlayer.getHealth() < ((Double)this.health.getValue()).floatValue() && this.ticks <= 1) {
/*  58 */       event.setSpeed(0.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPacket(EventPacketReceive event) {
/*  65 */     if (this.mode.is("WorldGuard") && 
/*  66 */       event.getPacket() instanceof net.minecraft.network.play.server.S2DPacketOpenWindow && this.ticks <= 1) {
/*  67 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPacket(EventPacketSend event) {
/*  74 */     if (this.mode.is("WorldGuard")) {
/*  75 */       Packet<?> p = event.getPacket();
/*     */       
/*  77 */       if (p instanceof C02PacketUseEntity && mc.thePlayer.onGround && mc.thePlayer.getHealth() < ((Double)this.health.getValue()).floatValue() && this.ticks <= 1) {
/*  78 */         C02PacketUseEntity wrapper = (C02PacketUseEntity)p;
/*     */         
/*  80 */         if (wrapper.getAction().equals(C02PacketUseEntity.Action.ATTACK)) {
/*  81 */           event.setCancelled(true);
/*     */           
/*  83 */           sendNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
/*  84 */           sendNoEvent((Packet)wrapper);
/*  85 */           sendNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.05D, mc.thePlayer.posZ, false));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPre(EventPreUpdate event) {
/*  93 */     VerifyData.instance.getClass();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     setSuffix((Serializable)this.mode.get());
/*     */     
/* 102 */     double value = ((Double)this.packet.get()).doubleValue();
/*     */     
/* 104 */     if (((Boolean)this.ground.get()).booleanValue() && !mc.thePlayer.onGround) {
/*     */       return;
/*     */     }
/* 107 */     if (((Boolean)this.check.get()).booleanValue() && Flight.getInstance.isEnabled()) {
/*     */       return;
/*     */     }
/* 110 */     if (((Boolean)this.onlyeffect.get()).booleanValue() && !mc.thePlayer.isPotionActive(Potion.regeneration)) {
/*     */       return;
/*     */     }
/* 113 */     if (this.mode.is("WorldGuard")) {
/* 114 */       if (mc.thePlayer.onGround && mc.thePlayer.getHealth() < ((Double)this.health.getValue()).floatValue()) {
/* 115 */         if (this.ticks <= 1) {
/* 116 */           event.setPosY(event.getPosY() - 0.05D);
/* 117 */           event.setYaw(this.yaw);
/* 118 */           event.setPitch(this.pitch);
/* 119 */           this.ticks++;
/*     */         } 
/*     */       } else {
/* 122 */         this.yaw = event.getYaw();
/* 123 */         this.pitch = event.getPitch();
/* 124 */         this.ticks = 0;
/*     */       } 
/*     */     }
/*     */     
/* 128 */     if (this.delay.isDelayComplete(((Double)this.regendelay.get()).intValue()) && playerCheck()) {
/* 129 */       for (int i = 0; i < value; i++) {
/* 130 */         switch ((String)this.mode.get()) {
/*     */           case "Normal":
/* 132 */             sendPacketNoEvent((Packet)new C03PacketPlayer(true));
/*     */             break;
/*     */           
/*     */           case "Normal2":
/* 136 */             sendPacketNoEvent((Packet)new C03PacketPlayer());
/*     */             break;
/*     */           
/*     */           case "Ghostly":
/* 140 */             sendPacketNoEvent((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-9D, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
/*     */             break;
/*     */           
/*     */           case "Old":
/* 144 */             sendPacketNoEvent((Packet)new C03PacketPlayer(mc.thePlayer.onGround));
/*     */             break;
/*     */         } 
/*     */       
/*     */       } 
/* 149 */       this.delay.reset();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean playerCheck() {
/* 154 */     return (mc.thePlayer.fallDistance <= 2.0F && mc.thePlayer.getHealth() < ((Double)this.health.getValue()).intValue() && mc.thePlayer.getFoodStats().getFoodLevel() >= 19);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\Regen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */