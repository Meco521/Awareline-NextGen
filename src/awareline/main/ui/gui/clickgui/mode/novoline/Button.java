/*     */ package awareline.main.ui.gui.clickgui.mode.novoline;
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
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Button
/*     */ {
/*     */   public final Module cheat;
/*     */   public Window parent;
/*     */   public float x;
/*     */   public float y;
/*     */   public int index;
/*     */   public int remander;
/*     */   public double opacity;
/*  26 */   public final ArrayList<ValueButton> buttons = new ArrayList<>();
/*     */   
/*     */   public boolean expand;
/*     */   
/*     */   boolean hover;
/*     */   
/*     */   int smoothalpha;
/*     */   
/*     */   float animationsize;
/*     */   
/*     */   float animationSize2;
/*     */   
/*     */   public boolean isExpand;
/*     */   
/*     */   final CFontRenderer font;
/*     */   
/*     */   public float arrow;
/*     */   
/*     */   public Button(Module cheat, float x, float y) {
/*  45 */     this.font = Client.instance.FontLoaders.Comfortaa16; this.cheat = cheat; this.x = x; this.y = y; float y2 = y + 15.0F; for (Value v : this.cheat.getValues()) {
/*     */       if (!v.isVisitable())
/*     */         continue;  this.buttons.add(new ValueButton(v, this.x + 5.0F, y2));
/*     */       y2 += 15.0F;
/*  49 */     }  } public void render(int mouseX, int mouseY, Limitation limitation) { float y2 = this.y + 15.0F;
/*  50 */     this.buttons.clear();
/*  51 */     for (Value v : this.cheat.getValues()) {
/*  52 */       if (((Boolean)ClickGui.Visitable.getValue()).booleanValue() && 
/*  53 */         !v.isVisitable())
/*     */         continue; 
/*  55 */       this.buttons.add(new ValueButton(v, this.x + 5.0F, y2));
/*  56 */       y2 += 15.0F;
/*     */     } 
/*  58 */     if (this.index != 0) {
/*  59 */       Button b2 = this.parent.buttons.get(this.index - 1);
/*  60 */       this.y = b2.y + 15.0F + this.animationsize;
/*  61 */       if (b2.expand) {
/*  62 */         this.parent.buttonanim = true;
/*  63 */         this.animationsize = AnimationUtil.moveUD(this.animationsize, (15 * b2.buttons.size()), 
/*  64 */             SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/*     */       } else {
/*  66 */         this.parent.buttonanim = true;
/*  67 */         this.animationsize = AnimationUtil.moveUD(this.animationsize, 0.0F, 
/*  68 */             SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/*     */       } 
/*  70 */       this.animationSize2 = this.animationsize;
/*     */     } 
/*  72 */     if (this.parent.buttonanim) {
/*  73 */       this.parent.buttonanim = false;
/*     */     }
/*  75 */     int i = 0;
/*  76 */     float size = this.buttons.size();
/*  77 */     while (i < size) {
/*  78 */       ((ValueButton)this.buttons.get(i)).y = this.y + 15.0F + (15 * i);
/*  79 */       ((ValueButton)this.buttons.get(i)).x = this.x + 5.0F;
/*  80 */       i++;
/*     */     } 
/*  82 */     smoothalphas();
/*  83 */     GL11.glPushMatrix();
/*  84 */     GL11.glEnable(3089);
/*  85 */     limitation.cut();
/*  86 */     this.hover = (mouseX > this.x - 7.0F && mouseX < this.x + 85.0F && mouseY > this.y - 6.0F && mouseY < this.y + this.font.getStringHeight() + 4.0F);
/*  87 */     SimpleRender.drawRectFloat(this.x - 5.0F, this.y - 5.0F, this.x + 85.0F + this.parent.allX, this.y + this.font.getStringHeight() + 5.0F, (new Color(40, 40, 40))
/*  88 */         .getRGB());
/*  89 */     SimpleRender.drawRectFloat(this.x - 5.0F, this.y - 5.0F - 1.0F, this.x + 85.0F + this.parent.allX, this.y + this.font.getStringHeight() + 3.0F + 1.0F, 
/*  90 */         hudcolorwithalpha());
/*  91 */     Client.instance.FontManager.SF17.drawStringWithShadow(this.cheat.getBreakName(true), this.x - 1.0F, this.y - 4.0F, -1);
/*     */     
/*  93 */     Color Ranbow = HUD.getInstance.fade(new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue(), this.smoothalpha), 70, 25);
/*  94 */     ValueButton.valuebackcolor = ((Boolean)HUD.dynamicColor.get()).booleanValue() ? Ranbow.getRGB() : (new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue())).getRGB();
/*  95 */     if (size >= 1.0F) {
/*  96 */       float rotateX = 5.0F;
/*  97 */       GlStateManager.pushMatrix();
/*  98 */       GlStateManager.translate(this.x + 73.0F + 5.0F + this.parent.allX, this.y + 2.0F, 0.0F);
/*  99 */       GlStateManager.rotate(this.arrow, 0.0F, 0.0F, 1.0F);
/* 100 */       if (this.expand && this.arrow < 180.0F) {
/* 101 */         this.arrow = AnimationUtil.moveUDSmooth(this.arrow, 180.0F);
/* 102 */       } else if (!this.expand && this.arrow > 0.0F) {
/* 103 */         this.arrow = AnimationUtil.moveUDSmooth(this.arrow, 0.0F);
/*     */       } 
/* 105 */       GlStateManager.translate(-(this.x + 73.0F + 5.0F + this.parent.allX), -(this.y + 2.0F), 0.0F);
/* 106 */       Client.instance.FontLoaders.FLUXICON16.drawString("i", this.x + 69.0F + 5.0F + this.parent.allX - (this.expand ? 0.5F : 0.0F), this.y + 1.0F - (this.expand ? 2.0F : 0.0F) + this.arrow / 100.0F, -1);
/*     */       
/* 108 */       GlStateManager.popMatrix();
/*     */     } 
/* 110 */     if (this.expand) {
/* 111 */       this.buttons.forEach(b -> b.render(mouseX, mouseY, this.parent));
/*     */     }
/* 113 */     GL11.glDisable(3089);
/* 114 */     GL11.glPopMatrix(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int hudcolorwithalpha() {
/* 120 */     return (new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue(), this.smoothalpha)).getRGB();
/*     */   }
/*     */   
/*     */   private void smoothalphas() {
/* 124 */     if (this.cheat.isEnabled()) {
/* 125 */       this.smoothalpha = (int)AnimationUtil.moveUD(this.smoothalpha, 255.0F, 
/* 126 */           SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/*     */     } else {
/* 128 */       this.smoothalpha = (int)AnimationUtil.moveUD(this.smoothalpha, 0.0F, 
/* 129 */           SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void key(char typedChar, int keyCode) {
/* 134 */     this.buttons.forEach(b -> b.key(typedChar, keyCode));
/*     */   }
/*     */   
/*     */   public void click(int mouseX, int mouseY, int button) {
/* 138 */     if (this.parent.drag) {
/*     */       return;
/*     */     }
/* 141 */     if (mouseX > this.x - 7.0F && mouseX < this.x + 85.0F + this.parent.allX && mouseY > this.y - 6.0F && mouseY < this.y + Client.instance.FontLoaders.SF17.getStringHeight()) {
/* 142 */       if (button == 0) {
/* 143 */         this.cheat.setEnabled(!this.cheat.isEnabled());
/*     */       }
/* 145 */       if (button == 1 && !this.buttons.isEmpty()) {
/* 146 */         this.expand = !this.expand;
/* 147 */         this.isExpand = !this.isExpand;
/*     */       } 
/*     */     } 
/* 150 */     if (this.expand) {
/* 151 */       this.buttons.forEach(b -> b.click(mouseX, mouseY, button));
/*     */     }
/*     */   }
/*     */   
/*     */   public void setParent(Window parent) {
/* 156 */     this.parent = parent;
/* 157 */     for (int i = 0; i < this.parent.buttons.size(); ) {
/* 158 */       if (this.parent.buttons.get(i) != this) { i++; continue; }
/* 159 */        this.index = i;
/* 160 */       this.remander = this.parent.buttons.size() - i;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\novoline\Button.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */