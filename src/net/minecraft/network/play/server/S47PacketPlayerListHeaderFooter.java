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
/*    */ public class S47PacketPlayerListHeaderFooter
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private IChatComponent header;
/*    */   private IChatComponent footer;
/*    */   
/*    */   public S47PacketPlayerListHeaderFooter() {}
/*    */   
/*    */   public S47PacketPlayerListHeaderFooter(IChatComponent headerIn) {
/* 21 */     this.header = headerIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 29 */     this.header = buf.readChatComponent();
/* 30 */     this.footer = buf.readChatComponent();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 38 */     buf.writeChatComponent(this.header);
/* 39 */     buf.writeChatComponent(this.footer);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 47 */     handler.handlePlayerListHeaderFooter(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public IChatComponent getHeader() {
/* 52 */     return this.header;
/*    */   }
/*    */ 
/*    */   
/*    */   public IChatComponent getFooter() {
/* 57 */     return this.footer;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S47PacketPlayerListHeaderFooter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */