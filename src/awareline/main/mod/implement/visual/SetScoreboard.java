/*    */ package awareline.main.mod.implement.visual;
/*    */ 
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ 
/*    */ public class SetScoreboard extends Module {
/* 10 */   public final Option<Boolean> hideboard = new Option("HideBoard", Boolean.valueOf(false));
/* 11 */   public final Option<Boolean> fastbord = new Option("FastBord", Boolean.valueOf(false));
/* 12 */   public final Option<Boolean> Norednumber = new Option("NoRedNumber", Boolean.valueOf(true));
/* 13 */   public final Option<Boolean> noServername = new Option("NoServerName", Boolean.valueOf(false));
/* 14 */   public final Option<Boolean> noanyfont = new Option("NoAnyFont", Boolean.valueOf(false));
/* 15 */   public final Numbers<Double> X = new Numbers("X", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(1000.0D), Double.valueOf(1.0D));
/* 16 */   public final Numbers<Double> Y = new Numbers("Y", Double.valueOf(4.5D), Double.valueOf(-300.0D), Double.valueOf(300.0D), Double.valueOf(1.0D));
/*    */   public static SetScoreboard getInstance;
/*    */   
/*    */   public SetScoreboard() {
/* 20 */     super("Scoreboard", ModuleType.Render);
/* 21 */     addSettings(new Value[] { (Value)this.X, (Value)this.Y, (Value)this.hideboard, (Value)this.fastbord, (Value)this.Norednumber, (Value)this.noServername, (Value)this.noanyfont });
/* 22 */     getInstance = this;
/* 23 */     setEnabledByConvention(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\SetScoreboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */