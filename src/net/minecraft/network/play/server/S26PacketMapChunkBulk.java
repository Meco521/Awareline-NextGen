/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class S26PacketMapChunkBulk
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private int[] xPositions;
/*     */   private int[] zPositions;
/*     */   private S21PacketChunkData.Extracted[] chunksData;
/*     */   private boolean isOverworld;
/*     */   
/*     */   public S26PacketMapChunkBulk() {}
/*     */   
/*     */   public S26PacketMapChunkBulk(List<Chunk> chunks) {
/*  23 */     int i = chunks.size();
/*  24 */     this.xPositions = new int[i];
/*  25 */     this.zPositions = new int[i];
/*  26 */     this.chunksData = new S21PacketChunkData.Extracted[i];
/*  27 */     this.isOverworld = !(((Chunk)chunks.get(0)).getWorld()).provider.getHasNoSky();
/*     */     
/*  29 */     for (int j = 0; j < i; j++) {
/*     */       
/*  31 */       Chunk chunk = chunks.get(j);
/*  32 */       S21PacketChunkData.Extracted s21packetchunkdata$extracted = S21PacketChunkData.getExtractedData(chunk, true, this.isOverworld, 65535);
/*  33 */       this.xPositions[j] = chunk.xPosition;
/*  34 */       this.zPositions[j] = chunk.zPosition;
/*  35 */       this.chunksData[j] = s21packetchunkdata$extracted;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) {
/*  43 */     this.isOverworld = buf.readBoolean();
/*  44 */     int i = buf.readVarIntFromBuffer();
/*  45 */     this.xPositions = new int[i];
/*  46 */     this.zPositions = new int[i];
/*  47 */     this.chunksData = new S21PacketChunkData.Extracted[i];
/*     */     
/*  49 */     for (int j = 0; j < i; j++) {
/*     */       
/*  51 */       this.xPositions[j] = buf.readInt();
/*  52 */       this.zPositions[j] = buf.readInt();
/*  53 */       this.chunksData[j] = new S21PacketChunkData.Extracted();
/*  54 */       (this.chunksData[j]).dataSize = buf.readShort() & 0xFFFF;
/*  55 */       (this.chunksData[j]).data = new byte[S21PacketChunkData.func_180737_a(Integer.bitCount((this.chunksData[j]).dataSize), this.isOverworld, true)];
/*     */     } 
/*     */     
/*  58 */     for (int k = 0; k < i; k++)
/*     */     {
/*  60 */       buf.readBytes((this.chunksData[k]).data);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) {
/*  68 */     buf.writeBoolean(this.isOverworld);
/*  69 */     buf.writeVarIntToBuffer(this.chunksData.length);
/*     */     
/*  71 */     for (int i = 0; i < this.xPositions.length; i++) {
/*     */       
/*  73 */       buf.writeInt(this.xPositions[i]);
/*  74 */       buf.writeInt(this.zPositions[i]);
/*  75 */       buf.writeShort((short)((this.chunksData[i]).dataSize & 0xFFFF));
/*     */     } 
/*     */     
/*  78 */     for (int j = 0; j < this.xPositions.length; j++)
/*     */     {
/*  80 */       buf.writeBytes((this.chunksData[j]).data);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  89 */     handler.handleMapChunkBulk(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getChunkX(int p_149255_1_) {
/*  94 */     return this.xPositions[p_149255_1_];
/*     */   }
/*     */ 
/*     */   
/*     */   public int getChunkZ(int p_149253_1_) {
/*  99 */     return this.zPositions[p_149253_1_];
/*     */   }
/*     */ 
/*     */   
/*     */   public int getChunkCount() {
/* 104 */     return this.xPositions.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getChunkBytes(int p_149256_1_) {
/* 109 */     return (this.chunksData[p_149256_1_]).data;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getChunkSize(int p_179754_1_) {
/* 114 */     return (this.chunksData[p_179754_1_]).dataSize;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S26PacketMapChunkBulk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */