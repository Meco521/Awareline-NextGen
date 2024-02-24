/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.lang.reflect.Array;
/*    */ import java.util.ArrayDeque;
/*    */ 
/*    */ public class ArrayCache
/*    */ {
/*  8 */   private Class elementClass = null;
/*  9 */   private int maxCacheSize = 0;
/* 10 */   private final ArrayDeque cache = new ArrayDeque();
/*    */ 
/*    */   
/*    */   public ArrayCache(Class elementClass, int maxCacheSize) {
/* 14 */     this.elementClass = elementClass;
/* 15 */     this.maxCacheSize = maxCacheSize;
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized Object allocate(int size) {
/* 20 */     Object object = this.cache.pollLast();
/*    */     
/* 22 */     if (object == null || Array.getLength(object) < size)
/*    */     {
/* 24 */       object = Array.newInstance(this.elementClass, size);
/*    */     }
/*    */     
/* 27 */     return object;
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized void free(Object arr) {
/* 32 */     if (arr != null) {
/*    */       
/* 34 */       Class<?> oclass = arr.getClass();
/*    */       
/* 36 */       if (oclass.getComponentType() != this.elementClass)
/*    */       {
/* 38 */         throw new IllegalArgumentException("Wrong component type");
/*    */       }
/* 40 */       if (this.cache.size() < this.maxCacheSize)
/*    */       {
/* 42 */         this.cache.add(arr);
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\ArrayCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */