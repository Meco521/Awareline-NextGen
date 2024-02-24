/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Arrays;
/*     */ import net.optifine.render.GlAlphaState;
/*     */ import net.optifine.render.GlBlendState;
/*     */ import net.optifine.shaders.config.RenderScale;
/*     */ 
/*     */ 
/*     */ public class Program
/*     */ {
/*     */   private final int index;
/*     */   private final String name;
/*     */   private final ProgramStage programStage;
/*     */   private final Program programBackup;
/*     */   private GlAlphaState alphaState;
/*     */   private GlBlendState blendState;
/*     */   private RenderScale renderScale;
/*  19 */   private final Boolean[] buffersFlip = new Boolean[8];
/*     */   private int id;
/*     */   private int ref;
/*     */   private String drawBufSettings;
/*     */   private IntBuffer drawBuffers;
/*     */   private IntBuffer drawBuffersBuffer;
/*     */   private int compositeMipmapSetting;
/*     */   private int countInstances;
/*  27 */   private final boolean[] toggleColorTextures = new boolean[8];
/*     */ 
/*     */   
/*     */   public Program(int index, String name, ProgramStage programStage, Program programBackup) {
/*  31 */     this.index = index;
/*  32 */     this.name = name;
/*  33 */     this.programStage = programStage;
/*  34 */     this.programBackup = programBackup;
/*     */   }
/*     */ 
/*     */   
/*     */   public Program(int index, String name, ProgramStage programStage, boolean ownBackup) {
/*  39 */     this.index = index;
/*  40 */     this.name = name;
/*  41 */     this.programStage = programStage;
/*  42 */     this.programBackup = ownBackup ? this : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetProperties() {
/*  47 */     this.alphaState = null;
/*  48 */     this.blendState = null;
/*  49 */     this.renderScale = null;
/*  50 */     Arrays.fill((Object[])this.buffersFlip, (Object)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetId() {
/*  55 */     this.id = 0;
/*  56 */     this.ref = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetConfiguration() {
/*  61 */     this.drawBufSettings = null;
/*  62 */     this.compositeMipmapSetting = 0;
/*  63 */     this.countInstances = 0;
/*     */     
/*  65 */     if (this.drawBuffersBuffer == null)
/*     */     {
/*  67 */       this.drawBuffersBuffer = Shaders.nextIntBuffer(8);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void copyFrom(Program p) {
/*  73 */     this.id = p.id;
/*  74 */     this.alphaState = p.alphaState;
/*  75 */     this.blendState = p.blendState;
/*  76 */     this.renderScale = p.renderScale;
/*  77 */     System.arraycopy(p.getBuffersFlip(), 0, this.buffersFlip, 0, this.buffersFlip.length);
/*  78 */     this.drawBufSettings = p.drawBufSettings;
/*  79 */     this.drawBuffers = p.drawBuffers;
/*  80 */     this.compositeMipmapSetting = p.compositeMipmapSetting;
/*  81 */     this.countInstances = p.countInstances;
/*  82 */     System.arraycopy(p.getToggleColorTextures(), 0, this.toggleColorTextures, 0, this.toggleColorTextures.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIndex() {
/*  87 */     return this.index;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  92 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public ProgramStage getProgramStage() {
/*  97 */     return this.programStage;
/*     */   }
/*     */ 
/*     */   
/*     */   public Program getProgramBackup() {
/* 102 */     return this.programBackup;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/* 107 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRef() {
/* 112 */     return this.ref;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDrawBufSettings() {
/* 117 */     return this.drawBufSettings;
/*     */   }
/*     */ 
/*     */   
/*     */   public IntBuffer getDrawBuffers() {
/* 122 */     return this.drawBuffers;
/*     */   }
/*     */ 
/*     */   
/*     */   public IntBuffer getDrawBuffersBuffer() {
/* 127 */     return this.drawBuffersBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCompositeMipmapSetting() {
/* 132 */     return this.compositeMipmapSetting;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCountInstances() {
/* 137 */     return this.countInstances;
/*     */   }
/*     */ 
/*     */   
/*     */   public GlAlphaState getAlphaState() {
/* 142 */     return this.alphaState;
/*     */   }
/*     */ 
/*     */   
/*     */   public GlBlendState getBlendState() {
/* 147 */     return this.blendState;
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderScale getRenderScale() {
/* 152 */     return this.renderScale;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean[] getBuffersFlip() {
/* 157 */     return this.buffersFlip;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] getToggleColorTextures() {
/* 162 */     return this.toggleColorTextures;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setId(int id) {
/* 167 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRef(int ref) {
/* 172 */     this.ref = ref;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDrawBufSettings(String drawBufSettings) {
/* 177 */     this.drawBufSettings = drawBufSettings;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDrawBuffers(IntBuffer drawBuffers) {
/* 182 */     this.drawBuffers = drawBuffers;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCompositeMipmapSetting(int compositeMipmapSetting) {
/* 187 */     this.compositeMipmapSetting = compositeMipmapSetting;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCountInstances(int countInstances) {
/* 192 */     this.countInstances = countInstances;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAlphaState(GlAlphaState alphaState) {
/* 197 */     this.alphaState = alphaState;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlendState(GlBlendState blendState) {
/* 202 */     this.blendState = blendState;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRenderScale(RenderScale renderScale) {
/* 207 */     this.renderScale = renderScale;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRealProgramName() {
/* 212 */     if (this.id == 0)
/*     */     {
/* 214 */       return "none";
/*     */     }
/*     */ 
/*     */     
/*     */     Program program;
/*     */     
/* 220 */     for (program = this; program.ref != this.id; program = program.programBackup) {
/*     */       
/* 222 */       if (program.programBackup == null || program.programBackup == program)
/*     */       {
/* 224 */         return "unknown";
/*     */       }
/*     */     } 
/*     */     
/* 228 */     return program.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 234 */     return "name: " + this.name + ", id: " + this.id + ", ref: " + this.ref + ", real: " + getRealProgramName();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\Program.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */