/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import net.minecraft.client.renderer.RegionRenderCacheBuilder;
/*     */ 
/*     */ 
/*     */ public class ChunkCompileTaskGenerator
/*     */ {
/*     */   private final RenderChunk renderChunk;
/*  12 */   private final ReentrantLock lock = new ReentrantLock();
/*  13 */   private final List<Runnable> listFinishRunnables = Lists.newArrayList();
/*     */   private final Type type;
/*     */   private RegionRenderCacheBuilder regionRenderCacheBuilder;
/*     */   private CompiledChunk compiledChunk;
/*  17 */   private Status status = Status.PENDING;
/*     */   
/*     */   private boolean finished;
/*     */   
/*     */   public ChunkCompileTaskGenerator(RenderChunk renderChunkIn, Type typeIn) {
/*  22 */     this.renderChunk = renderChunkIn;
/*  23 */     this.type = typeIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public Status getStatus() {
/*  28 */     return this.status;
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderChunk getRenderChunk() {
/*  33 */     return this.renderChunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public CompiledChunk getCompiledChunk() {
/*  38 */     return this.compiledChunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCompiledChunk(CompiledChunk compiledChunkIn) {
/*  43 */     this.compiledChunk = compiledChunkIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public RegionRenderCacheBuilder getRegionRenderCacheBuilder() {
/*  48 */     return this.regionRenderCacheBuilder;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRegionRenderCacheBuilder(RegionRenderCacheBuilder regionRenderCacheBuilderIn) {
/*  53 */     this.regionRenderCacheBuilder = regionRenderCacheBuilderIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStatus(Status statusIn) {
/*  58 */     this.lock.lock();
/*     */ 
/*     */     
/*     */     try {
/*  62 */       this.status = statusIn;
/*     */     }
/*     */     finally {
/*     */       
/*  66 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void finish() {
/*  72 */     this.lock.lock();
/*     */ 
/*     */     
/*     */     try {
/*  76 */       if (this.type == Type.REBUILD_CHUNK && this.status != Status.DONE)
/*     */       {
/*  78 */         this.renderChunk.setNeedsUpdate(true);
/*     */       }
/*     */       
/*  81 */       this.finished = true;
/*  82 */       this.status = Status.DONE;
/*     */       
/*  84 */       for (Runnable runnable : this.listFinishRunnables)
/*     */       {
/*  86 */         runnable.run();
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/*  91 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFinishRunnable(Runnable p_178539_1_) {
/*  97 */     this.lock.lock();
/*     */ 
/*     */     
/*     */     try {
/* 101 */       this.listFinishRunnables.add(p_178539_1_);
/*     */       
/* 103 */       if (this.finished)
/*     */       {
/* 105 */         p_178539_1_.run();
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 110 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ReentrantLock getLock() {
/* 116 */     return this.lock;
/*     */   }
/*     */ 
/*     */   
/*     */   public Type getType() {
/* 121 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFinished() {
/* 126 */     return this.finished;
/*     */   }
/*     */   
/*     */   public enum Status
/*     */   {
/* 131 */     PENDING,
/* 132 */     COMPILING,
/* 133 */     UPLOADING,
/* 134 */     DONE;
/*     */   }
/*     */   
/*     */   public enum Type
/*     */   {
/* 139 */     REBUILD_CHUNK,
/* 140 */     RESORT_TRANSPARENCY;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\chunk\ChunkCompileTaskGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */