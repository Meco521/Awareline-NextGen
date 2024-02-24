/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.base.Predicates;
/*    */ import com.google.common.collect.Iterators;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.IdentityHashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ObjectIntIdentityMap<T>
/*    */   implements IObjectIntIterable<T>
/*    */ {
/* 13 */   private final IdentityHashMap<T, Integer> identityMap = new IdentityHashMap<>(512);
/* 14 */   private final List<T> objectList = Lists.newArrayList();
/*    */ 
/*    */   
/*    */   public void put(T key, int value) {
/* 18 */     this.identityMap.put(key, Integer.valueOf(value));
/*    */     
/* 20 */     while (this.objectList.size() <= value)
/*    */     {
/* 22 */       this.objectList.add(null);
/*    */     }
/*    */     
/* 25 */     this.objectList.set(value, key);
/*    */   }
/*    */ 
/*    */   
/*    */   public int get(T key) {
/* 30 */     Integer integer = this.identityMap.get(key);
/* 31 */     return (integer == null) ? -1 : integer.intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public final T getByValue(int value) {
/* 36 */     return (value >= 0 && value < this.objectList.size()) ? this.objectList.get(value) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<T> iterator() {
/* 41 */     return (Iterator<T>)Iterators.filter(this.objectList.iterator(), Predicates.notNull());
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\ObjectIntIdentityMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */