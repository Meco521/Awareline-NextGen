/*    */ package awareline.main.mod.implement.world;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.EventTick;
/*    */ import awareline.main.event.events.world.packetEvents.EventPacketReceive;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Mode;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Value;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class TimeChanger extends Module {
/* 13 */   private final String[] AntiMode = new String[] { "Custom", "Change" };
/* 14 */   private final Mode<String> mode = new Mode("Mode", this.AntiMode, this.AntiMode[0]);
/* 15 */   private final Numbers<Double> time = new Numbers("Time", 
/* 16 */       Double.valueOf(8000.0D), Double.valueOf(0.0D), Double.valueOf(24000.0D), Double.valueOf(1000.0D), () -> Boolean.valueOf(this.mode.is("Custom")));
/*    */   int i;
/*    */   
/*    */   public TimeChanger() {
/* 20 */     super("TimeChanger", ModuleType.World);
/* 21 */     addSettings(new Value[] { (Value)this.mode, (Value)this.time });
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public final void onReceivePacket(EventPacketReceive event) {
/* 26 */     if (event.getPacket() instanceof net.minecraft.network.play.server.S03PacketTimeUpdate) {
/* 27 */       event.setCancelled(true);
/*    */     }
/* 29 */     if (this.mode.is("Custom")) {
/* 30 */       mc.theWorld.setWorldTime(((Double)this.time.getValue()).intValue());
/* 31 */     } else if (this.mode.is("Change")) {
/* 32 */       mc.theWorld.setWorldTime(this.i);
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public final void onMotionUpdate(EventTick event) {
/* 38 */     setSuffix((Serializable)this.mode.getValue());
/* 39 */     if (this.mode.is("Custom")) {
/* 40 */       mc.theWorld.setWorldTime(((Double)this.time.getValue()).intValue());
/* 41 */     } else if (this.mode.is("Change")) {
/* 42 */       this.i += 100;
/* 43 */       if (this.i > 24000) {
/* 44 */         this.i = 0;
/*    */       }
/* 46 */       mc.theWorld.setWorldTime(this.i);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\world\TimeChanger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */