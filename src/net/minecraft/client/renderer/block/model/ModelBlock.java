/*     */ package net.minecraft.client.renderer.block.model;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ModelBlock {
/*  22 */   private static final Logger LOGGER = LogManager.getLogger();
/*  23 */   static final Gson SERIALIZER = (new GsonBuilder()).registerTypeAdapter(ModelBlock.class, new Deserializer()).registerTypeAdapter(BlockPart.class, new BlockPart.Deserializer()).registerTypeAdapter(BlockPartFace.class, new BlockPartFace.Deserializer()).registerTypeAdapter(BlockFaceUV.class, new BlockFaceUV.Deserializer()).registerTypeAdapter(ItemTransformVec3f.class, new ItemTransformVec3f.Deserializer()).registerTypeAdapter(ItemCameraTransforms.class, new ItemCameraTransforms.Deserializer()).create();
/*     */   
/*     */   private final List<BlockPart> elements;
/*     */   private final boolean gui3d;
/*     */   private final boolean ambientOcclusion;
/*     */   private final ItemCameraTransforms cameraTransforms;
/*     */   public String name;
/*     */   protected final Map<String, String> textures;
/*     */   protected ModelBlock parent;
/*     */   protected ResourceLocation parentLocation;
/*     */   
/*     */   public static ModelBlock deserialize(Reader readerIn) {
/*  35 */     return (ModelBlock)SERIALIZER.fromJson(readerIn, ModelBlock.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ModelBlock deserialize(String jsonString) {
/*  40 */     return deserialize(new StringReader(jsonString));
/*     */   }
/*     */ 
/*     */   
/*     */   protected ModelBlock(List<BlockPart> elementsIn, Map<String, String> texturesIn, boolean ambientOcclusionIn, boolean gui3dIn, ItemCameraTransforms cameraTransformsIn) {
/*  45 */     this((ResourceLocation)null, elementsIn, texturesIn, ambientOcclusionIn, gui3dIn, cameraTransformsIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ModelBlock(ResourceLocation parentLocationIn, Map<String, String> texturesIn, boolean ambientOcclusionIn, boolean gui3dIn, ItemCameraTransforms cameraTransformsIn) {
/*  50 */     this(parentLocationIn, Collections.emptyList(), texturesIn, ambientOcclusionIn, gui3dIn, cameraTransformsIn);
/*     */   }
/*     */ 
/*     */   
/*     */   private ModelBlock(ResourceLocation parentLocationIn, List<BlockPart> elementsIn, Map<String, String> texturesIn, boolean ambientOcclusionIn, boolean gui3dIn, ItemCameraTransforms cameraTransformsIn) {
/*  55 */     this.name = "";
/*  56 */     this.elements = elementsIn;
/*  57 */     this.ambientOcclusion = ambientOcclusionIn;
/*  58 */     this.gui3d = gui3dIn;
/*  59 */     this.textures = texturesIn;
/*  60 */     this.parentLocation = parentLocationIn;
/*  61 */     this.cameraTransforms = cameraTransformsIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BlockPart> getElements() {
/*  66 */     return hasParent() ? this.parent.getElements() : this.elements;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasParent() {
/*  71 */     return (this.parent != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAmbientOcclusion() {
/*  76 */     return hasParent() ? this.parent.isAmbientOcclusion() : this.ambientOcclusion;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGui3d() {
/*  81 */     return this.gui3d;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isResolved() {
/*  86 */     return (this.parentLocation == null || (this.parent != null && this.parent.isResolved()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void getParentFromMap(Map<ResourceLocation, ModelBlock> p_178299_1_) {
/*  91 */     if (this.parentLocation != null)
/*     */     {
/*  93 */       this.parent = p_178299_1_.get(this.parentLocation);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTexturePresent(String textureName) {
/*  99 */     return !"missingno".equals(resolveTextureName(textureName));
/*     */   }
/*     */ 
/*     */   
/*     */   public String resolveTextureName(String textureName) {
/* 104 */     if (!startsWithHash(textureName))
/*     */     {
/* 106 */       textureName = '#' + textureName;
/*     */     }
/*     */     
/* 109 */     return resolveTextureName(textureName, new Bookkeep(this));
/*     */   }
/*     */ 
/*     */   
/*     */   private String resolveTextureName(String textureName, Bookkeep p_178302_2_) {
/* 114 */     if (startsWithHash(textureName)) {
/*     */       
/* 116 */       if (this == p_178302_2_.modelExt) {
/*     */         
/* 118 */         LOGGER.warn("Unable to resolve texture due to upward reference: " + textureName + " in " + this.name);
/* 119 */         return "missingno";
/*     */       } 
/*     */ 
/*     */       
/* 123 */       String s = this.textures.get(textureName.substring(1));
/*     */       
/* 125 */       if (s == null && hasParent())
/*     */       {
/* 127 */         s = this.parent.resolveTextureName(textureName, p_178302_2_);
/*     */       }
/*     */       
/* 130 */       p_178302_2_.modelExt = this;
/*     */       
/* 132 */       if (s != null && startsWithHash(s))
/*     */       {
/* 134 */         s = p_178302_2_.model.resolveTextureName(s, p_178302_2_);
/*     */       }
/*     */       
/* 137 */       return (s != null && !startsWithHash(s)) ? s : "missingno";
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 142 */     return textureName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean startsWithHash(String hash) {
/* 148 */     return (hash.charAt(0) == '#');
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getParentLocation() {
/* 153 */     return this.parentLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBlock getRootModel() {
/* 158 */     return hasParent() ? this.parent.getRootModel() : this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemCameraTransforms getAllTransforms() {
/* 163 */     ItemTransformVec3f itemtransformvec3f = getTransform(ItemCameraTransforms.TransformType.THIRD_PERSON);
/* 164 */     ItemTransformVec3f itemtransformvec3f1 = getTransform(ItemCameraTransforms.TransformType.FIRST_PERSON);
/* 165 */     ItemTransformVec3f itemtransformvec3f2 = getTransform(ItemCameraTransforms.TransformType.HEAD);
/* 166 */     ItemTransformVec3f itemtransformvec3f3 = getTransform(ItemCameraTransforms.TransformType.GUI);
/* 167 */     ItemTransformVec3f itemtransformvec3f4 = getTransform(ItemCameraTransforms.TransformType.GROUND);
/* 168 */     ItemTransformVec3f itemtransformvec3f5 = getTransform(ItemCameraTransforms.TransformType.FIXED);
/* 169 */     return new ItemCameraTransforms(itemtransformvec3f, itemtransformvec3f1, itemtransformvec3f2, itemtransformvec3f3, itemtransformvec3f4, itemtransformvec3f5);
/*     */   }
/*     */ 
/*     */   
/*     */   private ItemTransformVec3f getTransform(ItemCameraTransforms.TransformType type) {
/* 174 */     return (this.parent != null && !this.cameraTransforms.func_181687_c(type)) ? this.parent.getTransform(type) : this.cameraTransforms.getTransform(type);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void checkModelHierarchy(Map<ResourceLocation, ModelBlock> p_178312_0_) {
/* 179 */     for (ModelBlock modelblock : p_178312_0_.values()) {
/*     */ 
/*     */       
/*     */       try {
/* 183 */         ModelBlock modelblock1 = modelblock.parent;
/*     */         
/* 185 */         for (ModelBlock modelblock2 = modelblock1.parent; modelblock1 != modelblock2; modelblock2 = modelblock2.parent.parent)
/*     */         {
/* 187 */           modelblock1 = modelblock1.parent;
/*     */         }
/*     */         
/* 190 */         throw new LoopException();
/*     */       }
/* 192 */       catch (NullPointerException nullPointerException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Bookkeep
/*     */   {
/*     */     public final ModelBlock model;
/*     */     
/*     */     public ModelBlock modelExt;
/*     */ 
/*     */     
/*     */     Bookkeep(ModelBlock p_i46223_1_) {
/* 206 */       this.model = p_i46223_1_;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Deserializer
/*     */     implements JsonDeserializer<ModelBlock>
/*     */   {
/*     */     public ModelBlock deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 214 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 215 */       List<BlockPart> list = getModelElements(p_deserialize_3_, jsonobject);
/* 216 */       String s = getParent(jsonobject);
/* 217 */       boolean flag = StringUtils.isEmpty(s);
/* 218 */       boolean flag1 = list.isEmpty();
/*     */       
/* 220 */       if (flag1 && flag)
/*     */       {
/* 222 */         throw new JsonParseException("BlockModel requires either elements or parent, found neither");
/*     */       }
/* 224 */       if (!flag && !flag1)
/*     */       {
/* 226 */         throw new JsonParseException("BlockModel requires either elements or parent, found both");
/*     */       }
/*     */ 
/*     */       
/* 230 */       Map<String, String> map = getTextures(jsonobject);
/* 231 */       boolean flag2 = getAmbientOcclusionEnabled(jsonobject);
/* 232 */       ItemCameraTransforms itemcameratransforms = ItemCameraTransforms.DEFAULT;
/*     */       
/* 234 */       if (jsonobject.has("display")) {
/*     */         
/* 236 */         JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonobject, "display");
/* 237 */         itemcameratransforms = (ItemCameraTransforms)p_deserialize_3_.deserialize((JsonElement)jsonobject1, ItemCameraTransforms.class);
/*     */       } 
/*     */       
/* 240 */       return flag1 ? new ModelBlock(new ResourceLocation(s), map, flag2, true, itemcameratransforms) : new ModelBlock(list, map, flag2, true, itemcameratransforms);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Map<String, String> getTextures(JsonObject p_178329_1_) {
/* 246 */       Map<String, String> map = Maps.newHashMap();
/*     */       
/* 248 */       if (p_178329_1_.has("textures")) {
/*     */         
/* 250 */         JsonObject jsonobject = p_178329_1_.getAsJsonObject("textures");
/*     */         
/* 252 */         for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet())
/*     */         {
/* 254 */           map.put(entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
/*     */         }
/*     */       } 
/*     */       
/* 258 */       return map;
/*     */     }
/*     */ 
/*     */     
/*     */     private String getParent(JsonObject p_178326_1_) {
/* 263 */       return JsonUtils.getString(p_178326_1_, "parent", "");
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean getAmbientOcclusionEnabled(JsonObject p_178328_1_) {
/* 268 */       return JsonUtils.getBoolean(p_178328_1_, "ambientocclusion", true);
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<BlockPart> getModelElements(JsonDeserializationContext p_178325_1_, JsonObject p_178325_2_) {
/* 273 */       List<BlockPart> list = Lists.newArrayList();
/*     */       
/* 275 */       if (p_178325_2_.has("elements"))
/*     */       {
/* 277 */         for (JsonElement jsonelement : JsonUtils.getJsonArray(p_178325_2_, "elements"))
/*     */         {
/* 279 */           list.add((BlockPart)p_178325_1_.deserialize(jsonelement, BlockPart.class));
/*     */         }
/*     */       }
/*     */       
/* 283 */       return list;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LoopException extends RuntimeException {}
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\block\model\ModelBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */