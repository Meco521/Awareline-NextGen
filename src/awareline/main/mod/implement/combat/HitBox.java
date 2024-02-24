/*    */ package awareline.main.mod.implement.combat;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.EventEntityBorderSize;
/*    */ import awareline.main.event.events.world.EventTick;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Value;
/*    */ 
/*    */ public class HitBox extends Module {
/* 11 */   public final Numbers<Double> minsize = new Numbers("MinSize", Double.valueOf(0.1D), Double.valueOf(0.1D), Double.valueOf(0.8D), Double.valueOf(0.01D));
/* 12 */   public final Numbers<Double> maxsize = new Numbers("MaxSize", Double.valueOf(0.25D), Double.valueOf(0.1D), Double.valueOf(1.0D), Double.valueOf(0.01D));
/*    */   
/*    */   public HitBox() {
/* 15 */     super("HitBox", ModuleType.Combat);
/* 16 */     addSettings(new Value[] { (Value)this.maxsize, (Value)this.minsize });
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onTick(EventTick event) {
/* 21 */     setSuffix(Float.valueOf(((Double)this.maxsize.get()).floatValue()));
/* 22 */     mc.thePlayer.moveStrafing = 1.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getSize() {
/* 27 */     double min = Math.min(((Double)this.minsize.getValue()).doubleValue(), ((Double)this.maxsize.getValue()).doubleValue());
/* 28 */     double max = Math.max(((Double)this.minsize.getValue()).doubleValue(), ((Double)this.maxsize.getValue()).doubleValue());
/* 29 */     return (float)(isEnabled() ? (Math.random() * (max - min) + min) : 0.10000000149011612D);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventEntityBorderSize event) {
/* 34 */     event.setBorderSize(getSize());
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\HitBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */