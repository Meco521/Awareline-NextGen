/*      */ package net.minecraft.client.renderer;
/*      */ 
/*      */ import com.google.common.primitives.Floats;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.nio.ShortBuffer;
/*      */ import java.util.Arrays;
/*      */ import java.util.BitSet;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*      */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.optifine.SmartAnimations;
/*      */ import net.optifine.render.RenderEnv;
/*      */ import net.optifine.shaders.SVertexBuilder;
/*      */ import net.optifine.util.TextureUtils;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ 
/*      */ public class WorldRenderer {
/*      */   private ByteBuffer byteBuffer;
/*      */   public IntBuffer rawIntBuffer;
/*      */   private ShortBuffer rawShortBuffer;
/*      */   public FloatBuffer rawFloatBuffer;
/*      */   public int vertexCount;
/*      */   private VertexFormatElement vertexFormatElement;
/*      */   private int vertexFormatIndex;
/*      */   private boolean noColor;
/*      */   public int drawMode;
/*      */   private double xOffset;
/*      */   private double yOffset;
/*      */   private double zOffset;
/*      */   private VertexFormat vertexFormat;
/*      */   private boolean isDrawing;
/*   41 */   private EnumWorldBlockLayer blockLayer = null;
/*   42 */   private boolean[] drawnIcons = new boolean[256];
/*   43 */   private TextureAtlasSprite[] quadSprites = null;
/*   44 */   private TextureAtlasSprite[] quadSpritesPrev = null;
/*   45 */   private TextureAtlasSprite quadSprite = null;
/*      */   public SVertexBuilder sVertexBuilder;
/*   47 */   public RenderEnv renderEnv = null;
/*   48 */   public BitSet animatedSprites = null;
/*   49 */   public BitSet animatedSpritesCached = new BitSet();
/*      */   
/*      */   private boolean modeTriangles = false;
/*      */   private ByteBuffer byteBufferTriangles;
/*      */   
/*      */   public WorldRenderer(int bufferSizeIn) {
/*   55 */     this.byteBuffer = GLAllocation.createDirectByteBuffer(bufferSizeIn << 2);
/*   56 */     this.rawIntBuffer = this.byteBuffer.asIntBuffer();
/*   57 */     this.rawShortBuffer = this.byteBuffer.asShortBuffer();
/*   58 */     this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
/*   59 */     SVertexBuilder.initVertexBuilder(this);
/*      */   }
/*      */   
/*      */   public void addVertexWithUV(double p_addVertexWithUV_0_, double p_addVertexWithUV_2_, double p_addVertexWithUV_4_, double p_addVertexWithUV_6_, double p_addVertexWithUV_8_) {
/*   63 */     Tessellator.getInstance().getWorldRenderer().pos(p_addVertexWithUV_0_, p_addVertexWithUV_2_, p_addVertexWithUV_4_).tex(p_addVertexWithUV_6_, p_addVertexWithUV_8_).endVertex();
/*      */   }
/*      */ 
/*      */   
/*      */   private void growBuffer(int p_181670_1_) {
/*   68 */     if (p_181670_1_ > this.rawIntBuffer.remaining()) {
/*      */       
/*   70 */       int i = this.byteBuffer.capacity();
/*   71 */       int j = i % 2097152;
/*   72 */       int k = j + (((this.rawIntBuffer.position() + p_181670_1_ << 2) - j) / 2097152 + 1 << 21);
/*   73 */       LogManager.getLogger().warn("Needed to grow BufferBuilder buffer: Old size " + i + " bytes, new size " + k + " bytes.");
/*   74 */       int l = this.rawIntBuffer.position();
/*   75 */       ByteBuffer bytebuffer = GLAllocation.createDirectByteBuffer(k);
/*   76 */       this.byteBuffer.position(0);
/*   77 */       bytebuffer.put(this.byteBuffer);
/*   78 */       bytebuffer.rewind();
/*   79 */       this.byteBuffer = bytebuffer;
/*   80 */       this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
/*   81 */       this.rawIntBuffer = this.byteBuffer.asIntBuffer();
/*   82 */       this.rawIntBuffer.position(l);
/*   83 */       this.rawShortBuffer = this.byteBuffer.asShortBuffer();
/*   84 */       this.rawShortBuffer.position(l << 1);
/*      */       
/*   86 */       if (this.quadSprites != null) {
/*      */         
/*   88 */         TextureAtlasSprite[] atextureatlassprite = this.quadSprites;
/*   89 */         int i1 = getBufferQuadSize();
/*   90 */         this.quadSprites = new TextureAtlasSprite[i1];
/*   91 */         System.arraycopy(atextureatlassprite, 0, this.quadSprites, 0, Math.min(atextureatlassprite.length, this.quadSprites.length));
/*   92 */         this.quadSpritesPrev = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void sortVertexData(float p_181674_1_, float p_181674_2_, float p_181674_3_) {
/*   99 */     int i = this.vertexCount / 4;
/*  100 */     float[] afloat = new float[i];
/*      */     
/*  102 */     for (int j = 0; j < i; j++)
/*      */     {
/*  104 */       afloat[j] = getDistanceSq(this.rawFloatBuffer, (float)(p_181674_1_ + this.xOffset), (float)(p_181674_2_ + this.yOffset), (float)(p_181674_3_ + this.zOffset), this.vertexFormat.getIntegerSize(), j * this.vertexFormat.getNextOffset());
/*      */     }
/*      */     
/*  107 */     Integer[] ainteger = new Integer[i];
/*      */     
/*  109 */     for (int k = 0; k < ainteger.length; k++)
/*      */     {
/*  111 */       ainteger[k] = Integer.valueOf(k);
/*      */     }
/*      */     
/*  114 */     Arrays.sort(ainteger, (p_compare_1_, p_compare_2_) -> Floats.compare(afloat[p_compare_2_.intValue()], afloat[p_compare_1_.intValue()]));
/*  115 */     BitSet bitset = new BitSet();
/*  116 */     int l = this.vertexFormat.getNextOffset();
/*  117 */     int[] aint = new int[l];
/*      */     
/*  119 */     for (int l1 = 0; (l1 = bitset.nextClearBit(l1)) < ainteger.length; l1++) {
/*      */       
/*  121 */       int i1 = ainteger[l1].intValue();
/*      */       
/*  123 */       if (i1 != l1) {
/*      */         
/*  125 */         this.rawIntBuffer.limit(i1 * l + l);
/*  126 */         this.rawIntBuffer.position(i1 * l);
/*  127 */         this.rawIntBuffer.get(aint);
/*  128 */         int j1 = i1;
/*      */         int k1;
/*  130 */         for (k1 = ainteger[i1].intValue(); j1 != l1; k1 = ainteger[k1].intValue()) {
/*      */           
/*  132 */           this.rawIntBuffer.limit(k1 * l + l);
/*  133 */           this.rawIntBuffer.position(k1 * l);
/*  134 */           IntBuffer intbuffer = this.rawIntBuffer.slice();
/*  135 */           this.rawIntBuffer.limit(j1 * l + l);
/*  136 */           this.rawIntBuffer.position(j1 * l);
/*  137 */           this.rawIntBuffer.put(intbuffer);
/*  138 */           bitset.set(j1);
/*  139 */           j1 = k1;
/*      */         } 
/*      */         
/*  142 */         this.rawIntBuffer.limit(l1 * l + l);
/*  143 */         this.rawIntBuffer.position(l1 * l);
/*  144 */         this.rawIntBuffer.put(aint);
/*      */       } 
/*      */       
/*  147 */       bitset.set(l1);
/*      */     } 
/*      */     
/*  150 */     this.rawIntBuffer.limit(this.rawIntBuffer.capacity());
/*  151 */     this.rawIntBuffer.position(getBufferSize());
/*      */     
/*  153 */     if (this.quadSprites != null) {
/*      */       
/*  155 */       TextureAtlasSprite[] atextureatlassprite = new TextureAtlasSprite[this.vertexCount / 4];
/*  156 */       int i2 = this.vertexFormat.getNextOffset() / 4 << 2;
/*      */       
/*  158 */       for (int j2 = 0; j2 < ainteger.length; j2++) {
/*      */         
/*  160 */         int k2 = ainteger[j2].intValue();
/*  161 */         atextureatlassprite[j2] = this.quadSprites[k2];
/*      */       } 
/*      */       
/*  164 */       System.arraycopy(atextureatlassprite, 0, this.quadSprites, 0, atextureatlassprite.length);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public State getVertexState() {
/*  170 */     this.rawIntBuffer.rewind();
/*  171 */     int i = getBufferSize();
/*  172 */     this.rawIntBuffer.limit(i);
/*  173 */     int[] aint = new int[i];
/*  174 */     this.rawIntBuffer.get(aint);
/*  175 */     this.rawIntBuffer.limit(this.rawIntBuffer.capacity());
/*  176 */     this.rawIntBuffer.position(i);
/*  177 */     TextureAtlasSprite[] atextureatlassprite = null;
/*      */     
/*  179 */     if (this.quadSprites != null) {
/*      */       
/*  181 */       int j = this.vertexCount / 4;
/*  182 */       atextureatlassprite = new TextureAtlasSprite[j];
/*  183 */       System.arraycopy(this.quadSprites, 0, atextureatlassprite, 0, j);
/*      */     } 
/*      */     
/*  186 */     return new State(aint, new VertexFormat(this.vertexFormat), atextureatlassprite);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBufferSize() {
/*  191 */     return this.vertexCount * this.vertexFormat.getIntegerSize();
/*      */   }
/*      */ 
/*      */   
/*      */   private static float getDistanceSq(FloatBuffer p_181665_0_, float p_181665_1_, float p_181665_2_, float p_181665_3_, int p_181665_4_, int p_181665_5_) {
/*  196 */     float f = p_181665_0_.get(p_181665_5_);
/*  197 */     float f1 = p_181665_0_.get(p_181665_5_ + 1);
/*  198 */     float f2 = p_181665_0_.get(p_181665_5_ + 2);
/*  199 */     float f3 = p_181665_0_.get(p_181665_5_ + p_181665_4_);
/*  200 */     float f4 = p_181665_0_.get(p_181665_5_ + p_181665_4_ + 1);
/*  201 */     float f5 = p_181665_0_.get(p_181665_5_ + p_181665_4_ + 2);
/*  202 */     float f6 = p_181665_0_.get(p_181665_5_ + (p_181665_4_ << 1));
/*  203 */     float f7 = p_181665_0_.get(p_181665_5_ + (p_181665_4_ << 1) + 1);
/*  204 */     float f8 = p_181665_0_.get(p_181665_5_ + (p_181665_4_ << 1) + 2);
/*  205 */     float f9 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 3);
/*  206 */     float f10 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 3 + 1);
/*  207 */     float f11 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 3 + 2);
/*  208 */     float f12 = (f + f3 + f6 + f9) * 0.25F - p_181665_1_;
/*  209 */     float f13 = (f1 + f4 + f7 + f10) * 0.25F - p_181665_2_;
/*  210 */     float f14 = (f2 + f5 + f8 + f11) * 0.25F - p_181665_3_;
/*  211 */     return f12 * f12 + f13 * f13 + f14 * f14;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setVertexState(State state) {
/*  216 */     this.rawIntBuffer.clear();
/*  217 */     growBuffer((state.getRawBuffer()).length);
/*  218 */     this.rawIntBuffer.put(state.getRawBuffer());
/*  219 */     this.vertexCount = state.getVertexCount();
/*  220 */     this.vertexFormat = new VertexFormat(state.getVertexFormat());
/*      */     
/*  222 */     if (state.stateQuadSprites != null) {
/*      */       
/*  224 */       if (this.quadSprites == null)
/*      */       {
/*  226 */         this.quadSprites = this.quadSpritesPrev;
/*      */       }
/*      */       
/*  229 */       if (this.quadSprites == null || this.quadSprites.length < getBufferQuadSize())
/*      */       {
/*  231 */         this.quadSprites = new TextureAtlasSprite[getBufferQuadSize()];
/*      */       }
/*      */       
/*  234 */       TextureAtlasSprite[] atextureatlassprite = state.stateQuadSprites;
/*  235 */       System.arraycopy(atextureatlassprite, 0, this.quadSprites, 0, atextureatlassprite.length);
/*      */     }
/*      */     else {
/*      */       
/*  239 */       if (this.quadSprites != null)
/*      */       {
/*  241 */         this.quadSpritesPrev = this.quadSprites;
/*      */       }
/*      */       
/*  244 */       this.quadSprites = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void reset() {
/*  250 */     this.vertexCount = 0;
/*  251 */     this.vertexFormatElement = null;
/*  252 */     this.vertexFormatIndex = 0;
/*  253 */     this.quadSprite = null;
/*      */     
/*  255 */     if (SmartAnimations.isActive()) {
/*      */       
/*  257 */       if (this.animatedSprites == null)
/*      */       {
/*  259 */         this.animatedSprites = this.animatedSpritesCached;
/*      */       }
/*      */       
/*  262 */       this.animatedSprites.clear();
/*      */     }
/*  264 */     else if (this.animatedSprites != null) {
/*      */       
/*  266 */       this.animatedSprites = null;
/*      */     } 
/*      */     
/*  269 */     this.modeTriangles = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void begin(int glMode, VertexFormat format) {
/*  274 */     if (this.isDrawing)
/*      */     {
/*  276 */       throw new IllegalStateException("Already building!");
/*      */     }
/*      */ 
/*      */     
/*  280 */     this.isDrawing = true;
/*  281 */     reset();
/*  282 */     this.drawMode = glMode;
/*  283 */     this.vertexFormat = format;
/*  284 */     this.vertexFormatElement = format.getElement(this.vertexFormatIndex);
/*  285 */     this.noColor = false;
/*  286 */     this.byteBuffer.limit(this.byteBuffer.capacity());
/*      */     
/*  288 */     if (Config.isShaders())
/*      */     {
/*  290 */       SVertexBuilder.endSetVertexFormat(this);
/*      */     }
/*      */     
/*  293 */     if (Config.isMultiTexture()) {
/*      */       
/*  295 */       if (this.blockLayer != null)
/*      */       {
/*  297 */         if (this.quadSprites == null)
/*      */         {
/*  299 */           this.quadSprites = this.quadSpritesPrev;
/*      */         }
/*      */         
/*  302 */         if (this.quadSprites == null || this.quadSprites.length < getBufferQuadSize())
/*      */         {
/*  304 */           this.quadSprites = new TextureAtlasSprite[getBufferQuadSize()];
/*      */         }
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  310 */       if (this.quadSprites != null)
/*      */       {
/*  312 */         this.quadSpritesPrev = this.quadSprites;
/*      */       }
/*      */       
/*  315 */       this.quadSprites = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldRenderer tex(double u, double v) {
/*  322 */     if (this.quadSprite != null && this.quadSprites != null) {
/*      */       
/*  324 */       u = this.quadSprite.toSingleU((float)u);
/*  325 */       v = this.quadSprite.toSingleV((float)v);
/*  326 */       this.quadSprites[this.vertexCount / 4] = this.quadSprite;
/*      */     } 
/*      */     
/*  329 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.getOffset(this.vertexFormatIndex);
/*      */     
/*  331 */     switch (this.vertexFormatElement.getType()) {
/*      */       
/*      */       case FLOAT:
/*  334 */         this.byteBuffer.putFloat(i, (float)u);
/*  335 */         this.byteBuffer.putFloat(i + 4, (float)v);
/*      */         break;
/*      */       
/*      */       case UINT:
/*      */       case INT:
/*  340 */         this.byteBuffer.putInt(i, (int)u);
/*  341 */         this.byteBuffer.putInt(i + 4, (int)v);
/*      */         break;
/*      */       
/*      */       case USHORT:
/*      */       case SHORT:
/*  346 */         this.byteBuffer.putShort(i, (short)(int)v);
/*  347 */         this.byteBuffer.putShort(i + 2, (short)(int)u);
/*      */         break;
/*      */       
/*      */       case UBYTE:
/*      */       case BYTE:
/*  352 */         this.byteBuffer.put(i, (byte)(int)v);
/*  353 */         this.byteBuffer.put(i + 1, (byte)(int)u);
/*      */         break;
/*      */     } 
/*  356 */     nextVertexFormatIndex();
/*  357 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldRenderer lightmap(int p_181671_1_, int p_181671_2_) {
/*  362 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.getOffset(this.vertexFormatIndex);
/*      */     
/*  364 */     switch (this.vertexFormatElement.getType()) {
/*      */       
/*      */       case FLOAT:
/*  367 */         this.byteBuffer.putFloat(i, p_181671_1_);
/*  368 */         this.byteBuffer.putFloat(i + 4, p_181671_2_);
/*      */         break;
/*      */       
/*      */       case UINT:
/*      */       case INT:
/*  373 */         this.byteBuffer.putInt(i, p_181671_1_);
/*  374 */         this.byteBuffer.putInt(i + 4, p_181671_2_);
/*      */         break;
/*      */       
/*      */       case USHORT:
/*      */       case SHORT:
/*  379 */         this.byteBuffer.putShort(i, (short)p_181671_2_);
/*  380 */         this.byteBuffer.putShort(i + 2, (short)p_181671_1_);
/*      */         break;
/*      */       
/*      */       case UBYTE:
/*      */       case BYTE:
/*  385 */         this.byteBuffer.put(i, (byte)p_181671_2_);
/*  386 */         this.byteBuffer.put(i + 1, (byte)p_181671_1_);
/*      */         break;
/*      */     } 
/*  389 */     nextVertexFormatIndex();
/*  390 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putBrightness4(int p_178962_1_, int p_178962_2_, int p_178962_3_, int p_178962_4_) {
/*  395 */     int i = (this.vertexCount - 4) * this.vertexFormat.getIntegerSize() + this.vertexFormat.getUvOffsetById(1) / 4;
/*  396 */     int j = this.vertexFormat.getNextOffset() >> 2;
/*  397 */     this.rawIntBuffer.put(i, p_178962_1_);
/*  398 */     this.rawIntBuffer.put(i + j, p_178962_2_);
/*  399 */     this.rawIntBuffer.put(i + (j << 1), p_178962_3_);
/*  400 */     this.rawIntBuffer.put(i + j * 3, p_178962_4_);
/*      */   }
/*      */ 
/*      */   
/*      */   public void putPosition(double x, double y, double z) {
/*  405 */     int i = this.vertexFormat.getIntegerSize();
/*  406 */     int j = (this.vertexCount - 4) * i;
/*      */     
/*  408 */     for (int k = 0; k < 4; k++) {
/*      */       
/*  410 */       int l = j + k * i;
/*  411 */       int i1 = l + 1;
/*  412 */       int j1 = i1 + 1;
/*  413 */       this.rawIntBuffer.put(l, Float.floatToRawIntBits((float)(x + this.xOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(l))));
/*  414 */       this.rawIntBuffer.put(i1, Float.floatToRawIntBits((float)(y + this.yOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(i1))));
/*  415 */       this.rawIntBuffer.put(j1, Float.floatToRawIntBits((float)(z + this.zOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(j1))));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getColorIndex(int p_78909_1_) {
/*  424 */     return ((this.vertexCount - p_78909_1_) * this.vertexFormat.getNextOffset() + this.vertexFormat.getColorOffset()) / 4;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putColorMultiplier(float red, float green, float blue, int p_178978_4_) {
/*  429 */     int i = getColorIndex(p_178978_4_);
/*  430 */     int j = -1;
/*      */     
/*  432 */     if (!this.noColor) {
/*      */       
/*  434 */       j = this.rawIntBuffer.get(i);
/*      */       
/*  436 */       if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/*      */         
/*  438 */         int k = (int)((j & 0xFF) * red);
/*  439 */         int l = (int)((j >> 8 & 0xFF) * green);
/*  440 */         int i1 = (int)((j >> 16 & 0xFF) * blue);
/*  441 */         j &= 0xFF000000;
/*  442 */         j = j | i1 << 16 | l << 8 | k;
/*      */       }
/*      */       else {
/*      */         
/*  446 */         int j1 = (int)((j >> 24 & 0xFF) * red);
/*  447 */         int k1 = (int)((j >> 16 & 0xFF) * green);
/*  448 */         int l1 = (int)((j >> 8 & 0xFF) * blue);
/*  449 */         j &= 0xFF;
/*  450 */         j = j | j1 << 24 | k1 << 16 | l1 << 8;
/*      */       } 
/*      */     } 
/*      */     
/*  454 */     this.rawIntBuffer.put(i, j);
/*      */   }
/*      */ 
/*      */   
/*      */   private void putColor(int argb, int p_178988_2_) {
/*  459 */     int i = getColorIndex(p_178988_2_);
/*  460 */     int j = argb >> 16 & 0xFF;
/*  461 */     int k = argb >> 8 & 0xFF;
/*  462 */     int l = argb & 0xFF;
/*  463 */     int i1 = argb >> 24 & 0xFF;
/*  464 */     putColorRGBA(i, j, k, l, i1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void putColorRGB_F(float red, float green, float blue, int p_178994_4_) {
/*  469 */     int i = getColorIndex(p_178994_4_);
/*  470 */     int j = MathHelper.clamp_int((int)(red * 255.0F), 0, 255);
/*  471 */     int k = MathHelper.clamp_int((int)(green * 255.0F), 0, 255);
/*  472 */     int l = MathHelper.clamp_int((int)(blue * 255.0F), 0, 255);
/*  473 */     putColorRGBA(i, j, k, l, 255);
/*      */   }
/*      */ 
/*      */   
/*      */   public void putColorRGBA(int index, int red, int p_178972_3_, int p_178972_4_, int p_178972_5_) {
/*  478 */     if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/*      */       
/*  480 */       this.rawIntBuffer.put(index, p_178972_5_ << 24 | p_178972_4_ << 16 | p_178972_3_ << 8 | red);
/*      */     }
/*      */     else {
/*      */       
/*  484 */       this.rawIntBuffer.put(index, red << 24 | p_178972_3_ << 16 | p_178972_4_ << 8 | p_178972_5_);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void noColor() {
/*  493 */     this.noColor = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldRenderer color(float red, float green, float blue, float alpha) {
/*  498 */     return color((int)(red * 255.0F), (int)(green * 255.0F), (int)(blue * 255.0F), (int)(alpha * 255.0F));
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldRenderer color(int red, int green, int blue, int alpha) {
/*  503 */     if (this.noColor)
/*      */     {
/*  505 */       return this;
/*      */     }
/*      */ 
/*      */     
/*  509 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.getOffset(this.vertexFormatIndex);
/*      */     
/*  511 */     switch (this.vertexFormatElement.getType()) {
/*      */       
/*      */       case FLOAT:
/*  514 */         this.byteBuffer.putFloat(i, red / 255.0F);
/*  515 */         this.byteBuffer.putFloat(i + 4, green / 255.0F);
/*  516 */         this.byteBuffer.putFloat(i + 8, blue / 255.0F);
/*  517 */         this.byteBuffer.putFloat(i + 12, alpha / 255.0F);
/*      */         break;
/*      */       
/*      */       case UINT:
/*      */       case INT:
/*  522 */         this.byteBuffer.putFloat(i, red);
/*  523 */         this.byteBuffer.putFloat(i + 4, green);
/*  524 */         this.byteBuffer.putFloat(i + 8, blue);
/*  525 */         this.byteBuffer.putFloat(i + 12, alpha);
/*      */         break;
/*      */       
/*      */       case USHORT:
/*      */       case SHORT:
/*  530 */         this.byteBuffer.putShort(i, (short)red);
/*  531 */         this.byteBuffer.putShort(i + 2, (short)green);
/*  532 */         this.byteBuffer.putShort(i + 4, (short)blue);
/*  533 */         this.byteBuffer.putShort(i + 6, (short)alpha);
/*      */         break;
/*      */       
/*      */       case UBYTE:
/*      */       case BYTE:
/*  538 */         if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/*      */           
/*  540 */           this.byteBuffer.put(i, (byte)red);
/*  541 */           this.byteBuffer.put(i + 1, (byte)green);
/*  542 */           this.byteBuffer.put(i + 2, (byte)blue);
/*  543 */           this.byteBuffer.put(i + 3, (byte)alpha);
/*      */           
/*      */           break;
/*      */         } 
/*  547 */         this.byteBuffer.put(i, (byte)alpha);
/*  548 */         this.byteBuffer.put(i + 1, (byte)blue);
/*  549 */         this.byteBuffer.put(i + 2, (byte)green);
/*  550 */         this.byteBuffer.put(i + 3, (byte)red);
/*      */         break;
/*      */     } 
/*      */     
/*  554 */     nextVertexFormatIndex();
/*  555 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void addVertexData(int[] vertexData) {
/*  561 */     if (Config.isShaders())
/*      */     {
/*  563 */       SVertexBuilder.beginAddVertexData(this, vertexData);
/*      */     }
/*      */     
/*  566 */     growBuffer(vertexData.length);
/*  567 */     this.rawIntBuffer.position(getBufferSize());
/*  568 */     this.rawIntBuffer.put(vertexData);
/*  569 */     this.vertexCount += vertexData.length / this.vertexFormat.getIntegerSize();
/*      */     
/*  571 */     if (Config.isShaders())
/*      */     {
/*  573 */       SVertexBuilder.endAddVertexData(this);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void endVertex() {
/*  579 */     this.vertexCount++;
/*  580 */     growBuffer(this.vertexFormat.getIntegerSize());
/*  581 */     this.vertexFormatIndex = 0;
/*  582 */     this.vertexFormatElement = this.vertexFormat.getElement(this.vertexFormatIndex);
/*      */     
/*  584 */     if (Config.isShaders())
/*      */     {
/*  586 */       SVertexBuilder.endAddVertex(this);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldRenderer pos(double x, double y, double z) {
/*  592 */     if (Config.isShaders())
/*      */     {
/*  594 */       SVertexBuilder.beginAddVertex(this);
/*      */     }
/*      */     
/*  597 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.getOffset(this.vertexFormatIndex);
/*      */     
/*  599 */     switch (this.vertexFormatElement.getType()) {
/*      */       
/*      */       case FLOAT:
/*  602 */         this.byteBuffer.putFloat(i, (float)(x + this.xOffset));
/*  603 */         this.byteBuffer.putFloat(i + 4, (float)(y + this.yOffset));
/*  604 */         this.byteBuffer.putFloat(i + 8, (float)(z + this.zOffset));
/*      */         break;
/*      */       
/*      */       case UINT:
/*      */       case INT:
/*  609 */         this.byteBuffer.putInt(i, Float.floatToRawIntBits((float)(x + this.xOffset)));
/*  610 */         this.byteBuffer.putInt(i + 4, Float.floatToRawIntBits((float)(y + this.yOffset)));
/*  611 */         this.byteBuffer.putInt(i + 8, Float.floatToRawIntBits((float)(z + this.zOffset)));
/*      */         break;
/*      */       
/*      */       case USHORT:
/*      */       case SHORT:
/*  616 */         this.byteBuffer.putShort(i, (short)(int)(x + this.xOffset));
/*  617 */         this.byteBuffer.putShort(i + 2, (short)(int)(y + this.yOffset));
/*  618 */         this.byteBuffer.putShort(i + 4, (short)(int)(z + this.zOffset));
/*      */         break;
/*      */       
/*      */       case UBYTE:
/*      */       case BYTE:
/*  623 */         this.byteBuffer.put(i, (byte)(int)(x + this.xOffset));
/*  624 */         this.byteBuffer.put(i + 1, (byte)(int)(y + this.yOffset));
/*  625 */         this.byteBuffer.put(i + 2, (byte)(int)(z + this.zOffset));
/*      */         break;
/*      */     } 
/*  628 */     nextVertexFormatIndex();
/*  629 */     return this;
/*      */   }
/*      */   
/*      */   public void putNormal(float x, float y, float z) {
/*  633 */     int i = (byte)(int)(x * 127.0F) & 0xFF;
/*  634 */     int j = (byte)(int)(y * 127.0F) & 0xFF;
/*  635 */     int k = (byte)(int)(z * 127.0F) & 0xFF;
/*  636 */     int l = i | j << 8 | k << 16;
/*  637 */     int i1 = this.vertexFormat.getNextOffset() >> 2;
/*  638 */     int j1 = (this.vertexCount - 4) * i1 + this.vertexFormat.getNormalOffset() / 4;
/*  639 */     this.rawIntBuffer.put(j1, l);
/*  640 */     this.rawIntBuffer.put(j1 + i1, l);
/*  641 */     this.rawIntBuffer.put(j1 + (i1 << 1), l);
/*  642 */     this.rawIntBuffer.put(j1 + i1 * 3, l);
/*      */   }
/*      */ 
/*      */   
/*      */   private void nextVertexFormatIndex() {
/*  647 */     this.vertexFormatIndex++;
/*  648 */     this.vertexFormatIndex %= this.vertexFormat.getElementCount();
/*  649 */     this.vertexFormatElement = this.vertexFormat.getElement(this.vertexFormatIndex);
/*      */     
/*  651 */     if (this.vertexFormatElement.getUsage() == VertexFormatElement.EnumUsage.PADDING)
/*      */     {
/*  653 */       nextVertexFormatIndex();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldRenderer normal(float p_181663_1_, float p_181663_2_, float p_181663_3_) {
/*  659 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.getOffset(this.vertexFormatIndex);
/*      */     
/*  661 */     switch (this.vertexFormatElement.getType()) {
/*      */       
/*      */       case FLOAT:
/*  664 */         this.byteBuffer.putFloat(i, p_181663_1_);
/*  665 */         this.byteBuffer.putFloat(i + 4, p_181663_2_);
/*  666 */         this.byteBuffer.putFloat(i + 8, p_181663_3_);
/*      */         break;
/*      */       
/*      */       case UINT:
/*      */       case INT:
/*  671 */         this.byteBuffer.putInt(i, (int)p_181663_1_);
/*  672 */         this.byteBuffer.putInt(i + 4, (int)p_181663_2_);
/*  673 */         this.byteBuffer.putInt(i + 8, (int)p_181663_3_);
/*      */         break;
/*      */       
/*      */       case USHORT:
/*      */       case SHORT:
/*  678 */         this.byteBuffer.putShort(i, (short)((int)(p_181663_1_ * 32767.0F) & 0xFFFF));
/*  679 */         this.byteBuffer.putShort(i + 2, (short)((int)(p_181663_2_ * 32767.0F) & 0xFFFF));
/*  680 */         this.byteBuffer.putShort(i + 4, (short)((int)(p_181663_3_ * 32767.0F) & 0xFFFF));
/*      */         break;
/*      */       
/*      */       case UBYTE:
/*      */       case BYTE:
/*  685 */         this.byteBuffer.put(i, (byte)((int)(p_181663_1_ * 127.0F) & 0xFF));
/*  686 */         this.byteBuffer.put(i + 1, (byte)((int)(p_181663_2_ * 127.0F) & 0xFF));
/*  687 */         this.byteBuffer.put(i + 2, (byte)((int)(p_181663_3_ * 127.0F) & 0xFF));
/*      */         break;
/*      */     } 
/*  690 */     nextVertexFormatIndex();
/*  691 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTranslation(double x, double y, double z) {
/*  696 */     this.xOffset = x;
/*  697 */     this.yOffset = y;
/*  698 */     this.zOffset = z;
/*      */   }
/*      */ 
/*      */   
/*      */   public void finishDrawing() {
/*  703 */     if (!this.isDrawing)
/*      */     {
/*  705 */       throw new IllegalStateException("Not building!");
/*      */     }
/*      */ 
/*      */     
/*  709 */     this.isDrawing = false;
/*  710 */     this.byteBuffer.position(0);
/*  711 */     this.byteBuffer.limit(getBufferSize() << 2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer getByteBuffer() {
/*  717 */     return this.modeTriangles ? this.byteBufferTriangles : this.byteBuffer;
/*      */   }
/*      */ 
/*      */   
/*      */   public VertexFormat getVertexFormat() {
/*  722 */     return this.vertexFormat;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getVertexCount() {
/*  727 */     return this.modeTriangles ? (this.vertexCount / 4 * 6) : this.vertexCount;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDrawMode() {
/*  732 */     return this.modeTriangles ? 4 : this.drawMode;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putColor4(int argb) {
/*  737 */     for (int i = 0; i < 4; i++)
/*      */     {
/*  739 */       putColor(argb, i + 1);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void putColorRGB_F4(float red, float green, float blue) {
/*  745 */     for (int i = 0; i < 4; i++)
/*      */     {
/*  747 */       putColorRGB_F(red, green, blue, i + 1);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void putSprite(TextureAtlasSprite p_putSprite_1_) {
/*  753 */     if (this.animatedSprites != null && p_putSprite_1_ != null && p_putSprite_1_.getAnimationIndex() >= 0)
/*      */     {
/*  755 */       this.animatedSprites.set(p_putSprite_1_.getAnimationIndex());
/*      */     }
/*      */     
/*  758 */     if (this.quadSprites != null) {
/*      */       
/*  760 */       int i = this.vertexCount / 4;
/*  761 */       this.quadSprites[i - 1] = p_putSprite_1_;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSprite(TextureAtlasSprite p_setSprite_1_) {
/*  767 */     if (this.animatedSprites != null && p_setSprite_1_ != null && p_setSprite_1_.getAnimationIndex() >= 0)
/*      */     {
/*  769 */       this.animatedSprites.set(p_setSprite_1_.getAnimationIndex());
/*      */     }
/*      */     
/*  772 */     if (this.quadSprites != null)
/*      */     {
/*  774 */       this.quadSprite = p_setSprite_1_;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isMultiTexture() {
/*  780 */     return (this.quadSprites != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawMultiTexture() {
/*  785 */     if (this.quadSprites != null) {
/*      */       
/*  787 */       int i = Config.getMinecraft().getTextureMapBlocks().getCountRegisteredSprites();
/*      */       
/*  789 */       if (this.drawnIcons.length <= i)
/*      */       {
/*  791 */         this.drawnIcons = new boolean[i + 1];
/*      */       }
/*      */       
/*  794 */       Arrays.fill(this.drawnIcons, false);
/*  795 */       int j = 0;
/*  796 */       int k = -1;
/*  797 */       int l = this.vertexCount / 4;
/*      */       
/*  799 */       for (int i1 = 0; i1 < l; i1++) {
/*      */         
/*  801 */         TextureAtlasSprite textureatlassprite = this.quadSprites[i1];
/*      */         
/*  803 */         if (textureatlassprite != null) {
/*      */           
/*  805 */           int j1 = textureatlassprite.getIndexInMap();
/*      */           
/*  807 */           if (!this.drawnIcons[j1])
/*      */           {
/*  809 */             if (textureatlassprite == TextureUtils.iconGrassSideOverlay) {
/*      */               
/*  811 */               if (k < 0)
/*      */               {
/*  813 */                 k = i1;
/*      */               }
/*      */             }
/*      */             else {
/*      */               
/*  818 */               i1 = drawForIcon(textureatlassprite, i1) - 1;
/*  819 */               j++;
/*      */               
/*  821 */               if (this.blockLayer != EnumWorldBlockLayer.TRANSLUCENT)
/*      */               {
/*  823 */                 this.drawnIcons[j1] = true;
/*      */               }
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  830 */       if (k >= 0) {
/*      */         
/*  832 */         drawForIcon(TextureUtils.iconGrassSideOverlay, k);
/*  833 */         j++;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int drawForIcon(TextureAtlasSprite p_drawForIcon_1_, int p_drawForIcon_2_) {
/*  841 */     GL11.glBindTexture(3553, p_drawForIcon_1_.glSpriteTextureId);
/*  842 */     int i = -1;
/*  843 */     int j = -1;
/*  844 */     int k = this.vertexCount / 4;
/*      */     
/*  846 */     for (int l = p_drawForIcon_2_; l < k; l++) {
/*      */       
/*  848 */       TextureAtlasSprite textureatlassprite = this.quadSprites[l];
/*      */       
/*  850 */       if (textureatlassprite == p_drawForIcon_1_) {
/*      */         
/*  852 */         if (j < 0)
/*      */         {
/*  854 */           j = l;
/*      */         }
/*      */       }
/*  857 */       else if (j >= 0) {
/*      */         
/*  859 */         draw(j, l);
/*      */         
/*  861 */         if (this.blockLayer == EnumWorldBlockLayer.TRANSLUCENT)
/*      */         {
/*  863 */           return l;
/*      */         }
/*      */         
/*  866 */         j = -1;
/*      */         
/*  868 */         if (i < 0)
/*      */         {
/*  870 */           i = l;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  875 */     if (j >= 0)
/*      */     {
/*  877 */       draw(j, k);
/*      */     }
/*      */     
/*  880 */     if (i < 0)
/*      */     {
/*  882 */       i = k;
/*      */     }
/*      */     
/*  885 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   private void draw(int p_draw_1_, int p_draw_2_) {
/*  890 */     int i = p_draw_2_ - p_draw_1_;
/*      */     
/*  892 */     if (i > 0) {
/*      */       
/*  894 */       int j = p_draw_1_ << 2;
/*  895 */       int k = i << 2;
/*  896 */       GL11.glDrawArrays(this.drawMode, j, k);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBlockLayer(EnumWorldBlockLayer p_setBlockLayer_1_) {
/*  902 */     this.blockLayer = p_setBlockLayer_1_;
/*      */     
/*  904 */     if (p_setBlockLayer_1_ == null) {
/*      */       
/*  906 */       if (this.quadSprites != null)
/*      */       {
/*  908 */         this.quadSpritesPrev = this.quadSprites;
/*      */       }
/*      */       
/*  911 */       this.quadSprites = null;
/*  912 */       this.quadSprite = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private int getBufferQuadSize() {
/*  918 */     return (this.rawIntBuffer.capacity() << 2) / (this.vertexFormat.getIntegerSize() << 2);
/*      */   }
/*      */ 
/*      */   
/*      */   public RenderEnv getRenderEnv(IBlockState p_getRenderEnv_1_, BlockPos p_getRenderEnv_2_) {
/*  923 */     if (this.renderEnv == null) {
/*      */       
/*  925 */       this.renderEnv = new RenderEnv(p_getRenderEnv_1_, p_getRenderEnv_2_);
/*  926 */       return this.renderEnv;
/*      */     } 
/*      */ 
/*      */     
/*  930 */     this.renderEnv.reset(p_getRenderEnv_1_, p_getRenderEnv_2_);
/*  931 */     return this.renderEnv;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDrawing() {
/*  937 */     return this.isDrawing;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getXOffset() {
/*  942 */     return this.xOffset;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getYOffset() {
/*  947 */     return this.yOffset;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getZOffset() {
/*  952 */     return this.zOffset;
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumWorldBlockLayer getBlockLayer() {
/*  957 */     return this.blockLayer;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putColorMultiplierRgba(float p_putColorMultiplierRgba_1_, float p_putColorMultiplierRgba_2_, float p_putColorMultiplierRgba_3_, float p_putColorMultiplierRgba_4_, int p_putColorMultiplierRgba_5_) {
/*  962 */     int i = getColorIndex(p_putColorMultiplierRgba_5_);
/*  963 */     int j = -1;
/*      */     
/*  965 */     if (!this.noColor) {
/*      */       
/*  967 */       j = this.rawIntBuffer.get(i);
/*      */       
/*  969 */       if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/*      */         
/*  971 */         int k = (int)((j & 0xFF) * p_putColorMultiplierRgba_1_);
/*  972 */         int l = (int)((j >> 8 & 0xFF) * p_putColorMultiplierRgba_2_);
/*  973 */         int i1 = (int)((j >> 16 & 0xFF) * p_putColorMultiplierRgba_3_);
/*  974 */         int j1 = (int)((j >> 24 & 0xFF) * p_putColorMultiplierRgba_4_);
/*  975 */         j = j1 << 24 | i1 << 16 | l << 8 | k;
/*      */       }
/*      */       else {
/*      */         
/*  979 */         int k1 = (int)((j >> 24 & 0xFF) * p_putColorMultiplierRgba_1_);
/*  980 */         int l1 = (int)((j >> 16 & 0xFF) * p_putColorMultiplierRgba_2_);
/*  981 */         int i2 = (int)((j >> 8 & 0xFF) * p_putColorMultiplierRgba_3_);
/*  982 */         int j2 = (int)((j & 0xFF) * p_putColorMultiplierRgba_4_);
/*  983 */         j = k1 << 24 | l1 << 16 | i2 << 8 | j2;
/*      */       } 
/*      */     } 
/*      */     
/*  987 */     this.rawIntBuffer.put(i, j);
/*      */   }
/*      */ 
/*      */   
/*      */   public void quadsToTriangles() {
/*  992 */     if (this.drawMode == 7) {
/*      */       
/*  994 */       if (this.byteBufferTriangles == null)
/*      */       {
/*  996 */         this.byteBufferTriangles = GLAllocation.createDirectByteBuffer(this.byteBuffer.capacity() << 1);
/*      */       }
/*      */       
/*  999 */       if (this.byteBufferTriangles.capacity() < this.byteBuffer.capacity() << 1)
/*      */       {
/* 1001 */         this.byteBufferTriangles = GLAllocation.createDirectByteBuffer(this.byteBuffer.capacity() << 1);
/*      */       }
/*      */       
/* 1004 */       int i = this.vertexFormat.getNextOffset();
/* 1005 */       int j = this.byteBuffer.limit();
/* 1006 */       this.byteBuffer.rewind();
/* 1007 */       this.byteBufferTriangles.clear();
/*      */       
/* 1009 */       for (int k = 0; k < this.vertexCount; k += 4) {
/*      */         
/* 1011 */         this.byteBuffer.limit((k + 3) * i);
/* 1012 */         this.byteBuffer.position(k * i);
/* 1013 */         this.byteBufferTriangles.put(this.byteBuffer);
/* 1014 */         this.byteBuffer.limit((k + 1) * i);
/* 1015 */         this.byteBuffer.position(k * i);
/* 1016 */         this.byteBufferTriangles.put(this.byteBuffer);
/* 1017 */         this.byteBuffer.limit((k + 2 + 2) * i);
/* 1018 */         this.byteBuffer.position((k + 2) * i);
/* 1019 */         this.byteBufferTriangles.put(this.byteBuffer);
/*      */       } 
/*      */       
/* 1022 */       this.byteBuffer.limit(j);
/* 1023 */       this.byteBuffer.rewind();
/* 1024 */       this.byteBufferTriangles.flip();
/* 1025 */       this.modeTriangles = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isColorDisabled() {
/* 1031 */     return this.noColor;
/*      */   }
/*      */   
/*      */   public void startDrawingQuads() {
/* 1035 */     startDrawing(7);
/*      */   }
/*      */   public void startDrawing(int mode) {
/* 1038 */     if (this.isDrawing) {
/* 1039 */       throw new IllegalStateException("Already building!");
/*      */     }
/* 1041 */     this.isDrawing = true;
/* 1042 */     reset();
/* 1043 */     this.drawMode = mode;
/* 1044 */     this.noColor = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldRenderer func_181666_a(float p_181666_1_, float p_181666_2_, float p_181666_3_, float p_181666_4_) {
/* 1049 */     return color((int)(p_181666_1_ * 255.0F), (int)(p_181666_2_ * 255.0F), (int)(p_181666_3_ * 255.0F), (int)(p_181666_4_ * 255.0F));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldRenderer color(int colorHex) {
/* 1055 */     return color(colorHex >> 16 & 0xFF, colorHex >> 8 & 0xFF, colorHex & 0xFF, colorHex >> 24 & 0xFF);
/*      */   }
/*      */ 
/*      */   
/*      */   public static class State
/*      */   {
/*      */     private final int[] stateRawBuffer;
/*      */     private final VertexFormat stateVertexFormat;
/*      */     TextureAtlasSprite[] stateQuadSprites;
/*      */     
/*      */     public State(int[] p_i1_2_, VertexFormat p_i1_3_, TextureAtlasSprite[] p_i1_4_) {
/* 1066 */       this.stateRawBuffer = p_i1_2_;
/* 1067 */       this.stateVertexFormat = p_i1_3_;
/* 1068 */       this.stateQuadSprites = p_i1_4_;
/*      */     }
/*      */ 
/*      */     
/*      */     public State(int[] buffer, VertexFormat format) {
/* 1073 */       this.stateRawBuffer = buffer;
/* 1074 */       this.stateVertexFormat = format;
/*      */     }
/*      */ 
/*      */     
/*      */     public int[] getRawBuffer() {
/* 1079 */       return this.stateRawBuffer;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getVertexCount() {
/* 1084 */       return this.stateRawBuffer.length / this.stateVertexFormat.getIntegerSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public VertexFormat getVertexFormat() {
/* 1089 */       return this.stateVertexFormat;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\WorldRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */