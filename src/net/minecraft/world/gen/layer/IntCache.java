/*    */ package net.minecraft.world.gen.layer;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class IntCache
/*    */ {
/*  9 */   private static int intCacheSize = 256;
/* 10 */   private static final List<int[]> freeSmallArrays = Lists.newArrayList();
/* 11 */   private static final List<int[]> inUseSmallArrays = Lists.newArrayList();
/* 12 */   private static final List<int[]> freeLargeArrays = Lists.newArrayList();
/* 13 */   private static final List<int[]> inUseLargeArrays = Lists.newArrayList();
/*    */ 
/*    */   
/*    */   public static synchronized int[] getIntCache(int p_76445_0_) {
/* 17 */     if (p_76445_0_ <= 256) {
/*    */       
/* 19 */       if (freeSmallArrays.isEmpty()) {
/*    */         
/* 21 */         int[] aint4 = new int[256];
/* 22 */         inUseSmallArrays.add(aint4);
/* 23 */         return aint4;
/*    */       } 
/*    */ 
/*    */       
/* 27 */       int[] aint3 = freeSmallArrays.remove(freeSmallArrays.size() - 1);
/* 28 */       inUseSmallArrays.add(aint3);
/* 29 */       return aint3;
/*    */     } 
/*    */     
/* 32 */     if (p_76445_0_ > intCacheSize) {
/*    */       
/* 34 */       intCacheSize = p_76445_0_;
/* 35 */       freeLargeArrays.clear();
/* 36 */       inUseLargeArrays.clear();
/* 37 */       int[] aint2 = new int[intCacheSize];
/* 38 */       inUseLargeArrays.add(aint2);
/* 39 */       return aint2;
/*    */     } 
/* 41 */     if (freeLargeArrays.isEmpty()) {
/*    */       
/* 43 */       int[] aint1 = new int[intCacheSize];
/* 44 */       inUseLargeArrays.add(aint1);
/* 45 */       return aint1;
/*    */     } 
/*    */ 
/*    */     
/* 49 */     int[] aint = freeLargeArrays.remove(freeLargeArrays.size() - 1);
/* 50 */     inUseLargeArrays.add(aint);
/* 51 */     return aint;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized void resetIntCache() {
/* 60 */     if (!freeLargeArrays.isEmpty())
/*    */     {
/* 62 */       freeLargeArrays.remove(freeLargeArrays.size() - 1);
/*    */     }
/*    */     
/* 65 */     if (!freeSmallArrays.isEmpty())
/*    */     {
/* 67 */       freeSmallArrays.remove(freeSmallArrays.size() - 1);
/*    */     }
/*    */     
/* 70 */     freeLargeArrays.addAll(inUseLargeArrays);
/* 71 */     freeSmallArrays.addAll(inUseSmallArrays);
/* 72 */     inUseLargeArrays.clear();
/* 73 */     inUseSmallArrays.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized String getCacheSizes() {
/* 82 */     return "cache: " + freeLargeArrays.size() + ", tcache: " + freeSmallArrays.size() + ", allocated: " + inUseLargeArrays.size() + ", tallocated: " + inUseSmallArrays.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\layer\IntCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */