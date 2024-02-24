/*    */ package awareline.main.mod.implement.globals;
/*    */ 
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ 
/*    */ public class NoFireRender extends Module {
/*    */   public static NoFireRender getInstance;
/*    */   
/*    */   public NoFireRender() {
/* 10 */     super("NoFireRender", ModuleType.Globals);
/* 11 */     getInstance = this;
/* 12 */     setHide(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\globals\NoFireRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */