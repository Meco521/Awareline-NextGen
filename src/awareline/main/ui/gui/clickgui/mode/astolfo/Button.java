/*     */ package awareline.main.ui.gui.clickgui.mode.astolfo;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.font.cfont.CFontRenderer;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Button
/*     */ {
/*     */   public final Module cheat;
/*     */   public Window parent;
/*     */   public float x;
/*     */   public float y;
/*     */   public int arrow;
/*     */   public int index;
/*     */   public int remander;
/*     */   public double opacity;
/*  23 */   public final ArrayList<ValueButton> buttons = Lists.newArrayList();
/*     */   public boolean expand;
/*     */   int staticColor;
/*     */   
/*     */   public Button(Module cheat, int x, int y) {
/*  28 */     this.cheat = cheat;
/*  29 */     this.x = x;
/*  30 */     this.y = y;
/*  31 */     int y2 = y + 15;
/*  32 */     for (Value<?> v : (Iterable<Value<?>>)cheat.getValues()) {
/*  33 */       if (!v.isVisitable())
/*  34 */         continue;  this.buttons.add(new ValueButton(v, (x + 5), y2));
/*  35 */       y2 += 15;
/*     */     } 
/*  37 */     this.buttons.add(new KeyBindButton(cheat, x + 5, y2 - 5));
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(int mouseX, int mouseY, Limitation limitation) {
/*  42 */     CFontRenderer font = Client.instance.FontLoaders.Comfortaa17;
/*  43 */     CFontRenderer mfont = Client.instance.FontLoaders.bold15;
/*  44 */     float y2 = this.y + 15.0F;
/*  45 */     this.buttons.clear();
/*  46 */     for (Value<?> v : (Iterable<Value<?>>)this.cheat.getValues()) {
/*  47 */       if (!v.isVisitable())
/*  48 */         continue;  this.buttons.add(new ValueButton(v, this.x + 5.0F, y2));
/*  49 */       y2 += 15.0F;
/*     */     } 
/*  51 */     if (this.index != 0) {
/*  52 */       Button b2 = this.parent.buttons.get(this.index - 1);
/*  53 */       this.y = b2.y + 15.0F + (b2.expand ? (15 * b2.buttons.size()) : false);
/*     */     } 
/*  55 */     int i = 0;
/*  56 */     while (i < this.buttons.size()) {
/*  57 */       ((ValueButton)this.buttons.get(i)).y = this.y + 14.0F + (15 * i);
/*  58 */       ((ValueButton)this.buttons.get(i)).x = this.x + 7.0F;
/*  59 */       i++;
/*     */     } 
/*  61 */     switch (this.parent.category.name()) {
/*     */       case "Combat":
/*  63 */         this.staticColor = (new Color(231, 76, 60)).getRGB();
/*     */         break;
/*     */       
/*     */       case "Render":
/*  67 */         this.staticColor = (new Color(54, 1, 205)).getRGB();
/*     */         break;
/*     */       
/*     */       case "Movement":
/*  71 */         this.staticColor = (new Color(45, 203, 113)).getRGB();
/*     */         break;
/*     */       
/*     */       case "Player":
/*  75 */         this.staticColor = (new Color(141, 68, 173)).getRGB();
/*     */         break;
/*     */       
/*     */       case "World":
/*  79 */         this.staticColor = (new Color(38, 154, 255)).getRGB();
/*     */         break;
/*     */       
/*     */       case "Misc":
/*  83 */         this.staticColor = (new Color(102, 101, 101)).getRGB();
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/*  88 */     GL11.glPushMatrix();
/*  89 */     GL11.glEnable(3089);
/*  90 */     Limitation.doGlScissor(this.x - 5.0F, (int)(this.y - 5.0F), 90.0F + this.parent.allX, (font.getStringHeight() + 5));
/*  91 */     limitation.cut();
/*  92 */     Gui.drawRect((this.x - 5.0F), (this.y - 5.0F), (this.x + 85.0F + this.parent.allX), (this.y + 5.0F + font.getStringHeight()), (new Color(39, 39, 39)).getRGB());
/*  93 */     if (this.cheat.isEnabled()) {
/*  94 */       limitation.cut();
/*  95 */       Gui.drawRect((this.x - 4.0F), (this.y - 5.0F), (this.x + 84.0F + this.parent.allX), (this.y + 10.0F), this.staticColor);
/*     */     } 
/*  97 */     limitation.cut();
/*  98 */     mfont.drawString(this.cheat.getName().toLowerCase(), this.x + 81.0F + this.parent.allX - mfont.getStringWidth(this.cheat.getName().toLowerCase()), this.y, (new Color(220, 220, 220)).getRGB());
/*  99 */     if (mouseX > this.x - 7.0F && mouseX < this.x + 85.0F + this.parent.allX && mouseY > this.y - 6.0F && mouseY < this.y + mfont.getStringHeight()) {
/* 100 */       Gui.drawRect((this.x - 4.0F), (this.y - 5.0F), (this.x + 84.0F + this.parent.allX), (this.y + 10.0F), (new Color(233, 233, 233, 30)).getRGB());
/*     */     }
/* 102 */     GL11.glDisable(3089);
/* 103 */     GL11.glPopMatrix();
/* 104 */     if (this.expand) {
/* 105 */       this.buttons.forEach(component -> component.render(mouseX, mouseY, limitation, this.parent));
/*     */     }
/*     */   }
/*     */   
/*     */   public void key(char typedChar, int keyCode) {
/* 110 */     this.buttons.forEach(b -> b.key(typedChar, keyCode));
/*     */   }
/*     */   
/*     */   public void click(int mouseX, int mouseY, int button) {
/* 114 */     if (this.parent.drag) {
/*     */       return;
/*     */     }
/* 117 */     if (mouseX > this.x - 7.0F && mouseX < this.x + 85.0F + this.parent.allX && mouseY > this.y - 6.0F && mouseY < this.y + Client.instance.FontLoaders.bold16.getStringHeight()) {
/* 118 */       if (button == 0) {
/* 119 */         this.cheat.setEnabled(!this.cheat.isEnabled());
/*     */       }
/* 121 */       if (button == 1 && !this.buttons.isEmpty()) {
/* 122 */         this.expand = !this.expand;
/*     */       }
/*     */     } 
/* 125 */     if (this.expand) {
/* 126 */       this.buttons.forEach(b -> b.click(mouseX, mouseY, button, this.parent));
/*     */     }
/*     */   }
/*     */   
/*     */   public void setParent(Window parent) {
/* 131 */     this.parent = parent;
/* 132 */     int i2 = 0;
/* 133 */     while (i2 < this.parent.buttons.size()) {
/* 134 */       if (this.parent.buttons.get(i2) == this) {
/* 135 */         this.index = i2;
/* 136 */         this.remander = this.parent.buttons.size() - i2;
/*     */         break;
/*     */       } 
/* 139 */       i2++;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\astolfo\Button.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */