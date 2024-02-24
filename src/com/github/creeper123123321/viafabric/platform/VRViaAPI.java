/*    */ package com.github.creeper123123321.viafabric.platform;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Collection;
/*    */ import java.util.SortedSet;
/*    */ import java.util.TreeSet;
/*    */ import java.util.UUID;
/*    */ import us.myles.ViaVersion.api.Via;
/*    */ import us.myles.ViaVersion.api.ViaAPI;
/*    */ import us.myles.ViaVersion.api.boss.BossBar;
/*    */ import us.myles.ViaVersion.api.boss.BossColor;
/*    */ import us.myles.ViaVersion.api.boss.BossStyle;
/*    */ import us.myles.ViaVersion.api.data.UserConnection;
/*    */ import us.myles.ViaVersion.api.protocol.ProtocolRegistry;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VRViaAPI
/*    */   implements ViaAPI<UUID>
/*    */ {
/*    */   public int getPlayerVersion(UUID uuid) {
/* 44 */     UserConnection con = Via.getManager().getConnection(uuid);
/* 45 */     if (con != null) {
/* 46 */       return con.getProtocolInfo().getProtocolVersion();
/*    */     }
/*    */     try {
/* 49 */       return Via.getManager().getInjector().getServerProtocolVersion();
/* 50 */     } catch (Exception e) {
/* 51 */       throw new AssertionError(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInjected(UUID uuid) {
/* 57 */     return Via.getManager().isClientConnected(uuid);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getVersion() {
/* 62 */     return Via.getPlatform().getPluginVersion();
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendRawPacket(UUID uuid, ByteBuf byteBuf) throws IllegalArgumentException {
/* 67 */     UserConnection ci = Via.getManager().getConnection(uuid);
/* 68 */     ci.sendRawPacket(byteBuf);
/*    */   }
/*    */ 
/*    */   
/*    */   public BossBar<Void> createBossBar(String s, BossColor bossColor, BossStyle bossStyle) {
/* 73 */     return (BossBar<Void>)new VRBossBar(s, 1.0F, bossColor, bossStyle);
/*    */   }
/*    */ 
/*    */   
/*    */   public BossBar<Void> createBossBar(String s, float v, BossColor bossColor, BossStyle bossStyle) {
/* 78 */     return (BossBar<Void>)new VRBossBar(s, v, bossColor, bossStyle);
/*    */   }
/*    */ 
/*    */   
/*    */   public SortedSet<Integer> getSupportedVersions() {
/* 83 */     SortedSet<Integer> outputSet = new TreeSet<>(ProtocolRegistry.getSupportedVersions());
/* 84 */     outputSet.removeAll((Collection<?>)Via.getPlatform().getConf().getBlockedProtocols());
/*    */     
/* 86 */     return outputSet;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\github\creeper123123321\viafabric\platform\VRViaAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */