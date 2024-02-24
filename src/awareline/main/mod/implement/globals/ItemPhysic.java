/*    */ package awareline.main.mod.implement.globals;
/*    */ 
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ 
/*    */ public class ItemPhysic extends Module {
/*    */   public static ItemPhysic getInstance;
/*    */   
/*    */   public ItemPhysic() {
/* 10 */     super("ItemPhysic", ModuleType.Globals);
/* 11 */     getInstance = this;
/* 12 */     setHide(true);
/* 13 */     setEnabledByConvention(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\globals\ItemPhysic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */