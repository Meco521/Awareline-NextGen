/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ public enum EnumChatFormatting
/*     */ {
/*  13 */   BLACK("BLACK", '0', 0),
/*  14 */   DARK_BLUE("DARK_BLUE", '1', 1),
/*  15 */   DARK_GREEN("DARK_GREEN", '2', 2),
/*  16 */   DARK_AQUA("DARK_AQUA", '3', 3),
/*  17 */   DARK_RED("DARK_RED", '4', 4),
/*  18 */   DARK_PURPLE("DARK_PURPLE", '5', 5),
/*  19 */   GOLD("GOLD", '6', 6),
/*  20 */   GRAY("GRAY", '7', 7),
/*  21 */   DARK_GRAY("DARK_GRAY", '8', 8),
/*  22 */   BLUE("BLUE", '9', 9),
/*  23 */   GREEN("GREEN", 'a', 10),
/*  24 */   AQUA("AQUA", 'b', 11),
/*  25 */   RED("RED", 'c', 12),
/*  26 */   LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13),
/*  27 */   YELLOW("YELLOW", 'e', 14),
/*  28 */   WHITE("WHITE", 'f', 15),
/*  29 */   OBFUSCATED("OBFUSCATED", 'k', true),
/*  30 */   BOLD("BOLD", 'l', true),
/*  31 */   STRIKETHROUGH("STRIKETHROUGH", 'm', true),
/*  32 */   UNDERLINE("UNDERLINE", 'n', true),
/*  33 */   ITALIC("ITALIC", 'o', true),
/*  34 */   RESET("RESET", 'r', -1);
/*     */   static {
/*  36 */     nameMapping = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  42 */     formattingCodePattern = Pattern.compile("(?i)§[0-9A-FK-OR]");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 192 */     for (EnumChatFormatting enumchatformatting : values())
/*     */     {
/* 194 */       nameMapping.put(func_175745_c(enumchatformatting.name), enumchatformatting); } 
/*     */   }
/*     */   
/*     */   private static final Map<String, EnumChatFormatting> nameMapping;
/*     */   private static final Pattern formattingCodePattern;
/*     */   private final String name;
/*     */   private final char formattingCode;
/*     */   private final boolean fancyStyling;
/*     */   private final String controlString;
/*     */   private final int colorIndex;
/*     */   
/*     */   private static String func_175745_c(String p_175745_0_) {
/*     */     return p_175745_0_.toLowerCase().replaceAll("[^a-z]", "");
/*     */   }
/*     */   
/*     */   EnumChatFormatting(String formattingName, char formattingCodeIn, boolean fancyStylingIn, int colorIndex) {
/*     */     this.name = formattingName;
/*     */     this.formattingCode = formattingCodeIn;
/*     */     this.fancyStyling = fancyStylingIn;
/*     */     this.colorIndex = colorIndex;
/*     */     this.controlString = "§" + formattingCodeIn;
/*     */   }
/*     */   
/*     */   public static String getTextWithoutFormattingCodesFast(String text) {
/*     */     if (text == null)
/*     */       return null; 
/*     */     StringBuilder b = new StringBuilder();
/*     */     for (int i = 0; i < text.length(); i++) {
/*     */       char c = text.charAt(i);
/*     */       if (c == '搂' && i < text.length() - 1) {
/*     */         i++;
/*     */       } else {
/*     */         b.append(c);
/*     */       } 
/*     */     } 
/*     */     return b.toString();
/*     */   }
/*     */   
/*     */   public int getColorIndex() {
/*     */     return this.colorIndex;
/*     */   }
/*     */   
/*     */   public boolean isFancyStyling() {
/*     */     return this.fancyStyling;
/*     */   }
/*     */   
/*     */   public boolean isColor() {
/*     */     return (!this.fancyStyling && this != RESET);
/*     */   }
/*     */   
/*     */   public String getFriendlyName() {
/*     */     return name().toLowerCase();
/*     */   }
/*     */   
/*     */   public String toString() {
/*     */     return this.controlString;
/*     */   }
/*     */   
/*     */   public static String getTextWithoutFormattingCodes(String text) {
/*     */     return (text == null) ? null : formattingCodePattern.matcher(text).replaceAll("");
/*     */   }
/*     */   
/*     */   public static EnumChatFormatting getValueByName(String friendlyName) {
/*     */     return (friendlyName == null) ? null : nameMapping.get(func_175745_c(friendlyName));
/*     */   }
/*     */   
/*     */   public static EnumChatFormatting func_175744_a(int p_175744_0_) {
/*     */     if (p_175744_0_ < 0)
/*     */       return RESET; 
/*     */     for (EnumChatFormatting enumchatformatting : values()) {
/*     */       if (enumchatformatting.colorIndex == p_175744_0_)
/*     */         return enumchatformatting; 
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   public static Collection<String> getValidValues(boolean p_96296_0_, boolean p_96296_1_) {
/*     */     List<String> list = Lists.newArrayList();
/*     */     for (EnumChatFormatting enumchatformatting : values()) {
/*     */       if ((!enumchatformatting.isColor() || p_96296_0_) && (!enumchatformatting.fancyStyling || p_96296_1_))
/*     */         list.add(enumchatformatting.getFriendlyName()); 
/*     */     } 
/*     */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\EnumChatFormatting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */