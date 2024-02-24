/*     */ package net.optifine.entity.model;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.entity.model.anim.ModelUpdater;
/*     */ import net.optifine.entity.model.anim.ModelVariableUpdater;
/*     */ import net.optifine.player.PlayerItemParser;
/*     */ import net.optifine.util.Json;
/*     */ 
/*     */ public class CustomEntityModelParser {
/*     */   public static final String ENTITY = "entity";
/*     */   public static final String TEXTURE = "texture";
/*     */   public static final String SHADOW_SIZE = "shadowSize";
/*     */   public static final String ITEM_TYPE = "type";
/*     */   public static final String ITEM_TEXTURE_SIZE = "textureSize";
/*     */   public static final String ITEM_USE_PLAYER_TEXTURE = "usePlayerTexture";
/*     */   public static final String ITEM_MODELS = "models";
/*     */   public static final String ITEM_ANIMATIONS = "animations";
/*     */   public static final String MODEL_ID = "id";
/*     */   public static final String MODEL_BASE_ID = "baseId";
/*     */   public static final String MODEL_MODEL = "model";
/*     */   public static final String MODEL_TYPE = "type";
/*     */   public static final String MODEL_PART = "part";
/*     */   public static final String MODEL_ATTACH = "attach";
/*     */   public static final String MODEL_INVERT_AXIS = "invertAxis";
/*     */   public static final String MODEL_MIRROR_TEXTURE = "mirrorTexture";
/*     */   public static final String MODEL_TRANSLATE = "translate";
/*     */   public static final String MODEL_ROTATE = "rotate";
/*     */   public static final String MODEL_SCALE = "scale";
/*     */   public static final String MODEL_BOXES = "boxes";
/*     */   public static final String MODEL_SPRITES = "sprites";
/*     */   public static final String MODEL_SUBMODEL = "submodel";
/*     */   public static final String MODEL_SUBMODELS = "submodels";
/*     */   public static final String BOX_TEXTURE_OFFSET = "textureOffset";
/*     */   public static final String BOX_COORDINATES = "coordinates";
/*     */   public static final String BOX_SIZE_ADD = "sizeAdd";
/*     */   public static final String ENTITY_MODEL = "EntityModel";
/*     */   public static final String ENTITY_MODEL_PART = "EntityModelPart";
/*     */   
/*     */   public static CustomEntityRenderer parseEntityRender(JsonObject obj, String path) {
/*  55 */     ConnectedParser connectedparser = new ConnectedParser("CustomEntityModels");
/*  56 */     String s = connectedparser.parseName(path);
/*  57 */     String s1 = connectedparser.parseBasePath(path);
/*  58 */     String s2 = Json.getString(obj, "texture");
/*  59 */     int[] aint = Json.parseIntArray(obj.get("textureSize"), 2);
/*  60 */     float f = Json.getFloat(obj, "shadowSize", -1.0F);
/*  61 */     JsonArray jsonarray = (JsonArray)obj.get("models");
/*  62 */     checkNull(jsonarray, "Missing models");
/*  63 */     Map<Object, Object> map = new HashMap<>();
/*  64 */     List<CustomModelRenderer> list = new ArrayList();
/*     */     
/*  66 */     for (int i = 0; i < jsonarray.size(); i++) {
/*     */       
/*  68 */       JsonObject jsonobject = (JsonObject)jsonarray.get(i);
/*  69 */       processBaseId(jsonobject, map);
/*  70 */       processExternalModel(jsonobject, map, s1);
/*  71 */       processId(jsonobject, map);
/*  72 */       CustomModelRenderer custommodelrenderer = parseCustomModelRenderer(jsonobject, aint, s1);
/*     */       
/*  74 */       if (custommodelrenderer != null)
/*     */       {
/*  76 */         list.add(custommodelrenderer);
/*     */       }
/*     */     } 
/*     */     
/*  80 */     CustomModelRenderer[] acustommodelrenderer = list.<CustomModelRenderer>toArray(new CustomModelRenderer[0]);
/*  81 */     ResourceLocation resourcelocation = null;
/*     */     
/*  83 */     if (s2 != null)
/*     */     {
/*  85 */       resourcelocation = getResourceLocation(s1, s2, ".png");
/*     */     }
/*     */     
/*  88 */     CustomEntityRenderer customentityrenderer = new CustomEntityRenderer(s, s1, resourcelocation, acustommodelrenderer, f);
/*  89 */     return customentityrenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void processBaseId(JsonObject elem, Map mapModelJsons) {
/*  94 */     String s = Json.getString(elem, "baseId");
/*     */     
/*  96 */     if (s != null) {
/*     */       
/*  98 */       JsonObject jsonobject = (JsonObject)mapModelJsons.get(s);
/*     */       
/* 100 */       if (jsonobject == null) {
/*     */         
/* 102 */         Config.warn("BaseID not found: " + s);
/*     */       }
/*     */       else {
/*     */         
/* 106 */         copyJsonElements(jsonobject, elem);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void processExternalModel(JsonObject elem, Map mapModelJsons, String basePath) {
/* 113 */     String s = Json.getString(elem, "model");
/*     */     
/* 115 */     if (s != null) {
/*     */       
/* 117 */       ResourceLocation resourcelocation = getResourceLocation(basePath, s, ".jpm");
/*     */ 
/*     */       
/*     */       try {
/* 121 */         JsonObject jsonobject = loadJson(resourcelocation);
/*     */         
/* 123 */         if (jsonobject == null) {
/*     */           
/* 125 */           Config.warn("Model not found: " + resourcelocation);
/*     */           
/*     */           return;
/*     */         } 
/* 129 */         copyJsonElements(jsonobject, elem);
/*     */       }
/* 131 */       catch (IOException ioexception) {
/*     */         
/* 133 */         Config.error(ioexception.getClass().getName() + ": " + ioexception.getMessage());
/*     */       }
/* 135 */       catch (JsonParseException jsonparseexception) {
/*     */         
/* 137 */         Config.error(jsonparseexception.getClass().getName() + ": " + jsonparseexception.getMessage());
/*     */       }
/* 139 */       catch (Exception exception) {
/*     */         
/* 141 */         exception.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void copyJsonElements(JsonObject objFrom, JsonObject objTo) {
/* 148 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)objFrom.entrySet()) {
/*     */       
/* 150 */       if (!((String)entry.getKey()).equals("id") && !objTo.has(entry.getKey()))
/*     */       {
/* 152 */         objTo.add(entry.getKey(), entry.getValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ResourceLocation getResourceLocation(String basePath, String path, String extension) {
/* 159 */     if (!path.endsWith(extension))
/*     */     {
/* 161 */       path = path + extension;
/*     */     }
/*     */     
/* 164 */     if (!path.contains("/")) {
/*     */       
/* 166 */       path = basePath + "/" + path;
/*     */     }
/* 168 */     else if (path.startsWith("./")) {
/*     */       
/* 170 */       path = basePath + "/" + path.substring(2);
/*     */     }
/* 172 */     else if (path.startsWith("~/")) {
/*     */       
/* 174 */       path = "optifine/" + path.substring(2);
/*     */     } 
/*     */     
/* 177 */     return new ResourceLocation(path);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void processId(JsonObject elem, Map<String, JsonObject> mapModelJsons) {
/* 182 */     String s = Json.getString(elem, "id");
/*     */     
/* 184 */     if (s != null)
/*     */     {
/* 186 */       if (s.length() < 1) {
/*     */         
/* 188 */         Config.warn("Empty model ID: " + s);
/*     */       }
/* 190 */       else if (mapModelJsons.containsKey(s)) {
/*     */         
/* 192 */         Config.warn("Duplicate model ID: " + s);
/*     */       }
/*     */       else {
/*     */         
/* 196 */         mapModelJsons.put(s, elem);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static CustomModelRenderer parseCustomModelRenderer(JsonObject elem, int[] textureSize, String basePath) {
/* 203 */     String s = Json.getString(elem, "part");
/* 204 */     checkNull(s, "Model part not specified, missing \"replace\" or \"attachTo\".");
/* 205 */     boolean flag = Json.getBoolean(elem, "attach", false);
/* 206 */     ModelBase modelbase = new CustomEntityModel();
/*     */     
/* 208 */     if (textureSize != null) {
/*     */       
/* 210 */       modelbase.textureWidth = textureSize[0];
/* 211 */       modelbase.textureHeight = textureSize[1];
/*     */     } 
/*     */     
/* 214 */     ModelUpdater modelupdater = null;
/* 215 */     JsonArray jsonarray = (JsonArray)elem.get("animations");
/*     */     
/* 217 */     if (jsonarray != null) {
/*     */       
/* 219 */       List<ModelVariableUpdater> list = new ArrayList<>();
/*     */       
/* 221 */       for (int i = 0; i < jsonarray.size(); i++) {
/*     */         
/* 223 */         JsonObject jsonobject = (JsonObject)jsonarray.get(i);
/*     */         
/* 225 */         for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet()) {
/*     */           
/* 227 */           String s1 = entry.getKey();
/* 228 */           String s2 = ((JsonElement)entry.getValue()).getAsString();
/* 229 */           ModelVariableUpdater modelvariableupdater = new ModelVariableUpdater(s1, s2);
/* 230 */           list.add(modelvariableupdater);
/*     */         } 
/*     */       } 
/*     */       
/* 234 */       if (!list.isEmpty()) {
/*     */         
/* 236 */         ModelVariableUpdater[] amodelvariableupdater = list.<ModelVariableUpdater>toArray(new ModelVariableUpdater[0]);
/* 237 */         modelupdater = new ModelUpdater(amodelvariableupdater);
/*     */       } 
/*     */     } 
/*     */     
/* 241 */     ModelRenderer modelrenderer = PlayerItemParser.parseModelRenderer(elem, modelbase, textureSize, basePath);
/* 242 */     CustomModelRenderer custommodelrenderer = new CustomModelRenderer(s, flag, modelrenderer, modelupdater);
/* 243 */     return custommodelrenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void checkNull(Object obj, String msg) {
/* 248 */     if (obj == null)
/*     */     {
/* 250 */       throw new JsonParseException(msg);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static JsonObject loadJson(ResourceLocation location) throws IOException, JsonParseException {
/* 256 */     InputStream inputstream = Config.getResourceStream(location);
/*     */     
/* 258 */     if (inputstream == null)
/*     */     {
/* 260 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 264 */     String s = Config.readInputStream(inputstream, "ASCII");
/* 265 */     inputstream.close();
/* 266 */     JsonParser jsonparser = new JsonParser();
/* 267 */     JsonObject jsonobject = (JsonObject)jsonparser.parse(s);
/* 268 */     return jsonobject;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\CustomEntityModelParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */