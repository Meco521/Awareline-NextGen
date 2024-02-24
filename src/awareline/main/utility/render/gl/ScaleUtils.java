/*    */ package awareline.main.utility.render.gl;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ 
/*    */ 
/*    */ public class ScaleUtils
/*    */ {
/*    */   public static int[] getScaledMouseCoordinates(Minecraft mc, int mouseX, int mouseY) {
/* 10 */     int x = mouseX;
/* 11 */     int y = mouseY;
/* 12 */     switch (mc.gameSettings.guiScale) {
/*    */       case 0:
/* 14 */         x <<= 1;
/* 15 */         y <<= 1;
/*    */         break;
/*    */       case 1:
/* 18 */         x = (int)(x * 0.5D);
/* 19 */         y = (int)(y * 0.5D);
/*    */         break;
/*    */       case 3:
/* 22 */         x = (int)(x * 1.5D);
/* 23 */         y = (int)(y * 1.5D); break;
/*    */     } 
/* 25 */     return new int[] { x, y };
/*    */   }
/*    */ 
/*    */   
/*    */   public static double[] getScaledMouseCoordinates(Minecraft mc, double mouseX, double mouseY) {
/* 30 */     double x = mouseX;
/* 31 */     double y = mouseY;
/* 32 */     switch (mc.gameSettings.guiScale) {
/*    */       case 0:
/* 34 */         x *= 2.0D;
/* 35 */         y *= 2.0D;
/*    */         break;
/*    */       case 1:
/* 38 */         x *= 0.5D;
/* 39 */         y *= 0.5D;
/*    */         break;
/*    */       case 3:
/* 42 */         x *= 1.5D;
/* 43 */         y *= 1.5D; break;
/*    */     } 
/* 45 */     return new double[] { x, y };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void scale(Minecraft mc) {
/* 51 */     switch (mc.gameSettings.guiScale) {
/*    */       case 0:
/* 53 */         GlStateManager.scale(0.5D, 0.5D, 0.5D);
/*    */         break;
/*    */       case 1:
/* 56 */         GlStateManager.scale(2.0F, 2.0F, 2.0F);
/*    */         break;
/*    */       case 3:
/* 59 */         GlStateManager.scale(0.6666666666666667D, 0.6666666666666667D, 0.6666666666666667D);
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\gl\ScaleUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */