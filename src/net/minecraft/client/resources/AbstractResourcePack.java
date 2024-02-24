/*    */ package net.minecraft.client.resources;
/*    */ import com.google.common.base.Charsets;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.google.gson.JsonParser;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import net.minecraft.client.renderer.texture.TextureUtil;
/*    */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public abstract class AbstractResourcePack implements IResourcePack {
/* 20 */   private static final Logger resourceLog = LogManager.getLogger();
/*    */   
/*    */   public final File resourcePackFile;
/*    */   
/*    */   public AbstractResourcePack(File resourcePackFileIn) {
/* 25 */     this.resourcePackFile = resourcePackFileIn;
/*    */   }
/*    */ 
/*    */   
/*    */   private static String locationToName(ResourceLocation location) {
/* 30 */     return String.format("%s/%s/%s", new Object[] { "assets", location.getResourceDomain(), location.getResourcePath() });
/*    */   }
/*    */ 
/*    */   
/*    */   protected static String getRelativeName(File p_110595_0_, File p_110595_1_) {
/* 35 */     return p_110595_0_.toURI().relativize(p_110595_1_.toURI()).getPath();
/*    */   }
/*    */ 
/*    */   
/*    */   public InputStream getInputStream(ResourceLocation location) throws IOException {
/* 40 */     return getInputStreamByName(locationToName(location));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean resourceExists(ResourceLocation location) {
/* 45 */     return hasResourceName(locationToName(location));
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract InputStream getInputStreamByName(String paramString) throws IOException;
/*    */   
/*    */   protected abstract boolean hasResourceName(String paramString);
/*    */   
/*    */   protected void logNameNotLowercase(String name) {
/* 54 */     resourceLog.warn("ResourcePack: ignored non-lowercase namespace: {} in {}", new Object[] { name, this.resourcePackFile });
/*    */   }
/*    */ 
/*    */   
/*    */   public <T extends net.minecraft.client.resources.data.IMetadataSection> T getPackMetadata(IMetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
/* 59 */     return readMetadata(metadataSerializer, getInputStreamByName("pack.mcmeta"), metadataSectionName);
/*    */   }
/*    */ 
/*    */   
/*    */   static <T extends net.minecraft.client.resources.data.IMetadataSection> T readMetadata(IMetadataSerializer p_110596_0_, InputStream p_110596_1_, String p_110596_2_) {
/* 64 */     JsonObject jsonobject = null;
/* 65 */     BufferedReader bufferedreader = null;
/*    */ 
/*    */     
/*    */     try {
/* 69 */       bufferedreader = new BufferedReader(new InputStreamReader(p_110596_1_, Charsets.UTF_8));
/* 70 */       jsonobject = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
/*    */     }
/* 72 */     catch (RuntimeException runtimeexception) {
/*    */       
/* 74 */       throw new JsonParseException(runtimeexception);
/*    */     }
/*    */     finally {
/*    */       
/* 78 */       IOUtils.closeQuietly(bufferedreader);
/*    */     } 
/*    */     
/* 81 */     return (T)p_110596_0_.parseMetadataSection(p_110596_2_, jsonobject);
/*    */   }
/*    */ 
/*    */   
/*    */   public BufferedImage getPackImage() throws IOException {
/* 86 */     return TextureUtil.readBufferedImage(getInputStreamByName("pack.png"));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPackName() {
/* 91 */     return this.resourcePackFile.getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\AbstractResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */