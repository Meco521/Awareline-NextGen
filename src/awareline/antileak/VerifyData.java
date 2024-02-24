/*     */ package awareline.antileak;
/*     */ 
/*     */ import awareline.antileak.encode.Base64;
/*     */ import awareline.antileak.hwid.HWIDUtils;
/*     */ import awareline.antileak.hwid.HWIDUtilsMCStart;
/*     */ import awareline.antileak.web.HttpUtil;
/*     */ import awareline.antileak.web.WebUtils;
/*     */ import awareline.main.ui.gui.verify.VerifyLogin;
/*     */ import awareline.main.utility.math.MathUtil;
/*     */ import com.allatori.annotations.ControlFlowObfuscation;
/*     */ import com.allatori.annotations.ExtensiveFlowObfuscation;
/*     */ import com.allatori.annotations.StringEncryption;
/*     */ import com.allatori.annotations.StringEncryptionType;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ @ControlFlowObfuscation("enable")
/*     */ @ExtensiveFlowObfuscation("maximum")
/*     */ @StringEncryption("maximum")
/*     */ @StringEncryptionType("fast")
/*     */ public class VerifyData
/*     */ {
/*  27 */   public static final VerifyData instance = new VerifyData();
/*     */   
/*     */   public final boolean isBetaVersion = true;
/*  30 */   public final String User = randomStringHeavy();
/*     */   
/*     */   public VerifyLogin verifyLogin;
/*     */   public HWIDUtils hwidUtils;
/*  34 */   public String strGetHWIDURL = randomStringHeavy(), strGetVersionURL = randomStringHeavy();
/*  35 */   public String UserName = randomStringHeavy(), clientVersion = randomStringHeavy();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  41 */     this.strGetHWIDURL = "https://gitee.com/";
/*  42 */     this.strGetVersionURL = "https://gitee.com/";
/*  43 */     this.clientVersion = "1.0";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  50 */     if (AwarelineAntiLeak.class != null && HWIDUtils.class != null && HWIDUtilsMCStart.class != null && WebUtils.class != null && HttpUtil.class != null && Base64.class != null) {
/*     */       
/*  52 */       this.verifyLogin = new VerifyLogin();
/*  53 */       this.hwidUtils = new HWIDUtils();
/*     */       
/*  55 */       sendHWIDToLog();
/*  56 */       AwarelineAntiLeak.instance.init();
/*     */     } else {
/*  58 */       crasher();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sendHWIDToLog() {
/*     */     try {
/*  64 */       if (this.hwidUtils != null) {
/*  65 */         this.hwidUtils.setClipboardString();
/*  66 */         System.err.println("------------------------------------------------------");
/*  67 */         System.err.println("HWID is copy to clipboard");
/*  68 */         System.err.println("Normal HWID is " + this.hwidUtils.getHWID(false));
/*     */         
/*  70 */         System.err.println("Alpha HWID is " + this.hwidUtils.getHWID(true));
/*     */         
/*  72 */         System.err.println("------------------------------------------------------");
/*     */       } 
/*  74 */     } catch (RuntimeException e) {
/*  75 */       System.err.println("get hwid error ".toUpperCase() + e);
/*  76 */       crasher();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void crasher() {
/*  82 */     Minecraft.getMinecraft().shutdown();
/*  83 */     crashForHeavy();
/*     */   }
/*     */ 
/*     */   
/*     */   public void hang() {
/*     */     while (true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void crashForHeavy() {
/*     */     while (true) {
/*     */       try {
/*  95 */         Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
/*  96 */         unsafeField.setAccessible(true);
/*     */         
/*  98 */         Unsafe unsafe = (Unsafe)unsafeField.get(null);
/*     */         
/* 100 */         for (long address = 0L;; address++)
/* 101 */           unsafe.setMemory(address, Long.MAX_VALUE, -128); 
/*     */         break;
/* 103 */       } catch (Throwable t) {
/* 104 */         crashForHeavy();
/*     */ 
/*     */         
/* 107 */         hang();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private String randomStringHeavy() {
/* 113 */     return randomString((int)MathUtil.getRandomInRange(2.0F, (30 + (int)MathUtil.randomNumber(5.0D, -5.0D))));
/*     */   }
/*     */ 
/*     */   
/*     */   private String randomString(int length) {
/* 118 */     StringBuilder builder = new StringBuilder();
/*     */     
/* 120 */     for (int i = 0; i < length; i++) {
/* 121 */       builder.append("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_".charAt(ThreadLocalRandom.current().nextInt(0, "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_"
/* 122 */               .length() - 1)));
/*     */     }
/*     */     
/* 125 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\antileak\VerifyData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */