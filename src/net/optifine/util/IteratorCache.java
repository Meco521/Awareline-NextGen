/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.Deque;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class IteratorCache
/*    */ {
/* 10 */   private static final Deque<IteratorReusable<Object>> dequeIterators = new ArrayDeque<>();
/*    */ 
/*    */   
/*    */   public static Iterator<Object> getReadOnly(List list) {
/* 14 */     synchronized (dequeIterators) {
/*    */       
/* 16 */       IteratorReusable<Object> iteratorreusable = dequeIterators.pollFirst();
/*    */       
/* 18 */       if (iteratorreusable == null)
/*    */       {
/* 20 */         iteratorreusable = new IteratorReadOnly();
/*    */       }
/*    */       
/* 23 */       iteratorreusable.setList(list);
/* 24 */       return iteratorreusable;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   static void finished(IteratorReusable<Object> iterator) {
/* 30 */     synchronized (dequeIterators) {
/*    */       
/* 32 */       if (dequeIterators.size() <= 1000) {
/*    */         
/* 34 */         iterator.setList(null);
/* 35 */         dequeIterators.addLast(iterator);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   static {
/* 42 */     for (int i = 0; i < 1000; i++) {
/*    */       
/* 44 */       IteratorReadOnly iteratorcache$iteratorreadonly = new IteratorReadOnly();
/* 45 */       dequeIterators.add(iteratorcache$iteratorreadonly);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static interface IteratorReusable<E> extends Iterator<E> {
/*    */     void setList(List<E> param1List);
/*    */   }
/*    */   
/*    */   public static class IteratorReadOnly implements IteratorReusable<Object> {
/*    */     private List<Object> list;
/*    */     
/*    */     public void setList(List<Object> list) {
/* 57 */       if (this.hasNext)
/*    */       {
/* 59 */         throw new RuntimeException("Iterator still used, oldList: " + this.list + ", newList: " + list);
/*    */       }
/*    */ 
/*    */       
/* 63 */       this.list = list;
/* 64 */       this.index = 0;
/* 65 */       this.hasNext = (list != null && this.index < list.size());
/*    */     }
/*    */     private int index;
/*    */     private boolean hasNext;
/*    */     
/*    */     public Object next() {
/* 71 */       if (!this.hasNext)
/*    */       {
/* 73 */         return null;
/*    */       }
/*    */ 
/*    */       
/* 77 */       Object object = this.list.get(this.index);
/* 78 */       this.index++;
/* 79 */       this.hasNext = (this.index < this.list.size());
/* 80 */       return object;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public boolean hasNext() {
/* 86 */       if (!this.hasNext) {
/*    */         
/* 88 */         IteratorCache.finished(this);
/* 89 */         return false;
/*    */       } 
/*    */ 
/*    */       
/* 93 */       return this.hasNext;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public void remove() {
/* 99 */       throw new UnsupportedOperationException("remove");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\IteratorCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */