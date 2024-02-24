/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import awareline.main.ui.font.fastuni.FontLoader;
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufInputStream;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.base64.Base64;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.InputStream;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ServerListEntryNormal implements GuiListExtended.IGuiListEntry {
/*  29 */   private static final Logger logger = LogManager.getLogger();
/*  30 */   private static final ThreadPoolExecutor field_148302_b = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).build());
/*  31 */   private static final ResourceLocation UNKNOWN_SERVER = new ResourceLocation("textures/misc/unknown_server.png");
/*  32 */   private static final ResourceLocation SERVER_SELECTION_BUTTONS = new ResourceLocation("textures/gui/server_selection.png");
/*     */   
/*     */   final GuiMultiplayer owner;
/*     */   private final Minecraft mc;
/*     */   final ServerData server;
/*     */   private final ResourceLocation serverIcon;
/*     */   private String field_148299_g;
/*     */   private DynamicTexture field_148305_h;
/*     */   private long field_148298_f;
/*     */   
/*     */   protected ServerListEntryNormal(GuiMultiplayer p_i45048_1_, ServerData serverIn) {
/*  43 */     this.owner = p_i45048_1_;
/*  44 */     this.server = serverIn;
/*  45 */     this.mc = Minecraft.getMinecraft();
/*  46 */     this.serverIcon = new ResourceLocation("servers/" + serverIn.serverIP + "/icon");
/*  47 */     this.field_148305_h = (DynamicTexture)this.mc.getTextureManager().getTexture(this.serverIcon);
/*     */   }
/*     */   public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
/*     */     int l;
/*     */     String s1;
/*  52 */     if (!this.server.field_78841_f) {
/*     */       
/*  54 */       this.server.field_78841_f = true;
/*  55 */       this.server.pingToServer = -2L;
/*  56 */       this.server.serverMOTD = "";
/*  57 */       this.server.populationInfo = "";
/*  58 */       field_148302_b.execute(() -> {
/*     */ 
/*     */             
/*     */             try {
/*     */               this.owner.getOldServerPinger().ping(this.server);
/*  63 */             } catch (UnknownHostException var2) {
/*     */               this.server.pingToServer = -1L;
/*     */ 
/*     */               
/*     */               this.server.serverMOTD = EnumChatFormatting.DARK_RED + "Can't resolve hostname";
/*  68 */             } catch (Exception var3) {
/*     */               this.server.pingToServer = -1L;
/*     */               
/*     */               this.server.serverMOTD = EnumChatFormatting.DARK_RED + "Can't connect to server.";
/*     */             } 
/*     */           });
/*     */     } 
/*     */     
/*  76 */     boolean flag = (this.server.version > 47);
/*  77 */     boolean flag1 = (this.server.version < 47);
/*  78 */     boolean flag2 = (flag || flag1);
/*  79 */     FontLoader.PF16.drawString(this.server.serverName, (x + 32 + 3), y + 2, 16777215);
/*     */     
/*  81 */     List<String> list = this.mc.fontRendererObj.listFormattedStringToWidth(this.server.serverMOTD, listWidth - 32 - 2);
/*     */     
/*  83 */     for (int i = 0; i < Math.min(list.size(), 2); i++)
/*     */     {
/*  85 */       FontLoader.PF16.drawString(list.get(i), (x + 35), y + 14 + this.mc.fontRendererObj.FONT_HEIGHT * i, 8421504);
/*     */     }
/*     */ 
/*     */     
/*  89 */     String s2 = flag2 ? (EnumChatFormatting.DARK_RED + this.server.gameVersion) : this.server.populationInfo;
/*     */     
/*  91 */     int j = FontLoader.PF16.getStringWidth(s2);
/*  92 */     FontLoader.PF16.drawString(s2, (x + listWidth - j - 15 - 2), y + 3, 8421504);
/*     */     
/*  94 */     int k = 0;
/*  95 */     String s = null;
/*     */ 
/*     */ 
/*     */     
/*  99 */     if (flag2) {
/*     */       
/* 101 */       l = 5;
/* 102 */       s1 = flag ? "Client out of date!" : "Server out of date!";
/* 103 */       s = this.server.playerList;
/*     */     }
/* 105 */     else if (this.server.field_78841_f && this.server.pingToServer != -2L) {
/*     */       
/* 107 */       if (this.server.pingToServer < 0L) {
/*     */         
/* 109 */         l = 5;
/*     */       }
/* 111 */       else if (this.server.pingToServer < 150L) {
/*     */         
/* 113 */         l = 0;
/*     */       }
/* 115 */       else if (this.server.pingToServer < 300L) {
/*     */         
/* 117 */         l = 1;
/*     */       }
/* 119 */       else if (this.server.pingToServer < 600L) {
/*     */         
/* 121 */         l = 2;
/*     */       }
/* 123 */       else if (this.server.pingToServer < 1000L) {
/*     */         
/* 125 */         l = 3;
/*     */       }
/*     */       else {
/*     */         
/* 129 */         l = 4;
/*     */       } 
/*     */       
/* 132 */       if (this.server.pingToServer < 0L)
/*     */       {
/* 134 */         s1 = "(no connection)";
/*     */       }
/*     */       else
/*     */       {
/* 138 */         s1 = this.server.pingToServer + "ms";
/* 139 */         s = this.server.playerList;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 144 */       k = 1;
/* 145 */       l = (int)(Minecraft.getSystemTime() / 100L + (slotIndex * 2) & 0x7L);
/*     */       
/* 147 */       if (l > 4)
/*     */       {
/* 149 */         l = 8 - l;
/*     */       }
/*     */       
/* 152 */       s1 = "Pinging...";
/*     */     } 
/*     */     
/* 155 */     GlStateManager.enableAlpha();
/* 156 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 157 */     this.mc.getTextureManager().bindTexture(Gui.icons);
/* 158 */     Gui.drawModalRectWithCustomSizedTexture(x + listWidth - 15, y, (k * 10), (176 + l * 8), 10, 8, 256.0F, 256.0F);
/*     */     
/* 160 */     if (this.server.getBase64EncodedIconData() != null && !this.server.getBase64EncodedIconData().equals(this.field_148299_g)) {
/*     */       
/* 162 */       this.field_148299_g = this.server.getBase64EncodedIconData();
/* 163 */       prepareServerIcon();
/* 164 */       this.owner.getServerList().saveServerList();
/*     */     } 
/*     */     
/* 167 */     if (this.field_148305_h != null) {
/*     */       
/* 169 */       drawTextureAt(x, y, this.serverIcon);
/*     */     }
/*     */     else {
/*     */       
/* 173 */       drawTextureAt(x, y, UNKNOWN_SERVER);
/*     */     } 
/*     */     
/* 176 */     int i1 = mouseX - x;
/* 177 */     int j1 = mouseY - y;
/*     */     
/* 179 */     if (i1 >= listWidth - 15 && i1 <= listWidth - 5 && j1 >= 0 && j1 <= 8) {
/*     */       
/* 181 */       this.owner.setHoveringText(s1);
/*     */     }
/* 183 */     else if (i1 >= listWidth - j - 15 - 2 && i1 <= listWidth - 15 - 2 && j1 >= 0 && j1 <= 8) {
/*     */       
/* 185 */       this.owner.setHoveringText(s);
/*     */     } 
/*     */     
/* 188 */     if (this.mc.gameSettings.touchscreen || isSelected) {
/*     */       
/* 190 */       this.mc.getTextureManager().bindTexture(SERVER_SELECTION_BUTTONS);
/* 191 */       Gui.drawRect(x, y, x + 32, y + 32, -1601138544);
/* 192 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 193 */       int k1 = mouseX - x;
/* 194 */       int l1 = mouseY - y;
/*     */       
/* 196 */       if (func_178013_b())
/*     */       {
/* 198 */         if (k1 < 32 && k1 > 16) {
/*     */           
/* 200 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */         }
/*     */         else {
/*     */           
/* 204 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */         } 
/*     */       }
/*     */       
/* 208 */       if (GuiMultiplayer.func_175392_a(this, slotIndex))
/*     */       {
/* 210 */         if (k1 < 16 && l1 < 16) {
/*     */           
/* 212 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */         }
/*     */         else {
/*     */           
/* 216 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */         } 
/*     */       }
/*     */       
/* 220 */       if (this.owner.func_175394_b(this, slotIndex))
/*     */       {
/* 222 */         if (k1 < 16 && l1 > 16) {
/*     */           
/* 224 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */         }
/*     */         else {
/*     */           
/* 228 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 233 */     GlStateManager.disableAlpha();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawTextureAt(int p_178012_1_, int p_178012_2_, ResourceLocation p_178012_3_) {
/* 238 */     this.mc.getTextureManager().bindTexture(p_178012_3_);
/* 239 */     GlStateManager.enableBlend();
/* 240 */     Gui.drawModalRectWithCustomSizedTexture(p_178012_1_, p_178012_2_, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
/* 241 */     GlStateManager.disableBlend();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_178013_b() {
/* 246 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void prepareServerIcon() {
/* 251 */     if (this.server.getBase64EncodedIconData() == null) {
/*     */       
/* 253 */       this.mc.getTextureManager().deleteTexture(this.serverIcon);
/* 254 */       this.field_148305_h = null;
/*     */     } else {
/*     */       BufferedImage bufferedimage;
/*     */       
/* 258 */       ByteBuf bytebuf = Unpooled.copiedBuffer(this.server.getBase64EncodedIconData(), Charsets.UTF_8);
/* 259 */       ByteBuf bytebuf1 = Base64.decode(bytebuf);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 265 */         bufferedimage = TextureUtil.readBufferedImage((InputStream)new ByteBufInputStream(bytebuf1));
/* 266 */         Validate.validState((bufferedimage.getWidth() == 64), "Must be 64 pixels wide", new Object[0]);
/* 267 */         Validate.validState((bufferedimage.getHeight() == 64), "Must be 64 pixels high", new Object[0]);
/*     */       
/*     */       }
/* 270 */       catch (Throwable throwable) {
/*     */         
/* 272 */         logger.error("Invalid icon for server " + this.server.serverName + " (" + this.server.serverIP + ")", throwable);
/* 273 */         this.server.setBase64EncodedIconData((String)null);
/*     */       }
/*     */       finally {
/*     */         
/* 277 */         bytebuf.release();
/* 278 */         bytebuf1.release();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 284 */       if (this.field_148305_h == null) {
/*     */         
/* 286 */         this.field_148305_h = new DynamicTexture(bufferedimage.getWidth(), bufferedimage.getHeight());
/* 287 */         this.mc.getTextureManager().loadTexture(this.serverIcon, (ITextureObject)this.field_148305_h);
/*     */       } 
/*     */       
/* 290 */       bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), this.field_148305_h.getTextureData(), 0, bufferedimage.getWidth());
/* 291 */       this.field_148305_h.updateDynamicTexture();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
/* 300 */     if (p_148278_5_ <= 32) {
/*     */       
/* 302 */       if (p_148278_5_ < 32 && p_148278_5_ > 16 && func_178013_b()) {
/*     */         
/* 304 */         this.owner.selectServer(slotIndex);
/* 305 */         this.owner.connectToSelected();
/* 306 */         return true;
/*     */       } 
/*     */       
/* 309 */       if (p_148278_5_ < 16 && p_148278_6_ < 16 && GuiMultiplayer.func_175392_a(this, slotIndex)) {
/*     */         
/* 311 */         this.owner.func_175391_a(this, slotIndex, GuiScreen.isShiftKeyDown());
/* 312 */         return true;
/*     */       } 
/*     */       
/* 315 */       if (p_148278_5_ < 16 && p_148278_6_ > 16 && this.owner.func_175394_b(this, slotIndex)) {
/*     */         
/* 317 */         this.owner.func_175393_b(this, slotIndex, GuiScreen.isShiftKeyDown());
/* 318 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 322 */     this.owner.selectServer(slotIndex);
/*     */     
/* 324 */     if (Minecraft.getSystemTime() - this.field_148298_f < 250L)
/*     */     {
/* 326 */       this.owner.connectToSelected();
/*     */     }
/*     */     
/* 329 */     this.field_148298_f = Minecraft.getSystemTime();
/* 330 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerData getServerData() {
/* 346 */     return this.server;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\ServerListEntryNormal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */