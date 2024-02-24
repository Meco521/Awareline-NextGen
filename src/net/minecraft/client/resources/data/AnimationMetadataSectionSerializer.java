/*     */ package net.minecraft.client.resources.data;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ public class AnimationMetadataSectionSerializer extends BaseMetadataSectionSerializer<AnimationMetadataSection> implements JsonSerializer<AnimationMetadataSection> {
/*     */   public AnimationMetadataSection deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/*  15 */     List<AnimationFrame> list = Lists.newArrayList();
/*  16 */     JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "metadata section");
/*  17 */     int i = JsonUtils.getInt(jsonobject, "frametime", 1);
/*     */     
/*  19 */     if (i != 1)
/*     */     {
/*  21 */       Validate.inclusiveBetween(1L, 2147483647L, i, "Invalid default frame time");
/*     */     }
/*     */     
/*  24 */     if (jsonobject.has("frames")) {
/*     */       
/*     */       try {
/*     */         
/*  28 */         JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "frames");
/*     */         
/*  30 */         for (int j = 0; j < jsonarray.size(); j++)
/*     */         {
/*  32 */           JsonElement jsonelement = jsonarray.get(j);
/*  33 */           AnimationFrame animationframe = parseAnimationFrame(j, jsonelement);
/*     */           
/*  35 */           if (animationframe != null)
/*     */           {
/*  37 */             list.add(animationframe);
/*     */           }
/*     */         }
/*     */       
/*  41 */       } catch (ClassCastException classcastexception) {
/*     */         
/*  43 */         throw new JsonParseException("Invalid animation->frames: expected array, was " + jsonobject.get("frames"), classcastexception);
/*     */       } 
/*     */     }
/*     */     
/*  47 */     int k = JsonUtils.getInt(jsonobject, "width", -1);
/*  48 */     int l = JsonUtils.getInt(jsonobject, "height", -1);
/*     */     
/*  50 */     if (k != -1)
/*     */     {
/*  52 */       Validate.inclusiveBetween(1L, 2147483647L, k, "Invalid width");
/*     */     }
/*     */     
/*  55 */     if (l != -1)
/*     */     {
/*  57 */       Validate.inclusiveBetween(1L, 2147483647L, l, "Invalid height");
/*     */     }
/*     */     
/*  60 */     boolean flag = JsonUtils.getBoolean(jsonobject, "interpolate", false);
/*  61 */     return new AnimationMetadataSection(list, k, l, i, flag);
/*     */   }
/*     */ 
/*     */   
/*     */   private AnimationFrame parseAnimationFrame(int p_110492_1_, JsonElement p_110492_2_) {
/*  66 */     if (p_110492_2_.isJsonPrimitive())
/*     */     {
/*  68 */       return new AnimationFrame(JsonUtils.getInt(p_110492_2_, "frames[" + p_110492_1_ + "]"));
/*     */     }
/*  70 */     if (p_110492_2_.isJsonObject()) {
/*     */       
/*  72 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_110492_2_, "frames[" + p_110492_1_ + "]");
/*  73 */       int i = JsonUtils.getInt(jsonobject, "time", -1);
/*     */       
/*  75 */       if (jsonobject.has("time"))
/*     */       {
/*  77 */         Validate.inclusiveBetween(1L, 2147483647L, i, "Invalid frame time");
/*     */       }
/*     */       
/*  80 */       int j = JsonUtils.getInt(jsonobject, "index");
/*  81 */       Validate.inclusiveBetween(0L, 2147483647L, j, "Invalid frame index");
/*  82 */       return new AnimationFrame(j, i);
/*     */     } 
/*     */ 
/*     */     
/*  86 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonElement serialize(AnimationMetadataSection p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/*  92 */     JsonObject jsonobject = new JsonObject();
/*  93 */     jsonobject.addProperty("frametime", Integer.valueOf(p_serialize_1_.getFrameTime()));
/*     */     
/*  95 */     if (p_serialize_1_.getFrameWidth() != -1)
/*     */     {
/*  97 */       jsonobject.addProperty("width", Integer.valueOf(p_serialize_1_.getFrameWidth()));
/*     */     }
/*     */     
/* 100 */     if (p_serialize_1_.getFrameHeight() != -1)
/*     */     {
/* 102 */       jsonobject.addProperty("height", Integer.valueOf(p_serialize_1_.getFrameHeight()));
/*     */     }
/*     */     
/* 105 */     if (p_serialize_1_.getFrameCount() > 0) {
/*     */       
/* 107 */       JsonArray jsonarray = new JsonArray();
/*     */       
/* 109 */       for (int i = 0; i < p_serialize_1_.getFrameCount(); i++) {
/*     */         
/* 111 */         if (p_serialize_1_.frameHasTime(i)) {
/*     */           
/* 113 */           JsonObject jsonobject1 = new JsonObject();
/* 114 */           jsonobject1.addProperty("index", Integer.valueOf(p_serialize_1_.getFrameIndex(i)));
/* 115 */           jsonobject1.addProperty("time", Integer.valueOf(p_serialize_1_.getFrameTimeSingle(i)));
/* 116 */           jsonarray.add((JsonElement)jsonobject1);
/*     */         }
/*     */         else {
/*     */           
/* 120 */           jsonarray.add((JsonElement)new JsonPrimitive(Integer.valueOf(p_serialize_1_.getFrameIndex(i))));
/*     */         } 
/*     */       } 
/*     */       
/* 124 */       jsonobject.add("frames", (JsonElement)jsonarray);
/*     */     } 
/*     */     
/* 127 */     return (JsonElement)jsonobject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSectionName() {
/* 135 */     return "animation";
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\data\AnimationMetadataSectionSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */