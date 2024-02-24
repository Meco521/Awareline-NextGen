/*     */ package net.minecraft.network;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import io.netty.bootstrap.ServerBootstrap;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.epoll.EpollEventLoopGroup;
/*     */ import io.netty.channel.epoll.EpollServerSocketChannel;
/*     */ import io.netty.channel.local.LocalEventLoopGroup;
/*     */ import io.netty.channel.nio.NioEventLoopGroup;
/*     */ import io.netty.channel.socket.nio.NioServerSocketChannel;
/*     */ import io.netty.handler.timeout.ReadTimeoutHandler;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.net.InetAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.client.network.NetHandlerHandshakeMemory;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.network.NetHandlerHandshakeTCP;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.LazyLoadBase;
/*     */ import net.minecraft.util.MessageDeserializer;
/*     */ import net.minecraft.util.MessageDeserializer2;
/*     */ import net.minecraft.util.MessageSerializer2;
/*     */ 
/*     */ public class NetworkSystem {
/*  38 */   private static final Logger logger = LogManager.getLogger();
/*  39 */   public static final LazyLoadBase<NioEventLoopGroup> eventLoops = new LazyLoadBase<NioEventLoopGroup>()
/*     */     {
/*     */       protected NioEventLoopGroup load()
/*     */       {
/*  43 */         return new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Server IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*  46 */   public static final LazyLoadBase<EpollEventLoopGroup> SERVER_EPOLL_EVENTLOOP = new LazyLoadBase<EpollEventLoopGroup>()
/*     */     {
/*     */       protected EpollEventLoopGroup load()
/*     */       {
/*  50 */         return new EpollEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Epoll Server IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*  53 */   public static final LazyLoadBase<LocalEventLoopGroup> SERVER_LOCAL_EVENTLOOP = new LazyLoadBase<LocalEventLoopGroup>()
/*     */     {
/*     */       protected LocalEventLoopGroup load()
/*     */       {
/*  57 */         return new LocalEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Local Server IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   final MinecraftServer mcServer;
/*     */   
/*     */   public volatile boolean isAlive;
/*     */   
/*  66 */   private final List<ChannelFuture> endpoints = Collections.synchronizedList(Lists.newArrayList());
/*  67 */   final List<NetworkManager> networkManagers = Collections.synchronizedList(Lists.newArrayList());
/*     */ 
/*     */   
/*     */   public NetworkSystem(MinecraftServer server) {
/*  71 */     this.mcServer = server;
/*  72 */     this.isAlive = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLanEndpoint(InetAddress address, int port) {
/*  79 */     synchronized (this.endpoints) {
/*     */       Class<NioServerSocketChannel> clazz;
/*     */       
/*     */       LazyLoadBase<NioEventLoopGroup> lazyLoadBase;
/*     */       
/*  84 */       if (Epoll.isAvailable() && this.mcServer.shouldUseNativeTransport()) {
/*     */         
/*  86 */         Class<EpollServerSocketChannel> clazz1 = EpollServerSocketChannel.class;
/*  87 */         LazyLoadBase<EpollEventLoopGroup> lazyLoadBase1 = SERVER_EPOLL_EVENTLOOP;
/*  88 */         logger.info("Using epoll channel type");
/*     */       }
/*     */       else {
/*     */         
/*  92 */         clazz = NioServerSocketChannel.class;
/*  93 */         lazyLoadBase = eventLoops;
/*  94 */         logger.info("Using default channel type");
/*     */       } 
/*     */       
/*  97 */       this.endpoints.add(((ServerBootstrap)((ServerBootstrap)(new ServerBootstrap()).channel(clazz)).childHandler((ChannelHandler)new ChannelInitializer<Channel>()
/*     */             {
/*     */               protected void initChannel(Channel p_initChannel_1_)
/*     */               {
/*     */                 try {
/* 102 */                   p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
/*     */                 }
/* 104 */                 catch (ChannelException channelException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 109 */                 p_initChannel_1_.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("legacy_query", (ChannelHandler)new PingResponseHandler(NetworkSystem.this)).addLast("splitter", (ChannelHandler)new MessageDeserializer2()).addLast("decoder", (ChannelHandler)new MessageDeserializer(EnumPacketDirection.SERVERBOUND)).addLast("prepender", (ChannelHandler)new MessageSerializer2()).addLast("encoder", (ChannelHandler)new MessageSerializer(EnumPacketDirection.CLIENTBOUND));
/* 110 */                 NetworkManager networkmanager = new NetworkManager(EnumPacketDirection.SERVERBOUND);
/* 111 */                 NetworkSystem.this.networkManagers.add(networkmanager);
/* 112 */                 p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)networkmanager);
/* 113 */                 networkmanager.setNetHandler((INetHandler)new NetHandlerHandshakeTCP(NetworkSystem.this.mcServer, networkmanager));
/*     */               }
/* 115 */             }).group((EventLoopGroup)lazyLoadBase.getValue()).localAddress(address, port)).bind().syncUninterruptibly());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketAddress addLocalEndpoint() {
/*     */     ChannelFuture channelfuture;
/* 126 */     synchronized (this.endpoints) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 136 */       channelfuture = ((ServerBootstrap)((ServerBootstrap)(new ServerBootstrap()).channel(LocalServerChannel.class)).childHandler((ChannelHandler)new ChannelInitializer<Channel>() { protected void initChannel(Channel p_initChannel_1_) { NetworkManager networkmanager = new NetworkManager(EnumPacketDirection.SERVERBOUND); networkmanager.setNetHandler((INetHandler)new NetHandlerHandshakeMemory(NetworkSystem.this.mcServer, networkmanager)); NetworkSystem.this.networkManagers.add(networkmanager); p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)networkmanager); } }).group((EventLoopGroup)eventLoops.getValue()).localAddress((SocketAddress)LocalAddress.ANY)).bind().syncUninterruptibly();
/* 137 */       this.endpoints.add(channelfuture);
/*     */     } 
/*     */     
/* 140 */     return channelfuture.channel().localAddress();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void terminateEndpoints() {
/* 148 */     this.isAlive = false;
/*     */     
/* 150 */     for (ChannelFuture channelfuture : this.endpoints) {
/*     */ 
/*     */       
/*     */       try {
/* 154 */         channelfuture.channel().close().sync();
/*     */       }
/* 156 */       catch (InterruptedException var4) {
/*     */         
/* 158 */         logger.error("Interrupted whilst closing channel");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void networkTick() {
/* 169 */     synchronized (this.networkManagers) {
/*     */       
/* 171 */       Iterator<NetworkManager> iterator = this.networkManagers.iterator();
/*     */       
/* 173 */       while (iterator.hasNext()) {
/*     */         
/* 175 */         final NetworkManager networkmanager = iterator.next();
/*     */         
/* 177 */         if (!networkmanager.hasNoChannel()) {
/*     */           
/* 179 */           if (!networkmanager.isChannelOpen()) {
/*     */             
/* 181 */             iterator.remove();
/* 182 */             networkmanager.checkDisconnected();
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/*     */           try {
/* 188 */             networkmanager.processReceivedPackets();
/*     */           }
/* 190 */           catch (Exception exception) {
/*     */             
/* 192 */             if (networkmanager.isLocalChannel()) {
/*     */               
/* 194 */               CrashReport crashreport = CrashReport.makeCrashReport(exception, "Ticking memory connection");
/* 195 */               CrashReportCategory crashreportcategory = crashreport.makeCategory("Ticking connection");
/* 196 */               crashreportcategory.addCrashSectionCallable("Connection", new Callable<String>()
/*     */                   {
/*     */                     public String call() {
/* 199 */                       return networkmanager.toString();
/*     */                     }
/*     */                   });
/* 202 */               throw new ReportedException(crashreport);
/*     */             } 
/*     */             
/* 205 */             logger.warn("Failed to handle packet for " + networkmanager.getRemoteAddress(), exception);
/* 206 */             final ChatComponentText chatcomponenttext = new ChatComponentText("Internal server error");
/* 207 */             networkmanager.sendPacket((Packet)new S40PacketDisconnect((IChatComponent)chatcomponenttext), new GenericFutureListener<Future<? super Void>>()
/*     */                 {
/*     */                   public void operationComplete(Future<? super Void> p_operationComplete_1_) {
/* 210 */                     networkmanager.closeChannel((IChatComponent)chatcomponenttext);
/*     */                   }
/*     */                 },  (GenericFutureListener<? extends Future<? super Void>>[])new GenericFutureListener[0]);
/* 213 */             networkmanager.disableAutoRead();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MinecraftServer getServer() {
/* 223 */     return this.mcServer;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\NetworkSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */