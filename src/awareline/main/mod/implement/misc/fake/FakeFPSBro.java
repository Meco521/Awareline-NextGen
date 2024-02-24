/*    */ package awareline.main.mod.implement.misc.fake;
/*    */ 
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ 
/*    */ public class FakeFPSBro extends Module {
/*    */   public static FakeFPSBro getInstance;
/*    */   
/*    */   public FakeFPSBro() {
/* 10 */     super("FakeFPS", new String[] { "fakefps" }, ModuleType.Misc);
/* 11 */     setHide(true);
/* 12 */     getInstance = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\fake\FakeFPSBro.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */