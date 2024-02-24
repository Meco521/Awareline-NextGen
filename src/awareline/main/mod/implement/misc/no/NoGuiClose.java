/*    */ package awareline.main.mod.implement.misc.no;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.packetEvents.EventPacketReceive;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ 
/*    */ public class NoGuiClose
/*    */   extends Module {
/* 12 */   public final Option<Boolean> chatOnly = new Option("ChatOnly", Boolean.valueOf(false));
/*    */   
/*    */   public NoGuiClose() {
/* 15 */     super("NoGuiClose", ModuleType.Misc);
/* 16 */     addSettings(new Value[] { (Value)this.chatOnly });
/* 17 */     setEnabledByConvention(true);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPacketReceive(EventPacketReceive event) {
/* 22 */     if (event.getPacket() instanceof net.minecraft.network.play.server.S2EPacketCloseWindow && (mc.currentScreen instanceof net.minecraft.client.gui.GuiChat || !((Boolean)this.chatOnly.get()).booleanValue()))
/* 23 */       event.setCancelled(true); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\no\NoGuiClose.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */