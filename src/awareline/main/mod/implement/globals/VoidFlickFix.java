/*    */ package awareline.main.mod.implement.globals;
/*    */ 
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ 
/*    */ public class VoidFlickFix extends Module {
/*    */   public static VoidFlickFix getInstance;
/*    */   
/*    */   public VoidFlickFix() {
/* 10 */     super("VoidFlickFix", ModuleType.Globals);
/* 11 */     getInstance = this;
/* 12 */     setHide(true);
/* 13 */     setEnabledByConvention(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\globals\VoidFlickFix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */