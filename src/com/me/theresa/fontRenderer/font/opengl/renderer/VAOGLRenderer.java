/*     */ package com.me.theresa.fontRenderer.font.opengl.renderer;
/*     */ 
/*     */ import java.nio.DoubleBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VAOGLRenderer
/*     */   extends ImmediateModeOGLRenderer
/*     */ {
/*     */   private static final int TOLERANCE = 20;
/*     */   public static final int NONE = -1;
/*     */   public static final int MAX_VERTS = 5000;
/*  18 */   private int currentType = -1;
/*     */   
/*  20 */   private final float[] color = new float[] { 1.0F, 1.0F, 1.0F, 1.0F };
/*     */   
/*  22 */   private final float[] tex = new float[] { 0.0F, 0.0F };
/*     */ 
/*     */   
/*     */   private int vertIndex;
/*     */   
/*  27 */   private final float[] verts = new float[15000];
/*     */   
/*  29 */   private final float[] cols = new float[20000];
/*     */   
/*  31 */   private final float[] texs = new float[15000];
/*     */ 
/*     */   
/*  34 */   private final FloatBuffer vertices = BufferUtils.createFloatBuffer(15000);
/*     */   
/*  36 */   private final FloatBuffer colors = BufferUtils.createFloatBuffer(20000);
/*     */   
/*  38 */   private final FloatBuffer textures = BufferUtils.createFloatBuffer(10000);
/*     */ 
/*     */   
/*  41 */   private int listMode = 0;
/*     */ 
/*     */   
/*     */   public void initDisplay(int width, int height) {
/*  45 */     super.initDisplay(width, height);
/*     */     
/*  47 */     startBuffer();
/*  48 */     GL11.glEnableClientState(32884);
/*  49 */     GL11.glEnableClientState(32888);
/*  50 */     GL11.glEnableClientState(32886);
/*     */   }
/*     */ 
/*     */   
/*     */   private void startBuffer() {
/*  55 */     this.vertIndex = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private void flushBuffer() {
/*  60 */     if (this.vertIndex == 0) {
/*     */       return;
/*     */     }
/*  63 */     if (this.currentType == -1) {
/*     */       return;
/*     */     }
/*     */     
/*  67 */     if (this.vertIndex < 20) {
/*  68 */       GL11.glBegin(this.currentType);
/*  69 */       for (int i = 0; i < this.vertIndex; i++) {
/*  70 */         GL11.glColor4f(this.cols[(i << 2) + 0], this.cols[(i << 2) + 1], this.cols[(i << 2) + 2], this.cols[(i << 2) + 3]);
/*  71 */         GL11.glTexCoord2f(this.texs[(i << 1) + 0], this.texs[(i << 1) + 1]);
/*  72 */         GL11.glVertex3f(this.verts[i * 3 + 0], this.verts[i * 3 + 1], this.verts[i * 3 + 2]);
/*     */       } 
/*  74 */       GL11.glEnd();
/*  75 */       this.currentType = -1;
/*     */       return;
/*     */     } 
/*  78 */     this.vertices.clear();
/*  79 */     this.colors.clear();
/*  80 */     this.textures.clear();
/*     */     
/*  82 */     this.vertices.put(this.verts, 0, this.vertIndex * 3);
/*  83 */     this.colors.put(this.cols, 0, this.vertIndex << 2);
/*  84 */     this.textures.put(this.texs, 0, this.vertIndex << 1);
/*     */     
/*  86 */     this.vertices.flip();
/*  87 */     this.colors.flip();
/*  88 */     this.textures.flip();
/*     */     
/*  90 */     GL11.glVertexPointer(3, 0, this.vertices);
/*  91 */     GL11.glColorPointer(4, 0, this.colors);
/*  92 */     GL11.glTexCoordPointer(2, 0, this.textures);
/*     */     
/*  94 */     GL11.glDrawArrays(this.currentType, 0, this.vertIndex);
/*  95 */     this.currentType = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private void applyBuffer() {
/* 100 */     if (this.listMode > 0) {
/*     */       return;
/*     */     }
/*     */     
/* 104 */     if (this.vertIndex != 0) {
/* 105 */       flushBuffer();
/* 106 */       startBuffer();
/*     */     } 
/*     */     
/* 109 */     super.glColor4f(this.color[0], this.color[1], this.color[2], this.color[3]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() {
/* 114 */     super.flush();
/*     */     
/* 116 */     applyBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public void glBegin(int geomType) {
/* 121 */     if (this.listMode > 0) {
/* 122 */       super.glBegin(geomType);
/*     */       
/*     */       return;
/*     */     } 
/* 126 */     if (this.currentType != geomType) {
/* 127 */       applyBuffer();
/* 128 */       this.currentType = geomType;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void glColor4f(float r, float g, float b, float a) {
/* 134 */     a *= this.alphaScale;
/*     */     
/* 136 */     this.color[0] = r;
/* 137 */     this.color[1] = g;
/* 138 */     this.color[2] = b;
/* 139 */     this.color[3] = a;
/*     */     
/* 141 */     if (this.listMode > 0) {
/* 142 */       super.glColor4f(r, g, b, a);
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void glEnd() {
/* 149 */     if (this.listMode > 0) {
/* 150 */       super.glEnd();
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void glTexCoord2f(float u, float v) {
/* 157 */     if (this.listMode > 0) {
/* 158 */       super.glTexCoord2f(u, v);
/*     */       
/*     */       return;
/*     */     } 
/* 162 */     this.tex[0] = u;
/* 163 */     this.tex[1] = v;
/*     */   }
/*     */ 
/*     */   
/*     */   public void glVertex2f(float x, float y) {
/* 168 */     if (this.listMode > 0) {
/* 169 */       super.glVertex2f(x, y);
/*     */       
/*     */       return;
/*     */     } 
/* 173 */     glVertex3f(x, y, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void glVertex3f(float x, float y, float z) {
/* 178 */     if (this.listMode > 0) {
/* 179 */       super.glVertex3f(x, y, z);
/*     */       
/*     */       return;
/*     */     } 
/* 183 */     this.verts[this.vertIndex * 3 + 0] = x;
/* 184 */     this.verts[this.vertIndex * 3 + 1] = y;
/* 185 */     this.verts[this.vertIndex * 3 + 2] = z;
/* 186 */     this.cols[(this.vertIndex << 2) + 0] = this.color[0];
/* 187 */     this.cols[(this.vertIndex << 2) + 1] = this.color[1];
/* 188 */     this.cols[(this.vertIndex << 2) + 2] = this.color[2];
/* 189 */     this.cols[(this.vertIndex << 2) + 3] = this.color[3];
/* 190 */     this.texs[(this.vertIndex << 1) + 0] = this.tex[0];
/* 191 */     this.texs[(this.vertIndex << 1) + 1] = this.tex[1];
/* 192 */     this.vertIndex++;
/*     */     
/* 194 */     if (this.vertIndex > 4950 && 
/* 195 */       isSplittable(this.vertIndex, this.currentType)) {
/* 196 */       int type = this.currentType;
/* 197 */       applyBuffer();
/* 198 */       this.currentType = type;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSplittable(int count, int type) {
/* 205 */     switch (type) {
/*     */       case 7:
/* 207 */         return (count % 4 == 0);
/*     */       case 4:
/* 209 */         return (count % 3 == 0);
/*     */       case 6913:
/* 211 */         return (count % 2 == 0);
/*     */     } 
/*     */     
/* 214 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void glBindTexture(int target, int id) {
/* 219 */     applyBuffer();
/* 220 */     super.glBindTexture(target, id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void glBlendFunc(int src, int dest) {
/* 225 */     applyBuffer();
/* 226 */     super.glBlendFunc(src, dest);
/*     */   }
/*     */ 
/*     */   
/*     */   public void glCallList(int id) {
/* 231 */     applyBuffer();
/* 232 */     super.glCallList(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void glClear(int value) {
/* 237 */     applyBuffer();
/* 238 */     super.glClear(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void glClipPlane(int plane, DoubleBuffer buffer) {
/* 243 */     applyBuffer();
/* 244 */     super.glClipPlane(plane, buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
/* 249 */     applyBuffer();
/* 250 */     super.glColorMask(red, green, blue, alpha);
/*     */   }
/*     */ 
/*     */   
/*     */   public void glDisable(int item) {
/* 255 */     applyBuffer();
/* 256 */     super.glDisable(item);
/*     */   }
/*     */ 
/*     */   
/*     */   public void glEnable(int item) {
/* 261 */     applyBuffer();
/* 262 */     super.glEnable(item);
/*     */   }
/*     */ 
/*     */   
/*     */   public void glLineWidth(float width) {
/* 267 */     applyBuffer();
/* 268 */     super.glLineWidth(width);
/*     */   }
/*     */ 
/*     */   
/*     */   public void glPointSize(float size) {
/* 273 */     applyBuffer();
/* 274 */     super.glPointSize(size);
/*     */   }
/*     */ 
/*     */   
/*     */   public void glPopMatrix() {
/* 279 */     applyBuffer();
/* 280 */     super.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void glPushMatrix() {
/* 285 */     applyBuffer();
/* 286 */     super.glPushMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void glRotatef(float angle, float x, float y, float z) {
/* 291 */     applyBuffer();
/* 292 */     super.glRotatef(angle, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void glScalef(float x, float y, float z) {
/* 297 */     applyBuffer();
/* 298 */     super.glScalef(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void glScissor(int x, int y, int width, int height) {
/* 303 */     applyBuffer();
/* 304 */     super.glScissor(x, y, width, height);
/*     */   }
/*     */ 
/*     */   
/*     */   public void glTexEnvi(int target, int mode, int value) {
/* 309 */     applyBuffer();
/* 310 */     super.glTexEnvi(target, mode, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void glTranslatef(float x, float y, float z) {
/* 315 */     applyBuffer();
/* 316 */     super.glTranslatef(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void glEndList() {
/* 321 */     this.listMode--;
/* 322 */     super.glEndList();
/*     */   }
/*     */ 
/*     */   
/*     */   public void glNewList(int id, int option) {
/* 327 */     this.listMode++;
/* 328 */     super.glNewList(id, option);
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] getCurrentColor() {
/* 333 */     return this.color;
/*     */   }
/*     */ 
/*     */   
/*     */   public void glLoadMatrix(FloatBuffer buffer) {
/* 338 */     flushBuffer();
/* 339 */     super.glLoadMatrix(buffer);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\opengl\renderer\VAOGLRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */