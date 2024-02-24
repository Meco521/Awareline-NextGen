/*    */ package awareline.main.ui.gui.clickgui.mode.astolfo;
/*    */ 
/*    */ import awareline.main.utility.render.GuiRenderUtils;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class Limitation {
/*    */   public final float startX;
/*    */   public final float startY;
/*    */   public final float endX;
/*    */   public final float endY;
/*    */   
/*    */   public Limitation(float x1, float y1, float x2, float y2) {
/* 14 */     this.startX = x1;
/* 15 */     this.startY = y1;
/* 16 */     this.endX = x2;
/* 17 */     this.endY = y2;
/*    */   }
/*    */   
/*    */   public void cut() {
/* 21 */     GuiRenderUtils.doGlScissor((int)this.startX, (int)this.startY, this.endX - this.startX, this.endY - this.startY, 2.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void doGlScissor(float x, float y, float width, float height) {
/* 27 */     Minecraft mc = Minecraft.getMinecraft();
/* 28 */     int scaleFactor = 1;
/* 29 */     float k = mc.gameSettings.guiScale;
/* 30 */     if (k == 0.0F) {
/* 31 */       k = 1000.0F;
/*    */     }
/* 33 */     while (scaleFactor < k && mc.displayWidth / (scaleFactor + 1) >= 320 && mc.displayHeight / (scaleFactor + 1) >= 240)
/*    */     {
/* 35 */       scaleFactor++;
/*    */     }
/* 37 */     GL11.glScissor((int)(x * scaleFactor), (int)(mc.displayHeight - (y + height) * scaleFactor), (int)(width * scaleFactor), (int)(height * scaleFactor));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\clickgui\mode\astolfo\Limitation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */