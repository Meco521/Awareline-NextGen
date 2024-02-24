/*      */ package com.me.theresa.fontRenderer.font;
/*      */ 
/*      */ import com.me.theresa.fontRenderer.font.geom.Rectangle;
/*      */ import com.me.theresa.fontRenderer.font.geom.Shape;
/*      */ import com.me.theresa.fontRenderer.font.geom.ShapeRenderer;
/*      */ import com.me.theresa.fontRenderer.font.impl.Font;
/*      */ import com.me.theresa.fontRenderer.font.impl.ShapeFill;
/*      */ import com.me.theresa.fontRenderer.font.log.FastTrig;
/*      */ import com.me.theresa.fontRenderer.font.log.Log;
/*      */ import com.me.theresa.fontRenderer.font.opengl.TextureImpl;
/*      */ import com.me.theresa.fontRenderer.font.opengl.renderer.LineStripRenderer;
/*      */ import com.me.theresa.fontRenderer.font.opengl.renderer.Renderer;
/*      */ import com.me.theresa.fontRenderer.font.opengl.renderer.SGL;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.DoubleBuffer;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import org.lwjgl.BufferUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Graphics
/*      */ {
/*   26 */   protected static SGL GL = Renderer.get();
/*      */   
/*   28 */   private static final LineStripRenderer LSR = Renderer.getLineStripRenderer();
/*      */   
/*   30 */   public static int MODE_NORMAL = 1;
/*      */   
/*   32 */   public static int MODE_ALPHA_MAP = 2;
/*      */   
/*   34 */   public static int MODE_ALPHA_BLEND = 3;
/*      */   
/*   36 */   public static int MODE_COLOR_MULTIPLY = 4;
/*      */   
/*   38 */   public static int MODE_ADD = 5;
/*      */   
/*   40 */   public static int MODE_SCREEN = 6;
/*      */   
/*      */   private static final int DEFAULT_SEGMENTS = 50;
/*      */   
/*   44 */   protected static Graphics currentGraphics = null;
/*      */   
/*      */   protected static Font DEFAULT_FONT;
/*      */   
/*   48 */   private float sx = 1.0F;
/*      */   
/*   50 */   private float sy = 1.0F;
/*      */   private Font font;
/*      */   
/*      */   public static void setCurrent(Graphics current) {
/*   54 */     if (currentGraphics != current) {
/*   55 */       if (currentGraphics != null) {
/*   56 */         currentGraphics.disable();
/*      */       }
/*   58 */       currentGraphics = current;
/*   59 */       currentGraphics.enable();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   67 */   private Color currentColor = Color.white;
/*      */ 
/*      */   
/*      */   protected int screenWidth;
/*      */ 
/*      */   
/*      */   protected int screenHeight;
/*      */ 
/*      */   
/*      */   private boolean pushed;
/*      */ 
/*      */   
/*      */   private Rectangle clip;
/*      */ 
/*      */   
/*   82 */   private final DoubleBuffer worldClip = BufferUtils.createDoubleBuffer(4);
/*      */ 
/*      */   
/*   85 */   private final ByteBuffer readBuffer = BufferUtils.createByteBuffer(4);
/*      */ 
/*      */   
/*      */   private boolean antialias;
/*      */ 
/*      */   
/*      */   private Rectangle worldClipRecord;
/*      */ 
/*      */   
/*   94 */   private int currentDrawingMode = MODE_NORMAL;
/*      */ 
/*      */   
/*   97 */   private float lineWidth = 1.0F;
/*      */ 
/*      */   
/*  100 */   private final ArrayList stack = new ArrayList();
/*      */ 
/*      */   
/*      */   private int stackIndex;
/*      */ 
/*      */   
/*      */   public Graphics() {}
/*      */ 
/*      */   
/*      */   public Graphics(int width, int height) {
/*  110 */     if (DEFAULT_FONT == null) {
/*  111 */       AccessController.doPrivileged(new PrivilegedAction() {
/*      */             public Object run() {
/*      */               try {
/*  114 */                 Graphics.DEFAULT_FONT = new AngelCodeFont("org/newdawn/slick/data/defaultfont.fnt", "org/newdawn/slick/data/defaultfont.png");
/*      */               
/*      */               }
/*  117 */               catch (SlickException e) {
/*  118 */                 Log.error(e);
/*      */               } 
/*  120 */               return null;
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*  125 */     this.font = DEFAULT_FONT;
/*  126 */     this.screenWidth = width;
/*  127 */     this.screenHeight = height;
/*      */   }
/*      */   
/*      */   void setDimensions(int width, int height) {
/*  131 */     this.screenWidth = width;
/*  132 */     this.screenHeight = height;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDrawMode(int mode) {
/*  137 */     predraw();
/*  138 */     this.currentDrawingMode = mode;
/*  139 */     if (this.currentDrawingMode == MODE_NORMAL) {
/*  140 */       GL.glEnable(3042);
/*  141 */       GL.glColorMask(true, true, true, true);
/*  142 */       GL.glBlendFunc(770, 771);
/*      */     } 
/*  144 */     if (this.currentDrawingMode == MODE_ALPHA_MAP) {
/*  145 */       GL.glDisable(3042);
/*  146 */       GL.glColorMask(false, false, false, true);
/*      */     } 
/*  148 */     if (this.currentDrawingMode == MODE_ALPHA_BLEND) {
/*  149 */       GL.glEnable(3042);
/*  150 */       GL.glColorMask(true, true, true, false);
/*  151 */       GL.glBlendFunc(772, 773);
/*      */     } 
/*  153 */     if (this.currentDrawingMode == MODE_COLOR_MULTIPLY) {
/*  154 */       GL.glEnable(3042);
/*  155 */       GL.glColorMask(true, true, true, true);
/*  156 */       GL.glBlendFunc(769, 768);
/*      */     } 
/*  158 */     if (this.currentDrawingMode == MODE_ADD) {
/*  159 */       GL.glEnable(3042);
/*  160 */       GL.glColorMask(true, true, true, true);
/*  161 */       GL.glBlendFunc(1, 1);
/*      */     } 
/*  163 */     if (this.currentDrawingMode == MODE_SCREEN) {
/*  164 */       GL.glEnable(3042);
/*  165 */       GL.glColorMask(true, true, true, true);
/*  166 */       GL.glBlendFunc(1, 769);
/*      */     } 
/*  168 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void clearAlphaMap() {
/*  173 */     pushTransform();
/*  174 */     GL.glLoadIdentity();
/*      */     
/*  176 */     int originalMode = this.currentDrawingMode;
/*  177 */     setDrawMode(MODE_ALPHA_MAP);
/*  178 */     setColor(new Color(0, 0, 0, 0));
/*  179 */     fillRect(0.0F, 0.0F, this.screenWidth, this.screenHeight);
/*  180 */     setColor(this.currentColor);
/*  181 */     setDrawMode(originalMode);
/*      */     
/*  183 */     popTransform();
/*      */   }
/*      */ 
/*      */   
/*      */   private void predraw() {
/*  188 */     setCurrent(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void postdraw() {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void enable() {}
/*      */ 
/*      */   
/*      */   public void flush() {
/*  201 */     if (currentGraphics == this) {
/*  202 */       currentGraphics.disable();
/*  203 */       currentGraphics = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void disable() {}
/*      */ 
/*      */   
/*      */   public Font getFont() {
/*  213 */     return this.font;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBackground(Color color) {
/*  218 */     predraw();
/*  219 */     GL.glClearColor(color.r, color.g, color.b, color.a);
/*  220 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public Color getBackground() {
/*  225 */     predraw();
/*  226 */     FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
/*  227 */     GL.glGetFloat(3106, buffer);
/*  228 */     postdraw();
/*      */     
/*  230 */     return new Color(buffer);
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  235 */     predraw();
/*  236 */     GL.glClear(16384);
/*  237 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void resetTransform() {
/*  242 */     this.sx = 1.0F;
/*  243 */     this.sy = 1.0F;
/*      */     
/*  245 */     if (this.pushed) {
/*  246 */       predraw();
/*  247 */       GL.glPopMatrix();
/*  248 */       this.pushed = false;
/*  249 */       postdraw();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkPush() {
/*  255 */     if (!this.pushed) {
/*  256 */       predraw();
/*  257 */       GL.glPushMatrix();
/*  258 */       this.pushed = true;
/*  259 */       postdraw();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void scale(float sx, float sy) {
/*  265 */     this.sx *= sx;
/*  266 */     this.sy *= sy;
/*      */     
/*  268 */     checkPush();
/*      */     
/*  270 */     predraw();
/*  271 */     GL.glScalef(sx, sy, 1.0F);
/*  272 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void rotate(float rx, float ry, float ang) {
/*  277 */     checkPush();
/*      */     
/*  279 */     predraw();
/*  280 */     translate(rx, ry);
/*  281 */     GL.glRotatef(ang, 0.0F, 0.0F, 1.0F);
/*  282 */     translate(-rx, -ry);
/*  283 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void translate(float x, float y) {
/*  288 */     checkPush();
/*      */     
/*  290 */     predraw();
/*  291 */     GL.glTranslatef(x, y, 0.0F);
/*  292 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setFont(Font font) {
/*  297 */     this.font = font;
/*      */   }
/*      */ 
/*      */   
/*      */   public void resetFont() {
/*  302 */     this.font = DEFAULT_FONT;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setColor(Color color) {
/*  307 */     if (color == null) {
/*      */       return;
/*      */     }
/*      */     
/*  311 */     this.currentColor = new Color(color);
/*  312 */     predraw();
/*  313 */     this.currentColor.bind();
/*  314 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public Color getColor() {
/*  319 */     return new Color(this.currentColor);
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawLine(float x1, float y1, float x2, float y2) {
/*  324 */     float lineWidth = this.lineWidth - 1.0F;
/*      */     
/*  326 */     if (LSR.applyGLLineFixes()) {
/*  327 */       if (x1 == x2) {
/*  328 */         if (y1 > y2) {
/*  329 */           float temp = y2;
/*  330 */           y2 = y1;
/*  331 */           y1 = temp;
/*      */         } 
/*  333 */         float step = 1.0F / this.sy;
/*  334 */         lineWidth /= this.sy;
/*  335 */         fillRect(x1 - lineWidth / 2.0F, y1 - lineWidth / 2.0F, lineWidth + step, y2 - y1 + lineWidth + step); return;
/*      */       } 
/*  337 */       if (y1 == y2) {
/*  338 */         if (x1 > x2) {
/*  339 */           float temp = x2;
/*  340 */           x2 = x1;
/*  341 */           x1 = temp;
/*      */         } 
/*  343 */         float step = 1.0F / this.sx;
/*  344 */         lineWidth /= this.sx;
/*  345 */         fillRect(x1 - lineWidth / 2.0F, y1 - lineWidth / 2.0F, x2 - x1 + lineWidth + step, lineWidth + step);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  350 */     predraw();
/*  351 */     this.currentColor.bind();
/*  352 */     TextureImpl.bindNone();
/*      */     
/*  354 */     LSR.start();
/*  355 */     LSR.vertex(x1, y1);
/*  356 */     LSR.vertex(x2, y2);
/*  357 */     LSR.end();
/*      */     
/*  359 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void draw(Shape shape, ShapeFill fill) {
/*  364 */     predraw();
/*  365 */     TextureImpl.bindNone();
/*      */     
/*  367 */     ShapeRenderer.draw(shape, fill);
/*      */     
/*  369 */     this.currentColor.bind();
/*  370 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void fill(Shape shape, ShapeFill fill) {
/*  375 */     predraw();
/*  376 */     TextureImpl.bindNone();
/*      */     
/*  378 */     ShapeRenderer.fill(shape, fill);
/*      */     
/*  380 */     this.currentColor.bind();
/*  381 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void draw(Shape shape) {
/*  386 */     predraw();
/*  387 */     TextureImpl.bindNone();
/*  388 */     this.currentColor.bind();
/*      */     
/*  390 */     ShapeRenderer.draw(shape);
/*      */     
/*  392 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void fill(Shape shape) {
/*  397 */     predraw();
/*  398 */     TextureImpl.bindNone();
/*  399 */     this.currentColor.bind();
/*      */     
/*  401 */     ShapeRenderer.fill(shape);
/*      */     
/*  403 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void texture(Shape shape, Image image) {
/*  408 */     texture(shape, image, 0.01F, 0.01F, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public void texture(Shape shape, Image image, ShapeFill fill) {
/*  413 */     texture(shape, image, 0.01F, 0.01F, fill);
/*      */   }
/*      */ 
/*      */   
/*      */   public void texture(Shape shape, Image image, boolean fit) {
/*  418 */     if (fit) {
/*  419 */       texture(shape, image, 1.0F, 1.0F, true);
/*      */     } else {
/*  421 */       texture(shape, image, 0.01F, 0.01F, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void texture(Shape shape, Image image, float scaleX, float scaleY) {
/*  427 */     texture(shape, image, scaleX, scaleY, false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void texture(Shape shape, Image image, float scaleX, float scaleY, boolean fit) {
/*  433 */     predraw();
/*  434 */     TextureImpl.bindNone();
/*  435 */     this.currentColor.bind();
/*      */     
/*  437 */     if (fit) {
/*  438 */       ShapeRenderer.textureFit(shape, image, scaleX, scaleY);
/*      */     } else {
/*  440 */       ShapeRenderer.texture(shape, image, scaleX, scaleY);
/*      */     } 
/*      */     
/*  443 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void texture(Shape shape, Image image, float scaleX, float scaleY, ShapeFill fill) {
/*  449 */     predraw();
/*  450 */     TextureImpl.bindNone();
/*  451 */     this.currentColor.bind();
/*      */     
/*  453 */     ShapeRenderer.texture(shape, image, scaleX, scaleY, fill);
/*      */     
/*  455 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawRect(float x1, float y1, float width, float height) {
/*  460 */     float lineWidth = this.lineWidth;
/*      */     
/*  462 */     drawLine(x1, y1, x1 + width, y1);
/*  463 */     drawLine(x1 + width, y1, x1 + width, y1 + height);
/*  464 */     drawLine(x1 + width, y1 + height, x1, y1 + height);
/*  465 */     drawLine(x1, y1 + height, x1, y1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void clearClip() {
/*  470 */     this.clip = null;
/*  471 */     predraw();
/*  472 */     GL.glDisable(3089);
/*  473 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setWorldClip(float x, float y, float width, float height) {
/*  478 */     predraw();
/*  479 */     this.worldClipRecord = new Rectangle(x, y, width, height);
/*      */     
/*  481 */     GL.glEnable(12288);
/*  482 */     this.worldClip.put(1.0D).put(0.0D).put(0.0D).put(-x).flip();
/*  483 */     GL.glClipPlane(12288, this.worldClip);
/*  484 */     GL.glEnable(12289);
/*  485 */     this.worldClip.put(-1.0D).put(0.0D).put(0.0D).put((x + width)).flip();
/*  486 */     GL.glClipPlane(12289, this.worldClip);
/*      */     
/*  488 */     GL.glEnable(12290);
/*  489 */     this.worldClip.put(0.0D).put(1.0D).put(0.0D).put(-y).flip();
/*  490 */     GL.glClipPlane(12290, this.worldClip);
/*  491 */     GL.glEnable(12291);
/*  492 */     this.worldClip.put(0.0D).put(-1.0D).put(0.0D).put((y + height)).flip();
/*  493 */     GL.glClipPlane(12291, this.worldClip);
/*  494 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void clearWorldClip() {
/*  499 */     predraw();
/*  500 */     this.worldClipRecord = null;
/*  501 */     GL.glDisable(12288);
/*  502 */     GL.glDisable(12289);
/*  503 */     GL.glDisable(12290);
/*  504 */     GL.glDisable(12291);
/*  505 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setWorldClip(Rectangle clip) {
/*  510 */     if (clip == null) {
/*  511 */       clearWorldClip();
/*      */     } else {
/*  513 */       setWorldClip(clip.getX(), clip.getY(), clip.getWidth(), clip
/*  514 */           .getHeight());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Rectangle getWorldClip() {
/*  520 */     return this.worldClipRecord;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setClip(int x, int y, int width, int height) {
/*  525 */     predraw();
/*      */     
/*  527 */     if (this.clip == null) {
/*  528 */       GL.glEnable(3089);
/*  529 */       this.clip = new Rectangle(x, y, width, height);
/*      */     } else {
/*  531 */       this.clip.setBounds(x, y, width, height);
/*      */     } 
/*      */     
/*  534 */     GL.glScissor(x, this.screenHeight - y - height, width, height);
/*  535 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setClip(Rectangle rect) {
/*  540 */     if (rect == null) {
/*  541 */       clearClip();
/*      */       
/*      */       return;
/*      */     } 
/*  545 */     setClip((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), 
/*  546 */         (int)rect.getHeight());
/*      */   }
/*      */ 
/*      */   
/*      */   public Rectangle getClip() {
/*  551 */     return this.clip;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillRect(float x, float y, float width, float height, Image pattern, float offX, float offY) {
/*  557 */     int cols = (int)Math.ceil((width / pattern.getWidth())) + 2;
/*  558 */     int rows = (int)Math.ceil((height / pattern.getHeight())) + 2;
/*      */     
/*  560 */     Rectangle preClip = this.worldClipRecord;
/*  561 */     setWorldClip(x, y, width, height);
/*      */     
/*  563 */     predraw();
/*      */     
/*  565 */     for (int c = 0; c < cols; c++) {
/*  566 */       for (int r = 0; r < rows; r++) {
/*  567 */         pattern.draw((c * pattern.getWidth()) + x - offX, (r * pattern
/*  568 */             .getHeight()) + y - offY);
/*      */       }
/*      */     } 
/*  571 */     postdraw();
/*      */     
/*  573 */     setWorldClip(preClip);
/*      */   }
/*      */ 
/*      */   
/*      */   public void fillRect(float x1, float y1, float width, float height) {
/*  578 */     predraw();
/*  579 */     TextureImpl.bindNone();
/*  580 */     this.currentColor.bind();
/*      */     
/*  582 */     GL.glBegin(7);
/*  583 */     GL.glVertex2f(x1, y1);
/*  584 */     GL.glVertex2f(x1 + width, y1);
/*  585 */     GL.glVertex2f(x1 + width, y1 + height);
/*  586 */     GL.glVertex2f(x1, y1 + height);
/*  587 */     GL.glEnd();
/*  588 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawOval(float x1, float y1, float width, float height) {
/*  593 */     drawOval(x1, y1, width, height, 50);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawOval(float x1, float y1, float width, float height, int segments) {
/*  599 */     drawArc(x1, y1, width, height, segments, 0.0F, 360.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawArc(float x1, float y1, float width, float height, float start, float end) {
/*  605 */     drawArc(x1, y1, width, height, 50, start, end);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawArc(float x1, float y1, float width, float height, int segments, float start, float end) {
/*  611 */     predraw();
/*  612 */     TextureImpl.bindNone();
/*  613 */     this.currentColor.bind();
/*      */     
/*  615 */     while (end < start) {
/*  616 */       end += 360.0F;
/*      */     }
/*      */     
/*  619 */     float cx = x1 + width / 2.0F;
/*  620 */     float cy = y1 + height / 2.0F;
/*      */     
/*  622 */     LSR.start();
/*  623 */     int step = 360 / segments;
/*      */     int a;
/*  625 */     for (a = (int)start; a < (int)(end + step); a += step) {
/*  626 */       float ang = a;
/*  627 */       if (ang > end) {
/*  628 */         ang = end;
/*      */       }
/*  630 */       float x = (float)(cx + FastTrig.cos(Math.toRadians(ang)) * width / 2.0D);
/*  631 */       float y = (float)(cy + FastTrig.sin(Math.toRadians(ang)) * height / 2.0D);
/*      */       
/*  633 */       LSR.vertex(x, y);
/*      */     } 
/*  635 */     LSR.end();
/*  636 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void fillOval(float x1, float y1, float width, float height) {
/*  641 */     fillOval(x1, y1, width, height, 50);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillOval(float x1, float y1, float width, float height, int segments) {
/*  647 */     fillArc(x1, y1, width, height, segments, 0.0F, 360.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillArc(float x1, float y1, float width, float height, float start, float end) {
/*  653 */     fillArc(x1, y1, width, height, 50, start, end);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillArc(float x1, float y1, float width, float height, int segments, float start, float end) {
/*  659 */     predraw();
/*  660 */     TextureImpl.bindNone();
/*  661 */     this.currentColor.bind();
/*      */     
/*  663 */     while (end < start) {
/*  664 */       end += 360.0F;
/*      */     }
/*      */     
/*  667 */     float cx = x1 + width / 2.0F;
/*  668 */     float cy = y1 + height / 2.0F;
/*      */     
/*  670 */     GL.glBegin(6);
/*  671 */     int step = 360 / segments;
/*      */     
/*  673 */     GL.glVertex2f(cx, cy);
/*      */     int a;
/*  675 */     for (a = (int)start; a < (int)(end + step); a += step) {
/*  676 */       float ang = a;
/*  677 */       if (ang > end) {
/*  678 */         ang = end;
/*      */       }
/*      */       
/*  681 */       float x = (float)(cx + FastTrig.cos(Math.toRadians(ang)) * width / 2.0D);
/*  682 */       float y = (float)(cy + FastTrig.sin(Math.toRadians(ang)) * height / 2.0D);
/*      */       
/*  684 */       GL.glVertex2f(x, y);
/*      */     } 
/*  686 */     GL.glEnd();
/*      */     
/*  688 */     if (this.antialias) {
/*  689 */       GL.glBegin(6);
/*  690 */       GL.glVertex2f(cx, cy);
/*  691 */       if (end != 360.0F) {
/*  692 */         end -= 10.0F;
/*      */       }
/*      */       
/*  695 */       for (a = (int)start; a < (int)(end + step); a += step) {
/*  696 */         float ang = a;
/*  697 */         if (ang > end) {
/*  698 */           ang = end;
/*      */         }
/*      */         
/*  701 */         float x = (float)(cx + FastTrig.cos(Math.toRadians((ang + 10.0F))) * width / 2.0D);
/*      */         
/*  703 */         float y = (float)(cy + FastTrig.sin(Math.toRadians((ang + 10.0F))) * height / 2.0D);
/*      */ 
/*      */         
/*  706 */         GL.glVertex2f(x, y);
/*      */       } 
/*  708 */       GL.glEnd();
/*      */     } 
/*      */     
/*  711 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawRoundRect(float x, float y, float width, float height, int cornerRadius) {
/*  717 */     drawRoundRect(x, y, width, height, cornerRadius, 50);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawRoundRect(float x, float y, float width, float height, int cornerRadius, int segs) {
/*  723 */     if (cornerRadius < 0)
/*  724 */       throw new IllegalArgumentException("corner radius must be > 0"); 
/*  725 */     if (cornerRadius == 0) {
/*  726 */       drawRect(x, y, width, height);
/*      */       
/*      */       return;
/*      */     } 
/*  730 */     int mr = (int)Math.min(width, height) / 2;
/*      */     
/*  732 */     if (cornerRadius > mr) {
/*  733 */       cornerRadius = mr;
/*      */     }
/*      */     
/*  736 */     drawLine(x + cornerRadius, y, x + width - cornerRadius, y);
/*  737 */     drawLine(x, y + cornerRadius, x, y + height - cornerRadius);
/*  738 */     drawLine(x + width, y + cornerRadius, x + width, y + height - cornerRadius);
/*      */     
/*  740 */     drawLine(x + cornerRadius, y + height, x + width - cornerRadius, y + height);
/*      */ 
/*      */     
/*  743 */     float d = (cornerRadius << 1);
/*      */     
/*  745 */     drawArc(x + width - d, y + height - d, d, d, segs, 0.0F, 90.0F);
/*      */     
/*  747 */     drawArc(x, y + height - d, d, d, segs, 90.0F, 180.0F);
/*      */     
/*  749 */     drawArc(x + width - d, y, d, d, segs, 270.0F, 360.0F);
/*      */     
/*  751 */     drawArc(x, y, d, d, segs, 180.0F, 270.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillRoundRect(float x, float y, float width, float height, int cornerRadius) {
/*  757 */     fillRoundRect(x, y, width, height, cornerRadius, 50);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillRoundRect(float x, float y, float width, float height, int cornerRadius, int segs) {
/*  763 */     if (cornerRadius < 0)
/*  764 */       throw new IllegalArgumentException("corner radius must be > 0"); 
/*  765 */     if (cornerRadius == 0) {
/*  766 */       fillRect(x, y, width, height);
/*      */       
/*      */       return;
/*      */     } 
/*  770 */     int mr = (int)Math.min(width, height) / 2;
/*      */     
/*  772 */     if (cornerRadius > mr) {
/*  773 */       cornerRadius = mr;
/*      */     }
/*      */     
/*  776 */     float d = (cornerRadius << 1);
/*      */     
/*  778 */     fillRect(x + cornerRadius, y, width - d, cornerRadius);
/*  779 */     fillRect(x, y + cornerRadius, cornerRadius, height - d);
/*  780 */     fillRect(x + width - cornerRadius, y + cornerRadius, cornerRadius, height - d);
/*      */     
/*  782 */     fillRect(x + cornerRadius, y + height - cornerRadius, width - d, cornerRadius);
/*      */     
/*  784 */     fillRect(x + cornerRadius, y + cornerRadius, width - d, height - d);
/*      */ 
/*      */     
/*  787 */     fillArc(x + width - d, y + height - d, d, d, segs, 0.0F, 90.0F);
/*      */     
/*  789 */     fillArc(x, y + height - d, d, d, segs, 90.0F, 180.0F);
/*      */     
/*  791 */     fillArc(x + width - d, y, d, d, segs, 270.0F, 360.0F);
/*      */     
/*  793 */     fillArc(x, y, d, d, segs, 180.0F, 270.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLineWidth(float width) {
/*  798 */     predraw();
/*  799 */     this.lineWidth = width;
/*  800 */     LSR.setWidth(width);
/*  801 */     GL.glPointSize(width);
/*  802 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public float getLineWidth() {
/*  807 */     return this.lineWidth;
/*      */   }
/*      */ 
/*      */   
/*      */   public void resetLineWidth() {
/*  812 */     predraw();
/*      */     
/*  814 */     Renderer.getLineStripRenderer().setWidth(1.0F);
/*  815 */     GL.glLineWidth(1.0F);
/*  816 */     GL.glPointSize(1.0F);
/*      */     
/*  818 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAntiAlias(boolean anti) {
/*  823 */     predraw();
/*  824 */     this.antialias = anti;
/*  825 */     LSR.setAntiAlias(anti);
/*  826 */     if (anti) {
/*  827 */       GL.glEnable(2881);
/*      */     } else {
/*  829 */       GL.glDisable(2881);
/*      */     } 
/*  831 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAntiAlias() {
/*  836 */     return this.antialias;
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawString(String str, float x, float y) {
/*  841 */     predraw();
/*  842 */     this.font.drawString(x, y, str, this.currentColor);
/*  843 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawImage(Image image, float x, float y, Color col) {
/*  848 */     predraw();
/*  849 */     image.draw(x, y, col);
/*  850 */     this.currentColor.bind();
/*  851 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawAnimation(Animation anim, float x, float y) {
/*  856 */     drawAnimation(anim, x, y, Color.white);
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawAnimation(Animation anim, float x, float y, Color col) {
/*  861 */     predraw();
/*  862 */     anim.draw(x, y, col);
/*  863 */     this.currentColor.bind();
/*  864 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawImage(Image image, float x, float y) {
/*  869 */     drawImage(image, x, y, Color.white);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawImage(Image image, float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2) {
/*  875 */     predraw();
/*  876 */     image.draw(x, y, x2, y2, srcx, srcy, srcx2, srcy2);
/*  877 */     this.currentColor.bind();
/*  878 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawImage(Image image, float x, float y, float srcx, float srcy, float srcx2, float srcy2) {
/*  884 */     drawImage(image, x, y, x + image.getWidth(), y + image.getHeight(), srcx, srcy, srcx2, srcy2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyArea(Image target, int x, int y) {
/*  890 */     int format = target.getTexture().hasAlpha() ? 6408 : 6407;
/*  891 */     target.bind();
/*  892 */     GL.glCopyTexImage2D(3553, 0, format, x, this.screenHeight - y + target
/*  893 */         .getHeight(), target.getTexture()
/*  894 */         .getTextureWidth(), target.getTexture().getTextureHeight(), 0);
/*  895 */     target.ensureInverted();
/*      */   }
/*      */ 
/*      */   
/*      */   private int translate(byte b) {
/*  900 */     if (b < 0) {
/*  901 */       return 256 + b;
/*      */     }
/*      */     
/*  904 */     return b;
/*      */   }
/*      */ 
/*      */   
/*      */   public Color getPixel(int x, int y) {
/*  909 */     predraw();
/*  910 */     GL.glReadPixels(x, this.screenHeight - y, 1, 1, 6408, 5121, this.readBuffer);
/*      */     
/*  912 */     postdraw();
/*      */     
/*  914 */     return new Color(translate(this.readBuffer.get(0)), translate(this.readBuffer
/*  915 */           .get(1)), translate(this.readBuffer.get(2)), translate(this.readBuffer
/*  916 */           .get(3)));
/*      */   }
/*      */ 
/*      */   
/*      */   public void getArea(int x, int y, int width, int height, ByteBuffer target) {
/*  921 */     if (target.capacity() < width * height * 4) {
/*  922 */       throw new IllegalArgumentException("Byte buffer provided to get area is not big enough");
/*      */     }
/*      */     
/*  925 */     predraw();
/*  926 */     GL.glReadPixels(x, this.screenHeight - y - height, width, height, 6408, 5121, target);
/*      */     
/*  928 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawImage(Image image, float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2, Color col) {
/*  934 */     predraw();
/*  935 */     image.draw(x, y, x2, y2, srcx, srcy, srcx2, srcy2, col);
/*  936 */     this.currentColor.bind();
/*  937 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawImage(Image image, float x, float y, float srcx, float srcy, float srcx2, float srcy2, Color col) {
/*  943 */     drawImage(image, x, y, x + image.getWidth(), y + image.getHeight(), srcx, srcy, srcx2, srcy2, col);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawGradientLine(float x1, float y1, float red1, float green1, float blue1, float alpha1, float x2, float y2, float red2, float green2, float blue2, float alpha2) {
/*  951 */     predraw();
/*      */     
/*  953 */     TextureImpl.bindNone();
/*      */     
/*  955 */     GL.glBegin(1);
/*      */     
/*  957 */     GL.glColor4f(red1, green1, blue1, alpha1);
/*  958 */     GL.glVertex2f(x1, y1);
/*      */     
/*  960 */     GL.glColor4f(red2, green2, blue2, alpha2);
/*  961 */     GL.glVertex2f(x2, y2);
/*      */     
/*  963 */     GL.glEnd();
/*      */     
/*  965 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawGradientLine(float x1, float y1, Color Color1, float x2, float y2, Color Color2) {
/*  971 */     predraw();
/*      */     
/*  973 */     TextureImpl.bindNone();
/*      */     
/*  975 */     GL.glBegin(1);
/*      */     
/*  977 */     Color1.bind();
/*  978 */     GL.glVertex2f(x1, y1);
/*      */     
/*  980 */     Color2.bind();
/*  981 */     GL.glVertex2f(x2, y2);
/*      */     
/*  983 */     GL.glEnd();
/*      */     
/*  985 */     postdraw();
/*      */   }
/*      */   
/*      */   public void pushTransform() {
/*      */     FloatBuffer buffer;
/*  990 */     predraw();
/*      */ 
/*      */     
/*  993 */     if (this.stackIndex >= this.stack.size()) {
/*  994 */       buffer = BufferUtils.createFloatBuffer(18);
/*  995 */       this.stack.add(buffer);
/*      */     } else {
/*  997 */       buffer = this.stack.get(this.stackIndex);
/*      */     } 
/*      */     
/* 1000 */     GL.glGetFloat(2982, buffer);
/* 1001 */     buffer.put(16, this.sx);
/* 1002 */     buffer.put(17, this.sy);
/* 1003 */     this.stackIndex++;
/*      */     
/* 1005 */     postdraw();
/*      */   }
/*      */ 
/*      */   
/*      */   public void popTransform() {
/* 1010 */     if (this.stackIndex == 0) {
/* 1011 */       throw new RuntimeException("Attempt to pop a transform that hasn't be pushed");
/*      */     }
/*      */     
/* 1014 */     predraw();
/*      */     
/* 1016 */     this.stackIndex--;
/* 1017 */     FloatBuffer oldBuffer = this.stack.get(this.stackIndex);
/* 1018 */     GL.glLoadMatrix(oldBuffer);
/* 1019 */     this.sx = oldBuffer.get(16);
/* 1020 */     this.sy = oldBuffer.get(17);
/*      */     
/* 1022 */     postdraw();
/*      */   }
/*      */   
/*      */   public void destroy() {}
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\Graphics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */