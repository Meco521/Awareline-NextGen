/*    */ package awareline.main.ui.api;
/*    */ 
/*    */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*    */ import net.minecraft.client.renderer.texture.ITextureObject;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class StreamTexture implements ClientTexture<StreamTexture> {
/*    */   private final ResourceLocation resourceLocation;
/*    */   private final InputStream inputStream;
/*    */   
/* 15 */   public ResourceLocation getResourceLocation() { return this.resourceLocation; } private DynamicTexture dynamicTexture; private static int count; public InputStream getInputStream() {
/* 16 */     return this.inputStream;
/*    */   } public DynamicTexture getDynamicTexture() {
/* 18 */     return this.dynamicTexture;
/*    */   }
/*    */ 
/*    */   
/*    */   public StreamTexture(InputStream inputStream, String name) {
/* 23 */     this.inputStream = inputStream;
/*    */     try {
/* 25 */       this.dynamicTexture = new DynamicTexture(TextureUtil.readBufferedImage(inputStream));
/* 26 */     } catch (IOException e) {
/* 27 */       e.printStackTrace();
/*    */     } 
/* 29 */     this.resourceLocation = new ResourceLocation(String.format("target_stream_texture/%s_%d", new Object[] { name, Integer.valueOf(count) }));
/* 30 */     count++;
/*    */   }
/*    */ 
/*    */   
/*    */   public StreamTexture load() {
/* 35 */     Minecraft.getMinecraft().getTextureManager().loadTexture(this.resourceLocation, (ITextureObject)this.dynamicTexture);
/* 36 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public StreamTexture bind() {
/* 41 */     Minecraft.getMinecraft().getTextureManager().bindTexture(this.resourceLocation);
/* 42 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public StreamTexture draw(double x, double y, double width, double height) {
/* 47 */     draw(x, y, width, height, -1);
/* 48 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public StreamTexture draw(double x, double y, double width, double height, int color) {
/* 53 */     RenderUtil.drawImage(this.resourceLocation, x, y, width, height, color);
/* 54 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\api\StreamTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */