/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.github.creeper123123321.viafabric.ViaFabric;
/*     */ import com.github.creeper123123321.viafabric.util.ProtocolUtils;
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.viamcp.gui.GuiProtocolSelector;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.multiplayer.GuiConnecting;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.multiplayer.ServerList;
/*     */ import net.minecraft.client.network.LanServerDetector;
/*     */ import net.minecraft.client.network.OldServerPinger;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiMultiplayer
/*     */   extends GuiScreen {
/*  22 */   private static final Logger logger = LogManager.getLogger();
/*  23 */   private final OldServerPinger oldServerPinger = new OldServerPinger();
/*     */   
/*     */   private final GuiScreen parentScreen;
/*     */   
/*     */   private ServerSelectionList serverListSelector;
/*     */   
/*     */   private ServerList savedServerList;
/*     */   
/*     */   private GuiButton btnEditServer;
/*     */   private GuiButton btnSelectServer;
/*     */   private GuiButton btnDeleteServer;
/*     */   private boolean deletingServer;
/*     */   private boolean addingServer;
/*     */   private boolean editingServer;
/*     */   private boolean directConnect;
/*     */   private String hoveringText;
/*     */   private ServerData selectedServer;
/*     */   private LanServerDetector.LanServerList lanServerList;
/*     */   private LanServerDetector.ThreadLanServerFind lanServerDetector;
/*     */   private boolean initialized;
/*     */   
/*     */   public GuiMultiplayer(GuiScreen parentScreen) {
/*  45 */     this.parentScreen = parentScreen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  53 */     Keyboard.enableRepeatEvents(true);
/*  54 */     this.buttonList.clear();
/*     */     
/*  56 */     if (!this.initialized) {
/*  57 */       this.initialized = true;
/*  58 */       this.savedServerList = new ServerList(this.mc);
/*  59 */       this.savedServerList.loadServerList();
/*  60 */       this.lanServerList = new LanServerDetector.LanServerList();
/*     */       
/*     */       try {
/*  63 */         this.lanServerDetector = new LanServerDetector.ThreadLanServerFind(this.lanServerList);
/*  64 */         this.lanServerDetector.start();
/*  65 */       } catch (Exception exception) {
/*  66 */         logger.warn("Unable to start LAN server detection: " + exception.getMessage());
/*     */       } 
/*     */       
/*  69 */       this.serverListSelector = new ServerSelectionList(this, this.mc, this.width, this.height, 32, this.height - 64, 36);
/*  70 */       this.serverListSelector.func_148195_a(this.savedServerList);
/*     */     } else {
/*  72 */       this.serverListSelector.setDimensions(this.width, this.height, 32, this.height - 64);
/*     */     } 
/*     */     
/*  75 */     createButtons();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  82 */     super.handleMouseInput();
/*  83 */     this.serverListSelector.handleMouseInput();
/*     */   }
/*     */   
/*     */   public void createButtons() {
/*  87 */     this.buttonList.add(this.btnEditServer = new GuiButton(7, this.width / 2 - 154, this.height - 28, 70, 20, I18n.format("selectServer.edit", new Object[0])));
/*  88 */     this.buttonList.add(this.btnDeleteServer = new GuiButton(2, this.width / 2 - 74, this.height - 28, 70, 20, I18n.format("selectServer.delete", new Object[0])));
/*     */     
/*  90 */     this.buttonList.add(this
/*  91 */         .btnSelectServer = new GuiButton(1, this.width / 2 - 154, this.height - 52, 100, 20, I18n.format("selectServer.select", new Object[0])));
/*  92 */     this.buttonList.add(new GuiButton(4, this.width / 2 - 50, this.height - 52, 100, 20, I18n.format("selectServer.direct", new Object[0])));
/*  93 */     this.buttonList.add(new GuiButton(3, this.width / 2 + 4 + 50, this.height - 52, 100, 20, I18n.format("selectServer.add", new Object[0])));
/*  94 */     this.buttonList.add(new GuiButton(8, this.width / 2 + 4, this.height - 28, 70, 20, I18n.format("selectServer.refresh", new Object[0])));
/*  95 */     this.buttonList.add(new GuiButton(0, this.width / 2 + 4 + 76, this.height - 28, 75, 20, I18n.format("gui.cancel", new Object[0])));
/*  96 */     this.buttonList.add(new GuiButton(778, 10, 5, 100, 20, "Via " + ProtocolUtils.getProtocolName(ViaFabric.clientSideVersion)));
/*  97 */     ScaledResolution sr = new ScaledResolution(this.mc);
/*  98 */     this.buttonList.add(new GuiButton(779, sr.getScaledWidth() - 2 - 130, 5, 120, 20, "Join DCJ "));
/*     */     
/* 100 */     selectServer(this.serverListSelector.func_148193_k());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 107 */     super.updateScreen();
/*     */     
/* 109 */     if (this.lanServerList.getWasUpdated()) {
/* 110 */       List<LanServerDetector.LanServer> list = this.lanServerList.getLanServers();
/* 111 */       this.lanServerList.setWasNotUpdated();
/* 112 */       this.serverListSelector.func_148194_a(list);
/*     */     } 
/*     */     
/* 115 */     this.oldServerPinger.pingPendingNetworks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 122 */     Keyboard.enableRepeatEvents(false);
/*     */     
/* 124 */     if (this.lanServerDetector != null) {
/* 125 */       this.lanServerDetector.interrupt();
/* 126 */       this.lanServerDetector = null;
/*     */     } 
/*     */     
/* 129 */     this.oldServerPinger.clearPendingNetworks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/* 136 */     if (button.enabled) {
/* 137 */       GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (this.serverListSelector.func_148193_k() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());
/*     */       
/* 139 */       if (button.id == 2 && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/* 140 */         String s4 = (((ServerListEntryNormal)guilistextended$iguilistentry).getServerData()).serverName;
/*     */         
/* 142 */         if (s4 != null) {
/* 143 */           this.deletingServer = true;
/* 144 */           String s = I18n.format("selectServer.deleteQuestion", new Object[0]);
/* 145 */           String s1 = "'" + s4 + "' " + I18n.format("selectServer.deleteWarning", new Object[0]);
/* 146 */           String s2 = I18n.format("selectServer.deleteButton", new Object[0]);
/* 147 */           String s3 = I18n.format("gui.cancel", new Object[0]);
/* 148 */           GuiYesNo guiyesno = new GuiYesNo(this, s, s1, s2, s3, this.serverListSelector.func_148193_k());
/* 149 */           this.mc.displayGuiScreen(guiyesno);
/*     */         } 
/* 151 */       } else if (button.id == 1) {
/* 152 */         connectToSelected();
/* 153 */       } else if (button.id == 4) {
/* 154 */         this.directConnect = true;
/* 155 */         this.mc.displayGuiScreen(new GuiScreenServerList(this, this.selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "", false)));
/* 156 */       } else if (button.id == 3) {
/* 157 */         this.addingServer = true;
/* 158 */         this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "", false)));
/* 159 */       } else if (button.id == 7 && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/* 160 */         this.editingServer = true;
/* 161 */         ServerData serverdata = ((ServerListEntryNormal)guilistextended$iguilistentry).getServerData();
/* 162 */         this.selectedServer = new ServerData(serverdata.serverName, serverdata.serverIP, false);
/* 163 */         this.selectedServer.copyFrom(serverdata);
/* 164 */         this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.selectedServer));
/* 165 */       } else if (button.id == 0) {
/* 166 */         this.mc.displayGuiScreen(this.parentScreen);
/* 167 */       } else if (button.id == 8) {
/* 168 */         refreshServerList();
/* 169 */       } else if (button.id == 778) {
/* 170 */         this.mc.displayGuiScreen((GuiScreen)new GuiProtocolSelector(this));
/* 171 */       } else if (button.id == 779) {
/* 172 */         this.selectedServer = new ServerData("DCJ", "mc.mcga.ml", false);
/* 173 */         this.mc.displayGuiScreen((GuiScreen)new GuiConnecting(this, this.mc, this.selectedServer));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void refreshServerList() {
/* 179 */     this.mc.displayGuiScreen(new GuiMultiplayer(this.parentScreen));
/*     */   }
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 183 */     GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (this.serverListSelector.func_148193_k() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());
/*     */     
/* 185 */     if (this.deletingServer) {
/* 186 */       this.deletingServer = false;
/*     */       
/* 188 */       if (result && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/* 189 */         this.savedServerList.removeServerData(this.serverListSelector.func_148193_k());
/* 190 */         this.savedServerList.saveServerList();
/* 191 */         this.serverListSelector.setSelectedSlotIndex(-1);
/* 192 */         this.serverListSelector.func_148195_a(this.savedServerList);
/*     */       } 
/*     */       
/* 195 */       this.mc.displayGuiScreen(this);
/* 196 */     } else if (this.directConnect) {
/* 197 */       this.directConnect = false;
/*     */       
/* 199 */       if (result) {
/* 200 */         connectToServer(this.selectedServer);
/*     */       } else {
/* 202 */         this.mc.displayGuiScreen(this);
/*     */       } 
/* 204 */     } else if (this.addingServer) {
/* 205 */       this.addingServer = false;
/*     */       
/* 207 */       if (result) {
/* 208 */         this.savedServerList.addServerData(this.selectedServer);
/* 209 */         this.savedServerList.saveServerList();
/* 210 */         this.serverListSelector.setSelectedSlotIndex(-1);
/* 211 */         this.serverListSelector.func_148195_a(this.savedServerList);
/*     */       } 
/*     */       
/* 214 */       this.mc.displayGuiScreen(this);
/* 215 */     } else if (this.editingServer) {
/* 216 */       this.editingServer = false;
/*     */       
/* 218 */       if (result && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/* 219 */         ServerData serverdata = ((ServerListEntryNormal)guilistextended$iguilistentry).getServerData();
/* 220 */         serverdata.serverName = this.selectedServer.serverName;
/* 221 */         serverdata.serverIP = this.selectedServer.serverIP;
/* 222 */         serverdata.copyFrom(this.selectedServer);
/* 223 */         this.savedServerList.saveServerList();
/* 224 */         this.serverListSelector.func_148195_a(this.savedServerList);
/*     */       } 
/*     */       
/* 227 */       this.mc.displayGuiScreen(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 236 */     int i = this.serverListSelector.func_148193_k();
/* 237 */     GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (i < 0) ? null : this.serverListSelector.getListEntry(i);
/*     */     
/* 239 */     if (keyCode == 63) {
/* 240 */       refreshServerList();
/*     */     }
/* 242 */     else if (i >= 0) {
/* 243 */       if (keyCode == 200) {
/* 244 */         if (isShiftKeyDown()) {
/* 245 */           if (i > 0 && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/* 246 */             this.savedServerList.swapServers(i, i - 1);
/* 247 */             selectServer(this.serverListSelector.func_148193_k() - 1);
/* 248 */             this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
/* 249 */             this.serverListSelector.func_148195_a(this.savedServerList);
/*     */           } 
/* 251 */         } else if (i > 0) {
/* 252 */           selectServer(this.serverListSelector.func_148193_k() - 1);
/* 253 */           this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
/*     */           
/* 255 */           if (this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k()) instanceof ServerListEntryLanScan) {
/* 256 */             if (this.serverListSelector.func_148193_k() > 0) {
/* 257 */               selectServer(this.serverListSelector.getSize() - 1);
/* 258 */               this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
/*     */             } else {
/* 260 */               selectServer(-1);
/*     */             } 
/*     */           }
/*     */         } else {
/* 264 */           selectServer(-1);
/*     */         } 
/* 266 */       } else if (keyCode == 208) {
/* 267 */         if (isShiftKeyDown()) {
/* 268 */           if (i < this.savedServerList.countServers() - 1) {
/* 269 */             this.savedServerList.swapServers(i, i + 1);
/* 270 */             selectServer(i + 1);
/* 271 */             this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
/* 272 */             this.serverListSelector.func_148195_a(this.savedServerList);
/*     */           } 
/* 274 */         } else if (i < this.serverListSelector.getSize()) {
/* 275 */           selectServer(this.serverListSelector.func_148193_k() + 1);
/* 276 */           this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
/*     */           
/* 278 */           if (this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k()) instanceof ServerListEntryLanScan) {
/* 279 */             if (this.serverListSelector.func_148193_k() < this.serverListSelector.getSize() - 1) {
/* 280 */               selectServer(this.serverListSelector.getSize() + 1);
/* 281 */               this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
/*     */             } else {
/* 283 */               selectServer(-1);
/*     */             } 
/*     */           }
/*     */         } else {
/* 287 */           selectServer(-1);
/*     */         } 
/* 289 */       } else if (keyCode != 28 && keyCode != 156) {
/* 290 */         super.keyTyped(typedChar, keyCode);
/*     */       } else {
/* 292 */         actionPerformed(this.buttonList.get(2));
/*     */       } 
/*     */     } else {
/* 295 */       super.keyTyped(typedChar, keyCode);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 304 */     this.hoveringText = null;
/* 305 */     drawDefaultBackground();
/* 306 */     this.serverListSelector.drawScreen(mouseX, mouseY, partialTicks);
/* 307 */     drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.title", new Object[0]), this.width / 2, 20, 16777215);
/* 308 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 310 */     if (this.hoveringText != null) {
/* 311 */       drawHoveringText(Lists.newArrayList(Splitter.on("\n").split(this.hoveringText)), mouseX, mouseY);
/*     */     }
/*     */   }
/*     */   
/*     */   public void connectToSelected() {
/* 316 */     GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (this.serverListSelector.func_148193_k() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());
/*     */     
/* 318 */     if (guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/* 319 */       connectToServer(((ServerListEntryNormal)guilistextended$iguilistentry).getServerData());
/* 320 */     } else if (guilistextended$iguilistentry instanceof ServerListEntryLanDetected) {
/* 321 */       LanServerDetector.LanServer lanserverdetector$lanserver = ((ServerListEntryLanDetected)guilistextended$iguilistentry).getLanServer();
/* 322 */       connectToServer(new ServerData(lanserverdetector$lanserver.getServerMotd(), lanserverdetector$lanserver.getServerIpPort(), true));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void connectToServer(ServerData server) {
/* 327 */     this.mc.displayGuiScreen((GuiScreen)new GuiConnecting(this, this.mc, server));
/*     */   }
/*     */   
/*     */   public void selectServer(int index) {
/* 331 */     this.serverListSelector.setSelectedSlotIndex(index);
/* 332 */     GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (index < 0) ? null : this.serverListSelector.getListEntry(index);
/* 333 */     this.btnSelectServer.enabled = false;
/* 334 */     this.btnEditServer.enabled = false;
/* 335 */     this.btnDeleteServer.enabled = false;
/*     */     
/* 337 */     if (guilistextended$iguilistentry != null && !(guilistextended$iguilistentry instanceof ServerListEntryLanScan)) {
/* 338 */       this.btnSelectServer.enabled = true;
/*     */       
/* 340 */       if (guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/* 341 */         this.btnEditServer.enabled = true;
/* 342 */         this.btnDeleteServer.enabled = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public OldServerPinger getOldServerPinger() {
/* 348 */     return this.oldServerPinger;
/*     */   }
/*     */   
/*     */   public void setHoveringText(String p_146793_1_) {
/* 352 */     this.hoveringText = p_146793_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 359 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 360 */     this.serverListSelector.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 367 */     super.mouseReleased(mouseX, mouseY, state);
/* 368 */     this.serverListSelector.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */   
/*     */   public ServerList getServerList() {
/* 372 */     return this.savedServerList;
/*     */   }
/*     */   
/*     */   public static boolean func_175392_a(ServerListEntryNormal p_175392_1_, int p_175392_2_) {
/* 376 */     return (p_175392_2_ > 0);
/*     */   }
/*     */   
/*     */   public boolean func_175394_b(ServerListEntryNormal p_175394_1_, int p_175394_2_) {
/* 380 */     return (p_175394_2_ < this.savedServerList.countServers() - 1);
/*     */   }
/*     */   
/*     */   public void func_175391_a(ServerListEntryNormal p_175391_1_, int p_175391_2_, boolean p_175391_3_) {
/* 384 */     int i = p_175391_3_ ? 0 : (p_175391_2_ - 1);
/* 385 */     this.savedServerList.swapServers(p_175391_2_, i);
/*     */     
/* 387 */     if (this.serverListSelector.func_148193_k() == p_175391_2_) {
/* 388 */       selectServer(i);
/*     */     }
/*     */     
/* 391 */     this.serverListSelector.func_148195_a(this.savedServerList);
/*     */   }
/*     */   
/*     */   public void func_175393_b(ServerListEntryNormal p_175393_1_, int p_175393_2_, boolean p_175393_3_) {
/* 395 */     int i = p_175393_3_ ? (this.savedServerList.countServers() - 1) : (p_175393_2_ + 1);
/* 396 */     this.savedServerList.swapServers(p_175393_2_, i);
/*     */     
/* 398 */     if (this.serverListSelector.func_148193_k() == p_175393_2_) {
/* 399 */       selectServer(i);
/*     */     }
/*     */     
/* 402 */     this.serverListSelector.func_148195_a(this.savedServerList);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiMultiplayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */