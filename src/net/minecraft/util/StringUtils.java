/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.regex.Pattern;
/*    */ import java.util.stream.Collectors;
/*    */ 
/*    */ public class StringUtils
/*    */ {
/*  8 */   private static final Pattern patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
/*  9 */   private static final Pattern colorPatternCodes = Pattern.compile("(?i)\\u00A7[\\dA-F]");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String ticksToElapsedTime(int ticks) {
/* 16 */     int i = ticks / 20;
/* 17 */     int j = i / 60;
/* 18 */     i %= 60;
/* 19 */     return (i < 10) ? (j + ":0" + i) : (j + ":" + i);
/*    */   }
/*    */   
/*    */   public static String stripColorCodes(String text) {
/* 23 */     return colorPatternCodes.matcher(text).replaceAll("");
/*    */   }
/*    */   
/*    */   public static String stripControlCodes(String text) {
/* 27 */     return patternControlCode.matcher(text).replaceAll("");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isNullOrEmpty(String string) {
/* 35 */     return org.apache.commons.lang3.StringUtils.isEmpty(string);
/*    */   }
/*    */   
/*    */   public static String digitString(String string) {
/* 39 */     return string.chars().mapToObj(c -> Character.valueOf((char)c)).filter(Character::isDigit).map(String::valueOf).collect(Collectors.joining());
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\StringUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */