/*     */ package net.minecraft.util;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Timer
/*     */ {
/*     */   float ticksPerSecond;
/*     */   private double lastHRTime;
/*     */   public int elapsedTicks;
/*     */   public float renderPartialTicks;
/*  30 */   public float timerSpeed = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float elapsedPartialTicks;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastSyncSysClock;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastSyncHRClock;
/*     */ 
/*     */ 
/*     */   
/*     */   private long counter;
/*     */ 
/*     */ 
/*     */   
/*  53 */   private double timeSyncAdjustment = 1.0D;
/*     */ 
/*     */   
/*     */   public Timer(float tps) {
/*  57 */     this.ticksPerSecond = tps;
/*  58 */     this.lastSyncSysClock = Minecraft.getSystemTime();
/*  59 */     this.lastSyncHRClock = System.nanoTime() / 1000000L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTimer() {
/*  67 */     long i = Minecraft.getSystemTime();
/*  68 */     long j = i - this.lastSyncSysClock;
/*  69 */     long k = System.nanoTime() / 1000000L;
/*  70 */     double d0 = k / 1000.0D;
/*     */     
/*  72 */     if (j <= 1000L && j >= 0L) {
/*     */       
/*  74 */       this.counter += j;
/*     */       
/*  76 */       if (this.counter > 1000L) {
/*     */         
/*  78 */         long l = k - this.lastSyncHRClock;
/*  79 */         double d1 = this.counter / l;
/*  80 */         this.timeSyncAdjustment += (d1 - this.timeSyncAdjustment) * 0.20000000298023224D;
/*  81 */         this.lastSyncHRClock = k;
/*  82 */         this.counter = 0L;
/*     */       } 
/*     */       
/*  85 */       if (this.counter < 0L)
/*     */       {
/*  87 */         this.lastSyncHRClock = k;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  92 */       this.lastHRTime = d0;
/*     */     } 
/*     */     
/*  95 */     this.lastSyncSysClock = i;
/*  96 */     double d2 = (d0 - this.lastHRTime) * this.timeSyncAdjustment;
/*  97 */     this.lastHRTime = d0;
/*  98 */     d2 = MathHelper.clamp_double(d2, 0.0D, 1.0D);
/*  99 */     this.elapsedPartialTicks = (float)(this.elapsedPartialTicks + d2 * this.timerSpeed * this.ticksPerSecond);
/* 100 */     this.elapsedTicks = (int)this.elapsedPartialTicks;
/* 101 */     this.elapsedPartialTicks -= this.elapsedTicks;
/*     */     
/* 103 */     if (this.elapsedTicks > 10)
/*     */     {
/* 105 */       this.elapsedTicks = 10;
/*     */     }
/*     */     
/* 108 */     this.renderPartialTicks = this.elapsedPartialTicks;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\Timer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */