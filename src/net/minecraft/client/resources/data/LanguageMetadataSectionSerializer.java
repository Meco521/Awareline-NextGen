/*    */ package net.minecraft.client.resources.data;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.resources.Language;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ 
/*    */ 
/*    */ public class LanguageMetadataSectionSerializer
/*    */   extends BaseMetadataSectionSerializer<LanguageMetadataSection>
/*    */ {
/*    */   public LanguageMetadataSection deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 19 */     JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 20 */     Set<Language> set = Sets.newHashSet();
/*    */     
/* 22 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet()) {
/*    */       
/* 24 */       String s = entry.getKey();
/* 25 */       JsonObject jsonobject1 = JsonUtils.getJsonObject(entry.getValue(), "language");
/* 26 */       String s1 = JsonUtils.getString(jsonobject1, "region");
/* 27 */       String s2 = JsonUtils.getString(jsonobject1, "name");
/* 28 */       boolean flag = JsonUtils.getBoolean(jsonobject1, "bidirectional", false);
/*    */       
/* 30 */       if (s1.isEmpty())
/*    */       {
/* 32 */         throw new JsonParseException("Invalid language->'" + s + "'->region: empty value");
/*    */       }
/*    */       
/* 35 */       if (s2.isEmpty())
/*    */       {
/* 37 */         throw new JsonParseException("Invalid language->'" + s + "'->name: empty value");
/*    */       }
/*    */       
/* 40 */       if (!set.add(new Language(s, s1, s2, flag)))
/*    */       {
/* 42 */         throw new JsonParseException("Duplicate language->'" + s + "' defined");
/*    */       }
/*    */     } 
/*    */     
/* 46 */     return new LanguageMetadataSection(set);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSectionName() {
/* 54 */     return "language";
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\data\LanguageMetadataSectionSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */