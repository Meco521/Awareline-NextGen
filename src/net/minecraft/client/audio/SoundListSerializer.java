/*    */ package net.minecraft.client.audio;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ public class SoundListSerializer implements JsonDeserializer<SoundList> {
/*    */   public SoundList deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 13 */     JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "entry");
/* 14 */     SoundList soundlist = new SoundList();
/* 15 */     soundlist.setReplaceExisting(JsonUtils.getBoolean(jsonobject, "replace", false));
/* 16 */     SoundCategory soundcategory = SoundCategory.getCategory(JsonUtils.getString(jsonobject, "category", SoundCategory.MASTER.getCategoryName()));
/* 17 */     soundlist.setSoundCategory(soundcategory);
/* 18 */     Validate.notNull(soundcategory, "Invalid category", new Object[0]);
/*    */     
/* 20 */     if (jsonobject.has("sounds")) {
/*    */       
/* 22 */       JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "sounds");
/*    */       
/* 24 */       for (int i = 0; i < jsonarray.size(); i++) {
/*    */         
/* 26 */         JsonElement jsonelement = jsonarray.get(i);
/* 27 */         SoundList.SoundEntry soundlist$soundentry = new SoundList.SoundEntry();
/*    */         
/* 29 */         if (JsonUtils.isString(jsonelement)) {
/*    */           
/* 31 */           soundlist$soundentry.setSoundEntryName(JsonUtils.getString(jsonelement, "sound"));
/*    */         }
/*    */         else {
/*    */           
/* 35 */           JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonelement, "sound");
/* 36 */           soundlist$soundentry.setSoundEntryName(JsonUtils.getString(jsonobject1, "name"));
/*    */           
/* 38 */           if (jsonobject1.has("type")) {
/*    */             
/* 40 */             SoundList.SoundEntry.Type soundlist$soundentry$type = SoundList.SoundEntry.Type.getType(JsonUtils.getString(jsonobject1, "type"));
/* 41 */             Validate.notNull(soundlist$soundentry$type, "Invalid type", new Object[0]);
/* 42 */             soundlist$soundentry.setSoundEntryType(soundlist$soundentry$type);
/*    */           } 
/*    */           
/* 45 */           if (jsonobject1.has("volume")) {
/*    */             
/* 47 */             float f = JsonUtils.getFloat(jsonobject1, "volume");
/* 48 */             Validate.isTrue((f > 0.0F), "Invalid volume", new Object[0]);
/* 49 */             soundlist$soundentry.setSoundEntryVolume(f);
/*    */           } 
/*    */           
/* 52 */           if (jsonobject1.has("pitch")) {
/*    */             
/* 54 */             float f1 = JsonUtils.getFloat(jsonobject1, "pitch");
/* 55 */             Validate.isTrue((f1 > 0.0F), "Invalid pitch", new Object[0]);
/* 56 */             soundlist$soundentry.setSoundEntryPitch(f1);
/*    */           } 
/*    */           
/* 59 */           if (jsonobject1.has("weight")) {
/*    */             
/* 61 */             int j = JsonUtils.getInt(jsonobject1, "weight");
/* 62 */             Validate.isTrue((j > 0), "Invalid weight", new Object[0]);
/* 63 */             soundlist$soundentry.setSoundEntryWeight(j);
/*    */           } 
/*    */           
/* 66 */           if (jsonobject1.has("stream"))
/*    */           {
/* 68 */             soundlist$soundentry.setStreaming(JsonUtils.getBoolean(jsonobject1, "stream"));
/*    */           }
/*    */         } 
/*    */         
/* 72 */         soundlist.getSoundList().add(soundlist$soundentry);
/*    */       } 
/*    */     } 
/*    */     
/* 76 */     return soundlist;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\audio\SoundListSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */