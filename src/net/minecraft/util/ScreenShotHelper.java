/*     */ package net.minecraft.util;
/*     */ 
/*     */ import awareline.main.mod.implement.globals.ASyncScreenShot;
/*     */ import awareline.main.mod.implement.globals.tweaker.ScreenShotHook;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.nio.IntBuffer;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.event.ClickEvent;
/*     */ import net.minecraft.src.Config;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScreenShotHelper
/*     */ {
/*  29 */   private static final Logger logger = LogManager.getLogger();
/*  30 */   private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IntBuffer pixelBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] pixelValues;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IChatComponent saveScreenshot(File gameDirectory, int width, int height, Framebuffer buffer) {
/*  46 */     return saveScreenshot(gameDirectory, (String)null, width, height, buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IChatComponent saveScreenshot(File gameDirectory, String screenshotName, int width, int height, Framebuffer buffer) {
/*  55 */     if (ASyncScreenShot.getInstance.isEnabled()) {
/*  56 */       return ScreenShotHook.handleScreenshot(gameDirectory, screenshotName, width, height, buffer);
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  61 */       File file1 = new File(gameDirectory, "screenshots");
/*  62 */       file1.mkdir();
/*  63 */       Minecraft minecraft = Minecraft.getMinecraft();
/*  64 */       int i = (Config.getGameSettings()).guiScale;
/*  65 */       ScaledResolution scaledresolution = new ScaledResolution(minecraft);
/*  66 */       int j = scaledresolution.getScaleFactor();
/*  67 */       int k = Config.getScreenshotSize();
/*  68 */       boolean flag = (OpenGlHelper.isFramebufferEnabled() && k > 1);
/*     */       
/*  70 */       if (flag) {
/*     */         
/*  72 */         (Config.getGameSettings()).guiScale = j * k;
/*  73 */         resize(width * k, height * k);
/*  74 */         GlStateManager.pushMatrix();
/*  75 */         GlStateManager.clear(16640);
/*  76 */         minecraft.getFramebuffer().bindFramebuffer(true);
/*  77 */         minecraft.entityRenderer.updateCameraAndRender(Config.renderPartialTicks, System.nanoTime());
/*     */       } 
/*     */       
/*  80 */       if (OpenGlHelper.isFramebufferEnabled()) {
/*     */         
/*  82 */         width = buffer.framebufferTextureWidth;
/*  83 */         height = buffer.framebufferTextureHeight;
/*     */       } 
/*     */       
/*  86 */       int l = width * height;
/*     */       
/*  88 */       if (pixelBuffer == null || pixelBuffer.capacity() < l) {
/*     */         
/*  90 */         pixelBuffer = BufferUtils.createIntBuffer(l);
/*  91 */         pixelValues = new int[l];
/*     */       } 
/*     */       
/*  94 */       GL11.glPixelStorei(3333, 1);
/*  95 */       GL11.glPixelStorei(3317, 1);
/*  96 */       pixelBuffer.clear();
/*     */       
/*  98 */       if (OpenGlHelper.isFramebufferEnabled()) {
/*     */         
/* 100 */         GlStateManager.bindTexture(buffer.framebufferTexture);
/* 101 */         GL11.glGetTexImage(3553, 0, 32993, 33639, pixelBuffer);
/*     */       }
/*     */       else {
/*     */         
/* 105 */         GL11.glReadPixels(0, 0, width, height, 32993, 33639, pixelBuffer);
/*     */       } 
/*     */       
/* 108 */       pixelBuffer.get(pixelValues);
/* 109 */       TextureUtil.processPixelValues(pixelValues, width, height);
/* 110 */       BufferedImage bufferedimage = null;
/*     */       
/* 112 */       if (OpenGlHelper.isFramebufferEnabled()) {
/*     */         
/* 114 */         bufferedimage = new BufferedImage(buffer.framebufferWidth, buffer.framebufferHeight, 1);
/* 115 */         int i1 = buffer.framebufferTextureHeight - buffer.framebufferHeight;
/*     */         
/* 117 */         for (int j1 = i1; j1 < buffer.framebufferTextureHeight; j1++)
/*     */         {
/* 119 */           for (int k1 = 0; k1 < buffer.framebufferWidth; k1++)
/*     */           {
/* 121 */             bufferedimage.setRGB(k1, j1 - i1, pixelValues[j1 * buffer.framebufferTextureWidth + k1]);
/*     */           }
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 127 */         bufferedimage = new BufferedImage(width, height, 1);
/* 128 */         bufferedimage.setRGB(0, 0, width, height, pixelValues, 0, width);
/*     */       } 
/*     */       
/* 131 */       if (flag) {
/*     */         
/* 133 */         minecraft.getFramebuffer().unbindFramebuffer();
/* 134 */         GlStateManager.popMatrix();
/* 135 */         (Config.getGameSettings()).guiScale = i;
/* 136 */         resize(width, height);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 141 */       if (screenshotName == null) {
/*     */         
/* 143 */         file2 = getTimestampedPNGFileForDirectory(file1);
/*     */       }
/*     */       else {
/*     */         
/* 147 */         file2 = new File(file1, screenshotName);
/*     */       } 
/*     */       
/* 150 */       File file2 = file2.getCanonicalFile();
/* 151 */       ImageIO.write(bufferedimage, "png", file2);
/* 152 */       IChatComponent ichatcomponent = new ChatComponentText(file2.getName());
/* 153 */       ichatcomponent.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file2.getAbsolutePath()));
/* 154 */       ichatcomponent.getChatStyle().setUnderlined(Boolean.valueOf(true));
/* 155 */       return new ChatComponentTranslation("screenshot.success", new Object[] { ichatcomponent });
/*     */     }
/* 157 */     catch (Exception exception) {
/*     */       
/* 159 */       logger.warn("Couldn't save screenshot", exception);
/* 160 */       return new ChatComponentTranslation("screenshot.failure", new Object[] { exception.getMessage() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static File getTimestampedPNGFileForDirectory(File gameDirectory) {
/* 172 */     String s = dateFormat.format(new Date()).toString();
/* 173 */     int i = 1;
/*     */ 
/*     */     
/*     */     while (true) {
/* 177 */       File file1 = new File(gameDirectory, s + ((i == 1) ? "" : ("_" + i)) + ".png");
/*     */       
/* 179 */       if (!file1.exists())
/*     */       {
/* 181 */         return file1;
/*     */       }
/*     */       
/* 184 */       i++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void resize(int p_resize_0_, int p_resize_1_) {
/* 190 */     Minecraft minecraft = Minecraft.getMinecraft();
/* 191 */     minecraft.displayWidth = Math.max(1, p_resize_0_);
/* 192 */     minecraft.displayHeight = Math.max(1, p_resize_1_);
/*     */     
/* 194 */     if (minecraft.currentScreen != null) {
/*     */       
/* 196 */       ScaledResolution scaledresolution = new ScaledResolution(minecraft);
/* 197 */       minecraft.currentScreen.onResize(minecraft, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight());
/*     */     } 
/*     */     
/* 200 */     updateFramebufferSize();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void updateFramebufferSize() {
/* 205 */     Minecraft minecraft = Minecraft.getMinecraft();
/* 206 */     minecraft.getFramebuffer().createBindFramebuffer(minecraft.displayWidth, minecraft.displayHeight);
/*     */     
/* 208 */     if (minecraft.entityRenderer != null)
/*     */     {
/* 210 */       minecraft.entityRenderer.updateShaderGroupSize(minecraft.displayWidth, minecraft.displayHeight);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\ScreenShotHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */