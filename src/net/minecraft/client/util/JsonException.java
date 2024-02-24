/*    */ package net.minecraft.client.util;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ 
/*    */ public class JsonException
/*    */   extends IOException
/*    */ {
/* 12 */   private final List<Entry> field_151383_a = Lists.newArrayList();
/*    */   
/*    */   private final String exceptionMessage;
/*    */   
/*    */   public JsonException(String message) {
/* 17 */     this.field_151383_a.add(new Entry());
/* 18 */     this.exceptionMessage = message;
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonException(String message, Throwable cause) {
/* 23 */     super(cause);
/* 24 */     this.field_151383_a.add(new Entry());
/* 25 */     this.exceptionMessage = message;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_151380_a(String p_151380_1_) {
/* 30 */     ((Entry)this.field_151383_a.get(0)).func_151373_a(p_151380_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_151381_b(String p_151381_1_) {
/* 35 */     ((Entry)this.field_151383_a.get(0)).field_151376_a = p_151381_1_;
/* 36 */     this.field_151383_a.add(0, new Entry());
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 41 */     return "Invalid " + ((Entry)this.field_151383_a.get(this.field_151383_a.size() - 1)).toString() + ": " + this.exceptionMessage;
/*    */   }
/*    */ 
/*    */   
/*    */   public static JsonException func_151379_a(Exception p_151379_0_) {
/* 46 */     if (p_151379_0_ instanceof JsonException)
/*    */     {
/* 48 */       return (JsonException)p_151379_0_;
/*    */     }
/*    */ 
/*    */     
/* 52 */     String s = p_151379_0_.getMessage();
/*    */     
/* 54 */     if (p_151379_0_ instanceof java.io.FileNotFoundException)
/*    */     {
/* 56 */       s = "File not found";
/*    */     }
/*    */     
/* 59 */     return new JsonException(s, p_151379_0_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class Entry
/*    */   {
/* 70 */     String field_151376_a = null;
/* 71 */     private final List<String> field_151375_b = Lists.newArrayList();
/*    */ 
/*    */ 
/*    */     
/*    */     void func_151373_a(String p_151373_1_) {
/* 76 */       this.field_151375_b.add(0, p_151373_1_);
/*    */     }
/*    */ 
/*    */     
/*    */     public String func_151372_b() {
/* 81 */       return StringUtils.join(this.field_151375_b, "->");
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 86 */       return (this.field_151376_a != null) ? (!this.field_151375_b.isEmpty() ? (this.field_151376_a + " " + func_151372_b()) : this.field_151376_a) : (!this.field_151375_b.isEmpty() ? ("(Unknown file) " + func_151372_b()) : "(Unknown file)");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\clien\\util\JsonException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */