/*     */ package net.optifine.render;
/*     */ 
/*     */ 
/*     */ public class GlBlendState
/*     */ {
/*     */   private boolean enabled;
/*     */   private int srcFactor;
/*     */   private int dstFactor;
/*     */   private int srcFactorAlpha;
/*     */   private int dstFactorAlpha;
/*     */   
/*     */   public GlBlendState() {
/*  13 */     this(false, 1, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public GlBlendState(boolean enabled) {
/*  18 */     this(enabled, 1, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public GlBlendState(boolean enabled, int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
/*  23 */     this.enabled = enabled;
/*  24 */     this.srcFactor = srcFactor;
/*  25 */     this.dstFactor = dstFactor;
/*  26 */     this.srcFactorAlpha = srcFactorAlpha;
/*  27 */     this.dstFactorAlpha = dstFactorAlpha;
/*     */   }
/*     */ 
/*     */   
/*     */   public GlBlendState(boolean enabled, int srcFactor, int dstFactor) {
/*  32 */     this(enabled, srcFactor, dstFactor, srcFactor, dstFactor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setState(boolean enabled, int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
/*  37 */     this.enabled = enabled;
/*  38 */     this.srcFactor = srcFactor;
/*  39 */     this.dstFactor = dstFactor;
/*  40 */     this.srcFactorAlpha = srcFactorAlpha;
/*  41 */     this.dstFactorAlpha = dstFactorAlpha;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setState(GlBlendState state) {
/*  46 */     this.enabled = state.enabled;
/*  47 */     this.srcFactor = state.srcFactor;
/*  48 */     this.dstFactor = state.dstFactor;
/*  49 */     this.srcFactorAlpha = state.srcFactorAlpha;
/*  50 */     this.dstFactorAlpha = state.dstFactorAlpha;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/*  55 */     this.enabled = enabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabled() {
/*  60 */     this.enabled = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDisabled() {
/*  65 */     this.enabled = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFactors(int srcFactor, int dstFactor) {
/*  70 */     this.srcFactor = srcFactor;
/*  71 */     this.dstFactor = dstFactor;
/*  72 */     this.srcFactorAlpha = srcFactor;
/*  73 */     this.dstFactorAlpha = dstFactor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFactors(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
/*  78 */     this.srcFactor = srcFactor;
/*  79 */     this.dstFactor = dstFactor;
/*  80 */     this.srcFactorAlpha = srcFactorAlpha;
/*  81 */     this.dstFactorAlpha = dstFactorAlpha;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/*  86 */     return this.enabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSrcFactor() {
/*  91 */     return this.srcFactor;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDstFactor() {
/*  96 */     return this.dstFactor;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSrcFactorAlpha() {
/* 101 */     return this.srcFactorAlpha;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDstFactorAlpha() {
/* 106 */     return this.dstFactorAlpha;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSeparate() {
/* 111 */     return (this.srcFactor != this.srcFactorAlpha || this.dstFactor != this.dstFactorAlpha);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 116 */     return "enabled: " + this.enabled + ", src: " + this.srcFactor + ", dst: " + this.dstFactor + ", srcAlpha: " + this.srcFactorAlpha + ", dstAlpha: " + this.dstFactorAlpha;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\render\GlBlendState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */