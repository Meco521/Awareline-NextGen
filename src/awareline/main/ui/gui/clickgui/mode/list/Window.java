/*     */ package awareline.main.ui.gui.clickgui.mode.list;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.mod.implement.visual.ctype.ClickGui;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.animations.SmoothAnimationTimer;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.ui.gui.clickgui.mode.astolfo.Limitation;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import awareline.main.utility.render.GuiRenderUtils;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Window
/*     */ {
/*     */   public final ModuleType category;
/*  24 */   public final ArrayList<Button> buttons = Lists.newArrayList();
/*  25 */   public final SmoothAnimationTimer scrollAnimation = new SmoothAnimationTimer(0.0F);
/*     */   public boolean drag;
/*     */   public boolean extended;
/*     */   public float x;
/*     */   public float y;
/*     */   public float expand;
/*     */   public float dragX;
/*     */   public float dragY;
/*     */   public float scrollTo;
/*     */   public float totalY;
/*     */   public int globalAlpha;
/*     */   public float allX;
/*     */   float height;
/*     */   private int wheely;
/*     */   private int smoothAlpha;
/*     */   
/*     */   public Window(ModuleType category, float x, float y) {
/*  42 */     this.category = category;
/*  43 */     this.x = x;
/*  44 */     this.y = y;
/*  45 */     float y2 = y + 25.0F;
/*  46 */     for (Module c : Client.instance.getModuleManager().getModules()) {
/*  47 */       if (c.getType() != category) {
/*     */         continue;
/*     */       }
/*  50 */       this.buttons.add(new Button(c, x + 5.0F, y2, 0));
/*  51 */       y2 += 15.0F;
/*     */     } 
/*  53 */     for (Button b2 : this.buttons) {
/*  54 */       b2.setParent(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void render(int mouseX, int mouseY, int globalAlpha) {
/*  59 */     this.globalAlpha = globalAlpha;
/*  60 */     this.allX = 6.0F;
/*  61 */     float current = 0.0F;
/*  62 */     float iY = this.y + 22.0F;
/*  63 */     this.totalY = 17.0F;
/*  64 */     for (Button b3 : this.buttons) {
/*  65 */       b3.y = iY - this.scrollAnimation.getValue();
/*  66 */       iY += 15.0F;
/*  67 */       this.totalY += 17.0F;
/*  68 */       if (b3.expand) {
/*  69 */         for (ValueButton ignored : b3.buttons) {
/*  70 */           current += 15.0F;
/*  71 */           this.totalY += 17.0F;
/*     */         } 
/*     */       }
/*  74 */       current += 15.0F;
/*     */     } 
/*  76 */     this.height = 15.0F + current;
/*  77 */     switch (this.category.name()) {
/*     */       case "Misc":
/*  79 */         if (this.height > 270.0F) {
/*  80 */           this.height = 270.0F;
/*     */         }
/*     */         break;
/*     */       case "Player":
/*  84 */         if (this.height > 137.0F) {
/*  85 */           this.height = 260.0F;
/*     */         }
/*     */         break;
/*     */       case "Movement":
/*  89 */         if (this.height > 272.0F) {
/*  90 */           this.height = 272.0F;
/*     */         }
/*     */         break;
/*     */       case "World":
/*     */       case "Combat":
/*  95 */         if (this.height > 212.0F) {
/*  96 */           this.height = 240.0F;
/*     */         }
/*     */         break;
/*     */       default:
/* 100 */         if (this.height > 333.0F) {
/* 101 */           this.height = 333.0F;
/*     */         }
/*     */         break;
/*     */     } 
/* 105 */     boolean isOnPanel = (mouseX > this.x - 2.0F && mouseX < this.x + 92.0F && mouseY > this.y - 2.0F && mouseY < this.y + this.expand);
/* 106 */     this.scrollAnimation.setTarget(this.wheely);
/* 107 */     this.scrollAnimation.update(true);
/* 108 */     if (isOnPanel) {
/* 109 */       runWheel(this.height);
/*     */     }
/* 111 */     if (this.extended) {
/* 112 */       this.expand = AnimationUtil.moveUDSmooth(this.expand, this.height);
/*     */     } else {
/* 114 */       this.expand = AnimationUtil.moveUDSmooth(this.expand, 0.0F);
/*     */     } 
/* 116 */     if (globalAlpha >= 20) {
/* 117 */       GuiRenderUtils.drawRect(this.x, this.y + (this.extended ? -1 : false), 95.0F + this.allX / 2.0F - 2.0F, this.expand, (new Color(40, 40, 40, globalAlpha)).getRGB());
/* 118 */       GuiRenderUtils.drawRect(this.x, this.y - 1.0F, 95.0F + this.allX / 2.0F - 2.0F, 18.0F, (new Color(((Double)HUD.r
/* 119 */             .getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue(), globalAlpha)).getRGB());
/*     */       
/* 121 */       SimpleRender.drawRectFloat(this.x, this.y - 1.0F, this.x + 15.0F, this.y + 17.0F, (new Color(0, 0, 0, 75)).getRGB());
/*     */ 
/*     */       
/* 124 */       if (this.drag) {
/* 125 */         this.smoothAlpha = (int)AnimationUtil.moveUDSmooth(this.smoothAlpha, (globalAlpha >= 100) ? 100.0F : globalAlpha);
/*     */       } else {
/* 127 */         this.smoothAlpha = (int)AnimationUtil.moveUDSmooth(this.smoothAlpha, (globalAlpha / 255));
/*     */       } 
/* 129 */       SimpleRender.drawRectFloat(this.x, this.y - 1.0F, this.x + 90.0F + this.allX, this.y + 18.0F, (new Color(0, 0, 0, this.smoothAlpha)).getRGB());
/*     */ 
/*     */       
/* 132 */       Client.instance.FontLoaders.regular19.drawString(this.category.name(), this.x + 20.0F, this.y + 4.0F, (new Color(255, 255, 255, globalAlpha)).getRGB());
/* 133 */       switch (this.category.name()) {
/*     */         case "Combat":
/* 135 */           Client.instance.FontLoaders.icon18.drawString("1", this.x + 3.0F, this.y + 7.0F, (new Color(255, 255, 255, globalAlpha)).getRGB());
/*     */           break;
/*     */         case "Misc":
/* 138 */           Client.instance.FontLoaders.guiicons24.drawString("G", this.x + 3.3F, this.y + 5.5F, (new Color(255, 255, 255, globalAlpha)).getRGB());
/*     */           break;
/*     */         case "Globals":
/* 141 */           Client.instance.FontLoaders.guiicons24.drawString("J", this.x + 3.0F, this.y + 6.0F, (new Color(255, 255, 255, globalAlpha)).getRGB());
/*     */           break;
/*     */         case "Script":
/* 144 */           Client.instance.FontLoaders.novoicons24.drawString("G", this.x + 2.0F, this.y + 6.5F, (new Color(255, 255, 255, globalAlpha)).getRGB());
/*     */           break;
/*     */         case "Render":
/* 147 */           Client.instance.FontLoaders.guiicons18.drawString("F", this.x + 3.0F, this.y + 7.0F, (new Color(255, 255, 255, globalAlpha)).getRGB());
/*     */           break;
/*     */         case "Player":
/* 150 */           Client.instance.FontLoaders.guiicons22.drawString("C", this.x + 3.7F, this.y + 6.0F, (new Color(255, 255, 255, globalAlpha)).getRGB());
/*     */           break;
/*     */         case "Movement":
/* 153 */           Client.instance.FontLoaders.icon20.drawString("5", this.x + 3.0F, this.y + 7.0F, (new Color(255, 255, 255, globalAlpha)).getRGB());
/*     */           break;
/*     */         case "World":
/* 156 */           Client.instance.FontLoaders.guiicons24.drawString("E", this.x + 3.0F, this.y + 6.0F, (new Color(255, 255, 255, globalAlpha)).getRGB());
/*     */           break;
/*     */       } 
/* 159 */       if (this.expand >= 17.0F) {
/* 160 */         GL11.glPushMatrix();
/*     */         
/* 162 */         this.buttons.forEach(b2 -> {
/*     */               if (this.totalY < this.y || this.y > this.y + this.height) {
/*     */                 return;
/*     */               }
/*     */               
/*     */               b2.render(mouseX, mouseY, new Limitation(this.x, this.y + 17.0F, this.x + 90.0F + this.allX, this.y + this.expand));
/*     */             });
/* 169 */         RenderUtil.drawImage(new ResourceLocation("client/clickgui/bottom.png"), this.x, this.y + 17.0F, 90.0F + this.allX, 36.0F);
/* 170 */         GL11.glPopMatrix();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 175 */     if (this.drag) {
/* 176 */       if (!Mouse.isButtonDown(0)) {
/* 177 */         this.drag = false;
/*     */       }
/* 179 */       this.x = AnimationUtil.moveUDFaster(this.x, mouseX - this.dragX);
/* 180 */       this.y = AnimationUtil.moveUDFaster(this.y, mouseY - this.dragY);
/* 181 */       ((Button)this.buttons.get(0)).y = this.y + 22.0F - this.scrollAnimation.getValue();
/* 182 */       for (Button b4 : this.buttons) {
/* 183 */         b4.x = this.x + 5.0F;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void runWheel(float height) {
/* 190 */     if (Mouse.hasWheel()) {
/* 191 */       int wheel = Mouse.getDWheel();
/* 192 */       if (this.totalY - height <= 0.0F) {
/*     */         return;
/*     */       }
/* 195 */       if (wheel < 0) {
/* 196 */         if (this.wheely < this.totalY - height) {
/* 197 */           this.wheely += (((Boolean)ClickGui.fpsMouseWheel.get()).booleanValue() ? 60 : 20) + Mouse.getDWheel();
/* 198 */           if (this.wheely < 0) {
/* 199 */             this.wheely = 0;
/*     */           }
/*     */         } 
/* 202 */       } else if (wheel > 0) {
/* 203 */         this.wheely -= (((Boolean)ClickGui.fpsMouseWheel.get()).booleanValue() ? 60 : 20) + Mouse.getDWheel();
/* 204 */         if (this.wheely < 0) {
/* 205 */           this.wheely = 0;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void mouseScroll(int mouseX, int mouseY, int amount) {
/* 212 */     if (mouseX > this.x - 2.0F && mouseX < this.x + 92.0F && mouseY > this.y - 2.0F && mouseY < this.y + 17.0F + this.expand) {
/* 213 */       this.scrollTo -= (amount / 120 * 28);
/*     */     }
/*     */   }
/*     */   
/*     */   public void click(int mouseX, int mouseY, int button) {
/* 218 */     if (mouseX > this.x - 2.0F && mouseX < this.x + 92.0F && mouseY > this.y - 2.0F && mouseY < this.y + 17.0F) {
/* 219 */       if (button == 1) {
/* 220 */         this.extended = !this.extended;
/* 221 */       } else if (button == 0) {
/* 222 */         this.drag = true;
/* 223 */         this.dragX = mouseX - this.x;
/* 224 */         this.dragY = mouseY - this.y;
/*     */       } 
/*     */     }
/* 227 */     if (this.extended)
/* 228 */       this.buttons.stream().filter(b2 -> (b2.y < this.y + this.expand)).forEach(b2 -> b2.click(mouseX, mouseY, button)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\list\Window.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */