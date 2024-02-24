/*     */ package net.optifine.shaders.uniform;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.opengl.ARBShaderObjects;
/*     */ 
/*     */ 
/*     */ public abstract class ShaderUniformBase
/*     */ {
/*     */   private final String name;
/*  11 */   private int program = 0;
/*  12 */   private int[] locations = new int[] { -1 };
/*     */   
/*     */   private static final int LOCATION_UNDEFINED = -1;
/*     */   private static final int LOCATION_UNKNOWN = -2147483648;
/*     */   
/*     */   public ShaderUniformBase(String name) {
/*  18 */     this.name = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProgram(int program) {
/*  23 */     if (this.program != program) {
/*     */       
/*  25 */       this.program = program;
/*  26 */       expandLocations();
/*  27 */       onProgramSet(program);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void expandLocations() {
/*  33 */     if (this.program >= this.locations.length) {
/*     */       
/*  35 */       int[] aint = new int[this.program << 1];
/*  36 */       Arrays.fill(aint, -2147483648);
/*  37 */       System.arraycopy(this.locations, 0, aint, 0, this.locations.length);
/*  38 */       this.locations = aint;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void onProgramSet(int paramInt);
/*     */   
/*     */   public String getName() {
/*  46 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getProgram() {
/*  51 */     return this.program;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLocation() {
/*  56 */     if (this.program <= 0)
/*     */     {
/*  58 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*  62 */     int i = this.locations[this.program];
/*     */     
/*  64 */     if (i == Integer.MIN_VALUE) {
/*     */       
/*  66 */       i = ARBShaderObjects.glGetUniformLocationARB(this.program, this.name);
/*  67 */       this.locations[this.program] = i;
/*     */     } 
/*     */     
/*  70 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDefined() {
/*  76 */     return (getLocation() >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void disable() {
/*  81 */     this.locations[this.program] = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/*  86 */     this.program = 0;
/*  87 */     this.locations = new int[] { -1 };
/*  88 */     resetValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void resetValue();
/*     */   
/*     */   protected void checkGLError() {
/*  95 */     if (Shaders.checkGLError(this.name) != 0)
/*     */     {
/*  97 */       disable();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 103 */     return this.name;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shader\\uniform\ShaderUniformBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */