/*     */ package net.optifine;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.resources.IResourcePack;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ import net.optifine.util.ResUtils;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ public class TextureAnimations
/*     */ {
/*  25 */   private static TextureAnimation[] textureAnimations = null;
/*  26 */   private static int countAnimationsActive = 0;
/*  27 */   private static int frameCountAnimations = 0;
/*     */ 
/*     */   
/*     */   public static void reset() {
/*  31 */     textureAnimations = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update() {
/*  36 */     textureAnimations = null;
/*  37 */     countAnimationsActive = 0;
/*  38 */     IResourcePack[] airesourcepack = Config.getResourcePacks();
/*  39 */     textureAnimations = getTextureAnimations(airesourcepack);
/*  40 */     updateAnimations();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateAnimations() {
/*  45 */     if (textureAnimations != null && Config.isAnimatedTextures()) {
/*     */       
/*  47 */       int i = 0;
/*     */       
/*  49 */       for (int j = 0; j < textureAnimations.length; j++) {
/*     */         
/*  51 */         TextureAnimation textureanimation = textureAnimations[j];
/*  52 */         textureanimation.updateTexture();
/*     */         
/*  54 */         if (textureanimation.isActive())
/*     */         {
/*  56 */           i++;
/*     */         }
/*     */       } 
/*     */       
/*  60 */       int k = (Config.getMinecraft()).entityRenderer.frameCount;
/*     */       
/*  62 */       if (k != frameCountAnimations) {
/*     */         
/*  64 */         countAnimationsActive = i;
/*  65 */         frameCountAnimations = k;
/*     */       } 
/*     */       
/*  68 */       if (SmartAnimations.isActive())
/*     */       {
/*  70 */         SmartAnimations.resetTexturesRendered();
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  75 */       countAnimationsActive = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static TextureAnimation[] getTextureAnimations(IResourcePack[] rps) {
/*  81 */     List list = new ArrayList();
/*     */     
/*  83 */     for (int i = 0; i < rps.length; i++) {
/*     */       
/*  85 */       IResourcePack iresourcepack = rps[i];
/*  86 */       TextureAnimation[] atextureanimation = getTextureAnimations(iresourcepack);
/*     */       
/*  88 */       if (atextureanimation != null)
/*     */       {
/*  90 */         list.addAll(Arrays.asList(atextureanimation));
/*     */       }
/*     */     } 
/*     */     
/*  94 */     TextureAnimation[] atextureanimation1 = (TextureAnimation[])list.toArray((Object[])new TextureAnimation[0]);
/*  95 */     return atextureanimation1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static TextureAnimation[] getTextureAnimations(IResourcePack rp) {
/* 100 */     String[] astring = ResUtils.collectFiles(rp, "mcpatcher/anim/", ".properties", (String[])null);
/*     */     
/* 102 */     if (astring.length <= 0)
/*     */     {
/* 104 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 108 */     List<TextureAnimation> list = new ArrayList();
/*     */     
/* 110 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 112 */       String s = astring[i];
/* 113 */       Config.dbg("Texture animation: " + s);
/*     */ 
/*     */       
/*     */       try {
/* 117 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/* 118 */         InputStream inputstream = rp.getInputStream(resourcelocation);
/* 119 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 120 */         propertiesOrdered.load(inputstream);
/* 121 */         inputstream.close();
/* 122 */         TextureAnimation textureanimation = makeTextureAnimation((Properties)propertiesOrdered, resourcelocation);
/*     */         
/* 124 */         if (textureanimation != null) {
/*     */           
/* 126 */           ResourceLocation resourcelocation1 = new ResourceLocation(textureanimation.getDstTex());
/*     */           
/* 128 */           if (Config.getDefiningResourcePack(resourcelocation1) != rp)
/*     */           {
/* 130 */             Config.dbg("Skipped: " + s + ", target texture not loaded from same resource pack");
/*     */           }
/*     */           else
/*     */           {
/* 134 */             list.add(textureanimation);
/*     */           }
/*     */         
/*     */         } 
/* 138 */       } catch (FileNotFoundException filenotfoundexception) {
/*     */         
/* 140 */         Config.warn("File not found: " + filenotfoundexception.getMessage());
/*     */       }
/* 142 */       catch (IOException ioexception) {
/*     */         
/* 144 */         ioexception.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 148 */     TextureAnimation[] atextureanimation = list.<TextureAnimation>toArray(new TextureAnimation[0]);
/* 149 */     return atextureanimation;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static TextureAnimation makeTextureAnimation(Properties props, ResourceLocation propLoc) {
/* 155 */     String s = props.getProperty("from");
/* 156 */     String s1 = props.getProperty("to");
/* 157 */     int i = Config.parseInt(props.getProperty("x"), -1);
/* 158 */     int j = Config.parseInt(props.getProperty("y"), -1);
/* 159 */     int k = Config.parseInt(props.getProperty("w"), -1);
/* 160 */     int l = Config.parseInt(props.getProperty("h"), -1);
/*     */     
/* 162 */     if (s != null && s1 != null) {
/*     */       
/* 164 */       if (i >= 0 && j >= 0 && k >= 0 && l >= 0) {
/*     */         
/* 166 */         s = s.trim();
/* 167 */         s1 = s1.trim();
/* 168 */         String s2 = TextureUtils.getBasePath(propLoc.getResourcePath());
/* 169 */         s = TextureUtils.fixResourcePath(s, s2);
/* 170 */         s1 = TextureUtils.fixResourcePath(s1, s2);
/* 171 */         byte[] abyte = getCustomTextureData(s, k);
/*     */         
/* 173 */         if (abyte == null) {
/*     */           
/* 175 */           Config.warn("TextureAnimation: Source texture not found: " + s1);
/* 176 */           return null;
/*     */         } 
/*     */ 
/*     */         
/* 180 */         int i1 = abyte.length / 4;
/* 181 */         int j1 = i1 / k * l;
/* 182 */         int k1 = j1 * k * l;
/*     */         
/* 184 */         if (i1 != k1) {
/*     */           
/* 186 */           Config.warn("TextureAnimation: Source texture has invalid number of frames: " + s + ", frames: " + (i1 / (k * l)));
/* 187 */           return null;
/*     */         } 
/*     */ 
/*     */         
/* 191 */         ResourceLocation resourcelocation = new ResourceLocation(s1);
/*     */ 
/*     */         
/*     */         try {
/* 195 */           InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */           
/* 197 */           if (inputstream == null) {
/*     */             
/* 199 */             Config.warn("TextureAnimation: Target texture not found: " + s1);
/* 200 */             return null;
/*     */           } 
/*     */ 
/*     */           
/* 204 */           BufferedImage bufferedimage = readTextureImage(inputstream);
/*     */           
/* 206 */           if (i + k <= bufferedimage.getWidth() && j + l <= bufferedimage.getHeight()) {
/*     */             
/* 208 */             TextureAnimation textureanimation = new TextureAnimation(s, abyte, s1, resourcelocation, i, j, k, l, props);
/* 209 */             return textureanimation;
/*     */           } 
/*     */ 
/*     */           
/* 213 */           Config.warn("TextureAnimation: Animation coordinates are outside the target texture: " + s1);
/* 214 */           return null;
/*     */ 
/*     */         
/*     */         }
/* 218 */         catch (IOException var17) {
/*     */           
/* 220 */           Config.warn("TextureAnimation: Target texture not found: " + s1);
/* 221 */           return null;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 228 */       Config.warn("TextureAnimation: Invalid coordinates");
/* 229 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 234 */     Config.warn("TextureAnimation: Source or target texture not specified");
/* 235 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] getCustomTextureData(String imagePath, int tileWidth) {
/* 241 */     byte[] abyte = loadImage(imagePath, tileWidth);
/*     */     
/* 243 */     if (abyte == null)
/*     */     {
/* 245 */       abyte = loadImage("/anim" + imagePath, tileWidth);
/*     */     }
/*     */     
/* 248 */     return abyte;
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte[] loadImage(String name, int targetWidth) {
/* 253 */     GameSettings gamesettings = Config.getGameSettings();
/*     */ 
/*     */     
/*     */     try {
/* 257 */       ResourceLocation resourcelocation = new ResourceLocation(name);
/* 258 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */       
/* 260 */       if (inputstream == null)
/*     */       {
/* 262 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 266 */       BufferedImage bufferedimage = readTextureImage(inputstream);
/* 267 */       inputstream.close();
/*     */       
/* 269 */       if (bufferedimage == null)
/*     */       {
/* 271 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 275 */       if (targetWidth > 0 && bufferedimage.getWidth() != targetWidth) {
/*     */         
/* 277 */         double d0 = (bufferedimage.getHeight() / bufferedimage.getWidth());
/* 278 */         int j = (int)(targetWidth * d0);
/* 279 */         bufferedimage = scaleBufferedImage(bufferedimage, targetWidth, j);
/*     */       } 
/*     */       
/* 282 */       int k2 = bufferedimage.getWidth();
/* 283 */       int i = bufferedimage.getHeight();
/* 284 */       int[] aint = new int[k2 * i];
/* 285 */       byte[] abyte = new byte[k2 * i * 4];
/* 286 */       bufferedimage.getRGB(0, 0, k2, i, aint, 0, k2);
/*     */       
/* 288 */       for (int k = 0; k < aint.length; k++) {
/*     */         
/* 290 */         int l = aint[k] >> 24 & 0xFF;
/* 291 */         int i1 = aint[k] >> 16 & 0xFF;
/* 292 */         int j1 = aint[k] >> 8 & 0xFF;
/* 293 */         int k1 = aint[k] & 0xFF;
/*     */         
/* 295 */         if (gamesettings != null && gamesettings.anaglyph) {
/*     */           
/* 297 */           int l1 = (i1 * 30 + j1 * 59 + k1 * 11) / 100;
/* 298 */           int i2 = (i1 * 30 + j1 * 70) / 100;
/* 299 */           int j2 = (i1 * 30 + k1 * 70) / 100;
/* 300 */           i1 = l1;
/* 301 */           j1 = i2;
/* 302 */           k1 = j2;
/*     */         } 
/*     */         
/* 305 */         abyte[k << 2] = (byte)i1;
/* 306 */         abyte[(k << 2) + 1] = (byte)j1;
/* 307 */         abyte[(k << 2) + 2] = (byte)k1;
/* 308 */         abyte[(k << 2) + 3] = (byte)l;
/*     */       } 
/*     */       
/* 311 */       return abyte;
/*     */ 
/*     */     
/*     */     }
/* 315 */     catch (FileNotFoundException var18) {
/*     */       
/* 317 */       return null;
/*     */     }
/* 319 */     catch (Exception exception) {
/*     */       
/* 321 */       exception.printStackTrace();
/* 322 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static BufferedImage readTextureImage(InputStream par1InputStream) throws IOException {
/* 328 */     BufferedImage bufferedimage = ImageIO.read(par1InputStream);
/* 329 */     par1InputStream.close();
/* 330 */     return bufferedimage;
/*     */   }
/*     */ 
/*     */   
/*     */   private static BufferedImage scaleBufferedImage(BufferedImage image, int width, int height) {
/* 335 */     BufferedImage bufferedimage = new BufferedImage(width, height, 2);
/* 336 */     Graphics2D graphics2d = bufferedimage.createGraphics();
/* 337 */     graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/* 338 */     graphics2d.drawImage(image, 0, 0, width, height, (ImageObserver)null);
/* 339 */     return bufferedimage;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getCountAnimations() {
/* 344 */     return (textureAnimations == null) ? 0 : textureAnimations.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getCountAnimationsActive() {
/* 349 */     return countAnimationsActive;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\TextureAnimations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */