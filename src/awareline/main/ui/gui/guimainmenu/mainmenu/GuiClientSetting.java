/*     */ package awareline.main.ui.gui.guimainmenu.mainmenu;
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.font.cfont.CFontRenderer;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.ui.gui.guimainmenu.mainmenu.button.SimpleButton;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import awareline.main.utility.render.RoundedUtil;
/*     */ import awareline.main.utility.render.gl.flux.GLUtils;
/*     */ import awareline.main.utility.timer.TimerUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiOptions;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class GuiClientSetting extends GuiScreen {
/*     */   public float startX;
/*     */   public float startY;
/*     */   public int valueStart;
/*  29 */   public final Module currentModule = ClientSetting.getInstance;
/*     */   
/*     */   boolean previousmouse = true;
/*     */   
/*     */   boolean exiting;
/*  34 */   private final TimerUtil timer = new TimerUtil(); boolean mouse;
/*     */   public float moveX;
/*     */   public float moveY;
/*     */   boolean bind;
/*     */   float hue;
/*     */   public static int alpha;
/*     */   final boolean needAnim;
/*     */   boolean rev;
/*     */   float anim;
/*     */   float anim2;
/*  44 */   float anim3 = this.width;
/*     */   
/*     */   final GuiScreen prevGuiScreen;
/*     */   
/*     */   private final CFontRenderer font;
/*     */   
/*     */   boolean isJumpToMCOptions;
/*     */   
/*     */   public GuiClientSetting(GuiScreen prev, boolean needAnim) {
/*  53 */     this.font = Client.instance.FontLoaders.regular16;
/*     */     this.prevGuiScreen = prev;
/*     */     this.needAnim = needAnim;
/*     */   }
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  58 */     this.startX = (this.width / 2 - 215);
/*  59 */     this.startY = this.height / 2.0F - 150.0F;
/*  60 */     if (!this.timer.hasReached(500.0D)) {
/*  61 */       this.anim = this.anim2 = this.anim3 = this.width;
/*  62 */       this.rev = true;
/*  63 */     } else if (!this.exiting) {
/*  64 */       this.rev = false;
/*     */     } 
/*  66 */     if (this.rev) {
/*  67 */       this.anim = AnimationUtil.animate(this.width, this.anim, 6.0F / Minecraft.getDebugFPS());
/*  68 */       this.anim2 = AnimationUtil.animate(this.width, this.anim2, 4.0F / Minecraft.getDebugFPS());
/*  69 */       this.anim3 = AnimationUtil.animate(this.width, this.anim3, 5.5F / Minecraft.getDebugFPS());
/*  70 */       if (this.anim2 >= (this.width - 5) && this.timer.hasReached(510.0D)) {
/*  71 */         if (this.needAnim) {
/*  72 */           if (this.isJumpToMCOptions) {
/*  73 */             this.mc.displayGuiScreen((GuiScreen)new GuiOptions(this, this.mc.gameSettings));
/*  74 */             this.isJumpToMCOptions = false;
/*     */           } else {
/*  76 */             this.mc.displayGuiScreen(new ClientMainMenu());
/*     */           } 
/*     */         } else {
/*  79 */           this.mc.displayGuiScreen(this.prevGuiScreen);
/*     */         } 
/*     */       }
/*     */     } else {
/*  83 */       this.anim = AnimationUtil.animate(0.0F, this.anim, 3.0F / Minecraft.getDebugFPS());
/*  84 */       this.anim2 = AnimationUtil.animate(0.0F, this.anim2, 5.0F / Minecraft.getDebugFPS());
/*  85 */       this.anim3 = AnimationUtil.animate(0.0F, this.anim3, 4.5F / Minecraft.getDebugFPS());
/*     */     } 
/*     */     
/*  88 */     alpha = 255;
/*  89 */     if (this.hue > 255.0F) {
/*  90 */       this.hue = 0.0F;
/*     */     }
/*  92 */     int color4 = Client.instance.getClientColorNoRainbow(alpha);
/*  93 */     this.hue = (float)(this.hue + 0.1D);
/*  94 */     drawDefaultBackground();
/*  95 */     RoundedUtil.drawRound(this.startX + 50.0F, this.startY + 20.0F, 350.0F, 220.0F, 5.0F, new Color(0, 0, 0, 
/*  96 */           ClientSetting.shaderMode.is("Purple") ? 100 : 200));
/*     */     
/*  98 */     drawRect(0, 0, 0, 0, 0);
/*  99 */     int m = Mouse.getDWheel();
/* 100 */     List<Value> values = this.currentModule.getValues();
/* 101 */     if (isCategoryHovered(this.startX + 200.0F, this.startY + 50.0F, this.startX + 430.0F, this.startY + 315.0F, mouseX, mouseY)) {
/* 102 */       if (m < 0 && this.valueStart < values.size() - 1) {
/* 103 */         this.valueStart++;
/*     */       }
/* 105 */       if (m > 0 && this.valueStart > 0) {
/* 106 */         this.valueStart--;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 111 */     float mY = this.startY + 12.0F - 5.0F;
/*     */     
/* 113 */     for (int i = 0; i < values.size() && 
/* 114 */       mY <= this.startY + 220.0F; i++) {
/*     */       
/* 116 */       if (i >= this.valueStart) {
/*     */ 
/*     */         
/* 119 */         Value value = values.get(i);
/* 120 */         if (value instanceof Numbers) {
/* 121 */           float x = this.startX + 320.0F;
/*     */ 
/*     */ 
/*     */           
/* 125 */           double render = (74.0F * (((Number)value.getValue()).floatValue() - ((Numbers)value).getMinimum().floatValue()) / (((Numbers)value).getMaximum().floatValue() - ((Numbers)value).getMinimum().floatValue()));
/* 126 */           RenderUtil.drawFastRoundedRect(x - 35.0F, mY + 53.0F, x + 75.0F - 35.0F, mY + 56.0F, 1.0F, 
/* 127 */               isButtonHovered(x - 35.0F, mY + 53.0F, x + 75.0F - 35.0F, mY + 56.0F, mouseX, mouseY) ? (new Color(80, 80, 80, alpha)).getRGB() : (new Color(30, 30, 30, alpha)).getRGB());
/* 128 */           RenderUtil.drawFastRoundedRect((int)x - 35, mY + 53.0F, (int)(x + render + 1.0D - 35.0D), mY + 56.0F, 1.0F, Client.instance.getBlueColor());
/* 129 */           RenderUtil.drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/* 130 */           this.font.drawString(value.getName(), this.startX + 100.0F, mY + 50.0F + 3.0F, (new Color(255, 255, 255, alpha))
/* 131 */               .getRGB());
/* 132 */           this.font.drawString(value.getValue().toString(), x - 38.0F - this.font.getStringWidth(value.getValue().toString()), mY + 50.0F + 2.5F, (new Color(255, 255, 255, alpha))
/* 133 */               .getRGB());
/* 134 */           if (!Mouse.isButtonDown(0)) {
/* 135 */             this.previousmouse = false;
/*     */           }
/* 137 */           if (isButtonHovered(x - 35.0F, mY + 53.0F, x + 75.0F - 35.0F, mY + 56.0F, mouseX, mouseY) && 
/* 138 */             Mouse.isButtonDown(0)) {
/* 139 */             if (!this.previousmouse && Mouse.isButtonDown(0)) {
/* 140 */               render = ((Numbers)value).getMinimum().doubleValue();
/* 141 */               double max = ((Numbers)value).getMaximum().doubleValue();
/* 142 */               double inc = ((Numbers)value).getIncrement().doubleValue();
/* 143 */               double valAbs = mouseX - x + 1.0D - 35.0D;
/* 144 */               double perc = valAbs / 68.0D;
/* 145 */               perc = Math.min(Math.max(0.0D, perc), 1.0D);
/* 146 */               double valRel = (max - render) * perc;
/* 147 */               double val = render + valRel;
/* 148 */               val = Math.round(val * 1.0D / inc) / 1.0D / inc;
/* 149 */               value.setValue(Double.valueOf(val));
/*     */             } 
/* 151 */             if (!Mouse.isButtonDown(0)) {
/* 152 */               this.previousmouse = false;
/*     */             }
/*     */           } 
/*     */         } 
/* 156 */         if (value instanceof Option) {
/* 157 */           float x = this.startX + 270.0F;
/* 158 */           float xx = 65.0F;
/* 159 */           float x2x = 65.0F;
/* 160 */           ((Option)value).anim = AnimationUtil.moveUD(((Option)value).anim, ((Boolean)value.getValue()).booleanValue() ? 5.0F : 0.0F, 18.0F / Minecraft.getDebugFPS(), 7.0F / Minecraft.getDebugFPS());
/* 161 */           RenderUtil.drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/* 162 */           this.font.drawString(value.getName(), this.startX + 100.0F, mY + 50.0F + 3.0F, (new Color(255, 255, 255, alpha))
/* 163 */               .getRGB());
/* 164 */           GLUtils.startSmooth();
/* 165 */           drawFilledCircle((x + x2x), mY + 54.5D, (5.0F - ((Option)value).anim), (new Color(56, 56, 56)).getRGB(), 10);
/* 166 */           drawFilledCircle((x + x2x), mY + 54.5D, ((Option)value).anim, color4, 10);
/* 167 */           GLUtils.endSmooth();
/* 168 */           if (isCheckBoxHovered(x + xx - 5.0F, mY + 50.0F, x + x2x + 6.0F, mY + 59.0F, mouseX, mouseY)) {
/* 169 */             if (!this.previousmouse && Mouse.isButtonDown(0)) {
/* 170 */               this.previousmouse = true;
/* 171 */               this.mouse = true;
/*     */             } 
/*     */             
/* 174 */             if (this.mouse) {
/* 175 */               value.setValue(Boolean.valueOf(!((Boolean)value.getValue()).booleanValue()));
/* 176 */               this.mouse = false;
/*     */             } 
/*     */           } 
/* 179 */           if (!Mouse.isButtonDown(0)) {
/* 180 */             this.previousmouse = false;
/*     */           }
/*     */         } 
/* 183 */         if (value instanceof Mode) {
/* 184 */           float x = this.startX + 320.0F;
/* 185 */           RenderUtil.drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/* 186 */           this.font.drawString(value.getName(), this.startX + 100.0F, mY + 52.0F + 3.0F, (new Color(255, 255, 255, alpha))
/* 187 */               .getRGB());
/*     */           
/* 189 */           RenderUtil.drawRoundRect((x - 35.0F), (mY + 45.0F), (x + 75.0F - 35.0F), (mY + 65.0F), 3, (new Color(30, 30, 30)).getRGB());
/* 190 */           RenderUtil.drawRoundRect((x - 35.0F), (mY + 45.0F), (x + 13.0F - 35.0F), (mY + 65.0F), 1, color4);
/*     */           
/* 192 */           this.font.drawCenteredString("<", x + 7.5F - 36.0F, mY + 52.0F, (new Color(255, 255, 255, alpha)).getRGB());
/*     */           
/* 194 */           RenderUtil.drawRoundRect((x + 75.0F - 13.0F - 35.0F), (mY + 45.0F), (x + 75.0F - 35.0F), (mY + 65.0F), 1, color4);
/* 195 */           this.font.drawCenteredString(">", x + 75.0F - 36.0F - 5.5F, mY + 52.0F, (new Color(255, 255, 255, alpha)).getRGB());
/*     */           
/* 197 */           this.font.drawCenteredString(((Mode)value).getModeAsString(), x + 3.0F, mY + 52.0F, (new Color(255, 255, 255))
/* 198 */               .getRGB());
/*     */           
/* 200 */           if (isStringHovered(x - 35.0F, mY + 45.0F, x + 13.0F - 35.0F, mY + 65.0F, mouseX, mouseY) && 
/* 201 */             Mouse.isButtonDown(0) && !this.previousmouse) {
/* 202 */             Mode s = (Mode)value;
/* 203 */             if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
/* 204 */               List<String> options = Arrays.asList(s.getModes());
/*     */               
/* 206 */               int indexz = options.indexOf(s.getValue());
/* 207 */               if (Mouse.isButtonDown(0)) {
/* 208 */                 indexz++;
/*     */               } else {
/* 210 */                 indexz--;
/*     */               } 
/* 212 */               if (indexz >= options.size()) {
/* 213 */                 indexz = 0;
/* 214 */               } else if (indexz < 0) {
/* 215 */                 indexz = options.size() - 1;
/*     */               } 
/* 217 */               s.setValue(s.getModes()[indexz]);
/*     */             } 
/* 219 */             this.previousmouse = true;
/*     */           } 
/*     */ 
/*     */           
/* 223 */           if (isStringHovered(x + 75.0F - 13.0F - 35.0F, mY + 45.0F, x + 75.0F - 35.0F, mY + 65.0F, mouseX, mouseY) && 
/* 224 */             Mouse.isButtonDown(0) && !this.previousmouse) {
/* 225 */             Mode s = (Mode)value;
/* 226 */             if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
/* 227 */               List<String> options = Arrays.asList(s.getModes());
/*     */               
/* 229 */               int indexz = options.indexOf(s.getValue());
/* 230 */               if (Mouse.isButtonDown(0)) {
/* 231 */                 indexz++;
/*     */               } else {
/* 233 */                 indexz--;
/*     */               } 
/* 235 */               if (indexz >= options.size()) {
/* 236 */                 indexz = 0;
/* 237 */               } else if (indexz < 0) {
/* 238 */                 indexz = options.size() - 1;
/*     */               } 
/* 240 */               s.setValue(s.getModes()[indexz]);
/*     */             } 
/* 242 */             this.previousmouse = true;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 247 */         mY += 25.0F;
/*     */       } 
/* 249 */     }  if ((isHovered(this.startX, this.startY, this.startX + 450.0F, this.startY + 50.0F, mouseX, mouseY) || isHovered(this.startX, this.startY + 315.0F, this.startX + 450.0F, this.startY + 350.0F, mouseX, mouseY) || isHovered(this.startX + 430.0F, this.startY, this.startX + 450.0F, this.startY + 350.0F, mouseX, mouseY)) && Mouse.isButtonDown(0)) {
/* 250 */       if (this.moveX == 0.0F && this.moveY == 0.0F) {
/* 251 */         this.moveX = mouseX - this.startX;
/* 252 */         this.moveY = mouseY - this.startY;
/*     */       } else {
/* 254 */         this.startX = mouseX - this.moveX;
/* 255 */         this.startY = mouseY - this.moveY;
/*     */       } 
/* 257 */       this.previousmouse = true;
/* 258 */     } else if (this.moveX != 0.0F || this.moveY != 0.0F) {
/* 259 */       this.moveX = 0.0F;
/* 260 */       this.moveY = 0.0F;
/*     */     } 
/* 262 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 264 */     SimpleRender.drawRectFloat(-10.0F, -10.0F, this.anim, this.height + 10.0F, (new Color(156, 50, 255)).getRGB());
/* 265 */     SimpleRender.drawRectFloat(-10.0F, -10.0F, this.anim3, this.height + 10.0F, (new Color(128, 0, 255)).getRGB());
/* 266 */     SimpleRender.drawRectFloat(-10.0F, -10.0F, this.anim2, this.height + 10.0F, (new Color(0, 0, 0)).getRGB());
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 271 */     this.timer.reset();
/* 272 */     this.buttonList.add(new SimpleButton(0, this.width / 2, this.height - 11, "Back to main menu"));
/* 273 */     this.buttonList.add(new SimpleButton(1, this.width / 2, this.height - 20, "Open Minecraft Settings"));
/* 274 */     super.initGui();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/* 280 */     if (button.id == 0) {
/* 281 */       Client.instance.saveConfigForMods();
/* 282 */       this.rev = true;
/* 283 */       this.exiting = true;
/* 284 */       this.isJumpToMCOptions = false;
/* 285 */     } else if (button.id == 1) {
/* 286 */       this.rev = true;
/* 287 */       this.exiting = true;
/* 288 */       this.isJumpToMCOptions = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void keyTyped(char typedChar, int keyCode) {
/* 294 */     if (keyCode == 1) {
/* 295 */       this.rev = true;
/* 296 */       this.exiting = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
/* 302 */     float mY = this.startY + 30.0F;
/* 303 */     for (int i = 0; i < this.currentModule.getValues().size() && 
/* 304 */       mY <= this.startY + 350.0F; i++) {
/*     */       
/* 306 */       if (i >= this.valueStart) {
/*     */ 
/*     */         
/* 309 */         Value value = this.currentModule.getValues().get(i);
/* 310 */         if (value instanceof Numbers) {
/* 311 */           mY += 20.0F;
/*     */         }
/* 313 */         if (value instanceof Option)
/*     */         {
/* 315 */           mY += 20.0F;
/*     */         }
/* 317 */         if (value instanceof Mode)
/*     */         {
/* 319 */           mY += 25.0F; } 
/*     */       } 
/*     */     } 
/* 322 */     float x1 = this.startX + 320.0F;
/* 323 */     float yyy = this.startY + 240.0F;
/* 324 */     if (isHovered(x1 + 2.0F, yyy + 45.0F, x1 + 78.0F, yyy + 65.0F, mouseX, mouseY)) {
/* 325 */       this.bind = true;
/*     */     }
/* 327 */     super.mouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */   
/*     */   public boolean isStringHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
/* 331 */     return (mouseX >= f && mouseX <= g && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public boolean isSettingsButtonHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
/* 335 */     return (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public boolean isButtonHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
/* 339 */     return (mouseX >= f && mouseX <= g && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public boolean isCheckBoxHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
/* 343 */     return (mouseX >= f && mouseX <= g && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public boolean isCategoryHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
/* 347 */     return (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */   
/*     */   public boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
/* 351 */     return (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 356 */     alpha = 0;
/*     */   }
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 360 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\guimainmenu\mainmenu\GuiClientSetting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */