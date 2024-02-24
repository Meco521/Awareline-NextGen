/*    */ package awareline.main.mod.implement.globals.tweaker;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.nio.IntBuffer;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.shader.Framebuffer;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import org.lwjgl.BufferUtils;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScreenShotHook
/*    */ {
/*    */   private static IntBuffer pixelBuffer;
/*    */   private static int[] pixelValues;
/*    */   
/*    */   public static IChatComponent handleScreenshot(File gameDirectory, String screenshotName, int width, int height, Framebuffer buffer) {
/* 24 */     File screenshotDirectory = new File((Minecraft.getMinecraft()).mcDataDir, "screenshots");
/*    */     
/* 26 */     if (!screenshotDirectory.exists()) {
/* 27 */       screenshotDirectory.mkdir();
/*    */     }
/*    */     
/* 30 */     if (OpenGlHelper.isFramebufferEnabled()) {
/* 31 */       width = buffer.framebufferTextureWidth;
/* 32 */       height = buffer.framebufferTextureHeight;
/*    */     } 
/*    */     
/* 35 */     int imageScale = width * height;
/* 36 */     if (pixelBuffer == null || pixelBuffer.capacity() < imageScale) {
/* 37 */       pixelBuffer = BufferUtils.createIntBuffer(imageScale);
/* 38 */       pixelValues = new int[imageScale];
/*    */     } 
/*    */     
/* 41 */     GL11.glPixelStorei(3333, 1);
/* 42 */     GL11.glPixelStorei(3317, 1);
/* 43 */     pixelBuffer.clear();
/*    */     
/* 45 */     if (OpenGlHelper.isFramebufferEnabled()) {
/* 46 */       GlStateManager.bindTexture(buffer.framebufferTexture);
/* 47 */       GL11.glGetTexImage(3553, 0, 32993, 33639, pixelBuffer);
/*    */     } else {
/* 49 */       GL11.glReadPixels(0, 0, width, height, 32993, 33639, pixelBuffer);
/*    */     } 
/*    */     
/* 52 */     pixelBuffer.get(pixelValues);
/*    */     
/* 54 */     (new Thread(new ASyncScreenShot(width, height, pixelValues, Minecraft.getMinecraft().getFramebuffer(), screenshotDirectory))).start();
/* 55 */     return (IChatComponent)new ChatComponentText(EnumChatFormatting.BLUE + "(" + EnumChatFormatting.GRAY + EnumChatFormatting.BOLD + "Tweaker" + EnumChatFormatting.BLUE + ") " + EnumChatFormatting.GRAY + "Capturing screenshot.");
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\globals\tweaker\ScreenShotHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */