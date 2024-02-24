/*    */ package com.github.creeper123123321.viafabric.platform;
/*    */ 
/*    */ import us.myles.ViaVersion.api.data.UserConnection;
/*    */ import us.myles.ViaVersion.api.platform.ViaConnectionManager;
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
/*    */ public class VRConnectionManager
/*    */   extends ViaConnectionManager
/*    */ {
/*    */   public boolean isFrontEnd(UserConnection connection) {
/* 34 */     return !(connection instanceof VRClientSideUserConnection);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\github\creeper123123321\viafabric\platform\VRConnectionManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */