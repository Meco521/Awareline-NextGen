/*     */ package awareline.main.ui.gui.clickgui.mode.awareline;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.mod.implement.visual.ctype.ClickGui;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.font.cfont.CFontRenderer;
/*     */ import awareline.main.ui.gui.clickgui.mode.astolfo.Limitation;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import awareline.main.utility.render.RoundedUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Button
/*     */ {
/*     */   public final Module cheat;
/*  20 */   public final ArrayList<ValueButton> buttons = new ArrayList<>();
/*  21 */   final CFontRenderer font = Client.instance.FontLoaders.Comfortaa16; public Window parent;
/*     */   public float x;
/*     */   public float y;
/*     */   public int index;
/*     */   public int remander;
/*     */   public double opacity;
/*     */   public boolean expand;
/*     */   public boolean isExpand;
/*     */   public float arrow;
/*     */   boolean hover;
/*     */   int smoothalpha;
/*     */   float animationsize;
/*     */   float animationSize2;
/*     */   
/*     */   public Button(Module cheat, float x, float y) {
/*  36 */     this.cheat = cheat;
/*  37 */     this.x = x;
/*  38 */     this.y = y;
/*  39 */     float y2 = y + 15.0F;
/*  40 */     for (Value v : this.cheat.getValues()) {
/*  41 */       if (!v.isVisitable()) {
/*     */         continue;
/*     */       }
/*  44 */       this.buttons.add(new ValueButton(v, this.x + 5.0F, y2));
/*  45 */       y2 += 15.0F;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void render(int mouseX, int mouseY, Limitation limitation) {
/*  50 */     float y2 = this.y + 15.0F;
/*     */     
/*  52 */     this.buttons.clear();
/*     */     
/*  54 */     for (Value v : this.cheat.getValues()) {
/*  55 */       if (((Boolean)ClickGui.Visitable.getValue()).booleanValue() && !v.isVisitable()) {
/*     */         continue;
/*     */       }
/*  58 */       this.buttons.add(new ValueButton(v, this.x + 5.0F, y2));
/*  59 */       y2 += 15.0F;
/*     */     } 
/*     */     
/*  62 */     if (this.index != 0) {
/*  63 */       Button b2 = this.parent.buttons.get(this.index - 1);
/*     */       
/*  65 */       this.y = b2.y + 20.0F + this.animationsize;
/*  66 */       if (b2.expand) {
/*  67 */         this.parent.buttonanim = true;
/*  68 */         this.animationsize = AnimationUtil.moveUD(this.animationsize, (15 * b2.buttons.size()), 
/*  69 */             SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/*     */       } else {
/*  71 */         this.parent.buttonanim = true;
/*  72 */         this.animationsize = AnimationUtil.moveUD(this.animationsize, 0.0F, 
/*  73 */             SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/*     */       } 
/*  75 */       this.animationSize2 = this.animationsize;
/*     */     } 
/*  77 */     if (this.parent.buttonanim) {
/*  78 */       this.parent.buttonanim = false;
/*     */     }
/*  80 */     int i = 0;
/*  81 */     float size = this.buttons.size();
/*  82 */     while (i < size) {
/*  83 */       ((ValueButton)this.buttons.get(i)).y = this.y + 15.0F + (15 * i);
/*  84 */       ((ValueButton)this.buttons.get(i)).x = this.x + 7.0F;
/*  85 */       i++;
/*     */     } 
/*  87 */     smoothalphas();
/*  88 */     GL11.glPushMatrix();
/*  89 */     GL11.glEnable(3089);
/*  90 */     limitation.cut();
/*  91 */     this.hover = (mouseX > this.x - 7.0F && mouseX < this.x + 85.0F && mouseY > this.y - 6.0F && mouseY < this.y + this.font.getStringHeight() + 4.0F);
/*  92 */     RoundedUtil.drawRound(this.x - 2.0F, this.y - 5.0F, 84.0F + this.parent.allX, (this.font.getStringHeight() + 10), 5.0F, new Color(52, 48, 51));
/*     */ 
/*     */     
/*  95 */     RoundedUtil.drawRound(this.x - 2.0F, this.y - 5.0F, 84.0F + this.parent.allX, (this.font.getStringHeight() + 10), 5.0F, 
/*  96 */         hudcolorwithalpha());
/*  97 */     Client.instance.FontManager.regular16.drawString(this.cheat.getBreakName(true), this.x + 51.0F - (Client.instance.FontManager.regular16
/*     */         
/*  99 */         .getStringWidth(this.cheat.getBreakName(true)) / 2), this.y - 3.0F, -1);
/*     */ 
/*     */     
/* 102 */     Color Ranbow = HUD.getInstance.fade(new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue(), this.smoothalpha), 70, 25);
/*     */     
/* 104 */     ValueButton.valuebackcolor = ((Boolean)HUD.dynamicColor.get()).booleanValue() ? Ranbow.getRGB() : (new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue())).getRGB();
/* 105 */     if (this.expand) {
/* 106 */       this.buttons.forEach(b -> b.render(this.parent, mouseX, mouseY));
/*     */     }
/* 108 */     GL11.glDisable(3089);
/* 109 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private Color hudcolorwithalpha() {
/* 113 */     return new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue(), this.smoothalpha);
/*     */   }
/*     */   
/*     */   private void smoothalphas() {
/* 117 */     if (this.cheat.isEnabled()) {
/* 118 */       this.smoothalpha = (int)AnimationUtil.moveUD(this.smoothalpha, 255.0F, 
/* 119 */           SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/*     */     } else {
/* 121 */       this.smoothalpha = (int)AnimationUtil.moveUD(this.smoothalpha, 0.0F, 
/* 122 */           SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void key(char typedChar, int keyCode) {
/* 127 */     this.buttons.forEach(b -> b.key(typedChar, keyCode));
/*     */   }
/*     */   
/*     */   public void click(int mouseX, int mouseY, int button) {
/* 131 */     if (this.parent.drag) {
/*     */       return;
/*     */     }
/* 134 */     if (mouseX > this.x - 7.0F && mouseX < this.x + 85.0F + this.parent.allX && mouseY > this.y - 6.0F && mouseY < this.y + Client.instance.FontLoaders.SF17.getStringHeight()) {
/* 135 */       if (button == 0) {
/* 136 */         this.cheat.setEnabled(!this.cheat.isEnabled());
/*     */       }
/* 138 */       if (button == 1 && !this.buttons.isEmpty()) {
/* 139 */         this.expand = !this.expand;
/* 140 */         this.isExpand = !this.isExpand;
/*     */       } 
/*     */     } 
/* 143 */     if (this.expand) {
/* 144 */       this.buttons.forEach(b -> b.click(mouseX, mouseY, button));
/*     */     }
/*     */   }
/*     */   
/*     */   public void setParent(Window parent) {
/* 149 */     this.parent = parent;
/* 150 */     int size = this.parent.buttons.size();
/* 151 */     for (int i = 0; i < size; ) {
/* 152 */       if (this.parent.buttons.get(i) != this) { i++; continue; }
/* 153 */        this.index = i;
/* 154 */       this.remander = size - i;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\awareline\Button.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */