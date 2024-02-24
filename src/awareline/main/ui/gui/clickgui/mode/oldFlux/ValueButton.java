/*     */ package awareline.main.ui.gui.clickgui.mode.oldFlux;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.font.cfont.CFontRenderer;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ValueButton
/*     */ {
/*     */   public final Value value;
/*     */   public String name;
/*     */   public boolean custom;
/*     */   public boolean change;
/*     */   public int x;
/*     */   public float y;
/*     */   public double opacity;
/*     */   
/*     */   public ValueButton(Value value, int x, float y, Button parent) {
/*  27 */     this.value = value;
/*  28 */     this.x = x;
/*  29 */     this.y = y;
/*  30 */     this.name = "";
/*  31 */     if (this.value instanceof Option) {
/*  32 */       this.change = ((Boolean)this.value.getValue()).booleanValue();
/*  33 */     } else if (this.value instanceof Mode) {
/*  34 */       this.name = String.valueOf(this.value.getValue());
/*  35 */     } else if (value instanceof Numbers) {
/*  36 */       Numbers v = (Numbers)value;
/*  37 */       this.name += v.getValue();
/*     */     } 
/*  39 */     this.opacity = 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(int mouseX, int mouseY, Window parent) {
/*  44 */     CFontRenderer mFont = Client.instance.FontLoaders.Comfortaa16;
/*  45 */     if (!this.custom) {
/*  46 */       this.opacity = (mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6.0F && mouseY < this.y + mFont.getStringHeight() + 5.0F) ? ((this.opacity + 10.0D < 200.0D) ? (this.opacity += 10.0D) : 200.0D) : ((this.opacity - 6.0D > 0.0D) ? (this.opacity -= 6.0D) : 0.0D);
/*  47 */       Gui.drawRect((this.x - 9), (this.y - 4.0F), (this.x - 9 + 88), (this.y + 15.0F), (new Color(255, 255, 255, (int)this.opacity)).getRGB());
/*  48 */       if (this.change) {
/*  49 */         mFont.drawString(this.value.getName(), (this.x - 5), this.y + 1.0F, parent
/*  50 */             .hudColor());
/*     */       }
/*  52 */       if (this.value instanceof Option) {
/*  53 */         this.change = ((Boolean)this.value.getValue()).booleanValue();
/*  54 */       } else if (this.value instanceof Mode) {
/*  55 */         this.name = String.valueOf(this.value.getValue());
/*  56 */       } else if (this.value instanceof Numbers) {
/*  57 */         Numbers v = (Numbers)this.value;
/*  58 */         this.name = String.valueOf(v.getValue());
/*  59 */         if (mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6.0F && mouseY < this.y + mFont.getStringHeight() + 5.0F && Mouse.isButtonDown(0)) {
/*  60 */           double min = v.getMinimum().doubleValue();
/*  61 */           double max = v.getMaximum().doubleValue();
/*  62 */           double inc = v.getIncrement().doubleValue();
/*  63 */           double valAbs = mouseX - this.x + 1.0D;
/*  64 */           double per = valAbs / 68.0D;
/*  65 */           per = Math.min(Math.max(0.0D, per), 1.0D);
/*  66 */           double valRel = (max - min) * per;
/*  67 */           double val = min + valRel;
/*  68 */           val = Math.round(val * 1.0D / inc) / 1.0D / inc;
/*  69 */           v.setValue(Double.valueOf(val));
/*     */         } 
/*  71 */         double render = (68.0F * (((Number)v.getValue()).floatValue() - v.getMinimum().floatValue()) / (v.getMaximum().floatValue() - v.getMinimum().floatValue()));
/*  72 */         RenderUtil.circle((float)(this.x + render + 2.0D) + 2.0F, this.y + 8.0F, 2.0F, (new Color(240, 240, 240)).getRGB());
/*  73 */         RenderUtil.drawRect((this.x - 5), this.y + mFont.getStringHeight() + 2.0F, (float)((this.x + 58) + 1.0D), this.y + mFont.getStringHeight() + 3.0F, (new Color(191, 191, 191))
/*  74 */             .getRGB());
/*  75 */         RenderUtil.drawRect((this.x - 5), this.y + mFont.getStringHeight() + 2.0F, (float)(this.x + render + 1.0D), this.y + mFont.getStringHeight() + 3.0F, parent
/*  76 */             .hudColor());
/*     */       } 
/*  78 */       RenderUtil.drawRect(0.0D, 0.0D, 0.0D, 0.0D, 0);
/*  79 */       if (!this.change) {
/*  80 */         mFont.drawString(this.value.getName(), (this.x - 5), this.y + 1.0F, -1);
/*     */       }
/*  82 */       if (this.value instanceof Mode) {
/*  83 */         Client.instance.FontLoaders.Comfortaa14.drawString(this.name, (this.x + 80 - mFont
/*  84 */             .getStringWidth(this.name) + parent.allX), this.y + 2.0F, -1);
/*     */       } else {
/*     */         
/*  87 */         mFont.drawString(this.name, (this.x + 76 - mFont.getStringWidth(this.name) + parent.allX), this.y + 1.0F, -1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void click(int mouseX, int mouseY) {
/*  94 */     if (!this.custom && mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6.0F && mouseY < this.y + Client.instance.FontLoaders.Comfortaa16.getStringHeight() + 5.0F) {
/*  95 */       if (this.value instanceof Option) {
/*  96 */         Option m1 = (Option)this.value;
/*  97 */         m1.setValue(Boolean.valueOf(!((Boolean)m1.getValue()).booleanValue()));
/*     */         
/*     */         return;
/*     */       } 
/* 101 */       if (this.value instanceof Mode) {
/* 102 */         Mode m = (Mode)this.value;
/* 103 */         String current = (String)m.getValue();
/*     */         int next;
/* 105 */         for (next = 0; next < (m.getModes()).length; ) {
/* 106 */           if (m.getModes()[next].equals(current)) {
/* 107 */             next++;
/*     */             break;
/*     */           } 
/* 110 */           next++;
/*     */         } 
/* 112 */         this.value.setValue(m.getModes()[(next == (m.getModes()).length) ? 0 : next]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\oldFlux\ValueButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */