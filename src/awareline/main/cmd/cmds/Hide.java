/*    */ package awareline.main.cmd.cmds;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.cmd.Command;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ 
/*    */ @StringEncryption("maximum")
/*    */ @StringEncryptionType("fast")
/*    */ public class Hide extends Command {
/*    */   public Hide() {
/* 14 */     super("Hide", new String[] { "hide", "hidden" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] args) {
/* 19 */     if (args.length == 1) {
/* 20 */       Module m = Client.instance.getModuleManager().getAlias(args[0]);
/* 21 */       if (m != null) {
/* 22 */         m.setHide(!m.wasHide());
/* 23 */         ClientNotification.sendClientMessage("Hide", (m.wasHide() ? "Hide Module " : " Shown Module ") + m.getName(), 5000L, ClientNotification.Type.WARNING);
/*    */       } else {
/* 25 */         ClientNotification.sendClientMessage("Hide", " Invalid module name, double check spelling.", 5000L, ClientNotification.Type.WARNING);
/*    */       } 
/*    */     } else {
/* 28 */       ClientNotification.sendClientMessage("Hide", "Correct usage: " + Client.instance.prefix + "hide <module>", 5000L, ClientNotification.Type.WARNING);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\cmd\cmds\Hide.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */