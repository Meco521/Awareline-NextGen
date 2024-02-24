/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.CombatTracker;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class S42PacketCombatEvent
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   public Event eventType;
/*    */   public int field_179774_b;
/*    */   public int field_179775_c;
/*    */   public int field_179772_d;
/*    */   public String deathMessage;
/*    */   
/*    */   public S42PacketCombatEvent() {}
/*    */   
/*    */   public S42PacketCombatEvent(CombatTracker combatTrackerIn, Event combatEventType) {
/* 24 */     this.eventType = combatEventType;
/* 25 */     EntityLivingBase entitylivingbase = combatTrackerIn.func_94550_c();
/*    */     
/* 27 */     switch (combatEventType) {
/*    */       
/*    */       case END_COMBAT:
/* 30 */         this.field_179772_d = combatTrackerIn.func_180134_f();
/* 31 */         this.field_179775_c = (entitylivingbase == null) ? -1 : entitylivingbase.getEntityId();
/*    */         break;
/*    */       
/*    */       case ENTITY_DIED:
/* 35 */         this.field_179774_b = combatTrackerIn.getFighter().getEntityId();
/* 36 */         this.field_179775_c = (entitylivingbase == null) ? -1 : entitylivingbase.getEntityId();
/* 37 */         this.deathMessage = combatTrackerIn.getDeathMessage().getUnformattedText();
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 45 */     this.eventType = (Event)buf.readEnumValue(Event.class);
/*    */     
/* 47 */     if (this.eventType == Event.END_COMBAT) {
/*    */       
/* 49 */       this.field_179772_d = buf.readVarIntFromBuffer();
/* 50 */       this.field_179775_c = buf.readInt();
/*    */     }
/* 52 */     else if (this.eventType == Event.ENTITY_DIED) {
/*    */       
/* 54 */       this.field_179774_b = buf.readVarIntFromBuffer();
/* 55 */       this.field_179775_c = buf.readInt();
/* 56 */       this.deathMessage = buf.readStringFromBuffer(32767);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 64 */     buf.writeEnumValue(this.eventType);
/*    */     
/* 66 */     if (this.eventType == Event.END_COMBAT) {
/*    */       
/* 68 */       buf.writeVarIntToBuffer(this.field_179772_d);
/* 69 */       buf.writeInt(this.field_179775_c);
/*    */     }
/* 71 */     else if (this.eventType == Event.ENTITY_DIED) {
/*    */       
/* 73 */       buf.writeVarIntToBuffer(this.field_179774_b);
/* 74 */       buf.writeInt(this.field_179775_c);
/* 75 */       buf.writeString(this.deathMessage);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 84 */     handler.handleCombatEvent(this);
/*    */   }
/*    */   
/*    */   public enum Event
/*    */   {
/* 89 */     ENTER_COMBAT,
/* 90 */     END_COMBAT,
/* 91 */     ENTITY_DIED;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S42PacketCombatEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */