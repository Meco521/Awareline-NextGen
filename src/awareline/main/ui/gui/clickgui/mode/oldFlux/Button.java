/*     */ package awareline.main.ui.gui.clickgui.mode.oldFlux;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.font.fontmanager.UnicodeFontRenderer;
/*     */ import awareline.main.ui.gui.clickgui.mode.astolfo.Limitation;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Button
/*     */ {
/*     */   public final Module cheat;
/*     */   public Window parent;
/*     */   public int x;
/*     */   public float y;
/*     */   public int index;
/*     */   public double opacity;
/*  23 */   public final ArrayList<ValueButton> buttons = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public boolean expand;
/*     */ 
/*     */   
/*     */   float animationsize;
/*     */ 
/*     */   
/*     */   public int arrow;
/*     */ 
/*     */   
/*     */   boolean hover;
/*     */   
/*     */   final UnicodeFontRenderer font;
/*     */ 
/*     */   
/*     */   public Button(Module cheat, int x2, int y2) {
/*  41 */     this.font = Client.instance.FontManager.arial17; this.cheat = cheat; this.x = x2; this.y = y2; int y22 = y2 + 14; for (Value v2 : this.cheat.getValues()) {
/*     */       if (!v2.isVisitable())
/*     */         continue;  this.buttons.add(new ValueButton(v2, this.x + 5, y22, this)); y22 += 15;
/*  44 */     }  } public void render(int mouseX, int mouseY, Limitation limitation) { float y2 = this.y + 14.0F;
/*     */     
/*  46 */     this.buttons.clear();
/*  47 */     for (Value v2 : this.cheat.getValues()) {
/*  48 */       if (!v2.isVisitable())
/*  49 */         continue;  this.buttons.add(new ValueButton(v2, this.x + 5, y2, this));
/*  50 */       y2 += 15.0F;
/*     */     } 
/*     */     
/*  53 */     if (this.index != 0) {
/*  54 */       Button b2 = this.parent.buttons.get(this.index - 1);
/*  55 */       this.y = b2.y + 15.0F + this.animationsize;
/*  56 */       if (b2.expand) {
/*     */         
/*  58 */         this.animationsize = AnimationUtil.moveUD(this.animationsize, (15 * b2.buttons.size()), SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/*     */       } else {
/*     */         
/*  61 */         this.animationsize = AnimationUtil.moveUD(this.animationsize, 0.0F, SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/*     */       } 
/*     */     } 
/*  64 */     int i = 0;
/*  65 */     int size = this.buttons.size();
/*  66 */     while (i < size) {
/*  67 */       ((ValueButton)this.buttons.get(i)).y = this.y + 14.0F + (15 * i);
/*  68 */       ((ValueButton)this.buttons.get(i)).x = this.x + 5;
/*  69 */       i++;
/*     */     } 
/*  71 */     GL11.glPushMatrix();
/*  72 */     GL11.glEnable(3089);
/*     */     
/*  74 */     if (this.parent.category.name().equals("Movement")) {
/*  75 */       SimpleRender.drawRect((this.x - 5 + 73), (this.parent.y - 5 + 556 - 
/*  76 */           (int)(this.parent.totalY + 22.0F - this.parent.translate.getY())), (this.x + 85 - 18), (
/*     */           
/*  78 */           (float)((this.parent.y + this.font.getStringHeight()) + 3.8D) - 
/*  79 */           (int)(this.parent.totalY + 22.0F - this.parent.translate.getY()) + 398.0F), (new Color(233, 233, 233, 150))
/*     */           
/*  81 */           .getRGB());
/*     */     }
/*  83 */     if (this.parent.category.name().equals("Combat")) {
/*  84 */       SimpleRender.drawRect((this.x - 5 + 73), (this.parent.y - 5 + 408 - 
/*  85 */           (int)(this.parent.totalY + 22.0F - this.parent.translate.getY())), (this.x + 85 - 18), (
/*     */           
/*  87 */           (float)((this.parent.y + this.font.getStringHeight()) + 3.8D) - 
/*  88 */           (int)(this.parent.totalY + 22.0F - this.parent.translate.getY() + 44.0F) + 540.0F), (new Color(233, 233, 233, 150))
/*     */           
/*  90 */           .getRGB());
/*     */     }
/*  92 */     if (this.parent.category.name().equals("Player")) {
/*  93 */       SimpleRender.drawRect((this.x - 5 + 73), (this.parent.y - 5 + 236 - 
/*  94 */           (int)(this.parent.totalY + 22.0F - this.parent.translate.getY())), (this.x + 85 - 18), (
/*     */           
/*  96 */           (float)((this.parent.y + this.font.getStringHeight()) + 3.8D) - 
/*  97 */           (int)(this.parent.totalY + 22.0F - this.parent.translate.getY() + 44.0F) + 470.0F), (new Color(233, 233, 233, 150))
/*     */           
/*  99 */           .getRGB());
/*     */     }
/* 101 */     if (this.parent.category.name().equals("Misc")) {
/* 102 */       SimpleRender.drawRect((this.x - 5 + 73), (this.parent.y - 5 + 412 - 
/*     */           
/* 104 */           (int)(this.parent.totalY + 22.0F - this.parent.translate.getY() + 106.0F)), (this.x + 85 - 18), (
/*     */           
/* 106 */           (float)((this.parent.y + this.font.getStringHeight()) + 3.8D) - 
/* 107 */           (int)(this.parent.totalY + 22.0F - this.parent.translate.getY() + 64.0F) + 236.0F), (new Color(233, 233, 233, 150))
/*     */ 
/*     */           
/* 110 */           .getRGB());
/*     */     }
/* 112 */     if (this.parent.category.name().equals("Render")) {
/* 113 */       SimpleRender.drawRect((this.x - 5 + 73), (this.parent.y - 5 + 1088 - 
/* 114 */           (int)(this.parent.totalY + 22.0F - this.parent.translate.getY() + 191.0F)), (this.x + 85 - 18), (
/*     */ 
/*     */           
/* 117 */           (float)((this.parent.y + this.font.getStringHeight()) + 3.8D) - 
/* 118 */           (int)(this.parent.totalY + 22.0F - this.parent.translate.getY() - 312.0F) + 294.0F), (new Color(233, 233, 233, 150))
/*     */           
/* 120 */           .getRGB());
/*     */     }
/* 122 */     limitation.cut();
/* 123 */     this.hover = (mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6.0F && mouseY < this.y + this.font.getStringHeight() + 4.0F);
/*     */     
/* 125 */     if (this.cheat.isEnabled()) {
/*     */       
/* 127 */       this.font.drawString(this.cheat.getName(), (this.x + 10), this.y - 1.0F, this.parent.hudColor());
/*     */     } else {
/*     */       
/* 130 */       this.font.drawString(this.cheat.getName(), (this.x + 10), this.y - 1.0F, -1);
/*     */     } 
/* 132 */     limitation.cut();
/* 133 */     if (!this.expand && size >= 1) {
/* 134 */       Client.instance.FontLoaders.Comfortaa16.drawString(">", this.x, this.y + 2.0F, -1);
/*     */ 
/*     */     
/*     */     }
/* 138 */     else if (size >= 1) {
/* 139 */       Client.instance.FontLoaders.Comfortaa16.drawString("v", this.x, this.y + 2.0F, -1);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 144 */     if (this.expand) {
/* 145 */       this.buttons.forEach(b2 -> b2.render(mouseX, mouseY, this.parent));
/*     */     }
/* 147 */     GL11.glDisable(3089);
/* 148 */     GL11.glPopMatrix(); }
/*     */ 
/*     */   
/*     */   public void click(int mouseX, int mouseY, int button) {
/* 152 */     if (this.parent.drag) {
/*     */       return;
/*     */     }
/* 155 */     if (mouseX > this.x - 7 && mouseX < this.x + 85 + this.parent.allX && mouseY > this.y - 6.0F && mouseY < this.y + Client.instance.FontLoaders.SF17.getStringHeight()) {
/*     */       
/* 157 */       if (button == 0) {
/* 158 */         this.cheat.setEnabledWithoutNotification(!this.cheat.isEnabled());
/*     */       }
/* 160 */       if (button == 1 && !this.buttons.isEmpty()) {
/* 161 */         this.expand = !this.expand;
/*     */       }
/*     */     } 
/* 164 */     if (this.expand) {
/* 165 */       this.buttons.forEach(b2 -> b2.click(mouseX, mouseY));
/*     */     }
/*     */   }
/*     */   
/*     */   public void setParent(Window parent) {
/* 170 */     this.parent = parent;
/* 171 */     int i2 = 0;
/* 172 */     while (i2 < this.parent.buttons.size()) {
/* 173 */       if (this.parent.buttons.get(i2) == this) {
/* 174 */         this.index = i2;
/*     */         break;
/*     */       } 
/* 177 */       i2++;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\oldFlux\Button.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */