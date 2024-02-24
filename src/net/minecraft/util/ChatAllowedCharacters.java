/*    */ package net.minecraft.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatAllowedCharacters
/*    */ {
/*  8 */   public static final char[] allowedCharactersArray = new char[] { '/', '\n', '\r', '\t', Character.MIN_VALUE, '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':' };
/*    */ 
/*    */   
/*    */   public static boolean isAllowedCharacter(char character) {
/* 12 */     return (character != '§' && character >= ' ' && character != '');
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String filterAllowedCharacters(String input) {
/* 20 */     StringBuilder stringbuilder = new StringBuilder();
/*    */     
/* 22 */     for (char c0 : input.toCharArray()) {
/*    */       
/* 24 */       if (isAllowedCharacter(c0))
/*    */       {
/* 26 */         stringbuilder.append(c0);
/*    */       }
/*    */     } 
/*    */     
/* 30 */     return stringbuilder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\ChatAllowedCharacters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */