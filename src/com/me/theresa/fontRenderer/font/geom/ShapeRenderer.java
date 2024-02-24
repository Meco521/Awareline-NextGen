/*     */ package com.me.theresa.fontRenderer.font.geom;
/*     */ 
/*     */ import com.me.theresa.fontRenderer.font.Image;
/*     */ import com.me.theresa.fontRenderer.font.impl.ShapeFill;
/*     */ import com.me.theresa.fontRenderer.font.opengl.Texture;
/*     */ import com.me.theresa.fontRenderer.font.opengl.TextureImpl;
/*     */ import com.me.theresa.fontRenderer.font.opengl.renderer.LineStripRenderer;
/*     */ import com.me.theresa.fontRenderer.font.opengl.renderer.Renderer;
/*     */ import com.me.theresa.fontRenderer.font.opengl.renderer.SGL;
/*     */ 
/*     */ 
/*     */ public final class ShapeRenderer
/*     */ {
/*  14 */   static final SGL GL = Renderer.get();
/*     */   
/*  16 */   private static final LineStripRenderer LSR = Renderer.getLineStripRenderer();
/*     */ 
/*     */   
/*     */   public static final void draw(Shape shape) {
/*  20 */     Texture t = TextureImpl.getLastBind();
/*  21 */     TextureImpl.bindNone();
/*     */     
/*  23 */     float[] points = shape.getPoints();
/*     */     
/*  25 */     LSR.start();
/*  26 */     for (int i = 0; i < points.length; i += 2) {
/*  27 */       LSR.vertex(points[i], points[i + 1]);
/*     */     }
/*     */     
/*  30 */     if (shape.closed()) {
/*  31 */       LSR.vertex(points[0], points[1]);
/*     */     }
/*     */     
/*  34 */     LSR.end();
/*     */     
/*  36 */     if (t == null) {
/*  37 */       TextureImpl.bindNone();
/*     */     } else {
/*  39 */       t.bind();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static final void draw(Shape shape, ShapeFill fill) {
/*  45 */     float[] points = shape.getPoints();
/*     */     
/*  47 */     Texture t = TextureImpl.getLastBind();
/*  48 */     TextureImpl.bindNone();
/*     */     
/*  50 */     float[] center = shape.getCenter();
/*  51 */     GL.glBegin(3);
/*  52 */     for (int i = 0; i < points.length; i += 2) {
/*  53 */       fill.colorAt(shape, points[i], points[i + 1]).bind();
/*  54 */       Vector2f offset = fill.getOffsetAt(shape, points[i], points[i + 1]);
/*  55 */       GL.glVertex2f(points[i] + offset.x, points[i + 1] + offset.y);
/*     */     } 
/*     */     
/*  58 */     if (shape.closed()) {
/*  59 */       fill.colorAt(shape, points[0], points[1]).bind();
/*  60 */       Vector2f offset = fill.getOffsetAt(shape, points[0], points[1]);
/*  61 */       GL.glVertex2f(points[0] + offset.x, points[1] + offset.y);
/*     */     } 
/*  63 */     GL.glEnd();
/*     */     
/*  65 */     if (t == null) {
/*  66 */       TextureImpl.bindNone();
/*     */     } else {
/*  68 */       t.bind();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean validFill(Shape shape) {
/*  74 */     if (shape.getTriangles() == null) {
/*  75 */       return false;
/*     */     }
/*  77 */     return (shape.getTriangles().getTriangleCount() != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final void fill(Shape shape) {
/*  82 */     if (!validFill(shape)) {
/*     */       return;
/*     */     }
/*     */     
/*  86 */     Texture t = TextureImpl.getLastBind();
/*  87 */     TextureImpl.bindNone();
/*     */     
/*  89 */     fill(shape, new PointCallback()
/*     */         {
/*     */           public float[] preRenderPoint(Shape shape, float x, float y) {
/*  92 */             return null;
/*     */           }
/*     */         });
/*     */     
/*  96 */     if (t == null) {
/*  97 */       TextureImpl.bindNone();
/*     */     } else {
/*  99 */       t.bind();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static final void fill(Shape shape, PointCallback callback) {
/* 105 */     Triangulator tris = shape.getTriangles();
/*     */     
/* 107 */     GL.glBegin(4);
/* 108 */     for (int i = 0; i < tris.getTriangleCount(); i++) {
/* 109 */       for (int p = 0; p < 3; p++) {
/* 110 */         float[] pt = tris.getTrianglePoint(i, p);
/* 111 */         float[] np = callback.preRenderPoint(shape, pt[0], pt[1]);
/*     */         
/* 113 */         if (np == null) {
/* 114 */           GL.glVertex2f(pt[0], pt[1]);
/*     */         } else {
/* 116 */           GL.glVertex2f(np[0], np[1]);
/*     */         } 
/*     */       } 
/*     */     } 
/* 120 */     GL.glEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   public static final void texture(Shape shape, Image image) {
/* 125 */     texture(shape, image, 0.01F, 0.01F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final void textureFit(Shape shape, Image image) {
/* 130 */     textureFit(shape, image, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final void texture(Shape shape, final Image image, final float scaleX, final float scaleY) {
/* 135 */     if (!validFill(shape)) {
/*     */       return;
/*     */     }
/*     */     
/* 139 */     Texture t = TextureImpl.getLastBind();
/* 140 */     image.getTexture().bind();
/*     */     
/* 142 */     fill(shape, new PointCallback() {
/*     */           public float[] preRenderPoint(Shape shape, float x, float y) {
/* 144 */             float tx = x * scaleX;
/* 145 */             float ty = y * scaleY;
/*     */             
/* 147 */             tx = image.getTextureOffsetX() + image.getTextureWidth() * tx;
/* 148 */             ty = image.getTextureOffsetY() + image.getTextureHeight() * ty;
/*     */             
/* 150 */             ShapeRenderer.GL.glTexCoord2f(tx, ty);
/* 151 */             return null;
/*     */           }
/*     */         });
/*     */     
/* 155 */     float[] points = shape.getPoints();
/*     */     
/* 157 */     if (t == null) {
/* 158 */       TextureImpl.bindNone();
/*     */     } else {
/* 160 */       t.bind();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static final void textureFit(Shape shape, final Image image, final float scaleX, final float scaleY) {
/* 166 */     if (!validFill(shape)) {
/*     */       return;
/*     */     }
/*     */     
/* 170 */     float[] points = shape.getPoints();
/*     */     
/* 172 */     Texture t = TextureImpl.getLastBind();
/* 173 */     image.getTexture().bind();
/*     */     
/* 175 */     float minX = shape.getX();
/* 176 */     float minY = shape.getY();
/* 177 */     float maxX = shape.getMaxX() - minX;
/* 178 */     float maxY = shape.getMaxY() - minY;
/*     */     
/* 180 */     fill(shape, new PointCallback() {
/*     */           public float[] preRenderPoint(Shape shape, float x, float y) {
/* 182 */             x -= shape.getMinX();
/* 183 */             y -= shape.getMinY();
/*     */             
/* 185 */             x /= shape.getMaxX() - shape.getMinX();
/* 186 */             y /= shape.getMaxY() - shape.getMinY();
/*     */             
/* 188 */             float tx = x * scaleX;
/* 189 */             float ty = y * scaleY;
/*     */             
/* 191 */             tx = image.getTextureOffsetX() + image.getTextureWidth() * tx;
/* 192 */             ty = image.getTextureOffsetY() + image.getTextureHeight() * ty;
/*     */             
/* 194 */             ShapeRenderer.GL.glTexCoord2f(tx, ty);
/* 195 */             return null;
/*     */           }
/*     */         });
/*     */     
/* 199 */     if (t == null) {
/* 200 */       TextureImpl.bindNone();
/*     */     } else {
/* 202 */       t.bind();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static final void fill(Shape shape, final ShapeFill fill) {
/* 208 */     if (!validFill(shape)) {
/*     */       return;
/*     */     }
/*     */     
/* 212 */     Texture t = TextureImpl.getLastBind();
/* 213 */     TextureImpl.bindNone();
/*     */     
/* 215 */     float[] center = shape.getCenter();
/* 216 */     fill(shape, new PointCallback() {
/*     */           public float[] preRenderPoint(Shape shape, float x, float y) {
/* 218 */             fill.colorAt(shape, x, y).bind();
/* 219 */             Vector2f offset = fill.getOffsetAt(shape, x, y);
/*     */             
/* 221 */             return new float[] { offset.x + x, offset.y + y };
/*     */           }
/*     */         });
/*     */     
/* 225 */     if (t == null) {
/* 226 */       TextureImpl.bindNone();
/*     */     } else {
/* 228 */       t.bind();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static final void texture(Shape shape, final Image image, final float scaleX, final float scaleY, final ShapeFill fill) {
/* 234 */     if (!validFill(shape)) {
/*     */       return;
/*     */     }
/*     */     
/* 238 */     Texture t = TextureImpl.getLastBind();
/* 239 */     image.getTexture().bind();
/*     */     
/* 241 */     final float[] center = shape.getCenter();
/* 242 */     fill(shape, new PointCallback() {
/*     */           public float[] preRenderPoint(Shape shape, float x, float y) {
/* 244 */             fill.colorAt(shape, x - center[0], y - center[1]).bind();
/* 245 */             Vector2f offset = fill.getOffsetAt(shape, x, y);
/*     */             
/* 247 */             x += offset.x;
/* 248 */             y += offset.y;
/*     */             
/* 250 */             float tx = x * scaleX;
/* 251 */             float ty = y * scaleY;
/*     */             
/* 253 */             tx = image.getTextureOffsetX() + image.getTextureWidth() * tx;
/* 254 */             ty = image.getTextureOffsetY() + image.getTextureHeight() * ty;
/*     */             
/* 256 */             ShapeRenderer.GL.glTexCoord2f(tx, ty);
/*     */             
/* 258 */             return new float[] { offset.x + x, offset.y + y };
/*     */           }
/*     */         });
/*     */     
/* 262 */     if (t == null) {
/* 263 */       TextureImpl.bindNone();
/*     */     } else {
/* 265 */       t.bind();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static final void texture(Shape shape, Image image, final TexCoordGenerator gen) {
/* 270 */     Texture t = TextureImpl.getLastBind();
/*     */     
/* 272 */     image.getTexture().bind();
/*     */     
/* 274 */     float[] center = shape.getCenter();
/* 275 */     fill(shape, new PointCallback() {
/*     */           public float[] preRenderPoint(Shape shape, float x, float y) {
/* 277 */             Vector2f tex = gen.getCoordFor(x, y);
/* 278 */             ShapeRenderer.GL.glTexCoord2f(tex.x, tex.y);
/*     */             
/* 280 */             return new float[] { x, y };
/*     */           }
/*     */         });
/*     */     
/* 284 */     if (t == null) {
/* 285 */       TextureImpl.bindNone();
/*     */     } else {
/* 287 */       t.bind();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static interface PointCallback {
/*     */     float[] preRenderPoint(Shape param1Shape, float param1Float1, float param1Float2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\geom\ShapeRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */