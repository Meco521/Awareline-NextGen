/*     */ package com.me.guichaguri.betterfps.gui;
/*     */ 
/*     */ import com.me.guichaguri.betterfps.BetterFpsConfig;
/*     */ import com.me.guichaguri.betterfps.BetterFpsHelper;
/*     */ import com.me.guichaguri.betterfps.tweaker.BetterFpsTweaker;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.Util;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiBetterFpsConfig
/*     */   extends GuiScreen
/*     */ {
/*  28 */   private GuiScreen parent = null;
/*     */ 
/*     */   
/*     */   public GuiBetterFpsConfig() {}
/*     */ 
/*     */   
/*     */   public GuiBetterFpsConfig(GuiScreen parent) {
/*  35 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<GuiButton> initButtons() {
/*  40 */     List<GuiButton> buttons = new ArrayList<>();
/*  41 */     BetterFpsConfig config = BetterFpsConfig.getConfig();
/*  42 */     buttons.add(new AlgorithmButton(2, "Algorithm", BetterFpsHelper.displayHelpers, (T)config.algorithm, new String[] { "The algorithm of sine & cosine methods", "搂cRequires restarting to take effect", "", "搂eShift-click com.com.me to test algorithms 搂7(This will take a few seconds)", "", "搂aMore information soon" }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  49 */     buttons.add(new GuiCycleButton.GuiBooleanButton(3, "Update Checker", config.updateChecker, new String[] { "Whether will check for updates on startup", "It's highly recommended enabling this option", "", "Default: On" }));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  54 */     buttons.add(new GuiCycleButton.GuiBooleanButton(4, "Preallocate Memory", config.preallocateMemory, new String[] { "Whether will preallocate 10MB on startup.", "搂cRequires restarting to take effect", "", "Default in Vanilla: On", "Default in BetterFps: Off", "", "Note: This allocation will only be cleaned once the memory is almost full" }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  61 */     buttons.add(new GuiCycleButton.GuiBooleanButton(5, "Fast Box Render", config.fastBoxRender, new String[] { "Whether will only render the exterior of boxes.", "搂cRequires restarting to take effect", "", "Default in Vanilla: Off", "Default in BetterFps: On" }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     buttons.add(new GuiCycleButton.GuiBooleanButton(6, "Fog", config.fog, new String[] { "Whether will render the fog.", "搂cRequires restarting to take effect", "", "Default: On" }));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     buttons.add(new GuiCycleButton.GuiBooleanButton(7, "Fast Hopper", config.fastHopper, new String[] { "Whether will improve the hopper.", "搂cRequires restarting to take effect", "", "Default in Vanilla: Off", "Default in BetterFps: On" }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     buttons.add(new GuiCycleButton.GuiBooleanButton(8, "Fast Beacon", config.fastBeacon, new String[] { "Whether will improve the beacon.", "搂cRequires restarting to take effect", "", "Default in Vanilla: Off", "Default in BetterFps: On" }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     return buttons;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  89 */     int x1 = this.width / 2 - 155;
/*  90 */     int x2 = this.width / 2 + 5;
/*     */     
/*  92 */     this.buttonList.clear();
/*  93 */     this.buttonList.add(new GuiButton(-1, x1, this.height - 27, 150, 20, I18n.format("gui.done", new Object[0])));
/*  94 */     this.buttonList.add(new GuiButton(-2, x2, this.height - 27, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*     */     
/*  96 */     List<GuiButton> buttons = initButtons();
/*     */     
/*  98 */     int y = 25;
/*  99 */     int lastId = 0;
/*     */     
/* 101 */     for (GuiButton button : buttons) {
/* 102 */       boolean first = (button.id % 2 != 0);
/* 103 */       boolean large = (button.id - 1 != lastId);
/* 104 */       button.xPosition = (first || large) ? x1 : x2;
/* 105 */       button.yPosition = y;
/* 106 */       button.width = large ? 310 : 150;
/* 107 */       button.height = 20;
/* 108 */       this.buttonList.add(button);
/* 109 */       if (!first || large) y += 25; 
/* 110 */       lastId = button.id;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 117 */     drawDefaultBackground();
/* 118 */     if (mouseY < this.fontRendererObj.FONT_HEIGHT + 14) {
/* 119 */       if (Mouse.isButtonDown(1)) {
/* 120 */         drawCenteredString(this.fontRendererObj, "This is not a button", this.width / 2, 7, 12632256);
/*     */       } else {
/* 122 */         drawCenteredString(this.fontRendererObj, "Hold right-click on a button for information", this.width / 2, 7, 12632256);
/*     */       } 
/*     */     } else {
/* 125 */       drawCenteredString(this.fontRendererObj, "BetterFps Options", this.width / 2, 7, 16777215);
/*     */     } 
/* 127 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 128 */     if (Mouse.isButtonDown(1)) {
/* 129 */       for (GuiButton button : this.buttonList) {
/* 130 */         if (button instanceof GuiCycleButton && button.isMouseOver()) {
/* 131 */           int y = mouseY + 5;
/*     */           
/* 133 */           String[] help = ((GuiCycleButton)button).getHelpText();
/* 134 */           int fontHeight = this.fontRendererObj.FONT_HEIGHT, i = 0;
/* 135 */           drawGradientRect(0, y, this.mc.displayWidth, y + fontHeight * help.length + 10, -1072689136, -804253680);
/* 136 */           for (String h : help) {
/* 137 */             if (!h.isEmpty()) this.fontRendererObj.drawString(h, 5, y + i * fontHeight + 5, 16777215); 
/* 138 */             i++;
/*     */           } 
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 148 */     super.actionPerformed(button);
/* 149 */     if (button instanceof GuiCycleButton) {
/* 150 */       ((GuiCycleButton)button).actionPerformed();
/* 151 */     } else if (button.id == -1) {
/*     */       
/* 153 */       boolean restart = false;
/* 154 */       BetterFpsConfig config = BetterFpsConfig.getConfig();
/*     */       
/* 156 */       GuiCycleButton algorithmButton = getCycleButton(2);
/* 157 */       String algorithm = algorithmButton.<String>getSelectedValue();
/* 158 */       if (!algorithm.equals(config.algorithm)) restart = true;
/*     */       
/* 160 */       config.algorithm = algorithm;
/*     */       
/* 162 */       GuiCycleButton updateButton = getCycleButton(3);
/* 163 */       config.updateChecker = ((Boolean)updateButton.<Boolean>getSelectedValue()).booleanValue();
/*     */       
/* 165 */       GuiCycleButton preallocateButton = getCycleButton(4);
/* 166 */       boolean preallocate = ((Boolean)preallocateButton.<Boolean>getSelectedValue()).booleanValue();
/* 167 */       if (preallocate != config.preallocateMemory) restart = true; 
/* 168 */       config.preallocateMemory = preallocate;
/*     */       
/* 170 */       GuiCycleButton boxRenderButton = getCycleButton(5);
/* 171 */       boolean boxRender = ((Boolean)boxRenderButton.<Boolean>getSelectedValue()).booleanValue();
/* 172 */       if (boxRender != config.fastBoxRender) restart = true; 
/* 173 */       config.fastBoxRender = boxRender;
/*     */       
/* 175 */       GuiCycleButton fogButton = getCycleButton(6);
/* 176 */       boolean fog = ((Boolean)fogButton.<Boolean>getSelectedValue()).booleanValue();
/* 177 */       if (fog != config.fog) restart = true; 
/* 178 */       config.fog = fog;
/*     */       
/* 180 */       GuiCycleButton hopperButton = getCycleButton(7);
/* 181 */       boolean fastHopper = ((Boolean)hopperButton.<Boolean>getSelectedValue()).booleanValue();
/* 182 */       if (fastHopper != config.fastHopper) restart = true; 
/* 183 */       config.fastHopper = fastHopper;
/*     */       
/* 185 */       GuiCycleButton beaconButton = getCycleButton(8);
/* 186 */       boolean fastBeacon = ((Boolean)beaconButton.<Boolean>getSelectedValue()).booleanValue();
/* 187 */       if (fastBeacon != config.fastBeacon) restart = true; 
/* 188 */       config.fastBeacon = fastBeacon;
/*     */       
/* 190 */       BetterFpsHelper.saveConfig();
/*     */       
/* 192 */       this.mc.displayGuiScreen(restart ? new GuiRestartDialog(this.parent) : this.parent);
/* 193 */     } else if (button.id == -2) {
/* 194 */       this.mc.displayGuiScreen(this.parent);
/*     */     } 
/*     */   }
/*     */   
/*     */   private GuiCycleButton getCycleButton(int id) {
/* 199 */     for (GuiButton button : this.buttonList) {
/* 200 */       if (button.id == id) {
/* 201 */         return (GuiCycleButton)button;
/*     */       }
/*     */     } 
/* 204 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 209 */     return true;
/*     */   }
/*     */   
/*     */   private static class AlgorithmButton extends GuiCycleButton {
/* 213 */     Process process = null;
/*     */     
/*     */     public <T> AlgorithmButton(int buttonId, String title, HashMap<T, String> values, T defaultValue, String[] helpLines) {
/* 216 */       super(buttonId, title, values, defaultValue, helpLines);
/*     */     }
/*     */     
/*     */     private String getJavaDir() {
/* 220 */       String separator = System.getProperty("file.separator");
/* 221 */       String path = System.getProperty("java.home") + separator + "bin" + separator;
/* 222 */       if (Util.getOSType() == Util.EnumOS.WINDOWS && (new File(path + "javaw.exe")).isFile()) {
/* 223 */         return path + "javaw.exe";
/*     */       }
/* 225 */       return path + "java";
/*     */     }
/*     */     
/*     */     private boolean isRunning() {
/*     */       try {
/* 230 */         this.process.exitValue();
/* 231 */         return false;
/* 232 */       } catch (Exception ex) {
/* 233 */         return true;
/*     */       } 
/*     */     }
/*     */     
/*     */     private void updateAlgorithm() {
/* 238 */       if (this.process != null && !isRunning()) {
/*     */         try {
/* 240 */           BufferedReader in = new BufferedReader(new InputStreamReader(this.process.getInputStream()));
/*     */           String line;
/* 242 */           while ((line = in.readLine()) != null) {
/* 243 */             if (BetterFpsHelper.helpers.containsKey(line)) {
/* 244 */               for (int i = 0; i < this.keys.size(); i++) {
/* 245 */                 if (this.keys.get(i).equals(line)) {
/* 246 */                   this.key = i;
/* 247 */                   updateTitle();
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             }
/*     */           } 
/* 253 */         } catch (Exception exception) {}
/*     */         
/* 255 */         this.process = null;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void drawButton(Minecraft mc, int mouseX, int mouseY) {
/* 261 */       updateAlgorithm();
/* 262 */       super.drawButton(mc, mouseX, mouseY);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shiftClick() {
/* 267 */       if (this.process != null && isRunning()) {
/* 268 */         return true;
/*     */       }
/* 270 */       List<String> args = new ArrayList<>();
/* 271 */       args.add(getJavaDir());
/* 272 */       args.add("-Dtester=" + (Minecraft.getMinecraft()).mcDataDir.getAbsolutePath());
/* 273 */       args.add("-cp");
/* 274 */       args.add(BetterFpsTweaker.class.getProtectionDomain().getCodeSource().getLocation().getFile());
/* 275 */       args.add("com.com.me.guichaguri.betterfps.installer.BetterFpsInstaller");
/*     */       try {
/* 277 */         this.process = (new ProcessBuilder(args)).start();
/* 278 */       } catch (Exception ex) {
/* 279 */         ex.printStackTrace();
/*     */       } 
/* 281 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\gui\GuiBetterFpsConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */