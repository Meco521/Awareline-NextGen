/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ public class IPBanEntry
/*    */   extends BanEntry<String>
/*    */ {
/*    */   public IPBanEntry(String valueIn) {
/* 11 */     this(valueIn, (Date)null, (String)null, (Date)null, (String)null);
/*    */   }
/*    */ 
/*    */   
/*    */   public IPBanEntry(String valueIn, Date startDate, String banner, Date endDate, String banReason) {
/* 16 */     super(valueIn, startDate, banner, endDate, banReason);
/*    */   }
/*    */ 
/*    */   
/*    */   public IPBanEntry(JsonObject json) {
/* 21 */     super(getIPFromJson(json), json);
/*    */   }
/*    */ 
/*    */   
/*    */   private static String getIPFromJson(JsonObject json) {
/* 26 */     return json.has("ip") ? json.get("ip").getAsString() : null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onSerialization(JsonObject data) {
/* 31 */     if (getValue() != null) {
/*    */       
/* 33 */       data.addProperty("ip", getValue());
/* 34 */       super.onSerialization(data);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\server\management\IPBanEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */