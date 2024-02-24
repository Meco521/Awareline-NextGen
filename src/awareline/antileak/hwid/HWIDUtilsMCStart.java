/*     */ package awareline.antileak.hwid;
/*     */ 
/*     */ import awareline.main.ui.font.fontmanager.utils.StringUtils;
/*     */ import awareline.main.utility.math.MathUtil;
/*     */ import com.allatori.annotations.ControlFlowObfuscation;
/*     */ import com.allatori.annotations.ExtensiveFlowObfuscation;
/*     */ import com.allatori.annotations.StringEncryption;
/*     */ import com.allatori.annotations.StringEncryptionType;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.InputStreamReader;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Base64;
/*     */ import java.util.List;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ 
/*     */ @ControlFlowObfuscation("enable")
/*     */ @ExtensiveFlowObfuscation("normal")
/*     */ @StringEncryption("enable")
/*     */ @StringEncryptionType("fast")
/*     */ public class HWIDUtilsMCStart
/*     */ {
/*     */   private void shutdown(int id) {
/*  33 */     Minecraft.getMinecraft().shutdown();
/*     */   }
/*     */   
/*     */   public final String getHWID(int id) {
/*  37 */     if (id != 20230520) {
/*  38 */       shutdown(2);
/*  39 */       return StringUtils.randomStringHeavy();
/*     */     } 
/*  41 */     if (checkHWIDUtilsNotNull()) {
/*  42 */       Minecraft.getMinecraft().shutdown();
/*  43 */       return StringUtils.randomStringDefault((int)MathUtil.getRandomInRange(25.0F, 45.0F));
/*     */     } 
/*  45 */     Validate.notNull(getNormalVersionHWID(20230520));
/*  46 */     return getNormalVersionHWID(20230520);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkHWIDUtilsNotNull() {
/*  53 */     return getNormalVersionHWID(20230520).isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNormalVersionHWID(int id) {
/*  59 */     if (id != 20230520) {
/*     */       
/*  61 */       shutdown(161);
/*  62 */       return StringUtils.randomStringDefault("213120-943812-4812".length());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  68 */       return AESEncode(getQQ() + getNormalVersionMainHWID(), "kWOAZJpqao0R45rV");
/*  69 */     } catch (Exception e) {
/*  70 */       Minecraft.getMinecraft().shutdown();
/*  71 */       e.printStackTrace();
/*     */ 
/*     */       
/*  74 */       return StringUtils.randomStringDefault(30);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String getNormalVersionMainHWID() throws NoSuchAlgorithmException {
/*  80 */     StringBuilder s = new StringBuilder();
/*  81 */     String main = "鎿嶄綘濡堥�煎�掑崠姝讳釜濡堝" + System.getenv("PROCESS_IDENTIFIER") + "浣犲浜嗕釜閫肩殑姝诲簾鐗╃牬瑙ｄ綘濡堝憿" + System.getenv("COMPUTERNAME") + "NMSL51";
/*  82 */     byte[] bytes = main.getBytes(StandardCharsets.UTF_8);
/*  83 */     MessageDigest messageDigest = MessageDigest.getInstance("MD5");
/*  84 */     byte[] md5 = messageDigest.digest(bytes);
/*  85 */     for (byte b : md5) {
/*  86 */       s.append(Integer.toHexString(b & 0xFF | 0x300), 0, 3);
/*     */     }
/*  88 */     return s.substring(s.length() - 20, s.length());
/*     */   }
/*     */   
/*     */   private String AESEncode(String plainText, String key) throws Exception {
/*  92 */     if (key == null)
/*     */     {
/*  94 */       return null;
/*     */     }
/*     */     
/*  97 */     if (key.length() != 16)
/*     */     {
/*  99 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 103 */     String ENCODING = "utf-8";
/*     */     
/* 105 */     String ALGORITHM = "AES";
/*     */     
/* 107 */     SecretKey secretKey = new SecretKeySpec(key.getBytes("utf-8"), "AES");
/*     */ 
/*     */     
/* 110 */     String PATTERN = "AES/ECB/pkcs5padding";
/* 111 */     Cipher cipher = Cipher.getInstance("AES/ECB/pkcs5padding");
/*     */     
/* 113 */     cipher.init(1, secretKey);
/*     */     
/* 115 */     byte[] encryptData = cipher.doFinal(plainText.getBytes("utf-8"));
/* 116 */     return Base64.getEncoder().encodeToString(encryptData);
/*     */   }
/*     */   
/*     */   private List<String> getQQ() {
/* 120 */     ArrayList<String> qq = new ArrayList<>();
/*     */     try {
/* 122 */       File qqData = new File(System.getenv("PUBLIC") + "\\Documents\\Tencent\\QQ\\UserDataInfo.ini");
/* 123 */       if (qqData.exists() && qqData.isFile()) {
/* 124 */         BufferedReader stream = new BufferedReader(new InputStreamReader(Files.newInputStream(qqData.toPath(), new java.nio.file.OpenOption[0])));
/*     */         String line;
/* 126 */         while ((line = stream.readLine()) != null && !line.isEmpty()) {
/* 127 */           if (line.startsWith("UserDataSavePath=")) {
/* 128 */             File tencentFiles = new File(line.split("=")[1]);
/* 129 */             if (tencentFiles.exists() && tencentFiles.isDirectory()) {
/* 130 */               for (File qqdir : tencentFiles.listFiles()) {
/* 131 */                 if (qqdir.isDirectory() && qqdir.getName().length() >= 6 && qqdir.getName().length() <= 10 && qqdir.getName().matches("^\\d*$")) {
/* 132 */                   qq.add(qqdir.getName());
/*     */                 }
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/* 139 */     } catch (Throwable throwable) {}
/*     */ 
/*     */     
/* 142 */     return qq;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\antileak\hwid\HWIDUtilsMCStart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */