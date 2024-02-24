/*     */ package awareline.main.ui.gui.clickgui.mode.chocolate;
/*     */ 
/*     */ import awareline.antileak.VerifyData;
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.mod.implement.visual.ctype.ClickGui;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.animations.SmoothAnimationTimer;
/*     */ import awareline.main.ui.font.cfont.CFontRenderer;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class GuiClickUI extends GuiScreen {
/*  29 */   private static List<Module> inSetting = new CopyOnWriteArrayList<>();
/*     */   private static ModuleType currentCategory;
/*     */   private static float x;
/*  32 */   final CFontRenderer font1 = Client.instance.FontLoaders.regular18; private static float y; private static float wheel;
/*  33 */   final CFontRenderer font2 = Client.instance.FontLoaders.regular16;
/*  34 */   final CFontRenderer font3 = Client.instance.FontLoaders.regular14;
/*  35 */   public final SmoothAnimationTimer scrollAnimation = new SmoothAnimationTimer(0.0F);
/*     */   public float size;
/*     */   public boolean close;
/*     */   private boolean need2move;
/*     */   private float dragX;
/*     */   private float dragY;
/*     */   
/*     */   public GuiClickUI() {
/*  43 */     this.need2move = false;
/*  44 */     this.dragX = 0.0F;
/*  45 */     this.dragY = 0.0F;
/*     */   }
/*     */   
/*     */   public static void setX(float state) {
/*  49 */     x = state;
/*     */   }
/*     */   
/*     */   public static void setY(float state) {
/*  53 */     y = state;
/*     */   }
/*     */   
/*     */   public static void setCategory(ModuleType state) {
/*  57 */     currentCategory = state;
/*     */   }
/*     */   
/*     */   public static void setInSetting(List<Module> moduleList) {
/*  61 */     inSetting = moduleList;
/*     */   }
/*     */   
/*     */   public static void setWheel(int state) {
/*  65 */     wheel = state;
/*     */   }
/*     */   
/*     */   public static int getColor() {
/*  69 */     return (new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue(), 255))
/*  70 */       .getRGB();
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  75 */     super.initGui();
/*  76 */     if (x > this.width)
/*  77 */       x = 30.0F; 
/*  78 */     if (y > this.height)
/*  79 */       y = 30.0F; 
/*  80 */     this.need2move = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/*  85 */     return false;
/*     */   }
/*     */   
/*     */   public float lerp(float a, float b, float c) {
/*  89 */     return a + c * (b - a);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  95 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/*  97 */     if (!this.close) {
/*  98 */       this.size = lerp(this.size, 1.0F, 0.7F / Minecraft.getDebugFPS() / 20.0F);
/*     */     } else {
/* 100 */       this.size = lerp(this.size, 0.0F, 0.75F / Minecraft.getDebugFPS() / 20.0F);
/* 101 */       if (this.size < 0.01F) {
/* 102 */         this.mc.currentScreen = null;
/* 103 */         this.mc.mouseHelper.grabMouseCursor();
/* 104 */         this.mc.inGameHasFocus = true;
/* 105 */         ClickGui.memoriseX = x;
/* 106 */         ClickGui.memoriseY = y;
/* 107 */         ClickGui.memoriseWheel = (int)wheel;
/* 108 */         ClickGui.memoriseML = inSetting;
/* 109 */         ClickGui.memoriseCatecory = currentCategory;
/*     */         
/*     */         try {
/* 112 */           Mouse.setNativeCursor(null);
/* 113 */         } catch (Throwable e) {
/* 114 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } 
/* 118 */     GlStateManager.translate(x - x * this.size + this.width / 2.0F - this.width / 2.0F * this.size, y - y * this.size + this.height / 2.0F - this.height / 2.0F * this.size, x - x * this.size + this.width / 2.0F - this.width / 2.0F * this.size);
/* 119 */     GlStateManager.scale(this.size, this.size, 1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     if (this.need2move) {
/* 125 */       x = mouseX - this.dragX;
/* 126 */       y = mouseY - this.dragY;
/*     */     } 
/* 128 */     if (!Mouse.isButtonDown(0) && this.need2move) {
/* 129 */       this.need2move = false;
/*     */     }
/* 131 */     RenderUtil.drawBorderedRect(x, y, (x + 273.0F), (y + 198.0F), 3.0F, (new Color(20, 20, 20)).getRGB(), getColor());
/* 132 */     RenderUtil.drawBorderedRect((x + 2.0F), (y + 2.0F), (x + 273.0F - 2.0F), (y + 198.0F - 2.0F), 1.0F, getColor(), (new Color(20, 20, 20))
/* 133 */         .getRGB());
/* 134 */     SimpleRender.drawRectFloat(x + 70.0F, y + 35.0F, x + 269.0F, y + 195.0F, (new Color(0, 0, 0)).getRGB());
/* 135 */     Client.instance.getClass(); Client.instance.FontLoaders.regular26.drawStringWithShadow("Awareline", (x + 8.0F), (y + 8.0F), (new Color(180, 180, 180)).getRGB());
/* 136 */     this.font2.drawStringWithShadow(VerifyData.instance.clientVersion, (x + 8.0F), (y + 24.0F), (new Color(180, 180, 180)).getRGB());
/*     */     
/* 138 */     RenderUtil.drawGradientSideways((x + 70.0F), (y + 35.0F), (x + 80.0F), (y + 195.0F), (new Color(20, 20, 20)).getRGB(), (new Color(0, 0, 0, 0))
/* 139 */         .getRGB());
/* 140 */     RenderUtil.drawGradientSidewaysV((x + 3.0F), (y + 35.0F), (x + 273.0F - 3.0F), (y + 45.0F), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0))
/* 141 */         .getRGB());
/* 142 */     int cateY = 0;
/* 143 */     float strX = x + 40.0F;
/* 144 */     for (ModuleType category : ModuleType.values()) {
/* 145 */       float strY = y + 55.0F - 20.0F + cateY;
/* 146 */       boolean hover = (mouseX > x + 5.0F && mouseX < x + 65.0F && mouseY > strY && mouseY < strY + 20.0F);
/* 147 */       Client.instance.FontLoaders.regular20.drawCenteredStringWithShadow(category.name(), strX - 1.0F, strY + 6.0F, (category == currentCategory) ? 
/* 148 */           getColor() : (new Color(hover ? 255 : 140, hover ? 255 : 140, hover ? 255 : 140))
/* 149 */           .getRGB());
/* 150 */       cateY += 20;
/*     */     } 
/* 152 */     float startX = x + 80.0F + 2.0F;
/* 153 */     float startY = y + 9.0F + 2.0F + 28.0F;
/* 154 */     float length = 185.0F;
/* 155 */     float moduleY = this.scrollAnimation.getValue();
/* 156 */     RenderUtil.startGlScissor((int)startX, (int)(startY + 14.0F), 185, 140);
/* 157 */     for (Module m : Client.instance.getModuleManager().getModules()) {
/* 158 */       if (m.getType() != currentCategory)
/*     */         continue; 
/* 160 */       if (this.size < 0.85F) {
/*     */         continue;
/*     */       }
/* 163 */       if (moduleY > y + 170.0F) {
/*     */         continue;
/*     */       }
/* 166 */       RenderUtil.drawRoundedRectSmooth(startX, startY + moduleY, startX + 185.0F, startY + moduleY + 24.0F, 3.0F, (new Color(20, 20, 20))
/* 167 */           .getRGB());
/* 168 */       this.font1.drawStringWithShadow(m.getBreakName(true), (startX + 8.0F), (startY + 9.0F + moduleY), -1);
/* 169 */       RenderUtil.drawRoundRect((startX + 185.0F - 25.0F), (startY + moduleY + 7.0F), (startX + 185.0F - 5.0F), (startY + moduleY + 17.0F), 5, (new Color(0, 0, 0))
/* 170 */           .getRGB());
/* 171 */       m.enableAnim = AnimationUtil.moveUD(m.enableAnim, m.isEnabled() ? 14.0F : 24.0F, SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/* 172 */       m.disableAnim = AnimationUtil.moveUD(m.disableAnim, m.isEnabled() ? 6.0F : 16.0F, SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/* 173 */       float left = startX + 185.0F - m.enableAnim;
/* 174 */       float right = startX + 185.0F - m.disableAnim;
/* 175 */       RenderUtil.drawRoundedRectSmooth(left, startY + moduleY + 8.0F, right, startY + moduleY + 16.0F, 4.0F, getColor());
/*     */       
/* 177 */       boolean showSetting = inSetting.contains(m);
/* 178 */       int valueSizeY = m.getValues().size() * 20 + 5;
/* 179 */       float valueY = moduleY + 35.0F;
/* 180 */       if (showSetting) {
/* 181 */         SimpleRender.drawRectFloat(startX + 3.0F, startY + moduleY + 24.0F, startX + 185.0F - 3.0F, startY + moduleY + 30.0F, (new Color(30, 30, 30))
/* 182 */             .getRGB());
/* 183 */         RenderUtil.drawRoundRect((startX + 3.0F), (startY + moduleY + 24.0F), (startX + 185.0F - 3.0F), (startY + moduleY + 24.0F + valueSizeY), 3, (new Color(30, 30, 30))
/* 184 */             .getRGB());
/* 185 */         for (Value<?> setting : (Iterable<Value<?>>)m.getValues()) {
/* 186 */           if (setting instanceof Mode) {
/* 187 */             Mode s = (Mode)setting;
/* 188 */             this.font2.drawString(s.getBreakName(), startX + 10.0F, startY + valueY - 1.0F, -1);
/* 189 */             RenderUtil.drawRoundRect((startX + 185.0F - 85.0F), (startY + valueY - 4.0F), (startX + 185.0F - 6.0F), (startY + valueY + 8.0F), 3, (new Color(10, 10, 10))
/* 190 */                 .getRGB());
/* 191 */             int longValue = (int)((startX + 185.0F - 6.0F - startX + 185.0F - 85.0F) / 2.0F);
/* 192 */             this.font2.drawCenteredString(s.getModeAsString(), startX + 185.0F - 6.0F - longValue, startY + valueY - 0.5F, 
/* 193 */                 getColor());
/* 194 */             boolean hover = (mouseX > startX + 185.0F - 85.0F && mouseX < startX + 185.0F - 6.0F && mouseY > startY + valueY - 4.0F && mouseY < startY + valueY + 8.0F);
/*     */             
/* 196 */             if (hover) {
/* 197 */               RenderUtil.drawRoundRect((startX + 185.0F - 85.0F), (startY + valueY - 4.0F), (startX + 185.0F - 6.0F), (startY + valueY + 8.0F), 3, (new Color(0, 0, 0, 100))
/* 198 */                   .getRGB());
/*     */             }
/*     */           } 
/* 201 */           if (setting instanceof Numbers) {
/* 202 */             Numbers<Number> s = (Numbers)setting;
/* 203 */             this.font2.drawString(s.getName(), startX + 10.0F, startY + valueY - 3.0F, -1);
/* 204 */             double inc = s.getIncrement().doubleValue();
/* 205 */             double max = s.getMaximum().doubleValue();
/* 206 */             double min = s.getMinimum().doubleValue();
/* 207 */             double valn = ((Number)s.getValue()).doubleValue();
/* 208 */             int longValue = (int)(startX + 185.0F - 6.0F - startX + 185.0F - 83.0F);
/*     */ 
/*     */ 
/*     */             
/* 212 */             double render = (77.0F * (((Number)s.getValue()).floatValue() - s.getMinimum().floatValue()) / (s.getMaximum().floatValue() - s.getMinimum().floatValue()));
/* 213 */             s.smoothAnim = AnimationUtil.moveUD(s.smoothAnim, (float)render);
/* 214 */             this.font3.drawString(String.valueOf(((Number)s.getValue()).doubleValue()), startX + 185.0F - 84.0F, startY + valueY - 2.0F, -1);
/*     */             
/* 216 */             RenderUtil.drawRoundRect((startX + 185.0F - 85.0F), (startY + valueY + 5.0F), (startX + 185.0F - 6.0F), (startY + valueY + 7.0F), 1, (new Color(10, 10, 10))
/* 217 */                 .getRGB());
/*     */             
/* 219 */             RenderUtil.drawRoundRect((startX + 185.0F - 85.0F), (startY + valueY + 5.0F), (startX + 185.0F - 85.0F + s.smoothAnim + 2.0F), (startY + valueY + 7.0F), 1, 
/*     */                 
/* 221 */                 getColor());
/*     */             
/* 223 */             boolean hover = (mouseX > startX + 185.0F - 88.0F && mouseX < startX + 185.0F - 3.0F && mouseY > startY + valueY + 2.0F && mouseY < startY + valueY + 11.0F);
/*     */             
/* 225 */             if (hover) {
/* 226 */               RenderUtil.drawRoundRect((startX + 185.0F - 85.0F), (startY + valueY + 5.0F), (startX + 185.0F - 6.0F), (startY + valueY + 7.0F), 1, (new Color(0, 0, 0, 100))
/* 227 */                   .getRGB());
/* 228 */               if (Mouse.isButtonDown(0)) {
/* 229 */                 double valAbs = (mouseX - startX + 185.0F - 85.0F);
/* 230 */                 double perc = valAbs / longValue * Math.max(Math.min(valn / max, 0.0D), 1.0D);
/* 231 */                 perc = Math.min(Math.max(0.0D, perc), 1.0D);
/* 232 */                 double valRel = (max - min) * perc;
/* 233 */                 double val = min + valRel;
/* 234 */                 val = Math.round(val * 1.0D / inc) / 1.0D / inc;
/* 235 */                 s.setValue(Double.valueOf(val));
/*     */               } 
/*     */             } 
/*     */           } 
/* 239 */           if (setting instanceof Option) {
/* 240 */             Option<Boolean> s = (Option)setting;
/* 241 */             this.font2.drawString(s.getName(), startX + 10.0F, startY + valueY - 3.0F, -1);
/* 242 */             boolean hover = (mouseX > startX + 185.0F - 18.0F && mouseX < startX + 185.0F - 6.0F && mouseY > startY + valueY - 4.0F && mouseY < startY + valueY + 8.0F);
/*     */             
/* 244 */             RenderUtil.drawRoundRect((startX + 185.0F - 18.0F), (startY + valueY - 4.0F), (startX + 185.0F - 6.0F), (startY + valueY + 8.0F), 2, (new Color(10, 10, 10))
/* 245 */                 .getRGB());
/* 246 */             if (((Boolean)s.getValue()).booleanValue()) {
/* 247 */               Client.instance.FontLoaders.NovICON24.drawString("j", startX + 185.0F - 18.0F, startY + valueY - 1.0F, getColor());
/*     */             }
/* 249 */             if (hover) {
/* 250 */               RenderUtil.drawRoundRect((startX + 185.0F - 18.0F), (startY + valueY - 4.0F), (startX + 185.0F - 6.0F), (startY + valueY + 8.0F), 2, (new Color(0, 0, 0, 100))
/* 251 */                   .getRGB());
/*     */             }
/*     */           } 
/* 254 */           valueY += 20.0F;
/*     */         } 
/*     */       } 
/* 257 */       m.openAnim = AnimationUtil.moveUD(m.openAnim, (showSetting ? (26 + valueSizeY) : 26), 
/* 258 */           SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/* 259 */       moduleY += m.openAnim;
/*     */     } 
/* 261 */     RenderUtil.stopGlScissor();
/* 262 */     int real = Mouse.getDWheel();
/* 263 */     float moduleHeight = moduleY - this.scrollAnimation.getValue();
/* 264 */     if (Mouse.hasWheel() && mouseX > startX && mouseY > startY && mouseX < startX + 270.0F && mouseY < startY + 237.0F) {
/* 265 */       if (real > 0 && wheel < 0.0F) {
/* 266 */         for (int i = 0; i < 5 && 
/* 267 */           wheel < 0.0F; i++)
/*     */         {
/* 269 */           wheel += ((((Boolean)ClickGui.fpsMouseWheel.get()).booleanValue() ? 21 : 7) + Mouse.getDWheel());
/*     */         }
/*     */       } else {
/* 272 */         for (int i = 0; i < 5 && 
/* 273 */           real < 0 && moduleHeight > 158.0F && Math.abs(wheel) < moduleHeight - 154.0F; i++)
/*     */         {
/* 275 */           wheel -= ((((Boolean)ClickGui.fpsMouseWheel.get()).booleanValue() ? 21 : 7) + Mouse.getDWheel());
/*     */         }
/*     */       } 
/*     */     }
/* 279 */     this.scrollAnimation.setTarget(wheel);
/* 280 */     this.scrollAnimation.update(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 286 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 287 */     boolean hover2top = (mouseX > x && mouseX < x + 273.0F && mouseY > y && mouseY < y + 35.0F);
/* 288 */     if (hover2top && mouseButton == 0) {
/* 289 */       this.dragX = mouseX - x;
/* 290 */       this.dragY = mouseY - y;
/* 291 */       this.need2move = true;
/*     */     } else {
/* 293 */       int cateY = 0;
/* 294 */       for (ModuleType category : ModuleType.values()) {
/* 295 */         float strY = y + 55.0F - 20.0F + cateY;
/* 296 */         boolean hover = (mouseX > x + 5.0F && mouseX < x + 65.0F && mouseY > strY && mouseY < strY + 20.0F);
/* 297 */         if (hover && mouseButton == 0) {
/* 298 */           currentCategory = category;
/* 299 */           wheel = 0.0F;
/*     */           break;
/*     */         } 
/* 302 */         cateY += 20;
/*     */       } 
/* 304 */       float startX = x + 80.0F + 2.0F;
/* 305 */       float startY = y + 9.0F + 2.0F + 25.0F;
/* 306 */       int length = 185;
/* 307 */       float moduleY = this.scrollAnimation.getValue();
/* 308 */       for (Module m : Client.instance.getModuleManager().getModules()) {
/* 309 */         if (m.getType() != currentCategory)
/*     */           continue; 
/* 311 */         boolean onToggleButton = (mouseX > startX + 185.0F - 25.0F && mouseX < startX + 185.0F - 5.0F && mouseY > startY + moduleY + 7.0F && mouseY < startY + moduleY + 20.0F && mouseY < startY + 14.0F + 140.0F && mouseY > startY);
/*     */ 
/*     */         
/* 314 */         boolean onModuleRect = (mouseX > startX && mouseX < startX + 185.0F && mouseY > startY + moduleY && mouseY < startY + moduleY + 28.0F && mouseY < startY + 14.0F + 140.0F && mouseY > startY);
/*     */         
/* 316 */         if (onToggleButton && mouseButton == 0)
/* 317 */           m.setEnabled(!m.isEnabled()); 
/* 318 */         if (onModuleRect && mouseButton == 1)
/* 319 */           if (inSetting.contains(m)) {
/* 320 */             inSetting.remove(m);
/* 321 */           } else if (!m.getValues().isEmpty()) {
/* 322 */             inSetting.add(m);
/*     */           }  
/* 324 */         boolean showSetting = inSetting.contains(m);
/* 325 */         int valueSizeY = m.getValues().size() * 20 + 5;
/* 326 */         float valueY = moduleY + 35.0F;
/* 327 */         if (showSetting) {
/* 328 */           SimpleRender.drawRectFloat(startX + 3.0F, startY + moduleY + 24.0F, startX + 185.0F - 3.0F, startY + moduleY + 24.0F + valueSizeY, (new Color(30, 30, 30))
/* 329 */               .getRGB());
/* 330 */           for (Value<?> setting : (Iterable<Value<?>>)m.getValues()) {
/* 331 */             if (setting instanceof Mode) {
/* 332 */               Mode s = (Mode)setting;
/* 333 */               boolean hover = (mouseX > startX + 185.0F - 85.0F && mouseX < startX + 185.0F - 6.0F && mouseY > startY + valueY - 4.0F && mouseY < startY + valueY + 11.0F && mouseY < startY + 14.0F + 140.0F && mouseY > startY);
/*     */ 
/*     */               
/* 336 */               if (hover && (
/* 337 */                 mouseButton == 0 || mouseButton == 1)) {
/* 338 */                 List<String> options = Arrays.asList(s.getModes());
/*     */                 
/* 340 */                 int indexz = options.indexOf(s.getValue());
/* 341 */                 if (mouseButton == 0) {
/* 342 */                   indexz++;
/*     */                 } else {
/* 344 */                   indexz--;
/*     */                 } 
/* 346 */                 if (indexz >= options.size()) {
/* 347 */                   indexz = 0;
/* 348 */                 } else if (indexz < 0) {
/* 349 */                   indexz = options.size() - 1;
/*     */                 } 
/* 351 */                 s.setValue(s.getModes()[indexz]);
/*     */               } 
/*     */             } 
/*     */             
/* 355 */             if (setting instanceof Option) {
/* 356 */               Option<Boolean> s = (Option)setting;
/* 357 */               boolean hover = (mouseX > startX + 185.0F - 18.0F && mouseX < startX + 185.0F - 6.0F && mouseY > startY + valueY - 4.0F && mouseY < startY + valueY + 11.0F && mouseY < startY + 14.0F + 140.0F && mouseY > startY);
/*     */ 
/*     */               
/* 360 */               if (hover && (mouseButton == 0 || mouseButton == 2)) {
/* 361 */                 s.setValue(Boolean.valueOf(!((Boolean)s.getValue()).booleanValue()));
/*     */               }
/*     */             } 
/* 364 */             valueY += 20.0F;
/*     */           } 
/*     */         } 
/* 367 */         moduleY += (showSetting ? (26 + valueSizeY) : 26);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 374 */     super.mouseReleased(mouseX, mouseY, state);
/* 375 */     float startY = y + 9.0F + 2.0F + 28.0F;
/* 376 */     boolean hover2top = (mouseX > x + 1.0F && mouseX < x + 349.0F && mouseY > y + 1.0F && mouseY < y + 9.0F && mouseY < startY + 14.0F + 140.0F && mouseY > startY);
/*     */     
/* 378 */     if (hover2top && state == 0) {
/* 379 */       this.dragX = mouseX - x;
/* 380 */       this.dragY = mouseY - y;
/* 381 */       this.need2move = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 387 */     if (keyCode == 1) {
/* 388 */       this.close = true;
/* 389 */       if (this.mc.entityRenderer.theShaderGroup != null) {
/* 390 */         this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
/* 391 */         this.mc.entityRenderer.theShaderGroup = null;
/*     */       } 
/*     */     } else {
/* 394 */       super.keyTyped(typedChar, keyCode);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\chocolate\GuiClickUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */