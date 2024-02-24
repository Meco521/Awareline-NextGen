/*     */ package net.minecraft.network;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ 
/*     */ public class ServerStatusResponse {
/*     */   private IChatComponent serverMotd;
/*     */   private PlayerCountData playerCount;
/*     */   
/*     */   public IChatComponent getServerDescription() {
/*  20 */     return this.serverMotd;
/*     */   }
/*     */   private MinecraftProtocolVersionIdentifier protocolVersion; private String favicon;
/*     */   
/*     */   public void setServerDescription(IChatComponent motd) {
/*  25 */     this.serverMotd = motd;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlayerCountData getPlayerCountData() {
/*  30 */     return this.playerCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlayerCountData(PlayerCountData countData) {
/*  35 */     this.playerCount = countData;
/*     */   }
/*     */ 
/*     */   
/*     */   public MinecraftProtocolVersionIdentifier getProtocolVersionInfo() {
/*  40 */     return this.protocolVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProtocolVersionInfo(MinecraftProtocolVersionIdentifier protocolVersionData) {
/*  45 */     this.protocolVersion = protocolVersionData;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFavicon(String faviconBlob) {
/*  50 */     this.favicon = faviconBlob;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFavicon() {
/*  55 */     return this.favicon;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class MinecraftProtocolVersionIdentifier
/*     */   {
/*     */     private final String name;
/*     */     private final int protocol;
/*     */     
/*     */     public MinecraftProtocolVersionIdentifier(String nameIn, int protocolIn) {
/*  65 */       this.name = nameIn;
/*  66 */       this.protocol = protocolIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/*  71 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getProtocol() {
/*  76 */       return this.protocol;
/*     */     }
/*     */     
/*     */     public static class Serializer
/*     */       implements JsonDeserializer<MinecraftProtocolVersionIdentifier>, JsonSerializer<MinecraftProtocolVersionIdentifier>
/*     */     {
/*     */       public ServerStatusResponse.MinecraftProtocolVersionIdentifier deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/*  83 */         JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "version");
/*  84 */         return new ServerStatusResponse.MinecraftProtocolVersionIdentifier(JsonUtils.getString(jsonobject, "name"), JsonUtils.getInt(jsonobject, "protocol"));
/*     */       }
/*     */ 
/*     */       
/*     */       public JsonElement serialize(ServerStatusResponse.MinecraftProtocolVersionIdentifier p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/*  89 */         JsonObject jsonobject = new JsonObject();
/*  90 */         jsonobject.addProperty("name", p_serialize_1_.getName());
/*  91 */         jsonobject.addProperty("protocol", Integer.valueOf(p_serialize_1_.getProtocol()));
/*  92 */         return (JsonElement)jsonobject;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class PlayerCountData
/*     */   {
/*     */     private final int maxPlayers;
/*     */     private final int onlinePlayerCount;
/*     */     private GameProfile[] players;
/*     */     
/*     */     public PlayerCountData(int maxOnlinePlayers, int onlinePlayers) {
/* 105 */       this.maxPlayers = maxOnlinePlayers;
/* 106 */       this.onlinePlayerCount = onlinePlayers;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMaxPlayers() {
/* 111 */       return this.maxPlayers;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getOnlinePlayerCount() {
/* 116 */       return this.onlinePlayerCount;
/*     */     }
/*     */ 
/*     */     
/*     */     public GameProfile[] getPlayers() {
/* 121 */       return this.players;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setPlayers(GameProfile[] playersIn) {
/* 126 */       this.players = playersIn;
/*     */     }
/*     */     
/*     */     public static class Serializer
/*     */       implements JsonDeserializer<PlayerCountData>, JsonSerializer<PlayerCountData>
/*     */     {
/*     */       public ServerStatusResponse.PlayerCountData deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 133 */         JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "players");
/* 134 */         ServerStatusResponse.PlayerCountData serverstatusresponse$playercountdata = new ServerStatusResponse.PlayerCountData(JsonUtils.getInt(jsonobject, "max"), JsonUtils.getInt(jsonobject, "online"));
/*     */         
/* 136 */         if (JsonUtils.isJsonArray(jsonobject, "sample")) {
/*     */           
/* 138 */           JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "sample");
/*     */           
/* 140 */           if (jsonarray.size() > 0) {
/*     */             
/* 142 */             GameProfile[] agameprofile = new GameProfile[jsonarray.size()];
/*     */             
/* 144 */             for (int i = 0; i < agameprofile.length; i++) {
/*     */               
/* 146 */               JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonarray.get(i), "player[" + i + "]");
/* 147 */               String s = JsonUtils.getString(jsonobject1, "id");
/* 148 */               agameprofile[i] = new GameProfile(UUID.fromString(s), JsonUtils.getString(jsonobject1, "name"));
/*     */             } 
/*     */             
/* 151 */             serverstatusresponse$playercountdata.setPlayers(agameprofile);
/*     */           } 
/*     */         } 
/*     */         
/* 155 */         return serverstatusresponse$playercountdata;
/*     */       }
/*     */ 
/*     */       
/*     */       public JsonElement serialize(ServerStatusResponse.PlayerCountData p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 160 */         JsonObject jsonobject = new JsonObject();
/* 161 */         jsonobject.addProperty("max", Integer.valueOf(p_serialize_1_.getMaxPlayers()));
/* 162 */         jsonobject.addProperty("online", Integer.valueOf(p_serialize_1_.getOnlinePlayerCount()));
/*     */         
/* 164 */         if (p_serialize_1_.getPlayers() != null && (p_serialize_1_.getPlayers()).length > 0) {
/*     */           
/* 166 */           JsonArray jsonarray = new JsonArray();
/*     */           
/* 168 */           for (int i = 0; i < (p_serialize_1_.getPlayers()).length; i++) {
/*     */             
/* 170 */             JsonObject jsonobject1 = new JsonObject();
/* 171 */             UUID uuid = p_serialize_1_.getPlayers()[i].getId();
/* 172 */             jsonobject1.addProperty("id", (uuid == null) ? "" : uuid.toString());
/* 173 */             jsonobject1.addProperty("name", p_serialize_1_.getPlayers()[i].getName());
/* 174 */             jsonarray.add((JsonElement)jsonobject1);
/*     */           } 
/*     */           
/* 177 */           jsonobject.add("sample", (JsonElement)jsonarray);
/*     */         } 
/*     */         
/* 180 */         return (JsonElement)jsonobject;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Serializer
/*     */     implements JsonDeserializer<ServerStatusResponse>, JsonSerializer<ServerStatusResponse>
/*     */   {
/*     */     public ServerStatusResponse deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 189 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "status");
/* 190 */       ServerStatusResponse serverstatusresponse = new ServerStatusResponse();
/*     */       
/* 192 */       if (jsonobject.has("description"))
/*     */       {
/* 194 */         serverstatusresponse.setServerDescription((IChatComponent)p_deserialize_3_.deserialize(jsonobject.get("description"), IChatComponent.class));
/*     */       }
/*     */       
/* 197 */       if (jsonobject.has("players"))
/*     */       {
/* 199 */         serverstatusresponse.setPlayerCountData((ServerStatusResponse.PlayerCountData)p_deserialize_3_.deserialize(jsonobject.get("players"), ServerStatusResponse.PlayerCountData.class));
/*     */       }
/*     */       
/* 202 */       if (jsonobject.has("version"))
/*     */       {
/* 204 */         serverstatusresponse.setProtocolVersionInfo((ServerStatusResponse.MinecraftProtocolVersionIdentifier)p_deserialize_3_.deserialize(jsonobject.get("version"), ServerStatusResponse.MinecraftProtocolVersionIdentifier.class));
/*     */       }
/*     */       
/* 207 */       if (jsonobject.has("favicon"))
/*     */       {
/* 209 */         serverstatusresponse.setFavicon(JsonUtils.getString(jsonobject, "favicon"));
/*     */       }
/*     */       
/* 212 */       return serverstatusresponse;
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonElement serialize(ServerStatusResponse p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 217 */       JsonObject jsonobject = new JsonObject();
/*     */       
/* 219 */       if (p_serialize_1_.getServerDescription() != null)
/*     */       {
/* 221 */         jsonobject.add("description", p_serialize_3_.serialize(p_serialize_1_.getServerDescription()));
/*     */       }
/*     */       
/* 224 */       if (p_serialize_1_.getPlayerCountData() != null)
/*     */       {
/* 226 */         jsonobject.add("players", p_serialize_3_.serialize(p_serialize_1_.getPlayerCountData()));
/*     */       }
/*     */       
/* 229 */       if (p_serialize_1_.getProtocolVersionInfo() != null)
/*     */       {
/* 231 */         jsonobject.add("version", p_serialize_3_.serialize(p_serialize_1_.getProtocolVersionInfo()));
/*     */       }
/*     */       
/* 234 */       if (p_serialize_1_.getFavicon() != null)
/*     */       {
/* 236 */         jsonobject.addProperty("favicon", p_serialize_1_.getFavicon());
/*     */       }
/*     */       
/* 239 */       return (JsonElement)jsonobject;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\ServerStatusResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */