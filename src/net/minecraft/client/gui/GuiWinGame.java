/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.audio.MusicTicker;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.Charsets;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GuiWinGame
/*     */   extends GuiScreen {
/*  25 */   private static final Logger logger = LogManager.getLogger();
/*  26 */   private static final ResourceLocation MINECRAFT_LOGO = new ResourceLocation("textures/gui/title/minecraft.png");
/*  27 */   private static final ResourceLocation VIGNETTE_TEXTURE = new ResourceLocation("textures/misc/vignette.png");
/*     */   private int field_146581_h;
/*     */   private List<String> field_146582_i;
/*     */   private int field_146579_r;
/*  31 */   private final float field_146578_s = 0.5F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  38 */     MusicTicker musicticker = this.mc.getMusicTicker();
/*  39 */     SoundHandler soundhandler = this.mc.getSoundHandler();
/*     */     
/*  41 */     if (this.field_146581_h == 0) {
/*     */       
/*  43 */       musicticker.func_181557_a();
/*  44 */       musicticker.func_181558_a(MusicTicker.MusicType.CREDITS);
/*  45 */       soundhandler.resumeSounds();
/*     */     } 
/*     */     
/*  48 */     soundhandler.update();
/*  49 */     this.field_146581_h++;
/*  50 */     getClass(); float f = (this.field_146579_r + this.height + this.height + 24) / 0.5F;
/*     */     
/*  52 */     if (this.field_146581_h > f)
/*     */     {
/*  54 */       sendRespawnPacket();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) {
/*  63 */     if (keyCode == 1)
/*     */     {
/*  65 */       sendRespawnPacket();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendRespawnPacket() {
/*  71 */     this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
/*  72 */     this.mc.displayGuiScreen((GuiScreen)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/*  80 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  89 */     if (this.field_146582_i == null) {
/*     */       
/*  91 */       this.field_146582_i = Lists.newArrayList();
/*     */ 
/*     */       
/*     */       try {
/*  95 */         String s = "";
/*  96 */         String s1 = String.valueOf(EnumChatFormatting.WHITE) + EnumChatFormatting.OBFUSCATED + EnumChatFormatting.GREEN + EnumChatFormatting.AQUA;
/*  97 */         int i = 274;
/*  98 */         InputStream inputstream = this.mc.getResourceManager().getResource(new ResourceLocation("texts/end.txt")).getInputStream();
/*  99 */         BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream, Charsets.UTF_8));
/* 100 */         Random random = new Random(8124371L);
/*     */         
/* 102 */         while ((s = bufferedreader.readLine()) != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 107 */           for (s = s.replaceAll("PLAYERNAME", this.mc.getSession().getUsername()); s.contains(s1); s = s2 + EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, random.nextInt(4) + 3) + s3) {
/*     */             
/* 109 */             int j = s.indexOf(s1);
/* 110 */             String s2 = s.substring(0, j);
/* 111 */             String s3 = s.substring(j + s1.length());
/*     */           } 
/*     */           
/* 114 */           this.field_146582_i.addAll(this.mc.fontRendererObj.listFormattedStringToWidth(s, i));
/* 115 */           this.field_146582_i.add("");
/*     */         } 
/*     */         
/* 118 */         inputstream.close();
/*     */         
/* 120 */         for (int k = 0; k < 8; k++)
/*     */         {
/* 122 */           this.field_146582_i.add("");
/*     */         }
/*     */         
/* 125 */         inputstream = this.mc.getResourceManager().getResource(new ResourceLocation("texts/credits.txt")).getInputStream();
/* 126 */         bufferedreader = new BufferedReader(new InputStreamReader(inputstream, Charsets.UTF_8));
/*     */         
/* 128 */         while ((s = bufferedreader.readLine()) != null) {
/*     */           
/* 130 */           s = s.replaceAll("PLAYERNAME", this.mc.getSession().getUsername());
/* 131 */           s = s.replaceAll("\t", "    ");
/* 132 */           this.field_146582_i.addAll(this.mc.fontRendererObj.listFormattedStringToWidth(s, i));
/* 133 */           this.field_146582_i.add("");
/*     */         } 
/*     */         
/* 136 */         inputstream.close();
/* 137 */         this.field_146579_r = this.field_146582_i.size() * 12;
/*     */       }
/* 139 */       catch (Exception exception) {
/*     */         
/* 141 */         logger.error("Couldn't load credits", exception);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawWinGameScreen(int p_146575_1_, int p_146575_2_, float p_146575_3_) {
/* 148 */     Tessellator tessellator = Tessellator.getInstance();
/* 149 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 150 */     this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
/* 151 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 152 */     int i = this.width;
/* 153 */     getClass(); float f = 0.0F - (this.field_146581_h + p_146575_3_) * 0.5F * 0.5F;
/* 154 */     getClass(); float f1 = this.height - (this.field_146581_h + p_146575_3_) * 0.5F * 0.5F;
/* 155 */     float f2 = 0.015625F;
/* 156 */     float f3 = (this.field_146581_h + p_146575_3_ - 0.0F) * 0.02F;
/* 157 */     getClass(); float f4 = (this.field_146579_r + this.height + this.height + 24) / 0.5F;
/* 158 */     float f5 = (f4 - 20.0F - this.field_146581_h + p_146575_3_) * 0.005F;
/*     */     
/* 160 */     if (f5 < f3)
/*     */     {
/* 162 */       f3 = f5;
/*     */     }
/*     */     
/* 165 */     if (f3 > 1.0F)
/*     */     {
/* 167 */       f3 = 1.0F;
/*     */     }
/*     */     
/* 170 */     f3 *= f3;
/* 171 */     f3 = f3 * 96.0F / 255.0F;
/* 172 */     worldrenderer.pos(0.0D, this.height, this.zLevel).tex(0.0D, (f * f2)).color(f3, f3, f3, 1.0F).endVertex();
/* 173 */     worldrenderer.pos(i, this.height, this.zLevel).tex((i * f2), (f * f2)).color(f3, f3, f3, 1.0F).endVertex();
/* 174 */     worldrenderer.pos(i, 0.0D, this.zLevel).tex((i * f2), (f1 * f2)).color(f3, f3, f3, 1.0F).endVertex();
/* 175 */     worldrenderer.pos(0.0D, 0.0D, this.zLevel).tex(0.0D, (f1 * f2)).color(f3, f3, f3, 1.0F).endVertex();
/* 176 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 184 */     drawWinGameScreen(mouseX, mouseY, partialTicks);
/* 185 */     Tessellator tessellator = Tessellator.getInstance();
/* 186 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 187 */     int i = 274;
/* 188 */     int j = this.width / 2 - i / 2;
/* 189 */     int k = this.height + 50;
/* 190 */     getClass(); float f = -(this.field_146581_h + partialTicks) * 0.5F;
/* 191 */     GlStateManager.pushMatrix();
/* 192 */     GlStateManager.translate(0.0F, f, 0.0F);
/* 193 */     this.mc.getTextureManager().bindTexture(MINECRAFT_LOGO);
/* 194 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 195 */     drawTexturedModalRect(j, k, 0, 0, 155, 44);
/* 196 */     drawTexturedModalRect(j + 155, k, 0, 45, 155, 44);
/* 197 */     int l = k + 200;
/*     */     
/* 199 */     for (int i1 = 0; i1 < this.field_146582_i.size(); i1++) {
/*     */       
/* 201 */       if (i1 == this.field_146582_i.size() - 1) {
/*     */         
/* 203 */         float f1 = l + f - (this.height / 2 - 6);
/*     */         
/* 205 */         if (f1 < 0.0F)
/*     */         {
/* 207 */           GlStateManager.translate(0.0F, -f1, 0.0F);
/*     */         }
/*     */       } 
/*     */       
/* 211 */       if (l + f + 12.0F + 8.0F > 0.0F && l + f < this.height) {
/*     */         
/* 213 */         String s = this.field_146582_i.get(i1);
/*     */         
/* 215 */         if (s.startsWith("[C]")) {
/*     */           
/* 217 */           this.fontRendererObj.drawStringWithShadow(s.substring(3), (j + (i - this.fontRendererObj.getStringWidth(s.substring(3))) / 2), l, 16777215);
/*     */         }
/*     */         else {
/*     */           
/* 221 */           this.fontRendererObj.fontRandom.setSeed(i1 * 4238972211L + (this.field_146581_h / 4));
/* 222 */           this.fontRendererObj.drawStringWithShadow(s, j, l, 16777215);
/*     */         } 
/*     */       } 
/*     */       
/* 226 */       l += 12;
/*     */     } 
/*     */     
/* 229 */     GlStateManager.popMatrix();
/* 230 */     this.mc.getTextureManager().bindTexture(VIGNETTE_TEXTURE);
/* 231 */     GlStateManager.enableBlend();
/* 232 */     GlStateManager.blendFunc(0, 769);
/* 233 */     int j1 = this.width;
/* 234 */     int k1 = this.height;
/* 235 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 236 */     worldrenderer.pos(0.0D, k1, this.zLevel).tex(0.0D, 1.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 237 */     worldrenderer.pos(j1, k1, this.zLevel).tex(1.0D, 1.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 238 */     worldrenderer.pos(j1, 0.0D, this.zLevel).tex(1.0D, 0.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 239 */     worldrenderer.pos(0.0D, 0.0D, this.zLevel).tex(0.0D, 0.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 240 */     tessellator.draw();
/* 241 */     GlStateManager.disableBlend();
/* 242 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiWinGame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */