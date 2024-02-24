/*    */ package com.viamcp.utils;
/*    */ 
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import io.netty.channel.ChannelPipeline;
/*    */ 
/*    */ 
/*    */ public class Util
/*    */ {
/*    */   public static void decodeEncodePlacement(ChannelPipeline instance, String base, String newHandler, ChannelHandler handler) {
/* 10 */     switch (base) {
/*    */       case "decoder":
/* 12 */         if (instance.get("via-decoder") != null) {
/* 13 */           base = "via-decoder";
/*    */         }
/*    */         break;
/*    */       case "encoder":
/* 17 */         if (instance.get("via-encoder") != null) {
/* 18 */           base = "via-encoder";
/*    */         }
/*    */         break;
/*    */     } 
/* 22 */     instance.addBefore(base, newHandler, handler);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\viamc\\utils\Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */