/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ 
/*     */ public class NaturalTextures
/*     */ {
/*  17 */   private static NaturalProperties[] propertiesByIndex = new NaturalProperties[0];
/*     */ 
/*     */   
/*     */   public static void update() {
/*  21 */     propertiesByIndex = new NaturalProperties[0];
/*     */     
/*  23 */     if (Config.isNaturalTextures()) {
/*     */       
/*  25 */       String s = "optifine/natural.properties";
/*     */ 
/*     */       
/*     */       try {
/*  29 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/*     */         
/*  31 */         if (!Config.hasResource(resourcelocation)) {
/*     */           
/*  33 */           Config.dbg("NaturalTextures: configuration \"" + s + "\" not found");
/*     */           
/*     */           return;
/*     */         } 
/*  37 */         boolean flag = Config.isFromDefaultResourcePack(resourcelocation);
/*  38 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*  39 */         ArrayList<NaturalProperties> arraylist = new ArrayList(256);
/*  40 */         String s1 = Config.readInputStream(inputstream);
/*  41 */         inputstream.close();
/*  42 */         String[] astring = Config.tokenize(s1, "\n\r");
/*     */         
/*  44 */         if (flag) {
/*     */           
/*  46 */           Config.dbg("Natural Textures: Parsing default configuration \"" + s + "\"");
/*  47 */           Config.dbg("Natural Textures: Valid only for textures from default resource pack");
/*     */         }
/*     */         else {
/*     */           
/*  51 */           Config.dbg("Natural Textures: Parsing configuration \"" + s + "\"");
/*     */         } 
/*     */         
/*  54 */         TextureMap texturemap = TextureUtils.getTextureMapBlocks();
/*     */         
/*  56 */         for (int i = 0; i < astring.length; i++) {
/*     */           
/*  58 */           String s2 = astring[i].trim();
/*     */           
/*  60 */           if (s2.isEmpty() || s2.charAt(0) != '#') {
/*     */             
/*  62 */             String[] astring1 = Config.tokenize(s2, "=");
/*     */             
/*  64 */             if (astring1.length != 2) {
/*     */               
/*  66 */               Config.warn("Natural Textures: Invalid \"" + s + "\" line: " + s2);
/*     */             }
/*     */             else {
/*     */               
/*  70 */               String s3 = astring1[0].trim();
/*  71 */               String s4 = astring1[1].trim();
/*  72 */               TextureAtlasSprite textureatlassprite = texturemap.getSpriteSafe("minecraft:blocks/" + s3);
/*     */               
/*  74 */               if (textureatlassprite == null) {
/*     */                 
/*  76 */                 Config.warn("Natural Textures: Texture not found: \"" + s + "\" line: " + s2);
/*     */               }
/*     */               else {
/*     */                 
/*  80 */                 int j = textureatlassprite.getIndexInMap();
/*     */                 
/*  82 */                 if (j < 0) {
/*     */                   
/*  84 */                   Config.warn("Natural Textures: Invalid \"" + s + "\" line: " + s2);
/*     */                 }
/*     */                 else {
/*     */                   
/*  88 */                   if (flag && !Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/" + s3 + ".png"))) {
/*     */                     return;
/*     */                   }
/*     */ 
/*     */                   
/*  93 */                   NaturalProperties naturalproperties = new NaturalProperties(s4);
/*     */                   
/*  95 */                   if (naturalproperties.isValid()) {
/*     */                     
/*  97 */                     while (arraylist.size() <= j)
/*     */                     {
/*  99 */                       arraylist.add(null);
/*     */                     }
/*     */                     
/* 102 */                     arraylist.set(j, naturalproperties);
/* 103 */                     Config.dbg("NaturalTextures: " + s3 + " = " + s4);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 111 */         propertiesByIndex = arraylist.<NaturalProperties>toArray(new NaturalProperties[0]);
/*     */       }
/* 113 */       catch (FileNotFoundException var17) {
/*     */         
/* 115 */         Config.warn("NaturalTextures: configuration \"" + s + "\" not found");
/*     */         
/*     */         return;
/* 118 */       } catch (Exception exception) {
/*     */         
/* 120 */         exception.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static BakedQuad getNaturalTexture(BlockPos blockPosIn, BakedQuad quad) {
/* 127 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*     */     
/* 129 */     if (textureatlassprite == null)
/*     */     {
/* 131 */       return quad;
/*     */     }
/*     */ 
/*     */     
/* 135 */     NaturalProperties naturalproperties = getNaturalProperties(textureatlassprite);
/*     */     
/* 137 */     if (naturalproperties == null)
/*     */     {
/* 139 */       return quad;
/*     */     }
/*     */ 
/*     */     
/* 143 */     int i = ConnectedTextures.getSide(quad.getFace());
/* 144 */     int j = Config.getRandom(blockPosIn, i);
/* 145 */     int k = 0;
/* 146 */     boolean flag = false;
/*     */     
/* 148 */     if (naturalproperties.rotation > 1)
/*     */     {
/* 150 */       k = j & 0x3;
/*     */     }
/*     */     
/* 153 */     if (naturalproperties.rotation == 2)
/*     */     {
/* 155 */       k = k / 2 << 1;
/*     */     }
/*     */     
/* 158 */     if (naturalproperties.flip)
/*     */     {
/* 160 */       flag = ((j & 0x4) != 0);
/*     */     }
/*     */     
/* 163 */     return naturalproperties.getQuad(quad, k, flag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NaturalProperties getNaturalProperties(TextureAtlasSprite icon) {
/* 170 */     if (!(icon instanceof TextureAtlasSprite))
/*     */     {
/* 172 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 176 */     int i = icon.getIndexInMap();
/*     */     
/* 178 */     if (i >= 0 && i < propertiesByIndex.length) {
/*     */       
/* 180 */       NaturalProperties naturalproperties = propertiesByIndex[i];
/* 181 */       return naturalproperties;
/*     */     } 
/*     */ 
/*     */     
/* 185 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\NaturalTextures.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */