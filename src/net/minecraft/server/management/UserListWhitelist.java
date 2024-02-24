/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ public class UserListWhitelist
/*    */   extends UserList<GameProfile, UserListWhitelistEntry>
/*    */ {
/*    */   public UserListWhitelist(File p_i1132_1_) {
/* 12 */     super(p_i1132_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   protected UserListEntry<GameProfile> createEntry(JsonObject entryData) {
/* 17 */     return new UserListWhitelistEntry(entryData);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getKeys() {
/* 22 */     String[] astring = new String[getValues().size()];
/* 23 */     int i = 0;
/*    */     
/* 25 */     for (UserListWhitelistEntry userlistwhitelistentry : getValues().values())
/*    */     {
/* 27 */       astring[i++] = userlistwhitelistentry.getValue().getName();
/*    */     }
/*    */     
/* 30 */     return astring;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getObjectKey(GameProfile obj) {
/* 38 */     return obj.getId().toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GameProfile getBannedProfile(String name) {
/* 46 */     for (UserListWhitelistEntry userlistwhitelistentry : getValues().values()) {
/*    */       
/* 48 */       if (name.equalsIgnoreCase(userlistwhitelistentry.getValue().getName()))
/*    */       {
/* 50 */         return userlistwhitelistentry.getValue();
/*    */       }
/*    */     } 
/*    */     
/* 54 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\server\management\UserListWhitelist.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */