/*    */ package net.minecraft.util;
/*    */ 
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegistryNamespacedDefaultedByKey<K, V>
/*    */   extends RegistryNamespaced<K, V>
/*    */ {
/*    */   private final K defaultValueKey;
/*    */   private V defaultValue;
/*    */   
/*    */   public RegistryNamespacedDefaultedByKey(K defaultValueKeyIn) {
/* 17 */     this.defaultValueKey = defaultValueKeyIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void register(int id, K key, V value) {
/* 22 */     if (this.defaultValueKey.equals(key))
/*    */     {
/* 24 */       this.defaultValue = value;
/*    */     }
/*    */     
/* 27 */     super.register(id, key, value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void validateKey() {
/* 35 */     Validate.notNull(this.defaultValueKey);
/*    */   }
/*    */ 
/*    */   
/*    */   public V getObject(K name) {
/* 40 */     V v = super.getObject(name);
/* 41 */     return (v == null) ? this.defaultValue : v;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public V getObjectById(int id) {
/* 49 */     V v = super.getObjectById(id);
/* 50 */     return (v == null) ? this.defaultValue : v;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\RegistryNamespacedDefaultedByKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */