/*    */ package awareline.main.mod.implement.globals.tweaker;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.File;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import javax.imageio.ImageIO;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.shader.Framebuffer;
/*    */ import net.minecraft.event.ClickEvent;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ASyncScreenShot
/*    */   implements Runnable
/*    */ {
/*    */   private final int width;
/*    */   private final int height;
/*    */   private final int[] pixelValues;
/*    */   private final Framebuffer framebuffer;
/*    */   private final File screenshotDirectory;
/*    */   
/*    */   ASyncScreenShot(int width, int height, int[] pixelValues, Framebuffer framebuffer, File screenshotDirectory) {
/* 28 */     this.width = width;
/* 29 */     this.height = height;
/* 30 */     this.pixelValues = pixelValues;
/* 31 */     this.framebuffer = framebuffer;
/* 32 */     this.screenshotDirectory = screenshotDirectory;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 38 */     processPixelValues(this.pixelValues, this.width, this.height);
/*    */ 
/*    */     
/* 41 */     File screenshot = getTimestampedPNGFileForDirectory(this.screenshotDirectory);
/*    */     try {
/*    */       BufferedImage image;
/* 44 */       if (OpenGlHelper.isFramebufferEnabled()) {
/* 45 */         image = new BufferedImage(this.framebuffer.framebufferWidth, this.framebuffer.framebufferHeight, 1);
/*    */ 
/*    */ 
/*    */         
/* 49 */         for (int tHeight = this.framebuffer.framebufferTextureHeight - this.framebuffer.framebufferHeight, heightSize = tHeight; tHeight < this.framebuffer.framebufferTextureHeight; tHeight++) {
/* 50 */           for (int widthSize = 0; widthSize < this.framebuffer.framebufferWidth; widthSize++) {
/* 51 */             image.setRGB(widthSize, tHeight - heightSize, this.pixelValues[tHeight * this.framebuffer.framebufferTextureWidth + widthSize]);
/*    */           }
/*    */         } 
/*    */       } else {
/* 55 */         image = new BufferedImage(this.width, this.height, 1);
/* 56 */         image.setRGB(0, 0, this.width, this.height, this.pixelValues, 0, this.width);
/*    */       } 
/*    */       
/* 59 */       ImageIO.write(image, "png", screenshot);
/* 60 */       ChatComponentText chatComponentText = new ChatComponentText(EnumChatFormatting.BLUE + "(" + EnumChatFormatting.GRAY + EnumChatFormatting.BOLD + "Tweaker" + EnumChatFormatting.BLUE + ") " + EnumChatFormatting.GRAY + "Screenshot saved to " + EnumChatFormatting.BOLD + screenshot.getName());
/* 61 */       chatComponentText.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, screenshot.getCanonicalPath()));
/* 62 */       (Minecraft.getMinecraft()).thePlayer.addChatMessage((IChatComponent)chatComponentText);
/* 63 */     } catch (Exception e) {
/* 64 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void processPixelValues(int[] pixels, int displayWidth, int displayHeight) {
/* 69 */     int[] xValues = new int[displayWidth];
/*    */     
/* 71 */     for (int yValues = displayHeight / 2, val = 0; val < yValues; val++) {
/* 72 */       System.arraycopy(pixels, val * displayWidth, xValues, 0, displayWidth);
/* 73 */       System.arraycopy(pixels, (displayHeight - 1 - val) * displayWidth, pixels, val * displayWidth, displayWidth);
/* 74 */       System.arraycopy(xValues, 0, pixels, (displayHeight - 1 - val) * displayWidth, displayWidth);
/*    */     } 
/*    */   }
/*    */   private static File getTimestampedPNGFileForDirectory(File gameDirectory) {
/*    */     File screenshot;
/* 79 */     String dateFormatting = (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date());
/* 80 */     int screenshotCount = 1;
/*    */ 
/*    */     
/*    */     while (true) {
/* 84 */       screenshot = new File(gameDirectory, dateFormatting + ((screenshotCount == 1) ? "" : ("_" + screenshotCount)) + ".png");
/* 85 */       if (!screenshot.exists()) {
/*    */         break;
/*    */       }
/*    */       
/* 89 */       screenshotCount++;
/*    */     } 
/*    */     
/* 92 */     return screenshot;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\globals\tweaker\ASyncScreenShot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */