/*    */ package awareline.main.ui.gui.clickgui.mode.astolfo;
/*    */ 
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.implement.visual.ctype.ClickGui;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.awt.Color;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.input.Mouse;
/*    */ import org.lwjgl.opengl.Display;
/*    */ 
/*    */ public class asClickgui
/*    */   extends GuiScreen {
/* 19 */   public static final ArrayList<Window> windows = Lists.newArrayList();
/*    */   public int scrollVelocity;
/*    */   public static boolean binding;
/*    */   
/*    */   public asClickgui() {
/* 24 */     if (windows.isEmpty()) {
/* 25 */       int x = 45;
/* 26 */       ModuleType[] arrmoduleType = ModuleType.values();
/* 27 */       int n = arrmoduleType.length;
/* 28 */       int n2 = 0;
/* 29 */       while (n2 < n) {
/* 30 */         ModuleType c = arrmoduleType[n2];
/* 31 */         windows.add(new Window(c, x, 10));
/* 32 */         x += 115;
/* 33 */         n2++;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 40 */     Gui.drawRect(0, 0, Display.getWidth(), Display.getHeight(), (new Color(0, 0, 0, 150)).getRGB());
/* 41 */     GlStateManager.pushMatrix();
/* 42 */     windows.forEach(w -> w.render(mouseX, mouseY));
/* 43 */     GlStateManager.popMatrix();
/* 44 */     if (Mouse.hasWheel()) {
/* 45 */       int wheel = Mouse.getDWheel();
/* 46 */       this.scrollVelocity = (wheel < 0) ? -120 : ((wheel > 0) ? 120 : 0);
/*    */     } 
/* 48 */     windows.forEach(w -> w.mouseScroll(mouseX, mouseY, this.scrollVelocity));
/* 49 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 54 */     windows.forEach(w -> w.click(mouseX, mouseY, mouseButton));
/* 55 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void keyTyped(char typedChar, int keyCode) {
/* 60 */     if (keyCode == 1 && !binding) {
/* 61 */       this.mc.displayGuiScreen(null);
/*    */       return;
/*    */     } 
/* 64 */     windows.forEach(w -> w.key(typedChar, keyCode));
/*    */   }
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 69 */     if (((Boolean)ClickGui.Blur.getValue()).booleanValue() && 
/* 70 */       OpenGlHelper.shadersSupported && this.mc.thePlayer != null) {
/* 71 */       if (this.mc.entityRenderer.theShaderGroup != null) {
/* 72 */         this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
/*    */       }
/* 74 */       this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onGuiClosed() {
/* 81 */     this.mc.entityRenderer.stopUseShader();
/* 82 */     this.mc.entityRenderer.isShaderActive();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\astolfo\asClickgui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */