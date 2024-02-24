/*     */ package com.me.theresa.fontRenderer.font.opengl.renderer;
/*     */ 
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ public class QuadBasedLineStripRenderer
/*     */   implements LineStripRenderer {
/*   7 */   private final SGL GL = Renderer.get();
/*     */ 
/*     */   
/*  10 */   public static int MAX_POINTS = 10000;
/*     */   
/*     */   private boolean antialias;
/*     */   
/*  14 */   private float width = 1.0F;
/*     */ 
/*     */   
/*     */   private final float[] points;
/*     */   
/*     */   private final float[] colours;
/*     */   
/*     */   private int pts;
/*     */   
/*     */   private int cpt;
/*     */   
/*  25 */   private final DefaultLineStripRenderer def = new DefaultLineStripRenderer();
/*     */ 
/*     */   
/*     */   private boolean renderHalf;
/*     */   
/*     */   private boolean lineCaps = false;
/*     */ 
/*     */   
/*     */   public QuadBasedLineStripRenderer() {
/*  34 */     this.points = new float[MAX_POINTS << 1];
/*  35 */     this.colours = new float[MAX_POINTS << 2];
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLineCaps(boolean caps) {
/*  40 */     this.lineCaps = caps;
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  45 */     if (this.width == 1.0F) {
/*  46 */       this.def.start();
/*     */       
/*     */       return;
/*     */     } 
/*  50 */     this.pts = 0;
/*  51 */     this.cpt = 0;
/*  52 */     this.GL.flush();
/*     */     
/*  54 */     float[] col = this.GL.getCurrentColor();
/*  55 */     color(col[0], col[1], col[2], col[3]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void end() {
/*  60 */     if (this.width == 1.0F) {
/*  61 */       this.def.end();
/*     */       
/*     */       return;
/*     */     } 
/*  65 */     renderLines(this.points, this.pts);
/*     */   }
/*     */ 
/*     */   
/*     */   public void vertex(float x, float y) {
/*  70 */     if (this.width == 1.0F) {
/*  71 */       this.def.vertex(x, y);
/*     */       
/*     */       return;
/*     */     } 
/*  75 */     this.points[this.pts << 1] = x;
/*  76 */     this.points[(this.pts << 1) + 1] = y;
/*  77 */     this.pts++;
/*     */     
/*  79 */     int index = this.pts - 1;
/*  80 */     color(this.colours[index << 2], this.colours[(index << 2) + 1], this.colours[(index << 2) + 2], this.colours[(index << 2) + 3]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWidth(float width) {
/*  85 */     this.width = width;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAntiAlias(boolean antialias) {
/*  90 */     this.def.setAntiAlias(antialias);
/*  91 */     this.antialias = antialias;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderLines(float[] points, int count) {
/*  96 */     if (this.antialias) {
/*  97 */       this.GL.glEnable(2881);
/*  98 */       renderLinesImpl(points, count, this.width + 1.0F);
/*     */     } 
/*     */     
/* 101 */     this.GL.glDisable(2881);
/* 102 */     renderLinesImpl(points, count, this.width);
/*     */     
/* 104 */     if (this.antialias) {
/* 105 */       this.GL.glEnable(2881);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderLinesImpl(float[] points, int count, float w) {
/* 111 */     float width = w / 2.0F;
/*     */     
/* 113 */     float lastx1 = 0.0F;
/* 114 */     float lasty1 = 0.0F;
/* 115 */     float lastx2 = 0.0F;
/* 116 */     float lasty2 = 0.0F;
/*     */     
/* 118 */     this.GL.glBegin(7);
/* 119 */     for (int i = 0; i < count + 1; i++) {
/* 120 */       int current = i;
/* 121 */       int next = i + 1;
/* 122 */       int prev = i - 1;
/* 123 */       if (prev < 0) {
/* 124 */         prev += count;
/*     */       }
/* 126 */       if (next >= count) {
/* 127 */         next -= count;
/*     */       }
/* 129 */       if (current >= count) {
/* 130 */         current -= count;
/*     */       }
/*     */       
/* 133 */       float x1 = points[current << 1];
/* 134 */       float y1 = points[(current << 1) + 1];
/* 135 */       float x2 = points[next << 1];
/* 136 */       float y2 = points[(next << 1) + 1];
/*     */ 
/*     */       
/* 139 */       float dx = x2 - x1;
/* 140 */       float dy = y2 - y1;
/*     */       
/* 142 */       if (dx != 0.0F || dy != 0.0F) {
/*     */ 
/*     */ 
/*     */         
/* 146 */         float d2 = dx * dx + dy * dy;
/* 147 */         float d = (float)Math.sqrt(d2);
/* 148 */         dx *= width;
/* 149 */         dy *= width;
/* 150 */         dx /= d;
/* 151 */         dy /= d;
/*     */         
/* 153 */         float tx = dy;
/* 154 */         float ty = -dx;
/*     */         
/* 156 */         if (i != 0) {
/* 157 */           bindColor(prev);
/* 158 */           this.GL.glVertex3f(lastx1, lasty1, 0.0F);
/* 159 */           this.GL.glVertex3f(lastx2, lasty2, 0.0F);
/* 160 */           bindColor(current);
/* 161 */           this.GL.glVertex3f(x1 + tx, y1 + ty, 0.0F);
/* 162 */           this.GL.glVertex3f(x1 - tx, y1 - ty, 0.0F);
/*     */         } 
/*     */         
/* 165 */         lastx1 = x2 - tx;
/* 166 */         lasty1 = y2 - ty;
/* 167 */         lastx2 = x2 + tx;
/* 168 */         lasty2 = y2 + ty;
/*     */         
/* 170 */         if (i < count - 1) {
/* 171 */           bindColor(current);
/* 172 */           this.GL.glVertex3f(x1 + tx, y1 + ty, 0.0F);
/* 173 */           this.GL.glVertex3f(x1 - tx, y1 - ty, 0.0F);
/* 174 */           bindColor(next);
/* 175 */           this.GL.glVertex3f(x2 - tx, y2 - ty, 0.0F);
/* 176 */           this.GL.glVertex3f(x2 + tx, y2 + ty, 0.0F);
/*     */         } 
/*     */       } 
/*     */     } 
/* 180 */     this.GL.glEnd();
/*     */     
/* 182 */     float step = (width <= 12.5F) ? 5.0F : (180.0F / (float)Math.ceil(width / 2.5D));
/*     */ 
/*     */     
/* 185 */     if (this.lineCaps) {
/* 186 */       float dx = points[2] - points[0];
/* 187 */       float dy = points[3] - points[1];
/* 188 */       float fang = (float)Math.toDegrees(Math.atan2(dy, dx)) + 90.0F;
/*     */       
/* 190 */       if (dx != 0.0F || dy != 0.0F) {
/* 191 */         this.GL.glBegin(6);
/* 192 */         bindColor(0);
/* 193 */         this.GL.glVertex2f(points[0], points[1]); int j;
/* 194 */         for (j = 0; j < 180.0F + step; j = (int)(j + step)) {
/* 195 */           float ang = (float)Math.toRadians((fang + j));
/* 196 */           this.GL.glVertex2f(points[0] + MathHelper.cos(ang) * width, points[1] + 
/* 197 */               MathHelper.sin(ang) * width);
/*     */         } 
/* 199 */         this.GL.glEnd();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 204 */     if (this.lineCaps) {
/* 205 */       float dx = points[(count << 1) - 2] - points[(count << 1) - 4];
/* 206 */       float dy = points[(count << 1) - 1] - points[(count << 1) - 3];
/* 207 */       float fang = (float)Math.toDegrees(Math.atan2(dy, dx)) - 90.0F;
/*     */       
/* 209 */       if (dx != 0.0F || dy != 0.0F) {
/* 210 */         this.GL.glBegin(6);
/* 211 */         bindColor(count - 1);
/* 212 */         this.GL.glVertex2f(points[(count << 1) - 2], points[(count << 1) - 1]); int j;
/* 213 */         for (j = 0; j < 180.0F + step; j = (int)(j + step)) {
/* 214 */           float ang = (float)Math.toRadians((fang + j));
/* 215 */           this.GL.glVertex2f(points[(count << 1) - 2] + MathHelper.cos(ang) * width, points[(count << 1) - 1] + 
/* 216 */               MathHelper.sin(ang) * width);
/*     */         } 
/* 218 */         this.GL.glEnd();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void bindColor(int index) {
/* 225 */     if (index < this.cpt) {
/* 226 */       if (this.renderHalf) {
/* 227 */         this.GL.glColor4f(this.colours[index << 2] * 0.5F, this.colours[(index << 2) + 1] * 0.5F, this.colours[(index << 2) + 2] * 0.5F, this.colours[(index << 2) + 3] * 0.5F);
/*     */       } else {
/*     */         
/* 230 */         this.GL.glColor4f(this.colours[index << 2], this.colours[(index << 2) + 1], this.colours[(index << 2) + 2], this.colours[(index << 2) + 3]);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void color(float r, float g, float b, float a) {
/* 238 */     if (this.width == 1.0F) {
/* 239 */       this.def.color(r, g, b, a);
/*     */       
/*     */       return;
/*     */     } 
/* 243 */     this.colours[this.pts << 2] = r;
/* 244 */     this.colours[(this.pts << 2) + 1] = g;
/* 245 */     this.colours[(this.pts << 2) + 2] = b;
/* 246 */     this.colours[(this.pts << 2) + 3] = a;
/* 247 */     this.cpt++;
/*     */   }
/*     */   
/*     */   public boolean applyGLLineFixes() {
/* 251 */     if (this.width == 1.0F) {
/* 252 */       return this.def.applyGLLineFixes();
/*     */     }
/*     */     
/* 255 */     return this.def.applyGLLineFixes();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\renderer\QuadBasedLineStripRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */