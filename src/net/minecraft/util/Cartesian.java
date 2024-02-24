/*     */ package net.minecraft.util;
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ public class Cartesian {
/*     */   public static <T> Iterable<T[]> cartesianProduct(Class<T> clazz, Iterable<? extends Iterable<? extends T>> sets) {
/*  15 */     return new Product<>(clazz, toArray((Class)Iterable.class, sets));
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T> Iterable<List<T>> cartesianProduct(Iterable<? extends Iterable<? extends T>> sets) {
/*  20 */     return arraysAsLists(cartesianProduct(Object.class, sets));
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> Iterable<List<T>> arraysAsLists(Iterable<Object[]> arrays) {
/*  25 */     return Iterables.transform(arrays, new GetList());
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> T[] toArray(Class<? super T> clazz, Iterable<? extends T> it) {
/*  30 */     List<T> list = Lists.newArrayList();
/*     */     
/*  32 */     for (T t : it)
/*     */     {
/*  34 */       list.add(t);
/*     */     }
/*     */     
/*  37 */     return (T[])list.<Object>toArray(createArray((Class)clazz, list.size()));
/*     */   }
/*     */ 
/*     */   
/*     */   static <T> T[] createArray(Class<? super T> p_179319_0_, int p_179319_1_) {
/*  42 */     return (T[])Array.newInstance(p_179319_0_, p_179319_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class GetList<T>
/*     */     implements Function<Object[], List<T>>
/*     */   {
/*     */     public List<T> apply(Object[] p_apply_1_) {
/*  53 */       return Arrays.asList((T[])p_apply_1_);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Product<T>
/*     */     implements Iterable<T[]>
/*     */   {
/*     */     private final Class<T> clazz;
/*     */     private final Iterable<? extends T>[] iterables;
/*     */     
/*     */     Product(Class<T> clazz, Iterable<? extends T>[] iterables) {
/*  64 */       this.clazz = clazz;
/*  65 */       this.iterables = iterables;
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<T[]> iterator() {
/*  70 */       return (this.iterables.length <= 0) ? Collections.<T[]>singletonList(Cartesian.createArray(this.clazz, 0)).iterator() : (Iterator<T[]>)new ProductIterator<>(this.clazz, this.iterables);
/*     */     }
/*     */     
/*     */     static class ProductIterator<T>
/*     */       extends UnmodifiableIterator<T[]>
/*     */     {
/*     */       private int index;
/*     */       private final Iterable<? extends T>[] iterables;
/*     */       private final Iterator<? extends T>[] iterators;
/*     */       private final T[] results;
/*     */       
/*     */       ProductIterator(Class<T> clazz, Iterable<? extends T>[] iterables) {
/*  82 */         this.index = -2;
/*  83 */         this.iterables = iterables;
/*  84 */         this.iterators = Cartesian.<Iterator<? extends T>>createArray((Class)Iterator.class, this.iterables.length);
/*     */         
/*  86 */         for (int i = 0; i < this.iterables.length; i++)
/*     */         {
/*  88 */           this.iterators[i] = iterables[i].iterator();
/*     */         }
/*     */         
/*  91 */         this.results = Cartesian.createArray(clazz, this.iterators.length);
/*     */       }
/*     */ 
/*     */       
/*     */       private void endOfData() {
/*  96 */         this.index = -1;
/*  97 */         Arrays.fill((Object[])this.iterators, (Object)null);
/*  98 */         Arrays.fill((Object[])this.results, (Object)null);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/* 103 */         if (this.index == -2) {
/*     */           
/* 105 */           this.index = 0;
/*     */           
/* 107 */           for (Iterator<? extends T> iterator1 : this.iterators) {
/*     */             
/* 109 */             if (!iterator1.hasNext()) {
/*     */               
/* 111 */               endOfData();
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 116 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 120 */         if (this.index >= this.iterators.length)
/*     */         {
/* 122 */           for (this.index = this.iterators.length - 1; this.index >= 0; this.index--) {
/*     */             
/* 124 */             Iterator<? extends T> iterator = this.iterators[this.index];
/*     */             
/* 126 */             if (iterator.hasNext()) {
/*     */               break;
/*     */             }
/*     */ 
/*     */             
/* 131 */             if (this.index == 0) {
/*     */               
/* 133 */               endOfData();
/*     */               
/*     */               break;
/*     */             } 
/* 137 */             iterator = this.iterables[this.index].iterator();
/* 138 */             this.iterators[this.index] = iterator;
/*     */             
/* 140 */             if (!iterator.hasNext()) {
/*     */               
/* 142 */               endOfData();
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/* 148 */         return (this.index >= 0);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public T[] next() {
/* 154 */         if (!hasNext())
/*     */         {
/* 156 */           throw new NoSuchElementException();
/*     */         }
/*     */ 
/*     */         
/* 160 */         while (this.index < this.iterators.length) {
/*     */           
/* 162 */           this.results[this.index] = this.iterators[this.index].next();
/* 163 */           this.index++;
/*     */         } 
/*     */         
/* 166 */         return (T[])this.results.clone();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\Cartesian.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */