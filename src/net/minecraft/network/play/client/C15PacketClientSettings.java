/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ public class C15PacketClientSettings
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private String lang;
/*    */   private int view;
/*    */   private EntityPlayer.EnumChatVisibility chatVisibility;
/*    */   private boolean enableColors;
/*    */   private int modelPartFlags;
/*    */   
/*    */   public C15PacketClientSettings() {}
/*    */   
/*    */   public C15PacketClientSettings(String langIn, int viewIn, EntityPlayer.EnumChatVisibility chatVisibilityIn, boolean enableColorsIn, int modelPartFlagsIn) {
/* 22 */     this.lang = langIn;
/* 23 */     this.view = viewIn;
/* 24 */     this.chatVisibility = chatVisibilityIn;
/* 25 */     this.enableColors = enableColorsIn;
/* 26 */     this.modelPartFlags = modelPartFlagsIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 33 */     this.lang = buf.readStringFromBuffer(7);
/* 34 */     this.view = buf.readByte();
/* 35 */     this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(buf.readByte());
/* 36 */     this.enableColors = buf.readBoolean();
/* 37 */     this.modelPartFlags = buf.readUnsignedByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 44 */     buf.writeString(this.lang);
/* 45 */     buf.writeByte(this.view);
/* 46 */     buf.writeByte(this.chatVisibility.getChatVisibility());
/* 47 */     buf.writeBoolean(this.enableColors);
/* 48 */     buf.writeByte(this.modelPartFlags);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 56 */     handler.processClientSettings(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLang() {
/* 61 */     return this.lang;
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityPlayer.EnumChatVisibility getChatVisibility() {
/* 66 */     return this.chatVisibility;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isColorsEnabled() {
/* 71 */     return this.enableColors;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getModelPartFlags() {
/* 76 */     return this.modelPartFlags;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\client\C15PacketClientSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */