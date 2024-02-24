/*    */ package awareline.main.cmd.cmds;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.cmd.Command;
/*    */ import awareline.main.utility.chat.Helper;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ 
/*    */ @StringEncryption("maximum")
/*    */ @StringEncryptionType("fast")
/*    */ public class Config extends Command {
/*    */   public Config() {
/* 13 */     super("config", new String[] { "cfg" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] args) {
/* 18 */     if (args.length == 2 && args[0].equalsIgnoreCase("save")) {
/* 19 */       Client.instance.configManager.saveConfig(args[1]);
/*    */     }
/* 21 */     if (args.length == 2 && args[0].equalsIgnoreCase("load")) {
/* 22 */       Client.instance.configManager.LoadConfig(args[1]);
/*    */     }
/* 24 */     if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
/* 25 */       Client.instance.configManager.RemoveConfig(args[1]);
/*    */     }
/* 27 */     if (args.length != 2) {
/* 28 */       Helper.sendMessageWithoutPrefix("§7§m§l==================================");
/* 29 */       Helper.sendMessageWithoutPrefix("§b§lConfigManager");
/* 30 */       Helper.sendMessageWithoutPrefix("§b" + Client.instance.prefix + "config save <Configuration name> :§7 Save Config");
/* 31 */       Helper.sendMessageWithoutPrefix("§b" + Client.instance.prefix + "config load <Configuration name> :§7 Load Config");
/* 32 */       Helper.sendMessageWithoutPrefix("§b" + Client.instance.prefix + "config remove <Configuration name> :§7 Remove Config");
/* 33 */       Helper.sendMessageWithoutPrefix("§7§m§l==================================");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\cmd\cmds\Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */