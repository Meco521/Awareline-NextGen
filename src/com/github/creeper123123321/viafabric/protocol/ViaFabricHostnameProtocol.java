/*    */ package com.github.creeper123123321.viafabric.protocol;
/*    */ 
/*    */ import us.myles.ViaVersion.api.PacketWrapper;
/*    */ import us.myles.ViaVersion.api.protocol.SimpleProtocol;
/*    */ import us.myles.ViaVersion.api.remapper.PacketRemapper;
/*    */ import us.myles.ViaVersion.api.remapper.ValueTransformer;
/*    */ import us.myles.ViaVersion.api.type.Type;
/*    */ import us.myles.ViaVersion.packets.State;
/*    */ 
/*    */ public class ViaFabricHostnameProtocol extends SimpleProtocol {
/* 11 */   public static final ViaFabricHostnameProtocol INSTANCE = new ViaFabricHostnameProtocol();
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 15 */     registerIncoming(State.HANDSHAKE, 0, 0, new PacketRemapper()
/*    */         {
/*    */           public void registerMap() {
/* 18 */             map((Type)Type.VAR_INT);
/* 19 */             map(Type.STRING, new ValueTransformer<String, String>(Type.STRING)
/*    */                 {
/*    */                   public String transform(PacketWrapper packetWrapper, String s) {
/* 22 */                     return s;
/*    */                   }
/*    */                 });
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\github\creeper123123321\viafabric\protocol\ViaFabricHostnameProtocol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */