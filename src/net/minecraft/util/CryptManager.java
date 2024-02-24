/*     */ package net.minecraft.util;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.spec.EncodedKeySpec;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.KeyGenerator;
/*     */ import javax.crypto.SecretKey;
/*     */ 
/*     */ public class CryptManager {
/*  18 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SecretKey createNewSharedKey() {
/*     */     try {
/*  27 */       KeyGenerator keygenerator = KeyGenerator.getInstance("AES");
/*  28 */       keygenerator.init(128);
/*  29 */       return keygenerator.generateKey();
/*     */     }
/*  31 */     catch (NoSuchAlgorithmException nosuchalgorithmexception) {
/*     */       
/*  33 */       throw new Error(nosuchalgorithmexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyPair generateKeyPair() {
/*     */     try {
/*  44 */       KeyPairGenerator keypairgenerator = KeyPairGenerator.getInstance("RSA");
/*  45 */       keypairgenerator.initialize(1024);
/*  46 */       return keypairgenerator.generateKeyPair();
/*     */     }
/*  48 */     catch (NoSuchAlgorithmException nosuchalgorithmexception) {
/*     */       
/*  50 */       nosuchalgorithmexception.printStackTrace();
/*  51 */       LOGGER.error("SimpleWhiteKey pair generation failed!");
/*  52 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getServerIdHash(String serverId, PublicKey publicKey, SecretKey secretKey) {
/*     */     try {
/*  63 */       return digestOperation("SHA-1", new byte[][] { serverId.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded() });
/*     */     }
/*  65 */     catch (UnsupportedEncodingException unsupportedencodingexception) {
/*     */       
/*  67 */       unsupportedencodingexception.printStackTrace();
/*  68 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] digestOperation(String algorithm, byte[]... data) {
/*     */     try {
/*  79 */       MessageDigest messagedigest = MessageDigest.getInstance(algorithm);
/*     */       
/*  81 */       for (byte[] abyte : data)
/*     */       {
/*  83 */         messagedigest.update(abyte);
/*     */       }
/*     */       
/*  86 */       return messagedigest.digest();
/*     */     }
/*  88 */     catch (NoSuchAlgorithmException nosuchalgorithmexception) {
/*     */       
/*  90 */       nosuchalgorithmexception.printStackTrace();
/*  91 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PublicKey decodePublicKey(byte[] encodedKey) {
/*     */     try {
/* 102 */       EncodedKeySpec encodedkeyspec = new X509EncodedKeySpec(encodedKey);
/* 103 */       KeyFactory keyfactory = KeyFactory.getInstance("RSA");
/* 104 */       return keyfactory.generatePublic(encodedkeyspec);
/*     */     }
/* 106 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*     */ 
/*     */     
/*     */     }
/* 110 */     catch (InvalidKeySpecException invalidKeySpecException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     LOGGER.error("Public key reconstitute failed!");
/* 116 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SecretKey decryptSharedKey(PrivateKey key, byte[] secretKeyEncrypted) {
/* 124 */     return new SecretKeySpec(decryptData(key, secretKeyEncrypted), "AES");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] encryptData(Key key, byte[] data) {
/* 132 */     return cipherOperation(1, key, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] decryptData(Key key, byte[] data) {
/* 140 */     return cipherOperation(2, key, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] cipherOperation(int opMode, Key key, byte[] data) {
/*     */     try {
/* 150 */       return createTheCipherInstance(opMode, key.getAlgorithm(), key).doFinal(data);
/*     */     }
/* 152 */     catch (IllegalBlockSizeException illegalblocksizeexception) {
/*     */       
/* 154 */       illegalblocksizeexception.printStackTrace();
/*     */     }
/* 156 */     catch (BadPaddingException badpaddingexception) {
/*     */       
/* 158 */       badpaddingexception.printStackTrace();
/*     */     } 
/*     */     
/* 161 */     LOGGER.error("Cipher data failed!");
/* 162 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Cipher createTheCipherInstance(int opMode, String transformation, Key key) {
/*     */     try {
/* 172 */       Cipher cipher = Cipher.getInstance(transformation);
/* 173 */       cipher.init(opMode, key);
/* 174 */       return cipher;
/*     */     }
/* 176 */     catch (InvalidKeyException invalidkeyexception) {
/*     */       
/* 178 */       invalidkeyexception.printStackTrace();
/*     */     }
/* 180 */     catch (NoSuchAlgorithmException nosuchalgorithmexception) {
/*     */       
/* 182 */       nosuchalgorithmexception.printStackTrace();
/*     */     }
/* 184 */     catch (NoSuchPaddingException nosuchpaddingexception) {
/*     */       
/* 186 */       nosuchpaddingexception.printStackTrace();
/*     */     } 
/*     */     
/* 189 */     LOGGER.error("Cipher creation failed!");
/* 190 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cipher createNetCipherInstance(int opMode, Key key) {
/*     */     try {
/* 200 */       Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
/* 201 */       cipher.init(opMode, key, new IvParameterSpec(key.getEncoded()));
/* 202 */       return cipher;
/*     */     }
/* 204 */     catch (GeneralSecurityException generalsecurityexception) {
/*     */       
/* 206 */       throw new RuntimeException(generalsecurityexception);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\CryptManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */