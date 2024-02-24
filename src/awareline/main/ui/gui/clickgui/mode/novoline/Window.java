/*     */ package awareline.main.ui.gui.clickgui.mode.novoline;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.ctype.ClickGui;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.animations.SmoothAnimationTimer;
/*     */ import awareline.main.ui.font.cfont.CFontRenderer;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.ui.gui.clickgui.mode.astolfo.Limitation;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class Window
/*     */ {
/*     */   public final ModuleType category;
/*  21 */   public final ArrayList<Button> buttons = Lists.newArrayList();
/*     */   public boolean drag;
/*     */   public boolean extended;
/*     */   public float x;
/*     */   public float y;
/*     */   public float expand;
/*     */   public float dragX;
/*     */   public float dragY;
/*     */   public final float max;
/*     */   public int scroll;
/*     */   public int scrollTo;
/*     */   float allX;
/*  33 */   public final SmoothAnimationTimer scrollAnimation = new SmoothAnimationTimer(0.0F);
/*     */ 
/*     */ 
/*     */   
/*     */   int wheely;
/*     */ 
/*     */ 
/*     */   
/*     */   public float totalY;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean buttonanim;
/*     */ 
/*     */ 
/*     */   
/*     */   final CFontRenderer novoicons;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Window(ModuleType category, float x, float y) {
/*  55 */     this.novoicons = Client.instance.FontLoaders.novoicons25; this.category = category; this.x = x; this.y = y; this.max = 120.0F; float y2 = y + 22.0F; for (Module c : Client.instance.getModuleManager().getModules()) {
/*     */       if (c.getModuleType() != category)
/*     */         continue;  this.buttons.add(new Button(c, x + 5.0F, y2)); y2 += 15.0F;
/*     */     }  for (Button b2 : this.buttons)
/*  59 */       b2.setParent(this);  } public void render(int mouseX, int mouseY) { float current = 0.0F;
/*  60 */     float iY = this.y + 22.0F;
/*  61 */     this.totalY = 17.0F;
/*  62 */     for (Button b3 : this.buttons) {
/*  63 */       b3.y = iY - this.scrollAnimation.getValue();
/*  64 */       iY += 15.0F;
/*  65 */       this.totalY += 15.0F;
/*  66 */       if (b3.expand) {
/*  67 */         for (ValueButton ignored : b3.buttons) {
/*  68 */           current += 15.0F;
/*  69 */           this.totalY += 15.0F;
/*     */         } 
/*     */       }
/*  72 */       current += 15.0F;
/*     */     } 
/*     */     
/*  75 */     this.allX = 12.0F;
/*  76 */     float height = 15.0F + current;
/*  77 */     switch (this.category.name()) {
/*     */       case "Misc":
/*  79 */         if (height > 107.0F) {
/*  80 */           height = 107.0F;
/*     */         }
/*     */         break;
/*     */       case "Player":
/*  84 */         if (height > 137.0F) {
/*  85 */           height = 137.0F;
/*     */         }
/*     */         break;
/*     */       case "Movement":
/*  89 */         if (height > 272.0F) {
/*  90 */           height = 272.0F;
/*     */         }
/*     */         break;
/*     */       case "Combat":
/*  94 */         if (height > 212.0F) {
/*  95 */           height = 212.0F;
/*     */         }
/*     */         break;
/*     */       default:
/*  99 */         if (height > 316.0F) {
/* 100 */           height = 316.0F;
/*     */         }
/*     */         break;
/*     */     } 
/* 104 */     boolean isOnPanel = (mouseX > this.x - 2.0F && mouseX < this.x + 92.0F && mouseY > this.y - 2.0F && mouseY < this.y + this.expand);
/* 105 */     this.scrollAnimation.setTarget(this.wheely);
/* 106 */     this.scrollAnimation.update(true);
/* 107 */     if (isOnPanel) {
/* 108 */       runWheel(height);
/*     */     }
/* 110 */     if (this.extended) {
/* 111 */       this.expand = AnimationUtil.moveUDSmooth(this.expand, height);
/*     */     } else {
/* 113 */       this.expand = AnimationUtil.moveUDSmooth(this.expand, 0.0F);
/*     */     } 
/* 115 */     SimpleRender.drawRectFloat(this.x - 1.0F, this.y, this.x + 91.0F + this.allX, this.y + 17.0F, (new Color(29, 29, 29)).getRGB());
/* 116 */     RenderUtil.rectangleBordered((this.x - 1.0F), this.y - 0.5D, (this.x + 91.0F + this.allX), (this.y + this.expand + 2.0F), 0.05000000074505806D, (new Color(29, 29, 29))
/* 117 */         .getRGB(), (new Color(40, 40, 40)).getRGB());
/* 118 */     switch (this.category.name()) {
/*     */       case "Misc":
/* 120 */         Client.instance.FontLoaders.SF19.drawStringWithShadow("Exploit", (this.x + 4.0F), (this.y + 5.0F), -1);
/*     */         break;
/*     */       case "World":
/* 123 */         Client.instance.FontLoaders.SF19.drawStringWithShadow("Misc", (this.x + 4.0F), (this.y + 5.0F), -1);
/*     */         break;
/*     */       case "Render":
/* 126 */         Client.instance.FontLoaders.SF19.drawStringWithShadow("Visuals", (this.x + 4.0F), (this.y + 5.0F), -1);
/*     */         break;
/*     */       default:
/* 129 */         Client.instance.FontLoaders.SF19.drawStringWithShadow(this.category.name(), (this.x + 4.0F), (this.y + 5.0F), -1);
/*     */         break;
/*     */     } 
/*     */     
/* 133 */     switch (this.category.name()) {
/*     */       case "Combat":
/* 135 */         this.novoicons.drawString("D", this.x + 78.0F + this.allX, this.y + 7.0F, -1);
/*     */         break;
/*     */       case "Misc":
/* 138 */         this.novoicons.drawString("G", this.x + 78.0F + this.allX, this.y + 5.0F, -1);
/*     */         break;
/*     */       case "Render":
/* 141 */         this.novoicons.drawString("C", this.x + 78.0F + this.allX, this.y + 6.0F, -1);
/*     */         break;
/*     */       case "Player":
/* 144 */         this.novoicons.drawString("B", this.x + 78.0F + this.allX, this.y + 6.0F, -1);
/*     */         break;
/*     */       case "Movement":
/* 147 */         this.novoicons.drawString("A", this.x + 78.0F + this.allX, this.y + 6.0F, -1);
/*     */         break;
/*     */       case "World":
/* 150 */         this.novoicons.drawString("F", this.x + 78.0F + this.allX, this.y + 5.0F, -1);
/*     */         break;
/*     */     } 
/* 153 */     if (this.expand >= 17.0F) {
/* 154 */       float finalHeight = height;
/* 155 */       this.buttons.forEach(b2 -> {
/*     */             if (this.totalY < this.y || b2.y > this.y + finalHeight) {
/*     */               return;
/*     */             }
/*     */             b2.render(mouseX, mouseY, new Limitation(this.x, this.y + 17.0F, this.x + 90.0F + this.allX, this.y + this.expand + 1.0F));
/*     */           });
/*     */     } 
/* 162 */     if (this.drag) {
/* 163 */       if (!Mouse.isButtonDown(0)) {
/* 164 */         this.drag = false;
/*     */       }
/* 166 */       this.x = mouseX - this.dragX;
/* 167 */       this.y = mouseY - this.dragY;
/* 168 */       ((Button)this.buttons.get(0)).y = this.y + 22.0F - this.scrollAnimation.getValue();
/* 169 */       for (Button b4 : this.buttons) {
/* 170 */         b4.x = this.x + 5.0F;
/*     */       }
/*     */     }  }
/*     */ 
/*     */   
/*     */   protected void runWheel(float height) {
/* 176 */     if (Mouse.hasWheel()) {
/* 177 */       int wheel = Mouse.getDWheel();
/* 178 */       if (this.totalY - height <= 0.0F) {
/*     */         return;
/*     */       }
/* 181 */       if (wheel < 0) {
/* 182 */         if (this.wheely < this.totalY - height) {
/* 183 */           this.wheely += (((Boolean)ClickGui.fpsMouseWheel.get()).booleanValue() ? 60 : 20) + Mouse.getDWheel();
/* 184 */           if (this.wheely < 0) {
/* 185 */             this.wheely = 0;
/*     */           }
/*     */         } 
/* 188 */       } else if (wheel > 0) {
/* 189 */         this.wheely -= (((Boolean)ClickGui.fpsMouseWheel.get()).booleanValue() ? 60 : 20) + Mouse.getDWheel();
/* 190 */         if (this.wheely < 0) {
/* 191 */           this.wheely = 0;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void key(char typedChar, int keyCode) {
/* 198 */     this.buttons.forEach(b2 -> b2.key(typedChar, keyCode));
/*     */   }
/*     */   
/*     */   public void mouseScroll(int mouseX, int mouseY, int amount) {
/* 202 */     if (mouseX > this.x - 2.0F && mouseX < this.x + 92.0F && mouseY > this.y - 2.0F && mouseY < this.y + 17.0F + this.expand) {
/* 203 */       this.scrollTo = (int)(this.scrollTo - (amount / 120 * 28));
/*     */     }
/*     */   }
/*     */   
/*     */   public void click(int mouseX, int mouseY, int button) {
/* 208 */     if (mouseX > this.x - 2.0F && mouseX < this.x + 92.0F && mouseY > this.y - 2.0F && mouseY < this.y + 17.0F) {
/* 209 */       if (button == 1) {
/* 210 */         this.extended = !this.extended;
/*     */       }
/* 212 */       if (button == 0) {
/* 213 */         this.drag = true;
/* 214 */         this.dragX = mouseX - this.x;
/* 215 */         this.dragY = mouseY - this.y;
/*     */       } 
/*     */     } 
/* 218 */     if (this.extended)
/* 219 */       this.buttons.stream().filter(b2 -> (b2.y < this.y + this.expand)).forEach(b2 -> b2.click(mouseX, mouseY, button)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\novoline\Window.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */