/*     */ package awareline.main.ui.gui.clickgui.mode.distance;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.sucks.tenacityColor.ColorUtil;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.font.cfont.CFontRenderer;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.utility.render.gl.flux.GLUtils;
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.LWJGLException;
/*     */ import org.lwjgl.input.Cursor;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ClientClickGui extends GuiScreen {
/*  33 */   public ModuleType currentModuleType = ModuleType.Combat;
/*  34 */   public static final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
/*  35 */   public Module currentModule = Client.instance.getModuleManager().getModulesInType(this.currentModuleType).get(0);
/*  36 */   public static float startX = sr.getScaledWidth() / 2.0F - 290.0F; public static float startY = sr.getScaledHeight() / 2.0F - 175.0F;
/*     */   public int moduleStart;
/*     */   public int valueStart;
/*     */   boolean previousmouse = true;
/*     */   boolean exiting;
/*     */   boolean mouse;
/*     */   public float moveX;
/*     */   public float moveY;
/*     */   boolean bind;
/*     */   public float alpha;
/*     */   float AnimTypeY;
/*  47 */   private float animpos = 190.0F;
/*     */   
/*     */   boolean overCharge;
/*     */   
/*     */   boolean isDone;
/*     */   float yAnim;
/*     */   float yAnimValue;
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  56 */     int FPS = (Minecraft.getDebugFPS() <= 0) ? 1 : Minecraft.getDebugFPS();
/*  57 */     if (this.exiting && this.animpos >= 130.0F) {
/*  58 */       this.mc.displayGuiScreen(null);
/*  59 */       this.mc.setIngameFocus();
/*     */       try {
/*  61 */         Mouse.setNativeCursor(null);
/*  62 */       } catch (Throwable e) {
/*  63 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*  66 */     if (!this.isDone) {
/*  67 */       this.alpha = AnimationUtil.getAnimationState(this.alpha, this.exiting ? 0.0F : 255.0F, 120000.0F / FPS);
/*  68 */       if (this.overCharge) {
/*  69 */         this.animpos = AnimationUtil.moveUD(this.animpos, this.exiting ? 180.0F : 100.0F, 10.0F / FPS, 3.0F / FPS);
/*     */       } else {
/*  71 */         this.animpos = AnimationUtil.moveUD(this.animpos, this.exiting ? 180.0F : 80.0F, 10.0F / FPS, 3.0F / FPS);
/*     */       } 
/*  73 */       if (this.animpos <= 94.0F) {
/*  74 */         this.overCharge = true;
/*     */       }
/*     */     } 
/*  77 */     if (this.animpos == 80.0F) {
/*  78 */       this.isDone = true;
/*     */     }
/*  80 */     GlStateManager.translate(this.width / 2.0F, this.height / 2.0F, 0.0F);
/*  81 */     GlStateManager.scale(this.animpos / 100.0F, this.animpos / 100.0F, 0.0F);
/*  82 */     GlStateManager.translate(-this.width / 2.0F, -this.height / 2.0F, 0.0F);
/*  83 */     if (!this.exiting) {
/*  84 */       hideCursor();
/*     */     }
/*  86 */     float needDownY = 90.0F;
/*  87 */     RenderUtil.drawRect(startX, startY, startX + 550.0F, startY + 350.0F + 90.0F, (new Color(24, 24, 24, Math.max(0, (int)this.alpha - 18))).getRGB());
/*  88 */     RenderUtil.drawRect(startX + 120.0F, startY + 48.0F, startX + 510.0F, startY + 318.0F + 90.0F, (new Color(10, 10, 10, 
/*  89 */           Math.max(0, (int)this.alpha - 210))).getRGB());
/*  90 */     RenderUtil.drawRect(startX + 270.0F, startY + 48.0F, startX + 510.0F, startY + 318.0F + 90.0F, (new Color(10, 10, 10, Math.max(0, (int)this.alpha - 210))).getRGB());
/*     */     
/*  92 */     Client.instance.getClass(); Client.instance.FontLoaders.SF32.drawCenteredString("Awareline", startX + 47.0F + 5.0F, startY + 21.0F, (new Color(255, 255, 255, (int)this.alpha)).getRGB());
/*  93 */     float j = 60.0F;
/*  94 */     float l = 45.0F;
/*  95 */     float k = startY + 25.0F;
/*  96 */     float xx = startX + 10.0F;
/*     */     
/*  98 */     for (int i = 0; i < (ModuleType.values()).length; i++) {
/*  99 */       ModuleType[] iterator = ModuleType.values();
/* 100 */       if (iterator[i] == this.currentModuleType) {
/* 101 */         float typey = k + 12.0F + 60.0F + i * 45.0F;
/* 102 */         this.AnimTypeY = AnimationUtil.animate(typey, this.AnimTypeY, 14.4F / FPS);
/* 103 */         RenderUtil.drawRect(xx + 13.0F, this.AnimTypeY - 22.0F, xx + 1.0F + 15.0F, this.AnimTypeY - 1.0F, Client.instance.getClientColor((int)this.alpha));
/*     */       } 
/* 105 */       boolean movement = Objects.equals(iterator[i].toString(), "Movement");
/* 106 */       float y = k - 10.0F + 60.0F + i * 45.0F;
/* 107 */       float y2 = k + 20.0F + 60.0F + i * 45.0F;
/* 108 */       if (!movement) {
/* 109 */         Client.instance.FontLoaders.SF24.drawString(iterator[i].toString(), xx + 25.0F, k + 56.0F + 45.0F * i, (new Color(255, 255, 255, (int)this.alpha))
/* 110 */             .getRGB());
/*     */       }
/* 112 */       if (Objects.equals(iterator[i].toString(), "Combat")) {
/* 113 */         Client.instance.FontLoaders.NovICON24.drawString("H", xx + 8.0F, k + 57.0F + 45.0F * i, (new Color(255, 255, 255, (int)this.alpha))
/* 114 */             .getRGB());
/* 115 */       } else if (Objects.equals(iterator[i].toString(), "Render")) {
/* 116 */         Client.instance.FontLoaders.NovICON24.drawString("F", xx + 8.0F, k + 57.0F + 45.0F * i, (new Color(255, 255, 255, (int)this.alpha))
/* 117 */             .getRGB());
/* 118 */       } else if (movement) {
/* 119 */         Client.instance.FontLoaders.SF24.drawString("Move", xx + 25.0F, k + 56.0F + 45.0F * i, (new Color(255, 255, 255, (int)this.alpha))
/* 120 */             .getRGB());
/* 121 */         Client.instance.FontLoaders.NovICON24.drawString("I", xx + 8.0F, k + 57.0F + 45.0F * i, (new Color(255, 255, 255, (int)this.alpha))
/* 122 */             .getRGB());
/* 123 */       } else if (Objects.equals(iterator[i].toString(), "Player")) {
/* 124 */         Client.instance.FontLoaders.NovICON24.drawString("C", xx + 8.0F, k + 57.0F + 45.0F * i, (new Color(255, 255, 255, (int)this.alpha))
/* 125 */             .getRGB());
/* 126 */       } else if (Objects.equals(iterator[i].toString(), "World")) {
/* 127 */         Client.instance.FontLoaders.NovICON24.drawString("E", xx + 8.0F, k + 57.0F + 45.0F * i, (new Color(255, 255, 255, (int)this.alpha))
/* 128 */             .getRGB());
/*     */       } 
/*     */       
/* 131 */       if (isCategoryHovered(xx + 8.0F, y, xx + 80.0F, y2, mouseX, mouseY) && 
/* 132 */         Mouse.isButtonDown(0)) {
/* 133 */         this.currentModuleType = iterator[i];
/* 134 */         this.currentModule = Client.instance.getModuleManager().getModulesInType(this.currentModuleType).get(0);
/* 135 */         this.moduleStart = 0;
/* 136 */         this.valueStart = 0;
/*     */       } 
/*     */     } 
/*     */     
/* 140 */     int m = Mouse.getDWheel();
/* 141 */     if (isCategoryHovered(startX + 120.0F, startY + 40.0F, startX + 270.0F, startY + 315.0F, mouseX, mouseY)) {
/* 142 */       if (m < 0 && this.moduleStart < Client.instance.getModuleManager().getModulesInType(this.currentModuleType).size() - 9) {
/* 143 */         this.moduleStart++;
/*     */       }
/* 145 */       if (m > 0 && this.moduleStart > 0) {
/* 146 */         this.moduleStart--;
/*     */       }
/*     */     } 
/* 149 */     if (isCategoryHovered(startX + 270.0F, startY + 50.0F, startX + 510.0F, startY + 315.0F, mouseX, mouseY)) {
/* 150 */       if (m < 0 && this.valueStart < this.currentModule.getValues().size() - 7) {
/* 151 */         this.valueStart++;
/*     */       }
/* 153 */       if (m > 0 && this.valueStart > 0) {
/* 154 */         this.valueStart--;
/*     */       }
/*     */     } 
/* 157 */     float mY = startY + 12.0F; int n;
/* 158 */     for (n = 0; n < Client.instance.getModuleManager().getModulesInType(this.currentModuleType).size(); n++) {
/* 159 */       Module module = Client.instance.getModuleManager().getModulesInType(this.currentModuleType).get(n);
/* 160 */       if ((this.moduleStart - 25 * this.moduleStart) + mY > startY + 250.0F + 90.0F + 15.0F) {
/*     */         break;
/*     */       }
/* 163 */       if ((this.moduleStart - 25 * this.moduleStart) + mY > startY + 250.0F + 90.0F) {
/* 164 */         module.clickAnim = 0.0F;
/*     */       }
/* 166 */       if (n < this.moduleStart) {
/* 167 */         module.clickAnim = 0.0F;
/*     */       }
/* 169 */       this.yAnim = AnimationUtil.moveUD(this.yAnim, ((this.moduleStart - 25 * this.moduleStart) * 100), 1.0F / FPS, 1.0F / FPS);
/* 170 */       module.clickAnim = AnimationUtil.moveUD(module.clickAnim, module.isEnabled() ? 4.0F : 0.0F, 10.0F / FPS, 3.0F / FPS);
/* 171 */       if (isSettingsButtonHovered(startX + 120.0F, this.yAnim / 100.0F + mY + 45.0F, startX + 270.0F, this.yAnim / 100.0F + mY + 45.0F + 25.0F, mouseX, mouseY)) {
/* 172 */         module.hoverOpacity = AnimationUtil.moveUD(module.hoverOpacity, 20.0F, 14.0F / FPS, 6.0F / FPS);
/*     */       } else {
/* 174 */         module.hoverOpacity = AnimationUtil.moveUD(module.hoverOpacity, 0.0F, 8.0F / FPS, 3.0F / FPS);
/*     */       } 
/* 176 */       RenderUtil.prepareScissorBox(0.0F, startY + 57.0F, this.width, startY + 310.0F + 90.0F);
/* 177 */       GL11.glEnable(3089);
/* 178 */       if (isSettingsButtonHovered(0.0F, startY + 57.0F, this.width, startY + 310.0F + 90.0F, mouseX, mouseY)) {
/* 179 */         RenderUtil.drawRect(startX + 120.0F, this.yAnim / 100.0F + mY + 45.0F, startX + 270.0F, this.yAnim / 100.0F + mY + 45.0F + 25.0F, (new Color(1.0F, 1.0F, 1.0F, module.hoverOpacity / 100.0F)).getRGB());
/*     */       }
/* 181 */       if (this.yAnim / 100.0F + mY + 45.0F >= startY + 40.0F && this.yAnim / 100.0F + mY + 45.0F + 25.0F <= startY + 330.0F + 90.0F) {
/* 182 */         GLUtils.startSmooth();
/* 183 */         drawFilledCircle((startX + 140.0F), (this.yAnim / 100.0F + mY + 58.0F), (4.0F - module.clickAnim), (new Color(80, 80, 80, (int)this.alpha)).getRGB(), 5);
/* 184 */         drawFilledCircle((startX + 140.0F), (this.yAnim / 100.0F + mY + 58.0F), module.clickAnim, Client.instance.getClientColor((int)this.alpha), 5);
/* 185 */         GLUtils.endSmooth();
/* 186 */         drawRect(0, 0, 0, 0, 0);
/* 187 */         Client.instance.FontLoaders.SF18.drawString(module.getHUDName(), startX + 150.0F + module.hoverOpacity / 2.0F, this.yAnim / 100.0F + mY + 55.0F, (new Color(255, 255, 255, (int)this.alpha))
/* 188 */             .getRGB());
/*     */       } 
/* 190 */       GL11.glDisable(3089);
/* 191 */       if (isSettingsButtonHovered(0.0F, startY + 57.0F, this.width, startY + 310.0F + 90.0F, mouseX, mouseY)) {
/* 192 */         if (isSettingsButtonHovered(startX + 120.0F, this.yAnim / 100.0F + mY + 45.0F, startX + 270.0F, this.yAnim / 100.0F + mY + 70.0F, mouseX, mouseY)) {
/* 193 */           if (!this.previousmouse && Mouse.isButtonDown(0)) {
/* 194 */             module.setEnabled(!module.isEnabled());
/* 195 */             this.previousmouse = true;
/*     */           } 
/* 197 */           if (!this.previousmouse && Mouse.isButtonDown(1)) {
/* 198 */             this.previousmouse = true;
/*     */           }
/*     */         } 
/*     */         
/* 202 */         if (!Mouse.isButtonDown(0)) {
/* 203 */           this.previousmouse = false;
/*     */         }
/* 205 */         if (isSettingsButtonHovered(startX + 120.0F, this.yAnim / 100.0F + mY + 45.0F, startX + 270.0F, this.yAnim / 100.0F + mY + 45.0F + 25.0F, mouseX, mouseY) && 
/* 206 */           Mouse.isButtonDown(1)) {
/* 207 */           this.currentModule = module;
/* 208 */           this.valueStart = 0;
/*     */         } 
/*     */       } 
/* 211 */       mY += 25.0F;
/*     */     } 
/* 213 */     mY = startY + 12.0F;
/*     */     
/* 215 */     RenderUtil.prepareScissorBox(0.0F, startY + 57.0F, this.width, startY + 270.0F + 90.0F);
/* 216 */     GL11.glEnable(3089);
/* 217 */     if (this.currentModule.getValues().isEmpty()) {
/* 218 */       float f = startX + 410.0F;
/* 219 */       Client.instance.FontLoaders.SF16.drawCenteredStringWithShadow("Not anything change setting :)", f - 15.0F, startY + 12.0F + 50.0F + 3.0F, (new Color(150, 150, 150, (int)this.alpha))
/* 220 */           .getRGB());
/*     */     } else {
/* 222 */       for (n = 0; n < this.currentModule.getValues().size(); n++) {
/* 223 */         this.yAnimValue = AnimationUtil.moveUD(this.yAnimValue, ((this.valueStart - 25 * this.valueStart) * 100), 1.0F / FPS, 1.0F / FPS);
/* 224 */         Value value = this.currentModule.getValues().get(n);
/* 225 */         if (this.yAnimValue / 100.0F + mY > startY + 220.0F + 90.0F && 
/* 226 */           value instanceof Option) {
/* 227 */           ((Option)value).anim = 0.0F;
/*     */         }
/*     */         
/* 230 */         if (n < this.valueStart && 
/* 231 */           value instanceof Option) {
/* 232 */           ((Option)value).anim = 0.0F;
/*     */         }
/*     */ 
/*     */         
/* 236 */         if (this.yAnimValue / 100.0F + mY + 45.0F >= startY + 40.0F && this.yAnimValue / 100.0F + mY + 45.0F + 25.0F <= startY + 290.0F + 90.0F) {
/* 237 */           if (value instanceof Numbers) {
/* 238 */             float f = startX + 410.0F;
/*     */ 
/*     */ 
/*     */             
/* 242 */             double render = (68.0F * (((Number)value.getValue()).floatValue() - ((Numbers)value).getMinimum().floatValue()) / (((Numbers)value).getMaximum().floatValue() - ((Numbers)value).getMinimum().floatValue()));
/* 243 */             ((Numbers)value).smoothAnim = AnimationUtil.moveUD(((Numbers)value).smoothAnim, (float)render);
/* 244 */             RenderUtil.drawFastRoundedRect(f, this.yAnimValue / 100.0F + mY + 54.0F, (int)(f + 75.0D), this.yAnimValue / 100.0F + mY + 57.0F, 1.0F, 
/* 245 */                 isButtonHovered(f, this.yAnimValue / 100.0F + mY + 51.0F, f + 100.0F, this.yAnimValue / 100.0F + mY + 60.0F, mouseX, mouseY) ? (new Color(40, 40, 40, (int)this.alpha)).getRGB() : (new Color(30, 30, 30, (int)this.alpha)).getRGB());
/* 246 */             RenderUtil.drawFastRoundedRect(f, this.yAnimValue / 100.0F + mY + 54.0F, (float)((f + ((Numbers)value).smoothAnim) + 6.5D), this.yAnimValue / 100.0F + mY + 57.0F, 1.0F, Client.instance.getClientColor((int)this.alpha));
/* 247 */             this.font.drawString(value.getBreakName(), startX + 290.0F, this.yAnimValue / 100.0F + mY + 50.0F + 3.0F, (new Color(175, 175, 175, (int)this.alpha))
/* 248 */                 .getRGB());
/* 249 */             this.font.drawString(value.getValue().toString(), f - 2.0F - this.font.getStringWidth(value.getValue().toString()), this.yAnimValue / 100.0F + mY + 50.0F + 3.0F, (new Color(255, 255, 255, (int)this.alpha))
/* 250 */                 .getRGB());
/* 251 */             if (!Mouse.isButtonDown(0)) {
/* 252 */               this.previousmouse = false;
/*     */             }
/* 254 */             if (isButtonHovered(f, this.yAnimValue / 100.0F + mY + 51.0F, f + 100.0F, this.yAnimValue / 100.0F + mY + 60.0F, mouseX, mouseY) && 
/* 255 */               Mouse.isButtonDown(0)) {
/* 256 */               if (!this.previousmouse && Mouse.isButtonDown(0)) {
/* 257 */                 render = ((Numbers)value).getMinimum().doubleValue();
/* 258 */                 double max = ((Numbers)value).getMaximum().doubleValue();
/* 259 */                 double inc = ((Numbers)value).getIncrement().doubleValue();
/* 260 */                 double valAbs = mouseX - f + 4.0D;
/* 261 */                 double perc = valAbs / 68.0D;
/* 262 */                 perc = Math.min(Math.max(0.0D, perc), 1.0D);
/* 263 */                 double valRel = (max - render) * perc;
/* 264 */                 double val = render + valRel;
/* 265 */                 val = Math.round(val * 1.0D / inc) / 1.0D / inc;
/* 266 */                 value.setValue(Double.valueOf(val));
/*     */               } 
/* 268 */               if (!Mouse.isButtonDown(0)) {
/* 269 */                 this.previousmouse = false;
/*     */               }
/*     */             } 
/*     */           } 
/* 273 */           if (value instanceof Option) {
/* 274 */             drawRect(0, 0, 0, 0, 0);
/* 275 */             float f1 = startX + 412.0F;
/* 276 */             float xxx = 65.0F;
/* 277 */             float x2x = 65.0F;
/* 278 */             ((Option)value).anim = AnimationUtil.moveUD(((Option)value).anim, ((Boolean)value.getValue()).booleanValue() ? 5.0F : 0.0F, 18.0F / FPS, 7.0F / FPS);
/*     */             
/* 280 */             this.font.drawString(value.getBreakName(), startX + 290.0F, this.yAnimValue / 100.0F + mY + 50.0F + 3.0F, (new Color(175, 175, 175, (int)this.alpha))
/* 281 */                 .getRGB());
/* 282 */             GLUtils.startSmooth();
/* 283 */             drawFilledCircle((f1 + 65.0F), (this.yAnimValue / 100.0F + mY) + 54.5D, (5.0F - ((Option)value).anim), (new Color(56, 56, 56, (int)this.alpha)).getRGB(), 10);
/* 284 */             drawFilledCircle((f1 + 65.0F), (this.yAnimValue / 100.0F + mY) + 54.5D, ((Option)value).anim, Client.instance.getClientColor((int)this.alpha), 10);
/* 285 */             GLUtils.endSmooth();
/* 286 */             if (isCheckBoxHovered(f1 + 65.0F - 5.0F, this.yAnimValue / 100.0F + mY + 50.0F, f1 + 65.0F + 6.0F, this.yAnimValue / 100.0F + mY + 59.0F, mouseX, mouseY)) {
/* 287 */               if (!this.previousmouse && Mouse.isButtonDown(0)) {
/* 288 */                 this.previousmouse = true;
/* 289 */                 this.mouse = true;
/*     */               } 
/*     */               
/* 292 */               if (this.mouse) {
/* 293 */                 value.setValue(Boolean.valueOf(!((Boolean)value.getValue()).booleanValue()));
/* 294 */                 this.mouse = false;
/*     */               } 
/*     */             } 
/* 297 */             if (!Mouse.isButtonDown(0)) {
/* 298 */               this.previousmouse = false;
/*     */             }
/*     */           } 
/* 301 */           if (value instanceof Mode) {
/* 302 */             drawRect(0, 0, 0, 0, 0);
/* 303 */             float f = startX + 410.0F;
/* 304 */             this.font.drawString(value.getName(), startX + 290.0F, this.yAnimValue / 100.0F + mY + 52.0F + 3.0F, (new Color(175, 175, 175, (int)this.alpha))
/* 305 */                 .getRGB());
/*     */             
/* 307 */             RenderUtil.drawRoundedRect(f, this.yAnimValue / 100.0F + mY + 45.0F, f + 75.0F, this.yAnimValue / 100.0F + mY + 65.0F, 3.0F, (new Color(30, 30, 30, (int)this.alpha)).getRGB());
/*     */             
/* 309 */             RenderUtil.drawRoundRect(f, (this.yAnimValue / 100.0F + mY + 45.0F), (f + 13.0F), (this.yAnimValue / 100.0F + mY + 65.0F), 1, Client.instance
/* 310 */                 .getClientColor((int)this.alpha));
/* 311 */             this.font.drawCenteredString("<", f + 6.0F, this.yAnimValue / 100.0F + mY + 53.0F, (new Color(255, 255, 255, (int)this.alpha)).getRGB());
/*     */             
/* 313 */             RenderUtil.drawRoundRect((f + 75.0F - 13.0F), (this.yAnimValue / 100.0F + mY + 45.0F), (f + 75.0F), (this.yAnimValue / 100.0F + mY + 65.0F), 1, Client.instance
/* 314 */                 .getClientColor((int)this.alpha));
/* 315 */             this.font.drawCenteredString(">", f + 74.0F - 5.5F, this.yAnimValue / 100.0F + mY + 53.0F, (new Color(255, 255, 255, (int)this.alpha)).getRGB());
/* 316 */             this.font.drawCenteredStringWithShadow(((Mode)value).getModeAsString(), f + 38.0F, this.yAnimValue / 100.0F + mY + 53.0F, (new Color(255, 255, 255))
/* 317 */                 .getRGB());
/*     */             
/* 319 */             if (isStringHovered(f, this.yAnimValue / 100.0F + mY + 45.0F, f + 13.0F, this.yAnimValue / 100.0F + mY + 65.0F, mouseX, mouseY) && 
/* 320 */               Mouse.isButtonDown(0) && !this.previousmouse) {
/* 321 */               Mode m2 = (Mode)value;
/* 322 */               if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
/* 323 */                 List<String> options = Arrays.asList(m2.getModes());
/*     */                 
/* 325 */                 int index = options.indexOf(m2.getValue());
/* 326 */                 if (Mouse.isButtonDown(0)) {
/* 327 */                   index++;
/*     */                 } else {
/* 329 */                   index--;
/*     */                 } 
/* 331 */                 if (index >= options.size()) {
/* 332 */                   index = 0;
/* 333 */                 } else if (index < 0) {
/* 334 */                   index = options.size() - 1;
/*     */                 } 
/* 336 */                 m2.setValue(m2.getModes()[index]);
/*     */               } 
/* 338 */               this.previousmouse = true;
/*     */             } 
/*     */ 
/*     */             
/* 342 */             if (isStringHovered(f + 75.0F - 13.0F, this.yAnimValue / 100.0F + mY + 45.0F, f + 75.0F, this.yAnimValue / 100.0F + mY + 65.0F, mouseX, mouseY) && 
/* 343 */               Mouse.isButtonDown(0) && !this.previousmouse) {
/* 344 */               Mode m2 = (Mode)value;
/* 345 */               if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
/* 346 */                 List<String> options = Arrays.asList(m2.getModes());
/*     */                 
/* 348 */                 int index = options.indexOf(m2.getValue());
/* 349 */                 if (Mouse.isButtonDown(0)) {
/* 350 */                   index++;
/*     */                 } else {
/* 352 */                   index--;
/*     */                 } 
/* 354 */                 if (index >= options.size()) {
/* 355 */                   index = 0;
/* 356 */                 } else if (index < 0) {
/* 357 */                   index = options.size() - 1;
/*     */                 } 
/* 359 */                 m2.setValue(m2.getModes()[index]);
/*     */               } 
/* 361 */               this.previousmouse = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 367 */         mY += 25.0F;
/*     */       } 
/*     */     } 
/* 370 */     GL11.glDisable(3089);
/* 371 */     float x = startX + 405.0F;
/* 372 */     float yyy = startY + 240.0F + 90.0F;
/* 373 */     RenderUtil.drawRoundRect((x + 5.0F), (yyy + 45.0F), (x + 75.0F), (yyy + 65.0F), 1, isHovered(x + 2.0F, yyy + 45.0F, x + 78.0F, yyy + 65.0F, mouseX, mouseY) ? (new Color(80, 80, 80, (int)this.alpha)).getRGB() : (new Color(56, 56, 56, (int)this.alpha)).getRGB());
/* 374 */     this.font.drawString(Keyboard.getKeyName(this.currentModule.getKey()), x + 40.0F - (this.font
/* 375 */         .getStringWidth(Keyboard.getKeyName(this.currentModule.getKey())) / 2), yyy + 53.0F + 0.0F, (new Color(255, 255, 255, (int)this.alpha))
/* 376 */         .getRGB());
/* 377 */     this.font.drawString("Bind", startX + 290.0F, yyy + 52.0F + 1.0F, (new Color(170, 170, 170, (int)this.alpha)).getRGB());
/*     */     
/* 379 */     if ((isHovered(startX, startY, startX + 550.0F, startY + 50.0F, mouseX, mouseY) || isHovered(startX, startY + 315.0F, startX + 550.0F, startY + 350.0F, mouseX, mouseY) || isHovered(startX + 530.0F, startY, startX + 550.0F, startY + 350.0F, mouseX, mouseY)) && Mouse.isButtonDown(0)) {
/* 380 */       if (this.moveX == 0.0F && this.moveY == 0.0F) {
/* 381 */         this.moveX = mouseX - startX;
/* 382 */         this.moveY = mouseY - startY;
/*     */       } else {
/* 384 */         startX = mouseX - this.moveX;
/* 385 */         startY = mouseY - this.moveY;
/*     */       } 
/* 387 */       this.previousmouse = true;
/* 388 */     } else if (this.moveX != 0.0F || this.moveY != 0.0F) {
/* 389 */       this.moveX = 0.0F;
/* 390 */       this.moveY = 0.0F;
/*     */     } 
/* 392 */     GL11.glPushMatrix();
/* 393 */     GlStateManager.enableBlend();
/* 394 */     GlStateManager.scale(1.0F, 1.0F, 1.0F);
/* 395 */     GlStateManager.translate(0.0F, 0.0F, 0.0F);
/* 396 */     Client.instance.FontLoaders.FLUXICON17.drawString("p", (mouseX - 1), (mouseY + 2), ColorUtil.transparency(Palette.fade(new Color(255, 255, 255)), this.alpha));
/* 397 */     GlStateManager.scale(1.0F, 1.0F, 1.0F);
/* 398 */     GlStateManager.translate(0.0F, 0.0F, 0.0F);
/* 399 */     GlStateManager.disableBlend();
/* 400 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/* 403 */   final CFontRenderer font = Client.instance.FontLoaders.SF16;
/*     */   Cursor emptyCursor;
/*     */   
/*     */   public void initGui() {
/* 407 */     for (int i = 0; i < this.currentModule.getValues().size(); i++) {
/* 408 */       Value value = this.currentModule.getValues().get(i);
/* 409 */       if (value instanceof Option) {
/* 410 */         ((Option)value).anim = 0.0F;
/*     */       }
/*     */     } 
/* 413 */     for (Module m : Client.instance.getModuleManager().getModules()) {
/* 414 */       m.clickAnim = 0.0F;
/*     */     }
/*     */     
/* 417 */     super.initGui();
/*     */   }
/*     */ 
/*     */   
/*     */   public void keyTyped(char typedChar, int keyCode) {
/* 422 */     if (this.bind) {
/* 423 */       this.currentModule.setKey(keyCode);
/* 424 */       if (keyCode == 1) {
/* 425 */         this.currentModule.setKey(0);
/*     */       }
/* 427 */       this.bind = false;
/* 428 */     } else if (keyCode == 1) {
/* 429 */       this.exiting = true;
/*     */     } 
/* 431 */     if (isKeyComboCtrlX(keyCode)) {
/* 432 */       this.mc.displayGuiScreen(null);
/* 433 */       this.mc.setIngameFocus();
/*     */       try {
/* 435 */         Mouse.setNativeCursor(null);
/* 436 */       } catch (Throwable e) {
/* 437 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
/* 444 */     float x = startX + 405.0F;
/* 445 */     float mY = startY + 30.0F;
/* 446 */     for (int i = 0; i < this.currentModule.getValues().size() && 
/* 447 */       mY <= startY + 350.0F; i++) {
/*     */       
/* 449 */       if (i >= this.valueStart) {
/*     */ 
/*     */         
/* 452 */         Value value = this.currentModule.getValues().get(i);
/* 453 */         if (value instanceof Numbers) {
/* 454 */           mY += 20.0F;
/*     */         }
/* 456 */         if (value instanceof Option)
/*     */         {
/* 458 */           mY += 20.0F;
/*     */         }
/* 460 */         if (value instanceof Mode)
/*     */         {
/* 462 */           mY += 25.0F; } 
/*     */       } 
/*     */     } 
/* 465 */     float needDownY = 90.0F;
/* 466 */     float yyy = startY + 240.0F + 90.0F;
/* 467 */     if (isHovered(x + 5.0F, yyy + 45.0F, x + 75.0F, yyy + 65.0F, mouseX, mouseY)) {
/* 468 */       this.bind = true;
/*     */     }
/* 470 */     super.mouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void hideCursor() {
/* 476 */     if (this.emptyCursor == null) {
/* 477 */       if (Mouse.isCreated()) {
/* 478 */         int min = Cursor.getMinCursorSize();
/* 479 */         IntBuffer tmp = BufferUtils.createIntBuffer(min * min);
/*     */         try {
/* 481 */           this.emptyCursor = new Cursor(min, min, min / 2, min / 2, 1, tmp, null);
/* 482 */         } catch (LWJGLException e) {
/* 483 */           e.printStackTrace();
/*     */         } 
/*     */       } else {
/* 486 */         System.out.println("Could not create empty cursor before Mouse object is created");
/*     */       } 
/*     */     }
/*     */     try {
/* 490 */       Mouse.setNativeCursor(Mouse.isInsideWindow() ? this.emptyCursor : null);
/* 491 */     } catch (LWJGLException e) {
/* 492 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isStringHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
/* 497 */     return (mouseX >= f && mouseX <= g && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public boolean isSettingsButtonHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
/* 501 */     return (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public boolean isButtonHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
/* 505 */     return (mouseX >= f && mouseX <= g && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public boolean isCheckBoxHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
/* 509 */     return (mouseX >= f && mouseX <= g && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public boolean isCategoryHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
/* 513 */     return (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
/* 517 */     return (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 522 */     this.alpha = 0.0F;
/*     */     try {
/* 524 */       Mouse.setNativeCursor(null);
/* 525 */     } catch (Throwable e) {
/* 526 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 531 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\distance\ClientClickGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */