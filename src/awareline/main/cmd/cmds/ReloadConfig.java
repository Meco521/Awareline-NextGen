/*    */ package awareline.main.cmd.cmds;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.cmd.Command;
/*    */ import awareline.main.mod.manager.FileManager;
/*    */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ 
/*    */ @StringEncryption("maximum")
/*    */ @StringEncryptionType("fast")
/*    */ public class ReloadConfig extends Command {
/*    */   public ReloadConfig() {
/* 14 */     super("ReloadConfig");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] args) {
/* 19 */     if (args.length == 0) {
/* 20 */       FileManager.init();
/* 21 */       ClientNotification.sendClientMessage("ReloadConfig", "Client config  was reloaded!", 5000L, ClientNotification.Type.SUCCESS);
/*    */     } else {
/*    */       
/* 24 */       ClientNotification.sendClientMessage("ReloadConfig", "invalid syntax Valid " + Client.instance.prefix + "reload", 5000L, ClientNotification.Type.WARNING);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\cmd\cmds\ReloadConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */