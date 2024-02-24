/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.Lang;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ public class ShaderOptionSwitch
/*     */   extends ShaderOption
/*     */ {
/*  12 */   private static final Pattern PATTERN_DEFINE = Pattern.compile("^\\s*(//)?\\s*#define\\s+(\\w+)\\s*(//.*)?$");
/*  13 */   private static final Pattern PATTERN_IFDEF = Pattern.compile("^\\s*#if(n)?def\\s+(\\w+)(\\s*)?$");
/*     */ 
/*     */   
/*     */   public ShaderOptionSwitch(String name, String description, String value, String path) {
/*  17 */     super(name, description, value, new String[] { "false", "true" }, value, path);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSourceLine() {
/*  22 */     return isTrue(getValue()) ? ("#define " + getName() + " // Shader option ON") : ("//#define " + getName() + " // Shader option OFF");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueText(String val) {
/*  27 */     String s = super.getValueText(val);
/*  28 */     return (s != val) ? s : (isTrue(val) ? Lang.getOn() : Lang.getOff());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueColor(String val) {
/*  33 */     return isTrue(val) ? "§a" : "§c";
/*     */   }
/*     */ 
/*     */   
/*     */   public static ShaderOption parseOption(String line, String path) {
/*  38 */     Matcher matcher = PATTERN_DEFINE.matcher(line);
/*     */     
/*  40 */     if (!matcher.matches())
/*     */     {
/*  42 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  46 */     String s = matcher.group(1);
/*  47 */     String s1 = matcher.group(2);
/*  48 */     String s2 = matcher.group(3);
/*     */     
/*  50 */     if (s1 != null && !s1.isEmpty()) {
/*     */       
/*  52 */       boolean flag = Config.equals(s, "//");
/*  53 */       boolean flag1 = !flag;
/*  54 */       path = StrUtils.removePrefix(path, "/shaders/");
/*  55 */       ShaderOption shaderoption = new ShaderOptionSwitch(s1, s2, String.valueOf(flag1), path);
/*  56 */       return shaderoption;
/*     */     } 
/*     */ 
/*     */     
/*  60 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesLine(String line) {
/*  67 */     Matcher matcher = PATTERN_DEFINE.matcher(line);
/*     */     
/*  69 */     if (!matcher.matches())
/*     */     {
/*  71 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  75 */     String s = matcher.group(2);
/*  76 */     return s.matches(getName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkUsed() {
/*  82 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUsedInLine(String line) {
/*  87 */     Matcher matcher = PATTERN_IFDEF.matcher(line);
/*     */     
/*  89 */     if (matcher.matches()) {
/*     */       
/*  91 */       String s = matcher.group(2);
/*     */       
/*  93 */       if (s.equals(getName()))
/*     */       {
/*  95 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isTrue(String val) {
/* 104 */     return Boolean.valueOf(val).booleanValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\config\ShaderOptionSwitch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */