/*     */ package net.optifine.player;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.io.File;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.renderer.IImageBuffer;
/*     */ import net.minecraft.client.renderer.ThreadDownloadImageData;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.SimpleTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class CapeUtils
/*     */ {
/*  20 */   private static final Pattern PATTERN_USERNAME = Pattern.compile("\\w+");
/*     */ 
/*     */   
/*     */   public static void downloadCape(AbstractClientPlayer player) {
/*  24 */     String s = player.getNameClear();
/*     */     
/*  26 */     if (s != null && !s.isEmpty() && !s.contains("\000") && PATTERN_USERNAME.matcher(s).matches()) {
/*     */       
/*  28 */       String s1 = "http://s.optifine.net/capes/" + s + ".png";
/*  29 */       ResourceLocation resourcelocation = new ResourceLocation("capeof/" + s);
/*  30 */       TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
/*  31 */       ITextureObject itextureobject = texturemanager.getTexture(resourcelocation);
/*     */       
/*  33 */       if (itextureobject != null && itextureobject instanceof ThreadDownloadImageData) {
/*     */         
/*  35 */         ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData)itextureobject;
/*     */         
/*  37 */         if (threaddownloadimagedata.imageFound != null) {
/*     */           
/*  39 */           if (threaddownloadimagedata.imageFound.booleanValue()) {
/*     */             
/*  41 */             player.setLocationOfCape(resourcelocation);
/*     */             
/*  43 */             if (threaddownloadimagedata.getImageBuffer() instanceof CapeImageBuffer) {
/*     */               
/*  45 */               CapeImageBuffer capeimagebuffer1 = (CapeImageBuffer)threaddownloadimagedata.getImageBuffer();
/*  46 */               player.setElytraOfCape(capeimagebuffer1.isElytraOfCape());
/*     */             } 
/*     */           } 
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */       
/*  54 */       CapeImageBuffer capeimagebuffer = new CapeImageBuffer(player, resourcelocation);
/*  55 */       ThreadDownloadImageData threaddownloadimagedata1 = new ThreadDownloadImageData((File)null, s1, (ResourceLocation)null, (IImageBuffer)capeimagebuffer);
/*  56 */       threaddownloadimagedata1.pipeline = true;
/*  57 */       texturemanager.loadTexture(resourcelocation, (ITextureObject)threaddownloadimagedata1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static BufferedImage parseCape(BufferedImage img) {
/*  63 */     int i = 64;
/*  64 */     int j = 32;
/*  65 */     int k = img.getWidth();
/*     */     
/*  67 */     for (int l = img.getHeight(); i < k || j < l; j <<= 1)
/*     */     {
/*  69 */       i <<= 1;
/*     */     }
/*     */     
/*  72 */     BufferedImage bufferedimage = new BufferedImage(i, j, 2);
/*  73 */     Graphics graphics = bufferedimage.getGraphics();
/*  74 */     graphics.drawImage(img, 0, 0, (ImageObserver)null);
/*  75 */     graphics.dispose();
/*  76 */     return bufferedimage;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isElytraCape(BufferedImage imageRaw, BufferedImage imageFixed) {
/*  81 */     return (imageRaw.getWidth() > imageFixed.getHeight());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void reloadCape(AbstractClientPlayer player) {
/*  86 */     String s = player.getNameClear();
/*  87 */     ResourceLocation resourcelocation = new ResourceLocation("capeof/" + s);
/*  88 */     TextureManager texturemanager = Config.getTextureManager();
/*  89 */     ITextureObject itextureobject = texturemanager.getTexture(resourcelocation);
/*     */     
/*  91 */     if (itextureobject instanceof SimpleTexture) {
/*     */       
/*  93 */       SimpleTexture simpletexture = (SimpleTexture)itextureobject;
/*  94 */       simpletexture.deleteGlTexture();
/*  95 */       texturemanager.deleteTexture(resourcelocation);
/*     */     } 
/*     */     
/*  98 */     player.setLocationOfCape((ResourceLocation)null);
/*  99 */     player.setElytraOfCape(false);
/* 100 */     downloadCape(player);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\player\CapeUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */