/*     */ package net.minecraft.world.storage;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public class ThreadedFileIOBase
/*     */   implements Runnable
/*     */ {
/*  11 */   private static final ThreadedFileIOBase threadedIOInstance = new ThreadedFileIOBase();
/*  12 */   private final List<IThreadedFileIO> threadedIOQueue = Collections.synchronizedList(Lists.newArrayList());
/*     */   
/*     */   private volatile long writeQueuedCounter;
/*     */   private volatile long savedIOCounter;
/*     */   private volatile boolean isThreadWaiting;
/*     */   
/*     */   private ThreadedFileIOBase() {
/*  19 */     Thread thread = new Thread(this, "File IO Thread");
/*  20 */     thread.setPriority(1);
/*  21 */     thread.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ThreadedFileIOBase getThreadedIOInstance() {
/*  29 */     return threadedIOInstance;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     while (true) {
/*  36 */       processQueue();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processQueue() {
/*  45 */     for (int i = 0; i < this.threadedIOQueue.size(); i++) {
/*     */       
/*  47 */       IThreadedFileIO ithreadedfileio = this.threadedIOQueue.get(i);
/*  48 */       boolean flag = ithreadedfileio.writeNextIO();
/*     */       
/*  50 */       if (!flag) {
/*     */         
/*  52 */         this.threadedIOQueue.remove(i--);
/*  53 */         this.savedIOCounter++;
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/*  58 */         Thread.sleep(this.isThreadWaiting ? 0L : 10L);
/*     */       }
/*  60 */       catch (InterruptedException interruptedexception1) {
/*     */         
/*  62 */         interruptedexception1.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/*  66 */     if (this.threadedIOQueue.isEmpty()) {
/*     */       
/*     */       try {
/*     */         
/*  70 */         Thread.sleep(25L);
/*     */       }
/*  72 */       catch (InterruptedException interruptedexception) {
/*     */         
/*  74 */         interruptedexception.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void queueIO(IThreadedFileIO p_75735_1_) {
/*  84 */     if (!this.threadedIOQueue.contains(p_75735_1_)) {
/*     */       
/*  86 */       this.writeQueuedCounter++;
/*  87 */       this.threadedIOQueue.add(p_75735_1_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void waitForFinish() throws InterruptedException {
/*  93 */     this.isThreadWaiting = true;
/*     */     
/*  95 */     while (this.writeQueuedCounter != this.savedIOCounter)
/*     */     {
/*  97 */       Thread.sleep(10L);
/*     */     }
/*     */     
/* 100 */     this.isThreadWaiting = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\storage\ThreadedFileIOBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */