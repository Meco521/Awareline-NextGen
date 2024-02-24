/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ public class JsonUtils {
/*     */   public static boolean isString(JsonObject p_151205_0_, String p_151205_1_) {
/*  12 */     return !isJsonPrimitive(p_151205_0_, p_151205_1_) ? false : p_151205_0_.getAsJsonPrimitive(p_151205_1_).isString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isString(JsonElement p_151211_0_) {
/*  20 */     return !p_151211_0_.isJsonPrimitive() ? false : p_151211_0_.getAsJsonPrimitive().isString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isBoolean(JsonObject p_180199_0_, String p_180199_1_) {
/*  25 */     return !isJsonPrimitive(p_180199_0_, p_180199_1_) ? false : p_180199_0_.getAsJsonPrimitive(p_180199_1_).isBoolean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isJsonArray(JsonObject p_151202_0_, String p_151202_1_) {
/*  33 */     return !hasField(p_151202_0_, p_151202_1_) ? false : p_151202_0_.get(p_151202_1_).isJsonArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isJsonPrimitive(JsonObject p_151201_0_, String p_151201_1_) {
/*  42 */     return !hasField(p_151201_0_, p_151201_1_) ? false : p_151201_0_.get(p_151201_1_).isJsonPrimitive();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasField(JsonObject p_151204_0_, String p_151204_1_) {
/*  50 */     return (p_151204_0_ == null) ? false : ((p_151204_0_.get(p_151204_1_) != null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getString(JsonElement p_151206_0_, String p_151206_1_) {
/*  59 */     if (p_151206_0_.isJsonPrimitive())
/*     */     {
/*  61 */       return p_151206_0_.getAsString();
/*     */     }
/*     */ 
/*     */     
/*  65 */     throw new JsonSyntaxException("Expected " + p_151206_1_ + " to be a string, was " + toString(p_151206_0_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getString(JsonObject p_151200_0_, String p_151200_1_) {
/*  74 */     if (p_151200_0_.has(p_151200_1_))
/*     */     {
/*  76 */       return getString(p_151200_0_.get(p_151200_1_), p_151200_1_);
/*     */     }
/*     */ 
/*     */     
/*  80 */     throw new JsonSyntaxException("Missing " + p_151200_1_ + ", expected to find a string");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getString(JsonObject p_151219_0_, String p_151219_1_, String p_151219_2_) {
/*  90 */     return p_151219_0_.has(p_151219_1_) ? getString(p_151219_0_.get(p_151219_1_), p_151219_1_) : p_151219_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getBoolean(JsonElement p_151216_0_, String p_151216_1_) {
/*  99 */     if (p_151216_0_.isJsonPrimitive())
/*     */     {
/* 101 */       return p_151216_0_.getAsBoolean();
/*     */     }
/*     */ 
/*     */     
/* 105 */     throw new JsonSyntaxException("Expected " + p_151216_1_ + " to be a Boolean, was " + toString(p_151216_0_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getBoolean(JsonObject p_151212_0_, String p_151212_1_) {
/* 114 */     if (p_151212_0_.has(p_151212_1_))
/*     */     {
/* 116 */       return getBoolean(p_151212_0_.get(p_151212_1_), p_151212_1_);
/*     */     }
/*     */ 
/*     */     
/* 120 */     throw new JsonSyntaxException("Missing " + p_151212_1_ + ", expected to find a Boolean");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getBoolean(JsonObject p_151209_0_, String p_151209_1_, boolean p_151209_2_) {
/* 130 */     return p_151209_0_.has(p_151209_1_) ? getBoolean(p_151209_0_.get(p_151209_1_), p_151209_1_) : p_151209_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getFloat(JsonElement p_151220_0_, String p_151220_1_) {
/* 139 */     if (p_151220_0_.isJsonPrimitive() && p_151220_0_.getAsJsonPrimitive().isNumber())
/*     */     {
/* 141 */       return p_151220_0_.getAsFloat();
/*     */     }
/*     */ 
/*     */     
/* 145 */     throw new JsonSyntaxException("Expected " + p_151220_1_ + " to be a Float, was " + toString(p_151220_0_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getFloat(JsonObject p_151217_0_, String p_151217_1_) {
/* 154 */     if (p_151217_0_.has(p_151217_1_))
/*     */     {
/* 156 */       return getFloat(p_151217_0_.get(p_151217_1_), p_151217_1_);
/*     */     }
/*     */ 
/*     */     
/* 160 */     throw new JsonSyntaxException("Missing " + p_151217_1_ + ", expected to find a Float");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getFloat(JsonObject p_151221_0_, String p_151221_1_, float p_151221_2_) {
/* 170 */     return p_151221_0_.has(p_151221_1_) ? getFloat(p_151221_0_.get(p_151221_1_), p_151221_1_) : p_151221_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getInt(JsonElement p_151215_0_, String p_151215_1_) {
/* 179 */     if (p_151215_0_.isJsonPrimitive() && p_151215_0_.getAsJsonPrimitive().isNumber())
/*     */     {
/* 181 */       return p_151215_0_.getAsInt();
/*     */     }
/*     */ 
/*     */     
/* 185 */     throw new JsonSyntaxException("Expected " + p_151215_1_ + " to be a Int, was " + toString(p_151215_0_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getInt(JsonObject p_151203_0_, String p_151203_1_) {
/* 194 */     if (p_151203_0_.has(p_151203_1_))
/*     */     {
/* 196 */       return getInt(p_151203_0_.get(p_151203_1_), p_151203_1_);
/*     */     }
/*     */ 
/*     */     
/* 200 */     throw new JsonSyntaxException("Missing " + p_151203_1_ + ", expected to find a Int");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getInt(JsonObject p_151208_0_, String p_151208_1_, int p_151208_2_) {
/* 210 */     return p_151208_0_.has(p_151208_1_) ? getInt(p_151208_0_.get(p_151208_1_), p_151208_1_) : p_151208_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonObject getJsonObject(JsonElement p_151210_0_, String p_151210_1_) {
/* 219 */     if (p_151210_0_.isJsonObject())
/*     */     {
/* 221 */       return p_151210_0_.getAsJsonObject();
/*     */     }
/*     */ 
/*     */     
/* 225 */     throw new JsonSyntaxException("Expected " + p_151210_1_ + " to be a JsonObject, was " + toString(p_151210_0_));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonObject getJsonObject(JsonObject base, String key) {
/* 231 */     if (base.has(key))
/*     */     {
/* 233 */       return getJsonObject(base.get(key), key);
/*     */     }
/*     */ 
/*     */     
/* 237 */     throw new JsonSyntaxException("Missing " + key + ", expected to find a JsonObject");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonObject getJsonObject(JsonObject p_151218_0_, String p_151218_1_, JsonObject p_151218_2_) {
/* 247 */     return p_151218_0_.has(p_151218_1_) ? getJsonObject(p_151218_0_.get(p_151218_1_), p_151218_1_) : p_151218_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonArray getJsonArray(JsonElement p_151207_0_, String p_151207_1_) {
/* 256 */     if (p_151207_0_.isJsonArray())
/*     */     {
/* 258 */       return p_151207_0_.getAsJsonArray();
/*     */     }
/*     */ 
/*     */     
/* 262 */     throw new JsonSyntaxException("Expected " + p_151207_1_ + " to be a JsonArray, was " + toString(p_151207_0_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonArray getJsonArray(JsonObject p_151214_0_, String p_151214_1_) {
/* 271 */     if (p_151214_0_.has(p_151214_1_))
/*     */     {
/* 273 */       return getJsonArray(p_151214_0_.get(p_151214_1_), p_151214_1_);
/*     */     }
/*     */ 
/*     */     
/* 277 */     throw new JsonSyntaxException("Missing " + p_151214_1_ + ", expected to find a JsonArray");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonArray getJsonArray(JsonObject p_151213_0_, String p_151213_1_, JsonArray p_151213_2_) {
/* 287 */     return p_151213_0_.has(p_151213_1_) ? getJsonArray(p_151213_0_.get(p_151213_1_), p_151213_1_) : p_151213_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toString(JsonElement p_151222_0_) {
/* 295 */     String s = StringUtils.abbreviateMiddle(String.valueOf(p_151222_0_), "...", 10);
/*     */     
/* 297 */     if (p_151222_0_ == null)
/*     */     {
/* 299 */       return "null (missing)";
/*     */     }
/* 301 */     if (p_151222_0_.isJsonNull())
/*     */     {
/* 303 */       return "null (json)";
/*     */     }
/* 305 */     if (p_151222_0_.isJsonArray())
/*     */     {
/* 307 */       return "an array (" + s + ")";
/*     */     }
/* 309 */     if (p_151222_0_.isJsonObject())
/*     */     {
/* 311 */       return "an object (" + s + ")";
/*     */     }
/*     */ 
/*     */     
/* 315 */     if (p_151222_0_.isJsonPrimitive()) {
/*     */       
/* 317 */       JsonPrimitive jsonprimitive = p_151222_0_.getAsJsonPrimitive();
/*     */       
/* 319 */       if (jsonprimitive.isNumber())
/*     */       {
/* 321 */         return "a number (" + s + ")";
/*     */       }
/*     */       
/* 324 */       if (jsonprimitive.isBoolean())
/*     */       {
/* 326 */         return "a boolean (" + s + ")";
/*     */       }
/*     */     } 
/*     */     
/* 330 */     return s;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\JsonUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */