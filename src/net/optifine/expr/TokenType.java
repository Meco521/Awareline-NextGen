/*    */ package net.optifine.expr;
/*    */ 
/*    */ public enum TokenType
/*    */ {
/*  5 */   IDENTIFIER("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_:."),
/*  6 */   NUMBER("0123456789", "0123456789."),
/*  7 */   OPERATOR("+-*/%!&|<>=", "&|="),
/*  8 */   COMMA(","),
/*  9 */   BRACKET_OPEN("("),
/* 10 */   BRACKET_CLOSE(")");
/*    */   private final String charsFirst;
/*    */   
/*    */   static {
/* 14 */     VALUES = values();
/*    */   }
/*    */ 
/*    */   
/*    */   private final String charsNext;
/*    */   
/*    */   public static final TokenType[] VALUES;
/*    */   
/*    */   TokenType(String charsFirst, String charsNext) {
/* 23 */     this.charsFirst = charsFirst;
/* 24 */     this.charsNext = charsNext;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getCharsFirst() {
/* 29 */     return this.charsFirst;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getCharsNext() {
/* 34 */     return this.charsNext;
/*    */   }
/*    */ 
/*    */   
/*    */   public static TokenType getTypeByFirstChar(char ch) {
/* 39 */     for (int i = 0; i < VALUES.length; i++) {
/*    */       
/* 41 */       TokenType tokentype = VALUES[i];
/*    */       
/* 43 */       if (tokentype.charsFirst.indexOf(ch) >= 0)
/*    */       {
/* 45 */         return tokentype;
/*    */       }
/*    */     } 
/*    */     
/* 49 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasCharNext(char ch) {
/* 54 */     return (this.charsNext.indexOf(ch) >= 0);
/*    */   }
/*    */   
/*    */   private static class Const {
/*    */     static final String ALPHAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
/*    */     static final String DIGITS = "0123456789";
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\expr\TokenType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */