/*     */ package awareline.main.ui.gui.clickgui.mode.box;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.mod.implement.visual.ctype.ClickGui;
/*     */ import awareline.main.mod.implement.visual.sucks.tenacityColor.ColorUtil;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.animations.SmoothAnimationTimer;
/*     */ import awareline.main.ui.font.cfont.CFontRenderer;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.ui.gui.clickgui.mode.distance.Palette;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.LWJGLException;
/*     */ import org.lwjgl.input.Cursor;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class FluxGui
/*     */   extends GuiScreen {
/*  35 */   private static List<Module> inSetting = new ArrayList<>();
/*  36 */   private static ModuleType currentCategory = ModuleType.Combat; private static float x;
/*     */   private static float y;
/*     */   private static int wheel;
/*     */   private boolean need2move;
/*     */   private float dragX;
/*     */   private float dragY;
/*  42 */   public final SmoothAnimationTimer scrollAnimation = new SmoothAnimationTimer(0.0F); float finheight;
/*     */   float animheight;
/*     */   private float animationPosition;
/*     */   boolean showSetting;
/*     */   float valueSizeY;
/*     */   float valueY;
/*     */   final ModuleType[] values;
/*     */   float index;
/*     */   final CFontRenderer font1;
/*     */   final CFontRenderer font2;
/*     */   final CFontRenderer font3;
/*     */   Cursor emptyCursor;
/*     */   
/*     */   public void initGui() {
/*  56 */     super.initGui();
/*  57 */     if (x > this.width)
/*  58 */       x = 130.0F; 
/*  59 */     if (y > this.height)
/*  60 */       y = 130.0F; 
/*  61 */     this.need2move = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FluxGui() {
/*  73 */     this.values = ModuleType.values();
/*  74 */     this.index = 20.0F;
/*  75 */     this.font1 = Client.instance.FontLoaders.regular18;
/*  76 */     this.font2 = Client.instance.FontLoaders.regular15;
/*  77 */     this.font3 = Client.instance.FontLoaders.regular14;
/*     */     this.need2move = false;
/*     */     this.dragX = 0.0F;
/*     */     this.dragY = 0.0F;
/*  81 */     this.animationPosition = 150.0F; } private void hideCursor() { if (this.emptyCursor == null) {
/*  82 */       if (Mouse.isCreated()) {
/*  83 */         int min = Cursor.getMinCursorSize();
/*  84 */         IntBuffer tmp = BufferUtils.createIntBuffer(min * min);
/*     */         try {
/*  86 */           this.emptyCursor = new Cursor(min, min, min / 2, min / 2, 1, tmp, null);
/*  87 */         } catch (LWJGLException e) {
/*  88 */           e.printStackTrace();
/*     */         } 
/*     */       } else {
/*  91 */         System.out.println("Could not create empty cursor before Mouse object is created");
/*     */       } 
/*     */     }
/*     */     try {
/*  95 */       Mouse.setNativeCursor(Mouse.isInsideWindow() ? this.emptyCursor : null);
/*  96 */     } catch (LWJGLException e) {
/*  97 */       e.printStackTrace();
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 104 */     hideCursor();
/* 105 */     this.animationPosition = AnimationUtil.moveUD(this.animationPosition, 0.0F);
/* 106 */     GL11.glRotatef(this.animationPosition, 0.0F, 0.0F, 0.0F);
/* 107 */     GL11.glTranslatef(0.0F, this.animationPosition, 0.0F);
/* 108 */     if (this.need2move) {
/* 109 */       x = mouseX - this.dragX;
/* 110 */       y = mouseY - this.dragY;
/*     */     } 
/* 112 */     if (!Mouse.isButtonDown(0) && this.need2move) {
/* 113 */       this.need2move = false;
/*     */     }
/* 115 */     RenderUtil.drawBorderedRectNotSmooth((x - 25.0F), (y + 1.0F), (x + 492.0F), (y + 314.0F), 1.0F, (new Color(20, 20, 20))
/* 116 */         .getRGB(), (new Color(20, 20, 20)).getRGB());
/* 117 */     SimpleRender.drawRectFloat(x + 75.0F, y + 1.0F, x + 492.0F, y + 314.0F, (new Color(0, 0, 0)).getRGB());
/*     */     
/* 119 */     SimpleRender.drawRectFloat(x + 75.0F, y + 42.0F + this.animheight, x - 25.5F, y + 65.0F + this.animheight, getColor());
/*     */     
/* 121 */     Client.instance.FontLoaders.NovICON42.drawString("c", x - 5.0F - 13.0F, y + 10.0F, getColor());
/* 122 */     Client.instance.getClass(); Client.instance.FontLoaders.regular26.drawString("Awareline", x + 5.0F, y + 12.0F, (new Color(((Double)HUD.r
/* 123 */           .getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue())).getRGB());
/*     */     
/* 125 */     float strX = x + 20.0F - 15.0F;
/* 126 */     for (int i = 0; i < this.values.length; i++) {
/* 127 */       if (this.values[i] == currentCategory) {
/* 128 */         this.finheight = (i * 30);
/*     */       }
/* 130 */       Client.instance.FontLoaders.regular19.drawString(this.values[i].name(), strX - 1.0F, y + 49.0F + (i * 30), -1);
/* 131 */       if (this.values[i].toString().equals("Combat")) {
/* 132 */         Client.instance.FontLoaders.icon24.drawString("1", strX - 15.0F, y + 50.0F - 1.0F, -1);
/* 133 */       } else if (this.values[i].toString().equals("Movement")) {
/* 134 */         Client.instance.FontLoaders.icon26.drawString("5", strX - 15.0F, y + 50.0F + 30.0F - 1.0F, -1);
/* 135 */       } else if (this.values[i].toString().equals("Render")) {
/* 136 */         Client.instance.FontLoaders.guiicons22.drawString("F", strX - 15.0F, y + 50.0F + 60.0F, -1);
/* 137 */       } else if (this.values[i].toString().equals("Player")) {
/* 138 */         Client.instance.FontLoaders.guiicons28.drawString("C", strX - 15.0F, y + 50.0F + 90.0F - 1.0F, -1);
/* 139 */       } else if (this.values[i].toString().equals("World")) {
/* 140 */         Client.instance.FontLoaders.guiicons30.drawString("E", strX - 15.0F, y + 50.0F + 120.0F - 1.0F, -1);
/* 141 */       } else if (this.values[i].toString().equals("Misc")) {
/* 142 */         Client.instance.FontLoaders.guiicons28.drawString("G", strX - 15.0F, y + 50.0F + 150.0F - 1.0F, -1);
/* 143 */       } else if (this.values[i].toString().equals("Globals")) {
/* 144 */         Client.instance.FontLoaders.guiicons28.drawString("J", strX - 15.0F, y + 50.0F + 180.0F - 1.0F, -1);
/* 145 */       } else if (this.values[i].toString().equals("Script")) {
/* 146 */         Client.instance.FontLoaders.novoicons25.drawString("G", strX - 15.0F, y + 50.0F + 210.0F, -1);
/*     */       } 
/*     */     } 
/* 149 */     this.animheight = AnimationUtil.moveUD(this.animheight, this.finheight);
/* 150 */     float startX = x + 80.0F + 2.0F + 13.0F;
/* 151 */     float startY = y + 9.0F + 2.0F + 28.0F - 30.0F + 30.0F;
/*     */ 
/*     */ 
/*     */     
/* 155 */     float length = 375.0F;
/* 156 */     RenderUtil.startGlScissor((int)startX, (int)(startY - 16.0F), 375, 285 - (int)this.animationPosition);
/* 157 */     this.index = 20.0F;
/* 158 */     float moduleY = this.scrollAnimation.getValue();
/* 159 */     for (Module m : Client.instance.getModuleManager().getModules()) {
/* 160 */       if (m.getModuleType() != currentCategory)
/*     */         continue; 
/* 162 */       if (this.animationPosition > 32.0F) {
/*     */         continue;
/*     */       }
/* 165 */       if (moduleY > y + 170.0F) {
/*     */         continue;
/*     */       }
/* 168 */       RenderUtil.drawRoundedRectSmooth(startX, startY + moduleY, startX + 375.0F, startY + moduleY + 31.0F, 3.0F, (new Color(20, 20, 20)).getRGB());
/*     */       
/* 170 */       this.font1.drawString(m.getBreakName(true), startX + 8.0F, startY + 9.0F + moduleY + 2.0F, -1);
/*     */       
/* 172 */       RenderUtil.drawRoundRect((startX + 375.0F - 25.0F - this.index), (startY + moduleY + 10.0F), (startX + 375.0F - 5.0F - this.index), (startY + moduleY + 20.0F), 5, (new Color(0, 0, 0)).getRGB());
/* 173 */       m.enableAnim = AnimationUtil.moveUD(m.enableAnim, m.isEnabled() ? 14.0F : 24.0F, SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/* 174 */       m.disableAnim = AnimationUtil.moveUD(m.disableAnim, m.isEnabled() ? 6.0F : 16.0F, SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/* 175 */       float left = startX + 375.0F - m.enableAnim;
/* 176 */       float right = startX + 375.0F - m.disableAnim;
/*     */       
/* 178 */       RenderUtil.drawRoundedRectSmooth(left - this.index, startY + moduleY + 11.0F, right - this.index, startY + moduleY + 19.0F, 4.0F, getColor());
/*     */       
/* 180 */       this.showSetting = inSetting.contains(m);
/* 181 */       if (!m.getValues().isEmpty()) {
/* 182 */         Client.instance.FontLoaders.FLUXICON21.drawString(this.showSetting ? "h" : "i", startX + 8.0F + 375.0F - 27.0F, startY + 9.0F + moduleY + 3.0F, -1);
/*     */       }
/* 184 */       this.valueSizeY = m.getValues().size() * 20.0F + 15.0F;
/* 185 */       this.valueY = moduleY + 48.0F;
/* 186 */       if (this.showSetting) {
/* 187 */         SimpleRender.drawRectFloat(startX + 3.0F, startY + moduleY + 32.0F, startX + 375.0F - 3.0F, startY + moduleY + 38.0F, (new Color(30, 30, 30)).getRGB());
/* 188 */         RenderUtil.drawRoundedRectSmooth(startX + 3.0F, startY + moduleY + 32.0F, startX + 375.0F - 3.0F, startY + moduleY + 24.0F + this.valueSizeY + 8.0F, 3.0F, (new Color(30, 30, 30)).getRGB());
/* 189 */         for (Value<?> setting : (Iterable<Value<?>>)m.getValues()) {
/* 190 */           if (setting instanceof Mode) {
/* 191 */             Mode s = (Mode)setting;
/* 192 */             this.font2.drawString(s.getName(), startX + 10.0F, startY + this.valueY - 1.0F, -1);
/* 193 */             RenderUtil.drawRoundedRectSmooth(startX + 375.0F - 85.0F, startY + this.valueY - 4.0F, startX + 375.0F - 6.0F, startY + this.valueY + 8.0F, 3.0F, (new Color(10, 10, 10)).getRGB());
/*     */             
/* 195 */             float longValue = (startX + 375.0F - 6.0F - startX + 375.0F - 85.0F) / 2.0F;
/* 196 */             this.font2.drawCenteredString(s.getModeAsString(), startX + 375.0F - 6.0F - longValue, startY + this.valueY - 0.5F, getColor());
/* 197 */             boolean hover = (mouseX > startX + 375.0F - 85.0F && mouseX < startX + 375.0F - 6.0F && mouseY > startY + this.valueY - 4.0F && mouseY < startY + this.valueY + 8.0F);
/* 198 */             if (hover) {
/* 199 */               RenderUtil.drawRoundRect((startX + 375.0F - 85.0F), (startY + this.valueY - 4.0F), (startX + 375.0F - 6.0F), (startY + this.valueY + 8.0F), 3, (new Color(0, 0, 0, 100)).getRGB());
/*     */             }
/*     */           } 
/* 202 */           if (setting instanceof Numbers) {
/* 203 */             Numbers<Number> s = (Numbers)setting;
/* 204 */             this.font2.drawString(s.getBreakName(), startX + 10.0F, startY + this.valueY - 3.0F, -1);
/* 205 */             double inc = s.getIncrement().doubleValue();
/* 206 */             double max = s.getMaximum().doubleValue();
/* 207 */             double min = s.getMinimum().doubleValue();
/* 208 */             double valn = ((Number)s.getValue()).doubleValue();
/* 209 */             float longValue = startX + 375.0F - 6.0F - startX + 375.0F - 83.0F;
/*     */ 
/*     */ 
/*     */             
/* 213 */             double render = (77.0F * (((Number)s.getValue()).floatValue() - s.getMinimum().floatValue()) / (s.getMaximum().floatValue() - s.getMinimum().floatValue()));
/* 214 */             s.smoothAnim = AnimationUtil.moveUD(s.smoothAnim, (float)render);
/* 215 */             this.font3.drawString(String.valueOf(((Number)s.getValue()).doubleValue()), startX + 375.0F - 84.0F, startY + this.valueY - 2.0F, -1);
/*     */             
/* 217 */             RenderUtil.drawRoundRect((startX + 375.0F - 85.0F), (startY + this.valueY + 5.0F), (startX + 375.0F - 6.0F), (startY + this.valueY + 7.0F), 1, (new Color(10, 10, 10)).getRGB());
/* 218 */             RenderUtil.drawRoundRect((startX + 375.0F - 85.0F), (startY + this.valueY + 5.0F), (startX + 375.0F - 85.0F + s.smoothAnim + 2.0F), (startY + this.valueY + 7.0F), 1, 
/* 219 */                 getColor());
/*     */             
/* 221 */             boolean hover = (mouseX > startX + 375.0F - 88.0F && mouseX < startX + 375.0F - 3.0F && mouseY > startY + this.valueY + 2.0F && mouseY < startY + this.valueY + 11.0F);
/* 222 */             if (hover) {
/* 223 */               RenderUtil.drawRoundRect((startX + 375.0F - 85.0F), (startY + this.valueY + 5.0F), (startX + 375.0F - 6.0F), (startY + this.valueY + 7.0F), 1, (new Color(0, 0, 0, 100)).getRGB());
/* 224 */               if (Mouse.isButtonDown(0)) {
/* 225 */                 double valAbs = (mouseX - startX + 375.0F - 85.0F);
/* 226 */                 double perc = valAbs / longValue * Math.max(Math.min(valn / max, 0.0D), 1.0D);
/* 227 */                 perc = Math.min(Math.max(0.0D, perc), 1.0D);
/* 228 */                 double valRel = (max - min) * perc;
/* 229 */                 double val = min + valRel;
/* 230 */                 val = Math.round(val * 1.0D / inc) / 1.0D / inc;
/* 231 */                 s.setValue(Double.valueOf(val));
/*     */               } 
/*     */             } 
/*     */           } 
/* 235 */           if (setting instanceof Option) {
/* 236 */             Option<Boolean> s = (Option)setting;
/* 237 */             this.font2.drawString(s.getBreakName(), startX + 10.0F, startY + this.valueY - 3.0F, -1);
/* 238 */             boolean hover = (mouseX > startX + 375.0F - 18.0F && mouseX < startX + 375.0F - 6.0F && mouseY > startY + this.valueY - 4.0F && mouseY < startY + this.valueY + 8.0F);
/* 239 */             RenderUtil.drawRoundedRectSmooth(startX + 375.0F - 18.0F, startY + this.valueY - 4.0F, startX + 375.0F - 6.0F, startY + this.valueY + 8.0F, 2.0F, (new Color(10, 10, 10)).getRGB());
/* 240 */             if (((Boolean)s.getValue()).booleanValue()) {
/* 241 */               Client.instance.FontLoaders.NovICON24.drawString("j", startX + 375.0F - 18.0F, startY + this.valueY - 1.0F, getColor());
/*     */             }
/* 243 */             if (hover) {
/* 244 */               RenderUtil.drawRoundRect((startX + 375.0F - 18.0F), (startY + this.valueY - 4.0F), (startX + 375.0F - 6.0F), (startY + this.valueY + 8.0F), 2, (new Color(0, 0, 0, 100)).getRGB());
/*     */             }
/*     */           } 
/* 247 */           this.valueY += 20.0F;
/*     */         } 
/* 249 */         drawGradientSidewaysV(startX + 3.0F, startY + moduleY + 24.0F + 8.0F, startX + 375.0F - 3.0F, startY + moduleY + 34.0F + 8.0F, (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0)).getRGB());
/*     */       } 
/* 251 */       m.openAnim = AnimationUtil.moveUD(m.openAnim, this.showSetting ? (26.0F + this.valueSizeY + 15.0F) : 51.0F, SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/* 252 */       moduleY += m.openAnim;
/*     */     } 
/* 254 */     RenderUtil.stopGlScissor();
/* 255 */     int real = Mouse.getDWheel();
/* 256 */     float moduleHeight = moduleY - this.scrollAnimation.getValue();
/* 257 */     if (Mouse.hasWheel() && mouseX > startX - 15.0F && mouseY > startY - 40.0F && mouseX < startX + 395.0F && mouseY < startY + 267.0F) {
/* 258 */       if (real > 0 && wheel < 0) {
/* 259 */         for (int j = 0; j < 5 && 
/* 260 */           wheel < 0; j++)
/*     */         {
/* 262 */           wheel += (((Boolean)ClickGui.fpsMouseWheel.get()).booleanValue() ? 21 : 7) + Mouse.getDWheel();
/*     */         }
/*     */       } else {
/* 265 */         for (int j = 0; j < 5 && 
/* 266 */           real < 0 && moduleHeight > 158.0F && Math.abs(wheel) < moduleHeight - 154.0F; j++)
/*     */         {
/* 268 */           wheel -= (((Boolean)ClickGui.fpsMouseWheel.get()).booleanValue() ? 21 : 7) + Mouse.getDWheel();
/*     */         }
/*     */       } 
/*     */     }
/* 272 */     this.scrollAnimation.setTarget(wheel);
/* 273 */     this.scrollAnimation.update(true);
/*     */     
/* 275 */     GL11.glPushMatrix();
/* 276 */     GlStateManager.enableBlend();
/* 277 */     GlStateManager.scale(1.0F, 1.0F, 1.0F);
/* 278 */     GlStateManager.translate(0.0F, 0.0F, 0.0F);
/* 279 */     Client.instance.FontLoaders.FLUXICON17.drawStringWithShadow("p", (mouseX - 1), (mouseY + 2), ColorUtil.transparency(Palette.fade(new Color(255, 255, 255)), 255.0D));
/* 280 */     GlStateManager.scale(1.0F, 1.0F, 1.0F);
/* 281 */     GlStateManager.translate(0.0F, 0.0F, 0.0F);
/* 282 */     GlStateManager.disableBlend();
/* 283 */     GL11.glPopMatrix();
/* 284 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 290 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 291 */     boolean hover2top = (mouseX > x && mouseX < x + 273.0F - 4.0F + 165.0F + 84.0F + 11.0F && mouseY > y && mouseY < y + 35.0F);
/* 292 */     if (hover2top && mouseButton == 0) {
/* 293 */       this.dragX = mouseX - x;
/* 294 */       this.dragY = mouseY - y;
/* 295 */       this.need2move = true;
/*     */     } else {
/* 297 */       float cateY = 0.0F;
/* 298 */       for (ModuleType category : ModuleType.values()) {
/* 299 */         float strY = y + 55.0F + cateY;
/* 300 */         boolean typehover = (mouseX > x + 5.0F - 30.0F && mouseX < x + 65.0F + 30.0F && mouseY > strY - 30.0F && mouseY < strY + 20.0F);
/* 301 */         if (typehover && mouseButton == 0) {
/* 302 */           currentCategory = category;
/* 303 */           wheel = 0;
/*     */           break;
/*     */         } 
/* 306 */         cateY += 26.0F;
/*     */       } 
/* 308 */       float startX = x + 80.0F + 2.0F + 13.0F;
/* 309 */       float startY = y + 9.0F + 2.0F + 28.0F - 30.0F + 30.0F;
/*     */ 
/*     */ 
/*     */       
/* 313 */       float length = 375.0F;
/* 314 */       float moduleY = this.scrollAnimation.getValue();
/* 315 */       float size = 85.0F;
/* 316 */       for (Module m : Client.instance.getModuleManager().getModules()) {
/* 317 */         if (m.getModuleType() != currentCategory)
/*     */           continue; 
/* 319 */         boolean onToggleButton = (mouseX > startX + length - 25.0F - this.index && mouseX < startX + length - 5.0F - this.index && mouseY > startY + moduleY + 7.0F && mouseY < startY + moduleY + 20.0F && mouseY < startY + 14.0F + 140.0F + size && mouseY > startY);
/* 320 */         boolean onModuleRect = (mouseX > startX && mouseX < startX + length && mouseY > startY + moduleY && mouseY < startY + moduleY + 28.0F && mouseY < startY + 14.0F + 140.0F + size && mouseY > startY);
/* 321 */         if (onToggleButton && mouseButton == 0)
/* 322 */           m.setEnabled(!m.isEnabled()); 
/* 323 */         if (onModuleRect && mouseButton == 1)
/* 324 */           if (inSetting.contains(m)) {
/* 325 */             inSetting.remove(m);
/* 326 */           } else if (!m.getValues().isEmpty()) {
/* 327 */             inSetting.add(m);
/*     */           }  
/* 329 */         boolean showSetting = inSetting.contains(m);
/* 330 */         float valueSizeY = (m.getValues().size() * 20 + 5 + 10);
/* 331 */         float valueY = moduleY + 35.0F + 8.0F + 5.0F;
/* 332 */         if (showSetting) {
/* 333 */           SimpleRender.drawRectFloat(startX + 3.0F, startY + moduleY + 24.0F, startX + length - 3.0F, startY + moduleY + 24.0F + valueSizeY, (new Color(30, 30, 30)).getRGB());
/* 334 */           for (Value<?> setting : (Iterable<Value<?>>)m.getValues()) {
/* 335 */             if (setting instanceof Mode) {
/* 336 */               Mode s = (Mode)setting;
/* 337 */               boolean hover = (mouseX > startX + length - 85.0F && mouseX < startX + length - 6.0F && mouseY > startY + valueY - 4.0F && mouseY < startY + valueY + 8.0F);
/* 338 */               if (hover && (
/* 339 */                 mouseButton == 0 || mouseButton == 1)) {
/* 340 */                 List<String> options = Arrays.asList(s.getModes());
/*     */                 
/* 342 */                 int indexz = options.indexOf(s.getValue());
/* 343 */                 if (mouseButton == 0) {
/* 344 */                   indexz++;
/*     */                 } else {
/* 346 */                   indexz--;
/*     */                 } 
/* 348 */                 if (indexz >= options.size()) {
/* 349 */                   indexz = 0;
/* 350 */                 } else if (indexz < 0) {
/* 351 */                   indexz = options.size() - 1;
/*     */                 } 
/* 353 */                 s.setValue(s.getModes()[indexz]);
/*     */               } 
/*     */             } 
/*     */             
/* 357 */             if (setting instanceof Option) {
/* 358 */               Option<Boolean> s = (Option)setting;
/* 359 */               boolean hover = (mouseX > startX + length - 18.0F && mouseX < startX + length - 6.0F && mouseY > startY + valueY - 4.0F && mouseY < startY + valueY + 8.0F);
/* 360 */               if (hover && (mouseButton == 0 || mouseButton == 2 || mouseButton == 1)) {
/* 361 */                 s.setValue(Boolean.valueOf(!((Boolean)s.getValue()).booleanValue()));
/*     */               }
/*     */             } 
/* 364 */             valueY += 20.0F;
/*     */           } 
/*     */         } 
/* 367 */         moduleY += showSetting ? (26.0F + valueSizeY) : 51.0F;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 374 */     super.mouseReleased(mouseX, mouseY, state);
/* 375 */     float startY = y + 9.0F + 2.0F + 28.0F;
/* 376 */     boolean hover2top = (mouseX > x + 1.0F && mouseX < x + 349.0F && mouseY > y + 1.0F && mouseY < y + 9.0F && mouseY < startY + 14.0F + 140.0F + 85.0F && mouseY > startY);
/*     */     
/* 378 */     if (hover2top && state == 0) {
/* 379 */       this.dragX = mouseX - x;
/* 380 */       this.dragY = mouseY - y;
/* 381 */       this.need2move = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 387 */     super.onGuiClosed();
/* 388 */     ClickGui.getInstance.setEnabled(false);
/*     */     try {
/* 390 */       Mouse.setNativeCursor(null);
/* 391 */     } catch (Throwable e) {
/* 392 */       e.printStackTrace();
/*     */     } 
/* 394 */     ClickGui.memoriseX = x;
/* 395 */     ClickGui.memoriseY = y;
/* 396 */     ClickGui.memoriseWheel = wheel;
/* 397 */     ClickGui.memoriseML = inSetting;
/* 398 */     ClickGui.memoriseCatecory = currentCategory;
/*     */   }
/*     */   
/*     */   public static void setX(float state) {
/* 402 */     x = state;
/*     */   }
/*     */   
/*     */   public static void setY(float state) {
/* 406 */     y = state;
/*     */   }
/*     */   
/*     */   public static void setCategory(ModuleType state) {
/* 410 */     currentCategory = state;
/*     */   }
/*     */   
/*     */   public static void setInSetting(List<Module> moduleList) {
/* 414 */     inSetting = moduleList;
/*     */   }
/*     */   
/*     */   public static void setWheel(int state) {
/* 418 */     wheel = state;
/*     */   }
/*     */   
/*     */   private int getColor() {
/* 422 */     return (new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue())).getRGB();
/*     */   }
/*     */   
/*     */   public void drawGradientSidewaysV(float left, float top, float right, float bottom, int col1, int col2) {
/* 426 */     float f = (col1 >> 24 & 0xFF) / 255.0F;
/* 427 */     float f1 = (col1 >> 16 & 0xFF) / 255.0F;
/* 428 */     float f2 = (col1 >> 8 & 0xFF) / 255.0F;
/* 429 */     float f3 = (col1 & 0xFF) / 255.0F;
/* 430 */     float f4 = (col2 >> 24 & 0xFF) / 255.0F;
/* 431 */     float f5 = (col2 >> 16 & 0xFF) / 255.0F;
/* 432 */     float f6 = (col2 >> 8 & 0xFF) / 255.0F;
/* 433 */     float f7 = (col2 & 0xFF) / 255.0F;
/* 434 */     GL11.glEnable(3042);
/* 435 */     GL11.glDisable(3553);
/* 436 */     GL11.glBlendFunc(770, 771);
/* 437 */     GL11.glEnable(2848);
/* 438 */     GL11.glShadeModel(7425);
/* 439 */     GL11.glPushMatrix();
/* 440 */     GL11.glBegin(7);
/* 441 */     GL11.glColor4f(f1, f2, f3, f);
/* 442 */     GL11.glVertex2d(left, bottom);
/* 443 */     GL11.glVertex2d(right, bottom);
/* 444 */     GL11.glColor4f(f5, f6, f7, f4);
/* 445 */     GL11.glVertex2d(right, top);
/* 446 */     GL11.glVertex2d(left, top);
/* 447 */     GL11.glEnd();
/* 448 */     GL11.glPopMatrix();
/* 449 */     GL11.glEnable(3553);
/* 450 */     GL11.glDisable(3042);
/* 451 */     GL11.glDisable(2848);
/* 452 */     GL11.glShadeModel(7424);
/* 453 */     drawRect(0, 0, 0, 0, 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\box\FluxGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */