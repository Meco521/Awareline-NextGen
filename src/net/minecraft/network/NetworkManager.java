/*     */ package net.minecraft.network;
/*     */ import awareline.main.event.Event;
/*     */ import awareline.main.event.EventManager;
/*     */ import awareline.main.event.events.world.packetEvents.EventPacketReceive;
/*     */ import awareline.main.event.events.world.packetEvents.EventPacketSend;
/*     */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*     */ import com.github.creeper123123321.viafabric.ViaFabric;
/*     */ import com.github.creeper123123321.viafabric.handler.clientside.VRDecodeHandler;
/*     */ import com.github.creeper123123321.viafabric.platform.VRClientSideUserConnection;
/*     */ import com.github.creeper123123321.viafabric.protocol.ViaFabricHostnameProtocol;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import com.viamcp.utils.Util;
/*     */ import io.netty.bootstrap.Bootstrap;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.epoll.EpollEventLoopGroup;
/*     */ import io.netty.channel.epoll.EpollSocketChannel;
/*     */ import io.netty.channel.local.LocalEventLoopGroup;
/*     */ import io.netty.channel.nio.NioEventLoopGroup;
/*     */ import io.netty.channel.socket.nio.NioSocketChannel;
/*     */ import io.netty.handler.timeout.ReadTimeoutHandler;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.net.InetAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import javax.crypto.SecretKey;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.CryptManager;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.LazyLoadBase;
/*     */ import net.minecraft.util.MessageDeserializer;
/*     */ import net.minecraft.util.MessageDeserializer2;
/*     */ import net.minecraft.util.MessageSerializer2;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import us.myles.ViaVersion.api.data.UserConnection;
/*     */ import us.myles.ViaVersion.api.protocol.Protocol;
/*     */ import us.myles.ViaVersion.api.protocol.ProtocolPipeline;
/*     */ 
/*     */ public class NetworkManager extends SimpleChannelInboundHandler<Packet> {
/*  49 */   public static final Marker logMarkerNetwork = MarkerManager.getMarker("NETWORK");
/*  50 */   public static final Marker logMarkerPackets = MarkerManager.getMarker("NETWORK_PACKETS", logMarkerNetwork);
/*  51 */   public static final AttributeKey<EnumConnectionState> attrKeyConnectionState = AttributeKey.valueOf("protocol");
/*  52 */   public static final LazyLoadBase<NioEventLoopGroup> CLIENT_NIO_EVENTLOOP = new LazyLoadBase<NioEventLoopGroup>() {
/*     */       protected NioEventLoopGroup load() {
/*  54 */         return new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Client IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*  57 */   public static final LazyLoadBase<EpollEventLoopGroup> field_181125_e = new LazyLoadBase<EpollEventLoopGroup>() {
/*     */       protected EpollEventLoopGroup load() {
/*  59 */         return new EpollEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Epoll Client IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*  62 */   public static final LazyLoadBase<LocalEventLoopGroup> CLIENT_LOCAL_EVENTLOOP = new LazyLoadBase<LocalEventLoopGroup>() {
/*     */       protected LocalEventLoopGroup load() {
/*  64 */         return new LocalEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Local Client IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*  67 */   private static final Logger logger = LogManager.getLogger();
/*     */   private final EnumPacketDirection direction;
/*  69 */   private final Queue<InboundHandlerTuplePacketListener> outboundPacketsQueue = Queues.newConcurrentLinkedQueue();
/*  70 */   private final ReentrantReadWriteLock field_181680_j = new ReentrantReadWriteLock();
/*     */ 
/*     */   
/*     */   public UserConnection user;
/*     */ 
/*     */   
/*     */   Channel channel;
/*     */ 
/*     */   
/*     */   private SocketAddress socketAddress;
/*     */ 
/*     */   
/*     */   private INetHandler packetListener;
/*     */ 
/*     */   
/*     */   private IChatComponent terminationReason;
/*     */ 
/*     */   
/*     */   private boolean isEncrypted;
/*     */ 
/*     */   
/*     */   private boolean disconnected;
/*     */ 
/*     */   
/*     */   public NetworkManager(EnumPacketDirection packetDirection) {
/*  95 */     this.direction = packetDirection;
/*     */   } public static NetworkManager createNetworkManagerAndConnect(InetAddress p_181124_0_, int p_181124_1_, boolean p_181124_2_) {
/*     */     Class<NioSocketChannel> clazz;
/*     */     LazyLoadBase<NioEventLoopGroup> lazyLoadBase;
/*  99 */     final NetworkManager networkmanager = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
/*     */ 
/*     */ 
/*     */     
/* 103 */     if (Epoll.isAvailable() && p_181124_2_) {
/* 104 */       Class<EpollSocketChannel> clazz1 = EpollSocketChannel.class;
/* 105 */       LazyLoadBase<EpollEventLoopGroup> lazyLoadBase1 = field_181125_e;
/*     */     } else {
/* 107 */       clazz = NioSocketChannel.class;
/* 108 */       lazyLoadBase = CLIENT_NIO_EVENTLOOP;
/*     */     } 
/*     */     
/* 111 */     ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)lazyLoadBase.getValue())).handler((ChannelHandler)new ChannelInitializer<Channel>() {
/*     */           protected void initChannel(Channel p_initChannel_1_) {
/*     */             try {
/* 114 */               p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
/* 115 */             } catch (ChannelException channelException) {}
/*     */ 
/*     */ 
/*     */             
/* 119 */             p_initChannel_1_.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("splitter", (ChannelHandler)new MessageDeserializer2()).addLast("decoder", (ChannelHandler)new MessageDeserializer(EnumPacketDirection.CLIENTBOUND)).addLast("prepender", (ChannelHandler)new MessageSerializer2()).addLast("encoder", (ChannelHandler)new MessageSerializer(EnumPacketDirection.SERVERBOUND)).addLast("packet_handler", (ChannelHandler)networkmanager);
/*     */ 
/*     */             
/* 122 */             if (ViaFabric.clientSideVersion != 47 && 
/* 123 */               p_initChannel_1_ instanceof io.netty.channel.socket.SocketChannel) {
/* 124 */               networkmanager.user = (UserConnection)new VRClientSideUserConnection(p_initChannel_1_);
/* 125 */               (new ProtocolPipeline(networkmanager.user)).add((Protocol)ViaFabricHostnameProtocol.INSTANCE);
/*     */ 
/*     */               
/* 128 */               p_initChannel_1_.pipeline()
/* 129 */                 .addBefore("encoder", "via-encoder", (ChannelHandler)new VREncodeHandler(networkmanager.user))
/* 130 */                 .addBefore("decoder", "via-decoder", (ChannelHandler)new VRDecodeHandler(networkmanager.user));
/*     */ 
/*     */             
/*     */             }
/*     */           
/*     */           }
/* 136 */         })).channel(clazz)).connect(p_181124_0_, p_181124_1_).syncUninterruptibly();
/* 137 */     return networkmanager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NetworkManager provideLocalClient(SocketAddress address) {
/* 145 */     final NetworkManager networkmanager = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
/* 146 */     ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)CLIENT_LOCAL_EVENTLOOP.getValue())).handler((ChannelHandler)new ChannelInitializer<Channel>() {
/*     */           protected void initChannel(Channel p_initChannel_1_) {
/* 148 */             p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)networkmanager);
/*     */           }
/* 150 */         })).channel(LocalChannel.class)).connect(address).syncUninterruptibly();
/* 151 */     return networkmanager;
/*     */   }
/*     */   
/*     */   public void channelActive(ChannelHandlerContext p_channelActive_1_) throws Exception {
/* 155 */     super.channelActive(p_channelActive_1_);
/* 156 */     this.channel = p_channelActive_1_.channel();
/* 157 */     this.socketAddress = this.channel.remoteAddress();
/*     */     
/*     */     try {
/* 160 */       setConnectionState(EnumConnectionState.HANDSHAKING);
/* 161 */     } catch (Throwable throwable) {
/* 162 */       logger.fatal(throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConnectionState(EnumConnectionState newState) {
/* 170 */     this.channel.attr(attrKeyConnectionState).set(newState);
/* 171 */     this.channel.config().setAutoRead(true);
/* 172 */     logger.debug("Enabled auto read");
/*     */   }
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext p_channelInactive_1_) {
/* 176 */     closeChannel((IChatComponent)new ChatComponentTranslation("disconnect.endOfStream", new Object[0]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_) {
/*     */     ChatComponentTranslation chatcomponenttranslation;
/* 205 */     if (p_exceptionCaught_2_ instanceof io.netty.handler.timeout.TimeoutException) {
/* 206 */       chatcomponenttranslation = new ChatComponentTranslation("disconnect.timeout", new Object[0]);
/*     */     } else {
/* 208 */       chatcomponenttranslation = new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Internal Exception: " + p_exceptionCaught_2_ });
/*     */     } 
/*     */     
/* 211 */     closeChannel((IChatComponent)chatcomponenttranslation);
/*     */   }
/*     */   
/*     */   protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, Packet p_channelRead0_2_) throws Exception {
/* 215 */     PacketEvent packetEvent = new PacketEvent(p_channelRead0_2_, PacketEvent.State.INCOMING);
/* 216 */     if (this.direction == EnumPacketDirection.CLIENTBOUND) {
/* 217 */       EventManager.call((Event)packetEvent);
/*     */     }
/* 219 */     if (packetEvent.isCancelled()) {
/*     */       return;
/*     */     }
/* 222 */     if (this.channel.isOpen()) {
/*     */       
/* 224 */       EventPacketReceive event = new EventPacketReceive(p_channelRead0_2_);
/* 225 */       EventManager.call((Event)event);
/*     */       
/* 227 */       if (event.isCancelled()) {
/*     */         return;
/*     */       }
/*     */       try {
/* 231 */         event.getPacket().processPacket(this.packetListener);
/* 232 */       } catch (ThreadQuickExitException threadQuickExitException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPacket(Packet packetIn) {
/* 239 */     PacketEvent packetEvent = new PacketEvent(packetIn, PacketEvent.State.OUTGOING);
/* 240 */     if (this.direction == EnumPacketDirection.CLIENTBOUND) {
/* 241 */       EventManager.call((Event)packetEvent);
/*     */     }
/* 243 */     if (packetEvent.isCancelled()) {
/*     */       return;
/*     */     }
/* 246 */     if (isChannelOpen()) {
/* 247 */       flushOutboundQueue();
/*     */       
/* 249 */       EventPacketSend event = new EventPacketSend(packetIn);
/* 250 */       EventManager.call((Event)event);
/* 251 */       if (!event.isCancelled()) {
/* 252 */         dispatchPacket(packetIn, null);
/*     */       }
/*     */     } else {
/* 255 */       this.field_181680_j.writeLock().lock();
/*     */       
/*     */       try {
/* 258 */         this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(packetIn, (GenericFutureListener<? extends Future<? super Void>>[])null));
/*     */       } finally {
/*     */         
/* 261 */         this.field_181680_j.writeLock().unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPacket(Packet packetIn, GenericFutureListener<? extends Future<? super Void>> listener, GenericFutureListener<? extends Future<? super Void>>... listeners) {
/* 269 */     if (isChannelOpen()) {
/* 270 */       flushOutboundQueue();
/* 271 */       dispatchPacket(packetIn, (GenericFutureListener<? extends Future<? super Void>>[])ArrayUtils.add((Object[])listeners, 0, listener));
/*     */     } else {
/* 273 */       this.field_181680_j.writeLock().lock();
/*     */       
/*     */       try {
/* 276 */         this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(packetIn, (GenericFutureListener<? extends Future<? super Void>>[])ArrayUtils.add((Object[])listeners, 0, listener)));
/*     */       } finally {
/* 278 */         this.field_181680_j.writeLock().unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void dispatchPacket(final Packet inPacket, final GenericFutureListener<? extends Future<? super Void>>[] futureListeners) {
/* 288 */     final EnumConnectionState enumconnectionstate = EnumConnectionState.getFromPacket(inPacket);
/* 289 */     final EnumConnectionState enumconnectionstate1 = (EnumConnectionState)this.channel.attr(attrKeyConnectionState).get();
/*     */     
/* 291 */     if (enumconnectionstate1 != enumconnectionstate) {
/* 292 */       logger.debug("Disabled auto read");
/* 293 */       this.channel.config().setAutoRead(false);
/*     */     } 
/*     */     
/* 296 */     if (this.channel.eventLoop().inEventLoop()) {
/* 297 */       if (enumconnectionstate != enumconnectionstate1) {
/* 298 */         setConnectionState(enumconnectionstate);
/*     */       }
/*     */       
/* 301 */       ChannelFuture channelfuture = this.channel.writeAndFlush(inPacket);
/*     */       
/* 303 */       if (futureListeners != null) {
/* 304 */         channelfuture.addListeners((GenericFutureListener[])futureListeners);
/*     */       }
/*     */       
/* 307 */       channelfuture.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
/*     */     } else {
/* 309 */       this.channel.eventLoop().execute(new Runnable() {
/*     */             public void run() {
/* 311 */               if (enumconnectionstate != enumconnectionstate1) {
/* 312 */                 NetworkManager.this.setConnectionState(enumconnectionstate);
/*     */               }
/*     */               
/* 315 */               ChannelFuture channelfuture1 = NetworkManager.this.channel.writeAndFlush(inPacket);
/*     */               
/* 317 */               if (futureListeners != null) {
/* 318 */                 channelfuture1.addListeners(futureListeners);
/*     */               }
/*     */               
/* 321 */               channelfuture1.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void flushOutboundQueue() {
/* 331 */     if (this.channel != null && this.channel.isOpen()) {
/* 332 */       this.field_181680_j.readLock().lock();
/*     */       
/*     */       try {
/* 335 */         while (!this.outboundPacketsQueue.isEmpty()) {
/* 336 */           InboundHandlerTuplePacketListener networkmanager$inboundhandlertuplepacketlistener = this.outboundPacketsQueue.poll();
/* 337 */           dispatchPacket(networkmanager$inboundhandlertuplepacketlistener.packet, networkmanager$inboundhandlertuplepacketlistener.futureListeners);
/*     */         } 
/*     */       } finally {
/* 340 */         this.field_181680_j.readLock().unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processReceivedPackets() {
/* 349 */     flushOutboundQueue();
/*     */     
/* 351 */     if (this.packetListener instanceof ITickable) {
/* 352 */       ((ITickable)this.packetListener).update();
/*     */     }
/*     */     
/* 355 */     this.channel.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketAddress getRemoteAddress() {
/* 362 */     return this.socketAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeChannel(IChatComponent message) {
/* 369 */     if (this.channel.isOpen()) {
/* 370 */       this.channel.close().awaitUninterruptibly();
/* 371 */       this.terminationReason = message;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocalChannel() {
/* 380 */     return (this.channel instanceof LocalChannel || this.channel instanceof io.netty.channel.local.LocalServerChannel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableEncryption(SecretKey key) {
/* 387 */     this.isEncrypted = true;
/* 388 */     this.channel.pipeline().addBefore("splitter", "decrypt", (ChannelHandler)new NettyEncryptingDecoder(CryptManager.createNetCipherInstance(2, key)));
/* 389 */     this.channel.pipeline().addBefore("prepender", "encrypt", (ChannelHandler)new NettyEncryptingEncoder(CryptManager.createNetCipherInstance(1, key)));
/*     */   }
/*     */   
/*     */   public boolean getIsencrypted() {
/* 393 */     return this.isEncrypted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChannelOpen() {
/* 400 */     return (this.channel != null && this.channel.isOpen());
/*     */   }
/*     */   
/*     */   public boolean hasNoChannel() {
/* 404 */     return (this.channel == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public INetHandler getNetHandler() {
/* 411 */     return this.packetListener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNetHandler(INetHandler handler) {
/* 419 */     Validate.notNull(handler, "packetListener", new Object[0]);
/* 420 */     logger.debug("Set listener of {} to {}", new Object[] { this, handler });
/* 421 */     this.packetListener = handler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getExitMessage() {
/* 428 */     return this.terminationReason;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disableAutoRead() {
/* 435 */     this.channel.config().setAutoRead(false);
/*     */   }
/*     */   
/*     */   public void setCompressionTreshold(int treshold) {
/* 439 */     if (treshold >= 0) {
/* 440 */       if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
/* 441 */         ((NettyCompressionDecoder)this.channel.pipeline().get("decompress")).setCompressionTreshold(treshold);
/*     */       } else {
/*     */         
/* 444 */         Util.decodeEncodePlacement(this.channel.pipeline(), "decoder", "decompress", (ChannelHandler)new NettyCompressionDecoder(treshold));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 451 */       if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
/* 452 */         ((NettyCompressionEncoder)this.channel.pipeline().get("compress")).setCompressionTreshold(treshold);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 457 */         Util.decodeEncodePlacement(this.channel.pipeline(), "encoder", "compress", (ChannelHandler)new NettyCompressionEncoder(treshold));
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 462 */       if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
/* 463 */         this.channel.pipeline().remove("decompress");
/*     */       }
/*     */       
/* 466 */       if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
/* 467 */         this.channel.pipeline().remove("compress");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void checkDisconnected() {
/* 473 */     if (this.channel != null && !this.channel.isOpen()) {
/* 474 */       if (!this.disconnected) {
/* 475 */         this.disconnected = true;
/*     */         
/* 477 */         if (this.terminationReason != null) {
/* 478 */           this.packetListener.onDisconnect(this.terminationReason);
/* 479 */         } else if (this.packetListener != null) {
/* 480 */           this.packetListener.onDisconnect((IChatComponent)new ChatComponentText("Disconnected"));
/*     */         } 
/*     */       } else {
/* 483 */         logger.warn("handleDisconnection() called twice");
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void sendPacketNoEvent(Packet packetIn) {
/* 489 */     if (isChannelOpen()) {
/* 490 */       flushOutboundQueue();
/* 491 */       dispatchPacket(packetIn, (GenericFutureListener<? extends Future<? super Void>>[])null);
/*     */     } else {
/* 493 */       this.field_181680_j.writeLock().lock();
/*     */       
/*     */       try {
/* 496 */         this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(packetIn, (GenericFutureListener<? extends Future<? super Void>>[])null));
/*     */       } finally {
/* 498 */         this.field_181680_j.writeLock().unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static class InboundHandlerTuplePacketListener {
/*     */     final Packet packet;
/*     */     final GenericFutureListener<? extends Future<? super Void>>[] futureListeners;
/*     */     
/*     */     public InboundHandlerTuplePacketListener(Packet inPacket, GenericFutureListener<? extends Future<? super Void>>... inFutureListeners) {
/* 508 */       this.packet = inPacket;
/* 509 */       this.futureListeners = inFutureListeners;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\NetworkManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */