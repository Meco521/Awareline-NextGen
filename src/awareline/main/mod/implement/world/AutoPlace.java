/*    */ package awareline.main.mod.implement.world;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.renderEvents.EventRender;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Value;
/*    */ 
/*    */ public class AutoPlace extends Module {
/* 11 */   public final Numbers<Double> delay = new Numbers("PlaceDelay", 
/* 12 */       Double.valueOf(2.0D), Double.valueOf(1.0D), Double.valueOf(10.0D), Double.valueOf(1.0D));
/*    */   
/*    */   public AutoPlace() {
/* 15 */     super("AutoPlace", ModuleType.World);
/* 16 */     addSettings(new Value[] { (Value)this.delay });
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onRender(EventRender render) {
/* 21 */     if (isHoldingBlock() && 
/* 22 */       mc.thePlayer.ticksExisted % ((Double)this.delay.get()).intValue() == 0) {
/* 23 */       mc.rightClickMouse();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean isHoldingBlock() {
/* 29 */     return (mc.thePlayer.getHeldItem() != null && (mc.thePlayer.getHeldItem()).stackSize > 0 && mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemBlock);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\world\AutoPlace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */