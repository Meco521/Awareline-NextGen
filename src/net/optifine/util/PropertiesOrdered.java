/*    */ package net.optifine.util;
/*    */ import java.util.Collections;
/*    */ import java.util.Properties;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class PropertiesOrdered extends Properties {
/*  7 */   private final Set<Object> keysOrdered = new LinkedHashSet();
/*    */ 
/*    */   
/*    */   public synchronized Object put(Object key, Object value) {
/* 11 */     this.keysOrdered.add(key);
/* 12 */     return super.put(key, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<Object> keySet() {
/* 17 */     Set<Object> set = super.keySet();
/* 18 */     this.keysOrdered.retainAll(set);
/* 19 */     return Collections.unmodifiableSet(this.keysOrdered);
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized Enumeration<Object> keys() {
/* 24 */     return Collections.enumeration(keySet());
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\PropertiesOrdered.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */