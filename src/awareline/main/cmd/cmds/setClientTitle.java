/*    */ package awareline.main.cmd.cmds;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.cmd.Command;
/*    */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ import org.lwjgl.opengl.Display;
/*    */ 
/*    */ @StringEncryption("maximum")
/*    */ @StringEncryptionType("fast")
/*    */ public class setClientTitle extends Command {
/*    */   public setClientTitle() {
/* 14 */     super("setclienttitle", new String[] { "title" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] args) {
/* 19 */     if (args.length >= 1) {
/* 20 */       if (args.length == 1 && args[0].equalsIgnoreCase("clear")) {
/* 21 */         Display.setTitle("Minecraft 1.8.9");
/*    */         return;
/*    */       } 
/* 24 */       StringBuilder string = new StringBuilder();
/* 25 */       for (int i = 0; i < args.length; i++) {
/* 26 */         string.append(args[i]);
/* 27 */         if (i == args.length - 1)
/* 28 */           break;  string.append(" ");
/*    */       } 
/* 30 */       Display.setTitle(string.toString());
/* 31 */       ClientNotification.sendClientMessage("Title", "Set successfully!", 5000L, ClientNotification.Type.WARNING);
/*    */     } else {
/* 33 */       ClientNotification.sendClientMessage("Title", "Correct usage: " + Client.instance.prefix + "title <set/clear> <Title>", 5000L, ClientNotification.Type.WARNING);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\cmd\cmds\setClientTitle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */