/*     */ package net.optifine;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiIngame;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.profiler.Profiler;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.util.MemoryMonitor;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Lagometer
/*     */ {
/*     */   private static Minecraft mc;
/*     */   private static GameSettings gameSettings;
/*     */   private static Profiler profiler;
/*     */   public static boolean active = false;
/*  22 */   public static TimerNano timerTick = new TimerNano();
/*  23 */   public static TimerNano timerScheduledExecutables = new TimerNano();
/*  24 */   public static TimerNano timerChunkUpload = new TimerNano();
/*  25 */   public static TimerNano timerChunkUpdate = new TimerNano();
/*  26 */   public static TimerNano timerVisibility = new TimerNano();
/*  27 */   public static TimerNano timerTerrain = new TimerNano();
/*  28 */   public static TimerNano timerServer = new TimerNano();
/*  29 */   private static final long[] timesFrame = new long[512];
/*  30 */   private static final long[] timesTick = new long[512];
/*  31 */   private static final long[] timesScheduledExecutables = new long[512];
/*  32 */   private static final long[] timesChunkUpload = new long[512];
/*  33 */   private static final long[] timesChunkUpdate = new long[512];
/*  34 */   private static final long[] timesVisibility = new long[512];
/*  35 */   private static final long[] timesTerrain = new long[512];
/*  36 */   private static final long[] timesServer = new long[512];
/*  37 */   private static final boolean[] gcs = new boolean[512];
/*  38 */   private static int numRecordedFrameTimes = 0;
/*  39 */   private static long prevFrameTimeNano = -1L;
/*  40 */   private static long renderTimeNano = 0L;
/*     */ 
/*     */   
/*     */   public static void updateLagometer() {
/*  44 */     if (mc == null) {
/*     */       
/*  46 */       mc = Minecraft.getMinecraft();
/*  47 */       gameSettings = mc.gameSettings;
/*  48 */       profiler = mc.mcProfiler;
/*     */     } 
/*     */     
/*  51 */     if (gameSettings.showDebugInfo && (gameSettings.ofLagometer || gameSettings.showLagometer)) {
/*     */       
/*  53 */       active = true;
/*  54 */       long timeNowNano = System.nanoTime();
/*     */       
/*  56 */       if (prevFrameTimeNano == -1L)
/*     */       {
/*  58 */         prevFrameTimeNano = timeNowNano;
/*     */       }
/*     */       else
/*     */       {
/*  62 */         int j = numRecordedFrameTimes & timesFrame.length - 1;
/*  63 */         numRecordedFrameTimes++;
/*  64 */         boolean flag = MemoryMonitor.isGcEvent();
/*  65 */         timesFrame[j] = timeNowNano - prevFrameTimeNano - renderTimeNano;
/*  66 */         timesTick[j] = timerTick.timeNano;
/*  67 */         timesScheduledExecutables[j] = timerScheduledExecutables.timeNano;
/*  68 */         timesChunkUpload[j] = timerChunkUpload.timeNano;
/*  69 */         timesChunkUpdate[j] = timerChunkUpdate.timeNano;
/*  70 */         timesVisibility[j] = timerVisibility.timeNano;
/*  71 */         timesTerrain[j] = timerTerrain.timeNano;
/*  72 */         timesServer[j] = timerServer.timeNano;
/*  73 */         gcs[j] = flag;
/*  74 */         timerTick.reset();
/*  75 */         timerScheduledExecutables.reset();
/*  76 */         timerVisibility.reset();
/*  77 */         timerChunkUpdate.reset();
/*  78 */         timerChunkUpload.reset();
/*  79 */         timerTerrain.reset();
/*  80 */         timerServer.reset();
/*  81 */         prevFrameTimeNano = System.nanoTime();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  86 */       active = false;
/*  87 */       prevFrameTimeNano = -1L;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void showLagometer(ScaledResolution scaledResolution) {
/*  93 */     if (gameSettings != null)
/*     */     {
/*  95 */       if (gameSettings.ofLagometer || gameSettings.showLagometer) {
/*     */         
/*  97 */         long i = System.nanoTime();
/*  98 */         GlStateManager.clear(256);
/*  99 */         GlStateManager.matrixMode(5889);
/* 100 */         GlStateManager.pushMatrix();
/* 101 */         GlStateManager.enableColorMaterial();
/* 102 */         GlStateManager.loadIdentity();
/* 103 */         GlStateManager.ortho(0.0D, mc.displayWidth, mc.displayHeight, 0.0D, 1000.0D, 3000.0D);
/* 104 */         GlStateManager.matrixMode(5888);
/* 105 */         GlStateManager.pushMatrix();
/* 106 */         GlStateManager.loadIdentity();
/* 107 */         GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/* 108 */         GL11.glLineWidth(1.0F);
/* 109 */         GlStateManager.disableTexture2D();
/* 110 */         Tessellator tessellator = Tessellator.getInstance();
/* 111 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 112 */         worldrenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
/*     */         
/* 114 */         for (int j = 0; j < timesFrame.length; j++) {
/*     */           
/* 116 */           int k = (j - numRecordedFrameTimes & timesFrame.length - 1) * 100 / timesFrame.length;
/* 117 */           k += 155;
/* 118 */           float f = mc.displayHeight;
/* 119 */           long l = 0L;
/*     */           
/* 121 */           if (gcs[j]) {
/*     */             
/* 123 */             renderTime(j, timesFrame[j], k, k / 2, 0, f, worldrenderer);
/*     */           }
/*     */           else {
/*     */             
/* 127 */             renderTime(j, timesFrame[j], k, k, k, f, worldrenderer);
/* 128 */             f -= (float)renderTime(j, timesServer[j], k / 2, k / 2, k / 2, f, worldrenderer);
/* 129 */             f -= (float)renderTime(j, timesTerrain[j], 0, k, 0, f, worldrenderer);
/* 130 */             f -= (float)renderTime(j, timesVisibility[j], k, k, 0, f, worldrenderer);
/* 131 */             f -= (float)renderTime(j, timesChunkUpdate[j], k, 0, 0, f, worldrenderer);
/* 132 */             f -= (float)renderTime(j, timesChunkUpload[j], k, 0, k, f, worldrenderer);
/* 133 */             f -= (float)renderTime(j, timesScheduledExecutables[j], 0, 0, k, f, worldrenderer);
/* 134 */             float f2 = f - (float)renderTime(j, timesTick[j], 0, k, k, f, worldrenderer);
/*     */           } 
/*     */         } 
/*     */         
/* 138 */         renderTimeDivider(0, timesFrame.length, 33333333L, 196, 196, 196, mc.displayHeight, worldrenderer);
/* 139 */         renderTimeDivider(0, timesFrame.length, 16666666L, 196, 196, 196, mc.displayHeight, worldrenderer);
/* 140 */         tessellator.draw();
/* 141 */         GlStateManager.enableTexture2D();
/* 142 */         int j2 = mc.displayHeight - 80;
/* 143 */         int k2 = mc.displayHeight - 160;
/* 144 */         mc.fontRendererObj.drawString("30", 2, k2 + 1, -8947849);
/* 145 */         mc.fontRendererObj.drawString("30", 1, k2, -3881788);
/* 146 */         mc.fontRendererObj.drawString("60", 2, j2 + 1, -8947849);
/* 147 */         mc.fontRendererObj.drawString("60", 1, j2, -3881788);
/* 148 */         GlStateManager.matrixMode(5889);
/* 149 */         GlStateManager.popMatrix();
/* 150 */         GlStateManager.matrixMode(5888);
/* 151 */         GlStateManager.popMatrix();
/* 152 */         GlStateManager.enableTexture2D();
/* 153 */         float f1 = 1.0F - (float)((System.currentTimeMillis() - MemoryMonitor.getStartTimeMs()) / 1000.0D);
/* 154 */         f1 = Config.limit(f1, 0.0F, 1.0F);
/* 155 */         int l2 = (int)(170.0F + f1 * 85.0F);
/* 156 */         int i1 = (int)(100.0F + f1 * 55.0F);
/* 157 */         int j1 = (int)(10.0F + f1 * 10.0F);
/* 158 */         int k1 = l2 << 16 | i1 << 8 | j1;
/* 159 */         int l1 = 512 / scaledResolution.getScaleFactor() + 2;
/* 160 */         int i2 = mc.displayHeight / scaledResolution.getScaleFactor() - 8;
/* 161 */         GuiIngame guiingame = mc.ingameGUI;
/* 162 */         GuiIngame.drawRect(l1 - 1, i2 - 1, l1 + 50, i2 + 10, -1605349296);
/* 163 */         mc.fontRendererObj.drawString(" " + MemoryMonitor.getAllocationRateMb() + " MB/s", l1, i2, k1);
/* 164 */         renderTimeNano = System.nanoTime() - i;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static long renderTime(int frameNum, long time, int r, int g, int b, float baseHeight, WorldRenderer tessellator) {
/* 171 */     long i = time / 200000L;
/*     */     
/* 173 */     if (i < 3L)
/*     */     {
/* 175 */       return 0L;
/*     */     }
/*     */ 
/*     */     
/* 179 */     tessellator.pos((frameNum + 0.5F), (baseHeight - (float)i + 0.5F), 0.0D).color(r, g, b, 255).endVertex();
/* 180 */     tessellator.pos((frameNum + 0.5F), (baseHeight + 0.5F), 0.0D).color(r, g, b, 255).endVertex();
/* 181 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static long renderTimeDivider(int frameStart, int frameEnd, long time, int r, int g, int b, float baseHeight, WorldRenderer tessellator) {
/* 187 */     long i = time / 200000L;
/*     */     
/* 189 */     if (i < 3L)
/*     */     {
/* 191 */       return 0L;
/*     */     }
/*     */ 
/*     */     
/* 195 */     tessellator.pos((frameStart + 0.5F), (baseHeight - (float)i + 0.5F), 0.0D).color(r, g, b, 255).endVertex();
/* 196 */     tessellator.pos((frameEnd + 0.5F), (baseHeight - (float)i + 0.5F), 0.0D).color(r, g, b, 255).endVertex();
/* 197 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isActive() {
/* 203 */     return active;
/*     */   }
/*     */   
/*     */   public static class TimerNano
/*     */   {
/* 208 */     public long timeStartNano = 0L;
/* 209 */     public long timeNano = 0L;
/*     */ 
/*     */     
/*     */     public void start() {
/* 213 */       if (Lagometer.active)
/*     */       {
/* 215 */         if (this.timeStartNano == 0L)
/*     */         {
/* 217 */           this.timeStartNano = System.nanoTime();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 224 */       if (Lagometer.active)
/*     */       {
/* 226 */         if (this.timeStartNano != 0L) {
/*     */           
/* 228 */           this.timeNano += System.nanoTime() - this.timeStartNano;
/* 229 */           this.timeStartNano = 0L;
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     void reset() {
/* 236 */       this.timeNano = 0L;
/* 237 */       this.timeStartNano = 0L;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\Lagometer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */