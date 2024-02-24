/*    */ package awareline.main.utility.vec;
/*    */ 
/*    */ import awareline.main.utility.render.gl.GLUtils;
/*    */ 
/*    */ public final class Vec2f {
/*    */   private float x;
/*    */   private float y;
/*    */   
/*  9 */   public float getX() { return this.x; } public float getY() {
/* 10 */     return this.y;
/*    */   }
/*    */   public Vec2f() {
/* 13 */     this(0.0F, 0.0F);
/*    */   }
/*    */   
/*    */   public Vec2f(Vec2f vec) {
/* 17 */     this(vec.x, vec.y);
/*    */   }
/*    */   
/*    */   public Vec2f(double x, double y) {
/* 21 */     this((float)x, (float)y);
/*    */   }
/*    */   
/*    */   public Vec2f(float x, float y) {
/* 25 */     this.x = x;
/* 26 */     this.y = y;
/*    */   }
/*    */   
/*    */   public Vec2f setX(float x) {
/* 30 */     this.x = x;
/* 31 */     return this;
/*    */   }
/*    */   
/*    */   public Vec2f setY(float y) {
/* 35 */     this.y = y;
/* 36 */     return this;
/*    */   }
/*    */   
/*    */   public Vec2f add(Vec2f vec) {
/* 40 */     return new Vec2f(this.x + vec.x, this.y + vec.y);
/*    */   }
/*    */   
/*    */   public Vec2f add(double x, double y) {
/* 44 */     return add(new Vec2f(x, y));
/*    */   }
/*    */   
/*    */   public Vec2f add(float x, float y) {
/* 48 */     return add(new Vec2f(x, y));
/*    */   }
/*    */   
/*    */   public Vec2f sub(Vec2f vec) {
/* 52 */     return new Vec2f(this.x - vec.x, this.y - vec.y);
/*    */   }
/*    */   
/*    */   public Vec2f sub(double x, double y) {
/* 56 */     return sub(new Vec2f(x, y));
/*    */   }
/*    */   
/*    */   public Vec2f sub(float x, float y) {
/* 60 */     return sub(new Vec2f(x, y));
/*    */   }
/*    */   
/*    */   public Vec2f scale(float scale) {
/* 64 */     return new Vec2f(this.x * scale, this.y * scale);
/*    */   }
/*    */   
/*    */   public Vec3f toVec3() {
/* 68 */     return new Vec3f(this.x, this.y, 0.0D);
/*    */   }
/*    */   
/*    */   public Vec2f copy() {
/* 72 */     return new Vec2f(this);
/*    */   }
/*    */   
/*    */   public Vec2f transfer(Vec2f vec) {
/* 76 */     this.x = vec.x;
/* 77 */     this.y = vec.y;
/* 78 */     return this;
/*    */   }
/*    */   
/*    */   public float distanceTo(Vec2f vec) {
/* 82 */     double dx = (this.x - vec.x);
/* 83 */     double dy = (this.y - vec.y);
/* 84 */     return (float)Math.sqrt(dx * dx + dy * dy);
/*    */   }
/*    */   
/*    */   public Vec3f toScreen() {
/* 88 */     return GLUtils.toWorld(toVec3());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 92 */     return "Vec2{x=" + this.x + ", y=" + this.y + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\vec\Vec2f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */