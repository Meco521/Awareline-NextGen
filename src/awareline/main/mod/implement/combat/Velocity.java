/*     */ package awareline.main.mod.implement.combat;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.BBSetEvent;
/*     */ import awareline.main.event.events.world.EventAttack;
/*     */ import awareline.main.event.events.world.moveEvents.EventJump;
/*     */ import awareline.main.event.events.world.packetEvents.EventPacketReceive;
/*     */ import awareline.main.event.events.world.packetEvents.EventPacketSend;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.move.Flight;
/*     */ import awareline.main.mod.implement.move.antifallutily.TimeHelper;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*     */ import awareline.main.utility.PlayerUtil;
/*     */ import java.io.Serializable;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.network.play.server.S12PacketEntityVelocity;
/*     */ import net.minecraft.network.play.server.S27PacketExplosion;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ 
/*     */ public class Velocity
/*     */   extends Module
/*     */ {
/*  31 */   private final String[] modes = new String[] { "Normal", "Silky", "Watchdog", "SNCP", "AAC4.3.6", "AAC4.4.0", "AAC5.2.0", "Matrix", "Vulcan", "MMC", "UniversoCraft", "Karhu", "Intave", "Jump", "Legit" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   public final Mode<String> mode = new Mode("Mode", this.modes, this.modes[0]);
/*     */   
/*  40 */   private final Numbers<Double> h = new Numbers("Horizontal", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(100.0D), Double.valueOf(1.0D), () -> Boolean.valueOf(this.mode.is("Normal"))), v = new Numbers("Vertical", 
/*  41 */       Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(100.0D), Double.valueOf(1.0D), () -> Boolean.valueOf(this.mode.is("Normal")));
/*     */   
/*  43 */   private final Numbers<Double> hurttime = new Numbers("HurtTime", Double.valueOf(20.0D), Double.valueOf(0.0D), Double.valueOf(20.0D), Double.valueOf(1.0D));
/*     */   
/*  45 */   private final Option<Boolean> ground = new Option("OnlyGround", Boolean.valueOf(false)); private final Option<Boolean> nomove = new Option("OnlyNoMove", Boolean.valueOf(false)); private final Option<Boolean> waterchecks = new Option("WaterCheck", 
/*  46 */       Boolean.valueOf(false)); private final Option<Boolean> detect = new Option("CheckKB", Boolean.valueOf(false));
/*  47 */   private final Option<Boolean> onlywhentarget = new Option("OnlyWhenTargeting", Boolean.valueOf(false));
/*     */   
/*  49 */   private final TimeHelper knockBackTimer = new TimeHelper();
/*     */   
/*     */   private boolean wtfBoolean = true;
/*     */   
/*     */   private EntityLivingBase target;
/*     */   
/*     */   private int velocity;
/*     */   
/*     */   private boolean jump;
/*     */   
/*     */   private boolean attacked;
/*     */   
/*     */   public Velocity() {
/*  62 */     super("Velocity", ModuleType.Combat);
/*  63 */     addSettings(new Value[] { (Value)this.mode, (Value)this.h, (Value)this.v, (Value)this.hurttime, (Value)this.detect, (Value)this.onlywhentarget, (Value)this.nomove, (Value)this.ground, (Value)this.waterchecks });
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPre(EventPacketReceive e) {
/*  68 */     this.velocity++;
/*  69 */     this.jump = false;
/*     */     
/*  71 */     if (mc.thePlayer.hurtResistantTime > ((Double)this.hurttime.get()).intValue()) {
/*     */       return;
/*     */     }
/*  74 */     if (((Boolean)this.ground.get()).booleanValue() && !mc.thePlayer.onGround) {
/*     */       return;
/*     */     }
/*  77 */     if (((Boolean)this.nomove.get()).booleanValue() && isMoving()) {
/*     */       return;
/*     */     }
/*  80 */     if (((Boolean)this.waterchecks.get()).booleanValue() && PlayerUtil.inLiquid()) {
/*     */       return;
/*     */     }
/*  83 */     if (((Boolean)this.onlywhentarget.get()).booleanValue()) {
/*  84 */       getTarget();
/*  85 */       if (!(e.getPacket() instanceof net.minecraft.network.play.client.C0APacketAnimation) && this.target == null) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/*  90 */     if (this.mode.is("Jump")) {
/*  91 */       if (mc.thePlayer.hurtTime > 6 && mc.thePlayer.onGround) {
/*  92 */         mc.thePlayer.jump();
/*     */       }
/*  94 */     } else if (this.mode.is("AAC5.2.0")) {
/*  95 */       if (e.getPacket() instanceof S12PacketEntityVelocity || e.getPacket() instanceof S27PacketExplosion) {
/*  96 */         sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, Double.MAX_VALUE, mc.thePlayer.posZ, true));
/*  97 */         e.cancel();
/*     */       } 
/*  99 */     } else if (this.mode.is("AAC4.3.6")) {
/* 100 */       if (mc.thePlayer.hurtResistantTime > 0 && !bad(false, true, false, false, false)) {
/* 101 */         mc.thePlayer.motionX *= 0.6000000238418579D;
/* 102 */         mc.thePlayer.motionZ *= 0.6000000238418579D;
/*     */       } 
/* 104 */     } else if (this.mode.is("AAC4.4.0")) {
/* 105 */       if (mc.thePlayer.hurtResistantTime > 0 && !bad(false, true, false, false, false)) {
/* 106 */         mc.thePlayer.motionX *= 0.699999988079071D;
/* 107 */         mc.thePlayer.motionZ *= 0.699999988079071D;
/*     */       } 
/* 109 */     } else if (this.mode.is("Matrix")) {
/* 110 */       if (mc.thePlayer.hurtTime > 0) {
/* 111 */         mc.thePlayer.motionX *= 0.6D;
/* 112 */         mc.thePlayer.motionZ *= 0.6D;
/*     */       } 
/* 114 */     } else if (this.mode.is("Intave")) {
/* 115 */       if (mc.objectMouseOver.typeOfHit.equals(MovingObjectPosition.MovingObjectType.ENTITY) && mc.thePlayer.hurtTime > 0 && !this.attacked) {
/* 116 */         mc.thePlayer.motionX *= 0.6D;
/* 117 */         mc.thePlayer.motionZ *= 0.6D;
/* 118 */         mc.thePlayer.setSprinting(false);
/*     */       } 
/*     */       
/* 121 */       this.attacked = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onAttack(EventAttack event) {
/* 127 */     this.attacked = true;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onBB(BBSetEvent event) {
/* 132 */     if (this.mode.is("Karhu") && 
/* 133 */       event.getBlock() instanceof net.minecraft.block.BlockAir && mc.thePlayer.hurtTime > 0 && mc.thePlayer.ticksSinceVelocity <= 9) {
/* 134 */       double x = event.getBlockPos().getX(), y = event.getBlockPos().getY(), z = event.getBlockPos().getZ();
/*     */       
/* 136 */       if (y == Math.floor(mc.thePlayer.posY) + 1.0D) {
/* 137 */         event.setBoundingBox(AxisAlignedBB.fromBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D).offset(x, y, z));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onMove(EventJump e) {
/* 145 */     if (this.mode.is("Legit")) {
/* 146 */       if (this.jump && isMoving()) {
/* 147 */         e.setCancelled(true);
/*     */       }
/* 149 */     } else if (this.mode.is("AAC4.4.0") && 
/* 150 */       this.jump) {
/* 151 */       e.setCancelled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onPacketSend(EventPacketSend e) {
/* 158 */     if (this.mode.is("Vulcan") && 
/* 159 */       mc.thePlayer.hurtTime > 0 && e.getPacket() instanceof net.minecraft.network.play.client.C0FPacketConfirmTransaction) {
/* 160 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onPacket(EventPacketReceive e) {
/* 167 */     setSuffix(this.mode.is("Normal") ? (this.v.get() + "% " + this.h.get() + "%") : (Serializable)this.mode.get());
/*     */     
/* 169 */     if (((Boolean)this.detect.get()).booleanValue())
/*     */     {
/*     */       
/* 172 */       if (e.getPacket() instanceof S12PacketEntityVelocity) {
/* 173 */         S12PacketEntityVelocity veloPacket = (S12PacketEntityVelocity)e.getPacket();
/*     */         
/* 175 */         if (veloPacket.getEntityID() != mc.thePlayer.getEntityId()) {
/*     */           return;
/*     */         }
/* 178 */         if (this.knockBackTimer.isDelayComplete(250.0D) && mc.thePlayer.ticksExisted > 60) {
/* 179 */           if (this.wtfBoolean && mc.thePlayer.hurtResistantTime == 0 && mc.thePlayer.velocityChanged) {
/* 180 */             noti(getHUDName(), "You may have been KB checked!", 3000L, ClientNotification.Type.WARNING);
/* 181 */             this.wtfBoolean = false;
/* 182 */             mc.thePlayer.addVelocity(veloPacket.getMotionX(), veloPacket.getMotionY(), veloPacket.getMotionZ());
/*     */           } else {
/* 184 */             this.wtfBoolean = false;
/*     */           } 
/* 186 */         } else if (this.wtfBoolean && mc.thePlayer.hurtResistantTime > 0) {
/* 187 */           this.wtfBoolean = false;
/*     */         } 
/*     */         
/* 190 */         e.setCancelled(true);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 195 */     if (mc.thePlayer.hurtResistantTime > ((Double)this.hurttime.get()).intValue()) {
/*     */       return;
/*     */     }
/* 198 */     if (((Boolean)this.ground.get()).booleanValue() && !mc.thePlayer.onGround) {
/*     */       return;
/*     */     }
/* 201 */     if (((Boolean)this.nomove.get()).booleanValue() && isMoving()) {
/*     */       return;
/*     */     }
/* 204 */     if (((Boolean)this.waterchecks.get()).booleanValue() && PlayerUtil.inLiquid()) {
/*     */       return;
/*     */     }
/* 207 */     if (((Boolean)this.onlywhentarget.get()).booleanValue() && 
/* 208 */       !(e.getPacket() instanceof net.minecraft.network.play.client.C0APacketAnimation) && this.target == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 213 */     if (this.mode.is("Normal")) {
/* 214 */       if (((Double)this.h.get()).equals(Double.valueOf(0.0D)) && ((Double)this.v.get()).equals(Double.valueOf(0.0D))) {
/* 215 */         if (e.getPacket() instanceof S12PacketEntityVelocity || e.getPacket() instanceof S27PacketExplosion) {
/* 216 */           e.setCancelled(true);
/*     */         
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 222 */         if (e.getPacket() instanceof S12PacketEntityVelocity) {
/* 223 */           S12PacketEntityVelocity s12 = (S12PacketEntityVelocity)e.getPacket();
/* 224 */           if (s12.getEntityID() == mc.thePlayer.getEntityId()) {
/* 225 */             s12.setX((int)(s12.getMotionX() * ((Double)this.v.get()).doubleValue() / 100.0D));
/* 226 */             s12.setY((int)(s12.getMotionY() * ((Double)this.h.get()).doubleValue() / 100.0D));
/* 227 */             s12.setZ((int)(s12.getMotionZ() * ((Double)this.v.get()).doubleValue() / 100.0D));
/*     */           } 
/*     */         } 
/*     */         
/* 231 */         if (e.getPacket() instanceof S27PacketExplosion) {
/* 232 */           S27PacketExplosion s27 = (S27PacketExplosion)e.getPacket();
/* 233 */           s27.setX((s27.func_149149_c() * ((Double)this.v.get()).floatValue() / 100.0F));
/* 234 */           s27.setY((s27.func_149144_d() * ((Double)this.h.get()).floatValue() / 100.0F));
/* 235 */           s27.setZ((s27.func_149147_e() * ((Double)this.v.get()).floatValue() / 100.0F));
/*     */         }
/*     */       
/*     */       } 
/* 239 */     } else if (this.mode.is("Watchdog")) {
/*     */ 
/*     */       
/* 242 */       if (e.getPacket() instanceof S12PacketEntityVelocity) {
/* 243 */         S12PacketEntityVelocity veloPacket = (S12PacketEntityVelocity)e.getPacket();
/* 244 */         if (veloPacket.getEntityID() == mc.thePlayer.getEntityId()) {
/* 245 */           veloPacket.setX((int)(veloPacket.getMotionX() * 0.0D / 100.0D));
/* 246 */           veloPacket.setY((int)(veloPacket.getMotionY() * 100.0D / 100.0D));
/* 247 */           veloPacket.setZ((int)(veloPacket.getMotionZ() * 0.0D / 100.0D));
/*     */         } 
/*     */       } 
/* 250 */       if (e.getPacket() instanceof S27PacketExplosion) {
/* 251 */         S27PacketExplosion packet = (S27PacketExplosion)e.getPacket();
/* 252 */         packet.setX((packet.func_149149_c() * 0.0F / 100.0F));
/* 253 */         packet.setY((packet.func_149144_d() * 100.0F / 100.0F));
/* 254 */         packet.setZ((packet.func_149147_e() * 0.0F / 100.0F));
/*     */       }
/*     */     
/* 257 */     } else if (this.mode.is("Silky")) {
/* 258 */       Packet p = e.getPacket();
/* 259 */       if (p instanceof S12PacketEntityVelocity) {
/* 260 */         S12PacketEntityVelocity packet = (S12PacketEntityVelocity)p;
/* 261 */         int entId = packet.getEntityID();
/* 262 */         if (entId == mc.thePlayer.getEntityId() && !Flight.getInstance.isEnabled()) {
/* 263 */           if (mc.thePlayer.onGround) {
/* 264 */             e.setCancelled(true);
/* 265 */             mc.thePlayer.motionY = packet.getY() / 8000.0D;
/*     */           } else {
/* 267 */             e.setCancelled(true);
/*     */           } 
/*     */         }
/*     */       } 
/* 271 */     } else if (this.mode.is("SNCP")) {
/* 272 */       if (e.getPacket() instanceof S12PacketEntityVelocity || e.getPacket() instanceof S27PacketExplosion) {
/* 273 */         if (mc.thePlayer.onGround) {
/* 274 */           e.setCancelled(true);
/*     */         }
/* 276 */         else if (mc.thePlayer.hurtResistantTime > 0) {
/* 277 */           mc.thePlayer.motionX *= 0.6D;
/* 278 */           mc.thePlayer.motionZ *= 0.6D;
/*     */         }
/*     */       
/*     */       }
/* 282 */     } else if (this.mode.is("Legit")) {
/*     */       
/* 284 */       if (!mc.thePlayer.onGround) {
/*     */         return;
/*     */       }
/*     */       
/* 288 */       Packet<?> p = e.getPacket();
/*     */       
/* 290 */       if (p instanceof S12PacketEntityVelocity) {
/* 291 */         S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity)p;
/*     */         
/* 293 */         if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
/* 294 */           this.jump = true;
/*     */         }
/*     */       } 
/*     */       
/* 298 */       if (p instanceof S27PacketExplosion) {
/* 299 */         this.jump = true;
/*     */       }
/*     */     }
/* 302 */     else if (this.mode.is("MMC")) {
/* 303 */       Packet<?> packet = e.getPacket();
/*     */       
/* 305 */       if (this.velocity > 20) {
/* 306 */         if (packet instanceof S12PacketEntityVelocity) {
/* 307 */           S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity)packet;
/*     */           
/* 309 */           if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
/* 310 */             e.setCancelled(true);
/* 311 */             this.velocity = 0;
/*     */           } 
/* 313 */         } else if (packet instanceof S27PacketExplosion) {
/* 314 */           e.setCancelled(true);
/* 315 */           this.velocity = 0;
/*     */         }
/*     */       
/*     */       }
/* 319 */     } else if (this.mode.is("UniversoCraft")) {
/* 320 */       Packet<?> p = e.getPacket();
/*     */       
/* 322 */       if (p instanceof S12PacketEntityVelocity) {
/* 323 */         S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity)p;
/*     */         
/* 325 */         if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
/* 326 */           e.setCancelled(true);
/* 327 */           mc.thePlayer.motionY += 0.1D - Math.random() / 100.0D;
/*     */         } 
/*     */       } 
/*     */       
/* 331 */       if (p instanceof S27PacketExplosion) {
/* 332 */         e.setCancelled(true);
/* 333 */         mc.thePlayer.motionY += 0.1D - Math.random() / 100.0D;
/*     */       }
/*     */     
/* 336 */     } else if (this.mode.is("Vulcan")) {
/* 337 */       Packet<?> p = e.getPacket();
/*     */       
/* 339 */       double horizontal = ((Double)this.h.get()).doubleValue();
/* 340 */       double vertical = ((Double)this.v.get()).doubleValue();
/*     */       
/* 342 */       if (p instanceof S12PacketEntityVelocity) {
/* 343 */         S12PacketEntityVelocity s12 = (S12PacketEntityVelocity)p;
/*     */         
/* 345 */         if (s12.getEntityID() == mc.thePlayer.getEntityId()) {
/* 346 */           if (horizontal == 0.0D && vertical == 0.0D) {
/* 347 */             e.setCancelled(true);
/*     */             
/*     */             return;
/*     */           } 
/* 351 */           s12.motionX = (int)(s12.motionX * horizontal / 100.0D);
/* 352 */           s12.motionY = (int)(s12.motionY * vertical / 100.0D);
/* 353 */           s12.motionZ = (int)(s12.motionZ * horizontal / 100.0D);
/*     */           
/* 355 */           e.setPacket((Packet)s12);
/*     */         } 
/*     */       } 
/*     */       
/* 359 */       if (p instanceof S27PacketExplosion) {
/* 360 */         S27PacketExplosion s27 = (S27PacketExplosion)p;
/*     */         
/* 362 */         if (horizontal == 0.0D && vertical == 0.0D) {
/* 363 */           e.setCancelled(true);
/*     */           
/*     */           return;
/*     */         } 
/* 367 */         s27.posX *= horizontal / 100.0D;
/* 368 */         s27.posY *= vertical / 100.0D;
/* 369 */         s27.posZ *= horizontal / 100.0D;
/*     */         
/* 371 */         e.setPacket((Packet)s27);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void getTarget() {
/* 377 */     if (KillAura.getInstance.getTarget() != null) {
/* 378 */       this.target = KillAura.getInstance.getTarget();
/*     */       return;
/*     */     } 
/* 381 */     MovingObjectPosition mouseOver = mc.objectMouseOver;
/* 382 */     if (mouseOver != null)
/* 383 */       if (mouseOver.entityHit instanceof EntityLivingBase) {
/* 384 */         this.target = (EntityLivingBase)mouseOver.entityHit;
/*     */       } else {
/* 386 */         this.target = null;
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\Velocity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */