/*     */ package awareline.main.ui.gui.clickgui.mode.old;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.ui.gui.clickgui.mode.old.utils.Opacity;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import awareline.main.utility.render.gl.flux.GLUtils;
/*     */ import java.awt.Color;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.Display;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OldClickGui
/*     */   extends GuiScreen
/*     */ {
/*     */   public static final int opacityx = 255;
/*  31 */   private static ModuleType currentModuleType = ModuleType.Combat;
/*  32 */   private static Module currentModule = !Client.instance.getModuleManager().getModulesInType(currentModuleType).isEmpty() ? Client.instance.getModuleManager().getModulesInType(currentModuleType).get(0) : null;
/*  33 */   private static float startX = 100.0F;
/*  34 */   private static float startY = 100.0F;
/*     */ 
/*     */   
/*  37 */   public final Opacity opacity = new Opacity(0);
/*     */   public int moduleStart;
/*     */   public int valueStart;
/*     */   public float moveX;
/*     */   public float moveY;
/*     */   boolean previousmouse = true;
/*     */   boolean mouse;
/*     */   float x;
/*     */   
/*     */   public static boolean isStringHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
/*  47 */     return (mouseX >= f && mouseX <= g && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public static boolean isSettingsButtonHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
/*  51 */     return (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public static boolean isButtonHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
/*  55 */     return (mouseX >= f && mouseX <= g && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public static boolean isCheckBoxHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
/*  59 */     return (mouseX >= f && mouseX <= g && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public static boolean isCategoryHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
/*  63 */     return (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
/*  67 */     return (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  72 */     drawRect(0.0D, 0.0D, Display.getWidth(), Display.getHeight(), (new Color(25, 25, 25, (int)this.opacity.shadowAnim())).getRGB());
/*  73 */     if (isHovered(startX, startY - 25.0F, startX + 400.0F, startY + 25.0F, mouseX, mouseY) && Mouse.isButtonDown(0)) {
/*  74 */       if (this.moveX == 0.0F && this.moveY == 0.0F) {
/*  75 */         this.moveX = mouseX - startX;
/*  76 */         this.moveY = mouseY - startY;
/*     */       } else {
/*  78 */         startX = mouseX - this.moveX;
/*  79 */         startY = mouseY - this.moveY;
/*     */       } 
/*     */       
/*  82 */       this.previousmouse = true;
/*  83 */     } else if (this.moveX != 0.0F || this.moveY != 0.0F) {
/*  84 */       this.moveX = 0.0F;
/*  85 */       this.moveY = 0.0F;
/*     */     } 
/*  87 */     this.opacity.interpolate(255.0F);
/*  88 */     float morex = 65.0F;
/*  89 */     drawRect((startX + 50.0F), (startY - 5.0F), (startX + 440.0F + morex), (startY - 35.0F), (new Color(18, 17, 17, (int)this.opacity.getOpacity())).getRGB());
/*  90 */     drawRect((startX + 50.0F), (startY - 5.0F), (startX + 440.0F + morex), (startY + 220.0F + 20.0F), (new Color(22, 22, 22, (int)this.opacity.getOpacity())).getRGB());
/*     */     
/*  92 */     drawRect((startX + 52.0F), startY - 31.5D, (startX + 438.0F + morex), (startY - 32.0F), Client.instance
/*  93 */         .getClientColor((int)this.opacity.getOpacity()));
/*  94 */     drawRect((startX + 64.0F), startY, (startX + 145.0F + morex), (startY + 210.0F + 10.0F), (new Color(0, 0, 0, (int)this.opacity.getOpacity())).getRGB());
/*  95 */     drawRect((startX + 150.0F), startY, (startX + 420.0F + morex), (startY + 210.0F + 10.0F), (new Color(0, 0, 0, (int)this.opacity.getOpacity())).getRGB());
/*     */     
/*     */     int i;
/*  98 */     for (i = 0; i < (ModuleType.values()).length; i++) {
/*  99 */       ModuleType[] iterator = ModuleType.values();
/* 100 */       float mousedown = 20.0F;
/* 101 */       boolean combat = iterator[i].toString().equals("Combat");
/* 102 */       boolean movement = iterator[i].toString().equals("Movement");
/* 103 */       boolean render = iterator[i].toString().equals("Render");
/* 104 */       boolean player = iterator[i].toString().equals("Player");
/* 105 */       boolean world = iterator[i].toString().equals("World");
/* 106 */       boolean misc = iterator[i].toString().equals("Misc");
/*     */       
/* 108 */       if (iterator[i] != currentModuleType) {
/* 109 */         if (combat) {
/* 110 */           Client.instance.FontLoaders.SF22.drawString("Combot", startX + 75.0F, startY - 22.0F, (new Color(100, 100, 100)).getRGB());
/* 111 */         } else if (movement) {
/* 112 */           Client.instance.FontLoaders.SF22.drawString("Movement", startX + 145.0F, startY - 22.0F, (new Color(100, 100, 100)).getRGB());
/* 113 */         } else if (render) {
/* 114 */           Client.instance.FontLoaders.SF22.drawString("Render", startX + 215.0F + 20.0F, startY - 22.0F, (new Color(100, 100, 100)).getRGB());
/* 115 */         } else if (player) {
/* 116 */           Client.instance.FontLoaders.SF22.drawString("Player", startX + 285.0F + 20.0F, startY - 22.0F, (new Color(100, 100, 100)).getRGB());
/* 117 */         } else if (world) {
/* 118 */           Client.instance.FontLoaders.SF22.drawString("World", startX + 355.0F + 20.0F, startY - 22.0F, (new Color(100, 100, 100)).getRGB());
/* 119 */         } else if (misc) {
/* 120 */           Client.instance.FontLoaders.SF22.drawString("Misc", startX + 425.0F + 20.0F, startY - 22.0F, (new Color(100, 100, 100)).getRGB());
/*     */         }
/*     */       
/* 123 */       } else if (combat) {
/* 124 */         Client.instance.FontLoaders.SF22.drawString("Combot", startX + 75.0F, startY - 22.0F, (new Color(235, 235, 235)).getRGB());
/* 125 */       } else if (movement) {
/* 126 */         Client.instance.FontLoaders.SF22.drawString("Movement", startX + 145.0F, startY - 22.0F, (new Color(235, 235, 235)).getRGB());
/* 127 */       } else if (render) {
/* 128 */         Client.instance.FontLoaders.SF22.drawString("Render", startX + 215.0F + 20.0F, startY - 22.0F, (new Color(235, 235, 235)).getRGB());
/* 129 */       } else if (player) {
/* 130 */         Client.instance.FontLoaders.SF22.drawString("Player", startX + 285.0F + 20.0F, startY - 22.0F, (new Color(235, 235, 235)).getRGB());
/* 131 */       } else if (world) {
/* 132 */         Client.instance.FontLoaders.SF22.drawString("World", startX + 355.0F + 20.0F, startY - 22.0F, (new Color(235, 235, 235)).getRGB());
/* 133 */       } else if (misc) {
/* 134 */         Client.instance.FontLoaders.SF22.drawString("Misc", startX + 425.0F + 20.0F, startY - 22.0F, (new Color(235, 235, 235)).getRGB());
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 139 */         if (isCategoryHovered(startX + 75.0F + (i * 70), startY - 30.0F, startX + 105.0F + 20.0F + (i * 70), startY - 15.0F, mouseX, mouseY) && Mouse.isButtonDown(0)) {
/* 140 */           currentModuleType = iterator[i];
/* 141 */           currentModule = !Client.instance.getModuleManager().getModulesInType(currentModuleType).isEmpty() ? Client.instance.getModuleManager().getModulesInType(currentModuleType).get(0) : null;
/* 142 */           this.moduleStart = 0;
/*     */         } 
/* 144 */       } catch (Exception e) {
/* 145 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     i = Mouse.getDWheel();
/* 150 */     if (isCategoryHovered(startX + 60.0F, startY, startX + 200.0F, startY + 260.0F, mouseX, mouseY)) {
/* 151 */       if (i < 0 && this.moduleStart < Client.instance.getModuleManager().getModulesInType(currentModuleType).size() - 1) {
/* 152 */         this.moduleStart++;
/*     */       }
/* 154 */       if (i > 0 && this.moduleStart > 0) {
/* 155 */         this.moduleStart--;
/*     */       }
/*     */     } 
/*     */     
/* 159 */     if (isCategoryHovered(startX + 200.0F, startY, startX + 420.0F, startY + 260.0F, mouseX, mouseY)) {
/* 160 */       if (i < 0 && this.valueStart < currentModule.getValues().size() - 1) {
/* 161 */         this.valueStart++;
/*     */       }
/*     */       
/* 164 */       if (i > 0 && this.valueStart > 0) {
/* 165 */         this.valueStart--;
/*     */       }
/*     */     } 
/*     */     
/* 169 */     Client.instance.FontLoaders.SF15.drawString((currentModule == null) ? currentModuleType.toString() : (currentModule.getName() + "_Settings"), startX + 153.0F, startY + 9.0F, (new Color(248, 248, 248))
/* 170 */         .getRGB());
/* 171 */     if (currentModule != null) {
/* 172 */       float valueY = startY + 2.0F;
/*     */       
/* 174 */       for (i = 0; i < Client.instance.getModuleManager().getModulesInType(currentModuleType).size(); i++) {
/* 175 */         Module value = Client.instance.getModuleManager().getModulesInType(currentModuleType).get(i);
/* 176 */         if (valueY > startY + 210.0F) {
/*     */           break;
/*     */         }
/*     */         
/* 180 */         if (i >= this.moduleStart) {
/* 181 */           drawRect((startX + 128.0F + 20.0F), (startY + 220.0F), (startX + 140.0F + 5.0F), (valueY + 12.0F - 4.0F - 10.0F), (new Color(22, 22, 22, (int)this.opacity.getOpacity())).getRGB());
/* 182 */           if (!value.isEnabled()) {
/* 183 */             Client.instance.FontLoaders.SF18.drawString(value.getName(), (startX + 70.0F), valueY + 8.0D, (new Color(100, 100, 100, (int)this.opacity.getOpacity())).getRGB());
/*     */           } else {
/* 185 */             Client.instance.FontLoaders.SF22.drawString("|", startX + 65.0F, valueY + 2.0F + 4.0F, Client.instance.getClientColor((int)this.opacity.getOpacity()));
/* 186 */             Client.instance.FontLoaders.SF18.drawString(value.getName(), startX + 70.0F, valueY + 8.0F, (new Color(248, 248, 248, (int)this.opacity.getOpacity())).getRGB());
/*     */           } 
/*     */           
/* 189 */           if (isSettingsButtonHovered(startX + 70.0F, valueY - 30.0F, startX + 100.0F + Client.instance.FontLoaders.SF20
/* 190 */               .getStringWidth(value.getName()), valueY + 30.0F, mouseX, mouseY)) {
/* 191 */             if (!this.previousmouse && Mouse.isButtonDown(0)) {
/* 192 */               value.setEnabled(!value.isEnabled());
/* 193 */               this.previousmouse = true;
/*     */             } 
/*     */             
/* 196 */             if (!this.previousmouse && Mouse.isButtonDown(1)) {
/* 197 */               this.previousmouse = true;
/*     */             }
/*     */           } 
/*     */           
/* 201 */           if (!Mouse.isButtonDown(0)) {
/* 202 */             this.previousmouse = false;
/*     */           }
/*     */           
/* 205 */           if (isSettingsButtonHovered(startX + 65.0F, valueY, startX + 100.0F + Client.instance.FontLoaders.SF20
/* 206 */               .getStringWidth(value.getName()), valueY + 10.0F, mouseX, mouseY) && 
/* 207 */             Mouse.isButtonDown(1)) {
/* 208 */             currentModule = value;
/* 209 */             this.valueStart = 0;
/*     */           } 
/*     */           
/* 212 */           valueY += 25.0F;
/*     */         } 
/*     */       } 
/*     */       
/* 216 */       valueY = startY + 23.0F;
/*     */       
/* 218 */       for (i = 0; i < currentModule.getValues().size() && valueY <= ((int)startY + 210); i++) {
/* 219 */         if (i >= this.valueStart) {
/* 220 */           Value value = currentModule.getValues().get(i);
/* 221 */           if (value instanceof Numbers) {
/* 222 */             Numbers v = (Numbers)value;
/* 223 */             this.x = startX + 300.0F;
/* 224 */             double current = (68.0F * (((Number)value.get()).floatValue() - ((Numbers)value).getMinimum().floatValue()) / (((Numbers)value).getMaximum().floatValue() - ((Numbers)value).getMinimum().floatValue()));
/* 225 */             v.smoothAnim = AnimationUtil.moveUD(v.smoothAnim, (float)current, SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/* 226 */             drawRect((this.x - 6.0F), (valueY + 2.0F), (this.x + 75.0F), (valueY + 3.0F), (new Color(50, 50, 50, (int)this.opacity.getOpacity())).getRGB());
/* 227 */             drawRect((this.x - 6.0F), (valueY + 2.0F), (float)((this.x + v.smoothAnim) + 6.5D), (valueY + 3.0F), Client.instance.getClientColor((int)this.opacity.getOpacity()));
/* 228 */             GLUtils.startSmooth();
/* 229 */             RenderUtil.drawRoundRect((this.x + v.smoothAnim + 2.0F), valueY, (this.x + v.smoothAnim + 7.0F), (valueY + 5.0F), Client.instance.getClientColor((int)this.opacity.getOpacity()));
/* 230 */             GLUtils.endSmooth();
/* 231 */             Client.instance.FontLoaders.SF18.drawString(value.getName() + ": " + value.get(), (startX + 160.0F), valueY, -1);
/* 232 */             if (!Mouse.isButtonDown(0)) {
/* 233 */               this.previousmouse = false;
/*     */             }
/*     */             
/* 236 */             if (isButtonHovered(this.x, valueY - 2.0F, this.x + 100.0F, valueY + 7.0F, mouseX, mouseY) && Mouse.isButtonDown(0)) {
/* 237 */               if (!this.previousmouse && Mouse.isButtonDown(0)) {
/* 238 */                 current = ((Numbers)value).getMinimum().doubleValue();
/* 239 */                 double max = ((Numbers)value).getMaximum().doubleValue();
/* 240 */                 double inc = ((Numbers)value).getIncrement().doubleValue();
/* 241 */                 double valAbs = (mouseX - this.x + 1.0F);
/* 242 */                 double perc = valAbs / 68.0D;
/* 243 */                 perc = Math.min(Math.max(0.0D, perc), 1.0D);
/* 244 */                 double valRel = (max - current) * perc;
/* 245 */                 double val = current + valRel;
/* 246 */                 val = Math.round(val * 1.0D / inc) / 1.0D / inc;
/* 247 */                 value.setValue(Double.valueOf(val));
/*     */               } 
/*     */               
/* 250 */               if (!Mouse.isButtonDown(0)) {
/* 251 */                 this.previousmouse = false;
/*     */               }
/*     */             } 
/*     */             
/* 255 */             valueY += 17.0F;
/*     */           } 
/*     */           
/* 258 */           if (value instanceof Option) {
/* 259 */             Option v = (Option)value;
/* 260 */             this.x = startX + 237.0F;
/* 261 */             Client.instance.FontLoaders.SF18.drawString(value.getName(), (startX + 160.0F), valueY, -1);
/* 262 */             drawRect((this.x + 56.0F), valueY, (this.x + 76.0F), (valueY + 1.0F), (new Color(255, 255, 255, (int)this.opacity.getOpacity())).getRGB());
/* 263 */             drawRect((this.x + 56.0F), (valueY + 8.0F), (this.x + 76.0F), (valueY + 9.0F), (new Color(255, 255, 255, (int)this.opacity.getOpacity())).getRGB());
/* 264 */             drawRect((this.x + 56.0F), valueY, (this.x + 57.0F), (valueY + 9.0F), (new Color(255, 255, 255, (int)this.opacity.getOpacity())).getRGB());
/* 265 */             drawRect((this.x + 77.0F), valueY, (this.x + 76.0F), (valueY + 9.0F), (new Color(255, 255, 255, (int)this.opacity.getOpacity())).getRGB());
/*     */             
/* 267 */             v.enableAnim = AnimationUtil.moveUD(v.enableAnim, ((Boolean)v.get()).booleanValue() ? 9.0F : 0.0F, 14.0F / 
/* 268 */                 Minecraft.getDebugFPS(), 6.0F / Minecraft.getDebugFPS());
/* 269 */             v.disableAnim = AnimationUtil.moveUD(v.disableAnim, ((Boolean)v.get()).booleanValue() ? 10.0F : 0.0F, 14.0F / 
/* 270 */                 Minecraft.getDebugFPS(), 6.0F / Minecraft.getDebugFPS());
/*     */             
/* 272 */             drawRect((this.x + 58.0F + v.enableAnim), (valueY + 2.0F), (this.x + 65.0F + v.disableAnim), (valueY + 7.0F), 
/* 273 */                 ((Boolean)v.get()).booleanValue() ? Client.instance.getClientColor((int)this.opacity.getOpacity()) : (new Color(150, 150, 150, (int)this.opacity.getOpacity())).getRGB());
/* 274 */             if (isCheckBoxHovered(this.x + 56.0F, valueY, this.x + 76.0F, valueY + 9.0F, mouseX, mouseY)) {
/* 275 */               if (!this.previousmouse && Mouse.isButtonDown(0)) {
/* 276 */                 this.previousmouse = true;
/* 277 */                 this.mouse = true;
/*     */               } 
/*     */               
/* 280 */               if (this.mouse) {
/* 281 */                 value.setValue(Boolean.valueOf(!((Boolean)value.get()).booleanValue()));
/* 282 */                 this.mouse = false;
/*     */               } 
/*     */             } 
/*     */             
/* 286 */             if (!Mouse.isButtonDown(0)) {
/* 287 */               this.previousmouse = false;
/*     */             }
/*     */             
/* 290 */             valueY += 17.0F;
/*     */           } 
/*     */           
/* 293 */           if (value instanceof Mode) {
/* 294 */             this.x = startX + 300.0F;
/* 295 */             Client.instance.FontLoaders.SF18.drawString(value.getName(), (startX + 160.0F), valueY, -1);
/* 296 */             drawRect((this.x + 7.0F), (valueY - 5.0F), (this.x + 75.0F), (valueY + 10.0F), Client.instance.getClientColor((int)this.opacity.getOpacity()));
/* 297 */             Client.instance.FontLoaders.SF18.drawString(((Mode)value).getModeAsString(), (this.x + 40.0F - (Client.instance.FontLoaders.SF18.getStringWidth(((Mode)value).getModeAsString()) / 2)), valueY, -1);
/* 298 */             if (isStringHovered(this.x, valueY - 5.0F, this.x + 100.0F, valueY + 15.0F, mouseX, mouseY)) {
/* 299 */               if (!this.previousmouse) {
/* 300 */                 Mode m2 = (Mode)value;
/* 301 */                 if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
/* 302 */                   this.previousmouse = true;
/* 303 */                   List<String> options = Arrays.asList(m2.getModes());
/*     */                   
/* 305 */                   int index = options.indexOf(m2.get());
/* 306 */                   if (Mouse.isButtonDown(0)) {
/* 307 */                     index++;
/*     */                   } else {
/* 309 */                     index--;
/*     */                   } 
/* 311 */                   if (index >= options.size()) {
/* 312 */                     index = 0;
/* 313 */                   } else if (index < 0) {
/* 314 */                     index = options.size() - 1;
/*     */                   } 
/* 316 */                   m2.setValue(m2.getModes()[index]);
/*     */                 } 
/*     */               } 
/* 319 */               if (!Mouse.isButtonDown(0)) {
/* 320 */                 this.previousmouse = false;
/*     */               }
/*     */             } 
/* 323 */             valueY += 23.0F;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onGuiClosed() {
/* 331 */     this.opacity.setOpacity(0.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\old\OldClickGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */