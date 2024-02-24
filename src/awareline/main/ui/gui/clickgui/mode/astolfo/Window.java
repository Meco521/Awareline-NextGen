/*     */ package awareline.main.ui.gui.clickgui.mode.astolfo;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.ctype.ClickGui;
/*     */ import awareline.main.ui.animations.SmoothAnimationTimer;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class Window
/*     */ {
/*     */   public final ModuleType category;
/*  17 */   public final ArrayList<Button> buttons = Lists.newArrayList();
/*     */   public boolean drag;
/*     */   public boolean extended;
/*     */   public float x;
/*     */   public float y;
/*     */   public int expand;
/*     */   public float dragX;
/*     */   public float dragY;
/*     */   public int scroll;
/*     */   public int scrollTo;
/*     */   public double angel;
/*     */   int staticColor;
/*     */   public int totalY;
/*     */   int offset;
/*  31 */   public final SmoothAnimationTimer scrollAnimation = new SmoothAnimationTimer(0.0F);
/*     */   public float allX;
/*     */   
/*     */   public Window(ModuleType category, int x, int y) {
/*  35 */     this.category = category;
/*  36 */     this.x = x;
/*  37 */     this.y = y;
/*  38 */     int y2 = y + 25;
/*  39 */     for (Module c : Client.instance.getModuleManager().getModules()) {
/*  40 */       if (c.getModuleType() != category)
/*  41 */         continue;  this.buttons.add(new Button(c, x + 5, y2));
/*  42 */       y2 += 15;
/*     */     } 
/*  44 */     for (Button b2 : this.buttons) {
/*  45 */       b2.setParent(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(int mouseX, int mouseY) {
/*  51 */     int current = 0;
/*  52 */     float iY = this.y + 22.0F;
/*  53 */     this.totalY = 16;
/*  54 */     for (Button b3 : this.buttons) {
/*  55 */       b3.y = iY - this.scrollAnimation.getValue();
/*  56 */       iY += 15.0F;
/*  57 */       this.totalY += 15;
/*  58 */       if (b3.expand) {
/*  59 */         for (ValueButton ignored : b3.buttons) {
/*  60 */           current += 15;
/*  61 */           this.totalY += 15;
/*     */         } 
/*     */       }
/*  64 */       current += 15;
/*     */     } 
/*  66 */     int height = 16 + current;
/*  67 */     if (height > 316) {
/*  68 */       height = 316;
/*     */     }
/*  70 */     this.allX = 10.0F;
/*  71 */     this.expand = this.extended ? height : 0;
/*  72 */     this.angel = this.extended ? 180.0D : 0.0D;
/*  73 */     boolean isOnPanel = (mouseX > this.x - 2.0F && mouseX < this.x + 92.0F && mouseY > this.y - 2.0F && mouseY < this.y + this.expand);
/*  74 */     this.scrollAnimation.setTarget(this.offset);
/*  75 */     this.scrollAnimation.update(true);
/*  76 */     if (isOnPanel) {
/*  77 */       runWheel(height);
/*     */     }
/*  79 */     switch (this.category.name()) {
/*     */       case "Combat":
/*  81 */         this.staticColor = (new Color(231, 76, 60)).getRGB();
/*     */         break;
/*     */       
/*     */       case "Render":
/*  85 */         this.staticColor = (new Color(54, 1, 205)).getRGB();
/*     */         break;
/*     */       
/*     */       case "Movement":
/*  89 */         this.staticColor = (new Color(45, 203, 113)).getRGB();
/*     */         break;
/*     */       
/*     */       case "Player":
/*  93 */         this.staticColor = (new Color(141, 68, 173)).getRGB();
/*     */         break;
/*     */       
/*     */       case "World":
/*  97 */         this.staticColor = (new Color(38, 154, 255)).getRGB();
/*     */         break;
/*     */       
/*     */       case "Misc":
/* 101 */         this.staticColor = (new Color(102, 101, 101)).getRGB();
/*     */         break;
/*     */     } 
/*     */     
/* 105 */     RenderUtil.drawRect(this.x, this.y, this.x + 90.0F + this.allX, this.y + 17.0F, (new Color(25, 25, 25)).getRGB());
/* 106 */     if (this.expand > 0) {
/* 107 */       RenderUtil.rectangleBordered(this.x - 0.5D, this.y - 0.5D, this.x + 90.5D + this.allX, this.y + 5.5D + this.expand, 1.0D, this.staticColor, this.staticColor);
/* 108 */       RenderUtil.rectangleBordered(this.x, this.y, (this.x + 90.0F + this.allX), this.y + 5.0D + this.expand, 1.0D, (new Color(25, 25, 25)).getRGB(), (new Color(25, 25, 25)).getRGB());
/* 109 */       for (Button b2 : this.buttons) {
/* 110 */         b2.render(mouseX, mouseY, new Limitation(this.x, this.y + 16.0F, this.x + 90.0F + this.allX, this.y + this.expand));
/*     */       }
/*     */     } 
/* 113 */     Client.instance.FontLoaders.bold16.drawString(this.category.name().toLowerCase(), this.x + 4.0F, this.y + 6.0F, (new Color(233, 233, 233, 233)).getRGB());
/*     */     
/* 115 */     if (this.drag) {
/* 116 */       if (!Mouse.isButtonDown(0)) {
/* 117 */         this.drag = false;
/*     */       }
/* 119 */       this.x = mouseX - this.dragX;
/* 120 */       this.y = mouseY - this.dragY;
/* 121 */       ((Button)this.buttons.get(0)).y = this.y + 22.0F - this.scrollAnimation.getValue();
/* 122 */       for (Button b4 : this.buttons) {
/* 123 */         b4.x = this.x + 5.0F;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void runWheel(int height) {
/* 129 */     if (Mouse.hasWheel()) {
/* 130 */       int wheel = Mouse.getDWheel();
/* 131 */       if (this.totalY - height <= 0) {
/*     */         return;
/*     */       }
/* 134 */       if (wheel < 0) {
/* 135 */         if (this.offset < this.totalY - height) {
/* 136 */           this.offset += (((Boolean)ClickGui.fpsMouseWheel.get()).booleanValue() ? 60 : 30) + Mouse.getDWheel();
/* 137 */           if (this.offset < 0) {
/* 138 */             this.offset = 0;
/*     */           }
/*     */         } 
/* 141 */       } else if (wheel > 0) {
/* 142 */         this.offset -= (((Boolean)ClickGui.fpsMouseWheel.get()).booleanValue() ? 60 : 30) + Mouse.getDWheel();
/* 143 */         if (this.offset < 0) {
/* 144 */           this.offset = 0;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void key(char typedChar, int keyCode) {
/* 151 */     this.buttons.forEach(b2 -> b2.key(typedChar, keyCode));
/*     */   }
/*     */   
/*     */   public void mouseScroll(int mouseX, int mouseY, int amount) {
/* 155 */     if (mouseX > this.x - 2.0F && mouseX < this.x + 92.0F && mouseY > this.y - 2.0F && mouseY < this.y + 17.0F + this.expand) {
/* 156 */       this.scrollTo = (int)(this.scrollTo - (amount / 120 * 28));
/*     */     }
/*     */   }
/*     */   
/*     */   public void click(int mouseX, int mouseY, int button) {
/* 161 */     if (mouseX > this.x - 2.0F && mouseX < this.x + 92.0F && mouseY > this.y - 2.0F && mouseY < this.y + 17.0F) {
/* 162 */       if (button == 1) {
/* 163 */         this.extended = !this.extended;
/*     */       }
/* 165 */       if (button == 0) {
/* 166 */         this.drag = true;
/* 167 */         this.dragX = mouseX - this.x;
/* 168 */         this.dragY = mouseY - this.y;
/*     */       } 
/*     */     } 
/* 171 */     if (this.extended)
/* 172 */       this.buttons.stream().filter(b2 -> (b2.y < this.y + this.expand)).forEach(b2 -> b2.click(mouseX, mouseY, button)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\astolfo\Window.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */