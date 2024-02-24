/*     */ package net.minecraft.client.shader;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.util.JsonException;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.vector.Matrix4f;
/*     */ 
/*     */ public class ShaderGroup {
/*     */   public final Framebuffer mainFramebuffer;
/*  29 */   private final List<Shader> listShaders = Lists.newArrayList(); private final IResourceManager resourceManager; private final String shaderGroupName;
/*  30 */   private final Map<String, Framebuffer> mapFramebuffers = Maps.newHashMap();
/*  31 */   private final List<Framebuffer> listFramebuffers = Lists.newArrayList();
/*     */   
/*     */   private Matrix4f projectionMatrix;
/*     */   private int mainFramebufferWidth;
/*     */   private int mainFramebufferHeight;
/*     */   private float field_148036_j;
/*     */   private float field_148037_k;
/*     */   
/*     */   public ShaderGroup(TextureManager p_i1050_1_, IResourceManager p_i1050_2_, Framebuffer p_i1050_3_, ResourceLocation p_i1050_4_) throws IOException, JsonSyntaxException {
/*  40 */     this.resourceManager = p_i1050_2_;
/*  41 */     this.mainFramebuffer = p_i1050_3_;
/*  42 */     this.field_148036_j = 0.0F;
/*  43 */     this.field_148037_k = 0.0F;
/*  44 */     this.mainFramebufferWidth = p_i1050_3_.framebufferWidth;
/*  45 */     this.mainFramebufferHeight = p_i1050_3_.framebufferHeight;
/*  46 */     this.shaderGroupName = p_i1050_4_.toString();
/*  47 */     resetProjectionMatrix();
/*  48 */     parseGroup(p_i1050_1_, p_i1050_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void parseGroup(TextureManager p_152765_1_, ResourceLocation p_152765_2_) throws IOException, JsonSyntaxException {
/*  53 */     JsonParser jsonparser = new JsonParser();
/*  54 */     InputStream inputstream = null;
/*     */ 
/*     */     
/*     */     try {
/*  58 */       IResource iresource = this.resourceManager.getResource(p_152765_2_);
/*  59 */       inputstream = iresource.getInputStream();
/*  60 */       JsonObject jsonobject = jsonparser.parse(IOUtils.toString(inputstream, Charsets.UTF_8)).getAsJsonObject();
/*     */       
/*  62 */       if (JsonUtils.isJsonArray(jsonobject, "targets")) {
/*     */         
/*  64 */         JsonArray jsonarray = jsonobject.getAsJsonArray("targets");
/*  65 */         int i = 0;
/*     */         
/*  67 */         for (JsonElement jsonelement : jsonarray) {
/*     */ 
/*     */           
/*     */           try {
/*  71 */             initTarget(jsonelement);
/*     */           }
/*  73 */           catch (Exception exception1) {
/*     */             
/*  75 */             JsonException jsonexception1 = JsonException.func_151379_a(exception1);
/*  76 */             jsonexception1.func_151380_a("targets[" + i + "]");
/*  77 */             throw jsonexception1;
/*     */           } 
/*     */           
/*  80 */           i++;
/*     */         } 
/*     */       } 
/*     */       
/*  84 */       if (JsonUtils.isJsonArray(jsonobject, "passes")) {
/*     */         
/*  86 */         JsonArray jsonarray1 = jsonobject.getAsJsonArray("passes");
/*  87 */         int j = 0;
/*     */         
/*  89 */         for (JsonElement jsonelement1 : jsonarray1)
/*     */         {
/*     */           
/*     */           try {
/*  93 */             parsePass(p_152765_1_, jsonelement1);
/*     */           }
/*  95 */           catch (Exception exception) {
/*     */             
/*  97 */             JsonException jsonexception2 = JsonException.func_151379_a(exception);
/*  98 */             jsonexception2.func_151380_a("passes[" + j + "]");
/*  99 */             throw jsonexception2;
/*     */           } 
/*     */           
/* 102 */           j++;
/*     */         }
/*     */       
/*     */       } 
/* 106 */     } catch (Exception exception2) {
/*     */       
/* 108 */       JsonException jsonexception = JsonException.func_151379_a(exception2);
/* 109 */       jsonexception.func_151381_b(p_152765_2_.getResourcePath());
/* 110 */       throw jsonexception;
/*     */     }
/*     */     finally {
/*     */       
/* 114 */       IOUtils.closeQuietly(inputstream);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void initTarget(JsonElement p_148027_1_) throws JsonException {
/* 120 */     if (JsonUtils.isString(p_148027_1_)) {
/*     */       
/* 122 */       addFramebuffer(p_148027_1_.getAsString(), this.mainFramebufferWidth, this.mainFramebufferHeight);
/*     */     }
/*     */     else {
/*     */       
/* 126 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_148027_1_, "target");
/* 127 */       String s = JsonUtils.getString(jsonobject, "name");
/* 128 */       int i = JsonUtils.getInt(jsonobject, "width", this.mainFramebufferWidth);
/* 129 */       int j = JsonUtils.getInt(jsonobject, "height", this.mainFramebufferHeight);
/*     */       
/* 131 */       if (this.mapFramebuffers.containsKey(s))
/*     */       {
/* 133 */         throw new JsonException(s + " is already defined");
/*     */       }
/*     */       
/* 136 */       addFramebuffer(s, i, j);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void parsePass(TextureManager p_152764_1_, JsonElement p_152764_2_) throws IOException {
/* 142 */     JsonObject jsonobject = JsonUtils.getJsonObject(p_152764_2_, "pass");
/* 143 */     String s = JsonUtils.getString(jsonobject, "name");
/* 144 */     String s1 = JsonUtils.getString(jsonobject, "intarget");
/* 145 */     String s2 = JsonUtils.getString(jsonobject, "outtarget");
/* 146 */     Framebuffer framebuffer = getFramebuffer(s1);
/* 147 */     Framebuffer framebuffer1 = getFramebuffer(s2);
/*     */     
/* 149 */     if (framebuffer == null)
/*     */     {
/* 151 */       throw new JsonException("Input target '" + s1 + "' does not exist");
/*     */     }
/* 153 */     if (framebuffer1 == null)
/*     */     {
/* 155 */       throw new JsonException("Output target '" + s2 + "' does not exist");
/*     */     }
/*     */ 
/*     */     
/* 159 */     Shader shader = addShader(s, framebuffer, framebuffer1);
/* 160 */     JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "auxtargets", (JsonArray)null);
/*     */     
/* 162 */     if (jsonarray != null) {
/*     */       
/* 164 */       int i = 0;
/*     */       
/* 166 */       for (JsonElement jsonelement : jsonarray) {
/*     */ 
/*     */         
/*     */         try {
/* 170 */           JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonelement, "auxtarget");
/* 171 */           String s4 = JsonUtils.getString(jsonobject1, "name");
/* 172 */           String s3 = JsonUtils.getString(jsonobject1, "id");
/* 173 */           Framebuffer framebuffer2 = getFramebuffer(s3);
/*     */           
/* 175 */           if (framebuffer2 == null)
/*     */           {
/* 177 */             ResourceLocation resourcelocation = new ResourceLocation("textures/effect/" + s3 + ".png");
/*     */ 
/*     */             
/*     */             try {
/* 181 */               this.resourceManager.getResource(resourcelocation);
/*     */             }
/* 183 */             catch (FileNotFoundException var24) {
/*     */               
/* 185 */               throw new JsonException("Render target or texture '" + s3 + "' does not exist");
/*     */             } 
/*     */             
/* 188 */             p_152764_1_.bindTexture(resourcelocation);
/* 189 */             ITextureObject itextureobject = p_152764_1_.getTexture(resourcelocation);
/* 190 */             int j = JsonUtils.getInt(jsonobject1, "width");
/* 191 */             int k = JsonUtils.getInt(jsonobject1, "height");
/* 192 */             boolean flag = JsonUtils.getBoolean(jsonobject1, "bilinear");
/*     */             
/* 194 */             if (flag) {
/*     */               
/* 196 */               GL11.glTexParameteri(3553, 10241, 9729);
/* 197 */               GL11.glTexParameteri(3553, 10240, 9729);
/*     */             }
/*     */             else {
/*     */               
/* 201 */               GL11.glTexParameteri(3553, 10241, 9728);
/* 202 */               GL11.glTexParameteri(3553, 10240, 9728);
/*     */             } 
/*     */             
/* 205 */             shader.addAuxFramebuffer(s4, Integer.valueOf(itextureobject.getGlTextureId()), j, k);
/*     */           }
/*     */           else
/*     */           {
/* 209 */             shader.addAuxFramebuffer(s4, framebuffer2, framebuffer2.framebufferTextureWidth, framebuffer2.framebufferTextureHeight);
/*     */           }
/*     */         
/* 212 */         } catch (Exception exception1) {
/*     */           
/* 214 */           JsonException jsonexception = JsonException.func_151379_a(exception1);
/* 215 */           jsonexception.func_151380_a("auxtargets[" + i + "]");
/* 216 */           throw jsonexception;
/*     */         } 
/*     */         
/* 219 */         i++;
/*     */       } 
/*     */     } 
/*     */     
/* 223 */     JsonArray jsonarray1 = JsonUtils.getJsonArray(jsonobject, "uniforms", (JsonArray)null);
/*     */     
/* 225 */     if (jsonarray1 != null) {
/*     */       
/* 227 */       int l = 0;
/*     */       
/* 229 */       for (JsonElement jsonelement1 : jsonarray1) {
/*     */ 
/*     */         
/*     */         try {
/* 233 */           initUniform(jsonelement1);
/*     */         }
/* 235 */         catch (Exception exception) {
/*     */           
/* 237 */           JsonException jsonexception1 = JsonException.func_151379_a(exception);
/* 238 */           jsonexception1.func_151380_a("uniforms[" + l + "]");
/* 239 */           throw jsonexception1;
/*     */         } 
/*     */         
/* 242 */         l++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initUniform(JsonElement p_148028_1_) throws JsonException {
/* 250 */     JsonObject jsonobject = JsonUtils.getJsonObject(p_148028_1_, "uniform");
/* 251 */     String s = JsonUtils.getString(jsonobject, "name");
/* 252 */     ShaderUniform shaderuniform = ((Shader)this.listShaders.get(this.listShaders.size() - 1)).getShaderManager().getShaderUniform(s);
/*     */     
/* 254 */     if (shaderuniform == null)
/*     */     {
/* 256 */       throw new JsonException("Uniform '" + s + "' does not exist");
/*     */     }
/*     */ 
/*     */     
/* 260 */     float[] afloat = new float[4];
/* 261 */     int i = 0;
/*     */     
/* 263 */     for (JsonElement jsonelement : JsonUtils.getJsonArray(jsonobject, "values")) {
/*     */ 
/*     */       
/*     */       try {
/* 267 */         afloat[i] = JsonUtils.getFloat(jsonelement, "value");
/*     */       }
/* 269 */       catch (Exception exception) {
/*     */         
/* 271 */         JsonException jsonexception = JsonException.func_151379_a(exception);
/* 272 */         jsonexception.func_151380_a("values[" + i + "]");
/* 273 */         throw jsonexception;
/*     */       } 
/*     */       
/* 276 */       i++;
/*     */     } 
/*     */     
/* 279 */     switch (i) {
/*     */       default:
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 286 */         shaderuniform.set(afloat[0]);
/*     */ 
/*     */       
/*     */       case 2:
/* 290 */         shaderuniform.set(afloat[0], afloat[1]);
/*     */ 
/*     */       
/*     */       case 3:
/* 294 */         shaderuniform.set(afloat[0], afloat[1], afloat[2]);
/*     */       case 4:
/*     */         break;
/*     */     } 
/* 298 */     shaderuniform.set(afloat[0], afloat[1], afloat[2], afloat[3]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Framebuffer getFramebufferRaw(String p_177066_1_) {
/* 305 */     return this.mapFramebuffers.get(p_177066_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFramebuffer(String p_148020_1_, int p_148020_2_, int p_148020_3_) {
/* 310 */     Framebuffer framebuffer = new Framebuffer(p_148020_2_, p_148020_3_, true);
/* 311 */     framebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 312 */     this.mapFramebuffers.put(p_148020_1_, framebuffer);
/*     */     
/* 314 */     if (p_148020_2_ == this.mainFramebufferWidth && p_148020_3_ == this.mainFramebufferHeight)
/*     */     {
/* 316 */       this.listFramebuffers.add(framebuffer);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteShaderGroup() {
/* 322 */     for (Framebuffer framebuffer : this.mapFramebuffers.values())
/*     */     {
/* 324 */       framebuffer.deleteFramebuffer();
/*     */     }
/*     */     
/* 327 */     for (Shader shader : this.listShaders)
/*     */     {
/* 329 */       shader.deleteShader();
/*     */     }
/*     */     
/* 332 */     this.listShaders.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public Shader addShader(String p_148023_1_, Framebuffer p_148023_2_, Framebuffer p_148023_3_) throws IOException {
/* 337 */     Shader shader = new Shader(this.resourceManager, p_148023_1_, p_148023_2_, p_148023_3_);
/* 338 */     this.listShaders.add(this.listShaders.size(), shader);
/* 339 */     return shader;
/*     */   }
/*     */ 
/*     */   
/*     */   private void resetProjectionMatrix() {
/* 344 */     this.projectionMatrix = new Matrix4f();
/* 345 */     this.projectionMatrix.setIdentity();
/* 346 */     this.projectionMatrix.m00 = 2.0F / this.mainFramebuffer.framebufferTextureWidth;
/* 347 */     this.projectionMatrix.m11 = 2.0F / -this.mainFramebuffer.framebufferTextureHeight;
/* 348 */     this.projectionMatrix.m22 = -0.0020001999F;
/* 349 */     this.projectionMatrix.m33 = 1.0F;
/* 350 */     this.projectionMatrix.m03 = -1.0F;
/* 351 */     this.projectionMatrix.m13 = 1.0F;
/* 352 */     this.projectionMatrix.m23 = -1.0001999F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void createBindFramebuffers(int width, int height) {
/* 357 */     this.mainFramebufferWidth = this.mainFramebuffer.framebufferTextureWidth;
/* 358 */     this.mainFramebufferHeight = this.mainFramebuffer.framebufferTextureHeight;
/* 359 */     resetProjectionMatrix();
/*     */     
/* 361 */     for (Shader shader : this.listShaders)
/*     */     {
/* 363 */       shader.setProjectionMatrix(this.projectionMatrix);
/*     */     }
/*     */     
/* 366 */     for (Framebuffer framebuffer : this.listFramebuffers)
/*     */     {
/* 368 */       framebuffer.createBindFramebuffer(width, height);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadShaderGroup(float partialTicks) {
/* 374 */     if (partialTicks < this.field_148037_k) {
/*     */       
/* 376 */       this.field_148036_j += 1.0F - this.field_148037_k;
/* 377 */       this.field_148036_j += partialTicks;
/*     */     }
/*     */     else {
/*     */       
/* 381 */       this.field_148036_j += partialTicks - this.field_148037_k;
/*     */     } 
/*     */     
/* 384 */     for (this.field_148037_k = partialTicks; this.field_148036_j > 20.0F; this.field_148036_j -= 20.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 389 */     for (Shader shader : this.listShaders)
/*     */     {
/* 391 */       shader.loadShader(this.field_148036_j / 20.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getShaderGroupName() {
/* 397 */     return this.shaderGroupName;
/*     */   }
/*     */ 
/*     */   
/*     */   private Framebuffer getFramebuffer(String p_148017_1_) {
/* 402 */     return (p_148017_1_ == null) ? null : (p_148017_1_.equals("minecraft:main") ? this.mainFramebuffer : this.mapFramebuffers.get(p_148017_1_));
/*     */   }
/*     */   
/*     */   public List<Shader> getShaders() {
/* 406 */     return this.listShaders;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\shader\ShaderGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */