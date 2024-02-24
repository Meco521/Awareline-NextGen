/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityList;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ 
/*    */ public class EntityUtils
/*    */ {
/* 12 */   private static final Map<Class, Integer> mapIdByClass = (Map)new HashMap<>();
/* 13 */   private static final Map<String, Integer> mapIdByName = new HashMap<>();
/* 14 */   private static final Map<String, Class> mapClassByName = (Map)new HashMap<>();
/*    */ 
/*    */   
/*    */   public static int getEntityIdByClass(Entity entity) {
/* 18 */     return (entity == null) ? -1 : getEntityIdByClass(entity.getClass());
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getEntityIdByClass(Class cls) {
/* 23 */     Integer integer = mapIdByClass.get(cls);
/* 24 */     return (integer == null) ? -1 : integer.intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getEntityIdByName(String name) {
/* 29 */     Integer integer = mapIdByName.get(name);
/* 30 */     return (integer == null) ? -1 : integer.intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public static Class getEntityClassByName(String name) {
/* 35 */     Class oclass = mapClassByName.get(name);
/* 36 */     return oclass;
/*    */   }
/*    */ 
/*    */   
/*    */   static {
/* 41 */     for (int i = 0; i < 1000; i++) {
/*    */       
/* 43 */       Class oclass = EntityList.getClassFromID(i);
/*    */       
/* 45 */       if (oclass != null) {
/*    */         
/* 47 */         String s = EntityList.getStringFromID(i);
/*    */         
/* 49 */         if (s != null) {
/*    */           
/* 51 */           if (mapIdByClass.containsKey(oclass))
/*    */           {
/* 53 */             Config.warn("Duplicate entity class: " + oclass + ", id1: " + mapIdByClass.get(oclass) + ", id2: " + i);
/*    */           }
/*    */           
/* 56 */           if (mapIdByName.containsKey(s))
/*    */           {
/* 58 */             Config.warn("Duplicate entity name: " + s + ", id1: " + mapIdByName.get(s) + ", id2: " + i);
/*    */           }
/*    */           
/* 61 */           if (mapClassByName.containsKey(s))
/*    */           {
/* 63 */             Config.warn("Duplicate entity name: " + s + ", class1: " + mapClassByName.get(s) + ", class2: " + oclass);
/*    */           }
/*    */           
/* 66 */           mapIdByClass.put(oclass, Integer.valueOf(i));
/* 67 */           mapIdByName.put(s, Integer.valueOf(i));
/* 68 */           mapClassByName.put(s, oclass);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\EntityUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */