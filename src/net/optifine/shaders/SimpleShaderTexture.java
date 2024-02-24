/*     */ package net.optifine.shaders;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
/*     */ import net.minecraft.client.resources.data.IMetadataSectionSerializer;
/*     */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*     */ import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
/*     */ import net.minecraft.client.resources.data.TextureMetadataSection;
/*     */ import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ public class SimpleShaderTexture extends AbstractTexture {
/*  18 */   private static final IMetadataSerializer METADATA_SERIALIZER = makeMetadataSerializer();
/*     */   private final String texturePath;
/*     */   
/*     */   public SimpleShaderTexture(String texturePath) {
/*  22 */     this.texturePath = texturePath;
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadTexture(IResourceManager resourceManager) throws IOException {
/*  27 */     deleteGlTexture();
/*  28 */     InputStream inputstream = Shaders.getShaderPackResourceStream(this.texturePath);
/*     */     
/*  30 */     if (inputstream == null)
/*     */     {
/*  32 */       throw new FileNotFoundException("Shader texture not found: " + this.texturePath);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  38 */       BufferedImage bufferedimage = TextureUtil.readBufferedImage(inputstream);
/*  39 */       TextureMetadataSection texturemetadatasection = loadTextureMetadataSection(this.texturePath, new TextureMetadataSection(false, false, new ArrayList()));
/*  40 */       TextureUtil.uploadTextureImageAllocate(getGlTextureId(), bufferedimage, texturemetadatasection.getTextureBlur(), texturemetadatasection.getTextureClamp());
/*     */     }
/*     */     finally {
/*     */       
/*  44 */       IOUtils.closeQuietly(inputstream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static TextureMetadataSection loadTextureMetadataSection(String texturePath, TextureMetadataSection def) {
/*  51 */     String s = texturePath + ".mcmeta";
/*  52 */     String s1 = "texture";
/*  53 */     InputStream inputstream = Shaders.getShaderPackResourceStream(s);
/*     */     
/*  55 */     if (inputstream != null) {
/*     */       TextureMetadataSection texturemetadatasection1;
/*  57 */       IMetadataSerializer imetadataserializer = METADATA_SERIALIZER;
/*  58 */       BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*  63 */         JsonObject jsonobject = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
/*  64 */         TextureMetadataSection texturemetadatasection = (TextureMetadataSection)imetadataserializer.parseMetadataSection(s1, jsonobject);
/*     */         
/*  66 */         if (texturemetadatasection == null)
/*     */         {
/*  68 */           return def;
/*     */         }
/*     */         
/*  71 */         texturemetadatasection1 = texturemetadatasection;
/*     */       }
/*  73 */       catch (RuntimeException runtimeexception) {
/*     */         
/*  75 */         SMCLog.warning("Error reading metadata: " + s);
/*  76 */         SMCLog.warning(runtimeexception.getClass().getName() + ": " + runtimeexception.getMessage());
/*  77 */         return def;
/*     */       }
/*     */       finally {
/*     */         
/*  81 */         IOUtils.closeQuietly(bufferedreader);
/*  82 */         IOUtils.closeQuietly(inputstream);
/*     */       } 
/*     */       
/*  85 */       return texturemetadatasection1;
/*     */     } 
/*     */ 
/*     */     
/*  89 */     return def;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static IMetadataSerializer makeMetadataSerializer() {
/*  95 */     IMetadataSerializer imetadataserializer = new IMetadataSerializer();
/*  96 */     imetadataserializer.registerMetadataSectionType((IMetadataSectionSerializer)new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
/*  97 */     imetadataserializer.registerMetadataSectionType((IMetadataSectionSerializer)new FontMetadataSectionSerializer(), FontMetadataSection.class);
/*  98 */     imetadataserializer.registerMetadataSectionType((IMetadataSectionSerializer)new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
/*  99 */     imetadataserializer.registerMetadataSectionType((IMetadataSectionSerializer)new PackMetadataSectionSerializer(), PackMetadataSection.class);
/* 100 */     imetadataserializer.registerMetadataSectionType((IMetadataSectionSerializer)new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
/* 101 */     return imetadataserializer;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\SimpleShaderTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */