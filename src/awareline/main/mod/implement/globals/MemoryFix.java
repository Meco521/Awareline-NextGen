/*    */ package awareline.main.mod.implement.globals;
/*    */ 
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ 
/*    */ public class MemoryFix extends Module {
/*    */   public static MemoryFix getInstance;
/*    */   
/*    */   public MemoryFix() {
/* 10 */     super("MemoryFixer", ModuleType.Globals);
/* 11 */     setHide(true);
/* 12 */     setEnabledByConvention(true);
/* 13 */     getInstance = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\globals\MemoryFix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */