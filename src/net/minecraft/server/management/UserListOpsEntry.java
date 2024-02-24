/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.UUID;
/*    */ 
/*    */ 
/*    */ public class UserListOpsEntry
/*    */   extends UserListEntry<GameProfile>
/*    */ {
/*    */   private final int permissionLevel;
/*    */   private final boolean bypassesPlayerLimit;
/*    */   
/*    */   public UserListOpsEntry(GameProfile player, int permissionLevelIn, boolean bypassesPlayerLimitIn) {
/* 15 */     super(player);
/* 16 */     this.permissionLevel = permissionLevelIn;
/* 17 */     this.bypassesPlayerLimit = bypassesPlayerLimitIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public UserListOpsEntry(JsonObject p_i1150_1_) {
/* 22 */     super(constructProfile(p_i1150_1_), p_i1150_1_);
/* 23 */     this.permissionLevel = p_i1150_1_.has("level") ? p_i1150_1_.get("level").getAsInt() : 0;
/* 24 */     this.bypassesPlayerLimit = (p_i1150_1_.has("bypassesPlayerLimit") && p_i1150_1_.get("bypassesPlayerLimit").getAsBoolean());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getPermissionLevel() {
/* 32 */     return this.permissionLevel;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean bypassesPlayerLimit() {
/* 37 */     return this.bypassesPlayerLimit;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onSerialization(JsonObject data) {
/* 42 */     if (getValue() != null) {
/*    */       
/* 44 */       data.addProperty("uuid", (getValue().getId() == null) ? "" : getValue().getId().toString());
/* 45 */       data.addProperty("name", getValue().getName());
/* 46 */       super.onSerialization(data);
/* 47 */       data.addProperty("level", Integer.valueOf(this.permissionLevel));
/* 48 */       data.addProperty("bypassesPlayerLimit", Boolean.valueOf(this.bypassesPlayerLimit));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static GameProfile constructProfile(JsonObject p_152643_0_) {
/* 54 */     if (p_152643_0_.has("uuid") && p_152643_0_.has("name")) {
/*    */       UUID uuid;
/* 56 */       String s = p_152643_0_.get("uuid").getAsString();
/*    */ 
/*    */ 
/*    */       
/*    */       try {
/* 61 */         uuid = UUID.fromString(s);
/*    */       }
/* 63 */       catch (Throwable var4) {
/*    */         
/* 65 */         return null;
/*    */       } 
/*    */       
/* 68 */       return new GameProfile(uuid, p_152643_0_.get("name").getAsString());
/*    */     } 
/*    */ 
/*    */     
/* 72 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\server\management\UserListOpsEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */