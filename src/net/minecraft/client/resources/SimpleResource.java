/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.resources.data.IMetadataSection;
/*     */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ public class SimpleResource
/*     */   implements IResource
/*     */ {
/*  19 */   private final Map<String, IMetadataSection> mapMetadataSections = Maps.newHashMap();
/*     */   
/*     */   private final String resourcePackName;
/*     */   private final ResourceLocation srResourceLocation;
/*     */   private final InputStream resourceInputStream;
/*     */   private final InputStream mcmetaInputStream;
/*     */   private final IMetadataSerializer srMetadataSerializer;
/*     */   private boolean mcmetaJsonChecked;
/*     */   private JsonObject mcmetaJson;
/*     */   
/*     */   public SimpleResource(String resourcePackNameIn, ResourceLocation srResourceLocationIn, InputStream resourceInputStreamIn, InputStream mcmetaInputStreamIn, IMetadataSerializer srMetadataSerializerIn) {
/*  30 */     this.resourcePackName = resourcePackNameIn;
/*  31 */     this.srResourceLocation = srResourceLocationIn;
/*  32 */     this.resourceInputStream = resourceInputStreamIn;
/*  33 */     this.mcmetaInputStream = mcmetaInputStreamIn;
/*  34 */     this.srMetadataSerializer = srMetadataSerializerIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getResourceLocation() {
/*  39 */     return this.srResourceLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getInputStream() {
/*  44 */     return this.resourceInputStream;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMetadata() {
/*  49 */     return (this.mcmetaInputStream != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends IMetadataSection> T getMetadata(String p_110526_1_) {
/*  54 */     if (!hasMetadata())
/*     */     {
/*  56 */       return (T)null;
/*     */     }
/*     */ 
/*     */     
/*  60 */     if (this.mcmetaJson == null && !this.mcmetaJsonChecked) {
/*     */       
/*  62 */       this.mcmetaJsonChecked = true;
/*  63 */       BufferedReader bufferedreader = null;
/*     */ 
/*     */       
/*     */       try {
/*  67 */         bufferedreader = new BufferedReader(new InputStreamReader(this.mcmetaInputStream));
/*  68 */         this.mcmetaJson = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
/*     */       }
/*     */       finally {
/*     */         
/*  72 */         IOUtils.closeQuietly(bufferedreader);
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     IMetadataSection iMetadataSection = this.mapMetadataSections.get(p_110526_1_);
/*     */     
/*  78 */     if (iMetadataSection == null)
/*     */     {
/*  80 */       iMetadataSection = this.srMetadataSerializer.parseMetadataSection(p_110526_1_, this.mcmetaJson);
/*     */     }
/*     */     
/*  83 */     return (T)iMetadataSection;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getResourcePackName() {
/*  89 */     return this.resourcePackName;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  94 */     if (this == p_equals_1_)
/*     */     {
/*  96 */       return true;
/*     */     }
/*  98 */     if (!(p_equals_1_ instanceof SimpleResource))
/*     */     {
/* 100 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 104 */     SimpleResource simpleresource = (SimpleResource)p_equals_1_;
/*     */     
/* 106 */     if (this.srResourceLocation != null) {
/*     */       
/* 108 */       if (!this.srResourceLocation.equals(simpleresource.srResourceLocation))
/*     */       {
/* 110 */         return false;
/*     */       }
/*     */     }
/* 113 */     else if (simpleresource.srResourceLocation != null) {
/*     */       
/* 115 */       return false;
/*     */     } 
/*     */     
/* 118 */     if (this.resourcePackName != null) {
/*     */       
/* 120 */       if (!this.resourcePackName.equals(simpleresource.resourcePackName))
/*     */       {
/* 122 */         return false;
/*     */       }
/*     */     }
/* 125 */     else if (simpleresource.resourcePackName != null) {
/*     */       
/* 127 */       return false;
/*     */     } 
/*     */     
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 136 */     int i = (this.resourcePackName != null) ? this.resourcePackName.hashCode() : 0;
/* 137 */     i = 31 * i + ((this.srResourceLocation != null) ? this.srResourceLocation.hashCode() : 0);
/* 138 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\SimpleResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */