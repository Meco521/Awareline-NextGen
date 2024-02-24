/*     */ package net.minecraft.client.network;
/*     */ 
/*     */ import awareline.main.ui.gui.guimainmenu.mainmenu.ClientSetting;
/*     */ import awareline.main.utility.CLUtil;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.exceptions.AuthenticationException;
/*     */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.math.BigInteger;
/*     */ import java.security.PublicKey;
/*     */ import javax.crypto.SecretKey;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiDisconnected;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.network.EnumConnectionState;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.login.INetHandlerLoginClient;
/*     */ import net.minecraft.network.login.client.C01PacketEncryptionResponse;
/*     */ import net.minecraft.network.login.server.S00PacketDisconnect;
/*     */ import net.minecraft.network.login.server.S01PacketEncryptionRequest;
/*     */ import net.minecraft.network.login.server.S02PacketLoginSuccess;
/*     */ import net.minecraft.network.login.server.S03PacketEnableCompression;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.CryptManager;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class NetHandlerLoginClient
/*     */   implements INetHandlerLoginClient {
/*  34 */   private static final Logger logger = LogManager.getLogger();
/*     */   private final Minecraft mc;
/*     */   private final GuiScreen previousGuiScreen;
/*     */   final NetworkManager networkManager;
/*     */   private GameProfile gameProfile;
/*     */   
/*     */   public NetHandlerLoginClient(NetworkManager networkManagerIn, Minecraft mcIn, GuiScreen p_i45059_3_) {
/*  41 */     this.networkManager = networkManagerIn;
/*  42 */     this.mc = mcIn;
/*  43 */     this.previousGuiScreen = p_i45059_3_;
/*     */   }
/*     */   
/*     */   public void handleEncryptionRequest(S01PacketEncryptionRequest packetIn) {
/*  47 */     final SecretKey secretkey = CryptManager.createNewSharedKey();
/*  48 */     String s = packetIn.getServerId();
/*  49 */     PublicKey publickey = packetIn.getPublicKey();
/*  50 */     String s1 = (new BigInteger(CryptManager.getServerIdHash(s, publickey, secretkey))).toString(16);
/*     */     
/*  52 */     if (this.mc.getCurrentServerData() != null && this.mc.getCurrentServerData().isOnLAN()) {
/*     */       try {
/*  54 */         if (((Boolean)ClientSetting.clMode.getValue()).booleanValue()) {
/*  55 */           CLUtil.joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), s1);
/*     */         } else {
/*  57 */           getSessionService().joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), s1);
/*     */         } 
/*  59 */       } catch (AuthenticationException var10) {
/*  60 */         logger.warn("Couldn't connect to auth servers but will continue to join LAN");
/*     */       } 
/*     */     } else {
/*     */       try {
/*  64 */         if (((Boolean)ClientSetting.clMode.getValue()).booleanValue()) {
/*  65 */           CLUtil.joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), s1);
/*     */         } else {
/*  67 */           getSessionService().joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), s1);
/*     */         } 
/*  69 */       } catch (AuthenticationException authenticationexception) {
/*  70 */         this.networkManager.closeChannel((IChatComponent)new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] { authenticationexception.getMessage() }));
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  75 */     this.networkManager.sendPacket((Packet)new C01PacketEncryptionResponse(secretkey, publickey, packetIn.getVerifyToken()), new GenericFutureListener<Future<? super Void>>() {
/*     */           public void operationComplete(Future<? super Void> p_operationComplete_1_) {
/*  77 */             NetHandlerLoginClient.this.networkManager.enableEncryption(secretkey);
/*     */           }
/*     */         },  new GenericFutureListener[0]);
/*     */   }
/*     */   
/*     */   private MinecraftSessionService getSessionService() {
/*  83 */     return this.mc.getSessionService();
/*     */   }
/*     */   
/*     */   public void handleLoginSuccess(S02PacketLoginSuccess packetIn) {
/*  87 */     this.gameProfile = packetIn.getProfile();
/*  88 */     this.networkManager.setConnectionState(EnumConnectionState.PLAY);
/*  89 */     this.networkManager.setNetHandler((INetHandler)new NetHandlerPlayClient(this.mc, this.previousGuiScreen, this.networkManager, this.gameProfile));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisconnect(IChatComponent reason) {
/*  96 */     this.mc.displayGuiScreen((GuiScreen)new GuiDisconnected(this.previousGuiScreen, "connect.failed", reason));
/*     */   }
/*     */   
/*     */   public void handleDisconnect(S00PacketDisconnect packetIn) {
/* 100 */     this.networkManager.closeChannel(packetIn.func_149603_c());
/*     */   }
/*     */   
/*     */   public void handleEnableCompression(S03PacketEnableCompression packetIn) {
/* 104 */     if (!this.networkManager.isLocalChannel())
/* 105 */       this.networkManager.setCompressionTreshold(packetIn.getCompressionTreshold()); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\network\NetHandlerLoginClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */