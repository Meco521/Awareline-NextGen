/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class S21PacketChunkData
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private int chunkX;
/*     */   private int chunkZ;
/*     */   private Extracted extractedData;
/*     */   private boolean field_149279_g;
/*     */   
/*     */   public S21PacketChunkData() {}
/*     */   
/*     */   public S21PacketChunkData(Chunk chunkIn, boolean p_i45196_2_, int p_i45196_3_) {
/*  25 */     this.chunkX = chunkIn.xPosition;
/*  26 */     this.chunkZ = chunkIn.zPosition;
/*  27 */     this.field_149279_g = p_i45196_2_;
/*  28 */     this.extractedData = getExtractedData(chunkIn, p_i45196_2_, !(chunkIn.getWorld()).provider.getHasNoSky(), p_i45196_3_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) {
/*  35 */     this.chunkX = buf.readInt();
/*  36 */     this.chunkZ = buf.readInt();
/*  37 */     this.field_149279_g = buf.readBoolean();
/*  38 */     this.extractedData = new Extracted();
/*  39 */     this.extractedData.dataSize = buf.readShort();
/*  40 */     this.extractedData.data = buf.readByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) {
/*  47 */     buf.writeInt(this.chunkX);
/*  48 */     buf.writeInt(this.chunkZ);
/*  49 */     buf.writeBoolean(this.field_149279_g);
/*  50 */     buf.writeShort((short)(this.extractedData.dataSize & 0xFFFF));
/*  51 */     buf.writeByteArray(this.extractedData.data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  59 */     handler.handleChunkData(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getExtractedDataBytes() {
/*  64 */     return this.extractedData.data;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static int func_180737_a(int p_180737_0_, boolean p_180737_1_, boolean p_180737_2_) {
/*  69 */     int i = p_180737_0_ * 2 * 16 * 16 * 16;
/*  70 */     int j = p_180737_0_ * 16 * 16 * 16 / 2;
/*  71 */     int k = p_180737_1_ ? (p_180737_0_ * 16 * 16 * 16 / 2) : 0;
/*  72 */     int l = p_180737_2_ ? 256 : 0;
/*  73 */     return i + j + k + l;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Extracted getExtractedData(Chunk p_179756_0_, boolean p_179756_1_, boolean p_179756_2_, int p_179756_3_) {
/*  78 */     ExtendedBlockStorage[] aextendedblockstorage = p_179756_0_.getBlockStorageArray();
/*  79 */     Extracted s21packetchunkdata$extracted = new Extracted();
/*  80 */     List<ExtendedBlockStorage> list = Lists.newArrayList();
/*     */     
/*  82 */     for (int i = 0; i < aextendedblockstorage.length; i++) {
/*     */       
/*  84 */       ExtendedBlockStorage extendedblockstorage = aextendedblockstorage[i];
/*     */       
/*  86 */       if (extendedblockstorage != null && (!p_179756_1_ || !extendedblockstorage.isEmpty()) && (p_179756_3_ & 1 << i) != 0) {
/*     */         
/*  88 */         s21packetchunkdata$extracted.dataSize |= 1 << i;
/*  89 */         list.add(extendedblockstorage);
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     s21packetchunkdata$extracted.data = new byte[func_180737_a(Integer.bitCount(s21packetchunkdata$extracted.dataSize), p_179756_2_, p_179756_1_)];
/*  94 */     int j = 0;
/*     */     
/*  96 */     for (ExtendedBlockStorage extendedblockstorage1 : list) {
/*     */       
/*  98 */       char[] achar = extendedblockstorage1.getData();
/*     */       
/* 100 */       for (char c0 : achar) {
/*     */         
/* 102 */         s21packetchunkdata$extracted.data[j++] = (byte)(c0 & 0xFF);
/* 103 */         s21packetchunkdata$extracted.data[j++] = (byte)(c0 >> 8 & 0xFF);
/*     */       } 
/*     */     } 
/*     */     
/* 107 */     for (ExtendedBlockStorage extendedblockstorage2 : list)
/*     */     {
/* 109 */       j = func_179757_a(extendedblockstorage2.getBlocklightArray().getData(), s21packetchunkdata$extracted.data, j);
/*     */     }
/*     */     
/* 112 */     if (p_179756_2_)
/*     */     {
/* 114 */       for (ExtendedBlockStorage extendedblockstorage3 : list)
/*     */       {
/* 116 */         j = func_179757_a(extendedblockstorage3.getSkylightArray().getData(), s21packetchunkdata$extracted.data, j);
/*     */       }
/*     */     }
/*     */     
/* 120 */     if (p_179756_1_)
/*     */     {
/* 122 */       func_179757_a(p_179756_0_.getBiomeArray(), s21packetchunkdata$extracted.data, j);
/*     */     }
/*     */     
/* 125 */     return s21packetchunkdata$extracted;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int func_179757_a(byte[] p_179757_0_, byte[] p_179757_1_, int p_179757_2_) {
/* 130 */     System.arraycopy(p_179757_0_, 0, p_179757_1_, p_179757_2_, p_179757_0_.length);
/* 131 */     return p_179757_2_ + p_179757_0_.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getChunkX() {
/* 136 */     return this.chunkX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getChunkZ() {
/* 141 */     return this.chunkZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getExtractedSize() {
/* 146 */     return this.extractedData.dataSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149274_i() {
/* 151 */     return this.field_149279_g;
/*     */   }
/*     */   
/*     */   public static class Extracted {
/*     */     public byte[] data;
/*     */     public int dataSize;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S21PacketChunkData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */