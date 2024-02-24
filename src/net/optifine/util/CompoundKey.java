/*     */ package net.optifine.util;
/*     */ 
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ 
/*     */ public class CompoundKey
/*     */ {
/*     */   private final Object[] keys;
/*     */   private int hashcode;
/*     */   
/*     */   public CompoundKey(Object[] keys) {
/*  12 */     this.hashcode = 0;
/*  13 */     this.keys = (Object[])keys.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundKey(Object k1, Object k2) {
/*  18 */     this(new Object[] { k1, k2 });
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundKey(Object k1, Object k2, Object k3) {
/*  23 */     this(new Object[] { k1, k2, k3 });
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  28 */     if (this.hashcode == 0) {
/*     */       
/*  30 */       this.hashcode = 7;
/*     */       
/*  32 */       for (int i = 0; i < this.keys.length; i++) {
/*     */         
/*  34 */         Object object = this.keys[i];
/*     */         
/*  36 */         if (object != null)
/*     */         {
/*  38 */           this.hashcode = 31 * this.hashcode + object.hashCode();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  43 */     return this.hashcode;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  48 */     if (obj == null)
/*     */     {
/*  50 */       return false;
/*     */     }
/*  52 */     if (obj == this)
/*     */     {
/*  54 */       return true;
/*     */     }
/*  56 */     if (!(obj instanceof CompoundKey))
/*     */     {
/*  58 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  62 */     CompoundKey compoundkey = (CompoundKey)obj;
/*  63 */     Object[] aobject = compoundkey.getKeys();
/*     */     
/*  65 */     if (aobject.length != this.keys.length)
/*     */     {
/*  67 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  71 */     for (int i = 0; i < this.keys.length; i++) {
/*     */       
/*  73 */       if (!compareKeys(this.keys[i], aobject[i]))
/*     */       {
/*  75 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean compareKeys(Object key1, Object key2) {
/*  86 */     return (key1 == key2) ? true : ((key1 == null) ? false : ((key2 == null) ? false : key1.equals(key2)));
/*     */   }
/*     */ 
/*     */   
/*     */   private Object[] getKeys() {
/*  91 */     return this.keys;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] getKeysCopy() {
/*  96 */     return (Object[])this.keys.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 101 */     return "[" + Config.arrayToString(this.keys) + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\CompoundKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */