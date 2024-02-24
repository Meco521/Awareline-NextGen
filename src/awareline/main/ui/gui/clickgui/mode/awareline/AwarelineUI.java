/*     */ package awareline.main.ui.gui.clickgui.mode.awareline;
/*     */ 
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.ctype.ClickGui;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class AwarelineUI
/*     */   extends GuiScreen
/*     */ {
/*  18 */   public static final ArrayList<Window> windows = Lists.newArrayList(); public int scrollVelocity;
/*     */   public static boolean binding;
/*     */   private int shadowanim;
/*     */   
/*     */   public AwarelineUI() {
/*  23 */     if (windows.isEmpty()) {
/*  24 */       float x = 25.0F;
/*  25 */       ModuleType[] arrmoduleType = ModuleType.values();
/*  26 */       int n = arrmoduleType.length - 1;
/*  27 */       int n2 = 0;
/*  28 */       while (n2 < n) {
/*  29 */         ModuleType c = arrmoduleType[n2];
/*  30 */         windows.add(new Window(c, x, 15.0F));
/*  31 */         x += 130.0F;
/*  32 */         n2++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public boolean close; public float x; public float y; public float size;
/*     */   public void initGui() {
/*  38 */     super.initGui();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  45 */     GlStateManager.pushMatrix();
/*  46 */     if (!this.close) {
/*  47 */       this.shadowanim = (int)AnimationUtil.moveUD(this.shadowanim, 100.0F, SimpleRender.processFPS(0.016F), 
/*  48 */           SimpleRender.processFPS(0.014F));
/*     */     } else {
/*  50 */       this.shadowanim = (int)AnimationUtil.moveUD(this.shadowanim, 0.0F, SimpleRender.processFPS(0.016F), 
/*  51 */           SimpleRender.processFPS(0.014F));
/*     */     } 
/*  53 */     drawRect(0, 0, this.width, this.height, (new Color(0, 0, 0, this.shadowanim)).getRGB());
/*  54 */     if (!this.close) {
/*  55 */       this.size = lerp(this.size, 1.0F, SimpleRender.processFPS(0.016F));
/*     */     } else {
/*  57 */       this.size = lerp(this.size, 0.0F, SimpleRender.processFPS(0.016F));
/*  58 */       if (this.size < 0.01F) {
/*  59 */         this.mc.currentScreen = null;
/*  60 */         this.mc.mouseHelper.grabMouseCursor();
/*  61 */         this.mc.inGameHasFocus = true;
/*     */       } 
/*     */     } 
/*  64 */     GlStateManager.translate(this.x - this.x * this.size + this.width / 2.0F - this.width / 2.0F * this.size, this.y - this.y * this.size + this.height / 2.0F - this.height / 2.0F * this.size, 0.0F);
/*  65 */     GlStateManager.scale(this.size, this.size, 1.0F);
/*  66 */     windows.forEach(w2 -> w2.render(mouseX, mouseY));
/*  67 */     GlStateManager.popMatrix();
/*  68 */     if (Mouse.hasWheel()) {
/*  69 */       int wheel = Mouse.getDWheel();
/*  70 */       this.scrollVelocity = (wheel < 0) ? -120 : ((wheel > 0) ? 130 : 0);
/*     */     } 
/*  72 */     windows.forEach(w2 -> w2.mouseScroll(mouseX, mouseY, this.scrollVelocity));
/*  73 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  78 */     windows.forEach(w2 -> w2.click(mouseX, mouseY, mouseButton));
/*  79 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float lerp(float a, float b, float c) {
/*  87 */     return a + c * (b - a);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  92 */     if (keyCode == 1) {
/*  93 */       ClickGui.getInstance.setEnabled(false);
/*  94 */       this.close = true;
/*  95 */       if (this.mc.entityRenderer.theShaderGroup != null) {
/*  96 */         this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
/*  97 */         this.mc.entityRenderer.theShaderGroup = null;
/*     */       } 
/*     */     } else {
/* 100 */       super.keyTyped(typedChar, keyCode);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\awareline\AwarelineUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */