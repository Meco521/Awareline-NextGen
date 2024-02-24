/*     */ package com.me.theresa.fontRenderer.font;
/*     */ 
/*     */ import com.me.theresa.fontRenderer.font.opengl.Texture;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpriteSheet
/*     */   extends Image
/*     */ {
/*     */   private final int tw;
/*     */   private final int th;
/*  15 */   private int margin = 0;
/*     */   
/*     */   private Image[][] subImages;
/*     */   
/*     */   private int spacing;
/*     */   
/*     */   private final Image target;
/*     */ 
/*     */   
/*     */   public SpriteSheet(URL ref, int tw, int th) throws SlickException, IOException {
/*  25 */     this(new Image(ref.openStream(), ref.toString(), false), tw, th);
/*     */   }
/*     */ 
/*     */   
/*     */   public SpriteSheet(Image image, int tw, int th) {
/*  30 */     super(image);
/*     */     
/*  32 */     this.target = image;
/*  33 */     this.tw = tw;
/*  34 */     this.th = th;
/*     */ 
/*     */ 
/*     */     
/*  38 */     initImpl();
/*     */   }
/*     */ 
/*     */   
/*     */   public SpriteSheet(Image image, int tw, int th, int spacing, int margin) {
/*  43 */     super(image);
/*     */     
/*  45 */     this.target = image;
/*  46 */     this.tw = tw;
/*  47 */     this.th = th;
/*  48 */     this.spacing = spacing;
/*  49 */     this.margin = margin;
/*     */ 
/*     */ 
/*     */     
/*  53 */     initImpl();
/*     */   }
/*     */ 
/*     */   
/*     */   public SpriteSheet(Image image, int tw, int th, int spacing) {
/*  58 */     this(image, tw, th, spacing, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public SpriteSheet(String ref, int tw, int th, int spacing) throws SlickException {
/*  63 */     this(ref, tw, th, (Color)null, spacing);
/*     */   }
/*     */ 
/*     */   
/*     */   public SpriteSheet(String ref, int tw, int th) throws SlickException {
/*  68 */     this(ref, tw, th, (Color)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public SpriteSheet(String ref, int tw, int th, Color col) throws SlickException {
/*  73 */     this(ref, tw, th, col, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public SpriteSheet(String ref, int tw, int th, Color col, int spacing) throws SlickException {
/*  78 */     super(ref, false, 2, col);
/*     */     
/*  80 */     this.target = this;
/*  81 */     this.tw = tw;
/*  82 */     this.th = th;
/*  83 */     this.spacing = spacing;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpriteSheet(String name, InputStream ref, int tw, int th) throws SlickException {
/*  88 */     super(ref, name, false);
/*     */     
/*  90 */     this.target = this;
/*  91 */     this.tw = tw;
/*  92 */     this.th = th;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initImpl() {
/*  97 */     if (this.subImages != null) {
/*     */       return;
/*     */     }
/*     */     
/* 101 */     int tilesAcross = (getWidth() - (this.margin << 1) - this.tw) / (this.tw + this.spacing) + 1;
/* 102 */     int tilesDown = (getHeight() - (this.margin << 1) - this.th) / (this.th + this.spacing) + 1;
/* 103 */     if ((getHeight() - this.th) % (this.th + this.spacing) != 0) {
/* 104 */       tilesDown++;
/*     */     }
/*     */     
/* 107 */     this.subImages = new Image[tilesAcross][tilesDown];
/* 108 */     for (int x = 0; x < tilesAcross; x++) {
/* 109 */       for (int y = 0; y < tilesDown; y++) {
/* 110 */         this.subImages[x][y] = getSprite(x, y);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Image getSubImage(int x, int y) {
/* 117 */     init();
/*     */     
/* 119 */     if (x < 0 || x >= this.subImages.length) {
/* 120 */       throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
/*     */     }
/* 122 */     if (y < 0 || y >= (this.subImages[0]).length) {
/* 123 */       throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
/*     */     }
/*     */     
/* 126 */     return this.subImages[x][y];
/*     */   }
/*     */ 
/*     */   
/*     */   public Image getSprite(int x, int y) {
/* 131 */     this.target.init();
/* 132 */     initImpl();
/*     */     
/* 134 */     if (x < 0 || x >= this.subImages.length) {
/* 135 */       throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
/*     */     }
/* 137 */     if (y < 0 || y >= (this.subImages[0]).length) {
/* 138 */       throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
/*     */     }
/*     */     
/* 141 */     return this.target.getSubImage(x * (this.tw + this.spacing) + this.margin, y * (this.th + this.spacing) + this.margin, this.tw, this.th);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHorizontalCount() {
/* 146 */     this.target.init();
/* 147 */     initImpl();
/*     */     
/* 149 */     return this.subImages.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVerticalCount() {
/* 154 */     this.target.init();
/* 155 */     initImpl();
/*     */     
/* 157 */     return (this.subImages[0]).length;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderInUse(int x, int y, int sx, int sy) {
/* 162 */     this.subImages[sx][sy].drawEmbedded(x, y, this.tw, this.th);
/*     */   }
/*     */ 
/*     */   
/*     */   public void endUse() {
/* 167 */     if (this.target == this) {
/* 168 */       super.endUse();
/*     */       return;
/*     */     } 
/* 171 */     this.target.endUse();
/*     */   }
/*     */ 
/*     */   
/*     */   public void startUse() {
/* 176 */     if (this.target == this) {
/* 177 */       super.startUse();
/*     */       return;
/*     */     } 
/* 180 */     this.target.startUse();
/*     */   }
/*     */   
/*     */   public void setTexture(Texture texture) {
/* 184 */     if (this.target == this) {
/* 185 */       super.setTexture(texture);
/*     */       return;
/*     */     } 
/* 188 */     this.target.setTexture(texture);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\SpriteSheet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */