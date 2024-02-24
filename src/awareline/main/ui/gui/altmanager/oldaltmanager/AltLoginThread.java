/*    */ package awareline.main.ui.gui.altmanager.oldaltmanager;
/*    */ 
/*    */ import awareline.main.mod.manager.FileManager;
/*    */ import com.allatori.annotations.ControlFlowObfuscation;
/*    */ import com.allatori.annotations.ExtensiveFlowObfuscation;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ import com.mojang.authlib.Agent;
/*    */ import com.mojang.authlib.exceptions.AuthenticationException;
/*    */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*    */ import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
/*    */ import java.net.Proxy;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.Session;
/*    */ 
/*    */ @ControlFlowObfuscation("enable")
/*    */ @ExtensiveFlowObfuscation("maximum")
/*    */ @StringEncryption("enable")
/*    */ @StringEncryptionType("fast")
/*    */ public class AltLoginThread
/*    */   extends Thread
/*    */ {
/* 23 */   private final Minecraft mc = Minecraft.getMinecraft(); private final String password;
/*    */   public String getStatus() {
/* 25 */     return this.status;
/*    */   }
/*    */   private String status; private final String username;
/*    */   
/*    */   public AltLoginThread(String username, String password) {
/* 30 */     super("Alt Login Thread");
/* 31 */     this.username = username;
/* 32 */     this.password = password;
/* 33 */     this.status = "§eWaiting...";
/*    */   }
/*    */   
/*    */   private static Session createSession(String username, String password) {
/* 37 */     YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
/* 38 */     YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
/* 39 */     auth.setUsername(username);
/* 40 */     auth.setPassword(password);
/*    */     try {
/* 42 */       auth.logIn();
/* 43 */       return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
/* 44 */     } catch (AuthenticationException authenticationException) {
/* 45 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 51 */     if (this.password.isEmpty()) {
/* 52 */       this.mc.session = new Session(this.username, "", "", "mojang");
/*    */       
/* 54 */       this.status = "§aUser is (" + this.username + ") now!";
/*    */       return;
/*    */     } 
/* 57 */     this.status = "§eTry Logging...";
/* 58 */     Session auth = createSession(this.username, this.password);
/* 59 */     if (auth == null) {
/* 60 */       this.status = "§cLogin Error!";
/*    */     } else {
/* 62 */       AltManager.setLastAlt(new Alt(this.username, this.password));
/* 63 */       FileManager.saveLastAlt();
/*    */       
/* 65 */       this.status = "§aUser is (" + auth.getUsername() + ") now!";
/* 66 */       this.mc.session = auth;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setStatus(String status) {
/* 71 */     this.status = status;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\altmanager\oldaltmanager\AltLoginThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */