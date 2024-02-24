/*    */ package awareline.main.mod.implement.combat;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.packetEvents.EventPacketReceive;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class FastTiger
/*    */   extends Module
/*    */ {
/* 16 */   public final Numbers<Double> speed = new Numbers("Speed", 
/* 17 */       Double.valueOf(0.8D), Double.valueOf(0.01D), Double.valueOf(3.0D), Double.valueOf(0.1D));
/* 18 */   public final Numbers<Double> triggerDistance = new Numbers("Distance", 
/* 19 */       Double.valueOf(5.0D), Double.valueOf(1.0D), Double.valueOf(40.0D), Double.valueOf(1.0D));
/* 20 */   public final Numbers<Double> stoppingdistance = new Numbers("StoppingDistance", 
/* 21 */       Double.valueOf(0.8D), Double.valueOf(0.01D), Double.valueOf(3.0D), Double.valueOf(0.1D));
/* 22 */   private final Option<Boolean> timerBoost = new Option("Timer", Boolean.valueOf(false));
/* 23 */   private final Option<Boolean> jumper = new Option("Jump", Boolean.valueOf(false));
/* 24 */   private final Option<Boolean> lagBack = new Option("LagBackCheck", Boolean.valueOf(false));
/*    */   public Entity target;
/*    */   
/*    */   public FastTiger() {
/* 28 */     super("FastTiger", ModuleType.Combat);
/* 29 */     addSettings(new Value[] { (Value)this.triggerDistance, (Value)this.stoppingdistance, (Value)this.speed, (Value)this.jumper, (Value)this.timerBoost, (Value)this.lagBack });
/*    */   }
/*    */   
/*    */   @EventHandler(1)
/*    */   public void onPacket(EventPacketReceive e) {
/* 34 */     if (!((Boolean)this.lagBack.get()).booleanValue()) {
/*    */       return;
/*    */     }
/* 37 */     if (e.getPacket() instanceof net.minecraft.network.play.server.S08PacketPlayerPosLook) {
/* 38 */       checkModule(FastTiger.class);
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPreMotionEvent(EventPreUpdate e) {
/* 44 */     double minDistance = Double.MAX_VALUE;
/* 45 */     for (Entity entity : mc.theWorld.loadedEntityList) {
/* 46 */       if (entity instanceof net.minecraft.entity.player.EntityPlayer && entity != mc.thePlayer) {
/* 47 */         double distance = mc.thePlayer.getDistanceToEntity(entity);
/* 48 */         if (distance < minDistance) {
/* 49 */           minDistance = distance;
/* 50 */           this.target = entity;
/*    */         } 
/*    */       } 
/*    */     } 
/* 54 */     if (this.target != null && minDistance <= ((Double)this.triggerDistance.getValue()).floatValue() && 
/* 55 */       this.target != null) {
/* 56 */       double dx = this.target.posX - mc.thePlayer.posX;
/* 57 */       double dz = this.target.posZ - mc.thePlayer.posZ;
/* 58 */       double distance = Math.sqrt(dx * dx + dz * dz);
/* 59 */       if (distance > ((Double)this.stoppingdistance.getValue()).floatValue()) {
/* 60 */         jump();
/* 61 */         mc.thePlayer.motionX = dx / distance * ((Double)this.speed.getValue()).floatValue();
/* 62 */         mc.thePlayer.motionZ = dz / distance * ((Double)this.speed.getValue()).floatValue();
/* 63 */         setTimer(10.0F);
/*    */       } else {
/* 65 */         setTimer(1.0F);
/* 66 */         mc.thePlayer.motionX = 0.0D;
/* 67 */         mc.thePlayer.motionZ = 0.0D;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void jump() {
/* 74 */     if (!((Boolean)this.jumper.get()).booleanValue()) {
/*    */       return;
/*    */     }
/* 77 */     if (mc.thePlayer.onGround) {
/* 78 */       mc.thePlayer.jump();
/*    */     }
/*    */   }
/*    */   
/*    */   public void setTimer(float timer) {
/* 83 */     if (!((Boolean)this.timerBoost.get()).booleanValue()) {
/*    */       return;
/*    */     }
/* 86 */     mc.timer.timerSpeed = timer;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\FastTiger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */