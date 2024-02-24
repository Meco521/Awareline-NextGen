/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.texture.SimpleTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.http.HttpPipeline;
/*     */ import net.optifine.http.HttpRequest;
/*     */ import net.optifine.http.HttpResponse;
/*     */ import net.optifine.player.CapeImageBuffer;
/*     */ import net.optifine.shaders.ShadersTex;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class ThreadDownloadImageData
/*     */   extends SimpleTexture
/*     */ {
/*  31 */   static final Logger logger = LogManager.getLogger();
/*  32 */   private static final AtomicInteger threadDownloadCounter = new AtomicInteger(0);
/*     */   final File cacheFile;
/*     */   final String imageUrl;
/*     */   final IImageBuffer imageBuffer;
/*     */   private BufferedImage bufferedImage;
/*     */   private Thread imageThread;
/*     */   private boolean textureUploaded;
/*  39 */   public Boolean imageFound = null;
/*     */   
/*     */   public boolean pipeline = false;
/*     */   
/*     */   public ThreadDownloadImageData(File cacheFileIn, String imageUrlIn, ResourceLocation textureResourceLocation, IImageBuffer imageBufferIn) {
/*  44 */     super(textureResourceLocation);
/*  45 */     this.cacheFile = cacheFileIn;
/*  46 */     this.imageUrl = imageUrlIn;
/*  47 */     this.imageBuffer = imageBufferIn;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkTextureUploaded() {
/*  52 */     if (!this.textureUploaded && this.bufferedImage != null) {
/*     */       
/*  54 */       this.textureUploaded = true;
/*     */       
/*  56 */       if (this.textureLocation != null)
/*     */       {
/*  58 */         deleteGlTexture();
/*     */       }
/*     */       
/*  61 */       if (Config.isShaders()) {
/*     */         
/*  63 */         ShadersTex.loadSimpleTexture(super.getGlTextureId(), this.bufferedImage, false, false, Config.getResourceManager(), this.textureLocation, getMultiTexID());
/*     */       }
/*     */       else {
/*     */         
/*  67 */         TextureUtil.uploadTextureImage(super.getGlTextureId(), this.bufferedImage);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGlTextureId() {
/*  74 */     checkTextureUploaded();
/*  75 */     return super.getGlTextureId();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBufferedImage(BufferedImage bufferedImageIn) {
/*  80 */     this.bufferedImage = bufferedImageIn;
/*     */     
/*  82 */     if (this.imageBuffer != null)
/*     */     {
/*  84 */       this.imageBuffer.skinAvailable();
/*     */     }
/*     */     
/*  87 */     this.imageFound = Boolean.valueOf((this.bufferedImage != null));
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadTexture(IResourceManager resourceManager) throws IOException {
/*  92 */     if (this.bufferedImage == null && this.textureLocation != null)
/*     */     {
/*  94 */       super.loadTexture(resourceManager);
/*     */     }
/*     */     
/*  97 */     if (this.imageThread == null)
/*     */     {
/*  99 */       if (this.cacheFile != null && this.cacheFile.isFile()) {
/*     */         
/* 101 */         logger.debug("Loading http texture from local cache ({})", new Object[] { this.cacheFile });
/*     */ 
/*     */         
/*     */         try {
/* 105 */           this.bufferedImage = ImageIO.read(this.cacheFile);
/*     */           
/* 107 */           if (this.imageBuffer != null)
/*     */           {
/* 109 */             setBufferedImage(this.imageBuffer.parseUserSkin(this.bufferedImage));
/*     */           }
/*     */           
/* 112 */           loadingFinished();
/*     */         }
/* 114 */         catch (IOException ioexception) {
/*     */           
/* 116 */           logger.error("Couldn't load skin " + this.cacheFile, ioexception);
/* 117 */           loadTextureFromServer();
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 122 */         loadTextureFromServer();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadTextureFromServer() {
/* 129 */     this.imageThread = new Thread("Texture Downloader #" + threadDownloadCounter.incrementAndGet())
/*     */       {
/*     */         public void run()
/*     */         {
/* 133 */           HttpURLConnection httpurlconnection = null;
/* 134 */           ThreadDownloadImageData.logger.debug("Downloading http texture from {} to {}", new Object[] { this.this$0.imageUrl, this.this$0.cacheFile });
/*     */           
/* 136 */           if (ThreadDownloadImageData.this.shouldPipeline()) {
/*     */             
/* 138 */             ThreadDownloadImageData.this.loadPipelined();
/*     */           } else {
/*     */             try {
/*     */               BufferedImage bufferedimage;
/*     */ 
/*     */               
/* 144 */               httpurlconnection = (HttpURLConnection)(new URL(ThreadDownloadImageData.this.imageUrl)).openConnection(Minecraft.getMinecraft().getProxy());
/* 145 */               httpurlconnection.setDoInput(true);
/* 146 */               httpurlconnection.setDoOutput(false);
/* 147 */               httpurlconnection.connect();
/*     */               
/* 149 */               if (httpurlconnection.getResponseCode() / 100 != 2) {
/*     */                 
/* 151 */                 if (httpurlconnection.getErrorStream() != null)
/*     */                 {
/* 153 */                   Config.readAll(httpurlconnection.getErrorStream());
/*     */                 }
/*     */ 
/*     */                 
/*     */                 return;
/*     */               } 
/*     */ 
/*     */               
/* 161 */               if (ThreadDownloadImageData.this.cacheFile != null) {
/*     */                 
/* 163 */                 FileUtils.copyInputStreamToFile(httpurlconnection.getInputStream(), ThreadDownloadImageData.this.cacheFile);
/* 164 */                 bufferedimage = ImageIO.read(ThreadDownloadImageData.this.cacheFile);
/*     */               }
/*     */               else {
/*     */                 
/* 168 */                 bufferedimage = TextureUtil.readBufferedImage(httpurlconnection.getInputStream());
/*     */               } 
/*     */               
/* 171 */               if (ThreadDownloadImageData.this.imageBuffer != null)
/*     */               {
/* 173 */                 bufferedimage = ThreadDownloadImageData.this.imageBuffer.parseUserSkin(bufferedimage);
/*     */               }
/*     */               
/* 176 */               ThreadDownloadImageData.this.setBufferedImage(bufferedimage);
/*     */             }
/* 178 */             catch (Exception exception) {
/*     */               
/* 180 */               ThreadDownloadImageData.logger.error("Couldn't download http texture: " + exception.getClass().getName() + ": " + exception.getMessage());
/*     */ 
/*     */               
/*     */               return;
/*     */             } finally {
/* 185 */               if (httpurlconnection != null)
/*     */               {
/* 187 */                 httpurlconnection.disconnect();
/*     */               }
/*     */               
/* 190 */               ThreadDownloadImageData.this.loadingFinished();
/*     */             } 
/*     */           } 
/*     */         }
/*     */       };
/* 195 */     this.imageThread.setDaemon(true);
/* 196 */     this.imageThread.start();
/*     */   }
/*     */ 
/*     */   
/*     */   boolean shouldPipeline() {
/* 201 */     if (!this.pipeline)
/*     */     {
/* 203 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 207 */     Proxy proxy = Minecraft.getMinecraft().getProxy();
/* 208 */     return ((proxy.type() == Proxy.Type.DIRECT || proxy.type() == Proxy.Type.SOCKS) && this.imageUrl.startsWith("http://"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void loadPipelined() {
/*     */     try {
/*     */       BufferedImage bufferedimage;
/* 216 */       HttpRequest httprequest = HttpPipeline.makeRequest(this.imageUrl, Minecraft.getMinecraft().getProxy());
/* 217 */       HttpResponse httpresponse = HttpPipeline.executeRequest(httprequest);
/*     */       
/* 219 */       if (httpresponse.getStatus() / 100 != 2) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 224 */       byte[] abyte = httpresponse.getBody();
/* 225 */       ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte);
/*     */ 
/*     */       
/* 228 */       if (this.cacheFile != null) {
/*     */         
/* 230 */         FileUtils.copyInputStreamToFile(bytearrayinputstream, this.cacheFile);
/* 231 */         bufferedimage = ImageIO.read(this.cacheFile);
/*     */       }
/*     */       else {
/*     */         
/* 235 */         bufferedimage = TextureUtil.readBufferedImage(bytearrayinputstream);
/*     */       } 
/*     */       
/* 238 */       if (this.imageBuffer != null)
/*     */       {
/* 240 */         bufferedimage = this.imageBuffer.parseUserSkin(bufferedimage);
/*     */       }
/*     */       
/* 243 */       setBufferedImage(bufferedimage);
/*     */     }
/* 245 */     catch (Exception exception) {
/*     */       
/* 247 */       logger.error("Couldn't download http texture: " + exception.getClass().getName() + ": " + exception.getMessage());
/*     */ 
/*     */       
/*     */       return;
/*     */     } finally {
/* 252 */       loadingFinished();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void loadingFinished() {
/* 258 */     this.imageFound = Boolean.valueOf((this.bufferedImage != null));
/*     */     
/* 260 */     if (this.imageBuffer instanceof CapeImageBuffer) {
/*     */       
/* 262 */       CapeImageBuffer capeimagebuffer = (CapeImageBuffer)this.imageBuffer;
/* 263 */       capeimagebuffer.cleanup();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IImageBuffer getImageBuffer() {
/* 269 */     return this.imageBuffer;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\ThreadDownloadImageData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */