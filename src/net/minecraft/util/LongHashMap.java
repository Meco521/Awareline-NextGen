/*     */ package net.minecraft.util;
/*     */ 
/*     */ public class LongHashMap<V>
/*     */ {
/*   5 */   private transient Entry<V>[] hashArray = (Entry<V>[])new Entry[4096];
/*     */ 
/*     */   
/*     */   private transient int numHashElements;
/*     */ 
/*     */   
/*     */   private int mask;
/*     */ 
/*     */   
/*  14 */   private int capacity = 3072;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  19 */   private final float percentUseable = 0.75F;
/*     */ 
/*     */   
/*     */   private volatile transient int modCount;
/*     */ 
/*     */   
/*     */   public LongHashMap() {
/*  26 */     this.mask = this.hashArray.length - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getHashedKey(long originalKey) {
/*  34 */     return (int)(originalKey ^ originalKey >>> 27L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int hash(int integer) {
/*  42 */     integer = integer ^ integer >>> 20 ^ integer >>> 12;
/*  43 */     return integer ^ integer >>> 7 ^ integer >>> 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getHashIndex(int p_76158_0_, int p_76158_1_) {
/*  51 */     return p_76158_0_ & p_76158_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumHashElements() {
/*  56 */     return this.numHashElements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V getValueByKey(long p_76164_1_) {
/*  64 */     int i = getHashedKey(p_76164_1_);
/*     */     
/*  66 */     for (Entry<V> entry = this.hashArray[getHashIndex(i, this.mask)]; entry != null; entry = entry.nextEntry) {
/*     */       
/*  68 */       if (entry.key == p_76164_1_)
/*     */       {
/*  70 */         return entry.value;
/*     */       }
/*     */     } 
/*     */     
/*  74 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsItem(long p_76161_1_) {
/*  79 */     return (getEntry(p_76161_1_) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   final Entry<V> getEntry(long p_76160_1_) {
/*  84 */     int i = getHashedKey(p_76160_1_);
/*     */     
/*  86 */     for (Entry<V> entry = this.hashArray[getHashIndex(i, this.mask)]; entry != null; entry = entry.nextEntry) {
/*     */       
/*  88 */       if (entry.key == p_76160_1_)
/*     */       {
/*  90 */         return entry;
/*     */       }
/*     */     } 
/*     */     
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(long p_76163_1_, V p_76163_3_) {
/* 102 */     int i = getHashedKey(p_76163_1_);
/* 103 */     int j = getHashIndex(i, this.mask);
/*     */     
/* 105 */     for (Entry<V> entry = this.hashArray[j]; entry != null; entry = entry.nextEntry) {
/*     */       
/* 107 */       if (entry.key == p_76163_1_) {
/*     */         
/* 109 */         entry.value = p_76163_3_;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 114 */     this.modCount++;
/* 115 */     createKey(i, p_76163_1_, p_76163_3_, j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void resizeTable(int p_76153_1_) {
/* 123 */     Entry<V>[] entry = this.hashArray;
/* 124 */     int i = entry.length;
/*     */     
/* 126 */     if (i == 1073741824) {
/*     */       
/* 128 */       this.capacity = Integer.MAX_VALUE;
/*     */     }
/*     */     else {
/*     */       
/* 132 */       Entry[] arrayOfEntry = new Entry[p_76153_1_];
/* 133 */       copyHashTableTo((Entry<V>[])arrayOfEntry);
/* 134 */       this.hashArray = (Entry<V>[])arrayOfEntry;
/* 135 */       this.mask = this.hashArray.length - 1;
/* 136 */       float f = p_76153_1_;
/* 137 */       getClass();
/* 138 */       this.capacity = (int)(f * 0.75F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void copyHashTableTo(Entry<V>[] p_76154_1_) {
/* 147 */     Entry<V>[] entry = this.hashArray;
/* 148 */     int i = p_76154_1_.length;
/*     */     
/* 150 */     for (int j = 0; j < entry.length; j++) {
/*     */       
/* 152 */       Entry<V> entry1 = entry[j];
/*     */       
/* 154 */       if (entry1 != null) {
/*     */         Entry<V> entry2;
/* 156 */         entry[j] = null;
/*     */ 
/*     */         
/*     */         do {
/* 160 */           entry2 = entry1.nextEntry;
/* 161 */           int k = getHashIndex(entry1.hash, i - 1);
/* 162 */           entry1.nextEntry = p_76154_1_[k];
/* 163 */           p_76154_1_[k] = entry1;
/* 164 */           entry1 = entry2;
/*     */         }
/* 166 */         while (entry2 != null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V remove(long p_76159_1_) {
/* 180 */     Entry<V> entry = removeKey(p_76159_1_);
/* 181 */     return (entry == null) ? null : entry.value;
/*     */   }
/*     */ 
/*     */   
/*     */   final Entry<V> removeKey(long p_76152_1_) {
/* 186 */     int i = getHashedKey(p_76152_1_);
/* 187 */     int j = getHashIndex(i, this.mask);
/* 188 */     Entry<V> entry = this.hashArray[j];
/*     */     
/*     */     Entry<V> entry1;
/*     */     
/* 192 */     for (entry1 = entry; entry1 != null; entry1 = entry2) {
/*     */       
/* 194 */       Entry<V> entry2 = entry1.nextEntry;
/*     */       
/* 196 */       if (entry1.key == p_76152_1_) {
/*     */         
/* 198 */         this.modCount++;
/* 199 */         this.numHashElements--;
/*     */         
/* 201 */         if (entry == entry1) {
/*     */           
/* 203 */           this.hashArray[j] = entry2;
/*     */         }
/*     */         else {
/*     */           
/* 207 */           entry.nextEntry = entry2;
/*     */         } 
/*     */         
/* 210 */         return entry1;
/*     */       } 
/*     */       
/* 213 */       entry = entry1;
/*     */     } 
/*     */     
/* 216 */     return entry1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createKey(int p_76156_1_, long p_76156_2_, V p_76156_4_, int p_76156_5_) {
/* 224 */     Entry<V> entry = this.hashArray[p_76156_5_];
/* 225 */     this.hashArray[p_76156_5_] = new Entry<>(p_76156_1_, p_76156_2_, p_76156_4_, entry);
/*     */     
/* 227 */     if (this.numHashElements++ >= this.capacity)
/*     */     {
/* 229 */       resizeTable(2 * this.hashArray.length);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public double getKeyDistribution() {
/* 235 */     int i = 0;
/*     */     
/* 237 */     for (int j = 0; j < this.hashArray.length; j++) {
/*     */       
/* 239 */       if (this.hashArray[j] != null)
/*     */       {
/* 241 */         i++;
/*     */       }
/*     */     } 
/*     */     
/* 245 */     return i / this.numHashElements;
/*     */   }
/*     */ 
/*     */   
/*     */   static class Entry<V>
/*     */   {
/*     */     final long key;
/*     */     V value;
/*     */     Entry<V> nextEntry;
/*     */     final int hash;
/*     */     
/*     */     Entry(int p_i1553_1_, long p_i1553_2_, V p_i1553_4_, Entry<V> p_i1553_5_) {
/* 257 */       this.value = p_i1553_4_;
/* 258 */       this.nextEntry = p_i1553_5_;
/* 259 */       this.key = p_i1553_2_;
/* 260 */       this.hash = p_i1553_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public final long getKey() {
/* 265 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public final V getValue() {
/* 270 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean equals(Object p_equals_1_) {
/* 275 */       if (!(p_equals_1_ instanceof Entry))
/*     */       {
/* 277 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 281 */       Entry<V> entry = (Entry<V>)p_equals_1_;
/* 282 */       Object object = Long.valueOf(this.key);
/* 283 */       Object object1 = Long.valueOf(entry.key);
/*     */       
/* 285 */       if (object == object1 || object1.equals(object)) {
/*     */         
/* 287 */         Object object2 = this.value;
/* 288 */         Object object3 = entry.value;
/*     */         
/* 290 */         if (object2 == object3 || (object2 != null && object2.equals(object3)))
/*     */         {
/* 292 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 296 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final int hashCode() {
/* 302 */       return LongHashMap.getHashedKey(this.key);
/*     */     }
/*     */ 
/*     */     
/*     */     public final String toString() {
/* 307 */       return this.key + "=" + this.value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\LongHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */