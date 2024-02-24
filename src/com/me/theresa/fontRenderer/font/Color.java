/*     */ package com.me.theresa.fontRenderer.font;
/*     */ 
/*     */ import com.me.theresa.fontRenderer.font.opengl.renderer.Renderer;
/*     */ import com.me.theresa.fontRenderer.font.opengl.renderer.SGL;
/*     */ import java.io.Serializable;
/*     */ import java.nio.FloatBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Color
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1393939L;
/*  14 */   protected transient SGL GL = Renderer.get();
/*     */   
/*  16 */   public static final Color transparent = new Color(0.0F, 0.0F, 0.0F, 0.0F);
/*     */   
/*  18 */   public static final Color white = new Color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   
/*  20 */   public static final Color yellow = new Color(1.0F, 1.0F, 0.0F, 1.0F);
/*     */   
/*  22 */   public static final Color red = new Color(1.0F, 0.0F, 0.0F, 1.0F);
/*     */   
/*  24 */   public static final Color blue = new Color(0.0F, 0.0F, 1.0F, 1.0F);
/*     */   
/*  26 */   public static final Color green = new Color(0.0F, 1.0F, 0.0F, 1.0F);
/*     */   
/*  28 */   public static final Color black = new Color(0.0F, 0.0F, 0.0F, 1.0F);
/*     */   
/*  30 */   public static final Color gray = new Color(0.5F, 0.5F, 0.5F, 1.0F);
/*     */   
/*  32 */   public static final Color cyan = new Color(0.0F, 1.0F, 1.0F, 1.0F);
/*     */   
/*  34 */   public static final Color darkGray = new Color(0.3F, 0.3F, 0.3F, 1.0F);
/*     */   
/*  36 */   public static final Color lightGray = new Color(0.7F, 0.7F, 0.7F, 1.0F);
/*     */   
/*  38 */   public static final Color pink = new Color(255, 175, 175, 255);
/*     */   
/*  40 */   public static final Color orange = new Color(255, 200, 0, 255);
/*     */   
/*  42 */   public static final Color magenta = new Color(255, 0, 255, 255);
/*     */ 
/*     */   
/*     */   public float r;
/*     */   
/*     */   public float g;
/*     */   
/*     */   public float b;
/*     */   
/*  51 */   public float a = 1.0F;
/*     */ 
/*     */   
/*     */   public Color(Color color) {
/*  55 */     this.r = color.r;
/*  56 */     this.g = color.g;
/*  57 */     this.b = color.b;
/*  58 */     this.a = color.a;
/*     */   }
/*     */ 
/*     */   
/*     */   public Color(FloatBuffer buffer) {
/*  63 */     this.r = buffer.get();
/*  64 */     this.g = buffer.get();
/*  65 */     this.b = buffer.get();
/*  66 */     this.a = buffer.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public Color(float r, float g, float b) {
/*  71 */     this.r = r;
/*  72 */     this.g = g;
/*  73 */     this.b = b;
/*  74 */     this.a = 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public Color(float r, float g, float b, float a) {
/*  79 */     this.r = Math.min(r, 1.0F);
/*  80 */     this.g = Math.min(g, 1.0F);
/*  81 */     this.b = Math.min(b, 1.0F);
/*  82 */     this.a = Math.min(a, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public Color(int r, int g, int b) {
/*  87 */     this.r = r / 255.0F;
/*  88 */     this.g = g / 255.0F;
/*  89 */     this.b = b / 255.0F;
/*  90 */     this.a = 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public Color(int r, int g, int b, int a) {
/*  95 */     this.r = r / 255.0F;
/*  96 */     this.g = g / 255.0F;
/*  97 */     this.b = b / 255.0F;
/*  98 */     this.a = a / 255.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public Color(int value) {
/* 103 */     int r = (value & 0xFF0000) >> 16;
/* 104 */     int g = (value & 0xFF00) >> 8;
/* 105 */     int b = value & 0xFF;
/* 106 */     int a = (value & 0xFF000000) >> 24;
/*     */     
/* 108 */     if (a < 0) {
/* 109 */       a += 256;
/*     */     }
/* 111 */     if (a == 0) {
/* 112 */       a = 255;
/*     */     }
/*     */     
/* 115 */     this.r = r / 255.0F;
/* 116 */     this.g = g / 255.0F;
/* 117 */     this.b = b / 255.0F;
/* 118 */     this.a = a / 255.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Color decode(String nm) {
/* 123 */     return new Color(Integer.decode(nm).intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind() {
/* 128 */     this.GL.glColor4f(this.r, this.g, this.b, this.a);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 133 */     return (int)(this.r + this.g + this.b + this.a) * 255;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 138 */     if (other instanceof Color) {
/* 139 */       Color o = (Color)other;
/* 140 */       return (o.r == this.r && o.g == this.g && o.b == this.b && o.a == this.a);
/*     */     } 
/*     */     
/* 143 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 148 */     return "Color (" + this.r + "," + this.g + "," + this.b + "," + this.a + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   public Color darker() {
/* 153 */     return darker(0.5F);
/*     */   }
/*     */ 
/*     */   
/*     */   public Color darker(float scale) {
/* 158 */     scale = 1.0F - scale;
/* 159 */     Color temp = new Color(this.r * scale, this.g * scale, this.b * scale, this.a);
/*     */     
/* 161 */     return temp;
/*     */   }
/*     */ 
/*     */   
/*     */   public Color brighter() {
/* 166 */     return brighter(0.2F);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRed() {
/* 171 */     return (int)(this.r * 255.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGreen() {
/* 176 */     return (int)(this.g * 255.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlue() {
/* 181 */     return (int)(this.b * 255.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAlpha() {
/* 186 */     return (int)(this.a * 255.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRedByte() {
/* 191 */     return (int)(this.r * 255.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGreenByte() {
/* 196 */     return (int)(this.g * 255.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlueByte() {
/* 201 */     return (int)(this.b * 255.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAlphaByte() {
/* 206 */     return (int)(this.a * 255.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public Color brighter(float scale) {
/* 211 */     scale++;
/* 212 */     Color temp = new Color(this.r * scale, this.g * scale, this.b * scale, this.a);
/*     */     
/* 214 */     return temp;
/*     */   }
/*     */ 
/*     */   
/*     */   public Color multiply(Color c) {
/* 219 */     return new Color(this.r * c.r, this.g * c.g, this.b * c.b, this.a * c.a);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(Color c) {
/* 224 */     this.r += c.r;
/* 225 */     this.g += c.g;
/* 226 */     this.b += c.b;
/* 227 */     this.a += c.a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void scale(float value) {
/* 232 */     this.r *= value;
/* 233 */     this.g *= value;
/* 234 */     this.b *= value;
/* 235 */     this.a *= value;
/*     */   }
/*     */ 
/*     */   
/*     */   public Color addToCopy(Color c) {
/* 240 */     Color copy = new Color(this.r, this.g, this.b, this.a);
/* 241 */     copy.r += c.r;
/* 242 */     copy.g += c.g;
/* 243 */     copy.b += c.b;
/* 244 */     copy.a += c.a;
/*     */     
/* 246 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public Color scaleCopy(float value) {
/* 251 */     Color copy = new Color(this.r, this.g, this.b, this.a);
/* 252 */     copy.r *= value;
/* 253 */     copy.g *= value;
/* 254 */     copy.b *= value;
/* 255 */     copy.a *= value;
/*     */     
/* 257 */     return copy;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\Color.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */