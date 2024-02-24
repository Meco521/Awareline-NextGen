/*    */ package net.minecraft.client.resources;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.renderer.texture.TextureUtil;
/*    */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.optifine.reflect.ReflectorForge;
/*    */ 
/*    */ public class DefaultResourcePack implements IResourcePack {
/* 17 */   public static final Set<String> defaultResourceDomains = (Set<String>)ImmutableSet.of("minecraft", "realms");
/*    */   
/*    */   private final Map<String, File> mapAssets;
/*    */   
/*    */   public DefaultResourcePack(Map<String, File> mapAssetsIn) {
/* 22 */     this.mapAssets = mapAssetsIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public InputStream getInputStream(ResourceLocation location) throws IOException {
/* 27 */     InputStream inputstream = getResourceStream(location);
/*    */     
/* 29 */     if (inputstream != null)
/*    */     {
/* 31 */       return inputstream;
/*    */     }
/*    */ 
/*    */     
/* 35 */     InputStream inputstream1 = getInputStreamAssets(location);
/*    */     
/* 37 */     if (inputstream1 != null)
/*    */     {
/* 39 */       return inputstream1;
/*    */     }
/*    */ 
/*    */     
/* 43 */     throw new FileNotFoundException(location.getResourcePath());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public InputStream getInputStreamAssets(ResourceLocation location) throws IOException {
/* 49 */     File file1 = this.mapAssets.get(location.toString());
/* 50 */     return (file1 != null && file1.isFile()) ? new FileInputStream(file1) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public InputStream getResourceStream(ResourceLocation location) {
/* 55 */     String s = "/assets/" + location.getResourceDomain() + "/" + location.getResourcePath();
/* 56 */     InputStream inputstream = ReflectorForge.getOptiFineResourceStream(s);
/* 57 */     return (inputstream != null) ? inputstream : DefaultResourcePack.class.getResourceAsStream(s);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean resourceExists(ResourceLocation location) {
/* 62 */     return (getResourceStream(location) != null || this.mapAssets.containsKey(location.toString()));
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> getResourceDomains() {
/* 67 */     return defaultResourceDomains;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T extends net.minecraft.client.resources.data.IMetadataSection> T getPackMetadata(IMetadataSerializer metadataSerializer, String metadataSectionName) {
/*    */     try {
/* 73 */       InputStream inputstream = new FileInputStream(this.mapAssets.get("pack.mcmeta"));
/* 74 */       return AbstractResourcePack.readMetadata(metadataSerializer, inputstream, metadataSectionName);
/*    */     }
/* 76 */     catch (RuntimeException var4) {
/*    */       
/* 78 */       return (T)null;
/*    */     }
/* 80 */     catch (FileNotFoundException var5) {
/*    */       
/* 82 */       return (T)null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public BufferedImage getPackImage() throws IOException {
/* 88 */     return TextureUtil.readBufferedImage(DefaultResourcePack.class.getResourceAsStream("/" + (new ResourceLocation("pack.png")).getResourcePath()));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPackName() {
/* 93 */     return "Default";
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\DefaultResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */