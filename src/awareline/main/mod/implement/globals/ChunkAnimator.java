/*    */ package awareline.main.mod.implement.globals;
/*    */ 
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.ui.animations.AnimationHandler;
/*    */ 
/*    */ public class ChunkAnimator extends Module {
/*  8 */   public AnimationHandler animation = new AnimationHandler();
/*    */   public static ChunkAnimator getInstance;
/*    */   
/*    */   public ChunkAnimator() {
/* 12 */     super("ChunkAnimator", ModuleType.Globals);
/* 13 */     setHide(true);
/* 14 */     getInstance = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\globals\ChunkAnimator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */