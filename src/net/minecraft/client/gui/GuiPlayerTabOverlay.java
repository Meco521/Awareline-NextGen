/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import awareline.main.mod.implement.globals.TabListAnimations;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.font.FontUtils;
/*     */ import awareline.main.ui.font.fastuni.FontLoader;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import com.google.common.collect.ComparisonChain;
/*     */ import com.google.common.collect.Ordering;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*     */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ 
/*     */ public class GuiPlayerTabOverlay extends Gui {
/*  30 */   public static final Ordering<NetworkPlayerInfo> field_175252_a = Ordering.from(new PlayerComparator());
/*     */   
/*     */   private final Minecraft mc;
/*     */   
/*     */   private final GuiIngame guiIngame;
/*     */   
/*     */   public float animation;
/*     */   
/*     */   public boolean animationDone;
/*     */   
/*     */   public float hue;
/*     */   
/*     */   boolean isBeingRendered;
/*     */   private IChatComponent footer;
/*     */   private IChatComponent header;
/*     */   private long lastTimeOpened;
/*     */   
/*     */   public GuiPlayerTabOverlay(Minecraft mcIn, GuiIngame guiIngameIn) {
/*  48 */     this.mc = mcIn;
/*  49 */     this.guiIngame = guiIngameIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
/*  56 */     return (networkPlayerInfoIn.getDisplayName() != null) ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updatePlayerList(boolean willBeRendered) {
/*  64 */     if (willBeRendered && !this.isBeingRendered) {
/*  65 */       this.lastTimeOpened = Minecraft.getSystemTime();
/*     */     }
/*     */     
/*  68 */     this.isBeingRendered = willBeRendered;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderPlayerlist(int width, Scoreboard scoreboardIn, ScoreObjective scoreObjectiveIn) {
/*     */     int l;
/*  75 */     NetHandlerPlayClient nethandlerplayclient = this.mc.thePlayer.sendQueue;
/*  76 */     List<NetworkPlayerInfo> list = field_175252_a.sortedCopy(nethandlerplayclient.getPlayerInfoMap());
/*  77 */     int i = 0;
/*  78 */     int j = 0;
/*     */     
/*  80 */     for (NetworkPlayerInfo networkplayerinfo : list) {
/*  81 */       int k = FontLoader.PF16.getStringWidth(getPlayerName(networkplayerinfo));
/*     */       
/*  83 */       i = Math.max(i, k);
/*     */       
/*  85 */       if (scoreObjectiveIn != null && scoreObjectiveIn.getRenderType() != IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
/*     */         
/*  87 */         k = FontLoader.PF16.getStringWidth(" " + scoreboardIn.getValueFromObjective(networkplayerinfo.getGameProfile().getName(), scoreObjectiveIn).getScorePoints());
/*     */         
/*  89 */         j = Math.max(j, k);
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     list = list.subList(0, Math.min(list.size(), 80));
/*  94 */     int l3 = list.size();
/*  95 */     int i4 = l3;
/*     */     
/*     */     int j4;
/*  98 */     for (j4 = 1; i4 > 20; i4 = (l3 + j4 - 1) / j4) {
/*  99 */       j4++;
/*     */     }
/*     */     
/* 102 */     boolean flag = (this.mc.isIntegratedServerRunning() || this.mc.getNetHandler().getNetworkManager().getIsencrypted());
/*     */ 
/*     */     
/* 105 */     if (scoreObjectiveIn != null) {
/* 106 */       if (scoreObjectiveIn.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
/* 107 */         l = 90;
/*     */       } else {
/* 109 */         l = j;
/*     */       } 
/*     */     } else {
/* 112 */       l = 0;
/*     */     } 
/*     */     
/* 115 */     int i1 = Math.min(j4 * ((flag ? 9 : 0) + i + l + 13), width - 50) / j4;
/* 116 */     int j1 = width / 2 - (i1 * j4 + (j4 - 1) * 5) / 2;
/* 117 */     int l1 = i1 * j4 + (j4 - 1) * 5;
/* 118 */     List<String> list1 = null;
/* 119 */     List<String> list2 = null;
/*     */     
/* 121 */     if (this.header != null) {
/* 122 */       list1 = FontUtils.listFormattedStringToWidth(FontLoader.PF16, this.header.getFormattedText(), width - 50);
/*     */       
/* 124 */       for (String s : list1) {
/* 125 */         l1 = Math.max(l1, FontLoader.PF16.getStringWidth(s));
/*     */       }
/*     */     } 
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
/* 139 */     if (this.footer != null) {
/* 140 */       list2 = FontUtils.listFormattedStringToWidth(FontLoader.PF16, this.footer.getFormattedText(), width - 50);
/*     */       
/* 142 */       for (String s2 : list2) {
/* 143 */         l1 = Math.max(l1, FontLoader.PF16.getStringWidth(s2));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     if (TabListAnimations.getInstance.isEnabled()) {
/* 156 */       float target = this.isBeingRendered ? ((Double)TabListAnimations.getInstance.maxDownY.get()).floatValue() : -(list.size() / j4 * 10 + ((list1 != null) ? (list1.size() * 10) : 0) + ((list2 != null) ? (list2.size() * 10) : 0));
/* 157 */       this.animation = AnimationUtil.getAnimationStateFlux(this.animation, target, Math.max(10.0F, Math.abs(this.animation - target) * 35.0F) * 0.3F);
/*     */       
/* 159 */       this.animationDone = (target == this.animation && target < 0.0F);
/*     */     } 
/* 161 */     float k1 = TabListAnimations.getInstance.isEnabled() ? this.animation : 10.0F;
/* 162 */     if (list1 != null) {
/* 163 */       drawRect((width / 2 - l1 / 2 - 1), (k1 - 1.0F), (width / 2 + l1 / 2 + 1), (k1 + (list1.size() * this.mc.fontRendererObj.FONT_HEIGHT)), -2147483648);
/*     */       
/* 165 */       for (String s3 : list1) {
/* 166 */         int i2 = FontLoader.PF16.getStringWidth(s3);
/* 167 */         FontLoader.PF16.drawStringWithShadow(s3, (width / 2 - i2 / 2), k1, -1);
/*     */ 
/*     */ 
/*     */         
/* 171 */         k1 += this.mc.fontRendererObj.FONT_HEIGHT;
/*     */       } 
/*     */       
/* 174 */       k1++;
/*     */     } 
/*     */     
/* 177 */     drawRect((width / 2 - l1 / 2 - 1), (k1 - 1.0F), (width / 2 + l1 / 2 + 1), (k1 + (i4 * 9)), -2147483648);
/*     */     
/* 179 */     for (int k4 = 0; k4 < l3; k4++) {
/* 180 */       int l4 = k4 / i4;
/* 181 */       int i5 = k4 % i4;
/* 182 */       int j2 = j1 + l4 * i1 + l4 * 5;
/* 183 */       float k2 = k1 + (i5 * 9);
/* 184 */       drawRect(j2, k2, (j2 + i1), (k2 + 8.0F), 553648127);
/* 185 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 186 */       GlStateManager.enableAlpha();
/* 187 */       GlStateManager.enableBlend();
/* 188 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*     */       
/* 190 */       if (k4 < list.size()) {
/* 191 */         NetworkPlayerInfo networkplayerinfo1 = list.get(k4);
/* 192 */         String s1 = getPlayerName(networkplayerinfo1);
/* 193 */         GameProfile gameprofile = networkplayerinfo1.getGameProfile();
/*     */         
/* 195 */         if (flag) {
/* 196 */           EntityPlayer entityplayer = this.mc.theWorld.getPlayerEntityByUUID(gameprofile.getId());
/* 197 */           boolean flag1 = (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.CAPE) && (gameprofile.getName().equals("Dinnerbone") || gameprofile.getName().equals("Grumm")));
/* 198 */           this.mc.getTextureManager().bindTexture(networkplayerinfo1.getLocationSkin());
/* 199 */           int l2 = 8 + (flag1 ? 8 : 0);
/* 200 */           int i3 = 8 * (flag1 ? -1 : 1);
/* 201 */           RenderUtil.drawScaledCustomSizeModalRect(j2, k2, 8.0F, l2, 8, i3, 8.0F, 8.0F, 64.0F, 64.0F);
/*     */           
/* 203 */           if (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.HAT)) {
/* 204 */             int j3 = 8 + (flag1 ? 8 : 0);
/* 205 */             int k3 = 8 * (flag1 ? -1 : 1);
/* 206 */             RenderUtil.drawScaledCustomSizeModalRect(j2, k2, 40.0F, j3, 8, k3, 8.0F, 8.0F, 64.0F, 64.0F);
/*     */           } 
/*     */           
/* 209 */           j2 += 9;
/*     */         } 
/*     */         
/* 212 */         if (networkplayerinfo1.getGameType() == WorldSettings.GameType.SPECTATOR) {
/* 213 */           s1 = EnumChatFormatting.ITALIC + s1;
/* 214 */           FontLoader.PF16.drawStringWithShadow(s1, j2, k2 + 2.0F, -1862270977);
/*     */         } else {
/* 216 */           FontLoader.PF16.drawStringWithShadow(s1, j2, k2 + 2.0F, -1);
/*     */         } 
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
/* 231 */         if (scoreObjectiveIn != null && networkplayerinfo1.getGameType() != WorldSettings.GameType.SPECTATOR) {
/* 232 */           int k5 = j2 + i + 1;
/* 233 */           int l5 = k5 + l;
/*     */           
/* 235 */           if (l5 - k5 > 5) {
/* 236 */             drawScoreboardValues(scoreObjectiveIn, k2, gameprofile.getName(), k5, l5, networkplayerinfo1);
/*     */           }
/*     */         } 
/*     */         
/* 240 */         drawPing(i1, j2 - (flag ? 9 : 0), k2, networkplayerinfo1);
/*     */       } 
/*     */     } 
/*     */     
/* 244 */     if (list2 != null) {
/* 245 */       k1 = k1 + (i4 * 9) + 1.0F;
/* 246 */       drawRect((width / 2 - l1 / 2 - 1), (k1 - 1.0F), (width / 2 + l1 / 2 + 1), (k1 + (list2.size() * this.mc.fontRendererObj.FONT_HEIGHT)), -2147483648);
/*     */       
/* 248 */       for (String s4 : list2) {
/* 249 */         int j5 = FontLoader.PF16.getStringWidth(s4);
/* 250 */         FontLoader.PF16.drawStringWithShadow(s4, (width / 2 - j5 / 2), k1, -1);
/*     */ 
/*     */         
/* 253 */         k1 += this.mc.fontRendererObj.FONT_HEIGHT;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drawPing(int p_175245_1_, int p_175245_2_, float p_175245_3_, NetworkPlayerInfo networkPlayerInfoIn) {
/* 259 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 260 */     Minecraft.getMinecraft().getTextureManager().bindTexture(icons);
/* 261 */     int i = 0;
/* 262 */     int j = 0;
/*     */     
/* 264 */     if (networkPlayerInfoIn.getResponseTime() < 0) {
/* 265 */       j = 5;
/* 266 */     } else if (networkPlayerInfoIn.getResponseTime() < 150) {
/* 267 */       j = 0;
/* 268 */     } else if (networkPlayerInfoIn.getResponseTime() < 300) {
/* 269 */       j = 1;
/* 270 */     } else if (networkPlayerInfoIn.getResponseTime() < 600) {
/* 271 */       j = 2;
/* 272 */     } else if (networkPlayerInfoIn.getResponseTime() < 1000) {
/* 273 */       j = 3;
/*     */     } else {
/* 275 */       j = 4;
/*     */     } 
/*     */     
/* 278 */     this.zLevel += 100.0F;
/* 279 */     drawTexturedModalRect((p_175245_2_ + p_175245_1_ - 11), p_175245_3_, 0, 176 + (j << 3), 10, 8);
/* 280 */     this.zLevel -= 100.0F;
/*     */   }
/*     */   
/*     */   private void drawScoreboardValues(ScoreObjective p_175247_1_, float p_175247_2_, String p_175247_3_, int p_175247_4_, int p_175247_5_, NetworkPlayerInfo p_175247_6_) {
/* 284 */     int i = p_175247_1_.getScoreboard().getValueFromObjective(p_175247_3_, p_175247_1_).getScorePoints();
/*     */     
/* 286 */     if (p_175247_1_.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
/* 287 */       this.mc.getTextureManager().bindTexture(icons);
/*     */       
/* 289 */       if (this.lastTimeOpened == p_175247_6_.func_178855_p()) {
/* 290 */         if (i < p_175247_6_.func_178835_l()) {
/* 291 */           p_175247_6_.func_178846_a(Minecraft.getSystemTime());
/* 292 */           p_175247_6_.func_178844_b((this.guiIngame.getUpdateCounter() + 20));
/* 293 */         } else if (i > p_175247_6_.func_178835_l()) {
/* 294 */           p_175247_6_.func_178846_a(Minecraft.getSystemTime());
/* 295 */           p_175247_6_.func_178844_b((this.guiIngame.getUpdateCounter() + 10));
/*     */         } 
/*     */       }
/*     */       
/* 299 */       if (Minecraft.getSystemTime() - p_175247_6_.func_178847_n() > 1000L || this.lastTimeOpened != p_175247_6_.func_178855_p()) {
/* 300 */         p_175247_6_.func_178836_b(i);
/* 301 */         p_175247_6_.func_178857_c(i);
/* 302 */         p_175247_6_.func_178846_a(Minecraft.getSystemTime());
/*     */       } 
/*     */       
/* 305 */       p_175247_6_.func_178843_c(this.lastTimeOpened);
/* 306 */       p_175247_6_.func_178836_b(i);
/* 307 */       int j = MathHelper.ceiling_float_int(Math.max(i, p_175247_6_.func_178860_m()) / 2.0F);
/* 308 */       int k = Math.max(MathHelper.ceiling_float_int((i / 2)), Math.max(MathHelper.ceiling_float_int((p_175247_6_.func_178860_m() / 2)), 10));
/* 309 */       boolean flag = (p_175247_6_.func_178858_o() > this.guiIngame.getUpdateCounter() && (p_175247_6_.func_178858_o() - this.guiIngame.getUpdateCounter()) / 3L % 2L == 1L);
/*     */       
/* 311 */       if (j > 0) {
/* 312 */         float f = Math.min((p_175247_5_ - p_175247_4_ - 4) / k, 9.0F);
/*     */         
/* 314 */         if (f > 3.0F) {
/* 315 */           for (int l = j; l < k; l++) {
/* 316 */             drawTexturedModalRect(p_175247_4_ + l * f, p_175247_2_, flag ? 25 : 16, 0, 9, 9);
/*     */           }
/*     */           
/* 319 */           for (int j1 = 0; j1 < j; j1++) {
/* 320 */             drawTexturedModalRect(p_175247_4_ + j1 * f, p_175247_2_, flag ? 25 : 16, 0, 9, 9);
/*     */             
/* 322 */             if (flag) {
/* 323 */               if ((j1 << 1) + 1 < p_175247_6_.func_178860_m()) {
/* 324 */                 drawTexturedModalRect(p_175247_4_ + j1 * f, p_175247_2_, 70, 0, 9, 9);
/*     */               }
/*     */               
/* 327 */               if ((j1 << 1) + 1 == p_175247_6_.func_178860_m()) {
/* 328 */                 drawTexturedModalRect(p_175247_4_ + j1 * f, p_175247_2_, 79, 0, 9, 9);
/*     */               }
/*     */             } 
/*     */             
/* 332 */             if ((j1 << 1) + 1 < i) {
/* 333 */               drawTexturedModalRect(p_175247_4_ + j1 * f, p_175247_2_, (j1 >= 10) ? 160 : 52, 0, 9, 9);
/*     */             }
/*     */             
/* 336 */             if ((j1 << 1) + 1 == i) {
/* 337 */               drawTexturedModalRect(p_175247_4_ + j1 * f, p_175247_2_, (j1 >= 10) ? 169 : 61, 0, 9, 9);
/*     */             }
/*     */           } 
/*     */         } else {
/* 341 */           float f1 = MathHelper.clamp_float(i / 20.0F, 0.0F, 1.0F);
/* 342 */           int i1 = (int)((1.0F - f1) * 255.0F) << 16 | (int)(f1 * 255.0F) << 8;
/* 343 */           String s = String.valueOf(i / 2.0F);
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
/* 355 */           if (p_175247_5_ - FontLoader.PF16.getStringWidth(s + "hp") >= p_175247_4_) {
/* 356 */             s = s + "hp";
/*     */           }
/*     */           
/* 359 */           FontLoader.PF16.drawStringWithShadow(s, ((p_175247_5_ + p_175247_4_) / 2 - FontLoader.PF16.getStringWidth(s) / 2), p_175247_2_, i1);
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 364 */       String s1 = EnumChatFormatting.YELLOW + String.valueOf(i);
/* 365 */       FontLoader.PF16.drawStringWithShadow(s1, (p_175247_5_ - FontLoader.PF16.getStringWidth(s1)), p_175247_2_, 16777215);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFooter(IChatComponent footerIn) {
/* 371 */     this.footer = footerIn;
/*     */   }
/*     */   
/*     */   public void setHeader(IChatComponent headerIn) {
/* 375 */     this.header = headerIn;
/*     */   }
/*     */   
/*     */   public void resetFooterHeader() {
/* 379 */     this.header = null;
/* 380 */     this.footer = null;
/*     */   }
/*     */ 
/*     */   
/*     */   static class PlayerComparator
/*     */     implements Comparator<NetworkPlayerInfo>
/*     */   {
/*     */     public int compare(NetworkPlayerInfo p_compare_1_, NetworkPlayerInfo p_compare_2_) {
/* 388 */       ScorePlayerTeam scoreplayerteam = p_compare_1_.getPlayerTeam();
/* 389 */       ScorePlayerTeam scoreplayerteam1 = p_compare_2_.getPlayerTeam();
/* 390 */       return ComparisonChain.start().compareTrueFirst((p_compare_1_.getGameType() != WorldSettings.GameType.SPECTATOR), (p_compare_2_.getGameType() != WorldSettings.GameType.SPECTATOR)).compare((scoreplayerteam != null) ? scoreplayerteam.getRegisteredName() : "", (scoreplayerteam1 != null) ? scoreplayerteam1.getRegisteredName() : "").compare(p_compare_1_.getGameProfile().getName(), p_compare_2_.getGameProfile().getName()).result();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiPlayerTabOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */