/*     */ package net.optifine.render;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.VboRenderList;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.optifine.util.LinkedList;
/*     */ 
/*     */ 
/*     */ public class VboRegion
/*     */ {
/*     */   private final EnumWorldBlockLayer layer;
/*  17 */   private int glBufferId = OpenGlHelper.glGenBuffers();
/*  18 */   private int capacity = 4096;
/*     */   private int positionTop;
/*     */   private int sizeUsed;
/*  21 */   private final LinkedList<VboRange> rangeList = new LinkedList();
/*     */   
/*     */   private VboRange compactRangeLast;
/*     */   private IntBuffer bufferIndexVertex;
/*     */   private IntBuffer bufferCountVertex;
/*     */   private int drawMode;
/*     */   private final int vertexBytes;
/*     */   
/*     */   public VboRegion(EnumWorldBlockLayer layer) {
/*  30 */     this.bufferIndexVertex = Config.createDirectIntBuffer(this.capacity);
/*  31 */     this.bufferCountVertex = Config.createDirectIntBuffer(this.capacity);
/*  32 */     this.drawMode = 7;
/*  33 */     this.vertexBytes = DefaultVertexFormats.BLOCK.getNextOffset();
/*  34 */     this.layer = layer;
/*  35 */     bindBuffer();
/*  36 */     long i = toBytes(this.capacity);
/*  37 */     OpenGlHelper.glBufferData(OpenGlHelper.GL_ARRAY_BUFFER, i, OpenGlHelper.GL_STATIC_DRAW);
/*  38 */     unbindBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public void bufferData(ByteBuffer data, VboRange range) {
/*  43 */     int i = range.getPosition();
/*  44 */     int j = range.getSize();
/*  45 */     int k = toVertex(data.limit());
/*     */     
/*  47 */     if (k <= 0) {
/*     */       
/*  49 */       if (i >= 0)
/*     */       {
/*  51 */         range.setPosition(-1);
/*  52 */         range.setSize(0);
/*  53 */         this.rangeList.remove(range.getNode());
/*  54 */         this.sizeUsed -= j;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  59 */       if (k > j) {
/*     */         
/*  61 */         range.setPosition(this.positionTop);
/*  62 */         range.setSize(k);
/*  63 */         this.positionTop += k;
/*     */         
/*  65 */         if (i >= 0)
/*     */         {
/*  67 */           this.rangeList.remove(range.getNode());
/*     */         }
/*     */         
/*  70 */         this.rangeList.addLast(range.getNode());
/*     */       } 
/*     */       
/*  73 */       range.setSize(k);
/*  74 */       this.sizeUsed += k - j;
/*  75 */       checkVboSize(range.getPositionNext());
/*  76 */       long l = toBytes(range.getPosition());
/*  77 */       bindBuffer();
/*  78 */       OpenGlHelper.glBufferSubData(OpenGlHelper.GL_ARRAY_BUFFER, l, data);
/*  79 */       unbindBuffer();
/*     */       
/*  81 */       if (this.positionTop > this.sizeUsed * 11 / 10)
/*     */       {
/*  83 */         compactRanges(1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void compactRanges(int countMax) {
/*  90 */     if (!this.rangeList.isEmpty()) {
/*     */       int i;
/*  92 */       VboRange vborange = this.compactRangeLast;
/*     */       
/*  94 */       if (vborange == null || !this.rangeList.contains(vborange.getNode()))
/*     */       {
/*  96 */         vborange = (VboRange)this.rangeList.getFirst().getItem();
/*     */       }
/*     */ 
/*     */       
/* 100 */       VboRange vborange1 = vborange.getPrev();
/*     */       
/* 102 */       if (vborange1 == null) {
/*     */         
/* 104 */         i = 0;
/*     */       }
/*     */       else {
/*     */         
/* 108 */         i = vborange1.getPositionNext();
/*     */       } 
/*     */       
/* 111 */       int j = 0;
/*     */       
/* 113 */       while (vborange != null && j < countMax) {
/*     */         
/* 115 */         j++;
/*     */         
/* 117 */         if (vborange.getPosition() == i) {
/*     */           
/* 119 */           i += vborange.getSize();
/* 120 */           vborange = vborange.getNext();
/*     */           
/*     */           continue;
/*     */         } 
/* 124 */         int k = vborange.getPosition() - i;
/*     */         
/* 126 */         if (vborange.getSize() <= k) {
/*     */           
/* 128 */           copyVboData(vborange.getPosition(), i, vborange.getSize());
/* 129 */           vborange.setPosition(i);
/* 130 */           i += vborange.getSize();
/* 131 */           vborange = vborange.getNext();
/*     */           
/*     */           continue;
/*     */         } 
/* 135 */         checkVboSize(this.positionTop + vborange.getSize());
/* 136 */         copyVboData(vborange.getPosition(), this.positionTop, vborange.getSize());
/* 137 */         vborange.setPosition(this.positionTop);
/* 138 */         this.positionTop += vborange.getSize();
/* 139 */         VboRange vborange2 = vborange.getNext();
/* 140 */         this.rangeList.remove(vborange.getNode());
/* 141 */         this.rangeList.addLast(vborange.getNode());
/* 142 */         vborange = vborange2;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 147 */       if (vborange == null)
/*     */       {
/* 149 */         this.positionTop = ((VboRange)this.rangeList.getLast().getItem()).getPositionNext();
/*     */       }
/*     */       
/* 152 */       this.compactRangeLast = vborange;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkRanges() {
/* 158 */     int i = 0;
/* 159 */     int j = 0;
/*     */     
/* 161 */     for (VboRange vborange = (VboRange)this.rangeList.getFirst().getItem(); vborange != null; vborange = vborange.getNext()) {
/*     */       
/* 163 */       i++;
/* 164 */       j += vborange.getSize();
/*     */       
/* 166 */       if (vborange.getPosition() < 0 || vborange.getSize() <= 0 || vborange.getPositionNext() > this.positionTop)
/*     */       {
/* 168 */         throw new RuntimeException("Invalid range: " + vborange);
/*     */       }
/*     */       
/* 171 */       VboRange vborange1 = vborange.getPrev();
/*     */       
/* 173 */       if (vborange1 != null && vborange.getPosition() < vborange1.getPositionNext())
/*     */       {
/* 175 */         throw new RuntimeException("Invalid range: " + vborange);
/*     */       }
/*     */       
/* 178 */       VboRange vborange2 = vborange.getNext();
/*     */       
/* 180 */       if (vborange2 != null && vborange.getPositionNext() > vborange2.getPosition())
/*     */       {
/* 182 */         throw new RuntimeException("Invalid range: " + vborange);
/*     */       }
/*     */     } 
/*     */     
/* 186 */     if (i != this.rangeList.getSize())
/*     */     {
/* 188 */       throw new RuntimeException("Invalid count: " + i + " <> " + this.rangeList.getSize());
/*     */     }
/* 190 */     if (j != this.sizeUsed)
/*     */     {
/* 192 */       throw new RuntimeException("Invalid size: " + j + " <> " + this.sizeUsed);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkVboSize(int sizeMin) {
/* 198 */     if (this.capacity < sizeMin)
/*     */     {
/* 200 */       expandVbo(sizeMin);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void copyVboData(int posFrom, int posTo, int size) {
/* 206 */     long i = toBytes(posFrom);
/* 207 */     long j = toBytes(posTo);
/* 208 */     long k = toBytes(size);
/* 209 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_READ_BUFFER, this.glBufferId);
/* 210 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_WRITE_BUFFER, this.glBufferId);
/* 211 */     OpenGlHelper.glCopyBufferSubData(OpenGlHelper.GL_COPY_READ_BUFFER, OpenGlHelper.GL_COPY_WRITE_BUFFER, i, j, k);
/* 212 */     Config.checkGlError("Copy VBO range");
/* 213 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_READ_BUFFER, 0);
/* 214 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_WRITE_BUFFER, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void expandVbo(int sizeMin) {
/*     */     int i;
/* 222 */     for (i = this.capacity * 6 / 4; i < sizeMin; i = i * 6 / 4);
/*     */ 
/*     */ 
/*     */     
/* 226 */     long j = toBytes(this.capacity);
/* 227 */     long k = toBytes(i);
/* 228 */     int l = OpenGlHelper.glGenBuffers();
/* 229 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, l);
/* 230 */     OpenGlHelper.glBufferData(OpenGlHelper.GL_ARRAY_BUFFER, k, OpenGlHelper.GL_STATIC_DRAW);
/* 231 */     Config.checkGlError("Expand VBO");
/* 232 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
/* 233 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_READ_BUFFER, this.glBufferId);
/* 234 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_WRITE_BUFFER, l);
/* 235 */     OpenGlHelper.glCopyBufferSubData(OpenGlHelper.GL_COPY_READ_BUFFER, OpenGlHelper.GL_COPY_WRITE_BUFFER, 0L, 0L, j);
/* 236 */     Config.checkGlError("Copy VBO: " + k);
/* 237 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_READ_BUFFER, 0);
/* 238 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_WRITE_BUFFER, 0);
/* 239 */     OpenGlHelper.glDeleteBuffers(this.glBufferId);
/* 240 */     this.bufferIndexVertex = Config.createDirectIntBuffer(i);
/* 241 */     this.bufferCountVertex = Config.createDirectIntBuffer(i);
/* 242 */     this.glBufferId = l;
/* 243 */     this.capacity = i;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindBuffer() {
/* 248 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, this.glBufferId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawArrays(int drawMode, VboRange range) {
/* 253 */     if (this.drawMode != drawMode) {
/*     */       
/* 255 */       if (this.bufferIndexVertex.position() > 0)
/*     */       {
/* 257 */         throw new IllegalArgumentException("Mixed region draw modes: " + this.drawMode + " != " + drawMode);
/*     */       }
/*     */       
/* 260 */       this.drawMode = drawMode;
/*     */     } 
/*     */     
/* 263 */     this.bufferIndexVertex.put(range.getPosition());
/* 264 */     this.bufferCountVertex.put(range.getSize());
/*     */   }
/*     */ 
/*     */   
/*     */   public void finishDraw(VboRenderList vboRenderList) {
/* 269 */     bindBuffer();
/* 270 */     vboRenderList.setupArrayPointers();
/* 271 */     this.bufferIndexVertex.flip();
/* 272 */     this.bufferCountVertex.flip();
/* 273 */     GlStateManager.glMultiDrawArrays(this.drawMode, this.bufferIndexVertex, this.bufferCountVertex);
/* 274 */     this.bufferIndexVertex.limit(this.bufferIndexVertex.capacity());
/* 275 */     this.bufferCountVertex.limit(this.bufferCountVertex.capacity());
/*     */     
/* 277 */     if (this.positionTop > this.sizeUsed * 11 / 10)
/*     */     {
/* 279 */       compactRanges(1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void unbindBuffer() {
/* 285 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteGlBuffers() {
/* 290 */     if (this.glBufferId >= 0) {
/*     */       
/* 292 */       OpenGlHelper.glDeleteBuffers(this.glBufferId);
/* 293 */       this.glBufferId = -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private long toBytes(int vertex) {
/* 299 */     return vertex * this.vertexBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   private int toVertex(long bytes) {
/* 304 */     return (int)(bytes / this.vertexBytes);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPositionTop() {
/* 309 */     return this.positionTop;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\render\VboRegion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */