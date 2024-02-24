/*     */ package net.minecraft.server.management;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.io.Files;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class UserList<K, V extends UserListEntry<K>> {
/*  24 */   protected static final Logger logger = LogManager.getLogger();
/*     */   protected final Gson gson;
/*     */   private final File saveFile;
/*  27 */   private final Map<String, V> values = Maps.newHashMap(); private boolean lanServer = true;
/*     */   
/*  29 */   private static final ParameterizedType saveFileFormat = new ParameterizedType()
/*     */     {
/*     */       public Type[] getActualTypeArguments()
/*     */       {
/*  33 */         return new Type[] { UserListEntry.class };
/*     */       }
/*     */       
/*     */       public Type getRawType() {
/*  37 */         return List.class;
/*     */       }
/*     */       
/*     */       public Type getOwnerType() {
/*  41 */         return null;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public UserList(File saveFile) {
/*  47 */     this.saveFile = saveFile;
/*  48 */     GsonBuilder gsonbuilder = (new GsonBuilder()).setPrettyPrinting();
/*  49 */     gsonbuilder.registerTypeHierarchyAdapter(UserListEntry.class, new Serializer());
/*  50 */     this.gson = gsonbuilder.create();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLanServer() {
/*  55 */     return this.lanServer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLanServer(boolean state) {
/*  60 */     this.lanServer = state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEntry(V entry) {
/*  68 */     this.values.put(getObjectKey(entry.getValue()), entry);
/*     */ 
/*     */     
/*     */     try {
/*  72 */       writeChanges();
/*     */     }
/*  74 */     catch (IOException ioexception) {
/*     */       
/*  76 */       logger.warn("Could not save the list after adding a user.", ioexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public V getEntry(K obj) {
/*  82 */     removeExpired();
/*  83 */     return this.values.get(getObjectKey(obj));
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeEntry(K entry) {
/*  88 */     this.values.remove(getObjectKey(entry));
/*     */ 
/*     */     
/*     */     try {
/*  92 */       writeChanges();
/*     */     }
/*  94 */     catch (IOException ioexception) {
/*     */       
/*  96 */       logger.warn("Could not save the list after removing a user.", ioexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getKeys() {
/* 102 */     return (String[])this.values.keySet().toArray((Object[])new String[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getObjectKey(K obj) {
/* 110 */     return obj.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean hasEntry(K entry) {
/* 115 */     return this.values.containsKey(getObjectKey(entry));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeExpired() {
/* 123 */     List<K> list = Lists.newArrayList();
/*     */     
/* 125 */     for (UserListEntry<K> userListEntry : this.values.values()) {
/*     */       
/* 127 */       if (userListEntry.hasBanExpired())
/*     */       {
/* 129 */         list.add(userListEntry.getValue());
/*     */       }
/*     */     } 
/*     */     
/* 133 */     for (K k : list)
/*     */     {
/* 135 */       this.values.remove(k);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected UserListEntry<K> createEntry(JsonObject entryData) {
/* 141 */     return new UserListEntry<>(null, entryData);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Map<String, V> getValues() {
/* 146 */     return this.values;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeChanges() throws IOException {
/* 151 */     Collection<V> collection = this.values.values();
/* 152 */     String s = this.gson.toJson(collection);
/* 153 */     BufferedWriter bufferedwriter = null;
/*     */ 
/*     */     
/*     */     try {
/* 157 */       bufferedwriter = Files.newWriter(this.saveFile, Charsets.UTF_8);
/* 158 */       bufferedwriter.write(s);
/*     */     }
/*     */     finally {
/*     */       
/* 162 */       IOUtils.closeQuietly(bufferedwriter);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class Serializer
/*     */     implements JsonDeserializer<UserListEntry<K>>, JsonSerializer<UserListEntry<K>>
/*     */   {
/*     */     public JsonElement serialize(UserListEntry<K> p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 174 */       JsonObject jsonobject = new JsonObject();
/* 175 */       p_serialize_1_.onSerialization(jsonobject);
/* 176 */       return (JsonElement)jsonobject;
/*     */     }
/*     */ 
/*     */     
/*     */     public UserListEntry<K> deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 181 */       if (p_deserialize_1_.isJsonObject()) {
/*     */         
/* 183 */         JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 184 */         UserListEntry<K> userlistentry = UserList.this.createEntry(jsonobject);
/* 185 */         return userlistentry;
/*     */       } 
/*     */ 
/*     */       
/* 189 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\server\management\UserList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */