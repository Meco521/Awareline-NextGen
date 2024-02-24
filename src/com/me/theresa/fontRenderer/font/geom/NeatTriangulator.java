/*     */ package com.me.theresa.fontRenderer.font.geom;
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
/*     */ public class NeatTriangulator
/*     */   implements Triangulator
/*     */ {
/*     */   static final float EPSILON = 1.0E-6F;
/*     */   private float[] pointsX;
/*     */   private float[] pointsY;
/*     */   private int numPoints;
/*     */   private Edge[] edges;
/*     */   private int[] V;
/*     */   private int numEdges;
/*     */   private Triangle[] triangles;
/*     */   private int numTriangles;
/*  24 */   private float offset = 1.0E-6F;
/*     */ 
/*     */   
/*     */   public NeatTriangulator() {
/*  28 */     this.pointsX = new float[100];
/*  29 */     this.pointsY = new float[100];
/*  30 */     this.numPoints = 0;
/*  31 */     this.edges = new Edge[100];
/*  32 */     this.numEdges = 0;
/*  33 */     this.triangles = new Triangle[100];
/*  34 */     this.numTriangles = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  39 */     this.numPoints = 0;
/*  40 */     this.numEdges = 0;
/*  41 */     this.numTriangles = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private int findEdge(int i, int j) {
/*     */     int k;
/*     */     int l;
/*  48 */     if (i < j) {
/*  49 */       k = i;
/*  50 */       l = j;
/*     */     } else {
/*  52 */       k = j;
/*  53 */       l = i;
/*     */     } 
/*  55 */     for (int i1 = 0; i1 < this.numEdges; i1++) {
/*  56 */       if ((this.edges[i1]).v0 == k && (this.edges[i1]).v1 == l)
/*  57 */         return i1; 
/*     */     } 
/*  59 */     return -1;
/*     */   }
/*     */   private void addEdge(int i, int j, int k) {
/*     */     int m, n;
/*     */     Edge edge;
/*  64 */     int l, i1, l1 = findEdge(i, j);
/*     */ 
/*     */ 
/*     */     
/*  68 */     if (l1 < 0) {
/*  69 */       if (this.numEdges == this.edges.length) {
/*  70 */         Edge[] aedge = new Edge[this.edges.length << 1];
/*  71 */         System.arraycopy(this.edges, 0, aedge, 0, this.numEdges);
/*  72 */         this.edges = aedge;
/*     */       } 
/*  74 */       m = -1;
/*  75 */       n = -1;
/*  76 */       l1 = this.numEdges++;
/*  77 */       edge = this.edges[l1] = new Edge();
/*     */     } else {
/*  79 */       edge = this.edges[l1];
/*  80 */       m = edge.t0;
/*  81 */       n = edge.t1;
/*     */     } 
/*     */ 
/*     */     
/*  85 */     if (i < j) {
/*  86 */       l = i;
/*  87 */       i1 = j;
/*  88 */       m = k;
/*     */     } else {
/*  90 */       l = j;
/*  91 */       i1 = i;
/*  92 */       n = k;
/*     */     } 
/*  94 */     edge.v0 = l;
/*  95 */     edge.v1 = i1;
/*  96 */     edge.t0 = m;
/*  97 */     edge.t1 = n;
/*  98 */     edge.suspect = true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void deleteEdge(int i, int j) throws InternalException {
/*     */     int k;
/* 104 */     if (0 > (k = findEdge(i, j))) {
/* 105 */       throw new InternalException("Attempt to delete unknown edge");
/*     */     }
/* 107 */     this.edges[k] = this.edges[--this.numEdges];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void markSuspect(int i, int j, boolean flag) throws InternalException {
/*     */     int k;
/* 115 */     if (0 > (k = findEdge(i, j))) {
/* 116 */       throw new InternalException("Attempt to mark unknown edge");
/*     */     }
/* 118 */     (this.edges[k]).suspect = flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Edge chooseSuspect() {
/* 125 */     for (int i = 0; i < this.numEdges; i++) {
/* 126 */       Edge edge = this.edges[i];
/* 127 */       if (edge.suspect) {
/* 128 */         edge.suspect = false;
/* 129 */         if (edge.t0 >= 0 && edge.t1 >= 0) {
/* 130 */           return edge;
/*     */         }
/*     */       } 
/*     */     } 
/* 134 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static float rho(float f, float f1, float f2, float f3, float f4, float f5) {
/* 139 */     float f6 = f4 - f2;
/* 140 */     float f7 = f5 - f3;
/* 141 */     float f8 = f - f4;
/* 142 */     float f9 = f1 - f5;
/* 143 */     float f18 = f6 * f9 - f7 * f8;
/* 144 */     if (f18 > 0.0F) {
/* 145 */       if (f18 < 1.0E-6F)
/* 146 */         f18 = 1.0E-6F; 
/* 147 */       float f12 = f6 * f6;
/* 148 */       float f13 = f7 * f7;
/* 149 */       float f14 = f8 * f8;
/* 150 */       float f15 = f9 * f9;
/* 151 */       float f10 = f2 - f;
/* 152 */       float f11 = f3 - f1;
/* 153 */       float f16 = f10 * f10;
/* 154 */       float f17 = f11 * f11;
/* 155 */       return (f12 + f13) * (f14 + f15) * (f16 + f17) / f18 * f18;
/*     */     } 
/* 157 */     return -1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean insideTriangle(float f, float f1, float f2, float f3, float f4, float f5, float f6, float f7) {
/* 163 */     float f8 = f4 - f2;
/* 164 */     float f9 = f5 - f3;
/* 165 */     float f10 = f - f4;
/* 166 */     float f11 = f1 - f5;
/* 167 */     float f12 = f2 - f;
/* 168 */     float f13 = f3 - f1;
/* 169 */     float f14 = f6 - f;
/* 170 */     float f15 = f7 - f1;
/* 171 */     float f16 = f6 - f2;
/* 172 */     float f17 = f7 - f3;
/* 173 */     float f18 = f6 - f4;
/* 174 */     float f19 = f7 - f5;
/* 175 */     float f22 = f8 * f17 - f9 * f16;
/* 176 */     float f20 = f12 * f15 - f13 * f14;
/* 177 */     float f21 = f10 * f19 - f11 * f18;
/* 178 */     return (f22 >= 0.0D && f21 >= 0.0D && f20 >= 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean snip(int i, int j, int k, int l) {
/* 183 */     float f = this.pointsX[this.V[i]];
/* 184 */     float f1 = this.pointsY[this.V[i]];
/* 185 */     float f2 = this.pointsX[this.V[j]];
/* 186 */     float f3 = this.pointsY[this.V[j]];
/* 187 */     float f4 = this.pointsX[this.V[k]];
/* 188 */     float f5 = this.pointsY[this.V[k]];
/* 189 */     if (1.0E-6F > (f2 - f) * (f5 - f1) - (f3 - f1) * (f4 - f))
/* 190 */       return false; 
/* 191 */     for (int i1 = 0; i1 < l; i1++) {
/* 192 */       if (i1 != i && i1 != j && i1 != k) {
/* 193 */         float f6 = this.pointsX[this.V[i1]];
/* 194 */         float f7 = this.pointsY[this.V[i1]];
/* 195 */         if (insideTriangle(f, f1, f2, f3, f4, f5, f6, f7))
/* 196 */           return false; 
/*     */       } 
/*     */     } 
/* 199 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private float area() {
/* 204 */     float f = 0.0F;
/* 205 */     int i = this.numPoints - 1;
/* 206 */     for (int j = 0; j < this.numPoints; ) {
/* 207 */       f += this.pointsX[i] * this.pointsY[j] - this.pointsY[i] * this.pointsX[j];
/* 208 */       i = j++;
/*     */     } 
/*     */     
/* 211 */     return f * 0.5F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void basicTriangulation() throws InternalException {
/* 216 */     int i = this.numPoints;
/* 217 */     if (i < 3)
/*     */       return; 
/* 219 */     this.numEdges = 0;
/* 220 */     this.numTriangles = 0;
/* 221 */     this.V = new int[i];
/*     */     
/* 223 */     if (0.0D < area()) {
/* 224 */       for (int k = 0; k < i; k++) {
/* 225 */         this.V[k] = k;
/*     */       }
/*     */     } else {
/* 228 */       for (int l = 0; l < i; l++) {
/* 229 */         this.V[l] = this.numPoints - 1 - l;
/*     */       }
/*     */     } 
/* 232 */     int k1 = 2 * i;
/* 233 */     int i1 = i - 1;
/* 234 */     while (i > 2) {
/* 235 */       if (0 >= k1--) {
/* 236 */         throw new InternalException("Bad polygon");
/*     */       }
/*     */       
/* 239 */       int j = i1;
/* 240 */       if (i <= j)
/* 241 */         j = 0; 
/* 242 */       i1 = j + 1;
/* 243 */       if (i <= i1)
/* 244 */         i1 = 0; 
/* 245 */       int j1 = i1 + 1;
/* 246 */       if (i <= j1)
/* 247 */         j1 = 0; 
/* 248 */       if (snip(j, i1, j1, i)) {
/* 249 */         int l1 = this.V[j];
/* 250 */         int i2 = this.V[i1];
/* 251 */         int j2 = this.V[j1];
/* 252 */         if (this.numTriangles == this.triangles.length) {
/* 253 */           Triangle[] atriangle = new Triangle[this.triangles.length << 1];
/* 254 */           System.arraycopy(this.triangles, 0, atriangle, 0, this.numTriangles);
/* 255 */           this.triangles = atriangle;
/*     */         } 
/* 257 */         this.triangles[this.numTriangles] = new Triangle(l1, i2, j2);
/* 258 */         addEdge(l1, i2, this.numTriangles);
/* 259 */         addEdge(i2, j2, this.numTriangles);
/* 260 */         addEdge(j2, l1, this.numTriangles);
/* 261 */         this.numTriangles++;
/* 262 */         int k2 = i1;
/* 263 */         for (int l2 = i1 + 1; l2 < i; l2++) {
/* 264 */           this.V[k2] = this.V[l2];
/* 265 */           k2++;
/*     */         } 
/*     */         
/* 268 */         i--;
/* 269 */         k1 = 2 * i;
/*     */       } 
/*     */     } 
/* 272 */     this.V = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void optimize() throws InternalException {
/*     */     Edge edge;
/* 279 */     while ((edge = chooseSuspect()) != null) {
/*     */ 
/*     */       
/* 282 */       int i1 = edge.v0;
/* 283 */       int k1 = edge.v1;
/* 284 */       int i = edge.t0;
/* 285 */       int j = edge.t1;
/* 286 */       int j1 = -1;
/* 287 */       int l1 = -1;
/* 288 */       for (int k = 0; k < 3; ) {
/* 289 */         int i2 = (this.triangles[i]).v[k];
/* 290 */         if (i1 == i2 || k1 == i2) {
/*     */           k++; continue;
/*     */         } 
/* 293 */         l1 = i2;
/*     */       } 
/*     */ 
/*     */       
/* 297 */       for (int l = 0; l < 3; ) {
/* 298 */         int j2 = (this.triangles[j]).v[l];
/* 299 */         if (i1 == j2 || k1 == j2) {
/*     */           l++; continue;
/*     */         } 
/* 302 */         j1 = j2;
/*     */       } 
/*     */ 
/*     */       
/* 306 */       if (-1 == j1 || -1 == l1) {
/* 307 */         throw new InternalException("can't find quad");
/*     */       }
/*     */       
/* 310 */       float f = this.pointsX[i1];
/* 311 */       float f1 = this.pointsY[i1];
/* 312 */       float f2 = this.pointsX[j1];
/* 313 */       float f3 = this.pointsY[j1];
/* 314 */       float f4 = this.pointsX[k1];
/* 315 */       float f5 = this.pointsY[k1];
/* 316 */       float f6 = this.pointsX[l1];
/* 317 */       float f7 = this.pointsY[l1];
/* 318 */       float f8 = rho(f, f1, f2, f3, f4, f5);
/* 319 */       float f9 = rho(f, f1, f4, f5, f6, f7);
/* 320 */       float f10 = rho(f2, f3, f4, f5, f6, f7);
/* 321 */       float f11 = rho(f2, f3, f6, f7, f, f1);
/* 322 */       if (0.0F > f8 || 0.0F > f9) {
/* 323 */         throw new InternalException("original triangles backwards");
/*     */       }
/* 325 */       if (0.0F <= f10 && 0.0F <= f11) {
/* 326 */         if (f8 > f9) {
/* 327 */           f8 = f9;
/*     */         }
/* 329 */         if (f10 > f11) {
/* 330 */           f10 = f11;
/*     */         }
/* 332 */         if (f8 > f10) {
/* 333 */           deleteEdge(i1, k1);
/* 334 */           (this.triangles[i]).v[0] = j1;
/* 335 */           (this.triangles[i]).v[1] = k1;
/* 336 */           (this.triangles[i]).v[2] = l1;
/* 337 */           (this.triangles[j]).v[0] = j1;
/* 338 */           (this.triangles[j]).v[1] = l1;
/* 339 */           (this.triangles[j]).v[2] = i1;
/* 340 */           addEdge(j1, k1, i);
/* 341 */           addEdge(k1, l1, i);
/* 342 */           addEdge(l1, j1, i);
/* 343 */           addEdge(l1, i1, j);
/* 344 */           addEdge(i1, j1, j);
/* 345 */           addEdge(j1, l1, j);
/* 346 */           markSuspect(j1, l1, false);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean triangulate() {
/*     */     try {
/* 355 */       basicTriangulation();
/*     */       
/* 357 */       return true;
/* 358 */     } catch (InternalException e) {
/* 359 */       this.numEdges = 0;
/*     */       
/* 361 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addPolyPoint(float x, float y) {
/* 366 */     for (int i = 0; i < this.numPoints; i++) {
/* 367 */       if (this.pointsX[i] == x && this.pointsY[i] == y) {
/*     */         
/* 369 */         y += this.offset;
/* 370 */         this.offset += 1.0E-6F;
/*     */       } 
/*     */     } 
/*     */     
/* 374 */     if (this.numPoints == this.pointsX.length) {
/* 375 */       float[] af = new float[this.numPoints << 1];
/* 376 */       System.arraycopy(this.pointsX, 0, af, 0, this.numPoints);
/* 377 */       this.pointsX = af;
/* 378 */       af = new float[this.numPoints << 1];
/* 379 */       System.arraycopy(this.pointsY, 0, af, 0, this.numPoints);
/* 380 */       this.pointsY = af;
/*     */     } 
/*     */     
/* 383 */     this.pointsX[this.numPoints] = x;
/* 384 */     this.pointsY[this.numPoints] = y;
/* 385 */     this.numPoints++;
/*     */   }
/*     */ 
/*     */   
/*     */   class Triangle
/*     */   {
/*     */     int[] v;
/*     */ 
/*     */     
/*     */     Triangle(int i, int j, int k) {
/* 395 */       this.v = new int[3];
/* 396 */       this.v[0] = i;
/* 397 */       this.v[1] = j;
/* 398 */       this.v[2] = k;
/*     */     }
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
/*     */   
/*     */   class Edge
/*     */   {
/* 417 */     int v0 = -1;
/* 418 */     int v1 = -1;
/* 419 */     int t0 = -1;
/* 420 */     int t1 = -1;
/*     */     boolean suspect;
/*     */   }
/*     */   
/*     */   class InternalException
/*     */     extends Exception
/*     */   {
/*     */     public InternalException(String msg) {
/* 428 */       super(msg);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTriangleCount() {
/* 434 */     return this.numTriangles;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] getTrianglePoint(int tri, int i) {
/* 439 */     float xp = this.pointsX[(this.triangles[tri]).v[i]];
/* 440 */     float yp = this.pointsY[(this.triangles[tri]).v[i]];
/*     */     
/* 442 */     return new float[] { xp, yp };
/*     */   }
/*     */   
/*     */   public void startHole() {}
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\geom\NeatTriangulator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */