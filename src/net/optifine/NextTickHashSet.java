/*     */ package net.optifine;
/*     */ import com.google.common.collect.Iterators;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.LongHashMap;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.NextTickListEntry;
/*     */ 
/*     */ public class NextTickHashSet extends TreeSet {
/*  13 */   private final LongHashMap longHashMap = new LongHashMap();
/*  14 */   private int minX = Integer.MIN_VALUE;
/*  15 */   private int minZ = Integer.MIN_VALUE;
/*  16 */   private int maxX = Integer.MIN_VALUE;
/*  17 */   private int maxZ = Integer.MIN_VALUE;
/*     */   
/*     */   private static final int UNDEFINED = -2147483648;
/*     */   
/*     */   public NextTickHashSet(Set<? extends E> oldSet) {
/*  22 */     addAll(oldSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object obj) {
/*  27 */     if (!(obj instanceof NextTickListEntry))
/*     */     {
/*  29 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  33 */     NextTickListEntry nextticklistentry = (NextTickListEntry)obj;
/*  34 */     Set set = getSubSet(nextticklistentry, false);
/*  35 */     return (set == null) ? false : set.contains(nextticklistentry);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(Object obj) {
/*  41 */     if (!(obj instanceof NextTickListEntry))
/*     */     {
/*  43 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  47 */     NextTickListEntry nextticklistentry = (NextTickListEntry)obj;
/*     */     
/*  49 */     if (nextticklistentry == null)
/*     */     {
/*  51 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  55 */     Set<NextTickListEntry> set = getSubSet(nextticklistentry, true);
/*  56 */     boolean flag = set.add(nextticklistentry);
/*  57 */     boolean flag1 = super.add(obj);
/*     */     
/*  59 */     if (flag != flag1)
/*     */     {
/*  61 */       throw new IllegalStateException("Added: " + flag + ", addedParent: " + flag1);
/*     */     }
/*     */ 
/*     */     
/*  65 */     return flag1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(Object obj) {
/*  73 */     if (!(obj instanceof NextTickListEntry))
/*     */     {
/*  75 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  79 */     NextTickListEntry nextticklistentry = (NextTickListEntry)obj;
/*  80 */     Set set = getSubSet(nextticklistentry, false);
/*     */     
/*  82 */     if (set == null)
/*     */     {
/*  84 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  88 */     boolean flag = set.remove(nextticklistentry);
/*  89 */     boolean flag1 = super.remove(nextticklistentry);
/*     */     
/*  91 */     if (flag != flag1)
/*     */     {
/*  93 */       throw new IllegalStateException("Added: " + flag + ", addedParent: " + flag1);
/*     */     }
/*     */ 
/*     */     
/*  97 */     return flag1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Set getSubSet(NextTickListEntry entry, boolean autoCreate) {
/* 105 */     if (entry == null)
/*     */     {
/* 107 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 111 */     BlockPos blockpos = entry.position;
/* 112 */     int i = blockpos.getX() >> 4;
/* 113 */     int j = blockpos.getZ() >> 4;
/* 114 */     return getSubSet(i, j, autoCreate);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Set getSubSet(int cx, int cz, boolean autoCreate) {
/* 120 */     long i = ChunkCoordIntPair.chunkXZ2Int(cx, cz);
/* 121 */     HashSet hashset = (HashSet)this.longHashMap.getValueByKey(i);
/*     */     
/* 123 */     if (hashset == null && autoCreate) {
/*     */       
/* 125 */       hashset = new HashSet();
/* 126 */       this.longHashMap.add(i, hashset);
/*     */     } 
/*     */     
/* 129 */     return hashset;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator iterator() {
/* 134 */     if (this.minX == Integer.MIN_VALUE)
/*     */     {
/* 136 */       return super.iterator();
/*     */     }
/* 138 */     if (size() <= 0)
/*     */     {
/* 140 */       return (Iterator)Iterators.emptyIterator();
/*     */     }
/*     */ 
/*     */     
/* 144 */     int i = this.minX >> 4;
/* 145 */     int j = this.minZ >> 4;
/* 146 */     int k = this.maxX >> 4;
/* 147 */     int l = this.maxZ >> 4;
/* 148 */     List<Iterator> list = new ArrayList();
/*     */     
/* 150 */     for (int i1 = i; i1 <= k; i1++) {
/*     */       
/* 152 */       for (int j1 = j; j1 <= l; j1++) {
/*     */         
/* 154 */         Set set = getSubSet(i1, j1, false);
/*     */         
/* 156 */         if (set != null)
/*     */         {
/* 158 */           list.add(set.iterator());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     if (list.size() <= 0)
/*     */     {
/* 165 */       return (Iterator)Iterators.emptyIterator();
/*     */     }
/* 167 */     if (list.size() == 1)
/*     */     {
/* 169 */       return list.get(0);
/*     */     }
/*     */ 
/*     */     
/* 173 */     return Iterators.concat(list.iterator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIteratorLimits(int minX, int minZ, int maxX, int maxZ) {
/* 180 */     this.minX = Math.min(minX, maxX);
/* 181 */     this.minZ = Math.min(minZ, maxZ);
/* 182 */     this.maxX = Math.max(minX, maxX);
/* 183 */     this.maxZ = Math.max(minZ, maxZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearIteratorLimits() {
/* 188 */     this.minX = Integer.MIN_VALUE;
/* 189 */     this.minZ = Integer.MIN_VALUE;
/* 190 */     this.maxX = Integer.MIN_VALUE;
/* 191 */     this.maxZ = Integer.MIN_VALUE;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\NextTickHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */