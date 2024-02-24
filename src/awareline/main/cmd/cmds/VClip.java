/*    */ package awareline.main.cmd.cmds;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.cmd.Command;
/*    */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*    */ import awareline.main.utility.math.MathUtil;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ 
/*    */ @StringEncryption("maximum")
/*    */ @StringEncryptionType("fast")
/*    */ public class VClip
/*    */   extends Command
/*    */ {
/*    */   public VClip() {
/* 16 */     super("vclip", new String[] { "vc" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] args) {
/* 21 */     if (args.length > 0) {
/* 22 */       if (MathUtil.parsable(args[0], (byte)4)) {
/* 23 */         float distance = Float.parseFloat(args[0]);
/* 24 */         mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + distance, mc.thePlayer.posZ);
/* 25 */         ClientNotification.sendClientMessage("VClip", "VClipped " + distance + " blocks.", 4000L, ClientNotification.Type.INFO);
/*    */       } else {
/* 27 */         ClientNotification.sendClientMessage("VClip", args[0] + " is not a valid number", 4000L, ClientNotification.Type.WARNING);
/*    */       } 
/*    */     } else {
/* 30 */       ClientNotification.sendClientMessage("VClip", "Valid " + Client.instance.prefix + "vclip <number>", 4000L, ClientNotification.Type.WARNING);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\cmd\cmds\VClip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */