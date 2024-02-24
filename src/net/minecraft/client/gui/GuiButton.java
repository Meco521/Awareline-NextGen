/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.font.fastuni.FontLoader;
/*     */ import awareline.main.ui.font.fontmanager.color.ColorUtils;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StringUtils;
/*     */ 
/*     */ public class GuiButton
/*     */   extends Gui {
/*  19 */   protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
/*     */   public float cs;
/*  21 */   public float alpha = 0.5F;
/*     */ 
/*     */ 
/*     */   
/*     */   public int width;
/*     */ 
/*     */ 
/*     */   
/*     */   public int height;
/*     */ 
/*     */ 
/*     */   
/*     */   public int xPosition;
/*     */ 
/*     */ 
/*     */   
/*     */   public int yPosition;
/*     */ 
/*     */ 
/*     */   
/*     */   public String displayString;
/*     */ 
/*     */   
/*     */   public int id;
/*     */ 
/*     */   
/*     */   public boolean enabled;
/*     */ 
/*     */   
/*     */   public boolean visible;
/*     */ 
/*     */   
/*     */   protected boolean hovered;
/*     */ 
/*     */   
/*     */   private boolean enabledSmoothFont;
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiButton(int buttonId, int x, int y, String buttonText) {
/*  61 */     this(buttonId, x, y, 200, 20, buttonText);
/*     */   }
/*     */   public GuiButton(int buttonId, int x, int y, String buttonText, boolean enableSmoothFont) {
/*  64 */     this(buttonId, x, y, 200, 20, buttonText, enableSmoothFont);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, boolean enabledSmoothFont) {
/*  70 */     this.enabled = true;
/*  71 */     this.visible = true;
/*  72 */     this.id = buttonId;
/*  73 */     this.xPosition = x;
/*  74 */     this.yPosition = y;
/*  75 */     this.width = widthIn;
/*  76 */     this.height = heightIn;
/*  77 */     this.displayString = buttonText;
/*  78 */     this.enabledSmoothFont = enabledSmoothFont;
/*     */   }
/*     */   
/*     */   public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
/*  82 */     this.enabled = true;
/*  83 */     this.visible = true;
/*  84 */     this.id = buttonId;
/*  85 */     this.xPosition = x;
/*  86 */     this.yPosition = y;
/*  87 */     this.width = widthIn;
/*  88 */     this.height = heightIn;
/*  89 */     this.displayString = buttonText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getHoverState(boolean mouseOver) {
/*  97 */     int i = 1;
/*     */     
/*  99 */     if (!this.enabled) {
/* 100 */       i = 0;
/* 101 */     } else if (mouseOver) {
/* 102 */       i = 2;
/*     */     } 
/*     */     
/* 105 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
/* 112 */     if (this.visible) {
/* 113 */       this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
/* 114 */       updatefade();
/* 115 */       if (this.hovered) {
/* 116 */         if (this.cs != 8.0F) {
/* 117 */           this.cs = AnimationUtil.moveUD(this.cs, 8.0F, SimpleRender.processFPS(0.02F), SimpleRender.processFPS(0.018F));
/*     */         }
/*     */       }
/* 120 */       else if (this.cs != 0.0F) {
/* 121 */         this.cs = AnimationUtil.moveUD(this.cs, 0.0F, SimpleRender.processFPS(0.02F), SimpleRender.processFPS(0.018F));
/*     */       } 
/*     */       
/* 124 */       if (this.enabled) {
/* 125 */         SimpleRender.drawRectFloat(this.xPosition + this.cs, this.yPosition, (this.xPosition + this.width) - this.cs, (this.yPosition + this.height), 
/* 126 */             RenderUtil.reAlpha(ColorUtils.BLACK.c, Math.abs(Math.min(this.alpha, 1.0F))));
/* 127 */         SimpleRender.drawRectFloat(this.xPosition + this.cs, (this.yPosition + this.height) - 1.0F, (this.xPosition + this.width) - this.width / 2.0F, (this.yPosition + this.height), HUDColor(mc));
/* 128 */         SimpleRender.drawRectFloat(this.xPosition + this.width / 2.0F, (this.yPosition + this.height) - 1.0F, (this.xPosition + this.width) - this.cs, (this.yPosition + this.height), HUDColor(mc));
/*     */       } else {
/* 130 */         SimpleRender.drawRectFloat(this.xPosition, this.yPosition, (this.xPosition + this.width), (this.yPosition + this.height), (new Color(0.5F, 0.5F, 0.5F, 0.5F)).hashCode());
/*     */       } 
/* 132 */       if (this.enabledSmoothFont) {
/* 133 */         Client.instance.FontLoaders.regular17.drawCenteredString(StringUtils.stripControlCodes(this.displayString), this.xPosition + this.width / 2.0F, this.yPosition + (this.height - 3.0F) / 2.0F - 1.0F, -1);
/*     */       } else {
/*     */         
/* 136 */         FontLoader.PF18.drawCenteredString(StringUtils.stripControlCodes(this.displayString), this.xPosition + this.width / 2.0F, this.yPosition + (this.height - 3.0F) / 2.0F - 2.0F, -1);
/*     */       } 
/*     */       
/* 139 */       mouseDragged(mc, mouseX, mouseY);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int HUDColor(Minecraft mc) {
/* 144 */     return (mc.theWorld == null) ? Client.instance
/* 145 */       .getClientColorAlpha(255) : Client.instance.getClientColorNoRainbow(255);
/*     */   }
/*     */   
/*     */   private void updatefade() {
/* 149 */     if (this.enabled) {
/* 150 */       if (this.hovered) {
/* 151 */         this.alpha = AnimationUtil.moveUD(this.alpha, 0.8F);
/*     */       } else {
/* 153 */         this.alpha = AnimationUtil.moveUD(this.alpha, 0.5F);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
/* 175 */     return (this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMouseOver() {
/* 182 */     return this.hovered;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawButtonForegroundLayer(int mouseX, int mouseY) {}
/*     */   
/*     */   public void playPressSound(SoundHandler soundHandlerIn) {
/* 189 */     soundHandlerIn.playSound((ISound)PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
/*     */   }
/*     */   
/*     */   public int getButtonWidth() {
/* 193 */     return this.width;
/*     */   }
/*     */   
/*     */   public void setWidth(int width) {
/* 197 */     this.width = width;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */