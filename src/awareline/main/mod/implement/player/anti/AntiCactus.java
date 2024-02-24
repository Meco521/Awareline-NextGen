/*    */ package awareline.main.mod.implement.player.anti;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.BBSetEvent;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ 
/*    */ public class AntiCactus
/*    */   extends Module {
/*    */   public AntiCactus() {
/* 12 */     super("AntiCactus", ModuleType.Player);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onBoundingBox(BBSetEvent event) {
/* 17 */     if (event.getBlock() instanceof net.minecraft.block.BlockCactus)
/* 18 */       event.setBoundingBox(new AxisAlignedBB(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), (event.getPos().getX() + 1), (event.getPos().getY() + 1), (event.getPos().getZ() + 1))); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\anti\AntiCactus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */