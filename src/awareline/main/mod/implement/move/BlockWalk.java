/*    */ package awareline.main.mod.implement.move;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.BBSetEvent;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ 
/*    */ public class BlockWalk
/*    */   extends Module {
/* 13 */   private final Option<Boolean> snow = new Option("Snow", Boolean.valueOf(false));
/* 14 */   private final Option<Boolean> web = new Option("Web", Boolean.valueOf(true));
/*    */   
/*    */   public BlockWalk() {
/* 17 */     super("BlockWalk", ModuleType.Movement);
/* 18 */     addSettings(new Value[] { (Value)this.snow, (Value)this.web });
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onCollideWithBlock(BBSetEvent e) {
/* 23 */     if (e.getBlock() instanceof net.minecraft.block.BlockSnow && ((Boolean)this.snow.get()).booleanValue()) {
/* 24 */       e.setBoundingBox(new AxisAlignedBB(e.getBlockPos().getX(), e.getBlockPos().getY(), e.getBlockPos().getZ(), (e.getBlockPos().getX() + 1), (e.getBlockPos().getY() + 1), (e.getBlockPos().getZ() + 1)));
/*    */     }
/* 26 */     if (e.getBlock() instanceof net.minecraft.block.BlockWeb && ((Boolean)this.web.get()).booleanValue())
/* 27 */       e.setBoundingBox(new AxisAlignedBB(e.getBlockPos().getX(), e.getBlockPos().getY(), e.getBlockPos().getZ(), (e.getBlockPos().getX() + 1), (e.getBlockPos().getY() + 1), (e.getBlockPos().getZ() + 1))); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\BlockWalk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */