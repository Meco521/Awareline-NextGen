/*     */ package com.me.theresa.fontRenderer.font;
/*     */ 
/*     */ import com.me.theresa.fontRenderer.font.effect.Renderable;
/*     */ import com.me.theresa.fontRenderer.font.log.Log;
/*     */ import java.util.ArrayList;
/*     */ import org.lwjgl.Sys;
/*     */ 
/*     */ 
/*     */ public class Animation
/*     */   implements Renderable
/*     */ {
/*  12 */   private ArrayList frames = new ArrayList();
/*     */   
/*  14 */   private int currentFrame = -1;
/*     */   
/*  16 */   private long nextChange = 0L;
/*     */   
/*     */   private boolean stopped = false;
/*     */   
/*     */   private long timeLeft;
/*     */   
/*  22 */   private float speed = 1.0F;
/*     */   
/*  24 */   private int stopAt = -2;
/*     */   
/*     */   private long lastUpdate;
/*     */   
/*     */   private boolean firstUpdate = true;
/*     */   
/*     */   private boolean autoUpdate = true;
/*     */   
/*  32 */   private int direction = 1;
/*     */   
/*     */   private boolean pingPong;
/*     */   
/*     */   private boolean loop = true;
/*     */   
/*  38 */   SpriteSheet spriteSheet = null;
/*     */ 
/*     */   
/*     */   public Animation() {
/*  42 */     this(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Animation(Image[] frames, int duration) {
/*  47 */     this(frames, duration, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Animation(Image[] frames, int[] durations) {
/*  52 */     this(frames, durations, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Animation(boolean autoUpdate) {
/*  57 */     this.currentFrame = 0;
/*  58 */     this.autoUpdate = autoUpdate;
/*     */   }
/*     */ 
/*     */   
/*     */   public Animation(Image[] frames, int duration, boolean autoUpdate) {
/*  63 */     for (int i = 0; i < frames.length; i++) {
/*  64 */       addFrame(frames[i], duration);
/*     */     }
/*  66 */     this.currentFrame = 0;
/*  67 */     this.autoUpdate = autoUpdate;
/*     */   }
/*     */ 
/*     */   
/*     */   public Animation(Image[] frames, int[] durations, boolean autoUpdate) {
/*  72 */     this.autoUpdate = autoUpdate;
/*  73 */     if (frames.length != durations.length) {
/*  74 */       throw new RuntimeException("There must be one duration per frame");
/*     */     }
/*     */     
/*  77 */     for (int i = 0; i < frames.length; i++) {
/*  78 */       addFrame(frames[i], durations[i]);
/*     */     }
/*  80 */     this.currentFrame = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Animation(SpriteSheet frames, int duration) {
/*  85 */     this(frames, 0, 0, frames.getHorizontalCount() - 1, frames.getVerticalCount() - 1, true, duration, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Animation(SpriteSheet frames, int x1, int y1, int x2, int y2, boolean horizontalScan, int duration, boolean autoUpdate) {
/*  90 */     this.autoUpdate = autoUpdate;
/*     */     
/*  92 */     if (!horizontalScan) {
/*  93 */       for (int x = x1; x <= x2; x++) {
/*  94 */         for (int y = y1; y <= y2; y++) {
/*  95 */           addFrame(frames.getSprite(x, y), duration);
/*     */         }
/*     */       } 
/*     */     } else {
/*  99 */       for (int y = y1; y <= y2; y++) {
/* 100 */         for (int x = x1; x <= x2; x++) {
/* 101 */           addFrame(frames.getSprite(x, y), duration);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Animation(SpriteSheet ss, int[] frames, int[] duration) {
/* 109 */     this.spriteSheet = ss;
/* 110 */     int x = -1;
/* 111 */     int y = -1;
/*     */     
/* 113 */     for (int i = 0; i < frames.length / 2; i++) {
/* 114 */       x = frames[i << 1];
/* 115 */       y = frames[(i << 1) + 1];
/* 116 */       addFrame(duration[i], x, y);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFrame(int duration, int x, int y) {
/* 122 */     if (duration == 0) {
/* 123 */       Log.error("Invalid duration: " + duration);
/* 124 */       throw new RuntimeException("Invalid duration: " + duration);
/*     */     } 
/*     */     
/* 127 */     if (this.frames.isEmpty()) {
/* 128 */       this.nextChange = (int)(duration / this.speed);
/*     */     }
/*     */     
/* 131 */     this.frames.add(new Frame(duration, x, y));
/* 132 */     this.currentFrame = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAutoUpdate(boolean auto) {
/* 137 */     this.autoUpdate = auto;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPingPong(boolean pingPong) {
/* 142 */     this.pingPong = pingPong;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStopped() {
/* 147 */     return this.stopped;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSpeed(float spd) {
/* 152 */     if (spd > 0.0F) {
/*     */       
/* 154 */       this.nextChange = (long)((float)this.nextChange * this.speed / spd);
/*     */       
/* 156 */       this.speed = spd;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSpeed() {
/* 162 */     return this.speed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 167 */     if (this.frames.size() == 0) {
/*     */       return;
/*     */     }
/* 170 */     this.timeLeft = this.nextChange;
/* 171 */     this.stopped = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 176 */     if (!this.stopped) {
/*     */       return;
/*     */     }
/* 179 */     if (this.frames.size() == 0) {
/*     */       return;
/*     */     }
/* 182 */     this.stopped = false;
/* 183 */     this.nextChange = this.timeLeft;
/*     */   }
/*     */ 
/*     */   
/*     */   public void restart() {
/* 188 */     if (this.frames.size() == 0) {
/*     */       return;
/*     */     }
/* 191 */     this.stopped = false;
/* 192 */     this.currentFrame = 0;
/* 193 */     this.nextChange = (int)(((Frame)this.frames.get(0)).duration / this.speed);
/* 194 */     this.firstUpdate = true;
/* 195 */     this.lastUpdate = 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFrame(Image frame, int duration) {
/* 200 */     if (duration == 0) {
/* 201 */       Log.error("Invalid duration: " + duration);
/* 202 */       throw new RuntimeException("Invalid duration: " + duration);
/*     */     } 
/*     */     
/* 205 */     if (this.frames.isEmpty()) {
/* 206 */       this.nextChange = (int)(duration / this.speed);
/*     */     }
/*     */     
/* 209 */     this.frames.add(new Frame(frame, duration));
/* 210 */     this.currentFrame = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw() {
/* 215 */     draw(0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(float x, float y) {
/* 220 */     draw(x, y, getWidth(), getHeight());
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(float x, float y, Color filter) {
/* 225 */     draw(x, y, getWidth(), getHeight(), filter);
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(float x, float y, float width, float height) {
/* 230 */     draw(x, y, width, height, Color.white);
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(float x, float y, float width, float height, Color col) {
/* 235 */     if (this.frames.size() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 239 */     if (this.autoUpdate) {
/* 240 */       long now = getTime();
/* 241 */       long delta = now - this.lastUpdate;
/* 242 */       if (this.firstUpdate) {
/* 243 */         delta = 0L;
/* 244 */         this.firstUpdate = false;
/*     */       } 
/* 246 */       this.lastUpdate = now;
/* 247 */       nextFrame(delta);
/*     */     } 
/*     */     
/* 250 */     Frame frame = this.frames.get(this.currentFrame);
/* 251 */     frame.image.draw(x, y, width, height, col);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderInUse(int x, int y) {
/* 256 */     if (this.frames.size() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 260 */     if (this.autoUpdate) {
/* 261 */       long now = getTime();
/* 262 */       long delta = now - this.lastUpdate;
/* 263 */       if (this.firstUpdate) {
/* 264 */         delta = 0L;
/* 265 */         this.firstUpdate = false;
/*     */       } 
/* 267 */       this.lastUpdate = now;
/* 268 */       nextFrame(delta);
/*     */     } 
/*     */     
/* 271 */     Frame frame = this.frames.get(this.currentFrame);
/* 272 */     this.spriteSheet.renderInUse(x, y, frame.x, frame.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 277 */     return ((Frame)this.frames.get(this.currentFrame)).image.getWidth();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 282 */     return ((Frame)this.frames.get(this.currentFrame)).image.getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawFlash(float x, float y, float width, float height) {
/* 287 */     drawFlash(x, y, width, height, Color.white);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawFlash(float x, float y, float width, float height, Color col) {
/* 292 */     if (this.frames.size() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 296 */     if (this.autoUpdate) {
/* 297 */       long now = getTime();
/* 298 */       long delta = now - this.lastUpdate;
/* 299 */       if (this.firstUpdate) {
/* 300 */         delta = 0L;
/* 301 */         this.firstUpdate = false;
/*     */       } 
/* 303 */       this.lastUpdate = now;
/* 304 */       nextFrame(delta);
/*     */     } 
/*     */     
/* 307 */     Frame frame = this.frames.get(this.currentFrame);
/* 308 */     frame.image.drawFlash(x, y, width, height, col);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateNoDraw() {
/* 313 */     if (this.autoUpdate) {
/* 314 */       long now = getTime();
/* 315 */       long delta = now - this.lastUpdate;
/* 316 */       if (this.firstUpdate) {
/* 317 */         delta = 0L;
/* 318 */         this.firstUpdate = false;
/*     */       } 
/* 320 */       this.lastUpdate = now;
/* 321 */       nextFrame(delta);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(long delta) {
/* 327 */     nextFrame(delta);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFrame() {
/* 332 */     return this.currentFrame;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCurrentFrame(int index) {
/* 337 */     this.currentFrame = index;
/*     */   }
/*     */ 
/*     */   
/*     */   public Image getImage(int index) {
/* 342 */     Frame frame = this.frames.get(index);
/* 343 */     return frame.image;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFrameCount() {
/* 348 */     return this.frames.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public Image getCurrentFrame() {
/* 353 */     Frame frame = this.frames.get(this.currentFrame);
/* 354 */     return frame.image;
/*     */   }
/*     */ 
/*     */   
/*     */   private void nextFrame(long delta) {
/* 359 */     if (this.stopped) {
/*     */       return;
/*     */     }
/* 362 */     if (this.frames.size() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 366 */     this.nextChange -= delta;
/*     */     
/* 368 */     while (this.nextChange < 0L && !this.stopped) {
/* 369 */       if (this.currentFrame == this.stopAt) {
/* 370 */         this.stopped = true;
/*     */         break;
/*     */       } 
/* 373 */       if (this.currentFrame == this.frames.size() - 1 && !this.loop && !this.pingPong) {
/* 374 */         this.stopped = true;
/*     */         break;
/*     */       } 
/* 377 */       this.currentFrame = (this.currentFrame + this.direction) % this.frames.size();
/*     */       
/* 379 */       if (this.pingPong) {
/* 380 */         if (this.currentFrame <= 0) {
/* 381 */           this.currentFrame = 0;
/* 382 */           this.direction = 1;
/* 383 */           if (!this.loop) {
/* 384 */             this.stopped = true;
/*     */             break;
/*     */           } 
/* 387 */         } else if (this.currentFrame >= this.frames.size() - 1) {
/* 388 */           this.currentFrame = this.frames.size() - 1;
/* 389 */           this.direction = -1;
/*     */         } 
/*     */       }
/* 392 */       int realDuration = (int)(((Frame)this.frames.get(this.currentFrame)).duration / this.speed);
/* 393 */       this.nextChange += realDuration;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLooping(boolean loop) {
/* 399 */     this.loop = loop;
/*     */   }
/*     */ 
/*     */   
/*     */   private long getTime() {
/* 404 */     return Sys.getTime() * 1000L / Sys.getTimerResolution();
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopAt(int frameIndex) {
/* 409 */     this.stopAt = frameIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDuration(int index) {
/* 414 */     return ((Frame)this.frames.get(index)).duration;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDuration(int index, int duration) {
/* 419 */     ((Frame)this.frames.get(index)).duration = duration;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getDurations() {
/* 424 */     int[] durations = new int[this.frames.size()];
/* 425 */     for (int i = 0; i < this.frames.size(); i++) {
/* 426 */       durations[i] = getDuration(i);
/*     */     }
/*     */     
/* 429 */     return durations;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 434 */     StringBuilder res = new StringBuilder("[Animation (" + this.frames.size() + ") ");
/* 435 */     for (int i = 0; i < this.frames.size(); i++) {
/* 436 */       Frame frame = this.frames.get(i);
/* 437 */       res.append(frame.duration).append(",");
/*     */     } 
/*     */     
/* 440 */     res.append("]");
/* 441 */     return res.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public Animation copy() {
/* 446 */     Animation copy = new Animation();
/*     */     
/* 448 */     copy.spriteSheet = this.spriteSheet;
/* 449 */     copy.frames = this.frames;
/* 450 */     copy.autoUpdate = this.autoUpdate;
/* 451 */     copy.direction = this.direction;
/* 452 */     copy.loop = this.loop;
/* 453 */     copy.pingPong = this.pingPong;
/* 454 */     copy.speed = this.speed;
/*     */     
/* 456 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   private class Frame
/*     */   {
/*     */     public Image image;
/*     */     
/*     */     public int duration;
/*     */     
/* 466 */     public int x = -1;
/*     */     
/* 468 */     public int y = -1;
/*     */ 
/*     */     
/*     */     public Frame(Image image, int duration) {
/* 472 */       this.image = image;
/* 473 */       this.duration = duration;
/*     */     }
/*     */     
/*     */     public Frame(int duration, int x, int y) {
/* 477 */       this.image = Animation.this.spriteSheet.getSubImage(x, y);
/* 478 */       this.duration = duration;
/* 479 */       this.x = x;
/* 480 */       this.y = y;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\Animation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */