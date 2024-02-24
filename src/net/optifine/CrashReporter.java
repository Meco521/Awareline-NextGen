/*     */ package net.optifine;
/*     */ 
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.http.FileUploadThread;
/*     */ import net.optifine.http.IFileUploadListener;
/*     */ import net.optifine.shaders.Shaders;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CrashReporter
/*     */ {
/*     */   public static void onCrashReport(CrashReport crashReport, CrashReportCategory category) {
/*     */     try {
/*  21 */       Throwable throwable = crashReport.getCrashCause();
/*     */       
/*  23 */       if (throwable == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  28 */       if (throwable.getClass().getName().contains(".fml.client.SplashProgress")) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  33 */       if (throwable.getClass() == Throwable.class) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  38 */       extendCrashReport(category);
/*  39 */       GameSettings gamesettings = Config.getGameSettings();
/*     */       
/*  41 */       if (gamesettings == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  46 */       if (!gamesettings.snooperEnabled) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  51 */       String s = "http://optifine.net/crashReport";
/*  52 */       String s1 = makeReport(crashReport);
/*  53 */       byte[] abyte = s1.getBytes(StandardCharsets.US_ASCII);
/*  54 */       IFileUploadListener ifileuploadlistener = new IFileUploadListener()
/*     */         {
/*     */           public void fileUploadFinished(String url, byte[] content, Throwable exception) {}
/*     */         };
/*     */ 
/*     */       
/*  60 */       Map<Object, Object> map = new HashMap<>();
/*  61 */       map.put("OF-Version", Config.getVersion());
/*  62 */       map.put("OF-Summary", makeSummary(crashReport));
/*  63 */       FileUploadThread fileuploadthread = new FileUploadThread(s, map, abyte, ifileuploadlistener);
/*  64 */       fileuploadthread.setPriority(10);
/*  65 */       fileuploadthread.start();
/*  66 */       Thread.sleep(1000L);
/*     */     }
/*  68 */     catch (Exception exception) {
/*     */       
/*  70 */       Config.dbg(exception.getClass().getName() + ": " + exception.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String makeReport(CrashReport crashReport) {
/*  76 */     return "OptiFineVersion: " + Config.getVersion() + "\nSummary: " + 
/*  77 */       makeSummary(crashReport) + "\n\n" + crashReport
/*     */       
/*  79 */       .getCompleteReport() + "\n";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String makeSummary(CrashReport crashReport) {
/*  85 */     Throwable throwable = crashReport.getCrashCause();
/*     */     
/*  87 */     if (throwable == null)
/*     */     {
/*  89 */       return "Unknown";
/*     */     }
/*     */ 
/*     */     
/*  93 */     StackTraceElement[] astacktraceelement = throwable.getStackTrace();
/*  94 */     String s = "unknown";
/*     */     
/*  96 */     if (astacktraceelement.length > 0)
/*     */     {
/*  98 */       s = astacktraceelement[0].toString().trim();
/*     */     }
/*     */     
/* 101 */     String s1 = throwable.getClass().getName() + ": " + throwable.getMessage() + " (" + crashReport.getDescription() + ") [" + s + "]";
/* 102 */     return s1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void extendCrashReport(CrashReportCategory cat) {
/* 108 */     cat.addCrashSection("OptiFine Version", Config.getVersion());
/* 109 */     cat.addCrashSection("OptiFine Build", Config.getBuild());
/*     */     
/* 111 */     if (Config.getGameSettings() != null) {
/*     */       
/* 113 */       cat.addCrashSection("Render Distance Chunks", String.valueOf(Config.getChunkViewDistance()));
/* 114 */       cat.addCrashSection("Mipmaps", String.valueOf(Config.getMipmapLevels()));
/* 115 */       cat.addCrashSection("Anisotropic Filtering", String.valueOf(Config.getAnisotropicFilterLevel()));
/* 116 */       cat.addCrashSection("Antialiasing", String.valueOf(Config.getAntialiasingLevel()));
/* 117 */       cat.addCrashSection("Multitexture", String.valueOf(Config.isMultiTexture()));
/*     */     } 
/*     */     
/* 120 */     cat.addCrashSection("Shaders", Shaders.getShaderPackName());
/* 121 */     cat.addCrashSection("OpenGlVersion", Config.openGlVersion);
/* 122 */     cat.addCrashSection("OpenGlRenderer", Config.openGlRenderer);
/* 123 */     cat.addCrashSection("OpenGlVendor", Config.openGlVendor);
/* 124 */     cat.addCrashSection("CpuCount", String.valueOf(Config.getAvailableProcessors()));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\CrashReporter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */