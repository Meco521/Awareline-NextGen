/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ 
/*    */ public class NativeMemory
/*    */ {
/* 11 */   private static final LongSupplier bufferAllocatedSupplier = makeLongSupplier(new String[][] { { "sun.misc.SharedSecrets", "getJavaNioAccess", "getDirectBufferPool", "getMemoryUsed" }, { "jdk.internal.misc.SharedSecrets", "getJavaNioAccess", "getDirectBufferPool", "getMemoryUsed" } });
/* 12 */   private static final LongSupplier bufferMaximumSupplier = makeLongSupplier(new String[][] { { "sun.misc.VM", "maxDirectMemory" }, { "jdk.internal.misc.VM", "maxDirectMemory" } });
/*    */ 
/*    */   
/*    */   public static long getBufferAllocated() {
/* 16 */     return (bufferAllocatedSupplier == null) ? -1L : bufferAllocatedSupplier.getAsLong();
/*    */   }
/*    */ 
/*    */   
/*    */   public static long getBufferMaximum() {
/* 21 */     return (bufferMaximumSupplier == null) ? -1L : bufferMaximumSupplier.getAsLong();
/*    */   }
/*    */ 
/*    */   
/*    */   private static LongSupplier makeLongSupplier(String[][] paths) {
/* 26 */     List<Throwable> list = new ArrayList<>();
/*    */     
/* 28 */     for (int i = 0; i < paths.length; i++) {
/*    */       
/* 30 */       String[] astring = paths[i];
/*    */ 
/*    */       
/*    */       try {
/* 34 */         LongSupplier longsupplier = makeLongSupplier(astring);
/* 35 */         return longsupplier;
/*    */       }
/* 37 */       catch (Throwable throwable) {
/*    */         
/* 39 */         list.add(throwable);
/*    */       } 
/*    */     } 
/*    */     
/* 43 */     for (Throwable throwable1 : list)
/*    */     {
/* 45 */       Config.warn(throwable1.getClass().getName() + ": " + throwable1.getMessage());
/*    */     }
/*    */     
/* 48 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   private static LongSupplier makeLongSupplier(String[] path) throws Exception {
/* 53 */     if (path.length < 2)
/*    */     {
/* 55 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 59 */     Class<?> oclass = Class.forName(path[0]);
/* 60 */     Method method = oclass.getMethod(path[1], new Class[0]);
/* 61 */     method.setAccessible(true);
/* 62 */     Object object = null;
/*    */     
/* 64 */     for (int i = 2; i < path.length; i++) {
/*    */       
/* 66 */       String s = path[i];
/* 67 */       object = method.invoke(object, new Object[0]);
/* 68 */       method = object.getClass().getMethod(s, new Class[0]);
/* 69 */       method.setAccessible(true);
/*    */     } 
/*    */     
/* 72 */     final Method method1 = method;
/* 73 */     final Object o = object;
/* 74 */     LongSupplier longsupplier = new LongSupplier()
/*    */       {
/*    */         private boolean disabled = false;
/*    */         
/*    */         public long getAsLong() {
/* 79 */           if (this.disabled)
/*    */           {
/* 81 */             return -1L;
/*    */           }
/*    */ 
/*    */ 
/*    */           
/*    */           try {
/* 87 */             return ((Long)method1.invoke(o, new Object[0])).longValue();
/*    */           }
/* 89 */           catch (Throwable throwable) {
/*    */             
/* 91 */             Config.warn(throwable.getClass().getName() + ": " + throwable.getMessage());
/* 92 */             this.disabled = true;
/* 93 */             return -1L;
/*    */           } 
/*    */         }
/*    */       };
/*    */     
/* 98 */     return longsupplier;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\NativeMemory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */