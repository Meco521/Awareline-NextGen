/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ReflectorResolver
/*    */ {
/*  9 */   private static final List<IResolvable> RESOLVABLES = Collections.synchronizedList(new ArrayList<>());
/*    */   
/*    */   private static boolean resolved = false;
/*    */   
/*    */   protected static void register(IResolvable resolvable) {
/* 14 */     if (!resolved) {
/*    */       
/* 16 */       RESOLVABLES.add(resolvable);
/*    */     }
/*    */     else {
/*    */       
/* 20 */       resolvable.resolve();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void resolve() {
/* 26 */     if (!resolved) {
/*    */       
/* 28 */       for (IResolvable iresolvable : RESOLVABLES)
/*    */       {
/* 30 */         iresolvable.resolve();
/*    */       }
/*    */       
/* 33 */       resolved = true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\reflect\ReflectorResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */