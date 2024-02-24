/*     */ package com.me.theresa.fontRenderer.font.geom;
/*     */ 
/*     */ import com.me.theresa.fontRenderer.font.log.FastTrig;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Vector2f
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1339934L;
/*     */   public float x;
/*     */   public float y;
/*     */   
/*     */   public strictfp Vector2f() {}
/*     */   
/*     */   public strictfp Vector2f(float[] coords) {
/*  22 */     this.x = coords[0];
/*  23 */     this.y = coords[1];
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp Vector2f(double theta) {
/*  28 */     this.x = 1.0F;
/*  29 */     this.y = 0.0F;
/*  30 */     setTheta(theta);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp void setTheta(double theta) {
/*  37 */     if (theta < -360.0D || theta > 360.0D) {
/*  38 */       theta %= 360.0D;
/*     */     }
/*  40 */     if (theta < 0.0D) {
/*  41 */       theta = 360.0D + theta;
/*     */     }
/*  43 */     double oldTheta = getTheta();
/*  44 */     if (theta < -360.0D || theta > 360.0D) {
/*  45 */       oldTheta %= 360.0D;
/*     */     }
/*  47 */     if (theta < 0.0D) {
/*  48 */       oldTheta = 360.0D + oldTheta;
/*     */     }
/*     */     
/*  51 */     float len = length();
/*  52 */     this.x = len * (float)FastTrig.cos(StrictMath.toRadians(theta));
/*  53 */     this.y = len * (float)FastTrig.sin(StrictMath.toRadians(theta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp Vector2f add(double theta) {
/*  63 */     setTheta(getTheta() + theta);
/*     */     
/*  65 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp Vector2f sub(double theta) {
/*  70 */     setTheta(getTheta() - theta);
/*     */     
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp double getTheta() {
/*  77 */     double theta = StrictMath.toDegrees(StrictMath.atan2(this.y, this.x));
/*  78 */     if (theta < -360.0D || theta > 360.0D) {
/*  79 */       theta %= 360.0D;
/*     */     }
/*  81 */     if (theta < 0.0D) {
/*  82 */       theta = 360.0D + theta;
/*     */     }
/*     */     
/*  85 */     return theta;
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp float getX() {
/*  90 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp float getY() {
/*  95 */     return this.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp Vector2f(Vector2f other) {
/* 100 */     this(other.x, other.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp Vector2f(float x, float y) {
/* 105 */     this.x = x;
/* 106 */     this.y = y;
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp void set(Vector2f other) {
/* 111 */     set(other.x, other.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp float dot(Vector2f other) {
/* 116 */     return this.x * other.x + this.y * other.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp Vector2f set(float x, float y) {
/* 121 */     this.x = x;
/* 122 */     this.y = y;
/*     */     
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp Vector2f getPerpendicular() {
/* 129 */     return new Vector2f(-this.y, this.x);
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp Vector2f set(float[] pt) {
/* 134 */     return set(pt[0], pt[1]);
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp Vector2f negate() {
/* 139 */     return new Vector2f(-this.x, -this.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp Vector2f negateLocal() {
/* 144 */     this.x = -this.x;
/* 145 */     this.y = -this.y;
/*     */     
/* 147 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp Vector2f add(Vector2f v) {
/* 152 */     this.x += v.x;
/* 153 */     this.y += v.y;
/*     */     
/* 155 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp Vector2f sub(Vector2f v) {
/* 160 */     this.x -= v.x;
/* 161 */     this.y -= v.y;
/*     */     
/* 163 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp Vector2f scale(float a) {
/* 168 */     this.x *= a;
/* 169 */     this.y *= a;
/*     */     
/* 171 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp Vector2f normalise() {
/* 176 */     float l = length();
/*     */     
/* 178 */     if (l == 0.0F) {
/* 179 */       return this;
/*     */     }
/*     */     
/* 182 */     this.x /= l;
/* 183 */     this.y /= l;
/* 184 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp Vector2f getNormal() {
/* 189 */     Vector2f cp = copy();
/* 190 */     cp.normalise();
/* 191 */     return cp;
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp float lengthSquared() {
/* 196 */     return this.x * this.x + this.y * this.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp float length() {
/* 201 */     return (float)Math.sqrt(lengthSquared());
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp void projectOntoUnit(Vector2f b, Vector2f result) {
/* 206 */     float dp = b.dot(this);
/*     */     
/* 208 */     result.x = dp * b.x;
/* 209 */     result.y = dp * b.y;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp Vector2f copy() {
/* 215 */     return new Vector2f(this.x, this.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp String toString() {
/* 220 */     return "[Vector2f " + this.x + "," + this.y + " (" + length() + ")]";
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp float distance(Vector2f other) {
/* 225 */     return (float)Math.sqrt(distanceSquared(other));
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp float distanceSquared(Vector2f other) {
/* 230 */     float dx = other.x - this.x;
/* 231 */     float dy = other.y - this.y;
/*     */     
/* 233 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp int hashCode() {
/* 238 */     return 997 * (int)this.x ^ 991 * (int)this.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public strictfp boolean equals(Object other) {
/* 243 */     if (other instanceof Vector2f) {
/* 244 */       Vector2f o = (Vector2f)other;
/* 245 */       return (o.x == this.x && o.y == this.y);
/*     */     } 
/*     */     
/* 248 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\geom\Vector2f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */