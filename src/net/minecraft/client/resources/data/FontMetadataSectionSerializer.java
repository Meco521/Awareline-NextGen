/*    */ package net.minecraft.client.resources.data;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ 
/*    */ public class FontMetadataSectionSerializer
/*    */   extends BaseMetadataSectionSerializer<FontMetadataSection>
/*    */ {
/*    */   public FontMetadataSection deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 16 */     JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 17 */     float[] afloat = new float[256];
/* 18 */     float[] afloat1 = new float[256];
/* 19 */     float[] afloat2 = new float[256];
/* 20 */     float f = 1.0F;
/* 21 */     float f1 = 0.0F;
/* 22 */     float f2 = 0.0F;
/*    */     
/* 24 */     if (jsonobject.has("characters")) {
/*    */       
/* 26 */       if (!jsonobject.get("characters").isJsonObject())
/*    */       {
/* 28 */         throw new JsonParseException("Invalid font->characters: expected object, was " + jsonobject.get("characters"));
/*    */       }
/*    */       
/* 31 */       JsonObject jsonobject1 = jsonobject.getAsJsonObject("characters");
/*    */       
/* 33 */       if (jsonobject1.has("default")) {
/*    */         
/* 35 */         if (!jsonobject1.get("default").isJsonObject())
/*    */         {
/* 37 */           throw new JsonParseException("Invalid font->characters->default: expected object, was " + jsonobject1.get("default"));
/*    */         }
/*    */         
/* 40 */         JsonObject jsonobject2 = jsonobject1.getAsJsonObject("default");
/* 41 */         f = JsonUtils.getFloat(jsonobject2, "width", f);
/* 42 */         Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, f, "Invalid default width");
/* 43 */         f1 = JsonUtils.getFloat(jsonobject2, "spacing", f1);
/* 44 */         Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, f1, "Invalid default spacing");
/* 45 */         f2 = JsonUtils.getFloat(jsonobject2, "left", f1);
/* 46 */         Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, f2, "Invalid default left");
/*    */       } 
/*    */       
/* 49 */       for (int i = 0; i < 256; i++) {
/*    */         
/* 51 */         JsonElement jsonelement = jsonobject1.get(Integer.toString(i));
/* 52 */         float f3 = f;
/* 53 */         float f4 = f1;
/* 54 */         float f5 = f2;
/*    */         
/* 56 */         if (jsonelement != null) {
/*    */           
/* 58 */           JsonObject jsonobject3 = JsonUtils.getJsonObject(jsonelement, "characters[" + i + "]");
/* 59 */           f3 = JsonUtils.getFloat(jsonobject3, "width", f);
/* 60 */           Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, f3, "Invalid width");
/* 61 */           f4 = JsonUtils.getFloat(jsonobject3, "spacing", f1);
/* 62 */           Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, f4, "Invalid spacing");
/* 63 */           f5 = JsonUtils.getFloat(jsonobject3, "left", f2);
/* 64 */           Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, f5, "Invalid left");
/*    */         } 
/*    */         
/* 67 */         afloat[i] = f3;
/* 68 */         afloat1[i] = f4;
/* 69 */         afloat2[i] = f5;
/*    */       } 
/*    */     } 
/*    */     
/* 73 */     return new FontMetadataSection(afloat, afloat2, afloat1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSectionName() {
/* 81 */     return "font";
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\data\FontMetadataSectionSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */