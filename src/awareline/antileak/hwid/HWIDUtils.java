/*     */ package awareline.antileak.hwid;
/*     */ 
/*     */ import awareline.antileak.VerifyData;
/*     */ import awareline.antileak.encode.Base64;
/*     */ import awareline.main.ui.font.fontmanager.utils.StringUtils;
/*     */ import com.allatori.annotations.ControlFlowObfuscation;
/*     */ import com.allatori.annotations.ExtensiveFlowObfuscation;
/*     */ import com.allatori.annotations.StringEncryption;
/*     */ import com.allatori.annotations.StringEncryptionType;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.Clipboard;
/*     */ import java.awt.datatransfer.StringSelection;
/*     */ import java.awt.datatransfer.Transferable;
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
/*     */ import java.util.Objects;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ControlFlowObfuscation("enable")
/*     */ @ExtensiveFlowObfuscation("normal")
/*     */ @StringEncryption("enable")
/*     */ @StringEncryptionType("fast")
/*     */ public class HWIDUtils
/*     */ {
/*     */   public boolean junkCode;
/*     */   public boolean junkCode1;
/*     */   public boolean junkCode2;
/*     */   public boolean junkCode3;
/*     */   public boolean junkCode4;
/*     */   public boolean junkCode5;
/*     */   public boolean junkCode6;
/*     */   public boolean junkCode7;
/*     */   public boolean junkCode8;
/*     */   public boolean junkCode9;
/*     */   public boolean junkCode11;
/*     */   
/*     */   public final String getHWID(int id) {
/*  54 */     if (id != 230801) {
/*  55 */       Minecraft.getMinecraft().shutdown();
/*  56 */       return StringUtils.randomStringHeavy();
/*     */     } 
/*  58 */     if (checkHWIDUtilsNotNull()) {
/*  59 */       Minecraft.getMinecraft().shutdown();
/*  60 */       return StringUtils.randomStringHeavy();
/*     */     } 
/*  62 */     Validate.notNull(getBetaHWID(), getNormalVersionHWID(), new Object[0]);
/*  63 */     VerifyData.instance.getClass(); return Base64.decode(getBetaHWID());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getHWID(boolean getBeta) {
/*  69 */     if (checkHWIDUtilsNotNull()) {
/*  70 */       Minecraft.getMinecraft().shutdown();
/*  71 */       return StringUtils.randomStringHeavy();
/*     */     } 
/*  73 */     Validate.notNull(getBetaHWID(), getNormalVersionHWID(), new Object[0]);
/*  74 */     return getBeta ? Base64.decode(getBetaHWID()) : getNormalVersionHWID();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkHWIDUtilsNotNull() {
/*  79 */     if (getBetaHWID().isEmpty()) {
/*  80 */       return true;
/*     */     }
/*  82 */     if (Base64.decode(getBetaHWID()).isEmpty()) {
/*  83 */       return true;
/*     */     }
/*  85 */     if (Base64.decode(getNormalVersionHWID()).isEmpty()) {
/*  86 */       return true;
/*     */     }
/*  88 */     return getNormalVersionHWID().isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   private String getBetaVersionHWID() throws NoSuchAlgorithmException {
/*  93 */     StringBuilder s = new StringBuilder();
/*  94 */     String main = System.getenv("PROCESS_IDENTIFIER") + System.getenv("COMPUTERNAME");
/*  95 */     byte[] bytes = main.getBytes(StandardCharsets.UTF_8);
/*  96 */     MessageDigest messageDigest = MessageDigest.getInstance("MD5");
/*  97 */     byte[] md5 = messageDigest.digest(bytes);
/*  98 */     for (byte b : md5) {
/*  99 */       s.append(Integer.toHexString(b & 0xFF | 0x300), 0, 3);
/*     */     }
/* 101 */     return s.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setClipboardString() {
/* 106 */     Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
/*     */ 
/*     */     
/* 109 */     VerifyData.instance.getClass();
/* 110 */     String needCopyText = "NormalHWID锛�" + getHWID(false) + " with AlphaHWID锛�" + getHWID(true);
/*     */ 
/*     */ 
/*     */     
/* 114 */     Transferable trans = new StringSelection(needCopyText);
/*     */     
/* 116 */     clipboard.setContents(trans, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNormalVersionHWID() {
/*     */     try {
/* 123 */       return Objects.<String>requireNonNull(AESEncode(getQQ() + getNormalVersionMainHWID(), "kWOAZJpqao0R45rV"));
/* 124 */     } catch (Exception e) {
/* 125 */       e.printStackTrace();
/*     */       
/* 127 */       return StringUtils.randomStringHeavy();
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getBetaHWID() {
/*     */     try {
/* 133 */       return Base64.encode(getBetaVersionHWID());
/* 134 */     } catch (Exception e) {
/* 135 */       e.printStackTrace();
/*     */       
/* 137 */       return StringUtils.randomStringHeavy();
/*     */     } 
/*     */   }
/*     */   
/*     */   public final String getNormalVersionMainHWID() throws NoSuchAlgorithmException {
/* 142 */     StringBuilder s = new StringBuilder();
/* 143 */     String main = "鎿嶄綘濡堥�煎�掑崠姝讳釜濡堝" + System.getenv("PROCESS_IDENTIFIER") + "浣犲浜嗕釜閫肩殑姝诲簾鐗╃牬瑙ｄ綘濡堝憿" + System.getenv("COMPUTERNAME") + "NMSL51";
/* 144 */     byte[] bytes = main.getBytes(StandardCharsets.UTF_8);
/* 145 */     MessageDigest messageDigest = MessageDigest.getInstance("MD5");
/* 146 */     byte[] md5 = messageDigest.digest(bytes);
/* 147 */     for (byte b : md5) {
/* 148 */       s.append(Integer.toHexString(b & 0xFF | 0x300), 0, 3);
/*     */     }
/* 150 */     return s.substring(s.length() - 20, s.length());
/*     */   }
/*     */   
/*     */   private String AESEncode(String plainText, String key) throws Exception {
/* 154 */     if (key == null) {
/* 155 */       System.err.print("Key涓虹┖null");
/* 156 */       return null;
/*     */     } 
/*     */     
/* 159 */     if (key.length() != 16) {
/* 160 */       System.err.print("Key闀垮害涓嶆槸16浣�");
/* 161 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 166 */     String ENCODING = "utf-8";
/*     */ 
/*     */ 
/*     */     
/* 170 */     String ALGORITHM = "AES";
/* 171 */     SecretKey secretKey = new SecretKeySpec(key.getBytes("utf-8"), "AES");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 176 */     String PATTERN = "AES/ECB/pkcs5padding";
/* 177 */     Cipher cipher = Cipher.getInstance("AES/ECB/pkcs5padding");
/*     */     
/* 179 */     cipher.init(1, secretKey);
/*     */     
/* 181 */     byte[] encryptData = cipher.doFinal(plainText.getBytes("utf-8"));
/* 182 */     return Base64.getEncoder().encodeToString(encryptData);
/*     */   }
/*     */ 
/*     */   
/*     */   private List<String> getQQ() {
/* 187 */     ArrayList<String> qq = new ArrayList<>();
/*     */     try {
/* 189 */       File qqData = new File(System.getenv("PUBLIC") + "\\Documents\\Tencent\\QQ\\UserDataInfo.ini");
/* 190 */       if (qqData.exists() && qqData.isFile()) {
/* 191 */         BufferedReader stream = new BufferedReader(new InputStreamReader(Files.newInputStream(qqData.toPath(), new java.nio.file.OpenOption[0])));
/*     */         String line;
/* 193 */         while ((line = stream.readLine()) != null && !line.isEmpty()) {
/* 194 */           if (line.startsWith("UserDataSavePath=")) {
/* 195 */             File tencentFiles = new File(line.split("=")[1]);
/* 196 */             if (tencentFiles.exists() && tencentFiles.isDirectory()) {
/* 197 */               for (File qqdir : tencentFiles.listFiles()) {
/* 198 */                 if (qqdir.isDirectory() && qqdir.getName().length() >= 6 && qqdir.getName().length() <= 10 && qqdir.getName().matches("^\\d*$")) {
/* 199 */                   qq.add(qqdir.getName());
/*     */                 }
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/* 206 */     } catch (Throwable ignored) {
/* 207 */       System.err.println("QQ is null?");
/*     */     } 
/* 209 */     return qq;
/*     */   }
/*     */   
/*     */   public final String junkCode() {
/* 213 */     return "gsegsegs{seg2=" + this.junkCode + ", 4sgse11=" + this.junkCode1 + ", 523=" + this.junkCode2 + ", 5seg=" + this.junkCode3 + ", 05segse43=" + this.junkCode4 + ", seg=" + this.junkCode5 + ", 8segseg=" + this.junkCode6 + ", 3egseg=" + this.junkCode7 + ", 7segs=" + this.junkCode8 + ", sseegegse5=" + this.junkCode9 + ", 4segse2=" + this.junkCode11 + '=';
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\antileak\hwid\HWIDUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */