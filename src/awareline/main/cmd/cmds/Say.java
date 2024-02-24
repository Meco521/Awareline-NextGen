/*    */ package awareline.main.cmd.cmds;
/*    */ import awareline.main.Client;
/*    */ import awareline.main.cmd.Command;
/*    */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C01PacketChatMessage;
/*    */ 
/*    */ @StringEncryption("maximum")
/*    */ @StringEncryptionType("fast")
/*    */ public class Say extends Command {
/*    */   public Say() {
/* 14 */     super("Say");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] args) {
/* 19 */     if (args.length > 0) {
/* 20 */       StringBuilder string = new StringBuilder();
/* 21 */       for (String s : args) {
/* 22 */         string.append(s);
/*    */       }
/* 24 */       mc.thePlayer.sendQueue.addToSendQueue((Packet)new C01PacketChatMessage(string.toString()));
/*    */     } else {
/* 26 */       ClientNotification.sendClientMessage("Say", "Correct usage: " + Client.instance.prefix + "Say <Message>", 5000L, ClientNotification.Type.WARNING);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\cmd\cmds\Say.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */