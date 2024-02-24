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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Color4b
/*     */   extends Tuple4b
/*     */ {
/*     */   static final long serialVersionUID = -105080578052502155L;
/*     */   
/*     */   public Color4b(byte b1, byte b2, byte b3, byte b4) {
/*  60 */     super(b1, b2, b3, b4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color4b(byte[] c) {
/*  69 */     super(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color4b(Color4b c1) {
/*  79 */     super(c1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color4b(Tuple4b t1) {
/*  89 */     super(t1);
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
/*     */   public Color4b(Color color) {
/* 105 */     super((byte)color.getRed(), 
/* 106 */         (byte)color.getGreen(), 
/* 107 */         (byte)color.getBlue(), 
/* 108 */         (byte)color.getAlpha());
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
/*     */   public Color4b() {}
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
/* 131 */     this.x = (byte)color.getRed();
/* 132 */     this.y = (byte)color.getGreen();
/* 133 */     this.z = (byte)color.getBlue();
/* 134 */     this.w = (byte)color.getAlpha();
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
/* 147 */     int r = this.x & 0xFF;
/* 148 */     int g = this.y & 0xFF;
/* 149 */     int b = this.z & 0xFF;
/* 150 */     int a = this.w & 0xFF;
/*     */     
/* 152 */     return new Color(r, g, b, a);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\VLOUBOOS\javax\vecmath\Color4b.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */