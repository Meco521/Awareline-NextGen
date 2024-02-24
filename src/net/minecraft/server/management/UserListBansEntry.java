/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.Date;
/*    */ import java.util.UUID;
/*    */ 
/*    */ 
/*    */ public class UserListBansEntry
/*    */   extends BanEntry<GameProfile>
/*    */ {
/*    */   public UserListBansEntry(GameProfile profile) {
/* 13 */     this(profile, (Date)null, (String)null, (Date)null, (String)null);
/*    */   }
/*    */ 
/*    */   
/*    */   public UserListBansEntry(GameProfile profile, Date startDate, String banner, Date endDate, String banReason) {
/* 18 */     super(profile, endDate, banner, endDate, banReason);
/*    */   }
/*    */ 
/*    */   
/*    */   public UserListBansEntry(JsonObject json) {
/* 23 */     super(toGameProfile(json), json);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onSerialization(JsonObject data) {
/* 28 */     if (getValue() != null) {
/*    */       
/* 30 */       data.addProperty("uuid", (getValue().getId() == null) ? "" : getValue().getId().toString());
/* 31 */       data.addProperty("name", getValue().getName());
/* 32 */       super.onSerialization(data);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static GameProfile toGameProfile(JsonObject json) {
/* 42 */     if (json.has("uuid") && json.has("name")) {
/*    */       UUID uuid;
/* 44 */       String s = json.get("uuid").getAsString();
/*    */ 
/*    */ 
/*    */       
/*    */       try {
/* 49 */         uuid = UUID.fromString(s);
/*    */       }
/* 51 */       catch (Throwable var4) {
/*    */         
/* 53 */         return null;
/*    */       } 
/*    */       
/* 56 */       return new GameProfile(uuid, json.get("name").getAsString());
/*    */     } 
/*    */ 
/*    */     
/* 60 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\server\management\UserListBansEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */