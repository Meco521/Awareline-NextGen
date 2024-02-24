/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class ShaderParser
/*     */ {
/*   8 */   public static Pattern PATTERN_UNIFORM = Pattern.compile("\\s*uniform\\s+\\w+\\s+(\\w+).*");
/*   9 */   public static Pattern PATTERN_ATTRIBUTE = Pattern.compile("\\s*attribute\\s+\\w+\\s+(\\w+).*");
/*  10 */   public static Pattern PATTERN_CONST_INT = Pattern.compile("\\s*const\\s+int\\s+(\\w+)\\s*=\\s*([-+.\\w]+)\\s*;.*");
/*  11 */   public static Pattern PATTERN_CONST_FLOAT = Pattern.compile("\\s*const\\s+float\\s+(\\w+)\\s*=\\s*([-+.\\w]+)\\s*;.*");
/*  12 */   public static Pattern PATTERN_CONST_VEC4 = Pattern.compile("\\s*const\\s+vec4\\s+(\\w+)\\s*=\\s*(.+)\\s*;.*");
/*  13 */   public static Pattern PATTERN_CONST_BOOL = Pattern.compile("\\s*const\\s+bool\\s+(\\w+)\\s*=\\s*(\\w+)\\s*;.*");
/*  14 */   public static Pattern PATTERN_PROPERTY = Pattern.compile("\\s*(/\\*|//)?\\s*([A-Z]+):\\s*(\\w+)\\s*(\\*/.*|\\s*)");
/*  15 */   public static Pattern PATTERN_EXTENSION = Pattern.compile("\\s*#\\s*extension\\s+(\\w+)\\s*:\\s*(\\w+).*");
/*  16 */   public static Pattern PATTERN_DEFERRED_FSH = Pattern.compile(".*deferred\\d*\\.fsh");
/*  17 */   public static Pattern PATTERN_COMPOSITE_FSH = Pattern.compile(".*composite\\d*\\.fsh");
/*  18 */   public static Pattern PATTERN_FINAL_FSH = Pattern.compile(".*final\\.fsh");
/*  19 */   public static Pattern PATTERN_DRAW_BUFFERS = Pattern.compile("[0-7N]*");
/*     */ 
/*     */   
/*     */   public static ShaderLine parseLine(String line) {
/*  23 */     Matcher matcher = PATTERN_UNIFORM.matcher(line);
/*     */     
/*  25 */     if (matcher.matches())
/*     */     {
/*  27 */       return new ShaderLine(1, matcher.group(1), "", line);
/*     */     }
/*     */ 
/*     */     
/*  31 */     Matcher matcher1 = PATTERN_ATTRIBUTE.matcher(line);
/*     */     
/*  33 */     if (matcher1.matches())
/*     */     {
/*  35 */       return new ShaderLine(2, matcher1.group(1), "", line);
/*     */     }
/*     */ 
/*     */     
/*  39 */     Matcher matcher2 = PATTERN_PROPERTY.matcher(line);
/*     */     
/*  41 */     if (matcher2.matches())
/*     */     {
/*  43 */       return new ShaderLine(6, matcher2.group(2), matcher2.group(3), line);
/*     */     }
/*     */ 
/*     */     
/*  47 */     Matcher matcher3 = PATTERN_CONST_INT.matcher(line);
/*     */     
/*  49 */     if (matcher3.matches())
/*     */     {
/*  51 */       return new ShaderLine(3, matcher3.group(1), matcher3.group(2), line);
/*     */     }
/*     */ 
/*     */     
/*  55 */     Matcher matcher4 = PATTERN_CONST_FLOAT.matcher(line);
/*     */     
/*  57 */     if (matcher4.matches())
/*     */     {
/*  59 */       return new ShaderLine(4, matcher4.group(1), matcher4.group(2), line);
/*     */     }
/*     */ 
/*     */     
/*  63 */     Matcher matcher5 = PATTERN_CONST_BOOL.matcher(line);
/*     */     
/*  65 */     if (matcher5.matches())
/*     */     {
/*  67 */       return new ShaderLine(5, matcher5.group(1), matcher5.group(2), line);
/*     */     }
/*     */ 
/*     */     
/*  71 */     Matcher matcher6 = PATTERN_EXTENSION.matcher(line);
/*     */     
/*  73 */     if (matcher6.matches())
/*     */     {
/*  75 */       return new ShaderLine(7, matcher6.group(1), matcher6.group(2), line);
/*     */     }
/*     */ 
/*     */     
/*  79 */     Matcher matcher7 = PATTERN_CONST_VEC4.matcher(line);
/*  80 */     return matcher7.matches() ? new ShaderLine(8, matcher7.group(1), matcher7.group(2), line) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getIndex(String uniform, String prefix, int minIndex, int maxIndex) {
/*  92 */     if (uniform.length() != prefix.length() + 1)
/*     */     {
/*  94 */       return -1;
/*     */     }
/*  96 */     if (!uniform.startsWith(prefix))
/*     */     {
/*  98 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 102 */     int i = uniform.charAt(prefix.length()) - 48;
/* 103 */     return (i >= minIndex && i <= maxIndex) ? i : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getShadowDepthIndex(String uniform) {
/* 109 */     return uniform.equals("shadow") ? 0 : (uniform.equals("watershadow") ? 1 : getIndex(uniform, "shadowtex", 0, 1));
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getShadowColorIndex(String uniform) {
/* 114 */     return uniform.equals("shadowcolor") ? 0 : getIndex(uniform, "shadowcolor", 0, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getDepthIndex(String uniform) {
/* 119 */     return getIndex(uniform, "depthtex", 0, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getColorIndex(String uniform) {
/* 124 */     int i = getIndex(uniform, "gaux", 1, 4);
/* 125 */     return (i > 0) ? (i + 3) : getIndex(uniform, "colortex", 4, 7);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isDeferred(String filename) {
/* 130 */     return PATTERN_DEFERRED_FSH.matcher(filename).matches();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isComposite(String filename) {
/* 135 */     return PATTERN_COMPOSITE_FSH.matcher(filename).matches();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isFinal(String filename) {
/* 140 */     return PATTERN_FINAL_FSH.matcher(filename).matches();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isValidDrawBuffers(String str) {
/* 145 */     return PATTERN_DRAW_BUFFERS.matcher(str).matches();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\config\ShaderParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */