/*     */ package net.minecraft.client.network;
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import io.netty.bootstrap.Bootstrap;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.SimpleChannelInboundHandler;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ServerAddress;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.ServerStatusResponse;
/*     */ import net.minecraft.network.status.INetHandlerStatusClient;
/*     */ import net.minecraft.network.status.client.C01PacketPing;
/*     */ import net.minecraft.network.status.server.S00PacketServerInfo;
/*     */ import net.minecraft.network.status.server.S01PacketPong;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ 
/*     */ public class OldServerPinger {
/*  41 */   static final Splitter PING_RESPONSE_SPLITTER = Splitter.on(false).limit(6);
/*  42 */   static final Logger logger = LogManager.getLogger();
/*  43 */   private final List<NetworkManager> pingDestinations = Collections.synchronizedList(Lists.newArrayList());
/*     */ 
/*     */   
/*     */   public void ping(final ServerData server) throws UnknownHostException {
/*  47 */     ServerAddress serveraddress = ServerAddress.fromString(server.serverIP);
/*  48 */     final NetworkManager networkmanager = NetworkManager.createNetworkManagerAndConnect(InetAddress.getByName(serveraddress.getIP()), serveraddress.getPort(), false);
/*  49 */     this.pingDestinations.add(networkmanager);
/*  50 */     server.serverMOTD = "Pinging...";
/*  51 */     server.pingToServer = -1L;
/*  52 */     server.playerList = null;
/*  53 */     networkmanager.setNetHandler((INetHandler)new INetHandlerStatusClient()
/*     */         {
/*     */           private boolean field_147403_d = false;
/*     */           private boolean field_183009_e = false;
/*  57 */           private long field_175092_e = 0L;
/*     */           
/*     */           public void handleServerInfo(S00PacketServerInfo packetIn) {
/*  60 */             if (this.field_183009_e) {
/*     */               
/*  62 */               networkmanager.closeChannel((IChatComponent)new ChatComponentText("Received unrequested status"));
/*     */             }
/*     */             else {
/*     */               
/*  66 */               this.field_183009_e = true;
/*  67 */               ServerStatusResponse serverstatusresponse = packetIn.getResponse();
/*     */               
/*  69 */               if (serverstatusresponse.getServerDescription() != null) {
/*     */                 
/*  71 */                 server.serverMOTD = serverstatusresponse.getServerDescription().getFormattedText();
/*     */               }
/*     */               else {
/*     */                 
/*  75 */                 server.serverMOTD = "";
/*     */               } 
/*     */               
/*  78 */               if (serverstatusresponse.getProtocolVersionInfo() != null) {
/*     */                 
/*  80 */                 server.gameVersion = serverstatusresponse.getProtocolVersionInfo().getName();
/*  81 */                 server.version = serverstatusresponse.getProtocolVersionInfo().getProtocol();
/*     */               }
/*     */               else {
/*     */                 
/*  85 */                 server.gameVersion = "Old";
/*  86 */                 server.version = 0;
/*     */               } 
/*     */               
/*  89 */               if (serverstatusresponse.getPlayerCountData() != null) {
/*     */                 
/*  91 */                 server.populationInfo = EnumChatFormatting.GRAY + String.valueOf(serverstatusresponse.getPlayerCountData().getOnlinePlayerCount()) + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + serverstatusresponse.getPlayerCountData().getMaxPlayers();
/*     */                 
/*  93 */                 if (ArrayUtils.isNotEmpty((Object[])serverstatusresponse.getPlayerCountData().getPlayers()))
/*     */                 {
/*  95 */                   StringBuilder stringbuilder = new StringBuilder();
/*     */                   
/*  97 */                   for (GameProfile gameprofile : serverstatusresponse.getPlayerCountData().getPlayers()) {
/*     */                     
/*  99 */                     if (stringbuilder.length() > 0)
/*     */                     {
/* 101 */                       stringbuilder.append("\n");
/*     */                     }
/*     */                     
/* 104 */                     stringbuilder.append(gameprofile.getName());
/*     */                   } 
/*     */                   
/* 107 */                   if ((serverstatusresponse.getPlayerCountData().getPlayers()).length < serverstatusresponse.getPlayerCountData().getOnlinePlayerCount()) {
/*     */                     
/* 109 */                     if (stringbuilder.length() > 0)
/*     */                     {
/* 111 */                       stringbuilder.append("\n");
/*     */                     }
/*     */                     
/* 114 */                     stringbuilder.append("... and ").append(serverstatusresponse.getPlayerCountData().getOnlinePlayerCount() - (serverstatusresponse.getPlayerCountData().getPlayers()).length).append(" more ...");
/*     */                   } 
/*     */                   
/* 117 */                   server.playerList = stringbuilder.toString();
/*     */                 }
/*     */               
/*     */               } else {
/*     */                 
/* 122 */                 server.populationInfo = EnumChatFormatting.DARK_GRAY + "???";
/*     */               } 
/*     */               
/* 125 */               if (serverstatusresponse.getFavicon() != null) {
/*     */                 
/* 127 */                 String s = serverstatusresponse.getFavicon();
/*     */                 
/* 129 */                 if (s.startsWith("data:image/png;base64,"))
/*     */                 {
/* 131 */                   server.setBase64EncodedIconData(s.substring("data:image/png;base64,".length()));
/*     */                 }
/*     */                 else
/*     */                 {
/* 135 */                   OldServerPinger.logger.error("Invalid server icon (unknown format)");
/*     */                 }
/*     */               
/*     */               } else {
/*     */                 
/* 140 */                 server.setBase64EncodedIconData((String)null);
/*     */               } 
/*     */               
/* 143 */               this.field_175092_e = Minecraft.getSystemTime();
/* 144 */               networkmanager.sendPacket((Packet)new C01PacketPing(this.field_175092_e));
/* 145 */               this.field_147403_d = true;
/*     */             } 
/*     */           }
/*     */           
/*     */           public void handlePong(S01PacketPong packetIn) {
/* 150 */             long i = this.field_175092_e;
/* 151 */             long j = Minecraft.getSystemTime();
/* 152 */             server.pingToServer = j - i;
/* 153 */             networkmanager.closeChannel((IChatComponent)new ChatComponentText("Finished"));
/*     */           }
/*     */           
/*     */           public void onDisconnect(IChatComponent reason) {
/* 157 */             if (!this.field_147403_d) {
/*     */               
/* 159 */               OldServerPinger.logger.error("Can't ping " + server.serverIP + ": " + reason.getUnformattedText());
/* 160 */               server.serverMOTD = EnumChatFormatting.DARK_RED + "Can't connect to server.";
/* 161 */               server.populationInfo = "";
/* 162 */               OldServerPinger.this.tryCompatibilityPing(server);
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*     */     try {
/* 169 */       networkmanager.sendPacket((Packet)new C00Handshake(47, serveraddress.getIP(), serveraddress.getPort(), EnumConnectionState.STATUS));
/* 170 */       networkmanager.sendPacket((Packet)new C00PacketServerQuery());
/*     */     }
/* 172 */     catch (Throwable throwable) {
/*     */       
/* 174 */       logger.error(throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void tryCompatibilityPing(final ServerData server) {
/* 180 */     final ServerAddress serveraddress = ServerAddress.fromString(server.serverIP);
/* 181 */     ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)NetworkManager.CLIENT_NIO_EVENTLOOP.getValue())).handler((ChannelHandler)new ChannelInitializer<Channel>()
/*     */         {
/*     */           protected void initChannel(Channel p_initChannel_1_)
/*     */           {
/*     */             try {
/* 186 */               p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
/*     */             }
/* 188 */             catch (ChannelException channelException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 193 */             p_initChannel_1_.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)new SimpleChannelInboundHandler<ByteBuf>()
/*     */                   {
/*     */                     public void channelActive(ChannelHandlerContext p_channelActive_1_) throws Exception
/*     */                     {
/* 197 */                       super.channelActive(p_channelActive_1_);
/* 198 */                       ByteBuf bytebuf = Unpooled.buffer();
/*     */ 
/*     */                       
/*     */                       try {
/* 202 */                         bytebuf.writeByte(254);
/* 203 */                         bytebuf.writeByte(1);
/* 204 */                         bytebuf.writeByte(250);
/* 205 */                         char[] achar = "MC|PingHost".toCharArray();
/* 206 */                         bytebuf.writeShort(achar.length);
/*     */                         
/* 208 */                         for (char c0 : achar)
/*     */                         {
/* 210 */                           bytebuf.writeChar(c0);
/*     */                         }
/*     */                         
/* 213 */                         bytebuf.writeShort(7 + 2 * serveraddress.getIP().length());
/* 214 */                         bytebuf.writeByte(127);
/* 215 */                         achar = serveraddress.getIP().toCharArray();
/* 216 */                         bytebuf.writeShort(achar.length);
/*     */                         
/* 218 */                         for (char c1 : achar)
/*     */                         {
/* 220 */                           bytebuf.writeChar(c1);
/*     */                         }
/*     */                         
/* 223 */                         bytebuf.writeInt(serveraddress.getPort());
/* 224 */                         p_channelActive_1_.channel().writeAndFlush(bytebuf).addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
/*     */                       }
/*     */                       finally {
/*     */                         
/* 228 */                         bytebuf.release();
/*     */                       } 
/*     */                     }
/*     */                     protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, ByteBuf p_channelRead0_2_) {
/* 232 */                       short short1 = p_channelRead0_2_.readUnsignedByte();
/*     */                       
/* 234 */                       if (short1 == 255) {
/*     */                         
/* 236 */                         String s = new String(p_channelRead0_2_.readBytes(p_channelRead0_2_.readShort() << 1).array(), Charsets.UTF_16BE);
/* 237 */                         String[] astring = (String[])Iterables.toArray(OldServerPinger.PING_RESPONSE_SPLITTER.split(s), String.class);
/*     */                         
/* 239 */                         if ("§1".equals(astring[0])) {
/*     */                           
/* 241 */                           int i = MathHelper.parseIntWithDefault(astring[1], 0);
/* 242 */                           String s1 = astring[2];
/* 243 */                           String s2 = astring[3];
/* 244 */                           int j = MathHelper.parseIntWithDefault(astring[4], -1);
/* 245 */                           int k = MathHelper.parseIntWithDefault(astring[5], -1);
/* 246 */                           server.version = -1;
/* 247 */                           server.gameVersion = s1;
/* 248 */                           server.serverMOTD = s2;
/* 249 */                           server.populationInfo = EnumChatFormatting.GRAY + String.valueOf(j) + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + k;
/*     */                         } 
/*     */                       } 
/*     */                       
/* 253 */                       p_channelRead0_1_.close();
/*     */                     }
/*     */                     public void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_) {
/* 256 */                       p_exceptionCaught_1_.close();
/*     */                     }
/*     */                   }
/*     */                 });
/*     */           }
/* 261 */         })).channel(NioSocketChannel.class)).connect(serveraddress.getIP(), serveraddress.getPort());
/*     */   }
/*     */ 
/*     */   
/*     */   public void pingPendingNetworks() {
/* 266 */     synchronized (this.pingDestinations) {
/*     */       
/* 268 */       Iterator<NetworkManager> iterator = this.pingDestinations.iterator();
/*     */       
/* 270 */       while (iterator.hasNext()) {
/*     */         
/* 272 */         NetworkManager networkmanager = iterator.next();
/*     */         
/* 274 */         if (networkmanager.isChannelOpen()) {
/*     */           
/* 276 */           networkmanager.processReceivedPackets();
/*     */           
/*     */           continue;
/*     */         } 
/* 280 */         iterator.remove();
/* 281 */         networkmanager.checkDisconnected();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearPendingNetworks() {
/* 289 */     synchronized (this.pingDestinations) {
/*     */       
/* 291 */       Iterator<NetworkManager> iterator = this.pingDestinations.iterator();
/*     */       
/* 293 */       while (iterator.hasNext()) {
/*     */         
/* 295 */         NetworkManager networkmanager = iterator.next();
/*     */         
/* 297 */         if (networkmanager.isChannelOpen()) {
/*     */           
/* 299 */           iterator.remove();
/* 300 */           networkmanager.closeChannel((IChatComponent)new ChatComponentText("Cancelled"));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\network\OldServerPinger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */