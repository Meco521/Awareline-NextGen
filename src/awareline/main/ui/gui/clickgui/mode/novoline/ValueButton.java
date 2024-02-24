/*     */ package awareline.main.ui.gui.clickgui.mode.novoline;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.font.cfont.CFontRenderer;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import java.awt.Color;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.renderer.GlStateManager;
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
/*     */ 
/*     */ 
/*     */ public class ValueButton
/*     */ {
/*     */   public final Value value;
/*     */   public String name;
/*     */   public boolean custom;
/*     */   public boolean change;
/*     */   public float x;
/*     */   public float y;
/*     */   public static int valuebackcolor;
/*     */   float number;
/*     */   final CFontRenderer font;
/*     */   
/*     */   public ValueButton(Value value, float x, float y) {
/*  47 */     this.font = Client.instance.FontLoaders.SF16; this.value = value; this.x = x; this.y = y; this.name = ""; if (this.value instanceof Option) { this.change = ((Boolean)this.value.getValue()).booleanValue(); }
/*     */     else if (this.value instanceof Mode)
/*     */     { this.name = String.valueOf(this.value.getValue()); }
/*     */     else if (value instanceof Numbers)
/*     */     { Numbers v = (Numbers)value; this.name += v.isInteger() ? ((Number)v.getValue()).intValue() : ((Number)v.getValue()).doubleValue(); }
/*  52 */      } public void render(int mouseX, int mouseY, Window parent) { SimpleRender.drawRectFloat(this.x - 10.0F, this.y - 5.0F, this.x + 80.0F + parent.allX, this.y + 11.0F, (new Color(40, 40, 40)).getRGB());
/*  53 */     if (this.value instanceof Option) {
/*  54 */       this.change = ((Boolean)this.value.getValue()).booleanValue();
/*  55 */     } else if (this.value instanceof Mode) {
/*  56 */       this.name = String.valueOf(this.value.getValue()).toUpperCase();
/*  57 */     } else if (this.value instanceof Numbers) {
/*  58 */       Numbers v = (Numbers)this.value;
/*  59 */       this.name = String.valueOf(((Number)v.getValue()).doubleValue());
/*  60 */       this.font.getClass(); if (mouseX > this.x - 9.0F && mouseX < this.x + 87.0F && mouseY > this.y - 4.0F && mouseY < this.y + 0.0F + 4.0F && Mouse.isButtonDown(0)) {
/*  61 */         double min = v.getMinimum().doubleValue();
/*  62 */         double max = v.getMaximum().doubleValue();
/*  63 */         double inc = v.getIncrement().doubleValue();
/*  64 */         double valAbs = (mouseX - this.x + 1.0F);
/*  65 */         double perc = valAbs / 68.0D;
/*  66 */         perc = Math.min(Math.max(0.0D, perc), 1.0D);
/*  67 */         double valRel = (max - min) * perc;
/*  68 */         double val = min + valRel;
/*  69 */         val = Math.round(val * 1.0D / inc) / 1.0D / inc;
/*  70 */         v.setValue(Double.valueOf(val));
/*     */       } 
/*  72 */       this.number = 86.0F * (((Number)v.getValue()).floatValue() - v.getMinimum().floatValue()) / (v.getMaximum().floatValue() - v.getMinimum().floatValue());
/*  73 */       GlStateManager.pushMatrix();
/*  74 */       GlStateManager.translate(-9.0F, 1.0F, 0.0F);
/*  75 */       this.font.getClass(); Gui.drawRect((this.x + 1.0F), (this.y - 6.0F), (this.x + 87.0F + parent.allX), (this.y + 0.0F + 6.0F), (new Color(29, 29, 29)).getRGB());
/*  76 */       v.smoothAnim = AnimationUtil.moveUD(v.smoothAnim, this.number, SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/*  77 */       this.font.getClass(); SimpleRender.drawRectFloat(this.x + 1.0F, this.y - 6.0F, this.x + v.smoothAnim + 1.0F + parent.allX, this.y + 0.0F + 6.0F, valuebackcolor);
/*  78 */       GlStateManager.popMatrix();
/*     */     } 
/*  80 */     if (this.value instanceof Option) {
/*  81 */       float size = 2.0F;
/*  82 */       if (this.change) {
/*  83 */         SimpleRender.drawRectFloat(this.x + 62.0F + 2.0F + parent.allX + 4.0F, this.y - 4.0F + 2.0F - 1.0F, this.x + 76.0F - 2.0F + parent.allX + 4.0F, this.y + 9.0F - 2.0F, (new Color(29, 29, 29)).getRGB());
/*  84 */         Client.instance.FontLoaders.novoicons18.drawString("H", this.x + 64.5F + parent.allX + 4.0F, this.y + 1.0F, valuebackcolor);
/*     */       } else {
/*  86 */         SimpleRender.drawRectFloat(this.x + 62.0F + 2.0F + parent.allX + 4.0F, this.y - 4.0F + 2.0F - 1.0F, this.x + 76.0F - 2.0F + parent.allX + 4.0F, this.y + 9.0F - 2.0F, (new Color(29, 29, 29)).getRGB());
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     if (!(this.value instanceof Numbers)) {
/*  91 */       this.font.drawString(this.value.getName(), this.x - 7.0F, this.y, -1);
/*     */     }
/*  93 */     if (this.value instanceof Option) {
/*  94 */       this.font.drawString(this.name, this.x + this.font.getStringWidth(this.value.getName()), this.y, -1);
/*     */     }
/*  96 */     if (this.value instanceof Numbers) {
/*  97 */       this.font.drawString(this.value.getName(), this.x - 7.0F, this.y - 1.0F, -1);
/*  98 */       this.font.drawString(this.name, this.x + this.font.getStringWidth(this.value.getName()), this.y - 1.0F, -1);
/*     */     } 
/* 100 */     if (this.value instanceof Mode) {
/* 101 */       this.font.drawString(this.name, this.x + 90.0F - this.font.getStringWidth(this.name), this.y, -1);
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void key(char typedChar, int keyCode) {}
/*     */ 
/*     */   
/*     */   public void click(int mouseX, int mouseY, int button) {
/* 111 */     Client.instance.FontLoaders.Comfortaa18.getClass(); if (!this.custom && mouseX > this.x - 9.0F && mouseX < this.x + 87.0F && mouseY > this.y - 4.0F && mouseY < this.y + 0.0F + 4.0F) {
/* 112 */       if (this.value instanceof Option) {
/* 113 */         Option m1 = (Option)this.value;
/* 114 */         m1.setValue(Boolean.valueOf(!((Boolean)m1.getValue()).booleanValue()));
/*     */         return;
/*     */       } 
/* 117 */       if (this.value instanceof Mode) {
/* 118 */         Mode m = (Mode)this.value;
/* 119 */         if (button == 0 || button == 1) {
/* 120 */           List<String> options = Arrays.asList(m.getModes());
/*     */           
/* 122 */           int index = options.indexOf(m.getValue());
/* 123 */           if (button == 0) {
/* 124 */             index++;
/*     */           } else {
/* 126 */             index--;
/*     */           } 
/* 128 */           if (index >= options.size()) {
/* 129 */             index = 0;
/* 130 */           } else if (index < 0) {
/* 131 */             index = options.size() - 1;
/*     */           } 
/* 133 */           m.setValue(m.getModes()[index]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\novoline\ValueButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */