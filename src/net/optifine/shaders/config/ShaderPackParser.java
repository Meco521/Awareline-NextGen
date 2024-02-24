/*      */ package net.optifine.shaders.config;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.CharArrayWriter;
/*      */ import java.io.IOException;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import net.minecraft.src.Config;
/*      */ import net.optifine.expr.IExpression;
/*      */ import net.optifine.expr.IExpressionBool;
/*      */ import net.optifine.expr.ParseException;
/*      */ import net.optifine.shaders.IShaderPack;
/*      */ import net.optifine.shaders.Program;
/*      */ import net.optifine.shaders.SMCLog;
/*      */ import net.optifine.shaders.uniform.CustomUniform;
/*      */ import net.optifine.shaders.uniform.UniformType;
/*      */ 
/*      */ public class ShaderPackParser {
/*   22 */   private static final Pattern PATTERN_VERSION = Pattern.compile("^\\s*#version\\s+.*$");
/*   23 */   private static final Pattern PATTERN_INCLUDE = Pattern.compile("^\\s*#include\\s+\"([A-Za-z\\d_/.]+)\".*$");
/*   24 */   private static final Set<String> setConstNames = makeSetConstNames();
/*   25 */   private static final Map<String, Integer> mapAlphaFuncs = makeMapAlphaFuncs();
/*   26 */   private static final Map<String, Integer> mapBlendFactors = makeMapBlendFactors();
/*      */ 
/*      */   
/*      */   public static ShaderOption[] parseShaderPackOptions(IShaderPack shaderPack, String[] programNames, List<Integer> listDimensions) {
/*   30 */     if (shaderPack == null)
/*      */     {
/*   32 */       return new ShaderOption[0];
/*      */     }
/*      */ 
/*      */     
/*   36 */     Map<String, ShaderOption> map = new HashMap<>();
/*   37 */     collectShaderOptions(shaderPack, "/shaders", programNames, map);
/*   38 */     Iterator<Integer> iterator = listDimensions.iterator();
/*      */     
/*   40 */     while (iterator.hasNext()) {
/*      */       
/*   42 */       int i = ((Integer)iterator.next()).intValue();
/*   43 */       String s = "/shaders/world" + i;
/*   44 */       collectShaderOptions(shaderPack, s, programNames, map);
/*      */     } 
/*      */     
/*   47 */     Collection<ShaderOption> collection = map.values();
/*   48 */     ShaderOption[] ashaderoption = collection.<ShaderOption>toArray(new ShaderOption[0]);
/*   49 */     Comparator<ShaderOption> comparator = new Comparator<ShaderOption>()
/*      */       {
/*      */         public int compare(ShaderOption o1, ShaderOption o2)
/*      */         {
/*   53 */           return o1.getName().compareToIgnoreCase(o2.getName());
/*      */         }
/*      */       };
/*   56 */     Arrays.sort(ashaderoption, comparator);
/*   57 */     return ashaderoption;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void collectShaderOptions(IShaderPack shaderPack, String dir, String[] programNames, Map<String, ShaderOption> mapOptions) {
/*   63 */     for (int i = 0; i < programNames.length; i++) {
/*      */       
/*   65 */       String s = programNames[i];
/*      */       
/*   67 */       if (!s.isEmpty()) {
/*      */         
/*   69 */         String s1 = dir + "/" + s + ".vsh";
/*   70 */         String s2 = dir + "/" + s + ".fsh";
/*   71 */         collectShaderOptions(shaderPack, s1, mapOptions);
/*   72 */         collectShaderOptions(shaderPack, s2, mapOptions);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void collectShaderOptions(IShaderPack sp, String path, Map<String, ShaderOption> mapOptions) {
/*   79 */     String[] astring = getLines(sp, path);
/*      */     
/*   81 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*   83 */       String s = astring[i];
/*   84 */       ShaderOption shaderoption = getShaderOption(s, path);
/*      */       
/*   86 */       if (shaderoption != null && !shaderoption.getName().startsWith(ShaderMacros.getPrefixMacro()) && (!shaderoption.checkUsed() || isOptionUsed(shaderoption, astring))) {
/*      */         
/*   88 */         String s1 = shaderoption.getName();
/*   89 */         ShaderOption shaderoption1 = mapOptions.get(s1);
/*      */         
/*   91 */         if (shaderoption1 != null) {
/*      */           
/*   93 */           if (!Config.equals(shaderoption1.getValueDefault(), shaderoption.getValueDefault())) {
/*      */             
/*   95 */             Config.warn("Ambiguous shader option: " + shaderoption.getName());
/*   96 */             Config.warn(" - in " + Config.arrayToString((Object[])shaderoption1.getPaths()) + ": " + shaderoption1.getValueDefault());
/*   97 */             Config.warn(" - in " + Config.arrayToString((Object[])shaderoption.getPaths()) + ": " + shaderoption.getValueDefault());
/*   98 */             shaderoption1.setEnabled(false);
/*      */           } 
/*      */           
/*  101 */           if (shaderoption1.getDescription() == null || shaderoption1.getDescription().length() <= 0)
/*      */           {
/*  103 */             shaderoption1.setDescription(shaderoption.getDescription());
/*      */           }
/*      */           
/*  106 */           shaderoption1.addPaths(shaderoption.getPaths());
/*      */         }
/*      */         else {
/*      */           
/*  110 */           mapOptions.put(s1, shaderoption);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isOptionUsed(ShaderOption so, String[] lines) {
/*  118 */     for (int i = 0; i < lines.length; i++) {
/*      */       
/*  120 */       String s = lines[i];
/*      */       
/*  122 */       if (so.isUsedInLine(s))
/*      */       {
/*  124 */         return true;
/*      */       }
/*      */     } 
/*      */     
/*  128 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] getLines(IShaderPack sp, String path) {
/*      */     try {
/*  135 */       List<String> list = new ArrayList<>();
/*  136 */       String s = loadFile(path, sp, 0, list, 0);
/*      */       
/*  138 */       if (s == null)
/*      */       {
/*  140 */         return new String[0];
/*      */       }
/*      */ 
/*      */       
/*  144 */       ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(s.getBytes());
/*  145 */       String[] astring = Config.readLines(bytearrayinputstream);
/*  146 */       return astring;
/*      */     
/*      */     }
/*  149 */     catch (IOException ioexception) {
/*      */       
/*  151 */       Config.dbg(ioexception.getClass().getName() + ": " + ioexception.getMessage());
/*  152 */       return new String[0];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static ShaderOption getShaderOption(String line, String path) {
/*  158 */     ShaderOption shaderoption = null;
/*      */     
/*  160 */     if (shaderoption == null)
/*      */     {
/*  162 */       shaderoption = ShaderOptionSwitch.parseOption(line, path);
/*      */     }
/*      */     
/*  165 */     if (shaderoption == null)
/*      */     {
/*  167 */       shaderoption = ShaderOptionVariable.parseOption(line, path);
/*      */     }
/*      */     
/*  170 */     if (shaderoption != null)
/*      */     {
/*  172 */       return shaderoption;
/*      */     }
/*      */ 
/*      */     
/*  176 */     if (shaderoption == null)
/*      */     {
/*  178 */       shaderoption = ShaderOptionSwitchConst.parseOption(line, path);
/*      */     }
/*      */     
/*  181 */     if (shaderoption == null)
/*      */     {
/*  183 */       shaderoption = ShaderOptionVariableConst.parseOption(line, path);
/*      */     }
/*      */     
/*  186 */     return (shaderoption != null && setConstNames.contains(shaderoption.getName())) ? shaderoption : null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Set<String> makeSetConstNames() {
/*  192 */     Set<String> set = new HashSet<>();
/*  193 */     set.add("shadowMapResolution");
/*  194 */     set.add("shadowMapFov");
/*  195 */     set.add("shadowDistance");
/*  196 */     set.add("shadowDistanceRenderMul");
/*  197 */     set.add("shadowIntervalSize");
/*  198 */     set.add("generateShadowMipmap");
/*  199 */     set.add("generateShadowColorMipmap");
/*  200 */     set.add("shadowHardwareFiltering");
/*  201 */     set.add("shadowHardwareFiltering0");
/*  202 */     set.add("shadowHardwareFiltering1");
/*  203 */     set.add("shadowtex0Mipmap");
/*  204 */     set.add("shadowtexMipmap");
/*  205 */     set.add("shadowtex1Mipmap");
/*  206 */     set.add("shadowcolor0Mipmap");
/*  207 */     set.add("shadowColor0Mipmap");
/*  208 */     set.add("shadowcolor1Mipmap");
/*  209 */     set.add("shadowColor1Mipmap");
/*  210 */     set.add("shadowtex0Nearest");
/*  211 */     set.add("shadowtexNearest");
/*  212 */     set.add("shadow0MinMagNearest");
/*  213 */     set.add("shadowtex1Nearest");
/*  214 */     set.add("shadow1MinMagNearest");
/*  215 */     set.add("shadowcolor0Nearest");
/*  216 */     set.add("shadowColor0Nearest");
/*  217 */     set.add("shadowColor0MinMagNearest");
/*  218 */     set.add("shadowcolor1Nearest");
/*  219 */     set.add("shadowColor1Nearest");
/*  220 */     set.add("shadowColor1MinMagNearest");
/*  221 */     set.add("wetnessHalflife");
/*  222 */     set.add("drynessHalflife");
/*  223 */     set.add("eyeBrightnessHalflife");
/*  224 */     set.add("centerDepthHalflife");
/*  225 */     set.add("sunPathRotation");
/*  226 */     set.add("ambientOcclusionLevel");
/*  227 */     set.add("superSamplingLevel");
/*  228 */     set.add("noiseTextureResolution");
/*  229 */     return set;
/*      */   }
/*      */ 
/*      */   
/*      */   public static ShaderProfile[] parseProfiles(Properties props, ShaderOption[] shaderOptions) {
/*  234 */     String s = "profile.";
/*  235 */     List<ShaderProfile> list = new ArrayList<>();
/*      */     
/*  237 */     for (Object e : props.keySet()) {
/*      */       
/*  239 */       String s1 = (String)e;
/*  240 */       if (s1.startsWith(s)) {
/*      */         
/*  242 */         String s2 = s1.substring(s.length());
/*  243 */         props.getProperty(s1);
/*  244 */         Set<String> set = new HashSet<>();
/*  245 */         ShaderProfile shaderprofile = parseProfile(s2, props, set, shaderOptions);
/*      */         
/*  247 */         if (shaderprofile != null)
/*      */         {
/*  249 */           list.add(shaderprofile);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  254 */     if (list.size() <= 0)
/*      */     {
/*  256 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  260 */     ShaderProfile[] ashaderprofile = list.<ShaderProfile>toArray(new ShaderProfile[0]);
/*  261 */     return ashaderprofile;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map<String, IExpressionBool> parseProgramConditions(Properties props, ShaderOption[] shaderOptions) {
/*  267 */     String s = "program.";
/*  268 */     Pattern pattern = Pattern.compile("program\\.([^.]+)\\.enabled");
/*  269 */     Map<String, IExpressionBool> map = new HashMap<>();
/*      */     
/*  271 */     for (Object e : props.keySet()) {
/*      */       
/*  273 */       String s1 = (String)e;
/*  274 */       Matcher matcher = pattern.matcher(s1);
/*      */       
/*  276 */       if (matcher.matches()) {
/*      */         
/*  278 */         String s2 = matcher.group(1);
/*  279 */         String s3 = props.getProperty(s1).trim();
/*  280 */         IExpressionBool iexpressionbool = parseOptionExpression(s3, shaderOptions);
/*      */         
/*  282 */         if (iexpressionbool == null) {
/*      */           
/*  284 */           SMCLog.severe("Error parsing program condition: " + s1);
/*      */           
/*      */           continue;
/*      */         } 
/*  288 */         map.put(s2, iexpressionbool);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  293 */     return map;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static IExpressionBool parseOptionExpression(String val, ShaderOption[] shaderOptions) {
/*      */     try {
/*  300 */       ShaderOptionResolver shaderoptionresolver = new ShaderOptionResolver(shaderOptions);
/*  301 */       ExpressionParser expressionparser = new ExpressionParser(shaderoptionresolver);
/*  302 */       IExpressionBool iexpressionbool = expressionparser.parseBool(val);
/*  303 */       return iexpressionbool;
/*      */     }
/*  305 */     catch (ParseException parseexception) {
/*      */       
/*  307 */       SMCLog.warning(parseexception.getClass().getName() + ": " + parseexception.getMessage());
/*  308 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static Set<String> parseOptionSliders(Properties props, ShaderOption[] shaderOptions) {
/*  314 */     Set<String> set = new HashSet<>();
/*  315 */     String s = props.getProperty("sliders");
/*      */     
/*  317 */     if (s == null)
/*      */     {
/*  319 */       return set;
/*      */     }
/*      */ 
/*      */     
/*  323 */     String[] astring = Config.tokenize(s, " ");
/*      */     
/*  325 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  327 */       String s1 = astring[i];
/*  328 */       ShaderOption shaderoption = ShaderUtils.getShaderOption(s1, shaderOptions);
/*      */       
/*  330 */       if (shaderoption == null) {
/*      */         
/*  332 */         Config.warn("Invalid shader option: " + s1);
/*      */       }
/*      */       else {
/*      */         
/*  336 */         set.add(s1);
/*      */       } 
/*      */     } 
/*      */     
/*  340 */     return set;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ShaderProfile parseProfile(String name, Properties props, Set<String> parsedProfiles, ShaderOption[] shaderOptions) {
/*  346 */     String s = "profile.";
/*  347 */     String s1 = s + name;
/*      */     
/*  349 */     if (parsedProfiles.contains(s1)) {
/*      */       
/*  351 */       Config.warn("[Shaders] Profile already parsed: " + name);
/*  352 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  356 */     parsedProfiles.add(name);
/*  357 */     ShaderProfile shaderprofile = new ShaderProfile(name);
/*  358 */     String s2 = props.getProperty(s1);
/*  359 */     String[] astring = Config.tokenize(s2, " ");
/*      */     
/*  361 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  363 */       String s3 = astring[i];
/*      */       
/*  365 */       if (s3.startsWith(s)) {
/*      */         
/*  367 */         String s4 = s3.substring(s.length());
/*  368 */         ShaderProfile shaderprofile1 = parseProfile(s4, props, parsedProfiles, shaderOptions);
/*      */         
/*  370 */         if (shaderprofile != null)
/*      */         {
/*  372 */           shaderprofile.addOptionValues(shaderprofile1);
/*  373 */           shaderprofile.addDisabledPrograms(shaderprofile1.getDisabledPrograms());
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  378 */         String[] astring1 = Config.tokenize(s3, ":=");
/*      */         
/*  380 */         if (astring1.length == 1) {
/*      */           
/*  382 */           String s7 = astring1[0];
/*  383 */           boolean flag = true;
/*      */           
/*  385 */           if (!s7.isEmpty() && s7.charAt(0) == '!') {
/*      */             
/*  387 */             flag = false;
/*  388 */             s7 = s7.substring(1);
/*      */           } 
/*      */           
/*  391 */           String s5 = "program.";
/*      */           
/*  393 */           if (s7.startsWith(s5)) {
/*      */             
/*  395 */             String s6 = s7.substring(s5.length());
/*      */             
/*  397 */             if (!Shaders.isProgramPath(s6))
/*      */             {
/*  399 */               Config.warn("Invalid program: " + s6 + " in profile: " + shaderprofile.getName());
/*      */             }
/*  401 */             else if (flag)
/*      */             {
/*  403 */               shaderprofile.removeDisabledProgram(s6);
/*      */             }
/*      */             else
/*      */             {
/*  407 */               shaderprofile.addDisabledProgram(s6);
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/*  412 */             ShaderOption shaderoption1 = ShaderUtils.getShaderOption(s7, shaderOptions);
/*      */             
/*  414 */             if (!(shaderoption1 instanceof ShaderOptionSwitch))
/*      */             {
/*  416 */               Config.warn("[Shaders] Invalid option: " + s7);
/*      */             }
/*      */             else
/*      */             {
/*  420 */               shaderprofile.addOptionValue(s7, String.valueOf(flag));
/*  421 */               shaderoption1.setVisible(true);
/*      */             }
/*      */           
/*      */           } 
/*  425 */         } else if (astring1.length != 2) {
/*      */           
/*  427 */           Config.warn("[Shaders] Invalid option value: " + s3);
/*      */         }
/*      */         else {
/*      */           
/*  431 */           String s8 = astring1[0];
/*  432 */           String s9 = astring1[1];
/*  433 */           ShaderOption shaderoption = ShaderUtils.getShaderOption(s8, shaderOptions);
/*      */           
/*  435 */           if (shaderoption == null) {
/*      */             
/*  437 */             Config.warn("[Shaders] Invalid option: " + s3);
/*      */           }
/*  439 */           else if (!shaderoption.isValidValue(s9)) {
/*      */             
/*  441 */             Config.warn("[Shaders] Invalid value: " + s3);
/*      */           }
/*      */           else {
/*      */             
/*  445 */             shaderoption.setVisible(true);
/*  446 */             shaderprofile.addOptionValue(s8, s9);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  452 */     return shaderprofile;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map<String, ScreenShaderOptions> parseGuiScreens(Properties props, ShaderProfile[] shaderProfiles, ShaderOption[] shaderOptions) {
/*  458 */     Map<String, ScreenShaderOptions> map = new HashMap<>();
/*  459 */     parseGuiScreen("screen", props, map, shaderProfiles, shaderOptions);
/*  460 */     return map.isEmpty() ? null : map;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean parseGuiScreen(String key, Properties props, Map<String, ScreenShaderOptions> map, ShaderProfile[] shaderProfiles, ShaderOption[] shaderOptions) {
/*  465 */     String s = props.getProperty(key);
/*      */     
/*  467 */     if (s == null)
/*      */     {
/*  469 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  473 */     List<ShaderOption> list = new ArrayList<>();
/*  474 */     Set<String> set = new HashSet<>();
/*  475 */     String[] astring = Config.tokenize(s, " ");
/*      */     
/*  477 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  479 */       String s1 = astring[i];
/*      */       
/*  481 */       if (s1.equals("<empty>")) {
/*      */         
/*  483 */         list.add((ShaderOption)null);
/*      */       }
/*  485 */       else if (set.contains(s1)) {
/*      */         
/*  487 */         Config.warn("[Shaders] Duplicate option: " + s1 + ", key: " + key);
/*      */       }
/*      */       else {
/*      */         
/*  491 */         set.add(s1);
/*      */         
/*  493 */         if (s1.equals("<profile>")) {
/*      */           
/*  495 */           if (shaderProfiles == null)
/*      */           {
/*  497 */             Config.warn("[Shaders] Option profile can not be used, no profiles defined: " + s1 + ", key: " + key);
/*      */           }
/*      */           else
/*      */           {
/*  501 */             ShaderOptionProfile shaderoptionprofile = new ShaderOptionProfile(shaderProfiles, shaderOptions);
/*  502 */             list.add(shaderoptionprofile);
/*      */           }
/*      */         
/*  505 */         } else if (s1.equals("*")) {
/*      */           
/*  507 */           ShaderOption shaderoption1 = new ShaderOptionRest("<rest>");
/*  508 */           list.add(shaderoption1);
/*      */         }
/*  510 */         else if (!s1.isEmpty() && s1.charAt(0) == '[' && !s1.isEmpty() && s1.charAt(s1.length() - 1) == ']') {
/*      */           
/*  512 */           String s3 = StrUtils.removePrefixSuffix(s1, "[", "]");
/*      */           
/*  514 */           if (!s3.matches("^\\w+$"))
/*      */           {
/*  516 */             Config.warn("[Shaders] Invalid screen: " + s1 + ", key: " + key);
/*      */           }
/*  518 */           else if (!parseGuiScreen("screen." + s3, props, map, shaderProfiles, shaderOptions))
/*      */           {
/*  520 */             Config.warn("[Shaders] Invalid screen: " + s1 + ", key: " + key);
/*      */           }
/*      */           else
/*      */           {
/*  524 */             ShaderOptionScreen shaderoptionscreen = new ShaderOptionScreen(s3);
/*  525 */             list.add(shaderoptionscreen);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  530 */           ShaderOption shaderoption = ShaderUtils.getShaderOption(s1, shaderOptions);
/*      */           
/*  532 */           if (shaderoption == null) {
/*      */             
/*  534 */             Config.warn("[Shaders] Invalid option: " + s1 + ", key: " + key);
/*  535 */             list.add((ShaderOption)null);
/*      */           }
/*      */           else {
/*      */             
/*  539 */             shaderoption.setVisible(true);
/*  540 */             list.add(shaderoption);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  546 */     ShaderOption[] ashaderoption = list.<ShaderOption>toArray(new ShaderOption[0]);
/*  547 */     String s2 = props.getProperty(key + ".columns");
/*  548 */     int j = Config.parseInt(s2, 2);
/*  549 */     ScreenShaderOptions screenshaderoptions = new ScreenShaderOptions(key, ashaderoption, j);
/*  550 */     map.put(key, screenshaderoptions);
/*  551 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static BufferedReader resolveIncludes(BufferedReader reader, String filePath, IShaderPack shaderPack, int fileIndex, List<String> listFiles, int includeLevel) throws IOException {
/*  557 */     String s = "/";
/*  558 */     int i = filePath.lastIndexOf('/');
/*      */     
/*  560 */     if (i >= 0)
/*      */     {
/*  562 */       s = filePath.substring(0, i);
/*      */     }
/*      */     
/*  565 */     CharArrayWriter chararraywriter = new CharArrayWriter();
/*  566 */     int j = -1;
/*  567 */     Set<ShaderMacro> set = new LinkedHashSet<>();
/*  568 */     int k = 1;
/*      */ 
/*      */     
/*      */     while (true) {
/*  572 */       String s1 = reader.readLine();
/*      */       
/*  574 */       if (s1 == null) {
/*      */         
/*  576 */         char[] achar = chararraywriter.toCharArray();
/*      */         
/*  578 */         if (j >= 0 && !set.isEmpty()) {
/*      */           
/*  580 */           StringBuilder stringbuilder = new StringBuilder();
/*      */           
/*  582 */           for (ShaderMacro shadermacro : set) {
/*      */             
/*  584 */             stringbuilder.append("#define ");
/*  585 */             stringbuilder.append(shadermacro.getName());
/*  586 */             stringbuilder.append(" ");
/*  587 */             stringbuilder.append(shadermacro.getValue());
/*  588 */             stringbuilder.append("\n");
/*      */           } 
/*      */           
/*  591 */           String s7 = stringbuilder.toString();
/*  592 */           StringBuilder stringbuilder1 = new StringBuilder(new String(achar));
/*  593 */           stringbuilder1.insert(j, s7);
/*  594 */           String s9 = stringbuilder1.toString();
/*  595 */           achar = s9.toCharArray();
/*      */         } 
/*      */         
/*  598 */         CharArrayReader chararrayreader = new CharArrayReader(achar);
/*  599 */         return new BufferedReader(chararrayreader);
/*      */       } 
/*      */       
/*  602 */       if (j < 0) {
/*      */         
/*  604 */         Matcher matcher = PATTERN_VERSION.matcher(s1);
/*      */         
/*  606 */         if (matcher.matches()) {
/*      */           
/*  608 */           String s2 = ShaderMacros.getFixedMacroLines() + ShaderMacros.getOptionMacroLines();
/*  609 */           String s3 = s1 + "\n" + s2;
/*  610 */           String s4 = "#line " + (k + 1) + " " + fileIndex;
/*  611 */           s1 = s3 + s4;
/*  612 */           j = chararraywriter.size() + s3.length();
/*      */         } 
/*      */       } 
/*      */       
/*  616 */       Matcher matcher1 = PATTERN_INCLUDE.matcher(s1);
/*      */       
/*  618 */       if (matcher1.matches()) {
/*      */         
/*  620 */         String s6 = matcher1.group(1);
/*  621 */         boolean flag = (!s6.isEmpty() && s6.charAt(0) == '/');
/*  622 */         String s8 = flag ? ("/shaders" + s6) : (s + "/" + s6);
/*      */         
/*  624 */         if (!listFiles.contains(s8))
/*      */         {
/*  626 */           listFiles.add(s8);
/*      */         }
/*      */         
/*  629 */         int l = listFiles.indexOf(s8) + 1;
/*  630 */         s1 = loadFile(s8, shaderPack, l, listFiles, includeLevel);
/*      */         
/*  632 */         if (s1 == null)
/*      */         {
/*  634 */           throw new IOException("Included file not found: " + filePath);
/*      */         }
/*      */         
/*  637 */         if (!s1.isEmpty() && s1.charAt(s1.length() - 1) == '\n')
/*      */         {
/*  639 */           s1 = s1.substring(0, s1.length() - 1);
/*      */         }
/*      */         
/*  642 */         String s5 = "#line 1 " + l + "\n";
/*      */         
/*  644 */         if (s1.startsWith("#version "))
/*      */         {
/*  646 */           s5 = "";
/*      */         }
/*      */         
/*  649 */         s1 = s5 + s1 + "\n#line " + (k + 1) + " " + fileIndex;
/*      */       } 
/*      */       
/*  652 */       if (j >= 0 && s1.contains(ShaderMacros.getPrefixMacro())) {
/*      */         
/*  654 */         ShaderMacro[] ashadermacro = findMacros(s1, ShaderMacros.getExtensions());
/*      */         
/*  656 */         set.addAll(Arrays.asList(ashadermacro));
/*      */       } 
/*      */       
/*  659 */       chararraywriter.write(s1);
/*  660 */       chararraywriter.write("\n");
/*  661 */       k++;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static ShaderMacro[] findMacros(String line, ShaderMacro[] macros) {
/*  667 */     List<ShaderMacro> list = new ArrayList<>();
/*      */     
/*  669 */     for (int i = 0; i < macros.length; i++) {
/*      */       
/*  671 */       ShaderMacro shadermacro = macros[i];
/*      */       
/*  673 */       if (line.contains(shadermacro.getName()))
/*      */       {
/*  675 */         list.add(shadermacro);
/*      */       }
/*      */     } 
/*      */     
/*  679 */     ShaderMacro[] ashadermacro = list.<ShaderMacro>toArray(new ShaderMacro[0]);
/*  680 */     return ashadermacro;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String loadFile(String filePath, IShaderPack shaderPack, int fileIndex, List<String> listFiles, int includeLevel) throws IOException {
/*  685 */     if (includeLevel >= 10)
/*      */     {
/*  687 */       throw new IOException("#include depth exceeded: " + includeLevel + ", file: " + filePath);
/*      */     }
/*      */ 
/*      */     
/*  691 */     includeLevel++;
/*  692 */     InputStream inputstream = shaderPack.getResourceAsStream(filePath);
/*      */     
/*  694 */     if (inputstream == null)
/*      */     {
/*  696 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  700 */     InputStreamReader inputstreamreader = new InputStreamReader(inputstream, StandardCharsets.US_ASCII);
/*  701 */     BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
/*  702 */     bufferedreader = resolveIncludes(bufferedreader, filePath, shaderPack, fileIndex, listFiles, includeLevel);
/*  703 */     CharArrayWriter chararraywriter = new CharArrayWriter();
/*      */ 
/*      */     
/*      */     while (true) {
/*  707 */       String s = bufferedreader.readLine();
/*      */       
/*  709 */       if (s == null)
/*      */       {
/*  711 */         return chararraywriter.toString();
/*      */       }
/*      */       
/*  714 */       chararraywriter.write(s);
/*  715 */       chararraywriter.write("\n");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CustomUniforms parseCustomUniforms(Properties props) {
/*  723 */     String s = "uniform";
/*  724 */     String s1 = "variable";
/*  725 */     String s2 = s + ".";
/*  726 */     String s3 = s1 + ".";
/*  727 */     Map<String, IExpression> map = new HashMap<>();
/*  728 */     List<CustomUniform> list = new ArrayList<>();
/*      */     
/*  730 */     for (Object e : props.keySet()) {
/*      */       
/*  732 */       String s4 = (String)e;
/*  733 */       String[] astring = Config.tokenize(s4, ".");
/*      */       
/*  735 */       if (astring.length == 3) {
/*      */         
/*  737 */         String s5 = astring[0];
/*  738 */         String s6 = astring[1];
/*  739 */         String s7 = astring[2];
/*  740 */         String s8 = props.getProperty(s4).trim();
/*      */         
/*  742 */         if (map.containsKey(s7)) {
/*      */           
/*  744 */           SMCLog.warning("Expression already defined: " + s7); continue;
/*      */         } 
/*  746 */         if (s5.equals(s) || s5.equals(s1)) {
/*      */           
/*  748 */           SMCLog.info("Custom " + s5 + ": " + s7);
/*  749 */           CustomUniform customuniform = parseCustomUniform(s5, s7, s6, s8, map);
/*      */           
/*  751 */           if (customuniform != null) {
/*      */             
/*  753 */             map.put(s7, customuniform.getExpression());
/*      */             
/*  755 */             if (!s5.equals(s1))
/*      */             {
/*  757 */               list.add(customuniform);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  764 */     if (list.size() <= 0)
/*      */     {
/*  766 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  770 */     CustomUniform[] acustomuniform = list.<CustomUniform>toArray(new CustomUniform[0]);
/*  771 */     CustomUniforms customuniforms = new CustomUniforms(acustomuniform, map);
/*  772 */     return customuniforms;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static CustomUniform parseCustomUniform(String kind, String name, String type, String src, Map<String, IExpression> mapExpressions) {
/*      */     try {
/*  780 */       UniformType uniformtype = UniformType.parse(type);
/*      */       
/*  782 */       if (uniformtype == null) {
/*      */         
/*  784 */         SMCLog.warning("Unknown " + kind + " type: " + uniformtype);
/*  785 */         return null;
/*      */       } 
/*      */ 
/*      */       
/*  789 */       ShaderExpressionResolver shaderexpressionresolver = new ShaderExpressionResolver(mapExpressions);
/*  790 */       ExpressionParser expressionparser = new ExpressionParser((IExpressionResolver)shaderexpressionresolver);
/*  791 */       IExpression iexpression = expressionparser.parse(src);
/*  792 */       ExpressionType expressiontype = iexpression.getExpressionType();
/*      */       
/*  794 */       if (!uniformtype.matchesExpressionType(expressiontype)) {
/*      */         
/*  796 */         SMCLog.warning("Expression type does not match " + kind + " type, expression: " + expressiontype + ", " + kind + ": " + uniformtype + " " + name);
/*  797 */         return null;
/*      */       } 
/*      */ 
/*      */       
/*  801 */       iexpression = makeExpressionCached(iexpression);
/*  802 */       CustomUniform customuniform = new CustomUniform(name, uniformtype, iexpression);
/*  803 */       return customuniform;
/*      */ 
/*      */     
/*      */     }
/*  807 */     catch (ParseException parseexception) {
/*      */       
/*  809 */       SMCLog.warning(parseexception.getClass().getName() + ": " + parseexception.getMessage());
/*  810 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static IExpression makeExpressionCached(IExpression expr) {
/*  816 */     return (expr instanceof IExpressionFloat) ? (IExpression)new ExpressionFloatCached((IExpressionFloat)expr) : ((expr instanceof IExpressionFloatArray) ? (IExpression)new ExpressionFloatArrayCached((IExpressionFloatArray)expr) : expr);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void parseAlphaStates(Properties props) {
/*  821 */     for (Object e : props.keySet()) {
/*      */       
/*  823 */       String s = (String)e;
/*  824 */       String[] astring = Config.tokenize(s, ".");
/*      */       
/*  826 */       if (astring.length == 2) {
/*      */         
/*  828 */         String s1 = astring[0];
/*  829 */         String s2 = astring[1];
/*      */         
/*  831 */         if (s1.equals("alphaTest")) {
/*      */           
/*  833 */           Program program = Shaders.getProgram(s2);
/*      */           
/*  835 */           if (program == null) {
/*      */             
/*  837 */             SMCLog.severe("Invalid program name: " + s2);
/*      */             
/*      */             continue;
/*      */           } 
/*  841 */           String s3 = props.getProperty(s).trim();
/*  842 */           GlAlphaState glalphastate = parseAlphaState(s3);
/*      */           
/*  844 */           if (glalphastate != null)
/*      */           {
/*  846 */             program.setAlphaState(glalphastate);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static GlAlphaState parseAlphaState(String str) {
/*  856 */     String[] astring = Config.tokenize(str, " ");
/*      */     
/*  858 */     if (astring.length == 1) {
/*      */       
/*  860 */       String s = astring[0];
/*      */       
/*  862 */       if (s.equals("off") || s.equals("false"))
/*      */       {
/*  864 */         return new GlAlphaState(false);
/*      */       }
/*      */     }
/*  867 */     else if (astring.length == 2) {
/*      */       
/*  869 */       String s2 = astring[0];
/*  870 */       String s1 = astring[1];
/*  871 */       Integer integer = mapAlphaFuncs.get(s2);
/*  872 */       float f = Config.parseFloat(s1, -1.0F);
/*      */       
/*  874 */       if (integer != null && f >= 0.0F)
/*      */       {
/*  876 */         return new GlAlphaState(true, integer.intValue(), f);
/*      */       }
/*      */     } 
/*      */     
/*  880 */     SMCLog.severe("Invalid alpha test: " + str);
/*  881 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void parseBlendStates(Properties props) {
/*  886 */     for (Object e : props.keySet()) {
/*      */       
/*  888 */       String s = (String)e;
/*  889 */       String[] astring = Config.tokenize(s, ".");
/*      */       
/*  891 */       if (astring.length == 2) {
/*      */         
/*  893 */         String s1 = astring[0];
/*  894 */         String s2 = astring[1];
/*      */         
/*  896 */         if (s1.equals("blend")) {
/*      */           
/*  898 */           Program program = Shaders.getProgram(s2);
/*      */           
/*  900 */           if (program == null) {
/*      */             
/*  902 */             SMCLog.severe("Invalid program name: " + s2);
/*      */             
/*      */             continue;
/*      */           } 
/*  906 */           String s3 = props.getProperty(s).trim();
/*  907 */           GlBlendState glblendstate = parseBlendState(s3);
/*      */           
/*  909 */           if (glblendstate != null)
/*      */           {
/*  911 */             program.setBlendState(glblendstate);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static GlBlendState parseBlendState(String str) {
/*  921 */     String[] astring = Config.tokenize(str, " ");
/*      */     
/*  923 */     if (astring.length == 1) {
/*      */       
/*  925 */       String s = astring[0];
/*      */       
/*  927 */       if (s.equals("off") || s.equals("false"))
/*      */       {
/*  929 */         return new GlBlendState(false);
/*      */       }
/*      */     }
/*  932 */     else if (astring.length == 2 || astring.length == 4) {
/*      */       
/*  934 */       String s4 = astring[0];
/*  935 */       String s1 = astring[1];
/*  936 */       String s2 = s4;
/*  937 */       String s3 = s1;
/*      */       
/*  939 */       if (astring.length == 4) {
/*      */         
/*  941 */         s2 = astring[2];
/*  942 */         s3 = astring[3];
/*      */       } 
/*      */       
/*  945 */       Integer integer = mapBlendFactors.get(s4);
/*  946 */       Integer integer1 = mapBlendFactors.get(s1);
/*  947 */       Integer integer2 = mapBlendFactors.get(s2);
/*  948 */       Integer integer3 = mapBlendFactors.get(s3);
/*      */       
/*  950 */       if (integer != null && integer1 != null && integer2 != null && integer3 != null)
/*      */       {
/*  952 */         return new GlBlendState(true, integer.intValue(), integer1.intValue(), integer2.intValue(), integer3.intValue());
/*      */       }
/*      */     } 
/*      */     
/*  956 */     SMCLog.severe("Invalid blend mode: " + str);
/*  957 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void parseRenderScales(Properties props) {
/*  962 */     for (Object e : props.keySet()) {
/*      */       
/*  964 */       String s = (String)e;
/*  965 */       String[] astring = Config.tokenize(s, ".");
/*      */       
/*  967 */       if (astring.length == 2) {
/*      */         
/*  969 */         String s1 = astring[0];
/*  970 */         String s2 = astring[1];
/*      */         
/*  972 */         if (s1.equals("scale")) {
/*      */           
/*  974 */           Program program = Shaders.getProgram(s2);
/*      */           
/*  976 */           if (program == null) {
/*      */             
/*  978 */             SMCLog.severe("Invalid program name: " + s2);
/*      */             
/*      */             continue;
/*      */           } 
/*  982 */           String s3 = props.getProperty(s).trim();
/*  983 */           RenderScale renderscale = parseRenderScale(s3);
/*      */           
/*  985 */           if (renderscale != null)
/*      */           {
/*  987 */             program.setRenderScale(renderscale);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static RenderScale parseRenderScale(String str) {
/*  997 */     String[] astring = Config.tokenize(str, " ");
/*  998 */     float f = Config.parseFloat(astring[0], -1.0F);
/*  999 */     float f1 = 0.0F;
/* 1000 */     float f2 = 0.0F;
/*      */     
/* 1002 */     if (astring.length > 1) {
/*      */       
/* 1004 */       if (astring.length != 3) {
/*      */         
/* 1006 */         SMCLog.severe("Invalid render scale: " + str);
/* 1007 */         return null;
/*      */       } 
/*      */       
/* 1010 */       f1 = Config.parseFloat(astring[1], -1.0F);
/* 1011 */       f2 = Config.parseFloat(astring[2], -1.0F);
/*      */     } 
/*      */     
/* 1014 */     if (Config.between(f, 0.0F, 1.0F) && Config.between(f1, 0.0F, 1.0F) && Config.between(f2, 0.0F, 1.0F))
/*      */     {
/* 1016 */       return new RenderScale(f, f1, f2);
/*      */     }
/*      */ 
/*      */     
/* 1020 */     SMCLog.severe("Invalid render scale: " + str);
/* 1021 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parseBuffersFlip(Properties props) {
/* 1027 */     for (Object e : props.keySet()) {
/*      */       
/* 1029 */       String s = (String)e;
/* 1030 */       String[] astring = Config.tokenize(s, ".");
/*      */       
/* 1032 */       if (astring.length == 3) {
/*      */         
/* 1034 */         String s1 = astring[0];
/* 1035 */         String s2 = astring[1];
/* 1036 */         String s3 = astring[2];
/*      */         
/* 1038 */         if (s1.equals("flip")) {
/*      */           
/* 1040 */           Program program = Shaders.getProgram(s2);
/*      */           
/* 1042 */           if (program == null) {
/*      */             
/* 1044 */             SMCLog.severe("Invalid program name: " + s2);
/*      */             
/*      */             continue;
/*      */           } 
/* 1048 */           Boolean[] aboolean = program.getBuffersFlip();
/* 1049 */           int i = Shaders.getBufferIndexFromString(s3);
/*      */           
/* 1051 */           if (i >= 0 && i < aboolean.length) {
/*      */             
/* 1053 */             String s4 = props.getProperty(s).trim();
/* 1054 */             Boolean obool = Config.parseBoolean(s4, (Boolean)null);
/*      */             
/* 1056 */             if (obool == null) {
/*      */               
/* 1058 */               SMCLog.severe("Invalid boolean value: " + s4);
/*      */               
/*      */               continue;
/*      */             } 
/* 1062 */             aboolean[i] = obool;
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/* 1067 */           SMCLog.severe("Invalid buffer name: " + s3);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Map<String, Integer> makeMapAlphaFuncs() {
/* 1077 */     Map<String, Integer> map = new HashMap<>();
/* 1078 */     map.put("NEVER", new Integer(512));
/* 1079 */     map.put("LESS", new Integer(513));
/* 1080 */     map.put("EQUAL", new Integer(514));
/* 1081 */     map.put("LEQUAL", new Integer(515));
/* 1082 */     map.put("GREATER", new Integer(516));
/* 1083 */     map.put("NOTEQUAL", new Integer(517));
/* 1084 */     map.put("GEQUAL", new Integer(518));
/* 1085 */     map.put("ALWAYS", new Integer(519));
/* 1086 */     return Collections.unmodifiableMap(map);
/*      */   }
/*      */ 
/*      */   
/*      */   private static Map<String, Integer> makeMapBlendFactors() {
/* 1091 */     Map<String, Integer> map = new HashMap<>();
/* 1092 */     map.put("ZERO", new Integer(0));
/* 1093 */     map.put("ONE", new Integer(1));
/* 1094 */     map.put("SRC_COLOR", new Integer(768));
/* 1095 */     map.put("ONE_MINUS_SRC_COLOR", new Integer(769));
/* 1096 */     map.put("DST_COLOR", new Integer(774));
/* 1097 */     map.put("ONE_MINUS_DST_COLOR", new Integer(775));
/* 1098 */     map.put("SRC_ALPHA", new Integer(770));
/* 1099 */     map.put("ONE_MINUS_SRC_ALPHA", new Integer(771));
/* 1100 */     map.put("DST_ALPHA", new Integer(772));
/* 1101 */     map.put("ONE_MINUS_DST_ALPHA", new Integer(773));
/* 1102 */     map.put("CONSTANT_COLOR", new Integer(32769));
/* 1103 */     map.put("ONE_MINUS_CONSTANT_COLOR", new Integer(32770));
/* 1104 */     map.put("CONSTANT_ALPHA", new Integer(32771));
/* 1105 */     map.put("ONE_MINUS_CONSTANT_ALPHA", new Integer(32772));
/* 1106 */     map.put("SRC_ALPHA_SATURATE", new Integer(776));
/* 1107 */     return Collections.unmodifiableMap(map);
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\config\ShaderPackParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */