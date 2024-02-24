/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.awt.image.ImageObserver;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.optifine.shaders.ShadersTex;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class LayeredTexture
/*    */   extends AbstractTexture
/*    */ {
/* 19 */   private static final Logger logger = LogManager.getLogger();
/*    */   
/*    */   public final List<String> layeredTextureNames;
/*    */   private ResourceLocation textureLocation;
/*    */   
/*    */   public LayeredTexture(String... textureNames) {
/* 25 */     this.layeredTextureNames = Lists.newArrayList((Object[])textureNames);
/*    */     
/* 27 */     if (textureNames.length > 0 && textureNames[0] != null)
/*    */     {
/* 29 */       this.textureLocation = new ResourceLocation(textureNames[0]);
/*    */     }
/*    */   }
/*    */   
/*    */   public void loadTexture(IResourceManager resourceManager) {
/* 34 */     deleteGlTexture();
/* 35 */     BufferedImage bufferedimage = null;
/*    */ 
/*    */     
/*    */     try {
/* 39 */       for (String s : this.layeredTextureNames) {
/*    */         
/* 41 */         if (s != null)
/*    */         {
/* 43 */           InputStream inputstream = resourceManager.getResource(new ResourceLocation(s)).getInputStream();
/* 44 */           BufferedImage bufferedimage1 = TextureUtil.readBufferedImage(inputstream);
/*    */           
/* 46 */           if (bufferedimage == null)
/*    */           {
/* 48 */             bufferedimage = new BufferedImage(bufferedimage1.getWidth(), bufferedimage1.getHeight(), 2);
/*    */           }
/*    */           
/* 51 */           bufferedimage.getGraphics().drawImage(bufferedimage1, 0, 0, (ImageObserver)null);
/*    */         }
/*    */       
/*    */       } 
/* 55 */     } catch (IOException ioexception) {
/*    */       
/* 57 */       logger.error("Couldn't load layered image", ioexception);
/*    */       
/*    */       return;
/*    */     } 
/* 61 */     if (Config.isShaders()) {
/*    */       
/* 63 */       ShadersTex.loadSimpleTexture(getGlTextureId(), bufferedimage, false, false, resourceManager, this.textureLocation, getMultiTexID());
/*    */     }
/*    */     else {
/*    */       
/* 67 */       TextureUtil.uploadTextureImage(getGlTextureId(), bufferedimage);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\texture\LayeredTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */