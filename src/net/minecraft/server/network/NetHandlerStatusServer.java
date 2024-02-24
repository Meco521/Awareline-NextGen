/*    */ package net.minecraft.server.network;
/*    */ 
/*    */ import net.minecraft.network.NetworkManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.status.INetHandlerStatusServer;
/*    */ import net.minecraft.network.status.client.C00PacketServerQuery;
/*    */ import net.minecraft.network.status.client.C01PacketPing;
/*    */ import net.minecraft.network.status.server.S00PacketServerInfo;
/*    */ import net.minecraft.network.status.server.S01PacketPong;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class NetHandlerStatusServer implements INetHandlerStatusServer {
/* 15 */   private static final IChatComponent EXIT_MESSAGE = (IChatComponent)new ChatComponentText("Status request has been handled.");
/*    */   
/*    */   private final MinecraftServer server;
/*    */   private final NetworkManager networkManager;
/*    */   private boolean handled;
/*    */   
/*    */   public NetHandlerStatusServer(MinecraftServer serverIn, NetworkManager netManager) {
/* 22 */     this.server = serverIn;
/* 23 */     this.networkManager = netManager;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDisconnect(IChatComponent reason) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processServerQuery(C00PacketServerQuery packetIn) {
/* 35 */     if (this.handled) {
/*    */       
/* 37 */       this.networkManager.closeChannel(EXIT_MESSAGE);
/*    */     }
/*    */     else {
/*    */       
/* 41 */       this.handled = true;
/* 42 */       this.networkManager.sendPacket((Packet)new S00PacketServerInfo(this.server.getServerStatusResponse()));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void processPing(C01PacketPing packetIn) {
/* 48 */     this.networkManager.sendPacket((Packet)new S01PacketPong(packetIn.getClientTime()));
/* 49 */     this.networkManager.closeChannel(EXIT_MESSAGE);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\server\network\NetHandlerStatusServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */