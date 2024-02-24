/*     */ package awareline.main.ui.gui.clickgui.mode.astolfo;
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
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */   public float x;
/*     */   public float y;
/*     */   int staticColor;
/*     */   final CFontRenderer mfont;
/*     */   
/*     */   public ValueButton(Value value, float x, float y) {
/*  41 */     this.mfont = Client.instance.FontLoaders.bold15; this.value = value; this.x = x; this.y = y; this.name = ""; if (this.value instanceof Mode) {
/*     */       this.name = String.valueOf(this.value.getValue());
/*     */     } else if (value instanceof Numbers) {
/*     */       Numbers v = (Numbers)value;
/*     */       this.name += v.isInteger() ? ((Number)v.getValue()).intValue() : ((Number)v.getValue()).doubleValue();
/*  46 */     }  } public void render(int mouseX, int mouseY, Limitation limitation, Window parent) { if (!this.custom) {
/*  47 */       if (this.value instanceof Mode) {
/*  48 */         this.name = String.valueOf(this.value.getValue());
/*  49 */       } else if (this.value instanceof Numbers) {
/*  50 */         Numbers v = (Numbers)this.value;
/*  51 */         this.name = String.valueOf(v.isInteger() ? ((Number)v.getValue()).intValue() : ((Number)v.getValue()).doubleValue());
/*  52 */         if (mouseX > this.x - 7.0F && mouseX < this.x + 85.0F + parent.allX && mouseY > this.y + Client.instance.FontLoaders.bold15.getStringHeight() - 10.0F && mouseY < this.y + this.mfont.getStringHeight() + 2.0F && Mouse.isButtonDown(0)) {
/*  53 */           double min = v.getMinimum().doubleValue();
/*  54 */           double max = v.getMaximum().doubleValue();
/*  55 */           double inc = v.getIncrement().doubleValue();
/*  56 */           double valAbs = mouseX - this.x + 1.0D;
/*  57 */           double perc = valAbs / 68.0D;
/*  58 */           perc = Math.min(Math.max(0.0D, perc), 1.0D);
/*  59 */           double valRel = (max - min) * perc;
/*  60 */           double val = min + valRel;
/*  61 */           val = Math.round(val * 1.0D / inc) / 1.0D / inc;
/*  62 */           v.setValue(Double.valueOf(val));
/*     */         } 
/*     */       } 
/*  65 */       switch (parent.category.name()) {
/*     */         case "Combat":
/*  67 */           this.staticColor = (new Color(231, 76, 60)).getRGB();
/*     */           break;
/*     */         
/*     */         case "Render":
/*  71 */           this.staticColor = (new Color(54, 1, 205)).getRGB();
/*     */           break;
/*     */         
/*     */         case "Movement":
/*  75 */           this.staticColor = (new Color(45, 203, 113)).getRGB();
/*     */           break;
/*     */         
/*     */         case "Player":
/*  79 */           this.staticColor = (new Color(141, 68, 173)).getRGB();
/*     */           break;
/*     */         
/*     */         case "World":
/*  83 */           this.staticColor = (new Color(38, 154, 255)).getRGB();
/*     */           break;
/*     */         
/*     */         case "Misc":
/*  87 */           this.staticColor = (new Color(102, 101, 101)).getRGB();
/*     */           break;
/*     */       } 
/*     */       
/*  91 */       GL11.glEnable(3089);
/*  92 */       limitation.cut();
/*  93 */       Gui.drawRect((this.x - 10.0F), (this.y - 4.0F), (this.x + 80.0F + parent.allX), (this.y + 11.0F), (new Color(20, 20, 20)).getRGB());
/*  94 */       if (this.value instanceof Option) {
/*  95 */         this.mfont.drawString(this.value.getName(), this.x - 7.0F, this.y + 2.0F, 
/*  96 */             ((Boolean)this.value.getValue()).booleanValue() ? (new Color(255, 255, 255)).getRGB() : (new Color(108, 108, 108)).getRGB());
/*     */       }
/*  98 */       if (this.value instanceof Mode) {
/*  99 */         this.mfont.drawString(this.value.getName(), this.x - 7.0F, this.y + 3.0F, (new Color(255, 255, 255)).getRGB());
/* 100 */         this.mfont.drawString(this.name, this.x + 77.0F + parent.allX - this.mfont.getStringWidth(this.name), this.y + 3.0F, (new Color(182, 182, 182)).getRGB());
/*     */       } 
/* 102 */       if (this.value instanceof Numbers) {
/* 103 */         Numbers v = (Numbers)this.value;
/* 104 */         double render = ((82.0F + parent.allX) * AnimationUtil.getAnimationStateFlux((((Number)v.getValue()).floatValue() - v.getMinimum().floatValue()) / (v.getMaximum().floatValue() - v.getMinimum().floatValue()), 0.0F, 0.1F));
/* 105 */         v.smoothAnim = AnimationUtil.moveUD(v.smoothAnim, (float)render, SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/* 106 */         Gui.drawRect((this.x - 8.0F), (this.y + this.mfont.getStringHeight() + 2.0F), (this.x - 4.0F + v.smoothAnim), (this.y + this.mfont.getStringHeight() - 9.0F), this.staticColor);
/* 107 */         this.mfont.drawString(this.value.getName(), this.x - 7.0F, this.y, (new Color(255, 255, 255)).getRGB());
/* 108 */         this.mfont.drawString(this.name, this.x + this.mfont.getStringWidth(this.value.getName()), this.y, -1);
/*     */       } 
/* 110 */       GL11.glDisable(3089);
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/*     */   public void key(char typedChar, int keyCode) {}
/*     */ 
/*     */   
/*     */   public void click(int mouseX, int mouseY, int button, Window parent) {
/* 119 */     if (!this.custom && mouseX > this.x - 7.0F && mouseX < this.x + 85.0F && mouseY > this.y - 6.0F && mouseY < this.y + Client.instance.FontLoaders.bold15.getStringHeight()) {
/* 120 */       if (this.value instanceof Option) {
/* 121 */         Option v = (Option)this.value;
/* 122 */         v.setValue(Boolean.valueOf(!((Boolean)v.getValue()).booleanValue()));
/*     */         return;
/*     */       } 
/* 125 */       if (this.value instanceof Mode) {
/* 126 */         Mode m = (Mode)this.value;
/* 127 */         if (button == 0 || button == 1) {
/* 128 */           List<String> options = Arrays.asList(m.getModes());
/* 129 */           int index = options.indexOf(m.getValue());
/* 130 */           if (button == 0) {
/* 131 */             index++;
/*     */           } else {
/* 133 */             index--;
/*     */           } 
/* 135 */           if (index >= options.size()) {
/* 136 */             index = 0;
/* 137 */           } else if (index < 0) {
/* 138 */             index = options.size() - 1;
/*     */           } 
/* 140 */           m.setValue(m.getModes()[index]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\astolfo\ValueButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */