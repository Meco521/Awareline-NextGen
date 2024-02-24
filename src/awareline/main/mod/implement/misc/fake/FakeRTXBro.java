/*    */ package awareline.main.mod.implement.misc.fake;
/*    */ 
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ 
/*    */ public class FakeRTXBro extends Module {
/*    */   public static FakeRTXBro getInstance;
/*    */   
/*    */   public FakeRTXBro() {
/* 10 */     super("FakeRTX", new String[] { "fakertx" }, ModuleType.Misc);
/* 11 */     setHide(true);
/* 12 */     getInstance = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\fake\FakeRTXBro.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */