/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayDeque;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ 
/*     */ public class CacheObjectArray
/*     */ {
/*  11 */   private static final ArrayDeque<int[]> arrays = (ArrayDeque)new ArrayDeque<>();
/*     */   
/*     */   private static final int maxCacheSize = 10;
/*     */   
/*     */   private static synchronized int[] allocateArray(int size) {
/*  16 */     int[] aint = arrays.pollLast();
/*     */     
/*  18 */     if (aint == null || aint.length < size)
/*     */     {
/*  20 */       aint = new int[size];
/*     */     }
/*     */     
/*  23 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized void freeArray(int[] ints) {
/*  28 */     if (arrays.size() < 10)
/*     */     {
/*  30 */       arrays.add(ints);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/*  35 */     int i = 4096;
/*  36 */     int j = 500000;
/*  37 */     testNew(i, j);
/*  38 */     testClone(i, j);
/*  39 */     testNewObj(i, j);
/*  40 */     testCloneObj(i, j);
/*  41 */     testNewObjDyn(IBlockState.class, i, j);
/*  42 */     long k = testNew(i, j);
/*  43 */     long l = testClone(i, j);
/*  44 */     long i1 = testNewObj(i, j);
/*  45 */     long j1 = testCloneObj(i, j);
/*  46 */     long k1 = testNewObjDyn(IBlockState.class, i, j);
/*  47 */     Config.dbg("New: " + k);
/*  48 */     Config.dbg("Clone: " + l);
/*  49 */     Config.dbg("NewObj: " + i1);
/*  50 */     Config.dbg("CloneObj: " + j1);
/*  51 */     Config.dbg("NewObjDyn: " + k1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static long testClone(int size, int count) {
/*  56 */     long i = System.currentTimeMillis();
/*  57 */     int[] aint = new int[size];
/*     */     
/*  59 */     for (int j = 0; j < count; j++)
/*     */     {
/*  61 */       int[] arrayOfInt = (int[])aint.clone();
/*     */     }
/*     */     
/*  64 */     long k = System.currentTimeMillis();
/*  65 */     return k - i;
/*     */   }
/*     */ 
/*     */   
/*     */   private static long testNew(int size, int count) {
/*  70 */     long i = System.currentTimeMillis();
/*     */     
/*  72 */     for (int j = 0; j < count; j++)
/*     */     {
/*  74 */       int[] arrayOfInt = (int[])Array.newInstance(int.class, size);
/*     */     }
/*     */     
/*  77 */     long k = System.currentTimeMillis();
/*  78 */     return k - i;
/*     */   }
/*     */ 
/*     */   
/*     */   private static long testCloneObj(int size, int count) {
/*  83 */     long i = System.currentTimeMillis();
/*  84 */     IBlockState[] aiblockstate = new IBlockState[size];
/*     */     
/*  86 */     for (int j = 0; j < count; j++)
/*     */     {
/*  88 */       IBlockState[] arrayOfIBlockState = (IBlockState[])aiblockstate.clone();
/*     */     }
/*     */     
/*  91 */     long k = System.currentTimeMillis();
/*  92 */     return k - i;
/*     */   }
/*     */ 
/*     */   
/*     */   private static long testNewObj(int size, int count) {
/*  97 */     long i = System.currentTimeMillis();
/*     */     
/*  99 */     for (int j = 0; j < count; j++)
/*     */     {
/* 101 */       IBlockState[] arrayOfIBlockState = new IBlockState[size];
/*     */     }
/*     */     
/* 104 */     long k = System.currentTimeMillis();
/* 105 */     return k - i;
/*     */   }
/*     */ 
/*     */   
/*     */   private static long testNewObjDyn(Class<?> cls, int size, int count) {
/* 110 */     long i = System.currentTimeMillis();
/*     */     
/* 112 */     for (int j = 0; j < count; j++)
/*     */     {
/* 114 */       Object[] arrayOfObject = (Object[])Array.newInstance(cls, size);
/*     */     }
/*     */     
/* 117 */     long k = System.currentTimeMillis();
/* 118 */     return k - i;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\CacheObjectArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */