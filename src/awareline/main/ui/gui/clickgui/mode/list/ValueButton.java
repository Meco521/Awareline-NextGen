/*     */ package awareline.main.ui.gui.clickgui.mode.list;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.font.cfont.CFontRenderer;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import java.awt.Color;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.lwjgl.input.Mouse;
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
/*     */ 
/*     */ 
/*     */ public class ValueButton
/*     */ {
/*     */   public final Value value;
/*     */   public String name;
/*     */   public final boolean custom;
/*     */   public boolean change;
/*     */   public float x;
/*     */   public float y;
/*     */   final CFontRenderer mfont;
/*     */   
/*     */   public ValueButton(Value value, float x, float y) {
/*  43 */     this.mfont = Client.instance.FontLoaders.Comfortaa16; this.custom = false; this.value = value; this.x = x; this.y = y; this.name = ""; if (this.value instanceof Option) { this.change = ((Boolean)this.value.getValue()).booleanValue(); }
/*     */     else if (this.value instanceof Mode)
/*     */     { this.name = String.valueOf(this.value.getValue()); }
/*     */     else if (value instanceof Numbers)
/*     */     { Numbers v = (Numbers)value; this.name += v.isInteger() ? ((Number)v.getValue()).intValue() : ((Number)v.getValue()).doubleValue(); }
/*  48 */      } public void render(Window windows, float mouseX, float mouseY) { if (!this.custom) {
/*  49 */       if (this.value instanceof Option) {
/*  50 */         this.change = ((Boolean)this.value.getValue()).booleanValue();
/*  51 */       } else if (this.value instanceof Mode) {
/*  52 */         this.name = (String)((Mode)this.value).getValue();
/*  53 */       } else if (this.value instanceof Numbers) {
/*  54 */         Numbers v = (Numbers)this.value;
/*  55 */         this.name = String.valueOf(v.isInteger() ? ((Number)v.getValue()).intValue() : ((Number)v.getValue()).doubleValue());
/*  56 */         if (mouseX > this.x - 7.0F && mouseX < this.x + 85.0F + windows.allX && mouseY > this.y - 6.0F && mouseY < this.y + this.mfont.getStringHeight() + 5.0F && Mouse.isButtonDown(0)) {
/*  57 */           double min = v.getMinimum().doubleValue();
/*  58 */           double max = v.getMaximum().doubleValue();
/*  59 */           double inc = v.getIncrement().doubleValue();
/*  60 */           double valAbs = (mouseX - this.x + 1.0F);
/*  61 */           double perc = valAbs / 68.0D;
/*  62 */           perc = Math.min(Math.max(0.0D, perc), 1.0D);
/*  63 */           double valRel = (max - min) * perc;
/*  64 */           double val = min + valRel;
/*  65 */           val = Math.round(val * 1.0D / inc) / 1.0D / inc;
/*  66 */           v.setValue(Double.valueOf(val));
/*     */         } 
/*     */       } 
/*  69 */       if (this.value instanceof Numbers) {
/*  70 */         Numbers v = (Numbers)this.value;
/*  71 */         double render = (68.0F * (((Number)v.getValue()).floatValue() - v.getMinimum().floatValue()) / (v.getMaximum().floatValue() - v.getMinimum().floatValue()));
/*  72 */         v.smoothAnim = AnimationUtil.moveUD(v.smoothAnim, (float)render, SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/*  73 */         drawString(this.name, this.x + this.mfont.getStringWidth(this.value.getName()), this.y - 1.0F, -1);
/*  74 */         RenderUtil.drawGradientSidewaysV((this.x - 6.0F), (this.y - 3.0F), ((float)((this.x + v.smoothAnim) + 6.5D) + windows.allX), (this.y + 7.0F), (new Color(((Double)HUD.r
/*  75 */               .getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue(), 120)).getRGB(), (new Color(((Double)HUD.r
/*  76 */               .getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue(), 0)).getRGB());
/*  77 */         SimpleRender.drawRectFloat(0.0F, 0.0F, 0.0F, 0.0F, 0);
/*  78 */         if (this.value.getName().length() >= "MaxPacketTriggerDist".length() - 4.0F) {
/*  79 */           drawString(this.value.getName().substring(0, this.value.getName().length() / 2 + this.value.getName().length() / 4) + ":", this.x - 5.0F, this.y - 1.0F, (new Color(255, 255, 255)).getRGB());
/*     */         } else {
/*  81 */           drawString(this.value.getName() + ":", this.x - 5.0F, this.y - 1.0F, (new Color(255, 255, 255)).getRGB());
/*     */         } 
/*     */       } 
/*  84 */       if (this.value instanceof Option) {
/*  85 */         drawString(this.value.getBreakName(), this.x - 5.0F, this.y - 1.0F, this.change ? Client.instance
/*  86 */             .getClientColorNoRainbow(255) : (new Color(108, 108, 108)).getRGB());
/*     */       }
/*  88 */       if (this.value instanceof Mode) {
/*  89 */         drawString(this.value.getName() + ":", this.x - 5.0F, this.y - 1.0F, (new Color(255, 255, 255)).getRGB());
/*  90 */         drawString(this.name, this.x + Client.instance.FontLoaders.Roboto16.getStringWidth(this.value.getName()) - 1.0F, this.y - 1.0F, (new Color(255, 255, 255))
/*  91 */             .getRGB());
/*     */       } 
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawString(String text, float x, float y, int color) {
/*  98 */     Client.instance.FontManager.regular16.drawString(text, x + 2.0F, y - 1.0F, color);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void click(int mouseX, int mouseY, int button) {
/* 104 */     if (!this.custom && mouseX > this.x - 7.0F && mouseX < this.x + 85.0F && mouseY > this.y - 6.0F && mouseY < this.y + Client.instance.FontLoaders.Comfortaa16.getStringHeight() + 5.0F) {
/* 105 */       if (this.value instanceof Option) {
/* 106 */         Option m1 = (Option)this.value;
/* 107 */         m1.setValue(Boolean.valueOf(!((Boolean)m1.getValue()).booleanValue()));
/*     */         return;
/*     */       } 
/* 110 */       if (this.value instanceof Mode) {
/* 111 */         Mode m = (Mode)this.value;
/* 112 */         if (button == 0 || button == 1) {
/* 113 */           List<String> options = Arrays.asList(m.getModes());
/*     */           
/* 115 */           int index = options.indexOf(m.getValue());
/* 116 */           if (button == 0) {
/* 117 */             index++;
/*     */           } else {
/* 119 */             index--;
/*     */           } 
/* 121 */           if (index >= options.size()) {
/* 122 */             index = 0;
/* 123 */           } else if (index < 0) {
/* 124 */             index = options.size() - 1;
/*     */           } 
/* 126 */           m.setValue(m.getModes()[index]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\list\ValueButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */