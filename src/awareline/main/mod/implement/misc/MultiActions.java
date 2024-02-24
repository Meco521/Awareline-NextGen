/*    */ package awareline.main.mod.implement.misc;
/*    */ 
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ 
/*    */ public class MultiActions extends Module {
/*    */   public static MultiActions getInstance;
/*    */   
/*    */   public MultiActions() {
/* 10 */     super("MultiActions", ModuleType.Misc);
/* 11 */     setHide(true);
/* 12 */     setEnabledByConvention(true);
/* 13 */     getInstance = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\MultiActions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */