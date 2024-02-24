/*    */ package awareline.main.cmd.cmds;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.cmd.Command;
/*    */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*    */ import awareline.main.utility.chat.Helper;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ 
/*    */ @StringEncryption("maximum")
/*    */ @StringEncryptionType("fast")
/*    */ public class setClientName
/*    */   extends Command {
/*    */   public setClientName() {
/* 15 */     super("setclientname");
/*    */   }
/*    */   
/*    */   public void execute(String[] args) {
/* 19 */     if (args.length == 0) {
/* 20 */       Client.instance.hudName = Client.instance.lastName;
/*    */     } else {
/* 22 */       StringBuilder Ganga = null;
/* 23 */       boolean a = false;
/* 24 */       for (String s : args) {
/* 25 */         if (!a) {
/* 26 */           Ganga = new StringBuilder(s);
/* 27 */           a = true;
/*    */         } else {
/*    */           
/* 30 */           Ganga.append(" ").append(s);
/*    */         } 
/* 32 */       }  Client.instance.hudName = Ganga.toString();
/* 33 */       ClientNotification.sendClientMessage(getName(), "Set client name is " + Ganga + " now!", 4000L, ClientNotification.Type.SUCCESS);
/*    */       
/* 35 */       Helper.sendMessage("Set client name is " + Ganga + " now!");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\cmd\cmds\setClientName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */