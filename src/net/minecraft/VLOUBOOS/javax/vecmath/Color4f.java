/*     */ package net.minecraft.VLOUBOOS.javax.vecmath;
/*     */ 
/*     */ import java.awt.Color;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Color4f
/*     */   extends Tuple4f
/*     */ {
/*     */   static final long serialVersionUID = 8577680141580006740L;
/*     */   
/*     */   public Color4f(float x, float y, float z, float w) {
/*  56 */     super(x, y, z, w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color4f(float[] c) {
/*  65 */     super(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color4f(Color4f c1) {
/*  74 */     super(c1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color4f(Tuple4f t1) {
/*  83 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color4f(Tuple4d t1) {
/*  92 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color4f(Color color) {
/* 108 */     super(color.getRed() / 255.0F, color
/* 109 */         .getGreen() / 255.0F, color
/* 110 */         .getBlue() / 255.0F, color
/* 111 */         .getAlpha() / 255.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color4f() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Color color) {
/* 134 */     this.x = color.getRed() / 255.0F;
/* 135 */     this.y = color.getGreen() / 255.0F;
/* 136 */     this.z = color.getBlue() / 255.0F;
/* 137 */     this.w = color.getAlpha() / 255.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Color get() {
/* 150 */     int r = Math.round(this.x * 255.0F);
/* 151 */     int g = Math.round(this.y * 255.0F);
/* 152 */     int b = Math.round(this.z * 255.0F);
/* 153 */     int a = Math.round(this.w * 255.0F);
/*     */     
/* 155 */     return new Color(r, g, b, a);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\VLOUBOOS\javax\vecmath\Color4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */