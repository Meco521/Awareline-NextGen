/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.BlockStateBase;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SVertexBuilder
/*     */ {
/*     */   int vertexSize;
/*     */   int offsetNormal;
/*     */   int offsetUV;
/*     */   int offsetUVCenter;
/*     */   boolean hasNormal;
/*     */   boolean hasTangent;
/*     */   boolean hasUV;
/*     */   boolean hasUVCenter;
/*  28 */   long[] entityData = new long[10];
/*  29 */   int entityDataIndex = 0;
/*     */ 
/*     */   
/*     */   public SVertexBuilder() {
/*  33 */     this.entityData[this.entityDataIndex] = 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void initVertexBuilder(WorldRenderer wrr) {
/*  38 */     wrr.sVertexBuilder = new SVertexBuilder();
/*     */   }
/*     */ 
/*     */   
/*     */   public void pushEntity(long data) {
/*  43 */     this.entityDataIndex++;
/*  44 */     this.entityData[this.entityDataIndex] = data;
/*     */   }
/*     */ 
/*     */   
/*     */   public void popEntity() {
/*  49 */     this.entityData[this.entityDataIndex] = 0L;
/*  50 */     this.entityDataIndex--;
/*     */   }
/*     */   
/*     */   public static void pushEntity(IBlockState blockState, BlockPos blockPos, IBlockAccess blockAccess, WorldRenderer wrr) {
/*     */     int i, j;
/*  55 */     Block block = blockState.getBlock();
/*     */ 
/*     */ 
/*     */     
/*  59 */     if (blockState instanceof BlockStateBase) {
/*     */       
/*  61 */       BlockStateBase blockstatebase = (BlockStateBase)blockState;
/*  62 */       i = blockstatebase.getBlockId();
/*  63 */       j = blockstatebase.getMetadata();
/*     */     }
/*     */     else {
/*     */       
/*  67 */       i = Block.getIdFromBlock(block);
/*  68 */       j = block.getMetaFromState(blockState);
/*     */     } 
/*     */     
/*  71 */     int j1 = BlockAliases.getBlockAliasId(i, j);
/*     */     
/*  73 */     if (j1 >= 0)
/*     */     {
/*  75 */       i = j1;
/*     */     }
/*     */     
/*  78 */     int k = block.getRenderType();
/*  79 */     int l = ((k & 0xFFFF) << 16) + (i & 0xFFFF);
/*  80 */     int i1 = j & 0xFFFF;
/*  81 */     wrr.sVertexBuilder.pushEntity((i1 << 32L) + l);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void popEntity(WorldRenderer wrr) {
/*  86 */     wrr.sVertexBuilder.popEntity();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean popEntity(boolean value, WorldRenderer wrr) {
/*  91 */     wrr.sVertexBuilder.popEntity();
/*  92 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endSetVertexFormat(WorldRenderer wrr) {
/*  97 */     SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/*  98 */     VertexFormat vertexformat = wrr.getVertexFormat();
/*  99 */     svertexbuilder.vertexSize = vertexformat.getNextOffset() / 4;
/* 100 */     svertexbuilder.hasNormal = vertexformat.hasNormal();
/* 101 */     svertexbuilder.hasTangent = svertexbuilder.hasNormal;
/* 102 */     svertexbuilder.hasUV = vertexformat.hasUvOffset(0);
/* 103 */     svertexbuilder.offsetNormal = svertexbuilder.hasNormal ? (vertexformat.getNormalOffset() / 4) : 0;
/* 104 */     svertexbuilder.offsetUV = svertexbuilder.hasUV ? (vertexformat.getUvOffsetById(0) / 4) : 0;
/* 105 */     svertexbuilder.offsetUVCenter = 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginAddVertex(WorldRenderer wrr) {
/* 110 */     if (wrr.vertexCount == 0)
/*     */     {
/* 112 */       endSetVertexFormat(wrr);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endAddVertex(WorldRenderer wrr) {
/* 118 */     SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/*     */     
/* 120 */     if (svertexbuilder.vertexSize == 14) {
/*     */       
/* 122 */       if (wrr.drawMode == 7 && wrr.vertexCount % 4 == 0)
/*     */       {
/* 124 */         svertexbuilder.calcNormal(wrr, wrr.getBufferSize() - 4 * svertexbuilder.vertexSize);
/*     */       }
/*     */       
/* 127 */       long i = svertexbuilder.entityData[svertexbuilder.entityDataIndex];
/* 128 */       int j = wrr.getBufferSize() - 14 + 12;
/* 129 */       wrr.rawIntBuffer.put(j, (int)i);
/* 130 */       wrr.rawIntBuffer.put(j + 1, (int)(i >> 32L));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginAddVertexData(WorldRenderer wrr, int[] data) {
/* 136 */     if (wrr.vertexCount == 0)
/*     */     {
/* 138 */       endSetVertexFormat(wrr);
/*     */     }
/*     */     
/* 141 */     SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/*     */     
/* 143 */     if (svertexbuilder.vertexSize == 14) {
/*     */       
/* 145 */       long i = svertexbuilder.entityData[svertexbuilder.entityDataIndex];
/*     */       
/* 147 */       for (int j = 12; j + 1 < data.length; j += 14) {
/*     */         
/* 149 */         data[j] = (int)i;
/* 150 */         data[j + 1] = (int)(i >> 32L);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginAddVertexData(WorldRenderer wrr, ByteBuffer byteBuffer) {
/* 157 */     if (wrr.vertexCount == 0)
/*     */     {
/* 159 */       endSetVertexFormat(wrr);
/*     */     }
/*     */     
/* 162 */     SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/*     */     
/* 164 */     if (svertexbuilder.vertexSize == 14) {
/*     */       
/* 166 */       long i = svertexbuilder.entityData[svertexbuilder.entityDataIndex];
/* 167 */       int j = byteBuffer.limit() / 4;
/*     */       
/* 169 */       for (int k = 12; k + 1 < j; k += 14) {
/*     */         
/* 171 */         int l = (int)i;
/* 172 */         int i1 = (int)(i >> 32L);
/* 173 */         byteBuffer.putInt(k << 2, l);
/* 174 */         byteBuffer.putInt(k + 1 << 2, i1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endAddVertexData(WorldRenderer wrr) {
/* 181 */     SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/*     */     
/* 183 */     if (svertexbuilder.vertexSize == 14 && wrr.drawMode == 7 && wrr.vertexCount % 4 == 0)
/*     */     {
/* 185 */       svertexbuilder.calcNormal(wrr, wrr.getBufferSize() - 4 * svertexbuilder.vertexSize);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void calcNormal(WorldRenderer wrr, int baseIndex) {
/* 191 */     FloatBuffer floatbuffer = wrr.rawFloatBuffer;
/* 192 */     IntBuffer intbuffer = wrr.rawIntBuffer;
/* 193 */     int i = wrr.getBufferSize();
/* 194 */     float f = floatbuffer.get(baseIndex + 0);
/* 195 */     float f1 = floatbuffer.get(baseIndex + 0 + 1);
/* 196 */     float f2 = floatbuffer.get(baseIndex + 0 + 2);
/* 197 */     float f3 = floatbuffer.get(baseIndex + 0 + this.offsetUV);
/* 198 */     float f4 = floatbuffer.get(baseIndex + 0 + this.offsetUV + 1);
/* 199 */     float f5 = floatbuffer.get(baseIndex + this.vertexSize);
/* 200 */     float f6 = floatbuffer.get(baseIndex + this.vertexSize + 1);
/* 201 */     float f7 = floatbuffer.get(baseIndex + this.vertexSize + 2);
/* 202 */     float f8 = floatbuffer.get(baseIndex + this.vertexSize + this.offsetUV);
/* 203 */     float f9 = floatbuffer.get(baseIndex + this.vertexSize + this.offsetUV + 1);
/* 204 */     float f10 = floatbuffer.get(baseIndex + 2 * this.vertexSize);
/* 205 */     float f11 = floatbuffer.get(baseIndex + 2 * this.vertexSize + 1);
/* 206 */     float f12 = floatbuffer.get(baseIndex + 2 * this.vertexSize + 2);
/* 207 */     float f13 = floatbuffer.get(baseIndex + 2 * this.vertexSize + this.offsetUV);
/* 208 */     float f14 = floatbuffer.get(baseIndex + 2 * this.vertexSize + this.offsetUV + 1);
/* 209 */     float f15 = floatbuffer.get(baseIndex + 3 * this.vertexSize);
/* 210 */     float f16 = floatbuffer.get(baseIndex + 3 * this.vertexSize + 1);
/* 211 */     float f17 = floatbuffer.get(baseIndex + 3 * this.vertexSize + 2);
/* 212 */     float f18 = floatbuffer.get(baseIndex + 3 * this.vertexSize + this.offsetUV);
/* 213 */     float f19 = floatbuffer.get(baseIndex + 3 * this.vertexSize + this.offsetUV + 1);
/* 214 */     float f20 = f10 - f;
/* 215 */     float f21 = f11 - f1;
/* 216 */     float f22 = f12 - f2;
/* 217 */     float f23 = f15 - f5;
/* 218 */     float f24 = f16 - f6;
/* 219 */     float f25 = f17 - f7;
/* 220 */     float f30 = f21 * f25 - f24 * f22;
/* 221 */     float f31 = f22 * f23 - f25 * f20;
/* 222 */     float f32 = f20 * f24 - f23 * f21;
/* 223 */     float f33 = f30 * f30 + f31 * f31 + f32 * f32;
/* 224 */     float f34 = (f33 != 0.0D) ? (float)(1.0D / Math.sqrt(f33)) : 1.0F;
/* 225 */     f30 *= f34;
/* 226 */     f31 *= f34;
/* 227 */     f32 *= f34;
/* 228 */     f20 = f5 - f;
/* 229 */     f21 = f6 - f1;
/* 230 */     f22 = f7 - f2;
/* 231 */     float f26 = f8 - f3;
/* 232 */     float f27 = f9 - f4;
/* 233 */     f23 = f10 - f;
/* 234 */     f24 = f11 - f1;
/* 235 */     f25 = f12 - f2;
/* 236 */     float f28 = f13 - f3;
/* 237 */     float f29 = f14 - f4;
/* 238 */     float f35 = f26 * f29 - f28 * f27;
/* 239 */     float f36 = (f35 != 0.0F) ? (1.0F / f35) : 1.0F;
/* 240 */     float f37 = (f29 * f20 - f27 * f23) * f36;
/* 241 */     float f38 = (f29 * f21 - f27 * f24) * f36;
/* 242 */     float f39 = (f29 * f22 - f27 * f25) * f36;
/* 243 */     float f40 = (f26 * f23 - f28 * f20) * f36;
/* 244 */     float f41 = (f26 * f24 - f28 * f21) * f36;
/* 245 */     float f42 = (f26 * f25 - f28 * f22) * f36;
/* 246 */     f33 = f37 * f37 + f38 * f38 + f39 * f39;
/* 247 */     f34 = (f33 != 0.0D) ? (float)(1.0D / Math.sqrt(f33)) : 1.0F;
/* 248 */     f37 *= f34;
/* 249 */     f38 *= f34;
/* 250 */     f39 *= f34;
/* 251 */     f33 = f40 * f40 + f41 * f41 + f42 * f42;
/* 252 */     f34 = (f33 != 0.0D) ? (float)(1.0D / Math.sqrt(f33)) : 1.0F;
/* 253 */     f40 *= f34;
/* 254 */     f41 *= f34;
/* 255 */     f42 *= f34;
/* 256 */     float f43 = f32 * f38 - f31 * f39;
/* 257 */     float f44 = f30 * f39 - f32 * f37;
/* 258 */     float f45 = f31 * f37 - f30 * f38;
/* 259 */     float f46 = (f40 * f43 + f41 * f44 + f42 * f45 < 0.0F) ? -1.0F : 1.0F;
/* 260 */     int j = (int)(f30 * 127.0F) & 0xFF;
/* 261 */     int k = (int)(f31 * 127.0F) & 0xFF;
/* 262 */     int l = (int)(f32 * 127.0F) & 0xFF;
/* 263 */     int i1 = (l << 16) + (k << 8) + j;
/* 264 */     intbuffer.put(baseIndex + 0 + this.offsetNormal, i1);
/* 265 */     intbuffer.put(baseIndex + this.vertexSize + this.offsetNormal, i1);
/* 266 */     intbuffer.put(baseIndex + 2 * this.vertexSize + this.offsetNormal, i1);
/* 267 */     intbuffer.put(baseIndex + 3 * this.vertexSize + this.offsetNormal, i1);
/* 268 */     int j1 = ((int)(f37 * 32767.0F) & 0xFFFF) + (((int)(f38 * 32767.0F) & 0xFFFF) << 16);
/* 269 */     int k1 = ((int)(f39 * 32767.0F) & 0xFFFF) + (((int)(f46 * 32767.0F) & 0xFFFF) << 16);
/* 270 */     intbuffer.put(baseIndex + 0 + 10, j1);
/* 271 */     intbuffer.put(baseIndex + 0 + 10 + 1, k1);
/* 272 */     intbuffer.put(baseIndex + this.vertexSize + 10, j1);
/* 273 */     intbuffer.put(baseIndex + this.vertexSize + 10 + 1, k1);
/* 274 */     intbuffer.put(baseIndex + 2 * this.vertexSize + 10, j1);
/* 275 */     intbuffer.put(baseIndex + 2 * this.vertexSize + 10 + 1, k1);
/* 276 */     intbuffer.put(baseIndex + 3 * this.vertexSize + 10, j1);
/* 277 */     intbuffer.put(baseIndex + 3 * this.vertexSize + 10 + 1, k1);
/* 278 */     float f47 = (f3 + f8 + f13 + f18) / 4.0F;
/* 279 */     float f48 = (f4 + f9 + f14 + f19) / 4.0F;
/* 280 */     floatbuffer.put(baseIndex + 0 + 8, f47);
/* 281 */     floatbuffer.put(baseIndex + 0 + 8 + 1, f48);
/* 282 */     floatbuffer.put(baseIndex + this.vertexSize + 8, f47);
/* 283 */     floatbuffer.put(baseIndex + this.vertexSize + 8 + 1, f48);
/* 284 */     floatbuffer.put(baseIndex + 2 * this.vertexSize + 8, f47);
/* 285 */     floatbuffer.put(baseIndex + 2 * this.vertexSize + 8 + 1, f48);
/* 286 */     floatbuffer.put(baseIndex + 3 * this.vertexSize + 8, f47);
/* 287 */     floatbuffer.put(baseIndex + 3 * this.vertexSize + 8 + 1, f48);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void calcNormalChunkLayer(WorldRenderer wrr) {
/* 292 */     if (wrr.getVertexFormat().hasNormal() && wrr.drawMode == 7 && wrr.vertexCount % 4 == 0) {
/*     */       
/* 294 */       SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/* 295 */       endSetVertexFormat(wrr);
/* 296 */       int i = wrr.vertexCount * svertexbuilder.vertexSize;
/*     */       
/* 298 */       for (int j = 0; j < i; j += svertexbuilder.vertexSize << 2)
/*     */       {
/* 300 */         svertexbuilder.calcNormal(wrr, j);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawArrays(int drawMode, int first, int count, WorldRenderer wrr) {
/* 307 */     if (count != 0) {
/*     */       
/* 309 */       VertexFormat vertexformat = wrr.getVertexFormat();
/* 310 */       int i = vertexformat.getNextOffset();
/*     */       
/* 312 */       if (i == 56) {
/*     */         
/* 314 */         ByteBuffer bytebuffer = wrr.getByteBuffer();
/* 315 */         bytebuffer.position(32);
/* 316 */         GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, 5126, false, i, bytebuffer);
/* 317 */         bytebuffer.position(40);
/* 318 */         GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, i, bytebuffer);
/* 319 */         bytebuffer.position(48);
/* 320 */         GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, i, bytebuffer);
/* 321 */         bytebuffer.position(0);
/* 322 */         GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
/* 323 */         GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
/* 324 */         GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
/* 325 */         GlStateManager.glDrawArrays(drawMode, first, count);
/* 326 */         GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
/* 327 */         GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
/* 328 */         GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
/*     */       }
/*     */       else {
/*     */         
/* 332 */         GlStateManager.glDrawArrays(drawMode, first, count);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\SVertexBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */