/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.NoSuchElementException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapPopulator
/*    */ {
/*    */   public static <K, V> Map<K, V> createMap(Iterable<K> keys, Iterable<V> values) {
/* 13 */     return populateMap(keys, values, Maps.newLinkedHashMap());
/*    */   }
/*    */ 
/*    */   
/*    */   public static <K, V> Map<K, V> populateMap(Iterable<K> keys, Iterable<V> values, Map<K, V> map) {
/* 18 */     Iterator<V> iterator = values.iterator();
/*    */     
/* 20 */     for (K k : keys)
/*    */     {
/* 22 */       map.put(k, iterator.next());
/*    */     }
/*    */     
/* 25 */     if (iterator.hasNext())
/*    */     {
/* 27 */       throw new NoSuchElementException();
/*    */     }
/*    */ 
/*    */     
/* 31 */     return map;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\MapPopulator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */