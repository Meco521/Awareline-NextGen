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
/*    */ public class Help extends Command {
/*    */   public Help() {
/* 13 */     super("Help");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(String[] args) {
/* 19 */     sendMessage("§b§lCommand Helper");
/* 20 */     sendMessage("§b" + Client.instance.prefix + "help >§7 Show all commands");
/* 21 */     sendMessage("§b" + Client.instance.prefix + "bind >§7 Bind a module to a key");
/* 22 */     sendMessage("§b" + Client.instance.prefix + "binds >§7 Show bind key list");
/* 23 */     sendMessage("§b" + Client.instance.prefix + "config >§7 Show all configs");
/* 24 */     sendMessage("§b" + Client.instance.prefix + "reloadconfig >§7 Reload all configs and settings");
/* 25 */     sendMessage("§b" + Client.instance.prefix + "friend >§7 Add Friend a player");
/* 26 */     sendMessage("§b" + Client.instance.prefix + "showmodules >§7 Show all cheat modules");
/* 27 */     sendMessage("§b" + Client.instance.prefix + "nameprotect >§7 Set the name what protects your username");
/* 28 */     sendMessage("§b" + Client.instance.prefix + "worldclean >§7 Refresh Chunks");
/* 29 */     sendMessage("§b" + Client.instance.prefix + "say >§7 Say a message");
/* 30 */     sendMessage("§b" + Client.instance.prefix + "setspammer >§7 Set a spammer message");
/* 31 */     sendMessage("§b" + Client.instance.prefix + "tp >§7 Teleport to <x> <y> <z> or <playername>");
/* 32 */     sendMessage("§b" + Client.instance.prefix + "vclip >§7 Clip up/down your position");
/* 33 */     sendMessage("§b" + Client.instance.prefix + "prefix >§7 Set the command prefix");
/* 34 */     sendMessage("§b" + Client.instance.prefix + "setclientname >§7 Set the client name");
/* 35 */     sendMessage("§b" + Client.instance.prefix + "setmodulename >§7 Custom set the module name");
/* 36 */     sendMessage("§b" + Client.instance.prefix + "setclienttitle >§7 Set the client title");
/* 37 */     sendMessage("§b" + Client.instance.prefix + "t >§7 Toggle a module on/off");
/*    */   }
/*    */ 
/*    */   
/*    */   private void sendMessage(String msg) {
/* 42 */     Helper.sendMessageWithoutPrefix(msg);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\cmd\cmds\Help.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */