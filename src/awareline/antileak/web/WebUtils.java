/*     */ package awareline.antileak.web;
/*     */ 
/*     */ import awareline.main.ui.font.fontmanager.utils.StringUtils;
/*     */ import com.allatori.annotations.ControlFlowObfuscation;
/*     */ import com.allatori.annotations.ExtensiveFlowObfuscation;
/*     */ import com.allatori.annotations.StringEncryption;
/*     */ import com.allatori.annotations.StringEncryptionType;
/*     */ import io.netty.util.internal.ThreadLocalRandom;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import javax.swing.JOptionPane;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ @ControlFlowObfuscation("enable")
/*     */ @ExtensiveFlowObfuscation("maximum")
/*     */ @StringEncryption("enable")
/*     */ @StringEncryptionType("fast")
/*     */ public class WebUtils {
/*     */   public boolean junkCode;
/*     */   public boolean junkCode1;
/*     */   public boolean junkCode2;
/*     */   
/*     */   public String junkCode() {
/*  27 */     return "232{2=" + this.junkCode + ", 411=" + this.junkCode1 + ", 523=" + this.junkCode2 + ", 5=" + this.junkCode3 + ", 0=" + this.junkCode4 + ", 9=" + this.junkCode5 + ", 8=" + this.junkCode6 + ", 3=" + this.junkCode7 + ", 7=" + this.junkCode8 + ", 5=" + this.junkCode9 + ", 4=" + this.junkCode10 + ", 22=" + this.junkCode11 + '=';
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean junkCode3;
/*     */ 
/*     */   
/*     */   public boolean junkCode4;
/*     */ 
/*     */   
/*     */   public boolean junkCode5;
/*     */ 
/*     */   
/*     */   public boolean junkCode6;
/*     */ 
/*     */   
/*     */   public boolean junkCode7;
/*     */ 
/*     */   
/*     */   public boolean junkCode8;
/*     */ 
/*     */   
/*     */   public boolean junkCode9;
/*     */   
/*     */   public boolean junkCode10;
/*     */   
/*     */   public boolean junkCode11;
/*     */ 
/*     */   
/*     */   public static String get(String url) {
/*  58 */     if (url.isEmpty()) {
/*  59 */       Minecraft.getMinecraft().shutdown();
/*  60 */       return StringUtils.randomStringHeavy() + StringUtils.randomStringDefault(10);
/*     */     } 
/*     */     try {
/*  63 */       if (ThreadLocalRandom.current().nextBoolean()) {
/*  64 */         return HttpUtil.performGetRequest(new URL(url));
/*     */       }
/*  66 */       return get2(url);
/*     */     }
/*  68 */     catch (Throwable e) {
/*  69 */       Minecraft.getMinecraft().shutdown();
/*  70 */       e.printStackTrace();
/*     */ 
/*     */       
/*  73 */       return url;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String get2(String url) {
/*  78 */     if (url.isEmpty()) {
/*  79 */       JOptionPane.showInputDialog(null, "Get Error", "Can't get null");
/*  80 */       Minecraft.getMinecraft().shutdown();
/*  81 */       return StringUtils.randomStringHeavy() + StringUtils.randomStringDefault(33);
/*     */     } 
/*  83 */     Validate.notNull(url);
/*  84 */     StringBuilder result = new StringBuilder();
/*  85 */     BufferedReader in = null;
/*     */     
/*     */     try {
/*     */       try {
/*  89 */         URL realUrl = new URL(url);
/*  90 */         URLConnection connection = realUrl.openConnection();
/*  91 */         connection.setDoOutput(true);
/*  92 */         connection.setReadTimeout(99781);
/*  93 */         connection.setRequestProperty("accept", "*/*");
/*  94 */         connection.setRequestProperty("connection", "Keep-Alive");
/*  95 */         connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1) ZiMinClient;Chrome 69");
/*  96 */         connection.connect();
/*  97 */         connection.getHeaderFields();
/*  98 */         in = new BufferedReader(new InputStreamReader(connection.getInputStream())); String line;
/*  99 */         while ((line = in.readLine()) != null) {
/* 100 */           result.append(line).append("\n");
/*     */         }
/* 102 */       } catch (Exception exception) {
/*     */         try {
/* 104 */           if (in != null) {
/* 105 */             in.close();
/*     */           }
/* 107 */         } catch (Exception err) {
/* 108 */           err.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       try {
/* 113 */         if (in != null) {
/* 114 */           in.close();
/*     */         }
/* 116 */       } catch (Exception err) {
/* 117 */         err.printStackTrace();
/*     */       } 
/*     */     } 
/* 120 */     return result.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\antileak\web\WebUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */