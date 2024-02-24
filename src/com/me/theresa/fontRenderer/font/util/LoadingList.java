/*    */ package com.me.theresa.fontRenderer.font.util;
/*    */ 
/*    */ import com.me.theresa.fontRenderer.font.log.Log;
/*    */ import com.me.theresa.fontRenderer.font.opengl.InternalTextureLoader;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LoadingList
/*    */ {
/* 11 */   private static LoadingList single = new LoadingList();
/*    */ 
/*    */   
/*    */   public static LoadingList get() {
/* 15 */     return single;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void setDeferredLoading(boolean loading) {
/* 20 */     single = new LoadingList();
/* 21 */     InternalTextureLoader.get().setDeferredLoading(loading);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isDeferredLoading() {
/* 26 */     return InternalTextureLoader.get().isDeferredLoading();
/*    */   }
/*    */ 
/*    */   
/* 30 */   private final ArrayList deferred = new ArrayList();
/*    */ 
/*    */ 
/*    */   
/*    */   private int total;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(DeferredResource resource) {
/* 40 */     this.total++;
/* 41 */     this.deferred.add(resource);
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove(DeferredResource resource) {
/* 46 */     Log.info("Early loading of deferred resource due to req: " + resource.getDescription());
/* 47 */     this.total--;
/* 48 */     this.deferred.remove(resource);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTotalResources() {
/* 53 */     return this.total;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRemainingResources() {
/* 58 */     return this.deferred.size();
/*    */   }
/*    */   
/*    */   public DeferredResource getNext() {
/* 62 */     if (this.deferred.size() == 0) {
/* 63 */       return null;
/*    */     }
/*    */     
/* 66 */     return this.deferred.remove(0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\fon\\util\LoadingList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */