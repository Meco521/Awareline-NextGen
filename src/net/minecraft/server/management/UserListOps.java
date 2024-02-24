/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ public class UserListOps
/*    */   extends UserList<GameProfile, UserListOpsEntry>
/*    */ {
/*    */   public UserListOps(File saveFile) {
/* 12 */     super(saveFile);
/*    */   }
/*    */ 
/*    */   
/*    */   protected UserListEntry<GameProfile> createEntry(JsonObject entryData) {
/* 17 */     return new UserListOpsEntry(entryData);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getKeys() {
/* 22 */     String[] astring = new String[getValues().size()];
/* 23 */     int i = 0;
/*    */     
/* 25 */     for (UserListOpsEntry userlistopsentry : getValues().values())
/*    */     {
/* 27 */       astring[i++] = userlistopsentry.getValue().getName();
/*    */     }
/*    */     
/* 30 */     return astring;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean bypassesPlayerLimit(GameProfile profile) {
/* 35 */     UserListOpsEntry userlistopsentry = getEntry(profile);
/* 36 */     return (userlistopsentry != null) ? userlistopsentry.bypassesPlayerLimit() : false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getObjectKey(GameProfile obj) {
/* 44 */     return obj.getId().toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GameProfile getGameProfileFromName(String username) {
/* 52 */     for (UserListOpsEntry userlistopsentry : getValues().values()) {
/*    */       
/* 54 */       if (username.equalsIgnoreCase(userlistopsentry.getValue().getName()))
/*    */       {
/* 56 */         return userlistopsentry.getValue();
/*    */       }
/*    */     } 
/*    */     
/* 60 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\server\management\UserListOps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */