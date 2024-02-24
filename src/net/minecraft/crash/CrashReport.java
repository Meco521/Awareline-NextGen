/*     */ package net.minecraft.crash;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.RuntimeMXBean;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.world.gen.layer.IntCache;
/*     */ import net.optifine.CrashReporter;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class CrashReport {
/*  23 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   private final String description;
/*     */ 
/*     */   
/*     */   private final Throwable cause;
/*     */ 
/*     */   
/*  32 */   private final CrashReportCategory theReportCategory = new CrashReportCategory(this, "System Details");
/*  33 */   private final List<CrashReportCategory> crashReportSections = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   private File crashReportFile;
/*     */   
/*     */   private boolean firstCategoryInCrashReport = true;
/*     */   
/*  40 */   private StackTraceElement[] stacktrace = new StackTraceElement[0];
/*     */   
/*     */   private boolean reported = false;
/*     */   
/*     */   public CrashReport(String descriptionIn, Throwable causeThrowable) {
/*  45 */     this.description = descriptionIn;
/*  46 */     this.cause = causeThrowable;
/*  47 */     populateEnvironment();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateEnvironment() {
/*  56 */     this.theReportCategory.addCrashSectionCallable("Minecraft Version", new Callable<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/*  60 */             return "1.8.9";
/*     */           }
/*     */         });
/*  63 */     this.theReportCategory.addCrashSectionCallable("Operating System", new Callable<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/*  67 */             return System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
/*     */           }
/*     */         });
/*  70 */     this.theReportCategory.addCrashSectionCallable("Java Version", new Callable<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/*  74 */             return System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
/*     */           }
/*     */         });
/*  77 */     this.theReportCategory.addCrashSectionCallable("Java VM Version", new Callable<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/*  81 */             return System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
/*     */           }
/*     */         });
/*  84 */     this.theReportCategory.addCrashSectionCallable("Memory", new Callable<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/*  88 */             Runtime runtime = Runtime.getRuntime();
/*  89 */             long i = runtime.maxMemory();
/*  90 */             long j = runtime.totalMemory();
/*  91 */             long k = runtime.freeMemory();
/*  92 */             long l = i / 1024L / 1024L;
/*  93 */             long i1 = j / 1024L / 1024L;
/*  94 */             long j1 = k / 1024L / 1024L;
/*  95 */             return k + " bytes (" + j1 + " MB) / " + j + " bytes (" + i1 + " MB) up to " + i + " bytes (" + l + " MB)";
/*     */           }
/*     */         });
/*  98 */     this.theReportCategory.addCrashSectionCallable("JVM Flags", new Callable<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/* 102 */             RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
/* 103 */             List<String> list = runtimemxbean.getInputArguments();
/* 104 */             int i = 0;
/* 105 */             StringBuilder stringbuilder = new StringBuilder();
/*     */             
/* 107 */             for (String s : list) {
/*     */               
/* 109 */               if (s.startsWith("-X")) {
/*     */                 
/* 111 */                 if (i++ > 0)
/*     */                 {
/* 113 */                   stringbuilder.append(" ");
/*     */                 }
/*     */                 
/* 116 */                 stringbuilder.append(s);
/*     */               } 
/*     */             } 
/*     */             
/* 120 */             return String.format("%d total; %s", new Object[] { Integer.valueOf(i), stringbuilder.toString() });
/*     */           }
/*     */         });
/* 123 */     this.theReportCategory.addCrashSectionCallable("IntCache", new Callable<String>()
/*     */         {
/*     */           public String call() {
/* 126 */             return IntCache.getCacheSizes();
/*     */           }
/*     */         });
/*     */     
/* 130 */     if (Reflector.FMLCommonHandler_enhanceCrashReport.exists()) {
/*     */       
/* 132 */       Object object = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/* 133 */       Reflector.callString(object, Reflector.FMLCommonHandler_enhanceCrashReport, new Object[] { this, this.theReportCategory });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 142 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getCrashCause() {
/* 150 */     return this.cause;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSectionsInStringBuilder(StringBuilder builder) {
/* 158 */     if ((this.stacktrace == null || this.stacktrace.length <= 0) && !this.crashReportSections.isEmpty())
/*     */     {
/* 160 */       this.stacktrace = (StackTraceElement[])ArrayUtils.subarray((Object[])((CrashReportCategory)this.crashReportSections.get(0)).getStackTrace(), 0, 1);
/*     */     }
/*     */     
/* 163 */     if (this.stacktrace != null && this.stacktrace.length > 0) {
/*     */       
/* 165 */       builder.append("-- Head --\n");
/* 166 */       builder.append("Stacktrace:\n");
/*     */       
/* 168 */       for (StackTraceElement stacktraceelement : this.stacktrace) {
/*     */         
/* 170 */         builder.append("\t").append("at ").append(stacktraceelement.toString());
/* 171 */         builder.append("\n");
/*     */       } 
/*     */       
/* 174 */       builder.append("\n");
/*     */     } 
/*     */     
/* 177 */     for (CrashReportCategory crashreportcategory : this.crashReportSections) {
/*     */       
/* 179 */       crashreportcategory.appendToStringBuilder(builder);
/* 180 */       builder.append("\n\n");
/*     */     } 
/*     */     
/* 183 */     this.theReportCategory.appendToStringBuilder(builder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCauseStackTraceOrString() {
/* 191 */     StringWriter stringwriter = null;
/* 192 */     PrintWriter printwriter = null;
/* 193 */     Throwable throwable = this.cause;
/*     */     
/* 195 */     if (throwable.getMessage() == null) {
/*     */       
/* 197 */       if (throwable instanceof NullPointerException) {
/*     */         
/* 199 */         throwable = new NullPointerException(this.description);
/*     */       }
/* 201 */       else if (throwable instanceof StackOverflowError) {
/*     */         
/* 203 */         throwable = new StackOverflowError(this.description);
/*     */       }
/* 205 */       else if (throwable instanceof OutOfMemoryError) {
/*     */         
/* 207 */         throwable = new OutOfMemoryError(this.description);
/*     */       } 
/*     */       
/* 210 */       throwable.setStackTrace(this.cause.getStackTrace());
/*     */     } 
/*     */     
/* 213 */     String s = throwable.toString();
/*     */ 
/*     */     
/*     */     try {
/* 217 */       stringwriter = new StringWriter();
/* 218 */       printwriter = new PrintWriter(stringwriter);
/* 219 */       throwable.printStackTrace(printwriter);
/* 220 */       s = stringwriter.toString();
/*     */     }
/*     */     finally {
/*     */       
/* 224 */       IOUtils.closeQuietly(stringwriter);
/* 225 */       IOUtils.closeQuietly(printwriter);
/*     */     } 
/*     */     
/* 228 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCompleteReport() {
/* 236 */     if (!this.reported) {
/*     */       
/* 238 */       this.reported = true;
/* 239 */       CrashReporter.onCrashReport(this, this.theReportCategory);
/*     */     } 
/*     */     
/* 242 */     StringBuilder stringbuilder = new StringBuilder();
/* 243 */     stringbuilder.append("---- Minecraft Crash Report ----\n");
/* 244 */     Reflector.call(Reflector.BlamingTransformer_onCrash, new Object[] { stringbuilder });
/* 245 */     Reflector.call(Reflector.CoreModManager_onCrash, new Object[] { stringbuilder });
/* 246 */     stringbuilder.append("// ");
/* 247 */     stringbuilder.append(getWittyComment());
/* 248 */     stringbuilder.append("\n\n");
/* 249 */     stringbuilder.append("Time: ");
/* 250 */     stringbuilder.append((new SimpleDateFormat()).format(new Date()));
/* 251 */     stringbuilder.append("\n");
/* 252 */     stringbuilder.append("Description: ");
/* 253 */     stringbuilder.append(this.description);
/* 254 */     stringbuilder.append("\n\n");
/* 255 */     stringbuilder.append(getCauseStackTraceOrString());
/* 256 */     stringbuilder.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
/*     */     
/* 258 */     for (int i = 0; i < 87; i++)
/*     */     {
/* 260 */       stringbuilder.append("-");
/*     */     }
/*     */     
/* 263 */     stringbuilder.append("\n\n");
/* 264 */     getSectionsInStringBuilder(stringbuilder);
/* 265 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getFile() {
/* 273 */     return this.crashReportFile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean saveToFile(File toFile) {
/* 281 */     if (this.crashReportFile != null)
/*     */     {
/* 283 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 287 */     if (toFile.getParentFile() != null)
/*     */     {
/* 289 */       toFile.getParentFile().mkdirs();
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 294 */       FileWriter filewriter = new FileWriter(toFile);
/* 295 */       filewriter.write(getCompleteReport());
/* 296 */       filewriter.close();
/* 297 */       this.crashReportFile = toFile;
/* 298 */       return true;
/*     */     }
/* 300 */     catch (Throwable throwable) {
/*     */       
/* 302 */       logger.error("Could not save crash report to " + toFile, throwable);
/* 303 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReportCategory getCategory() {
/* 310 */     return this.theReportCategory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReportCategory makeCategory(String name) {
/* 318 */     return makeCategoryDepth(name, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReportCategory makeCategoryDepth(String categoryName, int stacktraceLength) {
/* 326 */     CrashReportCategory crashreportcategory = new CrashReportCategory(this, categoryName);
/*     */     
/* 328 */     if (this.firstCategoryInCrashReport) {
/*     */       
/* 330 */       int i = crashreportcategory.getPrunedStackTrace(stacktraceLength);
/* 331 */       StackTraceElement[] astacktraceelement = this.cause.getStackTrace();
/* 332 */       StackTraceElement stacktraceelement = null;
/* 333 */       StackTraceElement stacktraceelement1 = null;
/* 334 */       int j = astacktraceelement.length - i;
/*     */       
/* 336 */       if (j < 0)
/*     */       {
/* 338 */         System.out.println("Negative index in crash report handler (" + astacktraceelement.length + "/" + i + ")");
/*     */       }
/*     */       
/* 341 */       if (astacktraceelement != null && 0 <= j && j < astacktraceelement.length) {
/*     */         
/* 343 */         stacktraceelement = astacktraceelement[j];
/*     */         
/* 345 */         if (astacktraceelement.length + 1 - i < astacktraceelement.length)
/*     */         {
/* 347 */           stacktraceelement1 = astacktraceelement[astacktraceelement.length + 1 - i];
/*     */         }
/*     */       } 
/*     */       
/* 351 */       this.firstCategoryInCrashReport = crashreportcategory.firstTwoElementsOfStackTraceMatch(stacktraceelement, stacktraceelement1);
/*     */       
/* 353 */       if (i > 0 && !this.crashReportSections.isEmpty()) {
/*     */         
/* 355 */         CrashReportCategory crashreportcategory1 = this.crashReportSections.get(this.crashReportSections.size() - 1);
/* 356 */         crashreportcategory1.trimStackTraceEntriesFromBottom(i);
/*     */       }
/* 358 */       else if (astacktraceelement != null && astacktraceelement.length >= i && 0 <= j && j < astacktraceelement.length) {
/*     */         
/* 360 */         this.stacktrace = new StackTraceElement[j];
/* 361 */         System.arraycopy(astacktraceelement, 0, this.stacktrace, 0, this.stacktrace.length);
/*     */       }
/*     */       else {
/*     */         
/* 365 */         this.firstCategoryInCrashReport = false;
/*     */       } 
/*     */     } 
/*     */     
/* 369 */     this.crashReportSections.add(crashreportcategory);
/* 370 */     return crashreportcategory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getWittyComment() {
/* 378 */     String[] astring = { "Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!", "But it works on my machine." };
/*     */ 
/*     */     
/*     */     try {
/* 382 */       return astring[(int)(System.nanoTime() % astring.length)];
/*     */     }
/* 384 */     catch (Throwable var2) {
/*     */       
/* 386 */       return "Witty comment unavailable :(";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CrashReport makeCrashReport(Throwable causeIn, String descriptionIn) {
/*     */     CrashReport crashreport;
/* 397 */     if (causeIn instanceof ReportedException) {
/*     */       
/* 399 */       crashreport = ((ReportedException)causeIn).getCrashReport();
/*     */     }
/*     */     else {
/*     */       
/* 403 */       crashreport = new CrashReport(descriptionIn, causeIn);
/*     */     } 
/*     */     
/* 406 */     return crashreport;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\crash\CrashReport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */