/*    */ package awareline.main.mod.implement.visual.sucks.info;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.EventAttack;
/*    */ import awareline.main.event.events.world.packetEvents.EventPacketSend;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.server.S45PacketTitle;
/*    */ 
/*    */ 
/*    */ public class CombatManager
/*    */ {
/*    */   private int killCounts;
/*    */   private int second;
/*    */   private int minute;
/*    */   
/*    */   @EventHandler(4)
/*    */   private void onSendPacket(EventAttack e) {
/* 19 */     Entity syncEntity = e.getEntity();
/* 20 */     if (syncEntity != null && syncEntity.isDead)
/* 21 */       this.killCounts++; 
/*    */   }
/*    */   private int hour; private int totalPlayed; private int win; private long startTime;
/*    */   
/*    */   @EventHandler(4)
/*    */   private void onSendPacket(EventPacketSend e) {
/* 27 */     Packet<?> packet = e.getPacket();
/* 28 */     if (packet instanceof net.minecraft.network.handshake.client.C00Handshake) {
/* 29 */       this.startTime = System.currentTimeMillis();
/* 30 */       this.second = 0;
/* 31 */       this.minute = 0;
/* 32 */       this.hour = 0;
/*    */     } 
/*    */     
/* 35 */     if (packet instanceof S45PacketTitle) {
/* 36 */       S45PacketTitle packetTitle = (S45PacketTitle)packet;
/* 37 */       String title = packetTitle.getMessage().getFormattedText();
/* 38 */       if ((title.startsWith("§6§l") && title.endsWith("§r")) || (title.startsWith("§c§lYOU") && title.endsWith("§r")) || (title.startsWith("§c§lGame") && title.endsWith("§r")) || (title.startsWith("§c§lWITH") && title.endsWith("§r")) || (title.startsWith("§c§lYARR") && title.endsWith("§r")))
/* 39 */         this.totalPlayed++; 
/* 40 */       if (title.startsWith("§6§l") && title.endsWith("§r")) this.win++; 
/*    */     } 
/*    */   }
/*    */   
/*    */   public int getKillCounts() {
/* 45 */     return this.killCounts;
/*    */   }
/*    */   
/*    */   public int getSecond() {
/* 49 */     return this.second;
/*    */   }
/*    */   
/*    */   public int getMinute() {
/* 53 */     return this.minute;
/*    */   }
/*    */   
/*    */   public int getHour() {
/* 57 */     return this.hour;
/*    */   }
/*    */   
/*    */   public int getTotalPlayed() {
/* 61 */     return this.totalPlayed;
/*    */   }
/*    */   
/*    */   public int getWin() {
/* 65 */     return this.win;
/*    */   }
/*    */   
/*    */   public void setSecond(int second) {
/* 69 */     this.second = second;
/*    */   }
/*    */   
/*    */   public void setMinute(int minute) {
/* 73 */     this.minute = minute;
/*    */   }
/*    */   
/*    */   public void setHour(int hour) {
/* 77 */     this.hour = hour;
/*    */   }
/*    */   
/*    */   public void setStartTime(long startTime) {
/* 81 */     this.startTime = startTime;
/*    */   }
/*    */   
/*    */   public long getStartTime() {
/* 85 */     return this.startTime;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\sucks\info\CombatManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */