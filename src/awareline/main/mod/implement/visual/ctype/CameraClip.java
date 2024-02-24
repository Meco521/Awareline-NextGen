/*    */ package awareline.main.mod.implement.visual.ctype;
/*    */ 
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ 
/*    */ public class CameraClip extends Module {
/*    */   public static CameraClip getInstance;
/*    */   
/*    */   public CameraClip() {
/* 10 */     super("CameraClip", ModuleType.Render);
/* 11 */     getInstance = this;
/* 12 */     setEnabledByConvention(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\ctype\CameraClip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */