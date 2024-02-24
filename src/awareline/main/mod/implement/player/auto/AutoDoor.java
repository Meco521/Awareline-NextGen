/*    */ package awareline.main.mod.implement.player.auto;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.utility.MoveUtils;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockDoor;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ public class AutoDoor extends Module {
/*    */   public AutoDoor() {
/* 14 */     super("AutoDoor", ModuleType.Player);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPreUpdate e) {
/* 19 */     double yaw = MoveUtils.INSTANCE.getDirection();
/* 20 */     double x = mc.thePlayer.posX + -Math.sin(yaw) * 1.0D;
/* 21 */     double z = mc.thePlayer.posZ + Math.cos(yaw) * 1.0D;
/* 22 */     double y = mc.thePlayer.posY;
/*    */     
/* 24 */     BlockPos pos = new BlockPos(x, y, z);
/* 25 */     Block b = mc.theWorld.getBlockState(pos).getBlock();
/* 26 */     if (b instanceof BlockDoor && 
/* 27 */       !BlockDoor.isOpen((IBlockAccess)mc.theWorld, pos)) {
/* 28 */       mc.thePlayer.swingItem();
/* 29 */       mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), pos, mc.objectMouseOver.sideHit, mc.objectMouseOver.hitVec);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\auto\AutoDoor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */