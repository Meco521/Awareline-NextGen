/*     */ package net.minecraft.client.resources;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class FallbackResourceManager implements IResourceManager {
/*  15 */   static final Logger logger = LogManager.getLogger();
/*  16 */   protected final List<IResourcePack> resourcePacks = Lists.newArrayList();
/*     */   
/*     */   private final IMetadataSerializer frmMetadataSerializer;
/*     */   
/*     */   public FallbackResourceManager(IMetadataSerializer frmMetadataSerializerIn) {
/*  21 */     this.frmMetadataSerializer = frmMetadataSerializerIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addResourcePack(IResourcePack resourcePack) {
/*  26 */     this.resourcePacks.add(resourcePack);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getResourceDomains() {
/*  31 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public IResource getResource(ResourceLocation location) throws IOException {
/*  36 */     IResourcePack iresourcepack = null;
/*  37 */     ResourceLocation resourcelocation = getLocationMcmeta(location);
/*     */     
/*  39 */     for (int i = this.resourcePacks.size() - 1; i >= 0; i--) {
/*     */       
/*  41 */       IResourcePack iresourcepack1 = this.resourcePacks.get(i);
/*     */       
/*  43 */       if (iresourcepack == null && iresourcepack1.resourceExists(resourcelocation))
/*     */       {
/*  45 */         iresourcepack = iresourcepack1;
/*     */       }
/*     */       
/*  48 */       if (iresourcepack1.resourceExists(location)) {
/*     */         
/*  50 */         InputStream inputstream = null;
/*     */         
/*  52 */         if (iresourcepack != null)
/*     */         {
/*  54 */           inputstream = getInputStream(resourcelocation, iresourcepack);
/*     */         }
/*     */         
/*  57 */         return new SimpleResource(iresourcepack1.getPackName(), location, getInputStream(location, iresourcepack1), inputstream, this.frmMetadataSerializer);
/*     */       } 
/*     */     } 
/*     */     
/*  61 */     throw new FileNotFoundException(location.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   protected InputStream getInputStream(ResourceLocation location, IResourcePack resourcePack) throws IOException {
/*  66 */     InputStream inputstream = resourcePack.getInputStream(location);
/*  67 */     return logger.isDebugEnabled() ? new InputStreamLeakedResourceLogger(inputstream, location, resourcePack.getPackName()) : inputstream;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IResource> getAllResources(ResourceLocation location) throws IOException {
/*  72 */     List<IResource> list = Lists.newArrayList();
/*  73 */     ResourceLocation resourcelocation = getLocationMcmeta(location);
/*     */     
/*  75 */     for (IResourcePack iresourcepack : this.resourcePacks) {
/*     */       
/*  77 */       if (iresourcepack.resourceExists(location)) {
/*     */         
/*  79 */         InputStream inputstream = iresourcepack.resourceExists(resourcelocation) ? getInputStream(resourcelocation, iresourcepack) : null;
/*  80 */         list.add(new SimpleResource(iresourcepack.getPackName(), location, getInputStream(location, iresourcepack), inputstream, this.frmMetadataSerializer));
/*     */       } 
/*     */     } 
/*     */     
/*  84 */     if (list.isEmpty())
/*     */     {
/*  86 */       throw new FileNotFoundException(location.toString());
/*     */     }
/*     */ 
/*     */     
/*  90 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static ResourceLocation getLocationMcmeta(ResourceLocation location) {
/*  96 */     return new ResourceLocation(location.getResourceDomain(), location.getResourcePath() + ".mcmeta");
/*     */   }
/*     */   
/*     */   static class InputStreamLeakedResourceLogger
/*     */     extends InputStream
/*     */   {
/*     */     private final InputStream inputStream;
/*     */     private final String message;
/*     */     private boolean isClosed = false;
/*     */     
/*     */     public InputStreamLeakedResourceLogger(InputStream p_i46093_1_, ResourceLocation location, String resourcePack) {
/* 107 */       this.inputStream = p_i46093_1_;
/* 108 */       ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
/* 109 */       (new Exception()).printStackTrace(new PrintStream(bytearrayoutputstream));
/* 110 */       this.message = "Leaked resource: '" + location + "' loaded from pack: '" + resourcePack + "'\n" + bytearrayoutputstream.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 115 */       this.inputStream.close();
/* 116 */       this.isClosed = true;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void finalize() throws Throwable {
/* 121 */       if (!this.isClosed)
/*     */       {
/* 123 */         FallbackResourceManager.logger.warn(this.message);
/*     */       }
/*     */       
/* 126 */       super.finalize();
/*     */     }
/*     */ 
/*     */     
/*     */     public int read() throws IOException {
/* 131 */       return this.inputStream.read();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\FallbackResourceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */