/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface IChatComponent
/*     */   extends Iterable<IChatComponent>
/*     */ {
/*     */   IChatComponent setChatStyle(ChatStyle paramChatStyle);
/*     */   
/*     */   ChatStyle getChatStyle();
/*     */   
/*     */   IChatComponent appendText(String paramString);
/*     */   
/*     */   IChatComponent appendSibling(IChatComponent paramIChatComponent);
/*     */   
/*     */   String getUnformattedTextForChat();
/*     */   
/*     */   String getUnformattedText();
/*     */   
/*     */   String getFormattedText();
/*     */   
/*     */   List<IChatComponent> getSiblings();
/*     */   
/*     */   IChatComponent createCopy();
/*     */   
/*     */   public static class Serializer
/*     */     implements JsonDeserializer<IChatComponent>, JsonSerializer<IChatComponent>
/*     */   {
/*     */     private static final Gson GSON;
/*     */     
/*     */     public IChatComponent deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/*     */       IChatComponent ichatcomponent;
/*  54 */       if (p_deserialize_1_.isJsonPrimitive())
/*     */       {
/*  56 */         return new ChatComponentText(p_deserialize_1_.getAsString());
/*     */       }
/*  58 */       if (!p_deserialize_1_.isJsonObject()) {
/*     */         
/*  60 */         if (p_deserialize_1_.isJsonArray()) {
/*     */           
/*  62 */           JsonArray jsonarray1 = p_deserialize_1_.getAsJsonArray();
/*  63 */           IChatComponent ichatcomponent1 = null;
/*     */           
/*  65 */           for (JsonElement jsonelement : jsonarray1) {
/*     */             
/*  67 */             IChatComponent ichatcomponent2 = deserialize(jsonelement, jsonelement.getClass(), p_deserialize_3_);
/*     */             
/*  69 */             if (ichatcomponent1 == null) {
/*     */               
/*  71 */               ichatcomponent1 = ichatcomponent2;
/*     */               
/*     */               continue;
/*     */             } 
/*  75 */             ichatcomponent1.appendSibling(ichatcomponent2);
/*     */           } 
/*     */ 
/*     */           
/*  79 */           return ichatcomponent1;
/*     */         } 
/*     */ 
/*     */         
/*  83 */         throw new JsonParseException("Don't know how to turn " + p_deserialize_1_.toString() + " into a Component");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  88 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/*     */ 
/*     */       
/*  91 */       if (jsonobject.has("text")) {
/*     */         
/*  93 */         ichatcomponent = new ChatComponentText(jsonobject.get("text").getAsString());
/*     */       }
/*  95 */       else if (jsonobject.has("translate")) {
/*     */         
/*  97 */         String s = jsonobject.get("translate").getAsString();
/*     */         
/*  99 */         if (jsonobject.has("with"))
/*     */         {
/* 101 */           JsonArray jsonarray = jsonobject.getAsJsonArray("with");
/* 102 */           Object[] aobject = new Object[jsonarray.size()];
/*     */           
/* 104 */           for (int i = 0; i < aobject.length; i++) {
/*     */             
/* 106 */             aobject[i] = deserialize(jsonarray.get(i), p_deserialize_2_, p_deserialize_3_);
/*     */             
/* 108 */             if (aobject[i] instanceof ChatComponentText) {
/*     */               
/* 110 */               ChatComponentText chatcomponenttext = (ChatComponentText)aobject[i];
/*     */               
/* 112 */               if (chatcomponenttext.getChatStyle().isEmpty() && chatcomponenttext.getSiblings().isEmpty())
/*     */               {
/* 114 */                 aobject[i] = chatcomponenttext.getChatComponentText_TextValue();
/*     */               }
/*     */             } 
/*     */           } 
/*     */           
/* 119 */           ichatcomponent = new ChatComponentTranslation(s, aobject);
/*     */         }
/*     */         else
/*     */         {
/* 123 */           ichatcomponent = new ChatComponentTranslation(s, new Object[0]);
/*     */         }
/*     */       
/* 126 */       } else if (jsonobject.has("score")) {
/*     */         
/* 128 */         JsonObject jsonobject1 = jsonobject.getAsJsonObject("score");
/*     */         
/* 130 */         if (!jsonobject1.has("name") || !jsonobject1.has("objective"))
/*     */         {
/* 132 */           throw new JsonParseException("A score component needs a least a name and an objective");
/*     */         }
/*     */         
/* 135 */         ichatcomponent = new ChatComponentScore(JsonUtils.getString(jsonobject1, "name"), JsonUtils.getString(jsonobject1, "objective"));
/*     */         
/* 137 */         if (jsonobject1.has("value"))
/*     */         {
/* 139 */           ((ChatComponentScore)ichatcomponent).setValue(JsonUtils.getString(jsonobject1, "value"));
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 144 */         if (!jsonobject.has("selector"))
/*     */         {
/* 146 */           throw new JsonParseException("Don't know how to turn " + p_deserialize_1_.toString() + " into a Component");
/*     */         }
/*     */         
/* 149 */         ichatcomponent = new ChatComponentSelector(JsonUtils.getString(jsonobject, "selector"));
/*     */       } 
/*     */       
/* 152 */       if (jsonobject.has("extra")) {
/*     */         
/* 154 */         JsonArray jsonarray2 = jsonobject.getAsJsonArray("extra");
/*     */         
/* 156 */         if (jsonarray2.size() <= 0)
/*     */         {
/* 158 */           throw new JsonParseException("Unexpected empty array of components");
/*     */         }
/*     */         
/* 161 */         for (int j = 0; j < jsonarray2.size(); j++)
/*     */         {
/* 163 */           ichatcomponent.appendSibling(deserialize(jsonarray2.get(j), p_deserialize_2_, p_deserialize_3_));
/*     */         }
/*     */       } 
/*     */       
/* 167 */       ichatcomponent.setChatStyle((ChatStyle)p_deserialize_3_.deserialize(p_deserialize_1_, ChatStyle.class));
/* 168 */       return ichatcomponent;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void serializeChatStyle(ChatStyle style, JsonObject object, JsonSerializationContext ctx) {
/* 174 */       JsonElement jsonelement = ctx.serialize(style);
/*     */       
/* 176 */       if (jsonelement.isJsonObject()) {
/*     */         
/* 178 */         JsonObject jsonobject = (JsonObject)jsonelement;
/*     */         
/* 180 */         for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet())
/*     */         {
/* 182 */           object.add(entry.getKey(), entry.getValue());
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonElement serialize(IChatComponent p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 189 */       if (p_serialize_1_ instanceof ChatComponentText && p_serialize_1_.getChatStyle().isEmpty() && p_serialize_1_.getSiblings().isEmpty())
/*     */       {
/* 191 */         return (JsonElement)new JsonPrimitive(((ChatComponentText)p_serialize_1_).getChatComponentText_TextValue());
/*     */       }
/*     */ 
/*     */       
/* 195 */       JsonObject jsonobject = new JsonObject();
/*     */       
/* 197 */       if (!p_serialize_1_.getChatStyle().isEmpty())
/*     */       {
/* 199 */         serializeChatStyle(p_serialize_1_.getChatStyle(), jsonobject, p_serialize_3_);
/*     */       }
/*     */       
/* 202 */       if (!p_serialize_1_.getSiblings().isEmpty()) {
/*     */         
/* 204 */         JsonArray jsonarray = new JsonArray();
/*     */         
/* 206 */         for (IChatComponent ichatcomponent : p_serialize_1_.getSiblings())
/*     */         {
/* 208 */           jsonarray.add(serialize(ichatcomponent, ichatcomponent.getClass(), p_serialize_3_));
/*     */         }
/*     */         
/* 211 */         jsonobject.add("extra", (JsonElement)jsonarray);
/*     */       } 
/*     */       
/* 214 */       if (p_serialize_1_ instanceof ChatComponentText) {
/*     */         
/* 216 */         jsonobject.addProperty("text", ((ChatComponentText)p_serialize_1_).getChatComponentText_TextValue());
/*     */       }
/* 218 */       else if (p_serialize_1_ instanceof ChatComponentTranslation) {
/*     */         
/* 220 */         ChatComponentTranslation chatcomponenttranslation = (ChatComponentTranslation)p_serialize_1_;
/* 221 */         jsonobject.addProperty("translate", chatcomponenttranslation.getKey());
/*     */         
/* 223 */         if (chatcomponenttranslation.getFormatArgs() != null && (chatcomponenttranslation.getFormatArgs()).length > 0)
/*     */         {
/* 225 */           JsonArray jsonarray1 = new JsonArray();
/*     */           
/* 227 */           for (Object object : chatcomponenttranslation.getFormatArgs()) {
/*     */             
/* 229 */             if (object instanceof IChatComponent) {
/*     */               
/* 231 */               jsonarray1.add(serialize((IChatComponent)object, object.getClass(), p_serialize_3_));
/*     */             }
/*     */             else {
/*     */               
/* 235 */               jsonarray1.add((JsonElement)new JsonPrimitive(String.valueOf(object)));
/*     */             } 
/*     */           } 
/*     */           
/* 239 */           jsonobject.add("with", (JsonElement)jsonarray1);
/*     */         }
/*     */       
/* 242 */       } else if (p_serialize_1_ instanceof ChatComponentScore) {
/*     */         
/* 244 */         ChatComponentScore chatcomponentscore = (ChatComponentScore)p_serialize_1_;
/* 245 */         JsonObject jsonobject1 = new JsonObject();
/* 246 */         jsonobject1.addProperty("name", chatcomponentscore.getName());
/* 247 */         jsonobject1.addProperty("objective", chatcomponentscore.getObjective());
/* 248 */         jsonobject1.addProperty("value", chatcomponentscore.getUnformattedTextForChat());
/* 249 */         jsonobject.add("score", (JsonElement)jsonobject1);
/*     */       }
/*     */       else {
/*     */         
/* 253 */         if (!(p_serialize_1_ instanceof ChatComponentSelector))
/*     */         {
/* 255 */           throw new IllegalArgumentException("Don't know how to serialize " + p_serialize_1_ + " as a Component");
/*     */         }
/*     */         
/* 258 */         ChatComponentSelector chatcomponentselector = (ChatComponentSelector)p_serialize_1_;
/* 259 */         jsonobject.addProperty("selector", chatcomponentselector.getSelector());
/*     */       } 
/*     */       
/* 262 */       return (JsonElement)jsonobject;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public static String componentToJson(IChatComponent component) {
/* 268 */       return GSON.toJson(component);
/*     */     }
/*     */ 
/*     */     
/*     */     public static IChatComponent jsonToComponent(String json) {
/* 273 */       return (IChatComponent)GSON.fromJson(json, IChatComponent.class);
/*     */     }
/*     */ 
/*     */     
/*     */     static {
/* 278 */       GsonBuilder gsonbuilder = new GsonBuilder();
/* 279 */       gsonbuilder.registerTypeHierarchyAdapter(IChatComponent.class, new Serializer());
/* 280 */       gsonbuilder.registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer());
/* 281 */       gsonbuilder.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
/* 282 */       GSON = gsonbuilder.create();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\IChatComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */