/*    */ package awareline.main.mod.implement.move;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Mode;
/*    */ import awareline.main.mod.values.Value;
/*    */ import awareline.main.utility.MoveUtils;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.optifine.BlockPosM;
/*    */ 
/*    */ public class FastStairs extends Module {
/* 14 */   private final Mode<String> mode = new Mode("Mode", new String[] { "OldAAC", "NCP", "Legit" }, "NCP");
/*    */   
/*    */   public FastStairs() {
/* 17 */     super("FastStairs", ModuleType.Movement);
/* 18 */     addSettings(new Value[] { (Value)this.mode });
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPreUpdate event) {
/* 23 */     if (!MoveUtils.INSTANCE.isMovingKeyBindingActive()) {
/*    */       return;
/*    */     }
/* 26 */     BlockPosM blockPosM = new BlockPosM(mc.thePlayer.posX, mc.thePlayer.posY - 0.3D, mc.thePlayer.posZ);
/* 27 */     if (mc.theWorld.getBlockState((BlockPos)blockPosM).getBlock() instanceof net.minecraft.block.BlockStairs)
/* 28 */       if (this.mode.is("NCP")) {
/* 29 */         MoveUtils.INSTANCE.toFwd(0.12D);
/* 30 */       } else if (this.mode.is("OldAAC")) {
/* 31 */         mc.thePlayer.motionX *= 1.53D;
/* 32 */         mc.thePlayer.motionZ *= 1.53D;
/* 33 */       } else if (this.mode.is("Legit")) {
/* 34 */         mc.gameSettings.keyBindJump.pressed = (mc.thePlayer.ticksExisted % 2 == 0);
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\FastStairs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */