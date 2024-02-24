/*     */ package net.minecraft.client.multiplayer;
/*     */ import awareline.main.Client;
/*     */ import awareline.main.component.impl.server.LastConnectionComponent;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiDisconnected;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.network.NetHandlerLoginClient;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.network.EnumConnectionState;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.handshake.client.C00Handshake;
/*     */ import net.minecraft.network.login.client.C00PacketLoginStart;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class GuiConnecting extends GuiScreen {
/*  31 */   private static final AtomicInteger CONNECTION_ID = new AtomicInteger(0);
/*  32 */   static final Logger logger = LogManager.getLogger();
/*     */   
/*     */   NetworkManager networkManager;
/*     */   boolean cancel;
/*     */   final GuiScreen previousGuiScreen;
/*     */   
/*     */   public GuiConnecting(GuiScreen p_i1181_1_, Minecraft mcIn, ServerData p_i1181_3_) {
/*  39 */     this.mc = mcIn;
/*  40 */     this.previousGuiScreen = p_i1181_1_;
/*  41 */     ServerAddress serveraddress = ServerAddress.fromString(p_i1181_3_.serverIP);
/*  42 */     mcIn.loadWorld((WorldClient)null);
/*  43 */     mcIn.setServerData(p_i1181_3_);
/*  44 */     connect(serveraddress.getIP(), serveraddress.getPort());
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiConnecting(GuiScreen p_i1182_1_, Minecraft mcIn, String hostName, int port) {
/*  49 */     this.mc = mcIn;
/*  50 */     this.previousGuiScreen = p_i1182_1_;
/*  51 */     mcIn.loadWorld((WorldClient)null);
/*  52 */     connect(hostName, port);
/*     */   }
/*     */ 
/*     */   
/*     */   private void connect(final String ip, final int port) {
/*  57 */     logger.info("Connecting to " + ip + ", " + port);
/*  58 */     LastConnectionComponent.ip = ip;
/*  59 */     LastConnectionComponent.port = port;
/*  60 */     (new Thread("Server Connector #" + CONNECTION_ID.incrementAndGet())
/*     */       {
/*     */         public void run()
/*     */         {
/*  64 */           InetAddress inetaddress = null;
/*     */ 
/*     */           
/*     */           try {
/*  68 */             if (GuiConnecting.this.cancel) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  73 */             inetaddress = InetAddress.getByName(ip);
/*  74 */             GuiConnecting.this.networkManager = NetworkManager.createNetworkManagerAndConnect(inetaddress, port, GuiConnecting.this.mc.gameSettings.isUsingNativeTransport());
/*  75 */             GuiConnecting.this.networkManager.setNetHandler((INetHandler)new NetHandlerLoginClient(GuiConnecting.this.networkManager, GuiConnecting.this.mc, GuiConnecting.this.previousGuiScreen));
/*  76 */             GuiConnecting.this.networkManager.sendPacket((Packet)new C00Handshake(47, ip, port, EnumConnectionState.LOGIN));
/*  77 */             GuiConnecting.this.networkManager.sendPacket((Packet)new C00PacketLoginStart(GuiConnecting.this.mc.getSession().getProfile()));
/*     */           }
/*  79 */           catch (UnknownHostException unknownhostexception) {
/*     */             
/*  81 */             if (GuiConnecting.this.cancel) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  86 */             GuiConnecting.logger.error("Couldn't connect to server", unknownhostexception);
/*  87 */             GuiConnecting.this.mc.displayGuiScreen((GuiScreen)new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", (IChatComponent)new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Unknown host" })));
/*     */           }
/*  89 */           catch (Exception exception) {
/*     */             
/*  91 */             if (GuiConnecting.this.cancel) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  96 */             GuiConnecting.logger.error("Couldn't connect to server", exception);
/*  97 */             String s = exception.toString();
/*     */             
/*  99 */             if (inetaddress != null) {
/*     */               
/* 101 */               String s1 = inetaddress.toString() + ":" + port;
/* 102 */               s = s.replaceAll(s1, "");
/*     */             } 
/*     */             
/* 105 */             GuiConnecting.this.mc.displayGuiScreen((GuiScreen)new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", (IChatComponent)new ChatComponentTranslation("disconnect.genericReason", new Object[] { s })));
/*     */           } 
/*     */         }
/* 108 */       }).start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 116 */     if (this.networkManager != null)
/*     */     {
/* 118 */       if (this.networkManager.isChannelOpen()) {
/*     */         
/* 120 */         this.networkManager.processReceivedPackets();
/*     */       }
/*     */       else {
/*     */         
/* 124 */         this.networkManager.checkDisconnected();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 142 */     this.buttonList.clear();
/* 143 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/* 150 */     if (button.id == 0) {
/*     */       
/* 152 */       this.cancel = true;
/*     */       
/* 154 */       if (this.networkManager != null)
/*     */       {
/* 156 */         this.networkManager.closeChannel((IChatComponent)new ChatComponentText("Aborted"));
/*     */       }
/*     */       
/* 159 */       this.mc.displayGuiScreen(this.previousGuiScreen);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 164 */     ScaledResolution scaledResolution = new ScaledResolution(this.mc);
/*     */     
/* 166 */     drawDefaultBackground();
/* 167 */     int allY = 3;
/* 168 */     drawLoadingCircle((scaledResolution.getScaledWidth() / 2), (scaledResolution.getScaledHeight() / 4 + 70 - allY));
/*     */     
/* 170 */     String ip = "Unknown";
/*     */     
/* 172 */     ServerData serverData = this.mc.getCurrentServerData();
/* 173 */     if (serverData != null) {
/* 174 */       ip = serverData.serverIP;
/*     */     }
/* 176 */     Client.instance.FontManager.SF18.drawCenteredString("Connecting to", scaledResolution
/* 177 */         .getScaledWidth() / 2, scaledResolution.getScaledHeight() / 4 + 108 - allY, 16777215);
/* 178 */     Client.instance.FontManager.SF18.drawCenteredString(ip, scaledResolution
/* 179 */         .getScaledWidth() / 2, scaledResolution.getScaledHeight() / 4 + 120 - allY, (new Color(128, 0, 255)).getRGB());
/*     */     
/* 181 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   public static void drawLoadingCircle(float x, float y) {
/* 185 */     for (int i = 0; i < 4; i++) {
/* 186 */       int rot = (int)(System.nanoTime() / 5000000L * i % 360L);
/* 187 */       drawCircle(x, y, (i * 10), rot - 180, rot);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void drawCircle(float x, float y, float radius, int start, int end) {
/* 192 */     GlStateManager.enableBlend();
/* 193 */     GlStateManager.disableTexture2D();
/* 194 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 195 */     RenderUtil.glColor(Color.WHITE);
/*     */     
/* 197 */     GL11.glEnable(2848);
/* 198 */     GL11.glLineWidth(2.0F);
/* 199 */     GL11.glBegin(3); float i;
/* 200 */     for (i = end; i >= start; i -= 4.0F) {
/* 201 */       GL11.glVertex2f((float)(x + Math.cos(i * Math.PI / 180.0D) * (radius * 1.001F)), (float)(y + Math.sin(i * Math.PI / 180.0D) * (radius * 1.001F)));
/*     */     }
/* 203 */     GL11.glEnd();
/* 204 */     GL11.glDisable(2848);
/*     */     
/* 206 */     GlStateManager.enableTexture2D();
/* 207 */     GlStateManager.disableBlend();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\multiplayer\GuiConnecting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */