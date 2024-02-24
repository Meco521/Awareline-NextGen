/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ 
/*    */ public abstract class BanEntry<T>
/*    */   extends UserListEntry<T>
/*    */ {
/* 11 */   public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
/*    */   
/*    */   protected final Date banStartDate;
/*    */   protected final String bannedBy;
/*    */   protected final Date banEndDate;
/*    */   protected final String reason;
/*    */   
/*    */   public BanEntry(T valueIn, Date startDate, String banner, Date endDate, String banReason) {
/* 19 */     super(valueIn);
/* 20 */     this.banStartDate = (startDate == null) ? new Date() : startDate;
/* 21 */     this.bannedBy = (banner == null) ? "(Unknown)" : banner;
/* 22 */     this.banEndDate = endDate;
/* 23 */     this.reason = (banReason == null) ? "Banned by an operator." : banReason;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BanEntry(T valueIn, JsonObject json) {
/* 28 */     super(valueIn, json);
/*    */     
/*    */     Date date, date1;
/*    */     
/*    */     try {
/* 33 */       date = json.has("created") ? dateFormat.parse(json.get("created").getAsString()) : new Date();
/*    */     }
/* 35 */     catch (ParseException var7) {
/*    */       
/* 37 */       date = new Date();
/*    */     } 
/*    */     
/* 40 */     this.banStartDate = date;
/* 41 */     this.bannedBy = json.has("source") ? json.get("source").getAsString() : "(Unknown)";
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 46 */       date1 = json.has("expires") ? dateFormat.parse(json.get("expires").getAsString()) : null;
/*    */     }
/* 48 */     catch (ParseException var6) {
/*    */       
/* 50 */       date1 = null;
/*    */     } 
/*    */     
/* 53 */     this.banEndDate = date1;
/* 54 */     this.reason = json.has("reason") ? json.get("reason").getAsString() : "Banned by an operator.";
/*    */   }
/*    */ 
/*    */   
/*    */   public Date getBanEndDate() {
/* 59 */     return this.banEndDate;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getBanReason() {
/* 64 */     return this.reason;
/*    */   }
/*    */ 
/*    */   
/*    */   boolean hasBanExpired() {
/* 69 */     return (this.banEndDate == null) ? false : this.banEndDate.before(new Date());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onSerialization(JsonObject data) {
/* 74 */     data.addProperty("created", dateFormat.format(this.banStartDate));
/* 75 */     data.addProperty("source", this.bannedBy);
/* 76 */     data.addProperty("expires", (this.banEndDate == null) ? "forever" : dateFormat.format(this.banEndDate));
/* 77 */     data.addProperty("reason", this.reason);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\server\management\BanEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */