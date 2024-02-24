/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.UUID;
/*    */ 
/*    */ 
/*    */ public class UserListWhitelistEntry
/*    */   extends UserListEntry<GameProfile>
/*    */ {
/*    */   public UserListWhitelistEntry(GameProfile profile) {
/* 12 */     super(profile);
/*    */   }
/*    */ 
/*    */   
/*    */   public UserListWhitelistEntry(JsonObject json) {
/* 17 */     super(gameProfileFromJsonObject(json), json);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onSerialization(JsonObject data) {
/* 22 */     if (getValue() != null) {
/*    */       
/* 24 */       data.addProperty("uuid", (getValue().getId() == null) ? "" : getValue().getId().toString());
/* 25 */       data.addProperty("name", getValue().getName());
/* 26 */       super.onSerialization(data);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static GameProfile gameProfileFromJsonObject(JsonObject json) {
/* 32 */     if (json.has("uuid") && json.has("name")) {
/*    */       UUID uuid;
/* 34 */       String s = json.get("uuid").getAsString();
/*    */ 
/*    */ 
/*    */       
/*    */       try {
/* 39 */         uuid = UUID.fromString(s);
/*    */       }
/* 41 */       catch (Throwable var4) {
/*    */         
/* 43 */         return null;
/*    */       } 
/*    */       
/* 46 */       return new GameProfile(uuid, json.get("name").getAsString());
/*    */     } 
/*    */ 
/*    */     
/* 50 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\server\management\UserListWhitelistEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */