/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class S02PacketChat
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private IChatComponent chatComponent;
/*    */   private byte type;
/*    */   
/*    */   public S02PacketChat() {}
/*    */   
/*    */   public S02PacketChat(IChatComponent component) {
/* 21 */     this(component, (byte)1);
/*    */   }
/*    */ 
/*    */   
/*    */   public S02PacketChat(IChatComponent message, byte typeIn) {
/* 26 */     this.chatComponent = message;
/* 27 */     this.type = typeIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 35 */     this.chatComponent = buf.readChatComponent();
/* 36 */     this.type = buf.readByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 44 */     buf.writeChatComponent(this.chatComponent);
/* 45 */     buf.writeByte(this.type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 53 */     handler.handleChat(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public IChatComponent getChatComponent() {
/* 58 */     return this.chatComponent;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isChat() {
/* 63 */     return (this.type == 1 || this.type == 2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getType() {
/* 72 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S02PacketChat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */