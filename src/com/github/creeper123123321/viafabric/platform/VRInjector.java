/*    */ package com.github.creeper123123321.viafabric.platform;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.Arrays;
/*    */ import us.myles.ViaVersion.api.platform.ViaInjector;
/*    */ import us.myles.ViaVersion.util.GsonUtil;
/*    */ import us.myles.viaversion.libs.gson.JsonObject;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VRInjector
/*    */   implements ViaInjector
/*    */ {
/*    */   public void inject() {}
/*    */   
/*    */   public void uninject() {}
/*    */   
/*    */   public int getServerProtocolVersion() {
/* 49 */     return getClientProtocol();
/*    */   }
/*    */   
/*    */   private int getClientProtocol() {
/* 53 */     return 47;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getEncoderName() {
/* 58 */     return "via-encoder";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDecoderName() {
/* 63 */     return "via-decoder";
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonObject getDump() {
/* 68 */     JsonObject obj = new JsonObject();
/*    */     try {
/* 70 */       obj.add("serverNetworkIOChInit", GsonUtil.getGson().toJsonTree(
/* 71 */             Arrays.<Method>stream(Class.forName("net.minecraft.class_3242$1").getDeclaredMethods())
/* 72 */             .map(Method::toString)
/* 73 */             .toArray(x$0 -> new String[x$0])));
/* 74 */     } catch (ClassNotFoundException classNotFoundException) {}
/*    */     
/*    */     try {
/* 77 */       obj.add("clientConnectionChInit", GsonUtil.getGson().toJsonTree(
/* 78 */             Arrays.<Method>stream(Class.forName("net.minecraft.class_2535$1").getDeclaredMethods())
/* 79 */             .map(Method::toString)
/* 80 */             .toArray(x$0 -> new String[x$0])));
/* 81 */     } catch (ClassNotFoundException classNotFoundException) {}
/*    */     
/* 83 */     return obj;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\github\creeper123123321\viafabric\platform\VRInjector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */