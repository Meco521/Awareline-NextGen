/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class S0CPacketSpawnPlayer
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private int entityId;
/*     */   private UUID playerId;
/*     */   private int x;
/*     */   private int y;
/*     */   private int z;
/*     */   private byte yaw;
/*     */   private byte pitch;
/*     */   private int currentItem;
/*     */   private DataWatcher watcher;
/*     */   private List<DataWatcher.WatchableObject> field_148958_j;
/*     */   
/*     */   public S0CPacketSpawnPlayer() {}
/*     */   
/*     */   public S0CPacketSpawnPlayer(EntityPlayer player) {
/*  35 */     this.entityId = player.getEntityId();
/*  36 */     this.playerId = player.getGameProfile().getId();
/*  37 */     this.x = MathHelper.floor_double(player.posX * 32.0D);
/*  38 */     this.y = MathHelper.floor_double(player.posY * 32.0D);
/*  39 */     this.z = MathHelper.floor_double(player.posZ * 32.0D);
/*  40 */     this.yaw = (byte)(int)(player.rotationYaw * 256.0F / 360.0F);
/*  41 */     this.pitch = (byte)(int)(player.rotationPitch * 256.0F / 360.0F);
/*  42 */     ItemStack itemstack = player.inventory.getCurrentItem();
/*  43 */     this.currentItem = (itemstack == null) ? 0 : Item.getIdFromItem(itemstack.getItem());
/*  44 */     this.watcher = player.getDataWatcher();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  52 */     this.entityId = buf.readVarIntFromBuffer();
/*  53 */     this.playerId = buf.readUuid();
/*  54 */     this.x = buf.readInt();
/*  55 */     this.y = buf.readInt();
/*  56 */     this.z = buf.readInt();
/*  57 */     this.yaw = buf.readByte();
/*  58 */     this.pitch = buf.readByte();
/*  59 */     this.currentItem = buf.readShort();
/*  60 */     this.field_148958_j = DataWatcher.readWatchedListFromPacketBuffer(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  68 */     buf.writeVarIntToBuffer(this.entityId);
/*  69 */     buf.writeUuid(this.playerId);
/*  70 */     buf.writeInt(this.x);
/*  71 */     buf.writeInt(this.y);
/*  72 */     buf.writeInt(this.z);
/*  73 */     buf.writeByte(this.yaw);
/*  74 */     buf.writeByte(this.pitch);
/*  75 */     buf.writeShort(this.currentItem);
/*  76 */     this.watcher.writeTo(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  84 */     handler.handleSpawnPlayer(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<DataWatcher.WatchableObject> func_148944_c() {
/*  89 */     if (this.field_148958_j == null)
/*     */     {
/*  91 */       this.field_148958_j = this.watcher.getAllWatched();
/*     */     }
/*     */     
/*  94 */     return this.field_148958_j;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEntityID() {
/*  99 */     return this.entityId;
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getPlayer() {
/* 104 */     return this.playerId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getX() {
/* 109 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getY() {
/* 114 */     return this.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getZ() {
/* 119 */     return this.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getYaw() {
/* 124 */     return this.yaw;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getPitch() {
/* 129 */     return this.pitch;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCurrentItemID() {
/* 134 */     return this.currentItem;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S0CPacketSpawnPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */