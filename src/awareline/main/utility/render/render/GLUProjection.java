/*     */ package awareline.main.utility.render.render;
/*     */ 
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.util.glu.GLU;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GLUProjection
/*     */ {
/*     */   private static GLUProjection instance;
/*  19 */   private final FloatBuffer coords = BufferUtils.createFloatBuffer(3); private IntBuffer viewport; private FloatBuffer modelview;
/*     */   private FloatBuffer projection;
/*     */   private Vector3D frustumPos;
/*     */   
/*     */   public Vector3D[] getFrustum() {
/*  24 */     return this.frustum;
/*     */   }
/*     */   private Vector3D[] frustum; private Vector3D[] invFrustum;
/*     */   private Vector3D viewVec;
/*     */   private double displayWidth;
/*     */   private double displayHeight;
/*     */   private double widthScale;
/*     */   private double heightScale;
/*     */   private double bra;
/*     */   private double bla;
/*     */   private double tra;
/*     */   private double tla;
/*     */   private Line tb;
/*     */   private Line bb;
/*     */   private Line lb;
/*     */   private Line rb;
/*     */   
/*     */   public static GLUProjection getInstance() {
/*  42 */     if (instance == null) {
/*  43 */       instance = new GLUProjection();
/*     */     }
/*  45 */     return instance;
/*     */   }
/*     */   
/*     */   public Projection project(double x, double y, double z, ClampMode clampModeOutside, boolean extrudeInverted) {
/*  49 */     if (this.viewport == null || this.modelview == null || this.projection == null) {
/*  50 */       return new Projection(0.0D, 0.0D, Projection.Type.FAIL);
/*     */     }
/*  52 */     Vector3D posVec = new Vector3D(x, y, z);
/*  53 */     boolean[] frustum = doFrustumCheck(this.frustum, this.frustumPos, x, y, z);
/*  54 */     boolean outsideFrustum = (frustum[0] || frustum[1] || frustum[2] || frustum[3]);
/*  55 */     boolean bl = outsideFrustum;
/*  56 */     if (outsideFrustum) {
/*  57 */       boolean opposite = (posVec.sub(this.frustumPos).dot(this.viewVec) <= 0.0D);
/*  58 */       boolean[] invFrustum = doFrustumCheck(this.invFrustum, this.frustumPos, x, y, z);
/*  59 */       boolean outsideInvertedFrustum = (invFrustum[0] || invFrustum[1] || invFrustum[2] || invFrustum[3]);
/*  60 */       boolean bl2 = outsideInvertedFrustum;
/*  61 */       if (extrudeInverted && (!outsideInvertedFrustum || (outsideInvertedFrustum && clampModeOutside != ClampMode.NONE))) {
/*  62 */         if ((extrudeInverted && !outsideInvertedFrustum) || (clampModeOutside == ClampMode.DIRECT && outsideInvertedFrustum)) {
/*  63 */           double vecX = 0.0D;
/*  64 */           double vecY = 0.0D;
/*  65 */           if (!GLU.gluProject((float)x, (float)y, (float)z, this.modelview, this.projection, this.viewport, this.coords)) {
/*  66 */             return new Projection(0.0D, 0.0D, Projection.Type.FAIL);
/*     */           }
/*  68 */           if (opposite) {
/*  69 */             vecX = this.displayWidth * this.widthScale - this.coords.get(0) * this.widthScale - this.displayWidth * this.widthScale / 2.0D;
/*  70 */             vecY = this.displayHeight * this.heightScale - (this.displayHeight - this.coords.get(1)) * this.heightScale - this.displayHeight * this.heightScale / 2.0D;
/*     */           } else {
/*  72 */             vecX = this.coords.get(0) * this.widthScale - this.displayWidth * this.widthScale / 2.0D;
/*  73 */             vecY = (this.displayHeight - this.coords.get(1)) * this.heightScale - this.displayHeight * this.heightScale / 2.0D;
/*     */           } 
/*  75 */           Vector3D vec = (new Vector3D(vecX, vecY, 0.0D)).snormalize();
/*  76 */           vecX = vec.x;
/*  77 */           vecY = vec.y;
/*  78 */           Line vectorLine = new Line(this.displayWidth * this.widthScale / 2.0D, this.displayHeight * this.heightScale / 2.0D, 0.0D, vecX, vecY, 0.0D);
/*  79 */           double angle = Math.toDegrees(Math.acos(vec.y / Math.sqrt(vec.x * vec.x + vec.y * vec.y)));
/*  80 */           if (vecX < 0.0D) {
/*  81 */             angle = 360.0D - angle;
/*     */           }
/*  83 */           Vector3D intersect = new Vector3D(0.0D, 0.0D, 0.0D);
/*  84 */           intersect = (angle >= this.bra && angle < this.tra) ? this.rb.intersect(vectorLine) : ((angle >= this.tra && angle < this.tla) ? this.tb.intersect(vectorLine) : ((angle >= this.tla && angle < this.bla) ? this.lb.intersect(vectorLine) : this.bb.intersect(vectorLine)));
/*  85 */           return new Projection(intersect.x, intersect.y, outsideInvertedFrustum ? Projection.Type.OUTSIDE : Projection.Type.INVERTED);
/*     */         } 
/*  87 */         if (clampModeOutside != ClampMode.ORTHOGONAL || !outsideInvertedFrustum) {
/*  88 */           return new Projection(0.0D, 0.0D, Projection.Type.FAIL);
/*     */         }
/*  90 */         if (!GLU.gluProject((float)x, (float)y, (float)z, this.modelview, this.projection, this.viewport, this.coords)) {
/*  91 */           return new Projection(0.0D, 0.0D, Projection.Type.FAIL);
/*     */         }
/*  93 */         double d3 = this.coords.get(0) * this.widthScale;
/*  94 */         double d4 = (this.displayHeight - this.coords.get(1)) * this.heightScale;
/*  95 */         if (opposite) {
/*  96 */           d3 = this.displayWidth * this.widthScale - d3;
/*  97 */           d4 = this.displayHeight * this.heightScale - d4;
/*     */         } 
/*  99 */         if (d3 < 0.0D) {
/* 100 */           d3 = 0.0D;
/* 101 */         } else if (d3 > this.displayWidth * this.widthScale) {
/* 102 */           d3 = this.displayWidth * this.widthScale;
/*     */         } 
/* 104 */         if (d4 < 0.0D) {
/* 105 */           d4 = 0.0D;
/* 106 */           return new Projection(d3, d4, outsideInvertedFrustum ? Projection.Type.OUTSIDE : Projection.Type.INVERTED);
/*     */         } 
/* 108 */         if (d4 <= this.displayHeight * this.heightScale) {
/* 109 */           return new Projection(d3, d4, outsideInvertedFrustum ? Projection.Type.OUTSIDE : Projection.Type.INVERTED);
/*     */         }
/* 111 */         d4 = this.displayHeight * this.heightScale;
/* 112 */         return new Projection(d3, d4, outsideInvertedFrustum ? Projection.Type.OUTSIDE : Projection.Type.INVERTED);
/*     */       } 
/* 114 */       if (!GLU.gluProject((float)x, (float)y, (float)z, this.modelview, this.projection, this.viewport, this.coords)) {
/* 115 */         return new Projection(0.0D, 0.0D, Projection.Type.FAIL);
/*     */       }
/* 117 */       double d1 = this.coords.get(0) * this.widthScale;
/* 118 */       double d2 = (this.displayHeight - this.coords.get(1)) * this.heightScale;
/* 119 */       if (!opposite) {
/* 120 */         return new Projection(d1, d2, outsideInvertedFrustum ? Projection.Type.OUTSIDE : Projection.Type.INVERTED);
/*     */       }
/* 122 */       d1 = this.displayWidth * this.widthScale - d1;
/* 123 */       d2 = this.displayHeight * this.heightScale - d2;
/* 124 */       return new Projection(d1, d2, outsideInvertedFrustum ? Projection.Type.OUTSIDE : Projection.Type.INVERTED);
/*     */     } 
/* 126 */     if (!GLU.gluProject((float)x, (float)y, (float)z, this.modelview, this.projection, this.viewport, this.coords)) {
/* 127 */       return new Projection(0.0D, 0.0D, Projection.Type.FAIL);
/*     */     }
/* 129 */     double guiX = this.coords.get(0) * this.widthScale;
/* 130 */     double guiY = (this.displayHeight - this.coords.get(1)) * this.heightScale;
/* 131 */     return new Projection(guiX, guiY, Projection.Type.INSIDE);
/*     */   }
/*     */   
/*     */   public boolean[] doFrustumCheck(Vector3D[] frustumCorners, Vector3D frustumPos, double x, double y, double z) {
/* 135 */     Vector3D point = new Vector3D(x, y, z);
/* 136 */     boolean c1 = crossPlane(new Vector3D[] { frustumPos, frustumCorners[3], frustumCorners[0] }, point);
/* 137 */     boolean c2 = crossPlane(new Vector3D[] { frustumPos, frustumCorners[0], frustumCorners[1] }, point);
/* 138 */     boolean c3 = crossPlane(new Vector3D[] { frustumPos, frustumCorners[1], frustumCorners[2] }, point);
/* 139 */     boolean c4 = crossPlane(new Vector3D[] { frustumPos, frustumCorners[2], frustumCorners[3] }, point);
/* 140 */     return new boolean[] { c1, c2, c3, c4 };
/*     */   }
/*     */   
/*     */   public boolean crossPlane(Vector3D[] plane, Vector3D point) {
/* 144 */     Vector3D z = new Vector3D(0.0D, 0.0D, 0.0D);
/* 145 */     Vector3D e0 = plane[1].sub(plane[0]);
/* 146 */     Vector3D e1 = plane[2].sub(plane[0]);
/* 147 */     Vector3D normal = e0.cross(e1).snormalize();
/* 148 */     double D = z.sub(normal).dot(plane[2]);
/* 149 */     double dist = normal.dot(point) + D;
/* 150 */     return (dist >= 0.0D);
/*     */   }
/*     */   
/*     */   public static class Vector3D {
/*     */     public double x;
/*     */     public double y;
/*     */     public double z;
/*     */     
/*     */     public Vector3D(double x, double y, double z) {
/* 159 */       this.x = x;
/* 160 */       this.y = y;
/* 161 */       this.z = z;
/*     */     }
/*     */     
/*     */     public Vector3D add(Vector3D v) {
/* 165 */       return new Vector3D(this.x + v.x, this.y + v.y, this.z + v.z);
/*     */     }
/*     */     
/*     */     public Vector3D add(double x, double y, double z) {
/* 169 */       return new Vector3D(this.x + x, this.y + y, this.z + z);
/*     */     }
/*     */     
/*     */     public Vector3D sub(Vector3D v) {
/* 173 */       return new Vector3D(this.x - v.x, this.y - v.y, this.z - v.z);
/*     */     }
/*     */     
/*     */     public double dot(Vector3D v) {
/* 177 */       return this.x * v.x + this.y * v.y + this.z * v.z;
/*     */     }
/*     */     
/*     */     public Vector3D cross(Vector3D v) {
/* 181 */       return new Vector3D(this.y * v.z - this.z * v.y, this.z * v.x - this.x * v.z, this.x * v.y - this.y * v.x);
/*     */     }
/*     */     
/*     */     public double length() {
/* 185 */       return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/*     */     }
/*     */     
/*     */     public Vector3D snormalize() {
/* 189 */       double len = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/* 190 */       this.x /= len;
/* 191 */       this.y /= len;
/* 192 */       this.z /= len;
/* 193 */       return this;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 197 */       return "(X: " + this.x + " Y: " + this.y + " Z: " + this.z + ")";
/*     */     } }
/*     */   public static class Projection { private final double x;
/*     */     
/*     */     public double getX() {
/* 202 */       return this.x;
/*     */     } private final double y; private final Type t; public double getY() {
/* 204 */       return this.y;
/*     */     }
/*     */ 
/*     */     
/*     */     public Projection(double x, double y, Type t) {
/* 209 */       this.x = x;
/* 210 */       this.y = y;
/* 211 */       this.t = t;
/*     */     }
/*     */     
/*     */     public Type getType() {
/* 215 */       return this.t;
/*     */     }
/*     */     
/*     */     public enum Type {
/* 219 */       INSIDE,
/* 220 */       OUTSIDE,
/* 221 */       INVERTED,
/* 222 */       FAIL; } } public enum Type { INSIDE, OUTSIDE, INVERTED, FAIL; }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Line
/*     */   {
/* 228 */     public GLUProjection.Vector3D sourcePoint = new GLUProjection.Vector3D(0.0D, 0.0D, 0.0D);
/* 229 */     public GLUProjection.Vector3D direction = new GLUProjection.Vector3D(0.0D, 0.0D, 0.0D);
/*     */     
/*     */     public Line(double sx, double sy, double sz, double dx, double dy, double dz) {
/* 232 */       this.sourcePoint.x = sx;
/* 233 */       this.sourcePoint.y = sy;
/* 234 */       this.sourcePoint.z = sz;
/* 235 */       this.direction.x = dx;
/* 236 */       this.direction.y = dy;
/* 237 */       this.direction.z = dz;
/*     */     }
/*     */     
/*     */     public GLUProjection.Vector3D intersect(Line line) {
/* 241 */       double a = this.sourcePoint.x;
/* 242 */       double b = this.direction.x;
/* 243 */       double c = line.sourcePoint.x;
/* 244 */       double d = line.direction.x;
/* 245 */       double e = this.sourcePoint.y;
/* 246 */       double f = this.direction.y;
/* 247 */       double g = line.sourcePoint.y;
/* 248 */       double h = line.direction.y;
/* 249 */       double te = -a * h - c * h - d * (e - g);
/* 250 */       double be = b * h - d * f;
/* 251 */       if (be == 0.0D) {
/* 252 */         return intersectXZ(line);
/*     */       }
/* 254 */       double t = te / be;
/* 255 */       GLUProjection.Vector3D result = new GLUProjection.Vector3D(0.0D, 0.0D, 0.0D);
/* 256 */       this.sourcePoint.x += this.direction.x * t;
/* 257 */       this.sourcePoint.y += this.direction.y * t;
/* 258 */       this.sourcePoint.z += this.direction.z * t;
/* 259 */       return result;
/*     */     }
/*     */     
/*     */     private GLUProjection.Vector3D intersectXZ(Line line) {
/* 263 */       double a = this.sourcePoint.x;
/* 264 */       double b = this.direction.x;
/* 265 */       double c = line.sourcePoint.x;
/* 266 */       double d = line.direction.x;
/* 267 */       double e = this.sourcePoint.z;
/* 268 */       double f = this.direction.z;
/* 269 */       double g = line.sourcePoint.z;
/* 270 */       double h = line.direction.z;
/* 271 */       double te = -a * h - c * h - d * (e - g);
/* 272 */       double be = b * h - d * f;
/* 273 */       if (be == 0.0D) {
/* 274 */         return intersectYZ(line);
/*     */       }
/* 276 */       double t = te / be;
/* 277 */       GLUProjection.Vector3D result = new GLUProjection.Vector3D(0.0D, 0.0D, 0.0D);
/* 278 */       this.sourcePoint.x += this.direction.x * t;
/* 279 */       this.sourcePoint.y += this.direction.y * t;
/* 280 */       this.sourcePoint.z += this.direction.z * t;
/* 281 */       return result;
/*     */     }
/*     */     
/*     */     private GLUProjection.Vector3D intersectYZ(Line line) {
/* 285 */       double a = this.sourcePoint.y;
/* 286 */       double b = this.direction.y;
/* 287 */       double c = line.sourcePoint.y;
/* 288 */       double d = line.direction.y;
/* 289 */       double e = this.sourcePoint.z;
/* 290 */       double f = this.direction.z;
/* 291 */       double g = line.sourcePoint.z;
/* 292 */       double h = line.direction.z;
/* 293 */       double te = -a * h - c * h - d * (e - g);
/* 294 */       double be = b * h - d * f;
/* 295 */       if (be == 0.0D) {
/* 296 */         return null;
/*     */       }
/* 298 */       double t = te / be;
/* 299 */       GLUProjection.Vector3D result = new GLUProjection.Vector3D(0.0D, 0.0D, 0.0D);
/* 300 */       this.sourcePoint.x += this.direction.x * t;
/* 301 */       this.sourcePoint.y += this.direction.y * t;
/* 302 */       this.sourcePoint.z += this.direction.z * t;
/* 303 */       return result;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum ClampMode {
/* 308 */     ORTHOGONAL,
/* 309 */     DIRECT,
/* 310 */     NONE;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\render\GLUProjection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */