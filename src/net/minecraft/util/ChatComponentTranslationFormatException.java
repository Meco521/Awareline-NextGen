/*    */ package net.minecraft.util;
/*    */ 
/*    */ public class ChatComponentTranslationFormatException
/*    */   extends IllegalArgumentException
/*    */ {
/*    */   public ChatComponentTranslationFormatException(ChatComponentTranslation component, String message) {
/*  7 */     super(String.format("Error parsing: %s: %s", new Object[] { component, message }));
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatComponentTranslationFormatException(ChatComponentTranslation component, int index) {
/* 12 */     super(String.format("Invalid index %d requested for %s", new Object[] { Integer.valueOf(index), component }));
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatComponentTranslationFormatException(ChatComponentTranslation component, Throwable cause) {
/* 17 */     super(String.format("Error while parsing: %s", new Object[] { component }), cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\ChatComponentTranslationFormatException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */