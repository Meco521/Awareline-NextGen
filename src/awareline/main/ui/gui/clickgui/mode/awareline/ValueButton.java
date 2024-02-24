/*     */ package awareline.main.ui.gui.clickgui.mode.awareline;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.font.cfont.CFontRenderer;
/*     */ import awareline.main.ui.font.fontmanager.UnicodeFontRenderer;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import awareline.main.utility.render.RoundedUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class ValueButton
/*     */ {
/*     */   public static int valuebackcolor;
/*     */   public final Value value;
/*  23 */   final UnicodeFontRenderer font = Client.instance.FontManager.regular16;
/*  24 */   final CFontRenderer mfont = Client.instance.FontLoaders.Comfortaa16;
/*     */   
/*     */   public String name;
/*     */   public boolean custom;
/*     */   public boolean change;
/*     */   public float x;
/*     */   public float y;
/*     */   
/*     */   public ValueButton(Value value, float x, float y) {
/*  33 */     this.value = value;
/*  34 */     this.x = x;
/*  35 */     this.y = y;
/*     */     
/*  37 */     this.name = "";
/*  38 */     if (this.value instanceof Option) {
/*  39 */       this.change = ((Boolean)this.value.getValue()).booleanValue();
/*  40 */     } else if (this.value instanceof Mode) {
/*  41 */       this.name = String.valueOf(this.value.getValue());
/*  42 */     } else if (value instanceof Numbers) {
/*  43 */       Numbers v = (Numbers)value;
/*  44 */       this.name += v.isInteger() ? ((Number)v.getValue()).intValue() : ((Number)v.getValue()).doubleValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(Window windows, float mouseX, float mouseY) {
/*  50 */     if (!this.custom) {
/*  51 */       if (this.value instanceof Option) {
/*  52 */         this.change = ((Boolean)this.value.getValue()).booleanValue();
/*  53 */       } else if (this.value instanceof Mode) {
/*  54 */         this.name = (String)((Mode)this.value).getValue();
/*  55 */       } else if (this.value instanceof Numbers) {
/*  56 */         Numbers v = (Numbers)this.value;
/*  57 */         this.name = String.valueOf(v.isInteger() ? ((Number)v.getValue()).intValue() : ((Number)v.getValue()).doubleValue());
/*  58 */         if (mouseX > this.x - 7.0F && mouseX < this.x + 85.0F + windows.allX && mouseY > this.y - 6.0F && mouseY < this.y + this.mfont.getStringHeight() + 5.0F && Mouse.isButtonDown(0)) {
/*  59 */           double min = v.getMinimum().doubleValue();
/*  60 */           double max = v.getMaximum().doubleValue();
/*  61 */           double inc = v.getIncrement().doubleValue();
/*  62 */           double valAbs = (mouseX - this.x + 1.0F);
/*  63 */           double perc = valAbs / 68.0D;
/*  64 */           perc = Math.min(Math.max(0.0D, perc), 1.0D);
/*  65 */           double valRel = (max - min) * perc;
/*  66 */           double val = min + valRel;
/*  67 */           val = Math.round(val * 1.0D / inc) / 1.0D / inc;
/*  68 */           v.setValue(Double.valueOf(val));
/*     */         } 
/*     */       } 
/*  71 */       if (this.value instanceof Numbers) {
/*  72 */         Numbers v = (Numbers)this.value;
/*  73 */         double render = (68.0F * (((Number)v.getValue()).floatValue() - v.getMinimum().floatValue()) / (v.getMaximum().floatValue() - v.getMinimum().floatValue()));
/*  74 */         v.smoothAnim = AnimationUtil.moveUD(v.smoothAnim, (float)render, SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/*  75 */         drawString(this.name, this.x + this.mfont.getStringWidth(this.value.getName()), this.y - 1.0F, -1);
/*  76 */         RoundedUtil.drawRound(this.x - 7.0F, this.y + 0.5F, (float)(v.smoothAnim + 6.5D) + windows.allX, 10.0F, 3.0F, new Color(((Double)HUD.r
/*  77 */               .getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue(), 120));
/*     */         
/*  79 */         SimpleRender.drawRectFloat(0.0F, 0.0F, 0.0F, 0.0F, 0);
/*  80 */         if (this.value.getName().length() >= "MaxPacketTriggerDist".length() - 4.0F) {
/*  81 */           drawString(this.value.getName().substring(0, this.value.getName().length() / 2 + this.value.getName().length() / 4) + " ", this.x - 5.0F, this.y - 1.0F, (new Color(255, 255, 255)).getRGB());
/*     */         } else {
/*  83 */           drawString(this.value.getName() + " ", this.x - 5.0F, this.y - 1.0F, (new Color(255, 255, 255)).getRGB());
/*     */         } 
/*     */       } 
/*  86 */       if (this.value instanceof Option) {
/*  87 */         drawString(this.value.getBreakName(), this.x - 5.0F, this.y - 1.0F, this.change ? Client.instance
/*  88 */             .getClientColorNoRainbow(255) : (new Color(108, 108, 108)).getRGB());
/*     */       }
/*  90 */       if (this.value instanceof Mode) {
/*  91 */         drawString(this.value.getName() + " ", this.x - 5.0F, this.y - 1.0F, (new Color(255, 255, 255)).getRGB());
/*  92 */         drawString(this.name, this.x + Client.instance.FontLoaders.Roboto16.getStringWidth(this.value.getName()) - 1.0F, this.y - 1.0F, (new Color(255, 255, 255))
/*  93 */             .getRGB());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drawString(String text, float x, float y, int color) {
/*  99 */     this.font.drawString(text, x, y + 1.0F, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public void key(char typedChar, int keyCode) {}
/*     */ 
/*     */   
/*     */   public void click(int mouseX, int mouseY, int button) {
/* 107 */     if (!this.custom && mouseX > this.x - 7.0F && mouseX < this.x + 85.0F && mouseY > this.y - 6.0F && mouseY < this.y + Client.instance.FontLoaders.Comfortaa16.getStringHeight() + 5.0F) {
/* 108 */       if (this.value instanceof Option) {
/* 109 */         Option m1 = (Option)this.value;
/* 110 */         m1.setValue(Boolean.valueOf(!((Boolean)m1.getValue()).booleanValue()));
/*     */         return;
/*     */       } 
/* 113 */       if (this.value instanceof Mode) {
/* 114 */         Mode m = (Mode)this.value;
/* 115 */         if (button == 0 || button == 1) {
/* 116 */           List<String> options = Arrays.asList(m.getModes());
/*     */           
/* 118 */           int index = options.indexOf(m.getValue());
/* 119 */           if (button == 0) {
/* 120 */             index++;
/*     */           } else {
/* 122 */             index--;
/*     */           } 
/* 124 */           if (index >= options.size()) {
/* 125 */             index = 0;
/* 126 */           } else if (index < 0) {
/* 127 */             index = options.size() - 1;
/*     */           } 
/* 129 */           m.setValue(m.getModes()[index]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\awareline\ValueButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */