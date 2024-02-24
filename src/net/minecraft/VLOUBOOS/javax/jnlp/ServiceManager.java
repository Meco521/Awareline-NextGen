/*    */ package net.minecraft.VLOUBOOS.javax.jnlp;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ServiceManager
/*    */ {
/* 12 */   private static ServiceManagerStub stub = null;
/*    */   
/* 14 */   private static final Map lookupTable = new HashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Object lookup(String name) throws UnavailableServiceException {
/* 22 */     if (stub == null) {
/* 23 */       throw new UnavailableServiceException("service stub not set.");
/*    */     }
/* 25 */     synchronized (lookupTable) {
/* 26 */       Object result = lookupTable.get(name);
/*    */       
/* 28 */       if (result == null) {
/* 29 */         result = stub.lookup(name);
/* 30 */         if (result != null) {
/* 31 */           lookupTable.put(name, result);
/*    */         }
/*    */       } 
/* 34 */       if (result == null) {
/* 35 */         throw new UnavailableServiceException("service not available (stub returned null).");
/*    */       }
/* 37 */       return result;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static String[] getServiceNames() {
/* 43 */     if (stub == null) {
/* 44 */       return new String[0];
/*    */     }
/* 46 */     return stub.getServiceNames();
/*    */   }
/*    */   
/*    */   public static void setServiceManagerStub(ServiceManagerStub stub) {
/* 50 */     if (ServiceManager.stub == null)
/* 51 */       ServiceManager.stub = stub; 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\VLOUBOOS\javax\jnlp\ServiceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */