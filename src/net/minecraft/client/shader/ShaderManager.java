/*     */ package net.minecraft.client.shader;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.util.JsonBlendingMode;
/*     */ import net.minecraft.client.util.JsonException;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class ShaderManager
/*     */ {
/*  29 */   private static final Logger logger = LogManager.getLogger();
/*  30 */   private static final ShaderDefault defaultShaderUniform = new ShaderDefault();
/*  31 */   private static ShaderManager staticShaderManager = null;
/*  32 */   private static int currentProgram = -1;
/*     */   private static boolean field_148000_e = true;
/*  34 */   private final Map<String, Object> shaderSamplers = Maps.newHashMap();
/*  35 */   private final List<String> samplerNames = Lists.newArrayList();
/*  36 */   private final List<Integer> shaderSamplerLocations = Lists.newArrayList();
/*  37 */   private final List<ShaderUniform> shaderUniforms = Lists.newArrayList();
/*  38 */   private final List<Integer> shaderUniformLocations = Lists.newArrayList();
/*  39 */   private final Map<String, ShaderUniform> mappedShaderUniforms = Maps.newHashMap();
/*     */   
/*     */   private final int program;
/*     */   private final String programFilename;
/*     */   private final boolean useFaceCulling;
/*     */   private boolean isDirty;
/*     */   private final JsonBlendingMode field_148016_p;
/*     */   private final List<Integer> attribLocations;
/*     */   private final List<String> attributes;
/*     */   private final ShaderLoader vertexShaderLoader;
/*     */   private final ShaderLoader fragmentShaderLoader;
/*     */   
/*     */   public ShaderManager(IResourceManager resourceManager, String programName) throws IOException {
/*  52 */     JsonParser jsonparser = new JsonParser();
/*  53 */     ResourceLocation resourcelocation = new ResourceLocation("shaders/program/" + programName + ".json");
/*  54 */     this.programFilename = programName;
/*  55 */     InputStream inputstream = null;
/*     */ 
/*     */     
/*     */     try {
/*  59 */       inputstream = resourceManager.getResource(resourcelocation).getInputStream();
/*  60 */       JsonObject jsonobject = jsonparser.parse(IOUtils.toString(inputstream, Charsets.UTF_8)).getAsJsonObject();
/*  61 */       String s = JsonUtils.getString(jsonobject, "vertex");
/*  62 */       String s1 = JsonUtils.getString(jsonobject, "fragment");
/*  63 */       JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "samplers", (JsonArray)null);
/*     */       
/*  65 */       if (jsonarray != null) {
/*     */         
/*  67 */         int i = 0;
/*     */         
/*  69 */         for (JsonElement jsonelement : jsonarray) {
/*     */ 
/*     */           
/*     */           try {
/*  73 */             parseSampler(jsonelement);
/*     */           }
/*  75 */           catch (Exception exception2) {
/*     */             
/*  77 */             JsonException jsonexception1 = JsonException.func_151379_a(exception2);
/*  78 */             jsonexception1.func_151380_a("samplers[" + i + "]");
/*  79 */             throw jsonexception1;
/*     */           } 
/*     */           
/*  82 */           i++;
/*     */         } 
/*     */       } 
/*     */       
/*  86 */       JsonArray jsonarray1 = JsonUtils.getJsonArray(jsonobject, "attributes", (JsonArray)null);
/*     */       
/*  88 */       if (jsonarray1 != null) {
/*     */         
/*  90 */         int j = 0;
/*  91 */         this.attribLocations = Lists.newArrayListWithCapacity(jsonarray1.size());
/*  92 */         this.attributes = Lists.newArrayListWithCapacity(jsonarray1.size());
/*     */         
/*  94 */         for (JsonElement jsonelement1 : jsonarray1)
/*     */         {
/*     */           
/*     */           try {
/*  98 */             this.attributes.add(JsonUtils.getString(jsonelement1, "attribute"));
/*     */           }
/* 100 */           catch (Exception exception1) {
/*     */             
/* 102 */             JsonException jsonexception2 = JsonException.func_151379_a(exception1);
/* 103 */             jsonexception2.func_151380_a("attributes[" + j + "]");
/* 104 */             throw jsonexception2;
/*     */           } 
/*     */           
/* 107 */           j++;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 112 */         this.attribLocations = null;
/* 113 */         this.attributes = null;
/*     */       } 
/*     */       
/* 116 */       JsonArray jsonarray2 = JsonUtils.getJsonArray(jsonobject, "uniforms", (JsonArray)null);
/*     */       
/* 118 */       if (jsonarray2 != null) {
/*     */         
/* 120 */         int k = 0;
/*     */         
/* 122 */         for (JsonElement jsonelement2 : jsonarray2) {
/*     */ 
/*     */           
/*     */           try {
/* 126 */             parseUniform(jsonelement2);
/*     */           }
/* 128 */           catch (Exception exception) {
/*     */             
/* 130 */             JsonException jsonexception3 = JsonException.func_151379_a(exception);
/* 131 */             jsonexception3.func_151380_a("uniforms[" + k + "]");
/* 132 */             throw jsonexception3;
/*     */           } 
/*     */           
/* 135 */           k++;
/*     */         } 
/*     */       } 
/*     */       
/* 139 */       this.field_148016_p = JsonBlendingMode.func_148110_a(JsonUtils.getJsonObject(jsonobject, "blend", (JsonObject)null));
/* 140 */       this.useFaceCulling = JsonUtils.getBoolean(jsonobject, "cull", true);
/* 141 */       this.vertexShaderLoader = ShaderLoader.loadShader(resourceManager, ShaderLoader.ShaderType.VERTEX, s);
/* 142 */       this.fragmentShaderLoader = ShaderLoader.loadShader(resourceManager, ShaderLoader.ShaderType.FRAGMENT, s1);
/* 143 */       this.program = ShaderLinkHelper.getStaticShaderLinkHelper().createProgram();
/* 144 */       ShaderLinkHelper.getStaticShaderLinkHelper().linkProgram(this);
/* 145 */       setupUniforms();
/*     */       
/* 147 */       if (this.attributes != null)
/*     */       {
/* 149 */         for (String s2 : this.attributes)
/*     */         {
/* 151 */           int l = OpenGlHelper.glGetAttribLocation(this.program, s2);
/* 152 */           this.attribLocations.add(Integer.valueOf(l));
/*     */         }
/*     */       
/*     */       }
/* 156 */     } catch (Exception exception3) {
/*     */       
/* 158 */       JsonException jsonexception = JsonException.func_151379_a(exception3);
/* 159 */       jsonexception.func_151381_b(resourcelocation.getResourcePath());
/* 160 */       throw jsonexception;
/*     */     }
/*     */     finally {
/*     */       
/* 164 */       IOUtils.closeQuietly(inputstream);
/*     */     } 
/*     */     
/* 167 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteShader() {
/* 172 */     ShaderLinkHelper.getStaticShaderLinkHelper().deleteShader(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void endShader() {
/* 177 */     OpenGlHelper.glUseProgram(0);
/* 178 */     currentProgram = -1;
/* 179 */     staticShaderManager = null;
/* 180 */     field_148000_e = true;
/*     */     
/* 182 */     for (int i = 0; i < this.shaderSamplerLocations.size(); i++) {
/*     */       
/* 184 */       if (this.shaderSamplers.get(this.samplerNames.get(i)) != null) {
/*     */         
/* 186 */         GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + i);
/* 187 */         GlStateManager.bindTexture(0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void useShader() {
/* 194 */     this.isDirty = false;
/* 195 */     staticShaderManager = this;
/* 196 */     this.field_148016_p.func_148109_a();
/*     */     
/* 198 */     if (this.program != currentProgram) {
/*     */       
/* 200 */       OpenGlHelper.glUseProgram(this.program);
/* 201 */       currentProgram = this.program;
/*     */     } 
/*     */     
/* 204 */     if (this.useFaceCulling) {
/*     */       
/* 206 */       GlStateManager.enableCull();
/*     */     }
/*     */     else {
/*     */       
/* 210 */       GlStateManager.disableCull();
/*     */     } 
/*     */     
/* 213 */     for (int i = 0; i < this.shaderSamplerLocations.size(); i++) {
/*     */       
/* 215 */       if (this.shaderSamplers.get(this.samplerNames.get(i)) != null) {
/*     */         
/* 217 */         GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + i);
/* 218 */         GlStateManager.enableTexture2D();
/* 219 */         Object object = this.shaderSamplers.get(this.samplerNames.get(i));
/* 220 */         int j = -1;
/*     */         
/* 222 */         if (object instanceof Framebuffer) {
/*     */           
/* 224 */           j = ((Framebuffer)object).framebufferTexture;
/*     */         }
/* 226 */         else if (object instanceof ITextureObject) {
/*     */           
/* 228 */           j = ((ITextureObject)object).getGlTextureId();
/*     */         }
/* 230 */         else if (object instanceof Integer) {
/*     */           
/* 232 */           j = ((Integer)object).intValue();
/*     */         } 
/*     */         
/* 235 */         if (j != -1) {
/*     */           
/* 237 */           GlStateManager.bindTexture(j);
/* 238 */           OpenGlHelper.glUniform1i(OpenGlHelper.glGetUniformLocation(this.program, this.samplerNames.get(i)), i);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 243 */     for (ShaderUniform shaderuniform : this.shaderUniforms)
/*     */     {
/* 245 */       shaderuniform.upload();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 251 */     this.isDirty = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShaderUniform getShaderUniform(String p_147991_1_) {
/* 259 */     return this.mappedShaderUniforms.containsKey(p_147991_1_) ? this.mappedShaderUniforms.get(p_147991_1_) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShaderUniform getShaderUniformOrDefault(String p_147984_1_) {
/* 267 */     return this.mappedShaderUniforms.containsKey(p_147984_1_) ? this.mappedShaderUniforms.get(p_147984_1_) : defaultShaderUniform;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupUniforms() {
/* 275 */     int i = 0;
/*     */     
/* 277 */     for (int j = 0; i < this.samplerNames.size(); j++) {
/*     */       
/* 279 */       String s = this.samplerNames.get(i);
/* 280 */       int k = OpenGlHelper.glGetUniformLocation(this.program, s);
/*     */       
/* 282 */       if (k == -1) {
/*     */         
/* 284 */         logger.warn("Shader " + this.programFilename + "could not find sampler named " + s + " in the specified shader program.");
/* 285 */         this.shaderSamplers.remove(s);
/* 286 */         this.samplerNames.remove(j);
/* 287 */         j--;
/*     */       }
/*     */       else {
/*     */         
/* 291 */         this.shaderSamplerLocations.add(Integer.valueOf(k));
/*     */       } 
/*     */       
/* 294 */       i++;
/*     */     } 
/*     */     
/* 297 */     for (ShaderUniform shaderuniform : this.shaderUniforms) {
/*     */       
/* 299 */       String s1 = shaderuniform.getShaderName();
/* 300 */       int l = OpenGlHelper.glGetUniformLocation(this.program, s1);
/*     */       
/* 302 */       if (l == -1) {
/*     */         
/* 304 */         logger.warn("Could not find uniform named " + s1 + " in the specified shader program.");
/*     */         
/*     */         continue;
/*     */       } 
/* 308 */       this.shaderUniformLocations.add(Integer.valueOf(l));
/* 309 */       shaderuniform.setUniformLocation(l);
/* 310 */       this.mappedShaderUniforms.put(s1, shaderuniform);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void parseSampler(JsonElement p_147996_1_) {
/* 316 */     JsonObject jsonobject = JsonUtils.getJsonObject(p_147996_1_, "sampler");
/* 317 */     String s = JsonUtils.getString(jsonobject, "name");
/*     */     
/* 319 */     if (!JsonUtils.isString(jsonobject, "file")) {
/*     */       
/* 321 */       this.shaderSamplers.put(s, null);
/* 322 */       this.samplerNames.add(s);
/*     */     }
/*     */     else {
/*     */       
/* 326 */       this.samplerNames.add(s);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSamplerTexture(String p_147992_1_, Object p_147992_2_) {
/* 335 */     if (this.shaderSamplers.containsKey(p_147992_1_))
/*     */     {
/* 337 */       this.shaderSamplers.remove(p_147992_1_);
/*     */     }
/*     */     
/* 340 */     this.shaderSamplers.put(p_147992_1_, p_147992_2_);
/* 341 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   private void parseUniform(JsonElement p_147987_1_) throws JsonException {
/* 346 */     JsonObject jsonobject = JsonUtils.getJsonObject(p_147987_1_, "uniform");
/* 347 */     String s = JsonUtils.getString(jsonobject, "name");
/* 348 */     int i = ShaderUniform.parseType(JsonUtils.getString(jsonobject, "type"));
/* 349 */     int j = JsonUtils.getInt(jsonobject, "count");
/* 350 */     float[] afloat = new float[Math.max(j, 16)];
/* 351 */     JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "values");
/*     */     
/* 353 */     if (jsonarray.size() != j && jsonarray.size() > 1)
/*     */     {
/* 355 */       throw new JsonException("Invalid amount of values specified (expected " + j + ", found " + jsonarray.size() + ")");
/*     */     }
/*     */ 
/*     */     
/* 359 */     int k = 0;
/*     */     
/* 361 */     for (JsonElement jsonelement : jsonarray) {
/*     */ 
/*     */       
/*     */       try {
/* 365 */         afloat[k] = JsonUtils.getFloat(jsonelement, "value");
/*     */       }
/* 367 */       catch (Exception exception) {
/*     */         
/* 369 */         JsonException jsonexception = JsonException.func_151379_a(exception);
/* 370 */         jsonexception.func_151380_a("values[" + k + "]");
/* 371 */         throw jsonexception;
/*     */       } 
/*     */       
/* 374 */       k++;
/*     */     } 
/*     */     
/* 377 */     if (j > 1 && jsonarray.size() == 1)
/*     */     {
/* 379 */       while (k < j) {
/*     */         
/* 381 */         afloat[k] = afloat[0];
/* 382 */         k++;
/*     */       } 
/*     */     }
/*     */     
/* 386 */     int l = (j > 1 && j <= 4 && i < 8) ? (j - 1) : 0;
/* 387 */     ShaderUniform shaderuniform = new ShaderUniform(s, i + l, j, this);
/*     */     
/* 389 */     if (i <= 3) {
/*     */       
/* 391 */       shaderuniform.set((int)afloat[0], (int)afloat[1], (int)afloat[2], (int)afloat[3]);
/*     */     }
/* 393 */     else if (i <= 7) {
/*     */       
/* 395 */       shaderuniform.func_148092_b(afloat[0], afloat[1], afloat[2], afloat[3]);
/*     */     }
/*     */     else {
/*     */       
/* 399 */       shaderuniform.set(afloat);
/*     */     } 
/*     */     
/* 402 */     this.shaderUniforms.add(shaderuniform);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ShaderLoader getVertexShaderLoader() {
/* 408 */     return this.vertexShaderLoader;
/*     */   }
/*     */ 
/*     */   
/*     */   public ShaderLoader getFragmentShaderLoader() {
/* 413 */     return this.fragmentShaderLoader;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getProgram() {
/* 418 */     return this.program;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\shader\ShaderManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */