/*    */ package com.github.creeper123123321.viafabric.util;
/*    */ 
/*    */ import java.util.concurrent.Future;
/*    */ import us.myles.ViaVersion.api.platform.TaskId;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FutureTaskId
/*    */   implements TaskId
/*    */ {
/*    */   private final Future<?> object;
/*    */   
/*    */   public FutureTaskId(Future<?> object) {
/* 36 */     this.object = object;
/*    */   }
/*    */ 
/*    */   
/*    */   public Future<?> getObject() {
/* 41 */     return this.object;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\github\creeper123123321\viafabri\\util\FutureTaskId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */