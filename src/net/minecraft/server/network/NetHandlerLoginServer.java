/*     */ package net.minecraft.server.network;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.math.BigInteger;
/*     */ import java.security.PrivateKey;
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.crypto.SecretKey;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.login.INetHandlerLoginServer;
/*     */ import net.minecraft.network.login.client.C00PacketLoginStart;
/*     */ import net.minecraft.network.login.client.C01PacketEncryptionResponse;
/*     */ import net.minecraft.network.login.server.S00PacketDisconnect;
/*     */ import net.minecraft.network.login.server.S01PacketEncryptionRequest;
/*     */ import net.minecraft.network.login.server.S02PacketLoginSuccess;
/*     */ import net.minecraft.network.login.server.S03PacketEnableCompression;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.CryptManager;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ITickable;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class NetHandlerLoginServer implements INetHandlerLoginServer, ITickable {
/*  37 */   private static final AtomicInteger AUTHENTICATOR_THREAD_ID = new AtomicInteger(0);
/*  38 */   static final Logger logger = LogManager.getLogger();
/*  39 */   private static final Random RANDOM = new Random();
/*  40 */   private final byte[] verifyToken = new byte[4];
/*     */   final MinecraftServer server;
/*     */   public final NetworkManager networkManager;
/*  43 */   LoginState currentLoginState = LoginState.HELLO;
/*     */   
/*     */   private int connectionTimer;
/*     */   
/*     */   GameProfile loginGameProfile;
/*  48 */   String serverId = "";
/*     */   
/*     */   SecretKey secretKey;
/*     */   private EntityPlayerMP player;
/*     */   
/*     */   public NetHandlerLoginServer(MinecraftServer serverIn, NetworkManager networkManagerIn) {
/*  54 */     this.server = serverIn;
/*  55 */     this.networkManager = networkManagerIn;
/*  56 */     RANDOM.nextBytes(this.verifyToken);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  64 */     if (this.currentLoginState == LoginState.READY_TO_ACCEPT) {
/*     */       
/*  66 */       tryAcceptPlayer();
/*     */     }
/*  68 */     else if (this.currentLoginState == LoginState.DELAY_ACCEPT) {
/*     */       
/*  70 */       EntityPlayerMP entityplayermp = this.server.getConfigurationManager().getPlayerByUUID(this.loginGameProfile.getId());
/*     */       
/*  72 */       if (entityplayermp == null) {
/*     */         
/*  74 */         this.currentLoginState = LoginState.READY_TO_ACCEPT;
/*  75 */         this.server.getConfigurationManager().initializeConnectionToPlayer(this.networkManager, this.player);
/*  76 */         this.player = null;
/*     */       } 
/*     */     } 
/*     */     
/*  80 */     if (this.connectionTimer++ == 600)
/*     */     {
/*  82 */       closeConnection("Took too long to log in");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeConnection(String reason) {
/*     */     try {
/*  90 */       logger.info("Disconnecting " + getConnectionInfo() + ": " + reason);
/*  91 */       ChatComponentText chatcomponenttext = new ChatComponentText(reason);
/*  92 */       this.networkManager.sendPacket((Packet)new S00PacketDisconnect((IChatComponent)chatcomponenttext));
/*  93 */       this.networkManager.closeChannel((IChatComponent)chatcomponenttext);
/*     */     }
/*  95 */     catch (Exception exception) {
/*     */       
/*  97 */       logger.error("Error whilst disconnecting player", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tryAcceptPlayer() {
/* 103 */     if (!this.loginGameProfile.isComplete())
/*     */     {
/* 105 */       this.loginGameProfile = getOfflineProfile(this.loginGameProfile);
/*     */     }
/*     */     
/* 108 */     String s = this.server.getConfigurationManager().allowUserToConnect(this.networkManager.getRemoteAddress(), this.loginGameProfile);
/*     */     
/* 110 */     if (s != null) {
/*     */       
/* 112 */       closeConnection(s);
/*     */     }
/*     */     else {
/*     */       
/* 116 */       this.currentLoginState = LoginState.ACCEPTED;
/*     */       
/* 118 */       if (this.server.getNetworkCompressionTreshold() >= 0 && !this.networkManager.isLocalChannel())
/*     */       {
/* 120 */         this.networkManager.sendPacket((Packet)new S03PacketEnableCompression(this.server.getNetworkCompressionTreshold()), (GenericFutureListener)new ChannelFutureListener()
/*     */             {
/*     */               public void operationComplete(ChannelFuture p_operationComplete_1_) {
/* 123 */                 NetHandlerLoginServer.this.networkManager.setCompressionTreshold(NetHandlerLoginServer.this.server.getNetworkCompressionTreshold());
/*     */               }
/*     */             },  new GenericFutureListener[0]);
/*     */       }
/*     */       
/* 128 */       this.networkManager.sendPacket((Packet)new S02PacketLoginSuccess(this.loginGameProfile));
/* 129 */       EntityPlayerMP entityplayermp = this.server.getConfigurationManager().getPlayerByUUID(this.loginGameProfile.getId());
/*     */       
/* 131 */       if (entityplayermp != null) {
/*     */         
/* 133 */         this.currentLoginState = LoginState.DELAY_ACCEPT;
/* 134 */         this.player = this.server.getConfigurationManager().createPlayerForUser(this.loginGameProfile);
/*     */       }
/*     */       else {
/*     */         
/* 138 */         this.server.getConfigurationManager().initializeConnectionToPlayer(this.networkManager, this.server.getConfigurationManager().createPlayerForUser(this.loginGameProfile));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisconnect(IChatComponent reason) {
/* 148 */     logger.info(getConnectionInfo() + " lost connection: " + reason.getUnformattedText());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getConnectionInfo() {
/* 153 */     return (this.loginGameProfile != null) ? (this.loginGameProfile.toString() + " (" + this.networkManager.getRemoteAddress().toString() + ")") : String.valueOf(this.networkManager.getRemoteAddress());
/*     */   }
/*     */ 
/*     */   
/*     */   public void processLoginStart(C00PacketLoginStart packetIn) {
/* 158 */     Validate.validState((this.currentLoginState == LoginState.HELLO), "Unexpected hello packet", new Object[0]);
/* 159 */     this.loginGameProfile = packetIn.getProfile();
/*     */     
/* 161 */     if (this.server.isServerInOnlineMode() && !this.networkManager.isLocalChannel()) {
/*     */       
/* 163 */       this.currentLoginState = LoginState.KEY;
/* 164 */       this.networkManager.sendPacket((Packet)new S01PacketEncryptionRequest(this.serverId, this.server.getKeyPair().getPublic(), this.verifyToken));
/*     */     }
/*     */     else {
/*     */       
/* 168 */       this.currentLoginState = LoginState.READY_TO_ACCEPT;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void processEncryptionResponse(C01PacketEncryptionResponse packetIn) {
/* 174 */     Validate.validState((this.currentLoginState == LoginState.KEY), "Unexpected key packet", new Object[0]);
/* 175 */     PrivateKey privatekey = this.server.getKeyPair().getPrivate();
/*     */     
/* 177 */     if (!Arrays.equals(this.verifyToken, packetIn.getVerifyToken(privatekey)))
/*     */     {
/* 179 */       throw new IllegalStateException("Invalid nonce!");
/*     */     }
/*     */ 
/*     */     
/* 183 */     this.secretKey = packetIn.getSecretKey(privatekey);
/* 184 */     this.currentLoginState = LoginState.AUTHENTICATING;
/* 185 */     this.networkManager.enableEncryption(this.secretKey);
/* 186 */     (new Thread("User Authenticator #" + AUTHENTICATOR_THREAD_ID.incrementAndGet())
/*     */       {
/*     */         public void run()
/*     */         {
/* 190 */           GameProfile gameprofile = NetHandlerLoginServer.this.loginGameProfile;
/*     */ 
/*     */           
/*     */           try {
/* 194 */             String s = (new BigInteger(CryptManager.getServerIdHash(NetHandlerLoginServer.this.serverId, NetHandlerLoginServer.this.server.getKeyPair().getPublic(), NetHandlerLoginServer.this.secretKey))).toString(16);
/* 195 */             NetHandlerLoginServer.this.loginGameProfile = NetHandlerLoginServer.this.server.getMinecraftSessionService().hasJoinedServer(new GameProfile((UUID)null, gameprofile.getName()), s);
/*     */             
/* 197 */             if (NetHandlerLoginServer.this.loginGameProfile != null)
/*     */             {
/* 199 */               NetHandlerLoginServer.logger.info("UUID of player " + NetHandlerLoginServer.this.loginGameProfile.getName() + " is " + NetHandlerLoginServer.this.loginGameProfile.getId());
/* 200 */               NetHandlerLoginServer.this.currentLoginState = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
/*     */             }
/* 202 */             else if (NetHandlerLoginServer.this.server.isSinglePlayer())
/*     */             {
/* 204 */               NetHandlerLoginServer.logger.warn("Failed to verify username but will let them in anyway!");
/* 205 */               NetHandlerLoginServer.this.loginGameProfile = NetHandlerLoginServer.this.getOfflineProfile(gameprofile);
/* 206 */               NetHandlerLoginServer.this.currentLoginState = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
/*     */             }
/*     */             else
/*     */             {
/* 210 */               NetHandlerLoginServer.this.closeConnection("Failed to verify username!");
/* 211 */               NetHandlerLoginServer.logger.error("Username '" + NetHandlerLoginServer.this.loginGameProfile.getName() + "' tried to join with an invalid session");
/*     */             }
/*     */           
/* 214 */           } catch (AuthenticationUnavailableException var3) {
/*     */             
/* 216 */             if (NetHandlerLoginServer.this.server.isSinglePlayer()) {
/*     */               
/* 218 */               NetHandlerLoginServer.logger.warn("Authentication servers are down but will let them in anyway!");
/* 219 */               NetHandlerLoginServer.this.loginGameProfile = NetHandlerLoginServer.this.getOfflineProfile(gameprofile);
/* 220 */               NetHandlerLoginServer.this.currentLoginState = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
/*     */             }
/*     */             else {
/*     */               
/* 224 */               NetHandlerLoginServer.this.closeConnection("Authentication servers are down. Please try again later, sorry!");
/* 225 */               NetHandlerLoginServer.logger.error("Couldn't verify username because servers are unavailable");
/*     */             } 
/*     */           } 
/*     */         }
/* 229 */       }).start();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected GameProfile getOfflineProfile(GameProfile original) {
/* 235 */     UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + original.getName()).getBytes(Charsets.UTF_8));
/* 236 */     return new GameProfile(uuid, original.getName());
/*     */   }
/*     */   
/*     */   enum LoginState
/*     */   {
/* 241 */     HELLO,
/* 242 */     KEY,
/* 243 */     AUTHENTICATING,
/* 244 */     READY_TO_ACCEPT,
/* 245 */     DELAY_ACCEPT,
/* 246 */     ACCEPTED;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\server\network\NetHandlerLoginServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */