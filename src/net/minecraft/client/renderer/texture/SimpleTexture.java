/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import net.minecraft.client.resources.IResource;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.client.resources.data.TextureMetadataSection;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.optifine.EmissiveTextures;
/*    */ import net.optifine.shaders.ShadersTex;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class SimpleTexture
/*    */   extends AbstractTexture
/*    */ {
/* 19 */   private static final Logger logger = LogManager.getLogger();
/*    */   
/*    */   protected final ResourceLocation textureLocation;
/*    */   public ResourceLocation locationEmissive;
/*    */   public boolean isEmissive;
/*    */   
/*    */   public SimpleTexture(ResourceLocation textureResourceLocation) {
/* 26 */     this.textureLocation = textureResourceLocation;
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadTexture(IResourceManager resourceManager) throws IOException {
/* 31 */     deleteGlTexture();
/* 32 */     InputStream inputstream = null;
/*    */ 
/*    */     
/*    */     try {
/* 36 */       IResource iresource = resourceManager.getResource(this.textureLocation);
/* 37 */       inputstream = iresource.getInputStream();
/* 38 */       BufferedImage bufferedimage = TextureUtil.readBufferedImage(inputstream);
/* 39 */       boolean flag = false;
/* 40 */       boolean flag1 = false;
/*    */       
/* 42 */       if (iresource.hasMetadata()) {
/*    */         
/*    */         try {
/*    */           
/* 46 */           TextureMetadataSection texturemetadatasection = (TextureMetadataSection)iresource.getMetadata("texture");
/*    */           
/* 48 */           if (texturemetadatasection != null)
/*    */           {
/* 50 */             flag = texturemetadatasection.getTextureBlur();
/* 51 */             flag1 = texturemetadatasection.getTextureClamp();
/*    */           }
/*    */         
/* 54 */         } catch (RuntimeException runtimeexception) {
/*    */           
/* 56 */           logger.warn("Failed reading metadata of: " + this.textureLocation, runtimeexception);
/*    */         } 
/*    */       }
/*    */       
/* 60 */       if (Config.isShaders()) {
/*    */         
/* 62 */         ShadersTex.loadSimpleTexture(getGlTextureId(), bufferedimage, flag, flag1, resourceManager, this.textureLocation, getMultiTexID());
/*    */       }
/*    */       else {
/*    */         
/* 66 */         TextureUtil.uploadTextureImageAllocate(getGlTextureId(), bufferedimage, flag, flag1);
/*    */       } 
/*    */       
/* 69 */       if (EmissiveTextures.isActive())
/*    */       {
/* 71 */         EmissiveTextures.loadTexture(this.textureLocation, this);
/*    */       }
/*    */     }
/*    */     finally {
/*    */       
/* 76 */       if (inputstream != null)
/*    */       {
/* 78 */         inputstream.close();
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\texture\SimpleTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */