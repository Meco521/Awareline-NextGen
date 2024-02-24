/*    */ package awareline.main.ui.font.fontmanager.utils;
/*    */ 
/*    */ import awareline.main.utility.math.MathUtil;
/*    */ import com.allatori.annotations.ControlFlowObfuscation;
/*    */ import com.allatori.annotations.ExtensiveFlowObfuscation;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ import java.util.concurrent.ThreadLocalRandom;
/*    */ 
/*    */ @ControlFlowObfuscation("enable")
/*    */ @ExtensiveFlowObfuscation("maximum")
/*    */ @StringEncryption("maximum")
/*    */ @StringEncryptionType("fast")
/*    */ public final class StringUtils
/*    */ {
/*    */   public static String randomStringDefault(int length) {
/* 17 */     return randomString("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_", length);
/*    */   }
/*    */   
/*    */   public static String randomStringHeavy() {
/* 21 */     return randomString("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_", 
/* 22 */         (int)MathUtil.getRandomInRange(2.0F, (30 + (int)MathUtil.randomNumber(5.0D, -5.0D))));
/*    */   }
/*    */   
/*    */   public static String randomString(String pool, int length) {
/* 26 */     StringBuilder builder = new StringBuilder();
/*    */     
/* 28 */     for (int i = 0; i < length; i++) {
/* 29 */       builder.append(pool.charAt(ThreadLocalRandom.current().nextInt(0, pool.length() - 1)));
/*    */     }
/*    */     
/* 32 */     return builder.toString();
/*    */   }
/*    */   
/*    */   public static boolean isBlank(String s) {
/* 36 */     if (s == null) return true;
/*    */     
/* 38 */     for (int i = 0; i < s.length(); i++) {
/* 39 */       if (!Character.isWhitespace(s.charAt(i))) {
/* 40 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 44 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   private static boolean isEmojiCharacter(char codePoint) {
/* 49 */     return (codePoint == '\000' || codePoint == '\t' || codePoint == '\n' || codePoint == '\r' || (codePoint >= ' ' && codePoint <= '퟿') || (codePoint >= '' && codePoint <= '�') || (codePoint >= 65536 && codePoint <= 1114111));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String filterEmoji(String source) {
/* 57 */     if (isBlank(source)) {
/* 58 */       return source;
/*    */     }
/*    */     
/* 61 */     StringBuilder buf = null;
/* 62 */     int len = source.length();
/*    */     
/* 64 */     for (int i = 0; i < len; i++) {
/* 65 */       char codePoint = source.charAt(i);
/*    */       
/* 67 */       if (isEmojiCharacter(codePoint)) {
/* 68 */         if (buf == null) {
/* 69 */           buf = new StringBuilder(source.length());
/*    */         }
/*    */         
/* 72 */         buf.append(codePoint);
/*    */       } 
/*    */     } 
/*    */     
/* 76 */     if (buf == null) {
/* 77 */       return source;
/*    */     }
/* 79 */     if (buf.length() == len) {
/* 80 */       return source;
/*    */     }
/* 82 */     return buf.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\fontmanage\\utils\StringUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */