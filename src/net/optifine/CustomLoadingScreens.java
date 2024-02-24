/*     */ package net.optifine;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import net.minecraft.network.PacketThreadUtil;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.util.ResUtils;
/*     */ import org.apache.commons.lang3.tuple.ImmutablePair;
/*     */ import org.apache.commons.lang3.tuple.Pair;
/*     */ 
/*     */ public class CustomLoadingScreens {
/*  14 */   private static CustomLoadingScreen[] screens = null;
/*  15 */   private static int screensMinDimensionId = 0;
/*     */ 
/*     */   
/*     */   public static CustomLoadingScreen getCustomLoadingScreen() {
/*  19 */     if (screens == null)
/*     */     {
/*  21 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  25 */     int i = PacketThreadUtil.lastDimensionId;
/*  26 */     int j = i - screensMinDimensionId;
/*  27 */     CustomLoadingScreen customloadingscreen = null;
/*     */     
/*  29 */     if (j >= 0 && j < screens.length)
/*     */     {
/*  31 */       customloadingscreen = screens[j];
/*     */     }
/*     */     
/*  34 */     return customloadingscreen;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void update() {
/*  40 */     screens = null;
/*  41 */     screensMinDimensionId = 0;
/*  42 */     Pair<CustomLoadingScreen[], Integer> pair = parseScreens();
/*  43 */     screens = (CustomLoadingScreen[])pair.getLeft();
/*  44 */     screensMinDimensionId = ((Integer)pair.getRight()).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   private static Pair<CustomLoadingScreen[], Integer> parseScreens() {
/*  49 */     String s = "optifine/gui/loading/background";
/*  50 */     String s1 = ".png";
/*  51 */     String[] astring = ResUtils.collectFiles(s, s1);
/*  52 */     Map<Integer, String> map = new HashMap<>();
/*     */     
/*  54 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/*  56 */       String s2 = astring[i];
/*  57 */       String s3 = StrUtils.removePrefixSuffix(s2, s, s1);
/*  58 */       int j = Config.parseInt(s3, -2147483648);
/*     */       
/*  60 */       if (j == Integer.MIN_VALUE) {
/*     */         
/*  62 */         warn("Invalid dimension ID: " + s3 + ", path: " + s2);
/*     */       }
/*     */       else {
/*     */         
/*  66 */         map.put(Integer.valueOf(j), s2);
/*     */       } 
/*     */     } 
/*     */     
/*  70 */     Set<Integer> set = map.keySet();
/*  71 */     Integer[] ainteger = set.<Integer>toArray(new Integer[0]);
/*  72 */     Arrays.sort((Object[])ainteger);
/*     */     
/*  74 */     if (ainteger.length <= 0)
/*     */     {
/*  76 */       return (Pair<CustomLoadingScreen[], Integer>)new ImmutablePair(null, Integer.valueOf(0));
/*     */     }
/*     */ 
/*     */     
/*  80 */     String s5 = "optifine/gui/loading/loading.properties";
/*  81 */     Properties properties = ResUtils.readProperties(s5, "CustomLoadingScreens");
/*  82 */     int k = ainteger[0].intValue();
/*  83 */     int l = ainteger[ainteger.length - 1].intValue();
/*  84 */     int i1 = l - k + 1;
/*  85 */     CustomLoadingScreen[] acustomloadingscreen = new CustomLoadingScreen[i1];
/*     */     
/*  87 */     for (int j1 = 0; j1 < ainteger.length; j1++) {
/*     */       
/*  89 */       Integer integer = ainteger[j1];
/*  90 */       String s4 = map.get(integer);
/*  91 */       acustomloadingscreen[integer.intValue() - k] = CustomLoadingScreen.parseScreen(s4, integer.intValue(), properties);
/*     */     } 
/*     */     
/*  94 */     return (Pair<CustomLoadingScreen[], Integer>)new ImmutablePair(acustomloadingscreen, Integer.valueOf(k));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void warn(String str) {
/* 100 */     Config.warn("CustomLoadingScreen: " + str);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void dbg(String str) {
/* 105 */     Config.dbg("CustomLoadingScreen: " + str);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\CustomLoadingScreens.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */