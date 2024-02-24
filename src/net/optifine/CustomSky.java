/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.render.Blender;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ 
/*     */ public class CustomSky
/*     */ {
/*  21 */   private static CustomSkyLayer[][] worldSkyLayers = (CustomSkyLayer[][])null;
/*     */ 
/*     */   
/*     */   public static void reset() {
/*  25 */     worldSkyLayers = (CustomSkyLayer[][])null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update() {
/*  30 */     reset();
/*     */     
/*  32 */     if (Config.isCustomSky())
/*     */     {
/*  34 */       worldSkyLayers = readCustomSkies();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static CustomSkyLayer[][] readCustomSkies() {
/*  40 */     CustomSkyLayer[][] acustomskylayer = new CustomSkyLayer[10][0];
/*  41 */     String s = "mcpatcher/sky/world";
/*  42 */     int i = -1;
/*     */     
/*  44 */     for (int j = 0; j < acustomskylayer.length; j++) {
/*     */       
/*  46 */       String s1 = s + j + "/sky";
/*  47 */       List<CustomSkyLayer> list = new ArrayList();
/*     */       
/*  49 */       for (int k = 1; k < 1000; k++) {
/*     */         
/*  51 */         String s2 = s1 + k + ".properties";
/*     */ 
/*     */         
/*     */         try {
/*  55 */           ResourceLocation resourcelocation = new ResourceLocation(s2);
/*  56 */           InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */           
/*  58 */           if (inputstream == null) {
/*     */             break;
/*     */           }
/*     */ 
/*     */           
/*  63 */           PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  64 */           propertiesOrdered.load(inputstream);
/*  65 */           inputstream.close();
/*  66 */           Config.dbg("CustomSky properties: " + s2);
/*  67 */           String s3 = s1 + k + ".png";
/*  68 */           CustomSkyLayer customskylayer = new CustomSkyLayer((Properties)propertiesOrdered, s3);
/*     */           
/*  70 */           if (customskylayer.isValid(s2)) {
/*     */             
/*  72 */             ResourceLocation resourcelocation1 = new ResourceLocation(customskylayer.source);
/*  73 */             ITextureObject itextureobject = TextureUtils.getTexture(resourcelocation1);
/*     */             
/*  75 */             if (itextureobject == null)
/*     */             {
/*  77 */               Config.log("CustomSky: Texture not found: " + resourcelocation1);
/*     */             }
/*     */             else
/*     */             {
/*  81 */               customskylayer.textureId = itextureobject.getGlTextureId();
/*  82 */               list.add(customskylayer);
/*  83 */               inputstream.close();
/*     */             }
/*     */           
/*     */           } 
/*  87 */         } catch (FileNotFoundException var15) {
/*     */           
/*     */           break;
/*     */         }
/*  91 */         catch (IOException ioexception) {
/*     */           
/*  93 */           ioexception.printStackTrace();
/*     */         } 
/*     */       } 
/*     */       
/*  97 */       if (!list.isEmpty()) {
/*     */         
/*  99 */         CustomSkyLayer[] acustomskylayer2 = list.<CustomSkyLayer>toArray(new CustomSkyLayer[0]);
/* 100 */         acustomskylayer[j] = acustomskylayer2;
/* 101 */         i = j;
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     if (i < 0)
/*     */     {
/* 107 */       return (CustomSkyLayer[][])null;
/*     */     }
/*     */ 
/*     */     
/* 111 */     int l = i + 1;
/* 112 */     CustomSkyLayer[][] acustomskylayer1 = new CustomSkyLayer[l][0];
/*     */     
/* 114 */     System.arraycopy(acustomskylayer, 0, acustomskylayer1, 0, acustomskylayer1.length);
/*     */     
/* 116 */     return acustomskylayer1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderSky(World world, TextureManager re, float partialTicks) {
/* 122 */     if (worldSkyLayers != null) {
/*     */       
/* 124 */       int i = world.provider.getDimensionId();
/*     */       
/* 126 */       if (i >= 0 && i < worldSkyLayers.length) {
/*     */         
/* 128 */         CustomSkyLayer[] acustomskylayer = worldSkyLayers[i];
/*     */         
/* 130 */         if (acustomskylayer != null) {
/*     */           
/* 132 */           long j = world.getWorldTime();
/* 133 */           int k = (int)(j % 24000L);
/* 134 */           float f = world.getCelestialAngle(partialTicks);
/* 135 */           float f1 = world.getRainStrength(partialTicks);
/* 136 */           float f2 = world.getThunderStrength(partialTicks);
/*     */           
/* 138 */           if (f1 > 0.0F)
/*     */           {
/* 140 */             f2 /= f1;
/*     */           }
/*     */           
/* 143 */           for (int l = 0; l < acustomskylayer.length; l++) {
/*     */             
/* 145 */             CustomSkyLayer customskylayer = acustomskylayer[l];
/*     */             
/* 147 */             if (customskylayer.isActive(world, k))
/*     */             {
/* 149 */               customskylayer.render(world, k, f, f1, f2);
/*     */             }
/*     */           } 
/*     */           
/* 153 */           float f3 = 1.0F - f1;
/* 154 */           Blender.clearBlend(f3);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean hasSkyLayers(World world) {
/* 162 */     if (worldSkyLayers == null)
/*     */     {
/* 164 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 168 */     int i = world.provider.getDimensionId();
/*     */     
/* 170 */     if (i >= 0 && i < worldSkyLayers.length) {
/*     */       
/* 172 */       CustomSkyLayer[] acustomskylayer = worldSkyLayers[i];
/* 173 */       return (acustomskylayer == null) ? false : ((acustomskylayer.length > 0));
/*     */     } 
/*     */ 
/*     */     
/* 177 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\CustomSky.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */