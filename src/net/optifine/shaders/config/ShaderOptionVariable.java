/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ public class ShaderOptionVariable
/*     */   extends ShaderOption
/*     */ {
/*  13 */   private static final Pattern PATTERN_VARIABLE = Pattern.compile("^\\s*#define\\s+(\\w+)\\s+(-?[\\d.Ff]+|\\w+)\\s*(//.*)?$");
/*     */ 
/*     */   
/*     */   public ShaderOptionVariable(String name, String description, String value, String[] values, String path) {
/*  17 */     super(name, description, value, values, value, path);
/*  18 */     setVisible(((getValues()).length > 1));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSourceLine() {
/*  23 */     return "#define " + getName() + " " + getValue() + " // Shader option " + getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueText(String val) {
/*  28 */     String s = Shaders.translate("prefix." + getName(), "");
/*  29 */     String s1 = super.getValueText(val);
/*  30 */     String s2 = Shaders.translate("suffix." + getName(), "");
/*  31 */     String s3 = s + s1 + s2;
/*  32 */     return s3;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueColor(String val) {
/*  37 */     String s = val.toLowerCase();
/*  38 */     return (!s.equals("false") && !s.equals("off")) ? "§a" : "§c";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchesLine(String line) {
/*  43 */     Matcher matcher = PATTERN_VARIABLE.matcher(line);
/*     */     
/*  45 */     if (!matcher.matches())
/*     */     {
/*  47 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  51 */     String s = matcher.group(1);
/*  52 */     return s.matches(getName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ShaderOption parseOption(String line, String path) {
/*  58 */     Matcher matcher = PATTERN_VARIABLE.matcher(line);
/*     */     
/*  60 */     if (!matcher.matches())
/*     */     {
/*  62 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  66 */     String s = matcher.group(1);
/*  67 */     String s1 = matcher.group(2);
/*  68 */     String s2 = matcher.group(3);
/*  69 */     String s3 = StrUtils.getSegment(s2, "[", "]");
/*     */     
/*  71 */     if (s3 != null && !s3.isEmpty())
/*     */     {
/*  73 */       s2 = s2.replace(s3, "").trim();
/*     */     }
/*     */     
/*  76 */     String[] astring = parseValues(s1, s3);
/*     */     
/*  78 */     if (s != null && !s.isEmpty()) {
/*     */       
/*  80 */       path = StrUtils.removePrefix(path, "/shaders/");
/*  81 */       ShaderOption shaderoption = new ShaderOptionVariable(s, s2, s1, astring, path);
/*  82 */       return shaderoption;
/*     */     } 
/*     */ 
/*     */     
/*  86 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] parseValues(String value, String valuesStr) {
/*  93 */     String[] astring = { value };
/*     */     
/*  95 */     if (valuesStr == null)
/*     */     {
/*  97 */       return astring;
/*     */     }
/*     */ 
/*     */     
/* 101 */     valuesStr = valuesStr.trim();
/* 102 */     valuesStr = StrUtils.removePrefix(valuesStr, "[");
/* 103 */     valuesStr = StrUtils.removeSuffix(valuesStr, "]");
/* 104 */     valuesStr = valuesStr.trim();
/*     */     
/* 106 */     if (valuesStr.length() <= 0)
/*     */     {
/* 108 */       return astring;
/*     */     }
/*     */ 
/*     */     
/* 112 */     String[] astring1 = Config.tokenize(valuesStr, " ");
/*     */     
/* 114 */     if (astring1.length <= 0)
/*     */     {
/* 116 */       return astring;
/*     */     }
/*     */ 
/*     */     
/* 120 */     if (!Arrays.<String>asList(astring1).contains(value))
/*     */     {
/* 122 */       astring1 = (String[])Config.addObjectToArray((Object[])astring1, value, 0);
/*     */     }
/*     */     
/* 125 */     return astring1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\config\ShaderOptionVariable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */