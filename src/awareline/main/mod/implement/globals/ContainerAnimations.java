/*    */ package awareline.main.mod.implement.globals;
/*    */ 
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ 
/*    */ public class ContainerAnimations extends Module {
/*    */   public static ContainerAnimations getInstance;
/*    */   
/*    */   public ContainerAnimations() {
/* 10 */     super("ContainerAnimations", ModuleType.Globals);
/* 11 */     setHide(false);
/* 12 */     setEnabledByConvention(false);
/* 13 */     getInstance = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\globals\ContainerAnimations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */