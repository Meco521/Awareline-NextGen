/*    */ package awareline.antileak.encode;
/*    */ 
/*    */ import awareline.main.ui.font.fontmanager.utils.StringUtils;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ 
/*    */ @StringEncryption("enable")
/*    */ @StringEncryptionType("fast")
/*    */ public class Base64
/*    */ {
/* 14 */   private static final java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
/*    */   
/*    */   public static String encode(String text) {
/* 17 */     if (text.isEmpty()) {
/* 18 */       Minecraft.getMinecraft().shutdown();
/* 19 */       return StringUtils.randomStringHeavy();
/*    */     } 
/*    */     
/* 22 */     byte[] textByte = text.getBytes(StandardCharsets.UTF_8);
/* 23 */     return encoder.encodeToString(textByte);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 28 */   private static final java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
/*    */   
/*    */   public static String decode(String encodedText) {
/* 31 */     if (encodedText.isEmpty()) {
/* 32 */       Minecraft.getMinecraft().shutdown();
/* 33 */       return StringUtils.randomStringHeavy();
/*    */     } 
/*    */     
/* 36 */     String text = new String(decoder.decode(encodedText), StandardCharsets.UTF_8);
/* 37 */     return text;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\antileak\encode\Base64.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */