/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.Vec4b;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class S34PacketMaps
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private int mapId;
/*     */   private byte mapScale;
/*     */   private Vec4b[] mapVisiblePlayersVec4b;
/*     */   private int mapMinX;
/*     */   private int mapMinY;
/*     */   private int mapMaxX;
/*     */   private int mapMaxY;
/*     */   private byte[] mapDataBytes;
/*     */   
/*     */   public S34PacketMaps() {}
/*     */   
/*     */   public S34PacketMaps(int mapIdIn, byte scale, Collection<Vec4b> visiblePlayers, byte[] colors, int minX, int minY, int maxX, int maxY) {
/*  28 */     this.mapId = mapIdIn;
/*  29 */     this.mapScale = scale;
/*  30 */     this.mapVisiblePlayersVec4b = visiblePlayers.<Vec4b>toArray(new Vec4b[0]);
/*  31 */     this.mapMinX = minX;
/*  32 */     this.mapMinY = minY;
/*  33 */     this.mapMaxX = maxX;
/*  34 */     this.mapMaxY = maxY;
/*  35 */     this.mapDataBytes = new byte[maxX * maxY];
/*     */     
/*  37 */     for (int i = 0; i < maxX; i++) {
/*     */       
/*  39 */       for (int j = 0; j < maxY; j++)
/*     */       {
/*  41 */         this.mapDataBytes[i + j * maxX] = colors[minX + i + (minY + j << 7)];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) {
/*  50 */     this.mapId = buf.readVarIntFromBuffer();
/*  51 */     this.mapScale = buf.readByte();
/*  52 */     this.mapVisiblePlayersVec4b = new Vec4b[buf.readVarIntFromBuffer()];
/*     */     
/*  54 */     for (int i = 0; i < this.mapVisiblePlayersVec4b.length; i++) {
/*     */       
/*  56 */       short short1 = (short)buf.readByte();
/*  57 */       this.mapVisiblePlayersVec4b[i] = new Vec4b((byte)(short1 >> 4 & 0xF), buf.readByte(), buf.readByte(), (byte)(short1 & 0xF));
/*     */     } 
/*     */     
/*  60 */     this.mapMaxX = buf.readUnsignedByte();
/*     */     
/*  62 */     if (this.mapMaxX > 0) {
/*     */       
/*  64 */       this.mapMaxY = buf.readUnsignedByte();
/*  65 */       this.mapMinX = buf.readUnsignedByte();
/*  66 */       this.mapMinY = buf.readUnsignedByte();
/*  67 */       this.mapDataBytes = buf.readByteArray();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) {
/*  75 */     buf.writeVarIntToBuffer(this.mapId);
/*  76 */     buf.writeByte(this.mapScale);
/*  77 */     buf.writeVarIntToBuffer(this.mapVisiblePlayersVec4b.length);
/*     */     
/*  79 */     for (Vec4b vec4b : this.mapVisiblePlayersVec4b) {
/*     */       
/*  81 */       buf.writeByte((vec4b.func_176110_a() & 0xF) << 4 | vec4b.func_176111_d() & 0xF);
/*  82 */       buf.writeByte(vec4b.func_176112_b());
/*  83 */       buf.writeByte(vec4b.func_176113_c());
/*     */     } 
/*     */     
/*  86 */     buf.writeByte(this.mapMaxX);
/*     */     
/*  88 */     if (this.mapMaxX > 0) {
/*     */       
/*  90 */       buf.writeByte(this.mapMaxY);
/*  91 */       buf.writeByte(this.mapMinX);
/*  92 */       buf.writeByte(this.mapMinY);
/*  93 */       buf.writeByteArray(this.mapDataBytes);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 102 */     handler.handleMaps(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMapId() {
/* 107 */     return this.mapId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMapdataTo(MapData mapdataIn) {
/* 115 */     mapdataIn.scale = this.mapScale;
/* 116 */     mapdataIn.mapDecorations.clear();
/*     */     
/* 118 */     for (int i = 0; i < this.mapVisiblePlayersVec4b.length; i++) {
/*     */       
/* 120 */       Vec4b vec4b = this.mapVisiblePlayersVec4b[i];
/* 121 */       mapdataIn.mapDecorations.put("icon-" + i, vec4b);
/*     */     } 
/*     */     
/* 124 */     for (int j = 0; j < this.mapMaxX; j++) {
/*     */       
/* 126 */       for (int k = 0; k < this.mapMaxY; k++)
/*     */       {
/* 128 */         mapdataIn.colors[this.mapMinX + j + (this.mapMinY + k << 7)] = this.mapDataBytes[j + k * this.mapMaxX];
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S34PacketMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */