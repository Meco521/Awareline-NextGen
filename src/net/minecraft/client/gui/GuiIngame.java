/*      */ package net.minecraft.client.gui;
/*      */ import awareline.main.Client;
/*      */ import awareline.main.event.Event;
/*      */ import awareline.main.event.EventManager;
/*      */ import awareline.main.event.events.ketaShaderCall.RenderGameOverlayEvent;
/*      */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*      */ import awareline.main.mod.implement.globals.AnimatedView;
/*      */ import awareline.main.mod.implement.globals.Shader;
/*      */ import awareline.main.mod.implement.globals.TabListAnimations;
/*      */ import awareline.main.mod.implement.globals.ToolTipsAnim;
/*      */ import awareline.main.mod.implement.visual.HUD;
/*      */ import awareline.main.mod.implement.visual.NoPumpkinHead;
/*      */ import awareline.main.mod.implement.visual.SetScoreboard;
/*      */ import awareline.main.mod.implement.visual.ctype.Crosshair;
/*      */ import awareline.main.mod.implement.visual.sucks.tenacityColor.ColorUtil;
/*      */ import awareline.main.ui.animations.TranslateUtil;
/*      */ import awareline.main.ui.font.fastuni.FontLoader;
/*      */ import awareline.main.ui.font.fontmanager.font.FontManager;
/*      */ import awareline.main.ui.gui.BlurBuffer;
/*      */ import awareline.main.utility.render.RoundedUtil;
/*      */ import awareline.main.utility.render.color.ColorManager;
/*      */ import awareline.main.utility.render.render.blur.util.GLUtil;
/*      */ import awareline.main.utility.shader.ketaUtils.render.BlurUtil;
/*      */ import com.google.common.collect.Iterables;
/*      */ import com.google.common.collect.Lists;
/*      */ import java.awt.Color;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.stream.Collectors;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.SharedMonsterAttributes;
/*      */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*      */ import net.minecraft.entity.boss.BossStatus;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.scoreboard.Score;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.Scoreboard;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumChatFormatting;
/*      */ import net.minecraft.util.FoodStats;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.world.border.WorldBorder;
/*      */ 
/*      */ public class GuiIngame extends Gui {
/*   65 */   private static final ResourceLocation vignetteTexPath = new ResourceLocation("textures/misc/vignette.png");
/*   66 */   private static final ResourceLocation widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
/*   67 */   private static final ResourceLocation pumpkinBlurTexPath = new ResourceLocation("textures/misc/pumpkinblur.png");
/*   68 */   private final Random rand = new Random();
/*      */ 
/*      */   
/*      */   private final Minecraft mc;
/*      */   
/*      */   private final RenderItem itemRenderer;
/*      */   
/*      */   private final GuiNewChat persistantChatGUI;
/*      */   
/*      */   private final GuiOverlayDebug overlayDebug;
/*      */   
/*      */   private final GuiSpectator spectatorGui;
/*      */   
/*      */   private final GuiPlayerTabOverlay overlayPlayerList;
/*      */   
/*   83 */   private final TranslateUtil translate = new TranslateUtil(0.0F, 0.0F);
/*      */ 
/*      */ 
/*      */   
/*   87 */   public float prevVignetteBrightness = 1.0F;
/*      */ 
/*      */   
/*      */   private int updateCounter;
/*      */   
/*   92 */   private String recordPlaying = "";
/*      */ 
/*      */ 
/*      */   
/*      */   private int recordPlayingUpFor;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean recordIsPlaying;
/*      */ 
/*      */ 
/*      */   
/*      */   private int remainingHighlightTicks;
/*      */ 
/*      */   
/*      */   private ItemStack highlightingItemStack;
/*      */ 
/*      */   
/*      */   private int titlesTimer;
/*      */ 
/*      */   
/*  113 */   private String displayedTitle = "";
/*      */ 
/*      */ 
/*      */   
/*  117 */   private String displayedSubTitle = "";
/*      */ 
/*      */   
/*      */   private int titleFadeIn;
/*      */ 
/*      */   
/*      */   private int titleDisplayTime;
/*      */ 
/*      */   
/*      */   private int titleFadeOut;
/*      */ 
/*      */   
/*      */   private int playerHealth;
/*      */ 
/*      */   
/*      */   private int lastPlayerHealth;
/*      */   
/*      */   private long lastSystemTime;
/*      */   
/*      */   private long healthUpdateCounter;
/*      */   
/*      */   private float lastX;
/*      */   
/*      */   private float lastY;
/*      */   
/*  142 */   private float anim = 0.0F; private float animUp; private float animDown; private float animItem;
/*      */   
/*      */   public GuiIngame(Minecraft mcIn) {
/*  145 */     this.mc = mcIn;
/*  146 */     this.itemRenderer = mcIn.getRenderItem();
/*  147 */     this.overlayDebug = new GuiOverlayDebug(mcIn);
/*  148 */     this.spectatorGui = new GuiSpectator(mcIn);
/*  149 */     this.persistantChatGUI = new GuiNewChat(mcIn);
/*      */     
/*  151 */     this.overlayPlayerList = new GuiPlayerTabOverlay(mcIn, this);
/*  152 */     setDefaultTitlesTimes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaultTitlesTimes() {
/*  159 */     this.titleFadeIn = 10;
/*  160 */     this.titleDisplayTime = 70;
/*  161 */     this.titleFadeOut = 20;
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderGameOverlay(float partialTicks) {
/*  166 */     AnimatedView module = AnimatedView.getInstance;
/*      */     
/*  168 */     float currentX = this.mc.thePlayer.rotationYaw;
/*  169 */     float currentY = this.mc.thePlayer.rotationPitch;
/*      */     
/*  171 */     float curX = 0.0F, curY = 0.0F;
/*  172 */     float diffX = currentX - this.lastX;
/*  173 */     float diffY = currentY - this.lastY;
/*  174 */     if (module.isEnabled()) {
/*  175 */       this.translate.interpolate(diffX * 10.0F, diffY * 10.0F, 0.1F);
/*  176 */       curX = this.translate.getX();
/*  177 */       curY = this.translate.getY();
/*      */     } 
/*      */     
/*  180 */     ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/*  181 */     int i = scaledresolution.getScaledWidth();
/*  182 */     int j = scaledresolution.getScaledHeight();
/*  183 */     this.mc.entityRenderer.setupOverlayRendering();
/*  184 */     GlStateManager.enableBlend();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  215 */     RenderGameOverlayEvent event = new RenderGameOverlayEvent(partialTicks, scaledresolution);
/*  216 */     EventManager.call((Event)event);
/*      */     
/*  218 */     GlStateManager.translate(-curX, -curY, 0.0F);
/*      */ 
/*      */     
/*  221 */     Shader.getInstance.blurScreen(scaledresolution);
/*      */     
/*  223 */     if (this.mc.playerController.isSpectator()) {
/*  224 */       this.spectatorGui.renderTooltip(scaledresolution, partialTicks);
/*      */     } else {
/*  226 */       renderTooltip(scaledresolution, partialTicks);
/*      */     } 
/*      */     
/*  229 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  230 */     this.mc.getTextureManager().bindTexture(icons);
/*  231 */     GlStateManager.translate(curX, curY, 0.0F);
/*  232 */     GlStateManager.enableBlend();
/*      */     
/*  234 */     if (showCrosshair()) {
/*  235 */       GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
/*  236 */       GlStateManager.enableAlpha();
/*  237 */       drawTexturedModalRect(i / 2 - 7, j / 2 - 7, 0, 0, 16, 16);
/*      */     } 
/*  239 */     GlStateManager.translate(-curX, -curY, 0.0F);
/*  240 */     GlStateManager.translate(-curX, -curY, 0.0F);
/*  241 */     GlStateManager.enableAlpha();
/*  242 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  243 */     this.mc.mcProfiler.startSection("bossHealth");
/*      */     
/*  245 */     this.mc.mcProfiler.endSection();
/*  246 */     GlStateManager.pushMatrix();
/*      */     
/*  248 */     if (this.mc.playerController.shouldDrawHUD()) {
/*  249 */       renderPlayerStats(scaledresolution);
/*      */     }
/*  251 */     GlStateManager.popMatrix();
/*  252 */     GlStateManager.disableBlend();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  275 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  289 */     if (this.mc.gameSettings.heldItemTooltips && !this.mc.playerController.isSpectator()) {
/*  290 */       renderSelectedItem(scaledresolution);
/*  291 */     } else if (this.mc.thePlayer.isSpectator()) {
/*  292 */       this.spectatorGui.renderSelectedItem(scaledresolution);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  301 */     if (this.mc.gameSettings.showDebugInfo) {
/*  302 */       this.overlayDebug.renderDebugInfo(scaledresolution);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  337 */     if (this.titlesTimer > 0) {
/*  338 */       this.mc.mcProfiler.startSection("titleAndSubtitle");
/*  339 */       float f3 = this.titlesTimer - partialTicks;
/*  340 */       int i2 = 255;
/*      */       
/*  342 */       if (this.titlesTimer > this.titleFadeOut + this.titleDisplayTime) {
/*  343 */         float f4 = (this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut) - f3;
/*  344 */         i2 = (int)(f4 * 255.0F / this.titleFadeIn);
/*      */       } 
/*      */       
/*  347 */       if (this.titlesTimer <= this.titleFadeOut) {
/*  348 */         i2 = (int)(f3 * 255.0F / this.titleFadeOut);
/*      */       }
/*      */       
/*  351 */       i2 = MathHelper.clamp_int(i2, 0, 255);
/*      */       
/*  353 */       if (i2 > 8) {
/*  354 */         GlStateManager.pushMatrix();
/*  355 */         GlStateManager.translate((i / 2), (j / 2), 0.0F);
/*  356 */         GlStateManager.enableBlend();
/*  357 */         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  358 */         GlStateManager.pushMatrix();
/*  359 */         GlStateManager.scale(4.0F, 4.0F, 4.0F);
/*  360 */         int j2 = i2 << 24 & 0xFF000000;
/*  361 */         getFontRenderer().drawString(this.displayedTitle, (-getFontRenderer().getStringWidth(this.displayedTitle) / 2), -10.0F, 0xFFFFFF | j2, true);
/*  362 */         GlStateManager.popMatrix();
/*  363 */         GlStateManager.pushMatrix();
/*  364 */         GlStateManager.scale(2.0F, 2.0F, 2.0F);
/*  365 */         getFontRenderer().drawString(this.displayedSubTitle, (-getFontRenderer().getStringWidth(this.displayedSubTitle) / 2), 5.0F, 0xFFFFFF | j2, true);
/*  366 */         GlStateManager.popMatrix();
/*  367 */         GlStateManager.disableBlend();
/*  368 */         GlStateManager.popMatrix();
/*      */       } 
/*      */       
/*  371 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */     
/*  374 */     Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
/*  375 */     ScoreObjective scoreobjective = null;
/*  376 */     ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(this.mc.thePlayer.getName());
/*      */     
/*  378 */     if (scoreplayerteam != null) {
/*  379 */       int i1 = scoreplayerteam.getChatFormat().getColorIndex();
/*      */       
/*  381 */       if (i1 >= 0) {
/*  382 */         scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + i1);
/*      */       }
/*      */     } 
/*      */     
/*  386 */     ScoreObjective scoreobjective1 = (scoreobjective != null) ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);
/*      */     
/*  388 */     if (scoreobjective1 != null) {
/*  389 */       renderScoreboard(scoreobjective1, scaledresolution);
/*      */     }
/*      */     
/*  392 */     GlStateManager.enableBlend();
/*  393 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  394 */     GlStateManager.disableAlpha();
/*  395 */     GlStateManager.pushMatrix();
/*  396 */     GlStateManager.translate(0.0F, (j - 48), 0.0F);
/*  397 */     this.mc.mcProfiler.startSection("chat");
/*  398 */     this.persistantChatGUI.drawChat(this.updateCounter);
/*  399 */     this.mc.mcProfiler.endSection();
/*  400 */     GlStateManager.popMatrix();
/*  401 */     scoreobjective1 = scoreboard.getObjectiveInDisplaySlot(0);
/*      */     
/*  403 */     if (!this.mc.isIntegratedServerRunning() || this.mc.thePlayer.sendQueue.getPlayerInfoMap().size() > 1 || scoreobjective1 != null) {
/*      */       
/*  405 */       this.overlayPlayerList.updatePlayerList(this.mc.gameSettings.keyBindPlayerList.isKeyDown());
/*      */       
/*  407 */       if (this.mc.gameSettings.keyBindPlayerList.isKeyDown()) {
/*  408 */         this.overlayPlayerList.renderPlayerlist(i, scoreboard, scoreobjective1);
/*  409 */       } else if (!this.overlayPlayerList.isBeingRendered && !this.overlayPlayerList.animationDone && 
/*  410 */         TabListAnimations.getInstance.isEnabled()) {
/*  411 */         this.overlayPlayerList.renderPlayerlist(i, scoreboard, scoreobjective1);
/*      */       } 
/*      */     } else {
/*      */       
/*  415 */       this.overlayPlayerList.updatePlayerList(false);
/*      */     } 
/*      */ 
/*      */     
/*  419 */     drawHUD(scaledresolution, partialTicks);
/*      */ 
/*      */ 
/*      */     
/*  423 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  424 */     GlStateManager.disableLighting();
/*  425 */     GlStateManager.enableAlpha();
/*  426 */     GlStateManager.translate(curX, curY, 0.0F);
/*  427 */     this.lastX = currentX;
/*  428 */     this.lastY = currentY;
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawHUD(ScaledResolution scaledresolution, float partialTicks) {
/*  433 */     if (HUD.getInstance != null && HUD.getInstance.isEnabled()) {
/*  434 */       if (((Boolean)HUD.getInstance.blur.get()).booleanValue()) {
/*  435 */         BlurBuffer.updateBlurBuffer(true);
/*  436 */         BlurUtil.onRenderGameOverlay(this.mc.getFramebuffer(), new ScaledResolution(this.mc));
/*      */       } else {
/*  438 */         BlurBuffer.updateBlurBuffer(false);
/*      */       } 
/*      */     }
/*  441 */     EventManager.call((Event)new EventRender2D(scaledresolution, partialTicks));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void renderTooltip(ScaledResolution sr, float partialTicks) {
/*  446 */     boolean animModEnable = ToolTipsAnim.getInstance.isEnabled();
/*  447 */     if (animModEnable) {
/*  448 */       if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
/*  449 */         float needUp = 3.0F;
/*  450 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  451 */         EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
/*  452 */         this.animUp = sr.getScaledHeight() - 25.0F;
/*  453 */         float i = (sr.getScaledWidth() / 2);
/*  454 */         float f = this.zLevel;
/*  455 */         this.zLevel = -90.0F;
/*  456 */         this.anim = AnimationUtil.moveUDFaster(this.anim, (entityplayer.inventory.currentItem * 20));
/*  457 */         if (HUD.getInstance != null && HUD.getInstance.isEnabled() && ((Boolean)HUD.getInstance.blur.get()).booleanValue()) {
/*  458 */           BlurBuffer.blurRoundArea(i - 90.0F, this.animUp - 0.0F, 180.0F, 20.0F, 3.0F, true);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  463 */         RoundedUtil.drawRound(i - 90.0F, this.animUp, 180.0F, 20.0F, 3.0F, new Color(0, 0, 0, 150));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  468 */         RoundedUtil.drawRound(i - 90.0F + this.anim, this.animUp, 20.0F, 20.0F, 3.0F, new Color(233, 233, 233, 100));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  473 */         this.zLevel = f;
/*  474 */         GlStateManager.enableRescaleNormal();
/*  475 */         GlStateManager.enableBlend();
/*  476 */         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  477 */         RenderHelper.enableGUIStandardItemLighting();
/*  478 */         GlStateManager.pushMatrix();
/*  479 */         int l = (int)((sr.getScaledHeight() - 16 - 3) - 3.0F);
/*  480 */         for (int j = 0; j < 9; j++) {
/*  481 */           float k = (sr.getScaledWidth() / 2 - 90 + j * 20 + 2);
/*  482 */           renderHotbarItem(j, (int)k, l, partialTicks, entityplayer);
/*      */         } 
/*  484 */         GlStateManager.popMatrix();
/*  485 */         RenderHelper.disableStandardItemLighting();
/*  486 */         GlStateManager.disableRescaleNormal();
/*  487 */         GlStateManager.disableBlend();
/*      */       }
/*      */     
/*  490 */     } else if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
/*  491 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  492 */       this.mc.getTextureManager().bindTexture(widgetsTexPath);
/*  493 */       EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
/*  494 */       int i = sr.getScaledWidth() / 2;
/*  495 */       float f = this.zLevel;
/*  496 */       this.zLevel = -90.0F;
/*  497 */       drawTexturedModalRect(i - 91, sr.getScaledHeight() - 22 - 4, 0, 0, 182, 22);
/*  498 */       drawTexturedModalRect(i - 91 - 1 + entityplayer.inventory.currentItem * 20, sr
/*  499 */           .getScaledHeight() - 22 - 1 - 4, 0, 22, 24, 22);
/*  500 */       this.zLevel = f;
/*  501 */       GlStateManager.enableRescaleNormal();
/*  502 */       GlStateManager.enableBlend();
/*  503 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  504 */       RenderHelper.enableGUIStandardItemLighting();
/*      */       
/*  506 */       for (int j = 0; j < 9; j++) {
/*  507 */         int k = sr.getScaledWidth() / 2 - 90 + j * 20 + 2;
/*  508 */         int l = sr.getScaledHeight() - 16 - 3 - 4;
/*  509 */         renderHotbarItem(j, k, l, partialTicks, entityplayer);
/*      */       } 
/*      */       
/*  512 */       RenderHelper.disableStandardItemLighting();
/*  513 */       GlStateManager.disableRescaleNormal();
/*  514 */       GlStateManager.disableBlend();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderHorseJumpBar(ScaledResolution scaledRes, int x) {
/*  520 */     this.mc.mcProfiler.startSection("jumpBar");
/*  521 */     this.mc.getTextureManager().bindTexture(Gui.icons);
/*  522 */     float f = this.mc.thePlayer.getHorseJumpPower();
/*  523 */     int i = 182;
/*  524 */     int j = (int)(f * (i + 1));
/*  525 */     int k = scaledRes.getScaledHeight() - 32 + 3;
/*  526 */     drawTexturedModalRect(x, k, 0, 84, i, 5);
/*      */     
/*  528 */     if (j > 0) {
/*  529 */       drawTexturedModalRect(x, k, 0, 89, j, 5);
/*      */     }
/*      */     
/*  532 */     this.mc.mcProfiler.endSection();
/*      */   }
/*      */   
/*      */   public void renderExpBar(ScaledResolution scaledRes, int x) {
/*  536 */     this.mc.mcProfiler.startSection("expBar");
/*  537 */     this.mc.getTextureManager().bindTexture(Gui.icons);
/*  538 */     int i = this.mc.thePlayer.xpBarCap();
/*      */     
/*  540 */     if (i > 0) {
/*  541 */       int j = 182;
/*  542 */       int k = (int)(this.mc.thePlayer.experience * (j + 1));
/*  543 */       int l = scaledRes.getScaledHeight() - 32 + 3;
/*  544 */       drawTexturedModalRect(x, l, 0, 64, j, 5);
/*      */       
/*  546 */       if (k > 0) {
/*  547 */         drawTexturedModalRect(x, l, 0, 69, k, 5);
/*      */       }
/*      */     } 
/*      */     
/*  551 */     this.mc.mcProfiler.endSection();
/*      */     
/*  553 */     if (this.mc.thePlayer.experienceLevel > 0) {
/*  554 */       this.mc.mcProfiler.startSection("expLevel");
/*  555 */       int k1 = 8453920;
/*      */       
/*  557 */       if (Config.isCustomColors()) {
/*  558 */         k1 = CustomColors.getExpBarTextColor(k1);
/*      */       }
/*      */       
/*  561 */       String s = String.valueOf(this.mc.thePlayer.experienceLevel);
/*  562 */       int l1 = (scaledRes.getScaledWidth() - getFontRenderer().getStringWidth(s)) / 2;
/*  563 */       int i1 = scaledRes.getScaledHeight() - 31 - 4;
/*  564 */       getFontRenderer().drawString(s, l1 + 1, i1, 0);
/*  565 */       getFontRenderer().drawString(s, l1 - 1, i1, 0);
/*  566 */       getFontRenderer().drawString(s, l1, i1 + 1, 0);
/*  567 */       getFontRenderer().drawString(s, l1, i1 - 1, 0);
/*  568 */       getFontRenderer().drawString(s, l1, i1, k1);
/*  569 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renderSelectedItem(ScaledResolution scaledRes) {
/*  574 */     this.mc.mcProfiler.startSection("selectedItemName");
/*      */     
/*  576 */     if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null) {
/*  577 */       String s = this.highlightingItemStack.getDisplayName();
/*      */       
/*  579 */       if (this.highlightingItemStack.hasDisplayName()) {
/*  580 */         s = EnumChatFormatting.ITALIC + s;
/*      */       }
/*      */       
/*  583 */       int i = (scaledRes.getScaledWidth() - getFontRenderer().getStringWidth(s)) / 2;
/*  584 */       int j = scaledRes.getScaledHeight() - 59;
/*      */       
/*  586 */       if (!this.mc.playerController.shouldDrawHUD()) {
/*  587 */         j += 14;
/*      */       }
/*      */       
/*  590 */       int k = (int)(this.remainingHighlightTicks * 256.0F / 10.0F);
/*      */       
/*  592 */       if (k > 255) {
/*  593 */         k = 255;
/*      */       }
/*      */       
/*  596 */       if (k > 0) {
/*  597 */         GlStateManager.pushMatrix();
/*  598 */         GlStateManager.enableBlend();
/*  599 */         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  600 */         if (ToolTipsAnim.getInstance.isEnabled()) {
/*  601 */           FontLoader.PF18.drawStringWithShadow(s, i, j - 15.0F * GuiChat.openingAnimation.getOutput().floatValue(), 16777215 + (k << 24));
/*      */         } else {
/*  603 */           getFontRenderer().drawStringWithShadow(s, i, j - 15.0F * GuiChat.openingAnimation.getOutput().floatValue(), 16777215 + (k << 24));
/*      */         } 
/*  605 */         GlStateManager.disableBlend();
/*  606 */         GlStateManager.popMatrix();
/*      */       } 
/*      */     } 
/*      */     
/*  610 */     this.mc.mcProfiler.endSection();
/*      */   }
/*      */   public void renderDemo(ScaledResolution scaledRes) {
/*      */     String s;
/*  614 */     this.mc.mcProfiler.startSection("demo");
/*      */ 
/*      */     
/*  617 */     if (this.mc.theWorld.getTotalWorldTime() >= 120500L) {
/*  618 */       s = I18n.format("demo.demoExpired", new Object[0]);
/*      */     } else {
/*  620 */       s = I18n.format("demo.remainingTime", new Object[] { StringUtils.ticksToElapsedTime((int)(120500L - this.mc.theWorld.getTotalWorldTime())) });
/*      */     } 
/*      */     
/*  623 */     int i = getFontRenderer().getStringWidth(s);
/*  624 */     getFontRenderer().drawStringWithShadow(s, (scaledRes.getScaledWidth() - i - 10), 5.0F, 16777215);
/*  625 */     this.mc.mcProfiler.endSection();
/*      */   }
/*      */   
/*      */   protected boolean showCrosshair() {
/*  629 */     if ((this.mc.gameSettings.showDebugInfo && 
/*  630 */       !this.mc.thePlayer.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo) || Crosshair.getInstance
/*  631 */       .isEnabled())
/*  632 */       return false; 
/*  633 */     if (this.mc.playerController.isSpectator()) {
/*  634 */       if (this.mc.pointedEntity != null) {
/*  635 */         return true;
/*      */       }
/*  637 */       if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/*  638 */         BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
/*      */         
/*  640 */         return this.mc.theWorld.getTileEntity(blockpos) instanceof net.minecraft.inventory.IInventory;
/*      */       } 
/*      */       
/*  643 */       return false;
/*      */     } 
/*      */     
/*  646 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderScoreboardBlur(ScaledResolution scaledRes) {
/*  657 */     Scoreboard scoreboardOBJ = this.mc.theWorld.getScoreboard();
/*  658 */     ScoreObjective scoreobjective = null;
/*  659 */     ScorePlayerTeam scoreplayerteamObj = scoreboardOBJ.getPlayersTeam(this.mc.thePlayer.getName());
/*      */     
/*  661 */     if (scoreplayerteamObj != null) {
/*  662 */       int i1 = scoreplayerteamObj.getChatFormat().getColorIndex();
/*      */       
/*  664 */       if (i1 >= 0) {
/*  665 */         scoreobjective = scoreboardOBJ.getObjectiveInDisplaySlot(3 + i1);
/*      */       }
/*      */     } 
/*  668 */     ScoreObjective objective = (scoreobjective != null) ? scoreobjective : scoreboardOBJ.getObjectiveInDisplaySlot(1);
/*  669 */     if (objective != null && SetScoreboard.getInstance.isEnabled()) {
/*      */       
/*  671 */       Scoreboard scoreboard = objective.getScoreboard();
/*  672 */       Collection<Score> collection = scoreboard.getSortedScores(objective);
/*  673 */       List<Score> list = Lists.newArrayList(Iterables.filter(collection, p_apply_1_ -> (p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#"))));
/*      */       
/*  675 */       if (list.size() > 15) {
/*  676 */         collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
/*      */       } else {
/*  678 */         collection = list;
/*      */       } 
/*      */       
/*  681 */       float i = FontManager.default16.getStringWidth(objective.getDisplayName());
/*      */       
/*  683 */       for (Score score : collection) {
/*  684 */         ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
/*  685 */         String s = ScorePlayerTeam.formatPlayerName((Team)scoreplayerteam, score.getPlayerName()) + ": " + EnumChatFormatting.RED + score.getScorePoints();
/*  686 */         i = Math.max(i, FontManager.default16.getStringWidth(s));
/*      */       } 
/*      */       
/*  689 */       int i1 = collection.size() * FontManager.default16.getHeight();
/*  690 */       int j1 = scaledRes.getScaledHeight() / 2 + i1 / 3 + ((Double)SetScoreboard.getInstance.Y.getValue()).intValue();
/*  691 */       int k1 = 3;
/*  692 */       float l1 = scaledRes.getScaledWidth() - i - k1;
/*  693 */       int j = 0;
/*      */       
/*  695 */       for (Score score1 : collection) {
/*  696 */         j++;
/*  697 */         ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score1.getPlayerName());
/*  698 */         String s1 = ScorePlayerTeam.formatPlayerName((Team)scoreplayerteam1, score1.getPlayerName());
/*  699 */         int k = j1 - j * FontManager.default18.getHeight();
/*  700 */         int l = scaledRes.getScaledWidth() - k1 + 2;
/*  701 */         drawRect((l1 - 2.0F), k, l, (k + FontManager.default18.getHeight()), Color.BLACK.getRGB());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  707 */         if (j == collection.size()) {
/*  708 */           String s3 = objective.getDisplayName();
/*  709 */           drawRect((l1 - 2.0F), (k - FontManager.default16.getHeight() - 1), l, (k - 1), Color.BLACK.getRGB());
/*  710 */           drawRect((l1 - 2.0F), (k - 1), l, k, Color.BLACK.getRGB());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderScoreboard(ScoreObjective objective, ScaledResolution scaledRes) {
/*  718 */     Scoreboard scoreboard = objective.getScoreboard();
/*  719 */     Collection<Score> collection = scoreboard.getSortedScores(objective);
/*  720 */     List<Score> list = Lists.newArrayList(Iterables.filter(collection, p_apply_1_ -> (p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#"))));
/*      */     
/*  722 */     if (list.size() > 15) {
/*  723 */       collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
/*      */     } else {
/*  725 */       collection = list;
/*      */     } 
/*      */     
/*  728 */     float i = FontManager.default16.getStringWidth(objective.getDisplayName());
/*      */     
/*  730 */     for (Score score : collection) {
/*  731 */       ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
/*  732 */       String s = ScorePlayerTeam.formatPlayerName((Team)scoreplayerteam, score.getPlayerName()) + ": " + EnumChatFormatting.RED + score.getScorePoints();
/*  733 */       i = Math.max(i, FontManager.default16.getStringWidth(s));
/*      */     } 
/*      */     
/*  736 */     int i1 = collection.size() * FontManager.default16.getHeight();
/*  737 */     int j1 = scaledRes.getScaledHeight() / 2 + i1 / 3 + ((Double)SetScoreboard.getInstance.Y.getValue()).intValue();
/*  738 */     float l1 = scaledRes.getScaledWidth() - i - 3.0F;
/*  739 */     int j = 0;
/*      */     
/*  741 */     Color color = ColorUtil.applyOpacity(Color.BLACK, 0.29411766F);
/*  742 */     for (Score score1 : collection) {
/*  743 */       j++;
/*  744 */       ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score1.getPlayerName());
/*  745 */       String s1 = ScorePlayerTeam.formatPlayerName((Team)scoreplayerteam1, score1.getPlayerName());
/*  746 */       int k = j1 - j * FontManager.default18.getHeight();
/*  747 */       int l = scaledRes.getScaledWidth() - 3 + 2;
/*  748 */       GLUtil.startBlend();
/*  749 */       drawRect((l1 - 2.0F), k, l, (k + FontManager.default18.getHeight()), color.getRGB());
/*      */ 
/*      */       
/*  752 */       FontManager.default16.drawString(s1, l1, k, -1, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  760 */       if (j == collection.size()) {
/*  761 */         String s3 = objective.getDisplayName();
/*  762 */         drawRect((l1 - 2.0F), (k - FontManager.default16.getHeight() - 1), l, (k - 1), color.getRGB());
/*  763 */         GLUtil.startBlend();
/*  764 */         drawRect((l1 - 2.0F), (k - 1), l, k, color.getRGB());
/*  765 */         FontManager.default16.drawString(s3, (l1 + i / 2.0F - FontManager.default16.getStringWidth(s3) / 2.0F), (k - FontManager.default16
/*  766 */             .getHeight()), -1, false);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private void renderScoreboard2(ScoreObjective objective, ScaledResolution scaledRes) {
/*  771 */     Scoreboard scoreboard = objective.getScoreboard();
/*  772 */     Collection<Score> collection = scoreboard.getSortedScores(objective);
/*  773 */     List<Score> list = (List<Score>)collection.stream().filter(p_apply_1_ -> (p_apply_1_.getPlayerName() != null && (p_apply_1_.getPlayerName().isEmpty() || p_apply_1_.getPlayerName().charAt(0) != '#'))).collect(Collectors.toList());
/*      */     
/*  775 */     if (list.size() > 15) {
/*  776 */       collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
/*      */     } else {
/*  778 */       collection = list;
/*      */     } 
/*      */     
/*  781 */     int i = getFontRenderer().getStringWidth(objective.getDisplayName());
/*      */     
/*  783 */     for (Score score : collection) {
/*  784 */       ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
/*  785 */       String s = ScorePlayerTeam.formatPlayerName((Team)scoreplayerteam, score.getPlayerName()) + ": " + EnumChatFormatting.RED + score.getScorePoints();
/*  786 */       i = Math.max(i, getFontRenderer().getStringWidth(s));
/*      */     } 
/*      */     
/*  789 */     int i1 = collection.size() * (getFontRenderer()).FONT_HEIGHT;
/*  790 */     int j1 = scaledRes.getScaledHeight() / 2 + i1 / 3;
/*  791 */     int k1 = 3;
/*  792 */     int l1 = scaledRes.getScaledWidth() - i - k1;
/*  793 */     int j = 0;
/*      */     
/*  795 */     for (Score score1 : collection) {
/*  796 */       j++;
/*  797 */       ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score1.getPlayerName());
/*  798 */       String s1 = ScorePlayerTeam.formatPlayerName((Team)scoreplayerteam1, score1.getPlayerName());
/*  799 */       if (SetScoreboard.getInstance.isEnabled()) {
/*  800 */         int k = j1 - j * (getFontRenderer()).FONT_HEIGHT;
/*  801 */         int l = scaledRes.getScaledWidth() - k1 + 2;
/*      */         
/*  803 */         if (!((Boolean)SetScoreboard.getInstance.fastbord.getValue()).booleanValue())
/*      */         {
/*  805 */           drawRect(l1 - 2 - ((Double)SetScoreboard.getInstance.X.getValue()).intValue(), k + ((Double)SetScoreboard.getInstance.Y.getValue()).intValue(), l - ((Double)SetScoreboard.getInstance.X.getValue()).intValue(), k + (getFontRenderer()).FONT_HEIGHT + ((Double)SetScoreboard.getInstance.Y.getValue()).intValue(), 1342177280);
/*      */         }
/*      */         
/*  808 */         if (!s1.contains("ZQAT.top") && !s1.contains("mushmc.com") && !s1.contains("dcjnw.ga") && !s1.contains("mc110.net") && 
/*  809 */           !s1.contains("redesky.com") && !s1.toLowerCase().contains("pixel") && 
/*  810 */           !s1.contains("Mc986") && !s1.contains("loyisa.cn") && !s1.contains("HmXix.Wtf") && !s1.contains("Dcjnw.top")) {
/*  811 */           FontLoader.PF16.drawString(s1, (l1 - ((Double)SetScoreboard.getInstance.X.getValue()).intValue()), k + ((Double)SetScoreboard.getInstance.Y.getValue()).intValue(), -1);
/*      */         } else {
/*  813 */           char[] charArray = "Awareline - NextGen7".toCharArray();
/*  814 */           int length = 0;
/*  815 */           for (char charIndex : charArray) {
/*  816 */             if (!((Boolean)HUD.dynamicColor.getValue()).booleanValue()) {
/*  817 */               Client.instance.FontLoaders.SF18.drawStringWithShadow(String.valueOf(charIndex), (l1 - ((Double)SetScoreboard.getInstance.X.getValue()).intValue() + length), (k + ((Double)SetScoreboard.getInstance.Y.getValue()).intValue()), ((Boolean)HUD.rainbow.getValue()).booleanValue() ? HUD.getInstance.rainbowToEffect().hashCode() : ColorManager.HUDColor());
/*      */             } else {
/*  819 */               Color Ranbow = ColorManager.fade(new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g
/*  820 */                     .getValue()).intValue(), ((Double)HUD.b.getValue()).intValue()), 65, length + 39);
/*  821 */               Client.instance.FontLoaders.SF18.drawStringWithShadow(String.valueOf(charIndex), (l1 - ((Double)SetScoreboard.getInstance.X.getValue()).intValue() + length), (k + ((Double)SetScoreboard.getInstance.Y.getValue()).intValue()), ((Boolean)HUD.rainbow.getValue()).booleanValue() ? HUD.getInstance.rainbowToEffect().hashCode() : Ranbow.getRGB());
/*      */             } 
/*  823 */             length += Client.instance.FontLoaders.SF18.getCharWidth(charIndex);
/*      */           } 
/*      */         } 
/*      */         
/*  827 */         if (j == collection.size()) {
/*  828 */           String s3 = objective.getDisplayName();
/*  829 */           if (!((Boolean)SetScoreboard.getInstance.fastbord.getValue()).booleanValue()) {
/*  830 */             drawRect(l1 - 2 - ((Double)SetScoreboard.getInstance.X.getValue()).intValue(), k - 
/*  831 */                 (getFontRenderer()).FONT_HEIGHT - 1 + ((Double)SetScoreboard.getInstance.Y.getValue()).intValue(), l - ((Double)SetScoreboard.getInstance.X
/*  832 */                 .getValue()).intValue(), k - 1 + ((Double)SetScoreboard.getInstance.Y
/*  833 */                 .getValue()).intValue(), 1342177280);
/*  834 */             drawRect(l1 - 2 - ((Double)SetScoreboard.getInstance.X.getValue()).intValue(), k - 1 + ((Double)SetScoreboard.getInstance.Y.getValue()).intValue(), l - ((Double)SetScoreboard.getInstance.X.getValue()).intValue(), k + ((Double)SetScoreboard.getInstance.Y.getValue()).intValue(), 1342177280);
/*      */           } 
/*  836 */           if (!((Boolean)SetScoreboard.getInstance.noServername.getValue()).booleanValue()) {
/*  837 */             getFontRenderer().drawString(s3, l1 + i / 2 - getFontRenderer().getStringWidth(s3) / 2 - ((Double)SetScoreboard.getInstance.X.getValue()).intValue(), k - (getFontRenderer()).FONT_HEIGHT + ((Double)SetScoreboard.getInstance.Y.getValue()).intValue(), 553648127);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void renderPlayerStats(ScaledResolution scaledRes) {
/*  845 */     if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
/*  846 */       EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
/*  847 */       int i = MathHelper.ceiling_float_int(entityplayer.getHealth());
/*  848 */       boolean flag = (this.healthUpdateCounter > this.updateCounter && (this.healthUpdateCounter - this.updateCounter) / 3L % 2L == 1L);
/*      */       
/*  850 */       if (i < this.playerHealth && entityplayer.hurtResistantTime > 0) {
/*  851 */         this.lastSystemTime = Minecraft.getSystemTime();
/*  852 */         this.healthUpdateCounter = (this.updateCounter + 20);
/*  853 */       } else if (i > this.playerHealth && entityplayer.hurtResistantTime > 0) {
/*  854 */         this.lastSystemTime = Minecraft.getSystemTime();
/*  855 */         this.healthUpdateCounter = (this.updateCounter + 10);
/*      */       } 
/*      */       
/*  858 */       if (Minecraft.getSystemTime() - this.lastSystemTime > 1000L) {
/*  859 */         this.playerHealth = i;
/*  860 */         this.lastPlayerHealth = i;
/*  861 */         this.lastSystemTime = Minecraft.getSystemTime();
/*      */       } 
/*      */       
/*  864 */       this.playerHealth = i;
/*  865 */       int j = this.lastPlayerHealth;
/*  866 */       this.rand.setSeed(this.updateCounter * 312871L);
/*  867 */       FoodStats foodstats = entityplayer.getFoodStats();
/*  868 */       int k = foodstats.getFoodLevel();
/*  869 */       IAttributeInstance iattributeinstance = entityplayer.getEntityAttribute(SharedMonsterAttributes.maxHealth);
/*  870 */       int i1 = scaledRes.getScaledWidth() / 2 - 91;
/*  871 */       int j1 = scaledRes.getScaledWidth() / 2 + 91;
/*  872 */       int k1 = scaledRes.getScaledHeight() - 39;
/*  873 */       float f = (float)iattributeinstance.getAttributeValue();
/*  874 */       float f1 = entityplayer.getAbsorptionAmount();
/*  875 */       int l1 = MathHelper.ceiling_float_int((f + f1) / 2.0F / 10.0F);
/*  876 */       int i2 = Math.max(10 - l1 - 2, 3);
/*  877 */       int j2 = k1 - (l1 - 1) * i2 - 10;
/*  878 */       float f2 = f1;
/*  879 */       int k2 = entityplayer.getTotalArmorValue();
/*  880 */       int l2 = -1;
/*      */       
/*  882 */       if (entityplayer.isPotionActive(Potion.regeneration)) {
/*  883 */         l2 = this.updateCounter % MathHelper.ceiling_float_int(f + 5.0F);
/*      */       }
/*      */       
/*  886 */       this.mc.mcProfiler.startSection("armor");
/*      */       
/*  888 */       for (int i3 = 0; i3 < 10; i3++) {
/*  889 */         if (k2 > 0) {
/*  890 */           int j3 = i1 + (i3 << 3);
/*      */           
/*  892 */           if ((i3 << 1) + 1 < k2) {
/*  893 */             drawTexturedModalRect(j3, j2, 34, 9, 9, 9);
/*      */           }
/*      */           
/*  896 */           if ((i3 << 1) + 1 == k2) {
/*  897 */             drawTexturedModalRect(j3, j2, 25, 9, 9, 9);
/*      */           }
/*      */           
/*  900 */           if ((i3 << 1) + 1 > k2) {
/*  901 */             drawTexturedModalRect(j3, j2, 16, 9, 9, 9);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  906 */       this.mc.mcProfiler.endStartSection("health");
/*      */       
/*  908 */       for (int i6 = MathHelper.ceiling_float_int((f + f1) / 2.0F) - 1; i6 >= 0; i6--) {
/*  909 */         int j6 = 16;
/*      */         
/*  911 */         if (entityplayer.isPotionActive(Potion.poison)) {
/*  912 */           j6 += 36;
/*  913 */         } else if (entityplayer.isPotionActive(Potion.wither)) {
/*  914 */           j6 += 72;
/*      */         } 
/*      */         
/*  917 */         int k3 = 0;
/*      */         
/*  919 */         if (flag) {
/*  920 */           k3 = 1;
/*      */         }
/*      */         
/*  923 */         int l3 = MathHelper.ceiling_float_int((i6 + 1) / 10.0F) - 1;
/*  924 */         int i4 = i1 + (i6 % 10 << 3);
/*  925 */         int j4 = k1 - l3 * i2;
/*      */         
/*  927 */         if (i <= 4) {
/*  928 */           j4 += this.rand.nextInt(2);
/*      */         }
/*      */         
/*  931 */         if (i6 == l2) {
/*  932 */           j4 -= 2;
/*      */         }
/*      */         
/*  935 */         int k4 = 0;
/*      */         
/*  937 */         if (entityplayer.worldObj.getWorldInfo().isHardcoreModeEnabled()) {
/*  938 */           k4 = 5;
/*      */         }
/*      */         
/*  941 */         drawTexturedModalRect(i4, j4, 16 + k3 * 9, 9 * k4, 9, 9);
/*      */         
/*  943 */         if (flag) {
/*  944 */           if ((i6 << 1) + 1 < j) {
/*  945 */             drawTexturedModalRect(i4, j4, j6 + 54, 9 * k4, 9, 9);
/*      */           }
/*      */           
/*  948 */           if ((i6 << 1) + 1 == j) {
/*  949 */             drawTexturedModalRect(i4, j4, j6 + 63, 9 * k4, 9, 9);
/*      */           }
/*      */         } 
/*      */         
/*  953 */         if (f2 <= 0.0F) {
/*  954 */           if ((i6 << 1) + 1 < i) {
/*  955 */             drawTexturedModalRect(i4, j4, j6 + 36, 9 * k4, 9, 9);
/*      */           }
/*      */           
/*  958 */           if ((i6 << 1) + 1 == i) {
/*  959 */             drawTexturedModalRect(i4, j4, j6 + 45, 9 * k4, 9, 9);
/*      */           }
/*      */         } else {
/*  962 */           if (f2 == f1 && f1 % 2.0F == 1.0F) {
/*  963 */             drawTexturedModalRect(i4, j4, j6 + 153, 9 * k4, 9, 9);
/*      */           } else {
/*  965 */             drawTexturedModalRect(i4, j4, j6 + 144, 9 * k4, 9, 9);
/*      */           } 
/*      */           
/*  968 */           f2 -= 2.0F;
/*      */         } 
/*      */       } 
/*      */       
/*  972 */       Entity entity = entityplayer.ridingEntity;
/*      */       
/*  974 */       if (entity == null) {
/*  975 */         this.mc.mcProfiler.endStartSection("food");
/*      */         
/*  977 */         for (int k6 = 0; k6 < 10; k6++) {
/*  978 */           int j7 = k1;
/*  979 */           int l7 = 16;
/*  980 */           int k8 = 0;
/*      */           
/*  982 */           if (entityplayer.isPotionActive(Potion.hunger)) {
/*  983 */             l7 += 36;
/*  984 */             k8 = 13;
/*      */           } 
/*      */           
/*  987 */           if (entityplayer.getFoodStats().getSaturationLevel() <= 0.0F && this.updateCounter % (k * 3 + 1) == 0) {
/*  988 */             j7 = k1 + this.rand.nextInt(3) - 1;
/*      */           }
/*      */           
/*  991 */           int j9 = j1 - (k6 << 3) - 9;
/*  992 */           drawTexturedModalRect(j9, j7, 16 + k8 * 9, 27, 9, 9);
/*      */           
/*  994 */           if ((k6 << 1) + 1 < k) {
/*  995 */             drawTexturedModalRect(j9, j7, l7 + 36, 27, 9, 9);
/*      */           }
/*      */           
/*  998 */           if ((k6 << 1) + 1 == k) {
/*  999 */             drawTexturedModalRect(j9, j7, l7 + 45, 27, 9, 9);
/*      */           }
/*      */         } 
/* 1002 */       } else if (entity instanceof EntityLivingBase) {
/* 1003 */         this.mc.mcProfiler.endStartSection("mountHealth");
/* 1004 */         EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
/* 1005 */         int i7 = (int)Math.ceil(entitylivingbase.getHealth());
/* 1006 */         float f3 = entitylivingbase.getMaxHealth();
/* 1007 */         int j8 = (int)(f3 + 0.5F) / 2;
/*      */         
/* 1009 */         if (j8 > 30) {
/* 1010 */           j8 = 30;
/*      */         }
/*      */         
/* 1013 */         int i9 = k1;
/*      */         
/* 1015 */         for (int k9 = 0; j8 > 0; k9 += 20) {
/* 1016 */           int l4 = Math.min(j8, 10);
/* 1017 */           j8 -= l4;
/*      */           
/* 1019 */           for (int i5 = 0; i5 < l4; i5++) {
/* 1020 */             int j5 = 52;
/*      */             
/* 1022 */             int l5 = j1 - (i5 << 3) - 9;
/* 1023 */             drawTexturedModalRect(l5, i9, 0, 9, 9, 9);
/*      */             
/* 1025 */             if ((i5 << 1) + 1 + k9 < i7) {
/* 1026 */               drawTexturedModalRect(l5, i9, j5 + 36, 9, 9, 9);
/*      */             }
/*      */             
/* 1029 */             if ((i5 << 1) + 1 + k9 == i7) {
/* 1030 */               drawTexturedModalRect(l5, i9, j5 + 45, 9, 9, 9);
/*      */             }
/*      */           } 
/*      */           
/* 1034 */           i9 -= 10;
/*      */         } 
/*      */       } 
/*      */       
/* 1038 */       this.mc.mcProfiler.endStartSection("air");
/*      */       
/* 1040 */       if (entityplayer.isInsideOfMaterial(Material.water)) {
/* 1041 */         int l6 = this.mc.thePlayer.getAir();
/* 1042 */         int k7 = MathHelper.ceiling_double_int((l6 - 2) * 10.0D / 300.0D);
/* 1043 */         int i8 = MathHelper.ceiling_double_int(l6 * 10.0D / 300.0D) - k7;
/*      */         
/* 1045 */         for (int l8 = 0; l8 < k7 + i8; l8++) {
/* 1046 */           if (l8 < k7) {
/* 1047 */             drawTexturedModalRect(j1 - (l8 << 3) - 9, j2, 16, 18, 9, 9);
/*      */           } else {
/* 1049 */             drawTexturedModalRect(j1 - (l8 << 3) - 9, j2, 25, 18, 9, 9);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1054 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderBossHealth() {
/* 1062 */     if (BossStatus.bossName != null && BossStatus.statusBarTime > 0) {
/* 1063 */       BossStatus.statusBarTime--;
/* 1064 */       ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 1065 */       int i = scaledresolution.getScaledWidth();
/* 1066 */       int j = 182;
/* 1067 */       int k = i / 2 - j / 2;
/* 1068 */       int l = (int)(BossStatus.healthScale * (j + 1));
/* 1069 */       int i1 = 12;
/* 1070 */       drawTexturedModalRect(k, i1, 0, 74, j, 5);
/* 1071 */       drawTexturedModalRect(k, i1, 0, 74, j, 5);
/*      */       
/* 1073 */       if (l > 0) {
/* 1074 */         drawTexturedModalRect(k, i1, 0, 79, l, 5);
/*      */       }
/*      */       
/* 1077 */       String s = BossStatus.bossName;
/* 1078 */       getFontRenderer().drawStringWithShadow(s, (i / 2 - getFontRenderer().getStringWidth(s) / 2), (i1 - 10), 16777215);
/* 1079 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1080 */       this.mc.getTextureManager().bindTexture(icons);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void renderPumpkinOverlay(ScaledResolution scaledRes) {
/* 1085 */     if (NoPumpkinHead.getInstance.isEnabled()) {
/*      */       return;
/*      */     }
/* 1088 */     GlStateManager.disableDepth();
/* 1089 */     GlStateManager.depthMask(false);
/* 1090 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1091 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1092 */     GlStateManager.disableAlpha();
/* 1093 */     this.mc.getTextureManager().bindTexture(pumpkinBlurTexPath);
/* 1094 */     Tessellator tessellator = Tessellator.getInstance();
/* 1095 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1096 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1097 */     worldrenderer.pos(0.0D, scaledRes.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
/* 1098 */     worldrenderer.pos(scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
/* 1099 */     worldrenderer.pos(scaledRes.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
/* 1100 */     worldrenderer.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
/* 1101 */     tessellator.draw();
/* 1102 */     GlStateManager.depthMask(true);
/* 1103 */     GlStateManager.enableDepth();
/* 1104 */     GlStateManager.enableAlpha();
/* 1105 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderVignette(float lightLevel, ScaledResolution scaledRes) {
/* 1115 */     if (!Config.isVignetteEnabled()) {
/* 1116 */       GlStateManager.enableDepth();
/* 1117 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*      */     } else {
/* 1119 */       lightLevel = 1.0F - lightLevel;
/* 1120 */       lightLevel = MathHelper.clamp_float(lightLevel, 0.0F, 1.0F);
/* 1121 */       WorldBorder worldborder = this.mc.theWorld.getWorldBorder();
/* 1122 */       float f = (float)worldborder.getClosestDistance((Entity)this.mc.thePlayer);
/* 1123 */       double d0 = Math.min(worldborder.getResizeSpeed() * worldborder.getWarningTime() * 1000.0D, Math.abs(worldborder.getTargetSize() - worldborder.getDiameter()));
/* 1124 */       double d1 = Math.max(worldborder.getWarningDistance(), d0);
/*      */       
/* 1126 */       if (f < d1) {
/* 1127 */         f = 1.0F - (float)(f / d1);
/*      */       } else {
/* 1129 */         f = 0.0F;
/*      */       } 
/*      */       
/* 1132 */       this.prevVignetteBrightness = (float)(this.prevVignetteBrightness + (lightLevel - this.prevVignetteBrightness) * 0.01D);
/* 1133 */       GlStateManager.disableDepth();
/* 1134 */       GlStateManager.depthMask(false);
/* 1135 */       GlStateManager.tryBlendFuncSeparate(0, 769, 1, 0);
/*      */       
/* 1137 */       if (f > 0.0F) {
/* 1138 */         GlStateManager.color(0.0F, f, f, 1.0F);
/*      */       } else {
/* 1140 */         GlStateManager.color(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0F);
/*      */       } 
/*      */       
/* 1143 */       this.mc.getTextureManager().bindTexture(vignetteTexPath);
/* 1144 */       Tessellator tessellator = Tessellator.getInstance();
/* 1145 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1146 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1147 */       worldrenderer.pos(0.0D, scaledRes.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
/* 1148 */       worldrenderer.pos(scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
/* 1149 */       worldrenderer.pos(scaledRes.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
/* 1150 */       worldrenderer.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
/* 1151 */       tessellator.draw();
/* 1152 */       GlStateManager.depthMask(true);
/* 1153 */       GlStateManager.enableDepth();
/* 1154 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1155 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void renderPortal(float timeInPortal, ScaledResolution scaledRes) {
/* 1160 */     if (timeInPortal < 1.0F) {
/* 1161 */       timeInPortal *= timeInPortal;
/* 1162 */       timeInPortal *= timeInPortal;
/* 1163 */       timeInPortal = timeInPortal * 0.8F + 0.2F;
/*      */     } 
/*      */     
/* 1166 */     GlStateManager.disableAlpha();
/* 1167 */     GlStateManager.disableDepth();
/* 1168 */     GlStateManager.depthMask(false);
/* 1169 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1170 */     GlStateManager.color(1.0F, 1.0F, 1.0F, timeInPortal);
/* 1171 */     this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 1172 */     TextureAtlasSprite textureatlassprite = this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(Blocks.portal.getDefaultState());
/* 1173 */     float f = textureatlassprite.getMinU();
/* 1174 */     float f1 = textureatlassprite.getMinV();
/* 1175 */     float f2 = textureatlassprite.getMaxU();
/* 1176 */     float f3 = textureatlassprite.getMaxV();
/* 1177 */     Tessellator tessellator = Tessellator.getInstance();
/* 1178 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1179 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1180 */     worldrenderer.pos(0.0D, scaledRes.getScaledHeight(), -90.0D).tex(f, f3).endVertex();
/* 1181 */     worldrenderer.pos(scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), -90.0D).tex(f2, f3).endVertex();
/* 1182 */     worldrenderer.pos(scaledRes.getScaledWidth(), 0.0D, -90.0D).tex(f2, f1).endVertex();
/* 1183 */     worldrenderer.pos(0.0D, 0.0D, -90.0D).tex(f, f1).endVertex();
/* 1184 */     tessellator.draw();
/* 1185 */     GlStateManager.depthMask(true);
/* 1186 */     GlStateManager.enableDepth();
/* 1187 */     GlStateManager.enableAlpha();
/* 1188 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */   
/*      */   private void renderHotbarItem(int index, int xPos, int yPos, float partialTicks, EntityPlayer player) {
/* 1192 */     ItemStack itemstack = player.inventory.mainInventory[index];
/*      */     
/* 1194 */     if (itemstack != null) {
/* 1195 */       float f = itemstack.animationsToGo - partialTicks;
/*      */       
/* 1197 */       if (f > 0.0F) {
/* 1198 */         GlStateManager.pushMatrix();
/* 1199 */         float f1 = 1.0F + f / 5.0F;
/* 1200 */         GlStateManager.translate((xPos + 8), (yPos + 12), 0.0F);
/* 1201 */         GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
/* 1202 */         GlStateManager.translate(-(xPos + 8), -(yPos + 12), 0.0F);
/*      */       } 
/*      */       
/* 1205 */       this.itemRenderer.renderItemAndEffectIntoGUI(itemstack, xPos, yPos);
/*      */       
/* 1207 */       if (f > 0.0F) {
/* 1208 */         GlStateManager.popMatrix();
/*      */       }
/*      */       
/* 1211 */       this.itemRenderer.renderItemOverlays(this.mc.fontRendererObj, itemstack, xPos, yPos);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateTick() {
/* 1219 */     if (this.recordPlayingUpFor > 0) {
/* 1220 */       this.recordPlayingUpFor--;
/*      */     }
/*      */     
/* 1223 */     if (this.titlesTimer > 0) {
/* 1224 */       this.titlesTimer--;
/*      */       
/* 1226 */       if (this.titlesTimer <= 0) {
/* 1227 */         this.displayedTitle = "";
/* 1228 */         this.displayedSubTitle = "";
/*      */       } 
/*      */     } 
/*      */     
/* 1232 */     this.updateCounter++;
/*      */ 
/*      */     
/* 1235 */     if (this.mc.thePlayer != null) {
/* 1236 */       ItemStack itemstack = this.mc.thePlayer.inventory.getCurrentItem();
/*      */       
/* 1238 */       if (itemstack == null) {
/* 1239 */         this.remainingHighlightTicks = 0;
/* 1240 */       } else if (this.highlightingItemStack != null && itemstack.getItem() == this.highlightingItemStack.getItem() && ItemStack.areItemStackTagsEqual(itemstack, this.highlightingItemStack) && (itemstack.isItemStackDamageable() || itemstack.getMetadata() == this.highlightingItemStack.getMetadata())) {
/* 1241 */         if (this.remainingHighlightTicks > 0) {
/* 1242 */           this.remainingHighlightTicks--;
/*      */         }
/*      */       } else {
/* 1245 */         this.remainingHighlightTicks = 40;
/*      */       } 
/*      */       
/* 1248 */       this.highlightingItemStack = itemstack;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setRecordPlayingMessage(String recordName) {
/* 1253 */     setRecordPlaying(I18n.format("record.nowPlaying", new Object[] { recordName }), true);
/*      */   }
/*      */   
/*      */   public void setRecordPlaying(String message, boolean isPlaying) {
/* 1257 */     this.recordPlaying = message;
/* 1258 */     this.recordPlayingUpFor = 60;
/* 1259 */     this.recordIsPlaying = isPlaying;
/*      */   }
/*      */   
/*      */   public void displayTitle(String title, String subTitle, int timeFadeIn, int displayTime, int timeFadeOut) {
/* 1263 */     if (title == null && subTitle == null && timeFadeIn < 0 && displayTime < 0 && timeFadeOut < 0) {
/* 1264 */       this.displayedTitle = "";
/* 1265 */       this.displayedSubTitle = "";
/* 1266 */       this.titlesTimer = 0;
/* 1267 */     } else if (title != null) {
/* 1268 */       this.displayedTitle = title;
/* 1269 */       this.titlesTimer = this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut;
/* 1270 */     } else if (subTitle != null) {
/* 1271 */       this.displayedSubTitle = subTitle;
/*      */     } else {
/* 1273 */       if (timeFadeIn >= 0) {
/* 1274 */         this.titleFadeIn = timeFadeIn;
/*      */       }
/*      */       
/* 1277 */       if (displayTime >= 0) {
/* 1278 */         this.titleDisplayTime = displayTime;
/*      */       }
/*      */       
/* 1281 */       if (timeFadeOut >= 0) {
/* 1282 */         this.titleFadeOut = timeFadeOut;
/*      */       }
/*      */       
/* 1285 */       if (this.titlesTimer > 0) {
/* 1286 */         this.titlesTimer = this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setRecordPlaying(IChatComponent component, boolean isPlaying) {
/* 1292 */     setRecordPlaying(component.getUnformattedText(), isPlaying);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GuiNewChat getChatGUI() {
/* 1299 */     return this.persistantChatGUI;
/*      */   }
/*      */   
/*      */   public int getUpdateCounter() {
/* 1303 */     return this.updateCounter;
/*      */   }
/*      */   
/*      */   public FontRenderer getFontRenderer() {
/* 1307 */     return this.mc.fontRendererObj;
/*      */   }
/*      */   
/*      */   public GuiSpectator getSpectatorGui() {
/* 1311 */     return this.spectatorGui;
/*      */   }
/*      */   
/*      */   public GuiPlayerTabOverlay getTabList() {
/* 1315 */     return this.overlayPlayerList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetPlayersOverlayFooterHeader() {
/* 1322 */     this.overlayPlayerList.resetFooterHeader();
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiIngame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */