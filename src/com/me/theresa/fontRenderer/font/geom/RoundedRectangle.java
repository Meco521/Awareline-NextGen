/*     */ package com.me.theresa.fontRenderer.font.geom;
/*     */ 
/*     */ import com.me.theresa.fontRenderer.font.log.FastTrig;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class RoundedRectangle
/*     */   extends Rectangle
/*     */ {
/*     */   public static final int TOP_LEFT = 1;
/*     */   public static final int TOP_RIGHT = 2;
/*     */   public static final int BOTTOM_RIGHT = 4;
/*     */   public static final int BOTTOM_LEFT = 8;
/*     */   public static final int ALL = 15;
/*     */   private static final int DEFAULT_SEGMENT_COUNT = 25;
/*     */   private float cornerRadius;
/*     */   private final int segmentCount;
/*     */   private final int cornerFlags;
/*     */   
/*     */   public RoundedRectangle(float x, float y, float width, float height, float cornerRadius) {
/*  32 */     this(x, y, width, height, cornerRadius, 25);
/*     */   }
/*     */ 
/*     */   
/*     */   public RoundedRectangle(float x, float y, float width, float height, float cornerRadius, int segmentCount) {
/*  37 */     this(x, y, width, height, cornerRadius, segmentCount, 15);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RoundedRectangle(float x, float y, float width, float height, float cornerRadius, int segmentCount, int cornerFlags) {
/*  43 */     super(x, y, width, height);
/*     */     
/*  45 */     if (cornerRadius < 0.0F) {
/*  46 */       throw new IllegalArgumentException("corner radius must be >= 0");
/*     */     }
/*  48 */     this.x = x;
/*  49 */     this.y = y;
/*  50 */     this.width = width;
/*  51 */     this.height = height;
/*  52 */     this.cornerRadius = cornerRadius;
/*  53 */     this.segmentCount = segmentCount;
/*  54 */     this.pointsDirty = true;
/*  55 */     this.cornerFlags = cornerFlags;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getCornerRadius() {
/*  60 */     return this.cornerRadius;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCornerRadius(float cornerRadius) {
/*  65 */     if (cornerRadius >= 0.0F && 
/*  66 */       cornerRadius != this.cornerRadius) {
/*  67 */       this.cornerRadius = cornerRadius;
/*  68 */       this.pointsDirty = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeight() {
/*  75 */     return this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHeight(float height) {
/*  80 */     if (this.height != height) {
/*  81 */       this.height = height;
/*  82 */       this.pointsDirty = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWidth() {
/*  88 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWidth(float width) {
/*  93 */     if (width != this.width) {
/*  94 */       this.width = width;
/*  95 */       this.pointsDirty = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void createPoints() {
/* 100 */     this.maxX = this.x + this.width;
/* 101 */     this.maxY = this.y + this.height;
/* 102 */     this.minX = this.x;
/* 103 */     this.minY = this.y;
/* 104 */     float useWidth = this.width - 1.0F;
/* 105 */     float useHeight = this.height - 1.0F;
/* 106 */     if (this.cornerRadius == 0.0F) {
/* 107 */       this.points = new float[8];
/*     */       
/* 109 */       this.points[0] = this.x;
/* 110 */       this.points[1] = this.y;
/*     */       
/* 112 */       this.points[2] = this.x + useWidth;
/* 113 */       this.points[3] = this.y;
/*     */       
/* 115 */       this.points[4] = this.x + useWidth;
/* 116 */       this.points[5] = this.y + useHeight;
/*     */       
/* 118 */       this.points[6] = this.x;
/* 119 */       this.points[7] = this.y + useHeight;
/*     */     } else {
/* 121 */       float doubleRadius = this.cornerRadius * 2.0F;
/* 122 */       if (doubleRadius > useWidth) {
/* 123 */         doubleRadius = useWidth;
/* 124 */         this.cornerRadius = doubleRadius / 2.0F;
/*     */       } 
/* 126 */       if (doubleRadius > useHeight) {
/* 127 */         doubleRadius = useHeight;
/* 128 */         this.cornerRadius = doubleRadius / 2.0F;
/*     */       } 
/*     */       
/* 131 */       ArrayList<Float> tempPoints = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 136 */       if ((this.cornerFlags & 0x1) != 0) {
/* 137 */         tempPoints.addAll(createPoints(this.segmentCount, this.cornerRadius, this.x + this.cornerRadius, this.y + this.cornerRadius, 180.0F, 270.0F));
/*     */       } else {
/* 139 */         tempPoints.add(new Float(this.x));
/* 140 */         tempPoints.add(new Float(this.y));
/*     */       } 
/*     */ 
/*     */       
/* 144 */       if ((this.cornerFlags & 0x2) != 0) {
/* 145 */         tempPoints.addAll(createPoints(this.segmentCount, this.cornerRadius, this.x + useWidth - this.cornerRadius, this.y + this.cornerRadius, 270.0F, 360.0F));
/*     */       } else {
/* 147 */         tempPoints.add(new Float(this.x + useWidth));
/* 148 */         tempPoints.add(new Float(this.y));
/*     */       } 
/*     */ 
/*     */       
/* 152 */       if ((this.cornerFlags & 0x4) != 0) {
/* 153 */         tempPoints.addAll(createPoints(this.segmentCount, this.cornerRadius, this.x + useWidth - this.cornerRadius, this.y + useHeight - this.cornerRadius, 0.0F, 90.0F));
/*     */       } else {
/* 155 */         tempPoints.add(new Float(this.x + useWidth));
/* 156 */         tempPoints.add(new Float(this.y + useHeight));
/*     */       } 
/*     */ 
/*     */       
/* 160 */       if ((this.cornerFlags & 0x8) != 0) {
/* 161 */         tempPoints.addAll(createPoints(this.segmentCount, this.cornerRadius, this.x + this.cornerRadius, this.y + useHeight - this.cornerRadius, 90.0F, 180.0F));
/*     */       } else {
/* 163 */         tempPoints.add(new Float(this.x));
/* 164 */         tempPoints.add(new Float(this.y + useHeight));
/*     */       } 
/*     */       
/* 167 */       this.points = new float[tempPoints.size()];
/* 168 */       for (int i = 0; i < tempPoints.size(); i++) {
/* 169 */         this.points[i] = ((Float)tempPoints.get(i)).floatValue();
/*     */       }
/*     */     } 
/*     */     
/* 173 */     findCenter();
/* 174 */     calculateRadius();
/*     */   }
/*     */ 
/*     */   
/*     */   private List createPoints(int numberOfSegments, float radius, float cx, float cy, float start, float end) {
/* 179 */     ArrayList<Float> tempPoints = new ArrayList();
/*     */     
/* 181 */     int step = 360 / numberOfSegments;
/*     */     float a;
/* 183 */     for (a = start; a <= end + step; a += step) {
/* 184 */       float ang = a;
/* 185 */       if (ang > end) {
/* 186 */         ang = end;
/*     */       }
/* 188 */       float x = (float)(cx + FastTrig.cos(Math.toRadians(ang)) * radius);
/* 189 */       float y = (float)(cy + FastTrig.sin(Math.toRadians(ang)) * radius);
/*     */       
/* 191 */       tempPoints.add(new Float(x));
/* 192 */       tempPoints.add(new Float(y));
/*     */     } 
/*     */     
/* 195 */     return tempPoints;
/*     */   }
/*     */   
/*     */   public Shape transform(Transform transform) {
/* 199 */     checkPoints();
/*     */     
/* 201 */     Polygon resultPolygon = new Polygon();
/*     */     
/* 203 */     float[] result = new float[this.points.length];
/* 204 */     transform.transform(this.points, 0, result, 0, this.points.length / 2);
/* 205 */     resultPolygon.points = result;
/* 206 */     resultPolygon.findCenter();
/*     */     
/* 208 */     return resultPolygon;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\geom\RoundedRectangle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */