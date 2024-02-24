/*    */ package awareline.main.cmd.cmds;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.cmd.Command;
/*    */ import awareline.main.mod.implement.misc.Spammer;
/*    */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ 
/*    */ @StringEncryption("maximum")
/*    */ @StringEncryptionType("fast")
/*    */ public class setSpammer extends Command {
/*    */   public setSpammer() {
/* 14 */     super("SetSpammer", new String[] { "s" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] args) {
/* 19 */     if (args.length >= 1) {
/* 20 */       StringBuilder string = new StringBuilder();
/* 21 */       for (String str : args) {
/* 22 */         string.append(str).append(" ");
/*    */       }
/* 24 */       Spammer.bindmessage = string.toString();
/* 25 */       ClientNotification.sendClientMessage("SetSpammer", "Set successfully!", 5000L, ClientNotification.Type.WARNING);
/*    */     } else {
/* 27 */       ClientNotification.sendClientMessage("SetSpammer", "Correct usage: " + Client.instance.prefix + "setspammer <Message>", 5000L, ClientNotification.Type.WARNING);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\cmd\cmds\setSpammer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */