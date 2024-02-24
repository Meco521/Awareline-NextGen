/*    */ package net.optifine;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class DynamicLightsMap
/*    */ {
/* 10 */   private final Map<Integer, DynamicLight> map = new HashMap<>();
/* 11 */   private final List<DynamicLight> list = new ArrayList<>();
/*    */   
/*    */   private boolean dirty = false;
/*    */   
/*    */   public DynamicLight put(int id, DynamicLight dynamicLight) {
/* 16 */     DynamicLight dynamiclight = this.map.put(Integer.valueOf(id), dynamicLight);
/* 17 */     setDirty();
/* 18 */     return dynamiclight;
/*    */   }
/*    */ 
/*    */   
/*    */   public DynamicLight get(int id) {
/* 23 */     return this.map.get(Integer.valueOf(id));
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 28 */     return this.map.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public DynamicLight remove(int id) {
/* 33 */     DynamicLight dynamiclight = this.map.remove(Integer.valueOf(id));
/*    */     
/* 35 */     if (dynamiclight != null)
/*    */     {
/* 37 */       setDirty();
/*    */     }
/*    */     
/* 40 */     return dynamiclight;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 45 */     this.map.clear();
/* 46 */     this.list.clear();
/* 47 */     setDirty();
/*    */   }
/*    */ 
/*    */   
/*    */   private void setDirty() {
/* 52 */     this.dirty = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<DynamicLight> valueList() {
/* 57 */     if (this.dirty) {
/*    */       
/* 59 */       this.list.clear();
/* 60 */       this.list.addAll(this.map.values());
/* 61 */       this.dirty = false;
/*    */     } 
/*    */     
/* 64 */     return this.list;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\DynamicLightsMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */