/*    */ package com.compiler;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.util.concurrent.CompletableFuture;
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
/*    */ public class CloseableByteArrayOutputStream
/*    */   extends ByteArrayOutputStream
/*    */ {
/* 25 */   private final CompletableFuture<?> closeFuture = new CompletableFuture();
/*    */ 
/*    */   
/*    */   public void close() {
/* 29 */     this.closeFuture.complete(null);
/*    */   }
/*    */   
/*    */   public CompletableFuture<?> closeFuture() {
/* 33 */     return this.closeFuture;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\compiler\CloseableByteArrayOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */