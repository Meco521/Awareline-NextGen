/*     */ package net.minecraft.client.renderer.block.model;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.io.Reader;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.resources.model.ModelRotation;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class ModelBlockDefinition {
/*  19 */   static final Gson GSON = (new GsonBuilder()).registerTypeAdapter(ModelBlockDefinition.class, new Deserializer()).registerTypeAdapter(Variant.class, new Variant.Deserializer()).create();
/*  20 */   private final Map<String, Variants> mapVariants = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public static ModelBlockDefinition parseFromReader(Reader p_178331_0_) {
/*  24 */     return (ModelBlockDefinition)GSON.fromJson(p_178331_0_, ModelBlockDefinition.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBlockDefinition(Collection<Variants> p_i46221_1_) {
/*  29 */     for (Variants modelblockdefinition$variants : p_i46221_1_)
/*     */     {
/*  31 */       this.mapVariants.put(modelblockdefinition$variants.name, modelblockdefinition$variants);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBlockDefinition(List<ModelBlockDefinition> p_i46222_1_) {
/*  37 */     for (ModelBlockDefinition modelblockdefinition : p_i46222_1_)
/*     */     {
/*  39 */       this.mapVariants.putAll(modelblockdefinition.mapVariants);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Variants getVariants(String p_178330_1_) {
/*  45 */     Variants modelblockdefinition$variants = this.mapVariants.get(p_178330_1_);
/*     */     
/*  47 */     if (modelblockdefinition$variants == null)
/*     */     {
/*  49 */       throw new MissingVariantException();
/*     */     }
/*     */ 
/*     */     
/*  53 */     return modelblockdefinition$variants;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  59 */     if (this == p_equals_1_)
/*     */     {
/*  61 */       return true;
/*     */     }
/*  63 */     if (p_equals_1_ instanceof ModelBlockDefinition) {
/*     */       
/*  65 */       ModelBlockDefinition modelblockdefinition = (ModelBlockDefinition)p_equals_1_;
/*  66 */       return this.mapVariants.equals(modelblockdefinition.mapVariants);
/*     */     } 
/*     */ 
/*     */     
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  76 */     return this.mapVariants.hashCode();
/*     */   }
/*     */   
/*     */   public static class Deserializer
/*     */     implements JsonDeserializer<ModelBlockDefinition>
/*     */   {
/*     */     public ModelBlockDefinition deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/*  83 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/*  84 */       List<ModelBlockDefinition.Variants> list = parseVariantsList(p_deserialize_3_, jsonobject);
/*  85 */       return new ModelBlockDefinition(list);
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<ModelBlockDefinition.Variants> parseVariantsList(JsonDeserializationContext p_178334_1_, JsonObject p_178334_2_) {
/*  90 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_178334_2_, "variants");
/*  91 */       List<ModelBlockDefinition.Variants> list = Lists.newArrayList();
/*     */       
/*  93 */       for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet())
/*     */       {
/*  95 */         list.add(parseVariants(p_178334_1_, entry));
/*     */       }
/*     */       
/*  98 */       return list;
/*     */     }
/*     */ 
/*     */     
/*     */     protected ModelBlockDefinition.Variants parseVariants(JsonDeserializationContext p_178335_1_, Map.Entry<String, JsonElement> p_178335_2_) {
/* 103 */       String s = p_178335_2_.getKey();
/* 104 */       List<ModelBlockDefinition.Variant> list = Lists.newArrayList();
/* 105 */       JsonElement jsonelement = p_178335_2_.getValue();
/*     */       
/* 107 */       if (jsonelement.isJsonArray()) {
/*     */         
/* 109 */         for (JsonElement jsonelement1 : jsonelement.getAsJsonArray())
/*     */         {
/* 111 */           list.add((ModelBlockDefinition.Variant)p_178335_1_.deserialize(jsonelement1, ModelBlockDefinition.Variant.class));
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 116 */         list.add((ModelBlockDefinition.Variant)p_178335_1_.deserialize(jsonelement, ModelBlockDefinition.Variant.class));
/*     */       } 
/*     */       
/* 119 */       return new ModelBlockDefinition.Variants(s, list);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class MissingVariantException
/*     */     extends RuntimeException {}
/*     */ 
/*     */   
/*     */   public static class Variant
/*     */   {
/*     */     private final ResourceLocation modelLocation;
/*     */     private final ModelRotation modelRotation;
/*     */     private final boolean uvLock;
/*     */     private final int weight;
/*     */     
/*     */     public Variant(ResourceLocation modelLocationIn, ModelRotation modelRotationIn, boolean uvLockIn, int weightIn) {
/* 136 */       this.modelLocation = modelLocationIn;
/* 137 */       this.modelRotation = modelRotationIn;
/* 138 */       this.uvLock = uvLockIn;
/* 139 */       this.weight = weightIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public ResourceLocation getModelLocation() {
/* 144 */       return this.modelLocation;
/*     */     }
/*     */ 
/*     */     
/*     */     public ModelRotation getRotation() {
/* 149 */       return this.modelRotation;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isUvLocked() {
/* 154 */       return this.uvLock;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getWeight() {
/* 159 */       return this.weight;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object p_equals_1_) {
/* 164 */       if (this == p_equals_1_)
/*     */       {
/* 166 */         return true;
/*     */       }
/* 168 */       if (!(p_equals_1_ instanceof Variant))
/*     */       {
/* 170 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 174 */       Variant modelblockdefinition$variant = (Variant)p_equals_1_;
/* 175 */       return (this.modelLocation.equals(modelblockdefinition$variant.modelLocation) && this.modelRotation == modelblockdefinition$variant.modelRotation && this.uvLock == modelblockdefinition$variant.uvLock);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 181 */       int i = this.modelLocation.hashCode();
/* 182 */       i = 31 * i + ((this.modelRotation != null) ? this.modelRotation.hashCode() : 0);
/* 183 */       i = 31 * i + (this.uvLock ? 1 : 0);
/* 184 */       return i;
/*     */     }
/*     */     
/*     */     public static class Deserializer
/*     */       implements JsonDeserializer<Variant>
/*     */     {
/*     */       public ModelBlockDefinition.Variant deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 191 */         JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 192 */         String s = parseModel(jsonobject);
/* 193 */         ModelRotation modelrotation = parseRotation(jsonobject);
/* 194 */         boolean flag = parseUvLock(jsonobject);
/* 195 */         int i = parseWeight(jsonobject);
/* 196 */         return new ModelBlockDefinition.Variant(makeModelLocation(s), modelrotation, flag, i);
/*     */       }
/*     */ 
/*     */       
/*     */       private ResourceLocation makeModelLocation(String p_178426_1_) {
/* 201 */         ResourceLocation resourcelocation = new ResourceLocation(p_178426_1_);
/* 202 */         resourcelocation = new ResourceLocation(resourcelocation.getResourceDomain(), "block/" + resourcelocation.getResourcePath());
/* 203 */         return resourcelocation;
/*     */       }
/*     */ 
/*     */       
/*     */       private boolean parseUvLock(JsonObject p_178429_1_) {
/* 208 */         return JsonUtils.getBoolean(p_178429_1_, "uvlock", false);
/*     */       }
/*     */ 
/*     */       
/*     */       protected ModelRotation parseRotation(JsonObject p_178428_1_) {
/* 213 */         int i = JsonUtils.getInt(p_178428_1_, "x", 0);
/* 214 */         int j = JsonUtils.getInt(p_178428_1_, "y", 0);
/* 215 */         ModelRotation modelrotation = ModelRotation.getModelRotation(i, j);
/*     */         
/* 217 */         if (modelrotation == null)
/*     */         {
/* 219 */           throw new JsonParseException("Invalid BlockModelRotation x: " + i + ", y: " + j);
/*     */         }
/*     */ 
/*     */         
/* 223 */         return modelrotation;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected String parseModel(JsonObject p_178424_1_) {
/* 229 */         return JsonUtils.getString(p_178424_1_, "model");
/*     */       }
/*     */       
/*     */       protected int parseWeight(JsonObject p_178427_1_)
/*     */       {
/* 234 */         return JsonUtils.getInt(p_178427_1_, "weight", 1); } } } public static class Deserializer implements JsonDeserializer<Variant> { public ModelBlockDefinition.Variant deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException { JsonObject jsonobject = p_deserialize_1_.getAsJsonObject(); String s = parseModel(jsonobject); ModelRotation modelrotation = parseRotation(jsonobject); boolean flag = parseUvLock(jsonobject); int i = parseWeight(jsonobject); return new ModelBlockDefinition.Variant(makeModelLocation(s), modelrotation, flag, i); } protected int parseWeight(JsonObject p_178427_1_) { return JsonUtils.getInt(p_178427_1_, "weight", 1); }
/*     */     private ResourceLocation makeModelLocation(String p_178426_1_) { ResourceLocation resourcelocation = new ResourceLocation(p_178426_1_);
/*     */       resourcelocation = new ResourceLocation(resourcelocation.getResourceDomain(), "block/" + resourcelocation.getResourcePath());
/*     */       return resourcelocation; } private boolean parseUvLock(JsonObject p_178429_1_) { return JsonUtils.getBoolean(p_178429_1_, "uvlock", false); } protected ModelRotation parseRotation(JsonObject p_178428_1_) { int i = JsonUtils.getInt(p_178428_1_, "x", 0);
/*     */       int j = JsonUtils.getInt(p_178428_1_, "y", 0);
/*     */       ModelRotation modelrotation = ModelRotation.getModelRotation(i, j);
/*     */       if (modelrotation == null)
/*     */         throw new JsonParseException("Invalid BlockModelRotation x: " + i + ", y: " + j); 
/*     */       return modelrotation; } protected String parseModel(JsonObject p_178424_1_) { return JsonUtils.getString(p_178424_1_, "model"); } }
/*     */    public static class Variants
/*     */   {
/*     */     final String name; private final List<ModelBlockDefinition.Variant> listVariants; public Variants(String nameIn, List<ModelBlockDefinition.Variant> listVariantsIn) {
/* 246 */       this.name = nameIn;
/* 247 */       this.listVariants = listVariantsIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<ModelBlockDefinition.Variant> getVariants() {
/* 252 */       return this.listVariants;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object p_equals_1_) {
/* 257 */       if (this == p_equals_1_)
/*     */       {
/* 259 */         return true;
/*     */       }
/* 261 */       if (!(p_equals_1_ instanceof Variants))
/*     */       {
/* 263 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 267 */       Variants modelblockdefinition$variants = (Variants)p_equals_1_;
/* 268 */       return !this.name.equals(modelblockdefinition$variants.name) ? false : this.listVariants.equals(modelblockdefinition$variants.listVariants);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 274 */       int i = this.name.hashCode();
/* 275 */       i = 31 * i + this.listVariants.hashCode();
/* 276 */       return i;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\block\model\ModelBlockDefinition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */