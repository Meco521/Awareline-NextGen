/*     */ package net.optifine;
/*     */ import java.nio.IntBuffer;
/*     */ import net.minecraft.src.Config;
/*     */ import org.lwjgl.opengl.ARBDebugOutput;
/*     */ import org.lwjgl.opengl.ARBDebugOutputCallback;
/*     */ import org.lwjgl.opengl.ContextAttribs;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ import org.lwjgl.opengl.PixelFormat;
/*     */ 
/*     */ public class GlDebugHandler implements ARBDebugOutputCallback.Handler {
/*     */   public static void createDisplayDebug() throws LWJGLException {
/*  14 */     boolean flag = (GLContext.getCapabilities()).GL_ARB_debug_output;
/*  15 */     ContextAttribs contextattribs = (new ContextAttribs()).withDebug(true);
/*  16 */     Display.create((new PixelFormat()).withDepthBits(24), contextattribs);
/*  17 */     ARBDebugOutput.glDebugMessageCallbackARB(new ARBDebugOutputCallback(new GlDebugHandler()));
/*  18 */     ARBDebugOutput.glDebugMessageControlARB(4352, 4352, 4352, (IntBuffer)null, true);
/*  19 */     GL11.glEnable(33346);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleMessage(int source, int type, int id, int severity, String message) {
/*  24 */     if (!message.contains("glBindFramebuffer"))
/*     */     {
/*  26 */       if (!message.contains("Wide lines"))
/*     */       {
/*  28 */         if (!message.contains("shader recompiled")) {
/*     */           
/*  30 */           Config.dbg("[LWJGL] source: " + getSource(source) + ", type: " + getType(type) + ", id: " + id + ", severity: " + getSeverity(severity) + ", message: " + message);
/*  31 */           (new Throwable("StackTrace")).printStackTrace();
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSource(int source) {
/*  39 */     switch (source) {
/*     */       
/*     */       case 33350:
/*  42 */         return "API";
/*     */       
/*     */       case 33351:
/*  45 */         return "WIN";
/*     */       
/*     */       case 33352:
/*  48 */         return "SHADER";
/*     */       
/*     */       case 33353:
/*  51 */         return "EXT";
/*     */       
/*     */       case 33354:
/*  54 */         return "APP";
/*     */       
/*     */       case 33355:
/*  57 */         return "OTHER";
/*     */     } 
/*     */     
/*  60 */     return getUnknown(source);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType(int type) {
/*  66 */     switch (type) {
/*     */       
/*     */       case 33356:
/*  69 */         return "ERROR";
/*     */       
/*     */       case 33357:
/*  72 */         return "DEPRECATED";
/*     */       
/*     */       case 33358:
/*  75 */         return "UNDEFINED";
/*     */       
/*     */       case 33359:
/*  78 */         return "PORTABILITY";
/*     */       
/*     */       case 33360:
/*  81 */         return "PERFORMANCE";
/*     */       
/*     */       case 33361:
/*  84 */         return "OTHER";
/*     */     } 
/*     */     
/*  87 */     return getUnknown(type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSeverity(int severity) {
/*  93 */     switch (severity) {
/*     */       
/*     */       case 37190:
/*  96 */         return "HIGH";
/*     */       
/*     */       case 37191:
/*  99 */         return "MEDIUM";
/*     */       
/*     */       case 37192:
/* 102 */         return "LOW";
/*     */     } 
/*     */     
/* 105 */     return getUnknown(severity);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getUnknown(int token) {
/* 111 */     return "Unknown (0x" + Integer.toHexString(token).toUpperCase() + ")";
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\GlDebugHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */