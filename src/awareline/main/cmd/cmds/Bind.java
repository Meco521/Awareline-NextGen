/*    */ package awareline.main.cmd.cmds;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.cmd.Command;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*    */ import awareline.main.utility.chat.Helper;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ @StringEncryption("maximum")
/*    */ @StringEncryptionType("fast")
/*    */ public class Bind extends Command {
/*    */   public Bind() {
/* 16 */     super("Bind");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] args) {
/* 21 */     if (args.length == 2) {
/* 22 */       int key = Keyboard.getKeyIndex(args[1].toUpperCase());
/* 23 */       Object keyName = (key == 0) ? "none" : args[1].toUpperCase();
/* 24 */       if (args[0].equalsIgnoreCase("all")) {
/* 25 */         for (Module module : Client.instance.getModuleManager().getModules()) {
/* 26 */           module.setKey(key);
/*    */         }
/* 28 */         ClientNotification.sendClientMessage("Binds", "Bind all modules to " + keyName + " .", 5000L, ClientNotification.Type.SUCCESS);
/*    */       } else {
/* 30 */         Module m = Client.instance.getModuleManager().getAlias(args[0]);
/* 31 */         if (m != null) {
/* 32 */           m.setKey(key);
/* 33 */           ClientNotification.sendClientMessage("Binds", "bind " + m.getName() + " to " + keyName + " .", 5000L, ClientNotification.Type.SUCCESS);
/*    */         } else {
/* 35 */           Helper.sendMessage("§bInvalid module name, double check spelling.");
/*    */         } 
/*    */       } 
/*    */     } else {
/* 39 */       ClientNotification.sendClientMessage("Binds", "Correct usage: " + Client.instance.prefix + "bind <Module> <Key>", 5000L, ClientNotification.Type.WARNING);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\cmd\cmds\Bind.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */