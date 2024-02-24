/*     */ package net.minecraft.client.renderer.chunk;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Queues;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import com.google.common.util.concurrent.ListenableFutureTask;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RegionRenderCacheBuilder;
/*     */ import net.minecraft.client.renderer.VertexBufferUploader;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.WorldVertexBufferUploader;
/*     */ import net.minecraft.client.renderer.vertex.VertexBuffer;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ChunkRenderDispatcher {
/*  27 */   private static final Logger logger = LogManager.getLogger();
/*  28 */   private static final ThreadFactory threadFactory = (new ThreadFactoryBuilder()).setNameFormat("Chunk Batcher %d").setDaemon(true).build();
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
/*     */   public ChunkRenderDispatcher() {
/*  41 */     this(-1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  46 */   private final List<ChunkRenderWorker> listThreadedWorkers = Lists.newArrayList();
/*  47 */   final BlockingQueue<ChunkCompileTaskGenerator> queueChunkUpdates = Queues.newArrayBlockingQueue(100); private final BlockingQueue<RegionRenderCacheBuilder> queueFreeRenderBuilders;
/*  48 */   private final WorldVertexBufferUploader worldVertexUploader = new WorldVertexBufferUploader();
/*  49 */   private final VertexBufferUploader vertexUploader = new VertexBufferUploader();
/*  50 */   private final Queue<ListenableFutureTask<?>> queueChunkUploads = Queues.newArrayDeque(); private final ChunkRenderWorker renderWorker; private final int countRenderBuilders;
/*  51 */   private final List<RegionRenderCacheBuilder> listPausedBuilders = new ArrayList<>(); public ChunkRenderDispatcher(int p_i4_1_) {
/*  52 */     int i = Math.max(1, (int)(Runtime.getRuntime().maxMemory() * 0.3D) / 10485760);
/*  53 */     int j = Math.max(1, MathHelper.clamp_int(Runtime.getRuntime().availableProcessors() - 2, 1, i / 5));
/*     */     
/*  55 */     if (p_i4_1_ < 0) {
/*     */       
/*  57 */       this.countRenderBuilders = MathHelper.clamp_int(j << 3, 1, i);
/*     */     }
/*     */     else {
/*     */       
/*  61 */       this.countRenderBuilders = p_i4_1_;
/*     */     } 
/*     */     
/*  64 */     for (int k = 0; k < j; k++) {
/*     */       
/*  66 */       ChunkRenderWorker chunkrenderworker = new ChunkRenderWorker(this);
/*  67 */       Thread thread = threadFactory.newThread(chunkrenderworker);
/*  68 */       thread.start();
/*  69 */       this.listThreadedWorkers.add(chunkrenderworker);
/*     */     } 
/*     */     
/*  72 */     this.queueFreeRenderBuilders = Queues.newArrayBlockingQueue(this.countRenderBuilders);
/*     */     
/*  74 */     for (int l = 0; l < this.countRenderBuilders; l++)
/*     */     {
/*  76 */       this.queueFreeRenderBuilders.add(new RegionRenderCacheBuilder());
/*     */     }
/*     */     
/*  79 */     this.renderWorker = new ChunkRenderWorker(this, new RegionRenderCacheBuilder());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDebugInfo() {
/*  84 */     return String.format("pC: %03d, pU: %1d, aB: %1d", new Object[] { Integer.valueOf(this.queueChunkUpdates.size()), Integer.valueOf(this.queueChunkUploads.size()), Integer.valueOf(this.queueFreeRenderBuilders.size()) });
/*     */   }
/*     */   
/*     */   public boolean runChunkUploads(long p_178516_1_) {
/*     */     long i;
/*  89 */     boolean flag = false;
/*     */ 
/*     */     
/*     */     do {
/*  93 */       boolean flag1 = false;
/*  94 */       ListenableFutureTask listenablefuturetask = null;
/*     */       
/*  96 */       synchronized (this.queueChunkUploads) {
/*     */         
/*  98 */         listenablefuturetask = this.queueChunkUploads.poll();
/*     */       } 
/*     */       
/* 101 */       if (listenablefuturetask != null) {
/*     */         
/* 103 */         listenablefuturetask.run();
/* 104 */         flag1 = true;
/* 105 */         flag = true;
/*     */       } 
/*     */       
/* 108 */       if (p_178516_1_ == 0L || !flag1) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 113 */       i = p_178516_1_ - System.nanoTime();
/*     */     }
/* 115 */     while (i >= 0L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean updateChunkLater(RenderChunk chunkRenderer) {
/*     */     boolean flag;
/* 126 */     chunkRenderer.getLockCompileTask().lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 131 */       final ChunkCompileTaskGenerator chunkcompiletaskgenerator = chunkRenderer.makeCompileTaskChunk();
/* 132 */       chunkcompiletaskgenerator.addFinishRunnable(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 136 */               ChunkRenderDispatcher.this.queueChunkUpdates.remove(chunkcompiletaskgenerator);
/*     */             }
/*     */           });
/* 139 */       boolean flag1 = this.queueChunkUpdates.offer(chunkcompiletaskgenerator);
/*     */       
/* 141 */       if (!flag1)
/*     */       {
/* 143 */         chunkcompiletaskgenerator.finish();
/*     */       }
/*     */       
/* 146 */       flag = flag1;
/*     */     }
/*     */     finally {
/*     */       
/* 150 */       chunkRenderer.getLockCompileTask().unlock();
/*     */     } 
/*     */     
/* 153 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean updateChunkNow(RenderChunk chunkRenderer) {
/*     */     boolean flag;
/* 158 */     chunkRenderer.getLockCompileTask().lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 163 */       ChunkCompileTaskGenerator chunkcompiletaskgenerator = chunkRenderer.makeCompileTaskChunk();
/*     */ 
/*     */       
/*     */       try {
/* 167 */         this.renderWorker.processTask(chunkcompiletaskgenerator);
/*     */       }
/* 169 */       catch (InterruptedException interruptedException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 174 */       flag = true;
/*     */     }
/*     */     finally {
/*     */       
/* 178 */       chunkRenderer.getLockCompileTask().unlock();
/*     */     } 
/*     */     
/* 181 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopChunkUpdates() {
/* 186 */     clearChunkUpdates();
/*     */     
/* 188 */     while (runChunkUploads(0L));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 193 */     List<RegionRenderCacheBuilder> list = Lists.newArrayList();
/*     */     
/* 195 */     while (list.size() != this.countRenderBuilders) {
/*     */ 
/*     */       
/*     */       try {
/* 199 */         list.add(allocateRenderBuilder());
/*     */       }
/* 201 */       catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 207 */     this.queueFreeRenderBuilders.addAll(list);
/*     */   }
/*     */ 
/*     */   
/*     */   public void freeRenderBuilder(RegionRenderCacheBuilder p_178512_1_) {
/* 212 */     this.queueFreeRenderBuilders.add(p_178512_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public RegionRenderCacheBuilder allocateRenderBuilder() throws InterruptedException {
/* 217 */     return this.queueFreeRenderBuilders.take();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkCompileTaskGenerator getNextChunkUpdate() throws InterruptedException {
/* 222 */     return this.queueChunkUpdates.take();
/*     */   }
/*     */   
/*     */   public boolean updateTransparencyLater(RenderChunk chunkRenderer) {
/*     */     boolean flag1;
/* 227 */     chunkRenderer.getLockCompileTask().lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 232 */       final ChunkCompileTaskGenerator chunkcompiletaskgenerator = chunkRenderer.makeCompileTaskTransparency();
/*     */       
/* 234 */       if (chunkcompiletaskgenerator != null) {
/*     */         
/* 236 */         chunkcompiletaskgenerator.addFinishRunnable(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 240 */                 ChunkRenderDispatcher.this.queueChunkUpdates.remove(chunkcompiletaskgenerator);
/*     */               }
/*     */             });
/* 243 */         return this.queueChunkUpdates.offer(chunkcompiletaskgenerator);
/*     */       } 
/*     */       
/* 246 */       flag1 = true;
/*     */     }
/*     */     finally {
/*     */       
/* 250 */       chunkRenderer.getLockCompileTask().unlock();
/*     */     } 
/*     */     
/* 253 */     return flag1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ListenableFuture<Object> uploadChunk(final EnumWorldBlockLayer player, final WorldRenderer p_178503_2_, final RenderChunk chunkRenderer, final CompiledChunk compiledChunkIn) {
/* 258 */     if (Minecraft.getMinecraft().isCallingFromMinecraftThread()) {
/*     */       
/* 260 */       if (OpenGlHelper.useVbo()) {
/*     */         
/* 262 */         uploadVertexBuffer(p_178503_2_, chunkRenderer.getVertexBufferByLayer(player.ordinal()));
/*     */       }
/*     */       else {
/*     */         
/* 266 */         uploadDisplayList(p_178503_2_, ((ListedRenderChunk)chunkRenderer).getDisplayList(player, compiledChunkIn), chunkRenderer);
/*     */       } 
/*     */       
/* 269 */       p_178503_2_.setTranslation(0.0D, 0.0D, 0.0D);
/* 270 */       return Futures.immediateFuture(null);
/*     */     } 
/*     */ 
/*     */     
/* 274 */     ListenableFutureTask<Object> listenablefuturetask = ListenableFutureTask.create(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 278 */             ChunkRenderDispatcher.this.uploadChunk(player, p_178503_2_, chunkRenderer, compiledChunkIn);
/*     */           }
/*     */         }null);
/*     */     
/* 282 */     synchronized (this.queueChunkUploads) {
/*     */       
/* 284 */       this.queueChunkUploads.add(listenablefuturetask);
/* 285 */       return (ListenableFuture<Object>)listenablefuturetask;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void uploadDisplayList(WorldRenderer p_178510_1_, int p_178510_2_, RenderChunk chunkRenderer) {
/* 292 */     GL11.glNewList(p_178510_2_, 4864);
/* 293 */     GlStateManager.pushMatrix();
/* 294 */     chunkRenderer.multModelviewMatrix();
/* 295 */     this.worldVertexUploader.draw(p_178510_1_);
/* 296 */     GlStateManager.popMatrix();
/* 297 */     GL11.glEndList();
/*     */   }
/*     */ 
/*     */   
/*     */   private void uploadVertexBuffer(WorldRenderer p_178506_1_, VertexBuffer vertexBufferIn) {
/* 302 */     this.vertexUploader.setVertexBuffer(vertexBufferIn);
/* 303 */     this.vertexUploader.draw(p_178506_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearChunkUpdates() {
/* 308 */     while (!this.queueChunkUpdates.isEmpty()) {
/*     */       
/* 310 */       ChunkCompileTaskGenerator chunkcompiletaskgenerator = this.queueChunkUpdates.poll();
/*     */       
/* 312 */       if (chunkcompiletaskgenerator != null)
/*     */       {
/* 314 */         chunkcompiletaskgenerator.finish();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChunkUpdates() {
/* 321 */     return (this.queueChunkUpdates.isEmpty() && this.queueChunkUploads.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void pauseChunkUpdates() {
/* 326 */     while (this.listPausedBuilders.size() != this.countRenderBuilders) {
/*     */ 
/*     */       
/*     */       try {
/* 330 */         runChunkUploads(Long.MAX_VALUE);
/* 331 */         RegionRenderCacheBuilder regionrendercachebuilder = this.queueFreeRenderBuilders.poll(100L, TimeUnit.MILLISECONDS);
/*     */         
/* 333 */         if (regionrendercachebuilder != null)
/*     */         {
/* 335 */           this.listPausedBuilders.add(regionrendercachebuilder);
/*     */         }
/*     */       }
/* 338 */       catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resumeChunkUpdates() {
/* 347 */     this.queueFreeRenderBuilders.addAll(this.listPausedBuilders);
/* 348 */     this.listPausedBuilders.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\chunk\ChunkRenderDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */