/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import java.io.File;
/*    */ import java.net.SocketAddress;
/*    */ 
/*    */ 
/*    */ public class BanList
/*    */   extends UserList<String, IPBanEntry>
/*    */ {
/*    */   public BanList(File bansFile) {
/* 12 */     super(bansFile);
/*    */   }
/*    */ 
/*    */   
/*    */   protected UserListEntry<String> createEntry(JsonObject entryData) {
/* 17 */     return new IPBanEntry(entryData);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBanned(SocketAddress address) {
/* 22 */     String s = addressToString(address);
/* 23 */     return hasEntry(s);
/*    */   }
/*    */ 
/*    */   
/*    */   public IPBanEntry getBanEntry(SocketAddress address) {
/* 28 */     String s = addressToString(address);
/* 29 */     return getEntry(s);
/*    */   }
/*    */ 
/*    */   
/*    */   private String addressToString(SocketAddress address) {
/* 34 */     String s = address.toString();
/*    */     
/* 36 */     if (s.contains("/"))
/*    */     {
/* 38 */       s = s.substring(s.indexOf('/') + 1);
/*    */     }
/*    */     
/* 41 */     if (s.contains(":"))
/*    */     {
/* 43 */       s = s.substring(0, s.indexOf(':'));
/*    */     }
/*    */     
/* 46 */     return s;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\server\management\BanList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */