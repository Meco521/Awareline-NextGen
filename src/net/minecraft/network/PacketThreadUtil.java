/*    */ package net.minecraft.network;
/*    */ 
/*    */ import net.minecraft.network.play.server.S01PacketJoinGame;
/*    */ import net.minecraft.network.play.server.S07PacketRespawn;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.IThreadListener;
/*    */ 
/*    */ 
/*    */ public class PacketThreadUtil
/*    */ {
/* 11 */   public static int lastDimensionId = Integer.MIN_VALUE;
/*    */ 
/*    */   
/*    */   public static <T extends INetHandler> void checkThreadAndEnqueue(final Packet<T> p_180031_0_, final T p_180031_1_, IThreadListener p_180031_2_) throws ThreadQuickExitException {
/* 15 */     if (!p_180031_2_.isCallingFromMinecraftThread()) {
/*    */       
/* 17 */       p_180031_2_.addScheduledTask(new Runnable()
/*    */           {
/*    */             public void run()
/*    */             {
/* 21 */               PacketThreadUtil.clientPreProcessPacket(p_180031_0_);
/* 22 */               p_180031_0_.processPacket(p_180031_1_);
/*    */             }
/*    */           });
/* 25 */       throw ThreadQuickExitException.INSTANCE;
/*    */     } 
/*    */ 
/*    */     
/* 29 */     clientPreProcessPacket(p_180031_0_);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected static void clientPreProcessPacket(Packet p_clientPreProcessPacket_0_) {
/* 35 */     if (p_clientPreProcessPacket_0_ instanceof net.minecraft.network.play.server.S08PacketPlayerPosLook)
/*    */     {
/* 37 */       Config.getRenderGlobal().onPlayerPositionSet();
/*    */     }
/*    */     
/* 40 */     if (p_clientPreProcessPacket_0_ instanceof S07PacketRespawn) {
/*    */       
/* 42 */       S07PacketRespawn s07packetrespawn = (S07PacketRespawn)p_clientPreProcessPacket_0_;
/* 43 */       lastDimensionId = s07packetrespawn.getDimensionID();
/*    */     }
/* 45 */     else if (p_clientPreProcessPacket_0_ instanceof S01PacketJoinGame) {
/*    */       
/* 47 */       S01PacketJoinGame s01packetjoingame = (S01PacketJoinGame)p_clientPreProcessPacket_0_;
/* 48 */       lastDimensionId = s01packetjoingame.getDimension();
/*    */     }
/*    */     else {
/*    */       
/* 52 */       lastDimensionId = Integer.MIN_VALUE;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\PacketThreadUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */