/*     */ package net.optifine.player;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.awt.Dimension;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.entity.model.CustomEntityModelParser;
/*     */ import net.optifine.util.Json;
/*     */ 
/*     */ public class PlayerItemParser {
/*  21 */   private static final JsonParser jsonParser = new JsonParser();
/*     */   
/*     */   public static final String ITEM_TYPE = "type";
/*     */   public static final String ITEM_TEXTURE_SIZE = "textureSize";
/*     */   public static final String ITEM_USE_PLAYER_TEXTURE = "usePlayerTexture";
/*     */   public static final String ITEM_MODELS = "models";
/*     */   public static final String MODEL_ID = "id";
/*     */   public static final String MODEL_BASE_ID = "baseId";
/*     */   public static final String MODEL_TYPE = "type";
/*     */   public static final String MODEL_TEXTURE = "texture";
/*     */   public static final String MODEL_TEXTURE_SIZE = "textureSize";
/*     */   public static final String MODEL_ATTACH_TO = "attachTo";
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
/*     */   public static final String BOX_UV_DOWN = "uvDown";
/*     */   public static final String BOX_UV_UP = "uvUp";
/*     */   public static final String BOX_UV_NORTH = "uvNorth";
/*     */   public static final String BOX_UV_SOUTH = "uvSouth";
/*     */   public static final String BOX_UV_WEST = "uvWest";
/*     */   public static final String BOX_UV_EAST = "uvEast";
/*     */   public static final String BOX_UV_FRONT = "uvFront";
/*     */   public static final String BOX_UV_BACK = "uvBack";
/*     */   public static final String BOX_UV_LEFT = "uvLeft";
/*     */   public static final String BOX_UV_RIGHT = "uvRight";
/*     */   public static final String ITEM_TYPE_MODEL = "PlayerItem";
/*     */   public static final String MODEL_TYPE_BOX = "ModelBox";
/*     */   
/*     */   public static PlayerItemModel parseItemModel(JsonObject obj) {
/*  59 */     String s = Json.getString(obj, "type");
/*     */     
/*  61 */     if (!Config.equals(s, "PlayerItem"))
/*     */     {
/*  63 */       throw new JsonParseException("Unknown model type: " + s);
/*     */     }
/*     */ 
/*     */     
/*  67 */     int[] aint = Json.parseIntArray(obj.get("textureSize"), 2);
/*  68 */     checkNull(aint, "Missing texture size");
/*  69 */     Dimension dimension = new Dimension(aint[0], aint[1]);
/*  70 */     boolean flag = Json.getBoolean(obj, "usePlayerTexture", false);
/*  71 */     JsonArray jsonarray = (JsonArray)obj.get("models");
/*  72 */     checkNull(jsonarray, "Missing elements");
/*  73 */     Map<Object, Object> map = new HashMap<>();
/*  74 */     List list = new ArrayList();
/*  75 */     new ArrayList();
/*     */     
/*  77 */     int i = 0; while (true) { JsonObject jsonobject; if (i < jsonarray.size())
/*     */       
/*  79 */       { jsonobject = (JsonObject)jsonarray.get(i);
/*  80 */         String s1 = Json.getString(jsonobject, "baseId");
/*     */         
/*  82 */         if (s1 != null)
/*     */         
/*  84 */         { JsonObject jsonobject1 = (JsonObject)map.get(s1);
/*     */           
/*  86 */           if (jsonobject1 == null)
/*     */           
/*  88 */           { Config.warn("BaseID not found: " + s1); }
/*     */           
/*     */           else
/*     */           
/*  92 */           { for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject1.entrySet()) {
/*     */               
/*  94 */               if (!jsonobject.has(entry.getKey()))
/*     */               {
/*  96 */                 jsonobject.add(entry.getKey(), entry.getValue());
/*     */               }
/*     */             } 
/*     */ 
/*     */             
/* 101 */             String s2 = Json.getString(jsonobject, "id"); }  continue; }  } else { break; }  String str = Json.getString(jsonobject, "id");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       i++; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     PlayerItemRenderer[] aplayeritemrenderer = (PlayerItemRenderer[])list.toArray((Object[])new PlayerItemRenderer[0]);
/* 124 */     return new PlayerItemModel(dimension, flag, aplayeritemrenderer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkNull(Object obj, String msg) {
/* 130 */     if (obj == null)
/*     */     {
/* 132 */       throw new JsonParseException(msg);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static ResourceLocation makeResourceLocation(String texture) {
/* 138 */     int i = texture.indexOf(':');
/*     */     
/* 140 */     if (i < 0)
/*     */     {
/* 142 */       return new ResourceLocation(texture);
/*     */     }
/*     */ 
/*     */     
/* 146 */     String s = texture.substring(0, i);
/* 147 */     String s1 = texture.substring(i + 1);
/* 148 */     return new ResourceLocation(s, s1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int parseAttachModel(String attachModelStr) {
/* 154 */     if (attachModelStr == null)
/*     */     {
/* 156 */       return 0;
/*     */     }
/* 158 */     if (attachModelStr.equals("body"))
/*     */     {
/* 160 */       return 0;
/*     */     }
/* 162 */     if (attachModelStr.equals("head"))
/*     */     {
/* 164 */       return 1;
/*     */     }
/* 166 */     if (attachModelStr.equals("leftArm"))
/*     */     {
/* 168 */       return 2;
/*     */     }
/* 170 */     if (attachModelStr.equals("rightArm"))
/*     */     {
/* 172 */       return 3;
/*     */     }
/* 174 */     if (attachModelStr.equals("leftLeg"))
/*     */     {
/* 176 */       return 4;
/*     */     }
/* 178 */     if (attachModelStr.equals("rightLeg"))
/*     */     {
/* 180 */       return 5;
/*     */     }
/* 182 */     if (attachModelStr.equals("cape"))
/*     */     {
/* 184 */       return 6;
/*     */     }
/*     */ 
/*     */     
/* 188 */     Config.warn("Unknown attachModel: " + attachModelStr);
/* 189 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PlayerItemRenderer parseItemRenderer(JsonObject elem, Dimension textureDim) {
/* 195 */     String s = Json.getString(elem, "type");
/*     */     
/* 197 */     if (!Config.equals(s, "ModelBox")) {
/*     */       
/* 199 */       Config.warn("Unknown model type: " + s);
/* 200 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 204 */     String s1 = Json.getString(elem, "attachTo");
/* 205 */     int i = parseAttachModel(s1);
/* 206 */     ModelBase modelbase = new ModelPlayerItem();
/* 207 */     modelbase.textureWidth = textureDim.width;
/* 208 */     modelbase.textureHeight = textureDim.height;
/* 209 */     ModelRenderer modelrenderer = parseModelRenderer(elem, modelbase, (int[])null, (String)null);
/* 210 */     PlayerItemRenderer playeritemrenderer = new PlayerItemRenderer(i, modelrenderer);
/* 211 */     return playeritemrenderer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ModelRenderer parseModelRenderer(JsonObject elem, ModelBase modelBase, int[] parentTextureSize, String basePath) {
/* 217 */     ModelRenderer modelrenderer = new ModelRenderer(modelBase);
/* 218 */     String s = Json.getString(elem, "id");
/* 219 */     modelrenderer.setId(s);
/* 220 */     float f = Json.getFloat(elem, "scale", 1.0F);
/* 221 */     modelrenderer.scaleX = f;
/* 222 */     modelrenderer.scaleY = f;
/* 223 */     modelrenderer.scaleZ = f;
/* 224 */     String s1 = Json.getString(elem, "texture");
/*     */     
/* 226 */     if (s1 != null)
/*     */     {
/* 228 */       modelrenderer.setTextureLocation(CustomEntityModelParser.getResourceLocation(basePath, s1, ".png"));
/*     */     }
/*     */     
/* 231 */     int[] aint = Json.parseIntArray(elem.get("textureSize"), 2);
/*     */     
/* 233 */     if (aint == null)
/*     */     {
/* 235 */       aint = parentTextureSize;
/*     */     }
/*     */     
/* 238 */     if (aint != null)
/*     */     {
/* 240 */       modelrenderer.setTextureSize(aint[0], aint[1]);
/*     */     }
/*     */     
/* 243 */     String s2 = Json.getString(elem, "invertAxis", "").toLowerCase();
/* 244 */     boolean flag = s2.contains("x");
/* 245 */     boolean flag1 = s2.contains("y");
/* 246 */     boolean flag2 = s2.contains("z");
/* 247 */     float[] afloat = Json.parseFloatArray(elem.get("translate"), 3, new float[3]);
/*     */     
/* 249 */     if (flag)
/*     */     {
/* 251 */       afloat[0] = -afloat[0];
/*     */     }
/*     */     
/* 254 */     if (flag1)
/*     */     {
/* 256 */       afloat[1] = -afloat[1];
/*     */     }
/*     */     
/* 259 */     if (flag2)
/*     */     {
/* 261 */       afloat[2] = -afloat[2];
/*     */     }
/*     */     
/* 264 */     float[] afloat1 = Json.parseFloatArray(elem.get("rotate"), 3, new float[3]);
/*     */     
/* 266 */     for (int i = 0; i < afloat1.length; i++)
/*     */     {
/* 268 */       afloat1[i] = afloat1[i] / 180.0F * MathHelper.PI;
/*     */     }
/*     */     
/* 271 */     if (flag)
/*     */     {
/* 273 */       afloat1[0] = -afloat1[0];
/*     */     }
/*     */     
/* 276 */     if (flag1)
/*     */     {
/* 278 */       afloat1[1] = -afloat1[1];
/*     */     }
/*     */     
/* 281 */     if (flag2)
/*     */     {
/* 283 */       afloat1[2] = -afloat1[2];
/*     */     }
/*     */     
/* 286 */     modelrenderer.setRotationPoint(afloat[0], afloat[1], afloat[2]);
/* 287 */     modelrenderer.rotateAngleX = afloat1[0];
/* 288 */     modelrenderer.rotateAngleY = afloat1[1];
/* 289 */     modelrenderer.rotateAngleZ = afloat1[2];
/* 290 */     String s3 = Json.getString(elem, "mirrorTexture", "").toLowerCase();
/* 291 */     boolean flag3 = s3.contains("u");
/* 292 */     boolean flag4 = s3.contains("v");
/*     */     
/* 294 */     if (flag3)
/*     */     {
/* 296 */       modelrenderer.mirror = true;
/*     */     }
/*     */     
/* 299 */     if (flag4)
/*     */     {
/* 301 */       modelrenderer.mirrorV = true;
/*     */     }
/*     */     
/* 304 */     JsonArray jsonarray = elem.getAsJsonArray("boxes");
/*     */     
/* 306 */     if (jsonarray != null)
/*     */     {
/* 308 */       for (int j = 0; j < jsonarray.size(); j++) {
/*     */         
/* 310 */         JsonObject jsonobject = jsonarray.get(j).getAsJsonObject();
/* 311 */         int[] aint1 = Json.parseIntArray(jsonobject.get("textureOffset"), 2);
/* 312 */         int[][] aint2 = parseFaceUvs(jsonobject);
/*     */         
/* 314 */         if (aint1 == null && aint2 == null)
/*     */         {
/* 316 */           throw new JsonParseException("Texture offset not specified");
/*     */         }
/*     */         
/* 319 */         float[] afloat2 = Json.parseFloatArray(jsonobject.get("coordinates"), 6);
/*     */         
/* 321 */         if (afloat2 == null)
/*     */         {
/* 323 */           throw new JsonParseException("Coordinates not specified");
/*     */         }
/*     */         
/* 326 */         if (flag)
/*     */         {
/* 328 */           afloat2[0] = -afloat2[0] - afloat2[3];
/*     */         }
/*     */         
/* 331 */         if (flag1)
/*     */         {
/* 333 */           afloat2[1] = -afloat2[1] - afloat2[4];
/*     */         }
/*     */         
/* 336 */         if (flag2)
/*     */         {
/* 338 */           afloat2[2] = -afloat2[2] - afloat2[5];
/*     */         }
/*     */         
/* 341 */         float f1 = Json.getFloat(jsonobject, "sizeAdd", 0.0F);
/*     */         
/* 343 */         if (aint2 != null) {
/*     */           
/* 345 */           modelrenderer.addBox(aint2, afloat2[0], afloat2[1], afloat2[2], afloat2[3], afloat2[4], afloat2[5], f1);
/*     */         }
/*     */         else {
/*     */           
/* 349 */           modelrenderer.setTextureOffset(aint1[0], aint1[1]);
/* 350 */           modelrenderer.addBox(afloat2[0], afloat2[1], afloat2[2], (int)afloat2[3], (int)afloat2[4], (int)afloat2[5], f1);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 355 */     JsonArray jsonarray1 = elem.getAsJsonArray("sprites");
/*     */     
/* 357 */     if (jsonarray1 != null)
/*     */     {
/* 359 */       for (int k = 0; k < jsonarray1.size(); k++) {
/*     */         
/* 361 */         JsonObject jsonobject2 = jsonarray1.get(k).getAsJsonObject();
/* 362 */         int[] aint3 = Json.parseIntArray(jsonobject2.get("textureOffset"), 2);
/*     */         
/* 364 */         if (aint3 == null)
/*     */         {
/* 366 */           throw new JsonParseException("Texture offset not specified");
/*     */         }
/*     */         
/* 369 */         float[] afloat3 = Json.parseFloatArray(jsonobject2.get("coordinates"), 6);
/*     */         
/* 371 */         if (afloat3 == null)
/*     */         {
/* 373 */           throw new JsonParseException("Coordinates not specified");
/*     */         }
/*     */         
/* 376 */         if (flag)
/*     */         {
/* 378 */           afloat3[0] = -afloat3[0] - afloat3[3];
/*     */         }
/*     */         
/* 381 */         if (flag1)
/*     */         {
/* 383 */           afloat3[1] = -afloat3[1] - afloat3[4];
/*     */         }
/*     */         
/* 386 */         if (flag2)
/*     */         {
/* 388 */           afloat3[2] = -afloat3[2] - afloat3[5];
/*     */         }
/*     */         
/* 391 */         float f2 = Json.getFloat(jsonobject2, "sizeAdd", 0.0F);
/* 392 */         modelrenderer.setTextureOffset(aint3[0], aint3[1]);
/* 393 */         modelrenderer.addSprite(afloat3[0], afloat3[1], afloat3[2], (int)afloat3[3], (int)afloat3[4], (int)afloat3[5], f2);
/*     */       } 
/*     */     }
/*     */     
/* 397 */     JsonObject jsonobject1 = (JsonObject)elem.get("submodel");
/*     */     
/* 399 */     if (jsonobject1 != null) {
/*     */       
/* 401 */       ModelRenderer modelrenderer2 = parseModelRenderer(jsonobject1, modelBase, aint, basePath);
/* 402 */       modelrenderer.addChild(modelrenderer2);
/*     */     } 
/*     */     
/* 405 */     JsonArray jsonarray2 = (JsonArray)elem.get("submodels");
/*     */     
/* 407 */     if (jsonarray2 != null)
/*     */     {
/* 409 */       for (int l = 0; l < jsonarray2.size(); l++) {
/*     */         
/* 411 */         JsonObject jsonobject3 = (JsonObject)jsonarray2.get(l);
/* 412 */         ModelRenderer modelrenderer3 = parseModelRenderer(jsonobject3, modelBase, aint, basePath);
/*     */         
/* 414 */         if (modelrenderer3.getId() != null) {
/*     */           
/* 416 */           ModelRenderer modelrenderer1 = modelrenderer.getChild(modelrenderer3.getId());
/*     */           
/* 418 */           if (modelrenderer1 != null)
/*     */           {
/* 420 */             Config.warn("Duplicate model ID: " + modelrenderer3.getId());
/*     */           }
/*     */         } 
/*     */         
/* 424 */         modelrenderer.addChild(modelrenderer3);
/*     */       } 
/*     */     }
/*     */     
/* 428 */     return modelrenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[][] parseFaceUvs(JsonObject box) {
/* 433 */     int[][] aint = { Json.parseIntArray(box.get("uvDown"), 4), Json.parseIntArray(box.get("uvUp"), 4), Json.parseIntArray(box.get("uvNorth"), 4), Json.parseIntArray(box.get("uvSouth"), 4), Json.parseIntArray(box.get("uvWest"), 4), Json.parseIntArray(box.get("uvEast"), 4) };
/*     */     
/* 435 */     if (aint[2] == null)
/*     */     {
/* 437 */       aint[2] = Json.parseIntArray(box.get("uvFront"), 4);
/*     */     }
/*     */     
/* 440 */     if (aint[3] == null)
/*     */     {
/* 442 */       aint[3] = Json.parseIntArray(box.get("uvBack"), 4);
/*     */     }
/*     */     
/* 445 */     if (aint[4] == null)
/*     */     {
/* 447 */       aint[4] = Json.parseIntArray(box.get("uvLeft"), 4);
/*     */     }
/*     */     
/* 450 */     if (aint[5] == null)
/*     */     {
/* 452 */       aint[5] = Json.parseIntArray(box.get("uvRight"), 4);
/*     */     }
/*     */     
/* 455 */     boolean flag = false;
/*     */     
/* 457 */     for (int i = 0; i < aint.length; i++) {
/*     */       
/* 459 */       if (aint[i] != null) {
/* 460 */         flag = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 465 */     if (!flag)
/*     */     {
/* 467 */       return (int[][])null;
/*     */     }
/*     */ 
/*     */     
/* 471 */     return aint;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\player\PlayerItemParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */