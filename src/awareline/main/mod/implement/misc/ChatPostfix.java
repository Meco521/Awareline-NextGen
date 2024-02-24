/*    */ package awareline.main.mod.implement.misc;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Mode;
/*    */ import awareline.main.mod.values.Value;
/*    */ 
/*    */ public class ChatPostfix extends Module {
/*  8 */   private final String[] modes = new String[] { "A", "B", "C", "D" };
/*  9 */   public final Mode<String> mode = new Mode("Mode", this.modes, this.modes[0]);
/*    */   public static ChatPostfix getInstance;
/*    */   
/*    */   public ChatPostfix() {
/* 13 */     super("ChatPostfix", ModuleType.Misc);
/* 14 */     addSettings(new Value[] { (Value)this.mode });
/* 15 */     getInstance = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\ChatPostfix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */