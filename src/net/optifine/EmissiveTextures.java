/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.SimpleTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EmissiveTextures
/*     */ {
/*  19 */   private static String suffixEmissive = null;
/*  20 */   private static String suffixEmissivePng = null;
/*     */   private static boolean active = false;
/*     */   private static boolean render = false;
/*     */   private static boolean hasEmissive = false;
/*     */   private static boolean renderEmissive = false;
/*     */   private static float lightMapX;
/*     */   private static float lightMapY;
/*     */   private static final String SUFFIX_PNG = ".png";
/*  28 */   private static final ResourceLocation LOCATION_EMPTY = new ResourceLocation("mcpatcher/ctm/default/empty.png");
/*     */ 
/*     */   
/*     */   public static boolean isActive() {
/*  32 */     return active;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getSuffixEmissive() {
/*  37 */     return suffixEmissive;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginRender() {
/*  42 */     render = true;
/*  43 */     hasEmissive = false;
/*     */   }
/*     */   
/*     */   public static ITextureObject getEmissiveTexture(ITextureObject texture, Map<ResourceLocation, ITextureObject> mapTextures) {
/*     */     SimpleTexture simpleTexture1;
/*  48 */     if (!render)
/*     */     {
/*  50 */       return texture;
/*     */     }
/*  52 */     if (!(texture instanceof SimpleTexture))
/*     */     {
/*  54 */       return texture;
/*     */     }
/*     */ 
/*     */     
/*  58 */     SimpleTexture simpletexture = (SimpleTexture)texture;
/*  59 */     ResourceLocation resourcelocation = simpletexture.locationEmissive;
/*     */     
/*  61 */     if (!renderEmissive) {
/*     */       
/*  63 */       if (resourcelocation != null)
/*     */       {
/*  65 */         hasEmissive = true;
/*     */       }
/*     */       
/*  68 */       return texture;
/*     */     } 
/*     */ 
/*     */     
/*  72 */     if (resourcelocation == null)
/*     */     {
/*  74 */       resourcelocation = LOCATION_EMPTY;
/*     */     }
/*     */     
/*  77 */     ITextureObject itextureobject = mapTextures.get(resourcelocation);
/*     */     
/*  79 */     if (itextureobject == null) {
/*     */       
/*  81 */       simpleTexture1 = new SimpleTexture(resourcelocation);
/*  82 */       TextureManager texturemanager = Config.getTextureManager();
/*  83 */       texturemanager.loadTexture(resourcelocation, (ITextureObject)simpleTexture1);
/*     */     } 
/*     */     
/*  86 */     return (ITextureObject)simpleTexture1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasEmissive() {
/*  93 */     return hasEmissive;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginRenderEmissive() {
/*  98 */     lightMapX = OpenGlHelper.lastBrightnessX;
/*  99 */     lightMapY = OpenGlHelper.lastBrightnessY;
/* 100 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, lightMapY);
/* 101 */     renderEmissive = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endRenderEmissive() {
/* 106 */     renderEmissive = false;
/* 107 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightMapX, lightMapY);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endRender() {
/* 112 */     render = false;
/* 113 */     hasEmissive = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update() {
/* 118 */     active = false;
/* 119 */     suffixEmissive = null;
/* 120 */     suffixEmissivePng = null;
/*     */     
/* 122 */     if (Config.isEmissiveTextures()) {
/*     */       
/*     */       try {
/*     */         
/* 126 */         String s = "optifine/emissive.properties";
/* 127 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/* 128 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */         
/* 130 */         if (inputstream == null) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 135 */         dbg("Loading " + s);
/* 136 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 137 */         propertiesOrdered.load(inputstream);
/* 138 */         inputstream.close();
/* 139 */         suffixEmissive = propertiesOrdered.getProperty("suffix.emissive");
/*     */         
/* 141 */         if (suffixEmissive != null)
/*     */         {
/* 143 */           suffixEmissivePng = suffixEmissive + ".png";
/*     */         }
/*     */         
/* 146 */         active = (suffixEmissive != null);
/*     */       }
/* 148 */       catch (FileNotFoundException var4) {
/*     */         
/*     */         return;
/*     */       }
/* 152 */       catch (IOException ioexception) {
/*     */         
/* 154 */         ioexception.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void dbg(String str) {
/* 161 */     Config.dbg("EmissiveTextures: " + str);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void warn(String str) {
/* 166 */     Config.warn("EmissiveTextures: " + str);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isEmissive(ResourceLocation loc) {
/* 171 */     return (suffixEmissivePng == null) ? false : loc.getResourcePath().endsWith(suffixEmissivePng);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadTexture(ResourceLocation loc, SimpleTexture tex) {
/* 176 */     if (loc != null && tex != null) {
/*     */       
/* 178 */       tex.isEmissive = false;
/* 179 */       tex.locationEmissive = null;
/*     */       
/* 181 */       if (suffixEmissivePng != null) {
/*     */         
/* 183 */         String s = loc.getResourcePath();
/*     */         
/* 185 */         if (s.endsWith(".png"))
/*     */         {
/* 187 */           if (s.endsWith(suffixEmissivePng)) {
/*     */             
/* 189 */             tex.isEmissive = true;
/*     */           }
/*     */           else {
/*     */             
/* 193 */             String s1 = s.substring(0, s.length() - ".png".length()) + suffixEmissivePng;
/* 194 */             ResourceLocation resourcelocation = new ResourceLocation(loc.getResourceDomain(), s1);
/*     */             
/* 196 */             if (Config.hasResource(resourcelocation))
/*     */             {
/* 198 */               tex.locationEmissive = resourcelocation;
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\EmissiveTextures.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */