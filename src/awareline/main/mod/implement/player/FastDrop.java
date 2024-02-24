/*    */ package awareline.main.mod.implement.player;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.EventTick;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Value;
/*    */ 
/*    */ public class FastDrop extends Module {
/* 10 */   private final Numbers<Double> clicks = new Numbers("Clicks", Double.valueOf(64.0D), Double.valueOf(1.0D), Double.valueOf(64.0D), Double.valueOf(1.0D));
/*    */   
/*    */   public FastDrop() {
/* 13 */     super("FastDrop", ModuleType.Player);
/* 14 */     addSettings(new Value[] { (Value)this.clicks });
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onTick(EventTick e) {
/* 19 */     if (mc.gameSettings.keyBindDrop.isKeyDown())
/* 20 */       for (int i = 0; i < ((Double)this.clicks.get()).doubleValue(); i++)
/* 21 */         mc.thePlayer.dropOneItem(false);  
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\FastDrop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */