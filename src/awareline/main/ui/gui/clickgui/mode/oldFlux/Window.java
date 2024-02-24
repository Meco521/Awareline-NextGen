/*     */ package awareline.main.ui.gui.clickgui.mode.oldFlux;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.animations.TranslateUtil;
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
/*  20 */   public final ArrayList<Button> buttons = Lists.newArrayList();
/*     */   
/*     */   public boolean drag;
/*     */   
/*     */   public boolean extended;
/*     */   
/*     */   public int x;
/*     */   
/*     */   public int y;
/*     */   
/*     */   public float expand;
/*     */   
/*     */   public int dragX;
/*     */   
/*     */   public int dragY;
/*     */   
/*     */   public final int max;
/*     */   
/*     */   public int scroll;
/*     */   
/*     */   public int scrollTo;
/*     */   
/*     */   public double angel;
/*     */   
/*     */   protected final ClickUIOld parent;
/*     */   
/*     */   int allX;
/*     */   int smoothalpha;
/*     */   final TranslateUtil translate;
/*     */   int wheely;
/*     */   public float totalY;
/*     */   
/*     */   public Window(ModuleType category, int x2, int y2, ClickUIOld parent) {
/*  53 */     this.translate = new TranslateUtil(0.0F, 0.0F); this.category = category; this.x = x2; this.y = y2; this.max = 120; this.parent = parent; int y22 = y2 + 22; for (Module c2 : Client.instance.getModuleManager().getModules()) {
/*     */       if (c2.getModuleType() != category)
/*     */         continue;  this.buttons.add(new Button(c2, x2 + 5, y22)); y22 += 15;
/*     */     } 
/*     */     for (Button b2 : this.buttons)
/*  58 */       b2.setParent(this);  } public void render(int mouseX, int mouseY) { if (this.category == null) {
/*  59 */       this.y = 5;
/*     */     }
/*  61 */     this.allX = -15;
/*     */     
/*  63 */     int current = 0;
/*  64 */     int iY = this.y + 22;
/*  65 */     this.totalY = 17.0F;
/*  66 */     for (Button b3 : this.buttons) {
/*  67 */       b3.y = (int)(iY - this.translate.getY());
/*  68 */       iY += 15;
/*  69 */       this.totalY += 15.0F;
/*  70 */       if (b3.expand) {
/*  71 */         for (ValueButton ignored : b3.buttons) {
/*  72 */           current += 15;
/*  73 */           this.totalY += 15.0F;
/*     */         } 
/*     */       }
/*  76 */       current += 15;
/*     */     } 
/*  78 */     int height = 15 + current;
/*  79 */     if (this.category.name().equals("Misc") && 
/*  80 */       height > 107) {
/*  81 */       height = 107;
/*     */     }
/*     */     
/*  84 */     if (this.category.name().equals("Player") && 
/*  85 */       height > 137) {
/*  86 */       height = 137;
/*     */     }
/*     */     
/*  89 */     if (this.category.name().equals("Movement") && 
/*  90 */       height > 272) {
/*  91 */       height = 272;
/*     */     }
/*     */     
/*  94 */     if (this.category.name().equals("Combat") && 
/*  95 */       height > 212) {
/*  96 */       height = 212;
/*     */     }
/*     */     
/*  99 */     if (!this.category.name().equals("Player") && !this.category.name().equals("Misc") && 
/* 100 */       !this.category.name().equals("Combat") && !this.category.name().equals("Movement") && 
/* 101 */       height > 316) {
/* 102 */       height = 316;
/*     */     }
/*     */     
/* 105 */     smoothalphas();
/* 106 */     boolean isOnPanel = (mouseX > this.x - 2 && mouseX < this.x + 92 + this.allX && mouseY > this.y - 2 && mouseY < this.y + this.expand);
/* 107 */     this.translate.interpolate(0.0F, this.wheely, 0.15F);
/* 108 */     if (isOnPanel) {
/* 109 */       runWheel(height);
/*     */     }
/* 111 */     if (this.extended) {
/* 112 */       this.expand = AnimationUtil.moveUD(this.expand, height, SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/*     */     } else {
/* 114 */       this.expand = AnimationUtil.moveUD(this.expand, 5.0F, SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/*     */     } 
/* 116 */     RenderUtil.drawBorderedRect(this.x, this.y + 3.5D + 13.0D, (this.x + 90 + this.allX), (this.y + this.expand), 1.0F, (new Color(30, 30, 30, 180))
/* 117 */         .getRGB(), (new Color(15, 15, 15, 200))
/* 118 */         .getRGB());
/* 119 */     SimpleRender.drawRect((this.x - 2), (this.y + 17), (this.x + 92 + this.allX), this.y + 3.5D, (new Color(((Double)HUD.r
/* 120 */           .getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue(), this.smoothalpha)).getRGB());
/*     */     
/* 122 */     Client.instance.FontLoaders.Comfortaa16.drawStringWithShadow(this.category.name(), ((this.x + 35) - Client.instance.FontLoaders.Comfortaa16
/* 123 */         .getStringWidth(this.category.name()) / 2.0F), this.y + 8.5D, -1);
/*     */ 
/*     */     
/* 126 */     if (this.expand > 5.0F) {
/* 127 */       for (Button b2 : this.buttons) {
/* 128 */         b2.render(mouseX, mouseY, new Limitation(this.x, (this.y + 16), (this.x + 90 + this.allX), (int)(this.y + this.expand)));
/*     */       }
/*     */     }
/* 131 */     if (this.drag) {
/* 132 */       if (!Mouse.isButtonDown(0)) {
/* 133 */         this.drag = false;
/*     */       }
/* 135 */       this.x = mouseX - this.dragX;
/* 136 */       this.y = mouseY - this.dragY;
/* 137 */       ((Button)this.buttons.get(0)).y = (int)((this.y + 22) - this.translate.getY());
/* 138 */       for (Button b4 : this.buttons) {
/* 139 */         b4.x = this.x + 5;
/*     */       }
/*     */     }  }
/*     */ 
/*     */   
/*     */   protected void runWheel(int height) {
/* 145 */     if (Mouse.hasWheel()) {
/* 146 */       int wheel = Mouse.getDWheel();
/* 147 */       if (this.totalY - height <= 0.0F) {
/*     */         return;
/*     */       }
/* 150 */       if (wheel < 0) {
/* 151 */         if (this.wheely < this.totalY - height) {
/* 152 */           this.wheely += 20 + Mouse.getDWheel();
/* 153 */           if (this.wheely < 0) {
/* 154 */             this.wheely = 0;
/*     */           }
/*     */         } 
/* 157 */       } else if (wheel > 0) {
/* 158 */         this.wheely -= 20 + Mouse.getDWheel();
/* 159 */         if (this.wheely < 0) {
/* 160 */           this.wheely = 0;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int hudColor() {
/* 167 */     return (new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue())).getRGB();
/*     */   }
/*     */   
/*     */   public void mouseScroll(int mouseX, int mouseY, int amount) {
/* 171 */     if (mouseX > this.x - 2 && mouseX < this.x + 92 && mouseY > this.y - 2 && mouseY < (this.y + 17) + this.expand) {
/* 172 */       this.scrollTo = (int)(this.scrollTo - (amount / 120 * 28));
/*     */     }
/*     */   }
/*     */   
/*     */   private void smoothalphas() {
/* 177 */     if (this.drag) {
/* 178 */       this.smoothalpha = (int)AnimationUtil.moveUD(this.smoothalpha, 150.0F, 0.2F, 0.15F);
/*     */     } else {
/* 180 */       this.smoothalpha = (int)AnimationUtil.moveUD(this.smoothalpha, 255.0F, 0.2F, 0.15F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void click(int mouseX, int mouseY, int button) {
/* 185 */     if (mouseX > this.x - 2 && mouseX < this.x + 92 && mouseY > this.y + 4 && mouseY < this.y + 16) {
/* 186 */       if (button == 1) {
/* 187 */         this.extended = !this.extended;
/*     */       }
/* 189 */       if (button == 0) {
/* 190 */         this.drag = true;
/* 191 */         this.dragX = mouseX - this.x;
/* 192 */         this.dragY = mouseY - this.y;
/*     */       } 
/*     */     } 
/* 195 */     if (this.extended)
/* 196 */       this.buttons.stream().filter(b2 -> (b2.y < this.y + this.expand)).forEach(b2 -> b2.click(mouseX, mouseY, button)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\oldFlux\Window.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */