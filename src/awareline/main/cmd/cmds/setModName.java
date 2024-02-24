/*    */ package awareline.main.cmd.cmds;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.cmd.Command;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.utility.chat.Helper;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ 
/*    */ @StringEncryption("maximum")
/*    */ @StringEncryptionType("fast")
/*    */ public class setModName
/*    */   extends Command {
/* 17 */   public static List<String> list = new ArrayList<>();
/*    */   
/*    */   public setModName() {
/* 20 */     super("setmoduleName");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] args) {
/* 25 */     if (args.length == 0) {
/* 26 */       Helper.sendMessage("Correct usage .setClientName <module> [customname]");
/*    */     }
/* 28 */     boolean found = false;
/* 29 */     Module m = Client.instance.getModuleManager().getAlias(args[0]);
/* 30 */     if (m != null) {
/* 31 */       found = true;
/* 32 */       if (args.length >= 2) {
/* 33 */         m.setCustomName(args[1]);
/* 34 */         Helper.sendMessage(EnumChatFormatting.BLUE + m.getName() + EnumChatFormatting.GRAY + " was" + EnumChatFormatting.GREEN + " set" + EnumChatFormatting.GRAY + " to " + EnumChatFormatting.YELLOW + args[1]);
/*    */       } else {
/*    */         
/* 37 */         m.setCustomName(null);
/* 38 */         Helper.sendMessage(EnumChatFormatting.BLUE + m.getName() + EnumChatFormatting.GRAY + " was" + EnumChatFormatting.RED + " reset");
/*    */       } 
/*    */     } 
/* 41 */     if (!found)
/* 42 */       Helper.sendMessage("Module name " + EnumChatFormatting.RED + args[0] + EnumChatFormatting.GRAY + " is invalid"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\cmd\cmds\setModName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */