/*    */ package awareline.main.cmd.cmds;
/*    */ 
/*    */ import awareline.main.cmd.Command;
/*    */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ @StringEncryption("maximum")
/*    */ @StringEncryptionType("fast")
/*    */ public class WorldClean extends Command {
/*    */   public WorldClean() {
/* 13 */     super("worldclean", new String[] { "clean", "cleans", "clear" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] args) {
/* 18 */     mc.renderGlobal.loadRenderers();
/* 19 */     Config.updateFramebufferSize();
/* 20 */     ClientNotification.sendClientMessage("WorldClean", "Clean world litter memory.", 5000L, ClientNotification.Type.WARNING);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\cmd\cmds\WorldClean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */