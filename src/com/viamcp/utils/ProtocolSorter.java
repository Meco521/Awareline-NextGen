/*    */ package com.viamcp.utils;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.Collections;
/*    */ import java.util.LinkedList;
/*    */ import us.myles.ViaVersion.api.protocol.ProtocolVersion;
/*    */ 
/*    */ 
/*    */ public class ProtocolSorter
/*    */ {
/* 11 */   private static final LinkedList<ProtocolVersion> protocolVersions = new LinkedList<>();
/*    */   
/*    */   private static int count;
/*    */   
/*    */   static {
/* 16 */     for (Field f : ProtocolVersion.class.getDeclaredFields()) {
/* 17 */       if (f.getType().equals(ProtocolVersion.class)) {
/* 18 */         count++;
/*    */         try {
/* 20 */           ProtocolVersion ver = (ProtocolVersion)f.get(null);
/* 21 */           if (count >= 8 && !ver.getName().equals("UNKNOWN"))
/* 22 */             protocolVersions.add(ver); 
/* 23 */         } catch (IllegalAccessException e) {
/* 24 */           e.printStackTrace();
/*    */         } 
/*    */       } 
/*    */     } 
/* 28 */     Collections.reverse(protocolVersions);
/*    */   }
/*    */   
/*    */   public static LinkedList<ProtocolVersion> getProtocolVersions() {
/* 32 */     return protocolVersions;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\viamc\\utils\ProtocolSorter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */