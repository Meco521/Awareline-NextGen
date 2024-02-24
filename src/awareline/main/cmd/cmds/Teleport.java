/*    */ package awareline.main.cmd.cmds;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.cmd.Command;
/*    */ import awareline.main.mod.implement.move.TeleportMod;
/*    */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ 
/*    */ @StringEncryption("maximum")
/*    */ @StringEncryptionType("fast")
/*    */ public class Teleport
/*    */   extends Command {
/*    */   public Teleport() {
/* 15 */     super("tp", new String[] { "tp", "teleport" });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(String[] args) {
/* 21 */     if (args.length >= 3) {
/* 22 */       TeleportMod.x = Float.parseFloat(args[0]);
/* 23 */       TeleportMod.y = Float.parseFloat(args[1]);
/* 24 */       TeleportMod.z = Float.parseFloat(args[2]);
/* 25 */       TeleportMod.getInstance.setEnabled(true);
/* 26 */     } else if (args.length >= 1) {
/* 27 */       if (mc.theWorld.getPlayerEntityByName(args[0]) != null) {
/* 28 */         mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 5.0D, mc.thePlayer.posZ);
/* 29 */         TeleportMod.isTPPlayer = true;
/* 30 */         TeleportMod.playername = args[0];
/* 31 */         TeleportMod.getInstance.setEnabled(true);
/*    */       } else {
/* 33 */         ClientNotification.sendClientMessage("Teleport", "Player do not exist", 5000L, ClientNotification.Type.INFO);
/*    */       } 
/*    */     } else {
/* 36 */       ClientNotification.sendClientMessage("Teleport", Client.instance.prefix + "tp <x> <y> <z> or .tp <playername>", 5000L, ClientNotification.Type.WARNING);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\cmd\cmds\Teleport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */