/*     */ package net.minecraft.server.management;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.ProfileLookupCallback;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ 
/*     */ public class PlayerProfileCache {
/*  25 */   public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
/*  26 */   private final Map<String, ProfileEntry> usernameToProfileEntryMap = Maps.newHashMap();
/*  27 */   private final Map<UUID, ProfileEntry> uuidToProfileEntryMap = Maps.newHashMap();
/*  28 */   private final LinkedList<GameProfile> gameProfiles = Lists.newLinkedList(); private final MinecraftServer mcServer;
/*     */   protected final Gson gson;
/*     */   private final File usercacheFile;
/*     */   
/*  32 */   private static final ParameterizedType TYPE = new ParameterizedType()
/*     */     {
/*     */       public Type[] getActualTypeArguments()
/*     */       {
/*  36 */         return new Type[] { PlayerProfileCache.ProfileEntry.class };
/*     */       }
/*     */       
/*     */       public Type getRawType() {
/*  40 */         return List.class;
/*     */       }
/*     */       
/*     */       public Type getOwnerType() {
/*  44 */         return null;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public PlayerProfileCache(MinecraftServer server, File cacheFile) {
/*  50 */     this.mcServer = server;
/*  51 */     this.usercacheFile = cacheFile;
/*  52 */     GsonBuilder gsonbuilder = new GsonBuilder();
/*  53 */     gsonbuilder.registerTypeHierarchyAdapter(ProfileEntry.class, new Serializer());
/*  54 */     this.gson = gsonbuilder.create();
/*  55 */     load();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static GameProfile getGameProfile(MinecraftServer server, String username) {
/*  66 */     final GameProfile[] agameprofile = new GameProfile[1];
/*  67 */     ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback()
/*     */       {
/*     */         public void onProfileLookupSucceeded(GameProfile p_onProfileLookupSucceeded_1_)
/*     */         {
/*  71 */           agameprofile[0] = p_onProfileLookupSucceeded_1_;
/*     */         }
/*     */         
/*     */         public void onProfileLookupFailed(GameProfile p_onProfileLookupFailed_1_, Exception p_onProfileLookupFailed_2_) {
/*  75 */           agameprofile[0] = null;
/*     */         }
/*     */       };
/*  78 */     server.getGameProfileRepository().findProfilesByNames(new String[] { username }, Agent.MINECRAFT, profilelookupcallback);
/*     */     
/*  80 */     if (!server.isServerInOnlineMode() && agameprofile[0] == null) {
/*     */       
/*  82 */       UUID uuid = EntityPlayer.getUUID(new GameProfile((UUID)null, username));
/*  83 */       GameProfile gameprofile = new GameProfile(uuid, username);
/*  84 */       profilelookupcallback.onProfileLookupSucceeded(gameprofile);
/*     */     } 
/*     */     
/*  87 */     return agameprofile[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEntry(GameProfile gameProfile) {
/*  95 */     addEntry(gameProfile, (Date)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addEntry(GameProfile gameProfile, Date expirationDate) {
/* 103 */     UUID uuid = gameProfile.getId();
/*     */     
/* 105 */     if (expirationDate == null) {
/*     */       
/* 107 */       Calendar calendar = Calendar.getInstance();
/* 108 */       calendar.setTime(new Date());
/* 109 */       calendar.add(2, 1);
/* 110 */       expirationDate = calendar.getTime();
/*     */     } 
/*     */     
/* 113 */     String s = gameProfile.getName().toLowerCase(Locale.ROOT);
/* 114 */     ProfileEntry playerprofilecache$profileentry = new ProfileEntry(gameProfile, expirationDate);
/*     */     
/* 116 */     if (this.uuidToProfileEntryMap.containsKey(uuid)) {
/*     */       
/* 118 */       ProfileEntry playerprofilecache$profileentry1 = this.uuidToProfileEntryMap.get(uuid);
/* 119 */       this.usernameToProfileEntryMap.remove(playerprofilecache$profileentry1.getGameProfile().getName().toLowerCase(Locale.ROOT));
/* 120 */       this.gameProfiles.remove(gameProfile);
/*     */     } 
/*     */     
/* 123 */     this.usernameToProfileEntryMap.put(gameProfile.getName().toLowerCase(Locale.ROOT), playerprofilecache$profileentry);
/* 124 */     this.uuidToProfileEntryMap.put(uuid, playerprofilecache$profileentry);
/* 125 */     this.gameProfiles.addFirst(gameProfile);
/* 126 */     save();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameProfile getGameProfileForUsername(String username) {
/* 135 */     String s = username.toLowerCase(Locale.ROOT);
/* 136 */     ProfileEntry playerprofilecache$profileentry = this.usernameToProfileEntryMap.get(s);
/*     */     
/* 138 */     if (playerprofilecache$profileentry != null && (new Date()).getTime() >= playerprofilecache$profileentry.expirationDate.getTime()) {
/*     */       
/* 140 */       this.uuidToProfileEntryMap.remove(playerprofilecache$profileentry.getGameProfile().getId());
/* 141 */       this.usernameToProfileEntryMap.remove(playerprofilecache$profileentry.getGameProfile().getName().toLowerCase(Locale.ROOT));
/* 142 */       this.gameProfiles.remove(playerprofilecache$profileentry.getGameProfile());
/* 143 */       playerprofilecache$profileentry = null;
/*     */     } 
/*     */     
/* 146 */     if (playerprofilecache$profileentry != null) {
/*     */       
/* 148 */       GameProfile gameprofile = playerprofilecache$profileentry.getGameProfile();
/* 149 */       this.gameProfiles.remove(gameprofile);
/* 150 */       this.gameProfiles.addFirst(gameprofile);
/*     */     }
/*     */     else {
/*     */       
/* 154 */       GameProfile gameprofile1 = getGameProfile(this.mcServer, s);
/*     */       
/* 156 */       if (gameprofile1 != null) {
/*     */         
/* 158 */         addEntry(gameprofile1);
/* 159 */         playerprofilecache$profileentry = this.usernameToProfileEntryMap.get(s);
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     save();
/* 164 */     return (playerprofilecache$profileentry == null) ? null : playerprofilecache$profileentry.getGameProfile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getUsernames() {
/* 172 */     List<String> list = Lists.newArrayList(this.usernameToProfileEntryMap.keySet());
/* 173 */     return list.<String>toArray(new String[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameProfile getProfileByUUID(UUID uuid) {
/* 181 */     ProfileEntry playerprofilecache$profileentry = this.uuidToProfileEntryMap.get(uuid);
/* 182 */     return (playerprofilecache$profileentry == null) ? null : playerprofilecache$profileentry.getGameProfile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ProfileEntry getByUUID(UUID uuid) {
/* 190 */     ProfileEntry playerprofilecache$profileentry = this.uuidToProfileEntryMap.get(uuid);
/*     */     
/* 192 */     if (playerprofilecache$profileentry != null) {
/*     */       
/* 194 */       GameProfile gameprofile = playerprofilecache$profileentry.getGameProfile();
/* 195 */       this.gameProfiles.remove(gameprofile);
/* 196 */       this.gameProfiles.addFirst(gameprofile);
/*     */     } 
/*     */     
/* 199 */     return playerprofilecache$profileentry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load() {
/* 207 */     BufferedReader bufferedreader = null;
/*     */     
/*     */     try {
/* 210 */       bufferedreader = Files.newReader(this.usercacheFile, Charsets.UTF_8);
/* 211 */       List<ProfileEntry> list = (List<ProfileEntry>)this.gson.fromJson(bufferedreader, TYPE);
/* 212 */       if (list != null) {
/* 213 */         this.usernameToProfileEntryMap.clear();
/* 214 */         this.uuidToProfileEntryMap.clear();
/* 215 */         this.gameProfiles.clear();
/*     */         
/* 217 */         for (ProfileEntry playerprofilecache$profileentry : Lists.reverse(list)) {
/* 218 */           if (playerprofilecache$profileentry != null) {
/* 219 */             addEntry(playerprofilecache$profileentry.getGameProfile(), playerprofilecache$profileentry.getExpirationDate());
/*     */           }
/*     */         }
/*     */       
/*     */       } 
/* 224 */     } catch (FileNotFoundException fileNotFoundException) {
/*     */ 
/*     */     
/*     */     }
/* 228 */     catch (JsonParseException jsonParseException) {
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 234 */       IOUtils.closeQuietly(bufferedreader);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save() {
/* 243 */     String s = this.gson.toJson(getEntriesWithLimit(1000));
/* 244 */     BufferedWriter bufferedwriter = null;
/*     */ 
/*     */     
/*     */     try {
/* 248 */       bufferedwriter = Files.newWriter(this.usercacheFile, Charsets.UTF_8);
/* 249 */       bufferedwriter.write(s);
/*     */       
/*     */       return;
/* 252 */     } catch (FileNotFoundException fileNotFoundException) {
/*     */ 
/*     */     
/*     */     }
/* 256 */     catch (IOException var9) {
/*     */       
/*     */       return;
/*     */     }
/*     */     finally {
/*     */       
/* 262 */       IOUtils.closeQuietly(bufferedwriter);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private List<ProfileEntry> getEntriesWithLimit(int limitSize) {
/* 268 */     ArrayList<ProfileEntry> arraylist = Lists.newArrayList();
/*     */     
/* 270 */     for (GameProfile gameprofile : Lists.newArrayList(Iterators.limit(this.gameProfiles.iterator(), limitSize))) {
/*     */       
/* 272 */       ProfileEntry playerprofilecache$profileentry = getByUUID(gameprofile.getId());
/*     */       
/* 274 */       if (playerprofilecache$profileentry != null)
/*     */       {
/* 276 */         arraylist.add(playerprofilecache$profileentry);
/*     */       }
/*     */     } 
/*     */     
/* 280 */     return arraylist;
/*     */   }
/*     */ 
/*     */   
/*     */   static class ProfileEntry
/*     */   {
/*     */     private final GameProfile gameProfile;
/*     */     final Date expirationDate;
/*     */     
/*     */     ProfileEntry(GameProfile gameProfileIn, Date expirationDateIn) {
/* 290 */       this.gameProfile = gameProfileIn;
/* 291 */       this.expirationDate = expirationDateIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public GameProfile getGameProfile() {
/* 296 */       return this.gameProfile;
/*     */     }
/*     */ 
/*     */     
/*     */     public Date getExpirationDate() {
/* 301 */       return this.expirationDate;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class Serializer
/*     */     implements JsonDeserializer<ProfileEntry>, JsonSerializer<ProfileEntry>
/*     */   {
/*     */     public JsonElement serialize(PlayerProfileCache.ProfileEntry p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 313 */       JsonObject jsonobject = new JsonObject();
/* 314 */       jsonobject.addProperty("name", p_serialize_1_.getGameProfile().getName());
/* 315 */       UUID uuid = p_serialize_1_.getGameProfile().getId();
/* 316 */       jsonobject.addProperty("uuid", (uuid == null) ? "" : uuid.toString());
/* 317 */       jsonobject.addProperty("expiresOn", PlayerProfileCache.dateFormat.format(p_serialize_1_.getExpirationDate()));
/* 318 */       return (JsonElement)jsonobject;
/*     */     }
/*     */ 
/*     */     
/*     */     public PlayerProfileCache.ProfileEntry deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 323 */       if (p_deserialize_1_.isJsonObject()) {
/*     */         
/* 325 */         JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 326 */         JsonElement jsonelement = jsonobject.get("name");
/* 327 */         JsonElement jsonelement1 = jsonobject.get("uuid");
/* 328 */         JsonElement jsonelement2 = jsonobject.get("expiresOn");
/*     */         
/* 330 */         if (jsonelement != null && jsonelement1 != null) {
/*     */           
/* 332 */           String s = jsonelement1.getAsString();
/* 333 */           String s1 = jsonelement.getAsString();
/* 334 */           Date date = null;
/*     */           
/* 336 */           if (jsonelement2 != null) {
/*     */             
/*     */             try {
/*     */               
/* 340 */               date = PlayerProfileCache.dateFormat.parse(jsonelement2.getAsString());
/*     */             }
/* 342 */             catch (ParseException var14) {
/*     */               
/* 344 */               date = null;
/*     */             } 
/*     */           }
/*     */           
/* 348 */           if (s1 != null && s != null) {
/*     */             UUID uuid;
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/* 354 */               uuid = UUID.fromString(s);
/*     */             }
/* 356 */             catch (Throwable var13) {
/*     */               
/* 358 */               return null;
/*     */             } 
/*     */             
/* 361 */             PlayerProfileCache.ProfileEntry playerprofilecache$profileentry = new PlayerProfileCache.ProfileEntry(new GameProfile(uuid, s1), date);
/* 362 */             return playerprofilecache$profileentry;
/*     */           } 
/*     */ 
/*     */           
/* 366 */           return null;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 371 */         return null;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 376 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\server\management\PlayerProfileCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */