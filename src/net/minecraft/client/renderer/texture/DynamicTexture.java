/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DynamicTexture
/*    */   extends AbstractTexture
/*    */ {
/*    */   private final int[] dynamicTextureData;
/*    */   private final int width;
/*    */   private final int height;
/*    */   
/*    */   public DynamicTexture(BufferedImage bufferedImage) {
/* 19 */     this(bufferedImage.getWidth(), bufferedImage.getHeight());
/* 20 */     if (this.dynamicTextureData != null) {
/* 21 */       bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), this.dynamicTextureData, 0, bufferedImage.getWidth());
/* 22 */       updateDynamicTexture();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public DynamicTexture(int textureWidth, int textureHeight) {
/* 28 */     this.width = textureWidth;
/* 29 */     this.height = textureHeight;
/* 30 */     this.dynamicTextureData = new int[textureWidth * textureHeight];
/* 31 */     TextureUtil.allocateTexture(getGlTextureId(), textureWidth, textureHeight);
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadTexture(IResourceManager resourceManager) {}
/*    */ 
/*    */   
/*    */   public void updateDynamicTexture() {
/* 39 */     TextureUtil.uploadTexture(getGlTextureId(), this.dynamicTextureData, this.width, this.height);
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getTextureData() {
/* 44 */     return this.dynamicTextureData;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\texture\DynamicTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */