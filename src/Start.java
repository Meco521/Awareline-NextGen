/*    */ import com.allatori.annotations.ControlFlowObfuscation;
/*    */ import com.allatori.annotations.ExtensiveFlowObfuscation;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ import java.util.Arrays;
/*    */ import net.minecraft.client.main.Main;
/*    */ 
/*    */ 
/*    */ @ControlFlowObfuscation("enable")
/*    */ @ExtensiveFlowObfuscation("maximum")
/*    */ @StringEncryption("maximum")
/*    */ @StringEncryptionType("fast")
/*    */ public class Start
/*    */ {
/*    */   public static void main(String[] args) {
/* 16 */     if (args != null) {
/* 17 */       Main.main(concat(new String[] { "--version", "AwarelineY", "--accessToken", "0", "--assetsDir", "assets", "--assetIndex", "1.8", "--userProperties", "{}", "--width", "1280", "--height", "720" }, args));
/*    */     } else {
/*    */       
/* 20 */       System.err.println("Start args null");
/* 21 */       Runtime.getRuntime().gc();
/* 22 */       Runtime.getRuntime().exit(-1);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T> T[] concat(T[] first, T[] second) {
/* 28 */     T[] result = Arrays.copyOf(first, first.length + second.length);
/* 29 */     System.arraycopy(second, 0, result, first.length, second.length);
/* 30 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\Start.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */