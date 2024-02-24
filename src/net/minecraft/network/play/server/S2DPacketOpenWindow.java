/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class S2DPacketOpenWindow
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private int windowId;
/*     */   private String inventoryType;
/*     */   private IChatComponent windowTitle;
/*     */   private int slotCount;
/*     */   private int entityId;
/*     */   
/*     */   public S2DPacketOpenWindow() {}
/*     */   
/*     */   public S2DPacketOpenWindow(int incomingWindowId, String incomingWindowTitle, IChatComponent windowTitleIn) {
/*  24 */     this(incomingWindowId, incomingWindowTitle, windowTitleIn, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public S2DPacketOpenWindow(int windowIdIn, String guiId, IChatComponent windowTitleIn, int slotCountIn) {
/*  29 */     this.windowId = windowIdIn;
/*  30 */     this.inventoryType = guiId;
/*  31 */     this.windowTitle = windowTitleIn;
/*  32 */     this.slotCount = slotCountIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public S2DPacketOpenWindow(int windowIdIn, String guiId, IChatComponent windowTitleIn, int slotCountIn, int incomingEntityId) {
/*  37 */     this(windowIdIn, guiId, windowTitleIn, slotCountIn);
/*  38 */     this.entityId = incomingEntityId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  46 */     handler.handleOpenWindow(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  54 */     this.windowId = buf.readUnsignedByte();
/*  55 */     this.inventoryType = buf.readStringFromBuffer(32);
/*  56 */     this.windowTitle = buf.readChatComponent();
/*  57 */     this.slotCount = buf.readUnsignedByte();
/*     */     
/*  59 */     if (this.inventoryType.equals("EntityHorse"))
/*     */     {
/*  61 */       this.entityId = buf.readInt();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  70 */     buf.writeByte(this.windowId);
/*  71 */     buf.writeString(this.inventoryType);
/*  72 */     buf.writeChatComponent(this.windowTitle);
/*  73 */     buf.writeByte(this.slotCount);
/*     */     
/*  75 */     if (this.inventoryType.equals("EntityHorse"))
/*     */     {
/*  77 */       buf.writeInt(this.entityId);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWindowId() {
/*  83 */     return this.windowId;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiId() {
/*  88 */     return this.inventoryType;
/*     */   }
/*     */ 
/*     */   
/*     */   public IChatComponent getWindowTitle() {
/*  93 */     return this.windowTitle;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSlotCount() {
/*  98 */     return this.slotCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEntityId() {
/* 103 */     return this.entityId;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasSlots() {
/* 108 */     return (this.slotCount > 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S2DPacketOpenWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */