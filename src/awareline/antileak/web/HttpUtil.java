/*    */ package awareline.antileak.web;
/*    */ 
/*    */ import awareline.main.ui.font.fontmanager.utils.StringUtils;
/*    */ import com.allatori.annotations.ControlFlowObfuscation;
/*    */ import com.allatori.annotations.ExtensiveFlowObfuscation;
/*    */ import com.allatori.annotations.StringEncryption;
/*    */ import com.allatori.annotations.StringEncryptionType;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.URL;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import org.apache.commons.io.Charsets;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ 
/*    */ 
/*    */ @ControlFlowObfuscation("enable")
/*    */ @ExtensiveFlowObfuscation("maximum")
/*    */ @StringEncryption("enable")
/*    */ @StringEncryptionType("fast")
/*    */ public class HttpUtil
/*    */ {
/*    */   public static HttpURLConnection createUrlConnection(URL url) throws IOException {
/* 26 */     Validate.notNull(url);
/* 27 */     HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/* 28 */     connection.setConnectTimeout(15000);
/* 29 */     connection.setReadTimeout(15000);
/* 30 */     connection.setUseCaches(false);
/* 31 */     return connection;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String performGetRequest(URL url) throws IOException {
/* 36 */     return (new HttpUtil()).performGetRequestWithoutStatic(url, false);
/*    */   }
/*    */   public String performGetRequestWithoutStatic(URL url, boolean withKey) throws IOException {
/*    */     String var6;
/* 40 */     if (url == null) {
/* 41 */       Minecraft.getMinecraft().shutdown();
/* 42 */       return StringUtils.randomStringHeavy();
/*    */     } 
/* 44 */     Validate.notNull(url);
/*    */     
/* 46 */     HttpURLConnection connection = createUrlConnection(url);
/* 47 */     InputStream inputStream = null;
/* 48 */     connection.setRequestProperty("user-agent", "Mozilla/5.0 AppIeWebKit");
/*    */     
/* 50 */     if (withKey) {
/* 51 */       connection.setRequestProperty("xf-api-key", "LnM-qSeQqtJlJmJnVt76GhU-SoiolWs9");
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 58 */       inputStream = connection.getInputStream();
/* 59 */       return IOUtils.toString(inputStream, Charsets.UTF_8);
/* 60 */     } catch (IOException var10) {
/* 61 */       IOUtils.closeQuietly(inputStream);
/* 62 */       inputStream = connection.getErrorStream();
/* 63 */       if (inputStream == null) {
/* 64 */         throw var10;
/*    */       }
/*    */ 
/*    */       
/* 68 */       String result = IOUtils.toString(inputStream, Charsets.UTF_8);
/* 69 */       var6 = result;
/*    */     } finally {
/* 71 */       IOUtils.closeQuietly(inputStream);
/*    */     } 
/*    */     
/* 74 */     return var6;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\antileak\web\HttpUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */