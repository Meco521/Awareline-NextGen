/*    */ package awareline.main.cmd.cmds;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.cmd.Command;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.utility.chat.Helper;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ @StringEncryption("maximum")
/*    */ @StringEncryptionType("fast")
/*    */ public class Binds extends Command {
/*    */   public Binds() {
/* 16 */     super("binds");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] args) {
/* 21 */     if (args.length == 0) {
/* 22 */       StringBuilder list2 = new StringBuilder();
/* 23 */       for (Module cheat : Client.instance.getModuleManager().getModules()) {
/* 24 */         if (!Keyboard.getKeyName(cheat.getKey()).equals("NONE")) {
/* 25 */           list2.append(cheat.getName()).append(" ").append(Keyboard.getKeyName(cheat.getKey())).append(", ");
/*    */         }
/*    */       } 
/* 28 */       Helper.sendMessage(EnumChatFormatting.WHITE + "Binds:");
/* 29 */       Helper.sendMessage(EnumChatFormatting.WHITE + list2.substring(0, list2.toString().length() - 2));
/*    */     } else {
/* 31 */       Helper.sendMessage("invalid syntax Valid -binds");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\cmd\cmds\Binds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */