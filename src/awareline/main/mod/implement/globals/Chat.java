/*    */ package awareline.main.mod.implement.globals;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Mode;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ 
/*    */ public class Chat extends Module {
/*  9 */   public final Option<Boolean> enableFont = new Option("HDFont", Boolean.valueOf(true));
/* 10 */   public final Mode<String> animations = new Mode("Animations", new String[] { "Off", "Height" }, "Height");
/*    */   
/* 12 */   public final Mode<String> backgroundShadow = new Mode("Shadow", new String[] { "Off", "Normal", "Visuals" }, "Off");
/*    */   
/*    */   public static Chat getInstance;
/*    */   
/*    */   public Chat() {
/* 17 */     super("Chat", ModuleType.Globals);
/* 18 */     addSettings(new Value[] { (Value)this.enableFont, (Value)this.backgroundShadow, (Value)this.animations });
/* 19 */     getInstance = this;
/* 20 */     setHide(false);
/* 21 */     setEnabledByConvention(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\globals\Chat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */