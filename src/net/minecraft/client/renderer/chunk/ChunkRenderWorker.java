/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.util.concurrent.FutureCallback;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.RegionRenderCacheBuilder;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class ChunkRenderWorker
/*     */   implements Runnable
/*     */ {
/*  21 */   static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private final ChunkRenderDispatcher chunkRenderDispatcher;
/*     */   private final RegionRenderCacheBuilder regionRenderCacheBuilder;
/*     */   
/*     */   public ChunkRenderWorker(ChunkRenderDispatcher p_i46201_1_) {
/*  27 */     this(p_i46201_1_, (RegionRenderCacheBuilder)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkRenderWorker(ChunkRenderDispatcher chunkRenderDispatcherIn, RegionRenderCacheBuilder regionRenderCacheBuilderIn) {
/*  32 */     this.chunkRenderDispatcher = chunkRenderDispatcherIn;
/*  33 */     this.regionRenderCacheBuilder = regionRenderCacheBuilderIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/*     */       while (true) {
/*  42 */         processTask(this.chunkRenderDispatcher.getNextChunkUpdate());
/*     */       }
/*  44 */     } catch (InterruptedException var3) {
/*     */       
/*  46 */       LOGGER.debug("Stopping due to interrupt");
/*     */       
/*     */       return;
/*  49 */     } catch (Throwable throwable) {
/*     */       
/*  51 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Batching chunks");
/*  52 */       Minecraft.getMinecraft().crashed(Minecraft.getMinecraft().addGraphicsAndWorldToCrashReport(crashreport));
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processTask(final ChunkCompileTaskGenerator generator) throws InterruptedException {
/*  60 */     generator.getLock().lock();
/*     */ 
/*     */     
/*     */     try {
/*  64 */       if (generator.getStatus() != ChunkCompileTaskGenerator.Status.PENDING) {
/*     */         
/*  66 */         if (!generator.isFinished())
/*     */         {
/*  68 */           LOGGER.warn("Chunk render task was " + generator.getStatus() + " when I expected it to be pending; ignoring task");
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  74 */       generator.setStatus(ChunkCompileTaskGenerator.Status.COMPILING);
/*     */     }
/*     */     finally {
/*     */       
/*  78 */       generator.getLock().unlock();
/*     */     } 
/*     */     
/*  81 */     Entity lvt_2_1_ = Minecraft.getMinecraft().getRenderViewEntity();
/*     */     
/*  83 */     if (lvt_2_1_ == null) {
/*     */       
/*  85 */       generator.finish();
/*     */     }
/*     */     else {
/*     */       
/*  89 */       generator.setRegionRenderCacheBuilder(getRegionRenderCacheBuilder());
/*  90 */       float f = (float)lvt_2_1_.posX;
/*  91 */       float f1 = (float)lvt_2_1_.posY + lvt_2_1_.getEyeHeight();
/*  92 */       float f2 = (float)lvt_2_1_.posZ;
/*  93 */       ChunkCompileTaskGenerator.Type chunkcompiletaskgenerator$type = generator.getType();
/*     */       
/*  95 */       if (chunkcompiletaskgenerator$type == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK) {
/*     */         
/*  97 */         generator.getRenderChunk().rebuildChunk(f, f1, f2, generator);
/*     */       }
/*  99 */       else if (chunkcompiletaskgenerator$type == ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY) {
/*     */         
/* 101 */         generator.getRenderChunk().resortTransparency(f, f1, f2, generator);
/*     */       } 
/*     */       
/* 104 */       generator.getLock().lock();
/*     */ 
/*     */       
/*     */       try {
/* 108 */         if (generator.getStatus() != ChunkCompileTaskGenerator.Status.COMPILING) {
/*     */           
/* 110 */           if (!generator.isFinished())
/*     */           {
/* 112 */             LOGGER.warn("Chunk render task was " + generator.getStatus() + " when I expected it to be compiling; aborting task");
/*     */           }
/*     */           
/* 115 */           freeRenderBuilder(generator);
/*     */           
/*     */           return;
/*     */         } 
/* 119 */         generator.setStatus(ChunkCompileTaskGenerator.Status.UPLOADING);
/*     */       }
/*     */       finally {
/*     */         
/* 123 */         generator.getLock().unlock();
/*     */       } 
/*     */       
/* 126 */       final CompiledChunk lvt_7_1_ = generator.getCompiledChunk();
/* 127 */       ArrayList<ListenableFuture<Object>> lvt_8_1_ = Lists.newArrayList();
/*     */       
/* 129 */       if (chunkcompiletaskgenerator$type == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK) {
/*     */         
/* 131 */         for (EnumWorldBlockLayer enumworldblocklayer : EnumWorldBlockLayer.values())
/*     */         {
/* 133 */           if (lvt_7_1_.isLayerStarted(enumworldblocklayer))
/*     */           {
/* 135 */             lvt_8_1_.add(this.chunkRenderDispatcher.uploadChunk(enumworldblocklayer, generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(enumworldblocklayer), generator.getRenderChunk(), lvt_7_1_));
/*     */           }
/*     */         }
/*     */       
/* 139 */       } else if (chunkcompiletaskgenerator$type == ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY) {
/*     */         
/* 141 */         lvt_8_1_.add(this.chunkRenderDispatcher.uploadChunk(EnumWorldBlockLayer.TRANSLUCENT, generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(EnumWorldBlockLayer.TRANSLUCENT), generator.getRenderChunk(), lvt_7_1_));
/*     */       } 
/*     */       
/* 144 */       final ListenableFuture<List<Object>> listenablefuture = Futures.allAsList(lvt_8_1_);
/* 145 */       generator.addFinishRunnable(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 149 */               listenablefuture.cancel(false);
/*     */             }
/*     */           });
/* 152 */       Futures.addCallback(listenablefuture, new FutureCallback<List<Object>>()
/*     */           {
/*     */             public void onSuccess(List<Object> p_onSuccess_1_)
/*     */             {
/* 156 */               ChunkRenderWorker.this.freeRenderBuilder(generator);
/* 157 */               generator.getLock().lock();
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
/*     */ 
/*     */               
/*     */               try {
/*     */               
/*     */               } finally {
/* 175 */                 generator.getLock().unlock();
/*     */               } 
/*     */ 
/*     */ 
/*     */               
/* 180 */               generator.getRenderChunk().setCompiledChunk(lvt_7_1_);
/*     */             }
/*     */             
/*     */             public void onFailure(Throwable p_onFailure_1_) {
/* 184 */               ChunkRenderWorker.this.freeRenderBuilder(generator);
/*     */               
/* 186 */               if (!(p_onFailure_1_ instanceof java.util.concurrent.CancellationException) && !(p_onFailure_1_ instanceof InterruptedException))
/*     */               {
/* 188 */                 Minecraft.getMinecraft().crashed(CrashReport.makeCrashReport(p_onFailure_1_, "Rendering chunk"));
/*     */               }
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private RegionRenderCacheBuilder getRegionRenderCacheBuilder() throws InterruptedException {
/* 197 */     return (this.regionRenderCacheBuilder != null) ? this.regionRenderCacheBuilder : this.chunkRenderDispatcher.allocateRenderBuilder();
/*     */   }
/*     */ 
/*     */   
/*     */   void freeRenderBuilder(ChunkCompileTaskGenerator taskGenerator) {
/* 202 */     if (this.regionRenderCacheBuilder == null)
/*     */     {
/* 204 */       this.chunkRenderDispatcher.freeRenderBuilder(taskGenerator.getRegionRenderCacheBuilder());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\chunk\ChunkRenderWorker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */