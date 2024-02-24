/*    */ package awareline.main.mod.implement.globals;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Value;
/*    */ 
/*    */ public class TabListAnimations extends Module {
/*  8 */   public final Numbers<Double> maxDownY = new Numbers("MaxDownY", Double.valueOf(10.0D), Double.valueOf(10.0D), Double.valueOf(200.0D), Double.valueOf(10.0D));
/*    */   public static TabListAnimations getInstance;
/*    */   
/*    */   public TabListAnimations() {
/* 12 */     super("TabListAnimations", ModuleType.Globals);
/* 13 */     addSettings(new Value[] { (Value)this.maxDownY });
/* 14 */     setHide(false);
/* 15 */     setEnabledByConvention(false);
/* 16 */     getInstance = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\globals\TabListAnimations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */