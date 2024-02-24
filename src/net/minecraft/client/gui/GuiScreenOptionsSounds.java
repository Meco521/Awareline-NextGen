/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.audio.SoundCategory;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class GuiScreenOptionsSounds
/*     */   extends GuiScreen
/*     */ {
/*     */   private final GuiScreen field_146505_f;
/*     */   final GameSettings game_settings_4;
/*  19 */   protected String field_146507_a = "Options";
/*     */   
/*     */   private String field_146508_h;
/*     */   
/*     */   public GuiScreenOptionsSounds(GuiScreen p_i45025_1_, GameSettings p_i45025_2_) {
/*  24 */     this.field_146505_f = p_i45025_1_;
/*  25 */     this.game_settings_4 = p_i45025_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  34 */     int i = 0;
/*  35 */     this.field_146507_a = I18n.format("options.sounds.title", new Object[0]);
/*  36 */     this.field_146508_h = I18n.format("options.off", new Object[0]);
/*  37 */     this.buttonList.add(new Button(SoundCategory.MASTER.getCategoryId(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), SoundCategory.MASTER, true));
/*  38 */     i += 2;
/*     */     
/*  40 */     for (SoundCategory soundcategory : SoundCategory.values()) {
/*     */       
/*  42 */       if (soundcategory != SoundCategory.MASTER) {
/*     */         
/*  44 */         this.buttonList.add(new Button(soundcategory.getCategoryId(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), soundcategory, false));
/*  45 */         i++;
/*     */       } 
/*     */     } 
/*     */     
/*  49 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  56 */     if (button.enabled)
/*     */     {
/*  58 */       if (button.id == 200) {
/*     */         
/*  60 */         this.mc.gameSettings.saveOptions();
/*  61 */         this.mc.displayGuiScreen(this.field_146505_f);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  71 */     drawDefaultBackground();
/*  72 */     drawCenteredString(this.fontRendererObj, this.field_146507_a, this.width / 2, 15, 16777215);
/*  73 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getSoundVolume(SoundCategory p_146504_1_) {
/*  78 */     float f = this.game_settings_4.getSoundLevel(p_146504_1_);
/*  79 */     return (f == 0.0F) ? this.field_146508_h : ((int)(f * 100.0F) + "%");
/*     */   }
/*     */   
/*     */   class Button
/*     */     extends GuiButton {
/*     */     private final SoundCategory field_146153_r;
/*     */     private final String field_146152_s;
/*  86 */     public float field_146156_o = 1.0F;
/*     */     
/*     */     public boolean field_146155_p;
/*     */     
/*     */     public Button(int p_i45024_2_, int p_i45024_3_, int p_i45024_4_, SoundCategory p_i45024_5_, boolean p_i45024_6_) {
/*  91 */       super(p_i45024_2_, p_i45024_3_, p_i45024_4_, p_i45024_6_ ? 310 : 150, 20, "");
/*  92 */       this.field_146153_r = p_i45024_5_;
/*  93 */       this.field_146152_s = I18n.format("soundCategory." + p_i45024_5_.getCategoryName(), new Object[0]);
/*  94 */       this.displayString = this.field_146152_s + ": " + GuiScreenOptionsSounds.this.getSoundVolume(p_i45024_5_);
/*  95 */       this.field_146156_o = GuiScreenOptionsSounds.this.game_settings_4.getSoundLevel(p_i45024_5_);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getHoverState(boolean mouseOver) {
/* 100 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
/* 105 */       if (this.visible) {
/*     */         
/* 107 */         if (this.field_146155_p) {
/*     */           
/* 109 */           this.field_146156_o = (mouseX - this.xPosition + 4) / (this.width - 8);
/* 110 */           this.field_146156_o = MathHelper.clamp_float(this.field_146156_o, 0.0F, 1.0F);
/* 111 */           mc.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
/* 112 */           mc.gameSettings.saveOptions();
/* 113 */           this.displayString = this.field_146152_s + ": " + GuiScreenOptionsSounds.this.getSoundVolume(this.field_146153_r);
/*     */         } 
/*     */         
/* 116 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 117 */         drawTexturedModalRect(this.xPosition + (int)(this.field_146156_o * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
/* 118 */         drawTexturedModalRect(this.xPosition + (int)(this.field_146156_o * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
/* 124 */       if (super.mousePressed(mc, mouseX, mouseY)) {
/*     */         
/* 126 */         this.field_146156_o = (mouseX - this.xPosition + 4) / (this.width - 8);
/* 127 */         this.field_146156_o = MathHelper.clamp_float(this.field_146156_o, 0.0F, 1.0F);
/* 128 */         mc.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
/* 129 */         mc.gameSettings.saveOptions();
/* 130 */         this.displayString = this.field_146152_s + ": " + GuiScreenOptionsSounds.this.getSoundVolume(this.field_146153_r);
/* 131 */         this.field_146155_p = true;
/* 132 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 136 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void playPressSound(SoundHandler soundHandlerIn) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseReleased(int mouseX, int mouseY) {
/* 146 */       if (this.field_146155_p) {
/*     */         
/* 148 */         if (this.field_146153_r == SoundCategory.MASTER) {
/*     */           
/* 150 */           float f = 1.0F;
/*     */         }
/*     */         else {
/*     */           
/* 154 */           GuiScreenOptionsSounds.this.game_settings_4.getSoundLevel(this.field_146153_r);
/*     */         } 
/*     */         
/* 157 */         GuiScreenOptionsSounds.this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
/*     */       } 
/*     */       
/* 160 */       this.field_146155_p = false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiScreenOptionsSounds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */