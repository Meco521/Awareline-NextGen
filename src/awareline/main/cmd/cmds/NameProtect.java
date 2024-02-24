/*    */ package awareline.main.cmd.cmds;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.cmd.Command;
/*    */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ @StringEncryption("maximum")
/*    */ @StringEncryptionType("fast")
/*    */ public class NameProtect
/*    */   extends Command {
/* 14 */   private static final Pattern COMPILE = Pattern.compile(" nmsl");
/*    */   
/*    */   public NameProtect() {
/* 17 */     super("NameProtect");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] args) {
/* 22 */     StringBuilder string = new StringBuilder();
/* 23 */     if (args.length >= 1) {
/* 24 */       for (String str : args) {
/* 25 */         string.append(str).append(" ");
/*    */       }
/* 27 */       string.append("nmsl");
/* 28 */       awareline.main.mod.implement.visual.NameProtect.getInstance.name = COMPILE.split(string.toString())[0];
/* 29 */       ClientNotification.sendClientMessage("NameProtect", "Set successfully!", 5000L, ClientNotification.Type.WARNING);
/*    */     } else {
/* 31 */       ClientNotification.sendClientMessage("NameProtect", "Correct usage: " + Client.instance.prefix + "nameprotect <Message>", 5000L, ClientNotification.Type.WARNING);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\cmd\cmds\NameProtect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */