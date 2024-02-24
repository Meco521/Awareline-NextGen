/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
/*     */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.render.VboRegion;
/*     */ 
/*     */ 
/*     */ public class ViewFrustum
/*     */ {
/*     */   protected final RenderGlobal renderGlobal;
/*     */   protected final World world;
/*     */   protected int countChunksY;
/*     */   protected int countChunksX;
/*     */   protected int countChunksZ;
/*     */   public RenderChunk[] renderChunks;
/*  25 */   private final Map<ChunkCoordIntPair, VboRegion[]> mapVboRegions = (Map)new HashMap<>();
/*     */ 
/*     */   
/*     */   public ViewFrustum(World worldIn, int renderDistanceChunks, RenderGlobal p_i46246_3_, IRenderChunkFactory renderChunkFactory) {
/*  29 */     this.renderGlobal = p_i46246_3_;
/*  30 */     this.world = worldIn;
/*  31 */     setCountChunksXYZ(renderDistanceChunks);
/*  32 */     createRenderChunks(renderChunkFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createRenderChunks(IRenderChunkFactory renderChunkFactory) {
/*  37 */     int i = this.countChunksX * this.countChunksY * this.countChunksZ;
/*  38 */     this.renderChunks = new RenderChunk[i];
/*  39 */     int j = 0;
/*     */     
/*  41 */     for (int k = 0; k < this.countChunksX; k++) {
/*     */       
/*  43 */       for (int l = 0; l < this.countChunksY; l++) {
/*     */         
/*  45 */         for (int i1 = 0; i1 < this.countChunksZ; i1++) {
/*     */           
/*  47 */           int j1 = (i1 * this.countChunksY + l) * this.countChunksX + k;
/*  48 */           BlockPos blockpos = new BlockPos(k << 4, l << 4, i1 << 4);
/*  49 */           this.renderChunks[j1] = renderChunkFactory.makeRenderChunk(this.world, this.renderGlobal, blockpos, j++);
/*     */           
/*  51 */           if (Config.isVbo() && Config.isRenderRegions())
/*     */           {
/*  53 */             updateVboRegion(this.renderChunks[j1]);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  59 */     for (int k1 = 0; k1 < this.renderChunks.length; k1++) {
/*     */       
/*  61 */       RenderChunk renderchunk1 = this.renderChunks[k1];
/*     */       
/*  63 */       for (int l1 = 0; l1 < EnumFacing.VALUES.length; l1++) {
/*     */         
/*  65 */         EnumFacing enumfacing = EnumFacing.VALUES[l1];
/*  66 */         BlockPos blockpos1 = renderchunk1.getBlockPosOffset16(enumfacing);
/*  67 */         RenderChunk renderchunk = getRenderChunk(blockpos1);
/*  68 */         renderchunk1.setRenderChunkNeighbour(enumfacing, renderchunk);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteGlResources() {
/*  75 */     for (RenderChunk renderchunk : this.renderChunks)
/*     */     {
/*  77 */       renderchunk.deleteGlResources();
/*     */     }
/*     */     
/*  80 */     deleteVboRegions();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setCountChunksXYZ(int renderDistanceChunks) {
/*  85 */     int i = (renderDistanceChunks << 1) + 1;
/*  86 */     this.countChunksX = i;
/*  87 */     this.countChunksY = 16;
/*  88 */     this.countChunksZ = i;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateChunkPositions(double viewEntityX, double viewEntityZ) {
/*  93 */     int i = MathHelper.floor_double(viewEntityX) - 8;
/*  94 */     int j = MathHelper.floor_double(viewEntityZ) - 8;
/*  95 */     int k = this.countChunksX << 4;
/*     */     
/*  97 */     for (int l = 0; l < this.countChunksX; l++) {
/*     */       
/*  99 */       int i1 = func_178157_a(i, k, l);
/*     */       
/* 101 */       for (int j1 = 0; j1 < this.countChunksZ; j1++) {
/*     */         
/* 103 */         int k1 = func_178157_a(j, k, j1);
/*     */         
/* 105 */         for (int l1 = 0; l1 < this.countChunksY; l1++) {
/*     */           
/* 107 */           int i2 = l1 << 4;
/* 108 */           RenderChunk renderchunk = this.renderChunks[(j1 * this.countChunksY + l1) * this.countChunksX + l];
/* 109 */           BlockPos blockpos = renderchunk.getPosition();
/*     */           
/* 111 */           if (blockpos.getX() != i1 || blockpos.getY() != i2 || blockpos.getZ() != k1) {
/*     */             
/* 113 */             BlockPos blockpos1 = new BlockPos(i1, i2, k1);
/*     */             
/* 115 */             if (!blockpos1.equals(renderchunk.getPosition()))
/*     */             {
/* 117 */               renderchunk.setPosition(blockpos1);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int func_178157_a(int p_178157_1_, int p_178157_2_, int p_178157_3_) {
/* 127 */     int i = p_178157_3_ << 4;
/* 128 */     int j = i - p_178157_1_ + p_178157_2_ / 2;
/*     */     
/* 130 */     if (j < 0)
/*     */     {
/* 132 */       j -= p_178157_2_ - 1;
/*     */     }
/*     */     
/* 135 */     return i - j / p_178157_2_ * p_178157_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void markBlocksForUpdate(int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
/* 140 */     int i = MathHelper.bucketInt(fromX, 16);
/* 141 */     int j = MathHelper.bucketInt(fromY, 16);
/* 142 */     int k = MathHelper.bucketInt(fromZ, 16);
/* 143 */     int l = MathHelper.bucketInt(toX, 16);
/* 144 */     int i1 = MathHelper.bucketInt(toY, 16);
/* 145 */     int j1 = MathHelper.bucketInt(toZ, 16);
/*     */     
/* 147 */     for (int k1 = i; k1 <= l; k1++) {
/*     */       
/* 149 */       int l1 = k1 % this.countChunksX;
/*     */       
/* 151 */       if (l1 < 0)
/*     */       {
/* 153 */         l1 += this.countChunksX;
/*     */       }
/*     */       
/* 156 */       for (int i2 = j; i2 <= i1; i2++) {
/*     */         
/* 158 */         int j2 = i2 % this.countChunksY;
/*     */         
/* 160 */         if (j2 < 0)
/*     */         {
/* 162 */           j2 += this.countChunksY;
/*     */         }
/*     */         
/* 165 */         for (int k2 = k; k2 <= j1; k2++) {
/*     */           
/* 167 */           int l2 = k2 % this.countChunksZ;
/*     */           
/* 169 */           if (l2 < 0)
/*     */           {
/* 171 */             l2 += this.countChunksZ;
/*     */           }
/*     */           
/* 174 */           int i3 = (l2 * this.countChunksY + j2) * this.countChunksX + l1;
/* 175 */           RenderChunk renderchunk = this.renderChunks[i3];
/* 176 */           renderchunk.setNeedsUpdate(true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderChunk getRenderChunk(BlockPos pos) {
/* 184 */     int i = pos.getX() >> 4;
/* 185 */     int j = pos.getY() >> 4;
/* 186 */     int k = pos.getZ() >> 4;
/*     */     
/* 188 */     if (j >= 0 && j < this.countChunksY) {
/*     */       
/* 190 */       i %= this.countChunksX;
/*     */       
/* 192 */       if (i < 0)
/*     */       {
/* 194 */         i += this.countChunksX;
/*     */       }
/*     */       
/* 197 */       k %= this.countChunksZ;
/*     */       
/* 199 */       if (k < 0)
/*     */       {
/* 201 */         k += this.countChunksZ;
/*     */       }
/*     */       
/* 204 */       int l = (k * this.countChunksY + j) * this.countChunksX + i;
/* 205 */       return this.renderChunks[l];
/*     */     } 
/*     */ 
/*     */     
/* 209 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateVboRegion(RenderChunk p_updateVboRegion_1_) {
/* 215 */     BlockPos blockpos = p_updateVboRegion_1_.getPosition();
/* 216 */     int i = blockpos.getX() >> 8 << 8;
/* 217 */     int j = blockpos.getZ() >> 8 << 8;
/* 218 */     ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i, j);
/* 219 */     EnumWorldBlockLayer[] aenumworldblocklayer = RenderChunk.ENUM_WORLD_BLOCK_LAYERS;
/* 220 */     VboRegion[] avboregion = this.mapVboRegions.get(chunkcoordintpair);
/*     */     
/* 222 */     if (avboregion == null) {
/*     */       
/* 224 */       avboregion = new VboRegion[aenumworldblocklayer.length];
/*     */       
/* 226 */       for (int k = 0; k < aenumworldblocklayer.length; k++)
/*     */       {
/* 228 */         avboregion[k] = new VboRegion(aenumworldblocklayer[k]);
/*     */       }
/*     */       
/* 231 */       this.mapVboRegions.put(chunkcoordintpair, avboregion);
/*     */     } 
/*     */     
/* 234 */     for (int l = 0; l < aenumworldblocklayer.length; l++) {
/*     */       
/* 236 */       VboRegion vboregion = avboregion[l];
/*     */       
/* 238 */       if (vboregion != null)
/*     */       {
/* 240 */         p_updateVboRegion_1_.getVertexBufferByLayer(l).setVboRegion(vboregion);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteVboRegions() {
/* 247 */     for (VboRegion[] avboregion : this.mapVboRegions.values()) {
/*     */ 
/*     */       
/* 250 */       for (int i = 0; i < avboregion.length; i++) {
/*     */         
/* 252 */         VboRegion vboregion = avboregion[i];
/*     */         
/* 254 */         if (vboregion != null)
/*     */         {
/* 256 */           vboregion.deleteGlBuffers();
/*     */         }
/*     */         
/* 259 */         avboregion[i] = null;
/*     */       } 
/*     */     } 
/*     */     
/* 263 */     this.mapVboRegions.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\ViewFrustum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */