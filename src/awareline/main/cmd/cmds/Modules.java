/*    */ package awareline.main.cmd.cmds;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.cmd.Command;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.utility.chat.Helper;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ 
/*    */ @StringEncryption("maximum")
/*    */ @StringEncryptionType("fast")
/*    */ public class Modules extends Command {
/*    */   public Modules() {
/* 15 */     super("ShowModules", new String[] { "mods", "cheats" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] args) {
/* 20 */     if (args.length == 0) {
/* 21 */       StringBuilder list = new StringBuilder(Client.instance.getModuleManager().getModules().size() + " Modules - ");
/* 22 */       for (Module cheat : Client.instance.getModuleManager().getModules()) {
/* 23 */         list.append(cheat.isEnabled() ? EnumChatFormatting.GREEN : EnumChatFormatting.RED).append(cheat.getName()).append(", ");
/*    */       }
/* 25 */       Helper.sendMessage("> " + list.substring(0, list.toString().length() - 2));
/*    */     } else {
/* 27 */       Helper.sendMessage("§bCorrect usage: " + Client.instance.prefix + "modules");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\cmd\cmds\Modules.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */