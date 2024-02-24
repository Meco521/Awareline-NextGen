/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ public class CompactArrayList
/*     */ {
/*     */   private ArrayList list;
/*     */   private int initialCapacity;
/*     */   private float loadFactor;
/*     */   private int countValid;
/*     */   
/*     */   public CompactArrayList() {
/*  14 */     this(10, 0.75F);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompactArrayList(int initialCapacity) {
/*  19 */     this(initialCapacity, 0.75F);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompactArrayList(int initialCapacity, float loadFactor) {
/*  24 */     this.list = null;
/*  25 */     this.initialCapacity = 0;
/*  26 */     this.loadFactor = 1.0F;
/*  27 */     this.countValid = 0;
/*  28 */     this.list = new ArrayList(initialCapacity);
/*  29 */     this.initialCapacity = initialCapacity;
/*  30 */     this.loadFactor = loadFactor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int index, Object element) {
/*  35 */     if (element != null)
/*     */     {
/*  37 */       this.countValid++;
/*     */     }
/*     */     
/*  40 */     this.list.add(index, element);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(Object element) {
/*  45 */     if (element != null)
/*     */     {
/*  47 */       this.countValid++;
/*     */     }
/*     */     
/*  50 */     return this.list.add(element);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object set(int index, Object element) {
/*  55 */     Object object = this.list.set(index, element);
/*     */     
/*  57 */     if (element != object) {
/*     */       
/*  59 */       if (object == null)
/*     */       {
/*  61 */         this.countValid++;
/*     */       }
/*     */       
/*  64 */       if (element == null)
/*     */       {
/*  66 */         this.countValid--;
/*     */       }
/*     */     } 
/*     */     
/*  70 */     return object;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object remove(int index) {
/*  75 */     Object object = this.list.remove(index);
/*     */     
/*  77 */     if (object != null)
/*     */     {
/*  79 */       this.countValid--;
/*     */     }
/*     */     
/*  82 */     return object;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  87 */     this.list.clear();
/*  88 */     this.countValid = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void compact() {
/*  93 */     if (this.countValid <= 0 && this.list.size() <= 0) {
/*     */       
/*  95 */       clear();
/*     */     }
/*  97 */     else if (this.list.size() > this.initialCapacity) {
/*     */       
/*  99 */       float f = this.countValid / this.list.size();
/*     */       
/* 101 */       if (f <= this.loadFactor) {
/*     */         
/* 103 */         int i = 0;
/*     */         
/* 105 */         for (int j = 0; j < this.list.size(); j++) {
/*     */           
/* 107 */           Object object = this.list.get(j);
/*     */           
/* 109 */           if (object != null) {
/*     */             
/* 111 */             if (j != i)
/*     */             {
/* 113 */               this.list.set(i, object);
/*     */             }
/*     */             
/* 116 */             i++;
/*     */           } 
/*     */         } 
/*     */         
/* 120 */         if (this.list.size() > i) {
/* 121 */           this.list.subList(i, this.list.size()).clear();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object elem) {
/* 129 */     return this.list.contains(elem);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object get(int index) {
/* 134 */     return this.list.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 139 */     return this.list.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 144 */     return this.list.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCountValid() {
/* 149 */     return this.countValid;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\CompactArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */