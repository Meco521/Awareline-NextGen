/*    */ package net.minecraft.client.resources.data;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ 
/*    */ public class TextureMetadataSectionSerializer extends BaseMetadataSectionSerializer<TextureMetadataSection> {
/*    */   public TextureMetadataSection deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 14 */     JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 15 */     boolean flag = JsonUtils.getBoolean(jsonobject, "blur", false);
/* 16 */     boolean flag1 = JsonUtils.getBoolean(jsonobject, "clamp", false);
/* 17 */     List<Integer> list = Lists.newArrayList();
/*    */     
/* 19 */     if (jsonobject.has("mipmaps")) {
/*    */       
/*    */       try {
/*    */         
/* 23 */         JsonArray jsonarray = jsonobject.getAsJsonArray("mipmaps");
/*    */         
/* 25 */         for (int i = 0; i < jsonarray.size(); i++) {
/*    */           
/* 27 */           JsonElement jsonelement = jsonarray.get(i);
/*    */           
/* 29 */           if (jsonelement.isJsonPrimitive()) {
/*    */             
/*    */             try
/*    */             {
/* 33 */               list.add(Integer.valueOf(jsonelement.getAsInt()));
/*    */             }
/* 35 */             catch (NumberFormatException numberformatexception)
/*    */             {
/* 37 */               throw new JsonParseException("Invalid texture->mipmap->" + i + ": expected number, was " + jsonelement, numberformatexception);
/*    */             }
/*    */           
/* 40 */           } else if (jsonelement.isJsonObject()) {
/*    */             
/* 42 */             throw new JsonParseException("Invalid texture->mipmap->" + i + ": expected number, was " + jsonelement);
/*    */           }
/*    */         
/*    */         } 
/* 46 */       } catch (ClassCastException classcastexception) {
/*    */         
/* 48 */         throw new JsonParseException("Invalid texture->mipmaps: expected array, was " + jsonobject.get("mipmaps"), classcastexception);
/*    */       } 
/*    */     }
/*    */     
/* 52 */     return new TextureMetadataSection(flag, flag1, list);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSectionName() {
/* 60 */     return "texture";
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\data\TextureMetadataSectionSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */