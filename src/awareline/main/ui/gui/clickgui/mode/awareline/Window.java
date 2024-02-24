/*     */ package awareline.main.ui.gui.clickgui.mode.awareline;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.ctype.ClickGui;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.animations.SmoothAnimationTimer;
/*     */ import awareline.main.ui.font.cfont.CFontRenderer;
/*     */ import awareline.main.ui.gui.clickgui.mode.astolfo.Limitation;
/*     */ import awareline.main.utility.render.RoundedUtil;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class Window
/*     */ {
/*     */   public final ModuleType category;
/*  20 */   public final ArrayList<Button> buttons = Lists.newArrayList();
/*     */   public final float max;
/*  22 */   public final SmoothAnimationTimer scrollAnimation = new SmoothAnimationTimer(0.0F);
/*  23 */   final CFontRenderer novoicons = Client.instance.FontLoaders.novoicons25;
/*     */   public boolean drag;
/*     */   public boolean extended;
/*     */   public float x;
/*     */   public float y;
/*     */   public float expand;
/*     */   public float dragX;
/*     */   public float dragY;
/*     */   public int scroll;
/*     */   public int scrollTo;
/*     */   public float totalY;
/*     */   float allX;
/*     */   int wheely;
/*     */   boolean buttonanim;
/*     */   
/*     */   public Window(ModuleType category, float x, float y) {
/*  39 */     this.category = category;
/*  40 */     this.x = x;
/*  41 */     this.y = y;
/*  42 */     this.max = 120.0F;
/*  43 */     float y2 = y + 22.0F;
/*  44 */     for (Module c : Client.instance.getModuleManager().getModules()) {
/*  45 */       if (c.getModuleType() != category)
/*     */         continue; 
/*  47 */       this.buttons.add(new Button(c, x + 5.0F, y2));
/*  48 */       y2 += 15.0F;
/*     */     } 
/*  50 */     for (Button b2 : this.buttons) {
/*  51 */       b2.setParent(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(int mouseX, int mouseY) {
/*  57 */     float current = 0.0F;
/*  58 */     float iY = this.y + 22.0F + 2.0F;
/*  59 */     this.totalY = 22.0F;
/*  60 */     for (Button b3 : this.buttons) {
/*  61 */       b3.y = iY - this.scrollAnimation.getValue();
/*  62 */       iY += 20.0F;
/*  63 */       this.totalY += 20.0F;
/*  64 */       if (b3.expand) {
/*  65 */         for (ValueButton ignored : b3.buttons) {
/*  66 */           current += 15.0F;
/*  67 */           this.totalY += 15.0F;
/*     */         } 
/*     */       }
/*  70 */       current += 15.0F;
/*     */     } 
/*     */     
/*  73 */     this.allX = 22.0F;
/*  74 */     float height = 15.0F + current;
/*  75 */     switch (this.category.name()) {
/*     */       case "Misc":
/*  77 */         if (height > 170.0F) {
/*  78 */           height = 170.0F;
/*     */         }
/*     */         break;
/*     */       case "Player":
/*  82 */         if (height > 230.0F) {
/*  83 */           height = 230.0F;
/*     */         }
/*     */         break;
/*     */       case "Movement":
/*  87 */         if (height > 276.0F) {
/*  88 */           height = 276.0F;
/*     */         }
/*     */         break;
/*     */       case "Combat":
/*  92 */         if (height > 216.0F) {
/*  93 */           height = 216.0F;
/*     */         }
/*     */         break;
/*     */       default:
/*  97 */         if (height > 316.0F) {
/*  98 */           height = 316.0F;
/*     */         }
/*     */         break;
/*     */     } 
/* 102 */     boolean isOnPanel = (mouseX > this.x - 2.0F && mouseX < this.x + 92.0F && mouseY > this.y - 2.0F && mouseY < this.y + this.expand);
/* 103 */     this.scrollAnimation.setTarget(this.wheely);
/* 104 */     this.scrollAnimation.update(true);
/* 105 */     if (isOnPanel) {
/* 106 */       runWheel(height);
/*     */     }
/* 108 */     if (this.extended) {
/* 109 */       this.expand = AnimationUtil.moveUDSmooth(this.expand, height);
/*     */     } else {
/* 111 */       this.expand = AnimationUtil.moveUDSmooth(this.expand, 16.0F);
/*     */     } 
/* 113 */     RoundedUtil.drawRound(this.x - 1.0F, this.y - 0.5F, 104.0F + this.allX / 2.0F - 1.5F, this.expand + 2.0F, 5.0F, new Color(30, 27, 30));
/*     */     
/* 115 */     switch (this.category.name()) {
/*     */       case "Misc":
/* 117 */         Client.instance.FontManager.regular19.drawString("Exploit", this.x + 4.0F, this.y + 2.0F, -1);
/*     */         break;
/*     */       case "World":
/* 120 */         Client.instance.FontManager.regular19.drawString("Misc", this.x + 4.0F, this.y + 2.0F, -1);
/*     */         break;
/*     */       case "Render":
/* 123 */         Client.instance.FontManager.regular19.drawString("Visual", this.x + 4.0F, this.y + 2.0F, -1);
/*     */         break;
/*     */       default:
/* 126 */         Client.instance.FontManager.regular19.drawString(this.category.name(), this.x + 4.0F, this.y + 2.0F, -1);
/*     */         break;
/*     */     } 
/*     */     
/* 130 */     switch (this.category.name()) {
/*     */       case "Combat":
/* 132 */         this.novoicons.drawString("D", this.x + 76.0F + this.allX, this.y + 7.0F, -1);
/*     */         break;
/*     */       case "Misc":
/* 135 */         this.novoicons.drawString("G", this.x + 76.0F + this.allX, this.y + 6.0F, -1);
/*     */         break;
/*     */       case "Render":
/* 138 */         Client.instance.FontLoaders.guiicons22.drawString("F", this.x + 76.0F + this.allX, this.y + 6.0F, -1);
/*     */         break;
/*     */       case "Player":
/* 141 */         this.novoicons.drawString("B", this.x + 76.0F + this.allX, this.y + 6.0F, -1);
/*     */         break;
/*     */       case "Movement":
/* 144 */         this.novoicons.drawString("A", this.x + 76.0F + this.allX, this.y + 6.0F, -1);
/*     */         break;
/*     */       case "World":
/* 147 */         Client.instance.FontLoaders.guiicons26.drawString("G", this.x + 77.0F + this.allX, this.y + 5.0F, -1);
/*     */         break;
/*     */       case "Globals":
/* 150 */         Client.instance.FontLoaders.guiicons24.drawString("J", this.x + 76.0F + this.allX, this.y + 5.0F, -1);
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
/* 168 */       ((Button)this.buttons.get(0)).y = this.y + 22.0F + 5.0F - this.scrollAnimation.getValue();
/* 169 */       for (Button b4 : this.buttons) {
/* 170 */         b4.x = this.x + 5.0F;
/*     */       }
/*     */     } 
/*     */   }
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


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\awareline\Window.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */