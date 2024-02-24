/*    */ package net.optifine.util;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ 
/*    */ 
/*    */ public class Json
/*    */ {
/*    */   public static float getFloat(JsonObject obj, String field, float def) {
/* 12 */     JsonElement jsonelement = obj.get(field);
/* 13 */     return (jsonelement == null) ? def : jsonelement.getAsFloat();
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean getBoolean(JsonObject obj, String field, boolean def) {
/* 18 */     JsonElement jsonelement = obj.get(field);
/* 19 */     return (jsonelement == null) ? def : jsonelement.getAsBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getString(JsonObject jsonObj, String field) {
/* 24 */     return getString(jsonObj, field, (String)null);
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getString(JsonObject jsonObj, String field, String def) {
/* 29 */     JsonElement jsonelement = jsonObj.get(field);
/* 30 */     return (jsonelement == null) ? def : jsonelement.getAsString();
/*    */   }
/*    */ 
/*    */   
/*    */   public static float[] parseFloatArray(JsonElement jsonElement, int len) {
/* 35 */     return parseFloatArray(jsonElement, len, (float[])null);
/*    */   }
/*    */ 
/*    */   
/*    */   public static float[] parseFloatArray(JsonElement jsonElement, int len, float[] def) {
/* 40 */     if (jsonElement == null)
/*    */     {
/* 42 */       return def;
/*    */     }
/*    */ 
/*    */     
/* 46 */     JsonArray jsonarray = jsonElement.getAsJsonArray();
/*    */     
/* 48 */     if (jsonarray.size() != len)
/*    */     {
/* 50 */       throw new JsonParseException("Wrong array length: " + jsonarray.size() + ", should be: " + len + ", array: " + jsonarray);
/*    */     }
/*    */ 
/*    */     
/* 54 */     float[] afloat = new float[jsonarray.size()];
/*    */     
/* 56 */     for (int i = 0; i < afloat.length; i++)
/*    */     {
/* 58 */       afloat[i] = jsonarray.get(i).getAsFloat();
/*    */     }
/*    */     
/* 61 */     return afloat;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int[] parseIntArray(JsonElement jsonElement, int len) {
/* 68 */     return parseIntArray(jsonElement, len, (int[])null);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int[] parseIntArray(JsonElement jsonElement, int len, int[] def) {
/* 73 */     if (jsonElement == null)
/*    */     {
/* 75 */       return def;
/*    */     }
/*    */ 
/*    */     
/* 79 */     JsonArray jsonarray = jsonElement.getAsJsonArray();
/*    */     
/* 81 */     if (jsonarray.size() != len)
/*    */     {
/* 83 */       throw new JsonParseException("Wrong array length: " + jsonarray.size() + ", should be: " + len + ", array: " + jsonarray);
/*    */     }
/*    */ 
/*    */     
/* 87 */     int[] aint = new int[jsonarray.size()];
/*    */     
/* 89 */     for (int i = 0; i < aint.length; i++)
/*    */     {
/* 91 */       aint[i] = jsonarray.get(i).getAsInt();
/*    */     }
/*    */     
/* 94 */     return aint;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\Json.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */