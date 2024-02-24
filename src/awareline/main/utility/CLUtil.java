/*    */ package awareline.main.utility;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import com.allatori.annotations.ControlFlowObfuscation;
/*    */ import com.allatori.annotations.ExtensiveFlowObfuscation;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.net.Socket;
/*    */ 
/*    */ 
/*    */ 
/*    */ @ControlFlowObfuscation("enable")
/*    */ @ExtensiveFlowObfuscation("maximum")
/*    */ @StringEncryption("enable")
/*    */ @StringEncryptionType("fast")
/*    */ public class CLUtil
/*    */ {
/*    */   public static void joinServer(GameProfile profile, String authenticationToken, String serverId) {
/*    */     try {
/* 21 */       Socket sock = new Socket("localhost", 55996);
/* 22 */       sock.getOutputStream().write((serverId + "\000").getBytes());
/* 23 */       sock.getOutputStream().flush();
/* 24 */       String res = String.valueOf(sock.getInputStream().read());
/* 25 */       Client.instance.getClass(); System.out.println("[" + "Awareline" + " AutoCL] CL -> " + res);
/* 26 */       sock.close();
/* 27 */     } catch (Exception e) {
/* 28 */       e.printStackTrace();
/* 29 */       System.out.println("AutoCl:杩炴帴澶辫触");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\CLUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */