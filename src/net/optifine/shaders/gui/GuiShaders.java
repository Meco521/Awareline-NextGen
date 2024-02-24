/*     */ package net.optifine.shaders.gui;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.Lang;
/*     */ import net.optifine.gui.GuiScreenOF;
/*     */ import net.optifine.gui.TooltipManager;
/*     */ import net.optifine.gui.TooltipProvider;
/*     */ import net.optifine.gui.TooltipProviderEnumShaderOptions;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import net.optifine.shaders.ShadersTex;
/*     */ import net.optifine.shaders.config.EnumShaderOption;
/*     */ import org.lwjgl.Sys;
/*     */ 
/*     */ public class GuiShaders
/*     */   extends GuiScreenOF {
/*     */   protected GuiScreen parentGui;
/*  25 */   protected String screenTitle = "Shaders";
/*  26 */   private final TooltipManager tooltipManager = new TooltipManager((GuiScreen)this, (TooltipProvider)new TooltipProviderEnumShaderOptions());
/*  27 */   private int updateTimer = -1;
/*     */   private GuiSlotShaders shaderList;
/*     */   private boolean saved = false;
/*  30 */   private static final float[] QUALITY_MULTIPLIERS = new float[] { 0.5F, 0.6F, 0.6666667F, 0.75F, 0.8333333F, 0.9F, 1.0F, 1.1666666F, 1.3333334F, 1.5F, 1.6666666F, 1.8F, 2.0F };
/*  31 */   private static final String[] QUALITY_MULTIPLIER_NAMES = new String[] { "0.5x", "0.6x", "0.66x", "0.75x", "0.83x", "0.9x", "1x", "1.16x", "1.33x", "1.5x", "1.66x", "1.8x", "2x" };
/*     */   private static final float QUALITY_MULTIPLIER_DEFAULT = 1.0F;
/*  33 */   private static final float[] HAND_DEPTH_VALUES = new float[] { 0.0625F, 0.125F, 0.25F };
/*  34 */   private static final String[] HAND_DEPTH_NAMES = new String[] { "0.5x", "1x", "2x" };
/*     */   
/*     */   private static final float HAND_DEPTH_DEFAULT = 0.125F;
/*     */   public static final int EnumOS_UNKNOWN = 0;
/*     */   public static final int EnumOS_WINDOWS = 1;
/*     */   public static final int EnumOS_OSX = 2;
/*     */   public static final int EnumOS_SOLARIS = 3;
/*     */   public static final int EnumOS_LINUX = 4;
/*     */   
/*     */   public GuiShaders(GuiScreen par1GuiScreen, GameSettings par2GameSettings) {
/*  44 */     this.parentGui = par1GuiScreen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  53 */     this.screenTitle = I18n.format("of.options.shadersTitle", new Object[0]);
/*     */     
/*  55 */     if (Shaders.shadersConfig == null)
/*     */     {
/*  57 */       Shaders.loadConfig();
/*     */     }
/*     */     
/*  60 */     int i = 120;
/*  61 */     int j = 20;
/*  62 */     int k = this.width - i - 10;
/*  63 */     int l = 30;
/*  64 */     int i1 = 20;
/*  65 */     int j1 = this.width - i - 20;
/*  66 */     this.shaderList = new GuiSlotShaders(this, j1, this.height, l, this.height - 50, 16);
/*  67 */     this.shaderList.registerScrollButtons(7, 8);
/*  68 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.ANTIALIASING, k, 0 + l, i, j));
/*  69 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.NORMAL_MAP, k, i1 + l, i, j));
/*  70 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.SPECULAR_MAP, k, 2 * i1 + l, i, j));
/*  71 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.RENDER_RES_MUL, k, 3 * i1 + l, i, j));
/*  72 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.SHADOW_RES_MUL, k, 4 * i1 + l, i, j));
/*  73 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.HAND_DEPTH_MUL, k, 5 * i1 + l, i, j));
/*  74 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_HAND_LIGHT, k, 6 * i1 + l, i, j));
/*  75 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_LIGHTING, k, 7 * i1 + l, i, j));
/*  76 */     int k1 = Math.min(150, j1 / 2 - 10);
/*  77 */     int l1 = j1 / 4 - k1 / 2;
/*  78 */     int i2 = this.height - 25;
/*  79 */     this.buttonList.add(new GuiButton(201, l1, i2, k1 - 22 + 1, j, Lang.get("of.options.shaders.shadersFolder")));
/*  80 */     this.buttonList.add(new GuiButtonDownloadShaders(210, l1 + k1 - 22 - 1, i2));
/*  81 */     this.buttonList.add(new GuiButton(202, j1 / 4 * 3 - k1 / 2, this.height - 25, k1, j, I18n.format("gui.done", new Object[0])));
/*  82 */     this.buttonList.add(new GuiButton(203, k, this.height - 25, i, j, Lang.get("of.options.shaders.shaderOptions")));
/*  83 */     updateButtons();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateButtons() {
/*  88 */     boolean flag = Config.isShaders();
/*     */     
/*  90 */     for (GuiButton guibutton : this.buttonList) {
/*     */       
/*  92 */       if (guibutton.id != 201 && guibutton.id != 202 && guibutton.id != 210 && guibutton.id != EnumShaderOption.ANTIALIASING.ordinal())
/*     */       {
/*  94 */         guibutton.enabled = flag;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 104 */     super.handleMouseInput();
/* 105 */     this.shaderList.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/* 113 */     actionPerformed(button, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformedRightClick(GuiButton button) {
/* 118 */     actionPerformed(button, true);
/*     */   }
/*     */ 
/*     */   
/*     */   private void actionPerformed(GuiButton button, boolean rightClick) {
/* 123 */     if (button.enabled)
/*     */     {
/* 125 */       if (!(button instanceof GuiButtonEnumShaderOption)) {
/*     */         
/* 127 */         if (!rightClick) {
/*     */           String s; boolean flag; GuiShaderOptions guishaderoptions;
/* 129 */           switch (button.id) {
/*     */             
/*     */             case 201:
/* 132 */               switch (getOSType()) {
/*     */                 
/*     */                 case 1:
/* 135 */                   s = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] { Shaders.shaderPacksDir.getAbsolutePath() });
/*     */ 
/*     */                   
/*     */                   try {
/* 139 */                     Runtime.getRuntime().exec(s);
/*     */                     
/*     */                     return;
/* 142 */                   } catch (IOException ioexception) {
/*     */                     
/* 144 */                     ioexception.printStackTrace();
/*     */                     break;
/*     */                   } 
/*     */ 
/*     */                 
/*     */                 case 2:
/*     */                   try {
/* 151 */                     Runtime.getRuntime().exec(new String[] { "/usr/bin/open", Shaders.shaderPacksDir.getAbsolutePath() });
/*     */                     
/*     */                     return;
/* 154 */                   } catch (IOException ioexception1) {
/*     */                     
/* 156 */                     ioexception1.printStackTrace();
/*     */                     break;
/*     */                   } 
/*     */               } 
/* 160 */               flag = false;
/*     */ 
/*     */               
/*     */               try {
/* 164 */                 Class<?> oclass1 = Class.forName("java.awt.Desktop");
/* 165 */                 Object object1 = oclass1.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 166 */                 oclass1.getMethod("browse", new Class[] { URI.class }).invoke(object1, new Object[] { (new File(this.mc.mcDataDir, "shaderpacks")).toURI() });
/*     */               }
/* 168 */               catch (Throwable throwable1) {
/*     */                 
/* 170 */                 throwable1.printStackTrace();
/* 171 */                 flag = true;
/*     */               } 
/*     */               
/* 174 */               if (flag) {
/*     */                 
/* 176 */                 Config.dbg("Opening via system class!");
/* 177 */                 Sys.openURL("file://" + Shaders.shaderPacksDir.getAbsolutePath());
/*     */               } 
/*     */               return;
/*     */ 
/*     */             
/*     */             case 202:
/* 183 */               Shaders.storeConfig();
/* 184 */               this.saved = true;
/* 185 */               this.mc.displayGuiScreen(this.parentGui);
/*     */               return;
/*     */             
/*     */             case 203:
/* 189 */               guishaderoptions = new GuiShaderOptions((GuiScreen)this, Config.getGameSettings());
/* 190 */               Config.getMinecraft().displayGuiScreen((GuiScreen)guishaderoptions);
/*     */               return;
/*     */ 
/*     */             
/*     */             case 210:
/*     */               try {
/* 196 */                 Class<?> oclass = Class.forName("java.awt.Desktop");
/* 197 */                 Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 198 */                 oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { new URI("http://optifine.net/shaderPacks") });
/*     */               }
/* 200 */               catch (Throwable throwable) {
/*     */                 
/* 202 */                 throwable.printStackTrace();
/*     */               } 
/*     */               break;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 212 */           this.shaderList.actionPerformed(button);
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 218 */         GuiButtonEnumShaderOption guibuttonenumshaderoption = (GuiButtonEnumShaderOption)button;
/*     */         
/* 220 */         switch (guibuttonenumshaderoption.getEnumShaderOption()) {
/*     */           
/*     */           case ANTIALIASING:
/* 223 */             Shaders.nextAntialiasingLevel(!rightClick);
/*     */             
/* 225 */             if (hasShiftDown())
/*     */             {
/* 227 */               Shaders.configAntialiasingLevel = 0;
/*     */             }
/*     */             
/* 230 */             Shaders.uninit();
/*     */             break;
/*     */           
/*     */           case NORMAL_MAP:
/* 234 */             Shaders.configNormalMap = !Shaders.configNormalMap;
/*     */             
/* 236 */             if (hasShiftDown())
/*     */             {
/* 238 */               Shaders.configNormalMap = true;
/*     */             }
/*     */             
/* 241 */             Shaders.uninit();
/* 242 */             this.mc.scheduleResourcesRefresh();
/*     */             break;
/*     */           
/*     */           case SPECULAR_MAP:
/* 246 */             Shaders.configSpecularMap = !Shaders.configSpecularMap;
/*     */             
/* 248 */             if (hasShiftDown())
/*     */             {
/* 250 */               Shaders.configSpecularMap = true;
/*     */             }
/*     */             
/* 253 */             Shaders.uninit();
/* 254 */             this.mc.scheduleResourcesRefresh();
/*     */             break;
/*     */           
/*     */           case RENDER_RES_MUL:
/* 258 */             Shaders.configRenderResMul = getNextValue(Shaders.configRenderResMul, QUALITY_MULTIPLIERS, 1.0F, !rightClick, hasShiftDown());
/* 259 */             Shaders.uninit();
/* 260 */             Shaders.scheduleResize();
/*     */             break;
/*     */           
/*     */           case SHADOW_RES_MUL:
/* 264 */             Shaders.configShadowResMul = getNextValue(Shaders.configShadowResMul, QUALITY_MULTIPLIERS, 1.0F, !rightClick, hasShiftDown());
/* 265 */             Shaders.uninit();
/* 266 */             Shaders.scheduleResizeShadow();
/*     */             break;
/*     */           
/*     */           case HAND_DEPTH_MUL:
/* 270 */             Shaders.configHandDepthMul = getNextValue(Shaders.configHandDepthMul, HAND_DEPTH_VALUES, 0.125F, !rightClick, hasShiftDown());
/* 271 */             Shaders.uninit();
/*     */             break;
/*     */           
/*     */           case OLD_HAND_LIGHT:
/* 275 */             Shaders.configOldHandLight.nextValue(!rightClick);
/*     */             
/* 277 */             if (hasShiftDown())
/*     */             {
/* 279 */               Shaders.configOldHandLight.resetValue();
/*     */             }
/*     */             
/* 282 */             Shaders.uninit();
/*     */             break;
/*     */           
/*     */           case OLD_LIGHTING:
/* 286 */             Shaders.configOldLighting.nextValue(!rightClick);
/*     */             
/* 288 */             if (hasShiftDown())
/*     */             {
/* 290 */               Shaders.configOldLighting.resetValue();
/*     */             }
/*     */             
/* 293 */             Shaders.updateBlockLightLevel();
/* 294 */             Shaders.uninit();
/* 295 */             this.mc.scheduleResourcesRefresh();
/*     */             break;
/*     */           
/*     */           case TWEAK_BLOCK_DAMAGE:
/* 299 */             Shaders.configTweakBlockDamage = !Shaders.configTweakBlockDamage;
/*     */             break;
/*     */           
/*     */           case CLOUD_SHADOW:
/* 303 */             Shaders.configCloudShadow = !Shaders.configCloudShadow;
/*     */             break;
/*     */           
/*     */           case TEX_MIN_FIL_B:
/* 307 */             Shaders.configTexMinFilB = (Shaders.configTexMinFilB + 1) % 3;
/* 308 */             Shaders.configTexMinFilN = Shaders.configTexMinFilS = Shaders.configTexMinFilB;
/* 309 */             button.displayString = "Tex Min: " + Shaders.texMinFilDesc[Shaders.configTexMinFilB];
/* 310 */             ShadersTex.updateTextureMinMagFilter();
/*     */             break;
/*     */           
/*     */           case TEX_MAG_FIL_N:
/* 314 */             Shaders.configTexMagFilN = (Shaders.configTexMagFilN + 1) % 2;
/* 315 */             button.displayString = "Tex_n Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilN];
/* 316 */             ShadersTex.updateTextureMinMagFilter();
/*     */             break;
/*     */           
/*     */           case TEX_MAG_FIL_S:
/* 320 */             Shaders.configTexMagFilS = (Shaders.configTexMagFilS + 1) % 2;
/* 321 */             button.displayString = "Tex_s Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilS];
/* 322 */             ShadersTex.updateTextureMinMagFilter();
/*     */             break;
/*     */           
/*     */           case SHADOW_CLIP_FRUSTRUM:
/* 326 */             Shaders.configShadowClipFrustrum = !Shaders.configShadowClipFrustrum;
/* 327 */             button.displayString = "ShadowClipFrustrum: " + toStringOnOff(Shaders.configShadowClipFrustrum);
/* 328 */             ShadersTex.updateTextureMinMagFilter();
/*     */             break;
/*     */         } 
/* 331 */         guibuttonenumshaderoption.updateButtonText();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 341 */     super.onGuiClosed();
/*     */     
/* 343 */     if (!this.saved)
/*     */     {
/* 345 */       Shaders.storeConfig();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 354 */     drawDefaultBackground();
/* 355 */     this.shaderList.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 357 */     if (this.updateTimer <= 0) {
/*     */       
/* 359 */       this.shaderList.updateList();
/* 360 */       this.updateTimer += 20;
/*     */     } 
/*     */     
/* 363 */     drawCenteredString(this.fontRendererObj, this.screenTitle + " ", this.width / 2, 15, 16777215);
/* 364 */     String s = "OpenGL: " + Shaders.glVersionString + ", " + Shaders.glVendorString + ", " + Shaders.glRendererString;
/* 365 */     int i = this.fontRendererObj.getStringWidth(s);
/*     */     
/* 367 */     if (i < this.width - 5) {
/*     */       
/* 369 */       drawCenteredString(this.fontRendererObj, s, this.width / 2, this.height - 40, 8421504);
/*     */     }
/*     */     else {
/*     */       
/* 373 */       drawString(this.fontRendererObj, s, 5, this.height - 40, 8421504);
/*     */     } 
/*     */     
/* 376 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 377 */     this.tooltipManager.drawTooltips(mouseX, mouseY, this.buttonList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 385 */     super.updateScreen();
/* 386 */     this.updateTimer--;
/*     */   }
/*     */ 
/*     */   
/*     */   public Minecraft getMc() {
/* 391 */     return this.mc;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCenteredString(String text, int x, int y, int color) {
/* 396 */     drawCenteredString(this.fontRendererObj, text, x, y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toStringOnOff(boolean value) {
/* 401 */     String s = Lang.getOn();
/* 402 */     String s1 = Lang.getOff();
/* 403 */     return value ? s : s1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toStringAa(int value) {
/* 408 */     return (value == 2) ? "FXAA 2x" : ((value == 4) ? "FXAA 4x" : Lang.getOff());
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toStringValue(float val, float[] values, String[] names) {
/* 413 */     int i = getValueIndex(val, values);
/* 414 */     return names[i];
/*     */   }
/*     */ 
/*     */   
/*     */   private float getNextValue(float val, float[] values, float valDef, boolean forward, boolean reset) {
/* 419 */     if (reset)
/*     */     {
/* 421 */       return valDef;
/*     */     }
/*     */ 
/*     */     
/* 425 */     int i = getValueIndex(val, values);
/*     */     
/* 427 */     if (forward) {
/*     */       
/* 429 */       i++;
/*     */       
/* 431 */       if (i >= values.length)
/*     */       {
/* 433 */         i = 0;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 438 */       i--;
/*     */       
/* 440 */       if (i < 0)
/*     */       {
/* 442 */         i = values.length - 1;
/*     */       }
/*     */     } 
/*     */     
/* 446 */     return values[i];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getValueIndex(float val, float[] values) {
/* 452 */     for (int i = 0; i < values.length; i++) {
/*     */       
/* 454 */       float f = values[i];
/*     */       
/* 456 */       if (f >= val)
/*     */       {
/* 458 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 462 */     return values.length - 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toStringQuality(float val) {
/* 467 */     return toStringValue(val, QUALITY_MULTIPLIERS, QUALITY_MULTIPLIER_NAMES);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toStringHandDepth(float val) {
/* 472 */     return toStringValue(val, HAND_DEPTH_VALUES, HAND_DEPTH_NAMES);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getOSType() {
/* 477 */     String s = System.getProperty("os.name").toLowerCase();
/* 478 */     return s.contains("win") ? 1 : (s.contains("mac") ? 2 : (s.contains("solaris") ? 3 : (s.contains("sunos") ? 3 : (s.contains("linux") ? 4 : (s.contains("unix") ? 4 : 0)))));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasShiftDown() {
/* 483 */     return isShiftKeyDown();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\gui\GuiShaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */