/*    */ package awareline.main.utility.translate;
/*    */ 
/*    */ import awareline.main.utility.HttpUtils;
/*    */ import com.allatori.annotations.ControlFlowObfuscation;
/*    */ import com.allatori.annotations.ExtensiveFlowObfuscation;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.net.URLEncoder;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ @ControlFlowObfuscation("enable")
/*    */ @ExtensiveFlowObfuscation("maximum")
/*    */ @StringEncryption("maximum")
/*    */ @StringEncryptionType("fast")
/*    */ public class StringUtils
/*    */ {
/*    */   public static String translate(String string) {
/* 21 */     String text = getURLEncoderString(string);
/* 22 */     Map<String, String> header = new HashMap<>();
/* 23 */     header.put("Connection", " keep-alive");
/* 24 */     header.put("Referer", "http://fanyi.youdao.com/translate");
/* 25 */     header.put("Accept-Language", " zh-CN,zh;q=0.8");
/* 26 */     return org.apache.commons.lang3.StringUtils.substringAfterLast(HttpUtils.get("http://fanyi.youdao.com//translate?i=" + text + "&type=AUTO&doctype=text&xmlVersion=1.1&keyfrom=360se", null, header), "result=");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getURLEncoderString(String str) {
/* 32 */     String result = "";
/*    */     try {
/* 34 */       result = URLEncoder.encode(str, "UTF-8");
/* 35 */     } catch (UnsupportedEncodingException e) {
/* 36 */       e.printStackTrace();
/*    */     } 
/* 38 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\translate\StringUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */