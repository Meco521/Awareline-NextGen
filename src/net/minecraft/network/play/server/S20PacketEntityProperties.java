/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ 
/*     */ public class S20PacketEntityProperties
/*     */   implements Packet<INetHandlerPlayClient> {
/*     */   private int entityId;
/*  17 */   private final List<Snapshot> field_149444_b = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public S20PacketEntityProperties(int entityIdIn, Collection<IAttributeInstance> p_i45236_2_) {
/*  25 */     this.entityId = entityIdIn;
/*     */     
/*  27 */     for (IAttributeInstance iattributeinstance : p_i45236_2_)
/*     */     {
/*  29 */       this.field_149444_b.add(new Snapshot(iattributeinstance.getAttribute().getAttributeUnlocalizedName(), iattributeinstance.getBaseValue(), iattributeinstance.func_111122_c()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) {
/*  37 */     this.entityId = buf.readVarIntFromBuffer();
/*  38 */     int i = buf.readInt();
/*     */     
/*  40 */     for (int j = 0; j < i; j++) {
/*     */       
/*  42 */       String s = buf.readStringFromBuffer(64);
/*  43 */       double d0 = buf.readDouble();
/*  44 */       List<AttributeModifier> list = Lists.newArrayList();
/*  45 */       int k = buf.readVarIntFromBuffer();
/*     */       
/*  47 */       for (int l = 0; l < k; l++) {
/*     */         
/*  49 */         UUID uuid = buf.readUuid();
/*  50 */         list.add(new AttributeModifier(uuid, "Unknown synced attribute modifier", buf.readDouble(), buf.readByte()));
/*     */       } 
/*     */       
/*  53 */       this.field_149444_b.add(new Snapshot(s, d0, list));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) {
/*  61 */     buf.writeVarIntToBuffer(this.entityId);
/*  62 */     buf.writeInt(this.field_149444_b.size());
/*     */     
/*  64 */     for (Snapshot s20packetentityproperties$snapshot : this.field_149444_b) {
/*     */       
/*  66 */       buf.writeString(s20packetentityproperties$snapshot.func_151409_a());
/*  67 */       buf.writeDouble(s20packetentityproperties$snapshot.func_151410_b());
/*  68 */       buf.writeVarIntToBuffer(s20packetentityproperties$snapshot.func_151408_c().size());
/*     */       
/*  70 */       for (AttributeModifier attributemodifier : s20packetentityproperties$snapshot.func_151408_c()) {
/*     */         
/*  72 */         buf.writeUuid(attributemodifier.getID());
/*  73 */         buf.writeDouble(attributemodifier.getAmount());
/*  74 */         buf.writeByte(attributemodifier.getOperation());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  84 */     handler.handleEntityProperties(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEntityId() {
/*  89 */     return this.entityId;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Snapshot> func_149441_d() {
/*  94 */     return this.field_149444_b;
/*     */   }
/*     */   
/*     */   public S20PacketEntityProperties() {}
/*     */   
/*     */   public static class Snapshot {
/*     */     private final String field_151412_b;
/*     */     private final double field_151413_c;
/*     */     private final Collection<AttributeModifier> field_151411_d;
/*     */     
/*     */     public Snapshot(String p_i45235_2_, double p_i45235_3_, Collection<AttributeModifier> p_i45235_5_) {
/* 105 */       this.field_151412_b = p_i45235_2_;
/* 106 */       this.field_151413_c = p_i45235_3_;
/* 107 */       this.field_151411_d = p_i45235_5_;
/*     */     }
/*     */ 
/*     */     
/*     */     public String func_151409_a() {
/* 112 */       return this.field_151412_b;
/*     */     }
/*     */ 
/*     */     
/*     */     public double func_151410_b() {
/* 117 */       return this.field_151413_c;
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<AttributeModifier> func_151408_c() {
/* 122 */       return this.field_151411_d;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S20PacketEntityProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */