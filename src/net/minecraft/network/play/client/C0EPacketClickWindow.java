/*     */ package net.minecraft.network.play.client;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class C0EPacketClickWindow
/*     */   implements Packet<INetHandlerPlayServer>
/*     */ {
/*     */   private int windowId;
/*     */   private int slotId;
/*     */   private int usedButton;
/*     */   private short actionNumber;
/*     */   private ItemStack clickedItem;
/*     */   private int mode;
/*     */   
/*     */   public C0EPacketClickWindow() {}
/*     */   
/*     */   public C0EPacketClickWindow(int windowId, int slotId, int usedButton, int mode, ItemStack clickedItem, short actionNumber) {
/*  36 */     this.windowId = windowId;
/*  37 */     this.slotId = slotId;
/*  38 */     this.usedButton = usedButton;
/*  39 */     this.clickedItem = (clickedItem != null) ? clickedItem.copy() : null;
/*  40 */     this.actionNumber = actionNumber;
/*  41 */     this.mode = mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayServer handler) {
/*  49 */     handler.processClickWindow(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  57 */     this.windowId = buf.readByte();
/*  58 */     this.slotId = buf.readShort();
/*  59 */     this.usedButton = buf.readByte();
/*  60 */     this.actionNumber = buf.readShort();
/*  61 */     this.mode = buf.readByte();
/*  62 */     this.clickedItem = buf.readItemStackFromBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) {
/*  69 */     buf.writeByte(this.windowId);
/*  70 */     buf.writeShort(this.slotId);
/*  71 */     buf.writeByte(this.usedButton);
/*  72 */     buf.writeShort(this.actionNumber);
/*  73 */     buf.writeByte(this.mode);
/*  74 */     buf.writeItemStackToBuffer(this.clickedItem);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWindowId() {
/*  79 */     return this.windowId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSlotId() {
/*  84 */     return this.slotId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUsedButton() {
/*  89 */     return this.usedButton;
/*     */   }
/*     */ 
/*     */   
/*     */   public short getActionNumber() {
/*  94 */     return this.actionNumber;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getClickedItem() {
/*  99 */     return this.clickedItem;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMode() {
/* 104 */     return this.mode;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\client\C0EPacketClickWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */