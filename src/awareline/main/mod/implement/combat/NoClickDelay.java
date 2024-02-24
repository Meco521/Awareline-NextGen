/*    */ package awareline.main.mod.implement.combat;
/*    */ 
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ 
/*    */ public class NoClickDelay extends Module {
/*    */   public static NoClickDelay getInstance;
/*    */   
/*    */   public NoClickDelay() {
/* 10 */     super("NoClickDelay", ModuleType.Combat);
/* 11 */     getInstance = this;
/* 12 */     setEnabled(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\NoClickDelay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */