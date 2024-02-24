/*    */ package awareline.main.ui.gui.clickgui.mode.novoline;
/*    */ 
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.implement.visual.ctype.ClickGui;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClickyUI
/*    */   extends GuiScreen
/*    */ {
/* 22 */   public static final ArrayList<Window> windows = Lists.newArrayList();
/*    */   public int scrollVelocity;
/*    */   public static boolean binding;
/*    */   
/*    */   public ClickyUI() {
/* 27 */     if (windows.isEmpty()) {
/* 28 */       float x = 50.0F;
/* 29 */       ModuleType[] arrmoduleType = ModuleType.values();
/* 30 */       int n = arrmoduleType.length;
/* 31 */       int n2 = 0;
/* 32 */       while (n2 < n) {
/* 33 */         ModuleType c = arrmoduleType[n2];
/* 34 */         windows.add(new Window(c, x, 15.0F));
/* 35 */         x += 115.0F;
/* 36 */         n2++;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void initGui() {
/* 42 */     super.initGui();
/* 43 */     if (!((Boolean)ClickGui.disableBlur.get()).booleanValue() && 
/* 44 */       OpenGlHelper.shadersSupported && this.mc.thePlayer != null) {
/* 45 */       if (this.mc.entityRenderer.theShaderGroup != null) {
/* 46 */         this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
/*    */       }
/* 48 */       this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 55 */     GlStateManager.pushMatrix();
/* 56 */     windows.forEach(w2 -> w2.render(mouseX, mouseY));
/* 57 */     GlStateManager.popMatrix();
/* 58 */     if (Mouse.hasWheel()) {
/* 59 */       int wheel = Mouse.getDWheel();
/* 60 */       this.scrollVelocity = (wheel < 0) ? -120 : ((wheel > 0) ? 130 : 0);
/*    */     } 
/* 62 */     windows.forEach(w2 -> w2.mouseScroll(mouseX, mouseY, this.scrollVelocity));
/* 63 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 68 */     windows.forEach(w2 -> w2.click(mouseX, mouseY, mouseButton));
/* 69 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void keyTyped(char typedChar, int keyCode) {
/* 74 */     if (keyCode == 1 && !binding) {
/* 75 */       if (OpenGlHelper.shadersSupported && Minecraft.getMinecraft().getRenderViewEntity() instanceof net.minecraft.entity.player.EntityPlayer && 
/* 76 */         this.mc.entityRenderer.theShaderGroup != null) {
/* 77 */         this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
/* 78 */         this.mc.entityRenderer.theShaderGroup = null;
/*    */       } 
/*    */       
/* 81 */       this.mc.displayGuiScreen(null);
/*    */       return;
/*    */     } 
/* 84 */     windows.forEach(w2 -> w2.key(typedChar, keyCode));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\novoline\ClickyUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */