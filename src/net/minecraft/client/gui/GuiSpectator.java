/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
/*     */ import net.minecraft.client.gui.spectator.ISpectatorMenuRecipient;
/*     */ import net.minecraft.client.gui.spectator.SpectatorMenu;
/*     */ import net.minecraft.client.gui.spectator.categories.SpectatorDetails;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class GuiSpectator
/*     */   extends Gui implements ISpectatorMenuRecipient {
/*  16 */   private static final ResourceLocation field_175267_f = new ResourceLocation("textures/gui/widgets.png");
/*  17 */   public static final ResourceLocation field_175269_a = new ResourceLocation("textures/gui/spectator_widgets.png");
/*     */   
/*     */   private final Minecraft field_175268_g;
/*     */   private long field_175270_h;
/*     */   private SpectatorMenu field_175271_i;
/*     */   
/*     */   public GuiSpectator(Minecraft mcIn) {
/*  24 */     this.field_175268_g = mcIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_175260_a(int p_175260_1_) {
/*  29 */     this.field_175270_h = Minecraft.getSystemTime();
/*     */     
/*  31 */     if (this.field_175271_i != null) {
/*     */       
/*  33 */       this.field_175271_i.func_178644_b(p_175260_1_);
/*     */     }
/*     */     else {
/*     */       
/*  37 */       this.field_175271_i = new SpectatorMenu(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private float func_175265_c() {
/*  43 */     long i = this.field_175270_h - Minecraft.getSystemTime() + 5000L;
/*  44 */     return MathHelper.clamp_float((float)i / 2000.0F, 0.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderTooltip(ScaledResolution p_175264_1_, float p_175264_2_) {
/*  49 */     if (this.field_175271_i != null) {
/*     */       
/*  51 */       float f = func_175265_c();
/*     */       
/*  53 */       if (f <= 0.0F) {
/*     */         
/*  55 */         this.field_175271_i.func_178641_d();
/*     */       }
/*     */       else {
/*     */         
/*  59 */         int i = p_175264_1_.getScaledWidth() / 2;
/*  60 */         float f1 = this.zLevel;
/*  61 */         this.zLevel = -90.0F;
/*  62 */         float f2 = p_175264_1_.getScaledHeight() - 22.0F * f;
/*  63 */         SpectatorDetails spectatordetails = this.field_175271_i.func_178646_f();
/*  64 */         func_175258_a(p_175264_1_, f, i, f2, spectatordetails);
/*  65 */         this.zLevel = f1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_175258_a(ScaledResolution p_175258_1_, float p_175258_2_, int p_175258_3_, float p_175258_4_, SpectatorDetails p_175258_5_) {
/*  72 */     GlStateManager.enableRescaleNormal();
/*  73 */     GlStateManager.enableBlend();
/*  74 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  75 */     GlStateManager.color(1.0F, 1.0F, 1.0F, p_175258_2_);
/*  76 */     this.field_175268_g.getTextureManager().bindTexture(field_175267_f);
/*  77 */     drawTexturedModalRect((p_175258_3_ - 91), p_175258_4_, 0, 0, 182, 22);
/*     */     
/*  79 */     if (p_175258_5_.func_178681_b() >= 0)
/*     */     {
/*  81 */       drawTexturedModalRect((p_175258_3_ - 91 - 1 + p_175258_5_.func_178681_b() * 20), p_175258_4_ - 1.0F, 0, 22, 24, 22);
/*     */     }
/*     */     
/*  84 */     RenderHelper.enableGUIStandardItemLighting();
/*     */     
/*  86 */     for (int i = 0; i < 9; i++)
/*     */     {
/*  88 */       func_175266_a(i, p_175258_1_.getScaledWidth() / 2 - 90 + i * 20 + 2, p_175258_4_ + 3.0F, p_175258_2_, p_175258_5_.func_178680_a(i));
/*     */     }
/*     */     
/*  91 */     RenderHelper.disableStandardItemLighting();
/*  92 */     GlStateManager.disableRescaleNormal();
/*  93 */     GlStateManager.disableBlend();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_175266_a(int p_175266_1_, int p_175266_2_, float p_175266_3_, float p_175266_4_, ISpectatorMenuObject p_175266_5_) {
/*  98 */     this.field_175268_g.getTextureManager().bindTexture(field_175269_a);
/*     */     
/* 100 */     if (p_175266_5_ != SpectatorMenu.field_178657_a) {
/*     */       
/* 102 */       int i = (int)(p_175266_4_ * 255.0F);
/* 103 */       GlStateManager.pushMatrix();
/* 104 */       GlStateManager.translate(p_175266_2_, p_175266_3_, 0.0F);
/* 105 */       float f = p_175266_5_.func_178662_A_() ? 1.0F : 0.25F;
/* 106 */       GlStateManager.color(f, f, f, p_175266_4_);
/* 107 */       p_175266_5_.func_178663_a(f, i);
/* 108 */       GlStateManager.popMatrix();
/* 109 */       String s = String.valueOf(GameSettings.getKeyDisplayString(this.field_175268_g.gameSettings.keyBindsHotbar[p_175266_1_].getKeyCode()));
/*     */       
/* 111 */       if (i > 3 && p_175266_5_.func_178662_A_())
/*     */       {
/* 113 */         this.field_175268_g.fontRendererObj.drawStringWithShadow(s, (p_175266_2_ + 19 - 2 - this.field_175268_g.fontRendererObj.getStringWidth(s)), p_175266_3_ + 6.0F + 3.0F, 16777215 + (i << 24));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderSelectedItem(ScaledResolution p_175263_1_) {
/* 120 */     int i = (int)(func_175265_c() * 255.0F);
/*     */     
/* 122 */     if (i > 3 && this.field_175271_i != null) {
/*     */       
/* 124 */       ISpectatorMenuObject ispectatormenuobject = this.field_175271_i.func_178645_b();
/* 125 */       String s = (ispectatormenuobject != SpectatorMenu.field_178657_a) ? ispectatormenuobject.getSpectatorName().getFormattedText() : this.field_175271_i.func_178650_c().func_178670_b().getFormattedText();
/*     */       
/* 127 */       if (s != null) {
/*     */         
/* 129 */         int j = (p_175263_1_.getScaledWidth() - this.field_175268_g.fontRendererObj.getStringWidth(s)) / 2;
/* 130 */         int k = p_175263_1_.getScaledHeight() - 35;
/* 131 */         GlStateManager.pushMatrix();
/* 132 */         GlStateManager.enableBlend();
/* 133 */         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 134 */         this.field_175268_g.fontRendererObj.drawStringWithShadow(s, j, k, 16777215 + (i << 24));
/* 135 */         GlStateManager.disableBlend();
/* 136 */         GlStateManager.popMatrix();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_175257_a(SpectatorMenu p_175257_1_) {
/* 143 */     this.field_175271_i = null;
/* 144 */     this.field_175270_h = 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_175262_a() {
/* 149 */     return (this.field_175271_i != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_175259_b(int p_175259_1_) {
/*     */     int i;
/* 156 */     for (i = this.field_175271_i.func_178648_e() + p_175259_1_; i >= 0 && i <= 8 && (this.field_175271_i.func_178643_a(i) == SpectatorMenu.field_178657_a || !this.field_175271_i.func_178643_a(i).func_178662_A_()); i += p_175259_1_);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     if (i >= 0 && i <= 8) {
/*     */       
/* 163 */       this.field_175271_i.func_178644_b(i);
/* 164 */       this.field_175270_h = Minecraft.getSystemTime();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_175261_b() {
/* 170 */     this.field_175270_h = Minecraft.getSystemTime();
/*     */     
/* 172 */     if (func_175262_a()) {
/*     */       
/* 174 */       int i = this.field_175271_i.func_178648_e();
/*     */       
/* 176 */       if (i != -1)
/*     */       {
/* 178 */         this.field_175271_i.func_178644_b(i);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 183 */       this.field_175271_i = new SpectatorMenu(this);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiSpectator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */