/*    */ package awareline.main.cmd.cmds;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.cmd.Command;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.ui.gui.clickgui.mode.old.OldClickGui;
/*    */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ @StringEncryption("maximum")
/*    */ @StringEncryptionType("fast")
/*    */ public class Toggle extends Command {
/*    */   public Toggle() {
/* 16 */     super("toggle", new String[] { "t", "tt", "disable", "enable" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] args) {
/* 21 */     if (args.length < 1) {
/* 22 */       ClientNotification.sendClientMessage("Command", "Correct usage: " + Client.instance.prefix + "t <Module>", 5000L, ClientNotification.Type.WARNING);
/*    */       return;
/*    */     } 
/* 25 */     boolean found = false;
/* 26 */     Module m = Client.instance.getModuleManager().getAlias(args[0]);
/* 27 */     if (m != null) {
/* 28 */       if (m instanceof awareline.main.mod.implement.visual.ctype.ClickGui) {
/* 29 */         mc.displayGuiScreen((GuiScreen)new OldClickGui());
/*    */       } else {
/* 31 */         m.setEnabledWithoutNotification(!m.isEnabled());
/*    */       } 
/* 33 */       found = true;
/* 34 */       if (m.isEnabled()) {
/* 35 */         ClientNotification.sendClientMessage("Toggle", m.getName() + " was enabled", 5000L, ClientNotification.Type.WARNING);
/*    */       } else {
/* 37 */         ClientNotification.sendClientMessage("Toggle", m.getName() + " was disabled", 5000L, ClientNotification.Type.WARNING);
/*    */       } 
/*    */     } 
/* 40 */     if (!found)
/* 41 */       ClientNotification.sendClientMessage("Toggle", "Module name \"" + args[0] + "\" is invalid", 5000L, ClientNotification.Type.WARNING); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\cmd\cmds\Toggle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */