/*     */ package awareline.main.ui.gui.clickgui.mode.powerx;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.ctype.ClickGui;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.font.cfont.CFontRenderer;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PXClickGui
/*     */   extends GuiScreen
/*     */ {
/*  26 */   public static ModuleType currentModuleType = ModuleType.Combat;
/*  27 */   public static Module currentModule = !Client.instance.getModuleManager().getModulesInType(currentModuleType).isEmpty() ? Client.instance
/*  28 */     .getModuleManager().getModulesInType(currentModuleType).get(0) : null;
/*     */   
/*  30 */   public static float startX = 100.0F; public static float startY = 100.0F; public int moduleStart;
/*     */   public int valueStart;
/*     */   boolean previousmouse = true;
/*     */   boolean mouse;
/*     */   public float moveX;
/*     */   public float moveY;
/*  36 */   final CFontRenderer font = Client.instance.FontLoaders.Comfortaa18;
/*  37 */   final CFontRenderer typefont = Client.instance.FontLoaders.Comfortaa24;
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  42 */     if (isHovered(startX, startY - 25.0F, startX + 400.0F, startY + 25.0F, mouseX, mouseY) && Mouse.isButtonDown(0)) {
/*  43 */       if (this.moveX == 0.0F && this.moveY == 0.0F) {
/*  44 */         this.moveX = mouseX - startX;
/*  45 */         this.moveY = mouseY - startY;
/*     */       } else {
/*  47 */         startX = mouseX - this.moveX;
/*  48 */         startY = mouseY - this.moveY;
/*     */       } 
/*  50 */       this.previousmouse = true;
/*  51 */     } else if (this.moveX != 0.0F || this.moveY != 0.0F) {
/*  52 */       this.moveX = 0.0F;
/*  53 */       this.moveY = 0.0F;
/*     */     } 
/*     */     
/*  56 */     RenderUtil.drawRoundedRect(startX - 1.0F, startY - 1.0F, startX + 370.0F + 1.0F, startY + 230.0F + 1.0F, 3.0F, (new Color(11, 84, 150, 150)).getRGB());
/*  57 */     RenderUtil.drawRoundedRect(startX, startY, startX + 370.0F, startY + 230.0F, 2.0F, (new Color(27, 27, 27)).getRGB());
/*  58 */     RenderUtil.drawRect(startX, startY + 20.5D, (startX + 370.0F), (startY + 20.0F), (new Color(21, 21, 21, 140)).getRGB());
/*  59 */     RenderUtil.drawRect(startX, startY + 23.0F, startX + 370.0F, startY + 20.0F, (new Color(23, 23, 23, 130)).getRGB());
/*     */     
/*  61 */     RenderUtil.drawRect((startX + 80.0F), startY + 20.3D, (startX + 180.0F), (startY + 230.0F), (new Color(27, 27, 27)).getRGB());
/*     */     
/*  63 */     RenderUtil.drawRect((startX + 80.0F), startY + 20.3D, startX + 80.8D, (startY + 230.0F), (new Color(12, 12, 12, 200)).getRGB());
/*  64 */     RenderUtil.drawRect(startX + 80.8D, startY + 20.3D, startX + 81.6D, (startY + 230.0F), (new Color(13, 13, 13, 200)).getRGB());
/*  65 */     RenderUtil.drawRect(startX + 81.6D, startY + 20.3D, startX + 82.4D, (startY + 230.0F), (new Color(14, 14, 14, 200)).getRGB());
/*  66 */     RenderUtil.drawRect(startX + 82.4D, startY + 20.3D, startX + 83.2D, (startY + 230.0F), (new Color(15, 15, 15, 200)).getRGB());
/*  67 */     RenderUtil.drawRect(startX + 83.2D, startY + 20.3D, (startX + 84.0F), (startY + 230.0F), (new Color(16, 16, 16, 200)).getRGB());
/*  68 */     RenderUtil.drawRect((startX + 84.0F), startY + 20.3D, startX + 84.8D, (startY + 230.0F), (new Color(17, 17, 17, 200)).getRGB());
/*  69 */     RenderUtil.drawRect(startX + 84.8D, startY + 20.3D, startX + 85.8D, (startY + 230.0F), (new Color(18, 18, 18, 190)).getRGB());
/*  70 */     RenderUtil.drawRect(startX + 85.8D, startY + 20.3D, (startX + 90.0F), (startY + 230.0F), (new Color(20, 20, 20, 110)).getRGB());
/*  71 */     RenderUtil.drawRect((startX + 185.0F), startY + 20.3D, (startX + 182.0F), (startY + 230.0F), (new Color(21, 21, 21, 110)).getRGB());
/*  72 */     RenderUtil.drawRect((startX + 182.0F), startY + 20.3D, (startX + 180.0F), (startY + 230.0F), (new Color(21, 21, 21, 100)).getRGB());
/*  73 */     Client.instance.getClass(); Client.instance.FontLoaders.Comfortaa22.drawString("Awareline", startX + 5.0F, startY + 5.0F, (new Color(152, 152, 152)).getRGB());
/*     */ 
/*     */     
/*  76 */     for (int i = 0; i < (ModuleType.values()).length; i++) {
/*  77 */       if (ModuleType.values()[i] != currentModuleType)
/*     */       {
/*  79 */         RenderUtil.circle(startX + 30.0F, startY + 50.0F + (i * 20), 6.0F, (new Color(255, 255, 255, 0)).getRGB());
/*     */       }
/*     */       try {
/*  82 */         if (isCategoryHovered(startX + 15.0F, startY + 40.0F + (i * 20), startX + 60.0F, startY + 50.0F + (i * 20), mouseX, mouseY) && 
/*  83 */           Mouse.isButtonDown(0)) {
/*  84 */           currentModuleType = ModuleType.values()[i];
/*     */           
/*  86 */           currentModule = !Client.instance.getModuleManager().getModulesInType(currentModuleType).isEmpty() ? Client.instance.getModuleManager().getModulesInType(currentModuleType).get(0) : null;
/*     */           
/*  88 */           this.moduleStart = 0;
/*     */         } 
/*  90 */       } catch (Exception exception) {}
/*     */     } 
/*     */     
/*  93 */     int m = Mouse.getDWheel();
/*     */     
/*  95 */     if (isCategoryHovered(startX + 60.0F, startY, startX + 200.0F, startY + 320.0F, mouseX, mouseY)) {
/*  96 */       if (m < 0 && this.moduleStart < Client.instance.getModuleManager().getModulesInType(currentModuleType).size() - 1) {
/*  97 */         this.moduleStart++;
/*     */       }
/*  99 */       if (m > 0 && this.moduleStart > 0) {
/* 100 */         this.moduleStart--;
/*     */       }
/*     */     } 
/*     */     
/* 104 */     if (isCategoryHovered(startX + 200.0F, startY, startX + 420.0F, startY + 320.0F, mouseX, mouseY)) {
/* 105 */       if (m < 0 && this.valueStart < currentModule.getValues().size() - 1) {
/* 106 */         this.valueStart++;
/*     */       }
/* 108 */       if (m > 0 && this.valueStart > 0) {
/* 109 */         this.valueStart--;
/*     */       }
/*     */     } 
/* 112 */     Client.instance.FontLoaders.Comfortaa20.drawString(currentModuleType
/* 113 */         .toString(), startX + 85.0F, startY + 5.0F, (new Color(152, 152, 152))
/* 114 */         .getRGB());
/* 115 */     Client.instance.FontLoaders.Comfortaa20.drawString((currentModule == null) ? currentModuleType
/* 116 */         .toString() : ("Module:" + currentModule
/* 117 */         .getName()), startX + 185.0F, startY + 5.0F, (new Color(152, 152, 152))
/* 118 */         .getRGB());
/* 119 */     if (currentModule != null) {
/* 120 */       float mY = startY + 16.0F; int j;
/* 121 */       for (j = 0; j < Client.instance.getModuleManager().getModulesInType(currentModuleType).size(); j++) {
/* 122 */         Module module = Client.instance.getModuleManager().getModulesInType(currentModuleType).get(j);
/* 123 */         if (mY > startY + 200.0F)
/*     */           break; 
/* 125 */         if (j >= this.moduleStart) {
/*     */ 
/*     */           
/* 128 */           if (module.isEnabled()) {
/* 129 */             RenderUtil.drawRoundedRect(startX + 88.0F, mY + 8.0F, startX + 175.0F, mY + 25.0F, 8.0F, (new Color(58, 58, 58)).getRGB());
/* 130 */             RenderUtil.circle(startX + 95.0F, mY + 16.0F, 2.0F, (new Color(0, 100, 237)).getRGB());
/*     */           } else {
/* 132 */             RenderUtil.drawRoundedRect(startX + 88.0F, mY + 8.0F, startX + 175.0F, mY + 25.0F, 8.0F, (new Color(39, 39, 39)).getRGB());
/* 133 */             RenderUtil.circle(startX + 95.0F, mY + 16.0F, 2.0F, (new Color(152, 152, 152)).getRGB());
/*     */           } 
/* 135 */           RenderUtil.startGlScissor((int)startX, (int)(startY - 16.0F), 165, 285);
/* 136 */           Client.instance.FontLoaders.Comfortaa20.drawString(module.getName(), startX + 108.0F - 5.0F, mY + 13.0F, module.isEnabled() ? (new Color(152, 152, 152)).getRGB() : (new Color(68, 68, 68)).getRGB());
/* 137 */           RenderUtil.stopGlScissor();
/* 138 */           if (!module.getValues().isEmpty()) {
/* 139 */             Client.instance.FontLoaders.Comfortaa18.drawString("+", startX + 167.0F, mY + 15.0F, (new Color(150, 150, 150)).getRGB());
/*     */           }
/* 141 */           if (isSettingsButtonHovered(startX + 108.0F, mY, startX + 100.0F + this.typefont
/* 142 */               .getStringWidth(module.getName()), mY + 12.0F + this.typefont
/* 143 */               .getStringHeight(), mouseX, mouseY)) {
/* 144 */             if (!this.previousmouse && Mouse.isButtonDown(0)) {
/* 145 */               module.setEnabled(!module.isEnabled());
/* 146 */               this.previousmouse = true;
/*     */             } 
/* 148 */             if (!this.previousmouse && Mouse.isButtonDown(1)) {
/* 149 */               this.previousmouse = true;
/*     */             }
/*     */           } 
/*     */           
/* 153 */           if (!Mouse.isButtonDown(0)) {
/* 154 */             this.previousmouse = false;
/*     */           }
/* 156 */           if (isSettingsButtonHovered(startX + 90.0F, mY, startX + 100.0F + Client.instance.FontLoaders.Comfortaa20
/* 157 */               .getStringWidth(module.getName()), mY + 12.0F + Client.instance.FontLoaders.Comfortaa20
/* 158 */               .getStringHeight(), mouseX, mouseY) && Mouse.isButtonDown(1)) {
/* 159 */             currentModule = module;
/* 160 */             this.valueStart = 0;
/*     */           } 
/* 162 */           mY += 18.0F;
/*     */         } 
/* 164 */       }  mY = startY + 25.0F;
/* 165 */       for (j = 0; j < currentModule.getValues().size() && 
/* 166 */         mY <= startY + 215.0F; j++) {
/*     */         
/* 168 */         if (j >= this.valueStart) {
/*     */ 
/*     */           
/* 171 */           Value value = currentModule.getValues().get(j);
/* 172 */           if (!((Boolean)ClickGui.Visitable.getValue()).booleanValue() || 
/* 173 */             value.isVisitable()) {
/*     */             
/* 175 */             if (value instanceof Numbers) {
/* 176 */               float x = startX + 290.0F;
/*     */ 
/*     */ 
/*     */               
/* 180 */               double render = (68.0F * (((Number)value.getValue()).floatValue() - ((Numbers)value).getMinimum().floatValue()) / (((Numbers)value).getMaximum().floatValue() - ((Numbers)value).getMinimum().floatValue()));
/* 181 */               RenderUtil.drawRect(x - 6.0F, mY + 2.0F, (float)(x + 74.0D), mY + 3.0F, (new Color(200, 200, 200))
/* 182 */                   .getRGB());
/* 183 */               RenderUtil.drawRect(x - 6.0F, mY + 2.0F, (float)(x + render + 6.5D), mY + 3.0F, (new Color(0, 100, 237))
/* 184 */                   .getRGB());
/* 185 */               RenderUtil.circle((float)(x + render + 4.0D), mY + 2.0F, 2.0F, (new Color(0, 100, 237)).getRGB());
/* 186 */               this.font.drawString(value.getName() + ": " + value.getValue(), startX + 190.0F, mY, (new Color(152, 152, 152)).getRGB());
/* 187 */               if (!Mouse.isButtonDown(0)) {
/* 188 */                 this.previousmouse = false;
/*     */               }
/* 190 */               if (isButtonHovered(x, mY - 2.0F, x + 100.0F, mY + 7.0F, mouseX, mouseY) && 
/* 191 */                 Mouse.isButtonDown(0)) {
/* 192 */                 if (!this.previousmouse && Mouse.isButtonDown(0)) {
/* 193 */                   render = ((Numbers)value).getMinimum().doubleValue();
/* 194 */                   double max = ((Numbers)value).getMaximum().doubleValue();
/* 195 */                   double inc = ((Numbers)value).getIncrement().doubleValue();
/* 196 */                   double valAbs = mouseX - x + 1.0D;
/* 197 */                   double perc = valAbs / 68.0D;
/* 198 */                   perc = Math.min(Math.max(0.0D, perc), 1.0D);
/* 199 */                   double valRel = (max - render) * perc;
/* 200 */                   double val = render + valRel;
/* 201 */                   val = Math.round(val * 1.0D / inc) / 1.0D / inc;
/* 202 */                   value.setValue(Double.valueOf(val));
/*     */                 } 
/* 204 */                 if (!Mouse.isButtonDown(0)) {
/* 205 */                   this.previousmouse = false;
/*     */                 }
/*     */               } 
/* 208 */               mY += 20.0F;
/*     */             } 
/* 210 */             if (value instanceof awareline.main.mod.values.Option) {
/* 211 */               float x = startX + 230.0F;
/* 212 */               this.font.drawString(value.getName(), startX + 190.0F, mY, (new Color(152, 152, 152)).getRGB());
/* 213 */               if (((Boolean)value.getValue()).booleanValue()) {
/* 214 */                 RenderUtil.drawRoundedRect(x + 56.0F, mY - 1.0F, x + 76.0F, mY + 9.0F, 4.0F, (new Color(58, 58, 58)).getRGB());
/* 215 */                 RenderUtil.circle(x + 72.0F, mY + 4.0F, 4.0F, (new Color(0, 100, 237)).getRGB());
/*     */               } else {
/*     */                 
/* 218 */                 RenderUtil.drawRoundedRect(x + 56.0F, mY - 1.0F, x + 76.0F, mY + 9.0F, 4.0F, (new Color(58, 58, 58)).getRGB());
/* 219 */                 RenderUtil.circle(x + 60.0F, mY + 4.0F, 4.0F, (new Color(152, 152, 152)).getRGB());
/*     */               } 
/* 221 */               if (isCheckBoxHovered(x + 56.0F, mY, x + 76.0F, mY + 9.0F, mouseX, mouseY)) {
/* 222 */                 if (!this.previousmouse && Mouse.isButtonDown(0)) {
/* 223 */                   this.previousmouse = true;
/* 224 */                   this.mouse = true;
/*     */                 } 
/*     */                 
/* 227 */                 if (this.mouse) {
/* 228 */                   value.setValue(Boolean.valueOf(!((Boolean)value.getValue()).booleanValue()));
/* 229 */                   this.mouse = false;
/*     */                 } 
/*     */               } 
/* 232 */               if (!Mouse.isButtonDown(0)) {
/* 233 */                 this.previousmouse = false;
/*     */               }
/* 235 */               mY += 20.0F;
/*     */             } 
/* 237 */             if (value instanceof Mode)
/* 238 */             { Mode mo = (Mode)value;
/* 239 */               float x = startX + 275.0F;
/* 240 */               this.font.drawStringWithShadow(value.getName(), (startX + 190.0F), mY, (new Color(152, 152, 152)).getRGB());
/* 241 */               RenderUtil.drawRect(x + 20.0F, mY, x + 65.0F, mY + 10.0F, (new Color(120, 120, 120))
/* 242 */                   .getRGB());
/*     */               
/* 244 */               RenderUtil.drawRect(x + 5.0F, mY, x + 15.0F, mY + 10.0F, (new Color(0, 100, 237))
/* 245 */                   .getRGB());
/* 246 */               RenderUtil.drawRect(x + 70.0F, mY, x + 80.0F, mY + 10.0F, (new Color(0, 100, 237))
/* 247 */                   .getRGB());
/* 248 */               this.font.drawStringWithShadow("<", (x + 8.0F - 1.0F), (mY + 3.0F), (new Color(152, 152, 152)).getRGB());
/* 249 */               this.font.drawStringWithShadow(">", (x + 72.0F + 1.0F), (mY + 3.0F), (new Color(152, 152, 152)).getRGB());
/* 250 */               RenderUtil.startGlScissor((int)(x + 20.0F), (int)mY, (int)(x + 65.0F), (int)(mY + 10.0F));
/* 251 */               this.font.drawStringWithShadow(((Mode)value).getModeAsString(), (x + 40.0F - (this.font
/* 252 */                   .getStringWidth(((Mode)value).getModeAsString()) / 2)), (mY + 2.5F), -1);
/* 253 */               RenderUtil.stopGlScissor();
/* 254 */               if (isStringHovered(x, mY - 5.0F, x + 100.0F, mY + 15.0F, mouseX, mouseY)) {
/* 255 */                 if (!this.previousmouse) {
/* 256 */                   if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
/* 257 */                     List<String> options = Arrays.asList(mo.getModes());
/*     */                     
/* 259 */                     int index = options.indexOf(mo.getValue());
/* 260 */                     if (Mouse.isButtonDown(0)) {
/* 261 */                       index++;
/*     */                     } else {
/* 263 */                       index--;
/*     */                     } 
/* 265 */                     if (index >= options.size()) {
/* 266 */                       index = 0;
/* 267 */                     } else if (index < 0) {
/* 268 */                       index = options.size() - 1;
/*     */                     } 
/* 270 */                     mo.setValue(mo.getModes()[index]);
/*     */                   } 
/* 272 */                   this.previousmouse = true;
/*     */                 } 
/* 274 */                 if (!Mouse.isButtonDown(0)) {
/* 275 */                   this.previousmouse = false;
/*     */                 }
/*     */               } 
/*     */               
/* 279 */               mY += 25.0F; } 
/*     */           } 
/*     */         } 
/* 282 */       }  RenderUtil.startGlScissor((int)startX, (int)(startY - 16.0F), 78, 285);
/* 283 */       float typeY = 0.0F;
/*     */       
/* 285 */       for (int k = 0; k < (ModuleType.values()).length; k++) {
/* 286 */         ModuleType category = ModuleType.values()[k];
/* 287 */         this.typefont.drawString(category.name(), startX + 25.0F, startY + 40.0F + typeY, (category == currentModuleType) ? (new Color(0, 100, 237))
/* 288 */             .getRGB() : (new Color(152, 152, 152)).getRGB());
/* 289 */         typeY += 20.0F;
/*     */       } 
/* 291 */       Client.instance.FontLoaders.guiicons28.drawString("G", startX + 7.0F, startY + 140.0F, 
/* 292 */           currentModuleType.name().equals("Misc") ? (new Color(0, 100, 237)).getRGB() : (new Color(152, 152, 152)).getRGB());
/* 293 */       Client.instance.FontLoaders.guiicons28.drawString("J", startX + 7.0F, startY + 160.0F, 
/* 294 */           currentModuleType.name().equals("Globals") ? (new Color(0, 100, 237)).getRGB() : (new Color(152, 152, 152)).getRGB());
/* 295 */       Client.instance.FontLoaders.novoicons25.drawString("G", startX + 7.0F, startY + 180.0F, 
/* 296 */           currentModuleType.name().equals("Script") ? (new Color(0, 100, 237)).getRGB() : (new Color(152, 152, 152)).getRGB());
/* 297 */       int typeWidth = -4;
/* 298 */       switch (currentModuleType.name()) {
/*     */         case "Combat":
/* 300 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/" + currentModuleType + '\002' + ".png"), ((int)startX + 5), ((int)startY + 40 + -4), 14.0F, 14.0F);
/* 301 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/Render.png"), ((int)startX + 5), ((int)startY + 80 + -4), 14.0F, 14.0F);
/* 302 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/Movement.png"), ((int)startX + 5), ((int)startY + 60 + -4), 14.0F, 14.0F);
/* 303 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/Player.png"), ((int)startX + 5), ((int)startY + 100 + -4), 14.0F, 14.0F);
/* 304 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/World.png"), ((int)startX + 5), ((int)startY + 120 + -4), 14.0F, 14.0F);
/*     */           break;
/*     */         
/*     */         case "Render":
/* 308 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/Combat.png"), ((int)startX + 5), ((int)startY + 40 + -4), 14.0F, 14.0F);
/* 309 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/" + currentModuleType + '\002' + ".png"), ((int)startX + 5), ((int)startY + 80 + -4), 14.0F, 14.0F);
/* 310 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/Movement.png"), ((int)startX + 5), ((int)startY + 60 + -4), 14.0F, 14.0F);
/* 311 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/Player.png"), ((int)startX + 5), ((int)startY + 100 + -4), 14.0F, 14.0F);
/* 312 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/World.png"), ((int)startX + 5), ((int)startY + 120 + -4), 14.0F, 14.0F);
/*     */           break;
/*     */         
/*     */         case "Movement":
/* 316 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/Combat.png"), ((int)startX + 5), ((int)startY + 40 + -4), 14.0F, 14.0F);
/* 317 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/Render.png"), ((int)startX + 5), ((int)startY + 80 + -4), 14.0F, 14.0F);
/* 318 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/" + currentModuleType + '\002' + ".png"), ((int)startX + 5), ((int)startY + 60 + -4), 14.0F, 14.0F);
/* 319 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/Player.png"), ((int)startX + 5), ((int)startY + 100 + -4), 14.0F, 14.0F);
/* 320 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/World.png"), ((int)startX + 5), ((int)startY + 120 + -4), 14.0F, 14.0F);
/*     */           break;
/*     */         
/*     */         case "Player":
/* 324 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/Combat.png"), ((int)startX + 5), ((int)startY + 40 + -4), 14.0F, 14.0F);
/* 325 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/Render.png"), ((int)startX + 5), ((int)startY + 80 + -4), 14.0F, 14.0F);
/* 326 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/Movement.png"), ((int)startX + 5), ((int)startY + 60 + -4), 14.0F, 14.0F);
/* 327 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/" + currentModuleType + '\002' + ".png"), ((int)startX + 5), ((int)startY + 100 + -4), 14.0F, 14.0F);
/* 328 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/World.png"), ((int)startX + 5), ((int)startY + 120 + -4), 14.0F, 14.0F);
/*     */           break;
/*     */         
/*     */         case "World":
/* 332 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/Combat.png"), ((int)startX + 5), ((int)startY + 40 + -4), 14.0F, 14.0F);
/* 333 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/Render.png"), ((int)startX + 5), ((int)startY + 80 + -4), 14.0F, 14.0F);
/* 334 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/Movement.png"), ((int)startX + 5), ((int)startY + 60 + -4), 14.0F, 14.0F);
/* 335 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/Player.png"), ((int)startX + 5), ((int)startY + 100 + -4), 14.0F, 14.0F);
/* 336 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/" + currentModuleType + '\002' + ".png"), ((int)startX + 5), ((int)startY + 120 + -4), 14.0F, 14.0F);
/*     */           break;
/*     */         
/*     */         case "Globals":
/*     */         case "Script":
/*     */         case "Misc":
/* 342 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/Combat.png"), ((int)startX + 5), ((int)startY + 40 + -4), 14.0F, 14.0F);
/* 343 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/Render.png"), ((int)startX + 5), ((int)startY + 80 + -4), 14.0F, 14.0F);
/* 344 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/Movement.png"), ((int)startX + 5), ((int)startY + 60 + -4), 14.0F, 14.0F);
/* 345 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/Player.png"), ((int)startX + 5), ((int)startY + 100 + -4), 14.0F, 14.0F);
/* 346 */           RenderUtil.drawImage(new ResourceLocation("client/clickgui/Clickui/World.png"), ((int)startX + 5), ((int)startY + 120 + -4), 14.0F, 14.0F);
/*     */           break;
/*     */       } 
/*     */       
/* 350 */       RenderUtil.stopGlScissor();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isStringHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
/* 356 */     return (mouseX >= f && mouseX <= g && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public static boolean isSettingsButtonHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
/* 360 */     return (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public static boolean isButtonHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
/* 364 */     return (mouseX >= f && mouseX <= g && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public static boolean isCheckBoxHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
/* 368 */     return (mouseX >= f && mouseX <= g && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public static boolean isCategoryHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
/* 372 */     return (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
/* 376 */     return (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\powerx\PXClickGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */