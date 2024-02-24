/*    */ package awareline.main.mod.implement.visual.ctype;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.renderEvents.EventRenderCape;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Mode;
/*    */ import awareline.main.mod.values.Value;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class Cape extends Module {
/* 13 */   private final Mode<String> mode = new Mode("Mode", new String[] { "Enderman", "Creeper", "OneTap", "Astolfo", "Novoline", "PowerX", "LBDonate", "Exhibition", "Rise", "Vape", "Moon", "Valentine" }, "Enderman");
/*    */   
/*    */   public static Cape getInstance;
/*    */ 
/*    */   
/*    */   public Cape() {
/* 19 */     super("Cape", ModuleType.Render);
/* 20 */     addSettings(new Value[] { (Value)this.mode });
/* 21 */     getInstance = this;
/* 22 */     setEnabledByConvention(true);
/*    */   }
/*    */   
/*    */   public ResourceLocation getCape() {
/* 26 */     return new ResourceLocation("client/capes/" + ((String)this.mode.get()).toLowerCase() + ".png");
/*    */   }
/*    */   
/*    */   @EventHandler(4)
/*    */   public void onRender(EventRenderCape event) {
/* 31 */     if (mc.theWorld != null && mc.thePlayer != null) {
/* 32 */       if (Client.instance.friendManager.isFriend(event.getPlayer().getName()) || event.getPlayer() == mc.thePlayer) {
/* 33 */         event.setLocation(new ResourceLocation("client/capes/" + ((String)this.mode.get()).toLowerCase() + ".png"));
/*    */       }
/* 35 */       event.setCancelled(true);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\ctype\Cape.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */