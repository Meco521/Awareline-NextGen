/*    */ package com.github.creeper123123321.viafabric.providers;
/*    */ 
/*    */ import com.github.creeper123123321.viafabric.ViaFabric;
/*    */ import us.myles.ViaVersion.api.data.UserConnection;
/*    */ import us.myles.ViaVersion.protocols.base.VersionProvider;
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
/*    */ 
/*    */ 
/*    */ public class VRVersionProvider
/*    */   extends VersionProvider
/*    */ {
/*    */   public int getServerProtocol(UserConnection connection) throws Exception {
/* 37 */     if (connection instanceof com.github.creeper123123321.viafabric.platform.VRClientSideUserConnection) {
/* 38 */       return ViaFabric.clientSideVersion;
/*    */     }
/* 40 */     return super.getServerProtocol(connection);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\github\creeper123123321\viafabric\providers\VRVersionProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */