/*    */ package com.github.creeper123123321.viafabric.util;
/*    */ 
/*    */ import us.myles.ViaVersion.api.protocol.ProtocolVersion;
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
/*    */ public class ProtocolUtils
/*    */ {
/*    */   public static String getProtocolName(int id) {
/* 33 */     if (!ProtocolVersion.isRegistered(id)) return Integer.toString(id); 
/* 34 */     return ProtocolVersion.getProtocol(id).getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\github\creeper123123321\viafabri\\util\ProtocolUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */