/*     */ package awareline.main.ui.gui.clickgui.mode.fakeexhi;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.mod.implement.visual.ctype.ClickGui;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.font.cfont.CFontRenderer;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.ui.gui.guimainmenu.ColorCreator;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import java.awt.Color;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Clickgui
/*     */   extends GuiScreen
/*     */ {
/*  43 */   private final CFontRenderer titlefont = Client.instance.FontLoaders.Comfortaa20;
/*  44 */   private final CFontRenderer efont = Client.instance.FontLoaders.Comfortaa16;
/*     */ 
/*     */   
/*  47 */   public static ModuleType currentModuleType = ModuleType.Combat;
/*  48 */   public static Module currentModule = !Client.instance.getModuleManager().getModulesInType(currentModuleType).isEmpty() ? Client.instance.getModuleManager().getModulesInType(currentModuleType).get(0) : null;
/*  49 */   public static float startX = 100.0F;
/*  50 */   public static float startY = 100.0F; public int moduleStart; public int valueStart; boolean previousmouse; float x; float mY;
/*     */   boolean a;
/*     */   
/*     */   public Clickgui() {
/*  54 */     this.moduleStart = 0;
/*  55 */     this.valueStart = 0;
/*  56 */     this.previousmouse = true;
/*  57 */     this.a = false;
/*  58 */     this.inmode = null;
/*  59 */     this.nowinmode = null;
/*  60 */     this.inmodule = null;
/*  61 */     this.moveX = 0.0F;
/*  62 */     this.moveY = 0.0F;
/*     */   }
/*     */   String inmode; String nowinmode; String inmodule; boolean mouse; public float moveX; public float moveY;
/*     */   public int getAstolfoRainbow(int v1) {
/*  66 */     double length = 1.6D;
/*  67 */     double delay = Math.ceil(((System.currentTimeMillis() + (long)(v1 * 1.6D)) / 5L));
/*  68 */     float rainbow = ((float)((delay %= 360.0D) / 360.0D) < 0.5D) ? -((float)(delay / 360.0D)) : (float)(delay / 360.0D);
/*  69 */     return Color.getHSBColor(rainbow, 0.6F, 1.0F).getRGB();
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  74 */     if (((Boolean)ClickGui.Blur.getValue()).booleanValue() && 
/*  75 */       OpenGlHelper.shadersSupported && this.mc.thePlayer != null) {
/*  76 */       if (this.mc.entityRenderer.theShaderGroup != null) {
/*  77 */         this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
/*     */       }
/*  79 */       this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  87 */     if (isHovered(startX, startY - 25.0F, startX + 400.0F, startY + 25.0F, mouseX, mouseY) && Mouse.isButtonDown(0)) {
/*  88 */       if (this.moveX == 0.0F && this.moveY == 0.0F) {
/*  89 */         this.moveX = mouseX - startX;
/*  90 */         this.moveY = mouseY - startY;
/*     */       } else {
/*  92 */         startX = mouseX - this.moveX;
/*  93 */         startY = mouseY - this.moveY;
/*     */       } 
/*  95 */       this.previousmouse = true;
/*  96 */     } else if (this.moveX != 0.0F || this.moveY != 0.0F) {
/*  97 */       this.moveX = 0.0F;
/*  98 */       this.moveY = 0.0F;
/*     */     } 
/* 100 */     if (((Boolean)ClickGui.Streamer.getValue()).booleanValue()) {
/* 101 */       drawGradientRect(0, 0, this.width, this.height, (new Color(0, 0, 0, 0)).getRGB(), ColorCreator.createRainbowFromOffset(-6000, 5));
/*     */     }
/* 103 */     SimpleRender.drawBorderedRect(startX + 0.5D, startY + 0.5D, (startX + 450.0F) - 0.5D, (startY + 300.0F) - 0.5D, 0.5D, (new Color(40, 40, 40)).getRGB(), (new Color(60, 60, 60)).getRGB(), true);
/* 104 */     SimpleRender.drawBorderedRect((startX + 2.0F), (startY + 2.0F), (startX + 450.0F - 2.0F), (startY + 300.0F - 2.0F), 0.5D, (new Color(22, 22, 22)).getRGB(), (new Color(60, 60, 60)).getRGB(), true);
/* 105 */     SimpleRender.drawBorderedRect((startX + 2.0F), (startY + 2.0F), (startX + 50.0F - 2.0F), (startY + 300.0F - 2.0F), 0.5D, (new Color(15, 15, 15)).getRGB(), (new Color(60, 60, 60)).getRGB(), true);
/*     */     
/* 107 */     RenderUtil.drawGradientSideways((startX + 3.0F), (startY + 3.0F), (startX + 450.0F) - 2.5D, (startY + 4.0F), getAstolfoRainbow((int)(startX * 20.0F)), getAstolfoRainbow((int)(startX * 60.0F)));
/*     */     
/* 109 */     SimpleRender.drawBorderedRect((startX + 190.0F), (startY + 20.0F), (startX + 440.0F), (startY + 280.0F - 2.0F + 11.0F), 0.5D, (new Color(15, 15, 15)).getRGB(), (new Color(60, 60, 60)).getRGB(), true);
/* 110 */     RenderUtil.drawRect(startX + 195.0F, startY + 20.0F, startX + 222.0F, startY + 25.0F, (new Color(15, 15, 15)).getRGB());
/* 111 */     this.efont.drawString("Values", startX + 197.0F, startY + 20.0F, -1);
/*     */     
/* 113 */     Client.instance.FontLoaders.Comfortaa20.drawString("B", startX + 6.0F, startY + 7.0F, HUDColor());
/* 114 */     Client.instance.FontLoaders.Comfortaa20.drawString("luelun", startX + 6.0F + Client.instance.FontLoaders.Comfortaa20.getStringWidth("S"), startY + 7.0F, -1);
/*     */     
/* 116 */     for (int i = 0; i < (ModuleType.values()).length; i++) {
/* 117 */       if (ModuleType.values()[i] == currentModuleType) {
/* 118 */         if (ModuleType.values()[i].name().equals("Combat")) {
/* 119 */           SimpleRender.drawBorderedRect((startX + 2.0F), (startY + 20.0F), (startX + 50.0F - 2.0F), (startY + 65.0F - 2.0F), 0.5D, (new Color(22, 22, 22)).getRGB(), (new Color(50, 50, 50)).getRGB(), true);
/* 120 */           Gui.drawRect((startX + 47.0F), (startY + 20.0F + 1.0F) - 0.5D, (startX + 50.0F), (startY + 65.0F) - 2.5D, (new Color(22, 22, 22)).getRGB());
/*     */         } 
/* 122 */         if (ModuleType.values()[i].name().equals("Movement")) {
/* 123 */           SimpleRender.drawBorderedRect((startX + 2.0F), (startY + 62.0F), (startX + 50.0F - 2.0F), (startY + 103.0F), 0.5D, (new Color(22, 22, 22)).getRGB(), (new Color(50, 50, 50)).getRGB(), true);
/* 124 */           Gui.drawRect((startX + 47.0F), (startY + 62.0F) + 0.5D, (startX + 50.0F), (startY + 103.0F) - 0.5D, (new Color(22, 22, 22)).getRGB());
/*     */         } 
/* 126 */         if (ModuleType.values()[i].name().equals("Render")) {
/* 127 */           SimpleRender.drawBorderedRect((startX + 2.0F), (startY + 103.0F), (startX + 50.0F - 2.0F), (startY + 143.0F), 0.5D, (new Color(22, 22, 22)).getRGB(), (new Color(50, 50, 50)).getRGB(), true);
/* 128 */           Gui.drawRect((startX + 47.0F), (startY + 103.0F) + 0.5D, (startX + 50.0F), (startY + 143.0F) - 0.5D, (new Color(22, 22, 22)).getRGB());
/*     */         } 
/* 130 */         if (ModuleType.values()[i].name().equals("Player")) {
/* 131 */           SimpleRender.drawBorderedRect((startX + 2.0F), (startY + 143.0F), (startX + 50.0F - 2.0F), (startY + 183.0F - 2.0F), 0.5D, (new Color(22, 22, 22)).getRGB(), (new Color(50, 50, 50)).getRGB(), true);
/* 132 */           Gui.drawRect((startX + 47.0F), (startY + 143.0F) + 0.5D, (startX + 50.0F), (startY + 181.0F) - 0.5D, (new Color(22, 22, 22)).getRGB());
/*     */         } 
/* 134 */         if (ModuleType.values()[i].name().equals("World")) {
/* 135 */           SimpleRender.drawBorderedRect((startX + 2.0F), (startY + 183.0F), (startX + 50.0F - 2.0F), (startY + 223.0F - 2.0F), 0.5D, (new Color(22, 22, 22)).getRGB(), (new Color(50, 50, 50)).getRGB(), true);
/* 136 */           Gui.drawRect((startX + 47.0F), (startY + 183.0F) + 0.5D, (startX + 50.0F), (startY + 221.0F) - 0.5D, (new Color(22, 22, 22)).getRGB());
/*     */         } 
/* 138 */         if (ModuleType.values()[i].name().equals("Misc")) {
/* 139 */           SimpleRender.drawBorderedRect((startX + 2.0F), (startY + 223.0F), (startX + 50.0F - 2.0F), (startY + 263.0F - 2.0F), 0.5D, (new Color(22, 22, 22)).getRGB(), (new Color(50, 50, 50)).getRGB(), true);
/* 140 */           Gui.drawRect((startX + 47.0F), (startY + 223.0F) + 0.5D, (startX + 50.0F), (startY + 261.0F) - 0.5D, (new Color(22, 22, 22)).getRGB());
/*     */         } 
/*     */       } 
/* 143 */       if (ModuleType.values()[i].name().equals("Combat")) {
/* 144 */         Client.instance.FontLoaders.CSGO46.drawString("J", startX + 15.0F, startY + 33.0F, (ModuleType.values()[i] == currentModuleType) ? -1 : (new Color(200, 200, 200)).getRGB());
/*     */       }
/* 146 */       if (ModuleType.values()[i].name().equals("Movement")) {
/* 147 */         Client.instance.FontLoaders.CSGO40.drawString("E", startX + 15.0F, startY + 73.0F, (ModuleType.values()[i] == currentModuleType) ? -1 : (new Color(200, 200, 200)).getRGB());
/*     */       }
/* 149 */       if (ModuleType.values()[i].name().equals("Render")) {
/* 150 */         Client.instance.FontLoaders.CSGO40.drawString("C", startX + 15.0F, startY + 115.0F, (ModuleType.values()[i] == currentModuleType) ? -1 : (new Color(200, 200, 200)).getRGB());
/*     */       }
/* 152 */       if (ModuleType.values()[i].name().equals("Player")) {
/* 153 */         Client.instance.FontLoaders.CSGO36.drawString("F", startX + 15.0F, startY + 155.0F, (ModuleType.values()[i] == currentModuleType) ? -1 : (new Color(200, 200, 200)).getRGB());
/*     */       }
/* 155 */       if (ModuleType.values()[i].name().equals("World")) {
/* 156 */         Client.instance.FontLoaders.CSGO36.drawString("I", startX + 15.0F, startY + 195.0F, (ModuleType.values()[i] == currentModuleType) ? -1 : (new Color(200, 200, 200)).getRGB());
/*     */       }
/* 158 */       if (ModuleType.values()[i].name().equals("Misc")) {
/* 159 */         Client.instance.FontLoaders.CSGO36.drawString("J", startX + 15.0F, startY + 235.0F, (ModuleType.values()[i] == currentModuleType) ? -1 : (new Color(200, 200, 200)).getRGB());
/*     */       }
/*     */       try {
/* 162 */         if (isCategoryHovered(startX, startY + 15.0F + (this.titlefont.getStringHeight() + 3) + (i * 40), startX + 45.0F, startY + 45.0F + (this.titlefont.getStringHeight() + 3) + (i * 40), mouseX, mouseY) && 
/* 163 */           Mouse.isButtonDown(0)) {
/* 164 */           currentModuleType = ModuleType.values()[i];
/* 165 */           currentModule = !Client.instance.getModuleManager().getModulesInType(currentModuleType).isEmpty() ? Client.instance.getModuleManager().getModulesInType(currentModuleType).get(0) : null;
/* 166 */           this.moduleStart = 0;
/*     */         }
/*     */       
/* 169 */       } catch (Exception exception) {}
/*     */     } 
/*     */     
/* 172 */     int m = Mouse.getDWheel();
/* 173 */     if (isCategoryHovered(startX + 60.0F, startY + (this.titlefont.getStringHeight() + 3), startX + 200.0F, startY + (this.titlefont.getStringHeight() + 3) + 320.0F, mouseX, mouseY)) {
/* 174 */       if (m < 0 && this.moduleStart < Client.instance.getModuleManager().getModulesInType(currentModuleType).size() - 1) {
/* 175 */         this.moduleStart++;
/*     */       }
/* 177 */       if (m > 0 && this.moduleStart > 0) {
/* 178 */         this.moduleStart--;
/*     */       }
/*     */     } 
/* 181 */     List<Value> values1 = currentModule.getValues();
/*     */     
/* 183 */     if (isCategoryHovered(startX + 200.0F, startY + (this.titlefont.getStringHeight() + 3), startX + 420.0F, startY + (this.titlefont.getStringHeight() + 3) + 320.0F, mouseX, mouseY)) {
/* 184 */       if (m < 0 && this.valueStart < currentModule.getValues().size() - 1) {
/* 185 */         this.valueStart++;
/*     */       }
/* 187 */       if (m > 0 && this.valueStart > 0) {
/* 188 */         this.valueStart--;
/*     */       }
/*     */     } 
/* 191 */     this.efont.drawString((currentModule == null) ? currentModuleType.toString() : (currentModuleType.toString() + "-" + currentModule.getName()), startX + 420.0F - (Minecraft.getMinecraft()).fontRendererObj.getStringWidth((currentModule == null) ? currentModuleType.toString() : (currentModuleType.toString() + "-" + currentModule.getName())), startY + 10.0F, (new Color(248, 248, 248)).getRGB());
/* 192 */     if (currentModule != null) {
/*     */       
/* 194 */       this.mY = startY + 5.0F; int j;
/* 195 */       for (j = 0; j < Client.instance.getModuleManager().getModulesInType(currentModuleType).size(); j++) {
/* 196 */         Module module = Client.instance.getModuleManager().getModulesInType(currentModuleType).get(j);
/* 197 */         if (this.mY > startY + 290.0F) {
/*     */           break;
/*     */         }
/* 200 */         if (j >= this.moduleStart) {
/* 201 */           SimpleRender.drawBorderedRect((startX + 65.0F), (this.mY + 2.0F), (startX + 175.0F), (this.mY + 20.0F), 0.5D, (new Color(22, 22, 22)).getRGB(), (new Color(60, 60, 60)).getRGB(), true);
/* 202 */           if (!module.isEnabled()) {
/* 203 */             SimpleRender.drawBorderedRect((startX + 161.0F), (this.mY + 4.0F + 5.0F), (startX + 165.0F), (this.mY + 13.0F), 0.5D, (new Color(22, 22, 22)).getRGB(), (new Color(60, 60, 60)).getRGB(), true);
/*     */           } else {
/* 205 */             SimpleRender.drawBorderedRect((startX + 161.0F), (this.mY + 4.0F + 5.0F), (startX + 165.0F), (this.mY + 13.0F), 0.5D, HUDColor(), (new Color(60, 60, 60)).getRGB(), true);
/*     */           } 
/* 207 */           this.efont.drawString(module.getName(), startX + 75.0F, this.mY + 9.0F, module.isEnabled() ? HUDColor() : (new Color(200, 200, 200)).getRGB());
/* 208 */           this.inmodule = module.getName();
/* 209 */           if (isSettingsButtonHovered(startX + 90.0F, this.mY, startX + 100.0F + this.efont.getStringWidth(module.getName()), this.mY + 8.0F + this.efont.getHeight(), mouseX, mouseY)) {
/* 210 */             if (!this.previousmouse && Mouse.isButtonDown(0)) {
/* 211 */               module.setEnabled(!module.isEnabled());
/* 212 */               this.previousmouse = true;
/*     */             } 
/* 214 */             if (!this.previousmouse && Mouse.isButtonDown(1)) {
/* 215 */               this.previousmouse = true;
/*     */             }
/*     */           } 
/* 218 */           if (!Mouse.isButtonDown(0)) {
/* 219 */             this.previousmouse = false;
/*     */           }
/* 221 */           if (isSettingsButtonHovered(startX + 90.0F, this.mY, startX + 100.0F + this.efont.getStringWidth(module.getName()), this.mY + 8.0F + this.efont.getHeight(), mouseX, mouseY) && Mouse.isButtonDown(1)) {
/* 222 */             currentModule = module;
/* 223 */             this.valueStart = 0;
/*     */           } 
/* 225 */           this.mY += 22.0F;
/*     */         } 
/*     */       } 
/* 228 */       this.mY = startY + 30.0F;
/* 229 */       for (j = 0; j < currentModule.getValues().size() && this.mY <= startY + 277.0F; j++) {
/* 230 */         if (j >= this.valueStart) {
/* 231 */           if (this.mY > startY + 277.0F) {
/*     */             break;
/*     */           }
/* 234 */           Value value = values1.get(j);
/* 235 */           if (!((Boolean)ClickGui.Visitable.getValue()).booleanValue() || 
/* 236 */             value.isVisitable()) {
/*     */             
/* 238 */             if (value instanceof Numbers) {
/* 239 */               this.x = startX + 205.0F;
/* 240 */               double render = (68.0F * (((Number)value.getValue()).floatValue() - ((Numbers)value).getMinimum().floatValue()) / (((Numbers)value).getMaximum().floatValue() - ((Numbers)value).getMinimum().floatValue()));
/* 241 */               SimpleRender.drawBorderedRect((this.x - 6.0F - 1.0F), (this.mY + 2.0F + 5.0F - 1.0F), (this.x + 75.0F), (this.mY + 5.0F + 5.0F + 1.0F), 0.5D, (new Color(22, 22, 22)).getRGB(), (new Color(15, 15, 15)).getRGB(), true);
/* 242 */               RenderUtil.drawRect((this.x - 6.0F), (this.mY + 2.0F + 5.0F), this.x + render + 6.5D, (this.mY + 5.0F + 5.0F), HUDColor());
/* 243 */               this.efont.drawString(value.getName(), startX + 200.0F, this.mY, -1);
/* 244 */               this.efont.drawString(String.valueOf(value.getValue()), startX + 290.0F, this.mY + 6.0F, -1);
/* 245 */               if (!Mouse.isButtonDown(0)) {
/* 246 */                 this.previousmouse = false;
/*     */               }
/* 248 */               if (isButtonHovered(this.x, this.mY + 3.0F, this.x + 100.0F, this.mY + 12.0F, mouseX, mouseY) && Mouse.isButtonDown(0)) {
/* 249 */                 if (!this.previousmouse && Mouse.isButtonDown(0)) {
/* 250 */                   render = ((Numbers)value).getMinimum().doubleValue();
/* 251 */                   double max = ((Numbers)value).getMaximum().doubleValue();
/* 252 */                   double inc = ((Numbers)value).getIncrement().doubleValue();
/* 253 */                   double valAbs = (mouseX - this.x + 1.0F);
/* 254 */                   double perc = valAbs / 68.0D;
/* 255 */                   perc = Math.min(Math.max(0.0D, perc), 1.0D);
/* 256 */                   double valRel = (max - render) * perc;
/* 257 */                   double val = render + valRel;
/* 258 */                   val = Math.round(val * 1.0D / inc) / 1.0D / inc;
/* 259 */                   value.setValue(Double.valueOf(val));
/*     */                 } 
/* 261 */                 if (!Mouse.isButtonDown(0)) {
/* 262 */                   this.previousmouse = false;
/*     */                 }
/*     */               } 
/* 265 */               this.mY += 17.0F;
/*     */             } 
/* 267 */             if (value instanceof awareline.main.mod.values.Option) {
/* 268 */               this.x = startX + 120.0F;
/* 269 */               this.efont.drawString(value.getName(), startX + 200.0F, this.mY + 3.0F, -1);
/* 270 */               if (((Boolean)value.getValue()).booleanValue()) {
/* 271 */                 SimpleRender.drawBorderedRect((this.x + 182.0F), ((int)this.mY + 2), (this.x + 188.0F), (this.mY + 8.0F), 0.5D, HUDColor(), (new Color(60, 60, 60)).getRGB(), true);
/*     */               } else {
/* 273 */                 SimpleRender.drawBorderedRect((this.x + 182.0F), ((int)this.mY + 2), (this.x + 188.0F), (this.mY + 8.0F), 0.5D, (new Color(22, 22, 22)).getRGB(), (new Color(60, 60, 60)).getRGB(), true);
/*     */               } 
/* 275 */               if (isCheckBoxHovered(this.x + 80.0F, this.mY, this.x + 216.0F, this.mY + 9.0F, mouseX, mouseY)) {
/* 276 */                 if (!this.previousmouse && Mouse.isButtonDown(0)) {
/* 277 */                   this.previousmouse = true;
/* 278 */                   this.mouse = true;
/*     */                 } 
/* 280 */                 if (this.mouse) {
/* 281 */                   value.setValue(Boolean.valueOf(!((Boolean)value.getValue()).booleanValue()));
/* 282 */                   this.mouse = false;
/*     */                 } 
/*     */               } 
/* 285 */               if (!Mouse.isButtonDown(0)) {
/* 286 */                 this.previousmouse = false;
/*     */               }
/* 288 */               this.mY += 18.0F;
/*     */             } 
/* 290 */             if (value instanceof Mode) {
/* 291 */               this.x = startX + 300.0F;
/* 292 */               this.efont.drawString(value.getName(), startX + 200.0F, this.mY + 1.0F, -1);
/* 293 */               if (isStringHovered(this.x - 15.0F, this.mY, this.x + 100.0F - 25.0F, this.mY + 15.0F, mouseX, mouseY)) {
/* 294 */                 SimpleRender.drawBorderedRect((this.x - 5.0F), (this.mY - 3.0F), (this.x + 80.0F), (this.mY + 10.0F), 0.5D, (new Color(40, 40, 40)).getRGB(), (new Color(60, 60, 60)).getRGB(), true);
/*     */               } else {
/* 296 */                 SimpleRender.drawBorderedRect((this.x - 5.0F), (this.mY - 3.0F), (this.x + 80.0F), (this.mY + 10.0F), 0.5D, (new Color(40, 40, 40)).getRGB(), (new Color(0, 0, 0)).getRGB(), true);
/*     */               } 
/* 298 */               this.efont.drawString(((Mode)value).getModeAsString(), this.x, this.mY + 1.0F, -1);
/* 299 */               this.nowinmode = this.inmodule + value.getName();
/* 300 */               if (isStringHovered(this.x - 15.0F, this.mY, this.x + 75.0F, this.mY + 15.0F, mouseX, mouseY)) {
/* 301 */                 if (Mouse.isButtonDown(0) && !this.previousmouse) {
/* 302 */                   if (this.inmode.equals(this.inmodule + value.getName())) {
/* 303 */                     this.inmode = "heshang";
/*     */                   } else {
/* 305 */                     this.a = true;
/* 306 */                     this.inmode = this.nowinmode;
/*     */                   } 
/* 308 */                   this.previousmouse = true;
/*     */                 } 
/* 310 */                 if (Mouse.isButtonDown(0) && !this.previousmouse) {
/* 311 */                   this.previousmouse = true;
/*     */                 }
/* 313 */                 if (!Mouse.isButtonDown(0)) {
/* 314 */                   this.previousmouse = false;
/*     */                 }
/*     */               } 
/* 317 */               if (this.a) {
/* 318 */                 if (this.inmode.equals(this.inmodule + value.getName())) {
/* 319 */                   this.mc.fontRendererObj.drawString(">", (int)this.x + 70, (int)this.mY - 1, -1);
/* 320 */                   SimpleRender.drawBorderedRect(((int)this.x + 81), ((int)this.mY - 3), ((int)this.x + 70 + 60), ((int)this.mY - 1 + 10 * (((Mode)value).getModes()).length), 0.5D, (new Color(22, 22, 22)).getRGB(), (new Color(60, 60, 60)).getRGB(), true);
/* 321 */                   int next = (((Mode)value).getModes()).length - 1;
/* 322 */                   if (next == 0) {
/* 323 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[0], (startX + 384.0F), (this.mY + 1.0F), ModeColor());
/*     */                   }
/* 325 */                   if (next == 1) {
/* 326 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[0], (startX + 384.0F), (this.mY + 1.0F), ModeColor());
/* 327 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[1], (startX + 384.0F), (this.mY + 1.0F + 10.0F), ModeColor());
/*     */                   } 
/* 329 */                   if (next == 2) {
/* 330 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[0], (startX + 384.0F), (this.mY + 1.0F), ModeColor());
/* 331 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[1], (startX + 384.0F), (this.mY + 1.0F + 10.0F), ModeColor());
/* 332 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[2], (startX + 384.0F), (this.mY + 1.0F + 20.0F), ModeColor());
/*     */                   } 
/* 334 */                   if (next == 3) {
/* 335 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[0], (startX + 384.0F), (this.mY + 1.0F), ModeColor());
/* 336 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[1], (startX + 384.0F), (this.mY + 1.0F + 10.0F), ModeColor());
/* 337 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[2], (startX + 384.0F), (this.mY + 1.0F + 20.0F), ModeColor());
/* 338 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[3], (startX + 384.0F), (this.mY + 1.0F + 30.0F), ModeColor());
/*     */                   } 
/* 340 */                   if (next == 4) {
/* 341 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[0], (startX + 384.0F), (this.mY + 1.0F), ModeColor());
/* 342 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[1], (startX + 384.0F), (this.mY + 1.0F + 10.0F), ModeColor());
/* 343 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[2], (startX + 384.0F), (this.mY + 1.0F + 20.0F), ModeColor());
/* 344 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[3], (startX + 384.0F), (this.mY + 1.0F + 30.0F), ModeColor());
/* 345 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[4], (startX + 384.0F), (this.mY + 1.0F + 40.0F), ModeColor());
/*     */                   } 
/* 347 */                   if (next == 5) {
/* 348 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[0], (startX + 384.0F), (this.mY + 1.0F), ModeColor());
/* 349 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[1], (startX + 384.0F), (this.mY + 1.0F + 10.0F), ModeColor());
/* 350 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[2], (startX + 384.0F), (this.mY + 1.0F + 20.0F), ModeColor());
/* 351 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[3], (startX + 384.0F), (this.mY + 1.0F + 30.0F), ModeColor());
/* 352 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[4], (startX + 384.0F), (this.mY + 1.0F + 40.0F), ModeColor());
/* 353 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[5], (startX + 384.0F), (this.mY + 1.0F + 50.0F), ModeColor());
/*     */                   } 
/* 355 */                   if (next == 6) {
/* 356 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[0], (startX + 384.0F), (this.mY + 1.0F), ModeColor());
/* 357 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[1], (startX + 384.0F), (this.mY + 1.0F + 10.0F), ModeColor());
/* 358 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[2], (startX + 384.0F), (this.mY + 1.0F + 20.0F), ModeColor());
/* 359 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[3], (startX + 384.0F), (this.mY + 1.0F + 30.0F), ModeColor());
/* 360 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[4], (startX + 384.0F), (this.mY + 1.0F + 40.0F), ModeColor());
/* 361 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[5], (startX + 384.0F), (this.mY + 1.0F + 50.0F), ModeColor());
/* 362 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[6], (startX + 384.0F), (this.mY + 1.0F + 60.0F), ModeColor());
/*     */                   } 
/* 364 */                   if (next == 7) {
/* 365 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[0], (startX + 384.0F), (this.mY + 1.0F), ModeColor());
/* 366 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[1], (startX + 384.0F), (this.mY + 1.0F + 10.0F), ModeColor());
/* 367 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[2], (startX + 384.0F), (this.mY + 1.0F + 20.0F), ModeColor());
/* 368 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[3], (startX + 384.0F), (this.mY + 1.0F + 30.0F), ModeColor());
/* 369 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[4], (startX + 384.0F), (this.mY + 1.0F + 40.0F), ModeColor());
/* 370 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[5], (startX + 384.0F), (this.mY + 1.0F + 50.0F), ModeColor());
/* 371 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[6], (startX + 384.0F), (this.mY + 1.0F + 60.0F), ModeColor());
/* 372 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[7], (startX + 384.0F), (this.mY + 1.0F + 70.0F), ModeColor());
/*     */                   } 
/* 374 */                   if (next == 8) {
/* 375 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[0], (startX + 384.0F), (this.mY + 1.0F), ModeColor());
/* 376 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[1], (startX + 384.0F), (this.mY + 1.0F + 10.0F), ModeColor());
/* 377 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[2], (startX + 384.0F), (this.mY + 1.0F + 20.0F), ModeColor());
/* 378 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[3], (startX + 384.0F), (this.mY + 1.0F + 30.0F), ModeColor());
/* 379 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[4], (startX + 384.0F), (this.mY + 1.0F + 40.0F), ModeColor());
/* 380 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[5], (startX + 384.0F), (this.mY + 1.0F + 50.0F), ModeColor());
/* 381 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[6], (startX + 384.0F), (this.mY + 1.0F + 60.0F), ModeColor());
/* 382 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[7], (startX + 384.0F), (this.mY + 1.0F + 70.0F), ModeColor());
/* 383 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[8], (startX + 384.0F), (this.mY + 1.0F + 80.0F), ModeColor());
/*     */                   } 
/* 385 */                   if (next == 9) {
/* 386 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[0], (startX + 384.0F), (this.mY + 1.0F), ModeColor());
/* 387 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[1], (startX + 384.0F), (this.mY + 1.0F + 10.0F), ModeColor());
/* 388 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[2], (startX + 384.0F), (this.mY + 1.0F + 20.0F), ModeColor());
/* 389 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[3], (startX + 384.0F), (this.mY + 1.0F + 30.0F), ModeColor());
/* 390 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[4], (startX + 384.0F), (this.mY + 1.0F + 40.0F), ModeColor());
/* 391 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[5], (startX + 384.0F), (this.mY + 1.0F + 50.0F), ModeColor());
/* 392 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[6], (startX + 384.0F), (this.mY + 1.0F + 60.0F), ModeColor());
/* 393 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[7], (startX + 384.0F), (this.mY + 1.0F + 70.0F), ModeColor());
/* 394 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[8], (startX + 384.0F), (this.mY + 1.0F + 80.0F), ModeColor());
/* 395 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[9], (startX + 384.0F), (this.mY + 1.0F + 90.0F), ModeColor());
/*     */                   } 
/* 397 */                   if (next == 10) {
/* 398 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[0], (startX + 384.0F), (this.mY + 1.0F), ModeColor());
/* 399 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[1], (startX + 384.0F), (this.mY + 1.0F + 10.0F), ModeColor());
/* 400 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[2], (startX + 384.0F), (this.mY + 1.0F + 20.0F), ModeColor());
/* 401 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[3], (startX + 384.0F), (this.mY + 1.0F + 30.0F), ModeColor());
/* 402 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[4], (startX + 384.0F), (this.mY + 1.0F + 40.0F), ModeColor());
/* 403 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[5], (startX + 384.0F), (this.mY + 1.0F + 50.0F), ModeColor());
/* 404 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[6], (startX + 384.0F), (this.mY + 1.0F + 60.0F), ModeColor());
/* 405 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[7], (startX + 384.0F), (this.mY + 1.0F + 70.0F), ModeColor());
/* 406 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[8], (startX + 384.0F), (this.mY + 1.0F + 80.0F), ModeColor());
/* 407 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[9], (startX + 384.0F), (this.mY + 1.0F + 90.0F), ModeColor());
/* 408 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[10], (startX + 384.0F), (this.mY + 1.0F + 100.0F), ModeColor());
/*     */                   } 
/* 410 */                   if (next == 11) {
/* 411 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[0], (startX + 384.0F), (this.mY + 1.0F), ModeColor());
/* 412 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[1], (startX + 384.0F), (this.mY + 1.0F + 10.0F), ModeColor());
/* 413 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[2], (startX + 384.0F), (this.mY + 1.0F + 20.0F), ModeColor());
/* 414 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[3], (startX + 384.0F), (this.mY + 1.0F + 30.0F), ModeColor());
/* 415 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[4], (startX + 384.0F), (this.mY + 1.0F + 40.0F), ModeColor());
/* 416 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[5], (startX + 384.0F), (this.mY + 1.0F + 50.0F), ModeColor());
/* 417 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[6], (startX + 384.0F), (this.mY + 1.0F + 60.0F), ModeColor());
/* 418 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[7], (startX + 384.0F), (this.mY + 1.0F + 70.0F), ModeColor());
/* 419 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[8], (startX + 384.0F), (this.mY + 1.0F + 80.0F), ModeColor());
/* 420 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[9], (startX + 384.0F), (this.mY + 1.0F + 90.0F), ModeColor());
/* 421 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[10], (startX + 384.0F), (this.mY + 1.0F + 100.0F), ModeColor());
/* 422 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[11], (startX + 384.0F), (this.mY + 1.0F + 110.0F), ModeColor());
/*     */                   } 
/* 424 */                   if (next == 12) {
/* 425 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[0], (startX + 384.0F), (this.mY + 1.0F), ModeColor());
/* 426 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[1], (startX + 384.0F), (this.mY + 1.0F + 10.0F), ModeColor());
/* 427 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[2], (startX + 384.0F), (this.mY + 1.0F + 20.0F), ModeColor());
/* 428 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[3], (startX + 384.0F), (this.mY + 1.0F + 30.0F), ModeColor());
/* 429 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[4], (startX + 384.0F), (this.mY + 1.0F + 40.0F), ModeColor());
/* 430 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[5], (startX + 384.0F), (this.mY + 1.0F + 50.0F), ModeColor());
/* 431 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[6], (startX + 384.0F), (this.mY + 1.0F + 60.0F), ModeColor());
/* 432 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[7], (startX + 384.0F), (this.mY + 1.0F + 70.0F), ModeColor());
/* 433 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[8], (startX + 384.0F), (this.mY + 1.0F + 80.0F), ModeColor());
/* 434 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[9], (startX + 384.0F), (this.mY + 1.0F + 90.0F), ModeColor());
/* 435 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[10], (startX + 384.0F), (this.mY + 1.0F + 100.0F), ModeColor());
/* 436 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[11], (startX + 384.0F), (this.mY + 1.0F + 110.0F), ModeColor());
/* 437 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[12], (startX + 384.0F), (this.mY + 1.0F + 120.0F), ModeColor());
/*     */                   } 
/* 439 */                   if (next == 13) {
/* 440 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[0], (startX + 384.0F), (this.mY + 1.0F), ModeColor());
/* 441 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[1], (startX + 384.0F), (this.mY + 1.0F + 10.0F), ModeColor());
/* 442 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[2], (startX + 384.0F), (this.mY + 1.0F + 20.0F), ModeColor());
/* 443 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[3], (startX + 384.0F), (this.mY + 1.0F + 30.0F), ModeColor());
/* 444 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[4], (startX + 384.0F), (this.mY + 1.0F + 40.0F), ModeColor());
/* 445 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[5], (startX + 384.0F), (this.mY + 1.0F + 50.0F), ModeColor());
/* 446 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[6], (startX + 384.0F), (this.mY + 1.0F + 60.0F), ModeColor());
/* 447 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[7], (startX + 384.0F), (this.mY + 1.0F + 70.0F), ModeColor());
/* 448 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[8], (startX + 384.0F), (this.mY + 1.0F + 80.0F), ModeColor());
/* 449 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[9], (startX + 384.0F), (this.mY + 1.0F + 90.0F), ModeColor());
/* 450 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[10], (startX + 384.0F), (this.mY + 1.0F + 100.0F), ModeColor());
/* 451 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[11], (startX + 384.0F), (this.mY + 1.0F + 110.0F), ModeColor());
/* 452 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[10], (startX + 384.0F), (this.mY + 1.0F + 100.0F), ModeColor());
/* 453 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[11], (startX + 384.0F), (this.mY + 1.0F + 110.0F), ModeColor());
/* 454 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[12], (startX + 384.0F), (this.mY + 1.0F + 120.0F), ModeColor());
/* 455 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[13], (startX + 384.0F), (this.mY + 1.0F + 130.0F), ModeColor());
/*     */                   } 
/* 457 */                   if (next == 14) {
/* 458 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[0], (startX + 384.0F), (this.mY + 1.0F), ModeColor());
/* 459 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[1], (startX + 384.0F), (this.mY + 1.0F + 10.0F), ModeColor());
/* 460 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[2], (startX + 384.0F), (this.mY + 1.0F + 20.0F), ModeColor());
/* 461 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[3], (startX + 384.0F), (this.mY + 1.0F + 30.0F), ModeColor());
/* 462 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[4], (startX + 384.0F), (this.mY + 1.0F + 40.0F), ModeColor());
/* 463 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[5], (startX + 384.0F), (this.mY + 1.0F + 50.0F), ModeColor());
/* 464 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[6], (startX + 384.0F), (this.mY + 1.0F + 60.0F), ModeColor());
/* 465 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[7], (startX + 384.0F), (this.mY + 1.0F + 70.0F), ModeColor());
/* 466 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[8], (startX + 384.0F), (this.mY + 1.0F + 80.0F), ModeColor());
/* 467 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[9], (startX + 384.0F), (this.mY + 1.0F + 90.0F), ModeColor());
/* 468 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[10], (startX + 384.0F), (this.mY + 1.0F + 100.0F), ModeColor());
/* 469 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[11], (startX + 384.0F), (this.mY + 1.0F + 110.0F), ModeColor());
/* 470 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[12], (startX + 384.0F), (this.mY + 1.0F + 120.0F), ModeColor());
/* 471 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[13], (startX + 384.0F), (this.mY + 1.0F + 130.0F), ModeColor());
/* 472 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[14], (startX + 384.0F), (this.mY + 1.0F + 140.0F), ModeColor());
/*     */                   } 
/* 474 */                   if (next == 15) {
/* 475 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[0], (startX + 384.0F), (this.mY + 1.0F), ModeColor());
/* 476 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[1], (startX + 384.0F), (this.mY + 1.0F + 10.0F), ModeColor());
/* 477 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[2], (startX + 384.0F), (this.mY + 1.0F + 20.0F), ModeColor());
/* 478 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[3], (startX + 384.0F), (this.mY + 1.0F + 30.0F), ModeColor());
/* 479 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[4], (startX + 384.0F), (this.mY + 1.0F + 40.0F), ModeColor());
/* 480 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[5], (startX + 384.0F), (this.mY + 1.0F + 50.0F), ModeColor());
/* 481 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[6], (startX + 384.0F), (this.mY + 1.0F + 60.0F), ModeColor());
/* 482 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[7], (startX + 384.0F), (this.mY + 1.0F + 70.0F), ModeColor());
/* 483 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[8], (startX + 384.0F), (this.mY + 1.0F + 80.0F), ModeColor());
/* 484 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[9], (startX + 384.0F), (this.mY + 1.0F + 90.0F), ModeColor());
/* 485 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[10], (startX + 384.0F), (this.mY + 1.0F + 100.0F), ModeColor());
/* 486 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[11], (startX + 384.0F), (this.mY + 1.0F + 110.0F), ModeColor());
/* 487 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[12], (startX + 384.0F), (this.mY + 1.0F + 120.0F), ModeColor());
/* 488 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[13], (startX + 384.0F), (this.mY + 1.0F + 130.0F), ModeColor());
/* 489 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[14], (startX + 384.0F), (this.mY + 1.0F + 140.0F), ModeColor());
/* 490 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[15], (startX + 384.0F), (this.mY + 1.0F + 150.0F), ModeColor());
/*     */                   } 
/* 492 */                   if (next == 16) {
/* 493 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[0], (startX + 384.0F), (this.mY + 1.0F), ModeColor());
/* 494 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[1], (startX + 384.0F), (this.mY + 1.0F + 10.0F), ModeColor());
/* 495 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[2], (startX + 384.0F), (this.mY + 1.0F + 20.0F), ModeColor());
/* 496 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[3], (startX + 384.0F), (this.mY + 1.0F + 30.0F), ModeColor());
/* 497 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[4], (startX + 384.0F), (this.mY + 1.0F + 40.0F), ModeColor());
/* 498 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[5], (startX + 384.0F), (this.mY + 1.0F + 50.0F), ModeColor());
/* 499 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[6], (startX + 384.0F), (this.mY + 1.0F + 60.0F), ModeColor());
/* 500 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[7], (startX + 384.0F), (this.mY + 1.0F + 70.0F), ModeColor());
/* 501 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[8], (startX + 384.0F), (this.mY + 1.0F + 80.0F), ModeColor());
/* 502 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[9], (startX + 384.0F), (this.mY + 1.0F + 90.0F), ModeColor());
/* 503 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[10], (startX + 384.0F), (this.mY + 1.0F + 100.0F), ModeColor());
/* 504 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[11], (startX + 384.0F), (this.mY + 1.0F + 110.0F), ModeColor());
/* 505 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[12], (startX + 384.0F), (this.mY + 1.0F + 120.0F), ModeColor());
/* 506 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[13], (startX + 384.0F), (this.mY + 1.0F + 130.0F), ModeColor());
/* 507 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[14], (startX + 384.0F), (this.mY + 1.0F + 140.0F), ModeColor());
/* 508 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[15], (startX + 384.0F), (this.mY + 1.0F + 150.0F), ModeColor());
/* 509 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[16], (startX + 384.0F), (this.mY + 1.0F + 160.0F), ModeColor());
/*     */                   } 
/* 511 */                   if (next == 17) {
/* 512 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[0], (startX + 384.0F), (this.mY + 1.0F), ModeColor());
/* 513 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[1], (startX + 384.0F), (this.mY + 1.0F + 10.0F), ModeColor());
/* 514 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[2], (startX + 384.0F), (this.mY + 1.0F + 20.0F), ModeColor());
/* 515 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[3], (startX + 384.0F), (this.mY + 1.0F + 30.0F), ModeColor());
/* 516 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[4], (startX + 384.0F), (this.mY + 1.0F + 40.0F), ModeColor());
/* 517 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[5], (startX + 384.0F), (this.mY + 1.0F + 50.0F), ModeColor());
/* 518 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[6], (startX + 384.0F), (this.mY + 1.0F + 60.0F), ModeColor());
/* 519 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[7], (startX + 384.0F), (this.mY + 1.0F + 70.0F), ModeColor());
/* 520 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[8], (startX + 384.0F), (this.mY + 1.0F + 80.0F), ModeColor());
/* 521 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[9], (startX + 384.0F), (this.mY + 1.0F + 90.0F), ModeColor());
/* 522 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[10], (startX + 384.0F), (this.mY + 1.0F + 100.0F), ModeColor());
/* 523 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[11], (startX + 384.0F), (this.mY + 1.0F + 110.0F), ModeColor());
/* 524 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[12], (startX + 384.0F), (this.mY + 1.0F + 120.0F), ModeColor());
/* 525 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[13], (startX + 384.0F), (this.mY + 1.0F + 130.0F), ModeColor());
/* 526 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[14], (startX + 384.0F), (this.mY + 1.0F + 140.0F), ModeColor());
/* 527 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[15], (startX + 384.0F), (this.mY + 1.0F + 150.0F), ModeColor());
/* 528 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[16], (startX + 384.0F), (this.mY + 1.0F + 160.0F), ModeColor());
/* 529 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[17], (startX + 384.0F), (this.mY + 1.0F + 170.0F), ModeColor());
/*     */                   } 
/* 531 */                   if (next == 18) {
/* 532 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[0], (startX + 384.0F), (this.mY + 1.0F), ModeColor());
/* 533 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[1], (startX + 384.0F), (this.mY + 1.0F + 10.0F), ModeColor());
/* 534 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[2], (startX + 384.0F), (this.mY + 1.0F + 20.0F), ModeColor());
/* 535 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[3], (startX + 384.0F), (this.mY + 1.0F + 30.0F), ModeColor());
/* 536 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[4], (startX + 384.0F), (this.mY + 1.0F + 40.0F), ModeColor());
/* 537 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[5], (startX + 384.0F), (this.mY + 1.0F + 50.0F), ModeColor());
/* 538 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[6], (startX + 384.0F), (this.mY + 1.0F + 60.0F), ModeColor());
/* 539 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[7], (startX + 384.0F), (this.mY + 1.0F + 70.0F), ModeColor());
/* 540 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[8], (startX + 384.0F), (this.mY + 1.0F + 80.0F), ModeColor());
/* 541 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[9], (startX + 384.0F), (this.mY + 1.0F + 90.0F), ModeColor());
/* 542 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[10], (startX + 384.0F), (this.mY + 1.0F + 100.0F), ModeColor());
/* 543 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[11], (startX + 384.0F), (this.mY + 1.0F + 110.0F), ModeColor());
/* 544 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[12], (startX + 384.0F), (this.mY + 1.0F + 120.0F), ModeColor());
/* 545 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[13], (startX + 384.0F), (this.mY + 1.0F + 130.0F), ModeColor());
/* 546 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[14], (startX + 384.0F), (this.mY + 1.0F + 140.0F), ModeColor());
/* 547 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[15], (startX + 384.0F), (this.mY + 1.0F + 150.0F), ModeColor());
/* 548 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[16], (startX + 384.0F), (this.mY + 1.0F + 160.0F), ModeColor());
/* 549 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[17], (startX + 384.0F), (this.mY + 1.0F + 170.0F), ModeColor());
/* 550 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[18], (startX + 384.0F), (this.mY + 1.0F + 180.0F), ModeColor());
/*     */                   } 
/* 552 */                   if (next == 19) {
/* 553 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[0], (startX + 384.0F), (this.mY + 1.0F), ModeColor());
/* 554 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[1], (startX + 384.0F), (this.mY + 1.0F + 10.0F), ModeColor());
/* 555 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[2], (startX + 384.0F), (this.mY + 1.0F + 20.0F), ModeColor());
/* 556 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[3], (startX + 384.0F), (this.mY + 1.0F + 30.0F), ModeColor());
/* 557 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[4], (startX + 384.0F), (this.mY + 1.0F + 40.0F), ModeColor());
/* 558 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[5], (startX + 384.0F), (this.mY + 1.0F + 50.0F), ModeColor());
/* 559 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[6], (startX + 384.0F), (this.mY + 1.0F + 60.0F), ModeColor());
/* 560 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[7], (startX + 384.0F), (this.mY + 1.0F + 70.0F), ModeColor());
/* 561 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[8], (startX + 384.0F), (this.mY + 1.0F + 80.0F), ModeColor());
/* 562 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[9], (startX + 384.0F), (this.mY + 1.0F + 90.0F), ModeColor());
/* 563 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[10], (startX + 384.0F), (this.mY + 1.0F + 100.0F), ModeColor());
/* 564 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[11], (startX + 384.0F), (this.mY + 1.0F + 110.0F), ModeColor());
/* 565 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[12], (startX + 384.0F), (this.mY + 1.0F + 120.0F), ModeColor());
/* 566 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[13], (startX + 384.0F), (this.mY + 1.0F + 130.0F), ModeColor());
/* 567 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[14], (startX + 384.0F), (this.mY + 1.0F + 140.0F), ModeColor());
/* 568 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[15], (startX + 384.0F), (this.mY + 1.0F + 150.0F), ModeColor());
/* 569 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[16], (startX + 384.0F), (this.mY + 1.0F + 160.0F), ModeColor());
/* 570 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[17], (startX + 384.0F), (this.mY + 1.0F + 170.0F), ModeColor());
/* 571 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[18], (startX + 384.0F), (this.mY + 1.0F + 180.0F), ModeColor());
/* 572 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[19], (startX + 384.0F), (this.mY + 1.0F + 190.0F), ModeColor());
/*     */                   } 
/* 574 */                   if (next == 20) {
/* 575 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[0], (startX + 384.0F), (this.mY + 1.0F), ModeColor());
/* 576 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[1], (startX + 384.0F), (this.mY + 1.0F + 10.0F), ModeColor());
/* 577 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[2], (startX + 384.0F), (this.mY + 1.0F + 20.0F), ModeColor());
/* 578 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[3], (startX + 384.0F), (this.mY + 1.0F + 30.0F), ModeColor());
/* 579 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[4], (startX + 384.0F), (this.mY + 1.0F + 40.0F), ModeColor());
/* 580 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[5], (startX + 384.0F), (this.mY + 1.0F + 50.0F), ModeColor());
/* 581 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[6], (startX + 384.0F), (this.mY + 1.0F + 60.0F), ModeColor());
/* 582 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[7], (startX + 384.0F), (this.mY + 1.0F + 70.0F), ModeColor());
/* 583 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[8], (startX + 384.0F), (this.mY + 1.0F + 80.0F), ModeColor());
/* 584 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[9], (startX + 384.0F), (this.mY + 1.0F + 90.0F), ModeColor());
/* 585 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[10], (startX + 384.0F), (this.mY + 1.0F + 100.0F), ModeColor());
/* 586 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[11], (startX + 384.0F), (this.mY + 1.0F + 110.0F), ModeColor());
/* 587 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[12], (startX + 384.0F), (this.mY + 1.0F + 120.0F), ModeColor());
/* 588 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[13], (startX + 384.0F), (this.mY + 1.0F + 130.0F), ModeColor());
/* 589 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[14], (startX + 384.0F), (this.mY + 1.0F + 140.0F), ModeColor());
/* 590 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[15], (startX + 384.0F), (this.mY + 1.0F + 150.0F), ModeColor());
/* 591 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[16], (startX + 384.0F), (this.mY + 1.0F + 160.0F), ModeColor());
/* 592 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[17], (startX + 384.0F), (this.mY + 1.0F + 170.0F), ModeColor());
/* 593 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[18], (startX + 384.0F), (this.mY + 1.0F + 180.0F), ModeColor());
/* 594 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[19], (startX + 384.0F), (this.mY + 1.0F + 190.0F), ModeColor());
/* 595 */                     this.efont.drawStringWithShadow(((Mode)value).getModes()[20], (startX + 384.0F), (this.mY + 1.0F + 200.0F), ModeColor());
/*     */                   } 
/* 597 */                   if (Mouse.isButtonDown(0)) {
/* 598 */                     if (next >= 0 && isStringHovered(((int)this.x + 81), (int)this.mY, ((int)this.x + 70 + 60), ((int)this.mY + 5), mouseX, mouseY)) {
/* 599 */                       value.setValue(((Mode)value).getModes()[0]);
/* 600 */                       this.a = false;
/*     */                     } 
/* 602 */                     if (next >= 1 && isStringHovered(((int)this.x + 81), ((int)this.mY + 10), ((int)this.x + 70 + 60), ((int)this.mY + 5 + 10), mouseX, mouseY)) {
/* 603 */                       value.setValue(((Mode)value).getModes()[1]);
/* 604 */                       this.a = false;
/*     */                     } 
/* 606 */                     if (next >= 2 && isStringHovered(((int)this.x + 81), ((int)this.mY + 20), ((int)this.x + 70 + 60), ((int)this.mY + 5 + 20), mouseX, mouseY)) {
/* 607 */                       value.setValue(((Mode)value).getModes()[2]);
/* 608 */                       this.a = false;
/*     */                     } 
/* 610 */                     if (next >= 3 && isStringHovered(((int)this.x + 81), ((int)this.mY + 30), ((int)this.x + 70 + 60), ((int)this.mY + 5 + 30), mouseX, mouseY)) {
/* 611 */                       value.setValue(((Mode)value).getModes()[3]);
/* 612 */                       this.a = false;
/*     */                     } 
/* 614 */                     if (next >= 4 && isStringHovered(((int)this.x + 81), ((int)this.mY + 40), ((int)this.x + 70 + 60), ((int)this.mY + 5 + 40), mouseX, mouseY)) {
/* 615 */                       value.setValue(((Mode)value).getModes()[4]);
/* 616 */                       this.a = false;
/*     */                     } 
/* 618 */                     if (next >= 5 && isStringHovered(((int)this.x + 81), ((int)this.mY + 50), ((int)this.x + 70 + 60), ((int)this.mY + 5 + 50), mouseX, mouseY)) {
/* 619 */                       value.setValue(((Mode)value).getModes()[5]);
/* 620 */                       this.a = false;
/*     */                     } 
/* 622 */                     if (next >= 6 && isStringHovered(((int)this.x + 81), ((int)this.mY + 60), ((int)this.x + 70 + 60), ((int)this.mY + 5 + 60), mouseX, mouseY)) {
/* 623 */                       value.setValue(((Mode)value).getModes()[6]);
/* 624 */                       this.a = false;
/*     */                     } 
/* 626 */                     if (next >= 7 && isStringHovered(((int)this.x + 81), ((int)this.mY + 70), ((int)this.x + 70 + 60), ((int)this.mY + 5 + 70), mouseX, mouseY)) {
/* 627 */                       value.setValue(((Mode)value).getModes()[7]);
/* 628 */                       this.a = false;
/*     */                     } 
/* 630 */                     if (next >= 8 && isStringHovered(((int)this.x + 81), ((int)this.mY + 80), ((int)this.x + 70 + 60), ((int)this.mY + 5 + 80), mouseX, mouseY)) {
/* 631 */                       value.setValue(((Mode)value).getModes()[8]);
/* 632 */                       this.a = false;
/*     */                     } 
/* 634 */                     if (next >= 9 && isStringHovered(((int)this.x + 81), ((int)this.mY + 90), ((int)this.x + 70 + 60), ((int)this.mY + 5 + 90), mouseX, mouseY)) {
/* 635 */                       value.setValue(((Mode)value).getModes()[9]);
/* 636 */                       this.a = false;
/*     */                     } 
/* 638 */                     if (next >= 10 && isStringHovered(((int)this.x + 81), ((int)this.mY + 100), ((int)this.x + 70 + 60), ((int)this.mY + 5 + 100), mouseX, mouseY)) {
/* 639 */                       value.setValue(((Mode)value).getModes()[10]);
/* 640 */                       this.a = false;
/*     */                     } 
/* 642 */                     if (next >= 11 && isStringHovered(((int)this.x + 81), ((int)this.mY + 110), ((int)this.x + 70 + 60), ((int)this.mY + 5 + 110), mouseX, mouseY)) {
/* 643 */                       value.setValue(((Mode)value).getModes()[11]);
/* 644 */                       this.a = false;
/*     */                     } 
/* 646 */                     if (next >= 12 && isStringHovered(((int)this.x + 81), ((int)this.mY + 120), ((int)this.x + 70 + 60), ((int)this.mY + 5 + 120), mouseX, mouseY)) {
/* 647 */                       value.setValue(((Mode)value).getModes()[12]);
/* 648 */                       this.a = false;
/*     */                     } 
/* 650 */                     if (next >= 13 && isStringHovered(((int)this.x + 81), ((int)this.mY + 130), ((int)this.x + 70 + 60), ((int)this.mY + 5 + 130), mouseX, mouseY)) {
/* 651 */                       value.setValue(((Mode)value).getModes()[13]);
/* 652 */                       this.a = false;
/*     */                     } 
/* 654 */                     if (next >= 14 && isStringHovered(((int)this.x + 81), ((int)this.mY + 140), ((int)this.x + 70 + 60), ((int)this.mY + 5 + 140), mouseX, mouseY)) {
/* 655 */                       value.setValue(((Mode)value).getModes()[14]);
/* 656 */                       this.a = false;
/*     */                     } 
/* 658 */                     if (next >= 15 && isStringHovered(((int)this.x + 81), ((int)this.mY + 150), ((int)this.x + 70 + 60), ((int)this.mY + 5 + 150), mouseX, mouseY)) {
/* 659 */                       value.setValue(((Mode)value).getModes()[15]);
/* 660 */                       this.a = false;
/*     */                     } 
/* 662 */                     if (next >= 16 && isStringHovered(((int)this.x + 81), ((int)this.mY + 160), ((int)this.x + 70 + 60), ((int)this.mY + 5 + 160), mouseX, mouseY)) {
/* 663 */                       value.setValue(((Mode)value).getModes()[16]);
/* 664 */                       this.a = false;
/*     */                     } 
/* 666 */                     if (next >= 17 && isStringHovered(((int)this.x + 81), ((int)this.mY + 170), ((int)this.x + 70 + 60), ((int)this.mY + 5 + 170), mouseX, mouseY)) {
/* 667 */                       value.setValue(((Mode)value).getModes()[17]);
/* 668 */                       this.a = false;
/*     */                     } 
/* 670 */                     if (next >= 18 && isStringHovered(((int)this.x + 81), ((int)this.mY + 180), ((int)this.x + 70 + 60), ((int)this.mY + 5 + 180), mouseX, mouseY)) {
/* 671 */                       value.setValue(((Mode)value).getModes()[18]);
/* 672 */                       this.a = false;
/*     */                     } 
/* 674 */                     if (next >= 19 && isStringHovered(((int)this.x + 81), ((int)this.mY + 190), ((int)this.x + 70 + 60), ((int)this.mY + 5 + 190), mouseX, mouseY)) {
/* 675 */                       value.setValue(((Mode)value).getModes()[19]);
/* 676 */                       this.a = false;
/*     */                     } 
/* 678 */                     if (next >= 20) {
/* 679 */                       if (isStringHovered(((int)this.x + 81), ((int)this.mY + 200), ((int)this.x + 70 + 60), ((int)this.mY + 5 + 200), mouseX, mouseY)) {
/* 680 */                         value.setValue(((Mode)value).getModes()[20]);
/* 681 */                         this.a = false;
/*     */                       } 
/* 683 */                       this.previousmouse = true;
/*     */                     } 
/*     */                   } 
/*     */                 } else {
/* 687 */                   this.mc.fontRendererObj.drawString("V", (int)this.x + 70, (int)this.mY - 1, -1);
/*     */                 } 
/*     */               } else {
/* 690 */                 this.inmode = "START";
/*     */               } 
/* 692 */               this.mY += 17.0F;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private int ModeColor() {
/* 700 */     return (new Color(225, 225, 225)).getRGB();
/*     */   }
/*     */   
/*     */   private int HUDColor() {
/* 704 */     return (new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue())).getRGB();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {}
/*     */ 
/*     */   
/*     */   public boolean isStringHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
/* 712 */     return (mouseX >= f && mouseX <= g && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public boolean isSettingsButtonHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
/* 716 */     return (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public boolean isButtonHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
/* 720 */     return (mouseX >= f && mouseX <= g && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public boolean isCheckBoxHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
/* 724 */     return (mouseX >= f && mouseX <= g && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public boolean isCategoryHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
/* 728 */     return (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
/* 732 */     return (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 737 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\fakeexhi\Clickgui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */