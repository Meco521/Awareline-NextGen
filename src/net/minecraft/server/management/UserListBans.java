/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ public class UserListBans
/*    */   extends UserList<GameProfile, UserListBansEntry>
/*    */ {
/*    */   public UserListBans(File bansFile) {
/* 12 */     super(bansFile);
/*    */   }
/*    */ 
/*    */   
/*    */   protected UserListEntry<GameProfile> createEntry(JsonObject entryData) {
/* 17 */     return new UserListBansEntry(entryData);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBanned(GameProfile profile) {
/* 22 */     return hasEntry(profile);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getKeys() {
/* 27 */     String[] astring = new String[getValues().size()];
/* 28 */     int i = 0;
/*    */     
/* 30 */     for (UserListBansEntry userlistbansentry : getValues().values())
/*    */     {
/* 32 */       astring[i++] = userlistbansentry.getValue().getName();
/*    */     }
/*    */     
/* 35 */     return astring;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getObjectKey(GameProfile obj) {
/* 43 */     return obj.getId().toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public GameProfile isUsernameBanned(String username) {
/* 48 */     for (UserListBansEntry userlistbansentry : getValues().values()) {
/*    */       
/* 50 */       if (username.equalsIgnoreCase(userlistbansentry.getValue().getName()))
/*    */       {
/* 52 */         return userlistbansentry.getValue();
/*    */       }
/*    */     } 
/*    */     
/* 56 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\server\management\UserListBans.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */