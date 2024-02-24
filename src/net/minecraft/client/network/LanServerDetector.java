/*     */ package net.minecraft.client.network;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.MulticastSocket;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ThreadLanServerPing;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class LanServerDetector
/*     */ {
/*  20 */   static final AtomicInteger field_148551_a = new AtomicInteger(0);
/*  21 */   static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   public static class LanServer
/*     */   {
/*     */     private final String lanServerMotd;
/*     */     private final String lanServerIpPort;
/*     */     private long timeLastSeen;
/*     */     
/*     */     public LanServer(String motd, String address) {
/*  31 */       this.lanServerMotd = motd;
/*  32 */       this.lanServerIpPort = address;
/*  33 */       this.timeLastSeen = Minecraft.getSystemTime();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getServerMotd() {
/*  38 */       return this.lanServerMotd;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getServerIpPort() {
/*  43 */       return this.lanServerIpPort;
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateLastSeen() {
/*  48 */       this.timeLastSeen = Minecraft.getSystemTime();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LanServerList
/*     */   {
/*  54 */     private final List<LanServerDetector.LanServer> listOfLanServers = Lists.newArrayList();
/*     */     
/*     */     boolean wasUpdated;
/*     */     
/*     */     public synchronized boolean getWasUpdated() {
/*  59 */       return this.wasUpdated;
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized void setWasNotUpdated() {
/*  64 */       this.wasUpdated = false;
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized List<LanServerDetector.LanServer> getLanServers() {
/*  69 */       return Collections.unmodifiableList(this.listOfLanServers);
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized void func_77551_a(String p_77551_1_, InetAddress p_77551_2_) {
/*  74 */       String s = ThreadLanServerPing.getMotdFromPingResponse(p_77551_1_);
/*  75 */       String s1 = ThreadLanServerPing.getAdFromPingResponse(p_77551_1_);
/*     */       
/*  77 */       if (s1 != null) {
/*     */         
/*  79 */         s1 = p_77551_2_.getHostAddress() + ":" + s1;
/*  80 */         boolean flag = false;
/*     */         
/*  82 */         for (LanServerDetector.LanServer lanserverdetector$lanserver : this.listOfLanServers) {
/*     */           
/*  84 */           if (lanserverdetector$lanserver.getServerIpPort().equals(s1)) {
/*     */             
/*  86 */             lanserverdetector$lanserver.updateLastSeen();
/*  87 */             flag = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*  92 */         if (!flag) {
/*     */           
/*  94 */           this.listOfLanServers.add(new LanServerDetector.LanServer(s, s1));
/*  95 */           this.wasUpdated = true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ThreadLanServerFind
/*     */     extends Thread
/*     */   {
/*     */     private final LanServerDetector.LanServerList localServerList;
/*     */     private final InetAddress broadcastAddress;
/*     */     private final MulticastSocket socket;
/*     */     
/*     */     public ThreadLanServerFind(LanServerDetector.LanServerList p_i1320_1_) throws IOException {
/* 109 */       super("LanServerDetector #" + LanServerDetector.field_148551_a.incrementAndGet());
/* 110 */       this.localServerList = p_i1320_1_;
/* 111 */       setDaemon(true);
/* 112 */       this.socket = new MulticastSocket(4445);
/* 113 */       this.broadcastAddress = InetAddress.getByName("224.0.2.60");
/* 114 */       this.socket.setSoTimeout(5000);
/* 115 */       this.socket.joinGroup(this.broadcastAddress);
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 120 */       byte[] abyte = new byte[1024];
/*     */       
/* 122 */       while (!isInterrupted()) {
/*     */         
/* 124 */         DatagramPacket datagrampacket = new DatagramPacket(abyte, abyte.length);
/*     */ 
/*     */         
/*     */         try {
/* 128 */           this.socket.receive(datagrampacket);
/*     */         }
/* 130 */         catch (SocketTimeoutException var5) {
/*     */           
/*     */           continue;
/*     */         }
/* 134 */         catch (IOException ioexception) {
/*     */           
/* 136 */           LanServerDetector.logger.error("Couldn't ping server", ioexception);
/*     */           
/*     */           break;
/*     */         } 
/* 140 */         String s = new String(datagrampacket.getData(), datagrampacket.getOffset(), datagrampacket.getLength());
/* 141 */         LanServerDetector.logger.debug(datagrampacket.getAddress() + ": " + s);
/* 142 */         this.localServerList.func_77551_a(s, datagrampacket.getAddress());
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 147 */         this.socket.leaveGroup(this.broadcastAddress);
/*     */       }
/* 149 */       catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 154 */       this.socket.close();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\network\LanServerDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */