/*    */ package awareline.main.mod.implement.move;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.EventTick;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Mode;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Value;
/*    */ import awareline.main.utility.BlockUtils;
/*    */ 
/*    */ public class SlimeJump extends Module {
/* 13 */   private final Mode<String> mode = new Mode("Mode", new String[] { "Add", "Set" }, "Add");
/* 14 */   private final Numbers<Double> Motion = new Numbers("Motion", Double.valueOf(0.5D), Double.valueOf(0.0D), Double.valueOf(5.0D), Double.valueOf(0.1D));
/*    */   
/*    */   public SlimeJump() {
/* 17 */     super("SlimeJump", ModuleType.Movement);
/* 18 */     addSettings(new Value[] { (Value)this.mode, (Value)this.Motion });
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onTick(EventTick event) {
/* 24 */     if (mc.thePlayer != null && mc.theWorld != null && BlockUtils.getBlock(mc.thePlayer.getPosition().down()) instanceof net.minecraft.block.BlockSlime)
/* 25 */       if (this.mode.is("Add")) {
/* 26 */         mc.thePlayer.motionY += ((Double)this.Motion.getValue()).doubleValue();
/* 27 */       } else if (this.mode.is("Set")) {
/* 28 */         mc.thePlayer.motionY = ((Double)this.Motion.getValue()).doubleValue();
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\SlimeJump.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */