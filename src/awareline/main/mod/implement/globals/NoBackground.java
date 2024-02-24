/*    */ package awareline.main.mod.implement.globals;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ 
/*    */ public class NoBackground extends Module {
/*  8 */   public final Option<Boolean> inventory = new Option("Inventory", Boolean.valueOf(true));
/*  9 */   public final Option<Boolean> chest = new Option("Chest", Boolean.valueOf(true));
/* 10 */   public final Option<Boolean> gameMenu = new Option("GameMenu", Boolean.valueOf(true));
/* 11 */   public final Option<Boolean> allGui = new Option("AllGui", Boolean.valueOf(true));
/*    */   public static NoBackground getInstance;
/*    */   
/*    */   public NoBackground() {
/* 15 */     super("NoBackground", new String[] { "nobg" }, ModuleType.Globals);
/* 16 */     addSettings(new Value[] { (Value)this.inventory, (Value)this.chest, (Value)this.gameMenu, (Value)this.allGui });
/* 17 */     setHide(false);
/* 18 */     setEnabledByConvention(false);
/* 19 */     getInstance = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\globals\NoBackground.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */