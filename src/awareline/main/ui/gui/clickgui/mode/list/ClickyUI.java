/*     */ package awareline.main.ui.gui.clickgui.mode.list;
/*     */ 
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.mod.implement.visual.ctype.ClickGui;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import awareline.main.utility.math.MathUtil;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class ClickyUI
/*     */   extends GuiScreen {
/*  21 */   public static final ArrayList<Window> windows = Lists.newArrayList();
/*     */   
/*     */   public int scrollVelocity;
/*     */   
/*     */   public boolean close;
/*     */   private int shadowanim;
/*     */   
/*     */   public ClickyUI() {
/*  29 */     if (windows.isEmpty()) {
/*  30 */       float x = 40.0F;
/*  31 */       ModuleType[] arrmoduleType = ModuleType.values();
/*  32 */       int n = arrmoduleType.length - 1;
/*  33 */       int n2 = 0;
/*  34 */       while (n2 < n) {
/*  35 */         ModuleType c = arrmoduleType[n2];
/*  36 */         windows.add(new Window(c, x, 15.0F));
/*  37 */         x += 115.0F;
/*  38 */         n2++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public int globalAlpha; public float x; public float y;
/*     */   public float size;
/*     */   
/*     */   public float lerp(float a, float b, float c) {
/*  46 */     return a + c * (b - a);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  51 */     GlStateManager.pushMatrix();
/*  52 */     if (!this.close) {
/*  53 */       this.shadowanim = (int)AnimationUtil.moveUD(this.shadowanim, 180.0F, SimpleRender.processFPS(0.016F), SimpleRender.processFPS(0.014F));
/*     */     } else {
/*  55 */       this.shadowanim = (int)AnimationUtil.moveUD(this.shadowanim, 0.0F, SimpleRender.processFPS(0.016F), SimpleRender.processFPS(0.014F));
/*     */     } 
/*  57 */     int strictShadowAlpha = this.shadowanim;
/*  58 */     RenderUtil.drawGradientSidewaysV(0.0D, 0.0D, this.width, this.height, (new Color(0, 0, 0, 0))
/*  59 */         .getRGB(), (new Color(0, 0, 0, strictShadowAlpha)).getRGB());
/*  60 */     Minecraft.getMinecraft(); int FPS = Minecraft.getDebugFPS();
/*  61 */     if (FPS == 0) {
/*  62 */       FPS = 1;
/*     */     }
/*  64 */     if (!this.close) {
/*  65 */       this.size = lerp(this.size, 1.0F, SimpleRender.processFPS(0.016F));
/*  66 */       if (this.size >= 0.8F) {
/*  67 */         this.globalAlpha = (int)MathUtil.lerp(this.globalAlpha, 255.0F, SimpleRender.processFPS(0.01F));
/*     */       }
/*     */     } else {
/*  70 */       this.size = lerp(this.size, 0.0F, SimpleRender.processFPS(0.016F));
/*  71 */       this.globalAlpha = (int)(this.size * 255.0F);
/*  72 */       if (this.size < 0.01F) {
/*  73 */         this.mc.currentScreen = null;
/*  74 */         this.mc.mouseHelper.grabMouseCursor();
/*  75 */         this.mc.inGameHasFocus = true;
/*     */       } 
/*     */     } 
/*  78 */     GlStateManager.translate(this.x - this.x * this.size + this.width / 2.0F - this.width / 2.0F * this.size, this.y - this.y * this.size + this.height / 2.0F - this.height / 2.0F * this.size, 0.0F);
/*  79 */     GlStateManager.scale(this.size, this.size, 1.0F);
/*     */     
/*  81 */     Color Ranbow = HUD.getInstance.fade(new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue(), this.globalAlpha), 70, 25);
/*  82 */     drawGradientRect(0, 0, this.width, this.height, (new Color(0, 0, 0, 0)).getRGB(), Ranbow.getRGB());
/*     */     
/*  84 */     windows.forEach(w -> w.render(mouseX, mouseY, this.globalAlpha));
/*  85 */     GlStateManager.popMatrix();
/*  86 */     if (Mouse.hasWheel()) {
/*  87 */       int wheel = Mouse.getDWheel();
/*  88 */       this.scrollVelocity = (wheel < 0) ? -120 : ((wheel > 0) ? 120 : 0);
/*     */     } 
/*  90 */     windows.forEach(w -> w.mouseScroll(mouseX, mouseY, this.scrollVelocity));
/*  91 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  96 */     windows.forEach(w -> w.click(mouseX, mouseY, mouseButton));
/*     */     
/*  98 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 103 */     if (keyCode == 1) {
/* 104 */       ClickGui.getInstance.setEnabled(false);
/* 105 */       this.close = true;
/* 106 */       if (this.mc.entityRenderer.theShaderGroup != null) {
/* 107 */         this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
/* 108 */         this.mc.entityRenderer.theShaderGroup = null;
/*     */       } 
/*     */     } else {
/* 111 */       super.keyTyped(typedChar, keyCode);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void initGui() {
/* 116 */     super.initGui();
/* 117 */     this.globalAlpha = this.shadowanim = 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\list\ClickyUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */