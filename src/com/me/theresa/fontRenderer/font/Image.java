/*     */ package com.me.theresa.fontRenderer.font;
/*     */ 
/*     */ import com.me.theresa.fontRenderer.font.effect.Renderable;
/*     */ import com.me.theresa.fontRenderer.font.log.Log;
/*     */ import com.me.theresa.fontRenderer.font.opengl.ImageData;
/*     */ import com.me.theresa.fontRenderer.font.opengl.InternalTextureLoader;
/*     */ import com.me.theresa.fontRenderer.font.opengl.Texture;
/*     */ import com.me.theresa.fontRenderer.font.opengl.TextureImpl;
/*     */ import com.me.theresa.fontRenderer.font.opengl.pbuffer.GraphicsFactory;
/*     */ import com.me.theresa.fontRenderer.font.opengl.renderer.Renderer;
/*     */ import com.me.theresa.fontRenderer.font.opengl.renderer.SGL;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Image
/*     */   implements Renderable
/*     */ {
/*     */   public static final int TOP_LEFT = 0;
/*     */   public static final int TOP_RIGHT = 1;
/*     */   public static final int BOTTOM_RIGHT = 2;
/*     */   public static final int BOTTOM_LEFT = 3;
/*  28 */   protected static SGL GL = Renderer.get();
/*     */ 
/*     */   
/*     */   protected static Image inUse;
/*     */ 
/*     */   
/*     */   public static final int FILTER_LINEAR = 1;
/*     */   
/*     */   public static final int FILTER_NEAREST = 2;
/*     */   
/*     */   protected Texture texture;
/*     */   
/*     */   protected int width;
/*     */   
/*     */   protected int height;
/*     */   
/*     */   protected float textureWidth;
/*     */   
/*     */   protected float textureHeight;
/*     */   
/*     */   protected float textureOffsetX;
/*     */   
/*     */   protected float textureOffsetY;
/*     */   
/*     */   protected float angle;
/*     */   
/*  54 */   protected float alpha = 1.0F;
/*     */ 
/*     */   
/*     */   protected String ref;
/*     */ 
/*     */   
/*     */   protected boolean inited = false;
/*     */ 
/*     */   
/*     */   protected byte[] pixelData;
/*     */   
/*     */   protected boolean destroyed;
/*     */   
/*     */   protected float centerX;
/*     */   
/*     */   protected float centerY;
/*     */   
/*     */   protected String name;
/*     */   
/*     */   protected Color[] corners;
/*     */   
/*  75 */   private int filter = 9729;
/*     */ 
/*     */   
/*     */   private boolean flipped;
/*     */   
/*     */   private Color transparent;
/*     */ 
/*     */   
/*     */   protected Image(Image other) {
/*  84 */     this.width = other.getWidth();
/*  85 */     this.height = other.getHeight();
/*  86 */     this.texture = other.texture;
/*  87 */     this.textureWidth = other.textureWidth;
/*  88 */     this.textureHeight = other.textureHeight;
/*  89 */     this.ref = other.ref;
/*  90 */     this.textureOffsetX = other.textureOffsetX;
/*  91 */     this.textureOffsetY = other.textureOffsetY;
/*     */     
/*  93 */     this.centerX = (this.width / 2);
/*  94 */     this.centerY = (this.height / 2);
/*  95 */     this.inited = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Image() {}
/*     */ 
/*     */   
/*     */   public Image(Texture texture) {
/* 104 */     this.texture = texture;
/* 105 */     this.ref = texture.toString();
/* 106 */     clampTexture();
/*     */   }
/*     */ 
/*     */   
/*     */   public Image(String ref) throws SlickException {
/* 111 */     this(ref, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public Image(String ref, Color trans) throws SlickException {
/* 116 */     this(ref, false, 1, trans);
/*     */   }
/*     */ 
/*     */   
/*     */   public Image(String ref, boolean flipped) throws SlickException {
/* 121 */     this(ref, flipped, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public Image(String ref, boolean flipped, int filter) throws SlickException {
/* 126 */     this(ref, flipped, filter, (Color)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Image(String ref, boolean flipped, int f, Color transparent) throws SlickException {
/* 131 */     this.filter = (f == 1) ? 9729 : 9728;
/* 132 */     this.transparent = transparent;
/* 133 */     this.flipped = flipped;
/*     */     
/*     */     try {
/* 136 */       this.ref = ref;
/* 137 */       int[] trans = null;
/* 138 */       if (transparent != null) {
/* 139 */         trans = new int[3];
/* 140 */         trans[0] = (int)(transparent.r * 255.0F);
/* 141 */         trans[1] = (int)(transparent.g * 255.0F);
/* 142 */         trans[2] = (int)(transparent.b * 255.0F);
/*     */       } 
/* 144 */       this.texture = InternalTextureLoader.get().getTexture(ref, flipped, this.filter, trans);
/* 145 */     } catch (IOException e) {
/* 146 */       Log.error(e);
/* 147 */       throw new SlickException("Failed to load image from: " + ref, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFilter(int f) {
/* 153 */     this.filter = (f == 1) ? 9729 : 9728;
/*     */     
/* 155 */     this.texture.bind();
/* 156 */     GL.glTexParameteri(3553, 10241, this.filter);
/* 157 */     GL.glTexParameteri(3553, 10240, this.filter);
/*     */   }
/*     */ 
/*     */   
/*     */   public Image(int width, int height) throws SlickException {
/* 162 */     this(width, height, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Image(int width, int height, int f) throws SlickException {
/* 167 */     this.ref = super.toString();
/* 168 */     this.filter = (f == 1) ? 9729 : 9728;
/*     */     
/*     */     try {
/* 171 */       this.texture = InternalTextureLoader.get().createTexture(width, height, this.filter);
/* 172 */     } catch (IOException e) {
/* 173 */       Log.error(e);
/* 174 */       throw new SlickException("Failed to create empty image " + width + "x" + height);
/*     */     } 
/*     */     
/* 177 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public Image(InputStream in, String ref, boolean flipped) throws SlickException {
/* 182 */     this(in, ref, flipped, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public Image(InputStream in, String ref, boolean flipped, int filter) throws SlickException {
/* 187 */     load(in, ref, flipped, filter, null);
/*     */   }
/*     */ 
/*     */   
/*     */   Image(ImageBuffer buffer) {
/* 192 */     this(buffer, 1);
/* 193 */     TextureImpl.bindNone();
/*     */   }
/*     */ 
/*     */   
/*     */   Image(ImageBuffer buffer, int filter) {
/* 198 */     this((ImageData)buffer, filter);
/* 199 */     TextureImpl.bindNone();
/*     */   }
/*     */ 
/*     */   
/*     */   public Image(ImageData data) {
/* 204 */     this(data, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public Image(ImageData data, int f) {
/*     */     try {
/* 210 */       this.filter = (f == 1) ? 9729 : 9728;
/* 211 */       this.texture = InternalTextureLoader.get().getTexture(data, this.filter);
/* 212 */       this.ref = this.texture.toString();
/* 213 */     } catch (IOException e) {
/* 214 */       Log.error(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFilter() {
/* 220 */     return this.filter;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getResourceReference() {
/* 225 */     return this.ref;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setImageColor(float r, float g, float b, float a) {
/* 230 */     setColor(0, r, g, b, a);
/* 231 */     setColor(1, r, g, b, a);
/* 232 */     setColor(3, r, g, b, a);
/* 233 */     setColor(2, r, g, b, a);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setImageColor(float r, float g, float b) {
/* 238 */     setColor(0, r, g, b);
/* 239 */     setColor(1, r, g, b);
/* 240 */     setColor(3, r, g, b);
/* 241 */     setColor(2, r, g, b);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setColor(int corner, float r, float g, float b, float a) {
/* 246 */     if (this.corners == null) {
/* 247 */       this.corners = new Color[] { new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F) };
/*     */     }
/*     */     
/* 250 */     (this.corners[corner]).r = r;
/* 251 */     (this.corners[corner]).g = g;
/* 252 */     (this.corners[corner]).b = b;
/* 253 */     (this.corners[corner]).a = a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setColor(int corner, float r, float g, float b) {
/* 258 */     if (this.corners == null) {
/* 259 */       this.corners = new Color[] { new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F) };
/*     */     }
/*     */     
/* 262 */     (this.corners[corner]).r = r;
/* 263 */     (this.corners[corner]).g = g;
/* 264 */     (this.corners[corner]).b = b;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clampTexture() {
/* 269 */     if (GL.canTextureMirrorClamp()) {
/* 270 */       GL.glTexParameteri(3553, 10242, 34627);
/* 271 */       GL.glTexParameteri(3553, 10243, 34627);
/*     */     } else {
/* 273 */       GL.glTexParameteri(3553, 10242, 10496);
/* 274 */       GL.glTexParameteri(3553, 10243, 10496);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/* 280 */     this.name = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 285 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public Graphics getGraphics() throws SlickException {
/* 290 */     return GraphicsFactory.getGraphicsForImage(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private void load(InputStream in, String ref, boolean flipped, int f, Color transparent) throws SlickException {
/* 295 */     this.filter = (f == 1) ? 9729 : 9728;
/*     */     
/*     */     try {
/* 298 */       this.ref = ref;
/* 299 */       int[] trans = null;
/* 300 */       if (transparent != null) {
/* 301 */         trans = new int[3];
/* 302 */         trans[0] = (int)(transparent.r * 255.0F);
/* 303 */         trans[1] = (int)(transparent.g * 255.0F);
/* 304 */         trans[2] = (int)(transparent.b * 255.0F);
/*     */       } 
/* 306 */       this.texture = (Texture)InternalTextureLoader.get().getTexture(in, ref, flipped, this.filter, trans);
/* 307 */     } catch (IOException e) {
/* 308 */       Log.error(e);
/* 309 */       throw new SlickException("Failed to load image from: " + ref, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind() {
/* 315 */     this.texture.bind();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void reinit() {
/* 320 */     this.inited = false;
/* 321 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void init() {
/* 326 */     if (this.inited) {
/*     */       return;
/*     */     }
/*     */     
/* 330 */     this.inited = true;
/* 331 */     if (this.texture != null) {
/* 332 */       this.width = this.texture.getImageWidth();
/* 333 */       this.height = this.texture.getImageHeight();
/* 334 */       this.textureOffsetX = 0.0F;
/* 335 */       this.textureOffsetY = 0.0F;
/* 336 */       this.textureWidth = this.texture.getWidth();
/* 337 */       this.textureHeight = this.texture.getHeight();
/*     */     } 
/*     */     
/* 340 */     initImpl();
/*     */     
/* 342 */     this.centerX = (this.width / 2);
/* 343 */     this.centerY = (this.height / 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initImpl() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw() {
/* 353 */     draw(0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCentered(float x, float y) {
/* 358 */     draw(x - (getWidth() / 2), y - (getHeight() / 2));
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(float x, float y) {
/* 363 */     init();
/* 364 */     draw(x, y, this.width, this.height);
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(float x, float y, Color filter) {
/* 369 */     init();
/* 370 */     draw(x, y, this.width, this.height, filter);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawEmbedded(float x, float y, float width, float height) {
/* 375 */     init();
/*     */     
/* 377 */     if (this.corners == null) {
/* 378 */       GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY);
/* 379 */       GL.glVertex3f(x, y, 0.0F);
/* 380 */       GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY + this.textureHeight);
/* 381 */       GL.glVertex3f(x, y + height, 0.0F);
/* 382 */       GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY + this.textureHeight);
/*     */       
/* 384 */       GL.glVertex3f(x + width, y + height, 0.0F);
/* 385 */       GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY);
/* 386 */       GL.glVertex3f(x + width, y, 0.0F);
/*     */     } else {
/* 388 */       this.corners[0].bind();
/* 389 */       GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY);
/* 390 */       GL.glVertex3f(x, y, 0.0F);
/* 391 */       this.corners[3].bind();
/* 392 */       GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY + this.textureHeight);
/* 393 */       GL.glVertex3f(x, y + height, 0.0F);
/* 394 */       this.corners[2].bind();
/* 395 */       GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY + this.textureHeight);
/*     */       
/* 397 */       GL.glVertex3f(x + width, y + height, 0.0F);
/* 398 */       this.corners[1].bind();
/* 399 */       GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY);
/* 400 */       GL.glVertex3f(x + width, y, 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getTextureOffsetX() {
/* 406 */     init();
/*     */     
/* 408 */     return this.textureOffsetX;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getTextureOffsetY() {
/* 413 */     init();
/*     */     
/* 415 */     return this.textureOffsetY;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getTextureWidth() {
/* 420 */     init();
/*     */     
/* 422 */     return this.textureWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getTextureHeight() {
/* 427 */     init();
/*     */     
/* 429 */     return this.textureHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(float x, float y, float scale) {
/* 434 */     init();
/* 435 */     draw(x, y, this.width * scale, this.height * scale, Color.white);
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(float x, float y, float scale, Color filter) {
/* 440 */     init();
/* 441 */     draw(x, y, this.width * scale, this.height * scale, filter);
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(float x, float y, float width, float height) {
/* 446 */     init();
/* 447 */     draw(x, y, width, height, Color.white);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawSheared(float x, float y, float hshear, float vshear) {
/* 452 */     drawSheared(x, y, hshear, vshear, Color.white);
/*     */   }
/*     */   
/*     */   public void drawSheared(float x, float y, float hshear, float vshear, Color filter) {
/* 456 */     if (this.alpha != 1.0F) {
/* 457 */       if (filter == null) {
/* 458 */         filter = Color.white;
/*     */       }
/*     */       
/* 461 */       filter = new Color(filter);
/* 462 */       filter.a *= this.alpha;
/*     */     } 
/* 464 */     if (filter != null) {
/* 465 */       filter.bind();
/*     */     }
/*     */     
/* 468 */     this.texture.bind();
/*     */     
/* 470 */     GL.glTranslatef(x, y, 0.0F);
/* 471 */     if (this.angle != 0.0F) {
/* 472 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/* 473 */       GL.glRotatef(this.angle, 0.0F, 0.0F, 1.0F);
/* 474 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*     */     } 
/*     */     
/* 477 */     GL.glBegin(7);
/* 478 */     init();
/*     */     
/* 480 */     GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY);
/* 481 */     GL.glVertex3f(0.0F, 0.0F, 0.0F);
/* 482 */     GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY + this.textureHeight);
/* 483 */     GL.glVertex3f(hshear, this.height, 0.0F);
/* 484 */     GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY + this.textureHeight);
/*     */     
/* 486 */     GL.glVertex3f(this.width + hshear, this.height + vshear, 0.0F);
/* 487 */     GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY);
/* 488 */     GL.glVertex3f(this.width, vshear, 0.0F);
/* 489 */     GL.glEnd();
/*     */     
/* 491 */     if (this.angle != 0.0F) {
/* 492 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/* 493 */       GL.glRotatef(-this.angle, 0.0F, 0.0F, 1.0F);
/* 494 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*     */     } 
/* 496 */     GL.glTranslatef(-x, -y, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(float x, float y, float width, float height, Color filter) {
/* 501 */     if (this.alpha != 1.0F) {
/* 502 */       if (filter == null) {
/* 503 */         filter = Color.white;
/*     */       }
/*     */       
/* 506 */       filter = new Color(filter);
/* 507 */       filter.a *= this.alpha;
/*     */     } 
/* 509 */     if (filter != null) {
/* 510 */       filter.bind();
/*     */     }
/*     */     
/* 513 */     this.texture.bind();
/*     */     
/* 515 */     GL.glTranslatef(x, y, 0.0F);
/* 516 */     if (this.angle != 0.0F) {
/* 517 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/* 518 */       GL.glRotatef(this.angle, 0.0F, 0.0F, 1.0F);
/* 519 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*     */     } 
/*     */     
/* 522 */     GL.glBegin(7);
/* 523 */     drawEmbedded(0.0F, 0.0F, width, height);
/* 524 */     GL.glEnd();
/*     */     
/* 526 */     if (this.angle != 0.0F) {
/* 527 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/* 528 */       GL.glRotatef(-this.angle, 0.0F, 0.0F, 1.0F);
/* 529 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*     */     } 
/* 531 */     GL.glTranslatef(-x, -y, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawFlash(float x, float y, float width, float height) {
/* 536 */     drawFlash(x, y, width, height, Color.white);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCenterOfRotation(float x, float y) {
/* 541 */     this.centerX = x;
/* 542 */     this.centerY = y;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getCenterOfRotationX() {
/* 547 */     init();
/*     */     
/* 549 */     return this.centerX;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getCenterOfRotationY() {
/* 554 */     init();
/*     */     
/* 556 */     return this.centerY;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawFlash(float x, float y, float width, float height, Color col) {
/* 561 */     init();
/*     */     
/* 563 */     col.bind();
/* 564 */     this.texture.bind();
/*     */     
/* 566 */     if (GL.canSecondaryColor()) {
/* 567 */       GL.glEnable(33880);
/* 568 */       GL.glSecondaryColor3ubEXT((byte)(int)(col.r * 255.0F), (byte)(int)(col.g * 255.0F), (byte)(int)(col.b * 255.0F));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 573 */     GL.glTexEnvi(8960, 8704, 8448);
/*     */     
/* 575 */     GL.glTranslatef(x, y, 0.0F);
/* 576 */     if (this.angle != 0.0F) {
/* 577 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/* 578 */       GL.glRotatef(this.angle, 0.0F, 0.0F, 1.0F);
/* 579 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*     */     } 
/*     */     
/* 582 */     GL.glBegin(7);
/* 583 */     drawEmbedded(0.0F, 0.0F, width, height);
/* 584 */     GL.glEnd();
/*     */     
/* 586 */     if (this.angle != 0.0F) {
/* 587 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/* 588 */       GL.glRotatef(-this.angle, 0.0F, 0.0F, 1.0F);
/* 589 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*     */     } 
/* 591 */     GL.glTranslatef(-x, -y, 0.0F);
/*     */     
/* 593 */     if (GL.canSecondaryColor()) {
/* 594 */       GL.glDisable(33880);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawFlash(float x, float y) {
/* 600 */     drawFlash(x, y, getWidth(), getHeight());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRotation(float angle) {
/* 605 */     this.angle = angle % 360.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getRotation() {
/* 610 */     return this.angle;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getAlpha() {
/* 615 */     return this.alpha;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAlpha(float alpha) {
/* 620 */     this.alpha = alpha;
/*     */   }
/*     */ 
/*     */   
/*     */   public void rotate(float angle) {
/* 625 */     this.angle += angle;
/* 626 */     this.angle %= 360.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public Image getSubImage(int x, int y, int width, int height) {
/* 631 */     init();
/*     */     
/* 633 */     float newTextureOffsetX = x / this.width * this.textureWidth + this.textureOffsetX;
/* 634 */     float newTextureOffsetY = y / this.height * this.textureHeight + this.textureOffsetY;
/* 635 */     float newTextureWidth = width / this.width * this.textureWidth;
/* 636 */     float newTextureHeight = height / this.height * this.textureHeight;
/*     */     
/* 638 */     Image sub = new Image();
/* 639 */     sub.inited = true;
/* 640 */     sub.texture = this.texture;
/* 641 */     sub.textureOffsetX = newTextureOffsetX;
/* 642 */     sub.textureOffsetY = newTextureOffsetY;
/* 643 */     sub.textureWidth = newTextureWidth;
/* 644 */     sub.textureHeight = newTextureHeight;
/*     */     
/* 646 */     sub.width = width;
/* 647 */     sub.height = height;
/* 648 */     sub.ref = this.ref;
/* 649 */     sub.centerX = (width / 2);
/* 650 */     sub.centerY = (height / 2);
/*     */     
/* 652 */     return sub;
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(float x, float y, float srcx, float srcy, float srcx2, float srcy2) {
/* 657 */     draw(x, y, x + this.width, y + this.height, srcx, srcy, srcx2, srcy2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2) {
/* 662 */     draw(x, y, x2, y2, srcx, srcy, srcx2, srcy2, Color.white);
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2, Color filter) {
/* 667 */     init();
/*     */     
/* 669 */     if (this.alpha != 1.0F) {
/* 670 */       if (filter == null) {
/* 671 */         filter = Color.white;
/*     */       }
/*     */       
/* 674 */       filter = new Color(filter);
/* 675 */       filter.a *= this.alpha;
/*     */     } 
/* 677 */     filter.bind();
/* 678 */     this.texture.bind();
/*     */     
/* 680 */     GL.glTranslatef(x, y, 0.0F);
/* 681 */     if (this.angle != 0.0F) {
/* 682 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/* 683 */       GL.glRotatef(this.angle, 0.0F, 0.0F, 1.0F);
/* 684 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*     */     } 
/*     */     
/* 687 */     GL.glBegin(7);
/* 688 */     drawEmbedded(0.0F, 0.0F, x2 - x, y2 - y, srcx, srcy, srcx2, srcy2);
/* 689 */     GL.glEnd();
/*     */     
/* 691 */     if (this.angle != 0.0F) {
/* 692 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/* 693 */       GL.glRotatef(-this.angle, 0.0F, 0.0F, 1.0F);
/* 694 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*     */     } 
/* 696 */     GL.glTranslatef(-x, -y, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawEmbedded(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2) {
/* 705 */     drawEmbedded(x, y, x2, y2, srcx, srcy, srcx2, srcy2, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawEmbedded(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2, Color filter) {
/* 710 */     if (filter != null) {
/* 711 */       filter.bind();
/*     */     }
/*     */     
/* 714 */     float mywidth = x2 - x;
/* 715 */     float myheight = y2 - y;
/* 716 */     float texwidth = srcx2 - srcx;
/* 717 */     float texheight = srcy2 - srcy;
/*     */     
/* 719 */     float newTextureOffsetX = srcx / this.width * this.textureWidth + this.textureOffsetX;
/*     */     
/* 721 */     float newTextureOffsetY = srcy / this.height * this.textureHeight + this.textureOffsetY;
/*     */     
/* 723 */     float newTextureWidth = texwidth / this.width * this.textureWidth;
/*     */     
/* 725 */     float newTextureHeight = texheight / this.height * this.textureHeight;
/*     */ 
/*     */     
/* 728 */     GL.glTexCoord2f(newTextureOffsetX, newTextureOffsetY);
/* 729 */     GL.glVertex3f(x, y, 0.0F);
/* 730 */     GL.glTexCoord2f(newTextureOffsetX, newTextureOffsetY + newTextureHeight);
/*     */     
/* 732 */     GL.glVertex3f(x, y + myheight, 0.0F);
/* 733 */     GL.glTexCoord2f(newTextureOffsetX + newTextureWidth, newTextureOffsetY + newTextureHeight);
/*     */     
/* 735 */     GL.glVertex3f(x + mywidth, y + myheight, 0.0F);
/* 736 */     GL.glTexCoord2f(newTextureOffsetX + newTextureWidth, newTextureOffsetY);
/*     */     
/* 738 */     GL.glVertex3f(x + mywidth, y, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawWarped(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
/* 743 */     Color.white.bind();
/* 744 */     this.texture.bind();
/*     */     
/* 746 */     GL.glTranslatef(x1, y1, 0.0F);
/* 747 */     if (this.angle != 0.0F) {
/* 748 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/* 749 */       GL.glRotatef(this.angle, 0.0F, 0.0F, 1.0F);
/* 750 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*     */     } 
/*     */     
/* 753 */     GL.glBegin(7);
/* 754 */     init();
/*     */     
/* 756 */     GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY);
/* 757 */     GL.glVertex3f(0.0F, 0.0F, 0.0F);
/* 758 */     GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY + this.textureHeight);
/* 759 */     GL.glVertex3f(x2 - x1, y2 - y1, 0.0F);
/* 760 */     GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY + this.textureHeight);
/*     */     
/* 762 */     GL.glVertex3f(x3 - x1, y3 - y1, 0.0F);
/* 763 */     GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY);
/* 764 */     GL.glVertex3f(x4 - x1, y4 - y1, 0.0F);
/* 765 */     GL.glEnd();
/*     */     
/* 767 */     if (this.angle != 0.0F) {
/* 768 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/* 769 */       GL.glRotatef(-this.angle, 0.0F, 0.0F, 1.0F);
/* 770 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*     */     } 
/* 772 */     GL.glTranslatef(-x1, -y1, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 777 */     init();
/* 778 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 783 */     init();
/* 784 */     return this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   public Image copy() {
/* 789 */     init();
/* 790 */     return getSubImage(0, 0, this.width, this.height);
/*     */   }
/*     */ 
/*     */   
/*     */   public Image getScaledCopy(float scale) {
/* 795 */     init();
/* 796 */     return getScaledCopy((int)(this.width * scale), (int)(this.height * scale));
/*     */   }
/*     */ 
/*     */   
/*     */   public Image getScaledCopy(int width, int height) {
/* 801 */     init();
/* 802 */     Image image = copy();
/* 803 */     image.width = width;
/* 804 */     image.height = height;
/* 805 */     image.centerX = (width / 2);
/* 806 */     image.centerY = (height / 2);
/* 807 */     return image;
/*     */   }
/*     */ 
/*     */   
/*     */   public void ensureInverted() {
/* 812 */     if (this.textureHeight > 0.0F) {
/* 813 */       this.textureOffsetY += this.textureHeight;
/* 814 */       this.textureHeight = -this.textureHeight;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Image getFlippedCopy(boolean flipHorizontal, boolean flipVertical) {
/* 820 */     init();
/* 821 */     Image image = copy();
/*     */     
/* 823 */     if (flipHorizontal) {
/* 824 */       this.textureOffsetX += this.textureWidth;
/* 825 */       image.textureWidth = -this.textureWidth;
/*     */     } 
/* 827 */     if (flipVertical) {
/* 828 */       this.textureOffsetY += this.textureHeight;
/* 829 */       image.textureHeight = -this.textureHeight;
/*     */     } 
/*     */     
/* 832 */     return image;
/*     */   }
/*     */ 
/*     */   
/*     */   public void endUse() {
/* 837 */     if (inUse != this) {
/* 838 */       throw new RuntimeException("The sprite sheet is not currently in use");
/*     */     }
/* 840 */     inUse = null;
/* 841 */     GL.glEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   public void startUse() {
/* 846 */     if (inUse != null) {
/* 847 */       throw new RuntimeException("Attempt to start use of a sprite sheet before ending use with another - see endUse()");
/*     */     }
/* 849 */     inUse = this;
/* 850 */     init();
/*     */     
/* 852 */     Color.white.bind();
/* 853 */     this.texture.bind();
/* 854 */     GL.glBegin(7);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 859 */     init();
/*     */     
/* 861 */     return "[Image " + this.ref + " " + this.width + "x" + this.height + "  " + this.textureOffsetX + "," + this.textureOffsetY + "," + this.textureWidth + "," + this.textureHeight + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public Texture getTexture() {
/* 866 */     return this.texture;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTexture(Texture texture) {
/* 871 */     this.texture = texture;
/* 872 */     reinit();
/*     */   }
/*     */ 
/*     */   
/*     */   private int translate(byte b) {
/* 877 */     if (b < 0) {
/* 878 */       return 256 + b;
/*     */     }
/*     */     
/* 881 */     return b;
/*     */   }
/*     */ 
/*     */   
/*     */   public Color getColor(int x, int y) {
/* 886 */     if (this.pixelData == null) {
/* 887 */       this.pixelData = this.texture.getTextureData();
/*     */     }
/*     */     
/* 890 */     int xo = (int)(this.textureOffsetX * this.texture.getTextureWidth());
/* 891 */     int yo = (int)(this.textureOffsetY * this.texture.getTextureHeight());
/*     */     
/* 893 */     if (this.textureWidth < 0.0F) {
/* 894 */       x = xo - x;
/*     */     } else {
/* 896 */       x = xo + x;
/*     */     } 
/*     */     
/* 899 */     if (this.textureHeight < 0.0F) {
/* 900 */       y = yo - y;
/*     */     } else {
/* 902 */       y = yo + y;
/*     */     } 
/*     */     
/* 905 */     int offset = x + y * this.texture.getTextureWidth();
/* 906 */     offset *= this.texture.hasAlpha() ? 4 : 3;
/*     */     
/* 908 */     if (this.texture.hasAlpha()) {
/* 909 */       return new Color(translate(this.pixelData[offset]), translate(this.pixelData[offset + 1]), 
/* 910 */           translate(this.pixelData[offset + 2]), translate(this.pixelData[offset + 3]));
/*     */     }
/* 912 */     return new Color(translate(this.pixelData[offset]), translate(this.pixelData[offset + 1]), 
/* 913 */         translate(this.pixelData[offset + 2]));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDestroyed() {
/* 919 */     return this.destroyed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy() throws SlickException {
/* 924 */     if (this.destroyed) {
/*     */       return;
/*     */     }
/*     */     
/* 928 */     this.destroyed = true;
/* 929 */     this.texture.release();
/* 930 */     GraphicsFactory.releaseGraphicsForImage(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void flushPixelData() {
/* 935 */     this.pixelData = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\Image.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */