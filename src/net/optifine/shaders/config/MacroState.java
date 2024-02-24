/*     */ package net.optifine.shaders.config;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.expr.ExpressionParser;
/*     */ import net.optifine.expr.IExpression;
/*     */ import net.optifine.expr.IExpressionBool;
/*     */ import net.optifine.expr.IExpressionFloat;
/*     */ import net.optifine.expr.ParseException;
/*     */ 
/*     */ public class MacroState {
/*  13 */   private final Deque<Boolean> dequeState = new ArrayDeque<>(); private boolean active = true;
/*  14 */   private final Deque<Boolean> dequeResolved = new ArrayDeque<>();
/*  15 */   private final Map<String, String> mapMacroValues = new HashMap<>();
/*  16 */   private static final Pattern PATTERN_DIRECTIVE = Pattern.compile("\\s*#\\s*(\\w+)\\s*(.*)");
/*  17 */   private static final Pattern PATTERN_DEFINED = Pattern.compile("defined\\s+(\\w+)");
/*  18 */   private static final Pattern PATTERN_DEFINED_FUNC = Pattern.compile("defined\\s*\\(\\s*(\\w+)\\s*\\)");
/*  19 */   private static final Pattern PATTERN_MACRO = Pattern.compile("(\\w+)");
/*     */   private static final String DEFINE = "define";
/*     */   private static final String UNDEF = "undef";
/*     */   private static final String IFDEF = "ifdef";
/*     */   private static final String IFNDEF = "ifndef";
/*     */   private static final String IF = "if";
/*     */   private static final String ELSE = "else";
/*     */   private static final String ELIF = "elif";
/*     */   private static final String ENDIF = "endif";
/*  28 */   private static final List<String> MACRO_NAMES = Arrays.asList(new String[] { "define", "undef", "ifdef", "ifndef", "if", "else", "elif", "endif" });
/*     */ 
/*     */   
/*     */   public boolean processLine(String line) {
/*  32 */     Matcher matcher = PATTERN_DIRECTIVE.matcher(line);
/*     */     
/*  34 */     if (!matcher.matches())
/*     */     {
/*  36 */       return this.active;
/*     */     }
/*     */ 
/*     */     
/*  40 */     String s = matcher.group(1);
/*  41 */     String s1 = matcher.group(2);
/*  42 */     int i = s1.indexOf("//");
/*     */     
/*  44 */     if (i >= 0)
/*     */     {
/*  46 */       s1 = s1.substring(0, i);
/*     */     }
/*     */     
/*  49 */     boolean flag = this.active;
/*  50 */     processMacro(s, s1);
/*  51 */     this.active = !this.dequeState.contains(Boolean.FALSE);
/*  52 */     return (this.active || flag);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isMacroLine(String line) {
/*  58 */     Matcher matcher = PATTERN_DIRECTIVE.matcher(line);
/*     */     
/*  60 */     if (!matcher.matches())
/*     */     {
/*  62 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  66 */     String s = matcher.group(1);
/*  67 */     return MACRO_NAMES.contains(s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void processMacro(String name, String param) {
/*  73 */     StringTokenizer stringtokenizer = new StringTokenizer(param, " \t");
/*  74 */     String s = stringtokenizer.hasMoreTokens() ? stringtokenizer.nextToken() : "";
/*  75 */     String s1 = stringtokenizer.hasMoreTokens() ? stringtokenizer.nextToken("").trim() : "";
/*     */     
/*  77 */     if (name.equals("define")) {
/*     */       
/*  79 */       this.mapMacroValues.put(s, s1);
/*     */     }
/*  81 */     else if (name.equals("undef")) {
/*     */       
/*  83 */       this.mapMacroValues.remove(s);
/*     */     }
/*  85 */     else if (name.equals("ifdef")) {
/*     */       
/*  87 */       boolean flag6 = this.mapMacroValues.containsKey(s);
/*  88 */       this.dequeState.add(Boolean.valueOf(flag6));
/*  89 */       this.dequeResolved.add(Boolean.valueOf(flag6));
/*     */     }
/*  91 */     else if (name.equals("ifndef")) {
/*     */       
/*  93 */       boolean flag5 = !this.mapMacroValues.containsKey(s);
/*  94 */       this.dequeState.add(Boolean.valueOf(flag5));
/*  95 */       this.dequeResolved.add(Boolean.valueOf(flag5));
/*     */     }
/*  97 */     else if (name.equals("if")) {
/*     */       
/*  99 */       boolean flag4 = eval(param);
/* 100 */       this.dequeState.add(Boolean.valueOf(flag4));
/* 101 */       this.dequeResolved.add(Boolean.valueOf(flag4));
/*     */     }
/* 103 */     else if (!this.dequeState.isEmpty()) {
/*     */       
/* 105 */       if (name.equals("elif")) {
/*     */         
/* 107 */         boolean flag3 = ((Boolean)this.dequeState.removeLast()).booleanValue();
/* 108 */         boolean flag7 = ((Boolean)this.dequeResolved.removeLast()).booleanValue();
/*     */         
/* 110 */         if (flag7)
/*     */         {
/* 112 */           this.dequeState.add(Boolean.valueOf(false));
/* 113 */           this.dequeResolved.add(Boolean.valueOf(flag7));
/*     */         }
/*     */         else
/*     */         {
/* 117 */           boolean flag8 = eval(param);
/* 118 */           this.dequeState.add(Boolean.valueOf(flag8));
/* 119 */           this.dequeResolved.add(Boolean.valueOf(flag8));
/*     */         }
/*     */       
/* 122 */       } else if (name.equals("else")) {
/*     */         
/* 124 */         boolean flag = ((Boolean)this.dequeState.removeLast()).booleanValue();
/* 125 */         boolean flag1 = ((Boolean)this.dequeResolved.removeLast()).booleanValue();
/* 126 */         boolean flag2 = !flag1;
/* 127 */         this.dequeState.add(Boolean.valueOf(flag2));
/* 128 */         this.dequeResolved.add(Boolean.valueOf(true));
/*     */       }
/* 130 */       else if (name.equals("endif")) {
/*     */         
/* 132 */         this.dequeState.removeLast();
/* 133 */         this.dequeResolved.removeLast();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean eval(String str) {
/* 140 */     Matcher matcher = PATTERN_DEFINED.matcher(str);
/* 141 */     str = matcher.replaceAll("defined_$1");
/* 142 */     Matcher matcher1 = PATTERN_DEFINED_FUNC.matcher(str);
/* 143 */     str = matcher1.replaceAll("defined_$1");
/* 144 */     boolean flag = false;
/* 145 */     int i = 0;
/*     */ 
/*     */     
/*     */     do {
/* 149 */       flag = false;
/* 150 */       Matcher matcher2 = PATTERN_MACRO.matcher(str);
/*     */       
/* 152 */       while (matcher2.find()) {
/*     */         
/* 154 */         String s = matcher2.group();
/*     */         
/* 156 */         if (!s.isEmpty()) {
/*     */           
/* 158 */           char c0 = s.charAt(0);
/*     */           
/* 160 */           if ((Character.isLetter(c0) || c0 == '_') && this.mapMacroValues.containsKey(s)) {
/*     */             
/* 162 */             String s1 = this.mapMacroValues.get(s);
/*     */             
/* 164 */             if (s1 == null)
/*     */             {
/* 166 */               s1 = "1";
/*     */             }
/*     */             
/* 169 */             int j = matcher2.start();
/* 170 */             int k = matcher2.end();
/* 171 */             str = str.substring(0, j) + " " + s1 + " " + str.substring(k);
/* 172 */             flag = true;
/* 173 */             i++;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 179 */     } while (flag && i < 100);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     if (i >= 100) {
/*     */       
/* 187 */       Config.warn("Too many iterations: " + i + ", when resolving: " + str);
/* 188 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 194 */       IExpressionResolver iexpressionresolver = new MacroExpressionResolver(this.mapMacroValues);
/* 195 */       ExpressionParser expressionparser = new ExpressionParser(iexpressionresolver);
/* 196 */       IExpression iexpression = expressionparser.parse(str);
/*     */       
/* 198 */       if (iexpression.getExpressionType() == ExpressionType.BOOL) {
/*     */         
/* 200 */         IExpressionBool iexpressionbool = (IExpressionBool)iexpression;
/* 201 */         boolean flag1 = iexpressionbool.eval();
/* 202 */         return flag1;
/*     */       } 
/* 204 */       if (iexpression.getExpressionType() == ExpressionType.FLOAT) {
/*     */         
/* 206 */         IExpressionFloat iexpressionfloat = (IExpressionFloat)iexpression;
/* 207 */         float f = iexpressionfloat.eval();
/* 208 */         boolean flag2 = (f != 0.0F);
/* 209 */         return flag2;
/*     */       } 
/*     */ 
/*     */       
/* 213 */       throw new ParseException("Not a boolean or float expression: " + iexpression.getExpressionType());
/*     */     
/*     */     }
/* 216 */     catch (ParseException parseexception) {
/*     */       
/* 218 */       Config.warn("Invalid macro expression: " + str);
/* 219 */       Config.warn("Error: " + parseexception.getMessage());
/* 220 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\config\MacroState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */