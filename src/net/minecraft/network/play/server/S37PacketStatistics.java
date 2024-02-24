/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.stats.StatBase;
/*    */ import net.minecraft.stats.StatList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class S37PacketStatistics
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private Map<StatBase, Integer> field_148976_a;
/*    */   
/*    */   public S37PacketStatistics() {}
/*    */   
/*    */   public S37PacketStatistics(Map<StatBase, Integer> p_i45173_1_) {
/* 23 */     this.field_148976_a = p_i45173_1_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 31 */     handler.handleStatistics(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 38 */     int i = buf.readVarIntFromBuffer();
/* 39 */     this.field_148976_a = Maps.newHashMap();
/*    */     
/* 41 */     for (int j = 0; j < i; j++) {
/*    */       
/* 43 */       StatBase statbase = StatList.getOneShotStat(buf.readStringFromBuffer(32767));
/* 44 */       int k = buf.readVarIntFromBuffer();
/*    */       
/* 46 */       if (statbase != null)
/*    */       {
/* 48 */         this.field_148976_a.put(statbase, Integer.valueOf(k));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 57 */     buf.writeVarIntToBuffer(this.field_148976_a.size());
/*    */     
/* 59 */     for (Map.Entry<StatBase, Integer> entry : this.field_148976_a.entrySet()) {
/*    */       
/* 61 */       buf.writeString(((StatBase)entry.getKey()).statId);
/* 62 */       buf.writeVarIntToBuffer(((Integer)entry.getValue()).intValue());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<StatBase, Integer> func_148974_c() {
/* 68 */     return this.field_148976_a;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S37PacketStatistics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */