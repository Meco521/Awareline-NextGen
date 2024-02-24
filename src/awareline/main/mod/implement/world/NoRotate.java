/*    */ package awareline.main.mod.implement.world;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.packetEvents.EventPacketReceive;
/*    */ import awareline.main.event.events.world.worldChangeEvents.RespawnEvent;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.implement.move.antifallutily.TimeHelper;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C03PacketPlayer;
/*    */ import net.minecraft.network.play.server.S08PacketPlayerPosLook;
/*    */ 
/*    */ public class NoRotate extends Module {
/* 15 */   private final Option<Boolean> confirm = new Option("Confirm", Boolean.valueOf(false));
/* 16 */   private final Option<Boolean> confirmIllegalRotation = new Option("ConfirmIllegalRotation", Boolean.valueOf(false));
/* 17 */   private final Option<Boolean> noZero = new Option("NoZero", Boolean.valueOf(false));
/* 18 */   private final Option<Boolean> onGround = new Option("OnlyGround", Boolean.valueOf(false));
/* 19 */   private final Option<Boolean> respawnCheck = new Option("RespawnCheck", Boolean.valueOf(false));
/* 20 */   private final TimeHelper timer = new TimeHelper();
/*    */   
/*    */   public NoRotate() {
/* 23 */     super("NoRotate", ModuleType.World);
/* 24 */     addSettings(new Value[] { (Value)this.confirm, (Value)this.confirmIllegalRotation, (Value)this.noZero, (Value)this.onGround, (Value)this.respawnCheck });
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onPacket(EventPacketReceive event) {
/* 29 */     if (mc.thePlayer == null) {
/*    */       return;
/*    */     }
/* 32 */     Packet packet = event.getPacket();
/* 33 */     if (((Boolean)this.onGround.get()).booleanValue() && !mc.thePlayer.onGround) {
/*    */       return;
/*    */     }
/* 36 */     if (packet instanceof S08PacketPlayerPosLook) {
/* 37 */       if (((Boolean)this.respawnCheck.get()).booleanValue() && 
/* 38 */         !this.timer.isDelayComplete(5000.0D)) {
/*    */         return;
/*    */       }
/*    */       
/* 42 */       S08PacketPlayerPosLook thePacket = (S08PacketPlayerPosLook)packet;
/* 43 */       if (((Boolean)this.noZero.getValue()).booleanValue() && thePacket.getYaw() == 0.0F && thePacket.getPitch() == 0.0F) {
/*    */         return;
/*    */       }
/* 46 */       if ((((Boolean)this.confirmIllegalRotation.getValue()).booleanValue() || (thePacket.getPitch() <= 90.0F && thePacket.getPitch() >= -90.0F && thePacket.getYaw() != mc.thePlayer.rotationYaw && thePacket
/* 47 */         .getPitch() != mc.thePlayer.rotationPitch)) && (
/* 48 */         (Boolean)this.confirm.getValue()).booleanValue()) {
/* 49 */         sendPacketNoEvent((Packet)new C03PacketPlayer.C05PacketPlayerLook(thePacket.getYaw(), thePacket.getPitch(), mc.thePlayer.onGround));
/*    */       }
/*    */       
/* 52 */       thePacket.yaw = mc.thePlayer.rotationYaw;
/* 53 */       thePacket.pitch = mc.thePlayer.rotationPitch;
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onRespawn(RespawnEvent event) {
/* 59 */     if (((Boolean)this.respawnCheck.get()).booleanValue())
/* 60 */       this.timer.reset(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\world\NoRotate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */