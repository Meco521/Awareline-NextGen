/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class S30PacketWindowItems
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int windowId;
/*    */   private ItemStack[] itemStacks;
/*    */   
/*    */   public S30PacketWindowItems() {}
/*    */   
/*    */   public S30PacketWindowItems(int windowIdIn, List<ItemStack> p_i45186_2_) {
/* 22 */     this.windowId = windowIdIn;
/* 23 */     this.itemStacks = new ItemStack[p_i45186_2_.size()];
/*    */     
/* 25 */     for (int i = 0; i < this.itemStacks.length; i++) {
/*    */       
/* 27 */       ItemStack itemstack = p_i45186_2_.get(i);
/* 28 */       this.itemStacks[i] = (itemstack == null) ? null : itemstack.copy();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 37 */     this.windowId = buf.readUnsignedByte();
/* 38 */     int i = buf.readShort();
/* 39 */     this.itemStacks = new ItemStack[i];
/*    */     
/* 41 */     for (int j = 0; j < i; j++)
/*    */     {
/* 43 */       this.itemStacks[j] = buf.readItemStackFromBuffer();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 51 */     buf.writeByte(this.windowId);
/* 52 */     buf.writeShort(this.itemStacks.length);
/*    */     
/* 54 */     for (ItemStack itemstack : this.itemStacks)
/*    */     {
/* 56 */       buf.writeItemStackToBuffer(itemstack);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 65 */     handler.handleWindowItems(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_148911_c() {
/* 70 */     return this.windowId;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack[] getItemStacks() {
/* 75 */     return this.itemStacks;
/*    */   }
/*    */   
/*    */   public int getWindowID() {
/* 79 */     return this.windowId;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S30PacketWindowItems.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */