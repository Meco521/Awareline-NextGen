/*     */ package net.minecraft.network.play.client;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ public class C08PacketPlayerBlockPlacement
/*     */   implements Packet<INetHandlerPlayServer> {
/*  13 */   private static final BlockPos field_179726_a = new BlockPos(-1, -1, -1);
/*     */   
/*     */   private BlockPos position;
/*     */   
/*     */   private int placedBlockDirection;
/*     */   
/*     */   private ItemStack stack;
/*     */   private float facingX;
/*     */   private float facingY;
/*     */   private float facingZ;
/*     */   
/*     */   public C08PacketPlayerBlockPlacement() {}
/*     */   
/*     */   public C08PacketPlayerBlockPlacement(ItemStack stackIn) {
/*  27 */     this(field_179726_a, 255, stackIn, 0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public C08PacketPlayerBlockPlacement(BlockPos positionIn, int placedBlockDirectionIn, ItemStack stackIn, float facingXIn, float facingYIn, float facingZIn) {
/*  32 */     this.position = positionIn;
/*  33 */     this.placedBlockDirection = placedBlockDirectionIn;
/*  34 */     this.stack = (stackIn != null) ? stackIn.copy() : null;
/*  35 */     this.facingX = facingXIn;
/*  36 */     this.facingY = facingYIn;
/*  37 */     this.facingZ = facingZIn;
/*     */   }
/*     */   
/*     */   public C08PacketPlayerBlockPlacement(ItemStack stackIn, BlockPos pos) {
/*  41 */     this(pos, 255, stackIn, 0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  49 */     this.position = buf.readBlockPos();
/*  50 */     this.placedBlockDirection = buf.readUnsignedByte();
/*  51 */     this.stack = buf.readItemStackFromBuffer();
/*  52 */     this.facingX = buf.readUnsignedByte() / 16.0F;
/*  53 */     this.facingY = buf.readUnsignedByte() / 16.0F;
/*  54 */     this.facingZ = buf.readUnsignedByte() / 16.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) {
/*  61 */     buf.writeBlockPos(this.position);
/*  62 */     buf.writeByte(this.placedBlockDirection);
/*  63 */     buf.writeItemStackToBuffer(this.stack);
/*  64 */     buf.writeByte((int)(this.facingX * 16.0F));
/*  65 */     buf.writeByte((int)(this.facingY * 16.0F));
/*  66 */     buf.writeByte((int)(this.facingZ * 16.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayServer handler) {
/*  74 */     handler.processPlayerBlockPlacement(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getPosition() {
/*  79 */     return this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPlacedBlockDirection() {
/*  84 */     return this.placedBlockDirection;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getStack() {
/*  89 */     return this.stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPlacedBlockOffsetX() {
/*  97 */     return this.facingX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPlacedBlockOffsetY() {
/* 105 */     return this.facingY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPlacedBlockOffsetZ() {
/* 113 */     return this.facingZ;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\client\C08PacketPlayerBlockPlacement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */