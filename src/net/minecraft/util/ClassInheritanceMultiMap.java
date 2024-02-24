/*     */ package net.minecraft.util;
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ public class ClassInheritanceMultiMap<T> extends AbstractSet<T> {
/*  14 */   private static final Set<Class<?>> field_181158_a = Collections.newSetFromMap(new ConcurrentHashMap<>());
/*  15 */   final Map<Class<?>, List<T>> map = Maps.newHashMap();
/*  16 */   private final Set<Class<?>> knownKeys = Sets.newIdentityHashSet();
/*     */   private final Class<T> baseClass;
/*  18 */   private final List<T> values = Lists.newArrayList();
/*     */   
/*     */   public boolean empty;
/*     */   
/*     */   public ClassInheritanceMultiMap(Class<T> baseClassIn) {
/*  23 */     this.baseClass = baseClassIn;
/*  24 */     this.knownKeys.add(baseClassIn);
/*  25 */     this.map.put(baseClassIn, this.values);
/*     */     
/*  27 */     for (Class<?> oclass : field_181158_a)
/*     */     {
/*  29 */       createLookup(oclass);
/*     */     }
/*     */     
/*  32 */     this.empty = this.values.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createLookup(Class<?> clazz) {
/*  37 */     field_181158_a.add(clazz);
/*  38 */     int i = this.values.size();
/*     */     
/*  40 */     for (int j = 0; j < i; j++) {
/*     */       
/*  42 */       T t = this.values.get(j);
/*     */       
/*  44 */       if (clazz.isAssignableFrom(t.getClass()))
/*     */       {
/*  46 */         addForClass(t, clazz);
/*     */       }
/*     */     } 
/*     */     
/*  50 */     this.knownKeys.add(clazz);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Class<?> initializeClassLookup(Class<?> clazz) {
/*  55 */     if (this.baseClass.isAssignableFrom(clazz)) {
/*     */       
/*  57 */       if (!this.knownKeys.contains(clazz))
/*     */       {
/*  59 */         createLookup(clazz);
/*     */       }
/*     */       
/*  62 */       return clazz;
/*     */     } 
/*     */ 
/*     */     
/*  66 */     throw new IllegalArgumentException("Don't know how to search for " + clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(T p_add_1_) {
/*  72 */     for (Class<?> oclass : this.knownKeys) {
/*     */       
/*  74 */       if (oclass.isAssignableFrom(p_add_1_.getClass()))
/*     */       {
/*  76 */         addForClass(p_add_1_, oclass);
/*     */       }
/*     */     } 
/*     */     
/*  80 */     this.empty = this.values.isEmpty();
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addForClass(T value, Class<?> parentClass) {
/*  86 */     List<T> list = this.map.get(parentClass);
/*     */     
/*  88 */     if (list == null) {
/*     */       
/*  90 */       this.map.put(parentClass, Lists.newArrayList(new Object[] { value }));
/*     */     }
/*     */     else {
/*     */       
/*  94 */       list.add(value);
/*     */     } 
/*     */     
/*  97 */     this.empty = this.values.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object p_remove_1_) {
/* 102 */     T t = (T)p_remove_1_;
/* 103 */     boolean flag = false;
/*     */     
/* 105 */     for (Class<?> oclass : this.knownKeys) {
/*     */       
/* 107 */       if (oclass.isAssignableFrom(t.getClass())) {
/*     */         
/* 109 */         List<T> list = this.map.get(oclass);
/*     */         
/* 111 */         if (list != null && list.remove(t))
/*     */         {
/* 113 */           flag = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 118 */     this.empty = this.values.isEmpty();
/* 119 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object p_contains_1_) {
/* 124 */     return Iterators.contains(getByClass(p_contains_1_.getClass()).iterator(), p_contains_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public <S> Iterable<S> getByClass(final Class<S> clazz) {
/* 129 */     return new Iterable<S>()
/*     */       {
/*     */         public Iterator<S> iterator()
/*     */         {
/* 133 */           List<T> list = (List<T>)ClassInheritanceMultiMap.this.map.get(ClassInheritanceMultiMap.this.initializeClassLookup(clazz));
/*     */           
/* 135 */           if (list == null)
/*     */           {
/* 137 */             return (Iterator<S>)Iterators.emptyIterator();
/*     */           }
/*     */ 
/*     */           
/* 141 */           Iterator<T> iterator = list.iterator();
/* 142 */           return (Iterator<S>)Iterators.filter(iterator, clazz);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<T> iterator() {
/* 150 */     return this.values.isEmpty() ? (Iterator<T>)Iterators.emptyIterator() : IteratorCache.getReadOnly(this.values);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 155 */     return this.values.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 160 */     return this.empty;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\ClassInheritanceMultiMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */