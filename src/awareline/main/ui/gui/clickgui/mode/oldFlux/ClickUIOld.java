/*    */ package awareline.main.ui.gui.clickgui.mode.oldFlux;
/*    */ 
/*    */ import awareline.main.mod.ModuleType;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ public class ClickUIOld
/*    */   extends GuiScreen
/*    */ {
/* 14 */   public static final ArrayList<Window> windows = Lists.newArrayList();
/*    */   public double opacity;
/* 16 */   public static int alpha = 255;
/*    */   
/*    */   public int scrollVelocity;
/*    */   public static boolean binding;
/*    */   
/*    */   public ClickUIOld() {
/* 22 */     if (windows.isEmpty()) {
/* 23 */       int x2 = 5;
/* 24 */       ModuleType[] moduleTypes = ModuleType.values();
/* 25 */       int n2 = moduleTypes.length;
/* 26 */       int n22 = 0;
/* 27 */       while (n22 < n2) {
/* 28 */         ModuleType c2 = moduleTypes[n22];
/* 29 */         windows.add(new Window(c2, x2, 5, this));
/* 30 */         x2 += 95;
/* 31 */         n22++;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean doesGuiPauseGame() {
/* 38 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 43 */     this.opacity = (this.opacity + 10.0D < 200.0D) ? (this.opacity += 10.0D) : 200.0D;
/* 44 */     GlStateManager.pushMatrix();
/* 45 */     windows.forEach(w2 -> w2.render(mouseX, mouseY));
/* 46 */     GlStateManager.popMatrix();
/* 47 */     if (Mouse.hasWheel()) {
/* 48 */       int wheel = Mouse.getDWheel();
/* 49 */       this.scrollVelocity = (wheel < 0) ? -120 : ((wheel > 0) ? 130 : 0);
/*    */     } 
/* 51 */     windows.forEach(w2 -> w2.mouseScroll(mouseX, mouseY, this.scrollVelocity));
/* 52 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateScreen() {
/* 57 */     super.updateScreen();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 62 */     windows.forEach(w2 -> w2.click(mouseX, mouseY, mouseButton));
/* 63 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\oldFlux\ClickUIOld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */