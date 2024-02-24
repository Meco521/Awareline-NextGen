/*     */ package awareline.main.ui.gui.clickgui.mode.list;
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
/*     */ import awareline.main.utility.render.GuiRenderUtils;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class Button
/*     */ {
/*     */   public final Module cheat;
/*     */   public Window parent;
/*     */   public float x;
/*     */   public float y;
/*     */   public int index;
/*  27 */   public final List<ValueButton> buttons = new ArrayList<>();
/*     */   
/*     */   public boolean expand;
/*     */   
/*     */   boolean hover;
/*     */   
/*     */   float x2;
/*     */   
/*     */   private int smoothalpha;
/*     */   
/*     */   public float animationSize;
/*     */   
/*     */   final CFontRenderer font;
/*     */   
/*     */   public float y2;
/*     */   
/*     */   public float arrow;
/*     */   
/*     */   public int remander;
/*     */ 
/*     */   
/*     */   public Button(Module cheat, float x, float y, int buttonAlpha) {
/*  49 */     this.font = Client.instance.FontLoaders.Comfortaa18; this.cheat = cheat; this.x = x; this.y = y; float y2 = y + 15.0F; this.smoothalpha = buttonAlpha; for (Value v : this.cheat.getValues()) {
/*     */       if (!v.isVisitable())
/*     */         continue; 
/*     */       this.buttons.add(new ValueButton(v, this.x + 5.0F, y2));
/*     */       y2 += 15.0F;
/*  54 */     }  } public void render(float mouseX, float mouseY, Limitation limitation) { this.y2 = this.y + 15.0F;
/*  55 */     GL11.glEnable(3089);
/*  56 */     limitation.cut();
/*     */     
/*  58 */     this.buttons.clear();
/*  59 */     for (Value value : this.cheat.getValues()) {
/*  60 */       if (((Boolean)ClickGui.Visitable.getValue()).booleanValue() && 
/*  61 */         !value.isVisitable()) {
/*     */         continue;
/*     */       }
/*     */       
/*  65 */       this.buttons.add(new ValueButton(value, this.x + 5.0F, this.y2));
/*  66 */       this.y2 += 15.0F;
/*     */     } 
/*     */     
/*  69 */     if (this.index != 0.0F) {
/*  70 */       Button button = this.parent.buttons.get(this.index - 1);
/*  71 */       this.y = button.y + 17.0F + this.animationSize;
/*  72 */       if (button.expand) {
/*  73 */         this.animationSize = AnimationUtil.moveUD(this.animationSize, 15.0F * button.buttons.size());
/*     */       } else {
/*  75 */         this.animationSize = AnimationUtil.moveUD(this.animationSize, 0.0F);
/*     */       } 
/*     */     } 
/*     */     
/*  79 */     int i = 0;
/*  80 */     while (i < this.buttons.size()) {
/*  81 */       ((ValueButton)this.buttons.get(i)).y = this.y + 15.0F + 15.0F * i;
/*  82 */       ((ValueButton)this.buttons.get(i)).x = this.x + 5.0F;
/*  83 */       i++;
/*     */     } 
/*     */ 
/*     */     
/*  87 */     this.hover = (mouseX > this.x - 7.0F && mouseX < this.x + 85.0F && mouseY > this.y - 6.0F && mouseY < this.y + this.font.getStringHeight() + 4.0F);
/*     */     
/*  89 */     if (this.cheat.isEnabled()) {
/*  90 */       this.smoothalpha = (int)AnimationUtil.moveUD(this.smoothalpha, this.parent.globalAlpha, 
/*  91 */           SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/*     */     } else {
/*  93 */       this.smoothalpha = (int)AnimationUtil.moveUD(this.smoothalpha, (this.parent.globalAlpha / 255), 
/*  94 */           SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/*     */     } 
/*     */     
/*  97 */     if (!this.cheat.isEnabled()) {
/*  98 */       GuiRenderUtils.drawRect(this.x - 5.0F, this.y - 5.0F, 90.0F + this.parent.allX, 17.0F, (new Color(40, 40, 40, 255))
/*  99 */           .getRGB());
/*     */     }
/*     */     
/* 102 */     GuiRenderUtils.drawRect(this.x - 5.0F, this.y - 5.0F, 90.0F + this.parent.allX, 17.0F, (new Color(((Double)HUD.r
/* 103 */           .getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue(), this.smoothalpha)).getRGB());
/*     */     
/* 105 */     if (this.parent.globalAlpha >= 20) {
/* 106 */       if (this.buttons.size() >= 1) {
/* 107 */         float rotateX = 2.0F;
/* 108 */         GlStateManager.pushMatrix();
/* 109 */         GlStateManager.translate(this.x + 73.0F + 2.0F + this.parent.allX, this.y + 2.0F, 0.0F);
/* 110 */         GlStateManager.rotate(this.arrow, 0.0F, 0.0F, 1.0F);
/* 111 */         if (this.expand && this.arrow < 180.0F) {
/* 112 */           this.arrow = AnimationUtil.moveUDSmooth(this.arrow, 180.0F);
/* 113 */         } else if (!this.expand && this.arrow > 0.0F) {
/* 114 */           this.arrow = AnimationUtil.moveUDSmooth(this.arrow, 0.0F);
/*     */         } 
/* 116 */         GlStateManager.translate(-(this.x + 73.0F + 2.0F + this.parent.allX), -(this.y + 2.0F), 0.0F);
/*     */         
/* 118 */         Client.instance.FontLoaders.FLUXICON17.drawString("i", this.x + 69.0F + 2.0F + this.parent.allX - (this.expand ? 0.5F : 0.0F), this.y + 2.0F - (this.expand ? 4.0F : 0.0F) + this.arrow / 100.0F, (new Color(255, 255, 255, this.parent.globalAlpha))
/*     */             
/* 120 */             .getRGB());
/*     */         
/* 122 */         GlStateManager.popMatrix();
/*     */       } 
/* 124 */       if (this.expand) {
/* 125 */         this.buttons.forEach(component -> component.render(this.parent, mouseX, mouseY));
/*     */       }
/*     */     } 
/*     */     
/* 129 */     if (this.parent.globalAlpha >= 20) {
/* 130 */       Limitation.doGlScissor(this.parent.x, this.parent.y + 17.0F, ((Boolean)HUD.getInstance.fontLowerCase.get()).booleanValue() ? 75.0F : 70.0F, this.parent.y + this.parent.expand - 35.0F);
/* 131 */       drawString(this.cheat.getHUDName(), this.x, this.y, (new Color(255, 255, 255, this.parent.globalAlpha)).getRGB());
/* 132 */       GL11.glDisable(3089);
/*     */     } 
/*     */     
/* 135 */     GL11.glDisable(3089); }
/*     */ 
/*     */   
/*     */   public void drawString(String text, float x, float y, int color) {
/* 139 */     Client.instance.FontManager.regular16.drawString(text, x + 2.0F, y - 2.0F, color);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void click(int mouseX, int mouseY, int button) {
/* 145 */     if (this.parent.drag) {
/*     */       return;
/*     */     }
/* 148 */     if (this.hover) {
/* 149 */       if (button == 0) {
/* 150 */         this.cheat.setEnabled(!this.cheat.isEnabled());
/*     */       }
/* 152 */       if (button == 1 && !this.buttons.isEmpty() && 
/* 153 */         this.parent.expand >= this.parent.height / 2.0F) {
/* 154 */         this.expand = !this.expand;
/*     */       }
/*     */     } 
/*     */     
/* 158 */     if (this.expand) {
/* 159 */       this.buttons.forEach(b -> b.click(mouseX, mouseY, button));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParent(Window parent) {
/* 166 */     this.parent = parent;
/* 167 */     for (int i = 0; i < this.parent.buttons.size(); ) {
/* 168 */       if (this.parent.buttons.get(i) != this) {
/*     */         i++; continue;
/*     */       } 
/* 171 */       this.index = i;
/* 172 */       this.remander = this.parent.buttons.size() - i;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\list\Button.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */