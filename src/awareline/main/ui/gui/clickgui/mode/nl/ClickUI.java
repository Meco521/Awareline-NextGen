/*     */ package awareline.main.ui.gui.clickgui.mode.nl;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.ctype.ClickGui;
/*     */ import awareline.main.mod.implement.visual.sucks.tenacityColor.ColorUtil;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.animations.TranslateUtil;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.ui.gui.clickgui.mode.distance.Palette;
/*     */ import java.awt.Color;
/*     */ import java.nio.IntBuffer;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.LWJGLException;
/*     */ import org.lwjgl.input.Cursor;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ClickUI
/*     */   extends GuiScreen
/*     */ {
/*  28 */   private static ModuleType currentCategory = ModuleType.Combat;
/*  29 */   private static float startX = ClickGui.startX; private float moveX; private float moveY;
/*  30 */   private static float startY = ClickGui.startY;
/*     */   
/*     */   private boolean previousMouse = true;
/*  33 */   private float currentCateRectY = 22.0F; private float endCateY;
/*  34 */   private final TranslateUtil translate = new TranslateUtil(0.0F, 0.0F);
/*  35 */   private static int wheel = ClickGui.tempWheel; Cursor emptyCursor;
/*     */   boolean useLeft;
/*     */   
/*     */   private void hideCursor() {
/*  39 */     if (this.emptyCursor == null) {
/*  40 */       if (Mouse.isCreated()) {
/*  41 */         int min = Cursor.getMinCursorSize();
/*  42 */         IntBuffer tmp = BufferUtils.createIntBuffer(min * min);
/*     */         try {
/*  44 */           this.emptyCursor = new Cursor(min, min, min / 2, min / 2, 1, tmp, null);
/*  45 */         } catch (LWJGLException e) {
/*  46 */           e.printStackTrace();
/*     */         } 
/*     */       } else {
/*  49 */         System.out.println("Could not create empty cursor before Mouse object is created");
/*     */       } 
/*     */     }
/*     */     try {
/*  53 */       Mouse.setNativeCursor(Mouse.isInsideWindow() ? this.emptyCursor : null);
/*  54 */     } catch (LWJGLException e) {
/*  55 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  61 */     super.initGui();
/*  62 */     currentCategory = ClickGui.currentModuleType;
/*  63 */     this.translate.setX(0.0F);
/*  64 */     this.translate.setY(0.0F);
/*     */   }
/*     */   
/*     */   public float processFPS(float fps, float defF, float defV) {
/*  68 */     return defV / fps / defF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  76 */     hideCursor();
/*  77 */     if (RenderUtil.isHovered(startX, startY, startX + 520.0F, startY + 50.0F, mouseX, mouseY) && Mouse.isButtonDown(0)) {
/*  78 */       if (this.moveX == 0.0F && this.moveY == 0.0F) {
/*  79 */         this.moveX = mouseX - startX;
/*  80 */         this.moveY = mouseY - startY;
/*     */       } else {
/*  82 */         startX = mouseX - this.moveX;
/*  83 */         startY = mouseY - this.moveY;
/*     */       } 
/*  85 */       this.previousMouse = true;
/*  86 */     } else if (this.moveX != 0.0F || this.moveY != 0.0F) {
/*  87 */       this.moveX = 0.0F;
/*  88 */       this.moveY = 0.0F;
/*     */     } 
/*  90 */     double sizes = 1.0D;
/*     */     
/*  92 */     GL11.glScaled(sizes, sizes, sizes);
/*     */ 
/*     */ 
/*     */     
/*  96 */     RenderUtil.drawRect(startX + 10.0F, startY, startX + 520.0F, startY + 460.0F - 180.0F + 100.0F, (new Color(0, 0, 0, 200))
/*  97 */         .getRGB());
/*  98 */     RenderUtil.drawRect(startX + 10.0F, startY, startX + 520.0F, startY + 460.0F - 180.0F + 100.0F, (new Color(1, 20, 40, 180))
/*  99 */         .getRGB());
/*     */     
/* 101 */     RenderUtil.drawRect(startX + 120.0F, startY + 40.0F, startX + 520.0F, startY + 460.0F - 180.0F + 100.0F, (new Color(8, 8, 13))
/* 102 */         .getRGB());
/*     */ 
/*     */ 
/*     */     
/* 106 */     RenderUtil.drawRect(startX + 120.0F, startY, startX + 520.0F, startY + 38.0F, (new Color(8, 8, 13))
/* 107 */         .getRGB());
/*     */ 
/*     */ 
/*     */     
/* 111 */     RenderUtil.drawRect(startX + 120.0F, startY + 38.0F, startX + 520.0F, startY + 40.0F, (new Color(5, 26, 38))
/* 112 */         .getRGB());
/* 113 */     RenderUtil.drawRect(startX + 118.0F, startY, startX + 120.0F, startY + 460.0F - 180.0F + 100.0F, (new Color(5, 26, 38))
/* 114 */         .getRGB());
/*     */ 
/*     */ 
/*     */     
/* 118 */     Client.instance.getClass(); Client.instance.getClass(); Client.instance.FontLoaders.bold30.drawString("Awareline".toUpperCase(), startX + 65.0F - (Client.instance.FontLoaders.bold30
/* 119 */         .getStringWidth("Awareline".toUpperCase()) / 2) - 0.5F, startY + 17.0F - 0.5F, Color.CYAN
/* 120 */         .getRGB());
/* 121 */     Client.instance.getClass(); Client.instance.getClass(); Client.instance.FontLoaders.bold30.drawString("Awareline".toUpperCase(), startX + 65.0F - (Client.instance.FontLoaders.bold30
/* 122 */         .getStringWidth("Awareline".toUpperCase()) / 2), startY + 17.0F, (new Color(255, 255, 255))
/* 123 */         .getRGB());
/*     */     
/* 125 */     RenderUtil.startGlScissor((int)startX, (int)startY + 40 + 15, 600, 324);
/* 126 */     int cateY = 0;
/*     */     
/* 128 */     String oldPref = "";
/* 129 */     this.currentCateRectY = AnimationUtil.moveUD(this.currentCateRectY, this.endCateY);
/*     */     
/* 131 */     RenderUtil.drawFastRoundedRect(startX + 8.0F + 10.0F, startY + 18.0F + this.currentCateRectY - 2.0F, startX + 110.0F, startY + 36.0F + this.currentCateRectY - 3.0F, 4.0F, (new Color(8, 50, 74))
/*     */ 
/*     */         
/* 134 */         .getRGB());
/*     */ 
/*     */ 
/*     */     
/* 138 */     for (int i = 0; i < (ModuleType.values()).length; i++) {
/* 139 */       ModuleType category = ModuleType.values()[i];
/* 140 */       cateY += 20;
/*     */       
/* 142 */       if (!category.name().split("_")[0].equals(oldPref)) {
/* 143 */         cateY = (int)(cateY + 5.5F);
/* 144 */         oldPref = category.name().split("_")[0];
/*     */       } 
/* 146 */       if (category == currentCategory) {
/* 147 */         this.endCateY = cateY;
/*     */       }
/* 149 */       RenderUtil.drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/* 150 */       Client.instance.getClass(); Client.instance.FontLoaders.bold18.drawString(category.name().split("_")[0], startX + 9.0F + 60.0F - (Client.instance.FontLoaders.Comfortaa30
/* 151 */           .getStringWidth("Awareline") / 2), startY + 23.0F + cateY, (new Color(255, 255, 255))
/*     */           
/* 153 */           .getRGB());
/* 154 */       Client.instance.getClass(); float strX = startX + 70.0F - (Client.instance.FontLoaders.Comfortaa30.getStringWidth("Awareline") / 2);
/* 155 */       float y = startY + 21.0F + cateY;
/* 156 */       int Blue = (new Color(3, 168, 245)).getRGB();
/* 157 */       if (category.name().equals("Combat")) {
/* 158 */         Client.instance.FontLoaders.icon24.drawString("1", strX - 15.0F, y, Blue);
/*     */       }
/* 160 */       if (category.name().equals("Movement")) {
/* 161 */         Client.instance.FontLoaders.icon26.drawString("5", strX - 15.0F, y, Blue);
/*     */       }
/* 163 */       if (category.name().equals("Render")) {
/* 164 */         Client.instance.FontLoaders.guiicons22.drawString("F", strX - 15.0F, y, Blue);
/*     */       }
/* 166 */       if (category.name().equals("Player")) {
/* 167 */         Client.instance.FontLoaders.guiicons28.drawString("C", strX - 15.0F, y, Blue);
/*     */       }
/* 169 */       if (category.name().equals("World")) {
/* 170 */         Client.instance.FontLoaders.guiicons30.drawString("E", strX - 15.0F, y, Blue);
/*     */       }
/* 172 */       if (category.name().equals("Misc")) {
/* 173 */         Client.instance.FontLoaders.guiicons28.drawString("G", strX - 15.0F, y, Blue);
/*     */       }
/* 175 */       if (category.name().equals("Globals")) {
/* 176 */         Client.instance.FontLoaders.guiicons28.drawString("J", strX - 15.0F, y, -1);
/*     */       }
/* 178 */       if (category.name().equals("Script")) {
/* 179 */         Client.instance.FontLoaders.novoicons25.drawString("G", strX - 15.0F, y, -1);
/*     */       }
/* 181 */       if (RenderUtil.isHovered(startX + 8.0F, startY + 18.0F + cateY, startX + 110.0F, startY + 36.0F + cateY, mouseX, mouseY) && 
/* 182 */         Mouse.isButtonDown(0) && !this.previousMouse) {
/* 183 */         currentCategory = category;
/* 184 */         wheel = 0;
/* 185 */         this.previousMouse = true;
/*     */       } 
/*     */     } 
/*     */     
/* 189 */     float rightY = this.translate.getY(), leftY = rightY;
/*     */     
/* 191 */     int size = 0;
/*     */     
/* 193 */     for (Module m : Client.instance.getModuleManager().getModules()) {
/* 194 */       if (m.getModuleType() != currentCategory)
/*     */         continue; 
/* 196 */       for (Value v : m.getValues()) {
/* 197 */         if (((Boolean)ClickGui.Visitable.getValue()).booleanValue() && 
/* 198 */           !v.isVisitable())
/*     */           continue; 
/* 200 */         size++;
/*     */       } 
/* 202 */       float preY = ((size + 1) * 20);
/* 203 */       size = 0;
/*     */ 
/*     */ 
/*     */       
/* 207 */       this.useLeft = (leftY + preY <= rightY + preY);
/*     */       
/* 209 */       if (leftY + preY > rightY + preY) {
/* 210 */         int k = 0;
/* 211 */         for (Value v : m.getValues()) {
/* 212 */           if (((Boolean)ClickGui.Visitable.getValue()).booleanValue() && 
/* 213 */             !v.isVisitable())
/*     */             continue; 
/* 215 */           size += v.isDownopen() ? (v.listModes().size() + 1) : 1;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 220 */         if (rightY > startY + 185.0F + 100.0F) {
/*     */           break;
/*     */         }
/* 223 */         RenderUtil.drawFastRoundedRect((int)(startX + 325.0F), startY + 50.0F + rightY, (int)(startX + 510.0F), startY + 62.0F + rightY + ((size + 1) * 20) + k, 3.0F, (new Color(3, 13, 26)).getRGB());
/* 224 */         RenderUtil.drawRect(startX + 328.0F, startY + 50.0F + rightY + 14.0F, startX + 507.0F, startY + 52.0F + rightY + 14.0F, (new Color(5, 26, 38)).getRGB());
/*     */ 
/*     */ 
/*     */         
/* 228 */         Client.instance.FontLoaders.regular19.drawString(m.getHUDName(), startX + 329.0F, startY + 51.0F + rightY + 2.0F, -1);
/*     */         
/* 230 */         RenderUtil.drawRect(startX + 484.0F, startY + 58.0F + rightY + 13.0F - 18.0F, startX + 501.0F, startY + 64.0F + rightY + 15.0F - 18.0F, (new Color(3, 23, 46)).getRGB());
/* 231 */         RenderUtil.drawCircle((startX + 485.0F), (startY + 61.0F + rightY + 14.0F - 18.0F), 4.0D, new Color(3, 23, 46));
/* 232 */         RenderUtil.drawCircle((startX + 500.0F), (startY + 61.0F + rightY + 14.0F - 18.0F), 4.0D, new Color(3, 23, 46));
/* 233 */         RenderUtil.drawRect(startX + 485.0F, startY + 58.0F + rightY + 14.0F - 18.0F, startX + 500.0F, startY + 64.0F + rightY + 14.0F - 18.0F, m.isEnabled() ? (new Color(0, 102, 148)).getRGB() : (new Color(3, 6, 14)).getRGB());
/* 234 */         RenderUtil.drawCircle((startX + 485.0F), (startY + 61.0F + rightY + 14.0F - 18.0F), 3.0D, m.isEnabled() ? new Color(0, 102, 148) : new Color(3, 6, 14));
/* 235 */         RenderUtil.drawCircle((startX + 500.0F), (startY + 61.0F + rightY + 14.0F - 18.0F), 3.0D, m.isEnabled() ? new Color(0, 102, 148) : new Color(3, 6, 14));
/*     */         
/* 237 */         RenderUtil.drawCircle((startX + 487.0F + (m.isEnabled() ? 11 : false)), (startY + 61.0F + rightY + 14.0F - 18.0F), 5.0D, m.isEnabled() ? new Color(3, 168, 245) : new Color(120, 139, 151));
/*     */         
/* 239 */         if (RenderUtil.isHovered(startX + 485.0F, startY + 58.0F + rightY + 14.0F - 18.0F, startX + 500.0F, startY + 64.0F + rightY + 14.0F - 18.0F, mouseX, mouseY) && Mouse.isButtonDown(0) && !this.previousMouse) {
/* 240 */           m.setEnabled(!m.isEnabled());
/* 241 */           this.previousMouse = true;
/*     */         } 
/*     */         
/* 244 */         int j = 5;
/* 245 */         for (Value value : m.getValues()) {
/* 246 */           if (((Boolean)ClickGui.Visitable.getValue()).booleanValue() && 
/* 247 */             !value.isVisitable())
/*     */             continue; 
/* 249 */           if (value instanceof awareline.main.mod.values.Option) {
/* 250 */             Client.instance.FontLoaders.regular16.drawString(value.getBreakName(), startX + 330.0F, startY + 55.0F + rightY + 14.0F + j, (new Color(156, 178, 191)).getRGB());
/*     */             
/* 252 */             RenderUtil.drawRect(startX + 484.0F, startY + 58.0F + rightY + 11.0F + j, startX + 501.0F, startY + 64.0F + rightY + 13.0F + j, (new Color(3, 23, 46)).getRGB());
/* 253 */             RenderUtil.drawCircle((startX + 485.0F), (startY + 61.0F + rightY + 12.0F + j), 4.0D, new Color(3, 23, 46));
/* 254 */             RenderUtil.drawCircle((startX + 500.0F), (startY + 61.0F + rightY + 12.0F + j), 4.0D, new Color(3, 23, 46));
/* 255 */             RenderUtil.drawRect(startX + 485.0F, startY + 58.0F + rightY + 12.0F + j, startX + 500.0F, startY + 64.0F + rightY + 12.0F + j, ((Boolean)value.getValue()).booleanValue() ? (new Color(0, 102, 148)).getRGB() : (new Color(3, 6, 14)).getRGB());
/* 256 */             RenderUtil.drawCircle((startX + 485.0F), (startY + 61.0F + rightY + 12.0F + j), 3.0D, ((Boolean)value.getValue()).booleanValue() ? new Color(0, 102, 148) : new Color(3, 6, 14));
/* 257 */             RenderUtil.drawCircle((startX + 500.0F), (startY + 61.0F + rightY + 12.0F + j), 3.0D, ((Boolean)value.getValue()).booleanValue() ? new Color(0, 102, 148) : new Color(3, 6, 14));
/*     */             
/* 259 */             RenderUtil.drawCircle((startX + 487.0F + (((Boolean)value.getValue()).booleanValue() ? 11 : false)), (startY + 61.0F + rightY + 12.0F + j), 5.0D, ((Boolean)value.getValue()).booleanValue() ? new Color(3, 168, 245) : new Color(120, 139, 151));
/*     */             
/* 261 */             if (RenderUtil.isHovered(startX + 485.0F, startY + 58.0F + rightY + 10.0F + j, startX + 500.0F, startY + 64.0F + rightY + 14.0F + j, mouseX, mouseY) && Mouse.isButtonDown(0) && !this.previousMouse) {
/* 262 */               value.setValue(Boolean.valueOf(!((Boolean)value.getValue()).booleanValue()));
/* 263 */               this.mc.thePlayer.playSound("random.click", 1.0F, 1.0F);
/* 264 */               this.previousMouse = true;
/*     */             } 
/* 266 */             j += 20;
/*     */           } 
/*     */           
/* 269 */           if (value instanceof Numbers) {
/* 270 */             Client.instance.FontLoaders.regular18.drawString(value.getBreakName(), startX + 330.0F, startY + 55.0F + rightY + 14.0F + j, (new Color(156, 178, 191)).getRGB());
/*     */             
/* 272 */             RenderUtil.drawRect(startX + 415.0F, startY + 54.0F + rightY + 19.0F + j, startX + 480.0F, startY + 54.0F + rightY + 21.0F + j, (new Color(3, 23, 46)).getRGB());
/*     */             
/* 274 */             Numbers<Number> s = (Numbers<Number>)value;
/* 275 */             double state = ((Double)value.getValue()).doubleValue();
/* 276 */             double min = s.getMinimum().doubleValue();
/* 277 */             double max = s.getMaximum().doubleValue();
/* 278 */             double render = 68.0D * (state - min) / (max - min);
/* 279 */             RenderUtil.drawRect((startX + 415.0F), (startY + 54.0F + rightY + 19.0F + j), (startX + 415.0F) + render, (startY + 54.0F + rightY + 21.0F + j), (new Color(0, 102, 148)).getRGB());
/*     */             
/* 281 */             RenderUtil.drawCircle((startX + 416.0F) + render, (startY + 54.0F + rightY + 20.0F + j), 3.0D, new Color(61, 133, 224));
/*     */             
/* 283 */             Client.instance.FontLoaders.regular14.drawCenteredString(String.valueOf(value.getValue()), startX + 498.0F, startY + 55.0F + rightY + 14.0F + j, -1);
/*     */             
/* 285 */             if (RenderUtil.isHovered(startX + 415.0F, startY + 54.0F + rightY + 19.0F + j, startX + 483.0F, startY + 54.0F + rightY + 21.0F + j, mouseX, mouseY) && Mouse.isButtonDown(0) && !this.previousMouse) {
/* 286 */               render = ((Double)s.getMinimum()).doubleValue();
/* 287 */               max = ((Double)s.getMaximum()).doubleValue();
/* 288 */               double inc = s.getIncrement().doubleValue();
/*     */               
/* 290 */               double valAbs = (mouseX - startX + 415.0F);
/* 291 */               double perc = valAbs / 68.0D;
/* 292 */               perc = Math.min(Math.max(0.0D, perc), 1.0D);
/* 293 */               double valRel = (max - render) * perc;
/* 294 */               double val = render + valRel;
/* 295 */               val = Math.round(val * 1.0D / inc) / 1.0D / inc;
/* 296 */               double num = val;
/* 297 */               value.setValue(Double.valueOf(num));
/*     */             } 
/* 299 */             j += 20;
/*     */           } 
/*     */ 
/*     */           
/* 303 */           if (!value.listModes().isEmpty()) {
/* 304 */             Client.instance.FontLoaders.regular18.drawString(value.getBreakName(), startX + 330.0F, startY + 55.0F + rightY + 14.0F + j, (new Color(156, 178, 191)).getRGB());
/*     */             
/* 306 */             RenderUtil.drawFastRoundedRect((int)(startX + 439.0F), startY + 57.0F + rightY + 9.0F + j, (int)(startX + 502.0F), startY + 65.0F + rightY + 15.0F + j, 3.0F, (new Color(3, 23, 46)).getRGB());
/* 307 */             RenderUtil.drawFastRoundedRect((int)(startX + 440.0F), startY + 58.0F + rightY + 9.0F + j, (int)(startX + 501.0F), startY + 64.0F + rightY + 15.0F + j, 3.0F, (new Color(3, 5, 13)).getRGB());
/*     */             
/* 309 */             Client.instance.FontLoaders.regular16.drawCenteredString(value.isDownopen() ? "...." : (String)value.getValue(), startX + 470.0F, startY + 57.0F + rightY + 10.0F + j + 3.0F, (new Color(200, 200, 200))
/*     */                 
/* 311 */                 .getRGB());
/*     */             
/* 313 */             String msg = (String)value.getValue();
/*     */             
/* 315 */             if (RenderUtil.isHovered(startX + 440.0F, startY + 58.0F + rightY + 9.0F + j, startX + 501.0F, startY + 64.0F + rightY + 15.0F + j, mouseX, mouseY) && !this.previousMouse && Mouse.isButtonDown(0)) {
/* 316 */               value.setDownopen(!value.isDownopen());
/* 317 */               this.previousMouse = true;
/* 318 */               this.mc.thePlayer.playSound("random.click", 1.0F, 1.0F);
/*     */             } 
/* 320 */             if (value.isDownopen()) {
/* 321 */               RenderUtil.drawFastRoundedRect((int)(startX + 439.0F), startY + 65.0F + rightY + 17.0F + j, (int)(startX + 502.0F), startY + 65.0F + rightY + 17.0F + j + (12 * value.listModes().size()), 3.0F, (new Color(3, 23, 46)).getRGB());
/* 322 */               RenderUtil.drawFastRoundedRect((int)(startX + 440.0F), startY + 66.0F + rightY + 17.0F + j, (int)(startX + 501.0F), startY + 65.0F + rightY + 16.0F + j + (12 * value.listModes().size()), 3.0F, (new Color(3, 5, 13)).getRGB());
/*     */               
/* 324 */               int downY = 0;
/* 325 */               for (int v = 0; v < value.listModes().size(); v++) {
/* 326 */                 Client.instance.FontLoaders.regular14.drawCenteredString(value.getModeAt(v), startX + 470.0F, startY + 60.0F + rightY + 24.0F + j + downY + 2.0F, 
/*     */                     
/* 328 */                     msg.equals(value.getModeAt(v)) ? (new Color(57, 124, 210)).getRGB() : (new Color(114, 132, 144)).getRGB());
/* 329 */                 if (RenderUtil.isHovered(startX + 440.0F, startY + 66.0F + rightY + 17.0F + j + downY, startX + 501.0F, startY + 66.0F + rightY + 17.0F + j + downY + 12.0F, mouseX, mouseY) && Mouse.isButtonDown(0) && !this.previousMouse) {
/* 330 */                   if (value instanceof Mode) {
/* 331 */                     Mode vs = (Mode)value;
/* 332 */                     vs.setCurrentMode(v);
/* 333 */                     value.setDownopen(!value.isDownopen());
/*     */                   } 
/* 335 */                   this.mc.thePlayer.playSound("random.click", 1.0F, 1.0F);
/* 336 */                   this.previousMouse = true;
/*     */                 } 
/* 338 */                 downY += 12;
/*     */               } 
/*     */             } 
/* 341 */             j += value.isDownopen() ? (20 + 12 * value.listModes().size()) : 20;
/*     */           } 
/*     */         } 
/*     */         
/* 345 */         rightY += (16 + (size + 1) * 20); continue;
/*     */       } 
/* 347 */       GL11.glScaled(sizes, sizes, sizes);
/*     */       
/* 349 */       int listY = 0;
/* 350 */       for (Value v : m.getValues()) {
/* 351 */         if (((Boolean)ClickGui.Visitable.getValue()).booleanValue() && 
/* 352 */           !v.isVisitable())
/*     */           continue; 
/* 354 */         size += v.isDownopen() ? (v.listModes().size() + 1) : 1;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 359 */       if (leftY > startY + 185.0F + 100.0F) {
/*     */         break;
/*     */       }
/* 362 */       RenderUtil.drawFastRoundedRect((int)(startX + 130.0F), startY + 50.0F + leftY, (int)(startX + 315.0F), startY + 62.0F + leftY + ((size + 1) * 20) + listY, 3.0F, (new Color(3, 13, 26)).getRGB());
/* 363 */       RenderUtil.drawRect(startX + 133.0F, startY + 50.0F + leftY + 14.0F, startX + 312.0F, startY + 52.0F + leftY + 14.0F, (new Color(5, 26, 38)).getRGB());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 368 */       Client.instance.FontLoaders.regular19.drawString(m.getHUDName(), startX + 134.0F, startY + 51.0F + leftY + 2.0F, -1);
/*     */       
/* 370 */       RenderUtil.drawRect(startX + 289.0F, startY + 58.0F + leftY + 13.0F - 18.0F, startX + 306.0F, startY + 64.0F + leftY + 15.0F - 18.0F, (new Color(3, 23, 46)).getRGB());
/* 371 */       RenderUtil.drawCircle((startX + 290.0F), (startY + 61.0F + leftY + 14.0F - 18.0F), 4.0D, new Color(3, 23, 46));
/* 372 */       RenderUtil.drawCircle((startX + 305.0F), (startY + 61.0F + leftY + 14.0F - 18.0F), 4.0D, new Color(3, 23, 46));
/* 373 */       RenderUtil.drawRect(startX + 290.0F, startY + 58.0F + leftY + 14.0F - 18.0F, startX + 305.0F, startY + 64.0F + leftY + 14.0F - 18.0F, m.isEnabled() ? (new Color(0, 102, 148)).getRGB() : (new Color(3, 6, 14)).getRGB());
/* 374 */       RenderUtil.drawCircle((startX + 290.0F), (startY + 61.0F + leftY + 14.0F - 18.0F), 3.0D, m.isEnabled() ? new Color(0, 102, 148) : new Color(3, 6, 14));
/* 375 */       RenderUtil.drawCircle((startX + 305.0F), (startY + 61.0F + leftY + 14.0F - 18.0F), 3.0D, m.isEnabled() ? new Color(0, 102, 148) : new Color(3, 6, 14));
/*     */       
/* 377 */       RenderUtil.drawCircle((startX + 292.0F + (m.isEnabled() ? 11 : false)), (startY + 61.0F - 18.0F + leftY + 14.0F), 5.0D, m.isEnabled() ? new Color(3, 168, 245) : new Color(120, 139, 151));
/*     */       
/* 379 */       if (RenderUtil.isHovered(startX + 290.0F, startY + 58.0F + leftY + 14.0F - 18.0F, startX + 305.0F, startY + 64.0F + leftY + 14.0F - 18.0F, mouseX, mouseY) && Mouse.isButtonDown(0) && !this.previousMouse) {
/* 380 */         m.setEnabled(!m.isEnabled());
/* 381 */         this.previousMouse = true;
/*     */       } 
/*     */       
/* 384 */       int valueY = 5;
/* 385 */       for (Value value : m.getValues()) {
/* 386 */         if (((Boolean)ClickGui.Visitable.getValue()).booleanValue() && 
/* 387 */           !value.isVisitable())
/*     */           continue; 
/* 389 */         if (value instanceof awareline.main.mod.values.Option) {
/* 390 */           Client.instance.FontLoaders.regular18.drawString(value.getBreakName(), startX + 135.0F, startY + 55.0F + leftY + 14.0F + valueY, (new Color(156, 178, 191)).getRGB());
/*     */           
/* 392 */           RenderUtil.drawRect(startX + 289.0F, startY + 58.0F + leftY + 11.0F + valueY, startX + 306.0F, startY + 64.0F + leftY + 13.0F + valueY, (new Color(3, 23, 46)).getRGB());
/* 393 */           RenderUtil.drawCircle((startX + 290.0F), (startY + 61.0F + leftY + 12.0F + valueY), 4.0D, new Color(3, 23, 46));
/* 394 */           RenderUtil.drawCircle((startX + 305.0F), (startY + 61.0F + leftY + 12.0F + valueY), 4.0D, new Color(3, 23, 46));
/*     */           
/* 396 */           RenderUtil.drawRect(startX + 290.0F, startY + 58.0F + leftY + 12.0F + valueY, startX + 305.0F, startY + 64.0F + leftY + 12.0F + valueY, ((Boolean)value.getValue()).booleanValue() ? (new Color(0, 102, 148)).getRGB() : (new Color(3, 6, 14)).getRGB());
/*     */           
/* 398 */           RenderUtil.drawCircle((startX + 290.0F), (startY + 61.0F + leftY + 12.0F + valueY), 3.0D, ((Boolean)value.getValue()).booleanValue() ? new Color(0, 102, 148) : new Color(3, 6, 14));
/* 399 */           RenderUtil.drawCircle((startX + 305.0F), (startY + 61.0F + leftY + 12.0F + valueY), 3.0D, ((Boolean)value.getValue()).booleanValue() ? new Color(0, 102, 148) : new Color(3, 6, 14));
/*     */           
/* 401 */           RenderUtil.drawCircle((startX + 292.0F + (((Boolean)value.getValue()).booleanValue() ? 11 : false)), (startY + 61.0F + leftY + 12.0F + valueY), 5.0D, ((Boolean)value.getValue()).booleanValue() ? new Color(3, 168, 245) : new Color(120, 139, 151));
/*     */           
/* 403 */           if (RenderUtil.isHovered(startX + 290.0F, startY + 58.0F + leftY + 10.0F + valueY, startX + 305.0F, startY + 64.0F + leftY + 14.0F + valueY, mouseX, mouseY) && Mouse.isButtonDown(0) && !this.previousMouse) {
/* 404 */             value.setValue(Boolean.valueOf(!((Boolean)value.getValue()).booleanValue()));
/* 405 */             this.mc.thePlayer.playSound("random.click", 1.0F, 1.0F);
/* 406 */             this.previousMouse = true;
/*     */           } 
/* 408 */           valueY += 20;
/*     */         } 
/*     */         
/* 411 */         if (value instanceof Numbers) {
/* 412 */           Client.instance.FontLoaders.regular18.drawString(value.getBreakName(), startX + 135.0F, startY + 55.0F + leftY + 14.0F + valueY, (new Color(156, 178, 191)).getRGB());
/*     */           
/* 414 */           RenderUtil.drawRect(startX + 415.0F - 195.0F, startY + 54.0F + leftY + 19.0F + valueY, startX + 480.0F - 195.0F, startY + 54.0F + leftY + 21.0F + valueY, (new Color(3, 23, 46)).getRGB());
/* 415 */           Numbers<Number> s = (Numbers<Number>)value;
/*     */           
/* 417 */           RenderUtil.drawRect(startX + 415.0F - 195.0F, startY + 54.0F + leftY + 19.0F + valueY, startX + 480.0F - 195.0F, startY + 54.0F + leftY + 21.0F + valueY, (new Color(3, 23, 46)).getRGB());
/*     */           
/* 419 */           double render = 68.0D * (((Number)s.getValue()).doubleValue() - ((Double)s.getMinimum()).doubleValue()) / (((Double)s.getMaximum()).doubleValue() - ((Double)s.getMinimum()).doubleValue());
/*     */           
/* 421 */           RenderUtil.drawRect((startX + 415.0F - 195.0F), (startY + 54.0F + leftY + 19.0F + valueY), (startX + 415.0F) + render - 195.0D, (startY + 54.0F + leftY + 21.0F + valueY), (new Color(0, 102, 148)).getRGB());
/*     */           
/* 423 */           RenderUtil.drawCircle((startX + 416.0F) + render - 195.0D, (startY + 54.0F + leftY + 20.0F + valueY), 3.0D, new Color(61, 133, 224));
/*     */           
/* 425 */           Client.instance.FontLoaders.regular14.drawCenteredString(String.valueOf(value.getValue()), startX + 498.0F - 195.0F, startY + 55.0F + leftY + 14.0F + valueY, -1);
/*     */           
/* 427 */           if (RenderUtil.isHovered(startX + 415.0F - 195.0F, startY + 54.0F + leftY + 19.0F + valueY, startX + 483.0F - 195.0F, startY + 54.0F + leftY + 21.0F + valueY, mouseX, mouseY) && Mouse.isButtonDown(0) && !this.previousMouse) {
/* 428 */             render = ((Double)s.getMinimum()).doubleValue();
/* 429 */             double max = ((Double)s.getMaximum()).doubleValue();
/* 430 */             double inc = ((Double)s.getIncrement()).doubleValue();
/*     */             
/* 432 */             double valAbs = (mouseX - startX + 415.0F - 195.0F);
/* 433 */             double perc = valAbs / 68.0D;
/* 434 */             perc = Math.min(Math.max(0.0D, perc), 1.0D);
/* 435 */             double valRel = (max - render) * perc;
/* 436 */             double val = render + valRel;
/* 437 */             val = Math.round(val * 1.0D / inc) / 1.0D / inc;
/* 438 */             double num = val;
/* 439 */             value.setValue(Double.valueOf(num));
/*     */           } 
/*     */           
/* 442 */           valueY += 20;
/*     */         } 
/*     */         
/* 445 */         if (value instanceof Mode) {
/*     */           
/*     */           try {
/* 448 */             if (!value.listModes().isEmpty()) {
/* 449 */               Client.instance.FontLoaders.regular18.drawString(value.getBreakName(), startX + 135.0F, startY + 55.0F + leftY + 14.0F + valueY, (new Color(156, 178, 191)).getRGB());
/*     */               
/* 451 */               RenderUtil.drawFastRoundedRect((int)(startX + 439.0F - 195.0F), startY + 57.0F + leftY + 9.0F + valueY, (int)(startX + 502.0F - 195.0F), startY + 65.0F + leftY + 15.0F + valueY, 3.0F, (new Color(3, 23, 46)).getRGB());
/* 452 */               RenderUtil.drawFastRoundedRect((int)(startX + 440.0F - 195.0F), startY + 58.0F + leftY + 9.0F + valueY, (int)(startX + 501.0F - 195.0F), startY + 64.0F + leftY + 15.0F + valueY, 3.0F, (new Color(3, 5, 13)).getRGB());
/*     */               
/* 454 */               Client.instance.FontLoaders.regular16.drawCenteredString(value.isDownopen() ? "...." : (String)((Mode)value).getValue(), startX + 470.0F - 195.0F, startY + 57.0F + leftY + 10.0F + valueY + 3.0F, (new Color(200, 200, 200))
/*     */                   
/* 456 */                   .getRGB());
/* 457 */               String msg = (String)((Mode)value).getValue();
/*     */               
/* 459 */               if (RenderUtil.isHovered(startX + 440.0F - 195.0F, startY + 58.0F + leftY + 9.0F + valueY, startX + 501.0F - 195.0F, startY + 64.0F + leftY + 15.0F + valueY, mouseX, mouseY) && !this.previousMouse && Mouse.isButtonDown(0)) {
/* 460 */                 value.setDownopen(!value.isDownopen());
/* 461 */                 this.previousMouse = true;
/* 462 */                 this.mc.thePlayer.playSound("random.click", 1.0F, 1.0F);
/*     */               } 
/*     */               
/* 465 */               if (value.isDownopen()) {
/* 466 */                 RenderUtil.drawFastRoundedRect((int)(startX + 439.0F - 195.0F), startY + 65.0F + leftY + 17.0F + valueY, (int)(startX + 502.0F - 195.0F), startY + 65.0F + leftY + 17.0F + valueY + (12 * value.listModes().size()), 3.0F, (new Color(3, 23, 46)).getRGB());
/* 467 */                 RenderUtil.drawFastRoundedRect((int)(startX + 440.0F - 195.0F), startY + 66.0F + leftY + 17.0F + valueY, (int)(startX + 501.0F - 195.0F), startY + 65.0F + leftY + 16.0F + valueY + (12 * value.listModes().size()), 3.0F, (new Color(3, 5, 13)).getRGB());
/* 468 */                 int downY = 0;
/* 469 */                 for (int v = 0; v < value.listModes().size(); v++) {
/* 470 */                   Client.instance.FontLoaders.regular14.drawCenteredString(value.getModeAt(v), startX + 470.0F - 195.0F, startY + 60.0F + leftY + 24.0F + valueY + downY + 2.0F, 
/*     */                       
/* 472 */                       value.getModeAt(v).equals(msg) ? (new Color(57, 124, 210)).getRGB() : (new Color(114, 132, 144)).getRGB());
/* 473 */                   if (RenderUtil.isHovered(startX + 440.0F - 195.0F, startY + 66.0F + leftY + 17.0F + valueY + downY, startX + 501.0F - 195.0F, startY + 66.0F + leftY + 17.0F + valueY + downY + 12.0F, mouseX, mouseY) && Mouse.isButtonDown(0) && !this.previousMouse) {
/* 474 */                     Mode vs = (Mode)value;
/* 475 */                     vs.setCurrentMode(v);
/* 476 */                     value.setDownopen(!value.isDownopen());
/* 477 */                     this.mc.thePlayer.playSound("random.click", 1.0F, 1.0F);
/* 478 */                     this.previousMouse = true;
/*     */                   } 
/* 480 */                   downY += 12;
/*     */                 } 
/*     */               } 
/* 483 */               valueY += value.isDownopen() ? (20 + 12 * value.listModes().size()) : 20;
/*     */             } 
/* 485 */           } catch (Exception exception) {}
/*     */         }
/*     */       } 
/*     */       
/* 489 */       leftY += (16 + (size + 1) * 20);
/*     */     } 
/*     */     
/* 492 */     RenderUtil.stopGlScissor();
/*     */     
/* 494 */     int real = Mouse.getDWheel();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 499 */     float moduleHeight = Math.max(leftY - this.translate.getY(), rightY - this.translate.getY());
/*     */     
/* 501 */     if (Mouse.hasWheel() && mouseX > startX + 120.0F && mouseY > startY && mouseX < startX + 520.0F && mouseY < startY + 40.0F + 420.0F) {
/* 502 */       if (real > 0 && wheel < 0) {
/* 503 */         for (int j = 0; j < 5 && 
/* 504 */           wheel < 0; j++)
/*     */         {
/* 506 */           wheel += ((Boolean)ClickGui.fpsMouseWheel.get()).booleanValue() ? 15 : 5;
/*     */         }
/*     */       } else {
/* 509 */         for (int j = 0; j < 5 && 
/* 510 */           real < 0 && moduleHeight > 240.0F && Math.abs(wheel) < moduleHeight - 236.0F; j++)
/*     */         {
/* 512 */           wheel -= ((Boolean)ClickGui.fpsMouseWheel.get()).booleanValue() ? 15 : 5;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 519 */     this.translate.interpolate(0.0F, wheel, 0.25F);
/*     */     
/* 521 */     if (!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1)) {
/* 522 */       this.previousMouse = false;
/*     */     }
/* 524 */     GL11.glPushMatrix();
/* 525 */     GlStateManager.enableBlend();
/* 526 */     GlStateManager.scale(1.0F, 1.0F, 1.0F);
/* 527 */     GlStateManager.translate(0.0F, 0.0F, 0.0F);
/* 528 */     Client.instance.FontLoaders.FLUXICON17.drawStringWithShadow("p", (mouseX - 1), (mouseY + 2), ColorUtil.transparency(Palette.fade(new Color(255, 255, 255)), 255.0D));
/* 529 */     GlStateManager.scale(1.0F, 1.0F, 1.0F);
/* 530 */     GlStateManager.translate(0.0F, 0.0F, 0.0F);
/* 531 */     GlStateManager.disableBlend();
/* 532 */     GL11.glPopMatrix();
/* 533 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 538 */     return false;
/*     */   }
/*     */   
/*     */   public static void setX(int state) {
/* 542 */     startX = state;
/*     */   }
/*     */   
/*     */   public static void setY(int state) {
/* 546 */     startY = state;
/*     */   }
/*     */   
/*     */   public static void setCategory(ModuleType state) {
/* 550 */     currentCategory = state;
/*     */   }
/*     */   
/*     */   public static void setWheel(int state) {
/* 554 */     wheel = state;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 559 */     super.onGuiClosed();
/*     */     try {
/* 561 */       Mouse.setNativeCursor(null);
/* 562 */     } catch (Throwable throwable) {}
/*     */     
/* 564 */     ClickGui.currentModuleType = currentCategory;
/* 565 */     ClickGui.startX = startX;
/* 566 */     ClickGui.startY = startY;
/* 567 */     ClickGui.tempWheel = wheel;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\nl\ClickUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */